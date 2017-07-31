package vn.fjobs.app.common.api;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import vn.fjobs.app.Config;
import vn.fjobs.app.common.RequestParams;
import vn.fjobs.app.common.connection.Method;
import vn.fjobs.app.common.util.LogUtils;
import vn.fjobs.base.api.Api;
import vn.fjobs.base.api.ResponseReceiver;

public class OkHttpApiImpl implements Api {

    private final OkHttpClient mOkHttpClient;

    public OkHttpApiImpl() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(Config.TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(Config.TIMEOUT_READ, TimeUnit.SECONDS)
                .build();
        mOkHttpClient = okHttpClient;
    }

    @Override
    public void startRequest(int requestId, RequestParams data, ResponseReceiver responseReceiver) {
        callApi(requestId, data, responseReceiver);
        if (responseReceiver != null) {
            responseReceiver.startRequest(requestId);
        }
    }

    @Override
    public void restartRequest(int requestId, RequestParams data, ResponseReceiver responseReceiver) {
        cancelCallWithTag(requestId);
        callApi(requestId, data, responseReceiver);
        if (responseReceiver != null) {
            responseReceiver.startRequest(requestId);
        }
    }

    @Override
    public void destroyRequest(int requestId) {
        cancelCallWithTag(requestId);
    }

    private void callApi(int requestId, RequestParams baseRequest, ResponseReceiver listener) {
        LogUtils.d("request", baseRequest.toString());
        mOkHttpClient.newCall(makeRequest(baseRequest)
                .tag(requestId)
                .build())
                .enqueue(new CallBackWrapper(requestId, baseRequest, listener));
    }

    private Request.Builder makeRequest(RequestParams baseRequest) {
        String url = Config.SERVER_URL;
//        if (baseRequest.getMethod() == Method.GET) {
//            url = Config.IMAGE_SERVER_URL + baseRequest.toString();
//        }
        Request.Builder request = new Request.Builder()
                .url(url)
                .headers(baseRequest.getHeaders());
        if (baseRequest.getMethod() == Method.GET){
            request.method(ApiKey.API_METHOD_GET, null);
        }else{
            request.method(ApiKey.API_METHOD_POST, baseRequest.getBody());
        }

        return request;
    }

    private void cancelCallWithTag(int requestId) {
        // A call may transition from queue -> running. Remove queued Calls first.
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if ((int) call.request().tag() == requestId)
                call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if ((int) call.request().tag() == requestId)
                call.cancel();
        }
    }
}
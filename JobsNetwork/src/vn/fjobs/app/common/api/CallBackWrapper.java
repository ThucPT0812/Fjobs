package vn.fjobs.app.common.api;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.SoftReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import vn.fjobs.app.common.RequestParams;
import vn.fjobs.app.common.connection.ResponseData;
import vn.fjobs.app.common.util.LogUtils;
import vn.fjobs.base.api.ResponseReceiver;

public class CallBackWrapper implements Callback {
    private SoftReference<ResponseReceiver> softReference;
    private int requestId;
    private RequestParams requestParams;

    CallBackWrapper(int requestId, RequestParams requestParams, ResponseReceiver listener) {
        this.requestId = requestId;
        this.requestParams = requestParams;
        this.softReference = new SoftReference<>(listener);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (call.isCanceled()) return;
        e.printStackTrace();
        ResponseData responseData = new ResponseData();
        responseData.setStatus(vn.fjobs.app.common.connection.Response.CLIENT_ERROR_UNKNOW);
        // parse get data
        if (softReference.get() == null)
            return;

        vn.fjobs.app.common.connection.Response parseResponse = softReference.get().parseResponse(requestId, responseData);
        deliverUIResponse(parseResponse);
    }

    @Override
    public void onResponse(final Call call, Response response) throws IOException {

        int statusCode = response.code();
        boolean isError = false;
        if (statusCode < 200 || statusCode > 299) {
            isError = true;
        }
        /*
        If call API successfully
         */
        String responseBody = response.body().string();
        LogUtils.d("Request response", requestParams.getApi() + " - " + responseBody);
        ResponseData responseData = new ResponseData();
        responseData.setText(responseBody);
        if (!isError) {
            try {
                responseData.makeJSONObject();
            } catch (Exception e) {
                e.printStackTrace();
                responseData.setStatus(vn.fjobs.app.common.connection.Response.CLIENT_ERROR_PARSE_JSON);
            }
        } else {
            responseData.setStatus(vn.fjobs.app.common.connection.Response.CLIENT_SUCCESS);
        }

        ResponseReceiver listener = softReference.get();
        if (listener != null) {
            vn.fjobs.app.common.connection.Response temp = listener.parseResponse(requestId, responseData);
            deliverUIResponse(temp);
        }
        response.close();
    }

    private void deliverUIResponse(final vn.fjobs.app.common.connection.Response response) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ResponseReceiver listener = softReference.get();
                if (listener != null) {
                    listener.receiveResponse(requestId, response);
                }
            }
        });
    }

    public int getResponseCode(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getInt("code");
    }
}
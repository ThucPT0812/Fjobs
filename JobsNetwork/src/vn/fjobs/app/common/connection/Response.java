package vn.fjobs.app.common.connection;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.fjobs.app.BaseApp;

public abstract class Response implements Cloneable, Serializable {
    /**
     *
     */
    private static final long serialVersionUID                      = 8029736296125917165L;
    public static final int CLIENT_ERROR_UNKNOW                     = 100;
    public static final int CLIENT_ERROR_PARSE_JSON                 = 104;
    public static final int CLIENT_SUCCESS                          = 200;
    public static final int SERVER_SUCCESS                          = 1;
    public static final int SERVER_OUT_OF_DATE_API                  = 5;
    public static final int SERVER_INCORRECT_PASSWORD               = 0;

    public static final int SERVER_REGISTER_FAIL                    = 0;
    public static final int SERVER_REGISTER_EMAIL_EXISTS            = 2;

    public static final String ACTION_BLOCK_DEACTIVE = "response.block.deactive";
    public static final String EXTRA_CODE = "response.code";

    protected ResponseData responseData;
    @SerializedName("code")
    protected int code;

    protected abstract void parseData(ResponseData responseData);

    protected Context context;

    public Response() {
    }

    public Response(ResponseData responseData) {
        this.responseData = responseData;
        code = this.responseData.getStatus();
        parseData(this.responseData);
    }

    /**
     * Only use for History Response, not call parseData() in this contructor
     *
     * @param context
     * @param responseData
     */
    public Response(Context context, ResponseData responseData) {
        if(context != null) {
            this.context = context.getApplicationContext();
        } else {
            this.context = BaseApp.get();
        }
        this.responseData = responseData;
        code = this.responseData.getStatus();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    protected Response mResponse;

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void appendResponse(Response response) {
        mResponse = response;
    }

    public Response copyInstance(Response response) {
        try {
            return (Response) response.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response mResponse) {
        this.mResponse = mResponse;
    }

    @Override
    protected Response clone() throws CloneNotSupportedException {
        return (Response) super.clone();
    }
}

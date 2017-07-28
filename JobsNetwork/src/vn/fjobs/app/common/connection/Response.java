package vn.fjobs.app.common.connection;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.fjobs.app.BaseApp;

public abstract class Response implements Cloneable, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8029736296125917165L;
    public static final int CLIENT_ERROR_UNKNOW = 100;
    public static final int CLIENT_ERROR_NO_CONNECTION = 101;
    public static final int CLIENT_ERROR_NO_DATA = 102;
    public static final int CLIENT_ERROR_CAN_NOT_CONNECTION = 103;
    public static final int CLIENT_ERROR_PARSE_JSON = 104;
    public static final int CLIENT_ERROR_AUTHEN_WITH_CHAT_SERVER = 105;
    public static final int CLIENT_FILE_NOT_FOUND = 105;
    public static final int CLIENT_FILE_ERROR = 106;
    public static final int CLIENT_SUCCESS = 200;
    public static final int CLIENT_ERROR_BILLING_UNAVAIABLE = 300;

    public static final int SERVER_SUCCESS = 0;
    public static final int SERVER_UNKNOWN_ERROR = 1;
    public static final int SERVER_WRONG_DATA_FORMAT = 2;
    public static final int SERVER_INVALID_TOKEN = 3;
    public static final int SERVER_NO_CHANGE = 4;
    public static final int SERVER_OUT_OF_DATE_API = 5;
    public static final int SERVER_OLD_VERSION = 6;
    public static final int SERVER_MAINTAIN = 8;
    public static final int SERVER_AGE_DENY = 9;
    public static final int SERVER_EMAIL_NOT_FOUND = 10;
    public static final int SERVER_INVALID_EMAIL = 11;
    public static final int SERVER_EMAIL_REGISTERED = 12;
    public static final int SERVER_SEND_MAIL_FAIL = 13;
    public static final int SERVER_INVALID_USER_NAME = 14;
    public static final int SERVER_INVALID_BIRTHDAY = 15;
    public static final int SERVER_INCORRECT_PASSWORD = 20;
    public static final int SERVER_INVALID_PASSWORD = 21;
    public static final int SERVER_UPLOAD_IMAGE_ERROR = 30;
    public static final int SERVER_UPLOAD_FILE_ERROR = 35;
    public static final int SERVER_BUZZ_NOT_FOUND = 40;
    public static final int SERVER_ACCESS_DENIED = 41;
    public static final int SERVER_FORBIDDEN_IMAGE = 42;
    public static final int SERVER_COMMENT_NOT_FOUND = 43;
    public static final int SERVER_FILE_NOT_FOUND = 46;
    public static final int SERVER_LOCKED_FEARUTE = 50;
    public static final int SERVER_BLOCKED_USER = 60;
    public static final int SERVER_NOT_ENOUGHT_MONEY = 70;
    public static final int SERVER_RECEIVER_NOT_ENOUGH_MONEY = 71;
    public static final int SERVER_ITEM_NOT_AVAIABLE = 79;
    public static final int SERVER_USER_NOT_EXIST = 80;
    public static final int SERVER_LOOKED_USER = 81;
    public static final int SERVER_INCORRECT_CODE = 90;
    public static final int SERVER_ALREADY_PURCHASE = 99;

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

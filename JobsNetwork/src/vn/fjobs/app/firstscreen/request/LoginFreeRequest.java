package vn.fjobs.app.firstscreen.request;

import com.google.gson.annotations.SerializedName;

import vn.fjobs.app.common.RequestParams;

public class LoginFreeRequest extends RequestParams {

    @SerializedName("token")
    private String token;

    @SerializedName("codeid")
    private String codeID;

    public LoginFreeRequest(String codeID, String token){
        this.api = "loginfree";
        this.codeID = codeID;
        this.token = token;
    }
}

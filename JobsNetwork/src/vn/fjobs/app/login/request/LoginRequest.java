package vn.fjobs.app.login.request;

import com.google.gson.annotations.SerializedName;

import vn.fjobs.app.common.RequestParams;

public class LoginRequest extends RequestParams {

    private static final long serialVersionUID = -7060477858271184742L;

    @SerializedName("userid")
    protected String email;

    @SerializedName("pwd")
    protected String pwd;

    @SerializedName("codeid")
    protected int codeID;

    public LoginRequest(String email, String password, int codeID){
        this.api = "checkCredentials";
        this.email = email;
        this.pwd = password;
        this.codeID = codeID;
    }
}

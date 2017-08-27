package vn.fjobs.app.register.request;

import com.google.gson.annotations.SerializedName;

import vn.fjobs.app.common.RequestParams;

public class RegisterRequest extends RequestParams {

    @SerializedName("codeid")
    private String codeID;

    @SerializedName("nick_name")
    private String nickName;

    @SerializedName("email")
    private String email;

    @SerializedName("pass")
    private String pass;

    @SerializedName("birth_day")
    private String birthDay;

    @SerializedName("gender")
    private String gender;

    @SerializedName("type")
    private String type;

    public RegisterRequest(String codeID, String nickName, String email, String pass, String birthDay, String gender, String type){
        this.api = "register";
        this.codeID = codeID;
        this.nickName = nickName;
        this.email = email;
        this.pass = pass;
        this.birthDay = birthDay;
        this.gender = gender;
        this.type = type;
    }
}

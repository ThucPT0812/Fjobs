package vn.fjobs.app.common;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import vn.fjobs.app.common.connection.Method;

public abstract class RequestParams implements Serializable {

    @SerializedName("api")
    protected String api;

    public String getApi() {
        return api;
    }

    @SerializedName("token")
    protected String token;

    public void setApi(String api) {
        this.api = api;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Headers getHeaders() {
        Headers.Builder builder = new Headers.Builder()
                .add("Content-Type", "application/json");
        return builder.build();
    }

    public RequestBody getBody() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(JSON, toString());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMethod() {
        return Method.POST;
    }
}
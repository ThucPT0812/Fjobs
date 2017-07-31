package vn.fjobs.app.login.response;

import org.json.JSONException;
import org.json.JSONObject;

import vn.fjobs.app.common.account.AuthenticationData;
import vn.fjobs.app.common.connection.Response;
import vn.fjobs.app.common.connection.ResponseData;

public class LoginResponse extends Response {

    private static final long serialVersionUID = -8445666679255121953L;
    private AuthenticationData authenData;

    public LoginResponse(ResponseData responseData) {
        super(responseData);
    }

    @Override
    protected void parseData(ResponseData responseData) {
        try{
            JSONObject jsonObject = responseData.getJSONObject();
            if (jsonObject != null) {
                if (jsonObject.has("code")) {
                    setCode(jsonObject.getInt("code"));
                }

                if (jsonObject.has("data")) {
                    JSONObject dataJson = jsonObject.getJSONObject("data");
                    authenData = new AuthenticationData();

                    if (dataJson.has("token")) {
                        authenData.setToken(dataJson.getString("token"));
                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
            setCode(CLIENT_ERROR_PARSE_JSON);
        }
    }
}

package vn.fjobs.app.register.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.fjobs.app.common.account.AuthenticationData;
import vn.fjobs.app.common.connection.Response;
import vn.fjobs.app.common.connection.ResponseData;
import vn.fjobs.app.common.preferences.UserPreferences;

public class RegisterResponse extends Response {

    private AuthenticationData authenData;

    public RegisterResponse(ResponseData responseData){
        super(responseData);
    }

    @Override
    protected void parseData(ResponseData responseData) {
        try{
            JSONObject jsonObject = responseData.getJSONObject();
            if(jsonObject != null){
                if(jsonObject.has("data")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject objectJson = jsonArray.getJSONObject(0);
                    if(objectJson.has("errorcode")){
                        setCode(objectJson.getInt("errorcode"));
                    }

                    authenData = new AuthenticationData();
                    if(objectJson.has("token")){
                        authenData.setToken(objectJson.getString("token"));
                        UserPreferences.getInstance().saveToken(objectJson.getString("token"));
                    }
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            setCode(CLIENT_ERROR_PARSE_JSON);
        }
    }
}

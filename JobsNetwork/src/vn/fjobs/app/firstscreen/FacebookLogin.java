package vn.fjobs.app.firstscreen;

import android.content.Intent;

import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONObject;

public interface FacebookLogin {

    void initFacebook();

    void loginByFacebook();

    void clearFacebookToken();

    void setOnDataResult(OnDataResult onDataResult);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    interface OnDataResult {
        void onStartGetData();

        void onDataResult(JSONObject object, GraphResponse response, Profile profile);

    }
}

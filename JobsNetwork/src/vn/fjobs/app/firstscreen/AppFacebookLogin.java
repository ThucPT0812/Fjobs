package vn.fjobs.app.firstscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import vn.fjobs.app.common.util.LogUtils;

public class AppFacebookLogin implements FacebookLogin {

    private CallbackManager callbackManager;
    private OnDataResult onDataResult;
    private static final List<String> PERMISSIONS_READ = Arrays.asList("public_profile", "email","user_friends");
    private Activity activity;

    public AppFacebookLogin(Activity activity) {
        this.activity = activity;
        if(activity instanceof OnDataResult) {
            this.onDataResult = (OnDataResult) activity;
        }
    }

    @Override
    public void initFacebook() {

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getDataFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                clearFacebookToken();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void loginByFacebook() {
        if(isLoggedIn()) {
            getDataFacebook(AccessToken.getCurrentAccessToken());
        } else {
            LoginManager.getInstance().logInWithReadPermissions(activity, PERMISSIONS_READ);
        }
    }

    @Override
    public void clearFacebookToken() {
        if (isLoggedIn()) {
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void setOnDataResult(OnDataResult onDataResult) {
        this.onDataResult = onDataResult;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void getDataFacebook(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {

                    private ProfileTracker profileTracker;
                    @Override
                    public void onCompleted(final JSONObject object, final GraphResponse response) {
                        LogUtils.d("AppFacebookLogin", "data: " + object.toString());
                        if (onDataResult != null) {
                            if (Profile.getCurrentProfile() == null) {
                                profileTracker = new ProfileTracker() {
                                    @Override
                                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                        onDataResult.onDataResult(object, response, currentProfile);
                                        profileTracker.stopTracking();
                                    }
                                };
                            } else {
                                onDataResult.onDataResult(object, response, null);
                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "birthday, id, name, email, gender, picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
        if (onDataResult != null) {
            onDataResult.onStartGetData();
        }
    }
}

package vn.fjobs.app.firstscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import project.fjobs.R;
import vn.fjobs.app.Constant;
import vn.fjobs.app.common.connection.Response;
import vn.fjobs.app.common.connection.ResponseData;
import vn.fjobs.app.common.entity.User;
import vn.fjobs.app.common.preferences.UserPreferences;
import vn.fjobs.app.common.util.LogUtils;
import vn.fjobs.app.firstscreen.request.LoginFreeRequest;
import vn.fjobs.app.firstscreen.response.LoginFreeResponse;
import vn.fjobs.app.login.LoginActivity;
import vn.fjobs.app.register.RegisterActivity;
import vn.fjobs.base.activities.BaseAppActivity;
import vn.fjobs.base.api.ResponseReceiver;

public class SignUpActivity extends BaseAppActivity implements OnClickListener, GoogleApiClient.OnConnectionFailedListener,
        FacebookLogin.OnDataResult, ResponseReceiver {

    private static final String TAG = "SignUpActivity";
    private GoogleApiClient googleApiClient;
    private boolean isLoginEdGoogle = false;
    protected FacebookLogin facebookLogin;
    private User user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();

        facebookLogin = new AppFacebookLogin(this);
        facebookLogin.initFacebook();
    }

    private void initView() {
        Button login = (Button) findViewById(R.id.activity_signup_btn_login);
        Button signUpFree = (Button) findViewById(R.id.login_free);
        RelativeLayout loginByFacebook = (RelativeLayout) findViewById(R.id.activity_signup_connect_fb);
        RelativeLayout loginByGoogle = (RelativeLayout) findViewById(R.id.activity_signup_connect_google);
        Button register = (Button)findViewById(R.id.signup_btn_register);
        register.setOnClickListener(this);
        signUpFree.setOnClickListener(this);
        login.setOnClickListener(this);
        loginByFacebook.setOnClickListener(this);
        loginByGoogle.setOnClickListener(this);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.waiting));
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResultGoogle(result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getContentFrame() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_free:
                loginFree();
                break;
            case R.id.activity_signup_btn_login:
                startCustomActivityForResult(new Intent(this, LoginActivity.class));
                break;
            case R.id.activity_signup_connect_google:
                handlerLoginGoogleClick();
                break;
            case R.id.activity_signup_connect_fb:
                facebookLogin.loginByFacebook();
                break;
            case R.id.signup_btn_register:
                startRegisterActivity();
                break;
        }
    }

    private void loginFree(){
        LoginFreeRequest loginFreeRequest = new LoginFreeRequest(String.valueOf(Constant.LOADER_LOGIN_FREE),
                UserPreferences.getInstance().getToken());
        api.startRequest(Constant.LOADER_LOGIN_FREE, loginFreeRequest, this);
        progressDialog.show();
    }

    private void handlerLoginGoogleClick(){
        if(!isLoginEdGoogle){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, Constant.RC_SIGN_IN_GOOGLE);
            isLoginEdGoogle = true;
        }else{

            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(SignUpActivity.this, "Sign out google", Toast.LENGTH_LONG).show();
                        }
                    });
            isLoginEdGoogle = false;
        }

    }

    protected void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startCustomActivityForResult(intent);
    }

    protected void startCustomActivityForResult(Intent intent) {
        startActivityForResult(intent, Constant.REQUEST_EXIT);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LogUtils.d("SignUpActivity", "onConnectionFailed:" + connectionResult);
    }

    private void handleSignInResultGoogle(GoogleSignInResult result){
        if(result.isSuccess()){
            // Signed in successfully
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this, "Login google success: " + acct.getDisplayName(), Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RC_SIGN_IN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResultGoogle(result);
        }

        facebookLogin.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onStartGetData() {
        showProgressLoading();
    }

    @Override
    public void onDataResult(JSONObject object, GraphResponse response, Profile profile) {
        String name = "";
        String gender = "";
        String birthDay = "";
        String userId = "";
        String profilePictureUrl = "";

        try {
            if (object.has("name")) {
                name = object.getString("name");
            }
            if (object.has("gender")) {
                gender = object.getString("gender");
            }
            if (object.has("birthday")) {
                birthDay = object.optString("birthday");
            }
            if (object.has("id")) {
                userId = object.optString("id");
            }

            boolean isDefaultAvatar;
            if (object.has("picture")) {
                JSONObject picture = object.getJSONObject("picture");
                if (picture.has("data")) {
                    JSONObject data = picture.getJSONObject("data");
                    isDefaultAvatar = !data.has("is_silhouette") || data.getBoolean("is_silhouette");
                    profilePictureUrl = isDefaultAvatar ||  !data.has("url") ? "" : data.getString("url");
                }
            }

            Profile currentProfile = profile != null ? profile : Profile.getCurrentProfile();
            if (currentProfile != null) {
                userId = currentProfile.getId();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getFacebookUserInfo(name, profilePictureUrl, gender);
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        dismissProgressLoading();
    }

    private void getFacebookUserInfo(String fullName, String profilePictureUri, String birthDay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Date date = null;

        if (birthDay != null) {
            try {
                date = dateFormat.parse(birthDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        user = new User();
        user.setName(fullName);
        user.setBirthday(date);
        user.setAvatar(profilePictureUri);
    }

    @Override
    public void startRequest(int requestId) {

    }

    @Override
    public Response parseResponse(int requestId, ResponseData data) {
        Response response = null;
        switch (requestId){
            case Constant.LOADER_LOGIN_FREE:
                response = new LoginFreeResponse(data);
                break;
            default:
                break;
        }
        return response;
    }

    @Override
    public void receiveResponse(int requestId, Response response) {
        if(progressDialog != null)
            progressDialog.dismiss();

        if(response == null){
            return;
        }
        if(requestId == Constant.LOADER_LOGIN_FREE){
            if(response.getCode() == Response.SERVER_SUCCESS){
                LoginFreeResponse loginFreeResponse = (LoginFreeResponse) response;
                loginSuccess(loginFreeResponse);
            }
        }
    }

    private void loginSuccess(LoginFreeResponse response){

    }
}

package vn.fjobs.app.firstscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import project.fjobs.R;
import vn.fjobs.app.Constant;
import vn.fjobs.app.common.util.LogUtils;
import vn.fjobs.app.login.LoginActivity;
import vn.fjobs.app.register.RegisterActivity;
import vn.fjobs.base.activities.BaseAppActivity;

public class SignUpActivity extends BaseAppActivity implements OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignUpActivity";
    private Button signUp;
    private Button login;
    private RelativeLayout loginByFacebook;
    private GoogleApiClient googleApiClient;
    private RelativeLayout loginByGoogle;
    private boolean isLoginedGoogle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }

    private void initView() {
        login = (Button) findViewById(R.id.activity_signup_btn_login);
        signUp = (Button) findViewById(R.id.activity_signup_btn_signup);
        loginByFacebook = (RelativeLayout) findViewById(R.id.activity_signup_connect_fb);
        loginByGoogle = (RelativeLayout) findViewById(R.id.activity_signup_connect_google);
        signUp.setOnClickListener(this);
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
            case R.id.activity_signup_btn_signup:
                startRegisterActivity();
                break;
            case R.id.activity_signup_btn_login:
                startCustomActivityForResult(new Intent(this, LoginActivity.class));
                break;
            case R.id.activity_signup_connect_google:
                handlerLoginGoogleClick();
                break;
        }
    }

    private void handlerLoginGoogleClick(){
        if(!isLoginedGoogle){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, Constant.RC_SIGN_IN_GOOGLE);
            isLoginedGoogle = true;
        }else{

            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(SignUpActivity.this, "Sign out google", Toast.LENGTH_LONG).show();
                        }
                    });
            isLoginedGoogle = false;
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
    }
}

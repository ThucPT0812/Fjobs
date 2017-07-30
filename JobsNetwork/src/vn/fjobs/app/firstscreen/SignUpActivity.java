package vn.fjobs.app.firstscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import project.fjobs.R;
import vn.fjobs.app.Constant;
import vn.fjobs.app.register.RegisterActivity;
import vn.fjobs.base.activities.BaseAppActivity;

public class SignUpActivity extends BaseAppActivity implements OnClickListener {

    private static final String TAG = "SignUpActivity";
    private Button signUp;
    private Button login;
    private RelativeLayout loginByFacebook;

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
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
        loginByFacebook.setOnClickListener(this);

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
        }
    }

    protected void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startCustomActivityForResult(intent);
    }

    protected void startCustomActivityForResult(Intent intent) {
        startActivityForResult(intent, Constant.REQUEST_EXIT);
    }
}

package vn.fjobs.app.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import project.fjobs.R;
import vn.fjobs.app.Constant;
import vn.fjobs.app.common.connection.Response;
import vn.fjobs.app.common.connection.ResponseData;
import vn.fjobs.app.common.util.Utility;
import vn.fjobs.app.login.request.LoginRequest;
import vn.fjobs.app.login.response.LoginResponse;
import vn.fjobs.base.activities.BaseAppActivity;
import vn.fjobs.base.api.ResponseReceiver;
import vn.fjobs.base.view.customeview.ErrorApiDialog;

public class LoginActivity extends BaseAppActivity implements ResponseReceiver, View.OnClickListener {

    private Button login;
    private EditText email;
    private EditText password;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;
    protected TextView titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        login = (Button) findViewById(R.id.button_login);
        email = (EditText) findViewById(R.id.email);
        forgotPassword = (TextView) findViewById(R.id.text_view_forgot_pass);
        password = (EditText) findViewById(R.id.password);
        email.setNextFocusDownId(password.getId());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.waiting));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidParams()) {
                    login();
                    Utility.hideSoftKeyboard(LoginActivity.this);
                }
            }
        });

//        forgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(LoginActivity.this,
//                        ForgotPasswordSendEmail.class), Constant.REQUEST_EXIT);
//            }
//        });

        findViewById(R.id.actionbar_left).setOnClickListener(this);
        titleToolbar = (TextView) findViewById(R.id.actionbar_title);
        titleToolbar.setText(getString(R.string.login));
    }

    private void login() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        password = Utility.encryptPassword(password);

        LoginRequest loginRequest = new LoginRequest(email, password, String.valueOf(Constant.LOADER_LOGIN));
        api.startRequest(Constant.LOADER_LOGIN, loginRequest,this);
        progressDialog.show();
    }

    private boolean isValidParams() {
        String email = this.email.getText().toString();
        if (email.replace("\u3000", " ").trim().length() <= 0) {
            ErrorApiDialog.showAlert(this, getString(R.string.login),
                    getString(R.string.email_is_empty));
            return false;
        }
        if (!Utility.isValidEmail(email)) {
            ErrorApiDialog.showAlert(this, getString(R.string.login),
                    getString(R.string.email_invalid_format));

            return false;
        }
        if (this.email.getText().length() > Constant.MAX_EMAIL_LENGTH) {
            String msg = getString(R.string.email_length_must_than,
                    Constant.MAX_EMAIL_LENGTH + "");
            String title = getString(R.string.login);
            ErrorApiDialog.showAlert(this, title, msg);
            return false;
        }
        // check empty verify code
        int length = password.getText().toString().length();
        if (length < Constant.MIN_PASSWORD_LENGTH
                || length > Constant.MAX_PASSWORD_LENGTH) {
            String msg = String.format(
                    getString(R.string.password_length_must_than),
                    Constant.MIN_PASSWORD_LENGTH + "",
                    Constant.MAX_PASSWORD_LENGTH + "");
            String title = getString(R.string.login);
            ErrorApiDialog.showAlert(this, title, msg);
            return false;
        }
        return true;
    }

    @Override
    public int getContentFrame() {
        return 0;
    }

    @Override
    public void startRequest(int requestId) {

    }

    @Override
    public Response parseResponse(int requestId, ResponseData data) {
        Response response = null;
        switch (requestId) {
            case Constant.LOADER_LOGIN:
                response = new LoginResponse(data);
                break;
            default:
                break;
        }
        return response;
    }

    @Override
    public void receiveResponse(int requestId, Response response) {
        if (progressDialog != null)
            progressDialog.dismiss();
        if (response == null)
            return;

        if (requestId == Constant.LOADER_LOGIN) {
            if (response.getCode() == Constant.LOADER_LOGIN) {
                LoginResponse loginResponse = (LoginResponse) response;
                loginSuccess(loginResponse);

            } else {
                ErrorApiDialog.showAlert(this, R.string.login, response.getCode());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.actionbar_left:
                finish();
                break;
        }
    }

    private void loginSuccess(LoginResponse loginResponse) {

    }
}

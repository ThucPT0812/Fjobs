package vn.fjobs.app.register;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import project.fjobs.R;
import vn.fjobs.app.Constant;
import vn.fjobs.app.common.connection.Response;
import vn.fjobs.app.common.connection.ResponseData;
import vn.fjobs.app.common.entity.User;
import vn.fjobs.app.common.util.LogUtils;
import vn.fjobs.app.common.util.Utility;
import vn.fjobs.app.register.request.RegisterRequest;
import vn.fjobs.app.register.response.RegisterResponse;
import vn.fjobs.base.activities.BaseAppActivity;
import vn.fjobs.base.api.ResponseReceiver;
import vn.fjobs.base.view.customeview.ErrorApiDialog;

public class RegisterActivity extends BaseAppActivity implements View.OnClickListener, ResponseReceiver {

    private boolean isGenderSelected;
    private boolean isTypeUserSelected;
    private Button btnRegister;
    private TextView tvBirthday, tvTypeUser;
    private TextView tvGender;
    private EditText edUserName, edEmail, edPass, edCofirmPass;
    private Date birthdaySelected;
    private ProgressDialog dialogProgress;
    private TextView tvUserAgree;
    private TextView tvPolicy;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private User user;
    private int typeUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.actionbar_left);
        btnRegister = (Button) findViewById(R.id.btn_register);
        tvBirthday = (TextView) findViewById(R.id.txt_birthday);
        tvGender = (TextView) findViewById(R.id.txt_gender);
        tvTypeUser = (TextView)findViewById(R.id.txt_type_user);
        tvUserAgree = (TextView) findViewById(R.id.txt_term);
        tvPolicy = (TextView) findViewById(R.id.txt_policy);
        tvTitleToolbar = (TextView) findViewById(R.id.actionbar_title);
        edUserName = (EditText) findViewById(R.id.edt_name);
        edEmail = (EditText) findViewById(R.id.edt_email);
        edPass = (EditText) findViewById(R.id.edt_new_password);
        edCofirmPass = (EditText) findViewById(R.id.edt_confirm_password);

        imgBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        findViewById(R.id.tbr_birthday).setOnClickListener(this);
        findViewById(R.id.tbr_gender).setOnClickListener(this);
        findViewById(R.id.tbr_type_user).setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        tvTypeUser.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        tvUserAgree.setOnClickListener(this);
        tvPolicy.setOnClickListener(this);

        dialogProgress = new ProgressDialog(this);
        dialogProgress.setMessage(getString(R.string.creating_account_please_wait));
        dialogProgress.setCancelable(false);

    }

    private void initData() {
        tvTitleToolbar.setText(getResources().getString(R.string.title_register));
        if (user == null){
            user = new User();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        if (v == tvGender) {
            inflater.inflate(R.menu.menu_gender, menu);
            MenuItem item;
            switch (user.getGender()) {
                case Constant.GENDER_MALE:
                    item = menu.findItem(R.id.gender_male);
                    break;
                case Constant.GENDER_FEMALE:
                    item = menu.findItem(R.id.gender_female);
                    break;
                default:
                    item = menu.findItem(R.id.gender_male);
                    break;
            }
            if (isGenderSelected) item.setChecked(true);
        }else if(v == tvTypeUser){
            inflater.inflate(R.menu.menu_requirement, menu);
            MenuItem item;
            switch (user.getTypeUser()) {
                case Constant.USER_CANDIDATE:
                    item = menu.findItem(R.id.requirement_candidate);
                    break;
                case Constant.USER_RECRUITER:
                    item = menu.findItem(R.id.requirement_recruiter);
                    break;
                default:
                    item = menu.findItem(R.id.requirement_candidate);
                    break;
            }

            if (isTypeUserSelected) item.setChecked(true);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.gender_male:
                user.setGender(Constant.GENDER_MALE);
                tvGender.setText(R.string.common_man);
                isGenderSelected = true;
                return true;

            case R.id.gender_female:
                user.setGender(Constant.GENDER_FEMALE);
                tvGender.setText(R.string.common_woman);
                isGenderSelected = true;
                return true;

            case R.id.requirement_candidate:
                user.setTypeUser(Constant.USER_CANDIDATE);
                tvTypeUser.setText(R.string.common_candidate);
                isTypeUserSelected = true;
                return true;

            case R.id.requirement_recruiter:
                user.setTypeUser(Constant.USER_RECRUITER);
                tvTypeUser.setText(R.string.common_recruiter);
                isTypeUserSelected = true;
                return true;

            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public int getContentFrame() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_left:
                finish();
                break;

            case R.id.btn_register:
                register();
                break;

            case R.id.tbr_birthday:
            case R.id.txt_birthday:
                chooseBirthday();
                break;

            case R.id.tbr_gender:
            case R.id.txt_gender:
                registerContextMenu(tvGender);
                break;

            case R.id.tbr_type_user:
            case R.id.txt_type_user:
                registerContextMenu(tvTypeUser);
                break;

            default:
                break;
        }
    }

    private void register() {

        if (TextUtils.isEmpty(edUserName.getText())) {
            ErrorApiDialog.showAlert(this, getString(R.string.setup_profile), getString(R.string.user_name_is_invalid));
            return;
        }

        if(!TextUtils.isEmpty(validEmail(edEmail.getText().toString()))){
            ErrorApiDialog.showAlert(this, getString(R.string.setup_profile), validEmail(edEmail.getText().toString()));
            return;
        }

        if(!TextUtils.isEmpty(validPass(edPass.getText().toString()))){
            ErrorApiDialog.showAlert(this, getString(R.string.setup_profile), validPass(edPass.getText().toString()));
            return;
        }

        if(!edPass.getText().toString().equals(edCofirmPass.getText().toString())){
            ErrorApiDialog.showAlert(this, getString(R.string.setup_profile), getString(R.string.retype_password_is_not_the_same));
            return;
        }

        callApiRegister();
    }

    private void callApiRegister(){
        String nickName = edUserName.getText().toString();
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();
        pass = Utility.encryptPassword(pass);
        String birthDay = "";
        String gender = "1";
        String type = String.valueOf(typeUser);

        RegisterRequest registerRequest = new RegisterRequest(String.valueOf(Constant.LOADER_REGISTER), nickName, email,
                pass, birthDay, gender, type);
        api.startRequest(Constant.LOADER_REGISTER, registerRequest, this);
        dialogProgress.show();
    }

    private void gotoActivitySendCode(){
        Intent sendCodeIntent = new Intent(this, SendCodeActivity.class);
        startActivity(sendCodeIntent);
    }

    String validEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return   getApplicationContext().getString(R.string.email_is_empty_message);
        } else if (!Utility.isValidEmail(email)) {
            return getApplicationContext().getString(R.string.email_invalid_format);
        }
        return null;
    }

    String validPass(String pass) {
        if (TextUtils.isEmpty(pass)) {
            return  getString(R.string.password_is_empty_message);
        }
        if (pass.length() < Constant.MIN_PASSWORD_LENGTH
                || pass.length() > Constant.MAX_PASSWORD_LENGTH) {
            return   getString(R.string.password_out_of_range);
        }
        return null;
    }

    private void chooseBirthday() {
        int year;
        int dayOfMonth;
        int monthOfYear;
        if (tvBirthday.getText().length() > 0) {
            String text = tvBirthday.getText().toString();
            Date date = getBirthDayFromText(text);
            Calendar calendar = Calendar.getInstance();
            if (date != null) {
                calendar.setTime(date);
            }
            year = calendar.get(Calendar.YEAR);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            monthOfYear = calendar.get(Calendar.MONTH);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 1995);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            year = calendar.get(Calendar.YEAR);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            monthOfYear = calendar.get(Calendar.MONTH);
        }

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setBirthdayToDisplay(calendar.getTime());
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, onDateSetListener, year, monthOfYear, dayOfMonth);
        datePickerDialog.show();
    }

    private Date getBirthDayFromText(String text) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_DISPLAY, Locale.getDefault());
        try {
            return dateFormat.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    private void setBirthdayToDisplay(Date date) {
        if (date == null) return;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_DISPLAY, Locale.getDefault());
            String dateString = dateFormat.format(date);
            tvBirthday.setText(dateString);
            birthdaySelected = date;
            user.setBirthday(birthdaySelected);
        } catch (NullPointerException | IllegalArgumentException ex) {
            LogUtils.e(TAG, String.valueOf(ex.getMessage()));
        }
    }

    private void registerContextMenu(View view) {
        registerForContextMenu(view);
        openContextMenu(view);
        unregisterForContextMenu(view);
    }

    @Override
    public void startRequest(int requestId) {

    }

    @Override
    public Response parseResponse(int requestId, ResponseData data) {
        Response response = null;
        switch (requestId){
            case Constant.LOADER_REGISTER:
                response = new RegisterResponse(data);
                break;

            default:
                break;
        }

        return response;
    }

    @Override
    public void receiveResponse(int requestId, Response response) {
        if(dialogProgress != null){
            dialogProgress.dismiss();
        }

        if(requestId == Constant.LOADER_REGISTER){
            if(response.getCode() == Response.SERVER_SUCCESS){
                RegisterResponse registerResponse = (RegisterResponse) response;
                registerSuccess(registerResponse);
            }else if(response.getCode() == Response.SERVER_REGISTER_FAIL){
                ErrorApiDialog.showAlert(this, getString(R.string.setup_profile),
                        getString(R.string.register_fail));
            }else if(response.getCode() == Response.SERVER_REGISTER_EMAIL_EXISTS){
                ErrorApiDialog.showAlert(this, getString(R.string.setup_profile),
                        getString(R.string.email_exists));
            }
        }
    }

    private void registerSuccess(RegisterResponse registerResponse){
        gotoActivitySendCode();
    }
}

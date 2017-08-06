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
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import project.fjobs.R;
import vn.fjobs.app.Constant;
import vn.fjobs.app.common.entity.User;
import vn.fjobs.app.common.util.LogUtils;
import vn.fjobs.base.activities.BaseAppActivity;
import vn.fjobs.base.view.customeview.ErrorApiDialog;

public class RegisterActivity extends BaseAppActivity implements View.OnClickListener {

    private boolean isGenderSelected;
    private Button btnRegister;
    private TextView tvBirthday;
    private TextView tvGender;
    private Date birthdaySelected;
    private ProgressDialog dialogProgress;
    private TextView tvUserAgree;
    private TextView tvPolicy;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private User user;

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
        tvUserAgree = (TextView) findViewById(R.id.txt_term);
        tvPolicy = (TextView) findViewById(R.id.txt_policy);
        tvTitleToolbar = (TextView) findViewById(R.id.actionbar_title);

        imgBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        findViewById(R.id.tbr_birthday).setOnClickListener(this);
        findViewById(R.id.tbr_gender).setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
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

            default:
                break;
        }
    }

    private void register() {
        if (birthdaySelected == null) {
            ErrorApiDialog.showAlert(this, getString(R.string.setup_profile), getString(R.string.birthday_value_is_invalid));
            return;
        }

        if (TextUtils.isEmpty(tvGender.getText())) {
            ErrorApiDialog.showAlert(this, getString(R.string.setup_profile), getString(R.string.gender_is_invalid));
            return;
        }

        Intent sendCodeIntent = new Intent(this, SendCodeActivity.class);
        startActivity(sendCodeIntent);
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
}

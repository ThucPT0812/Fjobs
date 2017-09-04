package vn.fjobs.app.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import co.core.imageloader.NImageLoader;
import project.fjobs.R;
import vn.fjobs.base.activities.BaseAppActivity;
import vn.fjobs.base.view.customeview.ErrorApiDialog;

public class SendCodeActivity extends BaseAppActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code);
        initView();
    }

    private void initView() {
        verifyCode = (EditText) findViewById(R.id.input_verify_code);
        Button send = (Button) findViewById(R.id.btn_send);
        TextView title = (TextView) findViewById(R.id.actionbar_title);
        title.setText(getString(R.string.title_send_code));
        ImageView imgBack = (ImageView) findViewById(R.id.actionbar_left);

        send.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int idRes = view.getId();
        switch (idRes){
            case R.id.btn_send:
                if (isValidParams()) {
                    requestSendCode();
                }
                break;
            case R.id.actionbar_left:
                finish();
                break;
            default:
                break;
        }
    }

    private boolean isValidParams(){
        int lengthVerifyCode = verifyCode.getText().toString().replace("\u3000", " ").trim().length();
        if (lengthVerifyCode == 0) {
            ErrorApiDialog.showAlert(this, getString(R.string.title_register), getString(R.string.verify_code_empty));
            return false;
        }
        return true;
    }

    private void requestSendCode(){
        String vft_code = verifyCode.getText().toString();
    }

    @Override
    public int getContentFrame() {
        return 0;
    }

    @Override
    public NImageLoader getImageLoader() {
        return null;
    }
}

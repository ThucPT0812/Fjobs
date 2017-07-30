package vn.fjobs.app.splash;

import android.content.Intent;
import android.os.Bundle;

import project.fjobs.R;
import vn.fjobs.app.firstscreen.SignUpActivity;
import vn.fjobs.base.activities.BaseAppActivity;

public class SplashActivity extends BaseAppActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startAuThenScreen();
    }

    @Override
    public int getContentFrame() {
        return 0;
    }

    private void startAuThenScreen() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

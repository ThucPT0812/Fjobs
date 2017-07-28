package vn.fjobs.app.splash;

import android.os.Bundle;

import project.fjobs.R;
import vn.fjobs.base.activities.BaseAppActivity;

public class SplashActivity extends BaseAppActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public int getContentFrame() {
        return 0;
    }
}

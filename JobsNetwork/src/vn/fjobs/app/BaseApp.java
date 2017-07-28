package vn.fjobs.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import vn.fjobs.app.common.api.OkHttpApiImpl;
import vn.fjobs.base.api.Api;

public class BaseApp extends Application {
    private static BaseApp baseApp;
    private static boolean applicationVisible = false;
    private Api api;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
        api = new OkHttpApiImpl();
    }

    public void activityResume() {
        applicationVisible = true;
    }

    public void activityPause() {
        applicationVisible = false;
    }

    public static boolean isApplicationVisibile() {
        return applicationVisible;
    }

    public static BaseApp get() {
        return baseApp;
    }

    public Api getApi() {
        return api;
    }
}

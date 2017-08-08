package vn.fjobs.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;

import vn.fjobs.app.common.api.OkHttpApiImpl;
import vn.fjobs.base.api.Api;

public class BaseApp extends Application {
    private static BaseApp baseApp;
    private static boolean applicationVisible = false;
    private Api api;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "project.fjobs",  // replace with your unique package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
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

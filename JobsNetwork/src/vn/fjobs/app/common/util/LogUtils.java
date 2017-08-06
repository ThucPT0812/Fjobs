package vn.fjobs.app.common.util;

import android.util.Log;

import vn.fjobs.app.Config;

public class LogUtils {

    public static void d(String tag, String msg) {
        if (Config.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}

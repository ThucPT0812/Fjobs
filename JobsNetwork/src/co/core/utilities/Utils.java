package co.core.utilities;

import android.os.Looper;

public class Utils {

    public static void ensureOnMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper())
            throw new IllegalStateException(
                    "This method must be called from the UI thread.");
    }
}

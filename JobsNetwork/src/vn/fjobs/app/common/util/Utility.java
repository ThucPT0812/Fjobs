package vn.fjobs.app.common.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.fjobs.app.BaseApp;

public class Utility {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern emailPatten = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        Matcher matcher;
        matcher = emailPatten.matcher(email);
        return matcher.matches();
    }

    public static void hideSoftKeyboard(Context context) {
        Context con = BaseApp.get();
        if (con == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) con
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = ((Activity) context).getCurrentFocus();
        if (view == null) {
            return;
        }
        IBinder iBinder = view.getWindowToken();
        if (iBinder != null)
            inputMethodManager.hideSoftInputFromWindow(iBinder, 0);
    }

    public static void hideKeyboard(final Activity activity, View view) {
        final Context mContext = activity;
        if (activity == null) {
            return;
        }

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText) && !(view instanceof Button)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(mContext);
                    return false;
                }
            });
        }

        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            int size = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < size; i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboard(activity, innerView);
            }
        }
    }

    public static String encryptPassword(String unencryptedPassword) {
        String encryptedPassword = unencryptedPassword;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = unencryptedPassword.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();
            for (byte b : bytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            encryptedPassword = stringBuilder.toString();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            stringBuilder.delete(0, stringBuilder.length());
        }

        return encryptedPassword;
    }
}

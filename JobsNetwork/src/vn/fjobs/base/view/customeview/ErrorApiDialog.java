package vn.fjobs.base.view.customeview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import project.fjobs.R;
import vn.fjobs.app.common.connection.Response;
import vn.fjobs.app.common.util.ErrorString;
import vn.fjobs.app.common.util.LogUtils;

public class ErrorApiDialog {

    private static final String TAG = "AlertDialog";
    private static android.app.AlertDialog mDialog;

    public static void showAlert(final Activity activity, int title, int code) {
        showAlert(activity, title, code, null, true);
    }

    public static void showAlert(Activity activity, String title, String message) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.common_ok, null);
        builder.show();
    }

    private synchronized static void showAlert(final Activity activity,
                                              int title, final int code,
                                              final DialogInterface.OnClickListener onClickListener,
                                              boolean cancelAble) {
        if (activity == null) {
            LogUtils.w(TAG, "Activity is null");
            return;
        }
        if (mDialog == null || !mDialog.isShowing()) {
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            int message ;
            message = ErrorString.getDescriptionOfErrorCode(code);
            if (message == R.string.alert)
                return;
            builder.setCancelable(cancelAble);
            if (code == Response.SERVER_OUT_OF_DATE_API) {
                builder.setCancelable(false);
            }
            if (title != 0){
                builder.setTitle(title);
            }
            builder.setMessage(message);
            builder.setPositiveButton(R.string.common_yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            mDialog = builder.create();
        } else {
            return;
        }
        if(!activity.isFinishing())
            mDialog.show();
    }
}

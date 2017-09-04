package co.core.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import co.core.dialog.NDialogFragment;
import co.core.dialog.OnActionInDialogListener;
import co.core.fragments.OnFragmentResultListener;

/**
 * Created by freesky1102 on 8/28/16.
 * <pre>
 * This class is used to notify events from
 *
 * DialogFragment -> Fragment host
 * Fragment -> target Fragment
 * </pre>
 */
public class NotifyUtil {

    private NotifyUtil() {

    }

    public static void notifyAction(boolean isDismiss, NDialogFragment dialog, Intent intent, int requestCode, int action) {
        if (dialog == null) return;
        if (isDismiss) dialog.dismiss();

        Fragment responseFragment = dialog.getParentFragment();
        if (responseFragment != null && responseFragment instanceof OnActionInDialogListener) {
            ((OnActionInDialogListener) responseFragment).onDialogResult(requestCode, action, intent);
            return;
        }

        Activity activity = dialog.getActivity();
        if (activity != null && activity instanceof OnActionInDialogListener) {
            ((OnActionInDialogListener) activity).onDialogResult(requestCode, action, intent);
        }
    }

    public static void notifyFragmentAction(Fragment fragment, int action, Intent intent) {
        if (fragment == null) return;

        Fragment sourceFragment = fragment.getTargetFragment();
        if (sourceFragment == null || !(sourceFragment instanceof OnFragmentResultListener)) return;

        ((OnFragmentResultListener) sourceFragment).onFragmentResult(fragment.getTargetRequestCode(), action, intent);
    }
}

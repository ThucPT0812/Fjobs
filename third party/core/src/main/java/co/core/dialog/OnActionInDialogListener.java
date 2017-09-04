package co.core.dialog;

import android.content.Intent;

/**
 * Created by freesky1102 on 6/29/16.
 */
public interface OnActionInDialogListener {
    /**
     * @param requestCode the request code
     * @param action      action from dialog
     * @param extraData   the extra data that will be passed to the source fragment
     */
    void onDialogResult(int requestCode, int action, Intent extraData);
}

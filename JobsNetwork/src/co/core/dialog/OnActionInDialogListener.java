package co.core.dialog;

import android.content.Intent;

/**
 * Created by TrungThuc on 7/19/2017.
 */

public interface OnActionInDialogListener {

    /**
     * @param requestCode the request code
     * @param action      action from dialog
     * @param extraData   the extra data that will be passed to the source fragment
     */
    void onDialogResult(int requestCode, int action, Intent extraData);
}

package co.core.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import co.core.toolbox.NotifyUtil;

/**
 * Created by freesky1102 on 8/28/16.
 */
public abstract class NDialogFragment extends DialogFragment {

    public static final int ACTION_CANCEL = -1;
    private static final String EXTRA_REQUEST_CODE = "extra_request_code";

    protected int mRequestCode;

    public static Bundle makeBundle(int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_REQUEST_CODE, requestCode);
        return bundle;
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            getDataFrom(savedInstanceState);
        } else {
            Bundle bundle = getArguments();
            if (bundle != null)
                getDataFrom(bundle);
        }
    }

    @CallSuper
    protected void getDataFrom(@NonNull Bundle bundle) {
        mRequestCode = bundle.getInt(EXTRA_REQUEST_CODE);
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_REQUEST_CODE, mRequestCode);
    }

    @Override
    @CallSuper
    public void onCancel(DialogInterface dialog) {
        NotifyUtil.notifyAction(false, this, null, mRequestCode, ACTION_CANCEL);
    }
}

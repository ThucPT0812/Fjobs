package vn.fjobs.base.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import co.core.fragments.NFragment;
import project.fjobs.R;
import vn.fjobs.app.common.dialog.LoadingDialogFragment;
import vn.fjobs.base.activities.menu.CustomSlidingMenu;
import vn.fjobs.base.api.Api;
import vn.fjobs.base.fragments.actionbar.AppActionbar;

public abstract class BaseAppFragment extends NFragment {

    protected Api api;
    protected AppActionbar actionbar;
    protected CustomSlidingMenu slidingMenu;

    public static final String DIALOG_TAG = "dialog_tag";
    public static final String LOADING_TAG = "loading_tag";

    private Handler handler;

    private ProgressDialog progressDialog;

    public BaseAppFragment() {
        setArguments(new Bundle());
    }

    @Override
    protected boolean isHasActionbar() {
        return true;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CustomSlidingMenu) {
            slidingMenu = (CustomSlidingMenu) context;
        }

        if (pageFragmentHost != null && pageFragmentHost instanceof AppFragmentHost) {
            api = ((AppFragmentHost) pageFragmentHost).getDfeApi();
        }
    }

    /**
     * Shouldn't override this function...Use {@link #getLayoutRes()}
     */
    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (!isHasActionbar()) {
            int layoutRes = getLayoutRes();
            if (layoutRes != 0) {
                return inflater.inflate(layoutRes, container, false);
            } else return null;
        } else {
            LinearLayout frame = (LinearLayout) inflater.inflate(R.layout.fragment_frame_has_actionbar, container, false);
            int layoutRes = getLayoutRes();
            if (layoutRes != 0) {
                View contentView = inflater.inflate(layoutRes, container, false);
                frame.addView(contentView);
            }

            actionbar = (AppActionbar) frame.findViewById(R.id.actionbar);

            return frame;
        }
    }

    @Override
    @CallSuper
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (actionbar != null) {
            actionbar.initialize(navigationManager, this);
        }
    }

    protected void showLoading() {
        Fragment loading = getChildFragmentManager().findFragmentByTag(LOADING_TAG);
        if (loading != null) return;

        new LoadingDialogFragment().show(getChildFragmentManager(), LOADING_TAG);
    }

    protected void hideLoading() {

        if (handler == null) handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Fragment loading = getChildFragmentManager().findFragmentByTag(LOADING_TAG);
                if (loading == null) return;

                ((DialogFragment) loading).dismissAllowingStateLoss();
            }
        }, 500);
    }

    protected final void showDialog(DialogFragment dialog) {
        if (dialog == null) return;

        dialog.show(getChildFragmentManager(), DIALOG_TAG);
    }

    protected void showProgressLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading..");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void dismissProgressLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public CustomSlidingMenu getSlidingMenu() {
        return slidingMenu;
    }

    public AppActionbar getActionbar() {
        return actionbar;
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}

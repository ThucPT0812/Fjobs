package co.core.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.core.NFragmentHost;
import co.core.dialog.OnActionInDialogListener;

/**
 * This is base fragment. <br>
 * It contains some default attributes: Context, Api, ImageLoader,
 * NavigationManager, Actionbar <br>
 */

public abstract class NFragment extends Fragment implements OnActionInDialogListener {

    protected NFragmentHost pageFragmentHost;
    private boolean saveInstanceStateCalled;
    protected NavigationManager navigationManager;

    public NFragment() {
        setArguments(new Bundle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NFragmentHost) {
            pageFragmentHost = (NFragmentHost) context;
            navigationManager = pageFragmentHost.getNavigationManager();
        }
    }

    @Override
    @CallSuper
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        saveInstanceStateCalled = false;
    }

    protected boolean isHasActionbar() {
        return true;
    }

    @LayoutRes
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveInstanceStateCalled = false;
    }

    /**
     * Shouldn't override this function...Use {@link #getLayoutRes()}
     */
    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        saveInstanceStateCalled = false;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        saveInstanceStateCalled = false;
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        saveInstanceStateCalled = false;
    }

    /**
     * Method check state of fragment. Can not change state of fragment (like:
     * navigate in fragment, change layout...)
     *
     * @return true Valid for change state, otherwise not valid
     */
    public final boolean canChangeFragmentManagerState() {
        FragmentActivity activity = getActivity();
        return !(saveInstanceStateCalled || activity == null || activity.isFinishing());
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceStateCalled = true;
    }

    @Override
    public void onDialogResult(int requestCode, int action, Intent extraData) {
        //TODO Implement the default action listener from dialog
    }
}
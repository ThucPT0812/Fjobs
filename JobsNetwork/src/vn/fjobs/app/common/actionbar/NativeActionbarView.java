package vn.fjobs.app.common.actionbar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import co.core.fragments.NavigationManager;
import vn.fjobs.base.fragments.actionbar.ActionBarControl;
import vn.fjobs.base.fragments.actionbar.ActionBarController;
import vn.fjobs.base.fragments.actionbar.AppActionbar;

public class NativeActionbarView extends RelativeLayout implements AppActionbar, FragmentManager.OnBackStackChangedListener {
    private NavigationManager navigationManager;
    private int mCurrentResId;
    private ActionBarController barController;

    public NativeActionbarView(Context context) {
        super(context);
    }

    public NativeActionbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NativeActionbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initialize(NavigationManager navigationManager, Fragment fragment) {
        if(navigationManager == null) return;
        this.navigationManager = navigationManager;
        this.navigationManager.addOnBackStackChangedListener(this);
        syncController(fragment);
        syncActionBar(fragment);
    }

    private void syncController(Fragment fragment) {
        if (fragment instanceof ActionBarControl) {
            barController = ((ActionBarControl) fragment).getActionBarControl();
        }
    }

    @Override
    public void syncActionBar(Fragment activePage) {
        if (activePage == null) return;
        if (barController == null) return;
        int newResId = barController.findResourceIdForActionbar(activePage);
        if (newResId != mCurrentResId) {
            mCurrentResId = newResId;
            removeAllChildViews();
            inflate(getContext(), mCurrentResId, this);
            barController.findChildViews(this);
            barController.setupChildViews();
        }
        barController.syncChildView(activePage);

    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public void show() {
        setVisibility(View.VISIBLE);
    }

    protected void removeAllChildViews() {
        removeAllViews();
    }

    @Override
    public void updateTitle(String title) {
        if (barController != null)
            barController.updateTitle(title);
    }

    @Override
    public void displayUnreadChatMessage(int notificationNum) {
        if (barController != null)
            barController.displayUnreadChatMessage(notificationNum);
    }

    @Override
    public void displayUnreadNotification(int notificationNum) {
        if (barController != null)
            barController.displayUnreadNotification(notificationNum);
    }

    @Override
    public void onBackStackChanged() {
        if (barController != null) {
            barController.updateStackChildViews();
        }
    }

    @Override
    public View getActionbarLayout() {
        return this;
    }
}

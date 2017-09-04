package vn.fjobs.app.common.actionbar.controller;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import co.core.fragments.NavigationManager;
import project.fjobs.R;
import vn.fjobs.base.activities.menu.CustomSlidingMenu;
import vn.fjobs.base.fragments.BaseAppFragment;
import vn.fjobs.base.fragments.actionbar.ActionBarController;
import vn.fjobs.base.fragments.actionbar.ActionbarLeftHandler;

public class ActionbarForTopController implements ActionBarController {
    private NavigationManager mNavigationManager;
    private ImageView actionLeft;
    private View actionRight;

    public ActionbarForTopController(NavigationManager navigationManager) {
        this.mNavigationManager = navigationManager;
    }

    @Override
    public int findResourceIdForActionbar(Fragment fragment) {
        return R.layout.actionbar_for_top;
    }

    @Override
    public void findChildViews(View view) {
        actionLeft = (ImageView) view.findViewById(R.id.actionbar_left);
        actionRight = view.findViewById(R.id.actionbar_right);
    }

    @Override
    public void setupChildViews() {
        setUpLeft();
    }

    private void setUpLeft() {
        if (actionLeft == null) return;
        if (mNavigationManager.isBackStackEmpty()) {
            actionLeft.setImageResource(R.drawable.nav_menu);
        } else {
            actionLeft.setImageResource(R.drawable.ic_back);
        }

        actionLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = mNavigationManager.getActivePage();
                // Allow the current fragment controls action back
                if (fragment != null && fragment instanceof ActionbarLeftHandler) {
                    if (((ActionbarLeftHandler) fragment).onLeftHandled()) {
                        return;
                    }
                }
                // Normal show slide menu
                if (!mNavigationManager.goBack() && fragment instanceof BaseAppFragment) {
                    CustomSlidingMenu slidingMenu = ((BaseAppFragment) fragment).getSlidingMenu();
                    if (slidingMenu != null) {
                        slidingMenu.toggle();
                    }
                    else mNavigationManager.finishActivity();
                }
            }
        });
    }

    @Override
    public void updateStackChildViews() {
        if (actionLeft != null) {
            if (mNavigationManager.isBackStackEmpty()) {
                actionLeft.setImageResource(R.drawable.nav_menu);
            } else {
                actionLeft.setImageResource(R.drawable.ic_back);
            }
        }
    }

    @Override
    public void syncChildView(Fragment activePage) {

    }

    @Override
    public void updateTitle(String title) {

    }

    @Override
    public void displayUnreadChatMessage(int notificationNum) {

    }

    @Override
    public void displayUnreadNotification(int notificationNum) {

    }
}

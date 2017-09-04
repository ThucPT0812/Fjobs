package vn.fjobs.app.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import co.core.imageloader.NImageLoader;
import project.fjobs.R;
import vn.fjobs.app.common.util.Utility;
import vn.fjobs.app.main.menus.MainMenuLeft;
import vn.fjobs.app.top.TopFragment;
import vn.fjobs.base.activities.BaseSlidingActivity;
import vn.fjobs.base.fragments.actionbar.ActionbarLeftHandler;
import vn.fjobs.base.view.customeview.CustomConfirmDialog;

public class MainActivity extends BaseSlidingActivity {

    // Menu left controller
    private MainMenuLeft mainMenuLeftController;
    private ProgressDialog dialog;
    public boolean isBackPressed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting view
        initView();

        if (navigationManager.getActivePage() == null) {
            navigationManager.replacePage(new TopFragment(), false);
        }
    }

    private void initView(){
        // set layout for center
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.waiting));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        // Set keyboard controller
        setKeyboardController();
    }

    private void setKeyboardController(){
        View menu_left = findViewById(R.id.menu_left_layout);
        View center = findViewById(R.id.activity_main_content);

        Utility.hideKeyboard(this, menu_left);
        Utility.hideKeyboard(this, center);
    }

    @Override
    public void setTouchMode(int mode) {
        getSlidingMenu().setTouchModeAbove(mode);
    }

    @Override
    public void initSlidingMenu() {
//         set layout for menu left
        setBehindContentView(R.layout.layout_menu_left);

        LinearLayout menuLeftRoot = (LinearLayout) findViewById(R.id.menu_left_layout);
        mainMenuLeftController = new MainMenuLeft(this, menuLeftRoot, navigationManager);

        setUpSlidingMenu();
    }

    private void setUpSlidingMenu(){
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setShadowWidthRes(R.dimen.activity_main_shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.activity_main_shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.activity_main_slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);

    }

    @Override
    public int getContentFrame() {
        return R.id.activity_main_content;
    }

    @Override
    public NImageLoader getImageLoader() {
        return null;
    }

    @Override
    public void onBackPressed() {
        Fragment activePage = navigationManager.getActivePage();
        if (activePage instanceof ActionbarLeftHandler) {
            if (((ActionbarLeftHandler) activePage).onLeftHandled())
                return;
        }

        if (!navigationManager.goBack()) {
            CustomConfirmDialog mDialog = new CustomConfirmDialog(this, null,
                    getString(R.string.message_end_app), true);
            mDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    getString(R.string.common_yes),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isBackPressed = true;
                            MainActivity.super.onBackPressed();
                        }
                    });
            mDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    getString(R.string.common_no),
                    (DialogInterface.OnClickListener) null);

            mDialog.show();
        }
    }
}

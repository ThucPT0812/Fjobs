package vn.fjobs.base.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityHelper;

import vn.fjobs.base.activities.menu.CustomSlidingMenu;
import vn.fjobs.base.fragments.AppFragmentHost;

public abstract class BaseSlidingActivity extends BaseAppActivity implements AppFragmentHost, CustomSlidingMenu {

    private SlidingActivityHelper mHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHelper = new SlidingActivityHelper(this);
        mHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mHelper.onPostCreate(savedInstanceState);
    }

    @Override
    public View findViewById(@IdRes int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mHelper.findViewById(id);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mHelper.onSaveInstanceState(outState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mHelper.registerAboveContentView(getLayoutInflater().inflate(layoutResID, null),
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initSlidingMenu();
    }

    public abstract void initSlidingMenu();

    @Override
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mHelper.registerAboveContentView(view, params);
        initSlidingMenu();
    }

    public void setBehindContentView(int id) {
        setBehindContentView(getLayoutInflater().inflate(id, null));
    }

    public void setBehindContentView(View v) {
        setBehindContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setBehindContentView(View v, ViewGroup.LayoutParams params) {
        mHelper.setBehindContentView(v, params);
    }

    public SlidingMenu getSlidingMenu() {
        return mHelper.getSlidingMenu();
    }

    @Override
    public void showContent() {
        if (mHelper != null) {
            mHelper.showContent();
        }
    }

    @Override
    public void show() {
        if (mHelper != null) {
            mHelper.getSlidingMenu().showMenu();
        }
    }

    @Override
    public void hide() {
        if (mHelper != null) {
            mHelper.getSlidingMenu().showContent();
        }
    }

    @Override
    public void showSecondary() {
        if (mHelper != null) {
            mHelper.getSlidingMenu().showSecondaryMenu();
        }
    }

    @Override
    public void hideSecondary() {
        if (mHelper != null) {
            mHelper.getSlidingMenu().showContent();
        }
    }

    @Override
    public void toggle() {
        if (mHelper != null) {
            mHelper.getSlidingMenu().toggle();
        }
    }

    @Override
    public void setEnableSliding(boolean enable) {
        if (mHelper != null) {
            mHelper.getSlidingMenu().setSlidingEnabled(enable);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean b = mHelper.onKeyUp(keyCode, event);
        if (b) return b;
        return super.onKeyUp(keyCode, event);
    }
}

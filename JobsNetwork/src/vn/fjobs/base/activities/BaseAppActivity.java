package vn.fjobs.base.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

import co.core.activities.NActivity;
import co.core.fragments.NavigationManager;
import project.fjobs.R;
import vn.fjobs.app.BaseApp;
import vn.fjobs.app.Constant;
import vn.fjobs.base.api.Api;
import vn.fjobs.base.fragments.AppFragmentHost;
import vn.fjobs.base.fragments.toolbox.AppNavigationManager;

public abstract class BaseAppActivity extends NActivity implements AppFragmentHost {

    protected NavigationManager navigationManager;
    protected Api api;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationManager = new AppNavigationManager(this, getContentFrame());
        api = BaseApp.get().getApi();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_EXIT) {
            if (resultCode == Constant.RESULT_EXIT) {
                setResult(Constant.RESULT_EXIT);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApp.get().activityResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseApp.get().activityPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public NavigationManager getNavigationManager() {
        return navigationManager;
    }

    @Override
    public Api getDfeApi() {
        return api;
    }

    protected void showProgressLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
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

    public abstract int getContentFrame();
}

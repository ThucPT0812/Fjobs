package vn.fjobs.app.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import vn.fjobs.app.BaseApp;

public abstract class BasePrefers {
    protected Context mContext;

    public BasePrefers() {
        mContext = BaseApp.get();
    }

    protected SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(getFileNamePrefers(),Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    protected abstract String getFileNamePrefers();
}
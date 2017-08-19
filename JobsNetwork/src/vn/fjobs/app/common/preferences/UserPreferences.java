package vn.fjobs.app.common.preferences;

import vn.fjobs.app.common.util.LogUtils;

public class UserPreferences extends BasePrefers {

    private static String TAG = "UserPreferences";
    private static UserPreferences preferences = new UserPreferences();
    private static final String FILE_PREFERENCES = "user.preference";
    public static final String KEY_TOKEN = "key.token";

    protected UserPreferences() {
        super();
    }

    public static UserPreferences getInstance() {
        return preferences;
    }

    @Override
    protected String getFileNamePrefers() {
        return FILE_PREFERENCES;
    }

    public void clear() {
        getEditor().clear().commit();
    }

    public boolean saveToken(String token) {
        LogUtils.d(TAG, "Token: " + token);
        return getEditor().putString(KEY_TOKEN, token).commit();
    }

    public String getToken() {
        return getPreferences().getString(KEY_TOKEN, "");
    }
}

package vn.fjobs.app.common.preferences;

public class AppPreferences {
    private static AppPreferences mAppPreferences = new AppPreferences();

    protected AppPreferences() {
        super();
    }

    public static AppPreferences getInstance() {
        return mAppPreferences;
    }
}

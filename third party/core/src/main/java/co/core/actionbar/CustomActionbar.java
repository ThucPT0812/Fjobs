package co.core.actionbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import co.core.fragments.NavigationManager;

public interface CustomActionbar {
    /**
     * Initialize Actionbar for Activity. Should call in
     * {@link Activity#onCreate(Bundle)} after setContentView(...).
     *
     * @param navigationManager
     * @param fragment
     */
    void initialize(NavigationManager navigationManager,
                    Fragment fragment);

    /**
     * Sync state actionbar for specific fragment.
     *
     * @param activePage Fragment that current displayed.
     */
    void syncActionBar(Fragment activePage);

    void hide();

    void show();
}

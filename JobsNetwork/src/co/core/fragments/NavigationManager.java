package co.core.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public interface NavigationManager {
    /**
     * Pop fragment in stack if stack isn't empty.
     *
     * @return true if success, false otherwise. (maybe: stack is empty,
     * activity is in onSaveInstance())
     */
    boolean goBack();

    boolean isBackStackEmpty();

    Fragment getActivePage();

    void finishActivity();

    /**
     * Navigate to new fragment and add add to stack
     * @param fragment new fragment
     */
    void showPage(Fragment fragment);

    /**
     * Navigate to new fragment and add add to stack
     * @param fragment new fragment
     * @param tag fragment tag
     */
    void showPage(Fragment fragment, String tag);

    /**
     * Navigate to new fragment and add to stack
     * @param fragment new fragment
     * @param tag fragment tag
     * @param hasAnimation has show animation when change fragment
     * @param isAddBackStack has add to backStack
     */
    void showPage(Fragment fragment, String tag, boolean hasAnimation, boolean isAddBackStack);

    /**
     * Navigate to new fragment and do not add to stack
     * @param fragment new fragment
     */
    void swapPage(Fragment fragment, boolean hasAnimation);

    /**
     * Navigate to new fragment and clear all old fragment in stack
     * @param fragment new fragment
     */
    void replacePage(Fragment fragment);

    /**
     * Navigate to new fragment and clear all old fragment in stack
     * @param fragment new fragment
     * @param hasAnimation has show animation change fragment
     */
    void replacePage(Fragment fragment, boolean hasAnimation);

    void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener listener);
}

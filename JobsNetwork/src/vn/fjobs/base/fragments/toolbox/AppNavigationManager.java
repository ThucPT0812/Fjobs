package vn.fjobs.base.fragments.toolbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;

import co.core.activities.NActivity;
import co.core.fragments.NavigationManager;
import project.fjobs.R;

public class AppNavigationManager implements NavigationManager{

    protected NActivity mActivity;
    protected final Stack mBackStack = new MainThreadStack();
    protected FragmentManager mFragmentManager;
    protected int mPlaceholder;

    public AppNavigationManager(NActivity activity, int frameId) {
        mActivity = activity;
        mPlaceholder = frameId;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    /**
     * Check whether can navigate or not.
     *
     * @return true if can navigate, false otherwise.
     */
    protected boolean canNavigate() {
        return mActivity != null && mActivity.canChangeFragmentManagerState();
    }

    @Override
    public boolean goBack() {
        if (mActivity == null || !mActivity.canChangeFragmentManagerState()
                || mBackStack.isEmpty() || mFragmentManager.getBackStackEntryCount() == 0){
            return false;
        }
        mBackStack.pop();
        mFragmentManager.popBackStack();
        // Even popped back stack, the fragment which is added without addToBackStack would be showing.
        // So we need to remove it manually
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment currentFrag = mFragmentManager.findFragmentById(mPlaceholder);
        if (currentFrag != null) {
            transaction.remove(currentFrag);
            transaction.commit();
        }

        return true;
    }

    @Override
    public boolean isBackStackEmpty() {
        return (mActivity == null || mBackStack.isEmpty() || mFragmentManager.getBackStackEntryCount() == 0);
    }

    @Override
    public Fragment getActivePage() {
        return mFragmentManager.findFragmentById(mPlaceholder);
    }

    @Override
    public void finishActivity() {
        if (mActivity == null) {
            return;
        }
        mActivity.finish();
    }

    @Override
    public void showPage(Fragment fragment) {
        showPage(fragment, null,true, true);
    }

    @Override
    public void showPage(Fragment fragment, String tag) {
        showPage(fragment, tag,true, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void showPage(Fragment fragment, String tag, boolean hasAnimation, boolean isAddBackStack) {
        if (!canNavigate())
            return;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (hasAnimation)
            transaction.setCustomAnimations(R.anim.fragment_enter,
                    R.anim.fragment_exit, R.anim.fragment_pop_enter,
                    R.anim.fragment_pop_exit);
        transaction.replace(mPlaceholder, fragment, tag);
        NavigationState navigationState = new NavigationState(mPlaceholder);
        if (isAddBackStack) {
            transaction.addToBackStack(navigationState.backStackName);
            mBackStack.push(navigationState);
        }
        transaction.commit();
    }

    @Override
    public void swapPage(Fragment fragment, boolean hasAnimation) {
        showPage(fragment,"",hasAnimation,false);
    }

    @Override
    public void replacePage(Fragment fragment) {
        replacePage(fragment,true);
    }

    @Override
    public void replacePage(Fragment fragment, boolean hasAnimation) {
        if (!canNavigate()){
            return;
        }

        // Clear all back stack.
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = mFragmentManager.getBackStackEntryAt(i).getId();

            mFragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        } /* end of for */

        mBackStack.clear();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (hasAnimation)
            transaction.setCustomAnimations(R.anim.fragment_enter,
                    R.anim.fragment_exit, R.anim.fragment_pop_enter,
                    R.anim.fragment_pop_exit);
        transaction.replace(mPlaceholder, fragment);
        transaction.commit();
    }

    @Override
    public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {
        mFragmentManager.addOnBackStackChangedListener(listener);
    }
}

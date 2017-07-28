package co.core.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

public abstract class NActivity extends AppCompatActivity {

    protected static final String TAG = "FindJobsActivity";
    protected boolean saveInstanceStateCalled;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveInstanceStateCalled = false;
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        saveInstanceStateCalled = false;
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        saveInstanceStateCalled = false;
    }

    public final boolean canChangeFragmentManagerState() {
        return !(saveInstanceStateCalled || isFinishing());
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceStateCalled = true;
    }
}

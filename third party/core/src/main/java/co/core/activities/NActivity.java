package co.core.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

public abstract class NActivity extends AppCompatActivity {
    protected static final String TAG = "NTQActivity";
    protected boolean mSaveInstanceStateCalled;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSaveInstanceStateCalled = false;
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        mSaveInstanceStateCalled = false;
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        mSaveInstanceStateCalled = false;
    }

    public final boolean canChangeFragmentManagerState() {
        return !(mSaveInstanceStateCalled || isFinishing());
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSaveInstanceStateCalled = true;
    }
}

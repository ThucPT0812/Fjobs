package co.core.fragments;

import android.content.Intent;

public interface OnFragmentResultListener {

    void onFragmentResult(int requestCode, int action, Intent extraData);
}

package vn.fjobs.base.fragments;

import co.core.NFragmentHost;
import vn.fjobs.base.api.Api;

public interface AppFragmentHost extends NFragmentHost {

    Api getDfeApi();
}

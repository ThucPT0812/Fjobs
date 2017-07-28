package vn.fjobs.app.common.connection;

import android.content.Context;

import vn.fjobs.app.BaseApp;

public class RequestBuilder {

    private static final String TAG = "RequestBuilder";
    private Context mContext;

    private RequestBuilder() {
        mContext = BaseApp.get();
    }

    public static synchronized RequestBuilder getInstance() {
        return new RequestBuilder();
    }
}

package vn.fjobs.base.api;

import vn.fjobs.app.common.RequestParams;

public interface Api {

    void startRequest(int requestId, RequestParams data, ResponseReceiver responseReceiver);

    void restartRequest(int requestId, RequestParams data, ResponseReceiver responseReceiver);

    void destroyRequest(int requestId);
}

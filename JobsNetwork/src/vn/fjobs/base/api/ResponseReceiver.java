package vn.fjobs.base.api;

import vn.fjobs.app.common.connection.Response;
import vn.fjobs.app.common.connection.ResponseData;

public interface ResponseReceiver {

    void startRequest(int requestId);

    Response parseResponse(int requestId, ResponseData data);

    void receiveResponse(int requestId, Response response);
}

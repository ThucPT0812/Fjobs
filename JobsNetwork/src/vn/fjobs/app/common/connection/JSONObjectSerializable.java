package vn.fjobs.app.common.connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class JSONObjectSerializable extends JSONObject implements Serializable {
    private static final long serialVersionUID = -4513211889078414432L;

    public JSONObjectSerializable(String arg0) throws JSONException {
        super(arg0);
    }
}
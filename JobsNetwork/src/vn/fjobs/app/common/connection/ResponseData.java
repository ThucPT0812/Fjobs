package vn.fjobs.app.common.connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ResponseData implements Serializable {

    private static final long serialVersionUID = 8188554900430771350L;
    protected String text;
    protected JSONObjectSerializable jsonObject;

    private int status;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    public JSONObject getJSONObject(){
        return jsonObject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void makeJSONObject() throws JSONException {
        jsonObject = new JSONObjectSerializable(getText());
    }

    public int getResponseCode() throws JSONException {
        return jsonObject.getInt("code");
    }
}
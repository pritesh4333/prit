package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 23-Aug-16.
 */
public class LoggedInClientResponse implements GreekRequestModel, GreekResponseModel {

    private String gcid;
    private String disconnect_reason;

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getDisconnect_reason() {
        return disconnect_reason;
    }

    public void setDisconnect_reason(String disconnect_reason) {
        this.disconnect_reason = disconnect_reason;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("disconnect_reason", this.disconnect_reason);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.disconnect_reason = jo.optString("disconnect_reason");

        return this;
    }
}


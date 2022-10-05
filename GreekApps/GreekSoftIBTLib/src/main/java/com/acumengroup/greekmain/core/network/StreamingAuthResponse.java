package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 01-Sep-16.
 */
public class StreamingAuthResponse implements GreekRequestModel, GreekResponseModel {
    //0 - success
    //1 - failed
    //2 - server force close
    private String error_code;
    private String reconnect;

    public String getReconnect() {
        return reconnect;
    }

    public void setReconnect(String reconnect) {
        this.reconnect = reconnect;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("error_code", this.error_code);
        jo.put("reconnect", this.reconnect);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.error_code = jo.optString("error_code");
        this.reconnect = jo.optString("reconnect");
        return this;
    }
}

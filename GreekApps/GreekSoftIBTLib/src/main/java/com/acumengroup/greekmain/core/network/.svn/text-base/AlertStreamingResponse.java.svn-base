package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 9/30/2016.
 */

public class AlertStreamingResponse implements GreekRequestModel, GreekResponseModel {
    //0 - success
    //1 - failed
    private String gcid;
    private String error_code;


    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
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
        jo.put("gcid", this.gcid);
        jo.put("error_code", this.error_code);

        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.gcid = jo.optString("gcid");
        this.error_code = jo.optString("error_code");

        return this;
    }
}

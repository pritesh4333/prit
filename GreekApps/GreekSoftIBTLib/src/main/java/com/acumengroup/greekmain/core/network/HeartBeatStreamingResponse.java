package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 29-Aug-16.
 */
public class HeartBeatStreamingResponse implements GreekRequestModel, GreekResponseModel {
    //0 - success
    //1 - failed
    private String error_code;
    private String apptype;

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
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
        jo.put("apptype", this.apptype);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.error_code = jo.optString("error_code");
        this.apptype = jo.optString("apptype");
        return this;
    }
}

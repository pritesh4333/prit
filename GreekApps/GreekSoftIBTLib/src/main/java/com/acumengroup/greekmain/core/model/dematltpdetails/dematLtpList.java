package com.acumengroup.greekmain.core.model.dematltpdetails;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 31-Aug-17.
 */

public class dematLtpList implements GreekRequestModel, GreekResponseModel {
    private String Ltp;
    private String token;

    public String getLtp() {
        return Ltp;
    }

    public void setLtp(String ltp) {
        this.Ltp = ltp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("Ltp", this.Ltp);
        jo.put("token", this.token);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.Ltp = jo.optString("Ltp");
        this.token = jo.optString("token");
        return this;
    }
}


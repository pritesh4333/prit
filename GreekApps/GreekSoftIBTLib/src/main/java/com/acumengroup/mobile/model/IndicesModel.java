package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import org.json.JSONException;
import org.json.JSONObject;

public class IndicesModel implements GreekRequestModel, GreekResponseModel {

    String name, token, p_change, last, change;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getP_change() {
        return p_change;
    }

    public void setP_change(String p_change) {
        this.p_change = p_change;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("name", this.name);
        jo.put("token", this.token);
        jo.put("p_change", this.p_change);
        jo.put("last", this.last);
        jo.put("change", this.change);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.name = jo.optString("name").toLowerCase();
        this.token = jo.optString("token");
        this.p_change = jo.optString("p_change");
        this.last = jo.optString("last");
        this.change = jo.optString("change");
        return this;
    }
}

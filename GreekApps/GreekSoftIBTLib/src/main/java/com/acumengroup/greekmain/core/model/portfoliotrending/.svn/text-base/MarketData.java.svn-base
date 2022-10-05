package com.acumengroup.greekmain.core.model.portfoliotrending;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MarketData
        implements GreekRequestModel, GreekResponseModel {
    private String name;
    private String token;
    private String p_change;
    private String last;
    private String change;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getP_change() {
        return this.p_change;
    }

    public void setP_change(String p_change) {
        this.p_change = p_change;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getChange() {
        return this.change;
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
        this.name = jo.optString("name");
        this.token = jo.optString("token");
        this.p_change = jo.optString("p_change");
        this.last = jo.optString("last");
        this.change = jo.optString("change");
        return this;
    }
}



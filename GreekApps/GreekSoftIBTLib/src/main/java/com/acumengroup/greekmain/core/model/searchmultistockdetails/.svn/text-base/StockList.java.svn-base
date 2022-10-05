package com.acumengroup.greekmain.core.model.searchmultistockdetails;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class StockList
        implements GreekRequestModel, GreekResponseModel {
    private String assetType;
    private String exchange;
    private String token;

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getExchange() {
        return this.exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("assetType", this.assetType);
        jo.put("exchange", this.exchange);
        jo.put("token", this.token);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.assetType = jo.optString("assetType");
        this.exchange = jo.optString("exchange");
        this.token = jo.optString("token");
        return this;
    }
}



package com.acumengroup.greekmain.core.model.marketsmultiplescrip;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SymbolList
        implements GreekRequestModel, GreekResponseModel {
    private String assetType;
    private String token;

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
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
        jo.put("token", this.token);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.assetType = jo.optString("assetType");
        this.token = jo.optString("token");
        return this;
    }
}



package com.acumengroup.greekmain.core.model.portfolioeditwatchlist;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MFSymbolDetail implements GreekRequestModel, GreekResponseModel {
    private String amcname;
    private String schemecode;
    private String corpisin;
    private String assetType;

    public String getAmcname() {
        return amcname;
    }

    public void setAmcname(String amcname) {
        this.amcname = amcname;
    }

    public String getSchemecode() {
        return schemecode;
    }

    public void setSchemecode(String schemecode) {
        this.schemecode = schemecode;
    }

    public String getCorpisin() {
        return corpisin;
    }

    public void setCorpisin(String corpisin) {
        this.corpisin = corpisin;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("amcname", this.amcname);
        jo.put("schemecode", this.schemecode);
        jo.put("corpisin", this.corpisin);
        jo.put("assetType", this.assetType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.amcname = jo.optString("amcname");
        this.schemecode = jo.optString("schemecode");
        this.corpisin = jo.optString("corpisin");
        this.assetType = jo.optString("assetType");
        return this;
    }
}

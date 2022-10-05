package com.acumengroup.greekmain.core.model.portfoliotrending;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AllowedProduct implements GreekRequestModel, GreekResponseModel {
    private String iProductToken;
    private String cProductName;


    public String getiProductToken() {
        return iProductToken;
    }

    public void setiProductToken(String iProductToken) {
        this.iProductToken = iProductToken;
    }

    public String getcProductName() {
        return cProductName;
    }

    public void setcProductName(String cProductName) {
        this.cProductName = cProductName;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("iProductToken", this.iProductToken);
        jo.put("cProductName", this.cProductName);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.iProductToken = jo.optString("iProductToken");
        this.cProductName = jo.optString("cProductName");
        return this;
    }
}

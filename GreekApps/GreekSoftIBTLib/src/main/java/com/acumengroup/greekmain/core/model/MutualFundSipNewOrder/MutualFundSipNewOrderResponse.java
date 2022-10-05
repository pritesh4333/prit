package com.acumengroup.greekmain.core.model.MutualFundSipNewOrder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MutualFundSipNewOrderResponse implements GreekRequestModel, GreekResponseModel
{
    private String status;
    private String SIP_ID;
    private String STPId;
    private String SWPId;
    private String schemeName;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getSIP_ID() {
        return SIP_ID;
    }

    public void setSIP_ID(String SIP_ID) {
        this.SIP_ID = SIP_ID;
    }

    public String getSTPId() {
        return STPId;
    }

    public void setSTPId(String STPId) {
        this.STPId = STPId;
    }

    public String getSWPId() {
        return SWPId;
    }

    public void setSWPId(String SWPId) {
        this.SWPId = SWPId;
    }

    public JSONObject toJSONObject()
            throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("status", this.status);
        jo.put("SIP_ID", this.SIP_ID);
        jo.put("STPId", this.STPId);
        jo.put("SWPId", this.SWPId);
        jo.put("schemeName", this.schemeName);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException
    {
        this.status = jo.optString("status");
        this.SIP_ID = jo.optString("SIP_ID");
        this.STPId = jo.optString("STPId");
        this.SWPId = jo.optString("SWPId");
        this.schemeName = jo.optString("schemeName");
        return this;
    }
}


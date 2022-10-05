package com.acumengroup.greekmain.core.model.mutualfundmutualfundsendneworder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MutualFundMutualFundSendNewOrderResponse implements GreekRequestModel, GreekResponseModel
{
    private String status;
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

    public JSONObject toJSONObject()
            throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("status", this.status);
        jo.put("schemeName", this.schemeName);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException
    {
        this.status = jo.optString("status");
        this.schemeName = jo.optString("schemeName");
        return this;
    }
}

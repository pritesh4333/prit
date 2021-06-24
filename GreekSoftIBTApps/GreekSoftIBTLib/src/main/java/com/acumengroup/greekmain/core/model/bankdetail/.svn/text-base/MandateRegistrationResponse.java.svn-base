package com.acumengroup.greekmain.core.model.bankdetail;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MandateRegistrationResponse implements GreekRequestModel, GreekResponseModel {


    private String status;
    private String mandateId;

    public String getMandateId() {
        return mandateId;
    }

    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("status", this.status);
        jo.put("mandateId", this.mandateId);
        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.status = jo.optString("status");
        this.mandateId = jo.optString("mandateId");
        return this;
    }
}

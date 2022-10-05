package com.acumengroup.greekmain.core.model.bankdetail;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MandateDetailsResponse implements GreekRequestModel, GreekResponseModel {


    private String status;
    private String mandateId;
    private String amount;
    private String mandateType;
    private String ErrorCode;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMandateType() {
        return mandateType;
    }

    public void setMandateType(String mandateType) {
        this.mandateType = mandateType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("status", this.status);
        jo.put("mandateId", this.mandateId);
        jo.put("mandateType", this.mandateType);
        jo.put("amount", this.amount);
        jo.put("ErrorCode", this.ErrorCode);
        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.status = jo.optString("status");
        this.mandateId = jo.optString("mandateId");
        this.mandateType = jo.optString("mandateType");
        this.amount = jo.optString("amount");
        this.ErrorCode = jo.optString("ErrorCode");
        return this;
    }
}

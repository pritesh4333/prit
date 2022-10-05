package com.acumengroup.greekmain.core.model.bankdetail;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BankDetailResponse implements GreekRequestModel, GreekResponseModel {

    String ErrorCode;
    String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("ErrorCode", this.ErrorCode);
        jo.put("status", this.status);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.ErrorCode = jo.optString("ErrorCode");
        this.status = jo.optString("status");
        return this;
    }
}

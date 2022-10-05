package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sushant.patil on 3/28/2016.
 */
public class SendTranscationPasswordResponse implements GreekRequestModel, GreekResponseModel {
    private String status;
    private String executionCode;
    private String errorCode;
    private String clientCode;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecutionCode() {
        return executionCode;
    }

    public void setExecutionCode(String executionCode) {
        this.executionCode = executionCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("status", this.status);
        jo.put("Executioncode", this.executionCode);
        jo.put("ErrorCode", this.errorCode);
        jo.put("ClientCode", this.clientCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.status = jo.optString("status");
        this.executionCode = jo.optString("Executioncode");
        this.errorCode = jo.optString("ErrorCode");
        this.clientCode = jo.optString("ClientCode");
        return this;
    }
}


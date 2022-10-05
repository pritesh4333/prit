package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 9/19/2016.
 */
public class OTPResponse implements GreekRequestModel, GreekResponseModel {
    private String errorCode;
    private String clientCode;
    private String sessionId;
    private String executionCode;

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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.errorCode);
        jo.put("ClientCode", this.clientCode);
        jo.put("sessionId", this.sessionId);
        jo.put("Executioncode", this.executionCode);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.errorCode = jo.optString("ErrorCode");
        this.clientCode = jo.optString("ClientCode");
        this.sessionId = jo.optString("sessionId");
        this.executionCode = jo.optString("Executioncode");

        return this;
    }
}

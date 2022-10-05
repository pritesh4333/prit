package com.acumengroup.greekmain.core.model.forgotpassword;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordResponse
        implements GreekRequestModel, GreekResponseModel {
    private String ErrorCode;
    private String domainName;
    private String domainPort;
    private String isSecure;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainPort() {
        return domainPort;
    }

    public void setDomainPort(String domainPort) {
        this.domainPort = domainPort;
    }

    public String getIsSecure() {
        return isSecure;
    }

    public void setIsSecure(String isSecure) {
        this.isSecure = isSecure;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.ErrorCode);
        jo.put("domainName", this.domainName);
        jo.put("domainPort", this.domainPort);
        jo.put("isSecure", this.isSecure);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");
        this.domainName = jo.optString("domainName");
        this.domainPort = jo.optString("domainPort");
        this.isSecure = jo.optString("isSecure");
        return this;
    }
}



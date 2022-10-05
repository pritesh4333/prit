package com.acumengroup.greekmain.core.model.changepassword;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordResponse
        implements GreekRequestModel, GreekResponseModel {
    private String errorCode;
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
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.errorCode);
        jo.put("domainName", this.domainName);
        jo.put("domainPort", this.domainPort);
        jo.put("isSecure", this.isSecure);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.errorCode = jo.optString("ErrorCode");
        this.domainName = jo.optString("domainName");
        this.domainPort = jo.optString("domainPort");
        this.isSecure = jo.optString("isSecure");
        return this;
    }
}


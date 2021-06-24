package com.acumengroup.greekmain.core.model.LoginWithOTP;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginWithOTPREsponse  implements GreekRequestModel, GreekResponseModel {
    private String ClientCode;
    private String Executioncode;
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

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getExecutioncode() {
        return Executioncode;
    }

    public void setExecutioncode(String executioncode) {
        Executioncode = executioncode;
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
        jo.put("ClientCode", this.ClientCode);
        jo.put("Executioncode", this.Executioncode);
        jo.put("domainName", this.domainName);
        jo.put("domainPort", this.domainPort);
        jo.put("isSecure", this.isSecure);
        return  jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");
        this.ClientCode = jo.optString("ClientCode");
        this.Executioncode = jo.optString("Executioncode");
        this.domainName = jo.optString("domainName");
        this.domainPort = jo.optString("domainPort");
        this.isSecure = jo.optString("isSecure");
        return this;
    }
}

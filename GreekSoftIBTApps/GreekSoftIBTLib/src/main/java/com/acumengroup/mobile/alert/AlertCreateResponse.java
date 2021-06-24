package com.acumengroup.mobile.alert;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sushant on 9/29/2016.
 */

public class AlertCreateResponse implements GreekRequestModel, GreekResponseModel {
    private String errorCode;
    private String ruleNo;
    private String gtoken;

    public String getGtoken() {
        return gtoken;
    }

    public void setGtoken(String gtoken) {
        this.gtoken = gtoken;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.errorCode);
        jo.put("RuleNo", this.ruleNo);
        jo.put("Gtoken", this.gtoken);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.errorCode = jo.optString("ErrorCode");
        this.ruleNo = jo.optString("RuleNo");
        this.gtoken = jo.optString("Gtoken");
        return this;
    }
}

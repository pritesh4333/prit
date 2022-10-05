package com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AvailableExchange
        implements GreekRequestModel, GreekResponseModel {
    private Boolean nseFlag;
    private Boolean mcxFlag;
    private Boolean bseFlag;

    public Boolean getNseFlag() {
        return this.nseFlag;
    }

    public void setNseFlag(Boolean nseFlag) {
        this.nseFlag = nseFlag;
    }

    public Boolean getMcxFlag() {
        return this.mcxFlag;
    }

    public void setMcxFlag(Boolean mcxFlag) {
        this.mcxFlag = mcxFlag;
    }

    public Boolean getBseFlag() {
        return this.bseFlag;
    }

    public void setBseFlag(Boolean bseFlag) {
        this.bseFlag = bseFlag;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("nseFlag", this.nseFlag);
        jo.put("mcxFlag", this.mcxFlag);
        jo.put("bseFlag", this.bseFlag);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.nseFlag = Boolean.valueOf(jo.optBoolean("nseFlag"));
        this.mcxFlag = Boolean.valueOf(jo.optBoolean("mcxFlag"));
        this.bseFlag = Boolean.valueOf(jo.optBoolean("bseFlag"));
        return this;
    }
}



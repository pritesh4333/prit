package com.acumengroup.greekmain.core.model.StrategyFinder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class StrategyIDList
        implements GreekRequestModel, GreekResponseModel {
    String iStrategyType;
    String iTokenCount;
    String iCallPut;
    Boolean bIsCovered;

    public Boolean getbIsCovered() {
        return bIsCovered;
    }

    public void setbIsCovered(Boolean bIsCovered) {
        this.bIsCovered = bIsCovered;
    }

    public String getiStrategyType() {
        return iStrategyType;
    }

    public void setiStrategyType(String iStrategyType) {
        this.iStrategyType = iStrategyType;
    }


    public String getiCallPut() {
        return iCallPut;
    }

    public void setiCallPut(String iCallPut) {
        this.iCallPut = iCallPut;
    }
    public String getiTokenCount() {
        return iTokenCount;
    }

    public void setiTokenCount(String iTokenCount) {
        this.iTokenCount = iTokenCount;
    }




    public JSONObject toJSONObject()
            throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("iStrategyType", this.iStrategyType);
        jo.put("iTokenCount", this.iTokenCount);
        jo.put("iCallPut",this.iCallPut);
        jo.put("bIsCovered",this.bIsCovered);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException
    {
        this.iStrategyType = jo.optString("iStrategyType");
        this.iTokenCount = jo.optString("iTokenCount");
        this.iCallPut = jo.optString("iCallPut");
        this.bIsCovered = jo.optBoolean("bIsCovered");
        //this.token = jo.optString("token");
        return this;
    }
}

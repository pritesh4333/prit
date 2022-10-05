package com.acumengroup.greekmain.core.model.StrategyFinder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class FilterExpiryList implements GreekRequestModel, GreekResponseModel {
    String lExpDate;

    public String getlExpDate() {
        return lExpDate;
    }

    public void setlExpDate(String lExpDate) {
        this.lExpDate = lExpDate;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("lExpDate", this.lExpDate);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.lExpDate = jo.optString("lExpDate");
        return this;
    }
}

package com.acumengroup.greekmain.core.model.StrategyFinder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class FilterCallPutOptionList implements GreekRequestModel, GreekResponseModel {
    String iCallPutSelection;


    public String getiCallPutSelection() {
        return iCallPutSelection;
    }

    public void setiCallPutSelection(String iCallPutSelection) {
        this.iCallPutSelection = iCallPutSelection;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("iCallPutSelection", this.iCallPutSelection);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.iCallPutSelection = jo.optString("iCallPutSelection");
        return this;
    }
}

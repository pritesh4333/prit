package com.acumengroup.greekmain.core.model.searchequityinformation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 20-Mar-17.
 */

public class InstrumentName implements GreekRequestModel, GreekResponseModel {
    private String market_id;
    private String InstrumentName;

    public String getMarket_id() {
        return market_id;
    }

    public void setMarket_id(String market_id) {
        this.market_id = market_id;
    }

    public String getInstrumentName() {
        return InstrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        InstrumentName = instrumentName;
    }


    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("InstrumentName", this.InstrumentName);
        jo.put("market_id", this.market_id);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.InstrumentName = jo.optString("InstrumentName");
        this.market_id = jo.optString("market_id");

        return this;
    }
}



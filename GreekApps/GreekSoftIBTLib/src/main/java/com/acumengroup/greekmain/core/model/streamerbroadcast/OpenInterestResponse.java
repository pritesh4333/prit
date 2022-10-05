package com.acumengroup.greekmain.core.model.streamerbroadcast;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 18-Jul-16.
 */
public class OpenInterestResponse implements GreekRequestModel, GreekResponseModel {
    private String currentOI;
    private String token;
    private String market_id;

    public String getCurrentOI() {
        return currentOI;
    }

    public void setCurrentOI(String currentOI) {
        this.currentOI = currentOI;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMarket_id() {
        return market_id;
    }

    public void setMarket_id(String market_id) {
        this.market_id = market_id;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("currentOI", this.currentOI);
        jo.put("gtoken", this.token);
        jo.put("market_id", this.market_id);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.currentOI = jo.optString("currentOI");
        this.token = jo.optString("gtoken");
        this.market_id = jo.optString("market_id");
        return this;
    }
}
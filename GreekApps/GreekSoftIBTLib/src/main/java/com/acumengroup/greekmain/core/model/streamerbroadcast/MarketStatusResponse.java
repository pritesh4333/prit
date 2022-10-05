package com.acumengroup.greekmain.core.model.streamerbroadcast;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 14-Jul-16.
 */
public class MarketStatusResponse implements GreekRequestModel, GreekResponseModel {
    private String market_id;
    private String status;
    private String session;

    public String getMarket_id() {
        return market_id;
    }

    public void setMarket_id(String market_id) {
        this.market_id = market_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("market_id", this.market_id);
        jo.put("status", this.status);
        jo.put("session", this.session);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.market_id = jo.optString("market_id");
        this.status = jo.optString("status");
        this.session = jo.optString("session");

        return this;
    }
}

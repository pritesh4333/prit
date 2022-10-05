package com.acumengroup.greekmain.core.model.streamerbroadcast;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Level2
        implements GreekRequestModel, GreekResponseModel {
    private Bid bid;
    private Ask ask;

    public Bid getBid() {
        return this.bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Ask getAsk() {
        return this.ask;
    }

    public void setAsk(Ask ask) {
        this.ask = ask;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        if ((this.bid instanceof GreekRequestModel)) {
            jo.put("bid", this.bid.toJSONObject());
        }
        if ((this.ask instanceof GreekRequestModel)) {
            jo.put("ask", this.ask.toJSONObject());
        }
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("bid")) {
            this.bid = new Bid();
            this.bid.fromJSON(jo.getJSONObject("bid"));
        }
        if (jo.has("ask")) {
            this.ask = new Ask();
            this.ask.fromJSON(jo.getJSONObject("ask"));
        }
        return this;
    }
}



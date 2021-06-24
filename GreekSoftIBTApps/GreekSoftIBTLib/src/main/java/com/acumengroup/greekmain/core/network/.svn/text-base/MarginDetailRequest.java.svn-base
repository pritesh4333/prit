package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 5/24/2016.
 */
public class MarginDetailRequest implements GreekRequestModel, GreekResponseModel {
    private String gcid;
    private String segment;
    private String sessionId;
    private String exchange_type;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getExchange_type() {
        return exchange_type;
    }

    public void setExchange_type(String exchange_type) {
        this.exchange_type = exchange_type;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gcid", this.gcid);
        jo.put("segment", this.segment);
        jo.put("sessionId   ", this.sessionId);
        jo.put("exchange_type", this.exchange_type);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gcid = jo.optString("gcid");
        this.segment = jo.optString("segment");
        this.sessionId    = jo.optString("sessionId");
        this.exchange_type = jo.optString("exchange_type");
        return this;
    }
}
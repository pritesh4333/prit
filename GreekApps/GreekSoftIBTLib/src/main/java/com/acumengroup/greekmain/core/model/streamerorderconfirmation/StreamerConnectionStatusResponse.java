package com.acumengroup.greekmain.core.model.streamerorderconfirmation;

import android.util.Base64;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 01-Dec-17.
 */

public class StreamerConnectionStatusResponse implements GreekRequestModel, GreekResponseModel {

    private String market_id;
    private String status;


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

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("status", this.status);
        jo.put("market_id", this.market_id);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.status = jo.optString("status");
        this.market_id = jo.optString("market_id");
        return this;
    }

    public String decodeBase64(String decodeData) {
        String decodedString = new String(Base64.decode(decodeData, Base64.DEFAULT));
        return decodedString;
    }
}

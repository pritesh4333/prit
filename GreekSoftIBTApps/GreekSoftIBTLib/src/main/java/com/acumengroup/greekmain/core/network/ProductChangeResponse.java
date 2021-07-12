package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TEPANPRAVIN on 19/01/18.
 */

public class ProductChangeResponse implements GreekRequestModel, GreekResponseModel {

    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("Message", this.Message);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.Message = jo.optString("Message");

        return this;
    }

}
package com.acumengroup.greekmain.core.model.streamerorderconfirmation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailsModifiedResponse implements GreekRequestModel, GreekResponseModel {

    private String Message;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
        jo.put("status", this.status);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.Message = jo.optString("Message");
        this.status = jo.optString("status");

        return this;
    }


}
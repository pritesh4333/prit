package com.acumengroup.greekmain.core.model.streamerorderconfirmation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 12-Oct-17.
 */

public class FundPayOutResponse implements GreekRequestModel, GreekResponseModel {

    private String gscid;
    private String gcid;
    private String Message;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("gcid", this.gcid);
        jo.put("Message", this.Message);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gscid = jo.optString("gscid");
        this.gcid = jo.optString("gcid");
        this.Message = jo.optString("Message");

        return this;
    }

}

package com.acumengroup.greekmain.core.model.heartbeat;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 1/18/2016.
 */
public class HeartBeatResponse implements GreekRequestModel, GreekResponseModel {
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.errorCode);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.errorCode = jo.optString("ErrorCode");
        return this;

    }
}

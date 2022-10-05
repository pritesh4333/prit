package com.acumengroup.greekmain.core.model.FundTransfer;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 04-Jul-17.
 */

public class FundTransferResponseDetailsResponse implements GreekRequestModel, GreekResponseModel {

    private String ErrorCode;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.ErrorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.ErrorCode = jo.optString("ErrorCode");
        return this;
    }
}


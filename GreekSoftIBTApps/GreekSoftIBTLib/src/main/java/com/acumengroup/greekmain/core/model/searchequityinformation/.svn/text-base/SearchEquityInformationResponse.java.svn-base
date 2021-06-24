package com.acumengroup.greekmain.core.model.searchequityinformation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchEquityInformationResponse
        implements GreekRequestModel, GreekResponseModel {
    private ScripDetails scripDetails;
    private String ErrorCode;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public ScripDetails getScripDetails() {
        return this.scripDetails;
    }

    public void setScripDetails(ScripDetails scripDetails) {
        this.scripDetails = scripDetails;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.ErrorCode);
        if ((this.scripDetails instanceof GreekRequestModel)) {
            jo.put("scripDetails", this.scripDetails.toJSONObject());

        }
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");
        if (jo.has("scripDetails")) {
            this.scripDetails = new ScripDetails();
            this.scripDetails.fromJSON(jo.getJSONObject("scripDetails"));

        }
        return this;
    }
}



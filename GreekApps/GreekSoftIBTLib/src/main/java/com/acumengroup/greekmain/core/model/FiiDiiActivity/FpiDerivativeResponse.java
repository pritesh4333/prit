package com.acumengroup.greekmain.core.model.FiiDiiActivity;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FpiDerivativeResponse implements GreekRequestModel, GreekResponseModel {
    private String errorCode;
    private List<FpiInvestmentDetails> fpiInvestmentDetails = new ArrayList<>();

    public List<FpiInvestmentDetails> getFpiInvestmentDetails() {
        return fpiInvestmentDetails;
    }

    public void setFpiInvestmentDetails(List<FpiInvestmentDetails> fpiInvestmentDetails) {
        this.fpiInvestmentDetails = fpiInvestmentDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.errorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.fpiInvestmentDetails = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                JSONObject o = (JSONObject) ja1.get(i);
                FpiInvestmentDetails data = new FpiInvestmentDetails();
                data.fromJSON(o);
                this.fpiInvestmentDetails.add(data);

            }
        }
        return this;
    }
}


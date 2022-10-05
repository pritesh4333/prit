package com.acumengroup.greekmain.core.model.portfoliotrending;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class HoldingDataResponse implements GreekRequestModel, GreekResponseModel {

    private String HValue;
    private String CPrice;
    private String PnL;

    public String getHValue() {
        return HValue;
    }

    public void setHValue(String HValue) {
        this.HValue = HValue;
    }

    public String getCPrice() {
        return CPrice;
    }

    public void setCPrice(String CPrice) {
        this.CPrice = CPrice;
    }

    public String getPnL() {
        return PnL;
    }

    public void setPnL(String pnL) {
        PnL = pnL;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("HValue", this.HValue);
        jo.put("CPrice", this.CPrice);
        jo.put("PnL", this.PnL);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.HValue = jo.optString("HValue");
        this.CPrice = jo.optString("CPrice");
        this.PnL = jo.optString("PnL");
        return this;
    }
}


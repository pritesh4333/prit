package com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class NetQtyDetails
        implements GreekRequestModel, GreekResponseModel {
    private String currentDaysNetQty;
    private String boughtForwardNetAvgPrice;
    private String myAvgNetQty;
    private String settlementNetQty;
    private String currentDaysNetAvgPrice;
    private String settlementNetAvgPrice;
    private String boughtForwardNetQty;
    private String myNetAvgPrice;

    public String getCurrentDaysNetQty() {
        return this.currentDaysNetQty;
    }

    public void setCurrentDaysNetQty(String currentDaysNetQty) {
        this.currentDaysNetQty = currentDaysNetQty;
    }

    public String getBoughtForwardNetAvgPrice() {
        return this.boughtForwardNetAvgPrice;
    }

    public void setBoughtForwardNetAvgPrice(String boughtForwardNetAvgPrice) {
        this.boughtForwardNetAvgPrice = boughtForwardNetAvgPrice;
    }

    public String getMyAvgNetQty() {
        return this.myAvgNetQty;
    }

    public void setMyAvgNetQty(String myAvgNetQty) {
        this.myAvgNetQty = myAvgNetQty;
    }

    public String getSettlementNetQty() {
        return this.settlementNetQty;
    }

    public void setSettlementNetQty(String settlementNetQty) {
        this.settlementNetQty = settlementNetQty;
    }

    public String getCurrentDaysNetAvgPrice() {
        return this.currentDaysNetAvgPrice;
    }

    public void setCurrentDaysNetAvgPrice(String currentDaysNetAvgPrice) {
        this.currentDaysNetAvgPrice = currentDaysNetAvgPrice;
    }

    public String getSettlementNetAvgPrice() {
        return this.settlementNetAvgPrice;
    }

    public void setSettlementNetAvgPrice(String settlementNetAvgPrice) {
        this.settlementNetAvgPrice = settlementNetAvgPrice;
    }

    public String getBoughtForwardNetQty() {
        return this.boughtForwardNetQty;
    }

    public void setBoughtForwardNetQty(String boughtForwardNetQty) {
        this.boughtForwardNetQty = boughtForwardNetQty;
    }

    public String getMyNetAvgPrice() {
        return this.myNetAvgPrice;
    }

    public void setMyNetAvgPrice(String myNetAvgPrice) {
        this.myNetAvgPrice = myNetAvgPrice;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("currentDaysNetQty", this.currentDaysNetQty);
        jo.put("boughtForwardNetAvgPrice", this.boughtForwardNetAvgPrice);
        jo.put("myAvgNetQty", this.myAvgNetQty);
        jo.put("settlementNetQty", this.settlementNetQty);
        jo.put("currentDaysNetAvgPrice", this.currentDaysNetAvgPrice);
        jo.put("settlementNetAvgPrice", this.settlementNetAvgPrice);
        jo.put("boughtForwardNetQty", this.boughtForwardNetQty);
        jo.put("myNetAvgPrice", this.myNetAvgPrice);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.currentDaysNetQty = jo.optString("currentDaysNetQty");
        this.boughtForwardNetAvgPrice = jo.optString("boughtForwardNetAvgPrice");
        this.myAvgNetQty = jo.optString("myAvgNetQty");
        this.settlementNetQty = jo.optString("settlementNetQty");
        this.currentDaysNetAvgPrice = jo.optString("currentDaysNetAvgPrice");
        this.settlementNetAvgPrice = jo.optString("settlementNetAvgPrice");
        this.boughtForwardNetQty = jo.optString("boughtForwardNetQty");
        this.myNetAvgPrice = jo.optString("myNetAvgPrice");
        return this;
    }
}



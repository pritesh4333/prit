package com.acumengroup.greekmain.core.model.greekportfolio;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class GainInfo
        implements GreekRequestModel, GreekResponseModel {
    private String overAllNetWorth;
    private String overAllGainPerChange;
    private String overAllGain;
    private String dayGain;
    private String daysGainPerChange;
    private String investmentPrice;
    private String purchaseValue;
    private String currentValue;
    private String latestValue;
    private String assetType;

    public String getOverAllNetWorth() {
        return overAllNetWorth;
    }

    public void setOverAllNetWorth(String overAllNetWorth) {
        this.overAllNetWorth = overAllNetWorth;
    }

    public String getOverAllGainPerChange() {
        return overAllGainPerChange;
    }

    public void setOverAllGainPerChange(String overAllGainPerChange) {
        this.overAllGainPerChange = overAllGainPerChange;
    }

    public String getOverAllGain() {
        return overAllGain;
    }

    public void setOverAllGain(String overAllGain) {
        this.overAllGain = overAllGain;
    }

    public String getDayGain() {
        return dayGain;
    }

    public void setDayGain(String dayGain) {
        this.dayGain = dayGain;
    }

    public String getDaysGainPerChange() {
        return daysGainPerChange;
    }

    public void setDaysGainPerChange(String daysGainPerChange) {
        this.daysGainPerChange = daysGainPerChange;
    }

    public String getInvestmentPrice() {
        return investmentPrice;
    }

    public void setInvestmentPrice(String investmentPrice) {
        this.investmentPrice = investmentPrice;
    }

    public String getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(String purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getLatestValue() {
        return latestValue;
    }

    public void setLatestValue(String latestValue) {
        this.latestValue = latestValue;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("overAllNetWorth", this.overAllNetWorth);
        jo.put("overAllGainPerChange", this.overAllGainPerChange);
        jo.put("overAllGain", this.overAllGain);
        jo.put("dayGain", this.dayGain);
        jo.put("daysGainPerChange", this.daysGainPerChange);
        jo.put("investmentPrice", this.investmentPrice);
        jo.put("purchaseValue", this.purchaseValue);
        jo.put("currentValue", this.currentValue);
        jo.put("latestValue", this.latestValue);
        jo.put("assetType", this.assetType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.overAllNetWorth = jo.optString("overAllNetWorth");
        this.overAllGainPerChange = jo.optString("overAllGainPerChange");
        this.overAllGain = jo.optString("overAllGain");
        this.dayGain = jo.optString("dayGain");
        this.daysGainPerChange = jo.optString("daysGainPerChange");
        this.investmentPrice = jo.optString("investmentPrice");
        this.purchaseValue = jo.optString("purchaseValue");
        this.currentValue = jo.optString("currentValue");
        this.latestValue = jo.optString("latestValue");
        this.assetType = jo.optString("assetType");
        return this;
    }
}



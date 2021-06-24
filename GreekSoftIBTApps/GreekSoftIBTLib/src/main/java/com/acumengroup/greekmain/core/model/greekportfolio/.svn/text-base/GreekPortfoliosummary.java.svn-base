package com.acumengroup.greekmain.core.model.greekportfolio;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class GreekPortfoliosummary
        implements GreekRequestModel, GreekResponseModel {
    private String ltp;
    private String latestValue;
    private String purchaseValue;
    private String overAllGainPerChange;
    private String assetType;
    private String investmentPrice;
    private String greekViewCnt;
    private String unit;
    private String currentValue;
    private String daysGain;
    private String daysGainPerChange;
    private String overAllGain;

    public String getLtp() {
        return this.ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getLatestValue() {
        return this.latestValue;
    }

    public void setLatestValue(String latestValue) {
        this.latestValue = latestValue;
    }

    public String getPurchaseValue() {
        return this.purchaseValue;
    }

    public void setPurchaseValue(String purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public String getOverAllGainPerChange() {
        return this.overAllGainPerChange;
    }

    public void setOverAllGainPerChange(String overAllGainPerChange) {
        this.overAllGainPerChange = overAllGainPerChange;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getInvestmentPrice() {
        return this.investmentPrice;
    }

    public void setInvestmentPrice(String investmentPrice) {
        this.investmentPrice = investmentPrice;
    }

    public String getGreekViewCnt() {
        return this.greekViewCnt;
    }

    public void setGreekViewCnt(String greekViewCnt) {
        this.greekViewCnt = greekViewCnt;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getDaysGain() {
        return this.daysGain;
    }

    public void setDaysGain(String daysGain) {
        this.daysGain = daysGain;
    }

    public String getDaysGainPerChange() {
        return this.daysGainPerChange;
    }

    public void setDaysGainPerChange(String daysGainPerChange) {
        this.daysGainPerChange = daysGainPerChange;
    }

    public String getOverAllGain() {
        return this.overAllGain;
    }

    public void setOverAllGain(String overAllGain) {
        this.overAllGain = overAllGain;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ltp", this.ltp);
        jo.put("latestValue", this.latestValue);
        jo.put("purchaseValue", this.purchaseValue);
        jo.put("overAllGainPerChange", this.overAllGainPerChange);
        jo.put("assetType", this.assetType);
        jo.put("investmentPrice", this.investmentPrice);
        jo.put("greekViewCnt", this.greekViewCnt);
        jo.put("unit", this.unit);
        jo.put("currentValue", this.currentValue);
        jo.put("daysGain", this.daysGain);
        jo.put("daysGainPerChange", this.daysGainPerChange);
        jo.put("overAllGain", this.overAllGain);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ltp = jo.optString("ltp");
        this.latestValue = jo.optString("latestValue");
        this.purchaseValue = jo.optString("purchaseValue");
        this.overAllGainPerChange = jo.optString("overAllGainPerChange");
        this.assetType = jo.optString("assetType");
        this.investmentPrice = jo.optString("investmentPrice");
        this.greekViewCnt = jo.optString("greekViewCnt");
        this.unit = jo.optString("unit");
        this.currentValue = jo.optString("currentValue");
        this.daysGain = jo.optString("daysGain");
        this.daysGainPerChange = jo.optString("daysGainPerChange");
        this.overAllGain = jo.optString("overAllGain");
        return this;
    }
}



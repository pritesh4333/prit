package com.acumengroup.greekmain.core.model.tradeorderbook;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LegInfo implements GreekRequestModel, GreekResponseModel {
    private String iGreekCode;
    private String dOrderNumber;
    private String lOurOrderNo;
    private String lOurToken;
    private String iOrderType;
    private String iBuySell;
    private String lDisclosedVol;
    private String lDisclosedVolRemaining;
    private String lTotalVolRemaining;
    private String lVolume;
    private String lVolumeFilledToday;
    private String dPrice;
    private String dTriggerPrice;
    private String iOrderFlags;
    private String cOpenClose;
    private String cCoverUncover;
    private String cGiveupFlag;
    private String iLegNo;
    private String dLTP;
    private String iProductType;
    private String cGreekClientId;
    private String cAccountNumber;
    private String description;
    private String scripName;

    public String getiGreekCode() {
        return iGreekCode;
    }

    public void setiGreekCode(String iGreekCode) {
        this.iGreekCode = iGreekCode;
    }

    public String getdOrderNumber() {
        return dOrderNumber;
    }

    public void setdOrderNumber(String dOrderNumber) {
        this.dOrderNumber = dOrderNumber;
    }

    public String getlOurOrderNo() {
        return lOurOrderNo;
    }

    public void setlOurOrderNo(String lOurOrderNo) {
        this.lOurOrderNo = lOurOrderNo;
    }

    public String getlOurToken() {
        return lOurToken;
    }

    public void setlOurToken(String lOurToken) {
        this.lOurToken = lOurToken;
    }

    public String getiOrderType() {
        return iOrderType;
    }

    public void setiOrderType(String iOrderType) {
        this.iOrderType = iOrderType;
    }

    public String getiBuySell() {
        return iBuySell;
    }

    public void setiBuySell(String iBuySell) {
        this.iBuySell = iBuySell;
    }

    public String getlDisclosedVol() {
        return lDisclosedVol;
    }

    public void setlDisclosedVol(String lDisclosedVol) {
        this.lDisclosedVol = lDisclosedVol;
    }

    public String getlDisclosedVolRemaining() {
        return lDisclosedVolRemaining;
    }

    public void setlDisclosedVolRemaining(String lDisclosedVolRemaining) {
        this.lDisclosedVolRemaining = lDisclosedVolRemaining;
    }

    public String getlTotalVolRemaining() {
        return lTotalVolRemaining;
    }

    public void setlTotalVolRemaining(String lTotalVolRemaining) {
        this.lTotalVolRemaining = lTotalVolRemaining;
    }

    public String getlVolume() {
        return lVolume;
    }

    public void setlVolume(String lVolume) {
        this.lVolume = lVolume;
    }

    public String getlVolumeFilledToday() {
        return lVolumeFilledToday;
    }

    public void setlVolumeFilledToday(String lVolumeFilledToday) {
        this.lVolumeFilledToday = lVolumeFilledToday;
    }

    public String getdPrice() {
        return dPrice;
    }

    public void setdPrice(String dPrice) {
        this.dPrice = dPrice;
    }

    public String getdTriggerPrice() {
        return dTriggerPrice;
    }

    public void setdTriggerPrice(String dTriggerPrice) {
        this.dTriggerPrice = dTriggerPrice;
    }

    public String getiOrderFlags() {
        return iOrderFlags;
    }

    public void setiOrderFlags(String iOrderFlags) {
        this.iOrderFlags = iOrderFlags;
    }

    public String getcOpenClose() {
        return cOpenClose;
    }

    public void setcOpenClose(String cOpenClose) {
        this.cOpenClose = cOpenClose;
    }

    public String getcCoverUncover() {
        return cCoverUncover;
    }

    public void setcCoverUncover(String cCoverUncover) {
        this.cCoverUncover = cCoverUncover;
    }

    public String getcGiveupFlag() {
        return cGiveupFlag;
    }

    public void setcGiveupFlag(String cGiveupFlag) {
        this.cGiveupFlag = cGiveupFlag;
    }

    public String getiLegNo() {
        return iLegNo;
    }

    public void setiLegNo(String iLegNo) {
        this.iLegNo = iLegNo;
    }

    public String getdLTP() {
        return dLTP;
    }

    public void setdLTP(String dLTP) {
        this.dLTP = dLTP;
    }

    public String getiProductType() {
        return iProductType;
    }

    public void setiProductType(String iProductType) {
        this.iProductType = iProductType;
    }

    public String getcGreekClientId() {
        return cGreekClientId;
    }

    public void setcGreekClientId(String cGreekClientId) {
        this.cGreekClientId = cGreekClientId;
    }

    public String getcAccountNumber() {
        return cAccountNumber;
    }

    public void setcAccountNumber(String cAccountNumber) {
        this.cAccountNumber = cAccountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScripName() {
        return scripName;
    }

    public void setScripName(String scripName) {
        this.scripName = scripName;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("scripName", this.scripName);
        jo.put("description", this.description);
        jo.put("cGreekClientId", this.cGreekClientId);
        jo.put("cAccountNumber", this.cAccountNumber);
        jo.put("iProductType", this.iProductType);
        jo.put("iLegNo", this.iLegNo);
        jo.put("dLTP", this.dLTP);
        jo.put("cGiveupFlag", this.cGiveupFlag);
        jo.put("cOpenClose", this.cOpenClose);
        jo.put("cCoverUncover", this.cCoverUncover);
        jo.put("iOrderFlags", this.iOrderFlags);
        jo.put("dTriggerPrice", this.dTriggerPrice);
        jo.put("dPrice", this.dPrice);
        jo.put("lVolume", this.lVolume);
        jo.put("lVolumeFilledToday", this.lVolumeFilledToday);
        jo.put("lTotalVolRemaining", this.lTotalVolRemaining);
        jo.put("lDisclosedVolRemaining", this.lDisclosedVolRemaining);
        jo.put("lDisclosedVol", this.lDisclosedVol);
        jo.put("iBuySell", this.iBuySell);
        jo.put("iOrderType", this.iOrderType);
        jo.put("lOurToken", this.lOurToken);
        jo.put("lOurOrderNo", this.lOurOrderNo);
        jo.put("iGreekCode", this.iGreekCode);
        jo.put("dOrderNumber", this.dOrderNumber);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.scripName = jo.optString("scripName");
        this.description = jo.optString("description");
        this.cAccountNumber = jo.optString("cAccountNumber");
        this.cGreekClientId = jo.optString("cGreekClientId");
        this.iProductType = jo.optString("iProductType");
        this.iLegNo = jo.optString("iLegNo");
        this.dLTP = jo.optString("dLTP");
        this.cGiveupFlag = jo.optString("cGiveupFlag");
        this.cCoverUncover = jo.optString("cCoverUncover");
        this.cOpenClose = jo.optString("cOpenClose");
        this.iOrderFlags = jo.optString("iOrderFlags");
        this.dTriggerPrice = jo.optString("dTriggerPrice");
        this.dPrice = jo.optString("dPrice");
        this.lVolume = jo.optString("lVolume");
        this.lVolumeFilledToday = jo.optString("lVolumeFilledToday");
        this.lTotalVolRemaining = jo.optString("lTotalVolRemaining");
        this.lDisclosedVolRemaining = jo.optString("lDisclosedVolRemaining");
        this.lDisclosedVol = jo.optString("lDisclosedVol");
        this.iBuySell = jo.optString("iBuySell");
        this.iOrderType = jo.optString("iOrderType");
        this.lOurToken = jo.optString("lOurToken");
        this.lOurOrderNo = jo.optString("lOurOrderNo");
        this.iGreekCode = jo.optString("iGreekCode");
        this.dOrderNumber = jo.optString("dOrderNumber");

        return this;
    }
}



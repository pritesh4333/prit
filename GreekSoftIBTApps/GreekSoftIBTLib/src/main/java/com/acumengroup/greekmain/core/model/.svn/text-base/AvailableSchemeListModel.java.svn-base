package com.acumengroup.greekmain.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class AvailableSchemeListModel implements GreekRequestModel, GreekResponseModel {
    private String SchemeName;
    private String SchemeCode;
    private String ISIN;
    private String AMCName;
    private String NAVDate;
    private String MinAddPurchaseAmount;
    private String NAV;
    private String MinPurchaseAmount;
    private String MaxPurchaseAmount;
    private String SchemeType;
    private String MfSchemeCode;
    private String sipISIN;
    private String tradingISIN;
    private String mfClientCode;
    private String bseCode;
    private String bseRTACode;

    public String getBseCode() {
        return bseCode;
    }

    public void setBseCode(String bseCode) {
        this.bseCode = bseCode;
    }

    public String getBseRTACode() {
        return bseRTACode;
    }

    public void setBseRTACode(String bseRTACode) {
        this.bseRTACode = bseRTACode;
    }

    public String getMfClientCode() {
        return mfClientCode;
    }

    public void setMfClientCode(String mfClientCode) {
        this.mfClientCode = mfClientCode;
    }

    public AvailableSchemeListModel() {
    }

    public String getMfSchemeCode() {
        return MfSchemeCode;
    }

    public void setMfSchemeCode(String mfSchemeCode) {
        MfSchemeCode = mfSchemeCode;
    }

    public String getSipISIN() {
        return sipISIN;
    }

    public void setSipISIN(String sipISIN) {
        this.sipISIN = sipISIN;
    }

    public String getTradingISIN() {
        return tradingISIN;
    }

    public void setTradingISIN(String tradingISIN) {
        this.tradingISIN = tradingISIN;
    }

    public String getSchemeName() {
        return this.SchemeName;
    }

    public void setSchemeName(String SchemeName) {
        this.SchemeName = SchemeName;
    }

    public String getSchemeCode() {
        return this.SchemeCode;
    }

    public void setSchemeCode(String SchemeCode) {
        this.SchemeCode = SchemeCode;
    }

    public String getISIN() {
        return this.ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    public String getAMCName() {
        return this.AMCName;
    }

    public void setAMCName(String AMCName) {
        this.AMCName = AMCName;
    }

    public String getNAVDate() {
        return this.NAVDate;
    }

    public void setNAVDate(String NAVDate) {
        this.NAVDate = NAVDate;
    }

    public String getMinAddPurchaseAmount() {
        return this.MinAddPurchaseAmount;
    }

    public void setMinAddPurchaseAmount(String MinAddPurchaseAmount) {
        this.MinAddPurchaseAmount = MinAddPurchaseAmount;
    }

    public String getNAV() {
        return this.NAV;
    }

    public void setNAV(String NAV) {
        this.NAV = NAV;
    }

    public String getMinPurchaseAmount() {
        return this.MinPurchaseAmount;
    }

    public void setMinPurchaseAmount(String MinPurchaseAmount) {
        this.MinPurchaseAmount = MinPurchaseAmount;
    }

    public String getMaxPurchaseAmount() {
        return this.MaxPurchaseAmount;
    }

    public void setMaxPurchaseAmount(String MaxPurchaseAmount) {
        this.MaxPurchaseAmount = MaxPurchaseAmount;
    }

    public String getSchemeType() {
        return this.SchemeType;
    }

    public void setSchemeType(String SchemeType) {
        this.SchemeType = SchemeType;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("SchemeName", this.SchemeName);
        jo.put("SchemeCode", this.SchemeCode);
        jo.put("ISIN", this.ISIN);
        jo.put("AMCName", this.AMCName);
        jo.put("NAVDate", this.NAVDate);
        jo.put("MinAddPurchaseAmount", this.MinAddPurchaseAmount);
        jo.put("NAV", this.NAV);
        jo.put("MinPurchaseAmount", this.MinPurchaseAmount);
        jo.put("MaxPurchaseAmount", this.MaxPurchaseAmount);
        jo.put("SchemeType", this.SchemeType);
        jo.put("corp_schcode", this.MfSchemeCode);
        jo.put("trading_isin", this.tradingISIN);
        jo.put("sip_isin", this.sipISIN);
        jo.put("mfClientCode", this.mfClientCode);
        jo.put("bseCode", this.bseCode);
        jo.put("bseRTACode", this.bseRTACode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.SchemeName = jo.optString("SchemeName");
        this.SchemeCode = jo.optString("SchemeCode");
        this.ISIN = jo.optString("ISIN");
        this.AMCName = jo.optString("AMCName");
        this.NAVDate = jo.optString("NAVDate");
        this.MinAddPurchaseAmount = jo.optString("MinAddPurchaseAmount");
        this.NAV = jo.optString("NAV");
        this.MinPurchaseAmount = jo.optString("MinPurchaseAmount");
        this.MaxPurchaseAmount = jo.optString("MaxPurchaseAmount");
        this.SchemeType = jo.optString("SchemeType");
        this.MfSchemeCode = jo.optString("corp_schcode");
        this.tradingISIN = jo.optString("trading_isin");
        this.sipISIN = jo.optString("sip_isin");
        this.mfClientCode = jo.optString("mfClientCode");
        this.bseCode = jo.optString("bseCode");
        this.bseRTACode = jo.optString("bseRTACode");
        return this;
    }
}

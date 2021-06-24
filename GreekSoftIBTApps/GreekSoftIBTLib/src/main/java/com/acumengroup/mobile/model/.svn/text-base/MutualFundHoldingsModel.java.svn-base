package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MutualFundHoldingsModel implements GreekRequestModel, GreekResponseModel {

    private String AMCName;
    private String ClientCode;
    private String PurchaseRate;
    private String MaxPurAmt;
    private String AvailableUnits;
    private String AllotedUnits;
    private String MinAddPurAmt;
    private String ISIN;
    private String MinPurAmt;
    private String BlockedUnits;
    private String FolioNo;
    private String SchemeCode;
    private String RedemptionQtyMultiplier;
    private String MinRedemptionQty;
    private String NAV;
    private String SchemeName;
    private String sipISIN;
    private String mfSchemeCode;
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

    public String getAMCName() {
        return AMCName;
    }

    public void setAMCName(String AMCName) {
        this.AMCName = AMCName;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getPurchaseRate() {
        return PurchaseRate;
    }

    public void setPurchaseRate(String purchaseRate) {
        PurchaseRate = purchaseRate;
    }

    public String getMaxPurAmt() {
        return MaxPurAmt;
    }

    public void setMaxPurAmt(String maxPurAmt) {
        MaxPurAmt = maxPurAmt;
    }

    public String getAvailableUnits() {
        return AvailableUnits;
    }

    public void setAvailableUnits(String availableUnits) {
        AvailableUnits = availableUnits;
    }

    public String getAllotedUnits() {
        return AllotedUnits;
    }

    public void setAllotedUnits(String allotedUnits) {
        AllotedUnits = allotedUnits;
    }

    public String getMinAddPurAmt() {
        return MinAddPurAmt;
    }

    public void setMinAddPurAmt(String minAddPurAmt) {
        MinAddPurAmt = minAddPurAmt;
    }

    public String getISIN() {
        return ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    public String getMinPurAmt() {
        return MinPurAmt;
    }

    public void setMinPurAmt(String minPurAmt) {
        MinPurAmt = minPurAmt;
    }

    public String getBlockedUnits() {
        return BlockedUnits;
    }

    public void setBlockedUnits(String blockedUnits) {
        BlockedUnits = blockedUnits;
    }

    public String getFolioNo() {
        return FolioNo;
    }

    public void setFolioNo(String folioNo) {
        FolioNo = folioNo;
    }

    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        SchemeCode = schemeCode;
    }

    public String getRedemptionQtyMultiplier() {
        return RedemptionQtyMultiplier;
    }

    public void setRedemptionQtyMultiplier(String redemptionQtyMultiplier) {
        RedemptionQtyMultiplier = redemptionQtyMultiplier;
    }

    public String getMinRedemptionQty() {
        return MinRedemptionQty;
    }

    public void setMinRedemptionQty(String minRedemptionQty) {
        MinRedemptionQty = minRedemptionQty;
    }

    public String getNAV() {
        return NAV;
    }

    public void setNAV(String NAV) {
        this.NAV = NAV;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName) {
        SchemeName = schemeName;
    }

    public String getSipISIN() {
        return sipISIN;
    }

    public void setSipISIN(String sipISIN) {
        this.sipISIN = sipISIN;
    }

    public String getMfSchemeCode() {
        return mfSchemeCode;
    }

    public void setMfSchemeCode(String mfSchemeCode) {
        this.mfSchemeCode = mfSchemeCode;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("MinAddPurAmt", this.MinAddPurAmt);
        jo.put("ClientCode", this.ClientCode);
        jo.put("ISIN", this.ISIN);
        jo.put("SchemeName", this.SchemeName);
        jo.put("SchemeCode", this.SchemeCode);
        jo.put("AvailableUnits", this.AvailableUnits);
        jo.put("BlockedUnits", this.BlockedUnits);
        jo.put("AMCName", this.AMCName);
        jo.put("PurchaseRate", this.PurchaseRate);
        jo.put("FolioNo", this.FolioNo);
        jo.put("RedemptionQtyMultiplier", this.RedemptionQtyMultiplier);
        jo.put("NAV", this.NAV);
        jo.put("AllotedUnits", this.AllotedUnits);
        jo.put("MaxPurAmt", this.MaxPurAmt);
        jo.put("MinPurAmt", this.MinPurAmt);
        jo.put("MinRedemptionQty", this.MinRedemptionQty);
        jo.put("bseRTACode", this.bseRTACode);
        jo.put("bseCode", this.bseCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.MinAddPurAmt = jo.optString("MinAddPurAmt");
        this.SchemeName = jo.optString("SchemeName");
        this.SchemeCode = jo.optString("SchemeCode");
        this.AvailableUnits = jo.optString("AvailableUnits");
        this.BlockedUnits = jo.optString("BlockedUnits");
        this.AMCName = jo.optString("AMCName");
        this.PurchaseRate = jo.optString("PurchaseRate");
        this.FolioNo = jo.optString("FolioNo");
        this.RedemptionQtyMultiplier = jo.optString("RedemptionQtyMultiplier");
        this.NAV = jo.optString("NAV");
        this.AllotedUnits = jo.optString("AllotedUnits");
        this.MaxPurAmt = jo.optString("MaxPurAmt");
        this.MinPurAmt = jo.optString("MinPurAmt");
        this.MinRedemptionQty = jo.optString("MinRedemptionQty");
        this.ISIN = jo.optString("ISIN");
        this.sipISIN = jo.optString("sip_isin");
        this.mfSchemeCode = jo.optString("MF_SCHCODE");
        this.bseRTACode = jo.optString("bseRTACode");
        this.bseCode = jo.optString("bseCode");
        return this;
    }
}

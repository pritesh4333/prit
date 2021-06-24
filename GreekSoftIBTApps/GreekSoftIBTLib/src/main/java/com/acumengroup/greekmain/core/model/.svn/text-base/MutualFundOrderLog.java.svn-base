package com.acumengroup.greekmain.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MutualFundOrderLog implements GreekRequestModel, GreekResponseModel {
    private String SchemeType;
    private Boolean isCancellable;
    private String RefNo;
    private String MinAddPurAmt;
    private String TransactionDate;
    private String MinPurAmt;
    private Boolean isEditable;
    private String FolioNo;
    private String RequestedBy;
    private String PurchaseType;
    private String OrderType;
    private String MinRedemptionQty;
    private String TransactionNo;
    private String MaxPurAmt;
    private String status;
    private String AMCName;
    private String filterKey;
    private String RedemptionQtyMultiplier;
    private String InvestedAmount;
    private String SchemeName;
    private String Units;
    private String SchemeCode;

    public String getSchemeType() {
        return this.SchemeType;
    }

    public void setSchemeType(String SchemeType) {
        this.SchemeType = SchemeType;
    }

    public Boolean getIsCancellable() {
        return this.isCancellable;
    }

    public void setIsCancellable(Boolean isCancellable) {
        this.isCancellable = isCancellable;
    }

    public String getRefNo() {
        return this.RefNo;
    }

    public void setRefNo(String RefNo) {
        this.RefNo = RefNo;
    }

    public String getMinAddPurAmt() {
        return this.MinAddPurAmt;
    }

    public void setMinAddPurAmt(String MinAddPurAmt) {
        this.MinAddPurAmt = MinAddPurAmt;
    }

    public String getTransactionDate() {
        return this.TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getMinPurAmt() {
        return this.MinPurAmt;
    }

    public void setMinPurAmt(String MinPurAmt) {
        this.MinPurAmt = MinPurAmt;
    }

    public Boolean getIsEditable() {
        return this.isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    public String getFolioNo() {
        return this.FolioNo;
    }

    public void setFolioNo(String FolioNo) {
        this.FolioNo = FolioNo;
    }

    public String getRequestedBy() {
        return this.RequestedBy;
    }

    public void setRequestedBy(String RequestedBy) {
        this.RequestedBy = RequestedBy;
    }

    public String getPurchaseType() {
        return this.PurchaseType;
    }

    public void setPurchaseType(String PurchaseType) {
        this.PurchaseType = PurchaseType;
    }

    public String getOrderType() {
        return this.OrderType;
    }

    public void setOrderType(String OrderType) {
        this.OrderType = OrderType;
    }

    public String getMinRedemptionQty() {
        return this.MinRedemptionQty;
    }

    public void setMinRedemptionQty(String MinRedemptionQty) {
        this.MinRedemptionQty = MinRedemptionQty;
    }

    public String getTransactionNo() {
        return this.TransactionNo;
    }

    public void setTransactionNo(String TransactionNo) {
        this.TransactionNo = TransactionNo;
    }

    public String getMaxPurAmt() {
        return this.MaxPurAmt;
    }

    public void setMaxPurAmt(String MaxPurAmt) {
        this.MaxPurAmt = MaxPurAmt;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAMCName() {
        return this.AMCName;
    }

    public void setAMCName(String AMCName) {
        this.AMCName = AMCName;
    }

    public String getFilterKey() {
        return this.filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public String getRedemptionQtyMultiplier() {
        return this.RedemptionQtyMultiplier;
    }

    public void setRedemptionQtyMultiplier(String RedemptionQtyMultiplier) {
        this.RedemptionQtyMultiplier = RedemptionQtyMultiplier;
    }

    public String getInvestedAmount() {
        return this.InvestedAmount;
    }

    public void setInvestedAmount(String InvestedAmount) {
        this.InvestedAmount = InvestedAmount;
    }

    public String getSchemeName() {
        return this.SchemeName;
    }

    public void setSchemeName(String SchemeName) {
        this.SchemeName = SchemeName;
    }

    public String getUnits() {
        return this.Units;
    }

    public void setUnits(String Units) {
        this.Units = Units;
    }

    public String getSchemeCode() {
        return this.SchemeCode;
    }

    public void setSchemeCode(String SchemeCode) {
        this.SchemeCode = SchemeCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("SchemeType", this.SchemeType);
        jo.put("isCancellable", this.isCancellable);
        jo.put("RefNo", this.RefNo);
        jo.put("MinAddPurAmt", this.MinAddPurAmt);
        jo.put("TransactionDate", this.TransactionDate);
        jo.put("MinPurAmt", this.MinPurAmt);
        jo.put("isEditable", this.isEditable);
        jo.put("FolioNo", this.FolioNo);
        jo.put("RequestedBy", this.RequestedBy);
        jo.put("PurchaseType", this.PurchaseType);
        jo.put("OrderType", this.OrderType);
        jo.put("MinRedemptionQty", this.MinRedemptionQty);
        jo.put("TransactionNo", this.TransactionNo);
        jo.put("MaxPurAmt", this.MaxPurAmt);
        jo.put("status", this.status);
        jo.put("AMCName", this.AMCName);
        jo.put("filterKey", this.filterKey);
        jo.put("RedemptionQtyMultiplier", this.RedemptionQtyMultiplier);
        jo.put("InvestedAmount", this.InvestedAmount);
        jo.put("SchemeName", this.SchemeName);
        jo.put("Units", this.Units);
        jo.put("SchemeCode", this.SchemeCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.SchemeType = jo.optString("SchemeType");
        this.isCancellable = Boolean.valueOf(jo.optBoolean("isCancellable"));
        this.RefNo = jo.optString("RefNo");
        this.MinAddPurAmt = jo.optString("MinAddPurAmt");
        this.TransactionDate = jo.optString("TransactionDate");
        this.MinPurAmt = jo.optString("MinPurAmt");
        this.isEditable = Boolean.valueOf(jo.optBoolean("isEditable"));
        this.FolioNo = jo.optString("FolioNo");
        this.RequestedBy = jo.optString("RequestedBy");
        this.PurchaseType = jo.optString("PurchaseType");
        this.OrderType = jo.optString("OrderType");
        this.MinRedemptionQty = jo.optString("MinRedemptionQty");
        this.TransactionNo = jo.optString("TransactionNo");
        this.MaxPurAmt = jo.optString("MaxPurAmt");
        this.status = jo.optString("status");
        this.AMCName = jo.optString("AMCName");
        this.filterKey = jo.optString("filterKey");
        this.RedemptionQtyMultiplier = jo.optString("RedemptionQtyMultiplier");
        this.InvestedAmount = jo.optString("InvestedAmount");
        this.SchemeName = jo.optString("SchemeName");
        this.Units = jo.optString("Units");
        this.SchemeCode = jo.optString("SchemeCode");
        return this;
    }
}

package com.acumengroup.greekmain.core.model.tradeorderbook;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MFOrderBook implements GreekRequestModel, GreekResponseModel
{
    private String schemeName;
    private String amount;
    private String ISIN;
    private String dpTrans;
    private String schemeCode;
    private String orderNo;
    private String orderType;
    private String status;
    private String buySell;
    private String buySellType;

    private String startDate;
    private String endDate;
    private String noOfIntallment;
    private String regNo;
    private String transactionMode;
    private String sipFrequency;
    private String paymentMode;
    private String bseCode;
    private String quantity;
    private String UniqueRefNo;

    public String getUniqueRefNo() {
        return UniqueRefNo;
    }

    public void setUniqueRefNo(String uniqueRefNo) {
        UniqueRefNo = uniqueRefNo;
    }

    public String getBuySellType() {
        return buySellType;
    }

    public void setBuySellType(String buySellType) {
        this.buySellType = buySellType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBuySell() {
        return buySell;
    }

    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getSipFrequency() {
        return sipFrequency;
    }

    public void setSipFrequency(String sipFrequency) {
        this.sipFrequency = sipFrequency;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBseCode() {
        return bseCode;
    }

    public void setBseCode(String bseCode) {
        this.bseCode = bseCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNoOfIntallment() {
        return noOfIntallment;
    }

    public void setNoOfIntallment(String noOfIntallment) {
        this.noOfIntallment = noOfIntallment;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getISIN() {
        return ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    public String getDpTrans() {
        return dpTrans;
    }

    public void setDpTrans(String dpTrans) {
        this.dpTrans = dpTrans;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public JSONObject toJSONObject()
            throws JSONException
    {
        JSONObject jo = new JSONObject();
        jo.put("orderNo", this.orderNo);
        jo.put("orderType", this.orderType);
        jo.put("schemeCode", this.schemeCode);
        jo.put("dpTrans", this.dpTrans);
        jo.put("ISIN", this.ISIN);
        jo.put("amount", this.amount);
        jo.put("schemeName", this.schemeName);
        jo.put("status", this.status);
        jo.put("startDate", this.startDate);
        jo.put("endDate", this.endDate);
        jo.put("noOfIntallment", this.noOfIntallment);
        jo.put("transactionMode", this.transactionMode);
        jo.put("regNo", this.regNo);
        jo.put("sipFrequency", this.sipFrequency);
        jo.put("paymentMode", this.paymentMode);
        jo.put("bseCode", this.bseCode);
        jo.put("quantity", this.quantity);
        jo.put("buySell", this.buySell);
        jo.put("buySellType ", this.buySellType );
        jo.put("UniqueRefNo ", this.UniqueRefNo );


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException
    {
        this.orderNo = jo.optString("orderNo");
        this.orderType = jo.optString("orderType");
        this.schemeCode = jo.optString("schemeCode");
        this.dpTrans = jo.optString("dpTrans");
        this.ISIN = jo.optString("ISIN");
        this.amount = jo.optString("amount");
        this.schemeName = jo.optString("schemeName");
        this.status = jo.optString("status");
        this.startDate = jo.optString("startDate");
        this.endDate = jo.optString("endDate");
        this.noOfIntallment = jo.optString("noOfIntallment");
        this.regNo = jo.optString("regNo");
        this.transactionMode = jo.optString("transactionMode");
        this.sipFrequency = jo.optString("sipFrequency");
        this.paymentMode = jo.optString("paymentMode");
        this.bseCode = jo.optString("bseCode");
        this.quantity = jo.optString("quantity");
        this.buySell = jo.optString("buySell");
        this.buySellType  = jo.optString("buySellType");
        this.UniqueRefNo  = jo.optString("UniqueRefNo");
        return this;
    }
}

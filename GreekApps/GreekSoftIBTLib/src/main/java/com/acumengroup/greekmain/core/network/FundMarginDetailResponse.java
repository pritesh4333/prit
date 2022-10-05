package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 5/24/2016.
 */
public class FundMarginDetailResponse implements GreekRequestModel, GreekResponseModel {

    private String fundUtilized;
    private String realizedM2M;
    private String unRealizedM2M;
    private String AvailFundTransfer;


    private String availCashCredit;
    private String collateralValue;
    private String equitySellCredit;
    private String utilizedFund;
    private String availFund;
    private String payIn;
    private String payOut;
    private String optSellCr;
    private String spanExp;
    private String optBuyPrem;
    private String cashMargin;
    private String utilizedFundBajaj;
    private String collateralVal;
    private String eqSellCredit;


    public String getUtilizedFund() {
        return utilizedFund;
    }

    public void setUtilizedFund(String utilizedFund) {
        this.utilizedFund = utilizedFund;
    }

    public String getAvailFund() {
        return availFund;
    }

    public void setAvailFund(String availFund) {
        this.availFund = availFund;
    }

    public String getPayIn() {
        return payIn;
    }

    public void setPayIn(String payIn) {
        this.payIn = payIn;
    }

    public String getPayOut() {
        return payOut;
    }

    public void setPayOut(String payOut) {
        this.payOut = payOut;
    }

    public String getOptSellCr() {
        return optSellCr;
    }

    public void setOptSellCr(String optSellCr) {
        this.optSellCr = optSellCr;
    }

    public String getAvailFundTransfer() {
        return AvailFundTransfer;
    }

    public void setAvailFundTransfer(String availFundTransfer) {
        AvailFundTransfer = availFundTransfer;
    }

    public String getSpanExp() {
        return spanExp;
    }

    public void setSpanExp(String spanExp) {
        this.spanExp = spanExp;
    }

    public String getOptBuyPrem() {
        return optBuyPrem;
    }

    public void setOptBuyPrem(String optBuyPrem) {
        this.optBuyPrem = optBuyPrem;
    }

    public String getCashMargin() {
        return cashMargin;
    }

    public void setCashMargin(String cashMargin) {
        this.cashMargin = cashMargin;
    }

    public String getUtilizedFundBajaj() {
        return utilizedFundBajaj;
    }

    public void setUtilizedFundBajaj(String utilizedFundBajaj) {
        this.utilizedFundBajaj = utilizedFundBajaj;
    }

    public String getCollateralVal() {
        return collateralVal;
    }

    public void setCollateralVal(String collateralVal) {
        this.collateralVal = collateralVal;
    }

    public String getEqSellCredit() {
        return eqSellCredit;
    }

    public void setEqSellCredit(String eqSellCredit) {
        this.eqSellCredit = eqSellCredit;
    }

    public String getFundUtilized() {
        return fundUtilized;
    }

    public void setFundUtilized(String fundUtilized) {
        this.fundUtilized = fundUtilized;
    }

    public String getRealizedM2M() {
        return realizedM2M;
    }

    public void setRealizedM2M(String realizedM2M) {
        this.realizedM2M = realizedM2M;
    }

    public String getUnRealizedM2M() {
        return unRealizedM2M;
    }

    public String getAvailCashCredit() {
        return availCashCredit;
    }

    public void setAvailCashCredit(String availCashCredit) {
        this.availCashCredit = availCashCredit;
    }

    public String getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(String collateralValue) {
        this.collateralValue = collateralValue;
    }

    public String getEquitySellCredit() {
        return equitySellCredit;
    }

    public void setEquitySellCredit(String equitySellCredit) {
        this.equitySellCredit = equitySellCredit;
    }

    public void setUnRealizedM2M(String unRealizedM2M) {
        this.unRealizedM2M = unRealizedM2M;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("utilizedFund", this.fundUtilized);
        jo.put("realizedM2M", this.realizedM2M);
        jo.put("unrealizedM2M", this.unRealizedM2M);

        jo.put("AvailFund", this.availCashCredit);
        jo.put("collateralVal", this.collateralValue);
        jo.put("eqSellCredit", this.equitySellCredit);

        jo.put("utilizedFund", this.utilizedFund);
        jo.put("availFund", this.availFund);
        jo.put("AvailFund", this.AvailFundTransfer);
        jo.put("payIn", this.payIn);
        jo.put("payOut", this.payOut);
        jo.put("optSellCr", this.optSellCr);
        jo.put("spanExp", this.spanExp);
        jo.put("optBuyPrem", this.optBuyPrem);
        jo.put("cashMargin", this.cashMargin);
        jo.put("utilizedFundBajaj", this.utilizedFundBajaj);
        jo.put("collateralVal", this.collateralVal);
        jo.put("eqSellCredit", this.eqSellCredit);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.fundUtilized = jo.optString("utilizedFund");
        this.realizedM2M = jo.optString("realizedM2M");
        this.unRealizedM2M = jo.optString("unrealizedM2M");

        this.availCashCredit = jo.optString("AvailFund");
        this.collateralValue = jo.optString("collateralVal");
        this.equitySellCredit = jo.optString("eqSellCredit");

        this.utilizedFund = jo.optString("utilizedFund");
        this.availFund = jo.optString("availFund");
        this.AvailFundTransfer = jo.optString("AvailFund");
        this.payIn = jo.optString("payIn");
        this.payOut = jo.optString("payOut");
        this.optSellCr = jo.optString("optSellCr");
        this.spanExp = jo.optString("spanExp");
        this.optBuyPrem = jo.optString("optBuyPrem");
        this.cashMargin = jo.optString("cashMargin");
        this.utilizedFundBajaj = jo.optString("utilizedFundBajaj");
        this.collateralVal = jo.optString("collateralVal");
        this.eqSellCredit = jo.optString("eqSellCredit");

        return this;
    }

}

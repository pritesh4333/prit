package com.acumengroup.mobile.model;


import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LedgerData implements GreekRequestModel, GreekResponseModel {

    String EXCHANGE;
    String VOUCHER_DATE;
    String EFFECTIVE_DATE;
    String VOUCHER_TYPE;
    String VOUCHER_NO;
    String NARRATION;
    String CHEQUE_DDNO;
    String DEBIT;
    String CREDIT;
    String BALANCE;
    String fundRecieved, fundWithdrawn, reversalCredit, reversalDebit, cashDebit,cashCredit, derivativeCredit,
            derivativeDebit, opBal, clsBal, mtfCredit, mtfDebit, mfssCredit, mfssDebit, ipoCredit,
            ipoDebit;

    public String getFundRecieved() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(fundRecieved)));
    }

    public void setFundRecieved(String fundRecieved) {
        this.fundRecieved = fundRecieved;
    }

    public String getFundWithdrawn() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(fundWithdrawn)));

    }

    public void setFundWithdrawn(String fundWithdrawn) {
        this.fundWithdrawn = fundWithdrawn;
    }

    public String getReversalCredit() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(reversalCredit)));
    }

    public void setReversalCredit(String reversalCredit) {
        this.reversalCredit = reversalCredit;
    }

    public String getReversalDebit() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(reversalDebit)));
    }

    public void setReversalDebit(String reversalDebit) {
        this.reversalDebit = reversalDebit;
    }

    public String getCashDebit() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(cashDebit)));

    }

    public void setCashDebit(String cashDebit) {
        this.cashDebit = cashDebit;
    }

    public String getCashCredit() {
        return cashCredit;
    }

    public void setCashCredit(String cashCredit) {
        this.cashCredit = cashCredit;
    }

    public String getDerivativeCredit() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(derivativeCredit)));

    }

    public void setDerivativeCredit(String derivativeCredit) {
        this.derivativeCredit = derivativeCredit;
    }

    public String getDerivativeDebit() {

        return  String.format("%.2f", Double.parseDouble(String.valueOf(derivativeDebit)));
    }

    public void setDerivativeDebit(String derivativeDebit) {
        this.derivativeDebit = derivativeDebit;
    }

    public String getOpBal() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(opBal)));
    }

    public void setOpBal(String opBal) {
        this.opBal = opBal;
    }

    public String getClsBal() {

        return  String.format("%.2f", Double.parseDouble(String.valueOf(clsBal)));
    }

    public void setClsBal(String clsBal) {
        this.clsBal = clsBal;
    }

    public String getMtfCredit() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(mtfCredit)));
    }

    public void setMtfCredit(String mtfCredit) {
        this.mtfCredit = mtfCredit;
    }

    public String getMtfDebit() {

        return  String.format("%.2f", Double.parseDouble(String.valueOf(mtfDebit)));
    }

    public void setMtfDebit(String mtfDebit) {
        this.mtfDebit = mtfDebit;
    }

    public String getMfssCredit() {

        return  String.format("%.2f", Double.parseDouble(String.valueOf(mfssCredit)));
    }

    public void setMfssCredit(String mfssCredit) {
        this.mfssCredit = mfssCredit;
    }

    public String getMfssDebit() {

        return  String.format("%.2f", Double.parseDouble(String.valueOf(mfssDebit)));
    }

    public void setMfssDebit(String mfssDebit) {
        this.mfssDebit = mfssDebit;
    }

    public String getIpoCredit() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(ipoCredit)));
    }

    public void setIpoCredit(String ipoCredit) {
        this.ipoCredit = ipoCredit;
    }

    public String getIpoDebit() {
        return  String.format("%.2f", Double.parseDouble(String.valueOf(ipoDebit)));
    }

    public void setIpoDebit(String ipoDebit) {
        this.ipoDebit = ipoDebit;
    }

    public String getEXCHANGE() {
        return EXCHANGE;
    }

    public void setEXCHANGE(String EXCHANGE) {
        this.EXCHANGE = EXCHANGE;
    }

    public String getVOUCHER_DATE() {
        return VOUCHER_DATE;
    }

    public void setVOUCHER_DATE(String VOUCHER_DATE) {
        this.VOUCHER_DATE = VOUCHER_DATE;
    }

    public String getEFFECTIVE_DATE() {
        return EFFECTIVE_DATE;
    }

    public void setEFFECTIVE_DATE(String EFFECTIVE_DATE) {
        this.EFFECTIVE_DATE = EFFECTIVE_DATE;
    }

    public String getVOUCHER_TYPE() {
        return VOUCHER_TYPE;
    }

    public void setVOUCHER_TYPE(String VOUCHER_TYPE) {
        this.VOUCHER_TYPE = VOUCHER_TYPE;
    }

    public String getVOUCHER_NO() {
        return VOUCHER_NO;
    }

    public void setVOUCHER_NO(String VOUCHER_NO) {
        this.VOUCHER_NO = VOUCHER_NO;
    }

    public String getNARRATION() {
        return NARRATION;
    }

    public void setNARRATION(String NARRATION) {
        this.NARRATION = NARRATION;
    }

    public String getCHEQUE_DDNO() {
        return CHEQUE_DDNO;
    }

    public void setCHEQUE_DDNO(String CHEQUE_DDNO) {
        this.CHEQUE_DDNO = CHEQUE_DDNO;
    }

    public String getDEBIT() {
        return DEBIT;
    }

    public void setDEBIT(String DEBIT) {
        this.DEBIT = DEBIT;
    }

    public String getCREDIT() {
        return CREDIT;
    }

    public void setCREDIT(String CREDIT) {
        this.CREDIT = CREDIT;
    }

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();


        jo.put("EXCHANGE", this.EXCHANGE);
        jo.put("VOUCHER_DATE", this.VOUCHER_DATE);
        jo.put("EFFECTIVE_DATE", this.EFFECTIVE_DATE);
        jo.put("VOUCHER_TYPE", this.VOUCHER_TYPE);
        jo.put("VOUCHER_NO", this.VOUCHER_NO);
        jo.put("NARRATION", this.NARRATION);
        jo.put("CHEQUE_DDNO", this.CHEQUE_DDNO);
        jo.put("DEBIT", this.DEBIT);
        jo.put("CREDIT", this.CREDIT);
        jo.put("BALANCE", this.BALANCE);

        jo.put("fundRecieved", this.fundRecieved);
        jo.put("fundWithdrawn", this.fundWithdrawn);

        jo.put("reversalCredit", this.reversalCredit);
        jo.put("reversalDebit", this.reversalDebit);

        jo.put("cashDebit", this.cashDebit);
        jo.put("cashCredit", this.cashCredit);

        jo.put("derivativeCredit", this.derivativeCredit);
        jo.put("derivativeDebit", this.derivativeDebit);

        jo.put("opBal", this.opBal);
        jo.put("clsBal", this.clsBal);

        jo.put("mtfCredit", this.mtfCredit);
        jo.put("mtfDebit", this.mtfDebit);

        jo.put("mfssCredit", this.mfssCredit);
        jo.put("mfssDebit", this.mfssDebit);

        jo.put("ipoCredit", this.ipoCredit);
        jo.put("ipoDebit", this.ipoDebit);

        return jo;
    }


    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.EXCHANGE = jo.optString("EXCHANGE");
        this.VOUCHER_DATE = jo.optString("VOUCHER_DATE");
        this.EFFECTIVE_DATE = jo.optString("EFFECTIVE_DATE");
        this.VOUCHER_TYPE = jo.optString("VOUCHER_TYPE");
        this.VOUCHER_NO = jo.optString("VOUCHER_NO");
        this.NARRATION = jo.optString("NARRATION");
        this.CHEQUE_DDNO = jo.optString("CHEQUE_DDNO");
        this.DEBIT = jo.optString("DEBIT");
        this.CREDIT = jo.optString("CREDIT");
        this.BALANCE = jo.optString("BALANCE");

        this.fundRecieved = jo.optString("fundRecieved");
        this.fundWithdrawn = jo.optString("fundWithdrawn");

        this.reversalCredit = jo.optString("reversalCredit");
        this.reversalDebit = jo.optString("reversalDebit");

        this.cashDebit = jo.optString("cashDebit");
        this.cashCredit = jo.optString("cashCredit");

        this.derivativeCredit = jo.optString("derivativeCredit");
        this.derivativeDebit = jo.optString("derivativeDebit");

        this.opBal = jo.optString("opBal");
        this.clsBal = jo.optString("clsBal");

        this.mtfCredit = jo.optString("mtfCredit");
        this.mtfDebit = jo.optString("mtfDebit");

        this.mfssDebit = jo.optString("mfssDebit");
        this.mfssCredit = jo.optString("mfssCredit");

        this.ipoCredit = jo.optString("ipoCredit");
        this.ipoDebit = jo.optString("ipoDebit");

        return this;
    }
}


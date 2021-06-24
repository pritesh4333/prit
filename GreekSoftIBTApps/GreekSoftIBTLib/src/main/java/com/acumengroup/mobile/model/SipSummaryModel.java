package com.acumengroup.mobile.model;

import java.io.Serializable;

public class SipSummaryModel implements Serializable {
    private String sessionId;
    private String clientCode;
    private String sipFrequency;
    private String paymentMode;
    private String sipAmount;
    private String sipNoOfInst;
    private String localIp;
    private String amcName;
    private String schemeName;
    private String schemeIsin;
    private String startDate;
    private String endDate;
    private String transmode;
    private String dpTransaction;
    private String schemeCode;
    private String bseRTACode;
    private String bseCode;
    private String WithdrawalsUnit;
    private String WithdrawalsAmt;
    private String firstOrderToday;
    private String euinDecl;
    private String numberOfTransfer;
    private String fromSchemeCode;
    private String toSchemeCode;
    private String buySelltype;
    private String transferAmt;
    private String internalRefNumber;
    private String folioNumber;
    private String fromSchemeName;
    private String toSchemeName;
    private String mandateId;

    public String getMandateId() {
        return mandateId;
    }

    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    public String getFromSchemeName() {
        return fromSchemeName;
    }

    public void setFromSchemeName(String fromSchemeName) {
        this.fromSchemeName = fromSchemeName;
    }

    public String getToSchemeName() {
        return toSchemeName;
    }

    public void setToSchemeName(String toSchemeName) {
        this.toSchemeName = toSchemeName;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public String getInternalRefNumber() {
        return internalRefNumber;
    }

    public void setInternalRefNumber(String internalRefNumber) {
        this.internalRefNumber = internalRefNumber;
    }

    public String getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(String transferAmt) {
        this.transferAmt = transferAmt;
    }

    public String getBuySelltype() {
        return buySelltype;
    }

    public void setBuySelltype(String buySelltype) {
        this.buySelltype = buySelltype;
    }

    public String getFromSchemeCode() {
        return fromSchemeCode;
    }

    public void setFromSchemeCode(String fromSchemeCode) {
        this.fromSchemeCode = fromSchemeCode;
    }

    public String getToSchemeCode() {
        return toSchemeCode;
    }

    public void setToSchemeCode(String toSchemeCode) {
        this.toSchemeCode = toSchemeCode;
    }

    public String getNumberOfTransfer() {
        return numberOfTransfer;
    }

    public void setNumberOfTransfer(String numberOfTransfer) {
        this.numberOfTransfer = numberOfTransfer;
    }

    public String getEuinDecl() {
        return euinDecl;
    }

    public void setEuinDecl(String euinDecl) {
        this.euinDecl = euinDecl;
    }

    public String getFirstOrderToday() {
        return firstOrderToday;
    }

    public void setFirstOrderToday(String firstOrderToday) {
        this.firstOrderToday = firstOrderToday;
    }

    public String getWithdrawalsAmt() {
        return WithdrawalsAmt;
    }

    public void setWithdrawalsAmt(String withdrawalsAmt) {
        WithdrawalsAmt = withdrawalsAmt;
    }

    public String getWithdrawalsUnit() {
        return WithdrawalsUnit;
    }

    public void setWithdrawalsUnit(String withdrawalsUnit) {
        this.WithdrawalsUnit = withdrawalsUnit;
    }

    public String getBseRTACode() {
        return bseRTACode;
    }

    public void setBseRTACode(String bseRTACode) {
        this.bseRTACode = bseRTACode;
    }

    public String getBseCode() {
        return bseCode;
    }

    public void setBseCode(String bseCode) {
        this.bseCode = bseCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getTransmode() {
        return transmode;
    }

    public void setTransmode(String transmode) {
        this.transmode = transmode;
    }

    public String getDpTransaction() {
        return dpTransaction;
    }

    public void setDpTransaction(String dpTransaction) {
        this.dpTransaction = dpTransaction;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
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

    public String getSipAmount() {
        return sipAmount;
    }

    public void setSipAmount(String sipAmount) {
        this.sipAmount = sipAmount;
    }

    public String getSipNoOfInst() {
        return sipNoOfInst;
    }

    public void setSipNoOfInst(String sipNoOfInst) {
        this.sipNoOfInst = sipNoOfInst;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getAmcName() {
        return amcName;
    }

    public void setAmcName(String amcName) {
        this.amcName = amcName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeIsin() {
        return schemeIsin;
    }

    public void setSchemeIsin(String schemeIsin) {
        this.schemeIsin = schemeIsin;
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
}

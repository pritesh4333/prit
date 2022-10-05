package com.acumengroup.greekmain.core.model.MutualFundSipNewOrder;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.mutualfundmutualfundsendneworder.MutualFundMutualFundSendNewOrderRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MutualFundSipNewOrderRequest implements GreekRequestModel, GreekResponseModel {
    private String SchemeName;
    private String ClientCode;
    private String transactionCode;
    private String FolioNo;
    private String SchemeIsin;
    private String TransactionMode;
    private String DpTransactionMode;
    private String SipFrequency;
    private String PaymentMode;
    private String SipInstallmentAmount;
    private String SipStartDate;
    private String NumberOfInstallment;
    private String SipEndDate;
    private String RequestorType;
    private String RequestorIP;
    private String UserCode;
    private String SchemeCode;
    private static JSONObject echoParam = null;


    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        SchemeCode = schemeCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName) {
        SchemeName = schemeName;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getFolioNo() {
        return FolioNo;
    }

    public void setFolioNo(String folioNo) {
        FolioNo = folioNo;
    }

    public String getSchemeIsin() {
        return SchemeIsin;
    }

    public void setSchemeIsin(String schemeIsin) {
        SchemeIsin = schemeIsin;
    }

    public String getTransactionMode() {
        return TransactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        TransactionMode = transactionMode;
    }

    public String getDpTransactionMode() {
        return DpTransactionMode;
    }

    public void setDpTransactionMode(String dpTransactionMode) {
        DpTransactionMode = dpTransactionMode;
    }

    public String getSipFrequency() {
        return SipFrequency;
    }

    public void setSipFrequency(String sipFrequency) {
        SipFrequency = sipFrequency;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getSipInstallmentAmount() {
        return SipInstallmentAmount;
    }

    public void setSipInstallmentAmount(String sipInstallmentAmount) {
        SipInstallmentAmount = sipInstallmentAmount;
    }

    public String getSipStartDate() {
        return SipStartDate;
    }

    public void setSipStartDate(String sipStartDate) {
        SipStartDate = sipStartDate;
    }

    public String getNumberOfInstallment() {
        return NumberOfInstallment;
    }

    public void setNumberOfInstallment(String numberOfInstallment) {
        NumberOfInstallment = numberOfInstallment;
    }

    public String getSipEndDate() {
        return SipEndDate;
    }

    public void setSipEndDate(String sipEndDate) {
        SipEndDate = sipEndDate;
    }

    public String getRequestorType() {
        return RequestorType;
    }

    public void setRequestorType(String requestorType) {
        RequestorType = requestorType;
    }

    public String getRequestorIP() {
        return RequestorIP;
    }

    public void setRequestorIP(String requestorIP) {
        RequestorIP = requestorIP;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }


    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("SchemeName", this.SchemeName);
        jo.put("ClientCode", this.ClientCode);
        jo.put("transactionCode", this.transactionCode);
        jo.put("FolioNo", this.FolioNo);
        jo.put("SchemeIsin", this.SchemeIsin);
        jo.put("TransactionMode", this.TransactionMode);
        jo.put("DpTransactionMode", this.DpTransactionMode);
        jo.put("SipFrequency", this.SipFrequency);
        jo.put("transactionCode", this.transactionCode);
        jo.put("PaymentMode", this.PaymentMode);
        jo.put("SipInstallmentAmount", this.SipInstallmentAmount);
        jo.put("SipStartDate", this.SipStartDate);
        jo.put("NumberOfInstallment", this.NumberOfInstallment);
        jo.put("SipEndDate", this.SipEndDate);
        jo.put("RequestorType", this.RequestorType);
        jo.put("RequestorIP", this.RequestorIP);
        jo.put("UserCode", this.UserCode);
        jo.put("SchemeCode", this.SchemeCode);
        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.SchemeName = jo.optString("SchemeName");
        this.ClientCode = jo.optString("ClientCode");
        this.transactionCode = jo.optString("transactionCode");
        this.FolioNo = jo.optString("FolioNo");
        this.SchemeIsin = jo.optString("SchemeIsin");
        this.TransactionMode = jo.optString("TransactionMode");
        this.DpTransactionMode = jo.optString("DpTransactionMode");
        this.SipFrequency = jo.optString("SipFrequency");
        this.PaymentMode = jo.optString("PaymentMode");
        this.SipInstallmentAmount = jo.optString("SipInstallmentAmount");
        this.SipStartDate = jo.optString("SipStartDate");
        this.NumberOfInstallment = jo.optString("NumberOfInstallment");
        this.SipEndDate = jo.optString("SipEndDate");
        this.RequestorType = jo.optString("RequestorType");
        this.RequestorIP = jo.optString("RequestorIP");
        this.UserCode = jo.optString("UserCode");
        this.SchemeCode = jo.optString("SchemeCode");
        return this;
    }

    public static void sendRequest(MutualFundMutualFundSendNewOrderRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(MutualFundSipNewOrderResponse.class);
        jsonRequest.setService("siporderentryparam", "getConnectToMF");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }


    @Deprecated
    public static void sendRequest(String SchemeName, String ClientCode, String transactionCode, String FolioNo, String SchemeIsin, String TransactionMode, String DpTransactionMode, String SipFrequency, String PaymentMode, String SipInstallmentAmount, String SipStartDate, String NumberOfInstallment, String SipEndDate, String RequestorType, String RequestorIP, String UserCode, String SchemeCode, Context ctx, ServiceResponseListener listener) {
        MutualFundSipNewOrderRequest request = new MutualFundSipNewOrderRequest();
        request.setSchemeName(SchemeName);
        request.setClientCode(ClientCode);
        request.setTransactionCode(transactionCode);
        request.setFolioNo(FolioNo);
        request.setSchemeIsin(SchemeIsin);
        request.setTransactionMode(TransactionMode);
        request.setDpTransactionMode(DpTransactionMode);
        request.setSipFrequency(SipFrequency);
        request.setTransactionCode(transactionCode);
        request.setPaymentMode(PaymentMode);
        request.setSipInstallmentAmount(SipInstallmentAmount);
        request.setSipStartDate(SipStartDate);
        request.setNumberOfInstallment(NumberOfInstallment);
        request.setSipEndDate(SipEndDate);
        request.setRequestorType(RequestorType);
        request.setRequestorIP(RequestorIP);
        request.setUserCode(UserCode);
        request.setSchemeCode(SchemeCode);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


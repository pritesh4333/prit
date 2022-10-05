package com.acumengroup.greekmain.core.model.mutualfundmutualfundsendneworder;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MutualFundMutualFundSendNewOrderRequest implements GreekRequestModel, GreekResponseModel {
    private String schemeName;
    private String purchaseType;
    private String orderType;
    private String amcName;
    private String schemeCode;
    private String strToken;
    private String amtUnit;
    private String clientCode;
    private String transactionCode;
    private String dpTxn;
    private String qty;
    private String allRedeem;
    private String folioNumber;
    private String schemeIsin;

    private static JSONObject echoParam = null;

    public String getSchemeIsin() {
        return schemeIsin;
    }

    public void setSchemeIsin(String schemeIsin) {
        this.schemeIsin = schemeIsin;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getPurchaseType() {
        return this.purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAmcName() {
        return this.amcName;
    }

    public void setAmcName(String amcName) {
        this.amcName = amcName;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getStrToken() {
        return this.strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public String getAmtUnit() {
        return this.amtUnit;
    }

    public void setAmtUnit(String amtUnit) {
        this.amtUnit = amtUnit;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getDpTxn() {
        return dpTxn;
    }

    public void setDpTxn(String dpTxn) {
        this.dpTxn = dpTxn;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAllRedeem() {
        return allRedeem;
    }

    public void setAllRedeem(String allRedeem) {
        this.allRedeem = allRedeem;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("schemeName", this.schemeName);
        jo.put("purchaseType", this.purchaseType);
        jo.put("orderType", this.orderType);
        jo.put("amcName", this.amcName);
        jo.put("schemeCode", this.schemeCode);
        jo.put("strToken", this.strToken);
        jo.put("amtUnit", this.amtUnit);
        jo.put("clientCode", this.clientCode);
        jo.put("transactionCode", this.transactionCode);
        jo.put("dpTxn", this.dpTxn);
        jo.put("qty", this.qty);
        jo.put("allRedeem", this.allRedeem);
        jo.put("folioNumber", this.folioNumber);
        jo.put("schemeIsin", this.schemeIsin);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.schemeName = jo.optString("schemeName");
        this.purchaseType = jo.optString("purchaseType");
        this.orderType = jo.optString("orderType");
        this.amcName = jo.optString("amcName");
        this.schemeCode = jo.optString("schemeCode");
        this.strToken = jo.optString("strToken");
        this.amtUnit = jo.optString("amtUnit");
        this.clientCode = jo.optString("clientCode");
        this.transactionCode = jo.optString("transactionCode");
        this.dpTxn = jo.optString("dpTxn");
        this.qty = jo.optString("qty");
        this.allRedeem = jo.optString("allRedeem");
        this.folioNumber = jo.optString("folioNumber");
        this.schemeIsin = jo.optString("schemeIsin");
        return this;
    }

    public static void addEchoParam(String key, String value) {
        try {
            if (echoParam == null) {
                echoParam = new JSONObject();
            }
            echoParam.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        jsonRequest.setResponseClass(MutualFundMutualFundSendNewOrderResponse.class);
        jsonRequest.setService("orderentryparam", "getConnectToMF");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String transactionCode, String DPTxn, String qty, String allRedeem, String folioNo, String amcName, String amtUnit, String clientCode, String orderType, String purchaseType, String schemeCode, String schemeName, String strToken, String Isin, Context ctx, ServiceResponseListener listener) {
        MutualFundMutualFundSendNewOrderRequest request = new MutualFundMutualFundSendNewOrderRequest();
        request.setAmcName(amcName);
        request.setAmtUnit(amtUnit);
        request.setClientCode(clientCode);
        request.setOrderType(orderType);
        request.setPurchaseType(purchaseType);
        request.setSchemeCode(schemeCode);
        request.setSchemeName(schemeName);
        request.setStrToken(strToken);
        request.setTransactionCode(transactionCode);
        request.setDpTxn(DPTxn);
        request.setQty(qty);
        request.setAllRedeem(allRedeem);
        request.setFolioNumber(folioNo);
        request.setSchemeIsin(Isin);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

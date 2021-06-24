package com.acumengroup.greekmain.core.model.SWPOrder;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MutualFundSipNewOrderResponse;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SWPOrderlRequest implements GreekRequestModel, GreekResponseModel {

    public static final String SERVICE_NAME = "getConnectToMF";

    private static JSONObject echoParam = null;
    private String clientCode;
    private String flag;
    private String bseSchemeCode;
    private String transactionMode;
    private String folioNumber;
    private String internalRefNumber;
    private String startDate;
    private String numberOfWithdrawls;
    private String frequencyType;
    private String installmentAmount;
    private String installmentUnit;
    private String firstOrderToday;
    private String subBrokerCode;
    private String EUINDeclaration;
    private String EUINNumber;
    private String remarks;
    private String subBroker;
    private String cSchemeName;


    public String getcSchemeName() {
        return cSchemeName;
    }

    public void setcSchemeName(String cSchemeName) {
        this.cSchemeName = cSchemeName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getBseSchemeCode() {
        return bseSchemeCode;
    }

    public void setBseSchemeCode(String bseSchemeCode) {
        this.bseSchemeCode = bseSchemeCode;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNumberOfWithdrawls() {
        return numberOfWithdrawls;
    }

    public void setNumberOfWithdrawls(String numberOfWithdrawls) {
        this.numberOfWithdrawls = numberOfWithdrawls;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getInstallmentUnit() {
        return installmentUnit;
    }

    public void setInstallmentUnit(String installmentUnit) {
        this.installmentUnit = installmentUnit;
    }

    public String getFirstOrderToday() {
        return firstOrderToday;
    }

    public void setFirstOrderToday(String firstOrderToday) {
        this.firstOrderToday = firstOrderToday;
    }

    public String getSubBrokerCode() {
        return subBrokerCode;
    }

    public void setSubBrokerCode(String subBrokerCode) {
        this.subBrokerCode = subBrokerCode;
    }

    public String getEUINDeclaration() {
        return EUINDeclaration;
    }

    public void setEUINDeclaration(String EUINDeclaration) {
        this.EUINDeclaration = EUINDeclaration;
    }

    public String getEUINNumber() {
        return EUINNumber;
    }

    public void setEUINNumber(String EUINNumber) {
        this.EUINNumber = EUINNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSubBroker() {
        return subBroker;
    }

    public void setSubBroker(String subBroker) {
        this.subBroker = subBroker;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("clientCode", this.clientCode);
        jo.put("flag", this.flag);
        jo.put("bseSchemeCode", this.bseSchemeCode);
        jo.put("transactionMode", this.transactionMode);
        jo.put("folioNumber", this.folioNumber);
        jo.put("internalRefNumber", this.internalRefNumber);
        jo.put("startDate", this.startDate);
        jo.put("numberOfWithdrawls", this.numberOfWithdrawls);
        jo.put("frequencyType", this.frequencyType);
        jo.put("installmentAmount", this.installmentAmount);
        jo.put("installmentUnit", this.installmentUnit);
        jo.put("firstOrderToday", this.firstOrderToday);
        jo.put("subBrokerCode", this.subBrokerCode);
        jo.put("EUINDeclaration", this.EUINDeclaration);
        jo.put("EUINNumber", this.EUINNumber);
        jo.put("remarks", this.remarks);
        jo.put("subBroker", this.subBroker);
        jo.put("cSchemeName", this.cSchemeName);


        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.clientCode = jo.optString("clientCode");
        this.flag = jo.optString("flag");
        this.bseSchemeCode = jo.optString("bseSchemeCode");
        this.transactionMode = jo.optString("transactionMode");
        this.folioNumber = jo.optString("folioNumber");
        this.internalRefNumber = jo.optString("internalRefNumber");
        this.startDate = jo.optString("startDate");
        this.numberOfWithdrawls = jo.optString("numberOfWithdrawls");
        this.frequencyType = jo.optString("frequencyType");
        this.installmentAmount = jo.optString("installmentAmount");
        this.installmentUnit = jo.optString("installmentUnit");
        this.firstOrderToday = jo.optString("firstOrderToday");
        this.subBrokerCode = jo.optString("subBrokerCode");
        this.EUINDeclaration = jo.optString("EUINDeclaration");
        this.EUINNumber = jo.optString("EUINNumber");
        this.remarks = jo.optString("remarks");
        this.cSchemeName = jo.optString("cSchemeName");

        this.subBroker = jo.optString("subBroker");

        return this;
    }


    @Deprecated
    public static void sendRequest(String clientCode, String flag, String bseSchemeCode, String transactionMode, String folioNumber, String internalRefNumber, String startDate, String numberOfWithdrawls, String frequencyType, String installmentAmount, String installmentUnit, String firstOrderToday, String subBrokerCode, String EUINDeclaration, String EUINNumber, String remarks, String subBroker, String schemeName, Context ctx, ServiceResponseListener listener) {

        SWPOrderlRequest request = new SWPOrderlRequest();
        request.setClientCode(clientCode);
        request.setFlag(flag);
        request.setBseSchemeCode(bseSchemeCode);
        request.setTransactionMode(transactionMode);
        request.setFolioNumber(folioNumber);
        request.setInternalRefNumber(internalRefNumber);
        request.setStartDate(startDate);
        request.setNumberOfWithdrawls(numberOfWithdrawls);
        request.setFrequencyType(frequencyType);
        request.setInstallmentAmount(installmentAmount);
        request.setInstallmentUnit(installmentUnit);
        request.setFirstOrderToday(firstOrderToday);
        request.setSubBrokerCode(subBrokerCode);
        request.setEUINDeclaration(EUINDeclaration);
        request.setEUINNumber(EUINNumber);
        request.setRemarks(remarks);
        request.setSubBroker(subBroker);
        request.setcSchemeName(schemeName);


        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("ValidateGuest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(MutualFundSipNewOrderResponse.class);
        jsonRequest.setService("MFAPI", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
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

    public static void sendRequest(SWPOrderlRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

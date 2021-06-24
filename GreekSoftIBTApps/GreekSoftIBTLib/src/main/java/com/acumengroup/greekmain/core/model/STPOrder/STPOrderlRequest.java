package com.acumengroup.greekmain.core.model.STPOrder;

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

public class STPOrderlRequest implements GreekRequestModel, GreekResponseModel {

    public static final String SERVICE_NAME = "getConnectToMF";

    private static JSONObject echoParam = null;
    private String clientCode;
    private String flag;
    private String fromBseScheme;
    private String toBseSchemeCode;
    private String buySellType;
    private String transactionMode;
    private String internalRefNumber;
    private String folioNumber;
    private String startDate;
    private String frequencyType;
    private String noOfTransfers;
    private String installmentAmount;
    private String firstOrderToday;
    private String subBrokerCode;
    private String EUINDeclaration;
    private String EUINNumber;
    private String remarks;
    private String subBroker;
    private String cFromSchemeName;
    private String cToSchemeName;


    public String getcFromSchemeName() {
        return cFromSchemeName;
    }

    public void setcFromSchemeName(String cFromSchemeName) {
        this.cFromSchemeName = cFromSchemeName;
    }

    public String getcToSchemeName() {
        return cToSchemeName;
    }

    public void setcToSchemeName(String cToSchemeName) {
        this.cToSchemeName = cToSchemeName;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFromBseScheme() {
        return fromBseScheme;
    }

    public void setFromBseScheme(String fromBseScheme) {
        this.fromBseScheme = fromBseScheme;
    }

    public String getToBseSchemeCode() {
        return toBseSchemeCode;
    }

    public void setToBseSchemeCode(String toBseSchemeCode) {
        this.toBseSchemeCode = toBseSchemeCode;
    }

    public String getBuySellType() {
        return buySellType;
    }

    public void setBuySellType(String buySellType) {
        this.buySellType = buySellType;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
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

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public String getNoOfTransfers() {
        return noOfTransfers;
    }

    public void setNoOfTransfers(String noOfTransfers) {
        this.noOfTransfers = noOfTransfers;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
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
        jo.put("fromBseScheme", this.fromBseScheme);
        jo.put("toBseSchemeCode", this.toBseSchemeCode);
        jo.put("buySellType", this.buySellType);
        jo.put("transactionMode", this.transactionMode);
        jo.put("folioNumber", this.folioNumber);
        jo.put("internalRefNumber", this.internalRefNumber);
        jo.put("startDate", this.startDate);
        jo.put("frequencyType", this.frequencyType);
        jo.put("noOfTransfers", this.noOfTransfers);
        jo.put("installmentAmount", this.installmentAmount);
        jo.put("firstOrderToday", this.firstOrderToday);
        jo.put("subBrokerCode", this.subBrokerCode);
        jo.put("EUINDeclaration", this.EUINDeclaration);
        jo.put("EUINNumber", this.EUINNumber);
        jo.put("remarks", this.remarks);
        jo.put("subBrokerCode", this.subBrokerCode);
        jo.put("subBroker", this.subBroker);
        jo.put("cFromSchemeName", this.cFromSchemeName);
        jo.put("cToSchemeName", this.cToSchemeName);


        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.clientCode = jo.optString("clientCode");
        this.flag = jo.optString("flag");
        this.fromBseScheme = jo.optString("fromBseScheme");
        this.toBseSchemeCode = jo.optString("toBseSchemeCode");
        this.buySellType = jo.optString("buySellType");
        this.transactionMode = jo.optString("transactionMode");
        this.startDate = jo.optString("startDate");
        this.folioNumber = jo.optString("folioNumber");
        this.internalRefNumber = jo.optString("internalRefNumber");
        this.frequencyType = jo.optString("frequencyType");
        this.installmentAmount = jo.optString("installmentAmount");
        this.noOfTransfers = jo.optString("noOfTransfers");
        this.firstOrderToday = jo.optString("firstOrderToday");
        this.subBrokerCode = jo.optString("subBrokerCode");
        this.EUINDeclaration = jo.optString("EUINDeclaration");
        this.EUINNumber = jo.optString("EUINNumber");
        this.remarks = jo.optString("remarks");
        this.subBroker = jo.optString("subBroker");
        this.cFromSchemeName = jo.optString("cFromSchemeName");
        this.cToSchemeName = jo.optString("cToSchemeName");

        return this;
    }


    @Deprecated
    public static void sendRequest(String clientCode, String flag, String frombseSchemeCode, String tobseSchemecode, String buysellType, String transactionMode, String folioNumber, String internalRefNumber, String startDate, String frequencyType, String installmentAmount, String firstOrderToday, String subBrokerCode, String EUINDeclaration, String EUINNumber, String remarks, String subBroker, String numberoftransfer, String fromschemeName, String toschemeName, Context ctx, ServiceResponseListener listener) {

        STPOrderlRequest request = new STPOrderlRequest();
        request.setClientCode(clientCode);
        request.setFlag(flag);
        request.setToBseSchemeCode(tobseSchemecode);
        request.setFromBseScheme(frombseSchemeCode);
        request.setBuySellType(buysellType);
        request.setTransactionMode(transactionMode);
        request.setFolioNumber(folioNumber);
        request.setInternalRefNumber(internalRefNumber);
        request.setStartDate(startDate);
        request.setFrequencyType(frequencyType);
        request.setInstallmentAmount(installmentAmount);
        request.setFirstOrderToday(firstOrderToday);
        request.setSubBrokerCode(subBrokerCode);
        request.setEUINDeclaration(EUINDeclaration);
        request.setEUINNumber(EUINNumber);
        request.setRemarks(remarks);
        request.setSubBroker(subBroker);
        request.setNoOfTransfers(numberoftransfer);
        request.setcFromSchemeName(fromschemeName);
        request.setcToSchemeName(toschemeName);


        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("STPOrderlRequest", "jsonRequest==>" + jsonRequest);

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

    public static void sendRequest(STPOrderlRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.acumengroup.greekmain.core.model.FundTransfer;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 04-Jul-17.
 */

public class FundTransferResponseDetailsRequest implements GreekRequestModel, GreekResponseModel {
    private String ClientCode;
    private String SessionId;
    private String transId;
    private String bankTransId;
    private String cardNumber;
    private String bankName;
    private String UniqueId;

    public static final String SERVICE_NAME = "fundTransferResponseDetails";
    private static JSONObject echoParam = null;


    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getBankTransId() {
        return bankTransId;
    }

    public void setBankTransId(String bankTransId) {
        this.bankTransId = bankTransId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("ClientCode", this.ClientCode);
        jo.put("SessionId", this.SessionId);
        jo.put("transId", this.transId);
        jo.put("bankTransId", this.bankTransId);
        jo.put("cardNumber", this.cardNumber);
        jo.put("bankName", this.bankName);
        jo.put("UniqueId", this.UniqueId);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ClientCode = jo.optString("ClientCode");
        this.SessionId = jo.optString("SessionId");
        this.transId = jo.optString("transId");
        this.bankTransId = jo.optString("bankTransId");
        this.cardNumber = jo.optString("cardNumber");
        this.bankName = jo.optString("bankName");
        this.UniqueId = jo.optString("UniqueId");

        return this;
    }

    public static void sendRequest(FundTransferResponseDetailsRequest request, Context ctx, ServiceResponseListener listener) {
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
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(FundTransferResponseDetailsResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String clientcode, String sessionid, String transId, String bankTransId, String cardNumber, String bankName, String uniqueid, Context ctx, ServiceResponseListener listener) {
        FundTransferResponseDetailsRequest request = new FundTransferResponseDetailsRequest();

        request.setClientCode(clientcode);
        request.setSessionId(sessionid);
        request.setTransId(transId);
        request.setBankName(bankName);
        request.setBankTransId(bankTransId);
        request.setCardNumber(cardNumber);
        request.setUniqueId(uniqueid);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

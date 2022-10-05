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

public class UPITransferDetailsRequest implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String ClientCode;
    private String SessionId;
    private String amount;
    private String segment;
    private String bankName;
    private String transactionMethod;
    private String deviceType;
    private String descripn;



    public static final String SERVICE_NAME = "fundTransferDetailsUpi";
    private static JSONObject echoParam = null;


    public String getDescripn() {
        return descripn;
    }

    public void setDescripn(String descripn) {
        this.descripn = descripn;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTransactionMethod() {
        return transactionMethod;
    }

    public void setTransactionMethod(String transactionMethod) {
        this.transactionMethod = transactionMethod;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("gscid", this.gscid);
        jo.put("ClientCode", this.ClientCode);
        jo.put("amount", this.amount);
        jo.put("SessionId", this.SessionId);
        jo.put("segment", this.segment);
        jo.put("bankName", this.bankName);
        jo.put("transactionMethod", this.transactionMethod);
        jo.put("deviceType", this.deviceType);
        jo.put("descripn", this.descripn);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ClientCode = jo.optString("ClientCode");
        this.gscid = jo.optString("gscid");
        this.amount = jo.optString("amount");
        this.SessionId = jo.optString("SessionId");
        this.segment = jo.optString("segment");
        this.bankName = jo.optString("bankName");
        this.transactionMethod = jo.optString("transactionMethod");
        this.deviceType = jo.optString("deviceType");
        this.descripn = jo.optString("descripn");
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

    public static void sendRequest(UPITransferDetailsRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(FundTransferDetailsResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String clientcode, String amount, String sessionid, String segment,String bank, Context ctx, ServiceResponseListener listener) {
        UPITransferDetailsRequest request = new UPITransferDetailsRequest();

        request.setGscid(gscid);
        request.setClientCode(clientcode);
        request.setAmount(amount);
        request.setSessionId(sessionid);
        request.setSegment(segment);
        request.setBankName(bank);
        request.setTransactionMethod("UPI");
        request.setDeviceType("Android");
        request.setDescripn("Initiated");

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

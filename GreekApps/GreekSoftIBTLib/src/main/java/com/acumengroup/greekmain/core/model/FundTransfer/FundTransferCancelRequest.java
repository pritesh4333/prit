package com.acumengroup.greekmain.core.model.FundTransfer;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class FundTransferCancelRequest implements GreekRequestModel, GreekResponseModel {
    private String ClientCode;
    private String SessionId;
    private String discriminator;
    private String UniqueId;

    public static final String SERVICE_NAME = "FTCancelResponse";
    private static JSONObject echoParam = null;


    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
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
        jo.put("UniqueId", this.UniqueId);
        jo.put("discriminator", this.discriminator);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ClientCode = jo.optString("ClientCode");
        this.SessionId = jo.optString("SessionId");
        this.UniqueId = jo.optString("UniqueId");
        this.discriminator = jo.optString("discriminator");

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
        jsonRequest.setResponseClass(FundTransferCancelResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String clientcode, String sessionid, String uniqueid, String discriminator, Context ctx, ServiceResponseListener listener) {
        FundTransferCancelRequest request = new FundTransferCancelRequest();

        request.setClientCode(clientcode);
        request.setSessionId(sessionid);
        request.setUniqueId(uniqueid);
        request.setUniqueId(discriminator);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


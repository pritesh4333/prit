package com.acumengroup.greekmain.core.model.GCM;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sushant on 8/18/2016.
 */
public class SendNotifyInformationRequest implements GreekRequestModel, GreekResponseModel {
    private String gcid;
    private String gscid;
    private String sessionId;
    private String deviceId;
    private String gcmApiKey;
    private String gcmDeviceToken;
    private String statusFlag;

    public static final String SERVICE_NAME = "gcm_information";
    private static JSONObject echoParam = null;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGcmApiKey() {
        return gcmApiKey;
    }

    public void setGcmApiKey(String gcmApiKey) {
        this.gcmApiKey = gcmApiKey;
    }

    public String getGcmDeviceToken() {
        return gcmDeviceToken;
    }

    public void setGcmDeviceToken(String gcmDeviceToken) {
        this.gcmDeviceToken = gcmDeviceToken;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gcid", this.gcid);
        jo.put("sessionId", this.sessionId);
        jo.put("deviceId", this.deviceId);
        jo.put("gcmApiKey", this.gcmApiKey);
        jo.put("gcmDeviceToken", this.gcmDeviceToken);
        jo.put("flag", this.statusFlag);
        jo.put("gscid", this.gscid);

        JSONArray ja1 = new JSONArray();
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gcid = jo.optString("gcid");
        this.sessionId = jo.optString("sessionId");
        this.deviceId = jo.optString("deviceId");
        this.gcmApiKey = jo.optString("gcmApiKey");
        this.gcmDeviceToken = jo.optString("gcmDeviceToken");
        this.statusFlag = jo.optString("flag");
        this.gscid = jo.optString("gscid");

        return this;
    }

    public static void sendRequest(SendNotifyInformationRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
//        GreekJSONRequest jsonRequest = null;
//        jsonRequest = new GreekJSONRequest(ctx, request);

        GreekBaseJSONRequest jsonRequest = null;

        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(ReceiveNotifyInformationResponse.class);
        jsonRequest.setService("Login", "gcm_information");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gcid, String gscid, String sessionId, String deviceId, String gcmApiKey, String gcmDeviceToken, String statusFlag, Context ctx, ServiceResponseListener listener) {
        SendNotifyInformationRequest request = new SendNotifyInformationRequest();
        request.setGcid(gcid);
        request.setSessionId(sessionId);
        request.setDeviceId(deviceId);
        request.setGcmApiKey(gcmApiKey);
        request.setGcmDeviceToken(gcmDeviceToken);
        request.setStatusFlag(statusFlag);
        request.setGscid(gscid);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

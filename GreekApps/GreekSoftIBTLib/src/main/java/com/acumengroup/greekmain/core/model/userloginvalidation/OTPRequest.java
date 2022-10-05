package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sushant on 9/19/2016.
 */
public class OTPRequest implements GreekRequestModel, GreekResponseModel {
    private String mobile;
    private String deviceId;
    private String gcmApiKey;
    private String gcmToken;
    private String email;


    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "generate_password";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("mobile", this.mobile);
        jo.put("deviceId", this.deviceId);
        jo.put("gcmApiKey", this.gcmApiKey);
        jo.put("gcmToken", this.gcmToken);
        jo.put("email", this.email);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.mobile = jo.optString("mobile");
        this.deviceId = jo.optString("deviceId");
        this.gcmApiKey = jo.optString("gcmApiKey");
        this.gcmToken = jo.optString("gcmToken");
        this.email = jo.optString("email");

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

    public static void sendRequest(UserLoginValidationRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(OTPResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String mobile, String deviceId, String gcmApiKey, String gcmToken, Context ctx, ServiceResponseListener listener) {
        OTPRequest request = new OTPRequest();
        request.setMobile(mobile);
        request.setDeviceId(deviceId);
        request.setGcmApiKey(gcmApiKey);
        request.setGcmToken(gcmToken);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequestMF(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(OTPResponse.class);
        jsonRequest.setService("Login", "resendOtpMF");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequestMF(String mobile, String emailid, Context ctx, ServiceResponseListener listener) {
        OTPRequest request = new OTPRequest();
        request.setMobile(mobile);
        request.setEmail(emailid);

        try {
            sendRequestMF(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
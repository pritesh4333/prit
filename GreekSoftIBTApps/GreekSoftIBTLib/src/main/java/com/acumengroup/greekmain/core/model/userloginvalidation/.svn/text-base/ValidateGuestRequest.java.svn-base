package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sushant on 9/19/2016.
 */
public class ValidateGuestRequest implements GreekRequestModel, GreekResponseModel {
    private String deviceId;
    private String mobileNumber;
    private String gcmToken;
    private String serverApiKey;
    private String version_no;
    private String password;
    private String gscid;

    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "validate_guest";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public String getGsCid() {
        return gscid;
    }

    public void setGsCid(String gsCid) {
        this.gscid = gsCid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public String getServerApiKey() {
        return serverApiKey;
    }

    public void setServerApiKey(String serverApiKey) {
        this.serverApiKey = serverApiKey;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("deviceId", this.deviceId);
        jo.put("mobile", this.mobileNumber);
        jo.put("gcmToken", this.gcmToken);
        jo.put("gcmApiKey", this.serverApiKey);
        jo.put("version_no", this.version_no);
        jo.put("password", this.password);
        jo.put("gscid", this.gscid);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.deviceId = jo.optString("deviceId");
        this.mobileNumber = jo.optString("mobile");
        this.gcmToken = jo.optString("gcmToken");
        this.serverApiKey = jo.optString("gcmApiKey");
        this.version_no = jo.optString("version_no");
        this.password = jo.optString("password");
        this.gscid = jo.optString("gscid");
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
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        Log.e("ValidateGuest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(ValidateGuestResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String mobileNumber, String gcmToken, String serverApiKey, String deviceId, String version_no, String password, Context ctx, ServiceResponseListener listener) {
        ValidateGuestRequest request = new ValidateGuestRequest();
        request.setMobileNumber(mobileNumber);
        request.setDeviceId(deviceId);
        request.setGcmToken(gcmToken);
        request.setServerApiKey(serverApiKey);
        request.setGsCid(deviceId);
        request.setVersion_no(version_no);
        request.setPassword(password);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestFlag(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekBaseJSONRequest jsonRequest = null;

        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(ValidateGuestResponse.class);
        jsonRequest.setService("getFlagValues", "getFlagValues");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequestFlags(Context ctx, ServiceResponseListener listener) {
        ValidateGuestRequest request = new ValidateGuestRequest();

        try {
            sendRequestFlag(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
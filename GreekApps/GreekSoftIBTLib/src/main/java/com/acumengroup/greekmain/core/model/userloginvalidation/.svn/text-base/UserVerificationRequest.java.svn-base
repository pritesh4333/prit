package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserVerificationRequest implements GreekRequestModel, GreekResponseModel {

    private String deviceId;
    private String mobile;
    private String email;
    private String product;


    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "generateOtpMF";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("deviceId", this.deviceId);
        jo.put("mobile", this.mobile);
        jo.put("email", this.email);
        jo.put("product", this.product);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.deviceId = jo.optString("deviceId");
        this.mobile = jo.optString("mobile");
        this.email = jo.optString("email");
        this.product = jo.optString("product");
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

    public static void sendRequest(UserVerificationRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(GuestLoginResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String mobileNumber, String deviceId, String Email, String Product, Context ctx, ServiceResponseListener listener) {
        UserVerificationRequest request = new UserVerificationRequest();
        request.setMobile(mobileNumber);
        request.setDeviceId(deviceId);
        request.setEmail(Email);
        request.setProduct(Product);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


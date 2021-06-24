package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class SetNewPasswordRequest implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String passType;
    private String newPassword;
    private String encryptionType;

    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "jsetPassword_mf";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("passType", this.passType);
        jo.put("newPassword", this.newPassword);
        jo.put("encryptionType", this.encryptionType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gscid = jo.optString("gscid");
        this.passType = jo.optString("passType");
        this.newPassword = jo.optString("newPassword");
        this.encryptionType = jo.optString("encryptionType");
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

    public static void sendRequest(SendForgotPasswordRequest request, String BaseURL, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), BaseURL, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, String BaseURL, Context ctx, ServiceResponseListener listener) {
//        GreekJSONRequest jsonRequest = null;
//        jsonRequest = new GreekJSONRequest(ctx, request);

        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(SendForgotPasswordResponse.class);

        if (BaseURL.isEmpty()) {
            jsonRequest.setService("Login", "jsetPassword_mf");

        } else {
            jsonRequest.setService("Login", "jsetPassword_mf", BaseURL);

        }
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String passType, String newPassword, String BaseURL, Context ctx, ServiceResponseListener listener) {
        SetNewPasswordRequest request = new SetNewPasswordRequest();
        request.setGscid(gscid);
        request.setPassType(passType);
        request.setNewPassword(Util.convertPassMd5(newPassword));
        request.setEncryptionType("1");

        try {
            sendRequest(request.toJSONObject(), BaseURL, ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

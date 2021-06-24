package com.acumengroup.greekmain.core.model.changepassword;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordRequest
        implements GreekRequestModel, GreekResponseModel {
    private String brokerid;
    private String passType;
    private String password;
    private String oldpassword;
    private String userCode;
    private String encryptionType;
    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "jchange_password";
    private static JSONObject echoParam = null;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldpassword() {
        return this.oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getbrokerid() {
        return brokerid;
    }

    public void setbrokerid(String brokerid) {
        this.brokerid = brokerid;
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
        jo.put("changeNewPassword", this.password);
        jo.put("oldPassword", this.oldpassword);
        jo.put("gscid", this.userCode);
        jo.put("passType", this.passType);
        jo.put("brokerid", this.brokerid);
        jo.put("encryptionType", this.encryptionType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.password = jo.optString("password");
        this.oldpassword = jo.optString("oldpassword");
        this.userCode = jo.optString("userCode");
        this.passType = jo.optString("passType");
        this.brokerid = jo.optString("brokerid");
        this.encryptionType = jo.optString("encryptionType");
        return this;
    }

    public static void sendRequest(ChangePasswordRequest request, String BaseURl, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), BaseURl, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, String BaseURl, Context ctx, ServiceResponseListener listener) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(ChangePasswordResponse.class);
        if (BaseURl.isEmpty()) {

            jsonRequest.setService("Login", SERVICE_NAME);
        } else {

            jsonRequest.setService("Login", SERVICE_NAME, BaseURl);
        }
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String oldpassword, String password, String userCode, String passType, String brokerid,
                                   String BaseURl,
                                   Context ctx, ServiceResponseListener listener) {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldpassword(Util.convertPassMd5(oldpassword));
        request.setPassword(Util.convertPassMd5(password));
        request.setUserCode(userCode);
        request.setPassType(passType);
        request.setbrokerid(brokerid);
        request.setEncryptionType("1");

        try {
            sendRequest(request.toJSONObject(), BaseURl, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



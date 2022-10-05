package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;

import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginValidationRequest
        implements GreekRequestModel, GreekResponseModel {
    private String pass_type;
    private String pan_dob;
    private String deviceID;
    private String deviceDetails;
    private String gscid;
    private String gcid;
    private String mpin;
    private String otp;
    private String pass;
    private String Tpass;
    private String userType;
    private String brokerid;
    private String deviceType;
    private String version_no;
    private String encryptionType;


    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "jloginNew";
    public static final String SERVICE_CREATEMPIN = "createMPIN";
    public static final String SERVICE_VALIDATEOTPFORMPIN = "jvalidate_otp_mf";
    public static final String SERVICE_VALIDATEMPIN = "validateMPIN";
    public static final String SERVICE_FORGETMPIN = "forgetMPIN";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }


    public String getPass_type() {
        return pass_type;
    }

    public void setPass_type(String pass_type) {
        this.pass_type = pass_type;
    }

    public String getPan_dob() {
        return this.pan_dob;
    }

    public void setPan_dob(String pan_dob) {
        this.pan_dob = pan_dob;
    }

    public String getGscid() {
        return this.gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTpass() {
        return Tpass;
    }

    public void setTpass(String tpass) {
        Tpass = tpass;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setBrokerid(String brokerid) {
        this.brokerid = brokerid;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
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
        jo.put("pan_dob", this.pan_dob);
        jo.put("deviceId", this.deviceID);
        jo.put("gscid", this.gscid);
        jo.put("gcid", this.gcid);
        jo.put("mpin", this.mpin);
        jo.put("otp", this.otp);
        jo.put("deviceDetails", this.deviceDetails);
        jo.put("deviceType", this.deviceType);
        jo.put("pass", this.pass);
        jo.put("transPass", this.Tpass);
        jo.put("userType", this.userType);
        jo.put("brokerid", this.brokerid);
        jo.put("passType", this.pass_type);
        jo.put("version_no", this.version_no);
        jo.put("encryptionType", this.encryptionType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.pan_dob = jo.optString("pan_dob");
        this.deviceID = jo.optString("deviceId");
        this.deviceDetails = jo.optString("deviceDetails");
        this.deviceType = jo.optString("deviceType");
        this.gscid = jo.optString("gscid");
        this.gcid = jo.optString("gcid");
        this.mpin = jo.optString("mpin");
        this.otp = jo.optString("otp");
        this.pass = jo.optString("pass");
        this.Tpass = jo.optString("transPass");
        this.userType = jo.optString("userType");
        this.pass_type = jo.optString("passType");
        this.version_no = jo.optString("version_no");
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

    public static void sendRequest(UserLoginValidationRequest request, Context ctx, ServiceResponseListener listener, String URL) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener, URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener, String URL) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(UserLoginValidationResponse.class);

        if (URL.isEmpty()) {
            jsonRequest.setService("Login", SERVICE_NAME);

        } else {
            jsonRequest.setService("Login", SERVICE_NAME, URL);

        }

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String pan_dob, String deviceID, String devicedetails, String devicetype, String password, String Tpassword, String userID,
                                   String userType, String brokerid, String passType, String version_no, String BASEURL,
                                   Context ctx, ServiceResponseListener listener) {
        UserLoginValidationRequest request = new UserLoginValidationRequest();
        request.setPan_dob(pan_dob);
        request.setDeviceID(deviceID);
        request.setDeviceDetails(devicedetails);
        request.setDeviceType(devicetype);
        request.setPass(Util.convertPassMd5(password));
        if(Tpassword.length()>0&&!Tpassword.equalsIgnoreCase("")){
            request.setTpass(Util.convertPassMd5(Tpassword)   );

        }else {
            request.setTpass(Tpassword);
        }
        request.setGscid(userID);
        request.setUserType(userType);
        request.setBrokerid(brokerid);
        request.setPass_type(passType);
        request.setVersion_no(version_no);
        request.setEncryptionType("1");
        try {
            sendRequest(request.toJSONObject(), ctx, listener, BASEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(String gcid, String mpin, String gscid, Context ctx, ServiceResponseHandler serviceResponseHandler, String url) {
        UserLoginValidationRequest request = new UserLoginValidationRequest();
        request.setGcid(gcid);
        request.setMpin(mpin);
        request.setGscid(gscid);

        try {
            sendRequestCreateMpin(request.toJSONObject(), ctx, serviceResponseHandler, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(String passTYPE, String gcid, String otp, String gscid, Context ctx, ServiceResponseHandler serviceResponseHandler, String url) {
        UserLoginValidationRequest request = new UserLoginValidationRequest();
        request.setPass_type(passTYPE);
        request.setOtp(otp);
        request.setGscid(gscid);

        try {
            sendRequestValidateOTPForMpin(request.toJSONObject(), ctx, serviceResponseHandler, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(String mpin, String gscid, Context ctx, ServiceResponseHandler serviceResponseHandler, String url) {
        UserLoginValidationRequest request = new UserLoginValidationRequest();
        request.setMpin(mpin);
        request.setGscid(gscid);

        try {
            sendRequestValidateMpin(request.toJSONObject(), ctx, serviceResponseHandler, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(String gscid, Context ctx, ServiceResponseHandler serviceResponseHandler, String url) {
        UserLoginValidationRequest request = new UserLoginValidationRequest();
        request.setGscid(gscid);

        try {
            sendRequestforgetMPIN(request.toJSONObject(), ctx, serviceResponseHandler, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestforgetMPIN(JSONObject request, Context ctx, ServiceResponseListener listener, String URL) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(UserLoginValidationResponse.class);

        if (URL.isEmpty()) {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_FORGETMPIN);

        } else {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_FORGETMPIN, URL);

        }

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestValidateMpin(JSONObject request, Context ctx, ServiceResponseListener listener, String URL) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(UserLoginValidationResponse.class);

        if (URL.isEmpty()) {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_VALIDATEMPIN);

        } else {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_VALIDATEMPIN, URL);

        }

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestValidateOTPForMpin(JSONObject request, Context ctx, ServiceResponseListener listener, String URL) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(UserLoginValidationResponse.class);

        if (URL.isEmpty()) {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_VALIDATEOTPFORMPIN);

        } else {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_VALIDATEOTPFORMPIN, URL);

        }

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestCreateMpin(JSONObject request, Context ctx, ServiceResponseListener listener, String URL) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(UserLoginValidationResponse.class);

        if (URL.isEmpty()) {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_CREATEMPIN);

        } else {
            jsonRequest.setService(SERVICE_GROUP, SERVICE_CREATEMPIN, URL);

        }

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

}
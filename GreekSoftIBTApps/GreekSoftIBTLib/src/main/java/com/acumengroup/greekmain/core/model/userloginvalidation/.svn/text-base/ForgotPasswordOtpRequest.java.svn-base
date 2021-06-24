package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordOtpRequest implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String passType;
    private String otp;


    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "jvalidate_otp_mf";
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("passType", this.passType);
        jo.put("otp", this.otp);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gscid = jo.optString("gscid");
        this.passType = jo.optString("passType");
        this.otp = jo.optString("otp");


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

    public static void sendRequest(UserLoginValidationRequest request, String url, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), url, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, String url, Context ctx, ServiceResponseListener listener) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(ForgotPasswordOTPResponse.class);

        if (url.isEmpty()) {
            jsonRequest.setService("Login", SERVICE_NAME);

        } else {
            jsonRequest.setService("Login", SERVICE_NAME, url);
        }

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String passtype, String otp,String url, Context ctx, ServiceResponseListener listener) {
        ForgotPasswordOtpRequest request = new ForgotPasswordOtpRequest();
        request.setGscid(gscid);
        request.setPassType(passtype);
        request.setOtp(otp);


        try {
            sendRequest(request.toJSONObject(),url, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequestMF(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);
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

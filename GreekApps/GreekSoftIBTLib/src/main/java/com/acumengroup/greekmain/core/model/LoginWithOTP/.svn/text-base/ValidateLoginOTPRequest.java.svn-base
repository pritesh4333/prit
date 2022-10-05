package com.acumengroup.greekmain.core.model.LoginWithOTP;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.STPOrder.STPOrderlRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.UserLoginValidationResponse;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ValidateLoginOTPRequest implements GreekRequestModel, GreekResponseModel {

    String gscid;
    String otp;
    String deviceId;
    String deviceType;
    String deviceDetails;
    private static JSONObject echoParam = null;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
    }



    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("otp", this.otp);
        jo.put("deviceId", this.deviceId);
        jo.put("deviceDetails", this.deviceDetails);
        jo.put("deviceType", this.deviceType);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.gscid = jo.optString("gscid");
        this.otp = jo.optString("otp");
        this.deviceId = jo.optString("deviceId");
        this.deviceDetails = jo.optString("deviceDetails");
        this.deviceType = jo.optString("deviceType");
        return this;
    }


    @Deprecated
    public static void sendRequest(String gscid, String otp, String baseURL, Context ctx,String deviceid,
                                   String devicedetails,String devicetype, ServiceResponseListener listener) {

        ValidateLoginOTPRequest request = new ValidateLoginOTPRequest();
        request.setGscid(gscid);
        request.setOtp(otp);
        request.setDeviceDetails(devicedetails);
        request.setDeviceId(deviceid);
        request.setDeviceType(devicetype);


        try {
            sendRequest(request.toJSONObject(), baseURL, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, String baseURL, Context ctx, ServiceResponseListener listener) {
//        GreekJSONRequest jsonRequest = null;
//        jsonRequest = new GreekJSONRequest(ctx, request);

        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        Log.e("STPOrderlRequest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(UserLoginValidationResponse.class);

        if (baseURL.isEmpty()) {
            jsonRequest.setService("logiwithOTP", "jValidateLoginOTP");

        } else {

            jsonRequest.setService("logiwithOTP", "jValidateLoginOTP", baseURL);

        }
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
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

    public static void sendRequest(STPOrderlRequest request, String baseURL, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), baseURL, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

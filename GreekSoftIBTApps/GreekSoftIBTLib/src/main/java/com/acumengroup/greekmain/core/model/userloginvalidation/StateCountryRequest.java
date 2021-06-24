package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class StateCountryRequest implements GreekRequestModel, GreekResponseModel {

    private String mobile;
    private String otp;

    public static final String SERVICE_GROUP = "getStateCountryCodeForMobile";
    public static final String SERVICE_NAME = "getStateCountryCodeForMobile";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public String getMobile() {

        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
//        jo.put("mobile", this.mobile);
//        jo.put("otp", this.otp);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

//        this.mobile = jo.optString("mobile");
//        this.otp = jo.optString("otp");

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

    public static void sendRequest(Context ctx, ServiceResponseListener listener) {
        try {

            StateCountryRequest request = new StateCountryRequest();

            sendRequest(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {

        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("StateCountryRequest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null)
        {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(StateCountryResponse.class);
        jsonRequest.setService(SERVICE_GROUP, SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

}

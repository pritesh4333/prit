package com.acumengroup.greekmain.core.model.PanValidation;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PanRequest implements GreekRequestModel, GreekResponseModel {
    String panNo;
    String dob;
    String mobile;
    String cUserType;
    String clientCode;

    private static JSONObject echoParam = null;

    public static final String SERVICE_NAME = "getConnectToMF";

    public String getcUserType() {
        return cUserType;
    }

    public void setcUserType(String cUserType) {
        this.cUserType = cUserType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("panNo", this.panNo);
        jo.put("dob", this.dob);
        jo.put("mobile", this.mobile);
        jo.put("cUserType", this.cUserType);
        jo.put("clientCode", this.clientCode);

        return jo;

    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.panNo = jo.optString("PANNO");
        this.dob = jo.optString("Dob");
        this.mobile = jo.optString("mobile");
        this.cUserType = jo.optString("cUserType");
        this.clientCode = jo.optString("clientCode");

        return this;
    }


    @Deprecated
    public static void sendRequestPanLogin(String clientCode, String panNumber, String dob, String mobile, String cUserType, Context ctx, ServiceResponseListener listener) {
        PanRequest request = new PanRequest();
        request.setClientCode(clientCode);
        request.setPanNo(panNumber);
        request.setDob(dob);
        request.setMobile(mobile);
        request.setcUserType(cUserType);

        try {
            sendRequestPanLogin(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequestPanLogin(JSONObject request, Context ctx, ServiceResponseListener listener) {

        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("ValidateGuest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(ValidatePanResponse.class);
        jsonRequest.setService("validatepan_login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String panNumber, String dob, String mobile, String cUserType, Context ctx, ServiceResponseListener listener) {
        PanRequest request = new PanRequest();
        request.setPanNo(panNumber);
        request.setDob(dob);
        request.setMobile(mobile);
        request.setcUserType(cUserType);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {

        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("ValidateGuest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(ValidatePanResponse.class);
        jsonRequest.setService("validatepan", SERVICE_NAME);

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

    public static void sendRequest(PanRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

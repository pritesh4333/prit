package com.acumengroup.greekmain.core.model.FiiDiiActivity;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class FpiInvestmentHistoryRequest implements GreekRequestModel, GreekResponseModel {

    private String startDate;
    private String endDate;

    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "getFIIDII_Investments_History";
    private static JSONObject echoParam = null;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("startDate", this.startDate);
        jo.put("endDate", this.endDate);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.startDate = jo.optString("startDate");
        this.endDate = jo.optString("endDate");

        return this;
    }

    public static void sendRequest(FpiDerivativeRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(FpiInvestmentResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String startDate,String endDate, Context ctx, ServiceResponseListener listener) {
        FpiInvestmentHistoryRequest request = new FpiInvestmentHistoryRequest();
        try {
            request.setStartDate(startDate);
            request.setEndDate(endDate);

            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


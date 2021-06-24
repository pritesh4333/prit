package com.acumengroup.greekmain.core.model.FiiDiiActivity;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class FpiInvestmentRequest implements GreekRequestModel, GreekResponseModel {

    public static final String SERVICE_GROUP = "Login";
    //public static final String SERVICE_NAME = "getFIIDII_Investments_Date";
    public static final String SERVICE_NAME = "getFIIDIIData";
    private static JSONObject echoParam = null;

    private String startDate;
    private String endDate;

    private String interval;
    private String type;
    private String topN;


    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopN() {
        return topN;
    }

    public void setTopN(String topN) {
        this.topN = topN;
    }

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
        jo.put("interval", this.interval);
        jo.put("type", this.type);
        jo.put("topN", this.topN);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.interval = jo.optString("interval");
        this.type = jo.optString("type");
        this.topN = jo.optString("topN");


        return this;
    }

    public static void sendRequest(FpiInvestmentRequest request, Context ctx, ServiceResponseListener listener) {
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
    public static void sendRequest(Context ctx, String interval, String type, String topn, ServiceResponseListener listener) {

        FpiInvestmentRequest request = new FpiInvestmentRequest();
        request.setInterval(interval);
        request.setType(type);
        request.setTopN(topn);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}




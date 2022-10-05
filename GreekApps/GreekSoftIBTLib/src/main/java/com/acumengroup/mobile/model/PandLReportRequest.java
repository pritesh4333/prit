package com.acumengroup.mobile.model;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PandLReportRequest implements GreekRequestModel, GreekResponseModel {
    String gscid;
    String startDate;
    String endDate;
    String date;
    private static JSONObject echoParam = null;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
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

    @Override
    public JSONObject toJSONObject() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("startDate", this.startDate);
        jo.put("endDate", this.endDate);
        jo.put("date", this.date);

        return jo;
    }


    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.gscid = jo.optString("gscid");
        this.startDate = jo.optString("startDate");
        this.endDate = jo.optString("endDate");
        this.date = jo.optString("date");
        return this;
    }


    @Deprecated
    public static void sendRequest(String gscid, String ServiceName, String startDate, String endDate, Context ctx, ServiceResponseListener listener) {
        PandLReportRequest request = new PandLReportRequest();
        request.setGscid(gscid);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setDate(endDate);

        try {
            sendRequest(request.toJSONObject(), ServiceName, ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, String ServiceName, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PandLReportResponse.class);
        jsonRequest.setService("reports_bajaj", ServiceName);
//        jsonRequest.setService("portfolio", "getProfitAndLossEQ_Bajaj");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

}

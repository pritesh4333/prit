package com.acumengroup.mobile.model;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SaudaReportRequest implements GreekRequestModel, GreekResponseModel {
    String gscid, startDate, endDate, exchange, segment;
    private static JSONObject echoParam = null;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
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
        jo.put("exchange", this.exchange);
        jo.put("segment", this.segment);

        return jo;
    }


    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.gscid = jo.optString("gscid");
        this.startDate = jo.optString("startDate");
        this.endDate = jo.optString("endDate");
        this.exchange = jo.optString("exchange");
        this.segment = jo.optString("segment");
        return this;
    }

    @Deprecated
    public static void sendRequest(String gscid, String startDate, String endDate, String serviceName, String segment, String exchange, Context ctx, ServiceResponseListener listener) {
        SaudaReportRequest request = new SaudaReportRequest();
        request.setGscid(gscid);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setSegment(segment);
        request.setExchange(exchange);

        try {
            sendRequest(request.toJSONObject(), serviceName, ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, String serviceName, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(SaudaReportResponse.class);
//        jsonRequest.setService("reports_bajaj", "getSaudaData_Bajaj");
        jsonRequest.setService("reports_bajaj", serviceName);
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

}

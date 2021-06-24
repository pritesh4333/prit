package com.acumengroup.greekmain.core.model.intradaychart;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 12-Aug-16.
 */
public class DailyChartRequest implements GreekRequestModel, GreekResponseModel {
    private String strToken;
    private String interval;
    private String date;
    private String startTime;
    private String endTime;
    private String time;
    private String noofdays;
    private static JSONObject echoParam = null;


    public String getNoofdays() {
        return noofdays;
    }

    public void setNoofdays(String noofdays) {
        this.noofdays = noofdays;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStrToken() {
        return this.strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public String getInterval() {
        return this.interval;
    }

    public void setInterval(String clientCode) {
        this.interval = clientCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("token", this.strToken);
        jo.put("interval", this.interval);
        jo.put("date", this.date);
        jo.put("startTime", this.startTime);
        jo.put("endTime", this.endTime);
        jo.put("time", this.time);
        jo.put("noofdays", this.noofdays);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.strToken = jo.optString("token");
        this.interval = jo.optString("interval");
        this.date = jo.optString("date");
        this.startTime = jo.optString("startTime");
        this.endTime = jo.optString("endTime");
        this.time = jo.optString("time");
        this.noofdays = jo.optString("noofdays");
        return this;
    }


    public static void sendRequest(DailyChartRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(String serviceType, JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
//        jsonRequest.setService("chart", "jhistorical_New");
        jsonRequest.setService("chart", serviceType);
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
        jsonRequest.setService("chart", "jhistorical_New");
//        jsonRequest.setService("chart", "jHistorical_ChartIQ");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestchartIQ(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
//        jsonRequest.setService("chart", "jhistorical_New");
        jsonRequest.setService("chart", "jHistorical_ChartIQ");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestForWeekly(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
        jsonRequest.setService("chart", "jWeekly_New");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestForWeeklychartIQ(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
        jsonRequest.setService("chart", "jWeekly_ChartIQ");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestForDaily(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
        jsonRequest.setService("chart", "jDaily_New");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }


    public static void sendRequestForDailychartIQ(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
        jsonRequest.setService("chart", "jDaily_ChartIQ");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }


    @Deprecated
    public static void sendRequest(String serviceType, String token, String interval, String date, String time, Context ctx, ServiceResponseListener listener) {
        DailyChartRequest request = new DailyChartRequest();
        request.setInterval(interval);
        request.setStrToken(token);
        request.setDate(date);
        request.setStartTime("0");
        request.setEndTime(date);
        request.setTime(time);
        try {
            sendRequest(serviceType, request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendRequest(String token, String interval, String date, String time, Context ctx, ServiceResponseListener listener) {
        DailyChartRequest request = new DailyChartRequest();
        request.setInterval(interval);
        request.setStrToken(token);
        request.setDate(date);
        request.setNoofdays(interval);
        request.setTime(time);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendRequestChart(String token, String interval, String date, String time,String noofdays, Context ctx, ServiceResponseListener listener) {
        DailyChartRequest request = new DailyChartRequest();
        request.setInterval(interval);
        request.setStrToken(token);
        request.setDate(date);
        request.setNoofdays(noofdays);
        request.setTime(time);
        try {
            sendRequestchartIQ(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendRequestForWeekly(String token, String interval, String date, String time, Context ctx, ServiceResponseListener listener) {

        DailyChartRequest request = new DailyChartRequest();
        request.setInterval(interval);
        request.setStrToken(token);
        request.setEndTime(date);
        request.setStartTime(time);
        try {
            sendRequestForWeekly(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
 @Deprecated
    public static void sendRequestForWeeklychart(String token, String interval, String date, String time, Context ctx, ServiceResponseListener listener) {

        DailyChartRequest request = new DailyChartRequest();
        request.setInterval(interval);
        request.setStrToken(token);
        request.setEndTime(date);
        request.setStartTime(time);
        try {
            sendRequestForWeeklychartIQ(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendRequestForDaily(String token, String interval, String date, String time, Context ctx, ServiceResponseListener listener) {

        DailyChartRequest request = new DailyChartRequest();
        request.setInterval(interval);
        request.setStrToken(token);
        request.setEndTime(date);
        request.setStartTime(time);
        try {
            sendRequestForDaily(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

 @Deprecated
    public static void sendRequestForDailychart(String token, String interval, String date, String time, Context ctx, ServiceResponseListener listener) {

        DailyChartRequest request = new DailyChartRequest();
        request.setInterval(interval);
        request.setStrToken(token);
        request.setEndTime(date);
        request.setStartTime(time);
        try {
            sendRequestForDailychartIQ(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

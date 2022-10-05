package com.acumengroup.mobile.alert;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sushant on 9/29/2016.
 */

public class AlertCreateRequest implements GreekRequestModel, GreekResponseModel {
    private String token;
    private String assetType;
    private String alertType;
    private String range;
    private String direction;
    private String gcid;
    private String gscid;
    private String symbol;
    private String exchange;


    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public static JSONObject getEchoParam() {
        return echoParam;
    }

    public static void setEchoParam(JSONObject echoParam) {
        AlertCreateRequest.echoParam = echoParam;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    private static JSONObject echoParam = null;

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("alertType", this.alertType);
        jo.put("token", this.token);
        jo.put("assetType", this.assetType);
        jo.put("range", this.range);
        jo.put("direction", this.direction);
        jo.put("gcid", this.gcid);
        jo.put("symbol", this.symbol);
        jo.put("exchange", this.exchange);
        jo.put("gscid", this.gscid);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.alertType = jo.optString("alertType");
        this.token = jo.optString("token");
        this.assetType = jo.optString("assetType");
        this.direction = jo.optString("direction");
        this.range = jo.optString("range");
        this.gcid = jo.optString("gcid");
        this.exchange = jo.optString("exchange");
        this.symbol = jo.optString("symbol");
        this.gscid = jo.optString("gscid");
        return this;
    }


    public static void sendRequest(AlertCreateRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(AlertCreateResponse.class);
        jsonRequest.setService("Markets", "add_alerts");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String symbol, String gscid, String exchange, String token, String assetType, String gcid, String alertType, String direction, String range, Context ctx, ServiceResponseListener listener) {
        AlertCreateRequest request = new AlertCreateRequest();
        request.setToken(token);
        request.setAssetType(assetType);
        request.setAlertType(alertType);
        request.setDirection(direction);
        request.setRange(range);
        request.setGcid(gcid);
        request.setGscid(gscid);
        request.setSymbol(symbol);
        request.setExchange(exchange);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

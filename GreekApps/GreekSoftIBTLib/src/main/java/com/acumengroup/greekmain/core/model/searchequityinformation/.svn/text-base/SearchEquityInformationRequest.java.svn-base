package com.acumengroup.greekmain.core.model.searchequityinformation;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchEquityInformationRequest
        implements GreekRequestModel, GreekResponseModel {
    private String scripName;
    private String instType;
    private String exchangeType;
    private static JSONObject echoParam = null;

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getScripName() {
        return this.scripName;
    }

    public void setScripName(String scripName) {
        this.scripName = scripName;
    }

    public String getInstType() {
        return this.instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("exchangeType", this.exchangeType);
        jo.put("scripName", this.scripName);
        jo.put("inst_type", this.instType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.exchangeType = jo.optString("exchangeType");
        this.scripName = jo.optString("scripName");
        this.instType = jo.optString("inst_type");
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

    public static void sendRequest(SearchEquityInformationRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(SearchEquityInformationResponse.class);
        jsonRequest.setService("Search", "getEquityDetails");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String exchangeType, String instType, String scripName, Context ctx, ServiceResponseListener listener) {
        SearchEquityInformationRequest request = new SearchEquityInformationRequest();
        request.setExchangeType(exchangeType);
        request.setInstType(instType);
        request.setScripName(scripName);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



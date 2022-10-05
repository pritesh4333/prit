package com.acumengroup.greekmain.core.model.marketssinglescrip;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 25-Jul-17.
 */

public class ContractInfoRequest implements GreekRequestModel, GreekResponseModel {
    private String streaming_type;
    private String token;
    private String assetType;
    private String exchange;
    private String service_type;
    private static JSONObject echoParam = null;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getService_type() {
        return this.service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("streaming_type", this.streaming_type);
        jo.put("token", this.token);
        jo.put("assetType", this.assetType);
        jo.put("exchange", this.exchange);
        jo.put("service_type", this.service_type);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.streaming_type = jo.optString("streaming_type");
        this.token = jo.optString("token");
        this.assetType = jo.optString("assetType");
        this.exchange = jo.optString("exchange");
        this.service_type = jo.optString("service_type");
        return this;
    }

    public static void sendRequest(ContractInfoRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(ContractInfoResponse.class);
        jsonRequest.setService("Markets", "getContractInfo");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String token, String assetType, String exchange, Context ctx, ServiceResponseListener listener) {
        ContractInfoRequest request = new ContractInfoRequest();
        request.setToken(token);
        request.setAssetType(assetType);
        request.setExchange(exchange);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


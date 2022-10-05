package com.acumengroup.greekmain.core.model.greekportfolio;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class GreekPortfolioRequest
        implements GreekRequestModel, GreekResponseModel {
    private String clientCode;
    private String assetType;
    private String gscid;


    public static final String SERVICE_GROUP = "Portfolio";
    public static final String SERVICE_NAME = "greekPortfolio";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ClientCode", this.clientCode);
        jo.put("assetType", this.assetType);
        jo.put("gscid", this.gscid);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.clientCode = jo.optString("ClientCode");
        this.assetType = jo.optString("assetType");
        this.gscid = jo.optString("gscid");
        return this;
    }


    public static void sendRequest(GreekPortfolioRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(GreekPortfolioResponse.class);
        jsonRequest.setService("Portfolio", "GreekPortfolio");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String clientCode, String gscid, Context ctx, ServiceResponseListener listener) {
        GreekPortfolioRequest request = new GreekPortfolioRequest();
        request.setClientCode(clientCode);
        request.setGscid(gscid);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



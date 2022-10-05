package com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchFnoExpiryStrikePriceRequest
        implements GreekRequestModel, GreekResponseModel {
    private String searchString;
    private String instGroup;
    private String exchangeType;
    private String assetType;
    private static JSONObject echoParam = null;

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getInstGroup() {
        return this.instGroup;
    }

    public void setInstGroup(String instGroup) {
        this.instGroup = instGroup;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("exchangeType", this.exchangeType);
        jo.put("assetType", this.assetType);
        jo.put("searchString", this.searchString);
        jo.put("instGroup", this.instGroup);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.exchangeType = jo.optString("exchangeType");
        this.assetType = jo.optString("assetType");
        this.searchString = jo.optString("searchString");
        this.instGroup = jo.optString("instGroup");
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

    public static void sendRequest(SearchFnoExpiryStrikePriceRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(SearchFnoExpiryStrikePriceResponse.class);
        jsonRequest.setService("Search", "getExpiryStrikePriceForScrip");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String exchangeType, String assetType, String instGroup, String searchString, Context ctx, ServiceResponseListener listener) {
        SearchFnoExpiryStrikePriceRequest request = new SearchFnoExpiryStrikePriceRequest();
        request.setExchangeType(exchangeType);
        request.setInstGroup(instGroup);
        request.setSearchString(searchString);
        request.setAssetType(assetType);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



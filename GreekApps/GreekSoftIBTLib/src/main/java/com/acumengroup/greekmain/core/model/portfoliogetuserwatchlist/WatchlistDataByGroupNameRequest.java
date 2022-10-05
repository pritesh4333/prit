package com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist;

import android.content.Context;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WatchlistDataByGroupNameRequest implements GreekRequestModel, GreekResponseModel {
    private String gcid;
    private String gscid;
    private String WatchListType;
    private String WatchListGroup;

    private static JSONObject echoParam = null;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getGcid() {
        return gcid;
    }


    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getWatchListType() {
        return WatchListType;
    }

    public void setWatchListType(String watchListType) {
        WatchListType = watchListType;
    }

    public String getWatchListGroup() {
        return WatchListGroup;
    }

    public void setWatchListGroup(String watchListGroup) {
        WatchListGroup = watchListGroup;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gcid", this.gcid);
        jo.put("gscid", this.gscid);
        jo.put("WatchListType", this.WatchListType);
        jo.put("WatchListGroup", this.WatchListGroup);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gcid = jo.optString("gcid");
        this.gscid = jo.optString("gscid");
        this.WatchListType = jo.optString("WatchListType");
        this.WatchListGroup = jo.optString("WatchListGroup");

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

    public static void sendRequest(WatchlistGroupRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
        if(AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
            jsonRequest.setService("Portfolio", "getWatchlistDataByGroupName_MobileV2_Redis");
        }else{
            jsonRequest.setService("Portfolio", "getWatchlistDataByGroupName_MobileV2");
        }
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String watchlistGroup, String watchlistType, Context ctx, ServiceResponseListener listener) {
        WatchlistDataByGroupNameRequest request = new WatchlistDataByGroupNameRequest();
//        request.setGcid(gscid);
        request.setGscid(gscid);
        request.setWatchListGroup(watchlistGroup);
        request.setWatchListType(watchlistType);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendMFRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
        jsonRequest.setService("Portfolio", "getWatchlistDataByGroupNameMF");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendMFRequest(String clientCode, String watchlistGroup, String watchlistType, Context ctx, ServiceResponseListener listener) {
        WatchlistDataByGroupNameRequest request = new WatchlistDataByGroupNameRequest();
        request.setGcid(clientCode);
        request.setWatchListGroup(watchlistGroup);
        request.setWatchListType(watchlistType);

        try {
            sendMFRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

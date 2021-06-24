package com.acumengroup.greekmain.core.model.portfolioeditwatchlist;

import android.content.Context;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 3/3/2016.
 */
public class RemoveSymbolFromGroupRequest implements GreekRequestModel, GreekResponseModel {
    private String strToken;
    private List<SymbolDetail> symbolDetails = new ArrayList();
    private String watchlistType;
    private String clientCode;
    private String gscid;
    private String groupName;
    private static JSONObject echoParam = null;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getStrToken() {
        return this.strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public List<SymbolDetail> getSymbolDetails() {
        return this.symbolDetails;
    }

    public void setSymbolDetails(List<SymbolDetail> symbolDetails) {
        this.symbolDetails = symbolDetails;
    }

    public String getWatchListType() {
        return this.watchlistType;
    }

    public void setWatchListType(String watchListType) {
        this.watchlistType = watchListType;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        //jo.put("strToken", this.strToken);
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.symbolDetails.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("symbolDetails", ja1);
        jo.put("watchlistType", this.watchlistType);
        jo.put("gcid", this.clientCode);
        jo.put("gscid", this.gscid);
        jo.put("groupName", this.groupName);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        //this.strToken = jo.optString("strToken");
        if (jo.has("symbolDetails")) {
            JSONArray ja1 = jo.getJSONArray("symbolDetails");
            this.symbolDetails = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    SymbolDetail data = new SymbolDetail();
                    data.fromJSON((JSONObject) o);
                    this.symbolDetails.add(data);
                } else {
                    this.symbolDetails.add((SymbolDetail) o);
                }
            }
        }
        this.watchlistType = jo.optString("watchlistType");
        this.clientCode = jo.optString("clientCode");
        this.gscid = jo.optString("gscid");
        this.groupName = jo.optString("groupName");
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

    public static void sendRequest(RemoveSymbolFromGroupRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(RemoveSymbolFromGroupResponse.class);
        if(AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
            jsonRequest.setService("Portfolio", "deleteScriptFromWatchlistGroupV2_Redis");
        }else{
            jsonRequest.setService("Portfolio", "deleteScriptFromWatchlistGroupV2");
        }
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String clientCode, String groupName, String strToken, List symbolDetails, String watchlistType, Context ctx, ServiceResponseListener listener) {
        RemoveSymbolFromGroupRequest request = new RemoveSymbolFromGroupRequest();
//        request.setClientCode(clientCode);
        request.setGscid(gscid);
        request.setGroupName(groupName);
        //request.setStrToken(strToken);
        request.setSymbolDetails(symbolDetails);
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
        jsonRequest.setResponseClass(RemoveSymbolFromGroupResponse.class);
        jsonRequest.setService("Portfolio", "deleteScriptFromWatchlistGroupMF");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendMFRequest(String clientCode, String groupName, String strToken, List symbolDetails, String watchlistType, Context ctx, ServiceResponseListener listener) {
        RemoveSymbolFromGroupRequest request = new RemoveSymbolFromGroupRequest();
        request.setClientCode(clientCode);
        request.setGroupName(groupName);
        //request.setStrToken(strToken);
        request.setSymbolDetails(symbolDetails);
        request.setWatchListType(watchlistType);
        try {
            sendMFRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

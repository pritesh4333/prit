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
public class AddSymbolToGroupRequest implements GreekRequestModel, GreekResponseModel {
    private String strToken;
    private List<SymbolDetail> symbolDetails = new ArrayList();
    private List<MFSymbolDetail> MFsymbolDetails = new ArrayList();
    private String watchListType;
    private String clientCode;
    private String groupName;
    private String gscid;
    private String seqNo;

    private static JSONObject echoParam = null;

    public List<MFSymbolDetail> getMFsymbolDetails() {
        return MFsymbolDetails;
    }

    public void setMFsymbolDetails(List<MFSymbolDetail> MFsymbolDetails) {
        this.MFsymbolDetails = MFsymbolDetails;
    }


    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

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
        return this.watchListType;
    }

    public void setWatchListType(String watchListType) {
        this.watchListType = watchListType;
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
        jo.put("strToken", this.strToken);
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
        JSONArray MFja1 = new JSONArray();
        Iterator MFiterator = this.MFsymbolDetails.iterator();
        while (MFiterator.hasNext()) {
            Object o = MFiterator.next();
            if ((o instanceof GreekRequestModel)) {
                MFja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                MFja1.put(o);
            }
        }
        jo.put("MFsymbolDetails", MFja1);
        jo.put("watchlistType", this.watchListType);
        jo.put("gcid", this.clientCode);
        jo.put("gscid", this.gscid);
        jo.put("groupName", this.groupName);
        jo.put("seqNo", this.seqNo);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.strToken = jo.optString("strToken");
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
        if (jo.has("MFsymbolDetails")) {
            JSONArray ja1 = jo.getJSONArray("MFsymbolDetails");
            this.MFsymbolDetails = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    MFSymbolDetail data = new MFSymbolDetail();
                    data.fromJSON((JSONObject) o);
                    this.MFsymbolDetails.add(data);
                } else {
                    this.MFsymbolDetails.add((MFSymbolDetail) o);
                }
            }
        }
        this.watchListType = jo.optString("watchlistType");
        this.clientCode = jo.optString("clientCode");
        this.groupName = jo.optString("groupName");
        this.gscid = jo.optString("gscid");
        this.seqNo = jo.optString("seqNo");
        return this;
    }

    public static void sendRequest(AddSymbolToGroupRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(AddSymbolToGroupResponse.class);
        if(AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
            jsonRequest.setService("Portfolio", "addNewScriptToWatchlistGroupV2_Redis");
        }else{
            jsonRequest.setService("Portfolio", "addNewScriptToWatchlistGroupV2");
        }
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestNewsData(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(AddSymbolToGroupResponse.class);
        jsonRequest.setService("Portfolio", "getNewsData");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String clientCode, String gscid, String groupName, String strToken, List symbolDetails,String seqNO, String watchListType, Context ctx, ServiceResponseListener listener) {
        AddSymbolToGroupRequest request = new AddSymbolToGroupRequest();
        request.setClientCode(clientCode);
        request.setGroupName(groupName);
        //request.setStrToken(strToken);
        request.setSymbolDetails(symbolDetails);
        request.setGscid(gscid);
        request.setWatchListType(watchListType);
        request.setSeqNo(seqNO);
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
        jsonRequest.setResponseClass(AddSymbolToGroupResponse.class);
        jsonRequest.setService("Portfolio", "addNewScriptToWatchlistGroupMF");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendMFRequest(String clientCode, String gscid, String groupName, String strToken, List symbolDetails, String watchListType, Context ctx, ServiceResponseListener listener) {
        AddSymbolToGroupRequest request = new AddSymbolToGroupRequest();
        request.setClientCode(clientCode);
        request.setGroupName(groupName);
        //request.setStrToken(strToken);
        request.setMFsymbolDetails(symbolDetails);
        request.setGscid(gscid);
        request.setWatchListType(watchListType);
        try {
            sendMFRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

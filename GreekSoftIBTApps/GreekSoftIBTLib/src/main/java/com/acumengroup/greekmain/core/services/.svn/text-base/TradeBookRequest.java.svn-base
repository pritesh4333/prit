package com.acumengroup.greekmain.core.services;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;
import com.loopj.android.http.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class TradeBookRequest implements GreekRequestModel, GreekResponseModel {
    private String sessionId;
    private String gcid;
    private String assetType;
    private String gscid;


    public TradeBookRequest() {
    }

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


    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public static void sendRequest(String url, String assetType, String gcid, String strToken, String gscid, Context ctx, ServiceResponseListener listener) {
        TradeBookRequest request = new TradeBookRequest();
        request.setGcid(gcid);
        request.setSessionId(strToken);
        request.setAssetType(assetType);
        request.setGscid(gscid);
        try {

            //GreekJSONRequest jsonRequest = new GreekJSONRequest(ctx, request.toJSONObject(),  url, "?SessionId="+strToken+"&gcid="+gcid);
            //jsonRequest.setResponseClass(TradeBookResponse.class);
            //ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);

            GreekJSONRequest jsonRequest = new GreekJSONRequest(ctx, request.toJSONObject());
            jsonRequest.setResponseClass(TradeBookResponse.class);
            jsonRequest.setService("Trade", url);
            ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String decodeBase64(String decodeData) {
        String decodedString = new String(android.util.Base64.decode(decodeData, android.util.Base64.DEFAULT));
        return decodedString;
    }

    public String encodeToBase64(String stringToEncode) {
        byte[] data = new byte[0];
        try {
            data = stringToEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encyrpt = Base64.encodeToString(data, Base64.NO_WRAP);
        return encyrpt;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("SessionId", this.sessionId);
        jo.put("gcid", this.gcid);
        jo.put("assetType", this.assetType);
        jo.put("gscid", this.gscid);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.sessionId = jo.optString("SessionId");
        this.gcid = jo.optString("gcid");
        this.assetType = jo.optString("assetType");
        this.gscid = jo.optString("gscid");
        return this;
    }
}

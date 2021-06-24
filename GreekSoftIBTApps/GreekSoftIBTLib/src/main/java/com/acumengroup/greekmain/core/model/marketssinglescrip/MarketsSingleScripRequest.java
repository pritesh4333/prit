package com.acumengroup.greekmain.core.model.marketssinglescrip;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MarketsSingleScripRequest
        implements GreekRequestModel, GreekResponseModel {
    private String streaming_type;
    private String DPType;
    private String ReqType;
    private String ReqFlag;
    private String ReqIdentifier;
    private String RU;
    private String BOID;
    private String PAN;
    private String ExID;
    private String deviceId;
    private JSONArray stockDetails;
    private String token, assetType, gscid, gcid;
    private String service_type;
    private String reqType;
    private String BOId;



    private static JSONObject echoParam = null;

    public String getreqType() {
        return reqType;
    }

    public void setreqType(String reqType) {
        this.reqType = reqType;
    }

    public String getBOId() {
        return BOId;
    }

    public void setBOId(String BOId) {
        this.BOId = BOId;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getReqType() {
        return ReqType;
    }

    public void setReqType(String reqType) {
        ReqType = reqType;
    }

    public String getReqFlag() {
        return ReqFlag;
    }

    public void setReqFlag(String reqFlag) {
        ReqFlag = reqFlag;
    }

    public String getReqIdentifier() {
        return ReqIdentifier;
    }

    public void setReqIdentifier(String reqIdentifier) {
        ReqIdentifier = reqIdentifier;
    }

    public String getRU() {
        return RU;
    }

    public void setRU(String RU) {
        this.RU = RU;
    }

    public String getBOID() {
        return BOID;
    }

    public void setBOID(String BOID) {
        this.BOID = BOID;
    }

    public String getExID() {
        return ExID;
    }

    public void setExID(String exID) {
        ExID = exID;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public JSONArray getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(JSONArray stockDetails) {
        this.stockDetails = stockDetails;
    }


    public String getDPType() {
        return DPType;
    }

    public void setDPType(String DPType) {
        this.DPType = DPType;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getStreaming_type() {
        return this.streaming_type;
    }

    public void setStreaming_type(String streaming_type) {
        this.streaming_type = streaming_type;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
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
        jo.put("service_type", this.service_type);
        jo.put("gscid", this.gscid);
        jo.put("gcid", this.gcid);
        jo.put("DPType", this.DPType);
        jo.put("ReqType", this.ReqType);
        jo.put("ReqFlag", this.ReqFlag);
        jo.put("ReqIdentifier", this.ReqIdentifier);
        jo.put("RU", this.RU);
        jo.put("BOID", this.BOID);
        jo.put("ExID", this.ExID);
        jo.put("deviceId", this.deviceId);
        jo.put("PAN", this.PAN);
        jo.put("BOId", this.BOId);
        jo.put("reqType", this.reqType);
        jo.put("stockDetails", this.stockDetails);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.streaming_type = jo.optString("streaming_type");
        this.token = jo.optString("token");
        this.assetType = jo.optString("assetType");
        this.service_type = jo.optString("service_type");
        this.gscid = jo.optString("gscid");
        this.gcid = jo.optString("gcid");
        this.DPType = jo.optString("DPType");
        this.ReqType = jo.optString("ReqType");
        this.ReqFlag = jo.optString("ReqFlag");
        this.ReqIdentifier = jo.optString("ReqIdentifier");
        this.RU = jo.optString("RU");
        this.BOID = jo.optString("BOID");
        this.ExID = jo.optString("ExID");
        this.deviceId = jo.optString("deviceId");
        this.PAN = jo.optString("PAN");
        this.stockDetails = jo.optJSONArray("stockDetails");
        this.BOId = jo.optString("BOId");
        this.reqType = jo.optString("reqType");
        return this;
    }

    public static void sendRequest(MarketsSingleScripRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestQty(JSONObject request, Context ctx, ServiceResponseListener listener) {

        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(MarketsSingleScripResponse.class);
        jsonRequest.setService("Markets", "getPrevQtyDetailsForToken");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }


    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(MarketsSingleScripResponse.class);
        jsonRequest.setService("Markets", "getQuoteForSingleSymbol_V2");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestCDSL(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(MarketsSingleScripResponse.class);
        jsonRequest.setService("Markets", "sendAuthorizationRequest");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestGenerateTPIN(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(MarketsSingleScripResponse.class);
        jsonRequest.setService("Markets", "generateTPinCDSL");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String token, String assetType, String gcid, Context ctx, ServiceResponseListener listener) {
        MarketsSingleScripRequest request = new MarketsSingleScripRequest();
        request.setToken(token);
        request.setAssetType(assetType);
        request.setGscid(gscid);
        request.setGcid(gcid);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendRequest(String gscid, String dPType, String reqType, String requestIdentifier, String RU,
                                   String BOID, String ExID, String deviceId, JSONArray jsonObject, Context ctx, ServiceResponseListener listener) {
        MarketsSingleScripRequest request = new MarketsSingleScripRequest();
        request.setGscid(gscid);
        request.setDPType(dPType);
        request.setReqType(reqType);
        request.setReqIdentifier(requestIdentifier);
        request.setRU(RU);
        request.setBOID(BOID);
        request.setExID(ExID);
        request.setDeviceId(deviceId);
        request.setStockDetails(jsonObject);


        try {
            sendRequestCDSL(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendRequestPledge(String gscid, String deviceId, String reqType,String boid,
                                         JSONArray jsonObject, Context ctx, ServiceResponseListener listener) {
        MarketsSingleScripRequest request = new MarketsSingleScripRequest();
        request.setGscid(gscid);
        request.setDeviceId(deviceId);
        request.setreqType(reqType);
        request.setBOId(boid);
        request.setStockDetails(jsonObject);
        try {
            sendEPledgeRequest(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void sendEPledgeRequest(JSONObject toJSONObject, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, toJSONObject);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        Log.e("epledgerequest",toJSONObject.toString());
        jsonRequest.setResponseClass(MarketsSingleScripResponse.class);
        jsonRequest.setService("Markets", "sendEPledgeRequest");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String dPType, String ReqFlag,String BOID,String PAN, String deviceId,
                                   Context ctx, ServiceResponseListener listener) {
        MarketsSingleScripRequest request = new MarketsSingleScripRequest();
        request.setGscid(gscid);
        request.setDPType(dPType);
        request.setReqFlag(ReqFlag);
        request.setBOID(BOID);
        request.setPAN(PAN);
        request.setDeviceId(deviceId);

        try {
            sendRequestGenerateTPIN(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendRequestQty(String gscid, String token, String assetType, String gcid, Context ctx, ServiceResponseListener listener) {
        MarketsSingleScripRequest request = new MarketsSingleScripRequest();
        request.setToken(token);
        request.setAssetType(assetType);
        request.setGscid(gscid);
        request.setGcid(gcid);
        try {
            sendRequestQty(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}



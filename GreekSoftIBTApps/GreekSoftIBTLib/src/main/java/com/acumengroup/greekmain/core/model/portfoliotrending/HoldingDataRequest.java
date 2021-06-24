package com.acumengroup.greekmain.core.model.portfoliotrending;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.PanValidation.PanRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class HoldingDataRequest implements GreekRequestModel, GreekResponseModel {

    private static JSONObject echoParam = null;

    public static final String SERVICE_GROUP = "porttfolio";
//    public static final String SERVICE_NAME = "getHoldingData";
    public static final String SERVICE_NAME = "getHoldingData_WithMTF";

    private String SessionId;
    private String gscid;

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("SessionId", this.SessionId);
        jo.put("gscid", this.gscid);


        return jo;

    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.SessionId = jo.optString("SessionId");
        this.gscid = jo.optString("gscid");
        return this;
    }


    @Deprecated
    public static void sendRequest(String sessionId, String gscid, Context ctx, ServiceResponseListener listener) {
        HoldingDataRequest request = new HoldingDataRequest();

        request.setSessionId(sessionId);
        request.setGscid(gscid);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("Bankdetails", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(HoldingDataResponse.class);
        jsonRequest.setService("portfolio", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
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

    public static void sendRequest(PanRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

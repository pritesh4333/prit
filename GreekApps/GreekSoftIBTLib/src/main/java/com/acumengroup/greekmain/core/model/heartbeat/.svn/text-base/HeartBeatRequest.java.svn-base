package com.acumengroup.greekmain.core.model.heartbeat;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 1/18/2016.
 */
public class HeartBeatRequest implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String sessionId;
    private static JSONObject echoParam = null;
    public static final String SERVICE_NAME = "jheartbeat";

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("sessionId", this.sessionId);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gscid = jo.optString("gscid");
        this.sessionId = jo.optString("sessionId");
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

    public static void sendRequest(HeartBeatRequest request, Context ctx, ServiceResponseListener listener) {
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
        //Log.d("rqt", request.toString());
        jsonRequest.setResponseClass(HeartBeatResponse.class);
        jsonRequest.setService(SERVICE_NAME, SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequest(String gscid, String sessionId, Context ctx, ServiceResponseListener listener) {
        HeartBeatRequest request = new HeartBeatRequest();
        request.setGscid(gscid);
        request.setSessionId(sessionId);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

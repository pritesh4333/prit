package com.acumengroup.greekmain.core.model.Notifications;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.PanValidation.PanRequest;
import com.acumengroup.greekmain.core.model.recommonDisplay.RecommDisplayRequest;
import com.acumengroup.greekmain.core.model.recommonDisplay.RecommDisplayResponse;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationRequest implements GreekRequestModel, GreekResponseModel {


    private static JSONObject echoParam = null;

    public static final String SERVICE_GROUP = "getNotifications";
    public static final String SERVICE_NAME = "getNotifications";

    private String gscid;

    public String getGscid() {
        return gscid;
    }
    public void setGscid(String gscid) {
        this.gscid = gscid;
    }


    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.gscid = jo.optString("gscid");
        return this;
    }


    @Deprecated
    public static void sendRequest(String gscid, Context ctx, ServiceResponseListener listener) {

        RecommDisplayRequest request = new RecommDisplayRequest();
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

        Log.e("ValidateGuest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(RecommDisplayResponse.class);
        jsonRequest.setService("addpersonaldetails", SERVICE_NAME);

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


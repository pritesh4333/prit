package com.acumengroup.greekmain.core.model.FiiDiiActivity;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class FpiDerivativeRequest implements GreekRequestModel, GreekResponseModel {

    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "getFIIDII_Derivative";
    private static JSONObject echoParam = null;

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        return this;
    }

    public static void sendRequest(FpiDerivativeRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(FpiDerivativeResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest( Context ctx, ServiceResponseListener listener) {
        FpiDerivativeRequest request = new FpiDerivativeRequest();
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


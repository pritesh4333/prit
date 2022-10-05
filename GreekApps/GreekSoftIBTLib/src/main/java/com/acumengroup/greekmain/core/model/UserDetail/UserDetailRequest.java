package com.acumengroup.greekmain.core.model.UserDetail;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailRequest implements GreekRequestModel, GreekResponseModel {
    private String password;
    private String clientCode;

    private static JSONObject echoParam = null;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("clientCode", this.clientCode);
        jo.put("password", this.password);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.clientCode = jo.optString("clientCode");
        this.password = jo.optString("password");

        return this;
    }

    public static void sendRequest(UserDetailRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(ValidateGuestResponse.class);
        jsonRequest.setService("addLoginDetailsMF", "addLoginDetailsMF");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String clientCode, String password, Context ctx, ServiceResponseListener listener) {
        UserDetailRequest request = new UserDetailRequest();
        request.setClientCode(clientCode);
        request.setPassword(password);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestFlag(JSONObject request,Context ctx, ServiceResponseListener listener)
    {
        GreekJSONRequest jsonRequest = null;

        jsonRequest = new GreekJSONRequest(ctx,request);

        if (echoParam != null)
        {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(ValidateGuestResponse.class);
        jsonRequest.setService("addDetailsMF", "getConnectToMF");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequestFlags(Context ctx, ServiceResponseListener listener)
    {
        UserDetailRequest request = new UserDetailRequest();

        try {
            sendRequestFlag(request.toJSONObject(),ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

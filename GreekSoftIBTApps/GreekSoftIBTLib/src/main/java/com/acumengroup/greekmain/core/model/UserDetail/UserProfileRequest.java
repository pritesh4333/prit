package com.acumengroup.greekmain.core.model.UserDetail;

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

public class UserProfileRequest implements GreekRequestModel, GreekResponseModel {
    private String clientCode;
    private String gscid;
    private static JSONObject echoParam = null;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }





    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("clientCode", this.clientCode);
        jo.put("gscid", this.gscid);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.clientCode = jo.optString("clientCode");
        this.gscid = jo.optString("gscid");

        return this;
    }

    @Deprecated
    public static void sendRequest(String clientCode, String gscid, Context ctx, ServiceResponseListener listener) {
        UserProfileRequest request = new UserProfileRequest();
        request.setClientCode(clientCode);
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
        jsonRequest.setResponseClass(UserProfileResponse.class);
        jsonRequest.setService("userprofile_bajaj", "getProfileDetails_Bajaj");

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


/*    @Deprecated
    public static void sendRequest(String clientCode, String gscid, Context ctx, ServiceResponseListener listener) {
        UserProfileRequest request = new UserProfileRequest();
        request.setClientCode(clientCode);
        request.setGscid(gscid);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(UserDetailRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/






    /*public static void sendRequestFlag(JSONObject request,Context ctx, ServiceResponseListener listener)
    {
        GreekJSONRequest jsonRequest = null;

        jsonRequest = new GreekJSONRequest(ctx,request);

        if (echoParam != null)
        {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(UserProfileResponse.class);
        jsonRequest.setService("addDetailsMF", "getConnectToMF");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }*/


}

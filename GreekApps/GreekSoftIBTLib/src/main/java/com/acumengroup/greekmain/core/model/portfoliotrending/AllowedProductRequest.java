package com.acumengroup.greekmain.core.model.portfoliotrending;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AllowedProductRequest implements GreekRequestModel, GreekResponseModel
{

    public static final String SERVICE_NAME = "getAllowedProduct";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public JSONObject toJSONObject()
            throws JSONException
    {
        JSONObject jo = new JSONObject();

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException
    {

        return this;
    }

    public static void addEchoParam(String key, String value)
    {
        try
        {
            if (echoParam == null) {
                echoParam = new JSONObject();
            }
            echoParam.put(key, value);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendRequest(AllowedProductRequest request, Context ctx, ServiceResponseListener listener)
    {
        try
        {
            sendRequest(request.toJSONObject(), ctx, listener);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener)
    {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        if (echoParam != null)
        {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(AllowedProductResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest( Context ctx, ServiceResponseListener listener)
    {
        AllowedProductRequest request = new AllowedProductRequest();
        try
        {
            sendRequest(request.toJSONObject(), ctx, listener);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}

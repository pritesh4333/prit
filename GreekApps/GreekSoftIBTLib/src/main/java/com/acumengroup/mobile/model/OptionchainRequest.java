package com.acumengroup.mobile.model;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class OptionchainRequest implements GreekRequestModel, GreekResponseModel {


    private static JSONObject echoParam = null;

    public static final String SERVICE_GROUP = "addpersonaldetails";
    public static final String SERVICE_NAME = "OptionChainRequest";

    String cExchange;
    String cSymbol;
    String dFromStrike;
    String dToStrike;
    String cExpiry;


    public String getcExchange() {
        return cExchange;
    }

    public void setcExchange(String cExchange) {
        this.cExchange = cExchange;
    }

    public String getcSymbol() {
        return cSymbol;
    }

    public void setcSymbol(String cSymbol) {
        this.cSymbol = cSymbol;
    }

    public String getdFromStrike() {
        return dFromStrike;
    }

    public void setdFromStrike(String dFromStrike) {
        this.dFromStrike = dFromStrike;
    }

    public String getdToStrike() {
        return dToStrike;
    }

    public void setdToStrike(String dToStrike) {
        this.dToStrike = dToStrike;
    }

    public String getcExpiry() {
        return cExpiry;
    }

    public void setcExpiry(String cExpiry) {
        this.cExpiry = cExpiry;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("cExchange", this.cExchange);
        jo.put("cSymbol", this.cSymbol);
        jo.put("dFromStrike", this.dFromStrike);
        jo.put("dToStrike", this.dToStrike);
        jo.put("cExpiry", this.cExpiry);

        return jo;
    }

    /*String cExchange;
    String cSymbol;
    String dFromStrike;
    String dToStrike;
    String cExpiry;*/

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.cExchange = jo.optString("cExchange");
        this.cSymbol = jo.optString("cSymbol");
        this.dFromStrike = jo.optString("dFromStrike");
        this.dToStrike = jo.optString("dToStrike");
        this.cExpiry = jo.optString("cExpiry");

        return this;
    }


    @Deprecated
    public static void sendRequest(String exchange, String symbol, String expiry, String fromStrike, String toStrike, Context ctx, ServiceResponseListener listener) {
        //Log.e("getConnectTo---- 2", pan);
        OptionchainRequest request = new OptionchainRequest();
        request.setcExchange(exchange);
        request.setcSymbol(symbol);
        request.setcExpiry(expiry);
        request.setdFromStrike(fromStrike);
        request.setdToStrike(toStrike);

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
        jsonRequest.setResponseClass(OptionChainResponse.class);
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

    public static void sendRequest(OptionchainRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

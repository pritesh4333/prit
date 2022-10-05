package com.acumengroup.greekmain.core.model.streamerbroadcast;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamerBroadcastRequest
        implements GreekRequestModel, GreekResponseModel {
    private List<Symbol> symbols = new ArrayList();
    public static final String SERVICE_GROUP = "Streamer";
    public static final String SERVICE_NAME = "Broadcast";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public List<Symbol> getSymbols() {
        return this.symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.symbols.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("symbols", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("symbols")) {
            JSONArray ja1 = jo.getJSONArray("symbols");
            this.symbols = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    Symbol data = new Symbol();
                    data.fromJSON((JSONObject) o);
                    this.symbols.add(data);
                } else {
                    this.symbols.add((Symbol) o);
                }
            }
        }
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

    public static void sendRequest(StreamerBroadcastRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(StreamerBroadcastResponse.class);
        jsonRequest.setService("Streamer", "Broadcast");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(List symbols, Context ctx, ServiceResponseListener listener) {
        StreamerBroadcastRequest request = new StreamerBroadcastRequest();
        request.setSymbols(symbols);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



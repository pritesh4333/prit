package com.acumengroup.greekmain.core.model.marketsmultiplescrip;

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

public class MarketsMultipleScripRequest
        implements GreekRequestModel, GreekResponseModel {
    private List<SymbolList> symbolList = new ArrayList();
    private static JSONObject echoParam = null;

    public List<SymbolList> getSymbolList() {
        return this.symbolList;
    }

    public void setSymbolList(List<SymbolList> symbolList) {
        this.symbolList = symbolList;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.symbolList.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("symbolList", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("symbolList")) {
            JSONArray ja1 = jo.getJSONArray("symbolList");
            this.symbolList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    SymbolList data = new SymbolList();
                    data.fromJSON((JSONObject) o);
                    this.symbolList.add(data);
                } else {
                    this.symbolList.add((SymbolList) o);
                }
            }
        }
        return this;
    }

    public static void sendRequest(MarketsMultipleScripRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(MarketsMultipleScripResponse.class);
        jsonRequest.setService("Markets", "getQuoteForMultipleSymbols");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(List symbolList, Context ctx, ServiceResponseListener listener) {
        MarketsMultipleScripRequest request = new MarketsMultipleScripRequest();
        request.setSymbolList(symbolList);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



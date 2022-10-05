package com.acumengroup.mobile.model;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsDetailsDataRequest   implements GreekRequestModel, GreekResponseModel {

    String type,topN;
    private static JSONObject echoParam = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopN() {
        return topN;
    }

    public void setTopN(String topN) {
        this.topN = topN;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {


        JSONObject jo = new JSONObject();
        jo.put("type", this.type);
        jo.put("topN", this.topN);


        return jo;


    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.type = jo.optString("type");
        this.topN = jo.optString("topN");

        return this;
    }

    @Deprecated
    public static void sendRequest(String type, String topN,  Context ctx, ServiceResponseListener listener) {
        NewsDetailsDataRequest request = new NewsDetailsDataRequest();
        request.setType(type);
        request.setTopN(topN);

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
        jsonRequest.setResponseClass(MarketsSingleScripResponse.class);
        jsonRequest.setService("portfolio", "getNewsData");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

}

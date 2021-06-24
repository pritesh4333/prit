package com.acumengroup.greekmain.core.model;


import com.acumengroup.greekmain.core.model.streamerbroadcast.MarketStatusResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketStatusPostResponse implements GreekRequestModel, GreekResponseModel {
    private List<MarketStatusResponse> statusResponse = new ArrayList<>();

    public List<MarketStatusResponse> getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(List<MarketStatusResponse> statusResponse) {
        this.statusResponse = statusResponse;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("MarketStatus")) {
            JSONArray ja1 = jo.getJSONArray("MarketStatus");
            this.statusResponse = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    MarketStatusResponse data = new MarketStatusResponse();
                    data.fromJSON((JSONObject) o);
                    this.statusResponse.add(data);
                } else {
                    this.statusResponse.add((MarketStatusResponse) o);
                }
            }
        }
        return this;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.statusResponse.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("MarketStatus", ja1);
        return jo;
    }
}





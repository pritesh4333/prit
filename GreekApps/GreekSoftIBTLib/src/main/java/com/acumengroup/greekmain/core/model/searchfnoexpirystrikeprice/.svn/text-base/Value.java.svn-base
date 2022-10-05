package com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Value
        implements GreekRequestModel, GreekResponseModel {
    private String expiry;
    private List<TradeInfo> tradeInfo = new ArrayList();

    public String getExpiry() {
        return this.expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public List<TradeInfo> getTradeInfo() {
        return this.tradeInfo;
    }

    public void setTradeInfo(List<TradeInfo> tradeInfo) {
        this.tradeInfo = tradeInfo;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("expiry", this.expiry);
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.tradeInfo.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("tradeInfo", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.expiry = jo.optString("expiry");
        if (jo.has("tradeInfo")) {
            JSONArray ja1 = jo.getJSONArray("tradeInfo");
            this.tradeInfo = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    TradeInfo data = new TradeInfo();
                    data.fromJSON((JSONObject) o);
                    this.tradeInfo.add(data);
                } else {
                    this.tradeInfo.add((TradeInfo) o);
                }
            }
        }
        return this;
    }
}



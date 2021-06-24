package com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Expiry_strickPrice
        implements GreekRequestModel, GreekResponseModel {
    private List<Scrip> scrip = new ArrayList();
    private String exchange;

    public List<Scrip> getScrip() {
        return this.scrip;
    }

    public void setScrip(List<Scrip> scrip) {
        this.scrip = scrip;
    }

    public String getExchange() {
        return this.exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.scrip.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("scrip", ja1);
        jo.put("exchange", this.exchange);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("scrip")) {
            JSONArray ja1 = jo.getJSONArray("scrip");
            this.scrip = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    Scrip data = new Scrip();
                    data.fromJSON((JSONObject) o);
                    this.scrip.add(data);
                } else {
                    this.scrip.add((Scrip) o);
                }
            }
        }
        this.exchange = jo.optString("exchange");
        return this;
    }
}



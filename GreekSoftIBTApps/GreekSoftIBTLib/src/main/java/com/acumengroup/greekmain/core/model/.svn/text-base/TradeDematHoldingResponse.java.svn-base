package com.acumengroup.greekmain.core.model;

/**
 * Created by ankitnagda on 10/16/2015.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TradeDematHoldingResponse implements GreekRequestModel, GreekResponseModel {

    private List<DematHolding> dematHolding = new ArrayList();

    public TradeDematHoldingResponse() {
    }

    public List<DematHolding> getDematHolding() {
        return this.dematHolding;
    }

    public void setDmatHoldings(List<DematHolding> dematHolding) {
        this.dematHolding = dematHolding;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.dematHolding.iterator();

        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof GreekRequestModel) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }

        jo.put("dmatHoldings", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("dmatHoldings")) {
            JSONArray ja1 = jo.getJSONArray("dmatHoldings");
            this.dematHolding = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    DematHolding data = new DematHolding();
                    data.fromJSON((JSONObject) o);
                    this.dematHolding.add(data);
                } else {
                    this.dematHolding.add((DematHolding) o);
                }
            }
        }

        return this;
    }
}


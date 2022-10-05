package com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scrip
        implements GreekRequestModel, GreekResponseModel {
    private List<Value> value = new ArrayList();
    private String instrumentType;

    public List<Value> getValue() {
        return this.value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public String getInstrumentType() {
        return this.instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.value.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("value", ja1);
        jo.put("instrumentType", this.instrumentType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("value")) {
            JSONArray ja1 = jo.getJSONArray("value");
            this.value = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    Value data = new Value();
                    data.fromJSON((JSONObject) o);
                    this.value.add(data);
                } else {
                    this.value.add((Value) o);
                }
            }
        }
        this.instrumentType = jo.optString("instrumentType");
        return this;
    }
}



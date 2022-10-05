package com.acumengroup.greekmain.core.model.marketsindianindices;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketsIndianIndicesResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<IndianIndice> indianIndices = new ArrayList();

    public List<IndianIndice> getIndianIndices() {
        return this.indianIndices;
    }

    public void setIndianIndices(List<IndianIndice> indianIndices) {
        this.indianIndices = indianIndices;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.indianIndices.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("indianIndicesData", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("indianIndicesData")) {
            JSONArray ja1 = jo.getJSONArray("indianIndicesData");
            this.indianIndices = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    IndianIndice data = new IndianIndice();
                    data.fromJSON((JSONObject) o);
                    this.indianIndices.add(data);
                } else {
                    this.indianIndices.add((IndianIndice) o);
                }
            }
        }
        return this;
    }
}



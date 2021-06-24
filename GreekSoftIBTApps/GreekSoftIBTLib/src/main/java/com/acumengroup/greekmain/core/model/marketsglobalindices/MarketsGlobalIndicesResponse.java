package com.acumengroup.greekmain.core.model.marketsglobalindices;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketsGlobalIndicesResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<GlobalIndice> globalIndices = new ArrayList();

    public List<GlobalIndice> getGlobalIndices() {
        return this.globalIndices;
    }

    public void setGlobalIndices(List<GlobalIndice> globalIndices) {
        this.globalIndices = globalIndices;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.globalIndices.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("globalIndicesData", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("globalIndicesData")) {
            JSONArray ja1 = jo.getJSONArray("globalIndicesData");
            this.globalIndices = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    GlobalIndice data = new GlobalIndice();
                    data.fromJSON((JSONObject) o);
                    this.globalIndices.add(data);
                } else {
                    this.globalIndices.add((GlobalIndice) o);
                }
            }
        }
        return this;
    }
}



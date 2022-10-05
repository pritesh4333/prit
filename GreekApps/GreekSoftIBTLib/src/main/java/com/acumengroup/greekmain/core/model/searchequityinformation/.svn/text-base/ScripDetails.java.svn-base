package com.acumengroup.greekmain.core.model.searchequityinformation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScripDetails
        implements GreekRequestModel, GreekResponseModel {
    private List<EquityInfo> equityInfo = new ArrayList();

    public List<EquityInfo> getEquityInfo() {
        return this.equityInfo;
    }

    public void setEquityInfo(List<EquityInfo> equityInfo) {
        this.equityInfo = equityInfo;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.equityInfo.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("equityInfo", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("equityInfo")) {
            JSONArray ja1 = jo.getJSONArray("equityInfo");
            this.equityInfo = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    EquityInfo data = new EquityInfo();
                    data.fromJSON((JSONObject) o);
                    this.equityInfo.add(data);
                } else {
                    this.equityInfo.add((EquityInfo) o);
                }
            }
        }
        return this;
    }
}



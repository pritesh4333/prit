package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LedgerReportResponse implements GreekRequestModel, GreekResponseModel {
    private List<LedgerData> arrayList = new ArrayList();


    public List<LedgerData> getSaudaResponse() {
        return arrayList;
    }

    public void setSaudaResponse(List<LedgerData> saudaResponse) {
        this.arrayList = saudaResponse;
    }


    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();

        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.arrayList.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        return  jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        if(jo.optJSONArray("data") !=null) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.arrayList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    LedgerData data = new LedgerData();
                    data.fromJSON((JSONObject) o);
                    this.arrayList.add(data);
                } else {
                    this.arrayList.add((LedgerData) o);
                }
            }
        }


        return this;
    }

}
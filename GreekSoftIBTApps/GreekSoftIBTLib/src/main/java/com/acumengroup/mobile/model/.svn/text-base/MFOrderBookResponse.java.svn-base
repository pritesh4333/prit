package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.tradeorderbook.MFOrderBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MFOrderBookResponse {
    private List<MFOrderBook> newsdataList = new ArrayList<>();

    public MFOrderBookResponse() {
    }

    public List<MFOrderBook> getNewsdataList() {
        return newsdataList;
    }

    public void setNewsdataList(List<MFOrderBook> newsdataList) {
        this.newsdataList = newsdataList;
    }

    public List<MFOrderBook> fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.newsdataList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); ++i) {
                JSONObject o = (JSONObject) ja1.get(i);
                MFOrderBook data = new MFOrderBook();
                data.fromJSON(o);
                this.newsdataList.add(data);
            }
        }
        return this.newsdataList;
    }
}

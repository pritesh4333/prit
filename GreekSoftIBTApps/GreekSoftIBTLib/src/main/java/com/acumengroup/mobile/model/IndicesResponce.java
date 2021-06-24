package com.acumengroup.mobile.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndicesResponce {

    private List<IndicesModel> indicesModelList = new ArrayList<>();

    public IndicesResponce() {
    }

    public List<IndicesModel> getOrderBookModelList() {
        return indicesModelList;
    }

    public void setOrderBookModelList(List<IndicesModel> orderBookModelList) {
        this.indicesModelList = orderBookModelList;
    }

    public List<IndicesModel> fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.indicesModelList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); ++i) {
                JSONObject o = (JSONObject) ja1.get(i);
                IndicesModel data = new IndicesModel();
                data.fromJSON(o);
                this.indicesModelList.add(data);
            }
        }
        return this.indicesModelList;
    }

}

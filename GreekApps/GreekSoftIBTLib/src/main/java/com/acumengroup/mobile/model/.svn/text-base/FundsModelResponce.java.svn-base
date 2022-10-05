package com.acumengroup.mobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FundsModelResponce {

    private List<FundsModel> FundsModelModelList = new ArrayList<>();

    public FundsModelResponce() {
    }

    public List<FundsModel> getOrderBookModelList() {
        return FundsModelModelList;
    }

    public void setOrderBookModelList(List<FundsModel> bankNamesModelList) {
        this.FundsModelModelList = bankNamesModelList;
    }

    public List<FundsModel> fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.FundsModelModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                JSONObject o = (JSONObject) ja1.get(i);
                FundsModel data = new FundsModel();
                data.fromJSON(o);
                this.FundsModelModelList.add(data);
            }
        }
        return this.FundsModelModelList;
    }
}

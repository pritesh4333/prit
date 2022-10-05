package com.acumengroup.mobile.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BankNamesResponce {

    private List<BankName> bankNamesModelList = new ArrayList<>();

    public BankNamesResponce() {
    }
    public List<BankName> getOrderBookModelList() {
        return bankNamesModelList;
    }

    public void setOrderBookModelList(List<BankName> bankNamesModelList) {
        this.bankNamesModelList = bankNamesModelList;
    }

    public List<BankName> fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.bankNamesModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                JSONObject o = (JSONObject) ja1.get(i);
                BankName data = new BankName();
                data.fromJSON(o);
                this.bankNamesModelList.add(data);
            }
        }
        return this.bankNamesModelList;
    }
}

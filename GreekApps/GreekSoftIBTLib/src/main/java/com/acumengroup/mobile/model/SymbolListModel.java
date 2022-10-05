package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SymbolListModel {

    private List<SymbolList> symbolModelList = new ArrayList<>();

    public SymbolListModel() {
    }

    public List<SymbolList> getSymbolModelList() {
        return symbolModelList;
    }

    public void setSymbolModelList(List<SymbolList> symbolModelList) {
        this.symbolModelList = symbolModelList;
    }

    public List<SymbolList> fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.symbolModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                JSONObject o = (JSONObject) ja1.get(i);
                SymbolList data = new SymbolList();
                data.fromJSON(o);
                this.symbolModelList.add(data);
            }
        }
        return this.symbolModelList;
    }
}


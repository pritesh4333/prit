package com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetUserwatchlist
        implements GreekRequestModel, GreekResponseModel {
    private String watchlistName;
    private String watchtype;
    private String defaultFlag;

    private List<SymbolList> symbolList = new ArrayList();

    public String getWatchlistName() {
        return this.watchlistName;
    }

    public void setWatchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
    }

    public String getWatchtype() {
        return this.watchtype;
    }

    public void setWatchtype(String watchtype) {
        this.watchtype = watchtype;
    }

    public List<SymbolList> getSymbolList() {
        return this.symbolList;
    }

    public void setSymbolList(List<SymbolList> symbolList) {
        this.symbolList = symbolList;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("watchlistName", this.watchlistName);
        jo.put("watchtype", this.watchtype);
        jo.put("default", this.defaultFlag);
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.symbolList.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("symbolList", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.watchlistName = jo.optString("watchlistName");
        this.watchtype = jo.optString("watchtype");
        this.defaultFlag = jo.optString("default");
        if (jo.has("symbolList")) {
            JSONArray ja1 = jo.getJSONArray("symbolList");
            this.symbolList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    SymbolList data = new SymbolList();
                    data.fromJSON((JSONObject) o);
                    this.symbolList.add(data);
                } else {
                    this.symbolList.add((SymbolList) o);
                }
            }
        }
        return this;
    }
}



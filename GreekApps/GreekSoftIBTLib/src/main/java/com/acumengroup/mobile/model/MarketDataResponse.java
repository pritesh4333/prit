package com.acumengroup.mobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arcadia
 */
public class MarketDataResponse {
    private List<MarketDataModel> marketDataModelList = new ArrayList<>();

    public MarketDataResponse() {
    }

    public List<MarketDataModel> getMarketDataModelList() {
        return marketDataModelList;
    }

    public void setMarketDataModelList(List<MarketDataModel> marketDataModelList) {
        this.marketDataModelList = marketDataModelList;
    }

    public void fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.marketDataModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MarketDataModel data = new MarketDataModel();
                    data.fromJSON((JSONObject) o);
                    this.marketDataModelList.add(data);
                } else {
                    this.marketDataModelList.add((MarketDataModel) o);
                }
            }
        }
    }
}

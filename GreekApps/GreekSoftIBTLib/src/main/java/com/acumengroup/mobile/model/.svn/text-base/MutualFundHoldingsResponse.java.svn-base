package com.acumengroup.mobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MutualFundHoldingsResponse {

    private List<MutualFundHoldingsModel> mutualFundHoldingsModelList = new ArrayList<>();

    public MutualFundHoldingsResponse() {
    }

    public List<MutualFundHoldingsModel> getMarketDataModelList() {
        return mutualFundHoldingsModelList;
    }

    public List<MutualFundHoldingsModel> fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.mutualFundHoldingsModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MutualFundHoldingsModel data = new MutualFundHoldingsModel();
                    data.fromJSON((JSONObject) o);
                    this.mutualFundHoldingsModelList.add(data);
                } else {
                    this.mutualFundHoldingsModelList.add((MutualFundHoldingsModel) o);
                }
            }
        }
        return this.mutualFundHoldingsModelList;
    }
}

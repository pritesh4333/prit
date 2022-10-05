package com.acumengroup.mobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RollOverDataResponse{
    private ArrayList<MarketDataModel> gainerRollerDataModelList = new ArrayList<>();
    private ArrayList<MarketDataModel> looserRollerDataModelList = new ArrayList<>();

    public RollOverDataResponse() {
    }


    public ArrayList<MarketDataModel> getGainerRollerDataModelList() {
        return gainerRollerDataModelList;
    }

    public void setGainerRollerDataModelList(ArrayList<MarketDataModel> gainerRollerDataModelList) {
        this.gainerRollerDataModelList = gainerRollerDataModelList;
    }

    public ArrayList<MarketDataModel> getLooserRollerDataModelList() {
        return looserRollerDataModelList;
    }

    public void setLooserRollerDataModelList(ArrayList<MarketDataModel> looserRollerDataModelList) {
        this.looserRollerDataModelList = looserRollerDataModelList;
    }


    public void fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("gainer")) {
            JSONArray ja1 = jo.getJSONArray("gainer");
            this.gainerRollerDataModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MarketDataModel data = new MarketDataModel();
                    data.fromJSON((JSONObject) o);
                    this.gainerRollerDataModelList.add(data);
                } else {
                    this.gainerRollerDataModelList.add((MarketDataModel) o);
                }
            }
        }

        if (jo.has("looser")) {
            JSONArray ja1 = jo.getJSONArray("looser");
            this.looserRollerDataModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MarketDataModel data = new MarketDataModel();
                    data.fromJSON((JSONObject) o);
                    this.looserRollerDataModelList.add(data);
                } else {
                    this.looserRollerDataModelList.add((MarketDataModel) o);
                }
            }
        }
    }
}

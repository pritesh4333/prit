package com.acumengroup.mobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OIAnalysisDataResponse {
    private ArrayList<MarketDataModel> longBuildupDataModelList = new ArrayList<>();
    private ArrayList<MarketDataModel> shortBuildupDataModelList = new ArrayList<>();
    private ArrayList<MarketDataModel> longUnwindingDataModelList = new ArrayList<>();
    private ArrayList<MarketDataModel> shortCoveringDataModelList = new ArrayList<>();

    public OIAnalysisDataResponse() {
    }

    public ArrayList<MarketDataModel> getLongBuildupDataModelList() {
        return longBuildupDataModelList;
    }

    public void setLongBuildupDataModelList(ArrayList<MarketDataModel> longBuildupDataModelList) {
        this.longBuildupDataModelList = longBuildupDataModelList;
    }

    public ArrayList<MarketDataModel> getShortBuildupDataModelList() {
        return shortBuildupDataModelList;
    }

    public void setShortBuildupDataModelList(ArrayList<MarketDataModel> shortBuildupDataModelList) {
        this.shortBuildupDataModelList = shortBuildupDataModelList;
    }

    public ArrayList<MarketDataModel> getLongUnwindingDataModelList() {
        return longUnwindingDataModelList;
    }

    public void setLongUnwindingDataModelList(ArrayList<MarketDataModel> longUnwindingDataModelList) {
        this.longUnwindingDataModelList = longUnwindingDataModelList;
    }

    public ArrayList<MarketDataModel> getShortCoveringDataModelList() {
        return shortCoveringDataModelList;
    }

    public void setShortCoveringDataModelList(ArrayList<MarketDataModel> shortCoveringDataModelList) {
        this.shortCoveringDataModelList = shortCoveringDataModelList;
    }

    public void fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("long_build_up")) {
            JSONArray ja1 = jo.getJSONArray("long_build_up");
            this.longBuildupDataModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MarketDataModel data = new MarketDataModel();
                    data.fromJSON((JSONObject) o);
                    this.longBuildupDataModelList.add(data);
                } else {
                    this.longBuildupDataModelList.add((MarketDataModel) o);
                }
            }
        }

        if (jo.has("short_build_up")) {
            JSONArray ja1 = jo.getJSONArray("short_build_up");
            this.shortBuildupDataModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MarketDataModel data = new MarketDataModel();
                    data.fromJSON((JSONObject) o);
                    this.shortBuildupDataModelList.add(data);
                } else {
                    this.shortBuildupDataModelList.add((MarketDataModel) o);
                }
            }
        }

        if (jo.has("long_unwinding")) {
            JSONArray ja1 = jo.getJSONArray("long_unwinding");
            this.longUnwindingDataModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MarketDataModel data = new MarketDataModel();
                    data.fromJSON((JSONObject) o);
                    this.longUnwindingDataModelList.add(data);
                } else {
                    this.longUnwindingDataModelList.add((MarketDataModel) o);
                }
            }
        }

        if (jo.has("short_covering")) {
            JSONArray ja1 = jo.getJSONArray("short_covering");
            this.shortCoveringDataModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    MarketDataModel data = new MarketDataModel();
                    data.fromJSON((JSONObject) o);
                    this.shortCoveringDataModelList.add(data);
                } else {
                    this.shortCoveringDataModelList.add((MarketDataModel) o);
                }
            }
        }
    }
}

package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
//import com.bfsl.core.model.tradeorderbook.NewsDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsDetailsDataResponse implements GreekRequestModel, GreekResponseModel {

    private List<NewsDataModel> newsModelList = new ArrayList<>();

    public NewsDetailsDataResponse() {

    }

    private String errorCode;


    public List<NewsDataModel> getlist() {
        return this.newsModelList;
    }

    public void setlist(List<NewsDataModel> getUserwatchlist) {

        this.newsModelList = getUserwatchlist;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {

        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.newsModelList.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("ErrorCode", errorCode);
        jo.put("data", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        if (jo.has("ErrorCode")) {
            this.errorCode = jo.getString("ErrorCode");
        }
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.newsModelList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    NewsDataModel data = new NewsDataModel();
//                    data.fromJSON((JSONObject) o);
                    this.newsModelList.add(data);
                } else {
                    this.newsModelList.add((NewsDataModel) o);
                }
            }
        }
        return this;
    }
}

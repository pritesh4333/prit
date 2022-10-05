package com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PortfolioGetUserWatchListResponse
        implements GreekRequestModel, GreekResponseModel {

    private String errorCode;
    private List<GetUserwatchlist> getUserwatchlist = new ArrayList();

    public List<GetUserwatchlist> getGetUserwatchlist() {
        return this.getUserwatchlist;
    }

    public void setGetUserwatchlist(List<GetUserwatchlist> getUserwatchlist) {
        this.getUserwatchlist = getUserwatchlist;
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
        Iterator iterator = this.getUserwatchlist.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("ErrorCode", errorCode);
        jo.put("getwatchlistdata", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("ErrorCode")) {
            this.errorCode = jo.getString("ErrorCode");
        }
        if (jo.has("getwatchlistdata")) {
            JSONArray ja1 = jo.getJSONArray("getwatchlistdata");
            this.getUserwatchlist = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    GetUserwatchlist data = new GetUserwatchlist();
                    data.fromJSON((JSONObject) o);
                    this.getUserwatchlist.add(data);
                } else {
                    this.getUserwatchlist.add((GetUserwatchlist) o);
                }
            }
        }
        return this;
    }
}



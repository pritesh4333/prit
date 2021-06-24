package com.acumengroup.greekmain.core.model.searchmultistockdetails;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchMultiStockDetailsResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<StockDetail> stockDetails = new ArrayList();
    private String ErrorCode;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public List<StockDetail> getStockDetails() {
        return this.stockDetails;
    }

    public void setStockDetails(List<StockDetail> stockDetails) {
        this.stockDetails = stockDetails;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.stockDetails.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("stockDetails", ja1);
        jo.put("ErrorCode", this.ErrorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");

        if (jo.has("stockDetails")) {
            JSONArray ja1 = jo.getJSONArray("stockDetails");
            this.stockDetails = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    StockDetail data = new StockDetail();
                    data.fromJSON((JSONObject) o);
                    this.stockDetails.add(data);
                } else {
                    this.stockDetails.add((StockDetail) o);
                }
            }
        }
        return this;
    }
}



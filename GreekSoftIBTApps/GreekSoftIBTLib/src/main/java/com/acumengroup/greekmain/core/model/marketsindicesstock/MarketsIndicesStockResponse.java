package com.acumengroup.greekmain.core.model.marketsindicesstock;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketsIndicesStockResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<IndicesStock> indicesStock = new ArrayList();
    private String ErrorCode;

    public List<IndicesStock> getIndicesStock() {
        return this.indicesStock;
    }

    public void setIndicesStock(List<IndicesStock> indicesStock) {
        this.indicesStock = indicesStock;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.indicesStock.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("indicesStockData", ja1);
        jo.put("ErrorCode", ErrorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");
        if (jo.has("indicesStockData")) {
            JSONArray ja1 = jo.getJSONArray("indicesStockData");
            this.indicesStock = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    IndicesStock data = new IndicesStock();
                    data.fromJSON((JSONObject) o);
                    this.indicesStock.add(data);
                } else {
                    this.indicesStock.add((IndicesStock) o);
                }
            }
        }
        return this;
    }
}



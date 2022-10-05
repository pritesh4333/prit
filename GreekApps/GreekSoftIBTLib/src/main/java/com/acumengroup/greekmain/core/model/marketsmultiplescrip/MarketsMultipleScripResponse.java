package com.acumengroup.greekmain.core.model.marketsmultiplescrip;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketsMultipleScripResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<QuoteList> quoteList = new ArrayList();
    private String ErrorCode;

    public List<QuoteList> getQuoteList() {
        return this.quoteList;
    }

    public void setQuoteList(List<QuoteList> quoteList) {
        this.quoteList = quoteList;
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
        Iterator iterator = this.quoteList.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("quoteList", ja1);
        jo.put("ErrorCode", this.ErrorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");

        if (jo.has("quoteList")) {
            JSONArray ja1 = jo.getJSONArray("quoteList");
            this.quoteList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    QuoteList data = new QuoteList();
                    data.fromJSON((JSONObject) o);
                    this.quoteList.add(data);
                } else {
                    this.quoteList.add((QuoteList) o);
                }
            }
        }
        return this;
    }
}



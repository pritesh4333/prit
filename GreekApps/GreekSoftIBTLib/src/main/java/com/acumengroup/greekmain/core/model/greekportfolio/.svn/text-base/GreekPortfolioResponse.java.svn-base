package com.acumengroup.greekmain.core.model.greekportfolio;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GreekPortfolioResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<GreekPortfoliosummary> greekPortfoliosummary = new ArrayList();
    private GainInfo gainInfo;
    private String ErrorCode;


    public List<GreekPortfoliosummary> getGreekPortfoliosummary() {
        return this.greekPortfoliosummary;
    }

    public void setGreekPortfoliosummary(List<GreekPortfoliosummary> greekPortfoliosummary) {
        this.greekPortfoliosummary = greekPortfoliosummary;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public GainInfo getGainInfo() {
        return this.gainInfo;
    }

    public void setGainInfo(GainInfo gainInfo) {
        this.gainInfo = gainInfo;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.greekPortfoliosummary.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("ErrorCode", ErrorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.ErrorCode = jo.optString("ErrorCode");
        if (jo.has("gainInfo")) {
            this.gainInfo = new GainInfo();
            this.gainInfo.fromJSON(jo.getJSONObject("gainInfo"));
        }
        return this;
    }
}



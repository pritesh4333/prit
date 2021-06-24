package com.acumengroup.greekmain.core.model.marketsglobalindices;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class GlobalIndice
        implements GreekRequestModel, GreekResponseModel {
    private String date;
    private String indicesName;
    private String indexID;
    private String perChange;
    private String close;
    private String country;
    private String change;
    private String prevClose;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIndicesName() {
        return this.indicesName;
    }

    public void setIndicesName(String indicesName) {
        this.indicesName = indicesName;
    }

    public String getIndexID() {
        return this.indexID;
    }

    public void setIndexID(String indexID) {
        this.indexID = indexID;
    }

    public String getPerChange() {
        return this.perChange;
    }

    public void setPerChange(String perChange) {
        this.perChange = perChange;
    }

    public String getClose() {
        return this.close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getChange() {
        return this.change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPrevClose() {
        return this.prevClose;
    }

    public void setPrevClose(String prevClose) {
        this.prevClose = prevClose;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("date", this.date);
        jo.put("indicesName", this.indicesName);
        jo.put("indexID", this.indexID);
        jo.put("perChange", this.perChange);
        jo.put("close", this.close);
        jo.put("country", this.country);
        jo.put("change", this.change);
        jo.put("prevClose", this.prevClose);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.date = jo.optString("date");
        this.indicesName = jo.optString("indicesName");
        this.indexID = jo.optString("indexID");
        this.perChange = jo.optString("perChange");
        this.close = jo.optString("close");
        this.country = jo.optString("country");
        this.change = jo.optString("change");
        this.prevClose = jo.optString("prevClose");
        return this;
    }
}



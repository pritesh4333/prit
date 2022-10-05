package com.acumengroup.mobile.model;

import org.json.JSONObject;

/**
 * Created by user on 09-Aug-16.
 */
public class chartModel {
    //equity
    private String lToken;
    private String lOpen;
    private String lHigh;
    private String lLow;
    private String lDate;
    private String lTradedVol;
    private String lATP;
    private String lClose;

    public String getlToken() {
        return lToken;
    }

    public void setlToken(String lToken) {
        this.lToken = lToken;
    }

    public String getlOpen() {
        return lOpen;
    }

    public void setlOpen(String lOpen) {
        this.lOpen = lOpen;
    }

    public String getlHigh() {
        return lHigh;
    }

    public void setlHigh(String lHigh) {
        this.lHigh = lHigh;
    }

    public String getlLow() {
        return lLow;
    }

    public void setlLow(String lLow) {
        this.lLow = lLow;
    }

    public String getlDate() {
        return lDate;
    }

    public void setlDate(String lDate) {
        this.lDate = lDate;
    }

    public String getlTradeVol() {
        return lTradedVol;
    }

    public void setlTradeVol(String lTradeVol) {
        this.lTradedVol = lTradeVol;
    }

    public String getlATP() {
        return lATP;
    }

    public void setlATP(String lATP) {
        this.lATP = lATP;
    }

    public String getlClose() {
        return lClose;
    }

    public void setlClose(String lClose) {
        this.lClose = lClose;
    }

    public void fromJSONObject(JSONObject data) {
        //Equity
        this.lToken = data.optString("lToken");
        this.lOpen = data.optString("lOpen");
        this.lHigh = data.optString("lHigh");
        this.lLow = data.optString("lLow");
        this.lDate = data.optString("lDate");
        this.lATP = data.optString("lATP");
        this.lTradedVol = data.optString("lTradedVol");
        this.lClose = data.optString("lClose");

    }

}

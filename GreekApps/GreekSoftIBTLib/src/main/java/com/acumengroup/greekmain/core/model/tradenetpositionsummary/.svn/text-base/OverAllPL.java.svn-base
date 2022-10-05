package com.acumengroup.greekmain.core.model.tradenetpositionsummary;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class OverAllPL
        implements GreekRequestModel, GreekResponseModel {
    private String settMTM;
    private String grossTurnOver;
    private String total_days;
    private String total_days_bf;
    private String booked_days_bf;
    private String prov_days_bf;
    private String netTurnOver;
    private String dayMTM;
    private String booked_days;
    private String prov_days;

    public String getSettMTM() {
        return this.settMTM;
    }

    public void setSettMTM(String settMTM) {
        this.settMTM = settMTM;
    }

    public String getGrossTurnOver() {
        return this.grossTurnOver;
    }

    public void setGrossTurnOver(String grossTurnOver) {
        this.grossTurnOver = grossTurnOver;
    }

    public String getTotal_days() {
        return this.total_days;
    }

    public void setTotal_days(String total_days) {
        this.total_days = total_days;
    }

    public String getTotal_days_bf() {
        return this.total_days_bf;
    }

    public void setTotal_days_bf(String total_days_bf) {
        this.total_days_bf = total_days_bf;
    }

    public String getBooked_days_bf() {
        return this.booked_days_bf;
    }

    public void setBooked_days_bf(String booked_days_bf) {
        this.booked_days_bf = booked_days_bf;
    }

    public String getProv_days_bf() {
        return this.prov_days_bf;
    }

    public void setProv_days_bf(String prov_days_bf) {
        this.prov_days_bf = prov_days_bf;
    }

    public String getNetTurnOver() {
        return this.netTurnOver;
    }

    public void setNetTurnOver(String netTurnOver) {
        this.netTurnOver = netTurnOver;
    }

    public String getDayMTM() {
        return this.dayMTM;
    }

    public void setDayMTM(String dayMTM) {
        this.dayMTM = dayMTM;
    }

    public String getBooked_days() {
        return this.booked_days;
    }

    public void setBooked_days(String booked_days) {
        this.booked_days = booked_days;
    }

    public String getProv_days() {
        return this.prov_days;
    }

    public void setProv_days(String prov_days) {
        this.prov_days = prov_days;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("settMTM", this.settMTM);
        jo.put("grossTurnOver", this.grossTurnOver);
        jo.put("total_days", this.total_days);
        jo.put("total_days_bf", this.total_days_bf);
        jo.put("booked_days_bf", this.booked_days_bf);
        jo.put("prov_days_bf", this.prov_days_bf);
        jo.put("netTurnOver", this.netTurnOver);
        jo.put("dayMTM", this.dayMTM);
        jo.put("booked_days", this.booked_days);
        jo.put("prov_days", this.prov_days);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.settMTM = jo.optString("settMTM");
        this.grossTurnOver = jo.optString("grossTurnOver");
        this.total_days = jo.optString("total_days");
        this.total_days_bf = jo.optString("total_days_bf");
        this.booked_days_bf = jo.optString("booked_days_bf");
        this.prov_days_bf = jo.optString("prov_days_bf");
        this.netTurnOver = jo.optString("netTurnOver");
        this.dayMTM = jo.optString("dayMTM");
        this.booked_days = jo.optString("booked_days");
        this.prov_days = jo.optString("prov_days");
        return this;
    }
}



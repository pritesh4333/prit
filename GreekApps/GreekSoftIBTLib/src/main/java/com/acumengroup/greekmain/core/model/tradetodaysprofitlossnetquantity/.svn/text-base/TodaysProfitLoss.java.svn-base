package com.acumengroup.greekmain.core.model.tradetodaysprofitlossnetquantity;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class TodaysProfitLoss
        implements GreekRequestModel, GreekResponseModel {
    private String netTurnOver;
    private String booked_days;
    private String grossTurnOver;
    private String dayMTM;
    private String total_days;
    private String prov_days;

    public String getNetTurnOver() {
        return this.netTurnOver;
    }

    public void setNetTurnOver(String netTurnOver) {
        this.netTurnOver = netTurnOver;
    }

    public String getBooked_days() {
        return this.booked_days;
    }

    public void setBooked_days(String booked_days) {
        this.booked_days = booked_days;
    }

    public String getGrossTurnOver() {
        return this.grossTurnOver;
    }

    public void setGrossTurnOver(String grossTurnOver) {
        this.grossTurnOver = grossTurnOver;
    }

    public String getDayMTM() {
        return this.dayMTM;
    }

    public void setDayMTM(String dayMTM) {
        this.dayMTM = dayMTM;
    }

    public String getTotal_days() {
        return this.total_days;
    }

    public void setTotal_days(String total_days) {
        this.total_days = total_days;
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
        jo.put("netTurnOver", this.netTurnOver);
        jo.put("booked_days", this.booked_days);
        jo.put("grossTurnOver", this.grossTurnOver);
        jo.put("dayMTM", this.dayMTM);
        jo.put("total_days", this.total_days);
        jo.put("prov_days", this.prov_days);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.netTurnOver = jo.optString("netTurnOver");
        this.booked_days = jo.optString("booked_days");
        this.grossTurnOver = jo.optString("grossTurnOver");
        this.dayMTM = jo.optString("dayMTM");
        this.total_days = jo.optString("total_days");
        this.prov_days = jo.optString("prov_days");
        return this;
    }
}



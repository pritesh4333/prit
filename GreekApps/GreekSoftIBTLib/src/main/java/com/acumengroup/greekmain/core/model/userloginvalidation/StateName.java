package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class StateName
        implements GreekRequestModel, GreekResponseModel {
    private String sno;
    private String description;
    private String header;
    private String reportDate;

    public String getSno() {
        return this.sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getReportDate() {
        return this.reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("sno", this.sno);
        jo.put("description", this.description);
        jo.put("header", this.header);
        jo.put("reportDate", this.reportDate);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.sno = jo.optString("sno");
        this.description = jo.optString("description");
        this.header = jo.optString("header");
        this.reportDate = jo.optString("reportDate");
        return this;
    }
}



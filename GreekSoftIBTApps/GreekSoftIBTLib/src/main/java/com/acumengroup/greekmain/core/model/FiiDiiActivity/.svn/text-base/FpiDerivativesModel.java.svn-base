package com.acumengroup.greekmain.core.model.FiiDiiActivity;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class FpiDerivativesModel implements GreekRequestModel, GreekResponseModel {


    private String reportingDate;
    private String stock_futures;
    private String stock_options;
    private String index_futures;
    private String index_options;

    public String getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(String reportingDate) {
        this.reportingDate = reportingDate;
    }

    public String getStock_futures() {
        return stock_futures;
    }

    public void setStock_futures(String stock_futures) {
        this.stock_futures = stock_futures;
    }

    public String getStock_options() {
        return stock_options;
    }

    public void setStock_options(String stock_options) {
        this.stock_options = stock_options;
    }

    public String getIndex_futures() {
        return index_futures;
    }

    public void setIndex_futures(String index_futures) {
        this.index_futures = index_futures;
    }

    public String getIndex_options() {
        return index_options;
    }

    public void setIndex_options(String index_options) {
        this.index_options = index_options;
    }


    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("stock_futures", this.stock_futures);
        jo.put("stock_options", this.stock_options);
        jo.put("index_futures", this.index_futures);
        jo.put("index_options", this.index_options);
        jo.put("reportingDate", this.reportingDate);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.stock_futures = jo.optString("stock_futures");
        this.stock_options = jo.optString("stock_options");
        this.index_futures = jo.optString("index_futures");
        this.index_options = jo.optString("index_options");
        this.reportingDate = jo.optString("reportingDate");
        return this;
    }
}
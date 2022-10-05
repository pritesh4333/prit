package com.acumengroup.mobile.alert;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sushant on 9/30/2016.
 */

public class ShowAlertDetail implements GreekRequestModel, GreekResponseModel {

    private String errorCode;
    private String ruleNo;
    private String gtoken;
    private String alert_type;
    private String range;
    private String is_executed;
    private String direction_flag;
    private String start_datetime;
    private String end_datetime;
    private String last_updated_time;
    private String symbol;
    private String exchange;
    private String description;
    private String assetType;
    private String expiry_date;
    private String strike_price;
    private String series_instname;


    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getStrike_price() {
        return strike_price;
    }

    public void setStrike_price(String strike_price) {
        this.strike_price = strike_price;
    }

    public String getSeries_instname() {
        return series_instname;
    }

    public void setSeries_instname(String series_instname) {
        this.series_instname = series_instname;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    private Object details;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public String getAlert_type() {
        return alert_type;
    }

    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getIs_executed() {
        return is_executed;
    }

    public void setIs_executed(String is_executed) {
        this.is_executed = is_executed;
    }

    public String getDirection_flag() {
        return direction_flag;
    }

    public void setDirection_flag(String direction_flag) {
        this.direction_flag = direction_flag;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

    public String getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(String last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public String getGtoken() {
        return gtoken;
    }

    public void setGtoken(String gtoken) {
        this.gtoken = gtoken;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        //jo.put("ErrorCode", this.errorCode);
        jo.put("rule_no", this.ruleNo);
        jo.put("gtoken", this.gtoken);
        jo.put("alert_type", this.alert_type);
        jo.put("range", this.range);
        jo.put("is_executed", this.is_executed);
        jo.put("direction_flag", this.direction_flag);
        jo.put("start_datetime", this.start_datetime);
        jo.put("end_datetime", this.end_datetime);
        jo.put("last_updated_time", this.last_updated_time);
        jo.put("symbol", this.symbol);
        jo.put("exchange", this.exchange);
        jo.put("description", this.description);
        jo.put("assetType", this.assetType);
        jo.put("series_instname", this.series_instname);
        jo.put("expiry_date", this.expiry_date);
        jo.put("strike_price", this.strike_price);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        //this.errorCode = jo.optString("ErrorCode");
        this.ruleNo = jo.optString("rule_no");
        this.gtoken = jo.optString("gtoken");
        this.alert_type = jo.optString("alert_type");
        this.range = jo.optString("range");
        this.is_executed = jo.optString("is_executed");
        this.direction_flag = jo.optString("direction_flag");
        this.start_datetime = jo.optString("start_datetime");
        this.end_datetime = jo.optString("end_datetime");
        this.last_updated_time = jo.optString("last_updated_time");
        this.symbol = jo.optString("symbol");
        this.exchange = jo.optString("exchange");
        this.description = jo.optString("description");
        this.assetType = jo.optString("assetType");
        this.expiry_date = jo.optString("expiry_date");
        this.strike_price = jo.optString("strike_price");
        this.series_instname = jo.optString("series_instname");

        return this;
    }
}

package com.acumengroup.mobile.alert;

/**
 * Created by User on 9/30/2016.
 */

public class AlertData {

    private Object details;
    private String ruleNo;
    private String lourToken;
    private String alertType;
    private String range;
    private String isExecuted;
    private String directionFlag;
    private String startDateTime;
    private String endDateTime;
    private String lastUpadatedTime;
    private String symbol;
    private String exchange;
    private String description;
    private String assetType;
    private String series_instname;

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public String getLourToken() {
        return lourToken;
    }

    public void setLourToken(String lourToken) {
        this.lourToken = lourToken;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getIsExecuted() {
        return isExecuted;
    }

    public void setIsExecuted(String isExecuted) {
        this.isExecuted = isExecuted;
    }

    public String getDirectionFlag() {
        return directionFlag;
    }

    public void setDirectionFlag(String directionFlag) {
        this.directionFlag = directionFlag;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLastUpadatedTime() {
        return lastUpadatedTime;
    }

    public void setLastUpadatedTime(String lastUpadatedTime) {
        this.lastUpadatedTime = lastUpadatedTime;
    }

    public String getSeries_instname() {
        return series_instname;
    }

    public void setSeries_instname(String series_instname) {
        this.series_instname = series_instname;
    }
}

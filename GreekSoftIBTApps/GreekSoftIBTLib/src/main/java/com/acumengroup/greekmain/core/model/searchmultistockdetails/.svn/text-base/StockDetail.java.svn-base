package com.acumengroup.greekmain.core.model.searchmultistockdetails;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class StockDetail
        implements GreekRequestModel, GreekResponseModel {
    private String uniqueID;
    private String exchange;
    private Boolean isExpired;
    private String instrumentName;
    private String ScriptName;
    private String greekView;
    private String yHigh;
    private String yLow;
    private String token;
    private String detailName;
    private String multiplier;
    private String series;
    private String message;
    private String assetType;
    private String tradeSymbol;
    private String tickSize;
    private String normalMarketEligibility;
    private String lotQty;
    private String description;
    private String optionType;
    private String strickPrice;
    private String expiryDate;
    private String errorCode;

    public String getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getExchange() {
        return this.exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Boolean getIsExpired() {
        return this.isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public String getInstrumentName() {
        return this.instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getScriptName() {
        return this.ScriptName;
    }

    public void setScriptName(String ScriptName) {
        this.ScriptName = ScriptName;
    }

    public String getGreekView() {
        return this.greekView;
    }

    public void setGreekView(String greekView) {
        this.greekView = greekView;
    }

    public String getYHigh() {
        return this.yHigh;
    }

    public void setYHigh(String yHigh) {
        this.yHigh = yHigh;
    }

    public String getYLow() {
        return this.yLow;
    }

    public void setYLow(String yLow) {
        this.yLow = yLow;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDetailName() {
        return this.detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getSeries() {
        return this.series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getTradeSymbol() {
        return this.tradeSymbol;
    }

    public void setTradeSymbol(String tradeSymbol) {
        this.tradeSymbol = tradeSymbol;
    }

    public String getTickSize() {
        return this.tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public String getNormalMarketEligibility() {
        return this.normalMarketEligibility;
    }

    public void setNormalMarketEligibility(String normalMarketEligibility) {
        this.normalMarketEligibility = normalMarketEligibility;
    }

    public String getLotQty() {
        return this.lotQty;
    }

    public void setLotQty(String lotQty) {
        this.lotQty = lotQty;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOptionType() {
        return this.optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getStrickPrice() {
        return this.strickPrice;
    }

    public void setStrickPrice(String strickPrice) {
        this.strickPrice = strickPrice;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("uniqueID", this.uniqueID);
        jo.put("exchange", this.exchange);
        jo.put("isExpired", this.isExpired);
        jo.put("instrumentName", this.instrumentName);
        jo.put("ScriptName", this.ScriptName);
        jo.put("greekView", this.greekView);
        jo.put("yHigh", this.yHigh);
        jo.put("yLow", this.yLow);
        jo.put("token", this.token);
        jo.put("detailName", this.detailName);
        jo.put("multiplier", this.multiplier);
        jo.put("series", this.series);
        jo.put("message", this.message);
        jo.put("assetType", this.assetType);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("tickSize", this.tickSize);
        jo.put("normalMarketEligibility", this.normalMarketEligibility);
        jo.put("lotQty", this.lotQty);
        jo.put("description", this.description);
        jo.put("optionType", this.optionType);
        jo.put("strickPrice", this.strickPrice);
        jo.put("expiryDate", this.expiryDate);
        jo.put("ErrorCode", this.errorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.uniqueID = jo.optString("uniqueID");
        this.exchange = jo.optString("exchange");
        this.isExpired = Boolean.valueOf(jo.optBoolean("isExpired"));
        this.instrumentName = jo.optString("instrumentName");
        this.ScriptName = jo.optString("ScriptName");
        this.greekView = jo.optString("greekView");
        this.yHigh = jo.optString("yHigh");
        this.yLow = jo.optString("yLow");
        this.token = jo.optString("token");
        this.detailName = jo.optString("detailName");
        this.multiplier = jo.optString("multiplier");
        this.series = jo.optString("series");
        this.message = jo.optString("message");
        this.assetType = jo.optString("assetType");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.tickSize = jo.optString("tickSize");
        this.normalMarketEligibility = jo.optString("normalMarketEligibility");
        this.lotQty = jo.optString("lotQty");
        this.description = jo.optString("description");
        this.optionType = jo.optString("optionType");
        this.strickPrice = jo.optString("strickPrice");
        this.expiryDate = jo.optString("expiryDate");
        this.errorCode = jo.optString("ErrorCode");
        return this;
    }
}



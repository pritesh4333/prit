package com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class TradeInfo
        implements GreekRequestModel, GreekResponseModel {
    private String uniqueID;
    private String exchange;
    private String instrumentName;
    private String ScriptName;
    private String greekView;
    private String yHigh;
    private String yLow;
    private String token;
    private String Series;
    private String multiplier;
    private String assetType;
    private String tradeSymbol;
    private String tickSize;
    private String normalMarketEligibility;
    private String lotQty;
    private String description;
    private String optionType;
    private String strickPrice;
    private String expiryDate;

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

    public String getSeries() {
        return this.Series;
    }

    public void setSeries(String Series) {
        this.Series = Series;
    }

    public String getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
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

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("uniqueID", this.uniqueID);
        jo.put("exchange", this.exchange);
        jo.put("InstrumentName", this.instrumentName);
        jo.put("ScriptName", this.ScriptName);
        jo.put("greekView", this.greekView);
        jo.put("yHigh", this.yHigh);
        jo.put("yLow", this.yLow);
        jo.put("token", this.token);
        jo.put("Series", this.Series);
        jo.put("multiplier", this.multiplier);
        jo.put("assetType", this.assetType);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("tickSize", this.tickSize);
        jo.put("normalMarketEligibility", this.normalMarketEligibility);
        jo.put("lotQty", this.lotQty);
        jo.put("description", this.description);
        jo.put("optionType", this.optionType);
        jo.put("strickPrice", this.strickPrice);
        jo.put("expiryDate", this.expiryDate);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.uniqueID = jo.optString("uniqueID");
        this.exchange = jo.optString("exchange");
        this.instrumentName = jo.optString("InstrumentName");
        this.ScriptName = jo.optString("ScriptName");
        this.greekView = jo.optString("greekView");
        this.yHigh = jo.optString("yHigh");
        this.yLow = jo.optString("yLow");
        this.token = jo.optString("token");
        this.Series = jo.optString("Series");
        this.multiplier = jo.optString("multiplier");
        this.assetType = jo.optString("assetType");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.tickSize = jo.optString("tickSize");
        this.normalMarketEligibility = jo.optString("normalMarketEligibility");
        this.lotQty = jo.optString("lotQty");
        this.description = jo.optString("description");
        this.optionType = jo.optString("optionType");
        this.strickPrice = jo.optString("strickPrice");
        this.expiryDate = jo.optString("expiryDate");
        return this;
    }
}



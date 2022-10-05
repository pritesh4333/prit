package com.acumengroup.mobile.model;

import org.json.JSONObject;

/**
 * Created by hiren on 17-09-2015.
 */
public class ScripModel {
    //equity
    private String uniqueId;
    private String token;
    private String scriptName;
    private String detailName;
    private String series;
    private String exchange;
    private String assetType;
    private String tradeSymbol;
    private String description;
    private String change;
    private String p_change;
    private String ltp;
    private String multiplier;
    private String instrumentName;
    private String expiryDate;
    private String strickPrice;
    private String yHigh;
    private String yLow;
    private String lotQty;
    private String tickSize;
    private String greekView;

    //mutual fund
    private String mfCode;
    private String mfSchemeCode;
    private String amcName;
    private String schemeName;
    private String vclassCode;
    private String isin;
    private String corp_isin;
    private String trading_isin;
    private String sip_isin;
    private String trade_schcode;
    private String vclass;
    private String bseRTACode;
    private String bseCode;

    public String getBseRTACode() {
        return bseRTACode;
    }

    public void setBseRTACode(String bseRTACode) {
        this.bseRTACode = bseRTACode;
    }

    public String getBseCode() {
        return bseCode;
    }

    public void setBseCode(String bseCode) {
        this.bseCode = bseCode;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getCorp_isin() {
        return corp_isin;
    }

    public void setCorp_isin(String corp_isin) {
        this.corp_isin = corp_isin;
    }

    public String getTrading_isin() {
        return trading_isin;
    }

    public void setTrading_isin(String trading_isin) {
        this.trading_isin = trading_isin;
    }

    public String getSip_isin() {
        return sip_isin;
    }

    public void setSip_isin(String sip_isin) {
        this.sip_isin = sip_isin;
    }

    public String getTrade_schcode() {
        return trade_schcode;
    }

    public void setTrade_schcode(String trade_schcode) {
        this.trade_schcode = trade_schcode;
    }

    private String symbol;
    private String area;
    private String optionType;
    private String name;
    private String normalMarketEligibility;

    public void fromJSONObject(JSONObject data) {
        //Equity
        this.uniqueId = data.optString("UniqueId");
        this.token = data.optString("token");
        this.scriptName = data.optString("ScriptName");
        this.detailName = data.optString("detailName");
        this.series = data.optString("Series");
        this.exchange = data.optString("exchange");
        this.assetType = data.optString("assetType");
        this.tradeSymbol = data.optString("tradeSymbol");
        this.description = data.optString("description");
        this.multiplier = data.optString("multiplier");
        this.instrumentName = data.optString("instrumentName");
        this.expiryDate = data.optString("expiryDate");
        this.strickPrice = data.optString("strickPrice");
        this.yHigh = data.optString("yHigh");
        this.yLow = data.optString("yLow");
        this.lotQty = data.optString("lotQty");
        this.tickSize = data.optString("tickSize");
        this.greekView = data.optString("greekView");
        //Mutual Fund
        this.mfCode = data.optString("mfCoCode");
        this.mfSchemeCode = data.optString("schemeCode");
        this.amcName = data.optString("AMCName");
        this.schemeName = data.optString("schemeName");
        this.vclassCode = data.optString("vClassCode");
        this.vclass = data.optString("vClass");
        this.isin = data.optString("ISIN");
        this.corp_isin = data.optString("corp_isin");
        this.sip_isin = data.optString("sip_isin");
        this.trading_isin = data.optString("trading_isin");
        this.trade_schcode = data.optString("trade_schcode");
        //FNO, Currency, Commodity
        this.symbol = data.optString("symbol");
        this.bseRTACode = data.optString("bseRTACode");
        this.bseCode = data.optString("bseCode");
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalMarketEligibility() {
        return normalMarketEligibility;
    }

    public void setNormalMarketEligibility(String normalMarketEligibility) {
        this.normalMarketEligibility = normalMarketEligibility;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getTradeSymbol() {
        return tradeSymbol;
    }

    public void setTradeSymbol(String tradeSymbol) {
        this.tradeSymbol = tradeSymbol;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getP_change() {
        return p_change;
    }

    public void setP_change(String p_change) {
        this.p_change = p_change;
    }

    public String getLtp() {
        return ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStrickPrice() {
        return strickPrice;
    }

    public void setStrickPrice(String strickPrice) {
        this.strickPrice = strickPrice;
    }

    public String getyHigh() {
        return yHigh;
    }

    public void setyHigh(String yHigh) {
        this.yHigh = yHigh;
    }

    public String getyLow() {
        return yLow;
    }

    public void setyLow(String yLow) {
        this.yLow = yLow;
    }

    public String getLotQty() {
        return lotQty;
    }

    public void setLotQty(String lotQty) {
        this.lotQty = lotQty;
    }

    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public String getGreekView() {
        return greekView;
    }

    public void setGreekView(String greekView) {
        this.greekView = greekView;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMfCode() {
        return mfCode;
    }

    public void setMfCode(String mfCode) {
        this.mfCode = mfCode;
    }

    public String getMfSchemeCode() {
        return mfSchemeCode;
    }

    public void setMfSchemeCode(String mfSchemeCode) {
        this.mfSchemeCode = mfSchemeCode;
    }

    public String getAmcName() {
        return amcName;
    }

    public void setAmcName(String amcName) {
        this.amcName = amcName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getVclassCode() {
        return vclassCode;
    }

    public void setVclassCode(String vclassCode) {
        this.vclassCode = vclassCode;
    }

    public String getVclass() {
        return vclass;
    }

    public void setVclass(String vclass) {
        this.vclass = vclass;
    }
}

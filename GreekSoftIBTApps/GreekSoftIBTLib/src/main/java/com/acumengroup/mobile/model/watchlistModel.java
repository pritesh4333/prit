package com.acumengroup.mobile.model;

/**
 * Created by sushant.patil on 6/10/2016.
 */
public class watchlistModel {

    private String assetType;
    private String exchange;
    private String token;
    private String companyName;
    private String symbol;
    private String ltp;
    private String change;
    private String perChange;
    private String volume;
    private String value;
    private String oi;
    private String InstName;
    private String StrikePrice;
    private String OptType;
    private String instrumentname;
    private String strikeprice;
    private String optiontype;
    private String close;
    private String color;

    public watchlistModel(String symbol, String ltp, String change, String perChange, String close, String token, String color) {
        this.symbol = symbol;
        this.ltp = ltp;
        this.change = change;
        this.perChange = perChange;
        this.close = close;
        this.token = token;
        this.color = color;
    }

    public watchlistModel(String ltp, String change, String perChange, String color) {
        this.ltp = ltp;
        this.change = change;
        this.perChange = perChange;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getOptType() {
        return OptType;
    }

    public void setOptType(String OptType) {
        this.OptType = OptType;
    }

    public String getoptiontype() {
        return optiontype;
    }

    public void setoptiontype(String optiontype) {
        this.optiontype = optiontype;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStrikePrice() {
        return StrikePrice;
    }

    public void setStrikePrice(String StrikePrice) {
        this.StrikePrice = StrikePrice;
    }

    public String getstrikeprice() {
        return strikeprice;
    }

    public void setstrikeprice(String strikeprice) {
        this.strikeprice = strikeprice;
    }

    public String getInstName() {
        return InstName;
    }

    public void setInstName(String InstName) {
        this.InstName = InstName;
    }

    public String getinstrumentname() {
        return instrumentname;
    }

    public void setinstrumentname(String instrumentname) {
        this.instrumentname = instrumentname;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLtp() {
        return ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPerChange() {
        return perChange;
    }

    public void setPerChange(String perChange) {
        this.perChange = perChange;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOi() {
        return oi;
    }

    public void setOi(String oi) {
        this.oi = oi;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }
}

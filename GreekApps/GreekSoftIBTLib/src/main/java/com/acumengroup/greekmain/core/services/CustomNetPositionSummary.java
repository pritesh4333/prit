package com.acumengroup.greekmain.core.services;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arcadia
 */
public class CustomNetPositionSummary implements GreekResponseModel, GreekRequestModel {
    private String netAvg;
    private String buyAvg;
    private String sellAvg;
    private String product;
    private String uniqueID;
    private String exchange;
    private String preNetAvg;
    private String token;
    private String multiplier;
    private String price_multiplier;
    private String ProductType;
    private String assetType;
    private String MTM;
    private String todatMTM;
    private String tradeSymbol;
    private String tickSize;
    private String value;
    private String netAmt;
    private String action;
    private String lotQty;
    private String description;
    private String sellAmt;
    private String buyAmt;
    private String netQty;
    private String ltp;
    private String preNetAmt;
    private String DayNetAmt;
    private String sellQty;
    private String buyQty;
    private String preNetQty;
    private Boolean isSquareOff;
    private String prevNetAvg;
    private String BPL;
    private String pBQty;
    private String pSQty;
    private String pBAmt;
    private String pSAmt;
    private String pAmt;
    private String OverAllMTM;
    private String instrument;
    private String close;
    private String expiry_date;
    private String option_type;
    private String optionType;
    private String strikePrice;
    private String strike_price;

    private String NSEToken;
    private String BSEToken;
    private String EQFOExchange;
    private String CURRExchange;

    public String getTodatMTM() {
        return todatMTM;
    }

    public void setTodatMTM(String todatMTM) {
        this.todatMTM = todatMTM;
    }

    public String getDayNetAmt() {
        return DayNetAmt;
    }



    public void setDayNetAmt(String dayNetAmt) {
        DayNetAmt = dayNetAmt;
    }

    public String getpAmt() {
        return pAmt;
    }

    public void setpAmt(String pAmt) {
        this.pAmt = pAmt;
    }

    private String sqoffToken;

    public String getSqoffToken() {
        return sqoffToken;
    }

    public void setSqoffToken(String sqoffToken) {
        this.sqoffToken = sqoffToken;
    }

    public String getNSEToken() {
        return NSEToken;
    }

    public void setNSEToken(String NSEToken) {
        this.NSEToken = NSEToken;
    }

    public String getBSEToken() {
        return BSEToken;
    }

    public void setBSEToken(String BSEToken) {
        this.BSEToken = BSEToken;
    }

    public String getEQFOExchange() {
        return EQFOExchange;
    }

    public void setEQFOExchange(String EQFOExchange) {
        this.EQFOExchange = EQFOExchange;
    }

    public String getCURRExchange() {
        return CURRExchange;
    }

    public void setCURRExchange(String CURRExchange) {
        this.CURRExchange = CURRExchange;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getOption_type() {
        return option_type;
    }

    public void setOption_type(String option_type) {
        this.option_type = option_type;
    }

    public String getStrike_price() {
        return strike_price;
    }

    public void setStrike_price(String strike_price) {
        this.strike_price = strike_price;
    }

    public CustomNetPositionSummary() {
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getOverAllMTM() {
        return OverAllMTM;
    }

    public void setOverAllMTM(String overAllMTM) {
        OverAllMTM = overAllMTM;
    }

    public String getpBQty() {
        return pBQty;
    }

    public void setpBQty(String pBQty) {
        this.pBQty = pBQty;
    }

    public String getpSQty() {
        return pSQty;
    }

    public void setpSQty(String pSQty) {
        this.pSQty = pSQty;
    }

    public String getpBAmt() {
        return pBAmt;
    }

    public void setpBAmt(String pBAmt) {
        this.pBAmt = pBAmt;
    }

    public String getpSAmt() {
        return pSAmt;
    }

    public void setpSAmt(String pSAmt) {
        this.pSAmt = pSAmt;
    }

    public String getNetAvg() {
        return this.netAvg;
    }

    public void setNetAvg(String netAvg) {
        this.netAvg = netAvg;
    }

    public String getBuyAvg() {
        return this.buyAvg;
    }

    public void setBuyAvg(String buyAvg) {
        this.buyAvg = buyAvg;
    }

    public String getSellAvg() {
        return this.sellAvg;
    }

    public void setSellAvg(String sellAvg) {
        this.sellAvg = sellAvg;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

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

    public String getPreNetAvg() {
        return this.preNetAvg;
    }

    public void setPreNetAvg(String preNetAvg) {
        this.preNetAvg = preNetAvg;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getPrice_multiplier() {
        return price_multiplier;
    }

    public void setPrice_multiplier(String price_multiplier) {
        this.price_multiplier = price_multiplier;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getMTM() {


        return this.MTM;
    }

    public void setMTM(String MTM) {
        this.MTM = MTM;
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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNetAmt() {

        // this.netAmt=String.valueOf(Double.parseDouble(buyAmt)-Double.parseDouble(sellAmt));

        return this.netAmt;
    }

    public void setNetAmt(String netAmt) {
        this.netAmt = netAmt;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getSellAmt() {
        return this.sellAmt;
    }

    public void setSellAmt(String sellAmt) {
        this.sellAmt = sellAmt;
    }

    public String getBuyAmt() {
        return this.buyAmt;
    }

    public void setBuyAmt(String buyAmt) {
        this.buyAmt = buyAmt;
    }

    public String getNetQty() {

        //this.netQty=String.valueOf(Integer.parseInt(buyQty)-Integer.parseInt(sellQty));
        return this.netQty;
    }

    public void setNetQty(String netQty) {
        this.netQty = netQty;
    }

    public String getLtp() {
        return this.ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getPreNetAmt() {
        return this.preNetAmt;
    }

    public void setPreNetAmt(String preNetAmt) {
        this.preNetAmt = preNetAmt;
    }

    public String getSellQty() {
        return this.sellQty;
    }

    public void setSellQty(String sellQty) {
        this.sellQty = sellQty;
    }

    public String getBuyQty() {
        return this.buyQty;
    }

    public void setBuyQty(String buyQty) {
        this.buyQty = buyQty;
    }

    public String getPreNetQty() {
        return this.preNetQty;
    }

    public void setPreNetQty(String preNetQty) {
        this.preNetQty = preNetQty;
    }

    public Boolean getIsSquareOff() {
        return this.isSquareOff;
    }

    public void setIsSquareOff(Boolean isSquareOff) {
        this.isSquareOff = isSquareOff;
    }

    public void setPrevNetAmt(String prevNetAmt) {
        prevNetAmt = prevNetAmt;
    }


    public void setPrevNetQty(String prevNetQty) {
        prevNetQty = prevNetQty;
    }

    public String getPrevNetAvg() {
        return prevNetAvg;
    }

    public void setPrevNetAvg(String prevNetAvg) {
        this.prevNetAvg = prevNetAvg;
    }

    public String getBPL() {
        return BPL;
    }

    public void setBPL(String BPL) {
        this.BPL = BPL;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("netAvg", this.netAvg);
        jo.put("buyAvg", this.buyAvg);
        jo.put("sellAvg", this.sellAvg);
        jo.put("product", this.product);
        jo.put("uniqueID", this.uniqueID);
        jo.put("exchange", this.exchange);
        jo.put("preNetAvg", this.preNetAvg);
        jo.put("token", this.token);
        jo.put("multiplier", this.multiplier);
        jo.put("price_multiplier", this.price_multiplier);
        jo.put("ProductType", this.ProductType);
        jo.put("assetType", this.assetType);
        jo.put("MTM", this.MTM);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("tickSize", this.tickSize);
        jo.put("value", this.value);
        jo.put("netAmt", this.netAmt);
        jo.put("action", this.action);
        jo.put("lotQty", this.lotQty);
        jo.put("description", this.description);
        jo.put("sellAmt", this.sellAmt);
        jo.put("buyAmt", this.buyAmt);
        jo.put("netQty", this.netQty);
        jo.put("ltp", this.ltp);
        jo.put("preNetAmt", this.preNetAmt);
        jo.put("sellQty", this.sellQty);
        jo.put("buyQty", this.buyQty);
        jo.put("preNetQty", this.preNetQty);
        jo.put("isSquareOff", this.isSquareOff);
        jo.put("preNetAvg", this.prevNetAvg);
        jo.put("BPL", this.BPL);
        jo.put("pBQty", this.pBQty);
        jo.put("pSQty", this.pSQty);
        jo.put("pBAmt", this.pBAmt);
        jo.put("pSAmt", this.pSAmt);
        jo.put("PAmt", this.pAmt);
        jo.put("OverAllMTM", this.OverAllMTM);
        jo.put("instrument", this.instrument);
        jo.put("close", this.close);
        jo.put("expiry_date", this.expiry_date);
        jo.put("option_type", this.option_type);
        jo.put("strike_price", this.strike_price);
        jo.put("strikePrice", this.strikePrice);
        jo.put("optionType", this.optionType);

        jo.put("NSEToken", this.NSEToken);
        jo.put("BSEToken", this.BSEToken);
        jo.put("EQFOExchange", this.EQFOExchange);
        jo.put("CURRExchange", this.CURRExchange);
        jo.put("sqoffToken", this.sqoffToken);
        jo.put("DayNetAmt", this.DayNetAmt);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.netAvg = jo.optString("netAvg");
        this.buyAvg = jo.optString("buyAvg");
        this.sellAvg = jo.optString("sellAvg");
        this.product = jo.optString("product");
        this.uniqueID = jo.optString("uniqueID");
        this.exchange = jo.optString("exchange");
        this.preNetAvg = jo.optString("preNetAvg");
        this.token = jo.optString("token");
        this.multiplier = jo.optString("multiplier");
        this.price_multiplier = jo.optString("price_multiplier");
        this.ProductType = jo.optString("ProductType");
        this.assetType = jo.optString("assetType");
        this.MTM = jo.optString("MTM");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.tickSize = jo.optString("tickSize");
        this.value = jo.optString("value");
        this.netAmt = jo.optString("netAmt");
        this.action = jo.optString("action");
        this.lotQty = jo.optString("lotQty");
        this.description = jo.optString("description");
        this.sellAmt = jo.optString("sellAmt");
        this.buyAmt = jo.optString("buyAmt");
        this.netQty = jo.optString("netQty");
        this.ltp = jo.optString("ltp");
        this.preNetAmt = jo.optString("preNetAmt");
        this.sellQty = jo.optString("sellQty");
        this.buyQty = jo.optString("buyQty");
        this.preNetQty = jo.optString("preNetQty");
        this.isSquareOff = Boolean.valueOf(jo.optBoolean("isSquareOff"));
        this.prevNetAvg = jo.optString("preNetAvg");
        this.BPL = jo.optString("BPL");
        this.pBQty = jo.optString("pBQty");
        this.pSQty = jo.optString("pSQty");
        this.pBAmt = jo.optString("pBAmt");
        this.pSAmt = jo.optString("pSAmt");
        this.pAmt = jo.optString("PAmt");
        this.OverAllMTM = jo.optString("OverAllMTM");
        this.instrument = jo.optString("instrument");
        this.close = jo.optString("close");
        this.expiry_date = jo.optString("expiry_date");
        this.option_type = jo.optString("option_type");
        this.strike_price = jo.optString("strike_price");
        this.strikePrice = jo.optString("strikePrice");
        this.optionType = jo.optString("optionType");

        this.NSEToken = jo.optString("NSEToken");
        this.BSEToken = jo.optString("BSEToken");
        this.EQFOExchange = jo.optString("EQFOExchange");
        this.CURRExchange = jo.optString("CURRExchange");
        this.sqoffToken = jo.optString("sqoffToken");
        this.DayNetAmt = jo.optString("DayNetAmt");
        return this;
    }
}

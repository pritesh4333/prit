package com.acumengroup.greekmain.core.model.marketsindicesstock;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class IndicesStock
        implements GreekRequestModel, GreekResponseModel {
    private String highPrice;
    private String tradedQty;
    private String Weightage;
    private String openPrice;
    private String p_change;
    private String lastUpdatedTime;
    private String bestSellPrice;
    private String yHigh;
    private String yLow;
    private String token;
    private String lowPrice;
    private String bestSellQty;
    private String change;
    private String totMcap;
    private String mcap;
    private String oldPrice;
    private String price;
    private String ltp;
    private String bestBuyQty;
    private String bestBuyPrice;
    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHighPrice() {
        return this.highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getTradedQty() {
        return this.tradedQty;
    }

    public void setTradedQty(String tradedQty) {
        this.tradedQty = tradedQty;
    }

    public String getWeightage() {
        return this.Weightage;
    }

    public void setWeightage(String Weightage) {
        this.Weightage = Weightage;
    }

    public String getOpenPrice() {
        return this.openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getP_change() {
        return this.p_change;
    }

    public void setP_change(String p_change) {
        this.p_change = p_change;
    }

    public String getLastUpdatedTime() {
        return this.lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getBestSellPrice() {
        return this.bestSellPrice;
    }

    public void setBestSellPrice(String bestSellPrice) {
        this.bestSellPrice = bestSellPrice;
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

    public String getLowPrice() {
        return this.lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getBestSellQty() {
        return this.bestSellQty;
    }

    public void setBestSellQty(String bestSellQty) {
        this.bestSellQty = bestSellQty;
    }

    public String getChange() {
        return this.change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getTotMcap() {
        return this.totMcap;
    }

    public void setTotMcap(String totMcap) {
        this.totMcap = totMcap;
    }

    public String getMcap() {
        return this.mcap;
    }

    public void setMcap(String mcap) {
        this.mcap = mcap;
    }

    public String getOldPrice() {
        return this.oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLtp() {
        return this.ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getBestBuyQty() {
        return this.bestBuyQty;
    }

    public void setBestBuyQty(String bestBuyQty) {
        this.bestBuyQty = bestBuyQty;
    }

    public String getBestBuyPrice() {
        return this.bestBuyPrice;
    }

    public void setBestBuyPrice(String bestBuyPrice) {
        this.bestBuyPrice = bestBuyPrice;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("highPrice", this.highPrice);
        jo.put("tradedQty", this.tradedQty);
        jo.put("Weightage", this.Weightage);
        jo.put("openPrice", this.openPrice);
        jo.put("p_change", this.p_change);
        jo.put("lastUpdatedTime", this.lastUpdatedTime);
        jo.put("bestSellPrice", this.bestSellPrice);
        jo.put("yHigh", this.yHigh);
        jo.put("yLow", this.yLow);
        jo.put("token", this.token);
        jo.put("lowPrice", this.lowPrice);
        jo.put("bestSellQty", this.bestSellQty);
        jo.put("change", this.change);
        jo.put("totMcap", this.totMcap);
        jo.put("mcap", this.mcap);
        jo.put("oldPrice", this.oldPrice);
        jo.put("price", this.price);
        jo.put("ltp", this.ltp);
        jo.put("bestBuyQty", this.bestBuyQty);
        jo.put("bestBuyPrice", this.bestBuyPrice);
        jo.put("name", this.name);
        jo.put("description", this.description);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.highPrice = jo.optString("highPrice");
        this.tradedQty = jo.optString("tradedQty");
        this.Weightage = jo.optString("Weightage");
        this.openPrice = jo.optString("openPrice");
        this.p_change = jo.optString("p_change");
        this.lastUpdatedTime = jo.optString("lastUpdatedTime");
        this.bestSellPrice = jo.optString("bestSellPrice");
        this.yHigh = jo.optString("yHigh");
        this.yLow = jo.optString("yLow");
        this.token = jo.optString("token");
        this.lowPrice = jo.optString("lowPrice");
        this.bestSellQty = jo.optString("bestSellQty");
        this.change = jo.optString("change");
        this.totMcap = jo.optString("totMcap");
        this.mcap = jo.optString("mcap");
        this.oldPrice = jo.optString("oldPrice");
        this.price = jo.optString("price");
        this.ltp = jo.optString("ltp");
        this.bestBuyQty = jo.optString("bestBuyQty");
        this.bestBuyPrice = jo.optString("bestBuyPrice");
        this.name = jo.optString("name");
        this.description = jo.optString("description");
        return this;
    }
}



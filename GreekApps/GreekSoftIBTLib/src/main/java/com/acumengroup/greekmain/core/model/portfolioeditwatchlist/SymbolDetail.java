package com.acumengroup.greekmain.core.model.portfolioeditwatchlist;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SymbolDetail
        implements GreekRequestModel, GreekResponseModel {
    private String exchange;
    private String tradeSymbol;
    private String token;
    private String assetType;
    private String seqNo;


    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getExchange() {
        return this.exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTradeSymbol() {
        return this.tradeSymbol;
    }

    public void setTradeSymbol(String tradeSymbol) {
        this.tradeSymbol = tradeSymbol;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("exchange", this.exchange);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("token", this.token);
        jo.put("assetType", this.assetType);
        jo.put("seqNo", this.seqNo);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.exchange = jo.optString("exchange");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.token = jo.optString("token");
        this.assetType = jo.optString("assetType");
        this.seqNo = jo.optString("seqNo");
        return this;
    }
}
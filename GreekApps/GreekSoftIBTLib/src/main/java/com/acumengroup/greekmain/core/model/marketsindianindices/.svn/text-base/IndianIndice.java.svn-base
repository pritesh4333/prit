package com.acumengroup.greekmain.core.model.marketsindianindices;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class IndianIndice
        implements GreekRequestModel, GreekResponseModel {
    private String exchange;
    private String exchangeGroup;
    private String token;
    private String p_change;
    private String ltp;
    private String change;
    private String indexCode;
    private String high;
    private String low;
    private String seqNo;


    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getExchange() {
        return this.exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchangeGroup() {
        return this.exchangeGroup;
    }

    public void setExchangeGroup(String exchangeGroup) {
        this.exchangeGroup = exchangeGroup;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getP_change() {
        return this.p_change;
    }

    public void setP_change(String p_change) {
        this.p_change = p_change;
    }

    public String getLtp() {
        return this.ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getChange() {
        return this.change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("exchange", this.exchange);
        jo.put("exchangeGroup", this.exchangeGroup);
        jo.put("token", this.token);
        jo.put("p_change", this.p_change);
        jo.put("last", this.ltp);
        jo.put("change", this.change);
        jo.put("indexCode", this.indexCode);
        jo.put("high", this.high);
        jo.put("low", this.low);
        jo.put("seqNo", this.seqNo);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.exchange = jo.optString("exchange");
        this.exchangeGroup = jo.optString("exchangeGroup");
        this.token = jo.optString("token");
        this.p_change = jo.optString("p_change");
        this.ltp = jo.optString("last");
        this.change = jo.optString("change");
        this.indexCode = jo.optString("indexCode");
        this.low = jo.optString("low");
        this.high = jo.optString("high");
        this.seqNo = jo.optString("seqNo");
        return this;
    }
}



package com.acumengroup.greekmain.core.model.streamerorderconfirmation;

import android.util.Base64;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/29/2016.
 */
public class StreamerRmsRejectionResponse implements GreekRequestModel, GreekResponseModel {

    private String gorderid;
    private String eorderid;
    private String gtoken;
    private String code;
    private String reason;
    private String lu_time_exchange;
    private String lu_time;
    private String order_state;
    private String order_status;
    private String symbol;
    private String product;
    private String expiryDate;
    private String tradeSymbol;
    private String instrument;
    private String optionType;
    private String strikePrice;


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


    public String getTradeSymbol() {
        return tradeSymbol;
    }

    public void setTradeSymbol(String tradeSymbol) {
        this.tradeSymbol = tradeSymbol;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getGorderid() {
        return gorderid;
    }

    public void setGorderid(String gorderid) {
        this.gorderid = gorderid;
    }

    public String getEorderid() {
        return eorderid;
    }

    public void setEorderid(String eorderid) {
        this.eorderid = eorderid;
    }

    public String getGtoken() {
        return gtoken;
    }

    public void setGtoken(String gtoken) {
        this.gtoken = gtoken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLu_time_exchange() {
        return lu_time_exchange;
    }

    public void setLu_time_exchange(String lu_time_exchange) {
        this.lu_time_exchange = lu_time_exchange;
    }

    public String getLu_time() {
        return lu_time;
    }

    public void setLu_time(String lu_time) {
        this.lu_time = lu_time;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gorderid", this.gorderid);
        jo.put("eorderid", this.eorderid);
        jo.put("gtoken", this.gtoken);
        jo.put("lu_time_exchange", this.lu_time_exchange);
        jo.put("lu_time", this.lu_time);
        jo.put("order_state", this.order_state);
        jo.put("order_status", this.order_status);
        jo.put("code", this.code);
        jo.put("reason", this.reason);
        jo.put("symbol", this.symbol);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("instrument", this.instrument);
        jo.put(product, this.product);
        jo.put(expiryDate, this.expiryDate);
        jo.put("strikePrice", this.strikePrice);
        jo.put("optionType", this.optionType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gorderid = jo.optString("gorderid");
        this.eorderid = jo.optString("eorderid");
        this.gtoken = jo.optString("gtoken");
        this.lu_time_exchange = jo.optString("lu_time_exchange");
        this.lu_time = jo.optString("lu_time");
        this.order_state = jo.optString("order_state");
        this.order_status = jo.optString("order_status");
        this.code = jo.optString("code");
        this.reason = jo.optString("reason");
        this.symbol = jo.optString("symbol");
        this.product = jo.optString("product");
        this.expiryDate = jo.optString("expiryDate");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.instrument = jo.optString("instrument");
        this.strikePrice = jo.optString("strikePrice");
        this.optionType = jo.optString("optionType");
        return this;
    }

    public String decodeBase64(String decodeData) {
        String decodedString = new String(Base64.decode(decodeData, Base64.DEFAULT));
        return decodedString;
    }
}

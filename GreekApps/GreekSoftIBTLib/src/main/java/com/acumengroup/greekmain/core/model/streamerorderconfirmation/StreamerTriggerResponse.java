package com.acumengroup.greekmain.core.model.streamerorderconfirmation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 05-Oct-16.
 */



public class StreamerTriggerResponse implements GreekRequestModel, GreekResponseModel {

    private String gorderid;
    private String eorderid;
    private String gtoken;
    private String lu_time_exchange;
    private String lu_time;
    private String order_state;
    private String order_status;
    private String trigger_price;
    private Double disclosed_qty;
    private String side;
    private String product;
    private String pending_disclosed_qty;
    private String pending_qty;
    private String qty_filled_today;
    private String validity;
    private String qty;
    private String tradeid;
    private String traded_qty;
    private String traded_price;
    private String price;
    private String order_type;
    private String symbol;
    private String goodTillDate;
    private String regular_lot;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getTraded_qty() {
        return traded_qty;
    }

    public void setTraded_qty(String traded_qty) {
        this.traded_qty = traded_qty;
    }

    public String getTraded_price() {
        return traded_price;
    }

    public void setTraded_price(String traded_price) {
        this.traded_price = traded_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getTrigger_price() {
        return trigger_price;
    }

    public void setTrigger_price(String trigger_price) {
        this.trigger_price = trigger_price;
    }

    public Double getDisclosed_qty() {
        return disclosed_qty;
    }

    public void setDisclosed_qty(Double disclosed_qty) {
        this.disclosed_qty = disclosed_qty;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPending_disclosed_qty() {
        return pending_disclosed_qty;
    }

    public void setPending_disclosed_qty(String pending_disclosed_qty) {
        this.pending_disclosed_qty = pending_disclosed_qty;
    }

    public String getPending_qty() {
        return pending_qty;
    }

    public void setPending_qty(String pending_qty) {
        this.pending_qty = pending_qty;
    }

    public String getQty_filled_today() {
        return qty_filled_today;
    }

    public void setQty_filled_today(String qty_filled_today) {
        this.qty_filled_today = qty_filled_today;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getGoodTillDate() {
        return goodTillDate;
    }

    public void setGoodTillDate(String goodTillDate) {
        this.goodTillDate = goodTillDate;
    }

    public String getRegular_lot() {
        return regular_lot;
    }

    public void setRegular_lot(String regular_lot) {
        this.regular_lot = regular_lot;
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
        jo.put("pending_disclosed_qty", this.pending_disclosed_qty);
        jo.put("pending_qty", this.pending_qty);
        jo.put("qty_filled_today", this.qty_filled_today);
        jo.put("validity", this.validity);
        jo.put("qty", this.qty);
        jo.put("tradeid", this.tradeid);
        jo.put("traded_qty", this.traded_qty);
        jo.put("traded_price", this.traded_price);
        jo.put("trigger_price", this.trigger_price);
        jo.put("price", this.price);
        jo.put("side", this.side);
        jo.put("order_type", this.order_type);
        jo.put("symbol", this.symbol);
        jo.put("goodTillDate", this.goodTillDate);
        jo.put("regular_lot", this.regular_lot);
        jo.put("expiryDate", this.expiryDate);
        jo.put("product", this.product);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("instrument", this.instrument);
        jo.put("optionType", this.optionType);
        jo.put("strikePrice", this.strikePrice);

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
        this.pending_disclosed_qty = jo.optString("pending_disclosed_qty");
        this.pending_qty = jo.optString("pending_qty");
        this.qty_filled_today = jo.optString("qty_filled_today");
        this.validity = jo.optString("validity");
        this.qty = jo.optString("qty");
        this.tradeid = jo.optString("tradeid");
        this.traded_qty = jo.optString("traded_qty");
        this.traded_price = jo.optString("traded_price");
        this.trigger_price = jo.optString("trigger_price");
        this.price = jo.optString("price");
        this.side = jo.optString("side");
        this.order_type = jo.optString("order_type");
        this.symbol = jo.optString("symbol");
        this.goodTillDate = jo.optString("goodTillDate");
        this.regular_lot = jo.optString("regular_lot");
        this.expiryDate = jo.optString("expiryDate");
        this.product = jo.optString("product");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.instrument = jo.optString("instrument");
        this.optionType = jo.optString("optionType");
        this.strikePrice = jo.optString("strikePrice");

        return this;
    }
}



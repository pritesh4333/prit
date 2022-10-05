package com.acumengroup.greekmain.core.model.streamerorderconfirmation;

import android.util.Base64;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class StreamerOrderConfirmationResponse
        implements GreekRequestModel, GreekResponseModel {
    private String gcid;
    private String gorderid;
    private String eorderid;
    private String gtoken;
    private String side;
    private String product;
    private String exchange;
    private String flow_type;
    private String exchange_order_number;
    private String trigger_price;
    private String disclosed_qty;
    private String order_term;
    private String client_code;
    private Double tick_size;
    private String gtd_date;
    private String order_status;
    private String symbol;
    private String order_number;
    private String display_flag;
    private String date;
    private String error_message;
    private String price;
    private String order_type;
    private String order_state;
    private String code;
    private String reason;
    private String lu_time_exchange;
    private String lu_time;
    private String pending_disclosed_qty;
    private String pending_qty;
    private String qty_filled_today;
    private String validity;
    private String qty;
    private String goodTillDate;
    private String cancelledBy;
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

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPending_qty() {
        return pending_qty;
    }

    public void setPending_qty(String pending_qty) {
        this.pending_qty = pending_qty;
    }

    public String getPending_disclosed_qty() {
        return pending_disclosed_qty;
    }

    public void setPending_disclosed_qty(String pending_disclosed_qty) {
        this.pending_disclosed_qty = pending_disclosed_qty;
    }

    public String getQty_filled_today() {
        return qty_filled_today;
    }

    public void setQty_filled_today(String qty_filled_today) {
        this.qty_filled_today = qty_filled_today;
    }

    public String getRegular_lot() {
        return regular_lot;
    }

    public void setRegular_lot(String regular_lot) {
        this.regular_lot = regular_lot;
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

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
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


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSide() {
        return this.side;
    }

    public void setSide(String side) {
        this.side = side;
    }


    public String getProduct_type() {
        return this.product;
    }

    public void setProduct_type(String product_type) {
        this.product = product_type;
    }

    public String getExchange() {
        return this.exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getFlow_type() {
        return this.flow_type;
    }

    public void setFlow_type(String flow_type) {
        this.flow_type = flow_type;
    }

    public String getExchange_order_number() {
        return this.exchange_order_number;
    }

    public void setExchange_order_number(String exchange_order_number) {
        this.exchange_order_number = exchange_order_number;
    }

    public String getTrigger_price() {
        return trigger_price;
    }

    public void setTrigger_price(String trigger_price) {
        this.trigger_price = trigger_price;
    }

    public String getDisclosed_qty() {
        return disclosed_qty;
    }

    public void setDisclosed_qty(String disclosed_qty) {
        this.disclosed_qty = disclosed_qty;
    }

    public String getOrder_term() {
        return this.order_term;
    }

    public void setOrder_term(String order_term) {
        this.order_term = order_term;
    }


    public String getClient_code() {
        return this.client_code;
    }

    public void setClient_code(String client_code) {
        this.client_code = client_code;
    }

    public Double getTick_size() {
        return this.tick_size;
    }

    public void setTick_size(Double tick_size) {
        this.tick_size = tick_size;
    }

    public String getGtd_date() {
        return this.gtd_date;
    }

    public void setGtd_date(String gtd_date) {
        this.gtd_date = gtd_date;
    }

    public String getStatus() {
        return this.order_status;
    }

    public void setStatus(String status) {
        this.order_status = status;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOrder_number() {
        return this.order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getDisplay_flag() {
        return this.display_flag;
    }

    public void setDisplay_flag(String display_flag) {
        this.display_flag = display_flag;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getError_message() {
        return this.error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getGoodTillDate() {
        return goodTillDate;
    }

    public void setGoodTillDate(String goodTillDate) {
        this.goodTillDate = goodTillDate;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gcid", this.gcid);
        jo.put("gorderid", this.gorderid);
        jo.put("eorderid", this.eorderid);
        jo.put("gtoken", this.gtoken);
        jo.put("side", this.side);
        jo.put("product", this.product);
        jo.put("exchange", this.exchange);
        jo.put("flow_type", this.flow_type);
        jo.put("exchange_order_number", this.exchange_order_number);
        jo.put("trigger_price", this.trigger_price);
        jo.put("disclosed_qty", this.disclosed_qty);
        jo.put("order_term", this.order_term);
        jo.put("client_code", this.client_code);
        jo.put("tick_size", new Double(this.tick_size.doubleValue()));
        jo.put("gtd_date", this.gtd_date);
        jo.put("order_status", this.order_status);
        jo.put("symbol", this.symbol);
        jo.put("order_number", this.order_number);
        jo.put("display_flag", this.display_flag);
        jo.put("date", this.date);
        jo.put("error_message", this.error_message);
        jo.put("order_type", this.order_type);
        jo.put("order_state", this.order_state);
        jo.put("code", this.code);
        jo.put("reason", this.reason);
        jo.put("lu_time_exchange", this.lu_time_exchange);
        jo.put("lu_time", this.lu_time);
        jo.put("pending_disclosed_qty", this.pending_disclosed_qty);
        jo.put("pending_qty", this.pending_qty);
        jo.put("qty_filled_today", this.qty_filled_today);
        jo.put("validity", this.validity);
        jo.put("qty", this.qty);
        jo.put("price", this.price);
        jo.put("goodTillDate", this.goodTillDate);
        jo.put("cancelledBy", this.cancelledBy);
        jo.put("regular_lot", this.regular_lot);
        jo.put("expiryDate", this.expiryDate);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("instrument", this.instrument);
        jo.put("optionType", this.optionType);
        jo.put("strikePrice", this.strikePrice);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gcid = jo.optString("gcid");
        this.gorderid = jo.optString("gorderid");
        this.eorderid = jo.optString("eorderid");
        this.gtoken = jo.optString("gtoken");
        this.side = jo.optString("side");
        this.product = jo.optString("product");
        this.exchange = jo.optString("exchange");
        this.flow_type = jo.optString("flow_type");
        this.exchange_order_number = jo.optString("exchange_order_number");
        this.trigger_price = jo.optString("trigger_price");
        this.disclosed_qty = jo.optString("disclosed_qty");
        this.order_term = jo.optString("order_term");
        this.client_code = jo.optString("client_code");
        this.tick_size = Double.valueOf(jo.optDouble("tick_size"));
        this.gtd_date = jo.optString("gtd_date");
        this.order_status = jo.optString("order_status");
        this.symbol = jo.optString("symbol");
        this.order_number = jo.optString("order_number");
        this.display_flag = jo.optString("display_flag");
        this.date = jo.optString("date");
        this.error_message = jo.optString("error_message");
        this.order_type = jo.optString("order_type");
        this.order_state = jo.optString("order_state");
        this.code = jo.optString("code");
        this.reason = jo.optString("reason");
        this.lu_time_exchange = jo.optString("lu_time_exchange");
        this.lu_time = jo.optString("lu_time");
        this.pending_disclosed_qty = jo.optString("pending_disclosed_qty");
        this.pending_qty = jo.optString("pending_qty");
        this.qty_filled_today = jo.optString("qty_filled_today");
        this.qty = jo.optString("qty");
        this.validity = jo.optString("validity");
        this.price = jo.optString("price");
        this.goodTillDate = jo.optString("goodTillDate");
        this.cancelledBy = jo.optString("cancelledBy");
        this.regular_lot = jo.optString("regular_lot");
        this.expiryDate = jo.optString("expiryDate");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.instrument = jo.optString("instrument");
        this.optionType = jo.optString("optionType");
        this.strikePrice = jo.optString("strikePrice");

        return this;
    }

    public String decodeBase64(String decodeData) {
        String decodedString = new String(Base64.decode(decodeData, Base64.DEFAULT));
        return decodedString;
    }
}



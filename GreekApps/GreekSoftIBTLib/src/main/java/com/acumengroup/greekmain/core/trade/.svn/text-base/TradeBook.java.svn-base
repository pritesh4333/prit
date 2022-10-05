package com.acumengroup.greekmain.core.trade;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arcadia
 */
public class TradeBook implements GreekRequestModel, GreekResponseModel {

    private String action = "action";
    private String amount = "amount";
    private String assetType = "assetType";
    private String clientCode = "clientCode";
    private String description = "description";
    private String exchange = "exchange";
    private String price = "price";
    private String trdQty = "trdQty";
    private String uniqueOrderID = "uniqueOrderID";
    private String trdTime = "trdTime";
    private String ordID = "ordID";
    private String tradeNo = "tradeNo";
    private String tradeDateTime = "tradeDateTime";
    private String product = "product";
    private String qty = "qty";
    private String symbol = "symbol";
    private String tradeSymbol = "tradeSymbol";
    private String pendingQty = "pendingQty";
    private String instrument = "instrument";
    private String token = "token";
    private String regular_lot = "regular_lot";
    private String expiryDate = "expiryDate";
    private String cPANNumber = "cPANNumber";
    private String optionType = "optionType";
    private String strikePrice = "strikePrice";

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.product = jo.optString("product");
        this.ordID = jo.optString("ordID");
        this.exchange = jo.optString("exchange");
        this.trdQty = jo.optString("trdQty");
        this.amount = jo.optString("amount");
        this.uniqueOrderID = jo.optString("uniqueOrderID");
        this.qty = jo.optString("qty");
        this.assetType = jo.optString("assetType");
        this.clientCode = jo.optString("clientCode");
        this.price = jo.optString("price");
        this.action = jo.optString("action");
        this.description = jo.optString("description");
        this.trdTime = jo.optString("trdTime");
        this.tradeNo = jo.optString("tradeNo");
        this.tradeDateTime = jo.optString("tradeDateTime");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.symbol = jo.optString("symbol");
        this.pendingQty = jo.optString("pendingQty");
        this.instrument = jo.optString("instrument");
        this.token = jo.optString("token");
        this.regular_lot = jo.optString("regular_lot");
        this.expiryDate = jo.optString("expiryDate");
        this.cPANNumber = jo.optString("cPANNumber");
        this.cPANNumber = jo.optString("cPANNumber");
        this.strikePrice = jo.optString("strikePrice");
        this.optionType = jo.optString("optionType");
        return this;
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

    public String getTradeSymbol() {
        return tradeSymbol;
    }

    public void setTradeSymbol(String tradeSymbol) {
        this.tradeSymbol = tradeSymbol;
    }

    public String getcPANNumber() {
        return cPANNumber;
    }

    public void setcPANNumber(String cPANNumber) {
        this.cPANNumber = cPANNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getPendingQty() {
        return pendingQty;
    }

    public void setPendingQty(String pendingQty) {
        this.pendingQty = pendingQty;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTrdQty() {
        return trdQty;
    }

    public void setTrdQty(String trdQty) {
        this.trdQty = trdQty;
    }

    public String getUniqueOrderID() {
        return uniqueOrderID;
    }

    public void setUniqueOrderID(String uniqueOrderID) {
        this.uniqueOrderID = uniqueOrderID;
    }

    public String getTrdTime() {
        return trdTime;
    }

    public void setTrdTime(String trdTime) {
        this.trdTime = trdTime;
    }

    public String getOrdID() {
        return ordID;
    }

    public void setOrdID(String ordID) {
        this.ordID = ordID;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeDateTime() {
        return tradeDateTime;
    }

    public void setTradeDateTime(String tradeDateTime) {
        this.tradeDateTime = tradeDateTime;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public TradeBook() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRegular_lot() {
        return regular_lot;
    }

    public void setRegular_lot(String regular_lot) {
        this.regular_lot = regular_lot;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("product", this.product);
        jo.put("ordID", this.ordID);
        jo.put("exchange", this.exchange);
        jo.put("trdQty", this.trdQty);
        jo.put("amount", this.amount);
        jo.put("uniqueOrderID", this.uniqueOrderID);
        jo.put("qty", this.qty);
        jo.put("assetType", this.assetType);
        jo.put("clientCode", this.clientCode);
        jo.put("price", this.price);
        jo.put("action", this.action);
        jo.put("description", this.description);
        jo.put("trdTime", this.trdTime);
        jo.put("tradeNo", this.tradeNo);
        jo.put("tradeDateTime", this.tradeDateTime);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("symbol", this.symbol);
        jo.put("pendingQty", this.pendingQty);
        jo.put("instrument", this.instrument);
        jo.put("token", this.token);
        jo.put("regular_lot", this.regular_lot);
        jo.put("expiryDate", this.expiryDate);
        jo.put("cPANNumber", this.cPANNumber);
        jo.put("strikePrice", this.strikePrice);
        jo.put("optionType", this.optionType);

        return jo;
    }


}

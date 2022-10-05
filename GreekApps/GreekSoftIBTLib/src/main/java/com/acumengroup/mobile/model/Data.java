package com.acumengroup.mobile.model;



import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Data implements GreekRequestModel, GreekResponseModel {

    String errorCode;
    String Sauda_date;
    String Scrip_name;
    String AssetClass;
    String MarketRate;
    String NetRate;
    String TType;
    String Action;
    String Quantity;
    String Brokapplied;
    String service_tax;
    String TxnValue;


    public String getSauda_date() {
        return Sauda_date;
    }

    public String getMarketRate() {
        return MarketRate;
    }

    public void setMarketRate(String marketRate) {
        MarketRate = marketRate;
    }

    public String getNetRate() {
        return NetRate;
    }

    public void setNetRate(String netRate) {
        NetRate = netRate;
    }

    public void setSauda_date(String sauda_date) {
        Sauda_date = sauda_date;
    }

    public String getScrip_name() {
        return Scrip_name;
    }

    public void setScrip_name(String scrip_name) {
        Scrip_name = scrip_name;
    }

    public String getAssetClass() {
        return AssetClass;
    }

    public void setAssetClass(String assetClass) {
        AssetClass = assetClass;
    }

    public String getTType() {
        return TType;
    }

    public void setTType(String TType) {
        this.TType = TType;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getBrokapplied() {
        return Brokapplied;
    }

    public void setBrokapplied(String brokapplied) {
        Brokapplied = brokapplied;
    }

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }

    public String getTxnValue() {
        return TxnValue;
    }

    public void setTxnValue(String txnValue) {
        TxnValue = txnValue;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("errorCode", this.errorCode);
        jo.put("Date", this.Sauda_date);
        jo.put("ScripName", this.Scrip_name);
        jo.put("AssetClass", this.AssetClass);
        jo.put("TXNType", this.TType);
        jo.put("Action", this.Action);
        jo.put("MarketRate", this.MarketRate);
        jo.put("NetRate", this.NetRate);
        jo.put("Quantity", this.Quantity);
        jo.put("Brokapplied", this.Brokapplied);
        jo.put("service_tax", this.service_tax);
        jo.put("TxnValue", this.TxnValue);

        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.errorCode = jo.optString("errorCode");
        this.Sauda_date = jo.optString("Date");
        this.Scrip_name = jo.optString("ScripName");
        this.AssetClass = jo.optString("AssetClass");
        this.TType = jo.optString("TXNType");
        this.MarketRate = jo.optString("MarketRate");
        this.NetRate = jo.optString("NetRate");
        this.Action = jo.optString("Action");
        this.Quantity = jo.optString("Quantity");
        this.Brokapplied = jo.optString("Brokapplied");
        this.service_tax = jo.optString("service_tax");
        this.TxnValue = jo.optString("TxnValue");
        return this;
    }
}


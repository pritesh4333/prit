package com.acumengroup.greekmain.core.model;

/**
 * Created by ankitnagda on 10/16/2015.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class DematHolding implements GreekRequestModel, GreekResponseModel {
    private String scripName;
    private String NSETradeSymbol;
    private String product;
    private String BSETradeSymbol;
    private String uniqueID;
    private String Exchange;
    private String AvailableForSell;
    private String OrderBlockedQty;
    private String Lot;
    private String multiplier;
    private String instrumentType;
    private String instrument;
    private String assetType;
    private String TradeBlockedQty;
    private String BFQuantity;
    private String TickSize;
    private String Ltp;
    private String Value;
    private String NSEToken;
    private String BSEToken;
    private String ProfitLoss;
    private String AvgBuyPrice;
    private String HoldingQty;
    private String HoldingPrice;
    private String HoldingValue;
    private String OldHoldingValue;
    private String OldCurrentHoldingValue;
    private String MTM;
    private String BuyValue;
    private String SellValue;
    private String PrevValue;
    private String PrevQty;
    private String SellPrice;
    private String close;
    private String HPrice;
    private String Close;
    private String Qty;
    private String isin;



    private String symbol;
    private String ActualDPQty;
    private String ActualPoolQty;
    private String gscid;
    private String ActualDPPrice;
    private String ActualPoolPrice;
    private String ActualDPAmount;
    private String ActualPoolAmount;
    private String cISINumber;
    private String TSellQty;
    private String TBuyQty;
    private String TSellAmt;
    private String TBuyAmt;
    private String TSellATP;
    private String TBuyATP;
    private String TNQ;
    private String DPQty;
    private String DPValue;
    private String DPPrice;
    private String PoolQty;
    private String PoolValue;
    private String PoolPrice;
    private String NetHQty;
    private String NetHValue;
    private String NetHPrice;
    private String SoldQty;
    private String PendingQty;
    private String RiskBlockQty;
    private String FreeHoldingQty;


    private String ActualMTFQty;
    private String ActualMTFPrice;
    private String ActualMTFAmount;
    private String MTFQty;
    private String MTFValue;
    private String MTFPrice;


    private String holdingvalue;
    private String currentVal;
    private String currentMTM;


    public String getLtp() {
        return Ltp;
    }

    public void setLtp(String ltp) {
        Ltp = ltp;
    }

    public String getHPrice() {
        return HPrice;
    }

    public void setHPrice(String HPrice) {
        this.HPrice = HPrice;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getIsin() {
        return isin;
    }



    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getHoldingvalue() {
        return holdingvalue;
    }

    public void setHoldingvalue(String holdingvalue) {
        this.holdingvalue = holdingvalue;
    }

    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal;
    }

    public String getCurrentMTM() {
        return currentMTM;
    }

    public void setCurrentMTM(String currentMTM) {
        this.currentMTM = currentMTM;
    }

    public String getOldHoldingValue() {
        return OldHoldingValue;
    }

    public void setOldHoldingValue(String oldHoldingValue) {
        OldHoldingValue = oldHoldingValue;
    }

    public String getOldCurrentHoldingValue() {
        return OldCurrentHoldingValue;
    }

    public void setOldCurrentHoldingValue(String oldCurrentHoldingValue) {
        OldCurrentHoldingValue = oldCurrentHoldingValue;
    }

    public String getActualMTFQty() {
        return ActualMTFQty;
    }

    public void setActualMTFQty(String actualMTFQty) {
        ActualMTFQty = actualMTFQty;
    }

    public String getActualMTFPrice() {
        return ActualMTFPrice;
    }

    public void setActualMTFPrice(String actualMTFPrice) {
        ActualMTFPrice = actualMTFPrice;
    }

    public String getActualMTFAmount() {
        return ActualMTFAmount;
    }

    public void setActualMTFAmount(String actualMTFAmount) {
        ActualMTFAmount = actualMTFAmount;
    }

    public String getMTFQty() {
        return MTFQty;
    }

    public void setMTFQty(String MTFQty) {
        this.MTFQty = MTFQty;
    }

    public String getMTFValue() {
        return MTFValue;
    }

    public void setMTFValue(String MTFValue) {
        this.MTFValue = MTFValue;
    }

    public String getMTFPrice() {
        return MTFPrice;
    }

    public void setMTFPrice(String MTFPrice) {
        this.MTFPrice = MTFPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getActualDPQty() {
        return ActualDPQty;
    }

    public void setActualDPQty(String actualDPQty) {
        ActualDPQty = actualDPQty;
    }

    public String getActualPoolQty() {
        return ActualPoolQty;
    }

    public void setActualPoolQty(String actualPoolQty) {
        ActualPoolQty = actualPoolQty;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getActualDPPrice() {
        return ActualDPPrice;
    }

    public void setActualDPPrice(String actualDPPrice) {
        ActualDPPrice = actualDPPrice;
    }

    public String getActualPoolPrice() {
        return ActualPoolPrice;
    }

    public void setActualPoolPrice(String actualPoolPrice) {
        ActualPoolPrice = actualPoolPrice;
    }

    public String getActualDPAmount() {
        return ActualDPAmount;
    }

    public void setActualDPAmount(String actualDPAmount) {
        ActualDPAmount = actualDPAmount;
    }

    public String getActualPoolAmount() {
        return ActualPoolAmount;
    }

    public void setActualPoolAmount(String actualPoolAmount) {
        ActualPoolAmount = actualPoolAmount;
    }

    public String getcISINumber() {
        return cISINumber;
    }

    public void setcISINumber(String cISINumber) {
        this.cISINumber = cISINumber;
    }

    public String getTSellQty() {
        return TSellQty;
    }

    public void setTSellQty(String TSellQty) {
        this.TSellQty = TSellQty;
    }

    public String getTBuyQty() {
        return TBuyQty;
    }

    public void setTBuyQty(String TBuyQty) {
        this.TBuyQty = TBuyQty;
    }

    public String getTSellAmt() {
        return TSellAmt;
    }

    public void setTSellAmt(String TSellAmt) {
        this.TSellAmt = TSellAmt;
    }

    public String getTBuyAmt() {
        return TBuyAmt;
    }

    public void setTBuyAmt(String TBuyAmt) {
        this.TBuyAmt = TBuyAmt;
    }

    public String getTSellATP() {
        return TSellATP;
    }

    public void setTSellATP(String TSellATP) {
        this.TSellATP = TSellATP;
    }

    public String getTBuyATP() {
        return TBuyATP;
    }

    public void setTBuyATP(String TBuyATP) {
        this.TBuyATP = TBuyATP;
    }

    public String getTNQ() {
        return TNQ;//Today Net Qty
    }

    public void setTNQ(String TNQ) {
        this.TNQ = TNQ;
    }

    public String getDPQty() {
        return DPQty;
    }

    public void setDPQty(String DPQty) {
        this.DPQty = DPQty;
    }

    public String getDPValue() {
        return DPValue;
    }

    public void setDPValue(String DPValue) {
        this.DPValue = DPValue;
    }

    public String getDPPrice() {
        return DPPrice;
    }

    public void setDPPrice(String DPPrice) {
        this.DPPrice = DPPrice;
    }

    public String getPoolQty() {
        return PoolQty;
    }

    public void setPoolQty(String poolQty) {
        PoolQty = poolQty;
    }

    public String getPoolValue() {
        return PoolValue;
    }

    public void setPoolValue(String poolValue) {
        PoolValue = poolValue;
    }

    public String getPoolPrice() {
        return PoolPrice;
    }

    public void setPoolPrice(String poolPrice) {
        PoolPrice = poolPrice;
    }

    public String getNetHQty() {
        return NetHQty;
    }

    public void setNetHQty(String netHQty) {
        NetHQty = netHQty;
    }

    public String getNetHValue() {
        return NetHValue;
    }

    public void setNetHValue(String netHValue) {
        NetHValue = netHValue;
    }

    public String getNetHPrice() {
        return NetHPrice;
    }

    public void setNetHPrice(String netHPrice) {
        NetHPrice = netHPrice;
    }

    public String getSoldQty() {
        return SoldQty;
    }

    public void setSoldQty(String soldQty) {
        SoldQty = soldQty;
    }

    public String getPendingQty() {
        return PendingQty;
    }

    public void setPendingQty(String pendingQty) {
        PendingQty = pendingQty;
    }

    public String getRiskBlockQty() {
        return RiskBlockQty;
    }

    public void setRiskBlockQty(String riskBlockQty) {
        RiskBlockQty = riskBlockQty;
    }

    public String getFreeHoldingQty() {
        return FreeHoldingQty;
    }

    public void setFreeHoldingQty(String freeHoldingQty) {
        FreeHoldingQty = freeHoldingQty;
    }

    public String getPrevQty() {
        return PrevQty;
    }

    public void setPrevQty(String prevQty) {
        PrevQty = prevQty;
    }

    public String getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(String sellPrice) {
        SellPrice = sellPrice;
    }

    public String getBuyValue() {
        return BuyValue;
    }

    public void setBuyValue(String buyValue) {
        BuyValue = buyValue;
    }

    public String getSellValue() {
        return SellValue;
    }

    public void setSellValue(String sellValue) {
        SellValue = sellValue;
    }

    public String getPrevValue() {
        return PrevValue;
    }

    public void setPrevValue(String prevValue) {
        PrevValue = prevValue;
    }

    public String getMTM() {
        return MTM;
    }

    public void setMTM(String MTM) {
        this.MTM = MTM;
    }

    public String getHoldingPrice() {
        return HoldingPrice;
    }

    public void setHoldingPrice(String holdingPrice) {
        HoldingPrice = holdingPrice;
    }

    public DematHolding() {
    }

    public String getScripName() {
        return this.scripName;
    }

    public void setScripName(String scripName) {
        this.scripName = scripName;
    }

    public String getProfitLoss() {
        return this.ProfitLoss;
    }

    public void setProfitLoss(String ProfitLoss) {
        this.ProfitLoss = ProfitLoss;
    }

    public String getAvgBuyPrice() {
        return this.AvgBuyPrice;
    }

    public void setAvgBuyPrice(String AvgBuyPrice) {
        this.AvgBuyPrice = AvgBuyPrice;
    }

    public String getNSETradeSymbol() {
        return this.NSETradeSymbol;
    }

    public void setNSETradeSymbol(String NSETradeSymbol) {
        this.NSETradeSymbol = NSETradeSymbol;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBSETradeSymbol() {
        return this.BSETradeSymbol;
    }

    public void setBSETradeSymbol(String BSETradeSymbol) {
        this.BSETradeSymbol = BSETradeSymbol;
    }

    public String getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getExchange() {
        return this.Exchange;
    }

    public void setExchange(String Exchange) {
        this.Exchange = Exchange;
    }

    public String getAvailableForSell() {
        return this.AvailableForSell;
    }

    public void setAvailableForSell(String AvailableForSell) {
        this.AvailableForSell = AvailableForSell;
    }

    public String getOrderBlockedQty() {
        return this.OrderBlockedQty;
    }

    public void setOrderBlockedQty(String OrderBlockedQty) {
        this.OrderBlockedQty = OrderBlockedQty;
    }

    public String getLot() {
        return this.Lot;
    }

    public void setLot(String Lot) {
        this.Lot = Lot;
    }

    public String getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getInstrumentType() {
        return this.instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getTradeBlockedQty() {
        return this.TradeBlockedQty;
    }

    public void setTradeBlockedQty(String TradeBlockedQty) {
        this.TradeBlockedQty = TradeBlockedQty;
    }

    public String getBFQuantity() {
        return this.BFQuantity;
    }

    public void setBFQuantity(String BFQuantity) {
        this.BFQuantity = BFQuantity;
    }

    public String getTickSize() {
        return this.TickSize;
    }

    public void setTickSize(String TickSize) {
        this.TickSize = TickSize;
    }

    public String getLTP() {

        //this.Ltp="0";
        return this.Ltp;
    }

    public void setLTP(String LTP) {
        this.Ltp = LTP;
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getNSEToken() {
        return this.NSEToken;
    }

    public void setNSEToken(String NSEToken) {
        this.NSEToken = NSEToken;
    }

    public String getBSEToken() {
        return this.BSEToken;
    }

    public void setBSEToken(String BSEToken) {
        this.BSEToken = BSEToken;
    }

    public String getHoldingQty() {
        return HoldingQty;
    }

    public void setHoldingQty(String holdingQty) {
        HoldingQty = holdingQty;
    }


    public String getHoldingValue() {
        return HoldingValue;
    }

    public void setHoldingValue(String holdingValue) {
        HoldingValue = holdingValue;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("scripName", this.scripName);
        jo.put("NSETradeSymbol", this.NSETradeSymbol);
        jo.put("product", this.product);
        jo.put("BSETradeSymbol", this.BSETradeSymbol);
        jo.put("uniqueID", this.uniqueID);
        jo.put("Exchange", this.Exchange);
        jo.put("AvailableForSell", this.AvailableForSell);
        jo.put("OrderBlockedQty", this.OrderBlockedQty);
        jo.put("Lot", this.Lot);
        jo.put("multiplier", this.multiplier);
        jo.put("instrumentType", this.instrumentType);
        jo.put("assetType", this.assetType);
        jo.put("TradeBlockedQty", this.TradeBlockedQty);
        jo.put("BFQuantity", this.BFQuantity);
        jo.put("TickSize", this.TickSize);
        jo.put("Ltp", this.Ltp);
        jo.put("Value", this.Value);
        jo.put("NSEToken", this.NSEToken);
        jo.put("BSEToken", this.BSEToken);
        jo.put("AvgBuyPrice", this.AvgBuyPrice);
        jo.put("ProfitLoss", this.ProfitLoss);
        jo.put("HoldingQty", this.HoldingQty);
        jo.put("HoldingPrice", this.HoldingPrice);
        jo.put("HoldingValue", this.HoldingValue);
        jo.put("BuyValue", this.BuyValue);
        jo.put("SellValue", this.SellValue);
        jo.put("PrevValue", this.PrevValue);
        jo.put("PrevQty", this.PrevQty);
        jo.put("SellPrice", this.SellPrice);
        jo.put("Close", this.close);
        jo.put("HPrice", this.HPrice);
        jo.put("Qty", this.Qty);
        jo.put("isin", this.isin);
        jo.put("Close", this.Close);


        jo.put("symbol", this.symbol);
        jo.put("ActualDPQty", this.ActualDPQty);
        jo.put("ActualPoolQty", this.ActualPoolQty);
        jo.put("gscid", this.gscid);
        jo.put("ActualDPPrice", this.ActualDPPrice);
        jo.put("ActualPoolPrice", this.ActualPoolPrice);
        jo.put("ActualDPAmount", this.ActualDPAmount);
        jo.put("ActualPoolAmount", this.ActualPoolAmount);
        jo.put("cISINumber", this.cISINumber);
        jo.put("TSellQty", this.TSellQty);
        jo.put("TBuyQty", this.TBuyQty);
        jo.put("TSellAmt", this.TSellAmt);
        jo.put("TBuyAmt", this.TBuyAmt);
        jo.put("TSellATP", this.TSellATP);
        jo.put("TBuyATP", this.TBuyATP);
        jo.put("TNQ", this.TNQ);
        jo.put("DPQty", this.DPQty);
        jo.put("DPValue", this.DPValue);
        jo.put("DPPrice", this.DPPrice);
        jo.put("PoolQty", this.PoolQty);
        jo.put("PoolValue", this.PoolValue);
        jo.put("PoolPrice", this.PoolPrice);
        jo.put("NetHQty", this.NetHQty);
        jo.put("NetHValue", this.NetHValue);
        jo.put("NetHPrice", this.NetHPrice);
        jo.put("SoldQty", this.SoldQty);
        jo.put("RiskBlockQty", this.RiskBlockQty);
        jo.put("PendingQty", this.PendingQty);
        jo.put("FreeHoldingQty", this.FreeHoldingQty);
        jo.put("instrument", this.instrument);


        jo.put("ActualMTFQty", this.ActualMTFQty);
        jo.put("ActualMTFPrice", this.ActualMTFPrice);
        jo.put("ActualMTFAmount", this.ActualMTFAmount);
        jo.put("MTFQty", this.MTFQty);
        jo.put("MTFValue", this.MTFValue);
        jo.put("MTFPrice", this.MTFPrice);

        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.scripName = jo.optString("scripName");
        this.NSETradeSymbol = jo.optString("NSETradeSymbol");
        this.product = jo.optString("product");
        this.BSETradeSymbol = jo.optString("BSETradeSymbol");
        this.uniqueID = jo.optString("uniqueID");
        this.Exchange = jo.optString("Exchange");
        this.AvailableForSell = jo.optString("AvailableForSell");
        this.OrderBlockedQty = jo.optString("OrderBlockedQty");
        this.Lot = jo.optString("Lot");
        this.multiplier = jo.optString("multiplier");
        this.instrumentType = jo.optString("instrumentType");
        this.assetType = jo.optString("assetType");
        this.TradeBlockedQty = jo.optString("TradeBlockedQty");
        this.BFQuantity = jo.optString("BFQuantity");
        this.TickSize = jo.optString("TickSize");
        this.Ltp = jo.optString("Ltp");
        this.Value = jo.optString("Value");
        this.NSEToken = jo.optString("NSEToken");
        this.BSEToken = jo.optString("BSEToken");
        this.AvgBuyPrice = jo.optString("AvgBuyPrice");
        this.ProfitLoss = jo.optString("ProfitLoss");
        this.HoldingQty = jo.optString("HoldingQty");
        this.HoldingPrice = jo.optString("HoldingPrice");
        this.HoldingValue = jo.optString("HoldingValue");
        this.BuyValue = jo.optString("BuyValue");
        this.SellValue = jo.optString("SellValue");
        this.instrument = jo.optString("instrument");
        this.close = jo.optString("Close");
        if (jo.optString("PrevValue").equalsIgnoreCase("")) {
            this.PrevValue = "0.00";
        } else {
            this.PrevValue = jo.optString("PrevValue");
        }
        if (jo.optString("PrevQty").equalsIgnoreCase("")) {
            this.PrevQty = "0";
        } else {
            this.PrevQty = jo.optString("PrevQty");
        }
        if (jo.optString("SellPrice").equalsIgnoreCase("")) {
            this.SellPrice = "0.00";
        } else {
            this.SellPrice = jo.optString("SellPrice");
        }


        this.symbol = jo.optString("symbol");
        this.ActualDPQty = jo.optString("ActualDPQty");
        this.ActualPoolQty = jo.optString("ActualPoolQty");
        this.gscid = jo.optString("gscid");
        this.ActualDPPrice = jo.optString("ActualDPPrice");
        this.ActualPoolPrice = jo.optString("ActualPoolPrice");
        this.ActualDPAmount = jo.optString("ActualDPAmount");
        this.ActualPoolAmount = jo.optString("ActualPoolAmount");
        this.cISINumber = jo.optString("cISINumber");
        this.TSellQty = jo.optString("TSellQty");
        this.TBuyQty = jo.optString("TBuyQty");
        this.TSellAmt = jo.optString("TSellAmt");
        this.TBuyAmt = jo.optString("TBuyAmt");
        this.TSellATP = jo.optString("TSellATP");
        this.TBuyATP = jo.optString("TBuyATP");
        this.TNQ = jo.optString("TNQ");
        this.DPQty = jo.optString("DPQty");
        this.DPValue = jo.optString("DPValue");
        this.DPPrice = jo.optString("DPPrice");
        this.PoolQty = jo.optString("PoolQty");
        this.PoolValue = jo.optString("PoolValue");
        this.PoolPrice = jo.optString("PoolPrice");

        this.NetHQty = jo.optString("NetHQty");
        this.NetHValue = jo.optString("NetHValue");
        this.NetHPrice = jo.optString("NetHPrice");
        this.SoldQty = jo.optString("SoldQty");
        this.RiskBlockQty = jo.optString("RiskBlockQty");
        this.PendingQty = jo.optString("PendingQty");
        this.FreeHoldingQty = jo.optString("FreeHoldingQty");

        this.ActualMTFQty = jo.optString("ActualMTFQty");
        this.ActualMTFPrice = jo.optString("ActualMTFPrice");
        this.ActualMTFAmount = jo.optString("ActualMTFAmount");
        this.MTFQty = jo.optString("MTFQty");
        this.MTFValue = jo.optString("MTFValue");
        this.MTFPrice = jo.optString("MTFPrice");
        this.HPrice = jo.optString("HPrice");
        this.Qty = jo.optString("Qty");
        this.isin = jo.optString("isin");
        this.Close = jo.optString("Close");


        return this;
    }

}

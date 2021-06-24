package com.acumengroup.greekmain.core.model.marketssinglescrip;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketsSingleScripResponse
        implements GreekRequestModel, GreekResponseModel {

    private String ylow;
    private String tot_sellQty;
    private String tot_buyQty;
    private String bidqty;
    private String lot;
    private String yhigh;
    private String askqty;
    private String p_change;
    private String last;
    private String avgPrice;
    private String high;
    private String message;
    private String change;
    private String asset_type;
    private String open;
    private String total_buy;
    private String total_sell;
    private String ask;
    private String oi;
    private String lut;
    private String close;
    private String tot_vol;
    private List<Level2> level2 = new ArrayList();
    private String symbol;
    private String ltt;
    private String bid;
    private Boolean isError;
    private Boolean isAllowed=true;
    private Boolean isSqOff;
    private Boolean isRestrictFreshOrder=false;
    private String exch;
    private String name;
    private String reason;
    private String low;
    private String description;
    private String token;
    private String freeze_qty;
    private String sqOffQty;
    private String instrument;
    private String expiryDate;
    private Boolean isAllowedFO=true;
    private Boolean isAllowedIntraday=true;
    private String prevQty;
    private String todQty;
    private String strikeprice;
    private String optiontype;
    private String highRange;
    private String tickSize;
    private String lowRange;
    private Boolean isIntradayTimerSet=false;
    private String timeForTimer;
    private Boolean isAllowedDelivery=true;
    private Boolean isAllowedShortSell=true;
    private Boolean isAllowedT2TSell=true;
    private Boolean isT2TScript=false;
    private Boolean isMTFScript;
    private String isinumber;
    private String atp;

    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTot_sellQty() {
        return tot_sellQty;
    }

    public void setTot_sellQty(String tot_sellQty) {
        this.tot_sellQty = tot_sellQty;
    }

    public String getTot_buyQty() {
        return tot_buyQty;
    }

    public void setTot_buyQty(String tot_buyQty) {
        this.tot_buyQty = tot_buyQty;
    }

    public String getAtp() {
        return atp;
    }

    public void setAtp(String atp) {
        this.atp = atp;
    }

    public String getIsinumber() {
        return isinumber;
    }

    public void setIsinumber(String isinumber) {
        this.isinumber = isinumber;
    }

    public Boolean getMTFScript() {
        return isMTFScript;
    }

    public void setMTFScript(Boolean MTFScript) {
        isMTFScript = MTFScript;
    }

    public String getTotal_buy() {
        return total_buy;
    }

    public void setTotal_buy(String total_buy) {
        this.total_buy = total_buy;
    }

    public String getTotal_sell() {
        return total_sell;
    }

    public void setTotal_sell(String total_sell) {
        this.total_sell = total_sell;
    }

    public Boolean getIntradayTimerSet() {
        return isIntradayTimerSet;
    }

    public void setIntradayTimerSet(Boolean intradayTimerSet) {
        isIntradayTimerSet = intradayTimerSet;
    }

    public String getTimeForTimer() {
        return timeForTimer;
    }

    public void setTimeForTimer(String timeForTimer) {
        this.timeForTimer = timeForTimer;
    }

    public String getHighRange() {
        return highRange;
    }

    public void setHighRange(String highRange) {
        this.highRange = highRange;
    }

    public String getLowRange() {
        return lowRange;
    }

    public void setLowRange(String lowRange) {
        this.lowRange = lowRange;
    }

    public String getStrikeprice() {
        return strikeprice;
    }

    public void setStrikeprice(String strikeprice) {
        this.strikeprice = strikeprice;
    }

    public String getOptiontype() {
        return optiontype;
    }

    public void setOptiontype(String optiontype) {
        this.optiontype = optiontype;
    }

    public Boolean getAllowedIntraday() {
        return isAllowedIntraday;
    }

    public void setAllowedIntraday(Boolean allowedIntraday) {
        isAllowedIntraday = allowedIntraday;
    }

    public String getPrevQty() {
        return prevQty;
    }

    public void setPrevQty(String prevQty) {
        this.prevQty = prevQty;
    }

    public String getTodQty() {
        return todQty;
    }

    public void setTodQty(String todQty) {
        this.todQty = todQty;
    }

    public Boolean getAllowedFO() {
        return isAllowedFO;
    }

    public void setAllowedFO(Boolean allowedFO) {
        isAllowedFO = allowedFO;
    }

    public Boolean getRestrictFreshOrder() {
        return isRestrictFreshOrder;
    }

    public void setRestrictFreshOrder(Boolean restrictFreshOrder) {
        isRestrictFreshOrder = restrictFreshOrder;
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

    public String getSqOffQty() {
        return sqOffQty;
    }

    public void setSqOffQty(String sqOffQty) {
        this.sqOffQty = sqOffQty;
    }

    public Boolean getSqOff() {
        return isSqOff;
    }

    public void setSqOff(Boolean sqOff) {
        isSqOff = sqOff;
    }

    public Boolean getAllowed() {
        return isAllowed;
    }

    public void setAllowed(Boolean allowed) {
        isAllowed = allowed;
    }

    public String getFreeze_qty() {
        return freeze_qty;
    }

    public void setFreeze_qty(String freeze_qty) {
        this.freeze_qty = freeze_qty;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYlow() {
        return this.ylow;
    }

    public void setYlow(String ylow) {
        this.ylow = ylow;
    }

    public String getBidqty() {
        return this.bidqty;
    }

    public void setBidqty(String bidqty) {
        this.bidqty = bidqty;
    }

    public String getLot() {
        return this.lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getYhigh() {
        return this.yhigh;
    }

    public void setYhigh(String yhigh) {
        this.yhigh = yhigh;
    }

    public String getAskqty() {
        return this.askqty;
    }

    public void setAskqty(String askqty) {
        this.askqty = askqty;
    }

    public String getP_change() {
        return this.p_change;
    }

    public void setP_change(String p_change) {
        this.p_change = p_change;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getAvgPrice() {
        return this.avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getHigh() {
        return this.high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChange() {
        return this.change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getAsset_type() {
        return this.asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getOpen() {
        return this.open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getAsk() {
        return this.ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getOi() {
        return this.oi;
    }

    public void setOi(String oi) {
        this.oi = oi;
    }

    public String getClose() {
        return this.close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getTot_vol() {
        return this.tot_vol;
    }

    public void setTot_vol(String tot_vol) {
        this.tot_vol = tot_vol;
    }

    public List<Level2> getLevel2() {
        return this.level2;
    }

    public void setLevel2(List<Level2> level2) {
        this.level2 = level2;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLtt() {
        return this.ltt;
    }

    public void setLtt(String ltt) {
        this.ltt = ltt;
    }

    public String getBid() {
        return this.bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Boolean getIsError() {
        return this.isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    public String getExch() {
        return this.exch;
    }

    public void setExch(String exch) {
        this.exch = exch;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLow() {
        return this.low;
    }

    public void setLow(String low) {
        this.low = low;
    }


    public Boolean getAllowedDelivery() {
        return isAllowedDelivery;
    }

    public void setAllowedDelivery(Boolean allowedDelivery) {
        isAllowedDelivery = allowedDelivery;
    }

    public Boolean getAllowedShortSell() {
        return isAllowedShortSell;
    }

    public void setAllowedShortSell(Boolean allowedShortSell) {
        isAllowedShortSell = allowedShortSell;
    }

    public Boolean getAllowedT2TSell() {
        return isAllowedT2TSell;
    }

    public void setAllowedT2TSell(Boolean allowedT2TSell) {
        isAllowedT2TSell = allowedT2TSell;
    }

    public Boolean getT2TScript() {
        return isT2TScript;
    }

    public void setT2TScript(Boolean t2TScript) {
        isT2TScript = t2TScript;
    }

    public String getLut() {
        return lut;
    }

    public void setLut(String lut) {
        this.lut = lut;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("tot_sellQty", this.tot_sellQty);
        jo.put("tickSize", this.tickSize);
        jo.put("tot_buyQty", this.tot_buyQty);
        jo.put("ylow", this.ylow);
        jo.put("isinumber", this.isinumber);
        jo.put("bidqty", this.bidqty);
        jo.put("lot", this.lot);
        jo.put("yhigh", this.yhigh);
        jo.put("askqty", this.askqty);
        jo.put("p_change", this.p_change);
        jo.put("last", this.last);
        jo.put("avgPrice", this.avgPrice);
        jo.put("high", this.high);
        jo.put("message", this.message);
        jo.put("change", this.change);
        jo.put("asset_type", this.asset_type);
        jo.put("open", this.open);
        jo.put("ask", this.ask);
        jo.put("oi", this.oi);
        jo.put("lut", this.lut);
        jo.put("close", this.close);
        jo.put("tot_vol", this.tot_vol);
        jo.put("description", this.description);
        jo.put("token", this.token);
        jo.put("freezQty", this.freeze_qty);
        jo.put("instrument", this.instrument);
        jo.put("expiryDate", this.expiryDate);
        jo.put("isAllowedFO", this.isAllowedFO);
        jo.put("isAllowedIntraday", this.isAllowedIntraday);
        jo.put("prevQty", this.prevQty);
        jo.put("todQty", this.todQty);
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.level2.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("level2", ja1);
        jo.put("symbol", this.symbol);
        jo.put("ltt", this.ltt);
        jo.put("bid", this.bid);
        jo.put("isError", this.isError);
        jo.put("isAllowed", this.isAllowed);
        jo.put("isSqOff", this.isSqOff);
        jo.put("isRestrictFreshOrder", this.isRestrictFreshOrder);
        jo.put("exch", this.exch);
        jo.put("reason", this.reason);
        jo.put("name", this.name);
        jo.put("low", this.low);
        jo.put("optiontype", this.optiontype);
        jo.put("strikeprice", this.strikeprice);
        jo.put("sqOffQty", this.sqOffQty);
        jo.put("highRange", this.highRange);
        jo.put("lowRange", this.lowRange);
        jo.put("isIntradayTimerSet", this.isIntradayTimerSet);
        jo.put("timeForTimer", this.timeForTimer);
        jo.put("isAllowedDelivery", this.isAllowedDelivery);
        jo.put("isAllowedShortSell", this.isAllowedShortSell);
        jo.put("isAllowedT2TSell", this.isAllowedT2TSell);
        jo.put("isT2TScript", this.isT2TScript);
        jo.put("isMTFScript", this.isMTFScript);
        jo.put("atp", this.atp);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.tickSize = jo.optString("tickSize");
        this.tot_sellQty = jo.optString("tot_sellQty");
        this.tot_buyQty = jo.optString("tot_buyQty");
        this.ylow = jo.optString("ylow");
        this.isinumber = jo.optString("isinumber");
        this.bidqty = jo.optString("bidqty");
        this.lot = jo.optString("lot");
        this.yhigh = jo.optString("yhigh");
        this.askqty = jo.optString("askqty");
        this.p_change = jo.optString("p_change");
        this.last = jo.optString("last");
        this.avgPrice = jo.optString("avgPrice");
        this.high = jo.optString("high");
        this.message = jo.optString("message");
        this.change = jo.optString("change");
        this.asset_type = jo.optString("asset_type");
        this.open = jo.optString("open");
        this.ask = jo.optString("ask");
        this.oi = jo.optString("oi");
        this.lut = jo.optString("lut");
        this.close = jo.optString("close");
        this.tot_vol = jo.optString("tot_vol");
        this.description = jo.optString("description");
        this.token = jo.optString("token");
        this.freeze_qty = jo.optString("freezQty");
        this.instrument = jo.optString("instrument");
        this.expiryDate = jo.optString("expiryDate");
        if (jo.has("level2")) {
            JSONArray ja1 = jo.getJSONArray("level2");
            this.level2 = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    Level2 data = new Level2();
                    data.fromJSON((JSONObject) o);
                    this.level2.add(data);
                } else {
                    this.level2.add((Level2) o);
                }
            }
        }
        this.symbol = jo.optString("symbol");
        this.atp = jo.optString("atp");
        this.ltt = jo.optString("ltt");
        this.bid = jo.optString("bid");
        this.isError = Boolean.valueOf(jo.optBoolean("isError"));
        this.isAllowed = Boolean.valueOf(jo.optBoolean("isAllowed"));
        this.isRestrictFreshOrder = Boolean.valueOf(jo.optBoolean("isRestrictFreshOrder"));
        this.isAllowedFO = Boolean.valueOf(jo.optBoolean("isAllowedFO"));
        this.isAllowedIntraday = Boolean.valueOf(jo.optBoolean("isAllowedIntraday"));
        this.isSqOff = Boolean.valueOf(jo.optBoolean("isSqOff"));
        this.exch = jo.optString("exch");
        this.reason = jo.optString("reason");
        this.name = jo.optString("name");
        this.low = jo.optString("low");
        this.sqOffQty = jo.optString("sqOffQty");
        this.prevQty = jo.optString("prevQty");
        this.todQty = jo.optString("todQty");
        this.strikeprice = jo.optString("strikeprice");
        this.optiontype = jo.optString("optiontype");
        this.highRange = jo.optString("highRange");
        this.lowRange = jo.optString("lowRange");
        this.isIntradayTimerSet = Boolean.valueOf(jo.optBoolean("isIntradayTimerSet"));
        this.timeForTimer = jo.optString("timeForTimer");
        this.isAllowedDelivery = Boolean.valueOf(jo.optBoolean("isAllowedDelivery"));
        this.isAllowedShortSell = Boolean.valueOf(jo.optBoolean("isAllowedShortSell"));
        this.isAllowedT2TSell = Boolean.valueOf(jo.optBoolean("isAllowedT2TSell"));
        this.isT2TScript = Boolean.valueOf(jo.optBoolean("isT2TScript"));
        this.isMTFScript = Boolean.valueOf(jo.optBoolean("isMTFScript"));
        return this;
    }
}



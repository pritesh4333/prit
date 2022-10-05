package com.acumengroup.greekmain.core.model.streamerbroadcast;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamerBroadcastResponse
        implements GreekRequestModel, GreekResponseModel {
    private String ylow;
    private String bidqty;
    private String tot_buyQty;
    private String tot_sellQty;
    private String yhigh;
    private String tbq;
    private String askqty;
    private String p_change;
    private String last;
    private String high;
    private String change;
    private String asset_type;
    private String open;
    private String ask;
    private String oi;
    private String close;
    private String tot_vol;
    private List<Level2> level2 = new ArrayList();
    private String symbol;
    private String ltt;
    private String taq;
    private String bid;
    private String exch;
    private String low;
    private String name;
    private String ATP;
    private String lut;
    private String depth; //0=no depth, 1=with depth

    private String vega;
    private String theta;
    private String delta;
    private String gamma;
    private String indexCode;

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getVega() {
        return vega;
    }

    public void setVega(String vega) {
        this.vega = vega;
    }

    public String getTheta() {
        return theta;
    }

    public void setTheta(String theta) {
        this.theta = theta;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    public String getGamma() {
        return gamma;
    }

    public void setGamma(String gamma) {
        this.gamma = gamma;
    }

    public String getTot_buyQty() {
        return tot_buyQty;
    }

    public void setTot_buyQty(String tot_buyQty) {
        this.tot_buyQty = tot_buyQty;
    }

    public String getTot_sellQty() {
        return tot_sellQty;
    }

    public void setTot_sellQty(String tot_sellQty) {
        this.tot_sellQty = tot_sellQty;
    }

    public String getLut() {
        return lut;
    }

    public void setLut(String lut) {
        this.lut = lut;
    }

    public String getATP() {
        return ATP;
    }

    public void setATP(String ATP) {
        this.ATP = ATP;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
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

    public String getYhigh() {
        return this.yhigh;
    }

    public void setYhigh(String yhigh) {
        this.yhigh = yhigh;
    }

    public String getTbq() {
        return this.tbq;
    }

    public void setTbq(String tbq) {
        this.tbq = tbq;
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

    public String getHigh() {
        return this.high;
    }

    public void setHigh(String high) {
        this.high = high;
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

    public String getTaq() {
        return this.taq;
    }

    public void setTaq(String taq) {
        this.taq = taq;
    }

    public String getBid() {
        return this.bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getExch() {
        return this.exch;
    }

    public void setExch(String exch) {
        this.exch = exch;
    }

    public String getLow() {
        return this.low;
    }

    public void setLow(String low) {
        this.low = low;
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
        jo.put("l52w", this.ylow);
        jo.put("bidqty", this.bidqty);
        jo.put("h52w", this.yhigh);
        jo.put("tbq", this.tbq);
        jo.put("askqty", this.askqty);
        jo.put("p_change", this.p_change);
        jo.put("ltp", this.last);
        jo.put("high", this.high);
        jo.put("change", this.change);
        jo.put("asset_type", this.asset_type);
        jo.put("open", this.open);
        jo.put("ask", this.ask);
        jo.put("oi", this.oi);
        jo.put("close", this.close);
        jo.put("tot_vol", this.tot_vol);
        jo.put("depth", this.depth);
        jo.put("atp", this.ATP);
        jo.put("lut", this.lut);
        jo.put("tot_sellQty", this.tot_sellQty);
        jo.put("tot_buyQty", this.tot_buyQty);

        jo.put("theta", this.theta);
        jo.put("gamma", this.gamma);
        jo.put("vega", this.vega);
        jo.put("delta", this.delta);
        jo.put("indexCode", this.indexCode);

        if (this.depth.equals("1")) {
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
        }

        jo.put("symbol", this.symbol);
        jo.put("ltt", this.ltt);
        jo.put("taq", this.taq);
        jo.put("bid", this.bid);
        jo.put("exch", this.exch);
        jo.put("low", this.low);
        jo.put("name", this.name);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ylow = jo.optString("l52w");
        this.bidqty = jo.optString("bidqty");
        this.yhigh = jo.optString("h52w");
        this.tbq = jo.optString("tbq");
        this.askqty = jo.optString("askqty");
        this.p_change = jo.optString("p_change");
        this.last = jo.optString("ltp");
        this.high = jo.optString("high");
        this.change = jo.optString("change");
        this.asset_type = jo.optString("asset_type");
        this.open = jo.optString("open");
        this.ask = jo.optString("ask");
        this.oi = jo.optString("oi");
        this.close = jo.optString("close");
        this.tot_vol = jo.optString("tot_vol");
        this.depth = jo.optString("depth");
        this.ATP = jo.optString("atp");
        this.lut = jo.optString("lut");
        this.tot_buyQty = jo.optString("tot_buyQty");
        this.tot_sellQty = jo.optString("tot_sellQty");

        this.vega = jo.optString("vega");
        this.theta = jo.optString("theta");
        this.delta = jo.optString("delta");
        this.gamma = jo.optString("gamma");
        this.indexCode = jo.optString("indexCode");

        if (jo.has("level2")) {
            JSONArray ja1 = jo.getJSONArray("level2");
            int length = ja1.length();
            this.level2 = new ArrayList(length);
            for (int i = 0; i < length; i++)//todo
            {
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
        this.ltt = jo.optString("ltt");
        this.taq = jo.optString("taq");
        this.bid = jo.optString("bid");
        this.exch = jo.optString("exch");
        this.low = jo.optString("low");
        this.name = jo.optString("name");
        return this;
    }
}



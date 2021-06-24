package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class OIData implements GreekRequestModel, GreekResponseModel {
    String lOurToken;
    String lOpenInterest;
    String lPrevOpenInterest;
    String lVolume;
    String dLtp;
    String dNetChange;
    String lBidQty;
    String lBidPrice;
    String lAskQty;
    String AskPrice;
    String lClosingPrice;
    String callput;
    String strike;
    String expiryDate;

    String delta;
    String gamma;
    String vega;
    String theta;


    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getCallput() {
        return callput;
    }

    public void setCallput(String callput) {
        this.callput = callput;
    }

    public String getStrike() {
        return strike;
    }

    public void setStrike(String strike) {
        this.strike = strike;
    }

    public String getlOurToken() {
        return lOurToken;
    }

    public void setlOurToken(String lOurToken) {
        this.lOurToken = lOurToken;
    }

    public String getlOpenInterest() {
        return lOpenInterest;
    }

    public void setlOpenInterest(String lOpenInterest) {
        this.lOpenInterest = lOpenInterest;
    }

    public String getlPrevOpenInterest() {
        return lPrevOpenInterest;
    }

    public void setlPrevOpenInterest(String lPrevOpenInterest) {
        this.lPrevOpenInterest = lPrevOpenInterest;
    }

    public String getlVolume() {
        return lVolume;
    }

    public void setlVolume(String lVolume) {
        this.lVolume = lVolume;
    }

    public String getdLtp() {
        return dLtp;
    }

    public void setdLtp(String dLtp) {
        this.dLtp = dLtp;
    }

    public String getdNetChange() {
        return dNetChange;
    }

    public void setdNetChange(String dNetChange) {
        this.dNetChange = dNetChange;
    }

    public String getlBidQty() {
        return lBidQty;
    }

    public void setlBidQty(String lBidQty) {
        this.lBidQty = lBidQty;
    }

    public String getlBidPrice() {
        return lBidPrice;
    }

    public void setlBidPrice(String lBidPrice) {
        this.lBidPrice = lBidPrice;
    }

    public String getlAskQty() {
        return lAskQty;
    }

    public void setlAskQty(String lAskQty) {
        this.lAskQty = lAskQty;
    }

    public String getAskPrice() {
        return AskPrice;
    }

    public void setAskPrice(String askPrice) {
        AskPrice = askPrice;
    }

    public String getlClosingPrice() {
        return lClosingPrice;
    }

    public void setlClosingPrice(String lClosingPrice) {
        this.lClosingPrice = lClosingPrice;
    }



    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("lOurToken", this.lOurToken);
        jo.put("lOpenInterest", this.lOpenInterest);
        jo.put("lPrevOpenInterest", this.lPrevOpenInterest);
        jo.put("lVolume", this.lVolume);
        jo.put("lClosingPrice", this.lClosingPrice);
        jo.put("dLTP", this.dLtp);
        jo.put("dNetChange", this.dNetChange);
        jo.put("lBidQty", this.lBidQty);
        jo.put("lBidPrice", this.lBidPrice);
        jo.put("lAskQty", this.lAskQty);
        jo.put("AskPrice", this.AskPrice);
        jo.put("callput", this.callput);
        jo.put("strike", this.strike);
        jo.put("expiryDate", this.expiryDate);

        jo.put("theta", this.theta);
        jo.put("gamma", this.gamma);
        jo.put("vega", this.vega);
        jo.put("delta", this.delta);
        return jo;
    }
    /*String lOurToken;
      String lOpenInterest;
      String lPrevOpenInterest;
      String lVolume;
      String dLtp;
      String dNetChange;
      String lBidQty;
      String lBidPrice;
      String lAskQty;
      String AskPrice;
      String lClosingPrice;*/
    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.lOurToken = jo.optString("lOurToken");
        this.lOpenInterest = jo.optString("lOpenInterest");
        this.lPrevOpenInterest = jo.optString("lPrevOpenInterest");
        this.lVolume = jo.optString("lVolume");
        this.lClosingPrice = jo.optString("lClosingPrice");
        this.dLtp = jo.optString("dLtp");
        this.dNetChange = jo.optString("dNetChange");
        this.lBidQty = jo.optString("lBidQty");
        this.lBidPrice = jo.optString("lBidPrice");
        this.lAskQty = jo.optString("lAskQty");
        this.AskPrice = jo.optString("AskPrice");
        this.strike = jo.optString("strike");
        this.callput = jo.optString("callput");
        this.expiryDate = jo.optString("expiryDate");

        this.theta = jo.optString("theta");
        this.gamma = jo.optString("gamma");
        this.delta = jo.optString("delta");
        this.vega = jo.optString("vega");
        return this;
    }
}



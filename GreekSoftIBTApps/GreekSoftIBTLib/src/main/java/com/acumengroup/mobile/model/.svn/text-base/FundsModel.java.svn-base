package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class FundsModel implements GreekRequestModel, GreekResponseModel {
    private int id;
    private String category;
    private String schemeName;
    private String starRating;
    private String riskfactor;
    private double returns;
    private int image;
    private String ISIN;
    private String schemeCode;
    private String oneWeekRet;
    private String oneMonthRet;
    private String threeMonthRet;
    private String oneYearRet;
    private String threeYearRet;
    private String fiveYearRet;
    private String incRet;
    private String NAV;
    private String AUM;
    private String update_return_per;
    private String update_return_label;
    private boolean updateFlag = false;

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }


    public String getUpdate_return_per() {
        return update_return_per;
    }

    public void setUpdate_return_per(String update_return_per) {
        this.update_return_per = update_return_per;
    }

    public String getUpdate_return_label() {
        return update_return_label;
    }

    public void setUpdate_return_label(String update_return_label) {
        this.update_return_label = update_return_label;
    }

    public String getOneWeekRet() {
        return oneWeekRet;
    }

    public void setOneWeekRet(String oneWeekRet) {
        this.oneWeekRet = oneWeekRet;
    }

    public String getOneMonthRet() {
        return oneMonthRet;
    }

    public void setOneMonthRet(String oneMonthRet) {
        this.oneMonthRet = oneMonthRet;
    }

    public String getThreeMonthRet() {
        return threeMonthRet;
    }

    public void setThreeMonthRet(String threeMonthRet) {
        this.threeMonthRet = threeMonthRet;
    }

    public String getOneYearRet() {
        return oneYearRet;
    }

    public void setOneYearRet(String oneYearRet) {
        this.oneYearRet = oneYearRet;
    }

    public String getThreeYearRet() {
        return threeYearRet;
    }

    public void setThreeYearRet(String threeYearRet) {
        this.threeYearRet = threeYearRet;
    }

    public String getFiveYearRet() {
        return fiveYearRet;
    }

    public void setFiveYearRet(String fiveYearRet) {
        this.fiveYearRet = fiveYearRet;
    }

    public String getIncRet() {
        return incRet;
    }

    public void setIncRet(String incRet) {
        this.incRet = incRet;
    }

    public String getNAV() {
        return NAV;
    }

    public void setNAV(String NAV) {
        this.NAV = NAV;
    }

    public String getAUM() {
        return AUM;
    }

    public void setAUM(String AUM) {
        this.AUM = AUM;
    }

    public String getISIN() {
        return ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public double getReturns() {
        return returns;
    }

    public void setReturns(double returns) {
        this.returns = returns;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public String getRiskfactor() {
        return riskfactor;
    }

    public void setRiskfactor(String riskfactor) {
        this.riskfactor = riskfactor;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("category", this.category);
        jo.put("schemeName", this.schemeName);
        jo.put("starRating", this.starRating);
        jo.put("riskfactor", this.riskfactor);
        jo.put("returns", this.returns);
        jo.put("ISIN", this.ISIN);
        jo.put("schemeCode", this.schemeCode);
        jo.put("oneWeekRet", this.oneWeekRet);
        jo.put("oneMonthRet", this.oneMonthRet);
        jo.put("threeMonthRet", this.threeMonthRet);
        jo.put("oneYearRet", this.oneYearRet);
        jo.put("threeYearRet", this.threeYearRet);
        jo.put("fiveYearRet", this.fiveYearRet);
        jo.put("incRet", this.incRet);
        jo.put("NAV", this.NAV);
        jo.put("AUM", this.AUM);
        return jo;

    }


    @Override
    public GreekResponseModel fromJSON(JSONObject res) throws JSONException {

        this.category = res.optString("category");
        this.schemeName = res.optString("schemeName");
        this.starRating = res.optString("starRating");
        this.riskfactor = res.optString("riskfactor");
        this.returns = res.optDouble("returns");
        this.ISIN = res.optString("ISIN");
        this.schemeCode = res.optString("schemeCode");
        this.oneWeekRet = res.optString("oneWeekRet");
        this.oneMonthRet = res.optString("oneMonthRet");
        this.threeMonthRet = res.optString("threeMonthRet");
        this.oneYearRet = res.optString("oneYearRet");
        this.threeYearRet = res.optString("threeYearRet");
        this.fiveYearRet = res.optString("fiveYearRet");
        this.incRet = res.optString("incRet");
        this.NAV = res.optString("NAV");
        this.AUM = res.optString("AUM");
        return this;
    }
}
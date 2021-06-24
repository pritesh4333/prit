package com.acumengroup.greekmain.core.model.FiiDiiActivity;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class FpiInvestmentDetails implements GreekRequestModel, GreekResponseModel {
    private String reportingDate;
    private String logtime;
    private String last_updated_time;
    private String investment_category;
    private String investment_route;
    private String gross_purchases;
    private String gross_sales;
    private String net_investement_inr;
    private String net_investment_usd;
    private String conversion;

    private String derivative_product;
    private String buy_contracts;
    private String buy_ammount;
    private String sell_contracts;
    private String sell_ammount;
    private String oi_contracts;
    private String oi_ammount;

    private String stock_futures;
    private String stock_options;
    private String index_futures;
    private String index_options;

    public String getStock_futures() {
        return stock_futures;
    }

    public void setStock_futures(String stock_futures) {
        this.stock_futures = stock_futures;
    }

    public String getStock_options() {
        return stock_options;
    }

    public void setStock_options(String stock_options) {
        this.stock_options = stock_options;
    }

    public String getIndex_futures() {
        return index_futures;
    }

    public void setIndex_futures(String index_futures) {
        this.index_futures = index_futures;
    }

    public String getIndex_options() {
        return index_options;
    }

    public void setIndex_options(String index_options) {
        this.index_options = index_options;
    }

    public String getDerivative_product() {
        return derivative_product;
    }

    public void setDerivative_product(String derivative_product) {
        this.derivative_product = derivative_product;
    }

    public String getBuy_contracts() {
        return buy_contracts;
    }

    public void setBuy_contracts(String buy_contracts) {
        this.buy_contracts = buy_contracts;
    }

    public String getBuy_ammount() {
        return buy_ammount;
    }

    public void setBuy_ammount(String buy_ammount) {
        this.buy_ammount = buy_ammount;
    }

    public String getSell_contracts() {
        return sell_contracts;
    }

    public void setSell_contracts(String sell_contracts) {
        this.sell_contracts = sell_contracts;
    }

    public String getSell_ammount() {
        return sell_ammount;
    }

    public void setSell_ammount(String sell_ammount) {
        this.sell_ammount = sell_ammount;
    }

    public String getOi_contracts() {
        return oi_contracts;
    }

    public void setOi_contracts(String oi_contracts) {
        this.oi_contracts = oi_contracts;
    }

    public String getOi_ammount() {
        return oi_ammount;
    }

    public void setOi_ammount(String oi_ammount) {
        this.oi_ammount = oi_ammount;
    }

    public String getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(String reportingDate) {
        this.reportingDate = reportingDate;
    }

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    public String getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(String last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public String getInvestment_category() {
        return investment_category;
    }

    public void setInvestment_category(String investment_category) {
        this.investment_category = investment_category;
    }

    public String getInvestment_route() {
        return investment_route;
    }

    public void setInvestment_route(String investment_route) {
        this.investment_route = investment_route;
    }

    public String getGross_purchases() {
        return gross_purchases;
    }

    public void setGross_purchases(String gross_purchases) {
        this.gross_purchases = gross_purchases;
    }

    public String getGross_sales() {
        return gross_sales;
    }

    public void setGross_sales(String gross_sales) {
        this.gross_sales = gross_sales;
    }

    public String getNet_investement_inr() {
        return net_investement_inr;
    }

    public void setNet_investement_inr(String net_investement_inr) {
        this.net_investement_inr = net_investement_inr;
    }

    public String getNet_investment_usd() {
        return net_investment_usd;
    }

    public void setNet_investment_usd(String net_investment_usd) {
        this.net_investment_usd = net_investment_usd;
    }

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("reportingDate", this.reportingDate);
        jo.put("logtime", this.logtime);
        jo.put("last_updated_time", this.last_updated_time);
        jo.put("investment_category", this.investment_category);
        jo.put("investment_route", this.investment_route);
        jo.put("gross_purchases", this.gross_purchases);
        jo.put("gross_sales", this.gross_sales);
        jo.put("net_investement_inr", this.net_investement_inr);
        jo.put("net_investment_usd", this.net_investment_usd);
        jo.put("conversion", this.conversion);

        jo.put("derivative_product", this.derivative_product);
        jo.put("buy_contracts", this.buy_contracts);
        jo.put("buy_ammount", this.buy_ammount);
        jo.put("sell_contracts", this.sell_contracts);
        jo.put("sell_ammount", this.sell_ammount);
        jo.put("oi_contracts", this.oi_contracts);
        jo.put("oi_ammount", this.oi_ammount);

        jo.put("stock_futures", this.stock_futures);
        jo.put("stock_options", this.stock_options);
        jo.put("index_futures", this.index_futures);
        jo.put("index_options", this.index_options);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.reportingDate = jo.optString("reportingDate");
        this.logtime = jo.optString("logtime");
        this.last_updated_time = jo.optString("last_updated_time");
        this.investment_category = jo.optString("investment_category");
        this.investment_route = jo.optString("investment_route");
        this.gross_purchases = jo.optString("gross_purchases");
        this.gross_sales = jo.optString("gross_sales");
        this.net_investement_inr = jo.optString("net_investement_inr");
        this.net_investment_usd = jo.optString("net_investment_usd");
        this.conversion = jo.optString("conversion");

        this.derivative_product = jo.optString("derivative_product");
        this.buy_contracts = jo.optString("buy_contracts");
        this.buy_ammount = jo.optString("buy_ammount");
        this.sell_contracts = jo.optString("sell_contracts");
        this.sell_ammount = jo.optString("sell_ammount");
        this.oi_contracts = jo.optString("oi_contracts");
        this.oi_ammount = jo.optString("oi_ammount");

        this.stock_futures = jo.optString("stock_futures");
        this.stock_options = jo.optString("stock_options");
        this.index_futures = jo.optString("index_futures");
        this.index_options = jo.optString("index_options");
        return this;
    }
}




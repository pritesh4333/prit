package com.acumengroup.greekmain.core.model.FiiDiiActivity;

import java.io.Serializable;

public class FiiInvestmentModel implements Serializable {

    private String reportingDate;
    private String fii_investment_category;
    private String dii_investment_category;
    private String investment_route;
    private String investment_category;
    private String fii_gross_purchases;
    private String fii_gross_sales;
    private String fii_net_investement_inr;

    private String dii_gross_purchases;
    private String dii_gross_sales;
    private String dii_net_investement_inr;

    private String sell_ammount;
    private String buy_ammount;
    private String net_ammount;


    public String getNet_ammount() {
        return net_ammount;
    }

    public void setNet_ammount(String net_ammount) {
        this.net_ammount = net_ammount;
    }

    public String getSell_ammount() {
        return sell_ammount;
    }

    public void setSell_ammount(String sell_ammount) {
        this.sell_ammount = sell_ammount;
    }

    public String getBuy_ammount() {
        return buy_ammount;
    }

    public void setBuy_ammount(String buy_ammount) {
        this.buy_ammount = buy_ammount;
    }

    public String getInvestment_category() {
        return investment_category;
    }

    public void setInvestment_category(String investment_category) {
        this.investment_category = investment_category;
    }

    public String getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(String reportingDate) {
        this.reportingDate = reportingDate;
    }

    public String getFii_investment_category() {
        return fii_investment_category;
    }

    public void setFii_investment_category(String fii_investment_category) {
        this.fii_investment_category = fii_investment_category;
    }

    public String getDii_investment_category() {
        return dii_investment_category;
    }

    public void setDii_investment_category(String dii_investment_category) {
        this.dii_investment_category = dii_investment_category;
    }

    public String getInvestment_route() {
        return investment_route;
    }

    public void setInvestment_route(String investment_route) {
        this.investment_route = investment_route;
    }

    public String getFii_gross_purchases() {
        return fii_gross_purchases;
    }

    public void setFii_gross_purchases(String fii_gross_purchases) {
        this.fii_gross_purchases = fii_gross_purchases;
    }

    public String getFii_gross_sales() {
        return fii_gross_sales;
    }

    public void setFii_gross_sales(String fii_gross_sales) {
        this.fii_gross_sales = fii_gross_sales;
    }

    public String getFii_net_investement_inr() {
        return fii_net_investement_inr;
    }

    public void setFii_net_investement_inr(String fii_net_investement_inr) {
        this.fii_net_investement_inr = fii_net_investement_inr;
    }

    public String getDii_gross_purchases() {
        return dii_gross_purchases;
    }

    public void setDii_gross_purchases(String dii_gross_purchases) {
        this.dii_gross_purchases = dii_gross_purchases;
    }

    public String getDii_gross_sales() {
        return dii_gross_sales;
    }

    public void setDii_gross_sales(String dii_gross_sales) {
        this.dii_gross_sales = dii_gross_sales;
    }

    public String getDii_net_investement_inr() {
        return dii_net_investement_inr;
    }

    public void setDii_net_investement_inr(String dii_net_investement_inr) {
        this.dii_net_investement_inr = dii_net_investement_inr;
    }
}

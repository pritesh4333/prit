package com.acumengroup.mobile.model;

import java.io.Serializable;

public class OIChangeData implements Serializable {
    String lOurToken;
    String lPreviousOI;
    String lCurrentOI;
    String lClosingPrice;
    String dLTP;
    String symbol;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getlOurToken() {
        return lOurToken;
    }

    public void setlOurToken(String lOurToken) {
        this.lOurToken = lOurToken;
    }


    public String getlPreviousOI() {
        return lPreviousOI;
    }

    public void setlPreviousOI(String lPreviousOI) {
        this.lPreviousOI = lPreviousOI;
    }

    public String getlCurrentOI() {
        return lCurrentOI;
    }

    public void setlCurrentOI(String lCurrentOI) {
        this.lCurrentOI = lCurrentOI;
    }

    public String getlClosingPrice() {
        return lClosingPrice;
    }

    public void setlClosingPrice(String lClosingPrice) {
        this.lClosingPrice = lClosingPrice;
    }

    public String getdLTP() {
        return dLTP;
    }

    public void setdLTP(String dLTP) {
        this.dLTP = dLTP;
    }
}

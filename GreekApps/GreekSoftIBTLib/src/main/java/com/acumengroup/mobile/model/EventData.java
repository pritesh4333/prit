package com.acumengroup.mobile.model;

public class EventData {

    String srno;
    String token ;
    String symbol;
    String news_dateTime ;
    String heading;
    String news_type;
    String news_source ;
    String news_level ;
    String news_description ;
    String news_arttext ;

    public EventData(String srno, String token, String symbol, String news_dateTime, String heading, String news_type, String news_source, String news_level, String news_description, String news_arttext) {
        this.srno = srno;
        this.token = token;
        this.symbol = symbol;
        this.news_dateTime = news_dateTime;
        this.heading = heading;
        this.news_type = news_type;
        this.news_source = news_source;
        this.news_level = news_level;
        this.news_description = news_description;
        this.news_arttext = news_arttext;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNews_dateTime() {
        return news_dateTime;
    }

    public void setNews_dateTime(String news_dateTime) {
        this.news_dateTime = news_dateTime;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getNews_type() {
        return news_type;
    }

    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    public String getNews_source() {
        return news_source;
    }

    public void setNews_source(String news_source) {
        this.news_source = news_source;
    }

    public String getNews_level() {
        return news_level;
    }

    public void setNews_level(String news_level) {
        this.news_level = news_level;
    }

    public String getNews_description() {
        return news_description;
    }

    public void setNews_description(String news_description) {
        this.news_description = news_description;
    }

    public String getNews_arttext() {
        return news_arttext;
    }

    public void setNews_arttext(String news_arttext) {
        this.news_arttext = news_arttext;
    }
}

package com.acumengroup.mobile.model;

public class NewsDataModel {

    private String Sno;

    private String Section_name;

    private String Date;

    private String Time;

    private String Heading;

    private String Caption;

    private String Arttext;

    private String co_code;

    private String dateformat;

    public void setSno(String Sno){
        this.Sno = Sno;
    }
    public String getSno(){
        return this.Sno;
    }
    public void setSection_name(String Section_name){
        this.Section_name = Section_name;
    }
    public String getSection_name(){
        return this.Section_name;
    }
    public void setDate(String Date){
        this.Date = Date;
    }
    public String getDate(){
        return this.Date;
    }
    public void setTime(String Time){
        this.Time = Time;
    }
    public String getTime(){
        return this.Time;
    }
    public void setHeading(String Heading){
        this.Heading = Heading;
    }
    public String getHeading(){
        return this.Heading;
    }
    public void setCaption(String Caption){
        this.Caption = Caption;
    }
    public String getCaption(){
        return this.Caption;
    }
    public void setArttext(String Arttext){
        this.Arttext = Arttext;
    }
    public String getArttext(){
        return this.Arttext;
    }
    public void setCo_code(String co_code){
        this.co_code = co_code;
    }
    public String getCo_code(){
        return this.co_code;
    }
    public void setDateformat(String dateformat){
        this.dateformat = dateformat;
    }
    public String getDateformat(){
        return this.dateformat;
    }

}

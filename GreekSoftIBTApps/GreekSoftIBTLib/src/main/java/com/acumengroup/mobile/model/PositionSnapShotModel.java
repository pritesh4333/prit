package com.acumengroup.mobile.model;

/**
 * Created by Arcadia
 */
public class PositionSnapShotModel {
    private String clientCode;
    private String segment;
    private String openPosCount;
    private String mtmValue;
    private String totalOpenPosCount;
    private String totalMTMvalue;


    public String getTotalOpenPosCount() {
        return totalOpenPosCount;
    }

    public void setTotalOpenPosCount(String totalOpenPosCount) {
        this.totalOpenPosCount = totalOpenPosCount;
    }

    public String getTotalMTMvalue() {
        return totalMTMvalue;
    }

    public void setTotalMTMvalue(String totalMTMvalue) {
        this.totalMTMvalue = totalMTMvalue;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getOpenPosCount() {
        return openPosCount;
    }

    public void setOpenPosCount(String openPosCount) {
        this.openPosCount = openPosCount;
    }

    public String getMtmValue() {
        return mtmValue;
    }

    public void setMtmValue(String mtmValue) {
        this.mtmValue = mtmValue;
    }
}

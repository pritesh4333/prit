package com.acumengroup.greekmain.core.model.recommonDisplay;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Data implements GreekRequestModel, GreekResponseModel {
    //{"svcName":"getRecommendations","serverTime":"1450349681701","infoID":"0","appID":"bc90bb525bc9739a9595bb9e176dab17","svcVersion":"1.0.0","msgID":"a9bfad10-92ce-4fd7-968f- c25877c1bbb3","svcGroup":"addpersonaldetails",
    // "data":{"Data":
    // [{
    // "lLogTime":1560415580,
    // "cSenderId":"ADMIN",
    // "cRecieverId":"NB003",
    // "cMessage":"Recommendation 4",
    // "iMsgType":2,
    // "bSendStatus":0,
    // "iSendingChannel":0},

    private String lLogTime;
    private String cSenderId;
    private String cRecieverId;
    private String cMessage;
    private String iMsgType;
    private String iSendingChannel;

    public String getlLogTime() {
        return lLogTime;
    }

    public void setlLogTime(String lLogTime) {
        this.lLogTime = lLogTime;
    }

    public String getcSenderId() {
        return cSenderId;
    }

    public void setcSenderId(String cSenderId) {
        this.cSenderId = cSenderId;
    }

    public String getcRecieverId() {
        return cRecieverId;
    }

    public void setcRecieverId(String cRecieverId) {
        this.cRecieverId = cRecieverId;
    }

    public String getcMessage() {
        return cMessage;
    }

    public void setcMessage(String cMessage) {
        this.cMessage = cMessage;
    }

    public String getiMsgType() {
        return iMsgType;
    }

    public void setiMsgType(String iMsgType) {
        this.iMsgType = iMsgType;
    }

    public String getiSendingChannel() {
        return iSendingChannel;
    }

    public void setiSendingChannel(String iSendingChannel) {
        this.iSendingChannel = iSendingChannel;
    }

    /* private String lLogTime;
    private String cSenderId;
    private String cRecieverId;
    private String cMessage;
    private String iMsgType;
    private String iSendingChannel;*/
    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("lLogTime", this.lLogTime);
        jo.put("cSenderId", this.cSenderId);
        jo.put("cRecieverId", this.cRecieverId);
        jo.put("cMessage", this.cMessage);
        jo.put("iMsgType", this.iMsgType);
        jo.put("iSendingChannel", this.iSendingChannel);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.lLogTime = jo.optString("lLogTime");
        this.cSenderId = jo.optString("cSenderId");
        this.cRecieverId = jo.optString("cRecieverId");
        this.cMessage = jo.optString("cMessage");
        this.iMsgType = jo.optString("iMsgType");
        this.iSendingChannel = jo.optString("iSendingChannel");

        return this;
    }
}

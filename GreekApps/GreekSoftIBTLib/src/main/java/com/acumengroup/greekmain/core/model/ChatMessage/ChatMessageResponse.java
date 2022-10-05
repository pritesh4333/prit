package com.acumengroup.greekmain.core.model.ChatMessage;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatMessageResponse implements GreekRequestModel, GreekResponseModel {
    private String lLogTime;
    private String cSenderId;
    private String cRecieverId;
    private String cMessage;
    private String iMsgType;
    private String bSendStatus;
    private String iSendingChannel;

    public String getbSendStatus() {
        return bSendStatus;
    }

    public void setbSendStatus(String bSendStatus) {
        this.bSendStatus = bSendStatus;
    }

    public String getiSendingChannel() {
        return iSendingChannel;
    }

    public void setiSendingChannel(String iSendingChannel) {
        this.iSendingChannel = iSendingChannel;
    }

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

    public JSONObject toJSONObject()
            throws JSONException {

        JSONObject jo = new JSONObject();

        jo.put("lLogTime", this.lLogTime);
        jo.put("cSenderId", this.cSenderId);
        jo.put("cRecieverId", this.cRecieverId);
        jo.put("cMessage", this.cMessage);
        jo.put("iMsgType", this.iMsgType);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.lLogTime = jo.optString("lLogTime");
        this.cSenderId = jo.optString("cSenderId");
        this.cRecieverId = jo.optString("cRecieverId");
        this.cMessage = jo.optString("cMessage");
        this.iMsgType = jo.optString("iMsgType");

        return this;
    }
}


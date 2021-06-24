package com.acumengroup.greekmain.core.model.MutualFundSipNewOrder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MFundMediaUploadResponce implements GreekRequestModel, GreekResponseModel {
    private String svcName;
    private String serverTime;
    private String infoID;
    private String appID;
    private String svcVersion;
    private String msgID;
    private String svcGroup;
    private String ErrorCode;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getInfoID() {
        return infoID;
    }

    public void setInfoID(String infoID) {
        this.infoID = infoID;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getSvcVersion() {
        return svcVersion;
    }

    public void setSvcVersion(String svcVersion) {
        this.svcVersion = svcVersion;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getSvcGroup() {
        return svcGroup;
    }

    public void setSvcGroup(String svcGroup) {
        this.svcGroup = svcGroup;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("svcName", this.svcName);
        jo.put("serverTime", this.serverTime);
        jo.put("infoID", this.infoID);
        jo.put("appID", this.appID);
        jo.put("svcVersion", this.svcVersion);
        jo.put("msgID", this.msgID);
        jo.put("svcGroup", this.svcGroup);
        jo.put("ErrorCode", this.ErrorCode);
        jo.put("status", this.status);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.svcName = jo.optString("svcName");
        this.serverTime = jo.optString("serverTime");
        this.infoID = jo.optString("infoID");
        this.appID = jo.optString("appID");
        this.svcVersion = jo.optString("svcVersion");
        this.msgID = jo.optString("msgID");
        this.ErrorCode = jo.optString("ErrorCode");
        this.status = jo.optString("status");

        return this;
    }
}


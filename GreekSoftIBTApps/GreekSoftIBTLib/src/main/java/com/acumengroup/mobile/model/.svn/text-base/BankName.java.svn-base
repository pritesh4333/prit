package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BankName implements GreekRequestModel, GreekResponseModel {

    String uniqueId, gcid, amount, statusFlag, level1_reqTime, level1_resTime, level2_resTime, last_updated_time, level2_reqTime;
    String cGreekClientid, gscid, token, tempTranId, tType, tranId, bankTranId, bankName, cardNumber, discriminator, dateAndTime, transStatus, transStage;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getLevel1_reqTime() {
        return level1_reqTime;
    }

    public void setLevel1_reqTime(String level1_reqTime) {
        this.level1_reqTime = level1_reqTime;
    }

    public String getLevel1_resTime() {
        return level1_resTime;
    }

    public void setLevel1_resTime(String level1_resTime) {
        this.level1_resTime = level1_resTime;
    }

    public String getLevel2_resTime() {
        return level2_resTime;
    }

    public void setLevel2_resTime(String level2_resTime) {
        this.level2_resTime = level2_resTime;
    }

    public String getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(String last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public String getLevel2_reqTime() {
        return level2_reqTime;
    }

    public void setLevel2_reqTime(String level2_reqTime) {
        this.level2_reqTime = level2_reqTime;
    }

    public String getcGreekClientid() {
        return cGreekClientid;
    }

    public void setcGreekClientid(String cGreekClientid) {
        this.cGreekClientid = cGreekClientid;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTempTranId() {
        return tempTranId;
    }

    public void setTempTranId(String tempTranId) {
        this.tempTranId = tempTranId;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getBankTranId() {
        return bankTranId;
    }

    public void setBankTranId(String bankTranId) {
        this.bankTranId = bankTranId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransStage() {
        return transStage;
    }

    public void setTransStage(String transStage) {
        this.transStage = transStage;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("uniqueId", this.uniqueId);
        jo.put("gcid", this.gcid);
        jo.put("amount", this.amount);
        jo.put("statusFlag", this.statusFlag);
        jo.put("level1_reqTime", this.level1_reqTime);
        jo.put("level1_resTime", this.level1_resTime);
        jo.put("level2_resTime", this.level2_resTime);
        jo.put("last_updated_time", this.last_updated_time);
        jo.put("level2_reqTime", this.level2_reqTime);
        jo.put("cGreekClientid", this.cGreekClientid);
        jo.put("gscid", this.gscid);
        jo.put("tempTranId", this.tempTranId);
        jo.put("tType", this.tType);
        jo.put("tranId", this.tranId);
        jo.put("bankTranId", this.bankTranId);
        jo.put("cardNumber", this.cardNumber);
        jo.put("discriminator", this.discriminator);
        jo.put("dateAndTime", this.dateAndTime);
        jo.put("transStatus", this.transStatus);
        jo.put("transStage", this.transStage);
        jo.put("bankName", this.bankName);

        return jo;

    }

    @Override
    public GreekResponseModel fromJSON(JSONObject res) throws JSONException {

        this.uniqueId = res.optString("uniqueId");
        this.gcid = res.optString("gcid");
        this.amount = res.optString("amount");
        this.statusFlag = res.optString("statusFlag");
        this.level1_reqTime = res.optString("level1_reqTime");
        this.level1_resTime = res.optString("level1_resTime");
        this.level2_resTime = res.optString("level2_resTime");
        this.last_updated_time = res.optString("last_updated_time");
        this.level2_reqTime = res.optString("level2_reqTime");
        this.cGreekClientid = res.optString("cGreekClientid");
        this.gscid = res.optString("gscid");
        this.tempTranId = res.optString("tempTranId");
        this.tType = res.optString("tType");
        this.tranId = res.optString("tranId");
        this.bankTranId = res.optString("bankTranId");
        this.cardNumber = res.optString("cardNumber");
        this.discriminator = res.optString("discriminator");
        this.dateAndTime = res.optString("dateAndTime");
        this.transStatus = res.optString("transStatus");
        this.transStage = res.optString("transStage");
        this.bankName = res.optString("bankName");


        return this;
    }
}

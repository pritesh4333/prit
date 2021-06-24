package com.acumengroup.mobile.model;

public class BankNameData {

    String uniqueId, gcid, amount, statusFlag, level1_reqTime, level1_resTime, level2_resTime, last_updated_time, level2_reqTime;
    String cGreekClientid, gscid, token, tempTranId, tType, tranId, bankTranId, bankName, cardNumber, discriminator, dateAndTime, transStatus, transStage;

    private Object details;


    public Object getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(Object details) {
        this.details = details;
    }

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
}

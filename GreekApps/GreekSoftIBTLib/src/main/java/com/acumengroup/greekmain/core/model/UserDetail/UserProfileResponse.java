package com.acumengroup.greekmain.core.model.UserDetail;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileResponse implements GreekRequestModel, GreekResponseModel {

    private String ErrorCode;
    private String lmobileno;
    private String cemailid;
    private String cpanno;
    private String bankname;
    private String bankacno;
    private String cmktallowed;
    private String bisMF ;
    private String bMTF ;
    private String IntradayProductSetting;
    private String DeliveryProductSetting ;


    public String getLmobileno() {
        return lmobileno;
    }

    public void setLmobileno(String lmobileno) {
        this.lmobileno = lmobileno;
    }

    public String getCemailid() {
        return cemailid;
    }

    public void setCemailid(String cemailid) {
        this.cemailid = cemailid;
    }

    public String getCpanno() {
        return cpanno;
    }

    public void setCpanno(String cpanno) {
        this.cpanno = cpanno;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankacno() {
        return bankacno;
    }

    public void setBankacno(String bankacno) {
        this.bankacno = bankacno;
    }

    public String getCmktallowed() {
        return cmktallowed;
    }

    public void setCmktallowed(String cmktallowed) {
        this.cmktallowed = cmktallowed;
    }

    public String getBisMF() {
        return bisMF;
    }

    public void setBisMF(String bisMF) {
        this.bisMF = bisMF;
    }

    public String getbMTF() {
        return bMTF;
    }

    public void setbMTF(String bMTF) {
        this.bMTF = bMTF;
    }

    public String getIntradayProductSetting() {
        return IntradayProductSetting;
    }

    public void setIntradayProductSetting(String intradayProductSetting) {
        IntradayProductSetting = intradayProductSetting;
    }

    public String getDeliveryProductSetting() {
        return DeliveryProductSetting;
    }

    public void setDeliveryProductSetting(String deliveryProductSetting) {
        DeliveryProductSetting = deliveryProductSetting;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("ErrorCode", this.ErrorCode);
        jo.put("lmobileno", this.lmobileno);
        jo.put("cemailid", this.cemailid);
        jo.put("cpanno", this.cpanno);
        jo.put("bankname", this.bankname);
        jo.put("bankacno", this.bankacno);
        jo.put("cmktallowed", this.cmktallowed);
        jo.put("bisMF", this.bisMF);
        jo.put("bMTF", this.bMTF);
        jo.put("IntradayProductSetting", this.IntradayProductSetting);
        jo.put("DeliveryProductSetting", this.DeliveryProductSetting);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");
        this.lmobileno = jo.optString("lmobileno");
        this.cemailid = jo.optString("cemailid");
        this.cpanno = jo.optString("cpanno");
        this.bankname = jo.optString("bankname");
        this.bankacno = jo.optString("bankacno");
        this.cmktallowed = jo.optString("cmktallowed");
        this.bisMF = jo.optString("bisMF");
        this.bMTF = jo.optString("bMTF");
        this.IntradayProductSetting = jo.optString("IntradayProductSetting");
        this.DeliveryProductSetting = jo.optString("DeliveryProductSetting");

        return this;
    }
}

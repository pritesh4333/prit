package com.acumengroup.greekmain.core.model.FundTransfer;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BankAccountList implements GreekRequestModel, GreekResponseModel {
    private String bankAcNo;
    private String ifscCode;
    private String password;
    private String bankName;
    private String bankBranch;
    private String mobile;
    private String acHolderName;

    public String getBankAcNo() {
        return bankAcNo;
    }

    public void setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAcHolderName() {
        return acHolderName;
    }

    public void setAcHolderName(String acHolderName) {
        this.acHolderName = acHolderName;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("bankAcNo", this.bankAcNo);
        jo.put("ifscCode", this.ifscCode);
        jo.put("password", this.password);
        jo.put("bankName", this.bankName);
        jo.put("bankBranch", this.bankBranch);
        jo.put("mobile", this.mobile);
        jo.put("acHolderName", this.acHolderName);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.bankAcNo = jo.optString("bankAcNo");
        this.ifscCode = jo.optString("ifscCode");
        this.password = jo.optString("password");
        this.bankName = jo.optString("bankName");
        this.bankBranch = jo.optString("bankBranch");
        this.mobile = jo.optString("mobile");
        this.acHolderName = jo.optString("acHolderName");

        return this;
    }
}

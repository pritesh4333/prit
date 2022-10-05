package com.acumengroup.greekmain.core.model.FundTransfer;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 04-Jul-17.
 */

public class FundTransferDetailsResponse implements GreekRequestModel, GreekResponseModel {

    private String ErrorCode;
    private String uniqueId;
    private String cGreekClientid;
    private String gcid;
    private String password;
    private String tType;
    private String productID;
    private String custBankAcNo;
    private String clientCode;
    private String last_updated_time;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }


    public String getcGreekClientid() {
        return cGreekClientid;
    }

    public void setcGreekClientid(String cGreekClientid) {
        this.cGreekClientid = cGreekClientid;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCustBankAcNo() {
        return custBankAcNo;
    }

    public void setCustBankAcNo(String custBankAcNo) {
        this.custBankAcNo = custBankAcNo;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(String last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.ErrorCode);
        jo.put("uniqueId", this.uniqueId);
        jo.put("cGreekClientid", this.cGreekClientid);
        jo.put("gcid", this.gcid);
        jo.put("password", this.password);
        jo.put("tType", this.tType);
        jo.put("productID", this.productID);
        jo.put("custBankAcNo", this.custBankAcNo);
        jo.put("clientCode", this.clientCode);
        jo.put("last_updated_time", this.last_updated_time);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.ErrorCode = jo.optString("ErrorCode");
        this.uniqueId = jo.optString("uniqueId");
        this.cGreekClientid = jo.optString("cGreekClientid");
        this.gcid = jo.optString("gcid");
        this.password = jo.optString("password");
        this.tType = jo.optString("tType");
        this.productID = jo.optString("productID");
        this.custBankAcNo = jo.optString("custBankAcNo");
        this.clientCode = jo.optString("clientCode");
        this.last_updated_time = jo.optString("last_updated_time");
        return this;
    }
}

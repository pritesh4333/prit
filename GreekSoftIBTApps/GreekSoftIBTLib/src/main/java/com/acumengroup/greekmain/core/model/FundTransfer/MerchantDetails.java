package com.acumengroup.greekmain.core.model.FundTransfer;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MerchantDetails implements GreekRequestModel, GreekResponseModel {
    private String prodId;
    private String merchant_id;
    private String reqHashKey;
    private String resHashKey;
    private String password;
    private String segment;

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getReqHashKey() {
        return reqHashKey;
    }

    public void setReqHashKey(String reqHashKey) {
        this.reqHashKey = reqHashKey;
    }

    public String getResHashKey() {
        return resHashKey;
    }

    public void setResHashKey(String resHashKey) {
        this.resHashKey = resHashKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("prodId", this.prodId);
        jo.put("merchant_id", this.merchant_id);
        jo.put("password", this.password);
        jo.put("reqHashKey", this.reqHashKey);
        jo.put("resHashKey", this.resHashKey);
        jo.put("segment", this.segment);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.prodId = jo.optString("prodId");
        this.merchant_id = jo.optString("merchant_id");
        this.password = jo.optString("password");
        this.reqHashKey = jo.optString("reqHashKey");
        this.resHashKey = jo.optString("resHashKey");
        this.segment = jo.optString("segment");
        return this;
    }
}

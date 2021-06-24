package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 5/24/2016.
 */
public class ProductChangeRequest implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String gtoken;
    private String product;
    private String qty;
    private String traded_qty;
    private String iGiveUpStatus;
    private String reason;
    private String gorderid;
    private String tradeid;
    private String eorderid;
    private String side;

    public String getGorderid() {
        return gorderid;
    }

    public void setGorderid(String gorderid) {
        this.gorderid = gorderid;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getEorderid() {
        return eorderid;
    }

    public void setEorderid(String eorderid) {
        this.eorderid = eorderid;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getiGiveUpStatus() {
        return iGiveUpStatus;
    }

    public void setiGiveUpStatus(String iGiveUpStatus) {
        this.iGiveUpStatus = iGiveUpStatus;
    }

    public String getTraded_qty() {
        return traded_qty;
    }

    public void setTraded_qty(String traded_qty) {
        this.traded_qty = traded_qty;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getGtoken() {
        return gtoken;
    }

    public void setGtoken(String gtoken) {
        this.gtoken = gtoken;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("gtoken", this.gtoken);
        jo.put("qty", this.qty);
        jo.put("product", this.product);
        jo.put("traded_qty", this.traded_qty);
        jo.put("iGiveUpStatus", this.iGiveUpStatus);
        jo.put("reason", this.reason);
        jo.put("gorderid", this.gorderid);
        jo.put("tradeid", this.tradeid);
        jo.put("eorderid", this.eorderid);
        jo.put("side", this.side);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gscid = jo.optString("gscid");
        this.gtoken = jo.optString("gtoken");
        this.qty = jo.optString("qty");
        this.product = jo.optString("product");
        this.traded_qty = jo.optString("traded_qty");
        this.iGiveUpStatus = jo.optString("iGiveUpStatus");
        this.reason = jo.optString("reason");
        this.gorderid = jo.optString("gorderid");
        this.tradeid = jo.optString("tradeid");
        this.eorderid = jo.optString("eorderid");
        this.side = jo.optString("side");
        return this;
    }
}
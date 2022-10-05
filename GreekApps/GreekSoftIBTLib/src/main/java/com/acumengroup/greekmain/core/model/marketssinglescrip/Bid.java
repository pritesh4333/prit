package com.acumengroup.greekmain.core.model.marketssinglescrip;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Bid
        implements GreekRequestModel, GreekResponseModel {
    private String price;
    private String no;
    private String qty;
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNo() {
        return this.no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getQty() {
        return this.qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("price", this.price);
        jo.put("no", this.no);
        jo.put("qty", this.qty);
        jo.put("total", this.total);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.price = jo.optString("price");
        this.no = jo.optString("no");
        this.qty = jo.optString("qty");
        this.total = jo.optString("total");
        return this;
    }
}



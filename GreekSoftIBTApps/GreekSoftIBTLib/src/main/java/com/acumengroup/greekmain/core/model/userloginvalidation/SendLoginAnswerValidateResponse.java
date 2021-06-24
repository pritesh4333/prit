package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sushant.patil on 3/28/2016.
 */
public class SendLoginAnswerValidateResponse implements GreekRequestModel, GreekResponseModel {
    private String status;
    private String executionCode;
    private String errorCode;
    private String clientCode;
    private String OrderTime;
    private List<AllowedMarket> allowedMarket = new ArrayList();

    public List<AllowedMarket> getAllowedMarket() {
        return allowedMarket;
    }

    public void setAllowedMarket(List<AllowedMarket> allowedMarket) {
        this.allowedMarket = allowedMarket;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecutionCode() {
        return executionCode;
    }

    public void setExecutionCode(String executionCode) {
        this.executionCode = executionCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("status", this.status);
        jo.put("Executioncode", this.executionCode);
        jo.put("ErrorCode", this.errorCode);
        jo.put("ClientCode", this.clientCode);
        jo.put("OrderTime", this.OrderTime);
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.allowedMarket.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("AllowedMarket", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.status = jo.optString("status");
        this.executionCode = jo.optString("Executioncode");
        this.errorCode = jo.optString("ErrorCode");
        this.clientCode = jo.optString("ClientCode");
        this.OrderTime = jo.optString("OrderTime");
        if (jo.has("AllowedMarket")) {
            JSONArray ja1 = jo.getJSONArray("AllowedMarket");
            this.allowedMarket = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    AllowedMarket allowedMarket = new AllowedMarket();
                    allowedMarket.fromJSON((JSONObject) o);
                    this.allowedMarket.add(allowedMarket);
                } else {
                    this.allowedMarket.add((AllowedMarket) o);
                }
            }
        }
        return this;
    }
}

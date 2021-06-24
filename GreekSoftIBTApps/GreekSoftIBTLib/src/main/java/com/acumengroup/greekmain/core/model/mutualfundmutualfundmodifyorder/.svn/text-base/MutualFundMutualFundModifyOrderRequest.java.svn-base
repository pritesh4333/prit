package com.acumengroup.greekmain.core.model.mutualfundmutualfundmodifyorder;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MutualFundMutualFundModifyOrderRequest implements GreekRequestModel, GreekResponseModel {
    private String amtUnit;
    private String schemeName;
    private String strToken;
    private String schemeCode;
    private String clientCode;
    private String purchaseType;
    private String orderType;
    private String amcName;
    private String refNo;

    private static JSONObject echoParam = null;

    public String getAmtUnit() {
        return this.amtUnit;
    }

    public void setAmtUnit(String amtUnit) {
        this.amtUnit = amtUnit;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getStrToken() {
        return this.strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getPurchaseType() {
        return this.purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAmcName() {
        return this.amcName;
    }

    public void setAmcName(String amcName) {
        this.amcName = amcName;
    }

    public String getRefNo() {
        return this.refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("amtUnit", this.amtUnit);
        jo.put("schemeName", this.schemeName);
        jo.put("strToken", this.strToken);
        jo.put("schemeCode", this.schemeCode);
        jo.put("clientCode", this.clientCode);
        jo.put("purchaseType", this.purchaseType);
        jo.put("orderType", this.orderType);
        jo.put("amcName", this.amcName);
        jo.put("refNo", this.refNo);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.amtUnit = jo.optString("amtUnit");
        this.schemeName = jo.optString("schemeName");
        this.strToken = jo.optString("strToken");
        this.schemeCode = jo.optString("schemeCode");
        this.clientCode = jo.optString("clientCode");
        this.purchaseType = jo.optString("purchaseType");
        this.orderType = jo.optString("orderType");
        this.amcName = jo.optString("amcName");
        this.refNo = jo.optString("refNo");
        return this;
    }

    public static void sendRequest(MutualFundMutualFundModifyOrderRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(MutualFundMutualFundModifyOrderResponse.class);
        jsonRequest.setService("MutualFund", "MutualFundModifyOrder");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String amcName, String amtUnit, String clientCode, String orderType, String purchaseType, String refNo, String schemeCode, String schemeName, String strToken, Context ctx, ServiceResponseListener listener) {
        MutualFundMutualFundModifyOrderRequest request = new MutualFundMutualFundModifyOrderRequest();
        request.setAmcName(amcName);
        request.setAmtUnit(amtUnit);
        request.setClientCode(clientCode);
        request.setOrderType(orderType);
        request.setPurchaseType(purchaseType);
        request.setRefNo(refNo);
        request.setSchemeCode(schemeCode);
        request.setSchemeName(schemeName);
        request.setStrToken(strToken);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

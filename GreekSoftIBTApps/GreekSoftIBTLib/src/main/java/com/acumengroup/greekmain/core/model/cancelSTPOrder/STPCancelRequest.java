package com.acumengroup.greekmain.core.model.cancelSTPOrder;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MutualFundSipNewOrderResponse;
import com.acumengroup.greekmain.core.model.STPOrder.STPOrderlRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class STPCancelRequest implements GreekRequestModel, GreekResponseModel {

    public static final String SERVICE_GROUP = "MutualFund";
    public static final String SERVICE_NAME = "MutualFundSendNewOrder";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    private String STPRegNo;
    private String clientCode;
    private String remarks;
    private String flag;
    private String amtUnit;


    public String getAmtUnit() {
        return amtUnit;
    }

    public void setAmtUnit(String amtUnit) {
        this.amtUnit = amtUnit;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSTPRegNo() {
        return STPRegNo;
    }

    public void setSTPRegNo(String STPRegNo) {
        this.STPRegNo = STPRegNo;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("STPRegNo", this.STPRegNo);
        jo.put("clientCode", this.clientCode);
        jo.put("remarks", this.remarks);
        jo.put("flag", this.flag);
        jo.put("amtUnit", this.amtUnit);

        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.STPRegNo = jo.optString("STPRegNo");
        this.clientCode = jo.optString("clientCode");
        this.remarks = jo.optString("remarks");
        this.flag = jo.optString("flag");
        this.amtUnit = jo.optString("amtUnit");


        return this;
    }


    public static void addEchoParam(String key, String value) {
        try {
            if (echoParam == null) {
                echoParam = new JSONObject();
            }
            echoParam.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(STPOrderlRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(MutualFundSipNewOrderResponse.class);
        jsonRequest.setService("MFAPI", "getConnectToMF");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }


    @Deprecated
    public static void sendRequest(String amtUnit, String regno, String flag, String clientcode, String remarks, Context ctx, ServiceResponseListener listener) {
        STPCancelRequest request = new STPCancelRequest();
        request.setSTPRegNo(regno);
        request.setFlag(flag);
        request.setClientCode(clientcode);
        request.setRemarks(remarks);
        request.setAmtUnit(amtUnit);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


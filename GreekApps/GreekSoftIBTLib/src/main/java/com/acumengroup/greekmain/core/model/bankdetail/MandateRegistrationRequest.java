package com.acumengroup.greekmain.core.model.bankdetail;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MandateRegistrationRequest implements GreekRequestModel, GreekResponseModel {
    private String clientCode;
    private String amount;
    private String mandateType;
    private String accNo;
    private String accType;
    private String ifscCode;
    private String micrCode;
    private String startDate;
    private String endDate;
    private String flag;

    private static JSONObject echoParam = null;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMandateType() {
        return mandateType;
    }

    public void setMandateType(String mandateType) {
        this.mandateType = mandateType;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("clientCode", this.clientCode);
        jo.put("amount", this.amount);
        jo.put("mandateType", this.mandateType);
        jo.put("accNo", this.accNo);
        jo.put("accType", this.accType);
        jo.put("ifscCode", this.ifscCode);
        jo.put("micrCode", this.micrCode);
        jo.put("startDate", this.startDate);
        jo.put("endDate", this.endDate);
        jo.put("flag", this.flag);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.clientCode = jo.optString("clientCode");
        this.amount = jo.optString("amount");
        this.mandateType = jo.optString("mandateType");
        this.accNo = jo.optString("accNo");
        this.accType = jo.optString("accType");
        this.ifscCode = jo.optString("ifscCode");
        this.micrCode = jo.optString("micrCode");
        this.startDate = jo.optString("startDate");
        this.endDate = jo.optString("endDate");
        this.flag = jo.optString("flag");

        return this;
    }


    public static void sendRequest(MandateRegistrationRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(MandateRegistrationResponse.class);
        jsonRequest.setService("mfapi", "getConnectToMF");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String clientCode, String amount, String acctno, String accttype, String mandatetype, String micrCode, String ifsccode, String startdate, String enddate, String flag, Context ctx, ServiceResponseListener listener) {
        MandateRegistrationRequest request = new MandateRegistrationRequest();
        request.setClientCode(clientCode);
        request.setAmount(amount);
        request.setAccNo(acctno);
        request.setAccType(accttype);
        request.setMandateType(mandatetype);
        request.setMicrCode(micrCode);
        request.setIfscCode(ifsccode);
        request.setEndDate(enddate);
        request.setStartDate(startdate);
        request.setFlag(flag);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}


package com.acumengroup.greekmain.core.model.las;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.ChatMessage.ChatMessageResponse;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LASRequest implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String fisrtName;
    private String middleName;
    private String lastName;
    private String addr1;
    private String addr2;
    private String addr3;
    private String city;
    private String state;
    private String dob;
    private String pan;
    private String mobile;
    private String email;
    private String pin;
    private String loanAmount;
    private String roi;
    private String procFees;
    private String bankName;
    private String accNo;
    private String ifscCode;


    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getProcFees() {
        return procFees;
    }

    public void setProcFees(String procFees) {
        this.procFees = procFees;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    private static JSONObject echoParam = null;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }


    //, , , , , , , , , , , , , ,
    //                            , , , , ;

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("fisrtName", this.fisrtName);
        jo.put("middleName", this.middleName);
        jo.put("lastName", this.lastName);
        jo.put("addr1", this.addr1);
        jo.put("addr2", this.addr2);
        jo.put("addr3", this.addr3);
        jo.put("city", this.city);
        jo.put("state", this.state);
        jo.put("dob", this.dob);
        jo.put("pan", this.pan);
        jo.put("mobile", this.mobile);
        jo.put("email", this.email);
        jo.put("pin", this.pin);
        jo.put("loanAmount", this.loanAmount);
        jo.put("roi", this.roi);
        jo.put("procFees", this.procFees);
        jo.put("bankName", this.bankName);
        jo.put("accNo", this.accNo);
        jo.put("ifscCode", this.ifscCode);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.gscid = jo.optString("gscid");
        this.fisrtName = jo.optString("fisrtName");
        this.middleName = jo.optString("middleName");
        this.lastName = jo.optString("lastName");
        this.addr1 = jo.optString("addr1");
        this.addr2 = jo.optString("addr2");
        this.addr3 = jo.optString("addr3");
        this.city = jo.optString("city");
        this.state = jo.optString("state");
        this.dob = jo.optString("dob");
        this.pan = jo.optString("pan");
        this.mobile = jo.optString("mobile");
        this.email = jo.optString("email");
        this.pin = jo.optString("pin");
        this.loanAmount = jo.optString("loanAmount");
        this.roi = jo.optString("roi");
        this.procFees = jo.optString("procFees");
        this.bankName = jo.optString("bankName");
        this.accNo = jo.optString("accNo");
        this.ifscCode = jo.optString("ifscCode");


        return this;
    }


    public static void sendRequest(LASRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(ChatMessageResponse.class);
        jsonRequest.setService("Login", "getLoanEligibility");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequesForClientDetails(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(ChatMessageResponse.class);
        jsonRequest.setService("Login", "getClientDetailsForLAS");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequestForloanCreationRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(ChatMessageResponse.class);
        jsonRequest.setService("Login", "loanCreationRequest");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, Context ctx, ServiceResponseListener listener) {
        LASRequest request = new LASRequest();
        request.setGscid(gscid);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestForClientDetails(String gscid, Context ctx, ServiceResponseListener listener) {
        LASRequest request = new LASRequest();
        request.setGscid(gscid);
        try {
            sendRequesForClientDetails(request.toJSONObject(), ctx, listener);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestForloanCreationRequest(String gscid,
                                                         String fisrtName, String middleName, String lastName, String addr1, String addr2,
                                                         String addr3, String city, String state, String dob, String pan, String mobile,
                                                         String email, String pin, String loanAmount, String roi, String procFees, String bankName,
                                                         String accNo, String ifscCode,
                                                         Context ctx, ServiceResponseListener listener) {
        LASRequest request = new LASRequest();
        request.setGscid(gscid);
        request.setFisrtName(fisrtName);
        request.setMiddleName(middleName);
        request.setLastName(lastName);
        request.setAddr1(addr1);
        request.setAddr2(addr2);
        request.setAddr3(addr3);
        request.setCity(city);
        request.setState(state);
        request.setDob(dob);
        request.setPan(pan);
        request.setMobile(mobile);
        request.setEmail(email);
        request.setPin(pin);
        request.setLoanAmount(loanAmount);
        request.setRoi(roi);
        request.setProcFees(procFees);
        request.setBankName(bankName);
        request.setAccNo(accNo);
        request.setIfscCode(ifscCode);


        try {
            sendRequestForloanCreationRequest(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
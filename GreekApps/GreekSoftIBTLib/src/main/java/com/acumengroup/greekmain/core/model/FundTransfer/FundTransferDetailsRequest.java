package com.acumengroup.greekmain.core.model.FundTransfer;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 04-Jul-17.
 */

public class FundTransferDetailsRequest implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String ClientCode;
    private String SessionId;
    private String amount;
    private String segment;
    private String razorpay_payment_id;
    private String razorpay_order_id;
    private String signature ;
    private String account_number;
    private String deviceType;


    public static final String SERVICE_NAME = "fundTransferDetails";
    public static final String SERVICE_NAME_RAZOR = "razorPay";
    public static final String SERVICE_NAME_RAZORPAY_VERIFY = "razorPayVerifySign";
    public static final String SERVICE_NAME_RAZORPAY_FAIL = "razorPaymentFail";
    private static JSONObject echoParam = null;


    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRazorpay_payment_id() {
        return razorpay_payment_id;
    }

    public void setRazorpay_payment_id(String razorpay_payment_id) {
        this.razorpay_payment_id = razorpay_payment_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
    public String getRazorpay_order_id() {
        return razorpay_order_id;
    }

    public void setRazorpay_order_id(String razorpay_order_id) {
        this.razorpay_order_id = razorpay_order_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("gscid", this.gscid);
        jo.put("ClientCode", this.ClientCode);
        jo.put("amount", this.amount);
        jo.put("SessionId", this.SessionId);
        jo.put("segment", this.segment);
        jo.put("account_number", this.account_number);
        jo.put("razorpay_payment_id", this.razorpay_payment_id);
        jo.put("razorpay_order_id", this.razorpay_order_id);
        jo.put("signature", this.signature);
        jo.put("deviceType", this.deviceType);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ClientCode = jo.optString("ClientCode");
        this.gscid = jo.optString("gscid");
        this.amount = jo.optString("amount");
        this.SessionId = jo.optString("SessionId");
        this.segment = jo.optString("segment");
        this.account_number = jo.optString("account_number");
        this.razorpay_payment_id = jo.optString("razorpay_payment_id");
        this.razorpay_order_id = jo.optString("razorpay_order_id");
        this.signature = jo.optString("signature");
        this.deviceType = jo.optString("deviceType");


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

    public static void sendRequest(FundTransferDetailsRequest request, Context ctx, ServiceResponseListener listener) {
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
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(FundTransferDetailsResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }
    public static void sendRequestRazor(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(FundTransferDetailsResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME_RAZOR);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }
    public static void sendRequestRazorVerify(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(FundTransferDetailsResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME_RAZORPAY_VERIFY);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }
    public static void sendRequestrazorPaymentFail(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(FundTransferDetailsResponse.class);
        jsonRequest.setService("Login", SERVICE_NAME_RAZORPAY_FAIL);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String clientcode, String amount, String sessionid, String segment, Context ctx, ServiceResponseListener listener) {
        FundTransferDetailsRequest request = new FundTransferDetailsRequest();

        request.setGscid(gscid);
        request.setClientCode(clientcode);
        request.setAmount(amount);
        request.setSessionId(sessionid);
        request.setSegment(segment);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Deprecated
    public static void sendRequest(String gscid, String clientcode, String amount, String sessionid, String segStr, String bankacctspinner, Context ctx, ServiceResponseListener listener) {
        FundTransferDetailsRequest request = new FundTransferDetailsRequest();

        request.setGscid(gscid);
        request.setClientCode(clientcode);
        request.setAmount(amount);
        request.setSessionId(sessionid);
        request.setSegment(segStr);
        request.setAccount_number(bankacctspinner);
        request.setDeviceType("Android");



        try {
            sendRequestRazor(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void sendRequestRazorPayVerify(String paymentId, String orderId, String clientCode, String amount, String signature, String sessionid,String gscid,AppCompatActivity mainActivity, ServiceResponseHandler serviceResponseHandler) {
        FundTransferDetailsRequest request = new FundTransferDetailsRequest();


        request.setRazorpay_payment_id(paymentId);
        request.setRazorpay_order_id(orderId);
        request.setClientCode(clientCode);
        request.setAmount(amount);
        request.setSignature(signature);
        request.setGscid(gscid);
        request.setSessionId(sessionid);


        try {
            sendRequestRazorVerify(request.toJSONObject(), mainActivity, serviceResponseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void sendRequestrazorPaymentFail(String paymentId, String orderId, String clientCode, String amount, AppCompatActivity mainActivity, ServiceResponseHandler serviceResponseHandler) {
        FundTransferDetailsRequest request = new FundTransferDetailsRequest();


        request.setRazorpay_payment_id(paymentId);
        request.setRazorpay_order_id(orderId);
        request.setGscid(clientCode);
        request.setAmount(amount);
//        request.setSignature(signature);


        try {
            sendRequestrazorPaymentFail(request.toJSONObject(), mainActivity, serviceResponseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

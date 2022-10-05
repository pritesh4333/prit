package com.acumengroup.greekmain.core.model.bankdetail;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class KycDetailRequest implements GreekRequestModel, GreekResponseModel {
    private String clientCode;
    private String gscid;
    private String cFirstApplicantPAN;
    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "validate_guest";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public String getcFirstApplicantPAN() {
        return cFirstApplicantPAN;
    }

    public void setcFirstApplicantPAN(String cFirstApplicantPAN) {
        this.cFirstApplicantPAN = cFirstApplicantPAN;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("cFirstApplicantPAN", this.cFirstApplicantPAN);
        jo.put("gscid", this.gscid);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.cFirstApplicantPAN = jo.optString("cFirstApplicantPAN");
        this.gscid = jo.optString("gscid");

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

    public static void sendRequest(KycDetailRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {

        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("ValidateGuest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(KycDetailResponse.class);
        jsonRequest.setService("Login", "getKYCDetailsMF");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String cFirstApplicantPAN, String gscid, Context ctx, ServiceResponseListener listener) {
        KycDetailRequest request = new KycDetailRequest();
        request.setGscid(gscid);
        request.setcFirstApplicantPAN(cFirstApplicantPAN);

        try {

            sendRequest(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestFlag(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;

        jsonRequest = new GreekJSONRequest(ctx, request);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(ValidateGuestResponse.class);
        jsonRequest.setService("getFlagValues", "getKYCDetails");

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequestFlags(Context ctx, ServiceResponseListener listener) {
        ValidateGuestRequest request = new ValidateGuestRequest();

        try {
            sendRequestFlag(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

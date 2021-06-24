package com.acumengroup.greekmain.core.model.forgotpassword;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordRequest
        implements GreekRequestModel, GreekResponseModel {
    private String gscid;
    private String panno;
    private String emailid;
    private String brokerid;
    private String passType;

    private static JSONObject echoParam = null;
    public String getPanno() {
        return panno;
    }

    public void setPanno(String mobileno) {
        this.panno = mobileno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public static JSONObject getEchoParam() {
        return echoParam;
    }

    public static void setEchoParam(JSONObject echoParam) {
        ForgotPasswordRequest.echoParam = echoParam;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getBrokerid() {
        return brokerid;
    }

    public void setBrokerid(String brokerid) {
        this.brokerid = brokerid;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("panNo", this.panno);
        jo.put("email", this.emailid);
        jo.put("brokerid", this.brokerid);
        jo.put("passType", this.passType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.gscid = jo.optString("gscid");
        this.panno = jo.optString("panNo");
        this.emailid = jo.optString("email");
        this.brokerid = jo.optString("brokerid");
        this.passType = jo.optString("passType");
        return this;
    }


    public static void sendRequest(ForgotPasswordRequest request, String url, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), url, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject request, String url, Context ctx, ServiceResponseListener listener) {
        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(ForgotPasswordResponse.class);
        jsonRequest.setService("Login", "verifyPanEmail");


        /*if (url.isEmpty()) {
            jsonRequest.setService("Login", "jchange_password_mf");

        } else {
            jsonRequest.setService("Login", "jchange_password_mf", url);

        }
*/
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid,String panNo,String emailId, String brokerid, String passType, String url, Context ctx, ServiceResponseListener listener) {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setGscid(gscid);
        request.setPanno(panNo);
        request.setEmailid(emailId);
        request.setBrokerid(brokerid);
        request.setPassType(passType);
        try {
            sendRequest(request.toJSONObject(), url, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



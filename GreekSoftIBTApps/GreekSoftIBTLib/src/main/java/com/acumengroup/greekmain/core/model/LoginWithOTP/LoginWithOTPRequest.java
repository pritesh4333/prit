package com.acumengroup.greekmain.core.model.LoginWithOTP;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.STPOrder.STPOrderlRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekBaseJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginWithOTPRequest implements GreekRequestModel, GreekResponseModel {

    String gscid;
    String version_no;
    private static JSONObject echoParam = null;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("version_no", this.version_no);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.gscid = jo.optString("gscid");
        this.version_no = jo.optString("version_no");
        return this;
    }


    @Deprecated
    public static void sendRequest(String gscid, String url, Context ctx, ServiceResponseListener listener) {

        LoginWithOTPRequest request = new LoginWithOTPRequest();
        request.setGscid(gscid);
//        request.setVersion_no("1.0.0.9");
        request.setVersion_no("1.0.1.10");


        try {
            sendRequest(request.toJSONObject(), url, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, String url, Context ctx, ServiceResponseListener listener) {
//        GreekJSONRequest jsonRequest = null;
//        jsonRequest = new GreekJSONRequest(ctx, request);

        GreekBaseJSONRequest jsonRequest = null;
        jsonRequest = new GreekBaseJSONRequest(ctx, request);

        Log.e("STPOrderlRequest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(LoginWithOTPREsponse.class);

        if (url.isEmpty()) {
            jsonRequest.setService("logiwithOTP", "jLoginWithOTP");

        } else {
            jsonRequest.setService("logiwithOTP", "jLoginWithOTP", url);
        }

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
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

    public static void sendRequest(STPOrderlRequest request, String url, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), url, ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

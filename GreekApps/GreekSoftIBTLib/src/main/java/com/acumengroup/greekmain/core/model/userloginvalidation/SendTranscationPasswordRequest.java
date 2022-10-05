package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushant.patil on 3/28/2016.
 */
public class SendTranscationPasswordRequest implements GreekRequestModel, GreekResponseModel {
    private List<QuestionListManager> questionListManager = new ArrayList();
    private String gscid;
    private String sessionId;
    private String pass;
    private String brokerid;
    private String session;
    private String passtype;
    private String encryptionType;


    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "jvalidate_transaction_password";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public List<QuestionListManager> getQuestionListManager() {
        return questionListManager;
    }

    public void setQuestionListManager(List<QuestionListManager> questionListManager) {
        this.questionListManager = questionListManager;
    }

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getBrokerid() {
        return brokerid;
    }

    public void setBrokerid(String brokerid) {
        this.brokerid = brokerid;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getPasstype() {
        return passtype;
    }

    public void setPasstype(String passtype) {
        this.passtype = passtype;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("sessionId", this.sessionId);
        jo.put("pass", this.pass);
        jo.put("brokerid", this.brokerid);
        jo.put("session", this.session);
        jo.put("passType", this.passtype);
        jo.put("encryptionType", this.encryptionType);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gscid = jo.optString("gscid");
        this.sessionId = jo.optString("sessionId");
        this.pass = jo.optString("pass");
        this.brokerid = jo.optString("brokerid");
        this.session = jo.optString("session");
        this.passtype = jo.optString("passType");
        this.encryptionType = jo.optString("encryptionType");

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

    public static void sendRequest(SendTranscationPasswordRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(SendTranscationPasswordResponse.class);
        jsonRequest.setService("Login", "jvalidate_transaction_password");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String sessionId, String pass, String brokerid, String session, String passtype, Context ctx, ServiceResponseListener listener) {
        SendTranscationPasswordRequest request = new SendTranscationPasswordRequest();
        request.setGscid(gscid);
        request.setSessionId(sessionId);
        request.setPass(Util.convertPassMd5(pass));
        request.setBrokerid(brokerid);
        request.setSession(session);
        request.setPasstype(passtype);
        // 1 for md5 incrypted otherwise 0
        request.setEncryptionType("1");

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.acumengroup.greekmain.core.model.userloginvalidation;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sushant.patil on 3/23/2016.
 */
public class SendLoginQuestionAnswerRequest implements GreekRequestModel, GreekResponseModel {
    private List<QuestionListManager> questionListManager = new ArrayList();
    private String gscid;
    private String sessionId;
    private String deviceType;
    public static final String SERVICE_GROUP = "Login";
    public static final String SERVICE_NAME = "jsetQuestionBank";
    public static final String SERVICE_VERSION = "1.0.0";
    private static JSONObject echoParam = null;

    public List<QuestionListManager> getQuestionListManager() {
        return questionListManager;
    }

    public void setQuestionListManager(List<QuestionListManager> questionListManager) {
        this.questionListManager = questionListManager;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("gscid", this.gscid);
        jo.put("sessionId", this.sessionId);
        jo.put("deviceType", this.deviceType);
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.questionListManager.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("data2FA", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.gscid = jo.optString("gscid");
        this.sessionId = jo.optString("sessionId");
        this.deviceType = jo.optString("deviceType");

        if (jo.has("data2FA")) {
            JSONArray ja1 = jo.getJSONArray("data2FA");
            this.questionListManager = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    QuestionListManager data = new QuestionListManager();
                    data.fromJSON((JSONObject) o);
                    this.questionListManager.add(data);
                } else {
                    this.questionListManager.add((QuestionListManager) o);
                }
            }
        }
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

    public static void sendRequest(SendLoginQuestionAnswerRequest request, Context ctx, ServiceResponseListener listener) {
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
        jsonRequest.setResponseClass(SendLoginQuestionAnswerResponse.class);
        jsonRequest.setService("Login", "jsetQuestionBank");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequest(String gscid, String sessionId, String deviceType, List questionAnswerList, Context ctx, ServiceResponseListener listener) {
        SendLoginQuestionAnswerRequest request = new SendLoginQuestionAnswerRequest();
        request.setGscid(gscid);
        request.setSessionId(sessionId);
        request.setDeviceType(deviceType);
        request.setQuestionListManager(questionAnswerList);
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

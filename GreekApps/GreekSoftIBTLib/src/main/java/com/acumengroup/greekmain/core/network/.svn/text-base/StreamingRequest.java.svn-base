package com.acumengroup.greekmain.core.network;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class StreamingRequest {
    private String host;
    private int port;
    private final Context context;
    private final JSONObject reqJSON = new JSONObject();
    private JSONObject echoJSON = null;
    private RequestType requestType;
    private String serviceGroup;
    private String serviceName;
    private String gscid, clientCode, sessionId; //for login

    public StreamingRequest(Context context, JSONObject request) {
        this.context = context;
        setData("data", request);
        setResponseFormat("json");
    }

    public StreamingRequest(Context context, JSONObject request,String ReqId,String gscid,String sessionId) {
        this.context = context;
        setData("data", request);
        setResponseFormat("json");
        setData("gscid", gscid);
        setData("ReqId", ReqId);
        setData("sessionId", sessionId);

    }

    public void setLoginData(String gscid, String clientCode) {
        this.gscid = gscid;
        this.clientCode = clientCode;
        setData("gscid", gscid);
        setData("sessionId", sessionId);
        setData("gcid", clientCode);
    }

    public void setService(String serviceGroup, String serviceName) {
        this.serviceGroup = serviceGroup;
        this.serviceName = serviceName;
        setData("svcName", serviceName);
        setData("svcGroup", serviceGroup);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRequest() {
        if (reqJSON != null) {
            try {
                JSONObject baseJSON = new JSONObject();
                baseJSON.put("request", reqJSON);
                if (echoJSON != null) {
                    baseJSON.put("echo", echoJSON);
                }
                return baseJSON.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * quote, index, order etc
     *
     * @param streamingType
     */
    public void setStreamingType(String streamingType) {
        setData("streaming_type", streamingType);
    }

    public void setDepth(boolean depthRequired) {
        if (depthRequired) {
            setData("depth", "1");
        } else {
            setData("depth", "0");
        }
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
        if (requestType == RequestType.SUBSCRIBE) setData("request_type", "subscribe");
        else if (requestType == RequestType.UNSUBSCRIBE) setData("request_type", "unsubscribe");
    }

    public void setResponseFormat(String responseFormat) {
        setData("response_format", responseFormat);
    }

    public void setData(String key, Object value) {
        try {
            reqJSON.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addEchoParam(String key, String value) {
        try {
            if (echoJSON == null) echoJSON = new JSONObject();
            echoJSON.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public enum RequestType {
        SUBSCRIBE, UNSUBSCRIBE
    }
}
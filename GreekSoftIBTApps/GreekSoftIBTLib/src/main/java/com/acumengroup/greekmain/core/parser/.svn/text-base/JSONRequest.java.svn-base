package com.acumengroup.greekmain.core.parser;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.util.logger.GreekLog;
import com.loopj.android.http.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Arcadia
 */

public class JSONRequest extends ServiceRequest {
    protected Context context;
    private Class responseClass;
    private JSONObject reqJSON = new JSONObject();
    private JSONObject echoJSON = null;
    private String baseUrl;

    private String serviceGroup, serviceName;

    public JSONRequest(Context context, JSONObject request) {
        this.context = context;
        setReadTimeout(2 * 60 * 1000);
        setConnectionTimeout(60 * 1000);
        setData("data", request);
        setPostRequest(true);
    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
    }

    public void setService(String serviceGroup, String serviceName) {
        this.serviceGroup = serviceGroup;
        this.serviceName = serviceName;
        // this.serviceVersion = serviceVersion;
        setData("svcName", serviceName);
        setData("svcGroup", serviceGroup);
        setUrl(getBaseUrl() + "/" + serviceName);
    }

    public void setService(String serviceGroup, String serviceName, String URL) {
        this.serviceGroup = serviceGroup;
        this.serviceName = serviceName;
        // this.serviceVersion = serviceVersion;
        setData("svcName", serviceName);
        setData("svcGroup", serviceGroup);
        setUrl(URL + "/" + serviceName);
    }

    public String getServiceGroup() {
        return serviceGroup;
    }

    public String getServiceName() {
        return serviceName;
    }


    @Override
    protected void buildRequestBody(HttpPost httppost) throws IOException {
        if (reqJSON != null) {
            try {
                JSONObject baseJSON = new JSONObject();
                baseJSON.put("request", reqJSON);

                // Send echo param if exists
                if (echoJSON != null) {
                    baseJSON.put("echo", echoJSON);
                }

                String jsonRequest = baseJSON.toString();
                Log.d("JSONRequest", "POST Request Body===>" + jsonRequest);
                String encryptedJsonRequest = encodeToBase64(jsonRequest);
                // Set content type as json
                setContentType("application/json");
                setRequest(encryptedJsonRequest);
                super.buildRequestBody(httppost);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String decodeBase64(String decodeData) {
        String decodedString = new String(android.util.Base64.decode(decodeData, android.util.Base64.DEFAULT));
        return decodedString;
    }

    public String encodeToBase64(String stringToEncode) {
        byte[] data = new byte[0];
        try {
            data = stringToEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encyrpt = Base64.encodeToString(data, Base64.NO_WRAP);
        return encyrpt;
    }

    @Override
    protected Object readResponse(HttpResponse httpResponse) throws Exception {

        String data = (String) super.readResponse(httpResponse);
        JSONResponse jsonResponse;

        if (getResponseClass() == null) {
            GreekLog.error("Response Class not set.");
            jsonResponse = new JSONResponse(data, id);
        } else {
            jsonResponse = new JSONResponse(data, responseClass, id);
        }

        jsonResponse.setCache(getCacheKey());
        return jsonResponse;
    }

    /**
     * @param key
     * @param value <br>
     *              <br>
     *              add echo.
     */
    public void addEchoParam(String key, String value) {
        try {
            if (echoJSON == null) echoJSON = new JSONObject();
            echoJSON.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getEchoParam() {
        return echoJSON;
    }

    public void setEchoParam(JSONObject jsonObject) {
        echoJSON = jsonObject;
    }

    public String getEchoParam(String key) {
        try {
            return echoJSON.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Request Json Object creater.
    public void setData(String key, Object value) {
        try {
            reqJSON.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

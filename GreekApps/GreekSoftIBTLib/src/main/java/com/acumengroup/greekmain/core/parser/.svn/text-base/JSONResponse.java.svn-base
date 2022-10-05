package com.acumengroup.greekmain.core.parser;

import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Arcadia
 */
public class JSONResponse implements Serializable {

    private final String ECHO = "echo";
    private final String CONFIG = "config";
    private final String DATA = "data";
    private final String RESPONSE = "response";
    private final String SERVICE_GROUP = "svcGroup";
    private final String SERVICE_NAME = "svcName";
    private final String INFO_ID = "infoID";
    private final String INFO_MSG = "infoMsg";
    private final String SESSION_ID = "sessionId";

    private Class resClass;
    private int requestId;
    private String jsonString;
    private String cacheKey;

    private transient JSONObject resObject;

    /**
     * Constructor gets the JSON String
     *
     * @throws JSONException
     */
    public JSONResponse(String data, Class response, int requestId) throws JSONException {
        resObject = new JSONObject(data);
        jsonString = data;
        this.resClass = response;
        this.requestId = requestId;

    }

    public JSONResponse(String data, int requestId) throws JSONException {
        resObject = new JSONObject(data);
        jsonString = data;
        this.requestId = requestId;
    }

    public int getId() {
        return requestId;
    }

    public void setCache(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    /**
     * Gives the Service name.
     *
     * @return serviceName string.
     */
    public String getServiceName() {
        return (String) getData(SERVICE_NAME);
    }

    /**
     * Gives the Service group.
     *
     * @return serviceName string.
     */
    public String getServiceGroup() {
        return (String) getData(SERVICE_GROUP);
    }

    public String getSessionId() {
        if (getData(SESSION_ID).toString().equals("null") || getData(SESSION_ID).toString().equals("")) {
            return null;
        }
        return (String) getData(SESSION_ID);
    }

    /**
     * Gives the Info ID.
     *
     * @return Info ID.
     */
    public String getInfoID() {
        return (String) getData(INFO_ID);
    }

    /**
     * Gives the Info msg.
     *
     * @return infoMsg string.
     */
    public String getInfoMsg() {
        String msg = (String) getData(INFO_MSG);
        if (msg == null) msg = "";
        return msg;
    }

    /**
     * Gives the echo value for concern key.
     *
     * @return echo value as string.
     */
    public String getEchoParam(String key) {
        return getEchoParamData(key);
    }

    public Object getData(String key) {
        try {
            checkResponseData();
            JSONObject jo = (JSONObject) resObject.opt(RESPONSE);
            if (!jo.has(key)) return null;
            return jo.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkResponseData() throws JSONException {
        if (resObject == null) {
            resObject = new JSONObject(jsonString);
        }
    }

    public JSONObject getResObject() throws JSONException {
        if (resObject == null) {
            resObject = new JSONObject(jsonString);
        }

        return resObject;
    }

    private String getEchoParamData(String key) {
        try {
            checkResponseData();
            JSONObject jo = (JSONObject) resObject.opt(ECHO);
            return jo.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getResponseData() {
        try {
            checkResponseData();
            return (JSONObject) getData(DATA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        try {
            return getResObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * resClass for the current request.
     *
     * @return resClass JSONObject.
     */
    public GreekResponseModel getResponse() throws Exception {
        checkResponseData();
        JSONObject jo = (JSONObject) resObject.opt(RESPONSE);
        jo = jo.optJSONObject(DATA);
        if (jo.length() > 0) {
            GreekResponseModel mrm = (GreekResponseModel) resClass.newInstance();
            return mrm.fromJSON(jo);
        } else {
            return null;
        }
    }

}

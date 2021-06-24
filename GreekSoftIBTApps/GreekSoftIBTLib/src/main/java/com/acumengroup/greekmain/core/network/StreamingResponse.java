package com.acumengroup.greekmain.core.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Arcadia
 */
public class StreamingResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient JSONObject resObject;

    public StreamingResponse(String data) {
        try {
            resObject = new JSONObject(data).getJSONObject("response");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStreamingType() {
        try {
            return resObject.getString("streaming_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getSvcName() {
        try {
            return resObject.getString("svcName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public JSONObject getResponse() {
        try {
            return resObject.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
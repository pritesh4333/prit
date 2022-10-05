package com.acumengroup.greekmain.core.model.dematltpdetails;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 22-Aug-17.
 */

public class DematLtpDetailsResponse implements GreekRequestModel, GreekResponseModel {
    private List<com.acumengroup.greekmain.core.model.dematltpdetails.dematLtpList> dematLtpList = new ArrayList();
    private String ErrorCode;

    public List<com.acumengroup.greekmain.core.model.dematltpdetails.dematLtpList> getDematLtpList() {
        return dematLtpList;
    }

    public void setDematLtpList(List<com.acumengroup.greekmain.core.model.dematltpdetails.dematLtpList> dematLtpList) {
        this.dematLtpList = dematLtpList;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.dematLtpList.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("tokenList", ja1);
        jo.put("ErrorCode", this.ErrorCode);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.ErrorCode = jo.optString("ErrorCode");

        if (jo.has("tokenList")) {
            JSONArray ja1 = jo.getJSONArray("tokenList");
            this.dematLtpList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    com.acumengroup.greekmain.core.model.dematltpdetails.dematLtpList data = new dematLtpList();
                    data.fromJSON((JSONObject) o);
                    this.dematLtpList.add(data);
                } else {
                    this.dematLtpList.add((com.acumengroup.greekmain.core.model.dematltpdetails.dematLtpList) o);
                }
            }
        }
        return this;
    }
}




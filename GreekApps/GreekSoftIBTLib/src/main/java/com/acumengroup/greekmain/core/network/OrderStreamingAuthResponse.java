package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/23/2016.
 */
public class OrderStreamingAuthResponse implements GreekRequestModel, GreekResponseModel {
    //0 - success
    //1 - failed
    //2 - server force close
    private String error_code;
    private String gscid;
    private String reconnect;
    private String logtime;

    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReconnect() {
        return reconnect;
    }

    public void setReconnect(String reconnect) {
        this.reconnect = reconnect;
    }

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("error_code", this.error_code);
        jo.put("gscid", this.gscid);
        jo.put("reconnect", this.reconnect);
        jo.put("logtime", this.logtime);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.error_code = jo.optString("error_code");
        this.reconnect = jo.optString("reconnect");
        this.logtime = jo.optString("logtime");
        //this.gscid =  new String(Base64.decode(jo.optString("gscid"), Base64.DEFAULT));
        return this;
    }
}

package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 01-Feb-17.
 */

public class GrossExposureResponse implements GreekRequestModel, GreekResponseModel {
    //0 - success
    //1 - failed
    private String GrossExposure;
    private String NetTurnOver;
    private String gcid;

    public String getGrossExposure() {
        return GrossExposure;
    }

    public void setGrossExposure(String grossExposure) {
        GrossExposure = grossExposure;
    }

    public String getNetTurnOver() {
        return NetTurnOver;
    }

    public void setNetTurnOver(String netTurnOver) {
        NetTurnOver = netTurnOver;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("GrossExposure", this.GrossExposure);
        jo.put("NetTurnOver", this.NetTurnOver);
        jo.put("gcid", this.gcid);

        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.GrossExposure = jo.optString("GrossExposure");
        this.NetTurnOver = jo.optString("NetTurnOver");
        this.gcid = jo.optString("gcid");

        return this;
    }
}

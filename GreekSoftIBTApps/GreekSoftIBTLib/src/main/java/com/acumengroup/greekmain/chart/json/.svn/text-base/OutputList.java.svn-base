package com.acumengroup.greekmain.chart.json;

import org.json.JSONException;
import org.json.JSONObject;

public class OutputList {

    /**
     *
     */
    private String linetype;
    /**
     *
     */
    private String key;
    /**
     *
     */
    private String plottype;

    /**
     *
     */
    public String getLinetype() {
        return linetype;
    }

    /**
     *
     */
    public void setLinetype(String linetype) {
        this.linetype = linetype;
    }

    /**
     *
     */
    public String getKey() {
        return key;
    }

    /**
     *
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     */
    public String getPlottype() {
        return plottype;
    }

    /**
     *
     */
    public void setPlottype(String plottype) {
        this.plottype = plottype;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("linetype", linetype);
        jo.put("key", key);
        jo.put("plottype", plottype);
        return jo;
    }

    public void fromJSON(JSONObject jo) throws JSONException {
        linetype = jo.optString("linetype");
        key = jo.optString("key");
        plottype = jo.optString("plottype");

//return this;
    }
}

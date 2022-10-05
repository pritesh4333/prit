package com.acumengroup.greekmain.chart.json;

import org.json.JSONException;
import org.json.JSONObject;

public class InputList {

    /**
     *
     */
    private String defaultValue;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String description;

    /**
     *
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public String getType() {
        return type;
    }

    /**
     *
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("defaultValue", defaultValue);
        jo.put("name", name);
        jo.put("type", type);
        jo.put("description", description);
        return jo;
    }

    public void fromJSON(JSONObject jo) throws JSONException {
        defaultValue = jo.optString("defaultValue");
        name = jo.optString("name");
        type = jo.optString("type");
        description = jo.optString("description");

//return this;
    }
}

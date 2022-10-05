package com.acumengroup.greekmain.chart.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Indicator {

    /**
     *
     */
    private String name;
    /**
     *
     */
    private String description;
    /**
     *
     */
    private String category;
    /**
     *
     */
    private List<com.acumengroup.greekmain.chart.json.OutputList> OutputList = new ArrayList<com.acumengroup.greekmain.chart.json.OutputList>();
    /**
     *
     */
    private List<com.acumengroup.greekmain.chart.json.InputList> InputList = new ArrayList<com.acumengroup.greekmain.chart.json.InputList>();

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
    public String getDescription() {
        return description;
    }

    /**
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     */
    public List<com.acumengroup.greekmain.chart.json.OutputList> getOutputList() {
        return OutputList;
    }

    /**
     *
     */
    public void setOutputList(List<com.acumengroup.greekmain.chart.json.OutputList> OutputList) {
        this.OutputList = OutputList;
    }

    /**
     *
     */
    public List<com.acumengroup.greekmain.chart.json.InputList> getInputList() {
        return InputList;
    }

    /**
     *
     */
    public void setInputList(List<com.acumengroup.greekmain.chart.json.InputList> InputList) {
        this.InputList = InputList;
    }

    /*public JSONObject toJSONObject() throws JSONException
        {
            JSONObject jo = new JSONObject();
            jo.put("name",name);
            jo.put("description",description);
            jo.put("category",category);

            JSONArray ja1 = new JSONArray();
            for (Iterator iterator = OutputList.iterator(); iterator.hasNext();)
    {

        Object o = (Object) iterator.next();
        if( o instanceof MSFReqModel ){
            ja1.put(((MSFReqModel)o).toJSONObject());

        }else{
            ja1.put(o);
        }
    }
            jo.put("OutputList",ja1);

            JSONArray ja2 = new JSONArray();
            for (Iterator iterator = InputList.iterator(); iterator.hasNext();)
    {

        Object o = (Object) iterator.next();
        if( o instanceof MSFReqModel ){
            ja2.put(((MSFReqModel)o).toJSONObject());

        }else{
            ja2.put(o);
        }
    }
            jo.put("InputList",ja2);
    return jo;
    }
    */
    public void fromJSON(JSONObject jo) throws JSONException {
        name = jo.optString("name");
        description = jo.optString("description");
        category = jo.optString("category");

        JSONArray ja1 = jo.getJSONArray("OutputList");
        OutputList = new ArrayList(ja1.length());
        for (int i = 0; i < ja1.length(); i++) {
            Object o = ja1.get(i);
            if (o instanceof JSONObject) {
                com.acumengroup.greekmain.chart.json.OutputList data = new OutputList();
                data.fromJSON((JSONObject) o);
                OutputList.add(data);
            } else {
                OutputList.add((com.acumengroup.greekmain.chart.json.OutputList) o);
            }
        }
        JSONArray ja2 = jo.getJSONArray("InputList");
        InputList = new ArrayList(ja2.length());
        for (int i = 0; i < ja2.length(); i++) {
            Object o = ja2.get(i);
            if (o instanceof JSONObject) {
                com.acumengroup.greekmain.chart.json.InputList data = new InputList();
                data.fromJSON((JSONObject) o);
                InputList.add(data);
            } else {
                InputList.add((com.acumengroup.greekmain.chart.json.InputList) o);
            }
        }
//return this;
    }
}

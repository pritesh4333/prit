package com.acumengroup.greekmain.chart.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndicatorList {

    /**
     *
     */
    private List<Indicator> IndicatorList = new ArrayList<Indicator>();

    /**
     *
     */
    public List<Indicator> getIndicatorList() {
        return IndicatorList;
    }

    /**
     *
     */
    public void setIndicatorList(List<Indicator> IndicatorList) {
        this.IndicatorList = IndicatorList;
    }

    /*public JSONObject toJSONObject() throws JSONException
        {
            JSONObject jo = new JSONObject();

            JSONArray ja1 = new JSONArray();
            for (Iterator iterator = IndicatorList.iterator(); iterator.hasNext();)
    {

        Object o = (Object) iterator.next();
        if( o instanceof MSFReqModel ){
            ja1.put(((MSFReqModel)o).toJSONObject());

        }else{
            ja1.put(o);
        }
    }
            jo.put("IndicatorList",ja1);
    return jo;
    }
    */
    public void fromJSON(JSONObject jo) throws JSONException {

        JSONArray ja1 = jo.getJSONArray("IndicatorList");
        IndicatorList = new ArrayList(ja1.length());
        for (int i = 0; i < ja1.length(); i++) {
            Object o = ja1.get(i);
            if (o instanceof JSONObject) {
                Indicator data = new Indicator();
                data.fromJSON((JSONObject) o);
                IndicatorList.add(data);
            } else {
                IndicatorList.add((Indicator) o);
            }
        }
//return this;
    }
}

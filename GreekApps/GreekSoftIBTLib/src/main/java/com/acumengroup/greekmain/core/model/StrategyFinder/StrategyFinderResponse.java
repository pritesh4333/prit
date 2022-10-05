package com.acumengroup.greekmain.core.model.StrategyFinder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StrategyFinderResponse implements GreekRequestModel, GreekResponseModel {
    private List<data> strategyFinderResponse = new ArrayList();


    public List<data> getStrategyFinderResponse() {
        return strategyFinderResponse;
    }

    public void setStrategyFinderResponse(List<data> strategyFinderResponse) {
        this.strategyFinderResponse = strategyFinderResponse;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();


        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.strategyFinderResponse.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        return  jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        if(jo.optJSONArray("data") !=null) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.strategyFinderResponse = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    data data = new data();
                    data.fromJSON((JSONObject) o);
                    this.strategyFinderResponse.add(data);
                } else {
                    this.strategyFinderResponse.add((data) o);
                }
            }
        }


        return this;
    }
}

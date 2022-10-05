package com.acumengroup.greekmain.core.model.recommonDisplay;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecommDisplayResponse implements GreekRequestModel, GreekResponseModel {

    private List<Data> data = new ArrayList();


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();


        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.data.iterator();
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

        if(jo.optJSONArray("Data") !=null) {
            JSONArray ja1 = jo.getJSONArray("Data");
            this.data = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    Data data = new Data();
                    data.fromJSON((JSONObject) o);
                    this.data.add(data);
                } else {
                    this.data.add((Data) o);
                }
            }
        }


        return this;
    }
}
package com.acumengroup.greekmain.core.model.userloginvalidation;


import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StateCountryResponse implements GreekRequestModel, GreekResponseModel {
    private List<String> symbolSpecificNews = new ArrayList();

    public List<String> getSymbolSpecificNews() {
        return this.symbolSpecificNews;
    }

    public void setSymbolSpecificNews(List<String> symbolSpecificNews) {
        this.symbolSpecificNews = symbolSpecificNews;
    }


    public JSONObject toJSONObject()
            throws JSONException {

        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.symbolSpecificNews.iterator();

        while (iterator.hasNext()) {
            Object o = iterator.next();

            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());

            } else {
                ja1.put(o);
            }
        }

        jo.put("data", ja1);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("newsdata");
            this.symbolSpecificNews = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);

                this.symbolSpecificNews.add(i, ja1.getString(i));

//                if ((o instanceof JSONObject))
//                {
//                    SymbolSpecificNew data = new SymbolSpecificNew();
//                    data.fromJSON((JSONObject) o);
//                    this.symbolSpecificNews.add(data);
//
//                } else {
//                    this.symbolSpecificNews.add((SymbolSpecificNew) o);
//                }
            }
        }
        return this;
    }
}





package com.acumengroup.greekmain.core.model.searchequityinformation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 20-Mar-17.
 */

public class SearchInstrumentNameResponse implements GreekRequestModel, GreekResponseModel {
    private List<InstrumentName> instrumentNameModelList = new ArrayList<>();

    public List<InstrumentName> getInstrumentNameModelList() {
        return instrumentNameModelList;
    }

    public void setInstrumentNameModelList(List<InstrumentName> instrumentNameModelList) {
        this.instrumentNameModelList = instrumentNameModelList;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        if (jo.has("Data")) {
            JSONArray ja1 = jo.getJSONArray("Data");
            this.instrumentNameModelList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    InstrumentName data = new InstrumentName();
                    data.fromJSON((JSONObject) o);
                    this.instrumentNameModelList.add(data);
                } else {
                    this.instrumentNameModelList.add((InstrumentName) o);
                }
            }
        }
        return this;
    }
}
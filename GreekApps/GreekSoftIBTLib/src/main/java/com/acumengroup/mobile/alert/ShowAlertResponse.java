package com.acumengroup.mobile.alert;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sushant on 9/29/2016.
 */

public class ShowAlertResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<ShowAlertDetail> showAlertDetails ;

    public List<ShowAlertDetail> getShowAlertDetails() {
        return showAlertDetails;
    }

    public void setShowAlertDetails(List<ShowAlertDetail> showAlertDetails) {
        this.showAlertDetails = showAlertDetails;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.showAlertDetails.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("SymbolName", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        if (jo.has("SymbolName")) {
            JSONArray ja1 = jo.getJSONArray("SymbolName");
            this.showAlertDetails = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    ShowAlertDetail data = new ShowAlertDetail();
                    data.fromJSON((JSONObject) o);
                    this.showAlertDetails.add(data);
                } else {
                    this.showAlertDetails.add((ShowAlertDetail) o);
                }
            }
        }
        return this;
    }
}



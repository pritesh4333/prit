package com.acumengroup.greekmain.core.services;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.tradenetpositionsummary.OverAllPL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Arcadia
 */
public class TradeNetPositionSummaryResponse implements GreekResponseModel {
    private OverAllPL overAllPL;
    private List<CustomNetPositionSummary> netPositionSummary = new ArrayList();

    public TradeNetPositionSummaryResponse() {
    }

    public OverAllPL getOverAllPL() {
        return this.overAllPL;
    }

    public void setOverAllPL(OverAllPL overAllPL) {
        this.overAllPL = overAllPL;
    }

    public List<CustomNetPositionSummary> getNetPositionSummary() {
        return this.netPositionSummary;
    }

    public void setNetPositionSummary(List<CustomNetPositionSummary> netPositionSummary) {
        this.netPositionSummary = netPositionSummary;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        if (this.overAllPL instanceof GreekRequestModel) {
            jo.put("overAllPL", this.overAllPL.toJSONObject());
        }

        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.netPositionSummary.iterator();

        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof GreekRequestModel) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }

        jo.put("netPositionSummary", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("overAllPL")) {
            this.overAllPL = new OverAllPL();
            this.overAllPL.fromJSON(jo.getJSONObject("overAllPL"));
        }

        if (jo.has("netPositionSummary")) {
            JSONArray ja1 = jo.getJSONArray("netPositionSummary");
            this.netPositionSummary = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                Object o = ja1.get(i);
                if (o instanceof JSONObject) {
                    CustomNetPositionSummary data = new CustomNetPositionSummary();
                    data.fromJSON((JSONObject) o);
                    this.netPositionSummary.add(data);
                } else {
                    this.netPositionSummary.add((CustomNetPositionSummary) o);
                }
            }
        }

        return this;
    }
}

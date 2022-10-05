package com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScripDetails
        implements GreekRequestModel, GreekResponseModel {
    private AvailableExchange availableExchange;
    private List<Expiry_strickPrice> expiry_strickPrice = new ArrayList();

    public AvailableExchange getAvailableExchange() {
        return this.availableExchange;
    }

    public void setAvailableExchange(AvailableExchange availableExchange) {
        this.availableExchange = availableExchange;
    }

    public List<Expiry_strickPrice> getExpiry_strickPrice() {
        return this.expiry_strickPrice;
    }

    public void setExpiry_strickPrice(List<Expiry_strickPrice> expiry_strickPrice) {
        this.expiry_strickPrice = expiry_strickPrice;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        if ((this.availableExchange instanceof GreekRequestModel)) {
            jo.put("availableExchange", this.availableExchange.toJSONObject());
        }
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.expiry_strickPrice.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("expiry_strickPrice", ja1);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("availableExchange")) {
            this.availableExchange = new AvailableExchange();
            this.availableExchange.fromJSON(jo.getJSONObject("availableExchange"));
        }
        if (jo.has("expiry_strickPrice")) {
            JSONArray ja1 = jo.getJSONArray("expiry_strickPrice");
            this.expiry_strickPrice = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    Expiry_strickPrice data = new Expiry_strickPrice();
                    data.fromJSON((JSONObject) o);
                    this.expiry_strickPrice.add(data);
                } else {
                    this.expiry_strickPrice.add((Expiry_strickPrice) o);
                }
            }
        }
        return this;
    }
}



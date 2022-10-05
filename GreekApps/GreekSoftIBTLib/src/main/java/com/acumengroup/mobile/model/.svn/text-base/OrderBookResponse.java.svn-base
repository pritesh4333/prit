package com.acumengroup.mobile.model;

import com.acumengroup.greekmain.core.model.tradeorderbook.LegInfo;
import com.acumengroup.greekmain.core.model.tradeorderbook.OrderBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 1/15/2016.
 */
public class OrderBookResponse {

    private List<OrderBook> orderBookModelList = new ArrayList<>();
    private List<LegInfo> legInfoModelList = new ArrayList<>();

    public OrderBookResponse() {
    }

    public List<OrderBook> getOrderBookModelList() {
        return orderBookModelList;
    }

    public void setOrderBookModelList(List<OrderBook> orderBookModelList) {
        this.orderBookModelList = orderBookModelList;
    }

    public List<LegInfo> getLegInfoModelList() {
        return legInfoModelList;
    }

    public void setLegInfoModelList(List<LegInfo> legInfoModelList) {
        this.legInfoModelList = legInfoModelList;
    }

    public List<OrderBook> fromJSON(JSONObject jo) throws JSONException {
        if (jo.has("data")) {
            JSONArray ja1 = jo.getJSONArray("data");
            this.orderBookModelList = new ArrayList(ja1.length());

            for (int i = 0; i < ja1.length(); ++i) {
                JSONObject o = (JSONObject) ja1.get(i);
                OrderBook data = new OrderBook();
                data.fromJSON(o);
                this.orderBookModelList.add(data);


//                JSONArray ja2 = o.getJSONArray("LegInfo");
//                this.legInfoModelList = new ArrayList(ja2.length());
//
//                for (int k = 0; k < ja2.length(); ++k) {
//                    JSONObject l = (JSONObject) ja2.get(i);
//                    LegInfo legInfo = new LegInfo();
//                    legInfo.fromJSON(l);
//                    this.legInfoModelList.add(legInfo);
//                }
            }
        }
        return this.orderBookModelList;
    }
}

package com.acumengroup.greekmain.core.services;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.trade.TradeBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by summert on 6/4/2015.
 */
public class TradeBookResponse implements GreekRequestModel, GreekResponseModel {
    private List<TradeBook> tradeBook = new ArrayList();


    public TradeBookResponse() {
    }

    public List<TradeBook> getTradeBook() {
        return this.tradeBook;
    }

    public void setTradeBook(List<TradeBook> tradeBook) {
        this.tradeBook = tradeBook;
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator ja2 = this.tradeBook.iterator();

        while (ja2.hasNext()) {
            Object ja3 = ja2.next();
            if (ja3 instanceof GreekRequestModel) {
                ja1.put(((GreekRequestModel) ja3).toJSONObject());
            } else {
                ja1.put(ja3);
            }
        }

        jo.put("tradebookdata", ja1);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        JSONArray ja3;
        int i;
        Object o;
        if (jo.has("tradebookdata")) {
            ja3 = jo.getJSONArray("tradebookdata");
            this.tradeBook = new ArrayList(ja3.length());

            for (i = 0; i < ja3.length(); ++i) {
                o = ja3.get(i);
                if (o instanceof JSONObject) {
                    TradeBook data = new TradeBook();
                    data.fromJSON((JSONObject) o);
                    this.tradeBook.add(data);
                } else {
                    this.tradeBook.add((TradeBook) o);
                }
            }
        }
        return this;
    }
}
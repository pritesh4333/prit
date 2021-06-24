package com.acumengroup.greekmain.core.network;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sushant on 10/3/2016.
 */

//TODO:This class used to receive reponse of executed alerts

public class AlertExecutedStreamingResponse implements GreekRequestModel, GreekResponseModel {
    //0 - success
    //1 - failed
    private String CurrentValue;
    private String Symbol;
    private String RangeValue;
    private String Message;

    public String getCurrentValue() {
        return CurrentValue;
    }

    public void setCurrentValue(String currentValue) {
        CurrentValue = currentValue;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getRangeValue() {
        return RangeValue;
    }

    public void setRangeValue(String rangeValue) {
        RangeValue = rangeValue;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("Symbol", this.Symbol);
        jo.put("CurrentValue", this.CurrentValue);
        jo.put("RangeValue", this.RangeValue);
        jo.put("Message", this.Message);

        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.CurrentValue = jo.optString("CurrentValue");
        this.Message = jo.optString("Message");
        this.Symbol = jo.optString("Symbol");
        this.RangeValue = jo.optString("RangeValue");

        return this;
    }
}

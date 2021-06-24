package com.acumengroup.greekmain.core.model.streamerorderconfirmation;

import android.util.Base64;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 14-Sep-17.
 */

public class StreamerSymbolVarMarginResponse implements GreekRequestModel, GreekResponseModel {

    private String VARMargin;
    private String VARPercentage;
    private String ELMMargin;
    private String ELMPercentage;
    private String SpanMarginBuy;
    private String SpanMarginSell;
    private String ExposMargin;
    private String ExposeMarginPer;

    public String getSpanMarginBuy() {
        return SpanMarginBuy;
    }

    public void setSpanMarginBuy(String spanMarginBuy) {
        SpanMarginBuy = spanMarginBuy;
    }

    public String getSpanMarginSell() {
        return SpanMarginSell;
    }

    public void setSpanMarginSell(String spanMarginSell) {
        SpanMarginSell = spanMarginSell;
    }

    public String getExposMargin() {
        return ExposMargin;
    }

    public void setExposMargin(String exposMargin) {
        ExposMargin = exposMargin;
    }

    public String getExposeMarginPer() {
        return ExposeMarginPer;
    }

    public void setExposeMarginPer(String exposeMarginPer) {
        ExposeMarginPer = exposeMarginPer;
    }

    public String getVARMargin() {
        return VARMargin;
    }

    public void setVARMargin(String VARMargin) {
        this.VARMargin = VARMargin;
    }

    public String getVARPercentage() {
        return VARPercentage;
    }

    public void setVARPercentage(String VARPercentage) {
        this.VARPercentage = VARPercentage;
    }

    public String getELMMargin() {
        return ELMMargin;
    }

    public void setELMMargin(String ELMMargin) {
        this.ELMMargin = ELMMargin;
    }

    public String getELMPercentage() {
        return ELMPercentage;
    }

    public void setELMPercentage(String ELMPercentage) {
        this.ELMPercentage = ELMPercentage;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("VARMargin", this.VARMargin);
        jo.put("VARPercentage", this.VARPercentage);
        jo.put("ELMMargin", this.ELMMargin);
        jo.put("ELMPercentage", this.ELMPercentage);
        jo.put("SPANMargin_Buy", this.SpanMarginBuy);
        jo.put("SPANMargin_Sell", this.SpanMarginSell);
        jo.put("ExposMargin", this.ExposMargin);
        jo.put("ExposMargin_Per", this.ExposeMarginPer);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.VARMargin = jo.optString("VARMargin");
        this.VARPercentage = jo.optString("VARPercentage");
        this.ELMMargin = jo.optString("ELMMargin");
        this.ELMPercentage = jo.optString("ELMPercentage");
        this.SpanMarginBuy = jo.optString("SPANMargin_Buy");
        this.SpanMarginSell = jo.optString("SPANMargin_Sell");
        this.ExposMargin = jo.optString("ExposMargin");
        this.ExposeMarginPer = jo.optString("ExposMargin_Per");
        return this;
    }

    public String decodeBase64(String decodeData) {
        String decodedString = new String(Base64.decode(decodeData, Base64.DEFAULT));
        return decodedString;
    }
}


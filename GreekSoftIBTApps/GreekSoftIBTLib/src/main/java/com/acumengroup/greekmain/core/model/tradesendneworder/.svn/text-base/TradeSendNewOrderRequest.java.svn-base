package com.acumengroup.greekmain.core.model.tradesendneworder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class TradeSendNewOrderRequest
        implements GreekRequestModel, GreekResponseModel {
    private String triggerPrice;
    private String strToken;
    private String side; //action
    private String clientCode;
    private String validity;
    private String price;
    private String exchange;
    private String disQty;
    private String isSqOffOrder;
    private String tradeSymbol;
    private String lot;
    private String orderType;
    private String product;
    private String orderLife;
    private String qty;
    private String corderid;
    private String amo;


    private String offline;
    private String isPreOpen;
    private String isPostClosed;
    private long gtdExpiry;
    private String sl_price;
    private String target_price;

    private static JSONObject echoParam = null;

    public String getIsSqOffOrder() {
        return isSqOffOrder;
    }

    public void setIsSqOffOrder(String isSqOffOrder) {
        this.isSqOffOrder = isSqOffOrder;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getSl_price() {
        return sl_price;
    }

    public void setSl_price(String sl_price) {
        this.sl_price = sl_price;
    }

    public String getTarget_price() {
        return target_price;
    }

    public void setTarget_price(String target_price) {
        this.target_price = target_price;
    }

    public long getGtdExpiry() {
        return gtdExpiry;
    }

    public void setGtdExpiry(long gtdExpiry) {
        this.gtdExpiry = gtdExpiry;
    }

    public String getAmo() {
        return amo;
    }

    public void setAmo(String amo) {
        this.amo = amo;
    }

    public String getTriggerPrice() {
        return this.triggerPrice;
    }

    public void setTriggerPrice(String triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public String getStrToken() {
        return this.strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getDisQty() {
        return disQty;
    }

    public void setDisQty(String disQty) {
        this.disQty = disQty;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCorderid() {
        return corderid;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExchange() {
        return this.exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }


    public String getTradeSymbol() {
        return this.tradeSymbol;
    }

    public void setTradeSymbol(String tradeSymbol) {
        this.tradeSymbol = tradeSymbol;
    }


    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOrderLife() {
        return this.orderLife;
    }

    public void setOrderLife(String orderLife) {
        this.orderLife = orderLife;
    }


    public String setCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public String getIsPreOpen() {
        return isPreOpen;
    }

    public void setIsPreOpen(String isPreOpen) {
        this.isPreOpen = isPreOpen;
    }

    public String getIsPostClosed() {
        return isPostClosed;
    }

    public void setIsPostClosed(String isPostClosed) {
        this.isPostClosed = isPostClosed;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("trigger_price", this.triggerPrice);
        jo.put("gtoken", this.strToken);
        jo.put("side", this.side);
        jo.put("gcid", this.clientCode);
        jo.put("validity", this.validity);
        jo.put("price", this.price);
        jo.put("exchange", this.exchange);
        jo.put("disclosed_qty", this.disQty);
        jo.put("tradeSymbol", this.tradeSymbol);
        jo.put("lot", this.lot);
        jo.put("order_type", this.orderType);
        jo.put("product", this.product);
        jo.put("orderLife", this.orderLife);
        jo.put("qty", this.qty);
        jo.put("corderid", this.corderid);
        jo.put("amo", this.amo);
        jo.put("target_price", this.target_price);
        jo.put("sl_price", this.sl_price);
        jo.put("gtdExpiry", this.gtdExpiry);
        jo.put("is_post_closed", this.isPostClosed);
        jo.put("is_preopen_order", this.isPreOpen);
        jo.put("isSqOffOrder", this.isSqOffOrder);
        jo.put("offline", this.offline);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.triggerPrice = jo.optString("trigger_price");
        this.strToken = jo.optString("gtoken");
        this.side = jo.optString("side");
        this.clientCode = jo.optString("gcid");
        this.validity = jo.optString("validity");
        this.price = jo.optString("price");
        this.exchange = jo.optString("exchange");
        this.disQty = jo.optString("disclosed_qty");
        this.tradeSymbol = jo.optString("tradeSymbol");
        this.lot = jo.optString("lot");
        this.orderType = jo.optString("order_type");
        this.product = jo.optString("product");
        this.orderLife = jo.optString("orderLife");
        this.qty = jo.optString("qty");
        this.corderid = jo.optString("corderid");
        this.amo = jo.optString("amo");
        this.target_price = jo.optString("target_price");
        this.sl_price = jo.optString("sl_price");
        this.offline = jo.optString("offline");
        this.gtdExpiry = Long.valueOf(jo.optLong("gtdExpiry"));
        this.isPostClosed = jo.optString("is_post_closed");
        this.isPreOpen = jo.optString("is_preopen_order");
        this.isSqOffOrder = jo.optString("isSqOffOrder");
        return this;
    }

    public static void addEchoParam(String key, String value) {
        try {
            if (echoParam == null) {
                echoParam = new JSONObject();
            }
            echoParam.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
  
  /*public static void sendRequest(TradeSendNewOrderRequest request, Context ctx, ServiceResponseListener listener)
  {
    try
    {
      sendRequest(request.toJSONObject(), ctx, listener);
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }*/
  
  /*public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener)
  {
    GreekJSONRequest jsonRequest = null;
    jsonRequest = new GreekJSONRequest(ctx, request);
    if (echoParam != null)
    {
      jsonRequest.setEchoParam(echoParam);
      echoParam = null;
    }
    jsonRequest.setResponseClass(TradeSendNewOrderResponse.class);
    jsonRequest.setService("Trade", "SendNewOrder", "1.0.0");
    ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
  }*/
  
  /*@Deprecated
  public static void sendRequest(String action, String clientCode, Double disQty, String exchange, Double lot, String orderLife, String orderType, String price, String product, Double qty, String strToken, String tradeSymbol, String triggerPrice, String validityDate, Context ctx, ServiceResponseListener listener)
  {
    TradeSendNewOrderRequest request = new TradeSendNewOrderRequest();
    request.setAction(action);
    //request.setClientCode(clientCode);
    request.setDisQty(disQty);
    request.setExchange(exchange);
    request.setLot(lot);
    request.setOrderLife(orderLife);
    request.setOrderType(orderType);
    request.setPrice(price);
    request.setProduct(product);
    request.setQty(qty);
    request.setStrToken(strToken);
    request.setTradeSymbol(tradeSymbol);
    request.setTriggerPrice(triggerPrice);
    request.setValidityDate(validityDate);
    try
    {
      sendRequest(request.toJSONObject(), ctx, listener);
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }*/
}



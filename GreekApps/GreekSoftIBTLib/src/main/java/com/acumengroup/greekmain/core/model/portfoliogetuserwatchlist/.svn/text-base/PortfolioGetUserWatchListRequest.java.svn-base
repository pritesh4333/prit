package com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PortfolioGetUserWatchListRequest
  implements GreekRequestModel, GreekResponseModel
{
  private String strToken;
  private String clientCode;
  private static JSONObject echoParam = null;
  
  public String getStrToken()
  {
    return this.strToken;
  }
  
  public void setStrToken(String strToken)
  {
    this.strToken = strToken;
  }
  
  public String getClientCode()
  {
    return this.clientCode;
  }
  
  public void setClientCode(String clientCode)
  {
    this.clientCode = clientCode;
  }
  
  public JSONObject toJSONObject()
    throws JSONException
  {
    JSONObject jo = new JSONObject();
    jo.put("strToken", this.strToken);
    jo.put("gcid", this.clientCode);
    return jo;
  }
  
  public GreekResponseModel fromJSON(JSONObject jo)
    throws JSONException
  {
    this.strToken = jo.optString("strToken");
    this.clientCode = jo.optString("clientCode");
    return this;
  }
  
  public static void addEchoParam(String key, String value)
  {
    try
    {
      if (echoParam == null) {
        echoParam = new JSONObject();
      }
      echoParam.put(key, value);
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void sendRequest(PortfolioGetUserWatchListRequest request, Context ctx, ServiceResponseListener listener)
  {
    try
    {
      sendRequest(request.toJSONObject(), ctx, listener);
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener)
  {
    GreekJSONRequest jsonRequest = null;
    jsonRequest = new GreekJSONRequest(ctx, request);
    if (echoParam != null)
    {
      jsonRequest.setEchoParam(echoParam);
      echoParam = null;
    }
    jsonRequest.setResponseClass(PortfolioGetUserWatchListResponse.class);
    jsonRequest.setService("Portfolio", "getWatchlistData");
    ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
  }
  
  @Deprecated
  public static void sendRequest(String clientCode, String strToken, Context ctx, ServiceResponseListener listener)
  {
    PortfolioGetUserWatchListRequest request = new PortfolioGetUserWatchListRequest();
    request.setClientCode(clientCode);
    request.setStrToken(strToken);
    try
    {
      sendRequest(request.toJSONObject(), ctx, listener);
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
  }
}



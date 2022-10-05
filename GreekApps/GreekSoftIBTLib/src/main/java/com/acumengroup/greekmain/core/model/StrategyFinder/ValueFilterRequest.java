package com.acumengroup.greekmain.core.model.StrategyFinder;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.PanValidation.PanRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ValueFilterRequest implements GreekRequestModel, GreekResponseModel {


    String iStrikeFrom;
    String iStrikeTo;
    String dITMDelta;
    String dMaxAskBidDiff;
    String lMinVolume;
    String lMinOpenInterest;
    String iCallPutSelection;
    String iHideMaxGainLoss;
    String iRefreshTimer;
    String iAlertAddPosition;
    Boolean bIsLtpReferancePrice;
    Boolean bStkEquityFlag;
    String dSTKInterest;
    String dIDXInterest;


    public static final String SERVICE_GROUP = "filterStrategy";
    public static final String SERVICE_NAME = "valuefilter";
    private static JSONObject echoParam = null;


    public String getiStrikeFrom() {
        return iStrikeFrom;
    }

    public void setiStrikeFrom(String iStrikeFrom) {
        this.iStrikeFrom = iStrikeFrom;
    }

    public String getiStrikeTo() {
        return iStrikeTo;
    }

    public void setiStrikeTo(String iStrikeTo) {
        this.iStrikeTo = iStrikeTo;
    }

    public String getdITMDelta() {
        return dITMDelta;
    }

    public void setdITMDelta(String dITMDelta) {
        this.dITMDelta = dITMDelta;
    }

    public String getdMaxAskBidDiff() {
        return dMaxAskBidDiff;
    }

    public void setdMaxAskBidDiff(String dMaxAskBidDiff) {
        this.dMaxAskBidDiff = dMaxAskBidDiff;
    }

    public String getlMinVolume() {
        return lMinVolume;
    }

    public void setlMinVolume(String lMinVolume) {
        this.lMinVolume = lMinVolume;
    }

    public String getlMinOpenInterest() {
        return lMinOpenInterest;
    }

    public void setlMinOpenInterest(String lMinOpenInterest) {
        this.lMinOpenInterest = lMinOpenInterest;
    }

    public String getiCallPutSelection() {
        return iCallPutSelection;
    }

    public void setiCallPutSelection(String iCallPutSelection) {
        this.iCallPutSelection = iCallPutSelection;
    }

    public String getiHideMaxGainLoss() {
        return iHideMaxGainLoss;
    }

    public void setiHideMaxGainLoss(String iHideMaxGainLoss) {
        this.iHideMaxGainLoss = iHideMaxGainLoss;
    }

    public String getiRefreshTimer() {
        return iRefreshTimer;
    }

    public void setiRefreshTimer(String iRefreshTimer) {
        this.iRefreshTimer = iRefreshTimer;
    }

    public String getiAlertAddPosition() {
        return iAlertAddPosition;
    }

    public void setiAlertAddPosition(String iAlertAddPosition) {
        this.iAlertAddPosition = iAlertAddPosition;
    }

    public Boolean getbIsLtpReferancePrice() {
        return bIsLtpReferancePrice;
    }

    public void setbIsLtpReferancePrice(Boolean bIsLtpReferancePrice) {
        this.bIsLtpReferancePrice = bIsLtpReferancePrice;
    }

    public Boolean getbStkEquityFlag() {
        return bStkEquityFlag;
    }

    public void setbStkEquityFlag(Boolean bStkEquityFlag) {
        this.bStkEquityFlag = bStkEquityFlag;
    }

    public String getdSTKInterest() {
        return dSTKInterest;
    }

    public void setdSTKInterest(String dSTKInterest) {
        this.dSTKInterest = dSTKInterest;
    }

    public String getdIDXInterest() {
        return dIDXInterest;
    }

    public void setdIDXInterest(String dIDXInterest) {
        this.dIDXInterest = dIDXInterest;
    }


     /*  int		iStrikeFrom;
    int		iStrikeTo;
    double	dITMDelta;
    double	dMaxAskBidDiff;
    longtype	lMinVolume;
    longtype	lMinOpenInterest;
    short	iCallPutSelection;
    short	iHideMaxGainLoss;
    int		iRefreshTimer;
    short	iAlertAddPosition;
    bool    bIsLtpReferancePrice;
    bool    bStkEquityFlag;
    double  dSTKInterest;
    double  dIDXInterest;*/


    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("iStrikeFrom", this.iStrikeFrom);
        jo.put("iStrikeTo", this.iStrikeTo);
        jo.put("dITMDelta",this.dITMDelta);
        jo.put("dMaxAskBidDiff", this.dMaxAskBidDiff);
        jo.put("lMinVolume",this.lMinVolume);
        jo.put("lMinOpenInterest", this.lMinOpenInterest);
        jo.put("iCallPutSelection", this.iCallPutSelection);
        jo.put("iHideMaxGainLoss", this.iHideMaxGainLoss);
        jo.put("iRefreshTimer", this.iRefreshTimer);
        jo.put("iAlertAddPosition", this.iAlertAddPosition);
        jo.put("bIsLtpReferancePrice", this.bIsLtpReferancePrice);
        jo.put("bStkEquityFlag", this.bStkEquityFlag);
        jo.put("dSTKInterest", this.dSTKInterest);
        jo.put("dIDXInterest", this.dIDXInterest);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.iStrikeFrom = jo.getString("iStrikeFrom");
        this.iStrikeTo = jo.optString("iStrikeTo");
        this.dITMDelta = jo.optString("dITMDelta");
        this.dMaxAskBidDiff= jo.optString("dMaxAskBidDiff");
        this.lMinVolume = jo.optString("lMinVolume");
        this.lMinOpenInterest = jo.optString("lMinOpenInterest");
        this.iCallPutSelection=jo.optString("iCallPutSelection");
        this.iHideMaxGainLoss=jo.optString("iHideMaxGainLoss");
        this.iRefreshTimer=jo.optString("iRefreshTimer");
        this.iAlertAddPosition=jo.optString("iAlertAddPosition");
        this.bIsLtpReferancePrice=jo.optBoolean("bIsLtpReferancePrice");
        this.bStkEquityFlag=jo.optBoolean("bStkEquityFlag");
        this.dSTKInterest=jo.optString("dSTKInterest");
        this.dIDXInterest=jo.optString("dIDXInterest");
        return  this;
    }

    @Deprecated
    public static void sendRequest(String iStrikeFrom, String iStrikeTo, String dITMDelta, String dMaxAskBidDiff, String lMinVolume, String lMinOpenInterest, String iCallPutSelection, String iHideMaxGainLoss, String iRefreshTimer, String iAlertAddPosition, Boolean bIsLtpReferancePrice, Boolean bStkEquityFlag, String dSTKInterest,String dIDXInterest,Context ctx, ServiceResponseListener listener) {
        ValueFilterRequest request = new ValueFilterRequest();

        request.setiStrikeFrom(iStrikeFrom);
        request.setiStrikeTo(iStrikeTo);
        request.setdITMDelta(dITMDelta);
        request.setdMaxAskBidDiff(dMaxAskBidDiff);
        request.setlMinVolume(lMinVolume);
        request.setlMinOpenInterest(lMinOpenInterest);
        request.setiCallPutSelection(iCallPutSelection);
        request.setiHideMaxGainLoss(iHideMaxGainLoss);
        request.setiRefreshTimer(iRefreshTimer);
        request.setiAlertAddPosition(iAlertAddPosition);
        request.setbIsLtpReferancePrice(bIsLtpReferancePrice);
        request.setbStkEquityFlag(bStkEquityFlag);
        request.setdSTKInterest(dSTKInterest);
        request.setdIDXInterest(dIDXInterest);


        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("Bankdetails", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(StrategyFinderResponse.class);
        jsonRequest.setService("filterStrategy", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
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

    public static void sendRequest(PanRequest request, Context ctx, ServiceResponseListener listener)
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
}

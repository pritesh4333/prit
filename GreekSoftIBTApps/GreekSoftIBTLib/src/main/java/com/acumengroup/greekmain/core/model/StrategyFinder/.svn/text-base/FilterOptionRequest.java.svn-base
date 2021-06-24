package com.acumengroup.greekmain.core.model.StrategyFinder;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.PanValidation.PanRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterOptionRequest implements GreekRequestModel, GreekResponseModel {

//change done to test

    String gscid;

    //Value filter
    String iStrikeFrom;
    String iStrikeTo;
    String dITMDelta;
    String dMaxAskBidDiff;
    String lMinVolume;
    String lMinOpenInterest;
    private List<FilterCallPutOptionList> iCallPutSelection = new ArrayList();
    String iHideMaxGainLoss;
    String iRefreshTimer;
    String iAlertAddPosition;
    Boolean bIsLtpReferancePrice;
    Boolean bStkEquityFlag;
    String dSTKInterest;
    String dIDXInterest;

    //Strategy filter
    Boolean bIsCovered;
    String cSymbol;
    String iCallPut;
    String iTokenCount;
    String iVolatality;
    String iExchange;
    private List<StrategyIDList> iStrategyType = new ArrayList();
    String iStrikeInterval;
    String iStrikeCombinations;
    private List<FilterExpiryList> lExpDate = new ArrayList();
    String dLowerStrike;
    String dUpperStrike;
    public static final String SERVICE_GROUP = "filterStrategy";
    public static final String SERVICE_NAME = "strategyfinder_Filter";
    private static JSONObject echoParam = null;


    public String getGscid() {
        return gscid;
    }

    public void setGscid(String gscid) {
        this.gscid = gscid;
    }

    public List<FilterCallPutOptionList> getiCallPutSelection() {
        return iCallPutSelection;
    }

    public void setiCallPutSelection(List<FilterCallPutOptionList> iCallPutSelection) {
        this.iCallPutSelection = iCallPutSelection;
    }

    public List<FilterExpiryList> getlExpDate() {
        return lExpDate;
    }

    public void setlExpDate(List<FilterExpiryList> lExpDate) {
        this.lExpDate = lExpDate;
    }

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




    public Boolean getbIsCovered() {
        return bIsCovered;
    }

    public void setbIsCovered(Boolean bIsCovered) {
        this.bIsCovered = bIsCovered;
    }

    public String getcSymbol() {
        return cSymbol;
    }

    public void setcSymbol(String cSymbol) {
        this.cSymbol = cSymbol;
    }

    public String getiCallPut() {
        return iCallPut;
    }

    public void setiCallPut(String iCallPut) {
        this.iCallPut = iCallPut;
    }

    public String getiTokenCount() {
        return iTokenCount;
    }

    public void setiTokenCount(String iTokenCount) {
        this.iTokenCount = iTokenCount;
    }

    public String getiVolatality() {
        return iVolatality;
    }

    public void setiVolatality(String iVolatality) {
        this.iVolatality = iVolatality;
    }

    public String getiExchange() {
        return iExchange;
    }

    public void setiExchange(String iExchange) {
        this.iExchange = iExchange;
    }

    public List<StrategyIDList> getiStrategyType() {
        return iStrategyType;
    }

    public void setiStrategyType( List<StrategyIDList> iStrategyType) {
        this.iStrategyType = iStrategyType;
    }

    public String getiStrikeInterval() {
        return iStrikeInterval;
    }

    public void setiStrikeInterval(String iStrikeInterval) {
        this.iStrikeInterval = iStrikeInterval;
    }

    public String getiStrikeCombinations() {
        return iStrikeCombinations;
    }

    public void setiStrikeCombinations(String iStrikeCombinations) {
        this.iStrikeCombinations = iStrikeCombinations;
    }


    public String getdLowerStrike() {
        return dLowerStrike;
    }

    public void setdLowerStrike(String dLowerStrike) {
        this.dLowerStrike = dLowerStrike;
    }

    public String getdUpperStrike() {
        return dUpperStrike;
    }

    public void setdUpperStrike(String dUpperStrike) {
        this.dUpperStrike = dUpperStrike;
    }
    /*Boolean bIsCovered;
        String cSymbol;
        String iCallPut;
        String iTokenCount;
        String iVolatality;
        String iExchange;
        String iStrategyType;
        String iStrikeInterval;
        String iStrikeCombinations;
        String lExpDate;
        String dLowerStrike;
        String dUpperStrike;*/
    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        JSONArray ja2 = new JSONArray();
        JSONArray ja3 = new JSONArray();

        jo.put("gscid", this.gscid);
        jo.put("iStrikeFrom", this.iStrikeFrom);
        jo.put("iStrikeTo", this.iStrikeTo);
        jo.put("dITMDelta",this.dITMDelta);
        jo.put("dMaxAskBidDiff", this.dMaxAskBidDiff);
        jo.put("lMinVolume",this.lMinVolume);
        jo.put("lMinOpenInterest", this.lMinOpenInterest);
        //jo.put("iCallPutSelection", this.iCallPutSelection);
        jo.put("iHideMaxGainLoss", this.iHideMaxGainLoss);
        jo.put("iRefreshTimer", this.iRefreshTimer);
        jo.put("iAlertAddPosition", this.iAlertAddPosition);
        jo.put("bIsLtpReferancePrice", this.bIsLtpReferancePrice);
        jo.put("bStkEquityFlag", this.bStkEquityFlag);
        jo.put("dSTKInterest", this.dSTKInterest);
        jo.put("dIDXInterest", this.dIDXInterest);



        jo.put("bIsCovered", this.bIsCovered);
        jo.put("cSymbol", this.cSymbol);
        jo.put("iCallPut",this.iCallPut);
        jo.put("iTokenCount", this.iTokenCount);
        jo.put("iVolatality",this.iVolatality);
        jo.put("iExchange", this.iExchange);
        //  jo.put("iStrategyType", this.iStrategyType);
        jo.put("iStrikeInterval", this.iStrikeInterval);
        jo.put("iStrikeCombinations", this.iStrikeCombinations);
        //jo.put("lExpDate", this.lExpDate);
        jo.put("dLowerStrike", this.dLowerStrike);
        jo.put("dUpperStrike", this.dUpperStrike);

        Iterator iterator = this.iStrategyType.iterator();
        while (iterator.hasNext())
        {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel)o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("iStrategyType", ja1);

        Iterator iteratorExp = this.lExpDate.iterator();
        while (iteratorExp.hasNext())
        {
            Object o = iteratorExp.next();
            if ((o instanceof GreekRequestModel)) {
                ja2.put(((GreekRequestModel)o).toJSONObject());
            } else {
                ja2.put(o);
            }
        }
        jo.put("lExpDate", ja2);

        Iterator iteratorOption = this.iCallPutSelection.iterator();
        while (iteratorOption.hasNext())
        {
            Object o = iteratorOption.next();
            if ((o instanceof GreekRequestModel)) {
                ja3.put(((GreekRequestModel)o).toJSONObject());
            } else {
                ja3.put(o);
            }
        }
        jo.put("iCallPutSelection", ja3);
        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {


        this.gscid = jo.getString("gscid");
        this.iStrikeFrom = jo.getString("iStrikeFrom");
        this.iStrikeTo = jo.optString("iStrikeTo");
        this.dITMDelta = jo.optString("dITMDelta");
        this.dMaxAskBidDiff= jo.optString("dMaxAskBidDiff");
        this.lMinVolume = jo.optString("lMinVolume");
        this.lMinOpenInterest = jo.optString("lMinOpenInterest");
        this.iHideMaxGainLoss=jo.optString("iHideMaxGainLoss");
        this.iRefreshTimer=jo.optString("iRefreshTimer");
        this.iAlertAddPosition=jo.optString("iAlertAddPosition");
        this.bIsLtpReferancePrice=jo.optBoolean("bIsLtpReferancePrice");
        this.bStkEquityFlag=jo.optBoolean("bStkEquityFlag");
        this.dSTKInterest=jo.optString("dSTKInterest");
        this.dIDXInterest=jo.optString("dIDXInterest");




        this.bIsCovered = jo.optBoolean("bIsCovered");
        this.cSymbol = jo.optString("cSymbol");
        this.iCallPut = jo.optString("iCallPut");
        this.iTokenCount= jo.optString("iTokenCount");
        this.iVolatality = jo.optString("iVolatality");
        this.iExchange = jo.optString("iExchange");
        // this.iStrategyType=jo.optJSONArray("iStrategyType");
        this.iStrikeInterval=jo.optString("iStrikeInterval");
        this.iStrikeCombinations=jo.optString("iStrikeCombinations");
        this.dLowerStrike=jo.optString("dLowerStrike");
        this.dUpperStrike=jo.optString("dUpperStrike");
        if (jo.has("symbolList"))
        {
            JSONArray ja1 = jo.getJSONArray("iStrategyType");
            this.iStrategyType = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++)
            {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject))
                {
                    StrategyIDList data = new StrategyIDList();
                    data.fromJSON((JSONObject)o);
                    this.iStrategyType.add(data);
                }
                else
                {
                    this.iStrategyType.add((StrategyIDList)o);
                }
            }
        }

        if (jo.has("lExpDate"))
        {
            JSONArray ja1 = jo.getJSONArray("lExpDate");
            this.lExpDate = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++)
            {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject))
                {
                    FilterExpiryList data = new FilterExpiryList();
                    data.fromJSON((JSONObject)o);
                    this.lExpDate.add(data);
                }
                else
                {
                    this.lExpDate.add((FilterExpiryList)o);
                }
            }
        }

        if (jo.has("iCallPutSelection"))
        {
            JSONArray ja1 = jo.getJSONArray("iCallPutSelection");
            this.iCallPutSelection = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++)
            {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject))
                {
                    FilterCallPutOptionList data = new FilterCallPutOptionList();
                    data.fromJSON((JSONObject)o);
                    this.iCallPutSelection.add(data);
                }
                else
                {
                    this.iCallPutSelection.add((FilterCallPutOptionList)o);
                }
            }
        }



        return this;
    }



    @Deprecated
    public static void sendRequest(String gscid,String iStrikeFrom, String iStrikeTo, String dITMDelta, String dMaxAskBidDiff, String lMinVolume, String lMinOpenInterest, List iCallPutSelection, String iHideMaxGainLoss, String iRefreshTimer, String iAlertAddPosition, Boolean bIsLtpReferancePrice, Boolean bStkEquityFlag, String dSTKInterest, String dIDXInterest, Boolean bIsCovered, String cSymbol, String iCallPut, String iTokenCount, String iVolatality, String iExchange, List iStrategyType, String iStrikeCombinations, String iStrikeInterval, List lExpDate, String dLowerStrike, String dUpperStrike, Context ctx, ServiceResponseListener listener) {
        FilterOptionRequest request = new FilterOptionRequest();

       request.setGscid(gscid);
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

        //end valuefilter

        request.setbIsCovered(bIsCovered);
        request.setcSymbol(cSymbol);
        request.setiCallPut(iCallPut);
        request.setiTokenCount(iTokenCount);
        request.setiVolatality(iVolatality);
        request.setiExchange(iExchange);
        request.setiStrategyType(iStrategyType);
        request.setiStrikeInterval(iStrikeInterval);
        request.setiStrikeCombinations(iStrikeCombinations);
        request.setlExpDate(lExpDate);
        request.setdLowerStrike(dLowerStrike);
        request.setdUpperStrike(dUpperStrike);


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


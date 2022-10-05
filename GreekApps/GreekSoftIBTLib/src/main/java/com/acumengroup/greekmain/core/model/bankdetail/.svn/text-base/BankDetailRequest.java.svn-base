package com.acumengroup.greekmain.core.model.bankdetail;

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

public class BankDetailRequest implements GreekRequestModel, GreekResponseModel {

    private static JSONObject echoParam = null;

    public static final String SERVICE_GROUP = "addbankdetails";
    public static final String SERVICE_NAME = "getConnectToMF";

    private String ifsc;
    private String bankName;
    private String accNo;
    private String branch;
    String flagVal;
    String accType;
    private String nomName;
    private String nomRel;
    private String panNo;

    public String getFlagVal() {
        return flagVal;
    }

    public void setFlagVal(String flagVal) {
        this.flagVal = flagVal;
    }


    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }


    public String getNomName() {
        return nomName;
    }

    public void setNomName(String nomName) {
        this.nomName = nomName;
    }

    public String getNomRel() {
        return nomRel;
    }

    public void setNomRel(String nomRel) {
        this.nomRel = nomRel;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("ifsc", this.ifsc);
        jo.put("bankName", this.bankName);
        jo.put("branch", this.branch);
        jo.put("accNo", this.accNo);
        jo.put("panNo", this.panNo);
        jo.put("nomName", this.nomName);
        jo.put("nomRel", this.nomRel);
        jo.put("accType", this.accType);
        jo.put("flagVal", this.flagVal);


        return jo;

    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.ifsc = jo.optString("ifsc");
        this.bankName = jo.optString("bankName");
        this.accNo = jo.optString("accNo");
        this.branch = jo.optString("branch");
        this.nomName = jo.optString("nomName");
        this.nomRel = jo.optString("nomRel");
        this.panNo = jo.optString("panNo");
        this.accType = jo.optString("accType");
        this.flagVal = jo.optString("flagVal");

        return this;
    }


    @Deprecated
    public static void sendRequest(String ifcscode, String bankname, String accontnumber, String bankName, String nomineeName, String nomineeRelation, String panNo, String accType, String flagVal, Context ctx, ServiceResponseListener listener) {
        BankDetailRequest request = new BankDetailRequest();

        request.setIfsc(ifcscode);
        request.setBankName(bankname);
        request.setAccNo(accontnumber);
        request.setBankName(bankName);
        request.setNomName(nomineeName);
        request.setNomRel(nomineeRelation);
        request.setPanNo(panNo);
        request.setFlagVal(flagVal);
        request.setAccType(accType);
        request.setBranch("Mumbai");


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
        jsonRequest.setResponseClass(BankDetailResponse.class);
        jsonRequest.setService("addbankdetails", SERVICE_NAME);

        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
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

    public static void sendRequest(PanRequest request, Context ctx, ServiceResponseListener listener) {
        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

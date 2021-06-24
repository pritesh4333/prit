package com.acumengroup.greekmain.core.model.PersonaklDetail;

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

public class PersonalDetailRequest implements GreekRequestModel, GreekResponseModel {


    private static JSONObject echoParam = null;

    public static final String SERVICE_GROUP = "addpersonaldetails";
    public static final String SERVICE_NAME = "getConnectToMF";

    private String cClientEmail;
    private String reqType;
    private String userType;
    private String clientCode;
    private String cFirstApplicantName;
    private String cOccupation;
    private String cFirstApplicantDOB;
    private String cFirstAppGender;
    private String cFirstApplicantPAN;
    private String income;
    private String cAdd1;
    private String cAdd2;
    private String cAdd3;
    private String cCity;
    private String cClientState;
    private String cPINCode;
    private String cCountry;
    private String cResiPhone;
    private String cMobile;

    private String cForAdd1;
    private String cForAdd2;
    private String cForAdd3;
    private String cForCity;
    private String cForState;
    private String cForPinCode;
    private String cForCountry;
    private String cForResiPhone;
    private String cMaritalStatus;
    private String cNationality;
    private String cAadharNo;
    private String cClientGaurdian;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getcClientGaurdian() {
        return cClientGaurdian;
    }

    public void setcClientGaurdian(String cClientGaurdian) {
        this.cClientGaurdian = cClientGaurdian;
    }

    public String getcClientEmail() {
        return cClientEmail;
    }


    public String getcNationality() {
        return cNationality;
    }

    public void setcNationality(String cNationality) {
        this.cNationality = cNationality;
    }

    public String getcAadharNo() {
        return cAadharNo;
    }

    public void setcAadharNo(String cAadharNo) {
        this.cAadharNo = cAadharNo;
    }

    public void setcClientEmail(String cClientEmail) {
        this.cClientEmail = cClientEmail;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getcOccupation() {
        return cOccupation;
    }

    public void setcOccupation(String cOccupation) {
        this.cOccupation = cOccupation;
    }

    public String getcMaritalStatus() {
        return cMaritalStatus;
    }

    public void setcMaritalStatus(String cMaritalStatus) {
        this.cMaritalStatus = cMaritalStatus;
    }

    public String getcFirstApplicantName() {
        return cFirstApplicantName;
    }

    public void setcFirstApplicantName(String cFirstApplicantName) {
        this.cFirstApplicantName = cFirstApplicantName;
    }

    public String getcFirstApplicantDOB() {
        return cFirstApplicantDOB;
    }

    public void setcFirstApplicantDOB(String cFirstApplicantDOB) {
        this.cFirstApplicantDOB = cFirstApplicantDOB;
    }

    public String getcFirstAppGender() {
        return cFirstAppGender;
    }

    public void setcFirstAppGender(String cFirstAppGender) {
        this.cFirstAppGender = cFirstAppGender;
    }

    public String getcFirstApplicantPAN() {
        return cFirstApplicantPAN;
    }

    public void setcFirstApplicantPAN(String cFirstApplicantPAN) {
        this.cFirstApplicantPAN = cFirstApplicantPAN;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getcAdd1() {
        return cAdd1;
    }

    public void setcAdd1(String cAdd1) {
        this.cAdd1 = cAdd1;
    }

    public String getcAdd2() {
        return cAdd2;
    }

    public void setcAdd2(String cAdd2) {
        this.cAdd2 = cAdd2;
    }

    public String getcAdd3() {
        return cAdd3;
    }

    public void setcAdd3(String cAdd3) {
        this.cAdd3 = cAdd3;
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public String getcClientState() {
        return cClientState;
    }

    public void setcClientState(String cClientState) {
        this.cClientState = cClientState;
    }

    public String getcPINCode() {
        return cPINCode;
    }

    public void setcPINCode(String cPINCode) {
        this.cPINCode = cPINCode;
    }

    public String getcCountry() {
        return cCountry;
    }

    public void setcCountry(String cCountry) {
        this.cCountry = cCountry;
    }

    public String getcResiPhone() {
        return cResiPhone;
    }

    public void setcResiPhone(String cResiPhone) {
        this.cResiPhone = cResiPhone;
    }

    public String getcMobile() {
        return cMobile;
    }

    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }

    public String getcForAdd1() {
        return cForAdd1;
    }

    public void setcForAdd1(String cForAdd1) {
        this.cForAdd1 = cForAdd1;
    }

    public String getcForAdd2() {
        return cForAdd2;
    }

    public void setcForAdd2(String cForAdd2) {
        this.cForAdd2 = cForAdd2;
    }

    public String getcForAdd3() {
        return cForAdd3;
    }

    public void setcForAdd3(String cForAdd3) {
        this.cForAdd3 = cForAdd3;
    }

    public String getcForCity() {
        return cForCity;
    }

    public void setcForCity(String cForCity) {
        this.cForCity = cForCity;
    }

    public String getcForState() {
        return cForState;
    }

    public void setcForState(String cForState) {
        this.cForState = cForState;
    }

    public String getcForPinCode() {
        return cForPinCode;
    }

    public void setcForPinCode(String cForPinCode) {
        this.cForPinCode = cForPinCode;
    }

    public String getcForCountry() {
        return cForCountry;
    }

    public void setcForCountry(String cForCountry) {
        this.cForCountry = cForCountry;
    }

    public String getcForResiPhone() {
        return cForResiPhone;
    }

    public void setcForResiPhone(String cForResiPhone) {
        this.cForResiPhone = cForResiPhone;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("cFirstApplicantName", this.cFirstApplicantName);
        jo.put("cFirstApplicantDOB", this.cFirstApplicantDOB);
        jo.put("cMobile", this.cMobile);
        jo.put("cMaritalStatus", this.cMaritalStatus);
        jo.put("cFirstAppGender", this.cFirstAppGender);
        jo.put("cFirstApplicantPAN", this.cFirstApplicantPAN);
        jo.put("cAdd1", this.cAdd1);
        jo.put("cAdd2", this.cAdd2);
        jo.put("cAdd3", this.cAdd3);
        jo.put("cCity", this.cCity);
        jo.put("cClientState", this.cClientState);
        jo.put("cPINCode", this.cPINCode);
        jo.put("cCountry", this.cCountry);
        jo.put("cResiPhone", this.cResiPhone);
        jo.put("cForAdd1", this.cForAdd1);
        jo.put("cForAdd2", this.cForAdd2);
        jo.put("cForAdd3", this.cForAdd3);
        jo.put("cForCity", this.cForCity);
        jo.put("cForState", this.cForState);
        jo.put("cForResiPhone", this.cForResiPhone);
        jo.put("cForCountry", this.cForCountry);
        jo.put("cForPinCode", this.cForPinCode);
        jo.put("cOccupation", this.cOccupation);
        jo.put("clientCode", this.clientCode);
        jo.put("cClientEmail", this.cClientEmail);
        jo.put("cNationality", this.cNationality);
        jo.put("cAadharNo", this.cAadharNo);
        jo.put("cClientGuardian", this.cClientGaurdian);
        jo.put("income", this.income);
        jo.put("reqType", this.reqType);
        jo.put("userType", this.userType);


        return jo;
    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {

        this.cFirstApplicantName = jo.optString("cFirstApplicantName");
        this.cFirstApplicantDOB = jo.optString("cFirstApplicantDOB");
        this.cMobile = jo.optString("cMobile");
        this.cMaritalStatus = jo.optString("cMaritalStatus");
        this.cFirstAppGender = jo.optString("cFirstAppGender");
        this.cFirstApplicantPAN = jo.optString("cFirstApplicantPAN");
        this.cAdd1 = jo.optString("cAdd1");
        this.cAdd2 = jo.optString("cAdd2");
        this.cAdd3 = jo.optString("cAdd3");
        this.cCity = jo.optString("cCity");
        this.cClientState = jo.optString("cClientState");
        this.cPINCode = jo.optString("cPINCode");
        this.cCountry = jo.optString("cCountry");
        this.cResiPhone = jo.optString("cResiPhone");
        this.cForAdd1 = jo.optString("cForAdd1");
        this.cForAdd2 = jo.optString("cForAdd2");
        this.cForAdd3 = jo.optString("cForAdd3");
        this.cForCity = jo.optString("cForCity");
        this.cForState = jo.optString("cForState");
        this.cForPinCode = jo.optString("cForPinCode");
        this.cForCountry = jo.optString("cForCountry");
        this.cForResiPhone = jo.optString("cForResiPhone");
        this.cOccupation = jo.optString("cOccupation");
        this.clientCode = jo.optString("clientCode");
        this.cClientEmail = jo.optString("cClientEmail");
        this.cNationality = jo.optString("cNationality");
        this.cAadharNo = jo.optString("cAadharNo");
        this.cClientGaurdian = jo.optString("cClientGuardian");
        this.income = jo.optString("income");
        this.reqType = jo.optString("reqType");
        this.userType = jo.optString("userType");
        return this;
    }


    @Deprecated
    public static void sendRequest(String reqType, String userType, String name, String dob, String gaurdian, String mobile, String marital_status, String gender, String pan, String income, String caddr1, String caddr2, String caddr3, String ccity, String cclientstate, String cpin, String ccountry, String cresiphone, String cforaddr1, String cforaddr2, String cforaddr3, String cforcity, String cforclientstate, String cforpin, String cforcountry, String cforresiphone, String occupation, String clientcode, String email, String nationality, String aadharnumber, Context ctx, ServiceResponseListener listener) {
        Log.e("getConnectTo---- 2", pan);
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setcFirstApplicantName(name);
        request.setcFirstApplicantDOB(dob);
        request.setcClientGaurdian(gaurdian);
        request.setcMobile(mobile);
        request.setcMaritalStatus(marital_status);
        request.setcFirstAppGender(gender);
        request.setcFirstApplicantPAN(pan);
        request.setIncome(income);
        request.setcAdd1(caddr1);
        request.setcAdd2(caddr2);
        request.setcAdd3(caddr3);
        request.setcCity(ccity);
        request.setcClientState(cclientstate);
        request.setcPINCode(cpin);
        request.setcCountry(ccountry);
        request.setcResiPhone(cresiphone);
        request.setcForAdd1(cforaddr1);
        request.setcForAdd2(cforaddr2);
        request.setcForAdd3(cforaddr3);
        request.setcForCity(cforcity);
        request.setcForState(cforclientstate);
        request.setcForPinCode(cforpin);
        request.setcForCountry(cforcountry);
        request.setcForResiPhone(cforresiphone);
        request.setcOccupation(occupation);
        request.setClientCode(clientcode);
        request.setcClientEmail(email);
        request.setcNationality(nationality);
        request.setcAadharNo(aadharnumber);
        request.setReqType(reqType);
        request.setUserType(userType);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        Log.e("ValidateGuest", "jsonRequest==>" + jsonRequest);

        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        //Log.d("rqt",request.toString());
        jsonRequest.setResponseClass(PersonalDetailResponse.class);
        jsonRequest.setService("addpersonaldetails", SERVICE_NAME);

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

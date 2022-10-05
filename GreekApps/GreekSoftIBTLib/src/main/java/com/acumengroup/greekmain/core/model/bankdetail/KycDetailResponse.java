package com.acumengroup.greekmain.core.model.bankdetail;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class KycDetailResponse implements GreekRequestModel, GreekResponseModel {


    private String ErrorCode;
    private String cOccupation;
    private String income;
    private String cFirstApplicantName;
    private String cSecondApplicantName;
    private String cThirdApplicantName;
    private String cFirstApplicantDOB;
    private String cFirstAppGender;
    private String cClientGuardian;
    private String cFirstApplicantPAN;
    private String cClientNominee;
    private String cClientNomineeRelation;
    private String cGuardianPAN;
    private String cAdd1;
    private String cAdd2;
    private String cAdd3;
    private String cCity;
    private String cClientState;
    private String cPINCode;
    private String cCountry;
    private String cResiPhone;
    private String cResiFax;
    private String cOfficePhone;
    private String cClientOfficeFax;
    private String cClientEmail;
    private String cForAdd1;
    private String cForAdd2;
    private String cForAdd3;
    private String cForCity;
    private String cForPinCode;
    private String cForState;
    private String cForCountry;
    private String cForResiPhone;
    private String cForResiFax;
    private String cForOffPhone;
    private String cForOffFax;
    private String cMobile;

    private String cNationality;
    private String cAadharNo;
    private String cMaritalStatus;
    private String cClientGaurdian;


    private String cAccType1;
    private String cAccNoNo1;
    private String cClientMICRNo1;
    private String cNEFTIFSCCode1;
    private String cBankName1;
    private String cBankBranch1;
    private String cDefaultBankFlag1;

    private String cAccType2;
    private String cAccNoNo2;
    private String cClientMICRNo2;
    private String cNEFTIFSCCode2;
    private String cBankName2;
    private String cBankBranch2;
    private String cDefaultBankFlag2;

    private String cAccType3;
    private String cAccNoNo3;
    private String cClientMICRNo3;
    private String cNEFTIFSCCode3;
    private String cBankName3;
    private String cBankBranch3;
    private String cDefaultBankFlag3;

    private String cAccType4;
    private String cAccNoNo4;
    private String cClientMICRNo4;
    private String cNEFTIFSCCode4;
    private String cBankName4;
    private String cBankBranch4;
    private String cDefaultBankFlag4;

    private String cAccType5;
    private String cAccNoNo5;
    private String cClientMICRNo5;
    private String cNEFTIFSCCode5;
    private String cBankName5;
    private String cBankBranch5;
    private String cDefaultBankFlag5;


    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
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

    public String getcMaritalStatus() {
        return cMaritalStatus;
    }

    public void setcMaritalStatus(String cMaritalStatus) {
        this.cMaritalStatus = cMaritalStatus;
    }

    public String getcClientGaurdian() {
        return cClientGaurdian;
    }

    public void setcClientGaurdian(String cClientGaurdian) {
        this.cClientGaurdian = cClientGaurdian;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }


    public String getcOccupation() {
        return cOccupation;
    }

    public void setcOccupation(String cOccupation) {
        this.cOccupation = cOccupation;
    }

    public String getcFirstApplicantName() {
        return cFirstApplicantName;
    }

    public void setcFirstApplicantName(String cFirstApplicantName) {
        this.cFirstApplicantName = cFirstApplicantName;
    }

    public String getcSecondApplicantName() {
        return cSecondApplicantName;
    }

    public void setcSecondApplicantName(String cSecondApplicantName) {
        this.cSecondApplicantName = cSecondApplicantName;
    }

    public String getcThirdApplicantName() {
        return cThirdApplicantName;
    }

    public void setcThirdApplicantName(String cThirdApplicantName) {
        this.cThirdApplicantName = cThirdApplicantName;
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

    public String getcClientGuardian() {
        return cClientGuardian;
    }

    public void setcClientGuardian(String cClientGuardian) {
        this.cClientGuardian = cClientGuardian;
    }

    public String getcFirstApplicantPAN() {
        return cFirstApplicantPAN;
    }

    public void setcFirstApplicantPAN(String cFirstApplicantPAN) {
        this.cFirstApplicantPAN = cFirstApplicantPAN;
    }

    public String getcClientNominee() {
        return cClientNominee;
    }

    public void setcClientNominee(String cClientNominee) {
        this.cClientNominee = cClientNominee;
    }

    public String getcClientNomineeRelation() {
        return cClientNomineeRelation;
    }

    public void setcClientNomineeRelation(String cClientNomineeRelation) {
        this.cClientNomineeRelation = cClientNomineeRelation;
    }

    public String getcGuardianPAN() {
        return cGuardianPAN;
    }

    public void setcGuardianPAN(String cGuardianPAN) {
        this.cGuardianPAN = cGuardianPAN;
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

    public String getcResiFax() {
        return cResiFax;
    }

    public void setcResiFax(String cResiFax) {
        this.cResiFax = cResiFax;
    }

    public String getcOfficePhone() {
        return cOfficePhone;
    }

    public void setcOfficePhone(String cOfficePhone) {
        this.cOfficePhone = cOfficePhone;
    }

    public String getcClientOfficeFax() {
        return cClientOfficeFax;
    }

    public void setcClientOfficeFax(String cClientOfficeFax) {
        this.cClientOfficeFax = cClientOfficeFax;
    }

    public String getcClientEmail() {
        return cClientEmail;
    }

    public void setcClientEmail(String cClientEmail) {
        this.cClientEmail = cClientEmail;
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

    public String getcForPinCode() {
        return cForPinCode;
    }

    public void setcForPinCode(String cForPinCode) {
        this.cForPinCode = cForPinCode;
    }

    public String getcForState() {
        return cForState;
    }

    public void setcForState(String cForState) {
        this.cForState = cForState;
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

    public String getcForResiFax() {
        return cForResiFax;
    }

    public void setcForResiFax(String cForResiFax) {
        this.cForResiFax = cForResiFax;
    }

    public String getcForOffPhone() {
        return cForOffPhone;
    }

    public void setcForOffPhone(String cForOffPhone) {
        this.cForOffPhone = cForOffPhone;
    }

    public String getcForOffFax() {
        return cForOffFax;
    }

    public void setcForOffFax(String cForOffFax) {
        this.cForOffFax = cForOffFax;
    }

    public String getcMobile() {
        return cMobile;
    }

    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }

    public String getcAccType1() {
        return cAccType1;
    }

    public void setcAccType1(String cAccType1) {
        this.cAccType1 = cAccType1;
    }

    public String getcAccNoNo1() {
        return cAccNoNo1;
    }

    public void setcAccNoNo1(String cAccNoNo1) {
        this.cAccNoNo1 = cAccNoNo1;
    }

    public String getcClientMICRNo1() {
        return cClientMICRNo1;
    }

    public void setcClientMICRNo1(String cClientMICRNo1) {
        this.cClientMICRNo1 = cClientMICRNo1;
    }

    public String getcNEFTIFSCCode1() {
        return cNEFTIFSCCode1;
    }

    public void setcNEFTIFSCCode1(String cNEFTIFSCCode1) {
        this.cNEFTIFSCCode1 = cNEFTIFSCCode1;
    }

    public String getcBankName1() {
        return cBankName1;
    }

    public void setcBankName1(String cBankName1) {
        this.cBankName1 = cBankName1;
    }

    public String getcBankBranch1() {
        return cBankBranch1;
    }

    public void setcBankBranch1(String cBankBranch1) {
        this.cBankBranch1 = cBankBranch1;
    }

    public String getcDefaultBankFlag1() {
        return cDefaultBankFlag1;
    }

    public void setcDefaultBankFlag1(String cDefaultBankFlag1) {
        this.cDefaultBankFlag1 = cDefaultBankFlag1;
    }

    public String getcAccType2() {
        return cAccType2;
    }

    public void setcAccType2(String cAccType2) {
        this.cAccType2 = cAccType2;
    }

    public String getcAccNoNo2() {
        return cAccNoNo2;
    }

    public void setcAccNoNo2(String cAccNoNo2) {
        this.cAccNoNo2 = cAccNoNo2;
    }

    public String getcClientMICRNo2() {
        return cClientMICRNo2;
    }

    public void setcClientMICRNo2(String cClientMICRNo2) {
        this.cClientMICRNo2 = cClientMICRNo2;
    }

    public String getcNEFTIFSCCode2() {
        return cNEFTIFSCCode2;
    }

    public void setcNEFTIFSCCode2(String cNEFTIFSCCode2) {
        this.cNEFTIFSCCode2 = cNEFTIFSCCode2;
    }

    public String getcBankName2() {
        return cBankName2;
    }

    public void setcBankName2(String cBankName2) {
        this.cBankName2 = cBankName2;
    }

    public String getcBankBranch2() {
        return cBankBranch2;
    }

    public void setcBankBranch2(String cBankBranch2) {
        this.cBankBranch2 = cBankBranch2;
    }

    public String getcDefaultBankFlag2() {
        return cDefaultBankFlag2;
    }

    public void setcDefaultBankFlag2(String cDefaultBankFlag2) {
        this.cDefaultBankFlag2 = cDefaultBankFlag2;
    }

    public String getcAccType3() {
        return cAccType3;
    }

    public void setcAccType3(String cAccType3) {
        this.cAccType3 = cAccType3;
    }

    public String getcAccNoNo3() {
        return cAccNoNo3;
    }

    public void setcAccNoNo3(String cAccNoNo3) {
        this.cAccNoNo3 = cAccNoNo3;
    }

    public String getcClientMICRNo3() {
        return cClientMICRNo3;
    }

    public void setcClientMICRNo3(String cClientMICRNo3) {
        this.cClientMICRNo3 = cClientMICRNo3;
    }

    public String getcNEFTIFSCCode3() {
        return cNEFTIFSCCode3;
    }

    public void setcNEFTIFSCCode3(String cNEFTIFSCCode3) {
        this.cNEFTIFSCCode3 = cNEFTIFSCCode3;
    }

    public String getcBankName3() {
        return cBankName3;
    }

    public void setcBankName3(String cBankName3) {
        this.cBankName3 = cBankName3;
    }

    public String getcBankBranch3() {
        return cBankBranch3;
    }

    public void setcBankBranch3(String cBankBranch3) {
        this.cBankBranch3 = cBankBranch3;
    }

    public String getcDefaultBankFlag3() {
        return cDefaultBankFlag3;
    }

    public void setcDefaultBankFlag3(String cDefaultBankFlag3) {
        this.cDefaultBankFlag3 = cDefaultBankFlag3;
    }

    public String getcAccType4() {
        return cAccType4;
    }

    public void setcAccType4(String cAccType4) {
        this.cAccType4 = cAccType4;
    }

    public String getcAccNoNo4() {
        return cAccNoNo4;
    }

    public void setcAccNoNo4(String cAccNoNo4) {
        this.cAccNoNo4 = cAccNoNo4;
    }

    public String getcClientMICRNo4() {
        return cClientMICRNo4;
    }

    public void setcClientMICRNo4(String cClientMICRNo4) {
        this.cClientMICRNo4 = cClientMICRNo4;
    }

    public String getcNEFTIFSCCode4() {
        return cNEFTIFSCCode4;
    }

    public void setcNEFTIFSCCode4(String cNEFTIFSCCode4) {
        this.cNEFTIFSCCode4 = cNEFTIFSCCode4;
    }

    public String getcBankName4() {
        return cBankName4;
    }

    public void setcBankName4(String cBankName4) {
        this.cBankName4 = cBankName4;
    }

    public String getcBankBranch4() {
        return cBankBranch4;
    }

    public void setcBankBranch4(String cBankBranch4) {
        this.cBankBranch4 = cBankBranch4;
    }

    public String getcDefaultBankFlag4() {
        return cDefaultBankFlag4;
    }

    public void setcDefaultBankFlag4(String cDefaultBankFlag4) {
        this.cDefaultBankFlag4 = cDefaultBankFlag4;
    }

    public String getcAccType5() {
        return cAccType5;
    }

    public void setcAccType5(String cAccType5) {
        this.cAccType5 = cAccType5;
    }

    public String getcAccNoNo5() {
        return cAccNoNo5;
    }

    public void setcAccNoNo5(String cAccNoNo5) {
        this.cAccNoNo5 = cAccNoNo5;
    }

    public String getcClientMICRNo5() {
        return cClientMICRNo5;
    }

    public void setcClientMICRNo5(String cClientMICRNo5) {
        this.cClientMICRNo5 = cClientMICRNo5;
    }

    public String getcNEFTIFSCCode5() {
        return cNEFTIFSCCode5;
    }

    public void setcNEFTIFSCCode5(String cNEFTIFSCCode5) {
        this.cNEFTIFSCCode5 = cNEFTIFSCCode5;
    }

    public String getcBankName5() {
        return cBankName5;
    }

    public void setcBankName5(String cBankName5) {
        this.cBankName5 = cBankName5;
    }

    public String getcBankBranch5() {
        return cBankBranch5;
    }

    public void setcBankBranch5(String cBankBranch5) {
        this.cBankBranch5 = cBankBranch5;
    }

    public String getcDefaultBankFlag5() {
        return cDefaultBankFlag5;
    }

    public void setcDefaultBankFlag5(String cDefaultBankFlag5) {
        this.cDefaultBankFlag5 = cDefaultBankFlag5;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.ErrorCode);
        jo.put("cOccupation", this.cOccupation);
        jo.put("cFirstApplicantName", this.cFirstApplicantName);
        jo.put("cSecondApplicantName", this.cSecondApplicantName);
        jo.put("cThirdApplicantName", this.cThirdApplicantName);
        jo.put("cFirstApplicantDOB", this.cFirstApplicantDOB);
        jo.put("cFirstAppGender", this.cFirstAppGender);
        jo.put("cClientGuardian", this.cClientGuardian);
        jo.put("cFirstApplicantPAN", this.cFirstApplicantPAN);
        jo.put("cClientNominee", this.cClientNominee);
        jo.put("cClientNomineeRelation", this.cClientNomineeRelation);
        jo.put("cGuardianPAN", this.cGuardianPAN);
        jo.put("income", this.income);
        jo.put("cAdd1", this.cAdd1);
        jo.put("cAdd2", this.cAdd2);
        jo.put("cAdd3", this.cAdd3);
        jo.put("cCity", this.cCity);
        jo.put("cClientState", this.cClientState);
        jo.put("cPINCode", this.cPINCode);
        jo.put("cCountry", this.cCountry);
        jo.put("cResiPhone", this.cResiPhone);
        jo.put("cResiFax", this.cResiFax);
        jo.put("cOfficePhone", this.cOfficePhone);
        jo.put("cClientOfficeFax", this.cClientOfficeFax);
        jo.put("cClientEmail", this.cClientEmail);
        jo.put("cForAdd1", this.cForAdd1);
        jo.put("cForAdd2", this.cForAdd2);
        jo.put("cForAdd3", this.cForAdd3);
        jo.put("cForCity", this.cForCity);
        jo.put("cForPinCode", this.cForPinCode);
        jo.put("cForState", this.cForState);
        jo.put("cForCountry", this.cForCountry);
        jo.put("cForResiPhone", this.cForResiPhone);
        jo.put("cForResiFax", this.cForResiFax);
        jo.put("cForOffPhone", this.cForOffPhone);
        jo.put("cForOffFax", this.cForOffFax);
        jo.put("cMobile", this.cMobile);
        jo.put("cNationality", this.cNationality);
        jo.put("cAadharNo", this.cAadharNo);
        jo.put("cMaritalStatus", this.cMaritalStatus);
        jo.put("cClientGaurdian", this.cClientGaurdian);


        jo.put("cAccType1", this.cAccType1);
        jo.put("cAccNoNo1", this.cAccNoNo1);
        jo.put("cClientMICRNo1", this.cClientMICRNo1);
        jo.put("cNEFTIFSCCode1", this.cNEFTIFSCCode1);
        jo.put("cBankName1", this.cBankName1);
        jo.put("cBankBranch1", this.cBankBranch1);
        jo.put("cDefaultBankFlag1", this.cDefaultBankFlag1);

        jo.put("cAccType2", this.cAccType2);
        jo.put("cAccNoNo2", this.cAccNoNo2);
        jo.put("cClientMICRNo2", this.cClientMICRNo2);
        jo.put("cNEFTIFSCCode2", this.cNEFTIFSCCode2);
        jo.put("cBankName2", this.cBankName2);
        jo.put("cBankBranch2", this.cBankBranch2);
        jo.put("cDefaultBankFlag2", this.cDefaultBankFlag2);

        jo.put("cAccType4", this.cAccType4);
        jo.put("cAccNoNo4", this.cAccNoNo4);
        jo.put("cClientMICRNo4", this.cClientMICRNo4);
        jo.put("cNEFTIFSCCode4", this.cNEFTIFSCCode4);
        jo.put("cBankName4", this.cBankName4);
        jo.put("cBankBranch4", this.cBankBranch4);
        jo.put("cDefaultBankFlag4", this.cDefaultBankFlag4);

        jo.put("cAccType3", this.cAccType3);
        jo.put("cAccNoNo3", this.cAccNoNo3);
        jo.put("cClientMICRNo3", this.cClientMICRNo3);
        jo.put("cNEFTIFSCCode3", this.cNEFTIFSCCode3);
        jo.put("cBankName3", this.cBankName3);
        jo.put("cBankBranch3", this.cBankBranch3);
        jo.put("cDefaultBankFlag3", this.cDefaultBankFlag3);

        jo.put("cAccType5", this.cAccType5);
        jo.put("cAccNoNo5", this.cAccNoNo1);
        jo.put("cClientMICRNo5", this.cClientMICRNo5);
        jo.put("cNEFTIFSCCode5", this.cNEFTIFSCCode5);
        jo.put("cBankName5", this.cBankName5);
        jo.put("cBankBranch5", this.cBankBranch5);
        jo.put("cDefaultBankFlag5", this.cDefaultBankFlag5);
        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {


        this.ErrorCode = jo.optString("ErrorCode");
        this.cOccupation = jo.optString("cOccupation");
        this.cFirstApplicantName = jo.optString("cFirstApplicantName");
        this.cSecondApplicantName = jo.optString("cSecondApplicantName");
        this.cThirdApplicantName = jo.optString("cThirdApplicantName");
        this.cFirstApplicantDOB = jo.optString("cFirstApplicantDOB");
        this.cFirstAppGender = jo.optString("cFirstAppGender");
        this.cClientGuardian = jo.optString("cClientGuardian");
        this.cFirstApplicantPAN = jo.optString("cFirstApplicantPAN");
        this.cClientNominee = jo.optString("cClientNominee");
        this.cClientNomineeRelation = jo.optString("cClientNomineeRelation");
        this.cGuardianPAN = jo.optString("cGuardianPAN");
        this.income = jo.optString("income");
        this.cAdd1 = jo.optString("cAdd1");
        this.cAdd2 = jo.optString("cAdd2");
        this.cAdd3 = jo.optString("cAdd3");
        this.cCity = jo.optString("cCity");
        this.cClientState = jo.optString("cClientState");
        this.cPINCode = jo.optString("cPINCode");
        this.cCountry = jo.optString("cCountry");
        this.cResiPhone = jo.optString("cResiPhone");
        this.cResiFax = jo.optString("cResiFax");
        this.cOfficePhone = jo.optString("cOfficePhone");
        this.cClientOfficeFax = jo.optString("cClientOfficeFax");
        this.cClientEmail = jo.optString("cClientEmail");
        this.cForAdd1 = jo.optString("cForAdd1");
        this.cForAdd2 = jo.optString("cForAdd2");
        this.cForAdd3 = jo.optString("cForAdd3");
        this.cForCity = jo.optString("cForCity");
        this.cForPinCode = jo.optString("cForPinCode");
        this.cForState = jo.optString("cForState");
        this.cForCountry = jo.optString("cForCountry");
        this.cForResiPhone = jo.optString("cForResiPhone");
        this.cForResiFax = jo.optString("cForResiFax");
        this.cForOffPhone = jo.optString("cForOffPhone");
        this.cForOffFax = jo.optString("cForOffFax");
        this.cMobile = jo.optString("cMobile");
        this.cNationality = jo.optString("cNationality");
        this.cAadharNo = jo.optString("cAadharNo");
        this.cMaritalStatus = jo.optString("cMaritalStatus");
        this.cClientGaurdian = jo.optString("cClientGaurdian");


        this.cAccType1 = jo.optString("cAccType1");
        this.cAccNoNo1 = jo.optString("cAccNoNo1");
        this.cClientMICRNo1 = jo.optString("cClientMICRNo1");
        this.cNEFTIFSCCode1 = jo.optString("cNEFTIFSCCode1");
        this.cBankName1 = jo.optString("cBankName1");
        this.cBankBranch1 = jo.optString("cBankBranch1");
        this.cDefaultBankFlag1 = jo.optString("cDefaultBankFlag1");

        this.cAccType2 = jo.optString("cAccType2");
        this.cAccNoNo2 = jo.optString("cAccNoNo2");
        this.cClientMICRNo2 = jo.optString("cClientMICRNo2");
        this.cNEFTIFSCCode2 = jo.optString("cNEFTIFSCCode2");
        this.cBankName2 = jo.optString("cBankName2");
        this.cBankBranch2 = jo.optString("cBankBranch2");
        this.cDefaultBankFlag2 = jo.optString("cDefaultBankFlag2");

        this.cAccType3 = jo.optString("cAccType3");
        this.cAccNoNo3 = jo.optString("cAccNoNo3");
        this.cClientMICRNo3 = jo.optString("cClientMICRNo3");
        this.cNEFTIFSCCode3 = jo.optString("cNEFTIFSCCode3");
        this.cBankName3 = jo.optString("cBankName3");
        this.cBankBranch3 = jo.optString("cBankBranch3");
        this.cDefaultBankFlag3 = jo.optString("cDefaultBankFlag3");


        this.cAccType4 = jo.optString("cAccType4");
        this.cAccNoNo4 = jo.optString("cAccNoNo4");
        this.cClientMICRNo4 = jo.optString("cClientMICRNo4");
        this.cNEFTIFSCCode4 = jo.optString("cNEFTIFSCCode4");
        this.cBankName4 = jo.optString("cBankName4");
        this.cBankBranch4 = jo.optString("cBankBranch4");
        this.cDefaultBankFlag4 = jo.optString("cDefaultBankFlag4");

        this.cAccType5 = jo.optString("cAccType5");
        this.cAccNoNo5 = jo.optString("cAccNoNo5");
        this.cClientMICRNo5 = jo.optString("cClientMICRNo5");
        this.cNEFTIFSCCode5 = jo.optString("cNEFTIFSCCode5");
        this.cBankName5 = jo.optString("cBankName5");
        this.cBankBranch5 = jo.optString("cBankBranch5");
        this.cDefaultBankFlag5 = jo.optString("cDefaultBankFlag5");
        return this;
    }
}





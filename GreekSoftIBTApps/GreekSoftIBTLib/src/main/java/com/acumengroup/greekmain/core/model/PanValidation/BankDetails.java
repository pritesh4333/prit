package com.acumengroup.greekmain.core.model.PanValidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BankDetails implements GreekRequestModel, GreekResponseModel {
    private String cNEFTIFSCCode1;
    private String cBankName1;
    private String cAccNoNo1;
    private String cBankBranch1;
    String cDefaultBankFlag1;
    String cAccType1;
    private String nomName;
    private String nomRel;
    private String cClientNominee;
    private String cClientNomineeRelation;

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

    public String getcAccNoNo1() {
        return cAccNoNo1;
    }

    public void setcAccNoNo1(String cAccNoNo1) {
        this.cAccNoNo1 = cAccNoNo1;
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

    public String getcAccType1() {
        return cAccType1;
    }

    public void setcAccType1(String cAccType1) {
        this.cAccType1 = cAccType1;
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

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("cNEFTIFSCCode1", this.cNEFTIFSCCode1);
        jo.put("cBankName1", this.cBankName1);
        jo.put("cAccNoNo1", this.cAccNoNo1);
        jo.put("cBankBranch1", this.cBankBranch1);
        jo.put("cDefaultBankFlag1", this.cDefaultBankFlag1);
        jo.put("cAccType1", this.cAccType1);

        jo.put("cClientNominee", this.cClientNominee);
        jo.put("cClientNomineeRelation", this.cClientNomineeRelation);
        return jo;

    }

    @Override
    public GreekResponseModel fromJSON(JSONObject jo) throws JSONException {
        this.cNEFTIFSCCode1 = jo.optString("cNEFTIFSCCode1");
        this.cBankName1 = jo.optString("cBankName1");
        this.cAccNoNo1 = jo.optString("cAccNoNo1");
        this.cAccType1 = jo.optString("cAccType1");
        this.cClientNominee = jo.optString("cClientNominee");
        this.cClientNomineeRelation = jo.optString("cClientNomineeRelation");
        this.cDefaultBankFlag1 = jo.optString("cDefaultBankFlag1");


        return this;
    }
}

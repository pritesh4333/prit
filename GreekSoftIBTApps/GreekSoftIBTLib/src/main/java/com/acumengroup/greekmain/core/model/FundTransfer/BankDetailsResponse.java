package com.acumengroup.greekmain.core.model.FundTransfer;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 04-Jul-17.
 */

public class BankDetailsResponse implements GreekRequestModel, GreekResponseModel {

    private List<BankAccountList> bankAccountList = new ArrayList();
    private List<MerchantDetails> merchantDetails = new ArrayList();


    public List<BankAccountList> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccountList> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public List<MerchantDetails> getMerchantDetails() {
        return merchantDetails;
    }

    public void setMerchantDetails(List<MerchantDetails> merchantDetails) {
        this.merchantDetails = merchantDetails;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("BankAccountList")) {
            JSONArray ja1 = jo.getJSONArray("BankAccountList");
            this.bankAccountList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    BankAccountList data = new BankAccountList();
                    data.fromJSON((JSONObject) o);
                    this.bankAccountList.add(data);
                } else {
                    this.bankAccountList.add((BankAccountList) o);
                }
            }
        }

        if (jo.has("MerchantDetails")) {
            JSONArray ja1 = jo.getJSONArray("MerchantDetails");
            this.merchantDetails = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    MerchantDetails data = new MerchantDetails();
                    data.fromJSON((JSONObject) o);
                    this.merchantDetails.add(data);
                } else {
                    this.merchantDetails.add((MerchantDetails) o);
                }
            }
        }
        return this;
    }

}

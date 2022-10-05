package com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class TradeProfitLossAndNetQuantityResponse
        implements GreekRequestModel, GreekResponseModel {
    private NetQtyDetails netQtyDetails;
    private com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity.ProfitLoss ProfitLoss;

    public NetQtyDetails getNetQtyDetails() {
        return this.netQtyDetails;
    }

    public void setNetQtyDetails(NetQtyDetails netQtyDetails) {
        this.netQtyDetails = netQtyDetails;
    }

    public com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity.ProfitLoss getProfitLoss() {
        return this.ProfitLoss;
    }

    public void setProfitLoss(com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity.ProfitLoss ProfitLoss) {
        this.ProfitLoss = ProfitLoss;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        if ((this.netQtyDetails instanceof GreekRequestModel)) {
            jo.put("netQtyDetails", this.netQtyDetails.toJSONObject());
        }
        if ((this.ProfitLoss instanceof GreekRequestModel)) {
            jo.put("ProfitLoss", this.ProfitLoss.toJSONObject());
        }
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        if (jo.has("netQtyDetails")) {
            this.netQtyDetails = new NetQtyDetails();
            this.netQtyDetails.fromJSON(jo.getJSONObject("netQtyDetails"));
        }
        if (jo.has("ProfitLoss")) {
            this.ProfitLoss = new ProfitLoss();
            this.ProfitLoss.fromJSON(jo.getJSONObject("ProfitLoss"));
        }
        return this;
    }
}



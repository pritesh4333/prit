package com.acumengroup.greekmain.core.model.portfoliotrending;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllowedProductResponse implements GreekRequestModel, GreekResponseModel {


    private List<AllowedProduct> allowedProduct = new ArrayList();

    public List<AllowedProduct> getAllowedProduct() {
        return allowedProduct;
    }

    public void setAllowedProduct(List<AllowedProduct> allowedProduct) {
        this.allowedProduct = allowedProduct;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.allowedProduct.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("AllowedProduct", ja1);
        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {


        if (jo.has("AllowedProduct")) {
            JSONArray ja1 = jo.getJSONArray("AllowedProduct");
            this.allowedProduct = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    AllowedProduct allowedProduct = new AllowedProduct();
                    allowedProduct.fromJSON((JSONObject) o);
                    this.allowedProduct.add(allowedProduct);
                } else {
                    this.allowedProduct.add((AllowedProduct) o);
                }
            }
        }
        return this;
    }
}

package com.acumengroup.greekmain.core.model.MutualFundSipNewOrder;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MFundMediaAlreadyUploadResponce implements GreekRequestModel, GreekResponseModel {

    private String ErrorCode;
    private String imageName;
    private String imageData;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }


    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();

        jo.put("ErrorCode", this.ErrorCode);
        jo.put("ImageName", this.imageName);
        jo.put("ImageData", this.imageData);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {


        this.ErrorCode = jo.optString("ErrorCode");
        this.imageName = jo.optString("ImageName");
        this.imageData = jo.optString("ImageData");

        return this;
    }
}


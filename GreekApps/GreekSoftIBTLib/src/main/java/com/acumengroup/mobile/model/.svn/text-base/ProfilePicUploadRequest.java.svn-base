package com.acumengroup.mobile.model;

import android.content.Context;

import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaAlreadyUploadResponce;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaUploadResponce;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.acumengroup.greekmain.core.constants.ServiceConstants.ALREADY_UPLOAD_PHOTO_SVC_GROUP;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.KYC_SVC_GROUP;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.UPLOAD_PHOTO_SVC_GROUP;

public class ProfilePicUploadRequest implements GreekRequestModel, GreekResponseModel {

    private String imgeString;
    private String imgeType;
    private String imgeId;
    private String clientCode;
    private String panNo;
    private String flag;
    private static JSONObject echoParam = null;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getImgeId() {
        return imgeId;
    }

    public void setImgeId(String imgeId) {
        this.imgeId = imgeId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getImgeString() {
        return imgeString;
    }

    public void setImgeString(String imgeString) {
        this.imgeString = imgeString;
    }

    public String getImgType() {
        return imgeType;
    }

    public void setImgType(String imgType) {
        this.imgeType = imgType;
    }


    public JSONObject toJSONObject()
            throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put("imgeString", this.imgeString);
        jo.put("imgeType", this.imgeType);
        jo.put("imgeId", this.imgeId);
        jo.put("clientCode", this.clientCode);
        jo.put("panNo", this.panNo);
        jo.put("flag", this.flag);

        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {

        this.imgeString = jo.optString("imgeString");
        this.imgeType = jo.optString("imgeType");
        this.imgeId = jo.optString("imgeId");
        this.clientCode = jo.optString("clientCode");
        this.panNo = jo.optString("panNo");
        this.flag = jo.optString("flag");
        return this;
    }

    @Deprecated
    public static void sendRequest(String imgeId, String clientCode, String imgeString, String imgType, String panNumber,
                                   Context ctx, ServiceResponseListener listener) {
        ProfilePicUploadRequest request = new ProfilePicUploadRequest();
        request.setImgeString(imgeString);
        request.setImgType(imgType);
        request.setImgeId(imgeId);
        request.setClientCode(clientCode);
        request.setPanNo(panNumber);

        try {
            sendRequest(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Deprecated
    public static void sendRequestProfilePic(String clientCode, String panNumber, String flag, Context ctx, ServiceResponseListener listener) {
        ProfilePicUploadRequest request = new ProfilePicUploadRequest();
        request.setClientCode(clientCode);
        request.setPanNo(panNumber);
        request.setFlag(flag);

        try {
            sendRequestKYC(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void sendRequestKYC(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);

        jsonRequest.setResponseClass(MFundMediaUploadResponce.class);
        jsonRequest.setService(KYC_SVC_GROUP, "AlreadyPhotoUploadV2");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    public static void sendRequest(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);


        jsonRequest.setResponseClass(MFundMediaUploadResponce.class);
        jsonRequest.setService(UPLOAD_PHOTO_SVC_GROUP, UPLOAD_PHOTO_SVC_GROUP);
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

    @Deprecated
    public static void sendRequestUpload(String clientCode, String imageCode, String panNumber, Context ctx, ServiceResponseListener listener) {
        ProfilePicUploadRequest request = new ProfilePicUploadRequest();
        request.setClientCode(clientCode);
        request.setImgeId(imageCode);
        request.setPanNo(panNumber);

        try {
            sendRequestUpload(request.toJSONObject(), ctx, listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestUpload(JSONObject request, Context ctx, ServiceResponseListener listener) {
        GreekJSONRequest jsonRequest = null;
        jsonRequest = new GreekJSONRequest(ctx, request);
        if (echoParam != null) {
            jsonRequest.setEchoParam(echoParam);
            echoParam = null;
        }
        jsonRequest.setResponseClass(MFundMediaAlreadyUploadResponce.class);
        jsonRequest.setService(ALREADY_UPLOAD_PHOTO_SVC_GROUP, "AlreadyPhotoUploadV2");
        ServiceManager.getInstance(ctx).sendRequest(jsonRequest, listener);
    }

}

package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Sushant 9/19/2016.
 */
public class GuestLoginResponse implements GreekRequestModel, GreekResponseModel {
    private String errorCode;
    private String clientCode;
    private String Mobile;

    private String Arachne_IP;
    private String Apollo_IP;
    private String Iris_IP;
    private int Arachne_Port;
    private int Iris_Port;
    private int Apollo_Port;


    public String getArachne_IP() {
        return Arachne_IP;
    }

    public void setArachne_IP(String arachne_IP) {
        Arachne_IP = arachne_IP;
    }

    public String getApollo_IP() {
        return Apollo_IP;
    }

    public void setApollo_IP(String apollo_IP) {
        Apollo_IP = apollo_IP;
    }

    public String getIris_IP() {
        return Iris_IP;
    }

    public void setIris_IP(String iris_IP) {
        Iris_IP = iris_IP;
    }

    public int getArachne_Port() {
        return Arachne_Port;
    }

    public void setArachne_Port(int arachne_Port) {
        Arachne_Port = arachne_Port;
    }

    public int getIris_Port() {
        return Iris_Port;
    }

    public void setIris_Port(int iris_Port) {
        Iris_Port = iris_Port;
    }

    public int getApollo_Port() {
        return Apollo_Port;
    }

    public void setApollo_Port(int apollo_Port) {
        Apollo_Port = apollo_Port;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.errorCode);
        jo.put("ClientCode", this.clientCode);
        jo.put("Mobile", this.Mobile);
        jo.put("Arachne_IP", this.Arachne_IP);
        jo.put("Apollo_IP", this.Apollo_IP);
        jo.put("Iris_IP", this.Iris_IP);
        jo.put("Arachne_Port", this.Arachne_Port);
        jo.put("Iris_Port", this.Iris_Port);
        jo.put("Apollo_Port", this.Apollo_Port);
        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.errorCode = jo.optString("ErrorCode");
        this.clientCode = jo.optString("ClientCode");
        this.Mobile = jo.optString("Mobile");
        this.Arachne_IP = jo.optString("Arachne_IP");
        this.Apollo_IP = jo.optString("Apollo_IP");
        this.Iris_IP = jo.optString("Iris_IP");
        this.Arachne_Port = jo.optInt("Arachne_Port");
        this.Iris_Port = jo.optInt("Iris_Port");
        this.Apollo_Port = jo.optInt("Apollo_Port");

        return this;
    }
}

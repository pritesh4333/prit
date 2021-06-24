package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class    UserLoginValidationResponse
        implements GreekRequestModel, GreekResponseModel {
    private List<Questions> questionsList = new ArrayList();
    private String executionCode;
    private String errorCode;
    private String PassType;
    private String clientCode;
    private String StatusMessage;
    private String CashCommodity;
    private String UserName;
    private String Token;
    private String UserFlag;
    private String defaultProduct;
    private String validate2FA;
    private String holdingFlag;
    private String OrderTime;
    private String Theme;
    private String userType;
    private String KYCStatus;
    private String panNo;
    private String dob;
    private String clientName;
    private String domainName;
    private String domainPort;
    private String isSecure;

    private List<AllowedMarket> allowedMarket = new ArrayList();
    private String Arachne_IP;
    private String Apollo_IP;
    private String Iris_IP;
    private int Arachne_Port;
    private int Iris_Port;
    private int Apollo_Port;

    private String ChartSetting;
    private String IsStrategyProduct;
    private String IsEDISProduct;
    private String IsBOReport;
    private String IsSameDevice;
    private String isMPINSet;
    private String IsValidateSecondary;
    private String IsRedisEnabled;

    public String getIsBOReport() {
        return IsBOReport;
    }

    public void setIsBOReport(String isBOReport) {
        IsBOReport = isBOReport;
    }

    public String getIsRedisEnabled() {
        return IsRedisEnabled;
    }

    public void setIsRedisEnabled(String isRedisEnabled) {
        IsRedisEnabled = isRedisEnabled;
    }


    public String getIsValidateSecondary() {
        return IsValidateSecondary;
    }

    public void setIsValidateSecondary(String isValidateSecondary) {
        IsValidateSecondary = isValidateSecondary;
    }

    public String getIsMPINSet() {
        return isMPINSet;
    }

    public void setIsMPINSet(String isMPINSet) {
        this.isMPINSet = isMPINSet;
    }

    public String getIsSameDevice() {
        return IsSameDevice;
    }

    public void setIsSameDevice(String isSameDevice) {
        IsSameDevice = isSameDevice;
    }


    public String getIsEDISProduct() {
        return IsEDISProduct;
    }

    public void setIsEDISProduct(String isEDISProduct) {
        IsEDISProduct = isEDISProduct;
    }

    public String getIsStrategyProduct() {
        return IsStrategyProduct;
    }

    public void setIsStrategyProduct(String isStrategyProduct) {
        IsStrategyProduct = isStrategyProduct;
    }

    public String getChartSetting() {
        return ChartSetting;
    }

    public void setChartSetting(String chartSetting) {
        ChartSetting = chartSetting;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainPort() {
        return domainPort;
    }

    public void setDomainPort(String domainPort) {
        this.domainPort = domainPort;
    }

    public String getIsSecure() {
        return isSecure;
    }

    public void setIsSecure(String isSecure) {
        this.isSecure = isSecure;
    }

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

    private List<String> mandateIdlist = new ArrayList();

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getKYCStatus() {
        return KYCStatus;
    }

    public void setKYCStatus(String KYCStatus) {
        this.KYCStatus = KYCStatus;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getValidate2FA() {
        return validate2FA;
    }

    public void setValidate2FA(String validate2FA) {
        this.validate2FA = validate2FA;
    }

    public String getHoldingFlag() {
        return holdingFlag;
    }

    public void setHoldingFlag(String holdingFlag) {
        this.holdingFlag = holdingFlag;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getTheme() {
        return Theme;
    }

    public void setTheme(String theme) {
        Theme = theme;
    }

    public List<AllowedMarket> getAllowedMarket() {
        return allowedMarket;
    }

    public void setAllowedMarket(List<AllowedMarket> allowedMarket) {
        this.allowedMarket = allowedMarket;
    }

    public List<String> getMandateIdlist() {
        return mandateIdlist;
    }

    public void setMandateIdlist(List<String> mandateIdlist) {
        this.mandateIdlist = mandateIdlist;
    }

    public String getDefaultProduct() {
        return defaultProduct;
    }

    public void setDefaultProduct(String defaultProduct) {
        this.defaultProduct = defaultProduct;
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public String getExecutionCode() {
        return executionCode;
    }

    public void setExecutionCode(String executionCode) {
        this.executionCode = executionCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getPassType() {
        return PassType;
    }

    public void setPassType(String passType) {
        PassType = passType;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public void setClientCode(String UserCode) {
        this.clientCode = UserCode;
    }

    public String getStatusMessage() {
        return this.StatusMessage;
    }

    public void setStatusMessage(String StatusMessage) {
        this.StatusMessage = StatusMessage;
    }

    public String getCashCommodity() {
        return this.CashCommodity;
    }

    public void setCashCommodity(String CashCommodity) {
        this.CashCommodity = CashCommodity;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getToken() {
        return this.Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }


    public String getUserFlag() {
        return UserFlag;
    }

    public void setUserFlag(String userFlag) {
        UserFlag = userFlag;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ClientCode", this.clientCode);
        jo.put("Executioncode", this.executionCode);
        jo.put("ErrorCode", this.errorCode);
        jo.put("KYCStatus", this.KYCStatus);
        jo.put("panNo", this.panNo);
        jo.put("dob", this.dob);
        jo.put("PassType", this.PassType);
        jo.put("StatusMessage", this.StatusMessage);
        jo.put("CashCommodity", this.CashCommodity);
        jo.put("UserName", this.UserName);
        jo.put("Token", this.Token);
        jo.put("UserFlag", this.UserFlag);
        jo.put("defaultProduct", this.defaultProduct);
        jo.put("OrderTime", this.OrderTime);
        jo.put("validate2FA", this.validate2FA);
        jo.put("holdingFlag", this.holdingFlag);
        jo.put("Theme", this.holdingFlag);
        jo.put("userType", this.userType);
        jo.put("clientName", this.clientName);
        jo.put("Arachne_IP", this.Arachne_IP);
        jo.put("Apollo_IP", this.Apollo_IP);
        jo.put("Iris_IP", this.Iris_IP);
        jo.put("Arachne_Port", this.Arachne_Port);
        jo.put("Iris_Port", this.Iris_Port);
        jo.put("Apollo_Port", this.Apollo_Port);
        jo.put("domainPort", this.domainPort);
        jo.put("domainName", this.domainName);
        jo.put("isSecure", this.isSecure);
        jo.put("ChartSetting", this.ChartSetting);
        jo.put("IsStrategyProduct", this.IsStrategyProduct);
        jo.put("IsEDISProduct", this.IsEDISProduct);
        jo.put("IsSameDevice", this.IsSameDevice);
        jo.put("isMPINSet", this.isMPINSet);
        jo.put("IsValidateSecondary", this.IsValidateSecondary);
        jo.put("IsRedisEnabled", this.IsRedisEnabled);
        jo.put("IsBOReport", this.IsBOReport);

        JSONArray ja1 = new JSONArray();
        Iterator iterator = this.allowedMarket.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if ((o instanceof GreekRequestModel)) {
                ja1.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja1.put(o);
            }
        }
        jo.put("AllowedMarket", ja1);


        JSONArray ja2 = new JSONArray();
        Iterator iterator2 = this.mandateIdlist.iterator();
        while (iterator2.hasNext()) {
            Object o = iterator2.next();
            if ((o instanceof GreekRequestModel)) {
                ja2.put(((GreekRequestModel) o).toJSONObject());
            } else {
                ja2.put(o);
            }
        }
        jo.put("mandateId", ja2);

        return jo;
    }


    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.clientCode = jo.optString("ClientCode");
        this.executionCode = jo.optString("Executioncode");
        this.errorCode = jo.optString("ErrorCode");
        this.KYCStatus = jo.optString("KYCStatus");
        this.panNo = jo.optString("panNo");
        this.dob = jo.optString("dob");
        this.PassType = jo.optString("PassType");
        this.StatusMessage = jo.optString("StatusMessage");
        this.CashCommodity = jo.optString("CashCommodity");
        this.UserName = jo.optString("UserName");
        this.Token = jo.optString("Token");
        this.UserFlag = jo.optString("UserFlag");
        this.defaultProduct = jo.optString("defaultProduct");
        this.OrderTime = jo.optString("OrderTime");
        this.validate2FA = jo.optString("validate2FA");
        this.holdingFlag = jo.optString("holdingFlag");
        this.Theme = jo.optString("Theme");
        this.userType = jo.optString("userType");
        this.clientName = jo.optString("clientName");
        this.Arachne_IP = jo.optString("Arachne_IP");
        this.Apollo_IP = jo.optString("Apollo_IP");
        this.Iris_IP = jo.optString("Iris_IP");
        this.Arachne_Port = jo.optInt("Arachne_Port");
        this.Iris_Port = jo.optInt("Iris_Port");
        this.Apollo_Port = jo.optInt("Apollo_Port");
        this.domainName = jo.optString("domainName");
        this.domainPort = jo.optString("domainPort");
        this.isSecure = jo.optString("isSecure");
        this.ChartSetting = jo.optString("ChartSetting");
        this.IsStrategyProduct = jo.optString("IsStrategyProduct");
        this.IsEDISProduct = jo.optString("IsEDISProduct");
        this.IsSameDevice = jo.optString("IsSameDevice");
        this.isMPINSet = jo.optString("isMPINSet");
        this.IsValidateSecondary = jo.optString("IsValidateSecondary");
        this.IsRedisEnabled = jo.optString("IsRedisEnabled");
        this.IsBOReport = jo.optString("IsBOReport");

        if (jo.has("questions")) {
            JSONArray ja1 = jo.getJSONArray("questions");
            this.questionsList = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    Questions data = new Questions();
                    data.fromJSON((JSONObject) o);
                    this.questionsList.add(data);
                } else {
                    this.questionsList.add((Questions) o);
                }
            }
        }
        if (jo.has("AllowedMarket")) {
            JSONArray ja1 = jo.getJSONArray("AllowedMarket");
            this.allowedMarket = new ArrayList(ja1.length());
            for (int i = 0; i < ja1.length(); i++) {
                Object o = ja1.get(i);
                if ((o instanceof JSONObject)) {
                    AllowedMarket allowedMarket = new AllowedMarket();
                    allowedMarket.fromJSON((JSONObject) o);
                    this.allowedMarket.add(allowedMarket);
                } else {
                    this.allowedMarket.add((AllowedMarket) o);
                }
            }
        }

        if (jo.has("mandateId")) {

            Object intervention = jo.get("mandateId");
            if (intervention instanceof JSONArray) {
                // It's an array
                JSONArray ja2 = jo.getJSONArray("mandateId");
                this.mandateIdlist = new ArrayList(ja2.length());

                for (int i = 0; i < ja2.length(); i++) {
                    String id = ja2.getString(i);

                    this.mandateIdlist.add(id);

                }
            } else if (intervention instanceof JSONObject) {
                // It's an object
                mandateIdlist.clear();

            } else {
                mandateIdlist.clear();
            }


        }

        return this;
    }
}
package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 9/19/2016.
 */
public class ValidateGuestResponse implements GreekRequestModel, GreekResponseModel {
    private String errorCode;
    private String clientCode;
    private String sessionId;
    private String executionCode;
    private String Mobile;
    private String validateGuest;
    private String validateTransaction;
    private String validateThrough;
    private String showLogin;
    private String accord_Token;
    private String posCode;
    private String heartbeat_Intervals;
    private String reconnection_attempts;
    private String showDescription;
    private String validatePasswordOnce;
    private String ft_testing_bypass;
    private String isSecure;
    private String apr_version;

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
    private String IsRedisEnabled;
    private String PaymentGateway;
    private String upiPaymentEnabled;
    private String IsPledgeProduct;
    private String ssl_url;
     private String bo_office_url;
    private String ft_offline_url;
    private String ipo_url;
    private String ft_Link;
    private String ft_Link_Upi;
    private String PledgeOffline;
    private String eKycSignUpUrl;
    private String eKycSignUpPort;
    private String scripCountInWatchlist="50";



    private String login_compliance="null";

    public String getLogin_compliance() {
        return login_compliance;
    }

    public void setLogin_compliance(String login_compliance) {
        this.login_compliance = login_compliance;
    }
    public String getScripCountInWatchlist() {
        return scripCountInWatchlist;
    }

    public void setScripCountInWatchlist(String scripCountInWatchlist) {
        this.scripCountInWatchlist = scripCountInWatchlist;
    }

    public String geteKycSignUpUrl() {
        return eKycSignUpUrl;
    }

    public void seteKycSignUpUrl(String eKycSignUpUrl) {
        this.eKycSignUpUrl = eKycSignUpUrl;
    }

    public String geteKycSignUpPort() {
        return eKycSignUpPort;
    }

    public void seteKycSignUpPort(String eKycSignUpPort) {
        this.eKycSignUpPort = eKycSignUpPort;
    }

    public String getPledgeOffline() {
        return PledgeOffline;
    }

    public void setPledgeOffline(String pledgeOffline) {
        PledgeOffline = pledgeOffline;
    }

    public String getFt_Link() {
        return ft_Link;
    }

    public void setFt_Link(String ft_Link) {
        this.ft_Link = ft_Link;
    }

    public String getFt_Link_Upi() {
        return ft_Link_Upi;
    }

    public void setFt_Link_Upi(String ft_Link_Upi) {
        this.ft_Link_Upi = ft_Link_Upi;
    }

    public String getIsPledgeProduct() {
        return IsPledgeProduct;
    }

    public void setIsPledgeProduct(String isPledgeProduct) {
        IsPledgeProduct = isPledgeProduct;
    }

    public String getPaymentGateway() {
        return PaymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.PaymentGateway = paymentGateway;
    }

    public String getUpiPaymentEnabled() {
        return upiPaymentEnabled;
    }

    public void setUpiPaymentEnabled(String upiPaymentEnabled) {
        this.upiPaymentEnabled = upiPaymentEnabled;
    }

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

    public String getIsSecure() {
        return isSecure;
    }

    public void setIsSecure(String isSecure) {
        this.isSecure = isSecure;
    }

    public String getApr_version() {
        return apr_version;
    }

    public void setApr_version(String apr_version) {
        this.apr_version = apr_version;
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

    public String getFt_testing_bypass() {
        return ft_testing_bypass;
    }

    public void setFt_testing_bypass(String ft_testing_bypass) {
        this.ft_testing_bypass = ft_testing_bypass;
    }

    public String getValidatePasswordOnce() {
        return validatePasswordOnce;
    }

    public void setValidatePasswordOnce(String validatePasswordOnce) {
        this.validatePasswordOnce = validatePasswordOnce;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getAccord_Token() {
        return accord_Token;
    }

    public void setAccord_Token(String accord_Token) {
        this.accord_Token = accord_Token;
    }

    public String getShowDescription() {
        return showDescription;
    }

    public void setShowDescription(String showDescription) {
        this.showDescription = showDescription;
    }

    public String getHeartbeat_Intervals() {
        return heartbeat_Intervals;
    }

    public void setHeartbeat_Intervals(String heartbeat_Intervals) {
        this.heartbeat_Intervals = heartbeat_Intervals;
    }

    public String getReconnection_attempts() {
        return reconnection_attempts;
    }

    public void setReconnection_attempts(String reconnection_attempts) {
        this.reconnection_attempts = reconnection_attempts;
    }

    public String getValidateThrough() {
        return validateThrough;
    }

    public void setValidateThrough(String validateThrough) {
        this.validateThrough = validateThrough;
    }

    public String getValidateTransaction() {
        return validateTransaction;
    }

    public void setValidateTransaction(String validateTransaction) {
        this.validateTransaction = validateTransaction;
    }

    public String getValidateGuest() {
        return validateGuest;
    }

    public void setValidateGuest(String validateGuest) {
        this.validateGuest = validateGuest;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
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

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getShowLogin() {
        return showLogin;
    }

    public void setShowLogin(String showLogin) {
        this.showLogin = showLogin;
    }

    public String getSsl_url() {
        return ssl_url;
    }

    public void setSsl_url(String ssl_url) {
        this.ssl_url = ssl_url;
    }

    public String getBo_office_url() {
        return bo_office_url;
    }

    public void setBo_office_url(String bo_office_url) {
        this.bo_office_url = bo_office_url;
    }

    public String getFt_offline_url() {
        return ft_offline_url;
    }

    public void setFt_offline_url(String ft_offline_url) {
        this.ft_offline_url = ft_offline_url;
    }

    public String getIpo_url() {
        return ipo_url;
    }

    public void setIpo_url(String ipo_url) {
        this.ipo_url = ipo_url;
    }



    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("ErrorCode", this.errorCode);
        jo.put("ClientCode", this.clientCode);
        jo.put("sessionId", this.sessionId);
        jo.put("Executioncode", this.executionCode);
        jo.put("Mobile", this.Mobile);
        jo.put("validateGuest", this.validateGuest);
        jo.put("validateTransaction", this.validateTransaction);
        jo.put("showLogin", this.showLogin);
        jo.put("validateThrough", this.validateThrough);
        jo.put("accord_Token", this.accord_Token);
        jo.put("posCode", this.posCode);
        jo.put("reconnection_attempts", this.reconnection_attempts);
        jo.put("heartbeat_Intervals", this.heartbeat_Intervals);
        jo.put("showDescription", this.showDescription);
        jo.put("validatePasswordOnce", this.validatePasswordOnce);
        jo.put("ft_testing_bypass", this.ft_testing_bypass);
        jo.put("isSecure", this.isSecure);
        jo.put("apr_version", this.apr_version);
        jo.put("IsRedisEnabled", this.IsRedisEnabled);
        jo.put("PaymentGateway", this.PaymentGateway);
        jo.put("upiPaymentEnabled", this.upiPaymentEnabled);
        jo.put("IsPledgeProduct", this.IsPledgeProduct);
        jo.put("ssl_url", this.ssl_url);
         jo.put("bo_office_url", this.bo_office_url);
        jo.put("ft_offline_url", this.ft_offline_url);
        jo.put("ipo_url", this.ipo_url);
        jo.put("ft_Link", this.ft_Link);
        jo.put("ft_Link_Upi", this.ft_Link_Upi);
        jo.put("PledgeOffline", this.PledgeOffline);
        jo.put("eKycSignUpUrl", this.eKycSignUpUrl);
        jo.put("eKycSignUpPort", this.eKycSignUpPort);
        jo.put("login_compliance", this.login_compliance);
        jo.put("scripCountInWatchlist", this.scripCountInWatchlist);


        jo.put("Arachne_IP", this.Arachne_IP);
        jo.put("Apollo_IP", this.Apollo_IP);
        jo.put("Iris_IP", this.Iris_IP);
        jo.put("Arachne_Port", this.Arachne_Port);
        jo.put("Iris_Port", this.Iris_Port);
        jo.put("Apollo_Port", this.Apollo_Port);
        jo.put("ChartSetting", this.ChartSetting);
        jo.put("IsStrategyProduct", this.IsStrategyProduct);
        jo.put("IsEDISProduct", this.IsEDISProduct);
        jo.put("IsBOReport", this.IsBOReport);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.errorCode = jo.optString("ErrorCode");
        this.clientCode = jo.optString("ClientCode");
        this.sessionId = jo.optString("sessionId");
        this.executionCode = jo.optString("Executioncode");
        this.Mobile = jo.optString("Mobile");
        this.validateGuest = jo.optString("validateGuest");
        this.validateTransaction = jo.optString("validateTransaction");
        this.showLogin = jo.optString("showLogin");
        this.validateThrough = jo.optString("validateThrough");
        this.accord_Token = jo.optString("accord_Token");
        this.posCode = jo.optString("posCode");
        this.reconnection_attempts = jo.optString("reconnection_attempts");
        this.heartbeat_Intervals = jo.optString("heartbeat_Intervals");
        this.showDescription = jo.optString("showDescription");
        this.validatePasswordOnce = jo.optString("validatePasswordOnce");
        this.ft_testing_bypass = jo.optString("ft_testing_bypass");
        this.isSecure = jo.optString("isSecure");
        this.apr_version = jo.optString("apr_version");
        this.IsRedisEnabled = jo.optString("IsRedisEnabled");
        this.PaymentGateway = jo.optString("PaymentGateway");
        this.upiPaymentEnabled = jo.optString("upiPaymentEnabled");
        this.IsPledgeProduct = jo.optString("IsPledgeProduct");

        this.ssl_url = jo.optString("ssl_url");
        this.bo_office_url = jo.optString("bo_office_url");
        this.ft_offline_url = jo.optString("ft_offline_url");
        this.ipo_url = jo.optString("ipo_url");
         this.ft_Link = jo.optString("ft_Link");
        this.ft_Link_Upi = jo.optString("ft_Link_Upi");
        this.PledgeOffline = jo.optString("PledgeOffline");
        this.eKycSignUpUrl = jo.optString("eKycSignUpUrl");
        this.eKycSignUpPort = jo.optString("eKycSignUpPort");
        this.login_compliance = jo.optString("login_compliance");


        this.Arachne_IP = jo.optString("Arachne_IP");
        this.Apollo_IP = jo.optString("Apollo_IP");
        this.Iris_IP = jo.optString("Iris_IP");
        this.Arachne_Port = jo.optInt("Arachne_Port");
        this.Iris_Port = jo.optInt("Iris_Port");
        this.Apollo_Port = jo.optInt("Apollo_Port");
        this.ChartSetting = jo.optString("ChartSetting");
        this.IsStrategyProduct = jo.optString("IsStrategyProduct");
        this.IsEDISProduct = jo.optString("IsEDISProduct");
        this.IsBOReport = jo.optString("IsBOReport");
        this.scripCountInWatchlist = jo.optString("scripCountInWatchlist");

        return this;
    }
}

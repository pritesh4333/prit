package com.acumengroup.greekmain.core.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.greekmain.core.data.DataBuffer;
import com.acumengroup.greekmain.core.model.portfoliotrending.AllowedProduct;
import com.acumengroup.greekmain.util.Util;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AccountDetails implements Serializable {

    private final static String EXECUTIONCODE = "executionCode";
    private final static String ERRORCODE = "errorCode";
    private final static String SESSIONID = "sessionId";

    private final static String CLIENTCODE = "ClientCode";
    private final static String STATUSMESSAGE = "StatusMessage";
    private static final String MOBILENUMBER = "MobileNumber";

    private final static String USERNAME = "UserName";
    private final static String CLIENTNAME = "clientName";
    private final static String USERPAN = "cFirstApplicantPAN";
    private final static String TOKEN = "Token";
    private final static String USERTYPE = "UserType";
    private final static String BROKERID = "brokerid";
    private final static String timeBeforeIdleInMillis = "timeInMillis";
    private static Boolean validateTransaction = true;
    private static Boolean isVisibleNiftyTitle = false;
    private static Boolean isMainActivity = false;
    private static Boolean isOnPauseApp = false;
    private static Boolean isThemeApplied = false;
    private static Boolean isbackmainActivity = false;
    private static Boolean isLogoutApp = false;
    private static Boolean isAgeingHoldingReport = false;
    private static Boolean clientPOA = false;
    private static String POADPId = "0";
    private static String IsSameDevice = "";
    private static  String defaultProducttype_name;

    private static String IsStrategyProduct = "false";
    private static String chartSetting = "chartIQ";
    private static String LastSelectedGroup = "";
    private static String IsRedisEnabled = "false";
    public static boolean isLogout = false;

    private static Boolean isOHLCSHOW = false;
    private static Boolean isholdingbottomsheetshow = false;


    private static String accord_Token = "";

    private static String cCity = "";
    private static String cEmailId = "";
    private static String cGreekUserOrInvestorName = "";
    private static String cPanNo = "";
    private static String lPinCode = "";
    private static String cAddress = "";
    private static String lMobileNo = "";
    private static String bankName = "";
    private static String ifscCode = "";
    private static String bankAcNo = "";
    private static String bankamount = "";
    private static String DPID = "";
    private static String DPType  = "";
    private static String ft_Link_Upi  = "";
    private static String ft_Link  = "";
    private static String PledgeOffline  = "";

    private static String eKycSignUpUrl  = "";
    private static String eKycSignUpPort  = "";
    private static String scripCountInWatchlist  = "";




    private static String login_compliance   = "null";

    private static Boolean isscriptcurr = false;

    public static String getLogin_compliance() {
        return login_compliance;
    }

    public static void setLogin_compliance(String login_compliance) {
        AccountDetails.login_compliance = login_compliance;
    }

    public static String getScripCountInWatchlist() {
        return scripCountInWatchlist;
    }

    public static void setScripCountInWatchlist(String scripCountInWatchlist) {
        AccountDetails.scripCountInWatchlist = scripCountInWatchlist;
    }

    public static String geteKycSignUpUrl() {
        return eKycSignUpUrl;
    }

    public static void seteKycSignUpUrl(String eKycSignUpUrl) {
        AccountDetails.eKycSignUpUrl = eKycSignUpUrl;
    }

    public static String geteKycSignUpPort() {
        return eKycSignUpPort;
    }

    public static void seteKycSignUpPort(String eKycSignUpPort) {
        AccountDetails.eKycSignUpPort = eKycSignUpPort;
    }

    public static String getPledgeOffline() {
        return PledgeOffline;
    }

    public static void setPledgeOffline(String pledgeOffline) {
        PledgeOffline = pledgeOffline;
    }

    public static String getFt_Link_Upi() {
        return ft_Link_Upi;
    }

    public static void setFt_Link_Upi(String ft_Link_Upi) {
        AccountDetails.ft_Link_Upi = ft_Link_Upi;
    }

    public static String getFt_Link() {
        return ft_Link;
    }

    public static void setFt_Link(String ft_Link) {
        AccountDetails.ft_Link = ft_Link;
    }

    public static String getDPType() {
        return DPType;
    }

    public static void setDPType(String DPType) {
        AccountDetails.DPType = DPType;
    }


    public static Boolean getIsOnPauseApp() {
        return isOnPauseApp;
    }

    public static void setIsOnPauseApp(Boolean isOnPauseApp) {
        AccountDetails.isOnPauseApp = isOnPauseApp;
    }

    public static String getDefaultProducttype_name() {
        return defaultProducttype_name;
    }

    public static void setDefaultProducttype_name(String defaultProducttype_name) {
        AccountDetails.defaultProducttype_name = defaultProducttype_name;
    }

    public static boolean isIsLogout() {
        return isLogout;
    }

    public static void setIsLogout(boolean isLogout) {
        AccountDetails.isLogout = isLogout;
    }

    public static Boolean getIsbackmainActivity() {
        return isbackmainActivity;
    }

    public static void setIsbackmainActivity(Boolean isbackmainActivity) {
        AccountDetails.isbackmainActivity = isbackmainActivity;
    }

    public static Boolean getIsholdingbottomsheetshow() {
        return isholdingbottomsheetshow;
    }

    public static void setIsholdingbottomsheetshow(Boolean isholdingbottomsheetshow) {
        AccountDetails.isholdingbottomsheetshow = isholdingbottomsheetshow;
    }

    public static String getIsSameDevice() {
        return IsSameDevice;
    }

    public static void setIsSameDevice(String isSameDevice) {
        IsSameDevice = isSameDevice;
    }


    public static Boolean getIsLogoutApp() {
        return isLogoutApp;
    }

    public static void setIsLogoutApp(Boolean isLogoutApp) {
        AccountDetails.isLogoutApp = isLogoutApp;
    }

    public static String getPOADPId() {
        return POADPId;
    }

    public static void setPOADPId(String POADPId) {
        AccountDetails.POADPId = POADPId;
    }

    public static Boolean getIsAgeingHoldingReport() {
        return isAgeingHoldingReport;
    }

    public static void setIsAgeingHoldingReport(Boolean isAgeingHoldingReport) {
        AccountDetails.isAgeingHoldingReport = isAgeingHoldingReport;
    }

    public static String getIsRedisEnabled() {
        return IsRedisEnabled;
    }

    public static void setIsRedisEnabled(String isRedisEnabled) {
        IsRedisEnabled = isRedisEnabled;
    }



    public static String getChartSetting() {
        return chartSetting;
    }

    public static void setChartSetting(String chartSetting) {
        AccountDetails.chartSetting = chartSetting;
    }

    public static String getIsStrategyProduct() {
        return IsStrategyProduct;
    }

    public static void setIsStrategyProduct(String isStrategyProduct) {
        IsStrategyProduct = isStrategyProduct;
    }

    public static Boolean getIsMainActivity() {
        return isMainActivity;
    }

    public static void setIsMainActivity(Boolean isMainActivity) {
        AccountDetails.isMainActivity = isMainActivity;
    }

    public static Boolean getIsThemeApplied() {
        return isThemeApplied;
    }

    public static void setIsThemeApplied(Boolean isThemeApplied) {
        AccountDetails.isThemeApplied = isThemeApplied;
    }

    public static Boolean getIsscriptcurr() {
        return isscriptcurr;
    }

    public static void setIsscriptcurr(Boolean isscriptcurr) {
        AccountDetails.isscriptcurr = isscriptcurr;
    }

    public static Boolean getIsOHLCSHOW() {
        return isOHLCSHOW;
    }

    public static void setIsOHLCSHOW(Boolean isOHLCSHOW) {
        AccountDetails.isOHLCSHOW = isOHLCSHOW;
    }

    public static String getDPID() {
        return DPID;
    }

    public static void setDPID(String DPID) {
        AccountDetails.DPID = DPID;
    }

    public static String getBankamount() {
        return bankamount;
    }

    public static void setBankamount(String bankamount) {
        AccountDetails.bankamount = bankamount;
    }

    private static int selectedExpiryposition = 0;

    private static String posCode = "";
    private static String watchlistGroupName = "";
    private static String themeFlag = "black";
    public static int iris_Counter = 0;
    public static int iris_LoginCounter = 0;
    public static int apollo_LoginCounter = 0;

    private static String productTypeFlag = "1";
    private static Boolean dematFlag = true;
    private static String validateThrough = "both";
    private static String cUserType = "both";
    private static String netpositionChecked = "false";
    private static Boolean showDescription = true;
    public static Boolean isValidSession = true;


    private static String reconnection_attempts = "180";
    private static String heartbeat_Intervals = "10";
    private static boolean isIrisConnected = false;
    private static boolean isApolloConnected = false;

    private static String loginOnceFlag = "0";
    private static String ft_testing_bypass = "false";
    private static String login_user_type = "openuser";

    private static String IsEDISProduct = "false";
    private static String isBackOffice = "false";
    private static String isLasProduct = "false";

    private static String isSecure = "https";
    private static String isinumber = "";
    private static String eps = "";
    private static String pe = "";
    private static String pb = "";
    private static String divyield = "";
    private static String bsemcap = "";
    private static String nsemcap = "";
    private static String fv = "";
    private static String bv = "";
    private static String AssetsType = "";
    private static boolean iSCompanySummaryAvailbale = false;
    private static boolean iSAutoDomainEnable = false;


    private static String Arachne_IP = "";
    private static String apr_version = "5.0";
    private static String Apollo_IP = "";
    private static String Iris_IP = "";
    private static String PaymentGateway = "";
    private static String upiPaymentEnabled = "";
    private static String IsPledgeProduct = "";
    private static int Arachne_Port = 0;
    private static int Iris_Port = 0;
    private static int Apollo_Port = 0;
    private static String ssl_url = "";
     private static String bo_office_url = "";
    private static String ft_offline_url = "";
    private static String ipo_url = "";



    public static String getSsl_url() {
        return ssl_url;
    }

    public static void setSsl_url(String ssl_url) {
        AccountDetails.ssl_url = ssl_url;
    }

    public static String getBo_office_url() {
        return bo_office_url;
    }

    public static void setBo_office_url(String bo_office_url) {
        AccountDetails.bo_office_url = bo_office_url;
    }

    public static String getFt_offline_url() {
        return ft_offline_url;
    }

    public static void setFt_offline_url(String ft_offline_url) {
        AccountDetails.ft_offline_url = ft_offline_url;
    }

    public static String getIpo_url() {
        return ipo_url;
    }

    public static void setIpo_url(String ipo_url) {
        AccountDetails.ipo_url = ipo_url;
    }

    public static String getPaymentGateway() {
        return PaymentGateway;
    }

    public static void setPaymentGateway(String paymentGateway) {
        AccountDetails.PaymentGateway = paymentGateway;
    }

    public static String getUpiPaymentEnabled() {
        return upiPaymentEnabled;
    }

    public static void setUpiPaymentEnabled(String upiPaymentEnabled) {
        AccountDetails.upiPaymentEnabled = upiPaymentEnabled;
    }

    public static String getIsPledgeProduct() {
        return IsPledgeProduct;
    }

    public static void setIsPledgeProduct(String isPledgeProduct) {
        IsPledgeProduct = isPledgeProduct;
    }

    public static String getApr_version() {
        return apr_version;
    }

    public static void setApr_version(String apr_version) {
        AccountDetails.apr_version = apr_version;
    }

    public static String getIsBackOffice() {
        return isBackOffice;
    }

    public static void setIsBackOffice(String isBackOffice) {
        AccountDetails.isBackOffice = isBackOffice;
    }

    public static String getIsLasProduct() {
        return isLasProduct;
    }

    public static void setIsLasProduct(String isLasProduct) {
        AccountDetails.isLasProduct = isLasProduct;
    }

    public static String getLastSelectedGroup() {
        return LastSelectedGroup;
    }

    public static void setLastSelectedGroup(String lastSelectedGroup) {
        LastSelectedGroup = lastSelectedGroup;
    }

    public static String getIsEDISProduct() {
        return IsEDISProduct;
    }

    public static void setIsEDISProduct(String isEDISProduct) {
        IsEDISProduct = isEDISProduct;
    }

    public static boolean isiSAutoDomainEnable() {
        return iSAutoDomainEnable;
    }

    public static void setiSAutoDomainEnable(boolean iSAutoDomainEnable) {
        AccountDetails.iSAutoDomainEnable = iSAutoDomainEnable;
    }

    public static String getERRORCODE() {
        return ERRORCODE;
    }

    public static String getEps() {
        return eps;
    }

    public static void setEps(String eps) {
        AccountDetails.eps = eps;
    }

    public static String getPe() {
        return pe;
    }

    public static void setPe(String pe) {
        AccountDetails.pe = pe;
    }

    public static String getPb() {
        return pb;
    }

    public static void setPb(String pb) {
        AccountDetails.pb = pb;
    }

    public static String getDivyield() {
        return divyield;
    }

    public static void setDivyield(String divyield) {
        AccountDetails.divyield = divyield;
    }

    public static String getBsemcap() {
        return bsemcap;
    }

    public static void setBsemcap(String bsemcap) {
        AccountDetails.bsemcap = bsemcap;
    }

    public static String getNsemcap() {
        return nsemcap;
    }

    public static void setNsemcap(String nsemcap) {
        AccountDetails.nsemcap = nsemcap;
    }

    public static String getFv() {
        return fv;
    }

    public static void setFv(String fv) {
        AccountDetails.fv = fv;
    }

    public static String getBv() {
        return bv;
    }

    public static void setBv(String bv) {
        AccountDetails.bv = bv;
    }

    public static String getEXECUTIONCODE() {
        return EXECUTIONCODE;
    }

    public static String getcCity() {
        return cCity;
    }

    public static void setcCity(String cCity) {
        AccountDetails.cCity = cCity;
    }

    public static String getcEmailId() {
        return cEmailId;
    }

    public static void setcEmailId(String cEmailId) {
        AccountDetails.cEmailId = cEmailId;
    }

    public static String getcGreekUserOrInvestorName() {
        return cGreekUserOrInvestorName;
    }

    public static void setcGreekUserOrInvestorName(String cGreekUserOrInvestorName) {
        AccountDetails.cGreekUserOrInvestorName = cGreekUserOrInvestorName;
    }

    public static String getcPanNo() {
        return cPanNo;
    }

    public static void setcPanNo(String cPanNo) {
        AccountDetails.cPanNo = cPanNo;
    }

    public static String getlPinCode() {
        return lPinCode;
    }

    public static void setlPinCode(String lPinCode) {
        AccountDetails.lPinCode = lPinCode;
    }

    public static String getcAddress() {
        return cAddress;
    }

    public static void setcAddress(String cAddress) {
        AccountDetails.cAddress = cAddress;
    }

    public static String getlMobileNo() {
        return lMobileNo;
    }

    public static void setlMobileNo(String lMobileNo) {
        AccountDetails.lMobileNo = lMobileNo;
    }

    public static String getBankName() {
        return bankName;
    }

    public static void setBankName(String bankName) {
        AccountDetails.bankName = bankName;
    }

    public static String getIfscCode() {
        return ifscCode;
    }

    public static void setIfscCode(String ifscCode) {
        AccountDetails.ifscCode = ifscCode;
    }

    public static String getBankAcNo() {
        return bankAcNo;
    }

    public static void setBankAcNo(String bankAcNo) {
        AccountDetails.bankAcNo = bankAcNo;
    }

    public static String getValidateThrough() {
        return validateThrough;
    }

    public static void setValidateThrough(String validateThrough) {
        AccountDetails.validateThrough = validateThrough;
    }

    public static Boolean getIsVisibleNiftyTitle() {
        return isVisibleNiftyTitle;
    }

    public static void setIsVisibleNiftyTitle(Boolean isVisibleNiftyTitle) {
        AccountDetails.isVisibleNiftyTitle = isVisibleNiftyTitle;
    }

    public static int getSelectedExpiryposition() {
        return selectedExpiryposition;
    }

    public static void setSelectedExpiryposition(int selectedExpiryposition) {
        AccountDetails.selectedExpiryposition = selectedExpiryposition;
    }

    public static int getToastCount() {
        return toastCount;
    }

    public static void setToastCount(int toastCount) {
        AccountDetails.toastCount = toastCount;
    }

    private static int toastCount = 0;
    private static int heartBeatApolloCount = 0;
    private static int heartBeatIrisCount = 0;
    private static int fragHeight = 0;
    private static int endIndex = 0;
    private static int startIndex = 0;
    private static String navKeeper;
    public static String deviceID;
    public static String LastInterval;
    public static String LastCurrentDate;
    public static String mfUserId;
    private static String ordTime;
    private static String timediff;
    private static int rowSpinnerSimple;
    public static int textColorDropdown;
    public static int textColorBlue;
    public static int textColorRed;
    public static int marketStatusStrip;
    public static int topViewBg;
    public static int backgroundColor;
    public static int dividerColor;
    public static boolean mfOrderAllowed = false;
    public static Drawable backgroundBgDrawable;

    public static int currentFragment;

    public static boolean orderServerAuthenticated = false;
    public static boolean broadcastServerAuthenticated = false;
    public static boolean flagDynamicHeight = false;


    public static boolean recoonectbroadcastServerAuthenticated = false;

    public static boolean orderReconnectionFlag = false;
    public static boolean AutoRefreshForDemat = false;
    public static boolean AutoRefreshForNetPosition = false;

    public static boolean marketEquity = false;
    public static boolean marketDerivative = false;
    public static boolean marketCurrCom = false;
    public static boolean marketGlobal = false;


    public static boolean derivativeTab = false;

    public static boolean portfolio = true;
    public static boolean watchlist = false;
    public static boolean lastvisited = false;

    public static boolean isLoginAndDisconnected = false;
    public static boolean isNetworkConnected = true;
    public static boolean isWeakNetworkConnected = false;
    public static boolean needofDash = false;

    public static Bundle globalArg = new Bundle();
    public static Bundle globalArgTradeFrag = new Bundle();
    public static Bundle globalArgQuoteFrag = new Bundle();
    public static Bundle globalArgChartFrag = new Bundle();
    public static Bundle globalArgEditFrag = new Bundle();
    public static Bundle globalPlaceOrderBundle = new Bundle();

    public static boolean nse_eq_status = true;
    public static boolean bse_eq_status = true;
    public static boolean nse_fno_status = true;
    public static boolean bse_fno_status = true;
    public static boolean nse_cd_status = true;
    public static boolean bse_cd_status = true;
    public static boolean mcx_com_status = true;
    public static boolean ncdex_com_status = true;
    public static boolean nse_com_status = true;
    public static boolean bse_com_status = true;

    public static boolean isPreOpen_nse_eq = false;
    public static boolean isPreOpen_bse_eq = false;
    public static boolean isPreOpen_nse_fno = false;
    public static boolean isPreOpen_bse_fno = false;
    public static boolean isPreOpen_nse_cd = false;
    public static boolean isPreOpen_bse_cd = false;
    public static boolean isPreOpen_mcx_com = false;
    public static boolean isPreOpen_ncdex_com = false;
    public static boolean isPreOpen_nse_com = false;
    public static boolean isPreOpen_bse_com = false;

    public static boolean isPostClosed_nse_eq = false;
    public static boolean isPostClosed_bse_eq = false;
    public static boolean isPostClosed_nse_fno = false;
    public static boolean isPostClosed_bse_fno = false;
    public static boolean isPostClosed_nse_cd = false;
    public static boolean isPostClosed_bse_cd = false;
    public static boolean isPostClosed_mcx_com = false;
    public static boolean isPostClosed_ncdex_com = false;
    public static boolean isPostClosed_nse_com = false;
    public static boolean isPostClosed_bse_com = false;

    public static boolean allowedmarket_nse = false;
    public static boolean allowedmarket_nfo = false;
    public static boolean allowedmarket_ncd = false;
    public static boolean allowedmarket_bse = false;
    public static boolean allowedmarket_bfo = false;
    public static boolean allowedmarket_bcd = false;
    public static boolean allowedmarket_ncdex = false;
    public static boolean allowedmarket_mcx = false;
    public static boolean allowedmarket_nCOM = false;
    public static boolean allowedmarket_bCOM = false;

    public static boolean managerRetailer = true;
    public static boolean isRestrictFreshOrder = false;
    public static boolean isAllowedFO = true;
    public static boolean isAllowedIntraday = true;
    public static boolean isIntradayTimerSet = false;
    public static long timeForTimer = 0;
    public static long lut = 1264689223;
    public static boolean isAllowedDelivery = true;
    public static boolean isAllowedShortSell = true;
    public static boolean isAllowedT2TSell = true;
    public static boolean isT2TScript = false;
    public static boolean isMTFAllowed = false;
    public static boolean isAllowed = true;


    public static int forceClose = 3;
    public static int fClose = 0;
    public static int ofClose = 0;
    public static int orderforceClose = 3;
    public static int listHeightValue = 0;

    public static boolean send_portfolio_req = false;
    public static boolean orderTimeFlag = true;
    public static boolean validateTpassFlag = false;
    public static boolean ProductType_Flag = true;
    public static List<AllowedProduct> allowedProduct = new ArrayList<>();
    public static List<String> mandatIdlist = new ArrayList<>();
    public static ArrayList<String> dashboardList = new ArrayList<>();
    public static ArrayList<String> allowedProductList = new ArrayList<>();


    /**
     * Set the account details in the following order,
     * CLIENTCODE,USERNAME,USERTYPE,TOKEN,CASHCOMMODITY,STATUSMESSAGE
     *
     * @param ctx
     * @param accountDetails
     */
    public static void setAccountDetails(Context ctx, String... accountDetails) {
        SharedPreferences.Editor editor = Util.getPrefs(ctx).edit();
        editor.putString("SharedPref_CLIENTCODE", accountDetails[0]);
        editor.putString("SharedPref_USERNAME", accountDetails[1]);
        editor.putString("SharedPref_SESSIONID", accountDetails[8]);
        editor.commit();
        editor.apply();

        DataBuffer.getInstance(ctx).put(CLIENTCODE, accountDetails[0]);
        DataBuffer.getInstance(ctx).put(USERNAME, accountDetails[1]);
        DataBuffer.getInstance(ctx).put(USERTYPE, accountDetails[2]);
        DataBuffer.getInstance(ctx).put(TOKEN, accountDetails[3]);
        DataBuffer.getInstance(ctx).put(MOBILENUMBER, accountDetails[4]);
        DataBuffer.getInstance(ctx).put(STATUSMESSAGE, accountDetails[5]);
        DataBuffer.getInstance(ctx).put(timeBeforeIdleInMillis, accountDetails[5]);

        DataBuffer.getInstance(ctx).put(EXECUTIONCODE, accountDetails[6]);
        DataBuffer.getInstance(ctx).put(ERRORCODE, accountDetails[7]);
        DataBuffer.getInstance(ctx).put(SESSIONID, accountDetails[8]);
        DataBuffer.getInstance(ctx).put(BROKERID, accountDetails[9]);

        DataBuffer.getInstance(ctx).put(CLIENTNAME, accountDetails[10]);

    }

    public static int HeightOFScreen(AppCompatActivity context) {
        int height;
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            display.getRealSize(size);
        }
        height = size.y;

        return height;
    }

    public static int getHeartBeatApolloCount() {
        return heartBeatApolloCount;
    }

    public static void setHeartBeatApolloCount(int heartBeatApolloCount) {
        AccountDetails.heartBeatApolloCount = heartBeatApolloCount;
    }

    public static int getHeartBeatIrisCount() {
        return heartBeatIrisCount;
    }

    public static void setHeartBeatIrisCount(int heartBeatIrisCount) {
        AccountDetails.heartBeatIrisCount = heartBeatIrisCount;
    }

    public static boolean isMfOrderAllowed() {
        return mfOrderAllowed;
    }

    public static void setMfOrderAllowed(boolean mfOrderAllowed) {
        AccountDetails.mfOrderAllowed = mfOrderAllowed;
    }

    public static void setTimeBeforeIdle(Context ctx, Long timeInMillis) {
        Util.getPrefs(ctx).edit().putLong(timeBeforeIdleInMillis, timeInMillis).commit();
    }

    public static long getTimeBeforeIdle(Context ctx) {
        return Util.getPrefs(ctx).getLong(timeBeforeIdleInMillis, 0);
    }

    public static int getTextColorDropdown() {
        return textColorDropdown;
    }

    public static void setTextColorDropdown(int textColorDropdown) {
        AccountDetails.textColorDropdown = textColorDropdown;
    }

    public static int getEndIndex() {
        return endIndex;
    }

    public static void setEndIndex(int endIndex) {
        AccountDetails.endIndex = endIndex;
    }

    public static int getStartIndex() {
        return startIndex;
    }

    public static String getLastInterval() {
        return LastInterval;
    }

    public static void setLastInterval(String lastInterval) {
        LastInterval = lastInterval;
    }

    public static String getLastCurrentDate() {
        return LastCurrentDate;
    }

    public static void setLastCurrentDate(String lastCurrentDate) {
        LastCurrentDate = lastCurrentDate;
    }

    public static void setStartIndex(int startIndex) {
        AccountDetails.startIndex = startIndex;
    }

    public static String getMobileNumber(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(MOBILENUMBER);
    }

    public static String getClientCode(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(CLIENTCODE);
    }

    public static void setUsercode(Context ctx, String usercode) {
        DataBuffer.getInstance(ctx).put(CLIENTCODE, usercode);
    }

    public static void setCLIENTNAME(Context ctx, String usercode) {
        DataBuffer.getInstance(ctx).put(CLIENTNAME, usercode);
    }

    public static String getCLIENTNAME(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(CLIENTNAME);
    }

    public static String getUserCode(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(CLIENTCODE);
    }

    public static void setSession(Context ctx, String session) {
        DataBuffer.getInstance(ctx).put(SESSIONID, session);
    }

    public static String getSession(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(SESSIONID);
    }


    public static String getStatusmessage(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(STATUSMESSAGE);
    }

    public static String getUsername(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(USERNAME);
    }

    public static void setUsername(Context ctx, String username) {
        DataBuffer.getInstance(ctx).put(USERNAME, username);
    }

    public static String getUserPAN(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(USERPAN);
    }

    public static void setUserPAN(Context ctx, String username) {
        DataBuffer.getInstance(ctx).put(USERPAN, username);
    }

    public static String getToken(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(TOKEN);
    }

    public static String getUsertype(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(USERTYPE);
    }


    public static String getSessionId(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(SESSIONID);
    }

    public static String getBrokerId(Context ctx) {
        return (String) DataBuffer.getInstance(ctx).get(BROKERID);
    }

    public static void setBrokerid(Context ctx, String usercode) {
        DataBuffer.getInstance(ctx).put(BROKERID, usercode);
    }

    public static String getLoginOnceFlag() {
        return loginOnceFlag;
    }

    public static void setLoginOnceFlag(String loginOnceFlag) {
        AccountDetails.loginOnceFlag = loginOnceFlag;
    }

    public static boolean isOrderServerAuthenticated() {
        return orderServerAuthenticated;
    }


    public static boolean isForceClose() {
        return orderServerAuthenticated;
    }

    /**
     * Call this method on every Logout and Application exit event.
     */

    public static String getWatchlistGrp(Context ctx) {
        return watchlistGroupName;
    }

    public static void setWatchlistGroup(String groupname) {
        watchlistGroupName = groupname;
    }

    public static void setThemeflag(String themeType) {
        themeFlag = themeType;
    }

    public static String getThemeFlag(Context ctx) {
        return themeFlag;
    }

    public static void setNavKeeper(String keeper1) {
        navKeeper = keeper1;
    }

    public static String getNavKeeper(Context ctx) {
        return navKeeper;
    }

    public static int getRowSpinnerSimple() {
        return rowSpinnerSimple;
    }

    public static void setRowSpinnerSimple(int rowSpinnerSimple) {
        AccountDetails.rowSpinnerSimple = rowSpinnerSimple;
    }

    public static boolean isValidateTpassFlag() {
        return validateTpassFlag;
    }

    public static void setValidateTpassFlag(boolean validateTpassFlag) {
        AccountDetails.validateTpassFlag = validateTpassFlag;
    }

    public static void clearCache(Context ctx) {
        DataBuffer.getInstance(ctx).clearCache();
    }

    public static String getOrdTime() {
        return ordTime;
    }

    public static void setOrdTime(String ordTime) {
        AccountDetails.ordTime = ordTime;
    }

    public static String getTimediff() {
        return timediff;
    }

    public static void setTimediff(String timediff) {
        AccountDetails.timediff = timediff;
    }

    public static String getDeviceID(Context ctx) {
        return deviceID;
    }

    public static void setDeviceID(String deviceID) {
        AccountDetails.deviceID = deviceID;
    }

    public static String getProductTypeFlag() {
        return productTypeFlag;
    }

    public static void setProductTypeFlag(String productTypeFlag) {
        AccountDetails.productTypeFlag = productTypeFlag;
    }

    public static Boolean getDematFlag() {
        return dematFlag;
    }

    public static void setDematFlag(Boolean dematFlag) {
        AccountDetails.dematFlag = dematFlag;
    }

    public static int getFragHeight() {
        return fragHeight;
    }

    public static void setFragHeight(int fragHeight) {
        AccountDetails.fragHeight = fragHeight;
    }

    public static Boolean getValidateTransaction() {
        return validateTransaction;
    }

    public static void setValidateTransaction(Boolean validateTransaction) {
        AccountDetails.validateTransaction = validateTransaction;
    }

    public static Boolean getClientPOA() {
        return clientPOA;
    }

    public static void setClientPOA(Boolean clientPOA1) {
        AccountDetails.clientPOA = clientPOA1;
    }


    public static String getAccord_Token() {
        return accord_Token;
    }


    public static void setAccord_Token(String accord_Token) {

        AccountDetails.accord_Token = accord_Token;
    }

    public static void setposCode(String posCode) {

        AccountDetails.posCode = posCode;
    }

    public static String getposCode() {
        return posCode;
    }

    public static List<AllowedProduct> getAllowedProduct() {
        return allowedProduct;
    }

    public static void setAllowedProduct(List<AllowedProduct> allowedProduct) {
        AccountDetails.allowedProduct = allowedProduct;
    }

    public static List<String> getMandatIdlist() {
        return mandatIdlist;
    }

    public static void setMandatIdlist(List<String> mandatIdlist) {
        AccountDetails.mandatIdlist = mandatIdlist;
    }


    public static String getMfUserId() {
        return mfUserId;
    }

    public static void setMfUserId(String mfUserId) {
        AccountDetails.mfUserId = mfUserId;
    }

    public static String getcUserType() {
        return cUserType;
    }

    public static void setcUserType(String cUserType) {
        AccountDetails.cUserType = cUserType;
    }

    public static String getReconnection_attempts() {
        return reconnection_attempts;
    }

    public static void setReconnection_attempts(String reconnection_attempts) {
        AccountDetails.reconnection_attempts = reconnection_attempts;
    }

    public static String getHeartbeat_Intervals() {
        return heartbeat_Intervals;
    }

    public static void setHeartbeat_Intervals(String heartbeat_Intervals) {
        AccountDetails.heartbeat_Intervals = heartbeat_Intervals;
    }

    private void setInputTextLayoutColor(int color, TextInputLayout textInputLayout) {
        try {
            Field field = textInputLayout.getClass().getDeclaredField("mFocusedTextColor");
            field.setAccessible(true);
            int[][] states = new int[][]{
                    new int[]{}
            };
            int[] colors = new int[]{
                    color
            };
            ColorStateList myList = new ColorStateList(states, colors);
            field.set(textInputLayout, myList);

            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(textInputLayout, myList);

            Method method = textInputLayout.getClass().getDeclaredMethod("updateLabelState", boolean.class);
            method.setAccessible(true);
            method.invoke(textInputLayout, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Boolean getShowDescription() {
        return showDescription;
    }

    public static void setShowDescription(Boolean showDescription) {
        AccountDetails.showDescription = showDescription;
    }

    public static ArrayList<String> getDashboardList() {
        return dashboardList;
    }

    public static void setDashboardList(ArrayList<String> dashboardList) {
        AccountDetails.dashboardList = dashboardList;
    }

    public static boolean isIsIrisConnected() {
        return isIrisConnected;
    }

    public static void setIsIrisConnected(boolean isIrisConnected) {
        AccountDetails.isIrisConnected = isIrisConnected;
    }

    public static boolean isIsApolloConnected() {
        return isApolloConnected;
    }

    public static void setIsApolloConnected(boolean isApolloConnected) {
        AccountDetails.isApolloConnected = isApolloConnected;
    }

    public static boolean isiSCompanySummaryAvailbale() {
        return iSCompanySummaryAvailbale;
    }

    public static void setiSCompanySummaryAvailbale(boolean iSCompanySummaryAvailbale) {
        AccountDetails.iSCompanySummaryAvailbale = iSCompanySummaryAvailbale;
    }

    public static int getIris_Counter() {
        return iris_Counter;
    }

    public static void setIris_Counter(int iris_Counter) {
        AccountDetails.iris_Counter = iris_Counter;
    }

    public static int getIris_LoginCounter() {
        return iris_LoginCounter;
    }

    public static void setIris_LoginCounter(int iris_LoginCounter) {
        AccountDetails.iris_LoginCounter = iris_LoginCounter;
    }

    public static int getApollo_LoginCounter() {
        return apollo_LoginCounter;
    }

    public static void setApollo_LoginCounter(int apollo_LoginCounter) {
        AccountDetails.apollo_LoginCounter = apollo_LoginCounter;
    }

    public static String getNetpositionChecked() {
        return netpositionChecked;
    }

    public static void setNetpositionChecked(String netpositionChecked) {
        AccountDetails.netpositionChecked = netpositionChecked;
    }

    public static ArrayList<String> getAllowedProductList() {
        return allowedProductList;
    }

    public static void setAllowedProductList(ArrayList<String> allowedProductList) {
        AccountDetails.allowedProductList = allowedProductList;
    }

    public static int getListHeightValue() {
        return listHeightValue;
    }

    public static void setListHeightValue(int listHeightValue) {
        AccountDetails.listHeightValue = listHeightValue;
    }

    public static String getFt_testing_bypass() {
        return ft_testing_bypass;
    }


    public static void setFt_testing_bypass(String ft_testing_bypass) {
        AccountDetails.ft_testing_bypass = ft_testing_bypass;
    }

    public static String getLogin_user_type() {
        return login_user_type;
    }

    public static void setLogin_user_type(String login_user_type) {
        AccountDetails.login_user_type = login_user_type;
    }

    public static String getArachne_IP() {
        return Arachne_IP;
    }

    public static void setArachne_IP(String arachne_IP) {
        Arachne_IP = arachne_IP;
    }

    public static String getApollo_IP() {
        return Apollo_IP;
    }

    public static void setApollo_IP(String apollo_IP) {
        Apollo_IP = apollo_IP;
    }

    public static String getIris_IP() {
        return Iris_IP;
    }

    public static void setIris_IP(String iris_IP) {
        Iris_IP = iris_IP;
    }

    public static int getArachne_Port() {
        return Arachne_Port;
    }

    public static void setArachne_Port(int arachne_Port) {
        Arachne_Port = arachne_Port;
    }

    public static int getIris_Port() {
        return Iris_Port;
    }

    public static void setIris_Port(int iris_Port) {
        Iris_Port = iris_Port;
    }

    public static int getApollo_Port() {
        return Apollo_Port;
    }

    public static void setApollo_Port(int apollo_Port) {
        Apollo_Port = apollo_Port;
    }

    public static String getIsSecure() {
        return isSecure;
    }

    public static void setIsSecure(String isSecure) {
        AccountDetails.isSecure = isSecure;
    }

    public static String getIsinumber() {
        return isinumber;
    }

    public static void setIsinumber(String isinumber) {
        AccountDetails.isinumber = isinumber;
    }

    public static String getAssetsType() {
        return AssetsType;
    }

    public static void setAssetsType(String assetsType) {
        AssetsType = assetsType;
    }
}


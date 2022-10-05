package com.mandot.mobile;

import static com.acumengroup.greekmain.core.network.WSHandler.VERSION_NO;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.GCM.SendNotifyInformationRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.WatchlistGroupRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.Questions;
import com.acumengroup.greekmain.core.model.userloginvalidation.UserLoginValidationRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.UserLoginValidationResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.network.NetworkChangeReceiver;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.GreekConfig;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.logger.GreekLog;
import com.acumengroup.greekmain.util.statistics.GreekStatistics;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.Guest.GuestLoginActivity;
import com.acumengroup.mobile.login.ChangePasswordActivity;
import com.acumengroup.mobile.login.LoginQuestionsActivity;
import com.acumengroup.mobile.messaging.NotifyRegistrationIntentService;
import com.acumengroup.mobile.messaging.QuickstartPreferences;
import com.acumengroup.mobile.service.MyService;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.GreekDialog.Action;
import com.acumengroup.ui.GreekDialog.DialogListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Arcadia
 */
public class SplashActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants {
    private ServiceResponseHandler serviceResponseHandler;
    private int count = 0;
    private NetworkChangeReceiver networkChangeReceiver;
    private StreamingController streamController;
    private OrderStreamingController orderStreamingController;

    private String deviceId, gcmToken, serverApiKey, updateVersion;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences.Editor editor;
    private String username, pass, transpass;
    String sessionId = null;
    String currentVersion;
    public  static String APPNAME="Mandot";
//    public static int clientCode = 3;// 1= philips,    2= marwadi,     3= vishwas

    private void fp_Contacts_Response(String sHTML) {
//----------------< fp_Contacts_Response() >----------------
//* Parse HTML Response
//http://fritz.box/fon_num/fonbook_list.lua
//< check >
        if (sHTML=="") return;
//</ check >


        String sNodes = "";

        try
        {
//< read hmtl to document >
            Document doc = Jsoup.parse(sHTML);
//</ read hmtl to document >

//----< read innerTable >----
            Elements innerTable = doc.select("table") ;
            Elements rows=innerTable.select("tr");
//--< @Loop: Rows >--
            for (Element row : rows) {
//--< @Loop: Cells >--
                Elements cells=row.select("td");
                for (Element cell : cells) {
//< cell >  if(cell
                    if (cell.className().equals("tname") )
                    {sNodes += "\n" + cell.text();}
                    else if (cell.className().equals("tnum") )
                    {sNodes += " " + cell.text();}
//</ cell >
                }
//--</ @Loop: Cells >--
            }
//--</ @Loop: Rows >--
//----</ read innerTable >----
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




//----------------</ fp_Contacts_Response() >----------------
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mandot_splash_screen);
        SharedPreferences.Editor editors = Util.getPrefs(getApplicationContext()).edit();
        editors.putString("AppName", APPNAME);
        editors.commit();
        //fp_Contacts_Response("<body><div style=\" background-color:rgba(237,211,212,1.00); height:500px; margin-left:auto; margin-top:10%; padding-right:10px;\" align=\"center\"><div align=\"center\" style=\"text-align:center; height:100px; padding-top:10px;\"><h1>Thank You </h1></div><table cellpadding=\"5\" border=\"2px solid black\" align=\"center\"><tbody><tr><td width=\"30%\">MMP TXN</td><td width=\"30%\">11000058843477</td></tr><tr><td>Bank TXN</td><td>129981313494</td></tr><tr><td>Amount</td><td>55.00</td></tr><tr><td>Date</td><td>Tue Oct 26 12:37:56 IST 2021</td></tr><tr><td>Status</td><td>Failure</td></tr><tr><td>Bank Name</td><td>HDFC Bank</td></tr><tr><td>Surcharge</td><td>0.00</td></tr><tr><td>Description</td><td>REQUEST AUTHORISATION IS DECLINED</td></tr></tbody></table></div></body>");


        streamController = new StreamingController();
        orderStreamingController = new OrderStreamingController();
        editor = Util.getPrefs(this).edit();
        editor.putBoolean("LogoutStatus", false);
        editor.commit();
        editor.apply();

        AccountDetails.clearCache(getApplicationContext());


        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent in = getIntent();
        if (in.getIntExtra("isProceedFrom", 0) == NAV_TO_NOTIFICATION_SCREEN) {
            Util.getPrefs(this).edit().putBoolean("Notification", true).apply();
        }
       /* else if(Util.getPrefs(this).getInt("NotificationVal", 0) == 1012)
        {
            Util.getPrefs(this).edit().putBoolean("Notification", true).apply();
        }*/

        GreekLog.loggerSwitch = true;
        // To prevent from creating more than one instance of application.
        if (!isTaskRoot()) {
            finish();
            return;
        }
        // To prevent from creating more than one instance of application.
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        clearDataOnVersionChange();

        setUpController();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            checkNetwork();
        } else {
            GreekDialog.alertDialog(SplashActivity.this, 0, APPNAME, "Application is not supported", "Ok", false, new DialogListener() {

                @Override
                public void alertDialogAction(Action action, Object... data) {
                    finish();
                }
            });
        }

        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        username = Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", "");
        pass = Util.getPrefs(this).getString("GREEK_RETAINED_CUST_PASS", "");
        transpass = Util.getPrefs(this).getString("GREEK_RETAINED_CUST_TRANS_PASS", "");


        try {

            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.e("TAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();
                            Log.e("Greekbase", "Fetching FCM registration token" + token);

                            SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                            editor.putString("GCMToken", token);
                            editor.commit();

                            // Log and toast

                            //                        Log.e(TAG, msg);
                            //                        Toast.makeText(GreekBaseActivity.this, token, Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearDataOnVersionChange() {
        int lastVer = Util.getPrefs(this).getInt("appVersionCode", 0);
        if (lastVer == 0 || lastVer < getVersionCode()) {
            Util.getPrefs(this).edit().clear().apply();
        }
        saveVersionCode();
    }

    private void saveVersionCode() {
        Util.getPrefs(this).edit().putInt("appVersionCode", getVersionCode()).apply();
    }

    //TODO for every Release
    private int getVersionCode() {
        int code = 200;
        try {
            PackageInfo manager = getPackageManager().getPackageInfo(getPackageName(), 0);
            code = manager.versionCode;
        } catch (NameNotFoundException ignored) {
        }
        return code;
    }

    private void setUpController() {
        GreekConfig.setAllConfigDataFromFiles(this);
        serviceResponseHandler = new ServiceResponseHandler(this, this);
    }

    private void checkNetwork() {
        // Config request is put every single time when there is network
        if (GreekStatistics.isNetworkEnabled(this)) {
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //Check type of intent filter
                    if (intent.getAction().equals(NotifyRegistrationIntentService.REGISTRATION_SUCCESS)) {
                        //Registration success

                        String token = intent.getStringExtra("token");
                        SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                        editor.putString("GCMToken", token);
                        editor.commit();

                        //Toast.makeText(getApplication(),token,Toast.LENGTH_LONG).show();
                    } else if (intent.getAction().equals(NotifyRegistrationIntentService.REGISTRATION_ERROR)) {
                    } else {
                        //Tobe define
                    }
                }
            };
            //Check status of Google play service in device
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());


            //To Work with FCM Notification, Google play store need to check on User's handset=======================>>>
            if (ConnectionResult.SUCCESS != resultCode) {
                //Check type of error
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                    //So notification
                    GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
                }
            } else {
                //Start service
                Intent itent = new Intent(this, NotifyRegistrationIntentService.class);
                startService(itent);
                LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
                LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                        new IntentFilter(NotifyRegistrationIntentService.REGISTRATION_SUCCESS));
                LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                        new IntentFilter(NotifyRegistrationIntentService.REGISTRATION_ERROR));
            }

         /* int updatecancelval =   Util.getPrefs(this).getInt("UpdateCancel", 0);
            if(updatecancelval == 0) {
                new GetVersionCode().execute();
            }
            else
            {
                sendSplashRequest();
            }*/

            //new GetVersionCode().execute();
            sendSplashRequest();


        } else {
            // No network
            if (count < 2) {
                count++;
                GreekDialog.alertDialog(SplashActivity.this, 0, APPNAME, "Application needs network to proceed", "Retrieve", "Exit", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        if (action == Action.OK) {
                            checkNetwork();
                        } else if (action == Action.CANCEL) {
                            finish();
                        }
                    }
                });
            } else {
                GreekDialog.alertDialog(SplashActivity.this, 0, APPNAME, "Application needs network to proceed", "Exit", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        finish();
                    }
                });
            }
        }
    }

    private void sendSplashRequest() {

        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
        AccountDetails.setDeviceID(deviceId);
        gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");

        serverApiKey = getString(R.string.fcm_api_key);

        try {

            ValidateGuestRequest.sendRequestFlags(SplashActivity.this,deviceId, serviceResponseHandler);

        } catch (Exception e) {
            Log.d("failure", "getFlags failure");
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        AccountDetails.clearCache(getApplicationContext());
        NotificationManager notificationManager = (NotificationManager) SplashActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        if (response instanceof JSONResponse) {
            JSONResponse jsonResponse = (JSONResponse) response;

            String UserType = "";
            Bundle bundle = new Bundle();
            if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GUEST_VALIDATE_SVC_NAME.equals(jsonResponse.getServiceName())) {
                try {

                    ValidateGuestResponse loginResponse = (ValidateGuestResponse) jsonResponse.getResponse();


                    String errorCode = loginResponse.getErrorCode();
                    if (errorCode.equals("1")) {
                        GreekDialog.alertDialog(this, 0, APPNAME, "Your password is expired. Please change the password to continue", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                    Intent intent = new Intent(SplashActivity.this, ChangePasswordActivity.class);
                                    startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                                }
                            }
                        });
                    } else if (errorCode.equals("2")) {
                        GreekDialog.alertDialog(this, 0, APPNAME, "Invalid user or password", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("3")) {
                        GreekDialog.alertDialog(this, 0, APPNAME, "Failure", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("4")) {
                        GreekDialog.alertDialog(this, 0, APPNAME, "Duplicate Login", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("13")) {
                        GreekDialog.alertDialog(this, 0, APPNAME, "Retailer does not exist", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("11") || (errorCode.equals("16"))) {

                        Intent intent = new Intent(SplashActivity.this, GuestLoginActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    } else if (errorCode.equals("14")) {
                        final String package_name = getPackageName();
                        GreekDialog.alertDialog(this, 0, APPNAME, "Version Mismatch.Please Update your Apk", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                    //System.exit(0);
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + package_name)));
                                }
                            }
                        });
                    } else if (errorCode.equals("0")) {

                        if (loginResponse.getValidateGuest().equalsIgnoreCase("true")) {

                            Intent intent = new Intent(SplashActivity.this, GuestLoginActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        } else {
//                        } else {
                            String sessionId = jsonResponse.getSessionId();

                            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

                            AccountDetails.setAccountDetails(this, loginResponse.getClientCode(),
                                    "", "OPEN", deviceId,
                                    loginResponse.getMobile(), "", " ",
                                    loginResponse.getErrorCode(), sessionId, "1", "");


                            SharedPreferences.Editor editors = Util.getPrefs(this).edit();
                            editors.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                            editors.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                            editors.putString("setIris_Port", String.valueOf(loginResponse.getIris_Port()));
                            editors.putString("setArachne_IP", String.valueOf(loginResponse.getArachne_IP()));
                            editors.putString("setApollo_IP", String.valueOf(loginResponse.getApollo_IP()));
                            editors.putString("setIris_IP", String.valueOf(loginResponse.getIris_IP()));
                            editors.commit();
                            editors.apply();

                            AccountDetails.setArachne_Port(loginResponse.getArachne_Port());
                            AccountDetails.setApollo_Port(loginResponse.getApollo_Port());
                            AccountDetails.setIris_Port(loginResponse.getIris_Port());
                            AccountDetails.setArachne_IP(loginResponse.getArachne_IP());
                            AccountDetails.setApollo_IP(loginResponse.getApollo_IP());
                            AccountDetails.setIris_IP(loginResponse.getIris_IP());
                            AccountDetails.setChartSetting(loginResponse.getChartSetting());
                            AccountDetails.setIsStrategyProduct(loginResponse.getIsStrategyProduct());
                            AccountDetails.setIsEDISProduct(loginResponse.getIsEDISProduct());
                            AccountDetails.setIsBackOffice(loginResponse.getIsBOReport());
                            AccountDetails.setIsRedisEnabled(loginResponse.getIsRedisEnabled());






                            String isSameDevice = AccountDetails.getIsSameDevice();
                            String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                            if (isSameDevice.equalsIgnoreCase("false") ||
                                    hasmpatdatalist.equalsIgnoreCase(" ")) {
                                sendWatchListGroupNameRequest();
                            }

                            //TODO: sending request to Iris
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                            AccountDetails.setLogin_user_type("openuser");
                            sendWatchListGroupNameRequest();

                            // streamController.sendStreamingGuestLoginBroadcastRequest(this, deviceId, AccountDetails.getClientCode(this), jsonResponse.getSessionId(), null, null);
                            //  orderStreamingController.sendStreamingGuestLoginOrderRequest(this, deviceId, AccountDetails.getClientCode(this), gcmToken, serverApiKey, jsonResponse.getSessionId());

                            final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.putExtra("AskForMpin", false);

                            if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                                intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                            } else {
                                if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                    intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                                } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                    intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                                } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                                    intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                                } else {
                                    intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                                }
                            }

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            setResult(LOGIN_SUCCESS, intent);
                            startActivity(intent);
                            finish();
                            startService();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (LOGIN_FLAG_GROUP.equals(jsonResponse.getServiceGroup()) || LOGIN_FLAG_NAME.equals(jsonResponse.getServiceName())) {

                try {

                    ValidateGuestResponse loginResponse = (ValidateGuestResponse) jsonResponse.getResponse();

                    AccountDetails.setAccord_Token(loginResponse.getAccord_Token());
                    AccountDetails.setposCode(loginResponse.getPosCode());
                    AccountDetails.setLoginOnceFlag(loginResponse.getValidatePasswordOnce());
                    AccountDetails.setFt_testing_bypass(loginResponse.getFt_testing_bypass());
                    AccountDetails.setChartSetting(loginResponse.getChartSetting());
                    AccountDetails.setIsStrategyProduct(loginResponse.getIsStrategyProduct());
                    AccountDetails.setIsEDISProduct(loginResponse.getIsEDISProduct());
                    AccountDetails.setIsBackOffice(loginResponse.getIsBOReport());
                    AccountDetails.setIsRedisEnabled(loginResponse.getIsRedisEnabled());
                    AccountDetails.setApr_version(loginResponse.getApr_version());
                    AccountDetails.setUpiPaymentEnabled(loginResponse.getUpiPaymentEnabled());
                    AccountDetails.setIsPledgeProduct(loginResponse.getIsPledgeProduct());
                    AccountDetails.setPaymentGateway(loginResponse.getPaymentGateway());
                    AccountDetails.setSsl_url(loginResponse.getSsl_url());
                    AccountDetails.setBo_office_url(loginResponse.getBo_office_url());
                    AccountDetails.setFt_offline_url(loginResponse.getFt_offline_url());
                    AccountDetails.setIpo_url(loginResponse.getIpo_url());
                    AccountDetails.setFt_Link(loginResponse.getFt_Link());
                    AccountDetails.setFt_Link_Upi(loginResponse.getFt_Link_Upi());
                    AccountDetails.setPledgeOffline(loginResponse.getPledgeOffline());
                    AccountDetails.setScripCountInWatchlist(loginResponse.getScripCountInWatchlist());
                    AccountDetails.setLogin_compliance(loginResponse.getLogin_compliance());



                    SharedPreferences.Editor editor = Util.getPrefs(SplashActivity.this).edit();
                    editor.putString("accord_Token", loginResponse.getAccord_Token());
                    editor.putString("ValidatePasswordOnce", loginResponse.getValidatePasswordOnce());
                    editor.commit();
                    editor.apply();


                    if (loginResponse.getIsSecure().equalsIgnoreCase("true")) {
                        AccountDetails.setIsSecure("https");
                    } else {
                        AccountDetails.setIsSecure("http");
                    }


                    AccountDetails.setArachne_Port(loginResponse.getArachne_Port());
                    AccountDetails.setApollo_Port(loginResponse.getApollo_Port());
                    AccountDetails.setIris_Port(loginResponse.getIris_Port());
                    AccountDetails.setArachne_IP(loginResponse.getArachne_IP());
                    AccountDetails.setApollo_IP(loginResponse.getApollo_IP());
                    AccountDetails.setIris_IP(loginResponse.getIris_IP());



                    if (!loginResponse.getReconnection_attempts().equalsIgnoreCase("") && !loginResponse.getHeartbeat_Intervals().equalsIgnoreCase("")) {
                        AccountDetails.setReconnection_attempts(loginResponse.getReconnection_attempts());
                        AccountDetails.setHeartbeat_Intervals(loginResponse.getHeartbeat_Intervals());
                    }


                    if (!loginResponse.getShowDescription().equalsIgnoreCase("")) {
                        if (loginResponse.getShowDescription().equalsIgnoreCase("true")) {
                            AccountDetails.setShowDescription(true);
                        } else {
                            AccountDetails.setShowDescription(false);
                        }
                    }

                    if (loginResponse.getValidateTransaction() != null) {

                        if (loginResponse.getValidateTransaction().equalsIgnoreCase("false")) {

                            editor.putBoolean("is_validateTransactionshow", false);
                            editor.apply();
                            editor.commit();

                        } else {

                            editor.putBoolean("is_validateTransactionshow", true);
                            editor.apply();
                            editor.commit();
                        }


                    }

                    if (!loginResponse.getValidateThrough().equalsIgnoreCase("")) {
                        AccountDetails.setValidateThrough(getValidateThrough(loginResponse.getValidateThrough()));
                    }

                    if (loginResponse.getValidatePasswordOnce().equalsIgnoreCase("1")) {

                        if (pass.equalsIgnoreCase("")) {

                            if (loginResponse.getShowLogin().equalsIgnoreCase("true")) {

                                final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                String ClientOtp =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP", "");
                                String ClientOtpUserName =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP_CUST_UNAME", "");
                                if(!ClientOtp.isEmpty() && !ClientOtpUserName.isEmpty() && ClientOtp.contains(username) && ClientOtpUserName.contains(username)){
                                    intent.putExtra("AskForMpin", true);
                                }else{
                                    intent.putExtra("AskForMpin", false);
                                }

                                startActivity(intent);
                                finish();
                            } else {
                                ValidateGuestRequest.sendRequest("", gcmToken, serverApiKey, deviceId, VERSION_NO, "", SplashActivity.this, serviceResponseHandler);
                            }
                        } else {
                            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
                            String manufacturer = getDeviceName();
                            String model = Build.MODEL;
                            int version = Build.VERSION.SDK_INT;
                            String deviceDetails = manufacturer + "-" + model + "-" + version;
                            UserLoginValidationRequest.sendRequest(getString(R.string.login_dummy_dob), deviceId, deviceDetails, "0", pass, transpass, username, "Customer", "1", "0",
                                    VERSION_NO, "", SplashActivity.this, serviceResponseHandler);
                        }
                    } else {
                        if (loginResponse.getShowLogin().equalsIgnoreCase("true")) {

                            final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.putExtra("AskForMpin", false);
                            startActivity(intent);
                            finish();

                        } else {

                            ValidateGuestRequest.sendRequest("", gcmToken, serverApiKey, deviceId, VERSION_NO, "", SplashActivity.this, serviceResponseHandler);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && LOGIN_SVC_NAME.equals(jsonResponse.getServiceName())) {
                try {
                    UserLoginValidationResponse loginResponse = (UserLoginValidationResponse) jsonResponse.getResponse();
                    List<String> mandateIdlist = loginResponse.getMandateIdlist();
                    AccountDetails.setMandatIdlist(mandateIdlist);
                    AccountDetails.setDPType(loginResponse.getDPType());

                    String IsSameDevice = loginResponse.getIsSameDevice();
                    AccountDetails.setIsSameDevice(IsSameDevice);

                    if (loginResponse.getIsValidateSecondary().equalsIgnoreCase("true")) {
                        AccountDetails.setValidateTpassFlag(false);
                    } else {
                        AccountDetails.setValidateTpassFlag(true);
                    }

                    SharedPreferences.Editor editor = Util.getPrefs(this).edit();
                    editor.putString("GREEK_RETAINED_CUST_UNAME", username);
                    editor.putString("GREEK_RETAINED_CUST_PASS", pass);
                    editor.putString("GREEK_RETAINED_CLIENT_CODE", loginResponse.getClientCode());


                    if (!transpass.equalsIgnoreCase("")) {
                        editor.putString("GREEK_RETAINED_CUST_TRANS_PASS", transpass);
                    }

                    editor.apply();
                    editor.commit();

                    String PassTypeCode = loginResponse.getPassType();
                    String KYCStatus = loginResponse.getKYCStatus();
                    String panNo = loginResponse.getPanNo();
                    String dob = loginResponse.getDob();
                    String usertype = loginResponse.getUserType();
                    final String brokerid = "1";

                    AccountDetails.setcUserType(usertype);

                    AccountDetails.setUserPAN(getApplicationContext(), panNo);


                    if (PassTypeCode.equalsIgnoreCase("0")) {
                        String errorCode = loginResponse.getErrorCode();

                        if (loginResponse.getExecutionCode() != null && loginResponse.getExecutionCode().equals("0")) {

                            if (errorCode.equals("21")) {


                                String domainName = loginResponse.getDomainName();
                                int domainPort = Integer.parseInt(loginResponse.getDomainPort());
                                String BaseURL = "";

                                if (loginResponse.getIsSecure().equalsIgnoreCase("true")) {

                                    BaseURL = "https" + "://" + domainName + ":" + domainPort;

                                } else {

                                    BaseURL = "http" + "://" + domainName + ":" + domainPort;

                                }
                                deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk

                                String manufacturer = getDeviceName();
                                String model = Build.MODEL;
                                int version = Build.VERSION.SDK_INT;
                                String deviceDetails = manufacturer + "-" + model + "-" + version;

                                UserLoginValidationRequest.sendRequest(getString(R.string.login_dummy_dob), deviceId, deviceDetails, "0", pass, transpass, username, "Customer", "1", "0",
                                        VERSION_NO, BaseURL, SplashActivity.this, serviceResponseHandler);


                            } else if (!errorCode.equals("0")) {


                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                intent.putExtra("AskForMpin", false);
                                startActivity(intent);
                                finish();
                            } else if (errorCode.equals("0")) {


                                if (loginResponse.getUserType().equalsIgnoreCase("2")) {
                                    UserType = "CUSTOMER";
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.CUSTOMER;
                                    AccountDetails.setLogin_user_type("customer");
                                } else if (loginResponse.getUserType().equalsIgnoreCase("1")) {
                                    UserType = "MFCUSTOMER";
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.MFCUSTOMER;
                                    AccountDetails.setLogin_user_type("mfcustomer");
                                } else if (loginResponse.getUserType().equalsIgnoreCase("0")) {
                                    UserType = "IBTCUSTOMER";
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.IBTCUSTOMER;
                                    AccountDetails.setLogin_user_type("ibtcustomer");
                                }

                                SharedPreferences.Editor editors = Util.getPrefs(this).edit();
                                editors.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                                editors.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                                editors.putString("setIris_Port", String.valueOf(loginResponse.getIris_Port()));
                                editors.putString("setArachne_IP", String.valueOf(loginResponse.getArachne_IP()));
                                editors.putString("setApollo_IP", String.valueOf(loginResponse.getApollo_IP()));
                                editors.putString("setIris_IP", String.valueOf(loginResponse.getIris_IP()));
                                editors.commit();
                                editors.apply();

                                AccountDetails.setArachne_Port(loginResponse.getArachne_Port());
                                AccountDetails.setApollo_Port(loginResponse.getApollo_Port());
                                AccountDetails.setIris_Port(loginResponse.getIris_Port());
                                AccountDetails.setArachne_IP(loginResponse.getArachne_IP());
                                AccountDetails.setApollo_IP(loginResponse.getApollo_IP());
                                AccountDetails.setIris_IP(loginResponse.getIris_IP());
                                AccountDetails.setChartSetting(loginResponse.getChartSetting());
                                AccountDetails.setIsStrategyProduct(loginResponse.getIsStrategyProduct());
                                AccountDetails.setIsEDISProduct(loginResponse.getIsEDISProduct());
                                AccountDetails.setIsBackOffice(loginResponse.getIsBOReport());
                                AccountDetails.setIsRedisEnabled(loginResponse.getIsRedisEnabled());

                                AccountDetails.setDefaultProducttype_name(loginResponse.getDefaultProduct());


                                if (loginResponse.getDefaultProduct().equalsIgnoreCase("intraday")) {
                                    AccountDetails.setProductTypeFlag("1");
                                } else if (loginResponse.getDefaultProduct().equalsIgnoreCase("MTF")) {
                                    AccountDetails.setProductTypeFlag("2");
                                } else if (loginResponse.getDefaultProduct().equalsIgnoreCase("SSEQ")) {
                                    AccountDetails.setProductTypeFlag("3");
                                } else {
                                    AccountDetails.setProductTypeFlag("0");

                                }


                                if (loginResponse.getHoldingFlag().equalsIgnoreCase("true")) {
                                    AccountDetails.setDematFlag(true);
                                } else if (loginResponse.getHoldingFlag().equalsIgnoreCase("false")) {
                                    AccountDetails.setDematFlag(false);
                                } else {
                                    AccountDetails.setDematFlag(true);
                                }
                                sessionId = jsonResponse.getSessionId();

                                String userName = loginResponse.getUserName();


                                String themeFlag = Util.getPrefs(this).getString("THEME_FLAG", "black");
                                if (loginResponse.getTheme().equalsIgnoreCase("DarkTheme")) {
                                    AccountDetails.setThemeflag("black");
                                } else {
                                    AccountDetails.setThemeflag("white");
                                }

                                if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {

                                    deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                                    AccountDetails.setAccountDetails(SplashActivity.this, loginResponse.getClientCode(),
                                            userName, UserType, deviceId,
                                            "", "", loginResponse.getExecutionCode(),
                                            loginResponse.getErrorCode(), sessionId, brokerid, loginResponse.getClientName());

                                    String isSameDevice = AccountDetails.getIsSameDevice();
                                    String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                                    if (isSameDevice.equalsIgnoreCase("false") ||
                                            hasmpatdatalist.equalsIgnoreCase(" ")) {
                                        sendWatchListGroupNameRequest();
                                    }

                                    AccountDetails.allowedmarket_nse = false;
                                    AccountDetails.allowedmarket_nfo = false;
                                    AccountDetails.allowedmarket_ncd = false;
                                    AccountDetails.allowedmarket_bse = false;
                                    AccountDetails.allowedmarket_bfo = false;
                                    AccountDetails.allowedmarket_bcd = false;
                                    AccountDetails.allowedmarket_ncdex = false;
                                    AccountDetails.allowedmarket_mcx = false;

                                    for (int i = 0; i < loginResponse.getAllowedMarket().size(); i++) {
                                        if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("1")) {
                                            AccountDetails.allowedmarket_nse = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("2")) {
                                            AccountDetails.allowedmarket_nfo = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("3")) {
                                            AccountDetails.allowedmarket_ncd = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("4")) {
                                            AccountDetails.allowedmarket_bse = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("5")) {
                                            AccountDetails.allowedmarket_bfo = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("6")) {
                                            AccountDetails.allowedmarket_bcd = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("7")) {
                                            AccountDetails.allowedmarket_ncdex = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("9")) {
                                            AccountDetails.allowedmarket_mcx = true;
                                        }
                                    }

                                    AccountDetails.orderTimeFlag = true;
                                    AccountDetails.setOrdTime(loginResponse.getOrderTime());
                                    SharedPreferences.Editor editor1 = Util.getPrefs(this).edit();
                                    editor1.putString("orderTime", loginResponse.getOrderTime());
                                    editor1.putBoolean("orderTimeFlag", true);
                                    editor1.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                                    editor1.putString("GREEK_RETAINED_CUST_UNAME", userName);
                                    editor1.putString("GREEK_RETAINED_CUST_PASS", pass);
                                    editor1.putString("GREEK_RETAINED_BROKER_ID", brokerid);

                                    editor1.apply();
                                    editor1.commit();
                                    startService();

                                    // streamController.sendStreamingLoginRequest(this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this), null, null);
                                    //  orderStreamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(this), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this));

                                    SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", SplashActivity.this, serviceResponseHandler);
                                    final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    String ClientOtp =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP", "");
                                    String ClientOtpUserName =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP_CUST_UNAME", "");
                                    if(!ClientOtp.isEmpty() && !ClientOtpUserName.isEmpty() && ClientOtp.contains(username) && ClientOtpUserName.contains(username)){
                                        intent.putExtra("AskForMpin", true);
                                    }else{
                                        intent.putExtra("AskForMpin", false);
                                    }

                                    if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                                        intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                                    } else {
                                        if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                            intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                                        } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                            intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                                        } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                                            intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                                        } else {
                                            intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                                        }
                                    }

                                    setResult(LOGIN_SUCCESS, intent);
                                    startActivity(intent);
                                    // new HeartBeatService(SplashActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this), AccountDetails.getClientCode(this)).start();
                                    finish();

                                } else {

                                    if (loginResponse.getQuestionsList() != null) {
                                        GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                        AccountDetails.setLogin_user_type("openuser");
                                        List<Questions> question = loginResponse.getQuestionsList();
                                        final Intent intent = new Intent(SplashActivity.this, LoginQuestionsActivity.class);
                                        bundle.putSerializable("response", (Serializable) question);
                                        intent.putExtras(bundle);
                                        intent.putExtra("sessionId", sessionId);
                                        intent.putExtra("brokerId", brokerid);
                                        intent.putExtra("flag", loginResponse.getUserFlag());
                                        intent.putExtra("userpass", pass);
                                        intent.putExtra("usercode", userName);
                                        intent.putExtra("gcid", loginResponse.getClientCode());
                                        intent.putExtra("userType", loginResponse.getUserType());
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                                        finish();
                                    }

                                }


                            }
                        } else {
                            GreekDialog.alertDialog(this, 0, APPNAME, getString(R.string.CP_LOGIN_ERROR_MSG), "Ok", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
                                    }
                                }
                            });
                        }

                        if (KYCStatus.equalsIgnoreCase("N")) {
                            AccountDetails.setMfOrderAllowed(false);

                        } else if (KYCStatus.equalsIgnoreCase("Y")) {
                            AccountDetails.setMfOrderAllowed(true);

                        }

                    } else if (PassTypeCode.equalsIgnoreCase("1")) {
                        String errorCode = loginResponse.getErrorCode();

                        if (loginResponse.getExecutionCode() != null && loginResponse.getExecutionCode().equals("0")) {
                            if (!errorCode.equals("0")) {


                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                intent.putExtra("AskForMpin", false);

                                startActivity(intent);
                                finish();
                            } else if (errorCode.equals("0")) {

                                if (loginResponse.getUserType().equalsIgnoreCase("2")) {
                                    UserType = "CUSTOMER";
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.CUSTOMER;
                                    AccountDetails.setLogin_user_type("customer");
                                } else if (loginResponse.getUserType().equalsIgnoreCase("1")) {
                                    UserType = "MFCUSTOMER";
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.MFCUSTOMER;
                                    AccountDetails.setLogin_user_type("mfcustomer");
                                } else if (loginResponse.getUserType().equalsIgnoreCase("0")) {
                                    UserType = "IBTCUSTOMER";
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.IBTCUSTOMER;
                                    AccountDetails.setLogin_user_type("ibtcustomer");
                                }

                                SharedPreferences.Editor editors = Util.getPrefs(this).edit();
                                editors.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                                editors.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                                editors.putString("setIris_Port", String.valueOf(loginResponse.getIris_Port()));
                                editors.putString("setArachne_IP", String.valueOf(loginResponse.getArachne_IP()));
                                editors.putString("setApollo_IP", String.valueOf(loginResponse.getApollo_IP()));
                                editors.putString("setIris_IP", String.valueOf(loginResponse.getIris_IP()));
                                editors.commit();
                                editors.apply();

                                AccountDetails.setArachne_Port(loginResponse.getArachne_Port());
                                AccountDetails.setApollo_Port(loginResponse.getApollo_Port());
                                AccountDetails.setIris_Port(loginResponse.getIris_Port());
                                AccountDetails.setArachne_IP(loginResponse.getArachne_IP());
                                AccountDetails.setApollo_IP(loginResponse.getApollo_IP());
                                AccountDetails.setIris_IP(loginResponse.getIris_IP());

                                AccountDetails.setValidateTransaction(true);
                                AccountDetails.setDefaultProducttype_name(loginResponse.getDefaultProduct());

                                if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {
                                    deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                    String userName = loginResponse.getUserName();
                                    if (userName == null || userName.trim().length() == 0) {
                                        userName = username;
                                    }

                                    AccountDetails.setAccountDetails(SplashActivity.this, loginResponse.getClientCode(),
                                            userName, UserType, deviceId,
                                            "", "", loginResponse.getExecutionCode(),
                                            loginResponse.getErrorCode(), sessionId, brokerid, loginResponse.getClientName());


                                    String isSameDevice = AccountDetails.getIsSameDevice();
                                    String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                                    if (isSameDevice.equalsIgnoreCase("false") ||
                                            hasmpatdatalist.equalsIgnoreCase(" ")) {
                                        sendWatchListGroupNameRequest();
                                    }

                                    AccountDetails.allowedmarket_nse = false;
                                    AccountDetails.allowedmarket_nfo = false;
                                    AccountDetails.allowedmarket_ncd = false;
                                    AccountDetails.allowedmarket_bse = false;
                                    AccountDetails.allowedmarket_bfo = false;
                                    AccountDetails.allowedmarket_bcd = false;
                                    AccountDetails.allowedmarket_ncdex = false;
                                    AccountDetails.allowedmarket_mcx = false;

                                    for (int i = 0; i < loginResponse.getAllowedMarket().size(); i++) {
                                        if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("1")) {
                                            AccountDetails.allowedmarket_nse = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("2")) {
                                            AccountDetails.allowedmarket_nfo = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("3")) {
                                            AccountDetails.allowedmarket_ncd = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("4")) {
                                            AccountDetails.allowedmarket_bse = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("5")) {
                                            AccountDetails.allowedmarket_bfo = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("6")) {
                                            AccountDetails.allowedmarket_bcd = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("7")) {
                                            AccountDetails.allowedmarket_ncdex = true;
                                        } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("9")) {
                                            AccountDetails.allowedmarket_mcx = true;
                                        }
                                    }

                                    AccountDetails.orderTimeFlag = true;
                                    AccountDetails.setOrdTime(loginResponse.getOrderTime());
                                    SharedPreferences.Editor editor2 = Util.getPrefs(this).edit();
                                    editor2.putString("orderTime", loginResponse.getOrderTime());
                                    editor2.putBoolean("orderTimeFlag", true);
                                    editor2.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                                    editor2.putString("GREEK_RETAINED_CUST_UNAME", username);
                                    editor2.putString("GREEK_RETAINED_CUST_PASS", pass);
                                    editor2.putString("GREEK_RETAINED_BROKER_ID", brokerid);
                                    editor2.apply();
                                    editor2.commit();
                                    startService();

                                    //streamController.sendStreamingLoginRequest(this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this), null, null);
                                    // orderStreamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(this), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this));
                                    gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                                    SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", SplashActivity.this, serviceResponseHandler);


                                    final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    String ClientOtp =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP", "");
                                    String ClientOtpUserName =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP_CUST_UNAME", "");
                                    if(!ClientOtp.isEmpty() && !ClientOtpUserName.isEmpty() && ClientOtp.contains(username) && ClientOtpUserName.contains(username)){
                                        intent.putExtra("AskForMpin", true);
                                    }else{
                                        intent.putExtra("AskForMpin", false);
                                    }

                                    if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                                        intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                                    } else {
                                        if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                            intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                                        } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                            intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                                        } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                                            intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                                        } else {
                                            intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                                        }
                                    }

                                    setResult(LOGIN_SUCCESS, intent);
                                    startActivity(intent);
                                    //new HeartBeatService(SplashActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this), AccountDetails.getClientCode(this)).start();
                                    finish();

                                } else {

                                    if (loginResponse.getQuestionsList() != null) {
                                        GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                        AccountDetails.setLogin_user_type("openuser");
                                        List<Questions> question = loginResponse.getQuestionsList();
                                        final Intent intent = new Intent(SplashActivity.this, LoginQuestionsActivity.class);
                                        bundle.putSerializable("response", (Serializable) question);
                                        intent.putExtras(bundle);
                                        intent.putExtra("sessionId", sessionId);
                                        intent.putExtra("brokerId", brokerid);
                                        intent.putExtra("flag", loginResponse.getUserFlag());
                                        intent.putExtra("userpass", pass);
                                        intent.putExtra("usercode", username);
                                        intent.putExtra("gcid", loginResponse.getClientCode());
                                        intent.putExtra("userType", loginResponse.getUserType());

                                        startActivity(intent);
                                        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                                        finish();
                                    }

                                }


                            }
                        }

                        if (KYCStatus.equalsIgnoreCase("N")) {

                        } else if (KYCStatus.equalsIgnoreCase("Y")) {

                            AccountDetails.setMfOrderAllowed(true);

                        }
                    } else if (PassTypeCode.equalsIgnoreCase("")) {

                        if (loginResponse.getUserType().equalsIgnoreCase("2")) {
                            UserType = "CUSTOMER";
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.CUSTOMER;
                            AccountDetails.setLogin_user_type("customer");
                        } else if (loginResponse.getUserType().equalsIgnoreCase("1")) {
                            UserType = "MFCUSTOMER";
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.MFCUSTOMER;
                            AccountDetails.setLogin_user_type("mfcustomer");
                        } else if (loginResponse.getUserType().equalsIgnoreCase("0")) {
                            UserType = "IBTCUSTOMER";
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.IBTCUSTOMER;
                            AccountDetails.setLogin_user_type("ibtcustomer");
                        }


                        SharedPreferences.Editor editors = Util.getPrefs(this).edit();
                        editors.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                        editors.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                        editors.putString("setIris_Port", String.valueOf(loginResponse.getIris_Port()));
                        editors.putString("setArachne_IP", String.valueOf(loginResponse.getArachne_IP()));
                        editors.putString("setApollo_IP", String.valueOf(loginResponse.getApollo_IP()));
                        editors.putString("setIris_IP", String.valueOf(loginResponse.getIris_IP()));
                        editors.commit();
                        editors.apply();

                        AccountDetails.setArachne_Port(loginResponse.getArachne_Port());
                        AccountDetails.setApollo_Port(loginResponse.getApollo_Port());
                        AccountDetails.setIris_Port(loginResponse.getIris_Port());
                        AccountDetails.setArachne_IP(loginResponse.getArachne_IP());
                        AccountDetails.setApollo_IP(loginResponse.getApollo_IP());
                        AccountDetails.setIris_IP(loginResponse.getIris_IP());

                        AccountDetails.setDefaultProducttype_name(loginResponse.getDefaultProduct());


                        if (loginResponse.getDefaultProduct().equalsIgnoreCase("intraday")) {
                            AccountDetails.setProductTypeFlag("1");
                        } else if (loginResponse.getDefaultProduct().equalsIgnoreCase("MTF")) {
                            AccountDetails.setProductTypeFlag("2");
                        } else if (loginResponse.getDefaultProduct().equalsIgnoreCase("SSEQ")) {
                            AccountDetails.setProductTypeFlag("3");
                        } else {
                            AccountDetails.setProductTypeFlag("0");

                        }

                        if (loginResponse.getHoldingFlag().equalsIgnoreCase("true")) {
                            AccountDetails.setDematFlag(true);
                        } else if (loginResponse.getHoldingFlag().equalsIgnoreCase("false")) {
                            AccountDetails.setDematFlag(false);
                        } else {
                            AccountDetails.setDematFlag(true);
                        }
                        sessionId = jsonResponse.getSessionId();

                        String userName = loginResponse.getUserName();
                        if (userName == null || userName.trim().length() == 0) {
                            userName = username;
                        }

                        String themeFlag = Util.getPrefs(this).getString("THEME_FLAG", "black");
                        if (loginResponse.getTheme().equalsIgnoreCase("DarkTheme")) {
                            AccountDetails.setThemeflag("black");
                        } else {
                            AccountDetails.setThemeflag("white");
                        }

                        if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {

                            deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                            AccountDetails.setAccountDetails(SplashActivity.this, loginResponse.getClientCode(),
                                    userName, UserType, deviceId,
                                    "", "", loginResponse.getExecutionCode(),
                                    loginResponse.getErrorCode(), sessionId, brokerid, loginResponse.getClientName());

                            String isSameDevice = AccountDetails.getIsSameDevice();
                            String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                            if (isSameDevice.equalsIgnoreCase("false") ||
                                    hasmpatdatalist.equalsIgnoreCase(" ")) {
                                sendWatchListGroupNameRequest();
                            }

                            AccountDetails.allowedmarket_nse = false;
                            AccountDetails.allowedmarket_nfo = false;
                            AccountDetails.allowedmarket_ncd = false;
                            AccountDetails.allowedmarket_bse = false;
                            AccountDetails.allowedmarket_bfo = false;
                            AccountDetails.allowedmarket_bcd = false;
                            AccountDetails.allowedmarket_ncdex = false;
                            AccountDetails.allowedmarket_mcx = false;

                            for (int i = 0; i < loginResponse.getAllowedMarket().size(); i++) {
                                if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("1")) {
                                    AccountDetails.allowedmarket_nse = true;
                                } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("2")) {
                                    AccountDetails.allowedmarket_nfo = true;
                                } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("3")) {
                                    AccountDetails.allowedmarket_ncd = true;
                                } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("4")) {
                                    AccountDetails.allowedmarket_bse = true;
                                } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("5")) {
                                    AccountDetails.allowedmarket_bfo = true;
                                } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("6")) {
                                    AccountDetails.allowedmarket_bcd = true;
                                } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("7")) {
                                    AccountDetails.allowedmarket_ncdex = true;
                                } else if (loginResponse.getAllowedMarket().get(i).getMarket_id().equals("9")) {
                                    AccountDetails.allowedmarket_mcx = true;
                                }
                            }

                            AccountDetails.orderTimeFlag = true;
                            AccountDetails.setOrdTime(loginResponse.getOrderTime());
                            SharedPreferences.Editor editor3 = Util.getPrefs(this).edit();
                            editor3.putString("orderTime", loginResponse.getOrderTime());
                            editor3.putBoolean("orderTimeFlag", true);
                            editor3.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                            editor3.putString("GREEK_RETAINED_CUST_UNAME", userName);
                            editor3.putString("GREEK_RETAINED_CUST_PASS", pass);
                            editor3.putString("GREEK_RETAINED_BROKER_ID", brokerid);
                            editor3.apply();
                            editor3.commit();

                            startService();

                            gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");

                            //For now user need to verify Mpin after direct login, so now no need to connect sockets
                            //streamController.sendStreamingLoginRequest(this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this), null, null);
                            //orderStreamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(this), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this));
                            SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", SplashActivity.this, serviceResponseHandler);
                            final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                            String ClientOtp =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP", "");
                            String ClientOtpUserName =  Util.getPrefs(this).getString("GREEK_RETAINED_LOGIN_OTP_CUST_UNAME", "");
                            if(!AccountDetails.getLogin_compliance().isEmpty()) {
                                if (Integer.parseInt(AccountDetails.getLogin_compliance()) > 0) {
                                    if (!ClientOtp.isEmpty() && !ClientOtpUserName.isEmpty() && ClientOtp.contains(username) && ClientOtpUserName.contains(username)) {
                                        intent.putExtra("AskForMpin", true);
                                    } else {
                                        intent.putExtra("AskForMpin", false);
                                    }
                                } else {
                                    intent.putExtra("AskForMpin", true);
                                }
                                if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                                    intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                                } else {
                                    if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                        intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                                        intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                                    } else {
                                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                                    }
                                }

                                setResult(LOGIN_SUCCESS, intent);
                                startActivity(intent);

                                //For now user need to verify Mpin after direct login, so now no need to connect heartbeats services.
                                // new HeartBeatService(SplashActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this), AccountDetails.getClientCode(this)).start();
                                finish();
                            }else{
                                intent.putExtra("AskForMpin", true);
                                if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                                    intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                                } else {
                                    if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                        intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                                        intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                                    } else {
                                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                                    }
                                }

                                setResult(LOGIN_SUCCESS, intent);
                                startActivity(intent);

                                //For now user need to verify Mpin after direct login, so now no need to connect heartbeats services.
                                // new HeartBeatService(SplashActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this), AccountDetails.getClientCode(this)).start();
                                finish();

                                if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                                    intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                                } else {
                                    if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                        intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                                        intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                                    } else {
                                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                                    }
                                }

                                setResult(LOGIN_SUCCESS, intent);
                                startActivity(intent);

                                //For now user need to verify Mpin after direct login, so now no need to connect heartbeats services.
                                // new HeartBeatService(SplashActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this), AccountDetails.getClientCode(this)).start();
                                finish();

                            }
                        } else {

                            if (loginResponse.getQuestionsList() != null) {
                                GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                AccountDetails.setLogin_user_type("openuser");
                                List<Questions> question = loginResponse.getQuestionsList();
                                final Intent intent = new Intent(SplashActivity.this, LoginQuestionsActivity.class);
                                bundle.putSerializable("response", (Serializable) question);
                                intent.putExtras(bundle);
                                intent.putExtra("sessionId", sessionId);
                                intent.putExtra("brokerId", brokerid);
                                intent.putExtra("flag", loginResponse.getUserFlag());
                                intent.putExtra("userpass", pass);
                                intent.putExtra("usercode", userName);
                                intent.putExtra("gcid", loginResponse.getClientCode());
                                intent.putExtra("userType", loginResponse.getUserType());
                                startActivity(intent);


                                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                                finish();
                            }

                        }

                        if (KYCStatus.equalsIgnoreCase("N")) {

                            //PanRequest.sendRequestPanLogin(AccountDetails.getUsername(getApplicationContext()), panNo, dob, "9588333616", usertype, LoginActivity.this, serviceResponseHandler);


                        } else if (KYCStatus.equalsIgnoreCase("Y")) {

                            AccountDetails.setMfOrderAllowed(true);

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_GROUP_NAME_SVC_NAME.equals(jsonResponse.getServiceName())) {

                try {
                    SharedPreferences.Editor editor1 = Util.getPrefs(this).edit();
                    editor1.putString("WatchlistGroupsNew", jsonResponse.toString());
                    editor1.apply();
                    editor1.commit();
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

        }
    }


    private void sendWatchListGroupNameRequest() {
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            WatchlistGroupRequest.sendRequest(AccountDetails.getDeviceID(getApplicationContext()), getApplicationContext(), serviceResponseHandler);
        } else {
            WatchlistGroupRequest.sendRequest(AccountDetails.getUsername(getApplicationContext()), getApplicationContext(), serviceResponseHandler);
        }

    }

    private void startService() {

        Intent i = new Intent(this, MyService.class);
        startService(i);
    }

    private String getValidateThrough(String flag) {
        int flagInt = Integer.valueOf(flag);
        String returntype = "";
        if (flagInt == 0) {
            returntype = "email";
        }

        if (flagInt == 1) {
            returntype = "mobile";
        }
        if (flagInt == 2) {
            returntype = "both";
        }
        return returntype;
    }


    @Override
    protected void onActivityResult(int requestCode, int resCode, Intent arg2) {
        super.onActivityResult(requestCode, resCode, arg2);
        switch (resCode) {
            case SKIP_UPDATE_CODE:
                break;
            case EXIT_CODE:
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (networkChangeReceiver != null) {
            try {
                unregisterReceiver(networkChangeReceiver);
            } catch (Exception ignored) {
            } finally {
                networkChangeReceiver = null;
            }
        }
        super.onDestroy();
    }

    @Override
    public void handleError(int errorCode, final String message, Object error, ServiceRequest serviceRequest) {

        GreekDialog.alertDialog(SplashActivity.this, 0, APPNAME, message, "Retry", "Exit", false, new DialogListener() {

            @Override
            public void alertDialogAction(Action action, Object... data) {
                if (action == Action.OK) {
                    checkNetwork();
                } else if (action == Action.CANCEL) {
                    finish();
                }
            }
        });
    }

    @Override
    public void infoDialog(int action, final String msg, JSONResponse jsonResponse) {
        if (action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(this, 0, APPNAME, msg, "OK", true, new DialogListener() {
                @Override
                public void alertDialogAction(Action action, Object... data) {
                    if (action == Action.OK) if (msg.contains("Invalid Login")) {
                        SharedPreferences.Editor editor = Util.getPrefs(SplashActivity.this).edit();
                        editor.putString("GREEK_RETAINED_CUST_PASS", "");
                        editor.apply();
                    }
                }
            });
        }
    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {

    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {

    }


    class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashActivity.this.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }


        @Override

        protected void onPostExecute(final String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                String[] onlineVersionArr = onlineVersion.split("\\.");
                String[] currentVersionArr = currentVersion.split("\\.");

                int value = 0;
                for (int i = 0; i < onlineVersionArr.length; i++) {
                    if (Integer.valueOf(onlineVersionArr[i]) > Integer.valueOf(currentVersionArr[i])) {
                        value = 1;
                        break;
                    }
                }

                int updatecancelval = Util.getPrefs(getApplicationContext()).getInt("UpdateCancel", 0);
                String updateVersion = Util.getPrefs(getApplicationContext()).getString("UpdateVersion", "");

                if (!updateVersion.equalsIgnoreCase(onlineVersion)) {
                    if (value == 1) {

                        if (updatecancelval == 0) {


                            GreekDialog.alertDialog(SplashActivity.this, 0, APPNAME, "New Update is available on playstore", "Update", "Cancel", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
                                        Util.getPrefs(getApplicationContext()).edit().putInt("UpdateCancel", 0).apply();
                                        Util.getPrefs(getApplicationContext()).edit().putString("UpdateVersion", onlineVersion).apply();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + SplashActivity.this.getPackageName() + "&hl=en")));
                                    } else if (action == Action.CANCEL) {
                                        Util.getPrefs(getApplicationContext()).edit().putInt("UpdateCancel", 1).apply();
                                        Util.getPrefs(getApplicationContext()).edit().putString("UpdateVersion", onlineVersion).apply();
                                        sendSplashRequest();
                                    }
                                }
                            });
                        } else {
                            sendSplashRequest();
                        }
                    } else {
                        sendSplashRequest();
                    }
                } else {
                    sendSplashRequest();
                }


            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }
}


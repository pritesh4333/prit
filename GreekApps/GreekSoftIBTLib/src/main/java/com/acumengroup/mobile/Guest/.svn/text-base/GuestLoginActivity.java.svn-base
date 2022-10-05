package com.acumengroup.mobile.Guest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.userloginvalidation.GuestLoginRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.GuestLoginResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.OTPRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.OTPResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.login.LoginActivity;
import com.acumengroup.mobile.service.MyService;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.greekmain.core.network.WSHandler.VERSION_NO;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by Sushant on 9/19/2016.
 */
public class GuestLoginActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants, View.OnClickListener {

    private GreekBaseActivity greekBaseActivity;
    private ServiceResponseHandler serviceResponseHandler;
    private LinearLayout progressLayout, openAcctLayout;
    private GreekEditText userMobileText, userEmailText;
    private GreekButton loginBtn;
    private String mobileNo, gcmToken, serverApiKey, deviceId, emailId;
    private String from = "";
    private GreekEditText otp_edittext;
    private GreekTextView txt_resend_otp;
    private AlertDialog levelDialog;
    private static CountDownTimer countDownTimer;
    private StreamingController streamingController;
    private OrderStreamingController orderStreamingController;
    private RelativeLayout transactionlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_login);
        try {
            //TODO: to setup view components
            setupView();
            greekBaseActivity = new GreekBaseActivity();
            streamingController = new StreamingController();
            orderStreamingController = new OrderStreamingController();
            greekBaseActivity = new GreekBaseActivity();
            progressLayout = findViewById(R.id.customProgress);
            openAcctLayout = findViewById(R.id.OpenAcctTxt);
            Intent in = getIntent();
            from = in.getStringExtra("from");
            if (from.equalsIgnoreCase("openAcct")) {
                openAcctLayout.setVisibility(View.VISIBLE);
            } else {
                openAcctLayout.setVisibility(View.GONE);
            }
            if (in != null) {
                String fromActivity = in.getStringExtra("from");
                if (fromActivity != null) {
                    if (fromActivity.equalsIgnoreCase("heartBeat")) {
                        AccountDetails.setIsApolloConnected(false);
                        AccountDetails.setIsIrisConnected(false);
                        AccountDetails.clearCache(getApplicationContext());
                        GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                        AccountDetails.setLogin_user_type("openuser");
                        GreekDialog.alertDialog(this, 0, GREEK, "Invalid Session.Heart Beat stopped", "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                }
                            }
                        });
                        greekBaseActivity.onLogout(0);
                    }
                }
            }
        } catch (Exception e) {
            //generateNoteOnSd(e.toString() + "-");
        }
    }

    private void setupView() {
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        userMobileText = findViewById(R.id.userMobile_text);
        userEmailText = findViewById(R.id.userEmail_text);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence input = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void generateNoteOnSd(String error) {
        try {
            Random random = new Random();
            int value = random.nextInt();
            String h = String.valueOf(value);
            File root = new File(Environment.getExternalStorageDirectory(), "Logs");
            if (!root.exists()) {
                root.mkdir();
            }
            File filepath = new File(root, h + ".txt");
            FileWriter writer = new FileWriter(filepath);
            writer.append(error);
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        Bundle bundle = new Bundle();
//        progressLayout.setVisibility(View.GONE);

        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GUEST_REGISTER_SVC_NAME.equals(jsonResponse.getServiceName())) {
            loginBtn.setClickable(true);
            loginBtn.setBackgroundColor(getResources().getColor(R.color.blue_dark));
            GuestLoginResponse guestLoginResponse;
            try {
                guestLoginResponse = (GuestLoginResponse) jsonResponse.getResponse();
                String errorCode = guestLoginResponse.getErrorCode();

                //TODO: handle errors
                if (errorCode.equals("20")) {
                    GreekDialog.alertDialog(this, 0, GREEK, "Unauthorized Guest Email-ID", "Ok", true, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });

                } else if (errorCode.equals("15")) {
                    GreekDialog.alertDialog(this, 0, GREEK, "Unauthorized Guest Mobile Number", "Ok", true, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });

                } else if (errorCode.equals("0")) {


                    AccountDetails.setArachne_Port(guestLoginResponse.getArachne_Port());
                    AccountDetails.setApollo_Port(guestLoginResponse.getApollo_Port());
                    AccountDetails.setIris_Port(guestLoginResponse.getIris_Port());
                    AccountDetails.setArachne_IP(guestLoginResponse.getArachne_IP());
                    AccountDetails.setApollo_IP(guestLoginResponse.getApollo_IP());
                    AccountDetails.setIris_IP(guestLoginResponse.getIris_IP());

                    Toast.makeText(getApplicationContext(), "OTP Sent", Toast.LENGTH_SHORT).show();
                    deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                    AccountDetails.setLogin_user_type("openuser");
                    if(levelDialog!=null){
                        levelDialog.dismiss();
                        levelDialog=null;
                    }
                    View layout = LayoutInflater.from(GuestLoginActivity.this).inflate(R.layout.custom_otp_popup, null);


                    otp_edittext = layout.findViewById(R.id.otp_edittext);
                    transactionlayout = layout.findViewById(R.id.transaction_layout);
                    Button button_submit_otp = layout.findViewById(R.id.button_submit_otp);
                    Button button_cancel_otp = layout.findViewById(R.id.button_cancel_otp);
                    txt_resend_otp = layout.findViewById(R.id.txt_resend_otp);


//                    if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
//                        transactionlayout.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
//                        txt_resend_otp.setTextColor(getResources().getColor(R.color.black));
//                        otp_edittext.setTextColor(getResources().getColor(R.color.black));
//                        otp_edittext.setHintTextColor(getResources().getColor(R.color.black));
//
//                    } else {
//                        transactionlayout.setBackgroundColor(getResources().getColor(R.color.light_black));
//                        txt_resend_otp.setTextColor(getResources().getColor(R.color.white));
//                        otp_edittext.setTextColor(getResources().getColor(R.color.white));
//                        otp_edittext.setHintTextColor(getResources().getColor(R.color.white));
//                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(GuestLoginActivity.this);
                    builder.setView(layout);
                    builder.setCancelable(false);
                    levelDialog = builder.create();
                    if (!levelDialog.isShowing()) {
                        levelDialog.show();
                    }

                    txt_resend_otp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            resendOTPRequest();
                        }
                    });

                    button_submit_otp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            proceedToOTPVerification();

                        }
                    });

                    button_cancel_otp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            levelDialog.dismiss();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GUEST_VALIDATE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            ValidateGuestResponse otpResponse;
            try {
                otpResponse = (ValidateGuestResponse) jsonResponse.getResponse();
                String errorCode = otpResponse.getErrorCode();

                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                serverApiKey = getString(R.string.fcm_api_key);
                String clientCode = otpResponse.getClientCode();

                //TODO: handle errors
                if (errorCode.equals("12")) {
                    Toast.makeText(getApplicationContext(), "Failed To Register", Toast.LENGTH_SHORT).show();
                } else if (errorCode.equals("2")) {
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                } else if (errorCode.equals("16")) {
//                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuestLoginActivity.this, GuestLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if (errorCode.equals("0")) {


                    Toast.makeText(getApplicationContext(), "OTP Successfully", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = Util.getPrefs(GuestLoginActivity.this).edit();
                    editor.putString("guestSuccess", "guestsuccess");
                    editor.commit();
                    editor.apply();
                    AccountDetails.setAccountDetails(this, clientCode, "", "OPEN", deviceId, "", "", "", "", "", "1", "");

                    streamingController.sendStreamingGuestLoginBroadcastRequest(this, deviceId, clientCode, jsonResponse.getSessionId(), null, null);
                    orderStreamingController.sendStreamingGuestLoginOrderRequest(this, deviceId, clientCode, gcmToken, serverApiKey, jsonResponse.getSessionId());

                    final Intent intent = new Intent(GuestLoginActivity.this, GreekBaseActivity.class);

                    if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {
                        intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);
                    } else {
                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    setResult(LOGIN_SUCCESS, intent);
                    startActivity(intent);
                    finish();
                    startService();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GUEST_GENERATE_PASSWORD_SVC_NAME.equals(jsonResponse.getServiceName())) {
            OTPResponse resendOTPResponse;
            txt_resend_otp.setClickable(true);
            if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                txt_resend_otp.setTextColor(getResources().getColor(R.color.black));
            } else {
                txt_resend_otp.setTextColor(getResources().getColor(R.color.white));
            }
            try {

                resendOTPResponse = (OTPResponse) jsonResponse.getResponse();
                String errorCode = resendOTPResponse.getErrorCode();
                if (errorCode.equals("0")) {
                    Toast.makeText(getApplicationContext(), "OTP Sent to Your Registered Mobile Number / Email", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void resendOTPRequest() {

//        progressLayout.setVisibility(View.VISIBLE);
        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
        gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
        serverApiKey = getString(R.string.fcm_api_key);
        OTPRequest.sendRequest(mobileNo, deviceId, serverApiKey, gcmToken, GuestLoginActivity.this, serviceResponseHandler);
        txt_resend_otp.setClickable(false);
        txt_resend_otp.setTextColor(getResources().getColor(R.color.black_light));
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(Integer.valueOf(1) * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                txt_resend_otp.setClickable(true);
                if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                    txt_resend_otp.setTextColor(getResources().getColor(R.color.black));
                } else {
                    txt_resend_otp.setTextColor(getResources().getColor(R.color.white));
                }
            }
        }.start();

    }

    private void proceedToOTPVerification() {

        String otpText = otp_edittext.getText().toString();

        otpText = otp_edittext.getText().toString();

        if (otpText.isEmpty()) {
            otp_edittext.setError("OTP number should not be empty");
            otp_edittext.requestFocus();
        } else {
            gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
            serverApiKey = getString(R.string.fcm_api_key);
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
            ValidateGuestRequest.sendRequest(mobileNo, gcmToken, serverApiKey, deviceId, VERSION_NO, otpText, GuestLoginActivity.this, serviceResponseHandler);
        }
    }

    private void startService() {

        Intent i = new Intent(this, MyService.class);
        startService(i);
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {

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

    private void proceed() {

        mobileNo = userMobileText.getText().toString();
        emailId = userEmailText.getText().toString();

        if (AccountDetails.getValidateThrough().equalsIgnoreCase("both")) {
            if (mobileNo.isEmpty()) {
                GreekDialog.alertDialog(this, 0, GREEK, "Mobile number should not be empty.", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (mobileNo.length() < 10) {
                GreekDialog.alertDialog(this, 0, GREEK, "Invalid Mobile Number", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });


            } else if (emailId.isEmpty()) {
                GreekDialog.alertDialog(this, 0, GREEK, "Email Id should not be empty.", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (!emailId.equalsIgnoreCase("") && !isEmailValid(emailId)) {
                GreekDialog.alertDialog(this, 0, GREEK, "Invalid Email Id", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            } else {
                loginBtn.setClickable(false);
                loginBtn.setBackgroundColor(getResources().getColor(com.acumengroup.mobile.R.color.gray_border));
                Toast.makeText(this,"OTP has been sent",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                editor.putString("Mobile", mobileNo);
                editor.commit();
                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                //gcmToken = "di2elMkujkg:APA91bGb309LOokqPSuARboevHSxi3fhL7Ht-p4Lrqo3T9FGkAsy6-jOPstym4sIwzc0m9HoM39zoeIfzkfrDEYt5pVz2NhujqgaXbLpjmq14ud9LzyFWoy0O8tVC4qrjr2F37g1IqLN";
                serverApiKey = getString(R.string.fcm_api_key);
                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                GuestLoginRequest.sendRequest(mobileNo, gcmToken, serverApiKey, deviceId, VERSION_NO, "", emailId, GuestLoginActivity.this, serviceResponseHandler);
            }
        } else if (AccountDetails.getValidateThrough().equalsIgnoreCase("mobile")) {
            if (mobileNo.isEmpty()) {
                GreekDialog.alertDialog(this, 0, GREEK, "Mobile number should not be empty.", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (mobileNo.length() < 10) {
                GreekDialog.alertDialog(this, 0, GREEK, "Invalid Mobile Number", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });


            }  /*else if (emailId.isEmpty()) {
                GreekDialog.alertDialog(this, 0, GREEK, "Email Id should not be empty.", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (!emailId.equalsIgnoreCase("") && !isEmailValid(emailId)) {
                GreekDialog.alertDialog(this, 0, GREEK, "Invalid Email Id", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }*/ else {
                loginBtn.setClickable(false);
                loginBtn.setBackgroundColor(getResources().getColor(com.acumengroup.mobile.R.color.gray_border));
                SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                editor.putString("Mobile", mobileNo);
                editor.commit();
                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                //gcmToken = "di2elMkujkg:APA91bGb309LOokqPSuARboevHSxi3fhL7Ht-p4Lrqo3T9FGkAsy6-jOPstym4sIwzc0m9HoM39zoeIfzkfrDEYt5pVz2NhujqgaXbLpjmq14ud9LzyFWoy0O8tVC4qrjr2F37g1IqLN";
                serverApiKey = getString(R.string.fcm_api_key);
                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                GuestLoginRequest.sendRequest(mobileNo, gcmToken, serverApiKey, deviceId, VERSION_NO, "", emailId, GuestLoginActivity.this, serviceResponseHandler);
            }
        } else if (AccountDetails.getValidateThrough().equalsIgnoreCase("email")) {
            if (emailId.isEmpty()) {
                GreekDialog.alertDialog(this, 0, GREEK, "Email Id should not be empty.", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (!emailId.equalsIgnoreCase("") && !isEmailValid(emailId)) {
                GreekDialog.alertDialog(this, 0, GREEK, "Invalid Email Id", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
//                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }/* else if (mobileNo.isEmpty()) {
                GreekDialog.alertDialog(this, 0, GREEK, "Mobile number should not be empty.", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (mobileNo.length() < 10) {
                GreekDialog.alertDialog(this, 0, GREEK, "Invalid Mobile Number", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
                            progressLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }*/ else {
                loginBtn.setClickable(false);
                loginBtn.setBackgroundColor(getResources().getColor(com.acumengroup.mobile.R.color.gray_border));
                SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                editor.putString("Mobile", mobileNo);
                editor.commit();
                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                serverApiKey = getString(R.string.fcm_api_key);
                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                GuestLoginRequest.sendRequest(mobileNo, gcmToken, serverApiKey, deviceId, VERSION_NO, "", emailId, GuestLoginActivity.this, serviceResponseHandler);
            }
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginBtn) {
//            progressLayout.setVisibility(View.VISIBLE);

            proceed();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
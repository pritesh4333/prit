package com.acumengroup.mobile.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.userloginvalidation.OTPRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.OTPResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateOtpRequest;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.Guest.GuestLoginActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.service.MyService;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by User on 9/19/2016.
 */
public class OTPForOpenAccountActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants, View.OnClickListener {

    private GreekBaseActivity greekBaseActivity;
    private ServiceResponseHandler serviceResponseHandler;
    StreamingController streamingController;
    OrderStreamingController orderStreamingController;
    private LinearLayout progressLayout;
    private GreekEditText otp_text;
    private Button submitBtn;
    private GreekTextView resendOTPText;
    private static CountDownTimer countDownTimer;
    private String otpText, mobileNumber, gcmToken, serverApiKey, clientCode, deviceId, emailId;
    String from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_for_openanaccount);

        //Rmove Shared pref personal details
        SharedPreferences.Editor editor = Util.getPrefs(this).edit();
        editor.remove("name");
        editor.remove("errorcode");
        editor.remove("ipvflag");
        editor.remove("gender");
        editor.remove("dob");
        editor.remove("deltainfo");
        editor.remove("fname");
        editor.remove("nationality");
        editor.remove("resstatus");
        editor.remove("uidno");
        editor.remove("corraddr1");
        editor.remove("corraddr2");
        editor.remove("corraddr3");
        editor.remove("corrcity");
        editor.remove("corrpin");
        editor.remove("corrstate");
        editor.remove("corrcountry");
        editor.remove("offno");
        editor.remove("resno");
        editor.remove("faxno");
        editor.remove("email");
        editor.remove("peraddr1");
        editor.remove("peradd2");
        editor.remove("peradd3");
        editor.remove("percity");
        editor.remove("perpin");
        editor.remove("perstate");
        editor.remove("percountry");
        editor.remove("corraddressref");
        editor.remove("peraddref");
        editor.remove("mob");
        editor.remove("panno");
        editor.remove("income");
        editor.remove("bankname");
        editor.remove("branch");
        editor.remove("accno");
        editor.remove("acctype");
        editor.remove("ifcs");
        editor.remove("flag");
        editor.remove("nomName");
        editor.remove("nomRel");
        editor.remove("gname");
        editor.remove("perphone");
        editor.remove("AdharNo");
        editor.remove("MaritalStatus");
        editor.remove("Occupation");
        editor.remove("gaurdian");
        editor.remove("panSubmit");
        editor.remove("personalDetailSubmit");
        editor.remove("userdetailSubmit");
        editor.remove("bankDetailSubmit");
        editor.remove("clienttypeSubmit");
        editor.remove("uploadfaceSubmit");
        editor.remove("uploadpanSubmit");
        editor.remove("uploadsignatureSubmit");
        editor.remove("uploadfaceSubmit");
        editor.remove("panImage");
        editor.remove("faceImage");
        editor.remove("Ifsc");
        editor.remove("BankAccountNumber");
        editor.remove("NomineeName");
        editor.remove("AccountType");
        editor.remove("DefaultBankAccount");
        editor.remove("NomineeRelation");

        editor.commit();

        streamingController = new StreamingController();
        orderStreamingController = new OrderStreamingController();

        try {

            //TODO: to setup view components
            setupView();
            greekBaseActivity = new GreekBaseActivity();
            greekBaseActivity = new GreekBaseActivity();
            progressLayout = findViewById(R.id.customProgress);

            Intent in = getIntent();
            from = in.getStringExtra("from");
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

        otp_text = findViewById(R.id.otp_EditText);
        submitBtn = findViewById(R.id.submitsBtn);
        resendOTPText = findViewById(R.id.resendOTP);
        resendOTPText.setOnClickListener(this);
        //TODO: set oncliklistenre on submit button
        submitBtn.setOnClickListener(this);

        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        submitBtn.setTypeface(font,Typeface.NORMAL);

        otp_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");
                    progressLayout.setVisibility(View.VISIBLE);
                    proceedToVerification();
                }
                return false;
            }
        });
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
        progressLayout.setVisibility(View.GONE);
        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GUEST_VALIDATE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            ValidateGuestResponse otpResponse;
            try {
                otpResponse = (ValidateGuestResponse) jsonResponse.getResponse();
                String errorCode = otpResponse.getErrorCode();

                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                serverApiKey = getString(R.string.fcm_api_key);
                clientCode = otpResponse.getClientCode();

                //TODO: handle errors
                if (errorCode.equals("12")) {
                    Toast.makeText(getApplicationContext(), "Failed To Register", Toast.LENGTH_SHORT).show();
                } else if (errorCode.equals("2")) {
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                } else if (errorCode.equals("16")) {
                    Intent intent = new Intent(OTPForOpenAccountActivity.this, GuestLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if (errorCode.equals("0")) {

                    if (from.equalsIgnoreCase("guest")) {
                        Toast.makeText(getApplicationContext(), "OTP Successfully", Toast.LENGTH_SHORT).show();
                        //TODO: sending request to Iris
                        AccountDetails.setAccountDetails(this, "", "", "OPEN", deviceId, "", "", "", "", "", "1","");

                        streamingController.sendStreamingGuestLoginBroadcastRequest(this, mobileNumber, clientCode, jsonResponse.getSessionId(), null, null);
                        orderStreamingController.sendStreamingGuestLoginOrderRequest(this, mobileNumber, clientCode, gcmToken, serverApiKey, jsonResponse.getSessionId());

                        final Intent intent = new Intent(OTPForOpenAccountActivity.this, GreekBaseActivity.class);

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
                    } else {

                        final Intent intent = new Intent(OTPForOpenAccountActivity.this, UserKycValidation.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "validateOtpMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
            ValidateGuestResponse otpResponse;
            try {
                otpResponse = (ValidateGuestResponse) jsonResponse.getResponse();
                String errorCode = otpResponse.getErrorCode();

                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                serverApiKey = getString(R.string.fcm_api_key);
                clientCode = otpResponse.getClientCode();

                //TODO: handle errors
                if (errorCode.equals("12")) {
                    Toast.makeText(getApplicationContext(), "Failed To Register", Toast.LENGTH_SHORT).show();
                } else if (errorCode.equals("2")) {
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                } else if (errorCode.equals("16")) {
                    Intent intent = new Intent(OTPForOpenAccountActivity.this, GuestLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if (errorCode.equals("0")) {

                    if (from.equalsIgnoreCase("guest")) {
                        Toast.makeText(getApplicationContext(), "OTP Successfully", Toast.LENGTH_SHORT).show();
                        //TODO: sending request to Iris

                        AccountDetails.setAccountDetails(this, "", "", "OPEN", deviceId, "", "", "", "", "", "1","");

                        streamingController.sendStreamingGuestLoginBroadcastRequest(this, mobileNumber, clientCode, jsonResponse.getSessionId(), null, null);
                        orderStreamingController.sendStreamingGuestLoginOrderRequest(this, mobileNumber, clientCode, gcmToken, serverApiKey, jsonResponse.getSessionId());
                        final Intent intent = new Intent(OTPForOpenAccountActivity.this, GreekBaseActivity.class);

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
                    } else {
                        final Intent intent = new Intent(OTPForOpenAccountActivity.this, UserKycValidation.class);
                        intent.putExtra("from", from);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GUEST_GENERATE_PASSWORD_SVC_NAME.equals(jsonResponse.getServiceName())) {
            OTPResponse resendOTPResponse;
            try {
                resendOTPResponse = (OTPResponse) jsonResponse.getResponse();
                String errorCode = resendOTPResponse.getErrorCode();
                if (errorCode.equals("0")) {
                    Toast.makeText(getApplicationContext(), "OTP Sent to Your Registered Mobile Number / Email", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "resendOtpMF".equals(jsonResponse.getServiceName())) {
            OTPResponse resendOTPResponse;
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.submitsBtn) {
            progressLayout.setVisibility(View.VISIBLE);
            proceedToVerification();
        } else if (id == R.id.resendOTP) {
            progressLayout.setVisibility(View.VISIBLE);
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
            gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
            serverApiKey = getString(R.string.fcm_api_key);
            mobileNumber = Util.getPrefs(getApplicationContext()).getString("Mobile", "SORRY No Mobile Number");
            emailId = Util.getPrefs(getApplicationContext()).getString("emailotp", "SORRY No EMAILID ");
            OTPRequest.sendRequestMF(mobileNumber, emailId, OTPForOpenAccountActivity.this, serviceResponseHandler);
            resendOTPText.setEnabled(false);
            resendOTPText.setTextColor(getResources().getColor(R.color.red));
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            countDownTimer = new CountDownTimer(Integer.valueOf(1) * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    resendOTPText.setEnabled(true);
                    resendOTPText.setTextColor(getResources().getColor(R.color.black));
                }
            }.start();
        }
    }

    private void proceedToVerification() {

        otpText = otp_text.getText().toString();

        if (otpText.isEmpty()) {

            GreekDialog.alertDialog(this, 0, GREEK, "OTP number should not be empty.", "Ok", true, new GreekDialog.DialogListener() {

                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    if (action == GreekDialog.Action.OK) {
                        progressLayout.setVisibility(View.GONE);
                    }
                }
            });
        } else {

            mobileNumber = Util.getPrefs(getApplicationContext()).getString("Mobile", "SORRY No Mobile Number");
            gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
            serverApiKey = getString(R.string.fcm_api_key);
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
            ValidateOtpRequest.sendRequest(mobileNumber, otpText, OTPForOpenAccountActivity.this, serviceResponseHandler);
        }
    }
}

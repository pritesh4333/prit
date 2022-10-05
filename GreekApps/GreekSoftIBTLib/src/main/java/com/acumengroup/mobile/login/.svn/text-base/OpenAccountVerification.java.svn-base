package com.acumengroup.mobile.login;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.userloginvalidation.GuestLoginResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.OTPRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.OTPResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.UserVerificationRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateOtpRequest;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.Guest.GuestLoginActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.service.MyService;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.greekmain.util.Util.hideProgress;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class OpenAccountVerification extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants, View.OnClickListener {

    private GreekBaseActivity greekBaseActivity;
    private ServiceResponseHandler serviceResponseHandler;
    private LinearLayout progressLayout, openAcctLayout;
    private ProgressBar progressBar;
    private GreekEditText userMobileText, userEmailText;
    private GreekButton loginBtn;
    private String mobileNo, gcmToken, serverApiKey, deviceId, emailId;
    private String mobileNumber, clientCode;
    private StreamingController streamingController;
    private OrderStreamingController orderStreamingController;
    private boolean valid = false;
    private String from = "";
    private AlertDialog levelDialog;
    private CheckBox ibtcheck, mfcheck;
    private Boolean ibtcheckstatus = false, mfcheckstatus = false;
    private GreekEditText otp_edittext;
    private GreekTextView txt_resend_otp;
    private ProgressBar progressbar;
    private static CountDownTimer countDownTimer;
    private CredentialsClient mCredentialsClient;
    private Credential mCurrentCredential;
    private boolean mIsResolving = false;
    private BroadcastReceiver MySMSBroadcastReceiver;

    private static final String TAG = OpenAccountVerification.class.getSimpleName();
    private static final int RC_SAVE = 1;
    private static final int RC_HINT_MOBILE = 2;
    private static final int RC_HINT_EMAIL = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_verfication);

        try {
            //TODO: to setup view components
            greekBaseActivity = new GreekBaseActivity();
            greekBaseActivity = new GreekBaseActivity();
            progressLayout = findViewById(R.id.customProgress);
            openAcctLayout = findViewById(R.id.OpenAcctTxt);
            ibtcheck = findViewById(R.id.ibtcheck);
            mfcheck = findViewById(R.id.mfcheck);
            progressBar = findViewById(R.id.progressBar);
            Intent in = getIntent();
            from = in.getStringExtra("from");

            if (from.equalsIgnoreCase("openAcct")) {
                openAcctLayout.setVisibility(View.VISIBLE);
            } else {

                openAcctLayout.setVisibility(View.GONE);
            }

            mfcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mfcheckstatus = isChecked;
                }
            });

            ibtcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    ibtcheckstatus = isChecked;
                }
            });

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

        MySMSBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check type of intent filter
                if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                    Bundle extras = intent.getExtras();
                    Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                    switch (status.getStatusCode()) {
                        case CommonStatusCodes.SUCCESS:
                            // Get SMS message contents
                            String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                            // Extract one-time code from the message and complete verification
                            // by sending the code back to your server.
                            break;
                        case CommonStatusCodes.TIMEOUT:
                            // Waiting for SMS timed out (5 minutes)
                            // Handle the error ...
                            break;
                    }
                }

            }
        };

        CredentialsOptions options = new CredentialsOptions.Builder()
                .forceEnableSaveDialog()
                .build();
        mCredentialsClient = Credentials.getClient(this, options);
    }

    private void setupView() {
        serviceResponseHandler = new ServiceResponseHandler(this, this);


        userMobileText = findViewById(R.id.userMobile_text);
        userEmailText = findViewById(R.id.userEmail_text);

        userMobileText.setFocusableInTouchMode(true);

        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this);


        userEmailText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");
                    proceedToSubmit();
                }
                return false;
            }
        });


        userEmailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    loadHintClicked();
                }
            }
        });

        userMobileText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    loadPhoneNoHintClicked();
                }
            }
        });

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


        progressLayout.setVisibility(View.GONE);

        clearCache();
        clearPreferences();

        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "generateOtpMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
            GuestLoginResponse guestLoginResponse;

            try {
                guestLoginResponse = (GuestLoginResponse) jsonResponse.getResponse();
                String errorCode = guestLoginResponse.getErrorCode();

                if (errorCode.equals("15")) {
                    GreekDialog.alertDialog(this, 0, GREEK, "Unauthorized Guest Mobile Number", "Ok", true, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });

                } else if (errorCode.equals("0")) {
                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                    AccountDetails.setLogin_user_type("openuser");
                    Toast.makeText(getApplicationContext(), "OTP Sent to Your Registered Mobile Number / Email", Toast.LENGTH_SHORT).show();

                    View layout = LayoutInflater.from(OpenAccountVerification.this).inflate(R.layout.custom_otp_popup, null);

                    otp_edittext = layout.findViewById(R.id.otp_edittext);
                    Button button_submit_otp = layout.findViewById(R.id.button_submit_otp);
                    Button button_cancel_otp = layout.findViewById(R.id.button_cancel_otp);
                    txt_resend_otp = layout.findViewById(R.id.txt_resend_otp);
                    progressbar = layout.findViewById(R.id.progressBars);

                    AlertDialog.Builder builder = new AlertDialog.Builder(OpenAccountVerification.this);
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
        } else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "validateOtpMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
            ValidateGuestResponse otpResponse;

            if (progressBar != null) {

                progressbar.setVisibility(View.GONE);
            }

            try {
                otpResponse = (ValidateGuestResponse) jsonResponse.getResponse();
                String errorCode = otpResponse.getErrorCode();

                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
                serverApiKey = getString(R.string.fcm_api_key);
                clientCode = otpResponse.getClientCode();

                if (errorCode.equals("12")) {
                    Toast.makeText(getApplicationContext(), "Failed To Register", Toast.LENGTH_SHORT).show();
                } else if (errorCode.equals("2")) {
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    otp_edittext.setError("Invalid OTP");
                    otp_edittext.requestFocus();
                } else if (errorCode.equals("16")) {
                    Intent intent = new Intent(OpenAccountVerification.this, GuestLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if (errorCode.equals("0")) {

                    String guestSuccess = Util.getPrefs(this).getString("guestSuccess", "");

                    if (otpResponse.getValidateGuest().equalsIgnoreCase("true") &&  guestSuccess.equalsIgnoreCase("") ) {
                        Intent intent = new Intent(OpenAccountVerification.this, GuestLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {


                        clearPreferences();
                        clearCache();

                        Toast.makeText(getApplicationContext(), "OTP verify successfully", Toast.LENGTH_SHORT).show();

                        if (levelDialog != null && levelDialog.isShowing()) {
                            levelDialog.dismiss();
                        }

                        if (from.equalsIgnoreCase("guest")) {

                            AccountDetails.setAccountDetails(this, "", "", "OPEN", deviceId, "", "", "", "", "", "1");

                            streamingController.sendStreamingGuestLoginBroadcastRequest(this, mobileNumber, clientCode, jsonResponse.getSessionId(), null, null);
                            orderStreamingController.sendStreamingGuestLoginOrderRequest(this, mobileNumber, clientCode, gcmToken, serverApiKey, jsonResponse.getSessionId());

                            final Intent intent = new Intent(OpenAccountVerification.this, GreekBaseActivity.class);

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

                            Intent intent = new Intent(OpenAccountVerification.this, UserKycValidation.class);
                            intent.putExtra("from", from);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            startService();


                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginBtn) {
            proceedToSubmit();
        }
    }

    private void proceedToSubmit() {

        mobileNo = userMobileText.getText().toString();
        emailId = userEmailText.getText().toString();
        String acctCheck = null;

        if (ibtcheckstatus && mfcheckstatus) {
            acctCheck = "2";

        } else if (ibtcheckstatus) {
            acctCheck = "0";

        } else if (mfcheckstatus) {
            acctCheck = "1";
        }

        if (acctCheck == null) {
            progressLayout.setVisibility(View.GONE);
            valid = false;
            GreekDialog.alertDialog(this, 0, GREEK, "Please select login user type.", "Ok", true, new GreekDialog.DialogListener() {

                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    if (action == GreekDialog.Action.OK) {
                    }
                }
            });

            return;

        } else if (mobileNo.length() <= 0) {
            valid = false;
            userMobileText.setError("Enter Mobile number");
            userMobileText.requestFocus();

        } else if (mobileNo.length() < 10) {
            valid = false;
            userMobileText.setError("Enter 10 digit Mobile Number");
            userMobileText.requestFocus();

        } else if (emailId.length() <= 0) {
            valid = false;
            userEmailText.setError("Enter valid Email Id");
            userEmailText.requestFocus();

        } else if (!emailId.equalsIgnoreCase("") && !isEmailValid(emailId)) {
            valid = false;
            userEmailText.setError("Enter valid Email Id");
            userEmailText.requestFocus();
        } else {
            SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
            editor.putString("Mobile", mobileNo);
            editor.putString("emailotp", emailId);
            editor.putString("personalemail", emailId);
            editor.commit();
            editor.apply();

            AccountDetails.setcUserType(acctCheck);

            gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
            serverApiKey = getString(R.string.fcm_api_key);
            deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            progressLayout.setVisibility(View.VISIBLE);
            startSMSRetriverProcess();
            UserVerificationRequest.sendRequest(mobileNo, deviceId, emailId, acctCheck, OpenAccountVerification.this, serviceResponseHandler);
        }

    }

    private void startSMSRetriverProcess() {
        // Get an instance of SmsRetrieverClient, used to start listening for a matching
        // SMS message.
        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);

        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Successfully started retriever, expect broadcast intent
                // ...
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to start retriever, inspect Exception for more details
                // ...
            }
        });
    }

    public void clearCache() {
        SharedPreferences.Editor editor = Util.getPrefs(this).edit();
        editor.remove("name");
        editor.remove("errorcode");
        /*editor.remove("panno");*/
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
    }

    private void resendOTPRequest() {

        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
        gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
        serverApiKey = getString(R.string.fcm_api_key);
        mobileNumber = Util.getPrefs(getApplicationContext()).getString("Mobile", "SORRY No Mobile Number");
        emailId = Util.getPrefs(getApplicationContext()).getString("emailotp", "SORRY No EMAILID ");

//        progressLayout.setVisibility(View.VISIBLE);
        OTPRequest.sendRequestMF(mobileNumber, emailId, OpenAccountVerification.this, serviceResponseHandler);

        txt_resend_otp.setEnabled(false);
        txt_resend_otp.setTextColor(getResources().getColor(R.color.red));

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(Integer.valueOf(1) * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                txt_resend_otp.setEnabled(true);
                txt_resend_otp.setTextColor(getResources().getColor(R.color.black));
            }
        }.start();

    }

    private void proceedToOTPVerification() {

        String otpText = otp_edittext.getText().toString();

        if (otpText.isEmpty()) {

            otp_edittext.setError("OTP number should not be empty");
            otp_edittext.requestFocus();

        } else {

            String mobileNumber = Util.getPrefs(getApplicationContext()).getString("Mobile", "SORRY No Mobile Number");

            gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");

            serverApiKey = getString(R.string.fcm_api_key);

            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
            //progressLayout.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.VISIBLE);
            ValidateOtpRequest.sendRequest(mobileNumber, otpText, OpenAccountVerification.this, serviceResponseHandler);
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
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void clearPreferences() {
        SharedPreferences.Editor editor = Util.getPrefs(this).edit();
        editor.remove("name");
        editor.remove("errorcode");
        /*editor.remove("panno");*/
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
    }

    // Construct a request for phone numbers and show the picker


    private void loadHintClicked() {

        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()
                        .setShowCancelButton(true)
                        .build())
                .setIdTokenRequested(true)
                .setEmailAddressIdentifierSupported(true)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .build();

        PendingIntent intent = mCredentialsClient.getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RC_HINT_EMAIL, null, 0, 0, 0);
            mIsResolving = true;
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Could not start hint picker Intent", e);
            mIsResolving = false;
        }
    }

    private void loadPhoneNoHintClicked() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()
                        .setShowCancelButton(true)
                        .build())
                .setIdTokenRequested(true)
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = mCredentialsClient.getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RC_HINT_MOBILE, null, 0, 0, 0);
            mIsResolving = true;
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Could not start hint picker Intent", e);
            mIsResolving = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        hideProgress();

        switch (requestCode) {

            case RC_HINT_MOBILE:

                if (resultCode == RESULT_OK) {


                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);


                    userMobileText.setText(credential.getId().substring(3));


                } else {
                    Log.e(TAG, "Credential Read: NOT OK");

                }

                mIsResolving = false;
                break;
            case RC_HINT_EMAIL:

                if (resultCode == RESULT_OK) {


                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    userEmailText.setText(credential.getId());


                } else {
                    Log.e(TAG, "Credential Read: NOT OK");

                }

                mIsResolving = false;
                break;
            case RC_SAVE:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "Credential Save: OK");

                } else {
                    Log.e(TAG, "Credential Save: NOT OK");

                }

                mIsResolving = false;
                break;
        }
    }


    private void processRetrievedCredential(Credential credential, boolean isHint) {


        // If the Credential is not a hint, we should store it an enable the delete button.
        // If it is a hint, skip this because a hint cannot be deleted.
        if (!isHint) {

            mCurrentCredential = credential;

        } else {

        }

        userEmailText.setText(credential.getId());


    }

    @Override
    protected void onPause() {
        unregisterReceiver(MySMSBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupView();
        registerReceiver(MySMSBroadcastReceiver, new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION));

    }
}

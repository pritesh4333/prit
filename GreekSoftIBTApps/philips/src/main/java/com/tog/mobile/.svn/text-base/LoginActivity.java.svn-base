package com.tog.mobile;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.GCM.SendNotifyInformationRequest;
import com.acumengroup.greekmain.core.model.LoginWithOTP.LoginWithOTPREsponse;
import com.acumengroup.greekmain.core.model.LoginWithOTP.LoginWithOTPRequest;
import com.acumengroup.greekmain.core.model.LoginWithOTP.ValidateLoginOTPRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.WatchlistGroupRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.Questions;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendForgotPasswordResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendTranscationPasswordResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.UserLoginValidationRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.UserLoginValidationResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.TCPConnectionHandler;
import com.acumengroup.greekmain.core.network.TCPOrderConnectionHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.Guest.GuestLoginActivity;
import com.acumengroup.mobile.WebContent;
import com.acumengroup.mobile.login.ForgotPasswordActivity;
import com.acumengroup.mobile.login.LoginQuestionsActivity;
import com.acumengroup.mobile.login.OpenAccountVerification;
import com.acumengroup.mobile.login.PasswordChangeActivity;
import com.acumengroup.mobile.login.QuickTourActivity;
import com.acumengroup.mobile.service.HeartBeatService;
import com.acumengroup.mobile.service.MyService;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

import static com.tog.mobile.SplashActivity.APPNAME;




/**
 * Created by Arcadia
 */
public class LoginActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants, View.OnClickListener {
    private GreekEditText edUser, edPass, edTPass;
    private LinearLayout progressLayout, otp_visible_layout, login_visible_layout, create_visible_layout;
    private ImageView mpin_showImg, login_mpin_showImg, otp_mpin_showImg;
    private GreekTextView mpin_show_txt, login_mpin_show_txt, otp_mpin_show_txt;
    private String showhidestatus = "";
    private ServiceResponseHandler serviceResponseHandler;
    private String brokerId, userCode = "", userPass, transactionPass = " ", deviceId, gcmToken, serverApiKey;
    public StreamingController streamController;
    public OrderStreamingController orderStreamingController;
    GreekBaseFragment greekBaseFragment;
    GreekBaseActivity greekBaseActivity;
    private ProgressBar progressBar;
    private TCPOrderConnectionHandler tcpOrderConnectionHandler;
    private TCPConnectionHandler tcpConnectionHandler;
    Button btnLogin, btnSignUp, btnquicktour, btnguestLogin;
    GreekTextView txtOpenAcct, txtloginwitOTP, login_mpin_txtForgot, create_mpin_txtForgot, otp_mpin_txtForgot, reset_mpin_txtForgot, welcome_text,vishwas_txtbottom;
    int REQUEST_CODE = 1234;
    private TextInputLayout ti_clientTPass, ti_clientPass;
    private UserLoginValidationResponse loginResponse = null;
    String sessionId = null;
    private AlertDialog levelDialog;
    private boolean resendflag = false;
    private RelativeLayout customProgress;
    private String loginWithOTP, dsclaimer;
    private int SETTINGS_BACKGROUND_SERVICE = 101;
    private BroadcastReceiver mRegistrationBroadcastReceiver, networkBroadcastReciever;
    private LinearLayout login_layout, create_mpin_layout, login_mpin_layout, otp_mpin_layout, reset_mpin_layout;
    private GreekButton reset_submit, otp_submit, validated_mpin_btn, mpinCreate_btn;
    private GreekEditText create_mpin_edit_one, create_mpin_edit_two, create_mpin_edit_three, create_mpin_edit_four, create_mpin_edit_five, create_mpin_edit_six;
    private GreekEditText login_mpin_edit_one, login_mpin_edit_two, login_mpin_edit_three, login_mpin_edit_four, login_mpin_edit_five, login_mpin_edit_six;
    private GreekEditText otp_mpin_edit_one, otp_mpin_edit_two, otp_mpin_edit_three, otp_mpin_edit_four, otp_mpin_edit_five, otp_mpin_edit_six;
    private GreekEditText reset_mpin_edit_one, reset_mpin_edit_two, reset_mpin_edit_three, reset_mpin_edit_four, reset_mpin_edit_five, reset_mpin_edit_six;
//    private LinearLayout login_layout, create_mpin_layout, login_mpin_layout, otp_mpin_layout, reset_mpin_layout;
//    private GreekButton reset_submit, otp_submit, validated_mpin_btn, mpinCreate_btn;
//    private GreekEditText create_mpin_edit_one, create_mpin_edit_two, create_mpin_edit_three, create_mpin_edit_four, create_mpin_edit_five, create_mpin_edit_six;
//    private GreekEditText login_mpin_edit_one, login_mpin_edit_two, login_mpin_edit_three, login_mpin_edit_four, login_mpin_edit_five, login_mpin_edit_six;
//    private GreekEditText otp_mpin_edit_one, otp_mpin_edit_two, otp_mpin_edit_three, otp_mpin_edit_four, otp_mpin_edit_five, otp_mpin_edit_six;
//    private GreekEditText reset_mpin_edit_one, reset_mpin_edit_two, reset_mpin_edit_three, reset_mpin_edit_four, reset_mpin_edit_five, reset_mpin_edit_six;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (hasFocus) {
            ((GreekEditText) findViewById(R.id.login_mpin_edit_one)).requestFocus();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.philips_login_screen);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        ArrayList<String> crash = new ArrayList<>();
//        crash.add(1,"crash");
        String themeFlag = Util.getPrefs(this).getString("THEME_FLAG", "black");
        AccountDetails.setThemeflag(themeFlag);

/*        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, SETTINGS_BACKGROUND_SERVICE);*/

        try {
            setupView();
            //Remove SharedPreference For Email and Mobile
            SharedPreferences.Editor editor = Util.getPrefs(this).edit();
            editor.remove("Mobile");
            editor.remove("personalemail");
            editor.commit();

            greekBaseActivity = new GreekBaseActivity();
            tcpOrderConnectionHandler = new TCPOrderConnectionHandler(); //TODO PK
            tcpConnectionHandler = new TCPConnectionHandler(); //TODO PK
            streamController = new StreamingController();
            greekBaseFragment = new GreekBaseFragment();
            greekBaseActivity = new GreekBaseActivity();
            orderStreamingController = new OrderStreamingController();

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

            Intent in = getIntent();

            boolean AskForMpin = in.getBooleanExtra("AskForMpin", false);
            if (AskForMpin) {
                /// MPIN created successfully now validating MPIN

                String username = Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", "");
                String pass = Util.getPrefs(this).getString("GREEK_RETAINED_CUST_PASS", "");
                String transpass = Util.getPrefs(this).getString("GREEK_RETAINED_CUST_TRANS_PASS", "");

                 login_layout.setVisibility(View.GONE);
                vishwas_txtbottom.setVisibility(View.GONE);
                login_mpin_layout.setVisibility(View.VISIBLE);
                welcome_text.setText("Welcome " + username);

                login_mpin_edit_one.setText("");
                login_mpin_edit_two.setText("");
                login_mpin_edit_three.setText("");
                login_mpin_edit_four.setText("");
                login_mpin_edit_five.setText("");
                login_mpin_edit_six.setText("");
                edUser.clearFocus();
                edPass.clearFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                showKeyboard(login_mpin_edit_one);


            } else {
                AccountDetails.clearCache(getApplicationContext());
            }
            String fromActivity = in.getStringExtra("from");
            if (fromActivity != null) {
                if (fromActivity.equalsIgnoreCase("heartBeat")) {
                    AccountDetails.setIsApolloConnected(false);
                    AccountDetails.setIsIrisConnected(false);
                    AccountDetails.clearCache(getApplicationContext());
                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                    AccountDetails.setLogin_user_type("openuser");
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Invalid Session.Heart Beat stopped", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                    greekBaseActivity.onLogout(0);
                }
            }

        } catch (Exception e) {
            //generateNoteOnSd(e.toString() + "-");
        }

        boolean showTrasaction = Util.getPrefs(this).getBoolean("is_validateTransactionshow", false);

        if (showTrasaction) {

            edTPass.setVisibility(View.VISIBLE);
            ti_clientTPass.setVisibility(View.VISIBLE);
            txtloginwitOTP.setVisibility(View.GONE);
            edTPass.setImeOptions(EditorInfo.IME_ACTION_DONE);

        } else {

            edTPass.setVisibility(View.GONE);
            ti_clientTPass.setVisibility(View.GONE);
            txtloginwitOTP.setVisibility(View.VISIBLE);
            edPass.setImeOptions(EditorInfo.IME_ACTION_DONE);

        }
        gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "SORRY Token Not Produced.");
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


    private void setupView() {
        ti_clientTPass = findViewById(R.id.ti_clientTPass);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        progressLayout = findViewById(R.id.customProgress);
        progressBar = findViewById(R.id.progressBar);
        edUser = findViewById(R.id.ed_clientCode);
        txtloginwitOTP = findViewById(R.id.txtloginotp);
        login_mpin_txtForgot = findViewById(R.id.login_mpin_txtForgot);
        create_mpin_txtForgot = findViewById(R.id.create_mpin_txtForgot);
        otp_mpin_txtForgot = findViewById(R.id.otp_mpin_txtForgot);
        reset_mpin_txtForgot = findViewById(R.id.reset_mpin_txtForgot);
        welcome_text = findViewById(R.id.welcome_text);
        vishwas_txtbottom = findViewById(R.id.vishwas_txtbottom);
        txtloginwitOTP.setOnClickListener(this);
        login_mpin_txtForgot.setOnClickListener(this);
        create_mpin_txtForgot.setOnClickListener(this);
        otp_mpin_txtForgot.setOnClickListener(this);
        reset_mpin_txtForgot.setOnClickListener(this);

        ti_clientPass = findViewById(R.id.ti_clientPass);
        ti_clientTPass.setTypeface(Typeface.createFromAsset(getAssets(), "DaxOT.ttf"));
        ti_clientPass.setTypeface(Typeface.createFromAsset(getAssets(), "DaxOT.ttf"));

        imm.showSoftInput(edUser, InputMethodManager.SHOW_IMPLICIT);
        InputFilter[] filters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        edUser.setFilters(filters);

        edPass = findViewById(R.id.ed_clientPass);
        edPass.setNextFocusDownId(R.id.ed_clientTPass);
        imm.showSoftInput(edPass, InputMethodManager.SHOW_IMPLICIT);
        edTPass = findViewById(R.id.ed_clientTPass);
        imm.showSoftInput(edTPass, InputMethodManager.SHOW_IMPLICIT);

        edPass.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    edTPass.requestFocus();
                }
                return false;
            }
        });

        edTPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");
                    proceedToLogin();
                }
                return false;
            }
        });

        edPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");
                    proceedToLogin();
                }
                return false;
            }
        });


        GreekTextView disClaimer = findViewById(R.id.txtdisclaimer);
        disClaimer.setOnClickListener(this);

        GreekTextView create_mipn_disclaimer = findViewById(R.id.create_mipn_disclaimer);
        create_mipn_disclaimer.setOnClickListener(this);

        GreekTextView login_mipn_disclaimer = findViewById(R.id.login_mipn_disclaimer);
        login_mipn_disclaimer.setOnClickListener(this);

        GreekTextView otp_mipn_disclaimer = findViewById(R.id.otp_mipn_disclaimer);
        otp_mipn_disclaimer.setOnClickListener(this);

        GreekTextView reset_mipn_disclaimer = findViewById(R.id.reset_mipn_disclaimer);
        reset_mipn_disclaimer.setOnClickListener(this);

        txtOpenAcct = findViewById(R.id.txtOpenAcct);
        txtOpenAcct.setOnClickListener(this);

        GreekTextView txtGuestLogin = findViewById(R.id.txtForgotPass);
        txtGuestLogin.setOnClickListener(this);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnsignUp);
        btnquicktour = findViewById(R.id.quicktourbtn);
        btnguestLogin = findViewById(R.id.btn_GuestLogin);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnquicktour.setOnClickListener(this);
        btnguestLogin.setOnClickListener(this);

        login_layout = findViewById(R.id.login_layout);
        create_mpin_layout = findViewById(R.id.create_mpin_layout);
        login_mpin_layout = findViewById(R.id.login_mpin_layout);
        otp_mpin_layout = findViewById(R.id.otp_mpin_layout);
        reset_mpin_layout = findViewById(R.id.reset_mpin_layout);

        reset_submit = findViewById(R.id.reset_submit);
        otp_submit = findViewById(R.id.otp_submit);
        validated_mpin_btn = findViewById(R.id.validated_mpin_btn);
        mpinCreate_btn = findViewById(R.id.mpinCreate_btn);
        reset_submit.setOnClickListener(this);
        otp_submit.setOnClickListener(this);
        validated_mpin_btn.setOnClickListener(this);
        mpinCreate_btn.setOnClickListener(this);

        create_mpin_edit_one = findViewById(R.id.create_mpin_edit_one);
        create_mpin_edit_two = findViewById(R.id.create_mpin_edit_two);
        create_mpin_edit_three = findViewById(R.id.create_mpin_edit_three);
        create_mpin_edit_four = findViewById(R.id.create_mpin_edit_four);
        create_mpin_edit_five = findViewById(R.id.create_mpin_edit_five);
        create_mpin_edit_six = findViewById(R.id.create_mpin_edit_six);


        login_mpin_edit_one = findViewById(R.id.login_mpin_edit_one);
        login_mpin_edit_two = findViewById(R.id.login_mpin_edit_two);
        login_mpin_edit_three = findViewById(R.id.login_mpin_edit_three);
        login_mpin_edit_four = findViewById(R.id.login_mpin_edit_four);
        login_mpin_edit_five = findViewById(R.id.login_mpin_edit_five);
        login_mpin_edit_six = findViewById(R.id.login_mpin_edit_six);


        otp_mpin_edit_one = findViewById(R.id.otp_mpin_edit_one);
        otp_mpin_edit_two = findViewById(R.id.otp_mpin_edit_two);
        otp_mpin_edit_three = findViewById(R.id.otp_mpin_edit_three);
        otp_mpin_edit_four = findViewById(R.id.otp_mpin_edit_four);
        otp_mpin_edit_five = findViewById(R.id.otp_mpin_edit_five);
        otp_mpin_edit_six = findViewById(R.id.otp_mpin_edit_six);

        reset_mpin_edit_one = findViewById(R.id.reset_mpin_edit_one);
        reset_mpin_edit_two = findViewById(R.id.reset_mpin_edit_two);
        reset_mpin_edit_three = findViewById(R.id.reset_mpin_edit_three);
        reset_mpin_edit_four = findViewById(R.id.reset_mpin_edit_four);
        reset_mpin_edit_five = findViewById(R.id.reset_mpin_edit_five);
        reset_mpin_edit_six = findViewById(R.id.reset_mpin_edit_six);

        otp_visible_layout = findViewById(R.id.otp_visible_layout);
        login_visible_layout = findViewById(R.id.login_visible_layout);
        create_visible_layout = findViewById(R.id.create_visible_layout);

        mpin_show_txt = findViewById(R.id.mpin_showTxt);
        mpin_showImg = findViewById(R.id.mpin_showImg);

        login_mpin_show_txt = findViewById(R.id.login_mpin_showTxt2);
        login_mpin_showImg = findViewById(R.id.login_mpin_showImg);

        otp_mpin_show_txt = findViewById(R.id.otp_mpin_showTxt);
        otp_mpin_showImg = findViewById(R.id.otp_mpin_showImg);

        otp_mpin_show_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp_mpin_show_txt.getText().toString().equalsIgnoreCase("show")) {
                    otp_mpin_show_txt.setText("HIDE");
                    showhidestatus = "show";
                    otp_mpin_showImg.setImageResource(R.drawable.ic_baseline_visibility_off_24);

                    otp_mpin_edit_one.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    otp_mpin_edit_two.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    otp_mpin_edit_three.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    otp_mpin_edit_four.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    otp_mpin_edit_five.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    otp_mpin_edit_six.setTransformationMethod(HideReturnsTransformationMethod.getInstance());


                } else {
                    otp_mpin_show_txt.setText("SHOW");
                    showhidestatus = "hide";
                    otp_mpin_showImg.setImageResource(R.drawable.ic_baseline_visibility_24);
                    otp_mpin_edit_one.setTransformationMethod(new HiddenPassTransformationMethod());
                    otp_mpin_edit_two.setTransformationMethod(new HiddenPassTransformationMethod());
                    otp_mpin_edit_three.setTransformationMethod(new HiddenPassTransformationMethod());
                    otp_mpin_edit_four.setTransformationMethod(new HiddenPassTransformationMethod());
                    otp_mpin_edit_five.setTransformationMethod(new HiddenPassTransformationMethod());
                    otp_mpin_edit_six.setTransformationMethod(new HiddenPassTransformationMethod());


                }


            }
        });

        login_mpin_show_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_mpin_show_txt.getText().toString().equalsIgnoreCase("show")) {
                    login_mpin_show_txt.setText("HIDE");
                    showhidestatus = "show";
                    login_mpin_showImg.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    login_mpin_edit_one.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_mpin_edit_two.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_mpin_edit_three.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_mpin_edit_four.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_mpin_edit_five.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_mpin_edit_six.setTransformationMethod(HideReturnsTransformationMethod.getInstance());


                } else {
                    login_mpin_show_txt.setText("SHOW");
                    showhidestatus = "hide";
                    login_mpin_showImg.setImageResource(R.drawable.ic_baseline_visibility_24);
                    login_mpin_edit_one.setTransformationMethod(new HiddenPassTransformationMethod());
                    login_mpin_edit_two.setTransformationMethod(new HiddenPassTransformationMethod());
                    login_mpin_edit_three.setTransformationMethod(new HiddenPassTransformationMethod());
                    login_mpin_edit_four.setTransformationMethod(new HiddenPassTransformationMethod());
                    login_mpin_edit_five.setTransformationMethod(new HiddenPassTransformationMethod());
                    login_mpin_edit_six.setTransformationMethod(new HiddenPassTransformationMethod());

                }

            }
        });


        mpin_show_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpin_show_txt.getText().toString().equalsIgnoreCase("show")) {
                    mpin_show_txt.setText("HIDE");
                    showhidestatus = "show";
                    mpin_showImg.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    create_mpin_edit_one.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    create_mpin_edit_two.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    create_mpin_edit_three.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    create_mpin_edit_four.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    create_mpin_edit_five.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    create_mpin_edit_six.setTransformationMethod(HideReturnsTransformationMethod.getInstance());


                } else {
                    mpin_show_txt.setText("SHOW");
                    showhidestatus = "hide";
                    mpin_showImg.setImageResource(R.drawable.ic_baseline_visibility_24);
                    create_mpin_edit_one.setTransformationMethod(new HiddenPassTransformationMethod());
                    create_mpin_edit_two.setTransformationMethod(new HiddenPassTransformationMethod());
                    create_mpin_edit_three.setTransformationMethod(new HiddenPassTransformationMethod());
                    create_mpin_edit_four.setTransformationMethod(new HiddenPassTransformationMethod());
                    create_mpin_edit_five.setTransformationMethod(new HiddenPassTransformationMethod());
                    create_mpin_edit_six.setTransformationMethod(new HiddenPassTransformationMethod());


                }
            }

        });


        ediTextListner();

        login_mpin_edit_one.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        login_mpin_edit_two.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        login_mpin_edit_three.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        login_mpin_edit_four.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        login_mpin_edit_five.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        login_mpin_edit_six.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });


        otp_mpin_edit_one.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        otp_mpin_edit_two.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        otp_mpin_edit_three.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        otp_mpin_edit_four.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        otp_mpin_edit_five.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        otp_mpin_edit_six.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });


        create_mpin_edit_one.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        create_mpin_edit_two.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        create_mpin_edit_three.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        create_mpin_edit_four.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        create_mpin_edit_five.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        create_mpin_edit_six.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });



    }

    private void ediTextListner() {

        //MPIN CREATE EXITTEXT LINSTNER START
        create_mpin_edit_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //create_mpin_edit_one.requestFocus(create_mpin_edit_one.getText().length());
//                mpinCreateValidation();

                mpinCreateValidation();

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {


                    if (s.length() > 0 && !s.toString().equals("")) {
                        create_mpin_edit_two.requestFocus();
                    }
                    mpinCreateValidation();
                } else {
                    if (s.length() > 0) {
                        create_mpin_edit_two.setSelection(create_mpin_edit_two.getText().length());
                        create_mpin_edit_two.requestFocus();
                        create_mpin_edit_two.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        create_mpin_edit_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {
                        create_mpin_edit_one.requestFocus();
                        create_mpin_edit_one.setSelectAllOnFocus(true);
                        mpinCreateValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        create_mpin_edit_three.requestFocus();
                    }
                    mpinCreateValidation();
                } else {
                    if (s.length() > 0) {
                        create_mpin_edit_three.setSelection(create_mpin_edit_three.getText().length());
                        create_mpin_edit_three.requestFocus();
                        create_mpin_edit_three.setSelectAllOnFocus(true);

                    }
                }

                // TODO Auto-generated method stub
            }
        });
        create_mpin_edit_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {
                        create_mpin_edit_two.requestFocus();
                        create_mpin_edit_two.setSelectAllOnFocus(true);
                        mpinCreateValidation();

                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        create_mpin_edit_four.requestFocus();
                    }
                    mpinCreateValidation();
                } else {
                    if (s.length() > 0) {
                        create_mpin_edit_four.setSelection(create_mpin_edit_four.getText().length());
                        create_mpin_edit_four.requestFocus();
                        create_mpin_edit_four.setSelectAllOnFocus(true);

                    }
                }

                // TODO Auto-generated method stub
            }
        });
        create_mpin_edit_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {
                        create_mpin_edit_three.requestFocus();
                        create_mpin_edit_three.setSelectAllOnFocus(true);
                        mpinCreateValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        create_mpin_edit_five.requestFocus();
                    }
                    mpinCreateValidation();
                } else {
                    if (s.length() > 0) {
                        create_mpin_edit_five.setSelection(create_mpin_edit_five.getText().length());
                        create_mpin_edit_five.requestFocus();
                        create_mpin_edit_five.setSelectAllOnFocus(true);

                    }
                }

                // TODO Auto-generated method stub
            }
        });
        create_mpin_edit_five.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {

                        create_mpin_edit_four.requestFocus();
                        create_mpin_edit_four.setSelectAllOnFocus(true);
                        mpinCreateValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        create_mpin_edit_six.requestFocus();
                    }

                    mpinCreateValidation();
                } else {
                    if (s.length() > 0) {
                        create_mpin_edit_six.setSelection(create_mpin_edit_six.getText().length());
                        create_mpin_edit_six.requestFocus();
                        create_mpin_edit_six.setSelectAllOnFocus(true);
                    }
                }

                // TODO Auto-generated method stub
            }
        });
        create_mpin_edit_six.setTransformationMethod(new HiddenPassTransformationMethod());
        create_mpin_edit_six.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.toString().equals("")) {
                        create_mpin_edit_five.requestFocus();
                        create_mpin_edit_five.setSelectAllOnFocus(true);
                        mpinCreateValidation();
                    }
                }

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    mpinCreateValidation();
                    create_mpin_edit_six.setSelection(create_mpin_edit_six.getText().length());
                } else {
                    if (s.length() > 0) {
                        create_mpin_edit_six.setSelection(create_mpin_edit_six.getText().length());
                        create_mpin_edit_six.requestFocus();
                        create_mpin_edit_six.setSelectAllOnFocus(true);
                    }
                }
                mpinCreateValidation();
                showhidestatus = "";

            }
        });
//MPIN CREATE EXITTEXT LINSTNER END


        //MPIN LOGIN EXITTEXT LINSTNER START
        login_mpin_edit_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mpinLogineValidation();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {


                    if (s.length() > 0 && !s.toString().equals("")) {
                        login_mpin_edit_two.requestFocus();
                    }
                    mpinLogineValidation();
                } else {
                    if (s.length() > 0) {
                        login_mpin_edit_two.setSelection(login_mpin_edit_two.getText().length());
                        login_mpin_edit_two.requestFocus();
                        login_mpin_edit_two.setSelectAllOnFocus(true);

                    }
                }
                // TODO Auto-generated method stub
            }
        });
        login_mpin_edit_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.toString().equals("")) {
                        login_mpin_edit_one.requestFocus();
                        login_mpin_edit_one.setSelectAllOnFocus(true);
                        mpinLogineValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        login_mpin_edit_three.requestFocus();
                    }
                    mpinLogineValidation();
                } else {
                    if (s.length() > 0) {
                        login_mpin_edit_three.setSelection(login_mpin_edit_three.getText().length());
                        login_mpin_edit_three.requestFocus();
                        login_mpin_edit_three.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        login_mpin_edit_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.toString().equals("")) {
                        login_mpin_edit_two.requestFocus();
                        login_mpin_edit_two.setSelectAllOnFocus(true);
                        mpinLogineValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        login_mpin_edit_four.requestFocus();
                    }
                    mpinLogineValidation();
                } else {
                    if (s.length() > 0) {
                        login_mpin_edit_four.setSelection(login_mpin_edit_four.getText().length());
                        login_mpin_edit_four.requestFocus();
                        login_mpin_edit_four.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        login_mpin_edit_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.toString().equals("")) {
                        login_mpin_edit_three.requestFocus();
                        login_mpin_edit_three.setSelectAllOnFocus(true);
                        mpinLogineValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        login_mpin_edit_five.requestFocus();
                    }
                    mpinLogineValidation();
                } else {
                    if (s.length() > 0) {
                        login_mpin_edit_five.setSelection(login_mpin_edit_five.getText().length());
                        login_mpin_edit_five.requestFocus();
                        login_mpin_edit_five.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        login_mpin_edit_five.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.toString().equals("")) {

                        login_mpin_edit_four.requestFocus();
                        login_mpin_edit_four.setSelectAllOnFocus(true);
                        mpinLogineValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        login_mpin_edit_six.requestFocus();
                    }

                    mpinLogineValidation();
                } else {
                    if (s.length() > 0) {
                        login_mpin_edit_six.setSelection(login_mpin_edit_six.getText().length());
                        login_mpin_edit_six.requestFocus();
                        login_mpin_edit_six.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });

        login_mpin_edit_six.setTransformationMethod(new HiddenPassTransformationMethod());
        login_mpin_edit_six.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {
                        login_mpin_edit_five.requestFocus();
                        login_mpin_edit_five.setSelectAllOnFocus(true);
                        mpinLogineValidation();
                    }
                }
                mpinLogineValidation();
                showhidestatus = "";
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    mpinLogineValidation();
                    login_mpin_edit_six.setSelection(login_mpin_edit_six.getText().length());
                } else {
                    if (s.length() > 0) {
                        login_mpin_edit_six.setSelection(login_mpin_edit_six.getText().length());
                        login_mpin_edit_six.requestFocus();
                        login_mpin_edit_six.setSelectAllOnFocus(true);
                    }
                }
                mpinLogineValidation();
                showhidestatus = "";
                // TODO Auto-generated method stub
            }
        });
        //MPIN LOGIN EXITTEXT LINSTNER END

        //MPIN OTP EXITTEXT LINSTNER START
        otp_mpin_edit_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mpinOtpValidation();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {


                    if (s.length() > 0 && !s.toString().equals("")) {
                        otp_mpin_edit_two.requestFocus();
                    }
                    mpinOtpValidation();
                } else {
                    if (s.length() > 0) {
                        otp_mpin_edit_two.setSelection(otp_mpin_edit_two.getText().length());
                        otp_mpin_edit_two.requestFocus();
                        otp_mpin_edit_two.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        otp_mpin_edit_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {
                        otp_mpin_edit_one.requestFocus();
                        otp_mpin_edit_one.setSelectAllOnFocus(true);
                        mpinOtpValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        otp_mpin_edit_three.requestFocus();
                    }
                    mpinOtpValidation();
                } else {
                    if (s.length() > 0) {
                        otp_mpin_edit_three.setSelection(otp_mpin_edit_three.getText().length());
                        otp_mpin_edit_three.requestFocus();
                        otp_mpin_edit_three.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        otp_mpin_edit_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {
                        otp_mpin_edit_two.requestFocus();
                        otp_mpin_edit_two.setSelectAllOnFocus(true);
                        mpinOtpValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        otp_mpin_edit_four.requestFocus();
                    }
                    mpinOtpValidation();
                } else {
                    if (s.length() > 0) {
                        otp_mpin_edit_four.setSelection(otp_mpin_edit_four.getText().length());
                        otp_mpin_edit_four.requestFocus();
                        otp_mpin_edit_four.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        otp_mpin_edit_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {
                        otp_mpin_edit_three.requestFocus();
                        otp_mpin_edit_three.setSelectAllOnFocus(true);
                        mpinOtpValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        otp_mpin_edit_five.requestFocus();
                    }
                    mpinOtpValidation();
                } else {
                    if (s.length() > 0) {
                        otp_mpin_edit_five.setSelection(otp_mpin_edit_five.getText().length());
                        otp_mpin_edit_five.requestFocus();
                        otp_mpin_edit_five.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        otp_mpin_edit_five.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {

                    if (s.toString().equals("")) {

                        otp_mpin_edit_four.requestFocus();
                        otp_mpin_edit_four.setSelectAllOnFocus(true);
                        mpinOtpValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    if (s.length() > 0 && !s.toString().equals("")) {
                        otp_mpin_edit_six.requestFocus();
                    }

                    mpinOtpValidation();
                } else {
                    if (s.length() > 0) {
                        otp_mpin_edit_six.setSelection(otp_mpin_edit_six.getText().length());
                        otp_mpin_edit_six.requestFocus();
                        otp_mpin_edit_six.setSelectAllOnFocus(true);
                    }
                }
                // TODO Auto-generated method stub
            }
        });
        otp_mpin_edit_six.setTransformationMethod(new HiddenPassTransformationMethod());
        otp_mpin_edit_six.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {


                    if (s.toString().equals("")) {
                        otp_mpin_edit_five.requestFocus();
                        otp_mpin_edit_five.setSelectAllOnFocus(true);
                        mpinOtpValidation();
                    }
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!showhidestatus.equalsIgnoreCase("show") && !showhidestatus.equalsIgnoreCase("hide")) {
                    mpinOtpValidation();
                    otp_mpin_edit_six.setSelection(otp_mpin_edit_six.getText().length());
                } else {
                    if (s.length() > 0) {
                        otp_mpin_edit_six.setSelection(otp_mpin_edit_six.getText().length());
                        otp_mpin_edit_six.requestFocus();
                        otp_mpin_edit_six.setSelectAllOnFocus(true);
                    }
                }
                mpinOtpValidation();
                showhidestatus = "";
                // TODO Auto-generated method stub
            }
        });
        //MPIN OTP EXITTEXT LINSTNER END

        //MPIN RESET EXITTEXT LINSTNER START
        reset_mpin_edit_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mpinResetValidation();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equals("")) {
                    reset_mpin_edit_two.requestFocus();
                }
                mpinResetValidation();
                // TODO Auto-generated method stub
            }
        });
        reset_mpin_edit_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    reset_mpin_edit_one.requestFocus();
                    reset_mpin_edit_one.setSelectAllOnFocus(true);
                    mpinResetValidation();
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equals("")) {
                    reset_mpin_edit_three.requestFocus();
                }
                mpinResetValidation();
                // TODO Auto-generated method stub
            }
        });
        reset_mpin_edit_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    reset_mpin_edit_two.requestFocus();
                    reset_mpin_edit_two.setSelectAllOnFocus(true);
                    mpinResetValidation();
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equals("")) {
                    reset_mpin_edit_four.requestFocus();
                }
                mpinResetValidation();
                // TODO Auto-generated method stub
            }
        });
        reset_mpin_edit_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    reset_mpin_edit_three.requestFocus();
                    reset_mpin_edit_three.setSelectAllOnFocus(true);
                    mpinResetValidation();
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equals("")) {
                    reset_mpin_edit_five.requestFocus();
                }
                mpinResetValidation();
                // TODO Auto-generated method stub
            }
        });
        reset_mpin_edit_five.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().equals("")) {

                    reset_mpin_edit_four.requestFocus();
                    reset_mpin_edit_four.setSelectAllOnFocus(true);
                    mpinResetValidation();
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equals("")) {
                    reset_mpin_edit_six.requestFocus();
                }
                mpinResetValidation();
                // TODO Auto-generated method stub
            }
        });
        reset_mpin_edit_six.setTransformationMethod(new HiddenPassTransformationMethod());
        reset_mpin_edit_six.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().equals("")) {
                    reset_mpin_edit_five.requestFocus();
                    reset_mpin_edit_five.setSelectAllOnFocus(true);
                    mpinResetValidation();
                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                mpinResetValidation();
                // TODO Auto-generated method stub
            }
        });
        //MPIN RESET EXITTEXT LINSTNER END
    }

    private void mpinCreateValidation() {
        Boolean mpincreate = true;
        if (create_mpin_edit_one.getText().toString().equals("")) {
            mpincreate = false;
        } else if (create_mpin_edit_two.getText().toString().equals("")) {
            mpincreate = false;
        } else if (create_mpin_edit_three.getText().toString().equals("")) {
            mpincreate = false;
        } else if (create_mpin_edit_four.getText().toString().equals("")) {
            mpincreate = false;
        } else if (create_mpin_edit_five.getText().toString().equals("")) {
            mpincreate = false;
        } else if (create_mpin_edit_six.getText().toString().equals("")) {
            mpincreate = false;
        }

        if (mpincreate) {
            mpinCreate_btn.setEnabled(true);
            mpinCreate_btn.setBackground(getResources().getDrawable(R.drawable.orange_trading_button_effect));
        } else {
            mpinCreate_btn.setEnabled(false);
            mpinCreate_btn.setBackground(getResources().getDrawable(R.drawable.gray_trading_button_effect));
        }
    }

    private void mpinLogineValidation() {

        Boolean resetlogin = true;


        if (login_mpin_edit_one.getText().toString().equals("")) {
            resetlogin = false;
        } else if (login_mpin_edit_two.getText().toString().equals("")) {
            resetlogin = false;
        } else if (login_mpin_edit_three.getText().toString().equals("")) {
            resetlogin = false;
        } else if (login_mpin_edit_four.getText().toString().equals("")) {
            resetlogin = false;
        } else if (login_mpin_edit_five.getText().toString().equals("")) {
            resetlogin = false;
        } else if (login_mpin_edit_six.getText().toString().equals("")) {
            resetlogin = false;
        }

        if (resetlogin) {
            validated_mpin_btn.setEnabled(true);
            validated_mpin_btn.setBackground(getResources().getDrawable(R.drawable.orange_trading_button_effect));
        } else {
            validated_mpin_btn.setEnabled(false);
            validated_mpin_btn.setBackground(getResources().getDrawable(R.drawable.gray_trading_button_effect));
        }
    }

    private void mpinOtpValidation() {
        Boolean otpcreate = true;

        if (otp_mpin_edit_one.getText().toString().equals("")) {
            otpcreate = false;
        } else if (otp_mpin_edit_two.getText().toString().equals("")) {
            otpcreate = false;
        } else if (otp_mpin_edit_three.getText().toString().equals("")) {
            otpcreate = false;
        } else if (otp_mpin_edit_four.getText().toString().equals("")) {
            otpcreate = false;
        } else if (otp_mpin_edit_five.getText().toString().equals("")) {
            otpcreate = false;
        } else if (otp_mpin_edit_six.getText().toString().equals("")) {
            otpcreate = false;
        }

        if (otpcreate) {
            otp_submit.setEnabled(true);
            otp_submit.setBackground(getResources().getDrawable(R.drawable.orange_trading_button_effect));
        } else {
            otp_submit.setEnabled(false);
            otp_submit.setBackground(getResources().getDrawable(R.drawable.gray_trading_button_effect));
        }
    }

    private void mpinResetValidation() {
        Boolean resetcreate = true;


        if (reset_mpin_edit_one.getText().toString().equals("")) {
            resetcreate = false;
        } else if (reset_mpin_edit_two.getText().toString().equals("")) {
            resetcreate = false;
        } else if (reset_mpin_edit_three.getText().toString().equals("")) {
            resetcreate = false;
        } else if (reset_mpin_edit_four.getText().toString().equals("")) {
            resetcreate = false;
        } else if (reset_mpin_edit_five.getText().toString().equals("")) {
            resetcreate = false;
        } else if (reset_mpin_edit_six.getText().toString().equals("")) {
            resetcreate = false;
        }

        if (resetcreate) {
            reset_submit.setEnabled(true);
            reset_submit.setBackground(getResources().getDrawable(R.drawable.orange_trading_button_effect));
        } else {
            reset_submit.setEnabled(false);
            reset_submit.setBackground(getResources().getDrawable(R.drawable.gray_trading_button_effect));
        }
    }

    public void signUpAsGuest(View view) {
        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
        gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "SORRY Token Not Produced.");
        serverApiKey = getString(R.string.fcm_api_key);
        progressLayout.setVisibility(View.VISIBLE);
        ValidateGuestRequest.sendRequest("", gcmToken, serverApiKey, deviceId, VERSION_NO, "",
                LoginActivity.this, serviceResponseHandler);
    }

    @Override
    public void onClick(View view) {
        String disclaimerstring=getString(R.string.disclaimer);
        String disclaimer=disclaimerstring.replaceAll("APPNAME",APPNAME);
        int id = view.getId();
        if (id == R.id.txtdisclaimer) {
            getApplicationContext().setTheme(R.style.GREEKTheme1);
            final CharSequence formatEulaText = Html.fromHtml(disclaimer);
            ;
            GreekDialog.alertDialogOnly(LoginActivity.this, APPNAME, formatEulaText.toString());
        } else if (id == R.id.create_mipn_disclaimer) {
            getApplicationContext().setTheme(R.style.GREEKTheme1);
            final CharSequence create_mipn_disclaimer = Html.fromHtml(disclaimer);
            ;
            GreekDialog.alertDialogOnly(LoginActivity.this, APPNAME, create_mipn_disclaimer.toString());
        } else if (id == R.id.login_mipn_disclaimer) {
            getApplicationContext().setTheme(R.style.GREEKTheme1);
            final CharSequence login_mipn_disclaimer = Html.fromHtml(disclaimer);
            ;
            GreekDialog.alertDialogOnly(LoginActivity.this, APPNAME, login_mipn_disclaimer.toString());
        } else if (id == R.id.otp_mipn_disclaimer) {
            getApplicationContext().setTheme(R.style.GREEKTheme1);
            final CharSequence otp_mipn_disclaimer = Html.fromHtml(disclaimer);
            ;
            GreekDialog.alertDialogOnly(LoginActivity.this, APPNAME, otp_mipn_disclaimer.toString());
        } else if (id == R.id.reset_mipn_disclaimer) {
            getApplicationContext().setTheme(R.style.GREEKTheme1);
            final CharSequence reset_mipn_disclaimer = Html.fromHtml(disclaimer);
            ;
            GreekDialog.alertDialogOnly(LoginActivity.this, APPNAME, reset_mipn_disclaimer.toString());
        } else if (id == R.id.btnsignUp) {
//            if (clientCode == 3) {
//                String VxmlURL = "https://kyc.vishwasfincap.com:88/";
//                Intent intent = new Intent(getApplicationContext(), WebContent.class);
//                intent.putExtra("AtomRequest", VxmlURL);
//                intent.putExtra("uniqueid", "");
//                intent.putExtra("amt", "");
//                startActivity(intent);
//
//            } else if (clientCode == 2) {
//                String MxmlURL = "https://www.marwadionline.com/open-trading-demat-account";
//
//                Intent intent = new Intent(getApplicationContext(), WebContent.class);
//                intent.putExtra("AtomRequest", MxmlURL);
//                intent.putExtra("uniqueid", "");
//                intent.putExtra("amt", "");
//                startActivity(intent);
//
//            } else if (clientCode == 1) {

                Intent i = new Intent(this, OpenAccountVerification.class);
                i.putExtra("from", "openAcct");
                startActivity(i);
//            }
        } else if (id == R.id.txtForgotPass) {
            Intent forgotPass = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            //forgotPass.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(forgotPass);
        } else if (id == R.id.btnLogin) {
            proceedToLogin();
        } else if (id == R.id.reset_submit) {
            mpinReset();
        } else if (id == R.id.otp_submit) {
            otpSubmit();
        } else if (id == R.id.validated_mpin_btn) {
            validateMpinLogin();
        } else if (id == R.id.mpinCreate_btn) {
            mpinCreate();
        } else if (id == R.id.quicktourbtn) {
            Intent mainAct = new Intent(this, QuickTourActivity.class);
            startActivity(mainAct);
        } else if (id == R.id.btn_GuestLogin) {
            signUpAsGuest(view);
//                GreekDialog.alertDialogOnly(LoginActivity.this, APPNAME, getString(R.string.SEBI_STRING));
//                signUpAsGuest(view);
        } else if (id == R.id.login_mpin_txtForgot) {
            mpinForgot();
        } else if (id == R.id.create_mpin_txtForgot) {
            mpinForgot();
        } else if (id == R.id.otp_mpin_txtForgot) {
            mpinForgot();
        } else if (id == R.id.reset_mpin_txtForgot) {
            mpinForgot();
        } else if (id == R.id.txtloginotp) {
            if (TextUtils.isEmpty(edUser.getText().toString())) {
                edUser.setError("Enter Client Code");
            } else {
                progressLayout.setVisibility(View.VISIBLE);

                LoginWithOTPRequest.sendRequest(edUser.getText().toString(), "", LoginActivity.this, serviceResponseHandler);


            }
        }
    }

    private void showKeyboard(GreekEditText edittext) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT);
    }


    private void validateMpinLogin() {

        String mpin = login_mpin_edit_one.getText().toString() + login_mpin_edit_two.getText().toString() + login_mpin_edit_three.getText().toString()
                + login_mpin_edit_four.getText().toString() + login_mpin_edit_five.getText().toString() + login_mpin_edit_six.getText().toString();

        UserLoginValidationRequest.sendRequest(mpin, Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""),
                this, serviceResponseHandler, "");
    }

    private void mpinForgot() {


        UserLoginValidationRequest.sendRequest(Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""),
                this, serviceResponseHandler, "");


    }

    private void mpinCreate() {
        String mpin = create_mpin_edit_one.getText().toString() + create_mpin_edit_two.getText().toString() + create_mpin_edit_three.getText().toString()
                + create_mpin_edit_four.getText().toString() + create_mpin_edit_five.getText().toString() + create_mpin_edit_six.getText().toString();

        if (AccountDetails.getClientCode(getApplicationContext()) != null && !AccountDetails.getClientCode(getApplicationContext()).isEmpty()) {
            UserLoginValidationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()),
                    mpin, AccountDetails.getUsername(getApplicationContext()),
                    this, serviceResponseHandler, "");
        } else {
            UserLoginValidationRequest.sendRequest(Util.getPrefs(this).getString("GREEK_RETAINED_CLIENT_CODE", ""),
                    mpin, Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""),
                    this, serviceResponseHandler, "");
        }


    }

    private void otpSubmit() {

        String otp = otp_mpin_edit_one.getText().toString() + otp_mpin_edit_two.getText().toString() + otp_mpin_edit_three.getText().toString()
                + otp_mpin_edit_four.getText().toString() + otp_mpin_edit_five.getText().toString() + otp_mpin_edit_six.getText().toString();
        if (AccountDetails.getClientCode(getApplicationContext()) != null && !AccountDetails.getClientCode(getApplicationContext()).isEmpty()) {
            UserLoginValidationRequest.sendRequest("3", AccountDetails.getClientCode(getApplicationContext()), otp,
                    AccountDetails.getUsername(getApplicationContext()),
                    this, serviceResponseHandler, "");
        } else {
            UserLoginValidationRequest.sendRequest("3", Util.getPrefs(this).getString("GREEK_RETAINED_CLIENT_CODE", ""), otp,
                    Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""),
                    this, serviceResponseHandler, "");
        }


    }

    private void mpinReset() {
        String mpin = reset_mpin_edit_one.getText().toString() + reset_mpin_edit_two.getText().toString() + reset_mpin_edit_three.getText().toString()
                + reset_mpin_edit_four.getText().toString() + reset_mpin_edit_five.getText().toString() + reset_mpin_edit_six.getText().toString();
        if (AccountDetails.getClientCode(getApplicationContext()) != null && !AccountDetails.getClientCode(getApplicationContext()).isEmpty()) {
            UserLoginValidationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), mpin,
                    AccountDetails.getUsername(getApplicationContext()),
                    this, serviceResponseHandler, "");

        } else {

            UserLoginValidationRequest.sendRequest(Util.getPrefs(this).getString("GREEK_RETAINED_CLIENT_CODE", ""), mpin, Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""),
                    this, serviceResponseHandler, "");
        }
    }

    private void proceedToLogin() {

        boolean toProceed = true;
        String messageToShow = "";
        brokerId = "1";
        userCode = edUser.getText().toString();
        userPass = edPass.getText().toString();

        if (edTPass.getVisibility() == View.VISIBLE) {

            transactionPass = edTPass.getText().toString();
        } else {
            transactionPass = "";
        }

        if (userCode.isEmpty()) {
            messageToShow = "Username cannot be empty";
            toProceed = false;
        } else if (brokerId.isEmpty()) {

            messageToShow = "Broker id cannot be empty";
            toProceed = false;

        } else if (userPass.isEmpty()) {

            messageToShow = "Password cannot be empty";
            toProceed = false;
        } else if (edTPass.getVisibility() == View.VISIBLE && transactionPass.isEmpty()) {
            messageToShow = "Transaction Password cannot be empty";
            toProceed = false;
        }

        if (toProceed) {
            progressLayout.setVisibility(View.VISIBLE);
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
            String manufacturer = getDeviceName();
            String model = Build.MODEL;
            int version = Build.VERSION.SDK_INT;
            String deviceDetails = manufacturer + "-" + model + "-" + version;

            UserLoginValidationRequest.sendRequest(getString(R.string.login_dummy_dob), deviceId, deviceDetails, "0", userPass, transactionPass, userCode,
                    "Customer", brokerId, "0", VERSION_NO, "", LoginActivity.this, serviceResponseHandler);
            edPass.setText("");
            edTPass.setText("");
            btnLogin.setEnabled(false);
        } else {
            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, messageToShow, "OK", true, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (SETTINGS_BACKGROUND_SERVICE == requestCode) {

        }
        if (resultCode == EXIT_CODE) {
            finish();
        } else if (resultCode == PASSWORD_CHANGE_NEEDED) {

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

        }
    }

    public void onEventMainThread(String error) {
        if (error.equalsIgnoreCase("Failed")) {

        } else if (error.equalsIgnoreCase("Order Failed")) {

        } else if (error.equalsIgnoreCase("Force Stop")) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        tcpConnectionHandler.stopStreaming();
        tcpOrderConnectionHandler.stopStreaming();
        String userName = Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", "");
        if (userName != null && !userName.equalsIgnoreCase("")) {
            edUser.setText(userName);
        }
        edPass.setText("");
        if (edTPass.getVisibility() == View.VISIBLE) {
            edTPass.setText("");
        }
        edUser.requestFocus();

        try {

            AccountDetails.setIsApolloConnected(false);
            AccountDetails.setIsIrisConnected(false);
            //AccountDetails.clearCache(getApplicationContext());
            ValidateGuestRequest.sendRequestFlags(LoginActivity.this, serviceResponseHandler);

        } catch (Exception e) {
            Log.d("failure", "getFlags failure");
        }
    }

    @Override
    protected void onPause() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onPause();
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        SendTranscationPasswordResponse sendTranscationPasswordResponse;
        progressLayout.setVisibility(View.GONE);

        JSONResponse jsonResponse = (JSONResponse) response;
        Bundle bundle = new Bundle();
        String UserType = "";

        if (!userCode.equalsIgnoreCase("")) {
            SharedPreferences.Editor editor1 = Util.getPrefs(this).edit();
            editor1.putString("GREEK_RETAINED_CUST_UNAME", userCode);
            editor1.apply();
            editor1.commit();
        }

        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && LOGIN_VALIDATE_MPIN.equals(jsonResponse.getServiceName())) {

            try {
                loginResponse = (UserLoginValidationResponse) jsonResponse.getResponse();
                if (loginResponse.getErrorCode().equalsIgnoreCase("0")) {

                    streamController.sendStreamingLoginRequest(this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this), null, null);
                    orderStreamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(this), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this));
                    final Intent intent = new Intent(LoginActivity.this, GreekBaseActivity.class);

             /*       if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                        intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                    } else {*/
                    if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                        intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                    } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                        intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                    } else {
                        intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                    }
//                    }

                    setResult(LOGIN_SUCCESS, intent);
                    startActivity(intent);
                    new HeartBeatService(LoginActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this),
                            AccountDetails.getClientCode(this)).start();
                    finish();
                } else {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_MPIN_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && LOGIN_FORGET_MPIN.equals(jsonResponse.getServiceName())) {
            try {
                loginResponse = (UserLoginValidationResponse) jsonResponse.getResponse();
                if (loginResponse.getErrorCode().equalsIgnoreCase("0")) {

                    /// MPIN FORGET Now Validate OTP

                    login_mpin_layout.setVisibility(View.GONE);
                    reset_mpin_layout.setVisibility(View.GONE);
                    create_mpin_layout.setVisibility(View.GONE);
                    otp_mpin_layout.setVisibility(View.VISIBLE);
                    otp_mpin_edit_one.setText("");
                    otp_mpin_edit_two.setText("");
                    otp_mpin_edit_three.setText("");
                    otp_mpin_edit_four.setText("");
                    otp_mpin_edit_five.setText("");
                    otp_mpin_edit_six.setText("");

                    otp_mpin_edit_one.requestFocus();
                    edUser.clearFocus();
                    edPass.clearFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    showKeyboard(otp_mpin_edit_one);


                } else if (loginResponse.getErrorCode().equalsIgnoreCase("22")) {

                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_MPIN_NOTCREATED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });

                } else {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && LOGIN_VALIDATE_OTP_FOR_MPIN.equals(jsonResponse.getServiceName())) {
            try {
                loginResponse = (UserLoginValidationResponse) jsonResponse.getResponse();
                if (loginResponse.getErrorCode().equalsIgnoreCase("0")) {

                    /// OTP validatedsuccessfully now Reset or Create MPIN

                    /// MPIN Reset flow
                    otp_mpin_layout.setVisibility(View.GONE);
                    login_mpin_layout.setVisibility(View.GONE);
                    create_mpin_layout.setVisibility(View.GONE);
                    reset_mpin_layout.setVisibility(View.VISIBLE);
                    reset_mpin_edit_one.setText("");
                    reset_mpin_edit_two.setText("");
                    reset_mpin_edit_three.setText("");
                    reset_mpin_edit_four.setText("");
                    reset_mpin_edit_five.setText("");
                    reset_mpin_edit_six.setText("");

                    reset_mpin_edit_one.requestFocus();
                    edUser.clearFocus();
                    edPass.clearFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    showKeyboard(reset_mpin_edit_one);


                } else {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_OTP_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && LOGIN_CREATE_MPIN.equals(jsonResponse.getServiceName())) {
            try {
                loginResponse = (UserLoginValidationResponse) jsonResponse.getResponse();
                if (loginResponse.getErrorCode().equalsIgnoreCase("0")) {

                    /// MPIN created successfully now validating MPIN
                    create_mpin_layout.setVisibility(View.GONE);
                    reset_mpin_layout.setVisibility(View.GONE);
                    otp_mpin_layout.setVisibility(View.GONE);
                    login_mpin_layout.setVisibility(View.VISIBLE);
                    welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                    login_mpin_edit_one.setText("");
                    login_mpin_edit_two.setText("");
                    login_mpin_edit_three.setText("");
                    login_mpin_edit_four.setText("");
                    login_mpin_edit_five.setText("");
                    login_mpin_edit_six.setText("");

                    login_mpin_edit_one.requestFocus();
                    edUser.clearFocus();
                    edPass.clearFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    showKeyboard(login_mpin_edit_one);

                } else if (loginResponse.getErrorCode().equals("2")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_PASSWORD_INVALID_MSG),
                            "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (action == GreekDialog.Action.OK) {


                                    }
                                }
                            });
                } else if (loginResponse.getErrorCode().equals("3")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG),
                            "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {

                                    }
                                }
                            });
                } else if (loginResponse.getErrorCode().equals("4")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                } else if (loginResponse.getErrorCode().equals("5")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.LP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                } else if (loginResponse.getErrorCode().equals("7")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_SUSPENDED), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {


                            }
                        }
                    });
                } else if (loginResponse.getErrorCode().equals("10")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Same Login and Transaction Password", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {


                            }
                        }
                    });
                } else if (loginResponse.getErrorCode().equals("13")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Retailer does not exist", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                } else if (loginResponse.getErrorCode().equals("14")) {

                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Version Mismatch.Please Update your Application", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                } else if (loginResponse.getErrorCode().equals("17")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Account Locked.Please Contact Admin and Change Password", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {


                            }
                        }
                    });
                } else {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (LOGIN_FLAG_GROUP.equals(jsonResponse.getServiceGroup()) || LOGIN_FLAG_NAME.equals(jsonResponse.getServiceName())) {

            try {

                ValidateGuestResponse loginResponse = (ValidateGuestResponse) jsonResponse.getResponse();
                AccountDetails.setAccord_Token(loginResponse.getAccord_Token());
                AccountDetails.setApr_version(loginResponse.getApr_version());


                SharedPreferences.Editor editor = Util.getPrefs(LoginActivity.this).edit();
                editor.putString("accord_Token", loginResponse.getAccord_Token());
                editor.commit();
                editor.apply();

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

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && LOGIN_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                loginResponse = (UserLoginValidationResponse) jsonResponse.getResponse();
                btnLogin.setEnabled(true);

                List<String> mandateIdlist = loginResponse.getMandateIdlist();
                AccountDetails.setMandatIdlist(mandateIdlist);

                String IsSameDevice = loginResponse.getIsSameDevice();
                AccountDetails.setIsSameDevice(IsSameDevice);

                if (loginResponse.getIsValidateSecondary().equalsIgnoreCase("true")) {
                    AccountDetails.setValidateTpassFlag(false);
                } else {
                    AccountDetails.setValidateTpassFlag(true);
                }


                SharedPreferences.Editor editor = Util.getPrefs(this).edit();
                editor.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                editor.putString("GREEK_RETAINED_CUST_PASS", userPass);
                editor.putString("Username", loginResponse.getClientName());

                if (!transactionPass.equalsIgnoreCase("")) {
                    editor.putString("GREEK_RETAINED_CUST_TRANS_PASS", transactionPass);
                }

                editor.apply();
                editor.commit();

                AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());

                String PassTypeCode = loginResponse.getPassType();
                String KYCStatus = loginResponse.getKYCStatus();
                String panNo = loginResponse.getPanNo();
                String dob = loginResponse.getDob();
                String usertype = loginResponse.getUserType();

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
                            UserLoginValidationRequest.sendRequest(getString(R.string.login_dummy_dob), deviceId, deviceDetails, "0", userPass, transactionPass, userCode,
                                    "Customer", brokerId, "0", VERSION_NO, BaseURL, LoginActivity.this, serviceResponseHandler);


                        } else if (errorCode.equals("1")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {

                                        AccountDetails.setUsercode(LoginActivity.this, userCode);
                                        AccountDetails.setUsername(LoginActivity.this, userCode);
                                        AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                        AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());
                                        Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                        intent.putExtra("brokerid", brokerId);
                                        intent.putExtra("userCode", userCode);
                                        intent.putExtra("from", "loginactivity");
                                        intent.putExtra("PassExpiryType", "LoginPass");
                                        startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                                    }
                                }
                            });
                        } else if (errorCode.equalsIgnoreCase("18")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_BOTH_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {

                                        AccountDetails.setUsercode(LoginActivity.this, userCode);
                                        AccountDetails.setUsername(LoginActivity.this, userCode);
                                        AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                        AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());

                                        Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                        intent.putExtra("brokerid", brokerId);
                                        intent.putExtra("userCode", userCode);
                                        intent.putExtra("from", "PasswordChange");
                                        intent.putExtra("PassExpiryType", "BothPass");

                                        AccountDetails.setUsercode(LoginActivity.this, userCode);
                                        AccountDetails.setBrokerid(LoginActivity.this, brokerId);

                                        startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                                    }
                                }
                            });
                        } else if (errorCode.equals("2")) {
                            btnLogin.setEnabled(true);
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_PASSWORD_INVALID_MSG),
                                    "Ok", false, new GreekDialog.DialogListener() {

                                        @Override
                                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                            if (action == GreekDialog.Action.OK) {

                                                hideKeyboard(LoginActivity.this);

                                            }
                                        }
                                    });
                        } else if (errorCode.equals("3")) {
                            btnLogin.setEnabled(true);
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG),
                                    "Ok", false, new GreekDialog.DialogListener() {

                                        @Override
                                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                            if (action == GreekDialog.Action.OK) {
                                                hideKeyboard(LoginActivity.this);

                                            }
                                        }
                                    });
                        } else if (errorCode.equals("4")) {
                            btnLogin.setEnabled(true);
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("5")) {
                            btnLogin.setEnabled(true);
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.LP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("7")) {
                            btnLogin.setEnabled(true);
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_SUSPENDED), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("10")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Same Login and Transaction Password", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("13")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Retailer does not exist", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("14")) {
                            final String package_name = getPackageName();
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Version Mismatch.Please Update your Application", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        //System.exit(0);
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + package_name)));
                                    }
                                }
                            });
                        } else if (errorCode.equals("17")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Account Locked.Please Contact Admin and Change Password", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
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

                            SharedPreferences.Editor editor1 = Util.getPrefs(this).edit();
                            editor1.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                            editor1.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                            editor1.putString("isSecure", String.valueOf(loginResponse.getIsSecure()));
                            editor1.putString("setIris_Port", String.valueOf(loginResponse.getIris_Port()));
                            editor1.putString("setArachne_IP", String.valueOf(loginResponse.getArachne_IP()));
                            editor1.putString("setApollo_IP", String.valueOf(loginResponse.getApollo_IP()));
                            editor1.putString("setIris_IP", String.valueOf(loginResponse.getIris_IP()));
                            editor1.commit();
                            editor1.apply();

                            AccountDetails.setArachne_Port(loginResponse.getArachne_Port());
                            AccountDetails.setApollo_Port(loginResponse.getApollo_Port());
                            AccountDetails.setIris_Port(loginResponse.getIris_Port());
                            AccountDetails.setArachne_IP(loginResponse.getArachne_IP());
                            AccountDetails.setApollo_IP(loginResponse.getApollo_IP());
                            AccountDetails.setIris_IP(loginResponse.getIris_IP());
                            AccountDetails.setChartSetting(loginResponse.getChartSetting());
                            AccountDetails.setIsStrategyProduct(loginResponse.getIsStrategyProduct());


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
                                userName = userCode;
                            }

                            if (edTPass.getVisibility() == View.VISIBLE) {

                                AccountDetails.setValidateTransaction(true);

                            } else {
                                AccountDetails.setValidateTransaction(false);
                            }

                            String themeFlag = Util.getPrefs(this).getString("THEME_FLAG", "black");
                            AccountDetails.setThemeflag(themeFlag);


                            if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {

                                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                AccountDetails.setAccountDetails(LoginActivity.this, loginResponse.getClientCode(),
                                        userCode, UserType, deviceId,
                                        "", "", loginResponse.getExecutionCode(),
                                        loginResponse.getErrorCode(), sessionId, brokerId, loginResponse.getClientName());

                                String isSameDevice = AccountDetails.getIsSameDevice();

                                String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                                String errorsp = "{\"errorCode\":\"0\",\"getUserwatchlist\":[]}";

                                if (isSameDevice.equalsIgnoreCase("false") ||
                                        hasmpatdatalist.equalsIgnoreCase(" ") ||
                                        hasmpatdatalist.equalsIgnoreCase(errorsp)) {
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
                                SharedPreferences.Editor editor5 = Util.getPrefs(this).edit();
                                editor5.putString("orderTime", loginResponse.getOrderTime());
                                editor5.putBoolean("orderTimeFlag", true);
                                editor5.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                                editor5.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                                editor5.putString("GREEK_RETAINED_CUST_PASS", userPass);
                                editor5.putString("GREEK_RETAINED_BROKER_ID", brokerId);


                                editor5.apply();
                                editor5.commit();
                                startService();

                                progressLayout.setVisibility(View.VISIBLE);
                                SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", LoginActivity.this, serviceResponseHandler);
                                if (loginResponse.getIsMPINSet().equalsIgnoreCase("true")) {

                                    /// MPIN created successfully now validating MPIN
                                    login_layout.setVisibility(View.GONE);
                                    vishwas_txtbottom.setVisibility(View.GONE);
                                    login_mpin_layout.setVisibility(View.VISIBLE);
                                    welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                                    login_mpin_edit_one.setText("");
                                    login_mpin_edit_two.setText("");
                                    login_mpin_edit_three.setText("");
                                    login_mpin_edit_four.setText("");
                                    login_mpin_edit_five.setText("");
                                    login_mpin_edit_six.setText("");

                                    edUser.clearFocus();
                                    edPass.clearFocus();
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    showKeyboard(login_mpin_edit_one);

                                } else {
                                    /// MPIN created flow
                                    login_layout.setVisibility(View.GONE);
                                    vishwas_txtbottom.setVisibility(View.GONE);
                                    create_mpin_layout.setVisibility(View.VISIBLE);
                                    create_mpin_edit_one.setText("");
                                    create_mpin_edit_two.setText("");
                                    create_mpin_edit_three.setText("");
                                    create_mpin_edit_four.setText("");
                                    create_mpin_edit_five.setText("");
                                    create_mpin_edit_six.setText("");
                                    create_mpin_edit_one.requestFocus();
                                    edUser.clearFocus();
                                    edPass.clearFocus();
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    showKeyboard(create_mpin_edit_one);
                                }
                            } else {

                                if (loginResponse.getQuestionsList() != null) {
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                    AccountDetails.setLogin_user_type("openuser");
                                    List<Questions> question = loginResponse.getQuestionsList();
                                    final Intent intent = new Intent(LoginActivity.this, LoginQuestionsActivity.class);
                                    bundle.putSerializable("response", (Serializable) question);
                                    intent.putExtras(bundle);
                                    intent.putExtra("sessionId", sessionId);
                                    intent.putExtra("brokerId", brokerId);
                                    intent.putExtra("flag", loginResponse.getUserFlag());
                                    intent.putExtra("userpass", userPass);
                                    intent.putExtra("usercode", userCode);
                                    intent.putExtra("gcid", loginResponse.getClientCode());
                                    intent.putExtra("userType", loginResponse.getUserType());
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                                    finish();
                                }

                            }


                        }
                    } else {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_LOGIN_ERROR_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    btnLogin.setEnabled(true);
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
                            UserLoginValidationRequest.sendRequest(getString(R.string.login_dummy_dob), deviceId, deviceDetails, "0", userPass, transactionPass, userCode,
                                    "Customer", brokerId, "0", VERSION_NO, BaseURL, LoginActivity.this, serviceResponseHandler);


                        }

                        if (errorCode.equals("1")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_TRANS_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {

                                        AccountDetails.setUsercode(LoginActivity.this, userCode);
                                        AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                        AccountDetails.setUsername(LoginActivity.this, userCode);
                                        AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());

                                        Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                        intent.putExtra("from", "transactionalert");
                                        intent.putExtra("brokerid", brokerId);
                                        intent.putExtra("userCode", userCode);
                                        intent.putExtra("PassExpiryType", "TransPass");
                                        edTPass.setText("");
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else if (errorCode.equals("2")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_TRANS_PASSWORD_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("3")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("4")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("5")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.TP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("7")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_INACTIVE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
                        } else if (errorCode.equals("8")) {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_INVALID_2FA_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        hideKeyboard(LoginActivity.this);

                                    }
                                }
                            });
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

                            SharedPreferences.Editor editor1 = Util.getPrefs(this).edit();
                            editor1.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                            editor1.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                            editor1.putString("isSecure", String.valueOf(loginResponse.getIsSecure()));
                            editor1.putString("setIris_Port", String.valueOf(loginResponse.getIris_Port()));
                            editor1.putString("setArachne_IP", String.valueOf(loginResponse.getArachne_IP()));
                            editor1.putString("setApollo_IP", String.valueOf(loginResponse.getApollo_IP()));
                            editor1.putString("setIris_IP", String.valueOf(loginResponse.getIris_IP()));
                            editor1.commit();
                            editor1.apply();

                            AccountDetails.setArachne_Port(loginResponse.getArachne_Port());
                            AccountDetails.setApollo_Port(loginResponse.getApollo_Port());
                            AccountDetails.setIris_Port(loginResponse.getIris_Port());
                            AccountDetails.setArachne_IP(loginResponse.getArachne_IP());
                            AccountDetails.setApollo_IP(loginResponse.getApollo_IP());
                            AccountDetails.setIris_IP(loginResponse.getIris_IP());
                            AccountDetails.setValidateTransaction(true);
                            AccountDetails.setChartSetting(loginResponse.getChartSetting());
                            AccountDetails.setIsStrategyProduct(loginResponse.getIsStrategyProduct());

                            if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {
                                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                AccountDetails.setAccountDetails(LoginActivity.this, loginResponse.getClientCode(),
                                        userCode, UserType, deviceId,
                                        "", "", loginResponse.getExecutionCode(),
                                        loginResponse.getErrorCode(), sessionId, brokerId, loginResponse.getClientName());

                                String isSameDevice = AccountDetails.getIsSameDevice();
                                String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                                String errorsp = "{\"errorCode\":\"0\",\"getUserwatchlist\":[]}";

                                if (isSameDevice.equalsIgnoreCase("false") ||
                                        hasmpatdatalist.equalsIgnoreCase(" ") ||
                                        hasmpatdatalist.equalsIgnoreCase(errorsp)) {
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
                                SharedPreferences.Editor editor4 = Util.getPrefs(this).edit();
                                editor4.putString("orderTime", loginResponse.getOrderTime());
                                editor4.putBoolean("orderTimeFlag", true);
                                editor4.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                                editor4.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                                editor4.putString("GREEK_RETAINED_CUST_PASS", userPass);
                                editor4.putString("GREEK_RETAINED_BROKER_ID", brokerId);

                                editor4.apply();
                                editor4.commit();
                                startService();

                                gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "SORRY Token Not Produced.");
                                progressLayout.setVisibility(View.VISIBLE);
                                SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", LoginActivity.this, serviceResponseHandler);


                                if (loginResponse.getIsMPINSet().equalsIgnoreCase("true")) {

                                    /// MPIN created successfully now validating MPIN
                                    login_layout.setVisibility(View.GONE);
                                    vishwas_txtbottom.setVisibility(View.GONE);
                                    login_mpin_layout.setVisibility(View.VISIBLE);
                                    welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                                    login_mpin_edit_one.setText("");
                                    login_mpin_edit_two.setText("");
                                    login_mpin_edit_three.setText("");
                                    login_mpin_edit_four.setText("");
                                    login_mpin_edit_five.setText("");
                                    login_mpin_edit_six.setText("");

                                    login_mpin_edit_one.requestFocus();
                                    edUser.clearFocus();
                                    edPass.clearFocus();
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    showKeyboard(login_mpin_edit_one);

                                } else {
                                    /// MPIN created flow
                                    login_layout.setVisibility(View.GONE);
                                    vishwas_txtbottom.setVisibility(View.GONE);
                                    create_mpin_layout.setVisibility(View.VISIBLE);
                                    create_mpin_edit_one.setText("");
                                    create_mpin_edit_two.setText("");
                                    create_mpin_edit_three.setText("");
                                    create_mpin_edit_four.setText("");
                                    create_mpin_edit_five.setText("");
                                    create_mpin_edit_six.setText("");
                                    create_mpin_edit_one.requestFocus();
                                    edUser.clearFocus();
                                    edPass.clearFocus();
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    showKeyboard(create_mpin_edit_one);
                                }
                            } else {

                                if (loginResponse.getQuestionsList() != null) {
                                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                    AccountDetails.setLogin_user_type("openuser");
                                    List<Questions> question = loginResponse.getQuestionsList();
                                    final Intent intent = new Intent(LoginActivity.this, LoginQuestionsActivity.class);
                                    bundle.putSerializable("response", (Serializable) question);
                                    intent.putExtras(bundle);
                                    intent.putExtra("sessionId", sessionId);
                                    intent.putExtra("brokerId", brokerId);
                                    intent.putExtra("flag", loginResponse.getUserFlag());
                                    intent.putExtra("userpass", userPass);
                                    intent.putExtra("usercode", userCode);
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


                    AccountDetails.setDefaultProducttype_name(loginResponse.getDefaultProduct());


//unused code
                    if (loginResponse.getDefaultProduct().equalsIgnoreCase("intraday")) {
                        AccountDetails.setProductTypeFlag("1");
                    } else if (loginResponse.getDefaultProduct().equalsIgnoreCase("MTF")) {
                        AccountDetails.setProductTypeFlag("2");
                    } else if (loginResponse.getDefaultProduct().equalsIgnoreCase("SSEQ")) {
                        AccountDetails.setProductTypeFlag("3");
                    } else {
                        AccountDetails.setProductTypeFlag("0");
                    }

                    SharedPreferences.Editor editors = Util.getPrefs(this).edit();
                    editors.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                    editors.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                    editors.putString("isSecure", String.valueOf(loginResponse.getIsSecure()));
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
                        userName = userCode;
                    }

                    if (edTPass.getVisibility() == View.VISIBLE) {

                        AccountDetails.setValidateTransaction(true);

                    } else {
                        AccountDetails.setValidateTransaction(false);
                    }

                    String themeFlag = Util.getPrefs(this).getString("THEME_FLAG", "black");
                    AccountDetails.setThemeflag(themeFlag);


                    if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {

                        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        AccountDetails.setAccountDetails(LoginActivity.this, loginResponse.getClientCode(),
                                userCode, UserType, deviceId,
                                "", "", loginResponse.getExecutionCode(),
                                loginResponse.getErrorCode(), sessionId, brokerId, loginResponse.getClientName());

                        String isSameDevice = AccountDetails.getIsSameDevice();
                        String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                        String errorsp = "{\"errorCode\":\"0\",\"getUserwatchlist\":[]}";

                        if (isSameDevice.equalsIgnoreCase("false") ||
                                hasmpatdatalist.equalsIgnoreCase(" ") ||
                                hasmpatdatalist.equalsIgnoreCase(errorsp)) {
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
                        editor1.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                        editor1.putString("GREEK_RETAINED_CUST_PASS", userPass);
                        editor1.putString("GREEK_RETAINED_BROKER_ID", brokerId);
                        editor1.apply();
                        editor1.commit();

                        startService();

                        gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "SORRY Token Not Produced.");
                        progressLayout.setVisibility(View.VISIBLE);

                        if (loginResponse.getIsMPINSet().equalsIgnoreCase("true")) {

                            /// MPIN created successfully now validating MPIN
                            login_layout.setVisibility(View.GONE);
                            vishwas_txtbottom.setVisibility(View.GONE);
                            login_mpin_layout.setVisibility(View.VISIBLE);
                            welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                            login_mpin_edit_one.setText("");
                            login_mpin_edit_two.setText("");
                            login_mpin_edit_three.setText("");
                            login_mpin_edit_four.setText("");
                            login_mpin_edit_five.setText("");
                            login_mpin_edit_six.setText("");

                            login_mpin_edit_one.requestFocus();
                            edUser.clearFocus();
                            edPass.clearFocus();
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            showKeyboard(login_mpin_edit_one);
                        } else {
                            /// MPIN created flow
                            login_layout.setVisibility(View.GONE);
                            vishwas_txtbottom.setVisibility(View.GONE);
                            create_mpin_layout.setVisibility(View.VISIBLE);
                            create_mpin_edit_one.setText("");
                            create_mpin_edit_two.setText("");
                            create_mpin_edit_three.setText("");
                            create_mpin_edit_four.setText("");
                            create_mpin_edit_five.setText("");
                            create_mpin_edit_six.setText("");
                            create_mpin_edit_one.requestFocus();
                            edUser.clearFocus();
                            edPass.clearFocus();
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            showKeyboard(create_mpin_edit_one);
                        }


                    } else {

                        if (loginResponse.getQuestionsList() != null) {
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                            AccountDetails.setLogin_user_type("openuser");
                            List<Questions> question = loginResponse.getQuestionsList();
                            final Intent intent = new Intent(LoginActivity.this, LoginQuestionsActivity.class);
                            bundle.putSerializable("response", (Serializable) question);
                            intent.putExtras(bundle);
                            intent.putExtra("sessionId", sessionId);
                            intent.putExtra("brokerId", brokerId);
                            intent.putExtra("flag", loginResponse.getUserFlag());
                            intent.putExtra("userpass", userPass);
                            intent.putExtra("usercode", userCode);
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
        }
        else if (LOGIN_TRANSPASSWORD_GROUP.equals(jsonResponse.getServiceGroup()) && LOGIN_TRANSPASSWORD_NAME.equals(jsonResponse.getServiceName())) {

            try {
                sendTranscationPasswordResponse = (SendTranscationPasswordResponse) jsonResponse.getResponse();
                String errorCode = sendTranscationPasswordResponse.getErrorCode();

                if (sendTranscationPasswordResponse.getExecutionCode() != null && sendTranscationPasswordResponse.getExecutionCode().equals("0")) {
                    if (errorCode.equals("1")) {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_TRANS_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {

                                    AccountDetails.setUsercode(LoginActivity.this, userCode);
                                    AccountDetails.setUsername(LoginActivity.this, userCode);
                                    AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                    AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());
                                    Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                    intent.putExtra("from", "transactionalert");
                                    intent.putExtra("brokerid", brokerId);
                                    intent.putExtra("userCode", userCode);
                                    intent.putExtra("PassExpiryType", "TransPass");
                                    edTPass.setText("");
                                    startActivity(intent);
                                }
                            }
                        });
                    } else if (errorCode.equals("2")) {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_TRANS_PASSWORD_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("3")) {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("4")) {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("5")) {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.TP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("7")) {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_INACTIVE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                }
                            }
                        });
                    } else if (errorCode.equals("8")) {
                        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_INVALID_2FA_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                }
                            }
                        });
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
                        AccountDetails.setValidateTransaction(true);

                        String IsSameDevice = loginResponse.getIsSameDevice();
                        AccountDetails.setIsSameDevice(IsSameDevice);

                        if (loginResponse.getIsValidateSecondary().equalsIgnoreCase("true")) {
                            AccountDetails.setValidateTpassFlag(false);
                        } else {
                            AccountDetails.setValidateTpassFlag(true);
                        }

                        if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {
                            deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                            AccountDetails.setAccountDetails(LoginActivity.this, loginResponse.getClientCode(),
                                    userCode, UserType, deviceId,
                                    "", "", loginResponse.getExecutionCode(),
                                    loginResponse.getErrorCode(), sessionId, brokerId, loginResponse.getClientName());


                            String isSameDevice = AccountDetails.getIsSameDevice();
                            String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                            String errorsp = "{\"errorCode\":\"0\",\"getUserwatchlist\":[]}";

                            if (isSameDevice.equalsIgnoreCase("false") ||
                                    hasmpatdatalist.equalsIgnoreCase(" ") ||
                                    hasmpatdatalist.equalsIgnoreCase(errorsp)) {
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
                            SharedPreferences.Editor editor = Util.getPrefs(this).edit();
                            editor.putString("orderTime", loginResponse.getOrderTime());
                            editor.putBoolean("orderTimeFlag", true);
                            editor.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                            editor.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                            editor.putString("GREEK_RETAINED_CUST_PASS", userPass);
                            editor.putString("GREEK_RETAINED_BROKER_ID", brokerId);

                            editor.apply();
                            editor.commit();
                            startService();


                            streamController.sendStreamingLoginRequest(this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this), null, null);
                            orderStreamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(this), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this));
                            progressLayout.setVisibility(View.VISIBLE);
                            if (loginResponse.getIsMPINSet().equalsIgnoreCase("true")) {

                                /// MPIN created successfully now validating MPIN
                                login_layout.setVisibility(View.GONE);
                                vishwas_txtbottom.setVisibility(View.GONE);
                                login_mpin_layout.setVisibility(View.VISIBLE);
                                welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                                login_mpin_edit_one.setText("");
                                login_mpin_edit_two.setText("");
                                login_mpin_edit_three.setText("");
                                login_mpin_edit_four.setText("");
                                login_mpin_edit_five.setText("");
                                login_mpin_edit_six.setText("");

                                login_mpin_edit_one.requestFocus();
                                edUser.clearFocus();
                                edPass.clearFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                showKeyboard(login_mpin_edit_one);

                            } else {
                                /// MPIN created flow
                                login_layout.setVisibility(View.GONE);
                                vishwas_txtbottom.setVisibility(View.GONE);
                                create_mpin_layout.setVisibility(View.VISIBLE);
                                create_mpin_edit_one.setText("");
                                create_mpin_edit_two.setText("");
                                create_mpin_edit_three.setText("");
                                create_mpin_edit_four.setText("");
                                create_mpin_edit_five.setText("");
                                create_mpin_edit_six.setText("");
                                create_mpin_edit_one.requestFocus();
                                edUser.clearFocus();
                                edPass.clearFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                showKeyboard(create_mpin_edit_one);
                            }
                        } else {

                            if (loginResponse.getQuestionsList() != null) {
                                GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                AccountDetails.setLogin_user_type("openuser");
                                List<Questions> question = loginResponse.getQuestionsList();
                                final Intent intent = new Intent(LoginActivity.this, LoginQuestionsActivity.class);
                                bundle.putSerializable("response", (Serializable) question);
                                intent.putExtras(bundle);
                                intent.putExtra("sessionId", sessionId);
                                intent.putExtra("brokerId", brokerId);
                                intent.putExtra("flag", loginResponse.getUserFlag());
                                intent.putExtra("userpass", userPass);
                                intent.putExtra("usercode", userCode);
                                intent.putExtra("gcid", loginResponse.getClientCode());
                                intent.putExtra("userType", loginResponse.getUserType());
                                startActivity(intent);
                                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                                finish();
                            }

                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GUEST_VALIDATE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                ValidateGuestResponse guestloginResponse = (ValidateGuestResponse) jsonResponse.getResponse();

                String errorCode = guestloginResponse.getErrorCode();

                if (errorCode.equals("1")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Your password is expired. Please change the password to continue", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                                AccountDetails.setUsercode(LoginActivity.this, userCode);
                                AccountDetails.setUsername(LoginActivity.this, userCode);
                                AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());

                                Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                intent.putExtra("PassExpiryType", "BothPass");
                                startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                            }
                        }
                    });
                } else if (errorCode.equals("2")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Invalid user or password", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("3")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Failure", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("4")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Duplicate Login", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("11") || errorCode.equals("16")) {



                    Intent intent = new Intent(LoginActivity.this, GuestLoginActivity.class);
                    intent.putExtra("from", "guest");
                    startActivity(intent);


                } else if (errorCode.equals("14")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Version Mismatch.Please Update your Apk", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                                System.exit(0);
                            }
                        }
                    });
                } else if (errorCode.equals("13")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Retailer does not exist", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("17")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Account Locked.Please Contact Admin and Change Password", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("0")) {



                    String guestSuccess = Util.getPrefs(this).getString("guestSuccess", "");

                    if (guestloginResponse.getValidateGuest().equalsIgnoreCase("true") &&  guestSuccess.equalsIgnoreCase("") ) {

                        Intent intent = new Intent(LoginActivity.this, GuestLoginActivity.class);
                        intent.putExtra("from", "guest");
                        startActivity(intent);
                    }else{

                        String guestsessionId = jsonResponse.getSessionId();

                        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


                        AccountDetails.setArachne_Port(guestloginResponse.getArachne_Port());
                        AccountDetails.setApollo_Port(guestloginResponse.getApollo_Port());
                        AccountDetails.setIris_Port(guestloginResponse.getIris_Port());
                        AccountDetails.setArachne_IP(guestloginResponse.getArachne_IP());
                        AccountDetails.setApollo_IP(guestloginResponse.getApollo_IP());
                        AccountDetails.setIris_IP(guestloginResponse.getIris_IP());
                        AccountDetails.setChartSetting(guestloginResponse.getChartSetting());
                        AccountDetails.setIsStrategyProduct(guestloginResponse.getIsStrategyProduct());


                        AccountDetails.setAccountDetails(this, guestloginResponse.getClientCode(),
                                "", "OPEN", deviceId,
                                guestloginResponse.getMobile(), "", "",
                                guestloginResponse.getErrorCode(), guestsessionId, "1", "");


                        AccountDetails.setDeviceID(deviceId);
                        //TODO: sending request to Iris
//                    progressLayout.setVisibility(View.GONE);
                        gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "SORRY Token Not Produced.");
                        GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                        AccountDetails.setLogin_user_type("openuser");
                        sendWatchListGroupNameRequest();

                        streamController.sendStreamingGuestLoginBroadcastRequest(this, deviceId, AccountDetails.getClientCode(this), jsonResponse.getSessionId(), null, null);
                        orderStreamingController.sendStreamingGuestLoginOrderRequest(this, deviceId, AccountDetails.getClientCode(this), gcmToken, serverApiKey, jsonResponse.getSessionId());
                        SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", LoginActivity.this, serviceResponseHandler);


                        final Intent intent = new Intent(LoginActivity.this, GreekBaseActivity.class);

                  /*  if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                        intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                    } else {*/
                        if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                            intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                        } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                            intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                        } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false)) {

                            intent.putExtra("isProceed", NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT);

                        } else {
                            intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                        }
//                    }

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        setResult(LOGIN_SUCCESS, intent);
                        startActivity(intent);
                        finish();
                        startService();
                    }
                }

                } catch(Exception e){
                    e.printStackTrace();
                }

        } else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && FORGOT_PASSWORD_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {


                SendForgotPasswordResponse sendForgotPasswordResponse = (SendForgotPasswordResponse) jsonResponse.getResponse();
                String errorCode = sendForgotPasswordResponse.getErrorCode();
                if (errorCode.equals("0")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.LP_FORGOT_ANSWER), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                                progressLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("logiwithOTP".equals(jsonResponse.getServiceGroup()) && "jLoginWithOTP".equals(jsonResponse.getServiceName())) {


            try {

                if (customProgress != null) {
                    customProgress.setVisibility(View.GONE);
                } else {
                    progressLayout.setVisibility(View.GONE);
                }
                LoginWithOTPREsponse loginWithOTPREsponse = (LoginWithOTPREsponse) jsonResponse.getResponse();
                String errorCode = loginWithOTPREsponse.getErrorCode();

                if (errorCode.equals("21")) {


                    String domainName = loginWithOTPREsponse.getDomainName();
                    int domainPort = Integer.parseInt(loginWithOTPREsponse.getDomainPort());
                    String BaseURL = "";

                    if (loginWithOTPREsponse.getIsSecure().equalsIgnoreCase("true")) {

                        BaseURL = "https" + "://" + domainName + ":" + domainPort;

                    } else {

                        BaseURL = "http" + "://" + domainName + ":" + domainPort;

                    }

                    LoginWithOTPRequest.sendRequest(edUser.getText().toString(), BaseURL, LoginActivity.this, serviceResponseHandler);


                } else if (errorCode.equals("0")) {

                    Toast.makeText(getApplicationContext(), "OTP Sent", Toast.LENGTH_LONG).show();
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.alert_validate_otp, null, false);

//                    if(AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
//
//                        layout.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
//                    }


                    layout.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
                    GreekButton submit = layout.findViewById(R.id.button_submit_otp);
                    GreekButton cancel = layout.findViewById(R.id.button_cancel_otp);
                    customProgress = layout.findViewById(R.id.customProgress);
                    final GreekEditText otp = layout.findViewById(R.id.otp_edittext);
                    final GreekTextView resendText = layout.findViewById(R.id.txt_resend_otp);


                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(otp.getText().toString())) {
                                otp.setError("Enter OTP");
                            } else if (TextUtils.isEmpty(edUser.getText().toString())) {
                                levelDialog.hide();
                                edUser.setError("Enter User Name ");
                            } else {
                                customProgress.setVisibility(View.VISIBLE);
                                loginWithOTP = otp.getText().toString();
                                deviceId = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
                                String manufacturer = getDeviceName();
                                String model = Build.MODEL;
                                int version = Build.VERSION.SDK_INT;
                                String deviceDetails = manufacturer + "-" + model + "-" + version;
                                ValidateLoginOTPRequest.sendRequest(edUser.getText().toString(), otp.getText().toString(),
                                        "", LoginActivity.this, deviceId, deviceDetails, "0", serviceResponseHandler);
                            }
                        }
                    });

                    resendText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(edUser.getText().toString())) {
                                edUser.setError("Enter Client Code");
                            } else {
                                resendflag = true;
                                customProgress.setVisibility(View.VISIBLE);
                                LoginWithOTPRequest.sendRequest(edUser.getText().toString(), "", LoginActivity.this, serviceResponseHandler);
                            }
                        }
                    });


                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            otp.setError(null);
                            levelDialog.hide();
                            levelDialog = null;
                            resendflag = false;
                        }
                    });

                    if (levelDialog == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setView(layout);
                        builder.setCancelable(false);
                        levelDialog = builder.create();
                        if (!resendflag) {
                            if (!levelDialog.isShowing()) {
                                levelDialog.show();
                            }

                        }
                    }
                } else if (errorCode.equals("7")) {
                    SharedPreferences.Editor editor = Util.getPrefs(this).edit();

                    editor.putString("GREEK_RETAINED_CUST_UNAME", "");
                    editor.putString("GREEK_RETAINED_CUST_PASS", "");
                    editor.putString("GREEK_RETAINED_CUST_TRANS_PASS", "");

                    editor.commit();
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_SUSPENDED), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("13")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Retailer does not exist", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("17")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Account Locked. Please Contact Admin and Change Password", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("logiwithOTP".equals(jsonResponse.getServiceGroup()) && "jValidateLoginOTP".equals(jsonResponse.getServiceName())) {
            try {


                customProgress.setVisibility(View.GONE);


                loginResponse = (UserLoginValidationResponse) jsonResponse.getResponse();
                btnLogin.setEnabled(true);


                if (loginResponse.getErrorCode().equals("21")) {


                    String domainName = loginResponse.getDomainName();
                    int domainPort = Integer.parseInt(loginResponse.getDomainPort());
                    String BaseURL = "";

                    if (loginResponse.getIsSecure().equalsIgnoreCase("true")) {

                        BaseURL = "https" + "://" + domainName + ":" + domainPort;

                    } else {

                        BaseURL = "http" + "://" + domainName + ":" + domainPort;

                    }

                    deviceId = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);//todo pk
                    String manufacturer = getDeviceName();
                    String model = Build.MODEL;
                    int version = Build.VERSION.SDK_INT;
                    String deviceDetails = manufacturer + "-" + model + "-" + version;
                    ValidateLoginOTPRequest.sendRequest(edUser.getText().toString(), loginWithOTP, BaseURL, LoginActivity.this,
                            deviceId, deviceDetails, "0", serviceResponseHandler);

                    return;
                }


                if (loginResponse.getClientCode().equalsIgnoreCase("null")) {
                    GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Please Enter Valid OTP",
                            "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (action == GreekDialog.Action.OK) {

                                    }
                                }
                            });
                } else {

                    levelDialog.hide();
                    brokerId = "1";
                    userCode = edUser.getText().toString();

                    List<String> mandateIdlist = loginResponse.getMandateIdlist();
                    AccountDetails.setMandatIdlist(mandateIdlist);


                    String PassTypeCode = loginResponse.getPassType();
                    String KYCStatus = loginResponse.getKYCStatus();
                    String panNo = loginResponse.getPanNo();
                    String dob = loginResponse.getDob();
                    String usertype = loginResponse.getUserType();

                    AccountDetails.setcUserType(usertype);

                    AccountDetails.setUserPAN(getApplicationContext(), panNo);

                    String IsSameDevice = loginResponse.getIsSameDevice();
                    AccountDetails.setIsSameDevice(IsSameDevice);

                    if (loginResponse.getIsValidateSecondary().equalsIgnoreCase("true")) {
                        AccountDetails.setValidateTpassFlag(false);
                    } else {
                        AccountDetails.setValidateTpassFlag(true);
                    }


                    if (PassTypeCode.equalsIgnoreCase("0")) {
                        String errorCodeotp = loginResponse.getErrorCode();

                        if (loginResponse.getExecutionCode() != null && loginResponse.getExecutionCode().equals("0")) {
                            AccountDetails.setChartSetting(loginResponse.getChartSetting());
                            AccountDetails.setIsStrategyProduct(loginResponse.getIsStrategyProduct());

                            if (errorCodeotp.equals("1")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {

                                            AccountDetails.setUsercode(LoginActivity.this, userCode);
                                            AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                            AccountDetails.setUsername(LoginActivity.this, userCode);


                                            AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());
                                            Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                            intent.putExtra("brokerid", brokerId);
                                            intent.putExtra("userCode", userCode);
                                            intent.putExtra("from", "loginactivity");
                                            intent.putExtra("PassExpiryType", "LoginPass");
                                            startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equalsIgnoreCase("18")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_BOTH_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {

                                            AccountDetails.setUsercode(LoginActivity.this, userCode);
                                            AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                            AccountDetails.setUsername(LoginActivity.this, userCode);

                                            AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());
                                            Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                            intent.putExtra("brokerid", brokerId);
                                            intent.putExtra("userCode", userCode);
                                            intent.putExtra("from", "PasswordChange");
                                            intent.putExtra("PassExpiryType", "BothPass");

                                            AccountDetails.setUsercode(LoginActivity.this, userCode);
                                            AccountDetails.setBrokerid(LoginActivity.this, brokerId);

                                            startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("2")) {
                                btnLogin.setEnabled(true);
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_PASSWORD_INVALID_MSG),
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {

                                                }
                                            }
                                        });
                            } else if (errorCodeotp.equals("3")) {
                                btnLogin.setEnabled(true);
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG),
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                                if (action == GreekDialog.Action.OK) {
                                                }
                                            }
                                        });
                            } else if (errorCodeotp.equals("4")) {
                                btnLogin.setEnabled(true);
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("5")) {
                                btnLogin.setEnabled(true);
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.LP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("7")) {
                                btnLogin.setEnabled(true);
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_SUSPENDED), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("10")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Same Login and Transaction Password", "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("13")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Retailer does not exist", "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("14")) {
                                final String package_name = getPackageName();
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Version Mismatch.Please Update your Application", "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                            //System.exit(0);
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + package_name)));
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("17")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, "Account Locked.Please Contact Admin and Change Password", "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("0")) {


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
                                editors.putString("isSecure", String.valueOf(loginResponse.getIsSecure()));
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
                                //for chart display flag (m4 mpandroid chart)
                                AccountDetails.setChartSetting(loginResponse.getChartSetting());
                                AccountDetails.setIsStrategyProduct(loginResponse.getIsStrategyProduct());


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
                                    userName = userCode;
                                }

                                if (edTPass.getVisibility() == View.VISIBLE) {

                                    AccountDetails.setValidateTransaction(true);

                                } else {
                                    AccountDetails.setValidateTransaction(false);
                                }

                                String themeFlag = Util.getPrefs(this).getString("THEME_FLAG", "black");
                                AccountDetails.setThemeflag(themeFlag);


                                if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {

                                    deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                    AccountDetails.setAccountDetails(LoginActivity.this, loginResponse.getClientCode(),
                                            userCode, UserType, deviceId,
                                            "", "", loginResponse.getExecutionCode(),
                                            loginResponse.getErrorCode(), sessionId, brokerId, loginResponse.getClientName());


                                    String isSameDevice = AccountDetails.getIsSameDevice();
                                    String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                                    String errorsp = "{\"errorCode\":\"0\",\"getUserwatchlist\":[]}";

                                    if (isSameDevice.equalsIgnoreCase("false") ||
                                            hasmpatdatalist.equalsIgnoreCase(" ") ||
                                            hasmpatdatalist.equalsIgnoreCase(errorsp)) {
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
                                    editor3.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                                    editor3.putString("GREEK_RETAINED_CUST_PASS", userPass);
                                    editor3.putString("GREEK_RETAINED_BROKER_ID", brokerId);

                                    editor3.apply();
                                    editor3.commit();
                                    startService();


                                    progressLayout.setVisibility(View.VISIBLE);
                                    SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", LoginActivity.this, serviceResponseHandler);
                                    if (loginResponse.getIsMPINSet().equalsIgnoreCase("true")) {

                                        /// MPIN created successfully now validating MPIN
                                        login_layout.setVisibility(View.GONE);
                                        vishwas_txtbottom.setVisibility(View.GONE);
                                        login_mpin_layout.setVisibility(View.VISIBLE);
                                        welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                                        login_mpin_edit_one.setText("");
                                        login_mpin_edit_two.setText("");
                                        login_mpin_edit_three.setText("");
                                        login_mpin_edit_four.setText("");
                                        login_mpin_edit_five.setText("");
                                        login_mpin_edit_six.setText("");

                                        login_mpin_edit_one.requestFocus();
                                        edUser.clearFocus();
                                        edPass.clearFocus();
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                        showKeyboard(login_mpin_edit_one);

                                    } else {
                                        /// MPIN created flow
                                        login_layout.setVisibility(View.GONE);
                                        vishwas_txtbottom.setVisibility(View.GONE);
                                        create_mpin_layout.setVisibility(View.VISIBLE);
                                        create_mpin_edit_one.setText("");
                                        create_mpin_edit_two.setText("");
                                        create_mpin_edit_three.setText("");
                                        create_mpin_edit_four.setText("");
                                        create_mpin_edit_five.setText("");
                                        create_mpin_edit_six.setText("");
                                        create_mpin_edit_one.requestFocus();
                                        edUser.clearFocus();
                                        edPass.clearFocus();
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                        showKeyboard(create_mpin_edit_one);
                                    }
                                } else {

                                    if (loginResponse.getQuestionsList() != null) {
                                        GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                        AccountDetails.setLogin_user_type("openuser");
                                        List<Questions> question = loginResponse.getQuestionsList();
                                        final Intent intent = new Intent(LoginActivity.this, LoginQuestionsActivity.class);
                                        bundle.putSerializable("response", (Serializable) question);
                                        intent.putExtras(bundle);
                                        intent.putExtra("sessionId", sessionId);
                                        intent.putExtra("brokerId", brokerId);
                                        intent.putExtra("flag", loginResponse.getUserFlag());
                                        intent.putExtra("userpass", userPass);
                                        intent.putExtra("usercode", userCode);
                                        intent.putExtra("gcid", loginResponse.getClientCode());
                                        intent.putExtra("userType", loginResponse.getUserType());
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                                        finish();
                                    }

                                }


                            }
                        } else {
                            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_LOGIN_ERROR_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {
                                        btnLogin.setEnabled(true);
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
                        String errorCodeotp = loginResponse.getErrorCode();

                        if (loginResponse.getExecutionCode() != null && loginResponse.getExecutionCode().equals("0")) {
                            if (errorCodeotp.equals("1")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_TRANS_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {

                                            AccountDetails.setUsercode(LoginActivity.this, userCode);
                                            AccountDetails.setUsername(LoginActivity.this, userCode);
                                            AccountDetails.setBrokerid(LoginActivity.this, brokerId);
                                            AccountDetails.setCLIENTNAME(LoginActivity.this, loginResponse.getClientName());
                                            Intent intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
                                            intent.putExtra("from", "transactionalert");
                                            intent.putExtra("brokerid", brokerId);
                                            intent.putExtra("userCode", userCode);
                                            intent.putExtra("PassExpiryType", "TransPass");
                                            edTPass.setText("");
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("2")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_USER_TRANS_PASSWORD_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("3")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("4")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("5")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.TP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("7")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_INACTIVE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("8")) {
                                GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, getString(R.string.CP_INVALID_2FA_MSG), "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                        if (action == GreekDialog.Action.OK) {
                                        }
                                    }
                                });
                            } else if (errorCodeotp.equals("0")) {

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

                                AccountDetails.setValidateTransaction(true);


                                SharedPreferences.Editor editors = Util.getPrefs(this).edit();
                                editors.putString("setArachne_Port", String.valueOf(loginResponse.getArachne_Port()));
                                editors.putString("setApollo_Port", String.valueOf(loginResponse.getApollo_Port()));
                                editors.putString("isSecure", String.valueOf(loginResponse.getIsSecure()));
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


                                if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {
                                    deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                                    AccountDetails.setAccountDetails(LoginActivity.this, loginResponse.getClientCode(),
                                            userCode, UserType, deviceId,
                                            "", "", loginResponse.getExecutionCode(),
                                            loginResponse.getErrorCode(), sessionId, brokerId, loginResponse.getClientName());

                                    String isSameDevice = AccountDetails.getIsSameDevice();
                                    String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                                    String errorsp = "{\"errorCode\":\"0\",\"getUserwatchlist\":[]}";

                                    if (isSameDevice.equalsIgnoreCase("false") ||
                                            hasmpatdatalist.equalsIgnoreCase(" ") ||
                                            hasmpatdatalist.equalsIgnoreCase(errorsp)) {
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
                                    SharedPreferences.Editor editor = Util.getPrefs(this).edit();
                                    editor.putString("orderTime", loginResponse.getOrderTime());
                                    editor.putBoolean("orderTimeFlag", true);
                                    editor.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                                    editor.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                                    editor.putString("GREEK_RETAINED_CUST_PASS", userPass);
                                    editor.putString("GREEK_RETAINED_BROKER_ID", brokerId);

                                    editor.apply();
                                    editor.commit();
                                    startService();

                                    gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "SORRY Token Not Produced.");
                                    progressLayout.setVisibility(View.VISIBLE);
                                    SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", LoginActivity.this, serviceResponseHandler);

                                    if (loginResponse.getIsMPINSet().equalsIgnoreCase("true")) {

                                        /// MPIN created successfully now validating MPIN
                                        login_layout.setVisibility(View.GONE);
                                        vishwas_txtbottom.setVisibility(View.GONE);
                                        login_mpin_layout.setVisibility(View.VISIBLE);
                                        welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                                        login_mpin_edit_one.setText("");
                                        login_mpin_edit_two.setText("");
                                        login_mpin_edit_three.setText("");
                                        login_mpin_edit_four.setText("");
                                        login_mpin_edit_five.setText("");
                                        login_mpin_edit_six.setText("");

                                        login_mpin_edit_one.requestFocus();
                                        edUser.clearFocus();
                                        edPass.clearFocus();
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                        showKeyboard(login_mpin_edit_one);

                                    } else {
                                        /// MPIN created flow
                                        login_layout.setVisibility(View.GONE);
                                        vishwas_txtbottom.setVisibility(View.GONE);
                                        create_mpin_layout.setVisibility(View.VISIBLE);
                                        create_mpin_edit_one.setText("");
                                        create_mpin_edit_two.setText("");
                                        create_mpin_edit_three.setText("");
                                        create_mpin_edit_four.setText("");
                                        create_mpin_edit_five.setText("");
                                        create_mpin_edit_six.setText("");
                                        create_mpin_edit_one.requestFocus();
                                        edUser.clearFocus();
                                        edPass.clearFocus();
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                        showKeyboard(create_mpin_edit_one);
                                    }

                                } else {

                                    if (loginResponse.getQuestionsList() != null) {
                                        GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                        AccountDetails.setLogin_user_type("openuser");
                                        List<Questions> question = loginResponse.getQuestionsList();
                                        final Intent intent = new Intent(LoginActivity.this, LoginQuestionsActivity.class);
                                        bundle.putSerializable("response", (Serializable) question);
                                        intent.putExtras(bundle);
                                        intent.putExtra("sessionId", sessionId);
                                        intent.putExtra("brokerId", brokerId);
                                        intent.putExtra("flag", loginResponse.getUserFlag());
                                        intent.putExtra("userpass", userPass);
                                        intent.putExtra("usercode", userCode);
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
                        editors.putString("isSecure", String.valueOf(loginResponse.getIsSecure()));
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
                            userName = userCode;
                        }

                        if (edTPass.getVisibility() == View.VISIBLE) {

                            AccountDetails.setValidateTransaction(true);

                        } else {
                            AccountDetails.setValidateTransaction(false);
                        }

                        String themeFlag = Util.getPrefs(this).getString("THEME_FLAG", "black");
                        AccountDetails.setThemeflag(themeFlag);


                        if (loginResponse.getValidate2FA().equalsIgnoreCase("false")) {

                            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

                            AccountDetails.setAccountDetails(LoginActivity.this,
                                    loginResponse.getClientCode(),
                                    userCode, UserType, deviceId,
                                    "", "", loginResponse.getExecutionCode(),
                                    loginResponse.getErrorCode(), sessionId, brokerId, loginResponse.getClientName());

                            String isSameDevice = AccountDetails.getIsSameDevice();
                            String hasmpatdatalist = Util.getPrefs(this).getString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");

                            String errorsp = "{\"errorCode\":\"0\",\"getUserwatchlist\":[]}";

                            if (isSameDevice.equalsIgnoreCase("false") ||
                                    hasmpatdatalist.equalsIgnoreCase(" ") ||
                                    hasmpatdatalist.equalsIgnoreCase(errorsp)) {
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
                            AccountDetails.allowedmarket_nCOM = false;
                            AccountDetails.allowedmarket_bCOM = false;

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
                            SharedPreferences.Editor editor = Util.getPrefs(this).edit();
                            editor.putString("orderTime", loginResponse.getOrderTime());
                            editor.putBoolean("orderTimeFlag", true);
                            editor.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                            editor.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                            editor.putString("GREEK_RETAINED_CUST_PASS", userPass);
                            editor.putString("GREEK_RETAINED_BROKER_ID", brokerId);
                            editor.apply();
                            editor.commit();

                            startService();

                            gcmToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "SORRY Token Not Produced.");
                            progressLayout.setVisibility(View.VISIBLE);
                            SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmToken, "1", LoginActivity.this, serviceResponseHandler);
                            if (loginResponse.getIsMPINSet().equalsIgnoreCase("true")) {

                                /// MPIN created successfully now validating MPIN
                                login_layout.setVisibility(View.GONE);
                                vishwas_txtbottom.setVisibility(View.GONE);
                                login_mpin_layout.setVisibility(View.VISIBLE);
                                welcome_text.setText("Welcome " + Util.getPrefs(this).getString("GREEK_RETAINED_CUST_UNAME", ""));
                                login_mpin_edit_one.setText("");
                                login_mpin_edit_two.setText("");
                                login_mpin_edit_three.setText("");
                                login_mpin_edit_four.setText("");
                                login_mpin_edit_five.setText("");
                                login_mpin_edit_six.setText("");

                                login_mpin_edit_one.requestFocus();
                                edUser.clearFocus();
                                edPass.clearFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                showKeyboard(login_mpin_edit_one);

                            } else {
                                /// MPIN created flow
                                login_layout.setVisibility(View.GONE);
                                vishwas_txtbottom.setVisibility(View.GONE);
                                create_mpin_layout.setVisibility(View.VISIBLE);
                                create_mpin_edit_one.setText("");
                                create_mpin_edit_two.setText("");
                                create_mpin_edit_three.setText("");
                                create_mpin_edit_four.setText("");
                                create_mpin_edit_five.setText("");
                                create_mpin_edit_six.setText("");
                                create_mpin_edit_one.requestFocus();
                                edUser.clearFocus();
                                edPass.clearFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                showKeyboard(create_mpin_edit_one);
                            }
                        } else {

                            if (loginResponse.getQuestionsList() != null) {
                                GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                                AccountDetails.setLogin_user_type("openuser");
                                List<Questions> question = loginResponse.getQuestionsList();
                                final Intent intent = new Intent(LoginActivity.this, LoginQuestionsActivity.class);
                                bundle.putSerializable("response", (Serializable) question);
                                intent.putExtras(bundle);
                                intent.putExtra("sessionId", sessionId);
                                intent.putExtra("brokerId", brokerId);
                                intent.putExtra("flag", loginResponse.getUserFlag());
                                intent.putExtra("userpass", userPass);
                                intent.putExtra("usercode", userCode);
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
                }
//new end

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GCM_INFORMATIOn_SVC_NAME.equals(jsonResponse.getServiceName())) {
            progressLayout.setVisibility(View.GONE);
        } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_GROUP_NAME_SVC_NAME.equals(jsonResponse.getServiceName())) {
            // watchlist response store in shared prefrence
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

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {
        progressLayout.setVisibility(View.GONE);
        GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, message, "OK", true, null);
        btnLogin.setEnabled(true);
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        progressLayout.setVisibility(View.GONE);
        if (action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(LoginActivity.this, 0, APPNAME, msg, "OK", true, null);
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private class HiddenPassTransformationMethod implements TransformationMethod {

        private char DOT = '\u2022';

        @Override
        public CharSequence getTransformation(final CharSequence charSequence, final View view) {
            return new PassCharSequence(charSequence);
        }

        @Override
        public void onFocusChanged(final View view, final CharSequence charSequence, final boolean b, final int i,
                                   final Rect rect) {
            //nothing to do here
        }

        private class PassCharSequence implements CharSequence {

            private final CharSequence charSequence;

            public PassCharSequence(final CharSequence charSequence) {
                this.charSequence = charSequence;
            }

            @Override
            public char charAt(final int index) {
                return DOT;
            }

            @Override
            public int length() {
                return charSequence.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return new PassCharSequence(charSequence.subSequence(start, end));
            }
        }
    }
}


package com.acumengroup.mobile.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendForgotPasswordResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.SetNewPasswordRequest;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class SetPasswordActivity extends AppCompatActivity implements View.OnClickListener, GreekConstants, GreekUIServiceHandler {

    private GreekEditText newpass, confpass;
    private Button updatebtn;
    private ServiceResponseHandler serviceResponseHandler;
    public GreekBaseFragment previousFragment;
    private String gscid, passType,alert_title;
    private GreekTextView txt_label;
    boolean result = false;
    private LinearLayout customProgress;
    private RelativeLayout changePswdBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        setupViews();
        setupListeners();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void setupViews() {
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        String packagname =getPackageName();
        if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
            alert_title="Vishwas";
        }else  if (packagname.equalsIgnoreCase("com.tradeongo.mobile")){
            alert_title="Trade On Go";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            alert_title="MSFL";
        }else  if (packagname.equalsIgnoreCase("com.mandot.mobile")){
            alert_title="Mandot";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            alert_title="MSFL";
        }else  if (packagname.equalsIgnoreCase("com.clicktrade.mobile")){
            alert_title="Pentagon";
        }


        Intent i = getIntent();
        gscid = i.getStringExtra("gscid");
        passType = i.getStringExtra("passType");
        newpass = findViewById(R.id.newPwd_text);
        confpass = findViewById(R.id.confirmPwd_text);
        changePswdBg = findViewById(R.id.changePswdBg);
        txt_label = findViewById(R.id.txt_label);
        updatebtn = findViewById(R.id.updateBtn);
        customProgress = findViewById(R.id.customProgress);

        if(AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {

            txt_label.setTextColor(getResources().getColor(R.color.black));
            changePswdBg.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
        } else {
            txt_label.setTextColor(getResources().getColor(R.color.white));
            changePswdBg.setBackground(getResources().getDrawable(R.drawable.bg_drawable));

        }


        if(passType.equalsIgnoreCase("1")) {

            txt_label.setText("Transaction Password");
        } else if(passType.equalsIgnoreCase("0")) {

            txt_label.setText("Login Password");
        }
    }

    private void setupListeners() {
        updatebtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.updateBtn) {
            if (validateFields()) {
                sendForgotPassRequest();
            }
        } else if (id == R.id.cp_cancel_btn) {
            finish();
        }
    }

    private boolean validateFields() {
        boolean b = false, cb = false, validpass = false;
        for (int i = 0; i < newpass.getText().toString().length(); i++) {
            char c = newpass.getText().toString().charAt(i);
            if(Character.isLetter(c)) {
                b = true;
            } else if(Character.isDigit(c)) {
                cb = true;
            }
        }
        validpass = (b && cb);
        if("".equals(newpass.getText().toString())) {
            GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, getString(R.string.CP_NEW_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if("".equals(confpass.getText().toString())) {
            GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, getString(R.string.CP_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if(!newpass.getText().toString().trim().equals(confpass.getText().toString().trim())) {
            GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, getString(R.string.GREE_OLD_NEW_PASS_MSG), "OK", true, null);
            return false;
        } else if(newpass.getText().toString().length() < 8) {
            GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, getString(R.string.CP_LIMIT_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if(!validpass) {
            GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, getString(R.string.CP_ALPHANUMERIC_MSG), "OK", true, null);
            return false;
        } else if(!validateSpecialSymbols()) {
            GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, getString(R.string.CP_SYMBOL_MSG), "OK", true, null);
            return false;
        }
        return true;
    }

    private boolean validateSpecialSymbols() {
        result = false;
        String testPwdString = newpass.getText().toString();
        Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(testPwdString);

        while (matcher.find()) {
            result = true;
        }

        if(result == true) {
            return result;
        } else
            return result;
    }

    private void sendForgotPassRequest() {
        boolean toProceed = true;
        String messageToShow = "";
        String userCode = gscid;
        if(toProceed) {
            customProgress.setVisibility(View.VISIBLE);
            SetNewPasswordRequest.sendRequest(userCode, passType, newpass.getText().toString(), "", SetPasswordActivity.this, serviceResponseHandler);
        } else {
            GreekDialog.alertDialog(this, 0, alert_title, messageToShow, "OK", true, null);
        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        customProgress.setVisibility(View.GONE);
        JSONResponse jsonResponse = (JSONResponse) response;


        if(LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "jsetPassword_mf".equalsIgnoreCase(jsonResponse.getServiceName())) {
            try {


                SendForgotPasswordResponse sendForgotPasswordResponse = (SendForgotPasswordResponse) jsonResponse.getResponse();
                String errorCode = sendForgotPasswordResponse.getErrorCode();

                if(errorCode.equals("21")) {


                    String domainName = sendForgotPasswordResponse.getDomainName();
                    int domainPort = Integer.parseInt(sendForgotPasswordResponse.getDomainPort());
                    String BaseURL = "";

                    if(sendForgotPasswordResponse.getIsSecure().equalsIgnoreCase("true")) {

                        BaseURL = "https" + "://" + domainName + ":" + domainPort;

                    } else {

                        BaseURL = "http" + "://" + domainName + ":" + domainPort;

                    }

                    SetNewPasswordRequest.sendRequest(gscid, passType, newpass.getText().toString(), BaseURL, SetPasswordActivity.this, serviceResponseHandler);


                } else if(errorCode.equals("0")) {
                    GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.PASSWORD_UPDATED), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if(action == GreekDialog.Action.OK) {
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });

                }
                if(errorCode.equals("2")) {
                    GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.GREEK_INCORRECT_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        }
                    });
                }

                if(errorCode.equals("4")) {
                    GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.GREEK_DUPLICATE_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            newpass.setText("");
                            confpass.setText("");

                        }
                    });
                }

                if(errorCode.equals("5")) {
                    GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.GREEK_MAX_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        }
                    });
                }

                if(errorCode.equals("9")) {
                    GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.GREE_ID_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        }
                    });
                }
                if(errorCode.equals("10")) {
                    GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.GREE_LOGIN_TRANSC_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
                //goToStartupPage();
            }
        }
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        if(action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, msg, "OK", true, null);
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
        GreekDialog.alertDialog(SetPasswordActivity.this, 0, alert_title, message, "OK", true, new GreekDialog.DialogListener() {

            @Override
            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                finish();
            }
        });

    }
}

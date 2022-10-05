package com.acumengroup.mobile.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordRequest;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by Arcadia
 */
public class ChangePasswordActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants {
    private GreekEditText oldPwdTxt;
    private GreekEditText newPwdTxt;
    private GreekEditText confirmPwd, userIdTxt;
    private GreekButton submitBtn;
    private String from, usercode = "", brokerid = "",alert_title;
    private ServiceResponseHandler serviceResponseHandler;
    boolean result = false;
    private LinearLayout progressLayout;
    private RelativeLayout changePassBgLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        setupViews();
        setupListeners();
        setTheme();
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getApplication()).equalsIgnoreCase("white")) {
            oldPwdTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            newPwdTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            confirmPwd.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            userIdTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            submitBtn.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            submitBtn.setBackgroundColor(getResources().getColor(R.color.buttonColor));


        }
    }

    private void setupViews() {
        String packagname =getPackageName();
        if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
            alert_title="Vishwas";
        }else  if (packagname.equalsIgnoreCase("com.tradeongo.mobile")){
            alert_title="Trade On Go";
        }else  if (packagname.equalsIgnoreCase("com.mandot.mobile")){
            alert_title="Mandot";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            alert_title="MSFL";
        }   else  if (packagname.equalsIgnoreCase("com.clicktrade.mobile")){
            alert_title="Pentagon";
        }
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        Intent intent = getIntent();
        usercode = intent.getStringExtra("userCode");
        changePassBgLabel = findViewById(R.id.changePswdBg);
        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
            changePassBgLabel.setBackgroundColor(getResources().getColor(R.color.white));

        }

        progressLayout = findViewById(R.id.customProgress);
        userIdTxt = findViewById(R.id.usrId_text);
        userIdTxt.setText(usercode);
        userIdTxt.setEnabled(false);
        oldPwdTxt = findViewById(R.id.oldPwd_text);
        newPwdTxt = findViewById(R.id.newPwd_text);
        confirmPwd = findViewById(R.id.confirmPwd_text);
        submitBtn = findViewById(R.id.updateBtn);


        int textColor = AccountDetails.getTextColorDropdown();

        if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {

            userIdTxt.setTextColor(getResources().getColor(R.color.black));
            oldPwdTxt.setTextColor(getResources().getColor(R.color.black));
            newPwdTxt.setTextColor(getResources().getColor(R.color.black));
            confirmPwd.setTextColor(getResources().getColor(R.color.black));
            userIdTxt.setTextColor(getResources().getColor(R.color.black));
            Drawable lockimg = getResources().getDrawable(R.drawable.ic_person_black_24dp);
            oldPwdTxt.setCompoundDrawables(lockimg, null, null, null);

        } else if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("black")) {
            //changePassBgLabel.setBackgroundColor(getResources().getColor(R.color.black));

            userIdTxt.setTextColor(getResources().getColor(R.color.white));
            oldPwdTxt.setTextColor(getResources().getColor(R.color.white));
            newPwdTxt.setTextColor(getResources().getColor(R.color.white));
            confirmPwd.setTextColor(getResources().getColor(R.color.white));


            Drawable personimg = getResources().getDrawable(R.drawable.ic_person_white_24dp);
            userIdTxt.setCompoundDrawablesWithIntrinsicBounds(personimg, null, null, null);

            Drawable locimg = getResources().getDrawable(R.drawable.ic_lock_white_24dp);
            oldPwdTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(locimg, null, null, null);
            newPwdTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(locimg, null, null, null);
            confirmPwd.setCompoundDrawablesRelativeWithIntrinsicBounds(locimg, null, null, null);


        }
    }

    private void setupListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (validateFields()) {
                    from = intent.getStringExtra("from");
                    brokerid = intent.getStringExtra("brokerid");
                    progressLayout.setVisibility(View.VISIBLE);
                    if (from.equalsIgnoreCase("loginactivity")) {
                        ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(), usercode, "0", brokerid, "", ChangePasswordActivity.this, serviceResponseHandler);
                    } else if (from.equalsIgnoreCase("transactionalert")) {
                        ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(), usercode, "1", brokerid, "", ChangePasswordActivity.this, serviceResponseHandler);
                    }
                }
            }
        });
    }

    private boolean validateSpecialSymbols() {
        result = false;
        String testPwdString = newPwdTxt.getText().toString();
        Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(testPwdString);
        while (matcher.find()) {
            result = true;
        }

        if (result == true) {
            return result;
        } else
            return result;
    }

    private boolean validateFields() {

        boolean b = false, cb = false, validpass = false;
        for (int i = 0; i < newPwdTxt.getText().toString().length(); i++) {
            char c = newPwdTxt.getText().toString().charAt(i);
            if (Character.isLetter(c)) {
                b = true;
            } else if (Character.isDigit(c)) {
                cb = true;
            }
        }
        validpass = (b && cb);
        if ("".equals(oldPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_OLD_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if ("".equals(newPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_NEW_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (newPwdTxt.getText().toString().equals(oldPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_OLD_NEW_SAME_MSG), "OK", true, null);
            return false;
        } else if ("".equals(confirmPwd.getText().toString())) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (!newPwdTxt.getText().toString().equals(confirmPwd.getText().toString())) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_CONFIRM_NEW_PASSWORD_MSG), "OK", true, null);
            return false;
        } else if (newPwdTxt.getText().toString().length() < 8) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_LIMIT_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (!validpass) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_ALPHANUMERIC_MSG), "OK", true, null);
            return false;
        } else if (!validateSpecialSymbols()) {
            GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.CP_SYMBOL_MSG), "OK", true, null);
            return false;
        }
        return true;
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        progressLayout.setVisibility(View.GONE);
        try {
            ChangePasswordResponse changePasswordResponse = (ChangePasswordResponse) jsonResponse.getResponse();

            if (from.equalsIgnoreCase("loginactivity")) {

                if (ChangePasswordRequest.SERVICE_GROUP.equals(jsonResponse.getServiceGroup()) &&
                        ChangePasswordRequest.SERVICE_NAME.equals(jsonResponse.getServiceName())) {


                    if (changePasswordResponse.getErrorCode().equals("21")) {


                        String domainName = changePasswordResponse.getDomainName();
                        int domainPort = Integer.parseInt(changePasswordResponse.getDomainPort());
                        String BaseURL = "";

                        if (changePasswordResponse.getIsSecure().equalsIgnoreCase("true")) {

                            BaseURL = "https" + "://" + domainName + ":" + domainPort;

                        } else {

                            BaseURL = "http" + "://" + domainName + ":" + domainPort;

                        }

                        ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(),
                                usercode, "0", brokerid, BaseURL, ChangePasswordActivity.this, serviceResponseHandler);


                    } else if (changePasswordResponse.getErrorCode().equals("0")) {

                        GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.GREEK_CHANGE_PASSWORD_SUCCESS_TXT), "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        oldPwdTxt.setText("");
                        newPwdTxt.setText("");
                    }
                    if (changePasswordResponse.getErrorCode().equals("2")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Incorrect User OR Password.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("4")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Duplicate password is not allowed.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("5")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Max attempts has been exceeded.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("9")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "ID and Password should not be same.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("10")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Login Password and Transaction Password should not be same.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }


                }
            } else if (from.equalsIgnoreCase("transactionalert")) {

                if (ChangePasswordRequest.SERVICE_GROUP.equals(jsonResponse.getServiceGroup()) &&
                        ChangePasswordRequest.SERVICE_NAME.equals(jsonResponse.getServiceName())) {


                    if (changePasswordResponse.getErrorCode().equals("21")) {


                        String domainName = changePasswordResponse.getDomainName();
                        int domainPort = Integer.parseInt(changePasswordResponse.getDomainPort());
                        String BaseURL = "";

                        if (changePasswordResponse.getIsSecure().equalsIgnoreCase("true")) {

                            BaseURL = "https" + "://" + domainName + ":" + domainPort;

                        } else {

                            BaseURL = "http" + "://" + domainName + ":" + domainPort;

                        }

                        ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(),
                                usercode, "1", brokerid, BaseURL, ChangePasswordActivity.this, serviceResponseHandler);


                    } else if (changePasswordResponse.getErrorCode().equals("0")) {

                        GreekDialog.alertDialog(this, 0, alert_title, getString(R.string.GREEK_CHANGE_PASSWORD_SUCCESS_TXT), "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                                onBackPressed();

                            }
                        });
                        oldPwdTxt.setText("");
                        newPwdTxt.setText("");
                    }
                    if (changePasswordResponse.getErrorCode().equals("2")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Incorrect User OR Password.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("4")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Duplicate password is not allowed.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("5")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Max attempts has been exceeded.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("9")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "ID and Password should not be same.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }

                    if (changePasswordResponse.getErrorCode().equals("10")) {
                        GreekDialog.alertDialog(this, 0, alert_title, "Login Password and Transaction Password should not be same.", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                oldPwdTxt.setText("");
                                newPwdTxt.setText("");
                                confirmPwd.setText("");
                            }
                        });
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {
        GreekDialog.alertDialog(this, 0, alert_title, message, "OK", true, null);
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        if (action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(this, 0, alert_title, msg, "OK", true, null);
        }
    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {

    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void infoDialogOK(int action, String message, final JSONResponse jsonResponse) {
        if (action == ActionCode.ACT_CODE_OK_DIALOG.value) {
            GreekDialog.alertDialog(ChangePasswordActivity.this, 0, alert_title, message, "OK", true, new GreekDialog.DialogListener() {

                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    finish();
                    oldPwdTxt.setText("");
                    newPwdTxt.setText("");
                }
            });
        }
    }
}

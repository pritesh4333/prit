package com.acumengroup.mobile.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.acumengroup.mobile.GreekBaseActivity;
import com.google.android.material.textfield.TextInputLayout;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordRequest;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.acumengroup.mobile.login.PasswordChangeFragment.PassExpiryType;
import static com.acumengroup.mobile.login.PasswordChangeFragment.viewPager;


public class LoginPasswordFragment extends GreekBaseFragment implements View.OnClickListener, GreekUIServiceHandler, GreekConstants {

    private View mChangePwdView;
    private GreekEditText oldPwdTxt;
    private GreekEditText newPwdTxt;
    private GreekEditText confirmPwdTxt, userIdTxt;
    private GreekButton updateBtn;
    private boolean result = false;
    private static String passType = "login password";
    private LinearLayout progressLayout;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private ServiceResponseHandler serviceResponseHandler;
    private TextInputLayout ti_clientCode, ti_oldPass, ti_newPass, ti_confirmPass;
    private String alert_title;

    public LoginPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        mChangePwdView = inflater.inflate(R.layout.fragment_login_password, container, false);

        mChangePwdView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {

            attachLayout(R.layout.fragment_login_password).setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
        } else {
            attachLayout(R.layout.fragment_login_password).setBackground(getResources().getDrawable(R.drawable.bg_drawable));
        }
        AccountDetails.currentFragment = NAV_TO_LOGINPASSWORD;

        setupViews();
        setTheme();
        setupListeners();
        String packagname = getActivity().getPackageName();
        if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
            alert_title="Vishwas";
        }else  if (packagname.equalsIgnoreCase("com.tog.mobile")){
            alert_title="Trade On Go";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            alert_title="MSFL";
        }
        return mChangePwdView;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            oldPwdTxt.setTextColor(getResources().getColor(R.color.black));
            newPwdTxt.setTextColor(getResources().getColor(R.color.black));
            confirmPwdTxt.setTextColor(getResources().getColor(R.color.black));
            userIdTxt.setTextColor(getResources().getColor(R.color.black));

           /* updateBtn.setTextColor(getResources().getColor(R.color.black));
            updateBtn.setBackgroundColor(getResources().getColor(R.color.buttonColor));*/


        }
    }

    private void setupViews() {


        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);

        progressLayout = getActivity().findViewById(R.id.customProgress);
        userIdTxt = mChangePwdView.findViewById(R.id.usrId_text);
        relativeLayout = mChangePwdView.findViewById(R.id.changePswdBg);

        ti_clientCode = mChangePwdView.findViewById(R.id.ti_clientCode);
        ti_oldPass = mChangePwdView.findViewById(R.id.ti_oldPass);
        ti_newPass = mChangePwdView.findViewById(R.id.ti_newPass);
        ti_confirmPass = mChangePwdView.findViewById(R.id.ti_confirmPass);



        ti_clientCode.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf"));
        ti_oldPass.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf"));
        ti_newPass.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf"));
        ti_confirmPass.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf"));


        userIdTxt.setClickable(false);
        userIdTxt.setFocusable(false);
        userIdTxt.setEnabled(false);

        try {
            if (!AccountDetails.getUsername(getMainActivity()).equalsIgnoreCase("")) {

                userIdTxt.setText(AccountDetails.getUsername(getMainActivity()));
            } else {
                userIdTxt.setText(AccountDetails.getUserCode(getMainActivity()));
            }

        } catch (Exception ex) {
            Log.d("Exception", ex.getMessage());
        }


        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userIdTxt, InputMethodManager.SHOW_IMPLICIT);
        InputFilter[] filters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        userIdTxt.setFilters(filters);
        //  userIdTxt.setEnabled(false);
        oldPwdTxt = mChangePwdView.findViewById(R.id.oldPwd_text);
        newPwdTxt = mChangePwdView.findViewById(R.id.newPwd_text);
        confirmPwdTxt = mChangePwdView.findViewById(R.id.confirmPwd_text);
        updateBtn = mChangePwdView.findViewById(R.id.updateBtn);

        int textColor = AccountDetails.getTextColorDropdown();

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {

            userIdTxt.setTextColor(getResources().getColor(R.color.black));
            oldPwdTxt.setTextColor(getResources().getColor(R.color.black));
            newPwdTxt.setTextColor(getResources().getColor(R.color.black));
            confirmPwdTxt.setTextColor(getResources().getColor(R.color.black));
            userIdTxt.setTextColor(getResources().getColor(R.color.black));

        } else if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("black")) {
            // changePassBgLabel.setBackgroundColor(getResources().getColor(R.color.black));

            userIdTxt.setTextColor(getResources().getColor(R.color.white));
            oldPwdTxt.setTextColor(getResources().getColor(R.color.white));
            newPwdTxt.setTextColor(getResources().getColor(R.color.white));
            confirmPwdTxt.setTextColor(getResources().getColor(R.color.white));


            Drawable personimg = getContext().getResources().getDrawable(R.drawable.ic_person_white_24dp);
            userIdTxt.setCompoundDrawablesWithIntrinsicBounds(personimg, null, null, null);

            Drawable locimg = getContext().getResources().getDrawable(R.drawable.ic_lock_white_24dp);
            oldPwdTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(locimg, null, null, null);
            newPwdTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(locimg, null, null, null);
            confirmPwdTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(locimg, null, null, null);


        }
    }

    private void setupListeners() {
        updateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (updateBtn.equals(v)) {
            if (validateFields()) {

                String passtype = "";
                if (passType.equalsIgnoreCase("login password")) {
                    passtype = "0";
                } else {
                    passtype = "1";
                }
                ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(),
                        userIdTxt.getText().toString(), passtype,
                        AccountDetails.getBrokerId(getActivity()), "", getActivity(), serviceResponseHandler);

                progressLayout.setVisibility(View.VISIBLE);
            }
        }
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

        if ("".equals(userIdTxt.getText().toString())) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_USER_ID_EMPTY_MSG), "OK", true, null);
            return false;
        } else if ("".equals(oldPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_OLD_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if ("".equals(newPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_NEW_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (newPwdTxt.getText().toString().equals(oldPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_OLD_NEW_SAME_MSG), "OK", true, null);
            return false;
        } else if ("".equals(confirmPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (!newPwdTxt.getText().toString().equals(confirmPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_CONFIRM_NEW_PASSWORD_MSG), "OK", true, null);
            return false;
        } else if (newPwdTxt.getText().toString().length() < 8) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_LIMIT_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (!validpass) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_ALPHANUMERIC_MSG), "OK", true, null);
            return false;
        } else if (!validateSpecialSymbols()) {
            GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_SYMBOL_MSG), "OK", true, null);
            return false;
        }
        return true;
    }

    @Override
    public void process(Object response) {

    }


    @Override
    public void handleResponse(Object response) {
        progressLayout.setVisibility(View.GONE);

        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            ChangePasswordResponse changePasswordResponse = (ChangePasswordResponse) jsonResponse.getResponse();

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

                    String passtype = "";
                    if (passType.equalsIgnoreCase("login password")) {
                        passtype = "0";
                    } else {
                        passtype = "1";
                    }
                    ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(),
                            userIdTxt.getText().toString(), passtype,
                            AccountDetails.getBrokerId(getActivity()), BaseURL, getActivity(), serviceResponseHandler);


                } else if (changePasswordResponse.getErrorCode().equals("0")) {

                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.GREEK_CHANGE_PASSWORD_SUCCESS_TXT), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");


                            if (PassExpiryType.equalsIgnoreCase("BothPass")) {

                                viewPager.setCurrentItem(1, true);

                            } else if (PassExpiryType.equalsIgnoreCase("LoginPass")) {

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            } else {

                                viewPager.setCurrentItem(1, true);


                            }

                        }
                    });

                    oldPwdTxt.setText("");
                    newPwdTxt.setText("");
                }
                if (changePasswordResponse.getErrorCode().equals("2")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.GREEK_INCORRECT_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("4")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.GREEK_DUPLICATE_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("5")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.GREEK_MAX_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("9")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.GREE_ID_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }
                if (changePasswordResponse.getErrorCode().equals("10")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.GREE_LOGIN_TRANSC_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }

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
}

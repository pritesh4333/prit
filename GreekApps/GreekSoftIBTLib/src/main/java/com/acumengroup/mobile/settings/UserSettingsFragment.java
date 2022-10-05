package com.acumengroup.mobile.settings;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordRequest;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.greekmain.util.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class UserSettingsFragment extends GreekBaseFragment implements OnClickListener {

    private View mChangePwdView;
    private GreekEditText oldPwdTxt;
    private GreekEditText newPwdTxt;
    private GreekEditText confirmPwdTxt, userIdTxt;
    private GreekButton updateBtn;
    boolean result = false;
    private static GreekBaseFragment previousFragment;
    static String passType;
    String reqPassType = "",alert_title;

    public static UserSettingsFragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        passType = type;
        UserSettingsFragment fragment = new UserSettingsFragment();
        fragment.setArguments(args);
        UserSettingsFragment.previousFragment = previousFragment;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChangePwdView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.change_password).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.change_password).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_CHANGEPASSWORD_SCREEN;
        hideAppTitle();
        setupViews();
        setupListeners();

        return mChangePwdView;
    }

    private void setupViews() {
        String packagname =getActivity().getPackageName();
        if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
            alert_title="Vishwas";
        }else  if (packagname.equalsIgnoreCase("com.tradeongo.mobile")){
            alert_title="Trade On Go";
        }else  if (packagname.equalsIgnoreCase("com.mandot.mobile")){
            alert_title="Mandot";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            alert_title="MSFL";
        }else  if (packagname.equalsIgnoreCase("com.clicktrade.mobile")){
            alert_title="Pentagon";
        }
        userIdTxt = mChangePwdView.findViewById(R.id.usrId_text);
        userIdTxt.setText(AccountDetails.getUsername(getMainActivity()));
        userIdTxt.setEnabled(false);
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
            //changePassBgLabel.setBackgroundColor(getResources().getColor(R.color.black));

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
                showProgress();

                String passtype = "";
                if (passType.equalsIgnoreCase("login password")) {
                    passtype = "0";
                    reqPassType = "0";
                } else {
                    passtype = "1";
                    reqPassType = "1";
                }
                ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(), AccountDetails.getUsername(getMainActivity()), passtype, AccountDetails.getBrokerId(getActivity()), "", getMainActivity(), serviceResponseHandler);
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
        if ("".equals(oldPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(CP_OLD_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if ("".equals(newPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(CP_NEW_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if ("".equals(confirmPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (newPwdTxt.getText().toString().equals(oldPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(CP_OLD_NEW_SAME_MSG), "OK", true, null);
            return false;
        } else if (!newPwdTxt.getText().toString().trim().equals(confirmPwdTxt.getText().toString().trim())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREE_OLD_NEW_PASS_MSG), "OK", true, null);
            return false;
        } else if (newPwdTxt.getText().toString().length() < 8) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_LIMIT_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if (!validpass) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_ALPHANUMERIC_MSG), "OK", true, null);
            return false;
        } else if (!validateSpecialSymbols()) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_SYMBOL_MSG), "OK", true, null);
            return false;
        }

        return true;
    }

    @Override
    public void handleResponse(Object response) {
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
                        reqPassType = "0";
                    } else {
                        passtype = "1";
                        reqPassType = "1";
                    }
                    ChangePasswordRequest.sendRequest(oldPwdTxt.getText().toString(), newPwdTxt.getText().toString(),
                            AccountDetails.getUsername(getMainActivity()), passtype, AccountDetails.getBrokerId(getActivity()),
                            BaseURL, getMainActivity(), serviceResponseHandler);


                } else if (changePasswordResponse.getErrorCode().equals("0")) {

                    if (reqPassType.equalsIgnoreCase("1")) {
                        SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                        editor.putString("GREEK_RETAINED_CUST_TRANS_PASS", newPwdTxt.getText().toString());
                        editor.apply();
                        editor.commit();
                    }

                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_CHANGE_PASSWORD_SUCCESS_TXT), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                            //Logging out user
                            if (reqPassType.equalsIgnoreCase("0")) {
                                ((GreekBaseActivity) getMainActivity()).doLogout(0);
                            }

                        }
                    });

                    oldPwdTxt.setText("");
                    newPwdTxt.setText("");
                }
                if (changePasswordResponse.getErrorCode().equals("2")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_INCORRECT_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("4")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_DUPLICATE_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("5")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_MAX_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("9")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREE_ID_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }
                if (changePasswordResponse.getErrorCode().equals("10")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREE_LOGIN_TRANSC_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }
            }
            hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {
        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && CHANGE_PWD_SVC_NAME.equals(jsonResponse.getServiceName())) {
            hideProgress();
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, jsonResponse.getInfoMsg(), "OK", true, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    oldPwdTxt.setText("");
                    newPwdTxt.setText("");
                    confirmPwdTxt.setText("");
                    //Logging out user
                    ((GreekBaseActivity) getMainActivity()).doLogout(0);
                }
            });
        }
    }

    public void setPassType(String position) {
        passType = position;
    }
}
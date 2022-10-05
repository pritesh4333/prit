package com.acumengroup.mobile.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendForgotPasswordResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.SetNewPasswordRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class SetTransactionPasswordFragment extends GreekBaseFragment implements View.OnClickListener {

    private View transactionPwdView;
    private GreekEditText newPwdTxt;
    private GreekEditText confirmPwdTxt;
    private GreekButton updateBtn;
    boolean result = false;
    private static GreekBaseFragment previousFragment;
    static String passType,alert_title;
    Bundle args;
    CustomTransactionDialogFragment customTransactionDialogFragment;

    public static SetTransactionPasswordFragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        passType = type;
        SetTransactionPasswordFragment fragment = new SetTransactionPasswordFragment();
        fragment.setArguments(args);
        SetTransactionPasswordFragment.previousFragment = previousFragment;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        transactionPwdView = super.onCreateView(inflater, container, savedInstanceState);

        if(AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.activity_setpassword).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.activity_setpassword).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_CHANGEPASSWORD_SCREEN;

        hideAppTitle();
        setupViews();
        setupListeners();

        return transactionPwdView;
    }

    private void setupViews() {
        args = getArguments();
        customTransactionDialogFragment = new CustomTransactionDialogFragment();
        newPwdTxt = transactionPwdView.findViewById(R.id.newPwd_text);
        confirmPwdTxt = transactionPwdView.findViewById(R.id.confirmPwd_text);
        updateBtn = transactionPwdView.findViewById(R.id.updateBtn);

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
    }

    private void setupListeners() {
        updateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(updateBtn.equals(v)) {
            if(validateFields()) {
                showProgress();

                SetNewPasswordRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), "1", newPwdTxt.getText().toString(), "", getMainActivity(), serviceResponseHandler);
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

        if(result == true) {
            return result;
        } else
            return result;
    }

    private boolean validateFields() {
        boolean b = false, cb = false, validpass = false;
        for (int i = 0; i < newPwdTxt.getText().toString().length(); i++) {
            char c = newPwdTxt.getText().toString().charAt(i);
            if(Character.isLetter(c)) {
                b = true;
            } else if(Character.isDigit(c)) {
                cb = true;
            }
        }
        validpass = (b && cb);

        if("".equals(newPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(CP_NEW_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if("".equals(confirmPwdTxt.getText().toString())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if(!newPwdTxt.getText().toString().trim().equals(confirmPwdTxt.getText().toString().trim())) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREE_OLD_NEW_PASS_MSG), "OK", true, null);
            return false;
        } else if(newPwdTxt.getText().toString().length() < 8) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_LIMIT_PASSWORD_EMPTY_MSG), "OK", true, null);
            return false;
        } else if(!validpass) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_ALPHANUMERIC_MSG), "OK", true, null);
            return false;
        } else if(!validateSpecialSymbols()) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.CP_SYMBOL_MSG), "OK", true, null);
            return false;
        }

        return true;
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            SendForgotPasswordResponse sendForgotPasswordResponse = (SendForgotPasswordResponse) jsonResponse.getResponse();

            if("Login".equalsIgnoreCase(jsonResponse.getServiceGroup()) &&
                    "jsetPassword_mf".equalsIgnoreCase(jsonResponse.getServiceName())) {


                if(sendForgotPasswordResponse.getErrorCode().equals("21")) {


                    String domainName = sendForgotPasswordResponse.getDomainName();
                    int domainPort = Integer.parseInt(sendForgotPasswordResponse.getDomainPort());
                    String BaseURL = "";

                    if(sendForgotPasswordResponse.getIsSecure().equalsIgnoreCase("true")) {

                        BaseURL = "https" + "://" + domainName + ":" + domainPort;

                    } else {

                        BaseURL = "http" + "://" + domainName + ":" + domainPort;

                    }

                    SetNewPasswordRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), "1",
                            newPwdTxt.getText().toString(), BaseURL, getMainActivity(), serviceResponseHandler);

                }


                if(sendForgotPasswordResponse.getErrorCode().equals("0")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_CHANGE_PASSWORD_SUCCESS_TXT), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            //navigateTo(NAV_TO_TRADE_SCREEN, args, false);
                            goBackOnce();

                        }
                    });

                    newPwdTxt.setText("");
                    confirmPwdTxt.setText("");
                }
                if(sendForgotPasswordResponse.getErrorCode().equals("2")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_INCORRECT_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if(sendForgotPasswordResponse.getErrorCode().equals("4")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_DUPLICATE_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if(sendForgotPasswordResponse.getErrorCode().equals("5")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREEK_MAX_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if(sendForgotPasswordResponse.getErrorCode().equals("9")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREE_ID_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }
                if(sendForgotPasswordResponse.getErrorCode().equals("10")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.GREE_LOGIN_TRANSC_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
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
        if(LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && CHANGE_PWD_SVC_NAME.equals(jsonResponse.getServiceName())) {
            hideProgress();
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), jsonResponse.getInfoMsg(), "OK", true, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    newPwdTxt.setText("");
                    confirmPwdTxt.setText("");
                    //Logging out user
                    ((GreekBaseActivity) getMainActivity()).doLogout(0);
                }
            });
        }
    }

}

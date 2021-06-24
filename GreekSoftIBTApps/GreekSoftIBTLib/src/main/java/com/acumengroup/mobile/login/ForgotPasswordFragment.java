package com.acumengroup.mobile.login;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.model.forgotpassword.ForgotPasswordRequest;
import com.acumengroup.greekmain.core.model.forgotpassword.ForgotPasswordResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendForgotPasswordResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class ForgotPasswordFragment extends GreekBaseFragment implements View.OnClickListener {
    private GreekEditText userIdTxt, pan_text, email_text;
    private Button submitBtn, cancelBtn;
    private ScrollView forgotPassBglabel;
    static String passType;
    private View mforgotPasswordView;
    private static GreekBaseFragment previousFragment;
    CustomOTPDialogFragment customOTPDialogFragment;
    String userCode, usr_panNo, usr_email,alert_tiltle;


    public static ForgotPasswordFragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        passType = type;
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        fragment.setArguments(args);
        ForgotPasswordFragment.previousFragment = previousFragment;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mforgotPasswordView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.new_forgot_password).setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
        } else {
            attachLayout(R.layout.new_forgot_password);
        }
        AccountDetails.currentFragment = NAV_TO_CHANGEPASSWORD_SCREEN;
        setupViews();
        setupListeners();
        String packagname =getActivity().getPackageName();
        if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
            alert_tiltle="Vishwas";
        }else  if (packagname.equalsIgnoreCase("com.tog.mobile")){
            alert_tiltle="Trade On Go";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            alert_tiltle="MSFL";
        }

        return mforgotPasswordView;
    }


    private void setupViews() {
        GreekTextView header = mforgotPasswordView.findViewById(R.id.forgotPwdHeader);

        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        header.setTypeface(font, Typeface.NORMAL);
        header.setText(R.string.FP_HEADER_TXT);


        customOTPDialogFragment = new CustomOTPDialogFragment();
        userIdTxt = mforgotPasswordView.findViewById(R.id.usrId_text);
        pan_text = mforgotPasswordView.findViewById(R.id.pan_text);
        email_text = mforgotPasswordView.findViewById(R.id.email_text);
        InputFilter[] filters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        userIdTxt.setFilters(filters);
        pan_text.setFilters(filters);
        userIdTxt.setText("");
        submitBtn = mforgotPasswordView.findViewById(R.id.cp_submit_btn);
        cancelBtn = mforgotPasswordView.findViewById(R.id.cp_cancel_btn);
        forgotPassBglabel = mforgotPasswordView.findViewById(R.id.forgotPassBg);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            forgotPassBglabel.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
            header.setTextColor(getResources().getColor(R.color.black));
        }
        pan_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("Length....!!!!!!!!!!", i2 + "   " + pan_text.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (pan_text.length() == 10) {
                    if (!isValidPan(pan_text.getText().toString())) {
                        pan_text.setError("Enter valid Pan Number");
                        return;
                    }
                }
            }
        });



        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            //userIdTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            userIdTxt.setTextColor(getResources().getColor(R.color.black));
            pan_text.setTextColor(getResources().getColor(R.color.black));
            email_text.setTextColor(getResources().getColor(R.color.black));

        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

            //userIdTxt.setTextColor(getResources().getColor(textColor));
            userIdTxt.setTextColor(getResources().getColor(R.color.white));
            pan_text.setTextColor(getResources().getColor(R.color.white));
            email_text.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void setupListeners() {
        submitBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cp_submit_btn) {
            sendForgotPassRequest();
        } else if (id == R.id.cp_cancel_btn) {
            userIdTxt.setText("");
            getActivity().onBackPressed();
        }
    }


    private void sendForgotPassRequest() {
        boolean toProceed = true;
        String messageToShow = "";
        userCode = userIdTxt.getText().toString();
        usr_panNo = pan_text.getText().toString();
        usr_email = email_text.getText().toString();
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(pan_text.getText().toString());

        if (userCode.isEmpty()) {
            userIdTxt.setError(getString(R.string.FP_USER_ID_EMPTY_MSG));
            userIdTxt.requestFocus();
//            messageToShow = getString(R.string.FP_USER_ID_EMPTY_MSG);
//            toProceed = false;
        } else if (usr_panNo.isEmpty()) {
            pan_text.setError(getString(R.string.FP_PAN_NO_EMPTY));
            pan_text.requestFocus();
//            messageToShow = getString(R.string.FP_PAN_NO_EMPTY);
//            toProceed = false;
        } else if (!matcher.matches()) {
            pan_text.setError("Enter valid pan number");
//            messageToShow = "Enter valid pan number";
            pan_text.requestFocus();
//            toProceed = false;
        } else if (pan_text.getText().toString().equals("") || pan_text.getText().toString().length() < 10) {
            pan_text.setError("Enter valid pan number");
//            messageToShow = "Enter valid pan number";
            pan_text.requestFocus();
//            toProceed = false;
        } else if (usr_email.isEmpty()) {
            email_text.setError(getString(R.string.FP_EMAIL_ID_EMPTY));
            email_text.requestFocus();
//            messageToShow = getString(R.string.FP_EMAIL_ID_EMPTY);
//            toProceed = false;
        } else if (!usr_email.equalsIgnoreCase("") && !isEmailValid(usr_email)) {
            toProceed = false;
            email_text.setError("Enter valid Email Id");
//            messageToShow = "Enter valid Email Id";
            email_text.requestFocus();
        }
        if (toProceed) {
            String passtype = "";
            if (passType.equalsIgnoreCase("login password")) {
                passtype = "0";
            } else {
                passtype = "1";
            }

            ForgotPasswordRequest.sendRequest(userCode,usr_panNo,usr_email,"", passtype, "", getMainActivity(), serviceResponseHandler);
        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;


        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && FORGOT_PASSWORD_PAN_EMAIL_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                JSONObject jsonObject = jsonResponse.getResponseData();
                String errorCode = jsonObject.getString("ErrorCode");

                if (errorCode.equals("0")) {

                    String passtype = "";
                    if (passType.equalsIgnoreCase("login password")) {
                        passtype = "0";

                    } else {
                        passtype = "1";

                    }

                    Toast.makeText(getMainActivity(), "OTP Sent", Toast.LENGTH_LONG).show();


                    userIdTxt.setText("");
                    pan_text.setText("");
                    email_text.setText("");

                    Bundle args = new Bundle();
                    args.putString("gscid", userCode);
                    args.putString("usr_panNo", usr_panNo);
                    args.putString("usr_email", usr_email);
                    args.putString("passType", passtype);
                    args.putString("from", "loginpage");
                    customOTPDialogFragment.setArguments(args);
                    customOTPDialogFragment.show(getFragmentManager(), "otp");
                    customOTPDialogFragment.setCancelable(false);


                } else if (errorCode.equals("3")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, alert_tiltle, "Invalid Credentials.", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {


                            }
                        }
                    });

                }

              /*  SendForgotPasswordResponse sendForgotPasswordResponse = (SendForgotPasswordResponse) jsonResponse.getResponse();
                String errorCode = sendForgotPasswordResponse.getErrorCode();
                if (errorCode.equals("0")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, alert_tiltle getString(R.string.LP_FORGOT_ANSWER), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                    userIdTxt.setText("");
                    pan_text.setText("");
                    email_text.setText("");
                }

*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "jchange_password_mf".equalsIgnoreCase(jsonResponse.getServiceName())) {
            try {

                ForgotPasswordResponse forgotPasswordResponse = (ForgotPasswordResponse) jsonResponse.getResponse();
                String errorCode = forgotPasswordResponse.getErrorCode();

                if (errorCode.equals("21")) {


                    String domainName = forgotPasswordResponse.getDomainName();
                    int domainPort = Integer.parseInt(forgotPasswordResponse.getDomainPort());
                    String BaseURL = "";

                    if (forgotPasswordResponse.getIsSecure().equalsIgnoreCase("true")) {

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

                    ForgotPasswordRequest.sendRequest(userCode,usr_panNo, usr_email, "1", passtype, BaseURL, getMainActivity(), serviceResponseHandler);


                } else if (errorCode.equals("0")) {
                    String passtype = "";
                    if (passType.equalsIgnoreCase("login password")) {
                        passtype = "0";

                    } else {
                        passtype = "1";

                    }

                    Toast.makeText(getMainActivity(), "OTP Sent", Toast.LENGTH_LONG).show();
                    userIdTxt.setText("");
                    pan_text.setText("");
                    email_text.setText("");

                    Bundle args = new Bundle();
                    args.putString("gscid", userCode);
                    args.putString("usr_panNo", usr_panNo);
                    args.putString("usr_email", usr_email);
                    args.putString("passType", passtype);
                    args.putString("from", "loginpage");
                    customOTPDialogFragment.setArguments(args);
                    customOTPDialogFragment.show(getFragmentManager(), "otp");
                    customOTPDialogFragment.setCancelable(false);


                } else if (errorCode.equals("13")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, alert_tiltle, getString(R.string.LP_INVALID_CLIENT_NAME), "Ok", false, new GreekDialog.DialogListener() {

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
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        if (action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(getMainActivity(), 0, alert_tiltle, msg, "OK", true, null);
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
        GreekDialog.alertDialog(getMainActivity(), 0, alert_tiltle, message, "OK", true, new GreekDialog.DialogListener() {

            @Override
            public void alertDialogAction(GreekDialog.Action action, Object... data) {
            }
        });
        userIdTxt.setText("");
        pan_text.setText("");
        email_text.setText("");
    }

    @Override
    public void onFragmentPause() {
        userIdTxt.setText("");
        pan_text.setText("");
        email_text.setText("");
        super.onFragmentPause();
    }

    public void setPassType(String position) {
        if (userIdTxt != null)
            userIdTxt.setText("");
        if (pan_text != null)
            pan_text.setText("");
        if (email_text != null)
            email_text.setText("");
        passType = position;
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

    public boolean isValidPan(String pan) {
        Pattern mPattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

        Matcher mMatcher = mPattern.matcher(pan);
        return mMatcher.matches();
    }
}

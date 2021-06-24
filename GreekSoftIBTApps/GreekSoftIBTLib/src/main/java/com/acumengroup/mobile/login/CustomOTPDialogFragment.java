package com.acumengroup.mobile.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.forgotpassword.ForgotPasswordRequest;
import com.acumengroup.greekmain.core.model.forgotpassword.ForgotPasswordResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ForgotPasswordOTPResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.ForgotPasswordOtpRequest;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.menu.MenuGetter;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class CustomOTPDialogFragment extends DialogFragment implements GreekUIServiceHandler, GreekConstants {
    private View customOtpDialogView;
    private Button ok_btn, cancel_btn;
    private GreekEditText otp_edittext;
    private RelativeLayout transactionlayout;
    private ProgressBar progressBar;
    private Bundle args;
    private ServiceResponseHandler serviceResponseHandler;
    GreekBaseFragment greek;
    private GreekTextView transcation_pass_id, resendOTPtxt;
    private String gscid, passType, enterOTP,usr_panNo, usr_email,alert_title;


    public CustomOTPDialogFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customOtpDialogView = inflater.inflate(R.layout.custom_otp_popup, container, false);
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        greek = new GreekBaseFragment();
        args = new Bundle();
        args = getArguments();
        gscid = args.getString("gscid");
        usr_email = args.getString("usr_email");
        usr_panNo = args.getString("usr_panNo");
        passType = args.getString("passType");

        resendOTPtxt = customOtpDialogView.findViewById(R.id.txt_resend_otp);
        ok_btn = customOtpDialogView.findViewById(R.id.button_submit_otp);
        cancel_btn = customOtpDialogView.findViewById(R.id.button_cancel_otp);
        otp_edittext = customOtpDialogView.findViewById(R.id.otp_edittext);
        transcation_pass_id = customOtpDialogView.findViewById(R.id.otp_id);
        otp_edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
        transactionlayout = customOtpDialogView.findViewById(R.id.transaction_layout);
        progressBar = customOtpDialogView.findViewById(R.id.progressBars);

        otp_edittext.setText("");
        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            transactionlayout.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
            resendOTPtxt.setTextColor(getResources().getColor(R.color.black));
            otp_edittext.setTextColor(getResources().getColor(R.color.black));
            otp_edittext.setHintTextColor(getResources().getColor(R.color.black));

        } else {
            transactionlayout.setBackgroundColor(getResources().getColor(R.color.light_black));
            resendOTPtxt.setTextColor(getResources().getColor(R.color.white));
            otp_edittext.setTextColor(getResources().getColor(R.color.white));
            otp_edittext.setHintTextColor(getResources().getColor(R.color.white));
        }
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!otp_edittext.getText().toString().trim().equals(""))) {
                    progressBar.setVisibility(View.VISIBLE);

                    enterOTP = otp_edittext.getText().toString();
                    ForgotPasswordOtpRequest.sendRequest(gscid, passType, enterOTP, "", getActivity(), serviceResponseHandler);
                    otp_edittext.setText("");

                } else {

                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_OTP_EMPTY_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }

                        }
                    });
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_edittext.setText("");
                dismiss();
            }
        });


        resendOTPtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgotPasswordRequest.sendRequest(gscid, usr_panNo,usr_email,"", passType, "", getActivity(), serviceResponseHandler);


            }
        });

        String packagname =getActivity().getPackageName();
        if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
            alert_title="Vishwas";
        }else  if (packagname.equalsIgnoreCase("com.tog.mobile")){
            alert_title="Trade On Go";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            alert_title="MSFL";
        }
        return customOtpDialogView;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;


        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "jchange_password_mf".equalsIgnoreCase(jsonResponse.getServiceName())) {
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

                    ForgotPasswordRequest.sendRequest(gscid, "1","","", passType, BaseURL, getActivity(), serviceResponseHandler);


                } else if (errorCode.equals("0")) {

                    Toast.makeText(getActivity(), "OTP Sent", Toast.LENGTH_LONG).show();

//                    GreekDialog.alertDialog(getMainActivity(), 0, alert_title, getString(R.string.LP_FORGOT_ANSWER), "Ok", false, new GreekDialog.DialogListener() {
//
//                        @Override
//                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
//                            if (action == GreekDialog.Action.OK) {
//                            }
//                        }
//                    });
//                    userIdTxt.setText("");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            ForgotPasswordOTPResponse forgotPasswordOTPResponse;
            try {
                progressBar.setVisibility(View.GONE);
                forgotPasswordOTPResponse = (ForgotPasswordOTPResponse) jsonResponse.getResponse();
                String errorCode = forgotPasswordOTPResponse.getErrorCode();


                if (errorCode.equals("21")) {


                    String domainName = forgotPasswordOTPResponse.getDomainName();
                    int domainPort = Integer.parseInt(forgotPasswordOTPResponse.getDomainPort());
                    String BaseURL = "";

                    if (forgotPasswordOTPResponse.getIsSecure().equalsIgnoreCase("true")) {

                        BaseURL = "https" + "://" + domainName + ":" + domainPort;

                    } else {

                        BaseURL = "http" + "://" + domainName + ":" + domainPort;

                    }
                    ForgotPasswordOtpRequest.sendRequest(gscid, passType, enterOTP, BaseURL,
                            getActivity(), serviceResponseHandler);

                } else if (errorCode.equals("1")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_TRANS_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                } else if (errorCode.equals("2")) {
                    //getString(R.string.CP_USER_PASSWORD_INVALID_MSG)
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, "Invalid OTP", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("3")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("4")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("5")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.TP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("7")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_INACTIVE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("8")) {
                    GreekDialog.alertDialog(getActivity(), 0, alert_title, getString(R.string.CP_INVALID_2FA_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("0")) {

                    dismiss();
                    if (args.getString("from").equalsIgnoreCase("loginpage")) {
                        Intent i = new Intent(getActivity(), SetPasswordActivity.class);
                        i.putExtra("gscid", gscid);
                        i.putExtra("passType", passType);
                        startActivity(i);
                    } else {
                        navigateTo(NAV_TO_TRANS_PASS, args, true);
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void navigateTo(int id, Bundle bundle, final boolean addStack) {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager.isAcceptingText() && getActivity().getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        GreekBaseFragment fragment = MenuGetter.getFragmentById(id);
        if (fragment != null && bundle != null) {
            fragment.setArguments(bundle);
        }
        addFragment(R.id.activityFrameLayout, fragment, addStack);
    }

    private void addFragment(int containerViewId, Fragment fragment, boolean addStack) {
        ((GreekBaseActivity) getActivity()).addFragment(containerViewId, fragment, addStack);
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


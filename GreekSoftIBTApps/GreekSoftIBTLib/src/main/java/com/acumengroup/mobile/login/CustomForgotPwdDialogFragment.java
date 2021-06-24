package com.acumengroup.mobile.login;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.forgotpassword.ForgotPasswordRequest;
import com.acumengroup.greekmain.core.model.forgotpassword.ForgotPasswordResponse;
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

public class CustomForgotPwdDialogFragment extends DialogFragment implements GreekUIServiceHandler, GreekConstants {
    private View customForgotPwdDialog;
    private Button ok_btn, cancel_btn;
    private GreekEditText clientcode_txt;
    private RelativeLayout progressLayout;
    private RelativeLayout transactionlayout;
    Bundle args;
    private ServiceResponseHandler serviceResponseHandler;
    GreekBaseFragment greek;
    private GreekTextView title;
    CustomTransactionDialogFragment customTransactionDialogFragment;
    CustomOTPDialogFragment customOTPDialogFragment;

    public CustomForgotPwdDialogFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customForgotPwdDialog = inflater.inflate(R.layout.custom_forgotpwd_dialog_layout, container, false);
        customForgotPwdDialog.setMinimumHeight(250);
        customForgotPwdDialog.setMinimumWidth(350);
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        customTransactionDialogFragment = new CustomTransactionDialogFragment();
        customOTPDialogFragment = new CustomOTPDialogFragment();
        greek = new GreekBaseFragment();
        args = new Bundle();
        args = getArguments();
        ok_btn = customForgotPwdDialog.findViewById(R.id.button_ok_transc);
        cancel_btn = customForgotPwdDialog.findViewById(R.id.button_cancel_transc);
        clientcode_txt = customForgotPwdDialog.findViewById(R.id.clientcode_edittext);
        title = customForgotPwdDialog.findViewById(R.id.title_dialog);
        clientcode_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        progressLayout = customForgotPwdDialog.findViewById(R.id.customProgress);
        transactionlayout = customForgotPwdDialog.findViewById(R.id.transaction_layout);
        clientcode_txt.setText(AccountDetails.getUsername(getActivity()));
        clientcode_txt.setEnabled(false);
        title.setText("Forgot Password");

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            transactionlayout.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            clientcode_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            clientcode_txt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        }
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressLayout.setVisibility(View.VISIBLE);

                ForgotPasswordRequest.sendRequest(AccountDetails.getUsername(getActivity()),"","", "1", "1", "",
                        getActivity(), serviceResponseHandler);

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                customTransactionDialogFragment.setArguments(args);
                customTransactionDialogFragment.show(getFragmentManager(), "transaction");
                customTransactionDialogFragment.setCancelable(false);

            }
        });


        return customForgotPwdDialog;

    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        progressLayout.setVisibility(View.GONE);

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
                    ForgotPasswordRequest.sendRequest(AccountDetails.getUsername(getActivity()),"","", "1", "1", BaseURL,
                            getActivity(), serviceResponseHandler);


                } else if (errorCode.equals("0")) {

                    Toast.makeText(getActivity(), "OTP Sent", Toast.LENGTH_LONG).show();

                    dismiss();
                    args.putString("from", "transactionpage");
                    args.putString("gscid", AccountDetails.getUsername(getActivity()));
                    args.putString("passType", "1");
                    customOTPDialogFragment.setArguments(args);
                    customOTPDialogFragment.show(getFragmentManager(), "otp");
                    customOTPDialogFragment.setCancelable(false);
                    //navigateTo(NAV_TO_TRANS_PASS,args,false);

                } else if (errorCode.equals("3")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.LP_INVALID_CLIENT_NAME), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
                //goToStartupPage();
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


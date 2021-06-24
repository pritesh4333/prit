package com.acumengroup.mobile.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendTranscationPasswordRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendTranscationPasswordResponse;
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

/**
 * Created by sushant.patil on 3/29/2016.
 */
public class CustomTransactionDialogFragment extends DialogFragment implements GreekUIServiceHandler, GreekConstants {
    private View customTransactionView;
    private Button ok_btn, cancel_btn;
    private GreekEditText transcation_pass;
    private RelativeLayout transactionlayout;
    private ProgressBar progressBar;
    Bundle args;
    private ServiceResponseHandler serviceResponseHandler;
    GreekBaseFragment greek;
    private GreekTextView transcation_pass_id, forgotpasswordtxt;
    CustomForgotPwdDialogFragment customForgotPwdDialogFragment;

    public CustomTransactionDialogFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customTransactionView = inflater.inflate(R.layout.custom_transaction_alert, container, false);
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        customForgotPwdDialogFragment = new CustomForgotPwdDialogFragment();
        greek = new GreekBaseFragment();
        args = new Bundle();
        args = getArguments();

        forgotpasswordtxt = customTransactionView.findViewById(R.id.txt_forgot_trans_pwd);
        ok_btn = customTransactionView.findViewById(R.id.button_ok_transc);
        cancel_btn = customTransactionView.findViewById(R.id.button_cancel_transc);
        transcation_pass = customTransactionView.findViewById(R.id.transcation_pass_edittext);
        transcation_pass_id = customTransactionView.findViewById(R.id.transcation_pass_id);
        transcation_pass.setImeOptions(EditorInfo.IME_ACTION_DONE);
        transactionlayout = customTransactionView.findViewById(R.id.transaction_layout);
        progressBar = customTransactionView.findViewById(R.id.progressBars);

        transcation_pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

//                    proceedToLogin();
                    hideKeyboard(getActivity());
                }
                return false;
            }
        });


        transcation_pass.setText("");

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            transactionlayout.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            transcation_pass_id.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            transcation_pass.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            transcation_pass.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            forgotpasswordtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!transcation_pass.getText().toString().trim().equals(""))) {
                    progressBar.setVisibility(View.VISIBLE);

                    SendTranscationPasswordRequest.sendRequest(AccountDetails.getUsername(getActivity()), AccountDetails.getSessionId(getActivity()), transcation_pass.getText().toString(), AccountDetails.getBrokerId(getActivity()), AccountDetails.getSessionId(getActivity()), "1", getActivity(), serviceResponseHandler);
                    transcation_pass.setText("");
                } else {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.CP_TRANSPASSWORD_EMPTY_MSG), "Ok", false, new GreekDialog.DialogListener() {

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
                transcation_pass.setText("");
                dismiss();
            }
        });


        forgotpasswordtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                customForgotPwdDialogFragment.setArguments(args);
                customForgotPwdDialogFragment.show(getFragmentManager(), "Forgot Password");
                customForgotPwdDialogFragment.setCancelable(false);

            }
        });
        return customTransactionView;

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
        SendTranscationPasswordResponse sendTranscationPasswordResponse;
        try {
            progressBar.setVisibility(View.GONE);
            sendTranscationPasswordResponse = (SendTranscationPasswordResponse) jsonResponse.getResponse();
            String errorCode = sendTranscationPasswordResponse.getErrorCode();
            if (sendTranscationPasswordResponse.getExecutionCode() != null && sendTranscationPasswordResponse.getExecutionCode().equals("0")) {
                if (errorCode.equals("1")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.CP_TRANS_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

//                                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
//                                intent.putExtra("from", "transactionalert");
//                                intent.putExtra("brokerid", AccountDetails.getBrokerId(getActivity()));
//                                intent.putExtra("userCode", AccountDetails.getUsername(getActivity()));
//                                transcation_pass.setText("");
//                                startActivity(intent);


                                Intent intent = new Intent(getActivity(), PasswordChangeActivity.class);
                                intent.putExtra("from", "transactionalert");
                                intent.putExtra("Comingfrom", "PlaceOrder");
                                intent.putExtra("brokerid", AccountDetails.getBrokerId(getActivity()));
                                intent.putExtra("userCode", AccountDetails.getUsername(getActivity()));
                                intent.putExtra("PassExpiryType", "TransPass");
                                startActivity(intent);
                            }
                        }
                    });
                }
                else if (errorCode.equals("2")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.CP_USER_TPASSWORD_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                }
                else if (errorCode.equals("3")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("4")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("5")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.TP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("7")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.CP_INACTIVE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("8")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.CP_INVALID_2FA_MSG), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                            }
                        }
                    });
                } else if (errorCode.equals("0")) {
                    dismiss();
                    //EventBus.getDefault().post("orderpreview");
                    navigateTo(NAV_TO_ORDER_PREVIEW_SCREEN, args, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onDismiss(DialogInterface dialog)
    {
        InputMethodManager imm =
                (InputMethodManager)transcation_pass.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        super.onDismiss(dialog);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        } else {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

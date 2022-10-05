package com.acumengroup.mobile.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.bankdetail.BankDetailRequest;
import com.acumengroup.greekmain.core.model.bankdetail.BankDetailResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MyTextWatcher;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class UserCreationBankDetails extends GreekBaseFragment implements View.OnClickListener, Step, BlockingStep {
    GreekEditText ifscCode, bankAcc, cnfbankacc, nomineeName, bankName;
    GreekTextView bankaddress;
    Spinner spnr_nominee_relation, spnr_default_bank, spnr_bank_account_type;
    Button btnsubmit;
    private ArrayAdapter relationAdapter, defaultBankAdapter, accTypeAdapter;
    private ArrayList<String> relationlist, defaultBankLisrt, acctypelist;
    private BankDetailResponse bankResponse;
    String from = "";
    private boolean nextScreen_flag = false;
    private StepperLayout.OnNextClickedCallback onNextClickedCB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        attachLayout(R.layout.fragment_bank_details);
        SetupView(mfActionView);
        if (getArguments() != null) {
            from = getArguments().getString("from");
        }


        relationlist = new ArrayList<>();
        relationlist.add("Select Nominee Relation");
        relationlist.add("Wife");
        relationlist.add("Husband");
        relationlist.add("Father");
        relationlist.add("Mother");
        relationlist.add("Son");
        relationlist.add("Daughter");
        relationlist.add("Brother");
        relationlist.add("Sister");
        relationlist.add("Grand Son");
        relationlist.add("Grand Daughter");
        relationAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, relationlist);
        relationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_nominee_relation.setAdapter(relationAdapter);


        defaultBankLisrt = new ArrayList<>();
        defaultBankLisrt.add("Select Default Bank");
        defaultBankLisrt.add("Yes");
        defaultBankLisrt.add("No");
        defaultBankAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, defaultBankLisrt);
        defaultBankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_default_bank.setAdapter(defaultBankAdapter);


        acctypelist = new ArrayList<>();
        acctypelist.add("Select Account Type");
        acctypelist.add("Saving Bank");
        acctypelist.add("Current Bank");
        accTypeAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, acctypelist);
        accTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_bank_account_type.setAdapter(accTypeAdapter);

        btnsubmit.setOnClickListener(this);

        String submit = Util.getPrefs(getActivity()).getString("bankDetailSubmit", " ");

        if (!submit.equalsIgnoreCase("Yes")) {
            btnsubmit.setEnabled(true);
            btnsubmit.setClickable(true);

            ifscCode.setEnabled(true);
            bankAcc.setEnabled(true);
            cnfbankacc.setEnabled(true);
            nomineeName.setEnabled(true);
            spnr_nominee_relation.setEnabled(true);
            spnr_default_bank.setEnabled(true);
            spnr_bank_account_type.setEnabled(true);

        } else {
            btnsubmit.setEnabled(false);
            btnsubmit.setClickable(false);
            ifscCode.setEnabled(false);
            bankAcc.setEnabled(false);
            cnfbankacc.setEnabled(false);
            nomineeName.setEnabled(false);
            spnr_nominee_relation.setEnabled(false);
            spnr_default_bank.setEnabled(false);
            spnr_bank_account_type.setEnabled(false);
        }

        return mfActionView;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void SetupView(View mfActionView) {
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        ifscCode = mfActionView.findViewById(R.id.ed_ifsc_code);
        //ifscCode.setImeOptions(EditorInfo.IME_ACTION_DONE);
        InputFilter[] filters = {new InputFilter.LengthFilter(11), new InputFilter.AllCaps()};
        ifscCode.setFilters(filters);
        bankName = mfActionView.findViewById(R.id.ed_bank_name);
        bankAcc = mfActionView.findViewById(R.id.ed_acc_number);
        cnfbankacc = mfActionView.findViewById(R.id.ed_cnf_acc_number);
        nomineeName = mfActionView.findViewById(R.id.ed_nominee);
        btnsubmit = mfActionView.findViewById(R.id.btn_bank_submit);
        bankaddress = mfActionView.findViewById(R.id.txt_bank_address);
        spnr_nominee_relation = mfActionView.findViewById(R.id.spnr_nominee_relation);
        spnr_default_bank = mfActionView.findViewById(R.id.spnr_default_bank);
        spnr_bank_account_type = mfActionView.findViewById(R.id.spnr_bank_type);

        ifscCode.addTextChangedListener(new MyTextWatcher(ifscCode));
        bankAcc.addTextChangedListener(new MyTextWatcher(bankAcc));
        cnfbankacc.addTextChangedListener(new MyTextWatcher(cnfbankacc));
        nomineeName.addTextChangedListener(new MyTextWatcher(nomineeName));

    }

    @Override
    public void onClick(View view) {

        submitBankDetails();
    }

    private void submitBankDetails() {

        String acctype = "";

        if (TextUtils.isEmpty(ifscCode.getText().toString().trim())) {
            ifscCode.setError("Enter IFSC code");
            ifscCode.requestFocus();

        } else if (TextUtils.isEmpty(bankName.getText().toString().trim())) {
            bankName.setError("Enter Bank Name");
            bankName.requestFocus();

        } else if (TextUtils.isEmpty(bankAcc.getText().toString().trim())) {
            bankAcc.setError("Enter bank account number");
            bankAcc.requestFocus();

        } else if (TextUtils.isEmpty(cnfbankacc.getText().toString().trim())) {
            cnfbankacc.setError("Enter confirm bank account number");
            cnfbankacc.requestFocus();

        } else if (!bankAcc.getText().toString().equals(cnfbankacc.getText().toString())) {

            cnfbankacc.setError("Confirm account number does not match.");
            cnfbankacc.requestFocus();


        } else if (TextUtils.isEmpty(nomineeName.getText().toString().trim())) {

            nomineeName.setError("Enter Nominee name");
            nomineeName.requestFocus();

        } else if (spnr_bank_account_type.getSelectedItemPosition() == 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please select account type ", "OK", true, null);
            return;

        } else if (spnr_default_bank.getSelectedItemPosition() == 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please select default bank type ", "OK", true, null);
            return;

        } else if (spnr_nominee_relation.getSelectedItemPosition() == 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please select nominee relation ", "OK", true, null);
            return;

        } else {

            String bank = "";
            if (bankaddress.getText().toString() != null) {
                bank = bankaddress.getText().toString();
            }

            String ifsc = ifscCode.getText().toString();
            String bankname = bankName.getText().toString();
            String bankacc = bankAcc.getText().toString();
            String nominee = nomineeName.getText().toString();
            String nomineerelation = spnr_nominee_relation.getSelectedItem().toString();
            String pan = Util.getPrefs(getActivity()).getString("panno", "NA");

            String flagVal;
            if (spnr_default_bank.getSelectedItem().toString().equals("Yes")) {
                flagVal = "Y";
            } else {
                flagVal = "N";
            }


            if (spnr_bank_account_type.getSelectedItem().toString().equals("Saving Bank")) {
                acctype = "SB";
            } else {
                acctype = "CB";
            }


            SharedPreferences.Editor editor = Util.getPrefs(getActivity()).edit();
            editor.putString("bankDetailSubmit", "yes");
            editor.putString("Ifsc", ifsc);
            editor.putString("bankname", bankname);
            editor.putString("BankAccountNumber", bankacc);
            editor.putString("NomineeName", nominee);
            editor.putInt("AccountType", spnr_bank_account_type.getSelectedItemPosition());
            editor.putInt("DefaultBankAccount", spnr_default_bank.getSelectedItemPosition());
            editor.putInt("NomineeRelation", spnr_nominee_relation.getSelectedItemPosition());
            editor.apply();
            editor.commit();

            showProgress();
            BankDetailRequest.sendRequest(ifsc, bank, bankacc, bankname, nominee, nomineerelation, pan, acctype, flagVal, getActivity(), serviceResponseHandler);
        }
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            hideProgress();
            bankResponse = (BankDetailResponse) jsonResponse.getResponse();
            String status = bankResponse.getStatus();

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, status, "Ok", true, new GreekDialog.DialogListener() {

                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    if (action == GreekDialog.Action.OK) {

                        Bundle args = new Bundle();
                        args.putString("from", from);

                        nextScreen_flag = true;

                        if (onNextClickedCB != null) {
                            onNextClicked(onNextClickedCB);
                        }
                    }
                }
            });


            btnsubmit.setClickable(false);
            btnsubmit.setEnabled(false);
            btnsubmit.setBackgroundResource(R.color.grey_text);
            ifscCode.setEnabled(false);
            bankName.setEnabled(false);
            bankAcc.setEnabled(false);
            cnfbankacc.setEnabled(false);
            nomineeName.setEnabled(false);
            spnr_nominee_relation.setEnabled(false);
            spnr_default_bank.setEnabled(false);
            spnr_bank_account_type.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
        String submit = Util.getPrefs(getActivity()).getString("bankDetailSubmit", "");

        if (!submit.equalsIgnoreCase("Yes")) {
            btnsubmit.setEnabled(true);
            btnsubmit.setClickable(true);

            ifscCode.setEnabled(true);
            bankName.setEnabled(true);
            bankAcc.setEnabled(true);
            cnfbankacc.setEnabled(true);
            nomineeName.setEnabled(true);
            spnr_nominee_relation.setEnabled(true);
            spnr_default_bank.setEnabled(true);
            spnr_bank_account_type.setEnabled(true);


        } else {
            btnsubmit.setEnabled(false);
            btnsubmit.setClickable(false);

            ifscCode.setEnabled(false);
            bankName.setEnabled(false);
            bankAcc.setEnabled(false);
            cnfbankacc.setEnabled(false);
            nomineeName.setEnabled(false);
            spnr_nominee_relation.setEnabled(false);
            spnr_default_bank.setEnabled(false);
            spnr_bank_account_type.setEnabled(false);
        }
        String Ifsc = Util.getPrefs(getActivity()).getString("Ifsc", " ");
        String BankName = Util.getPrefs(getActivity()).getString("bankname", " ");
        String BankAccountNumber = Util.getPrefs(getActivity()).getString("BankAccountNumber", " ");
        String NomineeName = Util.getPrefs(getActivity()).getString("NomineeName", " ");

        int AccountType = Util.getPrefs(getActivity()).getInt("AccountType", 0);
        int DefaultBankAccount = Util.getPrefs(getActivity()).getInt("DefaultBankAccount", 0);
        int NomineeRelation = Util.getPrefs(getActivity()).getInt("NomineeRelation", 0);


        ifscCode.setText(Ifsc);
        bankName.setText(BankName);
        bankAcc.setText(BankAccountNumber);
        cnfbankacc.setText(BankAccountNumber);
        nomineeName.setText(NomineeName);

        spnr_default_bank.setSelection(DefaultBankAccount);
        spnr_bank_account_type.setSelection(AccountType);
        spnr_nominee_relation.setSelection(NomineeRelation);

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback onNextClickedCallback) {

        onNextClickedCB = onNextClickedCallback;

        if (nextScreen_flag) {

            onNextClickedCallback.goToNextStep();

        } else {
            submitBankDetails();
        }

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback onCompleteClickedCallback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback onBackClickedCallback) {

        onBackClickedCallback.goToPrevStep();

    }
}

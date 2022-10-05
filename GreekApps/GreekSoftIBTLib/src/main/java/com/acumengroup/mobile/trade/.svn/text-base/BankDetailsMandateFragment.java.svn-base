package com.acumengroup.mobile.trade;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.bankdetail.MandateDetailsRequest;
import com.acumengroup.greekmain.core.model.bankdetail.MandateDetailsResponse;
import com.acumengroup.greekmain.core.model.bankdetail.MandateRegistrationRequest;
import com.acumengroup.greekmain.core.model.bankdetail.MandateRegistrationResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BankDetailsMandateFragment extends GreekBaseFragment implements View.OnClickListener, Step {
    GreekEditText mandateamttxt;
    Button createmandatebtn;
    String from, clientCode = "";
    GreekTextView acctnotxt, accttypetxt, ifsccodetxt, bankname, bankbranch, banksetting, mandateidtxt, mandatetypetxt, amttxt;
    GreekTextView acctitle, acctypetitle, ifctitle, banktitle, bankbranchtitle, banksettintitle, mandatetitle, mandatetyptitle, amttitle, createmandatetitle, createmandatetypetitle, createamttitle;
    Spinner mandatetypeSpinner;
    private ArrayAdapter<String> mandatetypeAdapter;
    String cAccNoNo = "";
    CardView mandatedetaillayout, createmandatelayout, bankdetail;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createmandatebtn) {


            if (TextUtils.isEmpty(mandateamttxt.getText().toString())) {
                mandateamttxt.setError("Enter Amount");
            } else {
                sendRequest();
                //Send Request here
            }

        }


    }

    private void sendRequest() {

        String mandate = "";
        if (mandatetypeSpinner.getSelectedItem().toString().equalsIgnoreCase("E-mandate")) {
            mandate = "E";
        } else {
            mandate = "I";
        }
        String currentDate = getCurrentDate();
        MandateRegistrationRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), mandateamttxt.getText().toString(), acctnotxt.getText().toString(), accttypetxt.getText().toString(), mandate, "", ifsccodetxt.getText().toString(), currentDate, getMandateEndDate(currentDate), "06", getMainActivity(), serviceResponseHandler);
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    private String getMandateEndDate(String startDateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            Date startDate = sdf.parse(startDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, 100);


            return sdf.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return startDateStr;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        // attachLayout(R.layout.fragment_bankdetails_mandate);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_bankdetails_mandate).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_bankdetails_mandate).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        SetupView(mfActionView);

        if (getArguments() != null) {

            from = getArguments().getString("from");
            clientCode = getArguments().getString("clientCode");

            String cAccType = "", cNEFTIFSCCode = "", cBankName = "", cBankBranch = "", cDefaultBankFlag = "";

            if (getArguments().getString("tag").equals("1")) {

                cAccNoNo = getArguments().getString("cAccNoNo1");
                cAccType = getArguments().getString("cAccType1");
                cNEFTIFSCCode = getArguments().getString("cNEFTIFSCCode1");
                cBankName = getArguments().getString("cBankName1");
                cBankBranch = getArguments().getString("cBankBranch1");
                cDefaultBankFlag = getArguments().getString("cDefaultBankFlag1");


            }
            if (getArguments().getString("tag").equals("2")) {
                cAccNoNo = getArguments().getString("cAccNoNo2");
                cAccType = getArguments().getString("cAccType2");
                cNEFTIFSCCode = getArguments().getString("cNEFTIFSCCode2");
                cBankName = getArguments().getString("cBankName2");
                cBankBranch = getArguments().getString("cBankBranch2");
                cDefaultBankFlag = getArguments().getString("cDefaultBankFlag2");
            }
            if (getArguments().getString("tag").equals("3")) {
                cAccNoNo = getArguments().getString("cAccNoNo3");
                cAccType = getArguments().getString("cAccType3");
                cNEFTIFSCCode = getArguments().getString("cNEFTIFSCCode3");
                cBankName = getArguments().getString("cBankName3");
                cBankBranch = getArguments().getString("cBankBranch3");
                cDefaultBankFlag = getArguments().getString("cDefaultBankFlag3");
            }
            if (getArguments().getString("tag").equals("4")) {
                cAccNoNo = getArguments().getString("cAccNoNo4");
                cAccType = getArguments().getString("cAccType4");
                cNEFTIFSCCode = getArguments().getString("cNEFTIFSCCode4");
                cBankName = getArguments().getString("cBankName4");
                cBankBranch = getArguments().getString("cBankBranch4");
                cDefaultBankFlag = getArguments().getString("cDefaultBankFlag4");
            }
            if (getArguments().getString("tag").equals("5")) {
                cAccNoNo = getArguments().getString("cAccNoNo5");
                cAccType = getArguments().getString("cAccType5");
                cNEFTIFSCCode = getArguments().getString("cNEFTIFSCCode5");
                cBankName = getArguments().getString("cBankName5");
                cBankBranch = getArguments().getString("cBankBranch5");
                cDefaultBankFlag = getArguments().getString("cDefaultBankFlag5");
            }

            acctnotxt.setText(cAccNoNo);
            accttypetxt.setText(cAccType);
            ifsccodetxt.setText(cNEFTIFSCCode);
            bankname.setText(cBankName);
            bankbranch.setText(cBankBranch);
            banksetting.setText(cDefaultBankFlag);
        }

        getMandateDetails();

        return mfActionView;
    }

    private void getMandateDetails() {

        MandateDetailsRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), cAccNoNo, getMainActivity(), serviceResponseHandler);
    }

    private void SetupView(View mfActionView) {
        mandateamttxt = mfActionView.findViewById(R.id.mandateamttxt);
        acctnotxt = mfActionView.findViewById(R.id.acctnotxt);
        accttypetxt = mfActionView.findViewById(R.id.accttypetxt);
        ifsccodetxt = mfActionView.findViewById(R.id.ifsccodetxt);
        bankname = mfActionView.findViewById(R.id.bankname);
        bankbranch = mfActionView.findViewById(R.id.bankbranch);
        banksetting = mfActionView.findViewById(R.id.banksetting);
        mandateidtxt = mfActionView.findViewById(R.id.mandateidtxt);
        mandatetypetxt = mfActionView.findViewById(R.id.mandatetypetxt);
        amttxt = mfActionView.findViewById(R.id.amttxt);
        mandatetypeSpinner = mfActionView.findViewById(R.id.mandatetypeSpinner);
        createmandatebtn = mfActionView.findViewById(R.id.createmandatebtn);
        mandatedetaillayout = mfActionView.findViewById(R.id.mandatedetaillayout);
        createmandatelayout = mfActionView.findViewById(R.id.createmandatelayout);
        bankdetail = mfActionView.findViewById(R.id.bankdetail);
        acctitle = mfActionView.findViewById(R.id.acc_title);
        acctypetitle = mfActionView.findViewById(R.id.acctype_title);
        ifctitle = mfActionView.findViewById(R.id.ifsc_title);
        banktitle = mfActionView.findViewById(R.id.name_title);
        bankbranchtitle = mfActionView.findViewById(R.id.branch_title);
        banksettintitle = mfActionView.findViewById(R.id.setting_title);
        mandatetitle = mfActionView.findViewById(R.id.mandate_title);
        mandatetyptitle = mfActionView.findViewById(R.id.mandatetype_title);
        amttitle = mfActionView.findViewById(R.id.amt_title);
        createmandatetitle = mfActionView.findViewById(R.id.createmandate_title);
        createmandatetypetitle = mfActionView.findViewById(R.id.createmandatype_title);
        createamttitle = mfActionView.findViewById(R.id.createamt_title);

        createmandatebtn.setOnClickListener(this);

        ArrayList<String> mandatetypelist = new ArrayList<>();
        mandatetypelist.add("E-Mandate");
        mandatetypelist.add("I-Mandate");


        mandatetypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), mandatetypelist);
        mandatetypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        mandatetypeSpinner.setAdapter(mandatetypeAdapter);

    }


    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            if (jsonResponse.getServiceName().equalsIgnoreCase("getConnectToMF")) {
                MandateRegistrationResponse mandateRegistrationResponse = (MandateRegistrationResponse) jsonResponse.getResponse();
                GreekDialog.alertDialog(getMainActivity(), 0, "Mandate Order ", mandateRegistrationResponse.getStatus(), "Ok", true, null);
            } else if (jsonResponse.getServiceName().equalsIgnoreCase("getMandateDetails")) {
                MandateDetailsResponse mandateDetailsResponse = (MandateDetailsResponse) jsonResponse.getResponse();
                if (mandateDetailsResponse.getErrorCode().equalsIgnoreCase("3")) {
                    mandatedetaillayout.setVisibility(View.GONE);
                    createmandatelayout.setVisibility(View.VISIBLE);
                } else {
                    mandatedetaillayout.setVisibility(View.VISIBLE);
                    createmandatelayout.setVisibility(View.GONE);
                    mandateidtxt.setText(mandateDetailsResponse.getMandateId());
                    mandatetypetxt.setText(mandateDetailsResponse.getMandateType());
                    amttxt.setText(mandateDetailsResponse.getAmount());
                }
            }
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
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show err
        // or on EditText
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_BANK_MANDATE;
    }
}


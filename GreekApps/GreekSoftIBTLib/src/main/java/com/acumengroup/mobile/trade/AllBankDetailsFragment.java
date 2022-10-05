package com.acumengroup.mobile.trade;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.userloginvalidation.ValidateGuestResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class AllBankDetailsFragment extends GreekBaseFragment implements View.OnClickListener, Step {
    Button nextbtn, btn_previous;
    String from, clientCode = "";
    LinearLayout bankdetails;


    public static AllBankDetailsFragment newInstance(Bundle bundle, String param2) {
        AllBankDetailsFragment fragment = new AllBankDetailsFragment();
        Bundle args = bundle;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextbtn) {
            navigateTo(NAV_TO_MUTUALFUND_KYC_UPLOAD, getArguments(), false);

        } else if (view.getId() == R.id.btn_previous) {

            navigateTo(NAV_TO_MUTUALFUND_PERSONAL_DETAILS, getArguments(), false);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_all_bank_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_all_bank_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        SetupView(mfActionView);
        nextbtn.setOnClickListener(this);
        btn_previous.setOnClickListener(this);

        if (AccountDetails.getcUserType().equalsIgnoreCase("2") || AccountDetails.getcUserType().equalsIgnoreCase("1")) {
            nextbtn.setVisibility(View.VISIBLE);
        } else {
            nextbtn.setVisibility(View.GONE);
        }

        if (getArguments() != null) {

            from = getArguments().getString("from");
            clientCode = getArguments().getString("clientCode");

            if (!getArguments().getString("cAccNoNo1").equalsIgnoreCase("")) {

                String cAccNoNo1 = getArguments().getString("cAccNoNo1");
                String cAccType1 = getArguments().getString("cAccType1");
                String cNEFTIFSCCode1 = getArguments().getString("cNEFTIFSCCode1");
                String cBankName1 = getArguments().getString("cBankName1");
                String cBankBranch1 = getArguments().getString("cBankBranch1");
                String cDefaultBankFlag1 = getArguments().getString("cDefaultBankFlag1");

                createPositionsRow("Bank Name", cBankName1, "IFSC Code", cNEFTIFSCCode1, "Bank Account Number", cAccNoNo1, "Bank Branch", cBankBranch1, "Account Type", cAccType1, "Primary Bank", cDefaultBankFlag1, "1");
            } else if (!getArguments().getString("cAccNoNo2").equalsIgnoreCase("")) {
                String cAccNoNo2 = getArguments().getString("cAccNoNo2");
                String cAccType2 = getArguments().getString("cAccType2");
                String cNEFTIFSCCode2 = getArguments().getString("cNEFTIFSCCode2");
                String cBankName2 = getArguments().getString("cBankName2");
                String cBankBranch2 = getArguments().getString("cBankBranch2");
                String cDefaultBankFlag2 = getArguments().getString("cDefaultBankFlag2");

                createPositionsRow("Bank Name", cBankName2, "IFSC Code", cNEFTIFSCCode2, "Bank Account Number", cAccNoNo2, "Bank Branch", cBankBranch2, "Account Type", cAccType2, "Primary Bank", cDefaultBankFlag2, "2");
            } else if (!getArguments().getString("cAccNoNo3").equalsIgnoreCase("")) {

                String cAccNoNo3 = getArguments().getString("cAccNoNo3");
                String cAccType3 = getArguments().getString("cAccType3");
                String cNEFTIFSCCode3 = getArguments().getString("cNEFTIFSCCode3");
                String cBankName3 = getArguments().getString("cBankName3");
                String cBankBranch3 = getArguments().getString("cBankBranch3");
                String cDefaultBankFlag3 = getArguments().getString("cDefaultBankFlag3");
                createPositionsRow("Bank Name", cBankName3, "IFSC Code", cNEFTIFSCCode3, "Bank Account Number", cAccNoNo3, "Bank Branch", cBankBranch3, "Account Type", cAccType3, "Primary Bank", cDefaultBankFlag3, "3");
            } else if (!getArguments().getString("cAccNoNo4").equalsIgnoreCase("")) {

                String cAccNoNo4 = getArguments().getString("cAccNoNo4");
                String cAccType4 = getArguments().getString("cAccType4");
                String cNEFTIFSCCode4 = getArguments().getString("cNEFTIFSCCode4");
                String cBankName4 = getArguments().getString("cBankName4");
                String cBankBranch4 = getArguments().getString("cBankBranch4");
                String cDefaultBankFlag4 = getArguments().getString("cDefaultBankFlag4");

                createPositionsRow("Bank Name", cBankName4, "IFSC Code", cNEFTIFSCCode4, "Bank Account Number", cAccNoNo4, "Bank Branch", cBankBranch4, "Account Type", cAccType4, "Primary Bank", cDefaultBankFlag4, "4");
            } else if (!getArguments().getString("cAccNoNo5").equalsIgnoreCase("")) {

                String cAccNoNo5 = getArguments().getString("cAccNoNo5");
                String cAccType5 = getArguments().getString("cAccType5");
                String cNEFTIFSCCode5 = getArguments().getString("cNEFTIFSCCode5");
                String cBankName5 = getArguments().getString("cBankName5");
                String cBankBranch5 = getArguments().getString("cBankBranch5");
                String cDefaultBankFlag5 = getArguments().getString("cDefaultBankFlag5");
                createPositionsRow("Bank Name", cBankName5, "IFSC Code", cNEFTIFSCCode5, "Bank Account Number", cAccNoNo5, "Bank Branch", cBankBranch5, "Account Type", cAccType5, "Primary Bank", cDefaultBankFlag5, "5");
            }
        }


        return mfActionView;
    }

    private void SetupView(View mfActionView) {

        nextbtn = mfActionView.findViewById(R.id.nextbtn);
        btn_previous = mfActionView.findViewById(R.id.btn_previous);
        bankdetails = mfActionView.findViewById(R.id.bankdetails);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            bankdetails.setBackgroundColor(getResources().getColor(R.color.backgroundStripColorWhite));
        } else {
            bankdetails.setBackgroundColor(getResources().getColor(R.color.market_grey_light));

        }
    }

    private void createPositionsRow(String bankname, String banknamestr, String ifsccode, String ifsccodestr, String acctNo, String acctNoStr, String bankbranch, String bankbranchstr, String accttype, String accttypestr, String primarybank, String primarybnkstr, String tag) {

        CardView card = new CardView(getActivity());

        // Set the CardView layoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        card.setLayoutParams(params);

        // Set CardView corner radius
        card.setRadius(9);

        // Set cardView content padding
        card.setContentPadding(10, 10, 10, 10);

        // Set a background color for CardView
        //card.setCardBackgroundColor(Color.parseColor("#FFffff"));

        // Set the CardView maximum elevation
        card.setMaxCardElevation(15);
        card.setTag(tag);
        // Set CardView elevation
        card.setCardElevation(9);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////Do some job on button click
                Bundle args = new Bundle();
                if (v.getTag().equals("1")) {

                    args.putString("cAccNoNo1", getArguments().getString("cAccNoNo1"));
                    args.putString("cAccType1", getArguments().getString("cAccType1"));
                    args.putString("cNEFTIFSCCode1", getArguments().getString("cNEFTIFSCCode1"));
                    args.putString("cBankName1", getArguments().getString("cBankName1"));
                    args.putString("cBankBranch1", getArguments().getString("cBankBranch1"));
                    args.putString("cDefaultBankFlag1", getArguments().getString("cDefaultBankFlag1"));
                    args.putString("tag", "1");
                    navigateTo(NAV_TO_MUTUALFUND_BANK_MANDATE, args, true);
                }
                if (v.getTag().equals("2")) {
                    args.putString("cAccNoNo2", getArguments().getString("cAccNoNo2"));
                    args.putString("cAccType2", getArguments().getString("cAccType2"));
                    args.putString("cNEFTIFSCCode2", getArguments().getString("cNEFTIFSCCode2"));
                    args.putString("cBankName2", getArguments().getString("cBankName2"));
                    args.putString("cBankBranch2", getArguments().getString("cBankBranch2"));
                    args.putString("cDefaultBankFlag2", getArguments().getString("cDefaultBankFlag2"));
                    args.putString("tag", "2");
                    navigateTo(NAV_TO_MUTUALFUND_BANK_MANDATE, null, true);
                }
                if (v.getTag().equals("3")) {
                    args.putString("cAccNoNo3", getArguments().getString("cAccNoNo3"));
                    args.putString("cAccType3", getArguments().getString("cAccType3"));
                    args.putString("cNEFTIFSCCode3", getArguments().getString("cNEFTIFSCCode3"));
                    args.putString("cBankName3", getArguments().getString("cBankName3"));
                    args.putString("cBankBranch3", getArguments().getString("cBankBranch3"));
                    args.putString("cDefaultBankFlag3", getArguments().getString("cDefaultBankFlag3"));
                    args.putString("tag", "3");
                    navigateTo(NAV_TO_MUTUALFUND_BANK_MANDATE, null, true);
                }
                if (v.getTag().equals("4")) {
                    args.putString("cAccNoNo4", getArguments().getString("cAccNoNo4"));
                    args.putString("cAccType4", getArguments().getString("cAccType4"));
                    args.putString("cNEFTIFSCCode4", getArguments().getString("cNEFTIFSCCode4"));
                    args.putString("cBankName4", getArguments().getString("cBankName4"));
                    args.putString("cBankBranch4", getArguments().getString("cBankBranch4"));
                    args.putString("cDefaultBankFlag4", getArguments().getString("cDefaultBankFlag4"));
                    args.putString("tag", "4");
                    navigateTo(NAV_TO_MUTUALFUND_BANK_MANDATE, null, true);
                }
                if (v.getTag().equals("5")) {
                    args.putString("cAccNoNo5", getArguments().getString("cAccNoNo5"));
                    args.putString("cAccType5", getArguments().getString("cAccType5"));
                    args.putString("cNEFTIFSCCode5", getArguments().getString("cNEFTIFSCCode5"));
                    args.putString("cBankName5", getArguments().getString("cBankName5"));
                    args.putString("cBankBranch5", getArguments().getString("cBankBranch5"));
                    args.putString("cDefaultBankFlag5", getArguments().getString("cDefaultBankFlag5"));
                    args.putString("tag", "5");
                    navigateTo(NAV_TO_MUTUALFUND_BANK_MANDATE, null, true);
                }

            }
        });


        // Initialize a new TextView to put in CardView
        LinearLayout cardLinear = new LinearLayout(getMainActivity());
        LinearLayout.LayoutParams cl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardLinear.setOrientation(LinearLayout.VERTICAL);
        cardLinear.setLayoutParams(cl);


        LinearLayout row1 = new LinearLayout(getMainActivity());
        LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rp.weight = 2;
        row1.setLayoutParams(rp);
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(bankname);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        keyView.setLayoutParams(lp);
        keyView.setPadding(10, 10, 10, 10);

        GreekTextView valueView = new GreekTextView(getMainActivity());
        valueView.setPadding(10, 12, 5, 12);

        valueView.setText(banknamestr);
        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);

        valueView.setLayoutParams(lp);

        row1.addView(keyView);
        row1.addView(valueView);


        LinearLayout row2 = new LinearLayout(getMainActivity());
        row2.setLayoutParams(rp);
        GreekTextView keyView1 = new GreekTextView(getMainActivity());
        keyView1.setPadding(5, 12, 5, 12);
        keyView1.setText(ifsccode);
        keyView1.setLayoutParams(lp);

        keyView1.setPadding(10, 10, 10, 10);

        GreekTextView valueView1 = new GreekTextView(getMainActivity());
        valueView.setPadding(10, 12, 5, 12);

        valueView1.setText(ifsccodestr);

        valueView1.setTypeface(Typeface.DEFAULT_BOLD);
        valueView1.setGravity(GravityCompat.START);

        valueView1.setLayoutParams(lp);


        row2.addView(keyView1);
        row2.addView(valueView1);

        LinearLayout row3 = new LinearLayout(getMainActivity());
        row3.setLayoutParams(rp);
        GreekTextView keyView2 = new GreekTextView(getMainActivity());
        keyView2.setPadding(5, 12, 5, 12);
        keyView2.setText(acctNo);
        keyView2.setLayoutParams(lp);
        keyView2.setPadding(10, 10, 10, 10);

        GreekTextView valueView2 = new GreekTextView(getMainActivity());
        valueView2.setPadding(10, 12, 5, 12);

        valueView2.setText(acctNoStr);
        valueView2.setTypeface(Typeface.DEFAULT_BOLD);
        valueView2.setGravity(GravityCompat.START);

        valueView2.setLayoutParams(lp);

        row3.addView(keyView2);
        row3.addView(valueView2);


        LinearLayout row4 = new LinearLayout(getMainActivity());
        row4.setLayoutParams(rp);
        GreekTextView keyView3 = new GreekTextView(getMainActivity());
        keyView3.setPadding(5, 12, 5, 12);
        keyView3.setText(bankbranch);

        keyView3.setLayoutParams(lp);
        keyView3.setPadding(10, 10, 10, 10);

        GreekTextView valueView3 = new GreekTextView(getMainActivity());
        valueView3.setPadding(10, 12, 5, 12);

        valueView3.setText(bankbranchstr);
        valueView3.setTypeface(Typeface.DEFAULT_BOLD);
        valueView3.setGravity(GravityCompat.START);

        valueView3.setLayoutParams(lp);

        row4.addView(keyView3);
        row4.addView(valueView3);

        LinearLayout row5 = new LinearLayout(getMainActivity());
        row5.setLayoutParams(rp);
        GreekTextView keyView4 = new GreekTextView(getMainActivity());
        keyView4.setPadding(5, 12, 5, 12);
        keyView4.setText(accttype);

        keyView4.setLayoutParams(lp);
        keyView4.setPadding(10, 10, 10, 10);

        GreekTextView valueView4 = new GreekTextView(getMainActivity());
        valueView4.setPadding(10, 12, 5, 12);

        valueView4.setText(accttypestr);
        valueView4.setTypeface(Typeface.DEFAULT_BOLD);
        valueView4.setGravity(GravityCompat.START);

        valueView4.setLayoutParams(lp);

        row5.addView(keyView4);
        row5.addView(valueView4);


        LinearLayout row6 = new LinearLayout(getMainActivity());
        row6.setLayoutParams(rp);
        GreekTextView keyView5 = new GreekTextView(getMainActivity());
        keyView5.setPadding(5, 12, 5, 12);
        keyView5.setText(primarybank);

        keyView5.setLayoutParams(lp);
        keyView5.setPadding(10, 10, 10, 10);

        GreekTextView valueView5 = new GreekTextView(getMainActivity());
        valueView5.setPadding(10, 12, 5, 12);

        valueView5.setText(primarybnkstr);

        valueView5.setTypeface(Typeface.DEFAULT_BOLD);
        valueView5.setGravity(GravityCompat.START);

        valueView5.setLayoutParams(lp);

        row6.addView(keyView5);
        row6.addView(valueView5);

        cardLinear.addView(row1);
        cardLinear.addView(row2);
        cardLinear.addView(row3);
        cardLinear.addView(row4);
        cardLinear.addView(row5);
        cardLinear.addView(row6);

        card.addView(cardLinear);
        bankdetails.addView(card);

        // Put the TextView in CardView
        //card.addView(tv);

        // Finally, add the CardView in root layout
        //bankdetails.addView(card);

        //changeColor = !changeColor;
    }


    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            ValidateGuestResponse validateGuestResponse = (ValidateGuestResponse) jsonResponse.getResponse();
            if (validateGuestResponse.getErrorCode().equalsIgnoreCase("0")) {
                Bundle args = new Bundle();
                args.putString("from", from);
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

        btn_previous.setVisibility(View.GONE);
        nextbtn.setVisibility(View.GONE);

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_ALL_BANKDETAILS_MF;
    }
}


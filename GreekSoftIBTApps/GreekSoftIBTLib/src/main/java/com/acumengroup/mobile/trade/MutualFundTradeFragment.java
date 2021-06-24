package com.acumengroup.mobile.trade;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.greekmain.core.model.MutualFundOrderLog;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MutualFundHoldingsModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MutualFundTradeFragment extends GreekBaseFragment {

    private final ArrayList<String> orderTypeValues = new ArrayList<>();
    private View mfTradeView;
    private GreekTextView symbolText, schemeText, mfType;
    private String schemeCode;
    private LinearLayout itemsLayout;
    private Spinner mfOrderTypeSpinner, DPTransactionTypeSpinner;
    private GreekEditText mfAmtEdit, folioNo;
    private GreekButton purchase_btn;
    private Double minAmt, maxAmt = 0.0;
    private final View.OnClickListener purchaseBtnClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            sendRequest();
        }
    };
    private Boolean changeColor = true;
    private GreekTextView schemeName, NavRs, returns, aum;
    private MaterialRatingBar ratingBar;


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_TRADE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mfTradeView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_placeorder_mf).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_placeorder_mf).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        setupViews();
        getFromIntent();

        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(1, 1);
        return mfTradeView;
    }

    public AlertDialog alertDialog(Context context, final int id, String title, Spanned message, String buttonText, boolean cancelable, final GreekDialog.DialogListener listener) {
        AlertDialog dialog = null;
        try {
            dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).setCancelable(cancelable).setNeutralButton(buttonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (listener != null) listener.alertDialogAction(GreekDialog.Action.OK, id);

                }
            }).show();
            dialog.setCanceledOnTouchOutside(cancelable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    private void setupViews() {
        symbolText = mfTradeView.findViewById(R.id.symbolText);
        schemeText = mfTradeView.findViewById(R.id.schemeText);
        itemsLayout = mfTradeView.findViewById(R.id.itemsLayout);
        mfOrderTypeSpinner = mfTradeView.findViewById(R.id.mfOrderTypeSpinner);
        DPTransactionTypeSpinner = mfTradeView.findViewById(R.id.DPTransactionType);
        mfAmtEdit = mfTradeView.findViewById(R.id.mfAmtEdit);
        folioNo = mfTradeView.findViewById(R.id.folioNumber);
        mfAmtEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(20, 2)});

        purchase_btn = mfTradeView.findViewById(R.id.purchase_btn);
        purchase_btn.setOnClickListener(purchaseBtnClicked);
        mfType = mfTradeView.findViewById(R.id.mfType);

        schemeName = mfTradeView.findViewById(R.id.schemeName);
        returns = mfTradeView.findViewById(R.id.returns);
        NavRs = mfTradeView.findViewById(R.id.NavRs);
        aum = mfTradeView.findViewById(R.id.aum);
        ratingBar = mfTradeView.findViewById(R.id.ratingBar);

        GreekTextView disclaimer = mfTradeView.findViewById(R.id.disclaimerTxt);
        disclaimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String eulaText = "";
                try {

                    eulaText = Util.read(getMainActivity().getAssets().open("MF_Disclaimer")).toString();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                final CharSequence formatEulaText = Html.fromHtml(eulaText);

                GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, formatEulaText.toString());

            }
        });


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            GreekTextView txt = mfTradeView.findViewById(R.id.txt_title);
            txt.setTextColor(getResources().getColor(R.color.black));
            mfAmtEdit.setTextColor(getResources().getColor(R.color.black));


        } else {

            GreekTextView txt = mfTradeView.findViewById(R.id.txt_title);
            txt.setTextColor(getResources().getColor(R.color.black));
            mfAmtEdit.setTextColor(getResources().getColor(R.color.black));
            mfOrderTypeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            DPTransactionTypeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));

        }


    }

    private void getFromIntent() {

        String title = "";
        if (getArguments() != null) {
            if (getArguments().getBoolean("isModifyOrder")) {
                title = "Modify Mutual Fund";

                MutualFundOrderLog fundOrderLog = (MutualFundOrderLog) getArguments().getSerializable("Response");

                schemeCode = fundOrderLog.getSchemeCode();
                symbolText.setText(fundOrderLog.getSchemeName());
                schemeText.setText(fundOrderLog.getAMCName());

                minAmt = Double.parseDouble(fundOrderLog.getMinPurAmt());
                maxAmt = Double.parseDouble(fundOrderLog.getMaxPurAmt());

                createPositionsRow("Reference Number", fundOrderLog.getRefNo());
                createPositionsRow("Min Purchase Amt *", fundOrderLog.getMinPurAmt());
                createPositionsRow("Max Purchase Amt *", fundOrderLog.getMaxPurAmt());

            } else {
                title = "Mutual Fund Trade";
                if (getArguments().getBoolean("isFromHoldings")) {

                    MutualFundHoldingsModel holdingsData = (MutualFundHoldingsModel) getArguments().getSerializable("HoldingsData");

                    symbolText.setText(holdingsData.getSchemeName());
                    schemeText.setText(holdingsData.getAMCName());

                    minAmt = Double.parseDouble(holdingsData.getMinPurAmt());
                    maxAmt = Double.parseDouble(holdingsData.getMaxPurAmt());

                    createPositionsRow("NAV ", holdingsData.getNAV());
                    createPositionsRow("ISIN", holdingsData.getISIN());
                    createPositionsRow("Min Purchase Amt *", holdingsData.getMinPurAmt());
                    createPositionsRow("Max Purchase Amt *", holdingsData.getMaxPurAmt());

                } else {
                    AvailableSchemeListModel mfSchemeData = (AvailableSchemeListModel) getArguments().getSerializable("Request");


                    if (mfSchemeData.getSchemeName() != null) {
                        symbolText.setText(mfSchemeData.getSchemeName());
                    }
                    schemeText.setText(mfSchemeData.getAMCName());

                    minAmt = Double.parseDouble(mfSchemeData.getMinPurchaseAmount());

                    if (mfSchemeData.getMaxPurchaseAmount() != null && !mfSchemeData.getMaxPurchaseAmount().contains("null")) {

                        maxAmt = Double.parseDouble(mfSchemeData.getMaxPurchaseAmount());
                    }

                    createPositionsRow("NAV " + "(" + mfSchemeData.getNAVDate() + ")", mfSchemeData.getNAV());
                    createPositionsRow("ISIN", mfSchemeData.getISIN());
                    createPositionsRow("Min Purchase Amt *", mfSchemeData.getMinPurchaseAmount());
                    createPositionsRow("Max Purchase Amt *", mfSchemeData.getMaxPurchaseAmount());
                }

            }
        }

        setAppTitle(getClass().toString(), title);
        if (getArguments() != null) {
            if (getArguments().getBoolean("isPurchaseOrder")) {
                orderTypeValues.add("Fresh");
                orderTypeValues.add("Additional");
                purchase_btn.setText("Purchase");
                mfType.setText("Purchase *");
            } else if (getArguments().getBoolean("isRedeemOrder")) {
                orderTypeValues.add("Redeem");
                purchase_btn.setText("Redeem");
                mfType.setText("Redeem");
            } else if (getArguments().getBoolean("isAdditionalPurchase")) {
                orderTypeValues.add("Additional");
                orderTypeValues.add("Fresh");
                purchase_btn.setText("Purchase");
                mfType.setText("Purchase *");
            }
        }

        ArrayAdapter<String> mfTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), orderTypeValues);
        mfTypeSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mfOrderTypeSpinner.setAdapter(mfTypeSpAdapter);
        mfOrderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                GreekTextView selectedText = (GreekTextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList dptransactionMode = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.dptransactionMode)));
        ArrayAdapter<String> dptransactionModeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), dptransactionMode);
        dptransactionModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DPTransactionTypeSpinner.setAdapter(dptransactionModeAdapter);

        DPTransactionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                GreekTextView selectedText = (GreekTextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void createPositionsRow(String key, String value) {

        int color = (changeColor) ? R.color.white : R.color.light_white;

        TableRow Row = new TableRow(getMainActivity());
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(10, 12, 5, 12);
        keyView.setText(key);
        keyView.setSingleLine(true);
        keyView.setTextColor(getResources().getColor(R.color.black));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) keyView.getLayoutParams();
        params.weight = 1;

        GreekTextView valueView = new GreekTextView(getMainActivity());
        valueView.setPadding(10, 12, 5, 12);
        valueView.setText(value);

        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if (key.equals("Action") && value.equals("Buy"))
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                valueView.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }else {
                valueView.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }
        valueView.setBackgroundColor(getResources().getColor(color));
        valueView.setSingleLine(true);
        valueView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) valueView.getLayoutParams();
        params1.weight = 1;

        Row.addView(keyView);
        Row.addView(valueView);
        itemsLayout.addView(Row);

        changeColor = !changeColor;
    }

    private void sendRequest() {
        if (Validations()) {
            String dpTransaction = "";
            if (DPTransactionTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cdsl")) {
                dpTransaction = "C";
            } else if (DPTransactionTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("nsdl")) {
                dpTransaction = "N";
            } else if (DPTransactionTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("physical")) {
                dpTransaction = "P";
            }


            if (mfOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Additional")) {
                if (folioNo.getText().toString().length() <= 0) {

                    folioNo.setError("Please enter folio number");
                    return;
                }

            }

            Bundle bundle = new Bundle();
            bundle.putString("folioNo", folioNo.getText().toString());
            bundle.putString("Amount", mfAmtEdit.getText().toString());
            bundle.putString("PurchaseType", mfOrderTypeSpinner.getSelectedItem().toString());
            bundle.putString("dpTransaction", dpTransaction);

            boolean b;
            if (getArguments().getBoolean("isModifyOrder")) {
                b = true;
                bundle.putSerializable("Request", getArguments().getSerializable("Response"));

            } else {
                b = false;
                if (getArguments().getBoolean("isFromHoldings")) {
                    bundle.putSerializable("Request", getArguments().getSerializable("HoldingsData"));
                    bundle.putBoolean("isFromHoldings", true);

                } else {
                    bundle.putSerializable("Request", getArguments().getSerializable("Request"));
                    bundle.putBoolean("isFromHoldings", false);

                }
            }
            bundle.putBoolean("isModifyOrder", b);

            InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager.isAcceptingText())
                inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            navigateTo(NAV_TO_MUTUALFUND_TRADE_PREVIEW, bundle, true);
        }

    }

    private boolean Validations() {
        if (mfAmtEdit.getText().toString().length() <= 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Amount cannot be empty", "OK", false, null);
            return false;
        } else if (mfAmtEdit.getText().toString().length() > 0 && ".".equals(mfAmtEdit.getText().toString())) {
            showAlertDialog(mfAmtEdit, "Invalid Amount");
            return false;
        } else if (Double.parseDouble(mfAmtEdit.getText().toString()) < minAmt || Double.parseDouble(mfAmtEdit.getText().toString()) > maxAmt) {

            if (maxAmt != null && maxAmt != 0) {

                showAlertDialog(mfAmtEdit, "Amount should be greater than " + minAmt + " and less than " + maxAmt);

                return false;
            } else if (Double.parseDouble(mfAmtEdit.getText().toString()) < minAmt) {
                showAlertDialog(mfAmtEdit, "Amount should be greater than " + minAmt);

                return false;

            } else return true;
        }

        return true;

    }

    private void showAlertDialog(final GreekEditText editText, String message) {
        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), message, "Ok", true, new GreekDialog.DialogListener() {

            @Override
            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                if (action == GreekDialog.Action.OK) {
                    editText.setText("");
                    editText.requestFocus();
                }

            }
        });
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        final Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches()) return "";
            return null;
        }

    }

}


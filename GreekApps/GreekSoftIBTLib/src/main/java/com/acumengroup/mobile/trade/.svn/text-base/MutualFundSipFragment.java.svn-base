package com.acumengroup.mobile.trade;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.format.Formatter;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.SipSummaryModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MutualFundSipFragment extends GreekBaseFragment {

    private final ArrayList<String> frequencyTypeList = new ArrayList<>();
    Bundle bundle;
    private AvailableSchemeListModel mfSchemeData;
    private ArrayList<String> sipDateList = new ArrayList<>();
    private ArrayAdapter<String> sipDateAdapter;
    private Spinner paymentModeSpinner, frequencyTypeSpinner, sipDateSpinner, transtnModeSpinner, dpTransModeSpinner, mandateIdSpinner;
    private GreekTextView tv_price_diff;
    private GreekTextView tv_52High;
    private GreekTextView tv_curr_date;
    private GreekTextView tv_52Low;
    private GreekTextView instAmtLabel;
    private GreekTextView noOfInstLabel;
    private GreekTextView sipAmtText;
    private GreekTextView isintitle, dptxntitle, transactiontitle;
    private GreekTextView tv_curr_nav;
    private GreekEditText sipNoOfInstText, folionumbertext;
    private double sipAmountMax, sipAmountMin;
    private long sipNoOfInstMax, sipNoOfInstMin;
    private CheckBox termChkBox;
    private ImageView iv_price_diff;
    private LinearLayout mandate_linear;
    private GreekTextView schemeName, NavRs, returns, aum;
    private MaterialRatingBar ratingBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        //attachLayout(R.layout.fragment_mf_sip);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mf_sip).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mf_sip).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        getFromIntent();
        GreekTextView tvTerms = mfActionView.findViewById(R.id.tvTerms);
        tv_curr_nav = mfActionView.findViewById(R.id.tv_curr_nav);
        GreekTextView tv_isin = mfActionView.findViewById(R.id.tv_isin);
        tv_price_diff = mfActionView.findViewById(R.id.tv_price_diff);
        tv_curr_date = mfActionView.findViewById(R.id.tv_curr_date);
        tv_52High = mfActionView.findViewById(R.id.tv_52High);
        tv_52Low = mfActionView.findViewById(R.id.tv_52Low);

        iv_price_diff = mfActionView.findViewById(R.id.iv_price_diff);

        termChkBox = mfActionView.findViewById(R.id.termChkBox);

        instAmtLabel = mfActionView.findViewById(R.id.instAmtLabel);
        sipAmtText = mfActionView.findViewById(R.id.sipAmtText);

        noOfInstLabel = mfActionView.findViewById(R.id.noOfInstLabel);
        sipNoOfInstText = mfActionView.findViewById(R.id.sipNoOfInstText);

        mandateIdSpinner = mfActionView.findViewById(R.id.mandateIdSpinner);
        paymentModeSpinner = mfActionView.findViewById(R.id.paymentModeSpinner);
        frequencyTypeSpinner = mfActionView.findViewById(R.id.frequencyTypeSpinner);
        transtnModeSpinner = mfActionView.findViewById(R.id.transaction_mode);
        dpTransModeSpinner = mfActionView.findViewById(R.id.dp_transaction_mode);
        mandate_linear = mfActionView.findViewById(R.id.mandate_linear);
        isintitle = mfActionView.findViewById(R.id.isiin_title);
        transactiontitle = mfActionView.findViewById(R.id.transaction_title);
        dptxntitle = mfActionView.findViewById(R.id.dptxn_title);
        folionumbertext = mfActionView.findViewById(R.id.tvSymbol);

        schemeName = mfActionView.findViewById(R.id.schemeName);
        returns = mfActionView.findViewById(R.id.returns);
        NavRs = mfActionView.findViewById(R.id.NavRs);
        aum = mfActionView.findViewById(R.id.aum);
        ratingBar = mfActionView.findViewById(R.id.ratingBar);

        sipDateSpinner = mfActionView.findViewById(R.id.sipDateSpinner);
        sipDateList.add(0, "Select SIP date");
        sipDateAdapter = new ArrayAdapter<>(getMainActivity(),R.layout.row_spinner_mutualfund, sipDateList);
        sipDateAdapter.setDropDownViewResource(R.layout.custom_spinner);
        sipDateSpinner.setAdapter(sipDateAdapter);
        sipDateSpinner.setSelection(0);
        sipDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button btnPlaceOrder = mfActionView.findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        List<String> mandatIdlist = AccountDetails.getMandatIdlist();

        if (mandatIdlist != null && mandatIdlist.size() >= 1) {

            if (!mandatIdlist.contains("Select Mandate ID")) {
                mandatIdlist.add(0, "Select Mandate ID");
            }
            mandate_linear.setVisibility(View.VISIBLE);
            mandateIdSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> mandateAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, mandatIdlist);
            mandateAdapter.setDropDownViewResource(R.layout.custom_spinner);
            mandateIdSpinner.setAdapter(mandateAdapter);
            mandateIdSpinner.setSelection(mandatIdlist.indexOf("Select Mandate ID"));
            mandateIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    TextView selectedText = (TextView) parent.getChildAt(0);
                    if (selectedText != null) {
                        selectedText.setTextColor(getResources().getColor(R.color.black));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } else {
            mandateIdSpinner.setVisibility(View.GONE);
            mandateIdSpinner = null;
            mandate_linear.setVisibility(View.GONE);
        }


        ArrayList paymentMode = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.paymentMode)));
        ArrayAdapter<String> paymentModeAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, paymentMode);
        paymentModeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        paymentModeSpinner.setAdapter(paymentModeAdapter);
        paymentModeSpinner.setSelection(0);
        paymentModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList transactionMode = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.transactionMode)));
        ArrayAdapter<String> transactionModeAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, transactionMode);
        transactionModeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        transtnModeSpinner.setAdapter(transactionModeAdapter);
        transtnModeSpinner.setSelection(1);
        transtnModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList dptransactionMode = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.dptransactionMode)));
        ArrayAdapter<String> dptransactionModeAdapter = new ArrayAdapter<>(getMainActivity(),R.layout.row_spinner_mutualfund, dptransactionMode);
        dptransactionModeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        dpTransModeSpinner.setAdapter(dptransactionModeAdapter);
        dpTransModeSpinner.setSelection(1);
        dpTransModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            // isintitle.setTextColor(getResources().getColor(R.color.black));
            // transactiontitle.setTextColor(getResources().getColor(R.color.black));
            // dptxntitle.setTextColor(getResources().getColor(R.color.black));
            tv_isin.setTextColor(getResources().getColor(R.color.black));
            // sipAmtText.setTextColor(getResources().getColor(R.color.black));
            //sipNoOfInstText.setTextColor(getResources().getColor(R.color.black));
            //folionumbertext.setTextColor(getResources().getColor(R.color.black));

        } else {
            // isintitle.setTextColor(getResources().getColor(R.color.white));
            // transactiontitle.setTextColor(getResources().getColor(R.color.white));
            // dptxntitle.setTextColor(getResources().getColor(R.color.white));
            tv_isin.setTextColor(getResources().getColor(R.color.white));
            //sipAmtText.setTextColor(getResources().getColor(R.color.white));
            //sipNoOfInstText.setTextColor(getResources().getColor(R.color.white));
            // termChkBox.setBackgroundColor(R.drawable.custom_checkbox_black);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                termChkBox.setButtonTintList(ContextCompat.getColorStateList(getMainActivity(), R.color.checkbox_tint));
            }


            //paymentModeSpinner, frequencyTypeSpinner, sipDateSpinner, transtnModeSpinner, dpTransModeSpinner, mandateIdSpinner;
            paymentModeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            sipDateSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            frequencyTypeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            transtnModeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            dpTransModeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));

            if (mandateIdSpinner != null)
                mandateIdSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));

        }

        mfSchemeData = (AvailableSchemeListModel) getArguments().getSerializable("Request");
        setAppTitle(getClass().toString(), mfSchemeData.getSchemeName());
        boolean sipOrder = getArguments().getBoolean("isSIPOrder");
        boolean redeemOrder = getArguments().getBoolean("isRedeemOrder");


        final SpannableStringBuilder sb = new SpannableStringBuilder("I have read the Terms and Condition & I accept the same");
        // Span to set text color to some RGB value
        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(251, 178, 48));
        // Span to make text bold
        final StyleSpan bss = new StyleSpan(Typeface.BOLD);
        // Set the text color for first 4 characters
        ClickableSpan termsNCondClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, "These Terms and Conditions, along with the Client Agreement and the Power of Attorney executed by the Client in favour of Greeksoft Securities Limited (GREEK), the contract between the Client and GREEK for availing of the Facility. Nothing contained herein shall be construed as derogatory to the terms of the Client Agreement and the Power of Attorney executed by the Client. By availing of the Systematic Investment Plan (SIP) facility, the Client acknowledges having read, understood and accepted these Terms and Conditions.\n" +
                        "Systematic Investment Plan (SIP) Facility Terms & Conditions:\n" +
                        "The Form should be completed in English and filled in Block Letters only. Please tick in the appropriate box , where boxes have been provided.\n" +
                        "The registered broking clients of Greeksoft Securities Ltd (GREEK) can only register for this Facility.\n" +
                        "SIP means facility offered by GREEK on its trading platform whereby the client authorizes GREEK to place buy transactions in specific securities/MFs/ETFs in predetermined quantities or amounts at periodic intervals over a fixed period of time.\n" +
                        "SIP Registration shall mean clients instruction(s) under the Facility specifying the different parameters for placement of SIP Orders by GREEK.\n" +
                        "The client agrees that an SIP request can be placed for only one security/MF/ETF at a time. To place SIP requests for multiple securities/MFs/ETFs, the client will be required to place separate SIP request for each security/MF/ETF.\n" +
                        "Frequency shall mean the time intervals specified by the clients in the SIP request placed under this Facility.\n" +
                        "Minimum Period shall mean the period specified by GREEK as the minimum period under the Facility for which the SIP request can be placed.\n" +
                        "Total Period for SIP shall mean the period commencing from EQUITY/MF/ETF SIP start date till the date of completion of the SIP period as indicated by the client in the SIP registration.\n" +
                        "Minimum Order Value shall mean the minimum amount specified by GREEK which the Client can select for the SIP Registration under the Facility.\n" +
                        "Order Placement day shall mean the day on which SIP orders will be placed by GREEK as per the frequency and other order related parameters selected by the Client.\n" +
                        "The no. of units to be purchased would be arrived at by dividing the net investible amount by the market price prevailing at the time order is entered by GREEK. The time of order entry shall be at the absolute discretion of GREEK. Any fractional no. of units would be ignored. Order would be placed for remaining quantity.\n" +
                        "The amount received through ECS/Direct Debit would be credited to the client’s broking ledger account and the actual cost of the buying of units along with brokerage, STT etc. including ECS / Direct Debit collection charges (if any) will be debited to client ledger account. Any Amount remaining unutilized, will be refunded by way of credit to the broking ledger account of the client maintained with Greeksoft Securities Ltd. Post Execution of transaction any amount recoverable from the client would be debited to his ledger account. Outstanding debit balance if any, needs to be cleared as per GREEK risk management policy.\n" +
                        "Net Investible amount arrived at after deducting the transaction costs as per broking agreement and ECS/Direct Debit Charges will be levied from the first ECS/Direct Debit installment for a year. Annual charge by Greeksoft Securities Ltd for ECS is Rs. 200 plus applicable service tax and Direct debit is Rs. 275 plus applicable service tax.\n" +
                        "Order/s of the client who opt for Ledger debit would be executed on the mandated date only when required amount is available in client ledger prior to execution of trade. In case there is a delay in receiving the funds GREEK may execute the trades in certain cases at its absolute discretion.\n" +
                        "Client can revoke the trade authorization issued by him anytime by giving a notice 30 day prior to the specified date for execution of trade.\n" +
                        "The Client agrees not to hold GREEK liable for any loss or damage incurred or suffered by it due to any delay, error, defect, failure or interruption in the provision of Facility arising from or caused by any reason whatsoever.\n" +
                        "GREEK, at its sole discretion, reserves the right to either temporarily or permanently, withdraw or suspend the SIP Facility at any time without giving any notice or assigning any reason for the same, whether in respect of one or more Clients. In case of a temporary withdrawal, the privileges may be reinstated by GREEK at its sole discretion.\n" +
                        "The client agrees that in case it is observed that SIP order of the client may not be placed for an uncertain / long period due to the disablement of the security/MF/ETF, GREEK may cancel the SIP request of the client under intimation to the client.\n" +
                        "The client agrees that GREEK will not be liable for compensation of any kind for non placement / rejection of the SIP orders in the above cases or for reasons beyond its control.\n" +
                        "The Client has the right to cancel / modify SIP at any time he/she so desires by giving online request through the trading platform three day prior to the due date of next SIP.\n");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
                ds.setColor(Color.rgb(251, 178, 48));
            }
        };
        sb.setSpan(termsNCondClick, 16, 35, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        //sb.setSpan(bss, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        tvTerms.setText(sb);
        tvTerms.setMovementMethod(LinkMovementMethod.getInstance());
        //customTextView(tvTerms);

        final AdapterView.OnItemSelectedListener frequencyTypeListSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));

                }

                if (position != 0) {
                    loadInstallmentAmount();
                    loadNoOfInstallment();
                    loadSipDate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };


        //SIP FREQUENCY
        String sipFreqUrl = "getSIP_Frequency?SchemeName=" + mfSchemeData.getSchemeName() + "&SchemeIsin=" + mfSchemeData.getISIN() + "&bseRTACode=" + mfSchemeData.getBseRTACode() + "&schemeCode=" + mfSchemeData.getSchemeCode() + "&bseCode=" + mfSchemeData.getBseCode();
        showProgress();

        WSHandler.getRequest(getMainActivity(), sipFreqUrl, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    frequencyTypeList.clear();
                    frequencyTypeList.add("Select SIP frequency");
                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        frequencyTypeList.add(jsonObject.get("sip_frequency").toString());
                    }


                    ArrayAdapter<String> frequencyTypeAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, frequencyTypeList);
                    frequencyTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
                    frequencyTypeSpinner.setAdapter(frequencyTypeAdapter);
                    frequencyTypeSpinner.setSelection(0);
                    frequencyTypeSpinner.setOnItemSelectedListener(frequencyTypeListSelectedListener);

                    getOverview();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });


        tv_isin.setText(mfSchemeData.getISIN());

        return mfActionView;
    }

    private void getOverview() {

        String sipMFOverview = "getMFOverview?mf_schcode=" + mfSchemeData.getSchemeCode();
        showProgress();

        WSHandler.getRequest(getMainActivity(), sipMFOverview, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {

                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        double price_diff = Double.valueOf(jsonObject.getString("priceDiff"));
                        double per_change = Double.valueOf(jsonObject.getString("PerChange"));


                        tv_curr_nav.setText(jsonObject.getString("CurrNAV"));
                        tv_price_diff.setText(df.format(price_diff) + "( " + df.format(per_change) + " % )");

                        //tv_curr_date.setText(DateTimeFormatter.getDateFromTimeStamp(jsonObject.getString("CurrNavDate"), "dd MMM yyyy", "bse"));
                        tv_curr_date.setText(jsonObject.getString("CurrNavDate"));
                        tv_52High.setText(jsonObject.getString("nav52WH"));
                        tv_52Low.setText(jsonObject.getString("nav52WL"));

                        tv_price_diff.setText(df.format(price_diff) + "( " + df.format(per_change) + " % )");

                        if (Double.valueOf(df.format(price_diff)) > 0) {
                            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                tv_price_diff.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                            }else {
                                tv_price_diff.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                            }
                            iv_price_diff.setImageResource(R.drawable.up_arrow);

                        } else {
                            tv_price_diff.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                            iv_price_diff.setImageResource(R.drawable.down_arrow_red);

                        }
                        //tv_curr_date.setText("NAV as on " + DateTimeFormatter.getDateFromTimeStamp(jsonObject.getString("CurrNavDate"), "dd MMM yyyy", "bse"));
                        tv_curr_date.setText("NAV as on " + jsonObject.getString("CurrNavDate"));


                        if (jsonObject.getString("starRating") != null) {

                            float rateing = Float.parseFloat(jsonObject.getString("starRating"));

                            if (rateing < 0) {
                                ratingBar.setVisibility(View.INVISIBLE);

                            } else {

                                ratingBar.setRating(rateing);

                            }
                        } else {

                            ratingBar.setVisibility(View.INVISIBLE);
                        }

                        schemeName.setText(jsonObject.getString("SchemeName"));
                        // holder.category.setText(product.getCategory());
                        NavRs.setText(String.format("%.2f", Double.parseDouble(jsonObject.getString("CurrNAV"))));
                        String value = String.format("%.2f", Double.parseDouble(jsonObject.getString("threeYearRet")));
                        returns.setText(value + "%");
                        aum.setText(String.format("%.2f", Double.parseDouble(jsonObject.getString("AssetSize"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    Log.d("Exception", ex.toString());
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
        hideProgress();
    }

    private void loadInstallmentAmount() {
        //GET SIP AMOUNT RANGE
        String sipFrequency = frequencyTypeSpinner.getSelectedItem().toString();
        String sipInstAmtUrl = "getSIP_Installment_AmountRange?SchemeName=" + mfSchemeData.getSchemeName() + "&SchemeIsin=" + mfSchemeData.getISIN() + "&Sipfrequency=" + sipFrequency + "&bseRTACode=" + mfSchemeData.getBseRTACode() + "&schemeCode=" + mfSchemeData.getSchemeCode() + "&bseCode=" + mfSchemeData.getBseCode();
        showProgress();

        WSHandler.getRequest(getMainActivity(), sipInstAmtUrl, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    JSONObject jsonObject = respCategory.getJSONObject(0);

                    sipAmountMin = Double.parseDouble(jsonObject.get("Minimum_Amt").toString());
                    sipAmountMax = Double.parseDouble(jsonObject.get("Maximum_Amt").toString());

                    String message = sipAmountMin + "-" + sipAmountMax;
                    instAmtLabel.setText(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    private void loadNoOfInstallment() {
        String sipFrequency = frequencyTypeSpinner.getSelectedItem().toString();
        String sipInstAmtUrl = "getInstallment_SIP_AmountNumbers?SchemeName=" + mfSchemeData.getSchemeName() + "&SchemeIsin=" + mfSchemeData.getISIN() + "&Sipfrequency=" + sipFrequency + "&bseRTACode=" + mfSchemeData.getBseRTACode() + "&schemeCode=" + mfSchemeData.getSchemeCode() + "&bseCode=" + mfSchemeData.getBseCode();
        showProgress();

        WSHandler.getRequest(getMainActivity(), sipInstAmtUrl, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    JSONObject jsonObject = respCategory.getJSONObject(0);
                    sipNoOfInstMin = Long.parseLong(jsonObject.get("SIPMinInstallmentNo").toString());
                    sipNoOfInstMax = Long.parseLong(jsonObject.get("SIPMaxInstallmentNo").toString());

                    noOfInstLabel.setText("Minimum no = " + sipNoOfInstMin + " and Maximum no = " + sipNoOfInstMax);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    private void loadSipDate() {
        String sipFrequency = frequencyTypeSpinner.getSelectedItem().toString();
        String sipDateUrl = "getSIP_DATE?SchemeName=" + mfSchemeData.getSchemeName() + "&SchemeIsin=" + mfSchemeData.getISIN() + "&Sipfrequency=" + sipFrequency + "&bseRTACode=" + mfSchemeData.getBseRTACode() + "&schemeCode=" + mfSchemeData.getSchemeCode() + "&bseCode=" + mfSchemeData.getBseCode();
        showProgress();

        WSHandler.getRequest(getMainActivity(), sipDateUrl, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    sipDateList.clear();
                    sipDateAdapter.clear();

                    List<String> newList = new ArrayList<>();

                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        String dates = jsonObject.get("sip_date").toString();

                        newList = Arrays.asList(dates.split(","));
                    }

                    if (newList.size() > 0) {
                        sipDateList = buildDateList(newList);
                    }
                    sipDateList.add(0, "Select SIP date");
                    sipDateAdapter.addAll(sipDateList);
                    sipDateAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    /**
     * this return next month date if the passed day is passed
     *
     * @param day
     * @return
     */
    private Date getDate(String day) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, Integer.parseInt(day));
        Date date = calendar.getTime();

        //day is passed
        if (date.before(today)) {
            calendar.add(Calendar.MONTH, 1);
            date = calendar.getTime();
        }
        return date;
    }

    /**
     * calculate sip end date
     *
     * @param startDateStr
     * @param sipFrequency
     * @return
     */
    private String getSipEndDate(String startDateStr, String sipFrequency, String sipNoOfInst) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = sdf.parse(startDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            if (sipFrequency.equalsIgnoreCase("MONTHLY")) {
                calendar.add(Calendar.MONTH, Integer.parseInt(sipNoOfInst));
            } else if (sipFrequency.equalsIgnoreCase("QUARTERLY")) {
                calendar.add(Calendar.MONTH, (Integer.parseInt(sipNoOfInst) * 4));
            } else if (sipFrequency.equalsIgnoreCase("WEEKLY")) {
                calendar.add(Calendar.WEEK_OF_MONTH, (Integer.parseInt(sipNoOfInst)));
            }

            return sdf.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return startDateStr;

    }

    /**
     * convert day to date of current month
     *
     * @param list
     * @return
     */
    private ArrayList buildDateList(List<String> list) {

        ArrayList<String> dateList = new ArrayList<>();

        for (String day : list) {

            if (!day.equalsIgnoreCase("")) {
                Date date = getDate(day);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateList.add(sdf.format(date));

            }
        }
        return dateList;
    }

    private void getFromIntent() {
        if (getArguments().getBoolean("isSIPOrder")) {
            mfSchemeData = (AvailableSchemeListModel) getArguments().getSerializable("Request");
            mfSchemeData.getSchemeName();
        }
    }

    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i("Log", "***** IP=" + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Log", ex.toString());
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    private boolean validateSipData(String paymentMode, String sipFrequency, String sipAmount, String sipNoOfInst, String mandateID) {
        if (!termChkBox.isChecked()) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Please confirm the terms and condition", "Ok", true, null);
            return false;
        }
        if (isBlank(paymentMode) || paymentMode.equalsIgnoreCase("PAYMENT MODE")) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Select payment mode", "Ok", true, null);
            return false;
        }


        if (isBlank(sipFrequency) || sipFrequency.equalsIgnoreCase("Select SIP frequency")) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Select SIP frequency", "Ok", true, null);
            return false;
        }

        String sipStartDate = sipDateSpinner.getSelectedItem().toString();
        if (isBlank(sipStartDate) || sipStartDate.equalsIgnoreCase("Select SIP date")) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Select SIP date", "Ok", true, null);
            return false;
        }
        if (isBlank(sipAmount)) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter installment amount", "Ok", true, null);
            return false;
        }
        Double sipAmountDouble = Double.parseDouble(sipAmount);
        if (sipAmountDouble < sipAmountMin || sipAmountDouble > sipAmountMax) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter installments amount between " + sipAmountMin + " and " + sipAmountMax, "Ok", true, null);
            return false;
        }

        if (isBlank(sipNoOfInst)) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter no of installments", "Ok", true, null);
            return false;
        }
        Double noOfInstDouble = Double.parseDouble(sipNoOfInst);
        if (noOfInstDouble < sipNoOfInstMin || noOfInstDouble > sipNoOfInstMax) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter no of installments between " + sipNoOfInstMin + " and " + sipNoOfInstMax, "Ok", true, null);
            return false;
        }
        return true;
    }

    //submit sip data to server
    private void placeOrder() {

        String sipFrequency = "", mandateID = "Not Available";

        if (mandateIdSpinner != null) {
            mandateID = mandateIdSpinner.getSelectedItem().toString();
        }

        if (frequencyTypeSpinner.getSelectedItem() != null) {
            sipFrequency = frequencyTypeSpinner.getSelectedItem().toString();
        }


        String sessionId = AccountDetails.getToken(getMainActivity());
        String clientCode = mfSchemeData.getMfClientCode();

        if (transtnModeSpinner.getSelectedItemPosition() == 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "please select transaction mode", "OK", false, null);

            return;
        }

        String paymentMode = paymentModeSpinner.getSelectedItem().toString();
        String sipAmount = sipAmtText.getText().toString();
        String sipNoOfInst = sipNoOfInstText.getText().toString();
        String folio = folionumbertext.getText().toString();
        String localIp = getLocalIpAddress();

        if (!validateSipData(paymentMode, sipFrequency, sipAmount, sipNoOfInst, mandateID)) {
            return;
        }

        String dpTransaction = "";
        if (dpTransModeSpinner.getSelectedItem().toString().equalsIgnoreCase("cdsl")) {
            dpTransaction = "C";
        } else if (dpTransModeSpinner.getSelectedItem().toString().equalsIgnoreCase("nsdl")) {
            dpTransaction = "N";
        } else if (dpTransModeSpinner.getSelectedItem().toString().equalsIgnoreCase("physical")) {
            dpTransaction = "P";
        }

        String transmode = "";
        if (transtnModeSpinner.getSelectedItem().toString().equalsIgnoreCase("demat")) {
            transmode = "D";
        } else if (transtnModeSpinner.getSelectedItem().toString().equalsIgnoreCase("physical")) {
            transmode = "P";
        }
        String sipStartDate = sipDateSpinner.getSelectedItem().toString();
        String sipEndDate = getSipEndDate(sipStartDate, sipFrequency, sipNoOfInst);

        SipSummaryModel summary = new SipSummaryModel();
        summary.setFolioNumber(folio);
        summary.setSchemeName(mfSchemeData.getSchemeName());
        summary.setSchemeIsin(mfSchemeData.getISIN());
        summary.setClientCode(clientCode);
        summary.setSessionId(sessionId);
        summary.setSipFrequency(sipFrequency);
        summary.setPaymentMode(paymentMode);
        summary.setMandateId(mandateID);
        summary.setSipAmount(sipAmount);
        summary.setSipNoOfInst(sipNoOfInst);
        summary.setStartDate(sipStartDate);
        summary.setEndDate(sipEndDate);
        summary.setLocalIp(localIp);
        summary.setDpTransaction(dpTransaction);
        summary.setTransmode(transmode);
        summary.setSchemeCode(mfSchemeData.getSchemeCode());
        summary.setBseCode(mfSchemeData.getBseCode());
        summary.setBseRTACode(mfSchemeData.getBseRTACode());


        Bundle bundle = new Bundle();
        bundle.putSerializable("summary", summary);

        if (mandateID.equalsIgnoreCase("Not Available") || mandateID.equalsIgnoreCase("Select Mandate ID")) {
            bundle.putString("from", "sip");
        } else {
            bundle.putString("from", "xsip");
        }
        navigateTo(NAV_TO_MUTUAL_FUND_SIP_SUMMARY, bundle, true);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUAL_FUND_SIP;
    }
}

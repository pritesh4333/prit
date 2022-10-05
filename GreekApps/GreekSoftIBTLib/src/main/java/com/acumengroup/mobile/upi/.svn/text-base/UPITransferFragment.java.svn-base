package com.acumengroup.mobile.upi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.FundTransfer.BankDetailsRequest;
import com.acumengroup.greekmain.core.model.FundTransfer.BankDetailsResponse;
import com.acumengroup.greekmain.core.model.FundTransfer.FundTransferCancelRequest;
import com.acumengroup.greekmain.core.model.FundTransfer.FundTransferCancelResponse;
import com.acumengroup.greekmain.core.model.FundTransfer.FundTransferDetailsRequest;
import com.acumengroup.greekmain.core.model.FundTransfer.FundTransferDetailsResponse;
import com.acumengroup.greekmain.core.model.FundTransfer.FundTransferResponseDetailsRequest;
import com.acumengroup.greekmain.core.model.FundTransfer.FundTransferResponseDetailsResponse;
import com.acumengroup.greekmain.core.model.FundTransfer.UPITransferDetailsRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.WebContent;
import com.acumengroup.mobile.model.UPIResponse.UPIResponseModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.loopj.android.http.Base64;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 03-Jul-17.
 */

public class UPITransferFragment extends GreekBaseFragment {

    private List<String> productidList = new ArrayList<>();
    private LinearLayout fundtransfer;
    private BankDetailsResponse bankDetailsResponse;
    private String strBankId, merchantId, passKey, reqHash, segmentstr = "";
    private Button payinbtn;
    private FundTransferDetailsResponse fundTransferDetailsResponse;
    private LinkedHashMap<String, String> mapofresponse = new LinkedHashMap<>();
    private Spinner segmentspinner, bankNameSpinner, productidSpinner, bankacctspinner;
    private GreekEditText amttxt, bankaccounttxt, upiIdtxt;
    private ArrayAdapter<String> bankNameSpinAdapter;
    private ArrayAdapter<String> productIdSpinAdapter;
    private ArrayAdapter<String> acctNoSpinAdapter;
    private List<String> bankNameList = new ArrayList<>();
    private List<String> accNoList = new ArrayList<>();
    private final List<String> bankID = new ArrayList<>();
    private GreekTextView disclaimertxt, cleartxt, amtlbl, legderlbl, bankacctlbl, banknamelbl, segmentlbl, productidlbl, errortxt,upiId_txt;
    private GreekEditText ledgertxt;
    private String amount, order_state, order_state_msg;
    private ArrayList<HashMap<String, String>> bankDeatilsList;

    public static UPITransferFragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        UPITransferFragment fragment = new UPITransferFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fundtransferView = super.onCreateView(inflater, container, savedInstanceState);

        attachLayout(R.layout.upi_transfer);
       // AccountDetails.currentFragment = NAV_TO_TRANSFER_PAYING;
        setUpView(fundtransferView);
        setTheme();
        setupAdapter();
        return fundtransferView;

    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            fundtransfer.setBackgroundColor(getResources().getColor(R.color.white));
            bankacctlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            banknamelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            productidlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            errortxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            amtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            upiIdtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            upiId_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            legderlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            segmentlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            amttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bankaccounttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ledgertxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //    payinbtn.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //avail_fund.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //    withdraw_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //   txtfund.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            //           payinbtn.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
    }

    private void setUpView(View fundtransferView) {
        bankDeatilsList = new ArrayList<HashMap<String, String>>();
        setAppTitle(getClass().toString(), "Fund Transfer");
        segmentspinner = fundtransferView.findViewById(R.id.segmentspinner);
        bankNameSpinner = fundtransferView.findViewById(R.id.banknamespinner);
        bankacctspinner = fundtransferView.findViewById(R.id.backacctspinner);
        productidSpinner = fundtransferView.findViewById(R.id.productidspinner);
        amttxt = fundtransferView.findViewById(R.id.amttxt);
        //txtfund =fundtransferView.findViewById(R.id.txt_fund);
        fundtransfer = fundtransferView.findViewById(R.id.fundtransfer_bg);
        bankaccounttxt = fundtransferView.findViewById(R.id.bankaccounttxt);
        payinbtn = fundtransferView.findViewById(R.id.payinbtn);
        disclaimertxt = fundtransferView.findViewById(R.id.disclaimertxt);
        banknamelbl = fundtransferView.findViewById(R.id.bankname_txt);
        productidlbl = fundtransferView.findViewById(R.id.productid_txt);
        errortxt = fundtransferView.findViewById(R.id.errortxt);
        bankacctlbl = fundtransferView.findViewById(R.id.bankacct_txt);
        amtlbl = fundtransferView.findViewById(R.id.amt_txt);
        legderlbl = fundtransferView.findViewById(R.id.ledgeramt_txt);
        segmentlbl = fundtransferView.findViewById(R.id.segment_txt);
        cleartxt = fundtransferView.findViewById(R.id.cleartxt);
        ledgertxt = fundtransferView.findViewById(R.id.ledgeramttxt);

        upiId_txt = fundtransferView.findViewById(R.id.upiId_txt);
        upiIdtxt = fundtransferView.findViewById(R.id.upiIdtxt);

        InputFilter[] filters = {new InputFilter.LengthFilter(7)};
        amttxt.setFilters(filters);
        ledgertxt.setFilters(filters);

        //orderStreamingController.sendFundTransferDetailsRequest(getMainActivity(), "2", AccountDetails.getUsername(getMainActivity()), "50", "1", AccountDetails.getClientCode(getMainActivity()), "2");
        sendBankDetailsRequest(0);

        payinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amttxt.getText().toString();
                String segStr = "";
                if (segmentstr.equalsIgnoreCase("commodity")) {
                    segStr = "1";
                } else if (segmentstr.equalsIgnoreCase("non-commodity")) {
                    segStr = "2";
                }


                if (AccountDetails.getFt_testing_bypass().equalsIgnoreCase("true")) {
                    // If iris connected then only send request to iris server
                    if (AccountDetails.isIsIrisConnected()) {
                        orderStreamingController.sendFundTransferDetailsRequest(getMainActivity(), "0" /*fundTransferDetailsResponse.getUniqueId()*/, AccountDetails.getUsername(getMainActivity()), amount, "0", AccountDetails.getClientCode(getMainActivity()), segStr);
                    } else {
                        EventBus.getDefault().post("Socket IRIS Reconnect Attempts exceeds");
                    }
                } else {
                    if (AccountDetails.getFt_Link().equalsIgnoreCase("")){
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Please Contact Admin", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }
                    else if (amount.equalsIgnoreCase("")) {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Please enter the amount.", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    } else if (Float.valueOf(amount) <= 50) {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Amount should be greater than 50", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }else if (Float.valueOf(amount) > 100000) {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Amount should be less than or equal to 1 Lac", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }else if (upiIdtxt.getText().toString().isEmpty()) {

                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Please Enter valid UPI id", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });

                    } else if (bankacctspinner.getSelectedItem() == null) {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Details not found", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }else if (AccountDetails.getFt_Link_Upi().equalsIgnoreCase("")){
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Please Contact Admin", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    } else {
                        if (validateUPI(upiIdtxt.getText().toString())) {
                            UPITransferDetailsRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), AccountDetails.getClientCode(getMainActivity()), amttxt.getText().toString(), AccountDetails.getSessionId(getMainActivity()), segStr, bankNameSpinner.getSelectedItem().toString(),getMainActivity(), serviceResponseHandler);
                        }else{
                            GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Please Enter valid Upi id", "Ok", false, new GreekDialog.DialogListener() {
                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                }
                            });
                        }
                    }
                }


            }
        });

        disclaimertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eulaText = "";
                try {
                    eulaText = Util.read((getActivity().getAssets()).open("Disclaimer")).toString();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                final CharSequence formatEulaText = Html.fromHtml(eulaText);
                GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, formatEulaText.toString());
            }
        });


        cleartxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amttxt.setText("");
            }
        });
    }


    private void setupAdapter() {
        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), Arrays.asList(getResources().getStringArray(R.array.segmentType))) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }

                v.setPadding(15, 15, 15, 15);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(getResources().getColor(R.color.black));

                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        groupAdapter.setDropDownViewResource(R.layout.custom_spinner);
        segmentspinner.setAdapter(groupAdapter);
        segmentspinner.setOnItemSelectedListener(segmentItemSelectedListener);


        bankNameSpinAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), bankNameList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 15, 15, 15);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(getResources().getColor(R.color.black));
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        bankNameSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        bankNameSpinner.setAdapter(bankNameSpinAdapter);
        bankNameSpinner.setOnItemSelectedListener(bankNameItemSelectedListener);


        acctNoSpinAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), accNoList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }

                v.setPadding(15, 15, 15, 15);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(getResources().getColor(R.color.black));
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        acctNoSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        bankacctspinner.setAdapter(acctNoSpinAdapter);


    }

    private final AdapterView.OnItemSelectedListener segmentItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //sendContentRequest();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener productItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            for (int i = 0; i < bankDetailsResponse.getMerchantDetails().size(); i++) {
                if (productidSpinner.getSelectedItem().toString().equalsIgnoreCase(bankDetailsResponse.getMerchantDetails().get(i).getProdId())) {
                    merchantId = bankDetailsResponse.getMerchantDetails().get(i).getMerchant_id();
                    passKey = bankDetailsResponse.getMerchantDetails().get(i).getPassword();
                    reqHash = bankDetailsResponse.getMerchantDetails().get(i).getReqHashKey();
                    segmentstr = bankDetailsResponse.getMerchantDetails().get(i).getSegment();

                }
            }
// To get bank Details we are sending request
            if(!AccountDetails.getFt_Link_Upi().equalsIgnoreCase("")) {
                BankDetailsRequest(merchantId, bankDetailsResponse);
            }else{

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener bankNameItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String bankName = bankNameList.get(position).toString();

            accNoList.clear();
            for (int i = 0; i < bankDetailsResponse.getBankAccountList().size(); i++) {
                if (bankNameSpinner.getSelectedItem().toString().equalsIgnoreCase(bankDetailsResponse.getBankAccountList().get(i).getBankName())) {

                    accNoList.add(bankDetailsResponse.getBankAccountList().get(i).getBankAcNo());
                }
            }

            acctNoSpinAdapter.notifyDataSetChanged();

            for (int i = 0; i < bankDeatilsList.size(); i++) {
                if (bankDeatilsList.get(i).containsKey(bankName.toLowerCase())) {

                    strBankId = bankDeatilsList.get(i).get(bankName.toLowerCase());
                    break;
                }
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        if ("fundTransferDetailsUpi".equals(jsonResponse.getServiceName())) {

            try {

                fundTransferDetailsResponse = (FundTransferDetailsResponse) jsonResponse.getResponse();
                order_state = "1";
                String segStr = "";
                if (segmentstr.equalsIgnoreCase("commodity")) {
                    segStr = "1";
                } else if (segmentstr.equalsIgnoreCase("non-commodity")) {
                    segStr = "2";
                }
                orderStreamingController.sendFundTransferDetailsRequest(getMainActivity(),
                        fundTransferDetailsResponse.getUniqueId(),
                        AccountDetails.getUsername(getMainActivity()),
                        amount, order_state, AccountDetails.getClientCode(getMainActivity()), segStr);
                newReq();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if ("getBankAccountDetailMobile".equals(jsonResponse.getServiceName())) {

            try {

                bankNameList.clear();
                bankDetailsResponse = (BankDetailsResponse) jsonResponse.getResponse();

                for (int i = 0; i < bankDetailsResponse.getMerchantDetails().size(); i++) {
//                    if ((AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse || AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo || AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) && ((bankDetailsResponse.getMerchantDetails().get(i).getProdId().equalsIgnoreCase("non-commodity")) || (bankDetailsResponse.getMerchantDetails().get(i).getProdId().equalsIgnoreCase("nse")) || (bankDetailsResponse.getMerchantDetails().get(i).getProdId().equalsIgnoreCase("bse")))) {
                    if (!productidList.contains(bankDetailsResponse.getMerchantDetails().get(i).getProdId())) {
                        productidList.add(bankDetailsResponse.getMerchantDetails().get(i).getProdId());
                    }
                   /* } else if ((AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) && ((bankDetailsResponse.getMerchantDetails().get(i).getProdId().equalsIgnoreCase("commodity")) || (bankDetailsResponse.getMerchantDetails().get(i).getProdId().equalsIgnoreCase("mcx")) || (bankDetailsResponse.getMerchantDetails().get(i).getProdId().equalsIgnoreCase("ncdex")))) {
                        if (!productidList.contains(bankDetailsResponse.getMerchantDetails().get(i).getProdId())) {
                            productidList.add(bankDetailsResponse.getMerchantDetails().get(i).getProdId());
                        }

                    }*/

                }

                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                productIdSpinAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), productidList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                            v.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                productIdSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
                productidSpinner.setAdapter(productIdSpinAdapter);
                productIdSpinAdapter.notifyDataSetChanged();
                productidSpinner.setOnItemSelectedListener(productItemSelectedListener);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if ("fundTransferResponseDetails".equals(jsonResponse.getServiceName())) {
            FundTransferResponseDetailsResponse fundTransferResponseDetailsResponse;
            String segStr = "";
            if (segmentstr.equalsIgnoreCase("commodity")) {
                segStr = "1";
            } else if (segmentstr.equalsIgnoreCase("non-commodity")) {
                segStr = "2";
            }
            try {
                fundTransferResponseDetailsResponse = (FundTransferResponseDetailsResponse) jsonResponse.getResponse();
                if (fundTransferResponseDetailsResponse.getErrorCode().equalsIgnoreCase("0")) {
                    //orderStreamingController.sendFundTransferDetailsRequest(getMainActivity(), fundTransferDetailsResponse.getUniqueId(), AccountDetails.getUsername(getMainActivity()), amount, order_state, AccountDetails.getClientCode(getMainActivity()), segStr);
                    //sendSecondPaymentRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //goToStartupPage();
            }
        }
        else if ("FTCancelResponse".equals(jsonResponse.getServiceName())) {
            FundTransferCancelResponse fundTransferCancelResponse;
            String segStr = "";
            if (segmentstr.equalsIgnoreCase("commodity")) {
                segStr = "1";
            } else if (segmentstr.equalsIgnoreCase("non-commodity")) {
                segStr = "2";
            }
            try {
                fundTransferCancelResponse = (FundTransferCancelResponse) jsonResponse.getResponse();
                if (fundTransferCancelResponse.getErrorCode().equalsIgnoreCase("0")) {
//                    orderStreamingController.sendFundTransferDetailsRequest(getMainActivity(), fundTransferDetailsResponse.getUniqueId(), AccountDetails.getUsername(getMainActivity()), amount, order_state, AccountDetails.getClientCode(getMainActivity()), segStr);
                    //sendSecondPaymentRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //goToStartupPage();
            }


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        AccountDetails.setIsMainActivity(false);
        if (data != null ) {
            String message = data.getStringExtra("Result").replace("\"", "");
            //HTML Parsing Done=================>>>>
            /*if(message !=null && message.length()>0) {
                org.jsoup.nodes.Document doc1 = Jsoup.parse(message);
                Elements type1 = doc1.select("pre");
                if (type1.size() > 0) {
                    String text = type1.first().html();
                   // text = text.replaceAll(",", "\n");

                    Log.e("Atom Response text", text);

                Gson gson = new Gson();
                UPIResponseModel upiResponseModel = gson.fromJson(text, UPIResponseModel.class);
                if (upiResponseModel.getFCode() != null) {
                    if (upiResponseModel.getFCode().equalsIgnoreCase("ok")) {
                        order_state = "0";
                        order_state_msg = "successfully processed";
                    } else {
                        order_state = "1";
                        order_state_msg = "failed";
                    }
                }



                String payINMessage = "Your request for addition of Rs." + upiResponseModel.getAmt() + " has been " + order_state_msg;*/



                    /*String[] lines = text.split("\\r?\\n");
                    for (int i = 0; i < lines.length; i++) {
                        if (lines[i] != "") {
                            String[] datafields = lines[i].split("::");
                            if (datafields.length == 2) {
                                //mapofresponse.put("he"+i, "she"+i);

                                String data1 = datafields[0].trim();
                                String data2 = datafields[1].trim();

                                mapofresponse.put(data1, data2);

                            }
                        }
                    }

                    String messagetodisplay = "";
                    String mmptxn = "", banktxn = "", bankname = "";
                    if (mapofresponse.get("mmp_txn") != null) {
                        messagetodisplay += "MMP TXN: " + mapofresponse.get("mmp_txn") + "\n";
                        mmptxn = mapofresponse.get("mmp_txn");
                    } else {
                        mmptxn = "0";
                    }
                    if (mapofresponse.get("bank_txn") != null) {
                        messagetodisplay += "Bank TXN: " + mapofresponse.get("bank_txn") + "\n";
                        banktxn = mapofresponse.get("bank_txn");
                    } else {
                        banktxn = "0";
                    }
                    if (mapofresponse.get("amt") != null) {
                        messagetodisplay += "Amount: " + mapofresponse.get("amt") + "\n";
                    }
                    if (mapofresponse.get("date") != null) {
                        messagetodisplay += "Date: " + mapofresponse.get("date") + "\n";
                    }
                    if (mapofresponse.get("f_code") != null) {
                        messagetodisplay += "Status: " + mapofresponse.get("f_code") + "\n";
                    }
            *//*if (mapofresponse.get("bank_name") != null) {
                messagetodisplay += "Bank Name: " + mapofresponse.get("bank_name") + "\n";
                bankname = mapofresponse.get("bank_name");
            } else {
                bankname = "0";
            }*//*
                    if (mapofresponse.get("surcharge") != null) {
                        messagetodisplay += "Surcharge: " + mapofresponse.get("surcharge") + "\n";
                    }
            *//*if (mapofresponse.get("udf12") != null) {
                messagetodisplay += "Bank Name: " + mapofresponse.get("udf12") + "\n";
                bankname = mapofresponse.get("udf12");
            } else {
                bankname = "0";
            }*//*


                    if (mapofresponse.get("f_code") != null) {
                        if (mapofresponse.get("f_code").equalsIgnoreCase("ok")) {
                            order_state = "0";
                            order_state_msg = "successfully processed";
                        } else {
                            order_state = "1";
                            order_state_msg = "failed";
                        }
                    }

                    bankname = bankNameSpinner.getSelectedItem().toString();
//            order_state="0";

                    String payINMessage = "Your request for addition of Rs." + mapofresponse.get("amt") + " has been " + order_state_msg;

                    String segStr = "";
                    if (segmentstr.equalsIgnoreCase("commodity")) {
                        segStr = "1";
                    } else if (segmentstr.equalsIgnoreCase("non-commodity")) {
                        segStr = "2";
                    }*/

//              Transaction initiated updated on Aracane
               /* FundTransferResponseDetailsRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()),
                        AccountDetails.getSessionId(getMainActivity()), mmptxn, banktxn, "0", bankname,
                        fundTransferDetailsResponse.getUniqueId(), getMainActivity(), serviceResponseHandler);*/

//              Order Transaction initiated updated on Iris server.
                /*orderStreamingController.sendFundTransferDetailsRequest(getMainActivity(),
                        fundTransferDetailsResponse.getUniqueId(),
                        AccountDetails.getUsername(getMainActivity()), amount, order_state,
                        AccountDetails.getClientCode(getMainActivity()), segStr);*/


                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, message, "Ok", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        }
                    });
              /*  }
            }*/

        } else {
            order_state = "2";
            String segStr = "";
            if (segmentstr.equalsIgnoreCase("commodity")) {
                segStr = "1";
            } else if (segmentstr.equalsIgnoreCase("non-commodity")) {
                segStr = "2";
            }
//            Transaction initiated updated on Aracane
           /* FundTransferCancelRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()),
                    AccountDetails.getSessionId(getMainActivity()), fundTransferDetailsResponse.getUniqueId(),
                    "", getMainActivity(), serviceResponseHandler);*/
//           order Transaction initiated updated onIris
            /*orderStreamingController.sendFundTransferDetailsRequest(getMainActivity(),
                    fundTransferDetailsResponse.getUniqueId(),
                    AccountDetails.getUsername(getMainActivity()), amount, order_state,
                    AccountDetails.getClientCode(getMainActivity()), segStr);*/


        }
    }

    private void BankDetailsRequest(final String merchant_id, final BankDetailsResponse bankDetailResponse) {

        String url = AccountDetails.getFt_Link_Upi()+"/getbanklist?merchantId=" + merchant_id;
        showProgress();


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgress();
                        bankNameList.clear();
                        bankID.clear();
                        List<String> banklist = new ArrayList<>();
                        //List<BankDetailsResponse> bankresp = new ArrayList<>();

                        BankDetailsResponse bankresp = bankDetailResponse;

                        try {

                            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                            DocumentBuilder db = null;
                            try {
                                db = dbf.newDocumentBuilder();
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            }
                            Document doc = db.parse(new InputSource(new StringReader(response)));
                            doc.getDocumentElement().normalize();

                            NodeList nl = doc.getElementsByTagName("param");
                            // looping through all item nodes <item>
                            for (int i = 0; i < nl.getLength(); i++) {

                                Node node = nl.item(i);
                                if (node.getNodeType() == Node.ELEMENT_NODE) {
                                    Element element2 = (Element) node;
                                    String bankName = getValue("bankid", element2);
                                    String bankid = element2.getAttribute("bankid");

                                    banklist.add(bankName.toLowerCase());
                                    bankNameList.add(bankName);
                                    bankID.add(bankid);

                                    Log.e("BankDetailsRequest", bankName + "bankid=======>" + bankid);

                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put(bankName.toLowerCase(), bankid);
                                    bankDeatilsList.add(map);

                                }


                            }


                            bankNameList.clear();
                            for (int i = 0; i < bankresp.getBankAccountList().size(); i++) {
                                if (banklist.contains(bankresp.getBankAccountList().get(i).getBankName().toLowerCase())) {
                                    if (!bankNameList.contains(bankresp.getBankAccountList().get(i).getBankName())) {
                                        bankNameList.add(bankresp.getBankAccountList().get(i).getBankName());
                                    }
                                }
                            }

                            bankNameSpinAdapter.notifyDataSetChanged();


                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("merchantId", merchant_id);
                return params;
            }
        };
        Volley.newRequestQueue(getMainActivity()).add(postRequest);

    }

    private void newReq() {
        AccountDetails.setIsMainActivity(true);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setTimeZone(TimeZone.getDefault());

        final String date = df.format(new Date());
//        String login = "197";
//        String login = fundTransferDetailsResponse.getClientCode();
        String login = merchantId;
//        String pass = "Test@123";
//        String pass = fundTransferDetailsResponse.getPassword();
        String pass = passKey;
        String ttype = "NBFundTransfer";
        String prodid = productidSpinner.getSelectedItem().toString();
        String txnid = fundTransferDetailsResponse.getUniqueId();
        String amt = amttxt.getText().toString();
        String txncurr = "INR";

//        String reqKey = "KEY123657234";
        String reqKey = reqHash;
        final String signature = getEncodedValueWithSha2(reqKey, login, pass, ttype, prodid, txnid, amt, txncurr);

//        String xmlURL = "https://payment.atomtech.in/paynetz/epi/fts";
//        String Atom2Request = xmlURL + "?ttype=" + paymodel.getXmlttype() + "&tempTxnId=" + paymodel.getXmltempTxnId() + "&token=" + paymodel.getXmltoken() + "&txnStage=" + paymodel.getXmltxnStage();
        String Atom2Request = ""; /*= xmlURL + "?login=" + login + "&pass=" + pass + "&ttype="
                + ttype + "&prodid=" + prodid + "&txnid=" + txnid + "&amt=" + amt + "&txncurr="
                + txncurr + "&txnscamt=0" + "&clientcode=" +
                encodeToBase64(AccountDetails.getUsername(getMainActivity())) + "&date="
                + date + "&custacc=" + bankacctspinner.getSelectedItem().toString() +
                "&bankid=" + strBankId + "&signature=" + signature + "&ru=" + AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/getFundTransferResponse"+"&mdd=UP|SMSUPI|8956711498@ybl";
        Atom2Request = Atom2Request.replace(" ", "%20");*/


        Atom2Request = AccountDetails.getFt_Link_Upi()+"/epi/fts?login=" +login+ "&pass=" +pass+ "&ttype=NBFundTransfer&prodid="+prodid+"&txnid=" +txnid+ "&amt="+amt+"&txncurr=INR&signature="+signature+"&clientcode="+encodeToBase64(AccountDetails.getUsername(getMainActivity()))+"&date="+date+"&custacc="+ bankacctspinner.getSelectedItem().toString()+"&mdd=UP|SMSUPI|" +upiIdtxt.getText().toString()+ "&ru="+ AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port()+"/getUpiTransferResponse_ios"+"&udf11="+strBankId+"&udf12="+bankNameSpinner.getSelectedItem().toString()+"&udf1="+AccountDetails.getUsername(getMainActivity())+"&udf13="+AccountDetails.getClientCode(getMainActivity());
        Atom2Request = Atom2Request.replace(" ", "%20");

        Log.d("ATOM 2nd Request URl", Atom2Request);

        Intent intent = new Intent(getActivity(), WebContent.class);
        intent.putExtra("AtomRequest", Atom2Request);
        intent.putExtra("uniqueid", fundTransferDetailsResponse.getUniqueId());
        intent.putExtra("amt", amount);
        intent.putExtra("transactionMethod","UPI");
        //intent.putExtra("segstr", segStr);
        startActivityForResult(intent, 3);

    }


    //    login, String pass, String ttype, String prodid, String txnid, String amt, String txncurr
    public static String getEncodedValueWithSha2(String hashKey, String... param) {
        String resp = null;
        StringBuilder sb = new StringBuilder();
        for (String s : param) {
            sb.append(s);
        }
        try {
            System.out.println("[getEncodedValueWithSha2]String to Encode =" + sb.toString());
            resp = byteToHexString(encodeWithHMACSHA2(sb.toString(), hashKey));
            //resp = URLEncoder.encode(resp,"UTF-8");
        } catch (Exception e) {
            System.out.println("[getEncodedValueWithSha2]Unable to encocd value with key :" + hashKey + " and input :" + sb.toString());
            e.printStackTrace();
        }
        return resp;
    }

    public static byte[] encodeWithHMACSHA2(String text, String keyString) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        java.security.Key sk = new javax.crypto.spec.SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8), "HMACSHA512");
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance(sk.getAlgorithm());
        mac.init(sk);
        byte[] hmac = mac.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return hmac;
    }

    public static String byteToHexString(byte[] byData) {
        StringBuilder sb = new StringBuilder(byData.length * 2);
        for (int i = 0; i < byData.length; i++) {
            int v = byData[i] & 0xff;
            if (v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }


    private static String getValue(String tag, Element element) {
        //NodeList nodeList = element.getChildNodes().item(0).getNodeValue();
        // Node node = nodeList.item(0);
        return element.getChildNodes().item(0).getNodeValue();
    }

    public String encodeToBase64(String stringToEncode) {
        byte[] data = new byte[0];
        data = stringToEncode.getBytes(StandardCharsets.UTF_8);
        String encyrpt = Base64.encodeToString(data, Base64.NO_WRAP);
        return encyrpt;
    }


    @Override
    public void onFragmentResume() {
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 3);
        setAppTitle(getClass().toString(), "Demat Holdings");
        super.onFragmentResume();
    }


    @Override
    public void onResume() {
        super.onResume();

        //  refresh();
    }

    @Override
    public void onFragmentPause() {

        super.onFragmentPause();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    public void sendBankDetailsRequest(int position) {

        BankDetailsRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getSessionId(getMainActivity()), getMainActivity(), serviceResponseHandler);

    }

    public static boolean validateUPI(String upi) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^(.+)@(.+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(upi);
        return matcher.find();
    }


}


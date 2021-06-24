package com.acumengroup.mobile.reports;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.acumengroup.mobile.GreekBaseActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.BankName;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static com.acumengroup.greekmain.util.date.DateTimeFormatter.getDateFromTimeStamp;


public class BankDetailsScreenFragment extends GreekBaseFragment implements Step {

    private View bankDetailsView;
    LinearLayout bankDetails;
    private BankName bankNameData;
    private Boolean changeColor = true;
    NodeList aList;

    public static BankDetailsScreenFragment newInstance(Bundle bundle, String param2) {
        BankDetailsScreenFragment fragment = new BankDetailsScreenFragment();
        Bundle args = bundle;
        fragment.setArguments(args);
        return fragment;
    }

    public BankDetailsScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bankDetailsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_bank_details_screen).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_bank_details_screen).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_BANK_DETAILS_SCREEN;

        bankDetails = bankDetailsView.findViewById(R.id.order_details);
        setAppTitle(getClass().toString(), "Bank Details");

        parseReceivedArguments();

        return bankDetailsView;
    }

    private void parseReceivedArguments() {

        bankNameData = (BankName) getArguments().getSerializable("response");

        String status = "Status";
        createPositionsRow("Transaction Id", ":\t" + bankNameData.getTranId());
        createPositionsRow("Bank Transaction Id", ":\t" + bankNameData.getBankTranId());
        createPositionsRow("Bank Name", ":\t" + bankNameData.getBankName());
        createPositionsRow("Date & Time", ":\t" + getDateFromTimeStamp(bankNameData.getDateAndTime(), "dd MMM yyyy HH:MM:SS", "BSE"));
        createPositionsRow("Amount", ":\t" + bankNameData.getAmount());
        createPositionsRow("Status", ":\t" + bankNameData.getTransStatus());
        createPositionsRow("","");

        createPositionsRow("Stages", " ");
        createPositionsRow("","");
        createPositionsRow("Time", "\t" + status);
        createPositionsRow("","");

        if (bankNameData.getStatusFlag().equalsIgnoreCase("0")) { //message = "Initiated";


            if (!bankNameData.getLevel1_reqTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel1_reqTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Initiated");
                createPositionsRow("","");

            }

        } else if (bankNameData.getStatusFlag().equalsIgnoreCase("1")) {  //message = "Ack Received";

            if (!bankNameData.getLevel1_reqTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel1_reqTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Initiated");
                createPositionsRow("","");
            }
            if (!bankNameData.getLevel1_resTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel1_reqTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Ack Received");
                createPositionsRow("","");
            }

        } else if (bankNameData.getStatusFlag().equalsIgnoreCase("2")) {   //message = "Request Sent";


            if (!bankNameData.getLevel1_reqTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel1_reqTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Initiated");
                createPositionsRow("","");
            }
            if (!bankNameData.getLevel1_resTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel1_resTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Ack Received");
                createPositionsRow("","");
            }
            if (!bankNameData.getLevel2_reqTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel2_reqTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Request Sent");
                createPositionsRow("","");
            }

        } else if (bankNameData.getStatusFlag().equalsIgnoreCase("3")) {   //message = "Completed";


            if (!bankNameData.getLevel1_reqTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel1_reqTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Initiated");
                createPositionsRow("","");
            }
            if (!bankNameData.getLevel1_resTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel1_resTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Ack Received");
                createPositionsRow("","");
            }
            if (!bankNameData.getLevel2_reqTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel2_reqTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Request Sent");
                createPositionsRow("","");
            }
            if (!bankNameData.getLevel2_resTime().equalsIgnoreCase("0")) {
                createPositionsRow(getDateFromTimeStamp(bankNameData.getLevel2_resTime(), "dd MMM yyyy HH:mm:SS", "BSE"), "\t" + "Fund Transfer Request Executed");
                createPositionsRow("","");
            }
        }

        if (!bankNameData.getStatusFlag().equalsIgnoreCase("3")) {
            createPositionsRowforTracking("Tracking status");
            createPositionsRow("","");
        }


    }

    private void createPositionsRowforTracking(String key) {
        int color;
        //int color = (changeColor) ? R.color.market_grey_light : R.color.market_grey_dark;

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            color = (changeColor) ? R.color.floatingBgColor : R.color.backgroundStripColorWhite;
        } else {
            color = (changeColor) ? R.color.market_grey_dark : R.color.market_grey_light;
        }
        //bankDetails.setBackgroundColor(color);

        TableRow Row = new TableRow(getMainActivity());
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(key);
        keyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setClickable(true);
        keyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = (TableRow.LayoutParams) keyView.getLayoutParams();
        params.weight = 1;
        params.bottomMargin = 1;
        keyView.setPadding(10, 10, 10, 10);


        Row.addView(keyView);
        bankDetails.addView(Row);

        changeColor = !changeColor;

        keyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTrackingRequest();
            }
        });
    }


    private void sendTrackingRequest() {


        DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        final String date = df.format(Calendar.getInstance().getTime());

        String url = "https://payment.atomtech.in/paynetz/vfts";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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


                            aList = doc.getElementsByTagName("VerifyOutput");
                            String vParamName;
                            String responsevalue = "";

                            for (int atrCnt = 0; atrCnt < doc.getElementsByTagName("VerifyOutput").item(0).getAttributes().getLength(); atrCnt++) {

                                vParamName = doc.getElementsByTagName("VerifyOutput").item(0).getAttributes().item(atrCnt).getNodeName();

                                if (vParamName.equalsIgnoreCase("verified")) {

                                    responsevalue = doc.getElementsByTagName("VerifyOutput").item(0).getAttributes().item(atrCnt).getNodeValue();
                                    break;
                                }
                            }

                            String message = "";
                            if (responsevalue.equalsIgnoreCase("success")) {
                                message = "Transaction Success";
                            } else if (responsevalue.equalsIgnoreCase("failure")) {
                                message = "Transaction Failed";
                            } else if (responsevalue.equalsIgnoreCase("nodata")) {
                                message = "No Response from Bank";
                            }

                            if (!message.equalsIgnoreCase("")) {
                                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Tracking Status:" + message, "Ok", false, new GreekDialog.DialogListener() {
                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    }
                                });
                            }


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
                params.put("merchantid", bankNameData.getcGreekClientid());
                params.put("merchanttxnid", bankNameData.getTempTranId());
                params.put("amt", bankNameData.getAmount());
                params.put("tdate", date);
                return params;
            }
        };
        Volley.newRequestQueue(getMainActivity()).add(postRequest);

    }

    private void createPositionsRow(String key, String value) {
        int color;
        //int color = (changeColor) ? R.color.market_grey_light : R.color.market_grey_dark;

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            color = (changeColor) ? R.color.floatingBgColor : R.color.backgroundStripColorWhite;
        } else {
            color = (changeColor) ? R.color.market_grey_dark : R.color.market_grey_light;
        }

        TableRow Row = new TableRow(getMainActivity());
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(key);

        if (key.equals("Time")||key.equals("Stages")) {
            keyView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        keyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = (TableRow.LayoutParams) keyView.getLayoutParams();
        params.weight = 1;
        params.bottomMargin = 1;
        keyView.setPadding(10, 10, 10, 10);

        GreekTextView valueView = new GreekTextView(getMainActivity());
        valueView.setPadding(10, 12, 5, 12);

        valueView.setText(value);


        if (":\tBuy".equalsIgnoreCase(value)) {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        }
        else if (":\tSell".equalsIgnoreCase(value)) {
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
        }
        valueView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

       // valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if ("\tFund Transfer Initiated".equalsIgnoreCase(value)||"\tFund Transfer Ack Received".equalsIgnoreCase(value)||"\tFund Transfer Request Sent".equalsIgnoreCase(value)||"\tFund Transfer Request Executed".equalsIgnoreCase(value)) {
            valueView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }else {
            valueView.setTypeface(Typeface.DEFAULT_BOLD);

        }
        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
        valueView.setBackgroundColor(getResources().getColor(color));

        valueView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params1 = (TableRow.LayoutParams) valueView.getLayoutParams();
        params1.weight = 1;
        params1.bottomMargin = 1;

        Row.addView(keyView);
        Row.addView(valueView);
        bankDetails.addView(Row);

        changeColor = !changeColor;
    }

    @Override
    public void onFragmentResume() {

        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_BANK_DETAILS_SCREEN;
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        AccountDetails.setToastCount(0);
    }


    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        parseReceivedArguments();
    }

    @Override
    public void onError(@NonNull VerificationError verificationError) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}

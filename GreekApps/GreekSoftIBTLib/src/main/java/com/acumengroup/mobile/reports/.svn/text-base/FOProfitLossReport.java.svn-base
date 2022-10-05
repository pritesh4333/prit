package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;


import com.acumengroup.mobile.model.FOPLResponse;
import com.acumengroup.mobile.model.PandLReportRequest;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.columnsliderlib.AdaptiveTableLayout;
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter;
import com.acumengroup.columnsliderlib.OnItemClickListener;
import com.acumengroup.columnsliderlib.ViewHolderImpl;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;


public class FOProfitLossReport extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private AdaptiveTableLayout adaptiveTableLayout;
    private DateFormat df;
    private Spinner spinner1, spinner2;
    private GreekTextView errorMsgLayout, txt_ttlPNL,header;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    List<String> spinner1List, spinner2List;
    ArrayList<FOPLResponse.Response.Data.FOPLData> datalist;
    LinearLayout profitandloss_layout,header_layout;
    private String serviceName = "";
    private String spinnerselect;


    public FOProfitLossReport() {

    }

    public static FOProfitLossReport newInstance(String param1, String param2) {
        FOProfitLossReport fragment = new FOProfitLossReport();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        spinner1List = new ArrayList<>();
        spinner1List.add("EQ");
        spinner1List.add("FNO");


        spinner2List = new ArrayList<>();
        spinner2List.add("All Holdings");
        spinner2List.add("Positive Holdings");
        spinner2List.add("Zero Holdings");
        spinner2List.add("Negative Holdings");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f_o_profit_loss_report, container, false);
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        adaptiveTableLayout = view.findViewById(R.id.tableLayout);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        errorMsgLayout = view.findViewById(R.id.empty_view);
        header = view.findViewById(R.id.header);
        profitandloss_layout = view.findViewById(R.id.profitandloss_layout);
        header_layout = view.findViewById(R.id.header_layout);
        txt_ttlPNL = view.findViewById(R.id.txt_ttlPNL);

        errorMsgLayout.setVisibility(View.VISIBLE);
        adaptiveTableLayout.setVisibility(View.GONE);
        sort_btn = view.findViewById(R.id.sort_btn);

        setupTheam(view);

        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup;
                Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    popup = new PopupMenu(wrapper, sort_btn);
                } else {
                    popup = new PopupMenu(getMainActivity(), sort_btn);
                }

                popup.getMenuInflater().inflate(R.menu.sort_report_menu, popup.getMenu());



                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.US);

                        if (item.getItemId() == R.id.one) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.MONTH, 0);
                            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                            Date monthFirstDay = calendar.getTime();
                            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Date monthLastDay = calendar.getTime();

                            startDate = df.format(monthFirstDay);
                            Date c = Calendar.getInstance().getTime();
                            endDate = df.format(c);
                            sendReportRequest();


                        } else if (item.getItemId() == R.id.two) {

                            Calendar aCalendar = Calendar.getInstance();
                            aCalendar.add(Calendar.MONTH, -1);
                            aCalendar.set(Calendar.DATE, 1);

                            Date firstDateOfPreviousMonth = aCalendar.getTime();
                            startDate = df.format(firstDateOfPreviousMonth);

                            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Date lastDateOfPreviousMonth = aCalendar.getTime();
                            endDate = df.format(lastDateOfPreviousMonth);
                            sendReportRequest();


                        } else if (item.getItemId() == R.id.three) {

                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, year - 2);
                            cal.set(Calendar.MONTH, 3);
                            cal.set(Calendar.DAY_OF_MONTH, 1);
                            Date start = cal.getTime();
                            startDate = df.format(start);

                            cal.set(Calendar.YEAR, year - 1);
                            cal.set(Calendar.MONTH, 2); // 11 = december
                            cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

                            Date end = cal.getTime();

                            endDate = df.format(end);
                            sendReportRequest();


                        } else if (item.getItemId() == R.id.five) {

                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, year);
                            cal.set(Calendar.MONTH, 3);
                            cal.set(Calendar.DAY_OF_MONTH, 1);
                            Date start = cal.getTime();
                            startDate = df.format(start);

                            cal.set(Calendar.YEAR, year + 1);
                            cal.set(Calendar.MONTH, 2); // 11 = december
                            cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

                            Date end = cal.getTime();

                            endDate = df.format(end);
                            sendReportRequest();


                        } else if (item.getItemId() == R.id.four) {
                            showCustomDialog();


                        }


                        return true;
                    }
                });

                popup.show();


            }
        });

        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), spinner1List) {
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

        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner1.setAdapter(assetTypeAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                //If 0th position selected It will send request to aracane server to get ProfitAndLossEQ details
                    serviceName = "getProfitAndLossEQ_BajajV2";


                } else if (position == 1) {
                    //If 1st position selected It will send request to aracane server to get ProfitAndLossFO details
                    serviceName = "getProfitAndLossFO_BajajV2";

                }
                sendReportRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> TypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), spinner2List) {
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
        TypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner2.setAdapter(TypeAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerselect = spinner2List.get(position);
                if (spinner2List.get(position).equalsIgnoreCase("All Holdings")) {
                    if (datalist != null) {

                        ReportDataTableAdapter reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), datalist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, datalist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(datalist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, datalist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, datalist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(datalist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, datalist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });


                    }
                }
                else if (spinner2List.get(position).equalsIgnoreCase("Positive Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Positiveholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")){
                                if (datalist.get(i).getNetQty() != null && !datalist.get(i).getNetQty().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getNetQty()) > 0) {
                                        Positiveholdinglist.add(datalist.get(i));
                                    }
                                }
                            }else {

                                if (datalist.get(i).getQuantity() != null && !datalist.get(i).getQuantity().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getQuantity()) > 0) {
                                        Positiveholdinglist.add(datalist.get(i));
                                    }
                                }
                            }
                        }
                        Positiveholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "","",""));

                        ReportDataTableAdapter reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Positiveholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Positiveholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Positiveholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Positiveholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Positiveholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Positiveholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Positiveholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });


                    }
                }
                else if (spinner2List.get(position).equalsIgnoreCase("Zero Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Zerolholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {

                            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")){
                                if (datalist.get(i).getNetQty() != null && !datalist.get(i).getNetQty().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getNetQty()) == 0) {
                                        Zerolholdinglist.add(datalist.get(i));
                                    }
                                }
                            }else {
                                if (datalist.get(i).getQuantity() != null && !datalist.get(i).getQuantity().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getQuantity()) == 0) {
                                        Zerolholdinglist.add(datalist.get(i));
                                    }
                                }
                            }
                        }

                        Zerolholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "","",""));

                        ReportDataTableAdapter reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Zerolholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Zerolholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Zerolholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Zerolholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Zerolholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Zerolholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Zerolholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });


                    }
                }
                else {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Negativeholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")){
                                if (datalist.get(i).getNetQty() != null && !datalist.get(i).getNetQty().isEmpty()) {
                                    if (datalist.get(i).getNetQty().contains("-")) {
                                        Negativeholdinglist.add(datalist.get(i));
                                    }
                                }
                            }else {
                                if (datalist.get(i).getQuantity() != null && !datalist.get(i).getQuantity().isEmpty()) {
                                    if (datalist.get(i).getQuantity().contains("-")) {
                                        Negativeholdinglist.add(datalist.get(i));
                                    }
                                }
                            }
                        }

                        Negativeholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "","",""));

                        ReportDataTableAdapter reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Negativeholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Negativeholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Negativeholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Negativeholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Negativeholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Negativeholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Negativeholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void setupTheam(View view) {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            header.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_ttlPNL.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            errorMsgLayout.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sort_btn.setBackground(getResources().getDrawable(R.drawable.ic_filter));
            profitandloss_layout.setBackgroundColor(getResources().getColor(R.color.white));
            header_layout.setBackgroundColor(getResources().getColor(R.color.white));
            spinner1.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            spinner2.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
        }
    }


    private void sendReportRequest() {

        if (startDate.length() <= 0 || endDate.length() <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date monthFirstDay = calendar.getTime();
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthLastDay = calendar.getTime();

            startDate = df.format(monthFirstDay);
            Date c = Calendar.getInstance().getTime();
            endDate = df.format(c);
        }
        errorMsgLayout.setVisibility(View.VISIBLE);
        adaptiveTableLayout.setVisibility(View.GONE);

        showProgress();
        PandLReportRequest.sendRequest(AccountDetails.getUsername(getActivity()), serviceName, startDate, endDate, getActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        errorMsgLayout.setVisibility(View.GONE);
        adaptiveTableLayout.setVisibility(View.VISIBLE);
        ReportDataTableAdapter reportDataTableAdapter = null;
        if (response.toString().contains("getProfitAndLossEQ_BajajV2")) {


            try {

                Gson gson = new Gson();
                FOPLResponse staff = gson.fromJson(String.valueOf(response), FOPLResponse.class);


                FOPLResponse.Response.Data orderBookDataArrayList = staff.getResponse().getData();
                datalist = orderBookDataArrayList.getData();
                adaptiveTableLayout.setVisibility(View.VISIBLE);
                datalist.add(0, new FOPLResponse.Response.Data.FOPLData(
                        "", "", "",
                        "", "", "", "", "", "",
                        "", "", "", "",
                        "", "", "", "", "", "",
                        "", "", "", "","",""));

                    if (spinnerselect.equalsIgnoreCase("Positive Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Positiveholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (!datalist.get(i).getQuantity().equalsIgnoreCase("")) {
                                if (Integer.parseInt(datalist.get(i).getQuantity()) > 0) {
                                    Positiveholdinglist.add(datalist.get(i));
                                }
                            }

                        }
                        Positiveholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "","",""));

                        reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Positiveholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Positiveholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Positiveholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Positiveholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Positiveholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Positiveholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Positiveholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });


                    }
                } else if (spinnerselect.equalsIgnoreCase("Zero Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Zerolholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (!datalist.get(i).getQuantity().equalsIgnoreCase("")) {
                                if (Integer.parseInt(datalist.get(i).getQuantity()) == 0) {
                                    Zerolholdinglist.add(datalist.get(i));
                                }
                            }
                        }
                        Zerolholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "","",""));

                        reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Zerolholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Zerolholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Zerolholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Zerolholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Zerolholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Zerolholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Zerolholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });


                    }
                } else if (spinnerselect.equalsIgnoreCase("Negative Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Negativeholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (!datalist.get(i).getQuantity().equalsIgnoreCase("")) {
                                if (datalist.get(i).getQuantity().contains("-")) {
                                    Negativeholdinglist.add(datalist.get(i));
                                }
                            }
                        }
                        Negativeholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "","",""));
                        reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Negativeholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Negativeholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Negativeholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Negativeholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Negativeholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Negativeholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Negativeholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });

                    }
                } else {
                    reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), datalist);
                    adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                    adaptiveTableLayout.notifyLayoutChanged();
                    reportDataTableAdapter.notifyLayoutChanged();

                    reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int row, int column) {


                            Bundle args2 = new Bundle();
                            args2.putString(TradeFragment.SCRIP_NAME, datalist.get(row).getScripName());
                            args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(datalist.get(row).getOurtoken()));
                            args2.putString(TradeFragment.TOKEN, datalist.get(row).getOurtoken());
                            args2.putString(TradeFragment.TRADE_ACTION, "buy");
                            AccountDetails.globalPlaceOrderBundle = args2;
                            EventBus.getDefault().post("placeorder");
                        }

                        @Override
                        public void onRowHeaderClick(int row) {

                            Bundle args2 = new Bundle();
                            args2.putString(TradeFragment.SCRIP_NAME, datalist.get(row).getScripName());
                            args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(datalist.get(row).getOurtoken()));
                            args2.putString(TradeFragment.TOKEN, datalist.get(row).getOurtoken());
                            args2.putString(TradeFragment.TRADE_ACTION, "buy");
                            AccountDetails.globalPlaceOrderBundle = args2;
                            EventBus.getDefault().post("placeorder");

                        }

                        @Override
                        public void onColumnHeaderClick(int column) {

                        }

                        @Override
                        public void onLeftTopHeaderClick() {

                        }
                    });

                }


                hideProgress();
                toggleErrorLayout(false);


            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
                adaptiveTableLayout.setVisibility(View.GONE);
                toggleErrorLayout(true);

            }

        }
        else if (response.toString().contains("getProfitAndLossFO_BajajV2"))
        {


//            try {

            Gson gson = new Gson();
            FOPLResponse staff = gson.fromJson(String.valueOf(response), FOPLResponse.class);

            FOPLResponse.Response.Data orderBookDataArrayList = staff.getResponse().getData();
            datalist = orderBookDataArrayList.getData();
            adaptiveTableLayout.setVisibility(View.VISIBLE);
            if (datalist!=null) {
                hideProgress();
                adaptiveTableLayout.setVisibility(View.VISIBLE);
                toggleErrorLayout(false);
                datalist.add(0, new FOPLResponse.Response.Data.FOPLData(
                        "", "", "",
                        "", "", "", "", "", "",
                        "", "", "", "",
                        "", "", "", "", "", "",
                        "", "", "", "", "", ""));

                if (spinnerselect.equalsIgnoreCase("Positive Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Positiveholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")) {
                                if (datalist.get(i).getNetQty() != null && !datalist.get(i).getNetQty().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getNetQty()) > 0) {
                                        Positiveholdinglist.add(datalist.get(i));
                                    }
                                }
                            } else {
                                if (datalist.get(i).getQuantity() != null && !datalist.get(i).getQuantity().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getQuantity()) > 0) {
                                        Positiveholdinglist.add(datalist.get(i));
                                    }
                                }
                            }

                        }
                        Positiveholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "", "", ""));

                        reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Positiveholdinglist);

                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Positiveholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Positiveholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Positiveholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Positiveholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Positiveholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Positiveholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });


                    }
                } else if (spinnerselect.equalsIgnoreCase("Zero Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Zerolholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")) {
                                if (datalist.get(i).getNetQty() != null && !datalist.get(i).getNetQty().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getNetQty()) == 0) {
                                        Zerolholdinglist.add(datalist.get(i));
                                    }
                                }
                            } else {
                                if (datalist.get(i).getQuantity() != null && !datalist.get(i).getQuantity().isEmpty()) {
                                    if (Integer.parseInt(datalist.get(i).getQuantity()) == 0) {
                                        Zerolholdinglist.add(datalist.get(i));
                                    }
                                }
                            }
                        }
                        Zerolholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "", "", ""));

                        reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Zerolholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Zerolholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Zerolholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Zerolholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Zerolholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Zerolholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Zerolholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });


                    }
                } else if (spinnerselect.equalsIgnoreCase("Negative Holdings")) {
                    if (datalist != null) {
                        final ArrayList<FOPLResponse.Response.Data.FOPLData> Negativeholdinglist = new ArrayList<>();
                        for (int i = 0; i < datalist.size(); i++) {
                            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")) {
                                if (datalist.get(i).getNetQty() != null && !datalist.get(i).getNetQty().isEmpty()) {
                                    if (datalist.get(i).getNetQty().contains("-")) {
                                        Negativeholdinglist.add(datalist.get(i));
                                    }
                                }
                            } else {
                                if (datalist.get(i).getQuantity() != null && !datalist.get(i).getQuantity().isEmpty()) {
                                    if (datalist.get(i).getQuantity().contains("-")) {
                                        Negativeholdinglist.add(datalist.get(i));
                                    }
                                }
                            }
                        }
                        Negativeholdinglist.add(0, new FOPLResponse.Response.Data.FOPLData(
                                "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "",
                                "", "", "", "", "", "",
                                "", "", "", "", "", ""));
                        reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), Negativeholdinglist);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();
                        reportDataTableAdapter.notifyLayoutChanged();

                        reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int row, int column) {


                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Negativeholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Negativeholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Negativeholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");
                            }

                            @Override
                            public void onRowHeaderClick(int row) {

                                Bundle args2 = new Bundle();
                                args2.putString(TradeFragment.SCRIP_NAME, Negativeholdinglist.get(row).getScripName());
                                args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(Negativeholdinglist.get(row).getOurtoken()));
                                args2.putString(TradeFragment.TOKEN, Negativeholdinglist.get(row).getOurtoken());
                                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                                AccountDetails.globalPlaceOrderBundle = args2;
                                EventBus.getDefault().post("placeorder");

                            }

                            @Override
                            public void onColumnHeaderClick(int column) {

                            }

                            @Override
                            public void onLeftTopHeaderClick() {

                            }
                        });

                    }
                } else {
                    reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), datalist);
                    adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                    adaptiveTableLayout.notifyLayoutChanged();
                    reportDataTableAdapter.notifyLayoutChanged();

                    reportDataTableAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int row, int column) {


                            Bundle args2 = new Bundle();
                            args2.putString(TradeFragment.SCRIP_NAME, datalist.get(row).getScripName());
                            args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(datalist.get(row).getOurtoken()));
                            args2.putString(TradeFragment.TOKEN, datalist.get(row).getOurtoken());
                            args2.putString(TradeFragment.TRADE_ACTION, "buy");
                            AccountDetails.globalPlaceOrderBundle = args2;
                            EventBus.getDefault().post("placeorder");
                        }

                        @Override
                        public void onRowHeaderClick(int row) {

                            Bundle args2 = new Bundle();
                            args2.putString(TradeFragment.SCRIP_NAME, datalist.get(row).getScripName());
                            args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(datalist.get(row).getOurtoken()));
                            args2.putString(TradeFragment.TOKEN, datalist.get(row).getOurtoken());
                            args2.putString(TradeFragment.TRADE_ACTION, "buy");
                            AccountDetails.globalPlaceOrderBundle = args2;
                            EventBus.getDefault().post("placeorder");

                        }

                        @Override
                        public void onColumnHeaderClick(int column) {

                        }

                        @Override
                        public void onLeftTopHeaderClick() {

                        }
                    });

                }
            }else{
                hideProgress();
                adaptiveTableLayout.setVisibility(View.GONE);
                toggleErrorLayout(true);
            }





//            } catch (Exception e) {
//                e.printStackTrace();
//                hideProgress();
//                adaptiveTableLayout.setVisibility(View.GONE);
//                toggleErrorLayout(true);
//
//            }

        }


    }

    public class ReportDataTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

        private final LayoutInflater mLayoutInflater;
        private ArrayList<FOPLResponse.Response.Data.FOPLData> mTableDataSource;
        private final int mColumnWidth;
        private final int mRowHeight;
        private final int mHeaderHeight;
        private final int mHeaderWidth;
        private Context context;

        public ReportDataTableAdapter(Context context, ArrayList<FOPLResponse.Response.Data.FOPLData> tableDataSource) {
            mLayoutInflater = LayoutInflater.from(context);
            mTableDataSource = new ArrayList<FOPLResponse.Response.Data.FOPLData>();
            mTableDataSource.clear();
            mTableDataSource = tableDataSource;
            Resources res = context.getResources();
            mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width_fo_report);
            mRowHeight = res.getDimensionPixelSize(R.dimen.row_height_fo_report);
            mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height_fo_report);
            mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width_fo_report);
        }

        public void setAppList(ArrayList<FOPLResponse.Response.Data.FOPLData> categoryModel) {

            mTableDataSource.clear();
            mTableDataSource.addAll(categoryModel);
            notifyDataSetChanged();
        }

        public FOPLResponse.Response.Data.FOPLData getItemScanData(int position) {
            return mTableDataSource.get(position);
        }

        @Override
        public int getRowCount() {
            return mTableDataSource.size();
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
            return new TestViewHolder1(mLayoutInflater.inflate(R.layout.item_card, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.report_header_row, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder1, int row, int column) {
            final TestViewHolder1 vh = (TestViewHolder1) viewHolder1;


            FOPLResponse.Response.Data.FOPLData itemData = mTableDataSource.get(row); // skip headers

            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")) {
                if (column == 1) {
                    if (itemData.getBuyAvg() != null && !itemData.getBuyAvg().isEmpty()) {

                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getBuyAvg())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");
                    }
                    if (itemData.getBuyQty() != null && !itemData.getBuyQty().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getBuyQty())));
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");
                    }

                } else if (column == 2) {
                    if (itemData.getSellAvg() != null && !itemData.getSellAvg().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getSellAvg())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");
                    }
                    if (itemData.getSellQty() != null && !itemData.getSellQty().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getSellQty())));
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");
                    }

                } else if (column == 3) {
                    if (itemData.getNetPrice() != null && !itemData.getNetPrice().isEmpty()) {

                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getNetPrice())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");
                    }
                    if (itemData.getNetQty() != null && !itemData.getNetQty().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getNetQty())));
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");
                    }
                } else if (column == 4) {
                    if (itemData.getGLToday() != null && !itemData.getGLToday().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLToday())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }
                    if (itemData.getGLTodayPerc() != null && !itemData.getGLTodayPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLTodayPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");
                    }
                } else if (column == 5) {
                    if (itemData.getGLUnrealized() != null && !itemData.getGLUnrealized().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLUnrealized())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }
                    if (itemData.getGLUnrealPerc() != null && !itemData.getGLUnrealPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLUnrealPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");

                    }
                } else if (column == 6) {
                    if (itemData.getGLRealized() != null && !itemData.getGLRealized().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLRealized())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");
                    }
                    if (itemData.getGLRealPerc() != null && !itemData.getGLRealPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLRealPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");
                    }
                } else if (column == 7) {
                    if (itemData.getGLTotal() != null && !itemData.getGLTotal().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLTotal())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }
                    if (itemData.getGLTotalPerc() != null && !itemData.getGLTotalPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLTotalPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");

                    }
                }

            }else{
                if (column == 1) {
                    if (itemData.getQuantity() != null && !itemData.getQuantity().isEmpty()) {
                        vh.dataTitle.setText(itemData.getQuantity());
                        vh.dataSubtitle.setText("");
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");
                    }

                } else if (column == 2) {
                    if (itemData.getBuyAvg() != null && !itemData.getBuyAvg().isEmpty()) {
                        String[] parts = itemData.getBuyAvg().split("\\|");
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(parts[1])));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }


                    if (itemData.getInvestValue() != null && !itemData.getInvestValue().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getInvestValue())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");

                    }

                } else if (column == 3) {
                    if (itemData.getCurrentPrice() != null && !itemData.getCurrentPrice().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getCurrentPrice())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }
                    if (itemData.getCurrValue() != null && !itemData.getCurrValue().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getCurrValue())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");

                    }
                } else if (column == 4) {
                    if (itemData.getGLToday() != null && !itemData.getGLToday().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLToday())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }
                    if (itemData.getGLTodayPerc() != null && !itemData.getGLTodayPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLTodayPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");
                    }
                } else if (column == 5) {
                    if (itemData.getGLUnrealized() != null && !itemData.getGLUnrealized().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLUnrealized())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }
                    if (itemData.getGLUnrealPerc() != null && !itemData.getGLUnrealPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLUnrealPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");

                    }
                } else if (column == 6) {
                    if (itemData.getGLRealized() != null && !itemData.getGLRealized().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLRealized())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");
                    }
                    if (itemData.getGLRealPerc() != null && !itemData.getGLRealPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLRealPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");
                    }
                } else if (column == 7) {
                    if (itemData.getGLTotal() != null && !itemData.getGLTotal().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLTotal())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText("");

                    }
                    if (itemData.getGLTotalPerc() != null && !itemData.getGLTotalPerc().isEmpty()) {
                        vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGLTotalPerc())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        vh.dataSubtitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataSubtitle.setText("");

                    }
                }
            }
            //vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getTOTAL())));
//            } else if (column == 8) {
//                if (itemData.getSellQty() != null && !itemData.getSellQty().isEmpty()) {
//                    vh.dataTitle.setText(itemData.getBuyQty()+" | "+itemData.getSellQty());
//                }else{
//                    vh.dataTitle.setText("");
//                }
//                //vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getTOTAL())));
//            }


        }

        @Override
        public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
            TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;

            if (serviceName.equalsIgnoreCase("getProfitAndLossFO_BajajV2")){
                if (column == 1) {
                    vh.headerColumnTitle.setText("Avg. Buy Price ( \u20B9 )");  // skip left top header
                    vh.headerColumnSubtitle.setText("Buy Qty");  // skip left top header
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 2) {
                    vh.headerColumnTitle.setText("Avg. Sell Price ( \u20B9 )");  // skip left top header
                    vh.headerColumnSubtitle.setText("Sell Qty");  // skip left top header
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 3) {

                    vh.headerColumnTitle.setText("Net Avg. Price ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Net Qty ( \u20B9 )");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 4) {
                    vh.headerColumnTitle.setText("Gain/Loss ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Today (%)");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 5) {
                    vh.headerColumnTitle.setText("Gain/Loss ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Unrealised(%)");

                } else if (column == 6) {
                    vh.headerColumnTitle.setText("Gain/Loss  ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Realised (%)");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 7) {
                    vh.headerColumnTitle.setText("Total P&L");
                    vh.headerColumnSubtitle.setText(" ( % )");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                }

            }else {
                if (column == 1) {
                    vh.headerColumnTitle.setText("Quantity");  // skip left top header
                    vh.headerColumnSubtitle.setText("");  // skip left top header
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 2) {
                    vh.headerColumnTitle.setText("Avg. Buy Price ( \u20B9 )");  // skip left top header
                    vh.headerColumnSubtitle.setText("Investment Val( \u20B9 )");  // skip left top header
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 3) {

                    vh.headerColumnTitle.setText("Current Price ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Current Val ( \u20B9 )");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 4) {
                    vh.headerColumnTitle.setText("Gain/Loss ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Today (%)");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 5) {
                    vh.headerColumnTitle.setText("Gain/Loss ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Unrealised(%)");

                } else if (column == 6) {
                    vh.headerColumnTitle.setText("Gain/Loss  ( \u20B9 )");
                    vh.headerColumnSubtitle.setText("Realised (%)");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                } else if (column == 7) {
                    vh.headerColumnTitle.setText("Total P&L");
                    vh.headerColumnSubtitle.setText(" ( % )");
                    vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                    vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
                }
            }
        }

        @Override
        public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
            TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;
            vh.headerRowTitle.setText(mTableDataSource.get(row).getScripName());

            SpannableStringBuilder builder = new SpannableStringBuilder();

            if (mTableDataSource.get(row).getLtp() != null && !mTableDataSource.get(row).getLtp().isEmpty()
                    && mTableDataSource.get(row).getChange() != null && !mTableDataSource.get(row).getChange().isEmpty()
                    && mTableDataSource.get(row).getPerChange() != null && !mTableDataSource.get(row).getPerChange().isEmpty()) {
                String getLtp = String.format("%.2f", Double.parseDouble(mTableDataSource.get(row).getLtp())) + " | ";
                SpannableString whiteSpannable = new SpannableString(getLtp);


                whiteSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, getLtp.length(), 0);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    whiteSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, getLtp.length(), 0);
                }
                builder.append(whiteSpannable);
                if (mTableDataSource.get(row).getChange().contains("-")) {
                    String getChange = String.format("%.2f", Double.parseDouble(mTableDataSource.get(row).getChange())) + " ";
                    SpannableString whiteSpannable1 = new SpannableString(getChange);
                    whiteSpannable1.setSpan(new ForegroundColorSpan(Color.RED), 0, getChange.length(), 0);
                    builder.append(whiteSpannable1);
                } else {
                    String getChange = String.format("%.2f", Double.parseDouble(mTableDataSource.get(row).getChange())) + " ";
                    SpannableString whiteSpannable1 = new SpannableString(getChange);
                    whiteSpannable1.setSpan(new ForegroundColorSpan(Color.GREEN), 0, getChange.length(), 0);
                    builder.append(whiteSpannable1);
                }

                if (mTableDataSource.get(row).getPerChange().contains("-")) {
                    String getPerChange = "( " + String.format("%.2f", Double.parseDouble(mTableDataSource.get(row).getPerChange())) + " )";
                    SpannableString whiteSpannable2 = new SpannableString(getPerChange);
                    whiteSpannable2.setSpan(new ForegroundColorSpan(Color.RED), 0, getPerChange.length(), 0);
                    builder.append(whiteSpannable2);
                } else {
                    String getPerChange = "( " + String.format("%.2f", Double.parseDouble(mTableDataSource.get(row).getPerChange())) + " )";
                    SpannableString whiteSpannable2 = new SpannableString(getPerChange);
                    whiteSpannable2.setSpan(new ForegroundColorSpan(Color.GREEN), 0, getPerChange.length(), 0);
                    builder.append(whiteSpannable2);
                }
                vh.headerRowSubtitle.setText(builder);
            } else {
                vh.headerRowSubtitle.setText("00.00 | 00.00 ( 00.00 )");
            }
        }

        @Override
        public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
            TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;
            vh.leftTopTitle.setText("Scrip Name");
            vh.leftTopSubtitle.setText("LTP | Change (%)");
        }

        @Override
        public int getColumnWidth(int column) {
            return mColumnWidth;
        }

        @Override
        public int getHeaderColumnHeight() {
            return mHeaderHeight;
        }

        @Override
        public int getRowHeight(int row) {
            return mRowHeight;
        }

        @Override
        public int getHeaderRowWidth() {
            return mHeaderWidth;
        }

        //------------------------------------- view holders ------------------------------------------

        private class TestViewHolder1 extends ViewHolderImpl {
            GreekTextView dataSubtitle, dataTitle;
            LinearLayout headerColumn;
            View vLine, viewLine2;
            private TestViewHolder1(@NonNull View itemView) {
                super(itemView);
                dataTitle = itemView.findViewById(R.id.dataTitle);
                dataSubtitle = itemView.findViewById(R.id.dataSubtitle);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);
                headerColumn = itemView.findViewById(R.id.linear1);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    headerColumn.setBackgroundColor(getResources().getColor(R.color.white));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
                    dataTitle.setTextColor(getResources().getColor(R.color.black));
                    dataSubtitle.setTextColor(getResources().getColor(R.color.black));
                }
            }
        }

        private class TestHeaderColumnViewHolder extends ViewHolderImpl {
            GreekTextView headerColumnSubtitle, headerColumnTitle;

            View vLine, viewLine2;
            LinearLayout headerRow;
            private TestHeaderColumnViewHolder(@NonNull View itemView) {
                super(itemView);
                headerColumnTitle = itemView.findViewById(R.id.dataTitle);
                headerColumnSubtitle = itemView.findViewById(R.id.dataSubtitle);
                vLine = itemView.findViewById(R.id.vLine);

                headerRow = itemView.findViewById(R.id.layout1);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    headerRow.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
                    headerColumnTitle.setTextColor(getResources().getColor(R.color.white));
                    headerColumnSubtitle.setTextColor(getResources().getColor(R.color.white));
                }


            }
        }

        private class TestHeaderRowViewHolder extends ViewHolderImpl {
            GreekTextView headerRowTitle, headerRowSubtitle;
            LinearLayout headerRow;
            TestHeaderRowViewHolder(@NonNull View itemView) {
                super(itemView);
                headerRowTitle = itemView.findViewById(R.id.title);
                headerRowSubtitle = itemView.findViewById(R.id.subTitle);
                headerRow = itemView.findViewById(R.id.linear);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    headerRow.setBackgroundColor(getResources().getColor(R.color.white));
                    headerRowTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    headerRowSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }
        }

        private class TestHeaderLeftTopViewHolder extends ViewHolderImpl {
            GreekTextView leftTopSubtitle, leftTopTitle;
            LinearLayout layoutHeaderLeft ;
            private TestHeaderLeftTopViewHolder(@NonNull View itemView) {
                super(itemView);
                leftTopTitle = itemView.findViewById(R.id.title);
                leftTopSubtitle = itemView.findViewById(R.id.subTitle);
                layoutHeaderLeft = itemView.findViewById(R.id.layout);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    layoutHeaderLeft.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
//                    leftTopTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                    leftTopSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }
        }
    }





    private String getExchange(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else {
            return "BSE";
        }
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getMainActivity().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getMainActivity()).inflate(R.layout.custom_date_layout, viewGroup, false);


        final GreekEditText edt_from = dialogView.findViewById(R.id.edt_from);
        final GreekEditText edt_to = dialogView.findViewById(R.id.edt_to);
        final LinearLayout data_popup_layout = dialogView.findViewById(R.id.custom_date_parentLayout);
        final LinearLayout middle_layout = dialogView.findViewById(R.id.middle_layout);
        final GreekTextView selectDate_txt = dialogView.findViewById(R.id.selectDate_txt);
        final GreekTextView fron_txt = dialogView.findViewById(R.id.fron_txt);
        final GreekTextView to_txt = dialogView.findViewById(R.id.to_txt);
        final View line = dialogView.findViewById(R.id.line);

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            data_popup_layout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            middle_layout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            selectDate_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            fron_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            to_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edt_from.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edt_to.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edt_from.setBackgroundTintList(getResources().getColorStateList(R.color.gray_border));
            edt_to.setBackgroundTintList(getResources().getColorStateList(R.color.gray_border));
            line.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_border));


        }
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        edt_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = getInstance();
                year = calendar.get(YEAR);
                month = calendar.get(MONTH);
                day = calendar.get(DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edt_from.setText(dayOfMonth + "/" + (month + 1) + "/" + year);


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        try {
                            Date date = simpleDateFormat.parse(edt_from.getText().toString());
                            startDate = df.format(date);
                            edt_from.setText(startDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                dpd.show();
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());

            }
        });


        edt_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = getInstance();
                year = calendar.get(YEAR);
                month = calendar.get(MONTH);
                day = calendar.get(DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edt_to.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        try {
                            Date date = simpleDateFormat.parse(edt_to.getText().toString());
                            endDate = df.format(date);
                            edt_to.setText(endDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, year, month, day);

                dpd.show();
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());

            }
        });


        Button btn_save = dialogView.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendReportRequest();
                alertDialog.cancel();
            }
        });


    }

    private void toggleErrorLayout(boolean show) {

        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);

    }


}

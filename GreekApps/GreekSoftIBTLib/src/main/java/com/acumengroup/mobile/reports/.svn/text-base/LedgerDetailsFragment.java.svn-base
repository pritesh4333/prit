package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.LedgerData;
import com.acumengroup.mobile.model.LedgerReportRequest;
import com.acumengroup.mobile.model.LedgerReportResponse;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;


public class LedgerDetailsFragment extends GreekBaseFragment {


    private Spinner spinner_type, spinner_date;
    private GreekTextView emptyView;

    List<String> detailsList, reportList, dataList;
    private RecyclerView recyclerView;
    private MyRecyclerviewAdapter myRecyclerviewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton sort_btn;
    String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    ServiceResponseHandler serviceResponseHandler;
    private DateFormat df;
    private ArrayList<LedgerData> reportArrayList, tempArrayList;
    private String isBseOrNseFilter;
    LinearLayout linear_summary, linear_columns, spinner_layout;
    RelativeLayout ledger_relative;
    GreekTextView txt_closing, txt_reversal, txt_miscell, txt_ipo, txt_mfsssegment,
            txt_derivative, txt_cash, txt_utilization, txt_fundWith, txt_fundRecv, txt_opnBalance, txt_fundInOut;
    private TextView errorMsgLayout;

    public LedgerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dataList = new ArrayList<>();
        detailsList = new ArrayList<>();
        detailsList.add("ALL");
        detailsList.add("NSECM");
        detailsList.add("BSECM");
        detailsList.add("NSEFO");
        detailsList.add("BSEFO");
        detailsList.add("MFSS");


        reportList = new ArrayList<>();
//        reportList.add("Summary");
        reportList.add("Detailed");


        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ledger, container, false);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        spinner_date = view.findViewById(R.id.spinner1);
        spinner_type = view.findViewById(R.id.spinner2);
        linear_summary = view.findViewById(R.id.linear_summary);
        linear_columns = view.findViewById(R.id.linear_columns);
        spinner_layout = view.findViewById(R.id.spinner_layout);
        ledger_relative = view.findViewById(R.id.ledger_relative);

        errorMsgLayout = view.findViewById(R.id.txt_error_msg);

        txt_opnBalance = view.findViewById(R.id.txt_opnBalance);
        txt_closing = view.findViewById(R.id.txt_closing);
        txt_reversal = view.findViewById(R.id.txt_reversal);
        txt_miscell = view.findViewById(R.id.txt_miscell);
        txt_ipo = view.findViewById(R.id.txt_ipo);
        txt_mfsssegment = view.findViewById(R.id.txt_segment);
        txt_derivative = view.findViewById(R.id.txt_derivative);
        txt_cash = view.findViewById(R.id.txt_cash);
        txt_utilization = view.findViewById(R.id.txt_utilization);
        txt_fundWith = view.findViewById(R.id.txt_fundWith);
        txt_fundRecv = view.findViewById(R.id.txt_fundRecv);
        txt_fundInOut = view.findViewById(R.id.txt_fundInOut);

        recyclerView = view.findViewById(R.id.recyclerView);

        swipeRefreshLayout = view.findViewById(R.id.refreshList);

        AccountDetails.currentFragment = NAV_TO_LEDGER_DETAIL_REPORT;

        tempArrayList = new ArrayList<>();
        reportArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        myRecyclerviewAdapter = new MyRecyclerviewAdapter(getMainActivity(), tempArrayList);
        recyclerView.setAdapter(myRecyclerviewAdapter);


        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        sort_btn = view.findViewById(R.id.sort_btn);

        setupTheam(view);


        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup;
                //Inflating the Popup using xml file
                Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    popup = new PopupMenu(wrapper, sort_btn);
                } else {
                    popup = new PopupMenu(getMainActivity(), sort_btn);
                }
                popup.getMenuInflater().inflate(R.menu.sort_pop_menu, popup.getMenu());
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
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), detailsList) {
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
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner_date.setAdapter(assetTypeAdapter);
        spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                    isBseOrNseFilter = "ALL";

                    if (reportArrayList != null) {

                        tempArrayList.addAll(reportArrayList);
                        myRecyclerviewAdapter.notifyDataSetChanged();
                    }

                } else if (position == 1 && reportArrayList != null) {

                    isBseOrNseFilter = "NSECM";


                    tempArrayList.clear();
                    for (int i = 0; i < reportArrayList.size(); i++) {

                        if (reportArrayList.get(i).getEXCHANGE().equalsIgnoreCase("NSECM")) {
                            tempArrayList.add(reportArrayList.get(i));
                        }
                    }

                    myRecyclerviewAdapter.notifyDataSetChanged();

                } else if (position == 2 && reportArrayList != null) {

                    isBseOrNseFilter = "BSECM";
                    tempArrayList.clear();

                    for (int i = 0; i < reportArrayList.size(); i++) {

                        if (reportArrayList.get(i).getEXCHANGE().equalsIgnoreCase("BSECM")) {
                            tempArrayList.add(reportArrayList.get(i));
                        }
                    }

                    myRecyclerviewAdapter.notifyDataSetChanged();

                } else if (position == 3 && reportArrayList != null) {

                    isBseOrNseFilter = "NSEFO";
                    tempArrayList.clear();

                    for (int i = 0; i < reportArrayList.size(); i++) {

                        if (reportArrayList.get(i).getEXCHANGE().equalsIgnoreCase("NSEFO")) {
                            tempArrayList.add(reportArrayList.get(i));
                        }
                    }

                    myRecyclerviewAdapter.notifyDataSetChanged();

                } else if (position == 4 && reportArrayList != null) {

                    isBseOrNseFilter = "BSEFO";
                    tempArrayList.clear();

                    for (int i = 0; i < reportArrayList.size(); i++) {

                        if (reportArrayList.get(i).getEXCHANGE().equalsIgnoreCase("BSEFO")) {
                            tempArrayList.add(reportArrayList.get(i));
                        }
                    }

                    myRecyclerviewAdapter.notifyDataSetChanged();
                } else if (position == 5 && reportArrayList != null) {

                    isBseOrNseFilter = "MFSS";
                    tempArrayList.clear();

                    for (int i = 0; i < reportArrayList.size(); i++) {

                        if (reportArrayList.get(i).getEXCHANGE().equalsIgnoreCase("MFSS")) {
                            tempArrayList.add(reportArrayList.get(i));
                        }
                    }

                    myRecyclerviewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> TypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), reportList) {
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
        TypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner_type.setAdapter(TypeAdapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      /*          if (position == 1) {

                    spinner_date.setSelection(0);
                    spinner_date.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
                    linear_summary.setVisibility(View.GONE);
                    linear_columns.setVisibility(View.VISIBLE);


                } else if (position == 0) {
                    spinner_date.setSelection(0);
                    spinner_date.setEnabled(false);

                    recyclerView.setVisibility(View.GONE);
                    linear_summary.setVisibility(View.VISIBLE);
                    linear_columns.setVisibility(View.GONE);


                }*/

                spinner_date.setSelection(0);
                spinner_date.setEnabled(true);
                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
                linear_summary.setVisibility(View.GONE);
                linear_columns.setVisibility(View.VISIBLE);

                sendReportRequest();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void setupTheam(View view) {
        if (AccountDetails.getThemeFlag(view.getContext()).equals("white")) {
            spinner_layout.setBackgroundColor(getResources().getColor(R.color.white));
            sort_btn.setBackground(view.getContext().getResources().getDrawable(R.drawable.ic_filter));
            ledger_relative.setBackgroundColor(getResources().getColor(R.color.white));
            errorMsgLayout.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            linear_summary.setBackgroundColor(getResources().getColor(R.color.white));
            spinner_date.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            spinner_type.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
        }
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {

            sendReportRequest();

        }
    };

    private void refreshComplete() {
        hideProgress();
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
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
                if(edt_to.getText().toString().length()>0&&edt_from.getText().toString().length()>0) {
                    sendReportRequest();
                    alertDialog.cancel();
                }else{
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Please select from date and to date", "OK", true, null);
                   /* if(startDate.equalsIgnoreCase("")){
                        edt_from.setError("please select date");
                    } else if(endDate.equalsIgnoreCase("")){
                        edt_to.setError("please select date");
                    }*/
                }

            }
        });


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

        showProgress();

        if (linear_summary.getVisibility() != View.VISIBLE) {

            LedgerReportRequest.sendRequest(AccountDetails.getUsername(getActivity()), startDate, endDate, getActivity(), serviceResponseHandler);
        } else {

            LedgerReportRequest.sendRequestSummary(AccountDetails.getUsername(getActivity()), startDate, endDate, getActivity(), serviceResponseHandler);

        }


    }

    private void toggleErrorLayout(boolean show) {

        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void handleResponse(Object response) {
        refreshComplete();
        super.handleResponse(response);

        JSONResponse jsonResponse = (JSONResponse) response;
        LedgerReportResponse ledgerReportResponse;
        hideProgress();


        if (jsonResponse.getServiceName().equalsIgnoreCase("getLedgerDetails_Bajaj")) {
            reportArrayList.clear();
            tempArrayList.clear();
            try {
                ledgerReportResponse = (LedgerReportResponse) jsonResponse.getResponse();


                for (int i = 0; i < ledgerReportResponse.getSaudaResponse().size(); i++) {

                    reportArrayList.add(ledgerReportResponse.getSaudaResponse().get(i));
                }

                if (reportArrayList.size() > 0) {


                    for (int i = 0; i < reportArrayList.size(); i++) {

                        if (reportArrayList.get(i).getEXCHANGE().equalsIgnoreCase(isBseOrNseFilter)) {
                            tempArrayList.add(reportArrayList.get(i));
                        }
                    }

                    if (tempArrayList.size() > 0) {

                    } else if (isBseOrNseFilter.equalsIgnoreCase("ALL")) {

                        tempArrayList.addAll(reportArrayList);
                    }

                    toggleErrorLayout(false);

                } else {
                    toggleErrorLayout(true);
                }


            } catch (Exception e) {
                e.printStackTrace();
                toggleErrorLayout(true);
            }
            myRecyclerviewAdapter.notifyDataSetChanged();

        } else if (jsonResponse.getServiceName().equalsIgnoreCase("getLedgerSummery_Bajaj")) {
            toggleErrorLayout(false);
            try {
                ledgerReportResponse = (LedgerReportResponse) jsonResponse.getResponse();
                toggleErrorLayout(false);

                for (int i = 0; i < ledgerReportResponse.getSaudaResponse().size(); i++) {


                    txt_closing.setText(String.format("%.2f", Double.parseDouble(ledgerReportResponse.getSaudaResponse().get(i).getClsBal())));
                    txt_opnBalance.setText(String.format("%.2f", Double.parseDouble(ledgerReportResponse.getSaudaResponse().get(i).getOpBal())));


                    float reversalValue = Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getReversalDebit()) - Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getReversalCredit());


                    txt_reversal.setText(String.format("%.2f", reversalValue));

                    txt_miscell.setText(String.format("%.2f", reversalValue));


                    double cashSegment = Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getCashDebit()) - Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getCashCredit());
                    txt_cash.setText(String.format("%.2f", cashSegment));


                    double derivativeSegment = Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getDerivativeDebit()) - Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getDerivativeCredit());
                    txt_derivative.setText(String.format("%.2f", derivativeSegment));


                    double mffSegment = Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getMfssDebit()) - Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getMfssCredit());

                    txt_mfsssegment.setText(String.format("%.2f", mffSegment));


                    double IPOSegment = Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getIpoDebit()) - Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getIpoCredit());

                    txt_mfsssegment.setText(String.format("%.2f", IPOSegment));

                    double utilization = cashSegment + derivativeSegment + mffSegment + IPOSegment;

                    txt_utilization.setText(String.format("%.2f", utilization));

                    txt_fundWith.setText(String.format("%.2f", Double.parseDouble(ledgerReportResponse.getSaudaResponse().get(i).getFundWithdrawn())));

                    txt_fundRecv.setText(String.format("%.2f", Double.parseDouble(ledgerReportResponse.getSaudaResponse().get(i).getFundRecieved())));

                    double value = Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getFundWithdrawn()) + Float.parseFloat(ledgerReportResponse.getSaudaResponse().get(i).getFundRecieved());
                    txt_fundInOut.setText(String.format("%.2f", value));

                }


            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
            }
        }


    }

    private class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ViewHolder> {

        Context context;
        ArrayList<LedgerData> list;

        public MyRecyclerviewAdapter(Context context, ArrayList<LedgerData> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.row_ledger_layout, parent, false);

            return new MyRecyclerviewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyRecyclerviewAdapter.ViewHolder holder, int position) {

            LedgerData ledgerData = list.get(position);

           /* Date date = null;
            String dateS = null;
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                dateS = formatter.format(df.parse(ledgerData.getVOUCHER_DATE()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            if (AccountDetails.getThemeFlag(getContext()).equals("white")) {
                holder.txt_date.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_Exchange.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.txtChq.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_Voucher.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_Statement.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_Payment.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_Balance.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_narration.setTextColor(getContext().getResources().getColor(AccountDetails.textColorDropdown));
                holder.row_ledger_layout.setBackgroundColor(getContext().getResources().getColor(R.color.white));

            }

//            holder.txt_date.setText(ledgerData.getVOUCHER_DATE());
            holder.txt_date.setText(ledgerData.getVOUCHER_DATE());
            holder.txt_Exchange.setText(ledgerData.getEXCHANGE());
            holder.txtChq.setText(ledgerData.getCHEQUE_DDNO());
            holder.txt_Voucher.setText(ledgerData.getVOUCHER_TYPE());
            holder.txt_Statement.setText(ledgerData.getVOUCHER_NO());


            if (Double.parseDouble(ledgerData.getCREDIT()) > 0.0) {
                holder.txt_Payment.setText(StringStuff.commaDecorator(ledgerData.getCREDIT()) + " cr.");
            } else {
                holder.txt_Payment.setText(StringStuff.commaDecorator(ledgerData.getDEBIT()) + " dr.");
            }

            holder.txt_Balance.setText(StringStuff.commaDecorator(ledgerData.getBALANCE()));

            holder.txt_narration.setText(ledgerData.getNARRATION());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_date, txt_Exchange, txtChq, txt_Statement, txt_Voucher, txt_Payment, txt_Balance, txt_narration;
            LinearLayout row_ledger_layout;

            public ViewHolder(View itemView) {
                super(itemView);
                txt_date = itemView.findViewById(R.id.txt_date);
                txt_Exchange = itemView.findViewById(R.id.txt_Exchange);
                txtChq = itemView.findViewById(R.id.txtChq_);
                txt_Statement = itemView.findViewById(R.id.txt_Statement);
                txt_Voucher = itemView.findViewById(R.id.txt_Voucher);
                txt_Payment = itemView.findViewById(R.id.txt_Payment);
                txt_Balance = itemView.findViewById(R.id.txt_Balance);
                txt_narration = itemView.findViewById(R.id.txt_narration);
                row_ledger_layout = itemView.findViewById(R.id.row_ledger_layout);


            }
        }
    }

}

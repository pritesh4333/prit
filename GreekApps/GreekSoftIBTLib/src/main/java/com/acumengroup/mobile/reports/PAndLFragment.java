package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.PLData;
import com.acumengroup.mobile.model.PandLReportRequest;
import com.acumengroup.mobile.model.PandLReportResponse;
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

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;


public class PAndLFragment extends GreekBaseFragment {

    private Spinner spinner_report, spinner_date;
    List<String> detailsList, reportList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private DateFormat df;
    private String servicetype;
    ServiceResponseHandler serviceResponseHandler;
    private int year, month, day;
    private Calendar calendar;
    private TextView errorMsgLayout;
    private ArrayList<PLData> arrayList;
    private MyRecyclerviewAdapter myRecyclerviewAdapter;
    GreekTextView txt_ttlPNL;
    double total_pnl = 00.00;

    public PAndLFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        detailsList = new ArrayList<>();
        detailsList.add("EQ");
        detailsList.add("FNO");


        reportList = new ArrayList<>();
        reportList.add("Ledger Report");
        reportList.add("DII Cash");
        reportList.add("FII Future");
        reportList.add("FII Options");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_pand_l, container, false);
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);


        arrayList = new ArrayList<>();

        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        spinner_date = view.findViewById(R.id.spinner1);
        spinner_report = view.findViewById(R.id.spinner2);
        errorMsgLayout = view.findViewById(R.id.txt_error_msg);
        txt_ttlPNL = view.findViewById(R.id.txt_ttlPNL);


        myRecyclerviewAdapter = new MyRecyclerviewAdapter(getMainActivity(), arrayList);


        sort_btn = view.findViewById(R.id.sort_btn);

        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getContext(), sort_btn);
                //Inflating the Popup using xml file
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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        recyclerView.setAdapter(myRecyclerviewAdapter);
        swipeRefreshLayout = view.findViewById(R.id.refreshList);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);


        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), detailsList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(getResources().getColor(R.color.white));
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
        spinner_date.setAdapter(assetTypeAdapter);
        spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    //If 0th position selected It will send request to aracane server to get ProfitAndLossEQ details
                    servicetype = "getProfitAndLossEQ_Bajaj";

                } else if (position == 1) {
                    //If 1st position selected It will send request to aracane server to get ProfitAndLossFO details
                    servicetype = "getProfitAndLossFO_Bajaj";
                }
                sendReportRequest();

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
                v.setTextColor(getResources().getColor(R.color.white));
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
        spinner_report.setAdapter(TypeAdapter);
        spinner_report.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            total_pnl = 00.00;
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
        total_pnl = 00.00;
        PandLReportRequest.sendRequest(AccountDetails.getUsername(getActivity()), servicetype, startDate, endDate, getActivity(), serviceResponseHandler);
    }


    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        refreshComplete();
        arrayList.clear();

        JSONResponse jsonResponse = (JSONResponse) response;
        PandLReportResponse ledgerReportResponse;

        try {

            ledgerReportResponse = (PandLReportResponse) jsonResponse.getResponse();


            for (int i = 0; i < ledgerReportResponse.getSaudaResponse().size(); i++) {

                arrayList.add(ledgerReportResponse.getSaudaResponse().get(i));

                double total = Double.parseDouble(ledgerReportResponse.getSaudaResponse().get(i).getCurrValue()) - Double.parseDouble(ledgerReportResponse.getSaudaResponse().get(i).getInvestValue());

                total_pnl = total_pnl + total;
                txt_ttlPNL.setText(String.format("%.2f", total_pnl));

                if (txt_ttlPNL.getText().toString().startsWith("-")) {
                    txt_ttlPNL.setTextColor(getResources().getColor(R.color.red));
                } else {
                    txt_ttlPNL.setTextColor(getResources().getColor(R.color.green_A700));
                }
            }

            if (arrayList.size() > 0) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                toggleErrorLayout(false);

            } else {

                swipeRefreshLayout.setVisibility(View.GONE);
                toggleErrorLayout(true);
            }

            hideProgress();


        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            toggleErrorLayout(true);

        }

        myRecyclerviewAdapter.notifyDataSetChanged();

    }


    private void toggleErrorLayout(boolean show) {
        if (show) {
            total_pnl = 00.00;
            txt_ttlPNL.setText(String.format("%.2f", total_pnl));
        }
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    private class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ViewHolder> {

        Context context;
        ArrayList<PLData> list;


        public MyRecyclerviewAdapter(Context context, ArrayList<PLData> list) {
            this.context = context;
            this.list = list;
            total_pnl = 00.00;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.row_pl_layout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            PLData plData = list.get(position);

            holder.txt_script.setText(plData.getScripName());
            holder.txt_quantity.setText(StringStuff.commaINRDecorator(plData.getQuantity()));
            holder.txt_ltp.setText(String.format("%.2f", Double.parseDouble(plData.getLtp())));


            holder.txt_price.setText(StringStuff.commaDecorator(plData.getAvgPrice()));

            double total = Double.parseDouble(plData.getCurrValue()) - Double.parseDouble(plData.getInvestValue());

            holder.txt_cPrice.setText(String.format("%.2f", total));
//            total_pnl = total_pnl + total;
//            txt_ttlPNL.setText(String.format("%.2f", total_pnl));
//
//            if (txt_ttlPNL.getText().toString().startsWith("-")) {
//                txt_ttlPNL.setTextColor(getResources().getColor(R.color.red));
//            } else {
//                txt_ttlPNL.setTextColor(getResources().getColor(R.color.green_A700));
//            }

            holder.txt_inv_value.setText(StringStuff.commaDecorator(plData.getInvestValue()));

            if (plData.getPerChange().equalsIgnoreCase("null")) {

                holder.txt_change.setText("0");

            } else {
                holder.txt_change.setText(String.format("%.2f", Double.parseDouble(plData.getPerChange())) + "%");
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            GreekTextView txt_script, txt_quantity, txt_ltp, txt_change, txt_price, txt_cPrice, txt_inv_value;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_script = itemView.findViewById(R.id.txt_script);
                txt_quantity = itemView.findViewById(R.id.txt_quantity);
                txt_ltp = itemView.findViewById(R.id.txt_ltp);
                txt_change = itemView.findViewById(R.id.txt_change);
                txt_price = itemView.findViewById(R.id.txt_price);
                txt_cPrice = itemView.findViewById(R.id.txt_cPrice);
                txt_inv_value = itemView.findViewById(R.id.txt_inv_Price);

            }
        }
    }


}

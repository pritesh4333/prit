package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.acumengroup.mobile.messaging.AlertAdapter;
import com.acumengroup.mobile.model.SaudaReportModel;
import com.acumengroup.mobile.model.SaudaReportRequest;
import com.acumengroup.mobile.model.SaudaReportResponse;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;


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


public class SaudaFragment extends GreekBaseFragment {

    private RecyclerView recyclerView;
    private Spinner spinner_segments, spinner_exchange;
    private String servicetype, exchangeType, segmentType;
    List<String> typeList;
    List<String> dataList;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private ServiceResponseHandler serviceResponseHandler;
    private DateFormat df;
    private int year, month, day;
    private Calendar calendar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyRecyclerviewAdapter myRecyclerviewAdapter;
    GreekTextView errorMsgLayout;
    private ArrayList<SaudaReportModel> saudaReportModelsList;
    private LinearLayout linear_top_layout;
    RelativeLayout parent_layout;
    private boolean isFirstTime = true;


    public SaudaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataList = new ArrayList<>();
        dataList.add("NSE");
        dataList.add("BSE");
        dataList.add("MCX");

        typeList = new ArrayList<>();
        typeList.add("EQ");
        typeList.add("FNO");
        typeList.add("CD");

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sauda, container, false);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        saudaReportModelsList = new ArrayList<>();

        spinner_exchange = view.findViewById(R.id.spin1);
        spinner_segments = view.findViewById(R.id.spin);

        linear_top_layout = view.findViewById(R.id.linear_top_layout);
        parent_layout = view.findViewById(R.id.parent_layout);
        errorMsgLayout = view.findViewById(R.id.errorHeader);



        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), typeList) {
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
        typeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner_segments.setAdapter(typeAdapter);


        ArrayAdapter<String> typeAdapters = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), dataList) {
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
        typeAdapters.setDropDownViewResource(R.layout.custom_spinner);
        spinner_exchange.setAdapter(typeAdapters);


        recyclerView = view.findViewById(R.id.recyclerView);
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));

        swipeRefreshLayout = view.findViewById(R.id.suadarefreshList);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // when swipe refresh it will again send request to server

                sendSaudaReportRequest();
            }
        });//        sendSaudaReportRequest();

        myRecyclerviewAdapter = new MyRecyclerviewAdapter(getMainActivity(), saudaReportModelsList);
        recyclerView.setAdapter(myRecyclerviewAdapter);

        sort_btn = view.findViewById(R.id.sort_btn);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            recyclerView.setBackgroundColor(getActivity().getColor(R.color.white));
            linear_top_layout.setBackgroundColor(getActivity().getColor(R.color.white));
            parent_layout.setBackgroundColor(getActivity().getColor(R.color.white));
            sort_btn.setImageResource(R.drawable.ic_filter);
            ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setTextColor(getMainActivity().getColor(R.color.black));

        }


        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup;
                Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);
                //Inflating the Popup using xml file


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
                            sendSaudaReportRequest();


                        } else if (item.getItemId() == R.id.two) {

                            Calendar aCalendar = Calendar.getInstance();
                            aCalendar.add(Calendar.MONTH, -1);
                            aCalendar.set(Calendar.DATE, 1);

                            Date firstDateOfPreviousMonth = aCalendar.getTime();
                            startDate = df.format(firstDateOfPreviousMonth);

                            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Date lastDateOfPreviousMonth = aCalendar.getTime();
                            endDate = df.format(lastDateOfPreviousMonth);
                            sendSaudaReportRequest();


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
                            sendSaudaReportRequest();


                        } else if (item.getItemId() == R.id.four) {

                            showCustomDialog();
                        }


                        return true;
                    }
                });

                popup.show();


            }
        });


        spinner_exchange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exchangeType = dataList.get(position);
                if (position == 2) {
                    spinner_segments.setSelection(2, true);
                    spinner_segments.setEnabled(false);
                } else {
                    spinner_segments.setEnabled(true);
                    if(!isFirstTime) {
                        sendSaudaReportRequest();
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_segments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                segmentType = typeList.get(position);

                if (position == 0) {
                //If 0th position selected It will send request to aracane server to get tradeSummaryEQ details
                    servicetype = "gettradeSummaryEQ_Bajaj";

                } else if (position == 1) {
                    //If 1st position selected It will send request to aracane server to get tradeSummaryFO details
                    servicetype = "gettradeSummaryFO_Bajaj";

                } else if (position == 2) {

                    servicetype = "gettradeSummaryFO_Bajaj";
                }


                sendSaudaReportRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


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

                sendSaudaReportRequest();
                alertDialog.cancel();
            }
        });


    }


    private void sendSaudaReportRequest() {
        isFirstTime = false;

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
        swipeRefreshLayout.setRefreshing(true);
          showProgress();
        SaudaReportRequest.sendRequest(AccountDetails.getUsername(getActivity()), startDate, endDate, servicetype, segmentType,
                exchangeType, getActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
         JSONResponse jsonResponse = (JSONResponse) response;
//        StrategyFinderResponse filterResponse = (StrategyFinderResponse) jsonResponse.getResponse();
        SaudaReportResponse reportResponse = null;
        saudaReportModelsList.clear();

        try {
            reportResponse = (SaudaReportResponse) jsonResponse.getResponse();


            for (int i = 0; i < reportResponse.getSaudaResponse().size(); i++) {
                SaudaReportModel saudaReportModel = new SaudaReportModel();
                saudaReportModel.setAction(reportResponse.getSaudaResponse().get(i).getAction());
                saudaReportModel.setAssetClass(reportResponse.getSaudaResponse().get(i).getAssetClass());
                saudaReportModel.setBrokapplied(reportResponse.getSaudaResponse().get(i).getBrokapplied());
                saudaReportModel.setQuantity(reportResponse.getSaudaResponse().get(i).getQuantity());
                saudaReportModel.setSauda_date(reportResponse.getSaudaResponse().get(i).getSauda_date());
                saudaReportModel.setScrip_name(reportResponse.getSaudaResponse().get(i).getScrip_name());
                saudaReportModel.setService_tax(reportResponse.getSaudaResponse().get(i).getService_tax());
                saudaReportModel.setTType(reportResponse.getSaudaResponse().get(i).getTType());
                saudaReportModel.setTxnValue(reportResponse.getSaudaResponse().get(i).getTxnValue());
                saudaReportModel.setNetRate(reportResponse.getSaudaResponse().get(i).getNetRate());

                saudaReportModelsList.add(saudaReportModel);
            }


            if (saudaReportModelsList.size() > 0) {
                errorMsgLayout.setVisibility(View.GONE);


                recyclerView.setAdapter(myRecyclerviewAdapter);
            } else {
                errorMsgLayout.setVisibility(View.VISIBLE);

            }



            refreshComplete();

            myRecyclerviewAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();

            refreshComplete();
            errorMsgLayout.setVisibility(View.VISIBLE);
        }


    }




    private class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ViewHolder> {
        Context context;
        ArrayList<SaudaReportModel> list;

        public MyRecyclerviewAdapter(Context context, ArrayList<SaudaReportModel> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.row_sauda_layout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            SaudaReportModel reportResponse = list.get(position);

            holder.txt_date.setText(reportResponse.getSauda_date());
            holder.txt_action.setText(reportResponse.getAction());
            holder.txt_qty.setText(reportResponse.getQuantity());
            holder.txn_value.setText(String.format("%.2f", Double.parseDouble(reportResponse.getTxnValue())));
            holder.txt_script.setText(reportResponse.getScrip_name());
            holder.txt_class.setText(reportResponse.getAssetClass());
            holder.txt_borker.setText(reportResponse.getBrokapplied());
            holder.charges.setText(String.format("%.2f", Double.parseDouble(reportResponse.getNetRate())));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txt_date, txt_action, txt_qty, txt_script, txn_value, txt_class, txt_borker, charges;
            LinearLayout tade_summary_data;
            public ViewHolder(View itemView) {
                super(itemView);

                tade_summary_data = itemView.findViewById(R.id.tade_summary_data);
                txt_date = itemView.findViewById(R.id.txt_date);
                txt_action = itemView.findViewById(R.id.txt_action);
                txt_qty = itemView.findViewById(R.id.qty);
                txn_value = itemView.findViewById(R.id.txn_value);
                txt_script = itemView.findViewById(R.id.txt_script);
                txt_class = itemView.findViewById(R.id.txt_class);
                txt_borker = itemView.findViewById(R.id.txt_borker);
                charges = itemView.findViewById(R.id.charges);
                if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                    txt_date.setTextColor(context.getColor(R.color.black));
                    txt_action.setTextColor(context.getColor(R.color.black));
                    txt_qty.setTextColor(context.getColor(R.color.black));
                    txn_value.setTextColor(context.getColor(R.color.black));
                    txt_script.setTextColor(context.getColor(R.color.black));
                    txt_class.setTextColor(context.getColor(R.color.black));
                    txt_borker.setTextColor(context.getColor(R.color.black));
                    charges.setTextColor(context.getColor(R.color.black));
                    tade_summary_data.setBackgroundColor(context.getColor(R.color.white));
                }

            }
        }
    }

}

package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FiiInvestmentModel;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiInvestmentRequest;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiInvestmentResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FiiDiiTabFragment extends GreekBaseFragment {

    private Spinner spinner_time, spinner_types;
    private FpiInvestmentResponse fpiInvestmentResponse;
    private List<FiiInvestmentModel> fpiInvestmentModelList = new ArrayList<>();
    private XAxis xAxis;
    public ServiceResponseHandler serviceResponseHandler;
    List<String> timesList, typeList;
    private LinearLayout fidi_bg,linear;

    //ArrayList<Float> colors1 = new ArrayList<Float>();
    private String startDate = "", endDate = "";
    private DateFormat df;
    private int selectedspinerId;
    private ListView fiidiiList;
    private CustomAdapterFiiDii commonAdapter;
    CustomAdapterFiiDii.Holder holder;
    float maxVal = 0;
    private String typeSpinnerData = "";

    ArrayList<FiiInvestmentModel> fiiInvestmentModels = new ArrayList<FiiInvestmentModel>();

    public FiiDiiTabFragment() {
        // Required empty public constructor
    }

    public static FiiDiiTabFragment newInstance() {
        return new FiiDiiTabFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timesList = new ArrayList<>();
        timesList.add("Daily");
        timesList.add("Monthly");
        timesList.add("Yearly");


        typeList = new ArrayList<>();
        typeList.add("FII Cash");
        typeList.add("DII Cash");
        typeList.add("FII Index Futures");
        typeList.add("FII Stock Futures");
        typeList.add("FII Index Options");
        typeList.add("FII Stock Options");


//        typeList = new ArrayList<>();
//        typeList.add("FII Cash");
//        typeList.add("DII Cash");
//        typeList.add("FII Future");
//        typeList.add("FII Options");

        commonAdapter = new CustomAdapterFiiDii(getMainActivity(), fiiInvestmentModels);


        //Index Futures  Index Options  Stock Futures Stock Options


    }

 /*@Override
    public void onFragmentResume() {
        super.onFragmentResume();

        sendFpiRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        sendFpiRequest();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sendFpiRequest();
    }*/

    /*    @Override
    public void onStart() {
        super.onStart();
        sendFpiRequest();
    }*/


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
            ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), timesList) {
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

            spinner_time.setAdapter(assetTypeAdapter);
//        spinner_time.setSelection(0);

            ArrayAdapter<String> TypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), typeList) {
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
            spinner_types.setAdapter(TypeAdapter);
            spinner_types.setSelection(0);

//        sendFpiRequest();
            spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Calendar calendar = Calendar.getInstance();
                    if (position == 0) {
                        //Today


                        calendar.add(Calendar.MONTH, 0);
                        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        Date c = Calendar.getInstance().getTime();
                        startDate = df.format(c);
                        endDate = df.format(c);

                        sendFpiRequest();

                    } else if (position == 1) {
                        //this week

                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                        startDate = df.format(calendar.getTime());
                        calendar.add(Calendar.DATE, 6);
                        endDate = df.format(calendar.getTime());


                        sendFpiRequest();

                    } else if (position == 2) {
                        //this Month

                        calendar.add(Calendar.MONTH, 0);
                        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                        Date monthFirstDay = calendar.getTime();
                        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        Date monthLastDay = calendar.getTime();

                        startDate = df.format(monthFirstDay);
                        Date c = Calendar.getInstance().getTime();
                        endDate = df.format(monthLastDay);

                        sendFpiRequest();

                    } else if (position == 3) {
                        // this year


                        int year = calendar.get(Calendar.YEAR);

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year - 1);
                        cal.set(Calendar.DAY_OF_YEAR, 1);
                        Date start = cal.getTime();
                        startDate = df.format(start);

                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, 11); // 11 = december
                        cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

                        Date end = cal.getTime();

                        endDate = df.format(end);

                        sendFpiRequest();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fii_dii_tab, container, false);
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        spinner_time = view.findViewById(R.id.spinner_time);
        spinner_types = view.findViewById(R.id.spinner_types);
        fiidiiList = view.findViewById(R.id.fiidii_List);
        fidi_bg = view.findViewById(R.id.fifi_bg);
        linear = view.findViewById(R.id.linear);
        fiidiiList.setAdapter(commonAdapter);

        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);


        spinner_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    typeSpinnerData = "investment";
                } else if (position == 1) {
                    typeSpinnerData = "investment";
                } else if (position == 2) {
                    typeSpinnerData = "derivative";
                } else if (position == 3) {
                    typeSpinnerData = "derivative";
                } else if (position == 4) {
                    typeSpinnerData = "derivative";
                } else if (position == 5) {
                    typeSpinnerData = "derivative";
                }
                sendFpiRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setTheme();


        return view;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            fidi_bg.setBackgroundColor(getResources().getColor(R.color.white));
            fiidiiList.setBackgroundColor(getResources().getColor(R.color.white));
            linear.setBackgroundColor(getResources().getColor(R.color.selectColor));
        }
    }

    private ArrayList getData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, -80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, -50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, -60f));
        return entries;
    }

    private void sendFpiRequest() {

        if (startDate.length() <= 0 || endDate.length() <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date monthFirstDay = calendar.getTime();
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthLastDay = calendar.getTime();

            Date c = Calendar.getInstance().getTime();
            startDate = df.format(c);
            endDate = df.format(c);
        }


        if (!typeSpinnerData.equalsIgnoreCase("")) {
            if (typeSpinnerData.equalsIgnoreCase("investment")) {
                FpiInvestmentRequest.sendRequest(getActivity(), spinner_time.getSelectedItem().toString(), typeSpinnerData, "100", serviceResponseHandler);

            } else {

                FpiInvestmentRequest.sendRequest(getActivity(), spinner_time.getSelectedItem().toString(), typeSpinnerData, "200", serviceResponseHandler);

            }
        }

    }

    @Override
    public void handleResponse(Object response) {


        JSONResponse jsonResponse = (JSONResponse) response;
        try {

            fpiInvestmentResponse = (FpiInvestmentResponse) jsonResponse.getResponse();

            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            fiiInvestmentModels.clear();
            fpiInvestmentModelList.clear();

            maxVal = 0;
            List<String> reportingdatelist = new ArrayList<>();
            for (int i = 0; i < fpiInvestmentResponse.getFpiInvestmentDetails().size(); i++) {
                if (!reportingdatelist.contains(fpiInvestmentResponse.getFpiInvestmentDetails().get(i).getReportingDate().toUpperCase())) {
                    reportingdatelist.add(fpiInvestmentResponse.getFpiInvestmentDetails().get(i).getReportingDate().toUpperCase());
                }
            }

            if (typeSpinnerData.equalsIgnoreCase("investment")) {

                for (int k = 0; k < reportingdatelist.size(); k++) {

                    FiiInvestmentModel fiiInvestmentModel = new FiiInvestmentModel();
                    fiiInvestmentModel.setReportingDate(reportingdatelist.get(k));

                    for (int j = 0; j < fpiInvestmentResponse.getFpiInvestmentDetails().size(); j++) {
                        if (reportingdatelist.get(k).equalsIgnoreCase(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getReportingDate())) {

                            if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category().toLowerCase().contains("fii")) {


                                fiiInvestmentModel.setInvestment_category(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category());
                                fiiInvestmentModel.setFii_investment_category(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category());
                                fiiInvestmentModel.setFii_gross_purchases(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_purchases());
                                fiiInvestmentModel.setFii_gross_sales(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_sales());
                                fiiInvestmentModel.setFii_net_investement_inr(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getNet_investement_inr());
                                double netValue = Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getNet_investement_inr());

                            } else if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category().toLowerCase().contains("dii")) {
                                fiiInvestmentModel.setInvestment_category(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category());
                                fiiInvestmentModel.setDii_investment_category(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category());
                                fiiInvestmentModel.setDii_gross_purchases(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_purchases());
                                fiiInvestmentModel.setDii_gross_sales(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_sales());
                                fiiInvestmentModel.setDii_net_investement_inr(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getNet_investement_inr());

                            }
                        }

                        fpiInvestmentModelList.add(fiiInvestmentModel);
                    }

                    for (int j = 0; j < fpiInvestmentModelList.size(); j++) {
                        if (spinner_types.getSelectedItem().toString().toLowerCase().contains("fii")) {

                            if (fpiInvestmentModelList.get(j).getFii_net_investement_inr() != null) {
                                if (maxVal < Math.abs(Float.parseFloat(fpiInvestmentModelList.get(j).getFii_net_investement_inr()))) {
                                    maxVal = Math.abs(Float.parseFloat(fpiInvestmentModelList.get(j).getFii_net_investement_inr()));
                                }
                            }
                        } else {
                            if (fpiInvestmentModelList.get(j).getDii_net_investement_inr() != null) {
                                if (maxVal < Math.abs(Float.parseFloat(fpiInvestmentModelList.get(j).getDii_net_investement_inr()))) {
                                    maxVal = Math.abs(Float.parseFloat(fpiInvestmentModelList.get(j).getDii_net_investement_inr()));
                                }
                            }
                        }
                    }

                    fiiInvestmentModels.add(fiiInvestmentModel);


                }
            } else if (typeSpinnerData.equalsIgnoreCase("derivative")) {

                for (int k = 0; k < reportingdatelist.size(); k++) {

                    FiiInvestmentModel fiiInvestmentModel = new FiiInvestmentModel();
                    fiiInvestmentModel.setReportingDate(reportingdatelist.get(k));

                    for (int j = 0; j < fpiInvestmentResponse.getFpiInvestmentDetails().size(); j++) {

                        if (reportingdatelist.get(k).equalsIgnoreCase(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getReportingDate())) {

                            if (spinner_types.getSelectedItem().toString().equalsIgnoreCase("FII Index Futures")) {

                                if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("Index Futures")) {


                                    fiiInvestmentModel.setBuy_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount());
                                    fiiInvestmentModel.setSell_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                                    fiiInvestmentModel.setNet_ammount(String.valueOf(Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount())));

                                }

                            } else if (spinner_types.getSelectedItem().toString().equalsIgnoreCase("FII Stock Futures")) {

                                if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("Stock Futures")) {

                                    fiiInvestmentModel.setBuy_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount());
                                    fiiInvestmentModel.setSell_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                                    fiiInvestmentModel.setNet_ammount(String.valueOf(Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount())));
                                }
                            } else if (spinner_types.getSelectedItem().toString().equalsIgnoreCase("FII Index Options")) {

                                if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("Index Options")) {

                                    fiiInvestmentModel.setBuy_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount());
                                    fiiInvestmentModel.setSell_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                                    fiiInvestmentModel.setNet_ammount(String.valueOf(Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount())));
                                }
                            } else if (spinner_types.getSelectedItem().toString().equalsIgnoreCase("FII Stock Options")) {

                                if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("Stock Options")) {

                                    fiiInvestmentModel.setBuy_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount());
                                    fiiInvestmentModel.setSell_ammount(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                                    fiiInvestmentModel.setNet_ammount(String.valueOf(Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getSell_ammount())));
                                }
                            }
                        }

                        fpiInvestmentModelList.add(fiiInvestmentModel);
                    }

                    for (int w = 0; w < fpiInvestmentModelList.size(); w++) {
                        if (spinner_types.getSelectedItem().toString().toLowerCase().contains("future")) {
                            if (fpiInvestmentModelList.get(w).getNet_ammount() != null) {
                                if (maxVal < Math.abs(Float.parseFloat(fpiInvestmentModelList.get(w).getNet_ammount()))) {
                                    maxVal = Math.abs(Float.parseFloat(fpiInvestmentModelList.get(w).getNet_ammount()));
                                }
                            }
                        } else {
                            if (fpiInvestmentModelList.get(w).getNet_ammount() != null) {
                                if (maxVal < Math.abs(Float.parseFloat(fpiInvestmentModelList.get(w).getNet_ammount()))) {
                                    maxVal = Math.abs(Float.parseFloat(fpiInvestmentModelList.get(w).getNet_ammount()));
                                }
                            }
                        }
                    }
                    fiiInvestmentModels.add(fiiInvestmentModel);

                }


            }

            commonAdapter.notifyDataSetChanged();

            ArrayList<String> labels = new ArrayList<>();
            labels.add("January");
            labels.add("February");
            labels.add("March");
            labels.add("April");
            labels.add("May");
            labels.add("June");

            hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
            commonAdapter.notifyDataSetChanged();
            hideProgress();
        }
    }

    public class CustomAdapterFiiDii extends BaseAdapter {
        private final Context mContext;
        ArrayList<FiiInvestmentModel> fiiInvestmentModelsArray = new ArrayList<>();

        public CustomAdapterFiiDii(Context context, ArrayList<FiiInvestmentModel> fiiInvestmentModels) {
            this.mContext = context;
            this.fiiInvestmentModelsArray = fiiInvestmentModels;
        }


        public void add(FiiInvestmentModel model) {
            fiiInvestmentModelsArray.add(model);
        }


        public void clear() {

            fiiInvestmentModelsArray.clear();
            notifyDataSetChanged();
            notifyDataSetInvalidated();
        }


        @Override
        public int getCount() {
            return fiiInvestmentModelsArray.size();
        }

        @Override
        public Object getItem(int position) {
            return fiiInvestmentModelsArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null) {

                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_fiidii_list, null);
                holder.expirtTxt = convertView.findViewById(R.id.expirtTxt);
                holder.investment_inr_txt = convertView.findViewById(R.id.investment_inr_txt);
                holder.horizontalChart = convertView.findViewById(R.id.chart1);
                holder.parent_layout = convertView.findViewById(R.id.parent_layout);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    holder.expirtTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    holder.investment_inr_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    holder.parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
                    holder.horizontalChart.setBackgroundColor(getResources().getColor(R.color.white));

                }else {
                    holder.horizontalChart.setBackgroundColor(getResources().getColor(R.color.bajaj_black));

                }
                convertView.setTag(holder);

                holder.horizontalChart.invalidate();
                holder.horizontalChart.setDrawBarShadow(false);
                holder.horizontalChart.getDescription().setEnabled(false);
                holder.horizontalChart.setScaleEnabled(false);
                holder.horizontalChart.setPinchZoom(false);
                holder.horizontalChart.setClipChildren(false);

                holder.horizontalChart.setFitBars(false);
                holder.horizontalChart.setDrawGridBackground(false);
                holder.horizontalChart.setDrawMarkers(false);
                holder.horizontalChart.setDrawBorders(false);
                holder.horizontalChart.getAxisLeft().setDrawGridLines(false);
                holder.horizontalChart.getXAxis().setDrawGridLines(false);

                holder.horizontalChart.getXAxis().setEnabled(false);
                holder.horizontalChart.getAxisLeft().setEnabled(false);
                holder.horizontalChart.getAxisRight().setEnabled(false);
                holder.horizontalChart.invalidate();

                xAxis = holder.horizontalChart.getXAxis();
                xAxis.setTextColor(getResources().getColor(R.color.white));
                xAxis.setGranularity(1);
                xAxis.setGranularityEnabled(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setAxisLineColor(Color.TRANSPARENT);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


                YAxis yl = holder.horizontalChart.getAxisLeft();
                yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                yl.setDrawGridLines(false);
                yl.setDrawZeroLine(true);

                YAxis yr = holder.horizontalChart.getAxisRight();
                yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                yr.setDrawGridLines(false);

                holder.horizontalChart.getAxisRight().setAxisMaximum(maxVal);
                holder.horizontalChart.getAxisRight().setAxisMinimum(0 - maxVal);
                holder.horizontalChart.getAxisLeft().setAxisMaximum(maxVal);
                holder.horizontalChart.getAxisLeft().setAxisMinimum(0 - maxVal);

            } else {
                holder = (CustomAdapterFiiDii.Holder) convertView.getTag();
            }


            FiiInvestmentModel fiiInvestmentModel = fiiInvestmentModelsArray.get(position);

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<Integer>();
            String xaxisdata = "";

            if (typeSpinnerData.equalsIgnoreCase("investment")) {
                if (spinner_types.getSelectedItem().toString().toLowerCase().contains("fii")) {

                    try {

                        xaxisdata = position + "f";
                        if (fiiInvestmentModel.getFii_net_investement_inr() != null) {
                            entries.add(new BarEntry(Float.valueOf(xaxisdata), Float.parseFloat(fiiInvestmentModel.getFii_net_investement_inr())));
                            if (Float.parseFloat(fiiInvestmentModel.getFii_net_investement_inr()) > 0) {
                                colors.add(ContextCompat.getColor(getContext(), R.color.green_900));
                            } else {
                                colors.add(ContextCompat.getColor(getContext(), R.color.red_900));
                            }
                        }
                        if(fiiInvestmentModel!=null) {
                            if(fiiInvestmentModel.getReportingDate()!=null) {
                                holder.expirtTxt.setText(fiiInvestmentModel.getReportingDate().replace("-", " "));
                            }
                            if(fiiInvestmentModel.getFii_net_investement_inr()!=null) {
                                holder.investment_inr_txt.setText(String.format("%.2f", Double.parseDouble(fiiInvestmentModel.getFii_net_investement_inr())));
                            }
                        }
//                    }

                    } catch (Exception e) {
                    }

                } else {
                    xaxisdata = position + "f";
                    if (fiiInvestmentModel.getDii_net_investement_inr() != null) {
                        entries.add(new BarEntry(Float.valueOf(xaxisdata), Float.parseFloat(fiiInvestmentModel.getDii_net_investement_inr())));
                        if (Float.parseFloat(fiiInvestmentModel.getDii_net_investement_inr()) > 0) {
                            colors.add(ContextCompat.getColor(getContext(), R.color.green_900));
                        } else {
                            colors.add(ContextCompat.getColor(getContext(), R.color.red_900));
                        }
                    }
                    if (fiiInvestmentModel.getReportingDate() != null) {
                        holder.expirtTxt.setText(fiiInvestmentModel.getReportingDate().replace("-", " "));
                    }
                    if (fiiInvestmentModel.getDii_net_investement_inr() != null) {
                        holder.investment_inr_txt.setText(String.format("%.2f", Double.parseDouble(fiiInvestmentModel.getDii_net_investement_inr())));
                    }
                }

            } else if (typeSpinnerData.equalsIgnoreCase("derivative")) {

                //Index Futures  Index Options  Stock Futures Stock Options

                if (spinner_types.getSelectedItem().toString().toLowerCase().contains("future")) {
                    xaxisdata = position + "f";
                    if (fiiInvestmentModel.getNet_ammount() != null) {
                        entries.add(new BarEntry(Float.valueOf(xaxisdata), Float.parseFloat(fiiInvestmentModel.getNet_ammount())));
                        if (Float.parseFloat(fiiInvestmentModel.getNet_ammount()) > 0) {
                            colors.add(ContextCompat.getColor(getContext(), R.color.green_900));
                        } else {
                            colors.add(ContextCompat.getColor(getContext(), R.color.red_900));
                        }
                    }
                    if (fiiInvestmentModel != null) {
                        if (fiiInvestmentModel.getReportingDate() != null) {
                            holder.expirtTxt.setText(fiiInvestmentModel.getReportingDate().replace("-", " "));
                        }
                        if (fiiInvestmentModel.getNet_ammount() != null) {
                            holder.investment_inr_txt.setText(String.format("%.2f", Double.parseDouble(fiiInvestmentModel.getNet_ammount())));
                        }
                    }

                } else {
                    xaxisdata = position + "f";
                    if (fiiInvestmentModel.getNet_ammount() != null) {
                        entries.add(new BarEntry(Float.valueOf(xaxisdata), Float.parseFloat(fiiInvestmentModel.getNet_ammount())));
                        if (Float.parseFloat(fiiInvestmentModel.getNet_ammount()) > 0) {
                            colors.add(ContextCompat.getColor(getContext(), R.color.green_900));
                        } else {
                            colors.add(ContextCompat.getColor(getContext(), R.color.red_900));
                        }
                    }
                    if (fiiInvestmentModel != null) {
                        if (fiiInvestmentModel.getReportingDate() != null) {
                            holder.expirtTxt.setText(fiiInvestmentModel.getReportingDate().replace("-", " "));
                        }
                        if (fiiInvestmentModel.getNet_ammount() != null) {
                            holder.investment_inr_txt.setText(String.format("%.2f", Double.parseDouble(fiiInvestmentModel.getNet_ammount())));
                        }
                    }
                    // }
                }
            }

            BarDataSet barDataSet = new BarDataSet(entries, "Inducesmile");
            barDataSet.setColors(colors);
            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(1.9f);
            barData.setValueTextSize(10f);
            barData.setValueTextColor(getResources().getColor(R.color.transparent));

            holder.horizontalChart.getAxisRight().setAxisMaximum(maxVal);
            holder.horizontalChart.getAxisRight().setAxisMinimum(0 - maxVal);
            holder.horizontalChart.getAxisLeft().setAxisMaximum(maxVal);
            holder.horizontalChart.getAxisLeft().setAxisMinimum(0 - maxVal);
            holder.horizontalChart.setData(barData);
            holder.horizontalChart.getLegend().setEnabled(false);
            holder.horizontalChart.getData().setHighlightEnabled(false);
            holder.horizontalChart.invalidate();
            return convertView;


        }

        public class Holder {
            GreekTextView expirtTxt, investment_inr_txt;
            HorizontalBarChart horizontalChart;
            LinearLayout parent_layout;

        }
    }

}
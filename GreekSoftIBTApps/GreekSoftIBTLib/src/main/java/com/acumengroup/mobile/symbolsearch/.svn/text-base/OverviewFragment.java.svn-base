package com.acumengroup.mobile.symbolsearch;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ramotion.foldingcell.FoldingCell;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class OverviewFragment extends GreekBaseFragment implements OnChartValueSelectedListener {
    private View ftPayInView;
    private GreekTextView schemeCategory, SchemeName, txt_titile_sip, txt_sip_details, txt_lip, CurrNAV, minPurchaseAmt, threeYearRet, maxExit, expenseRatio, txt_inestamount_horizon, benchMark, AssetSize, txt_inestamount;
    private ImageView img_icon_arrow, img_icon_up;
    private FoldingCell foldingCell;
    private CardView cardview_calculator;
    private boolean expand = false;
    private SwitchCompat SwitchCompat;
    private AppCompatSeekBar sb_inestamount, sb_inestamount_date;
    private String orderType = "SIP";
    private String schemCode;
    private int progress_year = 30;
    private int progress_amount = 5000;
    private JSONObject jsonObject;
    private LineChart mChart;
    private ArrayList<String> arrayList_date;
    private ArrayList<Float> arrayList_rs;
    private TabLayout bottomNavigation;
    private MaterialRatingBar ratingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ftPayInView = super.onCreateView(inflater, container, savedInstanceState);

        attachLayout(R.layout.fragment_overview);
        setupViews();

        return ftPayInView;
    }

    private void setData() {
        ArrayList<String> xVals = arrayList_date;

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Showing Fund's Performance");

        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(R.color.common_red_bg);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        //set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        set1.setDrawValues(false);
        set1.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
//        LineData data = new LineData(xVals, dataSets);

        // set data
//        mChart.setData(data);
        mChart.invalidate();

    }


    private ArrayList<Entry> setYAxisValues() {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < arrayList_date.size(); i++) {
            yVals.add(new Entry(arrayList_rs.get(i), i));

        }
        return yVals;
    }


    private void setupViews() {
        Bundle bundle = getArguments();
        schemCode = bundle.getString("schemCode");

        mChart = ftPayInView.findViewById(R.id.chart);
        //  mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);


        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        //mChart.setDescription("Demo Line Chart");
//        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawLabels(false);

        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChart.invalidate();

        getChartGraphRequest("3Y");


        foldingCell = ftPayInView.findViewById(R.id.folding_cell);
        cardview_calculator = ftPayInView.findViewById(R.id.cardview_calculator);
        SwitchCompat = ftPayInView.findViewById(R.id.switchcompat);
        txt_inestamount = ftPayInView.findViewById(R.id.txt_inestamount);
        txt_inestamount_horizon = ftPayInView.findViewById(R.id.txt_inestamount_horizon);
        sb_inestamount = ftPayInView.findViewById(R.id.sb_inestamount);
        sb_inestamount_date = ftPayInView.findViewById(R.id.sb_inestamount_horizon);

        img_icon_arrow = ftPayInView.findViewById(R.id.img_icon_arrow);
        img_icon_up = ftPayInView.findViewById(R.id.img_icon_up);
        schemeCategory = ftPayInView.findViewById(R.id.schemeCategory);
        txt_lip = ftPayInView.findViewById(R.id.txt_lip);
        SchemeName = ftPayInView.findViewById(R.id.SchemeName);
        CurrNAV = ftPayInView.findViewById(R.id.CurrNAV);
        minPurchaseAmt = ftPayInView.findViewById(R.id.minPurchaseAmt);
        threeYearRet = ftPayInView.findViewById(R.id.threeYearRet);
        txt_titile_sip = ftPayInView.findViewById(R.id.txt_titile_sip);
        txt_sip_details = ftPayInView.findViewById(R.id.txt_sip_details);

        maxExit = ftPayInView.findViewById(R.id.maxExit);
        expenseRatio = ftPayInView.findViewById(R.id.expenseRatio);
        benchMark = ftPayInView.findViewById(R.id.benchMark);
        AssetSize = ftPayInView.findViewById(R.id.AssetSize);
        ratingBar = ftPayInView.findViewById(R.id.ratingBar);

        bottomNavigation = ftPayInView.findViewById(R.id.tabs);
        ratingBar = ftPayInView.findViewById(R.id.ratingBar);


        bottomNavigation.addTab(bottomNavigation.newTab().setText("1W"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("1M"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("3M"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("1Y"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("3Y"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("5Y"));

        TabLayout.Tab tab = bottomNavigation.getTabAt(4);
        tab.select();

        bottomNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {

                    case 0:

                        getChartGraphRequest("1W");

                        break;
                    case 1:

                        getChartGraphRequest("1M");

                        break;

                    case 2:
                        getChartGraphRequest("3M");

                        break;
                    case 3:

                        getChartGraphRequest("1Y");

                        break;
                    case 4:

                        getChartGraphRequest("3Y");

                        break;
                    case 5:
                        getChartGraphRequest("5Y");

                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        SwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    orderType = "SIP";
                    if (jsonObject != null)
                        CalculationForSip(jsonObject);

                } else {
                    orderType = "Lump Sum";
                    if (jsonObject != null)
                        CalculationForLumpSum(jsonObject);

                }


            }
        });

        sb_inestamount.setMax(100000);
        sb_inestamount.setProgress(5000);
        txt_inestamount.setText("Rs. 5,000");


        sb_inestamount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (progress == 0) {
                    progress = 500;
                }

                int stepSize = 500;

                progress = (progress / stepSize) * stepSize;
                seekBar.setProgress(progress);

                if (progress >= 5000) {
                    stepSize = 5000;

                    progress = (progress / stepSize) * stepSize;
                    seekBar.setProgress(progress);
                }

                progress_amount = progress;

                DecimalFormat formatter = new DecimalFormat("#,##,###");

                txt_inestamount.setText("Rs. " + formatter.format(new BigDecimal(progress)));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                getSIPRequest(progress_year, orderType, progress_amount);

            }
        });

        sb_inestamount_date.setMax(60);

        sb_inestamount_date.setProgress(sb_inestamount_date.getMax() / 2);
        txt_inestamount_horizon.setText("2.6 Years");

        sb_inestamount_date.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (progress == 0) {
                    progress = 6;
                }

                int stepSize = 6;

                progress = (progress / stepSize) * stepSize;
                seekBar.setProgress(progress);

                int year = progress / 12;

                if (year < 1) {

                    txt_inestamount_horizon.setText(sb_inestamount_date.getProgress() + " Months");


                } else {

                    int years = progress / 12;
                    int remainingMonths = progress % 12;

                    txt_inestamount_horizon.setText(years + "." + remainingMonths + " Years");

                }

                progress_year = progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                getSIPRequest(progress_year, orderType, progress_amount);


            }
        });


        getSIPRequest(progress_year, orderType, progress_amount);


        img_icon_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!expand) {
                    foldingCell.toggle(true);

                    expand = true;

                } else {

                    foldingCell.toggle(false);
                    expand = false;

                }


            }
        });

        img_icon_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!expand) {
                    foldingCell.toggle(true);

                    expand = true;

                } else {

                    foldingCell.toggle(false);
                    expand = false;

                }


            }
        });

        showProgress();
        String sipFrequency = "getMFOverview?mf_schcode=" + schemCode;

        WSHandler.getRequest(getMainActivity(), sipFrequency, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");


                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);

                        if (jsonObject.has("starRating")) {
                            if (jsonObject.getString("starRating") != null) {

                                float rateing = Float.parseFloat(jsonObject.getString("starRating"));

                                if (rateing < 0) {
                                    ratingBar.setVisibility(View.INVISIBLE);

                                } else {

                                    ratingBar.setVisibility(View.VISIBLE);
                                    ratingBar.setRating(rateing);

                                }
                            } else {

                                ratingBar.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            ratingBar.setVisibility(View.INVISIBLE);
                        }
                        String scheme = jsonObject.getString("schemeCategory") + " | " + "Inception Date : " + jsonObject.getString("insDate");
                        if (scheme.toUpperCase().contains("ELSS")) {
                            txt_lip.setText("3 Yrs");
                        }
                        schemeCategory.setText(scheme);

                        SchemeName.setText(jsonObject.getString("SchemeName"));
                        minPurchaseAmt.setText("Min. Lump-Sum Investment Rs. " + jsonObject.getString("minPurchaseAmt"));

                        String str_CurrNAV = "Nav. Rs." + jsonObject.getString("CurrNAV") + " [" + jsonObject.getString("CurrNavDate") + "]";
                        CurrNAV.setText(str_CurrNAV);

                        String value = String.format("%.2f", Double.parseDouble(jsonObject.getString("threeYearRet")));

                        threeYearRet.setText(value);

                        maxExit.setText(jsonObject.getString("maxExit"));
                        expenseRatio.setText(jsonObject.getString("expenseRatio"));
                        benchMark.setText(jsonObject.getString("benchMark"));
                        AssetSize.setText(String.format("%.2f", Double.parseDouble(jsonObject.getString("AssetSize"))));


                    }
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


    public void getChartGraphRequest(String Year) {

        String accord_Token = Util.getPrefs(getContext()).getString("accord_Token", null);

        String url = "http://mf.accordwebservices.com/MF/GetMFNAVGraph?SchemeCode=" + schemCode + "&Period=" + Year + "&ChType=RAW&DateOption=&token=" + accord_Token;

        showProgress();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgress();
                arrayList_date = new ArrayList<>();
                arrayList_rs = new ArrayList<>();
                Log.e("DividendFragment", "getChartGraphRequest==response====>" + response);

                try {
                    jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Table");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject_zero = (JSONObject) jsonArray.get(0);

                        float NAVRS_zero = Float.parseFloat(jsonObject_zero.getString("NAVRS"));


                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                        String NAVDATE = jsonObject.getString("NAVDATE");


                        float NAVRS = Float.parseFloat(jsonObject.getString("NAVRS")) - NAVRS_zero;

                        arrayList_date.add(NAVDATE);

                        arrayList_rs.add(NAVRS);


                    }

                    // add data to Chart
                    setData();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideProgress();
                error.printStackTrace();
                Log.e("DividendFragment", "VolleyError===>" + error);
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        Volley.newRequestQueue(getMainActivity()).add(postRequest);


    }

    public void getSIPRequest(final int totalMonth, String orderTypes, final int amount) {

        Calendar today = Calendar.getInstance();
        Calendar previousYearToday = today;

        previousYearToday.add(Calendar.MONTH, -totalMonth);

        DateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");

        String FromDate = newDate.format(previousYearToday.getTime());

        String ToDate = newDate.format(Calendar.getInstance().getTime());

        String accord_Token = Util.getPrefs(getContext()).getString("accord_Token", null);

        String url = "http://mf.accordwebservices.com/MF/" +
                "Get_SIPcal_Data?SchemeCode=" + schemCode + "&FrmDate=" + FromDate + "&ToDate=" + ToDate + "&Amount=" + amount + "&token=" + accord_Token;

        showProgress();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgress();

                Log.e("DividendFragment", "response====>" + response);

                try {

                    jsonObject = new JSONObject(response);

                    if (orderType.equalsIgnoreCase("Lump Sum")) {

                        CalculationForLumpSum(jsonObject);

                    } else {

                        CalculationForSip(jsonObject);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideProgress();
                error.printStackTrace();
                Log.e("DividendFragment", "VolleyError===>" + error);
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        Volley.newRequestQueue(getMainActivity()).add(postRequest);

    }

    public void CalculationForSip(JSONObject jsonObject) {
        JSONArray jsonArray_table = null;
        try {
            jsonArray_table = jsonObject.getJSONArray("Table1");

            JSONObject jsonObject1 = (JSONObject) jsonArray_table.get(0);

            DecimalFormat formatter = new DecimalFormat("#,##,###");


            String TotalAmount = formatter.format(new BigDecimal(jsonObject1.getString("TotalAmount")));
            String TotalUnitPurchased = jsonObject1.getString("TotalUnitPurchased");
            String ValueOnInvestment = formatter.format(new BigDecimal(jsonObject1.getString("ValueOnInvestment")));
            String AsOnDate = jsonObject1.getString("AsOnDate");
            String CAGR = jsonObject1.getString("CAGR");

            String AbsoluteGain_LossPercentage = String.format("%.2f", Double.parseDouble(jsonObject1.getString("AbsoluteGain_LossPercentage")));

            String value = "SIP of " + txt_inestamount.getText().toString() + " for " + txt_inestamount_horizon.getText().toString() + " would have earned "
                    + AbsoluteGain_LossPercentage + "% and it's value would have been Rs." + ValueOnInvestment;


            txt_sip_details.setText(value);
            txt_titile_sip.setText(value);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void CalculationForLumpSum(JSONObject jsonObject) {


        JSONArray jsonArray_table = null;
        try {
            jsonArray_table = jsonObject.getJSONArray("Table");

            JSONObject jObject_old = (JSONObject) jsonArray_table.get(0);
            JSONObject jObject_new = (JSONObject) jsonArray_table.get(jsonArray_table.length() - 1);


            Double old_Price = Double.parseDouble(jObject_old.getString("Price"));
            Double old_Units = Double.parseDouble(jObject_old.getString("Units"));


            Double new_Price = Double.parseDouble(jObject_new.getString("Price"));
            Double new_Units = Double.parseDouble(jObject_new.getString("Units"));

            Double Gain_LossPercentage = ((new_Price - old_Price) * 100) / old_Price;

            Double invest_value = old_Units * new_Price;


            DecimalFormat formatter = new DecimalFormat("#,##,###");


            String ValueOnInvestment = formatter.format(new BigDecimal(invest_value));


            String AbsoluteGain_LossPercentage = String.format("%.2f", Gain_LossPercentage);

            String value = "Investment of " + txt_inestamount.getText().toString() + " for " + txt_inestamount_horizon.getText().toString() +
                    " would have earned "
                    + AbsoluteGain_LossPercentage + "% and it's value would have been Rs." + ValueOnInvestment;


            txt_sip_details.setText(value);
            txt_titile_sip.setText(value);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



   @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

}



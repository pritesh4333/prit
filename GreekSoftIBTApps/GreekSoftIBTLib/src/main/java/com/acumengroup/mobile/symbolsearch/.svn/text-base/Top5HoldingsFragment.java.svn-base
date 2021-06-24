package com.acumengroup.mobile.symbolsearch;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Top5HoldingsFragment extends GreekBaseFragment implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {
    private static ListView lv_top5;
    private LinearLayout ll_header_top5;
    private View ftPayInView;
    private RelativeLayout errorMsgLayout;
    private GreekTextView errorTextView;
    private Spinner allocationTypeSpinner;
    private List<String> allocationTypeList = new ArrayList<>();
    private ArrayList<String> asset_value_arr;
    private ArrayList<String> type_arr;
    private PieChart mChart;
    GreekTextView holdingtitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ftPayInView = super.onCreateView(inflater, container, savedInstanceState);
        attachLayout(R.layout.fragment_top_5_holdings);
        setupViews();
        setupViewsForAllocation();

        return ftPayInView;
    }

    private void setupViews() {
        Bundle bundle = getArguments();
        String schemCode = bundle.getString("schemCode");
        lv_top5 = ftPayInView.findViewById(R.id.lv_top5);
        ll_header_top5 = ftPayInView.findViewById(R.id.ll_header_top5);
        errorMsgLayout = ftPayInView.findViewById(R.id.showmsgLayout);
        errorTextView = ftPayInView.findViewById(R.id.errorHeader);
        holdingtitle = ftPayInView.findViewById(R.id.txt_label);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            holdingtitle.setTextColor(getResources().getColor(R.color.black));
        } else {
            holdingtitle.setTextColor(getResources().getColor(R.color.white));
        }

        allocationTypeSpinner = ftPayInView.findViewById(R.id.allocationTypeSpinner);

        allocationTypeList = Arrays.asList(getResources().getStringArray(R.array.allocationType));
        ArrayAdapter<String> allocationTypeSpinAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), allocationTypeList);
        allocationTypeSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        //allocationTypeSpinAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        allocationTypeSpinner.setAdapter(allocationTypeSpinAdapter);

        allocationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (allocationTypeList.get(i).equalsIgnoreCase("Market Cap")) {

                    lv_top5.setVisibility(View.VISIBLE);
                    mChart.setVisibility(View.GONE);

                } else if (allocationTypeList.get(i).equalsIgnoreCase("Sector")) {

                    lv_top5.setVisibility(View.GONE);
                    mChart.setVisibility(View.VISIBLE);


                } else if (allocationTypeList.get(i).equalsIgnoreCase("Company")) {

                    lv_top5.setVisibility(View.VISIBLE);
                    mChart.setVisibility(View.GONE);

                } else {

                    lv_top5.setVisibility(View.VISIBLE);
                    mChart.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                lv_top5.setVisibility(View.VISIBLE);
                mChart.setVisibility(View.GONE);
            }
        });



        showProgress();
        String sipFrequency = "getTop5Holding?mf_schcode=" + schemCode + "&top=5";
        WSHandler.getRequest(getMainActivity(), sipFrequency, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList<>();
                    LinkedHashMap<String, String> linkedHashMap;
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        linkedHashMap = new LinkedHashMap<>();
                        linkedHashMap.put("co_name", jsonObject.getString("companyName"));
                        linkedHashMap.put("perc_hold", jsonObject.getString("personHolding"));
                        linkedHashMap.put("mktValue", jsonObject.getString("marketValue"));
                        arrayList.add(linkedHashMap);
                    }

                    lv_top5.setAdapter(new Top5CustomAdapter(getMainActivity(), arrayList));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
                ll_header_top5.setVisibility(View.GONE);
                errorMsgLayout.setVisibility(View.VISIBLE);
                errorTextView.setText("No data available");
                errorTextView.setVisibility(View.VISIBLE);
                lv_top5.setVisibility(View.GONE);
            }
        });
    }

    private void setupViewsForAllocation() {
        Bundle bundle = getArguments();
        String schemCode = bundle.getString("schemCode");

        mChart = ftPayInView.findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
//        mChart.setDescription("");

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        // mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        mChart.setCenterText("ASSET\nALLOCATION");

        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        //String sipFrequency = "getAsset_Allocation?mf_schcode=" + schemCode;

        String sipFrequency = "getAsset_Allocation?mf_schcode=" + schemCode;
        WSHandler.getRequest(getMainActivity(), sipFrequency, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    asset_value_arr = new ArrayList<>();
                    type_arr = new ArrayList<>();
                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        String asset_value = jsonObject.getString("assetValue");
                        String type = jsonObject.getString("assetType");
                        asset_value_arr.add(asset_value);
                        type_arr.add(type);
                    }

                    setData(respCategory.length() - 1, 100);
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

    public class Top5CustomAdapter extends BaseAdapter {
        final ArrayList<LinkedHashMap<String, String>> strAsset;
        private LayoutInflater inflater = null;

        public Top5CustomAdapter(Context context, ArrayList<LinkedHashMap<String, String>> strAsset) {
            this.strAsset = strAsset;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return strAsset.size();
        }

        @Override
        public Object getItem(int position) {
            return strAsset.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = inflater.inflate(R.layout.row_performance_layout, null);
                holder.tv_co_name = convertView.findViewById(R.id.tv_co_name);

                holder.tv_no_shares = convertView.findViewById(R.id.txt_two_value);
                holder.tv_shares_title = convertView.findViewById(R.id.txt_two_title);

                holder.tv_perc_hold = convertView.findViewById(R.id.txt_one_value);
                holder.tv_hold_title = convertView.findViewById(R.id.txt_one_title);

                holder.layout_block1 = convertView.findViewById(R.id.block_one);
                holder.layout_block2 = convertView.findViewById(R.id.block_two);
                holder.layout_block3 = convertView.findViewById(R.id.block_three);

                holder.layout_block3.setVisibility(View.GONE);


                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.tv_co_name.setText(strAsset.get(position).get("co_name"));

            holder.tv_shares_title.setText("Market(Cr.)");
            holder.tv_no_shares.setText(strAsset.get(position).get("mktValue"));

            holder.tv_hold_title.setText("Hold(%)");
            holder.tv_perc_hold.setText(String.format("%.2f%%", Double.valueOf(strAsset.get(position).get("perc_hold"))));


            convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_1 : R.color.white);
            return convertView;
        }

        public class Holder {
            GreekTextView tv_co_name, tv_no_shares, tv_perc_hold, tv_hold_title, tv_shares_title;
            LinearLayout layout_block1, layout_block2, layout_block3;
        }
    }

    private void setData(int count, float range) {
        List<Entry> yVals1 = new ArrayList<>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry(Float.valueOf(asset_value_arr.get(i)), i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(type_arr.get(i));

//        PieDataSet dataSet = new PieDataSet(yVals1, "");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

//        dataSet.setColors(colors);

//        PieData data = new PieData(xVals, dataSet);
//        mChart.setDrawSliceText(false);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.BLACK);
//
//        //  data.setValueTypeface(tf);
//        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

//    @Override
//    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//        if (e == null)
//            return;
//        Log.i("VAL SELECTED",
//                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
//                        + ", DataSet index: " + dataSetIndex);
//    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


}

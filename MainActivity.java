package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LineChart mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);

        // no description text


        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);



        {   // // Chart Style // //


            // background color
            //mChart.setBackgroundColor(Color.WHITE);

            // disable description text


            // enable touch gestures
            mChart.setTouchEnabled(true);

            // set listeners

            //mChart.setDrawGridBackground(true);

            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(mChart);
            mChart.setMarker(mv);

            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
             mChart.setScaleXEnabled(true);
             mChart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            mChart.setPinchZoom(true);
        }

        // draw points over time
        mChart.animateX(1000);

//        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        // draw legend entries as lines
//        l.setForm(LegendForm.LINE);


        // limit lines are drawn behind data (and not on top)


        mChart.getAxisRight().setEnabled(false);
       // mChart.getAxisLeft().setLabelCount(5, true);
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getXAxis().setTextColor(Color.WHITE);
        mChart.setBackgroundColor(Color.BLACK);
        mChart.getLegend().setEnabled(false);

        float[] lineData_1y = { -5000, -3000, -4000,0};
        float[] lineData_0y = { 0,4000, 3000, 5000};


        float[] lineData_1x = { -5000, -3000, -4000,0};
        float[] lineData_0x = { 0,4000, 3000, 5000};


        float[][] all_line_dataY = {lineData_1y, lineData_0y};
        float[][] all_line_dataX = {lineData_1x, lineData_0x};


        int[] graph_linear_background = {R.drawable.fade_red, R.drawable.fade_green};
        int[] graph_linear_point_color = {Color.rgb(0, 0, 0), Color.rgb(0, 0, 0)};

        final List<ArrayList<Entry>> values_all = new ArrayList<ArrayList<Entry>>();
        int p=0;
        for (int k = 0; k < all_line_dataY.length; k++) {
            ArrayList<Entry> list = new ArrayList<>();
            for (int i = 0; i < all_line_dataY[k].length; i++) {
                list.add(new Entry(p, all_line_dataY[k][i]));
                p++;
            }
            p--;
            values_all.add(list);
        }



        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();


        final Map<String, LineDataSet> map = new HashMap<>();
        for(int i = 0; i < all_line_dataY.length; i++) {

            map.put("set0" + i, new LineDataSet(values_all.get(i),""));

            // set the line to be drawn like this "- - - - - -"
            map.get("set0" + i).setColor(Color.RED);
            map.get("set0" + i).setCircleColor(Color.RED);
            map.get("set0" + i).setMode(LineDataSet.Mode.CUBIC_BEZIER);
            map.get("set0" + i).setLineWidth(2f);
//            map.get("set0" + i).setDrawValues(! map.get("set0" + i).isDrawValuesEnabled());
//            map.get("set0" + i).setDrawValues(true);
//            map.get("set0" + i).setDrawIcons(!map.get("set0" + i).isDrawIconsEnabled());
            map.get("set0" + i).setCircleRadius(4f);
            map.get("set0" + i).setDrawCircleHole(false);
           // map.get("set0" + i).setDrawFilled(true);
           // map.get("set0" + i).setDrawIcons(false);
            // draw dashed line
            //map.get("set0" + i).enableDashedLine(10f, 5f, 0f);

            // black lines and points
            map.get("set0" + i).setColor(Color.WHITE);
            map.get("set0" + i).setCircleColor(Color.WHITE);

            // line thickness and point size
            map.get("set0" + i).setLineWidth(1f);
            map.get("set0" + i).setCircleRadius(3f);

            // draw points as solid circles
            map.get("set0" + i).setDrawCircleHole(false);

            // customize legend entry
           // map.get("set0" + i).setFormLineWidth(1f);
           // map.get("set0" + i).setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            map.get("set0" + i).setFormSize(15.f);

            // text size of values
            map.get("set0" + i).setValueTextSize(12f);

            // draw selection line as dashed
           // map.get("set0" + i).enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            map.get("set0" + i).setDrawFilled(true);
            map.get("set0" + i).setValueTextColor(Color.WHITE);



            dataSets.add(map.get("set0" + i));



            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, graph_linear_background[i]);
                map.get("set0" + i).setFillDrawable(drawable);
            } else {
                map.get("set0" + i).setFillColor(Color.TRANSPARENT);
            }
        }
        LineData line_data = new LineData(dataSets);

        // set data
        mChart.getAxisLeft().setStartAtZero(false);
        mChart.getAxisRight().setStartAtZero(false);

        mChart.setData(line_data);


//        float[] lineData_0 = {-9000,-8000,-4000,-2000,2000,4000,8000};
//
//        int[] graph_linear_background = {R.drawable.fade_green};
//        int[] graph_linear_point_color = {Color.rgb(0, 0, 0), Color.rgb(0, 0, 0)};
//
//        Map<String, LineDataSet> map = new HashMap<>();
//        Map<String,   List<Entry>> linedata = new HashMap<>();
//        List<ArrayList<Entry>> values_all = new ArrayList<ArrayList<Entry>>();
//        ArrayList<Entry> list = new ArrayList<>();
//        for (int k = 0; k < lineData_0.length; k++) {
//
//
//                list.add(new Entry(k, lineData_0[k]));
//
//            values_all.add(list);
//        }
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//
//        for(int i = 0; i < list.size(); i++) {
//
//            map.put("set0" + i, new LineDataSet(list,""));
//
//            // set the line to be drawn like this "- - - - - -"
//            map.get("set0" + i).setColor(Color.BLACK);
//            map.get("set0" + i).setCircleColor(Color.BLACK);
//            map.get("set0" + i).setMode(LineDataSet.Mode.CUBIC_BEZIER);
//            map.get("set0" + i).setLineWidth(3f);
//            map.get("set0" + i).setDrawValues(false);
//            map.get("set0" + i).setCircleRadius(4f);
//            map.get("set0" + i).setDrawCircleHole(false);
//            map.get("set0" + i).setDrawFilled(true);
//
//            if (list.get(i).getY()>0){
//                if (Utils.getSDKInt() >= 18) {
//                    // fill drawable only supported on api level 18 and above
//                    Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_green);
//
//                    map.get("set0" + i).setFillDrawable(drawable);
//
//                } else {
//                    map.get("set0" + i).setFillColor(Color.TRANSPARENT);
//                }
//            }else{
//                if (Utils.getSDKInt() >= 18) {
//                    // fill drawable only supported on api level 18 and above
//                    Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                    map.get("set0" + i).setFillDrawable(drawable);
//
//                } else {
//                    map.get("set0" + i).setFillColor(Color.TRANSPARENT);
//                }
//            }
//
//            dataSets.add(map.get("set0" + i));
//
//        }
//        // create a data object with the datasets
//        LineData line_data = new LineData(dataSets);
//
//        // set data
//        mChart.getAxisLeft().setStartAtZero(false);
//        mChart.getAxisRight().setStartAtZero(false);
//        mChart.setData(line_data);




//    private void setData(int count, float range) {
//
//        ArrayList<Entry> values = new ArrayList<>();
//
////        for (int i = 0; i < count; i++) {
////
////            float val = (float) (Math.random() * range) - 30;
////            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.ic_launcher_background)));
////        }
//
//        values.add(new Entry(0,-20f));
//        values.add(new Entry(40,-40f));
//        values.add(new Entry(60,-60f));
//        values.add(new Entry(80,-80f));
//        values.add(new Entry(100,100f));
//        values.add(new Entry(120,120f));
//        values.add(new Entry(140,140f));
//        values.add(new Entry(160,160f));
//
//        LineDataSet set1 = null;
//        ArrayList<ILineDataSet> ILinedataSets = new ArrayList<>();
//
//        for (int i=0;i<values.size();i++){
//            ArrayList<Entry> newentity = new ArrayList<>();
//            newentity.add(new Entry(values.get(i).getX(),values.get(i).getY()));
//
//            for (int ii=0;ii<newentity.size();ii++){
//                set1=new LineDataSet(newentity,"");
//
//                set1.setDrawIcons(false);
//
//                // draw dashed line
//                set1.enableDashedLine(10f, 5f, 0f);
//
//                // black lines and points
//               // set1.setColor(Color.BLACK);
//                set1.setCircleColor(Color.BLACK);
//
//                // line thickness and point size
//                set1.setLineWidth(1f);
//                set1.setCircleRadius(3f);
//
//                // draw points as solid circles
//                set1.setDrawCircleHole(false);
//
//                // customize legend entry
//                set1.setFormLineWidth(1f);
//                set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//                set1.setFormSize(15.f);
//
//                // text size of values
//                set1.setValueTextSize(9f);
//
//                // draw selection line as dashed
//                set1.enableDashedHighlightLine(10f, 5f, 0f);
//
//                // set the filled area
//
//
//
//            }
//            set1.setDrawFilled(true);
//            set1.setDrawValues(false);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return chart.getAxisLeft().getAxisMinimum();
//                }
//            });
//
//      //      if (newentity.get(i).getY()>=0) {
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_green);
//                set1.setFillDrawable(drawable);
//        //    }else{
//          //      Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//            //    set1.setFillDrawable(drawable);
//            //}
//            ILinedataSets.add(set1);
//
//        }
//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_green);
//        set1.setFillDrawable(drawable);
//
//        LineData data = new LineData(ILinedataSets);
//
//        chart.setGridBackgroundColor(Color.RED);
//        // set data
//        chart.setData(data);
//
//
//
//
//    }
    }
}

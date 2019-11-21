package in.co.vyaparienterprise.ui.activity.Dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HICrosshair;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.ui.adapter.DashboardAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;

public class MaterialChartActivity extends SohoActivity {
    private Context mContext;
    @BindView(R.id.options)
    ImageView optionss;
    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.quantity)
    Button quantity;
    @BindView(R.id.value)
    Button value;
    View bottomSheet;
    BottomSheetBehavior behavior;
    @BindView(R.id.icon_up)
    ImageView iconUp;
    @BindView(R.id.materialList)
    RecyclerView materialList;
    @BindView(R.id.invoice_detail_currency_symbol)
    TextView invoice_detail_currency_symbol;
    HIChartView chartView;
    HIOptions options;
    private String QuentityOrVlaue="1";
    @BindView(R.id.bottom_sheet)
    LinearLayout bottom_sheet;
    private boolean isExpendedBSL;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_material_chart);
        ButterKnife.bind(this);
        setToolbarConfig(getString(R.string.HighestGrossingMaterial), false);
        mContext = this;
        optionss.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        bottomSheet = findViewById(R.id.bottom_sheet);
            behavior = BottomSheetBehavior.from(bottomSheet);



        chartView = findViewById(R.id.hc);
        final ArrayList<KeyValue> TopMaterial = getIntent().getParcelableArrayListExtra("TopMaterial");
        final ArrayList<KeyValue> TopMaterialByValue = getIntent().getParcelableArrayListExtra("TopMaterialByValue");

        createChart(TopMaterial,TopMaterialByValue);
        quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               value.setBackgroundResource(R.drawable.rightcorner);
               quantity.setBackgroundResource(R.drawable.leftcornerblue);



                QuentityOrVlaue="1";
                invoice_detail_currency_symbol.setText("Quantity");
                createChart(TopMaterial,TopMaterialByValue);

            }
        });
        value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                value.setBackgroundResource(R.drawable.rightcornerblue);
                quantity.setBackgroundResource(R.drawable.leftcorner);



                QuentityOrVlaue="2";
                invoice_detail_currency_symbol.setText("Value");
                createChart(TopMaterial,TopMaterialByValue);

            }
        });

        iconUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (behavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    isExpendedBSL=true;
                    iconUp.setRotation(-180 * 1);
                }else{
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    isExpendedBSL=false;
                    iconUp.setRotation(180 * 0);
                }
            }
        });





    }

    private void createChart(ArrayList<KeyValue> TopMaterial, ArrayList<KeyValue> TopMaterialByValue){
        options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("column");
        options.setChart(chart);

        HICredits hc = new HICredits();
        hc.setEnabled(false);
        options.setCredits(hc);

        HIExporting HE = new HIExporting();
        HE.setEnabled(false);
        options.setExporting(HE);

        HITitle title = new HITitle();
        title.setText("");
        options.setTitle(title);

        final HIYAxis yAxis = new HIYAxis();
        yAxis.setTitle(new HITitle());
        if (QuentityOrVlaue.equalsIgnoreCase("1")) {
            yAxis.getTitle().setText("Quantity");
        }else{
            yAxis.getTitle().setText("Value");
        }

        options.setYAxis(new ArrayList<HIYAxis>() {{
            add(yAxis);
        }});


       final HIXAxis xAxis = new HIXAxis();
        String[] categoriesList = new String[] {""};
        xAxis.setCategories(new ArrayList<>(Arrays.asList(categoriesList)));
        xAxis.setCrosshair(new HICrosshair());
        options.setXAxis(new ArrayList<HIXAxis>(){{add(xAxis);}});




        HITooltip tooltip = new HITooltip();
        if (QuentityOrVlaue.equalsIgnoreCase("1")) {
            tooltip.setValuePrefix("");
        }else {
            tooltip.setValuePrefix("₹");
        }
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setPointPadding(0.2);
        plotOptions.getColumn().setBorderWidth(0);

        options.setPlotOptions(plotOptions);


        ArrayList<HISeries> series = new ArrayList<>();
        if (QuentityOrVlaue.equalsIgnoreCase("1")) {
            if (TopMaterial!=null) {
                for (int i = 0; i < TopMaterial.size(); i++) {
                    if (i == 10) {
                        break;
                    }
                    HISeries series1 = new HISeries();
                    ArrayList<Number> values = new ArrayList<>();
                    series1.setName(TopMaterial.get(i).getKey());
                    values.add(Double.parseDouble(TopMaterial.get(i).getValue()));
                    series1.setData(new ArrayList<>(values));
                    series.add(series1);
                }
            }
        }else{
            if (TopMaterialByValue!=null) {
                for (int i = 0; i < TopMaterialByValue.size(); i++) {
                    if (i == 10) {
                        break;
                    }
                    HISeries series1 = new HISeries();
                    ArrayList<Number> values = new ArrayList<>();
                    series1.setName(TopMaterialByValue.get(i).getKey());
                    values.add(0, format2DecAmountDouble(Double.parseDouble(TopMaterialByValue.get(i).getValue())));
                    series1.setData(new ArrayList<>(values));
                    series.add(series1);
                }
            }
        }

        options.setSeries(series);

        chartView.setOptions(options);
        chartView.reload();
        DashboardAdapter dashboardAdapter=null;
        if (QuentityOrVlaue.equalsIgnoreCase("1")) {
            if (TopMaterial!=null) {
                dashboardAdapter = new DashboardAdapter(mContext, TopMaterial, "TopMaterial", QuentityOrVlaue);
            }
        }else{
            if (TopMaterialByValue!=null) {
                dashboardAdapter = new DashboardAdapter(mContext, TopMaterialByValue, "TopMaterial", QuentityOrVlaue);
            }
        }
        materialList.setAdapter(dashboardAdapter);
        materialList.setItemAnimator(new DefaultItemAnimator());
        materialList.setHasFixedSize(true);
        materialList.setNestedScrollingEnabled(false);
        materialList.setLayoutManager(new LinearLayoutManager(this));

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change

//                if (newState==BottomSheetBehavior.STATE_EXPANDED){
//                    iconUp.setRotation(-180 * 1);
//                }else{
//                    iconUp.setRotation(180 * 0);
//                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
                if ( isExpendedBSL){
                    iconUp.setRotation(-180 * slideOffset);
                }else{
                    iconUp.setRotation(180 * slideOffset);
                }
            }
        });





    }
    public static Double format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return Double.valueOf(formattedAmount);
    }
}

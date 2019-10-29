package in.co.vyapari.ui.activity.Dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.highsoft.highcharts.common.hichartsclasses.HIBar;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyapari.R;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.ui.adapter.DashboardAdapter;
import in.co.vyapari.ui.base.SohoActivity;

public class CustomerChartActivity extends SohoActivity {
    private Context mContext;
    @BindView(R.id.options)
    ImageView optionss;
    @BindView(R.id.edit)
    ImageView edit;
    HIChartView hc_customer;
    HIOptions optionsCustomer;
    View bottomSheet;
    BottomSheetBehavior behavior;
    @BindView(R.id.icon_up)
    ImageView iconUp;
    @BindView(R.id.materialList)
    RecyclerView materialList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chart);
        ButterKnife.bind(this);
        setToolbarConfig(getString(R.string.KeyCustomers), false);
        mContext = this;
        optionss.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        hc_customer = findViewById(R.id.hc_customer);
        ArrayList<KeyValue> TopCustomers = getIntent().getParcelableArrayListExtra("TopCustomers");
        optionsCustomer = new HIOptions();
        HIChart chart = new HIChart();
        chart.setType("bar");
        optionsCustomer.setChart(chart);

        HICredits hc = new HICredits();
        hc.setEnabled(false);
        optionsCustomer.setCredits(hc);

        HIExporting HE = new HIExporting();
        HE.setEnabled(false);
        optionsCustomer.setExporting(HE);

        HITitle title = new HITitle();
        title.setText("");
        optionsCustomer.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("");
        optionsCustomer.setSubtitle(subtitle);

        final HIXAxis xaxis = new HIXAxis();
        String[] categories = new String[]{""};
        xaxis.setCategories(new ArrayList<>(Arrays.asList(categories)));
        optionsCustomer.setXAxis(new ArrayList<HIXAxis>() {{
            add(xaxis);
        }});

        final HIYAxis yAxis = new HIYAxis();
        yAxis.setMin(0);
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText("Customers");
        optionsCustomer.setYAxis(new ArrayList<HIYAxis>() {{
            add(yAxis);
        }});

        HITooltip tooltip = new HITooltip();
        tooltip.setValuePrefix("₹");
        optionsCustomer.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setBar(new HIBar());
        plotOptions.getBar().setDataLabels(new HIDataLabels());
        plotOptions.getBar().getDataLabels().setEnabled(true);
        optionsCustomer.setPlotOptions(plotOptions);


        HICredits credits = new HICredits();
        credits.setEnabled(false);
        optionsCustomer.setCredits(credits);

        ArrayList<HISeries> series = new ArrayList<>();
        if (TopCustomers != null) {
            for (int i = 0; i < TopCustomers.size(); i++) {
                if (i == 10) {
                    break;
                }
                HISeries series1 = new HISeries();
                ArrayList<Number> values = new ArrayList<>();
                series1.setName(TopCustomers.get(i).getKey());
                values.add(Double.parseDouble(TopCustomers.get(i).getValue()));
                series1.setData(new ArrayList<>(values));
                series.add(series1);
            }
        }
        optionsCustomer.setSeries(series);

        hc_customer.setOptions(optionsCustomer);
        DashboardAdapter dashboardAdapter = null;
        if (TopCustomers != null) {
            dashboardAdapter = new DashboardAdapter(mContext, TopCustomers, "TopMaterial", "2");
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
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    iconUp.setRotation(-180 * 1);

                } else {

                    iconUp.setRotation(180 * 0);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events

            }
        });
        iconUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    iconUp.setRotation(-180 * 1);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    iconUp.setRotation(180 * 0);
                }
            }
        });

    }
}

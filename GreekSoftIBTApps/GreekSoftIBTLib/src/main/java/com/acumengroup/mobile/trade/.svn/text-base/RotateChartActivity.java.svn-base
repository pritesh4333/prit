package com.acumengroup.mobile.trade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.acumengroup.greekmain.chart.BarChart;
import com.acumengroup.greekmain.chart.NotifyChartData;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.draw.ChartConstants.XLabelDateFormat;
import com.acumengroup.greekmain.chart.draw.DrawChart;
import com.acumengroup.greekmain.chart.draw.GreekChartListener;
import com.acumengroup.greekmain.chart.draw.GreekChartView;
import com.acumengroup.greekmain.chart.json.Indicator;
import com.acumengroup.greekmain.chart.scroll.ChartInterceptScrollView;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.intradaychart.DailyChartRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseActivity.USER;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.chartiqscreen.MainActivity;
import com.acumengroup.mobile.model.chartModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.GreekDialog.Action;
import com.acumengroup.ui.GreekDialog.DialogListener;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.quickaction.QuickActionAdapter;
import com.acumengroup.ui.quickaction.QuickActionItemDetails;
import com.acumengroup.ui.quickaction.QuickActionView;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.github.mikephil.charting.components.Legend;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import de.greenrobot.event.EventBus;

import static com.acumengroup.greekmain.core.constants.ServiceConstants.CHART_SVC_GROUP;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.INTRADAY_CHART_SVC_NAME;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.MARKETS_SVC_GROUP;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.SINGLEQUOTE_SVC_NAME;

public class RotateChartActivity extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GreekUIServiceHandler, GreekConstants {
    public static final String SCRIP_NAME = "Scrip";
    public static final String TOKEN = "Token";
    public static final String LOT_QUANTITY = "Lots";
    public static final String MULTIPLIER = "Multiplier";
    public static final String TICK_SIZE = "TickSize";
    public static final String TRADE_SYMBOL = "TradeSymbol";
    public static final String ASSET_TYPE = "AssetType";
    private ServiceResponseListener serviceResponseHandler;
    private boolean isRequestCall = false;
    private boolean isEventRequestCall = false;
    private boolean isFirstTime = false;
    Context ctx;
    TextView nodataTxt;

    MarketsSingleScripResponse quoteResponse;
    ArrayList<String> sym = new ArrayList<>();
    private final int[] chartTypeDrawable = {R.drawable.ic_chart_black_24dp, R.drawable.area_chart_ic_30, R.drawable.ohlc_chart_ic_30, R.drawable.candle_chart_ic_30};
    private final String[] chartDuration = {"Intraday", "Daily"};

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refresh();
        }
    };
    private int selectedChartType = 3;
    private String selectedChartduration = "1";
    private LinearLayout chartsLayout, mpButton,parentlayout;
    private ScrollView scrollbtns;
    private String LastStoredResponseDate = "", LastCurrentDate = "", LastInterval;
    private int unSccussfulAttempt = 0;
    private boolean isDailybasis = false;
    private int checkedId = R.id.btn1M;
    private Spinner resolutionSpinner;
    private boolean isLoadmore = false;
    private ImageButton types, crosshair, crosshair_fullScreen;
    private ImageButton charttypes, candletype, ohcltype, searchtype, deletetype;
    private Button indicatortype, drawtype;
    private final View.OnClickListener typesClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            setupQuickAction(v);
        }
    };
    private RadioGroup resolutionBar;
    private RadioButton btn1M;
    private RadioButton btn5M;
    private RadioButton btn10M;
    private RadioButton btn15M;
    private RadioButton btnM;
    private RadioButton btn45M;
    private RadioButton btn20M;
    private RadioButton btn1W;
    private RadioButton btnM1;
    private RadioButton btnY1;
    private SwipeRefreshLayout swipeRefresh;
    private ChartInterceptScrollView scrollChart;
    LinearLayout roverlay, roverlaybtn;
    LinearLayout chartOuterlayout;
    static Hashtable chartData;
    private double[] close, open, low, high, volume, close1, open1, low1, high1, volume1;
    private String[] date, date1;
    private int size;
    private int size1;
    private final AdapterView.OnItemSelectedListener resolutionSelection = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {

                LastStoredResponseDate = "";
                size = 0;
                close = new double[size];
                open = new double[size];
                low = new double[size];
                high = new double[size];
                volume = new double[size];
                date = new String[size];

                try {
                    SimpleDateFormat expirydateformat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    Calendar c = Calendar.getInstance();
                    LastCurrentDate = expirydateformat.format(c.getTime());

                } catch (Exception e) {
                    Log.e("ChartsFragment", "Exceptions===================>>>>");
                }


                isDailybasis = false;

                setBtnTabText();
                changeIntraDayInterval("1");

            }
            if (position == 1) {

                LastStoredResponseDate = "";
                size = 0;
                close = new double[size];
                open = new double[size];
                low = new double[size];
                high = new double[size];
                volume = new double[size];
                date = new String[size];

                try {

                    SimpleDateFormat expirydateformat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    Calendar c = Calendar.getInstance();
                    LastCurrentDate = expirydateformat.format(c.getTime());

                } catch (Exception e) {
                    Log.e("ChartsFragment", "Exceptions===================>>>>");
                }


                isDailybasis = true;
                setBtnTabTextForDaily();
                changeIntraDayInterval("1D");


            }
            chartsLayout.removeAllViews();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private String scripName;
    private String token;
    private GreekTextView lastTextValue, symbolTextValue, changeTextValue, hightext, lowtext, closetext;
    private ChartSettings chartSettings = new ChartSettings();
    private DrawChart chartDisplay = null;
    public long lasttime = 0;
    private ArrayList<Double> closeList = new ArrayList<Double>();


    private final View.OnClickListener crossHairClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (chartDisplay == null) return;

            if (chartSettings.isShowCrossHairs()) {
                chartSettings.setShowCrossHairs(false);
                AccountDetails.setIsOHLCSHOW(true);
                chartSettings.setDoubleTapOverride(true);

            } else {
                chartSettings.setShowCrossHairs(true);
                AccountDetails.setIsOHLCSHOW(false);
                crosshair.setImageResource(R.drawable.pointer_inactive_ic_30);
            }


            chartDisplay.showCrossHairs(chartSettings);


        }
    };
    private Intent args;
    private StreamingController streamController;
    private JSONObject responseData;

    private void refresh() {
        sendQuotesRequest(args.getExtras().getString(TOKEN), getAssetTypeFromToken(args.getExtras().getString(TOKEN)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AccountDetails.setIsbackmainActivity(true);
            finish();
        }
        return super.onKeyDown(keyCode, event);


    }

    @Override
    public void onBackPressed() {
        AccountDetails.setIsbackmainActivity(true);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rotate_chart);
        ctx = this;
        chartDisplay = new DrawChart(this);
        streamController = new StreamingController();
        args = getIntent();
        close = new double[size];
        open = new double[size];
        low = new double[size];
        high = new double[size];
        volume = new double[size];
        date = new String[size];
        EventBus.getDefault().register(this);


        serviceResponseHandler = new ServiceResponseHandler(this, this);
        sendQuotesRequest(args.getExtras().getString(TOKEN), getAssetTypeFromToken(args.getExtras().getString(TOKEN)));
        setupViews();



    }

    private void setupViews() {

        swipeRefresh = findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        nodataTxt = findViewById(R.id.empty_view);
        nodataTxt.setVisibility(View.GONE);

        resolutionSpinner = findViewById(R.id.resolutionSpinner);
        ArrayAdapter<String> resolutionAdapter = new ArrayAdapter<>(this, AccountDetails.getRowSpinnerSimple(), chartDuration);
        resolutionAdapter.setDropDownViewResource(R.layout.custom_spinner);
        resolutionSpinner.setAdapter(resolutionAdapter);
        resolutionSpinner.setOnItemSelectedListener(resolutionSelection);

        types = findViewById(R.id.typesBtn);
        types.setOnClickListener(typesClicked);
        crosshair = findViewById(R.id.crossHairBtn);
        crosshair_fullScreen = findViewById(R.id.crossHairBtn_full);
        crosshair.setOnClickListener(crossHairClicked);
        crosshair_fullScreen.setOnClickListener(crossHairClicked);

        chartsLayout = findViewById(R.id.chartLayout);
        parentlayout = findViewById(R.id.parent_layout);
        lastTextValue = findViewById(R.id.lastText);
        symbolTextValue = findViewById(R.id.symbolText);
        hightext = findViewById(R.id.highText);
        lowtext = findViewById(R.id.lowText);
        closetext = findViewById(R.id.closeText);
        changeTextValue = findViewById(R.id.changeTextchart);
        mpButton = findViewById(R.id.btn_map);
        scrollbtns = findViewById(R.id.radio_layout);



        roverlay = findViewById(R.id.rlOverlay);
        roverlaybtn = findViewById(R.id.rlOverlaybtn);
        chartOuterlayout = findViewById(R.id.chatlayout);

        btn1M = findViewById(R.id.btn1M);
        btn5M = findViewById(R.id.btn5M);
        btn10M = findViewById(R.id.btn10M);//30M
        btnM = findViewById(R.id.btnM);//30M
        btn45M = findViewById(R.id.btn45M);//30M
        btn15M = findViewById(R.id.btn15M);//1H
        btn20M = findViewById(R.id.btn20M);//1D
        btn1W = findViewById(R.id.btn1H);//1W
        btnM1 = findViewById(R.id.btn30D);//M1
        btnY1 = findViewById(R.id.btn1y);//M1
        resolutionBar = findViewById(R.id.resolutionBar);
//        checkedId = R.id.btn1M;.



        resolutionBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedIds) {

                try {

                    SimpleDateFormat expirydateformat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    Calendar c = Calendar.getInstance();
                    LastCurrentDate = expirydateformat.format(c.getTime());

                } catch (Exception e) {
                    Log.e("ChartsFragment", "Exceptions===================>>>>");

                }

                LastStoredResponseDate = "";
                checkedId = checkedIds;
                token = args.getExtras().getString(TOKEN);
                if (checkedIds == R.id.btn1M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];
                        isDailybasis = false;

                        if (!isRequestCall) {
                            isRequestCall = true;
                            selectedChartduration = "1";
                            changeIntraDayInterval("1");

                        }
                    }
                } else if (checkedIds == R.id.btn5M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];
                        isDailybasis = false;
                        changeIntraDayInterval("5");
//                            }
                    }
                } else if (checkedIds == R.id.btnM) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];
                        isDailybasis = false;
                        changeIntraDayInterval("15");
//                            }
                    }
                } else if (checkedIds == R.id.btn45M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];
                        isDailybasis = false;
                        changeIntraDayInterval("45");
//                            }
                    }
                } else if (checkedIds == R.id.btn10M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];
                        isDailybasis = false;

                        changeIntraDayInterval("30");
//                            }
                    }
                } else if (checkedIds == R.id.btn15M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];
                        isDailybasis = false;

                        changeIntraDayInterval("1");
                    }
                } else if (checkedIds == R.id.btn20M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];
                        isDailybasis = true;
                        changeIntraDayInterval("day");
                    }
                } else if (checkedIds == R.id.btn1H) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];

//                            if (btn1M.getText().toString().toLowerCase().equalsIgnoreCase("1W")) {
                        isDailybasis = true;

                        changeIntraDayInterval("week");

                    }
                } else if (checkedIds == R.id.btn30D) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];

//                            if (isDailybasis) {c
                        isDailybasis = true;

                        changeIntraDayInterval("month");

                    }
                } else if (checkedIds == R.id.btn1y) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];

//                            if (isDailybasis) {c
                        isDailybasis = true;

                        changeIntraDayInterval("year");


                    }
                }
            }
        });

        resolutionBar.check(R.id.btn1M);
        GreekButton buyBtn = findViewById(R.id.buy_btn);
        GreekButton sellBtn = findViewById(R.id.sell_btn);

        buyBtn.setVisibility(View.VISIBLE);
        sellBtn.setVisibility(View.VISIBLE);



        scripName = args.getExtras().getString(SCRIP_NAME);
        String description = args.getExtras().getString("description");

        AccountDetails.globalArgChartFrag = args.getExtras();

        setBtnTabText();
//uncomment for chart rotation
        if (args.getExtras().containsKey("from")) {
            if ((getRequestedOrientation()) == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

        charttypes = findViewById(R.id.btn_chart);
        ohcltype = findViewById(R.id.btn_ohcl_chart);
        candletype = findViewById(R.id.btn_candle_chart);
        searchtype = findViewById(R.id.btn_search);//area chart
        indicatortype = findViewById(R.id.btn_indicator);
        drawtype = findViewById(R.id.btn_draw);
        deletetype = findViewById(R.id.btn_delete);

        charttypes.setOnClickListener(selectedtype);
        ohcltype.setOnClickListener(selectedtype);
        candletype.setOnClickListener(selectedtype);
        searchtype.setOnClickListener(selectedtype);
        drawtype.setOnClickListener(selectedtype);
        deletetype.setOnClickListener(crossHairClicked);


        if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
            lastTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            symbolTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            changeTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            parentlayout.setBackground(getDrawable(R.drawable.bg_drawable_white));
            btn1M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn5M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn10M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn15M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn45M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn1W.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btnM1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btnY1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn20M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btnM.setTextColor(getResources().getColor(AccountDetails.textColorDropdown)); btn1M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            btn1M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn5M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn10M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn15M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn45M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn1W.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btnM1.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btnY1.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn20M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btnM.setBackground(getDrawable(R.drawable.chart_radio__white_style));

            searchtype.setImageResource(R.drawable.area_chart_ic_30);
            candletype.setImageResource(R.drawable.candle_chart_ic_30);
            charttypes.setImageResource(R.drawable.ic_chart_black_24dp);
            ohcltype.setImageResource(R.drawable.ohlc_chart_ic_30);
            crosshair.setImageResource(R.drawable.pointer_active_ic_30);
        }

    }

    private final View.OnClickListener selectedtype = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_chart) {
                selectedChartType = 0;
                chartSettings.setShowVolumeChart(true);
                LastStoredResponseDate = "";
                showChart(0);

            }
            if (v.getId() == R.id.btn_ohcl_chart) {
                selectedChartType = 2;
                chartSettings.setShowVolumeChart(true);
                LastStoredResponseDate = "";
                showChart(2);
            }
            if (v.getId() == R.id.btn_candle_chart) {
                selectedChartType = 3;
                showChart(3);
            }
            if (v.getId() == R.id.btn_indicator) {
                selectedChartType = 3;
                showChart(3);
            }

            if (v.getId() == R.id.btn_search) {
                selectedChartType = 1;
                chartSettings.setShowVolumeChart(true);
                LastStoredResponseDate = "";
                showChart(1);
            }
        }
    };

    private void onRefreshComplete() {
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void setupQuickAction(View v) {
        Vector<QuickActionItemDetails> actionItemDetails = new Vector<>();
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.ic_chart_white_24dp, true, R.color.red));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.area_chart_ic_w_30, true, R.color.red));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.ohlc_chart_ic_w_30, true, R.color.red));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.candle_chart_ic_w_30, true, R.color.red));

        QuickActionAdapter quickActionAdapter = new QuickActionAdapter(this, actionItemDetails);

        quickActionAdapter.setItemMargin(3, 3, 3, 3);
        quickActionAdapter.setItemLayoutParams(-1, -1);

        QuickActionView quickaction = new QuickActionView(v, quickActionAdapter);
        quickaction.setNumColumns(1);
        quickActionAdapter.setSelection(selectedChartType, R.drawable.trade_drawable);

        quickaction.setOnClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                quickActionClick(dialog, which);
            }
        });

        quickaction.show();
    }

    private void quickActionClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        types.setImageResource(chartTypeDrawable[which]);
        showChart(which);
        selectedChartType = which;
    }

    private void setBtnTabText() {
        if (Util.getPrefs(this).getBoolean("GREEK_C11_TOGGLE", true)) {
            btn1M.setText("1M");
            btn1M.setVisibility(View.VISIBLE);
        } else btn1M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C12_TOGGLE", true)) {
            btn5M.setText("5M");
            btn5M.setVisibility(View.VISIBLE);
        } else btn5M.setVisibility(View.GONE);
        if (Util.getPrefs(this).getBoolean("GREEK_C12_TOGGLE", true)) {
            btnM.setText("15M");
            btnM.setVisibility(View.VISIBLE);
        } else btnM.setVisibility(View.GONE);
        if (Util.getPrefs(this).getBoolean("GREEK_C12_TOGGLE", true)) {
            btn45M.setText("45M");
            btn45M.setVisibility(View.VISIBLE);
        } else btn45M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C13_TOGGLE", true)) {
            btn10M.setText("30M");
            btn10M.setVisibility(View.VISIBLE);
        } else btn10M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C14_TOGGLE", true)) {
            btn15M.setText("1H");
            btn15M.setVisibility(View.VISIBLE);
        } else btn15M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C15_TOGGLE", true)) {
            btn20M.setText("1D");
            btn20M.setVisibility(View.VISIBLE);
        } else btn20M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C15_TOGGLE", true)) {
            btn1W.setText("1W");
            btn1W.setVisibility(View.VISIBLE);
        } else btn20M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C15_TOGGLE", true)) {
            btnM1.setText("M1");
            btnM1.setVisibility(View.VISIBLE);
        } else btnM1.setVisibility(View.GONE);
    }


    private void setBtnTabTextForDaily() {
        if (Util.getPrefs(this).getBoolean("GREEK_C11_TOGGLE", true)) {
            btn1M.setText("1 Day");
            btn1M.setVisibility(View.VISIBLE);
        } else btn1M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C12_TOGGLE", true)) {
            btn5M.setText("1 Week");
            btn5M.setVisibility(View.VISIBLE);
        } else btn5M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C13_TOGGLE", true)) {
            btn10M.setText("1 Month");
            btn10M.setVisibility(View.VISIBLE);
        } else btn10M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C14_TOGGLE", true)) {
            btn15M.setText("1 Year");
            btn15M.setVisibility(View.VISIBLE);

        } else btn15M.setVisibility(View.GONE);

        if (Util.getPrefs(this).getBoolean("GREEK_C15_TOGGLE", false)) {
            btn20M.setText("20M");
            btn20M.setVisibility(View.VISIBLE);
        } else btn20M.setVisibility(View.GONE);
    }

    private void sendQuotesRequest(String token, String assetType) {
//        showProgress();

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getDeviceID(this), token, assetType, AccountDetails.getClientCode(this), this, serviceResponseHandler);

        } else {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(this), token, assetType, AccountDetails.getClientCode(this), this, serviceResponseHandler);

        }
    }


    public void onEventMainThread(String ServiceRequest) {


        if (ServiceRequest.equalsIgnoreCase("MakeService")) {
            isLoadmore = true;

//            LastCurrentDate = AccountDetails.getLastCurrentDate();
            if (!isEventRequestCall) {
                LastCurrentDate = getYesterdayDate(LastCurrentDate);

                changeIntraDayInterval(AccountDetails.getLastInterval());
                isEventRequestCall = true;
            }
        }
    }

    private void changeIntraDayInterval(String interval) {


//        showProgress();
        LastInterval = getInterval();


        if (LastStoredResponseDate.length() > 0) {

            if (isDailybasis) {
                LastCurrentDate = getYesterdayDate(LastStoredResponseDate);
            } else {
                LastCurrentDate = getYesterdayDate(DateTimeFormatter.getDateFromTimeStamp(LastStoredResponseDate, "yyyyMMdd", "nse"));
            }

        }


        if (isDailybasis) {

            if (checkedId == R.id.btn1M || checkedId == R.id.btn5M || checkedId == R.id.btn10M || checkedId == R.id.btn15M || checkedId == R.id.btn20M) {

                DailyChartRequest.sendRequestForDaily(token, getInterval(), LastCurrentDate, "0", this, serviceResponseHandler);

            } else {
                DailyChartRequest.sendRequestForWeekly(token, getInterval(), LastCurrentDate, "0", this, serviceResponseHandler);
            }

        } else {

            DailyChartRequest.sendRequest(token, getInterval(), LastCurrentDate, "0", this, serviceResponseHandler);
        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
//        hideProgress();
        if (response instanceof JSONResponse) {
            JSONResponse jsonResponse = (JSONResponse) response;
            if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {

                try {
                    quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                    if (!quoteResponse.getIsError()) {
                        if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                            lastTextValue.setText("0  " + String.format("%.4f", Double.parseDouble(quoteResponse.getOpen())));
                            symbolTextValue.setText(quoteResponse.getSymbol() + " | " + getExchange(quoteResponse.getToken()));
                            hightext.setText("H   " + quoteResponse.getLast());
                            lowtext.setText("L   " + quoteResponse.getLow());
                            closetext.setText("C   " + quoteResponse.getClose());
                            AccountDetails.setIsscriptcurr(true);
                        } else {
                            lastTextValue.setText("0  " + String.format("%.2f", Double.parseDouble(quoteResponse.getOpen())));
                            symbolTextValue.setText(quoteResponse.getDescription() + " " + getExchange(quoteResponse.getToken()) + " |");
                            hightext.setText("H   " + quoteResponse.getLast());
                            lowtext.setText("L   " + quoteResponse.getLow());
                            closetext.setText("C   " + quoteResponse.getClose());
                            AccountDetails.setIsscriptcurr(false);
                        }
                        sym.clear();
                        sym.add(quoteResponse.getToken());
                        streamController.sendStreamingRequest(this, sym, "ltpinfo", null, null, true);
                        streamController.sendStreamingRequest(this, sym, "marketPicture", null, null, true);

                        if (getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx")) {

                            if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {

                            } else {

                            }

                        } else {

                        }

                    } else {

                        if (getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx")) {

                            if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
//                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                            } else {
//                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                            }
                        } else {
//                            setAppTitle(getClass().toString(), quoteResponse.getDescription() + " - " + quoteResponse.getInstrument());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (CHART_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INTRADAY_CHART_SVC_NAME.equals(jsonResponse.getServiceName())) {
                try {
                    nodataTxt.setVisibility(View.GONE);
                    chartsLayout.setVisibility(View.VISIBLE);
                    Float x, y;
                    if (jsonResponse.getResponseData().getJSONArray("data").length() > 0) {
                        isRequestCall = false;
                        isEventRequestCall = false;
                        AccountDetails.setLastInterval(LastInterval);
                        AccountDetails.setLastCurrentDate(LastCurrentDate);

                        x = chartsLayout.getX();
                        y = chartsLayout.getY();

                        unSccussfulAttempt = 0;
                        ctx = this;
                        chartDisplay = new DrawChart(this);

                        responseData = jsonResponse.getResponseData();
                        parseChartData(jsonResponse.getResponseData());

                        chartSettings.setxLabelDateFormat(XLabelDateFormat.HOUR_MINUTES_HHmm);
                        chartSettings.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        chartSettings.setDateTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        chartSettings.setShowVolumeChart(true);
                        chartSettings.setGridColor(getResources().getColor(R.color.white_subheading));
                        if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                            chartSettings.setyLabelDecimalPoint(4);
                        } else {
                            chartSettings.setyLabelDecimalPoint(2);
                        }
                        chartSettings.setShowCloseBtnForVolumeChart(true);
                        chartSettings.setShowDate(false);
                        chartSettings.setPinchZoom(true);
                        chartSettings.setShowOHLCOnCrossHair(true);

                        chartSettings.setDoubleTapOverride(true);

                        AccountDetails.setIsOHLCSHOW(false);
                        chartSettings.setShowChartTitle(true);
                        chartSettings.setShowOHLC(true);
                        chartSettings.setJoinCharts(true);

                        chartSettings.setIndicatorType(ChartConstants.ChartIndicatorType.OUTLINE);
                        chartSettings.setShowCloseBtnForIndicators(true);


                        chartSettings.setChartTitle(quoteResponse.getDescription() + " " + getExchange(quoteResponse.getToken()) + " |");
                        chartSettings.setChartOHLC("O: " + quoteResponse.getOpen() + " H:  " + quoteResponse.getHigh() + " L:  " + quoteResponse.getLow() + " C:  " + quoteResponse.getClose());

                        if (isDailybasis) {
                            chartSettings.setxLabelDateFormat(XLabelDateFormat.DAY_MONTH_ddMM);
                        } else {
                            chartSettings.setxLabelDateFormat(XLabelDateFormat.HOUR_MINUTES_HHmm);
                        }


                        showChart(selectedChartType);


                        chartDisplay.setOnDoubleTapListener(new GreekChartListener.onDoubleTapListener() {
                            @Override
                            public void onDoubleTap() {
                                if (!btnY1.isChecked()) {
                                    LinearLayout crosshairLayout = findViewById(R.id.crosshairlayout);
                                    if (roverlaybtn.getVisibility() == View.VISIBLE) {

                                        roverlay.setVisibility(View.GONE);
                                        roverlaybtn.setVisibility(View.GONE);
                                        crosshairLayout.setVisibility(View.VISIBLE);

                                        LastStoredResponseDate = "";
                                        changeIntraDayInterval(AccountDetails.getLastInterval());
                                    } else {
                                        roverlay.setVisibility(View.VISIBLE);
                                        roverlaybtn.setVisibility(View.VISIBLE);
                                        crosshairLayout.setVisibility(View.GONE);
                                        LastStoredResponseDate = "";
                                        changeIntraDayInterval(AccountDetails.getLastInterval());
                                    }
                                }
                            }
                        });


                    } else {
                        isRequestCall = false;
                        isEventRequestCall = false;
                        unSccussfulAttempt = unSccussfulAttempt + 1;
                        if (unSccussfulAttempt <= 5) {
                            // Make service call with 1 less date==============>>> max 5 attempts in case of failure response

                            LastCurrentDate = getYesterdayDate(LastCurrentDate);

                            changeIntraDayInterval(AccountDetails.getLastInterval());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    isRequestCall = false;
                    isEventRequestCall = false;

                    unSccussfulAttempt = unSccussfulAttempt + 1;
                    if (unSccussfulAttempt <= 5) {
                        LastCurrentDate = getYesterdayDate(LastCurrentDate);

                        changeIntraDayInterval(AccountDetails.getLastInterval());
                    }

                    if (unSccussfulAttempt > 5) {
                        chartsLayout.setVisibility(View.GONE);

                        nodataTxt.setVisibility(View.VISIBLE);

                    }
                }
            }
        }

        onRefreshComplete();

    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }


    private String getInterval() {

        String interval = "1";

        if (checkedId == R.id.btn1M) {
            interval = "1";
            selectedChartduration = "1";

//            }

        } else if (checkedId == R.id.btn5M) {
            interval = "5";
            selectedChartduration = "5";

//            }

        } else if (checkedId == R.id.btn10M) {


            interval = "30";
            selectedChartduration = "30";
//            }

        } else if (checkedId == R.id.btnM) {


            interval = "15";
            selectedChartduration = "15";
//            }

        }else if (checkedId == R.id.btn45M) {


            interval = "45";
            selectedChartduration = "45";
//            }

        } else if (checkedId == R.id.btn15M) {


            interval = "60";
//            }

        } else if (checkedId == R.id.btn20M) {

            interval = "day";
            selectedChartduration = "day";
        } else if (checkedId == R.id.btn1H) {

            interval = "week";
            selectedChartduration = "week";
        } else if (checkedId == R.id.btn30D) {

            interval = "month";
            selectedChartduration = "month";
        } else if (checkedId == R.id.btn1y) {
            selectedChartduration = "year";
            interval = "year";
        }

        return interval;
    }


    private String getYesterdayDate(String today) {
        String yesterdayAsString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(today);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            yesterdayAsString = dateFormat.format(calendar.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return yesterdayAsString;

    }


    private void parseChartData(JSONObject object) {
        JSONArray array = null;
        try {

            array = object.getJSONArray("data");
            ArrayList<chartModel> modelList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject e = array.getJSONObject(i);
                chartModel model = new chartModel();
                model.fromJSONObject(e);
                modelList.add(model);
            }


            int prevoiusSize = size;
            size = size + modelList.size();
            close1 = new double[size];
            open1 = new double[size];
            low1 = new double[size];
            high1 = new double[size];
            volume1 = new double[size];
            date1 = new String[size];

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            int i = 0;

            for (i = 0; i < modelList.size(); i++) {


                close1[i] = Double.parseDouble(modelList.get(i).getlClose());
                open1[i] = Double.parseDouble(modelList.get(i).getlOpen());
                low1[i] = Double.parseDouble(modelList.get(i).getlLow());
                high1[i] = Double.parseDouble(modelList.get(i).getlHigh());
                volume1[i] = Double.parseDouble(modelList.get(i).getlTradeVol());

                if (i == 0) {
                    LastStoredResponseDate = modelList.get(i).getlDate();
                }

                if (isDailybasis) {

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    String dates = modelList.get(i).getlDate();

                    String  year = dates.substring(0, 4);
                    String  month = dates.substring(4, 6);
                    String  day = dates.substring(6, 8);


                    date1[i] = modelList.get(i).getlDate();

                    try {
                        Date date = format.parse(year + "-" + month + "-" + day);
                        format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        date1[i] = format.format(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                } else {
                    date1[i] = DateTimeFormatter.getDateFromTimeStamp(modelList.get(i).getlDate(), "yyyy/MM/dd HH:mm:ss", "nse");
                }


                if (prevoiusSize <= 0) {
                    lasttime = Long.parseLong(modelList.get(i).getlDate());
                }

            }
            lasttime = lasttime + (1 * 60);


            for (int j = 0; j < prevoiusSize; i++, j++) {

                close1[i] = close[j];
                open1[i] = open[j];
                low1[i] = low[j];
                high1[i] = high[j];
                volume1[i] = volume[j];
                date1[i] = date[j];
            }

            chartData = new Hashtable();
            chartData.put("open", open1);
            chartData.put("high", high1);
            chartData.put("low", low1);
            chartData.put("close", close1);
            chartData.put("date", date1);
            chartData.put("volume", volume1);


            open = open1;
            close = close1;
            high = high1;
            low = low1;
            date = date1;
            volume = volume1;

            int start = AccountDetails.getStartIndex();
            int end = AccountDetails.getEndIndex();

            AccountDetails.setStartIndex(start + modelList.size());
            AccountDetails.setEndIndex(end + modelList.size());
            LastCurrentDate = getYesterdayDate(LastCurrentDate);
            //if (isFirstTime)
            chartSettings.setChartData(chartData);
            chartSettings.setStartIndex(start + modelList.size());
            chartSettings.setEndIndex(end + modelList.size());
            chartSettings.setIntraDate(LastCurrentDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("ltpinfo")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {

        if (token.equals(response.getSymbol())) {
            if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                lastTextValue.setText(String.format("%.4f", Double.parseDouble(response.getLast())));
                changeTextValue.setText(String.format("%s (%s%%)", String.format("%.4f", Double.parseDouble(response.getChange())), String.format("%.4f", Double.parseDouble(response.getP_change()))));
            } else {
                lastTextValue.setText(String.format("%.2f", Double.parseDouble(response.getLast())));
                changeTextValue.setText(String.format("%s (%s%%)", String.format("%.2f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
            }



        }
    }








    private void showChart(int position) {

        try {
            if (chartDisplay != null) {
                if (position == 0) chartDisplay.showLineChart(chartSettings, chartsLayout, 0xFF6BB6FF);
                else if (position == 1)
                    chartDisplay.showAreaChart(chartSettings, chartsLayout, 0xFF6BB6FF, 0xFF6BB6FF);
                else if (position == 2)
                    chartDisplay.showOHLCChart(chartSettings, chartsLayout, 0xFF009900, Color.RED);
                else if (position == 3)
                    chartDisplay.showCandleStickChart(chartSettings, chartsLayout, 0xFF009900, Color.RED);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
//        super.infoDialog(action, msg, jsonResponse);
        chartDisplay = null;
        chartSettings = new ChartSettings();
    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {

    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {

    }

    public String getExchange(String token) {
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


    private String getAssetTypeFromToken(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if (((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }

        // return "";
    }

    @Override
    public void onResume() {
        super.onResume();
//        resolutionBar.check(R.id.btn1M);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if ((getRequestedOrientation()) == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.v("On Double tAb", "Doubletap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.v("On Double tAb", "Doubletap");
        return false;
    }
}

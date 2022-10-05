package com.acumengroup.mobile.markets;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.acumengroup.greekmain.chart.draw.ChartConstants.XLabelDateFormat;
import com.acumengroup.greekmain.chart.draw.DrawChart;
import com.acumengroup.greekmain.chart.scroll.ChartInterceptScrollView;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.intradaychart.DailyChartRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseActivity.USER;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import de.greenrobot.event.EventBus;

public class ChartsFragment extends GreekBaseFragment {
    public static final String SCRIP_NAME = "Scrip";
    public static final String TOKEN = "Token";
    public static final String LOT_QUANTITY = "Lots";
    public static final String MULTIPLIER = "Multiplier";
    public static final String TICK_SIZE = "TickSize";
    public static final String TRADE_SYMBOL = "TradeSymbol";
    public static final String ASSET_TYPE = "AssetType";
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
    private View chartsView;
    private LinearLayout chartsLayout;
    private String LastStoredResponseDate = "", LastCurrentDate = "", LastInterval;
    private int unSccussfulAttempt = 0;
    private boolean isDailybasis = false;
    private int checkedId = R.id.btn1M;
    private Spinner resolutionSpinner;
    private ImageButton types, crosshair;
    private final OnClickListener typesClicked = new OnClickListener() {

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
    private RadioButton btn20M;
    private SwipeRefreshLayout swipeRefresh;
    private ChartInterceptScrollView scrollChart;
    private boolean mIsFling;
    static Hashtable chartData;
    boolean isLoadmore = false;
    private double[] close, open, low, high, volume, close1, open1, low1, high1, volume1;
    private String[] date, date1;
    private int size;
    private final OnItemSelectedListener resolutionSelection = new OnItemSelectedListener() {

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
    private GreekTextView lastTextValue, symbolTextValue, changeTextValue;
    private ChartSettings chartSettings = new ChartSettings();
    private DrawChart chartDisplay = null;
    public long lasttime = 0;
    private ArrayList<Double> closeList = new ArrayList<Double>();


    private final OnClickListener buyClicked = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (GreekBaseActivity.USER_TYPE == USER.CUSTOMER || GreekBaseActivity.USER_TYPE == USER.IBTCUSTOMER) {
                Bundle args = new Bundle();
                args.putString("TradeSymbol", getArguments().getString(TRADE_SYMBOL));
                args.putString("Token", getArguments().getString(TOKEN));
                args.putString("Lots", getArguments().getString(LOT_QUANTITY));
                args.putString("Action", getArguments().getString("Action"));
                args.putString("AssetType", getArguments().getString(ASSET_TYPE));
                args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                args.putString("TickSize", getArguments().getString(TICK_SIZE));
                args.putString("TradeSymbol", getArguments().getString(TRADE_SYMBOL));
                args.putString("Scrip", scripName);
                args.putString("Action", "buy");
                args.putString("ExchangeName", getExchange(token));
                navigateTo(NAV_TO_TRADE_SCREEN, args, true);

            } else if (GreekBaseActivity.USER_TYPE == USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_MF), "Ok", false, new DialogListener() {
                    @Override
                    public void alertDialogAction(Action action, Object... data) {

                    }
                });
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new DialogListener() {
                    @Override
                    public void alertDialogAction(Action action, Object... data) {

                    }
                });
            }
        }
    };
    private final OnClickListener sellClicked = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (GreekBaseActivity.USER_TYPE == USER.CUSTOMER || GreekBaseActivity.USER_TYPE == USER.IBTCUSTOMER) {
                Bundle args = new Bundle();
                args.putString("TradeSymbol", getArguments().getString(TRADE_SYMBOL));
                args.putString("Token", getArguments().getString(TOKEN));
                args.putString("Lots", getArguments().getString(LOT_QUANTITY));
                args.putString("Action", getArguments().getString("Action"));
                args.putString("AssetType", getArguments().getString(ASSET_TYPE));
                args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                args.putString("TickSize", getArguments().getString(TICK_SIZE));
                args.putString("Scrip", scripName);
                args.putString("Action", "sell");
                args.putString("ExchangeName", getExchange(token));
                navigateTo(NAV_TO_TRADE_SCREEN, args, true);
            } else if (GreekBaseActivity.USER_TYPE == USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_MF), "Ok", false, new DialogListener() {
                    @Override
                    public void alertDialogAction(Action action, Object... data) {

                    }
                });
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {

                    }
                });
            }
        }
    };

    private final OnClickListener crossHairClicked = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (chartDisplay == null) return;

            if (chartSettings.isShowCrossHairs()) {
                chartSettings.setShowCrossHairs(false);
                crosshair.setImageResource(R.drawable.pointer_active_ic_30);
//                if (!btn15M.isChecked()&&btn15M.getText().toString().equalsIgnoreCase("1 Year")) {
                   /* LastStoredResponseDate = "";
                    changeIntraDayInterval(AccountDetails.getLastInterval());*/
//                }

            } else {
                chartSettings.setShowCrossHairs(true);
                crosshair.setImageResource(R.drawable.pointer_inactive_ic_30);
            }
            chartDisplay.showCrossHairs(chartSettings);
        }
    };

    private void refresh() {
        sendQuotesRequest(getArguments().getString(TOKEN), getArguments().getString(ASSET_TYPE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chartsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_charts).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_charts).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        close = new double[size];
        open = new double[size];
        low = new double[size];
        high = new double[size];
        volume = new double[size];
        date = new String[size];

        AccountDetails.currentFragment = NAV_TO_CHARTS_SCREEN;
        setupViews();

        return chartsView;
    }

    private void setupViews() {

        swipeRefresh = chartsView.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);

        scrollChart = chartsView.findViewById(R.id.scrollChart);


//        scrollChart.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (!(chart.getLowestVisibleX() == chart.getXAxis().getAxisMinimum() || chart.getHighestVisibleX() == chart.getXAxis().getAxisMaximum())) {
//                    // Do your work here
//                    Toast.makeText(getContext(), "Hello Scroll to end check working", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }
//
//        });


        scrollChart.setOnEndScrollListener(new ChartInterceptScrollView.OnEndScrollListener() {
            @Override
            public void onEndScroll() {

                Toast.makeText(getContext(), "Hello Scroll to end check working", Toast.LENGTH_LONG).show();
            }
        });


        resolutionSpinner = chartsView.findViewById(R.id.resolutionSpinner);
        ArrayAdapter<String> resolutionAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), chartDuration);
        resolutionAdapter.setDropDownViewResource(R.layout.custom_spinner);
        resolutionSpinner.setAdapter(resolutionAdapter);
        resolutionSpinner.setOnItemSelectedListener(resolutionSelection);

        types = chartsView.findViewById(R.id.typesBtn);
        types.setOnClickListener(typesClicked);
        crosshair = chartsView.findViewById(R.id.crossHairBtn);
        crosshair.setOnClickListener(crossHairClicked);

        chartsLayout = chartsView.findViewById(R.id.chartLayout);
        lastTextValue = chartsView.findViewById(R.id.lastText);
        symbolTextValue = chartsView.findViewById(R.id.symbolText);
        changeTextValue = chartsView.findViewById(R.id.changeTextchart);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            lastTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            symbolTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            changeTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }

        btn1M = chartsView.findViewById(R.id.btn1M);
        btn5M = chartsView.findViewById(R.id.btn5M);
        btn10M = chartsView.findViewById(R.id.btn10M);
        btn15M = chartsView.findViewById(R.id.btn15M);
        btn20M = chartsView.findViewById(R.id.btn20M);
        resolutionBar = chartsView.findViewById(R.id.resolutionBar);
        resolutionBar.check(R.id.btn1M);
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
                if (checkedIds == R.id.btn1M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0 || resolutionSpinner.getSelectedItemPosition() == 1) {

                        size = 0;
                        close = new double[size];
                        open = new double[size];
                        low = new double[size];
                        high = new double[size];
                        volume = new double[size];
                        date = new String[size];

                        if (isDailybasis) {

                            changeIntraDayInterval("week");

                        } else {
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

                        if (isDailybasis) {

                            changeIntraDayInterval("month");

                        } else {
                            changeIntraDayInterval("5");
                        }
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


                        if (isDailybasis) {

                            changeIntraDayInterval("year");

                        } else {
                            changeIntraDayInterval("10");
                        }
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

                        changeIntraDayInterval("15");
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

                        changeIntraDayInterval("20");
                    }
                }
            }
        });

        GreekButton buyBtn = chartsView.findViewById(R.id.buy_btn);
        GreekButton sellBtn = chartsView.findViewById(R.id.sell_btn);

        buyBtn.setVisibility(View.VISIBLE);
        buyBtn.setOnClickListener(buyClicked);
        sellBtn.setVisibility(View.VISIBLE);
        sellBtn.setOnClickListener(sellClicked);


        scripName = getArguments().getString(SCRIP_NAME);
        String description = getArguments().getString("description");
        token = getArguments().getString(TOKEN);
        AccountDetails.globalArgChartFrag = getArguments();
        sendQuotesRequest(getArguments().getString(TOKEN), getArguments().getString(ASSET_TYPE));
        setAppTitle(getClass().toString(), description);
        setBtnTabText();
//uncomment for chart rotation
      /*  if(getArguments().containsKey("from")){
            if((getMainActivity().getRequestedOrientation())==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                getMainActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }else{
                getMainActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }*/
    }

    private void onRefreshComplete() {
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void setupQuickAction(View v) {
        Vector<QuickActionItemDetails> actionItemDetails = new Vector<>();
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.ic_chart_white_24dp, true, R.color.red));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.area_chart_ic_w_30, true, R.color.red));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.ohlc_chart_ic_w_30, true, R.color.red));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.candle_chart_ic_w_30, true, R.color.red));

        QuickActionAdapter quickActionAdapter = new QuickActionAdapter(getMainActivity(), actionItemDetails);

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
        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C11_TOGGLE", true)) {
            btn1M.setText("1M");
            btn1M.setVisibility(View.VISIBLE);
        } else btn1M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C12_TOGGLE", true)) {
            btn5M.setText("5M");
            btn5M.setVisibility(View.VISIBLE);
        } else btn5M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C13_TOGGLE", true)) {
            btn10M.setText("10M");
            btn10M.setVisibility(View.VISIBLE);
        } else btn10M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C14_TOGGLE", false)) {
            btn15M.setText("15M");
            btn15M.setVisibility(View.VISIBLE);
        } else btn15M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C15_TOGGLE", false)) {
            btn20M.setText("20M");
            btn20M.setVisibility(View.VISIBLE);
        } else btn20M.setVisibility(View.GONE);
    }


    private void setBtnTabTextForDaily() {
        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C11_TOGGLE", true)) {
            btn1M.setText("1 Day");
            btn1M.setVisibility(View.VISIBLE);
        } else btn1M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C12_TOGGLE", true)) {
            btn5M.setText("1 Week");
            btn5M.setVisibility(View.VISIBLE);
        } else btn5M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C13_TOGGLE", true)) {
            btn10M.setText("1 Month");
            btn10M.setVisibility(View.VISIBLE);
        } else btn10M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C14_TOGGLE", true)) {
            btn15M.setText("1 Year");
            btn15M.setVisibility(View.VISIBLE);

        } else btn15M.setVisibility(View.GONE);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_C15_TOGGLE", false)) {
            btn20M.setText("20M");
            btn20M.setVisibility(View.VISIBLE);
        } else btn20M.setVisibility(View.GONE);
    }

    private void sendQuotesRequest(String token, String assetType) {
        showProgress();

        if (GreekBaseActivity.USER_TYPE == USER.OPENUSER) {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()), token, assetType, AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);

        } else {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), token, assetType, AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);

        }
    }


    public void onEventMainThread(String ServiceRequest) {

        if (ServiceRequest.equalsIgnoreCase("MakeService")) {
            isLoadmore = true;
            LastCurrentDate = AccountDetails.getLastCurrentDate();

            LastCurrentDate = getYesterdayDate(LastCurrentDate);

            changeIntraDayInterval(AccountDetails.getLastInterval());
        }
    }

    private void changeIntraDayInterval(String interval) {

        //if (chartsLayout != null) chartsLayout.removeAllViews();
        showProgress();

//      LastInterval = interval;
        LastInterval = getInterval();


        if (LastStoredResponseDate.length() > 0) {

            if (isDailybasis) {
                LastCurrentDate = getYesterdayDate(LastStoredResponseDate);
            } else {
                LastCurrentDate = getYesterdayDate(DateTimeFormatter.getDateFromTimeStamp(LastStoredResponseDate, "yyyyMMdd", "nse"));
            }

        }

        if (isDailybasis) {

            if (checkedId == R.id.btn1M) {

                DailyChartRequest.sendRequestForDaily(token, getInterval(), LastCurrentDate, "0", getMainActivity(), serviceResponseHandler);

            } else {
                DailyChartRequest.sendRequestForWeekly(token, getInterval(), LastCurrentDate, "0", getMainActivity(), serviceResponseHandler);
            }

        } else {


            DailyChartRequest.sendRequest(token, getInterval(), LastCurrentDate, "0", getMainActivity(), serviceResponseHandler);
        }
    }

    @Override
    public void handleResponse(Object response) {
        hideProgress();
        if (response instanceof JSONResponse) {
            JSONResponse jsonResponse = (JSONResponse) response;
            if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {

                try {
                    quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                    if (!quoteResponse.getIsError()) {
                        if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                            lastTextValue.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getLast())));
                            symbolTextValue.setText(quoteResponse.getSymbol());
                            changeTextValue.setText(String.format("%s (%s%%)", String.format("%.4f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
                            changeTextValue.setTextColorForChange(quoteResponse.getP_change());
                        } else {
                            lastTextValue.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLast())));
                            symbolTextValue.setText(quoteResponse.getSymbol());
                            changeTextValue.setText(String.format("%s (%s%%)", String.format("%.2f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
                            changeTextValue.setTextColorForChange(quoteResponse.getP_change());
                        }
                        sym.clear();
                        sym.add(quoteResponse.getToken());
                        streamController.sendStreamingRequest(getMainActivity(), sym, "ltpinfo", null, null, true);
                        addToStreamingList("ltpinfo", sym);

                        if (getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx")) {

                            if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                            } else {
                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                            }

                        } else {
                            setAppTitle(getClass().toString(), quoteResponse.getDescription() + " - " + quoteResponse.getInstrument());
                        }

                    } else {

                        if (getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx")) {

                            if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                            } else {
                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                            }
                        } else {
                            setAppTitle(getClass().toString(), quoteResponse.getDescription() + " - " + quoteResponse.getInstrument());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (CHART_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INTRADAY_CHART_SVC_NAME.equals(jsonResponse.getServiceName())) {
                try {

                    if (jsonResponse.getResponseData().getJSONArray("data").length() > 0) {

                        AccountDetails.setLastInterval(LastInterval);
                        AccountDetails.setLastCurrentDate(LastCurrentDate);

                        unSccussfulAttempt = 0;                        //

                        parseChartData(jsonResponse.getResponseData());

                        chartSettings.setxLabelDateFormat(XLabelDateFormat.HOUR_MINUTES_HHmm);
                        chartSettings.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        chartSettings.setDateTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        chartSettings.setShowVolumeChart(true);
                        chartSettings.setGridColor(getResources().getColor(R.color.white_subheading));
//                        if (getArguments().getString(ASSET_TYPE).equalsIgnoreCase("currency")) {
                        if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                            chartSettings.setyLabelDecimalPoint(4);
                        } else {
                            chartSettings.setyLabelDecimalPoint(2);
                        }
                        chartSettings.setShowDate(false);
                        chartSettings.setPinchZoom(true);
                        chartSettings.setShowCrossHairs(true);
                        /*  if(!isLoadmore) {
                          isLoadmore= false;*/
                              chartDisplay = new DrawChart(getMainActivity());

//                          }

                        if (resolutionSpinner.getSelectedItemPosition() == 1)
                            chartSettings.setxLabelDateFormat(XLabelDateFormat.DAY_MONTH_ddMM);
                        else chartSettings.setxLabelDateFormat(XLabelDateFormat.HOUR_MINUTES_HHmm);

                        showChart(selectedChartType);


                    } else {

                        unSccussfulAttempt = unSccussfulAttempt + 1;
                        if (unSccussfulAttempt <= 5) {
                            // Make service call with 1 less date==============>>> max 5 attempts in case of failure response

                            LastCurrentDate = getYesterdayDate(LastCurrentDate);

                            changeIntraDayInterval(AccountDetails.getLastInterval());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    unSccussfulAttempt = unSccussfulAttempt + 1;
                    if (unSccussfulAttempt <= 5) {
                        // Make service call with 1 less date==============>>> max 5 attempts in case of failure response

                        LastCurrentDate = getYesterdayDate(LastCurrentDate);

                        changeIntraDayInterval(AccountDetails.getLastInterval());
                    }
                }
            }
        }
        onRefreshComplete();
    }

    private String getInterval() {

        String interval = "1";

        if (checkedId == R.id.btn1M) {

            if (isDailybasis) {

                interval = "day";

            } else {
                interval = "1";

            }

        } else if (checkedId == R.id.btn5M) {

            if (isDailybasis) {

                interval = "week";

            } else {
                interval = "5";

            }

        } else if (checkedId == R.id.btn10M) {

            if (isDailybasis) {

                interval = "month";


            } else {

                interval = "10";
            }

        } else if (checkedId == R.id.btn15M) {

            if (isDailybasis) {

                interval = "year";

            } else {

                interval = "10";
            }

        } else if (checkedId == R.id.btn20M) {


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
                    String year = dates.substring(0, 4);
                    String month = dates.substring(4, 6);
                    String day = dates.substring(6, 8);

                    date1[i] = modelList.get(i).getlDate();

                    try {
                        Date date = format.parse(year + "-" + month + "-" + day);
                        format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
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
                updateBroadcastData(broadcastResponse);
            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void updateBroadcastData(StreamerBroadcastResponse response) {

        if (token.equals(response.getSymbol())) {
            if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                lastTextValue.setText(String.format("%.4f", Double.parseDouble(response.getLast())));
                changeTextValue.setText(String.format("%s (%s%%)", String.format("%.4f", Double.parseDouble(response.getChange())), String.format("%.4f", Double.parseDouble(response.getP_change()))));
            } else {
                lastTextValue.setText(String.format("%.2f", Double.parseDouble(response.getLast())));
                changeTextValue.setText(String.format("%s (%s%%)", String.format("%.2f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
            }

//            calculatedata(response);

        }
    }


//    private void calculatedata(StreamerBroadcastResponse response) {
//        boolean editValue = false;
//        long timemilli = 0;
//        String time = response.getLtt();
//        SimpleDateFormat dt;
//        Date date;
//        dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        try {
//            date = (Date) dt.parse(time);
//
//            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
//            timemilli = timestamp.getTime() / 1000;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (lasttime <= timemilli) {//append
//            editValue = false;
//            closeList.add(Double.parseDouble(response.getLast()));
//            chartDisplay.setData(chartSettings, chartsLayout, 0, closeList, editValue);
//            lasttime = timemilli + 1 * 60;
//        } else {
//            ///edit
//            editValue = true;
//            closeList.add(Double.parseDouble(response.getLast()));
//            int size = closeList.size();
//            chartDisplay.setData(chartSettings, chartsLayout, 0, closeList, editValue);
//            closeList.remove(size - 1);
//
//        }
//
//    }


    private void showChart(int position) {
        if (chartDisplay != null) {
            if (position == 0) chartDisplay.showLineChart(chartSettings, chartsLayout, 0xFF6BB6FF);
            else if (position == 1)
                chartDisplay.showAreaChart(chartSettings, chartsLayout, 0xFF6BB6FF, 0xFF6BB6FF);
            else if (position == 2)
                chartDisplay.showOHLCChart(chartSettings, chartsLayout, 0xFF009900, Color.RED);
            else if (position == 3)
                chartDisplay.showCandleStickChart(chartSettings, chartsLayout, 0xFF009900, Color.RED);

        }
    }

    @Override
    public void onFragmentResult(Object data) {
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_CHARTS_SCREEN;
        EventBus.getDefault().register(this);
        if (sym != null) {
            if (streamController != null) {
                streamController.sendStreamingRequest(getMainActivity(), sym, "ltpinfo", null, null, false);
                addToStreamingList("ltpinfo", sym);
            }
        }
    }

    @Override
    public void onFragmentPause() {
        if (sym != null) {
            if (streamController != null) {
                streamController.pauseStreaming(getMainActivity(), "ltpinfo", sym);
            }
        }
        EventBus.getDefault().unregister(this);
        hideAppTitle();
        super.onFragmentPause();
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        super.infoDialog(action, msg, jsonResponse);
        chartDisplay = null;
        chartSettings = new ChartSettings();
    }

    private String getExchange(String token) {
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

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
}
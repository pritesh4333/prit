package com.acumengroup.mobile.trade;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.draw.DrawChart;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.intradaychart.DailyChartRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.chartiqscreen.MainActivity;
import com.acumengroup.mobile.model.chartModel;
import com.acumengroup.mobile.symbolsearch.ChartingFragment;
import com.acumengroup.ui.GreekDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import de.greenrobot.event.EventBus;

public class PlaceOrderChartFragment extends GreekBaseFragment {
    public static final String SCRIP_NAME = "Scrip";
    public static final String TOKEN = "Token";
    public static final String LOT_QUANTITY = "Lots";
    public static final String MULTIPLIER = "Multiplier";
    public static final String TICK_SIZE = "TickSize";
    public static final String TRADE_SYMBOL = "TradeSymbol";
    public static final String ASSET_TYPE = "AssetType";
    MarketsSingleScripResponse quoteResponse;
    ChartingFragment chartingFragment;
    ArrayList<String> sym = new ArrayList<>();
    private final int[] chartTypeDrawable = {R.drawable.ic_chart_white_24dp, R.drawable.area_chart_ic_w_30, R.drawable.ohlc_chart_ic_w_30, R.drawable.candle_chart_ic_w_30};
    private final int[] chartTypeDrawableBlack = {R.drawable.ic_chart_black_24dp, R.drawable.area_chart_ic_30, R.drawable.ohlc_chart_ic_30, R.drawable.candle_chart_ic_30};

    private final String[] chartDuration = {"Intraday"};
    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refresh();
        }
    };
    private int selectedChartType = 1;
    private View chartsView;
    private LinearLayout chartsLayout;
    private final AdapterView.OnItemSelectedListener resolutionSelection = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                //changeIntraDayInterval("1");
            }
            chartsLayout.removeAllViews();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private String scripName;
    private String token;
    private TextView nodataTxt;
    private JSONObject responseData;
    private boolean iscurrency = false;

    public static PlaceOrderChartFragment newInstance(String source, String type) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        PlaceOrderChartFragment fragment = new PlaceOrderChartFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final View.OnClickListener buyClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
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

            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_MF), "Ok", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            }
        }
    };
    private final View.OnClickListener sellClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
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
            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_MF), "Ok", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            }
        }
    };
    private GreekTextView lastTextValue, changeTextValue;
    private ChartSettings chartSettings = new ChartSettings();
    private DrawChart chartDisplay;
    private final View.OnClickListener crossHairClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (chartDisplay == null) return;

            if (chartSettings.isShowCrossHairs()) {

                //sendQuotesRequest();

                chartSettings.setShowCrossHairs(false);
                crosshair.setImageResource(R.drawable.pointer_inactive_ic_30);

            } else {
                chartSettings.setShowCrossHairs(true);
                crosshair.setImageResource(R.drawable.pointer_inactive_ic_30);
            }
            chartDisplay.showCrossHairs(chartSettings);
        }
    };
    private Spinner resolutionSpinner;
    private ImageButton types, crosshair, rotate;
    private final View.OnClickListener typesClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            setupQuickAction(v);
        }
    };


    private final View.OnClickListener rotateclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            EventBus.getDefault().post("dismiss");
            Bundle bundle = new Bundle();
//            bundle.putString("Scrip", AccountDetails.globalPlaceOrderBundle.getString(SCRIP_NAME));
            bundle.putString("Scrip", quoteResponse.getDescription() + " " + getExchange(quoteResponse.getToken()) + " |");
            bundle.putString("Token", AccountDetails.globalPlaceOrderBundle.getString(TOKEN));
            bundle.putString("TradeSymbol", AccountDetails.globalPlaceOrderBundle.getString(TRADE_SYMBOL));
            bundle.putString("description", "");
            bundle.putString("ltp", quoteResponse.getLast());
            bundle.putString("Lots", AccountDetails.globalPlaceOrderBundle.getString(LOT_QUANTITY));
            bundle.putString("Action", "1");
            bundle.putString("AssetType", AccountDetails.globalPlaceOrderBundle.getString("AssetType"));
            //uncomment for chart rotation
            bundle.putString("from", "placeorderchart");
            bundle.putSerializable("response", quoteResponse);
            bundle.putBoolean("iscurrency", iscurrency);
            AccountDetails.setIsMainActivity(true);
            if (AccountDetails.getChartSetting().equalsIgnoreCase("m4")) {
                Intent rotatechart = new Intent(getActivity(), RotateChartActivity.class);
                rotatechart.putExtras(bundle);
                startActivity(rotatechart);
            } else {
                Intent rotatechart = new Intent(getActivity(), MainActivity.class);
                rotatechart.putExtras(bundle);
                startActivity(rotatechart);
            }


        }
    };
    private RadioButton btn1M;
    private RadioButton btn5M;
    private RadioButton btn10M;
    private RadioButton btn15M;
    private RadioButton btn20M;
    private SwipeRefreshLayout swipeRefresh;


    private void refresh() {
        sendQuotesRequest();
    }


    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chartsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_po_chart).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_po_chart).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
//        AccountDetails.currentFragment = NAV_TO_CHARTS_SCREEN;
        setupViews();

        chartingFragment = new ChartingFragment();

        return chartsView;
    }

    private void setupViews() {
        swipeRefresh = chartsView.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        nodataTxt = chartsView.findViewById(R.id.empty_view);

        resolutionSpinner = chartsView.findViewById(R.id.resolutionSpinner);
        ArrayAdapter<String> resolutionAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), chartDuration);
        resolutionAdapter.setDropDownViewResource(R.layout.custom_spinner);
        resolutionSpinner.setAdapter(resolutionAdapter);
        resolutionSpinner.setOnItemSelectedListener(resolutionSelection);

        types = chartsView.findViewById(R.id.typesBtn);
        types.setOnClickListener(typesClicked);
        rotate = chartsView.findViewById(R.id.rotateBtn);
        rotate.setOnClickListener(rotateclick);
        crosshair = chartsView.findViewById(R.id.crossHairBtn);
        crosshair.setOnClickListener(crossHairClicked);

        chartsLayout = chartsView.findViewById(R.id.chartLayout);
        lastTextValue = chartsView.findViewById(R.id.lastText);
        changeTextValue = chartsView.findViewById(R.id.changeTextchart);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            lastTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            nodataTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            changeTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            types.setImageDrawable(getResources().getDrawable(R.drawable.candle_chart_ic_30));
            crosshair.setImageDrawable(getResources().getDrawable(R.drawable.pointer_active_ic_30));
            rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_white_24dp));
        }

        btn1M = chartsView.findViewById(R.id.btn1M);
        btn5M = chartsView.findViewById(R.id.btn5M);
        btn10M = chartsView.findViewById(R.id.btn10M);
        btn15M = chartsView.findViewById(R.id.btn15M);
        btn20M = chartsView.findViewById(R.id.btn20M);
        RadioGroup resolutionBar = chartsView.findViewById(R.id.resolutionBar);
        resolutionBar.check(R.id.btn1M);
        resolutionBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn1M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0) {
                        //changeIntraDayInterval("1");
                    }
                } else if (checkedId == R.id.btn5M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0) {
                        //changeIntraDayInterval("5");
                    }
                } else if (checkedId == R.id.btn10M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0) {
                        //changeIntraDayInterval("10");
                    }
                } else if (checkedId == R.id.btn15M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0) {
                        //changeIntraDayInterval("15");
                    }
                } else if (checkedId == R.id.btn20M) {
                    if (resolutionSpinner.getSelectedItemPosition() == 0) {
                        //changeIntraDayInterval("20");
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

        setAppTitle(getClass().toString(), description);
        setBtnTabText();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser)
            sendQuotesRequest();
    }

    private void onRefreshComplete() {
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void setupQuickAction(View v) {
        Vector<QuickActionItemDetails> actionItemDetails = new Vector<>();
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.ic_chart_white_24dp, true, R.color.common_red_bg));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.area_chart_ic_w_30, true, R.color.common_red_bg));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.ohlc_chart_ic_w_30, true, R.color.common_red_bg));
        actionItemDetails.add(new QuickActionItemDetails(R.drawable.candle_chart_ic_w_30, true, R.color.common_red_bg));

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


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            types.setImageResource(chartTypeDrawableBlack[which]);
        } else {
            types.setImageResource(chartTypeDrawable[which]);
        }
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

    private void sendQuotesRequest() {
        String token = AccountDetails.globalPlaceOrderBundle.getString(TOKEN);
        String assetType = AccountDetails.globalPlaceOrderBundle.getString("AssetType");
        //showProgress();
        MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), token, getAssetTypeFromToken(token), AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);
        changeIntraDayInterval("1", token);
    }

    private void changeIntraDayInterval(String interval, String token) {
        if (chartsLayout != null) chartsLayout.removeAllViews();
        //showProgress();
        //DailyChartRequest.sendRequest(token, interval, getMainActivity(), serviceResponseHandler);
        SimpleDateFormat expirydateformat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        String currentDate = expirydateformat.format(c.getTime());
        DailyChartRequest.sendRequest(token, interval, currentDate, "0", getMainActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        //hideProgress();
        if (response instanceof JSONResponse) {
            JSONResponse jsonResponse = (JSONResponse) response;
            if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {

                try {
                    quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                    if (!quoteResponse.getIsError()) {
                        if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                            lastTextValue.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getLast())));
                            changeTextValue.setText(String.format("%s (%s%%)", String.format("%.4f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
                            changeTextValue.setTextColorForChange(quoteResponse.getP_change());
                            iscurrency = true;
                            AccountDetails.setIsscriptcurr(true);
                        } else {
                            lastTextValue.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLast())));
                            changeTextValue.setText(String.format("%s (%s%%)", String.format("%.2f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
                            changeTextValue.setTextColorForChange(quoteResponse.getP_change());
                            iscurrency = false;
                            AccountDetails.setIsscriptcurr(false);
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
                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" +
                                        DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
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
                        chartsLayout.setVisibility(View.VISIBLE);

                        nodataTxt.setVisibility(View.GONE);
                        responseData = jsonResponse.getResponseData();
                        parseChartData(jsonResponse.getResponseData());

                        chartSettings.setxLabelDateFormat(ChartConstants.XLabelDateFormat.HOUR_MINUTES_HHmm);
                        chartSettings.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        chartSettings.setDateTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        chartSettings.setShowVolumeChart(true);
                        chartSettings.setGridColor(getResources().getColor(R.color.white_subheading));
                        /*if (getArguments().getString(ASSET_TYPE).equalsIgnoreCase("currency")) {
                            chartSettings.setyLabelDecimalPoint(4);
                        } else {
                            chartSettings.setyLabelDecimalPoint(2);
                        }*/
                        chartSettings.setShowDate(false);
                        chartSettings.setPinchZoom(true);
//                        chartSettings.setShowCrossHairs(true);
                        chartDisplay = new DrawChart(getMainActivity());

                        if (resolutionSpinner.getSelectedItemPosition() == 1)
                            chartSettings.setxLabelDateFormat(ChartConstants.XLabelDateFormat.DAY_MONTH_ddMM);
                        else
                            chartSettings.setxLabelDateFormat(ChartConstants.XLabelDateFormat.HOUR_MINUTES_HHmm);

                        showChart(selectedChartType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    chartsLayout.setVisibility(View.GONE);

                    nodataTxt.setVisibility(View.VISIBLE);

                }
            }
        }
        onRefreshComplete();
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
            int size = modelList.size();

            double[] close = new double[size];
            double[] open = new double[size];
            double[] low = new double[size];
            double[] high = new double[size];
            double[] volume = new double[size];
            String[] date = new String[size];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            for (int i = 0; i < modelList.size(); i++) {
                close[i] = Double.parseDouble(modelList.get(i).getlClose());
                open[i] = Double.parseDouble(modelList.get(i).getlOpen());
                low[i] = Double.parseDouble(modelList.get(i).getlLow());
                high[i] = Double.parseDouble(modelList.get(i).getlHigh());
                volume[i] = Double.parseDouble(modelList.get(i).getlTradeVol());
                date[i] = DateTimeFormatter.getDateFromTimeStamp(modelList.get(i).getlDate(), "yyyy/MM/dd HH:mm:ss", "nse");
            }

            Hashtable chartData = new Hashtable();

            chartData.put("open", open);
            chartData.put("high", high);
            chartData.put("low", low);
            chartData.put("close", close);
            chartData.put("date", date);
            chartData.put("volume", volume);

            chartSettings.setChartData(chartData);

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
        }
    }

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
//        AccountDetails.currentFragment = NAV_TO_CHARTS_SCREEN;
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


}
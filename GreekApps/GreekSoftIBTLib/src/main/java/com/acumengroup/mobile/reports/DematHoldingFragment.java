package com.acumengroup.mobile.reports;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.DematHolding;
import com.acumengroup.greekmain.core.model.TradeDematHoldingResponse;
import com.acumengroup.greekmain.core.model.dematltpdetails.DematList;
import com.acumengroup.greekmain.core.model.dematltpdetails.DematLtpDetailsRequest;
import com.acumengroup.greekmain.core.model.dematltpdetails.DematLtpDetailsResponse;
import com.acumengroup.greekmain.core.model.marketsmultiplescrip.MarketsMultipleScripResponse;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.AddSymbolToGroupRequest;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.SymbolDetail;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.SearchMultiStockDetailsResponse;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.StockList;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.login.LoginActivity;
import com.acumengroup.mobile.markets.QuotesFragment;
import com.acumengroup.mobile.model.watchlistModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class DematHoldingFragment extends GreekBaseFragment implements View.OnClickListener {
    private final List<String> groupName = new ArrayList<>();
    private final List<String> groupType = new ArrayList<>();
    private final List<SymbolDetail> symbolList = new ArrayList<>();
    private final ArrayList<StockList> stockList = new ArrayList<>();
    private final ArrayList<String> groupNameList = new ArrayList<>();
    private static final String[] optionsArray = {"BSE", "NSE"};
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private boolean isWaitingForResponseOnPTR = false;
    private TradeDematHoldingResponse dematHoldingResponse;
    private ListView positionList;
    private GreekTextView ltpSort, symText, mtmText, holdingValue, currentHolding, holdingMtm;
    boolean ltpAsc, symAsc, mtmAsc = false;
    DematHolding rowData = null;
    private DematHoldingFragment.TopGainerAdapter gainerAdapter;
    private double totalMtm = 0, totalcurrentHoldingValue = 0, totalholdingValue = 0;
    private PortfolioGetUserWatchListResponse getWatchListResponse;
    private ArrayAdapter<String> groupsAdapter;
    private List<SymbolList> symbolLists = new ArrayList<>();
    private List<DematList> dematLtpDetails = new ArrayList<>();
    private String selectedGrp = "", selectedGrpType = "";
    private AlertDialog alertDialog;
    private SearchMultiStockDetailsResponse mutiStockResponse;
    private DematLtpDetailsResponse dematLtpDetailsResponse;
    private MarketsMultipleScripResponse multiQuoteResponse;
    private GreekTextView errorTextView, holdingValueText, currentHoldingText, holding_MTM_text;
    ArrayList<watchlistModel> watchlistModelsArr = new ArrayList<>();
    private String userType = "";
    watchlistModel model;
    private final ArrayList<String> symbolsToUnsubscribe = new ArrayList<>();
    private ArrayList<HashMap<String, String>> hashArray;
    View dematHoldingView;
    private AlertDialog levelDialog;

    // Fetches the Watch List Group and Scrips.
    private void sendWatchlistRequest() {
        showProgress();
        PortfolioGetUserWatchListRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getSessionId(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dematHoldingView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_holding).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_holding).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_DEMATHOLDING_SINGLE_SCREEN;
        AccountDetails.AutoRefreshForDemat = true;
        setUp();
        return dematHoldingView;

    }

    private void setUp() {
        setUpView(dematHoldingView);
        setupAdapter();
    }

    private void setUpView(View dematHoldingView) {
        setAppTitle(getClass().toString(), "Demat Holdings");
        errorTextView = dematHoldingView.findViewById(R.id.errorHeader);
        positionList = dematHoldingView.findViewById(R.id.lv_future);

        symText = dematHoldingView.findViewById(R.id.row11);
        ltpSort = dematHoldingView.findViewById(R.id.row14);
        mtmText = dematHoldingView.findViewById(R.id.row24);


        holdingValueText = dematHoldingView.findViewById(R.id.holdingValueText);
        currentHoldingText = dematHoldingView.findViewById(R.id.currentHoldingText);
        holding_MTM_text = dematHoldingView.findViewById(R.id.holding_MTM_text);


        holdingValue = dematHoldingView.findViewById(R.id.holding_value);
        currentHolding = dematHoldingView.findViewById(R.id.curr_holding_value);
        holdingMtm = dematHoldingView.findViewById(R.id.holding_MTM);


        int textColor = AccountDetails.getTextColorDropdown();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            holdingValueText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            currentHoldingText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            holding_MTM_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            holdingValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            currentHolding.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            holdingMtm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

            holdingValueText.setTextColor(getResources().getColor(textColor));
            currentHoldingText.setTextColor(getResources().getColor(textColor));
            holding_MTM_text.setTextColor(getResources().getColor(textColor));

            holdingValue.setTextColor(getResources().getColor(textColor));
            currentHolding.setTextColor(getResources().getColor(textColor));
            holdingMtm.setTextColor(getResources().getColor(textColor));
        }


        symText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (symAsc) {
                    sortBySymbol(false, "Symbol", true);
                    symAsc = false;
                } else {
                    sortBySymbol(false, "Symbol", false);
                    symAsc = true;
                }
                streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
                getVisibleSymbolTokens(positionList.getFirstVisiblePosition(), positionList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });


        ltpSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ltpAsc) {
                    sortBySymbol(true, "LTP", true);
                    ltpAsc = false;
                } else {
                    sortBySymbol(true, "LTP", false);
                    ltpAsc = true;
                }
                streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
                getVisibleSymbolTokens(positionList.getFirstVisiblePosition(), positionList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });


        mtmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mtmAsc) {
                    sortBySymbol(true, "MTM", true);
                    mtmAsc = false;
                } else {
                    sortBySymbol(true, "MTM", false);
                    mtmAsc = true;
                }
                streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
                getVisibleSymbolTokens(positionList.getFirstVisiblePosition(), positionList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });

    }


    public void setupAdapter() {
        gainerAdapter = new TopGainerAdapter(getMainActivity(), new ArrayList<DematHolding>());
        positionList.setAdapter(gainerAdapter);
        positionList.setOnItemClickListener(positionsItemSelectionListener);
        sendDematHoldingRequest(0);

        positionList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case 1: {
                        break;
                    }
                    case 0: {
                        streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
                        sendStreamingRequest();
                        break;
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                getVisibleScrollSymbolTokens(firstVisibleItem, visibleItemCount);
            }

        });

    }


    private void sendStreamingRequest() {
        symbolsToUnsubscribe.clear();
        for (int i = 0; i < visibleSymbolTable.size(); i++) {
            symbolsToUnsubscribe.add(visibleSymbolTable.get(i));
        }
        if (symbolsToUnsubscribe != null) {
            streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
        }
        streamController.sendStreamingRequest(getMainActivity(), visibleSymbolTable, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", visibleSymbolTable);
    }


    private final AdapterView.OnItemClickListener positionsItemSelectionListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            rowData = gainerAdapter.getItem(position);
            View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_four_actions, null);
            GreekTextView view1 = layout.findViewById(R.id.pop_quote);
            view1.setText("Add to Watchlist");
            GreekTextView view2 = layout.findViewById(R.id.pop_advice);
            view2.setText(R.string.POPUP_SCRIPT_QUOTE);
            view2.setVisibility(View.GONE);
               /* TextView view3 = (TextView) layout.findViewById(R.id.pop_chart);
            //    view3.setText("Get Chart");
                view3.setText(R.string.POPUP_SCRIPT_CHARTS);*/
            GreekTextView view3 = layout.findViewById(R.id.pop_trade);
            //    view4.setText("Trade");
            view3.setText(R.string.POPUP_SCRIPT_TRADE);
            GreekTextView view4 = layout.findViewById(R.id.pop_trade_sell);
            //    view4.setText("Trade");
            view4.setText("Sell Order");

            view1.setOnClickListener(DematHoldingFragment.this);
            view2.setOnClickListener(DematHoldingFragment.this);
            view3.setOnClickListener(DematHoldingFragment.this);
            view4.setOnClickListener(DematHoldingFragment.this);

            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            builder.setView(layout);
            levelDialog = builder.create();
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }
        }
    };

    private void sortBySymbol(boolean isDouble, String sortCol, boolean ascending) {
        if (isDouble) {
            ArrayList<Double> out = new ArrayList<>();
            Double val = null;
            for (int j = 0; j < gainerAdapter.getCount(); j++) {

                DematHolding holding = gainerAdapter.getItem(j);
                if (sortCol.equalsIgnoreCase("LTP")) {
                    val = Double.parseDouble(holding.getLTP());
                } else if (sortCol.equalsIgnoreCase("MTM")) {
                    val = Double.parseDouble(holding.getMTM());
                }
                out.add(val);
            }

            Collections.sort(out, new Comparator<Double>() {
                public int compare(Double c1, Double c2) {
                    return c1.compareTo(c2);
                }
            });
            if (ascending) {
                Collections.reverse(out);
            }

            ArrayList<DematHolding> tempHashArray1 = new ArrayList<>();
            tempHashArray1.clear();
            tempHashArray1.addAll(gainerAdapter.getItems());
            gainerAdapter.clear();

            for (int i = 0; i < out.size(); i++) {
                Double sortedValue1 = out.get(i);
                Double sortColVal = null;
                for (int j = 0; j < tempHashArray1.size(); j++) {
                    // LinkedHashMap sortLinkedMap = (LinkedHashMap) tempHashArray1.get(j);
                    DematHolding sortLinkedMap = tempHashArray1.get(j);
                    if (sortCol.equalsIgnoreCase("LTP")) {
                        sortColVal = Double.valueOf(sortLinkedMap.getLTP());
                    } else if (sortCol.equalsIgnoreCase("MTM")) {
                        sortColVal = Double.valueOf(sortLinkedMap.getMTM());
                    }
                    // Double sortColValue = Double.valueOf(sortColVal);
                    int retval = sortColVal.compareTo(sortedValue1);
                    // int retval = Double.compare(Double.valueOf(sortColVal), Double.valueOf(sortedValue1));
                    if (retval == 0) {

                        //gainerAdapter.addItem(sortLinkedMap);
                        if (!sortLinkedMap.getNSEToken().equalsIgnoreCase("0")) {
                            if (!gainerAdapter.containsSymbol(sortLinkedMap.getNSEToken())) {
                                gainerAdapter.addSymbol(sortLinkedMap.getNSEToken());
                                gainerAdapter.addItem(sortLinkedMap);
                            }

                        } else {
                            /* gainerAdapter.addSymbol(sortLinkedMap.getBSEToken());*/
                            if (!gainerAdapter.containsSymbol(sortLinkedMap.getBSEToken())) {
                                gainerAdapter.addSymbol(sortLinkedMap.getBSEToken());
                                gainerAdapter.addItem(sortLinkedMap);
                            }
                        }
                    }
                }
            }

            gainerAdapter.notifyDataSetChanged();


        } else {
            ArrayList<String> out = new ArrayList<>();
            out.clear();
            for (int j = 0; j < gainerAdapter.getCount(); j++) {
                //LinkedHashMap linkedHashMap = (LinkedHashMap) hashArray.get(j);
                DematHolding holding = gainerAdapter.getItem(j);
                String val = holding.getNSETradeSymbol();
                out.add(val);
            }

            Collections.sort(out, new Comparator<String>() {
                public int compare(String c1, String c2) {
                    return c1.compareTo(c2);
                }
            });
            if (ascending) {
                Collections.reverse(out);
            }

            ArrayList<DematHolding> tempHashArray1 = new ArrayList<>();
            tempHashArray1.clear();
            tempHashArray1.addAll(gainerAdapter.getItems());


            gainerAdapter.clear();

            for (int i = 0; i < out.size(); i++) {
                String sortedValue1 = out.get(i);
                for (int j = 0; j < tempHashArray1.size(); j++) {
                    DematHolding sortLinkedMap = tempHashArray1.get(j);
                    String sortColVal = (String) sortLinkedMap.getNSETradeSymbol();
                    int retval = sortColVal.compareTo(sortedValue1);
                    if (retval == 0) {
                        if (!sortLinkedMap.getNSEToken().equalsIgnoreCase("0")) {
                            if (!gainerAdapter.containsSymbol(sortLinkedMap.getNSEToken())) {
                                gainerAdapter.addSymbol(sortLinkedMap.getNSEToken());
                                gainerAdapter.addItem(sortLinkedMap);
                            }

                        } else {
                            if (!gainerAdapter.containsSymbol(sortLinkedMap.getBSEToken())) {
                                gainerAdapter.addSymbol(sortLinkedMap.getBSEToken());
                                gainerAdapter.addItem(sortLinkedMap);
                            }
                        }
                    }
                }
            }

            gainerAdapter.notifyDataSetChanged();
        }


        //Cancel last callback if still waiting
        //handler.removeCallbacks(sortCallback);
        //handler.postDelayed(sortCallback, 1000);
    }

    private void getVisibleSymbolTokens(int firstVisibleItem, int totalVisibleCount) {
        visibleSymbolTable.clear();
        if (gainerAdapter.getSymbolTable().size() > 0) {
            int totalCount = firstVisibleItem + totalVisibleCount;
            for (int i = firstVisibleItem; i <= totalVisibleCount; i++) {
                if(gainerAdapter.getSymbolTable().size() > totalVisibleCount) {
                    visibleSymbolTable.add(gainerAdapter.getSymbolTable().get(i));
                }
            }
        }
    }

    private void getVisibleScrollSymbolTokens(int firstVisibleItem, int totalVisibleCount) {

        visibleSymbolTable.clear();
        if (gainerAdapter.getSymbolTable().size() > 0) {
            int totalCount = firstVisibleItem + totalVisibleCount;
            for (int i = firstVisibleItem; i < totalCount; i++) {
                visibleSymbolTable.add(gainerAdapter.getSymbolTable().get(i));
            }
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();//Add To Watchlist
        if (id == R.id.pop_quote) {//TODO
            if (GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER) {
                sendWatchlistRequest();
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        Intent i = new Intent(getMainActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });
            }
            levelDialog.dismiss();
            //Get Quote
        } else if (id == R.id.pop_advice) {//TODO
            Bundle args2 = new Bundle();
            args2.putString(QuotesFragment.SCRIP_NAME, rowData.getScripName());
            args2.putString("Description", rowData.getScripName());
            args2.putString(QuotesFragment.EXCHANGE_NAME, getExchange(rowData.getNSEToken()));
            args2.putString(QuotesFragment.TOKEN, rowData.getNSEToken());
            args2.putString(QuotesFragment.ASSET_TYPE, getAssetType(rowData.getNSEToken()));
            args2.putString(QuotesFragment.UNIQUE_ID, rowData.getUniqueID());
            args2.putString(QuotesFragment.TRADE_SYMBOL, rowData.getNSETradeSymbol());
            args2.putString("Lots", rowData.getLot());
            args2.putString(QuotesFragment.TICK_SIZE, rowData.getTickSize());
            args2.putString(QuotesFragment.MULTIPLIER, rowData.getMultiplier());
            args2.putString(QuotesFragment.INST_TYPE, rowData.getInstrumentType());
            args2.putString(QuotesFragment.FROM_PAGE, "Demat");
            args2.putString("Token", rowData.getNSEToken());
            navigateTo(NAV_TO_QUOTES_SCREEN, args2, true);
            levelDialog.dismiss();
            //Trade
        } else if (id == R.id.pop_trade) {
            if (!rowData.getNSEToken().equals("") && !rowData.getBSEToken().equals("")) {
                if ((!rowData.getNSEToken().equalsIgnoreCase("0")) && (!rowData.getBSEToken().equalsIgnoreCase("0"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                    builder.setTitle("Select an exchange for trading").setCancelable(true).setItems(optionsArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Action", "buy");
                                bundle.putString("Token", rowData.getBSEToken());
                                bundle.putString("SymbolName", rowData.getScripName());
                                bundle.putString("Lots", rowData.getLot());
                                bundle.putString("AssetType", getAssetType(rowData.getBSEToken()));
                                bundle.putString("UniqueId", rowData.getUniqueID());
                                bundle.putString("holdingProduct", rowData.getProduct());
                                bundle.putString("TradeSymbol", rowData.getBSETradeSymbol());
                                bundle.putString("InstType", rowData.getInstrumentType());
                                bundle.putString("ExchangeName", "BSE");
                                bundle.putString("AvailableForSell", rowData.getAvailableForSell());
                                bundle.putString("TickSize", rowData.getTickSize());
                                bundle.putString("Multiplier", rowData.getMultiplier());

                                bundle.putBoolean("isFromDemat", true);

                                navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);

                            } else if (which == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Action", "buy");
                                bundle.putString("Token", rowData.getNSEToken());
                                bundle.putString("SymbolName", rowData.getScripName());
                                bundle.putString("Lots", rowData.getLot());
                                bundle.putString("AssetType", getAssetType(rowData.getNSEToken()));
                                bundle.putString("UniqueId", rowData.getUniqueID());
                                bundle.putString("TradeSymbol", rowData.getNSETradeSymbol());
                                bundle.putString("InstType", rowData.getInstrumentType());
                                bundle.putString("ExchangeName", "NSE");
                                bundle.putBoolean("isFromDemat", true);
                                bundle.putString("AvailableForSell", rowData.getAvailableForSell());
                                bundle.putString("TickSize", rowData.getTickSize());
                                bundle.putString("Multiplier", rowData.getMultiplier());

                                navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);

                            }

                        }
                    }).show();
                } else if (rowData.getNSEToken().equalsIgnoreCase("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Action", "buy");
                    bundle.putString("Token", rowData.getBSEToken());
                    bundle.putString("SymbolName", rowData.getScripName());
                    bundle.putString("Lots", rowData.getLot());
                    bundle.putString("AssetType", getAssetType(rowData.getBSEToken()));
                    bundle.putString("UniqueId", rowData.getUniqueID());
                    bundle.putString("TradeSymbol", rowData.getBSETradeSymbol());
                    bundle.putString("InstType", rowData.getInstrumentType());
                    bundle.putString("ExchangeName", "BSE");
                    bundle.putBoolean("isFromDemat", true);
                    bundle.putString("TickSize", rowData.getTickSize());
                    bundle.putString("Multiplier", rowData.getMultiplier());
                    bundle.putString("LTP", rowData.getLTP());

                    bundle.putString("AvailableForSell", rowData.getAvailableForSell());

                    navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);
                } else if (rowData.getBSEToken().equalsIgnoreCase("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Action", "buy");
                    bundle.putString("Token", rowData.getNSEToken());
                    bundle.putString("SymbolName", rowData.getScripName());
                    bundle.putString("Lots", rowData.getLot());
                    bundle.putString("AssetType", getAssetType(rowData.getNSEToken()));
                    bundle.putString("UniqueId", rowData.getUniqueID());
                    bundle.putString("TradeSymbol", rowData.getNSETradeSymbol());
                    bundle.putString("InstType", rowData.getInstrumentType());
                    bundle.putString("ExchangeName", "NSE");
                    bundle.putBoolean("isFromDemat", true);
                    bundle.putString("TickSize", rowData.getTickSize());
                    bundle.putString("Multiplier", rowData.getMultiplier());
                    bundle.putString("LTP", rowData.getLTP());
                    bundle.putString("AvailableForSell", rowData.getAvailableForSell());

                    navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);
                }
            }
            levelDialog.dismiss();
        } else if (id == R.id.pop_trade_sell) {
            if (!rowData.getNSEToken().equals("") && !rowData.getBSEToken().equals("")) {

                if ((!rowData.getNSEToken().equalsIgnoreCase("0"))
                        && (!rowData.getBSEToken().equalsIgnoreCase("0"))

                        && (!rowData.getBSEToken().equalsIgnoreCase(rowData.getNSEToken()))) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                    builder.setTitle("Select an exchange for trading").setCancelable(true).setItems(optionsArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Action", "sell");
                                bundle.putString("Token", rowData.getBSEToken());
                                bundle.putString("SymbolName", rowData.getScripName());
                                bundle.putString("Lots", rowData.getLot());
                                bundle.putString("AssetType", getAssetType(rowData.getBSEToken()));
                                bundle.putString("UniqueId", rowData.getUniqueID());
                                bundle.putString("holdingProduct", rowData.getProduct());
                                bundle.putString("TradeSymbol", rowData.getBSETradeSymbol());
                                bundle.putString("InstType", rowData.getInstrumentType());
                                bundle.putString("ExchangeName", "BSE");
                                bundle.putString("AvailableForSell", rowData.getAvailableForSell());
                                bundle.putString("TickSize", rowData.getTickSize());
                                bundle.putString("Multiplier", rowData.getMultiplier());

                                bundle.putBoolean("isFromDemat", true);

                                navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);

                            } else if (which == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Action", "sell");
                                bundle.putString("Token", rowData.getNSEToken());
                                bundle.putString("SymbolName", rowData.getScripName());
                                bundle.putString("Lots", rowData.getLot());
                                bundle.putString("AssetType", getAssetType(rowData.getNSEToken()));
                                bundle.putString("UniqueId", rowData.getUniqueID());
                                bundle.putString("TradeSymbol", rowData.getNSETradeSymbol());
                                bundle.putString("InstType", rowData.getInstrumentType());
                                bundle.putString("ExchangeName", "NSE");
                                bundle.putBoolean("isFromDemat", true);
                                bundle.putString("AvailableForSell", rowData.getAvailableForSell());
                                bundle.putString("TickSize", rowData.getTickSize());
                                bundle.putString("Multiplier", rowData.getMultiplier());

                                navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);

                            }

                        }
                    }).show();

                } else if (rowData.getNSEToken().equalsIgnoreCase("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Action", "sell");
                    bundle.putString("Token", rowData.getBSEToken());
                    bundle.putString("SymbolName", rowData.getScripName());
                    bundle.putString("Lots", rowData.getLot());
                    bundle.putString("AssetType", getAssetType(rowData.getBSEToken()));
                    bundle.putString("UniqueId", rowData.getUniqueID());
                    bundle.putString("TradeSymbol", rowData.getBSETradeSymbol());
                    bundle.putString("InstType", rowData.getInstrumentType());
                    bundle.putString("ExchangeName", "BSE");
                    bundle.putBoolean("isFromDemat", true);
                    bundle.putString("TickSize", rowData.getTickSize());
                    bundle.putString("Multiplier", rowData.getMultiplier());
                    bundle.putString("LTP", rowData.getLTP());

                    bundle.putString("AvailableForSell", rowData.getAvailableForSell());

                    navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);
                } else if (rowData.getBSEToken().equalsIgnoreCase("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Action", "sell");
                    bundle.putString("Token", rowData.getNSEToken());
                    bundle.putString("SymbolName", rowData.getScripName());
                    bundle.putString("Lots", rowData.getLot());
                    bundle.putString("AssetType", getAssetType(rowData.getNSEToken()));
                    bundle.putString("UniqueId", rowData.getUniqueID());
                    bundle.putString("TradeSymbol", rowData.getNSETradeSymbol());
                    bundle.putString("InstType", rowData.getInstrumentType());
                    bundle.putString("ExchangeName", "NSE");
                    bundle.putBoolean("isFromDemat", true);
                    bundle.putString("TickSize", rowData.getTickSize());
                    bundle.putString("Multiplier", rowData.getMultiplier());
                    bundle.putString("LTP", rowData.getLTP());

                    bundle.putString("AvailableForSell", rowData.getAvailableForSell());

                    navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);
                }
            }
            levelDialog.dismiss();
        }


    }


    public void sendDematHoldingRequest(int position) {
        getMainActivity();
        showProgress();
        String holding_type = "";
        isWaitingForResponseOnPTR = true;

        if (position == 1) {
            holding_type = "dp";
        } else if (position == 2) {
            holding_type = "pool";
        } else if (position == 0) {
            holding_type = "demat";
        }
        WSHandler.getRequest(getMainActivity(), "getDematHoldingDetails" + "?ClientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&SessionId=" + AccountDetails.getSessionId(getMainActivity()) + "&holding_type=" + holding_type + "&gscid=" + AccountDetails.getUsername(getMainActivity()), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    dematHoldingResponse = new TradeDematHoldingResponse();

                    if (response.has("data")) {
                        JSONArray ja1 = response.getJSONArray("data");

                        for (int i = 0; i < ja1.length(); ++i) {
                            JSONObject o = (JSONObject) ja1.get(i);

                            DematHolding dmatholding = new DematHolding();
                            dmatholding.fromJSON(o);

                            if (!dmatholding.getNSEToken().equalsIgnoreCase("0") && !dmatholding.getBSEToken().equalsIgnoreCase("0")) {
                                if (dmatholding.getBSEToken().equalsIgnoreCase(dmatholding.getNSEToken())) {


                                    if (getExchange(dmatholding.getNSEToken()).equalsIgnoreCase("nse")) {
                                        dmatholding.setBSEToken("0");
                                    } else if (getExchange(dmatholding.getNSEToken()).equalsIgnoreCase("bse")) {
                                        dmatholding.setNSEToken("0");
                                    }
                                }
                            }
                           /* String mtm = "";
                            if (Integer.parseInt(dmatholding.getHoldingQty()) == 0) {
                                mtm = String.format("%.2f", (Double.parseDouble(dmatholding.getSellValue()) - (Double.parseDouble(dmatholding.getBuyValue()) + Double.parseDouble(dmatholding.getPrevValue()))));
                                dmatholding.setMTM(mtm);
                                //holder.row24.setText(String.format("%.2f",(Double.parseDouble(model.getSellPrice()) * Double.parseDouble(model.getPrevQty()))-Double.parseDouble(model.getPrevValue())));
                            } else {
                                mtm = String.format("%.2f", ((Double.parseDouble(dmatholding.getLTP()) - Double.parseDouble(dmatholding.getHoldingPrice())) * Integer.parseInt(dmatholding.getHoldingQty())));
                                dmatholding.setMTM(mtm);
                            }*/


                            dematHoldingResponse.getDematHolding().add(dmatholding);

                            if (!dematHoldingResponse.getDematHolding().get(i).getNSEToken().equalsIgnoreCase("0")) {
                                gainerAdapter.addSymbol(dematHoldingResponse.getDematHolding().get(i).getNSEToken());

                            } else {
                                gainerAdapter.addSymbol(dematHoldingResponse.getDematHolding().get(i).getBSEToken());
                            }


                            if (dematHoldingResponse.getDematHolding().get(i).getLTP().equalsIgnoreCase("0")) {
                                DematList dematList = new DematList();


                                if (!dematHoldingResponse.getDematHolding().get(i).getNSEToken().equalsIgnoreCase("0")) {
                                    //gainerAdapter.addSymbol(dematHoldingResponse.getDematHolding().get(i).getNSEToken());
                                    dematList.setToken(dematHoldingResponse.getDematHolding().get(i).getNSEToken());

                                } else {
                                    //gainerAdapter.addSymbol(dematHoldingResponse.getDematHolding().get(i).getBSEToken());
                                    dematList.setToken(dematHoldingResponse.getDematHolding().get(i).getBSEToken());
                                }
                                dematLtpDetails.add(dematList);
                            }

                        }

                        if (dematLtpDetails.size() != 0) {
                            DematLtpDetailsRequest.sendRequest(dematLtpDetails, getMainActivity(), serviceResponseHandler);
                        }
                    }


                    populateHoldingsList();
                    sendStreamingRequest();
                    //refreshComplete();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            getVisibleSymbolTokens(positionList.getFirstVisiblePosition(), positionList.getLastVisiblePosition());
                            sendStreamingRequest();
                        }
                    }, 1000);

                    //setAppTitle(getClass().toString(), "Demat Holdings");
                    hideProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {

                hideProgress();
            }
        });

        //  }
    }

    private void sendSaveWatchListRequest(String group, String groupType) {
        showProgress();
        AddSymbolToGroupRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getUsername(getMainActivity()), group, AccountDetails.getToken(getMainActivity()), symbolList, symbolList.size()+"",groupType, getMainActivity(), serviceResponseHandler);
    }

    private void showAddToWatchlistPopup() {
        View promptsView = LayoutInflater.from(getMainActivity()).inflate(R.layout.dialog_add_to_watchlist, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getMainActivity());
        alertDialogBuilder.setView(promptsView);

        Button ok_btn = promptsView.findViewById(R.id.button_ok_transc);
        Button cancel_btn = promptsView.findViewById(R.id.button_cancel_transc);

        ok_btn.setVisibility(View.GONE);
        cancel_btn.setVisibility(View.GONE);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                symbolList.clear();
                List<String> exchangeList = new ArrayList<>();
                List<String> tradeSymbols = new ArrayList<>();
                List<String> token = new ArrayList<>();
                List<String> assetType = new ArrayList<>();

                if (userType.equals("user")) {
                    for (SymbolList list : symbolLists) {
                        exchangeList.add(getExchange(list.getToken()));
                        tradeSymbols.add(list.getTradeSymbol());
                        token.add(list.getToken());
                        assetType.add(getAssetType(list.getToken()));

                    }
                }
                if (!rowData.getNSEToken().equalsIgnoreCase("0")) {
                    if (token.contains(rowData.getNSEToken())) {
                        GreekDialog.alertDialog(getActivity(), 0, getString(GREEK), getString(R.string.GR_SYMBOL_EXIST_MSG), "Ok", false, null);
                    } else {
                        SymbolDetail detail = new SymbolDetail();
                        detail.setExchange(getExchange(rowData.getNSEToken()));
                        detail.setTradeSymbol(rowData.getNSETradeSymbol());
                        detail.setToken(rowData.getNSEToken());
                        detail.setAssetType(getAssetType(rowData.getNSEToken()));
                        symbolList.add(detail);
                        sendSaveWatchListRequest(selectedGrp, selectedGrpType);
                    }
                } else {
                    if (token.contains(rowData.getBSEToken())) {
                        GreekDialog.alertDialog(getActivity(), 0, getString(GREEK), getString(R.string.GR_SYMBOL_EXIST_MSG), "Ok", false, null);
                    } else {
                        SymbolDetail detail = new SymbolDetail();
                        detail.setExchange(getExchange(rowData.getBSEToken()));
                        detail.setTradeSymbol(rowData.getBSETradeSymbol());
                        detail.setToken(rowData.getBSEToken());
                        detail.setAssetType(getAssetType(rowData.getBSEToken()));
                        symbolList.add(detail);
                        sendSaveWatchListRequest(selectedGrp, selectedGrpType);
                    }
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialogBuilder.setTitle(GREEK);
        alertDialog = alertDialogBuilder.create();

        final Spinner mSpinner = promptsView.findViewById(R.id.grpSpinner);
        groupsAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), groupName);
        groupsAdapter.setDropDownViewResource(R.layout.custom_spinner);
        mSpinner.setAdapter(groupsAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<GetUserwatchlist> getUserwatchlists = getWatchListResponse.getGetUserwatchlist();
                GetUserwatchlist watchlist = getUserwatchlists.get(position);
                userType = watchlist.getWatchtype();
                symbolLists = watchlist.getSymbolList();
                selectedGrp = mSpinner.getSelectedItem().toString();
                selectedGrpType = groupType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void getWatchlistGroupName() {
        if (getWatchListResponse != null) {
            List<GetUserwatchlist> getUserwatchlists = getWatchListResponse.getGetUserwatchlist();
            groupName.clear();
            groupType.clear();

            for (GetUserwatchlist watchlist : getUserwatchlists) {
                groupName.add(watchlist.getWatchlistName());
                groupType.add(watchlist.getWatchtype());
            }
            groupsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                if (getWatchListResponse.getGetUserwatchlist().size() != 0) {
                    showAddToWatchlistPopup();
                    getWatchlistGroupName();
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "No group created.Do you want to create the group?", "Yes", "No", true, new GreekDialog.DialogListener() {


                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            //Toast.makeText(getMainActivity(),action.name(), Toast.LENGTH_SHORT).show();

                            if (action.name().equalsIgnoreCase("ok")) {
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("Groups", groupNameList);
                                navigateTo(NAV_TO_ADD_NEW_PORTFOLIO_SCREEN, bundle, true);
                            } else if (action.name().equalsIgnoreCase("cancel")) {
                                //slertDialog.cancel();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();
        } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && ADD_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                alertDialog.dismiss();
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_ADDED_SUCCESS_MSG), "OK", true, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();

        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "getDematHoldingLtp".equals(jsonResponse.getServiceName())) {
            try {
                dematLtpDetailsResponse = (DematLtpDetailsResponse) jsonResponse.getResponse();
                updateLtp(dematLtpDetailsResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();

        }
    }

    private void updateLtp(DematLtpDetailsResponse dematLtpDetailsResponse) {

        try {

            for (int i = 0; i < dematLtpDetailsResponse.getDematLtpList().size(); i++) {
                if (gainerAdapter.containsSymbol(dematLtpDetailsResponse.getDematLtpList().get(i).getToken())) {
                    boolean notifyNeeded = false;

                    DematHolding data = gainerAdapter.getItem(gainerAdapter.indexOf(dematLtpDetailsResponse.getDematLtpList().get(i).getToken()));
                    String color = null;
                    if (((Integer.valueOf(dematLtpDetailsResponse.getDematLtpList().get(i).getToken()) >= 502000000) && (Integer.valueOf(dematLtpDetailsResponse.getDematLtpList().get(i).getToken()) <= 502999999)) || ((Integer.valueOf(dematLtpDetailsResponse.getDematLtpList().get(i).getToken()) >= 1302000000) && (Integer.valueOf(dematLtpDetailsResponse.getDematLtpList().get(i).getToken()) <= 1302999999))) {
                        data.setLTP(String.format("%.4f", Double.parseDouble(dematLtpDetailsResponse.getDematLtpList().get(i).getLtp())));

                    } else {
                        data.setLTP(String.format("%.2f", Double.parseDouble(dematLtpDetailsResponse.getDematLtpList().get(i).getLtp())));
                    }


                    gainerAdapter.updateData(gainerAdapter.indexOf(dematLtpDetailsResponse.getDematLtpList().get(i).getToken()), data);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gainerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onFragmentResume() {
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 3);
        AccountDetails.currentFragment = NAV_TO_DEMATHOLDING_SINGLE_SCREEN;
        if (AccountDetails.AutoRefreshForDemat) {
            setUp();
        }
        super.onFragmentResume();
    }

    private String getAssetType(String token) {
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
        //  return "";
    }

    private void populateHoldingsList() {
        gainerAdapter.clear();
        totalMtm = 0;
        totalholdingValue = 0;
        totalcurrentHoldingValue = 0;
        if (dematHoldingResponse != null) {
            List<DematHolding> dmatHoldings = dematHoldingResponse.getDematHolding();
            gainerAdapter.notifyDataSetChanged();
            if (dmatHoldings.size() > 0) {
                gainerAdapter.setData(dmatHoldings);
                for (DematHolding dematHolding : dmatHoldings) {

                    String mtm;
                    if (Integer.parseInt(dematHolding.getHoldingQty()) == 0) {
                        mtm = String.format("%.2f", (Double.parseDouble(dematHolding.getSellValue()) - (Double.parseDouble(dematHolding.getBuyValue()) + Double.parseDouble(dematHolding.getPrevValue()))));
                        dematHolding.setMTM(mtm);
                        //holder.row24.setText(String.format("%.2f",(Double.parseDouble(model.getSellPrice()) * Double.parseDouble(model.getPrevQty()))-Double.parseDouble(model.getPrevValue())));
                    } else {
                        mtm = String.format("%.2f", ((Double.parseDouble(dematHolding.getLTP()) - Double.parseDouble(dematHolding.getHoldingPrice())) * Integer.parseInt(dematHolding.getHoldingQty())));
                        dematHolding.setMTM(mtm);
                    }

                    totalMtm += Double.parseDouble(mtm);
                    totalholdingValue += Double.parseDouble(dematHolding.getHoldingValue());
                    totalcurrentHoldingValue += Integer.parseInt(dematHolding.getHoldingQty()) * Double.parseDouble(dematHolding.getLTP());


                    if (!dematHolding.getNSEToken().equalsIgnoreCase("0")) {
                        gainerAdapter.addSymbol(dematHolding.getNSEToken());
                    } else {
                        gainerAdapter.addSymbol(dematHolding.getBSEToken());
                    }
                }

                holdingValue.setText(String.format("%.2f", totalholdingValue));
                holdingMtm.setText(String.format("%.2f", totalMtm));
                currentHolding.setText(String.format("%.2f", totalcurrentHoldingValue));

                gainerAdapter.notifyDataSetChanged();
                //noDataFound(netPosAdapter);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        //  refresh();
    }

    @Override
    public void onFragmentPause() {
        if (streamController != null) {
            streamController.pauseStreaming(getActivity(), "ltpinfo", visibleSymbolTable);
        }
        super.onFragmentPause();
    }

    @Override
    public void onPause() {
        if (streamController != null) {
            streamController.pauseStreaming(getActivity(), "ltpinfo", visibleSymbolTable);
        }
        EventBus.getDefault().unregister(this);
        super.onPause();
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


    public void updateBroadcastData(StreamerBroadcastResponse response) {
        try {

            if (gainerAdapter.containsSymbol(response.getSymbol())) {
                double prevMtm = 0.0, currentMtm = 0.0, prevLtp = 0.0, currentLtp = 0.0;
                boolean notifyNeeded = false;

                DematHolding data = gainerAdapter.getItem(gainerAdapter.indexOf(response.getSymbol()));
                prevLtp = Double.parseDouble(data.getLTP());
                prevMtm = (Double.parseDouble(data.getLTP()) - Double.parseDouble(data.getHoldingPrice())) * Integer.parseInt(data.getHoldingQty());

                String color = null;
                if (!data.getLTP().equals(response.getLast())) {
                    double old = Double.parseDouble(data.getLTP());
                    double newd = Double.parseDouble(response.getLast());
                    if (old < newd) {
                        color = "green";
                    } else if (old > newd) {
                        color = "red";
                    }
                    if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                        data.setLTP(String.format("%.4f", Double.parseDouble(response.getLast())));

                    } else {
                        data.setLTP(String.format("%.2f", Double.parseDouble(response.getLast())));
                    }
                    //data.setColor(color);
                    notifyNeeded = true;
                } else {
                    if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                        data.setLTP(String.format("%.4f", Double.parseDouble(response.getLast())));

                    } else {
                        data.setLTP(String.format("%.2f", Double.parseDouble(response.getLast())));
                    }
                }

                currentLtp = Double.parseDouble(data.getLTP());
                if (Integer.parseInt(data.getHoldingQty()) != 0) {
                    if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                        data.setMTM(String.format("%.4f", ((Double.parseDouble(data.getLTP()) - Double.parseDouble(data.getHoldingPrice())) * Integer.parseInt(data.getHoldingQty()))));

                    } else {
                        data.setMTM(String.format("%.2f", ((Double.parseDouble(data.getLTP()) - Double.parseDouble(data.getHoldingPrice())) * Integer.parseInt(data.getHoldingQty()))));
                    }

                    currentMtm = Double.parseDouble(data.getMTM());
                    totalMtm = totalMtm - prevMtm + currentMtm;
                }


                totalcurrentHoldingValue = totalcurrentHoldingValue - (Integer.parseInt(data.getHoldingQty()) * prevLtp) + (Integer.parseInt(data.getHoldingQty()) * currentLtp);

                holdingMtm.setText(String.format("%.2f", totalMtm));
                currentHolding.setText(String.format("%.2f", totalcurrentHoldingValue));

                gainerAdapter.updateData(gainerAdapter.indexOf(response.getSymbol()), data);

                if (notifyNeeded) {
                    gainerAdapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TopGainerAdapter extends BaseAdapter {
        private final Context mContext;
        private List<DematHolding> topGainerList;
        ArrayList<String> tokenList;


        public TopGainerAdapter(Context context, ArrayList<DematHolding> topGainerList) {
            this.mContext = context;
            this.topGainerList = topGainerList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<DematHolding> gainerList) {
            this.topGainerList = gainerList;
            notifyDataSetChanged();
        }

        public void clear() {
            this.topGainerList.clear();
            this.tokenList.clear();
            notifyDataSetChanged();
        }

        public void addItem(DematHolding Item) {
            topGainerList.add(Item);
        }

        public void addSymbol(String symbol) {
            tokenList.add(symbol);
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public void updateData(int position, DematHolding model) {
            topGainerList.set(position, model);
        }

        public List<DematHolding> getItems() {
            return topGainerList;
        }

        public ArrayList<String> getSymbolTable() {
            return tokenList;
        }

        @Override
        public int getCount() {
            return topGainerList.size();
        }

        @Override
        public DematHolding getItem(int position) {
            return topGainerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_positions, parent, false);
                holder.row11 = convertView.findViewById(R.id.row11);
                holder.row12 = convertView.findViewById(R.id.row12);
                holder.row13 = convertView.findViewById(R.id.row13);
                holder.row14 = convertView.findViewById(R.id.row14);
                holder.row22 = convertView.findViewById(R.id.row22);
                holder.row23 = convertView.findViewById(R.id.row23);
                holder.row24 = convertView.findViewById(R.id.row24);
                convertView.setTag(holder);
            } else {
                holder = (DematHoldingFragment.TopGainerAdapter.Holder) convertView.getTag();
            }

            DematHolding model = getItem(position);
            //Log.d("logggg",String.valueOf(position));

            holder.row11.setText(model.getNSETradeSymbol() + " -" + model.getInstrumentType());
            holder.row12.setText(model.getHoldingQty());
            if (!model.getClose().equalsIgnoreCase("null")) {
                holder.row22.setText(String.format("%.2f", Double.parseDouble(model.getLTP()) - Double.parseDouble(model.getClose())));
            } else {
                holder.row22.setText("0.00");
            }
            if (model.getHoldingPrice().equalsIgnoreCase("")) {
                holder.row13.setText("0.00");
            } else {
                holder.row13.setText(String.format("%.2f", Double.parseDouble(model.getHoldingPrice())));
            }

            if (model.getLTP().equalsIgnoreCase("") || model.getLTP().equalsIgnoreCase("0")) {
                holder.row14.setText("0.00");
            } else {
                holder.row14.setText(String.format("%.2f", Double.parseDouble(model.getLTP())));
            }

            if (model.getHoldingQty().equalsIgnoreCase("")) {
                holder.row23.setText("00.00");
            } else {
                if (Integer.parseInt(model.getHoldingQty()) == 0) {
                    holder.row23.setText("00.00");
                } else {
                    holder.row23.setText(String.format("%.2f", (Double.parseDouble(model.getHoldingValue()))));
                }
            }

            String mtm = "";
            if (Integer.parseInt(model.getHoldingQty()) == 0) {
                mtm = String.format("%.2f", (Double.parseDouble(model.getSellValue()) - (Double.parseDouble(model.getBuyValue()) + Double.parseDouble(model.getPrevValue()))));
                holder.row24.setText(mtm);
                //holder.row24.setText(String.format("%.2f",(Double.parseDouble(model.getSellPrice()) * Double.parseDouble(model.getPrevQty()))-Double.parseDouble(model.getPrevValue())));
            } else {
                mtm = String.format("%.2f", ((Double.parseDouble(model.getLTP()) - Double.parseDouble(model.getHoldingPrice())) * Integer.parseInt(model.getHoldingQty())));
                holder.row24.setText(mtm);
            }

            model.setMTM(mtm);

            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.row11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row14.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row23.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row24.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row22.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                //holder.row23.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.row11.setTextColor(getResources().getColor(textColor));
                holder.row12.setTextColor(getResources().getColor(textColor));
                holder.row13.setTextColor(getResources().getColor(textColor));
                holder.row14.setTextColor(getResources().getColor(textColor));
                holder.row23.setTextColor(getResources().getColor(textColor));
                holder.row24.setTextColor(getResources().getColor(textColor));
                holder.row22.setTextColor(getResources().getColor(textColor));
                //holder.dividerEquityLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));
            }


            return convertView;
        }

        public class Holder {
            GreekTextView row11, row12, row13, row14, row23, row24, row22;
        }
    }
}


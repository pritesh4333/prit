package com.acumengroup.mobile.markets;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.marketsindicesstock.IndicesStock;
import com.acumengroup.greekmain.core.model.marketsindicesstock.MarketsIndicesStockRequest;
import com.acumengroup.greekmain.core.model.marketsindicesstock.MarketsIndicesStockResponse;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.SearchMultiStockDetailsRequest;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.SearchMultiStockDetailsResponse;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.StockDetail;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.StockList;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.watchlistModel;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */
public class IndicesStockFragment extends GreekBaseFragment implements View.OnClickListener {

    private final ArrayList<String> exchangelist = new ArrayList<>();
    private final ArrayList<String> indicesGrpList = new ArrayList<>();
    private final ArrayList<String> indicesNameList = new ArrayList<>();
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private final ArrayList<String> symbolsToUnsubscribe = new ArrayList<>();
    private List<IndianIndice> indices = new ArrayList<>();
    private LinkedHashMap lMap;
    private View stocksView;
    private Spinner exchangeSpinner, stockSpinner;
    private MarketsIndicesStockResponse indicesStockResponse;
    private int selectedIndice;
    private ArrayList<StockList> stockList;
    private boolean isWaitingForResponseOnPTR = false;
    private RelativeLayout showmsgLayout;
    int flag = 0;
    private GreekTextView ltpSort, symText, chgText, perchgText;
    private CustomAdapterLastVisited commonAdapter;
    CustomAdapterLastVisited.Holder holder;
    private ArrayList<HashMap<String, String>> hashArray;
    ArrayList<watchlistModel> watchlistModelsArr = new ArrayList<>();
    watchlistModel model;
    ListView indicesList;
    boolean ltpAsc, symAsc, chgAsc, perchgAsc = false;
    public String exchangeGroupNameStock, NiftyTitleText, selectedExchange;
    GreekTextView errorHeaderTextView;
    private AlertDialog levelDialog;

    private final AdapterView.OnItemSelectedListener optionSelection = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            showmsgLayout.setVisibility(View.GONE);
            selectedIndice = position;
            sendRequest(selectedIndice);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener exchangeSelection = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            indicesGrpList.clear();
            indicesNameList.clear();

            for (IndianIndice indianIndice : indices) {
                if (indianIndice.getExchange().equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString())) {
                    indicesGrpList.add(indianIndice.getToken());
                    indicesNameList.add(indianIndice.getExchange());
                }
            }

            ArrayAdapter<String> spinAdapter1 = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), indicesGrpList);
            spinAdapter1.setDropDownViewResource(R.layout.custom_spinner);
            stockSpinner.setOnItemSelectedListener(optionSelection);
            stockSpinner.setAdapter(spinAdapter1);
            int seletiongrp = -1;
            for (int i = 0; i < indicesGrpList.size(); i++) {
                if (indicesGrpList.get(i).equalsIgnoreCase(NiftyTitleText)) {
                    seletiongrp = i;
                }
            }
            stockSpinner.setSelection(seletiongrp);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private SearchMultiStockDetailsResponse multiStockResponse;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pop_advice) {
            Bundle args = new Bundle();
            args.putString("ScripName", (String) lMap.get("ScriptName"));
            args.putString("Description", (String) lMap.get("description"));
            args.putString("ExchangeName", (String) lMap.get("Exchange"));
            args.putString("Token", (String) lMap.get("Token"));
            args.putString("AssetType", (String) lMap.get("assetType"));
            args.putString("UniqueId", (String) lMap.get("uniqueID"));
            args.putString("TradeSymbol", (String) lMap.get("Symbol"));
            args.putString("Lots", (String) lMap.get("lot"));
            args.putString("TickSize", (String) lMap.get("tickSize"));
            args.putString("Multiplier", (String) lMap.get("multiplier"));
            args.putString("SelectedExp", (String) lMap.get("expiryDate"));
            args.putString("StrikePrice", (String) lMap.get("strickPrice"));
            args.putString("InstType", (String) lMap.get("InstrumentName"));
            args.putString("Expiry", (String) lMap.get("expiryDate"));
            if (lMap.get("InstrumentName").toString().contains("OPT")) {
                args.putString("SelectedOpt", (String) lMap.get("optionType"));
                args.putString("optType", (String) lMap.get("optionType"));
            }
            args.putString("From", "Watchlist");

            navigateTo(NAV_TO_QUOTES_SCREEN, args, true);
            levelDialog.dismiss();
            //Trades
        } else if (id == R.id.pop_trade) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {

                Bundle args2 = new Bundle();
                args2.putString("ScripName", (String) lMap.get("ScriptName"));
                args2.putString("ExchangeName", (String) lMap.get("Exchange"));
                args2.putString("Token", (String) lMap.get("Token"));
                args2.putString("AssetType", (String) lMap.get("assetType"));
                args2.putString("UniqueId", (String) lMap.get("uniqueID"));
                args2.putString("TradeSymbol", (String) lMap.get("Symbol"));
                args2.putString("Lots", (String) lMap.get("lot"));
                args2.putString("TickSize", (String) lMap.get("tickSize"));
                args2.putString("Multiplier", (String) lMap.get("multiplier"));
                args2.putString(TradeFragment.TRADE_ACTION, "buy");
                if (!"equity".equals(lMap.get("assetType"))) args2.putString("InstType", "");

                navigateTo(NAV_TO_TRADE_SCREEN, args2, true);
            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    }
                });
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    }
                });
            }
            levelDialog.dismiss();
        } else if (id == R.id.pop_trade_sell) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {

                Bundle args3 = new Bundle();
                args3.putString("ScripName", (String) lMap.get("ScriptName"));
                args3.putString("ExchangeName", (String) lMap.get("Exchange"));
                args3.putString("Token", (String) lMap.get("Token"));
                args3.putString("AssetType", (String) lMap.get("assetType"));
                args3.putString("UniqueId", (String) lMap.get("uniqueID"));
                args3.putString("TradeSymbol", (String) lMap.get("Symbol"));
                args3.putString("Lots", (String) lMap.get("lot"));
                args3.putString("TickSize", (String) lMap.get("tickSize"));
                args3.putString("Multiplier", (String) lMap.get("multiplier"));
                args3.putString(TradeFragment.TRADE_ACTION, "sell");
                if (!"equity".equals(lMap.get("assetType"))) args3.putString("InstType", "");

                navigateTo(NAV_TO_TRADE_SCREEN, args3, true);
            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    }
                });
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    }
                });
            }
            levelDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stocksView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_indices_stock_new).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_indices_stock_new).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_INDICES_STOCK_SCREEN;
        hideAppTitle();
        setupViews();
        return stocksView;
    }

    private void setupViews() {
        exchangeSpinner = stocksView.findViewById(R.id.col1Spinner);
        stockSpinner = stocksView.findViewById(R.id.col2Spinner);
        ltpSort = stocksView.findViewById(R.id.ltpText);
        symText = stocksView.findViewById(R.id.symbolText);
        chgText = stocksView.findViewById(R.id.changeText);
        perchgText = stocksView.findViewById(R.id.perchangeText);
        indicesList = stocksView.findViewById(R.id.watchlist_list);
        errorHeaderTextView = stocksView.findViewById(R.id.errorHeader);
        showmsgLayout = stocksView.findViewById(R.id.showmsgLayout);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = stocksView.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

        try {
            commonAdapter = new CustomAdapterLastVisited(getMainActivity(), new ArrayList<watchlistModel>());
            indicesList.setAdapter(commonAdapter);

        } catch (Exception e) {
            showmsgLayout.setVisibility(View.VISIBLE);
            errorHeaderTextView.setText("No data available");
            errorHeaderTextView.setVisibility(View.VISIBLE);

        }


        indicesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("Scroll sTate", "==========>>>" + scrollState);
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

        indicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

                lMap = (LinkedHashMap) commonAdapter.getItemHash(position);
                View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_four_actions, null);
                GreekTextView view1 = layout.findViewById(R.id.pop_quote);
                //    view1.setText("Delete Script");
                view1.setText(R.string.POPUP_SCRIPT_REMOVE);
                view1.setVisibility(View.GONE);
                GreekTextView view2 = layout.findViewById(R.id.pop_advice);
                //  view2.setText("Get Quote");
                view2.setText(R.string.POPUP_SCRIPT_QUOTE);
                GreekTextView view3 = layout.findViewById(R.id.pop_trade_sell);
                //   view3.setText("Get Chart");
                view3.setText(R.string.POPUP_SCRIPT_TRADE_SELL);
                GreekTextView view4 = layout.findViewById(R.id.pop_trade);
                //   view4.setText("Trade");
                view4.setText(R.string.POPUP_SCRIPT_TRADE);

                view1.setOnClickListener(IndicesStockFragment.this);
                view2.setOnClickListener(IndicesStockFragment.this);
                view3.setOnClickListener(IndicesStockFragment.this);
                view4.setOnClickListener(IndicesStockFragment.this);

                builder.setView(layout);
                levelDialog = builder.create();
                if (!levelDialog.isShowing()) {
                    levelDialog.show();
                }
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
                getVisibleSymbolTokens(indicesList.getFirstVisiblePosition(), indicesList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });

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
                getVisibleSymbolTokens(indicesList.getFirstVisiblePosition(), indicesList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });

        chgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chgAsc) {
                    sortBySymbol(true, "Chg (Rs)", true);
                    chgAsc = false;
                } else {
                    sortBySymbol(true, "Chg (Rs)", false);
                    chgAsc = true;
                }
                streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
                getVisibleSymbolTokens(indicesList.getFirstVisiblePosition(), indicesList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });

        perchgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (perchgAsc) {
                    sortBySymbol(true, "Chg (%)", true);
                    perchgAsc = false;
                } else {
                    sortBySymbol(true, "Chg (%)", false);
                    perchgAsc = true;
                }
                streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
                getVisibleSymbolTokens(indicesList.getFirstVisiblePosition(), indicesList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });
        getFromIntent();
    }


    private void sortBySymbol(boolean isDouble, String sortCol, boolean ascending) {
        if (isDouble) {
            ArrayList<Double> out = new ArrayList<>();

            for (int j = 0; j < hashArray.size(); j++) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) hashArray.get(j);
                String val = (String) linkedHashMap.get(sortCol);
                Double value = Double.valueOf(val);
                out.add(value);
            }

            Collections.sort(out, new Comparator<Double>() {
                public int compare(Double c1, Double c2) {
                    return c1.compareTo(c2);
                }
            });
            if (ascending) {
                Collections.reverse(out);
            }

            ArrayList<HashMap<String, String>> tempHashArray = (ArrayList<HashMap<String, String>>) hashArray.clone();
            commonAdapter.clear();

            for (int i = 0; i < out.size(); i++) {
                Double sortedValue = out.get(i);
                for (int j = 0; j < tempHashArray.size(); j++) {
                    LinkedHashMap sortLinkedMap = (LinkedHashMap) tempHashArray.get(j);
                    String sortColVal = (String) sortLinkedMap.get(sortCol);
                    Double sortColValue = Double.valueOf(sortColVal);
                    int retval = Double.compare(Double.valueOf(sortColVal), sortedValue);
                    if (retval == 0) {
                        commonAdapter.addHash(sortLinkedMap);
                        CommonRowData commonRow = new CommonRowData();
                        commonRow.setHead1(String.valueOf(sortLinkedMap.get("ScripName")));
                        if (String.valueOf(sortLinkedMap.get("assetType")).equalsIgnoreCase("currency")) {
                            commonRow.setHead2(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        } else {
                            commonRow.setHead2(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        }


                        commonRow.setHead4(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (%)")))));
                        commonRow.setHead6(String.valueOf(sortLinkedMap.get("Token")));
                        commonRow.setSubHead1("green");
                        commonAdapter.add(commonRow);
                        tempHashArray.remove(sortLinkedMap);
                    }
                }
            }
            commonAdapter.notifyDataSetChanged();
        } else {
            ArrayList<String> out = new ArrayList<>();
            for (int j = 0; j < hashArray.size(); j++) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) hashArray.get(j);
                String val = (String) linkedHashMap.get(sortCol);
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

            ArrayList<HashMap<String, String>> tempHashArray = (ArrayList<HashMap<String, String>>) hashArray.clone();
            commonAdapter.clear();

            for (int i = 0; i < out.size(); i++) {
                String sortedValue = out.get(i);
                for (int j = 0; j < tempHashArray.size(); j++) {
                    LinkedHashMap sortLinkedMap = (LinkedHashMap) tempHashArray.get(j);
                    String sortColVal = (String) sortLinkedMap.get(sortCol);
                    if (sortColVal.equals(sortedValue)) {
                        commonAdapter.addHash(sortLinkedMap);
                        CommonRowData commonRow = new CommonRowData();
                        commonRow.setHead1(String.valueOf(sortLinkedMap.get("ScripName")));
                        if (String.valueOf(sortLinkedMap.get("assetType")).equalsIgnoreCase("currency")) {
                            commonRow.setHead2(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        } else {
                            commonRow.setHead2(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        }


                        commonRow.setHead4(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (%)")))));
                        commonRow.setHead6(String.valueOf(sortLinkedMap.get("Token")));
                        commonRow.setSubHead1("green");
                        commonAdapter.add(commonRow);
                        tempHashArray.remove(sortLinkedMap);
                    }
                }
            }
            commonAdapter.notifyDataSetChanged();
        }


        //Cancel last callback if still waiting
        //handler.removeCallbacks(sortCallback);
        //handler.postDelayed(sortCallback, 1000);
    }

    private void getFromIntent() {
        Bundle bundle = getArguments();
        NiftyTitleText = bundle.getString("IndicesTitleText");
        MarketsIndianIndicesResponse indianIndicesResponse = (MarketsIndianIndicesResponse) bundle.getSerializable("Response");
        indices = indianIndicesResponse.getIndianIndices();

        if (indices != null) {
            for (IndianIndice indianIndice : indices) {
                if (indianIndice.getToken().equalsIgnoreCase(NiftyTitleText)) {
                    selectedExchange = indianIndice.getExchange();

                }
            }
        }

        if (indices != null) {

            for (IndianIndice indianIndice : indices) {
                if (!exchangelist.contains(indianIndice.getExchange())) {
                    exchangelist.add(indianIndice.getExchange());
                }
            }
        }

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangelist);
        spinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        int selectionexchange = -1;

        for (int i = 0; i < exchangelist.size(); i++) {
            if (exchangelist.get(i).equalsIgnoreCase(selectedExchange)) {
                selectionexchange = i;

            }
        }


        exchangeSpinner.setOnItemSelectedListener(exchangeSelection);
        exchangeSpinner.setAdapter(spinAdapter);

        for (IndianIndice indianIndice : indices) {
            if (indianIndice.getExchange().equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString())) {
                indicesGrpList.add(indianIndice.getToken());
                indicesNameList.add(indianIndice.getExchange());
            }
        }
        ArrayAdapter<String> spinAdapter1 = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), indicesGrpList);
        spinAdapter1.setDropDownViewResource(R.layout.custom_spinner);
        stockSpinner.setOnItemSelectedListener(optionSelection);
        stockSpinner.setAdapter(spinAdapter1);
        exchangeSpinner.setSelection(selectionexchange);
    }

    private void sendRequest(int pos) {
        showProgress();
        if (flag == 0) {
            flag = 1;
        } else {
            exchangeGroupNameStock = indicesGrpList.get(pos);
        }
        MarketsIndicesStockRequest.sendRequest(exchangeSpinner.getSelectedItem().toString(), stockSpinner.getSelectedItem().toString(), getMainActivity(), serviceResponseHandler);
        isWaitingForResponseOnPTR = true;
    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        if (isWaitingForResponseOnPTR) {
            isWaitingForResponseOnPTR = false;
        }
        super.showMsgOnScreen(action, msg, jsonResponse);
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
            if (commonAdapter.containsSymbol(response.getSymbol())) {

                ArrayList<Integer> changedCol = new ArrayList<>();
                HashMap<String, String> data = commonAdapter.getItemHash(commonAdapter.indexOf(response.getSymbol()));
                if (!data.get(GreekConstants.LAST_TRADED_PRICE).equals(response.getLast())) {
                    changedCol.add(1);
                    double old = Double.parseDouble(data.get(GreekConstants.LAST_TRADED_PRICE));
                    double newd = Double.parseDouble(response.getLast());
                    String color = data.get("NewChange");
                    if (old < newd) {
                        color = "green";
                    } else if (old > newd) {
                        color = "red";
                    }
                    data.put(GreekConstants.LAST_TRADED_PRICE, response.getLast());
                    data.put("NewChange", color);
                }
                if (!data.get(GreekConstants.CHG_RS).equals(response.getChange())) {
                    changedCol.add(2);
                    data.put(GreekConstants.CHG_RS, response.getChange());
                }
                if (!data.get(GreekConstants.CHG_PERCENTILE).equals(response.getP_change())) {
                    changedCol.add(3);
                    data.put(GreekConstants.CHG_PERCENTILE, response.getP_change());
                }
                if (!data.get(GreekConstants.VOLUME).equals(response.getTot_vol())) {
                    changedCol.add(4);
                    data.put(GreekConstants.VOLUME, response.getTot_vol());
                }
                commonAdapter.setItem(commonAdapter.indexOf(response.getSymbol()), data);
                commonAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_STOCKS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                hideAppTitle();
                indicesStockResponse = (MarketsIndicesStockResponse) jsonResponse.getResponse();
                if (indicesStockResponse.getErrorCode().equals("3")) {
                    hideProgress();
                    commonAdapter.clear();
                    showmsgLayout.setVisibility(View.VISIBLE);
                    errorHeaderTextView.setText("No data available");
                    errorHeaderTextView.setVisibility(View.VISIBLE);
                } else {
                    handleIndicesResponse();
                    sendMultiStockRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (SYMBOLSEARCH_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && MULTIQUOTEDETAILS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                multiStockResponse = (SearchMultiStockDetailsResponse) jsonResponse.getResponse();
                handleMultiStockDetails();
            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
            }
        }
    }

    private void sendMultiStockRequest() {
        SearchMultiStockDetailsRequest.sendRequest(stockList, getMainActivity(), serviceResponseHandler);
    }

    private void handleMultiStockDetails() {
        commonAdapter.clear();
        List<IndicesStock> indicesStocks = indicesStockResponse.getIndicesStock();
        List<StockDetail> detail = multiStockResponse.getStockDetails();

        if (indicesStocks.size() > 0) {
            commonAdapter.clear();
            for (int i = 0; i < indicesStocks.size(); i++) {
                for (int j = 0; j < detail.size(); j++) {
                    if (indicesStocks.get(i).getToken().equalsIgnoreCase(detail.get(j).getToken())) {
                        LinkedHashMap<String, String> hm = new LinkedHashMap<>();

                        StockDetail stockDetail = detail.get(j);
                        String assetType = stockDetail.getAssetType();
                        String scriptName = stockDetail.getScriptName();
                        String token = stockDetail.getToken();
                        String exchange = stockDetail.getExchange();

                        hm.put(GreekConstants.SYMBOL, indicesStocks.get(i).getName());
                        hm.put(GreekConstants.LAST_TRADED_PRICE, indicesStocks.get(i).getLtp());
                        hm.put("NewChange", "green");
                        hm.put(GreekConstants.CHG_RS, String.format("%.2f", Double.parseDouble(indicesStocks.get(i).getChange())));
                        hm.put(GreekConstants.CHG_PERCENTILE, String.format("%.2f", Double.parseDouble(indicesStocks.get(i).getP_change())));
                        hm.put(GreekConstants.VOLUME, indicesStocks.get(i).getTradedQty());
                        hm.put(GreekConstants.SCRIPTNAME, scriptName);
                        hm.put(GreekConstants.DESCRIPTION, stockDetail.getDescription());
                        hm.put(GreekConstants.TOKEN, token);
                        hm.put(GreekConstants.ASSET_TYPE, assetType);
                        hm.put(GreekConstants.UNIQUEID, stockDetail.getUniqueID());
                        hm.put(GreekConstants.LOT, stockDetail.getLotQty());
                        hm.put(GreekConstants.TICKSIZE, stockDetail.getTickSize());
                        hm.put(GreekConstants.MULTIPLIER, stockDetail.getMultiplier());
                        hm.put(GreekConstants.EXPIRYDATE, stockDetail.getExpiryDate());
                        hm.put(GreekConstants.STRICKPRICE, stockDetail.getStrickPrice());
                        hm.put(GreekConstants.INSTRUMENTNAME, stockDetail.getInstrumentName());
                        hm.put(GreekConstants.OPTIONTYPE, stockDetail.getOptionType());
                        hm.put(GreekConstants.EXCHANGE, stockDetail.getExchange());
                        hm.put(GreekConstants.SYMBOL, stockDetail.getTradeSymbol());


                        commonAdapter.addHash(hm);
                        CommonRowData commonRow = new CommonRowData();
                        commonRow.setHead1(stockDetail.getTradeSymbol() + "-" + exchange);
                        commonRow.setHead2(indicesStocks.get(i).getLtp());
                        commonRow.setHead3(indicesStocks.get(i).getChange());
                        commonRow.setHead4(indicesStocks.get(i).getP_change());
                        commonRow.setHead6(indicesStocks.get(i).getToken());
                        commonRow.setSubHead1("green");
                        commonAdapter.add(commonRow);

                    }
                }
            }
            commonAdapter.notifyDataSetChanged();
            hideProgress();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getVisibleSymbolTokens(indicesList.getFirstVisiblePosition(), indicesList.getLastVisiblePosition());
                    sendStreamingRequest();
                }
            }, 1000);
        }


    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_INDICES_STOCK_SCREEN;
        EventBus.getDefault().register(this);

        if (visibleSymbolTable != null) {

            if (visibleSymbolTable.size() > 0) {
                sendStreamingRequest();
            }
        }
    }

    @Override
    public void onFragmentPause() {
        if (visibleSymbolTable != null && streamController != null) {
            if (visibleSymbolTable.size() > 0) {
                streamController.pauseStreaming(getActivity(), "ltpinfo", visibleSymbolTable);
            }
        }
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    private void sendStreamingRequest() {
        symbolsToUnsubscribe.clear();

        for (int i = 0; i < visibleSymbolTable.size(); i++) {

            symbolsToUnsubscribe.add(visibleSymbolTable.get(i));
        }
        streamController.sendStreamingRequest(getMainActivity(), visibleSymbolTable, "ltpinfo", null, null, false); //PK TODO
        addToStreamingList("ltpinfo", visibleSymbolTable);
    }

    //this method will capture the visible list of symbols
    private void getVisibleSymbolTokens(int firstVisibleItem, int totalVisibleCount) {
        visibleSymbolTable.clear();
        if (commonAdapter.getSymbolTable().size() > 0) {
            int totalCount = firstVisibleItem + totalVisibleCount;
            for (int i = firstVisibleItem; i < totalVisibleCount; i++) {
                if(commonAdapter.getSymbolTable().size() > totalVisibleCount) {
                    visibleSymbolTable.add(commonAdapter.getSymbolTable().get(i));
                }
            }
        }
    }

    private void getVisibleScrollSymbolTokens(int firstVisibleItem, int totalVisibleCount) {

        visibleSymbolTable.clear();

        if (commonAdapter.getSymbolTable().size() > 0) {
            int totalCount = firstVisibleItem + totalVisibleCount;
            for (int i = firstVisibleItem; i < totalCount; i++) {

                visibleSymbolTable.add(commonAdapter.getSymbolTable().get(i));
            }
        }
    }

    private void handleIndicesResponse() {
        List<IndicesStock> indicesStocks = indicesStockResponse.getIndicesStock();
        stockList = new ArrayList<>();
        if (indicesStocks.size() > 0) {
            for (int i = 0; i < indicesStocks.size(); i++) {
                StockList stock = new StockList();
                stock.setAssetType("equity");
                stock.setExchange(indicesNameList.get(selectedIndice));
                stock.setToken(indicesStocks.get(i).getToken());
                stockList.add(stock);
            }
        }
    }


    public class CustomAdapterLastVisited extends BaseAdapter {
        private final Context mContext;
        ArrayList<watchlistModel> watchlistModelsArray = new ArrayList<>();
        private final ArrayList<String> tokenList;

        public CustomAdapterLastVisited(Context context, ArrayList<watchlistModel> watchlistModels) {
            this.mContext = context;
            this.watchlistModelsArray = watchlistModels;
            tokenList = new ArrayList<>();
            hashArray = new ArrayList<>();
        }

        public void setItem(int position, HashMap<String, String> row) {
            hashArray.set(position, row);
        }

        public void add(CommonRowData commonRow) {

            model = new watchlistModel(commonRow.getHead1(), commonRow.getHead2(), commonRow.getHead3(), commonRow.getHead4(), commonRow.getHead5(), commonRow.getHead6(), commonRow.getSubHead1());
            tokenList.add(commonRow.getHead6());
            watchlistModelsArr.add(model);
        }

        public ArrayList<String> getSymbolTable() {
            return tokenList;
        }

        public void clear() {
            tokenList.clear();
            watchlistModelsArr.clear();
            hashArray.clear();
            notifyDataSetChanged();
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        @Override
        public int getCount() {
            return watchlistModelsArr.size();
        }

        @Override
        public Object getItem(int position) {
            return watchlistModelsArr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_table_list, null);
                holder.tvsymbol = convertView.findViewById(R.id.symbolname_text);
                holder.descriptionname = convertView.findViewById(R.id.descriptionname_text);
                holder.tvltp = convertView.findViewById(R.id.ltp_text);
                holder.tvchange = convertView.findViewById(R.id.change_text);
                holder.tvperchange = convertView.findViewById(R.id.perchange_text);
                holder.strip = convertView.findViewById(R.id.strip);
                convertView.setTag(holder);

            } else {

                holder = (Holder) convertView.getTag();
            }

            HashMap<String, String> data1 = hashArray.get(position);

            holder.tvsymbol.setText(data1.get("Symbol"));
            if (AccountDetails.getShowDescription()) {
                holder.descriptionname.setText(data1.get("description"));
                holder.descriptionname.setVisibility(View.VISIBLE);
            } else {
                holder.descriptionname.setVisibility(View.GONE);
            }
            holder.tvltp.setText(String.format("%.2f", Double.parseDouble(data1.get("LTP"))));
            holder.tvchange.setText(String.format("%.2f", Double.parseDouble(data1.get("Chg (Rs)"))));
            holder.tvperchange.setText(String.format("%.2f", Double.parseDouble(data1.get("Chg (%)"))));

            int flashBluecolor, flashRedColor;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

                holder.tvltp.setTextColor(getResources().getColor(R.color.black));

            } else {
                holder.tvltp.setTextColor(getResources().getColor(R.color.white));
            }

            int textColorPositive, textColorNegative;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                flashBluecolor = R.drawable.light_blue_positive;
                flashRedColor = R.drawable.light_red_negative;
                textColorPositive = R.color.dark_blue_positive;
                textColorNegative = R.color.dark_red_negative;

            } else {
                flashBluecolor = R.drawable.light_green_textcolor;
                flashRedColor = R.drawable.dark_red_negative;
                textColorPositive = R.color.white;
                textColorNegative = R.color.white;
            }

            if (data1.get("NewChange").equalsIgnoreCase("green")) {

                holder.tvltp.setTextColor(getResources().getColor(textColorPositive));
                holder.tvltp.setBackground(getResources().getDrawable(flashBluecolor));

            } else if (data1.get("NewChange").equalsIgnoreCase("red")) {

                holder.tvltp.setTextColor(getResources().getColor(textColorNegative));
                holder.tvltp.setBackground(getResources().getDrawable(flashRedColor));
            }

            if (data1.get("Chg (Rs)").startsWith("-")) {

                holder.tvchange.setTextColor(getResources().getColor(textColorNegative));

            } else {
                holder.tvchange.setTextColor(getResources().getColor(textColorPositive));
            }

            if (data1.get("Chg (%)").startsWith("-")) {
                holder.tvperchange.setTextColor(getResources().getColor(textColorNegative));

            } else {

                holder.tvperchange.setTextColor(getResources().getColor(textColorPositive));
            }

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvsymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.descriptionname.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvsymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.descriptionname.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }
            return convertView;
        }

        public void addHash(LinkedHashMap<String, String> hm) {
            hashArray.add(hm);
        }

        public HashMap<String, String> getItemHash(int position) {
            return hashArray.get(position);
        }

        public class Holder {
            GreekTextView tvsymbol, descriptionname;
            GreekTextView tvltp;
            GreekTextView tvchange;
            GreekTextView tvperchange;
            GreekTextView strip;
        }
    }
}
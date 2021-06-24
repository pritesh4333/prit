package com.acumengroup.mobile.portfolio;

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
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsmultiplescrip.MarketsMultipleScripRequest;
import com.acumengroup.greekmain.core.model.marketsmultiplescrip.MarketsMultipleScripResponse;
import com.acumengroup.greekmain.core.model.marketsmultiplescrip.QuoteList;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.StockList;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.watchlistModel;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */

public class LastVisitedFragment extends GreekBaseFragment implements View.OnClickListener {
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private Spinner assetTypeSpinner;
    private LinkedHashMap lMap;
    private ArrayList<String> types = new ArrayList<>();
    private boolean isReponseReceived = false;
    private MarketsMultipleScripResponse multiQuoteResponse;
    private boolean isWaitingForResponseOnPTR = false;
    private GreekTextView errorTextView;
    private RelativeLayout errorMsgLayout;
    private GreekTextView ltpSort, symText, chgText, perchgText;
    private CustomAdapterLastVisited commonAdapter;
    CustomAdapterLastVisited.Holder holder;
    private ArrayList<HashMap<String, String>> hashArray;
    ArrayList<watchlistModel> watchlistModelsArr = new ArrayList<>();
    watchlistModel model;
    boolean ltpAsc, symAsc, chgAsc, perchgAsc = false;
    StreamingController streamController = new StreamingController();
    private ListView lastVisitList;
    private AlertDialog levelDialog;

    private final AdapterView.OnItemSelectedListener typeSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (isReponseReceived) handleMultiQuoteDetails();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private ArrayList<StockList> stockList;

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
            if (lMap.get("InstrumentName").toString().contains("OPT")) {
                args.putString("SelectedOpt", (String) lMap.get("optionType"));
                args.putString("optType", (String) lMap.get("optionType"));
                //added by rohit for crash issue
                args.putString("optionType", (String) lMap.get("optionType"));
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
                args2.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp((String) lMap.get("expiryDate"), "dd MMM yyyy", "bse"));
                args2.putString(TradeFragment.STRICKPRICE, (String) lMap.get("strickPrice"));
                args2.putString("OptType", (String) lMap.get("optionType"));
                if (!"equity".equals(lMap.get("assetType"))) args2.putString("InstType", "");

                navigateTo(NAV_TO_TRADE_SCREEN, args2, true);
            }
            levelDialog.dismiss();
        } else if (id == R.id.pop_trade_sell) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                Bundle args3 = new Bundle();
                args3.putString(TradeFragment.SCRIP_NAME, (String) lMap.get("ScriptName"));
                args3.putString(TradeFragment.EXCHANGE_NAME, (String) lMap.get("Exchange"));
                args3.putString(TradeFragment.TOKEN, (String) lMap.get("Token"));
                args3.putString(TradeFragment.ASSET_TYPE, (String) lMap.get("assetType"));
                args3.putString(TradeFragment.UNIQUEID, "");
                args3.putString(TradeFragment.TRADE_SYMBOL, (String) lMap.get("OriginalSymbol"));
                args3.putString(TradeFragment.LOT_QUANTITY, (String) lMap.get("lot"));
                args3.putString(TradeFragment.TICK_SIZE, (String) lMap.get("tickSize"));
                args3.putString(TradeFragment.MULTIPLIER, (String) lMap.get("multiplier"));
                args3.putString(TradeFragment.TRADE_ACTION, "Sell");
                args3.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp((String) lMap.get("expiryDate"), "dd MMM yyyy", "bse"));
                args3.putString(TradeFragment.STRICKPRICE, (String) lMap.get("strickPrice"));
                args3.putString("OptType", (String) lMap.get("optionType"));
                navigateTo(NAV_TO_TRADE_SCREEN, args3, true);
            }
            levelDialog.dismiss();
        } else if (id == R.id.action_item2) {
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
            }
            levelDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lastVisitedView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_last_visited_new).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_last_visited_new).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_LASTVISITED_SCREEN;


        setupViews(lastVisitedView);
        sendLastVisitedRequest();
        return lastVisitedView;
    }

    private void setupViews(View parent) {
        showProgress();
        assetTypeSpinner = parent.findViewById(R.id.col1Spinner);
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            types = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.watchlistTypes)));
        } else {
            getAssetTypeList();
        }
        ArrayAdapter<String> assetAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), types);
        errorMsgLayout = parent.findViewById(R.id.showmsgLayout);
        errorTextView = parent.findViewById(R.id.errorHeader);
        ltpSort = parent.findViewById(R.id.ltpText);
        symText = parent.findViewById(R.id.symbolText);
        chgText = parent.findViewById(R.id.changeText);
        perchgText = parent.findViewById(R.id.perchangeText);
        assetAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(assetAdapter);
        assetTypeSpinner.setOnItemSelectedListener(typeSelectedListener);
        lastVisitList = parent.findViewById(R.id.watchlist_list);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = parent.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

        try {
            commonAdapter = new CustomAdapterLastVisited(getMainActivity(), new ArrayList<watchlistModel>());
            lastVisitList.setAdapter(commonAdapter);

        } catch (Exception e) {
            errorMsgLayout.setVisibility(View.VISIBLE);
            errorTextView.setText("No data available");
            errorTextView.setVisibility(View.VISIBLE);
        }

        lastVisitList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case 1: {
                        break;
                    }
                    case 0: {
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

        lastVisitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                lMap = (LinkedHashMap) commonAdapter.getItemHash(i);
                View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_four_actions, null);
                GreekTextView view1 = layout.findViewById(R.id.pop_quote);
                view1.setText(R.string.POPUP_SCRIPT_REMOVE);
                view1.setVisibility(View.GONE);
                GreekTextView view2 = layout.findViewById(R.id.pop_advice);
                view2.setText(R.string.POPUP_SCRIPT_QUOTE);
                GreekTextView view3 = layout.findViewById(R.id.pop_trade_sell);
                view3.setText(R.string.POPUP_SCRIPT_TRADE_SELL);
                GreekTextView view4 = layout.findViewById(R.id.pop_trade);
                view4.setText(R.string.POPUP_SCRIPT_TRADE);

                view1.setOnClickListener(LastVisitedFragment.this);
                view2.setOnClickListener(LastVisitedFragment.this);
                view3.setOnClickListener(LastVisitedFragment.this);
                view4.setOnClickListener(LastVisitedFragment.this);

                builder.setView(layout);
                levelDialog = builder.create();
                if (!levelDialog.isShowing()) {
                    levelDialog.show();
                }

            }
        });


        lastVisitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lMap = (LinkedHashMap) commonAdapter.getItemHash(position);
                View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_four_actions, null);
                GreekTextView view1 = layout.findViewById(R.id.pop_quote);
                view1.setText(R.string.POPUP_SCRIPT_REMOVE);
                view1.setVisibility(View.GONE);
                GreekTextView view2 = layout.findViewById(R.id.pop_advice);
                view2.setText(R.string.POPUP_SCRIPT_QUOTE);
                GreekTextView view3 = layout.findViewById(R.id.pop_trade_sell);
                view3.setText(R.string.POPUP_SCRIPT_TRADE_SELL);
                GreekTextView view4 = layout.findViewById(R.id.pop_trade);
                view4.setText(R.string.POPUP_SCRIPT_TRADE);

                view1.setOnClickListener(LastVisitedFragment.this);
                view2.setOnClickListener(LastVisitedFragment.this);
                view3.setOnClickListener(LastVisitedFragment.this);
                view4.setOnClickListener(LastVisitedFragment.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
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
                getVisibleSymbolTokens(lastVisitList.getFirstVisiblePosition(), lastVisitList.getLastVisiblePosition());
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
                getVisibleSymbolTokens(lastVisitList.getFirstVisiblePosition(), lastVisitList.getLastVisiblePosition());
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
                getVisibleSymbolTokens(lastVisitList.getFirstVisiblePosition(), lastVisitList.getLastVisiblePosition());
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
                getVisibleSymbolTokens(lastVisitList.getFirstVisiblePosition(), lastVisitList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });
    }

    private void getAssetTypeList() {

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
            types.add("Equity");
        }
        if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
            types.add("FNO");
        }
        if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            types.add("Currency");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            types.add("Commodity");
        }

        if (types.size() > 1) {
            types.add(0, "All");
        }
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
                        commonRow.setHead5(String.format("%.2f", Double.parseDouble("0")));
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
                        commonRow.setHead5(String.format("%.2f", Double.parseDouble("0")));
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


    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        isWaitingForResponseOnPTR = false;
    }

    private void sendStreamingRequest() {
        if (visibleSymbolTable.size() > 0) {
            streamController.sendStreamingRequest(getMainActivity(), visibleSymbolTable, "ltpinfo", null, null, false);
            addToStreamingList("ltpinfo", visibleSymbolTable);
        }
    }

    @Override
    public void onPause() {
        streamController.pauseStreaming(getActivity(), "ltpinfo", visibleSymbolTable);
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }
        sendStreamingRequest();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_LASTVISITED_SCREEN;
        hideAppTitle();
        sendStreamingRequest();
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
                //update table
                //   HashMap<String, String> data = tableAdapter.getItem(tableAdapter.indexOf(response.getSymbol()));
                HashMap<String, String> data = commonAdapter.getItemHash(commonAdapter.indexOf(response.getSymbol()));
                double old = Double.parseDouble(data.get(GreekConstants.LAST_TRADED_PRICE));
                double newd = Double.parseDouble(response.getLast());
                String color = data.get("NewChange");
                if (old < newd) {
                    color = "green";
                } else if (old > newd) {
                    color = "red";
                }
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) >= 1302999999))) {
                    data.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.4f", Double.parseDouble(response.getLast())));
                    data.put(GreekConstants.CHG_RS, String.format("%.4f", Double.parseDouble(response.getChange())));
                } else {
                    data.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.2f", Double.parseDouble(response.getLast())));
                    data.put(GreekConstants.CHG_RS, String.format("%.2f", Double.parseDouble(response.getChange())));
                }

                data.put("NewChange", color);

                data.put(GreekConstants.CHG_PERCENTILE, String.format("%.2f", Double.parseDouble(response.getP_change())));
                data.put(GreekConstants.VOLUME, StringStuff.commaDecorator(response.getTot_vol()));
                data.put(GreekConstants.PREVIOUS_OPEN, response.getOpen());
                data.put(GreekConstants.PREVIOUS_CLOSE, response.getClose());
                data.put(GreekConstants.HIGH_52WEEK, response.getYhigh());
                data.put(GreekConstants.LOW_52WEEK, response.getYlow());
                data.put(GreekConstants.OPEN_INTEREST, response.getOi());

                commonAdapter.setItem(commonAdapter.indexOf(response.getSymbol()), data);
                commonAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Called from parent on tab click
    public void sendLastVisitedRequest() {
        showProgress();
        isReponseReceived = false;
        if (isReponseReceived) return;
        if (isWaitingForResponseOnPTR) return;
        isWaitingForResponseOnPTR = true;
        //sendGreekViewRequest();
        WSHandler.getRequest(getMainActivity(), "getPreviousViewedScripBySymbol?ClientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getUsername(getMainActivity()), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    //showProgress();
                    JSONArray dataJSONArray = response.getJSONArray("data");
                    stockList = new ArrayList<>();
                    for (int i = 0; i < dataJSONArray.length(); i++) {
                        JSONObject object = (JSONObject) dataJSONArray.get(i);
                        String segment = object.getString("Segment").toLowerCase();
                        String exchangeCode = object.getString("ExchangeCode");
                        String token = object.getString("Token");

                        StockList stock = new StockList();
                        stock.setAssetType(segment);
                        stock.setExchange(exchangeCode);
                        stock.setToken(token);
                        stockList.add(stock);
                    }
                    sendMultiQuoteRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                isWaitingForResponseOnPTR = false;
                hideProgress();
            }
        });
    }


    private void sendMultiQuoteRequest() {
        showProgress();
        MarketsMultipleScripRequest.sendRequest(stockList, getMainActivity(), serviceResponseHandler);
    }

    //this method will capture the visible list of symbols
    private void getVisibleSymbolTokens(int firstVisibleItem, int totalVisibleCount) {

        visibleSymbolTable.clear();
        if (commonAdapter.getSymbolTable().size() > 0) {
            int totalCount = firstVisibleItem + totalVisibleCount;
            for (int i = firstVisibleItem; i <= totalVisibleCount; i++) {
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

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        if (MULTIQUOTE_SVC_NAME.equals(jsonResponse.getServiceName()) && MULTIQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                multiQuoteResponse = (MarketsMultipleScripResponse) jsonResponse.getResponse();
                isReponseReceived = true;
                handleMultiQuoteDetails();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isWaitingForResponseOnPTR = false;
        hideAppTitle();
    }

    private void handleMultiQuoteDetails() {
        hideProgress();
        commonAdapter.clear();
        List<QuoteList> details = multiQuoteResponse.getQuoteList();
        for (int j = 0; j < details.size(); j++) {
            LinkedHashMap<String, String> hm = new LinkedHashMap<>();
            QuoteList quoteList = details.get(j);
            String exchange = quoteList.getExch();
            String assetType = quoteList.getAsset_type();


            if (AccountDetails.getShowDescription()) {

                if (exchange.equalsIgnoreCase("nse")) {
                    hm.put(GreekConstants.SYMBOL, quoteList.getSymbol() + "-" + "N");


                } else if (exchange.equalsIgnoreCase("bse")) {
                    hm.put(GreekConstants.SYMBOL, quoteList.getSymbol() + "-" + "B");

                } else if (exchange.equalsIgnoreCase("mcx")) {

                    if(quoteList.getOptionType().equalsIgnoreCase("XX"))
                    {
                        hm.put(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + quoteList.getInstrumentName());
                    }
                    else {
                        hm.put(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteList.getStrikePrice()+""+quoteList.getOptionType() + "-M" + " - " + quoteList.getInstrumentName());
                    }



                } else if (exchange.equalsIgnoreCase("ncdex")) {
                    hm.put(GreekConstants.SYMBOL, quoteList.getSymbol() + "-" + "NDX");
                }
            } else {
                if (exchange.equalsIgnoreCase("nse")) {
                    hm.put(GreekConstants.SYMBOL, quoteList.getName() + "-" + "N");

                } else if (exchange.equalsIgnoreCase("bse")) {
                    hm.put(GreekConstants.SYMBOL, quoteList.getName() + "-" + "B");

                } else if (exchange.equalsIgnoreCase("mcx")) {
                    if(quoteList.getOptionType().equalsIgnoreCase("XX"))
                    {
                        hm.put(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() +"-" + "M" + " - " + quoteList.getInstrumentName());
                    }
                    else {
                        hm.put(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteList.getStrikePrice()+quoteList.getOptionType()+"-" + "M" + " - " + quoteList.getInstrumentName());
                    }


                } else if (exchange.equalsIgnoreCase("ncdex")) {
                    hm.put(GreekConstants.SYMBOL, quoteList.getName() + "-" + "NDX");
                }
            }
            if (assetType.equalsIgnoreCase("currency")) {
                hm.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.4f", Double.parseDouble(quoteList.getLast())));
                hm.put(GreekConstants.CHG_RS, String.format("%.4f", Double.parseDouble(quoteList.getChange())));
            } else {
                hm.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.2f", Double.parseDouble(quoteList.getLast())));
                hm.put(GreekConstants.CHG_RS, String.format("%.2f", Double.parseDouble(quoteList.getChange())));
            }
            hm.put("NewChange", "green");


            hm.put(GreekConstants.CHG_PERCENTILE, String.format("%.2f", Double.parseDouble(quoteList.getP_change())));
            hm.put(GreekConstants.VOLUME, StringStuff.commaDecorator(quoteList.getTot_vol()));
            hm.put(GreekConstants.PREVIOUS_OPEN, quoteList.getOpen());
            hm.put(GreekConstants.PREVIOUS_CLOSE, quoteList.getClose());
            hm.put(GreekConstants.HIGH_52WEEK, quoteList.getYhigh());
            hm.put(GreekConstants.LOW_52WEEK, quoteList.getYlow());
            hm.put(GreekConstants.OPEN_INTEREST, quoteList.getOi());
            hm.put(GreekConstants.DESCRIPTION, quoteList.getName());
            hm.put(GreekConstants.EXCHANGE, exchange);
            hm.put(GreekConstants.SCRIPTNAME, quoteList.getScriptname());

            hm.put(GreekConstants.TOKEN, quoteList.getToken());
            hm.put(GreekConstants.ASSET_TYPE, quoteList.getAsset_type());
            hm.put(GreekConstants.UNIQUEID, quoteList.getToken());
            hm.put(GreekConstants.LOT, quoteList.getLot());
            hm.put(GreekConstants.TICKSIZE, quoteList.getTickSize());
            hm.put(GreekConstants.MULTIPLIER, quoteList.getMultiplier());
            hm.put(GreekConstants.EXPIRYDATE, quoteList.getExpiryDate());
            hm.put(GreekConstants.STRICKPRICE, quoteList.getStrikePrice());
            hm.put(GreekConstants.INSTRUMENTNAME, quoteList.getInstrumentName());
            hm.put(GreekConstants.OPTIONTYPE, quoteList.getOptionType());
            String currentSel = assetTypeSpinner.getSelectedItem().toString();
            if (currentSel.equalsIgnoreCase("All")) {
                commonAdapter.addHash(hm);
                CommonRowData commonRow = new CommonRowData();
                commonRow.setHead1(quoteList.getSymbol() + "-" + exchange);
                if (assetType.equalsIgnoreCase("currency")) {
                    commonRow.setHead2(String.format("%.4f", Double.parseDouble(quoteList.getLast())));
                    commonRow.setHead3(String.format("%.4f", Double.parseDouble(quoteList.getChange())));
                } else {
                    commonRow.setHead2(String.format("%.2f", Double.parseDouble(quoteList.getLast())));
                    commonRow.setHead3(String.format("%.2f", Double.parseDouble(quoteList.getChange())));
                }
                commonRow.setHead3(quoteList.getChange());
                commonRow.setHead4(quoteList.getP_change());
                commonRow.setHead5(quoteList.getClose());
                commonRow.setHead6(quoteList.getToken());
                commonRow.setSubHead1("green");
                commonAdapter.add(commonRow);
            } else if (assetType.equalsIgnoreCase(currentSel)) {
                commonAdapter.addHash(hm);
                CommonRowData commonRow = new CommonRowData();
                commonRow.setHead1(quoteList.getSymbol() + "-" + exchange);
                if (assetType.equalsIgnoreCase("currency")) {
                    commonRow.setHead2(String.format("%.4f", Double.parseDouble(quoteList.getLast())));
                    commonRow.setHead3(String.format("%.4f", Double.parseDouble(quoteList.getChange())));
                } else {
                    commonRow.setHead2(String.format("%.2f", Double.parseDouble(quoteList.getLast())));
                    commonRow.setHead3(String.format("%.2f", Double.parseDouble(quoteList.getChange())));
                }

                commonRow.setHead4(quoteList.getP_change());
                commonRow.setHead5(quoteList.getClose());
                commonRow.setHead6(quoteList.getToken());
                commonRow.setSubHead1("green");
                commonAdapter.add(commonRow);
            }

            commonAdapter.notifyDataSetChanged();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVisibleSymbolTokens(lastVisitList.getFirstVisiblePosition(), lastVisitList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        }, 1000);

        hideProgress();
        //}
    }


    public interface ListSortListener {
        void onSortListener();
    }

    public class CustomAdapterLastVisited extends BaseAdapter {
        private final Context mContext;
        ArrayList<watchlistModel> watchlistModelsArray = new ArrayList<>();
        private final ArrayList<String> tokenList;
        private ListSortListener sortListener;

        private final Runnable sortCallback = new Runnable() {
            @Override
            public void run() {
                if (sortListener != null) {
                    sortListener.onSortListener();
                    commonAdapter.notifyDataSetChanged();
                }
            }
        };


        public CustomAdapterLastVisited(Context context, ArrayList<watchlistModel> watchlistModels) {
            this.mContext = context;
            this.watchlistModelsArray = watchlistModels;
            tokenList = new ArrayList<>();
            hashArray = new ArrayList<>();
        }

        public void setItem(int position, HashMap<String, String> row) {
            // watchlistModelsArr.set(position,model);
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
            holder.tvsymbol.setSelected(true);
            holder.tvsymbol.requestFocus();
            holder.tvltp.setText(data1.get("LTP"));
            holder.tvchange.setText(data1.get("Chg (Rs)"));
            holder.tvperchange.setText(data1.get("Chg (%)"));

            int flashBluecolor, flashRedColor, textColorPositive, textColorNegative;

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

                holder.tvltp.setBackground(getResources().getDrawable(flashBluecolor));
                holder.tvltp.setTextColor(getResources().getColor(textColorPositive));

            } else if (data1.get("NewChange").equalsIgnoreCase("red")) {

                holder.tvltp.setBackground(getResources().getDrawable(flashRedColor));
                holder.tvltp.setTextColor(getResources().getColor(textColorNegative));

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

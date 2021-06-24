package com.acumengroup.mobile.markets;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.draw.DrawChart;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.intradaychart.DailyChartRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.AddSymbolToGroupRequest;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.AddSymbolToGroupResponse;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.SymbolDetail;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;
import com.acumengroup.greekmain.core.model.searchequityinformation.EquityInfo;
import com.acumengroup.greekmain.core.model.searchequityinformation.SearchEquityInformationRequest;
import com.acumengroup.greekmain.core.model.searchequityinformation.SearchEquityInformationResponse;
import com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice.Expiry_strickPrice;
import com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice.Scrip;
import com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice.ScripDetails;
import com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice.SearchFnoExpiryStrikePriceRequest;
import com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice.SearchFnoExpiryStrikePriceResponse;
import com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice.TradeInfo;
import com.acumengroup.greekmain.core.model.searchfnoexpirystrikeprice.Value;
import com.acumengroup.greekmain.core.model.streamerbroadcast.OpenInterestResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.chartModel;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.quickaction.QuickActionButtonAdapter;
import com.acumengroup.ui.quickaction.QuickActionButtonView;
import com.acumengroup.ui.quickaction.QuickActionItemDetails;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by Arcadia
 */

public class QuotesFragment extends GreekBaseFragment {
    public static final String SCRIP_NAME = "ScripName";
    public static final String EXCHANGE_NAME = "ExchangeName";
    public static final String UNIQUE_ID = "UniqueId";
    public static final String ASSET_TYPE = "AssetType";
    public static final String TRADE_SYMBOL = "TradeSymbol";
    public static final String INST_TYPE = "InstType";
    public static final String OPTION_TYPE = "optionType";
    public static final String TOKEN = "Token";
    public static final String FROM_PAGE = "From";
    public static final String CASH_INST = "CashInst";

    //Same as OPTION_TYPE
    public static final String SELECTED_OPTION = "SelectedOpt";
    public static final String MULTIPLIER = "Multiplier";
    public static final String TICK_SIZE = "TickSize";
    public static final String STRIKE_PRICE = "StrikePrice";
    public static final String EXPIRY = "Expiry";
    //Add to watchlist globals
    private final List<String> groupName = new ArrayList<>();
    private final List<String> groupType = new ArrayList<>();
    private final List<SymbolDetail> symbolList = new ArrayList<>();
    private final ChartSettings mChartSettings = new ChartSettings();
    private final ArrayList<String> futureExp = new ArrayList<>();
    private final ArrayList<String> tempfutureExp = new ArrayList<>();
    private final ArrayList<String> tempOptStrike = new ArrayList<>();
    private final ArrayList<String> optionExp = new ArrayList<>();
    private final ArrayList<String> optionExpformatted = new ArrayList<>();
    private final ArrayList<String> optionExpStk = new ArrayList<>();
    private final ArrayList<String> groupNameList = new ArrayList<>();
    String marketCap = "DUMMY_DUMMY";
    private String action = "";
    private String callput = "";
    private ListItemAdapter leftAdapter;
    private NewsCustomAdapter adapter;
    private String currentExchange;
    private String scripName;
    private ArrayList<String> optTypeList = new ArrayList<>();
    private String lotqty = "";
    private String total_buy = "00", total_sell = "00";
    private String streamingSymbol, selectedStrike = "";
    private PortfolioGetUserWatchListResponse getWatchListResponse;
    private ArrayAdapter<String> groupsAdapter;
    private List<SymbolList> symbolLists = new ArrayList<>();
    private String userType = "", chartAsset = "";
    private View quotesView;
    private ImageView greekView, iv_price_diff;
    private Spinner exchangeSpinner, assetTypeSpinner, strikePrcSpinner, expirySpinner, callputSpinner;
    private RadioButton bestaskbid_new, watchlist_quote, btnBuy, btnChart, btnSell, btnInfo, btnNews;
    private GreekTextView oitxt;
    private GreekTextView last;
    private GreekTextView bid;
    private GreekTextView ask;
    private GreekTextView askHeaderLbl, bidHeaderLbl, addWatchlistHeaderLbl;
    private GreekTextView change;
    private GreekTextView tapToExpand;
    private LinearLayout quoteDataLayout;
    private LinearLayout optionsLayout;
    private ArrayAdapter<String> exchangeSpinnerAdapter;
    private ArrayAdapter<String> expAdapter;
    private LinearLayout chartLayout;
    private LinearLayout totalLayout;
    private List<String> avlExchanges = new ArrayList<>();
    private List<String> avlExchangesID;
    private String currentTradeSymbol, currentLotSize, currentToken, currentUniqueid, description, currentAssetType, currentInstName, prevToken;
    private MarketsSingleScripResponse quoteResponse;
    private String Volume = "0";
    private String wHigh, wLow = "0";
    private HorizontalScrollView horizontalScrollView;
    private ListView leftItemList;
    private int selectedAssetType, selectedInstType = 0;
    private SearchFnoExpiryStrikePriceResponse optionSearchResponse;
    private StreamingController streamController = new StreamingController();
    private boolean nodatafound = false;
    private boolean showWatchlistPopUp = true;
    Button mktDeptBtn,tradeBtn,optBtn;


    private int selectedChartType = -1;

    ArrayList<String> sym = new ArrayList<>(); //will also be used for streaming
    private final AdapterView.OnItemSelectedListener strikePriceListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (callputSpinner.getSelectedItem().toString().equalsIgnoreCase("call")) {
                callput = "CE";
            } else {
                callput = "PE";
            }

            int itemPosition = 0, strkposition = 0;
            for (int i = 0; i < optionExp.size(); i++) {
                if (optionExp.get(i).equalsIgnoreCase(expirySpinner.getSelectedItem().toString())) {
                    itemPosition = i;

                }
            }
            List<Value> values = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue();

            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).getExpiry().equals(optionExpformatted.get(itemPosition))) {
                    strkposition = i;
                }
            }

            List<TradeInfo> tradeInfos = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue().get(strkposition).getTradeInfo();
            for (TradeInfo tradeinfo : tradeInfos) {
                String strikeprice;
                if (tradeinfo.getAssetType().equalsIgnoreCase("currency")) {
                    strikeprice = String.format("%.4f", Double.parseDouble(tradeinfo.getStrickPrice()));
                } else {
                    strikeprice = String.format("%.2f", Double.parseDouble(tradeinfo.getStrickPrice()));
                }
                if (callput.equalsIgnoreCase(tradeinfo.getOptionType()) && expirySpinner.getSelectedItem().toString().equals(DateTimeFormatter.getDateFromTimeStamp(tradeinfo.getExpiryDate(), "dd MMM yyyy", "bse")) &&
                        strikePrcSpinner.getSelectedItem().toString().equalsIgnoreCase(strikeprice)) {
                    currentToken = tradeinfo.getToken();
                    currentAssetType = tradeinfo.getAssetType();
                    currentExpiry = DateTimeFormatter.getDateFromTimeStamp(tradeinfo.getExpiryDate(), "dd MMM yyyy", "bse");
                    currentStrikePrice = strikeprice;
                    currentOptType = callputSpinner.getSelectedItem().toString();

                }

            }

            if (!prevToken.equalsIgnoreCase(currentToken)) {
                sendQuotesRequest(currentToken, currentAssetType);
                prevToken = currentToken;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private SearchFnoExpiryStrikePriceResponse futureSearchResponse;
    private String expiry = "", optType = "";
    private String assetType;
    private final View.OnClickListener mdClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (quoteResponse != null) {
                String scripNameTitle = quoteResponse.getDescription() + "-" + "NSE";
                if (exchangeSpinner.getSelectedItemPosition() != -1) {
                    scripNameTitle = quoteResponse.getDescription() + "-" + exchangeSpinner.getSelectedItem().toString();
                }
                Bundle bundle = new Bundle();
                bundle.putString("Symbol", currentToken);
                bundle.putString("Description", scripNameTitle);
                bundle.putString("TradeSymbol", currentTradeSymbol);
                bundle.putString("Token", currentToken);
                bundle.putString("Lots", currentLotSize);
                bundle.putString("Action", action);
                bundle.putString("AssetType", currentAssetType);
                bundle.putString("Scrip", scripName);
                bundle.putString("ExchangeName", currentExchange);
                bundle.putString("Multiplier", getArguments().getString(MULTIPLIER));
                bundle.putString("TickSize", getArguments().getString(TICK_SIZE));
                bundle.putString("instName", currentInstName);
                navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
            }
        }
    };
    private final View.OnClickListener chartClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (quoteResponse != null) {
                Bundle bundle = new Bundle();
                bundle.putString("Scrip", scripName);
                bundle.putString("Token", currentToken);
                bundle.putString("TradeSymbol", currentTradeSymbol);
                bundle.putString("description", quoteResponse.getDescription());
                bundle.putString("Lots", currentLotSize);
                bundle.putString("Action", action);
                bundle.putString("AssetType", quoteResponse.getAsset_type());
                navigateTo(NAV_TO_CHARTS_SCREEN, bundle, true);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
            }
        }
    };

    private final View.OnClickListener tradeClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                if (quoteResponse != null) {
                    Bundle args = new Bundle();
                    if (selectedInstType == 0) {
                        for (int i = 0; i < avlExchanges.size(); i++) {
                            if (exchangeSpinner.getSelectedItem().equals(avlExchanges.get(i))) {
                                args.putString("TradeSymbol", avlExchangesID.get(i).split(",")[3]);
                                args.putString("Token", avlExchangesID.get(i).split(",")[0]);
                                args.putString("Lots", avlExchangesID.get(i).split(",")[2]);
                                args.putString("Action", action);
                                args.putString("AssetType", currentAssetType);
                                args.putString("ExchangeName", currentExchange);
                                args.putString("instName", currentInstName);
                                args.putString("ScripName", scripName);

                                args.putString(TradeFragment.TRADE_ACTION, "buy");
                            }
                        }
                    } else {
                        args.putString("TradeSymbol", currentTradeSymbol);
                        args.putString("Token", currentToken);
                        args.putString("Lots", currentLotSize);
                        args.putString("Action", action);
                        args.putString("AssetType", currentAssetType);
                        args.putString("instName", currentInstName);
                        args.putString("ScripName", scripName);
                        args.putString("ExchangeName", currentExchange);

                        args.putString(TradeFragment.TRADE_ACTION, "buy");

                    }
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("futures")) {
                        if (expirySpinner.getSelectedItem() != null) {
                            args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                        } else {
                            args.putString("Expiry", currentExpiry);
                        }
                        args.putString(TradeFragment.STRICKPRICE, "00.00");
                        args.putString("OptType", "XX");
                    }
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("options")) {
                        if (expirySpinner.getSelectedItem() != null) {
                            args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                        } else {
                            args.putString("Expiry", currentExpiry);
                        }

                        if (strikePrcSpinner.getSelectedItem() != null) {
                            args.putString(TradeFragment.STRICKPRICE, strikePrcSpinner.getSelectedItem().toString());
                        } else {
                            args.putString(TradeFragment.STRICKPRICE, currentStrikePrice);
                        }
                        if (callputSpinner.getSelectedItem() != null) {
                            args.putString("OptType", getOptType(callputSpinner.getSelectedItem().toString()));
                        } else {
                            args.putString("OptType", getOptType(currentOptType));
                        }
                        //args.putString(TradeFragment.STRICKPRICE, strikePrcSpinner.getSelectedItem().toString());
                        //args.putString("OptType", getOptType(callputSpinner.getSelectedItem().toString()));
                    }
                    args.putString("description", quoteResponse.getDescription());
                    args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                    args.putString("TickSize", getArguments().getString(TICK_SIZE));
                    TradeFragment tradeFragment = new TradeFragment();
                    tradeFragment.setArguments(args);
                    navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
                }
            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
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

    private String getOptType(String optType) {


        if (optType != null) {
            if (optType.equalsIgnoreCase("call")) {
                return "CE";
            } else if (optType.equalsIgnoreCase("put")) {
                return "PE";
            }
        }
        return "";
    }

    private final View.OnClickListener tradeClickListenerSell = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                if (quoteResponse != null) {
                    Bundle args = new Bundle();
                    if (selectedInstType == 0) {
                        for (int i = 0; i < avlExchanges.size(); i++) {
                            if (exchangeSpinner.getSelectedItem().equals(avlExchanges.get(i))) {
                                args.putString("TradeSymbol", avlExchangesID.get(i).split(",")[3]);
                                args.putString("Token", avlExchangesID.get(i).split(",")[0]);
                                args.putString("Lots", avlExchangesID.get(i).split(",")[2]);
                                args.putString("Action", action);
                                args.putString("AssetType", currentAssetType);
                                args.putString("ExchangeName", currentExchange);
                                args.putString("instName", currentInstName);
                                args.putString("ScripName", scripName);
                                args.putString(TradeFragment.TRADE_ACTION, "sell");
                            }
                        }
                    } else {
                        args.putString("TradeSymbol", currentTradeSymbol);
                        args.putString("Token", currentToken);
                        args.putString("Lots", currentLotSize);
                        args.putString("Action", action);
                        args.putString("AssetType", currentAssetType);
                        args.putString("instName", currentInstName);
                        args.putString("ScripName", scripName);
                        args.putString("ExchangeName", currentExchange);
                        args.putString(TradeFragment.TRADE_ACTION, "sell");
                    }
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("futures")) {
                        if (expirySpinner.getSelectedItem() != null) {
                            args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                        } else {
                            args.putString("Expiry", currentExpiry);
                        }
                        args.putString(TradeFragment.STRICKPRICE, "00.00");
                        args.putString("OptType", "XX");
                    }
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("options")) {
                        if (expirySpinner.getSelectedItem() != null) {
                            args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                        } else {
                            args.putString("Expiry", currentExpiry);
                        }

                        if (strikePrcSpinner.getSelectedItem() != null) {
                            args.putString(TradeFragment.STRICKPRICE, strikePrcSpinner.getSelectedItem().toString());
                        } else {
                            args.putString(TradeFragment.STRICKPRICE, currentStrikePrice);
                        }
                        if (callputSpinner.getSelectedItem() != null) {
                            args.putString("OptType", getOptType(callputSpinner.getSelectedItem().toString()));
                        } else {
                            args.putString("OptType", getOptType(currentOptType));
                        }

                    }
                    args.putString("description", quoteResponse.getDescription());
                    args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                    args.putString("TickSize", getArguments().getString(TICK_SIZE));
                    navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
                }
            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
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


    private final View.OnClickListener watchClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (quoteResponse != null) {
                if (showWatchlistPopUp) {
                    showWatchlistPopUp = false;
                    sendWatchlistRequest();
                }

            } else {

                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
            }


        }
    };


    private final View.OnClickListener infoClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putString("Token", currentToken);
            args.putString("AssetType", currentAssetType);
            args.putString("Exchange", currentExchange);
            navigateTo(NAV_TO_CONTRACT_INFO_SCREEN, args, true);


        }
    };

    private final View.OnClickListener newsClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putString("Token", currentToken);
            args.putString("AssetType", currentAssetType);
            args.putString("Exchange", currentExchange);
            if (quoteResponse != null) {
                args.putString("searchText", quoteResponse.getSymbol());
            }
            navigateTo(NAV_TO_NEWS_SEARCH, args, true);


        }
    };
    private int selectedExchange;
    private List<Scrip> scrip = new ArrayList<>();
    private String selectedGrp = "", selectedGrpType = "";
    private AlertDialog alertDialog;
    private String currentStrikePrice = "";
    private String currentExpiry = "";
    private String currentOptType = "";
    private final AdapterView.OnItemSelectedListener optionExpiryListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (optionSearchResponse != null) {
                optionExpStk.clear();
                int itemPosition = 0, strkposition = 0;
                for (int i = 0; i < optionExp.size(); i++) {
                    if (optionExp.get(i).equalsIgnoreCase(expirySpinner.getSelectedItem().toString())) {
                        itemPosition = i;

                    }
                }
                List<Value> values = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue();

                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).getExpiry().equals(optionExpformatted.get(itemPosition))) {
                        strkposition = i;
                    }
                }

                List<TradeInfo> tradeInfos = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue().get(strkposition).getTradeInfo();


                if (callputSpinner.getSelectedItem().toString().equalsIgnoreCase("call")) {
                    callput = "CE";
                } else {
                    callput = "PE";
                }
                for (TradeInfo tradeinfo : tradeInfos) {
                    if (callput.equalsIgnoreCase(tradeinfo.getOptionType()) && expirySpinner.getSelectedItem().toString().equals(DateTimeFormatter.getDateFromTimeStamp(tradeinfo.getExpiryDate(), "dd MMM yyyy", "bse"))) {
                        if (tradeinfo.getAssetType().equalsIgnoreCase("currency")) {
                            optionExpStk.add(String.format("%.4f", Double.parseDouble(tradeinfo.getStrickPrice())));
                        } else {
                            optionExpStk.add(String.format("%.2f", Double.parseDouble(tradeinfo.getStrickPrice())));
                        }
                    }
                }




                ArrayAdapter<String> strkPriceAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optionExpStk);
                strkPriceAdapter.setDropDownViewResource(R.layout.custom_spinner);
                strikePrcSpinner.setAdapter(strkPriceAdapter);
                strikePrcSpinner.setSelection(0,false);

                if (selectedStrike.length() > 0) {
                    String decimalselectedStrike;
                    if (currentAssetType != null && !currentAssetType.equalsIgnoreCase("") && currentAssetType.equalsIgnoreCase("currency")) {
                        decimalselectedStrike = String.format("%.4f", Double.parseDouble(selectedStrike));
                    } else {
                        decimalselectedStrike = String.format("%.2f", Double.parseDouble(selectedStrike));
                    }
                    if (optionExpStk.contains(decimalselectedStrike))
                        strikePrcSpinner.setSelection(optionExpStk.indexOf(decimalselectedStrike));
                } else {
                    int selectedItem = 0;
                    if (getArguments().getString(STRIKE_PRICE) != null) {
                        for (int i = 0; i < optionExpStk.size(); i++) {
                            if (optionExpStk.get(i).equalsIgnoreCase(getArguments().getString(STRIKE_PRICE))) {
                                selectedItem = i;
                            }
                        }
                    }
                    strikePrcSpinner.setSelection(selectedItem);
                }

                for (TradeInfo tradeinfo : tradeInfos) {
                    if (callput.equalsIgnoreCase(tradeinfo.getOptionType()) && expirySpinner.getSelectedItem().toString().equals(DateTimeFormatter.getDateFromTimeStamp(tradeinfo.getExpiryDate(), "dd MMM yyyy", "bse")) &&
                            String.format("%.2f", Double.parseDouble(strikePrcSpinner.getSelectedItem().toString())).equalsIgnoreCase(String.format("%.2f", Double.parseDouble(tradeinfo.getStrickPrice())))) {
                        currentToken = tradeinfo.getToken();
                        currentAssetType = tradeinfo.getAssetType();
                        expiry = tradeinfo.getExpiryDate();
                        currentExpiry = DateTimeFormatter.getDateFromTimeStamp(tradeinfo.getExpiryDate(), "dd MMM yyyy", "bse");
                        currentStrikePrice = String.format("%.2f", Double.parseDouble(tradeinfo.getStrickPrice()));
                        currentOptType = callputSpinner.getSelectedItem().toString();

                    }
                }

                if (!assetTypeSpinner.getSelectedItem().toString().toLowerCase().contains("opt")) {
                    if (!prevToken.equalsIgnoreCase(currentToken)) {
                        sendQuotesRequest(currentToken, currentAssetType);
                        prevToken = currentToken;
                    }
                    //sendQuotesRequest(currentToken, currentAssetType);
                }
                //strikePrcSpinner.setSelection(0,false);
                strikePrcSpinner.setOnItemSelectedListener(strikePriceListener);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private RelativeLayout newsSection;
    private ProgressBar newsProgress, chartProgress;
    private RelativeLayout chartSection;
    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (currentToken != null) {
                sendQuotesRequest(currentToken, assetType);
            }
        }
    };
    private final AdapterView.OnItemSelectedListener optTypeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (optionSearchResponse != null) {
                String callput;
                if (callputSpinner.getSelectedItem().toString().equalsIgnoreCase("Call")) {
                    callput = "CE";
                } else {
                    callput = "PE";

                }

                int itemPosition = 0, strkposition = 0;
                for (int i = 0; i < optionExp.size(); i++) {
                    if (optionExp.get(i).equalsIgnoreCase(expirySpinner.getSelectedItem().toString())) {
                        itemPosition = i;

                    }
                }
                List<Value> values = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue();

                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).getExpiry().equals(optionExpformatted.get(itemPosition))) {
                        strkposition = i;
                    }
                }
                List<TradeInfo> infos = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue().get(strkposition).getTradeInfo();

                if (strikePrcSpinner.getSelectedItem() != null) {
                    for (TradeInfo tradeinfo : infos) {

                        if (callput.equalsIgnoreCase(tradeinfo.getOptionType()) && expirySpinner.getSelectedItem().toString().equals(DateTimeFormatter.getDateFromTimeStamp(tradeinfo.getExpiryDate(), "dd MMM yyyy", "bse"))
                                && String.format("%.2f", Double.parseDouble(strikePrcSpinner.getSelectedItem().toString())).equalsIgnoreCase(String.format("%.2f", Double.parseDouble(tradeinfo.getStrickPrice())))
                        ) {

                            currentLotSize = tradeinfo.getLotQty();
                            currentTradeSymbol = tradeinfo.getTradeSymbol();
                            currentToken = tradeinfo.getToken();
                            currentAssetType = tradeinfo.getAssetType();
                            currentExpiry = DateTimeFormatter.getDateFromTimeStamp(tradeinfo.getExpiryDate(), "dd MMM yyyy", "bse");
                            currentStrikePrice = String.format("%.2f", Double.parseDouble(tradeinfo.getStrickPrice()));
                            currentOptType = callputSpinner.getSelectedItem().toString();
                            //if(!assetTypeSpinner.getSelectedItem().toString().toLowerCase().contains("opt"))

                            //sendQuotesRequest(currentToken, currentAssetType);
                        }
                    }
                }

                if (!prevToken.equalsIgnoreCase(currentToken)) {
                    sendQuotesRequest(currentToken, currentAssetType);
                    prevToken = currentToken;
                }
                // strikePrcSpinner.setOnItemSelectedListener(strikePriceListener);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener futureExpiryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (futureSearchResponse != null) {
                int itemPosition = 0, strkposition = 0;
                for (int i = 0; i < futureExp.size(); i++) {
                    if (futureExp.get(i).equalsIgnoreCase(expirySpinner.getSelectedItem().toString())) {
                        itemPosition = i;

                    }
                }
                List<Value> values = futureSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue();

                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).getExpiry().equals(tempfutureExp.get(itemPosition))) {
                        strkposition = i;
                    }
                }
                List<TradeInfo> tradeInfos = scrip.get(0).getValue().get(strkposition).getTradeInfo();
                for (TradeInfo tradeInfo : tradeInfos) {
                    currentLotSize = tradeInfo.getLotQty();
                    currentToken = tradeInfo.getToken();
                    currentTradeSymbol = tradeInfo.getTradeSymbol();
                    currentAssetType = tradeInfo.getAssetType();
                    expiry = tradeInfo.getExpiryDate();
                }

                if (!prevToken.equalsIgnoreCase(currentToken)) {
                    sendQuotesRequest(currentToken, currentAssetType);
                    prevToken = currentToken;
                }
                //sendQuotesRequest(currentToken, currentAssetType);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener exchangeSelListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            currentExchange = exchangeSpinnerAdapter.getItem(arg2);
            if (getResources().getString(R.string.QT_CASH_INST_TYPE).equals(assetTypeSpinner.getSelectedItem())) {
                if (avlExchangesID.size() > 0) {
                    currentToken = avlExchangesID.get(exchangeSpinner.getSelectedItemPosition()).split(",")[0];
                    currentAssetType = avlExchangesID.get(exchangeSpinner.getSelectedItemPosition()).split(",")[5];
                    sendQuotesRequest(currentToken, currentAssetType);
                    currentUniqueid = avlExchangesID.get(exchangeSpinner.getSelectedItemPosition()).split(",")[4];
                }
            } else {
                if (selectedInstType == 1) {
                    filterFutureExpiry();
                } else if (selectedInstType == 2) {
                    //sendQuotesRequest(currentToken, currentAssetType);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };
    private RelativeLayout errorMsgLayout;
    private final AdapterView.OnItemSelectedListener assetTypeListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (quoteResponse != null) {

                streamController.pauseStreaming(getMainActivity(), "touchline", getStreamingSymbolList("touchline"));
                streamController.pauseStreaming(getMainActivity(), "ohlc", getStreamingSymbolList("ohlc"));
                streamController.pauseStreaming(getMainActivity(), "marketPicture", getStreamingSymbolList("marketPicture"));

            }
            quoteDataLayout.setVisibility(View.VISIBLE);
            errorMsgLayout.setVisibility(View.GONE);
            optionsLayout.setVisibility(View.GONE);
            if (getResources().getString(R.string.QT_CASH_INST_TYPE).equals(assetTypeSpinner.getSelectedItem())) {
                optionsLayout.setVisibility(View.GONE);
                greekView.setVisibility(View.VISIBLE);
                newsSection.setVisibility(View.GONE);
                assetType = "equity";
                selectedInstType = 0;
                selectedAssetType = 0;
                quoteResponse = null;
                sendGetEquityInfoRequest();
            } else if (getResources().getString(R.string.QT_FUTURES_INST_TYPE).equals(assetTypeSpinner.getSelectedItem())) {
                greekView.setVisibility(View.GONE);
                newsSection.setVisibility(View.GONE);
                selectedAssetType = 1;
                if ("equity".equals(assetType)) {
                    assetType = "fno";
                }

                if (selectedAssetType == 3 || getArguments().getString(ASSET_TYPE).equalsIgnoreCase("commodity")) {

                    sendFutOptRequest("Future", currentExchange, "commodity");
                } else {
                    if (currentAssetType.equalsIgnoreCase("equity")) {
                        sendFutOptRequest("Future", currentExchange, "fno");
                    } else {
                        sendFutOptRequest("Future", currentExchange, currentAssetType);
                    }
                }

                selectedInstType = 1;
            } else if (getResources().getString(R.string.QT_OPTIONS_INST_TYPE).equals(assetTypeSpinner.getSelectedItem())) {
                greekView.setVisibility(View.GONE);
                newsSection.setVisibility(View.GONE);
                selectedAssetType = 1;
                if ("equity".equals(assetType)) {
                    assetType = "fno";
                }

                if (getArguments().getString(ASSET_TYPE).equalsIgnoreCase("equity")) {
                    sendFutOptRequest("Option", currentExchange, "fno");
                } else {

                    sendFutOptRequest("Option", currentExchange, getArguments().getString(ASSET_TYPE));

                }


                selectedInstType = 2;
            }
            clearChartsLayout();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private static ViewGroup.LayoutParams fixListViewHeight(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        return params;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        quotesView = super.onCreateView(inflater, container, savedInstanceState);
        sym = new ArrayList<>();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_quotes).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_quotes).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        AccountDetails.currentFragment = NAV_TO_QUOTES_SCREEN;
        setupViews();
        settingThemeAsset();
        return quotesView;
    }

    public void settingThemeAsset() {
        bid.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        ask.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        bidHeaderLbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        askHeaderLbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        last.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        change.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        oitxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

    }

    private void extractArguments() {
        AccountDetails.globalArgQuoteFrag = getArguments();
        currentAssetType = getArguments().getString(ASSET_TYPE);
        if (currentAssetType != null) {
            currentAssetType = currentAssetType.toLowerCase();


        }
        currentTradeSymbol = getArguments().getString(TRADE_SYMBOL);
        currentToken = getArguments().getString(TOKEN);
        prevToken = currentToken;
        currentUniqueid = getArguments().getString(UNIQUE_ID);
        currentExchange = getArguments().getString(EXCHANGE_NAME);
        scripName = getArguments().getString(SCRIP_NAME);
        currentStrikePrice = getArguments().getString(STRIKE_PRICE);
        currentExpiry = getArguments().getString(EXPIRY);
        currentOptType = getArguments().getString(OPTION_TYPE);
        description = getArguments().getString("Description");
        currentInstName = getArguments().getString(INST_TYPE);
        optionExpStk.clear();
        optionExp.clear();
        optTypeList.clear();
        avlExchanges.clear();

        if (currentStrikePrice != null) {
            optionExpStk.add(String.format("%.2f", Double.parseDouble(currentStrikePrice)));

        }

        //futureExp.add(curr);
        if (currentExpiry != null && !currentExpiry.equalsIgnoreCase("0")) {
            optionExp.add(currentExpiry);
        }
        optTypeList.add(currentOptType);
        avlExchanges.add(getExchangeFromToken(currentToken));
    }

    private void setupViews() {

        extractArguments();
        errorMsgLayout = quotesView.findViewById(R.id.showmsgLayout);
        totalLayout = quotesView.findViewById(R.id.totalLayout);
        ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setText("No SymbolName Available.");
        horizontalScrollView = quotesView.findViewById(R.id.horizonscroll);
        //Complete SymbolName section to hide on no data.

        mktDeptBtn= quotesView.findViewById(R.id.btn_mkt_dept);
        tradeBtn= quotesView.findViewById(R.id.btn_trade);
        optBtn= quotesView.findViewById(R.id.btn_opt);

        optBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vector<QuickActionItemDetails> actionItemDetails = new Vector<>();
//                actionItemDetails.add(new QuickActionItemDetails("Sell", true, R.drawable.button_arcadia));
                actionItemDetails.add(new QuickActionItemDetails( "Chart",true, R.drawable.button_arcadia));
                actionItemDetails.add(new QuickActionItemDetails("Watchlist", true, R.drawable.button_arcadia));
                actionItemDetails.add(new QuickActionItemDetails("Info", true, R.drawable.button_arcadia));
                actionItemDetails.add(new QuickActionItemDetails("News", true, R.drawable.button_arcadia));

                QuickActionButtonAdapter quickActionAdapter = new QuickActionButtonAdapter(getMainActivity(), actionItemDetails);

                quickActionAdapter.setItemMargin(3, 3, 3, 3);
                quickActionAdapter.setItemLayoutParams(-1, -1);
//                quickActionAdapter.setEnableTextColor(R.color.white);

                QuickActionButtonView quickaction = new QuickActionButtonView(v, quickActionAdapter);
                quickaction.setNumColumns(1);
                quickActionAdapter.setSelection(selectedChartType, R.drawable.trade_drawable);
//                quickActionAdapter.setEnableTextColor(R.color.white);


                quickaction.setOnClickListener(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quickActionClick(dialog, which);
                    }
                });

                quickaction.show();

            }
        });

        mktDeptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quoteResponse != null) {
                    String scripNameTitle = quoteResponse.getDescription() + "-" + "NSE";
                    if (exchangeSpinner.getSelectedItemPosition() != -1) {
                        scripNameTitle = quoteResponse.getDescription() + "-" + exchangeSpinner.getSelectedItem().toString();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("Symbol", currentToken);
                    bundle.putString("Description", scripNameTitle);
                    bundle.putString("TradeSymbol", currentTradeSymbol);
                    bundle.putString("Token", currentToken);
                    bundle.putString("Lots", currentLotSize);
                    bundle.putString("Action", action);
                    bundle.putString("AssetType", currentAssetType);
                    bundle.putString("Scrip", scripName);
                    bundle.putString("ExchangeName", currentExchange);
                    bundle.putString("Multiplier", getArguments().getString(MULTIPLIER));
                    bundle.putString("TickSize", getArguments().getString(TICK_SIZE));
                    bundle.putString("instName", currentInstName);
                    navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
                }



            }
        });
        tradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                    if (quoteResponse != null) {
                        Bundle args = new Bundle();
                        if (selectedInstType == 0) {
                            for (int i = 0; i < avlExchanges.size(); i++) {
                                if (exchangeSpinner.getSelectedItem().equals(avlExchanges.get(i))) {
                                    args.putString("TradeSymbol", avlExchangesID.get(i).split(",")[3]);
                                    args.putString("Token", avlExchangesID.get(i).split(",")[0]);
                                    args.putString("Lots", avlExchangesID.get(i).split(",")[2]);
                                    args.putString("Action", action);
                                    args.putString("AssetType", currentAssetType);
                                    args.putString("ExchangeName", currentExchange);
                                    args.putString("instName", currentInstName);
                                    args.putString("ScripName", scripName);

                                    args.putString(TradeFragment.TRADE_ACTION, "buy");
                                }
                            }
                        } else {
                            args.putString("TradeSymbol", currentTradeSymbol);
                            args.putString("Token", currentToken);
                            args.putString("Lots", currentLotSize);
                            args.putString("Action", action);
                            args.putString("AssetType", currentAssetType);
                            args.putString("instName", currentInstName);
                            args.putString("ScripName", scripName);
                            args.putString("ExchangeName", currentExchange);

                            args.putString(TradeFragment.TRADE_ACTION, "buy");

                        }
                        if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("futures")) {
                            if (expirySpinner.getSelectedItem() != null) {
                                args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                            } else {
                                args.putString("Expiry", currentExpiry);
                            }
                            args.putString(TradeFragment.STRICKPRICE, "00.00");
                            args.putString("OptType", "XX");
                        }
                        if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("options")) {
                            if (expirySpinner.getSelectedItem() != null) {
                                args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                            } else {
                                args.putString("Expiry", currentExpiry);
                            }

                            if (strikePrcSpinner.getSelectedItem() != null) {
                                args.putString(TradeFragment.STRICKPRICE, strikePrcSpinner.getSelectedItem().toString());
                            } else {
                                args.putString(TradeFragment.STRICKPRICE, currentStrikePrice);
                            }
                            if (callputSpinner.getSelectedItem() != null) {
                                args.putString("OptType", getOptType(callputSpinner.getSelectedItem().toString()));
                            } else {
                                args.putString("OptType", getOptType(currentOptType));
                            }
                            //args.putString(TradeFragment.STRICKPRICE, strikePrcSpinner.getSelectedItem().toString());
                            //args.putString("OptType", getOptType(callputSpinner.getSelectedItem().toString()));
                        }
                        args.putString("description", quoteResponse.getDescription());
                        args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                        args.putString("TickSize", getArguments().getString(TICK_SIZE));
                        TradeFragment tradeFragment = new TradeFragment();
                        tradeFragment.setArguments(args);
                        navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
                    }
                } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
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
        });

        exchangeSpinner = quotesView.findViewById(R.id.exchange);
        assetTypeSpinner = quotesView.findViewById(R.id.asset_type);

        optionsLayout = quotesView.findViewById(R.id.optionLayout);
        callputSpinner = quotesView.findViewById(R.id.callputSpinner);
        expirySpinner = quotesView.findViewById(R.id.expirySpinner);
        strikePrcSpinner = quotesView.findViewById(R.id.strikePriceSpinner);


        ArrayAdapter<String> strkPriceAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optionExpStk);
        strkPriceAdapter.setDropDownViewResource(R.layout.custom_spinner);
        strikePrcSpinner.setAdapter(strkPriceAdapter);

        ArrayAdapter<String> optTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optTypeList);
        optTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        callputSpinner.setAdapter(optTypeAdapter);

        expAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optionExp);
        expAdapter.setDropDownViewResource(R.layout.custom_spinner);
        expirySpinner.setAdapter(expAdapter);


        quoteDataLayout = quotesView.findViewById(R.id.totalLayout);

        last = quotesView.findViewById(R.id.last_value);
        change = quotesView.findViewById(R.id.change_value);
        oitxt = quotesView.findViewById(R.id.oi);
        bid = quotesView.findViewById(R.id.bid_value);
        ask = quotesView.findViewById(R.id.ask_value);
        askHeaderLbl = quotesView.findViewById(R.id.ask_header);
        bidHeaderLbl = quotesView.findViewById(R.id.bid_header);
        addWatchlistHeaderLbl = quotesView.findViewById(R.id.addWatchlistHeader);

        chartSection = quotesView.findViewById(R.id.chartSection);
        chartLayout = chartSection.findViewById(R.id.chart_layout);
        tapToExpand = chartSection.findViewById(R.id.tapToExpand);
        chartProgress = chartSection.findViewById(R.id.progressBarChart);

        newsSection = quotesView.findViewById(R.id.newsSection);
        newsProgress = newsSection.findViewById(R.id.progressBarNews);

        RelativeLayout quoteSection = quotesView.findViewById(R.id.quoteList);
        leftItemList = quoteSection.findViewById(R.id.listLeft);
        leftAdapter = new ListItemAdapter(getMainActivity(), new ArrayList<String>());
        leftItemList.setAdapter(leftAdapter);

        greekView = quotesView.findViewById(R.id.iv_greek_view);
        iv_price_diff = quotesView.findViewById(R.id.iv_price_diff);
        watchlist_quote = quotesView.findViewById(R.id.watchlist_quote);
        bestaskbid_new = quotesView.findViewById(R.id.bestaskbid_new);
        btnChart = quotesView.findViewById(R.id.btnChart);
        btnBuy = quotesView.findViewById(R.id.btnBuy);
        btnSell = quotesView.findViewById(R.id.btnSell);
        btnInfo = quotesView.findViewById(R.id.info_quote);
        btnNews = quotesView.findViewById(R.id.news_quote);


        switch (currentAssetType) {
            case "equity":
                selectedAssetType = 0;
                newsSection.setVisibility(View.GONE);
                break;
            case "fno":
                selectedAssetType = 1;
                newsSection.setVisibility(View.GONE);
                break;
            case "currency":
                selectedAssetType = 2;
                newsSection.setVisibility(View.GONE);
                break;
            case "commodity":
                selectedAssetType = 3;
                newsSection.setVisibility(View.GONE);
                break;
        }


        avlExchangesID = new ArrayList<>();

        if (description != null) {
            setAppTitle(getClass().toString(), description + "-" + currentInstName);
        } else {
            setAppTitle(getClass().toString(), scripName + " - " + currentExchange);
        }
        setupAdapter();
        setupListeners();
    }

    private void setupAdapter() {
        exchangeSpinnerAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), avlExchanges);
        exchangeSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(exchangeSpinnerAdapter);

        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getInstrumentType());
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(assetTypeAdapter);
        String tempInst = getArguments().getString(INST_TYPE);
        if (tempInst != null) {
            if (tempInst.contains("OPT")) {
                selectedInstType = 2;
                if (getArguments().containsKey(SELECTED_OPTION)) {
                    optType = getArguments().getString(SELECTED_OPTION);
                }

                if (getArguments().containsKey("SelectedExp")) {
                    expiry = getArguments().getString("SelectedExp");
                }
                if (getArguments().containsKey(STRIKE_PRICE) && getArguments().get(STRIKE_PRICE) != null) {
                    selectedStrike = getArguments().getString(STRIKE_PRICE);
                }
                if (selectedAssetType == 1) assetTypeSpinner.setSelection(2);
                else if (selectedAssetType == 2) assetTypeSpinner.setSelection(1);
                else if (selectedAssetType == 3)
                    assetTypeSpinner.setSelection(2);
                if (getArguments().containsKey(FROM_PAGE)) {
                    if ("Watchlist".equals(getArguments().getString(FROM_PAGE))) {
                        if (currentAssetType.equalsIgnoreCase("fno")) {
                            if (tempInst.contains("OPT")) assetTypeSpinner.setSelection(2);
                            else assetTypeSpinner.setSelection(1);
                        }
                    }
                }

                optionsLayout.setVisibility(View.VISIBLE);
                callputSpinner.setVisibility(View.VISIBLE);
                strikePrcSpinner.setVisibility(View.VISIBLE);
                expirySpinner.setVisibility(View.VISIBLE);

            } else if (tempInst.contains("FUT")) {
                if (getArguments().containsKey(EXPIRY)) {
                    expiry = getArguments().getString(EXPIRY);
                }
                if (selectedAssetType == 1) assetTypeSpinner.setSelection(1);
                else if (selectedAssetType == 2)
                    assetTypeSpinner.setSelection(0);
                else if (selectedAssetType == 3)
                    assetTypeSpinner.setSelection(1);
            } else if (tempInst.contains("COM")) {
                assetTypeSpinner.setSelection(0);
            }
        }

        optTypeList = new ArrayList<>();
        optTypeList.add("Call");
        optTypeList.add("Put");
    }

    private List<String> getInstrumentType() {
        List<String> instType = new ArrayList<>();
        if (selectedAssetType == 0 || selectedAssetType == 1) {
            instType.add(getResources().getString(R.string.QT_CASH_INST_TYPE));
            instType.add(getResources().getString(R.string.QT_FUTURES_INST_TYPE));
            instType.add(getResources().getString(R.string.QT_OPTIONS_INST_TYPE));
        } else if (selectedAssetType == 2) {
            instType.add(getResources().getString(R.string.QT_FUTURES_INST_TYPE));
            instType.add(getResources().getString(R.string.QT_OPTIONS_INST_TYPE));
        } else if (selectedAssetType == 3) {
            instType.add(getResources().getString(R.string.QT_CASH_INST_TYPE));
            instType.add(getResources().getString(R.string.QT_FUTURES_INST_TYPE));
            instType.add(getResources().getString(R.string.QT_OPTIONS_INST_TYPE));
        }
        return instType;
    }

    private void setupListeners() {
        btnBuy.setOnClickListener(tradeClickListener);
        btnSell.setOnClickListener(tradeClickListenerSell);
        btnChart.setOnClickListener(chartClickListener);
        btnInfo.setOnClickListener(infoClickListener);
        btnNews.setOnClickListener(newsClickListener);
        watchlist_quote.setOnClickListener(watchClickListener);
        bestaskbid_new.setOnClickListener(mdClickListener);
        exchangeSpinner.setOnItemSelectedListener(exchangeSelListener);
        assetTypeSpinner.setOnItemSelectedListener(assetTypeListener);
        expirySpinner.setOnItemSelectedListener(optionExpiryListener);
        callputSpinner.setOnItemSelectedListener(optTypeListener);
        tapToExpand.setOnClickListener(chartClickListener);
    }

    private void sendQuotesRequest(String token, String assetType) {
        Log.d("token==========>", prevToken + "---" + token);
        showProgress();
        clearQuoteDetails();
        clearChartsLayout();

        lotqty = "0";
        chartAsset = assetType;
        MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), token, assetType, AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);
        chartProgress.setVisibility(View.VISIBLE);
        chartSection.setVisibility(View.VISIBLE);
        //DailyChartRequest.sendRequest(token, "1", getMainActivity(), serviceResponseHandler);
        SimpleDateFormat expirydateformat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        String currentDate = expirydateformat.format(c.getTime());
        DailyChartRequest.sendRequest(token, "1",currentDate,"0", getMainActivity(), serviceResponseHandler);
    }

    @Override
    public void onPause() {
        super.onPause();
        //hideAppTitle();
    }


    private void sendGetEquityInfoRequest() {
        SearchEquityInformationRequest request = new SearchEquityInformationRequest();
        request.setExchangeType(getArguments().getString(EXCHANGE_NAME));
        if (getArguments().getString(INST_TYPE).toLowerCase().contains("fut") || getArguments().getString(INST_TYPE).toLowerCase().contains("opt")) {
            if (getArguments().getString(EXCHANGE_NAME).toLowerCase().contains("nse")) {
                request.setInstType("EQ");
            } else if (getArguments().getString(EXCHANGE_NAME).toLowerCase().contains("bse")) {
                request.setInstType("A");
            } else if (getArguments().getString(EXCHANGE_NAME).toLowerCase().contains("mcx")) {
                request.setInstType("com");
            } else if (getArguments().getString(EXCHANGE_NAME).toLowerCase().contains("ncdex")) {
                request.setInstType("comdty");
            }
        } else {
            request.setInstType(getArguments().getString(INST_TYPE));
        }

        request.setScripName(scripName);
        SearchEquityInformationRequest.sendRequest(request, getMainActivity(), serviceResponseHandler);
    }

    private void sendFutOptRequest(String type, String exchangeName, String assetType) {
        showProgress();
        clearQuoteDetails();
        /*expAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple());
        expirySpinner.setAdapter(expAdapter);*/
        SearchFnoExpiryStrikePriceRequest.sendRequest(exchangeName, assetType, type, scripName, getMainActivity(), serviceResponseHandler);
        sendQuotesRequest(currentToken, assetType);
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        horizontalScrollView.setVisibility(View.GONE);
        if (SYMBOLSEARCH_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GETEQUITY.equals(jsonResponse.getServiceName())) {
            try {
                SearchEquityInformationResponse equityResponse = (SearchEquityInformationResponse) jsonResponse.getResponse();
                if (equityResponse.getErrorCode().equals("3")) {
                    totalLayout.setVisibility(View.GONE);
                    horizontalScrollView.setVisibility(View.GONE);
                    toggleErrorLayout(true);
                } else {
                    handleGetEquityResponse(equityResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();
        } else if (SYMBOLSEARCH_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GETOPTIONS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                if (selectedInstType == 1) {
                    optionsLayout.setVisibility(View.VISIBLE);
                    callputSpinner.setVisibility(View.GONE);
                    strikePrcSpinner.setVisibility(View.GONE);
                    expirySpinner.setVisibility(View.VISIBLE);
                    futureSearchResponse = (SearchFnoExpiryStrikePriceResponse) jsonResponse.getResponse();

                    if (futureSearchResponse.getErrorCode().equals("3")) {
                        optionsLayout.setVisibility(View.GONE);
                        horizontalScrollView.setVisibility(View.GONE);
                        totalLayout.setVisibility(View.GONE);
                        toggleErrorLayout(true);
                    } else {
                        handleFutureSearchResponse(futureSearchResponse);
                    }


                } else if (selectedInstType == 2) {
                    optionsLayout.setVisibility(View.VISIBLE);
                    callputSpinner.setVisibility(View.VISIBLE);
                    strikePrcSpinner.setVisibility(View.VISIBLE);
                    expirySpinner.setVisibility(View.VISIBLE);
                    optionSearchResponse = (SearchFnoExpiryStrikePriceResponse) jsonResponse.getResponse();

                    if (optionSearchResponse.getErrorCode().equals("3")) {
                        horizontalScrollView.setVisibility(View.GONE);
                        optionsLayout.setVisibility(View.GONE);
                        totalLayout.setVisibility(View.GONE);
                        toggleErrorLayout(true);
                    } else {
                        handleOptionsSearchResponse(optionSearchResponse);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();
        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            quoteDataLayout.setVisibility(View.VISIBLE);
            errorMsgLayout.setVisibility(View.GONE);
            try {
                quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                total_buy = "00";
                total_sell = "00";
                if (!quoteResponse.getIsError()) {
                    if (quoteResponse.getName() != null && quoteResponse.getName().length() > 1)
                        //setAppTitle(getClass().toString(), quoteResponse.getMessage());

                        if (quoteResponse.getExch().equalsIgnoreCase("MCX")) {

                            if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                            } else {
                                setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                            }


                        } else {

                            setAppTitle(getClass().toString(), quoteResponse.getDescription() + "-" + quoteResponse.getInstrument());

                        }

                    lotqty = quoteResponse.getLot();
                    description = quoteResponse.getDescription();
                    prevToken = currentToken = quoteResponse.getToken();
                    setQuoteDetails(quoteResponse);
                    updateSnapshotData(quoteResponse);

                    streamingSymbol = quoteResponse.getToken();
                    if (sym.size() != 0) {

                        streamController.pauseStreaming(getActivity(), "touchline", sym);
                        streamController.pauseStreaming(getActivity(), "ohlc", sym);
                        streamController.pauseStreaming(getActivity(), "marketPicture", sym);

                        sym.clear();
                        sym.add(quoteResponse.getToken());

                        streamController.sendStreamingRequest(getMainActivity(), sym, "touchline", null, null, false);
                        streamController.sendStreamingRequest(getMainActivity(), sym, "ohlc", null, null, false);
                        streamController.sendStreamingRequest(getMainActivity(), sym, "marketPicture", null, null, false);
                        addToStreamingList("touchline", sym);
                        addToStreamingList("ohlc", sym);
                        addToStreamingList("marketPicture", sym);

                    } else {

                        sym.add(quoteResponse.getToken());
                        streamController.sendStreamingRequest(getMainActivity(), sym, "touchline", null, null, false);
                        streamController.sendStreamingRequest(getMainActivity(), sym, "ohlc", null, null, false);
                        streamController.sendStreamingRequest(getMainActivity(), sym, "marketPicture", null, null, false);
                        addToStreamingList("touchline", sym);
                        addToStreamingList("ohlc", sym);
                        addToStreamingList("marketPicture", sym);

                    }
                } else {
                    setAppTitle(getClass().toString(), quoteResponse.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO: remove market captital
            hideProgress();
        } else if (CHART_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INTRADAY_CHART_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                parseChartData(jsonResponse.getResponseData());
                mChartSettings.setxLabelDateFormat(ChartConstants.XLabelDateFormat.HOUR_MINUTES_HHmm);
                mChartSettings.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                mChartSettings.setShowVolumeChart(false);
                mChartSettings.setGridColor(getResources().getColor(R.color.white_subheading));
                if (chartAsset.equalsIgnoreCase("currency")) {
                    mChartSettings.setyLabelDecimalPoint(4);
                } else {
                    mChartSettings.setyLabelDecimalPoint(2);
                }
                mChartSettings.setShowDate(false);
                DrawChart mDrawChart = new DrawChart(getMainActivity());
                mDrawChart.showAreaChart(mChartSettings, chartLayout, 0xFF6BB6FF, 0xFF6BB6FF);
                chartProgress.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (CHART_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INTRADAY_CHART_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                parseChartData(jsonResponse.getResponseData());
                mChartSettings.setxLabelDateFormat(ChartConstants.XLabelDateFormat.HOUR_MINUTES_HHmm);
                mChartSettings.setTextColor(Color.WHITE);
                mChartSettings.setShowVolumeChart(true);
                mChartSettings.setGridColor(getResources().getColor(R.color.white_subheading));
                mChartSettings.setyLabelDecimalPoint(2);
                mChartSettings.setShowDate(false);

                DrawChart mDrawChart = new DrawChart(getMainActivity());
                mDrawChart.showAreaChart(mChartSettings, chartLayout, 0xFF6BB6FF, 0xFF6BB6FF);
                chartProgress.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                if (getWatchListResponse.getGetUserwatchlist().size() != 0) {
                    showAddToWatchlistPopup();
                    getWatchlistGroupName();
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "No group created.Do you want to create the group?", "Yes", "No", true, new GreekDialog.DialogListener() {


                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action.name().equalsIgnoreCase("ok")) {
                                showWatchlistPopUp = true;
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("Groups", groupNameList);
                                navigateTo(NAV_TO_ADD_NEW_PORTFOLIO_SCREEN, bundle, true);
                            } else if (action.name().equalsIgnoreCase("cancel")) {
                                showWatchlistPopUp = true;
                            }
                        }
                    });
                }
            } catch (Exception e) {
                Log.d("pop error watchlist", e.getMessage());
                e.printStackTrace();
            }
            hideProgress();
        } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && ADD_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                AddSymbolToGroupResponse addsymboltogroupresponse = (AddSymbolToGroupResponse) jsonResponse.getResponse();
                alertDialog.cancel();
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_ADDED_SUCCESS_MSG), "OK", true, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();
        }
        onRefreshComplete();
    }

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void onRefreshComplete() {
        hideProgress();
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

            mChartSettings.setChartData(chartData);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clearChartsLayout() {
        chartLayout.removeAllViews();
    }


    private void handleGetEquityResponse(SearchEquityInformationResponse equityResponse) {
        avlExchanges.clear();
        avlExchangesID.clear();
        for (int i = 0; i < equityResponse.getScripDetails().getEquityInfo().size(); i++) {
            EquityInfo equityInfo = equityResponse.getScripDetails().getEquityInfo().get(i);
            com.acumengroup.greekmain.core.model.searchequityinformation.Scrip scrip = equityInfo.getScrip().get(0);
            avlExchanges.add(equityInfo.getExchange().toUpperCase());
            avlExchangesID.add(scrip.getToken() + "," + scrip.getTradeSymbol() + "," + scrip.getLotQty() + "," + scrip.getTradeSymbol() + "," + scrip.getUniqueID() + "," + scrip.getAssetType());
            if (currentTradeSymbol.equals(scrip.getTradeSymbol())) selectedExchange = i;
        }
        exchangeSpinnerAdapter.notifyDataSetChanged();
        setExchangeAdapter();
        String[] data = avlExchangesID.get(exchangeSpinner.getSelectedItemPosition()).split(",");
        currentToken = data[0];
        currentLotSize = data[2];
        currentTradeSymbol = data[3];
        currentUniqueid = data[4];
        currentAssetType = data[5];
    }

    private void setExchangeAdapter() {
        exchangeSpinnerAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), avlExchanges);
        exchangeSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(exchangeSpinnerAdapter);
        if (selectedAssetType == 0) {
            exchangeSpinner.setSelection(selectedExchange);
        }

        if (getArguments().containsKey(FROM_PAGE)) {
            int selectedExc = avlExchanges.indexOf(currentExchange);
            if (selectedExc == -1) selectedExc = 0;
            exchangeSpinner.setSelection(selectedExc);
        }
    }

    private void filterFutureExpiry() {
        if (futureSearchResponse != null) {
            ScripDetails details = futureSearchResponse.getScripDetails();
            List<Expiry_strickPrice> strikePriceList = details.getExpiry_strickPrice();
            if (strikePriceList.size() >= exchangeSpinnerAdapter.getCount())
                scrip = strikePriceList.get(exchangeSpinner.getSelectedItemPosition()).getScrip();
            else scrip = strikePriceList.get(0).getScrip();

            setFutureExpiry();
        }
    }

    private void handleFutureSearchResponse(SearchFnoExpiryStrikePriceResponse futureSearchResponse) {
        avlExchanges.clear();
        ScripDetails details = futureSearchResponse.getScripDetails();
        List<Expiry_strickPrice> strikePriceList = details.getExpiry_strickPrice();
        for (Expiry_strickPrice single : strikePriceList) {
            avlExchanges.add(single.getExchange().toUpperCase());
        }
        exchangeSpinnerAdapter.notifyDataSetChanged();
        setExchangeAdapter();
    }

    private void setFutureExpiry() {
        futureExp.clear();
        tempfutureExp.clear();

        for (Scrip expiry_strickPrice : scrip) {
            List<Value> values = expiry_strickPrice.getValue();
            for (Value value : values) {
                if (!futureExp.contains(value.getExpiry())) {
                    futureExp.add(value.getExpiry());


                }
            }
        }
        for (int i = 0; i < futureExp.size(); i++) {
            tempfutureExp.add(futureExp.get(i));
        }

        Collections.sort(tempfutureExp);

        futureExp.clear();
        for (int i = 0; i < tempfutureExp.size(); i++) {
            futureExp.add(DateTimeFormatter.getDateFromTimeStamp(tempfutureExp.get(i), "dd MMM yyyy", "bse"));
        }


        expAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), futureExp);
        expAdapter.setDropDownViewResource(R.layout.custom_spinner);
        expirySpinner.setAdapter(expAdapter);

        if (expiry.length() > 0) {
            if (futureExp.indexOf(expiry) != -1) {
                expirySpinner.setSelection(futureExp.indexOf(expiry));
            } else if (tempfutureExp.indexOf(expiry) != -1) {
                expirySpinner.setSelection(tempfutureExp.indexOf(expiry));
            }
        } else {
            int selectedItem = 0;
            if (getArguments().getString("SelectedExp") != null) {
                for (int i = 0; i < futureExp.size(); i++) {
                    if (futureExp.get(i).equalsIgnoreCase(getArguments().getString("SelectedExp")) || futureExp.get(i).equalsIgnoreCase(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("SelectedExp"), "dd MMM yyyy", "bse"))) {
                        selectedItem = i;
                    }
                }
            }
            expirySpinner.setSelection(selectedItem);
        }
        expirySpinner.setOnItemSelectedListener(futureExpiryListener);
    }

    private void setOptionsExpiry() {
        List<Scrip> scrip = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip();

        optionExp.clear();
        optionExpformatted.clear();
        for (Scrip expiry_strickPrice : scrip) {
            List<Value> values = expiry_strickPrice.getValue();
            for (Value value : values) {
                if (!optionExpformatted.contains(value.getExpiry())) {
                    optionExpformatted.add(value.getExpiry());
                }

            }
        }
        Collections.sort(optionExpformatted);
        optionExp.clear();
        for (int i = 0; i < optionExpformatted.size(); i++) {

            if (DateTimeFormatter.getDateFromTimeStamp(optionExpformatted.get(i), "dd MMM yyyy", "bse") != null) {
                optionExp.add(DateTimeFormatter.getDateFromTimeStamp(optionExpformatted.get(i), "dd MMM yyyy", "bse"));

            }

        }

        expAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optionExp);
        expAdapter.setDropDownViewResource(R.layout.custom_spinner);
        expirySpinner.setAdapter(expAdapter);

        if (expiry.length() > 0) {
            if (optionExp.indexOf(expiry) != -1) {
                expirySpinner.setSelection(optionExp.indexOf(expiry));
                nodatafound = false;
            } else if (optionExp.indexOf(DateTimeFormatter.getDateFromTimeStamp(expiry, "dd MMM yyyy", "bse")) != -1) {
                nodatafound = false;
                expirySpinner.setSelection(optionExp.indexOf(DateTimeFormatter.getDateFromTimeStamp(expiry, "dd MMM yyyy", "bse")));
            } else {
                nodatafound = true;
                totalLayout.setVisibility(View.GONE);

            }

        } else {
            int selectedItem = 0;
            if (getArguments().getString("SelectedExp") != null) {
                for (int i = 0; i < optionExp.size(); i++) {
                    if (optionExp.get(i).equalsIgnoreCase(getArguments().getString("SelectedExp")) || optionExp.get(i).equalsIgnoreCase(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("SelectedExp"), "dd MMM yyyy", "bse"))) {
                        selectedItem = i;
                    }
                }
            }
            expirySpinner.setSelection(selectedItem);
        }
        List<TradeInfo> tradeInfos = optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(exchangeSpinner.getSelectedItemPosition()).getScrip().get(0).getValue().get(0).getTradeInfo();

        optionExpStk.clear();
        tempOptStrike.clear();

        for (TradeInfo tradeinfo : tradeInfos) {
            if (tradeinfo.getOptionType().equalsIgnoreCase("CE"))
                optionExpStk.add(String.format("%.2f", Double.parseDouble(tradeinfo.getStrickPrice())));
        }
        for (int i = 0; i < optionExpStk.size(); i++) {
            tempOptStrike.add(optionExpStk.get(i));
        }
        Collections.sort(tempOptStrike);
        optionExpStk.clear();
        for (int i = 0; i < tempOptStrike.size(); i++) {
            optionExpStk.add(tempOptStrike.get(i));
        }


//        ArrayAdapter<String> strkPriceAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optionExpStk);
//        strkPriceAdapter.setDropDownViewResource(R.layout.custom_spinner);
//        strikePrcSpinner.setAdapter(strkPriceAdapter);

        if (selectedStrike.length() > 0) {
            if (optionExpStk.contains(selectedStrike))
                nodatafound = false;
            strikePrcSpinner.setSelection(optionExpStk.indexOf(selectedStrike));
        } else {
            int selectedItem = -1;
            if (getArguments().getString(STRIKE_PRICE) != null) {
                for (int i = 0; i < optionExpStk.size(); i++) {
                    if (optionExpStk.get(i).equalsIgnoreCase(getArguments().getString(STRIKE_PRICE))) {
                        selectedItem = i;
                    }
                }
            }
            if (selectedItem == -1) {
                nodatafound = true;
                totalLayout.setVisibility(View.GONE);
            } else {
                nodatafound = false;
                strikePrcSpinner.setSelection(selectedItem);
            }
        }
        expirySpinner.setOnItemSelectedListener(optionExpiryListener);
    }


    private void handleOptionsSearchResponse(SearchFnoExpiryStrikePriceResponse optionSearchResponse) {
        avlExchanges.clear();

        for (int i = 0; i < optionSearchResponse.getScripDetails().getExpiry_strickPrice().size(); i++) {
            avlExchanges.add(optionSearchResponse.getScripDetails().getExpiry_strickPrice().get(i).getExchange().toUpperCase());
        }
        exchangeSpinnerAdapter.notifyDataSetChanged();
        ArrayAdapter<String> optTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optTypeList);
        optTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        callputSpinner.setAdapter(optTypeAdapter);

        if (optType.length() > 0) {
            if ("ce".equalsIgnoreCase(optType) || "call".equalsIgnoreCase(optType))
                callputSpinner.setSelection(0);
            else callputSpinner.setSelection(1);
        } else {
            int selectedItem = 0;
            if (getArguments().getString(SELECTED_OPTION) != null) {
                for (int i = 0; i < optTypeList.size(); i++) {
                    if (optTypeList.get(i).equalsIgnoreCase(getArguments().getString(SELECTED_OPTION))) {
                        selectedItem = i;
                    }
                }
            }
            callputSpinner.setSelection(selectedItem);
        }

        setExchangeAdapter();
        setOptionsExpiry();
    }

    private void setQuoteDetails(MarketsSingleScripResponse quoteResp) {

        if (quoteResp.getAsset_type().equalsIgnoreCase("currency")) {
            bid.setText(String.format("%s (%s)", String.format("%.4f", Double.parseDouble(quoteResp.getBid())), quoteResp.getBidqty()));
            ask.setText(String.format("%s (%s)", String.format("%.4f", Double.parseDouble(quoteResp.getAsk())), quoteResp.getAskqty()));
            last.setText(String.format("%.4f", Double.parseDouble(quoteResp.getLast())));
            // last.setText(String.format("%.4f", String.format("%.4f", Double.parseDouble(quoteResp.getLast()))));
            change.setText(String.format("%s (%s%%)", String.format("%.4f", Double.parseDouble(quoteResp.getChange())), String.format("%.2f", Double.parseDouble(quoteResp.getP_change()))));
        } else {
            bid.setText(String.format("%s (%s)", String.format("%.2f", Double.parseDouble(quoteResp.getBid())), quoteResp.getBidqty()));
            ask.setText(String.format("%s (%s)", String.format("%.2f", Double.parseDouble(quoteResp.getAsk())), quoteResp.getAskqty()));
            last.setText(String.format("%.2f", Double.parseDouble(quoteResp.getLast())));
            change.setText(String.format("%s (%s%%)", String.format("%.2f", Double.parseDouble(quoteResp.getChange())), String.format("%.2f", Double.parseDouble(quoteResp.getP_change()))));
        }

        if (Float.parseFloat(quoteResp.getP_change()) >= 0) {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                change.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }else {
                change.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }
            iv_price_diff.setImageResource(R.drawable.arrow_up);

        } else {
            change.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
            iv_price_diff.setImageResource(R.drawable.arrow_down);

        }
    }

    private void clearQuoteDetails() {
        last.setText("0.0000");
        change.setText("0.0(0.0%)");
        bid.setText("0.0");
        ask.setText("0.0");
    }

    private void updateSnapshotData(MarketsSingleScripResponse quoteResp) {
        String volume = "0";
        leftAdapter.clear();
        leftAdapter.notifyDataSetChanged();
        if (quoteResp.getTot_vol().length() > 0 && Double.valueOf(quoteResp.getTot_vol()) > 0) {
            Double vol = Double.valueOf(quoteResp.getTot_vol()) / 1000;
            volume = StringStuff.commaDecorator(String.valueOf(vol));
        }
        wHigh = quoteResp.getYhigh();
        wLow = quoteResp.getYlow();
        //if (selectedAssetType == 0) {
        if (quoteResp.getAsset_type().equalsIgnoreCase("equity")) {


            addToList(0, "TOTAL BUY", total_buy, "TOTAL SELL", total_sell);
            addToList(1, "OPEN", String.format("%.2f", Double.parseDouble(quoteResp.getOpen())), "VOL('000)", quoteResp.getTot_vol());
            addToList(2, "HIGH", String.format("%.2f", Double.parseDouble(quoteResp.getHigh())), "52W HIGH", String.format("%.2f", Double.parseDouble(wHigh)));
            addToList(3, "LOW", String.format("%.2f", Double.parseDouble(quoteResp.getLow())), "52W LOW", String.format("%.2f", Double.parseDouble(wLow)));
            addToList(4, "CLOSE", String.format("%.2f", Double.parseDouble(quoteResp.getClose())), marketCap.split("_")[0], marketCap.split("_")[1]);
            oitxt.setVisibility(View.GONE);
        } else if (quoteResp.getAsset_type().equalsIgnoreCase("currency")) {
            addToList(0, "TOTAL BUY", total_buy, "TOTAL SELL", total_sell);
            addToList(1, "OPEN", String.format("%.4f", Double.parseDouble(quoteResp.getOpen())), "VOL('000)", quoteResp.getTot_vol());
            addToList(2, "HIGH", String.format("%.4f", Double.parseDouble(quoteResp.getHigh())), "CLOSE", String.format("%.4f", Double.parseDouble(quoteResp.getClose())));
            addToList(3, "LOW", String.format("%.4f", Double.parseDouble(quoteResp.getLow())), "LOT SIZE", lotqty);
            //addToList(3, "OPEN INTEREST", "0", "DUMMY", "DUMMY");
            oitxt.setVisibility(View.VISIBLE);
            oitxt.setText("Open Interest :" + quoteResp.getOi());
        } else {
            addToList(0, "TOTAL BUY", total_buy, "TOTAL SELL", total_sell);
            addToList(1, "OPEN", String.format("%.2f", Double.parseDouble(quoteResp.getOpen())), "VOL('000)", quoteResp.getTot_vol());
            addToList(2, "HIGH", String.format("%.2f", Double.parseDouble(quoteResp.getHigh())), "CLOSE", String.format("%.2f", Double.parseDouble(quoteResp.getClose())));
            addToList(3, "LOW", String.format("%.2f", Double.parseDouble(quoteResp.getLow())), "LOT SIZE", lotqty);
            //addToList(3, "OPEN INTEREST", "0", "DUMMY", "DUMMY");
            oitxt.setVisibility(View.VISIBLE);
            oitxt.setText("Open Interest :" + quoteResp.getOi());
        }
        ViewGroup.LayoutParams params = fixListViewHeight(leftItemList);
        leftItemList.setLayoutParams(params);
        leftItemList.requestLayout();
        leftAdapter.notifyDataSetChanged();
    }

    //TODO: to handle open interest response
    public void onEventMainThread(OpenInterestResponse openInterestResponse) {
        try {
            if (sym.get(0).equalsIgnoreCase(openInterestResponse.getToken())) {
                oitxt.setText("Open Interest :  " + openInterestResponse.getCurrentOI());
                leftAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equalsIgnoreCase("ohlc")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateOhlc(broadcastResponse);
            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("touchline")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketPicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcasBuySellTotal(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void updateOhlc(StreamerBroadcastResponse response) {
        if (response.getSymbol().equals(streamingSymbol)) {
            String volume = "0";
            if (response.getTot_vol().length() > 0 && Double.valueOf(response.getTot_vol()) > 0) {
                Double vol = Double.valueOf(response.getTot_vol()) / 1000;
                volume = StringStuff.commaDecorator(String.valueOf(vol));
            }
            leftAdapter.clear();
            leftAdapter.notifyDataSetChanged();

            volume = Volume;

            if (response.getExch().equalsIgnoreCase("bse")) {
                wHigh = response.getYhigh();
                wLow = response.getYlow();
            }

            Log.e("Questsfragment", "volume===>" + volume);

            if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {

                addToList(0, "TOTAL BUY", total_buy, "TOTAL SELL", total_sell);
                addToList(1, "OPEN", String.format("%.4f", Double.parseDouble(response.getOpen())), "VOL('000)", volume);
                addToList(2, "HIGH", String.format("%.4f", Double.parseDouble(response.getHigh())), "CLOSE", String.format("%.4f", Double.parseDouble(response.getClose())));
                addToList(3, "LOW", String.format("%.4f", Double.parseDouble(response.getLow())), "LOT SIZE", lotqty);
                oitxt.setVisibility(View.VISIBLE);
            } else if (((Integer.valueOf(response.getSymbol()) >= 101000000) && (Integer.valueOf(response.getSymbol()) <= 101999999)) || ((Integer.valueOf(response.getSymbol()) >= 201000000) && (Integer.valueOf(response.getSymbol()) <= 201999999))) {

                addToList(0, "TOTAL BUY", total_buy, "TOTAL SELL", total_sell);
                addToList(1, "OPEN", String.format("%.2f", Double.parseDouble(response.getOpen())), "VOL('000)", volume);
                addToList(2, "HIGH", String.format("%.2f", Double.parseDouble(response.getHigh())), "52W HIGH", String.format("%.2f", Double.parseDouble(wHigh)));
                addToList(3, "LOW", String.format("%.2f", Double.parseDouble(response.getLow())), "52W LOW", String.format("%.2f", Double.parseDouble(wLow)));
                addToList(4, "CLOSE", String.format("%.2f", Double.parseDouble(response.getClose())), marketCap.split("_")[0], marketCap.split("_")[1]);
                oitxt.setVisibility(View.GONE);

            } else {
                addToList(0, "TOTAL BUY", total_buy, "TOTAL SELL", total_sell);
                addToList(1, "OPEN", String.format("%.2f", Double.parseDouble(response.getOpen())), "VOL('000)", volume);
                addToList(2, "HIGH", String.format("%.2f", Double.parseDouble(response.getHigh())), "CLOSE", String.format("%.2f", Double.parseDouble(response.getClose())));
                addToList(3, "LOW", String.format("%.2f", Double.parseDouble(response.getLow())), "LOT SIZE", lotqty);
                oitxt.setVisibility(View.VISIBLE);
            }

            leftAdapter.notifyDataSetChanged();
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        if (response.getSymbol().equals(streamingSymbol)) {

            if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                bid.setText(String.format("%s (%s)", String.format("%.4f", Double.parseDouble(response.getBid())), response.getBidqty()));
                ask.setText(String.format("%s (%s)", String.format("%.4f", Double.parseDouble(response.getAsk())), response.getAskqty()));
                last.setText(String.format("%.4f", Double.parseDouble(response.getLast())));
                change.setText(String.format("%s (%s%%)", String.format("%.4f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
            } else {
                bid.setText(String.format("%s (%s)", response.getBid(), response.getBidqty()));
                ask.setText(String.format("%s (%s)", response.getAsk(), response.getAskqty()));
                last.setText(String.format("%.2f", Double.parseDouble(response.getLast())));
                change.setText(String.format("%s (%s%%)", String.format("%.2f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
            }

            Volume = response.getTot_vol();
            leftAdapter.setVol(1, response.getTot_vol());
            leftAdapter.notifyDataSetChanged();
        }
    }

    public void updateBroadcasBuySellTotal(StreamerBroadcastResponse response) {

        if (response.getSymbol().equals(streamingSymbol)) {

            total_buy = String.valueOf(Double.valueOf(response.getTot_buyQty()).intValue());
            total_sell = String.valueOf(Double.valueOf(response.getTot_sellQty()).intValue());
            leftAdapter.setBuySellQty(0, total_buy, total_sell);
            leftAdapter.notifyDataSetChanged();

        }
    }


    private void showAddToWatchlistPopup() {

        showWatchlistPopUp = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getMainActivity());
        View promptsView = LayoutInflater.from(getMainActivity()).inflate(R.layout.dialog_add_to_watchlist, null);
        alertDialogBuilder.setView(promptsView);

        Button ok_btn = promptsView.findViewById(R.id.button_ok_transc);
        Button cancel_btn = promptsView.findViewById(R.id.button_cancel_transc);
        GreekTextView addWatchlistHeaderLbl = promptsView.findViewById(R.id.addWatchlistHeader);
        LinearLayout addWatchlistPopup = promptsView.findViewById(R.id.addWatchlistPopupLayout);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            addWatchlistHeaderLbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            addWatchlistPopup.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        }

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWatchlistPopUp = true;
                symbolList.clear();
                List<String> exchangeList = new ArrayList<>();
                List<String> tradeSymbols = new ArrayList<>();
                List<String> assetTypeList = new ArrayList<>();
                List<String> tokenList = new ArrayList<>();

                currentTradeSymbol = quoteResponse.getDescription();
                currentToken = quoteResponse.getToken();
                if (userType.equals("user")) {
                    for (SymbolList list : symbolLists) {
                        exchangeList.add(list.getExchange());
                        tradeSymbols.add(list.getTradeSymbol());
                        tokenList.add(list.getToken());
                        assetTypeList.add(list.getAssetType());
                    }
                }

                if (tokenList.contains(currentToken)) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GR_SYMBOL_EXIST_MSG), "Ok", false, null);
                } else {
                    SymbolDetail detail = new SymbolDetail();
                    detail.setExchange(currentExchange);
                    detail.setTradeSymbol(currentTradeSymbol);
                    detail.setToken(currentToken);
                    detail.setAssetType(currentAssetType);

                    symbolList.add(detail);
                    sendSaveWatchListRequest(selectedGrp, selectedGrpType);
                }
            }


        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWatchlistPopUp = true;
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

    // Fetches the Watch List Group and Scrips.
    private void sendWatchlistRequest() {
        showProgress();
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            PortfolioGetUserWatchListRequest.sendRequest(AccountDetails.getToken(getMainActivity()), "1", getMainActivity(), serviceResponseHandler);
        } else {
            PortfolioGetUserWatchListRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), "1", getMainActivity(), serviceResponseHandler);
        }
    }

    private void sendSaveWatchListRequest(String group, String groupType) {
        showProgress();
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            AddSymbolToGroupRequest.sendRequest(AccountDetails.getToken(getMainActivity()), AccountDetails.getUsername(getMainActivity()), group, AccountDetails.getToken(getMainActivity()), symbolList,symbolList.size()+"", "user", getMainActivity(), serviceResponseHandler);
        } else {
            AddSymbolToGroupRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getUsername(getMainActivity()), group, AccountDetails.getToken(getMainActivity()), symbolList,symbolList.size()+"", "user", getMainActivity(), serviceResponseHandler);
        }
    }

    private void addToList(int position, String key, String value, String rightKey, String rightValue) {
        String toAdd = key + "_" + value + "_" + rightKey + "_" + rightValue;
        if (position < leftAdapter.getCount()) leftAdapter.set(position, toAdd);

        else {
            leftAdapter.add(toAdd);
        }
    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {
        handleNoData(jsonResponse);
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        handleNoData(jsonResponse);
    }

    private void handleNoData(JSONResponse jsonResponse) {
        if (CHART_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INTRADAY_CHART_SVC_NAME.equals(jsonResponse.getServiceName())) {
            chartProgress.setVisibility(View.GONE);
            chartSection.setVisibility(View.GONE);
        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && COMPANY_NEWS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            newsProgress.setVisibility(View.GONE);
            newsSection.setVisibility(View.GONE);
        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            leftAdapter.clear();
            leftAdapter.notifyDataSetChanged();
            quoteDataLayout.setVisibility(View.INVISIBLE);
            errorMsgLayout.setVisibility(View.VISIBLE);
        } else if (SYMBOLSEARCH_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GETEQUITY.equals(jsonResponse.getServiceName())) {
            quoteDataLayout.setVisibility(View.INVISIBLE);
            errorMsgLayout.setVisibility(View.VISIBLE);
        } else if (SYMBOLSEARCH_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GETOPTIONS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            avlExchanges.clear();
            exchangeSpinnerAdapter.notifyDataSetChanged();
            quoteDataLayout.setVisibility(View.INVISIBLE);
            errorMsgLayout.setVisibility(View.VISIBLE);
        }
        onRefreshComplete();
    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        handleNoData(jsonResponse);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_QUOTES_SCREEN;
        if (quoteResponse != null) {

            if (quoteResponse.getExch().equalsIgnoreCase("MCX")) {

                if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                    setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                } else {
                    setAppTitle(getClass().toString(), quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                }
            } else {
                setAppTitle(getClass().toString(), quoteResponse.getDescription() + "-" + quoteResponse.getInstrument());

            }
        }
        EventBus.getDefault().register(this);
        if (streamController != null) {
            streamController.sendStreamingRequest(getMainActivity(), sym, "touchline", null, null, false);
            streamController.sendStreamingRequest(getMainActivity(), sym, "ohlc", null, null, false);
            streamController.sendStreamingRequest(getMainActivity(), sym, "marketPicture", null, null, false);
        }
        addToStreamingList("touchline", sym);
        addToStreamingList("ohlc", sym);
        addToStreamingList("marketPicture", sym);
    }

    @Override
    public void onFragmentPause() {
        //hideAppTitle();
        if (streamController != null) {
            streamController.pauseStreaming(getActivity(), "touchline", sym);
            streamController.pauseStreaming(getActivity(), "ohlc", sym);
            streamController.pauseStreaming(getActivity(), "marketPicture", sym);
        }
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    public class ListItemAdapter extends BaseAdapter {
        private final Context context;
        private final ArrayList<String> list;

        ListItemAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public void clear() {
            list.clear();
        }

        public void add(String data) {
            list.add(data);
        }

        public void remove(String data) {
            list.remove(data);
        }

        public void set(int position, String data) {
            String[] upComing = data.split("_");
            String[] current = list.get(position).split("_");
            if (current[0].equals("DUMMY")) {
                current[0] = upComing[0];
                current[1] = upComing[1];
            } else if (current[2].equals("DUMMY")) {
                current[2] = upComing[2];
                current[3] = upComing[3];
            }
            list.set(position, current[0] + "_" + current[1] + "_" + current[2] + "_" + current[3]);
        }

        public void setVol(int position, String data) {
            String[] upComing = data.split("_");
            String[] current = list.get(position).split("_");
            if (current[0].equals("DUMMY")) {
                current[0] = upComing[0];
                current[1] = upComing[1];
            } else if (current[2].equals("DUMMY")) {
                current[2] = upComing[2];
                current[3] = upComing[3];
            }
            list.set(position, current[0] + "_" + current[1] + "_" + current[2] + "_" + data);
        }

        public void setBuySellQty(int position, String buydata, String sellData) {

            String[] current = list.get(position).split("_");

            list.set(position, current[0] + "_" + buydata + "_" + current[2] + "_" + sellData);
        }

        @Override
        public String getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            QuoteItemRowHolder holder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row_quotes, null);
                holder = new QuoteItemRowHolder(view);
                view.setTag(holder);
            } else {
                holder = (QuoteItemRowHolder) view.getTag();
            }
            String[] data = getItem(i).split("_");
            holder.setKeyText(data[0], i % 2 == 0);
            holder.setValueText(data[1], i % 2 == 0);
            if (!data[2].equals("DUMMY")) {
                holder.setVisibility(View.VISIBLE);
                holder.setRight(data[2], data[3], i % 2 == 0);
            } else {
                holder.setVisibility(View.INVISIBLE);
            }
            return view;
        }

        class QuoteItemRowHolder {
            final GreekTextView key;
            final GreekTextView value;
            final GreekTextView rightKey;
            final GreekTextView rightValue;

            QuoteItemRowHolder(View parent) {
                key = parent.findViewById(R.id.itemKey);
                value = parent.findViewById(R.id.itemValue);
                rightKey = parent.findViewById(R.id.rightKey);
                rightValue = parent.findViewById(R.id.rightValue);
            }

            public void setKeyText(String key, boolean isEven) {
                this.key.setText(key);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    this.key.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.key.setBackgroundColor(getResources().getColor(isEven ? R.color.marketStatusStripWhite : R.color.marketStatusStripWhite));
                } else {
                    this.key.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.key.setBackgroundColor(getResources().getColor(isEven ? R.color.market_grey_dark : R.color.market_grey_light));
                }
            }

            public void setValueText(String value, boolean isEven) {
                this.value.setText(value);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    this.value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.value.setBackgroundColor(getResources().getColor(isEven ? R.color.marketStatusStripWhite : R.color.marketStatusStripWhite));
                } else {
                    this.value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.value.setBackgroundColor(getResources().getColor(isEven ? R.color.market_grey_dark : R.color.market_grey_light));
                }
            }

            public void setVisibility(int visibility) {
                this.rightKey.setVisibility(visibility);
                this.rightValue.setVisibility(visibility);
            }

            public void setRight(String key, String value, boolean isEven) {
                this.rightKey.setText(key);
                this.rightValue.setText(value);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    this.rightKey.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.rightValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.rightKey.setBackgroundColor(getResources().getColor(isEven ? R.color.marketStatusStripWhite : R.color.marketStatusStripWhite));
                    this.rightValue.setBackgroundColor(getResources().getColor(isEven ? R.color.marketStatusStripWhite : R.color.marketStatusStripWhite));
                } else {
                    this.rightKey.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.rightValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    this.rightKey.setBackgroundColor(getResources().getColor(isEven ? R.color.market_grey_dark : R.color.market_grey_light));
                    this.rightValue.setBackgroundColor(getResources().getColor(isEven ? R.color.market_grey_dark : R.color.market_grey_light));
                }
            }
        }
    }

    public class NewsCustomAdapter extends BaseAdapter {
        final ArrayList<HashMap<String, String>> newsArrList;
        private LayoutInflater inflater = null;

        public NewsCustomAdapter(Context context, ArrayList<HashMap<String, String>> newsArrList) {
            this.newsArrList = newsArrList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return newsArrList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return newsArrList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            View rowView = convertView;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.row_quotes_news, parent, false);
                holder = new Holder(rowView);
                rowView.setTag(holder);
            } else {
                holder = (Holder) rowView.getTag();
            }
            HashMap<String, String> data = getItem(position);
            String[] ddMM = data.get("reportDDMM").split(" ");
            holder.news_month.setText(ddMM[1]);
            holder.news_date.setText(ddMM[0]);
            holder.heading.setText(data.get("header"));
            rowView.setBackgroundResource(position % 2 == 0 ? R.color.light_white : R.color.white);
            return rowView;
        }

        public class Holder {
            final GreekTextView news_month;
            final GreekTextView news_date;
            final GreekTextView heading;

            public Holder(View parent) {
                this.news_month = parent.findViewById(R.id.news_month);
                this.news_date = parent.findViewById(R.id.news_date);
                this.heading = parent.findViewById(R.id.heading);
            }
        }
    }


    private String getExchangeFromToken(String token) {
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




    private void quickActionClick(DialogInterface dialog, int which) {
        dialog.dismiss();
//        types.setImageResource(chartTypeDrawable[which]);
//        showChart(which);
        selectedChartType = which;

     /*   if(which==0){


            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                if (quoteResponse != null) {
                    Bundle args = new Bundle();
                    if (selectedInstType == 0) {
                        for (int i = 0; i < avlExchanges.size(); i++) {
                            if (exchangeSpinner.getSelectedItem().equals(avlExchanges.get(i))) {
                                args.putString("TradeSymbol", avlExchangesID.get(i).split(",")[3]);
                                args.putString("Token", avlExchangesID.get(i).split(",")[0]);
                                args.putString("Lots", avlExchangesID.get(i).split(",")[2]);
                                args.putString("Action", action);
                                args.putString("AssetType", currentAssetType);
                                args.putString("ExchangeName", currentExchange);
                                args.putString("instName", currentInstName);
                                args.putString("ScripName", scripName);
                                args.putString(TradeFragment.TRADE_ACTION, "sell");
                            }
                        }
                    } else {
                        args.putString("TradeSymbol", currentTradeSymbol);
                        args.putString("Token", currentToken);
                        args.putString("Lots", currentLotSize);
                        args.putString("Action", action);
                        args.putString("AssetType", currentAssetType);
                        args.putString("instName", currentInstName);
                        args.putString("ScripName", scripName);
                        args.putString("ExchangeName", currentExchange);
                        args.putString(TradeFragment.TRADE_ACTION, "sell");
                    }
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("futures")) {
                        if (expirySpinner.getSelectedItem() != null) {
                            args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                        } else {
                            args.putString("Expiry", currentExpiry);
                        }
                        args.putString(TradeFragment.STRICKPRICE, "00.00");
                        args.putString("OptType", "XX");
                    }
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("options")) {
                        if (expirySpinner.getSelectedItem() != null) {
                            args.putString("Expiry", expirySpinner.getSelectedItem().toString());
                        } else {
                            args.putString("Expiry", currentExpiry);
                        }

                        if (strikePrcSpinner.getSelectedItem() != null) {
                            args.putString(TradeFragment.STRICKPRICE, strikePrcSpinner.getSelectedItem().toString());
                        } else {
                            args.putString(TradeFragment.STRICKPRICE, currentStrikePrice);
                        }
                        if (callputSpinner.getSelectedItem() != null) {
                            args.putString("OptType", getOptType(callputSpinner.getSelectedItem().toString()));
                        } else {
                            args.putString("OptType", getOptType(currentOptType));
                        }

                    }
                    args.putString("description", quoteResponse.getDescription());
                    args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                    args.putString("TickSize", getArguments().getString(TICK_SIZE));
                    navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
                }
            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
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


        }else*/ if(which==0){

            if (quoteResponse != null) {
                Bundle bundle = new Bundle();
                bundle.putString("Scrip", scripName);
                bundle.putString("Token", currentToken);
                bundle.putString("TradeSymbol", currentTradeSymbol);
                bundle.putString("description", quoteResponse.getDescription());
                bundle.putString("Lots", currentLotSize);
                bundle.putString("Action", action);
                bundle.putString("AssetType", quoteResponse.getAsset_type());
                navigateTo(NAV_TO_CHARTS_SCREEN, bundle, true);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
            }



        }else if(which==1){


            if (quoteResponse != null) {
                if (showWatchlistPopUp) {
                    showWatchlistPopUp = false;
                    sendWatchlistRequest();
                }

            } else {

                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
            }






        }else if(which==2){


            Bundle args = new Bundle();
            args.putString("Token", currentToken);
            args.putString("AssetType", currentAssetType);
            args.putString("Exchange", currentExchange);
            navigateTo(NAV_TO_CONTRACT_INFO_SCREEN, args, true);





        }else if(which==3){


            Bundle args = new Bundle();
            args.putString("Token", currentToken);
            args.putString("AssetType", currentAssetType);
            args.putString("Exchange", currentExchange);
            if (quoteResponse != null) {
                args.putString("searchText", quoteResponse.getSymbol());
            }
            navigateTo(NAV_TO_NEWS_SEARCH, args, true);




        }


    }



  /*  private void showChart(int position) {
        if (chartDisplay != null) {
            if (position == 0) chartDisplay.showLineChart(chartSettings, chartsLayout, 0xFF6BB6FF);
            else if (position == 1)
                chartDisplay.showAreaChart(chartSettings, chartsLayout, 0xFF6BB6FF, 0xFF6BB6FF);
            else if (position == 2)
                chartDisplay.showOHLCChart(chartSettings, chartsLayout, 0xFF009900, Color.RED);
            else if (position == 3)
                chartDisplay.showCandleStickChart(chartSettings, chartsLayout, 0xFF009900, Color.RED);
        }
    }*/
}
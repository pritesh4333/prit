package com.acumengroup.mobile.chartiqscreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.marketsmultiplescrip.MarketsMultipleScripRequest;
import com.acumengroup.greekmain.core.model.marketsmultiplescrip.MarketsMultipleScripResponse;
import com.acumengroup.greekmain.core.model.marketsmultiplescrip.QuoteList;
import com.acumengroup.greekmain.core.model.searchequityinformation.SearchInstrumentNameResponse;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.SearchMultiStockDetailsResponse;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.StockDetail;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.StockList;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.core.symsearch.MultiStockDetailsData;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.markets.QuotesFragment;
import com.acumengroup.mobile.messaging.AlertFragment;
import com.acumengroup.mobile.model.NavigationModel;
import com.acumengroup.mobile.model.ScripModel;
import com.acumengroup.mobile.model.SearchPagerModel;
import com.acumengroup.mobile.model.watchlistModel;
import com.acumengroup.mobile.portfolio.MFWatchlistFragment;
import com.acumengroup.mobile.portfolio.WatchListFragment;
import com.acumengroup.mobile.symbolsearch.ChartSymbolAutoCompleteAdapter;
import com.acumengroup.mobile.symbolsearch.DelayAutoCompleteTextView;
import com.acumengroup.mobile.symbolsearch.FNOResultFragment;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.adapter.GreekPopulationListener;
import com.acumengroup.ui.adapter.LastSearchSymbolAdapter;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SearchSynbolActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants {


    private final ArrayList<Fragment> pagesList = new ArrayList<>();
    private ArrayList<String> assetTypeList = new ArrayList<>();
    private final ArrayList<SearchPagerModel> resultDataSet = new ArrayList<>();
    private final List<String> expiryDateList = new ArrayList<>();
    private final List<String> expiryTimeStampList = new ArrayList<>();
    private final List<String> instNameList = new ArrayList<>();
    private final List<String> expiryDateCommodityList = new ArrayList<>();
    private GreekCommonAdapter<MultiStockDetailsData> commonAdapter;
    GreekTextView title;
    private String pageSource = "";
    private String selecteScrip;
    private int pos, addSymbolAsset = 0;
    private LinearLayout equityView, fnoCurrencyView, commodityView;
    private ListCustomAdapter commodityAdapter;
    private RelativeLayout relativeExtend;
    ArrayAdapter<String> instSpinAdapter;
    ArrayAdapter<String> expiryDateCommodityAdapter;
    ArrayAdapter<String> expiryDateAdapter;
    private GreekTextView exptxt, tvexp;
    View mSymbolSearchView, upperViewLbl, lowerViewLbl;
    ViewPager mPager;
    int selectedInstIndex;
    ImageView searchImg;
    SearchInstrumentNameResponse instrumentNameResponse;
    ScripModel selectedModel = null;
    QuoteList selectedquoteModel = null;
    private final List<String> disInstrNameList = new ArrayList<>();
    private final List<String> disOptionTypeList = new ArrayList<>();
    private String symbolstring = "", typestring = "", exchstring = "", alreadySelectedExpry = null;

    private ArrayList<StockList> stockList;
    //    private ArrayList<LastSerchSymbolData> symbolList = new ArrayList<>();
    MarketsMultipleScripResponse multiQuoteResponse;
    boolean isresponseReceived = false;


    private ArrayList<HashMap<String, String>> hashArray;
    ArrayList<watchlistModel> watchlistModelsArr = new ArrayList<>();
    watchlistModel model;


    private final AdapterView.OnItemSelectedListener expiryCommodityListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String selectedDate = commodityExpiry.getSelectedItem().toString();
            commodityAdapter.clear();
            commodityAdapter.notifyDataSetChanged();
            for (int i = 0; i < resultDataSet.size(); i++) {
                SearchPagerModel model = resultDataSet.get(i);
                if (selectedDate.equalsIgnoreCase(model.getExpiryDate()) || selectedDate.equalsIgnoreCase("All")) {
                    commodityAdapter.add(model);
                }
            }
            commodityAdapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };
    private ListView searchResultsList, lastSearchSymbol;
    private GreekTextView lastsearchTitletxt;
    private Spinner assetTypeSpinner, exchangeTypeSpinner, expiryDateSpinner, commodityExpiry, instNameSpinner;
    private String assetType = "";
    LinearLayout parent_layout;

    private DelayAutoCompleteTextView searchBoxView;
    private ChartSymbolAutoCompleteAdapter autoCompleteAdapter;

    private final AdapterView.OnItemSelectedListener exchangeTypeSelectionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


            if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("NSE") || exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("BSE")) {
                List<String> assetTypeList = Arrays.asList(getResources().getStringArray(R.array.eqAssetType));
                ArrayAdapter<String> assetTypeSpinAdapter = new ArrayAdapter<>(SearchSynbolActivity.this, AccountDetails.getRowSpinnerSimple(), assetTypeList);
                assetTypeSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
                assetTypeSpinner.setAdapter(assetTypeSpinAdapter);
                if (addSymbolAsset == 0 || addSymbolAsset == 1)
                    assetTypeSpinner.setSelection(0);
                else if (addSymbolAsset == 2 || addSymbolAsset == 3)
                    assetTypeSpinner.setSelection(addSymbolAsset - 1);
            } else {
                List<String> assetTypeList = Arrays.asList(getResources().getStringArray(R.array.commAssetType));
                ArrayAdapter<String> assetTypeSpinAdapter = new ArrayAdapter<>(SearchSynbolActivity.this, AccountDetails.getRowSpinnerSimple(), assetTypeList);
                assetTypeSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
                assetTypeSpinner.setAdapter(assetTypeSpinAdapter);
                assetTypeSpinner.setSelection(0);
            }
            handleInstName(instrumentNameResponse);

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };

    private final AdapterView.OnItemSelectedListener instSelectionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            autoCompleteAdapter.setInstName(instNameSpinner.getSelectedItem().toString());
            if (selecteScrip != null) {

                if (instNameSpinner.getSelectedItem().toString().contains("FUT")) {
                    setIndicatorColor(0);
                    mPager.setCurrentItem(0);
                } else if (instNameSpinner.getSelectedItem().toString().contains("OPT")) {
                    setIndicatorColor(1);
                    mPager.setCurrentItem(1);

                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };
    private final AdapterView.OnItemSelectedListener assetTypeSelectionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            assetType = assetTypeSpinner.getSelectedItem().toString();
            String exchangeType = exchangeTypeSpinner.getSelectedItem().toString();
            autoCompleteAdapter.setAssetType(assetType);
            autoCompleteAdapter.setExchangeType(exchangeType);
            if (isresponseReceived)
                sendtoAdpterdata(multiQuoteResponse);

            searchBoxView.setText("");

            handleInstName(instrumentNameResponse);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };
    private int pagerActivePage = 0;
    private final AdapterView.OnItemSelectedListener expirySelectionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            FNOResultFragment.commonExpiry = expiryDateSpinner.getSelectedItem().toString();
            String expirydateTimestamp = null;

            if (expiryTimeStampList.size() == 0) {

                try {
                    expirydateTimestamp = String.valueOf(ConvertToLong(expiryDateSpinner.getSelectedItem().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                expirydateTimestamp = String.valueOf(expiryTimeStampList.get(expiryDateList.indexOf(expiryDateSpinner.getSelectedItem().toString())));

            }
            getOptionData(symbolstring, exchstring, typestring, expirydateTimestamp);
            //((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };
    private LinearLayout mTabsLinearLayout;
    private LastSearchSymbolAdapter lastSearchSymbolAdapter;
    private ServiceResponseHandler serviceResponseHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*mSymbolSearchView = super.onCreateView(inflater, container, savedInstanceState);
        attachLayout(R.layout.fragment_charting);*/

        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_searchsymbol);
        // AccountDetails.currentFragment = NAV_TO_SYMBOL_SEARCH_SCREEN;
        AccountDetails.needofDash = false;
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setupSpinners();
        setupViews();
//        setupViewPager(mSymbolSearchView);
//        showProgress();

//For BUG  0027881===========================================>
        lastSearchSymbol.setVisibility(View.VISIBLE);
//        lastsearchTitletxt.setVisibility(View.VISIBLE);
//        sendLastSearchSymbolRequest();

//        if (GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER) {
//            lastSearchSymbol.setVisibility(View.VISIBLE);
//            lastsearchTitletxt.setVisibility(View.VISIBLE);
//            sendLastSearchSymbolRequest();
//        } else {
//            lastsearchTitletxt.setVisibility(View.GONE);
//            lastSearchSymbol.setVisibility(View.GONE);
//
//        }

        //++++++++++++++++++++++++++++++++++++++++++++++++++++>


        searchBoxView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



    }

    private void setupSpinners() {
        assetTypeSpinner = findViewById(R.id.assetTypeSpinner);
        instNameSpinner = findViewById(R.id.instNameSpinner);
        exchangeTypeSpinner = findViewById(R.id.exchange);
        List<String> exchangeTypeList = Arrays.asList(getResources().getStringArray(R.array.exchangetTypeSpinner));
        ArrayAdapter<String> exchangeTypeSpinAdapter = new ArrayAdapter<>(this, AccountDetails.getRowSpinnerSimple(), exchangeTypeList);
        exchangeTypeSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeTypeSpinner.setAdapter(exchangeTypeSpinAdapter);

        instSpinAdapter = new ArrayAdapter<>(this, AccountDetails.getRowSpinnerSimple(), instNameList);
        instSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        instNameSpinner.setAdapter(instSpinAdapter);


        relativeExtend = findViewById(R.id.relative_extend);
        relativeExtend.setOnTouchListener(
                new RelativeLayout.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        return true;
                    }
                }
        );

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            assetTypeList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.eqAssetType)));
        } else {
//            getAssetTypeList();
            assetTypeList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.eqAssetType)));
        }

        ArrayAdapter<String> assetTypeSpinAdapter = new ArrayAdapter<>(this, AccountDetails.getRowSpinnerSimple(), assetTypeList);
        assetTypeSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(assetTypeSpinAdapter);
//        addSymbolAsset = getArguments().getInt("selectedAssetForSymbol");

//        String user = getArguments().getString("selectedAssetMf");
//        int selectionCount = 0;

//         if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
//            assetTypeSpinner.setSelection(3);
//        } else if (addSymbolAsset == 0 || addSymbolAsset == 1)
//            assetTypeSpinner.setSelection(0);
//        else if (addSymbolAsset == 4) {
//
//            assetTypeSpinner.setSelection(3);
//        } else {
//            assetTypeSpinner.setSelection(addSymbolAsset - 1);
//        }


        expiryDateSpinner = findViewById(R.id.currencyFnoExpiry);
        commodityExpiry = findViewById(R.id.commodityExpiry);
//        expiryDateList.clear();
//        expiryDateList.add("All");

        expiryDateCommodityList.clear();
        expiryDateCommodityList.add("All");
        expiryDateAdapter = new ArrayAdapter<>(this, AccountDetails.getRowSpinnerSimple(), expiryDateList);
        expiryDateAdapter.setDropDownViewResource(R.layout.custom_spinner);
        expiryDateSpinner.setAdapter(expiryDateAdapter);
        expiryDateSpinner.setOnItemSelectedListener(expirySelectionListener);

        expiryDateCommodityAdapter = new ArrayAdapter<>(this, AccountDetails.getRowSpinnerSimple(), expiryDateCommodityList);
        expiryDateCommodityAdapter.setDropDownViewResource(R.layout.custom_spinner);
        commodityExpiry.setAdapter(expiryDateCommodityAdapter);
        commodityExpiry.setOnItemSelectedListener(expiryCommodityListener);

        searchResultsList = findViewById(R.id.search_results);
        lastSearchSymbol = findViewById(R.id.watchlist_list);
        lastsearchTitletxt = findViewById(R.id.recent_search_title);
        parent_layout=findViewById(R.id.parent_layout);

        lastsearchTitletxt.setTextColor(getResources().getColor(R.color.white));
        /*commonAdapterlist = new CustomAdapterLastVisited(getMainActivity(), new ArrayList<watchlistModel>());
        searchResultsList.setAdapter(commonAdapterlist);*/
        lastSearchSymbol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedModel = lastSearchSymbolAdapter.getItem(position);
                selecteScrip = selectedModel.getScriptName();


                Bundle bundle = new Bundle();
                bundle.putString("Scrip", selectedModel.getScriptName());
                bundle.putString("Token", selectedModel.getToken());
                bundle.putString("TradeSymbol", selectedModel.getTradeSymbol());
                bundle.putString("description", selectedModel.getDescription());
                bundle.putString("Lots", selectedModel.getLotQty());
                bundle.putString("Action", "1");
                bundle.putString("AssetType", selectedModel.getAssetType());
                bundle.putString("ltp", multiQuoteResponse.getQuoteList().get(position).getLast());
//                navigateTo(NAV_TO_CHARTS_SCREEN, bundle, true);
                bundle.putString("from","placeorderchart");
                AccountDetails.setIsMainActivity(true);
//                sendAddSymbolRequest(selectedModel.getExchange(), selectedModel.getToken(), selectedModel.getTradeSymbol());
               /* Intent rotatechart = new Intent(SearchSynbolActivity.this, MainActivity.class);
                rotatechart.putExtras(bundle);
                startActivity(rotatechart);
                hideKeyboard(SearchSynbolActivity.this);*/

                Intent result = new Intent();
                result.putExtra("symbol", selectedModel.getScriptName());
                result.putExtra("Token", selectedModel.getToken());
                setResult(RESULT_OK, result);
                finish();

//                lastSearchSymbol.setVisibility(View.GONE);
//                lastsearchTitletxt.setVisibility(View.GONE);
//                selectedModel = null;
//                selectedModel = lastSearchSymbolAdapter.getItem(position);
//                selecteScrip = selectedModel.getScriptName();
//                InputMethodManager inputMethodManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//                //pagerActivePage = position;
//                String type = "";
//                disInstrNameList.clear();
//                disOptionTypeList.clear();
//                resultDataSet.clear();
//
//                if (pagerActivePage == 0) {
//                    type = "future";
//                } else if (pagerActivePage == 1) {
//                    type = "calloption";
//
//                } else if (pagerActivePage == 2) {
//                    type = "putoption";
//
//                }
//                if (!selectedModel.getAssetType().equalsIgnoreCase("Mutual Fund")) {
//
////                    searchBoxView.setText(selectedModel.getDescription());
//                    NavigationModel navModel = new NavigationModel();
//                    navModel.from(selectedModel);
//                    handleFurtherNavigation(navModel);
//                } else if (selectedModel.getAssetType().equalsIgnoreCase("FNO")) {
//
//                    String symbolStr = selectedModel.getTradeSymbol();
//                    String symbolExpry = selectedModel.getExpiryDate();
//                    String exchStr = selectedModel.getExchange();
////                    searchBoxView.setText(symbolStr);
//                    fnoCurrencyView.setVisibility(View.VISIBLE);
//                    if (type.equalsIgnoreCase("future")) {
//                        getResponseDataNew(symbolStr, exchStr, type);
//                    } else {
//                        getDistinctExpiry(symbolStr, exchStr, type);
//                    }
//                    //getResponseData(symbolStr,exchStr);
//                } else if (assetType.equalsIgnoreCase("Currency")) {
//                    String symbolStr = selectedModel.getTradeSymbol();
//                    String exchStr = selectedModel.getExchange();
////                    searchBoxView.setText(symbolStr);
//                    fnoCurrencyView.setVisibility(View.VISIBLE);
//                    //getResponseData(symbolStr,exchStr);
//                    //getResponseDataNew(symbolStr, exchStr, type);
//                    if (type.equalsIgnoreCase("future")) {
//                        getResponseDataNew(symbolStr, exchStr, type);
//                    } else {
//                        getDistinctExpiry(symbolStr, exchStr, type);
//                    }
//                } else if (assetType.equalsIgnoreCase("Commodity")) {
//                    String symbolStr = selectedModel.getTradeSymbol();
//                    String exchStr = selectedModel.getExchange();
////                    searchBoxView.setText(symbolStr);
//                    fnoCurrencyView.setVisibility(View.VISIBLE);
//                    //commodityView.setVisibility(View.VISIBLE);
//                    //getResponseData(symbolStr,exchStr);
//                    if (type.equalsIgnoreCase("future")) {
//                        getResponseDataNew(symbolStr, exchStr, type);
//                    } else {
//                        getDistinctExpiry(symbolStr, exchStr, type);
//                    }
//                } else if (assetType.equalsIgnoreCase("Mutual Fund")) {
//                    searchBoxView.setText(selectedModel.getSchemeName());
//                    //sendAddSymbolRequest(selectedModel.getExchange(), selectedModel.getToken(), selectedModel.getScriptName());
//                    searchBoxView.setText(selectedModel.getDescription());
//                    NavigationModel navModel = new NavigationModel();
//                    navModel.from(selectedModel);
//                    if (pageSource.equalsIgnoreCase("quote")) {
//                        pageSource = "MFQuote";
//                    }
//                    handleFurtherNavigation(navModel);
//
//                }

            }
        });

//        equityView = findViewById(R.id.search_results_layout);
        fnoCurrencyView = findViewById(R.id.fnoCurrencyView);
        commodityView = findViewById(R.id.commodityView);
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
    private void getAssetTypeList() {
        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
            assetTypeList.add("Equity");
        }
        if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
            assetTypeList.add("FNO");
        }
        if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            assetTypeList.add("Currency");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            assetTypeList.add("Commodity");
        }
        //assetTypeList.add("Mutual Fund");
    }



    private void setupViews() {

        assetTypeSpinner.setOnItemSelectedListener(assetTypeSelectionListener);
        exchangeTypeSpinner.setOnItemSelectedListener(exchangeTypeSelectionListener);
        searchBoxView = findViewById(R.id.search_text);
//        searchBoxView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchBoxView.setThreshold(2);

        searchImg = findViewById(R.id.srch_img);

        exptxt = findViewById(R.id.exptxt);
        tvexp = findViewById(R.id.tvExpiry);

        upperViewLbl = findViewById(R.id.upperLine);
        lowerViewLbl = findViewById(R.id.lowerLine);

        if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
            assetTypeSpinner.setBackground(getDrawable(R.drawable.rounded_react_shape_orange));
            parent_layout.setBackgroundColor(getColor(AccountDetails.backgroundColor));
            relativeExtend.setBackgroundColor(getColor(R.color.white));
            upperViewLbl.setBackgroundColor(getResources().getColor(R.color.black));
            lowerViewLbl.setBackgroundColor(getResources().getColor(R.color.black));
            searchBoxView.setTextColor(getResources().getColor(R.color.black));
            exptxt.setTextColor(getResources().getColor(R.color.black));
            tvexp.setTextColor(getResources().getColor(R.color.black));
            searchImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));

        } else {

            exptxt.setTextColor(getResources().getColor(R.color.white));
            tvexp.setTextColor(getResources().getColor(R.color.white));
        }


        autoCompleteAdapter = new ChartSymbolAutoCompleteAdapter(this, exchangeTypeSpinner.getSelectedItem().toString(), assetTypeSpinner.getSelectedItem().toString());
        searchBoxView.setAdapter(autoCompleteAdapter);
        searchBoxView.setLoadingIndicator((ProgressBar) findViewById(R.id.pb_loading_indicator));
        searchBoxView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedModel = autoCompleteAdapter.getItem(position);
                selecteScrip = selectedModel.getScriptName();

                Bundle bundle = new Bundle();
//                bundle.putString("Scrip", selectedModel.getScriptName());
                bundle.putString("Scrip", selectedModel.getDescription() + " " + selectedModel.getExchange() + " |");
                bundle.putString("Token", selectedModel.getToken());
                bundle.putString("TradeSymbol", selectedModel.getTradeSymbol());
                bundle.putString("description", selectedModel.getDescription());
                bundle.putString("Lots", selectedModel.getLotQty());
                bundle.putString("Action", "1");
                bundle.putString("AssetType", selectedModel.getAssetType());
                bundle.putString("from","placeorderchart");
//                bundle.putString("ltp", multiQuoteResponse.getQuoteList().get(position).getLast());
                AccountDetails.setIsMainActivity(true);
//                navigateTo(NAV_TO_CHARTS_SCREEN, bundle, true);
                searchBoxView.setText("");
                sendAddSymbolRequest(selectedModel.getExchange(), selectedModel.getToken(), selectedModel.getTradeSymbol());
                /*Intent rotatechart = new Intent(SearchSynbolActivity.this, MainActivity.class);
                rotatechart.putExtras(bundle);
                startActivity(rotatechart);*/

                Intent result = new Intent();
                result.putExtra("symbol", selectedModel.getScriptName());
                result.putExtra("Token", selectedModel.getToken());
                setResult(RESULT_OK, result);
                finish();
                hideKeyboard(SearchSynbolActivity.this);

//                lastSearchSymbol.setVisibility(View.GONE);
//                lastsearchTitletxt.setVisibility(View.GONE);
//                selectedModel = autoCompleteAdapter.getItem(position);
//                selecteScrip = selectedModel.getScriptName();
//                InputMethodManager inputMethodManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//                //pagerActivePage = position;
//                String type = "";
//                disInstrNameList.clear();
//                disOptionTypeList.clear();
//                resultDataSet.clear();
//                expiryDateList.clear();
//                expiryDateAdapter.clear();
//
//                if (pagerActivePage == 0) {
//                    type = "future";
//                } else if (pagerActivePage == 1) {
//                    type = "calloption";
//                } else if (pagerActivePage == 2) {
//                    type = "putoption";
//                }
//
//                if (assetType.equals("Equity")) {
//                    alreadySelectedExpry = DateTimeFormatter.getDateFromTimeStamp(selectedModel.getExpiryDate(), "dd MMM yyyy", "bse");
//
//                    searchBoxView.setText(selectedModel.getDescription());
//                    NavigationModel navModel = new NavigationModel();
//                    navModel.from(selectedModel);
//                    handleFurtherNavigation(navModel);
//                } else if (assetType.equalsIgnoreCase("FNO")) {
//                    String symbolStr = selectedModel.getTradeSymbol();
//                    String symbolExpry = selectedModel.getExpiryDate();
//                    alreadySelectedExpry = DateTimeFormatter.getDateFromTimeStamp(selectedModel.getExpiryDate(), "dd MMM yyyy", "bse");
//
//                    String exchStr = selectedModel.getExchange();
//                    searchBoxView.setText(symbolStr);
//                    fnoCurrencyView.setVisibility(View.VISIBLE);
//                    if (type.equalsIgnoreCase("future")) {
//                        getResponseDataNew(symbolStr, exchStr, type);
//                    } else {
//                        getDistinctExpiry(symbolStr, exchStr, type);
//                    }
//                    //getResponseData(symbolStr,exchStr);
//                } else if (assetType.equalsIgnoreCase("Currency")) {
//                    alreadySelectedExpry = DateTimeFormatter.getDateFromTimeStamp(selectedModel.getExpiryDate(), "dd MMM yyyy", "bse");
//
//                    String symbolStr = selectedModel.getTradeSymbol();
//                    String exchStr = selectedModel.getExchange();
//                    searchBoxView.setText(symbolStr);
//                    fnoCurrencyView.setVisibility(View.VISIBLE);
//                    //getResponseData(symbolStr,exchStr);
//                    //getResponseDataNew(symbolStr, exchStr, type);
//                    if (type.equalsIgnoreCase("future")) {
//                        getResponseDataNew(symbolStr, exchStr, type);
//                    } else {
//                        getDistinctExpiry(symbolStr, exchStr, type);
//                    }
//                } else if (assetType.equalsIgnoreCase("Commodity")) {
//
//                    alreadySelectedExpry = DateTimeFormatter.getDateFromTimeStamp(selectedModel.getExpiryDate(), "dd MMM yyyy", "bse");
//
//                    String symbolStr = selectedModel.getTradeSymbol();
//                    String exchStr = selectedModel.getExchange();
//                    searchBoxView.setText(symbolStr);
//                    fnoCurrencyView.setVisibility(View.VISIBLE);
//                    //commodityView.setVisibility(View.VISIBLE);
//                    //getResponseData(symbolStr,exchStr);
//                    if (type.equalsIgnoreCase("future")) {
//                        getResponseDataNew(symbolStr, exchStr, type);
//                    } else {
//                        getDistinctExpiry(symbolStr, exchStr, type);
//                    }
//                } else if (assetType.equalsIgnoreCase("Mutual Fund")) {
//                    searchBoxView.setText(selectedModel.getSchemeName());
//                    //sendAddSymbolRequest(selectedModel.getExchange(), selectedModel.getToken(), selectedModel.getScriptName());
//                    searchBoxView.setText(selectedModel.getDescription());
//                    NavigationModel navModel = new NavigationModel();
//                    navModel.from(selectedModel);
//                    if (pageSource.equalsIgnoreCase("quote")) {
//                        pageSource = "MFQuote";
//                    }
//
//
//                    handleFurtherNavigation(navModel);
//                }
            }
        });

        ListView commodityListView = findViewById(R.id.commodityListview);
        commodityAdapter = new ListCustomAdapter(SearchSynbolActivity.this, new ArrayList<SearchPagerModel>());
        commodityListView.setAdapter(commodityAdapter);
        commodityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavigationModel navModel = new NavigationModel();
                navModel.from(commodityAdapter.getItem(position));
                handleFurtherNavigation(navModel);
            }
        });

        setupAdapter();

//        if (getArguments().getString("Source") != null)
//            pageSource = getArguments().getString("Source");
    }


    private void setupViewPager(View parent) {/*
        pagesList.clear();

       *//* pagesList.add(FNOResultFragment.newInstance(pageSource, "Futures", previousFragment));
        pagesList.add(FNOResultFragment.newInstance(pageSource, "Call", previousFragment));
        pagesList.add(FNOResultFragment.newInstance(pageSource, "Put", previousFragment));
*//*
        String[] heading = new String[3];
        heading[0] = "Futures";
        heading[1] = "Call Option";
        heading[2] = "Put Option";

        mPager = parent.findViewById(R.id.pager);
        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), pagesList, heading);
        mPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabIndicator = parent.findViewById(R.id.indicator);
        tabIndicator.setViewPager(mPager);
        mTabsLinearLayout = ((LinearLayout) tabIndicator.getChildAt(0));
        setIndicatorColor(0);
        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerActivePage = position;
                setIndicatorColor(position);
                String symbolStr = selectedModel.getTradeSymbol();
                String exchStr = selectedModel.getExchange();

                if (resultDataSet.size() != 0) {

                    if (position == 0) {
                        if (symbolStr.equalsIgnoreCase(resultDataSet.get(0).getScriptName())) {

                            if ((disInstrNameList.contains("FUTSTK") || disInstrNameList.contains("FUTIDX") || disInstrNameList.contains("FUTCUR") || disInstrNameList.contains("FUTIVX") || disInstrNameList.contains("FUTCOM")) && disOptionTypeList.contains("XX")) {
                                //((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);
                                getResponseDataNew(symbolStr, exchStr, "future");


                            } else {
                                getResponseDataNew(symbolStr, exchStr, "future");
                            }

                        } else {
                            getResponseDataNew(symbolStr, exchStr, "future");
                        }

                    } else if (position == 1) {
                        if (symbolStr.equalsIgnoreCase(resultDataSet.get(0).getScriptName())) {

                            if ((disInstrNameList.contains("OPTSTK") || disInstrNameList.contains("OPTIDX") || disInstrNameList.contains("OPTCUR") || disInstrNameList.contains("OPTIVX") || disInstrNameList.contains("OPTCOM") || disInstrNameList.contains("OPTFUT")) && disOptionTypeList.contains("CE")) {
                                //((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);
                                getDistinctExpiry(symbolStr, exchStr, "calloption");
                            } else {
                                getDistinctExpiry(symbolStr, exchStr, "calloption");
                            }

                        } else {
                            getDistinctExpiry(symbolStr, exchStr, "calloption");
                        }
                    } else if (position == 2) {
                        if (symbolStr.equalsIgnoreCase(resultDataSet.get(0).getScriptName())) {

                            if ((disInstrNameList.contains("OPTSTK") || disInstrNameList.contains("OPTIDX") || disInstrNameList.contains("OPTCUR") || disInstrNameList.contains("OPTIVX") || disInstrNameList.contains("OPTCOM") || disInstrNameList.contains("OPTFUT")) && disOptionTypeList.contains("PE")) {
                                //((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);
                                getDistinctExpiry(symbolStr, exchStr, "putoption");
                            } else {
                                getDistinctExpiry(symbolStr, exchStr, "putoption");
                            }

                        } else {
                            getDistinctExpiry(symbolStr, exchStr, "putoption");
                        }
                    }

                } else {
                    if (position == 0) {
                        getResponseDataNew(symbolStr, exchStr, "future");
                    } else if (position == 1) {
                        getDistinctExpiry(symbolStr, exchStr, "calloption");
                    } else if (position == 2) {
                        getDistinctExpiry(symbolStr, exchStr, "putoption");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    */}


    private void setupAdapter() {

        int[] newsViewIDs = {R.id.symbolName, R.id.exchangeName};

        commonAdapter = new GreekCommonAdapter<>(SearchSynbolActivity.this, new ArrayList<MultiStockDetailsData>());
        commonAdapter.setLayoutTextViews(R.layout.row_symbol_search_results, newsViewIDs);
        if (AccountDetails.getThemeFlag(SearchSynbolActivity.this).equalsIgnoreCase("white")) {
            commonAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
        } else {
            commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        }

        commonAdapter.setPopulationListener(new GreekPopulationListener<MultiStockDetailsData>() {

            @Override
            public void populateFrom(View v, final int position, final MultiStockDetailsData row, View[] views) {
                ((GreekTextView) views[0]).setText(row.getDescription());
                ((GreekTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                ((GreekTextView) views[1]).setText(row.getExchange());
                ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }

            @Override
            public void onRowCreate(View[] views) {

            }
        });
        searchResultsList.setAdapter(commonAdapter);
    }

    private void sendAddSymbolRequest(String exchange, String token, String scripName) {

        String addSymbolURL;

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            addSymbolURL = "getPreviousViewedScripBySymbol?ClientCode=" + AccountDetails.getClientCode(SearchSynbolActivity.this) + "&ExchangeCode=" + exchange + "&Token=" + token + "&Segment=" + assetType.toLowerCase() + "&Symbol=" + scripName + "&gscid=" + AccountDetails.getDeviceID(SearchSynbolActivity.this);
        } else {
            addSymbolURL = "getPreviousViewedScripBySymbol?ClientCode=" + AccountDetails.getClientCode(SearchSynbolActivity.this) + "&ExchangeCode=" + exchange + "&Token=" + token + "&Segment=" + assetType.toLowerCase() + "&Symbol=" + scripName + "&gscid=" + AccountDetails.getUsername(SearchSynbolActivity.this);
        }


        WSHandler.getRequest(SearchSynbolActivity.this, addSymbolURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
//                    hideProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String message) {
                Log.d("exception", message);
            }
        });
    }

    private void sendLastSearchSymbolRequest() {
        String LatSearchSymbolURL;

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            LatSearchSymbolURL = "getPreviousViewedScripBySymbol?ClientCode=" + AccountDetails.getClientCode(SearchSynbolActivity.this) + "&gscid=" + AccountDetails.getDeviceID(SearchSynbolActivity.this);
        } else {
            LatSearchSymbolURL = "getPreviousViewedScripBySymbol?ClientCode=" + AccountDetails.getClientCode(SearchSynbolActivity.this) + "&gscid=" + AccountDetails.getUsername(SearchSynbolActivity.this);

        }

        Log.v(">>>>>>>>>>>>>>>>>", LatSearchSymbolURL);
        WSHandler.getRequest(SearchSynbolActivity.this, LatSearchSymbolURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
//                    hideProgress();


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


                } catch (Exception e) {
//                    hideProgress();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
//                hideProgress();
                Log.d("exception", message);
            }
        });
    }


    private void sendMultiQuoteRequest() {
//        showProgress();
        MarketsMultipleScripRequest.sendRequest(stockList, SearchSynbolActivity.this, serviceResponseHandler);
    }


    private void setIndicatorColor(int position) {
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            if (i == position) {
                tv.setTextColor(Color.WHITE);
            } else {
                tv.setTextColor(getResources().getColor(R.color.unselectedTxtColor));
            }
        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;


//        JSONResponse jsonResponse = (JSONResponse) response;

//        List<QuoteList> details = null;
        if (MULTIQUOTE_SVC_NAME.equals(jsonResponse.getServiceName()) && MULTIQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                multiQuoteResponse = (MarketsMultipleScripResponse) jsonResponse.getResponse();

                /*for(int i=0;i<multiQuoteResponse.getQuoteList().size();i++){
                    LastSerchSymbolData lastSerchSymbolData =new LastSerchSymbolData();
                    lastSerchSymbolData.setSymbol(multiQuoteResponse.getQuoteList().get(i).getSymbol());
                    symbolList.add(lastSerchSymbolData);*/
                isresponseReceived = true;

                sendtoAdpterdata(multiQuoteResponse);
                //lastSearchSymbolAdapter.notifyDataSetChanged();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (SYMBOLSEARCH_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && MULTIQUOTEDETAILS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                SearchMultiStockDetailsResponse mutiStockResponse = (SearchMultiStockDetailsResponse) jsonResponse.getResponse();
                StockDetail data = mutiStockResponse.getStockDetails().get(pos);
                NavigationModel navModel = new NavigationModel();
                navModel.from(data);
                handleFurtherNavigation(navModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("getInstrumentDetails".equals(jsonResponse.getServiceName())) {
            try {
                instrumentNameResponse = (SearchInstrumentNameResponse) jsonResponse.getResponse();
                handleInstName(instrumentNameResponse);
                instNameSpinner.setOnItemSelectedListener(instSelectionListener);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        hideProgress();
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {

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

    private void sendtoAdpterdata(MarketsMultipleScripResponse multiQuoteResponse) {

        lastSearchSymbol.setVisibility(View.VISIBLE);
        lastsearchTitletxt.setVisibility(View.GONE);
        List<QuoteList> details = multiQuoteResponse.getQuoteList();
        List<ScripModel> details_view = new ArrayList<>();

        String assetTypeSelected = assetTypeSpinner.getSelectedItem().toString();


        for (QuoteList qt : details) {

            if (!qt.getExch().equalsIgnoreCase("null")) {

                ScripModel scripModel = new ScripModel();
                scripModel.setScriptName(qt.getName());
                scripModel.setSymbol(qt.getSymbol());
                scripModel.setTradeSymbol(qt.getSymbol());
                scripModel.setExpiryDate(qt.getExpiryDate());
                scripModel.setExchange(qt.getExch());
                scripModel.setToken(qt.getToken());
                scripModel.setStrickPrice(qt.getStrikePrice());
                scripModel.setOptionType(qt.getOptionType());
                scripModel.setMultiplier(qt.getMultiplier());
                scripModel.setAssetType(qt.getAsset_type());
                scripModel.setDescription(qt.getName());
                scripModel.setInstrumentName(qt.getInstrumentName());
                scripModel.setChange(qt.getChange());
                scripModel.setP_change(qt.getP_change());
                scripModel.setLtp(qt.getLast());


                details_view.add(scripModel);
            }
        }


        lastSearchSymbolAdapter = new LastSearchSymbolAdapter(SearchSynbolActivity.this, details_view, assetTypeSpinner.getSelectedItem().toString());
        lastSearchSymbol.setAdapter(lastSearchSymbolAdapter);
        lastSearchSymbolAdapter.notifyDataSetChanged();
    }

    public void handleInstName(SearchInstrumentNameResponse response) {
        instNameList.clear();

        if (response != null) {
            for (int i = 0; i < response.getInstrumentNameModelList().size(); i++) {
                if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Nse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("equity") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("1")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Nse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("fno") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("2")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Nse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("currency") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("3")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("equity") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("4")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("fno") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("5")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("currency") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("6")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("mcx") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("commodity") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("9")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("ncdex") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("commodity") && response.getInstrumentNameModelList().get(i).getMarket_id().equalsIgnoreCase("7")) {
                    instNameList.add(response.getInstrumentNameModelList().get(i).getInstrumentName());
                }
            }

            instSpinAdapter.notifyDataSetChanged();

            if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Nse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("equity")) {
                getInstrumentIndex("EQ");
                instNameSpinner.setSelection(selectedInstIndex);
            } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Nse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("fno")) {
                getInstrumentIndex("FUTSTK");
                instNameSpinner.setSelection(selectedInstIndex);
            } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Nse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("currency")) {
                getInstrumentIndex("FUTCUR");
                instNameSpinner.setSelection(selectedInstIndex);
            } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("equity")) {
                getInstrumentIndex("A");
                instNameSpinner.setSelection(selectedInstIndex);
            } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("fno")) {
                //getInstrumentIndex("EQ");
                //instNameSpinner.setSelection(selectedInstIndex);
            } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bse") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("currency")) {
                //getInstrumentIndex("EQ");
                //instNameSpinner.setSelection(selectedInstIndex);
            } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("mcx") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("commodity")) {
                //getInstrumentIndex("EQ");
                //instNameSpinner.setSelection(selectedInstIndex);
            } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("ncdex") && assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("commodity")) {
                //getInstrumentIndex("EQ");
                //instNameSpinner.setSelection(selectedInstIndex);
            }


            if (instNameSpinner.getSelectedItem() != null) {
                autoCompleteAdapter.setInstName(instNameSpinner.getSelectedItem().toString());
            }

        }
    }

    public void getInstrumentIndex(String instName) {
        selectedInstIndex = 0;
        for (int i = 0; i < instNameList.size(); i++) {
            if (instNameList.get(i).equalsIgnoreCase(instName)) {
                selectedInstIndex = i;
            }
        }
    }

    public void handleFurtherNavigation(NavigationModel model) {
//        sendAddSymbolRequest(model.getExchangeName(), model.getToken(), model.getTradeSymbol());
        Bundle args = new Bundle();
        switch (pageSource) {
            case "Trade":
                args.clear();
                args.putString(TradeFragment.SCRIP_NAME, model.getScripName());
                args.putString(TradeFragment.EXCHANGE_NAME, model.getExchangeName());
                args.putString(TradeFragment.TOKEN, model.getToken());
                args.putString(TradeFragment.ASSET_TYPE, model.getAssetType());
                args.putString(TradeFragment.UNIQUEID, model.getUniqueId());
                args.putString(TradeFragment.TRADE_SYMBOL, model.getTradeSymbol());
                args.putString(TradeFragment.LOT_QUANTITY, model.getLots());
                args.putString(TradeFragment.TICK_SIZE, model.getTickSize());
                args.putString(TradeFragment.MULTIPLIER, model.getMultiplier());
                args.putString(TradeFragment.EXPIRYDATE, model.getExpiry());
                args.putString("instName", model.getInstType());
                args.putString("description", model.getDescription());
               /* if (previousFragment != null) {
                    //previousFragment.setArguments(args);
                    previousFragment.onFragmentResult(args);
                }
                goBackOnce();*/
                break;
            case "Watchlist":
                args.clear();
                args.putString(WatchListFragment.EXCHANGE_NAME, model.getExchangeName());
                args.putString(WatchListFragment.TRADE_SYMBOL, model.getTradeSymbol());
                args.putString(WatchListFragment.ASSET_TYPE, model.getAssetType());
                args.putString(WatchListFragment.ADDED_SYMBOL, model.getTradeSymbol());
                args.putString(WatchListFragment.TOKEN, model.getToken());
                args.putString(WatchListFragment.GOTO, "Watchlist");
               /* if (previousFragment != null)
                    previousFragment.onFragmentResult(args);
                goBackOnce();*/

                break;
            case "MFWatchlist":
                args.clear();
                args.putString(MFWatchlistFragment.AMCNAME, model.getAmcName());
                args.putString(MFWatchlistFragment.ASSET_TYPE, model.getAssetType());
                args.putString(MFWatchlistFragment.CORPISIN, model.getCorp_isin());
                args.putString(MFWatchlistFragment.SCHEMECODE, model.getMfSchemeCode());
                args.putString(MFWatchlistFragment.SCHEMENAME, model.getSchemeName());
                args.putString(MFWatchlistFragment.SIPISIN, model.getSip_isin());
                args.putString(MFWatchlistFragment.TRADINGISIN, model.getTrading_isin());
                args.putString(MFWatchlistFragment.BSECODE, model.getBseCode());
                args.putString(MFWatchlistFragment.BSERTACODE, model.getBseRTACode());
                args.putString(MFWatchlistFragment.GOTO, "MFWatchlist");
//                AccountDetails.currentFragment = NAV_TO_MF_WATCHLIST_MF;
              /*  if (previousFragment != null)
                    previousFragment.onFragmentResult(args);
                goBackOnce();*/

                break;
            case "Alert":
                args.clear();
                args.putString(AlertFragment.SCRIP_NAME, model.getScripName());
                args.putString(AlertFragment.TOKEN, model.getToken());
                args.putString(AlertFragment.ASSET_TYPE, model.getAssetType());
                args.putString(AlertFragment.FROM_PAGE, "alerts");

               /* if (previousFragment != null)
                    previousFragment.onFragmentResult(args);
                goBackOnce();*/

                break;
            case "Quote":
                args.clear();
                args.putString(QuotesFragment.SCRIP_NAME, model.getScripName());
                args.putString(QuotesFragment.EXCHANGE_NAME, model.getExchangeName());
                if (model.getAssetType().equalsIgnoreCase("future")) {
                    args.putString(QuotesFragment.ASSET_TYPE, "fno");
                } else {
                    args.putString(QuotesFragment.ASSET_TYPE, model.getAssetType().toLowerCase());
                }
                args.putString(QuotesFragment.UNIQUE_ID, model.getUniqueId());
                args.putString(QuotesFragment.TRADE_SYMBOL, model.getTradeSymbol());
                args.putString(QuotesFragment.TICK_SIZE, model.getTickSize());
                args.putString(QuotesFragment.INST_TYPE, model.getInstType());
                args.putString(QuotesFragment.EXPIRY, model.getExpiry());
                args.putString(QuotesFragment.MULTIPLIER, model.getMultiplier());
                args.putString(QuotesFragment.TOKEN, model.getToken());
                args.putString(QuotesFragment.FROM_PAGE, "symbolSearch");
                args.putString(QuotesFragment.OPTION_TYPE, model.getOptType());
                args.putString(QuotesFragment.STRIKE_PRICE, model.getStrickPrice());

//                navigateTo(NAV_TO_QUOTES_SCREEN, args, true);
                break;
            case "MFQuote":
                args.clear();
                args.putString("schemCode", model.getMfSchemeCode());
                args.putString("bseRTACode", model.getBseRTACode());
                args.putString("bseCode", model.getBseCode());
                args.putString("schemeName", model.getSchemeName());
                args.putString("tradingISIN", model.getTrading_isin());
                args.putString("sipISIN", model.getSip_isin());
                args.putBoolean("fromSearchSymbol", true);
//                navigateTo(NAV_TO_MUTUALFUND_GET_QUOTE, args, true);
                break;
        }
    }

   /* @Override
    public void onFragmentResult(Object data) {
        if (data != null) {
            if (((Bundle) data).getString("GoTO") != null) {
                if (previousFragment != null) previousFragment.onFragmentResult(data);
                goBackOnce();
            } else if (((Bundle) data).getString("Source") != null) {
                if (previousFragment != null) previousFragment.onFragmentResult(data);
                goBackOnce();
            }
        }
    }*/

   /* @Override
    public void onResume() {

        if (GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER) {
            lastSearchSymbol.setVisibility(View.VISIBLE);
            lastsearchTitletxt.setVisibility(View.VISIBLE);
            sendLastSearchSymbolRequest();
        } else {
            lastsearchTitletxt.setVisibility(View.GONE);
            lastSearchSymbol.setVisibility(View.GONE);

        }
        setAppTitle(getClass().toString(), "Search Stock/Contract");
        if (searchBoxView != null)
            searchBoxView.setText("");
        super.onResume();
    }*/

    @Override
    public void onResume() {


//        lastSearchSymbol.setVisibility(View.VISIBLE);
//        lastsearchTitletxt.setVisibility(View.VISIBLE);
//        sendLastSearchSymbolRequest();

        if (GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER) {
            lastSearchSymbol.setVisibility(View.VISIBLE);
//            lastsearchTitletxt.setVisibility(View.VISIBLE);
//            sendLastSearchSymbolRequest();
        } else {
            lastsearchTitletxt.setVisibility(View.GONE);
            lastSearchSymbol.setVisibility(View.GONE);

        }
       /* setAppTitle(getClass().toString(), "Search Stock/Contract");
        if (searchBoxView != null)
            searchBoxView.setText("");*/

        super.onResume();
    }

    public class ListCustomAdapter extends BaseAdapter {
        private final ArrayList<SearchPagerModel> arrayList;
        private LayoutInflater inflater = null;

        public ListCustomAdapter(Context context, ArrayList<SearchPagerModel> strAsset) {
            this.inflater = LayoutInflater.from(context);
            this.arrayList = strAsset;
        }

        public void clear() {
            arrayList.clear();
        }

        public void add(SearchPagerModel model) {
            arrayList.add(model);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public SearchPagerModel getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ListCustomAdapter.Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_symbol_search_mf, parent, false);
                holder = new ListCustomAdapter.Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ListCustomAdapter.Holder) convertView.getTag();
            }

            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(SearchSynbolActivity.this).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvDescription.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvExchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvExpiry.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(SearchSynbolActivity.this).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvDescription.setTextColor(getResources().getColor(textColor));
                holder.tvExchange.setTextColor(getResources().getColor(textColor));
                holder.tvExpiry.setTextColor(getResources().getColor(textColor));
            }


            if (getItem(position).getInstrumentName().equalsIgnoreCase("COMDTY")) {
                holder.tvDescription.setText(getItem(position).getDescription());
                holder.tvExchange.setText(getItem(position).getExchange());
                holder.tvExpiry.setText("");
            } else if (getItem(position).getInstrumentName().equalsIgnoreCase("COM")) {
                holder.tvDescription.setText(getItem(position).getDescription());
                holder.tvExchange.setText(getItem(position).getExchange());
                holder.tvExpiry.setText("");
            } else {
                if (getItem(position).getExchange().equalsIgnoreCase("mcx")) {
                    holder.tvDescription.setText(getItem(position).getScriptName() + getItem(position).getExpiryDate().trim() + (getItem(position).getStrickPrice()).substring(0, getItem(position).getStrickPrice().indexOf(".")) + getItem(position).getOptionType() + "-" + getItem(position).getInstrumentName());
                } else {
                    holder.tvDescription.setText(getItem(position).getDescription());
                }
                holder.tvExchange.setText(getItem(position).getExchange());
                holder.tvExpiry.setText(getItem(position).getExpiryDate());
            }

            return convertView;
        }

        public class Holder {
            final GreekTextView tvDescription;
            final GreekTextView tvExchange;
            final GreekTextView tvExpiry;

            public Holder(View parent) {
                tvDescription = parent.findViewById(R.id.tvDescription);
                tvExchange = parent.findViewById(R.id.tvExchange);
                tvExpiry = parent.findViewById(R.id.tvExpiry);
            }
        }
    }


    private void getResponseDataNew(String symbolStr, String exchStr, String type) {
//        showProgress();
        String url = null;

        symbolstring = symbolStr;
        exchstring = exchStr;
        typestring = type;

        byte[] data = symbolStr.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
        url = "getFullScripDetailsBySymbol_Mobile?exchange=" + exchStr + "&assetType=" + assetType.toLowerCase() + "&code=" + base64 + "&type=" + type;

        WSHandler.getRequest(SearchSynbolActivity.this, url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    JSONArray respCategory = response.getJSONArray("data");
                    commodityAdapter.clear();
                    commodityAdapter.notifyDataSetChanged();
                    resultDataSet.clear();
                    expiryDateList.clear();
                    expiryTimeStampList.clear();
                    expiryDateCommodityList.clear();
                    //expiryDateList.add("All");
                    expiryDateList.add(DateTimeFormatter.getDateFromTimeStamp(selectedModel.getExpiryDate(), "dd MMM yyyy", "bse"));

                    expiryTimeStampList.add(selectedModel.getExpiryDate());
                    //expiryDateCommodityList.add("All");
                    SearchPagerModel single;
                    for (int i = 0; i < respCategory.length(); i++) {
                        //JSONObject jsonObject = respCategory.getJSONObject(i);
                        single = new SearchPagerModel();
                        single.fromJSONObject(respCategory.getJSONObject(i));
                        commodityAdapter.add(single);
                        resultDataSet.add(single);

                        if (!disInstrNameList.contains(single.getInstrumentName())) {
                            disInstrNameList.add(single.getInstrumentName());
                        }

                        if (!disOptionTypeList.contains(single.getOptionType())) {
                            disOptionTypeList.add(single.getOptionType());
                        }


                        if (!single.getExpiryDate().isEmpty() && (!single.getExpiryDate().equalsIgnoreCase("-19800"))) {
                            if (!expiryDateList.contains(single.getExpiryDate())) {

                                expiryDateList.add(single.getExpiryDate());
                                expiryTimeStampList.add(single.getExpiryTimeStamp());

                            }
                            if (!expiryDateCommodityList.contains(single.getExpiryDate())) {
                                expiryDateCommodityList.add(single.getExpiryDate());
                            }
                        }

                        Bundle args = new Bundle();
                        args.putString(QuotesFragment.INST_TYPE, single.getInstrumentName());
                        args.putString(QuotesFragment.EXCHANGE_NAME, single.getExchange());

                    }
                    commodityAdapter.notifyDataSetChanged();
                    expiryDateAdapter.notifyDataSetChanged();
                    expiryDateCommodityAdapter.notifyDataSetChanged();

                    if (alreadySelectedExpry == null) {

                        if (expiryDateSpinner.getSelectedItem() != null) {

                            alreadySelectedExpry = expiryDateSpinner.getSelectedItem().toString();
                        }

                        expiryDateSpinner.setSelection(expiryDateList.indexOf(alreadySelectedExpry));
                        alreadySelectedExpry = null;


                    } else {
                        expiryDateSpinner.setSelection(expiryDateList.indexOf(alreadySelectedExpry));
                        alreadySelectedExpry = null;

                    }

                    //if (!assetType.equalsIgnoreCase("commodity")) {
                    FNOResultFragment.commonData = resultDataSet;
                    FNOResultFragment.commonExpiry = expiryDateSpinner.getSelectedItem().toString();
                    ((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);
                    //}

//                    hideProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
//                hideProgress();
                expiryDateAdapter.notifyDataSetChanged();
                expiryDateCommodityAdapter.notifyDataSetChanged();
                commodityAdapter.notifyDataSetChanged();
                FNOResultFragment.commonData = resultDataSet;
                ((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);

                Log.d("onFailure", message);
            }

        });
    }


    private void getOptionData(String symbolStr, String exchStr, String type, String expiryDateTS) {
//        showProgress();
        String url = null;

        symbolstring = symbolStr;
        exchstring = exchStr;
        typestring = type;
        FNOResultFragment.commonExpiry = expiryDateSpinner.getSelectedItem().toString();

        byte[] data = symbolStr.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);

        url = "getFullScripDetailsBySymbol_Option?exchange=" + exchStr + "&assetType=" + assetType.toLowerCase() + "&code=" + base64 + "&type=" + type + "&expiry=" + expiryDateTS;

        Log.e("SymbolSearch", "====>" + url);
        WSHandler.getRequest(SearchSynbolActivity.this, url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    JSONArray respCategory = response.getJSONArray("data");
                    commodityAdapter.clear();
                    commodityAdapter.notifyDataSetChanged();
                    resultDataSet.clear();
                    //expiryDateList.clear();
                    //expiryDateList.add("All");
                    //expiryDateCommodityList.clear();
                    //expiryDateCommodityList.add("All");
                    SearchPagerModel single;
                    for (int i = 0; i < respCategory.length(); i++) {
                        //JSONObject jsonObject = respCategory.getJSONObject(i);
                        single = new SearchPagerModel();
                        single.fromJSONObject(respCategory.getJSONObject(i));
                        commodityAdapter.add(single);
                        resultDataSet.add(single);

                        if (!disInstrNameList.contains(single.getInstrumentName())) {
                            disInstrNameList.add(single.getInstrumentName());
                        }

                        if (!disOptionTypeList.contains(single.getOptionType())) {
                            disOptionTypeList.add(single.getOptionType());
                        }


                       /* if (!single.getExpiryDate().isEmpty() && (!single.getExpiryDate().equalsIgnoreCase("-19800"))) {
                            if (!expiryDateList.contains(single.getExpiryDate())) {

                                expiryDateList.add(single.getExpiryDate());

                            }
                            if (!expiryDateCommodityList.contains(single.getExpiryDate())) {
                                expiryDateCommodityList.add(single.getExpiryDate());
                            }
                        }*/

                        Bundle args = new Bundle();
                        args.putString(QuotesFragment.INST_TYPE, single.getInstrumentName());
                        args.putString(QuotesFragment.EXCHANGE_NAME, single.getExchange());

                    }
                    commodityAdapter.notifyDataSetChanged();
                    //expiryDateAdapter.notifyDataSetChanged();
                    //expiryDateCommodityAdapter.notifyDataSetChanged();
                    //if (!assetType.equalsIgnoreCase("commodity")) {
                    FNOResultFragment.commonData = resultDataSet;
                    ((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);
                    //}

//                    hideProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
//                hideProgress();
                //expiryDateAdapter.notifyDataSetChanged();
                //expiryDateCommodityAdapter.notifyDataSetChanged();
                commodityAdapter.notifyDataSetChanged();
                FNOResultFragment.commonData = resultDataSet;
                ((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);

                Log.d("onFailure", message);
            }

        });
    }


    private void getDistinctExpiry(final String symbolStr, final String exchStr, final String type) {

//        showProgress();
        String url = null;

        symbolstring = symbolStr;
        exchstring = exchStr;
        typestring = type;

        byte[] data = symbolStr.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
        url = "getFullScripDetailsBySymbol_Expiry?exchange=" + exchStr + "&assetType=" + assetType.toLowerCase() + "&code=" + base64 + "&type=" + type;


        WSHandler.getRequest(SearchSynbolActivity.this, url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    JSONArray respCategory = response.getJSONArray("data");
                    commodityAdapter.clear();
                    commodityAdapter.notifyDataSetChanged();
                    //resultDataSet.clear();
                    expiryDateList.clear();
                    expiryTimeStampList.clear();
                    //expiryDateList.add("All");
                    expiryDateCommodityList.clear();
                    //expiryDateCommodityList.add("All");
                    SearchPagerModel single;
                    for (int i = 0; i < respCategory.length(); i++) {
                        //JSONObject jsonObject = respCategory.getJSONObject(i);
                        single = new SearchPagerModel();
                        single.fromJSONObject(respCategory.getJSONObject(i));
                        //commodityAdapter.add(single);
                        //resultDataSet.add(single);
                        if (!single.getExpiry().isEmpty() && (!single.getExpiry().equalsIgnoreCase("-19800"))) {
                            if (!expiryDateList.contains(single.getExpiry())) {

                                expiryDateList.add(single.getExpiry());
                                expiryTimeStampList.add(single.getExpiryTimeStamp());

                            }
                            if (!expiryDateCommodityList.contains(single.getExpiry())) {
                                expiryDateCommodityList.add(single.getExpiry());
                            }
                        }


                    }
                    //commodityAdapter.notifyDataSetChanged();
                    expiryDateAdapter.notifyDataSetChanged();
                    expiryDateCommodityAdapter.notifyDataSetChanged();

                    if (alreadySelectedExpry == null) {

                        if (expiryDateSpinner.getSelectedItem() != null) {
                            alreadySelectedExpry = expiryDateSpinner.getSelectedItem().toString();
                        }

                        expiryDateSpinner.setSelection(expiryDateList.indexOf(alreadySelectedExpry));
                        alreadySelectedExpry = null;


                    } else {
                        expiryDateSpinner.setSelection(expiryDateList.indexOf(alreadySelectedExpry));

                    }

                    //if (!assetType.equalsIgnoreCase("commodity")) {
                    //FNOResultFragment.commonData = resultDataSet;
                    //((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);
                    //}

                    //String expirydateTimestamp = String.valueOf(ConvertToLong(expiryDateSpinner.getSelectedItem().toString()));
                    String expirydateTimestamp = String.valueOf(expiryTimeStampList.get(expiryDateList.indexOf(expiryDateSpinner.getSelectedItem().toString())));


                    getOptionData(symbolStr, exchStr, type, expirydateTimestamp);

//                    hideProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
//                hideProgress();
                expiryDateAdapter.notifyDataSetChanged();
                expiryDateCommodityAdapter.notifyDataSetChanged();
                commodityAdapter.notifyDataSetChanged();
                FNOResultFragment.commonData = resultDataSet;
                ((FNOResultFragment) pagesList.get(pagerActivePage)).setDataSet(selecteScrip);

                Log.d("onFailure", message);
            }

        });
    }


    private Long ConvertToLong(String expdate) throws ParseException {

        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        Date d = f.parse(expdate);
        long dateExpiry = d.getTime();

        long output = d.getTime() / 1000L;
        output = output + 52200;

        return Long.valueOf(output);
    }

    public void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}


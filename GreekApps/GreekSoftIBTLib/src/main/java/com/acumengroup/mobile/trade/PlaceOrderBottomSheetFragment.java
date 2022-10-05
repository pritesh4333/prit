package com.acumengroup.mobile.trade;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.acumengroup.greekmain.core.model.MarketStatusPostRequest;
import com.acumengroup.greekmain.core.model.MarketStatusPostResponse;
import com.acumengroup.greekmain.core.model.portfoliotrending.AllowedProduct;
import com.acumengroup.mobile.login.UserKycValidation;
import com.acumengroup.mobile.model.SecurityInfoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.LabelConfig;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.MarketStatusResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerBannedResponse;
import com.acumengroup.greekmain.core.network.MarginDetailRequest;
import com.acumengroup.greekmain.core.network.MarginDetailResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.login.CustomTransactionDialogFragment;
import com.acumengroup.mobile.menu.MenuGetter;
import com.acumengroup.ui.AppConstants;
import com.acumengroup.ui.CustomViewPager;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CustomFragmentPagerAdapter;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.acumengroup.greekmain.util.inputfilter.InputFilterBeforeAfterDecimalDigits;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

import static com.acumengroup.greekmain.core.constants.GreekConstants.Catalyst_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.DESCRIPTION;
import static com.acumengroup.greekmain.core.constants.GreekConstants.Delivery_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.EXPIRYDATE;
import static com.acumengroup.greekmain.core.constants.GreekConstants.Intraday_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.MTF_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.NAV_TO_ORDER_PREVIEW_SCREEN;
import static com.acumengroup.greekmain.core.constants.GreekConstants.NAV_TO_TRADE_SCREEN;
import static com.acumengroup.greekmain.core.constants.GreekConstants.SSEQ_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.TNC_product;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.MARKETS_SVC_GROUP;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.PREQUOTEDETAILS_SVC_NAME;
import static com.acumengroup.greekmain.core.constants.ServiceConstants.SINGLEQUOTE_SVC_NAME;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.acumengroup.mobile.R.id.cSwitch_textView;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;


public class PlaceOrderBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener, LabelConfig, DatePickerDialog.OnDateSetListener, GreekUIServiceHandler {

    public static final String SCRIP_NAME = "ScripName", EXCHANGE_NAME = "ExchangeName", TOKEN = "Token", ASSET_TYPE = "AssetType", TRADE_SYMBOL = "TradeSymbol", LOT_QUANTITY = "Lots", TRADE_ACTION = "Action", MULTIPLIER = "Multiplier", TICK_SIZE = "TickSize", IS_MODIFY_ORDER = "isModifyOrder", SYMBOL_NAME = "SymbolName", PRICE = "Price";
    public static final double MAX_MULTIPLIER = 10000000;
    private OrderStreamingController orderStreamingController;
    private StreamingController streamController;
    private boolean valid_status = false;// if valid_status is true means respective market is offline or closed and vice-versa===================>>>>>
    public boolean amoAllowed = true, isPreOpen_status = false, isPostOpen_status = false, orderTimeFlag, allowed_market_bool = false, keepChangingText = true;
    private String isinumber, streamingSymbol = "", expiry, pageSource = "", lotQty = "";
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    private View tradeView;
    private View parent;
    private Spinner prodTypeSpinner, orderTypeSpinner, orderLifeSpinner, mainOrderTypeSpinner;
    private RadioButton buyBtn, sellBtn;
    private GreekButton placeOrderBtn, cancelbtn, gtdButton;
    private AppCompatCheckBox amoCheckBox, amoCheckBoxOne;
    private CustomTransactionDialogFragment customTransactionDialogFragment;
    private LinearLayout mTabsLinearLayout, addfieldslayout, bottomsheet_header, bottomesheet_edittext, bottomsheet_spinner, pager_layouts, buy_sell_layout;
    private ArrayList<String> sym = new ArrayList<>();
    private Calendar calendar;
    private int year, month, day, selectedAssetType;
    public ServiceResponseHandler serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
    private final ArrayList<Fragment> pagesList = new ArrayList<>();
    private Switch cSwitch;
    private GreekTextView cSwitchText;
    private GreekTextView symbols_exchange_text, last, time, change, bid_price, limit_price, holding_txt,
            symbols_description_text, symbols_exchange, rupee, last_price_text, change_text, ask_price, gtd_label, symbol_name_text, buysellTypeTxt, change_txt, symName;
    private GreekEditText slTrigPrice_txt, price_txt, qty_txt, value_text, discQty_txt, targetprice_txt, slprice_txt;
    private ArrayAdapter<String> prodTypeSpAdapter, orderTypeSpAdapter, orderLifeSpAdapter, mainOrderTypeSpAdapter;
    private MarketsSingleScripResponse quoteResponse = null;
    private List<String> productType, normalOrderType, orderLife, plusOrderType, mainOrderType;
    private String action, title, tradeSymbol, tickSize, modifyAction, assetType = "", multiplier = "", scripName = "", token = "", exchangeName = "";
    private boolean amoCheck = false, amoCheckOne = false;
    private GreekEditText gtd_text;
    public String nse_eq = "red", bse_eq = "red", nse_fno = "red", bse_fno = "red", nse_cd = "red", bse_cd = "red", mcx_com = "red", ncdex_com = "red";
    private boolean eq, bseeq = false;
    private boolean fno, bsefno = false;
    private boolean cd, bsecd = false;
    private boolean issentsetQuoteDetails = false;


    private final AdapterView.OnItemSelectedListener productTypeSelectionListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(!getArguments().getBoolean("isModifyOrder")) {
                if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(Delivery_product))) {


                    mainOrderType.remove("Bracket");
                    mainOrderType.remove("Cover");

                    if(!mainOrderType.contains("Regular")) {
                        mainOrderType.add(0, "Regular");
                    }
                    if(!mainOrderType.contains("AMO")) {
                        mainOrderType.add(1, "AMO");
                    }

                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);

                    mainOrderTypeSpAdapter.notifyDataSetChanged();
                    mainOrderTypeSpinner.setSelection(0);


                    if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                        gtd_text.setVisibility(View.VISIBLE);
                    } else {
                        gtd_text.setVisibility(View.INVISIBLE);
                    }
                    orderLifeSpinner.setEnabled(true);


                    if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                        gtd_text.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                    } else {
                        gtd_text.setVisibility(View.INVISIBLE);
                        addfieldslayout.setVisibility(View.GONE);
                        targetprice_txt.setVisibility(View.GONE);
                        slprice_txt.setVisibility(View.GONE);
                        slTrigPrice_txt.setEnabled(false);
                        /* slTrigPrice_txt.setVisibility(View.GONE);*/
                    }


                    if(orderTypeSpinner.getSelectedItem() != null && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop loss")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                          orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);

                    } else if(orderTypeSpinner.getSelectedItem() != null && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss Market")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    } else {

                        orderTypeSpinner.setEnabled(true);
                    }

                }
                else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(Intraday_product))) {

                    if(!mainOrderType.contains("Regular")) {
                        mainOrderType.add(0, "Regular");
                    }


                    if(!mainOrderType.contains("Bracket")) {
                        mainOrderType.add(1, "Bracket");
                    }
                    if (!mainOrderType.contains("Cover")) {
                        mainOrderType.add(2, "Cover");
                    }
                    if(!mainOrderType.contains("AMO")) {
                        mainOrderType.add(3, "AMO");
                    }
                    if(!getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("MCX")) {


                    }else{
                        if(!mainOrderType.contains("AMO")) {
                            mainOrderType.add(2, "AMO");
                        }
                    }
                    mainOrderTypeSpAdapter.notifyDataSetChanged();

                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                    orderLifeSpinner.setEnabled(true);


                    if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.VISIBLE);
                        slprice_txt.setVisibility(View.VISIBLE);

                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);

                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);

                        orderLifeSpinner.setSelection(0);
                        orderLifeSpinner.setEnabled(false);

                        orderTypeSpinner.setSelection(0);
                        orderTypeSpinner.setEnabled(false);


                    } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.VISIBLE);
                        orderLifeSpinner.setSelection(1);
                        orderLifeSpinner.setEnabled(false);

                        orderTypeSpinner.setSelection(0);
                        orderTypeSpinner.setEnabled(false);

                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);

                    } else {

                        if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                            gtd_text.setVisibility(View.VISIBLE);
                            targetprice_txt.setVisibility(View.INVISIBLE);
                            slprice_txt.setVisibility(View.INVISIBLE);
                        } else {
                            gtd_text.setVisibility(View.INVISIBLE);
                            addfieldslayout.setVisibility(View.GONE);
                            targetprice_txt.setVisibility(View.GONE);
                            slprice_txt.setVisibility(View.GONE);
                            slTrigPrice_txt.setEnabled(false);
                        }

                        orderTypeSpinner.setEnabled(true);
                        // mainOrderTypeSpAdapter.notifyDataSetChanged();
                    }


                    if(orderTypeSpinner.getSelectedItem() != null && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop loss")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    }
                    else if(orderTypeSpinner.getSelectedItem() != null && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss Market")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    }
                    else {

                    }


                }
                else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(MTF_product))) {


                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                    mainOrderType.remove("Bracket");
                    mainOrderType.remove("Cover");

                    if(!mainOrderType.contains("Regular")) {
                        mainOrderType.add(0, "Regular");
                    }
                    if(!mainOrderType.contains("AMO")) {
                        mainOrderType.add(1, "AMO");
                    }

                    mainOrderTypeSpAdapter.notifyDataSetChanged();


                    if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                        gtd_text.setVisibility(View.VISIBLE);
                    } else {
                        gtd_text.setVisibility(View.INVISIBLE);
                    }
                    orderLifeSpinner.setEnabled(true);

                    if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                        gtd_text.setVisibility(View.VISIBLE);
                    } else {
                        gtd_text.setVisibility(View.INVISIBLE);
                        addfieldslayout.setVisibility(View.GONE);
                        targetprice_txt.setVisibility(View.GONE);
                        slprice_txt.setVisibility(View.GONE);
                    }
                    orderTypeSpinner.setEnabled(true);


                    if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop loss")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    } else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss Market")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    } else {

                    }

                }
                else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(Catalyst_product))) {
                    mainOrderTypeSpAdapter.remove("Regular");
                    mainOrderTypeSpAdapter.remove("AMO");


                    if(!mainOrderType.contains("Bracket")) {
                        mainOrderType.add(0, "Bracket");

                    }
//                    if(!getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("MCX")) {
                        if (!mainOrderType.contains("Cover")) {
                            mainOrderType.add(1, "Cover");
//                        }
                    }

                    mainOrderTypeSpAdapter.notifyDataSetChanged();
                    mainOrderTypeSpinner.setSelection(0);


                    if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                        orderTypeSpinner.setSelection(0);
                        orderTypeSpinner.setEnabled(false);

                        gtd_text.setVisibility(View.GONE);
                        addfieldslayout.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.VISIBLE);
                        slprice_txt.setVisibility(View.VISIBLE);

                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);

                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);

                        orderLifeSpinner.setSelection(0);
                        orderLifeSpinner.setEnabled(false);


                    }
                    else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

//                    orderTypeSpinner.setSelection(0);
                        orderTypeSpinner.setEnabled(false);

                        addfieldslayout.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.VISIBLE);
                        orderLifeSpinner.setSelection(1);
                        orderLifeSpinner.setEnabled(false);
                        gtd_text.setVisibility(View.GONE);
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);


                    } else {
//                    mainOrderTypeSpAdapter.remove("Regular");
//                    mainOrderTypeSpAdapter.remove("AMO");
//                    mainOrderTypeSpAdapter.notifyDataSetChanged();

                        addfieldslayout.setVisibility(View.GONE);
                        targetprice_txt.setVisibility(View.GONE);
                        slprice_txt.setVisibility(View.GONE);


                        orderTypeSpinner.setEnabled(true);
                    }


                }
                else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(SSEQ_product))) {


                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                    mainOrderType.remove("Bracket");
                    mainOrderType.remove("Cover");
                    if(!mainOrderType.contains("Regular")) {
                        mainOrderType.add(0, "Regular");
                    }
                    if(!mainOrderType.contains("AMO")) {
                        mainOrderType.add(1, "AMO");
                    }
                    mainOrderTypeSpinner.setSelection(0);


                    mainOrderTypeSpAdapter.notifyDataSetChanged();


                    if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                        gtd_text.setVisibility(View.VISIBLE);
                    } else {
                        gtd_text.setVisibility(View.INVISIBLE);
                    }

                    orderLifeSpinner.setEnabled(true);
                    orderLifeSpinner.setSelection(0);

                    if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                        gtd_text.setVisibility(View.VISIBLE);
                    } else {
                        gtd_text.setVisibility(View.INVISIBLE);
                        addfieldslayout.setVisibility(View.GONE);
                        targetprice_txt.setVisibility(View.GONE);
                        slprice_txt.setVisibility(View.GONE);
                        slTrigPrice_txt.setEnabled(false);
                    }

                    orderTypeSpinner.setEnabled(true);


                    if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop loss")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    } else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss Market")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    } else {

                    }


                }
                else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(TNC_product))) {

                    mainOrderType.remove("Bracket");
                    mainOrderType.remove("Cover");

                    if(!mainOrderType.contains("Regular")) {
                        mainOrderType.add(0, "Regular");
                    }
                    if(!mainOrderType.contains("AMO")) {
                        mainOrderType.add(1, "AMO");
                    }


                    mainOrderTypeSpAdapter.notifyDataSetChanged();


                    mainOrderTypeSpinner.setSelection(0);
                    if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("AMO")) {

                    } else {
//                    mainOrderType.remove("Bracket");
//                    mainOrderType.remove("Cover");
//                    mainOrderTypeSpAdapter.notifyDataSetChanged();

                    }

                    if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {
                        gtd_text.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                    } else {
                        gtd_text.setVisibility(View.INVISIBLE);
                        addfieldslayout.setVisibility(View.GONE);
                        targetprice_txt.setVisibility(View.GONE);
                        slprice_txt.setVisibility(View.GONE);
                        slTrigPrice_txt.setEnabled(false);
                    }
                    orderLifeSpinner.setEnabled(true);
                    orderTypeSpinner.setEnabled(true);


                    if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop loss")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    } else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss Market")) {

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        orderLifeSpinner.setSelection(0);
                        if(orderLife.contains("IOC")){
                            orderLife.remove("IOC");
                        }
//                        orderLifeSpinner.setEnabled(false);


                    } else {

                    }
                }

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {


        }
    };

    private final AdapterView.OnItemSelectedListener orderLifeSelectionListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            gtd_text.setVisibility(View.GONE);

            if(position == 1) {
//                gtd_text.setVisibility(View.INVISIBLE);
                if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Cover")) {
                    addfieldslayout.setVisibility(View.GONE);
                }

                if(exchangeName != null) {
                    if(exchangeName.equalsIgnoreCase("mcx")) {
                        if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(true);
                        } else if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(false);
                        } else if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtd")) {
                            addfieldslayout.setVisibility(View.VISIBLE);
                            gtd_text.setVisibility(View.VISIBLE);
                            targetprice_txt.setVisibility(View.INVISIBLE);
                            slprice_txt.setVisibility(View.INVISIBLE);

                        } else {
                            gtd_text.setVisibility(View.GONE);
                        }
                    } else {
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                    }
                }
            }
            if(position == 0) {

//                gtd_text.setVisibility(View.INVISIBLE);
                if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bracket") &&
                        !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop Loss") &&
                        !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss Market")) {
                    addfieldslayout.setVisibility(View.GONE);
                }
                if(assetType != null) {
                    if(assetType.equalsIgnoreCase("equity") || assetType.equalsIgnoreCase("currency") && orderTypeSpinner.getSelectedItemPosition() != 2 && orderTypeSpinner.getSelectedItemPosition() != 5) {

                        if(getArguments().containsKey("DisQty")) {
                            if(Integer.parseInt(getArguments().getString("DisQty")) > 0) {
                                discQty_txt.setText(getArguments().getString("DisQty"));

                            }
                            if (!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bracket")) {
                                discQty_txt.setEnabled(true);
                            }
                        }else if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(false);
                        }else {
                            discQty_txt.setText("");
                            if (!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bracket")) {
                                discQty_txt.setEnabled(true);
                            }
                        }
                    } else if(assetType.equalsIgnoreCase("commodity")) {

                        if(exchangeName.equalsIgnoreCase("mcx") || exchangeName.equalsIgnoreCase("ncdex")) {
                            if(getArguments().containsKey("DisQty")) {
                                if(Integer.parseInt(getArguments().getString("DisQty")) != Integer.parseInt(qty_txt.getText().toString())) {
//                                    discQty_txt.setText(getArguments().getString("DisQty"));
                                    discQty_txt.setText(String.valueOf((Integer.parseInt(getArguments().getString("DisQty"))) / (Integer.parseInt(getArguments().getString("Lots")))));
                                    if (!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bracket")) {
                                        discQty_txt.setEnabled(true);
                                    }
                                }
                            }
                        } else {
                            discQty_txt.setText("");
                            if (!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bracket")) {
                                discQty_txt.setEnabled(true);
                            }
                        }

                    } else {
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                    }
                }
            }
            if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("GTD")) {

                if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtd")) {
                    addfieldslayout.setVisibility(View.VISIBLE);
                    gtd_text.setVisibility(View.VISIBLE);
                    targetprice_txt.setVisibility(View.INVISIBLE);
                    slprice_txt.setVisibility(View.INVISIBLE);

                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);

                } else {
                    gtd_text.setVisibility(View.GONE);
                }

                if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                }
            }
            if(position == 3) {
                if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                }
            }

            if(getArguments() != null && getArguments().getBoolean("isSquareOff")) {
                mainOrderTypeSpinner.setEnabled(false);
                orderTypeSpinner.setEnabled(true);
                prodTypeSpinner.setEnabled(false);
                orderLifeSpinner.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener mainOrderTypeSelectionListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //if(GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER) {
            if(orderLife.size()>0){
                orderLifeSpinner.setSelection(0);
            }

            gtd_text.setVisibility(View.GONE);

            if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {
                amoCheck = false;
                amoCheckOne = false;

                if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {


                    if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") ||
                            orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("limit")) {
                        orderTypeSpinner.setEnabled(true);
                    } else {
                        orderTypeSpinner.setSelection(0);
                        orderTypeSpinner.setEnabled(true);
                    }

                    if(getArguments() != null && !getArguments().getBoolean("isSquareOff")
                        /*&&!getArguments().getString("Product").equals("3")*/) {


//                            if(!productType.contains("TNC")) {
//                                prodTypeSpAdapter.add("TNC");
//                            }
//                            prodTypeSpAdapter.notifyDataSetChanged();

                        if(assetType.equalsIgnoreCase("equity")) {

                            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                                    /*if(AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("TNC")) {
                                        if(!productType.contains("TNC")) {
                                            prodTypeSpAdapter.add("TNC");
//                                    prodTypeSpAdapter.remove("TNC");
                                        }
                                    }*/

                                if(AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(TNC_product)) {
                                    if(!productType.contains(getProduct(TNC_product))) {
                                        prodTypeSpAdapter.add(getProduct(TNC_product));
//                                    prodTypeSpAdapter.remove("TNC");
                                    }
                                }
                            }
                            prodTypeSpAdapter.notifyDataSetChanged();
                        }
                    }

                    if(action.equals("Buy") || action.equals("Sell")) {

                        if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("limit")) {
                            orderLifeSpinner.setEnabled(true);
                            prodTypeSpinner.setEnabled(true);
                            slTrigPrice_txt.setText("");
                            slTrigPrice_txt.setEnabled(false);
                            price_txt.setEnabled(true);
                            addfieldslayout.setVisibility(View.GONE);
                            slTrigPrice_txt.setEnabled(false);
                            if(!getArguments().getBoolean("isSquareOff")) {
                                qty_txt.requestFocus();
                            } else {
                                price_txt.requestFocus();
                            }
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(false);
                            orderLifeSpinner.setEnabled(true);
                            targetprice_txt.setVisibility(View.INVISIBLE);
                            slprice_txt.setVisibility(View.INVISIBLE);
                            if("commodity".equalsIgnoreCase(assetType)) {
                                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                                orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity()) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                            v.setTextColor(getResources().getColor(R.color.black));
                                        } else {
                                            v.setTextColor(getResources().getColor(R.color.white));
                                        }
                                        v.setPadding(15, 10, 15, 12);
                                        return v;
                                    }

                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        v.setTextColor(Color.BLACK);
                                        v.setPadding(15, 15, 15, 15);
                                        return v;
                                    }
                                };
                                orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                                orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                                orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                                orderLifeSpinner.setSelection(0);
                            }
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                                if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                                    discQty_txt.setEnabled(true);
                                }
                            }

                        } else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market")) {
                            orderLifeSpinner.setEnabled(true);
                            if(getArguments() != null && !getArguments().getBoolean("isSquareOff")) {
                                prodTypeSpinner.setEnabled(true);
                            }
                            addfieldslayout.setVisibility(View.GONE);
                            slTrigPrice_txt.setEnabled(false);
                            slTrigPrice_txt.setText("");
                            price_txt.setEnabled(false);
                            price_txt.setText("");
                            // profitPrice_txt.setEnabled(false);
                            // profitPrice_txt.setText("");
                            // slLimitPrice_txt.setEnabled(false);
                            // slLimitPrice_txt.setText("");
                            price_txt.clearFocus();
                            slTrigPrice_txt.clearFocus();
                            orderLifeSpinner.setEnabled(true);
                            discQty_txt.setEnabled(false);
                            discQty_txt.setText("");
                            targetprice_txt.setVisibility(View.INVISIBLE);
                            slprice_txt.setVisibility(View.INVISIBLE);

                            if("commodity".equalsIgnoreCase(assetType)) {
                                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                                orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(),
                                        AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity()) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                            v.setTextColor(getResources().getColor(R.color.black));
                                        } else {
                                            v.setTextColor(getResources().getColor(R.color.white));
                                        }
                                        v.setPadding(15, 10, 15, 12);
                                        return v;
                                    }

                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        v.setTextColor(Color.BLACK);
                                        v.setPadding(15, 15, 15, 15);
                                        return v;
                                    }
                                };
                                orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                                orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                                orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                            }
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                                if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                                    discQty_txt.setEnabled(true);
                                }
                            }
                        } else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss")) {
                            orderLifeSpinner.setEnabled(true);
                            prodTypeSpinner.setEnabled(true);
                            addfieldslayout.setVisibility(View.VISIBLE);
                            slTrigPrice_txt.setEnabled(true);
                            price_txt.setEnabled(true);
                            //slLimitPrice_txt.setEnabled(false);
                            // slLimitPrice_txt.setText("");
                            price_txt.clearFocus();
                            slTrigPrice_txt.clearFocus();
                            //profitPrice_txt.setEnabled(false);
                            // profitPrice_txt.setText("");
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(false);
                            orderLifeSpinner.setSelection(0);
                            orderLifeSpinner.setEnabled(false);
                            targetprice_txt.setVisibility(View.INVISIBLE);
                            slprice_txt.setVisibility(View.INVISIBLE);
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(false);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(false);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                                discQty_txt.setEnabled(true);
                                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                                orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodityforStoploss()) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                            v.setTextColor(getResources().getColor(R.color.black));
                                        } else {
                                            v.setTextColor(getResources().getColor(R.color.white));
                                        }
                                        v.setPadding(15, 10, 15, 12);
                                        return v;
                                    }

                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        v.setTextColor(Color.BLACK);
                                        v.setPadding(15, 15, 15, 15);
                                        return v;
                                    }
                                };
                                orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                                orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                                orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                                orderLifeSpinner.setEnabled(true);
                            }

                        } else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                            addfieldslayout.setVisibility(View.VISIBLE);
                            slTrigPrice_txt.setEnabled(true);
                            price_txt.setText("");
                            price_txt.setEnabled(false);
                            //slLimitPrice_txt.setEnabled(false);
                            price_txt.clearFocus();
                            slTrigPrice_txt.clearFocus();
                            //profitPrice_txt.setEnabled(false);
                            discQty_txt.setEnabled(false);
                            discQty_txt.setText("");
                            orderLifeSpinner.setSelection(0);
                            orderLifeSpinner.setEnabled(false);

                            targetprice_txt.setVisibility(View.INVISIBLE);
                            slprice_txt.setVisibility(View.INVISIBLE);
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(false);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(false);
                            if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                        }
                    }
                }
                //  SJS - Modify order
                else {

                    if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market")) {
                        addfieldslayout.setVisibility(View.GONE);
                        slTrigPrice_txt.setEnabled(false);
                        price_txt.setEnabled(false);
                        slTrigPrice_txt.setText("");
                        price_txt.setText("");
                        //slLimitPrice_txt.setEnabled(false);
                        // profitPrice_txt.setText("");
                        // profitPrice_txt.setEnabled(false);
                        qty_txt.requestFocus();
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);

                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < orderLife.size(); i++) {
                            if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                                orderLifeSpinner.setSelection(i);
                                break;
                            }
                        }
                        orderLifeSpinner.setEnabled(true);


                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                            discQty_txt.setEnabled(true);
                            if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                                discQty_txt.setText(getArguments().getString("DisQty"));
                        }
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                            discQty_txt.setEnabled(true);
                            if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                                discQty_txt.setText(getArguments().getString("DisQty"));
                        }
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);

                        if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                            discQty_txt.setEnabled(true);
                            if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0"))) {
                                if(exchangeName.equalsIgnoreCase("mcx") || exchangeName.equalsIgnoreCase("ncdex")) {
                                    int pendinglotqty = Integer.parseInt(getArguments().getString("DisQty")) / Integer.parseInt(getArguments().getString("Lots"));
                                    discQty_txt.setText(String.valueOf(pendinglotqty));
                                } else {
                                    discQty_txt.setText(getArguments().getString("DisQty"));
                                }
                            }
                        }
                        if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                    }
                    else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Limit")) {
                        addfieldslayout.setVisibility(View.GONE);
                        slTrigPrice_txt.setEnabled(false);
                        slTrigPrice_txt.setText("");
                        price_txt.setEnabled(true);
                        price_txt.setText(getArguments().getString(PRICE));
                        //slLimitPrice_txt.setEnabled(false);
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        // profitPrice_txt.setEnabled(false);

                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                        qty_txt.clearFocus();
                        qty_txt.setFocusable(true);
                        qty_txt.selectAll();
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                            for (int i = 0; i < orderLife.size(); i++) {
                            if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                                orderLifeSpinner.setSelection(i);
                                break;
                            }
                        }
                        orderLifeSpinner.setEnabled(true);

                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                            discQty_txt.setEnabled(true);
                            if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                                discQty_txt.setText(getArguments().getString("DisQty"));
                        }
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                            discQty_txt.setEnabled(true);
                            if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                                discQty_txt.setText(getArguments().getString("DisQty"));
                        }
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 2) {
                            discQty_txt.setEnabled(true);
                            if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                                discQty_txt.setText(getArguments().getString("DisQty"));
                        }


                        if("commodity".equalsIgnoreCase(assetType) && (orderLifeSpinner.getSelectedItemPosition() == 0 || orderLifeSpinner.getSelectedItemPosition() == 2 || orderLifeSpinner.getSelectedItemPosition() == 3 || orderLifeSpinner.getSelectedItemPosition() == 4)) {
                            discQty_txt.setEnabled(true);
                            if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0"))) {
                                if("mcx".equalsIgnoreCase(exchangeName) || "ncdex".equalsIgnoreCase(exchangeName)) {
                                    if(Integer.parseInt(getArguments().getString("DisQty")) != Integer.parseInt(qty_txt.getText().toString())) {
                                        int disclsQty = Integer.parseInt(getArguments().getString("DisQty")) / Integer.parseInt(getArguments().getString("Lots"));
                                        discQty_txt.setText(String.valueOf(disclsQty));
                                    } else {
                                        discQty_txt.setText(getArguments().getString("DisQty"));
                                    }
                                } else {
                                    discQty_txt.setText(getArguments().getString("DisQty"));
                                }
                            }
                        }
                        if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                    }
                    else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop Loss")) {
                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);
                        price_txt.setText(getArguments().getString(PRICE));
                        //slLimitPrice_txt.setEnabled(false);
                        // slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        // profitPrice_txt.setEnabled(false);
                        discQty_txt.setEnabled(false);
                        discQty_txt.setText("");
                        for (int i = 0; i < orderLife.size(); i++) {
                            if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                                orderLifeSpinner.setSelection(i);
                                break;
                            }
                        }
                        orderLifeSpinner.setEnabled(true);

                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);

                        if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                            discQty_txt.setEnabled(true);

                        }
                    }
                    else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("StopLoss Market")) {
                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setText("");
                        price_txt.setEnabled(false);
                        //slLimitPrice_txt.setEnabled(false);
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        //profitPrice_txt.setEnabled(false);
                        discQty_txt.setEnabled(false);
                        discQty_txt.setText("");
                        for (int i = 0; i < orderLife.size(); i++) {
                            if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                                orderLifeSpinner.setSelection(i);
                                break;
                            }
                        }
                        orderLifeSpinner.setEnabled(true);

                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                    }
                }


                if(assetType.equalsIgnoreCase("equity")) {
                    orderTypeSpAdapter.remove("Stop Loss");
                    orderTypeSpAdapter.remove("StopLoss Market");

                    orderTypeSpAdapter.add("Stop Loss");
                    orderTypeSpAdapter.add("StopLoss Market");


                    orderTypeSpAdapter.notifyDataSetChanged();

                    if(!getArguments().getBoolean("isModifyOrder")) {
                        if(orderTypeSpinner.getSelectedItemId() != 2) {
                            orderLifeSpinner.setEnabled(true);//false
                        }
                    }
                }

                if(getArguments() != null && getArguments().getBoolean("isSquareOff")) {
                    orderTypeSpAdapter.remove("Stop Loss");
                    orderTypeSpAdapter.remove("StopLoss Market");
                    orderTypeSpAdapter.notifyDataSetChanged();
                }

                if(getArguments() != null && getArguments().getBoolean("isSquareOff")) {
                    mainOrderTypeSpinner.setEnabled(false);
                    orderTypeSpinner.setEnabled(true);
                    prodTypeSpinner.setEnabled(false);
                    orderLifeSpinner.setEnabled(false);
                }


            }
            else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {
                amoCheck = false;
                amoCheckOne = false;
                orderTypeSpinner.setSelection(0);
                orderTypeSpinner.setEnabled(false);

                if(assetType.equalsIgnoreCase("equity")) {
                    orderTypeSpAdapter.remove("Stop Loss");
                    orderTypeSpAdapter.remove("StopLoss Market");

                    orderTypeSpAdapter.add("Stop Loss");
                    orderTypeSpAdapter.add("StopLoss Market");
                    orderTypeSpAdapter.notifyDataSetChanged();
                    orderLifeSpinner.setEnabled(true);
                }


                if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {

                    if(action.equals("Buy") || action.equals("Sell")) {

                        orderLifeSpinner.setEnabled(true);
                        prodTypeSpinner.setEnabled(true);
                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                        orderLifeSpinner.setSelection(0);
                        orderLifeSpinner.setEnabled(false);

//                            prodTypeSpAdapter.remove("TNC");
//                            prodTypeSpAdapter.notifyDataSetChanged();
//                            prodTypeSpinner.setSelection(0);

//                            if(prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Catalyst")) {
//                            } else if(prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Intraday")) {
//                            } else {
//                                //prodTypeSpAdapter.notifyDataSetChanged();
//                                prodTypeSpinner.setSelection(getProductForBracketCover());
//                            }


//                            prodTypeSpinner.setEnabled(false);
                        addfieldslayout.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.VISIBLE);
                        slprice_txt.setVisibility(View.VISIBLE);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);


                    }

                } else {
                    addfieldslayout.setVisibility(View.VISIBLE);
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setEnabled(true);
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);

                    if(getArguments().getString("Product") != null && Integer.parseInt(getArguments().getString("Product")) >= 0) {
                        if(Integer.parseInt(getArguments().getString("Product")) < productType.size())
                            prodTypeSpinner.setSelection(Integer.parseInt(getArguments().getString("Product")));
                    } else {
                        prodTypeSpinner.setSelection(getProductForBracketCover());
                    }
                    orderTypeSpinner.setEnabled(false);
                    prodTypeSpinner.setEnabled(false);
                    orderLifeSpinner.setSelection(0);
                    orderLifeSpinner.setEnabled(false);
                    targetprice_txt.setVisibility(View.VISIBLE);
                    slprice_txt.setVisibility(View.VISIBLE);
                    targetprice_txt.setText(getArguments().getString("targetprice"));
                    slprice_txt.setText(getArguments().getString("stoplossprice"));
                    slTrigPrice_txt.setText(getArguments().getString("sltriggerprice"));
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                }

                if(getArguments() != null && getArguments().getBoolean("isSquareOff")) {
                    mainOrderTypeSpinner.setEnabled(false);
                    orderTypeSpinner.setEnabled(true);
                    prodTypeSpinner.setEnabled(false);
                    orderLifeSpinner.setEnabled(false);
                }


            }
            else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {
                amoCheck = false;
                amoCheckOne = false;
                if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    orderTypeSpinner.setSelection(0);
                }
                orderTypeSpinner.setEnabled(false);


                if(assetType.equalsIgnoreCase("equity")) {
                    orderTypeSpAdapter.remove("Stop Loss");
                    orderTypeSpAdapter.remove("StopLoss Market");

                    orderTypeSpAdapter.add("Stop Loss");
                    orderTypeSpAdapter.add("StopLoss Market");
                    orderTypeSpAdapter.notifyDataSetChanged();
                    orderLifeSpinner.setEnabled(true);
                }


                if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {

                    if(action.equals("Buy") || action.equals("Sell")) {


//                            prodTypeSpAdapter.remove("TNC");
//                            prodTypeSpAdapter.notifyDataSetChanged();
//                            prodTypeSpinner.setSelection(0);
//
//                            if(prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Catalyst")) {
//                            } else if(prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Intraday")) {
//                            } else {
//                                //  prodTypeSpAdapter.notifyDataSetChanged();
//                                prodTypeSpinner.setSelection(getProductForBracketCover());
//                            }

                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);
                        //price_txt.setText("");
                        //slLimitPrice_txt.setEnabled(false);
                        // slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        //profitPrice_txt.setEnabled(false);
                        //profitPrice_txt.setText("");
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                        if(!orderLife.contains("IOC")){
                            orderLife.add(1,"IOC");
                        }
                        orderLifeSpinner.setSelection(1);
                        orderLifeSpinner.setEnabled(false);
//                            prodTypeSpinner.setSelection(getProductForBracketCover());
//                            prodTypeSpinner.setEnabled(false);
                        addfieldslayout.setVisibility(View.VISIBLE);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.VISIBLE);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);


                    }
                }
                else {
                    addfieldslayout.setVisibility(View.VISIBLE);
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setEnabled(true);
                    //price_txt.setText("");
                    //slLimitPrice_txt.setEnabled(false);
                    //slLimitPrice_txt.setText("");
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    //profitPrice_txt.setEnabled(false);
                    // profitPrice_txt.setText("");
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                    prodTypeSpinner.setEnabled(true);
                    prodTypeSpinner.setSelection(getProductForBracketCover());
                    if(!getArguments().getString("OrderLife").equalsIgnoreCase("Day")) {
                        orderLifeSpinner.setSelection(1);
                    }
                    orderLifeSpinner.setEnabled(false);
                    targetprice_txt.setVisibility(View.INVISIBLE);
                    slprice_txt.setVisibility(View.VISIBLE);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                }


            }
            else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("AMO")) {


                amoCheck = true;
                amoCheckOne = true;
                orderTypeSpinner.setEnabled(true);

                if(assetType.equalsIgnoreCase("equity")) {
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(false);
                    orderTypeSpAdapter.remove("Stop Loss");
                    orderTypeSpAdapter.remove("StopLoss Market");
                    orderTypeSpAdapter.notifyDataSetChanged();
                    if(!getArguments().getBoolean("isModifyOrder")) {
                        orderLifeSpinner.setSelection(0);
                        orderLifeSpinner.setEnabled(true);

                    }
                    for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                        if(AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(TNC_product)) {
                            if(!productType.contains(getProduct(TNC_product))) {
                                prodTypeSpAdapter.add(getProduct(TNC_product));
//                                    prodTypeSpAdapter.remove("TNC");
                            }
                        }
                    }

                } else {
                    orderTypeSpinner.setSelection(0);
                    orderLifeSpinner.setEnabled(true);
                }
                // 0032202: In andriod,Trigger price field is not displaying for SL/SLM orders in NSE CD for AMO orders
                if((assetType.equalsIgnoreCase("fno") || assetType.equalsIgnoreCase("currency")) && (orderTypeSpinner.getSelectedItem().equals("Stop Loss") || orderTypeSpinner.getSelectedItem().equals("StopLoss Market"))) {

                    addfieldslayout.setVisibility(View.VISIBLE);
                } else {
                        addfieldslayout.setVisibility(View.GONE);


                }
                targetprice_txt.setVisibility(View.INVISIBLE);
                slprice_txt.setVisibility(View.INVISIBLE);
            }

//            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    private final AdapterView.OnItemSelectedListener orderTypeSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {

                if(action.equals("Buy") || action.equals("Sell")) {

                    if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Limit")) {
                        if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("COVER")&&!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("BRACKET")) {
                           if(!orderLife.contains("IOC")){
                            orderLife.add(1,"IOC");
                        }
                            orderLifeSpinner.setEnabled(true);
                        }
                        if(!getArguments().getBoolean("isModifyOrder")) {
                            orderLifeSpinner.setSelection(0);
                        }
                        prodTypeSpinner.setEnabled(true);
                        //slLimitPrice_txt.setText("");
                        if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("COVER")&&!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("BRACKET")) {
                            slTrigPrice_txt.setText("");
                            slTrigPrice_txt.setEnabled(false);
                            price_txt.setEnabled(true);
                            addfieldslayout.setVisibility(View.GONE);
                            slTrigPrice_txt.setEnabled(false);
                        }
                        //slLimitPrice_txt.setEnabled(false);
                        // profitPrice_txt.setText("");
                        // profitPrice_txt.setEnabled(false);
                        if(!getArguments().getBoolean("isSquareOff")) {
                            qty_txt.requestFocus();
                        } else {
                            price_txt.requestFocus();
                        }
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                        if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("COVER")&&!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("BRACKET")) {
                            orderLifeSpinner.setEnabled(true);
                        }
                        if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("BRACKET")) {
                            targetprice_txt.setVisibility(View.INVISIBLE);
                        }
                        if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("COVER")&&!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("BRACKET")) {
                            slprice_txt.setVisibility(View.INVISIBLE);
                        }
                        if("commodity".equalsIgnoreCase(assetType)) {
                            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                            orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity()) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    TextView v = (TextView) super.getView(position, convertView, parent);
                                    v.setTypeface(font);
                                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                        v.setTextColor(getResources().getColor(R.color.black));
                                    } else {
                                        v.setTextColor(getResources().getColor(R.color.white));
                                    }
                                    v.setPadding(15, 10, 15, 12);
                                    return v;
                                }

                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    TextView v = (TextView) super.getView(position, convertView, parent);
                                    v.setTypeface(font);
                                    v.setTextColor(Color.BLACK);
                                    v.setPadding(15, 10, 15, 15);
                                    return v;
                                }
                            };
                            orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                            orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                            orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                        }
                        if (!prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Catalyst_product)) && !prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product))) {
                            if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if ("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                                discQty_txt.setEnabled(true);
                            if ("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                                discQty_txt.setEnabled(false);
                            if ("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                                if (orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                                    discQty_txt.setEnabled(true);
                                }
                            }
                        }
                        if(orderLifeSpinner.getSelectedItem() != null && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtd")) {
                            addfieldslayout.setVisibility(View.VISIBLE);
                            gtd_text.setVisibility(View.VISIBLE);
                        } else {
                            gtd_text.setVisibility(View.GONE);
                        }

                        if(action.equals("Buy")) {
                            if(quoteResponse != null) {
                                if(getArguments().getBoolean("isSquareOff")) {
                                    price_txt.clearFocus();
                                    price_txt.requestFocus();
                                    if(!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss market")) {
                                        if(getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("6")) {
                                            price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAsk())));
                                        } else {
                                            price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));
                                        }
                                        price_txt.setSelection(price_txt.getText().length());


                                    }
                                }
                            }

                        }


                        if(action.equals("Sell")) {

                            if(quoteResponse != null) {
                                if(getArguments().getBoolean("isSquareOff")) {
                                    price_txt.clearFocus();
                                    price_txt.requestFocus();
                                    if(!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss market")) {
                                        if(getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("6")) {
                                            price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getBid())));
                                        } else {
                                            price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getBid())));
                                        }
                                        price_txt.setSelection(price_txt.getText().length());
                                    }
                                }
                            }
                        }


                    }
                    else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market")) {
                        if(!orderLife.contains("IOC")){
                            orderLife.add(1,"IOC");
                        }
                        orderLifeSpinner.setEnabled(true);
                        if(!getArguments().getBoolean("isModifyOrder")) {
                            orderLifeSpinner.setSelection(0);
                        }
                        if(getArguments() != null && !getArguments().getBoolean("isSquareOff")) {
                            prodTypeSpinner.setEnabled(true);
                        }

                        if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtd")) {
                            addfieldslayout.setVisibility(View.VISIBLE);
                            gtd_text.setVisibility(View.VISIBLE);
                        } else {
                            gtd_text.setVisibility(View.GONE);
                            addfieldslayout.setVisibility(View.GONE);
                        }

                        slTrigPrice_txt.setEnabled(false);
                        slTrigPrice_txt.setText("");
                        price_txt.setEnabled(false);
                        price_txt.setText("");
                        // profitPrice_txt.setEnabled(false);
                        // profitPrice_txt.setText("");
                        // slLimitPrice_txt.setEnabled(false);
                        // slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        orderLifeSpinner.setEnabled(true);
                        discQty_txt.setEnabled(false);
                        discQty_txt.setText("");
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        if("commodity".equalsIgnoreCase(assetType)) {
                            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                            orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity()) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    TextView v = (TextView) super.getView(position, convertView, parent);
                                    v.setTypeface(font);
                                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                        v.setTextColor(getResources().getColor(R.color.black));
                                    } else {
                                        v.setTextColor(getResources().getColor(R.color.white));
                                    }
                                    v.setPadding(15, 10, 15, 12);
                                    return v;
                                }

                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    TextView v = (TextView) super.getView(position, convertView, parent);
                                    v.setTypeface(font);
                                    v.setTextColor(Color.BLACK);
                                    v.setPadding(15, 15, 15, 15);
                                    return v;
                                }
                            };
                            orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                            orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                            orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                        }
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(true);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(true);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(true);
                        if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                            if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                                discQty_txt.setEnabled(true);
                            }
                        }
                    }
                    else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop Loss")) {
                        orderLifeSpinner.setEnabled(true);
                        prodTypeSpinner.setEnabled(true);

                        price_txt.setEnabled(true);
                        //slLimitPrice_txt.setEnabled(false);
                        // slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        //profitPrice_txt.setEnabled(false);
                        // profitPrice_txt.setText("");
                        if(!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("COVER")&&!mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("BRACKET")) {
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(true);
                        }
                        if(orderLife.contains("IOC")) {
                            orderLife.remove("IOC");
                        }
                        orderLifeSpinner.setSelection(0);
//                        orderLifeSpinner.setEnabled(false);

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(true);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(true);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                            discQty_txt.setEnabled(true);
                            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                            orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodityforStoploss()) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    TextView v = (TextView) super.getView(position, convertView, parent);
                                    v.setTypeface(font);
                                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                        v.setTextColor(getResources().getColor(R.color.black));
                                    } else {
                                        v.setTextColor(getResources().getColor(R.color.white));
                                    }
                                    v.setPadding(15, 10, 15, 12);
                                    return v;
                                }

                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    TextView v = (TextView) super.getView(position, convertView, parent);
                                    v.setTypeface(font);
                                    v.setTextColor(Color.BLACK);
                                    v.setPadding(15, 15, 15, 15);
                                    return v;
                                }
                            };
                            orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                            orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                            orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                            orderLifeSpinner.setEnabled(true);
                        }

                    }
                    else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("StopLoss Market")) {


                        price_txt.setText("");
                        price_txt.setEnabled(false);
                        //slLimitPrice_txt.setEnabled(false);
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        //profitPrice_txt.setEnabled(false);
                        discQty_txt.setEnabled(true);
                        discQty_txt.setText("");
                        if(orderLife.contains("IOC")) {
                            orderLife.remove("IOC");
                        }
                        orderLifeSpinner.setSelection(0);
//                        orderLifeSpinner.setEnabled(false);

                        addfieldslayout.setVisibility(View.VISIBLE);
                        slTrigPrice_txt.setEnabled(true);
                        targetprice_txt.setVisibility(View.INVISIBLE);
                        slprice_txt.setVisibility(View.INVISIBLE);

                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(true);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(true);
                        if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                    }
                }
            } else {

                if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market")) {
                    addfieldslayout.setVisibility(View.GONE);
                    slTrigPrice_txt.setEnabled(false);
                    price_txt.setEnabled(false);
                    slTrigPrice_txt.setText("");
                    price_txt.setText("");
                    //slLimitPrice_txt.setEnabled(false);
                    // profitPrice_txt.setText("");
                    // profitPrice_txt.setEnabled(false);
                    qty_txt.requestFocus();
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(false);

                    targetprice_txt.setVisibility(View.INVISIBLE);
                    slprice_txt.setVisibility(View.INVISIBLE);
                    //change for 33917: In android, validity of pending order is unable to modify after changing order type. by Rohit
                    orderLifeSpinner.setEnabled(true);

                    if (!orderLife.contains("IOC")){
                        orderLife.add("IOC");
                    }

                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);

                    if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0"))) {
                            if(exchangeName.equalsIgnoreCase("mcx") || exchangeName.equalsIgnoreCase("ncdex")) {
                                int pendinglotqty = Integer.parseInt(getArguments().getString("DisQty")) / Integer.parseInt(getArguments().getString("Lots"));
                                discQty_txt.setText(String.valueOf(pendinglotqty));
                            } else {
                                discQty_txt.setText(getArguments().getString("DisQty"));
                            }
                        }
                    }
                    if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                }
                else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Limit")) {
                    addfieldslayout.setVisibility(View.GONE);
                    slTrigPrice_txt.setEnabled(false);
                    slTrigPrice_txt.setText("");
                    price_txt.setEnabled(true);
                    price_txt.setText(getArguments().getString(PRICE));
                    //slLimitPrice_txt.setEnabled(false);
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    // profitPrice_txt.setEnabled(false);

                    targetprice_txt.setVisibility(View.INVISIBLE);
                    slprice_txt.setVisibility(View.INVISIBLE);
                    qty_txt.clearFocus();
                    qty_txt.setFocusable(true);
                    qty_txt.selectAll();
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(false);

                    if (!orderLife.contains("IOC")){
                        orderLife.add("IOC");
                    }

//                comented for 33917: In android, validity of pending order is unable to modify after changing order type. by Rohit
//                    if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    orderLifeSpinner.setEnabled(true);
//                    }
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 2) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }


                    if("commodity".equalsIgnoreCase(assetType) && (orderLifeSpinner.getSelectedItemPosition() == 0 || orderLifeSpinner.getSelectedItemPosition() == 2 || orderLifeSpinner.getSelectedItemPosition() == 3 || orderLifeSpinner.getSelectedItemPosition() == 4)) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0"))) {
                            if("mcx".equalsIgnoreCase(exchangeName) || "ncdex".equalsIgnoreCase(exchangeName)) {
                                if(Integer.parseInt(getArguments().getString("DisQty")) != Integer.parseInt(qty_txt.getText().toString())) {
                                    int disclsQty = Integer.parseInt(getArguments().getString("DisQty")) / Integer.parseInt(getArguments().getString("Lots"));
                                    discQty_txt.setText(String.valueOf(disclsQty));
                                } else {
                                    discQty_txt.setText(getArguments().getString("DisQty"));
                                }
                            } else {
                                discQty_txt.setText(getArguments().getString("DisQty"));
                            }
                        }
                    }
                    if("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                }
                else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop Loss")) {
                    addfieldslayout.setVisibility(View.VISIBLE);
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setEnabled(true);
                    price_txt.setText(getArguments().getString(PRICE));
                    //slLimitPrice_txt.setEnabled(false);
                    // slLimitPrice_txt.setText("");
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    // profitPrice_txt.setEnabled(false);
                    discQty_txt.setEnabled(false);
                    discQty_txt.setText("");
                   if (orderLife.contains("IOC")){
                        orderLife.remove("IOC");
                    }
                    for (int i = 0; i < orderLife.size(); i++) {
                        if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                            orderLifeSpinner.setSelection(i);
                            break;
                        }
                    }
                    orderLifeSpinner.setEnabled(true);

                    targetprice_txt.setVisibility(View.INVISIBLE);
                    slprice_txt.setVisibility(View.INVISIBLE);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);

                    if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                        discQty_txt.setEnabled(true);

                    }
                }
                else if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("StopLoss Market")) {
                    addfieldslayout.setVisibility(View.VISIBLE);
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setText("");
                    price_txt.setEnabled(false);
                    //slLimitPrice_txt.setEnabled(false);
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    //profitPrice_txt.setEnabled(false);
                    discQty_txt.setEnabled(false);
                    discQty_txt.setText("");
                    if (orderLife.contains("IOC")){
                        orderLife.remove("IOC");
                    }
                    for (int i = 0; i < orderLife.size(); i++) {
                        if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                            orderLifeSpinner.setSelection(i);
                            break;
                        }
                    }
                    orderLifeSpinner.setEnabled(true);

                    targetprice_txt.setVisibility(View.INVISIBLE);
                    slprice_txt.setVisibility(View.INVISIBLE);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                }
            }

            if(getArguments() != null && getArguments().getBoolean("isSquareOff")) {
                mainOrderTypeSpinner.setEnabled(false);
                orderTypeSpinner.setEnabled(true);
                prodTypeSpinner.setEnabled(false);
                orderLifeSpinner.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public PlaceOrderBottomSheetFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if(isInPictureInPictureMode) {
            dismiss();
            StopStreaming();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tradeView = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        customTransactionDialogFragment = new CustomTransactionDialogFragment();


        setupViews();
        parent = tradeView;
        settingThemeAssetTradeFragment();
        setupController();
        MarketStatusPostRequest.sendRequest(AccountDetails.getUsername(getActivity()),
                getActivity(), serviceResponseHandler);
        setupAdapters();
        getFromIntent();
        return tradeView;
    }

    private void setupViewPager(/*View parent*/) {
        AccountDetails.setiSCompanySummaryAvailbale(false);
        pagesList.clear();
        pagesList.add(PlaceOrderMarketDepthFragment.newInstance(pageSource, "MARKET DEPTH", quoteResponse));
        pagesList.add(PlaceOrderStockDetailsFragment.newInstance(pageSource, "STOCK DETAILS", quoteResponse));
        pagesList.add(PlaceOrderChartFragment.newInstance(pageSource, "CHART"));
        pagesList.add(PlaceOrderSecurityFragment.newInstance(pageSource, "Security Info"));
        pagesList.add(PlaceOrderTechnicalFragment.newInstance(pageSource, "TECHNICAL"));
//        pagesList.add(PlaceOrderFundaMentalFragment.newInstance(isinumber, "FUNDAMENTAL"));

        String[] heading = new String[5];
        heading[0] = "MARKET DEPTH";
        heading[1] = "STOCK DETAILS";
        heading[2] = "CHART";
        heading[3] = "SECURITY INFO";
        heading[4] = "TECHNICAL";
//        heading[3] = "FUNDAMENTAL";

        CustomViewPager mPager = tradeView.findViewById(R.id.pager);
        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            mPager.setBackgroundColor(getResources().getColor(R.color.black));
        }
        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), pagesList, heading);
        mPager.setAdapter(pagerAdapter);
        mPager.setOffscreenPageLimit(5);
        mPager.setHorizontalFadingEdgeEnabled(false);


        PagerSlidingTabStrip tabIndicator = parent.findViewById(R.id.indicator);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        tabIndicator.setTypeface(font, Typeface.NORMAL);
        tabIndicator.setViewPager(mPager);


        mTabsLinearLayout = ((LinearLayout) tabIndicator.getChildAt(0));

        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            mPager.setBackgroundColor(getResources().getColor(R.color.black));
            tabIndicator.setIndicatorColor(getResources().getColor(R.color.buttonColor));
            tabIndicator.setBackgroundColor(getResources().getColor(R.color.white));
            tabIndicator.setTextColor(getResources().getColor(R.color.black));

        }
        setIndicatorColor(0);
        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicatorColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicatorColor(int position) {
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            View bg = (View) mTabsLinearLayout.getChildAt(i);

            if(i == position) {
                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    tv.setTextColor(Color.BLACK);
                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        bg.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                } else {
                    tv.setTextColor(Color.WHITE);
                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        bg.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            } else {
                tv.setTextColor(getResources().getColor(R.color.unselectedTxtColor));

                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    bg.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        }
    }

    private void settingThemeAssetTradeFragment() {

        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {

            bottomsheet_header.setBackground(getResources().getDrawable(R.drawable.top_header_drawable_white));
            bottomesheet_edittext.setBackgroundColor(getResources().getColor(R.color.white));
            bottomsheet_spinner.setBackgroundColor(getResources().getColor(R.color.white));
            buy_sell_layout.setBackgroundColor(getResources().getColor(R.color.white));
            cancelbtn.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cancelbtn.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            gtd_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_black_24dp, 0);
            gtd_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            gtd_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            gtd_text.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            gtdButton.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            gtdButton.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            price_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            price_txt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            price_txt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            slTrigPrice_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            slTrigPrice_txt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            slTrigPrice_txt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            discQty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            discQty_txt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            discQty_txt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            targetprice_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            targetprice_txt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            targetprice_txt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            slprice_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            slprice_txt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            slprice_txt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));

            prodTypeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            mainOrderTypeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            orderTypeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            orderLifeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));

            amoCheckBox.setButtonDrawable(R.drawable.custom_checkbox_black);
            amoCheckBoxOne.setButtonDrawable(R.drawable.custom_layout_black_sell);

        }

    }

    private void setupController() {

        if(getArguments() != null) {

            if(getArguments().containsKey(ASSET_TYPE)) {

                if(getArguments().getString(ASSET_TYPE).equalsIgnoreCase("commodity")) {
                    normalOrderType = new ArrayList<>();
                    normalOrderType.add("Limit");
                    normalOrderType.add("Market");
                    normalOrderType.add("Stop Loss");
                } else {
                    normalOrderType = new ArrayList<>();
                    normalOrderType.add("Limit");
                    normalOrderType.add("Market");
                    normalOrderType.add("Stop Loss");
                    normalOrderType.add("StopLoss Market");

                    plusOrderType = new ArrayList<>();
                    plusOrderType.add("Market");
                }
            } else {
                normalOrderType = new ArrayList<>();
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
                normalOrderType.add("StopLoss Market");

                plusOrderType = new ArrayList<>();
                plusOrderType.add("Market");
            }
        }
    }


    private List<String> getOrderTypeForCommodity() {
        normalOrderType = new ArrayList<>();
        normalOrderType.add("Limit");
        normalOrderType.add("Market");
        normalOrderType.add("Stop Loss");
        normalOrderType.add("StopLoss Market");
        return normalOrderType;
    }

    private List<String> getOrderLife() {
        orderLife = new ArrayList<>();
        orderLife.add("Day");
        orderLife.add("IOC");
        orderLife.add("GTD");

        return orderLife;
    }

    private List<String> getMainOrderType() {
        mainOrderType = new ArrayList<>();
        mainOrderType.add("Regular");
        mainOrderType.add("Bracket");
        if(!getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("MCX")) {
            mainOrderType.add("Cover");
        }
        mainOrderType.add("AMO");

        return mainOrderType;
    }

    private List<String> getOrderLifeForCommodity() {
        orderLife = new ArrayList<>();

        String ex_name = null;

        if(getArguments() != null) {
            if(getArguments().containsKey(EXCHANGE_NAME)) {
                ex_name = getArguments().getString(EXCHANGE_NAME);
            } else if(getArguments().containsKey("Exchange")) {
                ex_name = getArguments().getString("Exchange");
            }
        }

        if(ex_name != null) {
            if(ex_name.equalsIgnoreCase("ncdex")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
//                orderLife.add("GTC");
            } else if(ex_name.equalsIgnoreCase("mcx")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
//                orderLife.add("GTC");
//                orderLife.add("EOS");
            } else if(ex_name.equalsIgnoreCase("nse") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("bse")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
            }
        } else if(exchangeName != null) {
            if(exchangeName.equalsIgnoreCase("ncdex")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
//                orderLife.add("GTC");
            } else if(exchangeName.equalsIgnoreCase("mcx")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
//                orderLife.add("GTC");
//                orderLife.add("EOS");
            } else if(exchangeName.equalsIgnoreCase("nse") || exchangeName.equalsIgnoreCase("bse")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
            }
        }

        return orderLife;
    }


    private List<String> getOrderLifeForCommodityforStoploss() {
        orderLife = new ArrayList<>();
        orderLife.add("Day");
//        orderLife.add("EOS");
        orderLife.add("GTD");
//        orderLife.add("GTC");
        return orderLife;
    }

    private void setupViews() {

        getProducttype();
        orderStreamingController = new OrderStreamingController();
        streamController = new StreamingController();
        gtd_label = tradeView.findViewById(R.id.gtd_label);
        buysellTypeTxt = tradeView.findViewById(R.id.buysellTypeTxt);
        change_txt = tradeView.findViewById(R.id.change_text);
        addfieldslayout = tradeView.findViewById(R.id.addfieldslayout);

        bottomsheet_header = tradeView.findViewById(R.id.bottomsheet_header);
        bottomesheet_edittext = tradeView.findViewById(R.id.bottomesheet_edittext);
        bottomsheet_spinner = tradeView.findViewById(R.id.bottomsheet_spinner);
        buy_sell_layout = tradeView.findViewById(R.id.buy_sell_layout);
        pager_layouts = tradeView.findViewById(R.id.pager_layout);

        prodTypeSpinner = tradeView.findViewById(R.id.productType_Spinner);
        mainOrderTypeSpinner = tradeView.findViewById(R.id.mainOrderType_Spinner);
        orderTypeSpinner = tradeView.findViewById(R.id.orderType_Spinner);
        orderLifeSpinner = tradeView.findViewById(R.id.orderLife_Spinner);

        buyBtn = tradeView.findViewById(R.id.btnBuy);
        sellBtn = tradeView.findViewById(R.id.btnSell);
        if(GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            buyBtn.setEnabled(false);
            sellBtn.setEnabled(false);
        }
        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            pager_layouts.setBackgroundColor(getResources().getColor(R.color.white));
        }
        amoCheckBox = tradeView.findViewById(R.id.amo_check);
        amoCheckBoxOne = tradeView.findViewById(R.id.amo_check_one);
        cSwitch = tradeView.findViewById(R.id.cSwitch);
        cSwitchText = tradeView.findViewById(cSwitch_textView);

        final RadioGroup radioGroup = tradeView.findViewById(R.id.btnGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btnBuy) {
                    action = "Buy";
                    selectBuySellButton(buyBtn);
                } else if (checkedId == R.id.btnSell) {
                    action = "Sell";
                    selectBuySellButton(sellBtn);
                }
            }
        });

        cSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConstraintLayout constraintLayout = tradeView.findViewById(R.id.signInLayout);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(cSwitch_textView, ConstraintSet.TOP, R.id.cSwitch, ConstraintSet.TOP, 0);
                constraintSet.connect(cSwitch_textView, ConstraintSet.BOTTOM, R.id.cSwitch, ConstraintSet.BOTTOM, 0);
                cSwitch.setThumbDrawable(tradeView.getResources().getDrawable(R.drawable.buy_ic));
                cSwitch.setTrackDrawable(ContextCompat.getDrawable(tradeView.getContext(), R.drawable.bg_sb));

                if(isChecked) {
                    cSwitchText.setText("");
                    constraintSet.connect(cSwitch_textView, ConstraintSet.LEFT, R.id.cSwitch, ConstraintSet.LEFT, 0);
                    constraintSet.connect(cSwitch_textView, ConstraintSet.RIGHT, ConstraintSet.UNSET, ConstraintSet.RIGHT, 0);
                    cSwitch.setTrackDrawable(ContextCompat.getDrawable(tradeView.getContext(), R.drawable.bg_sb));
                    cSwitch.setThumbDrawable(tradeView.getResources().getDrawable(R.drawable.sell_ic));
                    action = "Sell";
                    selectBuySellButton(sellBtn);
                    buysellTypeTxt.setText("SELL");
                } else {
                    cSwitchText.setText("");
                    constraintSet.connect(cSwitch_textView, ConstraintSet.RIGHT, R.id.cSwitch, ConstraintSet.RIGHT, 0);
                    constraintSet.connect(cSwitch_textView, ConstraintSet.LEFT, ConstraintSet.UNSET, ConstraintSet.LEFT, 0);
                    cSwitch.setTrackDrawable(ContextCompat.getDrawable(tradeView.getContext(), R.drawable.bg_sb));
                    cSwitch.setThumbDrawable(tradeView.getResources().getDrawable(R.drawable.buy_ic));
                    action = "Buy";
                    selectBuySellButton(buyBtn);
                    buysellTypeTxt.setText("BUY");
                }
                constraintSet.applyTo(constraintLayout);

            }
        });

        placeOrderBtn = tradeView.findViewById(R.id.place_Order_btn);
        cancelbtn = tradeView.findViewById(R.id.cancelbtn);
        symName = tradeView.findViewById(R.id.symbol_name_text);
        symbols_exchange_text = tradeView.findViewById(R.id.symbols_exchange);

        last = tradeView.findViewById(R.id.last_price_text);
        time = tradeView.findViewById(R.id.last_time_text);
        change = tradeView.findViewById(R.id.change_text);
        bid_price = tradeView.findViewById(R.id.txt_bid);
        limit_price = tradeView.findViewById(R.id.txt_limit);
        holding_txt = tradeView.findViewById(R.id.holding_txt);
        ask_price = tradeView.findViewById(R.id.txt_ask);
        symbols_description_text = tradeView.findViewById(R.id.symbols_description_text);
        symbol_name_text = tradeView.findViewById(R.id.symbol_name_text);
        symbols_exchange = tradeView.findViewById(R.id.symbols_exchange);
        rupee = tradeView.findViewById(R.id.rupee);
        last_price_text = tradeView.findViewById(R.id.last_price_text);
        change_text = tradeView.findViewById(R.id.change_text);

        if(getArguments() != null) {
            if(getArguments().containsKey(LOT_QUANTITY)) {
                lotQty = getArguments().getString(LOT_QUANTITY);
            }


            if(getArguments().containsKey("Token")) {

                assetType = getAssetType(getArguments().getString("Token"));
            }


            if(getArguments().containsKey(EXCHANGE_NAME)) {
                exchangeName = getArguments().getString(EXCHANGE_NAME);
            } else if(getArguments().containsKey("Exchange")) {
                exchangeName = getArguments().getString("Exchange");
            }

            if(getArguments().containsKey(SCRIP_NAME)) {
                scripName = getArguments().getString(SCRIP_NAME);
            }
        }
        /*if(assetType != null) {
            if(assetType.equalsIgnoreCase("1"))
                assetType = "equity";
            else if(assetType.equalsIgnoreCase("2"))
                assetType = "fno";
            else if(assetType.equalsIgnoreCase("3"))
                assetType = "currency";
        }*/

        gtdButton = tradeView.findViewById(R.id.gtdButton);
        Calendar c = getInstance();
        gtdButton.setText(sdf.format(c.getTime()));

        slTrigPrice_txt = tradeView.findViewById(R.id.trig_price_text);
        //slTrigPrice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        calendar = getInstance();
        year = calendar.get(YEAR);
        month = calendar.get(MONTH);
        day = calendar.get(DAY_OF_MONTH);
        //showDate(year,month+1,day);


        gtd_text = tradeView.findViewById(R.id.gtd_text);
        gtd_text.setShowSoftInputOnFocus(false);
        gtd_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });


        price_txt = tradeView.findViewById(R.id.price_text);
       // price_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        targetprice_txt = tradeView.findViewById(R.id.targetprice_text);
       // targetprice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        slprice_txt = tradeView.findViewById(R.id.sl_price_text);
        //slprice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if(assetType != null) {
            if(assetType != null && assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("3") || assetType.equalsIgnoreCase("6")) {
                slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 4)});
                targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 4)});
                slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 4)});
            } else {
                slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 2)});
                targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 2)});
                slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 2)});

                slTrigPrice_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                targetprice_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                slprice_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

            }


            if(assetType != null && assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("3") || assetType.equalsIgnoreCase("6")) {
                price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 4)});
            } else {
                price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 2)});
                price_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }

        }

        qty_txt = tradeView.findViewById(R.id.qty_text);
        qty_txt.setSelection(qty_txt.getText().length());
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(qty_txt, InputMethodManager.SHOW_IMPLICIT);
        //qty_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        discQty_txt = tradeView.findViewById(R.id.disc_qty_text);


        //discQty_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);


        productType = new ArrayList<>();


        qty_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(keepChangingText) {
                    keepChangingText = false;
                    setValueFiled();
                } else {
                    keepChangingText = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        price_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(keepChangingText) {
                    keepChangingText = false;
                    setValueFiled();
                } else {
                    keepChangingText = true;

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if(getArguments() != null) {

            if(getArguments().containsKey(TICK_SIZE)) {
                tickSize = getArguments().getString(TICK_SIZE);
            }
            if(getArguments().containsKey(MULTIPLIER)) {
                multiplier = getArguments().getString(MULTIPLIER);
            }

            if(getArguments().containsKey(TOKEN)) {
                token = getArguments().getString(TOKEN);
            }
        }
        action = "Buy";
        title = "Place Order";

        if(getArguments() != null) {
            if(!getArguments().isEmpty()) {
                if("equity".equalsIgnoreCase(assetType)) {
                    selectedAssetType = 0;
                } else if("fno".equalsIgnoreCase(assetType) || "future".equalsIgnoreCase(assetType)/*|| "option_index".equalsIgnoreCase(assetType)|| "option_stock".equalsIgnoreCase(assetType)*/) {
                    selectedAssetType = 1;
                    discQty_txt.setEnabled(false);
                } else if("currency".equalsIgnoreCase(assetType)) {
                    selectedAssetType = 2;

                } else if("commodity".equalsIgnoreCase(assetType)) {
                    selectedAssetType = 3;
                    discQty_txt.setEnabled(true);
                }
                sendQuotesRequest(token, getAssetTypeFromToken(token));
                sendMarginRequest("0");
                slTrigPrice_txt.requestFocus();
            }
        }


        gtdButton.setOnClickListener(this);
        placeOrderBtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        amoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                amoCheck = isChecked;
            }
        });


        amoCheckBoxOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                amoCheckOne = isChecked;
            }
        });


    }


    private void setupAdapters() {

        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        orderTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), new ArrayList<String>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 10, 15, 12);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        orderTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
        orderTypeSpinner.setAdapter(orderTypeSpAdapter);

        orderTypeSpinner.setOnItemSelectedListener(orderTypeSelectedListener);

        orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLife()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 10, 15, 12);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
        orderLifeSpinner.setAdapter(orderLifeSpAdapter);
        orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);


        mainOrderTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getMainOrderType()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 10, 15, 12);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        mainOrderTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
        mainOrderTypeSpinner.setAdapter(mainOrderTypeSpAdapter);
        mainOrderTypeSpinner.setOnItemSelectedListener(mainOrderTypeSelectionListener);

        List<String> exchanges_list = new ArrayList<String>();
        exchanges_list.add("BSE");
        exchanges_list.add("NSE");

        selectBuySellButton(buyBtn);


        prodTypeSpinner.setOnItemSelectedListener(productTypeSelectionListener);
        ((GreekBaseActivity) getActivity()).setChildMenuSelection(1, 0);
    }


    private void showDialog(View v) {
        Calendar now = getInstance();
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                gtd_text.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        Date newDate = now.getTime();
        dpd.getDatePicker().setMinDate(newDate.getTime());

        if(getArguments().getString(EXCHANGE_NAME) != null && getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("mcx")) {
            try {
                SimpleDateFormat expirydateformat = new SimpleDateFormat("dd/MM/yyyy");
                String formatteddate = DateTimeFormatter.getDateFromTimeStamp(expiry, "dd/MM/yyyy", "bse");
                String s = expirydateformat.format(new Date());
                Date date1 = expirydateformat.parse(s);
                Date date2 = expirydateformat.parse(formatteddate);

                long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
                now.add(DAY_OF_YEAR, Integer.parseInt(String.valueOf(day)));

            } catch (Exception e) {
                e.getMessage();
            }

        } else if(getArguments().getString(EXCHANGE_NAME) != null && getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {
            now.add(DAY_OF_YEAR, 6);
            dpd.getDatePicker().setMaxDate(now.getTimeInMillis());
        }

        dpd.show();
    }

    private String returnValue(GreekEditText text) {
        if(text.getText().toString().length() >= 1) return text.getText().toString();
        return "0";
    }


    private void navigate() {

        // if(market is Open amo order can not be procced.)
        // AMO conecept finish
        //If market is Closed============================>>>
        //if( AMO checked or selected by user)
        //args.putBoolean("valid_amo_status", true);
        //args.putBoolean("isOffline", false);
        //else(AMO Unchecked or N0t selected by user)
        //args.putBoolean("valid_amo_status", false);
        //args.putBoolean("isOffline", true);




        final Bundle args = new Bundle();
        if(quoteResponse != null) {

            if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("MCX")) {

                if(quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                    args.putString(TradePreviewFragment.SYMBOL, quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                } else {
                    args.putString(TradePreviewFragment.SYMBOL, quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                }


            } else {
                args.putString(TradePreviewFragment.SYMBOL, quoteResponse.getDescription() + " - " + getExchange(quoteResponse.getToken()));
            }
            args.putString(TradePreviewFragment.DESCRIPTION, quoteResponse.getDescription());
        } else if(getArguments().getString(SYMBOL_NAME) != null) {
            args.putString(TradePreviewFragment.SYMBOL, getArguments().getString(SYMBOL_NAME) + " - " + "");

        } else {
            args.putString(TradePreviewFragment.SYMBOL, scripName + " - " + "");
        }

        args.putBoolean("isSquareOff", getArguments().getBoolean("isSquareOff"));

        if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
            args.putString(TradePreviewFragment.ACTION, action);


            if(!valid_status) {

                args.putBoolean("isOffline", false);

                if(action.equalsIgnoreCase("buy")) {
                    if(amoCheck == true) {
                        args.putBoolean("valid_amo_status", true);

                    } else {
                        args.putBoolean("valid_amo_status", false);

                    }
                } else if(action.equalsIgnoreCase("sell")) {
                    if(amoCheckOne == true) {
                        args.putBoolean("valid_amo_status", true);

                    } else {
                        args.putBoolean("valid_amo_status", false);

                    }
                }
            } else {


                if(action.equalsIgnoreCase("buy")) {
                    if(amoCheck == true) {
                        args.putBoolean("valid_amo_status", true);
                        args.putBoolean("isOffline", false);

                        //args.putString(TradePreviewFragment.AMO, "1");
                    } else {
                        args.putBoolean("valid_amo_status", false);
                        args.putBoolean("isOffline", true);
                        //args.putString(TradePreviewFragment.AMO, "0");
                    }
                } else if(action.equalsIgnoreCase("sell")) {
                    if(amoCheckOne == true) {
                        args.putBoolean("valid_amo_status", true);
                        args.putBoolean("isOffline", false);
                        //args.putString(TradePreviewFragment.AMO, "1");
                    } else {
                        args.putBoolean("valid_amo_status", false);
                        args.putBoolean("isOffline", true);
                        //args.putString(TradePreviewFragment.AMO, "0");
                    }
                }

                // args.putBoolean("valid_amo_status", valid_status);
            }
        }
        else {
            args.putString(TradePreviewFragment.ACTION, action);
            args.putString(TradePreviewFragment.LEXCHANGEORDERNO1, getArguments().getString("lexchangeOrderNo1"));
            args.putString(TradePreviewFragment.EORDERID, getArguments().getString("eorderid"));
            args.putString(TradePreviewFragment.GORDERID, getArguments().getString("gorderid"));
            args.putString(TradePreviewFragment.LU_TIME_EXCHANGE, getArguments().getString("lu_time_exchange"));
            args.putString(TradePreviewFragment.QTY_FILLED_TODAY, getArguments().getString("qty_filled_today"));
            args.putString(TradePreviewFragment.IOM_RULE_NO, getArguments().getString("iomruleno"));
            args.putString("IstrategyNo", getArguments().getString("strategyid"));

            if(valid_status) {
                if(getArguments().getString("otype").equalsIgnoreCase("offline")) {
                    args.putBoolean("isOffline", true);
                } else {
                    args.putBoolean("isOffline", false);
                }
            } else {
                args.putBoolean("isOffline", false);

            }

            if(action.equalsIgnoreCase("buy")) {
                if(amoCheck == true) {
                    args.putBoolean("valid_amo_status", true);

                } else {
                    args.putBoolean("valid_amo_status", false);

                }
            } else if(action.equalsIgnoreCase("sell")) {
                if(amoCheckOne == true) {
                    args.putBoolean("valid_amo_status", true);

                } else {
                    args.putBoolean("valid_amo_status", false);

                }
            }

        }
        if(getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity")) {
            args.putString(TradePreviewFragment.QUANTITY, String.valueOf(((Integer.parseInt(qty_txt.getText().toString())) * (Integer.parseInt(quoteResponse.getLot())))));
            args.putString("OriginalQty", qty_txt.getText().toString());
            args.putString(TradePreviewFragment.PENDINGQTY, getArguments().getString("PendingQty"));
            if(getArguments().getString("PendingQty") != null) {
                args.putString(TradePreviewFragment.PENDINGQTY, String.valueOf(((Integer.parseInt(getArguments().getString("PendingQty"))) * (Integer.parseInt(quoteResponse.getLot())))));
                args.putString(TradePreviewFragment.PENDING_DISCLOSED_QTY, String.valueOf(((Integer.parseInt(getArguments().getString("pending_disclosed_qty"))) * (Integer.parseInt(quoteResponse.getLot())))));
            } else {
                args.putString(TradePreviewFragment.PENDINGQTY, getArguments().getString("PendingQty"));
                args.putString(TradePreviewFragment.PENDING_DISCLOSED_QTY, getArguments().getString("pending_disclosed_qty"));
            }

            args.putString(TradePreviewFragment.DIS_QUANTITY, String.valueOf((Integer.parseInt(returnValue(discQty_txt)) * (Integer.parseInt(quoteResponse.getLot())))));

        } else {
            args.putString(TradePreviewFragment.QUANTITY, qty_txt.getText().toString());
            args.putString("OriginalQty", qty_txt.getText().toString());
            args.putString(TradePreviewFragment.PENDINGQTY, getArguments().getString("PendingQty"));
            args.putString(TradePreviewFragment.DIS_QUANTITY, returnValue(discQty_txt));
            args.putString(TradePreviewFragment.PENDING_DISCLOSED_QTY, getArguments().getString("pending_disclosed_qty"));
        }

        if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market")) {
            args.putString(TradePreviewFragment.PRICE, last.getText().toString());
        } else {
            args.putString(TradePreviewFragment.PRICE, returnValue(price_txt));
        }
        args.putString(TradePreviewFragment.TRIGGER_PRICE, returnValue(slTrigPrice_txt));
        args.putString(TradePreviewFragment.PRODUCT_TYPE, prodTypeSpinner.getSelectedItem().toString());

        if(mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("regular") || mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("amo")) {
            args.putString(TradePreviewFragment.ORDER_TYPE, orderTypeSpinner.getSelectedItem().toString());
        } else if(mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bracket")) {
            args.putString(TradePreviewFragment.ORDER_TYPE, mainOrderTypeSpinner.getSelectedItem().toString());
            args.putString(TradePreviewFragment.TARGETPRICE, targetprice_txt.getText().toString());
            args.putString(TradePreviewFragment.SLPRICE, slprice_txt.getText().toString());
        } else if(mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
            args.putString(TradePreviewFragment.ORDER_TYPE, mainOrderTypeSpinner.getSelectedItem().toString());
            args.putString(TradePreviewFragment.SLPRICE, slprice_txt.getText().toString());
        }


        if(orderLifeSpinner.getSelectedItem() != null) {
            args.putString(TradePreviewFragment.ORDER_LIFE, orderLifeSpinner.getSelectedItem().toString());
        }
        args.putString(TradePreviewFragment.GTD_EXPIRY, returnValue(gtd_text));
        args.putString(TradePreviewFragment.TRADE_SYMBOL, quoteResponse.getSymbol());
        args.putString(TradePreviewFragment.TOKEN, token);
        if(quoteResponse != null)
            args.putString(TradePreviewFragment.EXCHANGE, getExchange(quoteResponse.getToken()));
        else args.putString(TradePreviewFragment.EXCHANGE, exchangeName);
        args.putString(TradePreviewFragment.ASSET_TYPE, assetType);
        args.putString(TradePreviewFragment.LOT_QUANTITY, lotQty);
        if(isPreOpen_status) {
            args.putString(TradePreviewFragment.isPreOpen, "1");
        } else {
            args.putString(TradePreviewFragment.isPreOpen, "0");
        }
        if(isPostOpen_status) {
            args.putString(TradePreviewFragment.isPostClosed, "1");
            if(action.equalsIgnoreCase("buy")) {
                if(!getArguments().getBoolean("isModifyOrder")) {
                    if (amoCheck == true) {
                        args.putBoolean("valid_amo_status", true);
                        args.putBoolean("isOffline", false);
                    } else {
                        args.putBoolean("valid_amo_status", false);
                        args.putBoolean("isOffline", true);
                    }
                }else{
                    if(getArguments().getString("otype") != null) {
                        if (getArguments().getString("otype").equalsIgnoreCase("amo")) {
                            args.putBoolean("valid_amo_status", true);
                            args.putBoolean("isOffline", false);
                            //args.putString(TradePreviewFragment.AMO, "1");
                        }else if (getArguments().getString("otype").equalsIgnoreCase("offline")) {
                            args.putBoolean("valid_amo_status", false);
                            args.putBoolean("isOffline", true);
                            //args.putString(TradePreviewFragment.AMO, "1");
                        }else{
                            args.putBoolean("valid_amo_status", false);
                            args.putBoolean("isOffline", false);
                            //args.putString(TradePreviewFragment.AMO, "1");
                        }
                    }

                }
            } else if(action.equalsIgnoreCase("sell")) {
                if(!getArguments().getBoolean("isModifyOrder")) {
                    if (amoCheckOne == true) {
                        args.putBoolean("valid_amo_status", true);
                        args.putBoolean("isOffline", false);
                        //args.putString(TradePreviewFragment.AMO, "1");
                    } else {
                        args.putBoolean("valid_amo_status", false);
                        args.putBoolean("isOffline", true);
                        //args.putString(TradePreviewFragment.AMO, "0");
                    }
                }else{
                    if(getArguments().getString("otype") != null) {
                        if (getArguments().getString("otype").equalsIgnoreCase("amo")) {
                            args.putBoolean("valid_amo_status", true);
                            args.putBoolean("isOffline", false);
                            //args.putString(TradePreviewFragment.AMO, "1");
                        }else if (getArguments().getString("otype").equalsIgnoreCase("offline")) {
                            args.putBoolean("valid_amo_status", false);
                            args.putBoolean("isOffline", true);
                            //args.putString(TradePreviewFragment.AMO, "1");
                        }else{
                            args.putBoolean("valid_amo_status", false);
                            args.putBoolean("isOffline", false);
                            //args.putString(TradePreviewFragment.AMO, "1");
                        }
                    }

                }
            }
            //args.putBoolean("isOffline", true);
        } else {
            args.putString(TradePreviewFragment.isPostClosed, "0");
            //args.putBoolean("isOffline", false);
        }


        if(orderLifeSpinner.getSelectedItemPosition() == 2 || orderLifeSpinner.getSelectedItemPosition() == 3)
            args.putString(TradePreviewFragment.VALIDITY, gtdButton.getText().toString());
        else args.putString(TradePreviewFragment.VALIDITY, "");
        args.putBoolean("isModifyOrder", getArguments().getBoolean(IS_MODIFY_ORDER, false));

        if(mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("amo")) {
            if(amoAllowed == false) {
                boolean isPostStatus = false;


                if(
                        (getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && AccountDetails.isPostClosed_nse_eq) ||
                                (getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSE") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && AccountDetails.isPostClosed_bse_eq)||(getExchange(quoteResponse.getToken()).equalsIgnoreCase("NSE") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && AccountDetails.isPostClosed_bse_eq)
                )
                {
                    isPostStatus = true;
                }

                if(!isPostOpen_status)
                {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "AMO Not allowed when market is open", "Ok", false, null);
                    return;
                }
            }else{
                args.putString(TradePreviewFragment.DIS_QUANTITY, "0");
            }
        }
        if(!quoteResponse.getReason().equalsIgnoreCase("")) {
            GreekDialog.alertDialog(getActivity(), 0, GREEK, quoteResponse.getReason(), "Yes", "No", true, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    if(action == GreekDialog.Action.OK) {
                        orderTimeFlag = AccountDetails.orderTimeFlag;
                        //boolean showTrasaction = Util.getPrefs(getActivity()).getBoolean("is_validateTransactionshow", false);
                        boolean showTrasaction = true;

                        if(!showTrasaction) {
                            if(orderTimeFlag) {

                                dismiss();
                                StopStreaming();
                                customTransactionDialogFragment.setArguments(args);
                                customTransactionDialogFragment.show(getFragmentManager(), "transaction");
                                customTransactionDialogFragment.setCancelable(false);
                            } else {

                                dismiss();
                                StopStreaming();
                                slTrigPrice_txt.setText("");
                                //slLimitPrice_txt.setText("");
                                //profitPrice_txt.setText("");
                                price_txt.setText("");
                                qty_txt.setText("");
                                discQty_txt.setText("");
                                slprice_txt.setText("");
                                targetprice_txt.setText("");
                                //TradePreviewFragment tradePreviewFragment = new TradePreviewFragment();


                                navigateTo(NAV_TO_ORDER_PREVIEW_SCREEN, args, true);
                            }

                        } else {
                            dismiss();
                            StopStreaming();
                            slTrigPrice_txt.setText("");
                            //slLimitPrice_txt.setText("");
                            //profitPrice_txt.setText("");
                            price_txt.setText("");
                            qty_txt.setText("");
                            discQty_txt.setText("");
                            slprice_txt.setText("");
                            targetprice_txt.setText("");

                            navigateTo(NAV_TO_ORDER_PREVIEW_SCREEN, args, true);
                        }
                    }
                }
            });
        } else {
            orderTimeFlag = AccountDetails.orderTimeFlag;
            //boolean showTrasaction = Util.getPrefs(getActivity()).getBoolean("is_validateTransactionshow", false);
            boolean showTrasaction = true;

            if(!showTrasaction) {
                if(orderTimeFlag) {

                    dismiss();
                    StopStreaming();
                    customTransactionDialogFragment.setArguments(args);
                    customTransactionDialogFragment.show(getFragmentManager(), "transaction");
                    customTransactionDialogFragment.setCancelable(false);
                } else {

                    dismiss();
                    StopStreaming();
                    slTrigPrice_txt.setText("");
                    //slLimitPrice_txt.setText("");
                    //profitPrice_txt.setText("");
                    price_txt.setText("");
                    qty_txt.setText("");
                    discQty_txt.setText("");
                    slprice_txt.setText("");
                    targetprice_txt.setText("");
                    //TradePreviewFragment tradePreviewFragment = new TradePreviewFragment();


                    navigateTo(NAV_TO_ORDER_PREVIEW_SCREEN, args, true);
                }

            } else {
                dismiss();
                StopStreaming();
                slTrigPrice_txt.setText("");
                //slLimitPrice_txt.setText("");
                //profitPrice_txt.setText("");
                price_txt.setText("");
                qty_txt.setText("");
                discQty_txt.setText("");
                slprice_txt.setText("");
                targetprice_txt.setText("");

                navigateTo(NAV_TO_ORDER_PREVIEW_SCREEN, args, true);
            }
        }
    }

    public void navigateTo(final int id, final Bundle bundle, final boolean addStack) {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputManager.isAcceptingText() && getActivity().getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        GreekBaseFragment fragment = MenuGetter.getFragmentById(id);
        if(fragment != null && bundle != null) {
            fragment.setArguments(bundle);
        }
        addFragment(R.id.activityFrameLayout, fragment, addStack);
    }

    private void addFragment(int containerViewId, Fragment fragment, boolean addStack) {
        ((GreekBaseActivity) getActivity()).addFragment(containerViewId, fragment, addStack);
    }

    @SuppressLint("NewApi")
    private void selectBuySellButton(RadioButton selectedBtn) {
        selectedBtn.setChecked(true);

        if(selectedBtn == sellBtn) {
//            if(GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER) {
            cSwitch.setChecked(true);
            placeOrderBtn.setBackgroundColor(getResources().getColor(R.color.sellColor));
            placeOrderBtn.setText("Sell");
            amoCheckBoxOne.setVisibility(View.GONE);
            amoCheckBox.setVisibility(View.GONE);
            if(!valid_status) {
                //If market is Online/Open

                //AMO Means After market order
                if(getArguments().getString("otype") != null) {
                    if(getArguments().getString("otype").equalsIgnoreCase("amo")) {
                        amoAllowed = true;
                        mainOrderTypeSpinner.setSelection(3);
                        mainOrderTypeSpinner.setEnabled(false);
                    } else if(getArguments().getString("otype").equalsIgnoreCase("offline")) {
                        amoCheckBoxOne.setEnabled(false);
                        amoAllowed = false;
                    }

                } else if(isPostOpen_status) {
                    amoCheckBoxOne.setEnabled(true);
                    mainOrderTypeSpinner.setEnabled(true);
                }
            } else {
                amoCheckBoxOne.setEnabled(true);
                mainOrderTypeSpinner.setEnabled(true);
            }

            if(quoteResponse != null) {
                if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    if(!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss market")) {
                        if(getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") ||
                                getAssetType(quoteResponse.getToken()).equalsIgnoreCase("6")) {
                            price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getBid())));
                        } else {
                            price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getBid())));
                        }
                    }
                }
            }
        }

        if(selectedBtn == buyBtn) {
            cSwitch.setChecked(false);
            if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                placeOrderBtn.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
            } else {
                placeOrderBtn.setBackgroundColor(getResources().getColor(R.color.buyColor));
            }
            placeOrderBtn.setText("Buy");
            amoCheckBoxOne.setVisibility(View.GONE);
            amoCheckBox.setVisibility(View.GONE);
            if(!valid_status) {
                //Market open
                if(getArguments().getString("otype") != null) {
                    if(getArguments().getString("otype").equalsIgnoreCase("amo")) {
                        amoCheckBox.setChecked(true);
                        amoCheckBox.setEnabled(false);
                        amoAllowed = true;
                        mainOrderTypeSpinner.setSelection(3);
                        mainOrderTypeSpinner.setEnabled(false);
                    } else if(getArguments().getString("otype").equalsIgnoreCase("offline")) {
                        amoAllowed = false;
                        amoCheckBox.setEnabled(false);
                        mainOrderTypeSpinner.setEnabled(false);
                    }
                } else if(isPostOpen_status) {
                    amoAllowed = true;
                    amoCheckBox.setEnabled(true);
                    mainOrderTypeSpinner.setEnabled(true);
                }
            } else {
                amoAllowed = true;
                amoCheckBox.setEnabled(true);
                mainOrderTypeSpinner.setEnabled(true);
            }


            if(quoteResponse != null) {
                if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    if(!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss market")) {
                        if(getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("6")) {
                            price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAsk())));
                        } else {
                            price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));
                        }
                    }
                }
            }


        }


        //Some condition and features are for both Action(BUY/SELL) thats the reason to keep such condition and this is very oldest codes so keep as
        // it is Working.
        if(selectedBtn == buyBtn || selectedBtn == sellBtn) {

            if(!getArguments().getBoolean("isSquareOff")) {
                qty_txt.clearFocus();
                qty_txt.requestFocus();
            } else {
                price_txt.clearFocus();
                price_txt.requestFocus();
            }

            price_txt.setEnabled(false);
            discQty_txt.setEnabled(false);
            if(orderTypeSpinner.getSelectedItemPosition() == 2) {
                addfieldslayout.setVisibility(View.VISIBLE);
                slTrigPrice_txt.setEnabled(true);
                price_txt.setEnabled(true);

                price_txt.clearFocus();
                slTrigPrice_txt.clearFocus();

                discQty_txt.setText("");
                discQty_txt.setEnabled(false);
                //Add for change orderlife GTD to Day issue
                if(!getArguments().getBoolean("isModifyOrder")) {
                    orderLifeSpinner.setSelection(0);
                    orderLifeSpinner.setEnabled(false);
                }
                //targetLayout.setVisibility(View.INVISIBLE);
                slprice_txt.setVisibility(View.INVISIBLE);
                if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                    discQty_txt.setEnabled(false);
                if("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                    discQty_txt.setEnabled(false);
                if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                    discQty_txt.setEnabled(false);
                if("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                    discQty_txt.setEnabled(false);
                if("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                    discQty_txt.setEnabled(true);
                    final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                    orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodityforStoploss()) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView v = (TextView) super.getView(position, convertView, parent);
                            v.setTypeface(font);
                            if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                v.setTextColor(getResources().getColor(R.color.black));
                            } else {
                                v.setTextColor(getResources().getColor(R.color.white));
                            }
                            v.setPadding(15, 10, 15, 12);
                            return v;
                        }

                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            TextView v = (TextView) super.getView(position, convertView, parent);
                            v.setTypeface(font);
                            v.setTextColor(Color.BLACK);
                            v.setPadding(15, 15, 15, 15);
                            return v;
                        }
                    };
                    orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                    orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                    orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                    orderLifeSpinner.setEnabled(true);
                }
            }
            if(orderTypeSpinner.getSelectedItemPosition() == 0) {
                price_txt.setEnabled(true);
                discQty_txt.setEnabled(true);
                orderLifeSpinner.setEnabled(true);
                if("equity".equalsIgnoreCase(assetType)) discQty_txt.setEnabled(true);
                if("commodity".equalsIgnoreCase(assetType)) discQty_txt.setEnabled(true);
                if("currency".equalsIgnoreCase(assetType)) discQty_txt.setEnabled(true);
            }
            if(orderTypeSpinner.getSelectedItemPosition() == 3) {
                orderLifeSpinner.setSelection(0);
                orderLifeSpinner.setEnabled(false);
                addfieldslayout.setVisibility(View.VISIBLE);
                slTrigPrice_txt.setEnabled(true);
                price_txt.setEnabled(false);
                discQty_txt.setEnabled(false);
            }
            if(orderTypeSpinner.getSelectedItemPosition() == 1) {
                orderLifeSpinner.setEnabled(true);
            }

            if(orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) {
                price_txt.setEnabled(true);
                prodTypeSpinner.setSelection(getProductForBracketCover());
                prodTypeSpinner.setEnabled(false);
            }

            if("equity".equals(assetType)) {
                if(orderTypeSpinner.getSelectedItemPosition() == 2) {

                } else if(orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 3) {
                    discQty_txt.setEnabled(false);
                } else {

                    if(lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else {
                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    }

                    discQty_txt.setEnabled(true);
                    if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                        discQty_txt.setText(getArguments().getString("DisQty"));
                }
            } else if("fno".equalsIgnoreCase(assetType)) {
                discQty_txt.setEnabled(false);
            } else if("currency".equalsIgnoreCase(assetType)) {
                if(orderTypeSpinner.getSelectedItemPosition() != 2) {
                    discQty_txt.setEnabled(true);
                    if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                        discQty_txt.setText(getArguments().getString("DisQty"));
                }
            } else if("commodity".equalsIgnoreCase(assetType)) {

                if(orderTypeSpinner.getSelectedItemPosition() == 2) {

                    if(exchangeName.equalsIgnoreCase("mcx")) {
                        discQty_txt.setEnabled(true);
                        if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    } else {
                        discQty_txt.setEnabled(false);
                    }

                } else {
                    qty_txt.setText("1");
                    qty_txt.setSelection(qty_txt.getText().length());
                    discQty_txt.setEnabled(true);
                    if(getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                        discQty_txt.setText(getArguments().getString("DisQty"));
                }
            }

            if(assetType != null && (!assetType.equalsIgnoreCase("equity") && !assetType.equalsIgnoreCase("commodity"))) {
                //qty_label.setText("Quantity");

                if(lotQty != null && !lotQty.equals("")) {
                    qty_txt.setText(lotQty);
                    qty_txt.setSelection(qty_txt.getText().length());
                }

            } else {
                if(assetType != null && (assetType.equalsIgnoreCase("equity") || assetType.equalsIgnoreCase("commodity") || assetType.equalsIgnoreCase("currency"))) {

                    if(exchangeName != null && exchangeName.equalsIgnoreCase("mcx")) {
                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else if(exchangeName != null && exchangeName.equalsIgnoreCase("ncdex")) {
                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else {
                        if(lotQty != null && !lotQty.equals("")) {
                            qty_txt.setText(lotQty);
                            qty_txt.setSelection(qty_txt.getText().length());
                        } else {
                            qty_txt.setText("1");
                            qty_txt.setSelection(qty_txt.getText().length());
                        }
                    }
                } else {
                    if(lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    }
                }
            }

            if(orderLifeSpinner.getSelectedItemPosition() == 1) {
                discQty_txt.setText("");
                discQty_txt.setEnabled(false);
            }
            productType.clear();

            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                /*if(selectedAssetType == 0 && (AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") || AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("mtf"))) {
                    productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                } else if(!AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") && !AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("mtf")) {
                    productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                }*/

                if(selectedAssetType == 0 && (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(SSEQ_product) || AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(MTF_product))) {
                    productType.add(getProductType(AccountDetails.getAllowedProduct().get(i).getiProductToken()));
                } else if(!AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(SSEQ_product) && !AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(MTF_product)) {
                    productType.add(getProductType(AccountDetails.getAllowedProduct().get(i).getiProductToken()));
                }
            }

            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
            prodTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), productType) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        v.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        v.setTextColor(getResources().getColor(R.color.white));
                    }
                    v.setPadding(15, 10, 15, 12);
                    return v;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    v.setTextColor(Color.BLACK);
                    v.setPadding(15, 15, 15, 15);
                    return v;
                }
            };
            prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
            prodTypeSpinner.setAdapter(prodTypeSpAdapter);
            int pos = 0;
            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                if(selectedAssetType != 0 && (AccountDetails.getProductTypeFlag().equalsIgnoreCase("2") || AccountDetails.getProductTypeFlag().equalsIgnoreCase("3"))) {

                } else {
                    if(AccountDetails.getProductTypeFlag().equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {

                        prodTypeSpinner.setSelection(productType.indexOf(getProductType(i+"")));
                        break;
                    }
                }
                pos++;
            }

            if(mainOrderTypeSpinner.getSelectedItem() != null) {
                if(mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bracket") || mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                    prodTypeSpinner.setSelection(getProductForBracketCover());
                }

            }

            orderTypeSpAdapter.clear();

            if(assetType != null && assetType.equalsIgnoreCase("commodity")) {
                getOrderTypeForCommodity();

            }

            for (String s : normalOrderType) {
                orderTypeSpAdapter.add(s);
            }
            //orderTypeSpinner.setAdapter(orderTypeSpAdapter);
            orderTypeSpAdapter.notifyDataSetChanged();
        }
        else {

            if(GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER) {
                qty_txt.requestFocus();
                price_txt.setText("");
                price_txt.setEnabled(false);
                price_txt.clearFocus();
                // profitPrice_txt.setText("");
                addfieldslayout.setVisibility(View.VISIBLE);
                slTrigPrice_txt.setEnabled(true);

                discQty_txt.setText("");
                discQty_txt.setEnabled(false);

                productType.clear();
                //productType.add(getString(TD_MARGIN_PLUS_ORDER_TYPE));

                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                prodTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), productType) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                            v.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 10, 15, 12);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
                prodTypeSpinner.setAdapter(prodTypeSpAdapter);
                int pos = 0;
                for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                    if(selectedAssetType != 0 && (AccountDetails.getProductTypeFlag().equalsIgnoreCase("2") || AccountDetails.getProductTypeFlag().equalsIgnoreCase("3"))) {

                    } else {
                        if(AccountDetails.getProductTypeFlag().equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {
                            prodTypeSpinner.setSelection(productType.indexOf(getProductType(i+"")));
                            break;
                        }
                    }
                    pos++;
                }

                if(mainOrderTypeSpinner.getSelectedItem() != null) {
                    if(mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bracket") || mainOrderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                        prodTypeSpinner.setSelection(getProductForBracketCover());
                    }

                }

                orderTypeSpAdapter.clear();

                for (String s : plusOrderType) {
                    orderTypeSpAdapter.add(s);
                }

                //orderTypeSpinner.setAdapter(orderTypeSpAdapter);
                orderTypeSpAdapter.notifyDataSetChanged();
            } else {
                GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            }
        }
    }

    private void setRetainedQty() {
        int preqQty;
        switch (selectedAssetType) {
            case 0:
                preqQty = Integer.parseInt(Util.getPrefs(getActivity()).getString("GREEK_LAST_CASH_QTY", "1"));
                qty_txt.setText(preqQty);
                qty_txt.setSelection(qty_txt.getText().length());
                break;

            default:
                qty_txt.setText("");
                break;
        }
    }

    private void getFromIntent() {

        AccountDetails.globalArgTradeFrag = getArguments();

        if(getArguments().getBoolean(IS_MODIFY_ORDER)) {

            placeOrderBtn.setText("Modify Order");
            if(getArguments().getString(TRADE_ACTION).equalsIgnoreCase("1")) {
                modifyAction = "buy";
            } else {
                modifyAction = "sell";
            }
            if("Buy".equalsIgnoreCase(modifyAction)) {
                action = "Buy";
                selectBuySellButton(buyBtn);
                cSwitch.setEnabled(false);
                //disableButtons(buyBtn, sellBtn);

            } else if("Sell".equalsIgnoreCase(modifyAction)) {
                action = "Sell";
                selectBuySellButton(sellBtn);
                cSwitch.setEnabled(false);
                //disableButtons(sellBtn, buyBtn);
            }


            handlePriceField(getArguments().getString(EXCHANGE_NAME), getArguments().getString("OrderType"), getArguments().getString("Product"));

            if(getOrderType(getArguments().getString("OrderType")).equalsIgnoreCase("bracket") || getOrderType(getArguments().getString("OrderType")).equalsIgnoreCase("cover")) {
                for (int i = 0; i < mainOrderType.size(); i++) {
                    if(mainOrderType.get(i).equalsIgnoreCase(getOrderType(getArguments().getString("OrderType")))) {
                        mainOrderTypeSpinner.setSelection(i);
                        mainOrderTypeSpinner.setEnabled(false);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < normalOrderType.size(); i++) {
                    if(normalOrderType.get(i).equalsIgnoreCase(getOrderType(getArguments().getString("OrderType")))) {
                        orderTypeSpinner.setSelection(i);
                        mainOrderTypeSpinner.setEnabled(false);
                        break;
                    }
                }
            }

            if(getArguments().getString("strategyid").equalsIgnoreCase("50")){
                prodTypeSpinner.setSelection(productType.indexOf(getProductType(getArguments().getString("Product"))));
            }else {

                for (int i = 0; i < productType.size(); i++) {
                    if (productType.get(i).equalsIgnoreCase(getProductType(getArguments().getString("Product")))) {
                        prodTypeSpinner.setSelection(productType.indexOf(getProductType(getArguments().getString("Product"))));
                        break;
                    }
                }
            }
            prodTypeSpinner.setEnabled(false);

            if(!getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("bse")) {
                if(getOrderType(getArguments().getString("OrderType")).equalsIgnoreCase("stop loss")) {
                    orderTypeSpinner.setEnabled(true);
                } else {
                    orderTypeSpinner.setEnabled(true);
                }
            } else {

                orderTypeSpinner.setEnabled(true);

            }

            String disQty;
            if(getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("mcx") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {
                qty_txt.setText(String.valueOf((Integer.parseInt(getArguments().getString("PendingQty"))) / (Integer.parseInt(getArguments().getString("Lots")))));
                qty_txt.setSelection(qty_txt.getText().length());
                disQty = String.valueOf((Integer.parseInt(getArguments().getString("DisQty"))) / (Integer.parseInt(getArguments().getString("Lots"))));
            } else {
                qty_txt.setText(getArguments().getString("PendingQty"));
                qty_txt.setSelection(qty_txt.getText().length());
                disQty = getArguments().getString("DisQty");
            }


            if(disQty != null && disQty.length() > 0 && Integer.parseInt(disQty) > 0) {
                discQty_txt.setText(disQty);
            } else {
                discQty_txt.setText("");
            }
//            if(price_txt.getText().toString().equalsIgnoreCase("")) {
            price_txt.setText(getArguments().getString(PRICE));
//            }
            slTrigPrice_txt.setText(getArguments().getString("TriggerPrice"));

            for (int i = 0; i < orderLife.size(); i++) {
                if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                    orderLifeSpinner.setSelection(i);
                    break;
                }
            }

            if(getArguments().getString("OrderLiveDays") != null && getArguments().getString("OrderLiveDays").length() > 0) {
                gtdButton.setText(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("OrderLiveDays"), "dd-MMM-yyyy", "bse"));

            }

            if(getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("mcx") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("nse") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("bse")) {
                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity()) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                            v.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 10, 15, 12);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);

                for (int i = 0; i < orderLife.size(); i++) {
                    if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                        orderLifeSpinner.setSelection(i);
                        break;
                    }
                }
                if(!getArguments().getString("lgoodtilldate").equalsIgnoreCase("0")) {

                    Log.e("TradeFragment", "lgoodtilldate===>" + getArguments().getString("lgoodtilldate"));

                    if(!getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("MCX") && !getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {

                        gtd_text.setText(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("lgoodtilldate"),
                                "dd/MM/yyyy", "nse"));
                    } else {
                        gtd_text.setText(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("lgoodtilldate"),
                                "dd/MM/yyyy", "bse"));
                    }

                }
            }


            Log.e("getFromIntent", "instName===========>>>" + getArguments().getString("instName"));
            Log.e("getFromIntent", "TRADE_SYMBOL===========>>>" + getArguments().getString(TRADE_SYMBOL));

            symName.setText(getArguments().getString(DESCRIPTION) + " - " + getArguments().getString("instName"));


            title = "Modify Order";

            if(getArguments().getString("strategyid").equalsIgnoreCase("51") || getArguments().getString("strategyid").equalsIgnoreCase("52") || getArguments().getString("strategyid").equalsIgnoreCase("54")) {
                qty_txt.setEnabled(false);
            } else {
                qty_txt.setEnabled(true);
            }
        }
        else if(getArguments().containsKey(TRADE_ACTION)) {
            if("buy".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                action = "Buy";
                selectBuySellButton(buyBtn);
            } else if("sell".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                action = "Sell";
                selectBuySellButton(sellBtn);
            } else {
                action = "Buy";
                selectBuySellButton(buyBtn);
            }
            if(getArguments().containsKey(PRICE)) {
                price_txt.setText(getArguments().getString(PRICE));
            }
        }

        if(getArguments().containsKey(ASSET_TYPE)) {
            if(getArguments().getString(ASSET_TYPE).equalsIgnoreCase("commodity")) {
                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity()) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                            v.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 10, 15, 12);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);

                for (int i = 0; i < orderLife.size(); i++) {
                    if(orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                        orderLifeSpinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        if(getArguments().getBoolean("isSquareOff")) {
            orderTypeSpinner.setSelection(0);
            if("buy".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                selectBuySellButton(buyBtn);
                action = "Buy";
                cSwitch.setEnabled(false);
                //disableButtons(buyBtn, sellBtn);
            } else if("sell".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                action = "Sell";
                selectBuySellButton(sellBtn);
                cSwitch.setEnabled(false);
                //disableButtons(sellBtn, buyBtn);
            }

            if(getArguments() != null && getArguments().getString("NetQty") != null) {
                if(getArguments().getString("AssetType").equalsIgnoreCase("1") || getArguments().getString("AssetType").equalsIgnoreCase("3") || getArguments().getString("AssetType").equalsIgnoreCase("equity") || getArguments().getString("AssetType").equalsIgnoreCase("currency") || getArguments().getString("AssetType").equalsIgnoreCase("commodity")) {
                    qty_txt.setText(String.valueOf(Math.abs(Integer.parseInt(getArguments().getString("NetQty")))));
                    qty_txt.setSelection(qty_txt.getText().length());
                } else if(getArguments().getString("AssetType").equalsIgnoreCase("2") || getArguments().getString("AssetType").equalsIgnoreCase("fno")) {
                    qty_txt.setText(String.valueOf(Math.abs(Integer.parseInt(getArguments().getString("NetQty")))));
                    qty_txt.setSelection(qty_txt.getText().length());
                }
            }

            for (int i = 0; i < productType.size(); i++) {
                if(productType.get(i).equalsIgnoreCase(getProductType(getArguments().getString("Product")))) {
                    prodTypeSpinner.setSelection(productType.indexOf(getProductType(i+"")));
                    break;
                }
            }

            prodTypeSpinner.setEnabled(false);

            if(getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {
                orderTypeSpinner.setSelection(0);

            } else {
                if(!getArguments().getBoolean("isSquareOff")) {
                    orderTypeSpinner.setSelection(1);
                } else {
                    orderTypeSpinner.setSelection(0);
                    qty_txt.clearFocus();
                    price_txt.requestFocus();
                }
            }


            orderLifeSpinner.setSelection(0);


        }
        else if(getArguments().getBoolean("isFromDemat")) {
            if(getArguments().getString("Action").equalsIgnoreCase("buy")) {
                selectBuySellButton(buyBtn);
            } else {
                selectBuySellButton(sellBtn);
            }

            qty_txt.setText(getArguments().getString("AvailableForSell"));
            price_txt.setText(getArguments().getString("LTP"));
            qty_txt.setSelection(qty_txt.getText().length());
            prodTypeSpinner.setEnabled(true);
        }

        if(!getArguments().getBoolean("isSquareOff")) {
            qty_txt.clearFocus();
            price_txt.clearFocus();
            qty_txt.requestFocus();
        } else {
            price_txt.clearFocus();
            price_txt.requestFocus();
            price_txt.clearFocus();
        }
    }


    private void handlePriceField(String mExchange, String mOrderType, String mProduct) {

        normalOrderType.clear();
        if(mProduct.contains("MarginPlus")) {
            qty_txt.setEnabled(false);
            orderTypeSpinner.setEnabled(false);
        } else if(mProduct.contains("SELL from DP")) {
            productType.clear();
            productType.add("SELL from DP");
            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
            prodTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), productType) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        v.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        v.setTextColor(getResources().getColor(R.color.white));
                    }
                    v.setPadding(15, 10, 15, 12);
                    return v;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    v.setTextColor(Color.BLACK);
                    v.setPadding(15, 15, 15, 15);
                    return v;
                }
            };
            prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
            prodTypeSpinner.setAdapter(prodTypeSpAdapter);

            prodTypeSpAdapter.notifyDataSetChanged();

        }
        if(mExchange.equals("BSE")) {
            switch (mOrderType) {
                case "1":
                    normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
                    normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
                    break;
                case "3":
                    addfieldslayout.setVisibility(View.VISIBLE);
                    slTrigPrice_txt.setEnabled(true);
                    normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
                    normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
                    normalOrderType.add("Stop Loss");
                    normalOrderType.add("StopLoss Market");
                    break;
                default:
                    addfieldslayout.setVisibility(View.VISIBLE);
                    slTrigPrice_txt.setEnabled(true);
                    normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
                    normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
                    normalOrderType.add("Stop Loss");
                    normalOrderType.add("StopLoss Market");
                    /*normalOrderType.add("Bracket");
                    normalOrderType.add("Cover");*/

                    break;
            }
        } else if(mExchange.equalsIgnoreCase("MCX") && (mOrderType.equals("Limit") || mOrderType.equals("1") || mOrderType.equals("2"))) {
            normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
            normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
        } else if(mExchange.equalsIgnoreCase("MCX") && mOrderType.equals("3")) {
            addfieldslayout.setVisibility(View.VISIBLE);
            slTrigPrice_txt.setEnabled(true);
            normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
            normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
            normalOrderType.add("Stop Loss");
            normalOrderType.add("StopLoss Market");

        } else if(mExchange.equalsIgnoreCase("MCX") && mOrderType.equals("Market")) {
            normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));

        } else {

            if(mExchange.equalsIgnoreCase("mcx") && mOrderType.equalsIgnoreCase("3")) {
                addfieldslayout.setVisibility(View.VISIBLE);
                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
            } else if(mExchange.equalsIgnoreCase("nse") && (mOrderType.equalsIgnoreCase("4") || mOrderType.equalsIgnoreCase("3"))) {
                addfieldslayout.setVisibility(View.VISIBLE);
                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
                normalOrderType.add("StopLoss Market");
            } else if(mOrderType.equalsIgnoreCase("1") && mExchange.equalsIgnoreCase("ncdex")) {
                addfieldslayout.setVisibility(View.VISIBLE);

                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");

            } else if(mOrderType.equalsIgnoreCase("1")) {
                addfieldslayout.setVisibility(View.VISIBLE);

                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
                normalOrderType.add("StopLoss Market");

            } else {
                slTrigPrice_txt.setEnabled(true);
                addfieldslayout.setVisibility(View.VISIBLE);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
                normalOrderType.add("StopLoss Market");
            }
        }

        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        orderTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), normalOrderType) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 10, 15, 12);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        orderTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
        orderTypeSpinner.setAdapter(orderTypeSpAdapter);
        orderTypeSpAdapter.notifyDataSetChanged();
    }

    public String getOrderType(String type) {
        if(type.equalsIgnoreCase("1")) {
            return "Limit";
        } else if(type.equalsIgnoreCase("2")) {
            return "Market";
        } else if(type.equalsIgnoreCase("3")) {
            return "Stop Loss";
        } else if(type.equalsIgnoreCase("4")) {
            return "Stoploss Market";
        } else if(type.equalsIgnoreCase("5")) {
            return "Cover";
        } else if(type.equalsIgnoreCase("7")) {
            return "Bracket";
        }
        return "";
    }

    public String getProductType(String type) {
        if(type.equalsIgnoreCase(Intraday_product))
            return getProduct(Intraday_product);
        else if(type.equalsIgnoreCase(Delivery_product))
            return getProduct(Delivery_product);
        else if(type.equalsIgnoreCase(MTF_product))
            return getProduct(MTF_product);
        else if(type.equalsIgnoreCase(SSEQ_product))
            return getProduct(SSEQ_product);
        else if(type.equalsIgnoreCase(TNC_product))
            return getProduct(TNC_product);
        else if(type.equalsIgnoreCase(Catalyst_product))
            return getProduct(Catalyst_product);
        return "";
    }

    // when navigating to trade from DematHolding
    private void disableButtons(RadioButton enableBtn, RadioButton d1) {
        enableBtn.setEnabled(true);
        enableBtn.setTextColor(Color.WHITE);
        d1.setEnabled(false);
    }

    private void sendQuotesRequest(String token, String assetType) {

        Log.e("sendQuotesRequest", "instName===========>>>" + getArguments().getString("instName"));
        Log.e("sendQuotesRequest", "TRADE_SYMBOL===========>>>" + getArguments().getString(TRADE_SYMBOL));


        if(tradeSymbol != null) {

            symName.setText(tradeSymbol);

        } else if(quoteResponse != null) {
            symName.setText(quoteResponse.getDescription() + "-" + quoteResponse.getInstrument());

        } else if(getArguments().getString(DESCRIPTION) != null) {

            symName.setText(getArguments().getString(DESCRIPTION) + " - " + getArguments().getString("instName"));
        }


        if(exchangeName != null)
            symbols_exchange_text.setText(getExchange(token));
        showProgress();
        quoteResponse = null;
        MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(getActivity()), token, assetType, AccountDetails.getClientCode(getActivity()), getActivity(), serviceResponseHandler);

        if(Util.getPrefs(getActivity()).getBoolean("GREEK_RETAIN_QTY_TOGGLE", false)) {
            setRetainedQty();
        }

        if("equity".equals(assetType)) {
            selectedAssetType = 0;
        } else if("fno".equals(assetType) || "future".equalsIgnoreCase(assetType)) {
            selectedAssetType = 1;
            discQty_txt.setEnabled(false);
        } else if("currency".equals(assetType)) {
            selectedAssetType = 2;
        } else if("commodity".equalsIgnoreCase(assetType)) {
            selectedAssetType = 3;
        }
    }


    private void sendMarginRequest(String exchangeType) {

        MarginDetailRequest marginDetailRequest = new MarginDetailRequest();
        marginDetailRequest.setGcid(AccountDetails.getClientCode(getActivity()));
        marginDetailRequest.setSegment(exchangeType);
        marginDetailRequest.setExchange_type("0");
        //orderStreamingController.sendMarginDetailRequest(getActivity(), marginDetailRequest);
    }


    public void onEventMainThread(MarginDetailResponse marginDetailResponse) {
        try {
            limit_price.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getAvailCashCredit())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setQuoteDetails(MarketsSingleScripResponse quoteResponse) {

        if(tradeSymbol != null && !tradeSymbol.trim().equals("null") && tradeSymbol.trim().length() > 0) {
            symName.setText(tradeSymbol);

        } else if(getArguments().getString(SCRIP_NAME) != null && quoteResponse.getInstrument() != null && quoteResponse.getDescription() != null) {
            symName.setText(quoteResponse.getDescription() + " - " + quoteResponse.getInstrument());
//            symName.setText(getArguments().getString(SCRIP_NAME) + " - " + quoteResponse.getInstrument());
        }
        valid_status = false;
        isPreOpen_status = false;
        isPostOpen_status = false;

        if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && AccountDetails.nse_eq_status) {
            valid_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("4")) && AccountDetails.bse_eq_status) {
            valid_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("2")) && AccountDetails.nse_fno_status) {
            valid_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("5")) && AccountDetails.bse_fno_status) {
            valid_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("3")) && AccountDetails.nse_cd_status) {
            valid_status = true;
        } else if((getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") ||
                getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR"))
                && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency")
                || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("6")) && AccountDetails.bse_cd_status) {
            valid_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.mcx_com_status) {
            valid_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.ncdex_com_status) {
            valid_status = true;
        }


        if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPreOpen_nse_eq) {
            isPreOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPreOpen_bse_eq) {
            isPreOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPreOpen_nse_fno) {
            isPreOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPreOpen_bse_fno) {
            isPreOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") && AccountDetails.isPreOpen_nse_cd) {
            isPreOpen_status = true;
        } else if((getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") ||
                getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR"))
                && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") && AccountDetails.isPreOpen_bse_cd) {
            isPreOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPreOpen_mcx_com) {
            isPreOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
            isPreOpen_status = true;
        }

        if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPostClosed_nse_eq) {
            isPostOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPostClosed_bse_eq) {
            isPostOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPostClosed_nse_fno) {
            isPostOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPostClosed_bse_fno) {
            isPostOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") && AccountDetails.isPostClosed_nse_cd) {
            isPostOpen_status = true;
        } else if((getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") ||
                getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR"))
                && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") && AccountDetails.isPostClosed_bse_cd) {
            isPostOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_mcx_com) {
            isPostOpen_status = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
            isPostOpen_status = true;
        }

        if(valid_status) {


        } else {
            amoCheckBox.setChecked(false);
            amoCheckBoxOne.setChecked(false);
            amoCheckBox.setEnabled(false);
            amoCheckBoxOne.setEnabled(false);
            amoAllowed = false;

            if(isPostOpen_status) {
                if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    amoCheckBox.setEnabled(true);
                    amoCheckBoxOne.setEnabled(true);

                    mainOrderTypeSpinner.setEnabled(true);
                } else {
                    if(getArguments().getString("otype") != null) {
                        if(getArguments().getString("otype").equalsIgnoreCase("amo")) {
                            amoCheckBox.setChecked(true);
                            amoCheckBox.setEnabled(false);
                            amoCheckBoxOne.setEnabled(true);
                            amoCheckBoxOne.setChecked(true);
                            amoAllowed = true;
                            mainOrderTypeSpinner.setSelection(3);
//                            mainOrderTypeSpinner.setSelection(0);
                            mainOrderTypeSpinner.setEnabled(false);
                        } else if(getArguments().getString("otype").equalsIgnoreCase("offline")) {

                            amoAllowed = false;
                            amoCheckBox.setEnabled(false);
                            amoCheckBoxOne.setChecked(false);

                        }
                    }
                }
            }
        }

        if(getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("6")) {
            last.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getLast())));
            change.setText(String.format("%s(%s%%)", String.format("%.4f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
            //time.setText(quoteResponse.getLtt());

            if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                if(getArguments().containsKey(TRADE_ACTION)) {
                    if(getArguments().getString(TRADE_ACTION).equalsIgnoreCase("buy") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("1")) {
                        if(getArguments().containsKey("MdPrice")) {
                            if(!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(getArguments().getString("MdPrice"))));
                            }
                        } else {
                            if(!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAsk())));
                            }
                        }

                        if(valid_status) {
                            //      amoCheckBox.setChecked(true);
                        } else {
                            amoCheckBox.setChecked(false);
                            amoCheckBox.setEnabled(false);
                            amoAllowed = false;
                            if(isPostOpen_status) {
                                amoCheckBox.setEnabled(true);
                            }
                        }
                    } else if(getArguments().getString(TRADE_ACTION).equalsIgnoreCase("sell") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("2")) {
                        if(getArguments().containsKey("MdPrice")) {
                            if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(getArguments().getString("MdPrice"))));
                            }
                        } else {
                            if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getBid())));
                            }
                        }

                        if(valid_status) {
                            //   amoCheckBoxOne.setChecked(true);
                        } else {
                            amoCheckBoxOne.setChecked(false);
                            amoCheckBoxOne.setEnabled(false);
                            amoAllowed = false;
                            if(isPostOpen_status) {
                                amoCheckBoxOne.setEnabled(true);
                            }
                        }
                    }
                } else {
                    if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                        price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAsk())));
                    }
                }
            }

        } else {
            last.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLast())));
            change.setText(String.format("%s(%s%%)", String.format("%.2f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
            if(!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                if(getArguments().containsKey(TRADE_ACTION)) {
                    if(getArguments().getString(TRADE_ACTION).equalsIgnoreCase("buy") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("1")) {
                        if(getArguments().containsKey("MdPrice")) {
                            if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(getArguments().getString("MdPrice"))));
                            }
                        } else {
                            if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));

                                if(!getArguments().getBoolean("isSquareOff")) {

                                    qty_txt.setText(quoteResponse.getLot());
                                    qty_txt.setSelection(qty_txt.getText().length());
                                } else {
                                    price_txt.setSelection(price_txt.getText().length());
                                }

                            }
                        }
                        if(valid_status) {
                            //   amoCheckBox.setChecked(true);
                        } else {
                            amoCheckBox.setChecked(false);
                            amoCheckBox.setEnabled(false);
                            amoAllowed = false;
                            if(isPostOpen_status) {
                                amoCheckBox.setEnabled(true);
                            }
                        }
                    } else if(getArguments().getString(TRADE_ACTION).equalsIgnoreCase("sell") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("2")) {
                        if(getArguments().containsKey("MdPrice")) {
                            if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(getArguments().getString("MdPrice"))));

                                if(!getArguments().getBoolean("isSquareOff")) {

                                    qty_txt.setText(quoteResponse.getLot());
                                    qty_txt.setSelection(qty_txt.getText().length());
                                } else {
                                    price_txt.setSelection(price_txt.getText().length());
                                }

                            }
                        } else {
                            if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getBid())));

                                if(!getArguments().getBoolean("isSquareOff")) {

                                    qty_txt.setText(quoteResponse.getLot());
                                    qty_txt.setSelection(qty_txt.getText().length());
                                }
                            }
                        }
                        if(valid_status) {
                            // amoCheckBoxOne.setChecked(true);
                        } else {
                            amoCheckBoxOne.setChecked(false);
                            amoCheckBoxOne.setEnabled(false);
                            amoAllowed = false;
                            if(isPostOpen_status) {
                                amoCheckBoxOne.setEnabled(true);
                            }
                        }
                    }
                } else {
                    if(orderTypeSpinner.getSelectedItem() != null && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                        price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));
                    }
                }
            }
        }

//This is the code which is coming from handleresponse section after setqoutedetails method's completion=================>>>>
        streamingSymbol = quoteResponse.getToken();
        sym.clear();


        if(getArguments().containsKey("isSquareOff") && getArguments().getBoolean("isSquareOff")) {

            sym.add(getArguments().getString("Token"));


        } else {

            sym.add(quoteResponse.getToken());
        }

        streamController.sendStreamingRequest(getActivity(), sym, "marketPicture", null, null, false);
        expiry = quoteResponse.getExpiryDate();
        qty_txt.requestFocus();

        if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("MCX")) {
            if(quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                symName.setText(quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
            } else {
                symName.setText(quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
            }

        } else {
            symName.setText(quoteResponse.getDescription() + "-" + quoteResponse.getInstrument());

        }

        // symName.setText(quoteResponse.getDescription() + " - " + quoteResponse.getInstrument());
        if(!getArguments().getBoolean(IS_MODIFY_ORDER) && !getArguments().getBoolean("isSquareOff")) {
            if(assetType != null && (assetType.equalsIgnoreCase("equity")
                    || assetType.equalsIgnoreCase("fno") ||
                    assetType.equalsIgnoreCase("commodity") ||
                    assetType.equalsIgnoreCase("currency"))) {
                //qty_txt.setText("1");

                if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") || getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex")) {
                    qty_txt.setText("1");
                    qty_txt.setSelection(qty_txt.getText().length());
                    Log.d("Lot size in if >>>>>>>", "lot");
                } else {
                    qty_txt.setText(quoteResponse.getLot());
                    qty_txt.setSelection(qty_txt.getText().length());
                    Log.d("Lot size in if >>>>>>>", quoteResponse.getLot());
                }
            }
//                        qty_txt.setText(quoteResponse.getLot());
            lotQty = quoteResponse.getLot();
        }
        if(getArguments().getBoolean("isSquareOff")) {
            if(Math.abs(Integer.parseInt(getArguments().getString("NetQty"))) > Integer.parseInt(quoteResponse.getFreeze_qty())) {
                qty_txt.setText(String.valueOf(Math.abs(Integer.parseInt(getArguments().getString("NetQty"))) - Integer.parseInt(quoteResponse.getFreeze_qty())));
//                            qty_txt.setSelection(qty_txt.getText().length());
            }

        }

        if(quoteResponse.getMTFScript()) {

            if(AccountDetails.getAllowedProductList().contains(getProduct(MTF_product))) {
                if(!productType.contains(getProduct(MTF_product))) {
                    productType.add(getProduct(MTF_product));
                }
            }

            prodTypeSpAdapter.notifyDataSetChanged();
        } else {

            //0028147==================================>
//                        if(productType.contains("MTF")) {
//                            productType.remove("MTF");
//                            prodTypeSpAdapter.notifyDataSetChanged();
//                        }
        }


    }

    private boolean isPriceWithinDPR(String price) {

        if(price.equalsIgnoreCase("-")) {
            return false;
        }
        if(quoteResponse != null) {
            if(Double.parseDouble(price) == Double.parseDouble(quoteResponse.getHighRange()) ||
                    Double.parseDouble(price) == Double.parseDouble(quoteResponse.getLowRange()) ||
                    (Double.parseDouble(price) < Double.parseDouble(quoteResponse.getHighRange()) &&
                            Double.parseDouble(price) > Double.parseDouble(quoteResponse.getLowRange()))) {
                return true;
            } else return false;
        }
        return false;
    }

    private Boolean validateFields() {
        try {
            if(quoteResponse == null) {
                GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", false, null);
                return false;

            } else if(symName.getText().length() <= 0) {

                GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.TD_SYMBOL_EMPTY_MSG), "OK", false, null);
                return false;

            } else if(getArguments() == null) {
                GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.TD_SYMBOL_EMPTY_MSG), "OK", false, null);
                return false;
            } else if(AppConstants.isEmptyEditText(getActivity(), qty_txt, getString(R.string.TD_QTY_EMPTY_MSG))) {
                // GreekDialog.EmptyAlertDialog(getMainActivity(), qty_txt,0, GREEK, getString(R.string.TD_QTY_EMPTY_MSG), "OK", false, null);
                return false;
            } else if(Integer.parseInt(qty_txt.getText().toString()) <= 0) {

                GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GR_MIN_QTY_MSG), "OK", false, null);
                return false;
            } else if(action.equals("Buy") || action.equals("Sell")) {


                if(!gtd_text.getText().toString().equalsIgnoreCase("")) {

                    SimpleDateFormat expirydateformat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    String currentDate = expirydateformat.format(c.getTime());
                    Date date1 = expirydateformat.parse(gtd_text.getText().toString());
                    Date date2 = expirydateformat.parse(currentDate);

                    if((getExchange(quoteResponse.getToken()).equalsIgnoreCase("nse")
                            || getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse")
                            || getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") || getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR") || getExchange(quoteResponse.getToken()).equalsIgnoreCase("NSECURR"))) {

                        if((date1.compareTo(date2) != 0) && !(date1.compareTo(date2) > 0)) {
                            GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, "GTD must be greater than 1 day", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if(action == GreekDialog.Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();
                                    }
                                }
                            });
                            return false;
                        } else if((date1.compareTo(date2) == 0)) {
                            GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, "GTD must be greater than 1 day", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if(action == GreekDialog.Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();

                                    }
                                }
                            });
                            return false;
                        }
                    }
                } else if(orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtd")) {

                    GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, "Please select the GTD expiry date", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if(action == GreekDialog.Action.OK) {
                                gtd_text.setText("");
                                gtd_text.requestFocus();

                            }
                        }
                    });
                    return false;
                }


                if(price_txt.isEnabled() && price_txt.getText().toString().length() <= 0 && (orderTypeSpinner.getSelectedItemPosition() == 0 || orderTypeSpinner.getSelectedItemPosition() == 2)) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.TD_PRICE_EMPTY_MSG), "OK", false, null);
                    return false;

                } else if((orderTypeSpinner.getSelectedItemPosition() == 2 || orderTypeSpinner.getSelectedItemPosition() == 3 || orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && AppConstants.isEmptyEditText(getActivity(), slTrigPrice_txt, getString(R.string.TD_TRIG_PRICE_EMPTY_MSG))) {
                    return false;
                } else if((orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && AppConstants.isEmptyEditText(getActivity(), slprice_txt, getString(R.string.TD_STOPLOSS_PRICE_EMPTY_MSG))) {
                    return false;
                } else if((orderTypeSpinner.getSelectedItemPosition() == 4) && AppConstants.isEmptyEditText(getActivity(), targetprice_txt, getString(R.string.TD_TARGET_PRICE_EMPTY_MSG))) {
                    return false;
                } else if((orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && AppConstants.isEmptyEditText(getActivity(), price_txt, "Please enter the Price")) {
                    return false;
                } else if(price_txt.isEnabled() && price_txt.getText().toString().equalsIgnoreCase("") || price_txt.getText().toString().length() > 0 && ".".equals(price_txt.getText().toString())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Invalid Price", "OK", false, null);
                    return false;
                } else if(slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0 && ".".equals(slTrigPrice_txt.getText().toString())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Invalid Trigger Price", "OK", false, null);
                    return false;
                } else if(price_txt.getText().toString().length() > 0 && !isPriceWithinDPR(price_txt.getText().toString())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Price should be within DPR: [" + quoteResponse.getLowRange() + " - " + quoteResponse.getHighRange() + "]", "OK", false, null);
                    return false;
                } else if((orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && price_txt.getText().toString().length() > 0 && !isPriceWithinDPR(price_txt.getText().toString())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Price should be within DPR: [" + quoteResponse.getLowRange() + " - " + quoteResponse.getHighRange() + "]", "OK", false, null);
                    return false;
                } else if(slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().equalsIgnoreCase("")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.TD_TRIG_PRICE_EMPTY_MSG), "OK", false, null);
                    return false;
                } else if(slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().equalsIgnoreCase("") || slTrigPrice_txt.getText().toString().length() > 0 && !isPriceWithinDPR(slTrigPrice_txt.getText().toString())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trigger Price should be within DPR: [" + quoteResponse.getLowRange() + " - " + quoteResponse.getHighRange() + "]", "OK", false, null);
                    return false;
                } else if(targetprice_txt.getVisibility() == View.VISIBLE && targetprice_txt.getText().toString().equalsIgnoreCase("")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.TD_TARGET_PRICE_EMPTY_MSG), "OK", false, null);
                    return false;
                } else if(targetprice_txt.getVisibility() == View.VISIBLE && targetprice_txt.getText().toString().equalsIgnoreCase("") || targetprice_txt.getText().toString().length() > 0 && !isPriceWithinDPR(targetprice_txt.getText().toString())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Target Price should be within DPR: [" + quoteResponse.getLowRange() + " - " + quoteResponse.getHighRange() + "]", "OK", false, null);
                    return false;
                } else if(slprice_txt.getVisibility() == View.VISIBLE && slprice_txt.getText().toString().equalsIgnoreCase("") || slprice_txt.getText().toString().length() > 0 && ".".equals(slprice_txt.getText().toString())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Please enter Stop Loss Price", "OK", false, null);
                    return false;
                }


                if(action.equalsIgnoreCase("buy") && targetprice_txt.isEnabled() && targetprice_txt.getText().toString().length() > 0
                        && Double.parseDouble(targetprice_txt.getText().toString().trim()) < Double.parseDouble(price_txt.getText().toString().trim())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Target Price must be greater than or equal to Order Price for Buy Order", "OK", false, null);
                    return false;
                }
                if(action.equalsIgnoreCase("sell") && targetprice_txt.isEnabled() && targetprice_txt.getText().toString().length() > 0
                        && Double.parseDouble(targetprice_txt.getText().toString().trim()) > Double.parseDouble(price_txt.getText().toString().trim())) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Target Price must be less than or equal to Order Price for Sell Order", "OK", false, null);
                    return false;
                }


                if(qty_txt.getText().length() > 0) {

                    if(getArguments().getBoolean("isSquareOff")) {

                        if(Math.abs(Integer.parseInt(getArguments().getString("NetQty"))) < Integer.parseInt(qty_txt.getText().toString())) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GR_SQROFF_QTY_MSG), "OK", false, null);
                            return false;
                        }
                    }

                    if(!getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity")) {

                        if(!lotQty.equalsIgnoreCase("") || lotQty != null) {
                            int qtyLot = Integer.parseInt(qty_txt.getText().toString());
                            int mod = Integer.parseInt(lotQty);
                            if(qtyLot % mod != 0) {
                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Invalid Qty", "OK", false, null);
                                return false;
                            }
                        }
                    }
                }
                if(tickSize != null && multiplier != null) {
                    double tick = Double.parseDouble(tickSize);
                    double price = 0;
                    if(price_txt.getText().toString().length() > 0)
                        price = Double.parseDouble(price_txt.getText().toString());


                    BigDecimal bigPrice = new BigDecimal(price);
                    BigDecimal bigTick = new BigDecimal(tick);

                    double tmpprice = 0, tmptiksize = 0, rem = 0;

                    if("fno".equalsIgnoreCase(assetType)) {
                        tmpprice = price * MAX_MULTIPLIER;
                        tmptiksize = tick * MAX_MULTIPLIER;
                        rem = tmpprice % tmptiksize;
                        if(tmptiksize == 0) {
                            rem = 0;
                        }
                    } else {
                        BigDecimal _bigPrice = new BigDecimal(price);
                        BigDecimal _mult1 = _bigPrice.multiply(new BigDecimal(1000000));
                        String s = String.format("%.0f", _mult1);
                        double d1 = Double.parseDouble(s);

                        BigDecimal _bigTick = new BigDecimal(tick);
                        BigDecimal _mult2 = bigTick.multiply(new BigDecimal(1000000));
                        String s1 = String.format("%.0f", _mult2);
                        double d2 = Double.parseDouble(s1);
                        rem = d1 % d2;
                        if(d2 == 0) {
                            rem = 0;
                        }
                    }


                    if(price_txt.getText().toString().length() > 0 && (rem != 0)) {
                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "Entered limit Price not in a multiple of Ticksize", "OK", false, null);
                        return false;
                        //0031863: In andriod,It is not allowing to square-off on limit price it is showing pop up 'Entered limit price not in multiple of tick si'
                    }

                    if(slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0) {
                        double stkPrice = 0;
                        stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());
                        double tmpprice1 = 0, tmptiksize1 = 0, rem1 = 0;

                        if("fno".equalsIgnoreCase(assetType)) {
                            tmpprice1 = stkPrice * MAX_MULTIPLIER;
                            tmptiksize1 = tick * MAX_MULTIPLIER;
                            rem = tmpprice1 % tmptiksize1;
                        } else {

                            BigDecimal _bigPrice = new BigDecimal(stkPrice);
                            BigDecimal _mult1 = _bigPrice.multiply(new BigDecimal(1000000));
                            String s = String.format("%.0f", _mult1);
                            double d1 = Double.parseDouble(s);


                            BigDecimal _bigTick = new BigDecimal(tick);
                            BigDecimal _mult2 = _bigTick.multiply(new BigDecimal(1000000));
                            String s1 = String.format("%.0f", _mult2);
                            double d2 = Double.parseDouble(s1);
                            rem = d1 % d2;

                        }

                        if(slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0 && (rem != 0)) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Entered trigger Price not in a multiple of Ticksize", "OK", false, null);
                            return false;
                            //0031863: In andriod,It is not allowing to square-off on limit price it is showing pop up 'Entered limit price not in multiple of tick si'
                        }
                    }
                }

                if(action.equals("Buy")) {
                    double price;
                    double stkPrice;
                    double slPrice;
                    if(price_txt.getText().toString().length() > 0 && slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString()) != 0) {

                        price = Double.parseDouble(price_txt.getText().toString());
                        stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());

                        if(stkPrice > price) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trigger Price must be less than Order Price for Buy Order", "OK", false, null);
                            return false;
                        }
                    }
                    if(slprice_txt.getVisibility() == View.VISIBLE) {
                        if(slprice_txt.getText().toString().length() > 0 && price_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString()) != 0) {
                            price = Double.parseDouble(price_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if(slPrice > price) {
                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Stoploss Price must be less than Order Price for Buy Order", "OK", false, null);
                                return false;

                            }
                        }
                        if(slprice_txt.getText().toString().length() > 0 && slTrigPrice_txt.getText().toString().length() > 0) {
                            stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if(slPrice > stkPrice) {
                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trigger Price must be more than StopLoss Price for Buy Order", "OK", false, null);
                                return false;
                            }
                        }
                    }
                } else if(action.equals("Sell")) {
                    double price;
                    double stkPrice;
                    double slPrice;
                    if(price_txt.getText().toString().length() > 0 && slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0) {

                        price = Double.parseDouble(price_txt.getText().toString());
                        stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());

                        if(price > stkPrice) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trigger Price must be more than Order Price for Sell Order", "OK", false, null);
                            return false;
                        }
                    }
                    if(slprice_txt.getVisibility() == View.VISIBLE) {
                        if(slprice_txt.getText().toString().length() > 0 && price_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString()) != 0 && Double.parseDouble(slprice_txt.getText().toString()) != 0) {
                            price = Double.parseDouble(price_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if(slPrice < price) {
                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Stoploss Price must be more than Order Price for Sell Order", "OK", false, null);
                                return false;

                            }
                        }
                        if(slprice_txt.getText().toString().length() > 0 && slTrigPrice_txt.getText().toString().length() > 0 && Double.parseDouble(slprice_txt.getText().toString()) != 0) {
                            stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if(slPrice < stkPrice) {
                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "StopLoss Price must be more than Trigger Price for Sell Order", "OK", false, null);
                                return false;

                            }
                        }
                    }
                }

                if(isPreOpen_status && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stop loss") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market"))) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Only RL order is allowed in Preopen Session", "OK", false, null);
                    return false;
                }

                if(isPreOpen_status && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "IOC order is not allowed in Preopen Session", "OK", false, null);
                    return false;
                }

                if(valid_status) {
                    if(action.equalsIgnoreCase("buy")) {

                        if(amoCheck == true && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Cover order is not allowed in Offline Session", "OK", false, null);

                            return false;
                        }

                        if(amoCheck == false && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stop loss") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market"))) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Only RL order is allowed in Offline Session", "OK", false, null);
                            return false;
                        }

                        if(amoCheck == false && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "IOC order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }

                        if(amoCheck == true && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "IOC order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }
                    } else if(action.equalsIgnoreCase("sell")) {

                        if(amoCheckOne == true && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Cover order is not allowed in Offline Session", "OK", false, null);

                            return false;
                        }

                        if(amoCheckOne == false && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market"))) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Only RL order is  allowed in Offline Session", "OK", false, null);
                            return false;
                        }

                        if(amoCheckOne == false && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "IOC order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }

                        if(amoCheckOne == true && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "IOC order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }
                    }
                }
            }

            if(discQty_txt.getText().toString().length() > 0) {

                if(Integer.parseInt(discQty_txt.getText().toString().trim()) <= 0) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Disclosed Quantity should be positive number", "OK", false, null);
                    return false;
                } else if(!exchangeName.equalsIgnoreCase("mcx")) {
                    if(Integer.parseInt(discQty_txt.getText().toString()) > 0) {
                        double i = Double.parseDouble(qty_txt.getText().toString());
                        boolean result1 = (i / 10.0) > (Double.parseDouble(discQty_txt.getText().toString()));
                        boolean result2 = Double.toString((i / 10.0d)).equalsIgnoreCase(Double.toString(Double.parseDouble(discQty_txt.getText().toString())));

                        if(result1 && !result2) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Disclosed quantity is less than 10% of order quantity", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if(action == GreekDialog.Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();
                                    }
                                }
                            });
                            return false;

                        }
                        if(Integer.parseInt(qty_txt.getText().toString()) < Integer.parseInt(discQty_txt.getText().toString())) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Disclosed quantity should not be greater than order quantity", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if(action == GreekDialog.Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();

                                    }
                                }
                            });
                            return false;
                        }
                    }
                } else if(exchangeName.equalsIgnoreCase("mcx")) {
                    double i = Double.parseDouble(qty_txt.getText().toString());
                    double i1 = Double.parseDouble(discQty_txt.getText().toString());

                    double min = (i * 25) / 100;

                    if(i1 < min) {
                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "Disclosed quantity is less than 25% of order quantity", "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if(action == GreekDialog.Action.OK) {
                                    discQty_txt.setText("");
                                    discQty_txt.requestFocus();
                                }
                            }
                        });
                        return false;

                    }

                    if(Integer.parseInt(qty_txt.getText().toString()) < Integer.parseInt(discQty_txt.getText().toString())) {
                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "Disclosed quantity should not be greater than order quantity", "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if(action == GreekDialog.Action.OK) {
                                    discQty_txt.setText("");
                                    discQty_txt.requestFocus();

                                }
                            }
                        });
                        return false;
                    }
                }
            }
            if(gtd_text.getVisibility() == View.VISIBLE) {
                if(!gtd_text.getText().toString().equalsIgnoreCase("")) {
                    SimpleDateFormat expirydateformat = new SimpleDateFormat("dd/MM/yyyy");
                    String formatteddate = DateTimeFormatter.getDateFromTimeStamp(expiry, "dd/MM/yyyy", "bse");
                    Date date1 = expirydateformat.parse(gtd_text.getText().toString());
                    Date date2 = expirydateformat.parse(formatteddate);

                    String token_date = DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd/MM/yyyy", "bse");
                    Date tknExpry_date = expirydateformat.parse(token_date);

                    if(!getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity")) {

                        if((date1.compareTo(tknExpry_date) == 0) && !(date1.compareTo(tknExpry_date) < 0)) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "GTD Expiry date cannot be Same Or greater than Token Expiry Date", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if(action == GreekDialog.Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();

                                    }
                                }
                            });
                            return false;
                        } else if(((date1.compareTo(tknExpry_date) == 1))) {
                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "GTD Expiry date cannot be greater than Token Expiry Date", "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if(action == GreekDialog.Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();

                                    }
                                }
                            });
                            return false;
                        }
                    }
                }
            }

        } catch (NumberFormatException ex) {
            Log.d("NumberFormatException", ex.getMessage());
        } catch (Exception ex) {
            Log.d("TRADEException", ex.getMessage());

        }

        return true;
    }

    private void validateAndSend() {
        if(validateFields()) {
            navigate();
        }
    }

    private void validateorderQty() {
        if(validateFields()) {
            validateAndSend();
        }
    }


    private boolean validateIntradayTimer() {
        if(quoteResponse.getIntradayTimerSet() && prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product))) {
            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Selected Exchange timer triggered.Orders cannot be placed in INTRADAY product", "Ok", false, null);
            return false;
        }
        return true;
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        if(MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                hideProgress();
                setValueFiled();
                if(!quoteResponse.getIsError()) {
                    isinumber = quoteResponse.getIsinumber();
                    tickSize = quoteResponse.getTickSize();
                    AccountDetails.setIsinumber(isinumber);

                    AccountDetails.setAssetsType(getExchange(quoteResponse.getToken()));

                    setQuoteDetails(quoteResponse);

                } else {
                    symName.setText(quoteResponse.getMessage());
                    quoteResponse = null;
                }

                setupViewPager();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if(MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && PREQUOTEDETAILS_SVC_NAME.equals(jsonResponse.getServiceName())) {

            Log.e("TradeFragment", "==============>>>" + jsonResponse.toString());
            JSONObject jsonObject = jsonResponse.getResponseData();
            try {
                JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                String HQty = jsonObjectData.getString("NQty");
                if(HQty != null && !HQty.isEmpty()) {
                    holding_txt.setText(HQty);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if("getMarketStatus".equals(jsonResponse.getServiceName())) {
            try {
                MarketStatusPostResponse marketStatusPostResponse = (MarketStatusPostResponse) jsonResponse.getResponse();
                List<MarketStatusResponse> statusResponse = marketStatusPostResponse.getStatusResponse();
                updateMarketStatus(statusResponse);
                //0031809: In Android App, offline orders gets placed when we login even when market is in open state.
                if(quoteResponse != null && !issentsetQuoteDetails) {
                    setQuoteDetails(quoteResponse);
                    issentsetQuoteDetails = true;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if(streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {
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

            if(streamingSymbol.equals(response.getSymbol())) {

                if(((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    last.setText(String.format("%.4f", Double.parseDouble(response.getLast())));
                    change.setText(String.format("%s(%s%%)", String.format("%.4f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
                } else {
                    last.setText(String.format("%.2f", Double.parseDouble(response.getLast())));
                    change.setText(String.format("%s(%s%%)", String.format("%.2f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
                }

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void updateMarketStatus(List<MarketStatusResponse> response) {

        LinkedHashMap<String, String> hm = new LinkedHashMap<>();
        hm.put("green", "1");
        hm.put("yellow", "2");
        hm.put("blue", "3");
        hm.put("pink", "4");
        hm.put("red", "5");

        for (int i = 0; i < response.size(); i++) {
            if(AccountDetails.allowedmarket_nse) {
                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = true;
                    nse_eq = "green";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "yellow";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = true;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "blue";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "pink";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                }
            }

            //FOR FNO
            /*  int fo = 0;*/
            if(AccountDetails.allowedmarket_nfo) {
                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    nse_fno = "green";
                    fno = true;
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "yellow";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = true;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "blue";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "pink";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                }
            }
            //FOR CURRENCY

            /* int cd = 0;*/
            if(AccountDetails.allowedmarket_ncd) {
                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    cd = true;
                    nse_cd = "green";
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "yellow";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = true;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "blue";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "pink";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                }
            }

            if(AccountDetails.allowedmarket_mcx) {
                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "green";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "yellow";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = true;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "blue";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "pink";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;
                }
            }

            if(AccountDetails.allowedmarket_bse) {
                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bseeq = true;
                    bse_eq = "green";
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "yellow";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = true;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "blue";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "pink";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                    bseeq = false;

                }

            }

            if(AccountDetails.allowedmarket_bfo) {
                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bsefno = true;
                    bse_fno = "green";
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    bsefno = false;
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "yellow";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = true;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "blue";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "pink";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                }

            }

            if(AccountDetails.allowedmarket_bcd) {

                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bsecd = true;
                    bse_cd = "green";
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "yellow";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = true;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "blue";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "pink";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;
                }
            }

            if(AccountDetails.allowedmarket_ncdex) {
                if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "green";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "yellow";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = true;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "blue";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = true;

                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "pink";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                } else if(response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                }
            }


            if(Integer.parseInt(hm.get(nse_eq)) <= Integer.parseInt(hm.get(bse_eq))) {
                //Log.d("color",hm.get(nse_eq)+"-----"+hm.get(bse_eq));
                // actionBar.changeStatus("eq", nse_eq);
            } else if(Integer.parseInt(hm.get(bse_eq)) <= Integer.parseInt(hm.get(nse_eq))) {
                //actionBar.changeStatus("eq", bse_eq);
            }

            if(Integer.parseInt(hm.get(nse_fno)) <= Integer.parseInt(hm.get(bse_fno))) {
                // actionBar.changeStatus("fno", nse_fno);
            } else if(Integer.parseInt(hm.get(bse_fno)) <= Integer.parseInt(hm.get(nse_fno))) {
//                actionBar.changeStatus("fno", bse_fno);
            }

            if(Integer.parseInt(hm.get(nse_cd)) <= Integer.parseInt(hm.get(bse_cd))) {
//                actionBar.changeStatus("cd", nse_cd);
            } else if(Integer.parseInt(hm.get(bse_cd)) <= Integer.parseInt(hm.get(nse_cd))) {
//                actionBar.changeStatus("cd", bse_cd);
            }

            if(Integer.parseInt(hm.get(mcx_com)) <= Integer.parseInt(hm.get(ncdex_com))) {
//                actionBar.changeStatus("com", mcx_com);
            } else if(Integer.parseInt(hm.get(ncdex_com)) <= Integer.parseInt(hm.get(mcx_com))) {
//                actionBar.changeStatus("com", ncdex_com);
            }
        }

    }

    //@Override
    public void onFragmentResult(Object data) {
        if(data != null) {
            getArguments().putAll((Bundle) data);
            AccountDetails.currentFragment = NAV_TO_TRADE_SCREEN;

            if(((Bundle) data).getString("GoTO") != null) {

                if(!orderTypeSpinner.getSelectedItem().toString().equals("Market")) {
                    price_txt.setText(((Bundle) data).getString("Price"));
                    //price_txt.requestFocus();
                    qty_txt.setText(((Bundle) data).getString("Qty"));
                    qty_txt.setSelection(qty_txt.getText().length());
                }

                if(data != null && ((Bundle) data).containsKey("GoTO") && ((Bundle) data).getString("GoTO").equalsIgnoreCase("trade")) {
                    if(!orderTypeSpinner.getSelectedItem().toString().equals("Market")) {
                        price_txt.setText(((Bundle) data).getString("Price"));
                        qty_txt.requestFocus();
                        qty_txt.setText(((Bundle) data).getString("qty"));
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else {
                        qty_txt.setText(((Bundle) data).getString("qty"));
                        qty_txt.setSelection(qty_txt.getText().length());
                    }
                }

            } else {

                //IF suppose user pressed back button  from TradePreview screen than need to handle all the values and parameters again on Tarde screen.
                getFromIntent();
                lotQty = ((Bundle) data).getString(LOT_QUANTITY);
                tradeSymbol = ((Bundle) data).getString(TRADE_SYMBOL) + " - " + ((Bundle) data).getString("instName");
                tickSize = ((Bundle) data).getString(TICK_SIZE);
                assetType = ((Bundle) data).getString(ASSET_TYPE);
                exchangeName = ((Bundle) data).getString(EXCHANGE_NAME);
                multiplier = ((Bundle) data).getString(MULTIPLIER);
                scripName = ((Bundle) data).getString(SCRIP_NAME);
                token = ((Bundle) data).getString(TOKEN);
                expiry = ((Bundle) data).getString(EXPIRYDATE);

                sendQuotesRequest(token, assetType);

                if(assetType != null && assetType.equalsIgnoreCase("currency")) {
                    slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 4)});
                    targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 4)});
                    slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 4)});
                } else {
                    slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 2)});
                    targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 2)});
                    slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 2)});

                    slTrigPrice_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    targetprice_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    slprice_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
                price_txt = tradeView.findViewById(R.id.price_text);
                if(assetType != null && assetType.equalsIgnoreCase("currency")) {
                    price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 4)});
                } else {
                    price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 2)});
                    price_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }


                if(assetType != null && (!assetType.equalsIgnoreCase("equity") && !assetType.equalsIgnoreCase("commodity"))) {

                    if(lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    }

                }

                if(assetType != null && (assetType.equalsIgnoreCase("equity") || assetType.equalsIgnoreCase("commodity") || assetType.equalsIgnoreCase("currency"))) {
                    discQty_txt.setEnabled(true);

                    if(lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else {

                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    }


                } //qty_label.setText("Quantity");

                productType.clear();

                for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                    if(assetType.equalsIgnoreCase("equity") && (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(SSEQ_product)
                            || AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(MTF_product))) {
                        productType.add(getProduct(AccountDetails.getAllowedProduct().get(i).getiProductToken()));
                    } else if(!AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(SSEQ_product) && !AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(MTF_product)) {
                        productType.add(getProduct(AccountDetails.getAllowedProduct().get(i).getiProductToken()));
                    }
                }

                final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                prodTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), productType) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                            v.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 10, 15, 12);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
                prodTypeSpinner.setAdapter(prodTypeSpAdapter);
                if(selectedAssetType != 0 && AccountDetails.getProductTypeFlag().equalsIgnoreCase("2")) {

                } else {
                    for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                        if(AccountDetails.getProductTypeFlag().equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {
                            prodTypeSpinner.setSelection(productType.indexOf(getProductType(i+"")));
                            break;
                        }

                    }
                }


                if(assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("Commodity")) {

                    if(assetType.equalsIgnoreCase("Commodity")) {
                        selectedAssetType = 3;


                        orderTypeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderTypeForCommodity()) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(font);
                                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                    v.setTextColor(getResources().getColor(R.color.black));
                                } else {
                                    v.setTextColor(getResources().getColor(R.color.white));
                                }
                                v.setPadding(15, 10, 15, 12);
                                return v;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(font);
                                v.setTextColor(Color.BLACK);
                                v.setPadding(15, 15, 15, 15);
                                return v;
                            }
                        };
                        orderTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                        orderTypeSpinner.setAdapter(orderTypeSpAdapter);
                        orderTypeSpinner.setOnItemSelectedListener(orderTypeSelectedListener);

                        orderLifeSpAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity()) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(font);
                                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                    v.setTextColor(getResources().getColor(R.color.black));
                                } else {
                                    v.setTextColor(getResources().getColor(R.color.white));
                                }
                                v.setPadding(15, 10, 15, 12);
                                return v;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                TextView v = (TextView) super.getView(position, convertView, parent);
                                v.setTypeface(font);
                                v.setTextColor(Color.BLACK);
                                v.setPadding(15, 15, 15, 15);
                                return v;
                            }
                        };
                        orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                        orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                        orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);


                    }

                }
            }
        } else {
            Bundle args = getArguments();
            if(args != null && args.containsKey("GoTO") && args.getString("GoTO").equalsIgnoreCase("trade")) {
                if(!orderTypeSpinner.getSelectedItem().toString().equals("Market")) {
                    price_txt.setText(args.getString("Price"));
                    qty_txt.requestFocus();
                    qty_txt.setText(args.getString("qty"));
                    qty_txt.setSelection(qty_txt.getText().length());
                } else {
                    qty_txt.setText(args.getString("qty"));
                    qty_txt.setSelection(qty_txt.getText().length());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.best5) {
            if (quoteResponse != null) {
                Bundle bundle = new Bundle();
                bundle = getArguments();
                bundle.putString("Symbol", token);
                bundle.putString("Description", quoteResponse.getDescription());
                bundle.putString("TradeSymbol", tradeSymbol);
                bundle.putString("Token", token);
                bundle.putString("Lots", lotQty);
                bundle.putString("AssetType", assetType);
                bundle.putString("Scrip", scripName);
                bundle.putString("ExchangeName", exchangeName);
                bundle.putString("instName", getArguments().getString("instName"));
                bundle.putBoolean("FromTrade", true);
                bundle.putBoolean("isModifyOrder", getArguments().getBoolean(IS_MODIFY_ORDER));
                bundle.putBoolean("isSquareOff", getArguments().getBoolean("isSquareOff"));
                bundle.putBoolean("isFromDemat", getArguments().getBoolean("isFromDemat"));
                //bundle.putBundle("BundleFromTrade", getArguments());

                if (getArguments().containsKey(TRADE_ACTION)) {
                    if (getArguments().getString(TRADE_ACTION).equalsIgnoreCase("1")) {
                        modifyAction = "buy";
                    } else {
                        modifyAction = "sell";
                    }
                }
                bundle.putString("modifyAction", modifyAction);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MarketDepth", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                //navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
            } else {
                GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
            }
        } else if (id == R.id.symbSearchBtn1) {
            Bundle args = new Bundle();
            args.putString("Source", "Trade");
            //navigateTo(NAV_TO_SYMBOL_SEARCH_SCREEN, args, true);
        } else if (id == R.id.cancelbtn) {
            dismiss();
            StopStreaming();
        } else if (id == R.id.place_Order_btn) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                if (quoteResponse != null) {
                    validateAllowedMarket();
                    if (allowed_market_bool) {
                        if (quoteResponse.getRestrictFreshOrder()) {
                            if (AccountDetails.isAllowed) {

                                if (getArguments().getBoolean(IS_MODIFY_ORDER)) {

                                    if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                        if (validateIntradayTimer()) {

                                            validateorderQty();

                                        }

                                    } else {
                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                    }

                                }
                                else {

                                    if (quoteResponse.getSqOff()) {
                                        if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {


                                            if (AppConstants.isEmptyEditText(getActivity(), qty_txt, getString(R.string.TD_QTY_EMPTY_MSG))) {
                                                qty_txt.setFocusable(true);
                                            } else if (Integer.parseInt(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                                if (validateIntradayTimer()) {
                                                    validateorderQty();

                                                }
                                            } else {
                                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                            }
                                        } else {
                                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                        }
                                    } else {
                                        orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG) + AccountDetails.getUsername(getActivity()), getRejectionType("restrict_fresh"));
                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG) + AccountDetails.getUsername(getActivity()), "Ok", true, null);
                                    }
                                }
                            } else {
                                if (getArguments().getBoolean(IS_MODIFY_ORDER)) {

                                    if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                        if (validateIntradayTimer()) {
                                            validateorderQty();

                                        }
                                    } else {
                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                    }

                                }
                                else {

                                    if (quoteResponse.getSqOff()) {
                                        if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                            if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                                if (validateIntradayTimer()) {
                                                    validateorderQty();

                                                }

                                            } else {
                                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                            }
                                        } else {
                                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                        }
                                    } else {
                                        String errorMessage = PrepareRejectionMessage(getAssetType(quoteResponse.getToken()), getExchange(quoteResponse.getToken()), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                        orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                    }
                                }
                            }
                        }
                        else {

                            if (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("2") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("future")) {
                                if (!AccountDetails.isAllowedIntraday && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product)))) {
                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trading not allowed under Intraday Product for user:" + AccountDetails.getUsername(getActivity()), "Ok", true, null);
                                } else if (!AccountDetails.isAllowedDelivery && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Delivery_product)))) {
                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trading not allowed under Delivery Product for user:" + AccountDetails.getUsername(getActivity()), "Ok", true, null);
                                } else {
//                                        if(quoteResponse.getAllowedFO()) {
                                    if (AccountDetails.isAllowedFO) {
                                        if (AccountDetails.isAllowed) {
                                            //place order
                                            if (validateIntradayTimer()) {
                                                validateorderQty();
                                            }
                                        } else {
                                            if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                                if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                                    if (validateIntradayTimer()) {
                                                        validateorderQty();


                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                                }
                                            } else {
                                                if (quoteResponse.getSqOff()) {
                                                    if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                        if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                                            if (validateIntradayTimer()) {
                                                                validateorderQty();
                                                            }
                                                        } else {
                                                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                        }
                                                    } else {
                                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                                    }
                                                } else {
                                                    String errorMessage = PrepareRejectionMessage(getAssetType(quoteResponse.getToken()), getExchange(quoteResponse.getToken()), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                                    orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                                }
                                            }
                                        }
                                    } else {
                                        if (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product))
                                                && quoteResponse.getAllowed()) {

                                            if (validateIntradayTimer()) {

                                                validateorderQty();

                                            }
                                        } else if (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product)) &&
                                                !quoteResponse.getAllowed()) {
                                            if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                                if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                                    if (validateIntradayTimer()) {
                                                        validateorderQty();

                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                                }

                                            } else {
                                                if (quoteResponse.getSqOff()) {
                                                    if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                        if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                                            if (validateIntradayTimer()) {
                                                                validateorderQty();

                                                            }

                                                        } else {
                                                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                        }
                                                    } else {
                                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                                    }
                                                } else {
                                                    String errorMessage = PrepareRejectionMessage(getAssetType(quoteResponse.getToken()), getExchange(quoteResponse.getToken()), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                                    orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                                }
                                            }
                                        } else {
                                            GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GREE_FO_SCRIPT_BANNED_MSG), "Ok", true, null);

                                            orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), getString(R.string.GREE_FO_SCRIPT_BANNED_MSG), getRejectionType("fo_banned"));
                                        }
                                    }
                                }

                            } else if (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("cash")) {
                                if (!AccountDetails.isAllowedIntraday && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product)))) {

                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trading not allowed under Intraday Product for user:" + AccountDetails.getUsername(getActivity()), "Ok", true, null);
                                } else if (!AccountDetails.isAllowedDelivery && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Delivery_product)))) {
                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trading not allowed under Delivery Product for user:" + AccountDetails.getUsername(getActivity()), "Ok", true, null);
                                } else {
                                    if (validateShortSell()) {
//                                            if(quoteResponse.getAllowed()) {
                                        if (AccountDetails.isAllowed) {
                                            //place order

                                            if (validateIntradayTimer()) {
                                                validateorderQty();

                                            }
                                        } else {
                                            if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                                if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {
                                                    if (validateIntradayTimer()) {
                                                        validateorderQty();
                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                                }

                                            } else {
                                                if (quoteResponse.getSqOff()) {
                                                    if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                        if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {
                                                            if (validateIntradayTimer()) {
                                                                validateorderQty();
                                                            }
                                                        } else {
                                                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                        }
                                                    } else {
                                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                                    }
                                                } else {
                                                    String errorMessage = PrepareRejectionMessage(getAssetType(quoteResponse.getToken()), getExchange(quoteResponse.getToken()), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                                    orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                                }
                                            }
                                        }
                                    }
                                }

                            } else {
                                if (!AccountDetails.isAllowedIntraday && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product)))) {
                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trading not allowed under Intraday Product for user:" + AccountDetails.getUsername(getActivity()), "Ok", true, null);
                                } else if (!AccountDetails.isAllowedDelivery && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Delivery_product)))) {
                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Trading not allowed under Delivery Product for user:" + AccountDetails.getUsername(getActivity()), "Ok", true, null);
                                } else {
                                    if (AccountDetails.isAllowed) {
                                        //place order
                                        if (validateIntradayTimer()) {

                                            validateorderQty();
                                        }
                                    } else {
                                        if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                            if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {
                                                if (validateIntradayTimer()) {
                                                    validateorderQty();
                                                }
                                            } else {
                                                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                            }

                                        } else {
                                            if (quoteResponse.getSqOff()) {
                                                if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                    if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {
                                                        if (validateIntradayTimer()) {
                                                            validateorderQty();
                                                        }
                                                    } else {
                                                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                                }
                                            } else {
                                                String errorMessage = PrepareRejectionMessage(getAssetType(quoteResponse.getToken()), getExchange(quoteResponse.getToken()), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                                GreekDialog.alertDialog(getActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                                orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.MARKET_NOT_ALLOWED), "Ok", true, null);
                    }
                } else {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GREE_NO_SCRIPT_MSG), "Ok", true, null);
                }

            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, getActivity().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            } else {
                GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    }
                });
            }
        } else if (id == R.id.gtdButton) {//showDialog(v);
        }
    }

    private String getRejectionType(String rejectionType) {
        if(rejectionType.equalsIgnoreCase("script_banned")) {
            return "0";
        } else if(rejectionType.equalsIgnoreCase("fo_banned")) {
            return "1";
        } else if(rejectionType.equalsIgnoreCase("restrict_fresh")) {
            return "2";
        } else if(rejectionType.equalsIgnoreCase("t2t_banned")) {
            return "3";
        } else if(rejectionType.equalsIgnoreCase("shortsell_not_allowed")) {
            return "4";
        }

        return "";
    }

    private String PrepareRejectionMessage(String assetType, String exchange, String instrument, String expiry, String strike, String opType, String symbol) {
        String msg = "";
        switch (assetType.toLowerCase()) {
            case "equity": {
                msg = "Script Banned " + exchange + " EQ " + " " + symbol + " @ " + AccountDetails.getUsername(getActivity()) + "(RETAILER)";
            }
            break;
            case "fno": {
                msg = "Script Banned " + exchange + " DERV " + " " + symbol + " " + instrument + " " + expiry + " " + opType + " " + strike + " @ " + AccountDetails.getUsername(getActivity()) + "(RETAILER)";
            }
            break;
            case "currency": {
                msg = "Script Banned " + exchange + " CURR " + " " + symbol + " " + instrument + " " + expiry + " " + opType + " " + strike + " @ " + AccountDetails.getUsername(getActivity()) + "(RETAILER)";
            }
            break;
            case "commodity": {
                msg = "Script Banned " + exchange + " COMM " + " " + symbol + " " + instrument + " " + expiry + " " + opType + " " + strike + " @ " + AccountDetails.getUsername(getActivity()) + "(RETAILER)";
            }
            break;
        }

        return msg;
    }

    private void validateAllowedMarket() {

        if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("nse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("1")) && AccountDetails.allowedmarket_nse) {
            //Toast.makeText(getMainActivity(),"Nse"+String.valueOf(AccountDetails.allowedmarket_nse),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("nse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("2") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("future")) && AccountDetails.allowedmarket_nfo) {
            //Toast.makeText(getMainActivity(),"nfo"+String.valueOf(AccountDetails.allowedmarket_nfo),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("nse")
                && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("3")) && AccountDetails.allowedmarket_ncd) {
            //Toast.makeText(getMainActivity(),"ncd"+String.valueOf(AccountDetails.allowedmarket_ncd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") &&
                (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("4")) && AccountDetails.allowedmarket_bse) {
            //Toast.makeText(getMainActivity(),"bse"+String.valueOf(AccountDetails.allowedmarket_bse),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("5") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("future")) && AccountDetails.allowedmarket_bfo) {
            //Toast.makeText(getMainActivity(),"bfo"+String.valueOf(AccountDetails.allowedmarket_bfo),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR")
                && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency")
                || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("6")) && AccountDetails.allowedmarket_bcd) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("9")) && AccountDetails.allowedmarket_mcx) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("7")) && AccountDetails.allowedmarket_ncdex) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("NSECOMM") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("16")) && AccountDetails.allowedmarket_nCOM) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECOMM") && (getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") || getAssetType(quoteResponse.getToken()).equalsIgnoreCase("17")) && AccountDetails.allowedmarket_bCOM) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        }

    }

    public void onEventMainThread(String event) {
        if(event.equalsIgnoreCase("dismiss")) {
            dismiss();
            StopStreaming();
        }

    }

    public void onEventMainThread(MarketStatusResponse response) {
        try {

            valid_status = false;
            isPreOpen_status = false;
            isPostOpen_status = false;

            LinkedHashMap<String, String> hm = new LinkedHashMap<>();
            hm.put("green", "1");
            hm.put("yellow", "2");
            hm.put("blue", "3");
            hm.put("pink", "4");
            hm.put("red", "5");


            if(AccountDetails.allowedmarket_nse) {
                //FOR EQUITY
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("1")) {
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = true;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                }
            }
            //FOR FNO

            if(AccountDetails.allowedmarket_nfo) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = true;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("2")) {


                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                }
            }
            //FOR CURRENCY


            if(AccountDetails.allowedmarket_ncd) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("3")) {


                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = true;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                }
            }

            if(AccountDetails.allowedmarket_mcx) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = true;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;
                }

            }

            if(AccountDetails.allowedmarket_bse) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = true;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                }
            }

            if(AccountDetails.allowedmarket_bfo) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = true;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("5")) {


                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;


                }

            }

            if(AccountDetails.allowedmarket_bcd) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = true;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("6")) {


                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;
                }

            }

            if(AccountDetails.allowedmarket_ncdex) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = true;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                }
            }

            if(AccountDetails.allowedmarket_nCOM) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("16")) {

                    AccountDetails.nse_com_status = false;
                    AccountDetails.isPreOpen_nse_com = false;
                    AccountDetails.isPostClosed_nse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("16")) {

                    AccountDetails.nse_com_status = true;
                    AccountDetails.isPreOpen_nse_com = false;
                    AccountDetails.isPostClosed_nse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("16")) {

                    AccountDetails.nse_com_status = false;
                    AccountDetails.isPreOpen_nse_com = true;
                    AccountDetails.isPostClosed_nse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("16")) {

                    AccountDetails.nse_com_status = true;
                    AccountDetails.isPreOpen_nse_com = false;
                    AccountDetails.isPostClosed_nse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("16")) {

                    AccountDetails.nse_com_status = false;
                    AccountDetails.isPreOpen_nse_com = false;
                    AccountDetails.isPostClosed_nse_com = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("16")) {

                    AccountDetails.nse_com_status = false;
                    AccountDetails.isPreOpen_nse_com = false;
                    AccountDetails.isPostClosed_nse_com = false;
                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("16")) {

                    AccountDetails.nse_com_status = true;
                    AccountDetails.isPreOpen_nse_com = false;
                    AccountDetails.isPostClosed_nse_com = false;
                }
            }

            if(AccountDetails.allowedmarket_bCOM) {
                if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("17")) {

                    AccountDetails.bse_com_status = false;
                    AccountDetails.isPreOpen_bse_com = false;
                    AccountDetails.isPostClosed_bse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("17")) {

                    AccountDetails.bse_com_status = true;
                    AccountDetails.isPreOpen_bse_com = false;
                    AccountDetails.isPostClosed_bse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("17")) {

                    AccountDetails.bse_com_status = false;
                    AccountDetails.isPreOpen_bse_com = true;
                    AccountDetails.isPostClosed_bse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("17")) {

                    AccountDetails.bse_com_status = true;
                    AccountDetails.isPreOpen_bse_com = false;
                    AccountDetails.isPostClosed_bse_com = false;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("17")) {

                    AccountDetails.bse_com_status = false;
                    AccountDetails.isPreOpen_bse_com = false;
                    AccountDetails.isPostClosed_bse_com = true;

                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("17")) {

                    AccountDetails.bse_com_status = false;
                    AccountDetails.isPreOpen_bse_com = false;
                    AccountDetails.isPostClosed_bse_com = false;
                } else if(response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("17")) {

                    AccountDetails.bse_com_status = true;
                    AccountDetails.isPreOpen_bse_com = false;
                    AccountDetails.isPostClosed_bse_com = false;
                }
            }


            valid_status = false;
            isPreOpen_status = false;
            isPostOpen_status = false;

            if(quoteResponse != null) {

                if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.nse_eq_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.bse_eq_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.nse_fno_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.bse_fno_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse")
                        && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency")
                        && AccountDetails.nse_cd_status) {
                    valid_status = true;
                } else if((getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") ||
                        getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR"))
                        && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency")
                        && AccountDetails.bse_cd_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.mcx_com_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.ncdex_com_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("NSECOMM") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.nse_com_status) {
                    valid_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECOMM") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.bse_com_status) {
                    valid_status = true;
                }


                if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPreOpen_nse_eq) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPreOpen_bse_eq) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPreOpen_nse_fno) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPreOpen_bse_fno) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") && AccountDetails.isPreOpen_nse_cd) {
                    isPreOpen_status = true;
                } else if((getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") ||
                        getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR"))
                        && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency")
                        && AccountDetails.isPreOpen_bse_cd) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPreOpen_mcx_com) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("NSECOMM") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPreOpen_nse_com) {
                    isPreOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECOMM") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_bse_com) {
                    isPreOpen_status = true;
                }

                if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPostClosed_nse_eq) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("equity") && AccountDetails.isPostClosed_bse_eq) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPostClosed_nse_fno) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("fno") && AccountDetails.isPostClosed_bse_fno) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("Nse") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency") && AccountDetails.isPostClosed_nse_cd) {
                    isPostOpen_status = true;
                } else if((getExchange(quoteResponse.getToken()).equalsIgnoreCase("bse") ||
                        getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECURR"))
                        && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("currency")
                        && AccountDetails.isPostClosed_bse_cd) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("mcx") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_mcx_com) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("ncdex") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("NSECOMM") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_nse_com) {
                    isPostOpen_status = true;
                } else if(getExchange(quoteResponse.getToken()).equalsIgnoreCase("BSECOMM") && getAssetType(quoteResponse.getToken()).equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_bse_com) {
                    isPostOpen_status = true;
                }

                if(valid_status) {
                    amoCheckBox.setEnabled(true);
                    amoCheckBoxOne.setEnabled(true);
                } else {
                    amoCheckBox.setChecked(false);
                    amoCheckBoxOne.setChecked(false);
                    amoCheckBox.setEnabled(false);
                    amoCheckBoxOne.setEnabled(false);
                    amoAllowed = false;
                    if(isPostOpen_status) {
                        amoCheckBox.setEnabled(true);
                        amoCheckBoxOne.setEnabled(true);
                    }
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onEventMainThread(StreamerBannedResponse response) {
        try {
            sendQuotesRequest(token, assetType);
            GreekDialog.dismissDialog();
            GreekDialog.alertDialog(getActivity(), 0, GREEK, response.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                }
            });

        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AccountDetails.setIsMainActivity(false);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }
        if(qty_txt != null) {
            if(!getArguments().getBoolean("isSquareOff")) {
                qty_txt.clearFocus();
                qty_txt.requestFocus();
            } else {
                qty_txt.clearFocus();
//                price_txt.clearFocus();
                price_txt.requestFocus();

            }

        }
    }

    private Boolean validateShortSell() {
        if(prodTypeSpinner.getSelectedItem() != null) {
            if(prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase(getProduct(Intraday_product))) {
                if(quoteResponse.getT2TScript()) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, " T2T Symbols not allowed for Trading under Intraday Product", "Ok", true, null);
                    return false;
                } else {
                    return true;
                }
            }
        }
        if(!sellBtn.isChecked()) {
            return true;
        }
//        if(quoteResponse.getT2TScript()) {
        if(AccountDetails.isT2TScript) {
//            if(quoteResponse.getAllowedT2TSell()) {
            if(AccountDetails.isAllowedT2TSell) {
                return true;
            }
        } else {
//            if(quoteResponse.getAllowedShortSell()) {
            if(AccountDetails.isAllowedShortSell) {
                return true;
            }
        }
        int totQty = 0;
        if(quoteResponse.getTodQty().equalsIgnoreCase("") && quoteResponse.getPrevQty().equalsIgnoreCase("")) {
            totQty = Integer.parseInt(quoteResponse.getSqOffQty());
        } else {
            totQty = Integer.parseInt(quoteResponse.getTodQty()) + Integer.parseInt(quoteResponse.getPrevQty());
        }

        if(AppConstants.isEmptyEditText(getActivity(), qty_txt, getString(R.string.TD_QTY_EMPTY_MSG))) {
            // GreekDialog.EmptyAlertDialog(getMainActivity(), qty_txt,0, GREEK, getString(R.string.TD_QTY_EMPTY_MSG), "OK", false, null);

            return false;
        } else {
            if(Integer.parseInt(qty_txt.getText().toString()) > totQty) {
                if(quoteResponse.getT2TScript()) {

                    GreekDialog.alertDialog(getActivity(), 0, GREEK, " T2T Symbols not allowed for Delivery Sell for User : " + AccountDetails.getUsername(getActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + totQty, "Ok", true, null);
                    orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), "T2T Symbols not allowed for Delivery Sell for User :" + AccountDetails.getUsername(getActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + totQty, getRejectionType("t2t_banned"));
                    return false;

                } else {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, " Short Sell not allowed under Delivery Product for User :" + AccountDetails.getUsername(getActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + totQty, "Ok", true, null);
                    orderStreamingController.sendStreamingRmsRejectionRequest(getActivity(), AccountDetails.getClientCode(getActivity()), quoteResponse.getToken(), " Short Sell not allowed under Delivery Product for User :" + AccountDetails.getUsername(getActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + totQty, getRejectionType("shortsell_not_allowed"));
                    return false;
                }
            }
        }


        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
        StopStreaming();
    }


    private String getExchange(String token) {

        int tokenInt = Integer.parseInt(token);
        if(((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if(((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if(((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else if((tokenInt >= 1903000000) && (tokenInt <= 1903999999)) {
            return "NSECOMM";
        } else if((tokenInt >= 2003000000) && (tokenInt <= 2003999999)) {
            return "BSECOMM";
        } else if((tokenInt >= 502000000) && (tokenInt <= 502999999)) {
            return "NSECURR";
        } else if((tokenInt >= 1302000000) && (tokenInt <= 1302999999)) {
            return "BSECURR";
        } else {
            return "BSE";
        }
    }

    private String getAssetType(String token) {
        int tokenInt = Integer.parseInt(token);
        if(((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if(((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if(((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }
    }

    private int getProductForBracketCover() {
        //change done Rohit Jadhav 21/4/2021
//4 is a sequence of bracket order in product list older seq no is 1 but now changed
        //change for switch bue sell to change default product
        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if("4".equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {
//                prodTypeSpinner.setSelection(i);
                prodTypeSpinner.setSelection(productType.indexOf(getProductType(i+"")));
                return productType.indexOf(getProductType(i+""));
                // break;
            }

        }

        return 0;
    }

    void setValueFiled() {
        int qty = 1;
        double price = 0.0;


        if(!qty_txt.getText().toString().isEmpty()) {
            qty = Integer.parseInt(qty_txt.getText().toString());
        }

        if(!price_txt.getText().toString().isEmpty() && !price_txt.getText().toString().equalsIgnoreCase(".") && !price_txt.getText().toString().equalsIgnoreCase("-")) {

            price = Double.parseDouble(price_txt.getText().toString());

        }

        if(price_txt.isEnabled() && price > 0.0 && quoteResponse != null) {

            double result = qty * price;


        } else if(!price_txt.isEnabled()) {

            if(action.equalsIgnoreCase("Buy") && quoteResponse != null) {

                price = Double.parseDouble(quoteResponse.getAsk());
                double result = qty * price;


            } else if(action.equalsIgnoreCase("Sell") && quoteResponse != null) {


                price = Double.parseDouble(quoteResponse.getBid());
                double result = qty * price;

            }

        }

    }

    public void SpinnerSelection() {

//String product_type, String order_type, String mainOrder_type, String Order_Life;

        if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(Delivery_product))) {

            if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Limit")) {


                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Stop Loss")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("StopLoss Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            }

        } else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(Intraday_product))) {

            if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Limit")) {


                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Stop Loss")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("StopLoss Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            }

        } else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(MTF_product))) {

            if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Limit")) {


                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Stop Loss")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("StopLoss Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            }

        } else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(Catalyst_product))) {

            if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Limit")) {


                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Stop Loss")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("StopLoss Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            }

        } else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(SSEQ_product))) {

            if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Limit")) {


                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Stop Loss")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("StopLoss Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            }

        } else if(prodTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase(getProduct(TNC_product))) {

            if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Limit")) {


                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Stop Loss")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            } else if(orderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("StopLoss Market")) {

                if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Regular")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Bracket")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Cover")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                } else if(mainOrderTypeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Amo")) {

                    if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("Day")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("IOC")) {


                    } else if(orderLifeSpinner.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("GTD")) {


                    }


                }


            }

        }
    }

    HashMap<String, String> product = new HashMap<>();

    private void getProducttype() {
        List<AllowedProduct> statusResponse = AccountDetails.getAllowedProduct();

        for (int i = 0; i < statusResponse.size(); i++) {
            product.put(statusResponse.get(i).getiProductToken(), statusResponse.get(i).getcProductName());
        }

    }

    private String getProduct(String token) {

        return product.get(token);

    }


    private String getAssetTypeFromToken(String token) {
        int tokenInt = Integer.parseInt(token);
        if(((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if(((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if(((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }
        // return "";
    }


    private void StopStreaming() {
        if(sym.size() > 0)
            streamController.pauseStreaming(getActivity(), "marketPicture", sym);
    }

    public void showProgress() {
        if(getActivity() != null) {
            if(getActivity().getClass().getSimpleName().equalsIgnoreCase("Userkycvalidation")) {
                ((UserKycValidation) getActivity()).showProgress();
            } else {
                ((GreekBaseActivity) getActivity()).showProgress();
            }
        }
    }

    public void hideProgress() {
        if(getActivity() != null) {
            if(getActivity().getClass().getSimpleName().equalsIgnoreCase("Userkycvalidation")) {
                ((UserKycValidation) getActivity()).hideProgress();
            } else {
                ((GreekBaseActivity) getActivity()).hideProgress();
            }
        }
    }


}


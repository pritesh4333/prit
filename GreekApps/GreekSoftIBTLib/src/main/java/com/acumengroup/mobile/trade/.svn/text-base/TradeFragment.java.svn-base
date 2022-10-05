package com.acumengroup.mobile.trade;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
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
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.login.CustomForgotPwdDialogFragment;
import com.acumengroup.mobile.login.CustomTransactionDialogFragment;
import com.acumengroup.ui.AppConstants;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.GreekDialog.Action;
import com.acumengroup.ui.GreekDialog.DialogListener;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

public class TradeFragment extends GreekBaseFragment implements OnClickListener, LabelConfig, DatePickerDialog.OnDateSetListener {
    //Static argument parameters
    public static final String SCRIP_NAME = "ScripName";
    public static final String EXCHANGE_NAME = "ExchangeName";
    public static final String TOKEN = "Token";
    public static final String ASSET_TYPE = "AssetType";
    public static final String TRADE_SYMBOL = "TradeSymbol";
    public static final String LOT_QUANTITY = "Lots";
    public static final String TRADE_ACTION = "Action";
    public static final String MULTIPLIER = "Multiplier";
    public static final String TICK_SIZE = "TickSize";
    public static final String IS_MODIFY_ORDER = "isModifyOrder";
    public static final String SYMBOL_NAME = "SymbolName";
    public static final String PRICE = "Price";
    OrderStreamingController orderStreamingController;
    public Boolean valid_status = false;
    public Boolean isPreOpen_status = false;
    public Boolean isPostOpen_status = false;
    public static final double MAX_MULTIPLIER = 10000000;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    private ImageView imgChange;
    private String streamingSymbol = "";
    private View tradeView;
    private Spinner prodTypeSpinner, orderTypeSpinner, orderLifeSpinner;
    private RadioButton buyBtn;
    private RadioButton sellBtn;
    private Button placeOrderBtn;
    private GreekButton gtdButton, searchBtn, best5Btn;
    private ImageView imgSearch;
    private AppCompatCheckBox amoCheckBox, amoCheckBoxOne;
    CustomTransactionDialogFragment customTransactionDialogFragment;
    CustomForgotPwdDialogFragment customForgotPwdDialogFragment;
    private boolean orderTimeFlag;
    private LinearLayout gtdExpiryLayout, targetLayout, slLayout;
    ArrayList<String> sym = new ArrayList<>();
    public boolean allowed_market_bool = false;
    private Calendar calendar;
    private int year, month, day;
    private String expiry;
    private boolean keepChangingText = true;


    private final OnItemSelectedListener orderLifeSelectionListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            gtdExpiryLayout.setVisibility(View.GONE);
            if (position == 1) {
                if (exchangeName != null) {
                    if (exchangeName.equalsIgnoreCase("mcx")) {
                        if (orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(true);
                        } else if (orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(false);
                        }
                    } else {
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                    }
                }
            }
            if (position == 0) {

                if (assetType != null) {
                    if (assetType.equalsIgnoreCase("equity") || assetType.equalsIgnoreCase("currency") && orderTypeSpinner.getSelectedItemPosition() != 2 && orderTypeSpinner.getSelectedItemPosition() != 5) {

                        if (getArguments().containsKey("DisQty")) {
                            if (Integer.parseInt(getArguments().getString("DisQty")) > 0) {
                                discQty_txt.setText(getArguments().getString("DisQty"));

                            }
                            discQty_txt.setEnabled(true);
                        }
                    } else if (assetType.equalsIgnoreCase("commodity")) {

                        if (exchangeName.equalsIgnoreCase("mcx") || exchangeName.equalsIgnoreCase("ncdex")) {
                            if (getArguments().containsKey("DisQty")) {
                                if (Integer.parseInt(getArguments().getString("DisQty")) != Integer.parseInt(qty_txt.getText().toString())) {
//                                    discQty_txt.setText(getArguments().getString("DisQty"));
                                    discQty_txt.setText(String.valueOf((Integer.parseInt(getArguments().getString("DisQty"))) / (Integer.parseInt(getArguments().getString("Lots")))));
                                    discQty_txt.setEnabled(true);
                                }
                            }
                        } else {
                            discQty_txt.setText("");
                            discQty_txt.setEnabled(true);
                        }

                    } else {
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                    }
                }
            }
            if (position == 2) {
                if (orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtd")) {
                    gtdExpiryLayout.setVisibility(View.VISIBLE);
                } else {
                    gtdExpiryLayout.setVisibility(View.GONE);
                }

                if (orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                }
            }
            if (position == 3) {
                if (orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtc")) {
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                }
            }

            if (getArguments() != null && getArguments().getBoolean("isSquareOff")) {
                orderLifeSpinner.setEnabled(false);
                prodTypeSpinner.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private GreekTextView symName, last, time, change, bid_price, limit_price, holding_txt, ask_price, gtd_label;
    private String lotQty = "";
    private GreekEditText slTrigPrice_txt, slLimitPrice_txt, profitPrice_txt, price_txt, qty_txt, value_text, discQty_txt, targetprice_txt, slprice_txt;
    private GreekTextView qty_label, value_label, disQty_label, price_Label, triggerPrice_Label, amoTextLabel, slpricelbl, targetpricelbl;
    private ArrayAdapter<String> prodTypeSpAdapter, orderTypeSpAdapter, orderLifeSpAdapter;
    private LinearLayout searchLayout;
    private LinearLayout symDetailSLayout;
    private LinearLayout streamingLayout;
    private MarketsSingleScripResponse quoteResponse = null;
    private List<String> productType, normalOrderType, orderLife, plusOrderType;
    private String action, title, tradeSymbol, tickSize, modifyAction;
    private String assetType = "", multiplier = "", scripName = "", token = "";
    private String exchangeName = "";
    private boolean amoCheck, amoCheckOne;
    private GreekTextView symbols_exchange_text;
    private GreekEditText gtd_text;
    private int selectedAssetType;

    private final OnItemSelectedListener orderTypeSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (!getArguments().getBoolean(IS_MODIFY_ORDER)) {

                if (action.equals("Buy") || action.equals("Sell")) {

                    if (position == 0) {
                        orderLifeSpinner.setEnabled(true);
                        prodTypeSpinner.setEnabled(true);
                        slTrigPrice_txt.setText("");
                        slTrigPrice_txt.setEnabled(false);
                        price_txt.setEnabled(true);
                        slTrigPrice_txt.setEnabled(false);
                        slLimitPrice_txt.setText("");
                        slLimitPrice_txt.setEnabled(false);
                        profitPrice_txt.setText("");
                        profitPrice_txt.setEnabled(false);
                        qty_txt.requestFocus();
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                        orderLifeSpinner.setEnabled(true);
                        targetLayout.setVisibility(View.GONE);
                        slLayout.setVisibility(View.GONE);
                        if ("commodity".equalsIgnoreCase(assetType)) {
                            orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity());
                            orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                            orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                            orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                        }
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

                    } else if (position == 1) {
                        orderLifeSpinner.setEnabled(true);
                        prodTypeSpinner.setEnabled(true);
                        slTrigPrice_txt.setEnabled(false);
                        slTrigPrice_txt.setText("");
                        price_txt.setEnabled(false);
                        price_txt.setText("");
                        profitPrice_txt.setEnabled(false);
                        profitPrice_txt.setText("");
                        slLimitPrice_txt.setEnabled(false);
                        slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        orderLifeSpinner.setEnabled(true);
                        discQty_txt.setEnabled(false);
                        discQty_txt.setText("");
                        targetLayout.setVisibility(View.GONE);
                        slLayout.setVisibility(View.GONE);

                        if ("commodity".equalsIgnoreCase(assetType)) {
                            orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity());
                            orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                            orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                            orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                        }
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
                    } else if (position == 2) {
                        orderLifeSpinner.setEnabled(true);
                        prodTypeSpinner.setEnabled(true);
                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);
                        slLimitPrice_txt.setEnabled(false);
                        slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        profitPrice_txt.setEnabled(false);
                        profitPrice_txt.setText("");
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(false);
                        orderLifeSpinner.setSelection(0);
                        orderLifeSpinner.setEnabled(false);
                        targetLayout.setVisibility(View.GONE);
                        slLayout.setVisibility(View.GONE);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if ("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                            discQty_txt.setEnabled(true);
                            orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodityforStoploss());
                            orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                            orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                            orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                            orderLifeSpinner.setEnabled(true);
                        }

                    } else if (position == 3) {
                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setText("");
                        price_txt.setEnabled(false);
                        slLimitPrice_txt.setEnabled(false);
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        profitPrice_txt.setEnabled(false);
                        discQty_txt.setEnabled(false);
                        discQty_txt.setText("");
                        orderLifeSpinner.setSelection(0);
                        orderLifeSpinner.setEnabled(false);

                        targetLayout.setVisibility(View.GONE);
                        slLayout.setVisibility(View.GONE);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                    } else if (position == 4) {
                        orderLifeSpinner.setEnabled(true);
                        prodTypeSpinner.setEnabled(true);
                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);
                        //price_txt.setText("");
                        slLimitPrice_txt.setEnabled(false);
                        slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        profitPrice_txt.setEnabled(false);
                        profitPrice_txt.setText("");
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(true);
                        orderLifeSpinner.setSelection(0);
                        orderLifeSpinner.setEnabled(false);
                        prodTypeSpinner.setSelection(getProductForBracketCover());
                        prodTypeSpinner.setEnabled(false);
                        targetLayout.setVisibility(View.VISIBLE);
                        slLayout.setVisibility(View.VISIBLE);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                    } else if (position == 5) {
                        slTrigPrice_txt.setEnabled(true);
                        price_txt.setEnabled(true);
                        //price_txt.setText("");
                        slLimitPrice_txt.setEnabled(false);
                        slLimitPrice_txt.setText("");
                        price_txt.clearFocus();
                        slTrigPrice_txt.clearFocus();
                        profitPrice_txt.setEnabled(false);
                        profitPrice_txt.setText("");
                        discQty_txt.setText("");
                        discQty_txt.setEnabled(true);
                        orderLifeSpinner.setSelection(1);
                        orderLifeSpinner.setEnabled(false);
                        prodTypeSpinner.setSelection(getProductForBracketCover());
                        prodTypeSpinner.setEnabled(false);
                        targetLayout.setVisibility(View.GONE);
                        slLayout.setVisibility(View.VISIBLE);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                        if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                            discQty_txt.setEnabled(false);
                        if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                            discQty_txt.setEnabled(false);
                    }

                }

            } else {

                if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market")) {
                    slTrigPrice_txt.setEnabled(false);
                    price_txt.setEnabled(false);
                    slTrigPrice_txt.setText("");
                    price_txt.setText("");
                    slLimitPrice_txt.setEnabled(false);
                    profitPrice_txt.setText("");
                    profitPrice_txt.setEnabled(false);
                    qty_txt.requestFocus();
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(false);

                    targetLayout.setVisibility(View.GONE);
                    slLayout.setVisibility(View.GONE);


                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);

                    if ("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0"))) {
                            if (exchangeName.equalsIgnoreCase("mcx") || exchangeName.equalsIgnoreCase("ncdex")) {
                                int pendinglotqty = Integer.parseInt(getArguments().getString("DisQty")) / Integer.parseInt(getArguments().getString("Lots"));
                                discQty_txt.setText(String.valueOf(pendinglotqty));
                            } else {
                                discQty_txt.setText(getArguments().getString("DisQty"));
                            }
                        }
                    }
                    if ("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                } else if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Limit")) {
                    slTrigPrice_txt.setEnabled(false);
                    slTrigPrice_txt.setText("");
                    price_txt.setEnabled(true);
                    price_txt.setText(getArguments().getString(PRICE));
                    slLimitPrice_txt.setEnabled(false);
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    profitPrice_txt.setEnabled(false);

                    targetLayout.setVisibility(View.GONE);
                    slLayout.setVisibility(View.GONE);
                    qty_txt.clearFocus();
                    qty_txt.setFocusable(true);
                    qty_txt.selectAll();
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(false);
                    orderLifeSpinner.setEnabled(true);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 2) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    }


                    if ("commodity".equalsIgnoreCase(assetType) && (orderLifeSpinner.getSelectedItemPosition() == 0 || orderLifeSpinner.getSelectedItemPosition() == 2 || orderLifeSpinner.getSelectedItemPosition() == 3 || orderLifeSpinner.getSelectedItemPosition() == 4)) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0"))) {
                            if ("mcx".equalsIgnoreCase(exchangeName) || "ncdex".equalsIgnoreCase(exchangeName)) {
                                if (Integer.parseInt(getArguments().getString("DisQty")) != Integer.parseInt(qty_txt.getText().toString())) {
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
                    if ("commodity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                } else if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stop Loss")) {
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setEnabled(true);
                    price_txt.setText(getArguments().getString(PRICE));
                    slLimitPrice_txt.setEnabled(false);
                    slLimitPrice_txt.setText("");
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    profitPrice_txt.setEnabled(false);
                    discQty_txt.setEnabled(false);
                    discQty_txt.setText("");
                    orderLifeSpinner.setSelection(0);
                    orderLifeSpinner.setEnabled(false);

                    targetLayout.setVisibility(View.GONE);
                    slLayout.setVisibility(View.GONE);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);

                    if ("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                        discQty_txt.setEnabled(true);

                    }
                } else if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("StopLoss Market")) {
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setText("");
                    price_txt.setEnabled(false);
                    slLimitPrice_txt.setEnabled(false);
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    profitPrice_txt.setEnabled(false);
                    discQty_txt.setEnabled(false);
                    discQty_txt.setText("");
                    orderLifeSpinner.setSelection(0);
                    orderLifeSpinner.setEnabled(false);

                    targetLayout.setVisibility(View.GONE);
                    slLayout.setVisibility(View.GONE);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                } else if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bracket")) {
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setEnabled(true);
                    //price_txt.setText("");
                    slLimitPrice_txt.setEnabled(false);
                    slLimitPrice_txt.setText("");
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    profitPrice_txt.setEnabled(false);
                    profitPrice_txt.setText("");
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                    prodTypeSpinner.setSelection(getProductForBracketCover());
                    orderTypeSpinner.setEnabled(false);
                    prodTypeSpinner.setEnabled(false);
                    orderLifeSpinner.setSelection(0);
                    orderLifeSpinner.setEnabled(false);
                    targetLayout.setVisibility(View.VISIBLE);
                    slLayout.setVisibility(View.VISIBLE);
                    targetprice_txt.setText(getArguments().getString("targetprice"));
                    slprice_txt.setText(getArguments().getString("stoplossprice"));
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                } else if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Cover")) {
                    slTrigPrice_txt.setEnabled(true);
                    price_txt.setEnabled(true);
                    //price_txt.setText("");
                    slLimitPrice_txt.setEnabled(false);
                    slLimitPrice_txt.setText("");
                    price_txt.clearFocus();
                    slTrigPrice_txt.clearFocus();
                    profitPrice_txt.setEnabled(false);
                    profitPrice_txt.setText("");
                    discQty_txt.setText("");
                    discQty_txt.setEnabled(true);
                    prodTypeSpinner.setEnabled(true);
                    prodTypeSpinner.setSelection(getProductForBracketCover());
                    orderLifeSpinner.setSelection(1);
                    orderLifeSpinner.setEnabled(false);
                    targetLayout.setVisibility(View.GONE);
                    slLayout.setVisibility(View.VISIBLE);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                    if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                        discQty_txt.setEnabled(false);
                    if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                        discQty_txt.setEnabled(false);
                }
            }

            if (getArguments() != null && getArguments().getBoolean("isSquareOff")) {
                orderTypeSpinner.setEnabled(false);
                prodTypeSpinner.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private int getProductForBracketCover() {

        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if ("1".equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {
                prodTypeSpinner.setSelection(i);
                return i;
                // break;
            }

        }

        return 0;
    }

    void setValueFiled() {
        int qty = 1;
        double price = 0.0;


        if (!qty_txt.getText().toString().isEmpty()) {
            qty = Integer.parseInt(qty_txt.getText().toString());
        }

        if (!price_txt.getText().toString().isEmpty()) {

            price = Double.parseDouble(price_txt.getText().toString());

        }

        if (price_txt.isEnabled() && price > 0.0 && quoteResponse != null) {

            double result = qty * price;

            if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                value_text.setText(String.format("%.4f", result));
            } else {
                value_text.setText(String.format("%.2f", result));
            }


        } else if (!price_txt.isEnabled()) {

            if (action.equalsIgnoreCase("Buy") && quoteResponse != null) {

                price = Double.parseDouble(quoteResponse.getAsk());
                double result = qty * price;

                if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                    value_text.setText(String.format("%.4f", result));
                } else {
                    value_text.setText(String.format("%.2f", result));
                }


            } else if (action.equalsIgnoreCase("Sell") && quoteResponse != null) {


                price = Double.parseDouble(quoteResponse.getBid());
                double result = qty * price;

                if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                    value_text.setText(String.format("%.4f", result));
                } else {
                    value_text.setText(String.format("%.2f", result));
                }
            }

        }

    }

    void setQuantityFiled() {

        double Value = 0.0;
        double price = 0.0;


        if (!value_text.getText().toString().isEmpty()) {
            Value = Double.parseDouble(value_text.getText().toString());
        }

        if (!price_txt.getText().toString().isEmpty()) {

            price = Double.parseDouble(price_txt.getText().toString());

        }

        if (price_txt.isEnabled() && price > 0.0) {

            double result = Value / price;
            int qty = (int) Math.floor(result);

            qty_txt.setText(String.valueOf(qty));

        } else if (!price_txt.isEnabled() && price == 0.0) {

            if (action.equals("Buy") && quoteResponse != null) {

                price = Double.parseDouble(quoteResponse.getBid());
                double result = Value / price;

                int qty = (int) Math.round(result);

                qty_txt.setText(String.valueOf(qty));


            } else if (action.equals("Sell") && quoteResponse != null) {


                price = Double.parseDouble(quoteResponse.getAsk());
                double result = Value / price;
                int qty = (int) Math.round(result);

                qty_txt.setText(String.valueOf(qty));

            }

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tradeView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_placeorder).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_placeorder).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        AccountDetails.currentFragment = NAV_TO_TRADE_SCREEN;
        customTransactionDialogFragment = new CustomTransactionDialogFragment();
        customForgotPwdDialogFragment = new CustomForgotPwdDialogFragment();
        setupViews();
        settingThemeAssetTradeFragment();
        setupController();
        setupAdapters();
        getFromIntent();

        return tradeView;
    }

    private void settingThemeAssetTradeFragment() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            gtd_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            gtd_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_black_24dp, 0);
            gtd_label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            value_label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            disQty_label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            price_Label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            triggerPrice_Label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            targetpricelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            slpricelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            value_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            price_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            slTrigPrice_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            discQty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            targetprice_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            slprice_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            amoTextLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            amoCheckBox.setButtonDrawable(R.drawable.custom_checkbox_black);
            amoCheckBoxOne.setButtonDrawable(R.drawable.custom_layout_black_sell);

        }

    }

    private void setupController() {
        if (getArguments().containsKey(ASSET_TYPE)) {
            if (getArguments().getString(ASSET_TYPE).equalsIgnoreCase("commodity")) {
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
                normalOrderType.add("Bracket");
                normalOrderType.add("Cover");
                plusOrderType = new ArrayList<>();
                plusOrderType.add("Market");
            }
        } else {
            normalOrderType = new ArrayList<>();
            normalOrderType.add("Limit");
            normalOrderType.add("Market");
            normalOrderType.add("Stop Loss");
            normalOrderType.add("StopLoss Market");
            normalOrderType.add("Bracket");
            normalOrderType.add("Cover");
            plusOrderType = new ArrayList<>();
            plusOrderType.add("Market");
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

    private List<String> getOrderLifeForCommodity() {

        orderLife = new ArrayList<>();
        String ex_name = null;

        if (getArguments().containsKey(EXCHANGE_NAME)) {
            ex_name = getArguments().getString(EXCHANGE_NAME);
        } else if (getArguments().containsKey("Exchange")) {
            ex_name = getArguments().getString("Exchange");
        }

        if (ex_name != null) {
            if (ex_name.equalsIgnoreCase("ncdex")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
                orderLife.add("GTC");
            } else if (ex_name.equalsIgnoreCase("mcx")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
                orderLife.add("GTC");
                orderLife.add("EOS");
            } else if (ex_name.equalsIgnoreCase("nse") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("bse")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
            }
        } else if (exchangeName != null) {

            if (exchangeName.equalsIgnoreCase("ncdex")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
                orderLife.add("GTC");
            } else if (exchangeName.equalsIgnoreCase("mcx")) {
                orderLife.add("Day");
                orderLife.add("IOC");
                orderLife.add("GTD");
                orderLife.add("GTC");
                orderLife.add("EOS");
            } else if (exchangeName.equalsIgnoreCase("nse") || exchangeName.equalsIgnoreCase("bse")) {
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
        orderLife.add("EOS");
        orderLife.add("GTD");
        orderLife.add("GTC");


        return orderLife;
    }

    private void setupViews() {

        orderStreamingController = new OrderStreamingController();
        gtd_label = tradeView.findViewById(R.id.gtd_label);
        imgChange = tradeView.findViewById(R.id.imgChange);
        imgSearch = tradeView.findViewById(R.id.symbSearchBtn1);
        prodTypeSpinner = tradeView.findViewById(R.id.productType_Spinner);
        orderTypeSpinner = tradeView.findViewById(R.id.orderType_Spinner);
        orderLifeSpinner = tradeView.findViewById(R.id.orderLife_Spinner);
        best5Btn = tradeView.findViewById(R.id.best5);

        searchBtn = tradeView.findViewById(R.id.symbSearchBtn);
        buyBtn = tradeView.findViewById(R.id.btnBuy);
        sellBtn = tradeView.findViewById(R.id.btnSell);
        amoCheckBox = tradeView.findViewById(R.id.amo_check);
        amoCheckBoxOne = tradeView.findViewById(R.id.amo_check_one);


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

        placeOrderBtn = tradeView.findViewById(R.id.place_Order_btn);
        symName = tradeView.findViewById(R.id.symbol_name_text);
        symbols_exchange_text = tradeView.findViewById(R.id.symbols_exchange);

        last = tradeView.findViewById(R.id.last_price_text);
        time = tradeView.findViewById(R.id.last_time_text);
        change = tradeView.findViewById(R.id.change_text);
        bid_price = tradeView.findViewById(R.id.txt_bid);
        limit_price = tradeView.findViewById(R.id.txt_limit);
        holding_txt = tradeView.findViewById(R.id.holding_txt);
        ask_price = tradeView.findViewById(R.id.txt_ask);

        if (getArguments() != null) {
            if (getArguments().containsKey(LOT_QUANTITY)) {
                lotQty = getArguments().getString(LOT_QUANTITY);
            }

            if (getArguments().containsKey(ASSET_TYPE)) {
                assetType = getArguments().getString(ASSET_TYPE);
            }

            if (getArguments().containsKey(EXCHANGE_NAME)) {
                exchangeName = getArguments().getString(EXCHANGE_NAME);
            } else if (getArguments().containsKey("Exchange")) {
                exchangeName = getArguments().getString("Exchange");
            }

            if (getArguments().containsKey(SCRIP_NAME)) {
                scripName = getArguments().getString(SCRIP_NAME);
            }
        }
        if (assetType != null) {
            if (assetType.equalsIgnoreCase("1"))
                assetType = "equity";
            else if (assetType.equalsIgnoreCase("2"))
                assetType = "fno";
            else if (assetType.equalsIgnoreCase("3"))
                assetType = "currency";
        }

        gtdButton = tradeView.findViewById(R.id.gtdButton);
        Calendar c = getInstance();
        gtdButton.setText(sdf.format(c.getTime()));

        slTrigPrice_txt = tradeView.findViewById(R.id.trig_price_text);
        //slTrigPrice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        calendar = getInstance();
        year = calendar.get(YEAR);
        month = calendar.get(MONTH);
        day = calendar.get(DAY_OF_MONTH);

        gtd_text = tradeView.findViewById(R.id.gtd_text);
        gtd_text.setShowSoftInputOnFocus(false);
        gtd_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });


        slLimitPrice_txt = tradeView.findViewById(R.id.limit_price_text);
        //slLimitPrice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        profitPrice_txt = tradeView.findViewById(R.id.profit_price_text);
        //profitPrice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        profitPrice_txt = tradeView.findViewById(R.id.profit_price_text);
        //profitPrice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        price_txt = tradeView.findViewById(R.id.price_text);
        //price_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        targetprice_txt = tradeView.findViewById(R.id.targetprice_text);
        //targetprice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        slprice_txt = tradeView.findViewById(R.id.sl_price_text);
        //slprice_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if (assetType != null) {
            if (assetType != null && assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("3") || assetType.equalsIgnoreCase("6")) {
                slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 4)});
                targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 4)});
                slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 4)});
            } else {
                slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 2)});
                targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 2)});
                slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 2)});
            }


            if (assetType != null && assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("3") || assetType.equalsIgnoreCase("6"))
                slLimitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slLimitPrice_txt, 13, 4)});
            else
                slLimitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slLimitPrice_txt, 13, 2)});


            if (assetType != null && assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("3") || assetType.equalsIgnoreCase("6"))
                profitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(profitPrice_txt, 13, 4)});
            else
                profitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(profitPrice_txt, 13, 2)});


            if (assetType != null && assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("3") || assetType.equalsIgnoreCase("6"))
                price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 4)});
            else
                price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 2)});

        }
        qty_txt = tradeView.findViewById(R.id.qty_text);
        value_text = tradeView.findViewById(R.id.value_text);
        qty_txt.setSelection(qty_txt.getText().length());
        InputMethodManager inputMethodManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(qty_txt, InputMethodManager.SHOW_IMPLICIT);
        qty_label = tradeView.findViewById(R.id.qty_label);
        value_label = tradeView.findViewById(R.id.value_label);
        disQty_label = tradeView.findViewById(R.id.disQty_label);
        price_Label = tradeView.findViewById(R.id.priceLbl);
        triggerPrice_Label = tradeView.findViewById(R.id.triggerPriceLbl);
        amoTextLabel = tradeView.findViewById(R.id.amo_textlabel);
        targetpricelbl = tradeView.findViewById(R.id.targetprice_label);
        slpricelbl = tradeView.findViewById(R.id.sl_price_label);
        //qty_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        discQty_txt = tradeView.findViewById(R.id.disc_qty_text);


        //discQty_txt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchLayout = tradeView.findViewById(R.id.search_layout);
        symDetailSLayout = tradeView.findViewById(R.id.symDetails_layout);
        streamingLayout = tradeView.findViewById(R.id.streamingValues);
        gtdExpiryLayout = tradeView.findViewById(R.id.gtd_expiry_layout);
        targetLayout = tradeView.findViewById(R.id.target_price_layout);
        slLayout = tradeView.findViewById(R.id.sl_price_layout);

        productType = new ArrayList<>();


        qty_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (keepChangingText) {
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

        value_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (keepChangingText) {
                    keepChangingText = false;
                    setQuantityFiled();
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

                if (keepChangingText) {
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


        if (getArguments() != null) {
            if (getArguments().containsKey(DESCRIPTION)) {
                tradeSymbol = getArguments().getString(DESCRIPTION);
            }

            if (getArguments().containsKey(TICK_SIZE)) {
                tickSize = getArguments().getString(TICK_SIZE);
            }
            if (getArguments().containsKey(MULTIPLIER)) {
                multiplier = getArguments().getString(MULTIPLIER);
            }

            if (getArguments().containsKey(TOKEN)) {
                token = getArguments().getString(TOKEN);
            }
        }
        //exchangeName = getArguments().getString(EXCHANGE_NAME);
        action = "Buy";
        title = "Place Order";

        if (!getArguments().isEmpty()) {
            if ("equity".equalsIgnoreCase(assetType)) {
                selectedAssetType = 0;
                qty_label.setText("Quantity");

            } else if ("fno".equalsIgnoreCase(assetType) || "future".equalsIgnoreCase(assetType)) {
                selectedAssetType = 1;
                discQty_txt.setEnabled(false);
                qty_label.setText("Quantity");

            } else if ("currency".equalsIgnoreCase(assetType)) {
                selectedAssetType = 2;
                qty_label.setText("Quantity");

            } else if ("commodity".equalsIgnoreCase(assetType)) {
                selectedAssetType = 3;
                discQty_txt.setEnabled(true);
                qty_label.setText("Quantity");
                disQty_label.setText("Disclosed Qty");


            }
            sendQuotesRequest(token, assetType);
            sendMarginRequest("0");
            slTrigPrice_txt.requestFocus();
        }


        gtdButton.setOnClickListener(this);
        best5Btn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        placeOrderBtn.setOnClickListener(this);
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


        setAppTitle(this.getClass().toString(), title);
    }


    private void setupAdapters() {

        orderTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), new ArrayList<String>());
        orderTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
        orderTypeSpinner.setAdapter(orderTypeSpAdapter);

        orderTypeSpinner.setOnItemSelectedListener(orderTypeSelectedListener);

        orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLife());
        orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

        orderLifeSpinner.setAdapter(orderLifeSpAdapter);
        orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);

        List<String> exchanges_list = new ArrayList<String>();
        exchanges_list.add("BSE");
        exchanges_list.add("NSE");

        selectBuySellButton(buyBtn);
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(1, 0);
    }


    private void showDialog(View v) {

        Calendar now = getInstance();
        DatePickerDialog dpd = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                gtd_text.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);


        Date newDate = now.getTime();
        dpd.getDatePicker().setMinDate(newDate.getTime());


        if (getArguments().getString(EXCHANGE_NAME) != null && getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("mcx")) {
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

        } else if (getArguments().getString(EXCHANGE_NAME) != null && getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {
            now.add(DAY_OF_YEAR, 6);
            dpd.getDatePicker().setMaxDate(now.getTimeInMillis());
        }

        dpd.show();
    }

    private String returnValue(GreekEditText text) {
        if (text.getText().toString().length() >= 1) return text.getText().toString();
        return "0";
    }


    private void navigate() {
        Bundle args = new Bundle();
        if (quoteResponse != null) {

            if (quoteResponse.getExch().equalsIgnoreCase("MCX")) {

                if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                    args.putString(TradePreviewFragment.SYMBOL, quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                } else {
                    args.putString(TradePreviewFragment.SYMBOL, quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                }


            } else {
                args.putString(TradePreviewFragment.SYMBOL, quoteResponse.getDescription() + " - " + quoteResponse.getExch());
            }
            args.putString(TradePreviewFragment.DESCRIPTION, quoteResponse.getDescription());
        } else if (getArguments().getString(SYMBOL_NAME) != null) {
            args.putString(TradePreviewFragment.SYMBOL, getArguments().getString(SYMBOL_NAME) + " - " + "");

        } else {
            args.putString(TradePreviewFragment.SYMBOL, scripName + " - " + "");
        }

        if (!getArguments().getBoolean(IS_MODIFY_ORDER)) {
            args.putString(TradePreviewFragment.ACTION, action);


            if (!valid_status) {

                args.putBoolean("isOffline", false);

                if (action.equalsIgnoreCase("buy")) {
                    if (amoCheck == true) {
                        args.putBoolean("valid_amo_status", true);
                        //args.putString(TradePreviewFragment.AMO, "1");
                    } else {
                        args.putBoolean("valid_amo_status", false);
                        //args.putString(TradePreviewFragment.AMO, "0");
                    }
                } else if (action.equalsIgnoreCase("sell")) {
                    if (amoCheckOne == true) {
                        args.putBoolean("valid_amo_status", true);
                        //args.putString(TradePreviewFragment.AMO, "1");
                    } else {
                        args.putBoolean("valid_amo_status", false);
                        //args.putString(TradePreviewFragment.AMO, "0");
                    }
                }
            } else {


                if (action.equalsIgnoreCase("buy")) {
                    if (amoCheck == true) {
                        args.putBoolean("valid_amo_status", true);
                        args.putBoolean("isOffline", false);

                        //args.putString(TradePreviewFragment.AMO, "1");
                    } else {
                        args.putBoolean("valid_amo_status", false);
                        args.putBoolean("isOffline", true);
                        //args.putString(TradePreviewFragment.AMO, "0");
                    }
                } else if (action.equalsIgnoreCase("sell")) {
                    if (amoCheckOne == true) {
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
        } else {
            args.putString(TradePreviewFragment.ACTION, action);
            args.putString(TradePreviewFragment.LEXCHANGEORDERNO1, getArguments().getString("lexchangeOrderNo1"));
            args.putString(TradePreviewFragment.EORDERID, getArguments().getString("eorderid"));
            args.putString(TradePreviewFragment.GORDERID, getArguments().getString("gorderid"));
            args.putString(TradePreviewFragment.LU_TIME_EXCHANGE, getArguments().getString("lu_time_exchange"));
            args.putString(TradePreviewFragment.QTY_FILLED_TODAY, getArguments().getString("qty_filled_today"));
            args.putString(TradePreviewFragment.IOM_RULE_NO, getArguments().getString("iomruleno"));

            if (valid_status) {
                if (getArguments().getString("otype").equalsIgnoreCase("offline")) {
                    args.putBoolean("isOffline", true);
                } else {
                    args.putBoolean("isOffline", false);
                }
            } else {
                args.putBoolean("isOffline", false);

            }

            if (action.equalsIgnoreCase("buy")) {
                if (amoCheck == true) {
                    args.putBoolean("valid_amo_status", true);
                    //args.putString(TradePreviewFragment.AMO, "1");
                } else {
                    args.putBoolean("valid_amo_status", false);
                    //args.putString(TradePreviewFragment.AMO, "0");
                }
            } else if (action.equalsIgnoreCase("sell")) {
                if (amoCheckOne == true) {
                    args.putBoolean("valid_amo_status", true);
                    //args.putString(TradePreviewFragment.AMO, "1");
                } else {
                    args.putBoolean("valid_amo_status", false);
                    //args.putString(TradePreviewFragment.AMO, "0");
                }
            }

        }
        if (quoteResponse.getAsset_type().toString().equalsIgnoreCase("commodity")) {
            args.putString(TradePreviewFragment.QUANTITY, String.valueOf(((Integer.parseInt(qty_txt.getText().toString())) * (Integer.parseInt(quoteResponse.getLot())))));
            args.putString("OriginalQty", qty_txt.getText().toString());
            args.putString(TradePreviewFragment.PENDINGQTY, getArguments().getString("PendingQty"));
            if (getArguments().getString("PendingQty") != null) {
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

        if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market")) {
            args.putString(TradePreviewFragment.PRICE, last.getText().toString());
        } else {
            args.putString(TradePreviewFragment.PRICE, returnValue(price_txt));
        }
        args.putString(TradePreviewFragment.TRIGGER_PRICE, returnValue(slTrigPrice_txt));
        args.putString(TradePreviewFragment.LIMIT_PRICE, returnValue(slLimitPrice_txt));
        args.putString(TradePreviewFragment.PRODUCT_TYPE, prodTypeSpinner.getSelectedItem().toString());
        args.putString(TradePreviewFragment.ORDER_TYPE, orderTypeSpinner.getSelectedItem().toString());
        if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bracket")) {
            args.putString(TradePreviewFragment.TARGETPRICE, targetprice_txt.getText().toString());
            args.putString(TradePreviewFragment.SLPRICE, slprice_txt.getText().toString());
        }
        if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Cover")) {
            args.putString(TradePreviewFragment.SLPRICE, slprice_txt.getText().toString());
        }

        if (orderLifeSpinner.getSelectedItem() != null) {
            args.putString(TradePreviewFragment.ORDER_LIFE, orderLifeSpinner.getSelectedItem().toString());
        }
        args.putString(TradePreviewFragment.PROFIT_PRICE, returnValue(profitPrice_txt));
        args.putString(TradePreviewFragment.GTD_EXPIRY, returnValue(gtd_text));
        args.putString(TradePreviewFragment.TRADE_SYMBOL, quoteResponse.getSymbol());
        args.putString(TradePreviewFragment.TOKEN, token);
        if (quoteResponse != null)
            args.putString(TradePreviewFragment.EXCHANGE, quoteResponse.getExch());
        else args.putString(TradePreviewFragment.EXCHANGE, exchangeName);
        args.putString(TradePreviewFragment.ASSET_TYPE, assetType);
        args.putString(TradePreviewFragment.LOT_QUANTITY, lotQty);
        if (isPreOpen_status) {
            args.putString(TradePreviewFragment.isPreOpen, "1");
        } else {
            args.putString(TradePreviewFragment.isPreOpen, "0");
        }
        if (isPostOpen_status) {
            args.putString(TradePreviewFragment.isPostClosed, "1");
            if (action.equalsIgnoreCase("buy")) {
                if (amoCheck == true) {
                    args.putBoolean("valid_amo_status", true);
                    args.putBoolean("isOffline", false);

                    //args.putString(TradePreviewFragment.AMO, "1");
                } else {
                    args.putBoolean("valid_amo_status", false);
                    args.putBoolean("isOffline", true);
                    //args.putString(TradePreviewFragment.AMO, "0");
                }
            } else if (action.equalsIgnoreCase("sell")) {
                if (amoCheckOne == true) {
                    args.putBoolean("valid_amo_status", true);
                    args.putBoolean("isOffline", false);
                    //args.putString(TradePreviewFragment.AMO, "1");
                } else {
                    args.putBoolean("valid_amo_status", false);
                    args.putBoolean("isOffline", true);
                    //args.putString(TradePreviewFragment.AMO, "0");
                }
            }
            //args.putBoolean("isOffline", true);
        } else {
            args.putString(TradePreviewFragment.isPostClosed, "0");
            //args.putBoolean("isOffline", false);
        }


        if (orderLifeSpinner.getSelectedItemPosition() == 2 || orderLifeSpinner.getSelectedItemPosition() == 3)
            args.putString(TradePreviewFragment.VALIDITY, gtdButton.getText().toString());
        else args.putString(TradePreviewFragment.VALIDITY, "");
        args.putBoolean("isModifyOrder", getArguments().getBoolean(IS_MODIFY_ORDER, false));

        orderTimeFlag = AccountDetails.orderTimeFlag;
        //orderTimeFlag = Util.getPrefs(getMainActivity()).getBoolean("orderTimeFlag", true);
        Boolean showTrasaction = Util.getPrefs(getActivity()).getBoolean("is_validateTransactionshow", false);
        //  if (!AccountDetails.getValidateTransaction()) {
        if (!showTrasaction) {
            if (orderTimeFlag) {

                customTransactionDialogFragment.setArguments(args);
                customTransactionDialogFragment.show(getFragmentManager(), "transaction");
                customTransactionDialogFragment.setCancelable(false);
            } else {

                slTrigPrice_txt.setText("");
                slLimitPrice_txt.setText("");
                profitPrice_txt.setText("");
                price_txt.setText("");
                qty_txt.setText("");
                discQty_txt.setText("");
                slprice_txt.setText("");
                targetprice_txt.setText("");
                navigateTo(NAV_TO_ORDER_PREVIEW_SCREEN, args, true);
            }

        } else {
            slTrigPrice_txt.setText("");
            slLimitPrice_txt.setText("");
            profitPrice_txt.setText("");
            price_txt.setText("");
            qty_txt.setText("");
            discQty_txt.setText("");
            slprice_txt.setText("");
            targetprice_txt.setText("");
            navigateTo(NAV_TO_ORDER_PREVIEW_SCREEN, args, true);
        }
    }

    @SuppressLint("NewApi")
    private void selectBuySellButton(RadioButton selectedBtn) {

        selectedBtn.setChecked(true);

        if (selectedBtn == sellBtn) {
            placeOrderBtn.setBackground(getResources().getDrawable(R.drawable.sell_style));
            best5Btn.setBackground(getResources().getDrawable(R.drawable.sell_style));
            searchBtn.setBackgroundColor(getResources().getColor(R.color.sellColor));
            amoCheckBoxOne.setVisibility(View.VISIBLE);
            amoCheckBox.setVisibility(View.GONE);
            if (!valid_status) {
//                amoCheckBoxOne.setChecked(true);

                if (getArguments().getString("otype") != null) {


                    if (getArguments().getString("otype").equalsIgnoreCase("amo")) {
                        amoCheckBoxOne.setChecked(true);
                        amoCheckBoxOne.setEnabled(false);
                    } else if (getArguments().getString("otype").equalsIgnoreCase("offline")) {


//                amoCheckBoxOne.setChecked(true);
                        amoCheckBoxOne.setEnabled(false);
                    }

                } else if (isPostOpen_status) {
                    amoCheckBoxOne.setEnabled(true);
                }
            } else {
                amoCheckBoxOne.setEnabled(true);
            }

            if (quoteResponse != null) {
                if (!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss market")) {
                        if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                            price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getBid())));
                        } else {
                            price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getBid())));
                        }
                    }
                }
            }
        }

        if (selectedBtn == buyBtn) {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                placeOrderBtn.setBackground(getResources().getDrawable(R.drawable.whitetheambuy_style));
                best5Btn.setBackground(getResources().getDrawable(R.drawable.whitetheambuy_style));
                searchBtn.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));

            }else {
                placeOrderBtn.setBackground(getResources().getDrawable(R.drawable.buy_style));
                best5Btn.setBackground(getResources().getDrawable(R.drawable.buy_style));
                searchBtn.setBackgroundColor(getResources().getColor(R.color.buyColor));

            }
            amoCheckBoxOne.setVisibility(View.GONE);
            amoCheckBox.setVisibility(View.VISIBLE);
            if (!valid_status) {
                if (getArguments().getString("otype") != null) {
                    if (getArguments().getString("otype").equalsIgnoreCase("amo")) {
                        amoCheckBox.setChecked(true);
                        amoCheckBox.setEnabled(false);
                    } else if (getArguments().getString("otype").equalsIgnoreCase("offline")) {

                        amoCheckBox.setEnabled(false);
                    }
                } else if (isPostOpen_status) {
                    amoCheckBox.setEnabled(true);
                }
            } else {
                amoCheckBox.setEnabled(true);
            }


            if (quoteResponse != null) {
                if (!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Stoploss market")) {
                        if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
                            price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAsk())));
                        } else {
                            price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));
                        }
                    }
                }
            }
        }

        if (selectedBtn == buyBtn || selectedBtn == sellBtn) {
            qty_txt.clearFocus();
            qty_txt.requestFocus();
            slLimitPrice_txt.setText("");
            profitPrice_txt.setText("");
            slLimitPrice_txt.setEnabled(false);
            profitPrice_txt.setEnabled(false);
            slLimitPrice_txt.setText("");
            profitPrice_txt.setText("");
            price_txt.setEnabled(false);
            discQty_txt.setEnabled(false);
            if (orderTypeSpinner.getSelectedItemPosition() == 2) {

                slTrigPrice_txt.setEnabled(true);
                price_txt.setEnabled(true);
                slLimitPrice_txt.setEnabled(false);
                slLimitPrice_txt.setText("");
                price_txt.clearFocus();
                slTrigPrice_txt.clearFocus();
                profitPrice_txt.setEnabled(false);
                profitPrice_txt.setText("");
                discQty_txt.setText("");
                discQty_txt.setEnabled(false);
                orderLifeSpinner.setSelection(0);
                orderLifeSpinner.setEnabled(false);
                targetLayout.setVisibility(View.GONE);
                slLayout.setVisibility(View.GONE);
                if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                    discQty_txt.setEnabled(false);
                if ("equity".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                    discQty_txt.setEnabled(false);
                if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 0)
                    discQty_txt.setEnabled(false);
                if ("currency".equalsIgnoreCase(assetType) && orderLifeSpinner.getSelectedItemPosition() == 1)
                    discQty_txt.setEnabled(false);
                if ("commodity".equalsIgnoreCase(assetType) && "mcx".equalsIgnoreCase(exchangeName)) {
                    discQty_txt.setEnabled(true);
                    orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodityforStoploss());
                    orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                    orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                    orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);
                    orderLifeSpinner.setEnabled(true);
                }
            }
            if (orderTypeSpinner.getSelectedItemPosition() == 0) {
                price_txt.setEnabled(true);
                discQty_txt.setEnabled(true);
                orderLifeSpinner.setEnabled(true);
                if ("equity".equalsIgnoreCase(assetType)) discQty_txt.setEnabled(true);
                if ("commodity".equalsIgnoreCase(assetType)) discQty_txt.setEnabled(true);
                if ("currency".equalsIgnoreCase(assetType)) discQty_txt.setEnabled(true);
            }
            if (orderTypeSpinner.getSelectedItemPosition() == 3) {
                orderLifeSpinner.setSelection(0);
                orderLifeSpinner.setEnabled(false);
                slTrigPrice_txt.setEnabled(true);
                price_txt.setEnabled(false);
                discQty_txt.setEnabled(false);
            }
            if (orderTypeSpinner.getSelectedItemPosition() == 1) {
                orderLifeSpinner.setEnabled(true);
            }

            if (orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) {
                price_txt.setEnabled(true);
                prodTypeSpinner.setSelection(getProductForBracketCover());
                prodTypeSpinner.setEnabled(false);
            }

            if ("equity".equals(assetType)) {
                if (orderTypeSpinner.getSelectedItemPosition() == 2) {

                } else if (orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 3) {
                    discQty_txt.setEnabled(false);
                } else {

                    if (lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else {
                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    }

                    discQty_txt.setEnabled(true);
                    if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                        discQty_txt.setText(getArguments().getString("DisQty"));
                }
            } else if ("fno".equalsIgnoreCase(assetType)) {
                discQty_txt.setEnabled(false);
            } else if ("currency".equalsIgnoreCase(assetType)) {
                if (orderTypeSpinner.getSelectedItemPosition() != 2) {
                    discQty_txt.setEnabled(true);
                    if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                        discQty_txt.setText(getArguments().getString("DisQty"));
                }
            } else if ("commodity".equalsIgnoreCase(assetType)) {

                if (orderTypeSpinner.getSelectedItemPosition() == 2) {

                    if (exchangeName.equalsIgnoreCase("mcx")) {
                        discQty_txt.setEnabled(true);
                        if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                            discQty_txt.setText(getArguments().getString("DisQty"));
                    } else {
                        discQty_txt.setEnabled(false);
                    }

                } else {
                    qty_txt.setText("1");
                    qty_txt.setSelection(qty_txt.getText().length());
                    discQty_txt.setEnabled(true);
                    if (getArguments().getString("DisQty") != null && (!getArguments().getString("DisQty").equalsIgnoreCase("0")))
                        discQty_txt.setText(getArguments().getString("DisQty"));
                }
            }

            if (assetType != null && (!assetType.equalsIgnoreCase("equity") && !assetType.equalsIgnoreCase("commodity"))) {
                qty_label.setText("Quantity");

                if (lotQty != null && !lotQty.equals("")) {
                    qty_txt.setText(lotQty);
                    qty_txt.setSelection(qty_txt.getText().length());
                }

            } else {
                if (assetType != null && (assetType.equalsIgnoreCase("equity") || assetType.equalsIgnoreCase("commodity") || assetType.equalsIgnoreCase("currency"))) {

                    if (exchangeName != null && exchangeName.equalsIgnoreCase("mcx")) {
                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else if (exchangeName != null && exchangeName.equalsIgnoreCase("ncdex")) {
                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else {
                        if (lotQty != null && !lotQty.equals("")) {
                            qty_txt.setText(lotQty);
                            qty_txt.setSelection(qty_txt.getText().length());
                        } else {
                            qty_txt.setText("1");
                            qty_txt.setSelection(qty_txt.getText().length());
                        }
                    }
                } else {
                    if (lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    }
                }
            }

            if (orderLifeSpinner.getSelectedItemPosition() == 1) {
                discQty_txt.setText("");
                discQty_txt.setEnabled(false);
            }
            productType.clear();

            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                if (selectedAssetType == 0 && (AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") || AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("mtf"))) {
                    productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                } else if (!AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") && !AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("mtf")) {
                    productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                }
            }


            prodTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), productType);
            prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
            prodTypeSpinner.setAdapter(prodTypeSpAdapter);
            int pos = 0;
            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                if (selectedAssetType != 0 && (AccountDetails.getProductTypeFlag().equalsIgnoreCase("2") || AccountDetails.getProductTypeFlag().equalsIgnoreCase("3"))) {

                } else {
                    if (AccountDetails.getProductTypeFlag().equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {
                        prodTypeSpinner.setSelection(i);
                        break;
                    }
                }
                pos++;
            }

            if (orderTypeSpinner.getSelectedItem() != null) {
                if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bracket") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                    prodTypeSpinner.setSelection(getProductForBracketCover());
                }

            }

            orderTypeSpAdapter.clear();

            if (assetType != null && assetType.equalsIgnoreCase("commodity")) {
                getOrderTypeForCommodity();

            }

            for (String s : normalOrderType) {
                orderTypeSpAdapter.add(s);
            }
            //orderTypeSpinner.setAdapter(orderTypeSpAdapter);
            orderTypeSpAdapter.notifyDataSetChanged();
        } else {
            qty_txt.requestFocus();
            slLimitPrice_txt.setEnabled(true);
            profitPrice_txt.setEnabled(true);
            price_txt.setText("");
            price_txt.setEnabled(false);
            price_txt.clearFocus();
            profitPrice_txt.setText("");
            slTrigPrice_txt.setEnabled(true);

            discQty_txt.setText("");
            discQty_txt.setEnabled(false);

            productType.clear();
            productType.add(getString(TD_MARGIN_PLUS_ORDER_TYPE));

            prodTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), productType);
            prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
            prodTypeSpinner.setAdapter(prodTypeSpAdapter);
            int pos = 0;
            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                if (selectedAssetType != 0 && (AccountDetails.getProductTypeFlag().equalsIgnoreCase("2") || AccountDetails.getProductTypeFlag().equalsIgnoreCase("3"))) {

                } else {
                    if (AccountDetails.getProductTypeFlag().equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {
                        prodTypeSpinner.setSelection(i);
                        break;
                    }
                }
                pos++;
            }

            if (orderTypeSpinner.getSelectedItem() != null) {
                if (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bracket") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                    prodTypeSpinner.setSelection(getProductForBracketCover());
                }

            }

            orderTypeSpAdapter.clear();

            for (String s : plusOrderType) {
                orderTypeSpAdapter.add(s);
            }

            //orderTypeSpinner.setAdapter(orderTypeSpAdapter);
            orderTypeSpAdapter.notifyDataSetChanged();
        }




      /*  if(orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("bracket") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover"))
        {
            prodTypeSpinner.setSelection(getProductForBracketCover());
        }*/


    }

    private void setRetainedQty() {
        int preqQty;
        switch (selectedAssetType) {
            case 0:
                preqQty = Integer.parseInt(Util.getPrefs(getMainActivity()).getString("GREEK_LAST_CASH_QTY", "1"));
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
        if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
            placeOrderBtn.setText("Modify Order");
            if (getArguments().getString(TRADE_ACTION).equalsIgnoreCase("1")) {
                modifyAction = "buy";
            } else {
                modifyAction = "sell";
            }
            if ("Buy".equalsIgnoreCase(modifyAction)) {
                action = "Buy";
                selectBuySellButton(buyBtn);
                disableButtons(buyBtn, sellBtn);

            } else if ("Sell".equalsIgnoreCase(modifyAction)) {
                action = "Sell";
                selectBuySellButton(sellBtn);
                disableButtons(sellBtn, buyBtn);
            }


            handlePriceField(getArguments().getString(EXCHANGE_NAME), getArguments().getString("OrderType"), getArguments().getString("Product"));

            for (int i = 0; i < normalOrderType.size(); i++) {
                if (normalOrderType.get(i).equalsIgnoreCase(getOrderType(getArguments().getString("OrderType")))) {
                    orderTypeSpinner.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < productType.size(); i++) {
                if (productType.get(i).equalsIgnoreCase(getProductType(getArguments().getString("Product")))) {
                    prodTypeSpinner.setSelection(i);
                    break;
                }
            }
            prodTypeSpinner.setEnabled(false);

            if (!getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("bse")) {
                if (getOrderType(getArguments().getString("OrderType")).equalsIgnoreCase("stop loss")) {
                    orderTypeSpinner.setEnabled(true);
                } else {
                    orderTypeSpinner.setEnabled(true);
                }
            } else {

                orderTypeSpinner.setEnabled(true);

            }

            String disQty;
            if (getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("mcx") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {
                qty_txt.setText(String.valueOf((Integer.parseInt(getArguments().getString("PendingQty"))) / (Integer.parseInt(getArguments().getString("Lots")))));
                qty_txt.setSelection(qty_txt.getText().length());
                disQty = String.valueOf((Integer.parseInt(getArguments().getString("DisQty"))) / (Integer.parseInt(getArguments().getString("Lots"))));
            } else {
                qty_txt.setText(getArguments().getString("PendingQty"));
                qty_txt.setSelection(qty_txt.getText().length());
                disQty = getArguments().getString("DisQty");
            }


            if (disQty != null && disQty.length() > 0 && Integer.parseInt(disQty) > 0) {
                discQty_txt.setText(disQty);
            } else {
                discQty_txt.setText("");
            }

            price_txt.setText(getArguments().getString(PRICE));
            slTrigPrice_txt.setText(getArguments().getString("TriggerPrice"));
            searchLayout.setVisibility(View.GONE);
            symDetailSLayout.setVisibility(View.VISIBLE);
            streamingLayout.setVisibility(View.VISIBLE);

            for (int i = 0; i < orderLife.size(); i++) {
                if (orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                    orderLifeSpinner.setSelection(i);
                    break;
                }
            }

            if (getArguments().getString("OrderLiveDays") != null && getArguments().getString("OrderLiveDays").length() > 0) {
                gtdButton.setText(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("OrderLiveDays"), "dd-MMM-yyyy", "bse"));

            }

            if (getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("mcx") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("nse") || getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("bse")) {
                orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity());
                orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);

                for (int i = 0; i < orderLife.size(); i++) {
                    if (orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                        orderLifeSpinner.setSelection(i);
                        break;
                    }
                }
                if (!getArguments().getString("lgoodtilldate").equalsIgnoreCase("0")) {

                    Log.e("TradeFragment", "lgoodtilldate===>" + getArguments().getString("lgoodtilldate"));

                    if (!getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("MCX") && !getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {

                        gtd_text.setText(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("lgoodtilldate"),
                                "dd/MM/yyyy", "nse"));
                    } else {
                        gtd_text.setText(DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("lgoodtilldate"),
                                "dd/MM/yyyy", "bse"));
                    }

                }
            }

            symName.setText(getArguments().getString(DESCRIPTION) + " - " + getArguments().getString("instName"));


            title = "Modify Order";
            setAppTitle(getClass().toString(), title);

        } else if (getArguments().containsKey(TRADE_ACTION)) {
            if ("buy".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                action = "Buy";
                selectBuySellButton(buyBtn);
            } else if ("sell".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                action = "Sell";
                selectBuySellButton(sellBtn);
            } else {
                action = "Buy";
                selectBuySellButton(buyBtn);
            }
            if (getArguments().containsKey(PRICE)) {
                price_txt.setText(getArguments().getString(PRICE));
            }
        }
        if (getArguments().containsKey(ASSET_TYPE)) {
            if (getArguments().getString(ASSET_TYPE).equalsIgnoreCase("commodity")) {
                orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity());
                orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);

                for (int i = 0; i < orderLife.size(); i++) {
                    if (orderLife.get(i).equalsIgnoreCase(getArguments().getString("OrderLife"))) {
                        orderLifeSpinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        if (getArguments().getBoolean("isSquareOff")) {

            if ("buy".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                selectBuySellButton(buyBtn);
                action = "Buy";
                disableButtons(buyBtn, sellBtn);
            } else if ("sell".equalsIgnoreCase(getArguments().getString(TRADE_ACTION))) {
                action = "Sell";
                selectBuySellButton(sellBtn);
                disableButtons(sellBtn, buyBtn);
            }

            if (getArguments() != null && getArguments().getString("NetQty") != null) {
                if (getArguments().getString("AssetType").equalsIgnoreCase("1") || getArguments().getString("AssetType").equalsIgnoreCase("3") || getArguments().getString("AssetType").equalsIgnoreCase("equity") || getArguments().getString("AssetType").equalsIgnoreCase("currency") || getArguments().getString("AssetType").equalsIgnoreCase("commodity")) {
                    qty_txt.setText(String.valueOf(Math.abs(Integer.parseInt(getArguments().getString("NetQty")))));
                    qty_txt.setSelection(qty_txt.getText().length());
                } else if (getArguments().getString("AssetType").equalsIgnoreCase("2") || getArguments().getString("AssetType").equalsIgnoreCase("fno")) {
                    qty_txt.setText(String.valueOf(Math.abs(Integer.parseInt(getArguments().getString("NetQty")))));
                    qty_txt.setSelection(qty_txt.getText().length());
                }
            }

            for (int i = 0; i < productType.size(); i++) {
                if (productType.get(i).equalsIgnoreCase(getProductType(getArguments().getString("Product")))) {
                    prodTypeSpinner.setSelection(i);
                    break;
                }
            }

            prodTypeSpinner.setEnabled(false);

            if (getArguments().getString(EXCHANGE_NAME).equalsIgnoreCase("ncdex")) {
                orderTypeSpinner.setSelection(0);

            } else {
                orderTypeSpinner.setSelection(1);
            }


            orderLifeSpinner.setSelection(0);


        } else if (getArguments().getBoolean("isFromDemat")) {
            if (getArguments().getString("Action").equalsIgnoreCase("buy")) {
                selectBuySellButton(buyBtn);
            } else {
                selectBuySellButton(sellBtn);
            }

            qty_txt.setText(getArguments().getString("AvailableForSell"));
            price_txt.setText(getArguments().getString("LTP"));
            qty_txt.setSelection(qty_txt.getText().length());
            prodTypeSpinner.setEnabled(true);
        }

        qty_txt.clearFocus();
        price_txt.clearFocus();
        qty_txt.requestFocus();
    }


    private void handlePriceField(String mExchange, String mOrderType, String mProduct) {

        normalOrderType.clear();
        if (mProduct.contains("MarginPlus")) {
            qty_txt.setEnabled(false);
            orderTypeSpinner.setEnabled(false);
        } else if (mProduct.contains("SELL from DP")) {
            productType.clear();
            productType.add("SELL from DP");
            prodTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), android.R.layout.simple_list_item_1, productType);
            prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

            prodTypeSpinner.setAdapter(prodTypeSpAdapter);

            prodTypeSpAdapter.notifyDataSetChanged();

        }
        if (mExchange.equals("BSE")) {
            switch (mOrderType) {
                case "1":
                    normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
                    normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
                    break;
                case "3":
                    slTrigPrice_txt.setEnabled(true);
                    normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
                    normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
                    normalOrderType.add("Stop Loss");
                    normalOrderType.add("StopLoss Market");
                    break;
                default:
                    slTrigPrice_txt.setEnabled(true);
                    normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
                    normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
                    normalOrderType.add("Stop Loss");
                    normalOrderType.add("StopLoss Market");
                    normalOrderType.add("Bracket");
                    normalOrderType.add("Cover");

                    break;
            }
        } else if (mExchange.equalsIgnoreCase("MCX") && (mOrderType.equals("Limit") || mOrderType.equals("1") || mOrderType.equals("2"))) {
            normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
            normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
        } else if (mExchange.equalsIgnoreCase("MCX") && mOrderType.equals("3")) {
            slTrigPrice_txt.setEnabled(true);
            normalOrderType.add(getString(R.string.TD_LIMIT_ORDER_TYPE));
            normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));
            normalOrderType.add("Stop Loss");
            normalOrderType.add("StopLoss Market");

        } else if (mExchange.equalsIgnoreCase("MCX") && mOrderType.equals("Market")) {
            normalOrderType.add(getString(R.string.TD_MARKET_ORDER_TYPE));

        } else {

            if (mExchange.equalsIgnoreCase("mcx") && mOrderType.equalsIgnoreCase("3")) {
                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
            } else if (mExchange.equalsIgnoreCase("nse") && (mOrderType.equalsIgnoreCase("4") || mOrderType.equalsIgnoreCase("3"))) {
                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
                normalOrderType.add("StopLoss Market");
            } else if (mOrderType.equalsIgnoreCase("1") && mExchange.equalsIgnoreCase("ncdex")) {

                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                //normalOrderType.add("Stop Loss");
                // normalOrderType.add("StopLoss Market");

            } else if (mOrderType.equalsIgnoreCase("1")) {

                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
                normalOrderType.add("StopLoss Market");

            } else {
                slTrigPrice_txt.setEnabled(true);
                normalOrderType.add("Limit");
                normalOrderType.add("Market");
                normalOrderType.add("Stop Loss");
                normalOrderType.add("StopLoss Market");
                normalOrderType.add("Cover");
                normalOrderType.add("Bracket");
            }
        }

        orderTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), normalOrderType);
        orderTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
        orderTypeSpinner.setAdapter(orderTypeSpAdapter);
        orderTypeSpAdapter.notifyDataSetChanged();
    }

    public String getOrderType(String type) {
        if (type.equalsIgnoreCase("1")) {
            return "Limit";
        } else if (type.equalsIgnoreCase("2")) {
            return "Market";
        } else if (type.equalsIgnoreCase("3")) {
            return "Stop Loss";
        } else if (type.equalsIgnoreCase("4")) {
            return "Stoploss Market";
        } else if (type.equalsIgnoreCase("5")) {
            return "Cover";
        } else if (type.equalsIgnoreCase("7")) {
            return "Bracket";
        }
        return "";
    }

    public String getProductType(String type) {

        if (type.equalsIgnoreCase("1"))
            return "Intraday";
        else if (type.equalsIgnoreCase("0"))
            return "Delivery";
        else if (type.equalsIgnoreCase("2"))
            return "MTF";
        else if (type.equalsIgnoreCase("5"))
            return "SSEQ";
        else if (type.equalsIgnoreCase("3"))
            return "TNC";
        else if (type.equalsIgnoreCase("4"))
            return "CATALYST";
        return "";
    }

    // when navigating to trade from DematHolding
    private void disableButtons(RadioButton enableBtn, RadioButton d1) {
        enableBtn.setEnabled(true);
        enableBtn.setTextColor(Color.WHITE);
        d1.setEnabled(false);
    }

    private void sendQuotesRequest(String token, String assetType) {
        searchLayout.setVisibility(View.GONE);
        symDetailSLayout.setVisibility(View.VISIBLE);
        streamingLayout.setVisibility(View.VISIBLE);
        if (tradeSymbol != null) {

            symName.setText(tradeSymbol);

        } else if (getArguments().getString(DESCRIPTION) != null) {

            symName.setText(getArguments().getString(DESCRIPTION) + " - " + getArguments().getString("instName"));
        }


        if (exchangeName != null)
            symbols_exchange_text.setText(exchangeName.toUpperCase());
        showProgress();
        quoteResponse = null;
        MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), token, assetType, AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);

        MarketsSingleScripRequest.sendRequestQty(AccountDetails.getUsername(getMainActivity()), token, assetType, AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);

        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_RETAIN_QTY_TOGGLE", false)) {
            setRetainedQty();
        }

        if ("equity".equals(assetType)) {
            selectedAssetType = 0;
        } else if ("fno".equals(assetType) || "future".equalsIgnoreCase(assetType)) {
            selectedAssetType = 1;
            discQty_txt.setEnabled(false);
        } else if ("currency".equals(assetType)) {
            selectedAssetType = 2;
        } else if ("commodity".equalsIgnoreCase(assetType)) {
            selectedAssetType = 3;
        }
    }


    private void sendMarginRequest(String exchangeType) {

        //setAppTitle(getClass().toString(),"Margin Details");
        MarginDetailRequest marginDetailRequest = new MarginDetailRequest();
        marginDetailRequest.setGcid(AccountDetails.getClientCode(getMainActivity()));
        marginDetailRequest.setSegment(exchangeType);
        marginDetailRequest.setExchange_type("0");
        orderStreamingController.sendMarginDetailRequest(getActivity(), marginDetailRequest);
    }


    public void onEventMainThread(MarginDetailResponse marginDetailResponse) {
        try {
            limit_price.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getAvailCashCredit())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setQuoteDetails(MarketsSingleScripResponse quoteResponse) {

        if (tradeSymbol != null && !tradeSymbol.trim().equals("null") && tradeSymbol.trim().length() > 0)
            symName.setText(tradeSymbol);
        else if (getArguments().getString(DESCRIPTION) != null)
            symName.setText(getArguments().getString(DESCRIPTION) + " - " + getArguments().getString("instName"));

        valid_status = false;
        isPreOpen_status = false;
        isPostOpen_status = false;

        if (quoteResponse.getExch().equalsIgnoreCase("Nse") && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && AccountDetails.nse_eq_status) {
            valid_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("4")) && AccountDetails.bse_eq_status) {
            valid_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && (quoteResponse.getAsset_type().equalsIgnoreCase("fno") || quoteResponse.getAsset_type().equalsIgnoreCase("2")) && AccountDetails.nse_fno_status) {
            valid_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && (quoteResponse.getAsset_type().equalsIgnoreCase("fno") || quoteResponse.getAsset_type().equalsIgnoreCase("5")) && AccountDetails.bse_fno_status) {
            valid_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("3")) && AccountDetails.nse_cd_status) {
            valid_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) && AccountDetails.bse_cd_status) {
            valid_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("mcx") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.mcx_com_status) {
            valid_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("ncdex") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.ncdex_com_status) {
            valid_status = true;
        }


        if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPreOpen_nse_eq) {
            isPreOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPreOpen_bse_eq) {
            isPreOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPreOpen_nse_fno) {
            isPreOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPreOpen_bse_fno) {
            isPreOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPreOpen_nse_cd) {
            isPreOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPreOpen_bse_cd) {
            isPreOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("mcx") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPreOpen_mcx_com) {
            isPreOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("ncdex") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
            isPreOpen_status = true;
        }

        if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPostClosed_nse_eq) {
            isPostOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPostClosed_bse_eq) {
            isPostOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPostClosed_nse_fno) {
            isPostOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPostClosed_bse_fno) {
            isPostOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPostClosed_nse_cd) {
            isPostOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPostClosed_bse_cd) {
            isPostOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("mcx") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_mcx_com) {
            isPostOpen_status = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("ncdex") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
            isPostOpen_status = true;
        }

        if (valid_status) {

        } else {
            amoCheckBox.setChecked(false);
            amoCheckBoxOne.setChecked(false);
            amoCheckBox.setEnabled(false);
            amoCheckBoxOne.setEnabled(false);
            if (isPostOpen_status) {
                if (!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                    amoCheckBox.setEnabled(true);
                    amoCheckBoxOne.setEnabled(true);
                } else {
                    if (getArguments().getString("otype") != null) {
                        if (getArguments().getString("otype").equalsIgnoreCase("amo")) {
                            amoCheckBox.setChecked(true);
                            amoCheckBox.setEnabled(false);
                            amoCheckBoxOne.setEnabled(true);
                            amoCheckBoxOne.setChecked(true);
                        } else if (getArguments().getString("otype").equalsIgnoreCase("offline")) {

                            amoCheckBox.setEnabled(false);
                            amoCheckBoxOne.setChecked(false);
                        }
                    }
                }
            }
        }

        if (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) {
            last.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getLast())));
            change.setText(String.format("%s(%s%%)", String.format("%.4f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
            time.setText(quoteResponse.getLtt());

            if (!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                if (getArguments().containsKey(TRADE_ACTION)) {
                    if (getArguments().getString(TRADE_ACTION).equalsIgnoreCase("buy") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("1")) {
                        if (getArguments().containsKey("MdPrice")) {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(getArguments().getString("MdPrice"))));
                            }
                        } else {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAsk())));
                            }
                        }

                        if (valid_status) {
                            //      amoCheckBox.setChecked(true);
                        } else {
                            amoCheckBox.setChecked(false);
                            amoCheckBox.setEnabled(false);
                            if (isPostOpen_status) {
                                amoCheckBox.setEnabled(true);
                            }
                        }
                    } else if (getArguments().getString(TRADE_ACTION).equalsIgnoreCase("sell") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("2")) {
                        if (getArguments().containsKey("MdPrice")) {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(getArguments().getString("MdPrice"))));
                            }
                        } else {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getBid())));
                            }
                        }

                        if (valid_status) {
                            //   amoCheckBoxOne.setChecked(true);
                        } else {
                            amoCheckBoxOne.setChecked(false);
                            amoCheckBoxOne.setEnabled(false);
                            if (isPostOpen_status) {
                                amoCheckBoxOne.setEnabled(true);
                            }
                        }
                    }
                } else {
                    if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                        price_txt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAsk())));
                    }
                }
            }

        } else {
            last.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLast())));
            change.setText(String.format("%s(%s%%)", String.format("%.2f", Double.parseDouble(quoteResponse.getChange())), String.format("%.2f", Double.parseDouble(quoteResponse.getP_change()))));
            time.setText(quoteResponse.getLtt());
            bid_price.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getBid())));
//            limit_price.setText(String.format("%.2f", Double.parseDouble(quoteResponse.get())));
            ask_price.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));
            if (!getArguments().getBoolean(IS_MODIFY_ORDER)) {
                if (getArguments().containsKey(TRADE_ACTION)) {
                    if (getArguments().getString(TRADE_ACTION).equalsIgnoreCase("buy") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("1")) {
                        if (getArguments().containsKey("MdPrice")) {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(getArguments().getString("MdPrice"))));
                            }
                        } else {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));

                                if (!getArguments().getBoolean("isSquareOff")) {

                                    qty_txt.setText(quoteResponse.getLot());
                                    qty_txt.setSelection(qty_txt.getText().length());
                                }

                            }
                        }
                        if (valid_status) {
                            //   amoCheckBox.setChecked(true);
                        } else {
                            amoCheckBox.setChecked(false);
                            amoCheckBox.setEnabled(false);
                            if (isPostOpen_status) {
                                amoCheckBox.setEnabled(true);
                            }
                        }
                    } else if (getArguments().getString(TRADE_ACTION).equalsIgnoreCase("sell") || getArguments().getString(TRADE_ACTION).equalsIgnoreCase("2")) {
                        if (getArguments().containsKey("MdPrice")) {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(getArguments().getString("MdPrice"))));
                            }
                        } else {
                            if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                                price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getBid())));
                            }
                        }
                        if (valid_status) {
                            // amoCheckBoxOne.setChecked(true);
                        } else {
                            amoCheckBoxOne.setChecked(false);
                            amoCheckBoxOne.setEnabled(false);
                            if (isPostOpen_status) {
                                amoCheckBoxOne.setEnabled(true);
                            }
                        }
                    }
                } else {
                    if (!orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("market") && !orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market")) {
                        price_txt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAsk())));
                    }
                }
            }
        }


        if (Float.parseFloat(quoteResponse.getP_change()) >= 0) {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                change.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }else {
                change.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }

            imgChange.setImageResource(R.drawable.ic_up);
        } else {
            change.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
            imgChange.setImageResource(R.drawable.ic_done_white);
        }
    }


    private Boolean validateFields() {
        try {
            if (quoteResponse == null) {
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", false, null);
                return false;

            } else if (symName.getText().length() <= 0) {

                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.TD_SYMBOL_EMPTY_MSG), "OK", false, null);
                return false;

            } else if (getArguments() == null) {
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.TD_SYMBOL_EMPTY_MSG), "OK", false, null);
                return false;
            } else if (AppConstants.isEmptyEditText(getMainActivity(), qty_txt, getString(R.string.TD_QTY_EMPTY_MSG))) {
                // GreekDialog.EmptyAlertDialog(getMainActivity(), qty_txt,0, GREEK, getString(R.string.TD_QTY_EMPTY_MSG), "OK", false, null);
                return false;
            } else if (Integer.parseInt(qty_txt.getText().toString()) <= 0) {

                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_MIN_QTY_MSG), "OK", false, null);
                return false;
            } else if (action.equals("Buy") || action.equals("Sell")) {


                if (!gtd_text.getText().toString().equalsIgnoreCase("")) {

                    SimpleDateFormat expirydateformat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    String currentDate = expirydateformat.format(c.getTime());
                    Date date1 = expirydateformat.parse(gtd_text.getText().toString());
                    Date date2 = expirydateformat.parse(currentDate);

                    if ((quoteResponse.getExch().equalsIgnoreCase("nse")
                            || quoteResponse.getExch().equalsIgnoreCase("bse")
                            || quoteResponse.getExch().equalsIgnoreCase("mcx"))) {

                        if ((date1.compareTo(date2) != 0) && !(date1.compareTo(date2) > 0)) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "GTD must be greater than 1 day", "Ok", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();
                                    }
                                }
                            });
                            return false;
                        } else if ((date1.compareTo(date2) == 0)) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "GTD must be greater than 1 day", "Ok", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();

                                    }
                                }
                            });
                            return false;
                        }
                    }
                } else if (orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("gtd")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please select the GTD expiry date", "Ok", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            if (action == Action.OK) {
                                gtd_text.setText("");
                                gtd_text.requestFocus();

                            }
                        }
                    });
                    return false;
                }


                if (price_txt.isEnabled() && price_txt.getText().toString().length() <= 0 && (orderTypeSpinner.getSelectedItemPosition() == 0 || orderTypeSpinner.getSelectedItemPosition() == 2)) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.TD_PRICE_EMPTY_MSG), "OK", false, null);
                    return false;

                } else if ((orderTypeSpinner.getSelectedItemPosition() == 2 || orderTypeSpinner.getSelectedItemPosition() == 3 || orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && AppConstants.isEmptyEditText(getMainActivity(), slTrigPrice_txt, getString(R.string.TD_TRIG_PRICE_EMPTY_MSG))) {
                    return false;
                } else if ((orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && AppConstants.isEmptyEditText(getMainActivity(), slprice_txt, getString(R.string.TD_STOPLOSS_PRICE_EMPTY_MSG))) {
                    return false;
                } else if ((orderTypeSpinner.getSelectedItemPosition() == 4) && AppConstants.isEmptyEditText(getMainActivity(), targetprice_txt, getString(R.string.TD_TARGET_PRICE_EMPTY_MSG))) {
                    return false;
                } else if ((orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && AppConstants.isEmptyEditText(getMainActivity(), price_txt, "Please enter the Price")) {
                    return false;
                } else if (price_txt.isEnabled() && price_txt.getText().toString().length() > 0 && ".".equals(price_txt.getText().toString())) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Invalid Price", "OK", false, null);
                    return false;
                } else if (slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0 && ".".equals(slTrigPrice_txt.getText().toString())) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Invalid Trigger Price", "OK", false, null);
                    return false;
                } else if (price_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString().trim()) <= 0.0) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Price should be positive number", "OK", false, null);
                    return false;
                } else if ((orderTypeSpinner.getSelectedItemPosition() == 4 || orderTypeSpinner.getSelectedItemPosition() == 5) && price_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString().trim()) == 0) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Price should be positive number", "OK", false, null);
                    return false;
                } else if (slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0 && Double.parseDouble(slTrigPrice_txt.getText().toString().trim()) <= 0) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Trigger Price should be positive number", "OK", false, null);
                    return false;
                } else if (targetprice_txt.isEnabled() && targetprice_txt.getText().toString().length() > 0 && Double.parseDouble(targetprice_txt.getText().toString().trim()) <= 0) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Target Price should be positive number", "OK", false, null);
                    return false;
                }


                if (qty_txt.getText().length() > 0) {

                    if (getArguments().getBoolean("isSquareOff")) {

                        if (Math.abs(Integer.parseInt(getArguments().getString("NetQty"))) < Integer.parseInt(qty_txt.getText().toString())) {

                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_SQROFF_QTY_MSG), "OK", false, null);
                            return false;
                        }
                    }

                    if (!quoteResponse.getAsset_type().equalsIgnoreCase("commodity")) {

                        if (!lotQty.equalsIgnoreCase("") || lotQty != null) {
                            int qtyLot = Integer.parseInt(qty_txt.getText().toString());
                            int mod = Integer.parseInt(lotQty);
                            if (qtyLot % mod != 0) {
                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Invalid Qty", "OK", false, null);
                                return false;
                            }
                        }
                    }
                }
                if (tickSize != null && multiplier != null) {
                    double tick = Double.parseDouble(tickSize);
                    double multi = Double.parseDouble(multiplier);
                    double price = 0;
                    if (price_txt.getText().toString().length() > 0)
                        price = Double.parseDouble(price_txt.getText().toString());


                    BigDecimal bigPrice = new BigDecimal(price);
                    BigDecimal mult1 = bigPrice.multiply(new BigDecimal(multi));


                    BigDecimal bigTick = new BigDecimal(tick);
                    BigDecimal mult2 = bigTick.multiply(new BigDecimal(multi));

                    double tmpprice = 0, tmptiksize = 0, rem = 0;

                    if ("fno".equalsIgnoreCase(assetType)) {
                        tmpprice = price * MAX_MULTIPLIER;
                        tmptiksize = tick * MAX_MULTIPLIER;
                        rem = tmpprice % tmptiksize;
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

                    }


                    if (price_txt.getText().toString().length() > 0 && (rem != 0)) {
                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Entered limit Price not in a multiple of Ticksize", "OK", false, null);
                        return false;
                    }
                    if (slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0) {
                        double stkPrice = 0;
                        stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());
                        double tmpprice1 = 0, tmptiksize1 = 0, rem1 = 0;

                        if ("fno".equalsIgnoreCase(assetType)) {
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

                        if (slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0 && (rem != 0)) {
                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Entered trigger Price not in a multiple of Ticksize", "OK", false, null);
                            return false;
                        }
                    }
                }

                if (action.equals("Buy")) {
                    double price;
                    double stkPrice;
                    double slPrice;
                    if (price_txt.getText().toString().length() > 0 && slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString()) != 0) {

                        price = Double.parseDouble(price_txt.getText().toString());
                        stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());

                        if (stkPrice > price) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Trigger Price must be less than Order Price for Buy Order", "OK", false, null);
                            return false;
                        }
                    }
                    if (slprice_txt.getVisibility() == View.VISIBLE) {
                        if (slprice_txt.getText().toString().length() > 0 && price_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString()) != 0) {
                            price = Double.parseDouble(price_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if (slPrice > price) {
                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Stoploss Price must be less than Order Price for Buy Order", "OK", false, null);
                                return false;

                            }
                        }
                        if (slprice_txt.getText().toString().length() > 0 && slTrigPrice_txt.getText().toString().length() > 0) {
                            stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if (slPrice > stkPrice) {
                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Trigger Price must be more than StopLoss Price for Buy Order", "OK", false, null);
                                return false;
                            }
                        }
                    }
                } else if (action.equals("Sell")) {
                    double price;
                    double stkPrice;
                    double slPrice;
                    if (price_txt.getText().toString().length() > 0 && slTrigPrice_txt.isEnabled() && slTrigPrice_txt.getText().toString().length() > 0) {

                        price = Double.parseDouble(price_txt.getText().toString());
                        stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());

                        if (price > stkPrice) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Trigger Price must be more than Order Price for Sell Order", "OK", false, null);
                            return false;
                        }
                    }
                    if (slprice_txt.getVisibility() == View.VISIBLE) {
                        if (slprice_txt.getText().toString().length() > 0 && price_txt.getText().toString().length() > 0 && Double.parseDouble(price_txt.getText().toString()) != 0 && Double.parseDouble(slprice_txt.getText().toString()) != 0) {
                            price = Double.parseDouble(price_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if (slPrice < price) {
                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Stoploss Price must be more than Order Price for Sell Order", "OK", false, null);
                                return false;

                            }
                        }
                        if (slprice_txt.getText().toString().length() > 0 && slTrigPrice_txt.getText().toString().length() > 0 && Double.parseDouble(slprice_txt.getText().toString()) != 0) {
                            stkPrice = Double.parseDouble(slTrigPrice_txt.getText().toString());
                            slPrice = Double.parseDouble(slprice_txt.getText().toString());

                            if (slPrice < stkPrice) {
                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "StopLoss Price must be more than Trigger Price for Sell Order", "OK", false, null);
                                return false;

                            }
                        }
                    }
                }

                if (isPreOpen_status && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stop loss") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market"))) {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Only RL order is allowed in Preopen Session", "OK", false, null);
                    return false;
                }

                if (isPreOpen_status && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "IOC order is not allowed in Preopen Session", "OK", false, null);
                    return false;
                }


                if (valid_status) {
                    if (action.equalsIgnoreCase("buy")) {

                        if (amoCheck == true && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Cover order is not allowed in Offline Session", "OK", false, null);

                            return false;
                        }

                        if (amoCheck == false && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stop loss") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market"))) {

                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Only RL order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }

                        if (amoCheck == false && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {

                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "IOC order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }
                    } else if (action.equalsIgnoreCase("sell")) {

                        if (amoCheckOne == true && orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("cover")) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Cover order is not allowed in Offline Session", "OK", false, null);

                            return false;
                        }

                        if (amoCheckOne == false && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && (orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss") || orderTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stoploss market"))) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Only RL order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }

                        if (amoCheckOne == false && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && orderLifeSpinner.getSelectedItem().toString().equalsIgnoreCase("ioc")) {

                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "IOC order is not allowed in Offline Session", "OK", false, null);
                            return false;
                        }
                    }
                }
            }

            if (discQty_txt.getText().toString().length() > 0) {

                if (Integer.parseInt(discQty_txt.getText().toString().trim()) <= 0) {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Disclosed Quantity should be positive number", "OK", false, null);
                    return false;
                } else if (!exchangeName.equalsIgnoreCase("mcx")) {
                    if (Integer.parseInt(discQty_txt.getText().toString()) > 0) {
                        double i = Double.parseDouble(qty_txt.getText().toString());
                        boolean result1 = (i / 10.0) > (Double.parseDouble(discQty_txt.getText().toString()));
                        boolean result2 = Double.toString((i / 10.0d)).equalsIgnoreCase(Double.toString(Double.parseDouble(discQty_txt.getText().toString())));

                        if (result1 && !result2) {

                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Disclosed quantity is less than 10% of order quantity", "Ok", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();
                                    }
                                }
                            });
                            return false;

                        }
                        if (Integer.parseInt(qty_txt.getText().toString()) < Integer.parseInt(discQty_txt.getText().toString())) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Disclosed quantity should not be greater than order quantity", "Ok", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();

                                    }
                                }
                            });
                            return false;
                        }
                    }
                } else if (exchangeName.equalsIgnoreCase("mcx")) {
                    double i = Double.parseDouble(qty_txt.getText().toString());
                    double i1 = Double.parseDouble(discQty_txt.getText().toString());

                    double min = (i * 25) / 100;

                    if (i1 < min) {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Disclosed quantity is less than 25% of order quantity", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                    discQty_txt.setText("");
                                    discQty_txt.requestFocus();
                                }
                            }
                        });
                        return false;

                    }

                    if (Integer.parseInt(qty_txt.getText().toString()) < Integer.parseInt(discQty_txt.getText().toString())) {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Disclosed quantity should not be greater than order quantity", "Ok", false, new DialogListener() {

                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action == Action.OK) {
                                    discQty_txt.setText("");
                                    discQty_txt.requestFocus();

                                }
                            }
                        });
                        return false;
                    }
                }
            }
            if (gtd_text.getVisibility() == View.VISIBLE) {
                if (!gtd_text.getText().toString().equalsIgnoreCase("")) {
                    SimpleDateFormat expirydateformat = new SimpleDateFormat("dd/MM/yyyy");
                    String formatteddate = DateTimeFormatter.getDateFromTimeStamp(expiry, "dd/MM/yyyy", "bse");
                    Date date1 = expirydateformat.parse(gtd_text.getText().toString());
                    Date date2 = expirydateformat.parse(formatteddate);

                    String token_date = DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd/MM/yyyy", "bse");
                    Date tknExpry_date = expirydateformat.parse(token_date);

                    if (!quoteResponse.getAsset_type().toString().equalsIgnoreCase("equity")) {

                        if ((date1.compareTo(tknExpry_date) == 0) && !(date1.compareTo(tknExpry_date) < 0)) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "GTD Expiry date cannot be Same Or greater than Token Expiry Date", "Ok", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
                                        discQty_txt.setText("");
                                        discQty_txt.requestFocus();

                                    }
                                }
                            });
                            return false;
                        } else if (((date1.compareTo(tknExpry_date) == 1))) {
                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "GTD Expiry date cannot be greater than Token Expiry Date", "Ok", false, new DialogListener() {

                                @Override
                                public void alertDialogAction(Action action, Object... data) {
                                    if (action == Action.OK) {
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
        if (validateFields()) {
            navigate();
        }
    }

    private void validateorderQty() {
        if (validateFields()) {
            validateAndSend();
        }
    }


    private boolean validateIntradayTimer() {
        if (quoteResponse.getIntradayTimerSet() && prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("intraday")) {
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Selected Exchange timer triggered.Orders cannot be placed in INTRADAY product", "Ok", false, null);
            return false;
        }
        return true;
    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            hideProgress();

            try {
                quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                setValueFiled();
                if (!quoteResponse.getIsError()) {
                    setQuoteDetails(quoteResponse);
                    streamingSymbol = quoteResponse.getToken();
                    sym.clear();
                    sym.add(quoteResponse.getToken());
                    streamController.sendStreamingRequest(getMainActivity(), sym, "touchline", null, null, false);
                    addToStreamingList("touchline", sym);
                    expiry = quoteResponse.getExpiryDate();
                    qty_txt.requestFocus();

                    if (quoteResponse.getExch().equalsIgnoreCase("MCX")) {
                        if (quoteResponse.getOptiontype().equalsIgnoreCase("XX")) {
                            symName.setText(quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + quoteResponse.getInstrument());
                        } else {
                            symName.setText(quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + "-" + quoteResponse.getInstrument());
                        }


                    } else {
                        symName.setText(quoteResponse.getDescription() + "-" + quoteResponse.getInstrument());

                    }

                    // symName.setText(quoteResponse.getDescription() + " - " + quoteResponse.getInstrument());
                    if (!getArguments().getBoolean(IS_MODIFY_ORDER) && !getArguments().getBoolean("isSquareOff")) {
                        if (assetType != null && (assetType.equalsIgnoreCase("equity")
                                || assetType.equalsIgnoreCase("fno") ||
                                assetType.equalsIgnoreCase("commodity") ||
                                assetType.equalsIgnoreCase("currency"))) {
                            //qty_txt.setText("1");

                            if (quoteResponse.getExch().equalsIgnoreCase("mcx") || quoteResponse.getExch().equalsIgnoreCase("ncdex")) {
                                qty_txt.setText("1");
                                qty_txt.setSelection(qty_txt.getText().length());
                            } else {
                                qty_txt.setText(quoteResponse.getLot());
                                qty_txt.setSelection(qty_txt.getText().length());
                            }
                        }
//                        qty_txt.setText(quoteResponse.getLot());
                        lotQty = quoteResponse.getLot();
                    }
                    if (getArguments().getBoolean("isSquareOff")) {
                        if (Math.abs(Integer.parseInt(getArguments().getString("NetQty"))) > Integer.parseInt(quoteResponse.getFreeze_qty())) {
                            qty_txt.setText(String.valueOf(Math.abs(Integer.parseInt(getArguments().getString("NetQty"))) - Integer.parseInt(quoteResponse.getFreeze_qty())));
                            qty_txt.setSelection(qty_txt.getText().length());
                        }

                    }

                    if (quoteResponse.getMTFScript()) {

                        if (AccountDetails.getAllowedProductList().contains("MTF")) {
                            if (!productType.contains("MTF")) {
                                productType.add("MTF");
                            }
                        }
                        /*productType.clear();

                        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                            if (assetType.equalsIgnoreCase("equity") && (AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") || AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("MTF"))) {
                                productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                            } else if (!AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") && !AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("MTF")) {
                                productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                            }
                        }*/

                        prodTypeSpAdapter.notifyDataSetChanged();
                    } else {
                        if (productType.contains("MTF")) {
                            productType.remove("MTF");
                            prodTypeSpAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    symName.setText(quoteResponse.getMessage());
                    quoteResponse = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && PREQUOTEDETAILS_SVC_NAME.equals(jsonResponse.getServiceName())) {

            Log.e("TradeFragment", "==============>>>" + jsonResponse.toString());
            JSONObject jsonObject = jsonResponse.getResponseData();
            try {
                JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                String HQty = jsonObjectData.getString("NQty");
                if (HQty != null && !HQty.isEmpty()) {
                    holding_txt.setText(HQty);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equalsIgnoreCase("touchline")) {
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

            if (streamingSymbol.equals(response.getSymbol())) {

                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    last.setText(String.format("%.4f", Double.parseDouble(response.getLast())));
                    change.setText(String.format("%s(%s%%)", String.format("%.4f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
                    bid_price.setText(String.format("%.4f", Double.parseDouble(response.getBid())));
                    ask_price.setText(String.format("%.4f", Double.parseDouble(response.getAsk())));
                } else {
                    last.setText(String.format("%.2f", Double.parseDouble(response.getLast())));
                    change.setText(String.format("%s(%s%%)", String.format("%.2f", Double.parseDouble(response.getChange())), String.format("%.2f", Double.parseDouble(response.getP_change()))));
                    bid_price.setText(String.format("%.2f", Double.parseDouble(response.getBid())));
                    ask_price.setText(String.format("%.2f", Double.parseDouble(response.getAsk())));
                }

                if (!response.getLtt().equalsIgnoreCase("")) {
                    time.setText(response.getLtt());
                }

                if (Float.parseFloat(quoteResponse.getP_change()) >= 0) {
                    if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        change.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                    }else {
                        change.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                    }

                    imgChange.setImageResource(R.drawable.ic_up);
                } else {
                    change.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    imgChange.setImageResource(R.drawable.ic_watchlist);
                }
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName()))
            quoteResponse = null;
        super.infoDialog(action, msg, jsonResponse);
    }

    @Override
    public void onFragmentResult(Object data) {
        if (data != null) {
            getArguments().putAll((Bundle) data);
            AccountDetails.currentFragment = NAV_TO_TRADE_SCREEN;

            if (((Bundle) data).getString("GoTO") != null) {

                if (!orderTypeSpinner.getSelectedItem().toString().equals("Market")) {
                    price_txt.setText(((Bundle) data).getString("Price"));
                    //price_txt.requestFocus();
                    qty_txt.setText(((Bundle) data).getString("Qty"));
                    qty_txt.setSelection(qty_txt.getText().length());
                }

                if (data != null && ((Bundle) data).containsKey("GoTO") && ((Bundle) data).getString("GoTO").equalsIgnoreCase("trade")) {
                    if (!orderTypeSpinner.getSelectedItem().toString().equals("Market")) {
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

                if (assetType != null && assetType.equalsIgnoreCase("currency")) {
                    slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 4)});
                    targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 4)});
                    slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 4)});
                } else {
                    slTrigPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slTrigPrice_txt, 13, 2)});
                    targetprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(targetprice_txt, 13, 2)});
                    slprice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slprice_txt, 13, 2)});
                }
                slLimitPrice_txt = tradeView.findViewById(R.id.limit_price_text);
                if (assetType != null && assetType.equalsIgnoreCase("currency"))
                    slLimitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slLimitPrice_txt, 13, 4)});
                else
                    slLimitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(slLimitPrice_txt, 13, 2)});

                profitPrice_txt = tradeView.findViewById(R.id.profit_price_text);
                if (assetType != null && assetType.equalsIgnoreCase("currency"))
                    profitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(profitPrice_txt, 13, 4)});
                else
                    profitPrice_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(profitPrice_txt, 13, 2)});
                price_txt = tradeView.findViewById(R.id.price_text);
                if (assetType != null && assetType.equalsIgnoreCase("currency"))
                    price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 4)});
                else
                    price_txt.setFilters(new InputFilter[]{new InputFilterBeforeAfterDecimalDigits(price_txt, 13, 2)});

                if (assetType != null && (!assetType.equalsIgnoreCase("equity") && !assetType.equalsIgnoreCase("commodity"))) {
                    qty_label.setText("Quantity");

                    if (lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    }

                }

                if (assetType != null && (assetType.equalsIgnoreCase("equity") || assetType.equalsIgnoreCase("commodity") || assetType.equalsIgnoreCase("currency"))) {
                    discQty_txt.setEnabled(true);

                    if (lotQty != null && !lotQty.equals("")) {
                        qty_txt.setText(lotQty);
                        qty_txt.setSelection(qty_txt.getText().length());
                    } else {

                        qty_txt.setText("1");
                        qty_txt.setSelection(qty_txt.getText().length());
                    }


                } else qty_label.setText("Quantity");

                productType.clear();

                for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                    if (assetType.equalsIgnoreCase("equity") && (AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") || AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("MTF"))) {
                        productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                    } else if (!AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("sseq") && !AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase("MTF")) {
                        productType.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                    }
                }

                prodTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), productType);
                prodTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);
                prodTypeSpinner.setAdapter(prodTypeSpAdapter);
                if (selectedAssetType != 0 && AccountDetails.getProductTypeFlag().equalsIgnoreCase("2")) {

                } else {
                    for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                        if (AccountDetails.getProductTypeFlag().equalsIgnoreCase(AccountDetails.getAllowedProduct().get(i).getiProductToken())) {
                            prodTypeSpinner.setSelection(i);
                            break;
                        }

                    }
                }


                if (assetType.equalsIgnoreCase("currency") || assetType.equalsIgnoreCase("Commodity")) {

                    if (assetType.equalsIgnoreCase("Commodity")) {
                        selectedAssetType = 3;

                        orderTypeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderTypeForCommodity());
                        orderTypeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                        orderTypeSpinner.setAdapter(orderTypeSpAdapter);
                        orderTypeSpinner.setOnItemSelectedListener(orderTypeSelectedListener);

                        orderLifeSpAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), getOrderLifeForCommodity());
                        orderLifeSpAdapter.setDropDownViewResource(R.layout.custom_spinner);

                        orderLifeSpinner.setAdapter(orderLifeSpAdapter);
                        orderLifeSpinner.setOnItemSelectedListener(orderLifeSelectionListener);


                    }

                }
            }
        } else {
            Bundle args = getArguments();
            if (args != null && args.containsKey("GoTO") && args.getString("GoTO").equalsIgnoreCase("trade")) {
                if (!orderTypeSpinner.getSelectedItem().toString().equals("Market")) {
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
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_TRADE_SCREEN;
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(1, 0);
        if (token != null && assetType != null) {
            if (!token.equalsIgnoreCase("") && !assetType.equalsIgnoreCase("")) {
                sendQuotesRequest(token, assetType);
            }
        }
        //setupAdapters();
        EventBus.getDefault().register(this);
        streamController = new StreamingController();
        streamController.sendStreamingRequest(getMainActivity(), sym, "touchline", null, null, false);
        addToStreamingList("touchline", sym);
        setAppTitle(this.getClass().toString(), title);

    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        qty_txt.clearFocus();
//        qty_txt.requestFocus();
//    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        if (streamController != null) {
            if (sym != null && sym.size() > 0) {
                streamController.pauseStreaming(getMainActivity(), "touchline", sym);
            }
        }
        super.onFragmentPause();
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
                SharedPreferences sharedPreferences = getMainActivity().getSharedPreferences("MarketDepth", getMainActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_NO_QUOTE_DATA_MSG), "Ok", true, null);
            }
        } else if (id == R.id.symbSearchBtn1) {
            Bundle args = new Bundle();
            args.putString("Source", "Trade");
            navigateTo(NAV_TO_SYMBOL_SEARCH_SCREEN, args, true);
        } else if (id == R.id.place_Order_btn) {
            if (quoteResponse != null) {
                validateAllowedMarket();
                if (allowed_market_bool) {
                    if (quoteResponse.getRestrictFreshOrder()) {
                        if (quoteResponse.getAllowed()) {

                            if (getArguments().getBoolean(IS_MODIFY_ORDER)) {

                                if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                    if (validateIntradayTimer()) {

                                        validateorderQty();

                                        InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        if (inputManager.isAcceptingText())
                                            inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    }

                                } else {
                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                }

                            } else {

                                if (quoteResponse.getSqOff()) {
                                    if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {


                                        if (AppConstants.isEmptyEditText(getMainActivity(), qty_txt, getString(R.string.TD_QTY_EMPTY_MSG))) {
                                            qty_txt.setFocusable(true);
                                        } else if (Integer.parseInt(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                            if (validateIntradayTimer()) {
                                                validateorderQty();

                                                InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                if (inputManager.isAcceptingText())
                                                    inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                            }
                                        } else {
                                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                        }
                                    } else {
                                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                    }
                                } else {
                                    orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG) + AccountDetails.getUsername(getMainActivity()), getRejectionType("restrict_fresh"));
                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG) + AccountDetails.getUsername(getMainActivity()), "Ok", true, null);
                                }
                            }
                        } else {
                            if (getArguments().getBoolean(IS_MODIFY_ORDER)) {

                                if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                    if (validateIntradayTimer()) {
                                        validateorderQty();

                                        InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        if (inputManager.isAcceptingText())
                                            inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                    }
                                } else {
                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                }

                            } else {

                                if (quoteResponse.getSqOff()) {
                                    if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                        if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                            if (validateIntradayTimer()) {
                                                validateorderQty();

                                                InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                if (inputManager.isAcceptingText())
                                                    inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                            }

                                        } else {
                                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                        }
                                    } else {
                                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                    }
                                } else {
                                    String errorMessage = PrepareRejectionMessage(quoteResponse.getAsset_type(), quoteResponse.getExch(), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                    orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                }
                            }
                        }
                    } else {

                        if (quoteResponse.getAsset_type().equalsIgnoreCase("fno") || quoteResponse.getAsset_type().equalsIgnoreCase("2") || quoteResponse.getAsset_type().equalsIgnoreCase("future")) {
                            if (!quoteResponse.getAllowedIntraday() && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("intraday"))) {
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Trading not allowed under Intraday Product for user:" + AccountDetails.getUsername(getMainActivity()), "Ok", true, null);
                            } else if (!quoteResponse.getAllowedDelivery() && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("delivery"))) {
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Trading not allowed under Delivery Product for user:" + AccountDetails.getUsername(getMainActivity()), "Ok", true, null);
                            } else {
                                if (quoteResponse.getAllowedFO()) {
                                    if (quoteResponse.getAllowed()) {
                                        //place order
                                        if (validateIntradayTimer()) {
                                            validateorderQty();

                                            InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if (inputManager.isAcceptingText())
                                                inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                        }
                                    } else {
                                        if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                            if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                                if (validateIntradayTimer()) {
                                                    validateorderQty();

                                                    InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    if (inputManager.isAcceptingText())
                                                        inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                                }
                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                            }
                                        } else {
                                            if (quoteResponse.getSqOff()) {
                                                if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                    if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                                        if (validateIntradayTimer()) {
                                                            validateorderQty();

                                                            InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                            if (inputManager.isAcceptingText())
                                                                inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                                        }
                                                    } else {
                                                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                                }
                                            } else {
                                                String errorMessage = PrepareRejectionMessage(quoteResponse.getAsset_type(), quoteResponse.getExch(), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                                orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                            }
                                        }
                                    }
                                } else {
                                    if (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("intraday") && quoteResponse.getAllowed()) {

                                        if (validateIntradayTimer()) {

                                            validateorderQty();

                                            InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if (inputManager.isAcceptingText())
                                                inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                                        }
                                    } else if (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("intraday") && !quoteResponse.getAllowed()) {
                                        if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                            if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {

                                                if (validateIntradayTimer()) {
                                                    validateorderQty();

                                                    InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    if (inputManager.isAcceptingText())
                                                        inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                                }
                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                            }

                                        } else {
                                            if (quoteResponse.getSqOff()) {
                                                if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                    if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {

                                                        if (validateIntradayTimer()) {
                                                            validateorderQty();

                                                            InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                            if (inputManager.isAcceptingText())
                                                                inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                                        }

                                                    } else {
                                                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                                }
                                            } else {
                                                String errorMessage = PrepareRejectionMessage(quoteResponse.getAsset_type(), quoteResponse.getExch(), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                                orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                            }
                                        }
                                    } else {
                                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREE_FO_SCRIPT_BANNED_MSG), "Ok", true, null);

                                        orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), getString(R.string.GREE_FO_SCRIPT_BANNED_MSG), getRejectionType("fo_banned"));
                                    }
                                }
                            }

                        } else if (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1") || quoteResponse.getAsset_type().equalsIgnoreCase("cash")) {
                            if (!quoteResponse.getAllowedIntraday() && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("intraday"))) {

                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Trading not allowed under Intraday Product for user:" + AccountDetails.getUsername(getMainActivity()), "Ok", true, null);
                            } else if (!quoteResponse.getAllowedDelivery() && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("delivery"))) {
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Trading not allowed under Delivery Product for user:" + AccountDetails.getUsername(getMainActivity()), "Ok", true, null);
                            } else {
                                if (validateShortSell()) {
                                    if (quoteResponse.getAllowed()) {
                                        //place order

                                        if (validateIntradayTimer()) {
                                            validateorderQty();

                                            InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if (inputManager.isAcceptingText())
                                                inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                        }
                                    } else {
                                        if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                            if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {
                                                if (validateIntradayTimer()) {
                                                    validateorderQty();

                                                    InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    if (inputManager.isAcceptingText())
                                                        inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                                }
                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                            }

                                        } else {
                                            if (quoteResponse.getSqOff()) {
                                                if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                    if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {
                                                        if (validateIntradayTimer()) {
                                                            validateorderQty();

                                                            InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                            if (inputManager.isAcceptingText())
                                                                inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                                        }
                                                    } else {
                                                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                                }
                                            } else {
                                                //orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG));
                                                //GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG), "Ok", true, null);
                                                String errorMessage = PrepareRejectionMessage(quoteResponse.getAsset_type(), quoteResponse.getExch(), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                                orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                            }
                                        }
                                    }
                                }
                            }

                        } else {
                            if (!quoteResponse.getAllowedIntraday() && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("intraday"))) {
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Trading not allowed under Intraday Product for user:" + AccountDetails.getUsername(getMainActivity()), "Ok", true, null);
                            } else if (!quoteResponse.getAllowedDelivery() && (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("delivery"))) {
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Trading not allowed under Delivery Product for user:" + AccountDetails.getUsername(getMainActivity()), "Ok", true, null);
                            } else {
                                if (quoteResponse.getAllowed()) {
                                    //place order
                                    if (validateIntradayTimer()) {

                                        validateorderQty();

                                        InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        if (inputManager.isAcceptingText())
                                            inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                    }
                                } else {
                                    if (getArguments().getBoolean(IS_MODIFY_ORDER)) {
                                        if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))) {
                                            if (validateIntradayTimer()) {
                                                validateorderQty();

                                                InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                if (inputManager.isAcceptingText())
                                                    inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                            }
                                        } else {
                                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(getArguments().getString("PendingQty")))), "Ok", true, null);
                                        }

                                    } else {
                                        if (quoteResponse.getSqOff()) {
                                            if (Integer.valueOf(quoteResponse.getSqOffQty()) < 0 && buyBtn.isChecked() || Integer.valueOf(quoteResponse.getSqOffQty()) > 0 && sellBtn.isChecked()) {
                                                if (Integer.valueOf(qty_txt.getText().toString()) <= Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))) {
                                                    if (validateIntradayTimer()) {
                                                        validateorderQty();

                                                        InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                        if (inputManager.isAcceptingText())
                                                            inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                                    }
                                                } else {
                                                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Quantity exceeded by " + (Integer.valueOf(qty_txt.getText().toString()) - Math.abs(Integer.valueOf(quoteResponse.getSqOffQty()))), "Ok", true, null);
                                                }
                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "You are not allowed to " + (buyBtn.isChecked() ? "buy" : "sell"), "Ok", true, null);
                                            }
                                        } else {
                                            //orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG));
                                            //GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREEK_NOT_ALLOWED_FRESH_ORDER_MSG), "Ok", true, null);
                                            String errorMessage = PrepareRejectionMessage(quoteResponse.getAsset_type(), quoteResponse.getExch(), quoteResponse.getInstrument(), DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "dd MMM yyyy", "bse"), quoteResponse.getStrikeprice(), quoteResponse.getOptiontype(), quoteResponse.getSymbol());
                                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, errorMessage, "Ok", true, null);
                                            orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), errorMessage, getRejectionType("script_banned"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.MARKET_NOT_ALLOWED), "Ok", true, null);
                }
            } else
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREE_NO_SCRIPT_MSG), "Ok", true, null);
        } else if (id == R.id.gtdButton) {//showDialog(v);
        }
    }


    private String getRejectionType(String rejectionType) {
        if (rejectionType.equalsIgnoreCase("script_banned")) {
            return "0";
        } else if (rejectionType.equalsIgnoreCase("fo_banned")) {
            return "1";
        } else if (rejectionType.equalsIgnoreCase("restrict_fresh")) {
            return "2";
        } else if (rejectionType.equalsIgnoreCase("t2t_banned")) {
            return "3";
        } else if (rejectionType.equalsIgnoreCase("shortsell_not_allowed")) {
            return "4";
        }

        return "";
    }

    private String PrepareRejectionMessage(String assetType, String exchange, String instrument, String expiry, String strike, String opType, String symbol) {
        String msg = "";
        switch (assetType.toLowerCase()) {
            case "equity": {
                msg = "Script Banned " + exchange + " EQ " + " " + symbol + " @ " + AccountDetails.getUsername(getMainActivity()) + "(RETAILER)";
            }
            break;
            case "fno": {
                msg = "Script Banned " + exchange + " DERV " + " " + symbol + " " + instrument + " " + expiry + " " + opType + " " + strike + " @ " + AccountDetails.getUsername(getMainActivity()) + "(RETAILER)";
            }
            break;
            case "currency": {
                msg = "Script Banned " + exchange + " CURR " + " " + symbol + " " + instrument + " " + expiry + " " + opType + " " + strike + " @ " + AccountDetails.getUsername(getMainActivity()) + "(RETAILER)";
            }
            break;
            case "commodity": {
                msg = "Script Banned " + exchange + " COMM " + " " + symbol + " " + instrument + " " + expiry + " " + opType + " " + strike + " @ " + AccountDetails.getUsername(getMainActivity()) + "(RETAILER)";
            }
            break;
        }

        return msg;
    }

    private void validateAllowedMarket() {

        if (quoteResponse.getExch().equalsIgnoreCase("nse") && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("1")) && AccountDetails.allowedmarket_nse) {
            //Toast.makeText(getMainActivity(),"Nse"+String.valueOf(AccountDetails.allowedmarket_nse),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("nse") && (quoteResponse.getAsset_type().equalsIgnoreCase("fno") || quoteResponse.getAsset_type().equalsIgnoreCase("2") || quoteResponse.getAsset_type().equalsIgnoreCase("future")) && AccountDetails.allowedmarket_nfo) {
            //Toast.makeText(getMainActivity(),"nfo"+String.valueOf(AccountDetails.allowedmarket_nfo),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("nse") && (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("3")) && AccountDetails.allowedmarket_ncd) {
            //Toast.makeText(getMainActivity(),"ncd"+String.valueOf(AccountDetails.allowedmarket_ncd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && (quoteResponse.getAsset_type().equalsIgnoreCase("equity") || quoteResponse.getAsset_type().equalsIgnoreCase("4")) && AccountDetails.allowedmarket_bse) {
            //Toast.makeText(getMainActivity(),"bse"+String.valueOf(AccountDetails.allowedmarket_bse),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && (quoteResponse.getAsset_type().equalsIgnoreCase("fno") || quoteResponse.getAsset_type().equalsIgnoreCase("5") || quoteResponse.getAsset_type().equalsIgnoreCase("future")) && AccountDetails.allowedmarket_bfo) {
            //Toast.makeText(getMainActivity(),"bfo"+String.valueOf(AccountDetails.allowedmarket_bfo),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && (quoteResponse.getAsset_type().equalsIgnoreCase("currency") || quoteResponse.getAsset_type().equalsIgnoreCase("6")) && AccountDetails.allowedmarket_bcd) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("mcx") && (quoteResponse.getAsset_type().equalsIgnoreCase("commodity") || quoteResponse.getAsset_type().equalsIgnoreCase("9")) && AccountDetails.allowedmarket_mcx) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
        } else if (quoteResponse.getExch().equalsIgnoreCase("ncdex") && (quoteResponse.getAsset_type().equalsIgnoreCase("commodity") || quoteResponse.getAsset_type().equalsIgnoreCase("7")) && AccountDetails.allowedmarket_ncdex) {
            //Toast.makeText(getMainActivity(),"bcd"+String.valueOf(AccountDetails.allowedmarket_bcd),Toast.LENGTH_SHORT).show();
            allowed_market_bool = true;
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


            if (AccountDetails.allowedmarket_nse) {
                //FOR EQUITY
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("1")) {
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = true;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("1")) {

                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                }
            }
            //FOR FNO
            /*  int fo = 0;*/
            if (AccountDetails.allowedmarket_nfo) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = true;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("2")) {


                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("2")) {

                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                }
            }
            //FOR CURRENCY

            /* int cd = 0;*/
            if (AccountDetails.allowedmarket_ncd) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("3")) {


                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = true;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("3")) {

                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                }
            }

            if (AccountDetails.allowedmarket_mcx) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = true;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;
                }

            }

            if (AccountDetails.allowedmarket_bse) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = true;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("4")) {

                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                }
            }

            if (AccountDetails.allowedmarket_bfo) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = true;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("5")) {


                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("5")) {

                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;


                }

            }

            if (AccountDetails.allowedmarket_bcd) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = true;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("6")) {

                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("6")) {


                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;
                }

            }

            if (AccountDetails.allowedmarket_ncdex) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = true;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("7")) {

                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                }
            }


            valid_status = false;
            isPreOpen_status = false;
            isPostOpen_status = false;

            if (quoteResponse != null) {
                if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.nse_eq_status) {
                    valid_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.bse_eq_status) {
                    valid_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.nse_fno_status) {
                    valid_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.bse_fno_status) {
                    valid_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.nse_cd_status) {
                    valid_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.bse_cd_status) {
                    valid_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("mcx") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.mcx_com_status) {
                    valid_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("ncdex") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.ncdex_com_status) {
                    valid_status = true;
                }


                if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPreOpen_nse_eq) {
                    isPreOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPreOpen_bse_eq) {
                    isPreOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPreOpen_nse_fno) {
                    isPreOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPreOpen_bse_fno) {
                    isPreOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPreOpen_nse_cd) {
                    isPreOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPreOpen_bse_cd) {
                    isPreOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("mcx") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPreOpen_mcx_com) {
                    isPreOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("ncdex") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
                    isPreOpen_status = true;
                }

                if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPostClosed_nse_eq) {
                    isPostOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("equity") && AccountDetails.isPostClosed_bse_eq) {
                    isPostOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPostClosed_nse_fno) {
                    isPostOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("fno") && AccountDetails.isPostClosed_bse_fno) {
                    isPostOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("Nse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPostClosed_nse_cd) {
                    isPostOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("bse") && quoteResponse.getAsset_type().equalsIgnoreCase("currency") && AccountDetails.isPostClosed_bse_cd) {
                    isPostOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("mcx") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_mcx_com) {
                    isPostOpen_status = true;
                } else if (quoteResponse.getExch().equalsIgnoreCase("ncdex") && quoteResponse.getAsset_type().equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
                    isPostOpen_status = true;
                }

                if (valid_status) {
                    amoCheckBox.setEnabled(true);
                    amoCheckBoxOne.setEnabled(true);
                } else {
                    amoCheckBox.setChecked(false);
                    amoCheckBoxOne.setChecked(false);
                    amoCheckBox.setEnabled(false);
                    amoCheckBoxOne.setEnabled(false);
                    if (isPostOpen_status) {
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
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, response.getMessage(), "OK", false, new GreekDialog.DialogListener() {
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
        qty_txt.clearFocus();
        qty_txt.requestFocus();
    }

    private Boolean validateShortSell() {
        if (prodTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("intraday")) {
            if (quoteResponse != null) {
                if (quoteResponse.getT2TScript()) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, " T2T Symbols not allowed for Trading under Intraday Product", "Ok", true, null);
                    return false;
                } else {
                    return true;
                }
            }
        }
        if (!sellBtn.isChecked()) {
            return true;
        }
        if (quoteResponse.getT2TScript()) {
            if (quoteResponse.getAllowedT2TSell()) {
                return true;
            }
        } else {
            if (quoteResponse.getAllowedShortSell()) {
                return true;
            }
        }
        int totQty = 0;
        if (quoteResponse.getTodQty().equalsIgnoreCase("") && quoteResponse.getPrevQty().equalsIgnoreCase("")) {
            totQty = Integer.parseInt(quoteResponse.getSqOffQty());
        } else {
            totQty = Integer.parseInt(quoteResponse.getTodQty()) + Integer.parseInt(quoteResponse.getPrevQty());
        }

        if (AppConstants.isEmptyEditText(getMainActivity(), qty_txt, getString(R.string.TD_QTY_EMPTY_MSG))) {
            // GreekDialog.EmptyAlertDialog(getMainActivity(), qty_txt,0, GREEK, getString(R.string.TD_QTY_EMPTY_MSG), "OK", false, null);

            return false;
        } else {
            if (Integer.parseInt(qty_txt.getText().toString()) > totQty) {
                if (quoteResponse.getT2TScript()) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, " T2T Symbols not allowed for Delivery Sell for User : " + AccountDetails.getUsername(getMainActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + String.valueOf(totQty), "Ok", true, null);
                    orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), "T2T Symbols not allowed for Delivery Sell for User :" + AccountDetails.getUsername(getMainActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + String.valueOf(totQty), getRejectionType("t2t_banned"));
                    return false;

                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, " Short Sell not allowed under Delivery Product for User :" + AccountDetails.getUsername(getMainActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + String.valueOf(totQty), "Ok", true, null);
                    orderStreamingController.sendStreamingRmsRejectionRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), quoteResponse.getToken(), " Short Sell not allowed under Delivery Product for User :" + AccountDetails.getUsername(getMainActivity()) + " Ordered Qty:" + qty_txt.getText().toString() + " Order Price :" + price_txt.getText().toString() + " Allowed Qty :" + String.valueOf(totQty), getRejectionType("shortsell_not_allowed"));
                    return false;
                }
            }
        }


        return true;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}

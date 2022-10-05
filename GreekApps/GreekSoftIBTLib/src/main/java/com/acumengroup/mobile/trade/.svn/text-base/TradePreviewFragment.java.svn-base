package com.acumengroup.mobile.trade;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import androidx.core.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.trademodifyorder.TradeModifyOrderRequest;
import com.acumengroup.greekmain.core.model.tradesendneworder.TradeSendNewOrderRequest;
import com.acumengroup.greekmain.core.network.OrderStreamingAuthResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimerTask;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class TradePreviewFragment extends GreekBaseFragment {
    public static final String ASSET_TYPE = "AssetType";
    public static final String TRADE_SYMBOL = "Trade Symbol";
    public static final String EXCHANGE = "Exchange";
    public static final String SYMBOL = "Symbol";
    public static final String LOT_QUANTITY = "LotQty";
    public static final String ACTION = "Action";
    public static final String GTD_EXPIRY = "gtdExpiry";
    public static final String PROFIT_PRICE = "Profit Price";
    public static final String LIMIT_PRICE = "Limit Price";
    public static final String ORDER_TYPE = "Order Type";
    public static final String TRIGGER_PRICE = "Trigger Price";
    public static final String PRODUCT_TYPE = "Product Type";
    public static final String DIS_QUANTITY = "Dis Qty";
    public static final String ORDER_LIFE = "Order Life";
    public static final String QUANTITY = "Qty";
    public static final String PRICE = "Price";
    public static final String VALIDITY = "Validity";
    public static final String TOKEN = "Token";
    public static final String LU_TIME_EXCHANGE = "lu_time_exchange";
    public static final String PENDING_DISCLOSED_QTY = "pending_disclosed_qty";
    public static final String QTY_FILLED_TODAY = "qty_filled_today";
    public static final String IOM_RULE_NO = "iomRuleNo";
    public static final String PENDINGQTY = "PendingQty";
    public static final String GORDERID = "gorderid";
    public static final String EORDERID = "eorderid";
    public static final String LEXCHANGEORDERNO1 = "lexchangeOrderNo1";
    public static final String DESCRIPTION = "description";
    public static final String TARGETPRICE = "target_price";
    public static final String SLPRICE = "sl_price";
    public static final String AMO = "amo";
    public static final String isPreOpen = "isPreOpen";
    public static final String isPostClosed = "isPostClosed";

    public boolean allowed_market_bool = false;
    String orderTime;
    private static Integer orderNumber = 0;
    TimerTask timerTask;
    GreekBaseActivity greekBaseActivity = new GreekBaseActivity();

    /**
     * this will generate a new sequence number and will be used in newOrder
     *
     * @return
     */
    private Integer getNextNumber() {
        return ++orderNumber;
    }


    private final OnClickListener backClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("price", getArguments().getString(PRICE));
            bundle.putString("qty", getArguments().getString("OriginalQty"));
            bundle.putString("GoTO", "Trade");
            if(previousFragment != null)
                previousFragment.onFragmentResult(bundle);
            goBackOnce();


        }
    };
    private View tradePreviewView;
    private GreekButton back, confirm;
    private LinearLayout orderDetails;
    private int selectedAssetType;
    private OrderStreamingController orderStreamingController;

    private final OnClickListener confirmClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Util.updateValidationTime(getMainActivity());
            if("Buy".equals(getArguments().getString(ACTION)) || "Sell".equals(getArguments().getString(ACTION))) {

                if(getArguments().getBoolean("valid_amo_status")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "It will be proceeded as AMO orders.Do you wish to continue?", "Yes", "No", false, new GreekDialog.DialogListener() {


                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if(action.name().equalsIgnoreCase("ok")) {
                                sendTradeRequest();
                            } else if(action.name().equalsIgnoreCase("cancel")) {
                                confirm.setEnabled(true);
                            }
                        }
                    });
                } else {
                    sendTradeRequest();
                }
            }
        }
    };


    private Boolean changeColor = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tradePreviewView = super.onCreateView(inflater, container, savedInstanceState);

        if(AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_tradepreview_traderesults).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_tradepreview_traderesults).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        setupViews();
        setupListeners();

        orderStreamingController = new OrderStreamingController();
        return tradePreviewView;

    }

    private void setupViews() {
        hideProgress();
        GreekTextView symName = tradePreviewView.findViewById(R.id.order_conf_sym);
        symName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        back = tradePreviewView.findViewById(R.id.backBtn);
        confirm = tradePreviewView.findViewById(R.id.confirmBtn);
        confirm.setClickDelay(15000);
        orderDetails = tradePreviewView.findViewById(R.id.order_details);
        String title = "Order Preview";
        setAppTitle(getClass().toString(), title);
        symName.setText(getArguments().getString(SYMBOL));

        Bundle bundle = new Bundle();
        bundle.putString("price", getArguments().getString(PRICE));
        bundle.putString("qty", getArguments().getString("OriginalQty"));
        bundle.putString("GoTO", "Trade");
        if(previousFragment != null)
            previousFragment.onFragmentResult(bundle);

        if(!AccountDetails.isOrderServerAuthenticated()) {

            if(AccountDetails.iris_Counter == 0) {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "We are facing Network congestion.Kindly try after sometime", "Ok", false, null);
                confirm.setVisibility(View.GONE);
                AccountDetails.iris_Counter++;
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "We are facing Network congestion.Kindly relogin", "Ok", false, null);
                confirm.setVisibility(View.GONE);
            }
        }

        if(getArguments().containsKey(ORDER_TYPE)){
            if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Limit")) {
                createPositionsRow("Product Type", ":\t  " + getArguments().getString(PRODUCT_TYPE));

                createPositionsRow("Action", ":\t  " + getArguments().getString(ACTION).toUpperCase());


                if("equity".equals(getArguments().getString(ASSET_TYPE))) {
                    createPositionsRow("Quantity", ":\t  " + getArguments().getString(QUANTITY));
                } else if("currency".equals(getArguments().getString(ASSET_TYPE))) {

                    createPositionsRow("Quantity", ":\t  " + getArguments().getString(QUANTITY));

                } else {
                    createPositionsRow("Qty/Per Lot", ":\t  " + getArguments().getString(LOT_QUANTITY));

                    createPositionsRow("Lots", ":\t  " + (Integer.parseInt(getArguments().getString(QUANTITY))) / (Integer.parseInt(getArguments().getString(LOT_QUANTITY))));

                }

                createPositionsRow("Order Type", ":\t  " + getArguments().getString(ORDER_TYPE));

                if(Double.parseDouble(getArguments().getString(DIS_QUANTITY)) > 0)
                    createPositionsRow("Dis Quantity", ":\t  " + getArguments().getString(DIS_QUANTITY));

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + getArguments().getString(PRICE));
                }

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("Trigger Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(TRIGGER_PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("Trigger Price", ":\t  " + getArguments().getString(TRIGGER_PRICE));
                }

                createPositionsRow("Order Life", ":\t  " + getArguments().getString(ORDER_LIFE));
            } else if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Market")) {
                createPositionsRow("Product Type", ":\t  " + getArguments().getString(PRODUCT_TYPE));

                createPositionsRow("Action", ":\t  " + getArguments().getString(ACTION).toUpperCase());


                if("equity".equals(getArguments().getString(ASSET_TYPE))) {
                    createPositionsRow("Quantity", ":\t  " + getArguments().getString(QUANTITY));
                } else if("currency".equals(getArguments().getString(ASSET_TYPE))) {

                    createPositionsRow("Lots", ":\t  " + getArguments().getString(QUANTITY));

                } else {
                    createPositionsRow("Qty/Per Lot", ":\t  " + getArguments().getString(LOT_QUANTITY));
                    createPositionsRow("Lots", ":\t  " + (Integer.parseInt(getArguments().getString(QUANTITY))) / (Integer.parseInt(getArguments().getString(LOT_QUANTITY))));
                }

                createPositionsRow("Order Type", ":\t  " + getArguments().getString(ORDER_TYPE));

                if(Double.parseDouble(getArguments().getString(DIS_QUANTITY)) > 0)
                    createPositionsRow("Dis Quantity", ":\t  " + getArguments().getString(DIS_QUANTITY));
                else createPositionsRow("Dis Quantity", ":\t  " + "");

                if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                    createPositionsRow("Price", ":\t  " + "Market");

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("Trigger Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(TRIGGER_PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("Trigger Price", ":\t  " + getArguments().getString(TRIGGER_PRICE));
                }

                createPositionsRow("Order Life", ":\t  " + getArguments().getString(ORDER_LIFE));
            } else if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Stop Loss")) {
                createPositionsRow("Product Type", ":\t  " + getArguments().getString(PRODUCT_TYPE));

                createPositionsRow("Action", ":\t  " + getArguments().getString(ACTION).toUpperCase());


                if("equity".equals(getArguments().getString(ASSET_TYPE))) {
                    createPositionsRow("Quantity", ":\t  " + getArguments().getString(QUANTITY));
                } else if("currency".equals(getArguments().getString(ASSET_TYPE))) {

                    createPositionsRow("Lots", ":\t  " + getArguments().getString(QUANTITY));

                } else {
                    createPositionsRow("Qty/Per Lot", ":\t  " + getArguments().getString(LOT_QUANTITY));

                    createPositionsRow("Lots", ":\t  " + (Integer.parseInt(getArguments().getString(QUANTITY))) / (Integer.parseInt(getArguments().getString(LOT_QUANTITY))));

                }

                createPositionsRow("Order Type", ":\t  " + getArguments().getString(ORDER_TYPE));

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + getArguments().getString(PRICE));
                }

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("SL Trigger Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(TRIGGER_PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("SL Trigger Price", ":\t  " + getArguments().getString(TRIGGER_PRICE));
                }

                createPositionsRow("Order Life", ":\t  " + getArguments().getString(ORDER_LIFE));
            } else if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("StopLoss Market")) {
                createPositionsRow("Product Type", ":\t  " + getArguments().getString(PRODUCT_TYPE));

                createPositionsRow("Action", ":\t  " + getArguments().getString(ACTION).toUpperCase());


                if("equity".equals(getArguments().getString(ASSET_TYPE))) {
                    createPositionsRow("Quantity", ":\t  " + getArguments().getString(QUANTITY));
                } else if("currency".equals(getArguments().getString(ASSET_TYPE))) {

                    createPositionsRow("Lots", ":\t  " + getArguments().getString(QUANTITY));

                } else {
                    createPositionsRow("Qty/Per Lot", ":\t  " + getArguments().getString(LOT_QUANTITY));
                    createPositionsRow("Lots", ":\t  " + (Integer.parseInt(getArguments().getString(QUANTITY))) / (Integer.parseInt(getArguments().getString(LOT_QUANTITY))));

                }

                createPositionsRow("Order Type", ":\t  " + getArguments().getString(ORDER_TYPE));

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + getArguments().getString(PRICE));
                }

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("Trigger Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(TRIGGER_PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("Trigger Price", ":\t  " + getArguments().getString(TRIGGER_PRICE));
                }

                createPositionsRow("Order Life", ":\t  " + getArguments().getString(ORDER_LIFE));
            } else if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Bracket")) {
                createPositionsRow("Product Type", ":\t  " + getArguments().getString(PRODUCT_TYPE));

                createPositionsRow("Action", ":\t  " + getArguments().getString(ACTION).toUpperCase());


                if("equity".equals(getArguments().getString(ASSET_TYPE))) {
                    createPositionsRow("Quantity", ":\t  " + getArguments().getString(QUANTITY));
                } else if("currency".equals(getArguments().getString(ASSET_TYPE))) {

                    createPositionsRow("Lots", ":\t  " + getArguments().getString(QUANTITY));

                } else {
                    createPositionsRow("Qty/Per Lot", ":\t  " + getArguments().getString(LOT_QUANTITY));

                    createPositionsRow("Lots", ":\t  " + (Integer.parseInt(getArguments().getString(QUANTITY))) / (Integer.parseInt(getArguments().getString(LOT_QUANTITY))));

                }

                createPositionsRow("Order Type", ":\t  " + getArguments().getString(ORDER_TYPE));

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(PRICE))));
                    else
                        createPositionsRow("Limit Price", ":\t  " + "Market");

                } else {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + getArguments().getString(PRICE));
                    else
                        createPositionsRow("Limit Price", ":\t  " + "Market");
                }

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("SL Trigger Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(TRIGGER_PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("SL Trigger Price", ":\t  " + getArguments().getString(TRIGGER_PRICE));
                }

                createPositionsRow("Order Life", ":\t  " + getArguments().getString(ORDER_LIFE));
                createPositionsRow("Target Price", ":\t  " + getArguments().getString(TARGETPRICE));
                createPositionsRow("StopLoss Price", ":\t  " + getArguments().getString(SLPRICE));
            } else if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Cover")) {
                createPositionsRow("Product Type", ":\t  " + getArguments().getString(PRODUCT_TYPE));
                createPositionsRow("Action", ":\t  " + getArguments().getString(ACTION).toUpperCase());


                if("equity".equals(getArguments().getString(ASSET_TYPE))) {
                    createPositionsRow("Quantity", ":\t  " + getArguments().getString(QUANTITY));
                } else if("currency".equals(getArguments().getString(ASSET_TYPE))) {

                    createPositionsRow("Lots", ":\t  " + getArguments().getString(QUANTITY));

                } else {
                    createPositionsRow("Qty/Per Lot", ":\t  " + getArguments().getString(LOT_QUANTITY));

                    createPositionsRow("Lots", ":\t  " + (Integer.parseInt(getArguments().getString(QUANTITY))) / (Integer.parseInt(getArguments().getString(LOT_QUANTITY))));

                }

                createPositionsRow("Order Type", ":\t  " + getArguments().getString(ORDER_TYPE));

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(PRICE))));
                    else
                        createPositionsRow("Limit Price", ":\t  " + "Market");
                } else {
                    if(Double.parseDouble(getArguments().getString(PRICE)) > 0)
                        createPositionsRow("Limit Price", ":\t  " + getArguments().getString(PRICE));
                    else
                        createPositionsRow("Limit Price", ":\t  " + "Market");
                }

                if(getArguments().getString("AssetType").equalsIgnoreCase("currency")) {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("SL Trigger Price", ":\t  " + String.format("%.4f", Double.parseDouble(getArguments().getString(TRIGGER_PRICE))));
                } else {
                    if(Double.parseDouble(getArguments().getString(TRIGGER_PRICE)) > 0)
                        createPositionsRow("SL Trigger Price", ":\t  " + getArguments().getString(TRIGGER_PRICE));
                }

                createPositionsRow("Order Life", ":\t  " + getArguments().getString(ORDER_LIFE));

                createPositionsRow("StopLoss Price", ":\t  " + getArguments().getString(SLPRICE));
            }
    }
        if(!getArguments().isEmpty()) {

            if("equity".equals(getArguments().getString(ASSET_TYPE))) {
                selectedAssetType = 0;
            } else if("fno".equals(getArguments().getString(ASSET_TYPE))) {
                selectedAssetType = 1;
            } else if("currency".equals(getArguments().getString(ASSET_TYPE))) {
                selectedAssetType = 2;
            } else if("Commodity".equals(getArguments().getString(ASSET_TYPE))) {
                selectedAssetType = 3;
            }

        }

    }

    private void setupListeners() {
        back.setOnClickListener(backClickListener);
        confirm.setOnClickListener(confirmClickListener);
    }

    private void createPositionsRow(String key, String value) {

        int color;
        if(AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            color = (changeColor) ? R.color.floatingBgColor : R.color.backgroundStripColorWhite;
        } else {
            color = (changeColor) ? R.color.market_grey_dark : R.color.market_grey_light;
        }


        TableRow Row = new TableRow(getMainActivity());
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(key);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        keyView.setTypeface(font);
        keyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//        keyView.setTextColor(getResources().getColor(R.color.white));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LayoutParams params = (LayoutParams) keyView.getLayoutParams();
        params.weight = 1;
        params.bottomMargin = 1;
        keyView.setPadding(10, 10, 10, 10);

        GreekTextView valueView = new GreekTextView(getMainActivity());
        valueView.setPadding(5, 12, 5, 12);
        valueView.setTypeface(font);
//        valueView.setTextColor(getResources().getColor(R.color.white));
        valueView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        valueView.setText(value);


        if(":\tBuy".equalsIgnoreCase(value))
            if(AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if(":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));

        //valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if(":\tBuy".equalsIgnoreCase(value)) {
            if(AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                back.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                confirm.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
                back.setBackgroundColor(getResources().getColor(R.color.buyColor));
                confirm.setBackgroundColor(getResources().getColor(R.color.buyColor));
            }


        } else if(":\tSell".equalsIgnoreCase(value)) {
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
            back.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
            confirm.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
        }
        valueView.setBackgroundColor(getResources().getColor(color));

        valueView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LayoutParams params1 = (LayoutParams) valueView.getLayoutParams();
        params1.weight = 1;
        params1.bottomMargin = 1;
        valueView.setPadding(10, 10, 10, 10);
        Row.addView(keyView);
        Row.addView(valueView);
        orderDetails.addView(Row);

        changeColor = !changeColor;
    }

    private void sendTradeRequest() {
        // showProgress();
        try {

            AccountDetails.iris_Counter = 0;

            if(getArguments().getBoolean("isModifyOrder")) {
                try {
                    TradeModifyOrderRequest modifyRequest = new TradeModifyOrderRequest();
                    modifyRequest.setClientCode(AccountDetails.getClientCode(getMainActivity()));
                    modifyRequest.setDisQty(getArguments().getString(DIS_QUANTITY));
                    modifyRequest.setiStrategyNo(getArguments().getString("IstrategyNo"));
                    modifyRequest.setExchange(getArguments().getString(EXCHANGE));
                    modifyRequest.setLot(getArguments().getString(LOT_QUANTITY));
                    //request.setOrderLife(getArguments().getString(ORDER_LIFE).toUpperCase());
                    modifyRequest.setOrderType(getOrderType(getArguments().getString(ORDER_TYPE))); //
                    if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Bracket")) {
                        modifyRequest.setTarget_price(getArguments().getString(TARGETPRICE));
                        modifyRequest.setSl_price(getArguments().getString(SLPRICE));
                        if(getArguments().getString(TRIGGER_PRICE).equalsIgnoreCase("0")) {
                            modifyRequest.setTriggerPrice(getArguments().getString(SLPRICE));
                        } else {
                            modifyRequest.setTriggerPrice(getArguments().getString(TRIGGER_PRICE));
                        }
                    } else if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Cover")) {
                        modifyRequest.setSl_price(getArguments().getString(SLPRICE));
                        if(getArguments().getString(TRIGGER_PRICE).equalsIgnoreCase("0")) {
                            modifyRequest.setTriggerPrice(getArguments().getString(SLPRICE));
                        } else {
                            modifyRequest.setTriggerPrice(getArguments().getString(TRIGGER_PRICE));
                        }
                    } else {
                        modifyRequest.setTriggerPrice(getArguments().getString(TRIGGER_PRICE));
                    }
                    modifyRequest.setPrice(getArguments().getString(PRICE));
                    modifyRequest.setProduct(getProduct(getArguments().getString(PRODUCT_TYPE)).toLowerCase());
                    modifyRequest.setQty((getArguments().getString(QUANTITY)));
                    modifyRequest.setStrToken(getArguments().getString(TOKEN));
                    modifyRequest.setTradeSymbol(getArguments().getString(TRADE_SYMBOL));
                    modifyRequest.setIomRuleNo(getArguments().getString(IOM_RULE_NO));

                    modifyRequest.setValidity(getValidity(getArguments().getString(ORDER_LIFE)));

                    if(getArguments().getBoolean("isOffline")) {

                        modifyRequest.setOffline("1");
                    } else {
                        modifyRequest.setOffline("0");
                    }

                    if(getArguments().getBoolean("isSquareOff")) {
                        modifyRequest.setIsSqOffOrder("true");
                    } else {
                        modifyRequest.setIsSqOffOrder("false");
                    }


                    if(getArguments().getBoolean("valid_amo_status")) {


                        modifyRequest.setAmo("1");
                    } else {
                        modifyRequest.setAmo("0");
                    }
                    if(!getArguments().getString(GTD_EXPIRY).equalsIgnoreCase("0")) {
                        //modifyRequest.setGtdExpiry(ConvertToLong(getArguments().getString(GTD_EXPIRY)));
                        if(getArguments().getString(EXCHANGE).equalsIgnoreCase("ncdex")) {
                            modifyRequest.setGtdExpiry(ConvertToLongForNcdex(getArguments().getString(GTD_EXPIRY)));
                        } else if(getArguments().getString(EXCHANGE).equalsIgnoreCase("mcx")) {
                            modifyRequest.setGtdExpiry(ConvertToLongForMcx(getArguments().getString(GTD_EXPIRY)));
                        } else if(getArguments().getString(EXCHANGE).equalsIgnoreCase("nse") || getArguments().getString(EXCHANGE).equalsIgnoreCase("bse")|| getArguments().getString(EXCHANGE).equalsIgnoreCase("BSECURR")|| getArguments().getString(EXCHANGE).equalsIgnoreCase("NSECURR")) {
//                            modifyRequest.setGtdExpiry(ConvertToLongForMcx(getArguments().getString(GTD_EXPIRY)));
                            modifyRequest.setGtdExpiry(ConvertToLongForNSEBSE(getArguments().getString(GTD_EXPIRY)));
                        }
                    } else {
                        modifyRequest.setGtdExpiry(Long.valueOf(getArguments().getString(GTD_EXPIRY)));
                    }
                    modifyRequest.setSide(getArguments().getString(ACTION).equalsIgnoreCase("buy") ? "1" : "2");
                    modifyRequest.setLu_time_exchange(getArguments().getString(LU_TIME_EXCHANGE));
                    modifyRequest.setPending_disclosed_qty(getArguments().getString(PENDING_DISCLOSED_QTY));
                    modifyRequest.setQty_filled_today(getArguments().getString(QTY_FILLED_TODAY));
                    modifyRequest.setPending_qty(getArguments().getString(PENDINGQTY));
                    modifyRequest.setGorderid(getArguments().getString(GORDERID));
                    modifyRequest.setEorderid(getArguments().getString(EORDERID));
                    modifyRequest.setLexchangeOrderNo1(getArguments().getString(LEXCHANGEORDERNO1));
                    modifyRequest.setIsPreOpen(getArguments().getString(isPreOpen));
                    modifyRequest.setIsPostClosed(getArguments().getString(isPostClosed));

                    if(AccountDetails.isOrderServerAuthenticated() && AccountDetails.orderforceClose == 2) {
                        orderStreamingController.sendStreamingModifyOrderRequest(getActivity(), modifyRequest);
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Order Sent for Modification", "OK", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                        confirm.setVisibility(View.GONE);
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "We are facing Network congestion.Kindly try after sometime", "Ok", false, null);
                        confirm.setVisibility(View.GONE);
                    }
                    greekBaseActivity.startOrderTimer();
                    hideProgress();

                } catch (Exception e) {
                    e.getStackTrace();
                    //generateNoteOnSd(e.toString() + "-");
                }
            } else
                {
                try {
                    TradeSendNewOrderRequest request = new TradeSendNewOrderRequest();
                    request.setClientCode(AccountDetails.getClientCode(getMainActivity()));
                    request.setDisQty(getArguments().getString(DIS_QUANTITY));
                    if(getArguments().getBoolean("isSquareOff")) {
                        request.setIsSqOffOrder("true");
                    } else {
                        request.setIsSqOffOrder("false");
                    }

                    request.setExchange(getArguments().getString(EXCHANGE));
                    request.setLot(getArguments().getString(LOT_QUANTITY));
                    //request.setOrderLife(getArguments().getString(ORDER_LIFE).toUpperCase());
                    request.setOrderType(getOrderType(getArguments().getString(ORDER_TYPE))); //
                    if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Bracket")) {
                        request.setTarget_price(getArguments().getString(TARGETPRICE));
                        request.setSl_price(getArguments().getString(SLPRICE));
                        if(getArguments().getString(TRIGGER_PRICE).equalsIgnoreCase("0")) {
                            request.setTriggerPrice(getArguments().getString(SLPRICE));
                        } else {
                            request.setTriggerPrice(getArguments().getString(TRIGGER_PRICE));
                        }
                    } else if(getArguments().getString(ORDER_TYPE).equalsIgnoreCase("Cover")) {
                        request.setSl_price(getArguments().getString(SLPRICE));
                        if(getArguments().getString(TRIGGER_PRICE).equalsIgnoreCase("0")) {
                            request.setTriggerPrice(getArguments().getString(SLPRICE));
                        } else {
                            request.setTriggerPrice(getArguments().getString(TRIGGER_PRICE));
                        }
                    } else {
                        request.setTriggerPrice(getArguments().getString(TRIGGER_PRICE));
                    }

                    //request.setPrice("0");
                    request.setPrice(getArguments().getString(PRICE));
                    request.setProduct(getProduct(getArguments().getString(PRODUCT_TYPE)).toLowerCase());
                    request.setQty(getArguments().getString(QUANTITY));
                    request.setStrToken(getArguments().getString(TOKEN));
                    request.setTradeSymbol(getArguments().getString(TRADE_SYMBOL));

                    if(!getArguments().getString(GTD_EXPIRY).equalsIgnoreCase("0")) {
                        if(getArguments().getString(EXCHANGE).equalsIgnoreCase("ncdex")) {
                            request.setGtdExpiry(ConvertToLongForNcdex(getArguments().getString(GTD_EXPIRY)));
                        } else if(getArguments().getString(EXCHANGE).equalsIgnoreCase("mcx")) {
                            request.setGtdExpiry(ConvertToLongForMcx(getArguments().getString(GTD_EXPIRY)));
                        } else if(getArguments().getString(EXCHANGE).equalsIgnoreCase("nse") || getArguments().getString(EXCHANGE).equalsIgnoreCase("bse")
                                || getArguments().getString(EXCHANGE).equalsIgnoreCase("BSECURR")
                                || getArguments().getString(EXCHANGE).equalsIgnoreCase("NSECURR")) {
//                            request.setGtdExpiry(ConvertToLongForMcx(getArguments().getString(GTD_EXPIRY)));
                            request.setGtdExpiry(ConvertToLongForNSEBSE(getArguments().getString(GTD_EXPIRY)));
                        }
                    } else {
                        request.setGtdExpiry(Long.valueOf(getArguments().getString(GTD_EXPIRY)));
                    }

                    if(getArguments().getBoolean("isOffline")) {

                        request.setOffline("1");
                    } else {
                        request.setOffline("0");
                    }

                    if(getArguments().getBoolean("valid_amo_status")) {
                        request.setAmo("1");
                    } else {
                        request.setAmo("0");
                    }


                    request.setIsPreOpen(getArguments().getString(isPreOpen));
                    request.setIsPostClosed(getArguments().getString(isPostClosed));
                    request.setValidity(getValidity(getArguments().getString(ORDER_LIFE)));
                    request.setSide(getArguments().getString(ACTION).equalsIgnoreCase("buy") ? "1" : "2");
                    request.setCorderid(getNextNumber().toString());

                    if(AccountDetails.isOrderServerAuthenticated() && AccountDetails.orderforceClose == 2) {
                        orderStreamingController.sendStreamingSendOrderRequest(getActivity(), request);

                        //GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Order detail captured and will be notified once submitted to server", "Ok", false, null);
                        LinearLayout orderStatus = tradePreviewView.findViewById(R.id.order_status_layout);
                        GreekTextView orderStatusTxt = tradePreviewView.findViewById(R.id.order_status);

                        orderStatus.setVisibility(View.VISIBLE);
                        orderStatusTxt.setVisibility(View.VISIBLE);
                        orderStatusTxt.setText("Order Captured");
                        orderStatusTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        confirm.setVisibility(View.GONE);
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "We are facing Network congestion.Kindly try after sometime", "Ok", false, null);
                        confirm.setVisibility(View.GONE);
                    }

                    hideProgress();
                } catch (Exception e) {
                    e.getStackTrace();
                    //generateNoteOnSd(e.toString() + "-");
                }
            }
            if(Util.getPrefs(getMainActivity()).getBoolean("GREEK_RETAIN_QTY_TOGGLE", false)) {
                saveOrderQty();
            }

            //getTimerStarted();
            greekBaseActivity.startOrderTimer();

        } catch (Exception e) {
            //generateNoteOnSd(e.toString() + "-");
        }
    }


    private Long ConvertToLongForNcdex(String expdate) throws ParseException {

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date d = f.parse(expdate);
        long dateExpiry = d.getTime();

        long output = d.getTime() / 1000L;
        output = output - (315493200 + 19800);

        return Long.valueOf(output);
    }

    private Long ConvertToLongForNSEBSE(String expdate) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date d = f.parse(expdate);
        long dateExpiry = d.getTime();

        long output = d.getTime() / 1000L;
        output = output - (315493200 + 19800);

        return Long.valueOf(output);
    }

    private Long ConvertToLongForMcx(String expdate) throws ParseException {

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date d = f.parse(expdate);
        long dateExpiry = d.getTime();

        long output = d.getTime() / 1000L;
        //output = output + 86399;
        output = output + 52199;
        return Long.valueOf(output);
    }


    public void generateNoteOnSd(String error) {
        try {
            Random random = new Random();
            int value = random.nextInt();
            String h = String.valueOf(value);
            File root = new File(Environment.getExternalStorageDirectory(), "Logs");
            if(!root.exists()) {
                root.mkdir();
            }
            File filepath = new File(root, h + ".txt");
            FileWriter writer = new FileWriter(filepath);
            writer.append(error);
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }
    }


    public void onEventMainThread(OrderStreamingAuthResponse response) {
        try {
            if(response.getError_code().equals("0")) {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Authentication success on order server, ready to accept order request", "Ok", false, null);
                confirm.setVisibility(View.VISIBLE);
            } else {
                GreekDialog.alertDialog(getActivity(), ALERTS_ACTION_ID, GREEK, "Authentication failed again on order server", "Ok", false, null);
                if(Util.getPrefs(getActivity()).getBoolean("GREEK_BEEP_VIBRATE_TOGGLE", true)) {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getActivity(), notification);
                    r.play();
                    Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProduct(String product) {
       /* if(product.equalsIgnoreCase("Intraday")) {
            return "1";
        }
        if(product.equalsIgnoreCase("Delivery")) {
            return "0";
        }
        if(product.equalsIgnoreCase("MTF")) {
            return "2";
        }
        if(product.equalsIgnoreCase("SSEQ")) {
            return "5";
        }
        if(product.equalsIgnoreCase("TNC")) {
            return "3";
        }
        if(product.equalsIgnoreCase("CATALYST")) {
            return "4";
        }
        return "";*/

        for(int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if(AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase(product)){
                return AccountDetails.getAllowedProduct().get(i).getiProductToken();
            }
        }
        return "";
    }


    public String getOrderType(String orderType) {
        if(orderType.equalsIgnoreCase("Limit")) {
            return "1";
        }
        if(orderType.equalsIgnoreCase("Market")) {
            return "2";
        }
        if(orderType.equalsIgnoreCase("Stop Loss")) {
            return "3";
        }
        if(orderType.equalsIgnoreCase("StopLoss Market")) {
            return "4";
        }
        if(orderType.equalsIgnoreCase("Cover")) {
            return "5";
        }
        if(orderType.equalsIgnoreCase("After Market")) {
            return "6";
        }
        if(orderType.equalsIgnoreCase("Bracket")) {
            return "7";
        }
        return "";
    }

    public String getValidity(String validity) {
        if(validity.equalsIgnoreCase("Day")) {
            return "0";
        } else if(validity.equalsIgnoreCase("IOC")) {
            return "1";
        } else if(validity.equalsIgnoreCase("EOS")) {
            return "2";
        } else if(validity.equalsIgnoreCase("GTD")) {
            return "3";
        } else if(validity.equalsIgnoreCase("GTC")) {
            return "4";
        }
        return "";
    }

    private void saveOrderQty() {
        switch (selectedAssetType) {
            case 0:
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_CASH_QTY", getArguments().getString(QUANTITY)).commit();
                break;
            case 1:
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_FNO_QTY", getArguments().getString(QUANTITY)).commit();
                break;
            case 2:
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_CURR_QTY", getArguments().getString(QUANTITY)).commit();
                break;
            case 3:
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_COMM_QTY", getArguments().getString(QUANTITY)).commit();
                break;
            default:
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_COMM_QTY", "").commit();
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_CURR_QTY", "").commit();
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_FNO_QTY", "").commit();
                Util.getPrefs(getMainActivity()).edit().putString("GREEK_LAST_CASH_QTY", "").commit();
                break;
        }
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
    }

    @Override
    public void onFragmentPause() {
        //super.onFragmentPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
package com.acumengroup.mobile.reports;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.core.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.MarketStatusPostRequest;
import com.acumengroup.greekmain.core.model.MarketStatusPostResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.MarketStatusResponse;
import com.acumengroup.greekmain.core.model.tradecancelorder.TradeCancelOrderRequest;
import com.acumengroup.greekmain.core.model.tradeorderbook.OrderBook;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class OrderBookDetailsFragment extends GreekBaseFragment {
    private OrderBook orderBookData;
    private View orderBookDetailsView;
    private Button modifyBtn, cancelBtn;
    private String orderType = "";
    OrderStreamingController orderStreamingController;
    LinearLayout orderDetails;
    private Boolean changeColor = true;
    private Boolean isPreOpen_status = false;
    private Boolean isPostOpen_status = false;
    private String modifyOrderType;
    private String modifyOrderLife;
    private boolean valid_status;
    private static CountDownTimer countDownTimer;
    public String nse_eq = "red", bse_eq = "red", nse_fno = "red", bse_fno = "red", nse_cd = "red", bse_cd = "red", mcx_com = "red", ncdex_com = "red";
    boolean eq, bseeq = false;
    boolean fno, bsefno = false;
    boolean cd, bsecd = false;

    private final OnClickListener modifyClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            switch (orderType) {
                case "OrderBook":
                    if (orderBookData.getTag().trim().equalsIgnoreCase("")) {
                        bundle.putString("Token", orderBookData.getToken());
                        bundle.putString("Action", orderBookData.getAction());
                        bundle.putString("TradeSymbol", orderBookData.getTradeSymbol());
                        bundle.putString("Product", orderBookData.getProduct());
                        bundle.putString("ExchangeName", getExchange(orderBookData.getToken()));
                        bundle.putString("OrderType", modifyOrderType);
                        bundle.putString("OrderLife", modifyOrderLife);
                        bundle.putString("OrderLiveDays", orderBookData.getOrdLiveDays());
                        bundle.putString("AssetType", getAssetType(orderBookData.getToken()));

                        int qty = Integer.parseInt(orderBookData.getPendingQty()) / Integer.parseInt(orderBookData.getLotSize());

                        bundle.putString("Qty", qty + "");
                        bundle.putString("DisQty", orderBookData.getDiscQty());
                        if (!modifyOrderType.equalsIgnoreCase("7")) {
                            if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {
                                bundle.putString("Price", String.format("%.4f", Double.parseDouble(orderBookData.getPrice())));
                                bundle.putString("TriggerPrice", String.format("%.4f", Double.parseDouble(orderBookData.getTrigPrice())));
                                bundle.putString("AssetType", "currency");
                            } else {
                                bundle.putString("Price", String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));
                                bundle.putString("TriggerPrice", String.format("%.2f", Double.parseDouble(orderBookData.getTrigPrice())));
                                bundle.putString("AssetType", getAssetType(orderBookData.getToken()));
                            }
                        } else {
                            if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {
                                bundle.putString("Price", String.format("%.4f", Double.parseDouble(orderBookData.getPrice())));
                                bundle.putString("TriggerPrice", String.format("%.4f", Double.parseDouble(orderBookData.getdSLTPrice())));
                                bundle.putString("AssetType", "currency");
                            } else {
                                bundle.putString("Price", String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));
                                bundle.putString("TriggerPrice", String.format("%.2f", Double.parseDouble(orderBookData.getdSLTPrice())));
                                bundle.putString("AssetType", getAssetType(orderBookData.getToken()));
                            }
                        }

                        bundle.putString("SymbolName", orderBookData.getTradeSymbol());
                        bundle.putString("OrderID", orderBookData.getUniqueOrderID());
                        bundle.putString("Lots", orderBookData.getLotSize());
                        bundle.putString("TickSize", orderBookData.getTickSize());
                        bundle.putString("Multiplier", orderBookData.getMultiplier());
                        bundle.putBoolean("isModifyOrder", true);
                        bundle.putString("PendingQty", orderBookData.getPendingQty());
                        bundle.putString("lu_time_exchange", orderBookData.getOrdModTime());
                        bundle.putString("pending_disclosed_qty", "1");
                        bundle.putString("qty_filled_today", orderBookData.getTrdQty());
                        bundle.putString("gorderid", orderBookData.getUniqueID());
                        bundle.putString("eorderid", orderBookData.getUniqueOrderID());
                        bundle.putString("lexchangeOrderNo1", orderBookData.getOrdID());
                        bundle.putString("lgoodtilldate", orderBookData.getLgoodtilldate());
                        bundle.putString("strategyid", orderBookData.getiStrategyId());
                        bundle.putString("stoplossprice", orderBookData.getdSLPrice());
                        bundle.putString("targetprice", orderBookData.getdTargetPrice());
                        bundle.putString("iomruleno", orderBookData.getlIOMRuleNo());

                        if (orderBookData.getOtype().equalsIgnoreCase("5")) {

                            bundle.putString("otype", "offline");
                        } else if (orderBookData.getOtype().equalsIgnoreCase("6")) {

                            bundle.putString("otype", "amo");
                        } else {
                            bundle.putString("otype", "normal");
                        }

                        getMarketstatus();

                        if ((!(orderBookData.getStatus().toLowerCase()).contains("unconfirmed")) && valid_status) {

                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Modify Not Allowed", "Ok", false, null);

                        } else {

                            navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);
                            break;

                        }


                    } else if (orderBookData.getTag().equalsIgnoreCase("ageing")) {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Modify Not Allowed For Ageing Order", "Ok", false, null);
                    }
            }
        }
    };

    private void getMarketstatus() {

        if (getExchange(orderBookData.getToken()).equalsIgnoreCase("Nse") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("equity") && AccountDetails.nse_eq_status) {
            valid_status = true;
        } else if (getExchange(orderBookData.getToken()).equalsIgnoreCase("bse") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("equity") && AccountDetails.bse_eq_status) {
            valid_status = true;
        } else if (getExchange(orderBookData.getToken()).equalsIgnoreCase("Nse") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("fno") && AccountDetails.nse_fno_status) {
            valid_status = true;
        } else if (getExchange(orderBookData.getToken()).equalsIgnoreCase("bse") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("fno") && AccountDetails.bse_fno_status) {
            valid_status = true;
        } else if (getExchange(orderBookData.getToken()).equalsIgnoreCase("Nse") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("currency") && AccountDetails.nse_cd_status) {
            valid_status = true;
        } else if (getExchange(orderBookData.getToken()).equalsIgnoreCase("bse") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("currency") && AccountDetails.bse_cd_status) {
            valid_status = true;
        } else if (getExchange(orderBookData.getToken()).equalsIgnoreCase("mcx") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("commodity") && AccountDetails.mcx_com_status) {
            valid_status = true;
        } else if (getExchange(orderBookData.getToken()).equalsIgnoreCase("ncdex") && getAssetType(orderBookData.getToken()).equalsIgnoreCase("commodity") && AccountDetails.ncdex_com_status) {
            valid_status = true;

        }
    }

    private final OnClickListener cancelClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (orderBookData.getTag().trim().equalsIgnoreCase("")) {

                if (AccountDetails.getToastCount() == 0) {
                    Toast.makeText(getActivity(), "Do you want to cancel the order? Press one more time", Toast.LENGTH_LONG).show();
//                AccountDetails.setToastCount(1);
                }

                if (AccountDetails.getToastCount() == 1) {
                    cancelTheOrder();
                    AccountDetails.setToastCount(0);
                } else {
                    AccountDetails.setToastCount(1);
                }

                countDownTimer = new CountDownTimer(Integer.valueOf(10) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        AccountDetails.setToastCount(0);
                    }
                }.start();
            } else if (orderBookData.getTag().equalsIgnoreCase("ageing")) {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Cancellation Not Allowed For Ageing Order", "Ok", false, null);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orderBookDetailsView = super.onCreateView(inflater, container, savedInstanceState);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_orderbook_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_orderbook_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_ORDER_BOOK_DETAILS_SCREEN;
        orderStreamingController = new OrderStreamingController();
        orderDetails = orderBookDetailsView.findViewById(R.id.order_details);
        setupView();
       /* MarketStatusPostRequest.sendRequest(AccountDetails.getUsername(getMainActivity()),
                getMainActivity(), serviceResponseHandler);*/
        return orderBookDetailsView;
    }


    private void setupView() {
        setAppTitle(getClass().toString(), "Order Details");

        modifyBtn = orderBookDetailsView.findViewById(R.id.modify_Order_btn);
        modifyBtn.setOnClickListener(modifyClickListener);

        cancelBtn = orderBookDetailsView.findViewById(R.id.cancel_Order_btn);
        cancelBtn.setOnClickListener(cancelClickListener);


        parseReceivedArguments();
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

    private void parseReceivedArguments() {
        orderType = getArguments().getString("Type");
        orderType = (orderType == null) ? "" : orderType;
        switch (orderType) {
            case "OrderBook":
                orderBookData = (OrderBook) getArguments().getSerializable("response");

                if (orderBookData.getStatus().equalsIgnoreCase("RMS Rejected")) {

                    if (getExchange(orderBookData.getToken()).equalsIgnoreCase("MCX")) {

                        if (orderBookData.getOptionType().equalsIgnoreCase("XX")) {
                            createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + orderBookData.getInstrument());
                        } else {
                            createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + orderBookData.getStrikePrice() + orderBookData.getOptionType() + "-" + orderBookData.getInstrument());
                        }


                    } else {
                        createPositionsRow("Symbol", ":\t" + orderBookData.getDescription() + " - " + orderBookData.getInstrument());
                    }

                    createPositionsRow("Order Id", ":\t" + orderBookData.getUniqueID());
                    createPositionsRow("Exchange", ":\t" + getExchange(orderBookData.getToken()));
                    if (!orderBookData.getExpiryDate().equalsIgnoreCase("0")) {
                        createPositionsRow("Expiry Date", ":\t" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "dd MMM yyyy", "bse"));
                    }
                    createPositionsRow("Order Time", ":\t" + orderBookData.getOrdTime());
                    createPositionsRow("Remark", ":\t" + orderBookData.getRemarks());

                } else {

                    if (getExchange(orderBookData.getToken()).equalsIgnoreCase("MCX")) {

                        if (orderBookData.getOptionType().equalsIgnoreCase("XX")) {
                            createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + orderBookData.getInstrument());
                        } else {
                            createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + orderBookData.getStrikePrice() + orderBookData.getOptionType() + "-" + orderBookData.getInstrument());
                        }

                    } else {
                        createPositionsRow("Symbol", ":\t" + orderBookData.getDescription() + " - " + orderBookData.getInstrument());
                    }

                    createPositionsRow("Order Id", ":\t" + orderBookData.getUniqueID());
                    createPositionsRow("Exchange Id", ":\t" + orderBookData.getUniqueOrderID());
                    createPositionsRow("Exchange", ":\t" + getExchange(orderBookData.getToken()));

//                    if (orderBookData.getProduct().equalsIgnoreCase("1")) {
//                        createPositionsRow("Product Type", ":\t" + "Intraday");
//                    } else if (orderBookData.getProduct().equalsIgnoreCase("0")) {
//                        createPositionsRow("Product Type", ":\t" + "Delivery");
//                    } else if (orderBookData.getProduct().equalsIgnoreCase("2")) {
//                        createPositionsRow("Product Type", ":\t" + "MTF");
//                    }else if (orderBookData.getProduct().equalsIgnoreCase("3")) {
//                        createPositionsRow("Product Type", ":\t" + "SSEQ");
//                    }

                    if (orderBookData.getProduct().equalsIgnoreCase("1")) {
                        createPositionsRow("Product Type", ":\t" + "Intraday");
                    } else if (orderBookData.getProduct().equalsIgnoreCase("0")) {
                        createPositionsRow("Product Type", ":\t" + "Delivery");
                    } else if (orderBookData.getProduct().equalsIgnoreCase("2")) {
                        createPositionsRow("Product Type", ":\t" + "MTF");
                    } else if (orderBookData.getProduct().equalsIgnoreCase("5")) {
                        createPositionsRow("Product Type", ":\t" + "SSEQ");
                    } else if (orderBookData.getProduct().equalsIgnoreCase("3")) {
                        createPositionsRow("Product Type", ":\t" + "TNC");
                    } else if (orderBookData.getProduct().equalsIgnoreCase("4")) {
                        createPositionsRow("Product Type", ":\t" + "CATALYST");
                    }

                    BitWiseOperation bt = new BitWiseOperation();

                    if (orderBookData.getStatus().equalsIgnoreCase("Unconfirmed")) {


                        if (orderBookData.getiStrategyId().equalsIgnoreCase("50")) {
                            createPositionsRow("Order Type", ":\t" + "Bracket");
                            modifyOrderType = "7";
                        } else if (Double.parseDouble(orderBookData.getPrice()) == 0.00) {
                            createPositionsRow("Order Type", ":\t" + "Market");
                            modifyOrderType = "2";
                        } else {
                            createPositionsRow("Order Type", ":\t" + "Limit");
                            modifyOrderType = "1";
                        }
                    } else {
                        if (orderBookData.getiStrategyId().equalsIgnoreCase("50")) {
                            createPositionsRow("Order Type", ":\t" + "Bracket");
                            modifyOrderType = "7";
                        } else {
                            if (orderBookData.getBookType().equalsIgnoreCase("1")) {
                                if (bt.getBitResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                                    if (Double.parseDouble(orderBookData.getPrice()) == 0.00) {
                                        createPositionsRow("Order Type", ":\t" + "Market");
                                        modifyOrderType = "2";
                                    } else {
                                        createPositionsRow("Order Type", ":\t" + "Limit");
                                        modifyOrderType = "1";
                                    }
                                } else {
                                    createPositionsRow("Order Type", ":\t" + "Limit");
                                    modifyOrderType = "1";
                                }
                            } else if (orderBookData.getBookType().equalsIgnoreCase("3")) {
                                if (bt.getBitResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                                    createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                    modifyOrderType = "4";
                                } else {
                                    modifyOrderType = "3";
                                    if (Double.parseDouble(orderBookData.getPrice()) == 0.00 && modifyOrderType.equalsIgnoreCase("3")) {
                                        createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                        modifyOrderType = "4";
                                    } else {
                                        createPositionsRow("Order Type", ":\t" + "Stop Loss");
                                        modifyOrderType = "3";
                                    }
                                }
                            }
                        }


                    }

                    if (!orderBookData.getExpiryDate().equalsIgnoreCase("0")) {
                        createPositionsRow("Expiry Date", ":\t" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "dd MMM yyyy", "bse"));
                    }


                    if (orderBookData.getAction().equalsIgnoreCase("1")) {
                        createPositionsRow("Action", ":\t" + "BUY");
                    } else {
                        createPositionsRow("Action", ":\t" + "SELL");
                    }

                    createPositionsRow("Pan Number", ":\t" + orderBookData.getcPANNumber());

                    if (modifyOrderType.equalsIgnoreCase("7")) {

                        if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {
                            createPositionsRow("Stoploss Price", ":\t" + String.format("%.4f", Double.parseDouble(orderBookData.getdSLPrice())));
                            createPositionsRow("Target Price", ":\t" + String.format("%.4f", Double.parseDouble(orderBookData.getdTargetPrice())));
                            createPositionsRow("Trigger Price", ":\t" + String.format("%.4f", Double.parseDouble(orderBookData.getdSLTPrice())));
                        } else {
                            createPositionsRow("Stoploss Price", ":\t" + String.format("%.2f", Double.parseDouble(orderBookData.getdSLPrice())));
                            createPositionsRow("Target Price", ":\t" + String.format("%.2f", Double.parseDouble(orderBookData.getdTargetPrice())));
                            createPositionsRow("Trigger Price", ":\t" + String.format("%.2f", Double.parseDouble(orderBookData.getdSLTPrice())));
                        }
                    }

                    if (orderBookData.getOrderType().equalsIgnoreCase("3")) {

                        if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {
                            createPositionsRow("Trigger Price", ":\t" + String.format("%.4f", Double.parseDouble(orderBookData.getTrigPrice())));
                        } else {
                            createPositionsRow("Trigger Price", ":\t" + String.format("%.2f", Double.parseDouble(orderBookData.getTrigPrice())));
                        }
                    }

                    if (getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("mcx") || getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("ncdex")) {
                        createPositionsRow("Quantity", ":\t" + (Integer.parseInt(orderBookData.getTrdQty()) + Integer.parseInt(orderBookData.getPendingQty())) / Integer.parseInt(orderBookData.getLotSize()));
                        createPositionsRow("Disclosed Quantity", ":\t" + (Integer.parseInt(orderBookData.getDiscQty())) / (Integer.parseInt(orderBookData.getLotSize())));
                    } else {
                        createPositionsRow("Quantity", ":\t" + (Integer.parseInt(orderBookData.getTrdQty()) + Integer.parseInt(orderBookData.getPendingQty())));
                        createPositionsRow("Disclosed Quantity", ":\t" + orderBookData.getDiscQty());
                    }

                    createPositionsRow("Order Time", ":\t" + orderBookData.getOrdTime());

                    if (bt.getDayResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                        createPositionsRow("Order Life", ":\t" + "Day");
                        modifyOrderLife = "Day";
                    } else if (bt.getIOCResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                        createPositionsRow("Order Life", ":\t" + "IOC");
                        modifyOrderLife = "IOC";
                    } else if (bt.getGTCResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                        createPositionsRow("Order Life", ":\t" + "GTC");
                        modifyOrderLife = "GTC";
                    } else if (bt.getEOSResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                        createPositionsRow("Order Life", ":\t" + "EOS");
                        modifyOrderLife = "EOS";
                    } else if (Integer.parseInt(orderBookData.getLgoodtilldate()) > 0) {
                        createPositionsRow("Order Life", ":\t" + "GTD");
                        modifyOrderLife = "GTD";
                    }


                    if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {
                        createPositionsRow("Order Price", ":\t" + String.format("%.4f", Double.parseDouble(orderBookData.getPrice())));
                    } else {
                        createPositionsRow("Order Price", ":\t" + String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));
                    }
                    if (getExchange(orderBookData.getToken()).equalsIgnoreCase("mcx") || getExchange(orderBookData.getToken()).equalsIgnoreCase("ncdex")) {
                        createPositionsRow("Volume Traded", ":\t" + (Integer.parseInt(orderBookData.getTrdQty())) / (Integer.parseInt(orderBookData.getLotSize())));
                        createPositionsRow("Volume Remaining", ":\t" + (Integer.parseInt(orderBookData.getPendingQty())) / (Integer.parseInt(orderBookData.getLotSize())));
                    } else {
                        createPositionsRow("Volume Traded", ":\t" + orderBookData.getTrdQty());
                        createPositionsRow("Volume Remaining", ":\t" + orderBookData.getPendingQty());
                    }


                    if (modifyOrderLife != null && modifyOrderLife.equalsIgnoreCase("gtd")) {
                        if (getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("nse")) {
                            createPositionsRow("GTD Expiry", ":\t" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getLgoodtilldate(), "dd-MMM-yyyy", "nse"));

                            Log.e("TradeFragment", "getLgoodtilldate===>" + orderBookData.getLgoodtilldate());


                        } else if (getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("bse")) {
                            createPositionsRow("GTD Expiry", ":\t" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getLgoodtilldate(), "dd-MMM-yyyy", "nse"));

                            Log.e("TradeFragment", "getLgoodtilldate===>" + orderBookData.getLgoodtilldate());


                        } else {
                            createPositionsRow("GTD Expiry", ":\t" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getLgoodtilldate(), "dd-MMM-yyyy", "bse"));
                        }

                    }
                    createPositionsRow("Status", ":\t" + orderBookData.getStatus());
                    GreekBaseActivity greekBaseActivity = new GreekBaseActivity();
                    if (!orderBookData.getErrorCode().equalsIgnoreCase("0") && !orderBookData.getErrorCode().equalsIgnoreCase("201") && !orderBookData.getErrorCode().equalsIgnoreCase("202") && !orderBookData.getErrorCode().equalsIgnoreCase("101") && !orderBookData.getErrorCode().equalsIgnoreCase("102") && !orderBookData.getErrorCode().equalsIgnoreCase("502") && !orderBookData.getErrorCode().equalsIgnoreCase("1302") && !orderBookData.getErrorCode().equalsIgnoreCase("303") && !orderBookData.getErrorCode().equalsIgnoreCase("1301") && !orderBookData.getErrorCode().equalsIgnoreCase("16388")) {
                        createPositionsRow("Code", ":\t" + removetillword(greekBaseActivity.getErrorMessage(greekBaseActivity.getMarketId(orderBookData.getToken()), orderBookData.getErrorCode()), "Reason"));
                    }
                }


                if (orderBookData.getStatus().equalsIgnoreCase("pending")) {
                    modifyBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                } else if (orderBookData.getStatus().equalsIgnoreCase("amo unconfirmed") || orderBookData.getStatus().equalsIgnoreCase("offline unconfirmed") || orderBookData.getStatus().equalsIgnoreCase("unconfirmed")) {
                    modifyBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                } else {
                    modifyBtn.setEnabled(false);
                    cancelBtn.setEnabled(false);
                }

                String exchangeType = getExchange(orderBookData.getToken());
                String assetType = getAssetType(orderBookData.getToken());

                if (exchangeType.equalsIgnoreCase("Nse") && assetType.equalsIgnoreCase("equity") && AccountDetails.isPreOpen_nse_eq) {
                    isPreOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("bse") && assetType.equalsIgnoreCase("equity") && AccountDetails.isPreOpen_bse_eq) {
                    isPreOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("Nse") && assetType.equalsIgnoreCase("fno") && AccountDetails.isPreOpen_nse_fno) {
                    isPreOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("bse") && assetType.equalsIgnoreCase("fno") && AccountDetails.isPreOpen_bse_fno) {
                    isPreOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("Nse") && assetType.equalsIgnoreCase("currency") && AccountDetails.isPreOpen_nse_cd) {
                    isPreOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("bse") && assetType.equalsIgnoreCase("currency") && AccountDetails.isPreOpen_bse_cd) {
                    isPreOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("mcx") && assetType.equalsIgnoreCase("commodity") && AccountDetails.isPreOpen_mcx_com) {
                    isPreOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("ncdex") && assetType.equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
                    isPreOpen_status = true;
                }

                if (exchangeType.equalsIgnoreCase("Nse") && assetType.equalsIgnoreCase("equity") && AccountDetails.isPostClosed_nse_eq) {
                    isPostOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("bse") && assetType.equalsIgnoreCase("equity") && AccountDetails.isPostClosed_bse_eq) {
                    isPostOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("Nse") && assetType.equalsIgnoreCase("fno") && AccountDetails.isPostClosed_nse_fno) {
                    isPostOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("bse") && assetType.equalsIgnoreCase("fno") && AccountDetails.isPostClosed_bse_fno) {
                    isPostOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("Nse") && assetType.equalsIgnoreCase("currency") && AccountDetails.isPostClosed_nse_cd) {
                    isPostOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("bse") && assetType.equalsIgnoreCase("currency") && AccountDetails.isPostClosed_bse_cd) {
                    isPostOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("mcx") && assetType.equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_mcx_com) {
                    isPostOpen_status = true;
                } else if (exchangeType.equalsIgnoreCase("ncdex") && assetType.equalsIgnoreCase("commodity") && AccountDetails.isPostClosed_ncdex_com) {
                    isPostOpen_status = true;
                }
                break;
        }
    }


    public String getValidity(String validity) {
        if (validity.equalsIgnoreCase("Day")) {
            return "0";
        }
        if (validity.equalsIgnoreCase("IOC")) {
            return "1";
        }
        if (validity.equalsIgnoreCase("EOS")) {
            return "2";
        }
        if (validity.equalsIgnoreCase("GTD")) {
            return "3";
        }
        if (validity.equalsIgnoreCase("GTC")) {
            return "4";
        }
        return "";
    }

    //TODO :Date time converter

    private void createPositionsRow(String key, String value) {
        int color;
        //int color = (changeColor) ? R.color.market_grey_light : R.color.market_grey_dark;

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            color = (changeColor) ? R.color.floatingBgColor : R.color.backgroundStripColorWhite;
        } else {
            color = (changeColor) ? R.color.market_grey_dark : R.color.market_grey_light;
        }


        TableRow Row = new TableRow(getMainActivity());
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(key);
        keyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = (TableRow.LayoutParams) keyView.getLayoutParams();
        params.weight = 1;
        params.bottomMargin = 1;
        keyView.setPadding(10, 10, 10, 10);

        GreekTextView valueView = new GreekTextView(getMainActivity());
        valueView.setPadding(10, 12, 5, 12);

        valueView.setText(value);


        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
        valueView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
        valueView.setBackgroundColor(getResources().getColor(color));

        valueView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params1 = (TableRow.LayoutParams) valueView.getLayoutParams();
        params1.weight = 1;
        params1.bottomMargin = 1;

        Row.addView(keyView);
        Row.addView(valueView);
        orderDetails.addView(Row);

        changeColor = !changeColor;
    }

    private void cancelTheOrder() {
        showProgress();
        Util.updateValidationTime(getMainActivity());
        //TradeCancelOrderRequest.sendRequest(orderBookData.getAction(), orderBookData.getClientCode(), orderBookData.getExchange(), orderBookData.getUniqueOrderID(), AccountDetails.getToken(getMainActivity()), orderBookData.getTradeSymbol(), getMainActivity(), serviceResponseHandler);
        TradeCancelOrderRequest cancelRequest = new TradeCancelOrderRequest();
        cancelRequest.setClientCode(AccountDetails.getClientCode(getMainActivity()));
        cancelRequest.setDisQty(orderBookData.getDiscQty());
        cancelRequest.setExchange(getExchange(orderBookData.getToken()));
        cancelRequest.setLot(orderBookData.getLotSize());
        cancelRequest.setOrderType(modifyOrderType); //
        cancelRequest.setPrice(orderBookData.getPrice());
        cancelRequest.setProduct(orderBookData.getProduct());
        if (getExchange(orderBookData.getToken()).equalsIgnoreCase("MCX")) {
            int qty = Integer.parseInt(orderBookData.getPendingQty());
            cancelRequest.setQty(String.valueOf(qty));

        } else {
            int qty = Integer.parseInt(orderBookData.getPendingQty());
            //int qty = Integer.parseInt(orderBookData.getPendingQty()) / Integer.parseInt(orderBookData.getLotSize());
            cancelRequest.setQty(String.valueOf(qty));

        }

        cancelRequest.setStrToken(orderBookData.getToken());
        cancelRequest.setTradeSymbol(orderBookData.getTradeSymbol());
        cancelRequest.setTriggerPrice(orderBookData.getTrigPrice());
        cancelRequest.setValidity(getValidity(modifyOrderLife));
        cancelRequest.setSide(orderBookData.getAction());
        cancelRequest.setLu_time_exchange(orderBookData.getOrdModTime());
        cancelRequest.setPending_disclosed_qty("1");
        cancelRequest.setQty_filled_today(orderBookData.getTrdQty());
        cancelRequest.setPending_qty(orderBookData.getPendingQty());
        cancelRequest.setGorderid(orderBookData.getUniqueID());
        cancelRequest.setEorderid(orderBookData.getUniqueOrderID());
        cancelRequest.setLexchangeOrderNo1(orderBookData.getOrdID());
        cancelRequest.setGtdExpiry(Long.valueOf(orderBookData.getLgoodtilldate()));

        if ((orderBookData.getStatus().toLowerCase()).contains("unconfirmed")) {

            if (orderBookData.getOtype().equalsIgnoreCase("6")) {
                cancelRequest.setAmo("1");
                cancelRequest.setOffline("0");
            } else if (orderBookData.getOtype().equalsIgnoreCase("5")) {
                cancelRequest.setOffline("1");
                cancelRequest.setAmo("0");
            }
        } else {
            cancelRequest.setAmo("0");
            cancelRequest.setOffline("0");
        }

        if (isPreOpen_status) {
            cancelRequest.setIsPreOpen("1");
        } else {
            cancelRequest.setIsPreOpen("0");
        }

        if (isPostOpen_status) {
            cancelRequest.setIsPostClosed("1");
        } else {
            cancelRequest.setIsPostClosed("0");
        }
        if (AccountDetails.isOrderServerAuthenticated()) {
            orderStreamingController.sendStreamingCancelOrderRequest(getActivity(), cancelRequest);
            cancelBtn.setEnabled(false);
            modifyBtn.setEnabled(false);
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.OR_ORDER_CANCEL_MSG), "Ok", false, null);
            cancelBtn.setVisibility(View.GONE);
            modifyBtn.setVisibility(View.GONE);
        } else {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "We are facing Network congestion.Kindly try after sometime", "Ok", false, null);
            cancelBtn.setVisibility(View.GONE);
        }

        hideProgress();
    }


    @Override
    public void onFragmentResume() {

        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_ORDER_BOOK_DETAILS_SCREEN;
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        AccountDetails.setToastCount(0);
    }


    public void onEventMainThread(MarketStatusResponse response) {
        try {
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

                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("9")) {

                    AccountDetails.mcx_com_status = true;
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
            /*MarketStatusPostRequest.sendRequest(AccountDetails.getUsername(getMainActivity()),
                    getMainActivity(), serviceResponseHandler);*/


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        JSONResponse jsonResponse = (JSONResponse) response;

        if ("getMarketStatus".equals(jsonResponse.getServiceName())) {
            try {
                MarketStatusPostResponse marketStatusPostResponse = (MarketStatusPostResponse) jsonResponse.getResponse();
                List<MarketStatusResponse> statusResponse = marketStatusPostResponse.getStatusResponse();
                updateMarketStatus(statusResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            if (AccountDetails.allowedmarket_nse) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = true;
                    nse_eq = "green";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "yellow";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = true;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "blue";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "pink";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                }
            }

            //FOR FNO
            /*  int fo = 0;*/
            if (AccountDetails.allowedmarket_nfo) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    nse_fno = "green";
                    fno = true;
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "yellow";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = true;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "blue";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "pink";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                }
            }
            //FOR CURRENCY

            /* int cd = 0;*/
            if (AccountDetails.allowedmarket_ncd) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    cd = true;
                    nse_cd = "green";
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "yellow";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = true;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "blue";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "pink";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                }
            }

            if (AccountDetails.allowedmarket_mcx) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "green";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "yellow";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = true;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "blue";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "pink";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;
                }
            }

            if (AccountDetails.allowedmarket_bse) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bseeq = true;
                    bse_eq = "green";
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "yellow";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = true;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "blue";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "pink";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                    bseeq = false;

                }

            }

            if (AccountDetails.allowedmarket_bfo) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bsefno = true;
                    bse_fno = "green";
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    bsefno = false;
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "yellow";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = true;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "blue";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "pink";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                }

            }

            if (AccountDetails.allowedmarket_bcd) {

                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bsecd = true;
                    bse_cd = "green";
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "yellow";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = true;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "blue";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "pink";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;
                }
            }

            if (AccountDetails.allowedmarket_ncdex) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "green";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "yellow";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = true;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "blue";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "pink";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                }
            }


            if (Integer.parseInt(hm.get(nse_eq)) <= Integer.parseInt(hm.get(bse_eq))) {
                //Log.d("color",hm.get(nse_eq)+"-----"+hm.get(bse_eq));
               // actionBar.changeStatus("eq", nse_eq);
            } else if (Integer.parseInt(hm.get(bse_eq)) <= Integer.parseInt(hm.get(nse_eq))) {
                //actionBar.changeStatus("eq", bse_eq);
            }

            if (Integer.parseInt(hm.get(nse_fno)) <= Integer.parseInt(hm.get(bse_fno))) {
               // actionBar.changeStatus("fno", nse_fno);
            } else if (Integer.parseInt(hm.get(bse_fno)) <= Integer.parseInt(hm.get(nse_fno))) {
//                actionBar.changeStatus("fno", bse_fno);
            }

            if (Integer.parseInt(hm.get(nse_cd)) <= Integer.parseInt(hm.get(bse_cd))) {
//                actionBar.changeStatus("cd", nse_cd);
            } else if (Integer.parseInt(hm.get(bse_cd)) <= Integer.parseInt(hm.get(nse_cd))) {
//                actionBar.changeStatus("cd", bse_cd);
            }

            if (Integer.parseInt(hm.get(mcx_com)) <= Integer.parseInt(hm.get(ncdex_com))) {
//                actionBar.changeStatus("com", mcx_com);
            } else if (Integer.parseInt(hm.get(ncdex_com)) <= Integer.parseInt(hm.get(mcx_com))) {
//                actionBar.changeStatus("com", ncdex_com);
            }
        }

    }


    public static String removetillword(String status, String word) {
        if (status.indexOf(word) != -1) {
            return status.substring(status.indexOf(word));
        } else {
            return status;
        }
    }

}
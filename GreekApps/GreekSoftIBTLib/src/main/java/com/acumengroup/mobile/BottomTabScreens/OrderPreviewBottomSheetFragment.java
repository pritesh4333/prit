package com.acumengroup.mobile.BottomTabScreens;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.MarketStatusPostResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.OrderBookResponses;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.core.widget.NestedScrollView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.streamerbroadcast.MarketStatusResponse;
import com.acumengroup.greekmain.core.model.tradecancelorder.TradeCancelOrderRequest;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.reports.BitWiseOperation;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;


public class OrderPreviewBottomSheetFragment extends BottomSheetDialogFragment implements GreekUIServiceHandler {


    private OrderBookResponses.OrdersBooksData orderBookData;
    private View orderBookDetailsView;
    private GreekButton modifyBtn, cancelBtn;
    private String orderType = "";
    private LinearLayout hederLayout;
    private NestedScrollView bottom_sheet_layout;
    OrderStreamingController orderStreamingController;
    LinearLayout orderDetails;
    private Boolean changeColor = true;
    private Boolean isPreOpen_status = false;
    private Boolean isPostOpen_status = false;
    private String modifyOrderType;
    private String modifyOrderLife;
    private String From = "";
    private boolean valid_status;
    private static CountDownTimer countDownTimer;
    private GreekTextView symbolnametxt,
            exchangetxt, exchangetxt1, exchangetxt2, exchangetxt3,
            tradedQtytxt, tradedQtytxt1, tradedQtytxt2, tradedQtytxt3,
            tradedPricetxt, tradedPricetxt1, tradedPricetxt2, tradedPricetxt3,
            pendingQyttxt, pendingQyttxt1, pendingQyttxt2, pendingQyttxt3,
            orderqtytxt, orderqtytxt1, orderqtytxt2, orderqtytxt3,
            orderPricetxt, orderPricetxt1, orderPricetxt2, orderPricetxt3,
            orderStatus, orderStatus1, orderStatus2, orderStatus3,
            triggerPricetxt, triggerPricetxt1, triggerPricetxt2, triggerPricetxt3,
            orderTypetxt, orderTypetxt1, orderTypetxt2, orderTypetxt3,
            productTypetxt, productTypetxt1, productTypetxt2, productTypetxt3,
            validityTypetxt, validityTypetxt1, validityTypetxt2, validityTypetxt3,
            disclosedQtytxt, disclosedQtytxt1, disclosedQtytxt2, disclosedQtytxt3,
            orderplacedtxt, orderplacedtxt1, orderplacedtxt2, orderplacedtxt3,
            ordernotxt, exchangeordernotxt;
    private GreekTextView txt_name_eon, txt_name_on, txt_name_dq, txt_name_ordtyp, txt_name_pt, txt_name_validity, txt_name_trip, txt_name_op, txt_name_oq, txt_name_pq, txt_name_tp, txt_name_tdq, txt_name_os, txt_name_opb;

    public String nse_eq = "red", bse_eq = "red", nse_fno = "red", bse_fno = "red", nse_cd = "red", bse_cd = "red", mcx_com = "red", ncdex_com = "red";
    boolean eq, bseeq = false;
    boolean fno, bsefno = false;
    boolean cd, bsecd = false;


    public ServiceResponseHandler serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);

    private LinearLayout linearLayout1, linearLayout2, linearLayout3, footer_buttons;

    private final View.OnClickListener modifyClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            switch (orderType) {
                case "OrderBook":
                    if (orderBookData.getTag() == null || orderBookData.getTag().trim().equalsIgnoreCase("")) {
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
                                    bundle.putString("TriggerPrice", String.format("%.4f", Double.parseDouble(orderBookData.getDSLTPrice())));
                                    bundle.putString("AssetType", "currency");
                                } else {
                                    bundle.putString("Price", String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));
                                    bundle.putString("TriggerPrice", String.format("%.2f", Double.parseDouble(orderBookData.getDSLTPrice())));
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
                        bundle.putString("strategyid", orderBookData.getIStrategyId());
                        bundle.putString("stoplossprice", orderBookData.getDSLPrice());
                        bundle.putString("targetprice", orderBookData.getDTargetPrice());
                        bundle.putString("iomruleno", orderBookData.getLIOMRuleNo());
                        bundle.putString("sltriggerprice", orderBookData.getDSLTPrice());

                        if (orderBookData.getOtype().equalsIgnoreCase("5")) {

                            bundle.putString("otype", "offline");
                        } else if (orderBookData.getOtype().equalsIgnoreCase("6")) {

                            bundle.putString("otype", "amo");
                        } else {
                            bundle.putString("otype", "normal");
                        }

                        getMarketstatus();
                        //Check market status for post close after conformation
                        AccountDetails.globalPlaceOrderBundle = bundle;
                        EventBus.getDefault().post("placeorder");
                        dismiss();
                        break;

                    } else if (orderBookData.getTag() != null && orderBookData.getTag().equalsIgnoreCase("ageing")) {
                        GreekDialog.alertDialog(getActivity(), 0, GREEK, "Modify Not Allowed For Ageing Order", "Ok", false, null);
                    }
            }
        }
    };


    private final View.OnClickListener cancelClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (orderBookData.getTag() == null || orderBookData.getTag().trim().equalsIgnoreCase("")) {

                cancelBtn.setEnabled(false);
                modifyBtn.setEnabled(false);
                cancelBtn.setVisibility(View.GONE);
                modifyBtn.setVisibility(View.GONE);
                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Do you want to cancel the order?", "Yes", "No", true, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        if (action == GreekDialog.Action.OK) {
                            cancelTheOrder();

                        } else {
                            dismiss();
                        }
                    }
                });

            } else if (orderBookData.getTag() != null && orderBookData.getTag().equalsIgnoreCase("ageing")) {
                GreekDialog.alertDialog(getActivity(), 0, GREEK, "Cancellation Not Allowed For Ageing Order", "Ok", false, null);
            }
        }
    };


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        orderBookDetailsView = inflater.inflate(R.layout.fragment_order_preview, container, false);
        orderStreamingController = new OrderStreamingController();
        orderDetails = orderBookDetailsView.findViewById(R.id.order_details);
        setupView();
        // MarketStatusPostRequest.sendRequest(AccountDetails.getUsername(getActivity()), getActivity(), serviceResponseHandler);
        return orderBookDetailsView;
    }


    private void setupView() {
        hederLayout = orderBookDetailsView.findViewById(R.id.hederLayout);
        bottom_sheet_layout = orderBookDetailsView.findViewById(R.id.bottom_sheet_layout);
        symbolnametxt = orderBookDetailsView.findViewById(R.id.symbolnametxt);
        exchangetxt = orderBookDetailsView.findViewById(R.id.exchangetxt);

        exchangetxt1 = orderBookDetailsView.findViewById(R.id.exchangetxt1);
        exchangetxt2 = orderBookDetailsView.findViewById(R.id.exchangetxt2);
        exchangetxt3 = orderBookDetailsView.findViewById(R.id.exchangetxt3);

        linearLayout1 = orderBookDetailsView.findViewById(R.id.leg1);
        linearLayout2 = orderBookDetailsView.findViewById(R.id.leg2);
        linearLayout3 = orderBookDetailsView.findViewById(R.id.leg3);


        footer_buttons = orderBookDetailsView.findViewById(R.id.footer_buttons);


        tradedQtytxt = orderBookDetailsView.findViewById(R.id.tradedQtytxt);
        tradedQtytxt1 = orderBookDetailsView.findViewById(R.id.tradedQtytxt1);
        tradedQtytxt2 = orderBookDetailsView.findViewById(R.id.tradedQtytxt2);
        tradedQtytxt3 = orderBookDetailsView.findViewById(R.id.tradedQtytxt3);


        tradedPricetxt = orderBookDetailsView.findViewById(R.id.tradedPricetxt);
        tradedPricetxt1 = orderBookDetailsView.findViewById(R.id.tradedPricetxt1);
        tradedPricetxt2 = orderBookDetailsView.findViewById(R.id.tradedPricetxt2);
        tradedPricetxt3 = orderBookDetailsView.findViewById(R.id.tradedPricetxt3);


        pendingQyttxt = orderBookDetailsView.findViewById(R.id.pendingQyttxt);
        pendingQyttxt1 = orderBookDetailsView.findViewById(R.id.pendingQyttxt1);
        pendingQyttxt2 = orderBookDetailsView.findViewById(R.id.pendingQyttxt2);
        pendingQyttxt3 = orderBookDetailsView.findViewById(R.id.pendingQyttxt3);


        orderqtytxt = orderBookDetailsView.findViewById(R.id.orderqtytxt);
        orderqtytxt1 = orderBookDetailsView.findViewById(R.id.orderqtytxt1);
        orderqtytxt2 = orderBookDetailsView.findViewById(R.id.orderqtytxt2);
        orderqtytxt3 = orderBookDetailsView.findViewById(R.id.orderqtytxt3);


        orderPricetxt = orderBookDetailsView.findViewById(R.id.orderPricetxt);
        orderPricetxt1 = orderBookDetailsView.findViewById(R.id.orderPricetxt1);
        orderPricetxt2 = orderBookDetailsView.findViewById(R.id.orderPricetxt2);
        orderPricetxt3 = orderBookDetailsView.findViewById(R.id.orderPricetxt3);


        orderStatus = orderBookDetailsView.findViewById(R.id.orderStatus);
        orderStatus1 = orderBookDetailsView.findViewById(R.id.orderStatus1);
        orderStatus2 = orderBookDetailsView.findViewById(R.id.orderStatus2);
        orderStatus3 = orderBookDetailsView.findViewById(R.id.orderStatus3);


        triggerPricetxt = orderBookDetailsView.findViewById(R.id.triggerPricetxt);
        triggerPricetxt1 = orderBookDetailsView.findViewById(R.id.triggerPricetxt1);
        triggerPricetxt2 = orderBookDetailsView.findViewById(R.id.triggerPricetxt2);
        triggerPricetxt3 = orderBookDetailsView.findViewById(R.id.triggerPricetxt3);


        orderTypetxt = orderBookDetailsView.findViewById(R.id.orderTypetxt);
        orderTypetxt1 = orderBookDetailsView.findViewById(R.id.orderTypetxt1);
        orderTypetxt2 = orderBookDetailsView.findViewById(R.id.orderTypetxt2);
        orderTypetxt3 = orderBookDetailsView.findViewById(R.id.orderTypetxt3);

        productTypetxt = orderBookDetailsView.findViewById(R.id.productTypetxt);
        productTypetxt1 = orderBookDetailsView.findViewById(R.id.productTypetxt1);
        productTypetxt2 = orderBookDetailsView.findViewById(R.id.productTypetxt2);
        productTypetxt3 = orderBookDetailsView.findViewById(R.id.productTypetxt3);


        validityTypetxt = orderBookDetailsView.findViewById(R.id.validityTypetxt);
        validityTypetxt1 = orderBookDetailsView.findViewById(R.id.validityTypetxt1);
        validityTypetxt2 = orderBookDetailsView.findViewById(R.id.validityTypetxt2);
        validityTypetxt3 = orderBookDetailsView.findViewById(R.id.validityTypetxt3);


        disclosedQtytxt = orderBookDetailsView.findViewById(R.id.disclosedQtytxt);
        disclosedQtytxt1 = orderBookDetailsView.findViewById(R.id.disclosedQtytxt1);
        disclosedQtytxt2 = orderBookDetailsView.findViewById(R.id.disclosedQtytxt2);
        disclosedQtytxt3 = orderBookDetailsView.findViewById(R.id.disclosedQtytxt3);

        orderplacedtxt = orderBookDetailsView.findViewById(R.id.orderplacedtxt);
        orderplacedtxt1 = orderBookDetailsView.findViewById(R.id.orderplacedtxt1);
        orderplacedtxt2 = orderBookDetailsView.findViewById(R.id.orderplacedtxt2);
        orderplacedtxt3 = orderBookDetailsView.findViewById(R.id.orderplacedtxt3);

        txt_name_eon = orderBookDetailsView.findViewById(R.id.txt_name_eon);
        txt_name_on = orderBookDetailsView.findViewById(R.id.txt_name_on);
        txt_name_dq = orderBookDetailsView.findViewById(R.id.txt_name_dq);
        txt_name_ordtyp = orderBookDetailsView.findViewById(R.id.txt_name_ordtyp);
        txt_name_pt = orderBookDetailsView.findViewById(R.id.txt_name_pt);
        txt_name_validity = orderBookDetailsView.findViewById(R.id.txt_name_validity);
        txt_name_trip = orderBookDetailsView.findViewById(R.id.txt_name_trip);
        txt_name_op = orderBookDetailsView.findViewById(R.id.txt_name_op);
        txt_name_oq = orderBookDetailsView.findViewById(R.id.txt_name_oq);
        txt_name_pq = orderBookDetailsView.findViewById(R.id.txt_name_pq);
        txt_name_tp = orderBookDetailsView.findViewById(R.id.txt_name_tp);
        txt_name_tdq = orderBookDetailsView.findViewById(R.id.txt_name_tdq);
        txt_name_os = orderBookDetailsView.findViewById(R.id.txt_name_os);
        txt_name_opb = orderBookDetailsView.findViewById(R.id.txt_name_opb);


        ordernotxt = orderBookDetailsView.findViewById(R.id.ordernotxt);


        exchangeordernotxt = orderBookDetailsView.findViewById(R.id.exchangeordernotxt);


        modifyBtn = orderBookDetailsView.findViewById(R.id.modify_Order_btn);
        modifyBtn.setOnClickListener(modifyClickListener);

        cancelBtn = orderBookDetailsView.findViewById(R.id.cancel_Order_btn);
        cancelBtn.setOnClickListener(cancelClickListener);


        parseReceivedArguments();
        setTheme();
    }


    private void setTheme() {


        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            hederLayout.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            linearLayout1.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            linearLayout2.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            linearLayout3.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            bottom_sheet_layout.setBackgroundColor(getResources().getColor(R.color.white));
            footer_buttons.setBackgroundColor(getResources().getColor(R.color.white));
            symbolnametxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangetxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangetxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangetxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            orderTypetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderTypetxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderTypetxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderTypetxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


            tradedQtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tradedQtytxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tradedQtytxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tradedQtytxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            tradedPricetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tradedPricetxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tradedPricetxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tradedPricetxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            pendingQyttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pendingQyttxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pendingQyttxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pendingQyttxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            orderqtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderqtytxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderqtytxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderqtytxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            productTypetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            productTypetxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            productTypetxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            productTypetxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            validityTypetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            validityTypetxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            validityTypetxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            validityTypetxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            disclosedQtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            disclosedQtytxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            disclosedQtytxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            disclosedQtytxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            orderplacedtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderplacedtxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderplacedtxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderplacedtxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            ordernotxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangeordernotxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            txt_name_eon.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_on.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_dq.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_ordtyp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_pt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_validity.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_trip.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_op.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_oq.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_pq.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_tp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_tdq.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            orderStatus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderStatus1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderStatus2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderStatus3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


            txt_name_os.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            triggerPricetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            triggerPricetxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            triggerPricetxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            triggerPricetxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderPricetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderPricetxt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderPricetxt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderPricetxt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_name_opb.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        }
    }

    private void setLegsOrderDetails() {
        if (orderBookData.getLegInfo() != null && orderBookData.getLegInfo().size() > 0) {

            for (int i = 0; i < orderBookData.getLegInfo().size(); i++) {

                if (i == 0) {

                    tradedQtytxt.setText(orderBookData.getLegInfo().get(i).getLVolumeFilledToday());
                    pendingQyttxt.setText(orderBookData.getLegInfo().get(i).getLTotalVolRemaining());
                    disclosedQtytxt.setText(orderBookData.getLegInfo().get(i).getLDisclosedVol());
                    orderqtytxt.setText(orderBookData.getLegInfo().get(i).getLVolume());


                    orderPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));

                    if (Integer.parseInt(orderBookData.getLegInfo().get(i).getLVolumeFilledToday()) > 0) {
                        tradedPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));
                    }

                    triggerPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDTriggerPrice())));


                    orderTypetxt.setText(getOrderType(orderBookData.getLegInfo().get(i).getIOrderType()));
                    productTypetxt.setText(getProductType(orderBookData.getLegInfo().get(i).getIProductType()));
                    validityTypetxt.setText(getOrderValidity(orderBookData.getLegInfo().get(i).getIOrderFlags()));
                    orderStatus.setText(orderBookData.getStatus());
                    orderplacedtxt.setText(orderBookData.getOrdTime());
                    exchangetxt.setText(orderBookData.getLegInfo().get(i).getDescription());

                }

                if (i == 1) {

                    tradedQtytxt1.setVisibility(View.VISIBLE);
                    pendingQyttxt1.setVisibility(View.VISIBLE);
                    disclosedQtytxt1.setVisibility(View.VISIBLE);
                    orderqtytxt1.setVisibility(View.VISIBLE);
                    orderPricetxt1.setVisibility(View.VISIBLE);
                    tradedPricetxt1.setVisibility(View.VISIBLE);
                    triggerPricetxt1.setVisibility(View.VISIBLE);
                    orderTypetxt1.setVisibility(View.VISIBLE);
                    productTypetxt1.setVisibility(View.VISIBLE);
                    validityTypetxt1.setVisibility(View.VISIBLE);
                    orderplacedtxt1.setVisibility(View.VISIBLE);
                    orderStatus1.setVisibility(View.VISIBLE);
                    exchangetxt1.setVisibility(View.VISIBLE);
                    linearLayout1.setVisibility(View.VISIBLE);

                    tradedQtytxt1.setText(orderBookData.getLegInfo().get(i).getLVolumeFilledToday());
                    pendingQyttxt1.setText(orderBookData.getLegInfo().get(i).getLTotalVolRemaining());
                    disclosedQtytxt1.setText(orderBookData.getLegInfo().get(i).getLDisclosedVol());
                    orderqtytxt1.setText(orderBookData.getLegInfo().get(i).getLVolume());

                    orderPricetxt1.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));

                    if (Integer.parseInt(orderBookData.getLegInfo().get(i).getLVolumeFilledToday()) > 0) {
                        tradedPricetxt1.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));
                    }


                    triggerPricetxt1.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDTriggerPrice())));


                    orderTypetxt1.setText(getOrderType(orderBookData.getLegInfo().get(i).getIOrderType()));
                    productTypetxt1.setText(getProductType(orderBookData.getLegInfo().get(i).getIProductType()));
                    validityTypetxt1.setText(getOrderValidity(orderBookData.getLegInfo().get(i).getIOrderFlags()));
                    orderStatus1.setText(orderBookData.getStatus());
                    orderplacedtxt1.setText(orderBookData.getOrdTime());
                    exchangetxt1.setText(orderBookData.getLegInfo().get(i).getDescription());


                }

                if (i == 2) {


                    tradedQtytxt2.setVisibility(View.VISIBLE);
                    pendingQyttxt2.setVisibility(View.VISIBLE);
                    disclosedQtytxt2.setVisibility(View.VISIBLE);
                    orderqtytxt2.setVisibility(View.VISIBLE);
                    orderPricetxt2.setVisibility(View.VISIBLE);
                    tradedPricetxt2.setVisibility(View.VISIBLE);
                    triggerPricetxt2.setVisibility(View.VISIBLE);
                    orderTypetxt2.setVisibility(View.VISIBLE);
                    productTypetxt2.setVisibility(View.VISIBLE);
                    validityTypetxt2.setVisibility(View.VISIBLE);
                    orderplacedtxt2.setVisibility(View.VISIBLE);
                    orderStatus2.setVisibility(View.VISIBLE);
                    exchangetxt2.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.VISIBLE);


                    tradedQtytxt2.setText(orderBookData.getLegInfo().get(i).getLVolumeFilledToday());
                    pendingQyttxt2.setText(orderBookData.getLegInfo().get(i).getLTotalVolRemaining());
                    disclosedQtytxt2.setText(orderBookData.getLegInfo().get(i).getLDisclosedVol());
                    orderqtytxt2.setText(orderBookData.getLegInfo().get(i).getLVolume());


                    orderPricetxt2.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));

                    if (Integer.parseInt(orderBookData.getLegInfo().get(i).getLVolumeFilledToday()) > 0) {
                        tradedPricetxt2.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));
                    }

                    triggerPricetxt2.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDTriggerPrice())));


                    orderTypetxt2.setText(getOrderType(orderBookData.getLegInfo().get(i).getIOrderType()));
                    productTypetxt2.setText(getProductType(orderBookData.getLegInfo().get(i).getIProductType()));
                    validityTypetxt2.setText(getOrderValidity(orderBookData.getLegInfo().get(i).getIOrderFlags()));
                    orderStatus2.setText(orderBookData.getStatus());
                    orderplacedtxt2.setText(orderBookData.getOrdTime());
                    exchangetxt2.setText(orderBookData.getLegInfo().get(i).getDescription());


                }

                if (i == 3) {

                    tradedQtytxt3.setVisibility(View.VISIBLE);
                    pendingQyttxt3.setVisibility(View.VISIBLE);
                    disclosedQtytxt3.setVisibility(View.VISIBLE);
                    orderqtytxt3.setVisibility(View.VISIBLE);
                    orderPricetxt3.setVisibility(View.VISIBLE);
                    tradedPricetxt3.setVisibility(View.VISIBLE);
                    triggerPricetxt3.setVisibility(View.VISIBLE);
                    orderTypetxt3.setVisibility(View.VISIBLE);
                    productTypetxt3.setVisibility(View.VISIBLE);
                    validityTypetxt3.setVisibility(View.VISIBLE);
                    orderplacedtxt3.setVisibility(View.VISIBLE);
                    orderStatus3.setVisibility(View.VISIBLE);
                    exchangetxt3.setVisibility(View.VISIBLE);
                    linearLayout3.setVisibility(View.VISIBLE);


                    tradedQtytxt3.setText(orderBookData.getLegInfo().get(i).getLVolumeFilledToday());
                    pendingQyttxt3.setText(orderBookData.getLegInfo().get(i).getLTotalVolRemaining());
                    disclosedQtytxt3.setText(orderBookData.getLegInfo().get(i).getLDisclosedVol());
                    orderqtytxt3.setText(orderBookData.getLegInfo().get(i).getLVolume());


                    orderPricetxt3.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));

                    if (Integer.parseInt(orderBookData.getLegInfo().get(i).getLVolumeFilledToday()) > 0) {
                        tradedPricetxt3.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDPrice())));
                    }

                    triggerPricetxt3.setText(String.format("%.2f", Double.parseDouble(orderBookData.getLegInfo().get(i).getDTriggerPrice())));


                    orderTypetxt3.setText(getOrderType(orderBookData.getLegInfo().get(i).getIOrderType()));
                    productTypetxt3.setText(getProductType(orderBookData.getLegInfo().get(i).getIProductType()));
                    validityTypetxt3.setText(getOrderValidity(orderBookData.getLegInfo().get(i).getIOrderFlags()));
                    orderStatus3.setText(orderBookData.getStatus());
                    orderplacedtxt3.setText(orderBookData.getOrdTime());
                    exchangetxt3.setText(orderBookData.getLegInfo().get(i).getDescription());

                }

            }
        }

    }

    private void parseReceivedArguments() {
        orderType = AccountDetails.globalPlaceOrderBundle.getString("Type");
        From = AccountDetails.globalPlaceOrderBundle.getString("From");

        orderType = (orderType == null) ? "" : orderType;

        switch (orderType) {
            case "OrderBook":
                String response = getArguments().getString("response");

                orderBookData = new Gson().fromJson(response, OrderBookResponses.OrdersBooksData.class);

                orderStatus.setText(orderBookData.getStatus());
                exchangeordernotxt.setText(orderBookData.getUniqueOrderID());


                if (orderBookData.getLegInfo() != null && orderBookData.getLegInfo().size() > 0) {

                    setLegsOrderDetails();

                } else {
                    if (orderBookData.getStatus().equalsIgnoreCase("RMS Rejected")) {

                        if (getExchange(orderBookData.getToken()).equalsIgnoreCase("MCX")) {

                            if (orderBookData.getOptionType().equalsIgnoreCase("XX")) {
                                symbolnametxt.setText(orderBookData.getDescription());
                                exchangetxt.setText(getExchange(orderBookData.getToken()));
                                //createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + orderBookData.getInstrument());
                            } else {
                                symbolnametxt.setText(orderBookData.getDescription());
                                exchangetxt.setText(getExchange(orderBookData.getToken()));
                                //createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + orderBookData.getStrikePrice() + orderBookData.getOptionType() + "-" + orderBookData.getInstrument());
                            }


                        } else {
                            //createPositionsRow("Symbol", ":\t" + orderBookData.getDescription() + " - " + orderBookData.getInstrument());
                            symbolnametxt.setText(orderBookData.getDescription());
                            exchangetxt.setText(getExchange(orderBookData.getToken()));
                        }

                        ordernotxt.setText(orderBookData.getUniqueID());
                        orderplacedtxt.setText(orderBookData.getOrdTime());

                        orderqtytxt.setText(String.valueOf(Integer.parseInt(orderBookData.getQty())));

                        triggerPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getTrigPrice())));

                        orderPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));

                        BitWiseOperation bt = new BitWiseOperation();
                        if (Integer.parseInt(orderBookData.getLgoodtilldate()) > 0) {
                            //createPositionsRow("Order Life", ":\t" + "GTD");
                            validityTypetxt.setText("GTD");
                            modifyOrderLife = "GTD";
                        } else if (bt.getDayResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            //createPositionsRow("Order Life", ":\t" + "Day");
                            validityTypetxt.setText("DAY");
                            modifyOrderLife = "Day";
                        } else if (bt.getIOCResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            //createPositionsRow("Order Life", ":\t" + "IOC");
                            validityTypetxt.setText("IOC");
                            modifyOrderLife = "IOC";
                        } else if (bt.getGTCResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            //createPositionsRow("Order Life", ":\t" + "GTC");
                            validityTypetxt.setText("GTC");
                            modifyOrderLife = "GTC";
                        } else if (bt.getEOSResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            // createPositionsRow("Order Life", ":\t" + "EOS");.
                            validityTypetxt.setText("EOS");
                            modifyOrderLife = "EOS";
                        }

                        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                            if (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(orderBookData.getProduct())) {
                                productTypetxt.setText(AccountDetails.getAllowedProduct().get(i).getcProductName().toUpperCase());
                            }
                        }

                        if (orderBookData.getIStrategyId() != null && orderBookData.getIStrategyId().equalsIgnoreCase("50")) {
                            orderTypetxt.setText("BRACKET");
                            //createPositionsRow("Order Type", ":\t" + "Bracket");
                            modifyOrderType = "7";
                        } else {
                            if (orderBookData.getBookType() != null && orderBookData.getBookType().equalsIgnoreCase("1")) {
                                if (orderBookData.getOrderFlags() != null) {
                                    if (bt.getBitResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                                        if (Double.parseDouble(orderBookData.getPrice()) == 0.00) {
                                            orderTypetxt.setText("MARKET");
                                            //createPositionsRow("Order Type", ":\t" + "Market");
                                            modifyOrderType = "2";
                                        } else {
                                            orderTypetxt.setText("LIMIT");
                                            //createPositionsRow("Order Type", ":\t" + "Limit");
                                            modifyOrderType = "1";
                                        }
                                    } else {
                                        orderTypetxt.setText("LIMIT");
                                        //createPositionsRow("Order Type", ":\t" + "Limit");
                                        modifyOrderType = "1";
                                    }
                                }
                            } else if (orderBookData.getBookType() != null && orderBookData.getBookType().equalsIgnoreCase("3")) {
                                if (bt.getBitResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                                    orderTypetxt.setText("STOPLOSS MARKET");
                                    //createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                    modifyOrderType = "4";
                                } else {
                                    modifyOrderType = "3";
                                    if (Double.parseDouble(orderBookData.getPrice()) == 0.00 && modifyOrderType.equalsIgnoreCase("3")) {
                                        orderTypetxt.setText("STOPLOSS MARKET");
                                        //createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                        modifyOrderType = "4";
                                    } else {
                                        orderTypetxt.setText("STOPLOSS");
                                        //createPositionsRow("Order Type", ":\t" + "Stop Loss");
                                        modifyOrderType = "3";
                                    }
                                }
                            }else {
                                orderTypetxt.setText("LIMIT");
                                 modifyOrderType = "1";
                            }
                        }


                    } else {

                        if (orderBookData.getToken() != null && getExchange(orderBookData.getToken()).equalsIgnoreCase("MCX")) {

                            if (orderBookData.getOptionType() != null && orderBookData.getOptionType().equalsIgnoreCase("XX")) {
                                symbolnametxt.setText(orderBookData.getDescription());
                                exchangetxt.setText(getExchange(orderBookData.getToken()));
                                //createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + orderBookData.getInstrument());
                            } else {
                                symbolnametxt.setText(orderBookData.getDescription());
                                exchangetxt.setText(getExchange(orderBookData.getToken()));
                                //createPositionsRow("Symbol", ":\t" + orderBookData.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() +orderBookData.getStrikePrice()+orderBookData.getOptionType()+ "-" + orderBookData.getInstrument());
                            }

                        } else {
                            symbolnametxt.setText(orderBookData.getDescription());
                            exchangetxt.setText(getExchange(orderBookData.getToken()));
                            //createPositionsRow("Symbol", ":\t" + orderBookData.getDescription() + " - " + orderBookData.getInstrument());
                        }

                        ordernotxt.setText(orderBookData.getUniqueID());
                        exchangeordernotxt.setText(orderBookData.getUniqueOrderID());
                        /*if (orderBookData.getProduct().equalsIgnoreCase("1")) {
                            productTypetxt.setText("INTRADAY");
                            //createPositionsRow("Product Type", ":\t" + "Intraday");
                        } else if (orderBookData.getProduct().equalsIgnoreCase("0")) {
                            productTypetxt.setText("DELIVERY");
                            //createPositionsRow("Product Type", ":\t" + "Delivery");
                        } else if (orderBookData.getProduct().equalsIgnoreCase("2")) {
                            productTypetxt.setText("MTF");
                            //createPositionsRow("Product Type", ":\t" + "MTF");
                        } else if (orderBookData.getProduct().equalsIgnoreCase("5")) {
                            productTypetxt.setText("SSEQ");
                            //createPositionsRow("Product Type", ":\t" + "SSEQ");
                        } else if (orderBookData.getProduct().equalsIgnoreCase("3")) {
                            productTypetxt.setText("TNC");
                            //createPositionsRow("Product Type", ":\t" + "SSEQ");
                        } else if (orderBookData.getProduct().equalsIgnoreCase("4")) {
                            productTypetxt.setText("CATALYST");
                            //createPositionsRow("Product Type", ":\t" + "SSEQ");
                        }*/

                        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                            if (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(orderBookData.getProduct())) {
                                productTypetxt.setText(AccountDetails.getAllowedProduct().get(i).getcProductName().toUpperCase());
                            }
                        }


                        BitWiseOperation bt = new BitWiseOperation();

                        if (orderBookData.getStatus() != null && orderBookData.getStatus().equalsIgnoreCase("Unconfirmed")) {


                            if (orderBookData.getIStrategyId() != null && orderBookData.getIStrategyId().equalsIgnoreCase("50")) {
                                orderTypetxt.setText("BRACKET");
                                //createPositionsRow("Order Type", ":\t" + "Bracket");
                                modifyOrderType = "7";
                            } else if (orderBookData.getBookType() != null && orderBookData.getBookType().equalsIgnoreCase("3")) {
                                // 0032201: In andriod,It is taking Limit order when Placed SL/SLM order in NSE FO/NSE CD for Offline orders
                                if (bt.getBitResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                                    orderTypetxt.setText("STOPLOSS MARKET");
                                    //createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                    modifyOrderType = "4";
                                } else {
                                    modifyOrderType = "3";
                                    if (Double.parseDouble(orderBookData.getPrice()) == 0.00 && modifyOrderType.equalsIgnoreCase("3")) {
                                        orderTypetxt.setText("STOPLOSS MARKET");
                                        //createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                        modifyOrderType = "4";
                                    } else {
                                        orderTypetxt.setText("STOPLOSS");
                                        //createPositionsRow("Order Type", ":\t" + "Stop Loss");
                                        modifyOrderType = "3";
                                    }
                                }
                            } else if (Double.parseDouble(orderBookData.getPrice()) == 0.00) {
                                // createPositionsRow("Order Type", ":\t" + "Market");
                                orderTypetxt.setText("MARKET");
                                modifyOrderType = "2";
                            } else {
                                //createPositionsRow("Order Type", ":\t" + "Limit");
                                orderTypetxt.setText("LIMIT");
                                modifyOrderType = "1";
                            }
                        } else {
                            if (orderBookData.getIStrategyId() != null && orderBookData.getIStrategyId().equalsIgnoreCase("50")) {
                                orderTypetxt.setText("BRACKET");
                                //createPositionsRow("Order Type", ":\t" + "Bracket");
                                modifyOrderType = "7";
                            } else {
                                if (orderBookData.getBookType() != null && orderBookData.getBookType().equalsIgnoreCase("1")) {
                                    if (orderBookData.getOrderFlags() != null) {
                                        if (bt.getBitResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                                            if (Double.parseDouble(orderBookData.getPrice()) == 0.00) {
                                                orderTypetxt.setText("MARKET");
                                                //createPositionsRow("Order Type", ":\t" + "Market");
                                                modifyOrderType = "2";
                                            } else {
                                                orderTypetxt.setText("LIMIT");
                                                //createPositionsRow("Order Type", ":\t" + "Limit");
                                                modifyOrderType = "1";
                                            }
                                        } else {
                                            orderTypetxt.setText("LIMIT");
                                            //createPositionsRow("Order Type", ":\t" + "Limit");
                                            modifyOrderType = "1";
                                        }
                                    }
                                } else if (orderBookData.getBookType() != null && orderBookData.getBookType().equalsIgnoreCase("3")) {
                                    if (bt.getBitResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                                        orderTypetxt.setText("STOPLOSS MARKET");
                                        //createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                        modifyOrderType = "4";
                                    } else {
                                        modifyOrderType = "3";
                                        if (Double.parseDouble(orderBookData.getPrice()) == 0.00 && modifyOrderType.equalsIgnoreCase("3")) {
                                            orderTypetxt.setText("STOPLOSS MARKET");
                                            //createPositionsRow("Order Type", ":\t" + "StopLoss Market");
                                            modifyOrderType = "4";
                                        } else {
                                            orderTypetxt.setText("STOPLOSS");
                                            //createPositionsRow("Order Type", ":\t" + "Stop Loss");
                                            modifyOrderType = "3";
                                        }
                                    }
                                }else{
                                    orderTypetxt.setText("LIMIT");
                                    modifyOrderType = "1";
                                }
                            }
                        }

                        if (!orderBookData.getExpiryDate().equalsIgnoreCase("0") && orderBookData.getExpiryDate() != null) {
                            //createPositionsRow("Expiry Date", ":\t" + DateTimeFormatter.getDateFromTimeStamp(orderBookData.getExpiryDate(), "dd MMM yyyy", "bse"));
                        }


                        if (modifyOrderType != null) {
                            if (modifyOrderType.equalsIgnoreCase("7")) {

                                if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {

                                    triggerPricetxt.setText(String.format("%.4f", Double.parseDouble(orderBookData.getDSLTPrice())));
                                } else {
                                    triggerPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getDSLTPrice())));
                                }
                            }
                        }

                        if (orderBookData.getOrderType() != null && orderBookData.getOrderType().equalsIgnoreCase("3")) {

                            if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {
                                triggerPricetxt.setText(String.format("%.4f", Double.parseDouble(orderBookData.getTrigPrice())));
                            } else {
                                triggerPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getTrigPrice())));
                            }
                        }

                        if (getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("mcx") ||
                                getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("ncdex")) {
                            tradedQtytxt.setText(orderBookData.getTrdQty());
                            pendingQyttxt.setText(orderBookData.getPendingQty());
                            disclosedQtytxt.setText(String.valueOf((Integer.parseInt(orderBookData.getDiscQty())) / (Integer.parseInt(orderBookData.getLotSize()))));
                            orderqtytxt.setText(String.valueOf((Integer.parseInt(orderBookData.getTrdQty()) + Integer.parseInt(orderBookData.getPendingQty())) /
                                    Integer.parseInt(orderBookData.getLotSize())));

                        } else {
                            tradedQtytxt.setText(orderBookData.getTrdQty());
                            pendingQyttxt.setText(orderBookData.getPendingQty());
                            disclosedQtytxt.setText(orderBookData.getDiscQty());
                            orderqtytxt.setText(String.valueOf(Integer.parseInt(orderBookData.getTrdQty()) + Integer.parseInt(orderBookData.getPendingQty())));
                        }

                        orderplacedtxt.setText(orderBookData.getOrdTime());
                        if (Integer.parseInt(orderBookData.getLgoodtilldate()) > 0) {
                            validityTypetxt.setText("GTD");
                            modifyOrderLife = "GTD";
                        } else if (bt.getDayResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            validityTypetxt.setText("DAY");
                            modifyOrderLife = "Day";
                        } else if (bt.getIOCResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            validityTypetxt.setText("IOC");
                            modifyOrderLife = "IOC";
                        } else if (bt.getGTCResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            validityTypetxt.setText("GTC");
                            modifyOrderLife = "GTC";
                        } else if (bt.getEOSResult(Short.parseShort(orderBookData.getOrderFlags())) == 1) {
                            validityTypetxt.setText("EOS");
                            modifyOrderLife = "EOS";
                        }


                        if (((Integer.valueOf(orderBookData.getToken()) >= 502000000) && (Integer.valueOf(orderBookData.getToken()) <= 502999999)) || ((Integer.valueOf(orderBookData.getToken()) >= 1302000000) && (Integer.valueOf(orderBookData.getToken()) <= 1302999999))) {
                            orderPricetxt.setText(String.format("%.4f", Double.parseDouble(orderBookData.getPrice())));
                            if (Integer.parseInt(orderBookData.getTrdQty()) > 0) {
                                tradedPricetxt.setText(String.format("%.4f", Double.parseDouble(orderBookData.getPrice())));
                            }

                        } else {
                            orderPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));
                            if (Integer.parseInt(orderBookData.getTrdQty()) > 0) {
                                tradedPricetxt.setText(String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));
                            }
                        }
                        if (getExchange(orderBookData.getToken()).equalsIgnoreCase("mcx") || getExchange(orderBookData.getToken()).equalsIgnoreCase("ncdex")) {
                            //createPositionsRow("Volume Traded", ":\t" + String.valueOf((Integer.parseInt(orderBookData.getTrdQty())) / (Integer.parseInt(orderBookData.getLotSize()))));
                            //createPositionsRow("Volume Remaining", ":\t" + String.valueOf((Integer.parseInt(orderBookData.getPendingQty())) / (Integer.parseInt(orderBookData.getLotSize()))));
                            tradedQtytxt.setText(String.valueOf((Integer.parseInt(orderBookData.getTrdQty())) / (Integer.parseInt(orderBookData.getLotSize()))));
                            pendingQyttxt.setText(String.valueOf((Integer.parseInt(orderBookData.getPendingQty())) / (Integer.parseInt(orderBookData.getLotSize()))));
                        } else {

                            tradedQtytxt.setText(orderBookData.getTrdQty());
                            pendingQyttxt.setText(orderBookData.getPendingQty());
                            //createPositionsRow("Volume Traded", ":\t" + orderBookData.getTrdQty());
                            //createPositionsRow("Volume Remaining", ":\t" + orderBookData.getPendingQty());
                        }
                    }
                }


                if (orderBookData.getStatus().equalsIgnoreCase("pending")) {
                    modifyBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    cancelBtn.setVisibility(View.VISIBLE);
                    modifyBtn.setVisibility(View.VISIBLE);
                } else if (orderBookData.getStatus().equalsIgnoreCase("amo unconfirmed")
                        || orderBookData.getStatus().equalsIgnoreCase("offline unconfirmed") ||
                        orderBookData.getStatus().equalsIgnoreCase("unconfirmed")) {

                    modifyBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    cancelBtn.setVisibility(View.VISIBLE);
                    modifyBtn.setVisibility(View.VISIBLE);

                } else {

                    modifyBtn.setEnabled(false);
                    cancelBtn.setEnabled(false);
                    cancelBtn.setVisibility(View.GONE);
                    modifyBtn.setVisibility(View.GONE);
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

    private void cancelTheOrder() {
        Util.updateValidationTime(getActivity());
        TradeCancelOrderRequest cancelRequest = new TradeCancelOrderRequest();
        cancelRequest.setClientCode(AccountDetails.getClientCode(getActivity()));
        cancelRequest.setDisQty(orderBookData.getDiscQty());
        cancelRequest.setExchange(getExchange(orderBookData.getToken()));
        cancelRequest.setLot(orderBookData.getLotSize());
        cancelRequest.setOrderType(modifyOrderType); //
        cancelRequest.setPrice(orderBookData.getPrice());
        cancelRequest.setProduct(orderBookData.getProduct());
        cancelRequest.setiStrategyNo(orderBookData.getIStrategyId());
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

        if (orderBookData.getTrigPrice().equalsIgnoreCase("0") || orderBookData.getTrigPrice().equalsIgnoreCase("0.0")) {
            cancelRequest.setTriggerPrice(orderBookData.getDSLTPrice());
        } else {
            cancelRequest.setTriggerPrice(orderBookData.getTrigPrice());
        }

        cancelRequest.setValidity(getValidity(modifyOrderLife));
        cancelRequest.setSide(orderBookData.getAction());
        cancelRequest.setLu_time_exchange(orderBookData.getOrdModTime());
        cancelRequest.setPending_disclosed_qty("1");
        cancelRequest.setQty_filled_today(orderBookData.getTrdQty());
        cancelRequest.setPending_qty(orderBookData.getPendingQty());
        cancelRequest.setGorderid(orderBookData.getUniqueID());
        cancelRequest.setEorderid(orderBookData.getUniqueOrderID());
        cancelRequest.setlIOMRuleNo(orderBookData.getLIOMRuleNo());
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
            GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.OR_ORDER_CANCEL_MSG), "Ok", false, null);
            cancelBtn.setVisibility(View.GONE);
            modifyBtn.setVisibility(View.GONE);
            dismiss();
        } else {
            GreekDialog.alertDialog(getActivity(), 0, GREEK, "We are facing Network congestion.Kindly try after sometime", "Ok", false, null);
            cancelBtn.setVisibility(View.GONE);
        }

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

            // MarketStatusPostRequest.sendRequest(AccountDetails.getUsername(getActivity()), getActivity(), serviceResponseHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
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

    private String getOrderValidity(String validity) {

        String validityName = "";

        BitWiseOperation bt = new BitWiseOperation();

        if (bt.getDayResult(Short.parseShort(validity)) == 1) {
            validityName = "Day";
        } else if (bt.getIOCResult(Short.parseShort(validity)) == 1) {
            validityName = "IOC";
        } else if (bt.getGTCResult(Short.parseShort(validity)) == 1) {
            validityName = "GTC";
        } else if (bt.getEOSResult(Short.parseShort(validity)) == 1) {
            validityName = "EOS";
        }
        return validityName;
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

    private String getProductType(String productType) {

        /*String productName = "";

        if (productType.equalsIgnoreCase("1")) {
            productName = "INTRADAY";
        } else if (productType.equalsIgnoreCase("0")) {
            productName = "DELIVERY";
        } else if (productType.equalsIgnoreCase("2")) {
            productName = "MTF";
        } else if (productType.equalsIgnoreCase("5")) {
            productName = "SSEQ";
        } else if (productType.equalsIgnoreCase("3")) {
            productName = "TNC";

        } else if (productType.equalsIgnoreCase("4")) {
            productName = "CATALYST";
        }
        return productName;*/


        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(productType)) {
                return AccountDetails.getAllowedProduct().get(i).getcProductName();
            }
        }

        return "";
    }

    private String getOrderType(String orderType) {

        String orderName = "Limit";

        if (orderType.equalsIgnoreCase("1")) {

            orderName = "Limit";
        } else if (orderType.equalsIgnoreCase("2")) {

            orderName = "Market";
        } else if (orderType.equalsIgnoreCase("3")) {

            orderName = "Stop Less";
        } else if (orderType.equalsIgnoreCase("4")) {

            orderName = "StopLess Market";
        } else if (orderType.equalsIgnoreCase("5")) {

            orderName = "Cover";
        } else if (orderType.equalsIgnoreCase("6")) {

            orderName = "After Market";
        } else if (orderType.equalsIgnoreCase("7")) {

            orderName = "Bracket";
        }

        return orderName;
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
}


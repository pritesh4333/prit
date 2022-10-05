package com.acumengroup.mobile.reports;


import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MutualFundSipNewOrderResponse;
import com.acumengroup.greekmain.core.model.cancelOrder.CancelOrderRequest;
import com.acumengroup.greekmain.core.model.cancelOrder.CancelPurchaseOrderRequest;
import com.acumengroup.greekmain.core.model.cancelSTPOrder.STPCancelRequest;
import com.acumengroup.greekmain.core.model.cancelWSP.SWPCancelRequest;
import com.acumengroup.greekmain.core.model.tradeorderbook.MFOrderBook;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MFOrderBookDetailsFragment extends GreekBaseFragment {

    private MFOrderBook orderBookData;
    private View orderBookDetailsView;
    private Button modifyBtn, cancelBtn;
    private String orderType = "";
    OrderStreamingController orderStreamingController;
    LinearLayout orderDetails;
    private Boolean changeColor = true;


    private final View.OnClickListener modifyClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            dialog = new AlertDialog.Builder(getMainActivity()).setTitle("Redeem Order")
                    .setMessage("Do you want to redeem this order?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            /* if (listener != null) listener.alertDialogAction(Action.OK,id);*/
                            String mforderType = getArguments().getString("orderType");
                            if (mforderType.equalsIgnoreCase("sip")) {
                                //sendSIPCancelRequest();
                            }

                            if (mforderType.equalsIgnoreCase("Lumpsum")) {

                                sendpurchaseRedeemRequest();
                            }

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {


                        }
                    }).show();


        }
    };


    private AlertDialog dialog;
    private final View.OnClickListener cancelClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            dialog = new AlertDialog.Builder(getMainActivity()).setTitle("Cancel Order")
                    .setMessage("Do you really want to cancel this order?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            /* if (listener != null) listener.alertDialogAction(Action.OK,id);*/
                            String mforderType = getArguments().getString("orderType");
                            if (mforderType.equalsIgnoreCase("sip")) {
                                sendSIPCancelRequest();
                            }

                            if (mforderType.equalsIgnoreCase("Lumpsum")) {
                                sendpurchaseCancelRequest();
                            }

                            if (mforderType.equalsIgnoreCase("SWP")) {
                                sendswpCancelRequest();
                            }

                            if (mforderType.equalsIgnoreCase("STP")) {
                                sendSTPCancelRequest();
                            }

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            /* if (listener != null) listener.alertDialogAction(Action.OK,id);*/


                        }
                    }).show();

        }
    };


    private void sendSIPCancelRequest() {
        String ClientCode = AccountDetails.getUsername(getMainActivity());

        showProgress();
        CancelOrderRequest.sendRequest(orderBookData.getSchemeName(), ClientCode, "CXL",
                "", orderBookData.getISIN(), orderBookData.getTransactionMode(), orderBookData.getDpTrans(),
                orderBookData.getSipFrequency(), orderBookData.getPaymentMode(), orderBookData.getAmount(),
                orderBookData.getStartDate(), orderBookData.getNoOfIntallment(), "", "MyBroker",
                "192.168.1.1", "2524", orderBookData.getBseCode(), orderBookData.getRegNo(), getMainActivity(),
                serviceResponseHandler);


    }


    private void sendpurchaseCancelRequest() {
        String ClientCode = AccountDetails.getUsername(getMainActivity());

        showProgress();
        CancelPurchaseOrderRequest.sendRequest("CXL", orderBookData.getDpTrans(), orderBookData.getQuantity(), "N", "", "", orderBookData.getAmount(), ClientCode, "p", "FRESH", orderBookData.getSchemeCode(), orderBookData.getSchemeName(), "", orderBookData.getISIN(), orderBookData.getOrderNo(), orderBookData.getUniqueRefNo(), getMainActivity(), serviceResponseHandler);

    }

    private void sendswpCancelRequest() {
        String ClientCode = AccountDetails.getUsername(getMainActivity());
        showProgress();
        SWPCancelRequest.sendRequest(orderBookData.getAmount(), orderBookData.getRegNo(), "10", ClientCode, "", getActivity(), serviceResponseHandler);

    }

    private void sendSTPCancelRequest() {
        String ClientCode = AccountDetails.getUsername(getMainActivity());

        showProgress();
        STPCancelRequest.sendRequest(orderBookData.getAmount(), orderBookData.getRegNo(), "09", ClientCode, "", getActivity(), serviceResponseHandler);

    }

    private void sendpurchaseRedeemRequest() {
        String ClientCode = AccountDetails.getUsername(getMainActivity());
        showProgress();
        CancelPurchaseOrderRequest.sendRequest("NEW", orderBookData.getDpTrans(),
                orderBookData.getQuantity(), "N", "234323456", "",
                orderBookData.getAmount(), ClientCode, "R", "FRESH", orderBookData.getSchemeCode(),
                orderBookData.getSchemeName(), "", orderBookData.getISIN(), orderBookData.getOrderNo(), orderBookData.getUniqueRefNo(),
                getMainActivity(), serviceResponseHandler);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orderBookDetailsView = super.onCreateView(inflater, container, savedInstanceState);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_orderbook_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_orderbook_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        orderStreamingController = new OrderStreamingController();
        orderDetails = orderBookDetailsView.findViewById(R.id.order_details);
        setupView();

        return orderBookDetailsView;
    }


    private void setupView() {
        setAppTitle(getClass().toString(), "Investment Report Details");

        modifyBtn = orderBookDetailsView.findViewById(R.id.modify_Order_btn);
        modifyBtn.setOnClickListener(modifyClickListener);

        modifyBtn.setText("Redeem");


        cancelBtn = orderBookDetailsView.findViewById(R.id.cancel_Order_btn);
        cancelBtn.setOnClickListener(cancelClickListener);

        parseReceivedArguments();
    }


    private void parseReceivedArguments() {
        orderType = getArguments().getString("Type");
        String mforderType = getArguments().getString("orderType");
        orderType = (orderType == null) ? "" : orderType;
        ArrayList<String> keys;
        switch (orderType) {
            case "OrderBook":
                orderBookData = (MFOrderBook) getArguments().getSerializable("response");


                if (mforderType.equalsIgnoreCase("Lumpsum")) {
                    createPositionsRow("Scheme Name", ":\t" + orderBookData.getSchemeName());
                    createPositionsRow("Scheme Code", ":\t" + orderBookData.getSchemeCode());
                    createPositionsRow("Isin", ":\t" + orderBookData.getISIN());

                    if (orderBookData.getBuySellType().equalsIgnoreCase("NEW")) {
                        createPositionsRow("BuySell Type", ":\t" + "New");
                    } else if (orderBookData.getBuySellType().equalsIgnoreCase("CXL")) {
                        createPositionsRow("BuySell Type", ":\t" + "Cancelled");
                    }

                    createPositionsRow("Amount", ":\t" + orderBookData.getAmount());


                    createPositionsRow("DP Transaction", ":\t" + getDPTransMode(orderBookData.getDpTrans()));


                    createPositionsRow("Order Number", ":\t" + orderBookData.getOrderNo());
                    if (orderBookData.getStatus().equalsIgnoreCase("0")) {
                        createPositionsRow("Status", ":\t" + "Success");
                    } else if (orderBookData.getStatus().equalsIgnoreCase("1")) {
                        createPositionsRow("Status", ":\t" + "Failure");
                    }


                } else if (mforderType.equalsIgnoreCase("sip")) {

                    createPositionsRow("Scheme Name", ":\t" + orderBookData.getSchemeName());
                    createPositionsRow("Scheme Code", ":\t" + orderBookData.getSchemeCode());
                    if (orderBookData.getBuySellType().equalsIgnoreCase("NEW")) {
                        createPositionsRow("BuySell Type", ":\t" + "New");
                    } else if (orderBookData.getBuySellType().equalsIgnoreCase("CXL")) {
                        createPositionsRow("BuySell Type", ":\t" + "Cancelled");
                    }
                    createPositionsRow("Amount", ":\t" + orderBookData.getAmount());

                    createPositionsRow("DP Transaction", ":\t" + getDPTransMode(orderBookData.getDpTrans()));

                    createPositionsRow("No Of Installments", ":\t" + orderBookData.getNoOfIntallment());
                    createPositionsRow("Start Date", ":\t" + orderBookData.getStartDate());

                    if (!orderBookData.getEndDate().equalsIgnoreCase("0")) {
                        // createPositionsRow("End Date", ":\t" + orderBookData.getEndDate());

                    }
                    createPositionsRow("Order Number", ":\t" + orderBookData.getRegNo());
                    if (orderBookData.getStatus().equalsIgnoreCase("0")) {
                        createPositionsRow("Status", ":\t" + "Success");
                    } else if (orderBookData.getStatus().equalsIgnoreCase("1")) {
                        createPositionsRow("Status", ":\t" + "Failure");
                    }


                } else if (mforderType.equalsIgnoreCase("swp")) {

                    createPositionsRow("Scheme Name", ":\t" + orderBookData.getSchemeName());
                    createPositionsRow("Scheme Code", ":\t" + orderBookData.getSchemeCode());
                    if (orderBookData.getBuySellType().equalsIgnoreCase("NEW")) {
                        createPositionsRow("BuySell Type", ":\t" + "New");
                    } else if (orderBookData.getBuySellType().equalsIgnoreCase("CXL")) {
                        createPositionsRow("BuySell Type", ":\t" + "Cancelled");
                    }
                    createPositionsRow("Amount", ":\t" + orderBookData.getAmount());

                    createPositionsRow("DP Transaction", ":\t" + getDPTransMode(orderBookData.getDpTrans()));

                    createPositionsRow("No Of Installments", ":\t" + orderBookData.getNoOfIntallment());
                    createPositionsRow("Start Date", ":\t" + orderBookData.getStartDate());


                    if (!orderBookData.getEndDate().equalsIgnoreCase("0")) {
                        //createPositionsRow("End Date", ":\t" + orderBookData.getEndDate());
                    }

                    createPositionsRow("Order Number", ":\t" + orderBookData.getRegNo());
                    if (orderBookData.getStatus().equalsIgnoreCase("0")) {
                        createPositionsRow("Status", ":\t" + "Success");
                    } else if (orderBookData.getStatus().equalsIgnoreCase("1")) {
                        createPositionsRow("Status", ":\t" + "Failure");
                    }


                } else if (mforderType.equalsIgnoreCase("stp")) {

                    createPositionsRow("Scheme Name", ":\t" + orderBookData.getSchemeName());
                    createPositionsRow("Scheme Code", ":\t" + orderBookData.getSchemeCode());
                    //createPositionsRow("Isin", ":\t" + orderBookData.getISIN());
                    if (orderBookData.getBuySellType().equalsIgnoreCase("NEW")) {
                        createPositionsRow("BuySell Type", ":\t" + "New");
                    } else if (orderBookData.getBuySellType().equalsIgnoreCase("CXL")) {
                        createPositionsRow("BuySell Type", ":\t" + "Cancelled");
                    }
                    createPositionsRow("Amount", ":\t" + orderBookData.getAmount());
                    createPositionsRow("DP Transaction", ":\t" + getDPTransMode(orderBookData.getDpTrans()));
                    createPositionsRow("No Of Installments", ":\t" + orderBookData.getNoOfIntallment());
                    createPositionsRow("Start Date", ":\t" + orderBookData.getStartDate());

                    if (!orderBookData.getEndDate().equalsIgnoreCase("0")) {
                        //createPositionsRow("End Date", ":\t" + orderBookData.getEndDate());

                    }

                    createPositionsRow("Order Number", ":\t" + orderBookData.getRegNo());

                    if (orderBookData.getStatus().equalsIgnoreCase("0")) {
                        createPositionsRow("Status", ":\t" + "Success");
                    } else if (orderBookData.getStatus().equalsIgnoreCase("1")) {
                        createPositionsRow("Status", ":\t" + "Failure");
                    }
                }


                if (orderBookData.getStatus().equalsIgnoreCase("0")) {

                    if (mforderType.equalsIgnoreCase("Lumpsum")) {

                        modifyBtn.setEnabled(true);
                        cancelBtn.setEnabled(true);
                    } else {

                        modifyBtn.setEnabled(false);
                        cancelBtn.setEnabled(true);
                    }
                } else {
                    modifyBtn.setEnabled(false);
                    cancelBtn.setEnabled(false);
                }


                if (orderBookData.getBuySellType().equalsIgnoreCase("new")) {

                    if (orderBookData.getStatus().equalsIgnoreCase("0")) {


                        if (orderBookData.getBuySell().equalsIgnoreCase("R")) {

                            modifyBtn.setEnabled(false);
                            cancelBtn.setEnabled(true);

                        } else if (orderBookData.getBuySell().equalsIgnoreCase("p")) {

                            modifyBtn.setEnabled(true);
                            cancelBtn.setEnabled(true);
                        }

                    } else {

                        modifyBtn.setEnabled(false);
                        cancelBtn.setEnabled(false);
                    }

                } else if (orderBookData.getBuySellType().equalsIgnoreCase("CXL")) {

                    modifyBtn.setEnabled(false);
                    cancelBtn.setEnabled(false);

                }


                break;
        }
    }

    private String getDPTransMode(String dptransMode) {

        String dpTransactionMode = "";
        if (dptransMode.equalsIgnoreCase("c")) {
            dpTransactionMode = "CDSL";
        } else if (dptransMode.equalsIgnoreCase("n")) {
            dpTransactionMode = "NSDL";
        } else if (dptransMode.equalsIgnoreCase("p")) {
            dpTransactionMode = "Physical";
        }

        return dpTransactionMode;
    }
    //TODO  ate time converter

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
            }        else if (":\tSell".equalsIgnoreCase(value))
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


    @Override
    public void onFragmentResume() {

        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MF_ORDER_BOOK_DETAILS_SCREEN;
    }

    @Override
    public void onFragmentPause() {
//        handler.removeCallbacksAndMessages(null);
        super.onFragmentPause();
        AccountDetails.setToastCount(0);

        //  GreekDialog.dismissDialog();

    }

    @Override
    public void onPause() {
        super.onPause();
        // GreekDialog.dismissDialog();
    }

    @Override
    public void handleResponse(Object response) {

        String alertMessage = "";

        JSONResponse jsonResponse = (JSONResponse) response;

        hideProgress();
        // if ("siporderentryparam".equalsIgnoreCase(jsonResponse.getServiceGroup()) && "getConnectToMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
        try {

            MutualFundSipNewOrderResponse orderResponse = (MutualFundSipNewOrderResponse) jsonResponse.getResponse();

            String sipId = orderResponse.getSIP_ID();
            String schemeName = orderResponse.getSchemeName();
            String status = orderResponse.getStatus();
            String mforderType = getArguments().getString("orderType");

            if (mforderType.equalsIgnoreCase("sip")) {

                // GreekDialog.alertDialog(getContext(), 0, schemeName, "SIP order is " + status + ": " + sipId, "Ok", true, null);

                alertMessage = "SIP order is " + status + ": " + sipId;

                String message = schemeName + "|" + alertMessage;

                EventBus.getDefault().post(message);

            }
            if (mforderType.equalsIgnoreCase("Lumpsum")) {

                //GreekDialog.alertDialog(getContext(), 0, schemeName, "Lumpsum order is " + status, "Ok", true, null);

                alertMessage = "Lumpsum order is " + status;

                String message = schemeName + "|" + alertMessage;

                EventBus.getDefault().post(message);

            }
            if (mforderType.equalsIgnoreCase("swp")) {

                // GreekDialog.alertDialog(getContext(), 0, schemeName, "SWP order is " + status, "Ok", true, null);

                alertMessage = "SWP order is " + status;

                String message = schemeName + "|" + alertMessage;
                EventBus.getDefault().post(message);

            }

            if (mforderType.equalsIgnoreCase("stp")) {

                //GreekDialog.alertDialog(getContext(), 0, schemeName, "STP order is " + status, "Ok", true, null);

                alertMessage = "STP order is " + status;

                String message = schemeName + "|" + alertMessage;

                EventBus.getDefault().post(message);
            }

//            getMainActivity().onBackPressed();

            navigateTo(NAV_TO_MF_ORDER_BOOK_SCREEN, new Bundle(), false);

            cancelBtn.setEnabled(false);
            modifyBtn.setEnabled(false);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
package com.acumengroup.mobile.reports;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerBannedResponse;
import com.acumengroup.greekmain.core.model.tradeorderbook.OrderBook;
import com.acumengroup.greekmain.core.network.ProductChangeResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.core.trade.OrderBookData;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.OrderBookResponse;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.adapter.GreekPopulationListener;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class OrderBookFragment extends GreekBaseFragment {
    private final ArrayList<String> assetList = new ArrayList<>();
    private final ArrayList<String> statusList = new ArrayList<>();
    private final ArrayList<String> exchangeList = new ArrayList<>();
    private List<OrderBook> orderBook;
    private boolean isWaitingForResponseOnPTR = false;
    private View orderBookView;
    private Spinner assetTypeSpinner, orderStatusSpinner, exchangeTypeSpinner;
    private ArrayAdapter<String> statusAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private ListView orderList;
    private RelativeLayout errorMsgLayout;
    private GreekCommonAdapter<OrderBookData> commonAdapter;

    OrderBookResponse orderBookResponse;

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        }
    };
    private final OnItemClickListener orderBookItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            Bundle bundle = new Bundle();
            OrderBookData rowData = commonAdapter.getItem(arg2);
            OrderBook hashtable = (OrderBook) rowData.getDetails();
            bundle.putSerializable("response", hashtable);
            bundle.putSerializable("Type", "OrderBook");

            navigateTo(NAV_TO_ORDER_BOOK_DETAILS_SCREEN, bundle, true);
        }
    };


    private final OnItemSelectedListener statusSelector = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private GreekTextView row11, row12, row13, row21, row22, row23, disclaimerTxt, rowheader11, rowheader12, rowheader13, rowheader21, rowheader22, rowheader23;
    private final OnItemSelectedListener assetTypeSelector = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            orderList.setAdapter(commonAdapter);
            setTitleNames();

            if (position == 6) disclaimerTxt.setVisibility(View.VISIBLE);
            else disclaimerTxt.setVisibility(View.GONE);

            if (position == 4) {
                exchangeTypeSpinner.setSelection(3);
            }

            manageOrderBookRequest(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final OnItemSelectedListener exchangeTypeSelector = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            orderList.setAdapter(commonAdapter);
            setTitleNames();

            if (position == 6) disclaimerTxt.setVisibility(View.VISIBLE);
            else disclaimerTxt.setVisibility(View.GONE);

            if (position == 3 || position == 4) {
                assetList.clear();
                assetList.add("Commodity");
                ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList);
                assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
                assetTypeSpinner.setAdapter(assetTypeAdapter);
                //assetTypeSpinner.setSelection(4);
            } else {
                if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("commodity")) {


                    getAssetTypeList();
                    ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList);
                    assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
                    assetTypeSpinner.setAdapter(assetTypeAdapter);
                }
            }
            manageOrderBookRequest(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void noDataFound(GreekCommonAdapter adapter) {
        if (adapter.getCount() > 0) {
            errorMsgLayout.setVisibility(View.GONE);
        } else {
            errorMsgLayout.setVisibility(View.VISIBLE);
        }
    }

    private void manageOrderBookRequest(int newPos) {

        if (orderStatusSpinner.getSelectedItem() != null) {
            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        } else {
            sendOrderStatusRequest("All");
        }
    }

    private void refreshData() {
        if ("All".equals(orderStatusSpinner.getSelectedItem())) {
            handleOrderBookResponse(orderBook);
            refreshComplete();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orderBookView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_orderbook).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_orderbook).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_ORDER_BOOK_SCREEN;
        setupViews();
        setupController();
        setupAdapter();
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 0);
        return orderBookView;
    }

    private void setupViews() {
        setAppTitle(getClass().toString(), "Order Book");
        swipeRefresh = orderBookView.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        orderList = orderBookView.findViewById(R.id.fnoListView);

        assetTypeSpinner = orderBookView.findViewById(R.id.optionsSpinner);


        exchangeTypeSpinner = orderBookView.findViewById(R.id.exchangeeSpinner);


        orderStatusSpinner = orderBookView.findViewById(R.id.expirySpinner);

        LinearLayout orderBookHeader = orderBookView.findViewById(R.id.orderBookListHeader);
        orderBookHeader.setVisibility(View.VISIBLE);

        errorMsgLayout = orderBookView.findViewById(R.id.showmsgLayout);
        GreekTextView errorTextView = orderBookView.findViewById(R.id.errorHeader);
        errorTextView.setText("You do not have any orders for today.");

        row11 = orderBookView.findViewById(R.id.row11);
        row12 = orderBookView.findViewById(R.id.row12);
        row13 = orderBookView.findViewById(R.id.row13);
        row21 = orderBookView.findViewById(R.id.row21);
        row22 = orderBookView.findViewById(R.id.row22);
        row23 = orderBookView.findViewById(R.id.row23);

        rowheader11 = orderBookView.findViewById(R.id.rowheader11);
        rowheader12 = orderBookView.findViewById(R.id.rowheader12);
        rowheader13 = orderBookView.findViewById(R.id.rowheader13);
        rowheader21 = orderBookView.findViewById(R.id.rowheader21);
        rowheader22 = orderBookView.findViewById(R.id.rowheader22);
        rowheader23 = orderBookView.findViewById(R.id.rowheader23);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {


            LinearLayout header_layout = orderBookView.findViewById(R.id.orderBookListHeader);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));

        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

        }


        disclaimerTxt = orderBookView.findViewById(R.id.disclaimerTxt);
        disclaimerTxt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.dialog_tnc_orderbook, null);

                    final AlertDialog builder = new AlertDialog.Builder(getMainActivity()).create();

                    builder.setView(layout);
                    builder.setCancelable(true);

                    // there are a lot of settings, for dialog, check them all out!
                    // set up text
                    GreekTextView text = layout.findViewById(R.id.msgTxt);
                    text.setText(Html.fromHtml(Util.read(getMainActivity().getAssets().open("MF_OrderBook_Disclaimer")).toString()));
                    text.setMovementMethod(LinkMovementMethod.getInstance());

                    // set up button
                    GreekButton button = layout.findViewById(R.id.okBtn);
                    button.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();

                        }
                    });

                    // now that the dialog is set up, it's time to show it
                    builder.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setTheme() {

        int textColor = AccountDetails.getTextColorDropdown();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            row11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            row12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            row13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            row21.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            row22.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            row23.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            LinearLayout header_layout = orderBookView.findViewById(R.id.orderBookListHeader);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));

        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
            row11.setTextColor(getResources().getColor(textColor));
            row12.setTextColor(getResources().getColor(textColor));
            row13.setTextColor(getResources().getColor(textColor));
            row21.setTextColor(getResources().getColor(textColor));
            row22.setTextColor(getResources().getColor(textColor));
            row23.setTextColor(getResources().getColor(textColor));
        }
    }

    private void setupController() {

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            assetList.clear();
            assetList.add("All");
            assetList.add("Equity");
            assetList.add("FNO");
            assetList.add("Currency");
            assetList.add("Commodity");
        } else {
            getAssetTypeList();
        }

        setStatusSpinner();
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList);
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(assetTypeAdapter);
        assetTypeSpinner.setSelection(0, false);
        assetTypeSpinner.setOnItemSelectedListener(assetTypeSelector);

        statusAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), statusList);
        statusAdapter.setDropDownViewResource(R.layout.custom_spinner);
        orderStatusSpinner.setAdapter(statusAdapter);
        orderStatusSpinner.setSelection(0, false);
        orderStatusSpinner.setOnItemSelectedListener(statusSelector);

        setupExchangeController();
    }

    private void getAssetTypeList() {

        assetList.clear();
        exchangeList.clear();

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
            assetList.add("Equity");
        }
        if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
            assetList.add("FNO");

        }
        if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            assetList.add("Currency");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            assetList.add("Commodity");
        }

        if (assetList.size() > 1) {
            assetList.add(0, "All");
        }

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_ncd) {
            exchangeList.add("NSE");
        }
        if (AccountDetails.allowedmarket_bse || AccountDetails.allowedmarket_bfo || AccountDetails.allowedmarket_bcd) {
            exchangeList.add("BSE");

        }
        if (AccountDetails.allowedmarket_mcx) {
            exchangeList.add("MCX");
        }
        if (AccountDetails.allowedmarket_ncdex) {
            exchangeList.add("NCDEX");
        }

        if (exchangeList.size() > 1) {
            exchangeList.add(0, "All");
        }
    }

    private void setupExchangeController() {

        ArrayAdapter<String> exchangeTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangeList);
        exchangeTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeTypeSpinner.setAdapter(exchangeTypeAdapter);
        exchangeTypeSpinner.setSelection(0, false);
        exchangeTypeSpinner.setOnItemSelectedListener(exchangeTypeSelector);


    }

    private void setStatusSpinner() {
        statusList.clear();
        statusList.add("All");
        statusList.add("Pending");
        statusList.add("Executed");
        statusList.add("Cancelled");
        statusList.add("Exchange Rejected");
        statusList.add("AMO Unconfirmed");
        statusList.add("Unconfirmed");
        statusList.add("RMS Rejected");
    }

    private void setTitleNames() {

        rowheader11.setText("Symbol");
        rowheader12.setText("Qty/Lot");
        rowheader13.setText("Price");
        rowheader21.setText("Exchange");
        rowheader22.setText("Action/Product");
        rowheader23.setText("Status");

    }

    private void sendOrderStatusRequest(String order_status) {
        if (!isWaitingForResponseOnPTR) {
            // isWaitingForResponseOnPTR = true;
            showProgress();
            clearData();
            switch (order_status) {
                case "All":
                    order_status = "All";
                    break;
                case "Pending":
                    order_status = "Pending";
                    break;
                case "Freezed":
                    order_status = "Freezed";
                    break;
                case "Traded":
                    order_status = "Traded";
                    break;
                case "Executed":
                    order_status = "Traded";
                    break;
                case "RMS Rejected":
                    order_status = "RMS Rejected";
                    break;
                case "Exchange Rejected":
                    order_status = "Exchange Rejected";
                    break;
                case "Unconfirmed":
                    order_status = "Unconfirmed";
                    break;
                case "AMO Unconfirmed":
                    order_status = "AMO Unconfirmed";
                    break;
            }
            String sessionId = AccountDetails.getSessionId(getActivity());
            String clientCode = AccountDetails.getClientCode(getActivity());
            String orderType = assetTypeSpinner.getSelectedItem().toString();
            String exchangeType = exchangeTypeSpinner.getSelectedItem().toString();


            //TODO: needs to add exchangeType in request
            String serviceURL = "getOrderBookDetail?exchangeType=" + exchangeType + "&ClientCode=" + clientCode + "&Order_Status=" + order_status.toUpperCase() + "&Ordertype=" + orderType.toUpperCase() + "&gscid=" + AccountDetails.getUsername(getMainActivity());
            //Log.d("orderUrl",serviceURL);

            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        hideProgress();
                        orderBookResponse = new OrderBookResponse();
                        orderBook = orderBookResponse.fromJSON(response);
                        handleOrderBookResponse(orderBook);
                        //setAppTitle(getClass().toString(), "Order Book");

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String message) {//todo
                    toggleErrorLayout(true);
                    //hideProgress();
                    isWaitingForResponseOnPTR = false;
                    refreshComplete();
                    //setAppTitle(getClass().toString(), "Order Book");
                }
            });
        }
    }


    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private void refreshComplete() {
        isWaitingForResponseOnPTR = false;
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }


    private void clearData() {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();
    }

    private void setupAdapter() {
        int[] newsViewIDs = {R.id.row11, R.id.row12, R.id.row13, R.id.row21, R.id.row22, R.id.row23};
        ArrayList<OrderBookData> orderBookModel = new ArrayList<>();
        commonAdapter = new GreekCommonAdapter<>(getMainActivity(), orderBookModel);
        commonAdapter.setLayoutTextViews(R.layout.row_order_book, newsViewIDs);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            commonAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
        } else {
            commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        }
        //commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        commonAdapter.setPopulationListener(new GreekPopulationListener<OrderBookData>() {

            @Override
            public void populateFrom(View v, int position, OrderBookData row, View[] views) {

                if (row.getExchange().equalsIgnoreCase("MCX")) {
                    ((ScrollingTextView) views[0]).setText(row.getTradeSymbol());
                } else {
                    ((ScrollingTextView) views[0]).setText(row.getTradeSymbol() + " - " + row.getInstrument());
                }

                if (row.getStatus().equalsIgnoreCase("Pending")) {
                    if (row.getExchange().equalsIgnoreCase("mcx") || row.getExchange().equalsIgnoreCase("ncdex")) {

                        ((GreekTextView) views[1]).setText(String.valueOf((Integer.parseInt(row.getPendingQty())) / (Integer.parseInt(row.getLotSize()))));

                    } else {
                        ((GreekTextView) views[1]).setText(row.getPendingQty());
                    }

                } else {
                    if (row.getExchange().equalsIgnoreCase("mcx") || row.getExchange().equalsIgnoreCase("ncdex")) {
                        ((GreekTextView) views[1]).setText(String.valueOf((Integer.parseInt(row.getQty())) / (Integer.parseInt(row.getLotSize()))));
                    } else {
                        ((GreekTextView) views[1]).setText(row.getQty());
                    }
                }
                if (((Integer.valueOf(row.getToken()) >= 502000000) && (Integer.valueOf(row.getToken()) <= 502999999)) || ((Integer.valueOf(row.getToken()) >= 1302000000) && (Integer.valueOf(row.getToken()) <= 1302999999))) {
                    ((GreekTextView) views[2]).setText(String.format("%.4f", Double.parseDouble(row.getPrice())));

                } else {
                    String temp_price = String.format("%.2f", Double.parseDouble(row.getPrice()));
                    ((GreekTextView) views[2]).setText(temp_price);
                }


                ((GreekTextView) views[3]).setText(getExchange(row.getToken()));
                ((GreekTextView) views[5]).setText(row.getStatus());

                GreekTextView tv = ((GreekTextView) views[4]);
                String action = row.getAction();
                String product = row.getProduct();
                tv.setText(String.format("%s/%s", action, product), TextView.BufferType.SPANNABLE);
                Spannable span = (Spannable) tv.getText();
                if (action.equalsIgnoreCase("Buy")) {
                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.whitetheambuyColor)), 0, action.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else {
                        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.buyColor)), 0, action.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } else if (action.equalsIgnoreCase("Sell")) {
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_textcolor)), 0, action.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }


                int textColor = AccountDetails.getTextColorDropdown();

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {


                    ((ScrollingTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[5]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

                    ((ScrollingTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[5]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }

            @Override
            public void onRowCreate(View[] views) {

            }
        });
        orderList.setAdapter(commonAdapter);


        orderList.setOnItemClickListener(orderBookItemClickListener);

        if (getArguments() != null) {
            if (getArguments().getString("AssetType") != null) {
                if ("equity".equals(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(1);
                if ("fno".equals(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(2);
                if ("currency".equals(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(3);
            }
        } else {
            orderStatusSpinner.setSelection(1);
        }
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

        // return "";
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
        //  return "";
    }


    private void handleOrderBookResponse(List<OrderBook> orderBook) {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();
        String currStat = orderStatusSpinner.getSelectedItem().toString();
        for (OrderBook item : orderBook) {
            boolean toAdd;
            switch (currStat) {
                case "Pending":
                    toAdd = item.getStatus().equals("Pending");//open
                    break;
                case "Cancelled":
                    toAdd = item.getStatus().equals("Cancelled");
                    break;
                case "Executed":
                    toAdd = item.getStatus().equals("Traded");
                    break;
                case "Rejected":
                    toAdd = item.getStatus().equals("Rejected");
                    break;
                case "Freezed":
                    toAdd = item.getStatus().equals("Freezed");
                    break;
                case "RMS Rejected":
                    toAdd = item.getStatus().equals("RMS Rejected");
                    break;
                case "Unconfirmed":
                    toAdd = item.getStatus().equals("Unconfirmed");
                    break;
                default:
                    toAdd = true;
                    break;
            }
            if (toAdd) {

                OrderBookData orderBookData = new OrderBookData();
                orderBookData.setToken(item.getToken());
                orderBookData.setAssetType(getAssetType(item.getToken()));


                if (getExchange(item.getToken()).equalsIgnoreCase("MCX")) {

                    if (item.getOptionType().equalsIgnoreCase("XX")) {
                        orderBookData.setTradeSymbol(item.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument());
                    } else {
                        orderBookData.setTradeSymbol(item.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument());
                    }


                } else {
                    orderBookData.setTradeSymbol(item.getTradeSymbol());
                }
                orderBookData.setPendingQty(item.getPendingQty());
                orderBookData.setQty(item.getQty());
                orderBookData.setPrice(item.getPrice());
                orderBookData.setExchange(getExchange(item.getToken()));
                orderBookData.setInstrument(item.getInstrument());
                //orderBookData.setProduct(item.getProduct());
                orderBookData.setStatus(item.getStatus());
                orderBookData.setUniqueID(item.getUniqueID());
                orderBookData.setLgoodtilldate(item.getLgoodtilldate());
                orderBookData.setErrorCode(item.getErrorCode());
                orderBookData.setLotSize(item.getLotSize());
                orderBookData.setOtype(item.getOtype());
                orderBookData.setExpiryDate(item.getExpiryDate());
                orderBookData.setcPANNumber(item.getcPANNumber());
                orderBookData.setdSLPrice(item.getdSLPrice());
                orderBookData.setdTargetPrice(item.getdTargetPrice());
                orderBookData.setlIOMRuleNo(item.getlIOMRuleNo());
                orderBookData.setiStrategyId(item.getiStrategyId());
                orderBookData.setdSLTPrice(item.getdSLTPrice());
                orderBookData.setStrikePrice(item.getStrikePrice());
                orderBookData.setOptionType(item.getOptionType());
                orderBookData.setTag(item.getTag());

//
//                if (item.getProduct().equals("1")) {
//                    orderBookData.setProduct("Intraday");
//                } else if (item.getProduct().equals("0")) {
//                    orderBookData.setProduct("Delivery");
//                } else if (item.getProduct().equals("2")) {
//                    orderBookData.setProduct("MTF");
//                }else if (item.getProduct().equals("3")) {
//                    orderBookData.setProduct("SSEQ");
//                } else {
//                    orderBookData.setProduct("");
//                }


                if (item.getProduct().equals("1")) {
                    orderBookData.setProduct("Intraday");
                } else if (item.getProduct().equals("0")) {
                    orderBookData.setProduct("Delivery");
                } else if (item.getProduct().equals("2")) {
                    orderBookData.setProduct("MTF");
                } else if (item.getProduct().equals("5")) {
                    orderBookData.setProduct("SSEQ");
                } else if (item.getProduct().equals("3")) {
                    orderBookData.setProduct("TNC");
                } else if (item.getProduct().equals("4")) {
                    orderBookData.setProduct("CATALYST");
                } else {
                    orderBookData.setProduct("");
                }


                if (item.getAction().equals("1")) {
                    orderBookData.setAction("Buy");
                } else if (item.getAction().equals("2")) {
                    orderBookData.setAction("Sell");
                } else {
                    orderBookData.setAction("");
                }
                orderBookData.setDetails(item);
                commonAdapter.add(orderBookData);
            }
            commonAdapter.notifyDataSetChanged();
            orderList.setAdapter(commonAdapter);
            noDataFound(commonAdapter);
        }

        refreshComplete();
    }


    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    @Override
    public void onFragmentResume() {
        //setAppTitle(getClass().toString(), "Order Book");
        AccountDetails.currentFragment = NAV_TO_ORDER_BOOK_SCREEN;
        EventBus.getDefault().register(this);
        //((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 0);
        if (orderStatusSpinner != null && orderStatusSpinner.getSelectedItem() != null)
            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        super.onFragmentResume();
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        refreshComplete();
    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {
        super.infoDialogOK(action, message, jsonResponse);
        refreshComplete();
    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        refreshComplete();
        super.showMsgOnScreen(action, msg, jsonResponse);
    }

    public void onEventMainThread(StreamerBannedResponse response) {
        try {
            if (orderStatusSpinner != null)
                sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());


        } catch (Exception e) {

        }
    }


    public void onEventMainThread(ProductChangeResponse productChangeResponse) {
        try {
            if (orderStatusSpinner.getSelectedItem().toString().equalsIgnoreCase("Executed") || orderStatusSpinner.getSelectedItem().toString().equalsIgnoreCase("all")) {
                sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


}


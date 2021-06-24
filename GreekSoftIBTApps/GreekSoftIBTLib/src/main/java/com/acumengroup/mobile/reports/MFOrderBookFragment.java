package com.acumengroup.mobile.reports;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.tradeorderbook.MFOrderBook;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.core.trade.OrderBookData;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MFOrderBookResponse;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.adapter.GreekPopulationListener;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MFOrderBookFragment extends GreekBaseFragment {
    private final ArrayList<String> assetList = new ArrayList<>();
    private final ArrayList<String> statusList = new ArrayList<>();
    private final ArrayList<String> exchangeList = new ArrayList<>();
    private List<MFOrderBook> orderBook;
    private boolean isWaitingForResponseOnPTR = false;
    private View orderBookView;
    private Spinner assetTypeSpinner, orderStatusSpinner, exchangeTypeSpinner;
    private ArrayAdapter<String> statusAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private ListView orderList;
    private RelativeLayout errorMsgLayout;
    private GreekCommonAdapter<OrderBookData> commonAdapter;
    private ArrayAdapter<String> assetTypeAdapter;
    private MFOrderBookResponse mfOrderBookResponse;

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        }
    };
    private final AdapterView.OnItemClickListener orderBookItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            Bundle bundle = new Bundle();
            OrderBookData rowData = commonAdapter.getItem(arg2);
            MFOrderBook hashtable = (MFOrderBook) rowData.getDetails();
            bundle.putSerializable("response", hashtable);
            bundle.putSerializable("Type", "OrderBook");
            bundle.putString("orderType", exchangeTypeSpinner.getSelectedItem().toString());

            navigateTo(NAV_TO_MF_ORDER_BOOK_DETAILS_SCREEN, bundle, true);
        }
    };


    private final AdapterView.OnItemSelectedListener statusSelector = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private GreekTextView row11, row12, row13, row21, row22, row23, disclaimerTxt, rowheader11, rowheader12, rowheader13, rowheader21, rowheader22, rowheader23;
    private final AdapterView.OnItemSelectedListener assetTypeSelector = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            orderList.setAdapter(commonAdapter);

            //setTitleNames();

//            if (position == 6) disclaimerTxt.setVisibility(View.VISIBLE);
//            else disclaimerTxt.setVisibility(View.GONE);
//
//            if (position == 4) {
//                exchangeTypeSpinner.setSelection(3);
//            }


            manageOrderBookRequest(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener exchangeTypeSelector = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (position == 0) {

                assetList.clear();
                assetList.add("All");
                assetList.add("Purchase");
                assetList.add("Cancelled");
                assetList.add("Redeem");

                assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList);
                assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
                assetTypeSpinner.setAdapter(assetTypeAdapter);
                assetTypeSpinner.setSelection(0, false);
                assetTypeAdapter.notifyDataSetChanged();

            } else {

                assetList.clear();
                assetTypeAdapter.clear();
                assetTypeSpinner.clearAnimation();

                assetList.add("All");
                assetList.add("Cancelled");


                assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList);
                assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
                assetTypeSpinner.setAdapter(assetTypeAdapter);
                assetTypeSpinner.setSelection(0, false);
                assetTypeAdapter.notifyDataSetChanged();

            }


            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            orderList.setAdapter(commonAdapter);
            setTitleNames(position);


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orderBookView = super.onCreateView(inflater, container, savedInstanceState);
        //attachLayout(R.layout.fragment_orderbook);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_orderbook_mf).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_orderbook_mf).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_MF_ORDER_BOOK_SCREEN;

        setupViews();
        setupController();
        setupAdapter();
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 0);

        if (orderStatusSpinner != null) {
            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        }
        return orderBookView;
    }

    private void setupViews() {
        setAppTitle(getClass().toString(), "Order Book");
        swipeRefresh = orderBookView.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        orderList = orderBookView.findViewById(R.id.fnoListView);

        assetTypeSpinner = orderBookView.findViewById(R.id.statusSpinner);


        exchangeTypeSpinner = orderBookView.findViewById(R.id.typeSpinner);


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

        //setTheme();


        disclaimerTxt = orderBookView.findViewById(R.id.disclaimerTxt);
        disclaimerTxt.setOnClickListener(new View.OnClickListener() {

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
                    button.setOnClickListener(new View.OnClickListener() {
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
        setAppTitle(getClass().toString(), "Order Book");
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


//        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
//            assetList.clear();
//            assetList.add("All");
//            assetList.add("Equity");
//            assetList.add("FNO");
//            assetList.add("Currency");
//            assetList.add("Commodity");
//        } else {
//            getAssetTypeList();
//        }

        getAssetTypeList();
        ArrayAdapter<String> exchangeTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangeList);
        exchangeTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeTypeSpinner.setAdapter(exchangeTypeAdapter);
        exchangeTypeSpinner.setSelection(0, true);
        exchangeTypeSpinner.setOnItemSelectedListener(exchangeTypeSelector);


        assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList);
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(assetTypeAdapter);
        assetTypeSpinner.setSelection(0, false);
        assetTypeSpinner.setOnItemSelectedListener(assetTypeSelector);

        setStatusSpinner();
        statusAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), statusList);
        statusAdapter.setDropDownViewResource(R.layout.custom_spinner);
        orderStatusSpinner.setAdapter(statusAdapter);
        orderStatusSpinner.setSelection(0, false);
        orderStatusSpinner.setOnItemSelectedListener(statusSelector);

    }

    private void getAssetTypeList() {

        assetList.clear();
        exchangeList.clear();


//        if (exchangeTypeSpinner.getSelectedItemPosition() == 0) {
//            assetList.add("All");
//            assetList.add("Cancelled");
//
//        } else {
//
//            assetList.add("All");
//            assetList.add("Purchase");
//            assetList.add("Cancelled");
//            assetList.add("Redeem");
//
//        }

        assetList.add("All");
        assetList.add("Purchase");
        assetList.add("Cancelled");
        assetList.add("Redeem");

        exchangeList.add("Lumpsum");
        exchangeList.add("SIP");
        exchangeList.add("SWP");
        exchangeList.add("STP");
    }

    private void setupExchangeController() {


    }

    private void setStatusSpinner() {
        statusList.clear();
        statusList.add("Success");
        statusList.add("Failure");

        //orderStatusSpinner.setOnItemSelectedListener(statusSelector);
    }

    private void setTitleNames(int pos) {

        if (pos == 3) {
            rowheader11.setText("Scheme Name");
            rowheader12.setText("Amount");
            rowheader13.setText("Order Type");
            rowheader21.setText("Start Date");
            rowheader22.setText("Scheme Code");
            rowheader23.setText("Status");
        }
        if (pos == 2) {
            rowheader11.setText("Scheme Name");
            rowheader12.setText("Amount");
            rowheader13.setText("Order Type");
            rowheader21.setText("Start Date");
            rowheader22.setText("Scheme Code");
            rowheader23.setText("Status");
        }
        if (pos == 1) {
            rowheader11.setText("Scheme Name");
            rowheader12.setText("Amount");
            rowheader13.setText("Order Type");
            rowheader21.setText("Start Date");
            rowheader22.setText("Scheme Code");
            rowheader23.setText("Status");
        } else if (pos == 0) {
            rowheader11.setText("Scheme Name");
            rowheader12.setText("Amount");
            rowheader13.setText("Order Type");
            rowheader21.setText("Isin");
            rowheader22.setText("Scheme Code");
            rowheader23.setText("Status");
        }

    }

    private void sendOrderStatusRequest(String order_status) {

        if (!isWaitingForResponseOnPTR) {
            // isWaitingForResponseOnPTR = true;
            showProgress();
            clearData();
            switch (order_status) {
                case "Success":
                    order_status = "0";
                    break;
                case "Failure":
                    order_status = "1";
                    break;

            }
            String sessionId = AccountDetails.getSessionId(getActivity());
            String clientCode = AccountDetails.getClientCode(getActivity());
            String orderCategory = assetTypeSpinner.getSelectedItem().toString();
            String exchangeType = exchangeTypeSpinner.getSelectedItem().toString();


            //TODO: needs to add exchangeType in request
            String selectedordertype;
            if (exchangeType.equalsIgnoreCase("Lumpsum")) {

                selectedordertype = "Purchase";

            } else {
                selectedordertype = exchangeType;
            }

            String serviceURL = "getOrderBookDetailMF?orderType=" + selectedordertype + "&status=" + order_status + "&orderCategory=" + orderCategory + "&gcid=" + AccountDetails.getClientCode(getMainActivity());

            Log.d("orderUrl", serviceURL);

            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {

                        hideProgress();
                        mfOrderBookResponse = new MFOrderBookResponse();
                        orderBook = mfOrderBookResponse.fromJSON(response);
                        handleOrderBookResponse(orderBook);

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

                if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Lumpsum")) {
                    ((ScrollingTextView) views[0]).setText(row.getSchemeName());

                    ((GreekTextView) views[1]).setText(row.getAmount());


//                    if (row.getDpTrans().equalsIgnoreCase("P")) {
//                        ((GreekTextView) views[2]).setText("Physical");
//
//                    } else {
//                        ((GreekTextView) views[2]).setText(row.getDpTrans());
//
//                    }


                    if (row.getBuySell().equalsIgnoreCase("P")) {

                        ((GreekTextView) views[2]).setText("Purchase");

                    } else if (row.getBuySell().equalsIgnoreCase("R")) {
                        ((GreekTextView) views[2]).setText("Redeem");

                    }


                    ((GreekTextView) views[3]).setText(row.getISIN());

                    if (row.getStatus().equals("0")) {
                        ((GreekTextView) views[5]).setText("Success");
                    } else {
                        ((GreekTextView) views[5]).setText("Failure");
                    }

                    ((GreekTextView) views[4]).setText(row.getSchemeCode());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("sip")) {

                    ((ScrollingTextView) views[0]).setText(row.getSchemeName());

                    ((GreekTextView) views[1]).setText(row.getAmount());


//                    if (row.getBuySell().equalsIgnoreCase("P")) {
//
//                        ((GreekTextView) views[2]).setText("Purchase");
//
//                    } else {
//                        ((GreekTextView) views[2]).setText("Redeem");
//
//                    }

                    ((GreekTextView) views[2]).setText(row.getOrderType());


                    ((GreekTextView) views[3]).setText(row.getStartDate());
                    if (row.getStatus().equals("0")) {
                        ((GreekTextView) views[5]).setText("Success");
                    } else {
                        ((GreekTextView) views[5]).setText("Failure");
                    }

                    ((GreekTextView) views[4]).setText(row.getSchemeCode());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("stp")) {

                    ((ScrollingTextView) views[0]).setText(row.getSchemeName());

                    ((GreekTextView) views[1]).setText(row.getAmount());


//                    if (row.getDpTrans().equalsIgnoreCase("P")) {
//                        ((GreekTextView) views[2]).setText("Physical");
//
//                    } else {
//                        ((GreekTextView) views[2]).setText(row.getDpTrans());
//
//                    }

//                    if (row.getBuySell().equalsIgnoreCase("P")) {
//
//                        ((GreekTextView) views[2]).setText("Purchase");
//
//                    } else if (row.getBuySell().equalsIgnoreCase("R")) {
//                        ((GreekTextView) views[2]).setText("Redeem");
//
//                    }

                    ((GreekTextView) views[2]).setText(row.getOrderType());


                    ((GreekTextView) views[3]).setText(row.getStartDate());
                    if (row.getStatus().equals("0")) {
                        ((GreekTextView) views[5]).setText("Success");
                    } else {
                        ((GreekTextView) views[5]).setText("Failure");
                    }

                    ((GreekTextView) views[4]).setText(row.getSchemeCode());
                } else if (exchangeTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("swp")) {

                    ((ScrollingTextView) views[0]).setText(row.getSchemeName());

                    ((GreekTextView) views[1]).setText(row.getAmount());


//                    ((GreekTextView) views[2]).setText(row.getDpTrans());

//                    if (row.getBuySell().equalsIgnoreCase("P")) {
//
//                        ((GreekTextView) views[2]).setText("Purchase");
//
//                    } else if (row.getBuySell().equalsIgnoreCase("R")) {
//
//                        ((GreekTextView) views[2]).setText("Redeem");
//
//                    }

                    ((GreekTextView) views[2]).setText(row.getOrderType());

                    ((GreekTextView) views[3]).setText(row.getStartDate());

                    if (row.getStatus().equals("0")) {
                        ((GreekTextView) views[5]).setText("Success");
                    } else {
                        ((GreekTextView) views[5]).setText("Failure");
                    }

                    ((GreekTextView) views[4]).setText(row.getSchemeCode());
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
    }


    private void handleOrderBookResponse(List<MFOrderBook> orderBook) {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();
        String currStat = orderStatusSpinner.getSelectedItem().toString();
        for (MFOrderBook item : orderBook) {
            boolean toAdd;
            switch (currStat) {
                case "Success":
                    toAdd = item.getStatus().equals("0");//open
                    break;
                case "Failure":
                    toAdd = item.getStatus().equals("1");
                    break;
                default:
                    toAdd = true;
                    break;
            }
            if (toAdd) {

                OrderBookData orderBookData = new OrderBookData();
                orderBookData.setSchemeCode(item.getSchemeCode());
                orderBookData.setSchemeName(item.getSchemeName());
                orderBookData.setAmount(item.getAmount());
                orderBookData.setQty(item.getQuantity());
                orderBookData.setDpTrans(item.getDpTrans());
                orderBookData.setISIN(item.getISIN());
                orderBookData.setOrderNo(item.getOrderNo());
                orderBookData.setStatus(item.getStatus());
                orderBookData.setStartDate(item.getStartDate());
                orderBookData.setEndDate(item.getEndDate());
                orderBookData.setRegNo(item.getRegNo());
                orderBookData.setNoOfIntallment(item.getNoOfIntallment());
                orderBookData.setNoOfIntallment(item.getTransactionMode());
                orderBookData.setNoOfIntallment(item.getSipFrequency());
                orderBookData.setNoOfIntallment(item.getPaymentMode());
                orderBookData.setNoOfIntallment(item.getBseCode());
                orderBookData.setBuySell(item.getBuySell());
                orderBookData.setOrderType(item.getOrderType());


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

        super.onFragmentPause();
    }

    @Override
    public void onFragmentResume() {
        setAppTitle(getClass().toString(), "Investment Report");
        AccountDetails.currentFragment = NAV_TO_MF_ORDER_BOOK_SCREEN;


        if (orderStatusSpinner != null) {
            sendOrderStatusRequest(orderStatusSpinner.getSelectedItem().toString());
        }
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


}


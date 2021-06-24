package com.acumengroup.mobile.reports;

import android.content.Context;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.model.tradenetpositionsummary.OverAllPL;
import com.acumengroup.greekmain.core.network.GrossExposureResponse;
import com.acumengroup.greekmain.core.network.ProductChangeResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.core.services.CustomNetPositionSummary;
import com.acumengroup.greekmain.core.services.TradeNetPositionSummaryResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class NetPositionFragment extends GreekBaseFragment implements OnClickListener {

    private final String[] allTypes = {"ALL"};
    private final String[] bseCashTypes = {"ALL", "Intraday", "Delivery"};
    private final String[] nseCashTypes = {"ALL", "Intraday", "Delivery"};
    private final String[] nseFnoTypes = {"ALL", "Intraday", "Delivery"};
    private final String[] nseCurrTypes = {"ALL", "Intraday", "Delivery"};
    private final String[] mcxComTypes = {"ALL", "Intraday", "Delivery"};

    private final List<String> selectedTypes = new ArrayList<>();
    private final List<String> exchangeList = new ArrayList<>();
    private Spinner exchangeSpinner, typeSpinner;
    private SwipeRefreshLayout swipeRefresh;
    private ListView positionList;
    private RelativeLayout errorMsgLayout;
    private View netPositionsView;
    private ArrayList streamingList;
    private String fromFragment = "";
    private GreekCommonAdapter<CustomNetPositionSummary> netPosAdapter;
    private NetPositionFragment.TopGainerAdapter gainerAdapter;
    private AlertDialog levelDialog;
    CustomNetPositionSummary rowData = null;
    ArrayAdapter<String> exchangesAdapter;
    private final OnItemClickListener positionsItemSelectionListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            rowData = gainerAdapter.getFilterItem(position);

            View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_five_actions, parent, false);
            GreekTextView sqOffTxt = layout.findViewById(R.id.squareOff);
            GreekTextView plDetails = layout.findViewById(R.id.plDetails);
            GreekTextView netQtyDetails = layout.findViewById(R.id.netQtyDetails);
            GreekTextView chartsTxt = layout.findViewById(R.id.viewChart);
            GreekTextView mdText = layout.findViewById(R.id.marketDepth);
            GreekTextView productchgtxt = layout.findViewById(R.id.product_chg);
            GreekTextView details = layout.findViewById(R.id.order_details);

            //productchgtxt.setVisibility(View.GONE);
            sqOffTxt.setOnClickListener(NetPositionFragment.this);
            plDetails.setOnClickListener(NetPositionFragment.this);
            netQtyDetails.setOnClickListener(NetPositionFragment.this);
            chartsTxt.setOnClickListener(NetPositionFragment.this);
            mdText.setOnClickListener(NetPositionFragment.this);
            productchgtxt.setOnClickListener(NetPositionFragment.this);
            details.setOnClickListener(NetPositionFragment.this);

            if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                if (Integer.parseInt(rowData.getNetQty()) == 0) {
                    productchgtxt.setEnabled(false);
                    productchgtxt.setTextColor(getResources().getColor(R.color.light_gray));
                }
                else {
                    productchgtxt.setEnabled(true);
                    productchgtxt.setTextColor(getResources().getColor(R.color.black));
                }
            } else {
                    productchgtxt.setEnabled(false);
                    productchgtxt.setTextColor(getResources().getColor(R.color.light_gray));
            }

            if (Integer.parseInt(rowData.getNetQty()) == 0) {
                sqOffTxt.setEnabled(false);
                sqOffTxt.setTextColor(getResources().getColor(R.color.light_gray));
            } else {
                sqOffTxt.setEnabled(true);
                sqOffTxt.setTextColor(getResources().getColor(R.color.black));
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            builder.setView(layout);
            levelDialog = builder.create();
            levelDialog.show();

        }
    };
    private TradeNetPositionSummaryResponse netPositionResponse;
    private final OnItemSelectedListener exchangesSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String[] temp = {"ALL"};
            switch (position) {
                case 0:
                    temp = allTypes;
                    break;
                case 1:
                    temp = bseCashTypes;
                    break;
                case 2:
                    temp = nseCashTypes;
                    break;
                case 3:
                    temp = nseFnoTypes;
                    break;
                case 4:
                    temp = nseCurrTypes;
                    break;
                case 5:
                    temp = bseCashTypes;
                    break;
                case 6:
                    temp = nseCurrTypes;
                    break;
                case 7:
                    temp = nseCurrTypes;
                    break;
                case 8:
                    temp = mcxComTypes;
                    break;
                case 9:
                    temp = nseCurrTypes;
                    break;
            }

            selectedTypes.clear();
            Collections.addAll(selectedTypes, temp);
            typeSpinner.setSelection(0);
            selectedTypes.clear();
            selectedTypes.add(0, "ALL");

            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                selectedTypes.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
            }
            handlePositionsResponse(selectedTypes.get(0));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final OnItemSelectedListener typesSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            handlePositionsResponse(typeSpinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private GreekTextView bookedDay;
    private GreekTextView bookedDayBF;
    private GreekTextView profDay;
    private GreekTextView profDayBF;
    private GreekTextView totalDay;
    private GreekTextView totalDayBF;
    private GreekTextView grossTo;
    private GreekTextView netTo;
    private GreekTextView dayMTM;
    private GreekTextView pltxt, daytxt, daybftxt, bookedtxt, provisionaltxt, grosstotxt, nettotxt, daymtmtxt;
    private Boolean isWaitingForResponseOnPTR = false;
    private CheckBox checkBox;

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            sendPTRRequest();

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        netPositionsView = super.onCreateView(inflater, container, savedInstanceState);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_positions).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_positions).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_NETPOSITION_SCREEN;
        AccountDetails.AutoRefreshForNetPosition = true;
        hideAppTitle();
        setUp();
        return netPositionsView;
    }

    private void setUp() {

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = netPositionsView.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

        checkBox = netPositionsView.findViewById(R.id.selectNetPosChkBox);
        View dividerview = netPositionsView.findViewById(R.id.dividerview);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            checkBox.setButtonTintList(ContextCompat.getColorStateList(getMainActivity(), R.color.checkbox_tint));
        }
        checkBox.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        dividerview.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));


        if(AccountDetails.getNetpositionChecked().equalsIgnoreCase("true"))
        {
            checkBox.setChecked(true);
            selectedTypes.clear();
            selectedTypes.add(0, "ALL");

            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                selectedTypes.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
            }

        }
        else
        {
            checkBox.setChecked(false);
            selectedTypes.clear();
            selectedTypes.add(0, "ALL");

            for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                selectedTypes.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
            }
        }

        checkBox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    AccountDetails.setNetpositionChecked("true");
                    getExchangeList();
                    exchangesAdapter.notifyDataSetChanged();
                    selectedTypes.clear();
                    selectedTypes.add(0, "ALL");

                    for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                        selectedTypes.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                    }
                    sendPTRRequest();
                } else {
                    AccountDetails.setNetpositionChecked("false");
                    getExchangeList();
                    exchangesAdapter.notifyDataSetChanged();
                    selectedTypes.clear();
                    selectedTypes.add(0, "ALL");

                    for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
                        selectedTypes.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
                    }
                    sendPTRRequest();
                }

            }
        });

        setupViews(netPositionsView);
        setUpTheme();
    }

    private void setUpTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            bookedDay.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bookedDayBF.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            profDay.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            profDayBF.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalDay.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalDayBF.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            grossTo.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            netTo.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dayMTM.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //  settMTM.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            daytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            daybftxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bookedtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            provisionaltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            // broughtforwardtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            grosstotxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            nettotxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            daymtmtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
    }

    private void setupViews(View parent) {
        setAppTitle(getClass().toString(), "Net Positions");

        getExchangeList();
        exchangeSpinner = parent.findViewById(R.id.exchangesSpinner);
        exchangesAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangeList);
        exchangesAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(exchangesAdapter);
        exchangeSpinner.setOnItemSelectedListener(exchangesSelectedListener);

        typeSpinner = parent.findViewById(R.id.typeSpinner);
        //selectedTypes.clear();
        //selectedTypes.add("ALL");
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), selectedTypes);
        typesAdapter.setDropDownViewResource(R.layout.custom_spinner);
        typeSpinner.setAdapter(typesAdapter);
        typeSpinner.setOnItemSelectedListener(typesSelectedListener);

        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        positionList = parent.findViewById(R.id.positionsListView);

        bookedDay = parent.findViewById(R.id.bookedDayTxt);
        bookedDayBF = parent.findViewById(R.id.bookedDayBFTxt);
        profDay = parent.findViewById(R.id.profDayTxt);
        profDayBF = parent.findViewById(R.id.profDayBFTxt);
        totalDay = parent.findViewById(R.id.totalDayTxt);
        totalDayBF = parent.findViewById(R.id.totalDayBFTxt);
        grossTo = parent.findViewById(R.id.grossTo);
        netTo = parent.findViewById(R.id.netTo);
        dayMTM = parent.findViewById(R.id.dayMTM);
        //  settMTM = (GreekTextView) parent.findViewById(R.id.settMTM);

        pltxt = parent.findViewById(R.id.pl_txt);
        daytxt = parent.findViewById(R.id.day_txt);
        daybftxt = parent.findViewById(R.id.day_bf_txt);
        bookedtxt = parent.findViewById(R.id.booked_txt);
        provisionaltxt = parent.findViewById(R.id.provisional_txt);
        // broughtforwardtxt = (GreekTextView) parent.findViewById(R.id.brought_forward_txt);
        grosstotxt = parent.findViewById(R.id.grossToTxt);
        nettotxt = parent.findViewById(R.id.netToTxt);
        daymtmtxt = parent.findViewById(R.id.dayMTMTxt);

        errorMsgLayout = netPositionsView.findViewById(R.id.showmsgLayout);
        GreekTextView errorTextView = netPositionsView.findViewById(R.id.errorHeader);
        errorTextView.setText(R.string.GREEK_NO_DATA_MSG);

        setupAdapter();
        sendPositionsRequest();
    }

    private void getExchangeList() {

        exchangeList.clear();

        if(AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
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
                exchangeList.add(0, "ALL");
            }


        }

    }

    private void setupAdapter() {

        gainerAdapter = new TopGainerAdapter(getMainActivity(), new ArrayList<CustomNetPositionSummary>());
        positionList.setAdapter(gainerAdapter);
        positionList.setOnItemClickListener(positionsItemSelectionListener);
    }

    public void sendPositionsRequest() {
        if (netPositionResponse == null) {
            sendPTRRequest();

        }
    }

    private void sendStreamingRequest() {
        streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", streamingList);
    }

    private void extractArguments() {
        if (getArguments() != null)
            fromFragment = getArguments().getString("from");
    }

    private void sendPTRRequest() {
        if (!isWaitingForResponseOnPTR) {
            checkBox.setEnabled(false);
            orderStreamingController.sendStreamingGrossExposureRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()));
            gainerAdapter.clear();
            gainerAdapter.notifyDataSetChanged();
            showProgress();
            isWaitingForResponseOnPTR = true;
            extractArguments();

            String serviceURL = "getNetPosition_Interportability?SessionId=" + AccountDetails.getSessionId(getActivity()) + "&ClientCode=" + AccountDetails.getClientCode(getActivity()) + "&assetType=" + fromFragment + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&interportability=" + AccountDetails.getNetpositionChecked();
            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        streamingList = new ArrayList();
                        netPositionResponse = new TradeNetPositionSummaryResponse();

                        if (response.has("data")) {
                            JSONArray ja1 = response.getJSONArray("data");

                            for (int i = 0; i < ja1.length(); ++i) {
                                JSONObject o = (JSONObject) ja1.get(i);

                                CustomNetPositionSummary customNetPositionSummary = new CustomNetPositionSummary();
                                OverAllPL overAllPL = new OverAllPL();
                                customNetPositionSummary.fromJSON(o);
                                netPositionResponse.getNetPositionSummary().add(customNetPositionSummary);

                                overAllPL.fromJSON(o);
                                netPositionResponse.setOverAllPL(overAllPL);
                                streamingList.add(netPositionResponse.getNetPositionSummary().get(i).getToken());
                                gainerAdapter.addSymbol(netPositionResponse.getNetPositionSummary().get(i).getToken());
                                hideProgress();
                            }
                            sendStreamingRequest();
                        }

                        handlePositionsResponse(typeSpinner.getSelectedItem().toString());
                        calculateTotal();
                        checkBox.setEnabled(true);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        checkBox.setEnabled(true);
                    }
                    refreshComplete();

                }

                @Override
                public void onFailure(String message) {
                    refreshComplete();
                    errorMsgLayout.setVisibility(View.VISIBLE);
                    isWaitingForResponseOnPTR = false;
                    checkBox.setEnabled(true);
                }
            });

        }
    }

    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("ltpinfo")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(ProductChangeResponse productChangeResponse) {
        try {
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "\n Message : " + productChangeResponse.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    //refresh();
                    sendPTRRequest();
                }
            });

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void onEventMainThread(GrossExposureResponse grossExposureResponse) {
        try {
            grossTo.setText(grossExposureResponse.getGrossExposure());
            netTo.setText(grossExposureResponse.getNetTurnOver());

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {

        if (gainerAdapter.containsSymbol(response.getSymbol())) {
            long startTime = System.nanoTime();
            for (int i = 0; i < gainerAdapter.getCount(); i++) {
                if (gainerAdapter.getItem(i).getToken().equals(response.getSymbol())) {
                    CustomNetPositionSummary rowData = gainerAdapter.getItem(i);
                    rowData.setToken(response.getSymbol());
                    rowData.setLtp(response.getLast());

                    if (rowData.getNetQty().equals("0")) {
                        rowData.setMTM(String.format("%.2f", (Double.parseDouble(rowData.getSellAmt()) - Double.parseDouble(rowData.getBuyAmt()))));
                    } else {
                        double multiplier = Double.parseDouble(rowData.getMultiplier());
                        double Ltp = Double.parseDouble(response.getLast());
                        int NetQty = Integer.parseInt(rowData.getNetQty());
                        double netAvg = Double.parseDouble(rowData.getNetAvg());
                        rowData.setMTM(String.format("%.2f", ((Ltp - netAvg) * NetQty) * multiplier));
                    }

                    gainerAdapter.updateData(i, rowData);
                    gainerAdapter.notifyDataSetChanged();
                    calculatedayMTM(gainerAdapter.getData());

                }
            }
        }

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_NETPOSITION_SCREEN;
        EventBus.getDefault().register(this);
        netPositionResponse = null;
        if (AccountDetails.AutoRefreshForNetPosition) {
            setUp();
        }
        if (streamingList != null) {
            streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
            addToStreamingList("ltpinfo", streamingList);
        }

    }

    @Override
    public void onFragmentPause() {

        EventBus.getDefault().unregister(this);
        if (streamingList != null) {
            streamController.pauseStreaming(getMainActivity(), "ltpinfo", streamingList);
        }
        super.onFragmentPause();
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        if (TRADE_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && NETPOSITIONS_SUMMARY_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                netPositionResponse = (TradeNetPositionSummaryResponse) jsonResponse.getResponse();
                handlePositionsResponse("ALL");
                calculateTotal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        refreshComplete();
    }

    public void calculatedayMTM(List<CustomNetPositionSummary> data) {

        float total = 0;
        float provisionaltotal = 0;
        float bookedtotal = 0;
        float value = 0;

        List<CustomNetPositionSummary> positionSummaries = netPositionResponse.getNetPositionSummary();
        for (CustomNetPositionSummary netPositionSummary : data) {
            total = total + Float.valueOf(netPositionSummary.getMTM());
            if (Integer.parseInt(netPositionSummary.getNetQty()) == 0) {
                bookedtotal = bookedtotal + Float.valueOf(netPositionSummary.getMTM());
            }
            if (Integer.parseInt(netPositionSummary.getNetQty()) != 0) {
                provisionaltotal = provisionaltotal + Float.valueOf(netPositionSummary.getMTM());
            }
        }

        float dayMtm = 0;
        dayMtm = dayMtm + total;
        dayMTM.setText(String.format("%.2f", dayMtm));
        bookedDay.setText(String.format("%.2f", bookedtotal));
        profDay.setText(String.format("%.2f", provisionaltotal));
        totalDay.setText(String.format("%.2f", (bookedtotal + provisionaltotal)));
    }

    private void calculateTotal() {
        double dayBooked = 0;
        double dayProvisional = 0;
        double totalDays;
        double dayBFBooked = 0;
        double dayBFProvisional = 0;
        double totalDaysBF;
        double buyTurnOver = 0;
        double sellTurnOver = 0;
        double dayMtm = 0;
        List<CustomNetPositionSummary> positionSummaries = netPositionResponse.getNetPositionSummary();
        for (CustomNetPositionSummary netPositionSummary : positionSummaries) {
            int prevNetQty = Integer.valueOf(netPositionSummary.getPreNetQty());
            float prevAvgPrice = Float.valueOf(netPositionSummary.getPrevNetAvg());
            int buyQty = Integer.valueOf(netPositionSummary.getBuyQty());
            int sellQty = Integer.valueOf(netPositionSummary.getSellQty());
            float sellAvgPrice = Float.valueOf(netPositionSummary.getSellAvg());
            float buyAvgPrice = Float.valueOf(netPositionSummary.getBuyAvg());

            double NetAmt = (Double.parseDouble(netPositionSummary.getBuyAmt()) - Double.parseDouble(netPositionSummary.getSellAmt()));

            double NetQty = (Integer.parseInt(netPositionSummary.getBuyQty()) - Integer.parseInt(netPositionSummary.getSellQty()));
            double NetPrice;
            double mtm = 0;
            if (NetQty != 0) {
                //NetPrice = NetAmt / NetQty;
                NetPrice = NetAmt / NetQty;
                NetPrice = NetPrice / Double.parseDouble(netPositionSummary.getMultiplier());
            } else {
                NetPrice = 0.0;
            }

            if (NetQty != 0) {
                Double price = (Double.parseDouble(netPositionSummary.getLtp()) - NetPrice);

                if (price == 0.0) {
                    mtm = (0 * (NetQty * Double.parseDouble(netPositionSummary.getMultiplier())));
                } else {

                    if (Double.parseDouble(netPositionSummary.getLtp()) > 0.0) {
                        mtm = ((Double.parseDouble(netPositionSummary.getLtp()) - NetPrice) * (NetQty * Double.parseDouble(netPositionSummary.getMultiplier())));
                    } else if (Double.parseDouble(netPositionSummary.getClose()) > 0.0) {
                        mtm = ((Double.parseDouble(netPositionSummary.getClose()) - NetPrice) * (NetQty * Double.parseDouble(netPositionSummary.getMultiplier())));
                    } else {
                        mtm = 0.00;
                    }
                }
            } else {
                mtm = NetAmt * (-1);

            }

            /*if (NetQty != 0) {
                //mtm = (((Double.parseDouble(model.getBuyAmt())) - (Double.parseDouble(model.getSellAmt())))* -1);
                mtm = ((Double.parseDouble(netPositionSummary.getLtp()) - NetPrice) * (NetQty * Double.parseDouble(netPositionSummary.getMultiplier())));
                //mtm= mtm * Integer.parseInt(model.getMultiplier());
            } else {
                mtm = NetAmt * (-1);
                //mtm= mtm * Integer.parseInt(model.getMultiplier());
            }*/


            float ltp = Float.valueOf(netPositionSummary.getLtp());

            if (Integer.parseInt(netPositionSummary.getNetQty()) == 0) {
                dayBooked = dayBooked + Double.valueOf(mtm);
            }
            if (Integer.parseInt(netPositionSummary.getNetQty()) != 0) {
                dayProvisional = dayProvisional + Double.valueOf(mtm);
            }

            if ((Integer.parseInt(netPositionSummary.getNetQty())) + (Integer.parseInt(netPositionSummary.getPreNetQty())) == 0) {
                dayBFBooked = dayBFBooked + Float.valueOf(netPositionSummary.getOverAllMTM());
            }
            if ((Integer.parseInt(netPositionSummary.getNetQty())) + (Integer.parseInt(netPositionSummary.getPreNetQty())) != 0) {
                dayBFProvisional = dayBFProvisional + Float.valueOf(netPositionSummary.getOverAllMTM());
            }

            //Day's Booked Profit/Loss
            float tempDaysBookedPL = 0;
            if (buyQty >= sellQty) {
                tempDaysBookedPL = sellQty * (sellAvgPrice - buyAvgPrice);
            } else if (buyQty < sellQty) {
                tempDaysBookedPL = buyQty * (sellAvgPrice - buyAvgPrice);
            }

            //Day's + BF Booked Profit/Loss
            float tempBFBookedPL = 0;
            if (prevNetQty > 0 && prevNetQty + buyQty >= sellQty) {
                tempBFBookedPL = sellQty * (sellAvgPrice - buyAvgPrice);
            } else if (prevNetQty < 0) {
                tempBFBookedPL = buyQty * (sellAvgPrice - buyAvgPrice);
            }

            //Day's provisional Profit/Loss
            float tempDaysProvisionalPL = 0;
            if (buyQty > sellQty) {
                tempDaysProvisionalPL = (buyQty - sellQty) * (ltp - buyAvgPrice);
            } else if (buyQty < sellQty) {
                tempDaysProvisionalPL = (sellQty - buyQty) * (sellAvgPrice - ltp);
            }
            //dayProvisional = dayProvisional + tempDaysProvisionalPL;

            //Day's + BF provisional Profit/Loss
            float tempProvisionalPL = 0;
            if (prevNetQty > 0) {
                if (prevNetQty + buyQty > sellQty) {
                    tempProvisionalPL = (prevNetQty + buyQty - sellQty) * (ltp - ((buyQty * buyAvgPrice + prevNetQty * prevAvgPrice) / (buyQty + prevNetQty)));
                } else if (prevNetQty + buyQty < sellQty) {
                    tempProvisionalPL = (sellQty - buyQty + prevNetQty) * (sellAvgPrice - ltp);
                }

            } else if (prevNetQty < 0) {
                if (buyQty > (-sellQty - (prevNetQty))) {
                    tempProvisionalPL = (buyQty + (-sellQty - (prevNetQty))) * (ltp - buyAvgPrice);
                } else if (buyQty < (-sellQty - (prevNetQty))) {
                    tempProvisionalPL = ((-sellQty - (prevNetQty)) - buyQty) * (((sellQty * sellAvgPrice + prevNetQty * prevAvgPrice) / (sellQty + prevNetQty)) - ltp);

                } else {
                    if (buyQty > sellQty) {
                        tempProvisionalPL = (buyQty - sellQty) * (ltp - buyAvgPrice);
                    } else if (buyQty < sellQty) {
                        tempProvisionalPL = (sellQty - buyQty) * (sellAvgPrice - ltp);
                    }
                }
            }
            // dayBFProvisional = dayBFProvisional + tempProvisionalPL;

            //Buy TurnOver
            buyTurnOver = buyTurnOver + (buyAvgPrice * buyQty);
            sellTurnOver = sellTurnOver + (sellAvgPrice * sellQty);

            //TODO Settled MTM

            dayMtm = dayMtm + Double.valueOf(mtm);
        }
        totalDays = dayBooked + dayProvisional;
        totalDaysBF = dayBFBooked + dayBFProvisional;


        bookedDay.setText(String.format("%.2f", dayBooked));
        profDay.setText(String.format("%.2f", dayProvisional));
        totalDay.setText(String.format("%.2f", totalDays));
        bookedDayBF.setText(String.format("%.2f", dayBFBooked));
        profDayBF.setText(String.format("%.2f", dayBFProvisional));
        totalDayBF.setText(String.format("%.2f", totalDaysBF));
        dayMTM.setText(String.format("%.2f", dayMtm));
//        settMTM.setText("");
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), msg, "OK", true, null);
    }

    private void handlePositionsResponse(String type) {
        if (netPositionResponse != null) {
            gainerAdapter.clear();
            List<CustomNetPositionSummary> positionSummaries = netPositionResponse.getNetPositionSummary();
            List<CustomNetPositionSummary> positionSummary = new ArrayList<CustomNetPositionSummary>();
            for (CustomNetPositionSummary netPositionSummary : positionSummaries) {

                if(AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {

                    if (((getProductType(netPositionSummary.getProductType()).equalsIgnoreCase(type)) || "ALL".equalsIgnoreCase(type)) && ((getExchange(netPositionSummary.getToken()).equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString())) || "ALL".equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString()))) {
                        gainerAdapter.addSymbol(netPositionSummary.getToken());
                        positionSummary.add(netPositionSummary);
                    }
                }
                else
                {
                    if (((getProductType(netPositionSummary.getProductType()).equalsIgnoreCase(type)) || "ALL".equalsIgnoreCase(type))) {
                        gainerAdapter.addSymbol(netPositionSummary.getToken());
                        positionSummary.add(netPositionSummary);
                    }
                }
            }
            gainerAdapter.setData(positionSummary);
            gainerAdapter.notifyDataSetChanged();
            noDataFound(gainerAdapter);
        }
    }

    public void noDataFound(NetPositionFragment.TopGainerAdapter adapter) {
        if (adapter.getCount() > 0) {
            errorMsgLayout.setVisibility(View.GONE);
        } else {
            errorMsgLayout.setVisibility(View.VISIBLE);
        }
    }

//    public String getProductType(String type) {
//        if (type.equalsIgnoreCase("1"))
//            return "Intraday";
//        else if (type.equalsIgnoreCase("0"))
//            return "Delivery";
//        else if (type.equalsIgnoreCase("2"))
//            return "MTF";
//        else if (type.equalsIgnoreCase("3"))
//            return "SSEQ";
//        return "";
//    }

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



    private void refreshComplete() {
        if (isWaitingForResponseOnPTR) {
            isWaitingForResponseOnPTR = false;
        }
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        super.showMsgOnScreen(action, msg, jsonResponse);
        refreshComplete();
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        int id = view.getId();
        if (id == R.id.squareOff) {
            if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                bundle.putString("Token", rowData.getToken());
                bundle.putString("ExchangeName", getExchange(rowData.getToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getToken()));
            } else {
                bundle.putString("Token", rowData.getSqoffToken());
                bundle.putString("ExchangeName", getExchange(rowData.getSqoffToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getSqoffToken()));
            }

            if (Integer.parseInt(rowData.getNetQty()) >= 0) {
                bundle.putString("Action", "sell");
            } else {
                bundle.putString("Action", "buy");
            }
            bundle.putString("TradeSymbol", rowData.getTradeSymbol());
            bundle.putString("Product", rowData.getProductType());

            int qty = Integer.parseInt(rowData.getNetQty()) / Integer.parseInt(rowData.getLotQty());
            bundle.putString("Qty", qty + "");
            if (getAssetTypeFromToken(rowData.getToken()).equalsIgnoreCase("commodity")) {
                bundle.putString("NetQty", String.valueOf(qty));
            } else {
                bundle.putString("NetQty", rowData.getNetQty());
            }
            bundle.putString("Price", "");
            bundle.putString("SymbolName", rowData.getTradeSymbol());
            bundle.putString("Lots", rowData.getLotQty());
            bundle.putBoolean("isModifyOrder", false);
            bundle.putBoolean("isSquareOff", true);
            bundle.putString("TickSize", rowData.getTickSize());
            bundle.putString("Multiplier", rowData.getMultiplier());
            navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.plDetails) {
            bundle.putString("TradeSymbol", rowData.getTradeSymbol());
            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx")) {

                if (rowData.getOption_type().equalsIgnoreCase("XX")) {
                    bundle.putString("Symbol", rowData.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rowData.getExpiry_date(), "yyMMM", "bse") + "-" + rowData.getInstrument());
                } else {
                    bundle.putString("Symbol", rowData.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rowData.getExpiry_date(), "yyMMM", "bse") + rowData.getStrike_price() + rowData.getOption_type() + "-" + rowData.getInstrument());
                }


            } else {
                bundle.putString("Symbol", rowData.getDescription());
            }
            bundle.putSerializable("SymbolName", rowData);
            bundle.putString("Request", "PLDetails");
            navigateTo(NAV_TO_PL_DETAILS_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.netQtyDetails) {
            bundle.putString("TradeSymbol", rowData.getTradeSymbol());


            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx")) {

                if (rowData.getOption_type().equalsIgnoreCase("XX")) {
                    bundle.putString("Symbol", rowData.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rowData.getExpiry_date(), "yyMMM", "bse").toUpperCase() + "-" + rowData.getInstrument());
                } else {
                    bundle.putString("Symbol", rowData.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rowData.getExpiry_date(), "yyMMM", "bse").toUpperCase() + rowData.getStrike_price() + rowData.getOption_type() + "-" + rowData.getInstrument());
                }


            } else {
                bundle.putString("Symbol", rowData.getDescription());
            }

            bundle.putSerializable("SymbolName", rowData);
            bundle.putString("Request", "NetQtyDetails");
            navigateTo(NAV_TO_PL_DETAILS_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.viewChart) {
            if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                bundle.putString("Token", rowData.getToken());
                bundle.putString("ExchangeName", getExchange(rowData.getToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getToken()));
            } else {
                bundle.putString("Token", rowData.getSqoffToken());
                bundle.putString("ExchangeName", getExchange(rowData.getSqoffToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getSqoffToken()));
            }
            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx")) {

                bundle.putString("Scrip", rowData.getTradeSymbol() + "-" + DateTimeFormatter.getDateFromTimeStamp(rowData.getExpiry_date(), "dd MMM yyyy", "bse") + "-" + rowData.getInstrument());


            } else {
                bundle.putString("Scrip", rowData.getDescription() + " - " + getExchange(rowData.getToken()));
            }

            bundle.putString("TickSize", rowData.getTickSize());
            bundle.putString("Multiplier", rowData.getMultiplier());
            bundle.putString("TradeSymbol", rowData.getTradeSymbol());
            bundle.putString("Lots", rowData.getLotQty());
            navigateTo(NAV_TO_CHARTS_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.marketDepth) {
            if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                bundle.putString("Symbol", rowData.getToken());
                bundle.putString("ExchangeName", getExchange(rowData.getToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getToken()));
            } else {
                bundle.putString("Symbol", rowData.getSqoffToken());
                bundle.putString("ExchangeName", getExchange(rowData.getSqoffToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getSqoffToken()));
            }


            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx")) {

                bundle.putString("Description", rowData.getTradeSymbol() + "-" + DateTimeFormatter.getDateFromTimeStamp(rowData.getExpiry_date(), "dd MMM yyyy", "bse") + "-" + rowData.getInstrument());


            } else {
                bundle.putString("Description", rowData.getDescription());
            }


            bundle.putString("TickSize", rowData.getTickSize());
            bundle.putString("Multiplier", rowData.getMultiplier());
            navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.product_chg) {
            Bundle args = new Bundle();

            if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                args.putString("gtoken", rowData.getToken());
                args.putString("exchange", getExchange(rowData.getToken()));
                args.putString("assetType", getAssetTypeFromToken(rowData.getToken()));
            } else {
                args.putString("gtoken", rowData.getSqoffToken());
                args.putString("exchange", getExchange(rowData.getSqoffToken()));
                args.putString("assetType", getAssetTypeFromToken(rowData.getSqoffToken()));
            }
            int qtylot = Math.abs(Integer.parseInt(rowData.getNetQty()) / Integer.parseInt(rowData.getLotQty()));
            args.putString("qtylot", String.valueOf(qtylot));
            args.putString("lot", rowData.getLotQty());
            //args.putString("qty", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
            args.putString("product", rowData.getProductType());
            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx") || getExchange(rowData.getToken()).equalsIgnoreCase("ncdex")) {
                args.putString("traded_qty_abs", String.valueOf(qtylot));
                args.putString("traded_qty", String.valueOf(Integer.parseInt(rowData.getNetQty()) / Integer.parseInt(rowData.getLotQty())));
                args.putString("qty", String.valueOf(Integer.parseInt(rowData.getNetQty()) / Integer.parseInt(rowData.getLotQty())));
            } else {
                args.putString("traded_qty_abs", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
                args.putString("traded_qty", rowData.getNetQty());
                args.putString("qty", rowData.getNetQty());


                   /* args.putString("traded_qty_abs", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
                    args.putString("traded_qty", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
                    args.putString("qty", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));*/
            }

            args.putString("gorderid", "");
            args.putString("tradeid", "");
            args.putString("eorderid", "");
            args.putString("side", "");
            args.putString("from", "Netposition");
            CustomNetpositionDialogFragment customNetpositionDialogFragment = new CustomNetpositionDialogFragment();
            customNetpositionDialogFragment.setArguments(args);
            customNetpositionDialogFragment.show(getFragmentManager(), "ProductChange");

            levelDialog.dismiss();
        } else if (id == R.id.order_details) {/*if(AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                    bundle.putString("token", rowData.getToken());
                    bundle.putString("ExchangeName", getExchange(rowData.getToken()));
                    bundle.putString("assetType", getAssetTypeFromToken(rowData.getToken()));
                }
                else {
                    if(rowData.getExchange().equalsIgnoreCase("nse"))
                    {
                        bundle.putString("token", rowData.getNSEToken());
                        bundle.putString("ExchangeName", getExchange(rowData.getNSEToken()));
                        bundle.putString("assetType", getAssetTypeFromToken(rowData.getNSEToken()));
                    }
                    else
                    {
                        bundle.putString("token", rowData.getBSEToken());
                        bundle.putString("ExchangeName", getExchange(rowData.getBSEToken()));
                        bundle.putString("assetType", getAssetTypeFromToken(rowData.getBSEToken()));
                    }
                }*/

            if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                bundle.putString("token", rowData.getToken());
                bundle.putString("ExchangeName", getExchange(rowData.getToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getToken()));
            } else {
                bundle.putString("token", rowData.getSqoffToken());
                bundle.putString("ExchangeName", getExchange(rowData.getSqoffToken()));
                bundle.putString("AssetType", getAssetTypeFromToken(rowData.getSqoffToken()));
            }

            bundle.putString("tradesymbol", rowData.getTradeSymbol());
            bundle.putString("description", rowData.getDescription());
            bundle.putString("expiry", rowData.getExpiry_date());
            bundle.putString("instName", rowData.getInstrument());
            bundle.putString("optionType", rowData.getOption_type());
            bundle.putString("strikePrice", rowData.getStrike_price());

            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx") || getExchange(rowData.getToken()).equalsIgnoreCase("ncdex")) {

                int NetQty = Integer.parseInt(rowData.getNetQty()) / Integer.parseInt(rowData.getLotQty());
                bundle.putString("qty", String.valueOf(NetQty));


            } else {

                bundle.putString("qty", rowData.getNetQty());
            }


            bundle.putString("ProductType", getProductType(rowData.getProductType()));
            ///bundle.putString("netamt", rowData.getNetAmt());
            bundle.putString("netamt", String.format("%.2f", (Double.parseDouble(rowData.getBuyAmt()) - Double.parseDouble(rowData.getSellAmt()))));
            //  Double   NetPrice1 =  Math.abs(Double.parseDouble(rowData.getNetAmt()) / Double.parseDouble(rowData.getNetQty()));
            bundle.putString("netprice", String.format("%.2f", (Double.parseDouble(rowData.getNetAvg()))));
            bundle.putString("LTP", String.format("%.2f", (Double.parseDouble(rowData.getLtp()))));
            bundle.putString("MTM", String.format("%.2f", (Double.parseDouble(rowData.getMTM()))));

            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx") || getExchange(rowData.getToken()).equalsIgnoreCase("ncdex")) {

                int SellQty = Integer.parseInt(rowData.getSellQty()) / Integer.parseInt(rowData.getLotQty());
                bundle.putString("sellqty", String.valueOf(SellQty));

            } else {

                bundle.putString("sellqty", rowData.getSellQty());

            }


            bundle.putString("sellamt", String.format("%.2f", (Double.parseDouble(rowData.getSellAmt()))));

            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx") || getExchange(rowData.getToken()).equalsIgnoreCase("ncdex")) {

                int BuyQty = Integer.parseInt(rowData.getBuyQty()) / Integer.parseInt(rowData.getLotQty());
                bundle.putString("buyqty", String.valueOf(BuyQty));

            } else {
                bundle.putString("buyqty", rowData.getBuyQty());
            }


            bundle.putString("buyamt", String.format("%.2f", (Double.parseDouble(rowData.getBuyAmt()))));
            bundle.putString("from", "netposition");

            //navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
            navigateTo(NAV_TO_POSITION_ORDER_DETAIL_SCREEN, bundle, true);

            levelDialog.dismiss();
        }
    }


    public String getAssetType(String assetType) {
        if (assetType.equalsIgnoreCase("1")) {
            return "Equity";
        }
        if (assetType.equalsIgnoreCase("2")) {
            return "FNO";
        }
        if (assetType.equalsIgnoreCase("3")) {
            return "currency";
        }
        if (assetType.equalsIgnoreCase("4")) {
            return "bse";
        }
        if (assetType.equalsIgnoreCase("5")) {
            return "bfo";
        }
        if (assetType.equalsIgnoreCase("6")) {
            return "6";
        }

        return "";
    }

    public String getAction(String action) {
        if (action.equalsIgnoreCase("1")) {
            return "buy";
        }
        if (action.equalsIgnoreCase("2")) {
            return "sell";
        }
        return "";
    }


    private String getAssetTypeFromToken(String token) {
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


    public class TopGainerAdapter extends BaseAdapter {
        private final Context mContext;
        private List<CustomNetPositionSummary> topGainerList;
        private List<CustomNetPositionSummary> filterList;
        ArrayList<String> tokenList;

        public TopGainerAdapter(Context context, ArrayList<CustomNetPositionSummary> topGainerList) {
            this.mContext = context;
            this.topGainerList = topGainerList;
            this.filterList = topGainerList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<CustomNetPositionSummary> gainerList) {
            this.topGainerList = gainerList;
            this.filterList = gainerList;
            notifyDataSetChanged();
        }

        public void clear() {
            this.topGainerList.clear();
            this.filterList.clear();
            this.tokenList.clear();
            notifyDataSetChanged();
        }

        public void addSymbol(String symbol) {
            tokenList.add(symbol);
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public void updateData(int position, CustomNetPositionSummary model) {
            topGainerList.set(position, model);
        }

        public List<CustomNetPositionSummary> getData() {
            return topGainerList;
        }


        @Override
        public int getCount() {
            return topGainerList.size();
        }

        @Override
        public CustomNetPositionSummary getItem(int position) {
            return topGainerList.get(position);
        }


        public CustomNetPositionSummary getFilterItem(int position) {
            return filterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NetPositionFragment.TopGainerAdapter.Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_positions, parent, false);
                holder.row11 = convertView.findViewById(R.id.row11);
                holder.row12 = convertView.findViewById(R.id.row12);
                holder.row13 = convertView.findViewById(R.id.row13);
                holder.row14 = convertView.findViewById(R.id.row14);
                holder.row21 = convertView.findViewById(R.id.row21);
                holder.row22 = convertView.findViewById(R.id.row22);
                holder.row23 = convertView.findViewById(R.id.row23);
                holder.row24 = convertView.findViewById(R.id.row24);
                convertView.setTag(holder);
            } else {
                holder = (NetPositionFragment.TopGainerAdapter.Holder) convertView.getTag();
            }

            CustomNetPositionSummary model = getItem(position);

//            holder.row11.setText(model.getDescription() + " - " + model.getInstrument());

            if (getExchange(model.getToken()).equalsIgnoreCase("mcx")) {

                if(model.getOption_type().equalsIgnoreCase("XX"))
                {
                    holder.row11.setText(model.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(model.getExpiry_date(), "yyMMM", "bse").toUpperCase() + "-" + model.getInstrument());
                }
                else {
                    holder.row11.setText(model.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(model.getExpiry_date(), "yyMMM", "bse").toUpperCase() +model.getStrike_price()+model.getOption_type()+ "-" + model.getInstrument());
                }


                holder.row12.setText(String.valueOf((Integer.parseInt(model.getNetQty())) / (Integer.parseInt(model.getLotQty()))));

            } else if (getExchange(model.getToken()).equalsIgnoreCase("ncdex")) {

                holder.row11.setText(model.getDescription() + " - " + model.getInstrument());

                holder.row12.setText(String.valueOf((Integer.parseInt(model.getNetQty())) / (Integer.parseInt(model.getLotQty()))));

            } else {

                holder.row12.setText(model.getNetQty());
                holder.row11.setText(model.getDescription() + " - " + model.getInstrument());

            }

            if(AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                holder.row21.setText(getExchange(model.getToken()));
            }
            else
            {
                holder.row21.setText(getExchange(model.getSqoffToken()));
            }

            holder.row22.setText(getProductType(model.getProductType()));
            int NetQty;
            double NetPrice, mtm, NetAmt;

            NetAmt = (Double.parseDouble(model.getBuyAmt()) - Double.parseDouble(model.getSellAmt()));

            NetQty = (Integer.parseInt(model.getBuyQty()) - Integer.parseInt(model.getSellQty()));
            if (NetQty != 0) {
                //NetPrice = NetAmt / NetQty;
                if (((Integer.valueOf(model.getToken()) >= 502000000) && (Integer.valueOf(model.getToken()) <= 502999999)) || ((Integer.valueOf(model.getToken()) >= 1302000000) && (Integer.valueOf(model.getToken()) <= 1302999999))) {
                    String netavg = String.format("%.4f", Double.parseDouble(model.getNetAvg()));
                    NetPrice = Double.parseDouble(netavg);
                } else {
                    String netavg = String.format("%.2f", Double.parseDouble(model.getNetAvg()));
                    NetPrice = Double.parseDouble(netavg);
                }

                /*if (!getExchange(model.getToken()).equalsIgnoreCase("mcx")) {
                    NetPrice = NetAmt / NetQty;
                    NetPrice = NetPrice / Double.parseDouble(model.getMultiplier());
                }*/
            } else {
                NetPrice = 0.0;
            }


            if (NetQty != 0) {
                //mtm = (((Double.parseDouble(model.getBuyAmt())) - (Double.parseDouble(model.getSellAmt())))* -1);
                Double price = (Double.parseDouble(model.getLtp()) - NetPrice);

                if (price == 0.0) {
                    mtm = (0 * (NetQty * Double.parseDouble(model.getMultiplier())));
                } else {

                    if (Double.parseDouble(model.getLtp()) > 0.0) {
                        mtm = ((Double.parseDouble(model.getLtp()) - NetPrice) * (NetQty * Double.parseDouble(model.getMultiplier())));
                    } else if (Double.parseDouble(model.getClose()) > 0.0) {
                        mtm = ((Double.parseDouble(model.getClose()) - NetPrice) * (NetQty * Double.parseDouble(model.getMultiplier())));
                    } else {
                        mtm = 0.00;
                    }

                    //mtm = ((Double.parseDouble(model.getLtp()) - NetPrice) * (NetQty * Integer.parseInt(model.getMultiplier()) ));
                }
                //mtm = ((Double.parseDouble(model.getLtp()) - NetPrice) * (NetQty * Integer.parseInt(model.getMultiplier()) ));
                //mtm= mtm * Integer.parseInt(model.getMultiplier());
            } else {
                mtm = NetAmt * (-1);

                /*if (getExchange(model.getToken()).equalsIgnoreCase("mcx")) {
                    mtm = mtm * Double.parseDouble(model.getMultiplier());
                }*/
            }

            model.setMTM(String.valueOf(mtm));

            if (((Integer.valueOf(model.getToken()) >= 502000000) && (Integer.valueOf(model.getToken()) <= 502999999)) || ((Integer.valueOf(model.getToken()) >= 1302000000) && (Integer.valueOf(model.getToken()) <= 1302999999))) {
                holder.row13.setText(String.format("%.4f", NetPrice));
                holder.row14.setText(String.format("%.4f", Double.parseDouble(model.getLtp())));
                holder.row24.setText(String.format("%.4f", mtm));
                holder.row23.setText(String.format("%.4f", NetAmt));
            } else {
                holder.row13.setText(String.format("%.2f", NetPrice));
                holder.row14.setText(String.format("%.2f", Double.parseDouble(model.getLtp())));
                holder.row24.setText(String.format("%.2f", mtm));
                holder.row23.setText(String.format("%.2f", NetAmt));
            }

            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
//                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.row11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row14.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row21.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row22.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row23.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row24.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.row11.setTextColor(getResources().getColor(textColor));
                holder.row12.setTextColor(getResources().getColor(textColor));
                holder.row13.setTextColor(getResources().getColor(textColor));
                holder.row14.setTextColor(getResources().getColor(textColor));
                holder.row21.setTextColor(getResources().getColor(textColor));
                holder.row22.setTextColor(getResources().getColor(textColor));
                holder.row23.setTextColor(getResources().getColor(textColor));
                holder.row24.setTextColor(getResources().getColor(textColor));
            }

            return convertView;
        }

        public class Holder {
            GreekTextView row11, row12, row13, row14, row21, row22, row23, row24;
        }
    }
}
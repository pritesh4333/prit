package com.acumengroup.mobile.reports;

import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by user on 28-Sep-17.
 */

public class CumulativePositionFragment extends GreekBaseFragment implements View.OnClickListener {

    private final String[] allTypes = {"ALL"};
    private final String[] bseCashTypes = {"ALL", "Intraday", "Delivery", "SELL from DP"};
    private final String[] nseCashTypes = {"ALL", "Intraday", "Delivery", "MarginPlus", "SELL from DP"};
    private final String[] nseFnoTypes = {"ALL", "Intraday", "MarginPlus"};
    private final String[] nseCurrTypes = {"ALL", "Intraday"};
    private final String[] mcxComTypes = {"ALL", "Intraday", "ValuePlus"};

    private final List<String> selectedTypes = new ArrayList<>();
    private final List<String> exchangeList = new ArrayList<>();
    private Spinner exchangeSpinner, typeSpinner;
    private SwipeRefreshLayout swipeRefresh;
    private ListView positionList;
    private RelativeLayout errorMsgLayout;
    private View netPositionsView;
    private ArrayList streamingList;
    private CumulativePositionFragment.TopGainerAdapter gainerAdapter;
    private AlertDialog levelDialog;
    CustomNetPositionSummary rowData = null;
    private final AdapterView.OnItemClickListener positionsItemSelectionListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            rowData = gainerAdapter.getFilterItem(position);
            View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_three_actions, parent, false);
            GreekTextView sqOffTxt = layout.findViewById(R.id.action_item1);
            GreekTextView chartsTxt = layout.findViewById(R.id.action_item2);
            GreekTextView mdText = layout.findViewById(R.id.action_item3);
            GreekTextView productText = layout.findViewById(R.id.action_item4);
            GreekTextView detailText = layout.findViewById(R.id.action_item5);

            detailText.setVisibility(View.VISIBLE);

            productText.setVisibility(View.GONE);
            sqOffTxt.setText("Square Off");
            chartsTxt.setText("View Chart");
            mdText.setText("Market Depth");
            detailText.setText("Details");
            productText.setVisibility(View.GONE);

            sqOffTxt.setOnClickListener(CumulativePositionFragment.this);
            chartsTxt.setOnClickListener(CumulativePositionFragment.this);
            mdText.setOnClickListener(CumulativePositionFragment.this);
            detailText.setOnClickListener(CumulativePositionFragment.this);

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
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }
        }
    };
    private TradeNetPositionSummaryResponse netPositionResponse;
    private final AdapterView.OnItemSelectedListener exchangesSelectedListener = new AdapterView.OnItemSelectedListener() {

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
    private final AdapterView.OnItemSelectedListener typesSelectedListener = new AdapterView.OnItemSelectedListener() {

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
    private GreekTextView pltxt, daytxt, daybftxt, bookedtxt, provisionaltxt, broughtforwardtxt, grosstotxt, nettotxt, daymtmtxt;
    private Boolean isWaitingForResponseOnPTR = false;
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

            attachLayout(R.layout.fragment_positions_fno).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_positions_fno).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            LinearLayout header_layout = netPositionsView.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg)); //e020 c@1 c@2
        }
        AccountDetails.currentFragment = NAV_TO_CUMULATIVE_SCREEN;
        AccountDetails.AutoRefreshForNetPosition = true;
        setUp();
        return netPositionsView;
    }

    private void setUp() {
        setupViews(netPositionsView);
    }


    private void setupViews(View parent) {
        setAppTitle(getClass().toString(), "Cumulative Positions");
        getExchangeList();
        exchangeSpinner = parent.findViewById(R.id.exchangesSpinner);
        ArrayAdapter<String> exchangesAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangeList);
        exchangesAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(exchangesAdapter);
        exchangeSpinner.setOnItemSelectedListener(exchangesSelectedListener);

        typeSpinner = parent.findViewById(R.id.typeSpinner);
        selectedTypes.add("ALL");
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

    private void setupAdapter() {

        gainerAdapter = new CumulativePositionFragment.TopGainerAdapter(getMainActivity(), new ArrayList<CustomNetPositionSummary>());
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


    private void sendPTRRequest() {
        if (!isWaitingForResponseOnPTR) {
            //orderStreamingController.sendStreamingGrossExposureRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()));
            gainerAdapter.clear();
            gainerAdapter.notifyDataSetChanged();
            showProgress();
            isWaitingForResponseOnPTR = true;

            String serviceURL = "getDerivativePosition?SessionId=" + AccountDetails.getSessionId(getActivity()) + "&ClientCode=" + AccountDetails.getClientCode(getActivity());
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
                                /*streamingList.add(netPositionResponse.getNetPositionSummary().get(i).getToken());
                                gainerAdapter.addSymbol(netPositionResponse.getNetPositionSummary().get(i).getToken());*/
                                hideProgress();
                            }
                            //sendStreamingRequest();
                        }

                        handlePositionsResponse(typeSpinner.getSelectedItem().toString());
                        //calculateTotal();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    refreshComplete();
                }

                @Override
                public void onFailure(String message) {
                    refreshComplete();
                    errorMsgLayout.setVisibility(View.VISIBLE);
                    isWaitingForResponseOnPTR = false;
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


    public void onEventMainThread(GrossExposureResponse grossExposureResponse) {
        try {
            grossTo.setText(grossExposureResponse.getGrossExposure());
            netTo.setText(grossExposureResponse.getNetTurnOver());

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    private String calculateMtm(CustomNetPositionSummary row) {
        String mtm;

        int NetQty;
        Double NetPrice;

        NetQty = (Integer.parseInt(row.getBuyQty()) - Integer.parseInt(row.getSellQty()));

        if (NetQty != 0) {
            //NetPrice = NetAmt / NetQty;
            //NetPrice = NetAmt / NetQty;
            NetPrice = Double.parseDouble(row.getNetAvg());
            /*if (!getExchange(row.getToken()).equalsIgnoreCase("mcx")) {
                NetPrice = NetPrice / Double.parseDouble(row.getMultiplier());
            }*/
        } else {
            NetPrice = 0.0;
        }

        Double price = 0.0;

        if (Integer.parseInt(row.getNetQty()) == 0) {
            mtm = String.valueOf((Double.parseDouble(row.getBuyAmt()) - Double.parseDouble(row.getSellAmt())) * -1);
        } else if (Integer.parseInt(row.getNetQty()) > 0) {

            if (Double.parseDouble(row.getLtp()) > 0.0) {
                price = (Double.parseDouble(row.getLtp()) - NetPrice);
            } else if (Double.parseDouble(row.getClose()) > 0.0) {
                price = (Double.parseDouble(row.getClose()) - NetPrice);
            }


            if (price == 0.0) {
                mtm = String.valueOf(0 * Integer.parseInt(row.getNetQty()) * Double.parseDouble(row.getMultiplier()));
            } else {
                if (Double.parseDouble(row.getLtp()) > 0.0) {
                    mtm = String.valueOf((Double.parseDouble(row.getLtp()) - NetPrice) * (NetQty * Double.parseDouble(row.getMultiplier())));
                } else if (Double.parseDouble(row.getClose()) > 0.0) {
                    mtm = String.valueOf((Double.parseDouble(row.getClose()) - NetPrice) * (NetQty * Double.parseDouble(row.getMultiplier())));
                } else {
                    mtm = "0.00";
                }
            }

        } else {

            if (Double.parseDouble(row.getLtp()) > 0.0) {
                price = (Double.parseDouble(row.getLtp()) - NetPrice);
            } else {
                price = (Double.parseDouble(row.getClose()) - NetPrice);
            }

            if (price == 0.0) {
                mtm = String.valueOf(0 * Integer.parseInt(row.getNetQty()) * Double.parseDouble(row.getMultiplier()));
            } else {
                if (Double.parseDouble(row.getLtp()) > 0.0) {
                    mtm = String.valueOf((Double.parseDouble(row.getLtp()) - NetPrice) * (NetQty * Double.parseDouble(row.getMultiplier())));
                } else if (Double.parseDouble(row.getClose()) > 0.0) {
                    mtm = String.valueOf((Double.parseDouble(row.getClose()) - NetPrice) * (NetQty * Double.parseDouble(row.getMultiplier())));
                } else {
                    mtm = "0.00";
                }
            }
        }

        return mtm;
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        float total = 0;
        float provisionaltotal = 0;
        float bookedtotal = 0;
        float value = 0;

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
                }
            }
        }

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_CUMULATIVE_SCREEN;
        EventBus.getDefault().register(this);

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
                //calculateTotal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        refreshComplete();
    }

    public void calculatedayMTM(float Mtm, float bookedday, float profday) {
        float dayMtm = 0;
        dayMtm = dayMtm + Mtm;
        dayMTM.setText(String.format("%.2f", dayMtm));
        bookedDay.setText(String.format("%.2f", bookedday));
        profDay.setText(String.format("%.2f", profday));
        totalDay.setText(String.format("%.2f", (bookedday + profday)));
    }


    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), msg, "OK", true, null);
    }

    private void noDataFound(TopGainerAdapter gainerAdapter) {
        if (gainerAdapter.getCount() > 0) {
            errorMsgLayout.setVisibility(View.GONE);
        } else {
            errorMsgLayout.setVisibility(View.VISIBLE);
        }
    }

    private void handlePositionsResponse(String type) {
        if (netPositionResponse != null) {
            if (streamingList != null) {
                streamController.pauseStreaming(getMainActivity(), "ltpinfo", streamingList);
            }
            gainerAdapter.clear();
            streamingList.clear();
            List<CustomNetPositionSummary> positionSummaries = netPositionResponse.getNetPositionSummary();
            List<CustomNetPositionSummary> positionSummary = new ArrayList<CustomNetPositionSummary>();

            for (CustomNetPositionSummary netPositionSummary : positionSummaries) {

                if (((getProductType(netPositionSummary.getProduct()).equalsIgnoreCase(type)) || "ALL".equalsIgnoreCase(type)) && ((getExchange(netPositionSummary.getToken()).equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString())) || "ALL".equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString()))) {
                    gainerAdapter.addSymbol(netPositionSummary.getToken());
                    positionSummary.add(netPositionSummary);
                    streamingList.add(netPositionSummary.getToken());

                }
            }
            gainerAdapter.setData(positionSummary);
            gainerAdapter.notifyDataSetChanged();
            if (streamingList.size() > 0) {
                sendStreamingRequest();
            }
            noDataFound(gainerAdapter);
        }
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
        if (id == R.id.action_item1) {
            bundle.putString("Token", rowData.getToken());
            if (Integer.parseInt(rowData.getNetQty()) >= 0) {
                bundle.putString("Action", "sell");
            } else {
                bundle.putString("Action", "buy");
            }
            bundle.putString("TradeSymbol", rowData.getTradeSymbol());
            bundle.putString("Product", rowData.getProduct());
            bundle.putString("ExchangeName", getExchange(rowData.getToken()));
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
            bundle.putString("AssetType", getAssetTypeFromToken(rowData.getToken()));
            bundle.putBoolean("isModifyOrder", false);
            bundle.putBoolean("isSquareOff", true);
            bundle.putString("TickSize", rowData.getTickSize());
            bundle.putString("Multiplier", rowData.getMultiplier());
            navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.action_item2) {
            bundle.putString("Scrip", rowData.getDescription() + " - " + getExchange(rowData.getToken()));
            bundle.putString("Exchange", getExchange(rowData.getToken()));
            bundle.putString("Token", rowData.getToken());
            bundle.putString("TickSize", rowData.getTickSize());
            bundle.putString("Multiplier", rowData.getMultiplier());
            bundle.putString("TradeSymbol", rowData.getTradeSymbol());
            bundle.putString("Lots", rowData.getLotQty());
            bundle.putString("AssetType", getAssetTypeFromToken(rowData.getToken()));
            navigateTo(NAV_TO_CHARTS_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.action_item3) {
            bundle.putString("Symbol", rowData.getToken());
            bundle.putString("Description", rowData.getDescription());
            bundle.putString("TickSize", rowData.getTickSize());
            bundle.putString("Multiplier", rowData.getMultiplier());
            bundle.putString("AssetType", getAssetTypeFromToken(rowData.getToken()));
            bundle.putString("ExchangeName", getExchange(rowData.getToken()));
            navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.action_item5) {
            bundle.putString("tradesymbol", rowData.getTradeSymbol());
            bundle.putString("description", rowData.getDescription());
            bundle.putString("expiry", rowData.getExpiry_date());
            bundle.putString("instName", rowData.getInstrument());
            bundle.putString("strikePrice", rowData.getStrike_price());
            bundle.putString("optionType", rowData.getOption_type());
            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx") || getExchange(rowData.getToken()).equalsIgnoreCase("ncdex")) {

                int NetQty = Integer.parseInt(rowData.getNetQty()) / Integer.parseInt(rowData.getLotQty());
                bundle.putString("qty", String.valueOf(NetQty));
            } else {
                bundle.putString("qty", rowData.getNetQty());
            }
            bundle.putString("ExchangeName", getExchange(rowData.getToken()));
            bundle.putString("ProductType", getProductType(rowData.getProduct()));
            ///bundle.putString("netamt", rowData.getNetAmt());
            bundle.putString("netamt", String.format("%.2f", (Double.parseDouble(rowData.getBuyAmt()) - Double.parseDouble(rowData.getSellAmt()))));
            //  Double   NetPrice1 =  Math.abs(Double.parseDouble(rowData.getNetAmt()) / Double.parseDouble(rowData.getNetQty()));
            bundle.putString("netprice", String.format("%.2f", (Double.parseDouble(rowData.getNetAvg()))));
            bundle.putString("LTP", String.format("%.2f", (Double.parseDouble(rowData.getLtp()))));
            bundle.putString("MTM", calculateMtm(rowData));
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
            bundle.putString("token", rowData.getToken());
            bundle.putString("from", "cumposition");
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
            CumulativePositionFragment.TopGainerAdapter.Holder holder;
            if (convertView == null) {
                holder = new CumulativePositionFragment.TopGainerAdapter.Holder();
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
                holder = (CumulativePositionFragment.TopGainerAdapter.Holder) convertView.getTag();
            }

            CustomNetPositionSummary model = getItem(position);


            if (getExchange(model.getToken()).equalsIgnoreCase("MCX")) {

                if(model.getOption_type().equalsIgnoreCase("XX"))
                {
                    holder.row11.setText(model.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(model.getExpiry_date(), "yyMMM", "bse").toUpperCase() + " - " + model.getInstrument());
                }
                else {
                    holder.row11.setText(model.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(model.getExpiry_date(), "yyMMM", "bse").toUpperCase() +model.getStrike_price()+model.getOption_type()+ " - " + model.getInstrument());
                }



            } else {

                holder.row11.setText(model.getDescription() + " - " + model.getInstrument());
            }

            if (getExchange(model.getToken()).equalsIgnoreCase("mcx") || getExchange(model.getToken()).equalsIgnoreCase("ncdex")) {
                int NetQty = Integer.parseInt(model.getNetQty()) / Integer.parseInt(model.getLotQty());
                holder.row12.setText(String.valueOf(NetQty));
            } else {

                holder.row12.setText(model.getNetQty());
            }

            holder.row21.setText(getExchange(model.getToken()));
            holder.row22.setText(getProductType(model.getProduct()));
            int NetQty;
            double NetPrice, mtm, NetAmt;

            /*if(Integer.parseInt(model.getNetQty()) == 0)
                mtm=(Double.parseDouble(model.getBuyAmt()) - Double.parseDouble(model.getSellAmt())) * -1;
            else if(Integer.parseInt(model.getNetQty()) > 0)
                mtm=(Double.parseDouble(model.getLtp()) - Double.parseDouble(model.getBuyAvg())) * Integer.parseInt(model.getNetQty());
            else
                mtm=(Double.parseDouble(model.getLtp()) - Double.parseDouble(model.getSellAvg())) * Integer.parseInt(model.getNetQty());*/

            NetAmt = (Double.parseDouble(model.getBuyAmt()) - Double.parseDouble(model.getSellAmt()));

            NetQty = (Integer.parseInt(model.getBuyQty()) - Integer.parseInt(model.getSellQty()));
            if (NetQty != 0) {
                //NetPrice = NetAmt / NetQty;
                //NetPrice = NetAmt / NetQty;
                NetPrice = Double.parseDouble(model.getNetAvg());
                /*if (!getExchange(model.getToken()).equalsIgnoreCase("mcx")) {
                    NetPrice = NetPrice / Double.parseDouble(model.getMultiplier());
                }*/
            } else {
                NetPrice = 0.0;
            }


            if (((Integer.valueOf(model.getToken()) >= 502000000) && (Integer.valueOf(model.getToken()) <= 502999999)) || ((Integer.valueOf(model.getToken()) >= 1302000000) && (Integer.valueOf(model.getToken()) <= 1302999999))) {
                holder.row13.setText(String.format("%.4f", Double.parseDouble(String.valueOf(NetPrice))));
                holder.row14.setText(String.format("%.4f", Double.parseDouble(model.getLtp())));
                holder.row24.setText(String.format("%.4f", Double.parseDouble(calculateMtm(model))));
                holder.row23.setText(String.format("%.4f", NetAmt));
            } else {
                holder.row13.setText(String.format("%.2f", Double.parseDouble(String.valueOf(NetPrice))));
                holder.row14.setText(String.format("%.2f", Double.parseDouble(model.getLtp())));
                holder.row24.setText(String.format("%.2f", Double.parseDouble(calculateMtm(model))));
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
                holder.row24.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row23.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.row11.setTextColor(getResources().getColor(textColor));
                holder.row12.setTextColor(getResources().getColor(textColor));
                holder.row13.setTextColor(getResources().getColor(textColor));
                holder.row14.setTextColor(getResources().getColor(textColor));
                holder.row21.setTextColor(getResources().getColor(textColor));
                holder.row22.setTextColor(getResources().getColor(textColor));
                holder.row24.setTextColor(getResources().getColor(textColor));
                holder.row23.setTextColor(getResources().getColor(textColor));
            }

            return convertView;
        }

        public class Holder {
            GreekTextView row11, row12, row13, row14, row21, row22, row23, row24;
        }
    }

    public void onEventMainThread(ProductChangeResponse productChangeResponse) {
        try {
            sendPTRRequest();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}

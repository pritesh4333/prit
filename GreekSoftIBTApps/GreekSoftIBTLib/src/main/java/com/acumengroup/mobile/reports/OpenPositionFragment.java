package com.acumengroup.mobile.reports;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
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
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.model.tradenetpositionsummary.OverAllPL;
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
import com.acumengroup.ui.adapter.GreekPopulationListener;
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
 * Created by sushant.patil on 8/4/2016.
 */
public class OpenPositionFragment extends GreekBaseFragment implements View.OnClickListener {

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
    private int clickedPos;
    private ArrayList streamingList;
    private AlertDialog levelDialog;
    private GreekTextView pltxt, daytxt, daybftxt, bookedtxt, provisionaltxt, grosstotxt, nettotxt, daymtmtxt;
    private GreekCommonAdapter<CustomNetPositionSummary> netPosAdapter;
    private final AdapterView.OnItemClickListener positionsItemSelectionListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

            clickedPos = position;
            final CustomNetPositionSummary rowData = netPosAdapter.getFilterItem(position);
            View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_three_actions, parent, false);
            GreekTextView sqOffTxt = layout.findViewById(R.id.action_item1);
            GreekTextView chartsTxt = layout.findViewById(R.id.action_item2);
            GreekTextView mdText = layout.findViewById(R.id.action_item3);
            GreekTextView pdChangeText = layout.findViewById(R.id.action_item4);
            GreekTextView detailsText = layout.findViewById(R.id.action_item5);

            detailsText.setVisibility(View.VISIBLE);

            pdChangeText.setVisibility(View.GONE);
            sqOffTxt.setText("Square Off");
            chartsTxt.setText("View Chart");
            mdText.setText("Market Depth");
            pdChangeText.setText("Product Change");
            detailsText.setText("Details");

            sqOffTxt.setOnClickListener(OpenPositionFragment.this);
            chartsTxt.setOnClickListener(OpenPositionFragment.this);
            mdText.setOnClickListener(OpenPositionFragment.this);
            pdChangeText.setOnClickListener(OpenPositionFragment.this);
            detailsText.setOnClickListener(OpenPositionFragment.this);

            if (Integer.parseInt(rowData.getNetQty()) == 0) {
                sqOffTxt.setEnabled(false);
                sqOffTxt.setTextColor(getResources().getColor(R.color.light_gray));
            } else {
                sqOffTxt.setEnabled(true);
                sqOffTxt.setTextColor(getResources().getColor(R.color.black));
            }
            builder.setView(layout);
            levelDialog = builder.create();
            levelDialog.show();
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
    private Boolean isWaitingForResponseOnPTR = false;
    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            sendPTRRequest();
        }
    };

    private void noDataFound(GreekCommonAdapter adapter) {
        if (adapter.getCount() > 0) {
            errorMsgLayout.setVisibility(View.GONE);
        } else {
            errorMsgLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        netPositionsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_positions_fno).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_positions_fno).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = GREEK_MENU_OPENPOSITION;

        setupViews(netPositionsView);

        return netPositionsView;
    }

    private void setupViews(View parent) {
        setAppTitle(getClass().toString(), "Open Positions");
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
        // settMTM = (GreekTextView) parent.findViewById(R.id.settMTM);

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

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            LinearLayout header_layout = parent.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

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
        int[] newsViewIDs = {R.id.row11, R.id.row12, R.id.row13, R.id.row14, R.id.row21, R.id.row22, R.id.row23, R.id.row24};

        netPosAdapter = new GreekCommonAdapter<>(getMainActivity(), new ArrayList<CustomNetPositionSummary>());

        netPosAdapter.setLayoutTextViews(R.layout.row_positions, newsViewIDs);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            netPosAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
        } else {
            netPosAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        }
        netPosAdapter.setPopulationListener(new GreekPopulationListener<CustomNetPositionSummary>() {

            @Override
            public void populateFrom(View v, int position, CustomNetPositionSummary row, View[] views) {


                if (row.getLtp() != null && row.getDescription() == null) {
                    if (((Integer.valueOf(row.getToken()) >= 502000000) && (Integer.valueOf(row.getToken()) <= 502999999)) || ((Integer.valueOf(row.getToken()) >= 1302000000) && (Integer.valueOf(row.getToken()) <= 1302999999))) {

                        ((GreekTextView) views[3]).setText(String.format("%.4f", Double.parseDouble(row.getLtp())));

                        ((GreekTextView) views[7]).setText(calculateMtm(row));

                    } else {

                        ((GreekTextView) views[3]).setText(String.format("%.2f", Double.parseDouble(row.getLtp())));
                        ((GreekTextView) views[7]).setText(calculateMtm(row));
                    }
                } else if (row.getDescription() != null && row.getNetQty() != null && row.getNetAvg() != null && row.getExchange() != null && row.getLtp() != null && row.getProductType() != null && row.getBPL() != null && row.getMTM() != null) {

                    Double NetAmt, NetPrice;
                    int NetQty;
                    NetAmt = (Double.parseDouble(row.getBuyAmt()) - Double.parseDouble(row.getSellAmt()));

                    NetQty = (Integer.parseInt(row.getBuyQty()) - Integer.parseInt(row.getSellQty()));
                    if (NetQty != 0) {

                        NetPrice = Double.parseDouble(row.getNetAvg());

                    } else {
                        NetPrice = 0.0;
                    }

                    if (getExchange(row.getToken()).equalsIgnoreCase("mcx")) {

                        if(row.getOptionType().equalsIgnoreCase("XX"))
                        {
                            ((GreekTextView) views[0]).setText(row.getTradeSymbol() + "-" + DateTimeFormatter.getDateFromTimeStamp(row.getExpiry_date(), "yyMMM", "bse").toUpperCase() + "-" + "M" + " - " + row.getInstrument());
                        }
                        else {
                            ((GreekTextView) views[0]).setText(row.getTradeSymbol() + "-" + DateTimeFormatter.getDateFromTimeStamp(row.getExpiry_date(), "yyMMM", "bse").toUpperCase()+row.getStrikePrice()+row.getOptionType() + "-" + "M" + " - " + row.getInstrument());
                        }



                    } else {
                        ((GreekTextView) views[0]).setText(row.getDescription() + " - " + row.getInstrument());

                    }

                    if (getExchange(row.getToken()).equalsIgnoreCase("mcx") || getExchange(row.getToken()).equalsIgnoreCase("ncdex")) {

                        NetQty = NetQty / Integer.parseInt(row.getLotQty());
                        ((GreekTextView) views[1]).setText(String.valueOf(NetQty));


                    } else {

                        ((GreekTextView) views[1]).setText(String.valueOf(NetQty));

                    }


                    if (((Integer.valueOf(row.getToken()) >= 502000000) && (Integer.valueOf(row.getToken()) <= 502999999)) || ((Integer.valueOf(row.getToken()) >= 1302000000) && (Integer.valueOf(row.getToken()) <= 1302999999))) {
                        ((GreekTextView) views[2]).setText(String.format("%.4f", Double.parseDouble(String.valueOf(NetPrice))));
                        ((GreekTextView) views[3]).setText(String.format("%.4f", Double.parseDouble(row.getLtp())));
                        ((GreekTextView) views[6]).setText(String.format("%.4f", NetAmt));

                    } else {
                        ((GreekTextView) views[2]).setText(String.format("%.2f", Double.parseDouble(String.valueOf(NetPrice))));
                        ((GreekTextView) views[3]).setText(String.format("%.2f", Double.parseDouble(row.getLtp())));
                        ((GreekTextView) views[6]).setText(String.format("%.2f", NetAmt));
                    }

                    ((GreekTextView) views[4]).setText(getExchange(row.getToken()));
                    ((GreekTextView) views[5]).setText(getProductType(row.getProduct()));
                    ((GreekTextView) views[7]).setText(String.format("%.2f", Double.parseDouble(calculateMtm(row))));
                }


                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {


                    ((GreekTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[5]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[7]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[6]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

                    ((GreekTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[5]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[7]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[6]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }

            @Override
            public void onRowCreate(View[] views) {
            }
        });

        positionList.setAdapter(netPosAdapter);
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
            sendPTRRequest();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        if (netPosAdapter.containsSymbol(response.getSymbol())) {
            CustomNetPositionSummary rowData = netPosAdapter.getItemElem(netPosAdapter.indexOf(response.getSymbol()));
            CustomNetPositionSummary customNetPositionSummary = new CustomNetPositionSummary();
            customNetPositionSummary = rowData;
            customNetPositionSummary.setToken(response.getSymbol());
            customNetPositionSummary.setLtp(response.getLast());

            if (!rowData.getNetQty().equals("0")) {
                customNetPositionSummary.setMTM(String.format("%.2f", Double.parseDouble(rowData.getNetQty()) * (Double.parseDouble(response.getLast()) - Double.parseDouble(rowData.getNetAvg()))));
            } else {
                customNetPositionSummary.setMTM(String.format("%.2f", Double.parseDouble(rowData.getNetQty()) * (-1)));
            }
            netPosAdapter.set(netPosAdapter.indexOf(response.getSymbol()), customNetPositionSummary);
            netPosAdapter.notifyDataSetChanged();
        }
    }

    private String calculateMtm(CustomNetPositionSummary row) {
        String mtm;
        int NetQty;
        Double NetPrice, NetAmt;

        NetAmt = (Double.parseDouble(row.getBuyAmt()) - Double.parseDouble(row.getSellAmt()));
        NetQty = (Integer.parseInt(row.getBuyQty()) - Integer.parseInt(row.getSellQty()));

        if (NetQty != 0) {

            NetPrice = Double.parseDouble(row.getNetAvg());

        } else {
            NetPrice = 0.0;
        }

        if (Integer.parseInt(row.getNetQty()) == 0) {
            mtm = String.valueOf((Double.parseDouble(row.getBuyAmt()) - Double.parseDouble(row.getSellAmt())) * -1);

        } else if (Integer.parseInt(row.getNetQty()) > 0) {
            Double price = (Double.parseDouble(row.getLtp()) - NetPrice);

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

            Double price = (Double.parseDouble(row.getLtp()) - NetPrice);

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

                //mtm = String.valueOf((Double.parseDouble(row.getLtp()) - Double.parseDouble(row.getSellAvg())) * (Integer.parseInt(row.getNetQty()) * Integer.parseInt(row.getMultiplier())));

                //mtm = String.valueOf((Double.parseDouble(row.getLtp()) - NetPrice) * (NetQty * Integer.parseInt(row.getMultiplier())));
            }


            // mtm = String.valueOf((Double.parseDouble(row.getLtp()) - Double.parseDouble(row.getSellAvg())) * Integer.parseInt(row.getNetQty()));
        }

        return mtm;
    }

    private void sendPTRRequest() {
        if (!isWaitingForResponseOnPTR) {
            netPosAdapter.clear();
            netPosAdapter.notifyDataSetChanged();
            showProgress();
            isWaitingForResponseOnPTR = true;

            String serviceURL = "getOpenPosition?SessionId=" + AccountDetails.getSessionId(getActivity()) + "&ClientCode=" + AccountDetails.getClientCode(getActivity());
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
                               /* streamingList.add(netPositionResponse.getNetPositionSummary().get(i).getToken());
                                netPosAdapter.addSymbol(netPositionResponse.getNetPositionSummary().get(i).getToken());*/
                                hideProgress();
                            }
                            //sendStreamingRequest();
                        }

//                        handlePositionsResponse("ALL");
                        handlePositionsResponse(typeSpinner.getSelectedItem().toString());
                        calculateTotal();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        refreshComplete();
                    }
                    refreshComplete();
                }

                @Override
                public void onFailure(String message) {
                    hideProgress();
                    errorMsgLayout.setVisibility(View.VISIBLE);
                    refreshComplete();
                }
            });
        }
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = GREEK_MENU_OPENPOSITION;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onFragmentPause() {
        if (streamController != null && streamingList != null) {
            streamController.pauseStreaming(getMainActivity(), "ltpinfo", streamingList);
        }
        EventBus.getDefault().unregister(this);
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

    private void calculateTotal() {
        float dayBooked = 0;
        float dayProvisional = 0;
        float totalDays;
        float dayBFBooked = 0;
        float dayBFProvisional = 0;
        float totalDaysBF;
        float buyTurnOver = 0;
        float sellTurnOver = 0;
        float grossTurnOver;
        float netTurnOver;
        float dayMtm = 0;
        List<CustomNetPositionSummary> positionSummaries = netPositionResponse.getNetPositionSummary();
        for (CustomNetPositionSummary netPositionSummary : positionSummaries) {
            int prevNetQty = Integer.valueOf(netPositionSummary.getPreNetQty());
            float prevAvgPrice = Float.valueOf(netPositionSummary.getPrevNetAvg());
            int buyQty = Integer.valueOf(netPositionSummary.getBuyQty());
            int sellQty = Integer.valueOf(netPositionSummary.getSellQty());
            float sellAvgPrice = Float.valueOf(netPositionSummary.getSellAvg());
            float buyAvgPrice = Float.valueOf(netPositionSummary.getBuyAvg());

            float ltp = Float.valueOf(netPositionSummary.getLtp());

            //Day's Booked Profit/Loss
            float tempDaysBookedPL = 0;
            if (buyQty >= sellQty) {
                tempDaysBookedPL = sellQty * (sellAvgPrice - buyAvgPrice);
            } else if (buyQty < sellQty) {
                tempDaysBookedPL = buyQty * (sellAvgPrice - buyAvgPrice);
            }
            dayBooked = dayBooked + tempDaysBookedPL;

            //Day's + BF Booked Profit/Loss
            float tempBFBookedPL = 0;
            if (prevNetQty > 0 && prevNetQty + buyQty >= sellQty) {
                tempBFBookedPL = sellQty * (sellAvgPrice - buyAvgPrice);
            } else if (prevNetQty < 0) {
                tempBFBookedPL = buyQty * (sellAvgPrice - buyAvgPrice);
            }
            dayBFBooked = dayBFBooked + tempBFBookedPL;

            //Day's provisional Profit/Loss
            float tempDaysProvisionalPL = 0;
            if (buyQty > sellQty) {
                tempDaysProvisionalPL = (buyQty - sellQty) * (ltp - buyAvgPrice);
            } else if (buyQty < sellQty) {
                tempDaysProvisionalPL = (sellQty - buyQty) * (sellAvgPrice - ltp);
            }
            dayProvisional = dayProvisional + tempDaysProvisionalPL;

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
            dayBFProvisional = dayBFProvisional + tempProvisionalPL;

            //Buy TurnOver
            buyTurnOver = buyTurnOver + (buyAvgPrice * buyQty);
            sellTurnOver = sellTurnOver + (sellAvgPrice * sellQty);

            //TODO Settled MTM

            dayMtm = dayMtm + Float.valueOf(netPositionSummary.getMTM());
        }
        totalDays = dayBooked + dayProvisional;
        totalDaysBF = dayBFBooked + dayBFProvisional;
        grossTurnOver = buyTurnOver + sellTurnOver;
        netTurnOver = buyTurnOver - sellTurnOver;

        bookedDay.setText(String.format("%.2f", dayBooked));
        profDay.setText(String.format("%.2f", dayProvisional));
        totalDay.setText(String.format("%.2f", totalDays));
        bookedDayBF.setText(String.format("%.2f", dayBFBooked));
        profDayBF.setText(String.format("%.2f", dayBFProvisional));
        totalDayBF.setText(String.format("%.2f", totalDaysBF));
        grossTo.setText(String.format("Gross To :%.2f", grossTurnOver));
        netTo.setText(String.format("Net To :%.2f", netTurnOver));
        dayMTM.setText(String.format("Day MTM :%.2f", dayMtm));
//        settMTM.setText("");
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), msg, "OK", true, null);
    }

    private void handlePositionsResponse(String type) {
        if (netPositionResponse != null) {
            netPosAdapter.clear();
            streamingList.clear();
            netPosAdapter.notifyDataSetChanged();
            List<CustomNetPositionSummary> positionSummaries = netPositionResponse.getNetPositionSummary();
            for (CustomNetPositionSummary netPositionSummary : positionSummaries) {
                if (((getProductType(netPositionSummary.getProduct()).equalsIgnoreCase(type)) || "ALL".equalsIgnoreCase(type))
                        && ((getExchange(netPositionSummary.getToken()).equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString()))
                        || "ALL".equalsIgnoreCase(exchangeSpinner.getSelectedItem().toString()))) {

                    netPosAdapter.add(netPositionSummary);
                    streamingList.add(netPositionSummary.getToken());
                    netPosAdapter.addSymbol(netPositionSummary.getToken());

                }
            }
            netPosAdapter.notifyDataSetChanged();
            if(streamingList.size() > 0)
            {
                sendStreamingRequest();

            }
            noDataFound(netPosAdapter);
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
        final CustomNetPositionSummary rowData = netPosAdapter.getFilterItem(clickedPos);
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
        } else if (id == R.id.action_item4) {
            Bundle args = new Bundle();
            int qtylot = Math.abs(Integer.parseInt(rowData.getNetQty()) / Integer.parseInt(rowData.getLotQty()));
            args.putString("qtylot", String.valueOf(qtylot));
            args.putString("lot", rowData.getLotQty());
            args.putString("qty", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
            args.putString("product", rowData.getProductType());
            args.putString("traded_qty_abs", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
            args.putString("traded_qty", rowData.getNetQty());
            args.putString("gtoken", rowData.getToken());
            CustomNetpositionDialogFragment customNetpositionDialogFragment = new CustomNetpositionDialogFragment();
            customNetpositionDialogFragment.setArguments(args);
            customNetpositionDialogFragment.show(getFragmentManager(), "ProductChange");

            levelDialog.dismiss();
        } else if (id == R.id.action_item5) {
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
            bundle.putString("from", "openposition");

            //navigateTo(NAV_TO_MARKET_DEPTH_SCREEN, bundle, true);
            navigateTo(NAV_TO_POSITION_ORDER_DETAIL_SCREEN, bundle, true);

            levelDialog.dismiss();
        }
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
}

package com.acumengroup.mobile.reports;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity.NetQtyDetails;
import com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity.ProfitLoss;
import com.acumengroup.greekmain.core.model.tradeprofitlossandnetquantity.TradeProfitLossAndNetQuantityResponse;
import com.acumengroup.greekmain.core.services.CustomNetPositionSummary;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class PLDetailsNetPositionsFragment extends GreekBaseFragment {

    private View plDetails;
    private GreekTextView bookedDay;
    private GreekTextView bookedDayBF;
    private GreekTextView profDay;
    private GreekTextView profDayBF;
    private GreekTextView totalDay;
    private GreekTextView totalDayBF;
    private GreekTextView netAvgQtyTxt;
    private GreekTextView netAvgPriceTxt;
    private int request;
    private ArrayList streamingList;
    private GreekTextView symbolName, header11, header12, header13, col11, col12, col13, bottomTxt;
    CustomNetPositionSummary customNetPositionSummary = new CustomNetPositionSummary();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        plDetails = super.onCreateView(inflater, container, savedInstanceState);

        //attachLayout(R.layout.fragment_pl_details);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_pl_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_pl_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setupViews();
        setuptheme();

        return plDetails;
    }

    private void setuptheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            symbolName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bookedDay.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bookedDayBF.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            profDay.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            profDayBF.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalDay.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalDayBF.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            header11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            header12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            header13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            col11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            col12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            col13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            netAvgQtyTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            netAvgPriceTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bottomTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


        }
    }

    private void setupViews() {

        symbolName = plDetails.findViewById(R.id.symbolName);
        bookedDay = plDetails.findViewById(R.id.bookedDayTxt);
        bookedDayBF = plDetails.findViewById(R.id.bookedDayBFTxt);
        profDay = plDetails.findViewById(R.id.profDayTxt);
        profDayBF = plDetails.findViewById(R.id.profDayBFTxt);
        totalDay = plDetails.findViewById(R.id.totalDayTxt);
        totalDayBF = plDetails.findViewById(R.id.totalDayBFTxt);
        header11 = plDetails.findViewById(R.id.header11);
        header12 = plDetails.findViewById(R.id.header12);
        header13 = plDetails.findViewById(R.id.header13);
        col11 = plDetails.findViewById(R.id.column11);
        col12 = plDetails.findViewById(R.id.column12);
        col13 = plDetails.findViewById(R.id.column13);
        LinearLayout netQtyLayout = plDetails.findViewById(R.id.netQtyLayout);
        LinearLayout plDetailsLayout = plDetails.findViewById(R.id.plDetailsLayout);
        netAvgQtyTxt = plDetails.findViewById(R.id.netAvgQtyTxt);
        netAvgPriceTxt = plDetails.findViewById(R.id.netAvgPriceTxt);
        bottomTxt = plDetails.findViewById(R.id.bottomTxt);

        if (getArguments().getString("Request").equals("NetQtyDetails")) {
            setAppTitle(getClass().toString(), "Net Quantity Details");
            request = 0;
            header11.setText("");
            header12.setText("Net Qty");
            header13.setText("Net Avg Price");
            col11.setText("Current Day's");
            col12.setText("Brought Forward");

            netQtyLayout.setVisibility(View.VISIBLE);
            plDetailsLayout.setVisibility(View.GONE);
            bottomTxt.setVisibility(View.GONE);
        } else if (getArguments().getString("Request").equals("PLDetails")) {
            setAppTitle(getClass().toString(), "Profit Loss Details");
            request = 1;
            header11.setText("Profit/Loss");
            header12.setText("Day's");
            header13.setText("Day's + B/F*");
            col11.setText("Booked");
            col12.setText("Provisional");
            col13.setText("Total");
            col13.setTextColor(getResources().getColor(R.color.common_red_bg));
            totalDay.setTextColor(getResources().getColor(R.color.common_red_bg));
            totalDayBF.setTextColor(getResources().getColor(R.color.common_red_bg));
            netQtyLayout.setVisibility(View.GONE);
            plDetailsLayout.setVisibility(View.VISIBLE);
            bottomTxt.setVisibility(View.VISIBLE);
        }
        customNetPositionSummary = (CustomNetPositionSummary) getArguments().getSerializable("SymbolName");
        calculateTotal((CustomNetPositionSummary) getArguments().getSerializable("SymbolName"));
        symbolName.setText(getArguments().getString("Symbol"));
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        if (TRADE_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && PF_NETQTY_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                TradeProfitLossAndNetQuantityResponse netQuantityResponse = (TradeProfitLossAndNetQuantityResponse) jsonResponse.getResponse();
                if (request == 1) {
                    setAppTitle(getClass().toString(), "Profit Loss Details");
                    ProfitLoss profitLoss = netQuantityResponse.getProfitLoss();
                    bookedDay.setText(profitLoss.getBooked_days());
                    bookedDayBF.setText(profitLoss.getBooked_days_bf());
                    profDay.setText(profitLoss.getProv_days());
                    profDayBF.setText(profitLoss.getProv_days_bf());
                    totalDay.setText(profitLoss.getTotal_days());
                    totalDayBF.setText(profitLoss.getTotal_days_bf());
                } else if (request == 0) {
                    NetQtyDetails netEty = netQuantityResponse.getNetQtyDetails();
                    bookedDay.setText(netEty.getSettlementNetQty());
                    bookedDayBF.setText(netEty.getSettlementNetAvgPrice());
                    profDay.setText(netEty.getBoughtForwardNetQty());
                    profDayBF.setText(netEty.getBoughtForwardNetAvgPrice());
                    totalDay.setText(netEty.getCurrentDaysNetQty());
                    totalDayBF.setText(netEty.getCurrentDaysNetAvgPrice());
                    netAvgQtyTxt.setText(netEty.getMyAvgNetQty());
                    netAvgPriceTxt.setText(netEty.getMyNetAvgPrice());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hideProgress();
        super.handleResponse(response);
    }

    private void sendStreamingRequest(String token) {
        streamingList = new ArrayList();
        streamingList.add(token);
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

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        if (streamingList.get(0).equals(response.getSymbol())) {
            customNetPositionSummary.setLtp(response.getLast());
            if (customNetPositionSummary.getNetQty().equals("0")) {
                customNetPositionSummary.setMTM(String.format("%.2f", (Double.parseDouble(customNetPositionSummary.getSellAmt()) - Double.parseDouble(customNetPositionSummary.getBuyAmt()))));
            } else {
                int multiplier = Integer.parseInt(customNetPositionSummary.getMultiplier());
                double Ltp = Double.parseDouble(response.getLast());
                int NetQty = Integer.parseInt(customNetPositionSummary.getNetQty());
                double netAvg = Double.parseDouble(customNetPositionSummary.getNetAvg());
                customNetPositionSummary.setMTM(String.format("%.2f", ((Ltp - netAvg) * NetQty) * multiplier));
            }

            if (customNetPositionSummary.getAssetType().equalsIgnoreCase("3") || customNetPositionSummary.getAssetType().equalsIgnoreCase("6")) {

                bookedDay.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getBPL())));
                profDay.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getMTM())));
                totalDay.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getBPL()) + Double.parseDouble(customNetPositionSummary.getMTM())));

            } else {

                bookedDay.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getBPL())));
                profDay.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getMTM())));
                totalDay.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getBPL()) + Double.parseDouble(customNetPositionSummary.getMTM())));

            }
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
    public void onFragmentResume() {
        super.onFragmentResume();

        EventBus.getDefault().register(this);
        if (streamingList != null) {
            streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
            addToStreamingList("ltpinfo", streamingList);
        }

    }

    private void calculateTotal(CustomNetPositionSummary netPositionSummary) {

        int netQty , prevNetQty = 0;
        if(getExchange(netPositionSummary.getToken()).equalsIgnoreCase("mcx") || getExchange(netPositionSummary.getToken()).equalsIgnoreCase("ncdex")) {
            netQty = Integer.valueOf(netPositionSummary.getNetQty()) / Integer.valueOf(netPositionSummary.getLotQty());
            prevNetQty = Integer.valueOf(netPositionSummary.getPreNetQty()) / Integer.valueOf(netPositionSummary.getLotQty());
        }
        else
        {
            netQty = Integer.valueOf(netPositionSummary.getNetQty());
            prevNetQty = Integer.valueOf(netPositionSummary.getPreNetQty());
        }
        float prevAvgPrice = Float.valueOf(netPositionSummary.getPrevNetAvg());

        if (request == 1) {
            sendStreamingRequest(netPositionSummary.getToken());
            double bpl = Double.valueOf(netPositionSummary.getBPL());
            double mtm = Double.valueOf(netPositionSummary.getMTM());
            if (netQty != 0 && prevNetQty != 0) {
                if (netPositionSummary.getAssetType().equalsIgnoreCase("3") || netPositionSummary.getAssetType().equalsIgnoreCase("6") || netPositionSummary.getAssetType().equalsIgnoreCase("currency")) {
                    bookedDay.setText("0.0000");
                    profDay.setText("0.0000");
                    totalDay.setText("0.0000");
                    bookedDayBF.setText(String.format("%.4f", Double.valueOf(netPositionSummary.getBPL())));
                    profDayBF.setText(String.format("%.4f", Double.valueOf(netPositionSummary.getMTM())));
                    totalDayBF.setText(String.format("%.4f", bpl + mtm));
                } else {
                    bookedDay.setText("0.00");
                    profDay.setText("0.00");
                    totalDay.setText("0.00");
                    bookedDayBF.setText(String.format("%.2f", Double.valueOf(netPositionSummary.getBPL())));
                    profDayBF.setText(String.format("%.2f", Double.valueOf(netPositionSummary.getMTM())));
                    totalDayBF.setText(String.format("%.2f", bpl + mtm));
                }
            } else {
                if (netPositionSummary.getAssetType().equalsIgnoreCase("3") || netPositionSummary.getAssetType().equalsIgnoreCase("6") || netPositionSummary.getAssetType().equalsIgnoreCase("currency")) {
                    bookedDay.setText(String.format("%.4f", Double.parseDouble(netPositionSummary.getMTM())));
                    profDay.setText(String.format("%.4f", Double.parseDouble(netPositionSummary.getBPL())));
                    totalDay.setText(String.format("%.4f", bpl + mtm));
                    bookedDayBF.setText("0.0000");
                    profDayBF.setText("0.0000");
                    totalDayBF.setText("0.0000");
                } else {
                    bookedDay.setText(String.format("%.2f", Double.parseDouble(netPositionSummary.getMTM())));
                    profDay.setText(String.format("%.2f", Double.parseDouble(netPositionSummary.getBPL())));
                    totalDay.setText(String.format("%.2f", bpl + mtm));
                    bookedDayBF.setText("0.00");
                    profDayBF.setText("0.00");
                    totalDayBF.setText("0.00");
                }
            }
        } else {


            int totalQ = netQty + prevNetQty;
            double dayTotal = Double.parseDouble(netPositionSummary.getNetAvg()) * netQty;
            double dayBFTotal = prevAvgPrice * prevNetQty;
            double myAvg = (dayTotal + dayBFTotal) / totalQ;

            if (netPositionSummary.getAssetType().equalsIgnoreCase("3") || netPositionSummary.getAssetType().equalsIgnoreCase("6") || netPositionSummary.getAssetType().equalsIgnoreCase("currency")) {
                netAvgQtyTxt.setText(String.valueOf(totalQ));
                netAvgPriceTxt.setText(String.format("%.4f", myAvg));
                bookedDay.setText(String.valueOf(netQty));
                bookedDayBF.setText(String.format("%.4f", Double.parseDouble(netPositionSummary.getNetAvg())));
                profDay.setText(String.valueOf(prevNetQty));
                profDayBF.setText(String.format("%.4f", Double.parseDouble(String.valueOf(prevAvgPrice))));
            } else {
                netAvgQtyTxt.setText(String.valueOf(totalQ));
                netAvgPriceTxt.setText(String.format("%.2f", myAvg));
                bookedDay.setText(String.valueOf(netQty));
                bookedDayBF.setText(String.format("%.2f", Double.parseDouble(netPositionSummary.getNetAvg())));
                profDay.setText(String.valueOf(prevNetQty));
                profDayBF.setText(String.format("%.2f", Double.parseDouble(String.valueOf(prevAvgPrice))));
            }
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
        //  return "";
    }
}
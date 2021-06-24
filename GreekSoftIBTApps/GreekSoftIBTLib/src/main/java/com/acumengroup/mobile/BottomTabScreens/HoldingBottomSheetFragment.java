package com.acumengroup.mobile.BottomTabScreens;

import android.os.Bundle;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;

import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.DematHolding;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class HoldingBottomSheetFragment extends BottomSheetDialogFragment {

    private View orderDetailsView;
    private LinearLayout hederLayout;
    private NestedScrollView bottom_sheet_layout;
    private GreekBaseActivity greekBaseActivity;
    private Boolean changeColor = true;
    private GreekTextView txt_ltp,title2, title1, title3, title4, title5, title6, title7, title8, title9, title10, title11, title12, title13,
            title14, symbolnametxt, exchangetxt, ltptxt, totalbuyQtytxt, totalsellQtytxt, totalbuyAmttxt, totalBuyAtpTxt, totalSellAtpTxt, totalsellAmttxt, totalNetQtytxt, netHoldingQtyTxt, netHoldingValueTxt, netHoldingPriceTxt, soldQtyTxt, pendingQtyTxt, riskBlockQtyTxt, freeHoldingQtyTxt;
    private Button sqrOffBtn, prodChangeBtn;
    private DematHolding dematHolding;
    private StreamingController streamingController;
    private ArrayList<String> sym = new ArrayList<>();


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AccountDetails.setIsholdingbottomsheetshow(false);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        orderDetailsView = inflater.inflate(R.layout.fragment_holding_bottom_sheet, container, false);
        setupView();

        return orderDetailsView;
    }


    private void setupView() {
        streamingController = new StreamingController();
        hederLayout = orderDetailsView.findViewById(R.id.hederLayout);
        bottom_sheet_layout = orderDetailsView.findViewById(R.id.bottom_sheet_layout);
        symbolnametxt = orderDetailsView.findViewById(R.id.symbolnametxt);
        exchangetxt = orderDetailsView.findViewById(R.id.exchangetxt);


        txt_ltp = orderDetailsView.findViewById(R.id.txt_ltp);
        title2 = orderDetailsView.findViewById(R.id.titile2);
        title1 = orderDetailsView.findViewById(R.id.titile1);
        title3 = orderDetailsView.findViewById(R.id.titile3);
        title4 = orderDetailsView.findViewById(R.id.titile4);
        title5 = orderDetailsView.findViewById(R.id.titile5);
        title6 = orderDetailsView.findViewById(R.id.titile6);
        title7 = orderDetailsView.findViewById(R.id.titile7);
        title8 = orderDetailsView.findViewById(R.id.titile8);
        title9 = orderDetailsView.findViewById(R.id.titile9);
        title10 = orderDetailsView.findViewById(R.id.titile10);
        title11 = orderDetailsView.findViewById(R.id.titile11);
        title12 = orderDetailsView.findViewById(R.id.titile12);
        title13 = orderDetailsView.findViewById(R.id.titile13);
        title14 = orderDetailsView.findViewById(R.id.titile14);

        ltptxt = orderDetailsView.findViewById(R.id.ltptxt);
        totalbuyQtytxt = orderDetailsView.findViewById(R.id.totalbuyQtytxt);
        totalsellQtytxt = orderDetailsView.findViewById(R.id.totalsellQtytxt);
        totalbuyAmttxt = orderDetailsView.findViewById(R.id.totalbuyAmttxt);
        totalBuyAtpTxt = orderDetailsView.findViewById(R.id.totalBuyAtpTxt);
        totalSellAtpTxt = orderDetailsView.findViewById(R.id.totalSellAtpTxt);
        totalsellAmttxt = orderDetailsView.findViewById(R.id.totalsellAmttxt);
        totalNetQtytxt = orderDetailsView.findViewById(R.id.totalNetQtytxt);
        netHoldingQtyTxt = orderDetailsView.findViewById(R.id.netHoldingQtyTxt);
        netHoldingValueTxt = orderDetailsView.findViewById(R.id.netHoldingValueTxt);
        netHoldingPriceTxt = orderDetailsView.findViewById(R.id.netHoldingPriceTxt);
        soldQtyTxt = orderDetailsView.findViewById(R.id.soldQtyTxt);
        pendingQtyTxt = orderDetailsView.findViewById(R.id.pendingQtyTxt);
        riskBlockQtyTxt = orderDetailsView.findViewById(R.id.riskBlockQtyTxt);
        freeHoldingQtyTxt = orderDetailsView.findViewById(R.id.freeHoldingQtyTxt);

        dematHolding = (DematHolding) getArguments().getSerializable("response");
        parseReceivedArguments();
        setTheme();
    }

    private void setTheme() {

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            hederLayout.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            bottom_sheet_layout.setBackgroundColor(getResources().getColor(R.color.white));
            symbolnametxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ltptxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalbuyQtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalsellQtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalbuyAmttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalBuyAtpTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalSellAtpTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalsellAmttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalNetQtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            netHoldingQtyTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            netHoldingValueTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            netHoldingPriceTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            soldQtyTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pendingQtyTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            riskBlockQtyTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            freeHoldingQtyTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            txt_ltp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title5.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title6.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title7.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title8.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title9.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title10.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title14.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        }
    }

    private void parseReceivedArguments() {

        if (!dematHolding.getNSEToken().isEmpty()) {
            if (getExchange(dematHolding.getNSEToken()).equalsIgnoreCase("mcx")) {
                symbolnametxt.setText(dematHolding.getScripName());
                exchangetxt.setText(getExchange(dematHolding.getNSEToken()));
            } else {
                symbolnametxt.setText(dematHolding.getNSETradeSymbol());
                exchangetxt.setText(getExchange(dematHolding.getNSEToken()));
            }
            if (getAssetTypeFromToken(dematHolding.getNSEToken()).equalsIgnoreCase("currency")) {
                ltptxt.setText(getResources().getString(R.string.rupee_symbol) + "" + String.format("%.4f", Double.parseDouble(dematHolding.getLTP())));
                totalbuyAmttxt.setText(String.format("%.4f", Double.parseDouble(dematHolding.getTBuyAmt())));
                totalBuyAtpTxt.setText(String.format("%.4f", Double.parseDouble(dematHolding.getTBuyATP())));
                totalSellAtpTxt.setText(String.format("%.4f", Double.parseDouble(dematHolding.getTSellATP())));
                totalsellAmttxt.setText(String.format("%.4f", Double.parseDouble(dematHolding.getTSellAmt())));
                netHoldingValueTxt.setText(String.format("%.4f", Double.parseDouble(dematHolding.getNetHValue())));

                if (dematHolding.getNetHPrice() != null && !dematHolding.getNetHPrice().equalsIgnoreCase("null")) {
                    netHoldingPriceTxt.setText(String.format("%.4f", Double.parseDouble(dematHolding.getNetHPrice())));
                }

            } else {
                ltptxt.setText(getResources().getString(R.string.rupee_symbol) + "" + String.format("%.2f", Double.parseDouble(dematHolding.getLTP())));
                totalbuyAmttxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getTBuyAmt())));
                totalBuyAtpTxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getTBuyATP())));
                totalSellAtpTxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getTSellATP())));
                totalsellAmttxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getTSellAmt())));
                netHoldingValueTxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getNetHValue())));
                netHoldingPriceTxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getNetHPrice())));

                if (dematHolding.getNetHPrice() != null && !dematHolding.getNetHPrice().equalsIgnoreCase("null")) {
                    netHoldingPriceTxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getNetHPrice())));
                }
            }

            totalbuyQtytxt.setText(dematHolding.getTBuyQty());
            totalsellQtytxt.setText(dematHolding.getTSellQty());
            totalNetQtytxt.setText(dematHolding.getTNQ());
            netHoldingQtyTxt.setText(dematHolding.getNetHQty());
            soldQtyTxt.setText(dematHolding.getSoldQty());
            pendingQtyTxt.setText(dematHolding.getPendingQty());
            riskBlockQtyTxt.setText(dematHolding.getRiskBlockQty());
            freeHoldingQtyTxt.setText(dematHolding.getFreeHoldingQty());


            sym.clear();
            sym.add(dematHolding.getNSEToken());
            streamingController.sendStreamingRequest(getActivity(), sym, "ltpinfo", null, null, false);
        } else {
            if (getExchange(dematHolding.getBSEToken()).equalsIgnoreCase("mcx")) {
                symbolnametxt.setText(dematHolding.getScripName());
                exchangetxt.setText(getExchange(dematHolding.getBSEToken()));
            } else {
                symbolnametxt.setText(dematHolding.getBSETradeSymbol());
                exchangetxt.setText(getExchange(dematHolding.getBSEToken()));
            }
            if (getAssetTypeFromToken(dematHolding.getBSEToken()).equalsIgnoreCase("currency")) {
                ltptxt.setText(getResources().getString(R.string.rupee_symbol) + "" + String.format("%.4f", Double.parseDouble(dematHolding.getLTP())));
                //mtmtxt.setText(String.format("%.4f", Double.parseDouble(dematHolding.getMTM())));
            } else {
                ltptxt.setText(getResources().getString(R.string.rupee_symbol) + "" + String.format("%.2f", Double.parseDouble(dematHolding.getLTP())));
                //mtmtxt.setText(String.format("%.2f", Double.parseDouble(dematHolding.getMTM())));
            }

            sym.clear();
            sym.add(dematHolding.getBSEToken());
            streamingController.sendStreamingRequest(getActivity(), sym, "ltpinfo", null, null, false);
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

    private void updateBroadcastData(StreamerBroadcastResponse broadcastResponse) {

        if (dematHolding != null && dematHolding.getNSEToken().equalsIgnoreCase(broadcastResponse.getSymbol())) {


            ltptxt.setText(getResources().getString(R.string.rupee_symbol) + "" + String.format("%.2f", Double.parseDouble(broadcastResponse.getLast())));


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
    }


}
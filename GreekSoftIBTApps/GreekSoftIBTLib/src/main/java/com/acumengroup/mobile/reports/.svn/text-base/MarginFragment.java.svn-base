package com.acumengroup.mobile.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.network.MarginDetailRequest;
import com.acumengroup.greekmain.core.network.MarginDetailResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MarginFragment extends GreekBaseFragment {
    private View marginView;
    private GreekTextView fundUtilizedValue, realizedM2M, unrealizedM2M;
    private GreekTextView availCashCreditValue, collateralValue, equitySellCreditValue;
    OrderStreamingController orderStreamingController;
    GreekTextView marginExchangeType, fundutilized, unrealizedmtm, realizedmtm;
    GreekTextView availCashText, collateralText, equitySellCreditText;
    private Spinner segmentspinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        marginView = super.onCreateView(inflater, container, savedInstanceState);

//        attachLayout(R.layout.fragment_margin_details);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_margin).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_margin).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_MARGIN_SUMMARY;
        hideAppTitle();
        setAppTitle(getClass().toString(), "Margin Details");
        orderStreamingController = new OrderStreamingController();
        setupViews(marginView);
        return marginView;
    }

    private void setupViews(View marginView) {
        segmentspinner = marginView.findViewById(R.id.segmentspinner);
        availCashCreditValue = marginView.findViewById(R.id.avail_cash_value);
        collateralValue = marginView.findViewById(R.id.collateral_value);
        equitySellCreditValue = marginView.findViewById(R.id.equity_sell_credit_value);
        fundUtilizedValue = marginView.findViewById(R.id.fund_utilized_value);
        realizedM2M = marginView.findViewById(R.id.realized_m2m_value);
        unrealizedM2M = marginView.findViewById(R.id.unrealized_m2m_value);

        availCashText = marginView.findViewById(R.id.avail_cash_text);
        collateralText = marginView.findViewById(R.id.collateral_text);
        equitySellCreditText = marginView.findViewById(R.id.equity_sell_credit_text);

        fundutilized = marginView.findViewById(R.id.fund_utilized);
        unrealizedmtm = marginView.findViewById(R.id.unrealized_m2m);
        realizedmtm = marginView.findViewById(R.id.realized_m2m);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            fundUtilizedValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            realizedM2M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            unrealizedM2M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            fundutilized.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            unrealizedmtm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            realizedmtm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            availCashCreditValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            collateralValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            equitySellCreditValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            availCashText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            collateralText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            equitySellCreditText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }

        ArrayList exchList = new ArrayList();
        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse || AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo || AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            exchList.add("NON-COMMODITY");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            exchList.add("COMMODITY");
        }

        if (exchList.size() == 2) {
            exchList.add(0, "ALL");
        }


        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchList);
        groupAdapter.setDropDownViewResource(R.layout.custom_spinner);
        segmentspinner.setAdapter(groupAdapter);
        segmentspinner.setOnItemSelectedListener(segmentItemSelectedListener);

        sendMarginRequest("0");
    }

    private final AdapterView.OnItemSelectedListener segmentItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (segmentspinner.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
                sendMarginRequest("0");
            } else if (segmentspinner.getSelectedItem().toString().equalsIgnoreCase("COMMODITY")) {
                sendMarginRequest("1");
            } else if (segmentspinner.getSelectedItem().toString().equalsIgnoreCase("NON-COMMODITY")) {
                sendMarginRequest("2");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();

    }

    public void onEventMainThread(MarginDetailResponse marginDetailResponse) {
        try {
            fundUtilizedValue.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getFundUtilized())));
            realizedM2M.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getRealizedM2M())));
            unrealizedM2M.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getUnRealizedM2M())));

            availCashCreditValue.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getAvailCashCredit())));
            collateralValue.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getCollateralValue())));
            equitySellCreditValue.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getEquitySellCredit())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendMarginRequest(String exchangeType) {

        //setAppTitle(getClass().toString(),"Margin Details");
        MarginDetailRequest marginDetailRequest = new MarginDetailRequest();
        marginDetailRequest.setGcid(AccountDetails.getClientCode(getMainActivity()));
        marginDetailRequest.setSegment(exchangeType);
        marginDetailRequest.setExchange_type("0");
        orderStreamingController.sendMarginDetailRequest(getActivity(), marginDetailRequest);
    }
}
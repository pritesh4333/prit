package com.acumengroup.mobile.BottomTabScreens;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.portfoliotrending.HoldingDataRequest;
import com.acumengroup.greekmain.core.model.portfoliotrending.HoldingDataResponse;
import com.acumengroup.greekmain.core.network.HoldingValueresponse;
import com.acumengroup.greekmain.core.network.MarginDetailRequest;
import com.acumengroup.greekmain.core.network.MarginDetailResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class DashboardTabFragment extends GreekBaseFragment {

    private View dashboardView;
    private GreekTextView netMtmTxt, totalHoldingTxt, availableLimitTxt, openingBaltxt, marginTxt,
            payinamtTxt, spanExposureTxt, collateralTxt, optionPremiumTxt, creditStockSoldTxt, deliveryTxt,
            creditOptionSellTxt, cashavailtxt, payouttxt, realizedMtmTxt, unrealizedMtmTxt, titleSixteen,
            titleFifteen, titleFourteen, titleOne, titleTwo, titleThree, titleFour, titleFive, titleSix,
            view1, view2, view3, view4, view5, view6, titleSeven, titleEight, titleNine, titleten, titleeleven,
            titleTwele, titlethirteen;

    private GreekButton addFundBtn;
    private LinearLayout positionlayoutTxt, holdinglayoutTxt, DashboardLayout, available_layout, cashLayout;
    private Handler handler;
    private OrderStreamingController orderStreamingController = new OrderStreamingController();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            sendMarginRequest("2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dashboardView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_dashboard_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_dashboard_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

//        AccountDetails.currentFragment = NAV_TO_PORTFOLIO_DASHBOARD_SCREEN;

        SetUpView(dashboardView);
        setTheme();
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

//        HoldingDataRequest.sendRequest(AccountDetails.getSessionId(getMainActivity()), AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);


        return dashboardView;
    }

    private void SetUpView(View dashboardView) {

        DashboardLayout = dashboardView.findViewById(R.id.DashboardLayout);
        cashLayout = dashboardView.findViewById(R.id.cashLayout);
        available_layout = dashboardView.findViewById(R.id.available_layout);
        addFundBtn = dashboardView.findViewById(R.id.addFundBtn);
        cashavailtxt = dashboardView.findViewById(R.id.cashavailtxt);
        netMtmTxt = dashboardView.findViewById(R.id.netMtmTxt);
        totalHoldingTxt = dashboardView.findViewById(R.id.totalHoldingTxt);
        availableLimitTxt = dashboardView.findViewById(R.id.availableLimitTxt);
        openingBaltxt = dashboardView.findViewById(R.id.openingBaltxt);
        marginTxt = dashboardView.findViewById(R.id.marginTxt);
        payinamtTxt = dashboardView.findViewById(R.id.payinamtTxt);
        spanExposureTxt = dashboardView.findViewById(R.id.spanExposureTxt);
        collateralTxt = dashboardView.findViewById(R.id.collateralTxt);
        optionPremiumTxt = dashboardView.findViewById(R.id.optionPremiumTxt);
        creditStockSoldTxt = dashboardView.findViewById(R.id.creditStockSoldTxt);
        deliveryTxt = dashboardView.findViewById(R.id.deliveryTxt);
        creditOptionSellTxt = dashboardView.findViewById(R.id.creditOptionSellTxt);
        unrealizedMtmTxt = dashboardView.findViewById(R.id.unrealizedMtmTxt);
        realizedMtmTxt = dashboardView.findViewById(R.id.realizedMtmTxt);
        payouttxt = dashboardView.findViewById(R.id.payouttxt);
        positionlayoutTxt = dashboardView.findViewById(R.id.positionlayoutTxt);
        holdinglayoutTxt = dashboardView.findViewById(R.id.holdinglayoutTxt);


        view1 = dashboardView.findViewById(R.id.view1);
        view2 = dashboardView.findViewById(R.id.view2);
        view3 = dashboardView.findViewById(R.id.view3);
        view4 = dashboardView.findViewById(R.id.view4);
        view5 = dashboardView.findViewById(R.id.view5);
        view6 = dashboardView.findViewById(R.id.view6);


        titleOne = dashboardView.findViewById(R.id.titleOne);
        titleTwo = dashboardView.findViewById(R.id.titleTwo);
        titleThree = dashboardView.findViewById(R.id.titleThree);
        titleFour = dashboardView.findViewById(R.id.titleFour);
        titleFive = dashboardView.findViewById(R.id.titleFive);
        titleSix = dashboardView.findViewById(R.id.titleSix);
        titleSeven = dashboardView.findViewById(R.id.titleSeven);
        titleEight = dashboardView.findViewById(R.id.titleEight);
        titleNine = dashboardView.findViewById(R.id.titleNine);
        titleten = dashboardView.findViewById(R.id.titleten);
        titleeleven = dashboardView.findViewById(R.id.titleeleven);
        titleTwele = dashboardView.findViewById(R.id.titleTwele);
        titlethirteen = dashboardView.findViewById(R.id.titlethirteen);
        titleFourteen = dashboardView.findViewById(R.id.titleFourteen);
        titleFifteen = dashboardView.findViewById(R.id.titleFifteen);
        titleSixteen = dashboardView.findViewById(R.id.titleSixteen);

        addFundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(NAV_TO_FUNDTRANSFER_SCREEN, new Bundle(), true);
            }
        });


        positionlayoutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("from", "dashboard");
                navigateTo(NAV_TO_BOTTOM_PORTFOLIO_TXT, b, true);

            }
        });

        holdinglayoutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("from", "dashboardholding");
                navigateTo(NAV_TO_BOTTOM_PORTFOLIO_TXT, b, true);

            }
        });

    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {


            DashboardLayout.setBackgroundColor(getResources().getColor(R.color.white));
            cashLayout.setBackgroundColor(getResources().getColor(R.color.light_spinner));
            available_layout.setBackgroundColor(getResources().getColor(R.color.light_spinner));
            cashavailtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            netMtmTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalHoldingTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            availableLimitTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            openingBaltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            marginTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            payinamtTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            spanExposureTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            collateralTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            optionPremiumTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            creditStockSoldTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            deliveryTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            creditOptionSellTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            unrealizedMtmTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            realizedMtmTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            payouttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            positionlayoutTxt.setBackgroundColor(getResources().getColor(R.color.light_spinner));
            holdinglayoutTxt.setBackgroundColor(getResources().getColor(R.color.light_spinner));
            collateralTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            collateralTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            addFundBtn.setBackgroundColor(getResources().getColor(R.color.login_button_bg));
            addFundBtn.setTextColor(getResources().getColor(R.color.white));
            //cashavailtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //netMtmTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //totalHoldingTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //availableLimitTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            openingBaltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            marginTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            payinamtTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            spanExposureTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            collateralTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            optionPremiumTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            creditStockSoldTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            deliveryTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            creditOptionSellTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            unrealizedMtmTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            realizedMtmTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            payouttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            titleOne.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleTwo.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleThree.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleFour.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleFive.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleSix.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleSeven.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleEight.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleNine.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleten.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleeleven.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleTwele.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titlethirteen.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleFourteen.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleFifteen.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            titleSixteen.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            view1.setBackgroundColor(getResources().getColor(R.color.black));
            view2.setBackgroundColor(getResources().getColor(R.color.black));
            view3.setBackgroundColor(getResources().getColor(R.color.black));
            view4.setBackgroundColor(getResources().getColor(R.color.black));
            view5.setBackgroundColor(getResources().getColor(R.color.black));
            view6.setBackgroundColor(getResources().getColor(R.color.black));


        }
    }

    public void onEventMainThread(HoldingValueresponse holdingValueresponse) {
        try {
            if (!String.valueOf(holdingValueresponse.getResponse().getData().getHValue()).equalsIgnoreCase("")) {
                if (holdingValueresponse.getResponse().getData().getHValue().equals("0.00")){
                    totalHoldingTxt.setText("0.00");
                }else {
                    totalHoldingTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(String.valueOf(holdingValueresponse.getResponse().getData().getHValue())))));
                }
            } else {
                totalHoldingTxt.setText("0");
            }


        } catch (Exception e) {

        }
    }

    public void onEventMainThread(MarginDetailResponse marginDetailResponse) {
        try {


            double leftTotal, rightTotal, Total;

           /* if (!marginDetailResponse.getMtm().isEmpty()) {
                netMtmTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(String.valueOf(marginDetailResponse.getMtm())))));
            }*/

            leftTotal = Double.parseDouble(marginDetailResponse.getAvailFund()) + Double.parseDouble(marginDetailResponse.getPayIn())
                    + Double.parseDouble(marginDetailResponse.getOptSellCr()) + Double.parseDouble(marginDetailResponse.getCollateralVal())
                     + Double.parseDouble(marginDetailResponse.getEqSellCredit());


            if (Double.parseDouble(marginDetailResponse.getUnRealizedM2M()) > 0) {

                rightTotal = Double.parseDouble(marginDetailResponse.getUtilizedFundBajaj()) + Double.parseDouble(marginDetailResponse.getOptBuyPrem())
                        + Double.parseDouble(marginDetailResponse.getCashMargin()) + Double.parseDouble(marginDetailResponse.getSpanExp())
                        + Double.parseDouble(marginDetailResponse.getPayOut());

            } else {

                rightTotal = Double.parseDouble(marginDetailResponse.getUtilizedFundBajaj()) + Double.parseDouble(marginDetailResponse.getOptBuyPrem())
                        + Double.parseDouble(marginDetailResponse.getCashMargin()) + Double.parseDouble(marginDetailResponse.getSpanExp())
                        + Double.parseDouble(marginDetailResponse.getPayOut());
            }

            Total = leftTotal - rightTotal;

            if (Total == 0.0) {
                availableLimitTxt.setText("0.00");
            } else {
                availableLimitTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Total)));

            }


            //netMtmTxt.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getRealizedM2M())));
            if (Double.parseDouble(marginDetailResponse.getAvailFund()) != 0.00) {
                cashavailtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getAvailFund()))));
            } else {
                cashavailtxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getAvailFund()) != 0.00) {
                openingBaltxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getAvailFund()))));
            } else {
                openingBaltxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getUtilizedFundBajaj()) != 0.00) {
                marginTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getUtilizedFundBajaj()))));
            } else {
                marginTxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getPayIn()) != 0.00) {
                payinamtTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getPayIn()))));
            } else {
                payinamtTxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getSpanExp()) != 0.00) {
                spanExposureTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getSpanExp()))));
            } else {
                spanExposureTxt.setText("0.00");
            }
            if (Double.parseDouble(marginDetailResponse.getCollateralValue()) != 0.00) {
                collateralTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getCollateralValue()))));
            } else {
                collateralTxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getOptBuyPrem()) != 0.00) {
                optionPremiumTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getOptBuyPrem()))));
            } else {
                optionPremiumTxt.setText("0.00");
            }
            if (Double.parseDouble(marginDetailResponse.getEquitySellCredit()) != 0.00) {
                creditStockSoldTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getEquitySellCredit()))));
            } else {
                creditStockSoldTxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getCashMargin()) != 0.00) {
                deliveryTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getCashMargin()))));
            } else {
                deliveryTxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getUnRealizedM2M()) != 0.00) {
                unrealizedMtmTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getUnRealizedM2M()))));
            } else {

                unrealizedMtmTxt.setText("0.00");
            }

            if (Double.parseDouble(marginDetailResponse.getRealizedM2M()) != 0.00) {
                realizedMtmTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getRealizedM2M()))));
            } else {
                realizedMtmTxt.setText("0.00");
            }
            if (Double.parseDouble(marginDetailResponse.getPayOut()) != 0.00) {
                payouttxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getPayOut()))));
            } else {
                payouttxt.setText("0.00");
            }
            if (Double.parseDouble(marginDetailResponse.getOptSellCr()) != 0.00) {
                creditOptionSellTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getOptSellCr()))));
            } else {
                creditOptionSellTxt.setText("0.00");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMarginRequest(String exchangeType) {

        //setAppTitle(getClass().toString(),"Margin Details");
        final MarginDetailRequest marginDetailRequest = new MarginDetailRequest();
        marginDetailRequest.setGcid(AccountDetails.getClientCode(getMainActivity()));
        marginDetailRequest.setSegment(exchangeType);
        marginDetailRequest.setExchange_type("-1");

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (AccountDetails.isIsIrisConnected()) {
                    //if iris is connected then only send margin details request
                    orderStreamingController.sendMarginDetailRequest(getActivity(), marginDetailRequest);
                }

                handler.postDelayed(this, 10000);
            }
        }, 1);


        sendPTRRequest();
    }


    private void sendPTRRequest() {

        String serviceURL = "getNetPositionMTM?gscid=" + AccountDetails.getUsername(getActivity());
        // To get netposition get service is request to aracane server
        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.has("data")) {
                        JSONArray ja1 = response.getJSONArray("data");
                        JSONObject jsonObject = response.getJSONArray("data").getJSONObject(0);
                        netMtmTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(String.valueOf(jsonObject.get("MTM"))))));

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    @Override
    public void handleResponse(Object response) {
        if (response instanceof JSONResponse) {
            JSONResponse jsonResponse = (JSONResponse) response;
            try {
                HoldingDataResponse holdingDataResponse = (HoldingDataResponse) jsonResponse.getResponse();
                JSONObject jsonObject = ((JSONResponse) response).getResponseData().getJSONObject("data");
                if (!String.valueOf(jsonObject.get("HValue")).equalsIgnoreCase("")) {
                    totalHoldingTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(String.valueOf(jsonObject.get("HValue"))))));
                } else {
                    totalHoldingTxt.setText("0");
                }

            } catch (Exception e) {

            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (AccountDetails.isIsIrisConnected()) {
            // If Iris is connected then send request for holdigvalueInfo
            orderStreamingController.sendHoldingValueinfo(getContext(), AccountDetails.getUsername(getContext()),
                    AccountDetails.getSessionId(getContext()));
        } else {
            if (AccountDetails.getIris_LoginCounter() == 0) {
                AccountDetails.setIris_LoginCounter(1);
            }else {
                EventBus.getDefault().post("Socket IRIS Reconnect Attempts exceeds");
            }
        }

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onPause();
    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onFragmentPause();

    }
}

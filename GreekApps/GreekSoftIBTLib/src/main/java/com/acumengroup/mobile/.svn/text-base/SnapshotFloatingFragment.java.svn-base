package com.acumengroup.mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.greekportfolio.GainInfo;
import com.acumengroup.greekmain.core.model.greekportfolio.GreekPortfolioRequest;
import com.acumengroup.greekmain.core.model.greekportfolio.GreekPortfolioResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.model.OrderPortfolioModel;
import com.acumengroup.mobile.model.PositionSnapShotModel;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sushant.patil on 4/18/2016.
 */
public class SnapshotFloatingFragment extends GreekBaseFragment implements View.OnClickListener {
    private static final String PORTFOLIO_SNAP_REQ = "PORTFOLIO";
    private static final String POSITION_SNAP_REQ = "POSITION";
    private static final String ORDER_SNAP_REQ = "ORDER";
    private final HashMap<String, String> requests = new HashMap<>();
    private GreekTextView networthtext, Investmenttext;
    private GreekTextView networthvalueone, InvestmentvalueOne;
    private GreekTextView networthvaluetwo, InvestmentvalueTwo;
    private GreekTextView daygaintext, Overallgaintext;
    private GreekTextView daygainvalueone, Overallgainvalueone;
    private GreekTextView daygainvaluetwo, Overallgainvaluetwo;
    private RelativeLayout l1_porfolio, r1_portfolio, r1_positions, r1_orders, pro_portfolio;
    private HashMap orderHashmap = new HashMap();
    private HashMap positionHashmap = new HashMap();
    private GreekTextView tvOpenOrder;
    private GreekTextView tvOpenOrderVal;
    private GreekTextView tvOpenPosition;
    private GreekTextView tvOpenPositionVal;
    private GreekTextView tvportfoliotxt;
    private GreekTextView tvpositiontxt, tvordertxt, openordertxt, openordervaluetxt, tvopenposition, tvopenpositionvaluetxt;
    private Button btnViewPosition, btnViewOrders, btnOpenPositions, btnTodayPositionEquity, btnCumulativePosition;
    private Button btnHoldings;
    private ImageView backImg, frwdImg;
    private LinearLayout scripLayout, portfolionetworth, portfolionetworth2, portfoliodaygain,portfoliodaygain2, positionopen, positionopenvalue, ordersopen, ordersopenvalue;
    JSONObject data;
    private PortfolioSnapHolder snapHolder;
    GreekPortfolioResponse greekPortfolioResponse;
    GainInfo gainInfo;
    Thread t;
    Handler handler = new Handler();
    private LinearLayout progressLayout;
    private ProgressBar progressBar;
    private boolean safeToWrite = false;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View snapView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_snapshot).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_snapshot).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_MYSCRIPTS_SCREEN;
        setupView(snapView);
        return snapView;
    }

    private void setupView(View parent) {
        hideAppTitle();
        scripLayout = parent.findViewById(R.id.scripLayout);

        portfolionetworth = parent.findViewById(R.id.portfolio_networth);
        portfolionetworth2 = parent.findViewById(R.id.portfolio_networth2);
        positionopen = parent.findViewById(R.id.position_open);
        positionopenvalue = parent.findViewById(R.id.positon_open_value);
        portfoliodaygain = parent.findViewById(R.id.portfolio_daygain);
        portfoliodaygain2 = parent.findViewById(R.id.portfolio_daygain2);
        ordersopen = parent.findViewById(R.id.orders_open);
        ordersopenvalue = parent.findViewById(R.id.orders_open_value);

        progressLayout = parent.findViewById(R.id.customProgress);
        progressBar = parent.findViewById(R.id.progressBar);
        r1_portfolio = parent.findViewById(R.id.portfolio_relative);
        r1_positions = parent.findViewById(R.id.position_relative);
        r1_orders = parent.findViewById(R.id.orders_relative);
        pro_portfolio = parent.findViewById(R.id.portfolio_outer_relative);

//        networth_relative = (RelativeLayout) parent.findViewById(R.id.networth_relative);
//        daygain_relative = (RelativeLayout) parent.findViewById(R.id.daygain_relative);

        pro_portfolio.setOnTouchListener(new RelativeLayoutTouchListener((AppCompatActivity) getActivity()));

        networthtext = parent.findViewById(R.id.networth_text);
        Investmenttext = parent.findViewById(R.id.networth_text2);
        tvportfoliotxt = parent.findViewById(R.id.portfolio_text);
        tvpositiontxt = parent.findViewById(R.id.position_text);
        tvordertxt = parent.findViewById(R.id.orders_text);
        //ivPlusPortfolio.setOnClickListener(this);
        networthvalueone = parent.findViewById(R.id.networth_value_one);
        InvestmentvalueOne = parent.findViewById(R.id.networth_value_one2);
        //ivPlusPosition.setOnClickListener(this);
        networthvaluetwo = parent.findViewById(R.id.networth_value_two);
        InvestmentvalueTwo = parent.findViewById(R.id.networth_value_two2);

        daygaintext = parent.findViewById(R.id.daygain_text);
        Overallgaintext = parent.findViewById(R.id.daygain_text2);
        //ivPlusPortfolio.setOnClickListener(this);
        daygainvalueone = parent.findViewById(R.id.daygain_value_one);
        Overallgainvalueone = parent.findViewById(R.id.daygain_value_one2);
        //ivPlusPosition.setOnClickListener(this);
        daygainvaluetwo = parent.findViewById(R.id.daygain_value_two);
        Overallgainvaluetwo = parent.findViewById(R.id.daygain_value_two2);

        openordertxt = parent.findViewById(R.id.open_orders_text);
        openordervaluetxt = parent.findViewById(R.id.open_orders_value_text);


        tvopenposition = parent.findViewById(R.id.tvOpenPosition);
        tvopenpositionvaluetxt = parent.findViewById(R.id.open_position_value_text);


//        networthtext_flip = (Button) parent.findViewById(R.id.networth_flip_btn);
//        daygaintext_flip = (Button) parent.findViewById(R.id.daygain_flip_btn);

        btnViewPosition = parent.findViewById(R.id.position_info);
        btnOpenPositions = parent.findViewById(R.id.position_info_open);
        btnViewOrders = parent.findViewById(R.id.orders_page);
        btnTodayPositionEquity = parent.findViewById(R.id.todays_position);
        btnHoldings = parent.findViewById(R.id.holdings);
        btnCumulativePosition = parent.findViewById(R.id.position_cumulative_open);

        backImg = parent.findViewById(R.id.backImgBtn);
        frwdImg = parent.findViewById(R.id.frwdImgBtn);


        tvOpenOrder = parent.findViewById(R.id.open_orders_text_value);
        tvOpenOrderVal = parent.findViewById(R.id.open_orders_value);

        tvOpenPosition = parent.findViewById(R.id.tvOpenPositionVal);
        tvOpenPositionVal = parent.findViewById(R.id.open_position_text_value);
        l1_porfolio = parent.findViewById(R.id.portfolio_relative);

        snapHolder = new PortfolioSnapHolder(parent);
        r1_portfolio.setVisibility(View.VISIBLE);
        r1_portfolio.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_right_in_activity));
        r1_positions.setVisibility(View.GONE);
        r1_orders.setVisibility(View.GONE);
        backImg.setVisibility(View.GONE);
        frwdImg.setVisibility(View.VISIBLE);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            pro_portfolio.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_shape_white));
            backImg.setImageDrawable(getResources().getDrawable(R.drawable.next_button_back_white));
            frwdImg.setImageDrawable(getResources().getDrawable(R.drawable.next_button_white));
            tvportfoliotxt.setTextColor(getResources().getColor(R.color.textColorCustomWhite));
            tvpositiontxt.setTextColor(getResources().getColor(R.color.textColorCustomWhite));
            tvordertxt.setTextColor(getResources().getColor(R.color.textColorCustomWhite));

            portfolionetworth.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));
            portfolionetworth2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));
            portfoliodaygain.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));
            portfoliodaygain2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));

            positionopen.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));
            positionopenvalue.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));

            ordersopen.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));
            ordersopenvalue.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_curve_button_shape_white));


            networthtext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            networthvalueone.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            networthvaluetwo.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            Investmenttext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            InvestmentvalueOne.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            InvestmentvalueTwo.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            daygaintext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            daygainvalueone.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            daygainvaluetwo.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            Overallgaintext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            Overallgainvalueone.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            Overallgainvaluetwo.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


            tvopenposition.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tvopenpositionvaluetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tvOpenPosition.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tvOpenPositionVal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            openordertxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            openordervaluetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tvOpenOrder.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            tvOpenOrderVal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
        if (AccountDetails.portfolio)
            sendSnapshotsRequest();

        btnHoldings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("from", "equity");
                if (AccountDetails.getDematFlag()) {
                    navigateTo(GREEK_MENU_DEMATHOLDING, args, true);
                } else {
                    navigateTo(GREEK_MENU_DEMAT_HOLDING_SINGLE, args, true);
                }
            }
        });

        btnViewPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("from", "fno");
                navigateTo(GREEK_MENU_NETPOSITION, args, true);
            }
        });

        btnTodayPositionEquity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("from", "equity");
                navigateTo(GREEK_MENU_NETPOSITION, args, true);
            }
        });
        btnCumulativePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateTo(NAV_TO_CUMULATIVE_SCREEN, null, true);
            }
        });


        btnOpenPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateTo(GREEK_MENU_OPENPOSITION, null, true);
            }
        });
        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(GREEK_MENU_ORDERPOSITION, null, true);
            }
        });


        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (r1_orders.getVisibility() == View.VISIBLE) {
                    sendPositionSnapshotRequest();
                    r1_portfolio.setVisibility(View.GONE);
                    r1_positions.setVisibility(View.VISIBLE);
                    r1_positions.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_left_in_activity));
                    r1_orders.setVisibility(View.GONE);
                    backImg.setVisibility(View.VISIBLE);
                    frwdImg.setVisibility(View.VISIBLE);
                } else if (r1_positions.getVisibility() == View.VISIBLE) {
                    sendSnapshotsRequest();
                    r1_portfolio.setVisibility(View.VISIBLE);
                    r1_portfolio.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_left_in_activity));
                    r1_positions.setVisibility(View.GONE);
                    r1_orders.setVisibility(View.GONE);
                    backImg.setVisibility(View.GONE);
                    frwdImg.setVisibility(View.VISIBLE);
                }
            }
        });

        frwdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (r1_portfolio.getVisibility() == View.VISIBLE) {
                    sendPositionSnapshotRequest();
                    r1_portfolio.setVisibility(View.GONE);
                    r1_positions.setVisibility(View.VISIBLE);
                    r1_positions.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_right_in_activity));
                    r1_orders.setVisibility(View.GONE);
                    backImg.setVisibility(View.VISIBLE);
                    frwdImg.setVisibility(View.VISIBLE);
                } else if (r1_positions.getVisibility() == View.VISIBLE) {
                    sendOrderSnapshotRequest();
                    r1_portfolio.setVisibility(View.GONE);
                    r1_positions.setVisibility(View.GONE);
                    r1_orders.setVisibility(View.VISIBLE);
                    r1_orders.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_right_in_activity));
                    backImg.setVisibility(View.VISIBLE);
                    frwdImg.setVisibility(View.GONE);
                }
            }
        });
    }


    public void rightToLeftSwipe() {

        if (r1_portfolio.getVisibility() == View.VISIBLE) {
            sendPositionSnapshotRequest();
            r1_portfolio.setVisibility(View.GONE);
            r1_positions.setVisibility(View.VISIBLE);
            r1_positions.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_right_in_activity));
            r1_orders.setVisibility(View.GONE);
            backImg.setVisibility(View.VISIBLE);
            frwdImg.setVisibility(View.VISIBLE);
        } else if (r1_positions.getVisibility() == View.VISIBLE) {
            sendOrderSnapshotRequest();
            r1_portfolio.setVisibility(View.GONE);
            r1_positions.setVisibility(View.GONE);
            r1_orders.setVisibility(View.VISIBLE);
            r1_orders.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_right_in_activity));
            backImg.setVisibility(View.VISIBLE);
            frwdImg.setVisibility(View.GONE);
        }
    }

    public void leftToRightSwipe() {
        if (r1_orders.getVisibility() == View.VISIBLE) {
            sendPositionSnapshotRequest();
            r1_portfolio.setVisibility(View.GONE);
            r1_positions.setVisibility(View.VISIBLE);
            r1_positions.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_left_in_activity));
            r1_orders.setVisibility(View.GONE);
            backImg.setVisibility(View.VISIBLE);
            frwdImg.setVisibility(View.VISIBLE);
        } else if (r1_positions.getVisibility() == View.VISIBLE) {
            sendSnapshotsRequest();
            r1_portfolio.setVisibility(View.VISIBLE);
            r1_portfolio.setAnimation(AnimationUtils.loadAnimation(getMainActivity(), R.anim.move_left_in_activity));
            r1_positions.setVisibility(View.GONE);
            r1_orders.setVisibility(View.GONE);
            backImg.setVisibility(View.GONE);
            frwdImg.setVisibility(View.VISIBLE);
        }
    }

    private void setPortfolioDayChangeView() {
        if (gainInfo != null) {
//            if (daygaintext.getText().toString().equalsIgnoreCase("Day Gain")) {

            Overallgaintext.setText("Overall Gain");
            Overallgainvalueone.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(gainInfo.getOverAllGain()))));
            Overallgainvaluetwo.setText(String.format("(%.2f%%)", Double.parseDouble(gainInfo.getOverAllGainPerChange())));

//            } else {
            daygaintext.setText("Day Gain");
            daygainvalueone.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(gainInfo.getDayGain()))));
            daygainvaluetwo.setText(String.format("(%.2f%%)", Double.parseDouble(gainInfo.getDaysGainPerChange())));
//            }
        }

    }

    public void sendSnapshotsRequest() {
        if (requests.size() < 7) {
            requests.clear();
            AccountDetails.send_portfolio_req = true;
            sendPortfolioRequest();

        }
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MYSCRIPTS_SCREEN;
    }

    @Override
    public void onPause() {
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    private static double roundOff(String input) {
        if (input == null || input.isEmpty()) return 0;
        return Util.round(Double.valueOf(input), 2);
    }

    private void sendPositionSnapshotRequest() {
        handler.removeCallbacksAndMessages(null);
        String url = "GetScripPosition?SessionId=" + AccountDetails.getSessionId(getMainActivity()) + "&ClientCode=" + AccountDetails.getClientCode(getMainActivity());
        WSHandler.getRequest(getMainActivity(), url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                requests.put(POSITION_SNAP_REQ, "true");
                positionHashmap = getPositionSnap(response);
                if (positionHashmap.size() == 0) {

                } else {
                    ArrayList<PositionSnapShotModel> dataArr = (ArrayList<PositionSnapShotModel>) positionHashmap.get("data");
                    ArrayList<PositionSnapShotModel> totalArr = (ArrayList<PositionSnapShotModel>) positionHashmap.get("total");

                    if (totalArr.size() != 0) {
                        tvOpenPosition.setText(totalArr.get(0).getTotalOpenPosCount());
                        tvOpenPositionVal.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(totalArr.get(0).getTotalMTMvalue()))));
                    }
                    requestComplete();
                }
            }

            @Override
            public void onFailure(String message) {
                requestComplete();
            }
        });
    }

    private void sendOrderSnapshotRequest() {
        //    showProgress();
        handler.removeCallbacksAndMessages(null);
        String url = "GetScripOrder?SessionId=" + AccountDetails.getSessionId(getMainActivity()) + "&ClientCode=" + AccountDetails.getClientCode(getMainActivity());
        WSHandler.getRequest(getMainActivity(), url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
                orderHashmap = getOrderPortfolio(response);
                if (orderHashmap.size() == 0) {
                    hideProgress();
                } else {
                    ArrayList<OrderPortfolioModel> dataArr = (ArrayList<OrderPortfolioModel>) orderHashmap.get("data");
                    ArrayList<OrderPortfolioModel> totalArr = (ArrayList<OrderPortfolioModel>) orderHashmap.get("total");
                    if (dataArr.size() != 0) {
                        tvOpenOrder.setText(dataArr.get(0).getOpenOrders());
                        tvOpenOrderVal.setText(StringStuff.commaINRDecorator(String.format("%.2f", (Double.parseDouble(dataArr.get(0).getOpenOrderValue())))));
                    }

                    requests.put(ORDER_SNAP_REQ, "true");
                    //           hideProgress();
                }
            }

            @Override
            public void onFailure(String message) {
                requests.put(ORDER_SNAP_REQ, "false");
                hideProgress();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.networth_flip_btn:
//                setPortfolioChangeView();
//                break;
        }
    }

    private void setPortfolioChangeView() {

        if (!safeToWrite) {
            Log.d("######################", String.valueOf(safeToWrite));
            return;
        }

        if (gainInfo != null) {

//            if (networthtext.getText().toString().equalsIgnoreCase("Networth")) {
            Investmenttext.setText("Investment");


            InvestmentvalueOne.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(gainInfo.getInvestmentPrice()))));
            //*********hack to resolve crash**************
            if (String.valueOf(gainInfo.getPurchaseValue()) == "null")
                gainInfo.setPurchaseValue("0");
            double change = Double.parseDouble(gainInfo.getLatestValue()) - Double.parseDouble(gainInfo.getPurchaseValue());
            String chng = StringStuff.commaINRDecorator(String.format("%.2f", change));
            double changePer = (change * 100) / Double.parseDouble(gainInfo.getPurchaseValue());
            if (String.valueOf(changePer).equalsIgnoreCase("NaN")) {
                changePer = 0.0;
                InvestmentvalueTwo.setText(String.format("%s(%.2f%%)", chng, changePer));
            } else
                InvestmentvalueTwo.setText(String.format("%s(%.2f%%)", chng, changePer));
//            } else {
            networthtext.setText("Networth");
            networthvalueone.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(gainInfo.getLatestValue()))));
            double change2 = Double.parseDouble(gainInfo.getLatestValue()) - Double.parseDouble(gainInfo.getPurchaseValue());
            String chng2 = StringStuff.commaINRDecorator(String.format("%.2f", change2));
            double changePer2 = (change2 * 100) / Double.parseDouble(gainInfo.getPurchaseValue());
            if (String.valueOf(changePer2).equalsIgnoreCase("NaN")) {
                changePer = 0.0;
                networthvaluetwo.setText(String.format("%s(%.2f%%)", chng2, changePer));
            } else
                networthvaluetwo.setText(String.format("%s(%.2f%%)", chng2, changePer));
//            }
        }
    }

    private void requestComplete() {
        if (requests.size() == 7) {
            hideProgress();
        }
    }

    private void sendPortfolioRequest() {
        requests.put(PORTFOLIO_SNAP_REQ, "true");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AccountDetails.send_portfolio_req) {
                    safeToWrite = false;

                    GreekPortfolioRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);

                }
                handler.postDelayed(this, 10000);
            }
        }, 0);

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_PORTFOLIO_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                greekPortfolioResponse = (GreekPortfolioResponse) jsonResponse.getResponse();
                safeToWrite = true;
                if (greekPortfolioResponse.getErrorCode().equals("3")) {
                } else {
                    gainInfo = greekPortfolioResponse.getGainInfo();
                    setPortfolioView(gainInfo);
                    setPortfolioDayView(gainInfo);
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        hideAppTitle();
        //requestComplete();
    }

    private void setPortfolioView(GainInfo list) {

        networthtext.setText("Networth");
        double changePer;
        networthvalueone.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(list.getLatestValue()))));
        double change = Double.parseDouble(list.getLatestValue()) - Double.parseDouble(list.getPurchaseValue());
        String chng = StringStuff.commaINRDecorator(String.format("%.2f", change));
        if (list.getPurchaseValue().equalsIgnoreCase("0")) {
            changePer = Double.parseDouble(list.getPurchaseValue());
        } else {
            changePer = (change * 100) / Double.parseDouble(list.getPurchaseValue());
        }
        //HANDLING NaN RESULT WHILE DOING CALCULATION
        if (String.valueOf(changePer).equalsIgnoreCase("NaN")) {
            changePer = 0.0;
            networthvaluetwo.setText(String.format("%s(%.2f%%)", chng, changePer));
        } else
            networthvaluetwo.setText(String.format("%s(%.2f%%)", chng, changePer));

        Investmenttext.setText("Investment");
        double changePer2;
        InvestmentvalueOne.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(gainInfo.getInvestmentPrice()))));
        //*********hack to resolve crash**************
        if (String.valueOf(gainInfo.getPurchaseValue()) == "null")
            gainInfo.setPurchaseValue("0");
        double change2 = Double.parseDouble(gainInfo.getLatestValue()) - Double.parseDouble(gainInfo.getPurchaseValue());
        String chng2 = StringStuff.commaINRDecorator(String.format("%.2f", change2));
        if (gainInfo.getPurchaseValue().equalsIgnoreCase("0")) {
            changePer2 = Double.parseDouble(gainInfo.getPurchaseValue());
        } else {
            changePer2 = (change2 * 100) / Double.parseDouble(gainInfo.getPurchaseValue());
        }
        if (String.valueOf(changePer2).equalsIgnoreCase("NaN")) {
            changePer2 = 0.0;
            InvestmentvalueTwo.setText(String.format("%s(%.2f%%)", chng2, changePer2));
        } else
            InvestmentvalueTwo.setText(String.format("%s(%.2f%%)", chng2, changePer2));

    }

    private void setPortfolioDayView(GainInfo list) {

        daygaintext.setText("Day Gain");
        daygainvalueone.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(gainInfo.getDayGain()))));
        daygainvaluetwo.setText(String.format("(%.2f%%)", Double.parseDouble(gainInfo.getDaysGainPerChange())));


        Overallgaintext.setText("Overall Gain");
        Overallgainvalueone.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.valueOf(gainInfo.getOverAllGain()))));
        Overallgainvaluetwo.setText(String.format("(%.2f%%)", Double.valueOf(gainInfo.getOverAllGainPerChange())));

    }

    class PortfolioSnapHolder {
        final GreekTextView daygain;
        final GreekTextView dayChange;


        PortfolioSnapHolder(View parent) {
            this.daygain = parent.findViewById(R.id.daygain_value_one);
            this.dayChange = parent.findViewById(R.id.daygain_value_two);
        }

    }

    @SuppressLint("ValidFragment")
    public class RelativeLayoutTouchListener implements View.OnTouchListener {
        private AppCompatActivity activity;
        static final int MIN_DISTANCE = 100;
        private float downX, downY, upX, upY;


        @SuppressLint("ValidFragment")
        public RelativeLayoutTouchListener(AppCompatActivity activity) {
            this.activity = activity;
        }

        public void onRightToLeftSwipe() {
            rightToLeftSwipe();
        }

        public void onLeftToRightSwipe() {
            leftToRightSwipe();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    downY = event.getY();
                    return true;
                }

                case MotionEvent.ACTION_UP: {
                    upX = event.getX();
                    upY = event.getY();
                    float deltaX = downX - upX;
                    float deltaY = downY - upY;
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        if (deltaX < 0) {
                            this.onLeftToRightSwipe();
                            return true;
                        }
                        if (deltaX > 0) {
                            this.onRightToLeftSwipe();
                            return true;
                        }
                    } else {
                    }
                    return false;
                }
            }
            return false;
        }
    }


    public HashMap getOrderPortfolio(JSONObject strJson) {
        ArrayList<OrderPortfolioModel> arrList = new ArrayList<>();
        ArrayList<OrderPortfolioModel> arrList1 = new ArrayList<>();
        HashMap map = new HashMap();
        try {
            JSONArray objJSONArray = strJson.getJSONArray("data");
            for (int i = 0; i < objJSONArray.length(); i++) {

                OrderPortfolioModel portfolioModel = new OrderPortfolioModel();
                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                portfolioModel.setClientCode(objJSONObject.getString("Client_Code") == null ? " - " : objJSONObject.getString("Client_Code"));
                portfolioModel.setSegment(objJSONObject.getString("Segment") == null ? " - " : objJSONObject.getString("Segment"));
                portfolioModel.setOpenOrders(objJSONObject.getString("OpenOrders") == null ? " - " : objJSONObject.getString("OpenOrders"));
                portfolioModel.setOpenOrderValue(objJSONObject.getString("OpenOrdersValue") == null ? " - " : objJSONObject.getString("OpenOrdersValue"));
                arrList.add(portfolioModel);

                OrderPortfolioModel portfolioModelTotal = new OrderPortfolioModel();
                portfolioModelTotal.setTotalOpenOrder(objJSONObject.getString("totalOpenOrder") == null ? " - " : objJSONObject.getString("totalOpenOrder"));
                portfolioModelTotal.setTotalOpenOrderValue(objJSONObject.getString("totalOpenOrderValue") == null ? " - " : objJSONObject.getString("totalOpenOrderValue"));

                arrList1.add(portfolioModelTotal);
            }
            map.put("data", arrList);
            map.put("total", arrList1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }


    public HashMap getPositionSnap(JSONObject strJson) {
        ArrayList<PositionSnapShotModel> arrList = new ArrayList<>();
        ArrayList<PositionSnapShotModel> arrList1 = new ArrayList<>();
        HashMap map = new HashMap();
        try {
            JSONArray objArrData = strJson.getJSONArray("data");

            for (int i = 0; i < objArrData.length(); i++) {
                JSONObject objJSONObject = objArrData.getJSONObject(i);

                PositionSnapShotModel positionSnapShotModel = new PositionSnapShotModel();
                positionSnapShotModel.setClientCode(objJSONObject.getString("Client_Code") == null ? "" : objJSONObject.getString("Client_Code"));
                positionSnapShotModel.setSegment(objJSONObject.getString("Segment") == null ? "" : objJSONObject.getString("Segment"));
                positionSnapShotModel.setOpenPosCount(objJSONObject.getString("OpenPosCount") == null ? "" : objJSONObject.getString("OpenPosCount"));
                positionSnapShotModel.setMtmValue(objJSONObject.getString("MTMvalue") == null ? "" : objJSONObject.getString("MTMvalue"));
                arrList.add(positionSnapShotModel);

                PositionSnapShotModel portfolioModel = new PositionSnapShotModel();
                portfolioModel.setTotalOpenPosCount(objJSONObject.getString("totalOpenPosCount") == null ? "" : objJSONObject.getString("totalOpenPosCount"));
                portfolioModel.setTotalMTMvalue(objJSONObject.getString("totalMTMvalue") == null ? "" : objJSONObject.getString("totalMTMvalue"));

                arrList1.add(portfolioModel);

            }

            map.put("data", arrList);
            map.put("total", arrList1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}

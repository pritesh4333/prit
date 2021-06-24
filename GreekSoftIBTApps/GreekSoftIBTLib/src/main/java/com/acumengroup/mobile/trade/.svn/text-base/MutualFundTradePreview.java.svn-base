package com.acumengroup.mobile.trade;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.greekmain.core.model.MutualFundOrderLog;
import com.acumengroup.greekmain.core.model.mutualfundmutualfundmodifyorder.MutualFundMutualFundModifyOrderRequest;
import com.acumengroup.greekmain.core.model.mutualfundmutualfundmodifyorder.MutualFundMutualFundModifyOrderResponse;
import com.acumengroup.greekmain.core.model.mutualfundmutualfundsendneworder.MutualFundMutualFundSendNewOrderRequest;
import com.acumengroup.greekmain.core.model.mutualfundmutualfundsendneworder.MutualFundMutualFundSendNewOrderResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MutualFundHoldingsModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import java.util.ArrayList;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MutualFundTradePreview extends GreekBaseFragment {

    private final View.OnClickListener backClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            goBackOnce();

        }
    };
    private final View.OnClickListener confirmClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Util.updateValidationTime(getMainActivity());

            if (getArguments().getBoolean("isModifyOrder")) {
                showProgress();
                MutualFundOrderLog fundOrderLog = (MutualFundOrderLog) getArguments().getSerializable("Request");

                if (getArguments().getString("PurchaseType").equals("Redeem")) {
                    MutualFundMutualFundModifyOrderRequest.sendRequest(fundOrderLog.getAMCName(), getArguments().getString("Amount"), AccountDetails.getClientCode(getMainActivity()), "Redemption", "", fundOrderLog.getRefNo(), fundOrderLog.getSchemeCode().trim(), fundOrderLog.getSchemeName(), AccountDetails.getToken(getMainActivity()), getActivity(), serviceResponseHandler);

                } else
                    MutualFundMutualFundModifyOrderRequest.sendRequest(fundOrderLog.getAMCName(), getArguments().getString("Amount"), AccountDetails.getClientCode(getMainActivity()), "Purchase", getArguments().getString("PurchaseType"), fundOrderLog.getRefNo(), fundOrderLog.getSchemeCode().trim(), fundOrderLog.getSchemeName(), AccountDetails.getToken(getMainActivity()), getActivity(), serviceResponseHandler);

            } else {
                showProgress();

                if (getArguments().getBoolean("isFromHoldings")) {

                    MutualFundHoldingsModel holdingsData = (MutualFundHoldingsModel) getArguments().getSerializable("Request");
                    if (getArguments().getString("PurchaseType").equals("Redeem")) {
                        MutualFundMutualFundSendNewOrderRequest.sendRequest("NEW", getArguments().getString("dpTransaction"), "1", "N", getArguments().getString("folioNo"), holdingsData.getAMCName(), getArguments().getString("Amount"), AccountDetails.getClientCode(getMainActivity()), "Redemption", "", holdingsData.getSchemeCode().trim(), holdingsData.getSchemeName(), AccountDetails.getToken(getMainActivity()), holdingsData.getISIN(), getMainActivity(), serviceResponseHandler);
                    } else {
                        MutualFundMutualFundSendNewOrderRequest.sendRequest("NEW", getArguments().getString("dpTransaction"), "1", "N", getArguments().getString("folioNo"), holdingsData.getAMCName(), getArguments().getString("Amount"), AccountDetails.getClientCode(getMainActivity()), "Purchase", getArguments().getString("PurchaseType"), holdingsData.getSchemeCode().trim(), holdingsData.getSchemeName().trim(), AccountDetails.getToken(getMainActivity()), holdingsData.getISIN(), getMainActivity(), serviceResponseHandler);
                    }
                } else {
                    AvailableSchemeListModel mfSchemeData = (AvailableSchemeListModel) getArguments().getSerializable("Request");
                    String ClientCode = AccountDetails.getUsername(getActivity());
                    String code = "";
                    if (Double.parseDouble(getArguments().getString("Amount")) >= 200000.0) {
                        code = mfSchemeData.getBseRTACode();
                    } else {
                        code = mfSchemeData.getBseCode();
                    }
                    if (code != null && !code.equalsIgnoreCase("")) {
                        if (getArguments().getString("PurchaseType").equalsIgnoreCase("Redeem")) {

                            MutualFundMutualFundSendNewOrderRequest.sendRequest("NEW", getArguments().getString("dpTransaction"), "", "N", getArguments().getString("folioNo"), mfSchemeData.getAMCName(), getArguments().getString("Amount"), ClientCode, "R", "", code, mfSchemeData.getSchemeName(), AccountDetails.getToken(getMainActivity()), mfSchemeData.getISIN(), getMainActivity(), serviceResponseHandler);
                        } else
                            MutualFundMutualFundSendNewOrderRequest.sendRequest("NEW", getArguments().getString("dpTransaction"), "", "N", getArguments().getString("folioNo"), mfSchemeData.getAMCName(), getArguments().getString("Amount"), ClientCode, "P", getArguments().getString("PurchaseType"), code, mfSchemeData.getSchemeName().trim(), AccountDetails.getSessionId(getMainActivity()), mfSchemeData.getISIN(), getMainActivity(), serviceResponseHandler);
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Order cannot be placed at this time.Contact Admin", "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            }
                        });
                    }
                }

            }
        }
    };
    private View mfTradePreviewView;
    private LinearLayout orderDetails, botttom;
    private Boolean changeColor = true;
    private ArrayList<CommonRowData> model;
    private GreekCommonAdapter<CommonRowData> commonAdapter;

    private ListView detView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mfTradePreviewView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mf_tradepreview).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mf_tradepreview).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        setupViews();
        getFromIntent();

        return mfTradePreviewView;


    }

    private void setupViews() {
        GreekButton back = mfTradePreviewView.findViewById(R.id.backBtn);
        GreekButton confirm = mfTradePreviewView.findViewById(R.id.confirmBtn);
        orderDetails = mfTradePreviewView.findViewById(R.id.order_details);
        botttom = mfTradePreviewView.findViewById(R.id.botttom);
        back.setOnClickListener(backClickListener);
        confirm.setOnClickListener(confirmClickListener);


//        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//            orderDetails.setBackgroundColor(getResources().getColor(R.color.white));
//            botttom.setBackgroundColor(getResources().getColor(R.color.white));
//
//        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
//
//            orderDetails.setBackgroundColor(getResources().getColor(R.color.black));
//            botttom.setBackgroundColor(getResources().getColor(R.color.black));
//        }

    }

    private void getFromIntent() {


        if (getArguments().getBoolean("isModifyOrder")) {
            MutualFundOrderLog fundOrderLog = (MutualFundOrderLog) getArguments().getSerializable("Request");

            setAppTitle(getClass().toString(), fundOrderLog.getSchemeName());
            createPositionsRow("Reference Number", fundOrderLog.getRefNo());
            createPositionsRow("Purchase Type", getArguments().getString("PurchaseType"));
            createPositionsRow("Amount", getArguments().getString("Amount"));

        } else {

            if (getArguments().getBoolean("isFromHoldings")) {
                MutualFundHoldingsModel holdingsData = (MutualFundHoldingsModel) getArguments().getSerializable("Request");
                setAppTitle(getClass().toString(), holdingsData.getSchemeName());
                createPositionsRow("Purchase Type", getArguments().getString("PurchaseType"));
                createPositionsRow("Amount", getArguments().getString("Amount"));
                createPositionsRow("NAV ", holdingsData.getNAV());
                createPositionsRow("Min Purchase Amt", holdingsData.getMinPurAmt());
                createPositionsRow("Max Purchase Amt", holdingsData.getMaxPurAmt());

            } else {
                AvailableSchemeListModel mfSchemeData = (AvailableSchemeListModel) getArguments().getSerializable("Request");
                setAppTitle(getClass().toString(), mfSchemeData.getSchemeName());
                createPositionsRow("Purchase Type", getArguments().getString("PurchaseType"));
                createPositionsRow("Amount", getArguments().getString("Amount"));
                createPositionsRow("NAV " + "(" + mfSchemeData.getNAVDate() + ")", mfSchemeData.getNAV());
                createPositionsRow("ISIN", mfSchemeData.getISIN());
                createPositionsRow("Min Purchase Amt", mfSchemeData.getMinPurchaseAmount());
                createPositionsRow("Max Purchase Amt", mfSchemeData.getMaxPurchaseAmount());
            }
        }
    }

    private void createPositionsRow(String key, String value) {
        int color;

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            color = (changeColor) ? R.color.floatingBgColor : R.color.backgroundStripColorWhite;
        } else {
            color = (changeColor) ? R.color.market_grey_dark : R.color.market_grey_light;
        }


        TableRow Row = new TableRow(getMainActivity());
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(key);
        keyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = (TableRow.LayoutParams) keyView.getLayoutParams();
        params.weight = 1;
        params.bottomMargin = 1;
        keyView.setPadding(10, 10, 10, 10);

        GreekTextView valueView = new GreekTextView(getMainActivity());
        valueView.setPadding(10, 12, 5, 12);

        valueView.setText(value);


        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
        valueView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
        valueView.setBackgroundColor(getResources().getColor(color));

        valueView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params1 = (TableRow.LayoutParams) valueView.getLayoutParams();
        params1.weight = 1;
        params1.bottomMargin = 1;

        Row.addView(keyView);
        Row.addView(valueView);
        orderDetails.addView(Row);

        changeColor = !changeColor;
    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        hideProgress();
        if ("orderentryparam".equalsIgnoreCase(jsonResponse.getServiceGroup()) && "getConnectToMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
            try {

                MutualFundMutualFundSendNewOrderResponse orderResponse = (MutualFundMutualFundSendNewOrderResponse) jsonResponse.getResponse();

                GreekDialog.alertDialog(getMainActivity(), 0, orderResponse.getSchemeName(), orderResponse.getStatus(), "View MF Orders", "New MF Trade", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isIPOEnable", false);
                            bundle.putBoolean("isMFEnable", true);
                            navigateTo(NAV_TO_MF_ORDER_BOOK_SCREEN, bundle, false);

                        } else {
                            navigateTo(NAV_TO_MUTUALFUND_ACTION, new Bundle(), false);
                        }

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MUTUALFUND_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && MUTUALFUND_MODIFY_ORDER_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                MutualFundMutualFundModifyOrderResponse modifyOrderResponse = (MutualFundMutualFundModifyOrderResponse) jsonResponse.getResponse();

                GreekDialog.alertDialog(getMainActivity(), 0, modifyOrderResponse.getSchemeName(), modifyOrderResponse.getStatus(), "View MF Orders", "New MF Trade", true, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        if (action == GreekDialog.Action.OK) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isIPOEnable", false);
                            bundle.putBoolean("isMFEnable", true);
                            navigateTo(NAV_TO_ORDER_BOOK_SCREEN, bundle, false);

                        } else {
                            navigateTo(NAV_TO_MUTUALFUND_ACTION, new Bundle(), false);
                        }

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Info Dialog", "View MF Orders", "New MF Trade", true, new GreekDialog.DialogListener() {

            @Override
            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                if (action == GreekDialog.Action.OK) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isIPOEnable", false);
                    bundle.putBoolean("isMFEnable", true);
                    navigateTo(NAV_TO_ORDER_BOOK_SCREEN, bundle, false);

                } else {
                    navigateTo(NAV_TO_MUTUALFUND_ACTION, new Bundle(), false);
                }
            }
        });

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_TRADE_PREVIEW;

    }
}

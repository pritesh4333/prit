package com.acumengroup.mobile.reports;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.FundPayOutResponse;
import com.acumengroup.greekmain.core.network.FundMarginDetailResponse;
import com.acumengroup.greekmain.core.network.MarginDetailRequest;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;


public class FundTransfer_payout_fragment extends GreekBaseFragment {

    private GreekEditText commodityamt, noncommodityamt;
    private GreekTextView commoditytxt, noncommoditytxt, txt_fund,avail_fund,withdraw_text;
    private Button submitbtn;
    private static GreekBaseFragment previousFragment;
    private LinearLayout withdraw_bg;

    public static FundTransfer_payout_fragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        FundTransfer_payout_fragment fragment = new FundTransfer_payout_fragment();
        fragment.setArguments(args);
        FundTransfer_payout_fragment.previousFragment = previousFragment;
        return fragment;
    }


    // Fetches the Watch List Group and Scrips.


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View payOutView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fund_transfer_payout).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fund_transfer_payout).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_TRANSFER_PAYOUT;
        setUpView(payOutView);
        setTheme();
        return payOutView;

    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            withdraw_bg.setBackgroundColor(getResources().getColor(R.color.white));
            commodityamt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            commodityamt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            noncommodityamt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            noncommodityamt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            noncommoditytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            commoditytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            avail_fund.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            withdraw_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_fund.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
          //  submitbtn.setBackgroundColor(getResources().getColor(R.color.buttonColor));

        }
    }

    private void setUpView(View fundtransferView) {
        withdraw_bg = fundtransferView.findViewById(R.id.withdraw_bg);
        withdraw_text = fundtransferView.findViewById(R.id.withdraw_txt);
        avail_fund = fundtransferView.findViewById(R.id.avail_fund);
        commodityamt = fundtransferView.findViewById(R.id.commodity_amt_edtxt);
        txt_fund = fundtransferView.findViewById(R.id.txt_fund);
        noncommodityamt = fundtransferView.findViewById(R.id.noncommodity_amt_edtxt);
        submitbtn = fundtransferView.findViewById(R.id.submitbtn);
        noncommoditytxt = fundtransferView.findViewById(R.id.noncommodity_amt_txt);
        commoditytxt = fundtransferView.findViewById(R.id.commodity_amt_txt);
        sendMarginRequest("0");
        sendAvailableFundsRequest();

        InputFilter[] filters = {new InputFilter.LengthFilter(7)};
        commodityamt.setFilters(filters);
        noncommodityamt.setFilters(filters);


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commodityamttxt, noncommodityamttxt = "";

                if (commodityamt.getText().toString().isEmpty()) {
                    commodityamttxt = "0";
                } else {
                    commodityamttxt = commodityamt.getText().toString();
                }

                if (commodityamttxt.equalsIgnoreCase("0")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Please Enter Amount", "Ok", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        }
                    });

                } else {
                    //If amount is correct then iris request is send to server
                    orderStreamingController.sendFundTransferPayoutRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), "0", commodityamttxt);

                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "WithDraw Amount Submitted", "Ok", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        }
                    });


                }
            }
        });
    }

    private void sendMarginRequest(String exchangeType) {
        MarginDetailRequest marginDetailRequest = new MarginDetailRequest();
        marginDetailRequest.setGcid(AccountDetails.getClientCode(getMainActivity()));
        marginDetailRequest.setSegment(exchangeType);
        marginDetailRequest.setExchange_type("0");
        orderStreamingController.sendMarginDetailRequest(getActivity(), marginDetailRequest);
    }

    private void sendAvailableFundsRequest() {
        MarginDetailRequest marginDetailRequest = new MarginDetailRequest();
        marginDetailRequest.setGcid(AccountDetails.getClientCode(getMainActivity()));
        marginDetailRequest.setSessionId(AccountDetails.getSessionId(getMainActivity()));
        //To check available fund we are requesting to iris server
        orderStreamingController.sendAvailableFundsRequest(getActivity(), marginDetailRequest);
    }


    public void onEventMainThread(FundPayOutResponse response) {
        try {
            GreekDialog.dismissDialog();
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, response.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    sendAvailableFundsRequest();
                }
            });
        } catch (Exception e) {

        }
    }


    public void onEventMainThread(FundMarginDetailResponse marginDetailResponse) {

        try {

            if (marginDetailResponse.getAvailFundTransfer().equalsIgnoreCase("0.00")) {

                txt_fund.setText(String.format("%.2f", Double.parseDouble(marginDetailResponse.getAvailFundTransfer())));

            } else {

                txt_fund.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(marginDetailResponse.getAvailFundTransfer()))));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();

    }


}

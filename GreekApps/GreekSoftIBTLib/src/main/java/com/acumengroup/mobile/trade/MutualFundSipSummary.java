package com.acumengroup.mobile.trade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MutualFundSipNewOrderRequest;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MutualFundSipNewOrderResponse;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MutualFundXSIPNewOrderRequest;
import com.acumengroup.greekmain.core.model.STPOrder.STPOrderlRequest;
import com.acumengroup.greekmain.core.model.mutualfundmutualfundmodifyorder.MutualFundMutualFundModifyOrderResponse;
import com.acumengroup.greekmain.core.model.SWPOrder.SWPOrderlRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.SipSummaryModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MutualFundSipSummary extends GreekBaseFragment {

    private SipSummaryModel summary;
    private String fromFragment;
    GreekTextView amctitle, schmtitle, schmisintitle, mandatetitle, fretitle, paymenttitle, siptitle, installtitle, sipstarttitle, sipendtitle, transactionidtitle, dptitle;
    GreekTextView amcNameValue, mandateIDValue, schemeNameValue, schemeIsinValue, sipFrequencyValue, paymentModeValue, sipInstallmentAmountValue, sipNoOfInstValue, sipStartDateValue, sipEndDateValue, transModeValue, dpTransModeValue;
    private Button btnConfirmOrder;
    LinearLayout amcNamelayout, schemeTitlelayout, schemeIsinlayout, mandateIdlayout, sipFrequencylayout, paymentModelayout, InstallmentAmountLayout, noOfInstallLayout, startdatelayout, enddatelayout, transactionModelayout, dpTransactionModelayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mfSummaryView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mf_sip_summary).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mf_sip_summary).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }


        amcNamelayout = mfSummaryView.findViewById(R.id.amcNamelayout);
        schemeTitlelayout = mfSummaryView.findViewById(R.id.schemeTitlelayout);
        schemeIsinlayout = mfSummaryView.findViewById(R.id.schemeIsinlayout);
        mandateIdlayout = mfSummaryView.findViewById(R.id.mandateIdlayout);
        sipFrequencylayout = mfSummaryView.findViewById(R.id.sipFrequencylayout);
        paymentModelayout = mfSummaryView.findViewById(R.id.paymentModelayout);
        InstallmentAmountLayout = mfSummaryView.findViewById(R.id.InstallmentAmountLayout);
        noOfInstallLayout = mfSummaryView.findViewById(R.id.noOfInstallLayout);
        startdatelayout = mfSummaryView.findViewById(R.id.startdatelayout);
        enddatelayout = mfSummaryView.findViewById(R.id.enddatelayout);
        transactionModelayout = mfSummaryView.findViewById(R.id.transactionModelayout);
        dpTransactionModelayout = mfSummaryView.findViewById(R.id.dpTransactionModelayout);


        amcNameValue = mfSummaryView.findViewById(R.id.amcNameValue);
        mandateIDValue = mfSummaryView.findViewById(R.id.mandateIDValue);
        schemeNameValue = mfSummaryView.findViewById(R.id.schemeNameValue);
        schemeIsinValue = mfSummaryView.findViewById(R.id.schemeIsinValue);
        sipFrequencyValue = mfSummaryView.findViewById(R.id.sipFrequencyValue);
        paymentModeValue = mfSummaryView.findViewById(R.id.paymentModeValue);
        sipInstallmentAmountValue = mfSummaryView.findViewById(R.id.sipInstallmentAmountValue);
        sipNoOfInstValue = mfSummaryView.findViewById(R.id.sipNoOfInstValue);
        sipStartDateValue = mfSummaryView.findViewById(R.id.sipStartDateValue);
        sipEndDateValue = mfSummaryView.findViewById(R.id.sipEndDateValue);
        transModeValue = mfSummaryView.findViewById(R.id.transModeValue);
        dpTransModeValue = mfSummaryView.findViewById(R.id.dptransModeValue);
        amctitle = mfSummaryView.findViewById(R.id.amctitle);

        schmtitle = mfSummaryView.findViewById(R.id.schm_title);
        schmisintitle = mfSummaryView.findViewById(R.id.schmisin_title);
        mandatetitle = mfSummaryView.findViewById(R.id.mandate_title);
        fretitle = mfSummaryView.findViewById(R.id.fre_title);
        paymenttitle = mfSummaryView.findViewById(R.id.payment_title);
        siptitle = mfSummaryView.findViewById(R.id.sip_title);
        installtitle = mfSummaryView.findViewById(R.id.install_title);
        sipstarttitle = mfSummaryView.findViewById(R.id.sip_start_title);
        sipendtitle = mfSummaryView.findViewById(R.id.sip_end_title);
        transactionidtitle = mfSummaryView.findViewById(R.id.transaction_id_title);
        dptitle = mfSummaryView.findViewById(R.id.dp_title);

        int textColor = AccountDetails.getTextColorDropdown();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            schmtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            schmisintitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mandatetitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            fretitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            paymenttitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            siptitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            installtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sipstarttitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sipendtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            transactionidtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dptitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            schemeNameValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            schemeIsinValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mandateIDValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sipFrequencyValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            paymentModeValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sipInstallmentAmountValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sipNoOfInstValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sipStartDateValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sipEndDateValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            transModeValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dpTransModeValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            amctitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            amcNameValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

            schmtitle.setTextColor(getResources().getColor(textColor));
            schmisintitle.setTextColor(getResources().getColor(textColor));
            mandatetitle.setTextColor(getResources().getColor(textColor));
            fretitle.setTextColor(getResources().getColor(textColor));
            paymenttitle.setTextColor(getResources().getColor(textColor));
            siptitle.setTextColor(getResources().getColor(textColor));
            installtitle.setTextColor(getResources().getColor(textColor));
            sipstarttitle.setTextColor(getResources().getColor(textColor));
            sipendtitle.setTextColor(getResources().getColor(textColor));
            transactionidtitle.setTextColor(getResources().getColor(textColor));
            dptitle.setTextColor(getResources().getColor(textColor));
            schemeNameValue.setTextColor(getResources().getColor(textColor));
            schemeIsinValue.setTextColor(getResources().getColor(textColor));
            mandateIDValue.setTextColor(getResources().getColor(textColor));
            sipFrequencyValue.setTextColor(getResources().getColor(textColor));
            paymentModeValue.setTextColor(getResources().getColor(textColor));
            sipInstallmentAmountValue.setTextColor(getResources().getColor(textColor));
            sipNoOfInstValue.setTextColor(getResources().getColor(textColor));
            sipStartDateValue.setTextColor(getResources().getColor(textColor));
            sipEndDateValue.setTextColor(getResources().getColor(textColor));
            transModeValue.setTextColor(getResources().getColor(textColor));
            dpTransModeValue.setTextColor(getResources().getColor(textColor));
            amctitle.setTextColor(getResources().getColor(textColor));
            amcNameValue.setTextColor(getResources().getColor(textColor));


        }


        summary = (SipSummaryModel) getArguments().getSerializable("summary");
        fromFragment = getArguments().getString("from");

        if (fromFragment.equalsIgnoreCase("stp")) {
            if (!summary.getFolioNumber().equalsIgnoreCase("")) {
                amcNamelayout.setVisibility(View.VISIBLE);
                amctitle.setText("Folio No :");
                amcNameValue.setText(summary.getFolioNumber());
            }
            schmtitle.setText("From Scheme :");
            schemeNameValue.setText(summary.getFromSchemeName());
            schmisintitle.setText("To Scheme :");
            schemeIsinValue.setText(summary.getToSchemeName());
            mandatetitle.setText("Transfer Amount :");
            mandateIDValue.setText(summary.getTransferAmt());
            fretitle.setText("STP Frequency :");
            sipFrequencyValue.setText(summary.getSipFrequency());

            paymenttitle.setText("EUIN Decl :");
            if (summary.getEuinDecl().equalsIgnoreCase("n")) {
                paymentModeValue.setText("No");
            } else {
                paymentModeValue.setText("Yes");
            }
            InstallmentAmountLayout.setVisibility(View.GONE);
            sipNoOfInstValue.setText(summary.getSipNoOfInst());
            sipStartDateValue.setText(summary.getStartDate());
            sipEndDateValue.setText(summary.getEndDate());

            transModeValue.setText(getTransMode(summary.getTransmode()));
            dpTransactionModelayout.setVisibility(View.GONE);

        } else if (fromFragment.equalsIgnoreCase("swp")) {
            if (!summary.getFolioNumber().equalsIgnoreCase("")) {
                amcNamelayout.setVisibility(View.VISIBLE);
                amctitle.setText("Folio No :");
                amcNameValue.setText(summary.getFolioNumber());
            }
            schmtitle.setText("Scheme Name:");
            schemeNameValue.setText(summary.getSchemeName());
            schmisintitle.setText("Scheme ISIN:");
            schemeIsinValue.setText(summary.getSchemeIsin());
            mandateIdlayout.setVisibility(View.GONE);
            fretitle.setText("STP Frequency :");
            sipFrequencyValue.setText(summary.getSipFrequency());

            paymenttitle.setText("EUIN Decl :");
            if (summary.getEuinDecl().equalsIgnoreCase("n")) {
                paymentModeValue.setText("No");
            } else {
                paymentModeValue.setText("Yes");
            }
            siptitle.setText("Withdrawal Amount :");
            sipInstallmentAmountValue.setText(summary.getWithdrawalsAmt());
            sipNoOfInstValue.setText(summary.getSipNoOfInst());
            sipStartDateValue.setText(summary.getStartDate());
            sipEndDateValue.setText(summary.getEndDate());
            transModeValue.setText(getTransMode(summary.getTransmode()));
            dptitle.setText("Withdrawal Unit");
            dpTransModeValue.setText(summary.getWithdrawalsUnit());
        } else {
            if (!summary.getFolioNumber().equalsIgnoreCase("")) {
                amcNamelayout.setVisibility(View.VISIBLE);
                amctitle.setText("Folio No :");
                amcNameValue.setText(summary.getFolioNumber());
            }
            mandateIDValue.setText(summary.getMandateId());
            schemeNameValue.setText(summary.getSchemeName());
            schemeIsinValue.setText(summary.getSchemeIsin());
            sipFrequencyValue.setText(summary.getSipFrequency());
            paymentModeValue.setText(summary.getPaymentMode());
            sipInstallmentAmountValue.setText(summary.getSipAmount());
            sipNoOfInstValue.setText(summary.getSipNoOfInst());
            sipStartDateValue.setText(summary.getStartDate());
            sipEndDateValue.setText(summary.getEndDate());
            transModeValue.setText(getTransMode(summary.getTransmode()));
            dpTransModeValue.setText(getDPTransMode(summary.getDpTransaction()));
        }

        btnConfirmOrder = mfSummaryView.findViewById(R.id.btnConfirmOrder);
        btnConfirmOrder.setClickable(true);
        btnConfirmOrder.setEnabled(true);

        Button btnCancelOrder = mfSummaryView.findViewById(R.id.btnCancelOrder);
        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackOnce();
            }
        });

        return mfSummaryView;
    }

    private String getTransMode(String transMode) {

        String transactionMode = "";
        if (transMode.equalsIgnoreCase("d")) {
            transactionMode = "Demat";
        } else if (transMode.equalsIgnoreCase("p")) {
            transactionMode = "Physical";
        }

        return transactionMode;
    }

    private String getDPTransMode(String dptransMode) {

        String dpTransactionMode = "";
        if (dptransMode.equalsIgnoreCase("c")) {
            dpTransactionMode = "CDSL";
        } else if (dptransMode.equalsIgnoreCase("n")) {
            dpTransactionMode = "NSDL";
        } else if (dptransMode.equalsIgnoreCase("p")) {
            dpTransactionMode = "Physical";
        }

        return dpTransactionMode;
    }

    private void confirmOrder() {

        String ClientCode = AccountDetails.getUsername(getMainActivity());
        String code = "";
        if (summary.getSipAmount() != null && !summary.getSipAmount().equalsIgnoreCase("")) {
            if (Double.parseDouble(summary.getSipAmount()) >= 200000.0) {
                code = summary.getBseRTACode();
            } else {
                code = summary.getBseCode();
            }
        }
        if (fromFragment.equalsIgnoreCase("sip")) {
            if (code != null && !code.equalsIgnoreCase("")) {
                MutualFundSipNewOrderRequest.sendRequest(summary.getSchemeName(), ClientCode,
                        "NEW", "", summary.getSchemeIsin(), summary.getTransmode(),
                        summary.getDpTransaction(), summary.getSipFrequency(), summary.getPaymentMode(),
                        summary.getSipAmount(), summary.getStartDate(), summary.getSipNoOfInst(),
                        "", "MyBroker", summary.getLocalIp(), "2524",
                        code, getMainActivity(), serviceResponseHandler);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "SIP cannot be placed at this time.Contact Admin", "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            }
        } else if (fromFragment.equalsIgnoreCase("xsip")) {

            if (code != null && !code.equalsIgnoreCase("")) {
                MutualFundXSIPNewOrderRequest.sendRequest(summary.getSchemeName(), ClientCode, "NEW", "", summary.getSchemeIsin(), summary.getTransmode(), summary.getDpTransaction(), summary.getSipFrequency(), summary.getPaymentMode(), summary.getSipAmount(), summary.getStartDate(), summary.getSipNoOfInst(), "", "MyBroker", summary.getLocalIp(), "2524", code, summary.getMandateId(), getMainActivity(), serviceResponseHandler);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "SIP cannot be placed at this time.Contact Admin", "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    }
                });
            }
        } else if (fromFragment.equalsIgnoreCase("swp")) {
            SWPOrderlRequest.sendRequest(/*summary.getSchemeName()*/ClientCode, "08", summary.getSchemeCode(), summary.getTransmode(), "undefined", summary.getInternalRefNumber(), summary.getStartDate(), summary.getWithdrawalsUnit(), summary.getSipFrequency(), summary.getWithdrawalsAmt(), summary.getSipNoOfInst(), summary.getFirstOrderToday(), "", summary.getEuinDecl(), "", "", "", summary.getSchemeName(), getMainActivity(), serviceResponseHandler);
        } else if (fromFragment.equalsIgnoreCase("stp")) {
            STPOrderlRequest.sendRequest(/*summary.getSchemeName()*/ClientCode, "07", summary.getFromSchemeCode(), summary.getToSchemeCode(), summary.getBuySelltype(), summary.getTransmode(), "undefined", summary.getInternalRefNumber(), summary.getStartDate(), summary.getSipFrequency(), summary.getTransferAmt(), summary.getFirstOrderToday(), "", summary.getEuinDecl(), "", "", "", summary.getNumberOfTransfer(), summary.getFromSchemeName(), summary.getToSchemeName(), getMainActivity(), serviceResponseHandler);
        }

    }


    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        hideProgress();

        btnConfirmOrder.setClickable(false);
        btnConfirmOrder.setEnabled(false);
        btnConfirmOrder.setBackgroundResource(R.color.grey_text);

        if ("siporderentryparam".equalsIgnoreCase(jsonResponse.getServiceGroup()) && "getConnectToMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
            try {

                MutualFundSipNewOrderResponse orderResponse = (MutualFundSipNewOrderResponse) jsonResponse.getResponse();

                String sipId = orderResponse.getSIP_ID();
                String status = orderResponse.getStatus();
                GreekDialog.alertDialog(getMainActivity(), 0, orderResponse.getSchemeName(), "SIP Order: " + status, "Ok", true, null);
                navigateTo(NAV_TO_MUTUALFUND_NO_SENSITIVE, new Bundle(), false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("xsiporderentryparam".equalsIgnoreCase(jsonResponse.getServiceGroup()) && "getConnectToMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
            try {

                MutualFundSipNewOrderResponse orderResponse = (MutualFundSipNewOrderResponse) jsonResponse.getResponse();

                String sipId = orderResponse.getSIP_ID();
                String status = orderResponse.getStatus();
                GreekDialog.alertDialog(getMainActivity(), 0, orderResponse.getSchemeName(), "SIP Order: " + status, "Ok", true, null);
                navigateTo(NAV_TO_MUTUALFUND_NO_SENSITIVE, new Bundle(), false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("MFAPI".equalsIgnoreCase(jsonResponse.getServiceGroup()) && "getConnectToMF".equalsIgnoreCase(jsonResponse.getServiceName())) {
            try {

                MutualFundSipNewOrderResponse orderResponse = (MutualFundSipNewOrderResponse) jsonResponse.getResponse();


                String status = orderResponse.getStatus();
                if (status.contains("SWP REGISTRATION")) {
                    String SWPid = orderResponse.getSWPId();
                    GreekDialog.alertDialog(getMainActivity(), 0, orderResponse.getSchemeName(), "SWP Order: " + status, "Ok", true, null);
                } else if (status.contains("STP REGISTRATION")) {
                    String STPid = orderResponse.getSTPId();
                    GreekDialog.alertDialog(getMainActivity(), 0, orderResponse.getSchemeName(), "STP Order: " + status, "Ok", true, null);
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, orderResponse.getSchemeName(), "Order Failed: " + status, "Ok", true, null);
                }
                navigateTo(NAV_TO_MUTUALFUND_NO_SENSITIVE, new Bundle(), false);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MUTUALFUND_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && MUTUALFUND_MODIFY_ORDER_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                MutualFundMutualFundModifyOrderResponse modifyOrderResponse = (MutualFundMutualFundModifyOrderResponse) jsonResponse.getResponse();

                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), modifyOrderResponse.getStatus(), "View MF Orders", "New MF Trade", true, new GreekDialog.DialogListener() {

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
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUAL_FUND_SIP_SUMMARY;
    }
}


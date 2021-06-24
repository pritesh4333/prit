package com.acumengroup.mobile.markets;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.marketssinglescrip.ContractInfoRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.ContractInfoResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerSymbolVarMarginResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 25-Jul-17.
 */

public class ContractInformationFragment extends GreekBaseFragment {

    private View contractInfoView;
    private String symbol, assetType, exchange = "";
    private GreekTextView cSeries, InstrumentType, dIssueCapital, iPermittedToTrade, cCreditRating, iNormal_SecurityStatus, iNormal_MarketAllowed, iOddLot_SecurityStatus, OddLot_MarketAllowed, iSpot_SecurityStatus, iSpot_MarketAllowed, iAuction_SecurityStatus, iAuction_MarketAllowed, lRegularLot, dPriceTick, dIssueRate, lIssueStartDt, lIssuePDt, lIssueMaturityDt, lFreezeQty, lListingDt, lExpulsionDt, lReadmissionDt, lExDt, lRecordDt, lNoDeliveryStartDt, lNoDeliveryEndDt, lIndexParticipant, iAONAllowed, iMFAllowed, dWarmingPercent, lBookClosureStartDt, lBookClosureEndDt, iDividend, iRights, iBonus, iInterest, iAGM, iEGM, cRemarks, cDeleteFlag, lLastUpdateTime, dFacevalue, cISINNumber, iCallAuction_MarketAllowed, iCallAuction_SecurityStatus, iCallAuction2_SecurityStatus, iCallAuction2_MarketAllowed, lSpread, lMinQty, iSSEC, iSurvInd, VARMargin, ELMMargin, ELMPercentage, VARPercentage, dGeneralNumerator, dGeneralDenominator, dLotNumerator, dLotDenominator, dDecimalLocator, dSellMarginRate, iTermsOfSpecialMargin, dBuySpecialMarginRate, dSellSpecialMarginRate, iMarginSpreadBenefitFlag, lInstrumentEndDate, cTradingCurrency, cContractMonth, iPreOpenAllowed, iGroupId, iMatchingType, iSpreadType, cValueMethod, cSLBMEligibility, dIRFfactor;
    private GreekTextView cSerieslbl, InstrumentTypelbl, dIssueCapitallbl, iPermittedToTradelbl, cCreditRatinglbl, iNormal_SecurityStatuslbl, iNormal_MarketAllowedlbl, description, symbolNameTxt, iOddLot_SecurityStatuslbl, OddLot_MarketAllowedlbl, iSpot_SecurityStatuslbl, iSpot_MarketAllowedlbl, iAuction_SecurityStatuslbl, iAuction_MarketAllowedlbl, lRegularLotlbl, dPriceTicklbl, dIssueRatelbl, lIssueStartDtlbl, lIssuePDtlbl, lIssueMaturityDtlbl, lFreezeQtylbl, lListingDtlbl, lExpulsionDtlbl, lReadmissionDtlbl, lExDtlbl, lRecordDtlbl, lNoDeliveryStartDtlbl, lNoDeliveryEndDtlbl, lIndexParticipantlbl, iAONAllowedlbl, iMFAllowedlbl, dWarmingPercentlbl, lBookClosureStartDtlbl, lBookClosureEndDtlbl, iDividendlbl, iRightslbl, iBonuslbl, iInterestlbl, iAGMlbl, iEGMlbl, cRemarkslbl, cDeleteFlaglbl, lLastUpdateTimelbl, dFacevaluelbl, cISINNumberlbl, iCallAuction_MarketAllowedlbl, iCallAuction_SecurityStatuslbl, iCallAuction2_SecurityStatuslbl, iCallAuction2_MarketAllowedlbl, lSpreadlbl, lMinQtylbl, iSSEClbl, iSurvIndlbl, VARMarginlbl, ELMMarginlbl, ELMPercentagelbl, VARPercentagelbl, dGeneralNumeratorlbl, dGeneralDenominatorlbl, dLotNumeratorlbl, dLotDenominatorlbl, dDecimalLocatorlbl, dSellMarginRatelbl, iTermsOfSpecialMarginlbl, dBuySpecialMarginRatelbl, dSellSpecialMarginRatelbl, iMarginSpreadBenefitFlaglbl, lInstrumentEndDatelbl, cTradingCurrencylbl, cContractMonthlbl, iPreOpenAllowedlbl, iGroupIdlbl, iMatchingTypelbl, iSpreadTypelbl, cValueMethodlbl, cSLBMEligibilitylbl, dIRFfactorlbl;
    private LinearLayout view0, view1, view2, view3, view4, view5, view6, view7, view8, view9, view10, view11, view12, view13, view14, view15, view16, view17, view18, view19, view20, view21, view22, view23, view24, view25, view26, view27, view28;
    private int market_id;
    private LinearLayout mcxview0, mcxview1, mcxview2, mcxview3, mcxview4, mcxview5, mcxview6, mcxview7, mcxview8, mcxview9, mcxview;
    private RelativeLayout errorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contractInfoView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_contract_info).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_contract_info).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_CONTRACTINFO_SCREEN;
        setupViews();
        settingThemeAssetContract();

        return contractInfoView;
    }

    private void settingThemeAssetContract() {

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            symbolNameTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            description.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cSeries.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            InstrumentType.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dIssueCapital.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iPermittedToTrade.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cCreditRating.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iNormal_SecurityStatus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iNormal_MarketAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iOddLot_SecurityStatus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            OddLot_MarketAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSpot_SecurityStatus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSpot_MarketAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAuction_SecurityStatus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAuction_MarketAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lRegularLot.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dPriceTick.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dIssueRate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIssueStartDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIssuePDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIssueMaturityDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lFreezeQty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lListingDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lExpulsionDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lReadmissionDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lExDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lRecordDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lNoDeliveryStartDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lNoDeliveryEndDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIndexParticipant.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAONAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iMFAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dWarmingPercent.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lBookClosureStartDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lBookClosureEndDt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iDividend.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iRights.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iBonus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iInterest.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAGM.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iEGM.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cRemarks.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cDeleteFlag.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lLastUpdateTime.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dFacevalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cISINNumber.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction_MarketAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction_SecurityStatus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction2_SecurityStatus.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction2_MarketAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lSpread.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lMinQty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSSEC.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSurvInd.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            VARMargin.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            VARPercentage.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ELMMargin.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ELMPercentage.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            dGeneralNumerator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dGeneralDenominator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dLotNumerator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dLotDenominator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dDecimalLocator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dSellMarginRate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iTermsOfSpecialMargin.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dBuySpecialMarginRate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dSellSpecialMarginRate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iMarginSpreadBenefitFlag.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lInstrumentEndDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cTradingCurrency.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cContractMonth.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iPreOpenAllowed.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iGroupId.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iMatchingType.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSpreadType.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cValueMethod.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cSLBMEligibility.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dIRFfactor.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


            cSerieslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            InstrumentTypelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dIssueCapitallbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iPermittedToTradelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cCreditRatinglbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iNormal_SecurityStatuslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iNormal_MarketAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iOddLot_SecurityStatuslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            OddLot_MarketAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSpot_SecurityStatuslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSpot_MarketAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAuction_SecurityStatuslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAuction_MarketAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lRegularLotlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dPriceTicklbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dIssueRatelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIssueStartDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIssuePDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIssueMaturityDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lFreezeQtylbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lListingDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lExpulsionDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lReadmissionDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lExDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lRecordDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lNoDeliveryStartDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lNoDeliveryEndDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lIndexParticipantlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAONAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iMFAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dWarmingPercentlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lBookClosureStartDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lBookClosureEndDtlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iDividendlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iRightslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iBonuslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iInterestlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iAGMlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iEGMlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cRemarkslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cDeleteFlaglbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lLastUpdateTimelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dFacevaluelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cISINNumberlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction_MarketAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction_SecurityStatuslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction2_SecurityStatuslbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iCallAuction2_MarketAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lSpreadlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lMinQtylbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSSEClbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSurvIndlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            VARMarginlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            VARPercentagelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ELMMarginlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ELMPercentagelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            dGeneralNumeratorlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dGeneralDenominatorlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dLotNumeratorlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dLotDenominatorlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dDecimalLocatorlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dSellMarginRatelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iTermsOfSpecialMarginlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dBuySpecialMarginRatelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dSellSpecialMarginRatelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iMarginSpreadBenefitFlaglbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lInstrumentEndDatelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cTradingCurrencylbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cContractMonthlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iPreOpenAllowedlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iGroupIdlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iMatchingTypelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            iSpreadTypelbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cValueMethodlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            cSLBMEligibilitylbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dIRFfactorlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


            view0.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view1.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view2.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view3.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view4.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view5.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view6.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view7.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view8.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view9.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view10.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view11.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view12.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view13.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view14.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view15.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view16.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view17.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view18.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view19.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view20.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view21.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view22.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view23.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view24.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view25.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view26.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view27.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            view28.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));


            mcxview0.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview1.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview2.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview3.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview4.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview5.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview6.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview7.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview8.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            mcxview9.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        }


    }

    public int getMarketId(String token) {
        int tokenNumber = Integer.parseInt(token);
        if (tokenNumber >= 101000000 && tokenNumber <= 101999999) {
            return 1;
        }
        if (tokenNumber >= 102000000 && tokenNumber <= 102999999) {
            return 2;
        }
        if (tokenNumber >= 502000000 && tokenNumber <= 502999999) {
            return 3;
        }
        if (tokenNumber >= 201000000 && tokenNumber <= 201999999) {
            return 4;
        }
        if (tokenNumber >= 202000000 && tokenNumber <= 202999999) {
            return 5;
        }
        if (tokenNumber >= 1302000000 && tokenNumber <= 1302999999) {
            return 6;
        }

        return 0;
    }


    private void setupViews() {
        symbol = getArguments().getString("Token");
        assetType = getArguments().getString("AssetType");
        exchange = getArguments().getString("Exchange");
        market_id = getMarketId(symbol);

        symbolNameTxt = contractInfoView.findViewById(R.id.cSymbol);
        description = contractInfoView.findViewById(R.id.cSecurityDesc);
        cSeries = contractInfoView.findViewById(R.id.cSeries_txt);
        InstrumentType = contractInfoView.findViewById(R.id.InstrumentType_txt);
        dIssueCapital = contractInfoView.findViewById(R.id.dIssueCapital_txt);
        iPermittedToTrade = contractInfoView.findViewById(R.id.iPermittedToTrade_txt);
        cCreditRating = contractInfoView.findViewById(R.id.cCreditRating_txt);
        iNormal_SecurityStatus = contractInfoView.findViewById(R.id.iNormal_SecurityStatus_txt);
        iNormal_MarketAllowed = contractInfoView.findViewById(R.id.iNormal_MarketAllowed_txt);
        iOddLot_SecurityStatus = contractInfoView.findViewById(R.id.iOddLot_SecurityStatus_txt);
        OddLot_MarketAllowed = contractInfoView.findViewById(R.id.OddLot_MarketAllowed_txt);
        iSpot_SecurityStatus = contractInfoView.findViewById(R.id.iSpot_SecurityStatus_txt);
        iSpot_MarketAllowed = contractInfoView.findViewById(R.id.iSpot_MarketAllowed_txt);
        iAuction_SecurityStatus = contractInfoView.findViewById(R.id.iAuction_SecurityStatus_text);
        iAuction_MarketAllowed = contractInfoView.findViewById(R.id.iAuction_MarketAllowed_txt);
        lRegularLot = contractInfoView.findViewById(R.id.lRegularLot_text);
        dPriceTick = contractInfoView.findViewById(R.id.dPriceTick_txt);
        dIssueRate = contractInfoView.findViewById(R.id.dIssueRate_text);
        lIssueStartDt = contractInfoView.findViewById(R.id.lIssueStartDt_txt);
        lIssuePDt = contractInfoView.findViewById(R.id.lIssuePDt_text);
        lIssueMaturityDt = contractInfoView.findViewById(R.id.lIssueMaturityDt_txt);
        lFreezeQty = contractInfoView.findViewById(R.id.lFreezeQty_text);
        lListingDt = contractInfoView.findViewById(R.id.lListingDt_txt);
        lExpulsionDt = contractInfoView.findViewById(R.id.lExpulsionDt_text);
        lReadmissionDt = contractInfoView.findViewById(R.id.lReadmissionDt_txt);
        lExDt = contractInfoView.findViewById(R.id.lExDt_text);
        lRecordDt = contractInfoView.findViewById(R.id.lRecordDt_txt);
        lNoDeliveryStartDt = contractInfoView.findViewById(R.id.lNoDeliveryStartDt_text);
        lNoDeliveryEndDt = contractInfoView.findViewById(R.id.lNoDeliveryEndDt_txt);
        lIndexParticipant = contractInfoView.findViewById(R.id.lIndexParticipant_text);
        iAONAllowed = contractInfoView.findViewById(R.id.iAONAllowed_txt);
        iMFAllowed = contractInfoView.findViewById(R.id.iMFAllowed_text);
        dWarmingPercent = contractInfoView.findViewById(R.id.dWarmingPercent_txt);
        lBookClosureStartDt = contractInfoView.findViewById(R.id.lBookClosureStartDt_text);
        lBookClosureEndDt = contractInfoView.findViewById(R.id.lBookClosureEndDt_txt);
        iDividend = contractInfoView.findViewById(R.id.iDividend_text);
        iRights = contractInfoView.findViewById(R.id.iRights_txt);
        iBonus = contractInfoView.findViewById(R.id.iBonus_text);
        iInterest = contractInfoView.findViewById(R.id.iInterest_txt);
        iAGM = contractInfoView.findViewById(R.id.iAGM_text);
        iEGM = contractInfoView.findViewById(R.id.iEGM_txt);
        cRemarks = contractInfoView.findViewById(R.id.cRemarks_text);
        cDeleteFlag = contractInfoView.findViewById(R.id.cDeleteFlag_txt);
        lLastUpdateTime = contractInfoView.findViewById(R.id.lLastUpdateTime_text);
        dFacevalue = contractInfoView.findViewById(R.id.dFacevalue_txt);
        cISINNumber = contractInfoView.findViewById(R.id.cISINNumber_text);
        iCallAuction_MarketAllowed = contractInfoView.findViewById(R.id.iCallAuction_MarketAllowed_txt);
        iCallAuction_SecurityStatus = contractInfoView.findViewById(R.id.iCallAuction_SecurityStatus_text);
        iCallAuction2_SecurityStatus = contractInfoView.findViewById(R.id.iCallAuction2_SecurityStatus_txt);
        iCallAuction2_MarketAllowed = contractInfoView.findViewById(R.id.iCallAuction2_MarketAllowed_text);
        lSpread = contractInfoView.findViewById(R.id.lSpread_txt);
        lMinQty = contractInfoView.findViewById(R.id.lMinQty_text);
        iSSEC = contractInfoView.findViewById(R.id.iSSEC_txt);
        iSurvInd = contractInfoView.findViewById(R.id.iSurvInd_text);
        VARMargin = contractInfoView.findViewById(R.id.VARMargin_txt);
        ELMMargin = contractInfoView.findViewById(R.id.ELMMargin_txt);
        ELMPercentage = contractInfoView.findViewById(R.id.ELMPercentage_text);
        VARPercentage = contractInfoView.findViewById(R.id.VARPercentage_text);

        dGeneralNumerator = contractInfoView.findViewById(R.id.dGeneralNumerator_txt);
        dGeneralDenominator = contractInfoView.findViewById(R.id.dGeneralDenominator_txt);
        dLotNumerator = contractInfoView.findViewById(R.id.dLotNumerator_txt);
        dLotDenominator = contractInfoView.findViewById(R.id.dLotDenominator_txt);
        dDecimalLocator = contractInfoView.findViewById(R.id.dDecimalLocator_txt);
        dSellMarginRate = contractInfoView.findViewById(R.id.dSellMarginRate_txt);
        iTermsOfSpecialMargin = contractInfoView.findViewById(R.id.iTermsOfSpecialMargin_txt);
        dBuySpecialMarginRate = contractInfoView.findViewById(R.id.dBuySpecialMarginRate_txt);
        dSellSpecialMarginRate = contractInfoView.findViewById(R.id.dSellMarginRate_txt);
        iMarginSpreadBenefitFlag = contractInfoView.findViewById(R.id.iMarginSpreadBenefitFlag_txt);
        lInstrumentEndDate = contractInfoView.findViewById(R.id.lInstrumentEndDate_txt);
        cTradingCurrency = contractInfoView.findViewById(R.id.cTradingCurrency_txt);
        cContractMonth = contractInfoView.findViewById(R.id.cContractMonth_txt);
        iPreOpenAllowed = contractInfoView.findViewById(R.id.iPreOpenAllowed_txt);
        iGroupId = contractInfoView.findViewById(R.id.iGroupId_txt);
        iMatchingType = contractInfoView.findViewById(R.id.iMatchingType_txt);
        iSpreadType = contractInfoView.findViewById(R.id.iSpreadType_txt);
        cValueMethod = contractInfoView.findViewById(R.id.cValueMethod_txt);
        cSLBMEligibility = contractInfoView.findViewById(R.id.cSLBMEligibility_txt);
        dIRFfactor = contractInfoView.findViewById(R.id.dIRFfactor_txt);


        cSerieslbl = contractInfoView.findViewById(R.id.cSeriesLbl);
        InstrumentTypelbl = contractInfoView.findViewById(R.id.InstrumentTypeLbl);
        dIssueCapitallbl = contractInfoView.findViewById(R.id.dIssueCapitalLbl);
        iPermittedToTradelbl = contractInfoView.findViewById(R.id.iPermittedToTradelbl);
        cCreditRatinglbl = contractInfoView.findViewById(R.id.cCreditRatingLbl);
        iNormal_SecurityStatuslbl = contractInfoView.findViewById(R.id.iNormal_SecurityStatusLbl);
        iNormal_MarketAllowedlbl = contractInfoView.findViewById(R.id.iNormal_MarketAllowedLbl);
        iOddLot_SecurityStatuslbl = contractInfoView.findViewById(R.id.iOddLot_SecurityStatuslbl);
        OddLot_MarketAllowedlbl = contractInfoView.findViewById(R.id.OddLot_MarketAllowedLbl);
        iSpot_SecurityStatuslbl = contractInfoView.findViewById(R.id.iSpot_SecurityStatusLbl);
        iSpot_MarketAllowedlbl = contractInfoView.findViewById(R.id.iSpot_MarketAllowedLbl);
        iAuction_SecurityStatuslbl = contractInfoView.findViewById(R.id.iAuction_SecurityStatuslbl);
        iAuction_MarketAllowedlbl = contractInfoView.findViewById(R.id.iAuction_MarketAllowedLbl);
        lRegularLotlbl = contractInfoView.findViewById(R.id.lRegularLotlbl);
        dPriceTicklbl = contractInfoView.findViewById(R.id.dPriceTickLbl);
        dIssueRatelbl = contractInfoView.findViewById(R.id.dIssueRatelbl);
        lIssueStartDtlbl = contractInfoView.findViewById(R.id.lIssueStartDtLbl);
        lIssuePDtlbl = contractInfoView.findViewById(R.id.lIssuePDtlbl);
        lIssueMaturityDtlbl = contractInfoView.findViewById(R.id.lIssueMaturityDtLbl);
        lFreezeQtylbl = contractInfoView.findViewById(R.id.lFreezeQtylbl);
        lListingDtlbl = contractInfoView.findViewById(R.id.lListingDtLbl);
        lExpulsionDtlbl = contractInfoView.findViewById(R.id.lExpulsionDtlbl);
        lReadmissionDtlbl = contractInfoView.findViewById(R.id.lReadmissionDtLbl);
        lExDtlbl = contractInfoView.findViewById(R.id.lExDtlbl);
        lRecordDtlbl = contractInfoView.findViewById(R.id.lRecordDtLbl);
        lNoDeliveryStartDtlbl = contractInfoView.findViewById(R.id.lNoDeliveryStartDtlbl);
        lNoDeliveryEndDtlbl = contractInfoView.findViewById(R.id.lNoDeliveryEndDtLbl);
        lIndexParticipantlbl = contractInfoView.findViewById(R.id.lIndexParticipantlbl);
        iAONAllowedlbl = contractInfoView.findViewById(R.id.iAONAllowedLbl);
        iMFAllowedlbl = contractInfoView.findViewById(R.id.iMFAllowedlbl);
        dWarmingPercentlbl = contractInfoView.findViewById(R.id.dWarmingPercentLbl);
        lBookClosureStartDtlbl = contractInfoView.findViewById(R.id.lBookClosureStartDtlbl);
        lBookClosureEndDtlbl = contractInfoView.findViewById(R.id.lBookClosureEndDtLbl);
        iDividendlbl = contractInfoView.findViewById(R.id.iDividendlbl);
        iRightslbl = contractInfoView.findViewById(R.id.iRightsLbl);
        iBonuslbl = contractInfoView.findViewById(R.id.iBonuslbl);
        iInterestlbl = contractInfoView.findViewById(R.id.iInterestLbl);
        iAGMlbl = contractInfoView.findViewById(R.id.iAGMlbl);
        iEGMlbl = contractInfoView.findViewById(R.id.iEGMLbl);
        cRemarkslbl = contractInfoView.findViewById(R.id.cRemarkstlbl);
        cDeleteFlaglbl = contractInfoView.findViewById(R.id.cDeleteFlagLbl);
        lLastUpdateTimelbl = contractInfoView.findViewById(R.id.lLastUpdateTimelbl);
        dFacevaluelbl = contractInfoView.findViewById(R.id.dFacevalueLbl);
        cISINNumberlbl = contractInfoView.findViewById(R.id.cISINNumberlbl);
        iCallAuction_MarketAllowedlbl = contractInfoView.findViewById(R.id.iCallAuction_MarketAllowedLbl);
        iCallAuction_SecurityStatuslbl = contractInfoView.findViewById(R.id.iCallAuction_SecurityStatuslbl);
        iCallAuction2_SecurityStatuslbl = contractInfoView.findViewById(R.id.iCallAuction2_SecurityStatusLbl);
        iCallAuction2_MarketAllowedlbl = contractInfoView.findViewById(R.id.iCallAuction2_MarketAllowedlbl);
        lSpreadlbl = contractInfoView.findViewById(R.id.lSpreadLbl);
        lMinQtylbl = contractInfoView.findViewById(R.id.lMinQtylbl);
        iSSEClbl = contractInfoView.findViewById(R.id.iSSECLbl);
        iSurvIndlbl = contractInfoView.findViewById(R.id.iSurvIndlbl);
        VARMarginlbl = contractInfoView.findViewById(R.id.VARMarginLbl);
        ELMMarginlbl = contractInfoView.findViewById(R.id.ELMMarginLbl);
        ELMPercentagelbl = contractInfoView.findViewById(R.id.ELMPercentagelbl);
        VARPercentagelbl = contractInfoView.findViewById(R.id.VARPercentagelbl);

        dGeneralNumeratorlbl = contractInfoView.findViewById(R.id.dGeneralNumeratorlbl);
        dGeneralDenominatorlbl = contractInfoView.findViewById(R.id.dGeneralDenominatorlbl);
        dLotNumeratorlbl = contractInfoView.findViewById(R.id.dLotNumeratorlbl);
        dLotDenominatorlbl = contractInfoView.findViewById(R.id.dLotDenominatorlbl);
        dDecimalLocatorlbl = contractInfoView.findViewById(R.id.dDecimalLocatorlbl);
        dSellMarginRatelbl = contractInfoView.findViewById(R.id.dSellMarginRatelbl);
        iTermsOfSpecialMarginlbl = contractInfoView.findViewById(R.id.iTermsOfSpecialMarginlbl);
        dBuySpecialMarginRatelbl = contractInfoView.findViewById(R.id.dBuySpecialMarginRatelbl);
        dSellSpecialMarginRatelbl = contractInfoView.findViewById(R.id.dSellSpecialMarginRatelbl);
        iMarginSpreadBenefitFlaglbl = contractInfoView.findViewById(R.id.iMarginSpreadBenefitFlaglbl);
        lInstrumentEndDatelbl = contractInfoView.findViewById(R.id.lInstrumentEndDatelbl);
        cTradingCurrencylbl = contractInfoView.findViewById(R.id.cTradingCurrencylbl);
        cContractMonthlbl = contractInfoView.findViewById(R.id.cContractMonthlbl);
        iPreOpenAllowedlbl = contractInfoView.findViewById(R.id.iPreOpenAllowedlbl);
        iGroupIdlbl = contractInfoView.findViewById(R.id.iGroupIdlbl);
        iMatchingTypelbl = contractInfoView.findViewById(R.id.iMatchingTypelbl);
        iSpreadTypelbl = contractInfoView.findViewById(R.id.iSpreadTypelbl);
        cValueMethodlbl = contractInfoView.findViewById(R.id.cValueMethodlbl);
        cSLBMEligibilitylbl = contractInfoView.findViewById(R.id.cSLBMEligibilitylbl);
        dIRFfactorlbl = contractInfoView.findViewById(R.id.dIRFfactorlbl);


        view0 = contractInfoView.findViewById(R.id.strip1);
        view1 = contractInfoView.findViewById(R.id.stripView1);
        view2 = contractInfoView.findViewById(R.id.stripView2);
        view3 = contractInfoView.findViewById(R.id.stripView3);
        view4 = contractInfoView.findViewById(R.id.stripView4);
        view5 = contractInfoView.findViewById(R.id.stripView5);
        view6 = contractInfoView.findViewById(R.id.stripView6);
        view7 = contractInfoView.findViewById(R.id.stripView7);
        view8 = contractInfoView.findViewById(R.id.stripView8);
        view9 = contractInfoView.findViewById(R.id.stripView9);
        view10 = contractInfoView.findViewById(R.id.stripView10);
        view11 = contractInfoView.findViewById(R.id.stripView11);
        view12 = contractInfoView.findViewById(R.id.stripView12);
        view13 = contractInfoView.findViewById(R.id.stripView13);
        view14 = contractInfoView.findViewById(R.id.stripView14);
        view15 = contractInfoView.findViewById(R.id.stripView15);
        view16 = contractInfoView.findViewById(R.id.stripView16);
        view17 = contractInfoView.findViewById(R.id.stripView17);
        view18 = contractInfoView.findViewById(R.id.stripView18);
        view19 = contractInfoView.findViewById(R.id.stripView19);
        view20 = contractInfoView.findViewById(R.id.stripView20);
        view21 = contractInfoView.findViewById(R.id.stripView21);
        view22 = contractInfoView.findViewById(R.id.stripView22);
        view23 = contractInfoView.findViewById(R.id.stripView23);
        view24 = contractInfoView.findViewById(R.id.stripView24);
        view25 = contractInfoView.findViewById(R.id.stripView25);
        view26 = contractInfoView.findViewById(R.id.stripView26);
        view27 = contractInfoView.findViewById(R.id.stripView27);
        view28 = contractInfoView.findViewById(R.id.stripView28);

        mcxview0 = contractInfoView.findViewById(R.id.mcxstripView1);
        mcxview1 = contractInfoView.findViewById(R.id.mcxstripView2);
        mcxview2 = contractInfoView.findViewById(R.id.mcxstripView3);
        mcxview3 = contractInfoView.findViewById(R.id.mcxstripView4);
        mcxview4 = contractInfoView.findViewById(R.id.mcxstripView5);
        mcxview5 = contractInfoView.findViewById(R.id.mcxstripView6);
        mcxview6 = contractInfoView.findViewById(R.id.mcxstripView7);
        mcxview7 = contractInfoView.findViewById(R.id.mcxstripView8);
        mcxview8 = contractInfoView.findViewById(R.id.mcxstripView9);
        mcxview9 = contractInfoView.findViewById(R.id.mcxstripView10);
        mcxview = contractInfoView.findViewById(R.id.mcxView);

        errorLayout = contractInfoView.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");

        setAppTitle(getClass().toString(), "Contract Information");
        setupAdapter();
    }


    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private void setupAdapter() {
        showProgress();
        sendMDRequest();
    }

    private void sendMDRequest() {
        ContractInfoRequest.sendRequest(symbol, assetType, exchange, getMainActivity(), serviceResponseHandler);
        orderStreamingController.sendStreamingContractInfoRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), symbol);
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


    @Override
    public void handleResponse(Object response) {
        hideProgress();
        JSONResponse jsonResponse = (JSONResponse) response;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && CONTRACTINFO_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                ContractInfoResponse mdResponse = (ContractInfoResponse) jsonResponse.getResponse();
                toggleErrorLayout(false);
                if (mdResponse.getErrorCode().equals("3")) {

                    toggleErrorLayout(true);

                } else {
                    checkNull(mdResponse);
                    if (getExchange(symbol).equalsIgnoreCase("mcx")) {

                        mcxview.setVisibility(View.VISIBLE);
                        if(mdResponse.getcOptionType().equalsIgnoreCase("XX"))
                        {
                            symbolNameTxt.setText(mdResponse.getcSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlLastTradingDate(), "yyMMM", "bse").toUpperCase()+ "-" + mdResponse.getcInstrumentName());
                        }
                        else {
                            symbolNameTxt.setText(mdResponse.getcSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlLastTradingDate(), "yyMMM", "bse").toUpperCase()+mdResponse.getdStrikePrice()+mdResponse.getcOptionType() + "-" + mdResponse.getcInstrumentName());
                        }


                        description.setText("Security name");
                        description.setText(mdResponse.getcSecurityDesc());

                        InstrumentType.setText(mdResponse.getiInstrumentType());
                        if (((Integer.valueOf(symbol) >= 502000000) && (Integer.valueOf(symbol) <= 502999999)) || ((Integer.valueOf(symbol) >= 1302000000) && (Integer.valueOf(symbol) <= 1302999999))) {
                            dPriceTick.setText(String.format("%.4f", Double.parseDouble(mdResponse.getdPriceTick())));
                        } else {
                            dPriceTick.setText(String.format("%.2f", Double.parseDouble(mdResponse.getdPriceTick())));

                        }

                        cSerieslbl.setText("Market Lot");
                        cSeries.setText(mdResponse.getlRegularLot());

                        dIssueCapitallbl.setText("Margin");
                        dIssueCapital.setText("0.00");

                        iPermittedToTradelbl.setText("Quantity Unit");
                        iPermittedToTrade.setText(mdResponse.getcDeliveryUnit());

                        cCreditRatinglbl.setText("U/L Asset");
                        cCreditRating.setText(mdResponse.getlToken());

                        iNormal_SecurityStatuslbl.setText("Delivery Unit");
                        iNormal_SecurityStatus.setText(mdResponse.getcDeliveryUnit());

                        iNormal_MarketAllowedlbl.setText("Range");
                        iNormal_MarketAllowed.setText(String.format("%.2f", Double.parseDouble(mdResponse.getdLowPriceRange())) + " - " + String.format("%.2f", Double.parseDouble(mdResponse.getdHighPriceRange())));

                        iOddLot_SecurityStatuslbl.setText("Price Quotation");
                        iOddLot_SecurityStatus.setText(mdResponse.getlPriceQuoteQty() + " " + mdResponse.getcPriceQuoteUnit());

                        OddLot_MarketAllowedlbl.setText("Delivery Quantity");
                        OddLot_MarketAllowed.setText(mdResponse.getdDeliveryUnitFactor());

                        iSpot_SecurityStatuslbl.setText("Maturity");
                        if (!mdResponse.getlInstrumentEndDate().equalsIgnoreCase("0")) {
                            iSpot_SecurityStatus.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlInstrumentEndDate(), "dd MMM yyyy", "bse"));
                        }

                        iSpot_MarketAllowedlbl.setText("Exp Date");
                        if (!mdResponse.getlLastTradingDate().equalsIgnoreCase("0")) {
                            //long time = Long.valueOf(mdResponse.getlLastTradingDate()) - 19800;
                            iSpot_MarketAllowed.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlLastTradingDate(), "dd MMM yyyy hh:mm:ss", "bse"));
                        }

                        iAuction_SecurityStatuslbl.setText("Max order size");
                        iAuction_SecurityStatus.setText(mdResponse.getlMaxSingleTransQty());

                        iAuction_MarketAllowedlbl.setText("Contract Start Date");
                        if (!mdResponse.getlContractStartDt().equalsIgnoreCase("0"))
                            iAuction_MarketAllowed.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlContractStartDt(), "dd MMM yyyy", "bse"));

                        lRegularLotlbl.setText("Contract End Date");
                        if (!mdResponse.getlInstrumentEndDate().equalsIgnoreCase("0"))
                            lRegularLot.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlInstrumentEndDate(), "dd MMM yyyy", "bse"));

                        dIssueRatelbl.setText("Tender Start Dt");
                        if (!mdResponse.getlTenderPeriodStartDt().equalsIgnoreCase("0"))
                            dIssueRate.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlTenderPeriodStartDt(), "dd MMM yyyy", "bse"));

                        lIssueStartDtlbl.setText("Tender End Dt");
                        if (!mdResponse.getlTenderPeriodEndDt().equalsIgnoreCase("0"))
                            lIssueStartDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlTenderPeriodEndDt(), "dd MMM yyyy", "bse"));

                        lIssuePDtlbl.setText("Del Start Date");
                        if (!mdResponse.getlDeliveryStartDt().equalsIgnoreCase("0"))
                            lIssuePDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlDeliveryStartDt(), "dd MMM yyyy", "bse"));


                        lIssueMaturityDtlbl.setText("Del End Date");
                        if (!mdResponse.getlDeliveryEndDt().equalsIgnoreCase("0"))
                            lIssueMaturityDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlDeliveryEndDt(), "dd MMM yyyy", "bse"));


                        view11.setVisibility(View.GONE);
                        view12.setVisibility(View.GONE);
                        view13.setVisibility(View.GONE);
                        view14.setVisibility(View.GONE);
                        view15.setVisibility(View.GONE);
                        view16.setVisibility(View.GONE);
                        view17.setVisibility(View.GONE);
                        view18.setVisibility(View.GONE);
                        view19.setVisibility(View.GONE);
                        view20.setVisibility(View.GONE);
                        view21.setVisibility(View.GONE);
                        view22.setVisibility(View.GONE);
                        view23.setVisibility(View.GONE);
                        view24.setVisibility(View.GONE);
                        view25.setVisibility(View.GONE);
                        view26.setVisibility(View.GONE);
                        view27.setVisibility(View.GONE);
                        view28.setVisibility(View.GONE);

                        mcxview0.setVisibility(View.GONE);
                        mcxview1.setVisibility(View.GONE);
                        mcxview2.setVisibility(View.GONE);
                        mcxview3.setVisibility(View.GONE);
                        mcxview4.setVisibility(View.GONE);
                        mcxview5.setVisibility(View.GONE);
                        mcxview6.setVisibility(View.GONE);
                        mcxview7.setVisibility(View.GONE);
                        mcxview8.setVisibility(View.GONE);
                        mcxview9.setVisibility(View.GONE);

                        lFreezeQtylbl.setVisibility(View.GONE);
                        lFreezeQty.setVisibility(View.GONE);
                    } else {
                        mcxview.setVisibility(View.GONE);
                        symbolNameTxt.setText(mdResponse.getcSymbol());
                        description.setText(mdResponse.getcSecurityDesc());
                        InstrumentType.setText(mdResponse.getiInstrumentType());
                        if (((Integer.valueOf(symbol) >= 502000000) && (Integer.valueOf(symbol) <= 502999999)) || ((Integer.valueOf(symbol) >= 1302000000) && (Integer.valueOf(symbol) <= 1302999999))) {
                            dPriceTick.setText(String.format("%.4f", Double.parseDouble(mdResponse.getdPriceTick())));
                        } else {
                            dPriceTick.setText(String.format("%.2f", Double.parseDouble(mdResponse.getdPriceTick())));

                        }

                        cSeries.setText(mdResponse.getcSeries());

                        dIssueCapital.setText(mdResponse.getdIssueCapital());
                        iPermittedToTrade.setText(mdResponse.getiPermittedToTrade());
                        if (assetType.equalsIgnoreCase("currency")) {
                            cCreditRating.setText(String.format("%.4f", Double.parseDouble(mdResponse.getdLowPriceRange())) + " - " + String.format("%.4f", Double.parseDouble(mdResponse.getdHighPriceRange())));
                        } else if (assetType.equalsIgnoreCase("fno")) {
                            cCreditRating.setText(String.format("%.2f", Double.parseDouble(mdResponse.getdLowPriceRange())) + " - " + String.format("%.2f", Double.parseDouble(mdResponse.getdHighPriceRange())));
                        } else {
                            cCreditRating.setText(mdResponse.getcCreditRating());
                        }

                        iNormal_SecurityStatus.setText(mdResponse.getiNormal_SecurityStatus());
                        iNormal_MarketAllowed.setText(mdResponse.getiNormal_MarketAllowed());
                        iOddLot_SecurityStatus.setText(mdResponse.getiOddLot_SecurityStatus());
                        OddLot_MarketAllowed.setText(mdResponse.getiOddLot_MarketAllowed());
                        iSpot_SecurityStatus.setText(mdResponse.getiSpot_SecurityStatus());
                        iSpot_MarketAllowed.setText(mdResponse.getiSpot_MarketAllowed());
                        iAuction_SecurityStatus.setText(mdResponse.getiAuction_SecurityStatus());
                        iAuction_MarketAllowed.setText(mdResponse.getiAuction_MarketAllowed());
                        lRegularLot.setText(mdResponse.getlRegularLot());


                        dIssueRate.setText(mdResponse.getdIssueRate());
                        if (!mdResponse.getlIssueStartDt().equalsIgnoreCase("0"))
                            lIssueStartDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlIssueStartDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlIssuePDt().equalsIgnoreCase("0"))
                            lIssuePDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlIssuePDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlIssueMaturityDt().equalsIgnoreCase("0"))
                            lIssueMaturityDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlIssueMaturityDt(), "dd MMM yyyy", "nse"));
                        lFreezeQty.setText(mdResponse.getlFreezeQty());
                        if (!mdResponse.getlListingDt().equalsIgnoreCase("0"))
                            lListingDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlListingDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlExpulsionDt().equalsIgnoreCase("0"))
                            lExpulsionDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlExpulsionDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlReadmissionDt().equalsIgnoreCase("0"))
                            lReadmissionDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlReadmissionDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlExDt().equalsIgnoreCase("0"))
                            lExDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlExDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlRecordDt().equalsIgnoreCase("0"))
                            lRecordDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlRecordDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlNoDeliveryStartDt().equalsIgnoreCase("0"))
                            lNoDeliveryStartDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlNoDeliveryStartDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlNoDeliveryEndDt().equalsIgnoreCase("0"))
                            lNoDeliveryEndDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlNoDeliveryEndDt(), "dd MMM yyyy", "nse"));
                        lIndexParticipant.setText(mdResponse.getlIndexParticipant());
                        iAONAllowed.setText(mdResponse.getiAONAllowed());
                        iMFAllowed.setText(mdResponse.getiMFAllowed());
                        dWarmingPercent.setText(mdResponse.getdWarmingPercent());
                        if (!mdResponse.getlBookClosureStartDt().equalsIgnoreCase("0"))
                            lBookClosureStartDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlBookClosureStartDt(), "dd MMM yyyy", "nse"));
                        if (!mdResponse.getlBookClosureEndDt().equalsIgnoreCase("0"))
                            lBookClosureEndDt.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlBookClosureEndDt(), "dd MMM yyyy", "nse"));
                        iDividend.setText(mdResponse.getiDividend());
                        iRights.setText(mdResponse.getiRights());
                        iBonus.setText(mdResponse.getiBonus());
                        iInterest.setText(mdResponse.getiInterest());
                        iAGM.setText(mdResponse.getiAGM());
                        iEGM.setText(mdResponse.getiEGM());
                        cRemarks.setText(mdResponse.getcRemarks());
                        cDeleteFlag.setText(mdResponse.getcDeleteFlag());
                        if (!mdResponse.getlLastUpdateTime().equalsIgnoreCase("0"))
                            lLastUpdateTime.setText(DateTimeFormatter.getDateFromTimeStamp(mdResponse.getlLastUpdateTime(), "dd MMM yyyy", "nse"));
                        dFacevalue.setText(mdResponse.getdFacevalue());
                        cISINNumber.setText(mdResponse.getcISINNumber());
                        iCallAuction_MarketAllowed.setText(mdResponse.getiCallAuction_MarketAllowed());
                        iCallAuction_SecurityStatus.setText(mdResponse.getiCallAuction_SecurityStatus());
                        iCallAuction2_SecurityStatus.setText(mdResponse.getiCallAuction2_SecurityStatus());
                        iCallAuction2_MarketAllowed.setText(mdResponse.getiCallAuction2_MarketAllowed());
                        lSpread.setText(mdResponse.getlSpread());
                        lMinQty.setText(mdResponse.getlMinQty());
                        iSSEC.setText(mdResponse.getiSSEC());
                        iSurvInd.setText(mdResponse.getiSurvInd());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkNull(ContractInfoResponse mdResponse) {

        if (mdResponse.getcCreditRating().equalsIgnoreCase("")) {
            mdResponse.setcCreditRating("0");
        }
        if (mdResponse.getcDeleteFlag().equalsIgnoreCase("")) {
            mdResponse.setcDeleteFlag("0");
        }
        if (mdResponse.getcISINNumber().equalsIgnoreCase("")) {
            mdResponse.setcISINNumber("0");
        }
        if (mdResponse.getcRemarks().equalsIgnoreCase("")) {
            mdResponse.setcRemarks("0");
        }
        if (mdResponse.getcSecurityDesc().equalsIgnoreCase("")) {
            mdResponse.setcSecurityDesc("0");
        }
        if (mdResponse.getcSeries().equalsIgnoreCase("")) {
            mdResponse.setcSeries("0");
        }
        if (mdResponse.getcSymbol().equalsIgnoreCase("")) {
            mdResponse.setcSymbol("0");
        }
        if (mdResponse.getdFacevalue().equalsIgnoreCase("")) {
            mdResponse.setdFacevalue("0");
        }
        if (mdResponse.getdIssueCapital().equalsIgnoreCase("")) {
            mdResponse.setdIssueCapital("0");
        }
        if (mdResponse.getdIssueRate().equalsIgnoreCase("")) {
            mdResponse.setdIssueRate("0");
        }
        if (mdResponse.getdPriceTick().equalsIgnoreCase("")) {
            mdResponse.setdPriceTick("0");
        }
        if (mdResponse.getdWarmingPercent().equalsIgnoreCase("")) {
            mdResponse.setdWarmingPercent("0");
        }
        if (mdResponse.getiAGM().equalsIgnoreCase("")) {
            mdResponse.setiAGM("0");
        }
        if (mdResponse.getiAONAllowed().equalsIgnoreCase("")) {
            mdResponse.setiAONAllowed("0");
        }
        if (mdResponse.getiAuction_MarketAllowed().equalsIgnoreCase("")) {
            mdResponse.setiAuction_MarketAllowed("0");
        }
        if (mdResponse.getiAuction_SecurityStatus().equalsIgnoreCase("")) {
            mdResponse.setiAuction_SecurityStatus("0");
        }
        if (mdResponse.getiBonus().equalsIgnoreCase("")) {
            mdResponse.setiBonus("0");
        }
        if (mdResponse.getiCallAuction2_MarketAllowed().equalsIgnoreCase("")) {
            mdResponse.setiCallAuction2_MarketAllowed("0");
        }
        if (mdResponse.getiCallAuction2_SecurityStatus().equalsIgnoreCase("")) {
            mdResponse.setiCallAuction2_SecurityStatus("0");
        }
        if (mdResponse.getiCallAuction_MarketAllowed().equalsIgnoreCase("")) {
            mdResponse.setiCallAuction_MarketAllowed("0");
        }
        if (mdResponse.getiCallAuction_SecurityStatus().equalsIgnoreCase("")) {
            mdResponse.setiCallAuction_SecurityStatus("0");
        }
        if (mdResponse.getiDividend().equalsIgnoreCase("")) {
            mdResponse.setiDividend("0");
        }
        if (mdResponse.getiEGM().equalsIgnoreCase("")) {
            mdResponse.setiEGM("0");
        }
        if (mdResponse.getiInstrumentType().equalsIgnoreCase("")) {
            mdResponse.setiInstrumentType("0");
        }
        if (mdResponse.getiInterest().equalsIgnoreCase("")) {
            mdResponse.setiInterest("0");
        }
        if (mdResponse.getiMFAllowed().equalsIgnoreCase("")) {
            mdResponse.setiMFAllowed("0");
        }
        if (mdResponse.getiNormal_MarketAllowed().equalsIgnoreCase("")) {
            mdResponse.setiNormal_MarketAllowed("0");
        }
        if (mdResponse.getiNormal_SecurityStatus().equalsIgnoreCase("")) {
            mdResponse.setiNormal_SecurityStatus("0");
        }
        if (mdResponse.getiOddLot_MarketAllowed().equalsIgnoreCase("")) {
            mdResponse.setiOddLot_MarketAllowed("0");
        }
        if (mdResponse.getiOddLot_SecurityStatus().equalsIgnoreCase("")) {
            mdResponse.setiOddLot_SecurityStatus("0");
        }
        if (mdResponse.getiPermittedToTrade().equalsIgnoreCase("")) {
            mdResponse.setiPermittedToTrade("0");
        }
        if (mdResponse.getiRights().equalsIgnoreCase("")) {
            mdResponse.setiRights("0");
        }
        if (mdResponse.getiSSEC().equalsIgnoreCase("")) {
            mdResponse.setiSSEC("0");
        }
        if (mdResponse.getiSpot_MarketAllowed().equalsIgnoreCase("")) {
            mdResponse.setiSpot_MarketAllowed("0");
        }
        if (mdResponse.getiSpot_SecurityStatus().equalsIgnoreCase("")) {
            mdResponse.setiSpot_SecurityStatus("0");
        }
        if (mdResponse.getiSurvInd().equalsIgnoreCase("")) {
            mdResponse.setiSurvInd("0");
        }
        if (mdResponse.getlBookClosureEndDt().equalsIgnoreCase("")) {
            mdResponse.setlBookClosureEndDt("0");
        }
        if (mdResponse.getlBookClosureStartDt().equalsIgnoreCase("")) {
            mdResponse.setlBookClosureStartDt("0");
        }
        if (mdResponse.getlExDt().equalsIgnoreCase("")) {
            mdResponse.setlExDt("0");
        }
        if (mdResponse.getlBookClosureEndDt().equalsIgnoreCase("")) {
            mdResponse.setlBookClosureEndDt("0");
        }
        if (mdResponse.getlBookClosureStartDt().equalsIgnoreCase("")) {
            mdResponse.setlBookClosureStartDt("0");
        }
        if (mdResponse.getlExDt().equalsIgnoreCase("")) {
            mdResponse.setlExDt("0");
        }
        if (mdResponse.getlExpulsionDt().equalsIgnoreCase("")) {
            mdResponse.setlExpulsionDt("0");
        }
        if (mdResponse.getlFreezeQty().equalsIgnoreCase("")) {
            mdResponse.setlFreezeQty("0");
        }
        if (mdResponse.getlIndexParticipant().equalsIgnoreCase("")) {
            mdResponse.setlIndexParticipant("0");
        }
        if (mdResponse.getlIssueMaturityDt().equalsIgnoreCase("")) {
            mdResponse.setlIssueMaturityDt("0");
        }
        if (mdResponse.getlIssuePDt().equalsIgnoreCase("")) {
            mdResponse.setlIssuePDt("0");
        }
        if (mdResponse.getlIssueStartDt().equalsIgnoreCase("")) {
            mdResponse.setlIssueStartDt("0");
        }
        if (mdResponse.getlLastUpdateTime().equalsIgnoreCase("")) {
            mdResponse.setlLastUpdateTime("0");
        }
        if (mdResponse.getlListingDt().equalsIgnoreCase("")) {
            mdResponse.setlListingDt("0");
        }
        if (mdResponse.getlMinQty().equalsIgnoreCase("")) {
            mdResponse.setlMinQty("0");
        }
        if (mdResponse.getlNoDeliveryEndDt().equalsIgnoreCase("")) {
            mdResponse.setlNoDeliveryEndDt("0");
        }
        if (mdResponse.getlNoDeliveryStartDt().equalsIgnoreCase("")) {
            mdResponse.setlNoDeliveryStartDt("0");
        }
        if (mdResponse.getlReadmissionDt().equalsIgnoreCase("")) {
            mdResponse.setlReadmissionDt("0");
        }
        if (mdResponse.getlRecordDt().equalsIgnoreCase("")) {
            mdResponse.setlRecordDt("0");
        }
        if (mdResponse.getlRegularLot().equalsIgnoreCase("")) {
            mdResponse.setlRegularLot("0");
        }
        if (mdResponse.getlSpread().equalsIgnoreCase("")) {
            mdResponse.setlSpread("0");
        }
        if (mdResponse.getlToken().equalsIgnoreCase("")) {
            mdResponse.setlToken("0");
        }
    }


    public void onEventMainThread(StreamerSymbolVarMarginResponse symbolVarMarginResponse) {
        try {
            if (market_id == 2 || market_id == 3 || market_id == 5 || market_id == 6) {
                VARMarginlbl.setText("Span Margin Buy");
                VARPercentagelbl.setText("Span Margin Sell");
                ELMMarginlbl.setText("Exposure Margin");
                ELMPercentagelbl.setText("Exposure Margin Percentage");


                VARMargin.setText(symbolVarMarginResponse.getSpanMarginBuy());
                VARPercentage.setText(symbolVarMarginResponse.getSpanMarginSell());
                ELMMargin.setText(symbolVarMarginResponse.getExposMargin());
                ELMPercentage.setText(symbolVarMarginResponse.getExposeMarginPer());
            } else {
                VARMarginlbl.setText("VAR Margin");
                VARPercentagelbl.setText("VAR Percentage");
                ELMMarginlbl.setText("ELM Margin");
                ELMPercentagelbl.setText("ELM Percentage");

                VARMargin.setText(symbolVarMarginResponse.getVARMargin());
                VARPercentage.setText(symbolVarMarginResponse.getVARPercentage());
                ELMMargin.setText(symbolVarMarginResponse.getELMMargin());
                ELMPercentage.setText(symbolVarMarginResponse.getELMPercentage());
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_CONTRACTINFO_SCREEN;
        EventBus.getDefault().register(this);


    }

    @Override
    public void onPause() {
        super.onPause();
        super.onFragmentPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }


    //TODO: Setting open interest on market depth also

}

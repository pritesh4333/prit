package com.acumengroup.greekmain.core.model.marketssinglescrip;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 25-Jul-17.
 */

public class ContractInfoResponse implements GreekRequestModel, GreekResponseModel {
    private String cSymbol;
    private String cSeries;
    private String iInstrumentType;
    private String dIssueCapital;
    private String iPermittedToTrade;
    private String cCreditRating;
    private String iNormal_SecurityStatus;
    private String iNormal_MarketAllowed;
    private String iOddLot_SecurityStatus;
    private String iOddLot_MarketAllowed;
    private String iSpot_SecurityStatus;
    private String iSpot_MarketAllowed;
    private String iAuction_SecurityStatus;
    private String iAuction_MarketAllowed;
    private String lRegularLot;
    private String dPriceTick;
    private String cSecurityDesc;
    private String dIssueRate;
    private String lIssueStartDt;
    private String lIssuePDt;
    private String lIssueMaturityDt;
    private String lFreezeQty;
    private String lListingDt;
    private String lExpulsionDt;
    private String lReadmissionDt;
    private String lExDt;
    private String lRecordDt;
    private String lNoDeliveryStartDt;
    private String lNoDeliveryEndDt;
    private String lIndexParticipant;
    private String iAONAllowed;
    private String iMFAllowed;
    private String dWarmingPercent;
    private String lBookClosureStartDt;
    private String lBookClosureEndDt;
    private String iDividend;
    private String iRights;
    private String iBonus;
    private String iInterest;
    private String iAGM;
    private String iEGM;
    private String cRemarks;
    private String cDeleteFlag;
    private String lLastUpdateTime;
    private String dFacevalue;
    private String cISINNumber;
    private String iCallAuction_MarketAllowed;
    private String iCallAuction_SecurityStatus;
    private String iCallAuction2_SecurityStatus;
    private String iCallAuction2_MarketAllowed;
    private String lSpread;
    private String lMinQty;
    private String iSSEC;
    private String iSurvInd;
    private String lToken;
    private String dLowPriceRange;
    private String dHighPriceRange;
    private String ErrorCode;


    private String cBandhaniRange;
    private String lContractStartDt;
    private String lLastTradingDate;
    private String lDeliveryStartDt;
    private String lDeliveryEndDt;
    private String iTrade2Trade;
    private String iIndexFlag;
    private String iDefaultIndex;
    private String iIndexInstrument;
    private String iFeedFlag;
    private String cInstrumentstatusFlag;
    private String iMinimumLot;
    private String lTenderPeriodStartDt;
    private String lTenderPeriodEndDt;
    private String cCommodityGroup;
    private String cUnderlyingCommodity;
    private String lUnderlyingIdentifier;
    private String cInstrumentName;
    private String lOriginalExpiryDt;
    private String dStrikePrice;
    private String cOptionType;
    private String iCALevel;
    private String iSegmentId;
    private String cPriceQuoteUnit;
    private String lPriceQuoteQty;
    private String iDPRTerms;
    private String dUpperDPR;
    private String dLowerDPR;
    private String iTenderPeriodIndicator;
    private String iSettleMentMethod;
    private String iInitialMarginTerms;
    private String dBuyMarginRate;
    private String lBasePrice;
    private String lMaxSingleTransQty;
    private String dMaxSingleTransValue;
    private String iInstrumentClass;
    private String lNearMonthInstIdentifier;
    private String lFarMonthInstIdentifier;
    private String cTradingUnit;
    private String dTradingUnitFactor;
    private String cDeliveryUnit;
    private String dDeliveryUnitFactor;
    private String dPriceNumerator;
    private String cSpecification;
    private String dPriceDenominator;
    private String dGeneralNumerator;
    private String dGeneralDenominator;
    private String dLotNumerator;
    private String dLotDenominator;
    private String dDecimalLocator;
    private String dSellMarginRate;
    private String iTermsOfSpecialMargin;
    private String dBuySpecialMarginRate;
    private String dSellSpecialMarginRate;
    private String iMarginSpreadBenefitFlag;
    private String lInstrumentEndDate;
    private String cTradingCurrency;
    private String cContractMonth;
    private String iPreOpenAllowed;
    private String iGroupId;
    private String iMatchingType;
    private String iSpreadType;
    private String cValueMethod;
    private String cSLBMEligibility;
    private String dIRFfactor;


    public String getcBandhaniRange() {
        return cBandhaniRange;
    }

    public void setcBandhaniRange(String cBandhaniRange) {
        this.cBandhaniRange = cBandhaniRange;
    }

    public String getlContractStartDt() {
        return lContractStartDt;
    }

    public void setlContractStartDt(String lContractStartDt) {
        this.lContractStartDt = lContractStartDt;
    }

    public String getlLastTradingDate() {
        return lLastTradingDate;
    }

    public void setlLastTradingDate(String lLastTradingDate) {
        this.lLastTradingDate = lLastTradingDate;
    }

    public String getlDeliveryStartDt() {
        return lDeliveryStartDt;
    }

    public void setlDeliveryStartDt(String lDeliveryStartDt) {
        this.lDeliveryStartDt = lDeliveryStartDt;
    }

    public String getlDeliveryEndDt() {
        return lDeliveryEndDt;
    }

    public void setlDeliveryEndDt(String lDeliveryEndDt) {
        this.lDeliveryEndDt = lDeliveryEndDt;
    }

    public String getiTrade2Trade() {
        return iTrade2Trade;
    }

    public void setiTrade2Trade(String iTrade2Trade) {
        this.iTrade2Trade = iTrade2Trade;
    }

    public String getiIndexFlag() {
        return iIndexFlag;
    }

    public void setiIndexFlag(String iIndexFlag) {
        this.iIndexFlag = iIndexFlag;
    }

    public String getiDefaultIndex() {
        return iDefaultIndex;
    }

    public void setiDefaultIndex(String iDefaultIndex) {
        this.iDefaultIndex = iDefaultIndex;
    }

    public String getiIndexInstrument() {
        return iIndexInstrument;
    }

    public void setiIndexInstrument(String iIndexInstrument) {
        this.iIndexInstrument = iIndexInstrument;
    }

    public String getiFeedFlag() {
        return iFeedFlag;
    }

    public void setiFeedFlag(String iFeedFlag) {
        this.iFeedFlag = iFeedFlag;
    }

    public String getcInstrumentstatusFlag() {
        return cInstrumentstatusFlag;
    }

    public void setcInstrumentstatusFlag(String cInstrumentstatusFlag) {
        this.cInstrumentstatusFlag = cInstrumentstatusFlag;
    }

    public String getiMinimumLot() {
        return iMinimumLot;
    }

    public void setiMinimumLot(String iMinimumLot) {
        this.iMinimumLot = iMinimumLot;
    }

    public String getlTenderPeriodStartDt() {
        return lTenderPeriodStartDt;
    }

    public void setlTenderPeriodStartDt(String lTenderPeriodStartDt) {
        this.lTenderPeriodStartDt = lTenderPeriodStartDt;
    }

    public String getlTenderPeriodEndDt() {
        return lTenderPeriodEndDt;
    }

    public void setlTenderPeriodEndDt(String lTenderPeriodEndDt) {
        this.lTenderPeriodEndDt = lTenderPeriodEndDt;
    }

    public String getcCommodityGroup() {
        return cCommodityGroup;
    }

    public void setcCommodityGroup(String cCommodityGroup) {
        this.cCommodityGroup = cCommodityGroup;
    }

    public String getcUnderlyingCommodity() {
        return cUnderlyingCommodity;
    }

    public void setcUnderlyingCommodity(String cUnderlyingCommodity) {
        this.cUnderlyingCommodity = cUnderlyingCommodity;
    }

    public String getlUnderlyingIdentifier() {
        return lUnderlyingIdentifier;
    }

    public void setlUnderlyingIdentifier(String lUnderlyingIdentifier) {
        this.lUnderlyingIdentifier = lUnderlyingIdentifier;
    }

    public String getcInstrumentName() {
        return cInstrumentName;
    }

    public void setcInstrumentName(String cInstrumentName) {
        this.cInstrumentName = cInstrumentName;
    }

    public String getlOriginalExpiryDt() {
        return lOriginalExpiryDt;
    }

    public void setlOriginalExpiryDt(String lOriginalExpiryDt) {
        this.lOriginalExpiryDt = lOriginalExpiryDt;
    }

    public String getdStrikePrice() {
        return dStrikePrice;
    }

    public void setdStrikePrice(String dStrikePrice) {
        this.dStrikePrice = dStrikePrice;
    }

    public String getcOptionType() {
        return cOptionType;
    }

    public void setcOptionType(String cOptionType) {
        this.cOptionType = cOptionType;
    }

    public String getiCALevel() {
        return iCALevel;
    }

    public void setiCALevel(String iCALevel) {
        this.iCALevel = iCALevel;
    }

    public String getiSegmentId() {
        return iSegmentId;
    }

    public void setiSegmentId(String iSegmentId) {
        this.iSegmentId = iSegmentId;
    }

    public String getcPriceQuoteUnit() {
        return cPriceQuoteUnit;
    }

    public void setcPriceQuoteUnit(String cPriceQuoteUnit) {
        this.cPriceQuoteUnit = cPriceQuoteUnit;
    }

    public String getlPriceQuoteQty() {
        return lPriceQuoteQty;
    }

    public void setlPriceQuoteQty(String lPriceQuoteQty) {
        this.lPriceQuoteQty = lPriceQuoteQty;
    }

    public String getiDPRTerms() {
        return iDPRTerms;
    }

    public void setiDPRTerms(String iDPRTerms) {
        this.iDPRTerms = iDPRTerms;
    }

    public String getdUpperDPR() {
        return dUpperDPR;
    }

    public void setdUpperDPR(String dUpperDPR) {
        this.dUpperDPR = dUpperDPR;
    }

    public String getdLowerDPR() {
        return dLowerDPR;
    }

    public void setdLowerDPR(String dLowerDPR) {
        this.dLowerDPR = dLowerDPR;
    }

    public String getiTenderPeriodIndicator() {
        return iTenderPeriodIndicator;
    }

    public void setiTenderPeriodIndicator(String iTenderPeriodIndicator) {
        this.iTenderPeriodIndicator = iTenderPeriodIndicator;
    }

    public String getiSettleMentMethod() {
        return iSettleMentMethod;
    }

    public void setiSettleMentMethod(String iSettleMentMethod) {
        this.iSettleMentMethod = iSettleMentMethod;
    }

    public String getiInitialMarginTerms() {
        return iInitialMarginTerms;
    }

    public void setiInitialMarginTerms(String iInitialMarginTerms) {
        this.iInitialMarginTerms = iInitialMarginTerms;
    }

    public String getdBuyMarginRate() {
        return dBuyMarginRate;
    }

    public void setdBuyMarginRate(String dBuyMarginRate) {
        this.dBuyMarginRate = dBuyMarginRate;
    }

    public String getlBasePrice() {
        return lBasePrice;
    }

    public void setlBasePrice(String lBasePrice) {
        this.lBasePrice = lBasePrice;
    }

    public String getlMaxSingleTransQty() {
        return lMaxSingleTransQty;
    }

    public void setlMaxSingleTransQty(String lMaxSingleTransQty) {
        this.lMaxSingleTransQty = lMaxSingleTransQty;
    }

    public String getdMaxSingleTransValue() {
        return dMaxSingleTransValue;
    }

    public void setdMaxSingleTransValue(String dMaxSingleTransValue) {
        this.dMaxSingleTransValue = dMaxSingleTransValue;
    }

    public String getiInstrumentClass() {
        return iInstrumentClass;
    }

    public void setiInstrumentClass(String iInstrumentClass) {
        this.iInstrumentClass = iInstrumentClass;
    }

    public String getlNearMonthInstIdentifier() {
        return lNearMonthInstIdentifier;
    }

    public void setlNearMonthInstIdentifier(String lNearMonthInstIdentifier) {
        this.lNearMonthInstIdentifier = lNearMonthInstIdentifier;
    }

    public String getlFarMonthInstIdentifier() {
        return lFarMonthInstIdentifier;
    }

    public void setlFarMonthInstIdentifier(String lFarMonthInstIdentifier) {
        this.lFarMonthInstIdentifier = lFarMonthInstIdentifier;
    }

    public String getcTradingUnit() {
        return cTradingUnit;
    }

    public void setcTradingUnit(String cTradingUnit) {
        this.cTradingUnit = cTradingUnit;
    }

    public String getdTradingUnitFactor() {
        return dTradingUnitFactor;
    }

    public void setdTradingUnitFactor(String dTradingUnitFactor) {
        this.dTradingUnitFactor = dTradingUnitFactor;
    }

    public String getcDeliveryUnit() {
        return cDeliveryUnit;
    }

    public void setcDeliveryUnit(String cDeliveryUnit) {
        this.cDeliveryUnit = cDeliveryUnit;
    }

    public String getdDeliveryUnitFactor() {
        return dDeliveryUnitFactor;
    }

    public void setdDeliveryUnitFactor(String dDeliveryUnitFactor) {
        this.dDeliveryUnitFactor = dDeliveryUnitFactor;
    }

    public String getdPriceNumerator() {
        return dPriceNumerator;
    }

    public void setdPriceNumerator(String dPriceNumerator) {
        this.dPriceNumerator = dPriceNumerator;
    }

    public String getcSpecification() {
        return cSpecification;
    }

    public void setcSpecification(String cSpecification) {
        this.cSpecification = cSpecification;
    }

    public String getdPriceDenominator() {
        return dPriceDenominator;
    }

    public void setdPriceDenominator(String dPriceDenominator) {
        this.dPriceDenominator = dPriceDenominator;
    }

    public String getdGeneralNumerator() {
        return dGeneralNumerator;
    }

    public void setdGeneralNumerator(String dGeneralNumerator) {
        this.dGeneralNumerator = dGeneralNumerator;
    }

    public String getdGeneralDenominator() {
        return dGeneralDenominator;
    }

    public void setdGeneralDenominator(String dGeneralDenominator) {
        this.dGeneralDenominator = dGeneralDenominator;
    }

    public String getdLotNumerator() {
        return dLotNumerator;
    }

    public void setdLotNumerator(String dLotNumerator) {
        this.dLotNumerator = dLotNumerator;
    }

    public String getdLotDenominator() {
        return dLotDenominator;
    }

    public void setdLotDenominator(String dLotDenominator) {
        this.dLotDenominator = dLotDenominator;
    }

    public String getdDecimalLocator() {
        return dDecimalLocator;
    }

    public void setdDecimalLocator(String dDecimalLocator) {
        this.dDecimalLocator = dDecimalLocator;
    }

    public String getdSellMarginRate() {
        return dSellMarginRate;
    }

    public void setdSellMarginRate(String dSellMarginRate) {
        this.dSellMarginRate = dSellMarginRate;
    }

    public String getiTermsOfSpecialMargin() {
        return iTermsOfSpecialMargin;
    }

    public void setiTermsOfSpecialMargin(String iTermsOfSpecialMargin) {
        this.iTermsOfSpecialMargin = iTermsOfSpecialMargin;
    }

    public String getdBuySpecialMarginRate() {
        return dBuySpecialMarginRate;
    }

    public void setdBuySpecialMarginRate(String dBuySpecialMarginRate) {
        this.dBuySpecialMarginRate = dBuySpecialMarginRate;
    }

    public String getdSellSpecialMarginRate() {
        return dSellSpecialMarginRate;
    }

    public void setdSellSpecialMarginRate(String dSellSpecialMarginRate) {
        this.dSellSpecialMarginRate = dSellSpecialMarginRate;
    }

    public String getiMarginSpreadBenefitFlag() {
        return iMarginSpreadBenefitFlag;
    }

    public void setiMarginSpreadBenefitFlag(String iMarginSpreadBenefitFlag) {
        this.iMarginSpreadBenefitFlag = iMarginSpreadBenefitFlag;
    }

    public String getlInstrumentEndDate() {
        return lInstrumentEndDate;
    }

    public void setlInstrumentEndDate(String lInstrumentEndDate) {
        this.lInstrumentEndDate = lInstrumentEndDate;
    }

    public String getcTradingCurrency() {
        return cTradingCurrency;
    }

    public void setcTradingCurrency(String cTradingCurrency) {
        this.cTradingCurrency = cTradingCurrency;
    }

    public String getcContractMonth() {
        return cContractMonth;
    }

    public void setcContractMonth(String cContractMonth) {
        this.cContractMonth = cContractMonth;
    }

    public String getiPreOpenAllowed() {
        return iPreOpenAllowed;
    }

    public void setiPreOpenAllowed(String iPreOpenAllowed) {
        this.iPreOpenAllowed = iPreOpenAllowed;
    }

    public String getiGroupId() {
        return iGroupId;
    }

    public void setiGroupId(String iGroupId) {
        this.iGroupId = iGroupId;
    }

    public String getiMatchingType() {
        return iMatchingType;
    }

    public void setiMatchingType(String iMatchingType) {
        this.iMatchingType = iMatchingType;
    }

    public String getiSpreadType() {
        return iSpreadType;
    }

    public void setiSpreadType(String iSpreadType) {
        this.iSpreadType = iSpreadType;
    }

    public String getcValueMethod() {
        return cValueMethod;
    }

    public void setcValueMethod(String cValueMethod) {
        this.cValueMethod = cValueMethod;
    }

    public String getcSLBMEligibility() {
        return cSLBMEligibility;
    }

    public void setcSLBMEligibility(String cSLBMEligibility) {
        this.cSLBMEligibility = cSLBMEligibility;
    }

    public String getdIRFfactor() {
        return dIRFfactor;
    }

    public void setdIRFfactor(String dIRFfactor) {
        this.dIRFfactor = dIRFfactor;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getdLowPriceRange() {
        return dLowPriceRange;
    }

    public void setdLowPriceRange(String dLowPriceRange) {
        this.dLowPriceRange = dLowPriceRange;
    }

    public String getdHighPriceRange() {
        return dHighPriceRange;
    }

    public void setdHighPriceRange(String dHighPriceRange) {
        this.dHighPriceRange = dHighPriceRange;
    }

    public String getcSymbol() {
        return cSymbol;
    }

    public void setcSymbol(String cSymbol) {
        this.cSymbol = cSymbol;
    }

    public String getcSeries() {
        return cSeries;
    }

    public void setcSeries(String cSeries) {
        this.cSeries = cSeries;
    }

    public String getiInstrumentType() {
        return iInstrumentType;
    }

    public void setiInstrumentType(String iInstrumentType) {
        this.iInstrumentType = iInstrumentType;
    }

    public String getdIssueCapital() {
        return dIssueCapital;
    }

    public void setdIssueCapital(String dIssueCapital) {
        this.dIssueCapital = dIssueCapital;
    }

    public String getiPermittedToTrade() {
        return iPermittedToTrade;
    }

    public void setiPermittedToTrade(String iPermittedToTrade) {
        this.iPermittedToTrade = iPermittedToTrade;
    }

    public String getcCreditRating() {
        return cCreditRating;
    }

    public void setcCreditRating(String cCreditRating) {
        this.cCreditRating = cCreditRating;
    }

    public String getiNormal_SecurityStatus() {
        return iNormal_SecurityStatus;
    }

    public void setiNormal_SecurityStatus(String iNormal_SecurityStatus) {
        this.iNormal_SecurityStatus = iNormal_SecurityStatus;
    }

    public String getiNormal_MarketAllowed() {
        return iNormal_MarketAllowed;
    }

    public void setiNormal_MarketAllowed(String iNormal_MarketAllowed) {
        this.iNormal_MarketAllowed = iNormal_MarketAllowed;
    }

    public String getiOddLot_SecurityStatus() {
        return iOddLot_SecurityStatus;
    }

    public void setiOddLot_SecurityStatus(String iOddLot_SecurityStatus) {
        this.iOddLot_SecurityStatus = iOddLot_SecurityStatus;
    }

    public String getiOddLot_MarketAllowed() {
        return iOddLot_MarketAllowed;
    }

    public void setiOddLot_MarketAllowed(String iOddLot_MarketAllowed) {
        this.iOddLot_MarketAllowed = iOddLot_MarketAllowed;
    }

    public String getiSpot_SecurityStatus() {
        return iSpot_SecurityStatus;
    }

    public void setiSpot_SecurityStatus(String iSpot_SecurityStatus) {
        this.iSpot_SecurityStatus = iSpot_SecurityStatus;
    }

    public String getiSpot_MarketAllowed() {
        return iSpot_MarketAllowed;
    }

    public void setiSpot_MarketAllowed(String iSpot_MarketAllowed) {
        this.iSpot_MarketAllowed = iSpot_MarketAllowed;
    }

    public String getiAuction_SecurityStatus() {
        return iAuction_SecurityStatus;
    }

    public void setiAuction_SecurityStatus(String iAuction_SecurityStatus) {
        this.iAuction_SecurityStatus = iAuction_SecurityStatus;
    }

    public String getiAuction_MarketAllowed() {
        return iAuction_MarketAllowed;
    }

    public void setiAuction_MarketAllowed(String iAuction_MarketAllowed) {
        this.iAuction_MarketAllowed = iAuction_MarketAllowed;
    }

    public String getlRegularLot() {
        return lRegularLot;
    }

    public void setlRegularLot(String lRegularLot) {
        this.lRegularLot = lRegularLot;
    }

    public String getdPriceTick() {
        return dPriceTick;
    }

    public void setdPriceTick(String dPriceTick) {
        this.dPriceTick = dPriceTick;
    }

    public String getcSecurityDesc() {
        return cSecurityDesc;
    }

    public void setcSecurityDesc(String cSecurityDesc) {
        this.cSecurityDesc = cSecurityDesc;
    }

    public String getdIssueRate() {
        return dIssueRate;
    }

    public void setdIssueRate(String dIssueRate) {
        this.dIssueRate = dIssueRate;
    }

    public String getlIssueStartDt() {
        return lIssueStartDt;
    }

    public void setlIssueStartDt(String lIssueStartDt) {
        this.lIssueStartDt = lIssueStartDt;
    }

    public String getlIssuePDt() {
        return lIssuePDt;
    }

    public void setlIssuePDt(String lIssuePDt) {
        this.lIssuePDt = lIssuePDt;
    }

    public String getlIssueMaturityDt() {
        return lIssueMaturityDt;
    }

    public void setlIssueMaturityDt(String lIssueMaturityDt) {
        this.lIssueMaturityDt = lIssueMaturityDt;
    }

    public String getlFreezeQty() {
        return lFreezeQty;
    }

    public void setlFreezeQty(String lFreezeQty) {
        this.lFreezeQty = lFreezeQty;
    }

    public String getlListingDt() {
        return lListingDt;
    }

    public void setlListingDt(String lListingDt) {
        this.lListingDt = lListingDt;
    }

    public String getlExpulsionDt() {
        return lExpulsionDt;
    }

    public void setlExpulsionDt(String lExpulsionDt) {
        this.lExpulsionDt = lExpulsionDt;
    }

    public String getlReadmissionDt() {
        return lReadmissionDt;
    }

    public void setlReadmissionDt(String lReadmissionDt) {
        this.lReadmissionDt = lReadmissionDt;
    }

    public String getlExDt() {
        return lExDt;
    }

    public void setlExDt(String lExDt) {
        this.lExDt = lExDt;
    }

    public String getlRecordDt() {
        return lRecordDt;
    }

    public void setlRecordDt(String lRecordDt) {
        this.lRecordDt = lRecordDt;
    }

    public String getlNoDeliveryStartDt() {
        return lNoDeliveryStartDt;
    }

    public void setlNoDeliveryStartDt(String lNoDeliveryStartDt) {
        this.lNoDeliveryStartDt = lNoDeliveryStartDt;
    }

    public String getlNoDeliveryEndDt() {
        return lNoDeliveryEndDt;
    }

    public void setlNoDeliveryEndDt(String lNoDeliveryEndDt) {
        this.lNoDeliveryEndDt = lNoDeliveryEndDt;
    }

    public String getlIndexParticipant() {
        return lIndexParticipant;
    }

    public void setlIndexParticipant(String lIndexParticipant) {
        this.lIndexParticipant = lIndexParticipant;
    }

    public String getiAONAllowed() {
        return iAONAllowed;
    }

    public void setiAONAllowed(String iAONAllowed) {
        this.iAONAllowed = iAONAllowed;
    }

    public String getiMFAllowed() {
        return iMFAllowed;
    }

    public void setiMFAllowed(String iMFAllowed) {
        this.iMFAllowed = iMFAllowed;
    }

    public String getdWarmingPercent() {
        return dWarmingPercent;
    }

    public void setdWarmingPercent(String dWarmingPercent) {
        this.dWarmingPercent = dWarmingPercent;
    }

    public String getlBookClosureStartDt() {
        return lBookClosureStartDt;
    }

    public void setlBookClosureStartDt(String lBookClosureStartDt) {
        this.lBookClosureStartDt = lBookClosureStartDt;
    }

    public String getlBookClosureEndDt() {
        return lBookClosureEndDt;
    }

    public void setlBookClosureEndDt(String lBookClosureEndDt) {
        this.lBookClosureEndDt = lBookClosureEndDt;
    }

    public String getiDividend() {
        return iDividend;
    }

    public void setiDividend(String iDividend) {
        this.iDividend = iDividend;
    }

    public String getiRights() {
        return iRights;
    }

    public void setiRights(String iRights) {
        this.iRights = iRights;
    }

    public String getiBonus() {
        return iBonus;
    }

    public void setiBonus(String iBonus) {
        this.iBonus = iBonus;
    }

    public String getiInterest() {
        return iInterest;
    }

    public void setiInterest(String iInterest) {
        this.iInterest = iInterest;
    }

    public String getiAGM() {
        return iAGM;
    }

    public void setiAGM(String iAGM) {
        this.iAGM = iAGM;
    }

    public String getiEGM() {
        return iEGM;
    }

    public void setiEGM(String iEGM) {
        this.iEGM = iEGM;
    }

    public String getcRemarks() {
        return cRemarks;
    }

    public void setcRemarks(String cRemarks) {
        this.cRemarks = cRemarks;
    }

    public String getcDeleteFlag() {
        return cDeleteFlag;
    }

    public void setcDeleteFlag(String cDeleteFlag) {
        this.cDeleteFlag = cDeleteFlag;
    }

    public String getlLastUpdateTime() {
        return lLastUpdateTime;
    }

    public void setlLastUpdateTime(String lLastUpdateTime) {
        this.lLastUpdateTime = lLastUpdateTime;
    }

    public String getdFacevalue() {
        return dFacevalue;
    }

    public void setdFacevalue(String dFacevalue) {
        this.dFacevalue = dFacevalue;
    }

    public String getcISINNumber() {
        return cISINNumber;
    }

    public void setcISINNumber(String cISINNumber) {
        this.cISINNumber = cISINNumber;
    }

    public String getiCallAuction_MarketAllowed() {
        return iCallAuction_MarketAllowed;
    }

    public void setiCallAuction_MarketAllowed(String iCallAuction_MarketAllowed) {
        this.iCallAuction_MarketAllowed = iCallAuction_MarketAllowed;
    }

    public String getiCallAuction_SecurityStatus() {
        return iCallAuction_SecurityStatus;
    }

    public void setiCallAuction_SecurityStatus(String iCallAuction_SecurityStatus) {
        this.iCallAuction_SecurityStatus = iCallAuction_SecurityStatus;
    }

    public String getiCallAuction2_SecurityStatus() {
        return iCallAuction2_SecurityStatus;
    }

    public void setiCallAuction2_SecurityStatus(String iCallAuction2_SecurityStatus) {
        this.iCallAuction2_SecurityStatus = iCallAuction2_SecurityStatus;
    }

    public String getiCallAuction2_MarketAllowed() {
        return iCallAuction2_MarketAllowed;
    }

    public void setiCallAuction2_MarketAllowed(String iCallAuction2_MarketAllowed) {
        this.iCallAuction2_MarketAllowed = iCallAuction2_MarketAllowed;
    }

    public String getlSpread() {
        return lSpread;
    }

    public void setlSpread(String lSpread) {
        this.lSpread = lSpread;
    }

    public String getlMinQty() {
        return lMinQty;
    }

    public void setlMinQty(String lMinQty) {
        this.lMinQty = lMinQty;
    }

    public String getiSSEC() {
        return iSSEC;
    }

    public void setiSSEC(String iSSEC) {
        this.iSSEC = iSSEC;
    }

    public String getiSurvInd() {
        return iSurvInd;
    }

    public void setiSurvInd(String iSurvInd) {
        this.iSurvInd = iSurvInd;
    }

    public String getlToken() {
        return lToken;
    }

    public void setlToken(String lToken) {
        this.lToken = lToken;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("cSymbol", this.cSymbol);
        jo.put("cSeries", this.cSeries);
        jo.put("iInstrumentType", this.iInstrumentType);
        jo.put("dIssueCapital", this.dIssueCapital);
        jo.put("iPermittedToTrade", this.iPermittedToTrade);
        jo.put("cCreditRating", this.cCreditRating);
        jo.put("iNormal_SecurityStatus", this.iNormal_SecurityStatus);
        jo.put("iNormal_MarketAllowed", this.iNormal_MarketAllowed);
        jo.put("iOddLot_SecurityStatus", this.iOddLot_SecurityStatus);
        jo.put("iOddLot_MarketAllowed", this.iOddLot_MarketAllowed);
        jo.put("iSpot_SecurityStatus", this.iSpot_SecurityStatus);
        jo.put("iSpot_MarketAllowed", this.iSpot_MarketAllowed);
        jo.put("iAuction_SecurityStatus", this.iAuction_SecurityStatus);
        jo.put("iAuction_MarketAllowed", this.iAuction_MarketAllowed);
        jo.put("lRegularLot", this.lRegularLot);
        jo.put("dPriceTick", this.dPriceTick);
        jo.put("cSecurityDesc", this.cSecurityDesc);
        jo.put("dIssueRate", this.dIssueRate);
        jo.put("lIssueStartDt", this.lIssueStartDt);
        jo.put("lIssueMaturityDt", this.lIssueMaturityDt);
        jo.put("lFreezeQty", this.lFreezeQty);
        jo.put("lListingDt", this.lListingDt);
        jo.put("lExpulsionDt", this.lExpulsionDt);
        jo.put("lReadmissionDt", this.lReadmissionDt);
        jo.put("lExDt", this.lExDt);
        jo.put("lRecordDt", this.lRecordDt);
        jo.put("lNoDeliveryStartDt", this.lNoDeliveryStartDt);
        jo.put("lNoDeliveryEndDt", this.lNoDeliveryEndDt);
        jo.put("lIndexParticipant", this.lIndexParticipant);
        jo.put("iAONAllowed", this.iAONAllowed);
        jo.put("iMFAllowed", this.iMFAllowed);
        jo.put("dWarmingPercent", this.dWarmingPercent);
        jo.put("lBookClosureStartDt", this.lBookClosureStartDt);
        jo.put("lBookClosureEndDt", this.lBookClosureEndDt);
        jo.put("iDividend", this.iDividend);
        jo.put("iRights", this.iRights);
        jo.put("iBonus", this.iBonus);
        jo.put("iInterest", this.iInterest);
        jo.put("iAGM", this.iAGM);
        jo.put("iEGM", this.iEGM);
        jo.put("cRemarks", this.cRemarks);
        jo.put("cDeleteFlag", this.cDeleteFlag);
        jo.put("lLastUpdateTime", this.lLastUpdateTime);
        jo.put("dFacevalue", this.dFacevalue);
        jo.put("cISINNumber", this.cISINNumber);
        jo.put("iCallAuction_MarketAllowed", this.iCallAuction_MarketAllowed);
        jo.put("iCallAuction_SecurityStatus", this.iCallAuction_SecurityStatus);
        jo.put("iCallAuction2_SecurityStatus", this.iCallAuction2_SecurityStatus);
        jo.put("iCallAuction2_MarketAllowed", this.iCallAuction2_MarketAllowed);
        jo.put("lSpread", this.lSpread);
        jo.put("lMinQty", this.lMinQty);
        jo.put("iSSEC", this.iSSEC);
        jo.put("iSurvInd", this.iSurvInd);
        jo.put("lToken", this.lToken);
        jo.put("lIssuePDt", this.lIssuePDt);
        jo.put("dLowPriceRange", this.dLowPriceRange);
        jo.put("dHighPriceRange", this.dHighPriceRange);
        jo.put("ErrorCode", this.ErrorCode);


        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.cSymbol = jo.optString("cSymbol");
        this.cSeries = jo.optString("cSeries");
        this.iInstrumentType = jo.optString("iInstrumentType");
        this.dIssueCapital = jo.optString("dIssueCapital");
        this.iPermittedToTrade = jo.optString("iPermittedToTrade");
        this.cCreditRating = jo.optString("cCreditRating");
        this.iNormal_SecurityStatus = jo.optString("iNormal_SecurityStatus");
        this.iNormal_MarketAllowed = jo.optString("iNormal_MarketAllowed");
        this.iOddLot_SecurityStatus = jo.optString("iOddLot_SecurityStatus");
        this.iOddLot_MarketAllowed = jo.optString("iOddLot_MarketAllowed");
        this.iSpot_SecurityStatus = jo.optString("iSpot_SecurityStatus");
        this.iSpot_MarketAllowed = jo.optString("iSpot_MarketAllowed");
        this.iAuction_SecurityStatus = jo.optString("iAuction_SecurityStatus");
        this.iAuction_MarketAllowed = jo.optString("iAuction_MarketAllowed");
        this.lRegularLot = jo.optString("lRegularLot");
        this.dPriceTick = jo.optString("dPriceTick");
        this.cSecurityDesc = jo.optString("cSecurityDesc");
        this.dIssueRate = jo.optString("dIssueRate");
        this.lIssueStartDt = jo.optString("lIssueStartDt");
        this.lIssueMaturityDt = jo.optString("lIssueMaturityDt");
        this.lFreezeQty = jo.optString("lFreezeQty");
        this.lListingDt = jo.optString("lListingDt");
        this.lExpulsionDt = jo.optString("lExpulsionDt");
        this.lReadmissionDt = jo.optString("lReadmissionDt");
        this.lExDt = jo.optString("lExDt");
        this.lRecordDt = jo.optString("lRecordDt");
        this.lNoDeliveryStartDt = jo.optString("lNoDeliveryStartDt");
        this.lNoDeliveryEndDt = jo.optString("lNoDeliveryEndDt");
        this.lIndexParticipant = jo.optString("lIndexParticipant");
        this.iAONAllowed = jo.optString("iAONAllowed");
        this.iMFAllowed = jo.optString("iMFAllowed");
        this.dWarmingPercent = jo.optString("dWarmingPercent");
        this.lBookClosureStartDt = jo.optString("lBookClosureStartDt");
        this.lBookClosureEndDt = jo.optString("lBookClosureEndDt");
        this.iDividend = jo.optString("iDividend");
        this.iRights = jo.optString("iRights");
        this.iBonus = jo.optString("iBonus");
        this.iInterest = jo.optString("iInterest");
        this.iAGM = jo.optString("iAGM");
        this.iEGM = jo.optString("iEGM");
        this.cRemarks = jo.optString("cRemarks");
        this.cDeleteFlag = jo.optString("cDeleteFlag");
        this.lLastUpdateTime = jo.optString("lLastUpdateTime");
        this.dFacevalue = jo.optString("dFacevalue");
        this.cISINNumber = jo.optString("cISINNumber");
        this.iCallAuction_MarketAllowed = jo.optString("iCallAuction_MarketAllowed");
        this.iCallAuction_SecurityStatus = jo.optString("iCallAuction_SecurityStatus");
        this.iCallAuction2_SecurityStatus = jo.optString("iCallAuction2_SecurityStatus");
        this.iCallAuction2_MarketAllowed = jo.optString("iCallAuction2_MarketAllowed");
        this.lSpread = jo.optString("lSpread");
        this.lMinQty = jo.optString("lMinQty");
        this.iSSEC = jo.optString("iSSEC");
        this.iSurvInd = jo.optString("iSurvInd");
        this.lToken = jo.optString("lToken");
        this.lIssuePDt = jo.optString("lIssuePDt");
        this.dLowPriceRange = jo.optString("dLowPriceRange");
        this.dHighPriceRange = jo.optString("dHighPriceRange");
        this.ErrorCode = jo.optString("ErrorCode");


        this.cBandhaniRange = jo.optString("cBandhaniRange");
        this.lContractStartDt = jo.optString("lContractStartDt");
        this.lLastTradingDate = jo.optString("lLastTradingDate");
        this.lDeliveryStartDt = jo.optString("lDeliveryStartDt");
        this.lDeliveryEndDt = jo.optString("lDeliveryEndDt");
        this.iTrade2Trade = jo.optString("iTrade2Trade");
        this.iIndexFlag = jo.optString("iIndexFlag");
        this.iDefaultIndex = jo.optString("iDefaultIndex");
        this.iIndexInstrument = jo.optString("iIndexInstrument");
        this.iFeedFlag = jo.optString("iFeedFlag");
        this.cInstrumentstatusFlag = jo.optString("cInstrumentstatusFlag");
        this.iMinimumLot = jo.optString("iMinimumLot");
        this.lTenderPeriodStartDt = jo.optString("lTenderPeriodStartDt");
        this.lTenderPeriodEndDt = jo.optString("lTenderPeriodEndDt");
        this.cCommodityGroup = jo.optString("cCommodityGroup");
        this.cUnderlyingCommodity = jo.optString("cUnderlyingCommodity");
        this.lUnderlyingIdentifier = jo.optString("lUnderlyingIdentifier");
        this.cInstrumentName = jo.optString("cInstrumentName");
        this.lOriginalExpiryDt = jo.optString("lOriginalExpiryDt");
        this.dStrikePrice = jo.optString("dStrikePrice");
        this.cOptionType = jo.optString("cOptionType");
        this.iCALevel = jo.optString("iCALevel");
        this.iSegmentId = jo.optString("iSegmentId");
        this.cPriceQuoteUnit = jo.optString("cPriceQuoteUnit");
        this.lPriceQuoteQty = jo.optString("lPriceQuoteQty");
        this.iDPRTerms = jo.optString("iDPRTerms");
        this.dUpperDPR = jo.optString("dUpperDPR");
        this.dLowerDPR = jo.optString("dLowerDPR");
        this.iTenderPeriodIndicator = jo.optString("iTenderPeriodIndicator");
        this.iSettleMentMethod = jo.optString("iSettleMentMethod");
        this.iInitialMarginTerms = jo.optString("iInitialMarginTerms");
        this.dBuyMarginRate = jo.optString("dBuyMarginRate");
        this.lBasePrice = jo.optString("lBasePrice");
        this.lMaxSingleTransQty = jo.optString("lMaxSingleTransQty");
        this.dMaxSingleTransValue = jo.optString("dMaxSingleTransValue");
        this.iInstrumentClass = jo.optString("iInstrumentClass");
        this.lNearMonthInstIdentifier = jo.optString("lNearMonthInstIdentifier");
        this.lFarMonthInstIdentifier = jo.optString("lFarMonthInstIdentifier");
        this.cTradingUnit = jo.optString("cTradingUnit");
        this.dTradingUnitFactor = jo.optString("dTradingUnitFactor");
        this.cDeliveryUnit = jo.optString("cDeliveryUnit");
        this.dDeliveryUnitFactor = jo.optString("dDeliveryUnitFactor");
        this.dPriceNumerator = jo.optString("dPriceNumerator");
        this.cSpecification = jo.optString("cSpecification");
        this.dPriceDenominator = jo.optString("dPriceDenominator");
        this.dGeneralNumerator = jo.optString("dGeneralNumerator");
        this.dGeneralDenominator = jo.optString("dGeneralDenominator");
        this.dLotNumerator = jo.optString("dLotNumerator");
        this.dLotDenominator = jo.optString("dLotDenominator");
        this.dDecimalLocator = jo.optString("dDecimalLocator");
        this.dSellMarginRate = jo.optString("dSellMarginRate");
        this.iTermsOfSpecialMargin = jo.optString("iTermsOfSpecialMargin");
        this.dBuySpecialMarginRate = jo.optString("dBuySpecialMarginRate");
        this.dSellSpecialMarginRate = jo.optString("dSellSpecialMarginRate");
        this.iMarginSpreadBenefitFlag = jo.optString("iMarginSpreadBenefitFlag");
        this.lInstrumentEndDate = jo.optString("lInstrumentEndDate");
        this.cTradingCurrency = jo.optString("cTradingCurrency");
        this.cContractMonth = jo.optString("cContractMonth");
        this.iPreOpenAllowed = jo.optString("iPreOpenAllowed");
        this.iGroupId = jo.optString("iGroupId");
        this.iMatchingType = jo.optString("iMatchingType");
        this.iSpreadType = jo.optString("iSpreadType");
        this.cValueMethod = jo.optString("cValueMethod");
        this.cSLBMEligibility = jo.optString("cSLBMEligibility");
        this.dIRFfactor = jo.optString("dIRFfactor");

        return this;
    }
}


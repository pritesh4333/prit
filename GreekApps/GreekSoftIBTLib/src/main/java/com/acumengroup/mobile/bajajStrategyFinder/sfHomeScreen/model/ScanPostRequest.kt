package com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model

data class ScanPostRequest(
        val request: Request
) {
    data class Request(
            val `data`: Data,
            val svcGroup: String,
            val svcName: String,
            val FormFactor: String,
            val svcVersion: String,
            val requestType: String
    ) {
        data class Data(
                val bIsCovered: String,
                val bIsLtpReferancePrice: String,
                val bStkEquityFlag: String,
                val cSymbol: String,
                val dIDXInterest: String,
                val dITMDelta: String,
                val dLowerStrike: String,
                val dMaxAskBidDiff: String,
                val dSTKInterest: String,
                var dStrikeDiff: String,
                val dUpperStrike: String,
                val iAlertAddPosition: String,
                var iCallPut: String,
                var iCallPutSelection: String,
                val iExchange: String,
                val iHideMaxGainLoss: String,
                val iRefreshTimer: String,
                var iStrategyType: List<IStrategyType>,
                val iStrikeCombinations: String,
                val iStrikeFrom: String,
                var iStrikeInterval: String,
                val iStrikeTo: String,
                val iTokenCount: String,
                val iVolatality: String,
                val iVolatalityTitle: String,
                val iMarketTitle: String,
                var lExpDate: String,
                val lMinOpenInterest: String,
                val lMinVolume: String
        ) {
            data class IStrategyType(
                    val bIsCovered: String,
                    val iCallPut: String,
                    val iStrategyType: String,
                    val iTokenCount: String
            )
        }
    }
}
package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model

data class ScanDataResponse(
    val config: Config,
    val response: Response
) {
    data class Config(
        val app: Int,
        val label: Int,
        val message: Int
    )

    data class Response(
        val appID: String,
        val `data`: Data,
        val infoID: String,
        val msgID: String,
        val serverTime: String,
        val svcGroup: String,
        val svcName: String,
        val svcVersion: String
    ) {
        data class Data(
            val `data`: ArrayList<ScanData>
        ) {
            data class ScanData(
                    val cTradeDetails: String,
                    var dBEP: String,
                    val dEquityAmt: String,
                    val dExpMargin: String,
                    var dInvestment: String,
                    var dMaxGain: String,
                    var dMaxLoss: String,
                    var dNetPremium: String,
                    var dOtherMargin: String,
                    val dPremium: String,
                    val dPrice: String,
                    val dRate_0: String,
                    val dRate_1: String,
                    val dRate_2: String,
                    val dRate_3: String,
                    var dRiskRatio: String,
                    var dSpanMargin: String,
                    val iBuySell_0: String,
                    val iBuySell_1: String,
                    val iBuySell_2: String,
                    val iBuySell_3: String,
                    var iMarketOutlook: String,
                    var iStrategyType: String="",
                    val iToken_0: String,
                    val iToken_1: String,
                    val iToken_2: String,
                    val iToken_3: String,
                    val iUnit_0: String,
                    val iUnit_1: String,
                    val iUnit_2: String,
                    val iUnit_3: String,
                    var iVolatility: String,
                    var lExpiry: String,
                    var lProbability: String,
                    var lRuleData: String,
                    var dStrikeDiff: String
            )
        }
    }


}
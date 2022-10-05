package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class StrategyBuilderViewRequest(
        val request: Request
) {
    data class Request(
            val FormFactor: String,
            val `data`: Data,
            val requestType: String,
            val svcGroup: String,
            val svcName: String,
            val svcVersion: String
    ) {
        data class Data(
                val cClientCode: String,
                val cEFBase: String,
                val cExchange: String,
                val cExpiry: String,
                val cGreekClientID: String,
                val cReportType: String,
                val cStrategy: String,
                val cSymbol: String,
                val dCallIV: Double,
                val dDaysLeft: Double,
                val dInterestRate: Double,
                val dMarketRate: Double,
                val dMidStrike: Double,
                val dPutIV: Double,
                val dStrikeDiff: Double,
                val iIVType: Int,
                val iRangeD: Int,
                val iRangeU: Int
        )
    }
}
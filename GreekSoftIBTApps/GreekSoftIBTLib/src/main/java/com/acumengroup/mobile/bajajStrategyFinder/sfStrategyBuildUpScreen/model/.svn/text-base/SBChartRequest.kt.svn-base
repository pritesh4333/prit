package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class SBChartRequest(
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
                val TokenInfo: List<TokenInf>,
                val cEFBase: String,
                val cGreekClientID: String,
                val cReportType: String,
                val dInterestRate: String,
                val dMidStrike: Double,
                val dStrikeDiff: Double,
                val iRangeD: Int,
                val iRangeU: Int
        ) {
            data class TokenInf(
                var dRate: Double,
                var lToken: Long,
                var lUnit: Long
            )
        }
    }
}
package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class GraphResponseData(
    val config: Config,
    val response: Response
) {
    data class Config(
        val app: Int,
        val label: Int,
        val message: Int
    )

    data class Response(
        val ErrorCode: Int,
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
            val ErrorCode: String,
            var `data`: ArrayList<ChartData>
        ) {
            data class ChartData(
                val dBalance: String,
                val dDeltaNeutral: String,
                val dDeltaVal: String,
                val dExpBalance: String,
                val dGammaVal: String,
                val dMarketRate: String,
                val dThetaVal: String,
                val dVegaVal: String,
                val iRange: String,
                val iReportType: String
            )
        }
    }
}
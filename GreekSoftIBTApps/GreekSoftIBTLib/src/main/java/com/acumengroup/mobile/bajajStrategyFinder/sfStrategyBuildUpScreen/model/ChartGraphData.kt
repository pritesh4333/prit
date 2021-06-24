package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class ChartGraphData(
    var config: Config,
    var response: Response
) {
    data class Config(
        var app: Int,
        var label: Int,
        var message: Int
    )

    data class Response(
        var ErrorCode: Int,
        var appID: String,
        var `data`: Data,
        var infoID: String,
        var msgID: String,
        var serverTime: String,
        var svcGroup: String,
        var svcName: String,
        var svcVersion: String
    ) {
        data class Data(
            var ErrorCode: String,
            var `data`: ArrayList<ChartData>
        ) {
            data class ChartData(
                var dBalance: String,
                var dDeltaNeutral: String,
                var dDeltaVal: String,
                var dExpBalance: String,
                var dGammaVal: String,
                var dMarketRate: String,
                var dThetaVal: String,
                var dVegaVal: String,
                var iRange: String,
                var iReportType: String
            )
        }
    }
}
package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class StrategyNameResponse(
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
                var ErrorCode: Int,
                var `data`: ArrayList<StrategyName>
        ) {
            data class StrategyName(
                    var cClientCode: String,
                    var cExchange: String,
                    var cExpiry: String,
                    var cGreekClientID: String,
                    var cStrategy: String,
                    var cSymbol: String,
                    var iHidePortfolio: String,
                    var isChecked: Boolean
            )
        }
    }
}
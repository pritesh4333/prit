package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class DeletePortfolioRequest(
    var request: Request
) {
    data class Request(
            var FormFactor: String,
            var `data`: PortfolioData,
            var requestType: String,
            var svcGroup: String,
            var svcName: String,
            var svcVersion: String
    ) {
        data class PortfolioData(
            var cClientCode: String,
            var cExchange: String,
            var cExpiry: String,
            var cGreekClientID: String,
            var cStrategy: String,
            var cSymbol: String,
            var iDeleteAll: String,
            var iErrorCode: String
        )
    }
}
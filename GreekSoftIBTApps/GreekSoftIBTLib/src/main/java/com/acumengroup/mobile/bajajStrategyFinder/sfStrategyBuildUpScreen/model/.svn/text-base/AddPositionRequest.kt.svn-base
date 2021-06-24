package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class AddPositionRequest(
        var request: Request
) {
    data class Request(
            var FormFactor: String,
            var `data`: Data,
            var requestType: String,
            var svcGroup: String,
            var svcName: String,
            var svcVersion: String
    ) {
        data class Data(
                var bIsOptionStrategy: Boolean,
                var bTempTradeFlag: Boolean,
                var cAccountNumber: String,
                var cBrokerID: String,
                var cExpiryDt: String,
                var cGreekClientID: String,
                var cInstrumentName: String,
                var cMappedSymbol: String,
                var cMaturityDate: String,
                var cOptionType: String,
                var cSeries: String,
                var cStrategyName: String,
                var cSymbolName: String,
                var cTradeDate: String,
                var dFillPrice: Double,
                var dStrikePrice: Double,
                var iBuySell: Int,
                var iExchange: Int,
                var iIdentifier: Int,
                var iMarketSegment: Int,
                var iProClient: Int,
                var iTradeType: Int,
                var lFillNumber: Long,
                var lFillQuantity: Long,
                var lOurOrderNo: Long,
                var lToken: Long
        )
    }
}
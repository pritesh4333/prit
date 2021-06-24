package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model

data class TradeNotificationRequest(
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
                val bIsOptionStrategy: Boolean,
                val bTempTradeFlag: Boolean,
                val cAccountNumber: String,
                val cBrokerID: String,
                val cExpiryDt: String,
                val cGreekClientID: String,
                val cInstrumentName: String,
                val cMappedSymbol: String,
                val cMaturityDate: String,
                val cOptionType: String,
                val cSeries: String,
                val cStrategyName: String,
                val cSymbolName: String,
                val cTradeDate: String,
                val dFillPrice: Double,
                val dStrikePrice: Double,
                val iBuySell: Int,
                val iExchange: Int,
                val iIdentifier: Int,
                val iMarketSegment: Int,
                val iProClient: Int,
                val iTradeType: Int,
                val lFillNumber: Long,
                val lFillQuantity: Long,
                val lOurOrderNo: Long,
                val lToken: Long
        )
    }
}
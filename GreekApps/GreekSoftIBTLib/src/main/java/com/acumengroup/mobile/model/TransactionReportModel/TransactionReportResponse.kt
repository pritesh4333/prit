package com.acumengroup.mobile.model.TransactionReportModel

data class TransactionReportResponse(
    var config: Config,
    var response: Response
) {
    data class Config(
        var app: Int, // 12
        var label: Int, // 8
        var message: Int // 9
    )

    data class Response(
        var ErrorCode: String, // 0
        var appID: String, // bc90bb525bc9739a9595bb9e176dab17
        var `data`: Data,
        var infoID: String, // 0
        var msgID: String, // a9bfad10-92ce-4fd7-968f- c25877c1bbb3
        var serverTime: String, // 1450349681701
        var svcGroup: String, // portfolio
        var svcName: String, // getTransactionReport_Phillip
        var svcVersion: String // 1.0.0
    ) {
        data class Data(
            var transactionData: TransactionData
        ) {
            data class TransactionData(
                var Message: Any?, // null
                var Success: Boolean, // false
                var Trades: List<Trade>
            ) {
                data class Trade(
                    var Buysell: String, // SELL
                    var Date: String, // 06/09/2021
                    var NetPrice: String, // 216.011600
                    var Price: String, // 216.121600
                    var Quantity: String, // 750
                    var Segment: String, // NSECASH
                    var Ser: String, // EQ-2021168N  
                    var Stock: String, // BANCO PRODUCTS (I) LTD
                    var TradeTime: String, // Sep  6 2021 12:05:13
                    var commission: String, // 0.110000
                    var order_no: String, // 1000000008515085
                    var party_code: String, // N00532    
                    var scripcd: String, // BANCOINDIA
                    var trade_no: String, // 2020009
                    var user_id: String // 40487
                )
            }
        }
    }
}
package com.acumengroup.mobile.model

data class UpdateNSDLAuthorizationResponse(
    var response: Response
) {
    data class Response(
        var appID: String,
        var `data`: Data,
        var infoID: String,
        var serverTime: String,
        var streaming_type: String,
        var svcName: String
    ) {
        data class Data(
            var gscid: String,
            var stockDetails: List<StockDetail>
        ) {
            data class StockDetail(
                var ISIN: String,
                var Quantity: String,
                var Remark: String,
                var Status: String,
                var Symbol: String,
                var TxnId: String,
                var token: String
            )
        }
    }
}
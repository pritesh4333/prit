package com.bfsl.core.network

data class NPDetailResponse(
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
                var islast: String,
                var noofrecords: String,
                var stockDetails: ArrayList<StockDetail>
        ) {
            data class StockDetail(
                    var BSEToken: String,
                    var NSEToken: String,
                    var PAmt: String,
                    var ProductType: String,
                    var account: String,
                    var buyAmt: String,
                    var buyQty: String,
                    var close: String,
                    var expiry_date: String,
                    var instrument: String,
                    var isin: String,
                    var lotQty: String,
                    var ltp: String,
                    var multiplier: String,
                    var price_multiplier: String,
                    var option_type: String,
                    var preNetQty: String,
                    var sellAmt: String,
                    var sellQty: String,
                    var sqoffToken: String,
                    var strike_price: String,
                    var symbol: String,
                    var tickSize: String,
                    var token: String,
                    var tradeSymbol: String
            )
        }
    }
}
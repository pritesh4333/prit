package com.bfsl.core.network

data class MarketMoversModel(
    val response: Response
) {
    data class Response(
        val appID: String,
        val `data`: ArrayList<MarketMovers>,
        val infoID: String,
        val serverTime: String,
        val streaming_type: String,
        val marketid: String,
        val svcName: String
    ) {
        data class MarketMovers(
                val change: String,
                val close: String,
                val ltp: String,
                val perchange: String,
                val symbol: String,
                val token: String,
                val description: String,
                val Exchange: String,
                val LotSize: String,
                val AssetType: String,
                val TickSize: String,
                val Multiply_factor: String,
                val ExpiryDate: String,
                val optiontype: String,
                val strikeprice: String,
                val instrumentname: String,
                val voltraded: String
        )
    }
}
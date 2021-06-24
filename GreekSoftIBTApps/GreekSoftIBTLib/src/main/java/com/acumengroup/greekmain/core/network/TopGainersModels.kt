package com.bfsl.core.network

data class TopGainersModels(
    val response: Response
) {
    data class Response(
            val appID: String,
            val `data`: ArrayList<TOPGainer>,
            val infoID: String,
            val serverTime: String,
            val streaming_type: String,
            val marketid: String,
            val svcName: String
    ) {
        data class TOPGainer(
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
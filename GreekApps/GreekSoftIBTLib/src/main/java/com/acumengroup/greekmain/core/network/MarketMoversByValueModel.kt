package com.acumengroup.greekmain.core.network

data class MarketMoversByValueModel(
    val response: Response
) {
    data class Response(
            val appID: String,
            val `data`: ArrayList<MarketMoversByValueData>,
            val infoID: String,
            val marketid: String,
            val serverTime: String,
            val streaming_type: String,
            val svcName: String
    ) {
        data class MarketMoversByValueData(
            val AssetType: String,
            val Exchange: String,
            val ExpiryDate: String,
            val LotSize: String,
            val Multiply_factor: String,
            val TickSize: String,
            val change: String,
            val close: String,
            val description: String,
            val instrumentname: String,
            val ltp: String,
            val optiontype: String,
            val perchange: String,
            val strikeprice: String,
            val symbol: String,
            val token: String,
            val value: String
        )
    }
}
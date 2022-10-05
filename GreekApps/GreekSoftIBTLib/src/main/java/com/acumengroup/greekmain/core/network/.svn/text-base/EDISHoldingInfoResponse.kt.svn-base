package com.acumengroup.greekmain.core.network

import java.util.*

data class EDISHoldingInfoResponse(
        val response: Response
) {
    data class Response(
            val appID: String,
            val `data`: Data,
            val infoID: String,
            val serverTime: String,
            val streaming_type: String,
            val svcName: String
    ) {
        data class Data(
                val islast: String,
                val noofrecords: String,
                val stockDetails: ArrayList<StockDetail>
        ) {
            data class StockDetail(
                    val HQty: String,
                    val FreeQty: String,
                    val PledgedQty: String,
                    val Rate: String,
                    val Value: String,
                    val OpenAuthQty: String,
                    val TodaySoldQty: String,
                    val TodayAuthQty: String,
                    val instrument: String,
                    val isin: String,
                    val symbol: String,
                    val close: String,
                    val token: String,
                    val sellmarket:String,
                    val lotSize:String
            )
        }
    }
}
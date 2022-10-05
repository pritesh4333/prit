package com.acumengroup.greekmain.core.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class HoldinginfoResponse(
    @SerializedName("response")
    val response: Response
) {
    data class Response(
            @SerializedName("appID")
        val appID: String,
            @SerializedName("data")
        val `data`: Data,
            @SerializedName("infoID")
        val infoID: String,
            @SerializedName("serverTime")
        val serverTime: String,
            @SerializedName("streaming_type")
        val streamingType: String,
            @SerializedName("svcName")
        val svcName: String
    ) {
        data class Data(
            @SerializedName("islast")
            val islast: String,
            @SerializedName("noofrecords")
            val noofrecords: String,
            @SerializedName("stockDetails")
            val stockDetails: ArrayList<StockDetail>
        ) {
            data class StockDetail(
                @SerializedName("BSEToken")
                val bSEToken: String,
                @SerializedName("Close")
                val close: String,
                @SerializedName("HPrice")
                val hPrice: String,
                @SerializedName("instrument")
                val instrument: String,
                @SerializedName("isin")
                val isin: String,
                @SerializedName("Ltp")
                val ltp: String,
                @SerializedName("NSEToken")
                val nSEToken: String,
                @SerializedName("Qty")
                val qty: String,
                @SerializedName("symbol")
                val symbol: String
            )
        }
    }
}
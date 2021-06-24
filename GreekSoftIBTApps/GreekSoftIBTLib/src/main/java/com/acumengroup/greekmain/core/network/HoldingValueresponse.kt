package com.acumengroup.greekmain.core.network


import com.google.gson.annotations.SerializedName

data class HoldingValueresponse(
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
            @SerializedName("CValue")
            val cValue: String,
            @SerializedName("gscid")
            val gscid: String,
            @SerializedName("HValue")
            val hValue: String
        )
    }
}
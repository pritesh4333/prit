package com.acumengroup.greekmain.core.network


import com.google.gson.annotations.SerializedName

data class SymbolVarMarginResponse(
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
            @SerializedName("ExposMargin")
            val exposMargin: String,
            @SerializedName("ExposMargin_Per")
            val exposMarginPer: String,
            @SerializedName("SPANMargin_Buy")
            val sPANMarginBuy: String,
            @SerializedName("SPANMargin_Sell")
            val sPANMarginSell: String,

            @SerializedName("VARMargin")
            val VARMargin: String,
            @SerializedName("VARPercentage")
            val VARPercentage: String,
            @SerializedName("ELMMargin")
            val ELMMargin: String,
            @SerializedName("ELMPercentage")
            val ELMPercentage: String


        )
    }
}
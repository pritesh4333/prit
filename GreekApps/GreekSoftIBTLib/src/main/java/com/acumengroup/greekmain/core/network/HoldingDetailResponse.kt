package com.acumengroup.greekmain.core.network


import com.google.gson.annotations.SerializedName

data class HoldingDetailResponse(
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
            @SerializedName("ActualDPPrice")
            val actualDPPrice: String,
            @SerializedName("ActualDPQty")
            val actualDPQty: String,
            @SerializedName("ActualMTFPrice")
            val actualMTFPrice: String,
            @SerializedName("ActualMTFQty")
            val actualMTFQty: String,
            @SerializedName("ActualPoolPrice")
            val actualPoolPrice: String,
            @SerializedName("ActualPoolQty")
            val actualPoolQty: String,
            @SerializedName("DPPrice")
            val dPPrice: String,
            @SerializedName("DPQty")
            val dPQty: String,
            @SerializedName("DPReleaseQty")
            val dPReleaseQty: String,
            @SerializedName("gscid")
            val gscid: String,
            @SerializedName("instrument")
            val instrument: String,
            @SerializedName("isin")
            val isin: String,
            @SerializedName("MTFPrice")
            val mTFPrice: String,
            @SerializedName("MTFQty")
            val mTFQty: String,
            @SerializedName("MTFReleaseQty")
            val mTFReleaseQty: String,
            @SerializedName("NetHoldingPrice")
            val netHoldingPrice: String,
            @SerializedName("NetHoldingQty")
            val netHoldingQty: String,
            @SerializedName("PendingAmount")
            val pendingAmount: String,
            @SerializedName("PendingQty")
            val pendingQty: String,
            @SerializedName("PoolPrice")
            val poolPrice: String,
            @SerializedName("PoolQty")
            val poolQty: String,
            @SerializedName("PoolReleaseQty")
            val poolReleaseQty: String,
            @SerializedName("RiskBlockQty")
            val riskBlockQty: String,
            @SerializedName("scripName")
            val scripName: String,
            @SerializedName("SoldQty")
            val soldQty: String,
            @SerializedName("symbol")
            val symbol: String,
            @SerializedName("TodayBuyATP")
            val todayBuyATP: String,
            @SerializedName("TodayBuyQty")
            val todayBuyQty: String,
            @SerializedName("TodayNetQty")
            val todayNetQty: String,
            @SerializedName("TodaySellATP")
            val todaySellATP: String,
            @SerializedName("TodaySellQty")
            val todaySellQty: String,
            @SerializedName("token")
            val token: String,
            @SerializedName("FreeHOldingQty")
        val FreeHOldingQty: String
        )
    }
}
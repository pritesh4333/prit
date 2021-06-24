package com.acumengroup.mobile.model

data class FOPLResponse(
    val config: Config,
    val response: Response
) {
    data class Config(
        val app: Int,
        val label: Int,
        val message: Int
    )

    data class Response(
        val ErrorCode: Int,
        val appID: String,
        val `data`: Data,
        val infoID: String,
        val msgID: String,
        val serverTime: String,
        val svcGroup: String,
        val svcName: String,
        val svcVersion: String
    ) {
        data class Data(
            val `data`: ArrayList<FOPLData>
        ) {
            data class FOPLData(
                    val AvgPrice: String,
                    val BuyAvg: String,
                    val BuyQty: String,
                    val Change: String,
                    val Close: String,
                    val CurrValue: String,
                    val CurrentPrice: String,
                    val GLRealPerc: String,
                    val GLRealized: String,
                    val GLToday: String,
                    val GLTodayPerc: String,
                    val GLTotal: String,
                    val GLUnrealPerc: String,
                    val GLUnrealized: String,
                    val InvestValue: String,
                    val Ltp: String,
                    val PerChange: String,
                    val Quantity: String,
                    val ScripName: String,
                    val SellAvg: String,
                    val SellQty: String,
                    val ourtoken: String,
                    val NetQty: String,
                    val NetPrice: String,
                    val GLTotalPerc: String
            )
        }
    }
}
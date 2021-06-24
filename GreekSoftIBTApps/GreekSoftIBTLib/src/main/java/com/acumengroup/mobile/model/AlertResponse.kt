package com.acumengroup.mobile.model


import com.google.gson.annotations.SerializedName

data class AlertResponse(
    @SerializedName("config")
    val config: Config,
    @SerializedName("response")
    val response: Response
) {
    data class Config(
        @SerializedName("app")
        val app: Int,
        @SerializedName("label")
        val label: Int,
        @SerializedName("message")
        val message: Int
    )

    data class Response(
        @SerializedName("appID")
        val appID: String,
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("ErrorCode")
        val errorCode: Int,
        @SerializedName("infoID")
        val infoID: String,
        @SerializedName("msgID")
        val msgID: String,
        @SerializedName("serverTime")
        val serverTime: String,
        @SerializedName("svcGroup")
        val svcGroup: String,
        @SerializedName("svcName")
        val svcName: String,
        @SerializedName("svcVersion")
        val svcVersion: String
    ) {
        data class Data(
            @SerializedName("Data")
            val `alertData`: List<AlertData>
        ) {
            data class AlertData(
                @SerializedName("alert_type")
                val alertType: String,
                @SerializedName("assetType")
                val assetType: String,
                @SerializedName("description")
                val description: String,
                @SerializedName("direction_flag")
                val directionFlag: String,
                @SerializedName("end_datetime")
                val endDatetime: String,
                @SerializedName("exchange")
                val exchange: String,
                @SerializedName("expiry_date")
                val expiryDate: String,
                @SerializedName("gcid")
                val gcid: String,
                @SerializedName("gscid")
                val gscid: String,
                @SerializedName("gtoken")
                val gtoken: String,
                @SerializedName("is_executed")
                val isExecuted: String,
                @SerializedName("last_updated_time")
                val lastUpdatedTime: String,
                @SerializedName("range")
                val range: String,
                @SerializedName("rule_no")
                val ruleNo: String,
                @SerializedName("series_instname")
                val seriesInstname: String,
                @SerializedName("start_datetime")
                val startDatetime: String,
                @SerializedName("strike_price")
                val strikePrice: String,
                @SerializedName("symbol")
                val symbol: String,
                @SerializedName("token")
                val token: String
            )
        }
    }
}
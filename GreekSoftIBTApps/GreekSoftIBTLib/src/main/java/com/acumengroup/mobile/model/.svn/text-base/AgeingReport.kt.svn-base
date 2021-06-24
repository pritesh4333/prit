package com.acumengroup.mobile.model

data class AgeingReport(
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
        val `data`: ArrayList<AgeingData>,
        val infoID: String,
        val msgID: String,
        val serverTime: String,
        val svcGroup: String,
        val svcName: String,
        val svcVersion: String
    ) {
        data class AgeingData(
            val AGEING_DEBIT_AMOUNT: String,
            val NUMBER_AGEING_DAYS: String,
            val SEGMENT: String,
            val CLIENT_CODE: String,
            val CLIENT_NAME: String,
            val SERIES: String,
            val ISIN_NO: String,
            val BSE_CODE: String,
            val SCRIP_NAME: String,
            val SETTLEMENT_NO: String,
            val NSE_CODE: String,
            val SETTLEMENT_TYPE: String,
            val EXCHANGE: String,
            val HOLDING_QUANTITY: String
        )
    }
}
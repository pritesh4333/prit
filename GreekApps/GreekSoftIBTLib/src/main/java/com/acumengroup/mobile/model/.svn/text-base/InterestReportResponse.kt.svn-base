package com.acumengroup.mobile.model

data class InterestReportResponse(
    val config: Config,
    val response: Response
) {
    data class Config(
        val app: Int,
        val label: Int,
        val message: Int
    )

    data class Response(
        val ErrorCode: String,
        val appID: String,
        val `data`: ArrayList<InterestReportData>,
        val infoID: String,
        val msgID: String,
        val serverTime: String,
        val svcGroup: String,
        val svcName: String,
        val svcVersion: String
    ) {
        data class InterestReportData(
            val InterestAmount: String,
            val InterestRate: String,
            val LedgerDebit: String,
            val date: String
        )
    }
}
package com.acumengroup.mobile.model

data class CombinedResponse(
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
        val `data`: ArrayList<CombinedData>,
        val infoID: String,
        val msgID: String,
        val serverTime: String,
        val svcGroup: String,
        val svcName: String,
        val svcVersion: String
    ) {
        data class CombinedData(
            val Credit: String,
            val DRAMT: String,
            val Debit: String,
            val Exchange: String,
            val Narration: String,
            val ReferenceNo: String,
            val RunnBal: String,
            val SettlementNo: String,
            val VoucherType: String,
            val date: String
        )
    }
}
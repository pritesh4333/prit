package com.acumengroup.mobile.model

data class CDSLReturnResponse(
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
        val `data`: Data,
        val infoID: String,
        val msgID: String,
        val serverTime: String,
        val svcGroup: String,
        val svcName: String,
        val svcVersion: String
    ) {
        data class Data(
            val ReqId: String,
            val Status: String,
            val stockDetails: ArrayList<StockDetail>
        ) {
            data class StockDetail(
                val ISIN: String,
                val Symbol: String,
                val Quantity: String,
                val Remark: String,
                val ReqIdentifier: String,
                val ReqType: String,
                val Status: String,
                val token: String,
                val TxnId:String
            )
        }
    }
}
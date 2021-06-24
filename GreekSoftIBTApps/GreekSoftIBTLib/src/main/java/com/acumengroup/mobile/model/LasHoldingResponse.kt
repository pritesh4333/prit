package com.acumengroup.mobile.model

data class LasHoldingResponse(
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
        val `data`: ArrayList<LASData>,
        val infoID: String,
        val msgID: String,
        val serverTime: String,
        val svcGroup: String,
        val svcName: String,
        val svcVersion: String
    ) {
        data class LASData(
            val ISIN: String,
            val ScriptName: String,
            val PledgeQty: String,
            val FreeQty: String,
            val TotalQty: String,
            val TodaysSellingQty: String,
            val AvailableforPledging: String,
            val Unpledge: String,
            val Action: String
        )
    }
}
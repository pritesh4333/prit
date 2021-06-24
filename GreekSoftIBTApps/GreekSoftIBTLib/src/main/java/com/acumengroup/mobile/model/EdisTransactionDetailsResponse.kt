package com.acumengroup.mobile.model

data class EdisTransactionDetailsResponse(
        val ErrorCode: Int,
        val `data`: ArrayList<EdisTransactionDetailsData>,
        val message: String,
        val success: String
) {
    data class EdisTransactionDetailsData(
            val AuthorizedQty: String,
            val PledgedQty: String,
            val ScripName: String,
            val Date: String,
            val ErrorDesc: String,
            val FreeQty: String,
            val Status: String,
            val symbol: String
    )
}
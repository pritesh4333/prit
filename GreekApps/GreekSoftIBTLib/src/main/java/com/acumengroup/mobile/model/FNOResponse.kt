package com.acumengroup.mobile.model

data class FNOResponse(
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
            val `data`: ArrayList<FNOData>,
            val infoID: String,
            val msgID: String,
            val serverTime: String,
            val svcGroup: String,
            val svcName: String,
            val svcVersion: String
    ) {
        data class FNOData(
                val CLIENT_CODE: String = "0",
                val CLIENT_NAME: String,
                val FUT_BROKERAGE: String = "0",
                val FUT_TURN_TAX: String = "0",
                val GST: String = "0",
                val OPT_BROKERAGE: String = "0",
                val OPT_TURN_TAX: String = "0",
                val OTHER_CHRG: String = "0",
                val SAUDA_DATE: String = "0",
                val SEBI_TAX: String = "0",
                val STAMP_DUTY: String = "0",
                val STT: String = "0",
                val TOTAL: String = "0",
                val SETT_TYPE: String = "0",
                val TURNOVER: String = "0",
                val SETT_NO: String = "0",
                val TOTAL_CHRG: String = "0",
                val SBC_CHGR: String = "0",
                val TURN_TAX: String = "0",
                val STT_CHRG: String = "0",
                val KRISHI_CHRG: String = "0",
                val TransactionDate: String = "0",
                val Exchange: String = "0",
                val SettlementNo: String = "0",
                val BROKERAGE: String = "0",
                val Brokerage: String = "0",
                val SecurityTrxTax: String = "0",
                val StampDuty: String = "0",
                val SebiFees: String = "0",
                val TransactionCharges: String = "0",
                val OtherCharges: String = "0",
                val TotalCharges: String = "0",
                val TOTAL_FLAG: String = "0"

        )
    }
}
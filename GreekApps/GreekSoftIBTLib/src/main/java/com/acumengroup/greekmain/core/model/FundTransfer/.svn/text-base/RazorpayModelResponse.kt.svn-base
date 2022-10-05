package com.acumengroup.greekmain.core.model.FundTransfer

data class RazorpayModelResponse(
    val config: Config,
    val response: Response
) {
    data class Config(
        val app: Int,
        val label: Int,
        val message: Int
    )

    data class Response(
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
            val arrayData: List<ArrayData>
        ) {
            data class ArrayData(
                val callback_url: String,
                val company: String,
                val key: String,
                val respo: Respo,
                val mobile: String,
                val email: String,
                val MobileImg: String,
            ) {
                data class Respo(
                    val amount: String,
                    val amount_due: String,
                    val amount_paid: String,
                    val attempts: String,
                    val created_at: String,
                    val currency: String,
                    val entity: String,
                    val id: String,
                    val notes: List<Any>,
                    val offer_id: Any,
                    val receipt: Any,
                    val status: String
                )
            }
        }
    }
}
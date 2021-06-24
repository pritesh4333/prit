package com.acumengroup.mobile.model

data class SendAuthorizationResponse(
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
            val DPId: String,
            val ReqId: String,
            val Version: String,
            val response: String,
            val Url:String
        )
    }
}
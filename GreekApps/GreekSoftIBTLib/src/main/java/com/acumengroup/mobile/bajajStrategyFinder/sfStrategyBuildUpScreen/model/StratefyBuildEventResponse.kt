package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class StratefyBuildEventResponse(
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
            val ErrorCode: Int,
            val `data`: ArrayList<SBEventData>
        ) {
            data class SBEventData(
                val cData: String
            )
        }
    }
}
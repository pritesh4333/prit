package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class StrategyBuilderEventRequest(
        val request: Request

) {
    data class Request(
            val FormFactor: String,
            val `data`: Data,
            val requestType: String,
            val svcGroup: String,
            val svcName: String,
            val svcVersion: String
    ) {
        data class Data(
                val iErrorCode: String,
                val cGreekClientID: String
        )
    }
}
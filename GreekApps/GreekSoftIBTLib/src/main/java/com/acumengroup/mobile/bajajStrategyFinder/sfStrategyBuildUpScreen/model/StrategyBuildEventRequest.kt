package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class StrategyBuildEventRequest(
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
                val iErrorCode: Int,
                val cGreekClientId: String,
                val cExchange: String,
                val cClientCode: String,
                val cSymbol: String,
                val cStrategyName: String,
                val cExpiryDate: String
        )
    }
}
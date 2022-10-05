package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class SFBuildUpResponseVar(
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
            val data : Data,
            val infoID: String,
            val msgID: String,
            val serverTime: String,
            val svcGroup: String,
            val svcName: String,
            val svcVersion: String
    ) {
        data class Data(
                val ErrorCode: String,
                var data:ArrayList<Datanew> = ArrayList()
        )
        data class Datanew(

                val iReportType:Int,
                val iRange:Int,
                val dMarketRate:Double,
                val dDeltaNeutral:Double,
                val dDeltaVal:Double,
                val dThetaVal:Double,
                val dVegaVal:Double,
                val dGammaVal:Double,
                val dBalance:Double,
                val dExpBalance:Double

        )
    }
}

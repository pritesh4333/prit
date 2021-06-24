package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class SFBuildUpResponseAccounting(
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
        val  iReportType : Int,
        
        var  dPremium:Double,
        
        var  dStackR :Double,
        
        var  dStackU : Double,
        
        var  dFutureR : Double,
        
        var  dFutureU : Double,
        
        var  dOptionR : Double,
        
        var  dOptionU : Double,
        
        var  dEquity : Double,
        
        var  dBalance : Double,
        
        var  dFundUtilized  : Double,
        
        var  dTodaysExpense : Double,
        
        var  dTotalExpense  : Double,
        
        var  dTodaysInterest : Double,
        
        var  dTotalInterest : Double,
        
        var  dExposure : Double,
        
        var  dTotalInvestment :Double,
        
        var  dSpanMargin :Double,
        
        var  dExpoMargin  : Double,
        
        var  dOtherMargin  : Double,
        
        var  dTotalMargin :Double

        )
    }
}

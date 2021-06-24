package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class SrategyBuildData(
        val config: Config,
        val response: Response
) {
    data class Config(
            val app: String,
            val label: String,
            val message: String
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
                val ErrorCode: String,
                val `data`: ArrayList<BuildData>
        ) {
            data class BuildData(
                    var cExpiry: String,
                    var cInstrumentName: String,
                    var cIvType: String,
                    var cMaturityDt: String,
                    var cMktSegment: String,
                    var cMktType: String,
                    var cOptType: String,
                    var cStrategy: String,
                    var dBalance: String,
                    var dChangeInIv: String,
                    var dDateDiff: String,
                    var dDaysATP: String,
                    var dDaysMtom: String,
                    var dDelta: String,
                    var dDeltaNeutral: String,
                    var dDiffMtom: String,
                    var dDval: String,
                    var dExpBalance: String,
                    var dExpense: String,
                    var dFundUtilised: String,
                    var dGamma: String,
                    var dGval: String,
                    var dLastIV: String,
                    var dLtp: String,
                    var dMTOM: String,
                    var dMktRate: String,
                    var dMotm: String,
                    var dNetBalance: String,
                    var dPreLTP: String,
                    var dPreviousIv: String,
                    var dRealizedIV: String,
                    var dStrike: String,
                    var dThPrice: String,
                    var dTheroticalPrice: String,
                    var dTheta: String,
                    var dTimeValue: String,
                    var dTradeAmt: String,
                    var dTval: String,
                    var dVega: String,
                    var dVval: String,
                    var iDataType: String = "1",
                    var lAssetToken: String,
                    var lDaysUnit: String,
                    var lNetQty: String,
                    var lOurToken: String
            )
        }
    }
}
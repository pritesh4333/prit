package com.acumengroup.mobile.model.PandLReportDetailsModel

data class PandLReportDetailsResponse(
    var config: Config,
    var response: Response
) {
    data class Config(
        var app: Int, // 12
        var label: Int, // 8
        var message: Int // 9
    )

    data class Response(
        var ErrorCode: String, // 0
        var appID: String, // bc90bb525bc9739a9595bb9e176dab17
        var `data`: Data,
        var infoID: String, // 0
        var msgID: String, // a9bfad10-92ce-4fd7-968f- c25877c1bbb3
        var serverTime: String, // 1450349681701
        var svcGroup: String, // reports_bajaj
        var svcName: String, // getProfitAndLossDetails_Phillip
        var svcVersion: String // 1.0.0
    ) {
        data class Data(
            var financialData: FinancialData
        ) {
            data class FinancialData(
                var Message: String,
                var PnlDetail: List<PnlDetails>,
                var Success: Boolean // true
            ) {
                data class PnlDetails(
                    var BuyDate: String, // 31/07/2014
                    var BuyQty: String, // 500
                    var BuyRate: String, // 0.00
                    var ClRate: String, // 0.00
                    var ISIN: String, // INE238A01034
                    var PartyCode: String, // N00532
                    var PnlAmount: String, // 0
                    var PnlType: String, // NOTIONAL
                    var Scrip: String, // AXISBANK
                    var SellDate: String, // -
                    var SellQty: String, // 0
                    var SellRate: String // 0.00
                )
            }
        }
    }
}
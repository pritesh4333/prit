package com.acumengroup.mobile.model.PortfolioReportModel

data class PortfolioReportResponse(
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
        var svcGroup: String, // portfolio
        var svcName: String, // getPortfolioDetails_Phillip
        var svcVersion: String // 1.0.0
    ) {
        data class Data(
            var financialData: FinancialData
        ) {
            data class FinancialData(
                var porfolioDetailView: List<PorfolioDetailView>
            ) {
                data class PorfolioDetailView(
                    var BuyPrice: String, // 249.836622
                    var CurrentPrice: String, // 294.900000
                    var CurrentValue: String, // 265410.00
                    var Dividend: String, // 0.0000
                    var HoldQty: String, // 900.0000
                    var InvestedAmt: String, // 224852.9600
                    var RealisedCurrentYear: String, // 0.00
                    var Scrip: String, // VEDANTA LIMITED (EQ NEW RS.1/-)-INE205A01025
                    var Sector: String, // Aluminium
                    var Segment: String, // DIRECT - EQ
                    var SegmentId: String, // 1
                    var UnRelised: String // 40557.0400
                )
            }
        }
    }
}
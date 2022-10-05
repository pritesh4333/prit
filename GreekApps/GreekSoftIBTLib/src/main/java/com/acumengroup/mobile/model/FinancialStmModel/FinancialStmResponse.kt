package com.acumengroup.mobile.model.FinancialStmModel

data class FinancialStmResponse(
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
        var svcName: String, // getClientFinancial_Phillip
        var svcVersion: String // 1.0.0
    ) {
        data class Data(
            var financialData: FinancialData
        ) {
            data class FinancialData(
                var ClientFinSumSummaryView: List<ClientFinSumSummaryViews>
            ) {
                data class ClientFinSumSummaryViews(
                    var BSEMFSS: String, // 0
                    var MCDX: String, // 0
                    var MCDXO: String, // 0
                    var MTFCASH: String, // 0
                    var MTFCASHO: String, // 0
                    var NCDX: String, // 0
                    var NCDXO: String, // 0
                    var TOTAL_COMBINED: String, // 3049000.26
                    var TOTAL_COMBINEDO: String, // 0
                    var bseslbs: String, // 0
                    var bseslbso: String, // 0
                    var cash: String, // -341622.23
                    var casho: String, // 0
                    var currencyderivatives: String, // 0
                    var currencyderivativeso: String, // 0
                    var derivatives: String, // 3390622.49
                    var derivativeso: String, // 0
                    var descrip: String, // Ledger Balance
                    var long_name: String,
                    var nseslbs: String, // 0
                    var nseslbso: String, // 0
                    var partycode: String, // N00532
                    var sqroff_flag: String, // N
                    var t_sev_squareoff: String, // N
                    var tradingon: String // DIRECT
                )
            }
        }
    }
}
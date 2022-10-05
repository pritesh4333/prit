package com.acumengroup.mobile.model

data class sendAuthorizationRequest_NSDLResponse(
    var config: Config,
    var response: Response
) {
    data class Config(
        var app: Int,
        var label: Int,
        var message: Int
    )

    data class Response(
        var ErrorCode: Int,
        var appID: String,
        var `data`: Data,
        var infoID: String,
        var msgID: String,
        var serverTime: String,
        var svcGroup: String,
        var svcName: String,
        var svcVersion: String
    ) {
        data class Data(
            var Url: String,
            var channel: String,
            var orderReqDtls: String,
            var requestReference: String,
            var requestTime: String,
            var requestor: String,
            var requestorId: String,
            var signUrl: String,
            var signature: String,
            var transactionType: String
        )
    }
}
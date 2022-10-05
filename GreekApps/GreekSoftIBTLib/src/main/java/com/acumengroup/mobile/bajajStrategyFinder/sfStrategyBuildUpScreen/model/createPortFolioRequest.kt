package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model

data class CreatePortFolioRequest(
        var request: Request
) {
    data class Request(
            var FormFactor: String,
            var `data`: Data,
            var requestType: String,
            var svcGroup: String,
            var svcName: String,
            var svcVersion: String
    ) {
        data class Data(
                var cClientCode: String,
                var cGreekClientID: String,
                var cStrategyName: String,
                var iErrorCode: String
        )
    }
}
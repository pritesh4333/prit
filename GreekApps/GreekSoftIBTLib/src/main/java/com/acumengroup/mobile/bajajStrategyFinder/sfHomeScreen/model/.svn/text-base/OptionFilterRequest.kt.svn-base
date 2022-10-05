package com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model

data class OptionFilterRequest(
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
            var gcid: String,
            var getset: String,
            var gscid: String,
            var settingdata: String,
            var settingkey: String
        )
    }
}
package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model


data class OrderBookResponses(
        var ErrorCode: String,
        var `data`: ArrayList<OrdersBooksData>,
        var message: String,
        var success: String
) {
    data class OrdersBooksData(
            var tag: String = "",
            var BookType: String,
            var LegInfo: ArrayList<LegInfos>,
            var LogTime: String,
            var NoOfLegs: String,
//            var OrderFlagsOrderFlags: String,
            var OrderFlags: String,
            var action: String,
            var amount: String,
            var assetType: String,
            var cPANNumber: String,
            var clientCode: String,
            var dSLPrice: String,
            var dSLTPrice: String,
            var dTargetPrice: String,
            var description: String,
            var discQty: String,
            var errorCode: String,
            var exchange: String,
            var expiryDate: String,
            var filterKey: String,
            var flowType: String,
            var iStrategyId: String,
            var instrument: String,
            var isCancellable: String,
            var isEditable: String,
            var lIOMRuleNo: String,
            var lastModBy: String,
            var lgoodtilldate: String,
            var lotSize: String,
            var multiplier: String,
            var optionType: String,
            var ordID: String,
            var ordLife: String,
            var ordLiveDays: String,
            var ordModTime: String,
            var ordTime: String,//order placed by
            var orderType: String,
            var otype: String,
            var pendingQty: String,
            var pendingdiscQty: String,
            var price: String,
            var product: String,
            var qty: String,
            var remarks: String,
            var scripName: String,
            var status: String,// order status
            var strikePrice: String,
            var tickSize: String,
            var tmplotSize: String,
            var token: String,
            var tradeSymbol: String,
            var trdQty: String,
            var trigPrice: String,
            var uniqueID: String,//order no(1)
            var uniqueOrderID: String,//exchange no.(1)
            var userID: String
    ) {
        data class LegInfos(
                var cAccountNumber: String,
                var cCoverUncover: String,
                var cGiveupFlag: String,
                var cGreekClientId: String,
                var cOpenClose: String,
                var dLTP: String,
                var dOrderNumber: String,
                var dPrice: String,// (traded price)=lVolumeFilledToday>0 price else 0 -----            // order price--------
                var dTriggerPrice: String,//trigger price----------
                var description: String,//  description title-----------
                var iBuySell: String,
                var iGreekCode: String,
                var iLegNo: String,
                var iOrderFlags: String,// validity cmpre vwith iOrderFlags  bitwisae code----------
                var iOrderType: String,// order type  compare with code----------
                var iProductType: String, //product type compare with codee-----------
                var lDisclosedVol: String,// disclosed qty-----
                var lDisclosedVolRemaining: String,
                var lOurOrderNo: String,
                var lOurToken: String,
                var lTotalVolRemaining: String,//pending qty----------
                var lVolume: String,//order qty-------
                var lVolumeFilledToday: String,//trade qty------
                var scripName: String
        )
    }
}
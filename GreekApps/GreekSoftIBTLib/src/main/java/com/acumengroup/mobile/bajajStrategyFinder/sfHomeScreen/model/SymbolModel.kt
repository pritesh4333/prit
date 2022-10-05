package com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model

class SymbolModel {
    data class SymbolInfo(
            val ErrorCode: Int,
            val `data`: List<SymbolName>,
            val message: String,
            val success: String
    ) {
        data class SymbolName(
                val symbol: String
        )
    }

    data class ExpiryDate(
            val ErrorCode: Int,
            val `data`: ArrayList<Data>,
            val message: String,
            val success: String
    ) {
        data class Data(
                val expiry: String,
                val token: String,
                val lourtoken: String
        )
    }

    data class StrikePrice(
            val ErrorCode: Int,
            val `data`: ArrayList<Data>,
            val message: String,
            val success: String
    ) {
        data class Data(
                val strikeprice: String,
                val strike: String
        )
    }

    data class SymbolLTP(
            val ErrorCode: Int,
            val `data`:Data,
            val message: String,
            val success: String
    ) {
        data class Data(
                val change: String,
                val closingprice: String,
                val ltp: String
        )
    }
}
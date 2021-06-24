package com.acumengroup.mobile.bajajStrategyFinder.api

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("getSymbol/")
    fun getSymbol(): Call<ResponseBody>

    @GET
    fun getManualTradeEntryData(@Url url: String): Call<ResponseBody>

    @GET
    fun getArachneDataRequest(@Url url: String): Call<ResponseBody>

    @GET
    fun getExpiry(@Url url: String): Call<ResponseBody>

    @POST("strategyfinder/")
    fun PostscanSubmit(@Body scanRequest: String): Call<ResponseBody>

    @POST("TradeNotificationView/")
    fun TradeNotificationView(@Body scanRequest: String): Call<ResponseBody>

    @POST("StrategyBuilderEvent/")
    fun StrategyBuilderEvent(@Body scanRequest: String): Call<ResponseBody>

    @POST("getStrategyBuilderView/")
    fun getStrategyBuilderView(@Body scanRequest: String): Call<ResponseBody>

    @POST("TradeNotificationView/")
    fun getStrategyTradeNotificationView(@Body scanRequest: String): Call<ResponseBody>

    @POST("GetSetDefaultSetting/")
    fun PostOptionFilter(@Body scanRequest: String): Call<ResponseBody>

    @POST("strategyFinderView/")
    fun getStrategyfinderView(@Body scanRequest: String): Call<ResponseBody>

}
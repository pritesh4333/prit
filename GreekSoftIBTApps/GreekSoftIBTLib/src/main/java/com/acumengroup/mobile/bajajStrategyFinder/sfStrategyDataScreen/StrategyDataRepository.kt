package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen

import android.util.Log
import com.acumengroup.greekmain.core.market.OrderStreamingController
import com.acumengroup.greekmain.core.network.WSHandler
import com.acumengroup.mobile.bajajStrategyFinder.api.ApiClient
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.ScanPostRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.SFBuildUpResponse
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.ScanDataResponse
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.TradeNotificationRequest
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class StrategyDataRepository {

    private val TAG = "StrategyDataRepository"
    private var orderStreamingController: OrderStreamingController? = null


    fun postStrategyScanFilter(ScanPostRequest: ScanPostRequest, onResult: (isSuccess: Boolean, response: ScanDataResponse?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(ScanPostRequest))
        ApiClient.instance.PostscanSubmit(WSHandler.encodeToBase64(Gson().toJson(ScanPostRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, ScanDataResponse::class.java)
                        onResult(true, mMineUserEntity)

                    } catch (e: Exception) {
                        onResult(false, null)
                    }

                } else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                onResult(false, null)
            }

        })
    }

    fun postTradeNotifyView(tradeNotificationRequest: TradeNotificationRequest, onResult: (isSuccess: Boolean, response: SFBuildUpResponse?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(tradeNotificationRequest))
        ApiClient.instance.
        TradeNotificationView(WSHandler.encodeToBase64(Gson().toJson(tradeNotificationRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, SFBuildUpResponse::class.java)
                        onResult(true, mMineUserEntity)
                    } catch (e: Exception) {
                        onResult(false, null)

                    }
                } else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                onResult(false, null)
            }

        })
    }

    companion object {
        private var INSTANCE: StrategyDataRepository? = null
        fun getInstance() = INSTANCE
                ?: StrategyDataRepository().also {
                    INSTANCE = it
                }
    }
}
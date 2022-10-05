package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen

import android.util.Log
import com.acumengroup.greekmain.core.network.WSHandler
import com.acumengroup.mobile.bajajStrategyFinder.api.ApiClient
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.*
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.SFBuildUpResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class strategyBuildUpRepository {

    private var TAG: String = "strategyBuildUpRepository"

    fun StrategyBuildCloseEventRequest(tradeNotificationRequest: SBCloseEventRequest,
                                    onResult: (isSuccess: Boolean, response: StratefyBuildEventResponse?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(tradeNotificationRequest))
        ApiClient.instance.StrategyBuilderEvent(WSHandler.encodeToBase64(Gson().toJson(tradeNotificationRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, StratefyBuildEventResponse::class.java)
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
    fun StrategyBuildEventRequest(tradeNotificationRequest: StrategyBuildEventRequest,
                                    onResult: (isSuccess: Boolean, response: StratefyBuildEventResponse?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(tradeNotificationRequest))
        ApiClient.instance.StrategyBuilderEvent(WSHandler.encodeToBase64(Gson().toJson(tradeNotificationRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, StratefyBuildEventResponse::class.java)
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
    fun StrategyBuilderEventRequest(tradeNotificationRequest: StrategyBuilderEventRequest,
                                    onResult: (isSuccess: Boolean, response: StrategyNameResponse?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(tradeNotificationRequest))
        ApiClient.instance.StrategyBuilderEvent(WSHandler.encodeToBase64(Gson().toJson(tradeNotificationRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, StrategyNameResponse::class.java)
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

    fun CreatePortfolioRequest(createPortFolioRequest: CreatePortFolioRequest,
                               onResult: (isSuccess: Boolean, response: SFBuildUpResponse?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(createPortFolioRequest))
        ApiClient.instance.StrategyBuilderEvent(WSHandler.encodeToBase64(Gson().toJson(createPortFolioRequest))).enqueue(object : Callback<ResponseBody> {

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

    fun DeletePortfolioRequest(createPortFolioRequest: DeletePortfolioRequest,
                               onResult: (isSuccess: Boolean, response: SFBuildUpResponse?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(createPortFolioRequest))
        ApiClient.instance.StrategyBuilderEvent(WSHandler.encodeToBase64(Gson().toJson(createPortFolioRequest))).enqueue(object : Callback<ResponseBody> {

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

    fun StrategyBuilderViewRequest(strategyBuilderViewRequest: StrategyBuilderViewRequest,
                                   onResult: (isSuccess: Boolean, response: SrategyBuildData?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(strategyBuilderViewRequest))
        ApiClient.instance.getStrategyBuilderView(WSHandler.encodeToBase64(Gson().toJson(strategyBuilderViewRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, SrategyBuildData::class.java)
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

    fun StrategyTradeNotifyViewRequest(tradeNotificationRequest: AddPositionRequest,
                                   onResult: (isSuccess: Boolean, response: SrategyBuildData?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(tradeNotificationRequest))
        ApiClient.instance.getStrategyTradeNotificationView(WSHandler.encodeToBase64(Gson().toJson(tradeNotificationRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, SrategyBuildData::class.java)
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

    //      getStrategyBuilderView request is send to server when user add position from StrategyDataListFragment and navigate to Strategy Builder screen.
    fun StrategyBuilderChartViewRequest(strategyBuilderViewRequest: StrategyBuilderViewRequest,
                                        onResult: (isSuccess: Boolean, response: ChartGraphData?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(strategyBuilderViewRequest))

        ApiClient.instance.getStrategyBuilderView(WSHandler.encodeToBase64(Gson().toJson(strategyBuilderViewRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)

                        val mMineUserEntity = Gson().fromJson(responseStr, ChartGraphData::class.java)

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



    fun StrategyfinderChartViewRequest(strategyfinderViewRequest: SBChartRequest,
                                        onResult: (isSuccess: Boolean, response: GraphResponseData?) -> Unit) {

        Log.e(TAG, "Request=====>" + Gson().toJson(strategyfinderViewRequest))

        ApiClient.instance.getStrategyfinderView(WSHandler.encodeToBase64(Gson().toJson(strategyfinderViewRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)

                        val mMineUserEntity = Gson().fromJson(responseStr, GraphResponseData::class.java)

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
        private var INSTANCE: strategyBuildUpRepository? = null
        fun getInstance() = INSTANCE
                ?: strategyBuildUpRepository().also {
                    INSTANCE = it
                }
    }
}
package com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen

import android.util.Log
import com.acumengroup.greekmain.core.network.WSHandler.*
import com.acumengroup.mobile.bajajStrategyFinder.api.ApiClient
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.OptionFilterRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.SymbolModel
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.SFBuildUpResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenRepository {

    private val TAG = "HomeScreenRepository"

    fun getManualTradeEntryDataSymbol(iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String, onResult: (isSuccess: Boolean, response: SymbolModel.SymbolInfo?) -> Unit) {

        var url = "iRequestFor=" + iRequestFor + "&iExchange=" + iExchange + "&iSegment=" + iSegment + "&cSymbol=" + cSymbol + "&lExpiry=" + lExpiry + "&cOptionType=" + cOptionType + "&dStrike=" + dStrike
        Log.e(TAG, url)
        Log.e(TAG, encodeToBase64(url))
        ApiClient.instance.getManualTradeEntryData("getDetails?" + encodeToBase64(url)).enqueue(object : Callback<ResponseBody> {


            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {

                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)

                        val symbolInfo = Gson().fromJson(responseStr, SymbolModel.SymbolInfo::class.java)
                        onResult(true, symbolInfo)

                    } catch (e: Exception) {
                        onResult(false, null)

                    }
                } else
                    onResult(false, null)
                Log.e("error", "" + response)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.e("error", "" + t)
                onResult(false, null)
            }

        })
    }


    fun getManualTradeEntryDataExpiry(iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String, onResult: (isSuccess: Boolean, response: SymbolModel.ExpiryDate?) -> Unit) {

        var url = "iRequestFor=" + iRequestFor + "&iExchange=" + iExchange + "&iSegment=" + iSegment + "&cSymbol=" + cSymbol + "&lExpiry=" + lExpiry + "&cOptionType=" + cOptionType + "&dStrike=" + dStrike
        Log.e(TAG, url)
        Log.e(TAG, encodeToBase64(url))
        ApiClient.instance.getManualTradeEntryData("getDetails?" + encodeToBase64(url)).enqueue(object : Callback<ResponseBody> {


            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {

                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)

                        val expirylist = Gson().fromJson(responseStr, SymbolModel.ExpiryDate::class.java)
                        onResult(true, expirylist)

                    } catch (e: Exception) {
                        onResult(false, null)

                    }
                } else
                    onResult(false, null)
                Log.e("error", "" + response)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.e("error", "" + t)
                onResult(false, null)
            }

        })
    }

    fun getManualTradeEntryDataStrikeprice(iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String, onResult: (isSuccess: Boolean, response: SymbolModel.StrikePrice?) -> Unit) {

        var url = "iRequestFor=" + iRequestFor + "&iExchange=" + iExchange + "&iSegment=" + iSegment + "&cSymbol=" + cSymbol + "&lExpiry=" + lExpiry + "&cOptionType=" + cOptionType + "&dStrike=" + dStrike
        Log.e(TAG, url)
        Log.e(TAG, encodeToBase64(url))
        ApiClient.instance.getManualTradeEntryData("getDetails?" + encodeToBase64(url)).enqueue(object : Callback<ResponseBody> {


            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {

                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)

                        val expirylist = Gson().fromJson(responseStr, SymbolModel.StrikePrice::class.java)
                        onResult(true, expirylist)

                    } catch (e: Exception) {
                        onResult(false, null)

                    }
                } else
                    onResult(false, null)
                Log.e("error", "" + response)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.e("error", "" + t)
                onResult(false, null)
            }

        })
    }

    fun getManualTradeEntryDataToken(iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String, onResult: (isSuccess: Boolean, response: SymbolModel.ExpiryDate?) -> Unit) {

        var url = "iRequestFor=" + iRequestFor + "&iExchange=" + iExchange + "&iSegment=" + iSegment + "&cSymbol=" + cSymbol + "&lExpiry=" + lExpiry + "&cOptionType=" + cOptionType + "&dStrike=" + dStrike
        Log.e(TAG, url)
        Log.e(TAG, encodeToBase64(url))
        ApiClient.instance.getManualTradeEntryData("getDetails?" + encodeToBase64(url)).enqueue(object : Callback<ResponseBody> {


            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {

                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)

                        val expirylist = Gson().fromJson(responseStr, SymbolModel.ExpiryDate::class.java)
                        onResult(true, expirylist)

                    } catch (e: Exception) {
                        onResult(false, null)

                    }
                } else
                    onResult(false, null)
                Log.e("error", "" + response)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.e("error", "" + t)
                onResult(false, null)
            }

        })
    }

    fun getRepoList(onResult: (isSuccess: Boolean, response: SymbolModel.SymbolInfo?) -> Unit) {

        ApiClient.instance.getSymbol().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)

                        val symbolInfo = Gson().fromJson(responseStr, SymbolModel.SymbolInfo::class.java)
                        onResult(true, symbolInfo)
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


    fun getExpiryList(symbol: String, onResult: (isSuccess: Boolean, response: SymbolModel.ExpiryDate?) -> Unit) {

        ApiClient.instance.getExpiry("getOptionsExpirydate?" + encodeToBase64("symbol=" + encodeToBase64(symbol))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {
                    try {
                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, SymbolModel.ExpiryDate::class.java)

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

    fun getLTPList(symbol: String, timeStamp: String, onResult: (isSuccess: Boolean, response: SymbolModel.SymbolLTP?) -> Unit) {

        ApiClient.instance.getExpiry("getFutLTP?" + encodeToBase64("symbol=" + encodeToBase64(symbol) + "&expiry=" + timeStamp)).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val ltpDetails = Gson().fromJson(responseStr, SymbolModel.SymbolLTP::class.java)

                        if (ltpDetails.ErrorCode.equals(0)) {
                            onResult(true, ltpDetails)

                        } else {
                            onResult(false, null)

                        }
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

    fun getStrikePriceList(symbol: String, onResult: (isSuccess: Boolean, response: SymbolModel.StrikePrice?) -> Unit) {

        ApiClient.instance.getExpiry("getstrikeprice?" + encodeToBase64("symbol=" + encodeToBase64(symbol))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = decodeBase64(response.body()!!.string())
                        Log.e(TAG, "Response=====>" + responseStr)
                        val mMineUserEntity = Gson().fromJson(responseStr, SymbolModel.StrikePrice::class.java)

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

    fun postOptionFilter(optionFilterRequest: OptionFilterRequest, onResult: (isSuccess: Boolean, response: SFBuildUpResponse?) -> Unit) {

        ApiClient.instance.PostOptionFilter(encodeToBase64(Gson().toJson(optionFilterRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    try {
                        var responseStr: String = decodeBase64(response.body()!!.string())
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
        private var INSTANCE: HomeScreenRepository? = null
        fun getInstance() = INSTANCE
                ?: HomeScreenRepository().also {
                    INSTANCE = it
                }
    }


}
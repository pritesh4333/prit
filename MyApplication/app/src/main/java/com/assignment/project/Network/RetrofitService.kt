package com.assignment.project.Network

import com.assignment.project.Model.BusinessListModel
import com.assignment.project.Model.sample
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {
    @Headers(
        "Accept: application/json",
        "Cache-Control: max-age=640000",
        "Authorization: Bearer XPFgzKwZGK1yqRxHi0d5xsARFOLpXIvccQj5jekqTnysweGyoIfVUHcH2tPfGq5Oc9kwKHPkcOjk2d1Xobn7aTjOFeop8x41IUfVvg2Y27KiINjYPADcE7Qza0RkX3Yx")
    @GET("/v3/businesses/search?")
    fun getBusinessList(@Query("term") s: String,@Query("location")  s1: String, @Query("radius")
    s2: String,@Query("sort_by") s3: String,@Query("limit") s4: String): Call<java.util.ArrayList<sample>>

    //    @GET("search")
//    fun getBusinessList(@Query("term") uid: String,
//                        @Query("location") location: String,
//                        @Query("radius") radiuse: String,
//                        @Query("sort_by") sort_by:String,
//                        @Query("limit") limit: String,
//    ): Call<ArrayList<BusinessList>>
    companion object {

        var retrofitService: RetrofitService? = null

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.yelp.com")
                    .addConverterFactory(GsonConverterFactory.create())

                    .build()

                retrofitService = retrofit.create(RetrofitService::class.java)

            }
            return retrofitService!!
        }
    }
}
package com.haqueit.bd.kotlin_mvvm_jetpack.retrofit

import com.prit.mvvmkotlintest.model.User_info_Data
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("enamul95/user_person_info/master/user_info.json")
    fun getUser_infos( ): Single<List<User_info_Data>>
}
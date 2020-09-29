package com.haqueit.bd.kotlin_mvvm_jetpack.retrofit

import com.prit.mvvmkotlintest.model.User_info_Data
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {


    //https://raw.githubusercontent.com/enamul95/user_person_info/master/user_info.json

    var baseurl = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(Api::class.java);

    fun getuser_info_service(): Single<List<User_info_Data>> {
        return api.getUser_infos( )
    }

}
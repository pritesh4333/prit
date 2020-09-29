package com.prit.mvvmkotlintest.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haqueit.bd.kotlin_mvvm_jetpack.retrofit.ApiService
import com.prit.mvvmkotlintest.model.LoginuserModel
import com.prit.mvvmkotlintest.model.User_info_Data
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginresponse = MutableLiveData<String>()

    val names = ArrayList<LoginuserModel>()
    val namesList = MutableLiveData<ArrayList<LoginuserModel>>()


    private val apiService = ApiService()
    var user_info_list = MutableLiveData<List<User_info_Data>>();

    fun onClickLogin(){

        if (username.value.isNullOrEmpty()){
            loginresponse.value="Enter Username"
        }else if(password.value.isNullOrEmpty()){
            loginresponse.value="Enter Password"
        }else{
            loginresponse.value="Login Success"
            names.add( LoginuserModel(username.value.toString(),password.value.toString()))
            namesList.value=names


        }

    }

    fun onSpinnerSelect(parent: AdapterView<*>?,view:View?,pos:Int,id:Long){

        val name :String? = names[pos].username
        Toast.makeText(parent?.context,name,Toast.LENGTH_LONG).show()
    }

    @SuppressLint("CheckResult")
    fun getuser_info_list(activity: Activity) {



        apiService.getuser_info_service()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<User_info_Data>>() {
                override fun onSuccess(model: List<User_info_Data>) {

                    user_info_list.value = model;
                    Log.e("user_info_list--",model.toString())


                }

                override fun onError(e: Throwable) {
                    // pDialog?.hide()
                    Log.e("error--",e.toString())

                    e.printStackTrace()

                }

            })

    }

}
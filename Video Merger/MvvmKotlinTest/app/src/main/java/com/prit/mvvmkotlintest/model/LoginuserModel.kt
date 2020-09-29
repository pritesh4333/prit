package com.prit.mvvmkotlintest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginuserModel(usernames: String, passwords: String) :ViewModel(){

    var username = String()
    var password = String()

    init {
        this.username = usernames
        this.password = passwords


    }
}
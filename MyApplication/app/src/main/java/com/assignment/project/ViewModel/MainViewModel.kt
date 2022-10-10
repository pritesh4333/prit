package com.assignment.project.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.project.Model.BusinessListModel
import com.assignment.project.Model.sample
import com.assignment.project.Repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val movieList = MutableLiveData<ArrayList<sample>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies(s: String, s1: String, s2: String, s3: String, s4: String) {
        val response = repository.getAllMovies(s,s1,s2,s3,s4)
        response.enqueue(object : Callback<java.util.ArrayList<sample>> {
            override fun onResponse(call: Call<java.util.ArrayList<sample>>, response: Response<java.util.ArrayList<sample>>) {
//                movieList.postValue(response.body().businesses)
                Log.e("Response ",response.body().toString());
            }

            override fun onFailure(call: Call<java.util.ArrayList<sample>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}
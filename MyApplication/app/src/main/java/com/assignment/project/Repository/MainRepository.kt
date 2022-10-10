package com.assignment.project.Repository

import com.assignment.project.Network.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies(s: String, s1: String, s2: String, s3: String, s4: String) = retrofitService.getBusinessList(s,s1,s2, s3,s4);
}
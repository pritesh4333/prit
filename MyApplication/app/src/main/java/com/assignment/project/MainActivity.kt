package com.assignment.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.assignment.project.Adpter.MainAdapter
import com.assignment.project.Network.RetrofitService
import com.assignment.project.Repository.MainRepository
import com.assignment.project.ViewModel.MainViewModel
import com.assignment.project.ViewModel.MyViewModelFactory
import com.assignment.project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        //set recyclerview adapter
        binding.recyclerview.adapter = adapter

        viewModel.movieList.observe(this, Observer {

            Log.d(TAG, "movieList: $it")
            if( it==null){
                binding.emptyView.visibility= View.VISIBLE
            }else{
                binding.emptyView.visibility= View.INVISIBLE
                adapter.setMovieList(it)
            }

        })

        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })

        viewModel.getAllMovies("restaurants","NYC","500","distance","15")
    }
}
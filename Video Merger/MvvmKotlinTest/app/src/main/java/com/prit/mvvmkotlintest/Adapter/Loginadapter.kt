package com.prit.mvvmkotlintest.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.prit.mvvmkotlintest.R
import com.prit.mvvmkotlintest.databinding.RecyclerItemBinding
import com.prit.mvvmkotlintest.model.LoginuserModel

class Loginadapter :
    RecyclerView.Adapter<Loginadapter.DeveloperViewHolder>() {

    private var mDeveloperModel: ArrayList<LoginuserModel>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<RecyclerItemBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.recycler_item, viewGroup, false
        )

        return DeveloperViewHolder(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mDeveloperModel!![i]


        mDeveloperViewHolder.mDeveloperListItemBinding.loginuser = currentStudent


    }

    override fun getItemCount(): Int {
        return if (mDeveloperModel != null) {
            mDeveloperModel!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: ArrayList<LoginuserModel>) {
        this.mDeveloperModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mDeveloperListItemBinding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(mDeveloperListItemBinding.root)
}
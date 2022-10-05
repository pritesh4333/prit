package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.SrategyBuildData
import com.acumengroup.mobile.databinding.StrategyBuildRowBinding
import com.acumengroup.mobile.databinding.StrategyDataRowBinding
import com.acumengroup.mobile.databinding.StrategyRowBinding
import kotlin.collections.ArrayList


class SFBuildUpAdapter(private var listener: OnCategoryClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCategoryList = ArrayList<SrategyBuildData.Response.Data.BuildData>()

    fun setAppList(categoryModel: ArrayList<SrategyBuildData.Response.Data.BuildData>) {

        mCategoryList.clear()
        mCategoryList.addAll(categoryModel)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        Log.d("LIST_SIZE", "" + mCategoryList.size)
        return mCategoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val appInfo = mCategoryList[position]

        (holder as SFBuildUpAdapter.RecyclerHolderCatIcon).bind(appInfo, listener, position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = StrategyBuildRowBinding.inflate(layoutInflater, parent, false)

        return RecyclerHolderCatIcon(applicationBinding)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(view: View, position: Int)
    }

    inner class RecyclerHolderCatIcon(private var applicationBinding: StrategyBuildRowBinding) : RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind(appInfo: SrategyBuildData.Response.Data.BuildData, listener: OnCategoryClickListener?, position: Int) {
            applicationBinding.buildData = appInfo

        }
    }
}
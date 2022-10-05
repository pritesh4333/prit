package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.view


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.ScanDataResponse
import com.acumengroup.mobile.databinding.StrategyDataRowBinding
import com.acumengroup.mobile.databinding.StrategyRowBinding
import kotlin.collections.ArrayList


class StrategyDataAdapter(private var listener: OnCategoryClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCategoryList = ArrayList<ScanDataResponse.Response.Data.ScanData>()
    private val checkedIdList = ArrayList<String>()

    fun setAppList(categoryModel: ArrayList<ScanDataResponse.Response.Data.ScanData>) {

        mCategoryList.clear()
        mCategoryList.addAll(categoryModel)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        Log.d("LIST_SIZE", "" + mCategoryList.size)
        return mCategoryList.size
    }

    fun getItemScanData(position: Int): ScanDataResponse.Response.Data.ScanData {
        Log.d("LIST_SIZE", "" + mCategoryList.size)
        return mCategoryList.get(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val appInfo = mCategoryList[position]


        (holder as StrategyDataAdapter.RecyclerHolderCatIcon).bind(appInfo, listener, position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = StrategyDataRowBinding.inflate(layoutInflater, parent, false)


        return RecyclerHolderCatIcon(applicationBinding)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(view: View, position: Int)
    }

    inner class RecyclerHolderCatIcon(private var applicationBinding: StrategyDataRowBinding) : RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind(appInfo: ScanDataResponse.Response.Data.ScanData, listener: OnCategoryClickListener?, position: Int) {
            applicationBinding.scanData = appInfo

            applicationBinding.txtTrade.setOnClickListener {

                listener!!.onCategoryClick(it, position)
            }
        }
    }
}
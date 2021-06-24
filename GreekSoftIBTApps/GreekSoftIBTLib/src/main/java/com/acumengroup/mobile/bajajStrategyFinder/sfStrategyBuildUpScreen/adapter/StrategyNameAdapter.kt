package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.StrategyNameResponse
import com.acumengroup.mobile.databinding.StrategyNameRowBinding
import kotlin.collections.ArrayList


class StrategyNameAdapter(private var listener: OnCategoryClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCategoryList = ArrayList<StrategyNameResponse.Response.Data.StrategyName>()

    fun setAppList(categoryModel: ArrayList<StrategyNameResponse.Response.Data.StrategyName>) {
        mCategoryList.clear()
        mCategoryList.addAll(categoryModel)
        notifyDataSetChanged()
    }

    fun getChekedStrategyNameList(): ArrayList<StrategyNameResponse.Response.Data.StrategyName>
    {
        return mCategoryList
    }

    override fun getItemCount(): Int {
        Log.d("LIST_SIZE", "" + mCategoryList.size)
        return mCategoryList.size
    }

    fun getItemScanData(position: Int): StrategyNameResponse.Response.Data.StrategyName {
        Log.d("LIST_SIZE", "" + mCategoryList.size)
        return mCategoryList.get(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val appInfo = mCategoryList[position]


        (holder as StrategyNameAdapter.RecyclerHolderCatIcon).bind(appInfo, listener, position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = StrategyNameRowBinding.inflate(layoutInflater, parent, false)
        if (AccountDetails.getThemeFlag(parent.context).equals("white")) {
            applicationBinding.chkName.setButtonDrawable(parent.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            applicationBinding.cExpiry.setTextColor(parent.context.resources.getColor(R.color.black))
            applicationBinding.cStrategy.setTextColor(parent.context.resources.getColor(R.color.black))
            applicationBinding.strategyNameLayout.setBackgroundColor(parent.context.resources.getColor(R.color.white))
        }
        return RecyclerHolderCatIcon(applicationBinding)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(isChecked: Boolean, strategyName: StrategyNameResponse.Response.Data.StrategyName)
    }

    inner class RecyclerHolderCatIcon(private var applicationBinding: StrategyNameRowBinding) : RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind(appInfo: StrategyNameResponse.Response.Data.StrategyName, listener: OnCategoryClickListener?, position: Int) {
            applicationBinding.sfName = appInfo

            applicationBinding.chkName.setOnCheckedChangeListener { compoundButton, b ->

                mCategoryList.get(position).isChecked = b
                listener!!.onCategoryClick(b, mCategoryList.get(position))
            }
        }
    }
}
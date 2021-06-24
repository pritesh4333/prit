package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.view


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.StrategyName
import com.acumengroup.mobile.databinding.DifferStrategyRowBinding
import kotlin.collections.ArrayList


class DifferCategoryAdapter(private var listener: OnCategoryClickListener, var isViewNeed: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCategoryList = ArrayList<StrategyName>()
    private var checkedPosition: Int = 0

    fun setAppList(categoryModel: ArrayList<StrategyName>) {
        mCategoryList.clear()
        mCategoryList.addAll(categoryModel)
        notifyDataSetChanged()
    }

    fun getCheckedDiffer(): String {


        if (mCategoryList.size > 0 && checkedPosition != 0)
            return mCategoryList.get(checkedPosition - 1).StrategyName
        else
            return ""
    }

    fun getCheckedIntervalIndexDiffer(): String {

        return checkedPosition.toString()
    }

    override fun getItemCount(): Int {
        Log.d("LIST_SIZE", "" + mCategoryList.size)
        return mCategoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val appInfo = mCategoryList[position]
        (holder as DifferCategoryAdapter.RecyclerHolderCatIcon).bind(appInfo, listener, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = DifferStrategyRowBinding.inflate(layoutInflater, parent, false)
        if (AccountDetails.getThemeFlag(parent.context).equals("white")) {
            applicationBinding.checkboxStrategy.setButtonDrawable(parent.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            applicationBinding.txtStrategy.setTextColor(parent.context.resources.getColor(R.color.black))
            applicationBinding.linearLayoutStrategy.setBackgroundColor(parent.context.resources.getColor(R.color.white))
        }
        return RecyclerHolderCatIcon(applicationBinding)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(position: Int)
    }

    inner class RecyclerHolderCatIcon(private var applicationBinding: DifferStrategyRowBinding) :
            RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind(appInfo: StrategyName, listener: OnCategoryClickListener?, position: Int) {

            applicationBinding.diffsName = appInfo

            if (isViewNeed) {
                applicationBinding.view1.visibility = View.VISIBLE
                applicationBinding.view2.visibility = View.VISIBLE

            } else {
                applicationBinding.view1.visibility = View.GONE
                applicationBinding.view2.visibility = View.GONE
            }

            applicationBinding.checkboxStrategy.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->


                checkedPosition = position + 1
                mCategoryList.get(position).StrategyCheck = true


                for (i in mCategoryList.indices) {
                    if (i == position) {
                        mCategoryList.get(position).StrategyCheck = true
                    } else {
                        mCategoryList.get(i).StrategyCheck = false
                    }
                }
                notifyDataSetChanged()

            })

        }


    }
}
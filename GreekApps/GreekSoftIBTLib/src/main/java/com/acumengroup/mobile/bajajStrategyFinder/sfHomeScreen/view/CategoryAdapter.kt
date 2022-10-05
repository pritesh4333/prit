package com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.view


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.StrategyName
import com.acumengroup.mobile.databinding.StrategyRowBinding
import kotlinx.android.synthetic.main.sf_filter_layout.view.*
import kotlin.collections.ArrayList


class CategoryAdapter(private var listener: OnCategoryClickListener, var isViewNeed: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCategoryList = ArrayList<StrategyName>()
    private val checkedIdList = ArrayList<String>()

    fun setAppList(categoryModel: ArrayList<StrategyName>) {

        mCategoryList.clear()
        checkedIdList.clear()
        mCategoryList.addAll(categoryModel)
        notifyDataSetChanged()

        mCategoryList.forEach {
            if (it.StrategyCheck) {
                checkedIdList.add(it.StrategyId)
            }
        }
    }

    fun setAllCheckedList(isAllChecked: Boolean) {

        if (isAllChecked) {
            mCategoryList.forEach {

                it.StrategyCheck = true

            }
        } else {
            mCategoryList.forEach {

                it.StrategyCheck = false
            }
        }

        notifyDataSetChanged()
    }


    fun getCheckedIDList(): ArrayList<String> {

        checkedIdList.clear()
        mCategoryList.forEach {
            if (it.StrategyCheck) {

                if (!checkedIdList.contains(it.StrategyId))
                    checkedIdList.add(it.StrategyId)
            }
        }
        return checkedIdList
    }

    fun getCheckedCategoryList(): ArrayList<StrategyName> {

        return mCategoryList
    }

    override fun getItemCount(): Int {
        Log.d("LIST_SIZE", "" + mCategoryList.size)
        return mCategoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val appInfo = mCategoryList[position]
        (holder as CategoryAdapter.RecyclerHolderCatIcon).bind(appInfo, listener, position)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = StrategyRowBinding.inflate(layoutInflater, parent, false)
        if (AccountDetails.getThemeFlag(parent.context).equals("white")) {
            applicationBinding.checkboxStrategy.setButtonDrawable(parent.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            applicationBinding.txtStrategy.setTextColor(parent.context.resources.getColor(R.color.black))
            applicationBinding.linearLayoutStrategy.setBackgroundColor(parent.context.resources.getColor(R.color.white))
        }
        return RecyclerHolderCatIcon(applicationBinding,parent.context)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(position: Int)
    }


    inner class RecyclerHolderCatIcon(private var applicationBinding: StrategyRowBinding, context: Context) : RecyclerView.ViewHolder(applicationBinding.root) {


        fun bind(appInfo: StrategyName, listener: OnCategoryClickListener?, position: Int) {
            applicationBinding.sName = appInfo

            if (isViewNeed) {
                applicationBinding.view1.visibility = View.VISIBLE
                applicationBinding.view2.visibility = View.VISIBLE



            } else {

                applicationBinding.view1.visibility = View.GONE
                applicationBinding.view2.visibility = View.GONE
            }
            applicationBinding.checkboxStrategy.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->

                mCategoryList.get(position).StrategyCheck = b
            })

        }


    }
}
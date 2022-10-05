package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuild.view.SfBuildVarFragment
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view.SfBuildAccountingFragment
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view.SfBuildGraphFragment
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view.SfBuildReportFragment


class ViewPagerAdapter( fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {

        when (position) {
            0 ->return SfBuildGraphFragment()
            1 -> return SfBuildReportFragment()
            2 -> return SfBuildVarFragment()
            3-> return  SfBuildAccountingFragment()

            else -> return SfBuildGraphFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return 4
    }
}
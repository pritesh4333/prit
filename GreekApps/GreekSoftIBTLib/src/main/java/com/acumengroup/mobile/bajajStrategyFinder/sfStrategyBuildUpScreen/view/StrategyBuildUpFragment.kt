package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.greekmain.core.constants.GreekConstants
import com.acumengroup.greekmain.core.constants.LabelConfig
import com.acumengroup.mobile.GreekBaseFragment
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.ScanPostRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter.SFBuildUpAdapter
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter.StrategyBuildTableAdapter
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter.ViewPagerAdapter
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.SBChartRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.SrategyBuildData
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.viewModel.StrategyBuildUpViewModel
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.ScanDataResponse
import com.acumengroup.mobile.databinding.FragmentStrategyBuildUpBinding
import com.acumengroup.ui.GreekDialog
import com.acumengroup.ui.button.GreekButton
import com.acumengroup.greekmain.util.Util
import com.acumengroup.pagersliderlib.PagerSlidingTabStrip
import com.acumengroup.columnsliderlib.AdaptiveTableLayout
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter
import com.acumengroup.columnsliderlib.OnItemClickListener
import com.acumengroup.columnsliderlib.OnItemLongClickListener
import com.acumengroup.mobile.GreekBaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_sfscan.*
import kotlinx.android.synthetic.main.fragment_strategy_build_up.*
import kotlinx.android.synthetic.main.fragment_strategy_build_up.mainLayout


class StrategyBuildUpFragment : GreekBaseFragment(), SFBuildUpAdapter.OnCategoryClickListener, OnItemClickListener, OnItemLongClickListener {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewDataBinding: FragmentStrategyBuildUpBinding
    lateinit var viewmodel: StrategyBuildUpViewModel
    lateinit var scanPostRequest: ScanPostRequest
    lateinit var scanDataRespnse: ScanDataResponse.Response.Data.ScanData
    private var tabLayout: PagerSlidingTabStrip? = null
    val spannable = SpannableStringBuilder()
    var cSymbol = ""
    var dStrikeDiff = "0"
    var lExpiry = "0"
    private var mTableLayout: AdaptiveTableLayout? = null
    private var mTableAdapter: LinkedAdaptiveTableAdapter<*>? = null
    var dataSourceArrayList = ArrayList<SrategyBuildData.Response.Data.BuildData>()
    private var textColorPositive = 0
    private var textColorNegative = 0
    private lateinit var viewpager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("")
            scanPostRequest = Util.getGsonParser().fromJson(it.getString("ScanData"), ScanPostRequest::class.java)
            scanDataRespnse = Util.getGsonParser().fromJson(it.getString("scanDataResponse"), ScanDataResponse.Response.Data.ScanData::class.java)


            cSymbol = scanPostRequest.request.data.cSymbol
            dStrikeDiff = scanPostRequest.request.data.dStrikeDiff
            lExpiry = scanDataRespnse.lExpiry


            var splitStringArray: ArrayList<String> = ArrayList()

            if (scanDataRespnse.cTradeDetails.contains("\n")) {
                splitStringArray = scanDataRespnse.cTradeDetails.split("\n") as ArrayList<String>


            } else {

                splitStringArray.add(scanDataRespnse.cTradeDetails)
            }
            var tokenlist: ArrayList<SBChartRequest.Request.Data.TokenInf> = ArrayList()
//            var list = null

            if (splitStringArray.size > 0) {

                var unit = splitStringArray[0].split(" ")[1];

                if (splitStringArray[0].contains("BUY")) {

                    var list = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_0.toDouble(), scanDataRespnse.iToken_0.toLong(), unit.toLong())
                    tokenlist.add(list)
                } else {
                    var list = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_0.toDouble(), scanDataRespnse.iToken_0.toLong(), -unit.toLong())
                    tokenlist.add(list)
                }
                var unit1 = splitStringArray[1].split(" ")[1];
                if (splitStringArray[1].contains("BUY")) {
                    var list1 = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_1.toDouble(), scanDataRespnse.iToken_1.toLong(), unit1.toLong())
                    tokenlist.add(list1)
                } else {
                    var list1 = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_1.toDouble(), scanDataRespnse.iToken_1.toLong(), -unit1.toLong())
                    tokenlist.add(list1)
                }


                if (scanDataRespnse.iToken_2 != "0") {
                    var unit3 = splitStringArray[2].split(" ")[1];
                    if (splitStringArray[2].contains("BUY")) {
                        var list2 = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_2.toDouble(), scanDataRespnse.iToken_2.toLong(), unit3.toLong())
                        tokenlist.add(list2)
                    } else {
                        var list2 = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_2.toDouble(), scanDataRespnse.iToken_2.toLong(), -unit3.toLong())
                        tokenlist.add(list2)
                    }

                }
                if (scanDataRespnse.iToken_3 != "0") {
                    var unit3 = splitStringArray[3].split(" ")[1];
                    if (splitStringArray[3].contains("BUY")) {
                        var list3 = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_3.toDouble(), scanDataRespnse.iToken_3.toLong(), unit3.toLong())
                        tokenlist.add(list3)
                    } else {
                        var list3 = SBChartRequest.Request.Data.TokenInf(scanDataRespnse.dRate_3.toDouble(), scanDataRespnse.iToken_3.toLong(), -unit3.toLong())
                        tokenlist.add(list3)
                    }
                }


                var arrayListToken = Gson().toJson(tokenlist)
                Util.getPrefs(activity).edit().putString("tokrnlist", arrayListToken).commit()
                //okk


                val str1 = "BUY"
                val str2 = "SELL"
                var index = 0

                if (splitStringArray.size > 0) {

                    splitStringArray.forEach {

                        if (it.contains(str1)) {
                            spannable.append(it)
                            spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), index, index + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            spannable.append("\n")
                        }
                        index = spannable.length

                        if (it.contains(str2)) {
                            spannable.append(it)
                            var startIndex = it.indexOf(str2)

                            if (index > 0) {
                                index = index - 1
                            }
                            spannable!!.setSpan(ForegroundColorSpan(Color.RED), index, index + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            spannable.append("\n")
                        }
                        index = spannable.length
                    }
                }

            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewDataBinding = DataBindingUtil.inflate<FragmentStrategyBuildUpBinding>(inflater, R.layout.fragment_strategy_build_up, container, false)
        viewmodel = ViewModelProviders.of(this@StrategyBuildUpFragment).get(StrategyBuildUpViewModel::class.java)

        AccountDetails.currentFragment = GreekConstants.NAV_TO_STRATEGY_FINDER

        viewDataBinding.scanBuildViewModel = viewmodel
        viewDataBinding.setLifecycleOwner { lifecycle }
        if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
            textColorPositive = R.color.whitetheambuyColor
        } else {
            textColorPositive = R.color.dark_green_positive
        }
        textColorNegative = R.color.dark_red_negative


        return viewDataBinding.root
    }


    private fun setUpViews(view: View) {

        if (spannable != null) {
            txt_tradDetails.setText(spannable)
        }
        setTheme();
    }

    private fun setTheme() {
        if (AccountDetails.getThemeFlag(mainActivity).equals("white", ignoreCase = true)) {
            mainLayout.setBackgroundColor(resources.getColor(R.color.white))
            imgICON.setBackground(resources.getDrawable(R.drawable.ic_filter))
            stripLAyout.setBackground(resources.getDrawable(R.drawable.whitegradient_center))
            txt_tradDetails.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            empty_view.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            dnTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_dl.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            dVal.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_deltaVal.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            thetaVAl.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_thetaVal.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            vegaVAlTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_vegaVal.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            gamaTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_gammaVal.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            fundTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_fundUtilised.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            balanceTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_balance.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            responseTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_expense.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            mtomTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_mTom.setTextColor(resources.getColor(AccountDetails.textColorDropdown))


        }
    }


    override fun onStop() {
        super.onStop()

        viewDataBinding.scanBuildViewModel?.StrategyBuilderCloseEventRequest()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dMidStrike = Util.getPrefs(view.context).getString("LTP", "0.0")
        var SymbolList = Util.getPrefs(activity).getString("SymbolList", "")


        viewDataBinding.scanBuildViewModel?.StrategyBuilderEventForStorage(AccountDetails.getUsername(view.context), cSymbol, dStrikeDiff, dMidStrike!!)
        //viewDataBinding.scanBuildViewModel?.StrategyBuilderView(AccountDetails.getUsername(view.context), cSymbol, dStrikeDiff, dMidStrike)
        viewDataBinding.scanBuildViewModel?.fetchSymbolList(SymbolList)
        mTableLayout = view.findViewById(R.id.tableLayout)

        setupObservers()
        setUpViews(view)
        empty_view.visibility = View.VISIBLE
        mTableLayout!!.visibility = View.GONE

        pagerClickListner(viewDataBinding.root)


    }


    private fun pagerClickListner(view: View) {

        view.findViewById<GreekButton>(R.id.graph_btn).setOnClickListener {
            view.findViewById<GreekButton>(R.id.graph_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj)
            view.findViewById<GreekButton>(R.id.report_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.var_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.accounting_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<ViewPager>(R.id.sfbuildviewPager).setCurrentItem(0, true)
        }
        view.findViewById<GreekButton>(R.id.report_btn).setOnClickListener {
            view.findViewById<GreekButton>(R.id.report_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj)
            view.findViewById<GreekButton>(R.id.graph_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.var_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.accounting_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<ViewPager>(R.id.sfbuildviewPager).setCurrentItem(1, true)

        }
        view.findViewById<GreekButton>(R.id.var_btn).setOnClickListener {
            view.findViewById<GreekButton>(R.id.var_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj)
            view.findViewById<GreekButton>(R.id.graph_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.report_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.accounting_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<ViewPager>(R.id.sfbuildviewPager).setCurrentItem(2, true)

        }
        view.findViewById<GreekButton>(R.id.accounting_btn).setOnClickListener {
            view.findViewById<GreekButton>(R.id.accounting_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj)
            view.findViewById<GreekButton>(R.id.graph_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.report_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<GreekButton>(R.id.var_btn).background = activity?.getDrawable(R.drawable.single_line_border_bajaj_gray)
            view.findViewById<ViewPager>(R.id.sfbuildviewPager).setCurrentItem(3, true)

        }


    }

    private fun setupObservers() {

        viewDataBinding.scanBuildViewModel?.refreshBottomTabs?.observe(viewLifecycleOwner, Observer {

            if (it) {
                viewpager = viewDataBinding.root.findViewById<ViewPager>(R.id.sfbuildviewPager)
                viewpager.offscreenPageLimit = 4
                val adapter = ViewPagerAdapter(activity!!.supportFragmentManager)
                viewpager.adapter = adapter
                viewpager.setOnTouchListener { v, event -> true }

            }
        })

        viewDataBinding.scanBuildViewModel?.selectedSymbolLive?.observe(viewLifecycleOwner, Observer {

            Util.getPrefs(activity).edit().putString("selectedSymbol", it).commit()
        })

        viewDataBinding.scanBuildViewModel?.selectedExpiryDateLive?.observe(viewLifecycleOwner, Observer {

            Util.getPrefs(activity).edit().putString("selectedExpiryDate", it).commit()
        })

        viewDataBinding.scanBuildViewModel?.selectedStrategyLive?.observe(viewLifecycleOwner, Observer {
            Util.getPrefs(activity).edit().putString("selectedStrategy", it).commit()
        })


        viewDataBinding.scanBuildViewModel?.addedPortFolio?.observe(viewLifecycleOwner, Observer {
            if (it) {
                GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK, "Portfolio Added.", "OK", true, null)
            } else
                GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK, "Portfolio not Added.", "OK", true, null)

        })
        viewDataBinding.scanBuildViewModel?.deletePortFolio?.observe(viewLifecycleOwner, Observer {
            if (it) {
                GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK, "Portfolio Deleted.", "OK", true, null)
            } else
                GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK, "Portfolio not Deleted.", "OK", true, null)

        })
        viewDataBinding.scanBuildViewModel?.dataLoading?.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgress()
            } else
                hideProgress()
        })

        viewDataBinding.scanBuildViewModel!!.srategyBuildDataLive.observe(viewLifecycleOwner, Observer {

            if (it.size > 0) {

                empty_view.visibility = View.GONE
                mTableLayout!!.visibility = View.VISIBLE
                dataSourceArrayList = it
                it.add(0, SrategyBuildData.Response.Data.BuildData("", "", "",
                        "", "", "", "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "", "", "", "",
                        "", ""))
                mTableAdapter = StrategyBuildTableAdapter(context!!, it)
                mTableAdapter!!.setOnItemClickListener(this)
                mTableAdapter!!.setOnItemLongClickListener(this)
                mTableLayout!!.setAdapter(mTableAdapter)
                mTableAdapter!!.notifyDataSetChanged()

            } else {

                empty_view.visibility = View.VISIBLE
                mTableLayout!!.visibility = View.GONE
            }
        })
        viewDataBinding.scanBuildViewModel!!.srategyChartDataLive.observe(viewLifecycleOwner, Observer {

            Log.e("StrategyBuildUpFragment", "ChartDataList" + it.get(0).dMarketRate)
        })

        viewDataBinding.scanBuildViewModel!!.dlTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_dl.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_dl.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.deltaValTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_deltaVal.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_deltaVal.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.thetaValTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_thetaVal.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_thetaVal.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.vegaValTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_vegaVal.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_vegaVal.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.gammaValTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_gammaVal.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_gammaVal.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.fundUtilisedTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_fundUtilised.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_fundUtilised.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.balanceTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_balance.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_balance.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.expenseTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_expense.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_expense.setTextColor(resources.getColor(textColorPositive))

            }

        })
        viewDataBinding.scanBuildViewModel!!.mTomTextColor.observe(viewLifecycleOwner, Observer {

            if (it) {
                txt_mTom.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_mTom.setTextColor(resources.getColor(textColorPositive))

            }

        })
    }

    override fun onCategoryClick(view: View, position: Int) {

    }

    override fun onColumnHeaderClick(column: Int) {

    }

    override fun onRowHeaderClick(row: Int) {
        var lOurToken = dataSourceArrayList.get(row).lOurToken
        var price = dataSourceArrayList.get(row).dTheroticalPrice
        var unit = dataSourceArrayList.get(row).lNetQty
        var expiryDate = dataSourceArrayList.get(row).cExpiry
        viewDataBinding.scanBuildViewModel?.showManualTradeEntryView(activity, cSymbol, expiryDate, price, unit, lOurToken)
//       getDetails request is send to server and Dialog box of ManualTradeEntry display
    }

    override fun onLeftTopHeaderClick() {

    }

    override fun onItemClick(row: Int, column: Int) {
        var lOurToken = dataSourceArrayList.get(row).lOurToken
        var price = dataSourceArrayList.get(row).dTheroticalPrice
        var unit = dataSourceArrayList.get(row).lNetQty
        var expiryDate = dataSourceArrayList.get(row).cExpiry
        viewDataBinding.scanBuildViewModel?.showManualTradeEntryView(activity, cSymbol, expiryDate, price, unit, lOurToken)
        //       getDetails request is send to server and Dialog box of ManualTradeEntry display
    }

    override fun onItemLongClick(row: Int, column: Int) {

    }

    override fun onLeftTopHeaderLongClick() {

    }


}

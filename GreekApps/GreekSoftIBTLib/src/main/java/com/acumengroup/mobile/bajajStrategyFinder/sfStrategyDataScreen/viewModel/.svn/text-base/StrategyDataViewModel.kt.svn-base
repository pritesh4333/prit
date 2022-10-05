package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.viewModel

import android.app.AlertDialog
import android.graphics.Color
import android.os.Trace.isEnabled
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.greekmain.core.market.OrderStreamingController
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.ScanPostRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.StrategyName
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.view.CategoryAdapter
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.viewModel.SFscanFragmentViewModel
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.StrategyDataRepository
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.*
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.view.DifferCategoryAdapter
import com.acumengroup.ui.GreekDialog
import com.acumengroup.ui.button.GreekButton
import com.acumengroup.greekmain.util.Util
import com.acumengroup.greekmain.util.date.DateTimeFormatter
import com.acumengroup.mobile.GreekBaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.sf_day_order_layout.view.*
import kotlinx.android.synthetic.main.sf_filter_layout.view.*
import kotlinx.android.synthetic.main.sf_ioc_order_layout.view.*
import kotlinx.android.synthetic.main.sf_ioc_order_layout.view.spinner_leg
import kotlinx.android.synthetic.main.sf_ioc_order_layout.view.txt_title
import org.json.JSONObject
import java.lang.Exception
import java.lang.reflect.Type


class StrategyDataViewModel : SFscanFragmentViewModel(), DifferCategoryAdapter.OnCategoryClickListener {

    var strategyDataListLive = MutableLiveData<ArrayList<ScanDataResponse.Response.Data.ScanData>>()

    lateinit var marketOutLook: String
    val weFound = MutableLiveData<String>()
    val symbolWithExpiry = MutableLiveData<String>()
    lateinit var betweenDiffr: String
    var responseScanData = ArrayList<ScanDataResponse.Response.Data.ScanData>()
    var filterScanData = ArrayList<ScanDataResponse.Response.Data.ScanData>()
    var ascendingType: Boolean = false
    var ascendingMaxLoss: Boolean = false
    var ascendingMaxGain: Boolean = false
    var ascendingBEP: Boolean = false
    var ascendingInvestment: Boolean = false
    var ascendingNetPremium: Boolean = false
    var ascendingRatio: Boolean = false
    var ascendingSpredDiff: Boolean = false
    var ascendingVolatility: Boolean = false

    lateinit var scanPostRequest: ScanPostRequest
    var dMaxGainList = ArrayList<Double>()
    var dMaxLossList = ArrayList<Double>()
    var filterMaxProfit = ""
    var filterMaxLoss = ""
    var date = ""

    var LossMaxValue: Float = 100.0F
    var LossMinValue: Float = 0F
    var ProfitMaxValue: Float = 100F
    var ProfitMInValue: Float = 0F
    private var activitys: FragmentActivity? = null

    fun onClickScanView(activity: FragmentActivity?, scanPost: ScanPostRequest) {

        activitys = activity
        dataLoading.value = true
        scanPostRequest = scanPost
        marketOutLook = scanPostRequest.request.data.iMarketTitle
        betweenDiffr = scanPostRequest.request.data.dLowerStrike.replace(".0", "") + "-" +
                scanPostRequest.request.data.dUpperStrike.replace(".0", "")

        date = DateTimeFormatter.getDateFromTimeStamp(scanPostRequest.request.data.lExpDate, "dd MMM yyyy", "nse")
        symbolWithExpiry.value = scanPostRequest.request.data.cSymbol + " " + date

        StrategyDataRepository.getInstance().postStrategyScanFilter(scanPostRequest, fun(isSuccess: Boolean, response: ScanDataResponse?) {
            if (isSuccess) {
                responseScanData = response!!.response.data.data.filter { it.lRuleData != "0" } as ArrayList<ScanDataResponse.Response.Data.ScanData>

                if (responseScanData.size > 0) {
                    responseScanData.forEach {

                        try {

                            if (it.iStrategyType != null) {
                                it.iStrategyType = getStrategyTypeName(scanPostRequest, it.iStrategyType)
                            }
                            if (it.dMaxGain != null) {
                                it.dMaxGain = String.format("%.2f", it.dMaxGain.toDouble())
                            }
                            if (it.dMaxLoss != null) {
                                it.dMaxLoss = String.format("%.2f", it.dMaxLoss.toDouble())
                            }
                            if (it.dBEP != null) {
                                it.dBEP = String.format("%.2f", it.dBEP.toDouble())
                            }
                            if (it.dInvestment != null) {
                                it.dInvestment = String.format("%.2f", it.dInvestment.toDouble())
                            }
                            if (it.iVolatility != null) {
                                it.iVolatility = String.format("%.2f", it.iVolatility.toDouble())
                            }
                            if (it.dNetPremium != null) {
                                it.dNetPremium = String.format("%.2f", it.dNetPremium.toDouble())
                            }
                            if (it.lProbability != null) {
                                it.lProbability = String.format("%.2f", it.lProbability.toDouble())
                            }
                            if (it.dRiskRatio != null) {
                                it.dRiskRatio = String.format("%.2f", it.dRiskRatio.toDouble())
                            }
                            if (it.dStrikeDiff != null) {
                                it.dStrikeDiff = String.format("%.2f", it.dStrikeDiff.toDouble())
                            }

                            if (it.dMaxGain != null && !it.dMaxGain.equals("0.00")) {
                                dMaxGainList.add(it.dMaxGain.toDouble())
                            }
                            if (it.dMaxLoss != null && !it.dMaxLoss.equals("0.00")) {
                                dMaxLossList.add(it.dMaxLoss.toDouble())
                            }
                        } catch (e: Exception) {
                            Log.e("error", "" + e)
                        }
                    }

                    if (responseScanData.size > 0) {
                        if (dMaxLossList != null && dMaxLossList.size > 0) {
                            LossMaxValue = dMaxLossList.maxByOrNull { it }!!.toFloat()
                            
                            LossMinValue = dMaxLossList.minByOrNull { it }!!.toFloat()
                        }
                        if (dMaxGainList != null && dMaxGainList.size > 0) {
                            ProfitMaxValue = dMaxGainList.maxByOrNull { it }!!.toFloat()
                            ProfitMInValue = dMaxGainList.minByOrNull { it }!!.toFloat()
                        }
                    }

                    var chk_Mgain = Util.getPrefs(activitys).getBoolean("chk_Mgain", false)
                    var chk_MLoss = Util.getPrefs(activitys).getBoolean("chk_MLoss", false)

                    if (chk_Mgain || chk_MLoss) {

                        var responseScan: List<ScanDataResponse.Response.Data.ScanData>? = null

                        if (chk_Mgain && chk_MLoss) {
                            responseScan = responseScanData.filter {
                                it.dMaxGain != "0.00" && it.dMaxLoss != "0.00"
                            }
                        } else if (chk_Mgain) {
                            responseScan = responseScanData.filter {
                                it.dMaxGain != "0.00"
                            }
                        } else if (chk_MLoss) {
                            responseScan = responseScanData.filter {
                                it.dMaxLoss != "0.00"
                            }
                        }

                        responseScanData.clear()
                        responseScanData = responseScan as ArrayList<ScanDataResponse.Response.Data.ScanData>

                    }

                    if (filterMaxLoss.isNotEmpty() && filterMaxProfit.isNotEmpty()) {

                        filterScanData.clear()
                        responseScanData.forEach {

                            if (it.dMaxLoss.toDouble() <= filterMaxLoss.toDouble() && it.dMaxGain.toDouble() <= filterMaxProfit.toDouble()) {

                                filterScanData.add(it)
                            }
                        }
                        var filterList = filterScanData.filter { it.cTradeDetails != "Empty" }

                        strategyDataListLive.value = filterList as ArrayList<ScanDataResponse.Response.Data.ScanData>
                    } else {

                        filterScanData.clear()
                        filterScanData = responseScanData
                        var filterList = responseScanData.filter { it.cTradeDetails != "Empty" }

                        strategyDataListLive.value = filterList as ArrayList<ScanDataResponse.Response.Data.ScanData>
                    }

                } else {
                    filterScanData.clear()
                    filterScanData = responseScanData

                    var filterList = responseScanData.filter { it.cTradeDetails != "Empty" }

                    strategyDataListLive.value = filterList as ArrayList<ScanDataResponse.Response.Data.ScanData>
                    empty.value = true

                }
            } else {
                empty.value = true
            }
            dataLoading.value = false

        })

    }

    fun tradeNotificationView(iBuySell: String, iToken: String, fillQty: String, dFillPrice: String, userName: String, position: Int) {

        var tradeNotificationRequest = TradeNotificationRequest(TradeNotificationRequest.Request(
                FormFactor = "M",
                data = TradeNotificationRequest.Request.Data(

                        bIsOptionStrategy = true,
                        bTempTradeFlag = true,
                        cAccountNumber = userName,
                        cBrokerID = "1234567",
                        cExpiryDt = "",
                        cGreekClientID = userName,
                        cInstrumentName = "",
                        cMappedSymbol = "",
                        cMaturityDate = "",
                        cOptionType = "",
                        cSeries = "",
                        cStrategyName = "#VIEW_ONLY#",
                        cSymbolName = "",
                        cTradeDate = "",
                        dFillPrice = dFillPrice.replace(" ", "").toDouble(),
                        dStrikePrice = 0.0,
                        iBuySell = iBuySell.toInt(),
                        iExchange = 1,
                        iIdentifier = 1,
                        iMarketSegment = 2,
                        iProClient = 1,
                        iTradeType = -1,
                        lFillNumber = 0,
                        lFillQuantity = fillQty.trim().toLong(),
                        lOurOrderNo = 0,
                        lToken = iToken.toLong()
                ),
                svcGroup = "portfolio",
                svcName = "TradeNotificationView",
                requestType = "U",
                svcVersion = "1.0.0"))


        StrategyDataRepository.getInstance().postTradeNotifyView(tradeNotificationRequest, fun(isSuccess: Boolean, response: SFBuildUpResponse?) {
            if (isSuccess) {
                var ErrorCode: String = response!!.response.data.ErrorCode
                empty.value = false
            } else {
                empty.value = true
            }
            dataLoading.value = false

        })

    }

    fun sortScanListData(
            SortNetPremium: Boolean, SortRatio: Boolean,
            SortSpredDiff: Boolean, SortVolatility: Boolean,
            SortMaxLoss: Boolean, SortMaxGain: Boolean,
            SortBEP: Boolean, SortInvestment: Boolean,
            SortType: Boolean, ascending: Boolean) {

        if (SortType) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.iStrategyType
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.iStrategyType
                }
            }

        }
        if (SortMaxLoss) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.dMaxLoss.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.dMaxLoss.toDouble()
                }
            }

        }
        if (SortMaxGain) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.dMaxGain.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.dMaxGain.toDouble()
                }
            }

        }
        if (SortBEP) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.dBEP.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.dBEP.toDouble()
                }
            }

        }
        if (SortInvestment) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.dInvestment.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.dInvestment.toDouble()
                }
            }

        }

        if (SortNetPremium) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.dNetPremium.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.dNetPremium.toDouble()
                }
            }
        }

        if (SortRatio) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.dRiskRatio.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.dRiskRatio.toDouble()
                }
            }
        }

        if (SortSpredDiff) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.dStrikeDiff.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.dStrikeDiff.toDouble()
                }
            }
        }

        if (SortVolatility) {
            if (ascending) {
                filterScanData.sortBy { it ->
                    it.iVolatility.toDouble()
                }
            } else {
                filterScanData.sortByDescending { it ->
                    it.iVolatility.toDouble()
                }
            }
        }


        var filterList = filterScanData.filter { it.cTradeDetails != "Empty" }

        strategyDataListLive.value = filterList as ArrayList<ScanDataResponse.Response.Data.ScanData>
    }

    fun getStrategyTypeName(scanPostRequest: ScanPostRequest, strategyID: String): String {

        var StrategyTypeName: String = ""

        if (strategyID != null && strategyID.isNotEmpty()) {
            prePareStrategyList(scanPostRequest.request.data.iMarketTitle, scanPostRequest.request.data.iVolatalityTitle).forEach {
                if (!strategyID.isBlank() && it.StrategyId == strategyID) {

                    StrategyTypeName = it.StrategyName
                }
            }
        }

        return StrategyTypeName
    }

    fun onClickSortMaxLoss() {

        ascendingMaxLoss = !ascendingMaxLoss

        sortScanListData(SortNetPremium = false, SortRatio = false,
                SortSpredDiff = false, SortVolatility = false,
                SortMaxGain = false, SortMaxLoss = true,
                SortBEP = false, SortInvestment = false, SortType = false, ascending = ascendingMaxLoss)
    }

    fun onClickSortMaxGain() {

        ascendingMaxGain = !ascendingMaxGain
        sortScanListData(SortNetPremium = false, SortRatio = false,
                SortSpredDiff = false, SortVolatility = false,
                SortMaxGain = true, SortMaxLoss = false, SortBEP = false, SortInvestment = false, SortType = false, ascending = ascendingMaxGain)

    }

    fun onClickSortBEP() {

        ascendingBEP = !ascendingBEP

        sortScanListData(SortNetPremium = false, SortRatio = false,
                SortSpredDiff = false, SortVolatility = false,
                SortMaxGain = false, SortMaxLoss = false, SortBEP = true, SortInvestment = false, SortType = false, ascending = ascendingBEP)

    }

    fun onClickSortInvestment() {

        ascendingInvestment = !ascendingInvestment

        sortScanListData(SortNetPremium = false, SortRatio = false,
                SortSpredDiff = false, SortVolatility = false,
                SortMaxGain = false, SortMaxLoss = false, SortBEP = false,
                SortInvestment = true, SortType = false, ascending = ascendingInvestment)

    }

    fun onClickSortTDetails() {

        ascendingType = !ascendingType

        sortScanListData(SortNetPremium = false, SortRatio = false,
                SortSpredDiff = false, SortVolatility = false,
                SortMaxGain = false, SortMaxLoss = false, SortBEP = false, SortInvestment = false,
                SortType = true, ascending = ascendingType)
    }

    fun onClickSortNetPremium() {

        ascendingNetPremium = !ascendingNetPremium

        sortScanListData(SortNetPremium = true, SortRatio = false,
                SortSpredDiff = false, SortVolatility = false,
                SortMaxGain = false, SortMaxLoss = false,
                SortBEP = false, SortInvestment = false, SortType = false, ascending = ascendingNetPremium)
    }

    fun onClickSortRatio() {

        ascendingRatio = !ascendingRatio

        sortScanListData(SortNetPremium = false, SortRatio = true,
                SortSpredDiff = false, SortVolatility = false,
                SortMaxGain = false, SortMaxLoss = false, SortBEP = false,
                SortInvestment = false, SortType = false, ascending = ascendingRatio)
    }

    fun onClickSpredDiff() {

        ascendingSpredDiff = !ascendingSpredDiff

        sortScanListData(SortNetPremium = false, SortRatio = false,
                SortSpredDiff = true, SortVolatility = false,
                SortMaxGain = false, SortMaxLoss = false,
                SortBEP = false, SortInvestment = false, SortType = false, ascending = ascendingSpredDiff)
    }

    fun onClickVolatility() {

        ascendingVolatility = !ascendingVolatility

        sortScanListData(SortNetPremium = false, SortRatio = false,
                SortSpredDiff = false, SortVolatility = true,
                SortMaxGain = false, SortMaxLoss = false,
                SortBEP = false, SortInvestment = false, SortType = false, ascending = ascendingVolatility)
    }

    fun ShowAlertDialogForSubmitIocOrder(activity: FragmentActivity?, scanData: ScanDataResponse.Response.Data.ScanData, type: String) {

        var selectedStrategyName = ""
        var selectedLeg = ""


        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.sf_ioc_order_layout, null)
        val mBuilder = android.app.AlertDialog.Builder(activity).setView(mDialogView)
        val mAlertDialog = mBuilder.show()

        if (AccountDetails.getThemeFlag(activity).equals("white")) {
            mDialogView.ioc_kayout.setBackgroundColor(activity!!.resources.getColor(R.color.white))
            mDialogView.header_layout.setBackgroundColor(activity!!.resources.getColor(R.color.selectColor))
            mDialogView.spread_difference_values.setBackgroundColor(activity!!.resources.getColor(R.color.light_gray))
            mDialogView.spread_difference_text.setBackgroundColor(activity!!.resources.getColor(R.color.light_gray))

            mDialogView.txt_buy_zero.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_zero_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_zero_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_buy_one.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_one_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_one_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_sell_one.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_one_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_one_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_buy_two.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_two_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_two_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_sell_two.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_two_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_two_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_buy_three.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_three_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_three_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_sell_three.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_three_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_three_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_buy_four.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_four_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_buy_four_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_sell_four.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_four_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_four_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_sell_five.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_five_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_five_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_sell_six.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_six_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_six_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.txt_sell_seven.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_seven_qty.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_sell_seven_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.byu_at_zero.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_zero.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.byu_at_one.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_one.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.byu_at_two.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_two.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.byu_at_three.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_three.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.byu_at_four.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_four.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.byu_at_five.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_five.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.byu_at_six.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_six.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.byu_at_seven.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sell_at_seven.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.leg.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.sleepage.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.ico_edt_leg.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_sleepleg.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.multiplier.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_multipliers.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.stategy_name.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edts_strategy.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView.spread_text.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.required_text.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.spread_difference.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.required_margin.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.validity_text.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.product_type_text.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.Ordertype_text.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
        }
        mDialogView.spread_difference.setText(String.format("%.2f", scanData.dStrikeDiff.toDouble()))
        mDialogView.required_margin.setText(String.format("%.2f", scanData.dExpMargin.toDouble()))

        var splitStringArray: ArrayList<String> = ArrayList()
        if (scanData.cTradeDetails.contains("\n")) {
            splitStringArray = scanData.cTradeDetails.split("\n") as ArrayList<String>

        } else {

            splitStringArray.add(scanData.cTradeDetails)
        }
        val str1 = "BUY"
        val str2 = "SELL"

        for (i in 0 until splitStringArray.size) {

            if (i == 0) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_zero.visibility = View.VISIBLE
                    mDialogView.buy_zero.visibility = View.VISIBLE
                    mDialogView.byu_at_zero. setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_zero_qty.setText(fillQty)
                    mDialogView.txt_buy_zero_price.setText(dFillPrice)
//                    mDialogView.txt_buy_one_qty.isEnabled = false
//                    mDialogView.txt_buy_one_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                          values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                          values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }
                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_zero.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_zero.visibility = View.VISIBLE
                    mDialogView.sell_zero.visibility = View.VISIBLE
                    mDialogView.sell_at_zero. setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_zero_qty.setText(fillQty)
                    mDialogView.txt_sell_zero_price.setText(dFillPrice)
//                    mDialogView.txt_sell_one_qty.isEnabled = false
//                    mDialogView.txt_sell_one_price.isEnabled = false

                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_zero.setText(spannable)

                }
            } else if (i == 1) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_one.visibility = View.VISIBLE
                    mDialogView.buy_one.visibility = View.VISIBLE
                    mDialogView.byu_at_one. setText(activity?.resources?.getString(R.string.at_therate))



                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_one_qty.setText(fillQty)
                    mDialogView.txt_buy_one_price.setText(dFillPrice)
//                    mDialogView.txt_buy_one_qty.isEnabled = false
//                    mDialogView.txt_buy_one_price.isEnabled = false

                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_one.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_one.visibility = View.VISIBLE
                    mDialogView.sell_one.visibility = View.VISIBLE
                    mDialogView.sell_at_one. setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_one_qty.setText(fillQty)
                    mDialogView.txt_sell_one_price.setText(dFillPrice)
//                    mDialogView.txt_sell_one_qty.isEnabled = false
//                    mDialogView.txt_sell_one_price.isEnabled = false

                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_one.setText(spannable)

                }
            } else if (i == 2) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_two.visibility = View.VISIBLE
                    mDialogView.buy_two.visibility = View.VISIBLE
                    mDialogView.byu_at_two.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_two_qty.setText(fillQty)
                    mDialogView.txt_buy_two_price.setText(dFillPrice)
//                    mDialogView.txt_buy_two_qty.isEnabled = false
//                    mDialogView.txt_buy_two_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_two.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_two.visibility = View.VISIBLE
                    mDialogView.sell_two.visibility = View.VISIBLE
                    mDialogView.sell_at_two.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_two_qty.setText(fillQty)
                    mDialogView.txt_sell_two_price.setText(dFillPrice)
//                    mDialogView.txt_sell_two_qty.isEnabled = false
//                    mDialogView.txt_sell_two_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_two.setText(spannable)

                }
            } else if (i == 3) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_three.visibility = View.VISIBLE
                    mDialogView.buy_three.visibility = View.VISIBLE
                    mDialogView.byu_at_three.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
//                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_three_qty.setText(fillQty)
                    mDialogView.txt_buy_three_price.setText(dFillPrice)
//                    mDialogView.txt_buy_three_qty.isEnabled = false
//                    mDialogView.txt_buy_three_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_three.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_three.visibility = View.VISIBLE
                    mDialogView.sell_three.visibility = View.VISIBLE
                    mDialogView.sell_at_three.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_three_qty.setText(fillQty)
                    mDialogView.txt_sell_three_price.setText(dFillPrice)
//                    mDialogView.txt_sell_three_qty.isEnabled = false
//                    mDialogView.txt_sell_three_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_three.setText(spannable)

                }
            } else if (i == 4) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_four.visibility = View.VISIBLE
                    mDialogView.buy_four.visibility = View.VISIBLE
                    mDialogView.byu_at_four.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
//                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_four_qty.setText(fillQty)
                    mDialogView.txt_buy_four_price.setText(dFillPrice)
//                    mDialogView.txt_buy_four_qty.isEnabled = false
//                    mDialogView.txt_buy_four_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }
                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_four.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_four.visibility = View.VISIBLE
                    mDialogView.sell_four.visibility = View.VISIBLE
                    mDialogView.sell_at_four.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_four_qty.setText(fillQty)
                    mDialogView.txt_sell_four_price.setText(dFillPrice)
//                    mDialogView.txt_sell_four_qty.isEnabled = false
//                    mDialogView.txt_sell_four_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_four.setText(spannable)

                }
            } else if (i == 5) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_five.visibility = View.VISIBLE
                    mDialogView.buy_five.visibility = View.VISIBLE
                    mDialogView.byu_at_five.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
//                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_five_qty.setText(fillQty)
                    mDialogView.txt_buy_five_price.setText(dFillPrice)
//                    mDialogView.txt_buy_five_qty.isEnabled = false
//                    mDialogView.txt_buy_five_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }
                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_five.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_five.visibility = View.VISIBLE
                    mDialogView.sell_five.visibility = View.VISIBLE
                    mDialogView.sell_at_five.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_five_qty.setText(fillQty)
                    mDialogView.txt_sell_five_price.setText(dFillPrice)
//                    mDialogView.txt_sell_five_qty.isEnabled = false
//                    mDialogView.txt_sell_five_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_five.setText(spannable)

                }
            } else if (i == 6) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_six.visibility = View.VISIBLE
                    mDialogView.buy_six.visibility = View.VISIBLE
                    mDialogView.byu_at_six.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_six_qty.setText(fillQty)
                    mDialogView.txt_buy_six_price.setText(dFillPrice)
//                    mDialogView.txt_buy_six_qty.isEnabled = false
//                    mDialogView.txt_buy_six_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }
                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_six.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_six.visibility = View.VISIBLE
                    mDialogView.sell_six.visibility = View.VISIBLE
                    mDialogView.sell_at_six.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_six_qty.setText(fillQty)
                    mDialogView.txt_sell_six_price.setText(dFillPrice)
//                    mDialogView.txt_sell_six_qty.isEnabled = false
//                    mDialogView.txt_sell_six_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_six.setText(spannable)

                }
            } else if (i == 7) {
                if (splitStringArray.get(i).contains("BUY")) {
                    mDialogView.buysell_seven.visibility = View.VISIBLE
                    mDialogView.buy_seven.visibility = View.VISIBLE
                    mDialogView.byu_at_seven.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_buy_seven_qty.setText(fillQty)
                    mDialogView.txt_buy_seven_price.setText(dFillPrice)
//                    mDialogView.txt_buy_seven_qty.isEnabled = false
//                    mDialogView.txt_buy_seven_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }
                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str1)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_buy_seven.setText(spannable)


                }
                if (splitStringArray.get(i).contains("SELL")) {
                    mDialogView.buysell_seven.visibility = View.VISIBLE
                    mDialogView.sell_seven.visibility = View.VISIBLE
                    mDialogView.sell_at_seven.  setText(activity?.resources?.getString(R.string.at_therate))
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice : String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")
                    }else {
                        dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    }
                    mDialogView.txt_sell_seven_qty.setText(fillQty)
                    mDialogView.txt_sell_seven_price.setText(dFillPrice)
//                    mDialogView.txt_sell_seven_qty.isEnabled = false
//                    mDialogView.txt_sell_seven_price.isEnabled = false
                    val values :String
                    if(splitStringArray.get(i).split(" ").size<=5){
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3)
                    }else {
                        values = splitStringArray.get(i).split(" ").get(0) + splitStringArray.get(i).split(" ").get(2) +
                                splitStringArray.get(i).split(" ").get(3) + splitStringArray.get(i).split(" ").get(4) +
                                splitStringArray.get(i).split(" ").get(5)
                    }

                    val spannable = SpannableStringBuilder()
                    var indexs = 0
                    spannable.append(values)
                    if (values.contains(str2)) {
                        spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    mDialogView.txt_sell_seven.setText(spannable)

                }
            }

        }


        mDialogView.edt_multipliers.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    if (s.toString().toInt() <= 10) {

                        for (i in 0 until splitStringArray.size) {

                            if (i == 0) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_zero_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_zero_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellfillQty = mDialogView.txt_sell_zero_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_zero_qty.setText("" + sellcalculate)
                                }
                            }
                            else if (i == 1) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_one_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_one_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellfillQty = mDialogView.txt_sell_one_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_one_qty.setText("" + sellcalculate)
                                }
                            }
                            else if (i == 2) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_two_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_two_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellfillQty = mDialogView.txt_sell_two_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_two_qty.setText("" + sellcalculate)
                                }
                            }
                            else if (i == 3) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_three_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_three_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    var sellfillQty = mDialogView.txt_sell_three_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_three_qty.setText("" + sellcalculate)
                                }
                            }
                            else if (i == 4) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_four_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_four_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellfillQty = mDialogView.txt_sell_four_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_four_qty.setText("" + sellcalculate)
                                }
                            }
                            else if (i == 5) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_five_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_five_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellfillQty = mDialogView.txt_sell_five_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_five_qty.setText("" + sellcalculate)
                                }
                            }
                            else if (i == 6) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_six_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_six_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellfillQty = mDialogView.txt_sell_six_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_six_qty.setText("" + sellcalculate)
                                }
                            }
                            else if (i == 7) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var butfillQty = mDialogView.txt_buy_seven_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var buycalculate = butfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_buy_seven_qty.setText("" + buycalculate)
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellfillQty = mDialogView.txt_sell_seven_qty.text.toString()
                                    var multiplier = mDialogView.edt_multipliers.text.toString()
                                    var sellcalculate = sellfillQty.toInt() * multiplier.toInt()
                                    mDialogView.txt_sell_seven_qty.setText("" + sellcalculate)
                                }
                            }

                        }


                    } else {
                        for (i in 0 until splitStringArray.size) {

                            if (i == 0) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_zero_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_zero_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            } else if (i == 1) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_one_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_one_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            } else if (i == 2) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_two_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_two_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            } else if (i == 3) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_three_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_three_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            } else if (i == 4) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_four_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_four_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            } else if (i == 5) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_five_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_five_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            } else if (i == 6) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_six_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_six_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            } else if (i == 7) {
                                if (splitStringArray.get(i).contains("BUY")) {

                                    mDialogView.txt_buy_seven_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                    mDialogView.txt_sell_seven_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                                }
                            }

                        }
                        mDialogView.edt_multipliers.setText("1")
                        GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK,
                                "Multiplier can't be greater than 10", "Ok", false) { action, data ->

                        }
                    }
                } catch (e: Exception) {
                    for (i in 0 until splitStringArray.size) {

                        if (i == 0) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_zero_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_zero_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        } else if (i == 1) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_one_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_one_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        } else if (i == 2) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_two_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_two_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        } else if (i == 3) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_three_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_three_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        } else if (i == 4) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_four_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_four_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        } else if (i == 5) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_five_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_five_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        } else if (i == 6) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_six_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_six_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        } else if (i == 7) {
                            if (splitStringArray.get(i).contains("BUY")) {

                                mDialogView.txt_buy_seven_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                mDialogView.txt_sell_seven_qty.setText("" + splitStringArray.get(i).split(" ").get(1))
                            }
                        }

                    }


                    Log.e("number error", "" + e.message)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        mDialogView.edt_sleepleg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    if ( s.toString().toInt() <= 10) {

                        for (i in 0 until splitStringArray.size) {

                            if (i == 0) {



                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate:Double
                                    var butfillprice = mDialogView.txt_buy_zero_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice.toDouble() + multiplier.toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.substring(1).toDouble()
                                    }
                                    mDialogView.txt_buy_zero_price.setText(String.format("%.2f",  buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate :Double
                                    var sellfillprice = mDialogView.txt_sell_zero_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice.toDouble() - multiplier.toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.substring(1).toDouble()
                                    }
                                    mDialogView.txt_sell_zero_price.setText(String.format("%.2f",  sellcalculate))
                                }
                            }
                            else if (i == 1) {



                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate:Double
                                    var butfillprice = mDialogView.txt_buy_one_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice .toDouble() + multiplier .toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.substring(1).toDouble()
                                    }
                                    mDialogView.txt_buy_one_price.setText(String.format("%.2f",  buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate :Double
                                    var sellfillprice = mDialogView.txt_sell_one_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice .toDouble() - multiplier .toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.substring(1).toDouble()
                                    }
                                    mDialogView.txt_sell_one_price.setText(String.format("%.2f",  sellcalculate))
                                }

                            }
                            else if (i == 2) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate  :Double
                                    var butfillprice = mDialogView.txt_buy_two_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice.toDouble() + multiplier.toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.toDouble()
                                    }
                                    mDialogView.txt_buy_two_price.setText(String.format("%.2f",  buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate  :Double
                                    var sellfillprice = mDialogView.txt_sell_two_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice.toDouble() - multiplier.toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.toDouble()
                                    }
                                    mDialogView.txt_sell_two_price.setText(String.format("%.2f",  sellcalculate))
                                }

                            }
                            else if (i == 3) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate  :Double
                                    var butfillprice = mDialogView.txt_buy_three_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice.toDouble() + multiplier.toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.toDouble()
                                    }

                                    mDialogView.txt_buy_three_price.setText(String.format("%.2f", buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate  :Double
                                    var sellfillprice = mDialogView.txt_sell_three_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice.toDouble() - multiplier.toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.toDouble()
                                    }

                                    mDialogView.txt_sell_three_price.setText(String.format("%.2f", sellcalculate))
                                }

                            }
                            else if (i == 4) {


                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate  :Double
                                    var butfillprice = mDialogView.txt_buy_four_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice.toDouble() + multiplier.toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.toDouble()
                                    }

                                    mDialogView.txt_buy_four_price.setText(String.format("%.2f",  buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate  :Double
                                    var sellfillprice = mDialogView.txt_sell_four_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice.toDouble() - multiplier.toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.toDouble()
                                    }

                                    mDialogView.txt_sell_four_price.setText(String.format("%.2f",  sellcalculate))
                                }

                            }
                            else if (i == 5) {

                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate  :Double
                                    var butfillprice = mDialogView.txt_buy_five_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice.toDouble() + multiplier.toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.toDouble()
                                    }
                                    mDialogView.txt_buy_five_price.setText(String.format("%.2f",  buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate  :Double
                                    var sellfillprice = mDialogView.txt_sell_five_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice.toDouble() - multiplier.toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.toDouble()
                                    }
                                    mDialogView.txt_sell_five_price.setText(String.format("%.2f",  sellcalculate))
                                }

                            }
                            else if (i == 6) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate  :Double
                                    var butfillprice = mDialogView.txt_buy_six_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice.toDouble() + multiplier.toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.toDouble()
                                    }
                                    mDialogView.txt_buy_six_price.setText(String.format("%.2f", buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate  :Double
                                    var sellfillprice = mDialogView.txt_sell_six_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice.toDouble() - multiplier.toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.toDouble()
                                    }
                                    mDialogView.txt_sell_six_price.setText(String.format("%.2f", sellcalculate))
                                }

                            }
                            else if (i == 7) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    var buycalculate  :Double
                                    var butfillprice = mDialogView.txt_buy_seven_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        buycalculate = butfillprice.toDouble() + multiplier.toDouble()

                                    } else {
                                        buycalculate = butfillprice.toDouble() - multiplier.toDouble()
                                    }

                                    mDialogView.txt_buy_seven_price.setText(String.format("%.2f",  buycalculate))
                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    var sellcalculate  :Double
                                    var sellfillprice = mDialogView.txt_sell_seven_price.text.toString()
                                    var multiplier = mDialogView.edt_sleepleg.text.toString()
                                    if (multiplier.toInt() > 0) {
                                        sellcalculate = sellfillprice.toDouble() - multiplier.toDouble()

                                    } else {
                                        sellcalculate = sellfillprice.toDouble() + multiplier.toDouble()
                                    }

                                    mDialogView.txt_sell_seven_price.setText(String.format("%.2f",  sellcalculate))
                                }

                            }

                        }


                    } else {
                        for (i in 0 until splitStringArray.size) {

                            if (i == 0) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_zero_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_zero_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_sell_zero_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_sell_zero_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                            } else if (i == 1) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_one_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_one_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_sell_one_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_sell_one_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                            } else if (i == 2) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_two_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_two_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_sell_two_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_sell_two_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                            } else if (i == 3) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_three_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_three_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_sell_three_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_sell_three_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                            } else if (i == 4) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_four_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_four_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_sell_four_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_sell_four_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                            } else if (i == 5) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_five_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_five_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_sell_five_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_sell_five_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                            } else if (i == 6) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_six_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_six_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_sell_six_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_sell_six_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                            } else if (i == 7) {
                                if (splitStringArray.get(i).contains("BUY")) {
                                    if(splitStringArray.get(i).split(" ").size<=5){
                                        mDialogView.txt_buy_seven_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                    }else {
                                        mDialogView.txt_buy_seven_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                    }

                                }
                                if (splitStringArray.get(i).contains("SELL")) {

                                        if(splitStringArray.get(i).split(" ").size<=5){
                                            mDialogView.txt_sell_seven_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                        }else {
                                            mDialogView.txt_sell_seven_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                        }

                                    }

                                }
                            }


                        mDialogView.edt_sleepleg.setText("0")

                        GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK,
                                "Sleepage can't be greater than 10", "Ok", false) { action, data ->

                        }
                    }
                } catch (e: Exception) {
                    for (i in 0 until splitStringArray.size) {

                        if (i == 0) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_zero_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_zero_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_zero_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_zero_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                        } else if (i == 1) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_one_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_one_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_one_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_one_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                        } else if (i == 2) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_two_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_two_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_two_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_two_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                        } else if (i == 3) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_three_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_three_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_three_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_three_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                        } else if (i == 4) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_four_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_four_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_four_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_four_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                        } else if (i == 5) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_five_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_five_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_five_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_five_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                        } else if (i == 6) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_six_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_six_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_six_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_six_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                        } else if (i == 7) {
                            if (splitStringArray.get(i).contains("BUY")) {
                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_buy_seven_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_buy_seven_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }
                            if (splitStringArray.get(i).contains("SELL")) {

                                if(splitStringArray.get(i).split(" ").size<=5){
                                    mDialogView.txt_sell_seven_price.setText("" + splitStringArray.get(i).split(" ").get(4).removePrefix("@"))
                                }else {
                                    mDialogView.txt_sell_seven_price.setText("" + splitStringArray.get(i).split(" ").get(6).removePrefix("@"))
                                }

                            }

                        }
                    }
                    Log.e("number error", "" + e.message)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        val lvalList = ArrayList<String>()
        lvalList.add("DAY")
        lvalList.add("IOC")


        var selectedvalidity = ""
        val lvalListadapter1: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(), lvalList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(activity!!.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(activity!!.resources.getColor(R.color.white))
                }
                v.setPadding(15, 15, 15, 15)
                return v
            }

//                  override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
//                      val v = super.getView(position, convertView, parent) as TextView
//                      //  v.setTypeface(font);
//                      if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
//                          v.setTextColor(activity!!.resources.getColor(R.color.black))
//                      } else {
//                          v.setTextColor(activity!!.resources.getColor(R.color.white))
//                      }
//                      v.setPadding(15, 15, 15, 15)
//                      return v
//                  }
        }
        lvalListadapter1.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.edt_validity.setAdapter(lvalListadapter1)
        if (type.equals("IOC")) {
            mDialogView.edt_validity.setSelection(1)
        } else {
            mDialogView.edt_validity.setSelection(0)
        }
        mDialogView.edt_validity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (lvalList.get(p2).equals("IOC")){
                    selectedvalidity = "1"

                }else{
                    selectedvalidity = "0"

                }
            }
        }

        val prodList = ArrayList<String>()
        prodList.add("Delivery")
        prodList.add("Intraday")
        //prodList.add("Catalyst")

        var selectedprodList = ""
        val prodListadapter1: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(), prodList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(activity!!.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(activity!!.resources.getColor(R.color.white))
                }
                v.setPadding(15, 15, 15, 15)
                return v
            }
//
//                  override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
//                      val v = super.getView(position, convertView, parent) as TextView
//                      //  v.setTypeface(font);
//                      if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
//                          v.setTextColor(activity!!.resources.getColor(R.color.black))
//                      } else {
//                          v.setTextColor(activity!!.resources.getColor(R.color.white))
//                      }
//                      v.setPadding(15, 15, 15, 15)
//                      return v
//                  }
        }
        prodListadapter1.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.edt_prodtype.setAdapter(prodListadapter1)
        mDialogView.edt_prodtype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (prodList.get(p2).equals("Delivery")){
                    selectedprodList="0"
                }else if (prodList.get(p2).equals("Intraday")){
                    selectedprodList="1"
                }else{

                }

            }
        }

        val ordertypeList = ArrayList<String>()
        ordertypeList.add("Regular")

        val ordertypeListdapter1: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(), ordertypeList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(activity!!.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(activity!!.resources.getColor(R.color.white))
                }
                v.setPadding(15, 15, 15, 15)
                return v
            }

//                  override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
//                      val v = super.getView(position, convertView, parent) as TextView
//                      //  v.setTypeface(font);
//                      if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
//                          v.setTextColor(activity!!.resources.getColor(R.color.black))
//                      } else {
//                          v.setTextColor(activity!!.resources.getColor(R.color.white))
//                      }
//                      v.setPadding(15, 15, 15, 15)
//                      return v
//                  }
        }
        ordertypeListdapter1.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.edt_ordertype.setAdapter(ordertypeListdapter1)



        mDialogView.ico_edt_leg.setText("${splitStringArray.size} Leg")

        mDialogView.imgs_close.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mDialogView.btn_cancels.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mDialogView.btn_submits.setOnClickListener {

            if (mDialogView.edt_sleepleg.text.toString().isEmpty()) {
                mDialogView.edt_sleepleg.setError("Enter Value")
                return@setOnClickListener
            }
            if (mDialogView.edt_multipliers.text.toString().isEmpty()) {
                mDialogView.edt_multipliers.setError("Enter Value")
                return@setOnClickListener
            }

            if (mDialogView.edt_multipliers.text.toString().equals("0")) {

                GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK,
                        "Please enter minimum 1 multiplier", "Ok", false) { action, data -> }
                return@setOnClickListener
            }

            if (mDialogView.edts_strategy.text.toString().isEmpty()) {
                mDialogView.edts_strategy.setError("Enter Name")
                return@setOnClickListener
            }


            var sleepageVal = mDialogView.edt_sleepleg.text.toString().toDouble()
            var multiplierVal = mDialogView.edt_multipliers.text.toString().toInt()
            var selectedStrategyName = mDialogView.edts_strategy.text.toString()

            if (selectedvalidity.equals("0")){
                submitDAYorder(activity, AccountDetails.getUserCode(activity), "0", sleepageVal, multiplierVal,selectedprodList, scanData)

            }else {

                submitIOCorder(activity, selectedStrategyName, AccountDetails.getUserCode(activity), selectedvalidity, sleepageVal, multiplierVal, selectedprodList, scanData)
            }
            mAlertDialog.dismiss()
        }

        val spannables = SpannableStringBuilder()

        var strategyName = "LONG RATIO CALL SPREAD"

        var msg = "Exchange Order Entry- $strategyName"
        spannables.append(msg)
        var index = msg.indexOf(strategyName)
        if (AccountDetails.getThemeFlag(activity).equals("white")) {
            spannables!!.setSpan(ForegroundColorSpan(activity!!.resources.getColor(R.color.login_button_bg)), index, index + strategyName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            spannables!!.setSpan(ForegroundColorSpan(activity!!.resources.getColor(R.color.login_button_bg)), index, index + strategyName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        mDialogView.txt_title.setText(spannables)


        val legList = ArrayList<String>()
        legList.add("E")
        legList.add("F")
        legList.add("O")

        var adapter1 = ArrayAdapter(activity, AccountDetails.getRowSpinnerSimple(), legList)
        adapter1.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.spinner_leg.setAdapter(adapter1)

        mDialogView.spinner_leg.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedLeg = legList.get(p2)
            }
        }

    }

//    fun ShowAlertDialogForSubmitDayOrder(activity: FragmentActivity?, scanData: ScanDataResponse.Response.Data.ScanData) {
//
//        val spannable = SpannableStringBuilder()
//
//        var selectedStrategyName = ""
//        var selectedLeg = ""
//
//        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.sf_day_order_layout, null)
//        val mBuilder = android.app.AlertDialog.Builder(activity).setView(mDialogView)
//        val mAlertDialog = mBuilder.show()
//
//        if (AccountDetails.getThemeFlag(activity).equals("white")){
//            mDialogView.day_order_layout.setBackgroundColor(activity!!.resources.getColor(R.color.white))
//            mDialogView.day_header_layout.setBackgroundColor(activity!!.resources.getColor(R.color.selectColor))
//            mDialogView.day_txt_buySell.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_leg.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_sleepage.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_edt_leg.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_edt_sleepage.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_multiplier.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_edt_multiplier.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_stategy_name.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//            mDialogView.day_edt_strategy.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
//        }
//
//        var splitStringArray: ArrayList<String> = ArrayList()
//        if (scanData.cTradeDetails.contains("\n")) {
//            splitStringArray = scanData.cTradeDetails.split("\n") as ArrayList<String>
//
//        } else {
//
//            splitStringArray.add(scanData.cTradeDetails)
//        }
//        val str1 = "BUY"
//        val str2 = "SELL"
//        var indexs = 0
//
//        if (splitStringArray.size > 0) {
//
//            splitStringArray.forEach {
//
//                if (it.contains(str1)) {
//                    spannable.append(it)
//                    spannable!!.setSpan(ForegroundColorSpan(Color.GREEN), indexs, indexs + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    spannable.append("\n")
//                }
//                indexs = spannable.length
//
//                if (it.contains(str2)) {
//                    spannable.append(it)
//                    var startIndex = it.indexOf(str2)
//
//                    if (indexs > 0) {
//                        indexs = indexs - 1
//                    }
//                    spannable!!.setSpan(ForegroundColorSpan(Color.RED), indexs, indexs + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    spannable.append("\n")
//                }
//                indexs = spannable.length
//            }
//        }
//
//        if (spannable != null) {
//            mDialogView.day_txt_buySell.setText(spannable)
//        }
//
//
//        mDialogView.img_closes.setOnClickListener {
//            mAlertDialog.dismiss()
//        }
//
//        mDialogView.btns_cancel.setOnClickListener {
//            mAlertDialog.dismiss()
//        }
//
//        mDialogView.btns_submit.setOnClickListener {
//
//            if (mDialogView.day_edt_sleepage.text.toString().isEmpty()) {
//                mDialogView.day_edt_sleepage.setError("Enter value")
//                return@setOnClickListener
//            }
//            if (mDialogView.day_edt_multiplier.text.toString().isEmpty()) {
//                mDialogView.day_edt_multiplier.setError("Enter value")
//                return@setOnClickListener
//            }
//            if (mDialogView.day_edt_multiplier.text.toString().equals("0")) {
//
//                GreekDialog.alertDialog(activity, 0, GreekBaseActivity.GREEK,
//                        "Please enter minimum 1 multiplier", "Ok", false) { action, data -> }
//                return@setOnClickListener
//            }
//            if (mDialogView.day_edt_strategy.text.toString().isEmpty()) {
//                mDialogView.day_edt_strategy.setError("Enter Name")
//                return@setOnClickListener
//            }
//
//            selectedStrategyName = mDialogView.day_edt_strategy.text.toString()
//            var sleepageVal = mDialogView.day_edt_sleepage.text.toString().toDouble()
//            var multiplierVal = mDialogView.day_edt_multiplier.text.toString().toInt()
//            mAlertDialog.dismiss()
//            submitDAYorder(activity, AccountDetails.getUserCode(activity), "0", sleepageVal, multiplierVal, scanData)
//        }
//        var strategyName = scanData.iStrategyType
//
//        val spannables = SpannableStringBuilder()
//        var msg = "Submit Day Order $strategyName"
//        spannables.append(msg)
//        var index = msg.indexOf(strategyName)
//
//        if (AccountDetails.getThemeFlag(activity).equals("white")){
//            spannables!!.setSpan(ForegroundColorSpan(activity!!.resources.getColor(R.color.white)), index, index + strategyName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        }else {
//            spannables!!.setSpan(ForegroundColorSpan(activity!!.resources.getColor(R.color.login_button_bg)), index, index + strategyName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        }
//        mDialogView.day_txt_title.setText(spannables)
//
//
//        val legList = ArrayList<String>()
//        legList.add("E")
//        legList.add("F")
//        legList.add("O")
//
//        var adapter1 = ArrayAdapter(activity, AccountDetails.getRowSpinnerSimple(), legList)
//        adapter1.setDropDownViewResource(R.layout.custom_spinner)
//        mDialogView.spinner_leg.setAdapter(adapter1)
//
//        mDialogView.spinner_leg.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                selectedLeg = legList.get(p2)
//            }
//        }
//    }

    private fun submitDAYorder(context: FragmentActivity?, username: String?, s: String, sleepageVal: Double, multiplierVal: Int,selectedprodList:String, scanData: ScanDataResponse.Response.Data.ScanData) {
        var orderStreamingController = OrderStreamingController()
        var gtoken = 0
        var side = 0
        var qty = 0
        var price = 0.0
        var ltp = ""

        if (!scanData.iToken_0.equals("0")) {

            gtoken = scanData.iToken_0.toInt()
            side = scanData.iBuySell_0.toInt()
            qty = scanData.iUnit_0.toInt() * multiplierVal
            ltp = ""
            price = if (scanData.iBuySell_0.equals("1")) {
                scanData.dRate_0.toDouble() + sleepageVal
            } else {
                scanData.dRate_0.toDouble() - sleepageVal
            }

            var iocOrderRequest = DayData(
                    SymbolName = "",
                    requestType = "NewOrderRequest",
                    gcid = username!!,
                    amo = "0",
                    corderid = "1",
                    disclosed_qty = "0",
                    exchange = getExchange(scanData.iToken_0)!!,
                    gtoken = gtoken.toString(),
                    is_post_closed = "0",
                    is_preopen_order = "0",
                    isSqOffOrder = "false",
                    lot = 1.toString(),
                    ltp = ltp,
                    offline = "0",
                    order_type = "1",
                    price = price.toString(),
                    product = selectedprodList.toString(),
                    qty = qty.toString(),
                    side = side.toString(),
                    sl_price = "0.00",
                    target_price = "0.00",
                    trigger_price = "0.00",
                    validity = "0"
            )


            orderStreamingController.sendStreamingSendOrderIOCRequest(context, JSONObject(Gson().toJson(iocOrderRequest)))
        }

        if (!scanData.iToken_1.equals("0")) {

            gtoken = scanData.iToken_1.toInt()
            side = scanData.iBuySell_1.toInt()
            qty = scanData.iUnit_1.toInt() * multiplierVal
            ltp = ""
            price = if (scanData.iBuySell_1.equals("1")) {
                scanData.dRate_1.toDouble() + sleepageVal
            } else {
                scanData.dRate_1.toDouble() - sleepageVal
            }

            var iocOrderRequest = DayData(
                    SymbolName = "",
                    requestType = "NewOrderRequest",
                    gcid = username!!,
                    amo = "0",
                    corderid = "1",
                    disclosed_qty = "0",
                    exchange = getExchange(scanData.iToken_0)!!,
                    gtoken = gtoken.toString(),
                    is_post_closed = "0",
                    is_preopen_order = "0",
                    isSqOffOrder = "false",
                    lot = 1.toString(),
                    ltp = ltp,
                    offline = "0",
                    order_type = "1",
                    price = price.toString(),
                    product = selectedprodList,
                    qty = qty.toString(),
                    side = side.toString(),
                    sl_price = "0.00",
                    target_price = "0.00",
                    trigger_price = "0.00",
                    validity = "0"
            )

            orderStreamingController.sendStreamingSendOrderIOCRequest(context, JSONObject(Gson().toJson(iocOrderRequest)))


        }

        if (!scanData.iToken_2.equals("0")) {

            gtoken = scanData.iToken_2.toInt()
            side = scanData.iBuySell_2.toInt()
            qty = scanData.iUnit_2.toInt() * multiplierVal
            ltp = ""
            price = if (scanData.iBuySell_2.equals("1")) {
                scanData.dRate_2.toDouble() + sleepageVal
            } else {
                scanData.dRate_2.toDouble() - sleepageVal
            }

            var iocOrderRequest = DayData(
                    SymbolName = "",
                    requestType = "NewOrderRequest",
                    gcid = username!!,
                    amo = "0",
                    corderid = "1",
                    disclosed_qty = "0",
                    exchange = getExchange(scanData.iToken_0)!!,
                    gtoken = gtoken.toString(),
                    is_post_closed = "0",
                    is_preopen_order = "0",
                    isSqOffOrder = "false",
                    lot = 1.toString(),
                    ltp = ltp,
                    offline = "0",
                    order_type = "1",
                    price = price.toString(),
                    product = selectedprodList,
                    qty = qty.toString(),
                    side = side.toString(),
                    sl_price = "0.00",
                    target_price = "0.00",
                    trigger_price = "0.00",
                    validity = "0"
            )
            orderStreamingController.sendStreamingSendOrderIOCRequest(context, JSONObject(Gson().toJson(iocOrderRequest)))
        }

        if (!scanData.iToken_3.equals("0")) {

            gtoken = scanData.iToken_3.toInt()
            side = scanData.iBuySell_3.toInt()
            qty = scanData.iUnit_3.toInt() * multiplierVal
            ltp = ""
            price = if (scanData.iBuySell_3.equals("1")) {
                scanData.dRate_3.toDouble() + sleepageVal
            } else {
                scanData.dRate_3.toDouble() - sleepageVal
            }
            var iocOrderRequest = DayData(
                    SymbolName = "",
                    requestType = "NewOrderRequest",
                    gcid = username!!,
                    amo = "0",
                    corderid = "1",
                    disclosed_qty = "0",
                    exchange = getExchange(scanData.iToken_0)!!,
                    gtoken = gtoken.toString(),
                    is_post_closed = "0",
                    is_preopen_order = "0",
                    isSqOffOrder = "false",
                    lot = 1.toString(),
                    ltp = ltp,
                    offline = "0",
                    order_type = "1",
                    price = price.toString(),
                    product = selectedprodList,
                    qty = qty.toString(),
                    side = side.toString(),
                    sl_price = "0.00",
                    target_price = "0.00",
                    trigger_price = "0.00",
                    validity = "0"
            )

            orderStreamingController.sendStreamingSendOrderIOCRequest(context, JSONObject(Gson().toJson(iocOrderRequest)))
        }
    }

    private fun submitIOCorder(context: FragmentActivity?, selectedStrategyName: String, username: String, validity: String, sleepageVal: Double, multiplierVal: Int,selectedprodList:String, scanData: ScanDataResponse.Response.Data.ScanData) {

        var noofLegs = 0
        var gtoken = 0
        var side = 0
        var qty = 0
        var price = 0.0
        var ltp = "0"
        var gtoken2 = 0
        var side2 = 0
        var qty2 = 0
        var price2 = 0.0
        var ltp2 = "0"
        var gtoken3 = 0
        var side3 = 0
        var qty3 = 0
        var price3 = 0.0
        var ltp3 = "0"
        var gtoken4 = 0
        var side4 = 0
        var qty4 = 0
        var price4 = 0.0
        var ltp4 = "0"

        if (!scanData.iToken_0.equals("0")) {

            if (validity.equals("1")) //IOC Order
            {
                noofLegs = 1
                gtoken = scanData.iToken_0.toInt()
                side = scanData.iBuySell_0.toInt()
                qty = scanData.iUnit_0.toInt() * multiplierVal
                ltp = "0"
                price = if (scanData.iBuySell_0.equals("1")) {
                    scanData.dRate_0.toDouble() + sleepageVal
                } else {
                    scanData.dRate_0.toDouble() - sleepageVal
                }

            } else {


            }
        }

        if (!scanData.iToken_1.equals("0")) {

            if (validity.equals("1")) //IOC Order
            {
                noofLegs = 2
                gtoken2 = scanData.iToken_1.toInt()
                side2 = scanData.iBuySell_1.toInt()
                qty2 = scanData.iUnit_1.toInt() * multiplierVal
                ltp2 = "0"
                price2 = if (scanData.iBuySell_1.equals("1")) {
                    scanData.dRate_1.toDouble() + sleepageVal
                } else {
                    scanData.dRate_1.toDouble() - sleepageVal
                }

            } else {

            }
        }

        if (!scanData.iToken_2.equals("0")) {

            if (validity.equals("1")) //IOC Order
            {
                noofLegs = 3
                gtoken3 = scanData.iToken_2.toInt()
                side3 = scanData.iBuySell_2.toInt()
                qty3 = scanData.iUnit_2.toInt() * multiplierVal
                ltp3 = "0"
                price3 = if (scanData.iBuySell_2.equals("1")) {
                    scanData.dRate_2.toDouble() + sleepageVal
                } else {
                    scanData.dRate_2.toDouble() - sleepageVal
                }

            } else {

            }
        }

        if (!scanData.iToken_3.equals("0")) {

            if (validity.equals("1")) //IOC Order
            {
                noofLegs = 4
                gtoken4 = scanData.iToken_3.toInt()
                side4 = scanData.iBuySell_3.toInt()
                qty4 = scanData.iUnit_3.toInt() * multiplierVal
                ltp4 = "0"
                price4 = if (scanData.iBuySell_3.equals("1")) {
                    scanData.dRate_3.toDouble() + sleepageVal
                } else {
                    scanData.dRate_3.toDouble() - sleepageVal
                }

            } else {

            }
        }


        var iocOrderRequest = Data(
                strategyName = selectedStrategyName,
                iStrategyNo = "55",
                SymbolName = "",
                requestType = "NewOrderRequest",
                gcid = username,
                amo = "0",
                corderid = "1",
                disclosed_qty = "0",
                exchange = getExchange(scanData.iToken_0)!!,
                gtoken = gtoken.toString(),
                gtoken2 = gtoken2.toString(),
                gtoken3 = gtoken3.toString(),
                gtoken4 = gtoken4.toString(),
                is_post_closed = "0",
                is_preopen_order = "0",
                isSqOffOrder = "false",
                lot = 1.toString(),
                ltp = ltp,
                ltp2 = ltp2,
                ltp3 = ltp3,
                ltp4 = ltp4,
                noofLegs = noofLegs.toString(),
                offline = "0",
                order_type = "1",
                price = price.toString(),
                price2 = price2.toString(),
                price3 = price3.toString(),
                price4 = price4.toString(),
                product = selectedprodList,
                qty = qty.toString(),
                qty2 = qty2.toString(),
                qty3 = qty3.toString(),
                qty4 = qty4.toString(),
                side = side.toString(),
                side2 = side2.toString(),
                side3 = side3.toString(),
                side4 = side4.toString(),
                sl_price = "0.00",
                target_price = "0.00",
                trigger_price = "0.00",
                validity = validity
        )

        var orderStreamingController = OrderStreamingController()

        orderStreamingController.sendStreamingSendOrderIOCRequest(context, JSONObject(Gson().toJson(iocOrderRequest)))

    }


    fun onClickSettingoption(view: View) {
        showFilertMenu(view)
    }

    fun showFilertMenu(view: View) {

        var chk_get = false
        var chk_pay = false
        var expiryDate = ""

        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.sf_filter_layout, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(view.context).setView(mDialogView)

        //show dialog
        val mAlertDialog = mBuilder.show()
        if (AccountDetails.getThemeFlag(view.context).equals("white")) {
            mDialogView.scroll_view.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.premium.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.call_put_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.max_loss_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.maxloss_seekbar.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.maxprofit_seekbar.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.checkbox_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.checkbox_layout2.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.chk_get.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogView.chk_pay.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogView.filter_header_text.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
            mDialogView.FilterRecyclerView.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.differRecyclerView.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView.premium.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_maxLoss.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.txt_maxProfit.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.call.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.put.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.expiry.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.max_loss.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.max_loss_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.max_profit.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.max_profit_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.strategy_type.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.spread_gaps.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
        }


        val Adapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(view.context!!, AccountDetails.getRowSpinnerSimple(), expirylList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(view.context).equals("white", ignoreCase = true)) {
                    v.setTextColor(view.context.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(view.context.resources.getColor(R.color.white))
                }
                v.setPadding(15, 5, 15, 5)
                return v
            }

            /*  override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                  val v = super.getView(position, convertView, parent) as TextView
                  //  v.setTypeface(font);
                  v.setTextColor(Color.BLACK)
                  v.setPadding(15, 15, 15, 15)
                  return v
              }*/
        }
        Adapter.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.findViewById<Spinner>(R.id.spinner_expiry).setAdapter(Adapter)

        mDialogView.spinner_expiry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                Util.getPrefs(view?.context).edit().putInt("SelectedPosition", p2).commit()

                if (expirylList.get(p2).equals("All Expiry Option")) {
//                    expiryDate = "All"
                    expiryDate = expiryTimeStamplList.get(0)
                    expiryDate = (expiryDate.toLong() - 315513000).toString()

                } else {
                    expiryDate = expiryTimeStamplList.get(p2 - 1)
                    expiryDate = (expiryDate.toLong() - 315513000).toString()
                }
            }
        }
        var position = Util.getPrefs(view?.context).getInt("SelectedPosition", 0)
        mDialogView.spinner_expiry.setSelection(position)

        var categoryAdapter = CategoryAdapter(this, false)
        val categoryLinearLayoutManager = LinearLayoutManager(view.context)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mDialogView.FilterRecyclerView.layoutManager = categoryLinearLayoutManager

        val json: String? = Util.getPrefs(view.context).getString("SavedStrategy", "")
        val type: Type = object : TypeToken<ArrayList<StrategyName?>?>() {}.type


        if (mCategoryList.size > 0) {
            categoryAdapter.setAppList(mCategoryList)
        } else if (json != null && json.isNotEmpty()) {
            val list: ArrayList<StrategyName> = Gson().fromJson(json, type)
            if (list.size > 0) {
                categoryAdapter.setAppList(list)

            }
        } else {
            categoryAdapter.setAppList(
                    prePareStrategyList(scanPostRequest.request.data.iMarketTitle,
                            scanPostRequest.request.data.iVolatalityTitle))
        }

        mDialogView.FilterRecyclerView.adapter = categoryAdapter

        var differAdapter = DifferCategoryAdapter(this, false)
        val differLinearLayoutManager = LinearLayoutManager(view.context)
        differLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mDialogView.differRecyclerView.layoutManager = differLinearLayoutManager

        var arrayList = ArrayList<StrategyName>()
        strikeDiffList.forEach {
            arrayList.add(StrategyName("1", false, it))
        }

        try {
            val selectedIntervalIndex = Util.getPrefs(view.context).getInt("selectedIntervalIndex", 1)

            arrayList.get(selectedIntervalIndex - 1).StrategyCheck = true

            differAdapter.setAppList(arrayList)
            mDialogView.differRecyclerView.adapter = differAdapter


            mDialogView.seekBAr_maxLoss.max = LossMaxValue
            mDialogView.seekBAr_maxLoss.min = LossMinValue

            if (filterMaxLoss.isNotEmpty()) {
                mDialogView.txt_maxLoss.setText("" + filterMaxLoss)
                mDialogView.seekBAr_maxLoss.setProgress(filterMaxLoss.toFloat())
            } else {
                mDialogView.txt_maxLoss.setText("" + LossMaxValue)
                mDialogView.seekBAr_maxLoss.setProgress(LossMaxValue)
            }
        } catch (e: Exception) {
        }


        mDialogView.seekBAr_maxLoss.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {

                if (seekBar!!.progress != 0)
                    mDialogView.txt_maxLoss.setText("" + seekBar!!.progress)
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                if (seekBar!!.progress != 0)
                    mDialogView.txt_maxLoss.setText("" + seekBar!!.progress)
            }
        }

        mDialogView.seekBAr_maxProfit.max = ProfitMaxValue
        mDialogView.seekBAr_maxProfit.min = ProfitMInValue

        if (filterMaxProfit.isNotEmpty()) {

            mDialogView.txt_maxProfit.setText("" + filterMaxProfit)
            mDialogView.seekBAr_maxProfit.setProgress(filterMaxProfit.toFloat())

        } else {

            mDialogView.txt_maxProfit.setText("" + ProfitMaxValue)
            mDialogView.seekBAr_maxProfit.setProgress(ProfitMaxValue)
        }




        mDialogView.seekBAr_maxProfit.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {

            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {

                mDialogView.txt_maxProfit.setText("" + seekBar!!.progress)
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                mDialogView.txt_maxProfit.setText("" + seekBar!!.progress)


            }
        }



        mDialogView.findViewById<GreekButton>(R.id.btn_filter).setOnClickListener {


            var iCallPut = ""
            if (chk_get) {
                iCallPut = "1"
            }
            if (chk_pay) {
                iCallPut = "2"
            }
            if (chk_pay && chk_get) {
                iCallPut = "3"
            } else if (!chk_pay && !chk_get) {
                iCallPut = "0"
            }


            Util.getPrefs(view?.context).edit().putInt("selectedIntervalIndex",
                    differAdapter.getCheckedIntervalIndexDiffer().toInt()).commit()

            scanPostRequest.request.data.lExpDate = expiryDate
            scanPostRequest.request.data.iCallPut = ""
            scanPostRequest.request.data.iCallPutSelection = iCallPut
            scanPostRequest.request.data.dStrikeDiff = differAdapter.getCheckedDiffer()
            scanPostRequest.request.data.iStrikeInterval = differAdapter.getCheckedIntervalIndexDiffer()


            strategyList.clear()

            mCategoryList = categoryAdapter.getCheckedCategoryList()

            categoryAdapter.getCheckedIDList().forEach {
                strategyList.add(getStrategyTypeLis(it.toInt()))
            }
            scanPostRequest.request.data.iStrategyType = strategyList

            filterMaxProfit = mDialogView.txt_maxProfit.text.toString()
            filterMaxLoss = mDialogView.txt_maxLoss.text.toString()

            onClickScanView(activitys, scanPostRequest)

            mAlertDialog.cancel()

        }

        mDialogView.chk_get.isChecked = Util.getPrefs(activitys).getBoolean("chk_get", false)
        mDialogView.chk_pay.isChecked = Util.getPrefs(activitys).getBoolean("chk_pay", false)

        mDialogView.findViewById<CheckBox>(R.id.chk_get).setOnCheckedChangeListener { compoundButton, b ->
            chk_get = b
            Util.getPrefs(activitys).edit().putBoolean("chk_get", b).commit()
        }
        mDialogView.findViewById<CheckBox>(R.id.chk_pay).setOnCheckedChangeListener { compoundButton, b ->
            chk_pay = b
            Util.getPrefs(activitys).edit().putBoolean("chk_pay", b).commit()

        }

        val close = mDialogView.findViewById<ImageView>(R.id.popup_close)
        close.setOnClickListener {
            mAlertDialog.cancel()
        }
    }


    private fun getExchange(token: String): String? {
        val tokenInt = token.toInt()
        return if (tokenInt <= 101000000 && tokenInt <= 102999999 || tokenInt <= 502000000 && tokenInt <= 502999999) {
            "NSE"
        } else if (tokenInt <= 403000000 && tokenInt <= 403999999) {
            "NCDEX"
        } else if (tokenInt <= 303000000 && tokenInt <= 303999999) {
            "MCX"
        } else {
            "BSE"
        }
    }


}



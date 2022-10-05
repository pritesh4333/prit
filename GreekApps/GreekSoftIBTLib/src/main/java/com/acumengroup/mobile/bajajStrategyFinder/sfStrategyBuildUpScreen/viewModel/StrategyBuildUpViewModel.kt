package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.viewModel

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.BaseViewModel
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.HomeScreenRepository
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.SymbolModel
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter.StrategyNameAdapter
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.*
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.strategyBuildUpRepository
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.SFBuildUpResponse
import com.acumengroup.ui.button.GreekButton
import com.acumengroup.ui.edittext.GreekEditText
import com.acumengroup.ui.textview.GreekTextView
import com.acumengroup.greekmain.util.Util
import com.acumengroup.greekmain.util.date.DateTimeFormatter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.sf_build_filter_setting.*
import kotlinx.android.synthetic.main.sf_buildup_builder_layout.view.*
import kotlinx.android.synthetic.main.sf_delete_portfolio_layout.view.*
import kotlinx.android.synthetic.main.sf_delete_portfolio_layout.view.StrategyRecyclerView
import kotlinx.android.synthetic.main.sf_manual_trade_entry_layout.view.*
import kotlinx.android.synthetic.main.sf_new_portfolio_layout.view.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StrategyBuildUpViewModel : BaseViewModel(), StrategyNameAdapter.OnCategoryClickListener {

    var dl = MutableLiveData<String>()
    var deltaVal = MutableLiveData<String>()
    var thetaVal = MutableLiveData<String>()
    var vegaVal = MutableLiveData<String>()
    var gammaVal = MutableLiveData<String>()
    var fundUtilised = MutableLiveData<String>()
    var balance = MutableLiveData<String>()
    var expense = MutableLiveData<String>()
    var mTom = MutableLiveData<String>()
    var selectedSymbolLive = MutableLiveData<String>()
    var selectedExpiryDateLive = MutableLiveData<String>()
    var selectedStrategyLive = MutableLiveData<String>()
    var refreshBottomTabs = MutableLiveData<Boolean>()
    val strikePriceListLive = MutableLiveData<List<String>>()
    val symboleListLive = MutableLiveData<List<String>>()
    val strikePriceTokenListLive = MutableLiveData<List<String>>()
    val expiryListLive = MutableLiveData<List<String>>()
    var dMidStrike = "0"
    var dStrikeDiff = "0"
    var userName = ""
    var cSymbolSearch = ""
    var gToken :Long = 1000


    var dlTextColor = MutableLiveData<Boolean>()
    var deltaValTextColor = MutableLiveData<Boolean>()
    var thetaValTextColor = MutableLiveData<Boolean>()
    var vegaValTextColor = MutableLiveData<Boolean>()
    var gammaValTextColor = MutableLiveData<Boolean>()
    var fundUtilisedTextColor = MutableLiveData<Boolean>()
    var balanceTextColor = MutableLiveData<Boolean>()
    var expenseTextColor = MutableLiveData<Boolean>()
    var mTomTextColor = MutableLiveData<Boolean>()
    var addedPortFolio = MutableLiveData<Boolean>()
    var deletePortFolio = MutableLiveData<Boolean>()

    var srategyBuildDataLive = MutableLiveData<ArrayList<SrategyBuildData.Response.Data.BuildData>>()
    var srategyChartDataLive = MutableLiveData<ArrayList<ChartGraphData.Response.Data.ChartData>>()

    var strategyNameAdapter: StrategyNameAdapter? = null
    val checkedIdList = ArrayList<StrategyNameResponse.Response.Data.StrategyName>()
    var strategyArrayList = ArrayList<StrategyNameResponse.Response.Data.StrategyName>()
    var strategyNameList = ArrayList<String>()
    var marketList = ArrayList<String>()
    var symbolArrayList = ArrayList<String>()
    var expiryArrayList = ArrayList<String>()
    var baseArrayList = ArrayList<String>()
    var expirylList = ArrayList<String>()
    var expiryTimeStamplList = ArrayList<String>()

    private var symbolListLive = ArrayList<String>()


    lateinit var adapterExpiry: ArrayAdapter<String>
    lateinit var adapterStrategy: ArrayAdapter<String>
    lateinit var adapterBase: ArrayAdapter<String>
    lateinit var adapterSymbol: ArrayAdapter<String>


    lateinit var adapter2: ArrayAdapter<String>
    lateinit var adapters: ArrayAdapter<String>//not initilized
    lateinit var Expadapters: ArrayAdapter<String>//not initilized
    lateinit var adapterStr: ArrayAdapter<String>

    var showFiltter = false
    var marektSelect = ""
    var selectedExpiryDate = ""
    var selectedSymbol = ""
    var selectedStrategy = ""
    var selectedBase = ""
    var isCheckedStrikeIV = "0"
    var isFilterApply = false
    var selectedMarketRate = "0.0"
    var selectedDays = "0.0"
    var selectedCall = "0.0"
    var selectedPut = "0.0"
    var selectedIntrstRate = "0.0"
    var dayDiff = "0"
    var mktRate = "0"
    var mDialogViewMenu: View? = null

    fun fetchSymbolList(symbolList: String?) {
        if (symbolList!!.isEmpty()) {
            dataLoading.value = true
            HomeScreenRepository.getInstance().getRepoList(
                    fun(isSuccess: Boolean, response: SymbolModel.SymbolInfo?) {
                        if (isSuccess) {
                            symbolListLive = response?.data?.map { it.symbol } as ArrayList<String>

                            empty.value = false
                        } else {
                            empty.value = true
                        }
                        dataLoading.value = false
                    })
        } else {
            val type: Type = object : TypeToken<ArrayList<String>?>() {}.type
            symbolListLive = Gson().fromJson(symbolList, type)
        }


    }

    fun fetchExpiryList(symbol: String) {

        dataLoading.value = true

        HomeScreenRepository.getInstance().getExpiryList(symbol, fun(isSuccess: Boolean, response: SymbolModel.ExpiryDate?) {
            if (isSuccess) {
                expirylList.clear()
                expiryTimeStamplList.clear()
                response?.data?.forEach {
                    var date = DateTimeFormatter.getDateFromTimeStamp(it.expiry, "dd MMM yyyy", "bse")
                    expirylList.add(date)
                    expiryTimeStamplList.add(it.expiry)
                    adapter2.setNotifyOnChange(true)
                    adapter2.notifyDataSetChanged()
                }
                empty.value = false
            } else {
                empty.value = true
            }
            dataLoading.value = false

        })

    }

    fun StrategyBuilderCloseEventRequest() {


        var sbCloseEventRequest = SBCloseEventRequest(
                SBCloseEventRequest.Request(
                        svcVersion = "1.0.0",
                        requestType = "U",
                        svcName = "StrategyBuilderEvent",
                        svcGroup = "portfolio",
                        FormFactor = "M",
                        data = SBCloseEventRequest.Request.Data(
                                iErrorCode = 1036,
                                cGreekClientID = userName
                        )
                )
        )

        strategyBuildUpRepository.getInstance().StrategyBuildCloseEventRequest(sbCloseEventRequest,
                fun(isSuccess: Boolean, response: StratefyBuildEventResponse?) {
                    if (isSuccess) {
                        var ErrorCode = response!!.response.data.ErrorCode

                        empty.value = false
                    } else {
                        empty.value = true
                    }
                    dataLoading.value = false

                })


    }

    fun StrategyBuilderEventForStorage(username: String, cSymbol: String, dStrikeDif: String, dMidStrik: String) {

        dStrikeDiff = dStrikeDif
        dMidStrike = dMidStrik
        userName = username

        var strategyBuilderEventRequest = StrategyBuildEventRequest(StrategyBuildEventRequest.Request(
                svcVersion = "1.0.0",
                requestType = "U",
                svcName = "StrategyBuilderEvent",
                svcGroup = "portfolio",
                FormFactor = "M",
                data = StrategyBuildEventRequest.Request.Data(
                        iErrorCode = 1039,
                        cGreekClientId = username,
                        cExchange = "NSE",
                        cClientCode = username,
                        cSymbol = if (cSymbol.isEmpty()) "All" else cSymbol,
                        cStrategyName = if (selectedStrategy.isEmpty()) "All" else selectedStrategy,
                        cExpiryDate = if (selectedExpiryDate.isEmpty()) "All" else selectedExpiryDate
                )))

        strategyBuildUpRepository.getInstance().StrategyBuildEventRequest(strategyBuilderEventRequest,
                fun(isSuccess: Boolean, response: StratefyBuildEventResponse?) {
                    if (isSuccess) {
                        var ErrorCode = response!!.response.data.ErrorCode

                        if (ErrorCode == 0) {

                            var cData: StratefyBuildEventResponse.Response.Data.SBEventData = response.response.data.data.get(0)

                            val lstValues: ArrayList<String> = cData.cData.split("|") as ArrayList<String>

                            strategyNameList.clear()
                            symbolArrayList.clear()
                            expiryArrayList.clear()

                            var symbolName = lstValues.get(0).removePrefix("Symbol:")
                            var strategyName = lstValues.get(1).removePrefix("Stratgy:")
                            var ExpiryName = lstValues.get(2).removePrefix("Expiry:")


                            if (!symbolName.equals("Symbol")) {

                                if (symbolName.contains(",")) {
                                    var symbolyList = symbolName.split(",") as ArrayList<String>

                                    symbolyList.forEach {
                                        symbolArrayList.add(it)
                                    }
                                } else {
                                    symbolArrayList.add(symbolName)

                                }
                            }

                            if (!strategyName.equals("Stratgy")) {

                                strategyNameList.add(0, "All")


                                if (strategyName.contains(",")) {

                                    var strategyList = strategyName.split(",") as ArrayList<String>
                                    strategyList.forEach { strategyNameList.add(it) }

                                } else {
                                    strategyNameList.add(strategyName)
                                }

                                selectedSymbolLive.value = cSymbolSearch
                                selectedStrategyLive.value = "All"
                                selectedExpiryDateLive.value = "All"
                                refreshBottomTabs.value = true

                                StrategyBuilderView(username, symbolArrayList.get(0), dStrikeDiff, dMidStrike)

                            } else {

                                //it's continues calling same API request in loop, to avoid deadlock of request API commented by Aditya & Ravi
                              /*  if (symbolArrayList.size > 0) {
                                    StrategyBuilderEventForStorage(username, symbolArrayList.get(0), dStrikeDiff, dMidStrike)
                                }*/

                            }

                            if (!ExpiryName.equals("Expiry")) {

                                expiryArrayList.add(0, "All")

                                if (ExpiryName.contains(",")) {
                                    var expiryList = ExpiryName.split(",") as ArrayList<String>
                                    expiryList.forEach {
                                        expiryArrayList.add(it)
                                    }

                                } else {

                                    expiryArrayList.add(ExpiryName)
                                }

                            }

                            if (symbolName.equals("Symbol") && strategyName.equals("Stratgy") && ExpiryName.equals("Expiry")) {

                                StrategyBuilderView(username, cSymbolSearch, dStrikeDiff, dMidStrike)
                            }


                            if (showFiltter) {
                                adapterExpiry!!.notifyDataSetChanged()
                                adapterStrategy!!.notifyDataSetChanged()
                                adapterSymbol!!.notifyDataSetChanged()
                            }
                        }

                        empty.value = false
                    } else {
                        empty.value = true
                    }
                    dataLoading.value = false

                })
    }

    fun StrategyBuilderEvent(username: String) {

        var strategyBuilderEventRequest = StrategyBuilderEventRequest(StrategyBuilderEventRequest.Request(
                svcVersion = "1.0.0",
                requestType = "U",
                svcName = "StrategyBuilderEvent",
                svcGroup = "portfolio",
                FormFactor = "M",
                data = StrategyBuilderEventRequest.Request.Data(
                        cGreekClientID = username,
                        iErrorCode = "1040")))

        strategyBuildUpRepository.getInstance().StrategyBuilderEventRequest(strategyBuilderEventRequest,
                fun(isSuccess: Boolean, response: StrategyNameResponse?) {
                    if (isSuccess) {

                        var ErrorCode = response!!.response.data.ErrorCode

                        if (ErrorCode == 0) {

                            strategyArrayList = response.response.data.data
                            strategyNameAdapter?.setAppList(strategyArrayList)
                        }

                        empty.value = false
                    } else {
                        empty.value = true
                    }
                    dataLoading.value = false

                })
    }

    fun StrategyBuilderView(username: String, cSymbol: String, dStrikeDiff: String, dMidStrike: String) {

        var strategyBuilderViewRequest: StrategyBuilderViewRequest

        if (isFilterApply) {

            strategyBuilderViewRequest = StrategyBuilderViewRequest(StrategyBuilderViewRequest.Request(
                    FormFactor = "M",
                    svcGroup = "portfolio",
                    svcName = "getStrategyBuilderView",
                    svcVersion = "1.0.0",
                    requestType = "u",
                    data = StrategyBuilderViewRequest.Request.Data(
                            cSymbol = if (cSymbol.isEmpty()) "All" else cSymbol,
                            cExchange = "NSE",
                            cClientCode = username,
                            cEFBase = selectedBase,
                            cExpiry = selectedExpiryDate,
                            cGreekClientID = username,
                            cReportType = "",
                            cStrategy = selectedStrategy,
                            dCallIV = selectedCall.toDouble(),
                            dDaysLeft = selectedDays.toDouble(),
                            dInterestRate = selectedIntrstRate.toDouble(),
                            dMarketRate = selectedMarketRate.toDouble(),
                            dMidStrike = 0.0,
                            dPutIV = selectedPut.toDouble(),
                            dStrikeDiff = 0.0,
                            iIVType = isCheckedStrikeIV.toInt(),  // 0 or 1
                            iRangeD = 0,
                            iRangeU = 0
                    )))

        } else {


            strategyBuilderViewRequest = StrategyBuilderViewRequest(StrategyBuilderViewRequest.Request(
                    FormFactor = "M",
                    svcGroup = "portfolio",
                    svcName = "getStrategyBuilderView",
                    svcVersion = "1.0.0",
                    requestType = "u",
                    data = StrategyBuilderViewRequest.Request.Data(
                            cSymbol = if (cSymbol.isEmpty()) "All" else cSymbol,
                            cExchange = "NSE",
                            cClientCode = username,
                            cEFBase = "F",
                            cExpiry = "All",
                            cGreekClientID = username,
                            cReportType = "",
                            cStrategy = "All",
                            dCallIV = 0.0,
                            dDaysLeft = 0.0,
                            dInterestRate = 0.0,
                            dMarketRate = 0.0,
                            dMidStrike = 0.0,
                            dPutIV = 0.0,
                            dStrikeDiff = 0.0,
                            iIVType = 0,
                            iRangeD = 0,
                            iRangeU = 0
                    )))
        }
        strategyBuildUpRepository.getInstance().StrategyBuilderViewRequest(strategyBuilderViewRequest,
                fun(isSuccess: Boolean, response: SrategyBuildData?) {
                    if (isSuccess) {
                        var ErrorCode = response!!.response.data.ErrorCode
                        if (ErrorCode.equals("0")) {
                            //try {

                            StrategyChartBuilderView(username, cSymbol, dStrikeDiff, dMidStrike, dMidStrike)
                            var responseData = ArrayList<SrategyBuildData.Response.Data.BuildData>()
                            response.response.data.data.forEach {
                                if (it.iDataType.equals("0")) {

                                    it.dMktRate = if (it.dMktRate == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dMktRate?.toDouble())
                                    }

                                    mktRate = if (it.dMktRate == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dMktRate?.toDouble())
                                    }

                                    if (mDialogViewMenu != null) {
                                        mDialogViewMenu!!.edt_marketRate.setText(mktRate)
                                    }

                                    dayDiff = it.dDateDiff

                                    it.dMotm = if (it.dMotm == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dMotm?.toDouble())
                                    }

                                    it.dTheroticalPrice = if (it.dTheroticalPrice == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dTheroticalPrice?.toDouble())
                                    }

                                    it.dLtp = if (it.dLtp == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dLtp?.toDouble())
                                    }

                                    it.dDelta = if (it.dDelta == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dDelta?.toDouble())
                                    }
                                    it.dStrike = if (it.dStrike == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dStrike?.toDouble())
                                    }

                                    it.dLastIV = if (it.dLastIV == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dLastIV?.toDouble())
                                    }

                                    responseData.add(it)

                                } else if (it.iDataType.equals("10")) {

                                    dl.value = if (it.dDeltaNeutral == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dDeltaNeutral?.toDouble())
                                    }

                                    deltaVal.value = if (it.dDelta == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dDelta?.toDouble())
                                    }


                                    thetaVal.value = if (it.dTheta == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dTheta?.toDouble())
                                    }


                                    vegaVal.value = if (it.dVega == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dVega?.toDouble())
                                    }



                                    gammaVal.value = if (it.dGamma == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dGamma?.toDouble())
                                    }


                                    fundUtilised.value = if (it.dFundUtilised == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dFundUtilised?.toDouble())
                                    }

                                    balance.value = if (it.dBalance == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dBalance?.toDouble())
                                    }

                                    expense.value = if (it.dExpense == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dExpense?.toDouble())
                                    }


                                    mTom.value = if (it.dMTOM == null) {
                                        "0.0"
                                    } else {
                                        String.format("%.2f", it.dMTOM?.toDouble())
                                    }

                                    dlTextColor.value = (String.format("%.2f", it.dDeltaNeutral?.toDouble()).startsWith("-"))
                                    deltaValTextColor.value = (String.format("%.2f", it.dDelta?.toDouble()).startsWith("-"))
                                    thetaValTextColor.value = (String.format("%.2f", it.dTheta?.toDouble()).startsWith("-"))
                                    vegaValTextColor.value = (String.format("%.2f", it.dVega?.toDouble()).startsWith("-"))
                                    gammaValTextColor.value = (String.format("%.2f", it.dGamma?.toDouble()).startsWith("-"))
                                    fundUtilisedTextColor.value = (String.format("%.2f", it.dFundUtilised?.toDouble()).startsWith("-"))
                                    balanceTextColor.value = (String.format("%.2f", it.dBalance?.toDouble()).startsWith("-"))
                                    expenseTextColor.value = (String.format("%.2f", it.dExpense?.toDouble()).startsWith("-"))
                                    mTomTextColor.value = (String.format("%.2f", it.dMTOM?.toDouble()).startsWith("-"))

                                }
                            }

                            srategyBuildDataLive.value = responseData

                            //} catch (e: Exception) {
                            //Log.e("error",""+e);
                            //}
                            empty.value = false
                        } else
                            empty.value = true
                    } else {
                        empty.value = true
                    }
                    dataLoading.value = false
                })
    }

    fun StrategyChartBuilderView(username: String, cSymbol: String, dStrikeDiff: String, dMidStrike: String, dMarketRate: String) {

        var strategyBuilderViewChartRequest = StrategyBuilderViewRequest(StrategyBuilderViewRequest.Request(
                FormFactor = "M",
                svcGroup = "portfolio",
                svcName = "getStrategyBuilderView",
                svcVersion = "1.0.0",
                requestType = "u",
                data = StrategyBuilderViewRequest.Request.Data(
                        cSymbol = if (cSymbol.isEmpty()) "NIFTY" else cSymbol,
                        cExchange = "NSE",
                        cClientCode = username,
                        cEFBase = "F",
                        cExpiry = "All",
                        cGreekClientID = username,
                        cReportType = "S",   //  var ="V"    accc="A"  report="S"
                        cStrategy = "All",
                        dCallIV = 0.0,
                        dDaysLeft = 0.0,
                        dInterestRate = 0.0,
                        dMarketRate = dMarketRate.toDouble(),
                        dMidStrike = dMidStrike.toDouble(),
                        dPutIV = 0.0,
                        dStrikeDiff = dStrikeDiff.toDouble(),
                        iIVType = 0,
                        iRangeD = 0,
                        iRangeU = 0)))

        strategyBuildUpRepository.getInstance().StrategyBuilderChartViewRequest(strategyBuilderViewChartRequest,
                fun(isSuccess: Boolean, response: ChartGraphData?) {
                    if (isSuccess) {
                        var ErrorCode = response!!.response.data.ErrorCode
                        if (ErrorCode.equals("0")) {
                            var chartDataList = response!!.response.data.data
                            srategyChartDataLive.value = chartDataList

                            empty.value = false
                        } else
                            empty.value = true
                    } else {
                        empty.value = true
                    }
                    dataLoading.value = false

                })
    }

    fun showManualTradeEntryView(activity: FragmentActivity?, cSymbol: String, expiryDate: String, price: String, unit: String, lOurToken: String) {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.sf_manual_trade_entry_layout, null)
        val mBuilder = android.app.AlertDialog.Builder(activity).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        var selectedExpiryToken = "0"
        var selectedMkgSeg = ""
        var selectedItem = ""
        var selectedOptionType = ""
        var selectedStrike = ""
        var selectedEntry = 2
        var iBuySell = 1
        var textColorPositive = 0;
        if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
            textColorPositive = R.color.whitetheambuyColor
        } else {
            textColorPositive = R.color.dark_green_positive
        }
        var textColorNegative = R.color.dark_red_negative
        var textColorGrayColor = R.color.grayStrip_bg



        if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
            mDialogView.manualParent_layout.setBackgroundColor(activity!!.resources.getColor(R.color.white))
            mDialogView.topheader_layout.setBackgroundColor(activity!!.resources.getColor(R.color.selectColor))
            mDialogView.marktTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.markt_txt.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.symbolTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_symbol.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_strike.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.expryTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.strikeTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.optionTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.dateTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_Date.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.unitTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.tradeTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_unit.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_price.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.strategyTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.entryTXT.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView.edt_strategyName.setTextColor(activity!!.resources.getColor(AccountDetails.textColorDropdown))
        }

        StrategyBuilderEvent(AccountDetails.getUsername(activity).capitalize())


        mDialogView.edt_strategyName.threshold = 0


        marketList.add("NSE")
        marketList.add("BSE")

        val MktAdapters: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(),
                marketList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(activity!!.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(activity!!.resources.getColor(R.color.white))
                }
                v.setPadding(15, 5, 15, 5)
                return v
            }

            /*override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                //  v.setTypeface(font);
                v.setTextColor(Color.BLACK)
                v.setPadding(15, 15, 15, 15)
                return v
            }*/
        }
        MktAdapters.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.spinner_mkt.setAdapter(MktAdapters)
        mDialogView.spinner_mkt.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                marektSelect = marketList[p2]

            }
        }

        val termList = ArrayList<String>()
        termList.add("Equity")
        termList.add("Future")
        termList.add("Options")

        val Adaptersterm: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(),
                termList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(activity.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(activity.resources.getColor(R.color.white))
                }
                v.setPadding(15, 5, 15, 5)
                return v
            }

            /*override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                //  v.setTypeface(font);
                v.setTextColor(Color.BLACK)
                v.setPadding(15, 15, 15, 15)
                return v
            }*/
        }
        Adaptersterm.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.spinner_mkgSeg.setAdapter(Adaptersterm)

        mDialogView.spinner_mkgSeg.setSelection(0)
        mDialogView.spinner_mkgSeg.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedMkgSeg = termList.get(p2)

                if (selectedMkgSeg.equals("Equity")) {
                    selectedMkgSeg = "1"
                    mDialogView.spinner_expDate.setEnabled(false);
                    mDialogView.edt_strike.setEnabled(false);
                    mDialogView.spinner_optiontype.setEnabled(false);
                    mDialogView.edt_strike.setText("");
                } else if (selectedMkgSeg.equals("Future")) {
                    selectedMkgSeg = "2"
                    mDialogView.spinner_expDate.setEnabled(true);
                    mDialogView.edt_strike.setEnabled(false);
                    mDialogView.spinner_optiontype.setEnabled(false);
                    mDialogView.edt_strike.setText("");
                } else if (selectedMkgSeg.equals("Options")) {
                    selectedMkgSeg = "3"

                    mDialogView.spinner_expDate.setEnabled(true);
                    mDialogView.edt_strike.setEnabled(true);
                    mDialogView.spinner_optiontype.setEnabled(true);
                    mDialogView.edt_strike.setText("");
                }


                getManualTradeEntryDataSymbol(mDialogView, activity, "0", marektSelect, selectedMkgSeg, "", "", "", "")
            }
        }


        mDialogView.edt_symbol.setText(cSymbol)

        adapters = ArrayAdapter(activity, R.layout.row_spinner_mutualfund, symbolListLive)
        adapters.setDropDownViewResource(R.layout.custom_spinner)
        adapters.setNotifyOnChange(true)
        mDialogView.edt_symbol.threshold = 1
        mDialogView.edt_symbol.setAdapter(adapters)

        mDialogView.edt_symbol.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

            selectedItem = adapterView.getItemAtPosition(i).toString()

            mDialogView.edt_price.setText("")
            mDialogView.edt_unit.setText("")
            getManualTradeEntryDataSymbol(mDialogView, activity, "0", marektSelect, selectedMkgSeg, "", "", "", "")

            if (selectedMkgSeg.equals("3")) {
                getManualTradeEntryDataExpiry(mDialogView, activity, "1", marektSelect, selectedMkgSeg, selectedItem, "", "", "")
            }


            //fetchExpiryList(selectedItem)
            //getStrike(mDialogView, activity, selectedItem)
        })


        if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
            Expadapters = ArrayAdapter(activity, R.layout.row_spinner_mutualfund, expirylList)
        } else {
            Expadapters = ArrayAdapter(activity, R.layout.row_spinner_mutualfund_white, expirylList)
        }


        Expadapters.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.spinner_expDate.setAdapter(Expadapters)

        mDialogView.spinner_expDate.setSelection(0)
        mDialogView.spinner_expDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                selectedExpiryDate = expiryTimeStamplList.get(p2)
                getManualTradeEntryDataStrikePrice(mDialogView, activity, "2", marektSelect, selectedMkgSeg, mDialogView.edt_symbol.text.toString(), selectedExpiryDate, "", "")


            }
        }


        val optiontypelist = ArrayList<String>()
        optiontypelist.add("CE")
        optiontypelist.add("PE")


        val AdaptersOption: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(),
                optiontypelist.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(activity.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(activity.resources.getColor(R.color.white))
                }
                v.setPadding(15, 5, 15, 5)
                return v
            }

            /*override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                //  v.setTypeface(font);
                v.setTextColor(Color.BLACK)
                v.setPadding(15, 15, 15, 15)
                return v
            }*/
        }
        AdaptersOption.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.spinner_optiontype.setAdapter(AdaptersOption)

        mDialogView.spinner_optiontype.setSelection(0)
        mDialogView.spinner_optiontype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                selectedOptionType = optiontypelist[p2]

            }
        }

        val Adapters: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(),
                strategyNameList.toTypedArray()) {
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

            /*override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                //  v.setTypeface(font);
                v.setTextColor(Color.BLACK)
                v.setPadding(15, 15, 15, 15)
                return v
            }*/
        }
        Adapters.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.edt_strategyName.setAdapter(Adapters)


        mDialogView.edt_strategyName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                mDialogView.edt_price.setText("")
                mDialogView.edt_unit.setText("")

            }
        }


//        if (!mDialogView.edt_symbol.text.isEmpty()) {
//
//            //getStrike(mDialogView, activity, mDialogView.edt_symbol.text.toString())
//        }
        mDialogView.edt_strike.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

            selectedStrike = adapterView.getItemAtPosition(i).toString()
            getManualTradeEntryDataToken(mDialogView, activity, "3", marektSelect, selectedMkgSeg, mDialogView.edt_symbol.text.toString(), selectedExpiryDate, selectedOptionType, selectedStrike)

        })

        val entryList = ArrayList<String>()
        entryList.add("PERMANENT")
        entryList.add("TEMPORARY")
        entryList.add("OPENPOSITION")

        val adapter1: ArrayAdapter<String?> = object : ArrayAdapter<String?>(activity!!, AccountDetails.getRowSpinnerSimple(),
                entryList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(activity.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(activity.resources.getColor(R.color.white))
                }
                v.setPadding(15, 5, 15, 5)
                return v
            }

            /*override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                //  v.setTypeface(font);
                v.setTextColor(Color.BLACK)
                v.setPadding(15, 15, 15, 15)
                return v
            }*/
        }
        adapter1.setDropDownViewResource(R.layout.custom_spinner)
        mDialogView.spinner_entryType.setAdapter(adapter1)

        mDialogView.spinner_entryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if (entryList.get(p2).equals("PERMANENT")) {
                    selectedEntry = 2
                } else if (entryList.get(p2).equals("TEMPORARY")) {
                    selectedEntry = 7

                } else if (entryList.get(p2).equals("OPENPOSITION")) {
                    selectedEntry = 1

                }
            }
        }
        mDialogView.edt_price.setText(price)
        mDialogView.edt_unit.setText(unit)


        mDialogView.btn_buy.setOnClickListener {

            iBuySell = 1
            mDialogView.btn_buy.setBackgroundColor(it.context.resources.getColor(textColorPositive))
            mDialogView.btn_sell.setBackgroundColor(it.context.resources.getColor(textColorGrayColor))
            mDialogView.btn_position.setBackgroundColor(it.context.resources.getColor(textColorPositive))
        }
        mDialogView.btn_sell.setOnClickListener {
            iBuySell = 2
            mDialogView.btn_buy.setBackgroundColor(it.context.resources.getColor(textColorGrayColor))
            mDialogView.btn_sell.setBackgroundColor(it.context.resources.getColor(textColorNegative))
            mDialogView.btn_position.setBackgroundColor(it.context.resources.getColor(textColorNegative))

        }
        mDialogView.img_close.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mDialogView.btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mDialogView.btn_position.setOnClickListener {

            if (mDialogView.edt_symbol.text.toString().isEmpty()) {
                mDialogView.edt_symbol.setError("Enter Symbol")
                return@setOnClickListener
            }
            if (!selectedMkgSeg.equals("1") && !selectedMkgSeg.equals("2") && !selectedMkgSeg.equals("")) {
                if (mDialogView.edt_strike.text.toString().isEmpty()) {
                    mDialogView.edt_strike.setError("Enter Strike")
                    return@setOnClickListener
                }
            }
            if (mDialogView.edt_Date.text.toString().isEmpty()) {
                mDialogView.edt_Date.setError("Select Date")
                return@setOnClickListener
            }
            if (mDialogView.edt_unit.text.toString().isEmpty()) {
                mDialogView.edt_unit.setError("Enter Unit")
                return@setOnClickListener
            }
            if (mDialogView.edt_price.text.toString().isEmpty()) {
                mDialogView.edt_price.setError("Enter Price")
                return@setOnClickListener
            }
            if (mDialogView.edt_strategyName.text.toString().isEmpty()) {
                mDialogView.edt_strategyName.setError("Enter Strategy Name")
                return@setOnClickListener
            }

            try {
                val date1: Date = SimpleDateFormat("dd MMM yyyy").parse(selectedExpiryDate)
                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                selectedExpiryDate = dateFormat.format(date1)

            } catch (e: Exception) {
            }
            var strike = 0.00;
            if (!mDialogView.edt_strike.text.toString().isEmpty()) {
                strike = mDialogView.edt_strike.text.toString().toDouble();
            }

            var addPositionRequest = AddPositionRequest(

                    request = AddPositionRequest.Request(
                            svcGroup = "portfolio",
                            svcName = "TradeNotificationView",
                            svcVersion = "1.0.0",
                            requestType = "U",
                            FormFactor = "M",
                            data = AddPositionRequest.Request.Data(
                                    bIsOptionStrategy = true,
                                    bTempTradeFlag = false,
                                    cAccountNumber = AccountDetails.getUsername(activity).capitalize(),
                                    cBrokerID = "1234567",
                                    cExpiryDt = selectedExpiryDate,// yyyy-mm-dd
                                    cInstrumentName = "",
                                    cMappedSymbol = "",
                                    cMaturityDate = "",
                                    cOptionType = selectedOptionType,//?????? ask to Shilav
                                    cSeries = "",
                                    cSymbolName = mDialogView.edt_symbol.text.toString(),
                                    cTradeDate = mDialogView.edt_Date.text.toString(),//yyyy-mm-dd as in web
                                    dFillPrice = mDialogView.edt_price.text.toString().toDouble(),
                                    dStrikePrice = strike,// ????? ask to shilav
                                    iBuySell = iBuySell,// buy=1 or sell=2
                                    iExchange = 1,
                                    iIdentifier = 0,
                                    iMarketSegment = 2,//??????????? ask to shilav
                                    iProClient = 1,
                                    iTradeType = selectedEntry,
                                    lFillNumber = 0,
                                    lFillQuantity = Math.abs(mDialogView.edt_unit.text.toString().toLong()),  // only positive,
                                    lOurOrderNo = 0,
                                    lToken = gToken,//lOurToken from builder,
                                    cStrategyName = mDialogView.edt_strategyName.text.toString(),
                                    cGreekClientID = AccountDetails.getUsername(activity).capitalize()
                            )))


            strategyBuildUpRepository.getInstance().StrategyTradeNotifyViewRequest(addPositionRequest,
                    fun(isSuccess: Boolean, response: SrategyBuildData?) {
                        if (isSuccess) {
                            var ErrorCode = response!!.response.data.ErrorCode

                        } else {
                            empty.value = true
                        }

                        dataLoading.value = false
                    })
            mAlertDialog.dismiss()
        }

        mDialogView.edt_Date.isFocusable = false
        val c = Calendar.getInstance()
        var mYear = c[Calendar.YEAR]
        var mMonth = c[Calendar.MONTH]
        var mDay = c[Calendar.DAY_OF_MONTH]

        mDialogView.edt_Date.setOnClickListener {

            val datePickerDialog = DatePickerDialog(activity,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        mDialogView.edt_Date.setText(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)

                    }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }

    }

    private fun getManualTradeEntryDataSymbol(view: View, activity: FragmentActivity?, iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String) {
        //validation
        HomeScreenRepository.getInstance().getManualTradeEntryDataSymbol(iRequestFor, iExchange, iSegment, cSymbol, lExpiry, cOptionType, dStrike,
                fun(isSuccess: Boolean, response: SymbolModel.SymbolInfo?) {
                    if (isSuccess) {


                        Log.e("Symbolresponse", response.toString())
                        symboleListLive.value = response?.data?.map { it.symbol }
                        var list = symboleListLive.value!!.toMutableList()
                        var strikeadapters = ArrayAdapter(activity!!, R.layout.row_spinner_mutualfund, list)
                        strikeadapters.setDropDownViewResource(R.layout.custom_spinner)
                        strikeadapters.setNotifyOnChange(true)
                        view.edt_symbol.threshold = 1
                        view.edt_symbol.setAdapter(strikeadapters)

                        if (iSegment.equals("3")) {
                            getManualTradeEntryDataExpiry(view, activity, "1", marektSelect, iSegment, view.edt_symbol.text.toString(), "", "", "")
                        }
                        getManualTradeEntryDataToken(view, activity, "3", marektSelect, iSegment, view.edt_symbol.text.toString(), "", "", "")


                    } else {

                    }
                })


    }

    private fun getManualTradeEntryDataExpiry(view: View, activity: FragmentActivity?, iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String) {
        //validation
        HomeScreenRepository.getInstance().getManualTradeEntryDataExpiry(iRequestFor, iExchange, iSegment, cSymbol, lExpiry, cOptionType, dStrike,
                fun(isSuccess: Boolean, response: SymbolModel.ExpiryDate?) {
                    if (isSuccess) {

                        expirylList.clear()
                        expiryTimeStamplList.clear()

                        response?.data?.forEach {
                            var date = DateTimeFormatter.getDateFromTimeStamp(it.expiry, "ddMMMyyyy", "bse")
                            expirylList.add(date)
                            expiryTimeStamplList.add(it.expiry)

                        }
                        Expadapters.setNotifyOnChange(true)
                        Expadapters.notifyDataSetChanged()
                        view.spinner_expDate.setSelection(0)
                        if (iSegment.equals("3")) {
                            getManualTradeEntryDataStrikePrice(view, activity, "2", marektSelect, iSegment, view.edt_symbol.text.toString(), selectedExpiryDate, "", "")
                        }
                    } else {

                    }
                })


    }

    private fun getManualTradeEntryDataStrikePrice(view: View, activity: FragmentActivity?, iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String) {
        //validation
        HomeScreenRepository.getInstance().getManualTradeEntryDataStrikeprice(iRequestFor, iExchange, iSegment, cSymbol, lExpiry, cOptionType, dStrike,
                fun(isSuccess: Boolean, response: SymbolModel.StrikePrice?) {
                    if (isSuccess) {


                        strikePriceListLive.value = response?.data?.map { it.strike }

                        var list = strikePriceListLive.value!!.toMutableList()
                        var strikeadapters = ArrayAdapter(activity!!, R.layout.row_spinner_mutualfund, list)
                        strikeadapters.setDropDownViewResource(R.layout.custom_spinner)
                        strikeadapters.setNotifyOnChange(true)
                        view.edt_strike.threshold = 1
                        view.edt_strike.setAdapter(strikeadapters)


                        empty.value = false
                    } else {
                        empty.value = true
                    }
                })


    }

    private fun getManualTradeEntryDataToken(view: View, activity: FragmentActivity?, iRequestFor: String, iExchange: String, iSegment: String, cSymbol: String, lExpiry: String, cOptionType: String, dStrike: String) {
        //validation

        HomeScreenRepository.getInstance().getManualTradeEntryDataToken(iRequestFor, iExchange, iSegment, cSymbol, lExpiry, cOptionType, dStrike,
                fun(isSuccess: Boolean, response: SymbolModel.ExpiryDate?) {
                    if (isSuccess) {

                        var gtokens = response?.data?.map { it.lourtoken }
                        gToken = gtokens?.get(0)?.toLong() ?: 1000

                        empty.value = false
                    } else {
                        empty.value = true
                    }
                })


    }

    private fun getStrike(mDialogView: View, activity: FragmentActivity?, strike: String) {

        //validation
        HomeScreenRepository.getInstance().getStrikePriceList(strike, fun(isSuccess: Boolean, response: SymbolModel.StrikePrice?) {
            if (isSuccess) {


                strikePriceListLive.value = response?.data?.map { it.strike }

                var list = strikePriceListLive.value!!.toMutableList()
                var strikeadapters = ArrayAdapter(activity!!, R.layout.row_spinner_mutualfund, list)
                strikeadapters.setDropDownViewResource(R.layout.custom_spinner)
                strikeadapters.setNotifyOnChange(true)
                mDialogView.edt_strike.threshold = 1
                mDialogView.edt_strike.setAdapter(strikeadapters)


                empty.value = false
            } else {
                empty.value = true
            }
        })
    }

    fun onClickSettingoption(view: View) {

        val dialog = Dialog(view.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.sf_build_filter_setting)
        var window = dialog.getWindow();
        val wlp = window!!.attributes
        wlp.gravity = Gravity.TOP or Gravity.RIGHT

        wlp.width = LinearLayout.LayoutParams.WRAP_CONTENT
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window!!.attributes = wlp
        val close = dialog.findViewById<ImageView>(R.id.popup_close)
        if (AccountDetails.getThemeFlag(view.context).equals("white")) {
            dialog.sf_menu_builder_filter.setBackgroundColor(view.context.resources.getColor(R.color.white))
            dialog.sf_new_portfolio.setBackgroundColor(view.context.resources.getColor(R.color.white))
            dialog.sf_delete_portfolio.setBackgroundColor(view.context.resources.getColor(R.color.white))
            dialog.sf_column_profile.setBackgroundColor(view.context.resources.getColor(R.color.white))
            dialog.sf_menu.setBackgroundColor(view.context.resources.getColor(R.color.white))
            dialog.setting_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))

            dialog.sf_menu_builder_filter.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            dialog.sf_new_portfolio.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            dialog.sf_delete_portfolio.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            dialog.sf_column_profile.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            dialog.sf_menu.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))




            dialog.setting_header.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
        }

        close.setOnClickListener {
            dialog.cancel();
        }
        val sf_menu_builder_filter = dialog.findViewById<TextView>(R.id.sf_menu_builder_filter)
        sf_menu_builder_filter.setOnClickListener {
            dialog.cancel();
            showBuilderFilertMenu(view)
//           strategyFinderView request is send to server and Builder Filter Dialog Box is displayed
        }
        val sf_new_portfolio = dialog.findViewById<TextView>(R.id.sf_new_portfolio)
        sf_new_portfolio.setOnClickListener {
            dialog.cancel();
            showPortfolioMenu(view)
//            New Portfolio Dialog Box is displayed
        }
        val sf_delete_portfolio = dialog.findViewById<TextView>(R.id.sf_delete_portfolio)
        sf_delete_portfolio.setOnClickListener {
            dialog.cancel();
            showDeletePortfolioMenu(view)
//            Delete Portfolio Dialog Box is displayed
        }
        val sf_column_profile = dialog.findViewById<TextView>(R.id.sf_column_profile)
        sf_column_profile.setOnClickListener {
            dialog.cancel();
            showProfileMenu(view)
        }
        val sf_menu = dialog.findViewById<TextView>(R.id.sf_menu)
        sf_menu.setOnClickListener {
            dialog.cancel()
            Toast.makeText(view.context, "Coming Soon", Toast.LENGTH_LONG).show()
//            showProfileMenu(view)
        }

        dialog.show()

    }

    private fun showBuilderFilertMenu(view: View) {

        selectedStrategy = ""
        selectedExpiryDate = ""
        selectedSymbol = ""
        showFiltter = true
        mDialogViewMenu = LayoutInflater.from(view.context).inflate(R.layout.sf_buildup_builder_layout, null)
        val mBuilder = AlertDialog.Builder(view.context).setView(mDialogViewMenu)
        val mAlertDialog = mBuilder.show()

        if (AccountDetails.getThemeFlag(view.context).equals("white")) {
            mDialogViewMenu!!.builder_header.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewMenu!!.builder_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))

            mDialogViewMenu!!.chk_strike.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))


            mDialogViewMenu!!.symbol.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.build_expiry.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.strategy_name.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.market_rate.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.build_days.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.build_stike.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.build_call.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.build_put.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.build_inrest.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.build_base.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.edt_marketRate.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.edt_days.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.edt_call.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.edt_put.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewMenu!!.edt_intrest.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))


            mDialogViewMenu!!.builder_header.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))

            mDialogViewMenu!!.spinner_symbol1.setBackgroundResource(R.drawable.gradient_spinner_black)
            mDialogViewMenu!!.spinner_expiry1.setBackgroundResource(R.drawable.gradient_spinner_black)
            mDialogViewMenu!!.spinner_strategy1.setBackgroundResource(R.drawable.gradient_spinner_black)
            mDialogViewMenu!!.spinner_base1.setBackgroundResource(R.drawable.gradient_spinner_black)
        }

        mDialogViewMenu!!.edt_marketRate.setText(mktRate)
        mDialogViewMenu!!.edt_days.setText(dayDiff)

        if (expiryArrayList.size == 0) {
            expiryArrayList.add("All")
        }


        val Adapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(view.context!!, AccountDetails.getRowSpinnerSimple(), expiryArrayList.toTypedArray()) {
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
        mDialogViewMenu!!.findViewById<Spinner>(R.id.spinner_expiry1).setAdapter(Adapter)
        mDialogViewMenu!!.spinner_expiry1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedExpiryDate = (p1 as GreekTextView).text.toString()
            }
        }

        if (strategyNameList.size == 0) {
            strategyNameList.add("All")
        }


        val strategybaseAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(view.context!!, AccountDetails.getRowSpinnerSimple(),
                strategyNameList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(view.context).equals("white", ignoreCase = true)) {
                    v.setTextColor(view.context.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(view.context.resources.getColor(R.color.white))
                }
                v.setPadding(5, 5, 5, 5)
                return v
            }

            /*      override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                      val v = super.getView(position, convertView, parent) as TextView
                      //  v.setTypeface(font);
                      v.setTextColor(Color.BLACK)
                      v.setPadding(15, 15, 15, 15)
                      return v
                  }*/
        }
        strategybaseAdapter.setDropDownViewResource(R.layout.custom_spinner)
        mDialogViewMenu!!.spinner_strategy1.setAdapter(strategybaseAdapter)
        mDialogViewMenu!!.spinner_strategy1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedStrategy = (p1 as GreekTextView).text.toString()

                StrategyBuilderEventForStorage(AccountDetails.getUsername(view.context), selectedSymbol, dStrikeDiff, dMidStrike)
            }
        }
        baseArrayList.clear()
        baseArrayList.add("E")
        baseArrayList.add("F")

        val baseAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(view.context!!, AccountDetails.getRowSpinnerSimple(),
                baseArrayList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(view.context).equals("white", ignoreCase = true)) {
                    v.setTextColor(view.context.resources.getColor(R.color.black))
                } else {
                    v.setTextColor(view.context.resources.getColor(R.color.white))
                }
                v.setPadding(5, 5, 5, 5)
                return v
            }

            /*      override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                      val v = super.getView(position, convertView, parent) as TextView
                      //  v.setTypeface(font);
                      v.setTextColor(Color.BLACK)
                      v.setPadding(15, 15, 15, 15)
                      return v
                  }*/
        }
        baseAdapter.setDropDownViewResource(R.layout.custom_spinner)
        mDialogViewMenu!!.spinner_base1.setAdapter(baseAdapter)
        mDialogViewMenu!!.spinner_base1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedBase = (p1 as GreekTextView).text.toString()
            }
        }

        val assetTypeAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(view.context!!, AccountDetails.getRowSpinnerSimple(),
                symbolArrayList.toTypedArray()) {
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

            /*      override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                      val v = super.getView(position, convertView, parent) as TextView
                      //  v.setTypeface(font);
                      v.setTextColor(Color.BLACK)
                      v.setPadding(15, 15, 15, 15)
                      return v
                  }*/
        }
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner)
        mDialogViewMenu!!.spinner_symbol1.setAdapter(assetTypeAdapter)

        mDialogViewMenu!!.spinner_symbol1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedSymbol = (p1 as GreekTextView).text.toString()
                StrategyBuilderEventForStorage(AccountDetails.getUsername(view.context), selectedSymbol, dStrikeDiff, dMidStrike)
            }
        }


        mDialogViewMenu!!.chk_strike.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                isCheckedStrikeIV = "0"
            } else {
                isCheckedStrikeIV = "1"
            }
        }

        mDialogViewMenu!!.btn_submit.setOnClickListener {

            selectedMarketRate = mDialogViewMenu!!.edt_marketRate.text.toString()
            selectedDays = mDialogViewMenu!!.edt_days.text.toString()
            selectedCall = mDialogViewMenu!!.edt_call.text.toString()
            selectedPut = mDialogViewMenu!!.edt_put.text.toString()
            selectedIntrstRate = mDialogViewMenu!!.edt_intrest.text.toString()

            if (symbolArrayList != null && symbolArrayList.size > 0) {
                selectedSymbolLive.value = symbolArrayList.get(0)
                selectedStrategyLive.value = "All"
                selectedExpiryDateLive.value = "All"
                refreshBottomTabs.value = true
            }

            isFilterApply = true
            StrategyBuilderView(AccountDetails.getUsername(view.context), selectedSymbol,
                    Util.getPrefs(it.context).getString("StrikeDiff", "0.0")!!,
                    Util.getPrefs(it.context).getString("LTP", "0.0")!!)
            mAlertDialog.cancel()

        }

        val close = mDialogViewMenu!!.findViewById<ImageView>(R.id.build_popup_close)
        close.setOnClickListener {
            mAlertDialog.cancel();
        }
    }

    fun showPortfolioMenu(view: View) {
        showFiltter = false
        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.sf_new_portfolio_layout, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(view.context)
                .setView(mDialogView)

        //show dialog
        val mAlertDialog = mBuilder.show()
        var strategyName = mDialogView.findViewById<GreekEditText>(R.id.edt_name)
        if (AccountDetails.getThemeFlag(view.context).equals("white")) {
            mDialogView!!.portfolio_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView!!.che.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView!!.expiry.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView!!.postion_name.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView!!.edt_name.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            mDialogView!!.portfolio_header.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))


        }
        strategyNameAdapter = StrategyNameAdapter(this)
        val differLinearLayoutManager = LinearLayoutManager(view.context)
        differLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mDialogView.StrategyRecyclerView.layoutManager = differLinearLayoutManager
        mDialogView.StrategyRecyclerView.adapter = strategyNameAdapter

        StrategyBuilderEvent(AccountDetails.getUsername(view.context))


        mDialogView.findViewById<GreekButton>(R.id.btn_save).setOnClickListener {

            if (strategyName.text!!.isEmpty()) {

                strategyName.error = "Enter Strategy Name"
                return@setOnClickListener
            }

//            StrategyBuilderEvent is send to server when user click on save button and new portfolio is added
            var createPortFolioRequest = CreatePortFolioRequest(CreatePortFolioRequest.Request(

                    FormFactor = "M",
                    requestType = "U",
                    svcVersion = "1.0.0",
                    svcGroup = "portfolio",
                    svcName = "StrategyBuilderEvent",
                    data = CreatePortFolioRequest.Request.Data(
                            cClientCode = AccountDetails.getUsername(it.context),
                            cGreekClientID = AccountDetails.getUsername(it.context),
                            iErrorCode = "1037",
                            cStrategyName = strategyName.text.toString()
                    )))

            strategyBuildUpRepository.getInstance().CreatePortfolioRequest(createPortFolioRequest,
                    fun(isSuccess: Boolean, response: SFBuildUpResponse?) {
                        if (isSuccess) {
                            var ErrorCode = response!!.response.data.ErrorCode

                            addedPortFolio.value = ErrorCode.equals("0")
                            mAlertDialog.cancel()

                            empty.value = false
                        } else {
                            empty.value = true
                        }
                        dataLoading.value = false

                    })
        }

        val close = mDialogView.findViewById<ImageView>(R.id.portfolio_popup_close)
        close.setOnClickListener {
            mAlertDialog.cancel();
        }
    }

    fun showDeletePortfolioMenu(view: View) {

        showFiltter = false

        var isAllChecked = false
        var request: DeletePortfolioRequest? = null
        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.sf_delete_portfolio_layout, null)
        val mBuilder = AlertDialog.Builder(view.context).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        if (AccountDetails.getThemeFlag(view.context).equals("white")) {
            mDialogView!!.delete_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogView!!.delete_header.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
            mDialogView!!.delete_che.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView!!.delete_expiry.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView!!.delete_startegy.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogView!!.chk_all.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogView!!.delete_edt_name.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))


        }
        mDialogView.findViewById<CheckBox>(R.id.chk_all).setOnCheckedChangeListener { compoundButton, b ->
            isAllChecked = b

            strategyArrayList.forEach {
                it.isChecked = b
            }
            strategyNameAdapter?.setAppList(strategyArrayList)

        }

        strategyNameAdapter = StrategyNameAdapter(this)
        val differLinearLayoutManager = LinearLayoutManager(view.context)
        differLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mDialogView.StrategyRecyclerView.layoutManager = differLinearLayoutManager
        mDialogView.StrategyRecyclerView.adapter = strategyNameAdapter

        StrategyBuilderEvent(AccountDetails.getUsername(view.context))

//      StrategyBuilderEvent request is send to server when user click on delete button.
        mDialogView.findViewById<GreekButton>(R.id.btn_delete).setOnClickListener {

            if (isAllChecked) {

                request = DeletePortfolioRequest(DeletePortfolioRequest.Request(
                        FormFactor = "M",
                        svcVersion = "1.0.0",
                        requestType = "U",
                        svcName = "StrategyBuilderEvent",
                        svcGroup = "portfolio",
                        data = DeletePortfolioRequest.Request.PortfolioData(
                                cClientCode = AccountDetails.getUsername(it.context),
                                iErrorCode = "1038",
                                cGreekClientID = AccountDetails.getUsername(it.context),
                                cStrategy = "All",
                                cExpiry = "All",
                                cExchange = "All",
                                cSymbol = "",
                                iDeleteAll = "1")))

                strategyBuildUpRepository.getInstance().DeletePortfolioRequest(request!!,
                        fun(isSuccess: Boolean, response: SFBuildUpResponse?) {
                            if (isSuccess) {
                                strategyArrayList.clear()
                                mAlertDialog.cancel()
                                var ErrorCode = response!!.response.data.ErrorCode
                                deletePortFolio.value = ErrorCode.equals("0")
                                empty.value = false
                            } else {
                                empty.value = true
                            }
                            dataLoading.value = false

                        })
            } else {

                var userName = AccountDetails.getUsername(it.context)
                strategyNameAdapter?.getChekedStrategyNameList()!!.forEach {

                    if (it.isChecked) {
                        request = DeletePortfolioRequest(DeletePortfolioRequest.Request(
                                FormFactor = "M",
                                svcVersion = "1.0.0",
                                requestType = "U",
                                svcName = "StrategyBuilderEvent",
                                svcGroup = "portfolio",
                                data = DeletePortfolioRequest.Request.PortfolioData(
                                        cClientCode = userName,
                                        iErrorCode = "1038",
                                        cGreekClientID = userName,
                                        cStrategy = it.cStrategy,
                                        cExpiry = it.cExpiry,
                                        cExchange = it.cExchange,
                                        cSymbol = it.cSymbol,
                                        iDeleteAll = "0")))

                        strategyBuildUpRepository.getInstance().DeletePortfolioRequest(request!!,
                                fun(isSuccess: Boolean, response: SFBuildUpResponse?) {
                                    if (isSuccess) {
                                        mAlertDialog.cancel()
                                        var ErrorCode = response!!.response.data.ErrorCode
                                        deletePortFolio.value = ErrorCode.equals("0")
                                        empty.value = false
                                    } else {
                                        empty.value = true
                                    }
                                    dataLoading.value = false

                                })
                    }
                }
            }


        }
        val close = mDialogView.findViewById<ImageView>(R.id.popup_close)
        close.setOnClickListener {
            mAlertDialog.cancel()
        }
    }

    fun showProfileMenu(view: View) {
        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.sf_profile_layout, null)
        val mBuilder = AlertDialog.Builder(view.context)
                .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        val close = mDialogView.findViewById<ImageView>(R.id.popup_close)
        close.setOnClickListener {
            mAlertDialog.cancel();
        }
    }


    override fun onCategoryClick(isChecked: Boolean, strategyName: StrategyNameResponse.Response.Data.StrategyName) {

        if (isChecked && !checkedIdList.contains(strategyName)) {
            checkedIdList.add(strategyName)
        }
    }
}
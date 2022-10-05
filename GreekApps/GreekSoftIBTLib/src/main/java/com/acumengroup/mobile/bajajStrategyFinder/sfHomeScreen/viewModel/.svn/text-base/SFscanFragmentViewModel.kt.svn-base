package com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.viewModel

import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.BaseViewModel
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.HomeScreenRepository
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.OptionFilterRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.ScanPostRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.StrategyName
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.SymbolModel
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.view.CategoryAdapter
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.SFBuildUpResponse
import com.acumengroup.ui.GreekDialog
import com.acumengroup.ui.textview.GreekTextView
import com.acumengroup.greekmain.util.Util
import com.acumengroup.greekmain.util.date.DateTimeFormatter
import com.acumengroup.greekmain.util.operation.StringStuff
import com.acumengroup.mobile.GreekBaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.strategy_filter_layout.view.*
import kotlinx.android.synthetic.main.strategy_option_filter_layout.view.*
import kotlinx.android.synthetic.main.strategy_option_filter_layout.view.btnClose
import kotlinx.android.synthetic.main.strategy_option_filter_layout.view.btnRset
import kotlinx.android.synthetic.main.strategy_option_filter_layout.view.img_Close
import java.lang.Exception
import kotlin.collections.ArrayList


open class SFscanFragmentViewModel : BaseViewModel(), CategoryAdapter.OnCategoryClickListener {

    val symbolListLive = MutableLiveData<List<String>>()
    val expiryTimeStampListLive = MutableLiveData<ArrayList<String>>()
    val strikePriceListLive = MutableLiveData<List<String>>()
    val submitScanRequest = MutableLiveData<ScanPostRequest>()
    val symbol = MutableLiveData<String>()
    val isSymbolEmpty = MutableLiveData<Boolean>()

    val lowerStrikePriceListLive = MutableLiveData<ArrayList<String>>()
    val uperStrikePriceListLive = MutableLiveData<ArrayList<String>>()
    val intervalListListLive = MutableLiveData<ArrayList<String>>()

    var lowerStrikePriceList = ArrayList<String>()
    var uperStrikePriceList = ArrayList<String>()
    var intervalListList = ArrayList<String>()

    val foldingCellSelectedLive = MutableLiveData<Boolean>()

    var selectedLowerStrikePosition = MutableLiveData<Int>()
    var selectedUpperStrikePosition = MutableLiveData<Int>()

    var change = MutableLiveData<String>()
    var closingprice = MutableLiveData<String>()
    var ltpValue = MutableLiveData<String>()
    var LtpValue = "00.00"
    var ltpValuetxt = MutableLiveData<String>()
    var pChange = MutableLiveData<String>()
    var expand: Boolean = false
    var prevDiff: Double = 0.0
    var diff: Double = 0.0
    lateinit var strikerangeType: String
    lateinit var strategyNameList: ArrayList<StrategyName>
    var checkedIdList = ArrayList<String>()
    var mCategoryList = ArrayList<StrategyName>()
    var strikeRangeTerm: String = "n"
    var edt_delta: String = "0"
    var edt_bidGap: String = "0"
    var edt_volume: String = "0"
    var edt_openInterest: String = "0"
    var edt_sec: String = "1"
    var edt_from: String = "5"
    var edt_to: String = "5"

    var chk_Mgain: Boolean = false
    var chk_alert: Boolean = false
    var chk_MLoss: Boolean = false
    var chk_bidAsk: Boolean = false
    var chk_ltp: Boolean = false
    var edt_sec_value: String = "0"
    var selectedLowerStrike: String = "0.00"
    var selectedUperStrike: String = "0.00"
    var selectedmarketItem: String = "1"
    var selectedVolatileItem: String = "0"
    var PreviousSelectedmarketItem: String = "1"
    var PreviousSelectedVolatileItem: String = "0"
    var selectedVolatileIndex: String = "1"
    var selectedExpiryDateItem: String = "00.00"
    var selectedStrikeDiffItem: String = "0"
    var selectedCombinationItem: String = "1"
    var selectedIntervalIndex: Int = 1

    lateinit var mDialogViewOptFilter: View

    var Term = "n"
    var From_value = 5
    var To_value = 5
    var selectedSymbol = ""

    var isOptionFiltterEnable = false

    companion object {
        var chk_call: Boolean = false
        var chk_put: Boolean = false
        var expirylList = ArrayList<String>()
        var expiryTimeStamplList = ArrayList<String>()
        var strikeDiffList = ArrayList<String>()
        var strategyList = ArrayList<ScanPostRequest.Request.Data.IStrategyType>()
    }


    fun fetchSymbolList() {
        dataLoading.value = true
        HomeScreenRepository.getInstance().getRepoList(
                fun(isSuccess: Boolean, response: SymbolModel.SymbolInfo?) {
                    if (isSuccess) {
                        symbolListLive.value = response?.data?.map { it.symbol }
                        empty.value = false
                    } else {
                        empty.value = true
                    }
                    dataLoading.value = false
                })
    }


    fun fetchExpiryList(symbol: String) {

        dataLoading.value = true
        selectedSymbol = symbol

        HomeScreenRepository.getInstance().getExpiryList(symbol, fun(isSuccess: Boolean, response: SymbolModel.ExpiryDate?) {
            if (isSuccess) {
                expirylList.clear()
                expiryTimeStamplList.clear()
                response?.data?.forEach {
                    var date = DateTimeFormatter.getDateFromTimeStamp(it.expiry, "dd MMM yyyy", "bse")
                    expirylList.add(date)
                    expiryTimeStamplList.add(it.expiry)
                }
                expirylList.add(0, "All Expiry Option")
                expiryTimeStampListLive.value = expirylList

                empty.value = false
            } else {
                empty.value = true
            }
            dataLoading.value = false

        })

        HomeScreenRepository.getInstance().getStrikePriceList(symbol, fun(isSuccess: Boolean, response: SymbolModel.StrikePrice?) {
            if (isSuccess) {
                strikePriceListLive.value = response?.data?.map { it.strikeprice }

                lowerStrikePriceList = (response?.data?.map { it.strikeprice as String } as ArrayList<String>?)!!
                uperStrikePriceList = (response?.data?.map { it.strikeprice as String } as ArrayList<String>?)!!
                intervalListList = (response?.data?.map { it.strikeprice } as ArrayList<String>?)!!

                empty.value = false
            } else {


                empty.value = true
            }
            dataLoading.value = false

        })

    }

    fun fetchLTPList(symbol: String, timestamp: String) {

        dataLoading.value = true
        HomeScreenRepository.getInstance().getLTPList(selectedSymbol, timestamp,
                fun(isSuccess: Boolean, response: SymbolModel.SymbolLTP?) {
                    if (isSuccess) {

                        change.value = String.format("%.2f", response?.data?.change?.toDouble())
                        ltpValue.value = String.format("%.2f", (response?.data?.ltp?.toDouble())?.div(100))
                        LtpValue = String.format("%.2f", (response?.data?.ltp?.toDouble())?.div(100))
                        closingprice.value = String.format("%.2f", response?.data?.closingprice?.toDouble()?.div(100))

                        pChange.value = String.format("%.2f", (ltpValue.value!!.toDouble() - closingprice.value!!.toDouble())) + "(" + change.value + "%)"
                        ltpValuetxt.value = StringStuff.commaDecorator(String.format("%.2f", (response?.data?.ltp?.toDouble())?.div(100)))

                        PrepareUpperAndLowerList()

                        empty.value = false
                    } else {
                        empty.value = true
                    }
                    dataLoading.value = false

                })

    }


    fun onSelectLowerStrike(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        if (lowerStrikePriceList.size > 0) {
            selectedLowerStrike = lowerStrikePriceListLive.value!!.get(pos)
        }
    }

    fun onSelectUperStrike(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        if (uperStrikePriceList.size > 0)
            selectedUperStrike = uperStrikePriceListLive.value!!.get(pos)
    }

    fun onSelectStrikeDifference(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        if (intervalListList.size > 0) {
            selectedStrikeDiffItem = intervalListListLive.value!!.get(pos)
            selectedIntervalIndex = pos + 1
        }
    }

    fun onSelectStrikeCombination(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        if (view != null)
            selectedCombinationItem = (view as GreekTextView).text.toString()

    }

    fun onSelectExpiryItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        var position = pos

        Util.getPrefs(view?.context).edit().putInt("SelectedPosition", position).commit()

        if (position > 0) {
            position = position - 1
            if (expiryTimeStamplList.size > 0) {
                selectedExpiryDateItem = expiryTimeStamplList.get(position)
                selectedExpiryDateItem = (selectedExpiryDateItem.toLong() - 315513000).toString()
            }
        } else {
//            selectedExpiryDateItem = "All"
            if (expiryTimeStamplList.size > 0) {
                selectedExpiryDateItem = expiryTimeStamplList.get(0)
                selectedExpiryDateItem = (selectedExpiryDateItem.toLong() - 315513000).toString()
            }
        }
        if (expiryTimeStamplList.size > 0) {
            fetchLTPList(selectedSymbol, selectedExpiryDateItem)
        }
        SaveOptionFilter("2", view!!)
    }

    fun onSelectSymbolItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        symbol.value = (view as GreekTextView).text.toString()
    }


    fun onSelectMarketItem(marketItem: String) {

        selectedmarketItem = marketItem
        checkedIdList.clear()
    }

    fun onSelectVolatilityItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        if (view != null) {
            selectedVolatileItem = (view as GreekTextView).text.toString()
        }
        selectedVolatileIndex = pos.toString() + 1
        checkedIdList.clear()
    }

    fun onClickExpandView(view: View) {

        if (!expand) {
            foldingCellSelectedLive.value = true
            expand = true
        } else {
            foldingCellSelectedLive.value = false
            expand = false
        }
    }


    fun onClickScanView(view: View) {

        if (symbol.value.toString().equals("null")) {
            isSymbolEmpty.value = true
            return
        }

        if (selectedmarketItem.equals("Select Outlook")) {
            GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK, "Please Select Market Outlook", "Ok", false) { action, data -> }
            return
        }

        if (selectedVolatileItem.equals("Select Volatility")) {
            GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK, "Please Select Volatility", "Ok", false) { action, data -> }
            return
        }

        if (selectedCombinationItem.equals("Select Combination")) {
            GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK, "Please Select Combination", "Ok", false) { action, data -> }
            return
        }

        if (selectedLowerStrike.toDouble() > selectedUperStrike.toDouble()) {
            GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK, "Lower Strike should be less than Upper strike", "Ok", false) { action, data -> }
            return
        }

        strategyList.clear()

        if (checkedIdList.size > 0) {
            checkedIdList.forEach {
                strategyList.add(getStrategyTypeLis(it.toInt()))
            }
        } else {
            prePareStrategyList(selectedmarketItem, selectedVolatileItem).forEach {
                if (it.StrategyCheck)
                    strategyList.add(getStrategyTypeLis(it.StrategyId.toInt()))
            }
        }

        if (view != null) {
            Util.getPrefs(view?.context).edit().putInt("selectedIntervalIndex", selectedIntervalIndex).commit()
        }

        var iCallPut = ""
        if (chk_call) {
            iCallPut = "1"
        }
        if (chk_put) {
            iCallPut = "2"
        }
        if (chk_put && chk_call) {
            iCallPut = "3"
        } else if (!chk_put && !chk_call) {
            iCallPut = "0"
        }

        var iHideMaxGainLoss = ""
        if (chk_Mgain) {
            iHideMaxGainLoss = "1"
        }
        if (chk_MLoss) {
            iHideMaxGainLoss = "2"
        }
        if (chk_Mgain && chk_MLoss) {
            iHideMaxGainLoss = "3"

        } else if (!chk_Mgain && !chk_MLoss) {
            iHideMaxGainLoss = "0"
        }

//        strategyfinder request is call when user click on Scan button and it will navigate to StrategyDataListFragment
        var scanRequest = ScanPostRequest(ScanPostRequest.Request(
                svcGroup = "portfolio",
                svcName = "strategyfinder",
                svcVersion = "1.0.0",
                requestType = "U",
                FormFactor = "M",
                data = ScanPostRequest.Request.Data(
                        bIsCovered = "true",
                        bIsLtpReferancePrice = if (chk_ltp) "true" else "false",
                        bStkEquityFlag = if (chk_bidAsk) "true" else "false",
                        cSymbol = symbol.value.toString(),
                        dIDXInterest = "0",
                        dITMDelta = edt_delta,
                        dLowerStrike = selectedLowerStrike,
                        dMaxAskBidDiff = edt_bidGap + "%",
                        dSTKInterest = "0",
                        dStrikeDiff = selectedStrikeDiffItem,
                        dUpperStrike = selectedUperStrike,
                        iAlertAddPosition = "",
                        iCallPut = "",
                        iCallPutSelection = iCallPut,
                        iExchange = "1",
                        iHideMaxGainLoss = if (iHideMaxGainLoss.isEmpty()) "0" else iHideMaxGainLoss,
                        iRefreshTimer = "",
                        iStrategyType = strategyList,
                        iStrikeCombinations = selectedCombinationItem,
                        iStrikeFrom = edt_from,
                        iStrikeInterval = selectedIntervalIndex.toString(),
                        iStrikeTo = edt_to,
                        iTokenCount = "",
                        iVolatality = if (selectedVolatileIndex.isEmpty()) "0" else selectedVolatileIndex,
                        iVolatalityTitle = selectedVolatileItem,
                        iMarketTitle = selectedmarketItem,
                        lExpDate = selectedExpiryDateItem,
                        lMinOpenInterest = if (edt_openInterest.isEmpty()) "0" else edt_openInterest,
                        lMinVolume = if (edt_volume.isEmpty()) "0" else edt_volume)))


        var sharePrefEditor = Util.getPrefs(view.context).edit()
        sharePrefEditor.putString("LTP", LtpValue)
        sharePrefEditor.putString("StrikeDiff", selectedStrikeDiffItem)
        sharePrefEditor.putString("Symbol", symbol.value.toString())
        sharePrefEditor.commit()

        submitScanRequest.value = scanRequest
    }

    fun onClickOptionFilterView(view: View) {

        SaveOptionFilter("2", view)

        mDialogViewOptFilter = LayoutInflater.from(view.context).inflate(R.layout.strategy_option_filter_layout, null)
        val mBuilder = AlertDialog.Builder(view.context).setView(mDialogViewOptFilter)
        val mAlertDialog = mBuilder.show()
        isOptionFiltterEnable = true

        if (AccountDetails.getThemeFlag(view.context).equals("white", ignoreCase = true)) {
            mDialogViewOptFilter.btnRset.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.single_line_border_bajaj))
            mDialogViewOptFilter.btnClose.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.single_line_border_bajaj))
            mDialogViewOptFilter.parent_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.edt_delta.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.edt_bidGap.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.edt_volume.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.edt_openInterest.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.edt_from.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.edt_to.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.edt_sec.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewOptFilter.midleLayout.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
            mDialogViewOptFilter.topLayout.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
            mDialogViewOptFilter.chk_call.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogViewOptFilter.chk_put.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogViewOptFilter.chk_Mgain.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogViewOptFilter.chk_MLoss.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogViewOptFilter.chk_alert.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogViewOptFilter.chk_bidAsk.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogViewOptFilter.chk_ltp.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))

            mDialogViewOptFilter.edt_delta.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewOptFilter.edt_bidGap.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewOptFilter.edt_volume.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewOptFilter.edt_openInterest.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewOptFilter.edt_from.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewOptFilter.edt_to.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewOptFilter.edt_sec.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

        }

        val termList = ArrayList<String>()
        termList.add("n")
        termList.add("%")

        var adapter = ArrayAdapter(view.context, AccountDetails.getRowSpinnerSimple(), termList)
        adapter.setDropDownViewResource(R.layout.custom_spinner)
        mDialogViewOptFilter.spinner_term.setAdapter(adapter)

        mDialogViewOptFilter.spinner_term.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                strikeRangeTerm = termList.get(p2)
            }
        }

        mDialogViewOptFilter.img_Close.setOnClickListener {

            mAlertDialog.dismiss()

        }

        mDialogViewOptFilter.btnRset.setOnClickListener {

//            SaveOptionFilter("2", view)
            mDialogViewOptFilter.spinner_term.setSelection(0)
            mDialogViewOptFilter.edt_from.setText("10")
            mDialogViewOptFilter.edt_to.setText("10")

            mDialogViewOptFilter.edt_delta.setText("0")
            mDialogViewOptFilter.edt_bidGap.setText("1.00")
            mDialogViewOptFilter.edt_volume.setText("0.00")
            mDialogViewOptFilter.edt_openInterest.setText("0")
            mDialogViewOptFilter.edt_sec.setText("1")

            mDialogViewOptFilter.chk_call.isChecked = true
            mDialogViewOptFilter.chk_put.isChecked = true
            mDialogViewOptFilter.chk_Mgain.isChecked = true
            mDialogViewOptFilter.chk_MLoss.isChecked = true
            mDialogViewOptFilter.chk_alert.isChecked = true
            mDialogViewOptFilter.chk_bidAsk.isChecked = false
            mDialogViewOptFilter.chk_ltp.isChecked = true

        }

        mDialogViewOptFilter.btnClose.setOnClickListener {

            mAlertDialog.dismiss()

        }
        mDialogViewOptFilter.saveOption.setOnClickListener {


            if (mDialogViewOptFilter.edt_delta.text.toString().isEmpty()) {
                mDialogViewOptFilter.edt_delta.setError("Enter value")
                return@setOnClickListener
            }

            if (mDialogViewOptFilter.edt_bidGap.text.toString().isEmpty()) {
                mDialogViewOptFilter.edt_bidGap.setError("Enter value")
                return@setOnClickListener
            }

            if (mDialogViewOptFilter.edt_volume.text.toString().isEmpty()) {
                mDialogViewOptFilter.edt_volume.setError("Enter value")
                return@setOnClickListener
            }
            if (mDialogViewOptFilter.edt_openInterest.text.toString().isEmpty()) {
                mDialogViewOptFilter.edt_openInterest.setError("Enter value")
                return@setOnClickListener
            }
            if (mDialogViewOptFilter.edt_sec.text.toString().isEmpty()) {
                mDialogViewOptFilter.edt_sec.setError("Enter value")
                return@setOnClickListener
            } else if (mDialogViewOptFilter.edt_sec.text.toString() == "0") {
                GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK,
                        "Please enter minimum 1 minute", "Ok", false) { action, data -> }
                return@setOnClickListener
            }

            if (mDialogViewOptFilter.edt_from.text.toString().isEmpty()) {
                mDialogViewOptFilter.edt_from.setError("Enter value")
                return@setOnClickListener
            }
            if (mDialogViewOptFilter.edt_to.text.toString().isEmpty()) {
                mDialogViewOptFilter.edt_to.setError("Enter value")
                return@setOnClickListener
            }

            Util.getPrefs(view.context).edit().putBoolean("chk_Mgain", chk_Mgain).commit()

            Util.getPrefs(view.context).edit().putBoolean("chk_MLoss", chk_MLoss).commit()

            edt_delta = mDialogViewOptFilter.edt_delta.text.toString()
            edt_bidGap = mDialogViewOptFilter.edt_bidGap.text.toString()
            edt_volume = mDialogViewOptFilter.edt_volume.text.toString()
            edt_openInterest = mDialogViewOptFilter.edt_openInterest.text.toString()
            edt_sec = mDialogViewOptFilter.edt_sec.text.toString()
            edt_from = mDialogViewOptFilter.edt_from.text.toString()
            edt_to = mDialogViewOptFilter.edt_to.text.toString()

            SaveOptionFilter("1", it)
            mAlertDialog.dismiss()
        }

        mDialogViewOptFilter.chk_call.setOnCheckedChangeListener { compoundButton, b ->
            chk_call = b
        }
        mDialogViewOptFilter.chk_put.setOnCheckedChangeListener { compoundButton, b ->
            chk_put = b
        }

        mDialogViewOptFilter.chk_Mgain.setOnCheckedChangeListener { compoundButton, b ->
            chk_Mgain = b
        }
        mDialogViewOptFilter.chk_MLoss.setOnCheckedChangeListener { compoundButton, b ->
            chk_MLoss = b
        }
        mDialogViewOptFilter.chk_alert.setOnCheckedChangeListener { compoundButton, b ->
            chk_alert = b
        }
        mDialogViewOptFilter.chk_bidAsk.setOnCheckedChangeListener { compoundButton, b ->
            chk_bidAsk = b
        }
        mDialogViewOptFilter.chk_ltp.setOnCheckedChangeListener { compoundButton, b ->
            chk_ltp = b
        }

    }


    fun SaveOptionFilter(getset: String, it: View) {

        var sharePrefEditor = Util.getPrefs(it.context).edit()
        sharePrefEditor.putString("RefreshTimer", edt_sec)
        sharePrefEditor.putBoolean("AlertPopup", chk_alert)
        sharePrefEditor.commit()

        var settingdata = "${edt_delta}|${edt_bidGap}|${edt_volume}|${edt_openInterest}" +
                "|${strikeRangeTerm}|${edt_from}|${edt_to}" +
                "|${chk_call}|${chk_put}|${chk_Mgain}|${chk_MLoss}|${edt_sec}|${chk_alert}|${chk_ltp}|${chk_bidAsk}|0|0"


//        GetSetDefaultSetting request is send to server to fetch all defaults symbols and parameters
        var reuest = OptionFilterRequest(OptionFilterRequest.Request(
                FormFactor = "M",
                svcGroup = "portfolio",
                svcName = "GetSetDefaultSetting",
                requestType = "U",
                svcVersion = "1.0.0",
                data = OptionFilterRequest.Request.Data(
                        gcid = AccountDetails.getUsername(it.context),
                        getset = getset,
                        gscid = AccountDetails.getUsername(it.context),
                        settingdata = settingdata,
                        settingkey = "OPTSTR_VALUE")))

        HomeScreenRepository.getInstance().postOptionFilter(reuest, fun(isSuccess: Boolean, response: SFBuildUpResponse?) {
            if (isSuccess) {

                var ErrorCode = response!!.response.data.ErrorCode
                var sdata = response!!.response.data.data

                if (sdata != null && !sdata.equals("0") && ErrorCode.equals("0")) {

                    val lstValues: ArrayList<String> = sdata.split("|") as ArrayList<String>

                    Term = lstValues.get(4)

                    if (lstValues.get(5).isNotEmpty()) {
                        From_value = lstValues.get(5).toInt()
                    }

                    if (lstValues.get(6).isNotEmpty()) {
                        To_value = lstValues.get(6).toInt()
                    }
                    val sharePrefEditors = Util.getPrefs(it.context).edit()
                    sharePrefEditors.putString("RefreshTimer", lstValues.get(11))
                    sharePrefEditors.putBoolean("AlertPopup", lstValues.get(12).equals("true"))
                    sharePrefEditors.apply()
                    if (lowerStrikePriceList.size > 0 && !LtpValue.equals("00.00")) {
                        PrepareUpperAndLowerList()
                    }

                    if (isOptionFiltterEnable) {

                        mDialogViewOptFilter.edt_delta.setText(lstValues.get(0))
                        mDialogViewOptFilter.edt_bidGap.setText(lstValues.get(1))
                        mDialogViewOptFilter.edt_volume.setText(lstValues.get(2))
                        edt_volume = lstValues.get(2)
                        mDialogViewOptFilter.edt_openInterest.setText(lstValues.get(3))

                        if (lstValues.get(4).equals("n")) {
                            mDialogViewOptFilter.spinner_term.setSelection(0)
                        } else {
                            mDialogViewOptFilter.spinner_term.setSelection(1)
                        }

                        mDialogViewOptFilter.edt_from.setText(lstValues.get(5))
                        mDialogViewOptFilter.edt_to.setText(lstValues.get(6))

                        mDialogViewOptFilter.chk_call.isChecked = lstValues.get(7).equals("true")
                        mDialogViewOptFilter.chk_put.isChecked = lstValues.get(8).equals("true")
                        mDialogViewOptFilter.chk_Mgain.isChecked = lstValues.get(9).equals("true")
                        chk_Mgain = lstValues.get(9).equals("true")
                        mDialogViewOptFilter.chk_MLoss.isChecked = lstValues.get(10).equals("true")
                        chk_MLoss = lstValues.get(10).equals("true")

                        mDialogViewOptFilter.edt_sec.setText(lstValues.get(11))

                        mDialogViewOptFilter.chk_alert.isChecked = lstValues.get(12).equals("true")
                        mDialogViewOptFilter.chk_ltp.isChecked = lstValues.get(13).equals("true")
                        chk_ltp = lstValues.get(13).equals("true")
                        mDialogViewOptFilter.chk_bidAsk.isChecked = lstValues.get(14).equals("true")


                    }

                } else {

                    Term = strikeRangeTerm
                    From_value = edt_from.toInt()
                    To_value = edt_to.toInt()

                    if (lowerStrikePriceList.size > 0 && !LtpValue.equals("00.00")) {
                        PrepareUpperAndLowerList()
                    }
                }


                empty.value = false
            } else {
                empty.value = true
            }
            dataLoading.value = false

        })

    }

    fun onClickStrategyFilterView(view: View) {

        if (selectedmarketItem.equals("Select  Outlook")) {
            GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK, "Please Select Market Outlook", "Ok", false) { action, data -> }
            return
        }

        if (selectedVolatileItem.equals("Select Volatility")) {
            GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK, "Please Select Volatility", "Ok", false) { action, data -> }
            return
        }

        var categoryAdapter: CategoryAdapter

        val mDialogViewStrategyFilter = LayoutInflater.from(view.context).inflate(R.layout.strategy_filter_layout, null)
        val mBuilder = AlertDialog.Builder(view.context).setView(mDialogViewStrategyFilter)
        val mAlertDialog = mBuilder.show()

        mDialogViewStrategyFilter.txt_mOutLook.setText(selectedmarketItem)
        mDialogViewStrategyFilter.txt_volatility.setText(selectedVolatileItem)

        if (AccountDetails.getThemeFlag(view.context).equals("white", ignoreCase = true)) {
            mDialogViewStrategyFilter.allCheckBx.setButtonDrawable(view.context.resources.getDrawable(R.drawable.white_checkbox_selector))
            mDialogViewStrategyFilter.btnRset.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.single_line_border_bajaj))
            mDialogViewStrategyFilter.btnClose.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.single_line_border_bajaj))
            mDialogViewStrategyFilter.mainLayout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            mDialogViewStrategyFilter.titleLAyout.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
            mDialogViewStrategyFilter.txt_mOutLook.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
            mDialogViewStrategyFilter.txt_volatility.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))

            mDialogViewStrategyFilter.title1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewStrategyFilter.title2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
           // mDialogViewStrategyFilter.txt_mOutLook.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
           // mDialogViewStrategyFilter.txt_volatility.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            mDialogViewStrategyFilter.title4.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))


        }


        categoryAdapter = CategoryAdapter(this, true)
        val categoryLinearLayoutManager = LinearLayoutManager(view.context)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mDialogViewStrategyFilter.recyclerView.layoutManager = categoryLinearLayoutManager

        if (mCategoryList.size > 0 && PreviousSelectedVolatileItem.equals(selectedVolatileItem) && PreviousSelectedmarketItem.equals(selectedmarketItem)) {
            categoryAdapter.setAppList(mCategoryList)
        } else {
            categoryAdapter.setAppList(prePareStrategyList(selectedmarketItem, selectedVolatileItem))
        }

        mDialogViewStrategyFilter.recyclerView.adapter = categoryAdapter

        mDialogViewStrategyFilter.allCheckBx.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->

            categoryAdapter.setAllCheckedList(b)

        })

        mDialogViewStrategyFilter.btnRset.setOnClickListener {

            categoryAdapter.setAppList(prePareStrategyList(selectedmarketItem, selectedVolatileItem))
            mDialogViewStrategyFilter.recyclerView.adapter = categoryAdapter
        }

        mDialogViewStrategyFilter.img_Close.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mDialogViewStrategyFilter.btnClose.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mDialogViewStrategyFilter.btnSave.setOnClickListener {
            checkedIdList = categoryAdapter.getCheckedIDList()

            if (checkedIdList.size <= 0) {
                GreekDialog.alertDialog(view.context, 0, GreekBaseActivity.GREEK,
                        "Please Select Strategy", "Ok", false) { action, data -> }
                return@setOnClickListener
            }
            PreviousSelectedVolatileItem = selectedVolatileItem
            PreviousSelectedmarketItem = selectedmarketItem
            mCategoryList = categoryAdapter.getCheckedCategoryList()

            val gson = Gson()
            val json = gson.toJson(mCategoryList)

            Util.getPrefs(view.context).edit().putString("SavedStrategy", json).commit()

            mAlertDialog.dismiss()
        }
    }

    fun onClickCloseView(view: View) {

        if (!expand) {
            foldingCellSelectedLive.value = true
            expand = true
        } else {
            foldingCellSelectedLive.value = true
            expand = false
        }
    }

    override fun onCategoryClick(position: Int) {

    }


    fun PrepareUpperAndLower() {

        var tempLowerStrikList: ArrayList<Double> = ArrayList()
        var tempUpperStrikeList: ArrayList<Double> = ArrayList()
        var tempStrike: ArrayList<String> = ArrayList()
        var selectionLowerStrike = 0000.00
        var selectionUpperStrike = 0000.00


        lowerStrikePriceList.forEach {

            tempLowerStrikList.add(it.toDouble())
            tempUpperStrikeList.add(it.toDouble())
        }

        tempLowerStrikList.sortBy { it }
        tempUpperStrikeList.sortBy { it }

        var ltp: Double = LtpValue.toDouble()

        if (Term.equals("n")) {
            var i = 0
            tempLowerStrikList.sortedByDescending { it }.forEach {


                if (it < ltp) {

                    if (i != From_value) {
                        i++
                        selectionLowerStrike = it
                    }
                }
            }

            var j = 0
            tempUpperStrikeList.forEach {

                if (it > ltp) {
                    if (j != To_value) {
                        j++
                        selectionUpperStrike = it
                    }
                }
            }
        } else {

            var tempStrikList: ArrayList<Double> = ArrayList()

            var lowerLTPValue = ltp - (ltp.times(From_value.toDouble().div(100)))

            tempStrikList.addAll(tempLowerStrikList)
            var StrikList = tempStrikList.filter { it < lowerLTPValue }

            if (StrikList.size > 0) {
                selectionLowerStrike = StrikList.get(StrikList.size - 1)
            }

            tempStrikList.clear()


            var upperLTPValue = ltp + (ltp.times(To_value.toDouble().div(100)))
            tempStrikList.addAll(tempUpperStrikeList)
            var strikeList = tempStrikList.filter { it > upperLTPValue }
            if (strikeList.size > 0) {
                selectionUpperStrike = strikeList.get(0)
            }

        }

        tempLowerStrikList.forEach {

            tempStrike.add(it.toString().replace(".0", ""))
        }

        lowerStrikePriceListLive.value = tempStrike

        tempStrike.clear()


        tempUpperStrikeList.forEach {

            tempStrike.add(it.toString().replace(".0", ""))
        }
        uperStrikePriceListLive.value = tempStrike

        selectedLowerStrike = selectionLowerStrike.toString()
        selectedUperStrike = selectionUpperStrike.toString()

        selectedUpperStrikePosition.value = tempUpperStrikeList.indexOf(selectionUpperStrike)
        selectedLowerStrikePosition.value = tempLowerStrikList.indexOf(selectionLowerStrike)


        for (k in 1 until uperStrikePriceList.size - 1) {

            diff = uperStrikePriceList.get(k + 1).toDouble() - uperStrikePriceList.get(k).toDouble()

            if (k == 1) {
                prevDiff = diff
            }
            if (diff < prevDiff) {
                prevDiff = diff
            }
        }

        strikeDiffList.clear()
        intervalListList.clear()
        for (interval in 1..5) {
            intervalListList.add((prevDiff * interval).toString().replace(".0", ""))
            strikeDiffList.add((prevDiff * interval).toString().replace(".0", ""))
        }
        intervalListListLive.value = intervalListList
        selectedStrikeDiffItem = intervalListList.get(0)

    }

    fun PrepareUpperAndLowerList() {

        try {


            var tempLowerStrikList: ArrayList<Double> = ArrayList()
            var tempUpperStrikeList: ArrayList<Double> = ArrayList()
            var tempStrike: ArrayList<String> = ArrayList()
            var selectionLowerStrike = 0000.00
            var selectionUpperStrike = 0000.00


            lowerStrikePriceList.forEach {

                tempLowerStrikList.add(it.toDouble())
                tempUpperStrikeList.add(it.toDouble())
            }

            tempLowerStrikList.sortBy { it }
            tempUpperStrikeList.sortBy { it }

            var ltp: Double = LtpValue.toDouble()


            var nearLowerStrikList: ArrayList<Double> = ArrayList()
            var nearUpperStrikeList: ArrayList<Double> = ArrayList()

            if (Term.equals("n")) {
                var i = 0

                tempLowerStrikList.sortedByDescending { it }.forEach {


                    if (it < ltp) {
                        nearLowerStrikList.add(it)
                    }
                }

                var j = 0
                tempUpperStrikeList.forEach {

                    if (it > ltp) {
                        nearUpperStrikeList.add(it)

                    }
                }

                var lowerStrike = if (nearLowerStrikList.size > 0) {
                    nearLowerStrikList.get(0)
                } else {
                    0.00
                }

                var uperStrike = if (nearUpperStrikeList.size > 0) {
                    nearUpperStrikeList.get(0)
                } else {
                    0.00
                }

                var strikeDiffer = Math.abs(uperStrike - lowerStrike)

                var mainStrike = 0.0

                if (Math.abs(uperStrike - ltp) < Math.abs(lowerStrike - ltp)) {
                    mainStrike = uperStrike
                } else {
                    mainStrike = lowerStrike
                }

                selectionLowerStrike = mainStrike - (strikeDiffer * edt_from.toInt())
                if (!tempLowerStrikList.contains(selectionLowerStrike) && nearLowerStrikList.size > 0) {
                    selectionLowerStrike = nearLowerStrikList.get(nearLowerStrikList.size - 2)
                }

                selectionUpperStrike = mainStrike + (strikeDiffer * edt_to.toInt())
                if (!tempUpperStrikeList.contains(selectionUpperStrike) && nearUpperStrikeList.size > 0) {
                    selectionUpperStrike = nearUpperStrikeList.last()
                }


            } else {


                tempLowerStrikList.sortedByDescending { it }.forEach {


                    if (it < ltp) {
                        nearLowerStrikList.add(it)
                    }
                }

                var j = 0
                tempUpperStrikeList.forEach {

                    if (it > ltp) {
                        nearUpperStrikeList.add(it)

                    }
                }

                var lowerStrike = if (nearLowerStrikList.size > 0) {
                    nearLowerStrikList.get(0)
                } else {
                    0.00
                }

                var uperStrike = if (nearUpperStrikeList.size > 0) {
                    nearUpperStrikeList.get(0)
                } else {
                    0.00
                }

                var mainStrike = 0.0

                if (Math.abs(uperStrike - ltp) < Math.abs(lowerStrike - ltp)) {
                    mainStrike = uperStrike
                } else {
                    mainStrike = lowerStrike
                }


                var tempStrikList: ArrayList<Double> = ArrayList()

                var lowerLTPValue = mainStrike - (mainStrike.times(From_value.toDouble().div(100)))

                tempStrikList.addAll(tempLowerStrikList)

                var lowerMIN = tempStrikList.filter { it < lowerLTPValue }.last()
                var lowerMAX = tempStrikList.filter { it > lowerLTPValue }.get(0)

                if (Math.abs(lowerMAX - lowerLTPValue) < Math.abs(lowerMIN - lowerLTPValue)) {

                    selectionLowerStrike = lowerMAX
                } else {
                    selectionLowerStrike = lowerMIN
                }

                if (!tempStrikList.contains(selectionLowerStrike) && nearLowerStrikList.size > 0) {
                    selectionLowerStrike = nearLowerStrikList.get(nearLowerStrikList.size - 2)
                }

                tempStrikList.clear()

                var upperLTPValue = mainStrike + (mainStrike.times(To_value.toDouble().div(100)))

                tempStrikList.addAll(tempUpperStrikeList)

                var upperMAX = tempStrikList.filter { it > upperLTPValue }.get(0)
                var upperMIN = tempStrikList.filter { it < upperLTPValue }.last()

                if (Math.abs(upperMAX - upperLTPValue) < Math.abs(upperMIN - upperLTPValue)) {

                    selectionUpperStrike = upperMAX
                } else {
                    selectionUpperStrike = upperMIN
                }

                if (!tempStrikList.contains(selectionUpperStrike) && nearUpperStrikeList.size > 0) {
                    selectionUpperStrike = nearUpperStrikeList.last()
                }
            }

            tempLowerStrikList.forEach {

                tempStrike.add(it.toString().replace(".0", ""))
            }

            lowerStrikePriceListLive.value = tempStrike

            tempStrike.clear()


            tempUpperStrikeList.forEach {

                tempStrike.add(it.toString().replace(".0", ""))
            }
            uperStrikePriceListLive.value = tempStrike

            selectedLowerStrike = selectionLowerStrike.toString()
            selectedUperStrike = selectionUpperStrike.toString()

            selectedUpperStrikePosition.value = tempUpperStrikeList.indexOf(selectionUpperStrike)
            selectedLowerStrikePosition.value = tempLowerStrikList.indexOf(selectionLowerStrike)


            for (k in 1 until uperStrikePriceList.size - 1) {

                diff = uperStrikePriceList.get(k + 1).toDouble() - uperStrikePriceList.get(k).toDouble()

                if (k == 1) {
                    prevDiff = diff
                }
                if (diff < prevDiff) {
                    prevDiff = diff
                }
            }

            strikeDiffList.clear()
            intervalListList.clear()
            for (interval in 1..5) {
                intervalListList.add((prevDiff * interval).toString().replace(".0", ""))
                strikeDiffList.add((prevDiff * interval).toString().replace(".0", ""))
            }
            intervalListListLive.value = intervalListList
            selectedStrikeDiffItem = intervalListList.get(0)


        } catch (x: Exception) {
        }

    }

    fun prePareStrategyList(selectedmarketItem: String, selectedVolatileItem: String): ArrayList<StrategyName> {

        var mOutLook: String = selectedmarketItem
        var volatility: String = selectedVolatileItem

        var strategyArrayList = arrayOfNulls<String>(45)
        strategyArrayList.set(10, "BEAR CALL SPREAD")
        strategyArrayList.set(11, "BEAR PUT SPREAD")
        strategyArrayList.set(1, "LONG PUT")
        strategyArrayList.set(3, "LONG RATIO PUT SPREAD")
        strategyArrayList.set(15, "COVERED PUT")
        strategyArrayList.set(16, "NAKED CALL (UNCOVERED CALL SHORT CALL)")
        strategyArrayList.set(5, "SHORT RATIO PUT SPREAD")
        strategyArrayList.set(12, "BULL CALL SPREAD")
        strategyArrayList.set(13, "BULL PUT SPREAD")
        strategyArrayList.set(14, "COVERED CALL")
        strategyArrayList.set(40, "COVERED RATIO SPREAD")
        strategyArrayList.set(0, "LONG CALL")
        strategyArrayList.set(2, "LONG RATIO CALL SPREAD")
        strategyArrayList.set(17, "NAKED PUT (UNCOVERED PUT SHORT PUT)")
        strategyArrayList.set(4, "SHORT RATIO CALL SPREAD")
        strategyArrayList.set(6, "LONG CALL CALENDAR SPREAD (CALL HORIZONTAL)")
        strategyArrayList.set(7, "LONG PUT CALENDAR SPREAD (PUT HORIZONTAL)")
        strategyArrayList.set(18, "LONG CALL CONDOR")
        strategyArrayList.set(19, "LONG PUT CONDOR")
        strategyArrayList.set(31, "SHORT CONDOR (IRON CONDOR)")
        strategyArrayList.set(21, "LONG PUT BUTTERFLY")
        strategyArrayList.set(25, "SHORT IRON BUTTERFLY")
        strategyArrayList.set(20, "LONG CALL BUTTERFLY")
        strategyArrayList.set(29, "SHORT STRANGLE (SHORT COMBINATION)")
        strategyArrayList.set(42, "SHORT STRADDLE")
        strategyArrayList.set(30, "LONG CONDOR (IRON CONDOR)")
        strategyArrayList.set(24, "LONG IRON BUTTERFLY")
        strategyArrayList.set(20, "LONG CALL BUTTERFLY")
        strategyArrayList.set(36, "LONG STRADDLE")
        strategyArrayList.set(28, "LONG STRANGLE (LONG COMBINATION)")
        strategyArrayList.set(8, "SHORT CALL CALENDAR SPREAD (SHORT CALL TIME SPREAD)")
        strategyArrayList.set(22, "SHORT CALL BUTTERFLY")
        strategyArrayList.set(23, "SHORT PUT BUTTERFLY")
        strategyArrayList.set(9, "SHORT PUT CALENDAR SPREAD (SHORT PUT TIME SPREAD)")
        strategyArrayList.set(24, "LONG IRON BUTTERFLY")
        strategyArrayList.set(24, "LONG IRON BUTTERFLY")


        strategyNameList = ArrayList<StrategyName>()

        if (mOutLook.equals("BULLISH") && volatility.equals("ALL VOLATILITY")) {

//            strategyNameList.add(StrategyName("-1", false, "All"))
            strategyNameList.add(StrategyName("12", true, strategyArrayList.get(12).toString()))
            strategyNameList.add(StrategyName("13", true, strategyArrayList.get(13).toString()))
            strategyNameList.add(StrategyName("14", true, strategyArrayList.get(14).toString()))
            strategyNameList.add(StrategyName("40", true, strategyArrayList.get(40).toString()))
            strategyNameList.add(StrategyName("0", false, strategyArrayList.get(0).toString()))
            strategyNameList.add(StrategyName("2", true, strategyArrayList.get(2).toString()))
            strategyNameList.add(StrategyName("17", false, strategyArrayList.get(17).toString()))
            strategyNameList.add(StrategyName("4", false, strategyArrayList.get(4).toString()))


        } else if (mOutLook.equals("BULLISH") && volatility.equals("INCREASE")) {

//            strategyNameList.add(StrategyName("-1", false, "All"))
            strategyNameList.add(StrategyName("12", true, strategyArrayList.get(12).toString()))
            strategyNameList.add(StrategyName("13", true, strategyArrayList.get(13).toString()))
            strategyNameList.add(StrategyName("14", true, strategyArrayList.get(14).toString()))
            strategyNameList.add(StrategyName("40", true, strategyArrayList.get(40).toString()))
            strategyNameList.add(StrategyName("0", false, strategyArrayList.get(0).toString()))
            strategyNameList.add(StrategyName("2", true, strategyArrayList.get(2).toString()))
            strategyNameList.add(StrategyName("17", false, strategyArrayList.get(17).toString()))
            strategyNameList.add(StrategyName("4", false, strategyArrayList.get(4).toString()))


        } else if (mOutLook.equals("BULLISH") && volatility.equals("DECREASE")) {

//            strategyNameList.add(StrategyName("-1", false, "All"))
            strategyNameList.add(StrategyName("13", true, strategyArrayList.get(13).toString()))
            strategyNameList.add(StrategyName("14", true, strategyArrayList.get(14).toString()))
            strategyNameList.add(StrategyName("40", true, strategyArrayList.get(40).toString()))
            strategyNameList.add(StrategyName("17", false, strategyArrayList.get(17).toString()))
            strategyNameList.add(StrategyName("4", false, strategyArrayList.get(4).toString()))

        } else if (mOutLook.equals("BULLISH") && volatility.equals("NOCHANGE")) {

            strategyNameList.add(StrategyName("-1", true, "All"))

        } else if (mOutLook.equals("BEARISH") && volatility.equals("ALL VOLATILITY")) {

//                strategyNameList.add(StrategyName("-1", false, "All"))
            strategyNameList.add(StrategyName("10", true, strategyArrayList.get(10).toString()))
            strategyNameList.add(StrategyName("11", true, strategyArrayList.get(11).toString()))
            strategyNameList.add(StrategyName("1", false, strategyArrayList.get(1).toString()))
            strategyNameList.add(StrategyName("3", true, strategyArrayList.get(3).toString()))
            strategyNameList.add(StrategyName("15", false, strategyArrayList.get(15).toString()))
            strategyNameList.add(StrategyName("16", false, strategyArrayList.get(16).toString()))
            strategyNameList.add(StrategyName("5", false, strategyArrayList.get(5).toString()))

        } else if (mOutLook.equals("BEARISH") && volatility.equals("INCREASE")) {

//                strategyNameList.add(StrategyName("-1", false, "All"))
            strategyNameList.add(StrategyName("10", true, strategyArrayList.get(10).toString()))
            strategyNameList.add(StrategyName("11", true, strategyArrayList.get(11).toString()))
            strategyNameList.add(StrategyName("1", false, strategyArrayList.get(1).toString()))
            strategyNameList.add(StrategyName("3", true, strategyArrayList.get(3).toString()))

        } else if (mOutLook.equals("BEARISH") && volatility.equals("DECREASE")) {

//                strategyNameList.add(StrategyName("-1", false, "All"))
            strategyNameList.add(StrategyName("10", true, strategyArrayList.get(10).toString()))
            strategyNameList.add(StrategyName("16", false, strategyArrayList.get(16).toString()))
            strategyNameList.add(StrategyName("5", false, strategyArrayList.get(5).toString()))


        } else if (mOutLook.equals("BEARISH") && volatility.equals("NOCHANGE")) {

//                strategyNameList.add(StrategyName("-1", false, "All"))

        } else


            if (mOutLook.equals("NEUTRAL") && volatility.equals("ALL VOLATILITY")) {

//                    strategyNameList.add(StrategyName("-1", false, "All"))
                strategyNameList.add(StrategyName("6", true, strategyArrayList.get(6).toString()))
                strategyNameList.add(StrategyName("7", true, strategyArrayList.get(7).toString()))
                strategyNameList.add(StrategyName("18", true, strategyArrayList.get(18).toString()))
                strategyNameList.add(StrategyName("19", true, strategyArrayList.get(19).toString()))
                strategyNameList.add(StrategyName("31", true, strategyArrayList.get(31).toString()))
                strategyNameList.add(StrategyName("21", true, strategyArrayList.get(21).toString()))
                strategyNameList.add(StrategyName("20", true, strategyArrayList.get(20).toString()))
                strategyNameList.add(StrategyName("25", true, strategyArrayList.get(25).toString()))
                strategyNameList.add(StrategyName("29", false, strategyArrayList.get(29).toString()))
                strategyNameList.add(StrategyName("42", false, strategyArrayList.get(42).toString()))


            } else if (mOutLook.equals("NEUTRAL") && volatility.equals("INCREASE")) {

//                    strategyNameList.add(StrategyName("-1", true, "All"))
                strategyNameList.add(StrategyName("6", true, strategyArrayList.get(6).toString()))
                strategyNameList.add(StrategyName("7", true, strategyArrayList.get(7).toString()))


            } else if (mOutLook.equals("NEUTRAL") && volatility.equals("DECREASE")) {

//                    strategyNameList.add(StrategyName("-1", true, "All"))
                strategyNameList.add(StrategyName("18", true, strategyArrayList.get(18).toString()))
                strategyNameList.add(StrategyName("19", true, strategyArrayList.get(19).toString()))
                strategyNameList.add(StrategyName("31", true, strategyArrayList.get(31).toString()))
                strategyNameList.add(StrategyName("29", true, strategyArrayList.get(29).toString()))


            } else if (mOutLook.equals("NEUTRAL") && volatility.equals("NOCHANGE")) {

//                    strategyNameList.add(StrategyName("-1", true, "All"))
                strategyNameList.add(StrategyName("21", true, strategyArrayList.get(21).toString()))
                strategyNameList.add(StrategyName("25", true, strategyArrayList.get(25).toString()))

            } else


                if (mOutLook.equals("VOLATILE") && volatility.equals("ALL VOLATILITY")) {

//                        strategyNameList.add(StrategyName("-1", false, "All"))
                    strategyNameList.add(StrategyName("30", true, strategyArrayList.get(30).toString()))
                    strategyNameList.add(StrategyName("24", true, strategyArrayList.get(24).toString()))
                    strategyNameList.add(StrategyName("36", true, strategyArrayList.get(36).toString()))
                    strategyNameList.add(StrategyName("28", false, strategyArrayList.get(28).toString()))
                    strategyNameList.add(StrategyName("8", true, strategyArrayList.get(8).toString()))
                    strategyNameList.add(StrategyName("22", true, strategyArrayList.get(22).toString()))
                    strategyNameList.add(StrategyName("23", true, strategyArrayList.get(23).toString()))
                    strategyNameList.add(StrategyName("9", true, strategyArrayList.get(9).toString()))

                } else if (mOutLook.equals("VOLATILE") && volatility.equals("INCREASE")) {

//                        strategyNameList.add(StrategyName("-1", false, "All"))
                    strategyNameList.add(StrategyName("30", true, strategyArrayList.get(30).toString()))
                    strategyNameList.add(StrategyName("24", true, strategyArrayList.get(24).toString()))
                    strategyNameList.add(StrategyName("36", true, strategyArrayList.get(36).toString()))
                    strategyNameList.add(StrategyName("28", false, strategyArrayList.get(28).toString()))

                } else if (mOutLook.equals("VOLATILE") && volatility.equals("DECREASE")) {

//                        strategyNameList.add(StrategyName("-1", true, "All"))
                    strategyNameList.add(StrategyName("8", true, strategyArrayList.get(8).toString()))

                } else if (mOutLook.equals("VOLATILE") && volatility.equals("NOCHANGE")) {

//                        strategyNameList.add(StrategyName("-1", true, "All"))
                    strategyNameList.add(StrategyName("22", true, strategyArrayList.get(22).toString()))
                    strategyNameList.add(StrategyName("23", true, strategyArrayList.get(23).toString()))
                    strategyNameList.add(StrategyName("9", true, strategyArrayList.get(9).toString()))
                }


        return strategyNameList
    }

    fun getStrategyTypeLis(StrategyCode: Int): ScanPostRequest.Request.Data.IStrategyType {

        var iStrategyTypeListItem: ScanPostRequest.Request.Data.IStrategyType? = null

        when (StrategyCode) {
            0 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "0", iTokenCount = "0", iCallPut = "1", bIsCovered = "false")

            1 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "1", iTokenCount = "0", iCallPut = "2", bIsCovered = "false")

            2 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "2", iTokenCount = "2", iCallPut = "1", bIsCovered = "false")

            3 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "3", iTokenCount = "2", iCallPut = "2", bIsCovered = "false")

            4 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "4", iTokenCount = "2", iCallPut = "1", bIsCovered = "false")

            5 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "5", iTokenCount = "2", iCallPut = "2", bIsCovered = "false")

            6 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "6", iTokenCount = "0", iCallPut = "1", bIsCovered = "false")

            7 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "7", iTokenCount = "0", iCallPut = "2", bIsCovered = "false")

            8 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "8", iTokenCount = "0", iCallPut = "1", bIsCovered = "false")

            9 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "9", iTokenCount = "0", iCallPut = "2", bIsCovered = "false")

            10 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "10", iTokenCount = "2", iCallPut = "1", bIsCovered = "false")

            11 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "11", iTokenCount = "2", iCallPut = "2", bIsCovered = "false")

            12 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "12", iTokenCount = "2", iCallPut = "1", bIsCovered = "false")

            13 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "13", iTokenCount = "2", iCallPut = "2", bIsCovered = "false")

            14 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "14", iTokenCount = "0", iCallPut = "1", bIsCovered = "true")
            15 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "15", iTokenCount = "0", iCallPut = "2", bIsCovered = "true")

            16 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "16", iTokenCount = "0", iCallPut = "1", bIsCovered = "false")

            17 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "17", iTokenCount = "0", iCallPut = "2", bIsCovered = "false")

            18 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "18", iTokenCount = "4", iCallPut = "1", bIsCovered = "false")

            19 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "19", iTokenCount = "4", iCallPut = "2", bIsCovered = "false")

            20 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "20", iTokenCount = "3", iCallPut = "1", bIsCovered = "false")

            21 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "21", iTokenCount = "3", iCallPut = "2", bIsCovered = "false")

            22 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "22", iTokenCount = "3", iCallPut = "1", bIsCovered = "false")

            23 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "23", iTokenCount = "3", iCallPut = "2", bIsCovered = "false")

            24 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "24", iTokenCount = "4", iCallPut = "3", bIsCovered = "false")

            25 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "25", iTokenCount = "4", iCallPut = "3", bIsCovered = "false")

            26 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "26", iTokenCount = "1", iCallPut = "1", bIsCovered = "true")

            27 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "27", iTokenCount = "1", iCallPut = "2", bIsCovered = "true")

            28 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "28", iTokenCount = "2", iCallPut = "3", bIsCovered = "false")

            29 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "29", iTokenCount = "2", iCallPut = "3", bIsCovered = "false")

            30 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "30", iTokenCount = "4", iCallPut = "3", bIsCovered = "false")

            31 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "31", iTokenCount = "4", iCallPut = "3", bIsCovered = "false")

            32 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "32", iTokenCount = "1", iCallPut = "1", bIsCovered = "true")

            33 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "33", iTokenCount = "1", iCallPut = "1", bIsCovered = "true")

            34 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "34", iTokenCount = "2", iCallPut = "3", bIsCovered = "false")

            35 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "35", iTokenCount = "2", iCallPut = "3", bIsCovered = "false")

            36 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "36", iTokenCount = "2", iCallPut = "3", bIsCovered = "false")

            37 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "37", iTokenCount = "0", iCallPut = "", bIsCovered = "false")

            38 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "38", iTokenCount = "1", iCallPut = "2", bIsCovered = "true")

            39 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "39", iTokenCount = "1", iCallPut = "1", bIsCovered = "true")

            40 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "40", iTokenCount = "2", iCallPut = "1", bIsCovered = "false")

            41 -> iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = "41", iTokenCount = "2", iCallPut = "3", bIsCovered = "false")
            else -> {
                //We can Consider it for -1 StrategyID
                iStrategyTypeListItem = ScanPostRequest.Request.Data.IStrategyType(iStrategyType = " ", iTokenCount = " ", iCallPut = " ", bIsCovered = " ")
            }
        }
        return iStrategyTypeListItem!!
    }


}

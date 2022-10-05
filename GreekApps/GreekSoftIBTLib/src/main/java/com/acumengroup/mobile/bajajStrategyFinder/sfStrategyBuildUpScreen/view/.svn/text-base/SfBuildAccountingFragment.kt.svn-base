package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view


import android.content.ContentValues
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.greekmain.core.network.WSHandler
import com.acumengroup.mobile.GreekBaseFragment
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.api.ApiClient
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.SFBuildUpResponseAccounting
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.StrategyBuilderViewRequest
import com.acumengroup.ui.textview.GreekTextView
import com.acumengroup.greekmain.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sf_build_accounting_fragment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SfBuildAccountingFragment : GreekBaseFragment() {

    private var _hasLoadedOnce = true
    lateinit var SFBuildAccountingData: SFBuildUpResponseAccounting
    lateinit var Accounting_txt_one: GreekTextView
    lateinit var Accounting_txt_two: GreekTextView
    lateinit var Accounting_txt_three: GreekTextView
    lateinit var Accounting_txt_four: GreekTextView
    lateinit var Accounting_txt_five: GreekTextView
    lateinit var Accounting_txt_six: GreekTextView
    lateinit var Accounting_txt_seven: GreekTextView
    lateinit var Accounting_value_one: GreekTextView
    lateinit var Accounting_value_two: GreekTextView
    lateinit var Accounting_value_three: GreekTextView
    lateinit var Accounting_value_four: GreekTextView
    lateinit var Accounting_value_five: GreekTextView
    lateinit var Accounting_value_six: GreekTextView
    lateinit var Accounting_value_seven: GreekTextView
    lateinit var balance_total_value: GreekTextView
    lateinit var linear_parent: LinearLayout
    lateinit var linear3: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sf_build_accounting_fragment, container, false)
        linear3 = view.findViewById(R.id.linear3)
        linear_parent = view.findViewById(R.id.linear_parent)
        balance_total_value = view.findViewById(R.id.balance_total_value)
        Accounting_value_five = view.findViewById(R.id.Accounting_value_five)
        Accounting_value_seven = view.findViewById(R.id.Accounting_value_seven)
        Accounting_value_four = view.findViewById(R.id.Accounting_value_four)
        Accounting_value_three = view.findViewById(R.id.Accounting_value_three)
        Accounting_value_six = view.findViewById(R.id.Accounting_value_six)
        Accounting_txt_seven = view.findViewById(R.id.Accounting_txt_seven)
        Accounting_value_one = view.findViewById(R.id.Accounting_value_one)
        Accounting_value_two = view.findViewById(R.id.Accounting_value_two)
        Accounting_txt_six = view.findViewById(R.id.Accounting_txt_six)
        Accounting_txt_five = view.findViewById(R.id.Accounting_txt_five)
        Accounting_txt_four = view.findViewById(R.id.Accounting_txt_four)
        Accounting_txt_three = view.findViewById(R.id.Accounting_txt_three)
        Accounting_txt_two = view.findViewById(R.id.Accounting_txt_two)
        Accounting_txt_one = view.findViewById(R.id.Accounting_txt_one)
        linear3 = view.findViewById(R.id.linear3)

        if (_hasLoadedOnce) {
            //   Toast.makeText(activity,"sf build ACCOUNT fragment",Toast.LENGTH_SHORT).show();
            StrategyBuilderAccountingView(view)
            setTheme()
        }
        return view
    }

    private fun setTheme() {
        if (AccountDetails.getThemeFlag(mainActivity).equals("white", ignoreCase = true)) {
            linear_parent.setBackgroundColor(resources.getColor(R.color.white))
            linear3.setBackgroundColor(resources.getColor(R.color.selectColor))
            Accounting_txt_one.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            Accounting_txt_two.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            Accounting_txt_three.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            Accounting_txt_four.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            Accounting_txt_five.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            Accounting_txt_six.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            Accounting_txt_seven.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
        }
    }


    override fun setUserVisibleHint(isFragmentVisible_: Boolean) {
        super.setUserVisibleHint(true)
        //  if (this.isVisible) { // we check that the fragment is becoming visible
        if (isFragmentVisible_ && !_hasLoadedOnce) {
            //NetCheck().execute()
            _hasLoadedOnce = true
        }
        //   }
    }

    fun StrategyBuilderAccountingView(view: View) {

//      getStrategyBuilderView request is send to server with requestType = u and cReportType = A for Accounting data
        var strategyBuilderViewRequest = StrategyBuilderViewRequest(StrategyBuilderViewRequest.Request(
                FormFactor = "M",
                svcGroup = "portfolio",
                svcName = "getStrategyBuilderView",
                svcVersion = "1.0.0",
                requestType = "u",
                data = StrategyBuilderViewRequest.Request.Data(
                        cSymbol = Util.getPrefs(activity).getString("selectedSymbol", "Nifty")!!,
                        cExchange = "NSE",
                        cClientCode = AccountDetails.getUsername(activity),
                        cEFBase = "F",
                        cExpiry = Util.getPrefs(activity).getString("selectedExpiryDate", "All")!!,
                        cGreekClientID = AccountDetails.getUsername(activity),
                        cReportType = "A",   //  var ="V"    accc="A"  report="S"
                        cStrategy = Util.getPrefs(activity).getString("selectedStrategy", "All")!!,
                        dCallIV = 0.0,
                        dDaysLeft = 0.0,
                        dInterestRate = 0.0,
                        dMarketRate = 0.0,
                        dMidStrike = 0.0,
                        dPutIV = 0.0,
                        dStrikeDiff = 0.0,
                        iIVType = 0,
                        iRangeD = 0,
                        iRangeU = 0)))

        Log.e(ContentValues.TAG, "Request=====>" + Gson().toJson(strategyBuilderViewRequest))
        ApiClient.instance.getStrategyBuilderView(WSHandler.encodeToBase64(Gson().toJson(strategyBuilderViewRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                    Log.e(ContentValues.TAG, "Response=====>" + responseStr)
                    SFBuildAccountingData = Gson().fromJson(responseStr, SFBuildUpResponseAccounting::class.java)
                    Log.e(ContentValues.TAG, "Response=====>" + SFBuildAccountingData)
                    ShowAccountingData(view)

                } else {

                }
            }


            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

            }

        })
    }

    private fun ShowAccountingData(view: View?) {
        try {
            val AccountingData: ArrayList<String> = arrayListOf()
            AccountingData.add("Accounting")
            AccountingData.add("Margins")
            AccountingData.add("Cash Out Flow")
            AccountingData.add("Expense")

            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(activity!!, AccountDetails.getRowSpinnerSimple(), AccountingData)
            adapter.setDropDownViewResource(R.layout.custom_spinner)


            accounting_spinner.setAdapter(adapter)
            accounting_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    var accountingSelection = AccountingData.get(p2)

                    if (SFBuildAccountingData.response.data.data != null) {
                        ShowDataOnAccountingSelection(accountingSelection)
                    }
                }


            }
        } catch (e: Exception) {

        }
    }

    private fun ShowDataOnAccountingSelection(accountingSelection: String) {

        if (accountingSelection.equals("Accounting")) {
            Accounting_txt_one.setText("Premium")
            Accounting_txt_two.setText("Stock (R)")
            Accounting_txt_three.setText("Future (R)")
            Accounting_txt_four.setText("Option (R)")
            Accounting_txt_five.setText("Stock (U)")
            Accounting_txt_six.setText("Future (U)")
            Accounting_txt_seven.setText("Option (U)")

            CheckIfGraterThenZero(Accounting_value_one, SFBuildAccountingData.response.data.data.get(0).dPremium)
            CheckIfGraterThenZero(Accounting_value_two, SFBuildAccountingData.response.data.data.get(0).dStackR)
            CheckIfGraterThenZero(Accounting_value_three, SFBuildAccountingData.response.data.data.get(0).dFutureR)
            CheckIfGraterThenZero(Accounting_value_four, SFBuildAccountingData.response.data.data.get(0).dOptionR)
            CheckIfGraterThenZero(Accounting_value_five, SFBuildAccountingData.response.data.data.get(0).dStackU)
            CheckIfGraterThenZero(Accounting_value_six, SFBuildAccountingData.response.data.data.get(0).dFutureU)
            CheckIfGraterThenZero(Accounting_value_seven, SFBuildAccountingData.response.data.data.get(0).dOptionU)

            val myIntList = listOf(
                    SFBuildAccountingData.response.data.data.get(0).dPremium,
                    SFBuildAccountingData.response.data.data.get(0).dStackR,
                    SFBuildAccountingData.response.data.data.get(0).dFutureR,
                    SFBuildAccountingData.response.data.data.get(0).dOptionR,
                    SFBuildAccountingData.response.data.data.get(0).dStackU,
                    SFBuildAccountingData.response.data.data.get(0).dFutureU,
                    SFBuildAccountingData.response.data.data.get(0).dOptionU
            )
            balance_total_text.setText("Balance")
            CheckIfGraterThenZero(balance_total_value, myIntList.sum())


        } else if (accountingSelection.equals("Margins")) {
            Accounting_txt_one.setText("Span Margin")
            Accounting_txt_two.setText("Exposure Margin")
            Accounting_txt_three.setText("Other Margin")
            Accounting_txt_four.setText("")
            Accounting_txt_five.setText("")
            Accounting_txt_six.setText("")
            Accounting_txt_seven.setText("")

            CheckIfGraterThenZero(Accounting_value_one, SFBuildAccountingData.response.data.data.get(0).dSpanMargin)
            CheckIfGraterThenZero(Accounting_value_two, SFBuildAccountingData.response.data.data.get(0).dExpoMargin)
            CheckIfGraterThenZero(Accounting_value_three, SFBuildAccountingData.response.data.data.get(0).dOtherMargin)
            Accounting_value_four.setText("")
            Accounting_value_five.setText("")
            Accounting_value_six.setText("")
            Accounting_value_seven.setText("")

            val myIntList = listOf(
                    SFBuildAccountingData.response.data.data.get(0).dSpanMargin,
                    SFBuildAccountingData.response.data.data.get(0).dExpoMargin,
                    SFBuildAccountingData.response.data.data.get(0).dOtherMargin
            )
            balance_total_text.setText("Total")
            CheckIfGraterThenZero(balance_total_value, myIntList.sum())
        } else if (accountingSelection.equals("Cash Out Flow")) {
            Accounting_txt_one.setText("Premium")
            Accounting_txt_two.setText("Equity")
            Accounting_txt_three.setText("Stock (R)")
            Accounting_txt_four.setText("Future (R)")
            Accounting_txt_five.setText("Option (R)")
            Accounting_txt_six.setText("Future (U)")
            Accounting_txt_seven.setText("")

            CheckIfGraterThenZero(Accounting_value_one, SFBuildAccountingData.response.data.data.get(0).dPremium)
            CheckIfGraterThenZero(Accounting_value_two, SFBuildAccountingData.response.data.data.get(0).dEquity)
            CheckIfGraterThenZero(Accounting_value_three, SFBuildAccountingData.response.data.data.get(0).dStackR)
            CheckIfGraterThenZero(Accounting_value_four, SFBuildAccountingData.response.data.data.get(0).dFutureR)
            CheckIfGraterThenZero(Accounting_value_five, SFBuildAccountingData.response.data.data.get(0).dOptionR)
            CheckIfGraterThenZero(Accounting_value_six, SFBuildAccountingData.response.data.data.get(0).dFutureU)
            Accounting_value_seven.setText("")

            val myIntList = listOf(
                    SFBuildAccountingData.response.data.data.get(0).dPremium,
                    SFBuildAccountingData.response.data.data.get(0).dEquity,
                    SFBuildAccountingData.response.data.data.get(0).dStackR,
                    SFBuildAccountingData.response.data.data.get(0).dFutureR,
                    SFBuildAccountingData.response.data.data.get(0).dOptionR,
                    SFBuildAccountingData.response.data.data.get(0).dFutureU
            )
            balance_total_text.setText("Total")
            CheckIfGraterThenZero(balance_total_value, myIntList.sum())

        } else if (accountingSelection.equals("Expense")) {
            Accounting_txt_one.setText("Today's Exp")
            Accounting_txt_two.setText("Total Exp")
            Accounting_txt_three.setText("Today Interest")
            Accounting_txt_four.setText("Total Interest")
            Accounting_txt_five.setText("Exposure(CR)")
            Accounting_txt_six.setText("")
            Accounting_txt_seven.setText("")

            CheckIfGraterThenZero(Accounting_value_one, SFBuildAccountingData.response.data.data.get(0).dTodaysExpense)
            CheckIfGraterThenZero(Accounting_value_two, SFBuildAccountingData.response.data.data.get(0).dTotalExpense)
            CheckIfGraterThenZero(Accounting_value_three, SFBuildAccountingData.response.data.data.get(0).dTodaysInterest)
            CheckIfGraterThenZero(Accounting_value_four, SFBuildAccountingData.response.data.data.get(0).dTotalInterest)
            CheckIfGraterThenZero(Accounting_value_five, SFBuildAccountingData.response.data.data.get(0).dExposure)
            Accounting_value_six.setText("")
            Accounting_value_seven.setText("")

            val myIntList = listOf(
                    SFBuildAccountingData.response.data.data.get(0).dTodaysExpense,
                    SFBuildAccountingData.response.data.data.get(0).dTotalExpense,
                    SFBuildAccountingData.response.data.data.get(0).dTodaysInterest,
                    SFBuildAccountingData.response.data.data.get(0).dTotalInterest,
                    SFBuildAccountingData.response.data.data.get(0).dExposure
            )
            balance_total_text.setText("Total")
            CheckIfGraterThenZero(balance_total_value, myIntList.sum())

        }

    }

    private fun CheckIfGraterThenZero(accountingValueOne: GreekTextView, dTodaysExpense: Double) {

        if (dTodaysExpense < 0) {
            accountingValueOne.setTextColor(resources.getColor(R.color.red_bg))
            accountingValueOne.setText(String.format("%.2f", dTodaysExpense))
        } else if (dTodaysExpense == 0.0) {

            if (AccountDetails.getThemeFlag(context).equals("white")) {
                //accountingValueOne.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
                accountingValueOne.setTextColor(resources.getColor(R.color.black))
            } else {
                accountingValueOne.setTextColor(resources.getColor(R.color.white))

            }

            if (accountingValueOne == balance_total_value) {
                accountingValueOne.setTextColor(resources.getColor(R.color.white))
            }
            accountingValueOne.setText(String.format("%.2f", dTodaysExpense))
        } else {
            accountingValueOne.setTextColor(resources.getColor(R.color.green_bg))
            accountingValueOne.setText(String.format("%.2f", dTodaysExpense))
        }

    }
}
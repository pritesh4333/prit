package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.greekmain.core.network.WSHandler
import com.acumengroup.mobile.GreekBaseFragment
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.api.ApiClient
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.SFBuildUpResponseVar
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.StrategyBuilderViewRequest
import com.acumengroup.ui.edittext.GreekEditText
import com.acumengroup.ui.textview.GreekTextView
import com.acumengroup.greekmain.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sf_build_report_fragment.*
import kotlinx.android.synthetic.main.sf_build_report_fragment.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SfBuildReportFragment : GreekBaseFragment() {

    private var _hasLoadedOnce = true
    lateinit var SFBuildReportData: SFBuildUpResponseVar
    var dMidStrike = 0.0
    var dStrikeDiff = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.sf_build_report_fragment, container, false)

        dMidStrike = Util.getPrefs(activity).getString("LTP", "0.0")!!.toDouble()
        dStrikeDiff = Util.getPrefs(activity).getString("StrikeDiff", "0.0")!!.toDouble()


        setTheam(view)
        view.mid_strike.setText("" + String.format("%.2f", dMidStrike))
        view.strike_diff.setText("" + String.format("%.2f", dStrikeDiff))

        view.findViewById<GreekEditText>(R.id.range_up).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().equals("")) {

                } else {
                    if (p0.toString().toInt() == 0 || p0.toString().toInt() >= 4) {
                        view.findViewById<GreekEditText>(R.id.range_up).setText("")
                    }
                }
            }
        })
        view.findViewById<GreekEditText>(R.id.range_down).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().equals("")) {

                } else {
                    if (p0.toString().toInt() == 0 || p0.toString().toInt() >= 4 || p0.toString().equals("")) {
                        view.findViewById<GreekEditText>(R.id.range_down).setText("")
                    }
                }
            }
        })
        if (_hasLoadedOnce) {
            StrategyBuilderReportView(view)
        }

        view.refresh.setOnClickListener {
            if (_hasLoadedOnce) {
                StrategyBuilderReportView(view)
            }
        }
        return view
    }

    private fun setTheam(view: View) {
        if (AccountDetails.getThemeFlag(context).equals("white")) {
            view!!.report_layout.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.header_layout.setBackgroundColor(view.context.resources.getColor(R.color.grey_textcolor))
            view!!.header_strip.setBackgroundColor(view.context.resources.getColor(R.color.grey_textcolor))
            view!!.mid_strike_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.mid_strikediff_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.mid_strikerange_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.mid_strikerangedown_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.mid_strike.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.strike_diff.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.range_up.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.range_down.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.mid_strike.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.strike_diff.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.range_up.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.range_down.setBackgroundColor(view.context.resources.getColor(R.color.white))

            view!!.strike_header.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.strike_balance.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.expbal.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.strike_delta.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.strike_theta.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.strike_vega.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.strike_gamma.setBackgroundColor(view.context.resources.getColor(R.color.white))

            view!!.strike_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.strike_balancetext.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.strike_exp_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_text.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.col1.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col2.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col3.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col4.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col5.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col6.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col7.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col8.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col9.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col10.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col11.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col12.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col13.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col14.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col15.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col16.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col17.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col18.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col19.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col20.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col21.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col22.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col23.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col24.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col25.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col26.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col27.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col28.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col29.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col30.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col31.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col32.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col33.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col34.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col35.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col36.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col37.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col38.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col39.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col40.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col41.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col42.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col43.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col44.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col45.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col46.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col47.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col48.setBackgroundColor(view.context.resources.getColor(R.color.white))
            view!!.col49.setBackgroundColor(view.context.resources.getColor(R.color.white))


            view!!.strike_down_3.setTextColor(view.context.resources.getColor(R.color.blue_700))
            view!!.strike_down_2.setTextColor(view.context.resources.getColor(R.color.blue_700))
            view!!.strike_down_1.setTextColor(view.context.resources.getColor(R.color.blue_700))
            view!!.strike_0.setTextColor(view.context.resources.getColor(R.color.blue_700))
            view!!.strike_up_1.setTextColor(view.context.resources.getColor(R.color.blue_700))
            view!!.strike_up_2.setTextColor(view.context.resources.getColor(R.color.blue_700))
            view!!.strike_up_3.setTextColor(view.context.resources.getColor(R.color.blue_700))


            view!!.balance_down_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.expbalance_down_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_down_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_down_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_down_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_down_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.balance_down_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.expbalance_down_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_down_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_down_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_down_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_down_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.balance_down_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.balance_down_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.expbalance_down_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_down_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_down_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_down_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_down_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.balance_down_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.balance_0.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.expbalance_0.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_0.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_0.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_0.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_0.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.balance_0.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.balance_up_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.expbalance_up_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_up_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_up_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_up_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_up_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.balance_up_1.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.balance_up_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.expbalance_up_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_up_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_up_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_up_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_up_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.balance_up_2.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

            view!!.balance_up_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.expbalance_up_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.delta_up_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.theta_up_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.vega_up_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.gamma_up_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
            view!!.balance_up_3.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
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


    fun StrategyBuilderReportView(view: View) {

//      getStrategyBuilderView request is send to server with requestType = u and cReportType = S for Report data
        var strategyBuilderViewRequest = StrategyBuilderViewRequest(StrategyBuilderViewRequest.Request(
                FormFactor = "M",
                svcGroup = "portfolio",
                svcName = "getStrategyBuilderView",
                svcVersion = "1.0.0",
                requestType = "u",
                data = StrategyBuilderViewRequest.Request.Data(
                        cSymbol = Util.getPrefs(activity).getString("selectedSymbol", "NIFTY")!!,
                        cExchange = "NSE",
                        cClientCode = AccountDetails.getUsername(activity),
                        cEFBase = "F",
                        cExpiry = Util.getPrefs(activity).getString("selectedExpiryDate", "All")!!,
                        cGreekClientID = AccountDetails.getUsername(activity),
                        cReportType = "S",   //  var ="V"    accc="A"  report="S"
                        cStrategy = Util.getPrefs(activity).getString("selectedStrategy", "All")!!,
                        dCallIV = 0.0,
                        dDaysLeft = 0.0,
                        dInterestRate = 0.0,
                        dMarketRate = 0.0,
                        dMidStrike = Util.getPrefs(activity).getString("LTP", "0.0")!!.toDouble(),
                        dPutIV = 0.0,
                        dStrikeDiff = Util.getPrefs(activity).getString("StrikeDiff", "0.0")!!.toDouble(),
                        iIVType = 0,
                        iRangeD = 0,
                        iRangeU = 0)))

        Log.e(ContentValues.TAG, "Request=====>" + Gson().toJson(strategyBuilderViewRequest))
        ApiClient.instance.getStrategyBuilderView(WSHandler.encodeToBase64(Gson().toJson(strategyBuilderViewRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {

                    var responseStr: String = WSHandler.decodeBase64(response.body()!!.string())
                    Log.e(ContentValues.TAG, "Response=====>" + responseStr)

                    SFBuildReportData = Gson().fromJson(responseStr, SFBuildUpResponseVar::class.java)
                    ShowReportnData(view)
                } else {

                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

            }

        })
    }

    private fun ShowReportnData(view: View) {
        var strick3 = 0.0
        var strick2 = 0.0
        var strick1 = 0.0
        var strickup1 = 0.0
        var strickup2 = 0.0
        var strickup3 = 0.0
        try {
            for (item in 0..4) {

                var rangedown = view.findViewById<GreekEditText>(R.id.range_down).text.toString().toInt()

                if (SFBuildReportData.response.data.data != null) {

                    if (SFBuildReportData.response.data.data[item].iReportType == 1) {

                        if (SFBuildReportData.response.data.data[item].iRange == 3) {

                            if (SFBuildReportData.response.data.data[item].iRange > rangedown) {

                                view.findViewById<LinearLayout>(R.id.down_3_row).visibility = View.GONE

                            } else {

                                view.findViewById<LinearLayout>(R.id.down_3_row).visibility = View.VISIBLE
                                strick3 = dMidStrike - dStrikeDiff - dStrikeDiff - dStrikeDiff
                                view.strike_down_3.setText("" + String.format("%.2f", strick3))
                                CheckIfGraterThenZeroview(view.balance_down_3, SFBuildReportData.response.data.data[item].dBalance)
                                CheckIfGraterThenZeroview(view.expbalance_down_3, SFBuildReportData.response.data.data[item].dExpBalance)
                                CheckIfGraterThenZeroview(view.delta_down_3, SFBuildReportData.response.data.data[item].dDeltaVal)
                                CheckIfGraterThenZeroview(view.theta_down_3, SFBuildReportData.response.data.data[item].dThetaVal)
                                CheckIfGraterThenZeroview(view.vega_down_3, SFBuildReportData.response.data.data[item].dVegaVal)
                                CheckIfGraterThenZeroview(view.gamma_down_3, SFBuildReportData.response.data.data[item].dGammaVal)
                            }

                        }

                    }
                    if (SFBuildReportData.response.data.data[item].iReportType == 1) {

                        if (SFBuildReportData.response.data.data[item].iRange == 2) {
                            if (SFBuildReportData.response.data.data[item].iRange > rangedown) {
                                view.findViewById<LinearLayout>(R.id.down_2_row).visibility = View.GONE
                            } else {
                                view.findViewById<LinearLayout>(R.id.down_2_row).visibility = View.VISIBLE
                                strick2 = dMidStrike - dStrikeDiff - dStrikeDiff
                                view.strike_down_2.setText("" + String.format("%.2f", strick2))
                                CheckIfGraterThenZeroview(view.balance_down_2, SFBuildReportData.response.data.data[item].dBalance)
                                CheckIfGraterThenZeroview(view.expbalance_down_2, SFBuildReportData.response.data.data[item].dExpBalance)
                                CheckIfGraterThenZeroview(view.delta_down_2, SFBuildReportData.response.data.data[item].dDeltaVal)
                                CheckIfGraterThenZeroview(view.theta_down_2, SFBuildReportData.response.data.data[item].dThetaVal)
                                CheckIfGraterThenZeroview(view.vega_down_2, SFBuildReportData.response.data.data[item].dVegaVal)
                                CheckIfGraterThenZeroview(view.gamma_down_2, SFBuildReportData.response.data.data[item].dGammaVal)
                            }
//                        }
//                        else{
//                            view.findViewById<LinearLayout>(R.id.down_2_row).visibility=View.GONE
//                        }
                        }

                    }
                    if (SFBuildReportData.response.data.data[item].iReportType == 1) {

                        if (SFBuildReportData.response.data.data[item].iRange == 1) {
//                        if (view.findViewById<GreekEditText>(R.id.range_down).text.toString().toInt()<=SFBuildReportData.response.data.data[item].iRange){
                            if (SFBuildReportData.response.data.data[item].iRange > rangedown) {
                                view.findViewById<LinearLayout>(R.id.down_1_row).visibility = View.GONE
                            } else {
                                view.findViewById<LinearLayout>(R.id.down_1_row).visibility = View.VISIBLE
                                strick1 = dMidStrike - dStrikeDiff
                                view.strike_down_1.setText("" + String.format("%.2f", strick1))
                                CheckIfGraterThenZeroview(view.balance_down_1, SFBuildReportData.response.data.data[item].dBalance)
                                CheckIfGraterThenZeroview(view.expbalance_down_1, SFBuildReportData.response.data.data[item].dExpBalance)
                                CheckIfGraterThenZeroview(view.delta_down_1, SFBuildReportData.response.data.data[item].dDeltaVal)
                                CheckIfGraterThenZeroview(view.theta_down_1, SFBuildReportData.response.data.data[item].dThetaVal)
                                CheckIfGraterThenZeroview(view.vega_down_1, SFBuildReportData.response.data.data[item].dVegaVal)
                                CheckIfGraterThenZeroview(view.gamma_down_1, SFBuildReportData.response.data.data[item].dGammaVal)
                            }
//                        }
//                        else{
//
//                            view.findViewById<LinearLayout>(R.id.down_1_row).visibility=View.GONE
//
//                        }
                        }

                    }
                    if (SFBuildReportData.response.data.data[item].iReportType == 1) {
                        if (SFBuildReportData.response.data.data[item].iRange == 0) {

                            view.strike_0.setText("" + String.format("%.2f", dMidStrike))
                            CheckIfGraterThenZeroview(view.balance_0, SFBuildReportData.response.data.data[item].dBalance)
                            CheckIfGraterThenZeroview(view.expbalance_0, SFBuildReportData.response.data.data[item].dExpBalance)
                            CheckIfGraterThenZeroview(view.delta_0, SFBuildReportData.response.data.data[item].dDeltaVal)
                            CheckIfGraterThenZeroview(view.theta_0, SFBuildReportData.response.data.data[item].dThetaVal)
                            CheckIfGraterThenZeroview(view.vega_0, SFBuildReportData.response.data.data[item].dVegaVal)
                            CheckIfGraterThenZeroview(view.gamma_0, SFBuildReportData.response.data.data[item].dGammaVal)

                        }
                    }

                }
            }
            for (item in 4..6) {
                var rangeup = view.findViewById<GreekEditText>(R.id.range_up).text.toString().toInt()
                if (SFBuildReportData.response.data.data != null) {
                    if (SFBuildReportData.response.data.data[item].iReportType == 1) {
                        if (SFBuildReportData.response.data.data[item].iRange == 1) {
                            if (SFBuildReportData.response.data.data[item].iRange > rangeup) {
                                view.findViewById<LinearLayout>(R.id.up_1_row).visibility = View.GONE
                            } else {
                                view.findViewById<LinearLayout>(R.id.up_1_row).visibility = View.VISIBLE
                                strickup1 = dMidStrike + dStrikeDiff
                                view.strike_up_1.setText("" + String.format("%.2f", strickup1))
                                CheckIfGraterThenZeroview(view.balance_up_1, SFBuildReportData.response.data.data[item].dBalance)
                                CheckIfGraterThenZeroview(view.expbalance_up_1, SFBuildReportData.response.data.data[item].dExpBalance)
                                CheckIfGraterThenZeroview(view.delta_up_1, SFBuildReportData.response.data.data[item].dDeltaVal)
                                CheckIfGraterThenZeroview(view.theta_up_1, SFBuildReportData.response.data.data[item].dThetaVal)
                                CheckIfGraterThenZeroview(view.vega_up_1, SFBuildReportData.response.data.data[item].dVegaVal)
                                CheckIfGraterThenZeroview(view.gamma_up_1, SFBuildReportData.response.data.data[item].dGammaVal)
                            }
                        }
                    }
                    if (SFBuildReportData.response.data.data[item].iReportType == 1) {
                        if (SFBuildReportData.response.data.data[item].iRange == 2) {
                            if (SFBuildReportData.response.data.data[item].iRange > rangeup) {
                                view.findViewById<LinearLayout>(R.id.up_2_row).visibility = View.GONE
                            } else {
                                view.findViewById<LinearLayout>(R.id.up_2_row).visibility = View.VISIBLE
                                strickup2 = dMidStrike + dStrikeDiff + dStrikeDiff
                                view.strike_up_2.setText("" + String.format("%.2f", strickup2))
                                CheckIfGraterThenZeroview(view.balance_up_2, SFBuildReportData.response.data.data[item].dBalance)
                                CheckIfGraterThenZeroview(view.expbalance_up_2, SFBuildReportData.response.data.data[item].dExpBalance)
                                CheckIfGraterThenZeroview(view.delta_up_2, SFBuildReportData.response.data.data[item].dDeltaVal)
                                CheckIfGraterThenZeroview(view.theta_up_2, SFBuildReportData.response.data.data[item].dThetaVal)
                                CheckIfGraterThenZeroview(view.vega_up_2, SFBuildReportData.response.data.data[item].dVegaVal)
                                CheckIfGraterThenZeroview(view.gamma_up_2, SFBuildReportData.response.data.data[item].dGammaVal)
                            }
                        }
                    }
                    if (SFBuildReportData.response.data.data[item].iReportType == 1) {
                        if (SFBuildReportData.response.data.data[item].iRange == 3) {
                            if (SFBuildReportData.response.data.data[item].iRange > rangeup) {
                                view.findViewById<LinearLayout>(R.id.up_3_row).visibility = View.GONE
                            } else {
                                view.findViewById<LinearLayout>(R.id.up_3_row).visibility = View.VISIBLE
                                strickup3 = dMidStrike + dStrikeDiff + dStrikeDiff + dStrikeDiff
                                strike_up_3.setText("" + String.format("%.2f", strickup3))
                                CheckIfGraterThenZeroview(balance_up_3, SFBuildReportData.response.data.data[item].dBalance)
                                CheckIfGraterThenZeroview(expbalance_up_3, SFBuildReportData.response.data.data[item].dExpBalance)
                                CheckIfGraterThenZeroview(delta_up_3, SFBuildReportData.response.data.data[item].dDeltaVal)
                                CheckIfGraterThenZeroview(theta_up_3, SFBuildReportData.response.data.data[item].dThetaVal)
                                CheckIfGraterThenZeroview(vega_up_3, SFBuildReportData.response.data.data[item].dVegaVal)
                                CheckIfGraterThenZero(view.gamma_up_3, SFBuildReportData.response.data.data[item].dGammaVal)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }
    private fun CheckIfGraterThenZeroview(strikeUp3: GreekTextView?, strickup3: Double) {
        try{
            if (strickup3 < 0) {
                strikeUp3?.setTextColor(resources.getColor(R.color.red_bg))
                strikeUp3?.setText(String.format("%.2f", strickup3))
            } else if (strickup3 == 0.0) {
                if (AccountDetails.getThemeFlag(context).equals("white")){
                    strikeUp3?.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
                    strikeUp3?.setText(String.format("%.2f", strickup3))
                }else {
                    strikeUp3?.setTextColor(resources.getColor(R.color.white))
                    strikeUp3?.setText(String.format("%.2f", strickup3))
                }
            } else {
                strikeUp3?.setTextColor(resources.getColor(R.color.green_bg))
                strikeUp3?.setText(String.format("%.2f", strickup3))
            }
        } catch (e: Exception ) {
            e.printStackTrace();
        }
    }

    private fun CheckIfGraterThenZero(accountingValueOne: GreekTextView, dTodaysExpense: Double) {


    }


}
package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuild.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.greekmain.core.network.WSHandler
import com.acumengroup.mobile.GreekBaseFragment
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.api.ApiClient
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.SFBuildUpResponseVar
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.StrategyBuilderViewRequest
import com.acumengroup.ui.textview.GreekTextView
import com.acumengroup.greekmain.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sf_build_filter_setting.*
import kotlinx.android.synthetic.main.sf_build_var_fragment.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SfBuildVarFragment : GreekBaseFragment() {
    var responseStr:String =""
    var isvisiblevar: Boolean =true
    lateinit var SFBuildvarData :SFBuildUpResponseVar
    var MarketUpDown ="Up"
    private var _hasLoadedOnce = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.sf_build_var_fragment, container, false)

        if (_hasLoadedOnce) {
            //Toast.makeText(activity,"sf build VAR fragment",Toast.LENGTH_SHORT).show();
            StrategyBuilderVarView(view)
        }
        view.market_up_down.setOnClickListener {

            if( responseStr!="") {
                ShowmarketupDownData(view)
            }
        }

        return view
    }
    override fun setUserVisibleHint(isFragmentVisible_: Boolean) {
        super.setUserVisibleHint(true)
       // if (this.isVisible) { // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                //NetCheck().execute()
                _hasLoadedOnce = true
            }
     //   }
    }


    fun StrategyBuilderVarView(view: View) {
        //      getStrategyBuilderView request is send to server with requestType = u and cReportType = V for Var
        var strategyBuilderViewRequest = StrategyBuilderViewRequest(StrategyBuilderViewRequest.Request(
                FormFactor = "M",
                svcGroup = "portfolio",
                svcName = "getStrategyBuilderView",
                svcVersion = "1.0.0",
                requestType = "u",
                data = StrategyBuilderViewRequest.Request.Data(
                        cSymbol= Util.getPrefs(activity).getString("selectedSymbol","Nifty")!!,
                        cExchange = "NSE",
                        cClientCode = AccountDetails.getUsername(activity),
                        cEFBase = "F",
                        cExpiry = Util.getPrefs(activity).getString("selectedExpiryDate", "All")!!,
                        cGreekClientID = AccountDetails.getUsername(activity),
                        cReportType = "V",   //  var ="V"    accc="A"  report="S"
                        cStrategy = Util.getPrefs(activity).getString("selectedStrategy", "All")!!,
                        dCallIV = 0.0,
                        dDaysLeft = 0.0,
                        dInterestRate = 0.0,
                        dMarketRate = 0.0,
                        dMidStrike = Util.getPrefs(activity).getString("LTP","0.0")!!.toDouble(),
                        dPutIV = 0.0,
                        dStrikeDiff = Util.getPrefs(activity).getString("StrikeDiff","0.0")!!.toDouble(),
                        iIVType = 0,
                        iRangeD = 0,
                        iRangeU = 0)))

        Log.e(TAG, "Request=====>" + Gson().toJson(strategyBuilderViewRequest))
        ApiClient.instance.getStrategyBuilderView(WSHandler.encodeToBase64(Gson().toJson(strategyBuilderViewRequest))).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                if (response != null && response.isSuccessful) {


                    responseStr = WSHandler.decodeBase64(response.body()!!.string())
                    Log.e(TAG, "Response=====>" + responseStr)

                    SFBuildvarData = Gson().fromJson(responseStr, SFBuildUpResponseVar::class.java)
                    Log.e(TAG, "Response=====>" + SFBuildvarData)

                    ShowmarketupDownData(view)

                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

            }

        })
    }
    private fun ShowmarketupDownData(view: View) {
        try {
            if (MarketUpDown.equals("Up")) {
                view.market_up_down.setBackgroundColor(getResources().getColor(R.color.green_bg))
                view.market_type.setText("Market Up")
                view.market_symbol.background = activity?.getDrawable(R.drawable.arrow4_up)
                view.market_symbol.setColorFilter(getResources().getColor(R.color.white));

                if (AccountDetails.getThemeFlag(view.context).equals("white")){

                    view.var_type_three.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.mareket_rate_three.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.balance_three.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.delta_neutral_three.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.gamma_value_three.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.theta_value_three.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.vega_value_three.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.parent_layout.setBackgroundColor(view.context.resources.getColor(R.color.marketDepthGreyColor))
                    view.var_type_three.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.mareket_rate_three.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.balance_three.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.delta_neutral_three.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.gamma_value_three.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.theta_value_three.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.vega_value_three.setBackgroundColor(view.context.resources.getColor(R.color.white))

                    view.var_type_two.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_matekt_rate_two.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_balance_two.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_delta_neutral_two.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_gamma_value_two.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_theta_value_two.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_vega_value_two.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.var_type_two.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_matekt_rate_two.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_balance_two.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_delta_neutral_two.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_gamma_value_two.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_theta_value_two.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_vega_value_two.setBackgroundColor(view.context.resources.getColor(R.color.white))

                    view.var_type_one.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_matekt_rate_one.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_balance_one.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_delta_neutral_one.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_gamma_value_one.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_theta_value_one.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.var_vega_value_one.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.var_type_one.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_matekt_rate_one.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_balance_one.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_delta_neutral_one.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_gamma_value_one.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_theta_value_one.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.var_vega_value_one.setBackgroundColor(view.context.resources.getColor(R.color.white))

                    view.mid_percentage.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.percent_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.mid_marketrate.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.market_rate_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.mid_balance.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.balance_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.mid_delta_neutral.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.delta_neutral_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.mid_gamma_value.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.gamma_value_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.mid_theta.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.theta_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.mid_vega_value.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.vega_value_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))

                    view.linear1.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.linear2.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.linear3.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.linear4.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.linear5.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.linear6.setBackgroundColor(view.context.resources.getColor(R.color.white))
                    view.linear7.setBackgroundColor(view.context.resources.getColor(R.color.white))

                   // view.Mid_txt.setTextColor(view.context.resources.getColor(AccountDetails.textColorDropdown))
                    view.linear.setBackgroundColor(view.context.resources.getColor(R.color.selectColor))
                }

                MarketUpDown = "Down"
                if (SFBuildvarData != null) {
                    for (item in 0..4) {

                        if (SFBuildvarData.response.data.data != null) {
                            if (SFBuildvarData.response.data.data[item].iReportType == 2) {
                                if (SFBuildvarData.response.data.data[item].iRange == 3) {
                                    view.var_type_three.setText("" + SFBuildvarData.response.data.data[item].iRange)
                                    view.mareket_rate_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dMarketRate))
                                    view.balance_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dBalance))
                                    view.delta_neutral_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dDeltaNeutral))
                                    view.gamma_value_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dGammaVal))
                                    view.theta_value_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dThetaVal))
                                    view.vega_value_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dVegaVal))
                                }
                            }

                            if (SFBuildvarData.response.data.data[item].iReportType == 2) {
                                if (SFBuildvarData.response.data.data[item].iRange == 2) {
                                    view.var_type_two.setText("" + SFBuildvarData.response.data.data[item].iRange)
                                    view.var_matekt_rate_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dMarketRate))
                                    view.var_balance_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dBalance))
                                    view.var_delta_neutral_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dDeltaNeutral))
                                    view.var_gamma_value_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dGammaVal))
                                    view.var_theta_value_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dThetaVal))
                                    view.var_vega_value_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dVegaVal))
                                }
                            }
                            if (SFBuildvarData.response.data.data[item].iReportType == 2) {
                                if (SFBuildvarData.response.data.data[item].iRange == 1) {
                                    view.var_type_one.setText("" + SFBuildvarData.response.data.data[item].iRange)
                                    view.var_matekt_rate_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dMarketRate))
                                    view.var_balance_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dBalance))
                                    view.var_delta_neutral_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dDeltaNeutral))
                                    view.var_gamma_value_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dGammaVal))
                                    view.var_theta_value_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dThetaVal))
                                    view.var_vega_value_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dVegaVal))
                                }
                            }
                            if (SFBuildvarData.response.data.data[item].iReportType == 2) {
                                if (SFBuildvarData.response.data.data[item].iRange == 0) {
                                    view.mid_percentage.setText("" + SFBuildvarData.response.data.data[item].iRange)
                                    view.mid_marketrate.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dMarketRate))
                                    view.mid_balance.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dBalance))
                                    view.mid_delta_neutral.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dDeltaNeutral))
                                    view.mid_gamma_value.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dGammaVal))
                                    view.mid_theta.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dThetaVal))
                                    view.mid_vega_value.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dVegaVal))
                                }
                            }
                        }
                    }
                }
            } else {
                view.market_up_down.setBackgroundColor(getResources().getColor(R.color.red_bg))
                view.market_type.setText("Market Down")
                view.market_symbol.background = activity?.getDrawable(R.drawable.arrow4_down)
                view.market_symbol.setColorFilter(getResources().getColor(R.color.white));

                MarketUpDown = "Up"
                if (SFBuildvarData != null) {
                    for (item in 4..6) {
                        if (SFBuildvarData.response.data.data != null) {
                            if (SFBuildvarData.response.data.data[item].iReportType == 2) {
                                if (SFBuildvarData.response.data.data[item].iRange == 1) {
                                    view.var_type_three.setText("" + SFBuildvarData.response.data.data[item].iRange)
                                    view.mareket_rate_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dMarketRate))
                                    view.balance_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dBalance))
                                    view.delta_neutral_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dDeltaNeutral))
                                    view.gamma_value_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dGammaVal))
                                    view.theta_value_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dThetaVal))
                                    view.vega_value_three.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dVegaVal))
                                }
                            }
                            if (SFBuildvarData.response.data.data[item].iReportType == 2) {
                                if (SFBuildvarData.response.data.data[item].iRange == 2) {
                                    view.var_type_two.setText("" + SFBuildvarData.response.data.data[item].iRange)
                                    view.var_matekt_rate_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dMarketRate))
                                    view.var_balance_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dBalance))
                                    view.var_delta_neutral_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dDeltaNeutral))
                                    view.var_gamma_value_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dGammaVal))
                                    view.var_theta_value_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dThetaVal))
                                    view.var_vega_value_two.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dVegaVal))
                                }
                            }

                            if (SFBuildvarData.response.data.data[item].iReportType == 2) {
                                if (SFBuildvarData.response.data.data[item].iRange == 3) {
                                    view.var_type_one.setText("" + SFBuildvarData.response.data.data[item].iRange)
                                    view.var_matekt_rate_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dMarketRate))
                                    view.var_balance_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dBalance))
                                    view.var_delta_neutral_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dDeltaNeutral))
                                    view.var_gamma_value_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dGammaVal))
                                    view.var_theta_value_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dThetaVal))
                                    view.var_vega_value_one.setText(String.format("%.2f", SFBuildvarData.response.data.data[item].dVegaVal))
                                }
                            }
                        }
                    }
                }
            }


        } catch (e: Exception) {
            e.printStackTrace();
        }
    }
}
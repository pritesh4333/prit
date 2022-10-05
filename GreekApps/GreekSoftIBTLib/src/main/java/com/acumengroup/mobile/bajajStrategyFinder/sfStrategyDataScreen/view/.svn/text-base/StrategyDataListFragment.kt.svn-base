package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.view

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.greekmain.core.constants.GreekConstants
import com.acumengroup.mobile.GreekBaseFragment
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.model.ScanPostRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.StrategyBuilderEventRequest
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.StrategyNameResponse
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.strategyBuildUpRepository
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.ScanDataResponse
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.viewModel.StrategyDataViewModel
import com.acumengroup.mobile.databinding.FragmentStrategyDataListBinding
import com.acumengroup.ui.button.GreekButton
import com.acumengroup.ui.textview.GreekTextView
import com.acumengroup.greekmain.util.Util
import com.acumengroup.greekmain.util.Util.getGsonParser
import com.acumengroup.columnsliderlib.AdaptiveTableLayout
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter
import com.acumengroup.columnsliderlib.OnItemClickListener
import com.acumengroup.columnsliderlib.OnItemLongClickListener
import kotlinx.android.synthetic.main.addpostition_alert.view.*
import kotlinx.android.synthetic.main.fragment_strategy_data_list.*
import kotlin.collections.ArrayList


class StrategyDataListFragment : GreekBaseFragment(), OnItemClickListener, OnItemLongClickListener {
    lateinit var handler: Handler
    lateinit var scanPostRequest: ScanPostRequest
    lateinit var viewDataBinding: FragmentStrategyDataListBinding
    lateinit var viewmodel: StrategyDataViewModel
    var strategyArrayList = ArrayList<StrategyNameResponse.Response.Data.StrategyName>()
    var strategyNameList = ArrayList<String>()
    private var mTableLayout: AdaptiveTableLayout? = null
    var MaxGainFilter: Boolean = false
    var InvestmentFilter: Boolean = false
    var PremiumFilter: Boolean = false
    var VolatilityFilter: Boolean = false
    var dataSourceArrayList = ArrayList<ScanDataResponse.Response.Data.ScanData>()
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private var _hasLoadedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            scanPostRequest = getGsonParser().fromJson(it.getString("ScanData"), ScanPostRequest::class.java)
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.sfDataViewModel?.onClickScanView(activity, scanPostRequest)

        mTableLayout = view.findViewById(R.id.tableLayout)

        setupObservers()


    }


    fun setupObservers() {
        var mTableAdapter: LinkedAdaptiveTableAdapter<*>? = null
        viewDataBinding.sfDataViewModel!!.strategyDataListLive = MutableLiveData()
        viewDataBinding.sfDataViewModel!!.strategyDataListLive.observe(viewLifecycleOwner, Observer {

            txt_wefound.text = it.size.toString()

            if (it.size > 0) {

                it.add(0, ScanDataResponse.Response.Data.ScanData("Empty", "",
                        "Empty", "Empty", "", "0.00", "0.00", "",
                        "", "", "", "",
                        "", "", "", "",
                        "", "", "", "", "",
                        "", "", "", "", "",
                        "", "", "", "", "", "",
                        "", "", "", ""))

                empty_view.visibility = View.GONE
                mTableLayout!!.visibility = View.VISIBLE
                dataSourceArrayList = it
                mTableAdapter = StrategyDataTableAdapter(context!!, it)
                mTableAdapter!!.setOnItemClickListener(this)
                mTableAdapter!!.setOnItemLongClickListener(this)
                if(it!=null) {
                    mTableLayout!!.setAdapter(mTableAdapter)
                }

                mTableAdapter!!.notifyDataSetChanged()

            } else {
                empty_view.visibility = View.VISIBLE
                mTableLayout!!.visibility = View.GONE
            }
        })

        this.viewDataBinding.sfDataViewModel?.weFound?.observe(viewLifecycleOwner, Observer {

            //            txt_wefound.text = it

        })
        this.viewDataBinding.sfDataViewModel?.dataLoading?.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgress()
            } else
                hideProgress()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewDataBinding = DataBindingUtil.inflate<FragmentStrategyDataListBinding>(inflater, R.layout.fragment_strategy_data_list, container, false)
        viewmodel = ViewModelProviders.of(this@StrategyDataListFragment).get(StrategyDataViewModel::class.java)

        AccountDetails.currentFragment = GreekConstants.NAV_TO_STRATEGY_FINDER

        viewDataBinding.sfDataViewModel = viewmodel
        viewDataBinding.setLifecycleOwner { lifecycle }
        setTheam();
        return viewDataBinding.root
    }

    fun setTheam(){
        if (AccountDetails.getThemeFlag(activity).equals("white")){
            viewDataBinding.sfLayout.setBackgroundColor(resources.getColor(R.color.white))
            viewDataBinding.sfDhader.setBackgroundColor(resources.getColor(R.color.selectColor))
            viewDataBinding.emptyView.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
        }
    }
    override fun onFragmentResume() {
        super.onFragmentResume()
        handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (activity != null) {
                    //Restrected as discuss with Bhushan sir
                  /*  viewDataBinding.sfDataViewModel?.onClickScanView(activity, scanPostRequest)
                    var longnum = Util.getPrefs(activity).getString("RefreshTimer", "1")
                    var milisecond = longnum!!.toInt() * 60000
                    handler.postDelayed(this, milisecond.toLong())//1 sec delay*/
                } else {

                }

            }
        }, 0)


    }

    override fun onFragmentPause() {
        super.onFragmentPause()
        handler.removeCallbacksAndMessages(null);

    }


    override fun onDetach() {
        handler.removeCallbacksAndMessages(null);
        super.onDetach()

    }

    override fun onColumnHeaderClick(column: Int) {

        if (column == 1) {

            if (MaxGainFilter) {

                viewDataBinding.sfDataViewModel?.onClickSortMaxGain()

            } else {
                viewDataBinding.sfDataViewModel?.onClickSortMaxLoss()

            }

            MaxGainFilter = !MaxGainFilter


        } else if (column == 2) {

            if (InvestmentFilter) {

                viewDataBinding.sfDataViewModel?.onClickSortInvestment()

            } else {
                viewDataBinding.sfDataViewModel?.onClickSortBEP()
            }
            InvestmentFilter = !InvestmentFilter

        } else if (column == 3) {

            if (PremiumFilter) {

                viewDataBinding.sfDataViewModel?.onClickSortNetPremium()

            } else {
                viewDataBinding.sfDataViewModel?.onClickSortRatio()
            }
            PremiumFilter = !PremiumFilter


        } else if (column == 4) {

            if (VolatilityFilter) {

                viewDataBinding.sfDataViewModel?.onClickVolatility()

            } else {
                viewDataBinding.sfDataViewModel?.onClickSpredDiff()
            }
            VolatilityFilter = !VolatilityFilter


        }

    }

    override fun onRowHeaderClick(row: Int) {


        StrategyName()
        val colors = arrayOf("Submit IOC Order", "Submit Day Order", "Add Position")

//        Alertdialog with "Submit IOC Order, Submit Day Order and Add position" option display.
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Pick Action")
        builder.setItems(colors, DialogInterface.OnClickListener { dialog, which ->

            if (which == 2) {
                if (Util.getPrefs(view?.context).getBoolean("AlertPopup", false) == false) {
                    AddPostionNavigate(row)
                } else {

                    val mDialogView = LayoutInflater.from(view?.context).inflate(R.layout.addpostition_alert, null)
                    val mBuilder = android.app.AlertDialog.Builder(view?.context).setView(mDialogView)
                    val mAlertDialog = mBuilder.show()


                    if (AccountDetails.getThemeFlag(view?.context).equals("white")){
                        mDialogView.addposition_layout.setBackgroundColor(view?.context!!.resources.getColor(R.color.white))
                        mDialogView.no.setBackgroundColor(view?.context!!.resources.getColor(R.color.grey_textcolor))
                        mDialogView.postion_header.setBackgroundColor(view?.context!!.resources.getColor(R.color.selectColor))
                        mDialogView.postion_text.setTextColor(view?.context!!.resources.getColor(AccountDetails.textColorDropdown))
                        mDialogView.postion_name.setTextColor(view?.context!!.resources.getColor(AccountDetails.textColorDropdown))
                        mDialogView!!.spinner_names.setBackgroundResource(R.drawable.gradient_spinner_black)

                    }
                    val assetTypeAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(view?.context!!, AccountDetails.getRowSpinnerSimple(),
                            strategyNameList.toTypedArray()) {
                        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                            val v = super.getView(position, convertView, parent) as TextView
                            // v.setTypeface(font);
                            if (AccountDetails.getThemeFlag(view?.context).equals("white", ignoreCase = true)) {
                                v.setTextColor(view?.context!!.resources.getColor(R.color.black))
                            } else {
                                v.setTextColor(view?.context!!.resources.getColor(R.color.white))
                            }
                            v.setPadding(15, 15, 15, 15)
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
                    mDialogView.findViewById<Spinner>(R.id.spinner_names).setAdapter(assetTypeAdapter)

                    mDialogView.spinner_names.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            var selectedStrategy = (p1 as GreekTextView).text.toString()
                        }
                    }

                    val yes = mDialogView.findViewById<GreekButton>(R.id.yes)

                    yes.setOnClickListener {
                        AddPostionNavigate(row)
                        mAlertDialog.cancel()
                    }
                    val no = mDialogView.findViewById<GreekButton>(R.id.no)
                    no.setOnClickListener {
                        yes.setBackgroundColor(R.drawable.single_line_border_bajaj_gray)
                        no.setBackgroundColor(R.drawable.single_line_border_bajaj)
                        mAlertDialog.cancel()
                    }
                    val close = mDialogView.findViewById<ImageView>(R.id.popup_close)
                    close.setOnClickListener {
                        mAlertDialog.cancel()
                    }

                }
            } else if (which == 1) {

                viewDataBinding.sfDataViewModel?.ShowAlertDialogForSubmitIocOrder(activity, dataSourceArrayList.get(row), "DAY")
            } else if (which == 0) {

                viewDataBinding.sfDataViewModel?.ShowAlertDialogForSubmitIocOrder(activity, dataSourceArrayList.get(row),"IOC")
            }
        })
        builder.show()
    }

    private fun AddPostionNavigate(row: Int) {

        var splitStringArray: ArrayList<String> = ArrayList()
        if (dataSourceArrayList.get(row).cTradeDetails.contains("\n")) {
            splitStringArray = dataSourceArrayList.get(row).cTradeDetails.split("\n") as ArrayList<String>

        } else {

            splitStringArray.add(dataSourceArrayList.get(row).cTradeDetails)
        }


        for (i in 0 until splitStringArray.size) {

            if (i == 0) {

                if (splitStringArray.get(i).contains("BUY")) {
                    var fillQty = splitStringArray.get(i).split(" ").get(1)
//                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")
                    var dFillPrice = splitStringArray.get(i).split(" ").get(4).removePrefix("@")

                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_0,
                            dataSourceArrayList.get(row).iToken_0,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)

                } else if (splitStringArray.get(i).contains("SELL")) {

                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")

                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_0,
                            dataSourceArrayList.get(row).iToken_0,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)
                }


            }
            else if (i == 1) {

                if (splitStringArray.get(i).contains("SELL")) {

                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")

                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_1,
                            dataSourceArrayList.get(row).iToken_1,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)

                } else if (splitStringArray.get(i).contains("BUY")) {

                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")

                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_1,
                            dataSourceArrayList.get(row).iToken_1,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)

                }


            }
            else if (i == 2) {

                if (splitStringArray.get(i).contains("BUY")) {

                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")

                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_2,
                            dataSourceArrayList.get(row).iToken_2,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)
                } else if (splitStringArray.get(i).contains("SELL")) {

                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")


                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_2,
                            dataSourceArrayList.get(row).iToken_2,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)

                }


            }
            else if (i == 3) {


                if (splitStringArray.get(i).contains("SELL")) {

                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")


                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_3,
                            dataSourceArrayList.get(row).iToken_3,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)

                } else if (splitStringArray.get(i).contains("BUY")) {

                    var fillQty = splitStringArray.get(i).split(" ").get(1)
                    var dFillPrice = splitStringArray.get(i).split(" ").get(6).removePrefix("@")

                    viewDataBinding.sfDataViewModel?.tradeNotificationView(
                            dataSourceArrayList.get(row).iBuySell_3,
                            dataSourceArrayList.get(row).iToken_3,
                            fillQty,
                            dFillPrice,
                            AccountDetails.getUsername(activity).capitalize(), row)
                }


            }

        }


        val args = Bundle()
        val scanPostJsonString: String = getGsonParser().toJson(scanPostRequest)
        val scanDataJsonString: String = getGsonParser().toJson(dataSourceArrayList.get(row))
        args.putString("ScanData", scanPostJsonString)
        args.putString("scanDataResponse", scanDataJsonString)
        navigateTo(GreekConstants.NAV_TO_STRATEGY_BUILDUP_SCREEN, args, true)


    }

    fun StrategyName() {

        var strategyBuilderEventRequest = StrategyBuilderEventRequest(StrategyBuilderEventRequest.Request(
                svcVersion = "1.0.0",
                requestType = "U",
                svcName = "StrategyBuilderEvent",
                svcGroup = "portfolio",
                FormFactor = "M",
                data = StrategyBuilderEventRequest.Request.Data(
                        cGreekClientID = AccountDetails.getUsername(view?.context),
                        iErrorCode = "1040")))

        strategyBuildUpRepository.getInstance().StrategyBuilderEventRequest(strategyBuilderEventRequest,
                fun(isSuccess: Boolean, response: StrategyNameResponse?) {
                    if (isSuccess) {
                        var ErrorCode = response!!.response.data.ErrorCode

                        if (ErrorCode == 0) {

                            strategyArrayList = response.response.data.data
                            strategyNameList.clear()
                            strategyNameList.add("#VIEW ONLY#")
                            strategyArrayList.forEach {
                                strategyNameList.add(it.cStrategy)
                            }
                        }
                    } else {

                    }

                })
    }

    override fun onLeftTopHeaderClick() {

        viewDataBinding.sfDataViewModel?.onClickSortTDetails()
    }

    override fun onItemClick(row: Int, column: Int) {


    }

    override fun onItemLongClick(row: Int, column: Int) {


    }

    override fun onLeftTopHeaderLongClick() {


    }

}

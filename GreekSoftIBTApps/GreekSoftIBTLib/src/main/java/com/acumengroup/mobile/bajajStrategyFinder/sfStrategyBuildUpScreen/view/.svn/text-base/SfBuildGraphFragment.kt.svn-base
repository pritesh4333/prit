package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.mobile.GreekBaseFragment
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.*
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.strategyBuildUpRepository
import com.acumengroup.greekmain.util.Util
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.otp_for_guest.*
import kotlinx.android.synthetic.main.sf_build_graph_fragment.*
import java.lang.Float
import java.lang.reflect.Type


class SfBuildGraphFragment : GreekBaseFragment() {
    var graph_setting = ArrayList<Int>()
    private var _hasLoadedOnce = true
    lateinit var views: View

    //    var arraylistminusey =null//Creating an empty arraylist
//    val arraylistminusey = arrayOf<Float>()
    var arraylistminusey = Array<kotlin.Float>(7) { 0.0F } //Creating an empty arraylist
    val arraylistpositvey = Array<kotlin.Float>(7) { 0.0F } //Creating an empty arraylist
    val arraylistminusex = Array<kotlin.Float>(7) { 0.0F } //Creating an empty arraylist
    val arraylistpositvex = Array<kotlin.Float>(7) { 0.0F } //Creating an empty arraylist
    var selectedchartSetting = "Balance";

    lateinit var layout_leftDays: LinearLayout
    lateinit var maxloss_name: TextView
    lateinit var investment_name: TextView
    lateinit var max_profit_name: TextView
    lateinit var left_arrow: TextView
    lateinit var right_arrow: TextView
    lateinit var breakevens_name: TextView
    lateinit var txt_investment: TextView
    lateinit var txt_breakeven: TextView
    lateinit var left_day_name: TextView
    lateinit var txt_leftdays: TextView
    lateinit var maxprofit_layout: LinearLayout
    lateinit var maxloss_layout: LinearLayout
    lateinit var layout_leftDays_name: LinearLayout
    lateinit var parent_layout: LinearLayout
    lateinit var layout_spinner: LinearLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        views = inflater.inflate(R.layout.sf_build_graph_fragment, container, false)
        val rainbow = context!!.resources.getIntArray(R.array.chartcolor)
        layout_leftDays = views.findViewById(R.id.layout_leftDays)
        max_profit_name = views.findViewById(R.id.max_profit_name)
        txt_leftdays = views.findViewById(R.id.txt_leftdays)
        left_arrow = views.findViewById(R.id.left_arrow)
        right_arrow = views.findViewById(R.id.right_arrow)
        maxloss_name = views.findViewById(R.id.maxloss_name)
        left_day_name = views.findViewById(R.id.left_day_name)
        investment_name = views.findViewById(R.id.investment_name)
        txt_investment = views.findViewById(R.id.txt_investment)
        breakevens_name = views.findViewById(R.id.breakevens_name)
        txt_breakeven = views.findViewById(R.id.txt_breakeven)
//        leftday_layout.setBackgroundColor(resources.getColor(R.color.white))
        maxprofit_layout = views.findViewById(R.id.maxprofit_layout)
        maxloss_layout = views.findViewById(R.id.maxloss_layout)
        layout_leftDays_name = views.findViewById(R.id.layout_leftDays_name)
        parent_layout = views.findViewById(R.id.parent_layout)
        layout_spinner = views.findViewById(R.id.layout_spinner)
        setTheme()
        for (i in 0 until rainbow.size) {
            graph_setting.add(i, rainbow[i])
        }
        val chartlist: ArrayList<String> = arrayListOf()
        chartlist.add("Balance")
        chartlist.add("Exp Bal")
        chartlist.add("Delta")
        chartlist.add("Theta")
        chartlist.add("Vega")
        chartlist.add("Gama")
        var adapters = CustomDropDownAdapter(views.context, graph_setting, chartlist)

        val spinner_graph_setting = views.findViewById<Spinner>(R.id.spinner_graph_setting)
        spinner_graph_setting.setAdapter(adapters)
        spinner_graph_setting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //expiryDate = SFscanFragmentViewModel.expirylList.get(p2)

                StrategyChartBuilderView()
                selectedchartSetting = chartlist.get(p2)

            }
        }
        if (_hasLoadedOnce) {
            StrategyChartBuilderView()
//            loadChart(views)
        }

        return views
    }


    private fun setTheme() {
        if (AccountDetails.getThemeFlag(mainActivity).equals("white", ignoreCase = true)) {
            layout_leftDays.setBackgroundColor(mainActivity.resources.getColor(R.color.selectColor))
//            parent_layout.setBackgroundColor(resources.getColor(R.color.white))
           /* max_profit_name.setTextColor(resources.getColor(R.color.black))
            maxloss_name.setTextColor(resources.getColor(R.color.black))
            investment_name.setTextColor(resources.getColor(R.color.black))
            txt_investment.setTextColor(resources.getColor(R.color.black))
            breakevens_name.setTextColor(resources.getColor(R.color.black))
            txt_breakeven.setTextColor(resources.getColor(R.color.black))*/
            txt_leftdays.setTextColor(resources.getColor(R.color.black))
            left_arrow.setTextColor(resources.getColor(R.color.black))
            right_arrow.setTextColor(resources.getColor(R.color.black))
            //left_day_name.setTextColor(resources.getColor(R.color.black))

            maxprofit_layout.setBackgroundColor(resources.getColor(R.color.selectColor))
            maxloss_layout.setBackgroundColor(resources.getColor(R.color.selectColor))
//            layout_leftDays_name.setBackgroundColor(resources.getColor(R.color.white))
            txt_leftdays.setBackgroundColor(resources.getColor(R.color.white))
            left_arrow.setBackgroundColor(resources.getColor(R.color.white))
            right_arrow.setBackgroundColor(resources.getColor(R.color.white))
            parent_layout.setBackgroundColor(resources.getColor(R.color.white))

        }
    }

    fun loadChart(views: View) {
        val mChart = views.findViewById(R.id.chart1) as LineChart
//        mChart.setBackgroundColor(resources.getColor(R.color.white))

        /*val rainbow = context!!.resources.getIntArray(R.array.chartcolor)

        for (i in 0 until rainbow.size) {
            graph_setting.add(i, rainbow[i])
        }
        val chartlist: ArrayList<String> = arrayListOf()
        chartlist.add("Balance")
        chartlist.add("Exp Bal")
        chartlist.add("Delta")
        chartlist.add("Theta")
        chartlist.add("Vega")
        chartlist.add("Gama")
        var adapters = CustomDropDownAdapter(views.context, graph_setting, chartlist)

        val spinner_graph_setting = views.findViewById<Spinner>(R.id.spinner_graph_setting)
        spinner_graph_setting.setAdapter(adapters)
        spinner_graph_setting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //expiryDate = SFscanFragmentViewModel.expirylList.get(p2)

                StrategyChartBuilderView()
                selectedchartSetting = chartlist.get(p2)

            }
        }*/
        showChartsDaynamic(mChart)
    }

    override fun setUserVisibleHint(isFragmentVisible_: Boolean) {
        super.setUserVisibleHint(true)
        //      if (isVisible) { // we check that the fragment is becoming visible
        if (isFragmentVisible_ && !_hasLoadedOnce) {
            //     loadChart(views)
            _hasLoadedOnce = true
        }
        //   }
    }

    private fun showChartsDaynamic(mChart: LineChart) {
        mChart.setDrawGridBackground(false)

        // no description text


        // enable touch gestures
        // no description text
// enable touch gestures
        mChart.setTouchEnabled(true)

        // enable scaling and dragging
        // enable scaling and dragging
        mChart.isDragEnabled = true
        mChart.setScaleEnabled(true)


        // // Chart Style // //
// background color
//mChart.setBackgroundColor(Color.WHITE);
// disable description text
// enable touch gestures
        mChart.setTouchEnabled(true)
        // set listeners
//mChart.setDrawGridBackground(true);
// create marker to display box when values are selected
        if (activity != null) {
            val mv = MyMarkerView(activity, R.layout.custom_marker_view)

            // Set the marker to the chart
            mv.setChartView(mChart)
            mChart.marker = mv
            // enable scaling and dragging
            mChart.isDragEnabled = true
            mChart.setScaleEnabled(true)
            mChart.isScaleXEnabled = true
            mChart.isScaleYEnabled = true
            // force pinch zoom along both axis
            mChart.setPinchZoom(true)


            // draw points over time
            // draw points over time
            mChart.animateX(1000)

//        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        // draw legend entries as lines
//        l.setForm(LegendForm.LINE);


            // limit lines are drawn behind data (and not on top)


            //        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        // draw legend entries as lines
//        l.setForm(LegendForm.LINE);
// limit lines are drawn behind data (and not on top)
            if (AccountDetails.getThemeFlag(context).equals("white", ignoreCase = true)) {
                mChart.axisRight.isEnabled = false
                // mChart.getAxisLeft().setLabelCount(5, true);
                // mChart.getAxisLeft().setLabelCount(5, true);
                mChart.axisLeft.textColor = Color.BLACK
                mChart.xAxis.textColor = Color.BLACK
                mChart.setBackgroundColor(Color.WHITE)
                mChart.legend.isEnabled = false
            } else {
                mChart.axisRight.isEnabled = false
                // mChart.getAxisLeft().setLabelCount(5, true);
                // mChart.getAxisLeft().setLabelCount(5, true);
                mChart.axisLeft.textColor = Color.WHITE
                mChart.xAxis.textColor = Color.WHITE
                mChart.setBackgroundColor(Color.BLACK)
                mChart.legend.isEnabled = false
            }


//        val lineData_0y =  arraylistminusey
            val lineData_1y = arraylistminusey

//        val lineData_0y = floatArrayOf(0f, 4000f, 3000f, 5000f)
//        val lineData_0y = arraylistpositvey


            /*val lineData_1x = floatArrayOf(-50000f, -30000f, -20000f, 0f)
        val lineData_0x = floatArrayOf(0f, 30000f, 40000f, 60000f)
        */
            val lineData_1x = arraylistminusex
//        val lineData_0x = arraylistpositvex


            val all_line_dataY = arrayOf(lineData_1y/*,lineData_0y*/)
            val all_line_dataX = arrayOf(lineData_1x)


            val graph_linear_background = intArrayOf(R.drawable.fade_red, R.drawable.fade_green)


            val values_all: MutableList<ArrayList<Entry>> = ArrayList()
            val values_allfinal: MutableList<ArrayList<Entry>> = ArrayList()
            var p = 0
            var pp = 0
            for (k in all_line_dataY.indices) {
                val list: ArrayList<Entry> = ArrayList()
                for (i in 0 until all_line_dataY[k].size) {
                    list.add(Entry(i.toFloat(), all_line_dataY[k][i]))

                }

                values_all.add(list)
            }
            for (k in values_all.indices) {

                for (i in 0 until all_line_dataX[k].size) {
                    values_all[k][i].x = all_line_dataX[k][i]

                }
            }


            val dataSets = ArrayList<ILineDataSet?>()


            val map: MutableMap<String, LineDataSet> = HashMap()
            for (i in all_line_dataY.indices) {
                map["set0$i"] = LineDataSet(values_all[i], "")
                // set the line to be drawn like this "- - - - - -"
                map["set0$i"]!!.color = Color.RED
                map["set0$i"]!!.setCircleColor(Color.RED)
                map["set0$i"]!!.mode = LineDataSet.Mode.CUBIC_BEZIER
                map["set0$i"]!!.lineWidth = 2f
                //            map.get("set0" + i).setDrawValues(! map.get("set0" + i).isDrawValuesEnabled());
//            map.get("set0" + i).setDrawValues(true);
//            map.get("set0" + i).setDrawIcons(!map.get("set0" + i).isDrawIconsEnabled());
                map["set0$i"]!!.circleRadius = 4f
                map["set0$i"]!!.setDrawCircleHole(false)
                // map.get("set0" + i).setDrawFilled(true);
// map.get("set0" + i).setDrawIcons(false);
// draw dashed line
//map.get("set0" + i).enableDashedLine(10f, 5f, 0f);
// black lines and points
                if (AccountDetails.getThemeFlag(context).equals("white", ignoreCase = true)) {
                    map["set0$i"]!!.color = Color.BLACK
                    map["set0$i"]!!.setCircleColor(Color.BLACK)
                } else {
                    map["set0$i"]!!.color = Color.WHITE
                    map["set0$i"]!!.setCircleColor(Color.WHITE)
                }
                // line thickness and point size
                map["set0$i"]!!.lineWidth = 1f
                map["set0$i"]!!.circleRadius = 3f
                // draw points as solid circles
                map["set0$i"]!!.setDrawCircleHole(false)
                // customize legend entry
// map.get("set0" + i).setFormLineWidth(1f);
// map.get("set0" + i).setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                map["set0$i"]!!.formSize = 15f
                // text size of values
                map["set0$i"]!!.valueTextSize = 12f
                // draw selection line as dashed
// map.get("set0" + i).enableDashedHighlightLine(10f, 5f, 0f);
// set the filled area
                if (AccountDetails.getThemeFlag(context).equals("white", ignoreCase = true)) {
                    map["set0$i"]!!.setDrawFilled(true)
                    map["set0$i"]!!.valueTextColor = Color.BLACK
                } else {
                    map["set0$i"]!!.setDrawFilled(true)
                    map["set0$i"]!!.valueTextColor = Color.WHITE
                }
                dataSets.add(map["set0$i"])
                if (Utils.getSDKInt() >= 18) { // fill drawable only supported on api level 18 and above

                    /*chartlist.add("Balance")
        chartlist.add("Exp Bal")
        chartlist.add("Delta")
        chartlist.add("Theta")
        chartlist.add("Vega")
        chartlist.add("Gama")*/
                    if (selectedchartSetting.equals("Balance")) {
                        val drawable = activity?.let { ContextCompat.getDrawable(it, graph_linear_background[i]) }
                        map["set0$i"]!!.fillDrawable = drawable
                    }
                    if (selectedchartSetting.equals("Exp Bal")) {
                        val drawable = activity?.let { ContextCompat.getDrawable(it, R.drawable.fade_expbal) }
                        map["set0$i"]!!.fillDrawable = drawable
                    }
                    if (selectedchartSetting.equals("Delta")) {
                        val drawable = activity?.let { ContextCompat.getDrawable(it, R.drawable.fade_delta) }
                        map["set0$i"]!!.fillDrawable = drawable
                    }
                    if (selectedchartSetting.equals("Theta")) {
                        val drawable = activity?.let { ContextCompat.getDrawable(it, R.drawable.fade_theta) }
                        map["set0$i"]!!.fillDrawable = drawable
                    }
                    if (selectedchartSetting.equals("Vega")) {
                        val drawable = activity?.let { ContextCompat.getDrawable(it, R.drawable.fade_vega) }
                        map["set0$i"]!!.fillDrawable = drawable
                    }
                    if (selectedchartSetting.equals("Gama")) {
                        val drawable = activity?.let { ContextCompat.getDrawable(it, R.drawable.fade_gama) }
                        map["set0$i"]!!.fillDrawable = drawable
                    }
                } else {
                    map["set0$i"]!!.fillColor = Color.TRANSPARENT
                }
            }
            val line_data = LineData(dataSets)

            // set data
            // set data
            mChart.axisLeft.setStartAtZero(false)
            mChart.axisRight.setStartAtZero(false)

            mChart.data = line_data

        }
    }


    fun StrategyChartBuilderView() {

        var arrayList = ArrayList<SBChartRequest.Request.Data.TokenInf>()
        var list = Util.getPrefs(activity).getString("tokrnlist", "")
        if (list!!.length > 0) {
            val type: Type = object : TypeToken<ArrayList<SBChartRequest.Request.Data.TokenInf>?>() {}.type
            arrayList = Gson().fromJson(list, type)

//      getStrategyBuilderView request is send to server with requestType = U and cReportType = S for Graph data
            var sbChartRequest = SBChartRequest(
                    request = SBChartRequest.Request(
                            FormFactor = "M",
                            requestType = "U",
                            svcGroup = "portfolio",
                            svcName = "strategyFinderView",
                            svcVersion = "1.0.0",
                            data = SBChartRequest.Request.Data(

                                    cEFBase = "F",
                                    cGreekClientID = AccountDetails.getUsername(activity),
                                    cReportType = "S",
                                    dInterestRate = "0",
                                    dMidStrike = Util.getPrefs(activity).getString("LTP", "0.0")!!.toDouble(),
                                    iRangeD = 3,
                                    iRangeU = 3,
                                    dStrikeDiff = Util.getPrefs(activity).getString("StrikeDiff", "0.0")!!.toDouble(),
                                    TokenInfo = arrayList
                            )
                    )
            )

            /*var strategyfinderViewChartRequest = StrategyfinderViewRequest(StrategyfinderViewRequest.Request(
                FormFactor = "M",
                svcGroup = "portfolio",
                svcName = "getStrategyBuilderView",
                svcVersion = "1.0.0",
                requestType = "u",
                data = StrategyfinderViewRequest.Request.Data(
//                        cSymbol = Util.getPrefs(activity).getString("selectedSymbol", "NIFTY"),
//                        cExchange = "NSE",
//                        cClientCode = AccountDetails.getUsername(activity),
                        cEFBase = "F",
//                        cExpiry = Util.getPrefs(activity).getString("selectedExpiryDate", "All"),
                        cGreekClientID = AccountDetails.getUsername(activity),
                        cReportType = "S",   //  var ="V"    accc="A"  report="S"
//                        cStrategy = Util.getPrefs(activity).getString("selectedStrategy", "All"),
//                        dCallIV = 0.0,
//                        dDaysLeft = 0.0,
                        dInterestRate = 0.0,
//                        dMarketRate = Util.getPrefs(activity).getString("LTP", "0.0").toDouble(),
                        dMidStrike = Util.getPrefs(activity).getString("LTP", "0.0").toDouble(),
//                        dPutIV = 0.0,
                        dStrikeDiff = Util.getPrefs(activity).getString("StrikeDiff", "0.0").toDouble(),
//                        iIVType = 0,
                        iRangeD = 0,
                        iRangeU = 0)))
*/
            strategyBuildUpRepository.getInstance().StrategyfinderChartViewRequest(sbChartRequest,
                    fun(isSuccess: Boolean, response: GraphResponseData?) {
                        if (isSuccess) {
                            var ErrorCode = response!!.response.data.ErrorCode
                            if (ErrorCode.equals("0")) {
                                var chartDataList = response!!.response.data.data


                                for (x in chartDataList.indices) {
                                    if (selectedchartSetting.equals("Balance")) {
//                                        if(Float.parseFloat(chartDataList[x].dBalance)<0.0) {
                                        arraylistminusey[x] = Float.parseFloat(chartDataList[x].dBalance)
                                        /* }else{
                                             arraylistpositvey[x] = Float.parseFloat(chartDataList[x].dBalance)
                                         }*/
                                    }
                                    if (selectedchartSetting.equals("Exp Bal")) {
                                        arraylistminusey[x] = Float.parseFloat(chartDataList[x].dExpBalance)
                                    }
                                    if (selectedchartSetting.equals("Delta")) {
                                        arraylistminusey[x] = Float.parseFloat(chartDataList[x].dDeltaVal)
                                    }
                                    if (selectedchartSetting.equals("Theta")) {
                                        arraylistminusey[x] = Float.parseFloat(chartDataList[x].dThetaVal)
                                    }
                                    if (selectedchartSetting.equals("Vega")) {
                                        arraylistminusey[x] = Float.parseFloat(chartDataList[x].dVegaVal)
                                    }
                                    if (selectedchartSetting.equals("Gama")) {
                                        arraylistminusey[x] = Float.parseFloat(chartDataList[x].dGammaVal)
                                    }
                                    /* if (Float.parseFloat(chartDataList[x].dMarketRate.toString()) < 0) {
                                     arraylistminusey[x]=Float.parseFloat(chartDataList[x].dMarketRate)
                                 } else {
                                     arraylistpositvey[x] = Float.parseFloat(chartDataList[x].dMarketRate);
                                 }*/

                                    arraylistminusex[x] = Float.parseFloat(chartDataList[x].dMarketRate);
                                    /*if (Float.parseFloat(chartDataList[x].dBalance.toString()) < 0) {
                                    arraylistminusex[x] = Float.parseFloat(chartDataList[x].dMarketRate);
                                } else {
                                    arraylistpositvex[x] = Float.parseFloat(chartDataList[x].dMarketRate);
                                }*/

                                }


                                /*totlachartvalue
                            forloop(rraylistminuse-){
                                totlachartvalue.add=={------}
                            }
                            forloop(rraylistminuse+){
                                totlachartvalue.add=={+++++}
                            }
                            totlachartvalue--------------++============*/





                                if (_hasLoadedOnce) {
//                                StrategyChartBuilderView()
                                    loadChart(views)
//                                    this method is call to load the chart in Graph
                                }
                            }
                        } else {

                            hideProgress()
                        }
                    })
        }
    }


    class MyMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
        private val tvContent: TextView

        // runs every time the MarkerView is redrawn, can be used to update the
// content (user-interface)
        override fun refreshContent(e: Entry, highlight: Highlight?) {
            if (e is CandleEntry) {
                tvContent.text = Utils.formatNumber(e.high, 0, true)
            } else {
                tvContent.text = Utils.formatNumber(e.y, 0, true)
            }
            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }

        init {
            tvContent = findViewById(R.id.tvContent)
        }
    }

    class CustomDropDownAdapter(val context: Context, var chartcolors: ArrayList<Int>, var chartname: ArrayList<String>) : BaseAdapter() {
        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.custome_chart_setting, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

            //vh.label.text = listItemsTxt.get(position)
            if (AccountDetails.getThemeFlag(context).equals("white", ignoreCase = true)) {
                vh.chart_text.setTextColor(context.getColor(R.color.black))
                vh.row_Layout.setBackgroundColor(context.getColor(R.color.grey_textcolor))
            }
            vh.chart_color.setBackgroundColor(chartcolors[position])
            vh.chart_text.setText(chartname[position])
            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return chartname.size
        }

        private class ItemRowHolder(row: View?) {

            val chart_text: TextView
            val chart_color: TextView
            val row_Layout: LinearLayout

            init {
                this.chart_text = row?.findViewById(R.id.chart_text) as TextView
                this.chart_color = row?.findViewById(R.id.chart_color) as TextView
                this.row_Layout = row?.findViewById(R.id.row_Layout) as LinearLayout
            }
        }
    }
}
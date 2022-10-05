package com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.acumengroup.greekmain.core.app.AccountDetails
import com.acumengroup.greekmain.core.constants.GreekConstants
import com.acumengroup.mobile.GreekBaseFragment
import com.acumengroup.mobile.R
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.viewModel.SFscanFragmentViewModel
import com.acumengroup.mobile.databinding.FragmentSfscanBinding
import com.acumengroup.greekmain.util.Util
import com.acumengroup.greekmain.util.Util.getGsonParser
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_sfscan.*


class SFScanFragment : GreekBaseFragment() {

    lateinit var viewDataBinding: FragmentSfscanBinding
    lateinit var viewmodel: SFscanFragmentViewModel
    lateinit var symbolList: List<String>
    lateinit var marketList: ArrayList<String>
    lateinit var combinationList: ArrayList<String>
    lateinit var volatileList: ArrayList<String>
    lateinit var strikePricelList: List<String>

    private var textColorPositive = 0
    private var textColorNegative = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
            textColorPositive = R.color.whitetheambuyColor
        } else {
            textColorPositive = R.color.dark_green_positive
        }
        textColorNegative = R.color.dark_red_negative

        symbolList = ArrayList<String>()
        strikePricelList = ArrayList<String>()

        marketList = ArrayList<String>()
        marketList.add("Select Outlook")
        marketList.add("BULLISH")
        marketList.add("BEARISH")
        marketList.add("NEUTRAL")
        marketList.add("VOLATILE")

        combinationList = ArrayList<String>()
        combinationList.add("Select Combination")
        combinationList.add("1")
        combinationList.add("2")
        combinationList.add("3")
        combinationList.add("4")
        combinationList.add("5")

        volatileList = ArrayList<String>()
        volatileList.add("Select Volatility")
        volatileList.add("ALL VOLATILITY")
        volatileList.add("INCREASE")
        volatileList.add("DECREASE")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewDataBinding = DataBindingUtil.inflate<FragmentSfscanBinding>(inflater, R.layout.fragment_sfscan, container, false)
        viewmodel = ViewModelProviders.of(this@SFScanFragment).get(SFscanFragmentViewModel::class.java)

        AccountDetails.currentFragment = GreekConstants.NAV_TO_STRATEGY_FINDER

        viewDataBinding.scanViewModel = viewmodel
        viewDataBinding.setLifecycleOwner { lifecycle }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.scanViewModel?.fetchSymbolList()

        setupSpinnerAdapter()
        setupObservers()
        setTheme()
    }

    private fun setTheme() {
        if (AccountDetails.getThemeFlag(mainActivity).equals("white", ignoreCase = true)) {
            txt_risk.setBackgroundColor(resources.getColor(R.color.selectColor))
            txt_target.setBackgroundColor(resources.getColor(R.color.selectColor))
            folding_cell.setBackgroundColor(resources.getColor(R.color.white))
            img_icon_down.setImageResource(R.drawable.black_more)
            img_icon_up.setImageResource(R.drawable.black_less)
            cell_content_view.setBackgroundColor(resources.getColor(R.color.white))
            btn_str_filter.setBackgroundColor(resources.getColor(R.color.selectColor))
            mainLayout.setBackgroundColor(resources.getColor(R.color.white))
            btn_optFilter.setBackgroundColor(resources.getColor(R.color.selectColor))
            symbolTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            expiryTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            autoCompleteTextView.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            marketTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            volatilityTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txtRAnk.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            targetTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            //txt_risk.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            //txt_target.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            ltpTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            changeTXT.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_ltp.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            txt_change.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            //btn_optFilter.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            //btn_str_filter.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            titile1.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            title2.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            title3.setTextColor(resources.getColor(AccountDetails.textColorDropdown))
            title4.setTextColor(resources.getColor(AccountDetails.textColorDropdown))

//            txt_risk.getBackground().setColorFilter(resources.getColor(R.color.marketDepthGreyColor),
//                    PorterDuff.Mode.SRC_ATOP)
//            txt_target.getBackground().setColorFilter(resources.getColor(R.color.marketDepthGreyColor),
//                    PorterDuff.Mode.SRC_ATOP)


        }
    }

    private fun setupSpinnerAdapter() {

        val Adapters: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(), volatileList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(resources.getColor(R.color.black))
                } else {
                    v.setTextColor(resources.getColor(R.color.white))
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
        spinner_volatility.setAdapter(Adapters)


        val Adapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(), marketList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(resources.getColor(R.color.black))
                } else {
                    v.setTextColor(resources.getColor(R.color.white))
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
        Adapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner_market.setAdapter(Adapter)


        spinner_market.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 3 || p2 == 4) {
                    volatileList.remove("NOCHANGE")
                    volatileList.add(4, "NOCHANGE")
                    Adapters.notifyDataSetChanged()
                } else {
                    volatileList.remove("NOCHANGE")
                    Adapters.notifyDataSetChanged()
                }
                viewDataBinding.scanViewModel?.onSelectMarketItem(marketList.get(p2))
            }
        }


        val TypeAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(), combinationList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(resources.getColor(R.color.black))
                } else {
                    v.setTextColor(resources.getColor(R.color.white))
                }
                v.setPadding(15, 15, 15, 15)
                return v
            }

            /* override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
                 val v = super.getView(position, convertView, parent) as TextView
                 //  v.setTypeface(font);
                 v.setTextColor(Color.BLACK)
                 v.setPadding(15, 15, 15, 15)
                 return v
             }*/
        }
        TypeAdapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner_combination.setAdapter(TypeAdapter)


        var expirylList = ArrayList<String>()
        expirylList.add("Select Expiry")
        val assetTypeAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(), expirylList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(resources.getColor(R.color.black))
                } else {
                    v.setTextColor(resources.getColor(R.color.white))
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
        spinner_expiry.setAdapter(assetTypeAdapter)


        var lowerList = ArrayList<String>()
        lowerList.add("Select Strike")

        val lowerAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(), lowerList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(resources.getColor(R.color.black))
                } else {
                    v.setTextColor(resources.getColor(R.color.white))
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
        lowerAdapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner_lwrStrike.setAdapter(lowerAdapter)

        val upperAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(), lowerList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(resources.getColor(R.color.black))
                } else {
                    v.setTextColor(resources.getColor(R.color.white))
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
        upperAdapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner_uprStrike.setAdapter(upperAdapter)


        val diffAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(), lowerList.toTypedArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent) as TextView
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                    v.setTextColor(resources.getColor(R.color.black))
                } else {
                    v.setTextColor(resources.getColor(R.color.white))
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
        diffAdapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner_strkDiffr.setAdapter(diffAdapter)


    }

    private fun setupObservers() {

        viewDataBinding.scanViewModel?.foldingCellSelectedLive?.observe(viewLifecycleOwner, Observer {

            folding_cell.toggle(it)

        })
        viewDataBinding.scanViewModel?.change?.observe(viewLifecycleOwner, Observer {

            if (it.startsWith("-")) {
                txt_change.setTextColor(resources.getColor(textColorNegative))
                txt_ltp.setTextColor(resources.getColor(textColorNegative))

            } else {
                txt_change.setTextColor(resources.getColor(textColorPositive))
                txt_ltp.setTextColor(resources.getColor(textColorPositive))
            }
        })
        viewDataBinding.scanViewModel?.ltpValuetxt?.observe(viewLifecycleOwner, Observer {

            //            if (it.startsWith("-")) {
//                txt_ltp.setTextColor(resources.getColor(textColorNegative))
//
//            } else {
//                txt_ltp.setTextColor(resources.getColor(textColorPositive))
//
//            }
        })

        viewDataBinding.scanViewModel?.symbolListLive?.observe(viewLifecycleOwner, Observer {
            symbolList = it

            val json = Gson().toJson(it)
            Util.getPrefs(activity).edit().putString("SymbolList", json).commit()
            setupAdapter()
        })

        viewDataBinding.scanViewModel?.expiryTimeStampListLive?.observe(viewLifecycleOwner, Observer {


            setupAdapteExpiryr(it)
            spinner_expiry.setSelection(1)
            spinner_combination.setSelection(1)
            spinner_market.setSelection(1)
            spinner_volatility.setSelection(1)
        })

        viewDataBinding.scanViewModel?.strikePriceListLive?.observe(viewLifecycleOwner, Observer {
            strikePricelList = it
        })
        viewDataBinding.scanViewModel?.isSymbolEmpty?.observe(viewLifecycleOwner, Observer {

            if (it) {
                autoCompleteTextView.setError("Select Symbol")
            }
        })

        viewDataBinding.scanViewModel?.lowerStrikePriceListLive?.observe(viewLifecycleOwner, Observer {
            if (it.size > 0)
                setupAdapteLowerStrike(it)
        })
        viewDataBinding.scanViewModel?.uperStrikePriceListLive?.observe(viewLifecycleOwner, Observer {
            if (it.size > 0)
                setupAdapteUpperStrike(it)
        })
        viewDataBinding.scanViewModel?.selectedUpperStrikePosition?.observe(viewLifecycleOwner, Observer {

            spinner_uprStrike.setSelection(it)
        })
        viewDataBinding.scanViewModel?.selectedLowerStrikePosition?.observe(viewLifecycleOwner, Observer {
            spinner_lwrStrike.setSelection(it)
        })
        viewDataBinding.scanViewModel?.intervalListListLive?.observe(viewLifecycleOwner, Observer {
            if (it.size > 0)
                setupAdapteUpperStrikeDiffrence(it)
        })

        viewDataBinding.scanViewModel?.dataLoading?.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgress()
            } else
                hideProgress()
        })

        viewDataBinding.scanViewModel?.submitScanRequest?.observe(viewLifecycleOwner, Observer {

            val args = Bundle()
            val scanDataJsonString: String = getGsonParser().toJson(it)
            args.putString("ScanData", scanDataJsonString)
            navigateTo(GreekConstants.NAV_TO_STRATEGY_DATA_SCREEN, args, true)
        })
    }

    private fun setupAdapter() {

        val viewModel = viewDataBinding.scanViewModel
        if (viewModel != null) {
            var adapters = ArrayAdapter(context!!, R.layout.row_spinner_mutualfund, symbolList)
            adapters.setDropDownViewResource(R.layout.custom_spinner)
            autoCompleteTextView.threshold = 1
            autoCompleteTextView.setAdapter(adapters) // fetch symbol list on click of textview

            autoCompleteTextView.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

                val selectedItem = adapterView.getItemAtPosition(i).toString()
                if (selectedItem.isNotEmpty())
                    viewDataBinding.scanViewModel?.fetchExpiryList(selectedItem)
                // on selection of symbol, getOptionsExpirydate request is call and expiry date is selected bydefault

            })
        }
    }

    private fun setupAdapteExpiryr(expirylList: ArrayList<String>) {
        val viewModel = viewDataBinding.scanViewModel
        if (viewModel != null) {
            val assetTypeAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!,
                    AccountDetails.getRowSpinnerSimple(), expirylList.toTypedArray()) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val v = super.getView(position, convertView, parent) as TextView
                    // v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                        v.setTextColor(resources.getColor(R.color.black))
                    } else {
                        v.setTextColor(resources.getColor(R.color.white))
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
            spinner_expiry.setAdapter(assetTypeAdapter)
        }

    }

    private fun setupAdapteLowerStrike(it: ArrayList<String>) {
        val viewModel = viewDataBinding.scanViewModel
        if (viewModel != null) {
            val lowerAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!,
                    AccountDetails.getRowSpinnerSimple(), it.toTypedArray()) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val v = super.getView(position, convertView, parent) as TextView
                    // v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                        v.setTextColor(resources.getColor(R.color.black))
                    } else {
                        v.setTextColor(resources.getColor(R.color.white))
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
            lowerAdapter.setDropDownViewResource(R.layout.custom_spinner)
            spinner_lwrStrike.setAdapter(lowerAdapter)


        }
    }

    private fun setupAdapteUpperStrike(it: ArrayList<String>) {
        val viewModel = viewDataBinding.scanViewModel
        if (viewModel != null) {

            val upperAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!,
                    AccountDetails.getRowSpinnerSimple(), it.toTypedArray()) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val v = super.getView(position, convertView, parent) as TextView
                    // v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                        v.setTextColor(resources.getColor(R.color.black))
                    } else {
                        v.setTextColor(resources.getColor(R.color.white))
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
            upperAdapter.setDropDownViewResource(R.layout.custom_spinner)
            spinner_uprStrike.setAdapter(upperAdapter)

        }
    }

    private fun setupAdapteUpperStrikeDiffrence(it: ArrayList<String>) {
        val viewModel = viewDataBinding.scanViewModel
        if (viewModel != null) {

            val diffAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(context!!, AccountDetails.getRowSpinnerSimple(),
                    it.toTypedArray()) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val v = super.getView(position, convertView, parent) as TextView
                    // v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(activity).equals("white", ignoreCase = true)) {
                        v.setTextColor(resources.getColor(R.color.black))
                    } else {
                        v.setTextColor(resources.getColor(R.color.white))
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
            diffAdapter.setDropDownViewResource(R.layout.custom_spinner)
            spinner_strkDiffr.setAdapter(diffAdapter)


        }
    }


}

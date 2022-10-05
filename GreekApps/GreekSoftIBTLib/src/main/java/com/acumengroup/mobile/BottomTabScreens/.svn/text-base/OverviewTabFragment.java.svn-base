package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.marketsindicesstock.IndicesStock;
import com.acumengroup.greekmain.core.model.marketsindicesstock.MarketsIndicesStockRequest;
import com.acumengroup.greekmain.core.model.marketsindicesstock.MarketsIndicesStockResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData_v2;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerMainHeader_v2;
import com.acumengroup.mobile.BottomTabScreens.adapter.OverViewRecycleAdapter;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MarketDataResponse;
import com.acumengroup.greekmain.core.network.MarketMoversByValueModel;
import com.acumengroup.mobile.model.SubHeader_v2;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.bfsl.core.network.MarketMoversModel;
import com.bfsl.core.network.TopGainersModels;
import com.bfsl.core.network.TopLosersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class OverviewTabFragment extends GreekBaseFragment implements View.OnClickListener {

    private ArrayList<GainerData_v2> glist = new ArrayList<>();
    private ArrayList<GainerData_v2> glistloser = new ArrayList<>();
    private ArrayList<GainerData_v2> glistsector = new ArrayList<>();
    private ArrayList<GainerData_v2> glistvolume = new ArrayList<>();
    private ArrayList<GainerData_v2> glistvalue = new ArrayList<>();
    private ArrayList<String> glistToken = new ArrayList<>();
    private ArrayList<String> glistloserToken = new ArrayList<>();
    private ArrayList<String> glistsectorToken = new ArrayList<>();
    private ArrayList<String> glistvolumeToken = new ArrayList<>();
    private ArrayList<String> glistvalueToken = new ArrayList<>();
    private SharedPreferences sp;
    private GreekTextView txt_nifty, txt_sensex, txt_high_nifty_name, txt_low_nifty_name, txt_high_sensex_name, txt_low_sensex_name;
    private GreekTextView niftyValuetxt, sensexValuetxt, niftychangetxt, sensexchangetxt, niftydayshightxt, sensexdayshightxtxt, niftydaylowtxt, sensexdaylowtxt, firstSymbolNametxt, secondSymboltxt, thirdSymbolnametxt, firstvalue, secondvalue, thirdvalue, firstchange, secondchange, thirdchange;
    private ImageView firstEditImg, scndEditImg, thrdEditImg, niftyEditImg, sensexEditImg;
    private RecyclerView expandableListView;
    private GreekTextView txt_first_symbol_name, txt_snd_symbol_name, txt_thrd_symbol_name, marekt_movers_text;
    private static String niftyexchange, sensexexchange, first_sym_exch, second_sym_exch, third_sym_exch;
    private StreamingController streamController = new StreamingController();
    public static final String TOP_GAINERS = "TOP GAINERS";
    public static final String TOP_LOSER = "TOP LOSERS";
    public static final String MARKET_MOVERS = "MARKET MOVERS";
    public static final String MOST_ACTIVE_BY_VOLUME = "MOST ACTIVE BY VOLUME";
    public static final String MOST_ACTIVE_BY_VALUE = "MOST ACTIVE BY VALUE";
    private MarketDataResponse marketResponse;
    private String currentType = "";

    private ArrayList topGainerStreamingList;
    private ArrayList topLoserStreamingList;
    private ArrayList MarketMoverStreamingList;
    private ArrayList MostActiveByVolumeStreamingList;
    private ArrayList MostActiveByValueStreamingList;
    private ArrayList handleIndicesResponseStreamingList;

    private ArrayList streamingList;
    private ArrayList streamingForIndexList;
    private OverViewRecycleAdapter adapter;
    private LinearLayout marekt_movers_spinn,
            firstBlockNifty, secondBlockSensex,
            linera1, linera2, linera3;
    private int textColorPositive, textColorNegative;
    private ArrayList<SubHeader_v2> subtopgainers, subtoplosers, submarketmove, submostactivevolume, submostactivevalue;
    private ArrayList<GainerMainHeader_v2> mainHeadersList;
    private String marketIDSelected = "1";
    private GreekTextView exchangetxt, advanceCountTXT, declineCountTXT;
    private ProgressBar progressBarAdvance;
    private MarketsIndicesStockResponse indicesStockResponse;
    private CategoryDropdownMenu stockIndexWindow;
    private String headingindicespopup = "";
    private LinearLayout second_layout, firstLayout, decline_Layout, Overview_layout;
    private SwipeRefreshLayout swipeRefresh;
    private Spinner market_statistic_spinner;
    private ArrayList<String> market_statistics;
    public static String txt_nifty_value, txt_sensex_value, txt_first_symbol_name_value, txt_snd_symbol_name_value, txt_thrd_symbol_name_value;
    public static String niftyValuetxtvalue, sensexValuetxtvalue, niftychangetxtvalue, sensexchangetxtvalue, niftydayshightxtvalue,
            sensexdayshightxtxtvalue, niftydaylowtxtvalue, sensexdaylowtxtvalue, firstSymbolNametxtvalue, secondSymboltxtvalue, thirdSymbolnametxtvalue, firstvaluevalue, secondvaluevalue, thirdvaluevalue, firstchangevalue, secondchangevalue, thirdchangevalue;
    static int minutes, seconds;
    static long starttime;
    public static boolean isFirstTime = true;

    public OverviewTabFragment() {

        market_statistics = new ArrayList<>();
        streamingList = new ArrayList<>();
        streamingForIndexList = new ArrayList<>();
        subtopgainers = new ArrayList<>();
        subtoplosers = new ArrayList<>();
        submarketmove = new ArrayList<>();
        submostactivevolume = new ArrayList<>();
        submostactivevalue = new ArrayList<>();
        mainHeadersList = new ArrayList<>();

        topGainerStreamingList = new ArrayList<>();
        topLoserStreamingList = new ArrayList<>();
        MarketMoverStreamingList = new ArrayList<>();
        MostActiveByVolumeStreamingList = new ArrayList<>();
        MostActiveByValueStreamingList = new ArrayList<>();
        handleIndicesResponseStreamingList = new ArrayList<>();
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
          // after pull down refresh again we load all this methods
            loadTopGainer("Top Gainers");
            loadTopLooser("Top Losers");
            loadMarketMovers("Most Active (Vol.)");
            loadMostActiveVolume("Most Active Volume");
            loadMostActiveValue("Most Active Value");

        }
    };


    @Override
    public void onFragmentResult(Object data) {
        if (data != null) {
            sp = PreferenceManager.getDefaultSharedPreferences(getMainActivity());
            SharedPreferences.Editor editor = sp.edit();
            if (((Bundle) data).getString("clickimg") != null) {
                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("nifty")) {
                    txt_nifty.setText(((Bundle) data).getString("symbol"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        niftyValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                        niftydayshightxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("high")))));
                        niftydaylowtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("low")))));
                    } else {
                        niftyValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                        niftydayshightxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("high")))));
                        niftydaylowtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("low")))));
                    }
                    niftychangetxt.setText(((Bundle) data).getString("change"));
                    niftyexchange = getExchangeFromToken(((Bundle) data).getString("token"));


                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex("0", ((Bundle) data).getString("symbol"));

                }
                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("sensex")) {
                    txt_sensex.setText(((Bundle) data).getString("symbol"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        sensexValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                        sensexdayshightxtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("high")))));
                        sensexdaylowtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("low")))));
                    } else {
                        sensexValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                        sensexdayshightxtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("high")))));
                        sensexdaylowtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("low")))));
                    }
                    sensexchangetxt.setText(((Bundle) data).getString("change"));
                    sensexexchange = getExchangeFromToken(((Bundle) data).getString("token"));

                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex("1", ((Bundle) data).getString("symbol"));

                }

                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("1")) {
                    firstSymbolNametxt.setText(((Bundle) data).getString("symbol"));
                    firstvalue.setText(((Bundle) data).getString("ltp"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        firstvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                    } else {
                        firstvalue.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }
                    firstchange.setText(((Bundle) data).getString("change"));
                    first_sym_exch = getExchangeFromToken(((Bundle) data).getString("token"));
                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex("2", ((Bundle) data).getString("symbol"));

                }
                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("2")) {
                    secondSymboltxt.setText(((Bundle) data).getString("symbol"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        secondvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                    } else {
                        secondvalue.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }
                    secondchange.setText(((Bundle) data).getString("change"));
                    second_sym_exch = getExchangeFromToken(((Bundle) data).getString("token"));
                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex("3", ((Bundle) data).getString("symbol"));

                }
                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("3")) {
                    thirdSymbolnametxt.setText(((Bundle) data).getString("symbol"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        thirdvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                    } else {
                        thirdvalue.setText(StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }
                    thirdchange.setText(((Bundle) data).getString("change"));
                    third_sym_exch = getExchangeFromToken(((Bundle) data).getString("token"));
                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex("4", ((Bundle) data).getString("symbol"));
                }

                txt_nifty_value = txt_nifty.getText().toString().trim();
                txt_sensex_value = txt_sensex.getText().toString().trim();
                txt_first_symbol_name_value = firstSymbolNametxt.getText().toString().trim();
                txt_snd_symbol_name_value = secondSymboltxt.getText().toString().trim();
                txt_thrd_symbol_name_value = thirdSymbolnametxt.getText().toString().trim();

                updateBorderLineColor();
                sendStreamingIndexRequest("index");

            }
        }


    }

    public void saveindex(String seqNo, String symbol) {
        String sipFreqUrl;
        showProgress();
        if (symbol.equalsIgnoreCase("nifty")) {
            symbol = "Nifty 50";
        }
/*        if(AccountDetails.getUsertype(getMainActivity()).equalsIgnoreCase("Open")) {
            sipFreqUrl = "addIndexForUserv2?index1=" + txt_nifty.getText().toString() + "&index2=" + txt_sensex.getText().toString() + "&index3=" + firstSymbolNametxt.getText().toString() + "&index4=" + secondSymboltxt.getText().toString() + "&index5=" + thirdSymbolnametxt.getText().toString() + "&gcid=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getToken(getMainActivity()) + "&assetType=equity";
        } else {
            sipFreqUrl = "addIndexForUserv2?index1=" + txt_nifty.getText().toString() + "&index2=" + txt_sensex.getText().toString() + "&index3=" + firstSymbolNametxt.getText().toString() + "&index4=" + secondSymboltxt.getText().toString() + "&index5=" + thirdSymbolnametxt.getText().toString() + "&gcid=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&assetType=equity";
        }*/
        //To save index senqno and symbol we call this request
        if (AccountDetails.getUsertype(getMainActivity()).equalsIgnoreCase("Open")) {
            sipFreqUrl = "addIndexSingleForUser?index=" + symbol + "&seqNo=" + seqNo + "&gcid=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getToken(getMainActivity()) + "&assetType=equity";
        } else {
            sipFreqUrl = "addIndexSingleForUser?index=" + symbol + "&seqNo=" + seqNo + "&gcid=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&assetType=equity";
        }

        WSHandler.getRequest(getMainActivity(), sipFreqUrl, new WSHandler.GreekResponseCallback() {

            @Override
            public void onSuccess(JSONObject response) {

                hideProgress();
                Log.e("OverviewTabScreen", "Response====>" + response);
                EventBus.getDefault().post("UpdateActionBarIndices");


            }

            @Override
            public void onFailure(String message) {
                EventBus.getDefault().post("UpdateActionBarIndices");
                hideProgress();
            }
        });
    }

    public void showOfflineLastData() {
        if (niftyValuetxtvalue!=null ) {
            if (niftyValuetxtvalue.equalsIgnoreCase("0.00") || sensexValuetxtvalue.equalsIgnoreCase("0.00")) {
                sendIndianIndicesRequest();
            } else {

                if (txt_nifty_value != null) {
                    txt_nifty.setText(txt_nifty_value);
                }

                if (txt_sensex_value != null) {
                    txt_sensex.setText(txt_sensex_value);
                }

                if (txt_first_symbol_name_value != null) {
                    firstSymbolNametxt.setText(txt_first_symbol_name_value);
                }

                if (txt_snd_symbol_name_value != null) {
                    secondSymboltxt.setText(txt_snd_symbol_name_value);
                }

                if (txt_thrd_symbol_name_value != null) {
                    thirdSymbolnametxt.setText(txt_thrd_symbol_name_value);
                }

                niftyValuetxt.setText(niftyValuetxtvalue);
                sensexValuetxt.setText(sensexValuetxtvalue);
                niftychangetxt.setText(niftychangetxtvalue);
                sensexchangetxt.setText(sensexchangetxtvalue);
                niftydayshightxt.setText(niftydayshightxtvalue);
                sensexdayshightxtxt.setText(sensexdayshightxtxtvalue);
                niftydaylowtxt.setText(niftydaylowtxtvalue);
                sensexdaylowtxt.setText(sensexdaylowtxtvalue);
                firstvalue.setText(firstvaluevalue);
                secondvalue.setText(secondvaluevalue);
                thirdvalue.setText(thirdvaluevalue);
                firstchange.setText(firstchangevalue);
                secondchange.setText(secondchangevalue);
                thirdchange.setText(thirdchangevalue);
                updateBorderLineColor();
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View overview = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_overview_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_overview_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        if (AccountDetails.allowedmarket_nse) {
            market_statistics.add("NSE");
        }

        if (AccountDetails.allowedmarket_bse) {
            market_statistics.add("BSE");
        }

        if (AccountDetails.allowedmarket_nse) {
            market_statistics.add("NSE FUTSTK");
            market_statistics.add("NSE OPTSTK");
        }

        streamingList = new ArrayList();
        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            textColorPositive = R.color.whitetheambuyColor;
        } else {
            textColorPositive = R.color.dark_green_positive;
        }
        textColorNegative = R.color.dark_red_negative;
        setupView(overview);
        if (isFirstTime == false) {
            if (minutes <= 0) {
                showOfflineLastData();
            } else {
                isFirstTime = true;
                sendIndianIndicesRequest();
            }

        } else {

            sendIndianIndicesRequest();
        }
        settheme();
        return overview;
    }

    private void settheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("White")) {
            progressBarAdvance.setProgressDrawable(getResources().getDrawable(R.drawable.whiteadvance_progress_drawable));
            exchangetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            marekt_movers_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_nifty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_sensex.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            niftychangetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            niftydaylowtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            niftydayshightxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            niftyValuetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sensexchangetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sensexdaylowtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sensexdayshightxtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sensexValuetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            firstSymbolNametxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            firstchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            firstvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            secondSymboltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            secondchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            secondvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            thirdSymbolnametxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            thirdchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            thirdvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_high_nifty_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_low_nifty_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_high_sensex_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_low_sensex_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            second_layout.setBackgroundColor(getResources().getColor(R.color.white));
            expandableListView.setBackgroundColor(getResources().getColor(R.color.white));
            firstLayout.setBackgroundColor(getResources().getColor(R.color.white));
            decline_Layout.setBackgroundColor(getResources().getColor(R.color.light_spinner));
            market_statistic_spinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            marekt_movers_spinn.setBackgroundColor(getResources().getColor(R.color.white));


            firstEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            scndEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            thrdEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            niftyEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            sensexEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
        }

    }

    private void updateBorderLineColor() {

        String value1 = firstchange.getText().toString();
        if (value1.startsWith("-")) {
            linera1.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            firstchange.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linera1.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            } else {
                linera1.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            firstchange.setTextColor(getResources().getColor(textColorPositive));

        }

        String value2 = secondchange.getText().toString();
        if (value2.startsWith("-")) {
            linera2.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            secondchange.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linera2.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            } else {
                linera2.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            secondchange.setTextColor(getResources().getColor(textColorPositive));
        }

        String value3 = thirdchange.getText().toString();
        if (value3.startsWith("-")) {
            linera3.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            thirdchange.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linera3.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            } else {
                linera3.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            thirdchange.setTextColor(getResources().getColor(textColorPositive));
        }

        String niftychange = niftychangetxt.getText().toString();
        if (niftychange.startsWith("-")) {
            firstBlockNifty.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            niftychangetxt.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                firstBlockNifty.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            } else {
                firstBlockNifty.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            niftychangetxt.setTextColor(getResources().getColor(textColorPositive));
        }

        String sensexchange = sensexchangetxt.getText().toString();

        if (sensexchange.startsWith("-")) {
            secondBlockSensex.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            sensexchangetxt.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                secondBlockSensex.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            } else {
                secondBlockSensex.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            sensexchangetxt.setTextColor(getResources().getColor(textColorPositive));
        }


    }

    private void setupView(View overview) {
        marekt_movers_text = overview.findViewById(R.id.marekt_movers_text);
        marekt_movers_spinn = overview.findViewById(R.id.marekt_movers_spinn);
        market_statistic_spinner = overview.findViewById(R.id.market_statistic_spinner);
        decline_Layout = overview.findViewById(R.id.decline_Layout);
        exchangetxt = overview.findViewById(R.id.exchangetxt);
        advanceCountTXT = overview.findViewById(R.id.advanceCountTXT);
        declineCountTXT = overview.findViewById(R.id.declineCountTXT);
        progressBarAdvance = overview.findViewById(R.id.netscripstotal);
        linera1 = overview.findViewById(R.id.linera1);
        linera2 = overview.findViewById(R.id.linera2);
        linera3 = overview.findViewById(R.id.linera3);
        firstBlockNifty = overview.findViewById(R.id.linearleft);
        secondBlockSensex = overview.findViewById(R.id.linearright);
        second_layout = overview.findViewById(R.id.second_layout);
        firstLayout = overview.findViewById(R.id.first_layout);
        txt_high_nifty_name = overview.findViewById(R.id.txt_day_high_name);
        txt_low_nifty_name = overview.findViewById(R.id.txt_day_low_name);
        txt_high_sensex_name = overview.findViewById(R.id.txt_day_high_name_sensex);
        txt_low_sensex_name = overview.findViewById(R.id.txt_day_low_name_sensex);
        Overview_layout = (LinearLayout) overview.findViewById(R.id.Overview_layout);
        swipeRefresh = overview.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);


        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        txt_nifty = overview.findViewById(R.id.txt_nifty);
        txt_sensex = overview.findViewById(R.id.txt_sensex);
        txt_first_symbol_name = overview.findViewById(R.id.txt_first_symbol_name);
        txt_snd_symbol_name = overview.findViewById(R.id.txt_snd_symbol_name);
        txt_thrd_symbol_name = overview.findViewById(R.id.txt_thrd_symbol_name);
        niftyValuetxt = overview.findViewById(R.id.txt_nifty_value);
        niftychangetxt = overview.findViewById(R.id.txt_nifty_change);
        niftydaylowtxt = overview.findViewById(R.id.txt_nifty_day_low);
        niftydayshightxt = overview.findViewById(R.id.txt_nifty_day_high);
        sensexValuetxt = overview.findViewById(R.id.txt_sensex_value);
        sensexchangetxt = overview.findViewById(R.id.txt_sensex_change);
        sensexdaylowtxt = overview.findViewById(R.id.txt_sensex_daylow);
        sensexdayshightxtxt = overview.findViewById(R.id.txt_sensex_day_high);
        firstSymbolNametxt = overview.findViewById(R.id.txt_first_symbol_name);
        firstchange = overview.findViewById(R.id.txt_first_change);
        firstvalue = overview.findViewById(R.id.txt_frst_value);
        secondSymboltxt = overview.findViewById(R.id.txt_snd_symbol_name);
        secondchange = overview.findViewById(R.id.txt_snd_change);
        secondvalue = overview.findViewById(R.id.txt_snd_value);
        thirdSymbolnametxt = overview.findViewById(R.id.txt_thrd_symbol_name);
        thirdvalue = overview.findViewById(R.id.txt_thrd_value);
        thirdchange = overview.findViewById(R.id.txt_thrd_change);

        niftyEditImg = overview.findViewById(R.id.img_nifty_edit);
        sensexEditImg = overview.findViewById(R.id.img_sensex_edit);
        firstEditImg = overview.findViewById(R.id.img_first_edit);
        scndEditImg = overview.findViewById(R.id.img_snd_edit);
        thrdEditImg = overview.findViewById(R.id.img_thrd_edit);

        niftyEditImg.setOnClickListener(this);
        sensexEditImg.setOnClickListener(this);
        firstEditImg.setOnClickListener(this);
        scndEditImg.setOnClickListener(this);
        thrdEditImg.setOnClickListener(this);

        expandableListView = overview.findViewById(R.id.overvie_detail_list);
        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MOST_ACTIVE_BY_VOLUME));
        mainHeadersList.add(new GainerMainHeader_v2(submostactivevalue, MOST_ACTIVE_BY_VALUE));
        adapter = new OverViewRecycleAdapter(getMainActivity(), mainHeadersList);
        expandableListView.setAdapter(adapter);
        expandableListView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        expandableListView.setHasFixedSize(true);
        ((SimpleItemAnimator) expandableListView.getItemAnimator()).setSupportsChangeAnimations(false);


        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");

        ArrayAdapter<String> expiryTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), market_statistics) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 5, 15, 5);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 8, 15, 8);
                return v;
            }
        };
        expiryTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        market_statistic_spinner.setAdapter(expiryTypeAdapter);
//        market_statistic_spinner.setSelection(0);
        market_statistic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (market_statistics.get(position).equalsIgnoreCase("NSE")) {
                    marketIDSelected = "1";
                } else if (market_statistics.get(position).equalsIgnoreCase("BSE")) {
                    marketIDSelected = "4";
                } else if (market_statistics.get(position).equalsIgnoreCase("NSE FUTSTK")) {
                    marketIDSelected = "21";

                } else if (market_statistics.get(position).equalsIgnoreCase("NSE OPTSTK")) {
                    marketIDSelected = "22";
                }

                Log.v("TopGainer Request",AccountDetails.isIsApolloConnected()+" "+ Util.getPrefs(getActivity()).getString("themeApplied", ""));
                String isthemeApplied = Util.getPrefs(getActivity()).getString("themeApplied", "");
                if(isthemeApplied.equalsIgnoreCase("true")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadTopGainer("Top Gainers");
                            loadTopLooser("Top Losers");
                            loadMostActiveVolume("Most Active Volume");
                            loadMostActiveValue("Most Active Value");
                        }
                    }, 500);
                }else {
                    loadTopGainer("Top Gainers");
                    loadTopLooser("Top Losers");
                    loadMostActiveVolume("Most Active Volume");
                    loadMostActiveValue("Most Active Value");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loadGetAdvanceDeclines();


        firstBlockNifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headingindicespopup = txt_nifty.getText().toString();

                showCategoryMenu(txt_nifty.getText().toString(), niftyexchange);

            }
        });


        secondBlockSensex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headingindicespopup = txt_sensex.getText().toString();
                showCategoryMenu(txt_sensex.getText().toString(), sensexexchange);

            }
        });

        linera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headingindicespopup = txt_first_symbol_name.getText().toString();
                showCategoryMenu(txt_first_symbol_name.getText().toString(), first_sym_exch);

            }
        });

        linera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headingindicespopup = txt_snd_symbol_name.getText().toString();
                showCategoryMenu(txt_snd_symbol_name.getText().toString(), second_sym_exch);

            }
        });

        linera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headingindicespopup = txt_thrd_symbol_name.getText().toString();
                showCategoryMenu(txt_thrd_symbol_name.getText().toString(), third_sym_exch);

            }
        });

    }

    private void showCategoryMenu(String title, String exchange) {
        showProgress();
        if (title.equalsIgnoreCase("Nifty")) {
            title = "Nifty 50";
        }
        MarketsIndicesStockRequest.sendRequest(exchange, title, getMainActivity(), serviceResponseHandler);
    }

    public void onEventMainThread(TopGainersModels topGainersModels) {
        Log.v("Onevent response", topGainersModels.toString());

        if (topGainersModels.getResponse().getMarketid().equalsIgnoreCase(marketIDSelected)) {

            topGainerStreamingList.clear();
            streamingList.clear();
            subtopgainers.clear();

            glistToken.clear();
            glist.clear();

            if (topGainersModels.getResponse().getData().size() < 10) {

                for (int i = 0; i < topGainersModels.getResponse().getData().size(); i++) {

                    GainerData_v2 gainerData;
                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                topGainersModels.getResponse().getData().get(i).getDescription(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topGainersModels.getResponse().getData().get(i).getToken()),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getChange(),
                                topGainersModels.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {

                        gainerData = new GainerData_v2(
                                topGainersModels.getResponse().getData().get(i).getSymbol(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topGainersModels.getResponse().getData().get(i).getToken()),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getChange(),
                                topGainersModels.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }


                    glist.add(gainerData);

                    topGainerStreamingList.add(gainerData.getToken());
                    glistToken.add(gainerData.getToken());

                }

            } else {
                for (int i = 0; i < 10; i++) {
                    GainerData_v2 gainerData;

                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                topGainersModels.getResponse().getData().get(i).getDescription(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topGainersModels.getResponse().getData().get(i).getToken()),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getChange(),
                                topGainersModels.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {

                        gainerData = new GainerData_v2(
                                topGainersModels.getResponse().getData().get(i).getSymbol(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topGainersModels.getResponse().getData().get(i).getToken()),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getChange(),
                                topGainersModels.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }

                    glist.add(gainerData);
                    topGainerStreamingList.add(gainerData.getToken());
                    glistToken.add(gainerData.getToken());


                }
            }

            if (glist.size() == 0) {
                GainerData_v2 gainerData = new GainerData_v2("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "");


                glist.add(gainerData);
            }

            addToStreamingList("ltpinfo", topGainerStreamingList);
            subtopgainers.add(new SubHeader_v2("", glist));
        }


        mainHeadersList.clear();
        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MOST_ACTIVE_BY_VOLUME));
        mainHeadersList.add(new GainerMainHeader_v2(submostactivevalue, MOST_ACTIVE_BY_VALUE));

        expandableListView.setAdapter(adapter);
        refreshComplete();


    }

    public void onEventMainThread(TopLosersModel topLosersModel) {
        Log.v("Onevent response", topLosersModel.toString());


        if (topLosersModel.getResponse().getMarketid().equalsIgnoreCase(marketIDSelected)) {

            topLoserStreamingList.clear();
            streamingList.clear();
            subtoplosers.clear();


            glistloserToken.clear();
            glistloser.clear();

            if (topLosersModel.getResponse().getData().size() < 10) {
                for (int i = 0; i < topLosersModel.getResponse().getData().size(); i++) {

                    GainerData_v2 gainerData;
                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                topLosersModel.getResponse().getData().get(i).getDescription(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topLosersModel.getResponse().getData().get(i).getToken()),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getChange(),
                                topLosersModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {
                        gainerData = new GainerData_v2(
                                topLosersModel.getResponse().getData().get(i).getSymbol(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topLosersModel.getResponse().getData().get(i).getToken()),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getChange(),
                                topLosersModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }


                    topLoserStreamingList.add(gainerData.getToken());
                    glistloser.add(gainerData);
                    glistloserToken.add(gainerData.getToken());
                    streamingList.add(gainerData.getToken());

                }

            } else {
                for (int i = 0; i < 10; i++) {

                    GainerData_v2 gainerData;
                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                topLosersModel.getResponse().getData().get(i).getDescription(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topLosersModel.getResponse().getData().get(i).getToken()),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getChange(),
                                topLosersModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {
                        gainerData = new GainerData_v2(
                                topLosersModel.getResponse().getData().get(i).getSymbol(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(topLosersModel.getResponse().getData().get(i).getToken()),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getChange(),
                                topLosersModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }

                    topLoserStreamingList.add(gainerData.getToken());
                    glistloser.add(gainerData);
                    glistloserToken.add(gainerData.getToken());


                }
            }

            if (glistloser.size() == 0) {
                GainerData_v2 gainerData = new GainerData_v2("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "");


                glistloser.add(gainerData);
            }
            addToStreamingList("ltpinfo", topLoserStreamingList);
            subtoplosers.add(new SubHeader_v2("", glistloser));


        }

        // sendStreamingRequest("ltpiNSE");
        mainHeadersList.clear();
        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MOST_ACTIVE_BY_VOLUME));
        mainHeadersList.add(new GainerMainHeader_v2(submostactivevalue, MOST_ACTIVE_BY_VALUE));

        expandableListView.setAdapter(adapter);

        refreshComplete();

    }

    public void onEventMainThread(MarketMoversModel marketMoversModel) {
        Log.v("Onevent response", marketMoversModel.toString());

        if (marketMoversModel.getResponse().getMarketid().equalsIgnoreCase(marketIDSelected)) {

            MarketMoverStreamingList.clear();
            streamingList.clear();
            submarketmove.clear();

            glistsectorToken.clear();
            glistsector.clear();

            if (marketMoversModel.getResponse().getData().size() < 10) {
                for (int i = 0; i < marketMoversModel.getResponse().getData().size(); i++) {

                    GainerData_v2 gainerData;
                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                marketMoversModel.getResponse().getData().get(i).getDescription(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversModel.getResponse().getData().get(i).getToken()),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getChange(),
                                marketMoversModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {
                        gainerData = new GainerData_v2(
                                marketMoversModel.getResponse().getData().get(i).getSymbol(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversModel.getResponse().getData().get(i).getToken()),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getChange(),
                                marketMoversModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }

                    MarketMoverStreamingList.add(gainerData.getToken());
                    glistsector.add(gainerData);
                    glistsectorToken.add(gainerData.getToken());
                    streamingList.add(gainerData.getToken());

                }

            } else {
                for (int i = 0; i < 10; i++) {

                    GainerData_v2 gainerData;

                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                marketMoversModel.getResponse().getData().get(i).getDescription(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversModel.getResponse().getData().get(i).getToken()),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getChange(),
                                marketMoversModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {
                        gainerData = new GainerData_v2(
                                marketMoversModel.getResponse().getData().get(i).getSymbol(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversModel.getResponse().getData().get(i).getToken()),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getChange(),
                                marketMoversModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }

                    MarketMoverStreamingList.add(gainerData.getToken());
                    glistsector.add(gainerData);
                    glistsectorToken.add(gainerData.getToken());
                    streamingList.add(gainerData.getToken());


                }

            }

            if (glistsector.size() == 0) {
                GainerData_v2 gainerData = new GainerData_v2("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "");


                glistsector.add(gainerData);
            }
            addToStreamingList("ltpinfo", MarketMoverStreamingList);
            submarketmove.add(new SubHeader_v2("", glistsector));

        }

        // sendStreamingRequest("ltpiNSE");
        mainHeadersList.clear();
        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MOST_ACTIVE_BY_VOLUME));
        mainHeadersList.add(new GainerMainHeader_v2(submostactivevalue, MOST_ACTIVE_BY_VALUE));

        expandableListView.setAdapter(adapter);
        refreshComplete();
    }

    public void onEventMainThread(MarketMoversByValueModel marketMoversByValueModel) {
                Log.e("Marketbyvalue",""+marketMoversByValueModel.getResponse());
        if (marketMoversByValueModel.getResponse().getMarketid().equalsIgnoreCase(marketIDSelected)) {

            MostActiveByValueStreamingList.clear();
            streamingList.clear();
            submostactivevalue.clear();

            glistvalueToken.clear();
            glistvalue.clear();

            if (marketMoversByValueModel.getResponse().getData().size() < 10) {

                for (int i = 0; i < marketMoversByValueModel.getResponse().getData().size(); i++) {

                    GainerData_v2 gainerData;

                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                marketMoversByValueModel.getResponse().getData().get(i).getDescription(),
                                marketMoversByValueModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversByValueModel.getResponse().getData().get(i).getToken()),
                                marketMoversByValueModel.getResponse().getData().get(i).getLtp(),
                                marketMoversByValueModel.getResponse().getData().get(i).getChange(),
                                marketMoversByValueModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {
                        gainerData = new GainerData_v2(
                                marketMoversByValueModel.getResponse().getData().get(i).getSymbol(),
                                marketMoversByValueModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversByValueModel.getResponse().getData().get(i).getToken()),
                                marketMoversByValueModel.getResponse().getData().get(i).getLtp(),
                                marketMoversByValueModel.getResponse().getData().get(i).getChange(),
                                marketMoversByValueModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }

                    MostActiveByValueStreamingList.add(gainerData.getToken());
                    glistvalue.add(gainerData);
                    glistvalueToken.add(gainerData.getToken());
                    streamingList.add(gainerData.getToken());
                }

            } else {
                for (int i = 0; i < 10; i++) {

                    GainerData_v2 gainerData;

                    if (marketIDSelected.equalsIgnoreCase("21") || marketIDSelected.equalsIgnoreCase("22")) {
                        gainerData = new GainerData_v2(
                                marketMoversByValueModel.getResponse().getData().get(i).getDescription(),
                                marketMoversByValueModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversByValueModel.getResponse().getData().get(i).getToken()),
                                marketMoversByValueModel.getResponse().getData().get(i).getLtp(),
                                marketMoversByValueModel.getResponse().getData().get(i).getChange(),
                                marketMoversByValueModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    } else {
                        gainerData = new GainerData_v2(
                                marketMoversByValueModel.getResponse().getData().get(i).getSymbol(),
                                marketMoversByValueModel.getResponse().getData().get(i).getToken(),
                                getExchangeFromToken(marketMoversByValueModel.getResponse().getData().get(i).getToken()),
                                marketMoversByValueModel.getResponse().getData().get(i).getLtp(),
                                marketMoversByValueModel.getResponse().getData().get(i).getChange(),
                                marketMoversByValueModel.getResponse().getData().get(i).getPerchange(),
                                "NSE"
                        );
                    }

                    MostActiveByValueStreamingList.add(gainerData.getToken());
                    glistvalue.add(gainerData);
                    glistvalueToken.add(gainerData.getToken());
                    streamingList.add(gainerData.getToken());
                }

            }

            if (glistvalue.size() == 0) {
                GainerData_v2 gainerData = new GainerData_v2("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "");

                glistvalue.add(gainerData);
            }
            addToStreamingList("ltpinfo", MostActiveByValueStreamingList);
            submostactivevalue.add(new SubHeader_v2("", glistvalue));
        }

        mainHeadersList.clear();
        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MOST_ACTIVE_BY_VOLUME));
        mainHeadersList.add(new GainerMainHeader_v2(submostactivevalue, MOST_ACTIVE_BY_VALUE));
        expandableListView.setAdapter(adapter);
        refreshComplete();
    }


    private void loadGetAdvanceDeclines() {
        showProgress();
        WSHandler.getRequest(getMainActivity(), "getAdvanceDeclines", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("POSecurityFragment", "" + response);

                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {

                        String Advances = respCategory.getJSONObject(i).getString("Advances");
                        String Declines = respCategory.getJSONObject(i).getString("Declines");
                        String Total = respCategory.getJSONObject(i).getString("Total");

                        progressBarAdvance.setMax((int) Double.parseDouble(Total));
                        progressBarAdvance.setProgress((int) Double.parseDouble(Advances));

                        advanceCountTXT.setText(Advances);
                        declineCountTXT.setText(Declines);

                        if (Advances.equalsIgnoreCase("0") && Declines.equalsIgnoreCase("0")) {
                            advanceCountTXT.setTextColor(getMainActivity().getResources().getColor(R.color.white));
                            declineCountTXT.setTextColor(getMainActivity().getResources().getColor(R.color.sellColor));
                            progressBarAdvance.setProgressDrawable(getMainActivity().getResources().getDrawable(R.drawable.white_trading_button_effect));
                        } else {
                            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                advanceCountTXT.setTextColor(getMainActivity().getResources().getColor(R.color.whitetheambuyColor));
                            } else {
                                advanceCountTXT.setTextColor(getMainActivity().getResources().getColor(R.color.dark_green_positive));
                            }
                        }
                    }
                    hideProgress();
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgress();
                    AccountDetails.setiSCompanySummaryAvailbale(false);
                }
            }

            @Override
            public void onFailure(String message) {
                //hideProgress();
                AccountDetails.setiSCompanySummaryAvailbale(false);
            }
        });
    }

    private void loadTopGainer(String top_gainers) {
        Log.e("timer is ", minutes + " Minute " + seconds + " seconds");

        //If apollo is connected then only we request to apollo
        if(AccountDetails.isIsApolloConnected()) {
            streamController.sendTOPgainersRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                    AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
        }else{
            refreshComplete();
        }

        /*if(AccountDetails.isNetworkConnected && AccountDetails.isIsApolloConnected()) {
            streamController.sendTOPgainersRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                    AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
        } else {
            if(AccountDetails.getApollo_LoginCounter() == 0) {
                AccountDetails.setApollo_LoginCounter(1);
            }else {
                EventBus.getDefault().post("Socket Apollo Reconnect Attempts exceeds");
            }
        }*/
    
      /*  String exchangeStr;

        boolean pre_NSE_checked = Util.getPrefs(getMainActivity()).getBoolean("MARKET_MOVERS_NSE_CHECKED", false);

        if(pre_NSE_checked) {
            exchangeStr = "NSE";

        } else {
            exchangeStr = "NSE";

        }
        exchangeStr = exchangeStr.toLowerCase();

        final String filtersStr = top_gainers;
        final String type;
        String service;
        String serviceURL;

        switch (filtersStr) {
            case "Top Gainers":
                type = "gainer";
                service = "getTopEQGainerLoser_V2";
                serviceURL = service + "?exchange=" + exchangeStr + "&type=" + type;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopEQGainerLoser_Mobile";
                serviceURL = service + "?exchange=" + exchangeStr + "&type=" + type;
                break;
            default:
                return;
        }

        currentType = type;
        if(serviceURL.length() > 0) {

            showProgress();


            marketResponse = null;


            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {


                    try {
                        streamingList.clear();
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);
                        subtopgainers.clear();

                        if(type.equalsIgnoreCase("gainer")) {

                            glistToken.clear();
                            glist.clear();

                            if(marketResponse.getMarketDataModelList().size() < 10) {
                                for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
                                    GainerData_v2 gainerData = new GainerData_v2(
                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
                                            marketResponse.getMarketDataModelList().get(i).getToken(),
                                            getExchangeFromToken(marketResponse.getMarketDataModelList().get(i).getToken()),
                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
                                            marketResponse.getMarketDataModelList().get(i).getChange(),
                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
                                            marketResponse.getMarketDataModelList().get(i).getAssetType()
                                    );

                                    glist.add(gainerData);

                                    glistToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());


                                }

                            } else {
                                for (int i = 0; i < 10; i++) {
                                    GainerData_v2 gainerData = new GainerData_v2(
                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
                                            marketResponse.getMarketDataModelList().get(i).getToken(),
                                            getExchangeFromToken(marketResponse.getMarketDataModelList().get(i).getToken()),
                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
                                            marketResponse.getMarketDataModelList().get(i).getChange(),
                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
                                            marketResponse.getMarketDataModelList().get(i).getAssetType()
                                    );

                                    glist.add(gainerData);
//                                    subtopgainers.add(new SubHeader("OPTION_CHAIN", glist));

                                    glistToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());


                                }
                            }

                            subtopgainers.add(new SubHeader_v2("", glist));

                        }


//                        sendStreamingRequest("ltpiNSE");

                        mainHeadersList.clear();
                        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
                        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
                        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MARKET_MOVERS));

                        expandableListView.setAdapter(adapter);

                    } catch (JSONException e) {
                        hideProgress();
//                        acumengroupgleErrorLayout(true);
                        e.printStackTrace();
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(String message) {
                    hideProgress();
                    subtopgainers.clear();
                    glist.clear();
                    GainerData_v2 gainerData = new GainerData_v2("",
                            "",
                            "",
                            "",
                            "",
                            "", "");

                    glist.add(gainerData);
                    subtopgainers.add(new SubHeader_v2("", glist));
                    mainHeadersList.clear();
                    mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
                    mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
                    mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MARKET_MOVERS));
                }
            });


        }*/
    }

    private void loadTopLooser(String top_losers) {
        //If apollo is connected then only we request to apollo
        if(AccountDetails.isIsApolloConnected()) {
            streamController.sendTOPlosersRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                    AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
        }else{
            refreshComplete();
        }

      /*  String exchangeStr;

        boolean pre_NSE_checked = Util.getPrefs(getMainActivity()).getBoolean("MARKET_MOVERS_NSE_CHECKED", false);

        if(pre_NSE_checked) {
            exchangeStr = "NSE";

        } else {
            exchangeStr = "NSE";

        }
        exchangeStr = exchangeStr.toLowerCase();

        final String filtersStr = top_losers;
        final String type;
        String service;
        String serviceURL;

        switch (filtersStr) {
            case "Top Gainers":
                type = "gainer";
                service = "getTopEQGainerLoser_V2";
                serviceURL = service + "?exchange=" + exchangeStr + "&type=" + type;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopEQGainerLoser_V2";
                serviceURL = service + "?exchange=" + exchangeStr + "&type=" + type;
                break;
            default:
                return;
        }

        currentType = type;
        if(serviceURL.length() > 0) {

           showProgress();
            marketResponse = null;


            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    try {
//                        streamingList.clear();
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);

                        subtoplosers.clear();
                        if(type.equalsIgnoreCase("loser")) {
                            glistloserToken.clear();
                            glistloser.clear();

                            if(marketResponse.getMarketDataModelList().size() < 10) {

                                for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {

                                    GainerData_v2 gainerData = new GainerData_v2(
                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
                                            marketResponse.getMarketDataModelList().get(i).getToken(),
                                            getExchangeFromToken(marketResponse.getMarketDataModelList().get(i).getToken()),
                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
                                            marketResponse.getMarketDataModelList().get(i).getChange(),
                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
                                            marketResponse.getMarketDataModelList().get(i).getAssetType()
                                    );
                                    glistloser.add(gainerData);

                                    glistloserToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());

                                }

                            } else {

                                for (int i = 0; i < 10; i++) {
                                    GainerData_v2 gainerData = new GainerData_v2(marketResponse.getMarketDataModelList().get(i).getSymbol(),
                                            marketResponse.getMarketDataModelList().get(i).getToken(),
                                            getExchangeFromToken(marketResponse.getMarketDataModelList().get(i).getToken()),
                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
                                            marketResponse.getMarketDataModelList().get(i).getChange(),
                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
                                            marketResponse.getMarketDataModelList().get(i).getPerChange()
                                    );
                                    glistloser.add(gainerData);

                                    glistloserToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());

                                }


                            }
                            subtoplosers.add(new SubHeader_v2("", glistloser));
                        }

//                        sendStreamingRequest("ltpiNSE");

                        mainHeadersList.clear();
                        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
                        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
                        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MARKET_MOVERS));

                        expandableListView.setAdapter(adapter);

                    } catch (JSONException e) {
                        hideProgress();
//                        acumengroupgleErrorLayout(true);
                        e.printStackTrace();
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(String message) {
                    hideProgress();

                    glistloser.clear();
                    subtoplosers.clear();
                    GainerData_v2 gainerData = new GainerData_v2("",
                            "",
                            "",
                            "",
                            "",
                            "", "");

                    glistloser.add(gainerData);
                    subtoplosers.add(new SubHeader_v2("", glistloser));
                    mainHeadersList.clear();
                    mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
                    mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
                    mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MARKET_MOVERS));
                }
            });
        }*/
    }

    private void loadMarketMovers(String movers) {
//If apollo is connected then only we request to apollo
        if(AccountDetails.isIsApolloConnected()) {
            streamController.sendMarketmoverRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                    AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
        }else{
            refreshComplete();
        }

        /*String exchangeStr;

        boolean pre_NSE_checked = Util.getPrefs(getMainActivity()).getBoolean("MARKET_MOVERS_NSE_CHECKED", false);

        if(pre_NSE_checked) {
            exchangeStr = "NSE";

        } else {
            exchangeStr = "NSE";

        }
        exchangeStr = exchangeStr.toLowerCase();

        final String filtersStr = movers;
        final String type;
        String service;
        String serviceURL; //9167870673

        switch (filtersStr) {
            case "Most Active (Vol.)":
                type = "volume";
                service = "getMostActiveEquityByVolume_Mobile_V2";
                serviceURL = service + "?exchange=" + exchangeStr;
                break;
            default:
                return;
        }

        currentType = type;
        if(serviceURL.length() > 0) {

            showProgress();
            marketResponse = null;
            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {


                    try {
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);

                        submarketmove.clear();
                        if(type.equalsIgnoreCase("volume")) {
                            glistsectorToken.clear();
                            glistsector.clear();

                            if(marketResponse.getMarketDataModelList().size() < 10) {
                                for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
                                    GainerData_v2 gainerData = new GainerData_v2(
                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
                                            marketResponse.getMarketDataModelList().get(i).getToken(),
                                            getExchangeFromToken(marketResponse.getMarketDataModelList().get(i).getToken()),
                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
                                            marketResponse.getMarketDataModelList().get(i).getChange(),
                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
                                            marketResponse.getMarketDataModelList().get(i).getAssetType()
                                    );
                                    glistsector.add(gainerData);

                                    glistsectorToken.add(gainerData.getToken());

                                }

                            } else {

                                for (int i = 0; i < 10; i++) {
                                    GainerData_v2 gainerData = new GainerData_v2(
                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
                                            marketResponse.getMarketDataModelList().get(i).getToken(),
                                            getExchangeFromToken(marketResponse.getMarketDataModelList().get(i).getToken()),
                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
                                            marketResponse.getMarketDataModelList().get(i).getChange(),
                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
                                            marketResponse.getMarketDataModelList().get(i).getAssetType()
                                    );
                                    glistsector.add(gainerData);

                                    glistsectorToken.add(gainerData.getToken());
                                }

                            }

                            submarketmove.add(new SubHeader_v2("", glistsector));

                        }

                        mainHeadersList.clear();
                        mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
                        mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
                        mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MARKET_MOVERS));

                        expandableListView.setAdapter(adapter);

                    } catch (JSONException e) {
                        hideProgress();
                        e.printStackTrace();
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(String message) {
                    hideProgress();
                    submarketmove.clear();
                    glistsector.clear();
                    GainerData_v2 gainerData = new GainerData_v2("",
                            "",
                            "",
                            "",
                            "",
                            "", "");

                    glistsector.add(gainerData);
                    submarketmove.add(new SubHeader_v2("", glistsector));

                    mainHeadersList.clear();
                    mainHeadersList.add(new GainerMainHeader_v2(subtopgainers, TOP_GAINERS));
                    mainHeadersList.add(new GainerMainHeader_v2(subtoplosers, TOP_LOSER));
                    mainHeadersList.add(new GainerMainHeader_v2(submarketmove, MARKET_MOVERS));

                }
            });
        }

        */
    }

    private void loadMostActiveVolume(String Most_active_volume) {
        //If apollo is connected then only we request to apollo
        if(AccountDetails.isIsApolloConnected()) {
            streamController.sendMostActiveVolumeRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                    AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
        }else{
            refreshComplete();
        }

    }

    private void loadMostActiveValue(String Most_active_value) {
        //If apollo is connected then only we request to apollo
        if(AccountDetails.isIsApolloConnected()) {
            streamController.sendMostActiveValueRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                    AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
        }else{
            refreshComplete();
        }
    }

    private void sendIndianIndicesRequest() {
        if (isFirstTime) {
            isFirstTime = false;
            Overview_layout.setVisibility(View.GONE);
        }
        showProgress();
        streamController.sendIndianIndicesRequesForCommodityCurrency(getMainActivity(), "equity", serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;

        MarketsIndianIndicesResponse indianIndicesResponse;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                hideProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "getIndianIndicesDataForUserV2".equals(jsonResponse.getServiceName())) {
            try {
                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                SharedPreferences getsp = PreferenceManager.getDefaultSharedPreferences(getMainActivity());
                sp = PreferenceManager.getDefaultSharedPreferences(getMainActivity());
                SharedPreferences.Editor editor = sp.edit();

                MarketsIndianIndicesResponse tempResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                ArrayList<Integer> indicesIndex = new ArrayList<>();
                ArrayList<IndianIndice> IndianIndiceList = new ArrayList<>();

                //TODO: Change logic for display Indices data by rohit. Now display as per Sequence number coming in response
                for (int i = 0; i < indianIndicesResponse.getIndianIndices().size(); i++) {

                    IndianIndiceList.add(indianIndicesResponse.getIndianIndices().get(i));
                    if (indianIndicesResponse.getIndianIndices().get(i).getToken().equalsIgnoreCase("Nifty 50")) {
                        indianIndicesResponse.getIndianIndices().get(i).setToken("Nifty");
                    }

                    if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("0")) {
                        niftyexchange = indianIndicesResponse.getIndianIndices().get(i).getExchange();
                        txt_nifty.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());

                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            niftyValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))));
                            niftychangetxt.setText(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                            niftydayshightxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getHigh()))));
                            niftydaylowtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLow()))));

                        } else {
                            niftyValuetxt.setText(StringStuff.commaDecorator(indianIndicesResponse.getIndianIndices().get(i).getLtp()));
                            niftychangetxt.setText(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                            niftydayshightxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getHigh()))));
                            niftydaylowtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLow()))));
                        }
                        streamingForIndexList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());
                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("1")) {

                        sensexexchange = indianIndicesResponse.getIndianIndices().get(i).getExchange();
                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            txt_sensex.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());
                            sensexValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))));


                            sensexchangetxt.setText(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                            sensexdayshightxtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getHigh()))));
                            sensexdaylowtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLow()))));

                        } else {

                            txt_sensex.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());
                            sensexValuetxt.setText(StringStuff.commaINRDecorator(indianIndicesResponse.getIndianIndices().get(i).getLtp()));
                            sensexchangetxt.setText(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                            sensexdayshightxtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getHigh()))));
                            sensexdaylowtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLow()))));

                        }
                        streamingForIndexList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());
                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("2")) {
                        first_sym_exch = indianIndicesResponse.getIndianIndices().get(i).getExchange();
                        firstSymbolNametxt.setText(IndianIndiceList.get(i).getToken());

                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            firstvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getLtp()))));
                            firstchange.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        } else {

                            firstvalue.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble((IndianIndiceList.get(i).getLtp())))));
                            firstchange.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        }

                        streamingForIndexList.add(IndianIndiceList.get(i).getIndexCode());

                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("3")) {
                        second_sym_exch = indianIndicesResponse.getIndianIndices().get(i).getExchange();
                        secondSymboltxt.setText(IndianIndiceList.get(i).getToken());

                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            secondvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getLtp()))));
                            secondchange.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        } else {

                            secondvalue.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getLtp()))));
                            secondchange.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        }

                        streamingForIndexList.add(IndianIndiceList.get(i).getIndexCode());


                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("4")) {
                        third_sym_exch = indianIndicesResponse.getIndianIndices().get(i).getExchange();
                        thirdSymbolnametxt.setText(IndianIndiceList.get(i).getToken());
                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            thirdvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getLtp()))));
                            thirdchange.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");
                        } else {
                            thirdvalue.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getLtp()))));
                            thirdchange.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");
                        }
                        streamingForIndexList.add(IndianIndiceList.get(i).getIndexCode());
                    }
                }

                txt_nifty_value = txt_nifty.getText().toString().trim();
                txt_sensex_value = txt_sensex.getText().toString().trim();
                txt_first_symbol_name_value = firstSymbolNametxt.getText().toString().trim();
                txt_snd_symbol_name_value = secondSymboltxt.getText().toString().trim();
                txt_thrd_symbol_name_value = thirdSymbolnametxt.getText().toString().trim();
                niftyValuetxtvalue = niftyValuetxt.getText().toString().trim();
                sensexValuetxtvalue = sensexValuetxt.getText().toString().trim();
                niftychangetxtvalue = niftychangetxt.getText().toString().trim();
                sensexchangetxtvalue = sensexchangetxt.getText().toString().trim();
                niftydayshightxtvalue = niftydayshightxt.getText().toString().trim();
                sensexdayshightxtxtvalue = sensexdayshightxtxt.getText().toString().trim();
                niftydaylowtxtvalue = niftydaylowtxt.getText().toString().trim();
                sensexdaylowtxtvalue = sensexdaylowtxt.getText().toString().trim();
                firstSymbolNametxtvalue = firstSymbolNametxt.getText().toString().trim();
                secondSymboltxtvalue = secondSymboltxt.getText().toString().trim();
                thirdSymbolnametxtvalue = thirdSymbolnametxt.getText().toString().trim();
                firstvaluevalue = firstvalue.getText().toString().trim();
                secondvaluevalue = secondvalue.getText().toString().trim();
                thirdvaluevalue = thirdvalue.getText().toString().trim();
                firstchangevalue = firstchange.getText().toString().trim();
                secondchangevalue = secondchange.getText().toString().trim();
                thirdchangevalue = thirdchange.getText().toString().trim();

                updateBorderLineColor();
                sendStreamingIndexRequest("index");
                hideProgress();
                Overview_layout.setVisibility(View.VISIBLE);
                // Toast.makeText(getActivity(),"show UI",Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_SVC_NAME_NEW.equals(jsonResponse.getServiceName())) {
        }
        else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_STOCKS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                indicesStockResponse = (MarketsIndicesStockResponse) jsonResponse.getResponse();

                hideProgress();
                handleIndicesResponse();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public void onEventMainThread(String message) {

        if (message.equalsIgnoreCase("placeorder")) {

            if (stockIndexWindow != null) {
                stockIndexWindow.dismiss();
            }
        }

  /*      if (message.equalsIgnoreCase("Gainersopen") ||
                message.equalsIgnoreCase("losersopen") ||
                message.equalsIgnoreCase("Marketopen")) {

            if (subtopgainers.size() == 0 || subtoplosers.size() == 0 || submarketmove.size() == 0) {
                streamController.sendTOPgainersRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                        AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
                streamController.sendTOPlosersRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                        AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
                streamController.sendMarketmoverRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                        AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
            }
        }*/


    }

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void handleIndicesResponse() {
        List<IndicesStock> indicesStocks = indicesStockResponse.getIndicesStock();
        stockIndexWindow = new CategoryDropdownMenu(getMainActivity(), headingindicespopup + " Stocks", indicesStocks);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getMainActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        stockIndexWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < indicesStocks.size(); i++) {
            handleIndicesResponseStreamingList.add(indicesStocks.get(i).getToken());
        }
        stockIndexWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        stockIndexWindow.setOutsideTouchable(true);
        stockIndexWindow.setFocusable(true);

        if (headingindicespopup.equalsIgnoreCase(txt_nifty.getText().toString())) {
            stockIndexWindow.showAsDropDown(firstBlockNifty);
        } else if (headingindicespopup.equalsIgnoreCase(txt_sensex.getText().toString())) {
            stockIndexWindow.showAsDropDown(secondBlockSensex);
        } else if (headingindicespopup.equalsIgnoreCase(txt_first_symbol_name.getText().toString())) {
            stockIndexWindow.showAsDropDown(linera1);
        } else if (headingindicespopup.equalsIgnoreCase(txt_snd_symbol_name.getText().toString())) {
            stockIndexWindow.showAsDropDown(linera2);
        } else if (headingindicespopup.equalsIgnoreCase(txt_thrd_symbol_name.getText().toString())) {
            stockIndexWindow.showAsDropDown(linera3);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_nifty_edit) {

            Bundle args = new Bundle();
            args.putString("imageid", "Nifty");
            args.putString("assetType", "equity");

            navigateTo(NAV_TO_MARKET_EDIT_FRAGMENT, args, true);

        }
        if (v.getId() == R.id.img_sensex_edit) {

            Bundle args = new Bundle();
            args.putString("imageid", "sensex");
            args.putString("assetType", "equity");

            navigateTo(NAV_TO_MARKET_EDIT_FRAGMENT, args, true);

        }
        if (v.getId() == R.id.img_first_edit) {

            Bundle args = new Bundle();
            args.putString("imageid", "1");
            args.putString("assetType", "equity");

            navigateTo(NAV_TO_MARKET_EDIT_FRAGMENT, args, true);

        }
        if (v.getId() == R.id.img_snd_edit) {
            Bundle args = new Bundle();
            args.putString("imageid", "2");
            args.putString("assetType", "equity");

            navigateTo(NAV_TO_MARKET_EDIT_FRAGMENT, args, true);
        }
        if (v.getId() == R.id.img_thrd_edit) {
            Bundle args = new Bundle();
            args.putString("imageid", "3");
            args.putString("assetType", "equity");

            navigateTo(NAV_TO_MARKET_EDIT_FRAGMENT, args, true);


        }
    }


    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        EventBus.getDefault().register(this);
        sendStreamingIndexRequest("index");
        //AccountDetails.currentFragment= NAV_TO_OVERVIEWSCREEN;
    }

    private Handler mTimerHandler = new Handler();
   /* private void startTimer(){
      Timer  mTimer1 = new Timer();
        TimerTask mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        //TODO
                        long millis = System.currentTimeMillis() - 0;
                        int seconds = (int) (millis / 1000);
                         minutes = seconds / 60;
                        seconds     = seconds % 60;

                    }
                });
            }
        };

        mTimer1.schedule(mTt1, 1, 0);
    }*/

    Handler h2 = new Handler();
    Runnable run = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - starttime;
            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            h2.postDelayed(this, 1);
        }
    };

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
//        sendPauseStreamingRequest();
        if (stockIndexWindow != null && stockIndexWindow.isShowing()) {
            stockIndexWindow.dismiss();
        }


        super.onFragmentPause();
    }

    @Override
    public void onResume() {
        h2.removeCallbacks(run);
        EventBus.getDefault().register(this);
        final Handler handler = new Handler(Looper.getMainLooper());
        Log.e("GreekBaseActivity", "OnResume======OverviewTab==================>>>>>>>>>>>>>");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 1000ms
                sendStreamingIndexRequest("index");
                if (topGainerStreamingList != null && topGainerStreamingList.size() > 0) {
                    sendStreamingRequest(topGainerStreamingList);
                }
                if (topLoserStreamingList != null && topLoserStreamingList.size() > 0) {
                    sendStreamingRequest(topLoserStreamingList);
                }
                if (MarketMoverStreamingList != null && MarketMoverStreamingList.size() > 0) {
                    sendStreamingRequest(MarketMoverStreamingList);
                }
                if (MostActiveByValueStreamingList != null && MostActiveByValueStreamingList.size() > 0) {
                    sendStreamingRequest(MostActiveByValueStreamingList);
                }
                if (handleIndicesResponseStreamingList != null && handleIndicesResponseStreamingList.size() > 0) {
                    if (stockIndexWindow.isShowing()) {
                        sendStreamingRequest(handleIndicesResponseStreamingList);
                    }
                }
            }
        }, 1000);

        super.onResume();

    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            GreekDialog.dismissDialog();
            if (stockIndexWindow != null) {
                stockIndexWindow.dismiss();
            }
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
//        sendPauseStreamingRequest();
        starttime = System.currentTimeMillis();
        h2.postDelayed(run, 0);
        super.onPause();

    }

    private void sendStreamingRequest(ArrayList<String> streaming) {


        if (streaming != null) {
            if (streaming.size() > 0) {
                streamController.sendStreamingRequest(getMainActivity(), streaming, "ltpinfo", null, null, false);
                addToStreamingList("ltpinfo", streaming);
            }
        }
    }

    private void sendPauseStreamingRequest() {

        if (streamingList != null) {
            if (streamingList.size() > 0) {
                streamController.pauseStreaming(getMainActivity(), "ltpinfo", streamingList);
            }
        }

        if (streamingForIndexList != null) {
            if (streamingForIndexList.size() > 0) {
                streamController.pauseStreaming(getMainActivity(), "index", streamingForIndexList);
            }
        }
    }

    private void sendStreamingIndexRequest(String type) {

       /* if (streamingForIndexList != null) {
            if (streamingForIndexList.size() > 0) {
                streamController.pauseStreaming(getMainActivity(), type, streamingForIndexList);
            }
        }*/
        if (streamingForIndexList.size() > 0) {
            if (AccountDetails.isIsApolloConnected()) {
                streamController.sendStreamingRequest(getMainActivity(), streamingForIndexList, type, null, null, false);
            } else {
                EventBus.getDefault().post("Socket Apollo Reconnect Attempts exceeds");
            }
            addToStreamingList(type, streamingForIndexList);
        }
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("index")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());

                String indexBrdName = broadcastResponse.getName();

                if (indexBrdName.equalsIgnoreCase("Nifty 50")) {
                    indexBrdName = "Nifty";
                }

                if (indexBrdName.equalsIgnoreCase(txt_nifty.getText().toString())) {
                    if (getAssetType(broadcastResponse.getIndexCode()).equalsIgnoreCase("currency")) {
                        niftyValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        niftychangetxt.setText(String.format("%.4f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                        if (Double.parseDouble(broadcastResponse.getLast()) > Double.parseDouble(niftydayshightxt.getText().toString().replace(",", ""))) {
                            niftydayshightxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        } else if (Double.parseDouble(broadcastResponse.getLast()) < Double.parseDouble(niftydaylowtxt.getText().toString().replace(",", ""))) {
                            niftydaylowtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        }
                    } else {
                        niftyValuetxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast()))));
                        niftychangetxt.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                        if (Double.parseDouble(broadcastResponse.getLast()) > Double.parseDouble(niftydayshightxt.getText().toString().replace(",", ""))) {
                            niftydayshightxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast()))));
                        } else if (Double.parseDouble(broadcastResponse.getLast()) < Double.parseDouble(niftydaylowtxt.getText().toString().replace(",", ""))) {
                            niftydaylowtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast()))));
                        }
                    }

                }
                if (indexBrdName.equalsIgnoreCase(txt_sensex.getText().toString())) {
                    if (getAssetType(broadcastResponse.getIndexCode()).equalsIgnoreCase("currency")) {
                        sensexValuetxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        sensexchangetxt.setText(String.format("%.4f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                        if (Double.parseDouble(broadcastResponse.getLast()) > Double.parseDouble(sensexdayshightxtxt.getText().toString())) {
                            sensexdayshightxtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        } else if (Double.parseDouble(broadcastResponse.getLast()) < Double.parseDouble(sensexdaylowtxt.getText().toString())) {
                            sensexdaylowtxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        }
                    } else {
                        sensexValuetxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast()))));

                        sensexchangetxt.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");

                        if (Double.parseDouble(broadcastResponse.getLast()) > Double.parseDouble(sensexdayshightxtxt.getText().toString().replace(",", ""))) {
                            sensexdayshightxtxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                        } else if (Double.parseDouble(broadcastResponse.getLast()) < Double.parseDouble(sensexdaylowtxt.getText().toString().replace(",", ""))) {
                            sensexdaylowtxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                        }
                    }
                }
                if (indexBrdName.equalsIgnoreCase(firstSymbolNametxt.getText().toString())) {
                    if (getAssetType(broadcastResponse.getIndexCode()).equalsIgnoreCase("currency")) {
                        firstvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        firstchange.setText(String.format("%.4f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.4f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                    } else {
                        firstvalue.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast()))));
                        firstchange.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                    }
                }
                if (indexBrdName.equalsIgnoreCase(secondSymboltxt.getText().toString())) {
                    if (getAssetType(broadcastResponse.getIndexCode()).equalsIgnoreCase("currency")) {
                        secondvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        secondchange.setText(String.format("%.4f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                    } else {
                        secondvalue.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast()))));
                        secondchange.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                    }
                }
                if (indexBrdName.equalsIgnoreCase(thirdSymbolnametxt.getText().toString())) {
                    if (getAssetType(broadcastResponse.getIndexCode()).equalsIgnoreCase("currency")) {
                        thirdvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast()))));
                        thirdchange.setText(String.format("%.4f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");

                    } else {
                        thirdvalue.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast()))));
                        thirdchange.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                    }
                }
                txt_nifty_value = txt_nifty.getText().toString().trim();
                txt_sensex_value = txt_sensex.getText().toString().trim();
                txt_first_symbol_name_value = firstSymbolNametxt.getText().toString().trim();
                txt_snd_symbol_name_value = secondSymboltxt.getText().toString().trim();
                txt_thrd_symbol_name_value = thirdSymbolnametxt.getText().toString().trim();
                niftyValuetxtvalue = niftyValuetxt.getText().toString().trim();
                sensexValuetxtvalue = sensexValuetxt.getText().toString().trim();
                niftychangetxtvalue = niftychangetxt.getText().toString().trim();
                sensexchangetxtvalue = sensexchangetxt.getText().toString().trim();
                niftydayshightxtvalue = niftydayshightxt.getText().toString().trim();
                sensexdayshightxtxtvalue = sensexdayshightxtxt.getText().toString().trim();
                niftydaylowtxtvalue = niftydaylowtxt.getText().toString().trim();
                sensexdaylowtxtvalue = sensexdaylowtxt.getText().toString().trim();
                firstSymbolNametxtvalue = firstSymbolNametxt.getText().toString().trim();
                secondSymboltxtvalue = secondSymboltxt.getText().toString().trim();
                thirdSymbolnametxtvalue = thirdSymbolnametxt.getText().toString().trim();
                firstvaluevalue = firstvalue.getText().toString().trim();
                secondvaluevalue = secondvalue.getText().toString().trim();
                thirdvaluevalue = thirdvalue.getText().toString().trim();
                firstchangevalue = firstchange.getText().toString().trim();
                secondchangevalue = secondchange.getText().toString().trim();
                thirdchangevalue = thirdchange.getText().toString().trim();
                updateBorderLineColor();
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void expandAllGroups(int gpos) {
//TODO : For Expand All tab bydefault
       /* if(!adapter.isGroupExpanded(gpos)) {
            adapter.acumengroupgleGroup(gpos);
            adapter.notifyDataSetChanged();
        }*/
    }


    private String getExchangeFromToken(String token) {
        /*int tokenInt = Integer.parseInt(token);
        if(((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if(((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NSEEX";
        } else if(((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "NSE";
        } else {
            return "NSE";
        }
        //  return "";*/
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else if ((tokenInt >= 1903000000) && (tokenInt <= 1903999999)) {
            return "NSECOMM";
        } else if ((tokenInt >= 2003000000) && (tokenInt <= 2003999999)) {
            return "BSECOMM";
        } else if ((tokenInt >= 502000000) && (tokenInt <= 502999999)) {
            return "NSECURR";
        } else if ((tokenInt >= 1302000000) && (tokenInt <= 1302999999)) {
            return "BSECURR";
        } else {
            return "BSE";
        }
    }

    private String getAssetType(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if (((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }
    }


}

package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.CommodityRecycleAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData_v2;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerMainHeader;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MarketDataResponse;
import com.acumengroup.mobile.model.OIAnalysisDataResponse;
import com.acumengroup.mobile.model.SubHeader;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.bfsl.core.network.MarketMoversModel;
import com.bfsl.core.network.TopGainersModels;
import com.bfsl.core.network.TopLosersModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class CommodityTabFragment extends GreekBaseFragment implements View.OnClickListener {


    private ArrayList<GainerData> glist = new ArrayList<>();
    private ArrayList<GainerData> glistloser = new ArrayList<>();
    private ArrayList<GainerData> glistsector = new ArrayList<>();
    private ArrayList<GainerData_v2> glistOIAnalysis = new ArrayList<>();

    private ArrayList<String> glistToken = new ArrayList<>();
    private ArrayList<String> glistloserToken = new ArrayList<>();
    private ArrayList<String> glistsectorToken = new ArrayList<>();
    private ArrayList<String> glistOIAnalysisToken = new ArrayList<>();
    private LinearLayout   second_layout,firstLayout,marekt_movers_spinn;;

    private SharedPreferences sp;
    private TextView niftyValuetxt, sensexValuetxt, niftychangetxt, sensexchangetxt, niftydayshightxt, sensexdayshightxtxt, niftydaylowtxt, sensexdaylowtxt, firstSymbolNametxt, secondSymboltxt, thirdSymbolnametxt, firstvalue, secondvalue, thirdvalue, firstchange, secondchange, thirdchange;
    private ImageView firstEditImg, scndEditImg, thrdEditImg;
    private RecyclerView expandableListView;
    private ArrayList<SubHeader> openAnalysisHeaderList;

    private GreekTextView txt_nifty, txt_sensex;
    private StreamingController streamController = new StreamingController();
    public static final String TOP_GAINERS = "TOP GAINERS";
    public static final String TOP_LOSER = "TOP LOSERS";
    public static final String MARKET_MOVERS = "MARKET MOVERS";
    public static final String OPEN_INTEREST_ANALYSIS = "OPEN INTEREST ANALYSIS";
    private MarketDataResponse marketResponse;
    private String currentType = "", selectedmarket_statistic = "";
    private ArrayList streamingList;
    private ArrayList streamingForIndexList;
    private CommodityRecycleAdapter adapter;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearleft, linearright;
    private int textColorPositive, textColorNegative;
    private ArrayList<SubHeader> subtopgainers, subtoplosers, submarketmove, subOIAnalysis;
    private ArrayList<GainerMainHeader> mainHeadersList;
    private GreekTextView nodataTxt,marekt_movers_text,txt_high_nifty_name,txt_low_nifty_name,txt_high_sensex_name,txt_low_sensex_name;
    private Spinner market_statistic_spinner;
    private ArrayList<String> market_statistics;
    private ArrayList<GainerData> longBuildupList, shortBuildUpList, longUnwindingList, shortCoveringList;
    private ArrayList<String> longBuildupListToken, shortBuildUpListToken, longUnwindingListToken, shortCoveringListToken;
    private OIAnalysisDataResponse oiAnalysisDataResponse;
    private String marketIDSelected = "9";


    public CommodityTabFragment() {

        streamingList = new ArrayList<>();
        streamingForIndexList = new ArrayList<>();
        subtopgainers = new ArrayList<>();
        subtoplosers = new ArrayList<>();
        submarketmove = new ArrayList<>();
        subOIAnalysis = new ArrayList<>();
        mainHeadersList = new ArrayList<>();
        market_statistics = new ArrayList<>();
        openAnalysisHeaderList = new ArrayList<SubHeader>();

    }


    @Override
    public void onFragmentResult(Object data) {
//        streamingForIndexList.clear();
        if (data != null) {
            sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sp.edit();
            if (((Bundle) data).getString("clickimg") != null) {
                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("1")) {
                    firstSymbolNametxt.setText(((Bundle) data).getString("symbol"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        firstvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f",Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }else {
                        firstvalue.setText(StringStuff.commaINRDecorator(String.format("%.2f",Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }
                    firstchange.setText(((Bundle) data).getString("change"));

                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex();

                }
                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("2")) {
                    secondSymboltxt.setText(((Bundle) data).getString("symbol"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        secondvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f",Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }else {
                        secondvalue.setText(StringStuff.commaINRDecorator(String.format("%.2f",Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }
                    secondchange.setText(((Bundle) data).getString("change"));

                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex();

                }
                if (((Bundle) data).getString("clickimg").equalsIgnoreCase("3")) {
                    thirdSymbolnametxt.setText(((Bundle) data).getString("symbol"));
                    if (getAssetType(((Bundle) data).getString("token")).equalsIgnoreCase("currency")) {
                        thirdvalue.setText(StringStuff.commaINRDecorator(String.format("%.4f",Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }else {
                        thirdvalue.setText(StringStuff.commaINRDecorator(String.format("%.2f",Double.parseDouble(((Bundle) data).getString("ltp")))));
                    }
                    thirdchange.setText(((Bundle) data).getString("change"));

                    if (!streamingForIndexList.contains(((Bundle) data).getString("token"))) {
                        streamingForIndexList.add(((Bundle) data).getString("token"));
                    }
                    saveindex();

                }
                updateBorderLineColor();
                sendStreamingIndexRequest("index");

            }
        }
    }


    public void saveindex() {

        String sipFreqUrl;
        final String currentAsset = "COMMODITY";//CURRENCY
        showProgress();
        if (AccountDetails.getUsertype(getActivity()).equalsIgnoreCase("Open")) {
            sipFreqUrl = "addIndexForUserV2?assetType=" + currentAsset.toLowerCase() + "&index1=" +
                    txt_nifty.getText().toString() + "&index2=" + txt_sensex.getText().toString()
                    + "&index3=" + firstSymbolNametxt.getText().toString() +
                    "&index4=" + secondSymboltxt.getText().toString() + "&index5=" +
                    thirdSymbolnametxt.getText().toString() + "&gcid=" +
                    AccountDetails.getClientCode(getActivity()) +
                    "&gscid=" + AccountDetails.getToken(getActivity());
        } else {
            sipFreqUrl = "addIndexForUserV2?assetType=" + currentAsset.toLowerCase() + "&index1=" +
                    txt_nifty.getText().toString() + "&index2=" + txt_sensex.getText().toString()
                    + "&index3=" + firstSymbolNametxt.getText().toString() + "&index4=" + secondSymboltxt.getText().toString()
                    + "&index5=" + thirdSymbolnametxt.getText().toString() +
                    "&gcid=" + AccountDetails.getClientCode(getActivity()) + "&gscid=" +
                    AccountDetails.getUsername(getActivity());
        }

        WSHandler.getRequest(getActivity(), sipFreqUrl, new WSHandler.GreekResponseCallback() {

            @Override
            public void onSuccess(JSONObject response) {

                hideProgress();
                Log.e("OverviewTabScreen", "Response====>" + response);

            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View overview  = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_commodty_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_commodty_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        streamingList = new ArrayList();
        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            textColorPositive = R.color.whitetheambuyColor;

        }else {
            textColorPositive = R.color.dark_green_positive;

        }
        textColorNegative = R.color.dark_red_negative;


        if (AccountDetails.allowedmarket_mcx) {
            market_statistics.add("MCX");
        }

        if (AccountDetails.allowedmarket_ncdex) {
            market_statistics.add("NCDEX");
        }

        if (AccountDetails.allowedmarket_nCOM) {
            market_statistics.add("NSECOM");
        }

        if (AccountDetails.allowedmarket_bCOM) {
            market_statistics.add("BSECOM");
        }

        setupView(overview);
        settheme();
        return overview;
    }
    private void settheme() {
        if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("White")){
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
            firstEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            scndEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            thrdEditImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
            second_layout.setBackgroundColor(getResources().getColor(R.color.white));
            expandableListView.setBackgroundColor(getResources().getColor(R.color.white));
            firstLayout.setBackgroundColor(getResources().getColor(R.color.white));
            marekt_movers_spinn.setBackgroundColor(getResources().getColor(R.color.white));
            market_statistic_spinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));

        }/*else if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("black")){

        }*/

    }
    private void updateBorderLineColor() {

        String value1 = firstchange.getText().toString();
        if (value1.startsWith("-")) {
            linearLayout1.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            firstchange.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linearLayout1.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            }else {
                linearLayout1.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }

            firstchange.setTextColor(getResources().getColor(textColorPositive));

        }

        String value2 = secondchange.getText().toString();
        if (value2.startsWith("-")) {
            linearLayout2.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            secondchange.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linearLayout2.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            }else {
                linearLayout2.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }


            secondchange.setTextColor(getResources().getColor(textColorPositive));
        }

        String value3 = thirdchange.getText().toString();
        if (value3.startsWith("-")) {
            linearLayout3.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            thirdchange.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linearLayout3.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            }else {
                linearLayout3.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            thirdchange.setTextColor(getResources().getColor(textColorPositive));
        }

        String niftychange = niftychangetxt.getText().toString();
        if (niftychange.startsWith("-")) {
            linearleft.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            niftychangetxt.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linearleft.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            }else {
                linearleft.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            niftychangetxt.setTextColor(getResources().getColor(textColorPositive));
        }

        String sensexchange = sensexchangetxt.getText().toString();

        if (sensexchange.startsWith("-")) {
            linearright.setBackground(getResources().getDrawable(R.drawable.single_line_red_border));
            sensexchangetxt.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                linearright.setBackground(getResources().getDrawable(R.drawable.whitetheamsingle_line_green_border));
            }else {
                linearright.setBackground(getResources().getDrawable(R.drawable.single_line_green_border));
            }
            sensexchangetxt.setTextColor(getResources().getColor(textColorPositive));
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");

            ArrayAdapter<String> expiryTypeAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), market_statistics) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")){
                        v.setTextColor(getResources().getColor(R.color.black));
                    }else {
                        v.setTextColor(getResources().getColor(R.color.white));
                    }
                    v.setPadding(15, 8, 15, 8);
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
            market_statistic_spinner.setSelection(0);
            market_statistic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedmarket_statistic = market_statistics.get(position);

                    if (market_statistics.get(position).equalsIgnoreCase("MCX")) {
                        marketIDSelected = "9";
                    } else if (market_statistics.get(position).equalsIgnoreCase("NCDEX")) {
                        marketIDSelected = "7";

                    } else if (market_statistics.get(position).equalsIgnoreCase("NSECOM")) {
                        marketIDSelected = "16";

                    } else if (market_statistics.get(position).equalsIgnoreCase("BSECOM")) {
                        marketIDSelected = "17";

                    }

                    loadTopGainer("Top Gainers");
                    loadTopLooser("Top Losers");
                    loadMarketMovers("Most Active (Vol.)");
                    loadOpenInterest("Most Active (Val.)");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            sendIndianIndicesRequest();
        }
    }

    private void setupView(View overview) {

        linearLayout1 = overview.findViewById(R.id.linera1);
        linearLayout2 = overview.findViewById(R.id.linera2);
        linearLayout3 = overview.findViewById(R.id.linera3);
        linearleft = overview.findViewById(R.id.linearleft);
        linearright = overview.findViewById(R.id.linearright);
        marekt_movers_text = overview.findViewById(R.id.marekt_movers_text);
        market_statistic_spinner = overview.findViewById(R.id.market_statistic_spinner);
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        txt_nifty = overview.findViewById(R.id.txt_nifty);
        txt_sensex = overview.findViewById(R.id.txt_sensex);
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

        firstEditImg = overview.findViewById(R.id.img_first_edit);
        scndEditImg = overview.findViewById(R.id.img_snd_edit);
        thrdEditImg = overview.findViewById(R.id.img_thrd_edit);
        second_layout = overview.findViewById(R.id.second_layout);
        firstLayout = overview.findViewById(R.id.first_layout);
        nodataTxt = overview.findViewById(R.id.txt_nodata);
        txt_high_nifty_name = overview.findViewById(R.id.txt_day_high_name);
        txt_low_nifty_name = overview.findViewById(R.id.txt_day_low_name);
        txt_high_sensex_name = overview.findViewById(R.id.txt_day_high_name_sensex);
        txt_low_sensex_name = overview.findViewById(R.id.txt_day_low_name_sensex);
        marekt_movers_spinn = overview.findViewById(R.id.marekt_movers_spinn);


        market_statistic_spinner = overview.findViewById(R.id.market_statistic_spinner);


        firstEditImg.setOnClickListener(this);
        scndEditImg.setOnClickListener(this);
        thrdEditImg.setOnClickListener(this);


        expandableListView = overview.findViewById(R.id.overvie_detail_list);
        adapter = new CommodityRecycleAdapter(getMainActivity(), mainHeadersList);
        expandableListView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        expandableListView.setHasFixedSize(true);
        ((SimpleItemAnimator) expandableListView.getItemAnimator()).setSupportsChangeAnimations(false);


    }

    public void onEventMainThread(TopGainersModels topGainersModels) {
        Log.v("Onevent response", topGainersModels.toString());

        try {



            if (marketIDSelected.equalsIgnoreCase(marketIDSelected)) {
                streamingList.clear();
                subtopgainers.clear();

                glistToken.clear();
                glist.clear();

                if (topGainersModels.getResponse().getData().size() < 10) {
                    for (int i = 0; i < topGainersModels.getResponse().getData().size(); i++) {
                        GainerData gainerData = new GainerData(
                                topGainersModels.getResponse().getData().get(i).getSymbol(),
                                topGainersModels.getResponse().getData().get(i).getDescription(),
                                topGainersModels.getResponse().getData().get(i).getExchange(),
                                topGainersModels.getResponse().getData().get(i).getLotSize(),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getChange(),
                                topGainersModels.getResponse().getData().get(i).getPerchange(),
                                topGainersModels.getResponse().getData().get(i).getAssetType(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                topGainersModels.getResponse().getData().get(i).getVoltraded(),
                                topGainersModels.getResponse().getData().get(i).getTickSize(),
                                topGainersModels.getResponse().getData().get(i).getMultiply_factor(),
                                topGainersModels.getResponse().getData().get(i).getExpiryDate(),
                                topGainersModels.getResponse().getData().get(i).getOptiontype(),
                                topGainersModels.getResponse().getData().get(i).getStrikeprice(),
                                topGainersModels.getResponse().getData().get(i).getInstrumentname()
                        );

                        glist.add(gainerData);

                        glistToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());


                    }

                } else {
                    for (int i = 0; i < 10; i++) {
                        GainerData gainerData = new GainerData(
                                topGainersModels.getResponse().getData().get(i).getSymbol(),
                                topGainersModels.getResponse().getData().get(i).getDescription(),
                                topGainersModels.getResponse().getData().get(i).getExchange(),
                                topGainersModels.getResponse().getData().get(i).getLotSize(),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getLtp(),
                                topGainersModels.getResponse().getData().get(i).getChange(),
                                topGainersModels.getResponse().getData().get(i).getPerchange(),
                                topGainersModels.getResponse().getData().get(i).getAssetType(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                topGainersModels.getResponse().getData().get(i).getToken(),
                                topGainersModels.getResponse().getData().get(i).getVoltraded(),
                                topGainersModels.getResponse().getData().get(i).getTickSize(),
                                topGainersModels.getResponse().getData().get(i).getMultiply_factor(),
                                topGainersModels.getResponse().getData().get(i).getExpiryDate(),
                                topGainersModels.getResponse().getData().get(i).getOptiontype(),
                                topGainersModels.getResponse().getData().get(i).getStrikeprice(),
                                topGainersModels.getResponse().getData().get(i).getInstrumentname()

                        );


                        glist.add(gainerData);
//                                    subtopgainers.add(new SubHeader("OPTION_CHAIN", glist));

                        glistToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());


                    }
                }

                subtopgainers.add(new SubHeader("", glist));

            }


//                        sendStreamingRequest("ltpinfo");

            mainHeadersList.clear();
            mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
            mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
            mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
            mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));

            expandableListView.setAdapter(adapter);


        } catch (Exception e) {
            hideProgress();
//          toggleErrorLayout(true);
            e.printStackTrace();
        }
    }

    public void onEventMainThread(TopLosersModel topLosersModel) {
        Log.v("Onevent response", topLosersModel.toString());

        try {
//                        streamingList.clear();


            if (marketIDSelected.equalsIgnoreCase(marketIDSelected)) {
                subtoplosers.clear();

                glistloserToken.clear();
                glistloser.clear();

                if (topLosersModel.getResponse().getData().size() < 10) {

                    for (int i = 0; i < topLosersModel.getResponse().getData().size(); i++) {

                        GainerData gainerData = new GainerData(
                                topLosersModel.getResponse().getData().get(i).getSymbol(),
                                topLosersModel.getResponse().getData().get(i).getDescription(),
                                topLosersModel.getResponse().getData().get(i).getExchange(),
                                topLosersModel.getResponse().getData().get(i).getLotSize(),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getChange(),
                                topLosersModel.getResponse().getData().get(i).getPerchange(),
                                topLosersModel.getResponse().getData().get(i).getAssetType(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                topLosersModel.getResponse().getData().get(i).getVoltraded(),
                                topLosersModel.getResponse().getData().get(i).getTickSize(),
                                topLosersModel.getResponse().getData().get(i).getMultiply_factor(),
                                topLosersModel.getResponse().getData().get(i).getExpiryDate(),
                                topLosersModel.getResponse().getData().get(i).getOptiontype(),
                                topLosersModel.getResponse().getData().get(i).getStrikeprice(),
                                topLosersModel.getResponse().getData().get(i).getInstrumentname()
                        );

                        glistloser.add(gainerData);

                        glistloserToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());

                    }

                } else {

                    for (int i = 0; i < 10; i++) {
                        GainerData gainerData = new GainerData(
                                topLosersModel.getResponse().getData().get(i).getSymbol(),
                                topLosersModel.getResponse().getData().get(i).getDescription(),
                                topLosersModel.getResponse().getData().get(i).getExchange(),
                                topLosersModel.getResponse().getData().get(i).getLotSize(),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getLtp(),
                                topLosersModel.getResponse().getData().get(i).getChange(),
                                topLosersModel.getResponse().getData().get(i).getPerchange(),
                                topLosersModel.getResponse().getData().get(i).getAssetType(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                topLosersModel.getResponse().getData().get(i).getToken(),
                                topLosersModel.getResponse().getData().get(i).getVoltraded(),
                                topLosersModel.getResponse().getData().get(i).getTickSize(),
                                topLosersModel.getResponse().getData().get(i).getMultiply_factor(),
                                topLosersModel.getResponse().getData().get(i).getExpiryDate(),
                                topLosersModel.getResponse().getData().get(i).getOptiontype(),
                                topLosersModel.getResponse().getData().get(i).getStrikeprice(),
                                topLosersModel.getResponse().getData().get(i).getInstrumentname()
                        );

                        glistloser.add(gainerData);

                        glistloserToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());

                    }


                }
                subtoplosers.add(new SubHeader("", glistloser));
            }

//                        sendStreamingRequest("ltpinfo");

            mainHeadersList.clear();
            mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
            mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
            mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
            mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));

            expandableListView.setAdapter(adapter);

        } catch (Exception e) {
            hideProgress();
//                        toggleErrorLayout(true);
            e.printStackTrace();
        }

    }

    public void onEventMainThread(MarketMoversModel marketMoversModel) {
        Log.v("Onevent response", marketMoversModel.toString());


        try {
//                        streamingList.clear();


            if (marketIDSelected.equalsIgnoreCase(marketIDSelected)) {
                submarketmove.clear();
                glistsectorToken.clear();
                glistsector.clear();

                if (marketMoversModel.getResponse().getData().size() < 10) {
                    for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
                        GainerData gainerData = new GainerData(
                                marketMoversModel.getResponse().getData().get(i).getSymbol(),
                                marketMoversModel.getResponse().getData().get(i).getDescription(),
                                marketMoversModel.getResponse().getData().get(i).getExchange(),
                                marketMoversModel.getResponse().getData().get(i).getLotSize(),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getChange(),
                                marketMoversModel.getResponse().getData().get(i).getPerchange(),
                                marketMoversModel.getResponse().getData().get(i).getAssetType(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                marketMoversModel.getResponse().getData().get(i).getVoltraded(),
                                marketMoversModel.getResponse().getData().get(i).getTickSize(),
                                marketMoversModel.getResponse().getData().get(i).getMultiply_factor(),
                                marketMoversModel.getResponse().getData().get(i).getExpiryDate(),
                                marketMoversModel.getResponse().getData().get(i).getOptiontype(),
                                marketMoversModel.getResponse().getData().get(i).getStrikeprice(),
                                marketMoversModel.getResponse().getData().get(i).getInstrumentname()
                        );

                        glistsector.add(gainerData);

                        glistsectorToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());

                    }

                } else {

                    for (int i = 0; i < 10; i++) {
                        GainerData gainerData = new GainerData(
                                marketMoversModel.getResponse().getData().get(i).getSymbol(),
                                marketMoversModel.getResponse().getData().get(i).getDescription(),
                                marketMoversModel.getResponse().getData().get(i).getExchange(),
                                marketMoversModel.getResponse().getData().get(i).getLotSize(),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getLtp(),
                                marketMoversModel.getResponse().getData().get(i).getChange(),
                                marketMoversModel.getResponse().getData().get(i).getPerchange(),
                                marketMoversModel.getResponse().getData().get(i).getAssetType(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                marketMoversModel.getResponse().getData().get(i).getToken(),
                                marketMoversModel.getResponse().getData().get(i).getVoltraded(),
                                marketMoversModel.getResponse().getData().get(i).getTickSize(),
                                marketMoversModel.getResponse().getData().get(i).getMultiply_factor(),
                                marketMoversModel.getResponse().getData().get(i).getExpiryDate(),
                                marketMoversModel.getResponse().getData().get(i).getOptiontype(),
                                marketMoversModel.getResponse().getData().get(i).getStrikeprice(),
                                marketMoversModel.getResponse().getData().get(i).getInstrumentname()
                        );

                        glistsector.add(gainerData);
                        glistsectorToken.add(gainerData.getToken());
//                                    streamingList.add(gainerData.getToken());
                    }
                }
                submarketmove.add(new SubHeader("", glistsector));

            }

//                        sendStreamingRequest("ltpinfo");

            mainHeadersList.clear();
            mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
            mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
            mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
            mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));

            expandableListView.setAdapter(adapter);

        } catch (Exception e) {
            hideProgress();
//          toggleErrorLayout(true);
            e.printStackTrace();
        }

    }

    private void loadTopGainer(String top_gainers) {


        final String currentAsset = "COMMODITY";//CURRENCY
        final String type;
        String service;
        String serviceURL;
        switch ("Top Gainers") {
            case "Top Gainers":
                type = "gainer";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() +
                        "&type=" + type + "&exchange=" + selectedmarket_statistic;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Most Active Contracts (Vol.)":
                type = "volume";
                service = "getMostActiveContracts_Mobile";
                serviceURL = service + "?type=" + currentAsset.toLowerCase();
                break;
            default:
                return;
        }
        currentType = type;

        if (serviceURL.length() > 0) {

            //showProgress();

            marketResponse = null;
            if(AccountDetails.isIsApolloConnected()) {
                streamController.sendTOPgainersRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                        AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
            }


//            WSHandler.getRequest(getActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
//                @Override
//                public void onSuccess(JSONObject response) {
//
//                    try {
//                        streamingList.clear();
//                        marketResponse = new MarketDataResponse();
//                        marketResponse.fromJSON(response);
//                        subtopgainers.clear();
//
//                        if (type.equalsIgnoreCase("gainer")) {
//
//                            glistToken.clear();
//                            glist.clear();
//
//                            if (marketResponse.getMarketDataModelList().size() < 10) {
//                                for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
//
//                                    GainerData gainerData = new GainerData(
//                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
//                                            marketResponse.getMarketDataModelList().get(i).getDescription(),
//                                            marketResponse.getMarketDataModelList().get(i).getExchange(),
//                                            marketResponse.getMarketDataModelList().get(i).getLotSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getAssetType(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getVolume(),
//                                            marketResponse.getMarketDataModelList().get(i).getTickSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getMultiply_factor(),
//                                            marketResponse.getMarketDataModelList().get(i).getExpiryDate(),
//                                            marketResponse.getMarketDataModelList().get(i).getoptiontype(),
//                                            marketResponse.getMarketDataModelList().get(i).getstrikeprice(),
//                                            marketResponse.getMarketDataModelList().get(i).getinstrumentname()
//                                    );
//
//                                    glist.add(gainerData);
//
//                                    glistToken.add(gainerData.getToken());
//
//                                }
//
//                            } else {
//                                for (int i = 0; i < 10; i++) {
//
//                                    GainerData gainerData = new GainerData(
//                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
//                                            marketResponse.getMarketDataModelList().get(i).getDescription(),
//                                            marketResponse.getMarketDataModelList().get(i).getExchange(),
//                                            marketResponse.getMarketDataModelList().get(i).getLotSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getAssetType(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getVolume(),
//                                            marketResponse.getMarketDataModelList().get(i).getTickSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getMultiply_factor(),
//                                            marketResponse.getMarketDataModelList().get(i).getExpiryDate(),
//                                            marketResponse.getMarketDataModelList().get(i).getoptiontype(),
//                                            marketResponse.getMarketDataModelList().get(i).getstrikeprice(),
//                                            marketResponse.getMarketDataModelList().get(i).getinstrumentname());
//
//
//                                    glist.add(gainerData);
//                                    glistToken.add(gainerData.getToken());
//
//
//                                }
//                            }
//
//                            subtopgainers.add(new SubHeader("", glist));
//
//                        }
//
//                        mainHeadersList.clear();
//                        mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
//                        mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
//                        mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
//                        mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
//
//                        expandableListView.setAdapter(adapter);
//
//                    } catch (JSONException e) {
//                        hideProgress();
//                        e.printStackTrace();
//                    }
//
//                    hideProgress();
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    hideProgress();
//                    subtopgainers.clear();
//                    glist.clear();
//                    GainerData gainerData = new GainerData("",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "");
//
//                    subtopgainers.add(new SubHeader("", glist));
//                    mainHeadersList.clear();
//                    mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
//                    mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
//                    mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
//                    mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
//                }
//            });
        }
    }

    private void loadTopLooser(String top_losers) {


        final String currentAsset = "COMMODITY";//CURRENCY
        final String type;
        String service;
        String serviceURL;
        switch ("Top Losers") {
            case "Top Gainers":
                type = "gainer";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" +
                        type + "&exchange=" + selectedmarket_statistic;
                break;
            case "Most Active Contracts (Vol.)":
                type = "volume";
                service = "getMostActiveContracts_Mobile";
                serviceURL = service + "?type=" + currentAsset.toLowerCase();
                break;
            default:
                return;
        }


        currentType = type;
        if (serviceURL.length() > 0) {

           // showProgress();
            marketResponse = null;
            if(AccountDetails.isIsApolloConnected()) {
                streamController.sendTOPlosersRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                        AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
            }
//            WSHandler.getRequest(getActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
//                @Override
//                public void onSuccess(JSONObject response) {
//
//                    try {
//                        marketResponse = new MarketDataResponse();
//                        marketResponse.fromJSON(response);
//
//                        subtoplosers.clear();
//                        if (type.equalsIgnoreCase("loser")) {
//                            glistloserToken.clear();
//                            glistloser.clear();
//
//                            if (marketResponse.getMarketDataModelList().size() < 10) {
//
//                                for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
//
//
//                                    GainerData gainerData = new GainerData(
//                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
//                                            marketResponse.getMarketDataModelList().get(i).getDescription(),
//                                            marketResponse.getMarketDataModelList().get(i).getExchange(),
//                                            marketResponse.getMarketDataModelList().get(i).getLotSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getAssetType(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getVolume(),
//                                            marketResponse.getMarketDataModelList().get(i).getTickSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getMultiply_factor(),
//                                            marketResponse.getMarketDataModelList().get(i).getExpiryDate(),
//                                            marketResponse.getMarketDataModelList().get(i).getoptiontype(),
//                                            marketResponse.getMarketDataModelList().get(i).getstrikeprice(),
//                                            marketResponse.getMarketDataModelList().get(i).getinstrumentname());
//
//
//                                    glistloser.add(gainerData);
//
//                                    glistloserToken.add(gainerData.getToken());
//
//                                }
//
//                            } else {
//
//                                for (int i = 0; i < 10; i++) {
//
//                                    GainerData gainerData = new GainerData(
//                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
//                                            marketResponse.getMarketDataModelList().get(i).getDescription(),
//                                            marketResponse.getMarketDataModelList().get(i).getExchange(),
//                                            marketResponse.getMarketDataModelList().get(i).getLotSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getAssetType(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getVolume(),
//                                            marketResponse.getMarketDataModelList().get(i).getTickSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getMultiply_factor(),
//                                            marketResponse.getMarketDataModelList().get(i).getExpiryDate(),
//                                            marketResponse.getMarketDataModelList().get(i).getoptiontype(),
//                                            marketResponse.getMarketDataModelList().get(i).getstrikeprice()
//                                            , marketResponse.getMarketDataModelList().get(i).getinstrumentname()
//                                    );
//                                    glistloser.add(gainerData);
//
//                                    glistloserToken.add(gainerData.getToken());
//
//                                }
//
//
//                            }
//                            subtoplosers.add(new SubHeader("", glistloser));
//                        }
//
//
//                        mainHeadersList.clear();
//                        mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
//                        mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
//                        mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
//                        mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
//
//                        expandableListView.setAdapter(adapter);
//
//                    } catch (JSONException e) {
//                        hideProgress();
//                        e.printStackTrace();
//                    }
//
//                    hideProgress();
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    hideProgress();
//
//                    glistloser.clear();
//                    subtoplosers.clear();
//
//                    GainerData gainerData = new GainerData("",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "");
//
//                    glistloser.add(gainerData);
//                    subtoplosers.add(new SubHeader("", glistloser));
//                    mainHeadersList.clear();
//                    mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
//                    mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
//                    mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
//                    mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
//                }
//            });
        }
    }

    private void loadMarketMovers(String movers) {
        final String currentAsset = "COMMODITY";  //CURRENCY
        final String type;
        String service;
        String serviceURL;
        switch ("Most Active Contracts (Vol.)") {
            case "Top Gainers":
                type = "gainer";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Most Active Contracts (Vol.)":
                type = "volume";
                service = "getMostActiveContracts_MobileV2";
                serviceURL = service + "?type=" + currentAsset.toLowerCase() + "&exchange=" + selectedmarket_statistic;
                break;
            default:
                return;
        }

        currentType = type;
        if (serviceURL.length() > 0) {

            //showProgress();
            marketResponse = null;
            if(AccountDetails.isIsApolloConnected()) {
                streamController.sendMarketmoverRequest(getContext(), marketIDSelected, AccountDetails.getUsername(getMainActivity()),
                        AccountDetails.getSessionId(getMainActivity()), AccountDetails.getClientCode(getMainActivity()));
            }

//            WSHandler.getRequest(getActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
//                @Override
//                public void onSuccess(JSONObject response) {
//
//
//                    try {
//                        marketResponse = new MarketDataResponse();
//                        marketResponse.fromJSON(response);
//
//                        submarketmove.clear();
//                        if (type.equalsIgnoreCase("volume")) {
//                            glistsectorToken.clear();
//                            glistsector.clear();
//
//                            if (marketResponse.getMarketDataModelList().size() < 10) {
//                                for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
//
//                                    GainerData gainerData = new GainerData(
//                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
//                                            marketResponse.getMarketDataModelList().get(i).getDescription(),
//                                            marketResponse.getMarketDataModelList().get(i).getExchange(),
//                                            marketResponse.getMarketDataModelList().get(i).getLotSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getAssetType(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getVolume(),
//                                            marketResponse.getMarketDataModelList().get(i).getTickSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getMultiply_factor(),
//                                            marketResponse.getMarketDataModelList().get(i).getExpiryDate(),
//                                            marketResponse.getMarketDataModelList().get(i).getoptiontype(),
//                                            marketResponse.getMarketDataModelList().get(i).getstrikeprice(),
//                                            marketResponse.getMarketDataModelList().get(i).getinstrumentname()
//
//                                    );
//
//
//                                    glistsector.add(gainerData);
//
//                                    glistsectorToken.add(gainerData.getToken());
//                                }
//
//                            } else {
//
//                                for (int i = 0; i < 10; i++) {
//
//
//                                    GainerData gainerData = new GainerData(
//                                            marketResponse.getMarketDataModelList().get(i).getSymbol(),
//                                            marketResponse.getMarketDataModelList().get(i).getDescription(),
//                                            marketResponse.getMarketDataModelList().get(i).getExchange(),
//                                            marketResponse.getMarketDataModelList().get(i).getLotSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getLtp(),
//                                            marketResponse.getMarketDataModelList().get(i).getChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getPerChange(),
//                                            marketResponse.getMarketDataModelList().get(i).getAssetType(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getToken(),
//                                            marketResponse.getMarketDataModelList().get(i).getVolume(),
//                                            marketResponse.getMarketDataModelList().get(i).getTickSize(),
//                                            marketResponse.getMarketDataModelList().get(i).getMultiply_factor(),
//                                            marketResponse.getMarketDataModelList().get(i).getExpiryDate(),
//                                            marketResponse.getMarketDataModelList().get(i).getoptiontype(),
//                                            marketResponse.getMarketDataModelList().get(i).getstrikeprice(),
//                                            marketResponse.getMarketDataModelList().get(i).getinstrumentname()
//                                    );
//
//
//                                    glistsector.add(gainerData);
//
//                                    glistsectorToken.add(gainerData.getToken());
//
//                                }
//
//                            }
//
//                            submarketmove.add(new SubHeader("", glistsector));
//
//                        }
//
//                        mainHeadersList.clear();
//                        mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
//                        mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
//                        mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
//                        mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
//                        expandableListView.setAdapter(adapter);
//
//                    } catch (JSONException e) {
//                        hideProgress();
//                        e.printStackTrace();
//                    }
//
//                    hideProgress();
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    hideProgress();
//                    submarketmove.clear();
//                    glistsector.clear();
//                    GainerData gainerData = new GainerData("",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "");
//
//                    glistsector.add(gainerData);
//                    submarketmove.add(new SubHeader("", glistsector));
//
//                    mainHeadersList.clear();
//                    mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
//                    mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
//                    mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
//                    mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
//
//                }
//            });
        }
    }

    private void loadOpenInterest(String movers) {

        final String currentAsset = "COMMODITY";//CURRENCY
        final String type;
        String service;
        String serviceURL;
        switch ("Most Active (Val.)") {
            case "Top Gainers":
                type = "gainer";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_MobileV2";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Most Active Contracts (Vol.)":
                type = "volume";
//                service = "getMostActiveContracts_Mobile";
                service = "getMostActiveCintracts_MobileV2";
                serviceURL = service + "?type=" + currentAsset.toLowerCase() + "&exchange=" + selectedmarket_statistic;
                break;

            case "Most Active (Val.)":
                type = "value";
                service = "getAllOpenInterestAnalysis_Mobile";
                serviceURL = service + "?exchange=" + selectedmarket_statistic;
                break;

            default:
                return;
        }

        currentType = type;
        if (serviceURL.length() > 0) {

            showProgress();
            marketResponse = null;

            WSHandler.getRequest(getActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    try {

                        openAnalysisHeaderList.clear();

                        hideProgress();
                        streamingList = new ArrayList();

                        longBuildupList = new ArrayList<GainerData>();
                        shortBuildUpList = new ArrayList<GainerData>();
                        longUnwindingList = new ArrayList<GainerData>();
                        shortCoveringList = new ArrayList<GainerData>();


                        longBuildupListToken = new ArrayList<String>();
                        shortBuildUpListToken = new ArrayList<String>();
                        longUnwindingListToken = new ArrayList<String>();
                        shortCoveringListToken = new ArrayList<String>();


                        oiAnalysisDataResponse = new OIAnalysisDataResponse();
                        oiAnalysisDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));

                        if (response.getJSONArray("data").getJSONObject(0).getJSONArray("long_build_up") != null) {
                            if (oiAnalysisDataResponse.getLongBuildupDataModelList().size() >= 1) {
                                for (int i = 0; i < oiAnalysisDataResponse.getLongBuildupDataModelList().size(); i++) {
                                    longBuildupList.add(new GainerData(
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getSymbol(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getDescription(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getExchange(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLotSize(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getChange(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getPerChange(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getAssetType(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getVolume(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getTickSize(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getMultiply_factor(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getExpiryDate(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getoptiontype(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getstrikeprice(),
                                            oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getinstrumentname()
                                    ));


                                    longBuildupListToken.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                                    streamingList.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                                }
                            } else {
                                longBuildupList.add(new GainerData(
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""));
                            }
                        }

                        if (response.getJSONArray("data").getJSONObject(0).getJSONArray("short_build_up") != null) {

                            if (oiAnalysisDataResponse.getShortBuildupDataModelList().size() >= 1) {
                                for (int i = 0; i < oiAnalysisDataResponse.getShortBuildupDataModelList().size(); i++) {


                                    shortBuildUpList.add(new GainerData(
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getSymbol(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getDescription(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getExchange(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLotSize(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getChange(), oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getPerChange(), oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getAssetType(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getVolume(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getTickSize(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getMultiply_factor(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getExpiryDate(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getoptiontype(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getstrikeprice(),
                                            oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getinstrumentname()
                                    ));

                                    shortBuildUpListToken.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                                    streamingList.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                                }
                            } else {
                                shortBuildUpList.add(new GainerData(
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""));
                            }
                        }

                        if (response.getJSONArray("data").getJSONObject(0).getJSONArray("long_unwinding") != null) {

                            if (oiAnalysisDataResponse.getLongUnwindingDataModelList().size() >= 1) {
                                for (int i = 0; i < oiAnalysisDataResponse.getLongUnwindingDataModelList().size(); i++) {

                                    longUnwindingList.add(new GainerData(
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getSymbol(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getDescription(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getExchange(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLotSize(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getChange(), oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getPerChange(), oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getAssetType(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getVolume(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getTickSize(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getMultiply_factor(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getExpiryDate(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getoptiontype(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getstrikeprice(),
                                            oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getinstrumentname()
                                    ));

                                    longUnwindingListToken.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());
                                    streamingList.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());

                                }
                            } else {
                                longUnwindingList.add(new GainerData(
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "", "", "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""));
                            }
                        }

                        if (response.getJSONArray("data").getJSONObject(0).getJSONArray("short_covering") != null) {
                            if (oiAnalysisDataResponse.getShortCoveringDataModelList().size() >= 1) {
                                for (int i = 0; i < oiAnalysisDataResponse.getShortCoveringDataModelList().size(); i++) {

                                    shortCoveringList.add(new GainerData(
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getSymbol(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getDescription(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getExchange(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLotSize(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getChange(), oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getPerChange(), oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getAssetType(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getVolume(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getTickSize(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getMultiply_factor(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getExpiryDate(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getoptiontype(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getstrikeprice(),
                                            oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getinstrumentname()
                                    ));

                                    shortCoveringListToken.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());
                                    streamingList.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());

                                }
                            } else {
                                shortCoveringList.add(new GainerData(
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "", "", "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""));
                            }

                        }

                        if (longBuildupList.size() > 0) {

                            openAnalysisHeaderList.remove(new SubHeader("LONG BUILD UP(OI UP, PRICE UP)", longBuildupList));
                            openAnalysisHeaderList.add(new SubHeader("LONG BUILD UP(OI UP, PRICE UP)", longBuildupList));
                            adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "LONG BUILD UP(OI UP, PRICE UP)", longBuildupListToken);
                        }


                        if (shortBuildUpList.size() > 0) {

                            openAnalysisHeaderList.remove(new SubHeader("SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpList));
                            openAnalysisHeaderList.add(new SubHeader("SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpList));
                            adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpListToken);
                        }

                        if (longUnwindingList.size() > 0) {

                            openAnalysisHeaderList.remove(new SubHeader("LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingList));
                            openAnalysisHeaderList.add(new SubHeader("LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingList));
                            adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingListToken);
                        }


                        if (shortCoveringList.size() > 0) {
                            openAnalysisHeaderList.remove(new SubHeader("SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringList));
                            openAnalysisHeaderList.add(new SubHeader("SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringList));
                            adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringListToken);
                        }


                        mainHeadersList.clear();
                        mainHeadersList.add(new GainerMainHeader(subtopgainers, TOP_GAINERS));
                        mainHeadersList.add(new GainerMainHeader(subtoplosers, TOP_LOSER));
                        mainHeadersList.add(new GainerMainHeader(submarketmove, MARKET_MOVERS));
                        mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
                        expandableListView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        hideProgress();


                    }

                    hideProgress();
                }

                @Override
                public void onFailure(String message) {
                    hideProgress();
                }
            });
        }
    }


    private void sendIndianIndicesRequest() {
        streamController.sendIndianIndicesRequesForCommodityCurrency(getMainActivity(), "COMMODITY", serviceResponseHandler);
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

        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "getIndianIndicesDataForUserV2".equals(jsonResponse.getServiceName())) {
            try {

                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                ArrayList<IndianIndice> IndianIndiceList = new ArrayList<>();

                for (int i = 0; i < indianIndicesResponse.getIndianIndices().size(); i++) {


                    IndianIndiceList.add(indianIndicesResponse.getIndianIndices().get(i));

                    if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("0")) {
                        txt_nifty.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());
                        niftyValuetxt.setText(StringStuff.commaDecorator(indianIndicesResponse.getIndianIndices().get(i).getLtp()));
                        niftychangetxt.setText(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) + "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                        niftydayshightxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getHigh()))));
                        niftydaylowtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLow()))));

                        streamingForIndexList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());

                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("1")) {

                        txt_sensex.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());
                        sensexValuetxt.setText(StringStuff.commaDecorator(indianIndicesResponse.getIndianIndices().get(i).getLtp()));
                        sensexchangetxt.setText(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) + "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                        sensexdayshightxtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getHigh()))));
                        sensexdaylowtxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLow()))));

                        streamingForIndexList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());
                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("2")) {

                        firstSymbolNametxt.setText(IndianIndiceList.get(i).getToken());
                        firstvalue.setText(StringStuff.commaDecorator(IndianIndiceList.get(i).getLtp()));
                        firstchange.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange())) + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");
                        streamingForIndexList.add(IndianIndiceList.get(i).getIndexCode());

                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("3")) {

                        secondSymboltxt.setText(IndianIndiceList.get(i).getToken());
                        secondvalue.setText(StringStuff.commaDecorator(IndianIndiceList.get(i).getLtp()));
                        secondchange.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange())) + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");
                        streamingForIndexList.add(IndianIndiceList.get(i).getIndexCode());


                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("4")) {

                        thirdSymbolnametxt.setText(IndianIndiceList.get(i).getToken());
                        thirdvalue.setText(IndianIndiceList.get(i).getLtp());
                        thirdchange.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange())) + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");
                        streamingForIndexList.add(IndianIndiceList.get(i).getIndexCode());


                    }


                }


                updateBorderLineColor();
                sendStreamingIndexRequest("index");
                hideProgress();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_SVC_NAME_NEW.equals(jsonResponse.getServiceName())) {


        }

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_first_edit) {

            Bundle args = new Bundle();
            args.putString("imageid", "1");
            args.putString("assetType", "commodity");

            navigateTo(NAV_TO_MARKET_EDIT_FRAGMENT, args, true);
        }

        if (v.getId() == R.id.img_snd_edit) {
            Bundle args = new Bundle();
            args.putString("imageid", "2");
            args.putString("assetType", "commodity");

            navigateTo(NAV_TO_MARKET_EDIT_FRAGMENT, args, true);
        }
        if (v.getId() == R.id.img_thrd_edit) {
            Bundle args = new Bundle();
            args.putString("imageid", "3");
            args.putString("assetType", "commodity");

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
    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        sendPauseStreamingRequest();
        super.onFragmentPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        sendStreamingIndexRequest("index");

    }


    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        sendPauseStreamingRequest();
        super.onPause();

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

        if (streamingForIndexList != null) {
            if (streamingForIndexList.size() > 0) {
                streamController.pauseStreaming(getMainActivity(), type, streamingForIndexList);
            }
        }
        if (streamingForIndexList.size() > 0) {
            streamController.sendStreamingRequest(getMainActivity(), streamingForIndexList, type, null, null, false);
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
                    niftyValuetxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    niftychangetxt.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");

                    if (Double.parseDouble(broadcastResponse.getLast()) >
                            Double.parseDouble(niftydayshightxt.getText().toString().replace(",", ""))) {
                        niftydayshightxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    } else if (Double.parseDouble(broadcastResponse.getLast()) <
                            Double.parseDouble(niftydaylowtxt.getText().toString().replace(",", ""))) {
                        niftydaylowtxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    }
                }
                if (indexBrdName.equalsIgnoreCase(txt_sensex.getText().toString())) {
                    sensexValuetxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    sensexchangetxt.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");

                    if (Double.parseDouble(broadcastResponse.getLast()) >
                            Double.parseDouble(sensexdayshightxtxt.getText().toString().replace(",", ""))) {
                        sensexdayshightxtxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    } else if (Double.parseDouble(broadcastResponse.getLast()) <
                            Double.parseDouble(sensexdaylowtxt.getText().toString().replace(",", ""))) {
                        sensexdaylowtxt.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    }
                }
                if (indexBrdName.equalsIgnoreCase(firstSymbolNametxt.getText().toString())) {
                    firstvalue.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    firstchange.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");

                }
                if (indexBrdName.equalsIgnoreCase(secondSymboltxt.getText().toString())) {
                    secondvalue.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    secondchange.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                }
                if (indexBrdName.equalsIgnoreCase(thirdSymbolnametxt.getText().toString())) {
                    thirdvalue.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    thirdchange.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                }

                updateBorderLineColor();
            }


        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void expandAllGroups(int gpos) {
//TODO : For all Group expand by default by Rohit
        if (!adapter.isGroupExpanded(gpos)) {
            adapter.toggleGroup(gpos);
            adapter.notifyDataSetChanged();
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
    private String getExchangeFromToken(String token) {
        if (token.isEmpty()) {
            return "";
        } else {
            int tokenInt = Integer.parseInt(token);
            if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
                return "NSE";
            } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
                return "NCDEX";
            } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
                return "MCX";
            } else {
                return "BSE";
            }
        }
        //  return "";
    }


}

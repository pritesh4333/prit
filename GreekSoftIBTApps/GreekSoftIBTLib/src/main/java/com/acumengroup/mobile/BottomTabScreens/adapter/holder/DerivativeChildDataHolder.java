

package com.acumengroup.mobile.BottomTabScreens.adapter.holder;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BottomTabScreens.ClickListener;
import com.acumengroup.mobile.BottomTabScreens.NpaGridLayoutManager;
import com.acumengroup.mobile.BottomTabScreens.WrapContentLinearLayoutManager;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;
import com.acumengroup.mobile.BottomTabScreens.adapter.OptionChainhashData;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.DerivativeFNOChildAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.DerivativeRecycleChildAdapter_single;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.DerivativeRollChildAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.OptionChainAdapter;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.mobile.login.UserKycValidation;
import com.acumengroup.mobile.model.OIChangeData;
import com.acumengroup.mobile.model.OptionChainResponse;
import com.acumengroup.mobile.model.OptionchainRequest;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.InstantAutoComplete;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.ramotion.foldingcell.FoldingCell;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;


public class DerivativeChildDataHolder extends ChildViewHolder implements GreekConstants, GreekUIServiceHandler {

    private RecyclerView derivativechildRecyclerview, OIA_recyclerview, optionchainRecycler;
    private ScrollView scrollingView;
    private View strikeView, strikeView1;
    private DerivativeRecycleChildAdapter_single childAdapter;
    private DerivativeFNOChildAdapter fnochildAdapter;
    private DerivativeRollChildAdapter rollchildAdapter;
    private OptionChainAdapter optionAdapter;
    private GreekTextView txt_header, txt_OIA_header, emptyView, oia_empty_view;
    private ArrayList<GainerData> gainerDataList, fnoFilterGainerDataList;
    private ArrayList<OIChangeData> oiChangeDataList;
    private ArrayList<OptionChainhashData> optionChainhashData = new ArrayList<>();
    private MarketsIndianIndicesResponse indianIndicesResponse;
    private InstantAutoComplete spnrsymbol;
    private Spinner spinner_fno_expiry, spinnerInstName, spnrexpiry, spinnerFromStrike, spinnerToStrike;
    private TextView txt_nifty, txt_nifty_value, txt_nifty_change, txt_sensex, txt_sensex_value, txt_sensex_change, txt_rollOver, txt_rollOverCost, txt_rollOver2, txt_rollOverCost2, txtrollperName, txtrollcostNmae, txtrollper2Name, txtrollcost2Name, strikerange_name;
    private List<String> instuName, expiryList, symbolname, upperstrikePriceList, lowerstrikePriceList;
    private LinearLayout linear_OIA_header, listheaderlayout, bottomlayout, spinnerLayout, textviewLayout, parentLayout, optionChainLayout, two_layout;
    private RelativeLayout strikeLayout, parent_recyclerview_layout, parent_OIA_layout, progressLayout;
    private View lineview;
    private Button viewAll;
    private Context activity;
    private  JSONArray rollOverIndexArraylist = new JSONArray();

    private ArrayAdapter<String> expiryTypeAdapter, symbolAdapter, expiryAdater;
    private HashMap<String, String> expiry, longdate;
    private FoldingCell foldingCell;
    private ImageView img_icon_arrow, img_icon_up;
    private LinearLayout rollOverIndexLinearLayout, roll_layout, first_layout, linearnifty, linearniftybank, roll_per, roll_cost, roll_per2, roll_cost2, rollindex1, rollinde2, rollvolume, rollvolume2;
    private String key = null, ourToken = "", selectedExpiry;
    public ServiceResponseHandler serviceResponseHandler;
    private StreamingController streamController;
    private ArrayList streamingList = new ArrayList();
    private ArrayList<String> rollOverToken1List, rollOverToken2List, rollOverLTP1List, rollOverLTP2List, tokenlist, fnoexpiryList;
    private Double dLTp;
    private ExpandableGroup group;
    boolean isoirequestsen = false;
    private String symName, expiryDate, instrumentName, toStrike, fromStrike;
    private ArrayList<String> fnoTokenList, rolloverTokenList, OIAnalysysTokenList;
    private static ArrayList<String> tempFno = new ArrayList<>();
    private static ArrayList<String> temproll = new ArrayList<>();
    private static ArrayList<String> tempOIAnaluysis = new ArrayList<>();
    private Typeface font;
    public static JSONObject getDataRollOverIndexData;


    public DerivativeChildDataHolder(View itemView, JSONObject jsonObject) {
        super(itemView);
        this.getDataRollOverIndexData=jsonObject;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //Token list for three tab
        fnoTokenList = new ArrayList<>();
        rolloverTokenList = new ArrayList<>();
        OIAnalysysTokenList = new ArrayList<>();


        fnoexpiryList = new ArrayList<>();
        expiryList = new ArrayList<>();
        symbolname = new ArrayList<>();
        oiChangeDataList = new ArrayList<>();

        progressLayout = itemView.findViewById(R.id.customProgress);
        parent_recyclerview_layout = itemView.findViewById(R.id.parent_recyclerview_layout);
        parent_OIA_layout = itemView.findViewById(R.id.parent_OIA_layout);
        emptyView = itemView.findViewById(R.id.empty_view);
        oia_empty_view = itemView.findViewById(R.id.oia_empty_view);
        txt_header = itemView.findViewById(R.id.txt_header);
        txt_OIA_header = itemView.findViewById(R.id.txt_OIA_header);
        spinner_fno_expiry = itemView.findViewById(R.id.spinner_fno_expiry);
        derivativechildRecyclerview = itemView.findViewById(R.id.derivative_child_list);
        OIA_recyclerview = itemView.findViewById(R.id.OIA_recyclerview);
        scrollingView = itemView.findViewById(R.id.main_layout);
        strikeView = itemView.findViewById(R.id.strike_view);
        strikeView1 = itemView.findViewById(R.id.strike_view1);
        optionchainRecycler = itemView.findViewById(R.id.option_child_list);

        //derivativechildRecyclerview.setLayoutManager(new NpaGridLayoutManager(activity, LinearLayoutManager.VERTICAL));

        derivativechildRecyclerview.setLayoutManager(new NpaGridLayoutManager(activity, LinearLayoutManager.VERTICAL));

        derivativechildRecyclerview.setHasFixedSize(true);

        ((SimpleItemAnimator) derivativechildRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);

        //OIA_recyclerview.setLayoutManager(new NpaGridLayoutManager(activity, LinearLayoutManager.VERTICAL));
        OIA_recyclerview.setLayoutManager(new NpaGridLayoutManager(activity, LinearLayoutManager.VERTICAL));

        OIA_recyclerview.setHasFixedSize(true);

        ((SimpleItemAnimator) OIA_recyclerview.getItemAnimator()).setSupportsChangeAnimations(false);

        optionchainRecycler.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        optionchainRecycler.setHasFixedSize(true);
        ((SimpleItemAnimator) optionchainRecycler.getItemAnimator()).setSupportsChangeAnimations(false);


        spinner_fno_expiry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fnoFilterGainerDataList.clear();
                if (AccountDetails.getSelectedExpiryposition() != position) {
                    if (fnoexpiryList.size() > 0) {
                        selectedExpiry = fnoexpiryList.get(position);
                        AccountDetails.setSelectedExpiryposition(position);
                        EventBus.getDefault().post(selectedExpiry);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.e("DerivativeRoll","loadRollOverIndexData====DerivativeChildDataHolder========>>>>");

        //RollOver Sections==========================>

        rollOverIndexLinearLayout = itemView.findViewById(R.id.rollOverIndexLinearLayout);
        roll_layout = itemView.findViewById(R.id.roll_layout);
        first_layout = itemView.findViewById(R.id.first_layout);
        linearnifty = itemView.findViewById(R.id.linearnifty);
        linearniftybank = itemView.findViewById(R.id.linearNiftyBank);
        roll_per = itemView.findViewById(R.id.roll_per);
        roll_cost = itemView.findViewById(R.id.roll_cost);
        roll_per2 = itemView.findViewById(R.id.roll_per2);
        roll_cost2 = itemView.findViewById(R.id.roll_cost2);
        rollindex1 = itemView.findViewById(R.id.layout_roll_index1);
        rollvolume = itemView.findViewById(R.id.roll_volue1);
        rollinde2 = itemView.findViewById(R.id.layout_roll_index2);
        rollvolume2 = itemView.findViewById(R.id.roll_volume2);
        txt_nifty = itemView.findViewById(R.id.txt_nifty);
        txt_sensex = itemView.findViewById(R.id.txt_sensex);
        txt_nifty_value = itemView.findViewById(R.id.txt_nifty_value);
        txt_nifty_change = itemView.findViewById(R.id.txt_nifty_change);
        txt_sensex_value = itemView.findViewById(R.id.txt_sensex_value);
        strikerange_name = itemView.findViewById(R.id.ltpText);
        txt_sensex_change = itemView.findViewById(R.id.txt_sensex_change);
        txt_rollOver = itemView.findViewById(R.id.txt_rollOver);
        txt_rollOverCost = itemView.findViewById(R.id.txt_rollOverCost);
        txt_rollOver2 = itemView.findViewById(R.id.txt_rollOver2);
        txt_rollOverCost2 = itemView.findViewById(R.id.txt_rollOverCost2);
        txtrollperName = itemView.findViewById(R.id.txt_roll_name);
        txtrollcostNmae = itemView.findViewById(R.id.txt_rollcost_name);
        txtrollper2Name = itemView.findViewById(R.id.txt_rollper2_name);
        txtrollcost2Name = itemView.findViewById(R.id.txt_rollcost2_name);


        //Option Chain=============================>
        optionChainLayout = itemView.findViewById(R.id.optionChainLayout);
        two_layout = itemView.findViewById(R.id.two_layout);
        lineview = itemView.findViewById(R.id.view);
        spnrsymbol = itemView.findViewById(R.id.spnr_symbol);
        spinnerInstName = itemView.findViewById(R.id.spnr_exchange);
        spnrexpiry = itemView.findViewById(R.id.spnr_expiry);
        listheaderlayout = itemView.findViewById(R.id.header_layout_interest);
        linear_OIA_header = itemView.findViewById(R.id.linear_OIA_header);
        bottomlayout = itemView.findViewById(R.id.bottom_layout);
        viewAll = itemView.findViewById(R.id.btn_view_all);
        spinnerLayout = itemView.findViewById(R.id.one_layout);
        textviewLayout = itemView.findViewById(R.id.checkbox_layout_main);
        parentLayout = itemView.findViewById(R.id.parent_layout);
        strikeLayout = itemView.findViewById(R.id.strike_layout);
        spinnerFromStrike = itemView.findViewById(R.id.spnr_fromStrike);
        spinnerToStrike = itemView.findViewById(R.id.spnr_toStrike);
        foldingCell = itemView.findViewById(R.id.folding_cell);
        img_icon_arrow = itemView.findViewById(R.id.img_icon_arrow);
        img_icon_up = itemView.findViewById(R.id.img_icon_up);
        optionChainLayout.setVisibility(View.GONE);
        parent_recyclerview_layout.setVisibility(View.VISIBLE);
        parent_OIA_layout.setVisibility(View.VISIBLE);

        instuName = new ArrayList<>();
        instuName.add("Index");
        instuName.add("Stock");

        expiryList = new ArrayList<>();
        expiryList.add("Select Expiry");
        lowerstrikePriceList = new ArrayList<>();
        lowerstrikePriceList.add("Select To Strike");
        upperstrikePriceList = new ArrayList<>();
        upperstrikePriceList.add("Select From Strike");

        //LEFT/RIGHT Swipes for BUY/SELL===============>>>
        derivativechildRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(activity, derivativechildRecyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    if (!group.getTitle().equalsIgnoreCase("OPTION CHAIN")) {
                        Bundle args2 = new Bundle();
                        args2.putString(TradeFragment.SCRIP_NAME, gainerDataList.get(position).getName());
                        args2.putString(TradeFragment.EXCHANGE_NAME, gainerDataList.get(position).getExchange());
                        args2.putString(TradeFragment.TOKEN, gainerDataList.get(position).getToken());
                        args2.putString(TradeFragment.TICK_SIZE, gainerDataList.get(position).getTicksize());
                        args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                        AccountDetails.globalPlaceOrderBundle = args2;
                        EventBus.getDefault().post("placeorder");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        //LEFT/RIGHT Swipes for BUY/SELL===============>>>
        OIA_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(activity, derivativechildRecyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    if (!group.getTitle().equalsIgnoreCase("OPTION CHAIN")) {
                        Bundle args2 = new Bundle();
                        args2.putString(TradeFragment.SCRIP_NAME, gainerDataList.get(position).getName());
                        args2.putString(TradeFragment.EXCHANGE_NAME, gainerDataList.get(position).getExchange());
                        args2.putString(TradeFragment.TOKEN, gainerDataList.get(position).getToken());
                        args2.putString(TradeFragment.TICK_SIZE, gainerDataList.get(position).getTicksize());
                        args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                        AccountDetails.globalPlaceOrderBundle = args2;
                        EventBus.getDefault().post("placeorder");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



    public void onBind(final ArrayList<GainerData> gainerData, final ArrayList<String> expiryfList, String header,
                       final ExpandableGroup groups, final Context activity, final int flatPosition, final int index, ServiceResponseHandler serviceresponseHandler) {

        this.gainerDataList = gainerData;
        this.fnoFilterGainerDataList = new ArrayList<GainerData>();
        this.activity = activity;
        font = Typeface.createFromAsset(activity.getResources().getAssets(), "DaxOT.ttf");
        this.fnoexpiryList = expiryfList;
        this.group = groups;
        this.serviceResponseHandler = new ServiceResponseHandler(activity, this);
        this.streamingList = new ArrayList();
        this.streamController = new StreamingController();
        setUpThemes();

        if (fnoexpiryList != null && fnoexpiryList.size() > 0) {
            expiryTypeAdapter = new ArrayAdapter<String>(activity, AccountDetails.getRowSpinnerSimple(), fnoexpiryList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                        v.setTextColor(activity.getResources().getColor(R.color.black));
                    } else {
                        v.setTextColor(activity.getResources().getColor(R.color.white));
                    }
                    v.setPadding(15, 15, 15, 15);
                    return v;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    v.setTextColor(Color.BLACK);
                    v.setPadding(15, 15, 15, 15);
                    return v;
                }
            };
            expiryTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
            spinner_fno_expiry.setAdapter(expiryTypeAdapter);
            spinner_fno_expiry.setSelection(AccountDetails.getSelectedExpiryposition());
        }


        expiry = new HashMap<>();
        longdate = new HashMap<>();
        enableLeftRightSwipe(group);

//This is for TAB groups records
        try {
            if (groups.getTitle().equalsIgnoreCase("Option Chain")) {

                optionChainLayout.setVisibility(View.VISIBLE);
                strikeLayout.setVisibility(View.GONE);
                parent_recyclerview_layout.setVisibility(View.GONE);
                parent_OIA_layout.setVisibility(View.GONE);
                foldingCell.isUnfolded();
                txt_header.setVisibility(View.GONE);
                spinner_fno_expiry.setVisibility(View.GONE);
                rollOverIndexLinearLayout.setVisibility(View.GONE);
                roll_layout.setVisibility(View.GONE);

                img_icon_arrow.setVisibility(View.VISIBLE);
                img_icon_up.setVisibility(View.VISIBLE);
                listheaderlayout.setVisibility(View.VISIBLE);
                bottomlayout.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.VISIBLE);
                textviewLayout.setVisibility(View.VISIBLE);
                parentLayout.setVisibility(View.VISIBLE);
                parent_OIA_layout.setVisibility(View.GONE);


                symName = Util.getPrefs(activity).getString("symbol", "");
                expiryDate = Util.getPrefs(activity).getString("expiry", "");
                instrumentName = Util.getPrefs(activity).getString("instrumentname", "");
                toStrike = Util.getPrefs(activity).getString("tostrike", "");
                fromStrike = Util.getPrefs(activity).getString("fromstrike", "");


                if (instrumentName.equalsIgnoreCase("index") || instrumentName.equalsIgnoreCase("")) {
                    showProgress();
                    loadOptionChainAllSpinnerData("getSymbolForInstrument?instName=FUTIDX", "symbol");
                } else {
                    loadOptionChainAllSpinnerData("getSymbolForInstrument?instName=FUTSTK", "symbol");
                }


                viewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        strikeLayout.setVisibility(View.VISIBLE);
                        foldingCell.toggle(true);
                    }
                });

                toggleErrorLayout(false);
//                derivativechildRecyclerview.getRecycledViewPool().clear();
                optionAdapter = new OptionChainAdapter(activity, R.layout.fragment_option_chain_row, optionChainhashData);
                ((SimpleItemAnimator) optionchainRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                optionchainRecycler.setAdapter(optionAdapter);


                lineview.setVisibility(View.GONE);
                spnrsymbol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //                    if(symbolname.size() > 0) {
                        spnrsymbol.showDropDown();
                        //                    }
                    }
                });

                spnrsymbol.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() == 0) {
                            if (symbolname.size() > 0) {
                                //                            spnrsymbol.showDropDown();
                            }
                            if (expiryList.size() > 0) {
                                expiryList.clear();
                                expiryList.add("Select Expiry");
                                expiryAdater.notifyDataSetChanged();
                                spnrexpiry.setSelection(0);
                            }

                            if (optionChainhashData.size() > 0) {
                                if (optionAdapter != null) {
                                    optionChainhashData.clear();
                                    derivativechildRecyclerview.getRecycledViewPool().clear();
                                    optionAdapter.notifyDataSetChanged();
                                }
                                if (upperstrikePriceList.size() > 0) {
                                    upperstrikePriceList.clear();
                                    lowerstrikePriceList.clear();
                                    upperstrikePriceList.add("Select From Strike");
                                    lowerstrikePriceList.add("Select TO Strike");
                                    spinnerFromStrike.setSelection(0);
                                    spinnerToStrike.setSelection(0);
                                }

                            }
                            symName = "";
                            expiryDate = "";
                            instrumentName = "";
                            fromStrike = "";
                            toStrike = "";


                        }

                    }
                });
                spnrsymbol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (expiryList.size() > 0) {
                            spnrexpiry.setSelection(0);
                        }

                        symName = "";
                        expiryDate = "";
                        instrumentName = "";
                        fromStrike = "";
                        toStrike = "";


                        if (optionChainhashData.size() > 0) {
                            if (optionAdapter != null) {
                                optionChainhashData.clear();
                                derivativechildRecyclerview.getRecycledViewPool().clear();
                                optionAdapter.notifyDataSetChanged();
                            }
                            if (upperstrikePriceList.size() > 0) {
                                upperstrikePriceList.clear();
                                lowerstrikePriceList.clear();
                                upperstrikePriceList.add("Select From Strike");
                                lowerstrikePriceList.add("Select TO Strike");
                                spinnerFromStrike.setSelection(0);
                                spinnerToStrike.setSelection(0);
                            }
                        }

                        hideKeyboard(activity);
                        showProgress();
                        loadOptionChainAllSpinnerData("getexpiryRequest?cSymbol=" + spnrsymbol.getText().toString(), "expiry");

                    }

                });
                ArrayAdapter<String> instumentAdapter = new ArrayAdapter<String>(activity, AccountDetails.getRowSpinnerSimple(), instuName) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                            v.setTextColor(activity.getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(activity.getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                instumentAdapter.setDropDownViewResource(R.layout.custom_spinner);
                spinnerInstName.setAdapter(instumentAdapter);
                spinnerInstName.setOnItemSelectedListener(instselectListener);

                if (!instrumentName.equalsIgnoreCase("")) {

                    if (instuName.contains(instrumentName)) {
                        spinnerInstName.setSelection(instuName.indexOf(instrumentName));
                    }

                }
                expiryAdater = new ArrayAdapter<String>(activity, AccountDetails.getRowSpinnerSimple(), expiryList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (expiryfList != null) {
                            TextView v = (TextView) super.getView(position, convertView, parent);
                            v.setTypeface(font);
                            if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                                v.setTextColor(activity.getResources().getColor(R.color.black));
                            } else {
                                v.setTextColor(activity.getResources().getColor(R.color.white));
                            }
                            v.setPadding(15, 15, 15, 15);
                            return v;
                        }
                        return null;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        if (expiryfList != null) {
                            TextView v = (TextView) super.getView(position, convertView, parent);
                            v.setTypeface(font);
                            v.setTextColor(Color.BLACK);
                            v.setPadding(15, 15, 15, 15);
                            return v;
                        }
                        return null;
                    }
                };
                expiryAdater.setDropDownViewResource(R.layout.custom_spinner);
                spnrexpiry.setAdapter(expiryAdater);

                ArrayAdapter<String> fromStrikeAdapter = new ArrayAdapter<String>(activity, AccountDetails.getRowSpinnerSimple(), upperstrikePriceList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                            v.setTextColor(activity.getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(activity.getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                fromStrikeAdapter.setDropDownViewResource(R.layout.custom_spinner);
                spinnerFromStrike.setAdapter(fromStrikeAdapter);
                spinnerFromStrike.setOnItemSelectedListener(lowerstrikeselectListener);

                ArrayAdapter<String> toStrikeAdapter = new ArrayAdapter<String>(activity, AccountDetails.getRowSpinnerSimple(), lowerstrikePriceList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                            v.setTextColor(activity.getResources().getColor(R.color.black));
                        } else {
                            v.setTextColor(activity.getResources().getColor(R.color.white));
                        }
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView v = (TextView) super.getView(position, convertView, parent);
                        v.setTypeface(font);
                        v.setTextColor(Color.BLACK);
                        v.setPadding(15, 15, 15, 15);
                        return v;
                    }
                };
                toStrikeAdapter.setDropDownViewResource(R.layout.custom_spinner);
                spinnerToStrike.setAdapter(toStrikeAdapter);
                spinnerToStrike.setOnItemSelectedListener(strikeselectListener);

            }
            else if (groups.getTitle().equalsIgnoreCase("ROLL OVER")) {

                progressLayout.setVisibility(View.GONE);
                optionChainLayout.setVisibility(View.GONE);
                strikeLayout.setVisibility(View.GONE);
                lineview.setVisibility(View.GONE);
                textviewLayout.setVisibility(View.GONE);

                listheaderlayout.setVisibility(View.GONE);
                bottomlayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.GONE);
                img_icon_arrow.setVisibility(View.GONE);
                img_icon_up.setVisibility(View.GONE);

                parentLayout.setVisibility(View.VISIBLE);
                parent_OIA_layout.setVisibility(View.GONE);
                parent_recyclerview_layout.setVisibility(View.VISIBLE);
                parent_OIA_layout.setVisibility(View.GONE);
                spinner_fno_expiry.setVisibility(View.GONE);

                txt_header.setVisibility(View.VISIBLE);
                txt_header.setText(header);

                rollOverIndexLinearLayout.setVisibility(View.GONE);

                if (header.equalsIgnoreCase("Highest Rollovers")) {
                    if (rollOverIndexLinearLayout.getVisibility() != View.VISIBLE) {
                        rollOverIndexLinearLayout.setVisibility(View.VISIBLE);
                    }

                } else {
                    rollOverIndexLinearLayout.setVisibility(View.GONE);
                }

                streamController.sendIndianIndicesRequest(activity, serviceResponseHandler);
                roll_layout.setVisibility(View.VISIBLE);

//                if (rollOverIndexArraylist == null) {
                if(rollOverIndexArraylist.length()<1) {
                    loadRollOverIndexData();
                }
//                }

                if (gainerDataList.size() <= 1 && header.equalsIgnoreCase("Highest Rollovers")) {
                    toggleErrorLayout(true);
                } else if (gainerDataList.size() <= 1 && header.equalsIgnoreCase("Lowest Rollovers")) {
                    toggleErrorLayout(true);
                } else {
                    toggleErrorLayout(false);

                    rollchildAdapter = new DerivativeRollChildAdapter(gainerDataList,
                            activity, groups.getTitle(), index);
                    derivativechildRecyclerview.setAdapter(rollchildAdapter);
                    derivativechildRecyclerview.getRecycledViewPool().clear();

                    for (int i = 0; i < gainerDataList.size(); i++) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(gainerDataList.get(i).getToken1(), gainerDataList.get(i).getToken2());
                        rollchildAdapter.AddToken(gainerDataList.get(i).getToken());
                        rollchildAdapter.AddToken2(gainerDataList.get(i).getToken2());
                        rolloverTokenList.add(gainerDataList.get(i).getToken());
                        rolloverTokenList.add(gainerDataList.get(i).getToken2());
                        temproll.add(gainerDataList.get(i).getToken());
                        temproll.add(gainerDataList.get(i).getToken2());
                    }

                    sendRollSubscribe();
                }


            } else if (groups.getTitle().equalsIgnoreCase("FNO ACTIVITY")) {

                if (flatPosition == 1) {
                    spinner_fno_expiry.setVisibility(View.VISIBLE);

                } else {
                    spinner_fno_expiry.setVisibility(View.GONE);
                }

                optionChainLayout.setVisibility(View.GONE);
                rollOverIndexLinearLayout.setVisibility(View.GONE);
                roll_layout.setVisibility(View.GONE);
                strikeLayout.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                txt_header.setVisibility(View.VISIBLE);
                parent_recyclerview_layout.setVisibility(View.VISIBLE);
                parent_OIA_layout.setVisibility(View.GONE);
                txt_header.setText(header);

                lineview.setVisibility(View.GONE);
                textviewLayout.setVisibility(View.GONE);

                listheaderlayout.setVisibility(View.GONE);
                bottomlayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.GONE);
                img_icon_arrow.setVisibility(View.GONE);
                img_icon_up.setVisibility(View.GONE);


                parentLayout.setVisibility(View.VISIBLE);
                parent_OIA_layout.setVisibility(View.GONE);


                if (gainerDataList.size() <= 1 && header.equalsIgnoreCase("MOST ACTIVE(FUTURES)")) {
                    toggleErrorLayout(true);
                } else if (gainerDataList.size() <= 1 && header.equalsIgnoreCase("MOST ACTIVE(OPTIONS INDEX)")) {
                    toggleErrorLayout(true);
                } else if (gainerDataList.size() <= 1 && header.equalsIgnoreCase("MOST ACTIVE(OPTIONS STOCK)")) {
                    toggleErrorLayout(true);
                } else {
                    toggleErrorLayout(false);
                    fnochildAdapter = new DerivativeFNOChildAdapter(gainerDataList, activity, groups.getTitle(), header, index);
                    derivativechildRecyclerview.setAdapter(fnochildAdapter);
                    derivativechildRecyclerview.getRecycledViewPool().clear();

                    for (int i = 0; i < gainerDataList.size(); i++) {
                        fnoTokenList.add(gainerDataList.get(i).getToken());
                        tempFno.add(gainerDataList.get(i).getToken());
                    }
                    sendFNOSubscribe();
                }

            } else if (groups.getTitle().equalsIgnoreCase("OPEN INTEREST ANALYSIS")) {
                //this for open interest Analysis tab
                progressLayout.setVisibility(View.GONE);
                spinner_fno_expiry.setVisibility(View.GONE);
                rollOverIndexLinearLayout.setVisibility(View.GONE);
                roll_layout.setVisibility(View.GONE);
                strikeLayout.setVisibility(View.GONE);
                optionChainLayout.setVisibility(View.GONE);
                parent_recyclerview_layout.setVisibility(View.GONE);
                parent_OIA_layout.setVisibility(View.VISIBLE);
                txt_header.setVisibility(View.GONE);

                txt_OIA_header.setVisibility(View.VISIBLE);
                txt_OIA_header.setText(header);
                lineview.setVisibility(View.GONE);
                textviewLayout.setVisibility(View.GONE);

                listheaderlayout.setVisibility(View.GONE);
                bottomlayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.GONE);
                img_icon_arrow.setVisibility(View.GONE);
                img_icon_up.setVisibility(View.GONE);


                parentLayout.setVisibility(View.VISIBLE);
                parent_OIA_layout.setVisibility(View.VISIBLE);


                if (gainerDataList.size() <= 1 && gainerDataList.get(0).getDescription().length() <= 0 && header.equalsIgnoreCase("LONG BUILD UP(OI UP, PRICE UP)")) {
                    OIA_recyclerview.setVisibility(View.GONE);
                    oia_empty_view.setVisibility(View.VISIBLE);
                } else if (gainerDataList.size() <= 1 && gainerDataList.get(0).getDescription().length() <= 0 && header.equalsIgnoreCase("SHORT BUILD UP(OI UP, PRICE DOWN)")) {
                    OIA_recyclerview.setVisibility(View.GONE);
                    oia_empty_view.setVisibility(View.VISIBLE);
                } else if (gainerDataList.size() <= 1 && gainerDataList.get(0).getDescription().length() <= 0 && header.equalsIgnoreCase("LONG UNWINDING(OI DOWN, PRICE DOWN)")) {
                    OIA_recyclerview.setVisibility(View.GONE);
                    oia_empty_view.setVisibility(View.VISIBLE);
                } else if (gainerDataList.size() <= 1 && gainerDataList.get(0).getDescription().length() <= 0 && header.equalsIgnoreCase("SHORT COVERING (OI DOWN, PRICE UP)")) {
                    OIA_recyclerview.setVisibility(View.GONE);
                    oia_empty_view.setVisibility(View.VISIBLE);
                } else {
                    OIA_recyclerview.setVisibility(View.VISIBLE);
                    oia_empty_view.setVisibility(View.GONE);

                    try {
                        childAdapter = new DerivativeRecycleChildAdapter_single(gainerDataList, activity, groups.getTitle(), header, 0, OIA_recyclerview);
                        OIA_recyclerview.setAdapter(childAdapter);
                        OIA_recyclerview.getRecycledViewPool().clear();
                        OIA_recyclerview.setItemAnimator(null);
                    } catch (Exception e) {
                        Log.e("Exception >>>>>>>>>>", e + "");
                        e.printStackTrace();
                    }


                    for (int i = 0; gainerDataList.size() > i; i++) {
                        childAdapter.addToken(gainerDataList.get(i).getToken());
                        if (!OIAnalysysTokenList.contains(gainerDataList.get(i).getToken())) {
                            OIAnalysysTokenList.add(gainerDataList.get(i).getToken());
                            tempOIAnaluysis.add(gainerDataList.get(i).getToken());
                        }
                    }
                    sendOIAnalysisSubscribe();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            onBindUpdateData(streamingResponse, activity);
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void onEventMainThread(String status) {

        if (status.equalsIgnoreCase("closefno")) {
            if (tempFno.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", tempFno);
                tempFno.clear();
                fnoTokenList.clear();
            }
        }
        if (status.equalsIgnoreCase("closeroll")) {
            if (temproll.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", temproll);
                temproll.clear();
                rolloverTokenList.clear();
            }
        }
        if (status.equalsIgnoreCase("close")) {
            if (tempOIAnaluysis.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", tempOIAnaluysis);
                tempOIAnaluysis.clear();
                OIAnalysysTokenList.clear();

            }
        }


    }

    public void onBindUpdateData(StreamingResponse streamingResponse, Context activity) {

        this.activity = activity;
        try {
            if (streamingResponse.getStreamingType().equals("index")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());

                if (broadcastResponse.getName().equalsIgnoreCase(txt_nifty.getText().toString())) {
                    txt_nifty_value.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    txt_nifty_change.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                }
                if (broadcastResponse.getName().equalsIgnoreCase(txt_sensex.getText().toString())) {
                    txt_sensex_value.setText(StringStuff.commaDecorator(broadcastResponse.getLast()));
                    txt_sensex_change.setText(String.format("%.2f", Double.parseDouble(broadcastResponse.getChange())) + "(" + String.format("%.2f", Double.parseDouble(broadcastResponse.getP_change())) + "%)");
                }
                updateBorderLineColor();
            }

            if (streamingResponse.getStreamingType().equals("ltpinfo")) {

                StreamerBroadcastResponse response = new StreamerBroadcastResponse();
                response.fromJSON(streamingResponse.getResponse());

                if (rollOverToken1List != null && rollOverToken1List.get(0).equalsIgnoreCase(response.getSymbol())) {


                    rollOverLTP1List.set(0, response.getLast());

                    Double ltp1 = Double.parseDouble(response.getLast());
                    Double ltp2 = Double.parseDouble(rollOverLTP2List.get(0));

                    Double rolloverCost = ltp2 - ltp1;
                    Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                    txt_rollOverCost.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");


                } else if (rollOverToken1List != null && rollOverToken1List.get(1).equalsIgnoreCase(response.getSymbol())) {


                    rollOverLTP1List.set(1, response.getLast());
                    Double ltp1 = Double.parseDouble(response.getLast());
                    Double ltp2 = Double.parseDouble(rollOverLTP2List.get(1));
                    Double rolloverCost = ltp2 - ltp1;
                    Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                    txt_rollOverCost2.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");


                } else if (rollOverToken1List != null && rollOverToken2List.get(0).equalsIgnoreCase(response.getSymbol())) {


                    rollOverLTP2List.set(0, response.getLast());

                    Double ltp1 = Double.parseDouble(rollOverLTP1List.get(0));
                    Double ltp2 = Double.parseDouble(response.getLast());

                    Double rolloverCost = ltp2 - ltp1;
                    Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                    txt_rollOverCost.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");


                } else if (rollOverToken1List != null && rollOverToken2List.get(1).equalsIgnoreCase(response.getSymbol())) {


                    rollOverLTP2List.set(1, response.getLast());
                    Double ltp1 = Double.parseDouble(rollOverLTP1List.get(1));
                    Double ltp2 = Double.parseDouble(response.getLast());

                    Double rolloverCost = ltp2 - ltp1;
                    Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                    txt_rollOverCost2.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");


                }


                updateBroadcastData(response);

            }

            if (streamingResponse.getStreamingType().equals("touchline")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }


        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    private void updateBroadcastData(StreamerBroadcastResponse response) {

        if (childAdapter != null && childAdapter.containsSymbol(response.getSymbol())) {
            boolean notifyNeeded = false;

            GainerData data = childAdapter.getItem(childAdapter.indexOf(response.getSymbol()));

            if (!data.getLtp().equals(response.getLast())) {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }
                notifyNeeded = true;
            } else {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }
            }

            if (!data.getChange().equals(response.getChange())) {
                if (response.getSymbol().contains("502") || response.getSymbol().contains("1302")) {
                    data.setChange(String.format("%.4f", Double.parseDouble(response.getChange())));
                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(response.getChange())));
                }
                notifyNeeded = true;
            } else {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setChange(String.format("%.4f", Double.parseDouble(data.getChange())));

                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(data.getChange())));
                }

            }

            if (!data.getPerchange().equals(response.getP_change())) {
                data.setPerchange(String.format("%.2f", Double.parseDouble(response.getP_change())));
                notifyNeeded = true;
            } else {
                data.setPerchange(String.format("%.2f", Double.parseDouble(data.getPerchange())));
            }
            childAdapter.updateData(childAdapter.indexOf(response.getSymbol()), data);
            derivativechildRecyclerview.getRecycledViewPool().clear();
            childAdapter.notifyDataSetChanged();

        }

    }


    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        spnrsymbol.dismissDropDown();
        streamingList.clear();
        tokenlist = new ArrayList<>();

        try {

            if (jsonResponse.getServiceName().equalsIgnoreCase(INDIAN_INDICES_SVC_NAME)) {
                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                updateAdapterSize();

            } else if (jsonResponse.getServiceName().equalsIgnoreCase(OPTION_CHAIN_SVC_NAME)) {
                try {
                    isoirequestsen = false;
                    OptionChainResponse optionChainResponse = (OptionChainResponse) jsonResponse.getResponse();
                    HashMap<String, OptionChainhashData> hmoptionchain = new HashMap<>();
                    optionChainhashData.clear();
                    optionAdapter = new OptionChainAdapter(activity, R.layout.fragment_option_chain_row, optionChainhashData);
                    ((SimpleItemAnimator) optionchainRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                    optionchainRecycler.setAdapter(optionAdapter);
                    tokenlist = new ArrayList<>();


                    for (int i = 0; i < optionChainResponse.getData().size(); i++) {

                        OptionChainhashData objopchain = new OptionChainhashData();
                        if (hmoptionchain.get(optionChainResponse.getData().get(i).getStrike()) != null)
                            objopchain = hmoptionchain.get(optionChainResponse.getData().get(i).getStrike());
                        Log.v("Strike priice CE -- ", optionChainResponse.getData().get(i).getStrike());
                        if (optionChainResponse.getData().get(i).getCallput().equalsIgnoreCase("CE")) {

                            String date = (Long.parseLong(key)/* + 315513000*/) + "";
                            if ((date.equalsIgnoreCase(optionChainResponse.getData().get(i).getExpiryDate()))) {
                                objopchain.setCallput_call(optionChainResponse.getData().get(i).getCallput());
                                objopchain.setdLtp_call(optionChainResponse.getData().get(i).getdLtp());
                                objopchain.setlOurToken_call(optionChainResponse.getData().get(i).getlOurToken());
                                objopchain.setStrike(optionChainResponse.getData().get(i).getStrike());
                                objopchain.setlOpenInterest_call(optionChainResponse.getData().get(i).getlOpenInterest());
                                objopchain.setlPrevOpenInterest_call(optionChainResponse.getData().get(i).getlPrevOpenInterest());

                                objopchain.setdNetChange_call(optionChainResponse.getData().get(i).getdNetChange());
                                objopchain.setlVolume_call(optionChainResponse.getData().get(i).getlVolume());
                                if (!optionChainResponse.getData().get(i).getdLtp().equals("")) {
                                    objopchain.setdLtp_call(optionChainResponse.getData().get(i).getdLtp());
                                } else {
                                    objopchain.setdLtp_call("0.00");
                                }
                                objopchain.setColor_call("normal");

                                tokenlist.add(optionChainResponse.getData().get(i).getlOurToken());

                                Log.v("Strike price CE -- ", optionChainResponse.getData().get(i).getStrike());

                                hmoptionchain.put(optionChainResponse.getData().get(i).getStrike(), objopchain);
                            }


                        } else if (optionChainResponse.getData().get(i).getCallput().equalsIgnoreCase("PE")) {
                            String date = (Long.parseLong(key) /*+ 315513000*/) + "";
                            if ((date.equalsIgnoreCase(optionChainResponse.getData().get(i).getExpiryDate()))) {
                                objopchain.setlOurToken_put(optionChainResponse.getData().get(i).getlOurToken());
                                objopchain.setdLtp_put(optionChainResponse.getData().get(i).getdLtp());
                                objopchain.setCallput_put(optionChainResponse.getData().get(i).getCallput());
                                objopchain.setStrike(optionChainResponse.getData().get(i).getStrike());
                                objopchain.setlOpenInterest_put(optionChainResponse.getData().get(i).getlOpenInterest());
                                objopchain.setdNetChange_put(optionChainResponse.getData().get(i).getdNetChange());
                                objopchain.setlVolume_put(optionChainResponse.getData().get(i).getlVolume());
                                objopchain.setlPrevOpenInterest_put(optionChainResponse.getData().get(i).getlPrevOpenInterest());
                                if (!optionChainResponse.getData().get(i).getdLtp().equals("")) {
                                    objopchain.setdLtp_put(optionChainResponse.getData().get(i).getdLtp());
                                } else {
                                    objopchain.setdLtp_put("0.00");
                                }
                                objopchain.setColor_put("normal");

                                tokenlist.add(optionChainResponse.getData().get(i).getlOurToken());
                                hmoptionchain.put(optionChainResponse.getData().get(i).getStrike(), objopchain);

                                Log.v("Strike priice PE -- ", optionChainResponse.getData().get(i).getStrike());


                            }

                        }

                    }


                    TreeMap<String, OptionChainhashData> sorted = new TreeMap<>(hmoptionchain);

                    optionChainhashData.addAll(sorted.values());
                    for (int i = 0; i < optionChainhashData.size(); i++) {
                        if (optionChainhashData.get(i).getlOurToken_call() != null) {
                            optionAdapter.AddTokenCE(optionChainhashData.get(i).getlOurToken_call());
                        }

                        if (optionChainhashData.get(i).getlOurToken_put() != null) {
                            optionAdapter.AddTokenPE(optionChainhashData.get(i).getlOurToken_put());
                        }

                    }
                    // derivativechildRecyclerview.getRecycledViewPool().clear();
                    optionAdapter.notifyDataSetChanged();
                    sendStreamingRequest();
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAdapterSize() {

        IndianIndice indianIndice = new IndianIndice();

        if (indianIndicesResponse != null) {

            for (int i = 0; i < indianIndicesResponse.getIndianIndices().size(); i++) {

                indianIndice = indianIndicesResponse.getIndianIndices().get(i);

                if (indianIndice.getToken().equalsIgnoreCase("NIFTY 50")) {
                    txt_nifty_change.setText(String.format("%.2f", Double.parseDouble(indianIndice.getChange())) + "(" + String.format("%.2f", Double.parseDouble(indianIndice.getP_change())) + "%)");
                    txt_nifty_value.setText(StringStuff.commaDecorator(indianIndice.getLtp()));
                    // txt_rollOver.setText(String.format("%.2f", Double.parseDouble(indianIndice.getP_change())));
                    // txt_rollOverCost.setText(String.format("%.2f", Double.parseDouble(indianIndice.getP_change())));
                    streamingList.add(indianIndice.getIndexCode());
                }

                if (indianIndice.getToken().equalsIgnoreCase("Nifty Bank")) {


                    txt_sensex_change.setText(String.format("%.2f", Double.parseDouble(indianIndice.getChange())) + "(" + String.format("%.2f", Double.parseDouble(indianIndice.getP_change())) + "%)");
                    txt_sensex_value.setText(StringStuff.commaDecorator(indianIndice.getLtp()));
                    // txt_rollOver2.setText(String.format("%.2f", Double.parseDouble(indianIndice.getP_change())));
                    //  txt_rollOverCost2.setText(String.format("%.2f", Double.parseDouble(indianIndice.getP_change())));
                    streamingList.add(indianIndice.getIndexCode());

                }
            }
            sendStreamingRequest("index");
            updateBorderLineColor();
        }

    }

    public void loadRollOverIndexData() {

        try {
            rollOverIndexArraylist = getDataRollOverIndexData.getJSONArray("data");

            rollOverToken1List = new ArrayList<>();
            rollOverToken2List = new ArrayList<>();

            rollOverLTP1List = new ArrayList<>();
            rollOverLTP2List = new ArrayList<>();

            streamingList.clear();
            rollOverToken1List.clear();
            rollOverToken2List.clear();
            rollOverLTP1List.clear();
            rollOverLTP2List.clear();


            if (rollOverIndexArraylist != null) {

                for (int i = 0; i < rollOverIndexArraylist.length(); i++) {
                    JSONObject jsonObject = rollOverIndexArraylist.getJSONObject(i);

                    if (jsonObject.getString("symbol").equalsIgnoreCase("NIFTY")) {

                        Double ltp = Double.parseDouble(jsonObject.getString("ltp"));
                        Double ltp1 = Double.parseDouble(jsonObject.getString("Ltp1"));
                        Double ltp2 = Double.parseDouble(jsonObject.getString("Ltp2"));

                        Double rolloverCost = ltp2 - ltp1;
                        Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                        txt_rollOver.setText(String.format("%.2f", ltp));
                        txt_rollOverCost.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");

                        streamingList.add(jsonObject.getString("token1"));
                        streamingList.add(jsonObject.getString("token2"));

                        rollOverToken1List.add(0, jsonObject.getString("token1"));
                        rollOverToken2List.add(0, jsonObject.getString("token2"));

                        rollOverLTP1List.add(0, jsonObject.getString("Ltp1"));
                        rollOverLTP2List.add(0, jsonObject.getString("Ltp2"));

                    }

                    if (jsonObject.getString("symbol").equalsIgnoreCase("BANKNIFTY")) {

                        Double ltp = Double.parseDouble(jsonObject.getString("ltp"));
                        Double ltp1 = Double.parseDouble(jsonObject.getString("Ltp1"));
                        Double ltp2 = Double.parseDouble(jsonObject.getString("Ltp2"));
                        Double rolloverCost = ltp2 - ltp1;
                        Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                        txt_rollOver2.setText(String.format("%.2f", ltp));
                        txt_rollOverCost2.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");

                        streamingList.add(jsonObject.getString("token1"));
                        streamingList.add(jsonObject.getString("token2"));

                        rollOverToken1List.add(1, jsonObject.getString("token1"));
                        rollOverToken2List.add(1, jsonObject.getString("token2"));

                        rollOverLTP1List.add(1, jsonObject.getString("Ltp1"));
                        rollOverLTP2List.add(1, jsonObject.getString("Ltp2"));
                    }

                }
                sendStreamingRequest("ltpinfo");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    private void LoadRollOverIndexData() {

        try {

            for (int i = 0; i < rollOverIndexArraylist.length(); i++) {
                JSONObject jsonObject = rollOverIndexArraylist.getJSONObject(i);

                if (jsonObject.getString("symbol").equalsIgnoreCase("NIFTY")) {

                    Double ltp = Double.parseDouble(jsonObject.getString("ltp"));
                    Double ltp1 = Double.parseDouble(jsonObject.getString("Ltp1"));
                    Double ltp2 = Double.parseDouble(jsonObject.getString("Ltp2"));

                    Double rolloverCost = ltp2 - ltp1;
                    Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                    txt_rollOver.setText(String.format("%.2f", ltp));
                    txt_rollOverCost.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");


                }

                if (jsonObject.getString("symbol").equalsIgnoreCase("BANKNIFTY")) {

                    Double ltp = Double.parseDouble(jsonObject.getString("ltp"));
                    Double ltp1 = Double.parseDouble(jsonObject.getString("Ltp1"));
                    Double ltp2 = Double.parseDouble(jsonObject.getString("Ltp2"));
                    Double rolloverCost = ltp2 - ltp1;
                    Double rolloverCostPer = ((ltp2 - ltp1) / ltp1) * 100;


                    txt_rollOver2.setText(String.format("%.2f", ltp));
                    txt_rollOverCost2.setText(String.format("%.2f", rolloverCost) + "(" + String.format("%.2f", rolloverCostPer) + "%)");


                }

            }


        } catch (Exception e) {

        }

    }

    private void sendStreamingRequest(String type) {

        if (streamingList != null) {
            if (streamingList.size() > 0) {
                streamController.pauseStreaming(activity, type, streamingList);
            }
            streamController.sendStreamingRequest(activity, streamingList, type, null, null, false);
        }

    }

    private void sendFNOSubscribe() {


        if (fnoTokenList != null)
            if (fnoTokenList.size() > 0) {
//                streamController.pauseStreaming(activity, "ltpinfo", tempFno);
                if (fnoTokenList.size() < 11) {
                    streamController.sendStreamingRequest(activity, fnoTokenList, "ltpinfo", null, null, false);
                } else {
                    fnoTokenList.clear();
                }


            }


    }

    private void sendRollSubscribe() {

        if (rolloverTokenList != null)
            if (rolloverTokenList.size() > 0) {
//            streamController.pauseStreaming(activity, "ltpinfo", rolloverTokenList);
                if (rolloverTokenList.size() < 21) {
                    streamController.sendStreamingRequest(activity, rolloverTokenList, "ltpinfo", null, null, false);
                } else {
                    rolloverTokenList.clear();
                }

            }


    }

    private void sendOIAnalysisSubscribe() {

        if (OIAnalysysTokenList != null)
            if (OIAnalysysTokenList.size() > 0) {
//            streamController.pauseStreaming(activity, "ltpinfo", OIAnalysysTokenList);
                if (OIAnalysysTokenList.size() < 11) {
                    streamController.sendStreamingRequest(activity, OIAnalysysTokenList, "ltpinfo", null, null, false);
                } else {
                    OIAnalysysTokenList.clear();
                }

            }
    }

    private void sendStreamingRequest() {

        if (tokenlist != null) {
            if (tokenlist.size() > 0) {
                streamController.pauseStreaming(activity, "touchline", tokenlist);
                streamController.sendStreamingRequest(activity, tokenlist, "touchline", null, null, false);

            }
        }
    }

    private void setUpThemes() {
        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")) {
            scrollingView.setBackgroundColor(activity.getColor(R.color.white));
            derivativechildRecyclerview.setBackgroundColor(activity.getColor(R.color.white));
            derivativechildRecyclerview.addItemDecoration(new SimpleDividerItemDecoration(activity));
            optionchainRecycler.setBackgroundColor(activity.getColor(R.color.white));
            parentLayout.setBackgroundColor(activity.getColor(R.color.white));
            spnrsymbol.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
            emptyView.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
            oia_empty_view.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
            strikerange_name.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
            img_icon_arrow.setImageDrawable(activity.getResources().getDrawable(R.drawable.white_more));
            img_icon_up.setImageDrawable(activity.getResources().getDrawable(R.drawable.black_less));
            two_layout.setBackground(activity.getDrawable(R.drawable.topless_boarder_black));
            strikeView.setBackgroundColor(activity.getColor(R.color.black));
            strikeView1.setBackgroundColor(activity.getColor(R.color.black));
            spinnerInstName.setBackground(activity.getResources().getDrawable(R.drawable.gradient_spinner_black));
            spnrexpiry.setBackground(activity.getResources().getDrawable(R.drawable.gradient_spinner_black));
            spinnerFromStrike.setBackground(activity.getResources().getDrawable(R.drawable.gradient_spinner_black));
            spinner_fno_expiry.setBackground(activity.getResources().getDrawable(R.drawable.gradient_spinner_black));
            spnrexpiry.setBackground(activity.getResources().getDrawable(R.drawable.gradient_spinner_black));
            spinnerToStrike.setBackground(activity.getResources().getDrawable(R.drawable.gradient_spinner_black));

            //roll over
            txt_nifty.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_sensex.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_nifty_change.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_nifty_value.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_rollOver.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_rollOver2.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_sensex_change.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_sensex_value.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_rollOverCost.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txt_rollOverCost2.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txtrollperName.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txtrollcostNmae.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txtrollper2Name.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            txtrollcost2Name.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
            rollOverIndexLinearLayout.setBackgroundColor(activity.getColor(R.color.white));
            first_layout.setBackgroundColor(activity.getColor(R.color.white));
            linearnifty.setBackground(activity.getDrawable(R.drawable.single_line_border_roll));
            linearniftybank.setBackground(activity.getDrawable(R.drawable.single_line_border_roll));
            roll_per.setBackgroundColor(activity.getColor(R.color.white));
            roll_cost.setBackgroundColor(activity.getColor(R.color.white));
            roll_per2.setBackgroundColor(activity.getColor(R.color.white));
            roll_cost2.setBackgroundColor(activity.getColor(R.color.white));
            rollindex1.setBackgroundColor(activity.getColor(R.color.white));
            rollvolume.setBackgroundColor(activity.getColor(R.color.white));
            rollinde2.setBackgroundColor(activity.getColor(R.color.white));
            rollvolume2.setBackgroundColor(activity.getColor(R.color.white));
            img_icon_arrow.setImageResource(R.drawable.black_more);
            img_icon_up.setImageResource(R.drawable.black_less);
        }
    }

    private void enableLeftRightSwipe(final ExpandableGroup group) {
        try {
//This is for LEFT/RIGHT Swipe BUY/SELL
            if (!group.getTitle().equalsIgnoreCase("Option Chain")) {


                final Paint p = new Paint();
                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                        return false;
                    }

                    @Override
                    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        int position = viewHolder.getAdapterPosition();
                        return super.getSwipeDirs(recyclerView, viewHolder);
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        try {
                            if (!group.getTitle().equalsIgnoreCase("Option Chain")) {

                                int position = viewHolder.getAdapterPosition();

                                if (gainerDataList.size() > 0) {
                                    if (direction == ItemTouchHelper.LEFT) {

                                        Bundle args2 = new Bundle();
                                        args2.putString(TradeFragment.SCRIP_NAME, gainerDataList.get(position).getName());
                                        args2.putString(TradeFragment.EXCHANGE_NAME, gainerDataList.get(position).getExchange());
                                        args2.putString(TradeFragment.TOKEN, gainerDataList.get(position).getToken());
                                        args2.putString(TradeFragment.TICK_SIZE, gainerDataList.get(position).getTicksize());
                                        args2.putString(TradeFragment.TRADE_ACTION, "Sell");
                                        AccountDetails.globalPlaceOrderBundle = args2;
                                        EventBus.getDefault().post("placeorder");

                                        if (childAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            childAdapter.notifyDataSetChanged();
                                        }

                                        if (rollchildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            rollchildAdapter.notifyDataSetChanged();
                                        }
                                        if (fnochildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            fnochildAdapter.notifyDataSetChanged();
                                        }


                                    } else {


                                        Bundle args2 = new Bundle();
                                        args2.putString(TradeFragment.SCRIP_NAME, gainerDataList.get(position).getName());
                                        args2.putString(TradeFragment.EXCHANGE_NAME, gainerDataList.get(position).getExchange());
                                        args2.putString(TradeFragment.TOKEN, gainerDataList.get(position).getToken());
                                        args2.putString(TradeFragment.TICK_SIZE, gainerDataList.get(position).getTicksize());
                                        args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                                        AccountDetails.globalPlaceOrderBundle = args2;
                                        EventBus.getDefault().post("placeorder");


                                        if (childAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            childAdapter.notifyDataSetChanged();
                                        }

                                        if (rollchildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            rollchildAdapter.notifyDataSetChanged();
                                        }
                                        if (fnochildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            fnochildAdapter.notifyDataSetChanged();
                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                        try {
                            if (!group.getTitle().equalsIgnoreCase("Option Chain")) {
                                Drawable icon;
                                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                                    View itemView = viewHolder.itemView;
                                    int height = itemView.getBottom() - itemView.getTop();
                                    int width = itemView.getRight() - itemView.getLeft();
                                    float iconH = activity.getResources().getDisplayMetrics().density * 28;
                                    float iconW = activity.getResources().getDisplayMetrics().density * 28;

                                    if (dX > 0) {

                                        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (300 - 20), itemView.getBottom());
                                        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                                            p.setColor(activity.getResources().getColor(R.color.whitetheambuyColor));
                                        } else {
                                            p.setColor(activity.getResources().getColor(R.color.buyColor));
                                        }
                                        c.drawRoundRect(leftButton, 0, 0, p);
                                        drawText("Buy", c, leftButton, p);


                                    } else if (dX < 0) {

                                        RectF rightButton = new RectF(itemView.getRight() - (300 - 20), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                                        p.setColor(activity.getResources().getColor(R.color.sellColor));
                                        c.drawRoundRect(rightButton, 0, 0, p);
                                        drawText("Sell", c, rightButton, p);


                                    }
                                }

                                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(derivativechildRecyclerview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
//This is for LEFT/RIGHT Swipe BUY/SELL
            if (!group.getTitle().equalsIgnoreCase("Option Chain")) {


                final Paint p = new Paint();
                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                        return false;
                    }

                    @Override
                    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        int position = viewHolder.getAdapterPosition();
                        return super.getSwipeDirs(recyclerView, viewHolder);
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        try {
                            if (!group.getTitle().equalsIgnoreCase("Option Chain")) {

                                int position = viewHolder.getAdapterPosition();

                                if (gainerDataList.size() > 0) {
                                    if (direction == ItemTouchHelper.LEFT) {

                                        Bundle args2 = new Bundle();
                                        args2.putString(TradeFragment.SCRIP_NAME, gainerDataList.get(position).getName());
                                        args2.putString(TradeFragment.EXCHANGE_NAME, gainerDataList.get(position).getExchange());
                                        args2.putString(TradeFragment.TOKEN, gainerDataList.get(position).getToken());
                                        args2.putString(TradeFragment.TICK_SIZE, gainerDataList.get(position).getTicksize());
                                        args2.putString(TradeFragment.TRADE_ACTION, "Sell");
                                        AccountDetails.globalPlaceOrderBundle = args2;
                                        EventBus.getDefault().post("placeorder");

                                        if (childAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            childAdapter.notifyDataSetChanged();
                                        }

                                        if (rollchildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            rollchildAdapter.notifyDataSetChanged();

                                        }
                                        if (fnochildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            fnochildAdapter.notifyDataSetChanged();
                                        }


                                    } else {


                                        Bundle args2 = new Bundle();
                                        args2.putString(TradeFragment.SCRIP_NAME, gainerDataList.get(position).getName());
                                        args2.putString(TradeFragment.EXCHANGE_NAME, gainerDataList.get(position).getExchange());
                                        args2.putString(TradeFragment.TOKEN, gainerDataList.get(position).getToken());
                                        args2.putString(TradeFragment.TICK_SIZE, gainerDataList.get(position).getTicksize());
                                        args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                                        AccountDetails.globalPlaceOrderBundle = args2;
                                        EventBus.getDefault().post("placeorder");


                                        if (childAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            childAdapter.notifyDataSetChanged();
                                        }

                                        if (rollchildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            rollchildAdapter.notifyDataSetChanged();
                                        }
                                        if (fnochildAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            fnochildAdapter.notifyDataSetChanged();
                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                        try {
                            if (!group.getTitle().equalsIgnoreCase("Option Chain")) {
                                Drawable icon;
                                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                                    View itemView = viewHolder.itemView;
                                    int height = itemView.getBottom() - itemView.getTop();
                                    int width = itemView.getRight() - itemView.getLeft();
                                    float iconH = activity.getResources().getDisplayMetrics().density * 28;
                                    float iconW = activity.getResources().getDisplayMetrics().density * 28;

                                    if (dX > 0) {

                                        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (300 - 20), itemView.getBottom());
                                        p.setColor(activity.getResources().getColor(R.color.buyColor));
                                        c.drawRoundRect(leftButton, 0, 0, p);
                                        drawText("Buy", c, leftButton, p);


                                    } else if (dX < 0) {

                                        RectF rightButton = new RectF(itemView.getRight() - (300 - 20), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                                        p.setColor(activity.getResources().getColor(R.color.sellColor));
                                        c.drawRoundRect(rightButton, 0, 0, p);
                                        drawText("Sell", c, rightButton, p);


                                    }
                                }

                                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(OIA_recyclerview);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {

    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {

    }


    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 40;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }


    private void loadOptionChainAllSpinnerData(String url, final String type) {
        // to LoadoptionChainallSpinner data get request is send to aracane server
        WSHandler.getRequest(activity, url, new WSHandler.GreekResponseCallback() {

            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    switch (type) {
                        case "symbol":
                            symbolname.clear();
                            if (expiryList.size() > 1) {
                                expiryList.clear();
                            }
                            for (int i = 0; i < respCategory.length(); i++) {
                                symbolname.add(respCategory.getJSONObject(i).getString("symbol"));
                                OIChangeData oiChangeDataModel = new OIChangeData();
                                oiChangeDataModel.setSymbol(respCategory.getJSONObject(i).getString("symbol"));
                                oiChangeDataList.add(oiChangeDataModel);
                            }
                            symbolAdapter = new ArrayAdapter<>(activity, R.layout.row_spinner_mutualfund, symbolname);
                            spnrsymbol.setAdapter(symbolAdapter);
                            spnrsymbol.setThreshold(2);
                            spnrsymbol.enoughToFilter();
                            if (symName != null) {
                                if (!symName.equalsIgnoreCase("")) {
                                    spnrsymbol.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

//                                        spnrsymbol.showDropDown();
                                        }
                                    }, 500);
                                    spnrsymbol.clearFocus();
                                    spnrsymbol.setText(symName);
                                    spnrsymbol.dismissDropDown();
                                    loadOptionChainAllSpinnerData("getexpiryRequest?cSymbol=" + spnrsymbol.getText().toString(), "expiry");
                                }
                            }
//                            checkViewAndUpdate();
//                            spnrsymbol.showDropDown();


                            break;
                        case "expiry":
                            String exp = "";
                            expiryList.clear();
                            expiryList.add("Select Expiry");
                            expiryAdater.notifyDataSetChanged();
                            spnrexpiry.setSelection(0);
                            expiry = new HashMap<>();
                            longdate = new HashMap<>();
                            for (int i = 0; i < respCategory.length(); i++) {

                                String pattern = "ddMMMyyyy";
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                                Date D = new Date((respCategory.getJSONObject(i).getLong("expiry") /*+ 315513000*/) * 1000);
                                String date = simpleDateFormat.format(D);

                                expiry.put(respCategory.getJSONObject(i).getString("expiry"), date);
                                longdate.put(respCategory.getJSONObject(i).getLong("lourtoken") + "", date);
                                expiryList.add(date);
                                expiryAdater.notifyDataSetChanged();
                            }
                            final Typeface font = Typeface.createFromAsset(activity.getResources().getAssets(), "DaxOT.ttf");
                            if (expiryList.size()>0) {
                                expiryAdater = new ArrayAdapter<String>(activity, AccountDetails.getRowSpinnerSimple(), expiryList) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                                            v.setTextColor(activity.getResources().getColor(R.color.black));
                                        } else {
                                            v.setTextColor(activity.getResources().getColor(R.color.white));
                                        }
                                        v.setPadding(15, 15, 15, 15);
                                        return v;
                                    }

                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        TextView v = (TextView) super.getView(position, convertView, parent);
                                        v.setTypeface(font);
                                        v.setTextColor(Color.BLACK);
                                        v.setPadding(15, 15, 15, 15);
                                        return v;
                                    }
                                };
                                expiryAdater.setDropDownViewResource(R.layout.custom_spinner);
                                spnrexpiry.setAdapter(expiryAdater);
                                spnrexpiry.setOnItemSelectedListener(expiryselectListener);
                            }
                            if (!expiryDate.equalsIgnoreCase("")) {

                                if (expiryList.contains(expiryDate)) {
                                    spnrexpiry.setSelection(expiryList.indexOf(expiryDate));
                                }
                            }

                            break;

                        case "strikeprice":
                            lowerstrikePriceList.clear();
                            upperstrikePriceList.clear();
                            upperstrikePriceList.add("SELECT FROM STRIKE");
                            lowerstrikePriceList.add("SELECT TO STRIKE");
                            for (int i = 0; i < respCategory.length(); i++) {
                                if (respCategory.getJSONObject(i).getDouble("strikeprice") > 0) {
                                    lowerstrikePriceList.add(Double.parseDouble(respCategory.getJSONObject(i).getString("strikeprice")) + "");
                                    upperstrikePriceList.add(Double.parseDouble(respCategory.getJSONObject(i).getString("strikeprice")) + "");
                                }
                            }
                            spinnerFromStrike.setSelection(0);
                            spinnerToStrike.setSelection(0);
                            showProgress();
                            loadOptionChainAllSpinnerData("getLTP?lOurToken=" + ourToken, "ltp");

                            break;
                        case "ltp":
                            dLTp = respCategory.getJSONObject(0).getDouble("ltp") / 100;

                            if (dLTp != null) {

                                ArrayList<Double> tempUpperStrike = new ArrayList<Double>();
                                ArrayList<Double> tempLowerstrike = new ArrayList<Double>();
                                ArrayList<Double> tempStrike = new ArrayList<Double>();
                                for (int i = 1; i < lowerstrikePriceList.size(); i++) {
                                    if (Double.parseDouble(lowerstrikePriceList.get(i)) > 0) {
                                        if (dLTp < Double.parseDouble(lowerstrikePriceList.get(i))) {
                                            tempUpperStrike.add(Double.parseDouble(lowerstrikePriceList.get(i)));
                                        } else {
                                            tempLowerstrike.add(Double.parseDouble(lowerstrikePriceList.get(i)));
                                        }
                                    }
                                }
                                Collections.sort(tempLowerstrike);
                                Collections.sort(tempUpperStrike);

                                if (tempLowerstrike.size() > 3) {
                                    for (int j = tempLowerstrike.size() - 1; j >= tempLowerstrike.size() - 3; j--) {
//                                        lowerstrikePriceList.add(String.valueOf(tempLowerstrike.get(j)));
//                                        upperstrikePriceList.add(String.valueOf(tempLowerstrike.get(j)));
                                        tempStrike.add(tempLowerstrike.get(j));
                                    }
                                } else {
                                    for (int j = 0; j > tempLowerstrike.size(); j++) {
//                                        lowerstrikePriceList.add(String.valueOf(tempLowerstrike.get(j)));
//                                        upperstrikePriceList.add(String.valueOf(tempLowerstrike.get(j)));
                                        tempStrike.add(tempLowerstrike.get(j));
                                    }
                                }

                                if (tempUpperStrike.size() > 3) {
                                    for (int j = 0; j < 3; j++) {
//                                        lowerstrikePriceList.add(String.valueOf(tempUpperStrike.get(j)));
//                                        upperstrikePriceList.add(String.valueOf(tempUpperStrike.get(j)));
                                        tempStrike.add(tempUpperStrike.get(j));
                                    }
                                } else {
                                    for (int j = 0; j < tempUpperStrike.size(); j++) {
//                                        lowerstrikePriceList.add(String.valueOf(tempUpperStrike.get(j)));
//                                        upperstrikePriceList.add(String.valueOf(tempUpperStrike.get(j)));
                                        tempStrike.add(tempUpperStrike.get(j));
                                    }
                                }

                                Collections.sort(tempStrike);

                                if (!fromStrike.equalsIgnoreCase("")) {
                                    spinnerFromStrike.setSelection(upperstrikePriceList.indexOf(fromStrike));
                                } else {
                                    spinnerFromStrike.setSelection(upperstrikePriceList.indexOf(String.valueOf(tempStrike.get(0).doubleValue())));
                                }

                                if (!toStrike.equalsIgnoreCase("")) {
                                    spinnerToStrike.setSelection(lowerstrikePriceList.indexOf(toStrike));
                                } else {
                                    spinnerToStrike.setSelection(lowerstrikePriceList.indexOf(String.valueOf(tempStrike.get(tempStrike.size() - 1).doubleValue())));
                                }


                                if (lowerstrikePriceList.size() > 0 && upperstrikePriceList.size() > 0) {
                                    if (lowerstrikePriceList.get(0) != null && upperstrikePriceList.get(upperstrikePriceList.size() - 1) != null) {
                                        if (spinnerToStrike.getSelectedItemId() > 0 && spinnerFromStrike.getSelectedItemId() > 0) {
                                            if(spinnerInstName.getSelectedItemId()>=0&& spnrexpiry.getSelectedItemId()>0 && symbolAdapter != null){
                                                if (!isoirequestsen &&
                                                        !spinnerInstName.getSelectedItem().toString().equalsIgnoreCase("")
                                                        && spinnerInstName.getSelectedItem() != null
                                                        && !spnrsymbol.getText().toString().equalsIgnoreCase("")
                                                        && spnrexpiry.getSelectedItem() != null
                                                        && !spnrexpiry.getSelectedItem().toString().equalsIgnoreCase("")
                                                        && !spinnerToStrike.getSelectedItem().toString().equalsIgnoreCase("")
                                                        && spinnerToStrike.getSelectedItem() != null
                                                        && !spinnerFromStrike.getSelectedItem().toString().equalsIgnoreCase("")
                                                        && spinnerFromStrike.getSelectedItem() != null) {
                                                    isoirequestsen = true;
                                                    if (activity != null) {
                                                        SharedPreferences.Editor editor = Util.getPrefs(activity).edit();
                                                        editor.putString("instrumentname", spinnerInstName.getSelectedItem().toString());
                                                        editor.putString("symbol", spnrsymbol.getText().toString());
                                                        editor.putString("expiry", spnrexpiry.getSelectedItem().toString());
                                                        editor.putString("tostrike", spinnerToStrike.getSelectedItem().toString());
                                                        editor.putString("fromstrike", spinnerFromStrike.getSelectedItem().toString());
                                                        editor.commit();
                                                    }

                                                    OptionchainRequest.sendRequest("NSE", spnrsymbol.getText().toString(), spnrexpiry.getSelectedItem().toString(), spinnerFromStrike.getSelectedItem().toString(), spinnerToStrike.getSelectedItem().toString(), activity, serviceResponseHandler);
                                                }
                                            }

                                            isoirequestsen = false;

                                        }

                                    }

                                }
                            }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    private void updateBorderLineColor() {
        int textColorPositive;
        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
            textColorPositive = R.color.whitetheambuyColor;
        } else {
            textColorPositive = R.color.dark_green_positive;
        }

        int textColorNegative = R.color.dark_red_negative;
        if (txt_nifty_change.getText().toString().startsWith("-")) {
            txt_nifty_change.setTextColor(activity.getResources().getColor(textColorNegative));
        } else {
            txt_nifty_change.setTextColor(activity.getResources().getColor(textColorPositive));
        }
//        if(txt_nifty_value.getText().toString().startsWith("-")) {
//            txt_nifty_value.setTextColor(activity.getResources().getColor(textColorNegative));
//        } else {
//            txt_nifty_value.setTextColor(activity.getResources().getColor(textColorPositive));
//
//        }

        if (txt_rollOver.getText().toString().startsWith("-")) {
            txt_rollOver.setTextColor(activity.getResources().getColor(textColorNegative));
        } else {
            txt_rollOver.setTextColor(activity.getResources().getColor(textColorPositive));
        }
        if (txt_rollOverCost.getText().toString().startsWith("-")) {
            txt_rollOverCost.setTextColor(activity.getResources().getColor(textColorNegative));
        } else {
            txt_rollOverCost.setTextColor(activity.getResources().getColor(textColorPositive));

        }

        if (txt_sensex_change.getText().toString().startsWith("-")) {
            txt_sensex_change.setTextColor(activity.getResources().getColor(textColorNegative));
        } else {
            txt_sensex_change.setTextColor(activity.getResources().getColor(textColorPositive));

        }
//        if(txt_sensex_value.getText().toString().startsWith("-")) {
//            txt_sensex_value.setTextColor(activity.getResources().getColor(textColorNegative));
//        } else {
//            txt_sensex_value.setTextColor(activity.getResources().getColor(textColorPositive));
//
//        }
        if (txt_rollOver2.getText().toString().startsWith("-")) {
            txt_rollOver2.setTextColor(activity.getResources().getColor(textColorNegative));
        } else {
            txt_rollOver2.setTextColor(activity.getResources().getColor(textColorPositive));
        }
        if (txt_rollOverCost2.getText().toString().startsWith("-")) {
            txt_rollOverCost2.setTextColor(activity.getResources().getColor(textColorNegative));
        } else {
            txt_rollOverCost2.setTextColor(activity.getResources().getColor(textColorPositive));

        }
    }

    private final AdapterView.OnItemSelectedListener lowerstrikeselectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            SharedPreferences.Editor editor = Util.getPrefs(activity).edit();
            if (position != 0) {
                if (spinnerToStrike.getSelectedItemPosition() > 0 && spinnerFromStrike.getSelectedItemPosition() > 0) {
                    if (Double.parseDouble(spinnerToStrike.getSelectedItem().toString()) < Double.parseDouble(spinnerFromStrike.getSelectedItem().toString())) {
                        GreekDialog.alertDialog(activity, 0, GREEK, "Please select to strike greater than from Strike ", "Ok", false, null);
                        return;
                    }
                } else {
                    if (lowerstrikePriceList.size() > 0 && upperstrikePriceList.size() > 0) {
                        if (lowerstrikePriceList.get(0) != null && upperstrikePriceList.get(upperstrikePriceList.size() - 1) != null) {
                            if (!isoirequestsen) {
                                if (spinnerToStrike.getSelectedItemId() > 0 && spinnerFromStrike.getSelectedItemId() > 0) {
                                    if (!spnrexpiry.getSelectedItem().toString().equalsIgnoreCase("") && !spinnerToStrike.getSelectedItem().toString().equalsIgnoreCase("") && !spinnerFromStrike.getSelectedItem().toString().equalsIgnoreCase("")) {
                                        editor.putString("instrumentname", spinnerInstName.getSelectedItem().toString());
                                        editor.putString("symbol", spnrsymbol.getText().toString());
                                        editor.putString("expiry", spnrexpiry.getSelectedItem().toString());
                                        editor.putString("tostrike", spinnerToStrike.getSelectedItem().toString());
                                        editor.putString("fromstrike", spinnerFromStrike.getSelectedItem().toString());
                                        editor.commit();
                                        optionChainhashData.clear();
                                        if (optionAdapter != null) {
                                            derivativechildRecyclerview.getRecycledViewPool().clear();
                                            optionAdapter.notifyDataSetChanged();
                                        }
                                        OptionchainRequest.sendRequest("NSE", spnrsymbol.getText().toString(), spnrexpiry.getSelectedItem().toString(), spinnerFromStrike.getSelectedItem().toString(), spinnerToStrike.getSelectedItem().toString(), activity, serviceResponseHandler);
                                    }
                                }
                            }
                        }
                    }
                }
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener strikeselectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            SharedPreferences.Editor editor = Util.getPrefs(activity).edit();
            if (position != 0) {
                if (Double.parseDouble(spinnerToStrike.getSelectedItem().toString()) < Double.parseDouble(spinnerFromStrike.getSelectedItem().toString())) {
                    GreekDialog.alertDialog(activity, 0, GREEK, "Please select to strike greater than from Strike ", "Ok", false, null);
                    return;
                }
                if (lowerstrikePriceList.size() > 0 && upperstrikePriceList.size() > 0) {


                    if (lowerstrikePriceList.get(0) != null && upperstrikePriceList.get(upperstrikePriceList.size() - 1) != null) {
                        if (!isoirequestsen) {
                            if (spinnerToStrike.getSelectedItemId() > 0 && spinnerFromStrike.getSelectedItemId() > 0) {
                                if (!spnrexpiry.getSelectedItem().toString().equalsIgnoreCase("") && !spinnerToStrike.getSelectedItem().toString().equalsIgnoreCase("") && !spinnerFromStrike.getSelectedItem().toString().equalsIgnoreCase("")) {

                                    editor.putString("instrumentname", spinnerInstName.getSelectedItem().toString());
                                    editor.putString("symbol", spnrsymbol.getText().toString());
                                    editor.putString("expiry", spnrexpiry.getSelectedItem().toString());
                                    editor.putString("tostrike", spinnerToStrike.getSelectedItem().toString());
                                    editor.putString("fromstrike", spinnerFromStrike.getSelectedItem().toString());
                                    editor.commit();


                                    optionChainhashData.clear();
                                    if (optionAdapter != null) {
                                        derivativechildRecyclerview.getRecycledViewPool().clear();
                                        optionAdapter.notifyDataSetChanged();
                                    }
                                    OptionchainRequest.sendRequest("NSE", spnrsymbol.getText().toString(), spnrexpiry.getSelectedItem().toString(), spinnerFromStrike.getSelectedItem().toString(), spinnerToStrike.getSelectedItem().toString(), activity, serviceResponseHandler);
                                }
                            }
                        }
                    }
                }

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener instselectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            SharedPreferences.Editor editor = Util.getPrefs(activity).edit();
            String insName = Util.getPrefs(activity).getString("instrumentname", "");

            if (!insName.equalsIgnoreCase(spinnerInstName.getSelectedItem().toString())) {
                symName = "";
                expiryDate = "";
                instrumentName = "";
                fromStrike = "";
                toStrike = "";
                spnrsymbol.setText("");
                spnrsymbol.setHint("Search Symbol");

            }


            if (expiryList.size() > 0) {
                expiryList.clear();
                if (expiryList != null) {
                    expiryList.add("Select Expiry");
                    expiryAdater.notifyDataSetChanged();
                    spnrexpiry.setSelection(0);
                }
            }

            if (optionChainhashData.size() > 0) {
                optionChainhashData.clear();
                if (optionAdapter != null) {
                    derivativechildRecyclerview.getRecycledViewPool().clear();
                    optionAdapter.notifyDataSetChanged();
                }
            }


            editor.putString("tempinstrument", spinnerInstName.getSelectedItem().toString());
            editor.commit();
            editor.apply();
            if (!insName.equalsIgnoreCase(spinnerInstName.getSelectedItem().toString())) {
                showProgress();
                if (spinnerInstName.getSelectedItem().toString().equalsIgnoreCase("index")) {
                    loadOptionChainAllSpinnerData("getSymbolForInstrument?instName=FUTIDX", "symbol");

                } else {
                    loadOptionChainAllSpinnerData("getSymbolForInstrument?instName=FUTSTK", "symbol");
                }
            } else {
                if (symName.equalsIgnoreCase("")) {
                    showProgress();
                    if (spinnerInstName.getSelectedItem().toString().equalsIgnoreCase("index")) {
                        loadOptionChainAllSpinnerData("getSymbolForInstrument?instName=FUTIDX", "symbol");

                    } else {
                        loadOptionChainAllSpinnerData("getSymbolForInstrument?instName=FUTSTK", "symbol");
                    }
                }

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener expiryselectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0 && expiryList != null && position < expiryList.size()) {
                String value = parent.getItemAtPosition(position).toString();


                if (!value.equalsIgnoreCase("") && !value.isEmpty()) {
                    for (Map.Entry entry : expiry.entrySet()) {
                        if (value.equals(entry.getValue())) {
                            key = entry.getKey().toString();
                        }
                    }
                    if (key == null || key.equalsIgnoreCase("")) {
                        value = parent.getItemAtPosition(2).toString();
                        if (!value.equalsIgnoreCase("")) {
                            for (Map.Entry entry : expiry.entrySet()) {
                                if (value.equals(entry.getValue())) {
                                    key = entry.getKey().toString();
                                }
                            }
                        }
                    }
                } else {
                    key = "0";
                }


                String token = null;
                if (!value.equalsIgnoreCase("") && !value.isEmpty()) {
                    for (Map.Entry entry : longdate.entrySet()) {
                        if (value.equals(entry.getValue())) {
                            token = entry.getKey().toString();
                            ourToken = token;
                        }
                    }
                } else {
                    key = "0";
                }
                showProgress();
                // strike request send to aracane server
                loadOptionChainAllSpinnerData("getstrikeRequest?cSymbol=" + spnrsymbol.getText().toString() + "&lExpiry=" + key, "strikeprice");

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

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


    public void showProgress() {
        if (progressLayout != null) {
            spnrsymbol.setEnabled(false);
            spnrexpiry.setEnabled(false);
            spinnerInstName.setEnabled(false);
            progressLayout.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        spnrsymbol.setEnabled(true);
        spnrexpiry.setEnabled(true);
        spinnerInstName.setEnabled(true);
        progressLayout.setVisibility(View.GONE);
    }


    public void hideKeyboard(Context activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        View view = null;
        //= activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void toggleErrorLayout(boolean show) {

        if (show) {
            derivativechildRecyclerview.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            derivativechildRecyclerview.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

}

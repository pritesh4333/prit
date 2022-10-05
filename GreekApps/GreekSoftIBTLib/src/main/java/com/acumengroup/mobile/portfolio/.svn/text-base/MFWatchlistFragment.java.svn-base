package com.acumengroup.mobile.portfolio;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.AddSymbolToGroupRequest;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.AddSymbolToGroupResponse;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.MFSymbolDetail;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.RemoveSymbolFromGroupRequest;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.RemoveSymbolFromGroupResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.WatchlistDataByGroupNameRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.WatchlistGroupRequest;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.SymbolListModel;
import com.acumengroup.mobile.model.watchlistModel;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MFWatchlistFragment extends GreekBaseFragment implements AbsListView.OnScrollListener, SlideAndDragListView.OnDragDropListener, SlideAndDragListView.OnSlideListener, SlideAndDragListView.OnItemScrollBackListener, View.OnClickListener {
    public static final String GOTO = "GoTO";
    public static final String ASSET_TYPE = "AssetType";
    public static final String AMCNAME = "amcName";
    public static final String CORPISIN = "corpIsin";
    public static final String SCHEMECODE = "schemeCode";
    public static final String SCHEMENAME = "schemeName";
    public static final String SIPISIN = "sipIsin";
    public static final String TRADINGISIN = "tradingIsin";
    public static final String BSECODE = "bseCode";
    public static final String BSERTACODE = "bseRTACode";

    public static final String TRADE_SYMBOL = "TradeSymbol";
    public static final String EXCHANGE_NAME = "ExchangeName";
    public static final String ADDED_SYMBOL = "addedSymbol";


    private final ArrayList<String> groupNameList = new ArrayList<>();
    private final ArrayList<String> groupTypeList = new ArrayList<>();

    private final List<String> grpAmcName = new ArrayList<>();
    private final List<String> grpCorpIsin = new ArrayList<>();
    private final List<String> grpAssetType = new ArrayList<>();
    private final List<String> grpSchemeCode = new ArrayList<>();

    private final List<MFSymbolDetail> symbolListG = new ArrayList<>();
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private final ArrayList<String> symbolsToUnsubscribe = new ArrayList<>();
    private Spinner groupSpinner;
    private Spinner assetTypeSpinner;
    private LinkedHashMap lMap;
    private ArrayAdapter<String> groupsAdapter;
    private String selectedGrp = "", selectedGrpType = "";
    private boolean isReponseReceived = false;
    private PortfolioGetUserWatchListResponse getWatchListResponse = null;
    private String addedGroup = "";
    private String addedAssetType = "";
    private boolean isWaitingForResponseOnPTR = false;
    private ArrayList<String> types = new ArrayList<>();
    private GreekTextView errorTextView;
    private GreekTextView ltpSort, symText;
    private RelativeLayout errorMsgLayout;
    private ImageButton addBtn, editBtn;
    StreamingController streamController = new StreamingController();
    LinkedHashMap<String, String> hm;
    LinkedHashMap<String, Integer> seqNo_hm;
    SlideAndDragListView watchlistList;
    ArrayList<watchlistModel> watchlistModelsArr = new ArrayList<>();
    private String addToken = "";
    watchlistModel model;
    private MFWatchlistFragment.CustomAdapter commonAdapter;
    MFWatchlistFragment.CustomAdapter.Holder holder;
    boolean ltpAsc, symAsc = false;
    private ArrayList<HashMap<String, String>> hashArray;

    private Menu mMenu;
    //    private LinkedHashMap hash_model;
    private AlertDialog levelDialog;
    private View portDetView;
    private int grpPos = 0;
    int check = 0;

    private final AdapterView.OnItemSelectedListener groupsItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (++check > 1) {
                selectedGrp = groupSpinner.getSelectedItem().toString();
                addedGroup = null;
                selectedGrpType = groupTypeList.get(position);
                AccountDetails.setWatchlistGroup(selectedGrp);
                getDetailOfGroup(selectedGrp);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener typeSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            addedAssetType = assetTypeSpinner.getSelectedItem().toString();
            if (isReponseReceived) populateSymbols();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onClick(View view) {
        int id = view.getId();//Add New Group
        if (id == R.id.action_item1) {
            if (groupsAdapter.getCount() < 4) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Groups", groupNameList);
                bundle.putString("groupCount", String.valueOf(groupsAdapter.getCount() + 1));
                navigateTo(NAV_TO_MF_ADD_NEW_PORTFOLIO_SCREEN, bundle, true);
            } else
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_MAX_GROUP_ADD_MSG), "Ok", true, null);
            //}
            levelDialog.dismiss();
            //Add new symbol
        } else if (id == R.id.action_item2) {
            if (groupsAdapter.getCount() > 0) {
                selectedGrp = groupSpinner.getSelectedItem().toString();
                selectedGrpType = groupTypeList.get(groupSpinner.getSelectedItemPosition());
                Bundle bundle = new Bundle();
                bundle.putString("Source", "MFWatchlist");
//                        bundle.putString("from", "Watchlist");
                bundle.putInt("selectedAssetForSymbol", 0);
                bundle.putString("selectedAssetMf", "MF");
                navigateTo(NAV_TO_SYMBOL_SEARCH_SCREEN, bundle, true);
            } else
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_NO_GROUP_EXIST_MSG), "Ok", true, null);
            levelDialog.dismiss();
        } else if (id == R.id.pop_quote) {
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Confirm Delete Scheme?", "Yes", "No", true, new GreekDialog.DialogListener() {


                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    if (action.name().equalsIgnoreCase("ok")) {
                        symbolListG.clear();
                        String schemeCode = (String) lMap.get("schemeCode");
                        for (int i = 0; i < grpAmcName.size(); i++) {
                            if (grpSchemeCode.get(i).equals(schemeCode)) {
                                MFSymbolDetail detail = new MFSymbolDetail();
                                detail.setAmcname(grpAmcName.get(i));
                                detail.setCorpisin(grpCorpIsin.get(i));
                                detail.setSchemecode(grpSchemeCode.get(i));
                                detail.setAssetType(grpAssetType.get(i));
                                symbolListG.add(detail);
                            }
                        }
                        selectedGrp = groupSpinner.getSelectedItem().toString();
                        addedAssetType = assetTypeSpinner.getSelectedItem().toString();
                        sendRemoveSymbolRequest(selectedGrp, groupTypeList.get(groupSpinner.getSelectedItemPosition()), symbolListG);
                    } else if (action.name().equalsIgnoreCase("cancel")) {
                    }
                }
            });
            levelDialog.dismiss();
        } else if (id == R.id.pop_advice) {
            Bundle args = new Bundle();
            args.putString("schemCode", (String) lMap.get(SCHEMECODE));
            args.putString("tradingISIN", (String) lMap.get("ISIN"));
            args.putString("sipISIN", (String) lMap.get("ISIN"));
            args.putString("schemeName", (String) lMap.get(SCHEMENAME));

            args.putString("From", "marketmutualfund");
            navigateTo(NAV_TO_MUTUALFUND_GET_QUOTE, args, true);
            levelDialog.dismiss();
            //Trade
        } else if (id == R.id.pop_trade) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER) {
                Bundle args2 = new Bundle();
                args2.putString(TradeFragment.SCRIP_NAME, (String) lMap.get("ScriptName"));
                args2.putString(TradeFragment.EXCHANGE_NAME, (String) lMap.get("Exchange"));
                args2.putString(TradeFragment.TOKEN, (String) lMap.get("Token"));
                args2.putString(TradeFragment.ASSET_TYPE, (String) lMap.get("assetType"));
                args2.putString(TradeFragment.UNIQUEID, "");
                args2.putString(TradeFragment.TRADE_SYMBOL, (String) lMap.get("OriginalSymbol"));
                args2.putString(TradeFragment.LOT_QUANTITY, (String) lMap.get("lot"));
                args2.putString(TradeFragment.TICK_SIZE, (String) lMap.get("tickSize"));
                args2.putString(TradeFragment.MULTIPLIER, (String) lMap.get("multiplier"));
                args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                args2.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp((String) lMap.get("expiryDate"), "dd MMM yyyy", "bse"));
                args2.putString(TradeFragment.STRICKPRICE, (String) lMap.get("strickPrice"));
                args2.putString("OptType", (String) lMap.get("optionType"));
                navigateTo(NAV_TO_TRADE_SCREEN, args2, true);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    }
                });
            }
            levelDialog.dismiss();
        } else if (id == R.id.pop_trade_sell) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER) {
                Bundle args3 = new Bundle();
                args3.putString(TradeFragment.SCRIP_NAME, (String) lMap.get("ScriptName"));
                args3.putString(TradeFragment.EXCHANGE_NAME, (String) lMap.get("Exchange"));
                args3.putString(TradeFragment.TOKEN, (String) lMap.get("Token"));
                args3.putString(TradeFragment.ASSET_TYPE, (String) lMap.get("assetType"));
                args3.putString(TradeFragment.UNIQUEID, "");
                args3.putString(TradeFragment.TRADE_SYMBOL, (String) lMap.get("OriginalSymbol"));
                args3.putString(TradeFragment.LOT_QUANTITY, (String) lMap.get("lot"));
                args3.putString(TradeFragment.TICK_SIZE, (String) lMap.get("tickSize"));
                args3.putString(TradeFragment.MULTIPLIER, (String) lMap.get("multiplier"));
                args3.putString(TradeFragment.TRADE_ACTION, "Sell");
                args3.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp((String) lMap.get("expiryDate"), "dd MMM yyyy", "bse"));
                args3.putString(TradeFragment.STRICKPRICE, (String) lMap.get("strickPrice"));
                args3.putString("OptType", (String) lMap.get("optionType"));
                navigateTo(NAV_TO_TRADE_SCREEN, args3, true);
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    }
                });
            }
            levelDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        portDetView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_watchlist_mf).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_watchlist_mf).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }


        setupView(portDetView);
        sendGroupNameRequest();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = portDetView.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
            addBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_playlist_add_black_24dp));
            editBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_mode_edit_black_24dp));

        } else {

            addBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_playlist_add_white_24dp));
            editBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_mode_edit_white_24dp));
        }

        return portDetView;
    }

    private void settingThemeAssetWatchlist() {
        addBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_playlist_add_black_24dp));
        editBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_mode_edit_black_24dp));

    }

    private void setupView(View parent) {
        hideAppTitle();
        errorTextView = parent.findViewById(R.id.errorHeader);
        ltpSort = parent.findViewById(R.id.ltpText);
        symText = parent.findViewById(R.id.symbolText);
        errorMsgLayout = parent.findViewById(R.id.showmsgLayout);

        groupNameList.clear();
        groupSpinner = parent.findViewById(R.id.groupSpinner);
        groupsAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), groupNameList);
        groupsAdapter.setDropDownViewResource(R.layout.custom_spinner);
        groupSpinner.setAdapter(groupsAdapter);
        groupSpinner.setOnItemSelectedListener(groupsItemSelectedListener);

        watchlistList = parent.findViewById(R.id.watchlist_list);

        initMenu();

        try {
            commonAdapter = new MFWatchlistFragment.CustomAdapter(getMainActivity(), new ArrayList<HashMap<String, String>>());
            initUiAndListener();
            watchlistList.setAdapter(commonAdapter);
        } catch (Exception e) {

            errorMsgLayout.setVisibility(View.VISIBLE);
            errorTextView.setText("No data available");
            errorTextView.setVisibility(View.VISIBLE);
        }
        assetTypeSpinner = parent.findViewById(R.id.col1Spinner);
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            types = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.watchlistTypes)));
        } else {
            getAssetTypeList();
        }
        types = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.MFwatchlistTypes)));

        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), types);
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(assetTypeAdapter);
        assetTypeSpinner.setOnItemSelectedListener(typeSelectedListener);

        addBtn = parent.findViewById(R.id.addBtn);

        watchlistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                lMap = (LinkedHashMap) commonAdapter.getItemHash(position);
                View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_four_actions, null);
                GreekTextView view1 = layout.findViewById(R.id.pop_quote);
                view1.setText("Delete Scheme");
                GreekTextView view2 = layout.findViewById(R.id.pop_advice);
                view2.setText("Scheme Details");
                GreekTextView view3 = layout.findViewById(R.id.pop_trade_sell);
                view3.setText(R.string.POPUP_SCRIPT_TRADE_SELL);
                GreekTextView view4 = layout.findViewById(R.id.pop_trade);
                view4.setText(R.string.POPUP_SCRIPT_TRADE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);
                view1.setOnClickListener(MFWatchlistFragment.this);
                view2.setOnClickListener(MFWatchlistFragment.this);
                view3.setOnClickListener(MFWatchlistFragment.this);
                view4.setOnClickListener(MFWatchlistFragment.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                builder.setView(layout);
                levelDialog = builder.create();
                if (!levelDialog.isShowing()) {
                    levelDialog.show();
                }
            }
        });
        ltpSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ltpAsc) {
                    sortBySymbol(true, "NAV", true);
                    ltpAsc = false;
                } else {
                    sortBySymbol(true, "NAV", false);
                    ltpAsc = true;
                }
                getVisibleSymbolTokens(watchlistList.getFirstVisiblePosition(), watchlistList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });

        symText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (symAsc) {
                    sortBySymbol(false, "schemeName", true);
                    symAsc = false;
                } else {
                    sortBySymbol(false, "schemeName", false);
                    symAsc = true;
                }
                getVisibleSymbolTokens(watchlistList.getFirstVisiblePosition(), watchlistList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupsAdapter.getCount() == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("Groups", groupNameList);
                    navigateTo(NAV_TO_MF_ADD_NEW_PORTFOLIO_SCREEN, bundle, true);
                } else {
                    showQuickActionPopup(v);
                }
            }
        });

        editBtn = parent.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getWatchListResponse != null) {
                    if (groupsAdapter.getCount() > 0) {
                        selectedGrp = groupSpinner.getSelectedItem().toString();
                        selectedGrpType = groupTypeList.get(groupSpinner.getSelectedItemPosition());

                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("grpnamelist", groupTypeList);
                        bundle.putSerializable("Response", getWatchListResponse);
                        bundle.putInt("SelectedGrp", groupSpinner.getSelectedItemPosition());
                        navigateTo(NAV_TO_MF_EDIT_WATCHLIST_SCREEN, bundle, true);
                    }
                }
            }
        });


        watchlistList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case 1: {
                        break;
                    }
                    case 0: {

                        streamController.pauseStreaming(getActivity(), "ltpinfo", symbolsToUnsubscribe);
                        sendStreamingRequest();
                        break;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                getVisibleScrollSymbolTokens(firstVisibleItem, visibleItemCount);
            }

        });
    }

    public void initMenu() {

        mMenu = new Menu(false, 0);
        mMenu.addItem(new MenuItem.Builder().setWidth(0)
                .setText(" ")
                .setTextColor(Color.GRAY)
                .setTextSize(14)
                .build());

        mMenu.addItem(new MenuItem.Builder().setWidth(0)
                .setText(" ")
                .setTextColor(Color.BLACK)
                .setTextSize((14))
                .build());

        mMenu.addItem(new MenuItem.Builder().setWidth(0)
                .setText(" ")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.BLACK)
                .setTextSize(14)
                .build());

        mMenu.addItem(new MenuItem.Builder().setWidth(0)
                .setText(" ")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.BLACK)
                .setTextSize(14)
                .build());
    }

    public void initUiAndListener() {

        watchlistList.setMenu(mMenu);
        watchlistList.setAdapter(commonAdapter);
        watchlistList.setOnDragDropListener(this);
        watchlistList.setOnSlideListener(this);
        watchlistList.setOnItemScrollBackListener(this);
    }

    public void sendGroupNameRequest() {
        showProgress();
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            WatchlistGroupRequest.sendMFRequest(AccountDetails.getDeviceID(getMainActivity()), getMainActivity(), serviceResponseHandler);
        } else {
            WatchlistGroupRequest.sendMFRequest(AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);
        }
    }

    private void getAssetTypeList() {
        types.clear();
        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
            types.add("Equity");
        }
        if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
            types.add("FNO");
        }
        if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            types.add("Currency");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            types.add("Commodity");
        }

        if (types.size() > 1) {
            types.add(0, "All");
        }
    }


    private void showQuickActionPopup(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

        View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_two_actions, null);
        GreekTextView view1 = layout.findViewById(R.id.action_item1);
        view1.setText("Group");
        GreekTextView view2 = layout.findViewById(R.id.action_item2);
        view2.setText("Scheme");
        view1.setOnClickListener(MFWatchlistFragment.this);
        view2.setOnClickListener(MFWatchlistFragment.this);


        builder.setView(layout);
        levelDialog = builder.create();
        if (!levelDialog.isShowing()) {
            levelDialog.show();
        }

    }

    private void populateSymbols() {
        watchlistModelsArr.clear();
        commonAdapter.clear();
        int symIndex = 0;
        if (getWatchListResponse != null && groupsAdapter.getCount() > 0) {
            for (int i = 0; i < getWatchListResponse.getGetUserwatchlist().size(); i++) {
                if (getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName().equalsIgnoreCase(AccountDetails.getWatchlistGrp(getMainActivity()))) {
                    groupSpinner.setSelection(groupNameList.indexOf(AccountDetails.getWatchlistGrp(getMainActivity())));
                    symIndex = i;
                    break;
                }
            }
            List<GetUserwatchlist> getWatchlist = getWatchListResponse.getGetUserwatchlist();
            isReponseReceived = true;
            grpAmcName.clear();
            grpCorpIsin.clear();
            grpSchemeCode.clear();
            grpAssetType.clear();

            GetUserwatchlist watchlist = getWatchlist.get(symIndex);
            List<SymbolList> symbolLists = watchlist.getSymbolList();

            commonAdapter.notifyDataSetChanged();
            if (symbolLists.size() == 0) {
                errorMsgLayout.setVisibility(View.VISIBLE);
                errorTextView.setText("No Symbol available");
                errorTextView.setVisibility(View.VISIBLE);
            } else {
                errorMsgLayout.setVisibility(View.GONE);
            }

            int i = 0;
            for (SymbolList item : symbolLists) {
                hm = new LinkedHashMap<>();
                String schemeCode = item.getSchemeCode();
                String isin = item.getIsin();
                String amcname = item.getAmcName();
                String assetType = item.getAssetType();
                grpAmcName.add(amcname);
                grpCorpIsin.add(isin);
                grpSchemeCode.add(schemeCode);
                grpAssetType.add(assetType);


                hm.put(SCHEMENAME, item.getSchemeName());
                hm.put("NAV", item.getNav());
                hm.put("NAVDate", item.getNavDate());
                hm.put("ISIN", item.getIsin());
                hm.put("assetType", item.getAssetType());
                hm.put("schemeCode", item.getSchemeCode());
                hm.put("amcName", item.getAmcName());
                hm.put("oneyr", item.getOneyr());
                hm.put("threeyr", item.getThreeyr());
                hm.put("fiveyr", item.getFiveyr());
                hm.put("incret", item.getIncret());


                String currentSel = types.get(assetTypeSpinner.getSelectedItemPosition());

                if (currentSel.equalsIgnoreCase("Mutual Fund")) {

                    commonAdapter.addHash(hm);
                    CommonRowData commonRow = new CommonRowData();


                    commonRow.setHead1(item.getSchemeName());
                    commonRow.setHead2(item.getNav());
                    commonRow.setHead3(item.getNavDate());
                    commonRow.setHead4(item.getIsin());
                    commonRow.setHead5(item.getSchemeCode());
                    commonRow.setHead6(item.getToken());
                    commonRow.setSubHead1("green");
                    commonAdapter.add(commonRow);

                } else if (item.getAssetType().equalsIgnoreCase(currentSel)) {
                    commonAdapter.addHash(hm);
                    CommonRowData commonRow = new CommonRowData();

                    commonRow.setHead1(item.getSchemeName());
                    commonRow.setHead2(item.getNav());
                    commonRow.setHead3(item.getNavDate());
                    commonRow.setHead4(item.getIsin());
                    commonRow.setHead5(item.getSchemeCode());
                    commonRow.setHead6(item.getToken());
                    commonRow.setSubHead1("green");
                    commonAdapter.add(commonRow);
                }

                commonAdapter.notifyDataSetChanged();

            }

            hideProgress();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getVisibleSymbolTokens(watchlistList.getFirstVisiblePosition(), watchlistList.getLastVisiblePosition());
                    sendStreamingRequest();
                }
            }, 1000);


        }
    }

    //this method will capture the visible list of symbols
    private void getVisibleSymbolTokens(int firstVisibleItem, int totalVisibleCount) {
        visibleSymbolTable.clear();
        if (commonAdapter.getSymbolTable().size() > 0) {
            for (int i = firstVisibleItem; i <= totalVisibleCount; i++) {
                if(commonAdapter.getSymbolTable().size() > totalVisibleCount) {
                    visibleSymbolTable.add(commonAdapter.getSymbolTable().get(i));
                }
            }
        }
    }

    private void getVisibleScrollSymbolTokens(int firstVisibleItem, int totalVisibleCount) {

        visibleSymbolTable.clear();
        if (commonAdapter.getSymbolTable().size() > 0) {
            int totalCount = firstVisibleItem + totalVisibleCount;
            for (int i = firstVisibleItem; i < totalCount; i++) {
                visibleSymbolTable.add(commonAdapter.getSymbolTable().get(i));
            }
        }
    }

    private void getWatchlistGroupName() {
        hideProgress();
        if (getWatchListResponse != null) {
            List<GetUserwatchlist> getWatchlist = getWatchListResponse.getGetUserwatchlist();
            groupNameList.clear();
            groupTypeList.clear();

            for (GetUserwatchlist watchlist : getWatchlist) {
                if (watchlist.getWatchlistName().equalsIgnoreCase("HIGH CONVICTION")) {
                    groupNameList.add("HIGH CONVICTION");
                    groupTypeList.add("default");
                } else if (watchlist.getWatchlistName().equalsIgnoreCase("nifty")) {
                    groupNameList.add("NIFTY");
                    groupTypeList.add("default2");
                } else {
                    groupNameList.add(watchlist.getWatchlistName());
                    groupTypeList.add(watchlist.getWatchtype());
                }
            }

            groupsAdapter.notifyDataSetChanged();

            int selectedIndex = 0;
            if (selectedGrp != null) {
                for (int j = 0; j < groupNameList.size(); j++) {
                    if (selectedGrp.equals(groupNameList.get(j))) {
                        selectedIndex = j;
                    }
                }
            }

            if (addedGroup != null) {
                for (int i = 0; i < groupNameList.size(); i++) {
                    if (addedGroup.equals(groupNameList.get(i))) {
                        selectedIndex = i;
                    }
                }
            }

            if (!AccountDetails.getWatchlistGrp(getMainActivity()).equalsIgnoreCase("")) {
                groupSpinner.setSelection(groupNameList.indexOf(AccountDetails.getWatchlistGrp(getMainActivity())));
            } else {
                groupSpinner.setSelection(selectedIndex);
            }

            if (addedAssetType != null) {
                if (types.indexOf(addedAssetType) != -1) {
                    assetTypeSpinner.setSelection(types.indexOf(addedAssetType));
                }
            }

        }
    }

    private void sendHighConvictionIdeasRequest() {
        showProgress();
        getWatchlistGroupName();
    }


    private void sendGetWatchListRequest() {
        hideAppTitle();
        if (!isWaitingForResponseOnPTR) {
            showProgress();
            isWaitingForResponseOnPTR = true;

            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                PortfolioGetUserWatchListRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()), AccountDetails.getSessionId(getMainActivity()), getMainActivity(), serviceResponseHandler);
            } else {
                PortfolioGetUserWatchListRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getSessionId(getMainActivity()), getMainActivity(), serviceResponseHandler);
            }
        }
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        //hideProgress();
        try {
            if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                if (getWatchListResponse != null && getWatchListResponse.getErrorCode() != null && getWatchListResponse.getErrorCode().equals("3")) {

                    errorMsgLayout.setVisibility(View.VISIBLE);
                    errorTextView.setText("No data available");
                    errorTextView.setVisibility(View.VISIBLE);
                    populateSymbols();
                    hideProgress();
                } else {
                    hideProgress();
                    sendHighConvictionIdeasRequest();

                }

                populateSymbols();
                isReponseReceived = true;
            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "deleteScriptFromWatchlistGroupMF".equals(jsonResponse.getServiceName())) {
                RemoveSymbolFromGroupResponse removeSymbolFromGroupResponse = (RemoveSymbolFromGroupResponse) jsonResponse.getResponse();
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_DELETE_SUCCESS_MSG), "OK", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        //refresh();
                        sendSequenceSaveRequest();

                    }
                });
                getDetailOfGroup(groupSpinner.getSelectedItem().toString());

            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "addNewScriptToWatchlistGroupMF".equals(jsonResponse.getServiceName())) {
                AddSymbolToGroupResponse addsymboltogroupresponse = (AddSymbolToGroupResponse) jsonResponse.getResponse();
                addSymbolToGroupRefresh();

                //hideProgress();
            } else if (REORDER_SAVE_PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && ADD_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
                // AddSymbolToGroupResponse addsymboltogroupresponse = (AddSymbolToGroupResponse) jsonResponse.getResponse();
                showProgress();

                hideProgress();

            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "getWatchlistGroupsNewMF_Mobile".equals(jsonResponse.getServiceName())) {
                hideProgress();
                isReponseReceived = true;
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                groupNameList.clear();
                groupTypeList.clear();
                List<SymbolList> symbolLists = null;
                for (int i = 0; i < getWatchListResponse.getGetUserwatchlist().size(); i++) {


                    groupNameList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                    groupTypeList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                    if (getWatchListResponse.getGetUserwatchlist().get(i).getDefaultFlag().equalsIgnoreCase("true")) {
                        symbolLists = getWatchListResponse.getGetUserwatchlist().get(i).getSymbolList();
                        AccountDetails.setWatchlistGroup(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                        grpPos = i;
                    }

                }
                groupsAdapter.notifyDataSetChanged();

                commonAdapter.clear();
                if (groupTypeList.size() > 0) {
                    groupSpinner.setSelection(grpPos);

                }
                populateGroup(symbolLists);
                hideProgress();
            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && "getWatchlistDataByGroupNameMF".equals(jsonResponse.getServiceName())) {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                populateSymbols();
                hideProgress();
            }
            hideAppTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isWaitingForResponseOnPTR = false;

    }

    private void populateGroup(List<SymbolList> symbolLists) {

        commonAdapter.notifyDataSetChanged();

        if (symbolLists.size() == 0) {
            errorMsgLayout.setVisibility(View.VISIBLE);
            errorTextView.setText("No Symbol available");
            errorTextView.setVisibility(View.VISIBLE);
        } else {
            errorMsgLayout.setVisibility(View.GONE);
        }

        int i = 0;
        for (SymbolList item : symbolLists) {
            hm = new LinkedHashMap<>();
            String schemeCode = item.getSchemeCode();
            String isin = item.getIsin();
            String amcname = item.getAmcName();
            String assetType = item.getAssetType();
            grpAmcName.add(amcname);
            grpCorpIsin.add(isin);
            grpSchemeCode.add(schemeCode);
            grpAssetType.add(assetType);


            hm.put(SCHEMENAME, item.getSchemeName());
            hm.put("NAV", item.getNav());
            hm.put("NAVDate", item.getNavDate());
            hm.put("ISIN", item.getIsin());
            hm.put("assetType", item.getAssetType());
            hm.put("schemeCode", item.getSchemeCode());
            hm.put("amcName", item.getAmcName());
            hm.put("oneyr", item.getOneyr());
            hm.put("threeyr", item.getThreeyr());
            hm.put("fiveyr", item.getFiveyr());
            hm.put("incret", item.getIncret());


            String currentSel = types.get(assetTypeSpinner.getSelectedItemPosition());

            if (currentSel.equalsIgnoreCase("Mutual Fund")) {

                commonAdapter.addHash(hm);
                CommonRowData commonRow = new CommonRowData();

                commonRow.setHead1(item.getSchemeName());
                commonRow.setHead2(item.getNav());
                commonRow.setHead3(item.getNavDate());
                commonRow.setHead4(item.getIsin());
                commonRow.setHead5(item.getSchemeCode());
                commonRow.setHead6(item.getToken());
                commonRow.setSubHead1("green");
                commonAdapter.add(commonRow);

            } else if (item.getAssetType().equalsIgnoreCase(currentSel)) {
                commonAdapter.addHash(hm);
                CommonRowData commonRow = new CommonRowData();

                commonRow.setHead1(item.getSchemeName());
                commonRow.setHead2(item.getNav());
                commonRow.setHead3(item.getNavDate());
                commonRow.setHead4(item.getIsin());
                commonRow.setHead5(item.getSchemeCode());
                commonRow.setHead6(item.getToken());
                commonRow.setSubHead1("green");
                commonAdapter.add(commonRow);
            }


            commonAdapter.notifyDataSetChanged();
        }

        hideProgress();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVisibleSymbolTokens(watchlistList.getFirstVisiblePosition(), watchlistList.getLastVisiblePosition());
                sendStreamingRequest();
            }
        }, 1000);
    }

    private void getDetailOfGroup(String grpname) {
        showProgress();
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            WatchlistDataByGroupNameRequest.sendMFRequest(AccountDetails.getDeviceID(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
        } else {
            WatchlistDataByGroupNameRequest.sendMFRequest(AccountDetails.getClientCode(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
        }
    }

    private void addSymbolToGroupRefresh() {

        String serviceURL = "getTokenDetailsMF?token=" + addToken;

        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    hideProgress();
                    SymbolListModel symbolListModel = new SymbolListModel();
                    List<SymbolList> symbolList = symbolListModel.fromJSON(response);

                    List<SymbolList> symbolLists = null;
                    List<GetUserwatchlist> getWatchlist = getWatchListResponse.getGetUserwatchlist();
                    for (int i = 0; i < getWatchlist.size(); i++) {
                        if (getWatchlist.get(i).getWatchlistName().equalsIgnoreCase(groupSpinner.getSelectedItem().toString())) {
                            getWatchlist.get(i).getSymbolList().addAll(getWatchlist.get(i).getSymbolList().size(), symbolList);
                            symbolLists = getWatchlist.get(i).getSymbolList();
                        }
                    }

                    commonAdapter.clear();
                    commonAdapter.notifyDataSetChanged();
                    if (symbolLists.size() == 0) {
                        errorMsgLayout.setVisibility(View.VISIBLE);
                        errorTextView.setText("No Symbol available");
                        errorTextView.setVisibility(View.VISIBLE);
                    } else {
                        errorMsgLayout.setVisibility(View.GONE);
                    }

                    for (SymbolList item : symbolLists) {
                        hm = new LinkedHashMap<>();
                        String schemeCode = item.getSchemeCode();
                        String isin = item.getIsin();
                        String amcname = item.getAmcName();
                        String assetType = item.getAssetType();
                        grpAmcName.add(amcname);
                        grpCorpIsin.add(isin);
                        grpSchemeCode.add(schemeCode);
                        grpAssetType.add(assetType);


                        hm.put(SCHEMENAME, item.getSchemeName());
                        hm.put("NAV", item.getNav());
                        hm.put("NAVDate", item.getNavDate());
                        hm.put("ISIN", item.getIsin());
                        hm.put("assetType", item.getAssetType());
                        hm.put("schemeCode", item.getSchemeCode());
                        hm.put("amcName", item.getAmcName());
                        hm.put("oneyr", item.getOneyr());
                        hm.put("threeyr", item.getThreeyr());
                        hm.put("fiveyr", item.getFiveyr());
                        hm.put("incret", item.getIncret());


                        String currentSel = types.get(assetTypeSpinner.getSelectedItemPosition());

                        if (currentSel.equalsIgnoreCase("Mutual Fund")) {

                            commonAdapter.addHash(hm);
                            CommonRowData commonRow = new CommonRowData();


                            commonRow.setHead1(item.getSchemeName());
                            commonRow.setHead2(item.getNav());
                            commonRow.setHead3(item.getNavDate());
                            commonRow.setHead4(item.getIsin());
                            commonRow.setHead5(item.getSchemeCode());
                            commonRow.setHead6(item.getToken());
                            commonRow.setSubHead1("green");
                            commonAdapter.add(commonRow);

                        } else if (item.getAssetType().equalsIgnoreCase(currentSel)) {
                            commonAdapter.addHash(hm);
                            CommonRowData commonRow = new CommonRowData();

                            commonRow.setHead1(item.getSchemeName());
                            commonRow.setHead2(item.getNav());
                            commonRow.setHead3(item.getNavDate());
                            commonRow.setHead4(item.getIsin());
                            commonRow.setHead5(item.getSchemeCode());
                            commonRow.setHead6(item.getToken());
                            commonRow.setSubHead1("green");
                            commonAdapter.add(commonRow);
                        }

                        commonAdapter.notifyDataSetChanged();

                    }

                    hideProgress();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getVisibleSymbolTokens(watchlistList.getFirstVisiblePosition(), watchlistList.getLastVisiblePosition());
                            sendStreamingRequest();
                        }
                    }, 1000);


                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_ADDED_SUCCESS_MSG), "OK", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            sendSequenceSaveRequest();
                        }


                    });

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {//todo

                hideProgress();

            }
        });


    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        isWaitingForResponseOnPTR = false;
        super.showMsgOnScreen(action, msg, jsonResponse);
    }


    public void refresh() {
        commonAdapter.clear();
    }


    @Override
    public void onPause() {
        streamController.pauseStreaming(getActivity(), "ltpinfo", visibleSymbolTable);
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }
        sendStreamingRequest();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

    @Override
    public void onFragmentResume() {
        AccountDetails.currentFragment = NAV_TO_MF_WATCHLIST_MF;

        if (EventBus.getDefault().isRegistered(this)) {
            if (visibleSymbolTable != null) {
                if (visibleSymbolTable.size() > 0) {
                    sendStreamingRequest();
                }
            }
        } else {
            EventBus.getDefault().register(this);
            if (visibleSymbolTable != null) {
                if (visibleSymbolTable.size() > 0) {
                    sendStreamingRequest();
                }
            }
        }
        hideAppTitle();

    }

    @Override
    public void onFragmentResult(Object data) {
        hideAppTitle();
        symbolListG.clear();


        if (data != null) {

            Bundle args = (Bundle) data;
            if (args.getString(GOTO) != null) {
                if (grpSchemeCode.contains(args.getString(SCHEMECODE))) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GR_SYMBOL_EXIST_MSG), "Ok", false, null);
                } else {
                    MFSymbolDetail detail = new MFSymbolDetail();
                    detail.setAmcname(args.getString(AMCNAME));
                    detail.setCorpisin(args.getString(CORPISIN));
                    detail.setSchemecode(args.getString(SCHEMECODE));
                    detail.setAssetType(args.getString(ASSET_TYPE));
                    symbolListG.add(detail);
                    addToken = args.getString(SCHEMECODE);
                    addedAssetType = args.getString(ASSET_TYPE);
                    sendSaveSymbolRequest(selectedGrp, selectedGrpType, symbolListG);
                }
            } else if (args.getBoolean("toBeRefreshed")) {
                if (args.getString(ADDED_SYMBOL) != null) addedGroup = args.getString(ADDED_SYMBOL);
                else addedGroup = "";
                if (args.getSerializable("lastResponse") != null) {
                    getWatchListResponse = (PortfolioGetUserWatchListResponse) args.getSerializable("lastResponse");
                    sendGroupNameRequest();
                } else {
                    sendGroupNameRequest();
                }

            }
        }

    }

    private void sendRemoveSymbolRequest(String selectedGrp, String selectedGrpType, List<MFSymbolDetail> symbolListG) {
        Context con = getMainActivity();
        selectedGrpType = "user";
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            RemoveSymbolFromGroupRequest.sendMFRequest(AccountDetails.getDeviceID(getMainActivity()), selectedGrp, AccountDetails.getSessionId(con), symbolListG, selectedGrpType, con, serviceResponseHandler);
        } else {
            RemoveSymbolFromGroupRequest.sendMFRequest(AccountDetails.getClientCode(con), selectedGrp, AccountDetails.getSessionId(con), symbolListG, selectedGrpType, con, serviceResponseHandler);
        }
    }

    private void sendSaveSymbolRequest(String selectedGrp, String selectedGrpType, List<MFSymbolDetail> symbolListG) {
        Context con = getMainActivity();
        selectedGrpType = "user";
        showProgress();
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            AddSymbolToGroupRequest.sendMFRequest(AccountDetails.getDeviceID(getMainActivity()), AccountDetails.getUsername(getMainActivity()), selectedGrp, AccountDetails.getSessionId(con), symbolListG, selectedGrpType, con, serviceResponseHandler);
        } else {
            AddSymbolToGroupRequest.sendMFRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getUsername(getMainActivity()), selectedGrp, AccountDetails.getSessionId(con), symbolListG, selectedGrpType, con, serviceResponseHandler);
        }
    }

    private void sendStreamingRequest() {

        symbolsToUnsubscribe.clear();
        for (int i = 0; i < visibleSymbolTable.size(); i++) {
            symbolsToUnsubscribe.add(visibleSymbolTable.get(i));
        }
        streamController.sendStreamingRequest(getMainActivity(), visibleSymbolTable, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", visibleSymbolTable);
    }

    private void sortBySymbol(boolean isDouble, String sortCol, boolean ascending) {
        if (isDouble) {
            ArrayList<Double> out = new ArrayList<>();

            for (int j = 0; j < hashArray.size(); j++) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) hashArray.get(j);
                String val = (String) linkedHashMap.get(sortCol);
                Double value = Double.valueOf(val);
                out.add(value);
            }

            Collections.sort(out, new Comparator<Double>() {
                public int compare(Double c1, Double c2) {
                    return c1.compareTo(c2);
                }
            });
            if (ascending) {
                Collections.reverse(out);
            }

            ArrayList<HashMap<String, String>> tempHashArray = (ArrayList<HashMap<String, String>>) hashArray.clone();
            commonAdapter.clear();

            for (int i = 0; i < out.size(); i++) {
                Double sortedValue = out.get(i);
                for (int j = 0; j < tempHashArray.size(); j++) {
                    LinkedHashMap sortLinkedMap = (LinkedHashMap) tempHashArray.get(j);
                    String sortColVal = (String) sortLinkedMap.get(sortCol);
                    Double sortColValue = Double.valueOf(sortColVal);
                    int retval = Double.compare(Double.valueOf(sortColVal), sortedValue);
                    if (retval == 0) {
                        commonAdapter.addHash(sortLinkedMap);
                        CommonRowData commonRow = new CommonRowData();

                        commonRow.setHead1(String.valueOf(sortLinkedMap.get("ScripName")));

                        if (String.valueOf(sortLinkedMap.get("assetType")).equalsIgnoreCase("currency")) {
                            commonRow.setHead2(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("NAV")))));
                            commonRow.setHead3(String.valueOf(sortLinkedMap.get("NAVDate")));
                        } else {
                            commonRow.setHead2(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("NAV")))));
                            commonRow.setHead3(String.valueOf(sortLinkedMap.get("NAVDate")));
                        }


                        commonRow.setHead4(String.valueOf(sortLinkedMap.get("ISIN")));
                        commonRow.setHead5(String.valueOf(sortLinkedMap.get("schemeCode")));
                        commonRow.setHead6(String.valueOf(sortLinkedMap.get("Token")));
                        commonRow.setSubHead1("green");
                        commonAdapter.add(commonRow);
                        tempHashArray.remove(sortLinkedMap);
                    }
                }
            }
            commonAdapter.notifyDataSetChanged();
        } else {
            ArrayList<String> out = new ArrayList<>();
            for (int j = 0; j < hashArray.size(); j++) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) hashArray.get(j);
                String val = (String) linkedHashMap.get(sortCol);
                out.add(val);
            }

            Collections.sort(out, new Comparator<String>() {
                public int compare(String c1, String c2) {
                    return c1.compareTo(c2);
                }
            });
            if (ascending) {
                Collections.reverse(out);
            }

            ArrayList<HashMap<String, String>> tempHashArray = (ArrayList<HashMap<String, String>>) hashArray.clone();
            commonAdapter.clear();

            for (int i = 0; i < out.size(); i++) {
                String sortedValue = out.get(i);
                for (int j = 0; j < tempHashArray.size(); j++) {
                    LinkedHashMap sortLinkedMap = (LinkedHashMap) tempHashArray.get(j);
                    String sortColVal = (String) sortLinkedMap.get(sortCol);
                    if (sortColVal.equals(sortedValue)) {
                        commonAdapter.addHash(sortLinkedMap);
                        CommonRowData commonRow = new CommonRowData();
                        commonRow.setHead1(String.valueOf(sortLinkedMap.get("ScripName")));

                        if (String.valueOf(sortLinkedMap.get("assetType")).equalsIgnoreCase("currency")) {
                            commonRow.setHead2(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("NAV")))));
                            commonRow.setHead3(String.valueOf(sortLinkedMap.get("NAVDate")));
                        } else {
                            commonRow.setHead2(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("NAV")))));
                            commonRow.setHead3(String.valueOf(sortLinkedMap.get("NAVDate")));
                        }


                        commonRow.setHead4(String.valueOf(sortLinkedMap.get("ISIN")));
                        commonRow.setHead5(String.valueOf(sortLinkedMap.get("schemeCode")));
                        commonRow.setHead6(String.valueOf(sortLinkedMap.get("Token")));
                        commonRow.setSubHead1("green");
                        commonAdapter.add(commonRow);
                        tempHashArray.remove(sortLinkedMap);
                    }
                }
            }
            commonAdapter.notifyDataSetChanged();
        }

        sendSequenceSaveRequest();

    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("ltpinfo")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();

                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();

                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void updateBroadcastData(StreamerBroadcastResponse response) {

        if (commonAdapter.containsSymbol(response.getSymbol())) {

            boolean notifyNeeded = false;
            ArrayList<Integer> changedCol = new ArrayList<>();

            System.out.println("update Hash array------->>>> " + (Arrays.toString(hashArray.toArray())));

            HashMap<String, String> data = commonAdapter.getItemHash(commonAdapter.indexOf(response.getSymbol()));
            String color = data.get("NewChange");

            if (!data.get("LTP").equals(response.getLast())) {
                changedCol.add(1);
                double old = Double.parseDouble(data.get("LTP"));
                double newd = Double.parseDouble(response.getLast());

                if (old < newd) {
                    color = "green";
                } else if (old > newd) {
                    color = "red";
                }
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.put("LTP", String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.put("LTP", String.format("%.2f", Double.parseDouble(response.getLast())));
                }
                data.put("NewChange", color);
                notifyNeeded = true;
            } else {
                data.put("NewChange", color);


                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {

                    data.put("LTP", String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {

                    data.put("LTP", String.format("%.2f", Double.parseDouble(response.getLast())));
                }
            }

            if (!data.get("Chg (Rs)").equals(response.getChange())) {
                changedCol.add(2);
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {

                    data.put("Chg (Rs)", String.format("%.4f", Double.parseDouble(response.getChange())));
                } else {

                    data.put("Chg (Rs)", String.format("%.2f", Double.parseDouble(response.getChange())));
                }
                notifyNeeded = true;
            } else {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {

                    data.put("Chg (Rs)", String.format("%.4f", Double.parseDouble(data.get("Chg (Rs)"))));

                } else {

                    data.put("Chg (Rs)", String.format("%.2f", Double.parseDouble(data.get("Chg (Rs)"))));
                }

            }

            if (!data.get("Chg (%)").equals(response.getP_change())) {
                changedCol.add(3);

                data.put("Chg (%)", String.format("%.2f", Double.parseDouble(response.getP_change())));
                notifyNeeded = true;
            } else {

                data.put("Chg (%)", String.format("%.2f", Double.parseDouble(data.get("Chg (%)"))));
            }

            commonAdapter.setItem(commonAdapter.indexOf(response.getSymbol()), data);

            if (notifyNeeded) {
                commonAdapter.notifyDataSetChanged();
            }


        }
    }

    @Override
    public void onDragViewStart(int beginPosition) {

//        hash_model = (LinkedHashMap) hashArray.get(beginPosition);
    }

    @Override
    public void onDragDropViewMoved(int fromPosition, int toPosition) {

        int from, to;
        from = fromPosition;


        LinkedHashMap hsh_model = (LinkedHashMap) hashArray.remove(fromPosition);

        hashArray.add(toPosition, hsh_model);
        to = toPosition;

        commonAdapter.updateTokenList(from, to);

    }

    @Override
    public void onDragViewDown(int finalPosition) {
        commonAdapter.notifyDataSetChanged();
        sendStreamingRequest();
        sendSequenceSaveRequest();

    }

    public void sendSequenceSaveRequest() {

        ArrayList<SymbolList> symbolListNew = new ArrayList<>();
        JSONObject data = new JSONObject();
        JSONObject object;
        JSONArray jsonArray = new JSONArray();
        if (getWatchListResponse != null) {

            for (int j = 0; j < getWatchListResponse.getGetUserwatchlist().size(); j++) {
                if (getWatchListResponse.getGetUserwatchlist().get(j).getWatchlistName().equalsIgnoreCase(groupSpinner.getSelectedItem().toString())) {
                    GetUserwatchlist symbolList = getWatchListResponse.getGetUserwatchlist().get(j);
                    for (int i = 0; i < hashArray.size(); i++) {
                        for (int k = 0; k < symbolList.getSymbolList().size(); k++) {
                            Log.e("WatchListFragment", "hashArray===>" + hashArray.get(i).get(GreekConstants.SCRIPTNAME) + "*****" + symbolList.getSymbolList().get(k).getDescription());
                            if (hashArray.get(i).get(SCHEMECODE).equalsIgnoreCase(symbolList.getSymbolList().get(k).getSchemeCode())) {
                                symbolListNew.add(symbolList.getSymbolList().get(k));
                            }
                        }
                    }

                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("all")) {
                        getWatchListResponse.getGetUserwatchlist().get(j).setSymbolList(symbolListNew);
                    }
                }
            }
            for (int i = 0; i < hashArray.size(); i++) {
                object = new JSONObject();


                try {
                    object.put("tradeSymbol", hashArray.get(i).get(GreekConstants.SCRIPTNAME));
                    object.put("exchange", hashArray.get(i).get(GreekConstants.EXCHANGE));
                    object.put("assetType", hashArray.get(i).get(GreekConstants.ASSET_TYPE));
                    object.put("token", hashArray.get(i).get(GreekConstants.TOKEN));
                    object.put("seqNo", i + 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(object);

            }


            try {

                data.put("symbolDetails", jsonArray);
                data.put("gcid", AccountDetails.getClientCode(getMainActivity()));
                data.put("gscid", AccountDetails.getDeviceID(getMainActivity()));
                data.put("groupName", groupSpinner.getSelectedItem());
                data.put("watchlistType", "user");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (addedAssetType.equalsIgnoreCase("All")) {
                WatchlistGroupRequest.sendRequest(data, "addNewScriptToWatchlistGroup", getMainActivity(), serviceResponseHandler);
            }
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onSlideOpen(View view, View view1, int i, int i1) {

    }

    @Override
    public void onSlideClose(View view, View view1, int i, int i1) {

    }

    @Override
    public void onScrollBackAnimationFinished(View view, int i) {

    }


    public interface ListSortListener {
        void onSortListener();
    }

    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        ArrayList<watchlistModel> watchlistModelsArray = new ArrayList<>();
        private final ArrayList<String> tokenList;
        private MFWatchlistFragment.ListSortListener sortListener;
        private final Handler handler = new Handler();
        private int j = 0;


        public CustomAdapter(Context context, ArrayList<HashMap<String, String>> watchlistModels) {
            this.mContext = context;
            hashArray = new ArrayList<>();
            hashArray = watchlistModels;
            tokenList = new ArrayList<>();
            seqNo_hm = new LinkedHashMap<>();

        }

        public void setItem(int position, HashMap<String, String> row) {

            hashArray.set(position, row);

        }

        public void add(CommonRowData commonRow) {

            model = new watchlistModel(commonRow.getHead1(), commonRow.getHead2(), commonRow.getHead3(), commonRow.getHead4(), commonRow.getHead5(), commonRow.getHead6(), commonRow.getSubHead1());
            tokenList.add(commonRow.getHead6());

            watchlistModelsArr.add(model);
        }

        public void updateTokenList(int from, int to) {

            Collections.swap(tokenList, from, to);
        }


        public ArrayList<String> getSymbolTable() {
            return tokenList;
        }

        public void clear() {
            watchlistList.clearAnimation();
            tokenList.clear();
            watchlistModelsArr.clear();
            hashArray.clear();
            notifyDataSetChanged();
        }


        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        public int hasharrayindexOf(String symbol) {
            return hashArray.indexOf(symbol);
        }

        public String tokenOf(int position) {
            return tokenList.get(position);
        }

        @Override
        public int getCount() {
            return watchlistModelsArr.size();
        }

        @Override
        public Object getItem(int position) {

            return watchlistModelsArr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new MFWatchlistFragment.CustomAdapter.Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_table_list, null);

                holder.tvsymbol = convertView.findViewById(R.id.symbolname_text);
                holder.tvltp = convertView.findViewById(R.id.ltp_text);
                holder.tvchange = convertView.findViewById(R.id.change_text);
                holder.tvperchange = convertView.findViewById(R.id.perchange_text);

                holder.strip = convertView.findViewById(R.id.strip);
                convertView.setTag(holder);
            } else {
                holder = (MFWatchlistFragment.CustomAdapter.Holder) convertView.getTag();
            }


            HashMap<String, String> data1 = hashArray.get(position);


            holder.tvsymbol.setText(data1.get("schemeName"));
            holder.tvsymbol.setSelected(true);
            holder.tvsymbol.requestFocus();
            holder.tvltp.setText(data1.get("NAV"));
            holder.tvchange.setText(data1.get("NAVDate"));
            holder.tvperchange.setText(data1.get("ISIN"));


            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvsymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvltp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvperchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvsymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvltp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvperchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }

            return convertView;
        }

        public void addHash(LinkedHashMap<String, String> hm) {
            hashArray.add(hm);

        }

        public HashMap<String, String> getItemHash(int position) {
            return hashArray.get(position);
        }

        public class Holder {
            GreekTextView tvsymbol;
            GreekTextView tvltp;
            GreekTextView tvchange;
            GreekTextView tvperchange;
            GreekTextView strip;
        }
    }
}
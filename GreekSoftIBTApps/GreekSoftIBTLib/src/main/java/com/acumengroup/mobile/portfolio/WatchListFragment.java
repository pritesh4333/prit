package com.acumengroup.mobile.portfolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.AddSymbolToGroupRequest;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.AddSymbolToGroupResponse;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.RemoveSymbolFromGroupRequest;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.RemoveSymbolFromGroupResponse;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.SymbolDetail;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.WatchlistDataByGroupNameRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.WatchlistGroupRequest;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BottomTabScreens.ClickListener;
import com.acumengroup.mobile.BottomTabScreens.ItemTouchHelperAdapter;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.mobile.model.SymbolListModel;
import com.acumengroup.mobile.model.watchlistModel;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.google.gson.Gson;


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

/**
 * Created by Arcadia
 */
public class WatchListFragment extends GreekBaseFragment implements View.OnClickListener {
    public static final String GOTO = "GoTO";
    public static final String TRADE_SYMBOL = "TradeSymbol";
    public static final String EXCHANGE_NAME = "ExchangeName";
    public static final String ASSET_TYPE = "AssetType";
    public static final String ADDED_SYMBOL = "addedSymbol";
    public final ArrayList<String> groupNameList = new ArrayList<>();
    private final ArrayList<String> groupTypeList = new ArrayList<>();
    private final List<String> grpExchangeList = new ArrayList<>();
    private final List<String> grpTradeSymbols = new ArrayList<>();
    private final List<String> grpTokenSymbols = new ArrayList<>();
    private final List<String> grpAssetTypeSymbols = new ArrayList<>();
    private final List<SymbolDetail> symbolListG = new ArrayList<>();
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private final ArrayList<String> symbolsToUnsubscribe = new ArrayList<>();
    private ArrayList<watchlistModel> watchlistModelsArr = new ArrayList<>();
    private final List<String> restrictedGrp = new ArrayList();
    private ArrayList<String> types = new ArrayList<>();
    private Spinner groupSpinner, assetTypeSpinner;
    private LinkedHashMap lMap;
    private ArrayAdapter<String> groupsAdapter;
    private String selectedGrp = "", selectedGrpType = "default", addedGroup = "", addedAssetType = "";
    private boolean isReponseReceived = false;
    private PortfolioGetUserWatchListResponse getWatchListResponse = null;
    private boolean isWaitingForResponseOnPTR = false, isWaitingForResponseOnDelete = false;
    private GreekTextView ltpSort, symText, chgText, perchgText, errorTextView;
    private GreekEditText edt_searchtxt;
    private GreekButton btn_group, btn_create_group;
    private ImageView img_btn;
    private RelativeLayout errorMsgLayout;
    private ImageButton addBtn, editBtn;
    private StreamingController streamController = new StreamingController();
    private LinkedHashMap<String, String> hm;
    private LinkedHashMap<String, Integer> seqNo_hm;
    private RecyclerView watchlistList;
    private String addToken = "";
    private watchlistModel model;
    private MyListAdapter commonAdapter;
    private boolean ltpAsc, symAsc, chgAsc, perchgAsc = false;
    private ArrayList<HashMap<String, String>> hashArray;
    private AlertDialog levelDialog;
    private View portDetView;
    private static int grpPos = 0;
    public static String defaultGroupName = "";
    private int check = 0;
    //private SwipeRefreshLayout swipeRefresh;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout line1, line2, line3, line4, search_layout;
    private GreekTextView txt_grptitle1, txt_grptitle2, txt_grptitle3, txt_grptitle4;
    private ImageView img_btn1, img_btn2, img_btn3, img_btn4;
    private String sharedprefWatchList = "";
    public static HashMap<String, List<SymbolList>> hashMapWatchListData = new HashMap<String, List<SymbolList>>();
    private PopupWindow popupWindow;


//    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//        @Override
//        public void onRefresh() {
//            commonAdapter.clear();
//            getDetailOfGroup(btn_group.getText().toString());
//        }
//    };
//
//    private void refreshComplete() {
//        hideProgress();
//        if(swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
//    }

    private final AdapterView.OnItemSelectedListener groupsItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (++check > 1) {
                selectedGrp = groupSpinner.getSelectedItem().toString();
                addedGroup = null;
                selectedGrpType = groupTypeList.get(position);
                AccountDetails.setWatchlistGroup(selectedGrp);
                getDetailOfGroup(btn_group.getText().toString());
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
            if (isReponseReceived)
                populateSymbols(getWatchListResponse.getGetUserwatchlist().get(position).getSymbolList());
            if (commonAdapter.getItemCount() <= 0) {
                errorMsgLayout.setVisibility(View.VISIBLE);
                errorTextView.setText("No Symbol available");
                errorTextView.setVisibility(View.VISIBLE);
            }
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
                navigateTo(NAV_TO_ADD_NEW_PORTFOLIO_SCREEN, bundle, true);
            } else
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_MAX_GROUP_ADD_MSG), "Ok", true, null);
            levelDialog.dismiss();
            //Add new symbol
        } else if (id == R.id.action_item2) {
            if (groupsAdapter.getCount() > 0) {
                selectedGrp = btn_group.getText().toString();
                selectedGrpType = groupTypeList.get(groupSpinner.getSelectedItemPosition());
                int selectedAsset = assetTypeSpinner.getSelectedItemPosition();
                Bundle bundle = new Bundle();
                bundle.putString("Source", "Watchlist");
                bundle.putInt("selectedAssetForSymbol", selectedAsset);
                navigateTo(NAV_TO_SYMBOL_SEARCH_SCREEN, bundle, true);
            } else
                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_NO_GROUP_EXIST_MSG), "Ok", true, null);
            levelDialog.dismiss();
            //}
        } else if (id == R.id.pop_quote) {
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Confirm Delete scrip?", "Yes", "No", true, new GreekDialog.DialogListener() {


                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    if (action.name().equalsIgnoreCase("ok")) {
                        symbolListG.clear();
                        String tradeSymbol = (String) lMap.get("OriginalSymbol");
                        String currToken = (String) lMap.get("Token");
                        for (int i = 0; i < grpExchangeList.size(); i++) {
                            if (grpTokenSymbols.get(i).equals(currToken)) {
                                SymbolDetail detail = new SymbolDetail();
                                detail.setExchange(grpExchangeList.get(i));
                                detail.setTradeSymbol(grpTradeSymbols.get(i));
                                detail.setToken(grpTokenSymbols.get(i));
                                detail.setAssetType(grpAssetTypeSymbols.get(i));
                                symbolListG.add(detail);
                            }
                        }

                        selectedGrp = btn_group.getText().toString();
                        addedAssetType = assetTypeSpinner.getSelectedItem().toString();
                        sendRemoveSymbolRequest(selectedGrp, groupTypeList.get(groupSpinner.getSelectedItemPosition()), symbolListG);
                    } else if (action.name().equalsIgnoreCase("cancel")) {
                    }
                }
            });
            levelDialog.dismiss();
        } else if (id == R.id.pop_advice) {
            Bundle args = new Bundle();
            args.putString("ScripName", (String) lMap.get(GreekConstants.SCRIPTNAME));
            args.putString("Description", (String) lMap.get(GreekConstants.DESCRIPTION));
            args.putString("ExchangeName", (String) lMap.get(GreekConstants.EXCHANGE));
            args.putString("Token", (String) lMap.get(GreekConstants.TOKEN));
            args.putString("AssetType", (String) lMap.get(GreekConstants.ASSET_TYPE));
            args.putString("UniqueId", (String) lMap.get(GreekConstants.UNIQUEID));
            args.putString("TradeSymbol", (String) lMap.get(GreekConstants.SYMBOL));
            args.putString("Lots", (String) lMap.get(GreekConstants.LOT));
            args.putString("TickSize", (String) lMap.get(GreekConstants.TICKSIZE));
            args.putString("Multiplier", (String) lMap.get(GreekConstants.MULTIPLIER));
            args.putString("SelectedExp", (String) lMap.get(GreekConstants.EXPIRYDATE));
            args.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp((String) lMap.get(GreekConstants.EXPIRYDATE), "dd MMM yyyy", "bse"));
            args.putString("StrikePrice", (String) lMap.get(GreekConstants.STRICKPRICE));
            args.putString("InstType", (String) lMap.get(GreekConstants.INSTRUMENTNAME));
            if (lMap.get("InstrumentName").toString().contains("OPT")) {
                args.putString("SelectedOpt", (String) lMap.get("optionType"));
                if (((String) lMap.get("optionType")).equalsIgnoreCase("ce")) {
                    args.putString("optionType", "Call");
                } else {
                    args.putString("optionType", "Put");
                }
            }
            args.putString("From", "Watchlist");
            navigateTo(NAV_TO_QUOTES_SCREEN, args, true);
            levelDialog.dismiss();
            //Trade
        } else if (id == R.id.pop_trade) {
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

            levelDialog.dismiss();
        } else if (id == R.id.pop_trade_sell) {
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

            levelDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        portDetView = super.onCreateView(inflater, container, savedInstanceState);
        attachLayout(R.layout.fragment_watchlist_new).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        AccountDetails.currentFragment = NAV_TO_WATCHLIST_SCREEN;
        setupView(portDetView);
        setTheam();
        sendGroupNameRequest();


        return portDetView;
    }


    private void setupView(View parent) {
        try {
            hideAppTitle();
            restrictedGrp.clear();
            restrictedGrp.add("NSE");
            restrictedGrp.add("BSE");
            restrictedGrp.add("MCX");
            restrictedGrp.add("NCDEX");
            errorTextView = parent.findViewById(R.id.errorHeader);
            search_layout = parent.findViewById(R.id.search_layout);
            btn_group = parent.findViewById(R.id.btn_group);
            ltpSort = parent.findViewById(R.id.ltpText);
            img_btn = parent.findViewById(R.id.img_btn);
            symText = parent.findViewById(R.id.symbolText);
            chgText = parent.findViewById(R.id.changeText);
            perchgText = parent.findViewById(R.id.perchangeText);
            edt_searchtxt = parent.findViewById(R.id.edt_searchtxt);
            errorMsgLayout = parent.findViewById(R.id.showmsgLayout);

//        swipeRefresh = parent.findViewById(R.id.refreshList);
//        swipeRefresh.setOnRefreshListener(onRefreshListener);
            selectedGrp = btn_group.getText().toString();

            btn_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    LayoutInflater layoutInflater = (LayoutInflater) getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View popupView = layoutInflater.inflate(R.layout.layout_create_watchlist, null);
                    popupWindow = new PopupWindow(
                            popupView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    LinearLayout watchlist_popup = popupView.findViewById(R.id.watchlist_popup);


                    line1 = popupView.findViewById(R.id.line1);
                    line2 = popupView.findViewById(R.id.line2);
                    line3 = popupView.findViewById(R.id.line3);
                    line4 = popupView.findViewById(R.id.line4);


                    txt_grptitle1 = popupView.findViewById(R.id.txt_grptitle1);
                    txt_grptitle2 = popupView.findViewById(R.id.txt_grptitle2);
                    txt_grptitle3 = popupView.findViewById(R.id.txt_grptitle3);
                    txt_grptitle4 = popupView.findViewById(R.id.txt_grptitle4);

                    txt_grptitle1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            btn_group.setText(txt_grptitle1.getText().toString());
                            getDetailOfGroup(btn_group.getText().toString());
                        }
                    });
                    txt_grptitle2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            btn_group.setText(txt_grptitle2.getText().toString());
                            getDetailOfGroup(btn_group.getText().toString());
                        }
                    });
                    txt_grptitle3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            btn_group.setText(txt_grptitle3.getText().toString());
                            getDetailOfGroup(btn_group.getText().toString());
                        }
                    });
                    txt_grptitle4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            btn_group.setText(txt_grptitle4.getText().toString());
                            getDetailOfGroup(btn_group.getText().toString());

                        }
                    });


                    img_btn1 = popupView.findViewById(R.id.img_btn1);
                    img_btn2 = popupView.findViewById(R.id.img_btn2);
                    img_btn3 = popupView.findViewById(R.id.img_btn3);
                    img_btn4 = popupView.findViewById(R.id.img_btn4);
                    btn_create_group = popupView.findViewById(R.id.btn_create_group);


                    if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {

                        watchlist_popup.setBackgroundColor(getResources().getColor(R.color.white_subheading));
                        line1.setBackgroundColor(getResources().getColor(R.color.white));
                        line2.setBackgroundColor(getResources().getColor(R.color.white));
                        line3.setBackgroundColor(getResources().getColor(R.color.white));
                        line4.setBackgroundColor(getResources().getColor(R.color.white));
                        txt_grptitle1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        txt_grptitle2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        txt_grptitle3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        txt_grptitle4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                        img_btn1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
                        img_btn2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
                        img_btn3.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
                        img_btn4.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(AccountDetails.textColorDropdown)));
                        //btn_create_group.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));


                        if (groupNameList.size() == 4) {
                            btn_create_group.setEnabled(false);
                            btn_create_group.setBackground(getResources().getDrawable(R.drawable.single_line_border_bajaj_gray));

                        } else {
                            btn_create_group.setEnabled(true);
                            btn_create_group.setBackground(getResources().getDrawable(R.drawable.single_line_border_bajaj));

                        }
                    } else {

                        if (groupNameList.size() == 4) {
                            btn_create_group.setEnabled(false);

                            btn_create_group.setBackground(getResources().getDrawable(R.drawable.single_line_border_bajaj_gray));
                        } else {
                            btn_create_group.setEnabled(true);
                            btn_create_group.setBackground(getResources().getDrawable(R.drawable.single_line_border_bajaj));

                        }
                    }
                    setGroupNames();


                    btn_create_group.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            ViewGroup viewGroup = getMainActivity().findViewById(android.R.id.content);

                            //then we will inflate the custom alert dialog xml that we created
                            View dialogView = LayoutInflater.from(getMainActivity()).inflate(R.layout.create_watchlist_layout, viewGroup, false);

                            LinearLayout create_watchlist = dialogView.findViewById(R.id.create_watchlist);
                            final GreekEditText edt_groupName = dialogView.findViewById(R.id.edt_groupName);
                            GreekButton btn_confirm = dialogView.findViewById(R.id.btn_confirm);
                            GreekButton btn_cancel = dialogView.findViewById(R.id.btn_cancel);
                            GreekTextView create_watchlist_name = dialogView.findViewById(R.id.create_watchlist_name);

                            edt_groupName.setText("WatchList" + (groupNameList.size() + 1));
                            int position = edt_groupName.length();
                            Editable etext = edt_groupName.getText();
                            Selection.setSelection(etext, position);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

                            builder.setView(dialogView);
                            final AlertDialog alertDialog = builder.create();

                            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                                create_watchlist.setBackgroundColor(getResources().getColor(R.color.white_subheading));
                                edt_groupName.setBackgroundColor(getResources().getColor(R.color.white));
                                edt_groupName.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                                edt_groupName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                                create_watchlist_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                                //btn_confirm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                                btn_cancel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                                //btn_confirm.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                                btn_cancel.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));

                            }


                            btn_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (edt_groupName.getText().toString().trim().length() > 0) {

                                        ArrayList grpName = new ArrayList();
                                        for (int i = 0; i < groupNameList.size(); i++) {

                                            grpName.add(groupNameList.get(i).toLowerCase().trim());
                                        }
                                        if (!restrictedGrp.contains(edt_groupName.getText().toString().toLowerCase().trim().toUpperCase())) {

                                            if (!grpName.contains(edt_groupName.getText().toString().toLowerCase().trim())) {
                                                showProgress();
                                                String groupAddURL;
                                                if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                                                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                                                        groupAddURL = "createGroupForWatchlist_Redis?clientCode=" + AccountDetails.getDeviceID(getMainActivity()) + "&gscid=" + AccountDetails.getDeviceID(getMainActivity()) + "&groupName=" + edt_groupName.getText().toString().trim() + "&watchlistType=user";
                                                    } else {
                                                        groupAddURL = "createGroupForWatchlist_Redis?clientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&groupName=" + edt_groupName.getText().toString().trim() + "&watchlistType=user";
                                                    }
                                                } else {
                                                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                                                        groupAddURL = "createGroupForWatchlist?clientCode=" + AccountDetails.getDeviceID(getMainActivity()) + "&gscid=" + AccountDetails.getDeviceID(getMainActivity()) + "&groupName=" + edt_groupName.getText().toString().trim() + "&watchlistType=user";
                                                    } else {
                                                        groupAddURL = "createGroupForWatchlist?clientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&groupName=" + edt_groupName.getText().toString().trim() + "&watchlistType=user";
                                                    }
                                                }
                                                //to create group send request
                                                WSHandler.getRequest(getMainActivity(), groupAddURL, new WSHandler.GreekResponseCallback() {
                                                    @Override
                                                    public void onSuccess(JSONObject response) {
                                                        try {
                                                            hideProgress();
                                                            popupWindow.dismiss();
                                                            //Add empty symbole list in hashmap
                                                            List<SymbolList> symbolLists = new ArrayList<>();
                                                            if (hashMapWatchListData.keySet().size() == 0) {

                                                                defaultGroupName = edt_groupName.getText().toString();

                                                            }
                                                            hashMapWatchListData.put(edt_groupName.getText().toString(), symbolLists);


                                                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_ADDED_SUCCESS_MSG), "OK", false, new GreekDialog.DialogListener() {

                                                                @Override
                                                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                                                    alertDialog.cancel();
                                                                    AccountDetails.setLastSelectedGroup(edt_groupName.getText().toString().trim());
                                                                    //sendGroupNameRequest();
                                                                    groupNameList.add(edt_groupName.getText().toString().trim());
                                                                    btn_group.setText(edt_groupName.getText().toString().trim());
                                                                    getDetailOfGroup(edt_groupName.getText().toString().trim());
                                                                }
                                                            });
                                                        } catch (Exception e) {
                                                            //toggleErrorLayout(true);
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(String message) {
                                                        //toggleErrorLayout(true);
                                                        //refreshComplete();
                                                    }
                                                });
                                            } else
                                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_GROUP_NAME_EXIST_MSG), "OK", true, new GreekDialog.DialogListener() {

                                                    @Override
                                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                                        edt_groupName.setText("");
                                                    }
                                                });
                                        } else {
                                            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_GROUP_NAME_ERROR_MSG), "OK", true, new GreekDialog.DialogListener() {

                                                @Override
                                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                                    edt_groupName.setText("");
                                                }
                                            });
                                        }
                                    } else
                                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_GROUP_NAME_CANT_EMPTY_MSG), "OK", true, null);


                                }
                            });

                            btn_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    alertDialog.cancel();
                                }
                            });

                            alertDialog.show();


                        }
                    });

                    img_btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();

                            showAlertConFirmation(line1, txt_grptitle1.getText().toString());
                        }
                    });
                    img_btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            showAlertConFirmation(line2, txt_grptitle2.getText().toString());
                        }
                    });
                    img_btn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            showAlertConFirmation(line3, txt_grptitle3.getText().toString());
                        }
                    });
                    img_btn4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            showAlertConFirmation(line4, txt_grptitle4.getText().toString());
                        }
                    });

                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            //TODO do sth here on dismiss
                        }
                    });
                    try {
                        popupWindow.showAsDropDown(view);

                    } catch (Exception e) {

                    }


                }
            });


            groupNameList.clear();
            groupSpinner = parent.findViewById(R.id.groupSpinner);
            groupsAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), groupNameList);
            groupsAdapter.setDropDownViewResource(R.layout.custom_spinner);
            groupSpinner.setAdapter(groupsAdapter);
            groupSpinner.setOnItemSelectedListener(groupsItemSelectedListener);

            watchlistList = parent.findViewById(R.id.watchlist_list);
            linearLayoutManager = new LinearLayoutManager(getMainActivity());
            watchlistList.setLayoutManager(linearLayoutManager);
            ((SimpleItemAnimator) watchlistList.getItemAnimator()).setSupportsChangeAnimations(false);
            watchlistList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));


            final Paint p = new Paint();
            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                    commonAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                    sendSequenceSaveRequest();
                    return true;
                }

                @Override
                public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    return super.getSwipeDirs(recyclerView, viewHolder);
                }


                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();

                    if (commonAdapter != null) {

                        lMap = (LinkedHashMap) commonAdapter.getItemHash(position);
                        if (direction == ItemTouchHelper.RIGHT) {
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

                            AccountDetails.globalPlaceOrderBundle = args2;
                            EventBus.getDefault().post("placeorder");

                            commonAdapter.notifyDataSetChanged();

                        } else if (direction == ItemTouchHelper.LEFT) {

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
                            AccountDetails.globalPlaceOrderBundle = args3;
                            EventBus.getDefault().post("placeorder");

                            commonAdapter.notifyDataSetChanged();

                        }
                    }
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    // left right swipe
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        View itemView = viewHolder.itemView;
                        if (dX > 0) {


                            RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (300 - 20), itemView.getBottom());
                            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                                p.setColor(getActivity().getResources().getColor(R.color.whitetheambuyColor));
                            }else {
                                p.setColor(getActivity().getResources().getColor(R.color.buyColor));
                            }
                            c.drawRoundRect(leftButton, 0, 0, p);
                            drawText("Buy", c, leftButton, p);


                        } else if (dX < 0) {

                            RectF rightButton = new RectF(itemView.getRight() - (300 - 20), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                            p.setColor(getActivity().getResources().getColor(R.color.sellColor));
                            c.drawRoundRect(rightButton, 0, 0, p);
                            drawText("Sell", c, rightButton, p);


                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(watchlistList);

            img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup;
                    Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);

                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                        popup = new PopupMenu(wrapper, img_btn);
                    } else {
                        popup = new PopupMenu(getMainActivity(), img_btn);
                    }

                    popup.getMenuInflater().inflate(R.menu.watchlist_pop_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(android.view.MenuItem item) {

                            if (item.getItemId() == R.id.one) {

                                if (symAsc) {
                                    sortBySymbol(false, "Symbol", true);
                                    symAsc = false;
                                } else {
                                    sortBySymbol(false, "Symbol", false);
                                    symAsc = true;
                                }
                                getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                                sendStreamingRequest();

                            } else if (item.getItemId() == R.id.two) {


                                if (perchgAsc) {
                                    sortBySymbol(true, "Chg (%)", true);
                                    perchgAsc = false;
                                } else {
                                    sortBySymbol(true, "Chg (%)", false);
                                    perchgAsc = true;
                                }
                                getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                                sendStreamingRequest();


                            } else if (item.getItemId() == R.id.three) {
                                if (ltpAsc) {
                                    sortBySymbol(true, "LTP", true);
                                    ltpAsc = false;
                                } else {
                                    sortBySymbol(true, "LTP", false);
                                    ltpAsc = true;
                                }
                                getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                                sendStreamingRequest();


                            }

                            return true;
                        }
                    });

                    popup.show();


                }
            });


            try {

                commonAdapter = new MyListAdapter(getMainActivity(), new ArrayList<HashMap<String, String>>());
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
            ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), types);
            assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
            assetTypeSpinner.setAdapter(assetTypeAdapter);
            assetTypeSpinner.setOnItemSelectedListener(typeSelectedListener);

            addBtn = parent.findViewById(R.id.addBtn);

            edt_searchtxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (btn_group.getText().toString().equalsIgnoreCase("") || btn_group.getText().toString().equalsIgnoreCase("Create Watchlist") || btn_group.getText().toString().length() <= 0) {


                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.GR_GROUP_NAME_CANT_EMPTY_MSG), "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            }
                        });

                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("Source", "Watchlist");
                    bundle.putInt("selectedAssetForSymbol", 0);
                    navigateTo(NAV_TO_SYMBOL_SEARCH_SCREEN, bundle, true);
                }
            });

            watchlistList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), watchlistList, new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    if (commonAdapter != null) {

                        lMap = (LinkedHashMap) commonAdapter.getItemHash(position);
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

                        AccountDetails.globalPlaceOrderBundle = args2;
                        EventBus.getDefault().post("placeorder");

                    }
                }


                @Override
                public void onLongClick(View view, int position) {


                }
            }));

            ltpSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ltpAsc) {
                        sortBySymbol(true, "LTP", true);
                        ltpAsc = false;
                    } else {
                        sortBySymbol(true, "LTP", false);
                        ltpAsc = true;
                    }
                    getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                    sendStreamingRequest();
                }
            });

            symText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (symAsc) {
                        sortBySymbol(false, "Symbol", true);
                        symAsc = false;
                    } else {
                        sortBySymbol(false, "Symbol", false);
                        symAsc = true;
                    }
                    getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                    sendStreamingRequest();
                }
            });

            chgText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chgAsc) {
                        sortBySymbol(true, "Chg (Rs)", true);
                        chgAsc = false;
                    } else {
                        sortBySymbol(true, "Chg (Rs)", false);
                        chgAsc = true;
                    }
                    getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                    sendStreamingRequest();
                }
            });

            perchgText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (perchgAsc) {
                        sortBySymbol(true, "Chg (%)", true);
                        perchgAsc = false;
                    } else {
                        sortBySymbol(true, "Chg (%)", false);
                        perchgAsc = true;
                    }
                    getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                    sendStreamingRequest();
                }
            });
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getWatchListResponse != null) {
                        if (groupsAdapter.getCount() > 0) {
                            showQuickActionPopup(v);
                        } else {

                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("Groups", groupNameList);
                            navigateTo(NAV_TO_ADD_NEW_PORTFOLIO_SCREEN, bundle, true);
                        }
                    }
                }
            });

            editBtn = parent.findViewById(R.id.editBtn);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getWatchListResponse != null) {
                        if (groupsAdapter.getCount() > 0) {

                            selectedGrp = btn_group.getText().toString();
                            selectedGrpType = groupTypeList.get(groupSpinner.getSelectedItemPosition());

                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("grpnamelist", groupTypeList);
                            bundle.putSerializable("Response", getWatchListResponse);
                            bundle.putInt("SelectedGrp", groupSpinner.getSelectedItemPosition());
                            navigateTo(NAV_TO_EDIT_WATCHLIST_SCREEN, bundle, true);
                        }
                    }
                }
            });

            watchlistList.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    switch (newState) {
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
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    getVisibleScrollSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(),
                            linearLayoutManager.findLastVisibleItemPosition());
                }
            });

            Bundle args = new Bundle();
            args.putInt("Source", 1);

        } catch (Exception e) {

        }

    }

    private void setTheam() {
        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            search_layout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            edt_searchtxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.single_line_w_border_white));
            // btn_group.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //btn_group.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            img_btn.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

            img_btn.setBackground(getResources().getDrawable(R.drawable.ic_filter));
            watchlistList.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

            errorTextView.setTextColor(getActivity().getResources().getColor(AccountDetails.textColorDropdown));

        }
    }

    public void showAlertConFirmation(final LinearLayout layout, final String groupTitle) {
        try {
            ViewGroup viewGroup = getMainActivity().findViewById(android.R.id.content);

            View dialogView = LayoutInflater.from(getMainActivity()).inflate(R.layout.confirm_delete_layout, viewGroup, false);

            LinearLayout delete_popup = dialogView.findViewById(R.id.delete_popup);
            GreekTextView txt_mreesg = dialogView.findViewById(R.id.txt_msg);
            GreekTextView delete_text = dialogView.findViewById(R.id.delete_text);

            GreekButton btn_confirm = dialogView.findViewById(R.id.btn_confirm);
            GreekButton btn_cancel = dialogView.findViewById(R.id.btn_cancel);


            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();

            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                delete_popup.setBackgroundColor(getResources().getColor(R.color.white));
                txt_mreesg.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                delete_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                // btn_confirm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                btn_cancel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                //btn_confirm.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                btn_cancel.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));


            }

            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    layout.setVisibility(View.GONE);
                    alertDialog.cancel();

                    showProgress();
                    String groupAddURL;
                    if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                            groupAddURL = "deleteGroupForWatchlistV2_Redis?gscid=" + AccountDetails.getDeviceID(getMainActivity()) + "&groupName=" + groupTitle + "&watchlistType=user";
                        } else {
                            groupAddURL = "deleteGroupForWatchlistV2_Redis?gscid=" + AccountDetails.getUsername(getMainActivity()) + "&groupName=" + groupTitle + "&watchlistType=user";
                        }
                    } else {
                        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                            groupAddURL = "deleteGroupForWatchlistV2?gscid=" + AccountDetails.getDeviceID(getMainActivity()) + "&groupName=" + groupTitle + "&watchlistType=user";
                        } else {
                            groupAddURL = "deleteGroupForWatchlistV2?gscid=" + AccountDetails.getUsername(getMainActivity()) + "&groupName=" + groupTitle + "&watchlistType=user";
                        }
                    }
                    //to delete group we are sending request to aracane server.
                    WSHandler.getRequest(getMainActivity(), groupAddURL, new WSHandler.GreekResponseCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            //toggleErrorLayout(false);
                            try {
                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_SUCCESSFUL_MSG), "OK", false, null);

                                groupNameList.remove(groupTitle);
                                //                            sendGroupNameRequest();
                                hashMapWatchListData.remove(groupTitle);

                                if (groupNameList.size() > 0) {
                                    getDetailOfGroup(groupNameList.get(0));
                                    defaultGroupName = groupNameList.get(0);
                                    AccountDetails.setLastSelectedGroup(groupNameList.get(0));
                                } else {
                                    AccountDetails.setLastSelectedGroup("");
                                    commonAdapter.clear();
                                    errorMsgLayout.setVisibility(View.VISIBLE);
                                    errorTextView.setText("No data available");
                                    btn_group.setText("Create Watchlist");
                                    errorTextView.setVisibility(View.VISIBLE);
                                }
                                groupsAdapter.notifyDataSetChanged();
                                commonAdapter.notifyDataSetChanged();
                                hideProgress();
                            } catch (Exception e) {
                                //toggleErrorLayout(true);
                                e.printStackTrace();
                            }
                            //refreshComplete();
                        }

                        @Override
                        public void onFailure(String message) {
                        }
                    });


                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alertDialog.cancel();

                }
            });

            alertDialog.show();

        } catch (Exception e) {

        }

    }

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

    public void sendGroupNameRequest() {

//        showProgress();
//        if(GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
//            WatchlistGroupRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()), getMainActivity(), serviceResponseHandler);
//        } else {
//            WatchlistGroupRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);
//        }

        // store the watchlist group in shared preference
        sharedprefWatchList = Util.getPrefs(getActivity()).getString("WatchlistGroupsNew", "");

        if (!sharedprefWatchList.equalsIgnoreCase(" ") && !sharedprefWatchList.isEmpty()) {
            //Very 1st time coming on watchlist screen , so use loaded data from sharedPref.
            try {

                JSONObject jsonObject = new JSONObject(sharedprefWatchList);

                SharedPreferences.Editor editor1 = Util.getPrefs(getActivity()).edit();
                editor1.putString("WatchlistGroupsNew", " ");
                editor1.apply();
                editor1.commit();

                getWatchListResponse = new PortfolioGetUserWatchListResponse();


                List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                int error = jsonObject1.getInt("ErrorCode");
                JSONArray jsonArray = jsonObject2.getJSONArray("getwatchlistdata");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String watchlistName = object.getString("watchlistName");
                    String watchtype = object.getString("watchtype");
                    String defaults = object.getString("default");
                    JSONArray symbolLisst = object.getJSONArray("symbolList");
                    GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
                    getUserwatchlist.setWatchlistName(watchlistName);
                    getUserwatchlist.setWatchtype(watchtype);
                    getUserwatchlist.setDefaultFlag(defaults);

                    List<SymbolList> symbolList = new ArrayList<>();
                    for (int k = 0; k < symbolLisst.length(); k++) {
                        JSONObject jsonObject3 = symbolLisst.getJSONObject(k);
                        SymbolList symbolList1 = new SymbolList();
                        symbolList1.setLot(jsonObject3.getString("lot"));
                        symbolList1.setExchange(jsonObject3.getString("exchange"));
                        symbolList1.setP_change(jsonObject3.getString("p_change"));
                        symbolList1.setInstrumentName(jsonObject3.getString("instrumentName"));
                        symbolList1.setScriptName(jsonObject3.getString("ScriptName"));
                        symbolList1.setToken(jsonObject3.getString("token"));
                        symbolList1.setMultiplier(jsonObject3.getString("multiplier"));
                        symbolList1.setChange(jsonObject3.getString("change"));
                        symbolList1.setAssetType(jsonObject3.getString("assetType"));
                        symbolList1.setClose(jsonObject3.getString("close"));
                        symbolList1.setTradeSymbol(jsonObject3.getString("tradeSymbol"));
                        symbolList1.setTickSize(jsonObject3.getString("tickSize"));
                        symbolList1.setDescription(jsonObject3.getString("description"));
                        symbolList1.setLtp(jsonObject3.getString("ltp"));
                        symbolList1.setOptionType(jsonObject3.getString("optionType"));
                        symbolList1.setStrickPrice(jsonObject3.getString("strickPrice"));
                        symbolList1.setExpiryDate(jsonObject3.getString("expiryDate"));
                        symbolList1.setSeqNo(jsonObject3.getString("seqNo"));

                        symbolList.add(symbolList1);

                    }
                    getUserwatchlist.setSymbolList(symbolList);

                    getUserwatchlists.add(getUserwatchlist);

                }


                getWatchListResponse.setGetUserwatchlist(getUserwatchlists);
                getWatchListResponse.setErrorCode(String.valueOf(error));

            } catch (Exception e) {
                e.printStackTrace();
            }

            isReponseReceived = true;

            groupNameList.clear();
            groupTypeList.clear();

            List<SymbolList> symbolLists = null;

            if (getWatchListResponse.getGetUserwatchlist().size() <= 0 ||
                    getWatchListResponse.getErrorCode() != null &&
                            getWatchListResponse.getErrorCode().equalsIgnoreCase("3")) {

            }

            for (int i = 0; i < getWatchListResponse.getGetUserwatchlist().size(); i++) {

                groupNameList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                groupTypeList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());

                if (getWatchListResponse.getGetUserwatchlist().get(i).getDefaultFlag().equalsIgnoreCase("true")) {
                    symbolLists = getWatchListResponse.getGetUserwatchlist().get(i).getSymbolList();
                    hashMapWatchListData.put(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName(), symbolLists);
                    AccountDetails.setWatchlistGroup(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                    grpPos = i;
                    defaultGroupName = getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName();
                    btn_group.setText(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                    AccountDetails.setLastSelectedGroup(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());

                } else {
                    //Default group false , at least group will be added in hashmap list.
                    // IN CAse of empty symbolList , it will add group with empty list.
                    List<SymbolList> symbolList = getWatchListResponse.getGetUserwatchlist().get(i).getSymbolList();

                    if (symbolList != null) {
                        hashMapWatchListData.put(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName(),
                                symbolList);
                    }

                }
            }

            groupsAdapter.notifyDataSetChanged();
            commonAdapter.clear();
            if (groupTypeList.size() > 0) {
                groupSpinner.setSelection(grpPos);
            }

            if (getWatchListResponse.getErrorCode() != null && getWatchListResponse.getErrorCode().equalsIgnoreCase("3")) {
                errorMsgLayout.setVisibility(View.VISIBLE);
                errorTextView.setText("No data available");
                btn_group.setText("Create Watchlist");
                errorTextView.setVisibility(View.VISIBLE);
            } else {
                if (symbolLists != null) {
                    populateGroup(symbolLists);
                }
            }


        } else {
            // Now use already Store hashmap object for watchlist data
            if (hashMapWatchListData.size() > 0) {
                groupNameList.clear();

                if (hashMapWatchListData != null) {
                    for (String key : hashMapWatchListData.keySet()) {
                        System.out.println(key);
                        groupNameList.add(key);

                    }
                }
                if (AccountDetails.getLastSelectedGroup().isEmpty()) {
                    getDetailOfGroup(defaultGroupName);
                } else {
                    getDetailOfGroup(AccountDetails.getLastSelectedGroup());
                }

            } else {
                // showing data from new stored sp if hashmap has no data
                String hasmpatdatalist = Util.getPrefs(getActivity()).getString("HMapWatchlist" + AccountDetails.getUsername(getActivity()), "");
                Log.e("HashmapWatchlistData", hasmpatdatalist);

                if (!hasmpatdatalist.isEmpty() && !hasmpatdatalist.equalsIgnoreCase(" ")) {
                    try {

                        JSONObject jsonObject = new JSONObject(hasmpatdatalist);


                        getWatchListResponse = new PortfolioGetUserWatchListResponse();


                        List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();

                        int error = jsonObject.getInt("errorCode");
                        JSONArray jsonArray = jsonObject.getJSONArray("getUserwatchlist");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String watchlistName = object.getString("watchlistName");
                            String watchtype = object.getString("watchtype");
                            String defaults = object.getString("defaultFlag");
                            JSONArray symbolLisst = object.getJSONArray("symbolList");
                            GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
                            getUserwatchlist.setWatchlistName(watchlistName);
                            getUserwatchlist.setWatchtype(watchtype);
                            getUserwatchlist.setDefaultFlag(defaults);

                            List<SymbolList> symbolList = new ArrayList<>();
                            for (int k = 0; k < symbolLisst.length(); k++) {
                                JSONObject jsonObject3 = symbolLisst.getJSONObject(k);
                                SymbolList symbolList1 = new SymbolList();
                                symbolList1.setLot(jsonObject3.getString("lot"));
                                symbolList1.setExchange(jsonObject3.getString("exchange"));
                                symbolList1.setP_change(jsonObject3.getString("p_change"));
                                symbolList1.setInstrumentName(jsonObject3.getString("instrumentName"));
                                symbolList1.setScriptName(jsonObject3.getString("ScriptName"));
                                symbolList1.setToken(jsonObject3.getString("token"));
                                symbolList1.setMultiplier(jsonObject3.getString("multiplier"));
                                symbolList1.setChange(jsonObject3.getString("change"));
                                symbolList1.setAssetType(jsonObject3.getString("assetType"));
                                symbolList1.setClose(jsonObject3.getString("close"));
                                symbolList1.setTradeSymbol(jsonObject3.getString("tradeSymbol"));
                                symbolList1.setTickSize(jsonObject3.getString("tickSize"));
                                symbolList1.setDescription(jsonObject3.getString("description"));
                                symbolList1.setLtp(jsonObject3.getString("ltp"));
                                symbolList1.setOptionType(jsonObject3.getString("optionType"));
                                symbolList1.setStrickPrice(jsonObject3.getString("strickPrice"));
                                symbolList1.setExpiryDate(jsonObject3.getString("expiryDate"));
                                symbolList1.setSeqNo(jsonObject3.getString("seqNo"));

                                symbolList.add(symbolList1);

                            }
                            getUserwatchlist.setSymbolList(symbolList);

                            getUserwatchlists.add(getUserwatchlist);

                        }


                        getWatchListResponse.setGetUserwatchlist(getUserwatchlists);
                        getWatchListResponse.setErrorCode(String.valueOf(error));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    isReponseReceived = true;

                    groupNameList.clear();
                    groupTypeList.clear();

                    List<SymbolList> symbolLists = null;

                    if (getWatchListResponse.getGetUserwatchlist() != null) {
                        for (int i = 0; i < getWatchListResponse.getGetUserwatchlist().size(); i++) {

                            groupNameList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                            groupTypeList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());

                            if (getWatchListResponse.getGetUserwatchlist().get(i).getDefaultFlag().equalsIgnoreCase("true")) {
                                symbolLists = getWatchListResponse.getGetUserwatchlist().get(i).getSymbolList();
                                hashMapWatchListData.put(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName(), symbolLists);
                                AccountDetails.setWatchlistGroup(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                                grpPos = i;
                                defaultGroupName = getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName();
                                btn_group.setText(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                                AccountDetails.setLastSelectedGroup(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());

                            } else {
                                //Default group false , at least group will be added in hashmap list.
                                // IN CAse of empty symbolList , it will add group with empty list.
                                List<SymbolList> symbolList = getWatchListResponse.getGetUserwatchlist().get(i).getSymbolList();

                                if (symbolList != null) {
                                    hashMapWatchListData.put(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName(),
                                            symbolList);
                                }

                            }

                        }

                        groupsAdapter.notifyDataSetChanged();
                        commonAdapter.clear();
                        if (groupTypeList.size() > 0) {
                            groupSpinner.setSelection(grpPos);
                        }

                        if (getWatchListResponse.getErrorCode() != null && getWatchListResponse.getErrorCode().equalsIgnoreCase("3")) {
                            errorMsgLayout.setVisibility(View.VISIBLE);
                            errorTextView.setText("No data available");
                            btn_group.setText("Create Watchlist");
                            errorTextView.setVisibility(View.VISIBLE);
                        } else {
                            if (symbolLists != null) {
                                populateGroup(symbolLists);
                            }
                        }
                    }


                }

            }
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
        view2.setText("Symbol");
        view1.setOnClickListener(WatchListFragment.this);
        view2.setOnClickListener(WatchListFragment.this);


        builder.setView(layout);
        levelDialog = builder.create();
        if (!levelDialog.isShowing()) {
            levelDialog.show();
        }

    }

    private void populateSymbols(List<SymbolList> symbolLists) {
        watchlistModelsArr.clear();
        commonAdapter.clear();
        int symIndex = 0;

//        if(getWatchListResponse != null) {
//            for (int i = 0; i < getWatchListResponse.getGetUserwatchlist().size(); i++) {
//                if(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName().equalsIgnoreCase(AccountDetails.getWatchlistGrp(getMainActivity()))) {
//                    groupSpinner.setSelection(groupNameList.indexOf(AccountDetails.getWatchlistGrp(getMainActivity())));
//                    symIndex = i;
//                    break;
//                }
//            }
        isReponseReceived = true;
        grpExchangeList.clear();
        grpTradeSymbols.clear();
        grpTokenSymbols.clear();
        grpAssetTypeSymbols.clear();


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
            String exchange = item.getExchange();
            String tradeSymbol = item.getTradeSymbol();
            String token = item.getToken();
            String seqNo = item.getSeqNo();

            String assetType = item.getAssetType();
            grpExchangeList.add(exchange);
            grpTradeSymbols.add(tradeSymbol);
            grpTokenSymbols.add(token);

            grpAssetTypeSymbols.add(assetType);

            String optType = item.getOptionType();
            if (!optType.isEmpty()) {
                if (optType.equalsIgnoreCase("Call")) {
                    optType = "CE";
                } else if (optType.equalsIgnoreCase("Put")) {
                    optType = "PE";
                }
            }
            String strikePrice = item.getStrickPrice();
            if (!strikePrice.isEmpty()) {
                if (strikePrice.equalsIgnoreCase("0.00")) {
                    strikePrice = "";
                } else {
                    strikePrice = " " + strikePrice;
                }
            }

            if (AccountDetails.getShowDescription()) {

                if (exchange.equalsIgnoreCase("nse")) {
                    hm.put(GreekConstants.SYMBOL, item.getScriptName() + "-" + "N" + " - " + item.getInstrumentName());

                } else if (exchange.equalsIgnoreCase("bse")) {
                    hm.put(GreekConstants.SYMBOL, item.getScriptName() + "-" + "B" + " - " + item.getInstrumentName());

                } else if (exchange.equalsIgnoreCase("mcx")) {
                    if (item.getOptionType().equalsIgnoreCase("XX")) {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + "-" + "M" + "-" + item.getInstrumentName());
                    } else {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + item.getStrickPrice() + item.getOptionType() + "-" + "M" + "-" + item.getInstrumentName());
                    }
                } else if (exchange.equalsIgnoreCase("ncdex")) {
                    hm.put(GreekConstants.SYMBOL, item.getScriptName() + "-" + "NDX" + " - " + item.getInstrumentName());
                }
            } else {
                if (exchange.equalsIgnoreCase("nse")) {
                    hm.put(GreekConstants.SYMBOL, item.getDescription() + "-" + "N" + " - " + item.getInstrumentName());
                } else if (exchange.equalsIgnoreCase("bse")) {
                    hm.put(GreekConstants.SYMBOL, item.getDescription() + "-" + "B" + " - " + item.getInstrumentName());

                } else if (exchange.equalsIgnoreCase("mcx")) {
                    if (item.getOptionType().equalsIgnoreCase("XX")) {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + "-" + "M" + "-" + item.getInstrumentName());
                    } else {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + item.getStrickPrice() + item.getOptionType() + "-" + "M" + "-" + item.getInstrumentName());
                    }


                } else if (exchange.equalsIgnoreCase("ncdex")) {
                    hm.put(GreekConstants.SYMBOL, item.getDescription() + "-" + "NDX" + " - " + item.getInstrumentName());
                }
            }

            hm.put("OriginalSymbol", tradeSymbol);
            if (item.getAssetType().equalsIgnoreCase("currency")) {
                hm.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.4f", Double.parseDouble(item.getLtp())));
                hm.put(GreekConstants.CHG_RS, String.format("%.4f", Double.parseDouble(item.getChange())));
            } else {
                hm.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.2f", Double.parseDouble(item.getLtp())));
                hm.put(GreekConstants.CHG_RS, String.format("%.2f", Double.parseDouble(item.getChange())));
            }

            hm.put("NewChange", "green");

            hm.put(GreekConstants.CHG_PERCENTILE, String.format("%.2f", Double.parseDouble(item.getP_change())));
            hm.put(GreekConstants.VOLUME, StringStuff.commaDecorator(item.getVolume()));
            hm.put(GreekConstants.PREVIOUS_OPEN, item.getOpen());
            hm.put(GreekConstants.PREVIOUS_CLOSE, item.getClose());
            hm.put(GreekConstants.HIGH_52WEEK, item.getYHigh());
            hm.put(GreekConstants.LOW_52WEEK, item.getYLow());
            hm.put(GreekConstants.OPEN_INTEREST, item.getOI());

            //Not Needed for layout
            hm.put(GreekConstants.EXCHANGE, exchange);
            hm.put(GreekConstants.OPEN_INTEREST_PERCENTILE, "0.00");
            hm.put(GreekConstants.SCRIPTNAME, item.getScriptName());
            hm.put(GreekConstants.DESCRIPTION, item.getDescription());
            hm.put(GreekConstants.TOKEN, item.getToken());

            if (item.getSeqNo().equalsIgnoreCase("undefined") || item.getSeqNo().equalsIgnoreCase("") || item.getSeqNo().length() <= 0) {
                hm.put(GreekConstants.SEQNO, String.valueOf(i + 1));

            } else {
                hm.put(GreekConstants.SEQNO, item.getSeqNo());
            }

            i++;
            hm.put(GreekConstants.ASSET_TYPE, item.getAssetType());
            hm.put(GreekConstants.UNIQUEID, item.getUniqueID());
            hm.put(GreekConstants.LOT, item.getLot());
            hm.put(GreekConstants.TICKSIZE, item.getTickSize());
            hm.put(GreekConstants.MULTIPLIER, item.getMultiplier());
            hm.put(GreekConstants.EXPIRYDATE, item.getExpiryDate());
            hm.put(GreekConstants.STRICKPRICE, item.getStrickPrice());
            hm.put(GreekConstants.INSTRUMENTNAME, item.getInstrumentName());
            hm.put(GreekConstants.OPTIONTYPE, item.getOptionType());

            String currentSel = types.get(assetTypeSpinner.getSelectedItemPosition());
            if (currentSel.equalsIgnoreCase("All")) {

                commonAdapter.addHash(hm);
//                    commonAdapter.addHash(hm,Integer.parseInt(item.getSeqNo()));
                CommonRowData commonRow = new CommonRowData();

                if (exchange.toLowerCase().contains("nse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "N");
                } else if (exchange.toLowerCase().contains("bse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "B");
                } else if (exchange.toLowerCase().contains("mcx")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "M");
                } else if (exchange.toLowerCase().contains("ncdex")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "NDX");
                }

                if (item.getAssetType().equalsIgnoreCase("currency")) {
                    commonRow.setHead2(String.format("%.4f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.4f", Double.parseDouble(item.getChange())));
                } else {
                    commonRow.setHead2(String.format("%.2f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.2f", Double.parseDouble(item.getChange())));
                }

                commonRow.setHead4(item.getP_change());
                commonRow.setHead5(item.getClose());
                commonRow.setHead6(item.getToken());
                commonRow.setSubHead1("green");
                commonAdapter.add(commonRow);
            } else if (item.getAssetType().equalsIgnoreCase(currentSel)) {
                commonAdapter.addHash(hm);
                CommonRowData commonRow = new CommonRowData();

                if (exchange.toLowerCase().contains("nse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "N");
                } else if (exchange.toLowerCase().contains("bse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "B");
                } else if (exchange.toLowerCase().contains("mcx")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "M");
                } else if (exchange.toLowerCase().contains("ncdex")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "NDX");
                }

                if (item.getAssetType().equalsIgnoreCase("currency")) {
                    commonRow.setHead2(String.format("%.4f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.4f", Double.parseDouble(item.getChange())));
                } else {
                    commonRow.setHead2(String.format("%.2f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.2f", Double.parseDouble(item.getChange())));
                }
                commonRow.setHead4(item.getP_change());
                commonRow.setHead5(item.getClose());
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
                getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                sendStreamingRequest();
            }
        }, 1000);

        if (isWaitingForResponseOnDelete) {
            isWaitingForResponseOnDelete = false;
            sendSequenceSaveRequest();
        }
        //}
    }

    private void getVisibleSymbolTokens(int firstVisibleItem, int totalVisibleCount) {
        visibleSymbolTable.clear();
        if (commonAdapter.getSymbolTable().size() > 0) {
            for (int i = firstVisibleItem; i <= totalVisibleCount; i++) {
                if (commonAdapter.getSymbolTable().size() > totalVisibleCount) {
                    if (i != -1) {
                        visibleSymbolTable.add(commonAdapter.getSymbolTable().get(i));
                    }
                }
            }
        }
    }

    private void getVisibleScrollSymbolTokens(int firstVisibleItem, int totalVisibleCount) {
        try {

            // It will prepare list of only visible script in watchlist screen.
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItem = firstVisibleItemPosition + visibleItemCount;


            visibleSymbolTable.clear();
            if (commonAdapter.getSymbolTable().size() > 0) {
                int totalCount = totalVisibleCount - firstVisibleItem;
                for (int i = firstVisibleItem; i < lastVisibleItem; i++) {
                    if (i != -1) {

                        if (commonAdapter.getSymbolTable().size() > i) {
                            visibleSymbolTable.add(commonAdapter.getSymbolTable().get(i));

                        }
                    }
                }
            }

        } catch (Exception e) {

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


    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                if (getWatchListResponse != null && getWatchListResponse.getErrorCode() != null && getWatchListResponse.getErrorCode().equals("3")) {

                    errorMsgLayout.setVisibility(View.VISIBLE);
                    errorTextView.setText("No data available");
                    errorTextView.setVisibility(View.VISIBLE);
                    populateSymbols(getWatchListResponse.getGetUserwatchlist().get(0).getSymbolList());
                    hideProgress();
                } else {
                    hideProgress();
                    sendHighConvictionIdeasRequest();

                }

                populateSymbols(getWatchListResponse.getGetUserwatchlist().get(0).getSymbolList());
                isReponseReceived = true;
            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && DELETE_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
                RemoveSymbolFromGroupResponse removeSymbolFromGroupResponse = (RemoveSymbolFromGroupResponse) jsonResponse.getResponse();

                if (removeSymbolFromGroupResponse.getStatus().equalsIgnoreCase("0")) {


                    List<SymbolList> symbolLists = hashMapWatchListData.get(btn_group.getText().toString());
                    for (int i = 0; i < symbolLists.size(); i++) {
                        if (symbolLists.get(i).getToken().equalsIgnoreCase(symbolListG.get(0).getToken())) {
                            symbolLists.remove(i);
                        }
                    }

                    hashMapWatchListData.put(btn_group.getText().toString(), symbolLists);

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_DELETE_SUCCESS_MSG), "OK", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            //refresh();
                            sendSequenceSaveRequest();

                        }
                    });
                    isWaitingForResponseOnDelete = true;
//                    getDetailOfGroup(AccountDetails.getUsername(getMainActivity()));
                    getDetailOfGroup(btn_group.getText().toString());
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GR_DELETE_FAILURE_MSG), "OK", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            //refresh();
                            //sendSequenceSaveRequest();

                        }
                    });
                }


            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && ADD_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
                AddSymbolToGroupResponse addsymboltogroupresponse = (AddSymbolToGroupResponse) jsonResponse.getResponse();
                addSymbolToGroupRefresh();


            } else if (REORDER_SAVE_PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && ADD_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
                // AddSymbolToGroupResponse addsymboltogroupresponse = (AddSymbolToGroupResponse) jsonResponse.getResponse();
                showProgress();
                hideProgress();

            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_GROUP_NAME_SVC_NAME.equals(jsonResponse.getServiceName())) {
                hideProgress();
                /*                isReponseReceived = true;
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                groupNameList.clear();
                groupTypeList.clear();

                List<SymbolList> symbolLists = null;

                if(getWatchListResponse.getGetUserwatchlist().size() <= 0 || getWatchListResponse.getErrorCode() != null && getWatchListResponse.getErrorCode().equalsIgnoreCase("3")) {

                }

                for (int i = 0; i < getWatchListResponse.getGetUserwatchlist().size(); i++) {

                    groupNameList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                    groupTypeList.add(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());

                    if(!AccountDetails.getLastSelectedGroup().isEmpty() && groupNameList.contains(AccountDetails.getLastSelectedGroup())) {

                        getDetailOfGroup(AccountDetails.getLastSelectedGroup());

                    } else {

                        if(getWatchListResponse.getGetUserwatchlist().get(i).getDefaultFlag().equalsIgnoreCase("true")) {
                            symbolLists = getWatchListResponse.getGetUserwatchlist().get(i).getSymbolList();
                            WatchListData.put(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName(),
                                    getWatchListResponse);
                            AccountDetails.setWatchlistGroup(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                            grpPos = i;
                            btn_group.setText(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());
                            AccountDetails.setLastSelectedGroup(getWatchListResponse.getGetUserwatchlist().get(i).getWatchlistName());

                        }
                    }

                }

                groupsAdapter.notifyDataSetChanged();

                commonAdapter.clear();
                if(groupTypeList.size() > 0) {
                    groupSpinner.setSelection(grpPos);

                }

                if(getWatchListResponse.getErrorCode() != null && getWatchListResponse.getErrorCode().equalsIgnoreCase("3")) {
                    errorMsgLayout.setVisibility(View.VISIBLE);
                    errorTextView.setText("No data available");
                    btn_group.setText("Create Watchlist");
                    errorTextView.setVisibility(View.VISIBLE);
                } else {
                    if(symbolLists != null) {
                        populateGroup(symbolLists);
                    }
                }
                hideProgress();*/
            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_DATA_SVC_NAME.equals(jsonResponse.getServiceName())) {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                if (getWatchListResponse.getGetUserwatchlist().size() > 0) {
                    hashMapWatchListData.put(getWatchListResponse.getGetUserwatchlist().get(0).getWatchlistName(),
                            getWatchListResponse.getGetUserwatchlist().get(0).getSymbolList());
                    populateSymbols(getWatchListResponse.getGetUserwatchlist().get(0).getSymbolList());
                } else {
                    watchlistModelsArr.clear();
                    grpExchangeList.clear();
                    grpTradeSymbols.clear();
                    grpTokenSymbols.clear();
                    grpAssetTypeSymbols.clear();
                    commonAdapter.clear();
                    commonAdapter.notifyDataSetChanged();
                    errorMsgLayout.setVisibility(View.VISIBLE);
                    errorTextView.setText("No Symbol available");
                    errorTextView.setVisibility(View.VISIBLE);
                }

//                sendSequenceSaveRequest();

                hideProgress();
            }
            hideAppTitle();
        } catch (Exception e) {
            e.printStackTrace();
            errorMsgLayout.setVisibility(View.VISIBLE);
            errorTextView.setText("No data available");
            errorTextView.setVisibility(View.VISIBLE);
        }
        isWaitingForResponseOnPTR = false;

//        refreshComplete();
    }


    public void setGroupNames() {

        for (int position = 0; position < groupNameList.size(); position++) {

            if (position == 0) {
                line1.setVisibility(View.VISIBLE);
                txt_grptitle1.setText(groupNameList.get(position));


            } else if (position == 1) {
                line2.setVisibility(View.VISIBLE);
                txt_grptitle2.setText(groupNameList.get(position));

            } else if (position == 2) {

                line3.setVisibility(View.VISIBLE);
                txt_grptitle3.setText(groupNameList.get(position));
            } else if (position == 3) {
                line4.setVisibility(View.VISIBLE);
                txt_grptitle4.setText(groupNameList.get(position));
            }
        }


    }

    private void populateGroup(List<SymbolList> symbolLists) {

        commonAdapter.notifyDataSetChanged();
        grpExchangeList.clear();
        grpTradeSymbols.clear();
        grpTokenSymbols.clear();
        grpAssetTypeSymbols.clear();

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
            String exchange = item.getExchange();
            String tradeSymbol = item.getTradeSymbol();
            String token = item.getToken();
            String seqNo = item.getSeqNo();

            String assetType = item.getAssetType();
            grpExchangeList.add(exchange);
            grpTradeSymbols.add(tradeSymbol);
            grpTokenSymbols.add(token);

            grpAssetTypeSymbols.add(assetType);

            String optType = item.getOptionType();
            if (!optType.isEmpty()) {
                if (optType.equalsIgnoreCase("Call")) {
                    optType = "CE";
                } else if (optType.equalsIgnoreCase("Put")) {
                    optType = "PE";
                }
            }
            String strikePrice = item.getStrickPrice();
            if (!strikePrice.isEmpty()) {
                if (strikePrice.equalsIgnoreCase("0.00")) {
                    strikePrice = "";
                } else {
                    strikePrice = " " + strikePrice;
                }
            }
            if (AccountDetails.getShowDescription()) {

                if (exchange.equalsIgnoreCase("nse")) {

                    hm.put(GreekConstants.SYMBOL, item.getScriptName() + "-" + "N" + " - " + item.getInstrumentName());


                } else if (exchange.equalsIgnoreCase("bse")) {

                    hm.put(GreekConstants.SYMBOL, item.getScriptName() + "-" + "B" + " - " + item.getInstrumentName());

                } else if (exchange.equalsIgnoreCase("mcx")) {
                    if (item.getOptionType().equalsIgnoreCase("XX")) {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + "-" + "M" + "-" + item.getInstrumentName());
                    } else {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + item.getStrickPrice() + item.getOptionType() + "-" + "M" + "-" + item.getInstrumentName());
                    }


                } else if (exchange.equalsIgnoreCase("ncdex")) {
                    hm.put(GreekConstants.SYMBOL, item.getScriptName() + "-" + "NDX" + " - " + item.getInstrumentName());
                }
            } else {
                if (exchange.equalsIgnoreCase("nse")) {
                    hm.put(GreekConstants.SYMBOL, item.getDescription() + "-" + "N" + " - " + item.getInstrumentName());
                } else if (exchange.equalsIgnoreCase("bse")) {
                    hm.put(GreekConstants.SYMBOL, item.getDescription() + "-" + "B" + " - " + item.getInstrumentName());

                } else if (exchange.equalsIgnoreCase("mcx")) {
                    if (item.getOptionType().equalsIgnoreCase("XX")) {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + "-" + "M" + "-" + item.getInstrumentName());
                    } else {
                        hm.put(GreekConstants.SYMBOL, item.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "" + item.getStrickPrice() + item.getOptionType() + "-" + "M" + "-" + item.getInstrumentName());
                    }


                } else if (exchange.equalsIgnoreCase("ncdex")) {
                    hm.put(GreekConstants.SYMBOL, item.getDescription() + "-" + "NDX" + " - " + item.getInstrumentName());
                }
            }


            //}
            hm.put("OriginalSymbol", tradeSymbol);
            if (item.getAssetType().equalsIgnoreCase("currency")) {
                hm.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.4f", Double.parseDouble(item.getLtp())));
                hm.put(GreekConstants.CHG_RS, String.format("%.4f", Double.parseDouble(item.getChange())));
            } else {
                hm.put(GreekConstants.LAST_TRADED_PRICE, String.format("%.2f", Double.parseDouble(item.getLtp())));
                hm.put(GreekConstants.CHG_RS, String.format("%.2f", Double.parseDouble(item.getChange())));
            }

            hm.put("NewChange", "green");

            hm.put(GreekConstants.CHG_PERCENTILE, String.format("%.2f", Double.parseDouble(item.getP_change())));
            hm.put(GreekConstants.VOLUME, StringStuff.commaDecorator(item.getVolume()));
            hm.put(GreekConstants.PREVIOUS_OPEN, item.getOpen());
            hm.put(GreekConstants.PREVIOUS_CLOSE, item.getClose());
            hm.put(GreekConstants.HIGH_52WEEK, item.getYHigh());
            hm.put(GreekConstants.LOW_52WEEK, item.getYLow());
            hm.put(GreekConstants.OPEN_INTEREST, item.getOI());

            //Not Needed for layout
            hm.put(GreekConstants.EXCHANGE, exchange);
            hm.put(GreekConstants.OPEN_INTEREST_PERCENTILE, "0.00");
            hm.put(GreekConstants.SCRIPTNAME, item.getScriptName());
            hm.put(GreekConstants.DESCRIPTION, item.getDescription());
            hm.put(GreekConstants.TOKEN, item.getToken());

            if (item.getSeqNo().equalsIgnoreCase("undefined") || item.getSeqNo().equalsIgnoreCase("") || item.getSeqNo().length() <= 0) {
                hm.put(GreekConstants.SEQNO, String.valueOf(i + 1));

            } else {
                hm.put(GreekConstants.SEQNO, item.getSeqNo());
            }

            i++;
            hm.put(GreekConstants.ASSET_TYPE, item.getAssetType());
            hm.put(GreekConstants.UNIQUEID, item.getUniqueID());
            hm.put(GreekConstants.LOT, item.getLot());
            hm.put(GreekConstants.TICKSIZE, item.getTickSize());
            hm.put(GreekConstants.MULTIPLIER, item.getMultiplier());
            hm.put(GreekConstants.EXPIRYDATE, item.getExpiryDate());
            hm.put(GreekConstants.STRICKPRICE, item.getStrickPrice());
            hm.put(GreekConstants.INSTRUMENTNAME, item.getInstrumentName());
            hm.put(GreekConstants.OPTIONTYPE, item.getOptionType());

            //String currentSel = types.get(assetTypeSpinner.getSelectedItemPosition());
            String currentSel = "All";
            if (currentSel.equalsIgnoreCase("All")) {

                commonAdapter.addHash(hm);
                CommonRowData commonRow = new CommonRowData();

                if (exchange.toLowerCase().contains("nse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "N");
                } else if (exchange.toLowerCase().contains("bse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "B");
                } else if (exchange.toLowerCase().contains("mcx")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "M");
                } else if (exchange.toLowerCase().contains("ncdex")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "NDX");
                }

                if (item.getAssetType().equalsIgnoreCase("currency")) {
                    commonRow.setHead2(String.format("%.4f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.4f", Double.parseDouble(item.getChange())));
                } else {
                    commonRow.setHead2(String.format("%.2f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.2f", Double.parseDouble(item.getChange())));
                }


                commonRow.setHead4(item.getP_change());
                commonRow.setHead5(item.getClose());
                commonRow.setHead6(item.getToken());
                commonRow.setSubHead1("green");
                commonAdapter.add(commonRow);
            } else if (item.getAssetType().equalsIgnoreCase(currentSel)) {
                commonAdapter.addHash(hm);
                CommonRowData commonRow = new CommonRowData();

                if (exchange.toLowerCase().contains("nse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "N");
                } else if (exchange.toLowerCase().contains("bse")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "B");
                } else if (exchange.toLowerCase().contains("mcx")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "M");
                } else if (exchange.toLowerCase().contains("ncdex")) {
                    commonRow.setHead1(item.getScriptName() + "-" + "NDX");
                }

                if (item.getAssetType().equalsIgnoreCase("currency")) {
                    commonRow.setHead2(String.format("%.4f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.4f", Double.parseDouble(item.getChange())));
                } else {
                    commonRow.setHead2(String.format("%.2f", Double.parseDouble(item.getLtp())));
                    commonRow.setHead3(String.format("%.2f", Double.parseDouble(item.getChange())));
                }
                commonRow.setHead4(item.getP_change());
                commonRow.setHead5(item.getClose());
                commonRow.setHead6(item.getToken());
                commonRow.setSubHead1("green");
                commonAdapter.add(commonRow);
            }
        }

        commonAdapter.notifyDataSetChanged();

        hideProgress();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                sendStreamingRequest();
            }
        }, 1000);
    }

    private void getDetailOfGroup(String grpname) {


        AccountDetails.setLastSelectedGroup(grpname);
        btn_group.setText(grpname);

        if (hashMapWatchListData.size() > 0) {
            List<SymbolList> watchList = hashMapWatchListData.get(grpname);
            if (watchList != null && watchList.size() != 0) {
                populateSymbols(watchList);
            } else {
                showProgress();
                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                    WatchlistDataByGroupNameRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
                } else {
                    WatchlistDataByGroupNameRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
                }
            }
        } else {
            showProgress();
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                WatchlistDataByGroupNameRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
            } else {
                WatchlistDataByGroupNameRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
            }
        }
    }


    private void addSymbolToGroupRefresh() {
        List<SymbolList> symbolList;
        String serviceURL = "";
        if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
            serviceURL = "getTokenDetails_Mobile_Redis?token=" + addToken;
        } else {
            serviceURL = "getTokenDetails_Mobile?token=" + addToken;
        }
        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    hideProgress();
                    SymbolListModel symbolListModel = new SymbolListModel();
                    List<SymbolList> symbolList = symbolListModel.fromJSON(response);

                    sendSequenceSaveRequest();
                    List<SymbolList> symbolModelList = new ArrayList<>();
                    symbolModelList.add(symbolList.get(0));

                    int alreadyCount = hashMapWatchListData.get(btn_group.getText().toString()).size();

                    if (alreadyCount > 0) {
                        for (int i = 0; i < alreadyCount; i++) {
                            symbolModelList.add(hashMapWatchListData.get(btn_group.getText().toString()).get(i));
                        }

//                        GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
//                        getUserwatchlist.setSymbolList(symbolModelList);
//                        getUserwatchlist.setDefaultFlag(getWatchListResponse.getGetUserwatchlist().get(0).getDefaultFlag());
//                        getUserwatchlist.setWatchlistName(getWatchListResponse.getGetUserwatchlist().get(0).getWatchlistName());
//                        getUserwatchlist.setWatchtype(getWatchListResponse.getGetUserwatchlist().get(0).getWatchtype());
//                        List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();
//                        getUserwatchlists.add(getUserwatchlist);
//                        PortfolioGetUserWatchListResponse portfolioGetUserWatchListResponse = new PortfolioGetUserWatchListResponse();
//                        portfolioGetUserWatchListResponse.setGetUserwatchlist(getUserwatchlists);


                    }
                    hashMapWatchListData.put(btn_group.getText().toString(), symbolModelList);


                    GreekDialog.alertDialog(getMainActivity(), 0, "", "Added " + symbolList.get(0).getDescription() + " to Watchlist Successfully", "OK", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            // Now get Group details from hashMap storage object  By Pritesh
                            List<SymbolList> watchList = hashMapWatchListData.get(btn_group.getText().toString());
                            if (watchList != null) {
                                populateSymbols(watchList);
                            }
                            // getDetailOfGroup(btn_group.getText().toString()+"Added"); for now no need.


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
        if (hashMapWatchListData != null && hashMapWatchListData.size() > 0) {
            PortfolioGetUserWatchListResponse portfolioGetUserWatchListResponse = new PortfolioGetUserWatchListResponse();
            portfolioGetUserWatchListResponse.setErrorCode("0");
            List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();
            for (int i = 0; i < hashMapWatchListData.size(); i++) {
                GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
                getUserwatchlist.setWatchlistName(hashMapWatchListData.keySet().toArray()[i].toString());
                getUserwatchlist.setWatchtype("User");
                if (defaultGroupName.equalsIgnoreCase(hashMapWatchListData.keySet().toArray()[i].toString())) {
                    getUserwatchlist.setDefaultFlag("true");
                } else {
                    getUserwatchlist.setDefaultFlag("false");
                }
                List<SymbolList> symbolList = new ArrayList<>();
                symbolList = hashMapWatchListData.get(hashMapWatchListData.keySet().toArray()[i].toString());

                getUserwatchlist.setSymbolList(symbolList);

                getUserwatchlists.add(getUserwatchlist);
            }


            portfolioGetUserWatchListResponse.setGetUserwatchlist(getUserwatchlists);

            SharedPreferences.Editor editor1 = Util.getPrefs(getActivity()).edit();
            Gson gson = new Gson();
            String data = gson.toJson(portfolioGetUserWatchListResponse);
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getActivity()), data);
            editor1.apply();
            editor1.commit();
        } else {
            PortfolioGetUserWatchListResponse portfolioGetUserWatchListResponse1 = new PortfolioGetUserWatchListResponse();
            portfolioGetUserWatchListResponse1.setErrorCode("0");
            List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();
/*            for (int i = 0; i < hashMapWatchListData.size(); i++) {
                GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
                getUserwatchlist.setWatchlistName(hashMapWatchListData.keySet().toArray()[i].toString());
                getUserwatchlist.setWatchtype("User");
                if(defaultGroupName.equalsIgnoreCase(hashMapWatchListData.keySet().toArray()[i].toString())){
                    getUserwatchlist.setDefaultFlag("true");
                }else{
                    getUserwatchlist.setDefaultFlag("false");
                }
                List<SymbolList> symbolList = new ArrayList<>();
                symbolList =hashMapWatchListData.get(hashMapWatchListData.keySet().toArray()[i].toString());

                getUserwatchlist.setSymbolList(symbolList);

                getUserwatchlists.add(getUserwatchlist);
            }*/


            portfolioGetUserWatchListResponse1.setGetUserwatchlist(getUserwatchlists);

            SharedPreferences.Editor editor1 = Util.getPrefs(getActivity()).edit();
            Gson gson = new Gson();
            String data = gson.toJson(portfolioGetUserWatchListResponse1);
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getActivity()), data);
            editor1.apply();
            editor1.commit();
        }
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
        AccountDetails.setIsMainActivity(false);
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
                if (grpTokenSymbols.contains(args.getString(TOKEN))) {
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, getString(R.string.GR_SYMBOL_EXIST_MSG), "Ok", false, null);
                } else {
                    SymbolDetail detail = new SymbolDetail();
                    detail.setExchange(args.getString(EXCHANGE_NAME));
                    detail.setTradeSymbol(args.getString(TRADE_SYMBOL));
                    detail.setToken(args.getString(TOKEN));
                    detail.setAssetType(args.getString(ASSET_TYPE));
                    symbolListG.add(detail);
                    addToken = args.getString(TOKEN);
                    addedAssetType = args.getString(ASSET_TYPE);

                    selectedGrp = btn_group.getText().toString();


                    sendSaveSymbolRequest(selectedGrp, selectedGrpType, symbolListG);
                }
            } else if (args.getBoolean("toBeRefreshed")) {

                if (args.getString(ADDED_SYMBOL) != null) addedGroup = args.getString(ADDED_SYMBOL);
                else addedGroup = "";
                //sendGroupNameRequest();
                if (args.getSerializable("lastResponse") != null) {
                    getWatchListResponse = (PortfolioGetUserWatchListResponse) args.getSerializable("lastResponse");
//                    sendGroupNameRequest();
//                    getDetailOfGroup(AccountDetails.getUsername(getMainActivity()));
                    getDetailOfGroup(btn_group.getText().toString());
                } else {
//                    sendGroupNameRequest();
//                    getDetailOfGroup(AccountDetails.getUsername(getMainActivity()));
                    getDetailOfGroup(btn_group.getText().toString());
                }

            }

//            getDetailOfGroup(AccountDetails.getUsername(getMainActivity()));
        }

    }

    private void sendRemoveSymbolRequest(String selectedGrp, String selectedGrpType, List<SymbolDetail> symbolListG) {
        Context con = getMainActivity();
        selectedGrpType = "user";
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
//            selectedGrp = "guest";
            RemoveSymbolFromGroupRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()), AccountDetails.getDeviceID(getMainActivity()), selectedGrp, AccountDetails.getSessionId(con), symbolListG, selectedGrpType, con, serviceResponseHandler);
        } else {
            RemoveSymbolFromGroupRequest.sendRequest(AccountDetails.getUsername(con), AccountDetails.getClientCode(con), selectedGrp, AccountDetails.getSessionId(con), symbolListG, selectedGrpType, con, serviceResponseHandler);
        }
    }

    private void sendSaveSymbolRequest(String selectedGrp, String selectedGrpType, List<SymbolDetail> symbolListG) {
        Context con = getMainActivity();
        selectedGrpType = "user";
        showProgress();
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
//            selectedGrp = "guest";
            AddSymbolToGroupRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()),
                    AccountDetails.getDeviceID(getMainActivity()), btn_group.getText().toString(),
                    AccountDetails.getSessionId(con), symbolListG, symbolListG.size() + "",
                    selectedGrpType, con, serviceResponseHandler);
        } else {
            AddSymbolToGroupRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getUsername(getMainActivity()), btn_group.getText().toString(), AccountDetails.getSessionId(con), symbolListG, symbolListG.size() + "", selectedGrpType, con, serviceResponseHandler);
        }
    }

    private void sendStreamingRequest() {

        symbolsToUnsubscribe.clear();
        for (int i = 0; i < visibleSymbolTable.size(); i++) {
            symbolsToUnsubscribe.add(visibleSymbolTable.get(i));
        }

        if (AccountDetails.isIsApolloConnected()) {
            streamController.sendStreamingRequest(getMainActivity(), visibleSymbolTable, "ltpinfo", null, null, false);
        } else {
            EventBus.getDefault().post("Socket Apollo Reconnect Attempts exceeds");
        }
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
                            commonRow.setHead2(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        } else {
                            commonRow.setHead2(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        }


                        commonRow.setHead4(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (%)")))));
                        commonRow.setHead5(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Close")))));
                        commonRow.setHead6(String.valueOf(sortLinkedMap.get("Token")));
                        commonRow.setSubHead1("green");
                        commonAdapter.add(commonRow);
                        //hashArray.add(sortLinkedMap);
                        tempHashArray.remove(sortLinkedMap);
                        //commonAdapter.notifyDataSetChanged();
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
                            commonRow.setHead2(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.4f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        } else {
                            commonRow.setHead2(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("LTP")))));
                            commonRow.setHead3(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (Rs)")))));
                        }


                        commonRow.setHead4(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Chg (%)")))));
                        commonRow.setHead5(String.format("%.2f", Double.parseDouble(String.valueOf(sortLinkedMap.get("Close")))));
                        commonRow.setHead6(String.valueOf(sortLinkedMap.get("Token")));
                        commonRow.setSubHead1("green");
                        commonAdapter.add(commonRow);
                        //hashArray.add(sortLinkedMap);
                        tempHashArray.remove(sortLinkedMap);
                        //commonAdapter.notifyDataSetChanged();
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

        Log.e("WatchList", "Broadcasting token======>>" + response.getSymbol());
        if (commonAdapter != null && commonAdapter.containsSymbol(response.getSymbol())) {
            boolean notifyNeeded = false;
            ArrayList<Integer> changedCol = new ArrayList<>();

            System.out.println("update Hash array------->>>> " + (Arrays.toString(hashArray.toArray())));

            HashMap<String, String> data = commonAdapter.getItemHash(commonAdapter.indexOf(response.getSymbol()));

            if (data.get("Token").equalsIgnoreCase(response.getSymbol())) {


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
                    Log.v("symbol======>>>>>>>", response.getSymbol());
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

                List<SymbolList> symbolLists = hashMapWatchListData.get(btn_group.getText().toString());
                SymbolList symbolList = symbolLists.get(commonAdapter.indexOf(response.getSymbol()));

                if (symbolList.getToken().equalsIgnoreCase(response.getSymbol())) {
                    symbolList.setLtp(response.getLast());
                    symbolList.setChange(response.getChange());
                    symbolList.setP_change(response.getP_change());
                    symbolLists.set(commonAdapter.indexOf(response.getSymbol()), symbolList);
                    hashMapWatchListData.put(btn_group.getText().toString(), symbolLists);

                }

                commonAdapter.setItem(commonAdapter.indexOf(response.getSymbol()), data);

            }
        }
    }


    public void sendSequenceSaveRequest() {

        ArrayList<SymbolList> symbolListNew = new ArrayList<>();
        JSONObject data = new JSONObject();
        JSONObject object;
        JSONArray jsonArray = new JSONArray();
        if (getWatchListResponse != null) {

            for (int j = 0; j < getWatchListResponse.getGetUserwatchlist().size(); j++) {
                if (getWatchListResponse.getGetUserwatchlist().get(j).getWatchlistName().equalsIgnoreCase(AccountDetails.getUsername(getMainActivity()))) {
                    GetUserwatchlist symbolList = getWatchListResponse.getGetUserwatchlist().get(j);
                    for (int i = 0; i < hashArray.size(); i++) {
                        for (int k = 0; k < symbolList.getSymbolList().size(); k++) {
                            Log.e("WatchListFragment", "hashArray===>" + hashArray.get(i).get(GreekConstants.SCRIPTNAME) + "*****" + symbolList.getSymbolList().get(k).getDescription());
                            if (hashArray.get(i).get(GreekConstants.TOKEN).equalsIgnoreCase(symbolList.getSymbolList().get(k).getToken())) {
                                symbolListNew.add(symbolList.getSymbolList().get(k));
                            }
                        }
                    }

//                  if(assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("all")) {
                    getWatchListResponse.getGetUserwatchlist().get(j).setSymbolList(symbolListNew);

//                    }
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
                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                    data.put("gscid", AccountDetails.getDeviceID(getMainActivity()));
                } else {
                    data.put("gscid", AccountDetails.getUsername(getMainActivity()));
                }
                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                    data.put("groupName", btn_group.getText().toString());
                } else {
                    data.put("groupName", btn_group.getText().toString());
                }
                //data.put("groupName", AccountDetails.getUsername(getMainActivity()));
                data.put("watchlistType", "user");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                WatchlistGroupRequest.sendRequest(data, "addNewScriptToWatchlistGroup_Redis", getMainActivity(), serviceResponseHandler);
            } else {
                WatchlistGroupRequest.sendRequest(data, "addNewScriptToWatchlistGroup", getMainActivity(), serviceResponseHandler);

            }

        }

    }

    public interface ListSortListener {
        void onSortListener();
    }


    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

        private Context mContext;
        ArrayList<watchlistModel> watchlistModelsArray = new ArrayList<>();
        private final ArrayList<String> tokenList;
        private ListSortListener sortListener;
        private final Handler handler = new Handler();
        private int j = 0;

        // RecyclerView recyclerView;
        public MyListAdapter(Context context, ArrayList<HashMap<String, String>> watchlistModels) {
            this.mContext = context;
            hashArray = new ArrayList<>();
            hashArray = watchlistModels;
            tokenList = new ArrayList<>();
            seqNo_hm = new LinkedHashMap<>();

        }

        @Override
        public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_watch_list, parent, false);
            return new MyListAdapter.ViewHolder(listItem);
        }

        @Override
        public void onBindViewHolder(MyListAdapter.ViewHolder holder, int position) {

            HashMap<String, String> data1 = hashArray.get(position);


//          if(data1.get("assetType").equalsIgnoreCase("Commodity") ||
//                    data1.get("assetType").equalsIgnoreCase("currency")) {
//                holder.tvsymbol.setText(data1.get("ScriptName"));
//
//              if(data1.get("InstrumentName") != null && data1.get("expiryDate") != null) {
//                    holder.descriptionname.setText(
//                            DateTimeFormatter.getDateFromTimeStamp(data1.get("expiryDate"), "ddMMMyy", "bse").toUpperCase()
//                                    + " " + data1.get("InstrumentName").toUpperCase());
//                }
//            } else {
//                holder.tvsymbol.setText(data1.get("description"));
//                holder.descriptionname.setText(data1.get("Exchange"));
//            }

            holder.tvsymbol.setText(data1.get("description"));
            holder.descriptionname.setText(data1.get("Exchange") + " - " + data1.get("InstrumentName"));
            holder.tvltp.setText(data1.get("LTP"));
            holder.tvchange.setText(data1.get("Chg (Rs)") + " (" + data1.get("Chg (%)") + "%)");


            int flashBluecolor, flashRedColor, textColorPositive, textColorNegative;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                flashBluecolor = R.drawable.light_green_textcolor;
                flashRedColor = R.drawable.dark_red_negative;
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    textColorPositive = R.color.whitetheambuyColor;
                }else {
                    textColorPositive = R.color.dark_green_positive;
                }
                textColorNegative = R.color.dark_red_negative;
                holder.watchlist_row.setBackgroundColor(getResources().getColor(R.color.white));
                holder.tvsymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.descriptionname.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvltp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else {
                flashBluecolor = R.drawable.light_green_textcolor;
                flashRedColor = R.drawable.dark_red_negative;
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    textColorPositive = R.color.whitetheambuyColor;
                }else {
                    textColorPositive = R.color.dark_green_positive;
                }
                textColorNegative = R.color.dark_red_negative;
            }

            if (data1.get("Chg (Rs)").startsWith("-")) {
                holder.tvchange.setTextColor(getResources().getColor(textColorNegative));

            } else {
                holder.tvchange.setTextColor(getResources().getColor(textColorPositive));
            }

            int arrow_image;

//
//            holder.tvsymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            holder.descriptionname.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            holder.tvltp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        }

        public void add(CommonRowData commonRow) {

            model = new watchlistModel(commonRow.getHead1(), commonRow.getHead2(), commonRow.getHead3(), commonRow.getHead4(), commonRow.getHead5(), commonRow.getHead6(), commonRow.getSubHead1());
            tokenList.add(commonRow.getHead6());

            watchlistModelsArr.add(model);
        }

        public void addHash(LinkedHashMap<String, String> hm) {
            hashArray.add(hm);
        }

        public void addHash(LinkedHashMap<String, String> hm, int pos) {
            hashArray.add(pos, hm);
//            hashArray.add(hm);
        }

        public void setItem(int position, HashMap<String, String> row) {
            hashArray.set(position, row);
//            commonAdapter.notifyItemChanged(position);
            notifyItemChanged(position);
        }


        @Override
        public int getItemCount() {
            return hashArray.size();
        }

        public HashMap<String, String> getItemHash(int position) {
            return hashArray.get(position);
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

        public void updateTokenList(int from, int to) {

            Collections.swap(tokenList, from, to);
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {

            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(hashArray, i, i + 1);
                    Collections.swap(tokenList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(hashArray, i, i - 1);
                    Collections.swap(tokenList, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void OnItemDismiss(int position) {

            hashArray.remove(position);
            notifyItemRemoved(position);

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvsymbol, descriptionname, tvltp, tvchange;
            LinearLayout watchlist_row;

            public ViewHolder(View itemView) {
                super(itemView);

                tvsymbol = (GreekTextView) itemView.findViewById(R.id.symbolname_text);
                descriptionname = (GreekTextView) itemView.findViewById(R.id.descriptionname_text);
                tvltp = (GreekTextView) itemView.findViewById(R.id.ltp_text);
                tvchange = (GreekTextView) itemView.findViewById(R.id.change_text);
                watchlist_row = (LinearLayout) itemView.findViewById(R.id.watchlist_row);
                //upDownArrow = (ImageView) itemView.findViewById(R.id.updownArrow);


                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = getLayoutPosition();

                        if (commonAdapter != null) {

                            lMap = (LinkedHashMap) commonAdapter.getItemHash(position);
                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Confirm Delete scrip?", "Yes", "No", true, new GreekDialog.DialogListener() {
                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (action.name().equalsIgnoreCase("ok")) {
                                        symbolListG.clear();
                                        String tradeSymbol = (String) lMap.get("OriginalSymbol");
                                        String currToken = (String) lMap.get("Token");
                                        for (int i = 0; i < grpExchangeList.size(); i++) {
                                            if (grpTokenSymbols.get(i).equals(currToken)) {
                                                SymbolDetail detail = new SymbolDetail();
                                                detail.setExchange(grpExchangeList.get(i));
                                                detail.setTradeSymbol(grpTradeSymbols.get(i));
                                                detail.setToken(grpTokenSymbols.get(i));
                                                detail.setAssetType(grpAssetTypeSymbols.get(i));
                                                symbolListG.add(detail);
                                            }
                                        }
                                        selectedGrp = btn_group.getText().toString();
                                        addedAssetType = assetTypeSpinner.getSelectedItem().toString();

                                        sendRemoveSymbolRequest(selectedGrp, "default", symbolListG);
                                    } else if (action.name().equalsIgnoreCase("cancel")) {
                                    }


                                }
                            });

                        }
                        return true;

                    }
                });
            }
        }

    }

    public void onEventMainThread(String error) {
        if (error.equalsIgnoreCase("orderpreview")) {
            Toast.makeText(getMainActivity(), "recieved", Toast.LENGTH_SHORT).show();
            //onSlideClose();
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 40;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        p.setTypeface(font);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }
}
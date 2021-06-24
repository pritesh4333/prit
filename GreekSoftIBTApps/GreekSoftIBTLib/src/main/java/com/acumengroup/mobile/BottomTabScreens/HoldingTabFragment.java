package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.DematHolding;
import com.acumengroup.greekmain.core.model.TradeDematHoldingResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.HoldingDetailResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.greekmain.core.network.HoldinginfoResponse;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class HoldingTabFragment extends GreekBaseFragment {

    private RecyclerView recyclerView;
    private HoldingTabFragment.MyListAdapter commonAdapter;
    private GreekTextView emptyView, title1, title2, title3, symbol_nametxt, pricetxt, qtytxt, valuetxt, cmptxt, mtmtxt;
    private TradeDematHoldingResponse dematHoldingResponse;
    private List<DematHolding> dematLtpDetails;
    private static final String[] optionsArray = {"BSE", "NSE"};
    private StreamingController streamController;
    private ArrayList streamingList;
    private GreekTextView currentValtxt, holdingValtxt, currentMtmtxt;
    private String tempCurrentMtmtxt;
    private double totalMtm = 0, totalcurrentHoldingValue = 0, totalholdingValue = 0, BroadcastPNL = 0;
    private SwipeRefreshLayout swipeRefresh;
    private static String token = "";
    private static String ltp;
    private String scrptName;
    private OrderStreamingController orderStreamingController;
    private double totalHoldingValue = 0.0, totalCurrentValue = 0.0, totalCurrentMTOM = 0.0;
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private final ArrayList<String> symbolsToUnsubscribe = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private View holdingParentView;
    private LinearLayout TitleLayout, ColumnLauout;

    public HoldingTabFragment() {

    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            sendDematHoldingRequest(0);
        }
    };

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dematLtpDetails = new ArrayList<>();
        if (getArguments() != null) {

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        orderStreamingController = new OrderStreamingController();
        if (isVisibleToUser) {
            sendDematHoldingRequest(0);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        holdingParentView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_holding_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_holding_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        //AccountDetails.currentFragment = NAV_TO_PORTFOLIO_HOLDING_SCREEN;


        TitleLayout = holdingParentView.findViewById(R.id.TitleLayout);
        ColumnLauout = holdingParentView.findViewById(R.id.ColumnLauout);
        recyclerView = holdingParentView.findViewById(R.id.recyclerView);
        emptyView = holdingParentView.findViewById(R.id.empty_view);
        title1 = holdingParentView.findViewById(R.id.title1);
        title2 = holdingParentView.findViewById(R.id.title2);
        title3 = holdingParentView.findViewById(R.id.title3);
        symbol_nametxt = holdingParentView.findViewById(R.id.symbol_nametxt);
        pricetxt = holdingParentView.findViewById(R.id.pricetxt);
        qtytxt = holdingParentView.findViewById(R.id.qtytxt);
        valuetxt = holdingParentView.findViewById(R.id.valuetxt);
        cmptxt = holdingParentView.findViewById(R.id.cmptxt);
        mtmtxt = holdingParentView.findViewById(R.id.mtmtxt);

        swipeRefresh = holdingParentView.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        linearLayoutManager = new LinearLayoutManager(getMainActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        streamController = new StreamingController();
        setUpViews(holdingParentView);
        commonAdapter = new MyListAdapter(dematLtpDetails, getMainActivity());
        recyclerView.setAdapter(commonAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                sendStreamingRequest();
            }
        });
        final Paint p = new Paint();
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                return super.getSwipeDirs(recyclerView, viewHolder);
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    if (position != -1) {
                        callBuySell(commonAdapter.getItem(position), "sell");
                        commonAdapter.notifyDataSetChanged();
                    }

                } else {
                    if (position != -1) {
                        callBuySell(commonAdapter.getItem(position), "buy");
                        commonAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Drawable icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;

                    if (dX > 0) {


                        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (300 - 20), itemView.getBottom());
                        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                            p.setColor(getActivity().getResources().getColor(R.color.whitetheambuyColor));
                        } else {
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
        itemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position != -1) {
                    DematHolding rowData = commonAdapter.getItem(position);
                    ltp = rowData.getLTP();
                    scrptName = rowData.getScripName();
                    if (rowData.getNSEToken().isEmpty() || rowData.getNSEToken().equalsIgnoreCase("0")) {
                        token = rowData.getBSEToken();
                    } else {
                        token = rowData.getNSEToken();
                    }
                    //to get holdingscripposition we are requesting to iris server
                    orderStreamingController.sendHoldingScripPositionInfo(getMainActivity(),
                            AccountDetails.getUsername(getContext()), AccountDetails.getSessionId(getContext()), token);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return holdingParentView;
    }

    private void setUpViews(View view) {
        currentValtxt = view.findViewById(R.id.currentVal);
        holdingValtxt = view.findViewById(R.id.holdingVal);
        currentMtmtxt = view.findViewById(R.id.currentMtm);
        setTheme();
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            TitleLayout.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            ColumnLauout.setBackgroundColor(getResources().getColor(R.color.selectColor));
            currentValtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            holdingValtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            currentMtmtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            emptyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
    }


    private void callBuySell(final DematHolding rowData, final String action) {

        if (!rowData.getNSEToken().equals("") && !rowData.getBSEToken().equals("")) {

            if ((!rowData.getNSEToken().equalsIgnoreCase("0")) && (!rowData.getBSEToken().equalsIgnoreCase("0"))) {

                if (rowData.getNSEToken().startsWith("101") && rowData.getBSEToken().startsWith("101")) {
                    //Only NSE Scrpts==========>

                    Bundle bundle = new Bundle();
                    bundle.putString("Action", action);
                    bundle.putString("Token", rowData.getNSEToken());
                    bundle.putString("SymbolName", rowData.getScripName());
                    bundle.putString("Lots", rowData.getLot());
                    bundle.putString("AssetType", getAssetType(rowData.getNSEToken()));
                    bundle.putString("UniqueId", rowData.getUniqueID());
                    bundle.putString("TradeSymbol", rowData.getNSETradeSymbol());
                    bundle.putString("InstType", rowData.getInstrumentType());
                    bundle.putString("ExchangeName", "NSE");
                    bundle.putBoolean("isFromDemat", true);
                    bundle.putString("TickSize", rowData.getTickSize());
                    bundle.putString("Multiplier", rowData.getMultiplier());
                    bundle.putString("LTP", rowData.getLTP());
                    bundle.putString("AvailableForSell", rowData.getAvailableForSell());
                    AccountDetails.globalPlaceOrderBundle = bundle;
                    EventBus.getDefault().post("placeorder");
                    //navigateTo(NAV_TO_TRADE_SCREEN, bundle, true);


                } else if (rowData.getNSEToken().startsWith("201") && rowData.getBSEToken().startsWith("201")) {

                    Bundle bundle = new Bundle();
                    bundle.putString("Action", action);
                    bundle.putString("Token", rowData.getBSEToken());
                    bundle.putString("SymbolName", rowData.getScripName());
                    bundle.putString("Lots", rowData.getLot());
                    bundle.putString("AssetType", getAssetType(rowData.getBSEToken()));
                    bundle.putString("UniqueId", rowData.getUniqueID());
                    bundle.putString("TradeSymbol", rowData.getBSETradeSymbol());
                    bundle.putString("InstType", rowData.getInstrumentType());
                    bundle.putString("ExchangeName", "BSE");
                    bundle.putBoolean("isFromDemat", true);
                    bundle.putString("TickSize", rowData.getTickSize());
                    bundle.putString("Multiplier", rowData.getMultiplier());
                    bundle.putString("LTP", rowData.getLTP());

                    bundle.putString("AvailableForSell", rowData.getAvailableForSell());

                    AccountDetails.globalPlaceOrderBundle = bundle;
                    EventBus.getDefault().post("placeorder");

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                    builder.setTitle("Select an exchange for trading").setCancelable(true).setItems(optionsArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Action", action);
                                bundle.putString("Token", rowData.getBSEToken());
                                bundle.putString("SymbolName", rowData.getScripName());
                                bundle.putString("Lots", rowData.getLot());
                                bundle.putString("AssetType", getAssetType(rowData.getBSEToken()));
                                bundle.putString("UniqueId", rowData.getUniqueID());
                                bundle.putString("holdingProduct", rowData.getProduct());
                                bundle.putString("TradeSymbol", rowData.getBSETradeSymbol());
                                bundle.putString("InstType", rowData.getInstrumentType());
                                bundle.putString("ExchangeName", "BSE");
                                bundle.putString("AvailableForSell", rowData.getAvailableForSell());
                                bundle.putString("TickSize", rowData.getTickSize());
                                bundle.putString("Multiplier", rowData.getMultiplier());
                                bundle.putBoolean("isFromDemat", true);
                                AccountDetails.globalPlaceOrderBundle = bundle;
                                EventBus.getDefault().post("placeorder");

                            } else if (which == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Action", action);
                                bundle.putString("Token", rowData.getNSEToken());
                                bundle.putString("SymbolName", rowData.getScripName());
                                bundle.putString("Lots", rowData.getLot());
                                bundle.putString("AssetType", getAssetType(rowData.getNSEToken()));

                                bundle.putString("UniqueId", rowData.getUniqueID());
                                bundle.putString("TradeSymbol", rowData.getNSETradeSymbol());
                                bundle.putString("InstType", rowData.getInstrumentType());
                                bundle.putString("ExchangeName", "NSE");
                                bundle.putBoolean("isFromDemat", true);
                                bundle.putString("AvailableForSell", rowData.getAvailableForSell());
                                bundle.putString("TickSize", rowData.getTickSize());
                                bundle.putString("Multiplier", rowData.getMultiplier());
                                AccountDetails.globalPlaceOrderBundle = bundle;
                                EventBus.getDefault().post("placeorder");
                            }

                        }
                    }).show();
                }

            } else if (rowData.getNSEToken().equalsIgnoreCase("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("Action", action);
                bundle.putString("Token", rowData.getBSEToken());
                bundle.putString("SymbolName", rowData.getScripName());
                bundle.putString("Lots", rowData.getLot());
                bundle.putString("AssetType", getAssetType(rowData.getBSEToken()));
                bundle.putString("UniqueId", rowData.getUniqueID());
                bundle.putString("TradeSymbol", rowData.getBSETradeSymbol());
                bundle.putString("InstType", rowData.getInstrumentType());
                bundle.putString("ExchangeName", "BSE");
                bundle.putBoolean("isFromDemat", true);
                bundle.putString("TickSize", rowData.getTickSize());
                bundle.putString("Multiplier", rowData.getMultiplier());
                bundle.putString("LTP", rowData.getLTP());

                bundle.putString("AvailableForSell", rowData.getAvailableForSell());

                AccountDetails.globalPlaceOrderBundle = bundle;
                EventBus.getDefault().post("placeorder");
            } else if (rowData.getBSEToken().equalsIgnoreCase("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("Action", action);
                bundle.putString("Token", rowData.getNSEToken());
                bundle.putString("SymbolName", rowData.getScripName());
                bundle.putString("Lots", rowData.getLot());
                bundle.putString("AssetType", getAssetType(rowData.getNSEToken()));
                bundle.putString("UniqueId", rowData.getUniqueID());
                bundle.putString("TradeSymbol", rowData.getNSETradeSymbol());
                bundle.putString("InstType", rowData.getInstrumentType());
                bundle.putString("ExchangeName", "NSE");
                bundle.putBoolean("isFromDemat", true);
                bundle.putString("TickSize", rowData.getTickSize());
                bundle.putString("Multiplier", rowData.getMultiplier());
                bundle.putString("LTP", rowData.getLTP());
                bundle.putString("AvailableForSell", rowData.getAvailableForSell());

                AccountDetails.globalPlaceOrderBundle = bundle;
                EventBus.getDefault().post("placeorder");
            }
        }
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

    private void sendStreamingRequest() {

        symbolsToUnsubscribe.clear();
        for (int i = 0; i < visibleSymbolTable.size(); i++) {
            symbolsToUnsubscribe.add(visibleSymbolTable.get(i));
        }
        streamController.sendStreamingRequest(getMainActivity(), visibleSymbolTable, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", visibleSymbolTable);
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

    public void sendDematHoldingRequest(int position) {

        if (dematLtpDetails != null) {
            dematLtpDetails.clear();
        }
        totalHoldingValue = 0.0;
        totalCurrentValue = 0.0;
        totalCurrentMTOM = 0.0;
            // holding Details request is send to iris server
        orderStreamingController.sendHoldingDetailsInfo(getContext(), AccountDetails.getUsername(getContext()),
                AccountDetails.getSessionId(getContext()), AccountDetails.getToken(getContext()));

/*        showProgress();
        WSHandler.getRequest(getMainActivity(), "getHoldingDataTCP" +
                "?sessionId=" + AccountDetails.getClientCode(getMainActivity()) +
                "&gscid=" + AccountDetails.getUsername(getMainActivity()) +
                "&requestType=HoldingDetailsInfo", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                toggleErrorLayout(false);
                totalHoldingValue = 0.0;
                totalCurrentValue = 0.0;
                totalCurrentMTOM = 0.0;

                try {
                    dematHoldingResponse = new TradeDematHoldingResponse();
                    streamingList = new ArrayList();

                    if (response.has("data")) {
                        JSONArray ja1 = response.getJSONArray("data");
                        commonAdapter.clear();
                        dematLtpDetails.clear();
                        for (int i = 0; i < ja1.length(); ++i) {
                            JSONObject o = (JSONObject) ja1.get(i);
                            DematHolding dmatholding = new DematHolding();
                            dmatholding.fromJSON(o);


                            double holdingValues = Integer.parseInt(dmatholding.getQty()) *
                                    Double.parseDouble(dmatholding.getHPrice());

                            totalHoldingValue = totalHoldingValue + holdingValues;
                            dmatholding.setHoldingvalue(String.valueOf(holdingValues));

                            double currentValue = Integer.parseInt(dmatholding.getQty()) *
                                    Double.parseDouble(dmatholding.getLtp());

                            dmatholding.setCurrentVal(String.valueOf(currentValue));


                            totalCurrentValue = totalCurrentValue + currentValue;

                            double currentMTOM = currentValue - holdingValues;
                            dmatholding.setCurrentMTM(String.valueOf(currentMTOM));

                            totalCurrentMTOM = totalCurrentMTOM + currentMTOM;

                            dematLtpDetails.add(dmatholding);
                            if (dmatholding.getNSEToken().isEmpty() || dmatholding.getNSEToken().equalsIgnoreCase("0")) {
                                streamingList.add(dmatholding.getBSEToken());
                                commonAdapter.addSymbol(dmatholding.getBSEToken());
                            } else {
                                streamingList.add(dmatholding.getNSEToken());
                                commonAdapter.addSymbol(dmatholding.getNSEToken());
                            }
                        }

                        holdingValtxt.setText(StringStuff.commaDecorator(String.valueOf(totalHoldingValue)));
                        currentValtxt.setText(String.valueOf(StringStuff.commaDecorator(String.valueOf(totalCurrentValue))));
                        currentMtmtxt.setText(StringStuff.commaDecorator(String.valueOf(totalCurrentMTOM)));

                        getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
                        sendStreamingRequest();

                        commonAdapter.notifyDataSetChanged();
                        hideProgress();


                        *//*
                        JSONArray ja1 = response.getJSONArray("data");

                        for (int i = 0; i < ja1.length(); ++i) {
                            JSONObject o = (JSONObject) ja1.get(i);

                            DematHolding dmatholding = new DematHolding();
                            dmatholding.fromJSON(o);

                            if(!dmatholding.getNSEToken().equalsIgnoreCase("0") && !dmatholding.getBSEToken().equalsIgnoreCase("0")) {
                                if(dmatholding.getBSEToken().equalsIgnoreCase(dmatholding.getNSEToken())) {


                                    if(getExchange(dmatholding.getNSEToken()).equalsIgnoreCase("nse")) {
                                        dmatholding.setBSEToken("0");
                                    } else if(getExchange(dmatholding.getNSEToken()).equalsIgnoreCase("bse")) {
                                        dmatholding.setNSEToken("0");
                                    }
                                }
                            }


                            dematHoldingResponse.getDematHolding().add(dmatholding);

                            DematList dematList = new DematList();


                            if(!dematHoldingResponse.getDematHolding().get(i).getNSEToken().equalsIgnoreCase("0")) {
                                commonAdapter.addSymbol(dematHoldingResponse.getDematHolding().get(i).getNSEToken());
                                dematList.setToken(dematHoldingResponse.getDematHolding().get(i).getNSEToken());
                                streamingList.add(dematHoldingResponse.getDematHolding().get(i).getNSEToken());

                            } else {
                                commonAdapter.addSymbol(dematHoldingResponse.getDematHolding().get(i).getBSEToken());
                                dematList.setToken(dematHoldingResponse.getDematHolding().get(i).getBSEToken());
                                streamingList.add(dematHoldingResponse.getDematHolding().get(i).getBSEToken());
                            }

                            dematLtpDetails.add(dmatholding);
                        }


                        commonAdapter.notifyDataSetChanged();

//                        populateHoldingsList();
                        streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);

                        refreshComplete();
                    *//*
                    }

                    hideProgress();
                    refreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    hideProgress();
                    toggleErrorLayout(true);

                }
            }

            @Override
            public void onFailure(String message) {

                holdingValtxt.setText("");
                currentMtmtxt.setText("");
                currentValtxt.setText("");

                hideProgress();
                toggleErrorLayout(true);
                refreshComplete();

            }
        });*/


    }


    public void onEventMainThread(HoldinginfoResponse response) {
        toggleErrorLayout(false);
        hideProgress();
        ArrayList<HoldinginfoResponse.Response.Data.StockDetail> stockArrayList =
                response.getResponse().getData().getStockDetails();

        int noofrecords = Integer.parseInt(response.getResponse().getData().getNoofrecords());
        int islast = Integer.parseInt(response.getResponse().getData().getIslast());

        Log.e("HoldingTAB", "noofrecords====>>" + noofrecords);
        Log.e("HoldingTAB", "islast====>>" + islast);
        Log.e("HoldingTAB", "islast====>>" + stockArrayList.size());

        if (stockArrayList != null && stockArrayList.size() > 0) {
            streamingList = new ArrayList();
            if (islast == 1 || (stockArrayList.size() == noofrecords && islast == 2)) {
                // Vanish previous records and show current records ( this is the 1st package )
                if (noofrecords > 0) {

                    toggleErrorLayout(false);
                    dematLtpDetails.clear();
                    totalHoldingValue = 0.0;
                    totalCurrentValue = 0.0;
                    totalCurrentMTOM = 0.0;

                    for (int i = 0; i < stockArrayList.size(); i++) {
                        DematHolding dmlist = new DematHolding();

                        dmlist.setScripName(stockArrayList.get(i).getSymbol());
                        dmlist.setSymbol(stockArrayList.get(i).getSymbol());
                        dmlist.setBSEToken(stockArrayList.get(i).getBSEToken());
                        dmlist.setClose(stockArrayList.get(i).getClose());
                        dmlist.setHoldingPrice(stockArrayList.get(i).getHPrice());
                        dmlist.setHPrice(stockArrayList.get(i).getHPrice());
                        dmlist.setInstrumentType(stockArrayList.get(i).getInstrument());
                        dmlist.setInstrument(stockArrayList.get(i).getInstrument());
                        dmlist.setcISINumber(stockArrayList.get(i).getIsin());
                        dmlist.setLTP(stockArrayList.get(i).getLtp());
                        dmlist.setNSEToken(stockArrayList.get(i).getNSEToken());
                        dmlist.setTNQ(stockArrayList.get(i).getQty());
                        dmlist.setQty(stockArrayList.get(i).getQty());

                        double holdingValues = Integer.parseInt(stockArrayList.get(i).getQty()) *
                                Double.parseDouble(stockArrayList.get(i).getHPrice());

                        totalHoldingValue = totalHoldingValue + holdingValues;
                        dmlist.setHoldingvalue(String.valueOf(holdingValues));

                        double currentValue = Integer.parseInt(stockArrayList.get(i).getQty()) *
                                Double.parseDouble(stockArrayList.get(i).getLtp());

                        dmlist.setCurrentVal(String.valueOf(currentValue));

                        totalCurrentValue = totalCurrentValue + currentValue;

                        double currentMTOM = currentValue - holdingValues;
                        dmlist.setCurrentMTM(String.valueOf(currentMTOM));

                        totalCurrentMTOM = totalCurrentMTOM + currentMTOM;

                        dematLtpDetails.add(dmlist);
                        if (stockArrayList.get(i).getNSEToken().equalsIgnoreCase("0")) {
                            streamingList.add(stockArrayList.get(i).getBSEToken());
                            commonAdapter.addSymbol(stockArrayList.get(i).getBSEToken());
                        } else {
                            streamingList.add(stockArrayList.get(i).getNSEToken());
                            commonAdapter.addSymbol(stockArrayList.get(i).getNSEToken());
                        }
                    }

                    holdingValtxt.setText(StringStuff.commaDecorator(String.valueOf(totalHoldingValue)));
                    currentValtxt.setText(String.valueOf(StringStuff.commaDecorator(String.valueOf(totalCurrentValue))));
                    currentMtmtxt.setText(StringStuff.commaDecorator(String.valueOf(totalCurrentMTOM)));
                    /*commonAdapter = new MyListAdapter(dematLtpDetails, getMainActivity());
                    recyclerView.setAdapter(commonAdapter);*/
                    commonAdapter.notifyDataSetChanged();
                } else {
                    toggleErrorLayout(true);
                }


            } else {
                //Appends new records with previous already exists records.

                for (int i = 0; i < stockArrayList.size(); i++) {
                    DematHolding dmlist = new DematHolding();

                    dmlist.setScripName(stockArrayList.get(i).getSymbol());
                    dmlist.setSymbol(stockArrayList.get(i).getSymbol());
                    dmlist.setBSEToken(stockArrayList.get(i).getBSEToken());
                    dmlist.setClose(stockArrayList.get(i).getClose());
                    dmlist.setHoldingPrice(stockArrayList.get(i).getHPrice());
                    dmlist.setHPrice(stockArrayList.get(i).getHPrice());
                    dmlist.setInstrumentType(stockArrayList.get(i).getInstrument());
                    dmlist.setInstrument(stockArrayList.get(i).getInstrument());
                    dmlist.setcISINumber(stockArrayList.get(i).getIsin());
                    dmlist.setLTP(stockArrayList.get(i).getLtp());
                    dmlist.setNSEToken(stockArrayList.get(i).getNSEToken());
                    dmlist.setTNQ(stockArrayList.get(i).getQty());
                    dmlist.setQty(stockArrayList.get(i).getQty());

                    double holdingValues = Integer.parseInt(stockArrayList.get(i).getQty()) *
                            Double.parseDouble(stockArrayList.get(i).getHPrice());

                    totalHoldingValue = totalHoldingValue + holdingValues;
                    dmlist.setHoldingvalue(String.valueOf(holdingValues));

                    double currentValue = Integer.parseInt(stockArrayList.get(i).getQty()) *
                            Double.parseDouble(stockArrayList.get(i).getLtp());

                    dmlist.setCurrentVal(String.valueOf(currentValue));


                    totalCurrentValue = totalCurrentValue + currentValue;

                    double currentMTOM = currentValue - holdingValues;
                    dmlist.setCurrentMTM(String.valueOf(currentMTOM));

                    totalCurrentMTOM = totalCurrentMTOM + currentMTOM;

                    dematLtpDetails.add(dmlist);
                    if (stockArrayList.get(i).getNSEToken().equalsIgnoreCase("0")) {
                        streamingList.add(stockArrayList.get(i).getBSEToken());
                        commonAdapter.addSymbol(stockArrayList.get(i).getBSEToken());
                    } else {
                        streamingList.add(stockArrayList.get(i).getNSEToken());
                        commonAdapter.addSymbol(stockArrayList.get(i).getNSEToken());
                    }

//                    commonAdapter.addSymbol(stockArrayList.get(i).getNSEToken());
                }

                holdingValtxt.setText(StringStuff.commaDecorator(String.valueOf(totalHoldingValue)));
                currentValtxt.setText(String.valueOf(StringStuff.commaDecorator(String.valueOf(totalCurrentValue))));
                currentMtmtxt.setText(StringStuff.commaDecorator(String.valueOf(totalCurrentMTOM)));

               /* commonAdapter = new MyListAdapter(dematLtpDetails, getMainActivity());
                recyclerView.setAdapter(commonAdapter);*/
                commonAdapter.notifyDataSetChanged();
            }


            /*getVisibleSymbolTokens(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
            sendStreamingRequest();*/
            refreshComplete();
        } else {
            refreshComplete();
            toggleErrorLayout(true);
        }
    }

    public void onEventMainThread(HoldingDetailResponse response) {
        DematHolding dmlist = new DematHolding();
        dmlist.setGscid(response.getResponse().getData().getGscid());
        dmlist.setBSEToken(token);
        dmlist.setNSEToken(token);
        dmlist.setNetHQty(response.getResponse().getData().getNetHoldingQty());
        dmlist.setNetHPrice(response.getResponse().getData().getNetHoldingPrice());
        dmlist.setDPQty(response.getResponse().getData().getDPQty());
        dmlist.setDPPrice(response.getResponse().getData().getDPPrice());
        dmlist.setPoolQty(response.getResponse().getData().getPoolQty());
        dmlist.setPoolPrice(response.getResponse().getData().getPoolPrice());
        dmlist.setMTFQty(response.getResponse().getData().getMTFQty());
        dmlist.setMTFPrice(response.getResponse().getData().getMTFPrice());
        dmlist.setActualDPQty(response.getResponse().getData().getActualDPQty());
        dmlist.setActualDPPrice(response.getResponse().getData().getActualDPPrice());
        dmlist.setActualPoolQty(response.getResponse().getData().getActualPoolQty());
        dmlist.setActualPoolPrice(response.getResponse().getData().getActualPoolPrice());
        dmlist.setActualMTFQty(response.getResponse().getData().getActualMTFQty());
        dmlist.setActualMTFPrice(response.getResponse().getData().getActualMTFPrice());
        dmlist.setPendingQty(response.getResponse().getData().getPendingQty());
        dmlist.setRiskBlockQty(response.getResponse().getData().getRiskBlockQty());
        dmlist.setSoldQty(response.getResponse().getData().getSoldQty());
        dmlist.setTBuyQty(response.getResponse().getData().getTodayBuyQty());
        dmlist.setTBuyATP(response.getResponse().getData().getTodayBuyATP());
        dmlist.setTSellQty(response.getResponse().getData().getTodaySellQty());
        dmlist.setTNQ(response.getResponse().getData().getTodayNetQty());
        dmlist.setTSellATP(response.getResponse().getData().getTodaySellATP());
        int free = Integer.parseInt(response.getResponse().getData().getNetHoldingQty()) - ((Integer.parseInt(response.getResponse().getData().getRiskBlockQty()) + Integer.parseInt(response.getResponse().getData().getSoldQty())));
        dmlist.setFreeHoldingQty(response.getResponse().getData().getFreeHOldingQty());
        dmlist.setScripName(response.getResponse().getData().getScripName());
        dmlist.setSymbol(response.getResponse().getData().getSymbol());
        dmlist.setcISINumber(response.getResponse().getData().getIsin());
        dmlist.setInstrumentType(response.getResponse().getData().getInstrument());
        dmlist.setLTP(ltp);
        double qty, atp;
        if (Double.parseDouble(response.getResponse().getData().getTodayBuyQty()) > 0) {
            qty = Double.parseDouble(response.getResponse().getData().getTodayBuyQty());
        } else {
            qty = 0.0;
        }
        if (Double.parseDouble(response.getResponse().getData().getTodayBuyATP()) > 0) {
            atp = Double.parseDouble(response.getResponse().getData().getTodayBuyATP());
        } else {
            atp = 0.0;
        }
        double sellqty, sellatp;
        if (Double.parseDouble(response.getResponse().getData().getTodaySellQty()) > 0) {
            sellqty = Double.parseDouble(response.getResponse().getData().getTodaySellQty());
        } else {
            sellqty = 0.0;
        }
        if (Double.parseDouble(response.getResponse().getData().getTodaySellATP()) > 0) {
            sellatp = Double.parseDouble(response.getResponse().getData().getTodaySellATP());
        } else {
            sellatp = 0.0;
        }

        dmlist.setTBuyAmt((qty * atp) + "");
        dmlist.setTSellAmt((sellqty * sellatp) + "");
        dmlist.setNetHValue(Integer.parseInt(response.getResponse().getData().getNetHoldingQty()) *
                Double.parseDouble(response.getResponse().getData().getNetHoldingPrice()) + "");

        dmlist.setScripName(response.getResponse().getData().getScripName());
        dmlist.setNSETradeSymbol(response.getResponse().getData().getScripName());
        dmlist.setBSETradeSymbol(response.getResponse().getData().getScripName());

        Bundle bundle = new Bundle();
        bundle.putSerializable("response", dmlist);
        AccountDetails.globalPlaceOrderBundle = bundle;
        EventBus.getDefault().post("HoldingDetail");
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
        try {

//            BroadcastPNL = Double.parseDouble(tempCurrentMtmtxt);

            if (commonAdapter.containsSymbol(response.getSymbol())) {
                boolean notifyNeeded = false;
                double prevMtm = 0.0, currentMtm = 0.0, prevLtp = 0.0, currentLtp = 0.0;

                DematHolding data = commonAdapter.getItem(commonAdapter.indexOf(response.getSymbol()));

                prevLtp = totalcurrentHoldingValue;
                prevMtm = totalCurrentValue;
                currentMtm = totalCurrentMTOM;

                if (!data.getLTP().equals(response.getLast())) {

                    if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                        data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                    } else {
                        data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                    }


                    double holdingValues = Integer.parseInt(data.getQty()) * Double.parseDouble(data.getHPrice());

                    totalHoldingValue = (totalHoldingValue - Double.parseDouble(data.getHoldingvalue())) + Double.parseDouble(data.getHoldingvalue());


                    double currentValue = Integer.parseInt(data.getQty()) * Double.parseDouble(data.getLtp());

                    totalCurrentValue = (totalCurrentValue - Double.parseDouble(data.getCurrentVal())) + currentValue;
                    data.setCurrentVal(String.valueOf(currentValue));

                    double currentMTOM = currentValue - holdingValues;
                    totalCurrentMTOM = (totalCurrentMTOM - Double.parseDouble(data.getCurrentMTM())) + currentMTOM;
                    data.setCurrentMTM(String.valueOf(currentMTOM));

                    holdingValtxt.setText(StringStuff.commaDecorator(String.valueOf(totalHoldingValue)));
                    currentValtxt.setText(String.valueOf(StringStuff.commaDecorator(String.valueOf(totalCurrentValue))));
                    currentMtmtxt.setText(StringStuff.commaDecorator(String.valueOf(totalCurrentMTOM)));
                    commonAdapter.updateData(commonAdapter.indexOf(response.getSymbol()), data);
                    commonAdapter.notifyItemChanged(commonAdapter.indexOf(response.getSymbol()));
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyListAdapter extends RecyclerView.Adapter<HoldingTabFragment.MyListAdapter.ViewHolder> {

        private List<DematHolding> topGainerList;
        private Context context;
        private ArrayList<String> tokenList;

        public MyListAdapter(List<DematHolding> orderBook, Context context) {
            this.context = context;
            this.topGainerList = orderBook;
            tokenList = new ArrayList<>();
        }

        @Override
        public HoldingTabFragment.MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_holding_tab, parent, false);
            return new HoldingTabFragment.MyListAdapter.ViewHolder(listItem);
        }

        public DematHolding getItem(int position) {
            if (getItemCount() > 0) return topGainerList.get(position);
            return null;
        }

        public void updateData(int position, DematHolding model) {
            topGainerList.set(position, model);
        }

        public void clear() {
            topGainerList.clear();
            this.tokenList.clear();
            notifyDataSetChanged();
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public void addSymbol(String symbol) {
            tokenList.add(symbol);
        }

        public ArrayList<String> getSymbolTable() {
            return tokenList;
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        @Override
        public void onBindViewHolder(HoldingTabFragment.MyListAdapter.ViewHolder holder, int position) {


            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.txt_script.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_qty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_cmp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_price.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_mtm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }

            DematHolding dematHolding = topGainerList.get(position);

            holder.txt_script.setText(dematHolding.getSymbol() + " - " + dematHolding.getInstrument());
            holder.txt_qty.setText(StringStuff.commaINRDecorator(dematHolding.getQty()));


            if (dematHolding.getLtp().equalsIgnoreCase("") || dematHolding.getLtp().equalsIgnoreCase("0")) {
                holder.txt_cmp.setText("0.00");
            } else {

                if (dematHolding.getAssetType() != null && dematHolding.getAssetType().equalsIgnoreCase("currency")) {

                    holder.txt_cmp.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(dematHolding.getLtp()))));

                } else {

                    holder.txt_cmp.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(dematHolding.getLtp()))));
                }
            }

            if (dematHolding.getAssetType() != null && dematHolding.getAssetType().equalsIgnoreCase("currency")) {

                holder.txt_price.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(dematHolding.getHoldingPrice()))));

            } else {
                holder.txt_price.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(dematHolding.getHPrice()))));
            }

            if (dematHolding.getAssetType() != null && dematHolding.getAssetType().equalsIgnoreCase("currency")) {

                holder.txt_value.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(dematHolding.getHoldingvalue()))));

            } else {
                holder.txt_value.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(dematHolding.getHoldingvalue()))));
            }

            if (dematHolding.getAssetType() != null && dematHolding.getAssetType().equalsIgnoreCase("currency")) {

                holder.txt_mtm.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(dematHolding.getCurrentMTM()))));

            } else {
                holder.txt_mtm.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(dematHolding.getCurrentMTM()))));
            }

        }


        @Override
        public int getItemCount() {
            return topGainerList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            GreekTextView txt_script, txt_price, txt_qty, txt_value, txt_cmp, txt_mtm;
            LinearLayout parent_layout;

            public ViewHolder(View itemView) {
                super(itemView);
                txt_script = itemView.findViewById(R.id.symbol_name);
                txt_price = itemView.findViewById(R.id.price);
                txt_qty = itemView.findViewById(R.id.qty);
                txt_value = itemView.findViewById(R.id.value);
                txt_cmp = itemView.findViewById(R.id.cmp);
                txt_mtm = itemView.findViewById(R.id.mtm);
                parent_layout = itemView.findViewById(R.id.parent_layout);
            }
        }
    }

    private void toggleErrorLayout(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
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

    private String getExchange(String token) {
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

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        EventBus.getDefault().register(this);


    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();

    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 40;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }
}


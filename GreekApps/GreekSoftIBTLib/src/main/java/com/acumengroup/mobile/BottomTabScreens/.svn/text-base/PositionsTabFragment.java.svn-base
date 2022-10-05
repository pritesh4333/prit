package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.DematHolding;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.model.tradeorderbook.OrderBook;
import com.acumengroup.greekmain.core.network.ProductChangeResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.services.CustomNetPositionSummary;
import com.acumengroup.greekmain.core.services.TradeNetPositionSummaryResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
 import com.acumengroup.mobile.model.OrderBookResponse;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.bfsl.core.network.NPDetailResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class PositionsTabFragment extends GreekBaseFragment {

    private OrderBookResponse orderBookResponse;
    private List<OrderBook> orderBook;
    private RelativeLayout errorMsgLayout;
    private LinearLayout title_layout, column_layout;
    private RecyclerView recyclerView;
    private MyListAdapter commonAdapter;
    private ArrayList streamingList;
    private StreamingController streamController;
    private TradeNetPositionSummaryResponse netPositionResponse;
    private SwipeRefreshLayout swipeRefresh;
    ArrayList<String> tokenList = new ArrayList<>();
    private List<CustomNetPositionSummary> positionList;
    private List<CustomNetPositionSummary> TemppositionList;
    private GreekTextView emptyView, totalmtmtxt, negScripCount, posScripCount, totalmtmtxtLabel, title1, title2, title3, title4,
            title5, title6, title7, title8;
    private ProgressBar netscripstotal;
    private LinearLayoutManager linearLayoutManager;
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private final ArrayList<String> symbolsToUnsubscribe = new ArrayList<>();
    private OrderStreamingController orderStreamingController = new OrderStreamingController();
    private View view;
    private  Boolean positionsymboleAssending=true;
    private  Boolean positionhValueAssending=true;

    public PositionsTabFragment() {
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            sendPTRRequest();
        }
    };

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        positionList = new ArrayList<>();
        TemppositionList = new ArrayList<>();
        AccountDetails.currentFragment = NAV_TO_PORTFOLIO_POSITION_SCREEN;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            sendPTRRequest();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_positions_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_positions_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        AccountDetails.currentFragment = NAV_TO_PORTFOLIO_POSITION_SCREEN;


        title_layout = view.findViewById(R.id.title_layout);
        column_layout = view.findViewById(R.id.column_layout);
        recyclerView = view.findViewById(R.id.recyclerView);
        totalmtmtxtLabel = view.findViewById(R.id.totalmtmtxtLabel);
        emptyView = view.findViewById(R.id.empty_view);
        swipeRefresh = view.findViewById(R.id.refreshList);
        totalmtmtxt = view.findViewById(R.id.totalmtmtxt);
        negScripCount = view.findViewById(R.id.negScripCount);
        title2 = view.findViewById(R.id.title2);
        title3 = view.findViewById(R.id.title3);
        title4 = view.findViewById(R.id.title4);
        title5 = view.findViewById(R.id.title5);
        title6 = view.findViewById(R.id.title6);
        title7 = view.findViewById(R.id.title7);
        title8 = view.findViewById(R.id.title8);
        title1 = view.findViewById(R.id.title1);
        posScripCount = view.findViewById(R.id.posScripCount);
        netscripstotal = view.findViewById(R.id.netscripstotal);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        streamController = new StreamingController();
        linearLayoutManager = new LinearLayoutManager(getMainActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getMainActivity()));
        commonAdapter = new MyListAdapter(positionList, getMainActivity());
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

        setTheme();

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
                int position = viewHolder.getLayoutPosition();

                Log.e("position Script Name ", positionList.get(position).getTradeSymbol());
                if (direction == ItemTouchHelper.RIGHT) {
                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        Bundle args2 = new Bundle();
                        args2.putString(TradeFragment.SCRIP_NAME, positionList.get(position).getTradeSymbol());
                        args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(positionList.get(position).getToken()));
                        args2.putString(TradeFragment.TOKEN, positionList.get(position).getToken());
                        args2.putString(TradeFragment.ASSET_TYPE, getAssetType(positionList.get(position).getToken()));
                        args2.putString(TradeFragment.UNIQUEID, "");
                        args2.putString(TradeFragment.TRADE_SYMBOL, positionList.get(position).getTradeSymbol());
                        args2.putString(TradeFragment.LOT_QUANTITY, positionList.get(position).getLotQty());
                        args2.putString(TradeFragment.TICK_SIZE, positionList.get(position).getTickSize());
                        args2.putString(TradeFragment.MULTIPLIER, positionList.get(position).getMultiplier());
                        args2.putString(TradeFragment.TRADE_ACTION, "buy");
                        args2.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp(positionList.get(position).getExpiry_date(), "dd MMM yyyy", "bse"));
                        args2.putString(TradeFragment.STRICKPRICE, positionList.get(position).getStrike_price());
                        args2.putString("OptType", positionList.get(position).getOption_type());
                        AccountDetails.globalPlaceOrderBundle = args2;
                        EventBus.getDefault().post("placeorder");
                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            }
                        });
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }
                    commonAdapter.notifyDataSetChanged();

                } else {

                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        Bundle args2 = new Bundle();
                        args2.putString(TradeFragment.SCRIP_NAME, positionList.get(position).getTradeSymbol());
                        args2.putString(TradeFragment.EXCHANGE_NAME, getExchange(positionList.get(position).getToken()));
                        args2.putString(TradeFragment.TOKEN, positionList.get(position).getToken());
                        args2.putString(TradeFragment.ASSET_TYPE, getAssetType(positionList.get(position).getToken()));
                        args2.putString(TradeFragment.UNIQUEID, "");
                        args2.putString(TradeFragment.TRADE_SYMBOL, positionList.get(position).getTradeSymbol());
                        args2.putString(TradeFragment.LOT_QUANTITY, positionList.get(position).getLotQty());
                        args2.putString(TradeFragment.TICK_SIZE, positionList.get(position).getTickSize());
                        args2.putString(TradeFragment.MULTIPLIER, positionList.get(position).getMultiplier());
                        args2.putString(TradeFragment.TRADE_ACTION, "sell");
                        args2.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp(positionList.get(position).getExpiry_date(), "dd MMM yyyy", "bse"));
                        args2.putString(TradeFragment.STRICKPRICE, positionList.get(position).getStrike_price());
                        args2.putString("OptType", positionList.get(position).getOption_type());
                        AccountDetails.globalPlaceOrderBundle = args2;
                        EventBus.getDefault().post("placeorder");

                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            }
                        });
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }
                    commonAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Drawable icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    int height = itemView.getBottom() - itemView.getTop();
                    int width = itemView.getRight() - itemView.getLeft();
                    float iconH = getActivity().getResources().getDisplayMetrics().density * 28;
                    float iconW = getActivity().getResources().getDisplayMetrics().density * 28;

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
                    Bundle bundle = new Bundle();
                    CustomNetPositionSummary rowData = commonAdapter.getItem(position);
                    bundle.putSerializable("response", rowData);
                    bundle.putString("fromPage", "DayPosition");
                    AccountDetails.globalPlaceOrderBundle = bundle;
                    EventBus.getDefault().post("NetpositionOption");
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!positionsymboleAssending){
                    positionsymboleAssending=true;
                }else{
                    positionsymboleAssending=false;
                }


                if(positionsymboleAssending){
                    Collections.sort(positionList, new Comparator<CustomNetPositionSummary>() {

                        public int compare(CustomNetPositionSummary c1, CustomNetPositionSummary c2) {
                            return c1.getDescription().compareTo(c2.getDescription());
                        }
                    });
                }else{
                    Collections.sort(positionList, new Comparator<CustomNetPositionSummary>() {

                        public int compare(CustomNetPositionSummary c1, CustomNetPositionSummary c2) {
                            return c1.getDescription().compareTo(c2.getDescription());
                        }
                    });
                    Collections.reverse(positionList);
                }

                commonAdapter = new MyListAdapter(positionList, getMainActivity());
                recyclerView.setAdapter(commonAdapter);
                for (int i =0;i<positionList.size();i++){

                    CustomNetPositionSummary dematHolding= positionList.get(i);

                    streamingList.add(dematHolding.getToken());
                    commonAdapter.addSymbol(dematHolding.getToken());
                }

            }
        });
        title5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!positionhValueAssending){
                    positionhValueAssending=true;
                }else{
                    positionhValueAssending=false;
                }


                if(positionhValueAssending){
                    Collections.sort(positionList, new Comparator<CustomNetPositionSummary>() {
                        public int compare(CustomNetPositionSummary c1, CustomNetPositionSummary c2) {
                            return  (int)(Double.valueOf(c1.getNetAvg()) - Double.valueOf(c2.getNetAvg()));

                        }
                    });
                }else{
                    Collections.sort(positionList, new Comparator<CustomNetPositionSummary>() {
                        public int compare(CustomNetPositionSummary c1, CustomNetPositionSummary c2) {
                            return  (int)(Double.valueOf(c1.getNetAvg()) - Double.valueOf(c2.getNetAvg()));

                        }
                    });
                    Collections.reverse(positionList);
                }

                commonAdapter = new MyListAdapter(positionList, getMainActivity());
                recyclerView.setAdapter(commonAdapter);
                for (int i =0;i<positionList.size();i++){

                    CustomNetPositionSummary dematHolding= positionList.get(i);

                    streamingList.add(dematHolding.getToken());
                    commonAdapter.addSymbol(dematHolding.getToken());
                }

            }
        });

        return view;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            column_layout.setBackgroundColor(getResources().getColor(R.color.selectColor));
            emptyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalmtmtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            negScripCount.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            posScripCount.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalmtmtxtLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        }
    }

    private static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
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

    private void sendPTRRequest() {

        if (positionList != null) {
            positionList.clear();
        } else {
            positionList = new ArrayList<>();
        }
        //to get netpositonDetails we are requesting to iris server
        orderStreamingController.sendNPDetailRequest(getContext(), AccountDetails.getUsername(getContext()),
                AccountDetails.getSessionId(getContext()), AccountDetails.getToken(getContext()));
        /*showProgress();// Arachne API details..................
        String serviceURL = "getNetPosition_Interportability_V3_Web?SessionId=" + AccountDetails.getSessionId(getActivity()) + "&ClientCode=" + AccountDetails.getClientCode(getActivity()) + "&assetType=" + "all" + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&interportability=true";
        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    toggleErrorLayout(false);
                    commonAdapter.clear();
                    streamingList = new ArrayList();
                    netPositionResponse = new TradeNetPositionSummaryResponse();

                    if(response.has("data")) {
                        JSONArray ja1 = response.getJSONArray("data");

                        for (int i = 0; i < ja1.length(); ++i) {
                            JSONObject o = (JSONObject) ja1.get(i);

                            CustomNetPositionSummary customNetPositionSummary = new CustomNetPositionSummary();
                            OverAllPL overAllPL = new OverAllPL();
                            customNetPositionSummary.fromJSON(o);
                            netPositionResponse.getNetPositionSummary().add(customNetPositionSummary);

                            overAllPL.fromJSON(o);
                            netPositionResponse.setOverAllPL(overAllPL);
                            streamingList.add(netPositionResponse.getNetPositionSummary().get(i).getToken());


                            List<CustomNetPositionSummary> positionSummaries = netPositionResponse.getNetPositionSummary();

                            commonAdapter.addData(netPositionResponse.getNetPositionSummary().get(i));
                            commonAdapter.addSymbol(netPositionResponse.getNetPositionSummary().get(i).getToken());

                            streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);

                        }

                        commonAdapter.notifyDataSetChanged();

                        calculateTotal();
                        calculateMTM(netPositionResponse.getNetPositionSummary());

//                    calculatedayMTM(commonAdapter.getData());

                        hideProgress();
                        refreshComplete();
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(String message) {
                toggleErrorLayout(true);
                refreshComplete();
            }
        });*/

    }


    public void onEventMainThread(NPDetailResponse response) {
        toggleErrorLayout(false);
        ArrayList<NPDetailResponse.Response.Data.StockDetail> stockArrayList = response.getResponse().getData().getStockDetails();

        int noofrecords = Integer.parseInt(response.getResponse().getData().getNoofrecords());
        int islast = Integer.parseInt(response.getResponse().getData().getIslast());

//        Log.e("HoldingTAB", "noofrecords====>>" + noofrecords);
//        Log.e("HoldingTAB", "islast====>>" + islast);

        if (stockArrayList != null && stockArrayList.size() > 0) {
            streamingList = new ArrayList();
            if (islast == 1 || (stockArrayList.size() == noofrecords && islast == 2)) {
                // Vanish previous records and show current records ( this is the 1st package )
                if (noofrecords > 0) {
                    positionList.clear();
                    toggleErrorLayout(false);

                    for (int i = 0; i < stockArrayList.size(); i++) {

                        CustomNetPositionSummary dmlist = new CustomNetPositionSummary();

                        dmlist.setDescription(stockArrayList.get(i).getTradeSymbol());
                        dmlist.setBSEToken(stockArrayList.get(i).getBSEToken());
                        dmlist.setNSEToken(stockArrayList.get(i).getNSEToken());
                        dmlist.setClose(stockArrayList.get(i).getClose());
                        dmlist.setInstrument(stockArrayList.get(i).getInstrument());
                        dmlist.setpAmt(stockArrayList.get(i).getPAmt());
                        dmlist.setProductType(stockArrayList.get(i).getProductType());
                        dmlist.setTradeSymbol(stockArrayList.get(i).getTradeSymbol());
                        dmlist.setLotQty(stockArrayList.get(i).getLotQty());
                        dmlist.setToken(stockArrayList.get(i).getToken());
                        dmlist.setBuyQty(stockArrayList.get(i).getBuyQty());
                        dmlist.setBuyAmt(stockArrayList.get(i).getBuyAmt());
                        dmlist.setSellQty(stockArrayList.get(i).getSellQty());
                        dmlist.setSellAmt(stockArrayList.get(i).getSellAmt());
                        dmlist.setSqoffToken(stockArrayList.get(i).getSqoffToken());
                        dmlist.setMultiplier(stockArrayList.get(i).getMultiplier());
                        dmlist.setPrice_multiplier(stockArrayList.get(i).getPrice_multiplier());
                        dmlist.setTickSize(stockArrayList.get(i).getTickSize());
                        dmlist.setExpiry_date(stockArrayList.get(i).getExpiry_date());
                        dmlist.setOption_type(stockArrayList.get(i).getOption_type());
                        dmlist.setStrike_price(stockArrayList.get(i).getStrike_price());

                        int netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());

                        //Pravin Pasi Shared Logic for more info Directly call him.
                        if (getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("MCX") ||
                                getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("NCDEX")) {

                            netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());
                            netQty = netQty / Integer.parseInt(stockArrayList.get(i).getLotQty());

                            dmlist.setPreNetQty(String.valueOf(
                                    Integer.parseInt(stockArrayList.get(i).getPreNetQty()) / Integer.parseInt(stockArrayList.get(i).getLotQty())
                            ));


                        } else if (getAssetType(stockArrayList.get(i).getToken()).equalsIgnoreCase("currency")) {

                            netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());
                            netQty = netQty / Integer.parseInt(stockArrayList.get(i).getMultiplier());

                            dmlist.setPreNetQty(String.valueOf(Integer.parseInt(stockArrayList.get(i).getPreNetQty()) / Integer.parseInt(stockArrayList.get(i).getMultiplier())));


                        } else {
                            netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());
                            dmlist.setPreNetQty(stockArrayList.get(i).getPreNetQty());

                        }


                        dmlist.setNetQty(String.valueOf(netQty));

                        double netAmt = Double.parseDouble(stockArrayList.get(i).getBuyAmt()) - Double.parseDouble(stockArrayList.get(i).getSellAmt());
                        dmlist.setDayNetAmt(String.valueOf(netAmt));
                        dmlist.setSqoffToken(stockArrayList.get(i).getSqoffToken());

                        if (netQty != 0) {

                            //Pravin Pasi Shared Logic for more info Directly call him.
                            if (getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("MCX") ||
                                    getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("NCDEX")) {

                                dmlist.setNetAvg(String.valueOf(((netAmt / netQty) / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                        / Integer.parseInt(stockArrayList.get(i).getLotQty())));

                                if (Integer.parseInt(stockArrayList.get(i).getBuyQty()) > 0) {

                                    dmlist.setBuyAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getBuyAmt()) / Integer.parseInt(stockArrayList.get(i).getBuyQty()))
                                            / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))));
                                } else {
                                    dmlist.setBuyAvg("0");
                                }

                                if (Integer.parseInt(stockArrayList.get(i).getSellQty()) > 0) {
                                    dmlist.setSellAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getSellAmt()) / Integer.parseInt(stockArrayList.get(i).getSellQty()))
                                            / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                    ));
                                } else {
                                    dmlist.setSellAvg("0");
                                }


                            } else if (getAssetType(stockArrayList.get(i).getToken()).equalsIgnoreCase("currency")) {
                                dmlist.setNetAvg(String.valueOf((netAmt / netQty) / Double.parseDouble(stockArrayList.get(i).getMultiplier())));

                                double buyAvg = Double.parseDouble(dmlist.getBuyAmt()) / Integer.parseInt(dmlist.getBuyQty());
                                double sellAvg = Double.parseDouble(dmlist.getSellAmt()) / Integer.parseInt(dmlist.getSellQty());

                                if (Integer.parseInt(dmlist.getBuyQty()) > 0) {
                                    dmlist.setBuyAvg(String.valueOf(buyAvg));
                                } else {
                                    dmlist.setBuyAvg("0");
                                }

                                if (Integer.parseInt(dmlist.getSellQty()) > 0) {
                                    dmlist.setSellAvg(String.valueOf(sellAvg));
                                } else {
                                    dmlist.setSellAvg("0");
                                }

                            } else {
                                dmlist.setNetAvg(String.valueOf(netAmt / netQty));

                                double buyAvg = Double.parseDouble(dmlist.getBuyAmt()) / Integer.parseInt(dmlist.getBuyQty());
                                double sellAvg = Double.parseDouble(dmlist.getSellAmt()) / Integer.parseInt(dmlist.getSellQty());

                                if (Integer.parseInt(dmlist.getBuyQty()) > 0) {
                                    dmlist.setBuyAvg(String.valueOf(buyAvg));
                                } else {
                                    dmlist.setBuyAvg("0");
                                }

                                if (Integer.parseInt(dmlist.getSellQty()) > 0) {
                                    dmlist.setSellAvg(String.valueOf(sellAvg));
                                } else {
                                    dmlist.setSellAvg("0");
                                }
                            }

                        } else {

                            if (getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("MCX") ||
                                    getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("NCDEX")) {

                                dmlist.setNetAvg(String.valueOf(((netAmt / netQty) / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                        / Integer.parseInt(stockArrayList.get(i).getLotQty())));

                                if (Integer.parseInt(stockArrayList.get(i).getBuyQty()) > 0) {

                                    dmlist.setBuyAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getBuyAmt()) / Integer.parseInt(stockArrayList.get(i).getBuyQty()))
                                            / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))));
                                } else {
                                    dmlist.setBuyAvg("0");
                                }

                                if (Integer.parseInt(stockArrayList.get(i).getSellQty()) > 0) {
                                    dmlist.setSellAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getSellAmt()) / Integer.parseInt(stockArrayList.get(i).getSellQty()))
                                            / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                    ));
                                } else {
                                    dmlist.setSellAvg("0");
                                }
                            } else {
                                dmlist.setNetAvg(String.valueOf("0.00"));

                                double buyAvg = Double.parseDouble(dmlist.getBuyAmt()) / Integer.parseInt(dmlist.getBuyQty());
                                double sellAvg = Double.parseDouble(dmlist.getSellAmt()) / Integer.parseInt(dmlist.getSellQty());

                                if (Integer.parseInt(dmlist.getBuyQty()) > 0) {
                                    dmlist.setBuyAvg(String.valueOf(buyAvg));
                                } else {
                                    dmlist.setBuyAvg("0");
                                }

                                if (Integer.parseInt(dmlist.getSellQty()) > 0) {
                                    dmlist.setSellAvg(String.valueOf(sellAvg));
                                } else {
                                    dmlist.setSellAvg("0");
                                }
                            }
                        }


                        dmlist.setLtp(stockArrayList.get(i).getLtp());
                        dmlist.setClose(stockArrayList.get(i).getClose());
                        dmlist.setMultiplier(stockArrayList.get(i).getMultiplier());

//                        commonAdapter.addData(dmlist);
                        if (!stockArrayList.get(i).getSymbol().equalsIgnoreCase("undefined")) {
                            positionList.add(dmlist);
                        }



                        streamingList.add(stockArrayList.get(i).getToken());
                        commonAdapter.addSymbol(stockArrayList.get(i).getToken());

                    }
                    commonAdapter.notifyDataSetChanged();
                    calculatePostiveAndNegativeScripts();
                    calculateTotalMTM(positionList);

                } else {
                    toggleErrorLayout(true);
                }


            } else {
                //Appends new records with previous already exists records.

                for (int i = 0; i < stockArrayList.size(); i++) {

                    CustomNetPositionSummary dmlist = new CustomNetPositionSummary();

                    dmlist.setDescription(stockArrayList.get(i).getTradeSymbol());
                    dmlist.setBSEToken(stockArrayList.get(i).getBSEToken());
                    dmlist.setNSEToken(stockArrayList.get(i).getNSEToken());
                    dmlist.setClose(stockArrayList.get(i).getClose());
                    dmlist.setInstrument(stockArrayList.get(i).getInstrument());
                    dmlist.setpAmt(stockArrayList.get(i).getPAmt());
                    dmlist.setProductType(stockArrayList.get(i).getProductType());
                    dmlist.setTradeSymbol(stockArrayList.get(i).getTradeSymbol());
                    dmlist.setLotQty(stockArrayList.get(i).getLotQty());
                    dmlist.setToken(stockArrayList.get(i).getToken());
                    dmlist.setBuyQty(stockArrayList.get(i).getBuyQty());
                    dmlist.setBuyAmt(stockArrayList.get(i).getBuyAmt());
                    dmlist.setSellQty(stockArrayList.get(i).getSellQty());
                    dmlist.setSellAmt(stockArrayList.get(i).getSellAmt());
                    dmlist.setSqoffToken(stockArrayList.get(i).getSqoffToken());
                    dmlist.setMultiplier(stockArrayList.get(i).getMultiplier());
                    dmlist.setPrice_multiplier(stockArrayList.get(i).getPrice_multiplier());
                    dmlist.setTickSize(stockArrayList.get(i).getTickSize());
                    dmlist.setExpiry_date(stockArrayList.get(i).getExpiry_date());
                    dmlist.setOption_type(stockArrayList.get(i).getOption_type());
                    dmlist.setStrike_price(stockArrayList.get(i).getStrike_price());

                    int netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());

                    //Pravin Pasi Shared Logic for more info Directly call him.
                    if (getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("MCX") ||
                            getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("NCDEX")) {

                        netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());
                        netQty = netQty / Integer.parseInt(stockArrayList.get(i).getLotQty());

                        dmlist.setPreNetQty(String.valueOf(
                                Integer.parseInt(stockArrayList.get(i).getPreNetQty()) / Integer.parseInt(stockArrayList.get(i).getLotQty())
                        ));


                    } else if (getAssetType(stockArrayList.get(i).getToken()).equalsIgnoreCase("currency")) {

                        netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());
                        netQty = netQty / Integer.parseInt(stockArrayList.get(i).getMultiplier());

                        dmlist.setPreNetQty(String.valueOf(Integer.parseInt(stockArrayList.get(i).getPreNetQty()) / Integer.parseInt(stockArrayList.get(i).getMultiplier())));


                    } else {
                        netQty = Integer.parseInt(stockArrayList.get(i).getBuyQty()) - Integer.parseInt(stockArrayList.get(i).getSellQty());
                        dmlist.setPreNetQty(stockArrayList.get(i).getPreNetQty());

                    }


                    dmlist.setNetQty(String.valueOf(netQty));

                    double netAmt = Double.parseDouble(stockArrayList.get(i).getBuyAmt()) - Double.parseDouble(stockArrayList.get(i).getSellAmt());
                    dmlist.setDayNetAmt(String.valueOf(netAmt));
                    dmlist.setSqoffToken(stockArrayList.get(i).getSqoffToken());

                    if (netQty != 0) {

                        //Pravin Pasi Shared Logic for more info Directly call him.
                        if (getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("MCX") ||
                                getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("NCDEX")) {

                            dmlist.setNetAvg(String.valueOf(((netAmt / netQty) / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                    / Integer.parseInt(stockArrayList.get(i).getLotQty())));

                            if (Integer.parseInt(stockArrayList.get(i).getBuyQty()) > 0) {

                                dmlist.setBuyAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getBuyAmt()) / Integer.parseInt(stockArrayList.get(i).getBuyQty()))
                                        / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))));
                            } else {
                                dmlist.setBuyAvg("0");
                            }

                            if (Integer.parseInt(stockArrayList.get(i).getSellQty()) > 0) {
                                dmlist.setSellAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getSellAmt()) / Integer.parseInt(stockArrayList.get(i).getSellQty()))
                                        / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                ));
                            } else {
                                dmlist.setSellAvg("0");
                            }


                        } else if (getAssetType(stockArrayList.get(i).getToken()).equalsIgnoreCase("currency")) {
                            dmlist.setNetAvg(String.valueOf((netAmt / netQty) / Double.parseDouble(stockArrayList.get(i).getMultiplier())));

                            double buyAvg = Double.parseDouble(dmlist.getBuyAmt()) / Integer.parseInt(dmlist.getBuyQty());
                            double sellAvg = Double.parseDouble(dmlist.getSellAmt()) / Integer.parseInt(dmlist.getSellQty());

                            if (Integer.parseInt(dmlist.getBuyQty()) > 0) {
                                dmlist.setBuyAvg(String.valueOf(buyAvg));
                            } else {
                                dmlist.setBuyAvg("0");
                            }

                            if (Integer.parseInt(dmlist.getSellQty()) > 0) {
                                dmlist.setSellAvg(String.valueOf(sellAvg));
                            } else {
                                dmlist.setSellAvg("0");
                            }

                        } else {
                            dmlist.setNetAvg(String.valueOf(netAmt / netQty));

                            double buyAvg = Double.parseDouble(dmlist.getBuyAmt()) / Integer.parseInt(dmlist.getBuyQty());
                            double sellAvg = Double.parseDouble(dmlist.getSellAmt()) / Integer.parseInt(dmlist.getSellQty());

                            if (Integer.parseInt(dmlist.getBuyQty()) > 0) {
                                dmlist.setBuyAvg(String.valueOf(buyAvg));
                            } else {
                                dmlist.setBuyAvg("0");
                            }

                            if (Integer.parseInt(dmlist.getSellQty()) > 0) {
                                dmlist.setSellAvg(String.valueOf(sellAvg));
                            } else {
                                dmlist.setSellAvg("0");
                            }
                        }

                    } else {

                        if (getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("MCX") ||
                                getExchange(stockArrayList.get(i).getToken()).equalsIgnoreCase("NCDEX")) {

                            dmlist.setNetAvg(String.valueOf(((netAmt / netQty) / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                    / Integer.parseInt(stockArrayList.get(i).getLotQty())));

                            if (Integer.parseInt(stockArrayList.get(i).getBuyQty()) > 0) {

                                dmlist.setBuyAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getBuyAmt()) / Integer.parseInt(stockArrayList.get(i).getBuyQty()))
                                        / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))));
                            } else {
                                dmlist.setBuyAvg("0");
                            }

                            if (Integer.parseInt(stockArrayList.get(i).getSellQty()) > 0) {
                                dmlist.setSellAvg(String.valueOf(((Double.parseDouble(stockArrayList.get(i).getSellAmt()) / Integer.parseInt(stockArrayList.get(i).getSellQty()))
                                        / Double.parseDouble(stockArrayList.get(i).getPrice_multiplier()))
                                ));
                            } else {
                                dmlist.setSellAvg("0");
                            }
                        } else {
                            dmlist.setNetAvg(String.valueOf("0.00"));

                            double buyAvg = Double.parseDouble(dmlist.getBuyAmt()) / Integer.parseInt(dmlist.getBuyQty());
                            double sellAvg = Double.parseDouble(dmlist.getSellAmt()) / Integer.parseInt(dmlist.getSellQty());

                            if (Integer.parseInt(dmlist.getBuyQty()) > 0) {
                                dmlist.setBuyAvg(String.valueOf(buyAvg));
                            } else {
                                dmlist.setBuyAvg("0");
                            }

                            if (Integer.parseInt(dmlist.getSellQty()) > 0) {
                                dmlist.setSellAvg(String.valueOf(sellAvg));
                            } else {
                                dmlist.setSellAvg("0");
                            }
                        }
                    }


                    dmlist.setLtp(stockArrayList.get(i).getLtp());
                    dmlist.setClose(stockArrayList.get(i).getClose());
                    dmlist.setMultiplier(stockArrayList.get(i).getMultiplier());


//                        commonAdapter.addData(dmlist);
                    if (!stockArrayList.get(i).getSymbol().equalsIgnoreCase("undefined")) {
                        positionList.add(dmlist);
                    }

                   /* if (stockArrayList.get(i).getNSEToken().equalsIgnoreCase("0")) {
                        streamingList.add(stockArrayList.get(i).getBSEToken());
                        commonAdapter.addSymbol(stockArrayList.get(i).getBSEToken());
                    } else {
                        streamingList.add(stockArrayList.get(i).getNSEToken());
                        commonAdapter.addSymbol(stockArrayList.get(i).getNSEToken());
                    }*/

                    streamingList.add(stockArrayList.get(i).getToken());
                    commonAdapter.addSymbol(stockArrayList.get(i).getToken());

                }
                commonAdapter.notifyDataSetChanged();
                calculatePostiveAndNegativeScripts();
                calculateTotalMTM(positionList);

            }
            refreshComplete();
        } else {
            toggleErrorLayout(true);
            refreshComplete();
        }
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
            long startTime = System.nanoTime();
            for (int i = 0; i < commonAdapter.getItemCount(); i++) {

                //Single(Script) Token can hold multiple records because of product types=======================>>>>>
                if (commonAdapter.getItem(i).getToken().equals(response.getSymbol())) {
                    CustomNetPositionSummary rowData = commonAdapter.getItem(i);
                    rowData.setToken(response.getSymbol());
                    rowData.setLtp(response.getLast());

                    commonAdapter.updateData(i, rowData);
                    commonAdapter.notifyDataSetChanged();
                    calculatebcastmtm(commonAdapter.getData());

                }
            }
        }

    }

    private void calculatebcastmtm(List<CustomNetPositionSummary> data) {

        double totMTM = 0.0, NetAmt = 0.0;
        for (CustomNetPositionSummary netPositionSummary : data) {

            double mtm = 0.0;
            NetAmt = Double.parseDouble(netPositionSummary.getDayNetAmt());
            double preAmount = Double.parseDouble(netPositionSummary.getpAmt()) + NetAmt;

            int PrevNetQty = Integer.parseInt(netPositionSummary.getPreNetQty()) + Integer.parseInt(netPositionSummary.getNetQty());

            if (PrevNetQty != 0) {

                double ltpClose = Double.parseDouble(netPositionSummary.getLtp()) > 0 ? Double.parseDouble(netPositionSummary.getLtp()) : Double.parseDouble(netPositionSummary.getClose()) > 0 ? Double.parseDouble(netPositionSummary.getClose()) : 0.0;
                double price = 0.0;

                if (getAssetType(netPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                    if (PrevNetQty != 0) {
                        price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(netPositionSummary.getPrice_multiplier())
                                * Integer.parseInt(netPositionSummary.getLotQty()))));
                    } else {
                        price = 0.0;
                    }
                } else if (getAssetType(netPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                    if (PrevNetQty != 0) {
                        price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(netPositionSummary.getMultiplier()))));
                    } else {
                        price = 0.0;
                    }
                } else {
                    price = Math.abs(preAmount / PrevNetQty);
                }

                if (price != 0) {
                    if (getAssetType(netPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                        mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(netPositionSummary.getPrice_multiplier()) * Integer.parseInt(netPositionSummary.getLotQty())));
                    } else {

                        mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(netPositionSummary.getMultiplier())));
                    }

                } else {
                    mtm = 0.0;
                }
            } else {
                mtm = preAmount * (-1);
            }
            totMTM = totMTM + mtm;
        }

        totalmtmtxt.setText(StringStuff.commaDecorator(String.format("%.2f", totMTM)));
        if (totalmtmtxt.getText().toString().startsWith("-")) {
            totalmtmtxt.setTextColor(getResources().getColor(R.color.sellColor));
        } else {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                totalmtmtxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            } else {
                totalmtmtxt.setTextColor(getResources().getColor(R.color.buyColor));
            }
        }
    }

    private void calculateTotalMTM(List<CustomNetPositionSummary> customNetPositionSummary) {

        double totMTM = 0.0, NetAmt = 0.0;
        for (int i = 0; i < customNetPositionSummary.size(); i++) {
            double mtm = 0.0;

            NetAmt = Double.parseDouble(customNetPositionSummary.get(i).getDayNetAmt());
            double preAmount = Double.parseDouble(customNetPositionSummary.get(i).getpAmt()) + NetAmt;


            int PrevNetQty = Integer.parseInt(customNetPositionSummary.get(i).getPreNetQty()) +
                    Integer.parseInt(customNetPositionSummary.get(i).getNetQty());

            if (PrevNetQty != 0) {

                double ltpClose = Double.parseDouble(customNetPositionSummary.get(i).getLtp()) > 0 ?
                        Double.parseDouble(customNetPositionSummary.get(i).getLtp())
                        : Double.parseDouble(customNetPositionSummary.get(i).getClose()) > 0 ?
                        Double.parseDouble(customNetPositionSummary.get(i).getClose()) : 0.0;

                double price = 0.0;

                if (getAssetType(customNetPositionSummary.get(i).getToken()).equalsIgnoreCase("commodity")) {
                    price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.get(i).getPrice_multiplier()) * Double.parseDouble(customNetPositionSummary.get(i).getLotQty()))));
                } else if (getAssetType(customNetPositionSummary.get(i).getToken()).equalsIgnoreCase("currency")) {
                    price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.get(i).getMultiplier()))));
                } else {
                    price = Math.abs(preAmount / PrevNetQty);
                }

                if (price != 0) {
                    if (getAssetType(customNetPositionSummary.get(i).getToken()).equalsIgnoreCase("commodity")) {
                        mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(customNetPositionSummary.get(i).getPrice_multiplier()) * Integer.parseInt(customNetPositionSummary.get(i).getLotQty())));
                    } else {
                        mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(customNetPositionSummary.get(i).getMultiplier())));
                    }

                } else {
                    mtm = 0.0;
                }
            } else {

                mtm = preAmount * (-1);
            }
            totMTM = totMTM + mtm;

        }
        totalmtmtxt.setText(StringStuff.commaDecorator(String.format("%.2f", totMTM)));

        if (totalmtmtxt.getText().toString().startsWith("-")) {
            totalmtmtxt.setTextColor(getResources().getColor(R.color.sellColor));
        } else {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                totalmtmtxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            } else {
                totalmtmtxt.setTextColor(getResources().getColor(R.color.buyColor));
            }
        }
    }

    private void calculatePostiveAndNegativeScripts() {
        hideProgress();
        double dayMtm = 0, totMTM = 0.0;
        int negQtyCount = 0;
        int posQtyCount = 0;
        double mtm;


        List<CustomNetPositionSummary> positionSummaries = positionList;

        for (CustomNetPositionSummary netPositionSummary : positionSummaries) {


            int NetQty;
            double NetPrice, todayMTM, NetAmt = 0.0;

            if (!netPositionSummary.getDayNetAmt().equalsIgnoreCase("")) {
                NetAmt = Double.parseDouble(netPositionSummary.getDayNetAmt());
            }
            double preAmount = Double.parseDouble(netPositionSummary.getpAmt()) + NetAmt;

            int PrevNetQty = Integer.parseInt(netPositionSummary.getPreNetQty()) + Integer.parseInt(netPositionSummary.getNetQty());

            if (PrevNetQty != 0) {

                double ltpClose = Double.parseDouble(netPositionSummary.getLtp()) > 0 ? Double.parseDouble(netPositionSummary.getLtp())
                        : Double.parseDouble(netPositionSummary.getClose()) > 0 ? Double.parseDouble(netPositionSummary.getClose()) : 0.0;
                double price = 0.0;

                if (getAssetType(netPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                    price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(netPositionSummary.getPrice_multiplier()) * Double.parseDouble(netPositionSummary.getLotQty()))));

                } else if (getAssetType(netPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                    price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(netPositionSummary.getMultiplier()))));
                } else {
                    price = Math.abs(preAmount / PrevNetQty);
                }

                if (price != 0) {
                    if (getAssetType(netPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                        mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(netPositionSummary.getPrice_multiplier())) * Integer.parseInt(netPositionSummary.getLotQty()));

                    } else {
                        mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(netPositionSummary.getMultiplier())));
                    }
                } else {
                    mtm = 0.0;
                }
            } else {

                mtm = preAmount * (-1);
            }


            if (mtm > 0.0) {
                posQtyCount = posQtyCount + 1;
            } else if (mtm < 0.0) {
                negQtyCount = negQtyCount + 1;
            }
        }

        if (totalmtmtxt.getText().toString().startsWith("-")) {
            totalmtmtxt.setTextColor(getResources().getColor(R.color.sellColor));
        } else {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                totalmtmtxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            } else {
                totalmtmtxt.setTextColor(getResources().getColor(R.color.buyColor));
            }
        }
        posScripCount.setText(posQtyCount + " SCRIP POSITIVE");
        negScripCount.setText(negQtyCount + " SCRIP NEGATIVE");
        int perTotal = (negQtyCount * 100) / positionSummaries.size();
        netscripstotal.setProgress(perTotal);

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

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

        private List<CustomNetPositionSummary> topGainerList;
        private List<CustomNetPositionSummary> filterList;
        private Double totMTM = 0.0;
        Context context;

        public MyListAdapter(List<CustomNetPositionSummary> orderBook, Context context) {
            this.context = context;
            this.topGainerList = orderBook;
            this.filterList = orderBook;

        }

        public void addData(CustomNetPositionSummary model) {
            topGainerList.add(model);
            totMTM = 0.0;
        }

        public List<CustomNetPositionSummary> getdata() {
            return topGainerList;
        }

        public void clear() {
            topGainerList.clear();
            filterList.clear();
            tokenList.clear();
        }

        @Override
        public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_positions_layout, parent, false);
            return new MyListAdapter.ViewHolder(listItem);
        }

        public void addSymbol(String symbol) {
            tokenList.add(symbol);
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public void updateData(int position, CustomNetPositionSummary model) {
            topGainerList.set(position, model);
            totMTM = 0.0;
        }

        public ArrayList<String> getSymbolTable() {
            return tokenList;
        }


        public CustomNetPositionSummary getFilterItem(int position) {
            return filterList.get(position);
        }

        public List<CustomNetPositionSummary> getData() {
            return topGainerList;
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        @Override
        public int getItemViewType(int position) {
            return Integer.parseInt(topGainerList.get(position).getToken());
        }
        @Override
        public void onBindViewHolder(MyListAdapter.ViewHolder holder, int position) {

            CustomNetPositionSummary customNetPositionSummary = topGainerList.get(position);

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.symbol_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_netQty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_cmp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_todaysQty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_avgPrice.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_mtm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_open.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_cmp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_Todaymtm.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }

            holder.symbol_name.setText(customNetPositionSummary.getDescription());


            if (getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("MCX") ||
                    getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("NCDEX")) {

                holder.txt_todaysQty.setText(String.valueOf(Integer.parseInt(customNetPositionSummary.getNetQty()))
                );
                holder.txt_open.setVisibility(View.VISIBLE);

                holder.txt_open.setText(String.valueOf(Integer.parseInt(customNetPositionSummary.getPreNetQty())));


                int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());


                holder.txt_netQty.setText(String.valueOf(totalQty));

            } else if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {

                holder.txt_todaysQty.setText(String.valueOf(Integer.parseInt(customNetPositionSummary.getNetQty())));

                holder.txt_open.setVisibility(View.VISIBLE);
                holder.txt_open.setText(String.valueOf(
                        Integer.parseInt(customNetPositionSummary.getPreNetQty())));

                int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());


                holder.txt_netQty.setText(String.valueOf(totalQty));

            } else {
                holder.txt_todaysQty.setText(customNetPositionSummary.getNetQty());
                holder.txt_open.setVisibility(View.VISIBLE);
                holder.txt_open.setText(customNetPositionSummary.getPreNetQty());

                int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());

                holder.txt_netQty.setText(String.valueOf(totalQty));

            }


            int NetQty;
            double NetPrice, todayMTM, mtm, NetAmt;

            NetAmt = Double.parseDouble(customNetPositionSummary.getDayNetAmt());
            NetQty = Integer.parseInt(customNetPositionSummary.getNetQty());

            if (NetQty != 0) {
                if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                    String netavg = String.format("%.4f", Double.parseDouble(customNetPositionSummary.getNetAvg()));
                    NetPrice = Double.parseDouble(netavg);
                } else {
                    String netavg = String.format("%.2f", Double.parseDouble(customNetPositionSummary.getNetAvg()));
                    NetPrice = Double.parseDouble(netavg);
                }

            } else {
                NetPrice = 0.0;
            }


            if (NetQty != 0) {

                double ltpClose = Double.parseDouble(customNetPositionSummary.getLtp()) > 0 ? Double.parseDouble(customNetPositionSummary.getLtp()) :
                        Double.parseDouble(customNetPositionSummary.getClose()) > 0 ? Double.parseDouble(customNetPositionSummary.getClose()) : 0.0;
                double price = NetPrice;

                if (price != 0) {

                    if (getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("MCX") ||
                            getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("NCDEX")) {

                        todayMTM = (ltpClose - price) * ((NetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier()) * Integer.parseInt(customNetPositionSummary.getLotQty())));

                    } else {
                        todayMTM = (ltpClose - price) * ((NetQty * Double.parseDouble(customNetPositionSummary.getMultiplier())));
                    }

                } else {
                    todayMTM = 0.0;
                }

            } else {

                if (NetAmt == 0.0) {
                    todayMTM = NetAmt;
                } else {
                    todayMTM = NetAmt * (-1);
                }
            }

            if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                holder.txt_Todaymtm.setText(StringStuff.commaINRDecorator(String.format("%.4f", todayMTM)));
            } else {
                holder.txt_Todaymtm.setText(StringStuff.commaDecorator(String.format("%.2f", todayMTM)));
            }

            customNetPositionSummary.setTodatMTM(String.format("%.2f", Double.parseDouble(String.valueOf(todayMTM))));

            double preAmount = Double.parseDouble(customNetPositionSummary.getpAmt()) + NetAmt;

            int PrevNetQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());

            if (PrevNetQty != 0) {

                double price = 0.0;

                double ltpClose = Double.parseDouble(customNetPositionSummary.getLtp()) > 0 ? Double.parseDouble(customNetPositionSummary.getLtp()) : Double.parseDouble(customNetPositionSummary.getClose()) > 0 ? Double.parseDouble(customNetPositionSummary.getClose()) : 0.0;
                if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                    if (PrevNetQty != 0) {
                        price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier()) *
                                Integer.parseInt(customNetPositionSummary.getLotQty()))));
                    } else {
                        price = 0.0;
                    }
                } else if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                    if (PrevNetQty != 0) {
                        price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.getMultiplier()))));
                    } else {
                        price = 0.0;
                    }
                } else {
                    price = Math.abs(preAmount / PrevNetQty);
                }

                if (Integer.parseInt(customNetPositionSummary.getPreNetQty()) != 0) {
                    if (price != 0) {
                        if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                            mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier()) *
                                    Integer.parseInt(customNetPositionSummary.getLotQty())));
                        } else {
                            mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(customNetPositionSummary.getMultiplier())));
                        }

                    } else {
                        mtm = 0.0;
                    }
                } else {
                    mtm = todayMTM;
                }
            } else {

                mtm = preAmount * (-1);
            }

            customNetPositionSummary.setMTM(String.format("%.2f", Double.parseDouble(String.valueOf(mtm))));


            if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {

                holder.txt_mtm.setText(StringStuff.commaINRDecorator(String.format("%.4f", mtm)));
                holder.txt_cmp.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getLtp()))));

                if (customNetPositionSummary.getNetQty().equalsIgnoreCase("0")) {
                    holder.txt_avgPrice.setText("0");
                } else {
                    holder.txt_avgPrice.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getNetAvg()))));
                }

            } else {
                holder.txt_mtm.setText(StringStuff.commaDecorator(String.format("%.2f", mtm)));
                holder.txt_cmp.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getLtp()))));

                if (customNetPositionSummary.getNetQty().equalsIgnoreCase("0")) {
                    holder.txt_avgPrice.setText("0");
                } else {
                    holder.txt_avgPrice.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getNetAvg()))));
                }
            }
            int  textColorPositive, textColorNegative;
            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {

                if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                    textColorPositive = R.color.whitetheambuyColor;
                }else {
                    textColorPositive = R.color.dark_green_positive;
                }
                textColorNegative = R.color.dark_red_negative;


            } else {

                if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                    textColorPositive = R.color.whitetheambuyColor;
                }else {
                    textColorPositive = R.color.dark_green_positive;
                }
                textColorNegative = R.color.dark_red_negative;
            }
            if (String.valueOf(mtm).startsWith("-")) {
                holder.txt_mtm.setTextColor(getResources().getColor(textColorNegative));

            } else if (String.valueOf(mtm).equals("0.0")){
                holder.txt_mtm.setTextColor(getResources().getColor(R.color.gray_border));
            }else {
                holder.txt_mtm.setTextColor(getResources().getColor(textColorPositive));
            }
            if (String.valueOf(todayMTM).startsWith("-")) {
                holder.txt_Todaymtm.setTextColor(getResources().getColor(textColorNegative));

            }else if (String.valueOf(todayMTM).equals("0.0") ){
                holder.txt_Todaymtm.setTextColor(getResources().getColor(R.color.gray_border));
            } else {
                holder.txt_Todaymtm.setTextColor(getResources().getColor(textColorPositive));
            }


        }

        public void setData(List<CustomNetPositionSummary> gainerList) {
            this.topGainerList = gainerList;
            this.filterList = gainerList;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return topGainerList.size();
        }

        public CustomNetPositionSummary getItem(int position) {
            if (getItemCount() > 0) return topGainerList.get(position);
            return null;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            GreekTextView symbol_name, txt_netQty, txt_todaysQty, txt_avgPrice, txt_Todaymtm, txt_mtm, txt_open, txt_cmp;
            LinearLayout parent_layout;


            public ViewHolder(View itemView) {
                super(itemView);
                parent_layout = itemView.findViewById(R.id.parent_layout);
                symbol_name = itemView.findViewById(R.id.txt_symbolName);
                txt_todaysQty = itemView.findViewById(R.id.txt_todaysQty);
                txt_netQty = itemView.findViewById(R.id.txt_netQty);
                txt_open = itemView.findViewById(R.id.txt_open);
                txt_avgPrice = itemView.findViewById(R.id.txt_avgPrice);
                txt_mtm = itemView.findViewById(R.id.txt_mtm);
                txt_Todaymtm = itemView.findViewById(R.id.txt_Todaymtm);
                txt_cmp = itemView.findViewById(R.id.txt_cmp);
            }
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

    public void onEventMainThread(ProductChangeResponse productChangeResponse) {
        try {
            GreekDialog.alertDialog(getActivity(), 0, GREEK, "\n Message : " + productChangeResponse.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    //refresh();
                    sendPTRRequest();
                }
            });

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_PORTFOLIO_POSITION_SCREEN;
        if(!EventBus.getDefault().isRegistered(getActivity())) {
            EventBus.getDefault().register(this);
        }
        if (streamingList != null && streamingList.size() > 0)
            streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);


    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        AccountDetails.currentFragment = NAV_TO_PORTFOLIO_POSITION_SCREEN;
        EventBus.getDefault().register(this);

        if (streamingList != null && streamingList.size() > 0)
            streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);

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
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        p.setTypeface(font);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
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
}

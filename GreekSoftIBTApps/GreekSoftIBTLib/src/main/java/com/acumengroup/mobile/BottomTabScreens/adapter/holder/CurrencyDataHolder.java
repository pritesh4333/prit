package com.acumengroup.mobile.BottomTabScreens.adapter.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.mobile.BottomTabScreens.ClickListener;
import com.acumengroup.mobile.BottomTabScreens.CurrencyTabFragment;
import com.acumengroup.mobile.BottomTabScreens.OverviewTabFragment;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.CurrencyMarketMoveAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.CurrencyRecycleChildAdapterSingle;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.CurrencyTopGainerAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.CurrencyTopLoserAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.OIAnalysisAdapter;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.textview.GreekTextView;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class CurrencyDataHolder extends ChildViewHolder {
    private final LinearLayoutManager layoutManager;
    GreekTextView symboltxt, exchangeTxt, ltpTxt, changeTxt;
    LinearLayout layout,childmain_layout;
    AppCompatActivity activity;
    ArrayList<GainerData> gainerData;
    CurrencyTopGainerAdapter topgaineAdapter;
    CurrencyTopLoserAdapter topLoserAdapter;
    CurrencyMarketMoveAdapter marketMoveAdapter;
    OIAnalysisAdapter oiAnalysisAdapter;
    RecyclerView recyclerView;
    StreamingController streamController;
    GreekTextView noData,txt_header;
    ArrayList<String> streamingList, streamingloserList, streamingmoverList;
    private LinearLayout  linear_header;
    private CurrencyRecycleChildAdapterSingle childAdapter;
    private ArrayList<String>  OIAnalysysTokenList;
    private static ArrayList<String> tempOIAnaluysis = new ArrayList<>();

    public CurrencyDataHolder(View itemView) {
        super(itemView);
        symboltxt = itemView.findViewById(R.id.symbolname_text);
        exchangeTxt = itemView.findViewById(R.id.descriptionname_text);
        ltpTxt = itemView.findViewById(R.id.ltp_text);
        changeTxt = itemView.findViewById(R.id.change_text);
        layout = itemView.findViewById(R.id.child_layout);
        recyclerView = itemView.findViewById(R.id.overview_child_list);
        noData = itemView.findViewById(R.id.txt_nodata);
        txt_header = itemView.findViewById(R.id.txt_header);
        linear_header = itemView.findViewById(R.id.linear_header);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        OIAnalysysTokenList = new ArrayList<>();

        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(activity, recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                if (gainerData != null) {
                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, gainerData.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, gainerData.get(position).getExchange());
                    args2.putString(TradeFragment.TOKEN, gainerData.get(position).getToken());
                    args2.putString(TradeFragment.TICK_SIZE, gainerData.get(position).getTicksize());
                    args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }


        }));


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
                int position = viewHolder.getAdapterPosition();


                if (direction == ItemTouchHelper.LEFT) {


                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, gainerData.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, gainerData.get(position).getExchange());
                    args2.putString(TradeFragment.TOKEN, gainerData.get(position).getToken());
                    args2.putString(TradeFragment.TICK_SIZE, gainerData.get(position).getTicksize());
                      /* args2.putString(TradeFragment.ASSET_TYPE, gainerData.get(position).getAssetType());
                 args2.putString(TradeFragment.UNIQUEID, "");
                    args2.putString(TradeFragment.TRADE_SYMBOL, gainerData.get(position).getName());
                    args2.putString(TradeFragment.LOT_QUANTITY, gainerData.get(position).getLotSize());
                    args2.putString(TradeFragment.TICK_SIZE, gainerData.get(position).getTicksize());
                    args2.putString(TradeFragment.MULTIPLIER, gainerData.get(position).getMultipliyer());*/
                    args2.putString(TradeFragment.TRADE_ACTION, "Sell");
                   /* args2.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp(gainerData.get(position).getExpiry(), "dd MMM yyyy", "bse"));
                    args2.putString(TradeFragment.STRICKPRICE, gainerData.get(position).getStrkeprice());
                    args2.putString("OptType", gainerData.get(position).getOptType());*/

                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");


                    if (topgaineAdapter != null) {
                        topgaineAdapter.notifyDataSetChanged();
                    }

                    if (topLoserAdapter != null) {
                        topLoserAdapter.notifyDataSetChanged();
                    }

                    if (marketMoveAdapter != null) {
                        marketMoveAdapter.notifyDataSetChanged();
                    }

                    if (oiAnalysisAdapter != null) {
                        oiAnalysisAdapter.notifyDataSetChanged();
                    }

                    if (childAdapter != null) {
                        childAdapter.notifyDataSetChanged();
                    }

                } else {

//                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, gainerData.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, gainerData.get(position).getExchange());
                    args2.putString(TradeFragment.TOKEN, gainerData.get(position).getToken());
                    args2.putString(TradeFragment.TICK_SIZE, gainerData.get(position).getTicksize());
                    /*args2.putString(TradeFragment.ASSET_TYPE, gainerData.get(position).getAssetType());
                    args2.putString(TradeFragment.UNIQUEID, "");
                    args2.putString(TradeFragment.TRADE_SYMBOL, gainerData.get(position).getName());
                    args2.putString(TradeFragment.LOT_QUANTITY, gainerData.get(position).getLotSize());
                    args2.putString(TradeFragment.TICK_SIZE, gainerData.get(position).getTicksize());
                    args2.putString(TradeFragment.MULTIPLIER, gainerData.get(position).getMultipliyer());*/
                    args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                    /*args2.putString("Expiry", DateTimeFormatter.getDateFromTimeStamp(gainerData.get(position).getExpiry(), "dd MMM yyyy", "bse"));
                    args2.putString(TradeFragment.STRICKPRICE, gainerData.get(position).getStrkeprice());
                    args2.putString("OptType", gainerData.get(position).getOptType());
*/
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");


                }

                if (topgaineAdapter != null) {
                    topgaineAdapter.notifyDataSetChanged();
                }

                if (topLoserAdapter != null) {
                    topLoserAdapter.notifyDataSetChanged();
                }

                if (marketMoveAdapter != null) {
                    marketMoveAdapter.notifyDataSetChanged();
                }

                if (oiAnalysisAdapter != null) {
                    oiAnalysisAdapter.notifyDataSetChanged();
                }

                if (childAdapter != null) {
                    childAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

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
                        }else {
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
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }


public void onBind(ArrayList<GainerData> gainerData, String header,
                   ExpandableGroup group, AppCompatActivity activity) {
    this.gainerData = gainerData;
    this.activity = activity;
    streamController = new StreamingController();
    if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")){
        recyclerView.setBackgroundColor(activity.getColor(R.color.white));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity));
        noData.setTextColor(activity.getResources().getColor(AccountDetails.textColorDropdown));
    }


    if (group.getTitle().equalsIgnoreCase(OverviewTabFragment.TOP_GAINERS)) {
        linear_header.setVisibility(View.GONE);
        if (gainerData.size() > 1) {
            streamingList = new ArrayList<>();
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);


            topgaineAdapter = new CurrencyTopGainerAdapter(gainerData, activity);
            recyclerView.setAdapter(topgaineAdapter);
            topgaineAdapter.notifyDataSetChanged();
            for (int i = 0; i < gainerData.size(); i++) {

                topgaineAdapter.addToken(gainerData.get(i).getToken());
                streamingList.add(gainerData.get(i).getToken());

            }

            sendStreamingRequest();

        } else {
            if (gainerData.size()!=0) {
                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {

                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    streamingList = new ArrayList<>();
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);


                    topgaineAdapter = new CurrencyTopGainerAdapter(gainerData, activity);
                    recyclerView.setAdapter(topgaineAdapter);
                    topgaineAdapter.notifyDataSetChanged();
                    for (int i = 0; i < gainerData.size(); i++) {

                        topgaineAdapter.addToken(gainerData.get(i).getToken());
                        streamingList.add(gainerData.get(i).getToken());

                    }

                    sendStreamingRequest();
                }
            }else{
                noData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    if (group.getTitle().equalsIgnoreCase(OverviewTabFragment.TOP_LOSER)) {
        linear_header.setVisibility(View.GONE);

        if (gainerData.size() > 1) {
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            streamingloserList = new ArrayList<>();
            topLoserAdapter = new CurrencyTopLoserAdapter(gainerData, activity);
            recyclerView.setAdapter(topLoserAdapter);
            topLoserAdapter.notifyDataSetChanged();

            for (int i = 0; i < gainerData.size(); i++) {

                topLoserAdapter.addToken(gainerData.get(i).getToken());
                streamingloserList.add(gainerData.get(i).getToken());
            }

            sendStreamingLosersRequest();

        } else {
            if (gainerData.size()!=0) {
                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    streamingloserList = new ArrayList<>();
                    topLoserAdapter = new CurrencyTopLoserAdapter(gainerData, activity);
                    recyclerView.setAdapter(topLoserAdapter);
                    topLoserAdapter.notifyDataSetChanged();

                    for (int i = 0; i < gainerData.size(); i++) {

                        topLoserAdapter.addToken(gainerData.get(i).getToken());
                        streamingloserList.add(gainerData.get(i).getToken());
                    }

                    sendStreamingLosersRequest();
                }
            }else{
                noData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    if (group.getTitle().equalsIgnoreCase(OverviewTabFragment.MARKET_MOVERS)) {
        linear_header.setVisibility(View.GONE);
        if (gainerData.size() > 1) {
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            streamingmoverList = new ArrayList<>();
            marketMoveAdapter = new CurrencyMarketMoveAdapter(gainerData, activity);
            recyclerView.setAdapter(marketMoveAdapter);
            marketMoveAdapter.notifyDataSetChanged();

            for (int i = 0; i < gainerData.size(); i++) {

                marketMoveAdapter.addToken(gainerData.get(i).getToken());
                streamingmoverList.add(gainerData.get(i).getToken());
            }

            sendStreamingMoversRequest();
        } else {

            if (gainerData.size()!=0) {
                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    streamingmoverList = new ArrayList<>();
                    marketMoveAdapter = new CurrencyMarketMoveAdapter(gainerData, activity);
                    recyclerView.setAdapter(marketMoveAdapter);
                    marketMoveAdapter.notifyDataSetChanged();

                    for (int i = 0; i < gainerData.size(); i++) {

                        marketMoveAdapter.addToken(gainerData.get(i).getToken());
                        streamingmoverList.add(gainerData.get(i).getToken());
                    }

                    sendStreamingMoversRequest();
                }
            }else{
                noData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    if (group.getTitle().equalsIgnoreCase(CurrencyTabFragment.OPEN_INTEREST_ANALYSIS)) {
        linear_header.setVisibility(View.VISIBLE);
        txt_header.setText(header);

        if (gainerData.size() <= 1 && gainerData.get(0).getDescription().length() <= 0 &&
                header.equalsIgnoreCase("LONG BUILD UP(OI UP, PRICE UP)")) {
            toggleErrorLayout(true);
        } else if (gainerData.size() <= 1 && gainerData.get(0).getDescription().length()
                <= 0 && header.equalsIgnoreCase("SHORT BUILD UP(OI UP, PRICE DOWN)")) {
            toggleErrorLayout(true);
        } else if (gainerData.size() <= 1 && gainerData.get(0).getDescription().length()
                <= 0 && header.equalsIgnoreCase("LONG UNWINDING(OI DOWN, PRICE DOWN)")) {
            toggleErrorLayout(true);
        } else if (gainerData.size() <= 1 && gainerData.get(0).getDescription().length()
                <= 0 && header.equalsIgnoreCase("SHORT COVERING (OI DOWN, PRICE UP)")) {
            toggleErrorLayout(true);
        } else {

            toggleErrorLayout(false);
            childAdapter = new CurrencyRecycleChildAdapterSingle(gainerData, activity, group.getTitle(), header, 0);
            recyclerView.setAdapter(childAdapter);
            childAdapter.notifyDataSetChanged();


            for (int i = 0; gainerData.size() > i; i++) {
                OIAnalysysTokenList.add(gainerData.get(i).getToken());
                tempOIAnaluysis.add(gainerData.get(i).getToken());
            }

            sendOIAnalysisSubscribe();


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


    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 40;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }


    private void sendStreamingRequest() {

        if (streamingList != null) {
            if (streamingList.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", streamingList);
                streamController.sendStreamingRequest(activity, streamingList, "ltpinfo", null, null, false);

            }
        }
    }

    private void sendStreamingLosersRequest() {

        if (streamingloserList != null) {
            if (streamingloserList.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", streamingloserList);
                streamController.sendStreamingRequest(activity, streamingloserList, "ltpinfo", null, null, false);

            }
        }
    }


    private void sendStreamingMoversRequest() {

        if (streamingmoverList != null) {
            if (streamingmoverList.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", streamingmoverList);
                streamController.sendStreamingRequest(activity, streamingmoverList, "ltpinfo", null, null, false);

            }
        }
    }


    public void onEventMainThread(String flag) {
        try {
            if (flag.equalsIgnoreCase("Gainersclose")) {
                if (streamingList.size() > 0) {
                    streamController.pauseStreaming(activity, "ltpinfo", streamingList);
                }

            }
            if (flag.equalsIgnoreCase("Losersclose")) {
                if (streamingloserList.size() > 0) {
                    streamController.pauseStreaming(activity, "ltpinfo", streamingloserList);
                }
            }
            if (flag.equalsIgnoreCase("Marketclose")) {
                if (streamingmoverList.size() > 0) {
                    streamController.pauseStreaming(activity, "ltpinfo", streamingmoverList);
                }

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void toggleErrorLayout(boolean show) {

        if (show) {
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }
}

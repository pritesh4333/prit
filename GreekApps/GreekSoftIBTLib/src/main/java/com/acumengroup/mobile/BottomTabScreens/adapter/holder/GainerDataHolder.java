package com.acumengroup.mobile.BottomTabScreens.adapter.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.mobile.BottomTabScreens.ClickListener;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData_v2;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.MarketMoveAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.MostActiveValueAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.MostActiveVolumeAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.OIAnalysisAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.TopGainerAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.TopLoserAdapter;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.textview.GreekTextView;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


import static com.acumengroup.mobile.BottomTabScreens.CurrencyTabFragment.OPEN_INTEREST_ANALYSIS;
import static com.acumengroup.mobile.BottomTabScreens.OverviewTabFragment.MARKET_MOVERS;
import static com.acumengroup.mobile.BottomTabScreens.OverviewTabFragment.MOST_ACTIVE_BY_VALUE;
import static com.acumengroup.mobile.BottomTabScreens.OverviewTabFragment.MOST_ACTIVE_BY_VOLUME;
import static com.acumengroup.mobile.BottomTabScreens.OverviewTabFragment.TOP_GAINERS;
import static com.acumengroup.mobile.BottomTabScreens.OverviewTabFragment.TOP_LOSER;

public class GainerDataHolder extends ChildViewHolder {
    private final LinearLayoutManager layoutManager;
    GreekTextView symboltxt, exchangeTxt, ltpTxt, changeTxt;
    LinearLayout layout;
    AppCompatActivity activity;
    ArrayList<GainerData_v2> gainerData;
    TopGainerAdapter topgaineAdapter;
    TopLoserAdapter topLoserAdapter;
    MarketMoveAdapter marketMoveAdapter;
    MostActiveVolumeAdapter mostActiveVolumeAdapter;
    MostActiveValueAdapter mostActiveValueAdapter;
    OIAnalysisAdapter oiAnalysisAdapter;
    RecyclerView recyclerView;
    StreamingController streamController;
    GreekTextView noData;
    ArrayList<String> streamingList, streamingloserList, streamingmoverList,streamingvolumeList,streamingvalueList;

    public GainerDataHolder(View itemView) {
        super(itemView);
        symboltxt = itemView.findViewById(R.id.symbolname_text);
        exchangeTxt = itemView.findViewById(R.id.descriptionname_text);
        ltpTxt = itemView.findViewById(R.id.ltp_text);
        changeTxt = itemView.findViewById(R.id.change_text);
        layout = itemView.findViewById(R.id.child_layout);
        recyclerView = itemView.findViewById(R.id.overview_child_list);
        noData = itemView.findViewById(R.id.txt_nodata);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);


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
                    args2.putString(TradeFragment.TRADE_ACTION, "Sell");
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
                    if (mostActiveVolumeAdapter != null) {
                        mostActiveVolumeAdapter.notifyDataSetChanged();
                    }
                    if (mostActiveValueAdapter != null) {
                        mostActiveValueAdapter.notifyDataSetChanged();
                    }
                } else {
                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, gainerData.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, gainerData.get(position).getExchange());
                    args2.putString(TradeFragment.TOKEN, gainerData.get(position).getToken());
                    args2.putString(TradeFragment.TICK_SIZE, gainerData.get(position).getTicksize());
                    args2.putString(TradeFragment.TRADE_ACTION, "Buy");
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
                if (mostActiveVolumeAdapter != null) {
                    mostActiveVolumeAdapter.notifyDataSetChanged();
                }
                if (mostActiveValueAdapter != null) {
                    mostActiveValueAdapter.notifyDataSetChanged();
                }

                if (oiAnalysisAdapter != null) {
                    oiAnalysisAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;

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


    public void onBind(ArrayList<GainerData_v2> gainerData, ExpandableGroup group, AppCompatActivity activity) {
        this.gainerData = gainerData;
        this.activity = activity;
        streamController = new StreamingController();

        if(AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")) {
            recyclerView.setBackgroundColor(activity.getColor(R.color.white));
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity));
            noData.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
        }

        if (group.getTitle().equalsIgnoreCase(TOP_GAINERS)) {
            if (gainerData.size() > 1) {
                streamingList = new ArrayList<>();
                recyclerView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);


                topgaineAdapter = new TopGainerAdapter(gainerData, activity);
                recyclerView.setAdapter(topgaineAdapter);
                topgaineAdapter.notifyDataSetChanged();
                for (int i = 0; i < gainerData.size(); i++) {
                    topgaineAdapter.addToken(gainerData.get(i).getToken());
                    streamingList.add(gainerData.get(i).getToken());
                }

                sendStreamingRequest();

            } else {
                if (gainerData.size() != 0){
                    if (gainerData.get(0).getChange().equalsIgnoreCase("")) {

                        noData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        streamingList = new ArrayList<>();
                        recyclerView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);


                        topgaineAdapter = new TopGainerAdapter(gainerData, activity);
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

        if (group.getTitle().equalsIgnoreCase(TOP_LOSER)) {
            if (gainerData.size() > 1) {
                recyclerView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                streamingloserList = new ArrayList<>();
                topLoserAdapter = new TopLoserAdapter(gainerData, activity);
                recyclerView.setAdapter(topLoserAdapter);
                topLoserAdapter.notifyDataSetChanged();

                for (int i = 0; i < gainerData.size(); i++) {

                    topLoserAdapter.addToken(gainerData.get(i).getToken());
                    streamingloserList.add(gainerData.get(i).getToken());
                }

                sendStreamingLosersRequest();

            } else {
                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    streamingloserList = new ArrayList<>();
                    topLoserAdapter = new TopLoserAdapter(gainerData, activity);
                    recyclerView.setAdapter(topLoserAdapter);
                    topLoserAdapter.notifyDataSetChanged();

                    for (int i = 0; i < gainerData.size(); i++) {

                        topLoserAdapter.addToken(gainerData.get(i).getToken());
                        streamingloserList.add(gainerData.get(i).getToken());
                    }

                    sendStreamingLosersRequest();
                }
            }
        }

        if (group.getTitle().equalsIgnoreCase(MARKET_MOVERS)) {
            if (gainerData.size() > 1) {
                recyclerView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                streamingmoverList = new ArrayList<>();
                marketMoveAdapter = new MarketMoveAdapter(gainerData, activity);
                recyclerView.setAdapter(marketMoveAdapter);
                marketMoveAdapter.notifyDataSetChanged();

                for (int i = 0; i < gainerData.size(); i++) {

                    marketMoveAdapter.addToken(gainerData.get(i).getToken());
                    streamingmoverList.add(gainerData.get(i).getToken());
                }

                sendStreamingMoversRequest();
            } else {


                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    streamingmoverList = new ArrayList<>();
                    marketMoveAdapter = new MarketMoveAdapter(gainerData, activity);
                    recyclerView.setAdapter(marketMoveAdapter);
                    marketMoveAdapter.notifyDataSetChanged();

                    for (int i = 0; i < gainerData.size(); i++) {

                        marketMoveAdapter.addToken(gainerData.get(i).getToken());
                        streamingmoverList.add(gainerData.get(i).getToken());
                    }

                    sendStreamingMoversRequest();
                }

            }
        }
        if (group.getTitle().equalsIgnoreCase(MOST_ACTIVE_BY_VOLUME)) {
            if (gainerData.size() > 1) {
                recyclerView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                streamingvolumeList = new ArrayList<>();
                mostActiveVolumeAdapter = new MostActiveVolumeAdapter(gainerData, activity);
                recyclerView.setAdapter(mostActiveVolumeAdapter);
                mostActiveVolumeAdapter.notifyDataSetChanged();

                for (int i = 0; i < gainerData.size(); i++) {

                    mostActiveVolumeAdapter.addToken(gainerData.get(i).getToken());
                    streamingvolumeList.add(gainerData.get(i).getToken());
                }

                sendStreamingMostActiveVolumeRequest();
            } else {


                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    streamingvolumeList = new ArrayList<>();
                    mostActiveVolumeAdapter = new MostActiveVolumeAdapter(gainerData, activity);
                    recyclerView.setAdapter(mostActiveVolumeAdapter);
                    mostActiveVolumeAdapter.notifyDataSetChanged();

                    for (int i = 0; i < gainerData.size(); i++) {
                        mostActiveVolumeAdapter.addToken(gainerData.get(i).getToken());
                        streamingvolumeList.add(gainerData.get(i).getToken());
                    }

                    sendStreamingMostActiveVolumeRequest();
                }

            }
        }
        if (group.getTitle().equalsIgnoreCase(MOST_ACTIVE_BY_VALUE)) {

            if (gainerData.size() > 1) {
                recyclerView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                streamingvalueList = new ArrayList<>();
                mostActiveValueAdapter = new MostActiveValueAdapter(gainerData, activity);
                recyclerView.setAdapter(mostActiveValueAdapter);
                mostActiveValueAdapter.notifyDataSetChanged();

                for (int i = 0; i < gainerData.size(); i++) {
                    mostActiveValueAdapter.addToken(gainerData.get(i).getToken());
                    streamingvalueList.add(gainerData.get(i).getToken());
                }
                sendStreamingMostActiveValueRequest();
            } else {

                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    streamingvalueList = new ArrayList<>();
                    mostActiveValueAdapter = new MostActiveValueAdapter(gainerData, activity);
                    recyclerView.setAdapter(mostActiveValueAdapter);
                    mostActiveValueAdapter.notifyDataSetChanged();

                    for (int i = 0; i < gainerData.size(); i++) {
                        mostActiveValueAdapter.addToken(gainerData.get(i).getToken());
                        streamingvalueList.add(gainerData.get(i).getToken());
                    }

                    sendStreamingMostActiveValueRequest();
                }

            }
        }


        if (group.getTitle().equalsIgnoreCase(OPEN_INTEREST_ANALYSIS)) {
            if (gainerData.size() > 1) {
                recyclerView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                streamingmoverList = new ArrayList<>();
                oiAnalysisAdapter = new OIAnalysisAdapter(gainerData, activity);
                recyclerView.setAdapter(oiAnalysisAdapter);
                oiAnalysisAdapter.notifyDataSetChanged();

                for (int i = 0; i < gainerData.size(); i++) {

                    oiAnalysisAdapter.addToken(gainerData.get(i).getToken());
                    streamingmoverList.add(gainerData.get(i).getToken());
                }

                sendStreamingMoversRequest();
            } else {


                if (gainerData.get(0).getChange().equalsIgnoreCase("")) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    streamingmoverList = new ArrayList<>();
                    oiAnalysisAdapter = new OIAnalysisAdapter(gainerData, activity);
                    recyclerView.setAdapter(oiAnalysisAdapter);
                    oiAnalysisAdapter.notifyDataSetChanged();

                    for (int i = 0; i < gainerData.size(); i++) {

                        oiAnalysisAdapter.addToken(gainerData.get(i).getToken());
                        streamingmoverList.add(gainerData.get(i).getToken());
                    }

                    sendStreamingMoversRequest();
                }

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
    private void sendStreamingMostActiveVolumeRequest() {

        if (streamingvolumeList != null) {
            if (streamingvolumeList.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", streamingvolumeList);
                streamController.sendStreamingRequest(activity, streamingvolumeList, "ltpinfo", null, null, false);

            }
        }
    }
    private void sendStreamingMostActiveValueRequest() {

        if (streamingvalueList != null) {
            if (streamingvalueList.size() > 0) {
                streamController.pauseStreaming(activity, "ltpinfo", streamingvalueList);
                streamController.sendStreamingRequest(activity, streamingvalueList, "ltpinfo", null, null, false);

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

            }if (flag.equalsIgnoreCase("MarketByValueclose")) {
                if (streamingvalueList.size() > 0) {
                    streamController.pauseStreaming(activity, "ltpinfo", streamingvalueList);
                }

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


}

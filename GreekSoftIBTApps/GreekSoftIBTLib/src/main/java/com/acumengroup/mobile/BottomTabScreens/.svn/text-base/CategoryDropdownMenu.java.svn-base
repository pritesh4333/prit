package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindicesstock.IndicesStock;
import com.acumengroup.greekmain.core.model.marketsindicesstock.MarketsIndicesStockResponse;
import com.acumengroup.greekmain.core.model.searchmultistockdetails.StockList;
import com.acumengroup.mobile.BottomTabScreens.adapter.CategoryDropdownAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData_v2;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MarketDataResponse;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by Admin2 on 10/Jul/2018.
 */

public class CategoryDropdownMenu extends PopupWindow {
    private final StreamingController streamController;
    private Context context;
    private RecyclerView rvCategory;
    private GreekTextView ttx_title, txt_nodata;
    private ImageView sort_btn;
    private CategoryDropdownAdapter dropdownAdapter;
    private boolean isAscending = false;
    private MarketDataResponse marketResponse;
    private ArrayList streamingList;
    private ArrayList<GainerData_v2> glist = new ArrayList<>();
    private ArrayList<String> glistToken = new ArrayList<>();
    String title;
    public List<IndicesStock> indicesStock;
    private MarketsIndicesStockResponse indicesStockResponse;
    private ArrayList<StockList> stockList;
    private final ArrayList<String> indicesNameList = new ArrayList<>();

    public CategoryDropdownMenu(Context context, String title, List<IndicesStock> indicesStock) {
        super(context);
        this.context = context;
        this.title = title;
        this.indicesStock = indicesStock;

        streamController = new StreamingController();
        setupView();
    }


    public static boolean hasNavBar(Resources resources) {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }

    private void setupView() {
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
        isAscending = false;

        View view = LayoutInflater.from(context).inflate(R.layout.popup_category, null);

        rvCategory = view.findViewById(R.id.rvCategory);
        sort_btn = view.findViewById(R.id.sort_btn);
        ttx_title = view.findViewById(R.id.indices_title);
        txt_nodata = view.findViewById(R.id.txt_nodata);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvCategory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        ((SimpleItemAnimator) rvCategory.getItemAnimator()).setSupportsChangeAnimations(false);
        ttx_title.setText(title);
        streamingList = new ArrayList<>();
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sorting();
            }
        });
        if (indicesStock.size() > 0) {
            txt_nodata.setVisibility(View.GONE);
            rvCategory.setVisibility(View.VISIBLE);
            Sorting();
        } else {
            rvCategory.setVisibility(View.GONE);
            txt_nodata.setVisibility(View.VISIBLE);
        }

        rvCategory.addOnItemTouchListener(new RecyclerTouchListener(context, rvCategory, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                if (indicesStock != null) {
                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, indicesStock.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, getExchangeFromToken(indicesStock.get(position).getToken()));
                    args2.putString(TradeFragment.TOKEN, indicesStock.get(position).getToken());
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
                    args2.putString(TradeFragment.SCRIP_NAME, indicesStock.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, getExchangeFromToken(indicesStock.get(position).getToken()));
                    args2.putString(TradeFragment.TOKEN, indicesStock.get(position).getToken());
                    args2.putString(TradeFragment.TRADE_ACTION, "Sell");
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");


                    if (dropdownAdapter != null) {
                        dropdownAdapter.notifyDataSetChanged();
                    }
                } else {
                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, indicesStock.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, getExchangeFromToken(indicesStock.get(position).getToken()));
                    args2.putString(TradeFragment.TOKEN, indicesStock.get(position).getToken());
                    args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");
                }

                if (dropdownAdapter != null) {
                    dropdownAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;

                    if (dX > 0) {
                        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (300 - 20), itemView.getBottom());
                        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                            p.setColor(context.getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            p.setColor(context.getResources().getColor(R.color.buyColor));
                        }
                        c.drawRoundRect(leftButton, 0, 0, p);
                        drawText("Buy", c, leftButton, p);
                    } else if (dX < 0) {

                        RectF rightButton = new RectF(itemView.getRight() - (300 - 20), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        p.setColor(context.getResources().getColor(R.color.sellColor));
                        c.drawRoundRect(rightButton, 0, 0, p);
                        drawText("Sell", c, rightButton, p);


                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvCategory);

        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            rvCategory.setBackgroundColor(context.getColor(R.color.white));
            txt_nodata.setTextColor(context.getColor(AccountDetails.textColorDropdown));
            txt_nodata.setBackgroundColor(context.getColor(R.color.white));
        }

        setContentView(view);
    }

    private void Sorting() {
        isAscending = !isAscending;
        glistToken.clear();
        if (!isAscending) {
            sort_btn.setImageResource(R.drawable.ic_baseline_sort_24);
        } else {
            sort_btn.setImageResource(R.drawable.ic_baseline_sort_24);
        }
        Collections.sort(indicesStock, new Comparator<IndicesStock>() {
            public int compare(IndicesStock obj1, IndicesStock obj2) {

                if (!isAscending) {
                    // ## Ascending order

                    return Double.valueOf(obj1.getP_change()).compareTo(Double.valueOf(obj2.getP_change())); // To compare string values
                    // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values
                } else {
//                            return obj2.getChange().compareToIgnoreCase(obj1.getChange()); // To compare string values
                    return Double.valueOf(obj2.getP_change()).compareTo(Double.valueOf(obj1.getP_change()));
                    // ## Descending order
                    // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                    // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values

                }
            }
        });


        dropdownAdapter = new CategoryDropdownAdapter(indicesStock, context);
        rvCategory.setAdapter(dropdownAdapter);
        for (int i = 0; i < indicesStock.size(); i++) {
            glistToken.add(indicesStock.get(i).getToken());
            dropdownAdapter.addToken(indicesStock.get(i).getToken());
        }
        sendStreamingRequest();
        dropdownAdapter.notifyDataSetChanged();
    }

    private void sendStreamingRequest() {

        if (glistToken != null) {
            if (glistToken.size() > 0) {
                streamController.pauseStreaming(context, "ltpinfo", glistToken);
                streamController.sendStreamingRequest(context, glistToken, "ltpinfo", null, null, false);

            }
        }
    }


    private String getExchangeFromToken(String token) {
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


    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 40;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
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
}

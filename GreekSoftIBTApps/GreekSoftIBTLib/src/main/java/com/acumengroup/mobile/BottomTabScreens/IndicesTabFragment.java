package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.marketsindicesstock.IndicesStock;
import com.acumengroup.greekmain.core.model.marketsindicesstock.MarketsIndicesStockRequest;
import com.acumengroup.greekmain.core.model.marketsindicesstock.MarketsIndicesStockResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.CategoryDropdownAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData_v2;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.watchlistModel;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class IndicesTabFragment extends GreekBaseFragment {

    private StreamingController streamingController = new StreamingController();
    private MarketsIndianIndicesResponse indianIndicesResponse;
    private Typeface font;
    private IndicesListAdapter indicesListAdapter;
    private ArrayList<String> tokenList;
    private MarketsIndicesStockResponse indicesStockResponse;
    private List<IndicesStock> indicesStocks = new ArrayList<IndicesStock>();

    public IndicesTabFragment() {
    }

    Spinner exchangeSpinner, indicesSpinner;
    RecyclerView indicesstockdetailrecycler;
    private ArrayList<String> exchangeList = new ArrayList<>();
    private ArrayList<String> indicesStockList = new ArrayList<>();
    private ArrayAdapter<String> indicesStockAdapter;
    LinearLayout column_layout, spinner_layout;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        ArrayAdapter<String> exchangeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);

                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
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
        exchangeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(exchangeAdapter);
        exchangeSpinner.setSelection(0);
        exchangeSpinner.setOnItemSelectedListener(exchangeselected);


            indicesStockAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), indicesStockList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        v.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        v.setTextColor(getResources().getColor(R.color.white));
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
            indicesStockAdapter.setDropDownViewResource(R.layout.custom_spinner);
            indicesSpinner.setAdapter(indicesStockAdapter);
            indicesSpinner.setSelection(0);
            indicesSpinner.setOnItemSelectedListener(stockselected);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View indicesview = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_indices_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_indices_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        setupview(indicesview);
        sendIndianIndicesRequest();
        return indicesview;
    }

    @Override
    public void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    private void setupview(View indicesview) {

        font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        indicesstockdetailrecycler = indicesview.findViewById(R.id.stocdetail_indices_recyclerview);
        indicesstockdetailrecycler.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        ((SimpleItemAnimator) indicesstockdetailrecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        exchangeSpinner = indicesview.findViewById(R.id.exchange_spinner);
        indicesSpinner = indicesview.findViewById(R.id.indices_spinner);
        column_layout = indicesview.findViewById(R.id.column_layout);
        spinner_layout = indicesview.findViewById(R.id.spinner_layout);

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            column_layout.setBackgroundColor(getResources().getColor(R.color.selectColor));
        }


        indicesstockdetailrecycler.addOnItemTouchListener(new RecyclerTouchListener(getContext(), indicesstockdetailrecycler, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                if (indicesStocks != null) {
                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, indicesStocks.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, getExchangeFromToken(indicesStocks.get(position).getToken()));
                    args2.putString(TradeFragment.TOKEN, indicesStocks.get(position).getToken());
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
                    args2.putString(TradeFragment.SCRIP_NAME, indicesStocks.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, getExchangeFromToken(indicesStocks.get(position).getToken()));
                    args2.putString(TradeFragment.TOKEN, indicesStocks.get(position).getToken());
                    args2.putString(TradeFragment.TRADE_ACTION, "Sell");
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");

                    if (indicesListAdapter != null) {
                        indicesListAdapter.notifyDataSetChanged();
                    }

                } else {
                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, indicesStocks.get(position).getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, getExchangeFromToken(indicesStocks.get(position).getToken()));
                    args2.putString(TradeFragment.TOKEN, indicesStocks.get(position).getToken());
                    args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");
                }

                if (indicesListAdapter != null) {
                    indicesListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;

                    if (dX > 0) {
                        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (300 - 20), itemView.getBottom());
                        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                            p.setColor(getMainActivity().getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            p.setColor(getMainActivity().getResources().getColor(R.color.buyColor));
                        }
                        c.drawRoundRect(leftButton, 0, 0, p);
                        drawText("Buy", c, leftButton, p);
                    } else if (dX < 0) {

                        RectF rightButton = new RectF(itemView.getRight() - (300 - 20), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        p.setColor(getMainActivity().getResources().getColor(R.color.sellColor));
                        c.drawRoundRect(rightButton, 0, 0, p);
                        drawText("Sell", c, rightButton, p);


                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(indicesstockdetailrecycler);

    }

    private void sendIndianIndicesRequest() {
        streamingController.sendIndianIndicesRequest(getMainActivity(), serviceResponseHandler); //TODO PK
    }

    @Override
    public void handleResponse(Object response) {
        hideProgress();
        JSONResponse jsonResponse = (JSONResponse) response;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_STOCKS_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                hideAppTitle();
                indicesStockResponse = (MarketsIndicesStockResponse) jsonResponse.getResponse();
                if (indicesStockResponse.getErrorCode().equals("3")) {
                    hideProgress();
                } else {
                    handleIndicesResponse();
                    // sendMultiStockRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                handleindices(indianIndicesResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleIndicesResponse() {
        if (indicesStocks != null) {
            indicesStocks.clear();
        }
        indicesStocks = indicesStockResponse.getIndicesStock();
        tokenList = new ArrayList<>();
        indicesListAdapter = new IndicesListAdapter(indicesStocks, getMainActivity());
        for (int i = 0; i < indicesStocks.size(); i++) {
            tokenList.add(indicesStocks.get(i).getToken());
        }

        streamController.sendStreamingRequest(getMainActivity(), tokenList, "ltpinfo", null, null, false); //PK TODO
        addToStreamingList("ltpinfo", tokenList);


        indicesstockdetailrecycler.setAdapter(indicesListAdapter);
        indicesListAdapter.notifyDataSetChanged();
    }


    private void handleindices(MarketsIndianIndicesResponse indianIndicesResponse) {
        List<IndianIndice> indices = new ArrayList<>();
        indices = indianIndicesResponse.getIndianIndices();
        if (indices != null) {

            for (IndianIndice indianIndice : indices) {
                if (!exchangeList.contains(indianIndice.getExchange())) {
                    exchangeList.add(indianIndice.getExchange());
                }
            }

        }
    }

    private final AdapterView.OnItemSelectedListener exchangeselected = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            indicesStockList.clear();
            List<IndianIndice> indices = new ArrayList<>();
            indices = indianIndicesResponse.getIndianIndices();
            for (IndianIndice indianIndice : indices) {
                if (!indicesStockList.contains(indianIndice.getToken())) {
                    if (exchangeSpinner.getSelectedItem().toString().equalsIgnoreCase(indianIndice.getExchange())) {
                        if (!(indianIndice.getToken()).equalsIgnoreCase(""))
                            indicesStockList.add(indianIndice.getToken());
                    }
                }
            }
            indicesStockAdapter.notifyDataSetChanged();
            indicesSpinner.setSelection(0);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    private final AdapterView.OnItemSelectedListener stockselected = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            indicesListAdapter = new IndicesListAdapter(new ArrayList<IndicesStock>(), getMainActivity());
            indicesstockdetailrecycler.setAdapter(indicesListAdapter);
            sendRequest(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void sendRequest(int pos) {
        showProgress();
        MarketsIndicesStockRequest.sendRequest(exchangeSpinner.getSelectedItem().toString(), indicesSpinner.getSelectedItem().toString(), getMainActivity(), serviceResponseHandler);

    }


    public class IndicesListAdapter extends RecyclerView.Adapter<IndicesListAdapter.ViewHolder> {

        private List<IndicesStock> indicesStockResponse;
        Context context;

        public IndicesListAdapter(List<IndicesStock> indicesStockResponse, Context context) {
            this.indicesStockResponse = indicesStockResponse;
            this.context = context;
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        public IndicesStock getItem(int position) {
            return indicesStockResponse.get(position);
        }

        public void updateData(int position, IndicesStock indicesStock) {

            indicesStockResponse.set(position, indicesStock);
            notifyItemChanged(position);

        }


        @Override
        public IndicesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new IndicesListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_table_list, parent, false));
        }

        @Override
        public void onBindViewHolder(IndicesListAdapter.ViewHolder holder, int position) {
            final IndicesStock gainerData1Item = indicesStockResponse.get(position);
            holder.tvsymbol.setText(gainerData1Item.getDescription());


            if (((Integer.valueOf(gainerData1Item.getToken()) >= 502000000) && (Integer.valueOf(gainerData1Item.getToken()) <= 502999999)) ||
                    ((Integer.valueOf(gainerData1Item.getToken()) >= 1302000000) && (Integer.valueOf(gainerData1Item.getToken()) <= 1302999999))) {

                holder.tvltp.setText(String.format("%.4f", Double.parseDouble(gainerData1Item.getLtp())));
                holder.tvchange.setText(String.format("%.4f", Double.parseDouble(gainerData1Item.getChange())));
                holder.tvperchange.setText(String.format("%.4f", Double.parseDouble(gainerData1Item.getP_change())) + "%");

            } else {
                holder.tvltp.setText(String.format("%.2f", Double.parseDouble(gainerData1Item.getLtp())));
                holder.tvchange.setText(String.format("%.2f", Double.parseDouble(gainerData1Item.getChange())));
                holder.tvperchange.setText(String.format("%.2f", Double.parseDouble(gainerData1Item.getP_change())) + "%");
            }

            if (gainerData1Item.getP_change().startsWith("-")) {

                holder.tvperchange.setTextColor(context.getResources().getColor(R.color.bajaj_light_red));

            } else {
                if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                    holder.tvperchange.setTextColor(context.getResources().getColor(R.color.whitetheambuyColor));
                } else {
                    holder.tvperchange.setTextColor(context.getResources().getColor(R.color.dark_green_positive));
                }
            }
            if (gainerData1Item.getChange().startsWith("-")) {

                holder.tvchange.setTextColor(context.getResources().getColor(R.color.bajaj_light_red));

            } else {
                if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                    holder.tvchange.setTextColor(context.getResources().getColor(R.color.whitetheambuyColor));
                } else {
                    holder.tvchange.setTextColor(context.getResources().getColor(R.color.dark_green_positive));
                }
            }

        }

        @Override
        public int getItemCount() {
            return indicesStockResponse.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            GreekTextView tvsymbol, descriptionname;
            GreekTextView tvltp;
            GreekTextView tvchange;
            GreekTextView tvperchange;
            TextView strip;

            public ViewHolder(View convertView) {
                super(convertView);

                tvsymbol = (GreekTextView) convertView.findViewById(R.id.symbolname_text);
                descriptionname = (GreekTextView) convertView.findViewById(R.id.descriptionname_text);
                tvltp = (GreekTextView) convertView.findViewById(R.id.ltp_text);
                tvchange = (GreekTextView) convertView.findViewById(R.id.change_text);
                tvperchange = (GreekTextView) convertView.findViewById(R.id.perchange_text);
                strip = (TextView) convertView.findViewById(R.id.strip);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    tvsymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    tvltp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }
        }
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("ltpinfo")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {

        int index = indicesListAdapter.indexOf(response.getSymbol());

        IndicesStock data = indicesListAdapter.getItem(index);

        if (data.getToken().equalsIgnoreCase(response.getSymbol())) {


            if (!data.getLtp().equals(response.getLast())) {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }

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

            } else {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setChange(String.format("%.4f", Double.parseDouble(data.getChange())));

                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(data.getChange())));
                }

            }

            if (!data.getP_change().equals(response.getP_change())) {
                data.setP_change(String.format("%.2f", Double.parseDouble(response.getP_change())));

            } else {
                data.setP_change(String.format("%.2f", Double.parseDouble(data.getP_change())));
            }
            indicesListAdapter.updateData(index, data);
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
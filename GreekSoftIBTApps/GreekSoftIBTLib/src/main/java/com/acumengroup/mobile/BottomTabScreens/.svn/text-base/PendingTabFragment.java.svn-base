package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.acumengroup.greekmain.core.model.tradeorderbook.OrderBook;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.trade.OrderBookData;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.OrderBookResponses;
import com.acumengroup.mobile.model.OrderBookResponse;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class PendingTabFragment extends GreekBaseFragment {

    private OrderBookResponse orderBookResponse;
    private List<OrderBook> orderBook;
    private RecyclerView recyclerView;
    private MyListAdapter commonAdapter;
    private ArrayList<OrderBookResponses.OrdersBooksData> orderBookDataArrayList;
    private TextView emptyView, txt_action;
    private GreekTextView script_name, qty, price;
    private LinearLayout ColumnLauout;
    private SwipeRefreshLayout swipeRefresh;
    private View pendingParentview;
    private Spinner exchangeTypeSpinner, assetTypeSpinner;
    private final ArrayList<String> assetList = new ArrayList<>();
    private final ArrayList<String> exchangeList = new ArrayList<>();
    String servicetype;
    private String marketIDSelected = "1";


    public PendingTabFragment() {
        // Required empty public constructor
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            getOrderBookDetailWithLegV2  request is send to Arachne server when layout is refresh
            sendRequest();
        }
    };

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pendingParentview = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_pending_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_pending_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_PENDING_TAB_SCREEN;
        recyclerView = pendingParentview.findViewById(R.id.recyclerView);
        emptyView = pendingParentview.findViewById(R.id.empty_view);
        txt_action = pendingParentview.findViewById(R.id.txt_action);
        txt_action.setVisibility(View.VISIBLE);
        swipeRefresh = pendingParentview.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMainActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        script_name = pendingParentview.findViewById(R.id.script_name);
        qty = pendingParentview.findViewById(R.id.qty);
        price = pendingParentview.findViewById(R.id.price);
        ColumnLauout = pendingParentview.findViewById(R.id.ColumnLauout);
        exchangeTypeSpinner = pendingParentview.findViewById(R.id.spinner1);
        assetTypeSpinner = pendingParentview.findViewById(R.id.spinner2);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle bundle = new Bundle();
                OrderBookResponses.OrdersBooksData rowData = commonAdapter.getItem(position);
                bundle.putString("response", new Gson().toJson(rowData));
                bundle.putSerializable("Type", "OrderBook");
                bundle.putSerializable("From", "Pending");
                AccountDetails.globalPlaceOrderBundle = bundle;
                EventBus.getDefault().post("orderModifyCancel");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getAssetTypeList();
        setupExchangeController();
        setupController();
        sendRequest(); // getOrderBookDetailWithLegV2 is send to Arachne server
        setTheme();

        return pendingParentview;
    }

    private void setupController() {
        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 7, 15, 10);
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

        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(assetTypeAdapter);
        assetTypeSpinner.setSelection(0, false);
        assetTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                applyOrderFiltter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void applyOrderFiltter() {

//        Order Data is filer according to Exchange type and Asset type
        if (exchangeTypeSpinner.getSelectedItem() != null && assetTypeSpinner.getSelectedItem() != null && orderBookDataArrayList != null && orderBookDataArrayList.size() > 0) {

            String selectedExchangeType = exchangeTypeSpinner.getSelectedItem().toString();
            String selectedAssetstype = assetTypeSpinner.getSelectedItem().toString();


            ArrayList<OrderBookResponses.OrdersBooksData> tempOrderBookDataArrayList = new ArrayList<>();

            if (selectedAssetstype.equalsIgnoreCase("All") && selectedExchangeType.equalsIgnoreCase("All")) {
                commonAdapter = new MyListAdapter(orderBookDataArrayList, getMainActivity());
                recyclerView.setAdapter(commonAdapter);
                commonAdapter.notifyDataSetChanged();
                toggleErrorLayout(false);
            } else {
                for (int i = 0; i < orderBookDataArrayList.size(); i++) {

                    if (selectedAssetstype.equalsIgnoreCase("All") && getExchange(orderBookDataArrayList.get(i).getToken()).equalsIgnoreCase(selectedExchangeType)) {

                        tempOrderBookDataArrayList.add(orderBookDataArrayList.get(i));

                    } else if (selectedExchangeType.equalsIgnoreCase("All") && getAssetType(orderBookDataArrayList.get(i).getToken()).equalsIgnoreCase(selectedAssetstype)) {

                        tempOrderBookDataArrayList.add(orderBookDataArrayList.get(i));

                    } else if (getExchange(orderBookDataArrayList.get(i).getToken()).equalsIgnoreCase(selectedExchangeType) && getAssetType(orderBookDataArrayList.get(i).getToken()).equalsIgnoreCase(selectedAssetstype)) {

                        tempOrderBookDataArrayList.add(orderBookDataArrayList.get(i));

                    }

                }

                if (tempOrderBookDataArrayList.size() > 0) {
                    commonAdapter = new MyListAdapter(tempOrderBookDataArrayList, getMainActivity());
                    recyclerView.setAdapter(commonAdapter);
                    commonAdapter.notifyDataSetChanged();
                    toggleErrorLayout(false);

                } else {
                    toggleErrorLayout(true);
                }

            }


        }
    }


    private void getAssetTypeList() {

        assetList.clear();
        exchangeList.clear();

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
            assetList.add("Equity");
        }
        if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
            assetList.add("FNO");

        }
        if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            assetList.add("Currency");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            assetList.add("Commodity");
        }

        if (assetList.size() > 1) {
            assetList.add(0, "All");
        }

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_ncd) {
            exchangeList.add("NSE");
        }
        if (AccountDetails.allowedmarket_bse || AccountDetails.allowedmarket_bfo || AccountDetails.allowedmarket_bcd) {
            exchangeList.add("BSE");
        }
        if (AccountDetails.allowedmarket_mcx) {
            exchangeList.add("MCX");
        }
        if (AccountDetails.allowedmarket_ncdex) {
            exchangeList.add("NCDEX");
        }

        if (exchangeList.size() > 1) {
            exchangeList.add(0, "All");
        }
    }

    private void setupExchangeController() {
        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 7, 15, 10);
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
        typeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeTypeSpinner.setAdapter(typeAdapter);
        exchangeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                applyOrderFiltter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            ColumnLauout.setBackgroundColor(getResources().getColor(R.color.selectColor));
            emptyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
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

    void sendRequest() {

        String sessionId = AccountDetails.getSessionId(getActivity());
        String clientCode = AccountDetails.getClientCode(getActivity());

//        getOrderBookDetailWithLegV2 request is send to Arachne server with parameters clientCode, Order_Status, Ordertype and gscid
        String serviceURL = "getOrderBookDetailWithLegV2?exchangeType=All" + "&ClientCode=" + clientCode + "&Order_Status="
                + "PENDING_BAJAJ" + "&Ordertype=All" + "&gscid=" + AccountDetails.getUsername(getMainActivity());

        showProgress();
        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    toggleErrorLayout(false);

                    Gson gson = new Gson();
                    OrderBookResponses staff = gson.fromJson(String.valueOf(response), OrderBookResponses.class);

                    orderBookDataArrayList = staff.getData();
                    commonAdapter = new MyListAdapter(orderBookDataArrayList, getMainActivity());
                    recyclerView.setAdapter(commonAdapter);
                    hideProgress();
                    refreshComplete();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                toggleErrorLayout(true);
                hideProgress();
                refreshComplete();
            }
        });
    }

    private void handleOrderBookResponse(List<OrderBook> orderBook) {


        for (OrderBook item : orderBook) {

            OrderBookData orderBookData = new OrderBookData();
            orderBookData.setToken(item.getToken());
            orderBookData.setAssetType(getAssetType(item.getToken()));


            if (getExchange(item.getToken()).equalsIgnoreCase("MCX")) {

                if (item.getOptionType().equalsIgnoreCase("XX")) {
                    orderBookData.setTradeSymbol(item.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument());
                } else {
                    orderBookData.setTradeSymbol(item.getScripName() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument());
                }


            } else {
                orderBookData.setTradeSymbol(item.getTradeSymbol());
            }
            orderBookData.setPendingQty(item.getPendingQty());
            orderBookData.setQty(item.getQty());
            orderBookData.setPrice(item.getPrice());
            orderBookData.setExchange(getExchange(item.getToken()));
            orderBookData.setInstrument(item.getInstrument());
            orderBookData.setStatus(item.getStatus());
            orderBookData.setUniqueID(item.getUniqueID());
            orderBookData.setLgoodtilldate(item.getLgoodtilldate());
            orderBookData.setErrorCode(item.getErrorCode());
            orderBookData.setLotSize(item.getLotSize());
            orderBookData.setOtype(item.getOtype());
            orderBookData.setExpiryDate(item.getExpiryDate());
            orderBookData.setcPANNumber(item.getcPANNumber());
            orderBookData.setdSLPrice(item.getdSLPrice());
            orderBookData.setdTargetPrice(item.getdTargetPrice());
            orderBookData.setlIOMRuleNo(item.getlIOMRuleNo());
            orderBookData.setiStrategyId(item.getiStrategyId());
            orderBookData.setdSLTPrice(item.getdSLTPrice());
            orderBookData.setStrikePrice(item.getStrikePrice());
            orderBookData.setOptionType(item.getOptionType());
            orderBookData.setTag(item.getTag());


            if (item.getProduct().equals("1")) {
                orderBookData.setProduct("Intraday");
            } else if (item.getProduct().equals("0")) {
                orderBookData.setProduct("Delivery");
            } else if (item.getProduct().equals("2")) {
                orderBookData.setProduct("MTF");
            } else if (item.getProduct().equals("3")) {
                orderBookData.setProduct("SSEQ");
            } else {
                orderBookData.setProduct("");
            }
            if (item.getAction().equals("1")) {
                orderBookData.setAction("Buy");
            } else if (item.getAction().equals("2")) {
                orderBookData.setAction("Sell");
            } else {
                orderBookData.setAction("");
            }
            orderBookData.setDetails(item);
        }

    }

    private void toggleErrorLayout(boolean show) {
        if (recyclerView != null) {
            if (show) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        }
    }


    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

        private ArrayList<OrderBookResponses.OrdersBooksData> orderBooklist;
        Context context;

        // RecyclerView recyclerView;
        public MyListAdapter(ArrayList<OrderBookResponses.OrdersBooksData> orderBook, Context context) {
            this.context = context;
            this.orderBooklist = orderBook;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_pending_layout, parent, false);
            return new ViewHolder(listItem);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            OrderBookResponses.OrdersBooksData orderBookData = orderBooklist.get(position);


            List<OrderBookResponses.OrdersBooksData.LegInfos> legInfoList = orderBookData.getLegInfo();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.orderLayout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.LegsLayout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.legOneLayout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.legTwoLayout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.legThreeLayout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.legFourLayout.setBackgroundColor(getResources().getColor(R.color.white));

                holder.txt_script.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_price.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_qty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_action.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.type_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                holder.txt_script1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_price1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_qty1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_action1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.type_name1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                holder.txt_script2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_price2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_qty2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_action2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.type_name2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                holder.txt_script3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_price3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_qty3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_action3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.type_name3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                holder.txt_script4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_price4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_qty4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_action4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.type_name4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            }


            if (legInfoList != null && legInfoList.size() > 0) {
                holder.orderLayout.setVisibility(View.GONE);
                holder.LegsLayout.setVisibility(View.VISIBLE);

                if (legInfoList.size() == 1) {

                    holder.legOneLayout.setVisibility(View.VISIBLE);
                    holder.legTwoLayout.setVisibility(View.GONE);
                    holder.legThreeLayout.setVisibility(View.GONE);
                    holder.legFourLayout.setVisibility(View.GONE);

                } else if (legInfoList.size() == 2) {

                    holder.legOneLayout.setVisibility(View.VISIBLE);
                    holder.legTwoLayout.setVisibility(View.VISIBLE);
                    holder.legThreeLayout.setVisibility(View.GONE);
                    holder.legFourLayout.setVisibility(View.GONE);

                } else if (legInfoList.size() == 3) {

                    holder.legOneLayout.setVisibility(View.VISIBLE);
                    holder.legTwoLayout.setVisibility(View.VISIBLE);
                    holder.legThreeLayout.setVisibility(View.VISIBLE);
                    holder.legFourLayout.setVisibility(View.GONE);

                } else if (legInfoList.size() == 4) {

                    holder.legOneLayout.setVisibility(View.VISIBLE);
                    holder.legTwoLayout.setVisibility(View.VISIBLE);
                    holder.legThreeLayout.setVisibility(View.VISIBLE);
                    holder.legFourLayout.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < legInfoList.size(); i++) {

                    OrderBookResponses.OrdersBooksData.LegInfos legInfos = legInfoList.get(i);

                    if (i == 0) {

                        holder.txt_script1.setText(legInfos.getDescription());
                        holder.type_name1.setText(".NSE");
                        holder.txt_price1.setText(String.format("%.2f", Double.parseDouble(legInfos.getDPrice())));
                        holder.txt_qty1.setText(legInfos.getLTotalVolRemaining());

                        if (legInfos.getIBuySell().equalsIgnoreCase("1")) {
                            holder.txt_action1.setTextColor(getResources().getColor(R.color.green_500));
                            holder.txt_action1.setText("BUY");
                        } else {
                            holder.txt_action1.setTextColor(getResources().getColor(R.color.red_textcolor));
                            holder.txt_action1.setText("SELL");

                        }


                    }
                    if (i == 1) {

                        holder.txt_script2.setText(legInfos.getDescription());
                        holder.type_name2.setText(".NSE");
                        holder.txt_price2.setText(String.format("%.2f", Double.parseDouble(legInfos.getDPrice())));
                        holder.txt_qty2.setText(legInfos.getLTotalVolRemaining());

                        if (legInfos.getIBuySell().equalsIgnoreCase("1")) {
                            holder.txt_action2.setTextColor(getResources().getColor(R.color.green_500));
                            holder.txt_action2.setText("BUY");
                        } else {
                            holder.txt_action2.setTextColor(getResources().getColor(R.color.red_textcolor));
                            holder.txt_action2.setText("SELL");
                        }


                    }
                    if (i == 2) {

                        holder.txt_script3.setText(legInfos.getDescription());
                        holder.type_name3.setText(".NSE");
                        holder.txt_price3.setText(String.format("%.2f", Double.parseDouble(legInfos.getDPrice())));
                        holder.txt_qty3.setText(legInfos.getLTotalVolRemaining());

                        if (legInfos.getIBuySell().equalsIgnoreCase("1")) {
                            holder.txt_action3.setTextColor(getResources().getColor(R.color.green_500));
                            holder.txt_action3.setText("BUY");
                        } else {
                            holder.txt_action3.setTextColor(getResources().getColor(R.color.red_textcolor));
                            holder.txt_action3.setText("SELL");
                        }


                    }
                    if (i == 3) {

                        holder.txt_script4.setText(legInfos.getDescription());
                        holder.type_name4.setText(".NSE");
                        holder.txt_price4.setText(String.format("%.2f", Double.parseDouble(legInfos.getDPrice())));
                        holder.txt_qty4.setText(legInfos.getLTotalVolRemaining());

                        if (legInfos.getIBuySell().equalsIgnoreCase("1")) {
                            holder.txt_action4.setTextColor(getResources().getColor(R.color.green_500));
                            holder.txt_action4.setText("BUY");
                        } else {
                            holder.txt_action4.setTextColor(getResources().getColor(R.color.red_textcolor));
                            holder.txt_action4.setText("SELL");
                        }

                    }

                }

            } else {


                holder.txt_script.setText(orderBookData.getDescription());
                holder.type_name.setText("." + getExchange(orderBookData.getToken()));

                if (getAssetType(orderBookData.getToken()).equalsIgnoreCase("currency")) {
                    holder.txt_price.setText(String.format("%.4f", Double.parseDouble(orderBookData.getPrice())));

                } else {
                    holder.txt_price.setText(String.format("%.2f", Double.parseDouble(orderBookData.getPrice())));

                }

                if (getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("mcx") ||
                        getExchange(orderBookData.getToken()).toLowerCase().equalsIgnoreCase("ncdex")) {

                    holder.txt_qty.setText(String.valueOf((Integer.parseInt(orderBookData.getPendingQty())) /
                            Integer.parseInt(orderBookData.getLotSize())));

                } else {

                    holder.txt_qty.setText(orderBookData.getPendingQty());

                }


                if (orderBookData.getAction().equalsIgnoreCase("1")) {
                    holder.txt_action.setTextColor(getResources().getColor(R.color.green_500));
                    holder.txt_action.setText("BUY");
                } else {
                    holder.txt_action.setTextColor(getResources().getColor(R.color.red_textcolor));
                    holder.txt_action.setText("SELL");

                }

            }

        }


        @Override
        public int getItemCount() {
            return orderBooklist.size();
        }


        public OrderBookResponses.OrdersBooksData getItem(int position) {
            if (getItemCount() > 0) return orderBooklist.get(position);
            return null;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            GreekTextView txt_script, txt_price, txt_qty, txt_action, type_name;
            GreekTextView txt_script1, txt_price1, txt_qty1, txt_action1, type_name1;
            GreekTextView txt_script2, txt_price2, txt_qty2, txt_action2, type_name2;
            GreekTextView txt_script3, txt_price3, txt_qty3, txt_action3, type_name3;
            GreekTextView txt_script4, txt_price4, txt_qty4, txt_action4, type_name4;

            LinearLayout orderLayout, LegsLayout, legOneLayout, legTwoLayout, legThreeLayout, legFourLayout;

            public ViewHolder(View itemView) {
                super(itemView);

                orderLayout = itemView.findViewById(R.id.orderLayout);
                LegsLayout = itemView.findViewById(R.id.LegsLayout);
                legOneLayout = itemView.findViewById(R.id.legOneLayout);
                legTwoLayout = itemView.findViewById(R.id.legTwoLayout);
                legThreeLayout = itemView.findViewById(R.id.legThreeLayout);
                legFourLayout = itemView.findViewById(R.id.legFourLayout);

                txt_script = itemView.findViewById(R.id.script_name);
                txt_price = itemView.findViewById(R.id.price);
                txt_qty = itemView.findViewById(R.id.qty);
                txt_action = itemView.findViewById(R.id.action);
                type_name = itemView.findViewById(R.id.type_name);

                txt_script1 = itemView.findViewById(R.id.script_name1);
                txt_price1 = itemView.findViewById(R.id.price1);
                txt_qty1 = itemView.findViewById(R.id.qty1);
                txt_action1 = itemView.findViewById(R.id.action1);
                type_name1 = itemView.findViewById(R.id.type_name1);

                txt_script2 = itemView.findViewById(R.id.script_name2);
                txt_price2 = itemView.findViewById(R.id.price2);
                txt_qty2 = itemView.findViewById(R.id.qty2);
                txt_action2 = itemView.findViewById(R.id.action2);
                type_name2 = itemView.findViewById(R.id.type_name2);

                txt_script3 = itemView.findViewById(R.id.script_name3);
                txt_price3 = itemView.findViewById(R.id.price3);
                txt_qty3 = itemView.findViewById(R.id.qty3);
                txt_action3 = itemView.findViewById(R.id.action3);
                type_name3 = itemView.findViewById(R.id.type_name3);

                txt_script4 = itemView.findViewById(R.id.script_name4);
                txt_price4 = itemView.findViewById(R.id.price4);
                txt_qty4 = itemView.findViewById(R.id.qty4);
                txt_action4 = itemView.findViewById(R.id.action4);
                type_name4 = itemView.findViewById(R.id.type_name4);

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

        // return "";
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
        //  return "";
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            AccountDetails.currentFragment = NAV_TO_PENDING_TAB_SCREEN;
//            sendRequest();
            //_PendingLoaded = true;
        }
    }
}

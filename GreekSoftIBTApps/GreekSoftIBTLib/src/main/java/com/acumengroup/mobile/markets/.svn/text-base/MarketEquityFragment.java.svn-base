package com.acumengroup.mobile.markets;

import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MarketDataModel;
import com.acumengroup.mobile.model.MarketDataResponse;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */

public class MarketEquityFragment extends GreekBaseFragment {
    private boolean isWaitingForRequest = false;
    private Spinner exchangeSpinner, filtersSpinner;
    private ListView listMarketEquity;
    private String currentType = "";
    private ArrayList streamingList;
    private TopGainerAdapter gainerAdapter;
    private MarketDataResponse marketResponse;
    private com.acumengroup.ui.textview.GreekTextView changingHeader;
    Bundle args;
    StreamingController streamController = new StreamingController();
    private AlertDialog levelDialog;
    private boolean pullToRefreshFlag = false;


    private final AdapterView.OnItemClickListener marketMoversListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            LayoutInflater inflater = getMainActivity().getLayoutInflater();
            View layout = inflater.inflate(R.layout.alert_quick_three_actions, parent, false);
            GreekTextView view2 = layout.findViewById(R.id.action_item2);
            //   view1.setText("Trade");
            view2.setText(R.string.POPUP_SCRIPT_TRADE);
            GreekTextView view1 = layout.findViewById(R.id.action_item1);
            //   view2.setText("Get Quote");
            view1.setText(R.string.POPUP_SCRIPT_QUOTE);
            GreekTextView view3 = layout.findViewById(R.id.action_item3);
            //    view3.setText("Get Charts");
            view3.setText(R.string.POPUP_SCRIPT_TRADE_SELL);

            view2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        moveToPage(position, "trade");
                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
                        levelDialog.dismiss();
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", true, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    } else {
                        levelDialog.dismiss();
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.no_access), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }
                }
            });

            view3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        moveToPage(position, "sell");
                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
                        levelDialog.dismiss();
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", true, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    } else {
                        levelDialog.dismiss();
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.no_access), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    }
                }
            });

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToPage(position, "quote");
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            builder.setView(layout);
            levelDialog = builder.create();
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }
        }
    };

    private RelativeLayout errorLayout;
    private SwipeRefreshLayout swipeRefresh;

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            pullToRefreshFlag = true;
            loadTopGainerLooserList();
        }
    };
    private final AdapterView.OnItemSelectedListener exchangeItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (AccountDetails.marketEquity)
                loadTopGainerLooserList();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener filterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (AccountDetails.marketEquity)
                loadTopGainerLooserList();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    //For Top Header Portion
    private int clickedPos = -1;

    private void moveToPage(int position, final String toPage) {
        try {
            final MarketDataModel data = marketResponse.getMarketDataModelList().get(position);
            Bundle args = new Bundle();
            levelDialog.dismiss();
            switch (toPage) {
                case "trade":
                    args.putString(TradeFragment.SCRIP_NAME, data.getSymbol());
                    args.putString(TradeFragment.EXCHANGE_NAME, data.getExchange());
                    args.putString(TradeFragment.TOKEN, data.getToken());
                    args.putString(TradeFragment.ASSET_TYPE, data.getAssetType());
                    //args.putString("UniqueId", multiStockDetails.getUniqueID());
                    args.putString(TradeFragment.TRADE_SYMBOL, data.getDescription());
                    args.putString(TradeFragment.LOT_QUANTITY, data.getLotSize());
                    args.putString(TradeFragment.MULTIPLIER, data.getMultiply_factor());
                    args.putString(TradeFragment.TICK_SIZE, data.getTickSize());
                    args.putString(TradeFragment.TRADE_ACTION, "buy");
                    //  args.putString(TradeFragment.TRADE_ACTION, "buy");
                    navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                    break;
                case "sell":
                    args.putString(TradeFragment.SCRIP_NAME, data.getSymbol());
                    args.putString(TradeFragment.EXCHANGE_NAME, data.getExchange());
                    args.putString(TradeFragment.TOKEN, data.getToken());
                    args.putString(TradeFragment.ASSET_TYPE, data.getAssetType());
                    //args.putString("UniqueId", multiStockDetails.getUniqueID());
                    args.putString(TradeFragment.TRADE_SYMBOL, data.getDescription());
                    args.putString(TradeFragment.LOT_QUANTITY, data.getLotSize());
                    args.putString(TradeFragment.MULTIPLIER, data.getMultiply_factor());
                    args.putString(TradeFragment.TICK_SIZE, data.getTickSize());
                    args.putString(TradeFragment.TRADE_ACTION, "sell");
                    //   args.putString(TradeFragment.TRADE_ACTION, "sell");
                    navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                    break;
                case "quote":
                    args.putString(QuotesFragment.CASH_INST, data.getCashInstName());
                    args.putString(QuotesFragment.SCRIP_NAME, data.getSymbol());
                    args.putString(QuotesFragment.EXCHANGE_NAME, data.getExchange());
                    args.putString(QuotesFragment.TOKEN, data.getToken());
                    if (data.getAssetType().equalsIgnoreCase("future")) {
                        args.putString(QuotesFragment.ASSET_TYPE, "fno");
                    } else {
                        args.putString(QuotesFragment.ASSET_TYPE, data.getAssetType().toLowerCase());
                    }
                    args.putString(QuotesFragment.UNIQUE_ID, data.getToken());
                    args.putString(QuotesFragment.TRADE_SYMBOL, data.getDescription());
                    //args.putString("Lots", multiStockDetails.getLotQty());
                    args.putString(QuotesFragment.INST_TYPE, data.getInstName());
                    args.putString(QuotesFragment.MULTIPLIER, data.getMultiply_factor());
                    args.putString(QuotesFragment.TICK_SIZE, data.getTickSize());
                    //For Currency
                    args.putString(QuotesFragment.EXPIRY, data.getExpiryDate());
                    args.putString(QuotesFragment.STRIKE_PRICE, data.getStrikePrice());
                    args.putString(QuotesFragment.OPTION_TYPE, data.getOptType());
                    args.putString(QuotesFragment.SELECTED_OPTION, data.getOptType());
                    navigateTo(NAV_TO_QUOTES_SCREEN, args, true);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View newsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_market_equity).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_market_equity).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setupViews(newsView);
        sendContentRequest();
        return newsView;
    }


    public void loadPageContent() {
        sendContentRequest();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setupViews(View parent) {
        hideAppTitle();

        exchangeSpinner = parent.findViewById(R.id.exchangeSpinner);
        filtersSpinner = parent.findViewById(R.id.filtersSpinner);

        changingHeader = parent.findViewById(R.id.tvRandom);

        listMarketEquity = parent.findViewById(R.id.listMarketEquity);
        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        errorLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = parent.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }
        setupAdapter();
    }

    private void setupAdapter() {

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(),
                Arrays.asList(getResources().getStringArray(R.array.exchangeType)));
        groupAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(groupAdapter);
        exchangeSpinner.setOnItemSelectedListener(exchangeItemSelectedListener);

        ArrayAdapter<String> col1Adapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(),
                Arrays.asList(getResources().getStringArray(R.array.marketFilterEquity)));
        col1Adapter.setDropDownViewResource(R.layout.custom_spinner);
        filtersSpinner.setAdapter(col1Adapter);
        filtersSpinner.setOnItemSelectedListener(filterItemSelectedListener);

        gainerAdapter = new TopGainerAdapter(getMainActivity(), new ArrayList<MarketDataModel>());
        listMarketEquity.setAdapter(gainerAdapter);
    }


    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    private void sendContentRequest() {
        exchangeSpinner.setSelection(0);
        filtersSpinner.setSelection(0);
        loadTopGainerLooserList();
    }

    private void loadTopGainerLooserList() {

        String exchangeStr = exchangeSpinner.getSelectedItem().toString();
        exchangeStr = exchangeStr.toLowerCase();

        String filtersStr = filtersSpinner.getSelectedItem().toString();
        String type;
        String service;
        String serviceURL;
        changingHeader.setText("Change");
        switch (filtersStr) {
            case "Top Gainers":
                type = "gainer";
                service = "getTopEQGainerLoser_Mobile";
                serviceURL = service + "?exchange=" + exchangeStr + "&type=" + type;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopEQGainerLoser_Mobile";
                serviceURL = service + "?exchange=" + exchangeStr + "&type=" + type;
                break;
            case "Top Performing Sectors":
                type = "sectors";
                service = "getTopPerformingSectors";
                serviceURL = service + "?exchange=" + exchangeStr;
                break;
            case "Worst Performing Sectors":
                type = "sectors";
                service = "getWorstPerformingSectors";
                serviceURL = service + "?exchange=" + exchangeStr;
                break;
            case "Most Active (Vol.)":
                type = "volume";
                service = "getMostActiveEquityByVolume_Mobile";
                serviceURL = service + "?exchange=" + exchangeStr;
                changingHeader.setText("Vol (000)");
                break;
            case "Most Active (Val.)":
                type = "value";
                service = "getMostActiveEquityByValue_Mobile";

                serviceURL = service + "?exchange=" + exchangeStr;
                changingHeader.setText("Value(lac)");
                break;
            default:
                return;
        }
        if ("sectors".equals(type)) {
            listMarketEquity.setOnItemClickListener(null);
        } else {
            listMarketEquity.setOnItemClickListener(marketMoversListener);
        }

        currentType = type;
        if (!isWaitingForRequest && serviceURL.length() > 0) {
            gainerAdapter.clear();
            isWaitingForRequest = true;

            if (!pullToRefreshFlag) {
                showProgress();
            }

            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    toggleErrorLayout(false);
                    try {
                        streamingList = new ArrayList();
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);
                        gainerAdapter.setData(marketResponse.getMarketDataModelList());

                        for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
                            streamingList.add(marketResponse.getMarketDataModelList().get(i).getToken());
                            gainerAdapter.addSymbol(marketResponse.getMarketDataModelList().get(i).getToken());
                        }
                        if (AccountDetails.marketEquity) {
                            if (getStreamingSymbolList("ltpinfo") != null) {
                                if (getStreamingSymbolList("ltpinfo").size() > 0) {
                                    streamController.pauseStreaming(getMainActivity(), "ltpinfo", getStreamingSymbolList("ltpinfo"));
                                }
                            }
                            sendStreamingRequest();
                        }
                    } catch (JSONException e) {
                        toggleErrorLayout(true);
                        e.printStackTrace();
                    }
                    refreshComplete();
                }

                @Override
                public void onFailure(String message) {
                    toggleErrorLayout(true);
                    refreshComplete();
                }
            });
        }
    }


    private void sendStreamingRequest() {
        streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", streamingList);
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onPause() {
        if (streamingList != null) {
            if (streamingList.size() > 0)
                streamController.pauseStreaming(getActivity(), "ltpinfo", streamingList);
        }
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (streamingList != null) {
            if (streamingList.size() > 0)
                sendStreamingRequest();
        }
    }

    @Override
    public void onFragmentPause() {
        if (streamingList.size() > 0)
            streamController.pauseStreaming(getActivity(), "ltpinfo", streamingList);
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    @Override
    public void onFragmentResume() {
        if (EventBus.getDefault().isRegistered(this)) {
            if (streamingList != null) {
                if (streamingList.size() > 0) {
                    sendStreamingRequest();
                }
            }
        } else {
            EventBus.getDefault().register(this);
            if (streamingList != null) {
                if (streamingList.size() > 0) {
                    sendStreamingRequest();
                }
            }
        }
    }

    private void refreshComplete() {
        hideProgress();
        isWaitingForRequest = false;
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    //todo

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

            if (gainerAdapter.containsSymbol(response.getSymbol())) {
                boolean notifyNeeded = false;

                MarketDataModel data = gainerAdapter.getItem(gainerAdapter.indexOf(response.getSymbol()));
                String color = null;
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

                if (!data.getPerChange().equals(response.getP_change())) {
                    data.setPerChange(String.format("%.2f", Double.parseDouble(response.getP_change())));
                    notifyNeeded = true;
                } else {
                    data.setPerChange(String.format("%.2f", Double.parseDouble(data.getPerChange())));
                }
                gainerAdapter.updateData(gainerAdapter.indexOf(response.getSymbol()), data);

                if (notifyNeeded) {
                    gainerAdapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class TopGainerAdapter extends BaseAdapter {
        private final Context mContext;
        private List<MarketDataModel> topGainerList;
        ArrayList<String> tokenList;

        public TopGainerAdapter(Context context, ArrayList<MarketDataModel> topGainerList) {
            this.mContext = context;
            this.topGainerList = topGainerList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<MarketDataModel> gainerList) {
            this.topGainerList = gainerList;
            notifyDataSetChanged();
        }

        public void clear() {
            this.topGainerList.clear();
            this.tokenList.clear();
            notifyDataSetChanged();
        }

        public void addSymbol(String symbol) {
            tokenList.add(symbol);
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public void updateData(int position, MarketDataModel model) {
            topGainerList.set(position, model);
        }

        @Override
        public int getCount() {
            return topGainerList.size();
        }

        @Override
        public MarketDataModel getItem(int position) {
            return topGainerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_market_equity_derivative, parent, false);
                holder.tvSymbol = convertView.findViewById(R.id.tvSymbol);
                holder.txtCompany = convertView.findViewById(R.id.txtCompany);
                holder.tvLtp = convertView.findViewById(R.id.tvLtp);
                holder.tvVol = convertView.findViewById(R.id.tvVol);
                holder.tvPerChange = convertView.findViewById(R.id.tvChg);
                holder.upDownArrow = convertView.findViewById(R.id.updownArrow);
                holder.dividerEquityLabel = convertView.findViewById(R.id.dividerDerivative);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            MarketDataModel model = getItem(position);

            holder.tvSymbol.setText(model.getSymbol());
            holder.txtCompany.setText(model.getCompanyName());
            holder.tvLtp.setText(String.format("%.2f", Double.parseDouble(model.getLtp())));
            //holder.tvLtp.setBackgroundColor(getResources().getColor(R.color.red));

            switch (currentType) {
                case "gainer":
                case "loser":
                case "sectors":
                    holder.tvVol.setText(String.format("%.2f", Double.parseDouble(model.getChange())));

                    break;
                case "volume":
                    holder.tvVol.setText(model.getVolume());
                    break;
                case "value":
                    holder.tvVol.setText(String.format("%.2f", Double.parseDouble(model.getValue())));
                    break;
            }
            Double perChange;

            if ( model.getPerChange() != null || model.getPerChange().equals("null") || model.getPerChange().equalsIgnoreCase("")) {
                perChange = 0.0;
            } else {
                perChange = Double.valueOf(model.getPerChange());
            }
            holder.tvPerChange.setText(String.format("%.2f%%", perChange));
            int textColor = AccountDetails.getTextColorDropdown();
            int arrow_image = 0;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvSymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvLtp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txtCompany.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvVol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dividerEquityLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

                arrow_image = R.drawable.up_arrow;

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvSymbol.setTextColor(getResources().getColor(textColor));
                holder.tvLtp.setTextColor(getResources().getColor(textColor));
                holder.txtCompany.setTextColor(getResources().getColor(textColor));
                holder.tvVol.setTextColor(getResources().getColor(textColor));
                holder.dividerEquityLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

                arrow_image = R.drawable.up_arrow_green;
            }

            if (perChange < 0) {
                holder.tvPerChange.setTextColor(getResources().getColor(R.color.red));
                holder.upDownArrow.setImageResource(R.drawable.down_arrow_red);

            } else {
//                holder.tvPerChange.setTextColor(getResources().getColor(R.color.green_textcolor));
                holder.tvPerChange.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
                holder.upDownArrow.setImageResource(arrow_image);
            }
//          [TODO: SUSHANT]




            return convertView;
        }

        public class Holder {
            ScrollingTextView tvSymbol, txtCompany;
            GreekTextView tvLtp, tvVol, tvPerChange;
            ImageView upDownArrow;
            View dividerEquityLabel;
        }
    }

}

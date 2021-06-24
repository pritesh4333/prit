package com.acumengroup.mobile.markets;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.OpenInterestResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MarketDataModel;
import com.acumengroup.mobile.model.MarketDataResponse;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */
public class MarketDerivativeFragment extends GreekBaseFragment implements View.OnClickListener {
    private Spinner filtersSpinner, exchangeSpinner;
    private DerivativeAdapter commonAdapter;
    private MarketDataResponse marketResponse;
    private GreekTextView changingHeader;
    private boolean isWaitingForRequest = false;
    private boolean pullToRefreshFlag = false;
    private String currentType = "";
    private RelativeLayout errorLayout;
    private ArrayList streamingList;
    Bundle args;
    private int exchange_checkedItem = 0;
    private int checkedItem = 0;
    private AlertDialog levelDialog;
    ArrayAdapter<String> filterAdapter;
    ArrayAdapter<String> groupAdapter;


    private final AdapterView.OnItemClickListener marketMoversListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            LayoutInflater inflater = getMainActivity().getLayoutInflater();
            View layout = inflater.inflate(R.layout.alert_quick_three_actions, null);
            GreekTextView view1 = layout.findViewById(R.id.action_item2);
            //    view1.setText("Trade");
            view1.setText(R.string.POPUP_SCRIPT_TRADE);
            GreekTextView view2 = layout.findViewById(R.id.action_item1);
            //   view2.setText("Get Quote");
            view2.setText(R.string.POPUP_SCRIPT_QUOTE);
            GreekTextView view3 = layout.findViewById(R.id.action_item3);
            //   view3.setText("Get Charts");
            view3.setText(R.string.POPUP_SCRIPT_TRADE_SELL);

            view1.setOnClickListener(new View.OnClickListener() {

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
            view2.setOnClickListener(new View.OnClickListener() {
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
    private SwipeRefreshLayout swipeRefresh;
    private final AdapterView.OnItemSelectedListener filterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (AccountDetails.marketDerivative)
                loadDerivativeData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // if (AccountDetails.marketDerivative)
            pullToRefreshFlag = true;
            loadDerivativeData();
        }
    };

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
        View marketDerivativeView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_market_derivatives).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_market_derivatives).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        hideAppTitle();
        setupViews(marketDerivativeView);
        loadPageContent();

        return marketDerivativeView;
    }

    private final AdapterView.OnItemSelectedListener exchangeItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            loadPageContent();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setupViews(View parent) {
        exchangeSpinner = parent.findViewById(R.id.exchangeSpinner);
        groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), Arrays.asList(getResources().getStringArray(R.array.exchangeType)));
        groupAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(groupAdapter);
        exchangeSpinner.setOnItemSelectedListener(exchangeItemSelectedListener);

        filtersSpinner = parent.findViewById(R.id.filtersSpinner);
        filterAdapter = new ArrayAdapter<>(getMainActivity()
                , AccountDetails.getRowSpinnerSimple(), Arrays.asList(getResources().getStringArray(R.array.marketFilterDerivative)));
        filterAdapter.setDropDownViewResource(R.layout.custom_spinner);
        filtersSpinner.setAdapter(filterAdapter);
        filtersSpinner.setOnItemSelectedListener(filterItemSelectedListener);

        changingHeader = parent.findViewById(R.id.tvRandom);

        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);

        ListView listMarketDerivative = parent.findViewById(R.id.listMarketDerivative);
        commonAdapter = new DerivativeAdapter(getMainActivity(), new ArrayList<MarketDataModel>());
        listMarketDerivative.setAdapter(commonAdapter);
        listMarketDerivative.setOnItemClickListener(marketMoversListener);

        errorLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = parent.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void loadPageContent() {

        exchangeSpinner.setSelection(0);
        filtersSpinner.setSelection(0);
        loadDerivativeData();
    }


    private void loadDerivativeData() {
        String exchangeStr = exchangeSpinner.getSelectedItem().toString();
        exchangeStr = exchangeStr.toLowerCase();
        String service;
        String type;
        changingHeader.setText("OI");
        switch (filtersSpinner.getSelectedItem().toString()) {
            case "Top Gainers - Futures":
                type = "gainer";
                service = "getTopGainerAndLoserForFutures_Mobile";
                break;
            case "Top Losers - Futures":
                type = "loser";
                service = "getTopGainerAndLoserForFutures_Mobile";
                break;
            case "Most Active Futures contracts (Vol.)":
                type = "future";
                service = "getMostActiveByVolume_Mobile";
                changingHeader.setText("Vol (000)");
                break;
            case "Most Active Index Option contracts (Vol.)":
                type = "option_index";
                service = "getMostActiveByVolume_Mobile";
                break;
            case "Most Active Stock Option contracts (Vol.)":
                type = "option_stock";
                service = "getMostActiveByVolume_Mobile";
                break;
            default:
                return;
        }
        currentType = type;

        if ( type.length() > 0 && service.length() > 0) {
            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            isWaitingForRequest = true;
            if (!pullToRefreshFlag) {
                showProgress();
            }
            WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&type=" + type, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    toggleErrorLayout(false);
                    refreshComplete();
                    try {
                        streamingList = new ArrayList();
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);
                        commonAdapter.setData(marketResponse.getMarketDataModelList());

                        if (marketResponse.getMarketDataModelList().size() > 0) {
                            for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
                                streamingList.add(marketResponse.getMarketDataModelList().get(i).getToken());
                                commonAdapter.addSymbol(marketResponse.getMarketDataModelList().get(i).getToken());
                            }
                            if (AccountDetails.marketDerivative) {
                                if (getStreamingSymbolList("ltpinfo") != null) {
                                    if (getStreamingSymbolList("ltpinfo").size() > 0) {
                                        streamController.pauseStreaming(getMainActivity(), "ltpinfo", getStreamingSymbolList("ltpinfo"));
                                    }
                                }

                                sendStreamingRequest();
                            }

                        }
                        commonAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        toggleErrorLayout(true);
                        e.printStackTrace();
                    }
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
        if (streamController != null && streamingList != null) {
            streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
            addToStreamingList("ltpinfo", streamingList);
        }
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void refreshComplete() {
        hideProgress();
        isWaitingForRequest = false;
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
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
            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("OpenInterest")) {
                OpenInterestResponse openInterestResponse = new OpenInterestResponse();
                openInterestResponse.fromJSON(streamingResponse.getResponse());
                updateOpenInterestData(openInterestResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(OpenInterestResponse openInterestResponse) {
        updateOpenInterestData(openInterestResponse);
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        if (commonAdapter.containsSymbol(response.getSymbol())) {
            ArrayList<Integer> changedCol = new ArrayList<>();
            MarketDataModel data = commonAdapter.getItem(commonAdapter.indexOf(response.getSymbol()));
            //update table

            if (!data.getLtp().equals(response.getLast())) {
                changedCol.add(1);


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

            if (!data.getPerChange().equals(response.getP_change())) {
                changedCol.add(3);
                data.setPerChange(String.format("%.2f", Double.parseDouble(response.getP_change())));
            } else {
                data.setPerChange(String.format("%.2f", Double.parseDouble(data.getPerChange())));
            }
            commonAdapter.updateData(commonAdapter.indexOf(response.getSymbol()), data);
            commonAdapter.notifyDataSetChanged();
        }
    }

    public void updateOpenInterestData(OpenInterestResponse openInterestResponse) {
        try {
            if (commonAdapter.containsSymbol(openInterestResponse.getToken())) {
                MarketDataModel data = commonAdapter.getItem(commonAdapter.indexOf(openInterestResponse.getToken()));
                if (!data.getOi().equals(openInterestResponse.getCurrentOI())) {
                    data.setOi(String.format("%.2f", Double.parseDouble(openInterestResponse.getCurrentOI())));
                } else {
                    data.setOi(String.format("%.2f", Double.parseDouble(openInterestResponse.getCurrentOI())));
                }
                commonAdapter.updateData(commonAdapter.indexOf(openInterestResponse.getToken()), data);
                commonAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        if (R.id.exchangeSpinner == view.getId()) {

            final CharSequence[] filter = new CharSequence[]{"NSE", "BSE"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            builder.setTitle("DERIVATIVE");
            builder.setSingleChoiceItems(filter, exchange_checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    exchange_checkedItem = i;
                    exchangeSpinner.setSelection(i, true);
                    loadDerivativeData();
                    levelDialog.dismiss();
                }
            });
            levelDialog = builder.create();
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }

        } else if (R.id.filtersSpinner == view.getId()) {

            final CharSequence[] filters = new CharSequence[]{
                    "Top Gainers - Futures",
                    "Top Losers - Futures",
                    "Most Active Futures contracts (Vol.)",
                    "Most Active Index Option contracts (Vol.)"
                    , "Most Active Stock Option contracts (Vol.)"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            builder.setTitle("MARKET");
            builder.setSingleChoiceItems(filters, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkedItem = i;

                    filtersSpinner.setSelection(i, true);
                    // filtersSpinner.setText(filters[i]);
                    loadDerivativeData();
                    levelDialog.dismiss();
                }
            });
            levelDialog = builder.create();
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }
        }

    }


    public class DerivativeAdapter extends BaseAdapter {
        private final Context mContext;
        private List<MarketDataModel> derivativeList;
        ArrayList<String> tokenList;


        public DerivativeAdapter(Context context, ArrayList<MarketDataModel> derivativeList) {
            this.mContext = context;
            this.derivativeList = derivativeList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<MarketDataModel> derivativeList) {
            this.derivativeList = derivativeList;
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        public void addSymbol(String symbol) {
            tokenList.add(symbol);
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public void updateData(int position, MarketDataModel model) {
            derivativeList.set(position, model);
        }

        public void clear() {
            this.derivativeList.clear();
            this.tokenList.clear();
        }

        @Override
        public int getCount() {
            return derivativeList.size();
        }

        @Override
        public MarketDataModel getItem(int position) {
            return derivativeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            Double perChange;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_market_equity_derivative, parent, false);
                holder.tvSymbol = convertView.findViewById(R.id.tvSymbol);
                holder.txtExpiryDate = convertView.findViewById(R.id.txtCompany);
                holder.tvLtp = convertView.findViewById(R.id.tvLtp);
                holder.tvVol = convertView.findViewById(R.id.tvVol);
                holder.tvChg = convertView.findViewById(R.id.tvChg);
                holder.dividerDerivativeLabel = convertView.findViewById(R.id.dividerDerivative);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            MarketDataModel model = getItem(position);
            String optiontype = "XX";

            if (model.getOptType().equals(optiontype)) {
                holder.tvSymbol.setText(String.format("%s - %s", model.getSymbol(), model.getInstName()));
            } else {
                holder.tvSymbol.setText(String.format("%s - %s (%s) ", model.getSymbol(), model.getInstName(), model.getOptType()));
            }
            if (Double.valueOf(model.getStrikePrice()) > 0) {
                holder.txtExpiryDate.setText(String.format("%s - (%s) ", DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "bse"), model.getStrikePrice()));
            } else {
                holder.txtExpiryDate.setText(String.format("%s", DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "bse")));
            }

            if (currentType.equals("future")) {
                holder.tvVol.setText(model.getVolume());
            } else {
                holder.tvVol.setText(model.getOi());
            }

            holder.tvLtp.setText(String.format("%.2f", Double.parseDouble(model.getLtp())));

            if (!model.getPerChange().equalsIgnoreCase("null")) {
                perChange = Double.valueOf(model.getPerChange());
                holder.tvChg.setText(String.format("%.2f%%", perChange));
                if (perChange < 0) {
                    holder.tvChg.setTextColor(getResources().getColor(R.color.red));
                } else {
                    holder.tvChg.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
                }
            } else {
                perChange = 0.00;
                holder.tvChg.setText(String.format("%.2f%%", perChange));
                holder.tvChg.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
            }

//            [TODO: SUSHANT]
            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvSymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvLtp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dividerDerivativeLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvVol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txtExpiryDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvSymbol.setTextColor(getResources().getColor(textColor));
                holder.tvLtp.setTextColor(getResources().getColor(textColor));
                holder.dividerDerivativeLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvVol.setTextColor(getResources().getColor(textColor));
                holder.txtExpiryDate.setTextColor(getResources().getColor(textColor));
            }
            return convertView;
        }

        public class Holder {
            ScrollingTextView tvSymbol, txtExpiryDate;
            GreekTextView tvChg, tvVol, tvLtp;
            View dividerDerivativeLabel;
        }
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
        super.onFragmentResume();
        if (EventBus.getDefault().isRegistered(this)) {
            sendStreamingRequest();
        } else {
            EventBus.getDefault().register(this);
            sendStreamingRequest();
        }
    }
}
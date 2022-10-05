package com.acumengroup.mobile.Dashboard;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.OpenInterestResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.markets.QuotesFragment;
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
import java.util.List;

import de.greenrobot.event.EventBus;

public class CurrencyBoardFragment extends GreekBaseFragment {

    private ListView listCommodityCurrency;
    private String currFilter = "";
    private CurrencyBoardFragment.CustomAdapter commonAdapter;
    private boolean isWaitingForRequest = false;
    /* For Top Header */
    private AlertDialog levelDialog;
    private MarketDataResponse marketResponse;
    private ArrayList streamingList;
    private RelativeLayout errorLayout;
    private View view;
    private final List<String> exchangelist = new ArrayList<>();
    private int maxSizeList = 5;
    private boolean isExpanded = false;


    private final AdapterView.OnItemClickListener marketMoversListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            LayoutInflater inflater = getMainActivity().getLayoutInflater();
            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            View layout = inflater.inflate(R.layout.alert_quick_three_actions, null);
            GreekTextView view1 = layout.findViewById(R.id.action_item2);
            view1.setText(R.string.POPUP_SCRIPT_TRADE);
            GreekTextView view2 = layout.findViewById(R.id.action_item1);
            view2.setText(R.string.POPUP_SCRIPT_QUOTE);
            GreekTextView view3 = layout.findViewById(R.id.action_item3);
            view3.setText(R.string.POPUP_SCRIPT_TRADE_SELL);

            view1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        moveToPage(position, "trade");
                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
                        levelDialog.dismiss();
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {

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
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
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

            builder.setView(layout);
            levelDialog = builder.create();
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }
        }
    };

    public CurrencyBoardFragment() {
        // Required empty public constructor
    }

    public void onEventMainThread(final DashboardAnimate dashboardAnimate) {

        if (dashboardAnimate.getPosition() == 1) {

            if (dashboardAnimate.isExpand()) {

                ViewGroup.LayoutParams params = listCommodityCurrency.getLayoutParams();
                params.height = AccountDetails.getFragHeight();
                listCommodityCurrency.setLayoutParams(params);
                listCommodityCurrency.requestLayout();

                isExpanded = true;
                updateAdapterSize();
                commonAdapter.notifyDataSetChanged();

            } else {

                ViewGroup.LayoutParams params = listCommodityCurrency.getLayoutParams();
                params.height = AccountDetails.getFragHeight() / 3;
                listCommodityCurrency.setLayoutParams(params);
                listCommodityCurrency.requestLayout();

                isExpanded = false;
                updateAdapterSize();
                commonAdapter.notifyDataSetChanged();

            }

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_commodity).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_commodity).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }


        listCommodityCurrency = view.findViewById(R.id.listMarketComodityCurency);
        listCommodityCurrency.setOnItemClickListener(marketMoversListener);

        errorLayout = view.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        listCommodityCurrency.setEmptyView(errorLayout);

        setupAdapter();
        loadCommCurrData();

        ViewGroup.LayoutParams params = listCommodityCurrency.getLayoutParams();
        params.height = 540 + 12;
        listCommodityCurrency.setLayoutParams(params);
        listCommodityCurrency.requestLayout();

        return view;
    }

    private void setupAdapter() {
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
        } else {
            exchangelist.add("CURRENCY");
        }

        commonAdapter = new CurrencyBoardFragment.CustomAdapter(getMainActivity(), new ArrayList<MarketDataModel>());
        listCommodityCurrency.setAdapter(commonAdapter);
    }


    private void loadCommCurrData() {
        final String currentAsset = "CURRENCY";
        String type;
        String service;
        String serviceURL;
        switch ("Top Gainers") {
            case "Top Gainers":
                type = "gainer";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_Mobile";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Top Losers":
                type = "loser";
                service = "getTopGainerAndLoserForCurrencyAndCommodity_Mobile";
                serviceURL = service + "?assetType=" + currentAsset.toLowerCase() + "&type=" + type;
                break;
            case "Most Active Contracts (Vol.)":
                type = "volume";
                service = "getMostActiveContracts_Mobile";
                serviceURL = service + "?type=" + currentAsset.toLowerCase();
                break;
            default:
                return;
        }
        currFilter = type;

        if (!isWaitingForRequest && serviceURL.length() > 0) {
            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            isWaitingForRequest = true;

            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    toggleErrorLayout(false);
                    refreshComplete();
                    hideProgress();
                    try {
                        streamingList = new ArrayList();
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);
                        updateAdapterSize();
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

    public void updateAdapterSize() {

        List<MarketDataModel> temp_list = new ArrayList<>();
        MarketDataModel marketDataModel = new MarketDataModel();

        if (marketResponse != null) {


            if (isExpanded) {

                maxSizeList = marketResponse.getMarketDataModelList().size();
            } else {
                commonAdapter.clear();
                if (marketResponse.getMarketDataModelList().size() > 5) {

                    maxSizeList = 5;
                } else {

                    maxSizeList = marketResponse.getMarketDataModelList().size();
                }

            }

            streamingList.clear();
            commonAdapter.clear();
            for (int i = 0; i < maxSizeList; i++) {

                marketDataModel = marketResponse.getMarketDataModelList().get(i);
                temp_list.add(marketDataModel);

                streamingList.add(marketResponse.getMarketDataModelList().get(i).getToken());
                commonAdapter.addSymbol(marketResponse.getMarketDataModelList().get(i).getToken());
            }

            commonAdapter.setData(temp_list);
            setListViewHeightBasedOnItems(listCommodityCurrency);
            /*if (getStreamingSymbolList("ltpinfo") != null) {
                if (getStreamingSymbolList("ltpinfo").size() > 0) {
                    streamController.pauseStreaming(getMainActivity(), "ltpinfo", getStreamingSymbolList("ltpinfo"));
                }
            }*/

            if(streamingList != null && streamingList.size() > 0)
            {
                streamController.pauseStreaming(getMainActivity(), "ltpinfo", streamingList);
            }
            sendStreamingRequest();
        }

    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        private List<MarketDataModel> commCurrList;
        ArrayList<String> tokenList;

        public CustomAdapter(Context context, List<MarketDataModel> commCurrList) {
            this.mContext = context;
            this.commCurrList = commCurrList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<MarketDataModel> commCurrList) {
            this.commCurrList = commCurrList;
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
            commCurrList.set(position, model);
        }

        public void clear() {
            this.commCurrList.clear();
            this.tokenList.clear();
        }

        @Override
        public int getCount() {
            return commCurrList.size();
        }

        @Override
        public MarketDataModel getItem(int position) {
            return commCurrList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CurrencyBoardFragment.CustomAdapter.Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.dashboard_row_market_, parent, false);
                holder = new CurrencyBoardFragment.CustomAdapter.Holder();

                holder.tvSymbol = convertView.findViewById(R.id.tvSymbol);
                holder.txtExpiryDate = convertView.findViewById(R.id.txtCompany);
                holder.tvLtp = convertView.findViewById(R.id.tvLtp);
                holder.tvVol = convertView.findViewById(R.id.tvVol); //OI or Volume
                holder.tvChg = convertView.findViewById(R.id.tvChg);


                convertView.setTag(holder);
            } else {
                holder = (CurrencyBoardFragment.CustomAdapter.Holder) convertView.getTag();
            }

            MarketDataModel model = getItem(position);
            String currentAsset = "CURRENCY";
            if (currentAsset.equals("CURRENCY")) {

                holder.txtExpiryDate.setText(String.format("%s", DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "bse")));
            } else {
                holder.tvSymbol.setText(model.getSymbol());
                holder.txtExpiryDate.setText(DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "bse"));
            }

            holder.tvLtp.setText(String.format("%.4f", Double.parseDouble(model.getLtp())));
            if (currFilter.equals("gainer") || currFilter.equals("loser")) {
                holder.tvVol.setText(model.getOi());
                holder.tvSymbol.setText(String.format("%s", model.getSymbol()));
            } else {
                holder.tvVol.setText(model.getVolume());
                holder.tvSymbol.setText(String.format("%s", model.getSymbol()) + "-" + String.format("%s", model.getinstrumentname()));
                holder.txtExpiryDate.setText(String.format("%s", DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "nse")));
            }

            Double perChange = Double.valueOf(model.getPerChange());
            holder.tvChg.setText(String.format("%.2f", perChange));

            int flashBluecolor, flashRedColor, textColorPositive, textColorNegative;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                flashBluecolor = R.drawable.light_blue_positive;
                flashRedColor = R.drawable.light_red_negative;
                textColorPositive = R.color.dark_blue_positive;
                textColorNegative = R.color.dark_red_negative;

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

            if (perChange < 0) {
                holder.tvChg.setTextColor(getResources().getColor(R.color.red));
                holder.tvLtp.setTextColor(getResources().getColor(textColorNegative));
                holder.tvLtp.setBackground(getResources().getDrawable(flashRedColor));

            } else {
                holder.tvChg.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
                holder.tvLtp.setTextColor(getResources().getColor(textColorPositive));
                holder.tvLtp.setBackground(getResources().getDrawable(flashBluecolor));
            }

//            [TODO: SUSHANT]
            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvSymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvVol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txtExpiryDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvSymbol.setTextColor(getResources().getColor(textColor));
                holder.tvVol.setTextColor(getResources().getColor(textColor));
                holder.txtExpiryDate.setTextColor(getResources().getColor(textColor));
            }

            return convertView;
        }

        public class Holder {
            ScrollingTextView tvSymbol, txtExpiryDate;
            GreekTextView tvChg, tvVol, tvLtp;
        }
    }

    private void sendStreamingRequest() {

        streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", streamingList);
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        if (commonAdapter.containsSymbol(response.getSymbol())) {
            ArrayList<Integer> changedCol = new ArrayList<>();
            MarketDataModel data = commonAdapter.getItem(commonAdapter.indexOf(response.getSymbol()));
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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }
        if (streamingList != null) {
            if (streamingList.size() > 0)
                sendStreamingRequest();
        }
    }

    @Override
    public void onFragmentPause() {
        if (streamingList.size() > 0)
            streamController.pauseStreaming(getActivity(), "ltpinfo", streamingList);
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
        updateAdapterSize();
        commonAdapter.notifyDataSetChanged();
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void refreshComplete() {
        hideProgress();
        isWaitingForRequest = false;
    }

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
                    args.putString(QuotesFragment.INST_TYPE, data.getinstrumentname());
                    args.putString(QuotesFragment.MULTIPLIER, data.getMultiply_factor());
                    args.putString(QuotesFragment.TICK_SIZE, data.getTickSize());
                    //For Currency
                    args.putString(QuotesFragment.EXPIRY, data.getExpiryDate());
                    args.putString(QuotesFragment.STRIKE_PRICE, data.getstrikeprice());
                    args.putString(QuotesFragment.OPTION_TYPE, data.getoptiontype());
                    args.putString(QuotesFragment.SELECTED_OPTION, data.getoptiontype());
                    navigateTo(NAV_TO_QUOTES_SCREEN, args, true);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

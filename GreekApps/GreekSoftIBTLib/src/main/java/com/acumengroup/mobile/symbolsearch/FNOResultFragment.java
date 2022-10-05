package com.acumengroup.mobile.symbolsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.chartiqscreen.MainActivity;
import com.acumengroup.mobile.markets.QuotesFragment;
import com.acumengroup.mobile.messaging.AlertFragment;
import com.acumengroup.mobile.model.NavigationModel;
import com.acumengroup.mobile.model.SearchPagerModel;
import com.acumengroup.mobile.portfolio.WatchListFragment;
import com.acumengroup.mobile.trade.RotateChartActivity;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */

public class FNOResultFragment extends GreekBaseFragment {
    public static ArrayList<SearchPagerModel> commonData;
    public static String commonExpiry = "All";
    private static GreekBaseFragment previousFragment;
    private ListView resultListView;
    private GreekTextView errorTextView;
    private FutureCustomAdapter futureCustomAdapter;
    private String pageSource = "";
    private String currentPage;

    public static FNOResultFragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        FNOResultFragment fragment = new FNOResultFragment();
        fragment.setArguments(args);
        FNOResultFragment.previousFragment = previousFragment;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ftPayInView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_fno_results).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_fno_results).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        if (!getArguments().get("Source").toString().equalsIgnoreCase("Watchlist")) {
            AccountDetails.currentFragment = NAV_TO_SYMBOL_SEARCH_SCREEN;
        }

        setupViews(ftPayInView);

        return ftPayInView;
    }

    private void setupViews(View parent) {
        if (getArguments().getString("Source") != null)
            pageSource = getArguments().getString("Source");
        currentPage = getArguments().getString("Type");

        errorTextView = parent.findViewById(R.id.errorHeader);
        resultListView = parent.findViewById(R.id.lv_future);
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchPagerModel data = futureCustomAdapter.getItem(position);
                sendAddSymbolRequest(data.getExchange(), data.getToken(), data.getAssetType(), data.getTradeSymbol());
                NavigationModel model = new NavigationModel();
                model.from(data);
                Bundle args = new Bundle();
                switch (pageSource) {
                    case "Trade":
                        args.clear();
                        args.putString(TradeFragment.SCRIP_NAME, model.getScripName());
                        args.putString(TradeFragment.EXCHANGE_NAME, model.getExchangeName());
                        args.putString(TradeFragment.TOKEN, model.getToken());
                        args.putString(TradeFragment.ASSET_TYPE, model.getAssetType());
                        args.putString(TradeFragment.UNIQUEID, model.getUniqueId());
                        args.putString(TradeFragment.TRADE_SYMBOL, model.getTradeSymbol());
                        args.putString(TradeFragment.LOT_QUANTITY, model.getLots());
                        args.putString(TradeFragment.TICK_SIZE, model.getTickSize());
                        args.putString("InstType", model.getInstType());
                        args.putString("OptType", data.getOptionType());
                        args.putString(TradeFragment.MULTIPLIER, model.getMultiplier());
                        args.putString("Expiry", model.getExpiry());
                        args.putString(TradeFragment.STRICKPRICE, model.getStrickPrice());
                        args.putString("addedSymbol", model.getTradeSymbol());
                        if (previousFragment != null) previousFragment.onFragmentResult(args);
                        goBackOnce();
                        break;
                    case "Quote":
                        args.clear();
                        args.putString(QuotesFragment.SCRIP_NAME, data.getScriptName());
                        args.putString(QuotesFragment.EXCHANGE_NAME, data.getExchange());
                        args.putString(QuotesFragment.ASSET_TYPE, data.getAssetType());
                        args.putString(QuotesFragment.UNIQUE_ID, data.getUniqueId());
                        args.putString(QuotesFragment.TRADE_SYMBOL, data.getTradeSymbol());
                        args.putString("Lots", data.getLotQty());
                        args.putString(QuotesFragment.TICK_SIZE, data.getTickSize());
                        args.putString(QuotesFragment.INST_TYPE, data.getInstrumentName());
                        args.putString(QuotesFragment.MULTIPLIER, data.getMultiplier());
                        args.putString(QuotesFragment.TOKEN, data.getToken());
                        if (currentPage.equals("Call") || currentPage.equals("Put")) {
                            args.putString(QuotesFragment.OPTION_TYPE, currentPage);
                            args.putString(QuotesFragment.SELECTED_OPTION, currentPage);
                        }
                        args.putString("SelectedExp", data.getExpiryDate());
                        args.putString(QuotesFragment.EXPIRY, data.getExpiryDate());
                        args.putString(QuotesFragment.STRIKE_PRICE, data.getStrickPrice());
                        args.putString(QuotesFragment.FROM_PAGE, "SymbolSearch");
                        navigateTo(NAV_TO_QUOTES_SCREEN, args, true);
                        break;
                    case "Watchlist":
                        args.clear();
                        args.putString(WatchListFragment.EXCHANGE_NAME, model.getExchangeName());
                        args.putString(WatchListFragment.TRADE_SYMBOL, model.getTradeSymbol());
                        args.putString(WatchListFragment.ASSET_TYPE, model.getAssetType());
                        args.putString(WatchListFragment.ADDED_SYMBOL, model.getTradeSymbol());
                        args.putString(WatchListFragment.TOKEN, model.getToken());
                        args.putString(WatchListFragment.GOTO, "Watchlist");
                        if (previousFragment != null) previousFragment.onFragmentResult(args);
                        goBackOnce();
                        break;
                    case "Alert":
                        args.clear();
                        args.putString(AlertFragment.SCRIP_NAME, model.getScripName());
                        args.putString(AlertFragment.TOKEN, model.getToken());
                        args.putString(AlertFragment.ASSET_TYPE, model.getAssetType());
                        args.putString(AlertFragment.FROM_PAGE, "alerts");

                        if (previousFragment != null)
                            previousFragment.onFragmentResult(args);
                        goBackOnce();

                        break;
                }
            }
        });
        futureCustomAdapter = new FutureCustomAdapter(getMainActivity(), new ArrayList<SearchPagerModel>());
        resultListView.setAdapter(futureCustomAdapter);
    }

    public void setDataSet(String scrip) {
        futureCustomAdapter.clear();
        String[] instrumentFilter;
        String optionType;
        if ("Futures".equals(currentPage)) {
            instrumentFilter = new String[]{"FUTCUR", "FUTSTK", "FUTIDX", "FUTIVX", "FUTCOM", "FUTIRC", "FUTIRT", "FUTIRD"};
        } else {
            instrumentFilter = new String[]{"OPTCUR", "OPTSTK", "OPTIDX", "OPTIVX", "OPTCOM", "OPTFUT"};
        }
        if ("Call".equals(currentPage)) {
            optionType = "CE";
        } else if ("Put".equals(currentPage)) {
            optionType = "PE";
        } else {
            optionType = "XX";
        }
        List<String> temp = Arrays.asList(instrumentFilter);
        futureCustomAdapter.clear();
        if (commonData != null && commonData.size() > 0) {
            if (commonData.get(0).getScriptName().equalsIgnoreCase(scrip)) {
                errorTextView.setVisibility(View.GONE);
                resultListView.setVisibility(View.VISIBLE);
                for (SearchPagerModel single : commonData) {
                    //if()
                    if (commonExpiry.equals(single.getExpiryDate())) {
                        if (temp.contains(single.getInstrumentName())) {
                            if (single.getOptionType().equalsIgnoreCase(optionType)) {
                                futureCustomAdapter.add(single);
                            }
                        }
                    } else if (commonExpiry.equals("All")) {
                        if (temp.contains(single.getSeries())) {
                            if (single.getOptionType().equalsIgnoreCase(optionType)) {
                                futureCustomAdapter.add(single);
                            }
                        }
                    }
                }
            }
        } else {
            errorTextView.setText("No SymbolName");
            errorTextView.setVisibility(View.VISIBLE);
            resultListView.setVisibility(View.GONE);
        }
        futureCustomAdapter.notifyDataSetChanged();
    }

    private void sendAddSymbolRequest(String exchange, String token, String assetType, String scripName) {

        String addSymbolURL = "getPreviousViewedScripBySymbol?ClientCode=" + AccountDetails.getClientCode(getMainActivity())
                + "&ExchangeCode=" + exchange +
                "&Token=" + token +
                "&Segment=" + assetType.toLowerCase() +
                "&Symbol=" + scripName +
                "&gscid=" + AccountDetails.getUsername(getMainActivity());

        WSHandler.getRequest(getMainActivity(), addSymbolURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
            }

            @Override
            public void onFailure(String message) {

            }

        });
    }

    public class FutureCustomAdapter extends BaseAdapter {
        private final ArrayList<SearchPagerModel> arrayList;
        private LayoutInflater inflater = null;

        public FutureCustomAdapter(Context context, ArrayList<SearchPagerModel> arrayList) {
            this.inflater = LayoutInflater.from(context);
            this.arrayList = arrayList;
        }

        public void clear() {
            arrayList.clear();
        }

        public void add(SearchPagerModel row) {
            arrayList.add(row);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public SearchPagerModel getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_symbol_search_fno, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            final SearchPagerModel data = getItem(position);
            holder.tvName.setText(data.getScriptName());
            holder.tvExpiryDate.setText(data.getExpiryDate());
            holder.tvStrikePrice.setText(data.getStrickPrice());

            holder.sellTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, data.getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, data.getExchange());
                    args2.putString(TradeFragment.TOKEN, data.getToken());
                    args2.putString(TradeFragment.TICK_SIZE, data.getTickSize());
                    args2.putString(TradeFragment.TRADE_ACTION, "Sell");
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");

                }
            });
            holder.buyTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle args2 = new Bundle();
                    args2.putString(TradeFragment.SCRIP_NAME, data.getName());
                    args2.putString(TradeFragment.EXCHANGE_NAME, data.getExchange());
                    args2.putString(TradeFragment.TOKEN, data.getToken());
                    args2.putString(TradeFragment.TICK_SIZE, data.getTickSize());
                    args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                    AccountDetails.globalPlaceOrderBundle = args2;
                    EventBus.getDefault().post("placeorder");
                }
            });
            holder.chartTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("Scrip", data.getDescription() + " " + getExchange(data.getToken()) + " |");
                    bundle.putString("Token", data.getToken());
                    bundle.putString("TradeSymbol", AccountDetails.globalPlaceOrderBundle.getString("TradeSymbol"));
                    bundle.putString("description", "");
//                  bundle.putString("ltp", data.getLast());
                    bundle.putString("Lots", AccountDetails.globalPlaceOrderBundle.getString("Lots"));
                    bundle.putString("Action", "1");
                    bundle.putString("AssetType", AccountDetails.globalPlaceOrderBundle.getString("AssetType"));
                    bundle.putString("from", "placeorderchart");
                    bundle.putBoolean("iscurrency", false);
                    AccountDetails.setIsMainActivity(true);
                    if (AccountDetails.getChartSetting().equalsIgnoreCase("m4")) {
                        Intent rotatechart = new Intent(getActivity(), RotateChartActivity.class);
                        rotatechart.putExtras(bundle);
                        startActivity(rotatechart);
                    } else {
                        Intent rotatechart = new Intent(getActivity(), MainActivity.class);
                        rotatechart.putExtras(bundle);
                        startActivity(rotatechart);
                    }


                }
            });


            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvExpiryDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvStrikePrice.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvName.setTextColor(getResources().getColor(textColor));
                holder.tvExpiryDate.setTextColor(getResources().getColor(textColor));
                holder.tvStrikePrice.setTextColor(getResources().getColor(textColor));
            }

            return convertView;
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

        public class Holder {
            final GreekTextView tvName;
            final GreekTextView tvExpiryDate;
            final GreekTextView tvStrikePrice;
            final GreekTextView buyTxt, sellTxt, chartTxt;

            public Holder(View parent) {
                this.tvName = parent.findViewById(R.id.tvName);
                this.tvExpiryDate = parent.findViewById(R.id.tvExpiryDate);
                this.tvStrikePrice = parent.findViewById(R.id.tvStrikePrice);
                this.buyTxt = parent.findViewById(R.id.buyTxt);
                this.sellTxt = parent.findViewById(R.id.sellTxt);
                this.chartTxt = parent.findViewById(R.id.chartTxt);
            }
        }

    }
}

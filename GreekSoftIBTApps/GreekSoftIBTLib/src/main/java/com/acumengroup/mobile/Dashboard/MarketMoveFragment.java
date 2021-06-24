package com.acumengroup.mobile.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
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
import com.acumengroup.greekmain.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.Dashboard.DashBoardFragment.requestTimer;

public class MarketMoveFragment extends GreekBaseFragment implements View.OnClickListener {


    boolean isExpanded = false;
    private RelativeLayout errorLayout;
    private boolean isWaitingForRequest = false;
    private ImageButton collapse;
    private ListView listMarketEquity;
    private String currentType = "";
    private ArrayList streamingList = new ArrayList();
    private MarketMoveFragment.TopGainerAdapter gainerAdapter;
    private MarketDataResponse marketResponse;
    StreamingController streamController = new StreamingController();
    private int maxSizeList = 2;
    private GreekTextView filtersSpinner;
    private AlertDialog levelDialog;
    private static RelativeLayout parent_layout;
    private RadioGroup mm_radio_group;
    private RadioButton tg_radiio, tl_radio, ma_vo_radio, ma_va_radio, rb;
    private GreekTextView dialog_label_exchange;


    private final AdapterView.OnItemClickListener marketMoversListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            LayoutInflater inflater = getMainActivity().getLayoutInflater();
            View layout = inflater.inflate(R.layout.alert_quick_three_actions, parent, false);
            GreekTextView view2 = layout.findViewById(R.id.action_item2);
            view2.setText(R.string.POPUP_SCRIPT_TRADE);
            GreekTextView view1 = layout.findViewById(R.id.action_item1);
            view1.setText(R.string.POPUP_SCRIPT_QUOTE);
            GreekTextView view3 = layout.findViewById(R.id.action_item3);
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

    public MarketMoveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void RequestMethod() {
        getMainActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.e("MarketMoveFragment", "Run Request Method");
                loadTopGainerLooserList();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parent = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_market_move).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_market_move).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        collapse = parent.findViewById(R.id.collapsed);
        parent_layout = parent.findViewById(R.id.parent_layout);


/*        requestTimer = new Timer();
        requestTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                RequestMethod();

            }
        }, 0, 1000 * 5 * 60);*/

        collapse.setOnClickListener(this);
        filtersSpinner = parent.findViewById(R.id.filtersSpinner);
        listMarketEquity = parent.findViewById(R.id.listMarketEquity);

        errorLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");

        int pre_checkedId = Util.getPrefs(getMainActivity()).getInt("MARKET_MOVERS_CHECKED_ID", 0);

        if (pre_checkedId == R.id.tl_radio) {

            filtersSpinner.setText("Top Losers");

        } else if (pre_checkedId == R.id.tg_radiio) {

            filtersSpinner.setText("Top Gainers");

        } else if (pre_checkedId == R.id.ma_vo_radio) {

            filtersSpinner.setText("Most Active (Vol.)");

        } else if (pre_checkedId == R.id.ma_va_radio) {

            filtersSpinner.setText("Most Active (Val.)");

        } else {

            filtersSpinner.setText("Top Gainers");

        }

        ViewGroup.LayoutParams params = parent_layout.getLayoutParams();
        params.height = 460;
        parent_layout.setLayoutParams(params);
        parent_layout.requestLayout();

        setupAdapter();
        sendContentRequest();

        return parent;
    }

    private void setupAdapter() {

        ArrayAdapter<String> col1Adapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(),
                Arrays.asList(getResources().getStringArray(R.array.marketFilterEquity)));
        col1Adapter.setDropDownViewResource(R.layout.custom_spinner);
        filtersSpinner.setOnClickListener(this);

        gainerAdapter = new MarketMoveFragment.TopGainerAdapter(getMainActivity(), new ArrayList<MarketDataModel>());
        listMarketEquity.setAdapter(gainerAdapter);

    }


    private void loadTopGainerLooserList() {

        String exchangeStr;

        boolean pre_bse_checked = Util.getPrefs(getMainActivity()).getBoolean("MARKET_MOVERS_BSE_CHECKED", false);

        if (pre_bse_checked) {
            exchangeStr = "BSE";

        } else {
            exchangeStr = "NSE";

        }
        exchangeStr = exchangeStr.toLowerCase();

        String filtersStr = filtersSpinner.getText().toString();
        String type;
        String service;
        String serviceURL;

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
                break;
            case "Most Active (Val.)":
                type = "value";
                service = "getMostActiveEquityByValue_Mobile";
                serviceURL = service + "?exchange=" + exchangeStr;
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
        if ( serviceURL.length() > 0) {
            gainerAdapter.clear();
            //gainerAdapter.notifyDataSetChanged();
            isWaitingForRequest = true;

            gainerAdapter.clear();
            marketResponse = null;

            final DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
            dashBoardCommunicate.setShowProgress(true);
            EventBus.getDefault().post(dashBoardCommunicate);

            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    toggleErrorLayout(false);

                    dashBoardCommunicate.setShowProgress(false);
                    EventBus.getDefault().post(dashBoardCommunicate);


                    try {
                        streamingList.clear();
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);

                        updateAdapterSize();
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

                    dashBoardCommunicate.setShowProgress(false);
                    EventBus.getDefault().post(dashBoardCommunicate);

                }
            });
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
            MarketMoveFragment.TopGainerAdapter.Holder holder;
            Activity activity = getActivity();
            if (activity != null && isAdded()) {
                if (convertView == null) {
                    holder = new MarketMoveFragment.TopGainerAdapter.Holder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.row_items_layout, parent, false);

                    holder.tvSymbol = convertView.findViewById(R.id.tvSymbol);
                    holder.tvLtp = convertView.findViewById(R.id.tvLtp);
                    holder.tvVol = convertView.findViewById(R.id.tvVol);
                    holder.tvPerChange = convertView.findViewById(R.id.tvChg);
                    holder.upDownArrow = convertView.findViewById(R.id.updownArrow);
                    holder.dividerEquityLabel = convertView.findViewById(R.id.dividerDerivative);
                    holder.linearLayout = convertView.findViewById(R.id.linearParent);
                    convertView.setTag(holder);
                } else {
                    holder = (MarketMoveFragment.TopGainerAdapter.Holder) convertView.getTag();
                }

                MarketDataModel model = getItem(position);
                holder.tvSymbol.setText(model.getSymbol());
                holder.tvLtp.setText(String.format("%.2f", Double.parseDouble(model.getLtp())));

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

                if (model.getPerChange().equals("null")) {
                    perChange = 0.0;
                } else {
                    perChange = Double.valueOf(model.getPerChange());
                }
                holder.tvPerChange.setText(String.format("%.2f%%", perChange));


                int flashBluecolor, flashRedColor, textColorPositive, textColorNegative,arrow_image;

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    flashBluecolor = R.drawable.light_blue_positive;
                    flashRedColor = R.drawable.light_red_negative;


                    textColorPositive = R.color.dark_blue_positive;
                    textColorNegative = R.color.dark_red_negative;
                    arrow_image = R.drawable.up_arrow;

                } else {

                    flashBluecolor = R.drawable.light_green_textcolor;
                    flashRedColor = R.drawable.dark_red_negative;
                    if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        textColorPositive = R.color.whitetheambuyColor;
                    }else {
                        textColorPositive = R.color.dark_green_positive;
                    }
                    textColorNegative = R.color.dark_red_negative;
                    arrow_image = R.drawable.up_arrow_green;
                }

                if (perChange < 0) {

                    holder.tvLtp.setTextColor(mContext.getResources().getColor(textColorNegative));
                    holder.tvLtp.setBackground(mContext.getResources().getDrawable(flashRedColor));

                    holder.tvVol.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.tvPerChange.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.upDownArrow.setImageResource(R.drawable.down_arrow_red);

                } else {
                    holder.tvLtp.setTextColor(mContext.getResources().getColor(textColorPositive));
                    holder.tvLtp.setBackground(mContext.getResources().getDrawable(flashBluecolor));

                    holder.tvVol.setTextColor(mContext.getResources().getColor(AccountDetails.textColorBlue));
                    holder.tvPerChange.setTextColor(mContext.getResources().getColor(AccountDetails.textColorBlue));
                    holder.upDownArrow.setImageResource(arrow_image);
                }
//          [TODO: SUSHANT]

                int textColor = AccountDetails.getTextColorDropdown();


                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                    holder.tvSymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    holder.dividerEquityLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

                } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                    convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                    holder.tvSymbol.setTextColor(getResources().getColor(textColor));
                    holder.dividerEquityLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));
                }

                return convertView;
            }
            return null;
        }

        public class Holder {
            GreekTextView tvSymbol;
            GreekTextView tvLtp, tvVol, tvPerChange;
            ImageView upDownArrow;
            View dividerEquityLabel;
            LinearLayout linearLayout;
        }
    }


    @Override
    public void onClick(View view) {

        if (R.id.filtersSpinner == view.getId()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

            LayoutInflater inflater = getMainActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_dashboard_layout, null);
            SwitchCompat switchOne = dialogView.findViewById(R.id.switch_one);

            mm_radio_group = dialogView.findViewById(R.id.mm_radio_group);
            tl_radio = dialogView.findViewById(R.id.tl_radio);
            tg_radiio = dialogView.findViewById(R.id.tg_radiio);
            ma_va_radio = dialogView.findViewById(R.id.ma_va_radio);
            ma_vo_radio = dialogView.findViewById(R.id.ma_vo_radio);
            dialog_label_exchange = dialogView.findViewById(R.id.label_exchange);


            boolean pre_bse_checked = Util.getPrefs(getMainActivity()).getBoolean("MARKET_MOVERS_BSE_CHECKED", false);

            if (pre_bse_checked) {
                dialog_label_exchange.setText("BSE");
                switchOne.setChecked(true);

            } else {
                dialog_label_exchange.setText("NSE");
                switchOne.setChecked(false);
            }

            int pre_checkedId = Util.getPrefs(getMainActivity()).getInt("MARKET_MOVERS_CHECKED_ID", 0);

            if (pre_checkedId == R.id.tl_radio) {
                tl_radio.setChecked(true);

            } else if (pre_checkedId == R.id.tg_radiio) {

                tg_radiio.setChecked(true);

            } else if (pre_checkedId == R.id.ma_va_radio) {
                ma_va_radio.setChecked(true);


            } else if (pre_checkedId == R.id.ma_vo_radio) {
                ma_vo_radio.setChecked(true);

            } else {

                tg_radiio.setChecked(true);
            }

            mm_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    rb = group.findViewById(checkedId);

                    if (rb != null) {
                        filtersSpinner.setText(rb.getText().toString());

                    }

                    Util.getPrefs(getMainActivity()).edit().putInt("MARKET_MOVERS_CHECKED_ID", checkedId).commit();

                    loadTopGainerLooserList();
                    levelDialog.dismiss();

                }
            });

            switchOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        dialog_label_exchange.setText("BSE");
                        Util.getPrefs(getMainActivity()).edit().putBoolean("MARKET_MOVERS_BSE_CHECKED", true).commit();

                    } else {

                        dialog_label_exchange.setText("NSE");
                        Util.getPrefs(getMainActivity()).edit().putBoolean("MARKET_MOVERS_BSE_CHECKED", false).commit();
                    }

                    loadTopGainerLooserList();
                    levelDialog.dismiss();
                }
            });

            builder.setView(dialogView);

            levelDialog = builder.create();
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }

        } else if (R.id.collapsed == view.getId()) {

            if (!isExpanded) {

                ViewGroup.LayoutParams params = parent_layout.getLayoutParams();
                params.height = AccountDetails.getFragHeight();
                parent_layout.setLayoutParams(params);
                parent_layout.requestLayout();

                isExpanded = true;
                updateAdapterSize();
                gainerAdapter.notifyDataSetChanged();

                collapse.setImageResource(R.drawable.ic_collapse);
                DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
                dashBoardCommunicate.setName("Market");
                dashBoardCommunicate.setPosition(2);
                dashBoardCommunicate.setExpand(true);
                EventBus.getDefault().post(dashBoardCommunicate);

            } else {

                ViewGroup.LayoutParams params = parent_layout.getLayoutParams();
                params.height = 460;
                parent_layout.setLayoutParams(params);
                parent_layout.requestLayout();

                isExpanded = false;
                updateAdapterSize();
                gainerAdapter.notifyDataSetChanged();

                collapse.setImageResource(R.drawable.ic_expand);
                DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
                dashBoardCommunicate.setName("Market");
                dashBoardCommunicate.setPosition(2);
                dashBoardCommunicate.setListview_height(getHeightListview());
                dashBoardCommunicate.setExpand(false);
                EventBus.getDefault().post(dashBoardCommunicate);

            }
        }

    }

    public void updateAdapterSize() {
        List<MarketDataModel> temp_list = new ArrayList<>();
        MarketDataModel marketDataModel = new MarketDataModel();

        if (marketResponse != null) {

            if (isExpanded) {

                maxSizeList = marketResponse.getMarketDataModelList().size();

            } else {

                if (marketResponse.getMarketDataModelList().size() > 3) {

                    maxSizeList = 3;
                } else {
                    maxSizeList = marketResponse.getMarketDataModelList().size();
                }
            }

            streamingList.clear();
            gainerAdapter.clear();

            for (int i = 0; i < maxSizeList; i++) {
                marketDataModel = marketResponse.getMarketDataModelList().get(i);
                temp_list.add(marketDataModel);

                streamingList.add(marketResponse.getMarketDataModelList().get(i).getToken());
                AccountDetails.dashboardList.add(marketResponse.getMarketDataModelList().get(i).getToken());
                gainerAdapter.addSymbol(marketResponse.getMarketDataModelList().get(i).getToken());

            }
            gainerAdapter.setData(temp_list);
            gainerAdapter.notifyDataSetChanged();
            if (gainerAdapter.getCount() > 0)
                setListViewHeightBasedOnItems(listMarketEquity);


            if (streamingList != null) {
                if (streamingList.size() > 0) {
                    streamController.pauseStreaming(getMainActivity(), "ltpinfo", streamingList);
                }
            }
            sendStreamingRequest();


        }
    }

    public boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
       // ListAdapter listAdapter = gainerAdapter;

        if (gainerAdapter != null) {

            int numberOfItems = gainerAdapter.getCount();

            if(numberOfItems > 0) {
                // Get total height of all items.
                int totalItemsHeight = 0;
               /* for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                    View item = gainerAdapter.getView(itemPos, null, listView);
                    if (item != null) {
                        item.measure(0, 0);
                        totalItemsHeight += item.getMeasuredHeight();
                    }
                }*/

               if(AccountDetails.getListHeightValue() != 0)
               {
                   totalItemsHeight = AccountDetails.getListHeightValue() * gainerAdapter.getCount();
               }
               else {

                   for (int i = 0; i < numberOfItems; i++) {
                       View item = gainerAdapter.getView(i, null, listView);
                       if (item != null) {
                           item.measure(0, 0);
                           AccountDetails.setListHeightValue(item.getMeasuredHeight());
                           totalItemsHeight += item.getMeasuredHeight();
                       }
                   }
               }
                // Get total height of all item dividers.
                int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);

                // Set list height.
            /*ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();*/

                if (totalItemsHeight + totalDividersHeight > 0) {

                    ViewGroup.LayoutParams params2 = parent_layout.getLayoutParams();
                    params2.height = totalItemsHeight + totalDividersHeight;
                    //params2.height = 0;
                    parent_layout.setLayoutParams(params2);
                    parent_layout.requestLayout();
                }
            }
            return true;

        } else {
            return false;
        }

    }

    public int getHeightListview() {
        int height = 0;
        for (int i = 0; i < listMarketEquity.getChildCount(); i++) {
            height += listMarketEquity.getChildAt(i).getMeasuredHeight();
            height += listMarketEquity.getDividerHeight();

        }

        return height;
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

    private void sendContentRequest() {
        loadTopGainerLooserList();
    }

    private void sendStreamingRequest() {
        streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", streamingList);
    }

    private void refreshComplete() {
        hideProgress();
        isWaitingForRequest = false;
    }

    @Override
    public void onPause() {
        if (streamingList != null) {
            if (streamingList.size() > 0)
                streamController.pauseStreaming(getActivity(), "ltpinfo", streamingList);
        }
        EventBus.getDefault().unregister(this);
        if(requestTimer != null) {
            requestTimer.cancel();
            requestTimer = null;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(AccountDetails.currentFragment == NAV_TO_MARKET_HOME_SCREEN) {
            EventBus.getDefault().register(this);
            if (streamingList != null) {
                if (streamingList.size() > 0)
                    sendStreamingRequest();
            }
            //loadTopGainerLooserList();

            if(requestTimer != null)
            {
                requestTimer.cancel();
            }
            requestTimer = new Timer();
            requestTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    RequestMethod();

                }
            }, 0, 1000 * 60 * 5);
        }
    }

    @Override
    public void onFragmentPause() {
        if (streamingList.size() > 0)
            streamController.pauseStreaming(getActivity(), "ltpinfo", streamingList);
        EventBus.getDefault().unregister(this);
        if(requestTimer != null) {
            requestTimer.cancel();
            requestTimer = null;
        }
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

        if (gainerAdapter != null) {
            updateAdapterSize();
            gainerAdapter.notifyDataSetChanged();
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

    public void onEventMainThread(String refresh)
    {
        if(refresh.equalsIgnoreCase("refresh"))
        {

        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        try {

            if (gainerAdapter.containsSymbol(response.getSymbol())) {
                boolean notifyNeeded = false;

                MarketDataModel data = gainerAdapter.getItem(gainerAdapter.indexOf(response.getSymbol()));

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

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}

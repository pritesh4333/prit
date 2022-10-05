package com.acumengroup.mobile.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.Dashboard.DashBoardFragment.derivative_Timer;

public class DerivativesFragment extends GreekBaseFragment {

    boolean isExpanded = false;
    private GreekTextView filtersSpinner;
    private DerivativesFragment.DerivativeAdapter commonAdapter;
    private MarketDataResponse marketResponse;
    private boolean isWaitingForRequest = false;
    private String currentType = "";
    private RelativeLayout errorLayout;
    private ArrayList streamingList;
    private ImageButton collapse;
    private int maxSizeList = 2;
    private AlertDialog levelDialog;
    private ListView listMarketDerivative;
    private static RelativeLayout parent_layout;
    private RadioGroup derivative_radiogroup;
    private RadioButton tl_radio, tg_radiio, ma_io_radio, ma_fc_radio, ma_sp_radio, rb;
    private GreekTextView dialog_label_exchange;

    public DerivativesFragment() {
        // Required empty public constructor
    }

    private void RequestMethod() {
        getMainActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.e("DerivativesFragment", "Run Request Method");
                loadDerivativeData();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private final AdapterView.OnItemClickListener marketMoversListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            LayoutInflater inflater = getMainActivity().getLayoutInflater();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            builder.setView(layout);
            levelDialog = builder.create();
            if (!levelDialog.isShowing()) {
                levelDialog.show();
            }
        }
    };

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // if (AccountDetails.marketDerivative)
            loadDerivativeData();
        }
    };

    private final AdapterView.OnItemSelectedListener filterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //if (AccountDetails.marketDerivative)
            loadDerivativeData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_derivatives).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_derivatives).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

      /*  derivative_Timer = new Timer();
        derivative_Timer.schedule(new TimerTask() {
            @Override
            public void run() {

                RequestMethod();

            }
        }, 0, 1000 * 5 * 60);*/


        parent_layout = view.findViewById(R.id.parent_layout);

        filtersSpinner = view.findViewById(R.id.filtersSpinner);
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(getMainActivity(),
                AccountDetails.getRowSpinnerSimple(), Arrays.asList(getResources().getStringArray(R.array.marketFilterDerivative)));
        filterAdapter.setDropDownViewResource(R.layout.custom_spinner);
        int pre_checkedId = Util.getPrefs(getMainActivity()).getInt("DERIVATIVES_CHECKED_ID", 0);

        if (pre_checkedId == R.id.tl_radio) {

            filtersSpinner.setText("Top Losers - Futures");

        } else if (pre_checkedId == R.id.tg_radiio) {

            filtersSpinner.setText("Top Gainers - Futures");

        } else if (pre_checkedId == R.id.ma_io_radio) {

            filtersSpinner.setText("Most Active Index Option contracts (Vol.)");

        } else if (pre_checkedId == R.id.ma_fc_radio) {

            filtersSpinner.setText("Most Active Futures contracts (Vol.)");

        } else if (pre_checkedId == R.id.ma_sp_radio) {
            filtersSpinner.setText("Most Active Stock Option contracts (Vol.)");

        } else {

            filtersSpinner.setText("Top Gainers - Futures");
        }

        filtersSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] filter = new CharSequence[]{
                        "Top Gainers - Futures",
                        "Top Losers - Futures",
                        "Most Active Futures contracts (Vol.)",
                        "Most Active Index Option contracts (Vol.)",
                        "Most Active Stock Option contracts (Vol.)"};


                final AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                LayoutInflater inflater = getMainActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_derivatives_layout, null);
                SwitchCompat switchOne = dialogView.findViewById(R.id.switch_one);

                derivative_radiogroup = dialogView.findViewById(R.id.derivative_radiogroup);
                tl_radio = dialogView.findViewById(R.id.tl_radio);
                tg_radiio = dialogView.findViewById(R.id.tg_radiio);
                ma_io_radio = dialogView.findViewById(R.id.ma_io_radio);
                ma_fc_radio = dialogView.findViewById(R.id.ma_fc_radio);
                ma_sp_radio = dialogView.findViewById(R.id.ma_sp_radio);
                dialog_label_exchange = dialogView.findViewById(R.id.label_exchange);


                boolean pre_bse_checked = Util.getPrefs(getMainActivity()).getBoolean("DERIVATIVES_BSE_CHECKED", false);

                if (pre_bse_checked) {
                    dialog_label_exchange.setText("BSE");
                    switchOne.setChecked(true);

                } else {
                    dialog_label_exchange.setText("NSE");
                    switchOne.setChecked(false);
                }

                int pre_checkedId = Util.getPrefs(getMainActivity()).getInt("DERIVATIVES_CHECKED_ID", 0);

                if (pre_checkedId == R.id.tl_radio) {

                    tl_radio.setChecked(true);

                } else if (pre_checkedId == R.id.tg_radiio) {

                    tg_radiio.setChecked(true);

                } else if (pre_checkedId == R.id.ma_io_radio) {

                    ma_io_radio.setChecked(true);

                } else if (pre_checkedId == R.id.ma_fc_radio) {

                    ma_fc_radio.setChecked(true);

                } else if (pre_checkedId == R.id.ma_sp_radio) {
                    ma_sp_radio.setChecked(true);

                } else {

                    tg_radiio.setChecked(true);
                }

                derivative_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        rb = group.findViewById(checkedId);

                        if (rb != null) {
                            filtersSpinner.setText(rb.getText().toString());
                        }

                        Util.getPrefs(getMainActivity()).edit().putInt("DERIVATIVES_CHECKED_ID", checkedId).commit();

                        loadDerivativeData();
                        levelDialog.dismiss();


                    }
                });

                switchOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            dialog_label_exchange.setText("BSE");
                            Util.getPrefs(getMainActivity()).edit().putBoolean("DERIVATIVES_BSE_CHECKED", true).commit();

                        } else {

                            dialog_label_exchange.setText("NSE");
                            Util.getPrefs(getMainActivity()).edit().putBoolean("DERIVATIVES_BSE_CHECKED", false).commit();
                        }

                        loadDerivativeData();
                        levelDialog.dismiss();
                    }
                });

                builder.setView(dialogView);

                levelDialog = builder.create();
                if (!levelDialog.isShowing()) {
                    levelDialog.show();
                }
            }
        });

        collapse = view.findViewById(R.id.collapsed);

        listMarketDerivative = view.findViewById(R.id.listMarketDerivative);
        commonAdapter = new DerivativesFragment.DerivativeAdapter(getMainActivity(), new ArrayList<MarketDataModel>());
        listMarketDerivative.setAdapter(commonAdapter);
        listMarketDerivative.setOnItemClickListener(marketMoversListener);
        loadDerivativeData();

        errorLayout = view.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        RelativeLayout.LayoutParams lph = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        ViewGroup.LayoutParams params = parent_layout.getLayoutParams();
        params.height = 460;
        parent_layout.setLayoutParams(params);
        parent_layout.requestLayout();


        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpanded) {

                    ViewGroup.LayoutParams params = parent_layout.getLayoutParams();
                    params.height = AccountDetails.getFragHeight();
                    parent_layout.setLayoutParams(params);
                    parent_layout.requestLayout();

                    isExpanded = true;
                    updateAdapterSize();
                    commonAdapter.notifyDataSetChanged();
                    collapse.setImageResource(R.drawable.ic_collapse);


                    DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
                    dashBoardCommunicate.setName("Derivative");
                    dashBoardCommunicate.setPosition(3);
                    dashBoardCommunicate.setExpand(true);
                    EventBus.getDefault().post(dashBoardCommunicate);

                } else {

                    ViewGroup.LayoutParams params = parent_layout.getLayoutParams();
                    params.height = 460;
                    parent_layout.setLayoutParams(params);
                    parent_layout.requestLayout();

                    isExpanded = false;
                    updateAdapterSize();
                    commonAdapter.notifyDataSetChanged();
                    collapse.setImageResource(R.drawable.ic_expand);

                    DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
                    dashBoardCommunicate.setName("Derivative");
                    dashBoardCommunicate.setPosition(3);
                    dashBoardCommunicate.setExpand(false);
                    EventBus.getDefault().post(dashBoardCommunicate);

                }

            }
        });


        return view;
    }

    private void loadDerivativeData() {

        String exchangeStr;

        boolean pre_bse_checked = Util.getPrefs(getMainActivity()).getBoolean("DERIVATIVES_BSE_CHECKED", false);

        if (pre_bse_checked) {

            exchangeStr = "BSE";

        } else {
            exchangeStr = "NSE";

        }
        exchangeStr = exchangeStr.toLowerCase();

        String service;
        String type;
        switch (filtersSpinner.getText().toString()) {
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

        if (!isWaitingForRequest && type.length() > 0 && service.length() > 0) {
            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            isWaitingForRequest = true;

            commonAdapter.clear();
            marketResponse = null;

            final DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
            dashBoardCommunicate.setShowProgress(true);
            EventBus.getDefault().post(dashBoardCommunicate);

            WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&type=" + type, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    toggleErrorLayout(false);
                    refreshComplete();

                    dashBoardCommunicate.setShowProgress(false);
                    EventBus.getDefault().post(dashBoardCommunicate);

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

                    dashBoardCommunicate.setShowProgress(false);
                    EventBus.getDefault().post(dashBoardCommunicate);
                }
            });
        }
    }

    public void updateAdapterSize() {

        List<MarketDataModel> temp_list = new ArrayList<>();
        MarketDataModel marketDataModel = new MarketDataModel();

        if (marketResponse != null) {

            if (isExpanded) {
                commonAdapter.clear();
                maxSizeList = marketResponse.getMarketDataModelList().size();
            } else {
                commonAdapter.clear();

                if (marketResponse.getMarketDataModelList().size() >= 3) {
                    maxSizeList = 3;
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
                AccountDetails.dashboardList.add(marketResponse.getMarketDataModelList().get(i).getToken());
                commonAdapter.addSymbol(marketResponse.getMarketDataModelList().get(i).getToken());

            }
            commonAdapter.setData(temp_list);
            commonAdapter.notifyDataSetChanged();

            if(commonAdapter.getCount() > 0)
                setListViewHeightBasedOnItems(listMarketDerivative);

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
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                if (item != null) {
                    item.measure(0, 0);
                    totalItemsHeight += item.getMeasuredHeight();
                }
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);

            // Set list height.
            /*ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();*/

            if(totalItemsHeight + totalDividersHeight > 0) {
                ViewGroup.LayoutParams params2 = parent_layout.getLayoutParams();
                //params2.height = totalItemsHeight + totalDividersHeight;
                params2.height = totalItemsHeight + totalDividersHeight;
                parent_layout.setLayoutParams(params2);
                parent_layout.requestLayout();
            }

            return true;

        } else {
            return false;
        }

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
                    args.putString(QuotesFragment.EXPIRY, DateTimeFormatter.getDateFromTimeStamp(data.getExpiryDate(), "dd MMM yyyy", "bse"));
                    if(data.getOptType().equalsIgnoreCase("ce")) {
                        args.putString("optionType", "Call");
                    }
                    else
                    {
                        args.putString("optionType", "Put");
                    }
                    args.putString(QuotesFragment.STRIKE_PRICE, data.getStrikePrice());
                    //args.putString(QuotesFragment.OPTION_TYPE, data.getOptType());
                    args.putString(QuotesFragment.SELECTED_OPTION, data.getOptType());
                    navigateTo(NAV_TO_QUOTES_SCREEN, args, true);
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
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
            DerivativesFragment.DerivativeAdapter.Holder holder;
            Double perChange;
            Activity activity = getActivity();
            if (activity != null && isAdded()) {
                if (convertView == null) {
                    holder = new DerivativesFragment.DerivativeAdapter.Holder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.row_items_layout, parent, false);

                    holder.linearLayout = convertView.findViewById(R.id.linearParent);
                    holder.tvSymbol = convertView.findViewById(R.id.tvSymbol);
                    holder.tvLtp = convertView.findViewById(R.id.tvLtp);
                    holder.tvVol = convertView.findViewById(R.id.tvVol);
                    holder.tvChg = convertView.findViewById(R.id.tvChg);
                    holder.dividerDerivativeLabel = convertView.findViewById(R.id.dividerDerivative);
                    holder.upDownArrow = convertView.findViewById(R.id.updownArrow);
                    convertView.setTag(holder);
                } else {
                    holder = (DerivativesFragment.DerivativeAdapter.Holder) convertView.getTag();
                }

                MarketDataModel model = getItem(position);
                String optiontype = "XX";

                if (model.getOptType().equals(optiontype)) {
                    holder.tvSymbol.setText(String.format("%s - %s - %s", model.getSymbol(), DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "bse"), model.getInstName()));
                } else {
                    holder.tvSymbol.setText(String.format("%s - %s - %s(%s)", model.getSymbol(), DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "bse"), model.getInstName(), model.getOptType()));
                }

                if (Double.valueOf(model.getStrikePrice()) > 0) {

                    // holder.tvSymbol.append(String.format("%s - (%s) ",DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(),"dd MMM yyyy", "bse"), model.getStrikePrice() ));

                } else {

                    // holder.tvSymbol.append(String.format("%s", DateTimeFormatter.getDateFromTimeStamp(model.getExpiryDate(), "dd MMM yyyy", "bse")));
                }
                //  holder.txtExpiryDate.setVisibility(View.GONE);

                if (currentType.equals("future_")) {
                    holder.tvVol.setText(model.getVolume());
                } else {
                    holder.tvVol.setText(model.getOi());
                }

                holder.tvLtp.setText(String.format("%.2f", Double.parseDouble(model.getLtp())));

                if (!model.getPerChange().equalsIgnoreCase("null")) {
                    perChange = Double.valueOf(model.getPerChange());
                    holder.tvChg.setText(String.format("%.2f%%", perChange));

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

                        holder.tvChg.setTextColor(getResources().getColor(R.color.red));
                        holder.upDownArrow.setImageResource(R.drawable.down_arrow_red);


                    } else {

                        holder.tvLtp.setTextColor(mContext.getResources().getColor(textColorPositive));
                        holder.tvLtp.setBackground(mContext.getResources().getDrawable(flashBluecolor));
                        holder.tvVol.setTextColor(mContext.getResources().getColor(AccountDetails.textColorBlue));

                        holder.tvChg.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
                        holder.upDownArrow.setImageResource(arrow_image);
                    }
                } else {
                    perChange = 0.00;
                    holder.tvChg.setText(String.format("%.2f%%", perChange));
//                holder.tvChg.setTextColor(getResources().getColor(R.color.green_textcolor));
                    holder.tvChg.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
                }

//            [TODO: SUSHANT]
                int textColor = AccountDetails.getTextColorDropdown();

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                    holder.tvSymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    holder.dividerDerivativeLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

                } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                    convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                    holder.tvSymbol.setTextColor(getResources().getColor(textColor));
                    holder.dividerDerivativeLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
                return convertView;
            }
            return null;
        }

        public class Holder {
            GreekTextView tvSymbol;
            GreekTextView tvChg, tvVol, tvLtp;
            View dividerDerivativeLabel;
            LinearLayout linearLayout;
            ImageView upDownArrow;

        }
    }

    private void sendStreamingRequest() {

        streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", streamingList);
    }


    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void refreshComplete() {
        hideProgress();
        isWaitingForRequest = false;
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
                EventBus.getDefault().post(streamingResponse);
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
            commonAdapter.notifyDataSetInvalidated();
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
        if(derivative_Timer != null) {
            derivative_Timer.cancel();
            derivative_Timer = null;
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
           // loadDerivativeData();

            if(derivative_Timer != null)
            {
                derivative_Timer.cancel();
            }
            derivative_Timer = new Timer();
            derivative_Timer.schedule(new TimerTask() {
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
        super.onFragmentPause();
        if(derivative_Timer != null) {
            derivative_Timer.cancel();
            derivative_Timer = null;
        }
        updateAdapterSize();
        commonAdapter.notifyDataSetChanged();
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

}

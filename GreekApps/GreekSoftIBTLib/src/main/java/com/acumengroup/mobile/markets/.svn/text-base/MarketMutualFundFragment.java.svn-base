package com.acumengroup.mobile.markets;

import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
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
import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MarketDataModel;
import com.acumengroup.mobile.model.MarketDataResponse;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketMutualFundFragment extends GreekBaseFragment {
    private final AvailableSchemeListModel availableSchemeList = new AvailableSchemeListModel();
    private Spinner assetSpinner, filtersSpinner;
    private ListView listCommodityCurrency;
    private MarketMutualFundFragment.CustomAdapter commonAdapter;
    private boolean isWaitingForRequest = false;
    private boolean pullToRefreshFlag = false;
    /* For Top Header */
    private MarketDataResponse marketResponse;
    ArrayAdapter<String> groupAdapter;
    private final List<String> mutualfundmenulist = new ArrayList<>();
    private AlertDialog levelDialog;
    MarketDataModel rowData = null;

    private final AdapterView.OnItemClickListener marketMoversListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            rowData = commonAdapter.getItem(position);
            LayoutInflater inflater = getMainActivity().getLayoutInflater();

            View layout = inflater.inflate(R.layout.alert_quick_three_actions, null);
            GreekTextView view1 = layout.findViewById(R.id.action_item2);
            view1.setText("Lumpsum");
            GreekTextView view2 = layout.findViewById(R.id.action_item1);
            view2.setText("Fund Details");
            GreekTextView view3 = layout.findViewById(R.id.action_item3);
            view3.setText("SIP");


            view1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
                        getMFData(rowData.getSchemeCode());
                        processPurchase("PURCHASE", rowData.getTradingISIN());
                        levelDialog.dismiss();
                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        levelDialog.dismiss();
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_MF), "Ok", true, new GreekDialog.DialogListener() {

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
                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
                        getMFData(rowData.getSchemeCode());
                        processPurchase("SIP", rowData.getTradingISIN());
                        levelDialog.dismiss();

                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        levelDialog.dismiss();
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_MF), "Ok", true, new GreekDialog.DialogListener() {

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
                    Bundle args = new Bundle();
                    args.putString("schemCode", rowData.getSchemeCode());
                    args.putString("tradingISIN", rowData.getTradingISIN());
                    args.putString("sipISIN", rowData.getSipISIN());
                    args.putString("schemeName", rowData.getSchemeName());

                    args.putString("From", "marketmutualfund");
                    navigateTo(NAV_TO_MUTUALFUND_GET_QUOTE, args, true);
                    levelDialog.dismiss();
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
    private RelativeLayout errorLayout;
    private final AdapterView.OnItemSelectedListener exchangeItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            loadCommCurrData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener filterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            loadCommCurrData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void getMFData(String schemCode) {
        showProgress();
        String sipFrequency = "getMFOverview?mf_schcode=" + schemCode;
        WSHandler.getRequest(getMainActivity(), sipFrequency, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);

                        availableSchemeList.setAMCName(jsonObject.optString("AMCName"));
                        availableSchemeList.setNAVDate(jsonObject.optString("CurrNavDate"));
                        availableSchemeList.setSchemeCode(jsonObject.optString("mf_schcode"));
                        availableSchemeList.setNAV(jsonObject.optString("CurrNAV"));
                        availableSchemeList.setSchemeName(jsonObject.optString("SchemeName"));
                        availableSchemeList.setSchemeType(jsonObject.optString("schemetype"));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    private void processPurchase(final String purchaseType, String isin) {
        showProgress();
        String sipFrequency = "getMFDataForISIN?ISIN=" + isin;

        WSHandler.getRequest(getMainActivity(), sipFrequency, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        availableSchemeList.setISIN(jsonObject.getString("ISIN"));
                        availableSchemeList.setMaxPurchaseAmount(jsonObject.getString("maxPurchaseAmt"));
                        availableSchemeList.setMinPurchaseAmount(jsonObject.getString("minPurchaseAmt"));
                        availableSchemeList.setMfSchemeCode(jsonObject.getString("corpSchCode"));
                        availableSchemeList.setNAVDate(jsonObject.getString("navDate"));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Request", availableSchemeList);

                        switch (purchaseType) {
                            case "SIP":
                                bundle.putBoolean("isSIPOrder", true);
                                bundle.putBoolean("isRedeemOrder", false);
                                navigateTo(NAV_TO_MUTUAL_FUND_SIP, bundle, true);
                                break;
                            case "XSIP":
                                bundle.putBoolean("isSIPOrder", true);
                                bundle.putBoolean("isRedeemOrder", false);
                                navigateTo(NAV_TO_MUTUAL_FUND_XSIP, bundle, true);
                                break;
                            case "STP":
                                bundle.putBoolean("isSIPOrder", true);
                                bundle.putBoolean("isRedeemOrder", false);
                                navigateTo(NAV_TO_MUTUAL_FUND_STP, bundle, true);
                                break;
                            case "SWP":
                                bundle.putBoolean("isSIPOrder", true);
                                bundle.putBoolean("isRedeemOrder", false);
                                navigateTo(NAV_TO_MUTUAL_FUND_SWP, bundle, true);
                                break;
                            case "MANDATE":
                                bundle.putBoolean("isSIPOrder", true);
                                bundle.putBoolean("isRedeemOrder", false);
                                navigateTo(NAV_TO_MUTUAL_FUND_MANDATE, bundle, true);
                                break;
                            case "PURCHASE":
                                bundle.putBoolean("isPurchaseOrder", true);
                                bundle.putBoolean("isRedeemOrder", false);
                                navigateTo(NAV_TO_MUTUALFUND_TRADE, bundle, true);
                                break;
                            case "REDEEM":
                                bundle.putBoolean("isPurchaseOrder", false);
                                bundle.putBoolean("isRedeemOrder", true);
                                navigateTo(NAV_TO_MUTUALFUND_TRADE, bundle, true);
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            pullToRefreshFlag = true;
            loadCommCurrData();
        }
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_market_mf).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_market_mf).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        hideAppTitle();
        setupViews(newsView);

        return newsView;
    }

    private void setupViews(View parent) {

        assetSpinner = parent.findViewById(R.id.exchangeSpinner);
        filtersSpinner = parent.findViewById(R.id.filtersSpinner);

        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);

        listCommodityCurrency = parent.findViewById(R.id.listMarketComodityCurency);
        listCommodityCurrency.setOnItemClickListener(marketMoversListener);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = parent.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

        errorLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");

        setupAdapter();

    }

    private void setupAdapter() {
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
            groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), Arrays.asList(getResources().getStringArray(R.array.exchangeTypeComodityCurr)));
        } else {
            getExchangeList();
            groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), mutualfundmenulist);
        }

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(),
                Arrays.asList(getResources().getStringArray(R.array.exchangeTypemf)));
        groupAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetSpinner.setAdapter(groupAdapter);
        assetSpinner.setOnItemSelectedListener(exchangeItemSelectedListener);

        ArrayAdapter<String> col1Adapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(),
                Arrays.asList(getResources().getStringArray(R.array.marketFiltermf)));

        col1Adapter.setDropDownViewResource(R.layout.custom_spinner);
        filtersSpinner.setAdapter(col1Adapter);
        filtersSpinner.setOnItemSelectedListener(filterItemSelectedListener);

        commonAdapter = new MarketMutualFundFragment.CustomAdapter(getMainActivity(), new ArrayList<MarketDataModel>());
        listCommodityCurrency.setAdapter(commonAdapter);
        loadPageContent();
    }

    private void getExchangeList() {

        mutualfundmenulist.clear();

        mutualfundmenulist.add("Our Recommendation");
        mutualfundmenulist.add("Top Funds");

    }


    @Override
    public void onDetach() {

        super.onDetach();
    }

    public void loadPageContent() {
        loadCommCurrData();
    }

    private void loadCommCurrData() {
        String type;
        String service;
        String serviceURL;
        switch (filtersSpinner.getSelectedItem().toString()) {
            case "Equity":
                type = "equity";
                service = "getMarketMutualFund";
                serviceURL = service + "?schemeType=" + type;
                break;
            case "Debt":
                type = "debt";
                service = "getMarketMutualFund";
                serviceURL = service + "&schemeType=" + type;
                break;
            case "ELSS":
                type = "elss";
                service = "getMarketMutualFund";
                serviceURL = service + "?schemeType=" + type;
                break;
            default:
                return;
        }
        if (!isWaitingForRequest && serviceURL.length() > 0) {
            commonAdapter.clear();
            commonAdapter.notifyDataSetChanged();
            isWaitingForRequest = true;
            if (!pullToRefreshFlag) {
                showProgress();
            }
            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    toggleErrorLayout(false);
                    refreshComplete();
                    try {
                        marketResponse = new MarketDataResponse();
                        marketResponse.fromJSON(response);
                        commonAdapter.setData(marketResponse.getMarketDataModelList());

                        if (marketResponse.getMarketDataModelList().size() > 0) {
                            for (int i = 0; i < marketResponse.getMarketDataModelList().size(); i++) {
                                commonAdapter.addSymbol(marketResponse.getMarketDataModelList().get(i).getToken());
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

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void refreshComplete() {
        hideProgress();
        isWaitingForRequest = false;
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
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
            MarketMutualFundFragment.CustomAdapter.Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_market_mf, parent, false);
                holder = new MarketMutualFundFragment.CustomAdapter.Holder();
                holder.tvSymbol = convertView.findViewById(R.id.tvSymbol);
                holder.tvChg = convertView.findViewById(R.id.tvChg);
                holder.dividerLabel = convertView.findViewById(R.id.dividerLbl);
                holder.oneyr = convertView.findViewById(R.id.oneyr);
                holder.fiveyr = convertView.findViewById(R.id.fiveyr);
                holder.threeyr = convertView.findViewById(R.id.threeyr);

                convertView.setTag(holder);

            } else {
                holder = (MarketMutualFundFragment.CustomAdapter.Holder) convertView.getTag();
            }

            MarketDataModel model = getItem(position);
            holder.tvSymbol.setText(model.getSchemeName());
            holder.oneyr.setText(String.format("%.2f%%", Double.valueOf(model.getOneYearRet())));
            holder.fiveyr.setText(String.format("%.2f%%", Double.valueOf(model.getFiveYearRet())));
            holder.threeyr.setText(String.format("%.2f%%", Double.valueOf(model.getThreeYearRet())));

            holder.tvChg.setText(String.format("%.2f%%", Double.valueOf(model.getIncRet())));


//            [TODO: SUSHANT]
            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvSymbol.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvChg.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dividerLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

                holder.oneyr.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.threeyr.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.fiveyr.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvSymbol.setTextColor(getResources().getColor(textColor));
                holder.tvChg.setTextColor(getResources().getColor(textColor));
                holder.dividerLabel.setBackgroundColor(getResources().getColor(textColor));

                holder.oneyr.setTextColor(getResources().getColor(textColor));
                holder.threeyr.setTextColor(getResources().getColor(textColor));
                holder.fiveyr.setTextColor(getResources().getColor(textColor));
            }
            return convertView;
        }

        public class Holder {
            ScrollingTextView tvSymbol;
            GreekTextView tvChg;
            GreekTextView oneyr, fiveyr, threeyr;
            View dividerLabel;
        }
    }
}

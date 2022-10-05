package com.acumengroup.mobile.trade;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.MutualFundHoldingsModel;
import com.acumengroup.mobile.model.MutualFundHoldingsResponse;
import com.acumengroup.mobile.symbolsearch.DelayAutoCompleteTextView;
import com.acumengroup.mobile.symbolsearch.MFAutoCompleteAdapter;
import com.acumengroup.mobile.tablefixheader.TableFixHeaders;
import com.acumengroup.mobile.tablefixheader.trade.WatchlistLayoutAdapter;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MutualFundFragmentNew extends GreekBaseFragment implements View.OnClickListener {

    private final ArrayList<AvailableSchemeListModel> schemeListData = new ArrayList<>();
    private final Bundle bundle = new Bundle();
    private AvailableSchemeListModel availableSchemeList;
    private Button btnSIP;
    private Button btnPurchase;
    private int checkedItem = 0;
    private AlertDialog levelDialog;
    private GreekTextView txt_or;
    private final AdapterView.OnItemSelectedListener schemeSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                position = position - 1;
                enableOrDisableButtons(schemeListData.get(position).getTradingISIN(), schemeListData.get(position).getSipISIN());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private RelativeLayout errorMsgLayout;
    private TableFixHeaders tableFixHeaders;
    private List<String> fundHouseList;
    private List<String> categoryList;
    private List<String> schemeNameList;
    private WatchlistLayoutAdapter baseTableAdapter;
    private List<MutualFundHoldingsModel> mutFundListHoldings;
    private Spinner fundHouseSpinner, categorySpinner, schemeSpinner;
    private ArrayAdapter<String> fundHouseAdapter, categoryAdapter, schemesAdapter;
    private final AdapterView.OnItemSelectedListener fundHouseSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (position != 0) {

                categorySpinner.setSelection(0);
                schemeSpinner.setSelection(0);
                showProgress();

                loadSpinnerData("getCategory?FundHouse=" + fundHouseSpinner.getItemAtPosition(position), "category");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener categorySelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                schemeSpinner.setSelection(0);
                showProgress();
                loadSpinnerData("getMFSchemeForFundHouseCategory?FundHouse=" + fundHouseSpinner.getSelectedItem().toString() + "&Category=" + categorySpinner.getItemAtPosition(position) + "&gcid=" + AccountDetails.getClientCode(getMainActivity()), "schemes");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private boolean searchClicked = false;
    private MutualFundHoldingsModel clickedHolding;
    private DelayAutoCompleteTextView search_text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        //attachLayout(R.layout.fragment_mutualfund);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mutualfund).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mutualfund).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            //  schemeSpinner.setTextColor(getResources().getColor(R.color.black));
        }

        setupViews(mfActionView);
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(1, 1);

        return mfActionView;
    }

    private void setupViews(View mfActionView) {
        errorMsgLayout = mfActionView.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        search_text = mfActionView.findViewById(R.id.search_text_mf);
        txt_or = mfActionView.findViewById(R.id.txt_or);
        search_text.setThreshold(3);
        search_text.setAdapter(new MFAutoCompleteAdapter(getActivity()));
        search_text.setLoadingIndicator(
                (android.widget.ProgressBar) mfActionView.findViewById(R.id.pb_loading_indicator));
        search_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                searchClicked = true;
                availableSchemeList = (AvailableSchemeListModel) adapterView.getItemAtPosition(position);
                enableOrDisableButtons(availableSchemeList.getTradingISIN(), availableSchemeList.getSipISIN());
                search_text.setText(availableSchemeList.getSchemeName());
            }
        });
        setAppTitle(getClass().toString(), "Select the Fund to Invest");
        tableFixHeaders = mfActionView.findViewById(R.id.table);
        String[] headers = {"Scheme Name", "Allotted Units", "Purchase Rate", "Scheme Code", "Available Units", "NAV"};
        int[] widths = {120, 120, 120, 120, 120, 100};
        baseTableAdapter = new WatchlistLayoutAdapter(getMainActivity(), headers, widths);
        baseTableAdapter.setSortingEnabled(false);
        baseTableAdapter.setOnRowClickListener(new WatchlistLayoutAdapter.TableItemClickListener() {
            @Override
            public void onClick(int row, View view) {
                clickedHolding = mutFundListHoldings.get(row);

                View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_four_actions, null);
                GreekTextView view1 = layout.findViewById(R.id.pop_quote);
                view1.setText("Get Quote");
                GreekTextView view2 = layout.findViewById(R.id.pop_advice);
                view2.setText("Redeem");
                GreekTextView view3 = layout.findViewById(R.id.pop_trade);
                view3.setText("Add Purchase");
                GreekTextView view4 = layout.findViewById(R.id.pop_trade_sell);
                view4.setText("SIP");

                view1.setOnClickListener(MutualFundFragmentNew.this);
                view2.setOnClickListener(MutualFundFragmentNew.this);
                view3.setOnClickListener(MutualFundFragmentNew.this);
                view4.setOnClickListener(MutualFundFragmentNew.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                builder.setView(layout);
                levelDialog = builder.create();
                levelDialog.show();

            }

            @Override
            public void onGreekViewClick(int row, View view) {

            }
        });
        tableFixHeaders.setAdapter(baseTableAdapter);

        fundHouseSpinner = mfActionView.findViewById(R.id.spnr_fundhouse);
        fundHouseList = new ArrayList<>();
        fundHouseList.add("Select Fund House");
        fundHouseAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), fundHouseList);
        fundHouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fundHouseSpinner.setAdapter(fundHouseAdapter);
        fundHouseSpinner.setOnItemSelectedListener(fundHouseSelectedListener);

        categorySpinner = mfActionView.findViewById(R.id.spnr_category);
        categoryList = new ArrayList<>();
        categoryList.add("Select Category");
        categoryAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(categorySelectedListener);

        schemeSpinner = mfActionView.findViewById(R.id.spnr_scheme_name);
        schemeNameList = new ArrayList<>();
        schemeNameList.add("Select Scheme Name");
        schemesAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), schemeNameList);
        schemesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schemeSpinner.setAdapter(schemesAdapter);
        schemeSpinner.setOnItemSelectedListener(schemeSelectedListener);

        btnPurchase = mfActionView.findViewById(R.id.btn_invest_purchase);
        btnPurchase.setOnClickListener(MutualFundFragmentNew.this);
        btnSIP = mfActionView.findViewById(R.id.btn_invest_sip);
        btnSIP.setOnClickListener(MutualFundFragmentNew.this);

        sendHoldingsRequest();
        loadSpinnerData("getFundHouse", "fundhouse");

        setThemeViews();
    }

    private void setThemeViews() {

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            search_text.setTextColor(getResources().getColor(R.color.black));
            search_text.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_search_black_24dp), null);
//            fundHouseSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            categorySpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            schemeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            txt_or.setTextColor(getResources().getColor(R.color.black));


        } else {

            search_text.setTextColor(getResources().getColor(R.color.white));
            search_text.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_search_white_24dp), null);
//            fundHouseSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_white));
//            categorySpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_white));
//            schemeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_white));
            txt_or.setTextColor(getResources().getColor(R.color.white));


        }
    }

    private void loadSpinnerData(String url, final String type) {
        showProgress();
        WSHandler.getRequest(getMainActivity(), url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    switch (type) {
                        case "fundhouse":
                            fundHouseList.clear();
                            fundHouseList.add("Select Fund House");
                            for (int i = 0; i < respCategory.length(); i++) {

                                fundHouseList.add(respCategory.getJSONObject(i).getString("cAMCName"));
                            }
                            fundHouseAdapter.notifyDataSetChanged();
                            break;
                        case "category":
                            categoryList.clear();
                            categoryList.add("Select Category");
                            for (int i = 0; i < respCategory.length(); i++) {
                                categoryList.add(respCategory.getJSONObject(i).getString("category"));
                            }
                            categoryAdapter.notifyDataSetChanged();
                            break;
                        case "schemes":
                            schemeListData.clear();
                            schemeNameList.clear();
                            schemeNameList.add("Select Scheme Name");
                            schemesAdapter.notifyDataSetChanged();
                            for (int i = 0; i < respCategory.length(); i++) {
                                JSONObject objJSONObject = respCategory.getJSONObject(i);
                                schemeNameList.add(objJSONObject.optString("schemeName"));

                                AvailableSchemeListModel availableSchemeList = new AvailableSchemeListModel();
                                availableSchemeList.setAMCName(objJSONObject.optString("AMCName"));
                                availableSchemeList.setNAVDate(objJSONObject.optString("navDate"));
                                availableSchemeList.setMaxPurchaseAmount(objJSONObject.optString("maxPurchaseAmt"));
                                availableSchemeList.setMinAddPurchaseAmount(objJSONObject.optString("minPurchaseAmt"));
                                availableSchemeList.setSchemeCode(objJSONObject.optString("schemeCode"));
                                availableSchemeList.setTradingISIN(objJSONObject.optString("tradingISIN"));
                                availableSchemeList.setSipISIN(objJSONObject.optString("sipISIN"));
                                availableSchemeList.setMfSchemeCode(objJSONObject.optString("corpSchCode"));
                                availableSchemeList.setNAV(objJSONObject.optString("navValue"));
                                availableSchemeList.setMinPurchaseAmount(objJSONObject.optString("minPurchaseAmt"));
                                availableSchemeList.setISIN(objJSONObject.optString("ISIN"));
                                availableSchemeList.setSchemeName(objJSONObject.optString("schemeName"));
                                availableSchemeList.setSchemeType(objJSONObject.optString("schemeType"));
                                availableSchemeList.setMfClientCode(objJSONObject.optString("mfClientCode"));
                                availableSchemeList.setBseCode(objJSONObject.optString("bseCode"));
                                availableSchemeList.setBseRTACode(objJSONObject.optString("bseRTACode"));
                                schemeListData.add(availableSchemeList);
                            }
                            schemesAdapter.notifyDataSetChanged();
                            break;
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

    private void enableOrDisableButtons(String tradingISIN, String sipISIN) {
        if (tradingISIN.equals("")) {
            btnPurchase.setEnabled(false);
        } else {
            btnPurchase.setEnabled(true);
        }
        if (sipISIN.equals("")) {
            btnSIP.setEnabled(false);
        } else {
            btnSIP.setEnabled(true);
        }
    }

    private void sendHoldingsRequest() {
        showProgress();
        WSHandler.getRequest(getMainActivity(), "getMFHoldings?ClientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&SessionId=" + AccountDetails.getToken(getMainActivity()), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    MutualFundHoldingsResponse mutualFundHoldingsResponse = new MutualFundHoldingsResponse();
                    mutFundListHoldings = mutualFundHoldingsResponse.fromJSON(response);
                    if (mutFundListHoldings.size() > 0) {
                        for (MutualFundHoldingsModel mfHolding : mutFundListHoldings) {
                            LinkedHashMap<String, String> hm = new LinkedHashMap<>();
                            hm.put("Scheme Name", mfHolding.getSchemeName());
                            hm.put("Allotted Units", mfHolding.getAllotedUnits());
                            hm.put("Purchase Rate", mfHolding.getPurchaseRate());
                            hm.put("Scheme Code", mfHolding.getSchemeCode());
                            hm.put("Available Units", mfHolding.getAvailableUnits());
                            hm.put("NAV", mfHolding.getNAV());
                            hm.put("bseRTACode", mfHolding.getBseRTACode());
                            hm.put("bseRTACode", mfHolding.getBseCode());
                            baseTableAdapter.add(hm);
                        }
                    }
                    baseTableAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                toggleErrorLayout(true);
                tableFixHeaders.setVisibility(View.GONE);
                hideProgress();
            }
        });
    }

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.pop_quote) {
            bundle.clear();
            bundle.putSerializable("schemCode", clickedHolding.getMfSchemeCode());
            bundle.putSerializable("schemeName", clickedHolding.getSchemeName());
            bundle.putBoolean("fromHoldings", true);
            bundle.putSerializable("tradingISIN", clickedHolding.getISIN());
            bundle.putSerializable("sipISIN", clickedHolding.getSipISIN());
            navigateTo(NAV_TO_MUTUALFUND_GET_QUOTE, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.pop_advice) {
            bundle.clear();
            bundle.putBoolean("isRedeemOrder", true);
            bundle.putBoolean("isFromHoldings", true);
            bundle.putSerializable("HoldingsData", clickedHolding);
            navigateTo(NAV_TO_MUTUALFUND_TRADE, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.pop_trade) {
            bundle.clear();
            bundle.putBoolean("isFromHoldings", true);
            bundle.putBoolean("isAdditionalPurchase", true);
            bundle.putSerializable("HoldingsData", clickedHolding);
            navigateTo(NAV_TO_MUTUALFUND_TRADE, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.pop_trade_sell) {
            AvailableSchemeListModel list = new AvailableSchemeListModel();
            list.setSchemeName(clickedHolding.getSchemeName());
            list.setMinPurchaseAmount(clickedHolding.getMinPurAmt());
            list.setMaxPurchaseAmount(clickedHolding.getMaxPurAmt());
            list.setAMCName(clickedHolding.getAMCName());
            list.setISIN(clickedHolding.getISIN());
            list.setMfSchemeCode(clickedHolding.getMfSchemeCode());
            list.setSchemeCode(clickedHolding.getSchemeCode());
            list.setNAV(clickedHolding.getNAV());
            bundle.clear();
            bundle.putSerializable("Request", list);
            bundle.putBoolean("isSIPOrder", true);
            bundle.putBoolean("isRedeemOrder", false);
            navigateTo(NAV_TO_MUTUAL_FUND_SIP, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.btn_invest_purchase) {
            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK,
                        "Login to enjoy the services", "Ok", false,
                        new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {


                            }
                        });

            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {

                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK,
                        getResources().getString(R.string.MESSAGE_DISPLAY_MF), "Ok", false,
                        new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {


                            }
                        });

            } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER) {

                if (!AccountDetails.isMfOrderAllowed()) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK,
                            "Your KYC is Not Approved ", "Ok", false,
                            new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {


                                }
                            });

                } else {

                    new BottomSheet.Builder(getActivity(), R.style.MyBottomSheetStyle)
                            .setSheet(R.menu.invest_menu)
                            // .setTitle("Invest Now")
                            .setListener(new BottomSheetListener() {
                                @Override
                                public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object o) {

                                }

                                @Override
                                public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {

                                    Bundle bundle = new Bundle();

                                    if (fundHouseSpinner.getSelectedItemPosition() != 0 && categorySpinner.getSelectedItemPosition() != 0 && schemeSpinner.getSelectedItemPosition() != 0 || searchClicked) {

                                        if (menuItem.getTitle().toString().equalsIgnoreCase("Lumpsum")) {

                                            if (fundHouseSpinner.getSelectedItemPosition() != 0 && categorySpinner.getSelectedItemPosition() != 0 && schemeSpinner.getSelectedItemPosition() != 0) {
                                                AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isPurchaseOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUALFUND_TRADE, bundle, true);

                                            } else if (searchClicked) {
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isPurchaseOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUALFUND_TRADE, bundle, true);

                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please Select Fund to Invest ", "OK", true, null);
                                            }


                                        } else if (menuItem.getTitle().toString().equalsIgnoreCase("SIP")) {


                                            if (fundHouseSpinner.getSelectedItemPosition() != 0 && categorySpinner.getSelectedItemPosition() != 0 && schemeSpinner.getSelectedItemPosition() != 0) {
                                                AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_SIP, bundle, true);

                                            } else if (searchClicked) {
                                                //AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_SIP, bundle, true);

                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please Select Fund to Invest ", "OK", true, null);
                                            }
                                                /*AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_SIP, bundle, true);*/


                                        } else if (menuItem.getTitle().toString().equalsIgnoreCase("STP")) {
                                                /*AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_STP, bundle, true);*/
                                            if (fundHouseSpinner.getSelectedItemPosition() != 0 && categorySpinner.getSelectedItemPosition() != 0 && schemeSpinner.getSelectedItemPosition() != 0) {
                                                AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_STP, bundle, true);

                                            } else if (searchClicked) {
                                                //AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_STP, bundle, true);

                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please Select Fund to Invest ", "OK", true, null);
                                            }

                                        } else if (menuItem.getTitle().toString().equalsIgnoreCase("SWP")) {

                                                /*AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_SWP, bundle, true);*/

                                            if (fundHouseSpinner.getSelectedItemPosition() != 0 && categorySpinner.getSelectedItemPosition() != 0 && schemeSpinner.getSelectedItemPosition() != 0) {
                                                AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_SWP, bundle, true);

                                            } else if (searchClicked) {
                                                //AvailableSchemeListModel availableSchemeList = schemeListData.get(schemeSpinner.getSelectedItemPosition() - 1);
                                                bundle.clear();
                                                bundle.putSerializable("Request", availableSchemeList);
                                                bundle.putBoolean("isSIPOrder", true);
                                                bundle.putBoolean("isRedeemOrder", false);
                                                navigateTo(NAV_TO_MUTUAL_FUND_SWP, bundle, true);

                                            } else {
                                                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please Select Fund to Invest ", "OK", true, null);
                                            }
                                        }

                                    } else {
                                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please Select Fund to Invest ", "OK", true, null);
                                    }

                                }

                                @Override
                                public void onSheetDismissed(@NonNull BottomSheet
                                                                     bottomSheet, @Nullable Object o, int i) {

                                }
                            })
                            .object("Some object")
                            .show();

                }
            }


//
        }
    }


    @Override
    public void onFragmentResume() {

        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_ACTION;
        if (search_text != null)
            search_text.setText("");
    }
}

package com.acumengroup.mobile.symbolsearch;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.GreekFragmentPagerAdapter;
import com.acumengroup.ui.button.GreekButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MutualFundGetQuoteFragment extends GreekBaseFragment {

    private final AvailableSchemeListModel availableSchemeList = new AvailableSchemeListModel();
    private Bundle bundle;
    private String schemCode, bsecode, bsertacode;
    private String tradingISIN;
    private String sipISIN;
    private GreekButton btn_invest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pfDetailsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mutualfund_getquote).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mutualfund_getquote).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }


        bundle = getArguments();
        schemCode = bundle.getString("schemCode");
        tradingISIN = bundle.getString("tradingISIN");
        sipISIN = bundle.getString("sipISIN");
        bsertacode = bundle.getString("bseRTACode");
        bsecode = bundle.getString("bseCode");

        setAppTitle(getClass().toString(), bundle.getString("schemeName"));

        Bundle args = new Bundle();
        args.putString("schemCode", schemCode);
        args.putString("bseRTACode", bsertacode);
        args.putString("bseCode", bsecode);

        OverviewFragment overviewFragment = new OverviewFragment();
        overviewFragment.setArguments(args);

        Top5HoldingsFragment top5HoldingsFragment = new Top5HoldingsFragment();
        top5HoldingsFragment.setArguments(args);

        PeerComparisonFragment peerComparisonFragment = new PeerComparisonFragment();
        peerComparisonFragment.setArguments(args);

        DividendFragment dividendFragment = new DividendFragment();
        dividendFragment.setArguments(args);


        ArrayList<Fragment> list = new ArrayList<>();
        list.add(overviewFragment);
        list.add(top5HoldingsFragment);
        list.add(peerComparisonFragment);
        list.add(dividendFragment);

        String[] heading = new String[list.size()];
        heading[0] = "Overview";
        heading[1] = "Holding";
        heading[2] = "Performance";
        heading[3] = "Dividend";

        ViewPager pager = pfDetailsView.findViewById(R.id.mutualFundPager);
        GreekFragmentPagerAdapter pagerAdapter = new GreekFragmentPagerAdapter(getChildFragmentManager(), list, heading);

        pager.setAdapter(pagerAdapter);


        PagerSlidingTabStrip tabs = pfDetailsView.findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        setView(pfDetailsView);
        getMFData();

        return pfDetailsView;
    }

    private void setView(View pfDetailsView) {

        btn_invest = pfDetailsView.findViewById(R.id.btn_invest);

        btn_invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
//                           .setTitle("Invest Now")
                                .setListener(new BottomSheetListener() {
                                    @Override
                                    public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object o) {

                                    }

                                    @Override
                                    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {

                                        Bundle bundle = new Bundle();

                                        if (menuItem.getTitle().toString().equalsIgnoreCase("Lumpsum")) {

                                            processPurchase("PURCHASE", tradingISIN);

                                        } else if (menuItem.getTitle().toString().equalsIgnoreCase("SIP")) {

                                            processPurchase("SIP", tradingISIN);

                                        } else if (menuItem.getTitle().toString().equalsIgnoreCase("STP")) {

                                            processPurchase("STP", tradingISIN);

                                        } else if (menuItem.getTitle().toString().equalsIgnoreCase("SWP")) {

                                            processPurchase("SWP", tradingISIN);
                                        }

                                    }

                                    @Override
                                    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object o, int i) {

                                    }
                                })
                                .object("Some object")
                                .show();
                    }
                }
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
                        availableSchemeList.setBseRTACode(jsonObject.getString("bseRTACode"));
                        availableSchemeList.setBseCode(jsonObject.getString("bseCode"));

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

    private void getMFData() {
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


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_GET_QUOTE;
    }
}

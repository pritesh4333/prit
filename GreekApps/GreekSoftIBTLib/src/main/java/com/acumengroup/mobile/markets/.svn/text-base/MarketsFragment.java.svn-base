package com.acumengroup.mobile.markets;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.viewpagerindicator.IconPagerAdapter;
import com.acumengroup.mobile.viewpagerindicator.TabPageIndicator;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

/**
 * Created by Arcadia
 */
public class MarketsFragment extends GreekBaseFragment {
    private final ArrayList<String> tokenList = new ArrayList<>();
    private LinearLayout mTabsLinearLayout, equityLayout, fnoLayout, currencyLayout;
    private FloatingActionButton equityFltBtn, derivativeFltBtn, currencyFltBtn, globalFltBtn;
    private String selectedPage = "equity";
    ArrayList<Fragment> list;
    private GreekTextView marketcomm_txt;
    int eq_pos, fno_pos, currcomm_pos, glo_pos;
    private ArrayList<String> listhead;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View marketsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.pager_fragment_markets).setBackgroundColor(getResources().getColor(R.color.floatingBgColor));
        } else {
            attachLayout(R.layout.pager_fragment_markets).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_MARKET_STARTUP_SCREEN;
        hideAppTitle();
        setupViews(marketsView);
        return marketsView;
    }

    public void setupViews(View marketsView) {
        equityFltBtn = marketsView.findViewById(R.id.flt_market_equity);
        derivativeFltBtn = marketsView.findViewById(R.id.flt_market_derivative);
        currencyFltBtn = marketsView.findViewById(R.id.flt_currency);
        globalFltBtn = marketsView.findViewById(R.id.flt_market_global);

        equityLayout = marketsView.findViewById(R.id.equity_layout);
        fnoLayout = marketsView.findViewById(R.id.fno_layout);
        currencyLayout = marketsView.findViewById(R.id.currency_layout);

        marketcomm_txt = marketsView.findViewById(R.id.market_commodity);
        equityFocusEffect();

        list = new ArrayList<>();
        ArrayList<String> heading = new ArrayList<>();
        listhead = new ArrayList<>();

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

            equityLayout.setVisibility(View.VISIBLE);
            fnoLayout.setVisibility(View.VISIBLE);
            currencyLayout.setVisibility(View.VISIBLE);
            MarketEquityFragment equityFragment = new MarketEquityFragment();
            MarketDerivativeFragment derivativeFragment = new MarketDerivativeFragment();
            MarketCommodityCurrencyFragment commodityCurrencyFragment = new MarketCommodityCurrencyFragment();

            list.add(new MarketEquityFragment());
            heading.add("Equity");

            list.add(new MarketDerivativeFragment());
            heading.add("Derivatives");

            heading.add("Comm&Curr");
            heading.add("Global Markets");
            //heading.add("Mutual Fund");
            list.add(new MarketCommodityCurrencyFragment());

            list.add(new MarketGlobalFragment());
            //list.add(new MarketMutualFundFragment());

            listhead.add("equity");
            listhead.add("derivatives");
            listhead.add("currcomm");
            listhead.add("global");
            //listhead.add("mutualfund");


        } else {

            if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
                equityLayout.setVisibility(View.VISIBLE);
                MarketEquityFragment equityFragment = new MarketEquityFragment();
                list.add(new MarketEquityFragment());
                heading.add("Equity");
                listhead.add("equity");

            }
            if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
                fnoLayout.setVisibility(View.VISIBLE);
                MarketDerivativeFragment derivativeFragment = new MarketDerivativeFragment();
                list.add(new MarketDerivativeFragment());
                heading.add("Derivatives");
                listhead.add("derivatives");

            }
            if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
                currencyLayout.setVisibility(View.VISIBLE);
                MarketCommodityCurrencyFragment commodityCurrencyFragment = new MarketCommodityCurrencyFragment();
                heading.add("Comm&Curr");
                listhead.add("currcomm");
                list.add(new MarketCommodityCurrencyFragment());
                marketcomm_txt.setText("Currency");
            }
            if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
                currencyLayout.setVisibility(View.VISIBLE);
                MarketCommodityCurrencyFragment commodityCurrencyFragment = new MarketCommodityCurrencyFragment();

                if (!listhead.contains("currcomm")) {
                    listhead.add("currcomm");
                    heading.add("Comm&Curr");
                    list.add(new MarketCommodityCurrencyFragment());
                    marketcomm_txt.setText("Commodity");
                }
            }
            list.add(new MarketGlobalFragment());
            heading.add("Global Markets");
            //heading.add("Mutual Fund");
            listhead.add("global");
            //listhead.add("mutualfund");
            //list.add(new MarketMutualFundFragment());


        }

        for (int i = 0; i < listhead.size(); i++) {

            if (listhead.get(i).equalsIgnoreCase("equity")) {
                eq_pos = i;
            } else if (listhead.get(i).equalsIgnoreCase("derivatives")) {
                fno_pos = i;
            } else if (listhead.get(i).equalsIgnoreCase("currcomm")) {
                currcomm_pos = i;
            } else if (listhead.get(i).equalsIgnoreCase("global")) {
                glo_pos = i;
            } else if (listhead.get(i).equalsIgnoreCase("mutualfund")) {
                glo_pos = i;
            }
        }


        final ViewPager mPager = marketsView.findViewById(R.id.pager);

        final MarketsPagerAdapter adapter = new MarketsPagerAdapter(getChildFragmentManager(), list, heading);

        mPager.setAdapter(adapter);

        equityFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = "equity";

                mPager.setCurrentItem(eq_pos, false);

                ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(0, eq_pos);

            }
        });

        derivativeFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = "derivative";
                mPager.setCurrentItem(fno_pos, false);

                ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(0, fno_pos);
            }
        });

        currencyFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = "currcomm";
                mPager.setCurrentItem(currcomm_pos, false);

                ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(0, currcomm_pos);
            }
        });

        globalFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = "global";
                mPager.setCurrentItem(glo_pos, false);
                globalFocusEffect();
                ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(0, glo_pos);

            }
        });


        final TabPageIndicator indicator = marketsView.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        mTabsLinearLayout = ((LinearLayout) indicator.getChildAt(0));
        setIndicatorColor(0);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setIndicatorColor(position);

                if (listhead.get(position).equalsIgnoreCase("equity")) {
                    selectedPage = "equity";
                    callMarketPage(position);
                } else if (listhead.get(position).equalsIgnoreCase("derivatives")) {
                    selectedPage = "derivatives";
                    callMarketPage(position);
                } else if (listhead.get(position).equalsIgnoreCase("currcomm")) {
                    selectedPage = "currcomm";
                    callMarketPage(position);
                } else if (listhead.get(position).equalsIgnoreCase("global")) {
                    selectedPage = "global";
                    callMarketPage(position);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void callMarketPage(int pos) {

        if (selectedPage.equalsIgnoreCase("equity")) {
            AccountDetails.marketEquity = true;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = false;
            ((MarketEquityFragment) list.get(pos)).loadPageContent();
        } else if (selectedPage.equalsIgnoreCase("derivatives")) {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = true;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = false;
            ((MarketDerivativeFragment) list.get(pos)).loadPageContent();
        } else if (selectedPage.equalsIgnoreCase("currcomm")) {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = true;
            AccountDetails.marketGlobal = false;
            ((MarketCommodityCurrencyFragment) list.get(pos)).loadPageContent();
        } else if (selectedPage.equalsIgnoreCase("global")) {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = true;
            ((MarketGlobalFragment) list.get(pos)).sendGlobalIndicesRequest();
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
        AccountDetails.currentFragment = NAV_TO_MARKET_STARTUP_SCREEN;
        if (AccountDetails.marketEquity) {

            if (getChildFragmentManager().getFragments() != null) {
                for (int i = 0; i < getChildFragmentManager().getFragments().size(); i++) {
                    GreekBaseFragment fragment = (GreekBaseFragment) getChildFragmentManager().getFragments().get(i);
                    if (fragment instanceof MarketEquityFragment)
                        fragment.onFragmentResume();
                }
            }
        } else if (AccountDetails.marketDerivative) {
            if (getChildFragmentManager().getFragments() != null) {
                for (int i = 0; i < getChildFragmentManager().getFragments().size(); i++) {
                    GreekBaseFragment fragment = (GreekBaseFragment) getChildFragmentManager().getFragments().get(i);
                    if (fragment instanceof MarketDerivativeFragment)
                        fragment.onFragmentResume();
                }
            }
        } else if (AccountDetails.marketCurrCom) {
            if (getChildFragmentManager().getFragments() != null) {
                for (int i = 0; i < getChildFragmentManager().getFragments().size(); i++) {
                    GreekBaseFragment fragment = (GreekBaseFragment) getChildFragmentManager().getFragments().get(i);
                    if (fragment instanceof MarketCommodityCurrencyFragment)
                        fragment.onFragmentResume();
                }
            }
        }
    }


    private void setIndicatorColor(int position) {
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            GreekTextView tv = (GreekTextView) mTabsLinearLayout.getChildAt(i);
            if (i == position) {
                tv.setTextColor(Color.parseColor("#007aff"));
            } else {
                tv.setTextColor(Color.parseColor("#ffffff"));
            }
        }
    }


    class MarketsPagerAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {

        final int[] ICONS = new int[]{
                R.drawable.settings_selector,
                R.drawable.derivative_selector,
                R.drawable.commodity_selector,
                R.drawable.global_selector
        };

        private final ArrayList<String> pageTitles;
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public MarketsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> pageTitles) {
            super(fm);
            this.fragmentList = fragmentList;
            this.pageTitles = pageTitles;
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles.get(position);
        }

        @Override
        public int getIconResId(int index) {

            if (getPageTitle(index).toString().equalsIgnoreCase("Equity")) {
                return R.drawable.settings_selector;

            } else if (getPageTitle(index).toString().equalsIgnoreCase("Derivatives")) {
                return R.drawable.derivative_selector;

            } else if (getPageTitle(index).toString().equalsIgnoreCase("Comm&Curr")) {
                return R.drawable.commodity_selector;

            } else if (getPageTitle(index).toString().equalsIgnoreCase("Global Markets")) {
                return R.drawable.global_selector;
            } else if (getPageTitle(index).toString().equalsIgnoreCase("Mutual Fund")) {
                return R.drawable.mutualfund_selector;
            }

            return ICONS[index];
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

//METHOD TO HANDLE FOCUS ON FLOATING BUTTONS

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void equityFocusEffect() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AccountDetails.marketEquity = true;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = false;

            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
        } else {
            AccountDetails.marketEquity = true;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = false;
            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
        }
    }

    public void derivativeFocusEffect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = true;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = false;
            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
        } else {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = true;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = false;
            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
        }
    }

    public void currencyFocusEffect() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = true;
            AccountDetails.marketGlobal = false;
            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
        } else {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = true;
            AccountDetails.marketGlobal = false;
            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
        }

    }

    public void globalFocusEffect() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = true;
            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon, getActivity().getTheme()));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        } else {

            AccountDetails.marketEquity = false;
            AccountDetails.marketDerivative = false;
            AccountDetails.marketCurrCom = false;
            AccountDetails.marketGlobal = true;

            equityFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            derivativeFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            currencyFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));
            globalFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.equity_icon));

            equityFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            derivativeFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            currencyFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            globalFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        }

    }
}
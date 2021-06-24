package com.acumengroup.mobile.BottomTabScreens;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


public class PortfolioBottomFragment extends GreekBaseFragment {

    private PagerSlidingTabStrip tabLayout;
    private CustomViewPager viewPager;
    private View view;

    public PortfolioBottomFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AccountDetails.setIsMainActivity(false);
        AccountDetails.currentFragment = NAV_TO_PORTFOLIO_BOTTOM_SCREEN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_market_bottom).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_market_bottom).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
//        AccountDetails.currentFragment = NAV_TO_PORTFOLIO_BOTTOM_SCREEN;


        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        setupViewPager(viewPager);
        if (getArguments() != null) {
            if (getArguments().getString("from").equalsIgnoreCase("dashboard")) {
                viewPager.setCurrentItem(2);
            }

            if (getArguments().getString("from").equalsIgnoreCase("dashboardholding")) {
                viewPager.setCurrentItem(1);
            }
        }
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        tabLayout.setTypeface(font, Typeface.NORMAL);
        tabLayout.setViewPager(viewPager);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            tabLayout.setIndicatorColor(getResources().getColor(R.color.buttonColor));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabLayout.setTextColor(getResources().getColor(R.color.black));
        }

        return view;
    }

    private void setupViewPager(CustomViewPager viewPager) {

        PortfolioBottomFragment.ViewPagerAdapter adapter = new PortfolioBottomFragment.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new DashboardTabFragment(), "DASHBOARD");
        adapter.addFrag(new HoldingTabFragment(), "HOLDINGS");
        adapter.addFrag(new PositionsTabFragment(), "POSITIONS");

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(2);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}


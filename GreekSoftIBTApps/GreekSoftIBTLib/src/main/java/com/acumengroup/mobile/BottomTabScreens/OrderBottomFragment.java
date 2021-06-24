package com.acumengroup.mobile.BottomTabScreens;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.mobile.reports.TradeBookFragment;
import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


public class OrderBottomFragment extends GreekBaseFragment {

    private PagerSlidingTabStrip tabLayout;
    private CustomViewPager viewPager;
    private View view;

    public OrderBottomFragment() {
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
        AccountDetails.currentFragment = NAV_TO_ORDER_BOTTOM_FRAGMENT;
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
        AccountDetails.currentFragment = NAV_TO_ORDER_BOTTOM_FRAGMENT;

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        setupViewPager(viewPager);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        tabLayout.setTypeface(font, Typeface.NORMAL);
        tabLayout.setViewPager(viewPager);

        if (getArguments() != null) {
            if (getArguments().getString("FromTab") != null && getArguments().getString("FromTab").equalsIgnoreCase("PendingTab")) {
                viewPager.setCurrentItem(0);
            } else if (getArguments().getString("FromTab") != null && getArguments().getString("FromTab").equalsIgnoreCase("ExecutedTab")) {
                viewPager.setCurrentItem(1);
            } else if (getArguments().getString("FromTab") != null && getArguments().getString("FromTab").equalsIgnoreCase("RejectedTab")) {
                viewPager.setCurrentItem(2);
            } else if (getArguments().getString("FromTab") != null && getArguments().getString("FromTab").equalsIgnoreCase("CancelledTab")) {
                viewPager.setCurrentItem(3);
            } else if (getArguments().getString("FromTab") != null && getArguments().getString("FromTab").equalsIgnoreCase("TradeDetailsTab")) {
                viewPager.setCurrentItem(4);
            }
        }

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            tabLayout.setIndicatorColor(getResources().getColor(R.color.buttonColor));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabLayout.setTextColor(getResources().getColor(R.color.black));
        }

        return view;
    }

    private void setupViewPager(CustomViewPager viewPager) {

        OrderBottomFragment.ViewPagerAdapter adapter = new OrderBottomFragment.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new PendingTabFragment(), "PENDING");
        adapter.addFrag(new ExecutedTabFragment(), "EXECUTED");
        adapter.addFrag(new RejectedTabFragment(), "REJECTED");
        adapter.addFrag(new CancelledTabFragment(), "CANCELLED");
//        adapter.addFrag(new TradeBookFragment(), "TRADE BOOK");
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
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

package com.acumengroup.mobile.BottomTabScreens;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.util.Util;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.init.SplashActivity;
import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


public class MarketBottomFragment extends GreekBaseFragment {
    // TODO: Rename and change types of parameters
    private PagerSlidingTabStrip tabLayout;
    private CustomViewPager viewPager;
    private LinearLayout pipLinearLayout;


    public MarketBottomFragment() {
        // Required empty public constructor
        AccountDetails.currentFragment=NAV_TO_BOTTOM_MARKET;

    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);

        if(isInPictureInPictureMode){
            pipLinearLayout.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);
        }else{
            pipLinearLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_market_bottom, container, false);
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        pipLinearLayout = view.findViewById(R.id.pipLayout);
        setupViewPager(viewPager);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            tabLayout.setIndicatorColor(getResources().getColor(R.color.buttonColor));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabLayout.setTextColor(getResources().getColor(R.color.black));
        }
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        tabLayout.setTypeface(font,Typeface.NORMAL);
        tabLayout.setViewPager(viewPager);
        return view;
    }

    private void setupViewPager(CustomViewPager viewPager) {


       //Todo heading Tab setting for Market
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new OverviewTabFragment(), "OVERVIEW");
            adapter.addFrag(new IndicesTabFragment(), "INDICES");
        adapter.addFrag(new DerivativeTabFragment(), "DERIVATIVES");
        if (AccountDetails.allowedmarket_ncdex==true || AccountDetails.allowedmarket_mcx==true) {
            adapter.addFrag(new CommodityTabFragment(), "COMMODITY");
        }
        if (AccountDetails.allowedmarket_ncd==true || AccountDetails.allowedmarket_bcd==true) {
            adapter.addFrag(new CurrencyTabFragment(), "CURRENCY");
        }
        adapter.addFrag(new NewsTabFragment(), "NEWS");
        adapter.addFrag(new EventTabFragment(), "EVENTS");
        adapter.addFrag(new FiiDiiTabFragment(), "FII & DII");
        viewPager.setOffscreenPageLimit(7);
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
            if (getCount() > position) return mFragmentList.get(position);
            return null;
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

    @Override
    public void onResume() {
        super.onResume();
        //Todo: for Avoid Logout Popup by Rohit
        AccountDetails.setIsMainActivity(false);
        AccountDetails.currentFragment=NAV_TO_BOTTOM_MARKET;
    }

}

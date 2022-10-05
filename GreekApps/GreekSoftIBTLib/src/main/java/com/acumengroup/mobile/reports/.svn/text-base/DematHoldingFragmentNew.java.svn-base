package com.acumengroup.mobile.reports;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.adapter.CustomFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by user on 24-Nov-16.
 */

public class DematHoldingFragmentNew extends GreekBaseFragment{
    private final ArrayList<Fragment> pagesList = new ArrayList<>();
    private String pageSource = "";
    private LinearLayout mTabsLinearLayout;
    View dematHoldingView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dematHoldingView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_demat_holdings).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_demat_holdings).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_DEMATHOLDING_SCREEN;
        AccountDetails.AutoRefreshForDemat = true;
        setUp();

        return dematHoldingView;

    }

    private void setUp() {
        setUpView(dematHoldingView);
        setupViewPager(dematHoldingView);
    }


    private void setUpView(View dematHoldingView) {

        setAppTitle(getClass().toString(), "Demat Holdings");

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = dematHoldingView.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));

        }
    }

    private void setupViewPager(View parent) {
        pagesList.clear();
        pagesList.add(demat_tabs_fragment.newInstance(pageSource, "Futures", previousFragment));
        pagesList.add(demat_tabs_fragment.newInstance(pageSource, "Call", previousFragment));
        pagesList.add(demat_tabs_fragment.newInstance(pageSource, "Put", previousFragment));

        String[] heading = new String[3];
        heading[0] = "Demat Holdings";
        heading[1] = "Demat";
        heading[2] = "Pool Demat";

        ViewPager mPager = parent.findViewById(R.id.pager);
        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), pagesList, heading);
        mPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabIndicator = parent.findViewById(R.id.indicator);
        tabIndicator.setViewPager(mPager);
        mTabsLinearLayout = ((LinearLayout) tabIndicator.getChildAt(0));
        setIndicatorColor(0);
        showProgress();
        ((demat_tabs_fragment) pagesList.get(0)).sendDematHoldingRequest(0);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = parent.findViewById(R.id.header_layout);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
            tabIndicator.setUnderlineColor(getResources().getColor(R.color.grayStrip_bg));
        }

        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //pagerActivePage = position;
                setIndicatorColor(position);
                ((demat_tabs_fragment) pagesList.get(position)).sendDematHoldingRequest(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicatorColor(int position) {
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            if (i == position) {
                tv.setTextColor(Color.WHITE);
            } else {
                tv.setTextColor(getResources().getColor(R.color.unselectedTxtColor));
            }
        }
    }


    @Override
    public void onFragmentResume() {
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 3);
        setAppTitle(getClass().toString(), "Demat Holdings");
        AccountDetails.currentFragment = NAV_TO_DEMATHOLDING_SCREEN;
        if (AccountDetails.AutoRefreshForDemat) {
            setUp();
        }
        super.onFragmentResume();
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

}

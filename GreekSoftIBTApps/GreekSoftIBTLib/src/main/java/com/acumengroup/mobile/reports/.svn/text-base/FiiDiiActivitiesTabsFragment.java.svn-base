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
import com.acumengroup.ui.CustomViewPager;
import com.acumengroup.ui.adapter.CustomFragmentPagerAdapter;

import java.util.ArrayList;

public class FiiDiiActivitiesTabsFragment extends GreekBaseFragment {
    private final ArrayList<Fragment> pagesList = new ArrayList<>();
    private String pageSource = "";
    private LinearLayout mTabsLinearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View FiiDiiActivitiesView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fund_transfer_tabs).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fund_transfer_tabs).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_FIIDII_SCREEN;
        setUpView();
        setupViewPager(FiiDiiActivitiesView);
        return FiiDiiActivitiesView;

    }

    private void setUpView() {
        //setAppTitle(getClass().toString(), "FII/DII Activities");
        hideAppTitle();
    }

    private void setupViewPager(View parent) {
        pagesList.clear();
        pagesList.add(FpiInvestmentFragment.newInstance(pageSource, "FPI Investment", previousFragment));
        pagesList.add(FpiDerivativesFragment.newInstance(pageSource, "FPI Derivatives", previousFragment));

        String[] heading = new String[3];
        heading[0] = "FPI Investment";
        heading[1] = "FPI Derivatives";

        CustomViewPager mPager = parent.findViewById(R.id.pager);
        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), pagesList, heading);
        mPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabIndicator = parent.findViewById(R.id.indicator);
        tabIndicator.setViewPager(mPager);

        mTabsLinearLayout = ((LinearLayout) tabIndicator.getChildAt(0));
        setIndicatorColor(0);
        mPager.setPagingEnabled(false);


        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setIndicatorColor(position);

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
        super.onFragmentResume();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}

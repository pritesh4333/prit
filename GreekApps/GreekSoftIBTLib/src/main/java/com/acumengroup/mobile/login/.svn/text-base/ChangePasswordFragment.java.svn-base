package com.acumengroup.mobile.login;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.util.Util;
import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.settings.UserSettingsFragment;
import com.acumengroup.ui.CustomViewPager;

import com.acumengroup.ui.adapter.CustomFragmentPagerAdapter;

import java.util.ArrayList;

public class ChangePasswordFragment extends GreekBaseFragment {
    private final ArrayList<Fragment> pagesList = new ArrayList<>();
    private String pageSource = "";
    private LinearLayout mTabsLinearLayout;
    private PagerSlidingTabStrip tabIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View changePasswordView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fund_transfer_tabs).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fund_transfer_tabs).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_FUNDTRANSFER_SCREEN;
        setUpView();
        setupViewPager(changePasswordView);

        tabIndicator = changePasswordView.findViewById(R.id.indicator);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            tabIndicator.setIndicatorColor(getResources().getColor(R.color.buttonColor));
            tabIndicator.setBackgroundColor(getResources().getColor(R.color.white));
            tabIndicator.setTextColor(getResources().getColor(R.color.black));
        }

        return changePasswordView;

    }


    private void setUpView() {
        setAppTitle(getClass().toString(), "Change Password");


    }

    private void setupViewPager(View parent) {
        pagesList.clear();
        pagesList.add(UserSettingsFragment.newInstance(pageSource, "Login Password", previousFragment));

        boolean is_validateTransactionshow = Util.getPrefs(getMainActivity()).getBoolean("is_validateTransactionshow", false);

        if (is_validateTransactionshow) {
            pagesList.add(UserSettingsFragment.newInstance(pageSource, "Transaction Password", previousFragment));
        }


        String[] heading = new String[3];
        heading[0] = "Login Password";
        heading[1] = "Transaction Password";

        CustomViewPager mPager = parent.findViewById(R.id.pager);
        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), pagesList, heading);
        mPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabIndicator = parent.findViewById(R.id.indicator);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        tabIndicator.setTypeface(font, Typeface.NORMAL);
        tabIndicator.setViewPager(mPager);


        mTabsLinearLayout = ((LinearLayout) tabIndicator.getChildAt(0));
        setIndicatorColor(0);
        mPager.setPagingEnabled(true);
        ((UserSettingsFragment) pagesList.get(0)).setPassType("login password");


        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                setIndicatorColor(position);
                if (position == 0) {
                    ((UserSettingsFragment) pagesList.get(position)).setPassType("login password");
                } else {
                    ((UserSettingsFragment) pagesList.get(position)).setPassType("transaction password");
                }
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

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    tv.setTextColor(getResources().getColor(R.color.black));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.white));
                }
            } else {
                // tv.setTextColor(getResources().getColor(R.color.unselectedTxtColor));
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

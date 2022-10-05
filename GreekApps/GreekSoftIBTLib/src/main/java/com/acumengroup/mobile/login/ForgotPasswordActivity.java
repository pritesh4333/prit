package com.acumengroup.mobile.login;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.CustomViewPager;
import com.acumengroup.ui.adapter.CustomFragmentPagerAdapter;

import java.util.ArrayList;

public class ForgotPasswordActivity extends AppCompatActivity {
    private final ArrayList<Fragment> pagesList = new ArrayList<>();
    private String pageSource = "";
    private LinearLayout mTabsLinearLayout;
    public GreekBaseFragment previousFragment;
    private PagerSlidingTabStrip tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_tabs_layout);
        setupViewPager();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupViewPager() {
        pagesList.clear();
        pagesList.add(ForgotPasswordFragment.newInstance(pageSource, "Login Password", previousFragment));

        boolean is_validateTransactionshow = Util.getPrefs(getApplicationContext()).getBoolean("is_validateTransactionshow", false);

        if (is_validateTransactionshow) {
            pagesList.add(ForgotPasswordFragment.newInstance(pageSource, "Transaction Password", previousFragment));
        }


        tabIndicator = findViewById(R.id.indicator);

        if (AccountDetails.getThemeFlag(getApplication()).equalsIgnoreCase("white")) {
            tabIndicator.setIndicatorColor(getResources().getColor(R.color.buttonColor));
            tabIndicator.setBackgroundColor(getResources().getColor(R.color.white));
            tabIndicator.setTextColor(getResources().getColor(R.color.black));
        }

        String[] heading = new String[3];
        heading[0] = "Login Password";
        heading[1] = "Transaction Password";

        CustomViewPager mPager = findViewById(R.id.pager);
        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), pagesList, heading);
        mPager.setAdapter(pagerAdapter);


        PagerSlidingTabStrip tabIndicator = findViewById(R.id.indicator);
        tabIndicator.setViewPager(mPager);

        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        tabIndicator.setTypeface(font, Typeface.NORMAL);

        mTabsLinearLayout = ((LinearLayout) tabIndicator.getChildAt(0));
        setIndicatorColor(0);
        mPager.setPagingEnabled(true);
        ((ForgotPasswordFragment) pagesList.get(0)).setPassType("login password");


        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicatorColor(position);
                if (position == 0) {
                    ((ForgotPasswordFragment) pagesList.get(position)).setPassType("login password");
                } else {
                    ((ForgotPasswordFragment) pagesList.get(position)).setPassType("transaction password");
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
                if (AccountDetails.getThemeFlag(getApplication()).equalsIgnoreCase("white")) {
                    tv.setTextColor(getResources().getColor(R.color.black));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.white));
                }
            } else {
                tv.setTextColor(getResources().getColor(R.color.unselectedTxtColor));
            }
        }
    }

}
package com.acumengroup.mobile.reports;

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

import com.acumengroup.pagersliderlib.PagerSlidingTabStrip;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.CustomViewPager;
import com.acumengroup.ui.adapter.CustomFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by user on 03-Jul-17.
 */

public class FundTransferFragment extends GreekBaseFragment {
    private final ArrayList<Fragment> pagesList = new ArrayList<>();
    private String pageSource = "";
    private LinearLayout mTabsLinearLayout;
    private  PagerSlidingTabStrip tabIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fundTransferView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fund_transfer_tabs).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fund_transfer_tabs).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_FUNDTRANSFER_SCREEN;
        setUpView(fundTransferView);
        setupViewPager(fundTransferView);

        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(0, 0);

        tabIndicator = fundTransferView.findViewById(R.id.indicator);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            tabIndicator.setIndicatorColor(getResources().getColor(R.color.buttonColor));
            tabIndicator.setBackgroundColor(getResources().getColor(R.color.white));
            tabIndicator.setTextColor(getResources().getColor(R.color.black));
        }
        return fundTransferView;

    }



    private void setUpView(View fundTransferView) {
        setAppTitle(getClass().toString(), "Fund Tranfer");

    }

    private void setupViewPager(View parent) {
        pagesList.clear();
        pagesList.add(FundTransfer_tabs_Fragment.newInstance(pageSource, "Add Fund", previousFragment));
        pagesList.add(FundTransfer_payout_fragment.newInstance(pageSource, "Withdraw Fund", previousFragment));

        String[] heading = new String[3];
        heading[0] = "Add Fund";
        heading[1] = "Withdraw Fund";


        CustomViewPager mPager = parent.findViewById(R.id.pager);
        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), pagesList, heading);
        mPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabIndicator = parent.findViewById(R.id.indicator);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        tabIndicator.setTypeface(font,Typeface.NORMAL);
        tabIndicator.setViewPager(mPager);


        mTabsLinearLayout = ((LinearLayout) tabIndicator.getChildAt(0));
        setIndicatorColor(0);
        //mPager.setPagingEnabled(true);
        //mTabsLinearLayout.getChildAt(1).setEnabled(false);
        //mPager.setPagingEnabled(false);


        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //pagerActivePage = position;
                setIndicatorColor(position);

                //     ((FundTransfer_tabs_Fragment) pagesList.get(position)).sendBankDetailsRequest(position);
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
                }else {
                    tv.setTextColor(getResources().getColor(R.color.white));
                }
//                tv.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
            } else {
//                tv.setTextColor(getResources().getColor(R.color.unselectedTxtColor));
                //tv.setBackgroundColor(getResources().getColor(R.color.bajaj_gray));
            }
        }
    }


    @Override
    public void onFragmentResume() {
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 3);
        //((FundTransfer_tabs_Fragment) pagesList.get(0)).sendDematHoldingRequest(0);
        super.onFragmentResume();
    }


    @Override
    public void onResume() {
        super.onResume();
        //  refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}

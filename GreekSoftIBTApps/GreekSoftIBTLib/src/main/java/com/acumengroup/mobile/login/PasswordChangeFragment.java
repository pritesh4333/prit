package com.acumengroup.mobile.login;

import android.os.Bundle;

import com.acumengroup.greekmain.util.Util;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


public class PasswordChangeFragment extends GreekBaseFragment {

    private TabLayout tabLayout;
    public static CustomViewPager viewPager;
    public boolean isExpanded = false;
    public static String PassExpiryType, Comingfrom;


    public PasswordChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {

            attachLayout(R.layout.fragment_password_change).setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
        } else {
            attachLayout(R.layout.fragment_password_change).setBackground(getResources().getDrawable(R.drawable.bg_drawable));
        }

        //View view = inflater.inflate(R.layout.fragment_password_change, container, false);

        PassExpiryType = getArguments().getString("PassExpiryType");
        Comingfrom = getArguments().getString("Comingfrom");


        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tablayout);
        setupViewPager(viewPager);
        setTheme();
        tabLayout.setupWithViewPager(viewPager);


        if (PassExpiryType.equalsIgnoreCase("LoginPass")) {


            viewPager.setCurrentItem(0, true);
            viewPager.setPagingEnabled(false);
//            ViewGroup tabitem = (ViewGroup) ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
//            tabitem.setClickable(false);


        } else if (PassExpiryType.equalsIgnoreCase("TransPass")) {

            viewPager.setCurrentItem(1, true);
            viewPager.setPagingEnabled(false);
//            ViewGroup tabitem = (ViewGroup) ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
//            tabitem.setClickable(false);


            LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }

        } else if (PassExpiryType.equalsIgnoreCase("BothPass")) {


        }

        return view;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            tabLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tabbar_backcolor_white));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.buttonColor));
        }
    }

    private void setupViewPager(CustomViewPager viewPager) {

        PasswordChangeFragment.ViewPagerAdapter adapter = new PasswordChangeFragment.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new LoginPasswordFragment(), "Login Password");

        boolean is_validateTransactionshow = Util.getPrefs(getMainActivity()).getBoolean("is_validateTransactionshow", false);

        if (is_validateTransactionshow) {
            adapter.addFrag(new TransPasswordFragment(), "Transaction Password");
        }
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

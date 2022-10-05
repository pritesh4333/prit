package com.acumengroup.mobile.Dashboard;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class HomeTabsFragment extends GreekBaseFragment {

    private TabLayout tabLayout;
    private CustomPager viewPager;
    private boolean isExpanded = false;
    public static ImageButton collapse;

    public HomeTabsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    public void onEventMainThread(final DashboardAnimate dashboardAnimate) {

        if (dashboardAnimate.getPosition() == 1) {

            isExpanded = dashboardAnimate.isExpand();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs_home, container, false);
        collapse = view.findViewById(R.id.collapsed);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tablayout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.amber_900));

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            GreekTextView tv = (GreekTextView)LayoutInflater.from(getMainActivity()).inflate(R.layout.custom_tab,null);
            //tv.setTypeface(Typeface.);
            tabLayout.getTabAt(i).setCustomView(tv);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isExpanded) {

                    collapse.setImageResource(R.drawable.ic_collapse);
                    DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
                    dashBoardCommunicate.setName("Home");
                    dashBoardCommunicate.setPosition(1);
                    dashBoardCommunicate.setExpand(true);
                    EventBus.getDefault().post(dashBoardCommunicate);
                    isExpanded = true;


                } else {

                    collapse.setImageResource(R.drawable.ic_expand);
                    DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
                    dashBoardCommunicate.setName("Home");
                    dashBoardCommunicate.setPosition(1);
                    dashBoardCommunicate.setExpand(false);
                    EventBus.getDefault().post(dashBoardCommunicate);
                    isExpanded = false;
                }
            }
        });

        return view;
    }


    private void setupViewPager(CustomPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new HomeBoardFragment(), "HOME");
        adapter.addFrag(new IndicesBoardFragment(), "INDICES");
        adapter.addFrag(new CommodityBoardFragment(), "COMMODITY");
        adapter.addFrag(new CurrencyBoardFragment(), "CURRENCY");
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

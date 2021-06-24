package com.acumengroup.mobile.portfolio;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.LabelConfig;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseActivity.USER;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SnapshotFloatingFragment;
import com.acumengroup.mobile.login.LoginActivity;
import com.acumengroup.mobile.viewpagerindicator.IconPagerAdapter;
import com.acumengroup.mobile.viewpagerindicator.TabPageIndicator;
import com.acumengroup.ui.CustomViewPager;
import com.acumengroup.ui.GreekDialog;

import java.util.ArrayList;

public class PortfolioSectionFragment extends GreekBaseFragment {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private CustomViewPager mPager;
    private LinearLayout mTabsLinearLayout;
    private RelativeLayout pagerFragmentPortfolioView;
    private FloatingActionButton snapshotFltBtn, watchlistFltBtn, lastFltBtn, dashboardFltBtn;
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View portFolioView = super.onCreateView(inflater, container, savedInstanceState);

        attachLayout(R.layout.pager_fragment_portfolio);
        AccountDetails.currentFragment = NAV_TO_WATCHLIST_SCREEN_SCREEN;
        ctx = getMainActivity();
        hideAppTitle();

        setupViews(portFolioView);
        return portFolioView;
    }


    private void setupViews(View portFolioView) {
        fragmentArrayList.clear();
//        int source = getArguments().getInt("Source");
        int source = 1;
        if (source == 0) {
            fragmentArrayList.add(new SnapshotFloatingFragment());
        }
        if (source == 1) {
            fragmentArrayList.add(new WatchListFragment());
        }
        if (source == 2) {
            fragmentArrayList.add(new LastVisitedFragment());
        }


        dashboardFltBtn = portFolioView.findViewById(R.id.flt_dashboard);
        snapshotFltBtn = portFolioView.findViewById(R.id.flt_snapshot);
        watchlistFltBtn = portFolioView.findViewById(R.id.flt_watchlist);
        lastFltBtn = portFolioView.findViewById(R.id.flt_last_visited);

        pagerFragmentPortfolioView = portFolioView.findViewById(R.id.pagerFragmentPortfolio);

        if (pagerFragmentPortfolioView != null) {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {


                pagerFragmentPortfolioView.setBackgroundColor(getResources().getColor(R.color.floatingBgColor));
            } else {

                pagerFragmentPortfolioView.setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
            }
        }


        String[] heading = new String[3];
//        heading[0] = "My Snapshot";
        heading[1] = "My Watchlist";
//        heading[2] = "Last Visited";

        mPager = portFolioView.findViewById(R.id.portfolioPager);
        FragmentStatePagerAdapter adapter = new PortfolioSectionAdapter(getChildFragmentManager(), fragmentArrayList, heading);
        mPager.setAdapter(adapter);
        mPager.setPagingEnabled(false);


        dashboardFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER || GreekBaseActivity.USER_TYPE == USER.MFCUSTOMER) {
                    ((GreekBaseActivity) getMainActivity()).performMenuAction(LabelConfig.GREEK_MENU_DASHBOARD_TXT);
                    ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(0, 0);

                } else if (GreekBaseActivity.USER_TYPE == USER.MFCUSTOMER) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        }
                    });

                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        }
                    });
                }
            }
        });


        snapshotFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                    AccountDetails.portfolio = true;
                    snapFocusEffect();
                    ((GreekBaseActivity) getMainActivity()).performMenuAction("GREEK_MENU_MYSTOCKS_TXT");

                } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        }
                    });
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        }
                    });
                }
            }
        });

        watchlistFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                watchlistFocusEffect();
                ((GreekBaseActivity) getMainActivity()).performMenuAction("GREEK_MENU_WATCHLIST_TXT");


            }
        });

        lastFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                    lastVisitedFocusEffect();
                    ((GreekBaseActivity) getMainActivity()).performMenuAction("GREEK_MENU_LASTVISITED_TXT");
                } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", false, new GreekDialog.DialogListener() {
                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        }
                    });
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        }
                    });
                }
            }
        });
        final TabPageIndicator indicator = portFolioView.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        mTabsLinearLayout = ((LinearLayout) indicator.getChildAt(0));

        indicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(1, position);

                setIndicatorColor(position);
                switch (position) {

                    case 0:
                        if (GreekBaseActivity.USER_TYPE == USER.OPENUSER) {
                            openAccount();
                        } else {
                            AccountDetails.portfolio = true;
                            snapFocusEffect();
                            ((SnapshotFloatingFragment) fragmentArrayList.get(position)).sendSnapshotsRequest();
                        }
                        break;
                    case 1:

                        watchlistFocusEffect();
                        ((WatchListFragment) fragmentArrayList.get(position)).sendGroupNameRequest();
                        break;
                    case 2:
                        if (GreekBaseActivity.USER_TYPE == USER.OPENUSER) {
                            openAccount();
                        } else {
                            lastVisitedFocusEffect();
                            ((LastVisitedFragment) fragmentArrayList.get(position)).sendLastVisitedRequest();
                        }
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        mPager.postDelayed(new Runnable() {
            @Override
            public void run() {

                int source = getArguments().getInt("Source");
                mPager.setCurrentItem(source, false);

                ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(1, source);
            }
        }, 100);
    }

    private void setIndicatorColor(int position) {
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            if (i == position) {
                tv.setTextColor(Color.BLACK);
            } else {
                tv.setTextColor(Color.parseColor("#7E7E7E"));
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void snapFocusEffect() {

        AccountDetails.currentFragment = NAV_TO_WATCHLIST_SCREEN_SCREEN;

        if (getActivity() != null && getMainActivity() != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AccountDetails.portfolio = true;
                AccountDetails.watchlist = false;
                AccountDetails.lastvisited = false;

                snapshotFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.scrip_selected_icon, getActivity().getTheme()));
                watchlistFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_watchlist, getActivity().getTheme()));
                lastFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_last_white_24dp, getActivity().getTheme()));

                snapshotFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                watchlistFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                lastFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            } else {
                AccountDetails.portfolio = true;
                AccountDetails.watchlist = false;
                AccountDetails.lastvisited = false;
                snapshotFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.scrip_selected_icon));
                watchlistFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_watchlist));
                lastFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_last_white_24dp));
                snapshotFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                watchlistFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                lastFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            }
        }

    }

    public void watchlistFocusEffect() {
        if (getActivity() != null && getMainActivity() != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AccountDetails.portfolio = false;
                AccountDetails.watchlist = true;
                AccountDetails.lastvisited = false;
                AccountDetails.send_portfolio_req = false;
                snapshotFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_snapshot, getActivity().getTheme()));
                watchlistFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_watchlist, getActivity().getTheme()));
                lastFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_last_white_24dp, getActivity().getTheme()));
                snapshotFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                watchlistFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                lastFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            } else {
                AccountDetails.portfolio = false;
                AccountDetails.watchlist = true;
                AccountDetails.lastvisited = false;
                AccountDetails.send_portfolio_req = false;
                snapshotFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_snapshot));
                watchlistFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_watchlist));
                lastFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_last_white_24dp));
                snapshotFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                watchlistFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                lastFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
            }
        }
    }

    public void lastVisitedFocusEffect() {

        if (getActivity() != null && getMainActivity() != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AccountDetails.portfolio = false;
                AccountDetails.watchlist = false;
                AccountDetails.lastvisited = true;
                AccountDetails.send_portfolio_req = false;
                snapshotFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_snapshot, getMainActivity().getTheme()));
                watchlistFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_watchlist, getMainActivity().getTheme()));
                lastFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_last_red_24dp, getMainActivity().getTheme()));
                snapshotFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                watchlistFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                lastFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            } else {
                AccountDetails.portfolio = false;
                AccountDetails.watchlist = false;
                AccountDetails.lastvisited = true;
                AccountDetails.send_portfolio_req = false;
                snapshotFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_snapshot));
                watchlistFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.my_watchlist));
                lastFltBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_last_red_24dp));
                snapshotFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                watchlistFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.common_red_bg));
                lastFltBtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onFragmentPause() {


        AccountDetails.send_portfolio_req = false;
        if (getStreamingSymbolList("ltpinfo") != null) {
            streamController.pauseStreaming(getActivity(), "ltpinfo", getStreamingSymbolList("ltpinfo"));
        }


    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_WATCHLIST_SCREEN_SCREEN;
        if (AccountDetails.watchlist) {
            if (getChildFragmentManager().getFragments() != null) {
                for (int i = 0; i < getChildFragmentManager().getFragments().size(); i++) {
                    GreekBaseFragment fragment = (GreekBaseFragment) getChildFragmentManager().getFragments().get(i);
                    if (fragment instanceof WatchListFragment)
                        fragment.onFragmentResume();
                }
            }
        } else if (AccountDetails.lastvisited) {
            if (getChildFragmentManager().getFragments() != null) {
                for (int i = 0; i < getChildFragmentManager().getFragments().size(); i++) {
                    GreekBaseFragment fragment = (GreekBaseFragment) getChildFragmentManager().getFragments().get(i);
                    if (fragment instanceof LastVisitedFragment)
                        fragment.onFragmentResume();
                }
            }
        }
    }


    private void openAccount() {
        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

            @Override
            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                Intent i = new Intent(getMainActivity(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    class PortfolioSectionAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {
        private final int[] ICONS = new int[]{R.drawable.ic_portfolio_portfolio, R.drawable.portfolio_selector, R.drawable.ic_portfolio_lastviewed};
        private final String[] pageTitles;
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public PortfolioSectionAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, String[] pageTitles) {
            super(fm);
            this.pageTitles = pageTitles;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles[position];
        }

        @Override
        public int getIconResId(int index) {
            return ICONS[index];
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
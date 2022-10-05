package com.acumengroup.mobile.markets;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.mobile.viewpagerindicator.TabPageIndicator;
import com.acumengroup.ui.edittext.GreekEditText;

import java.util.ArrayList;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by Arcadia
 */
public class NewsFragmentPager extends GreekBaseFragment {
    private GreekEditText searchBar;
    private final View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openSearchPage();
        }
    };
    private View newsView;
    private LinearLayout mTabsLinearLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.pager_fragment_reports_news).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.pager_fragment_reports_news).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setAppTitle(getClass().toString(), "News");
        AccountDetails.currentFragment = NAV_TO_NEWS_PAGER;
        setupViews();

        return newsView;
    }

    private void openSearchPage() {
        String text = searchBar.getText().toString();
        searchBar.setText("");
        if (!text.equalsIgnoreCase("")) {
            Bundle args = new Bundle();
            args.putString("searchText", text);
            navigateTo(NAV_TO_NEWS_SEARCH, args, true);
        } else {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.CP_ENTER_TEXT_MSG), "OK", true, null);
        }
    }

    private void setIndicatorColor(int position) {
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            if (i == position) {
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    tv.setTextColor(Color.WHITE);
                } else {
                    tv.setTextColor(Color.WHITE);
                }

            } else {
                tv.setTextColor(Color.parseColor("#7E7E7E"));
            }
        }
    }

    private void setupViews() {
        final ArrayList<Fragment> pageList = new ArrayList<>();


        searchBar = newsView.findViewById(R.id.inputSearch);
        searchBar.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    openSearchPage();
                }
                return true;
            }
        });

        ImageView imgSearch = newsView.findViewById(R.id.iconSearch);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            searchBar.setTextColor(getResources().getColor(R.color.black));
            imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));

        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

            searchBar.setTextColor(getResources().getColor(R.color.white));
            imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_white_24dp));
        }
        if(getArguments() != null) {
            if (getArguments().containsKey("Symbol")) {
                searchBar.setText(getArguments().getString("Symbol"));
                openSearchPage();
            }
        }
        else {
            ImageView searchButton = newsView.findViewById(R.id.iconSearch);
            searchButton.setOnClickListener(searchClickListener);

            pageList.add(NewsFragment.newInstance(0, "MarketNews"));
        /*pageList.add(NewsFragment.newInstance(1, "CommodityNews"));
        pageList.add(NewsFragment.newInstance(2, "MFNews"));
        pageList.add(NewsFragment.newInstance(3, "IPONews"));
        pageList.add(NewsFragment.newInstance(4, "IndustryNews"));
        pageList.add(NewsFragment.newInstance(5, "PortfolioNews"));*/

            String[] heading = new String[6];
            heading[0] = "Market News";
        /*heading[1] = "Commodity";
        heading[2] = "Mutual Funds";
        heading[3] = "IPO's";
        heading[4] = "Industry";
        heading[5] = "My Portfolio";*/

            ViewPager mPager = newsView.findViewById(R.id.pager);
            FragmentStatePagerAdapter adapter = new NewsPagerAdapter(getChildFragmentManager(), pageList, heading);
            mPager.setAdapter(adapter);

            final TabPageIndicator indicator = newsView.findViewById(R.id.indicator);
            indicator.setViewPager(mPager);
            mTabsLinearLayout = ((LinearLayout) indicator.getChildAt(0));
            setIndicatorColor(0);
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setIndicatorColor(position);
                    ((NewsFragment) pageList.get(position)).sendMarketNewsRequest();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            mPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((NewsFragment) pageList.get(0)).sendMarketNewsRequest();
                }
            }, 100);
        }
    }

    class NewsPagerAdapter extends FragmentStatePagerAdapter {
        private final String[] pageTitles;
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public NewsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, String[] pageTitles) {
            super(fm);
            this.fragmentList = fragmentList;
            this.pageTitles = pageTitles;
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
        public int getCount() {
            return fragmentList.size();
        }
    }
}
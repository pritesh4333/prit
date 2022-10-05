package com.acumengroup.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class GreekFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] pageTitles;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();


    /**
     * Constructor of the class
     */
    public GreekFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, String[] pageTitles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.pageTitles = pageTitles;
    }

    public void setPageTitles(String[] pageTitles) {
        this.pageTitles = pageTitles;
    }

    /**
     * This method will be invoked when a page is requested to create
     */
    @Override
    public Fragment getItem(int index) {
        return fragmentList.get(index);
    }

    /**
     * Returns the number of pages
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (pageTitles != null) return pageTitles[position];
        else return "";

    }

}

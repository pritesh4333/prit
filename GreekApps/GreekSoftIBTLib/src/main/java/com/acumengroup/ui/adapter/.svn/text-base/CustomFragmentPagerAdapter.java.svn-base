package com.acumengroup.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

/**
 * Created by Arcadia
 */
public class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] heading;
    private ArrayList<Fragment> fragmentList;

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CustomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, String[] pageTitles) {
        super(fm);
        this.heading = pageTitles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {

        if (fragmentList.get(position) != null)
            return fragmentList.get(position);
        else
            return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return heading[position];
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void deleteItem(int position) {
        fragmentList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}

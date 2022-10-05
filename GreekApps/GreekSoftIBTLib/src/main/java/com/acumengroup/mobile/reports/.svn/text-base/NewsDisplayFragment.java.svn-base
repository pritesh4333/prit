package com.acumengroup.mobile.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

/**
 * Created by user on 09-Sep-16.
 */
public class NewsDisplayFragment extends GreekBaseFragment {

    private View newsDisplayView;
    private GreekTextView text_news_label;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsDisplayView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_news_display).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_news_display).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_NEWS_DISPLAY;
        text_news_label = newsDisplayView.findViewById(R.id.text_news_recomm);
        text_news_label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        setAppTitle(getClass().toString(), "News");
        return newsDisplayView;
    }


}

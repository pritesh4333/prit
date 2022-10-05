package com.acumengroup.mobile.markets;

import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.acumengroup.mobile.markets.NewsFragment.removeCharAt;

/**
 * Created by Arcadia
 */
public class NewsDetailFragment extends GreekBaseFragment {
    public static final String ID = "id";
    public static final String HEADING = "Heading";
    public static final String FROM_PAGE = "From";
    public static final String SECTION_NAME = "Section_name";
    public static final String SUMMARY = "Arttext";
    public static final String DATE_FORMAT = "dateformat";
    GreekTextView titleText, dateText, categoryText, summaryText;
    private View mDetailNewsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDetailNewsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_news_report_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_news_report_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setupViews();

        return mDetailNewsView;
    }

    private void setupViews() {
        titleText = mDetailNewsView.findViewById(R.id.headingText);
        titleText.setText(getArguments().getString(HEADING));
        dateText = mDetailNewsView.findViewById(R.id.dateText);
        dateText.setText(getArguments().getString(DATE_FORMAT));
        categoryText = mDetailNewsView.findViewById(R.id.categoryText);
        categoryText.setText(getArguments().getString(SECTION_NAME));
        summaryText = mDetailNewsView.findViewById(R.id.summaryText);
        LinearLayout buttonLayout = mDetailNewsView.findViewById(R.id.bottomBtnLayout);
        buttonLayout.setVisibility(View.GONE);

        dateText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        categoryText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        summaryText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        if ("Quotes".equals(getArguments().getString(FROM_PAGE))) {
            summaryText.setText(Html.fromHtml(getArguments().getString(SUMMARY)));
        } else {
            sendNewsDetailRequest();
        }
    }

    private void sendNewsDetailRequest() {
        showProgress();
        WSHandler.getRequest(getMainActivity(), "getNewsForId?id=" + getArguments().getString(ID), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
                try {
                    JSONArray array = response.getJSONArray("data");
                    JSONObject object = array.getJSONObject(0);
                    //summaryText.setText(Html.fromHtml(new String(Base64.decode(object.optString("Arttext"), Base64.DEFAULT))));
                    summaryText.setText(Html.fromHtml(removeCharAt(new String(Base64.decode(object.optString("Arttext"), Base64.DEFAULT)), 0)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }
}

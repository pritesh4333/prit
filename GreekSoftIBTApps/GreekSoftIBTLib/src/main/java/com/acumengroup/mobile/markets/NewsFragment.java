package com.acumengroup.mobile.markets;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.login.LoginActivity;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CommonAdapter;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.adapter.PopulationListener;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by snehaganapuram on 8/10/2015.
 */
public class NewsFragment extends GreekBaseFragment {

    RelativeLayout errorMsgLayout;
    private JSONObject newsData;
    private ListView newsList;
    private CommonAdapter commonAdapter;
    private final AdapterView.OnItemClickListener newsDetailListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            CommonRowData rowData = (CommonRowData) commonAdapter.getItem(arg2);
            Bundle args = new Bundle();
            args.putString(NewsDetailFragment.ID, rowData.getSubHead1());
            args.putString(NewsDetailFragment.HEADING, rowData.getSubHead2());
            args.putString(NewsDetailFragment.DATE_FORMAT, rowData.getSubHead3());
            args.putString(NewsDetailFragment.SECTION_NAME, rowData.getSubHead4());
            navigateTo(NAV_TO_NEWS_DETAIL, args, true);
        }
    };
    private SwipeRefreshLayout swipeRefresh;
    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            newsData = null;
            sendMarketNewsRequest();
        }
    };

    public static NewsFragment newInstance(int position, String type) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        args.putString("Type", type);
        fragment.setArguments(args);
        return fragment;
    }

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newsView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_news).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_news).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setupViews(newsView);

        return newsView;
    }

    private void setupViews(View parent) {

        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        newsList = parent.findViewById(R.id.listNews);
        errorMsgLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        setupAdapter();
        setAppTitle(getClass().toString(), "News");
    }

    private void setupAdapter() {
        int[] newsViewIDs = {R.id.dateUpper, R.id.dateLower, R.id.newsTxt, R.id.newsdetailTxt, R.id.timestamp};

        commonAdapter = new CommonAdapter(getMainActivity(), new ArrayList<CommonRowData>());
        commonAdapter.notifyDataSetChanged();
        commonAdapter.setLayoutTextViews(R.layout.row_news, newsViewIDs);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            commonAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
        } else {
            commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        }
        commonAdapter.setPopulationListener(new PopulationListener() {
            @Override
            public void populateFrom(View v, int position, CommonRowData row, View[] views) {
                ((GreekTextView) views[0]).setText(row.getHead1());
                ((GreekTextView) views[1]).setText(row.getHead2());
                ((GreekTextView) views[2]).setText(Html.fromHtml(row.getHead3()));
                ((GreekTextView) views[3]).setText(Html.fromHtml(removeCharAt(row.getNewsDetails(), 0)));
                ((GreekTextView) views[4]).setText(row.getHead4());

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

                    ((GreekTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

                    ((GreekTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }

            @Override
            public void onRowCreate(View[] views) {
            }
        });
        newsList.setAdapter(commonAdapter);
        newsList.setOnItemClickListener(newsDetailListener);
    }

    public void sendMarketNewsRequest() {
        if (newsData != null) handleNewsResponse();
        else {
            if (getArguments().getString("Type").equals("PortfolioNews") && GreekBaseActivity.USER_TYPE.equals(GreekBaseActivity.USER.OPENUSER)) {
                GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        Intent i = new Intent(getMainActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });
            } else {
                showProgress();
                String newsUrl = "getPortfolioNews?sectionType=" + "equity" + "&ClientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&topN=10";
                WSHandler.getRequest(getMainActivity(), newsUrl, new WSHandler.GreekResponseCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        toggleErrorLayout(false);
                        refreshComplete();
                        commonAdapter.clear();
                        commonAdapter.notifyDataSetChanged();
                        Log.e("CustomLoopj", response.toString());
                        newsData = response;
                        handleNewsResponse();
                    }

                    @Override
                    public void onFailure(String message) {
                        toggleErrorLayout(true);
                        if (newsList.getVisibility() == View.VISIBLE) {
                            newsList.setVisibility(View.GONE);
                        }
                        refreshComplete();
                    }
                });
            }
        }
    }

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void handleNewsResponse() {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();


        if (newsData == null) return;
        try {
            JSONArray data = newsData.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                newsList.setVisibility(View.VISIBLE);
                JSONObject object = data.getJSONObject(i);
                String type;

                if (object.opt("Section_name").toString().equalsIgnoreCase("commodity")) {
                    type = "nse";
                } else {
                    type = "bse";
                }
                CommonRowData commonRow = new CommonRowData();
                commonRow.setHead1(DateTimeFormatter.getDateFromTimeStamp(object.optString("dateformat"), "MMM", type));
                commonRow.setHead2(DateTimeFormatter.getDateFromTimeStamp(object.optString("dateformat"), "dd", type));
                commonRow.setHead3(new String(Base64.decode(object.optString("Heading"), Base64.DEFAULT)));
                commonRow.setNewsDetails(new String(Base64.decode(object.optString("Arttext"), Base64.DEFAULT)));
                commonRow.setHead4((DateTimeFormatter.getDateFromTimeStampForNews(object.optString("dateformat"), "hh:mm:ss", type)).toLowerCase());
                commonRow.setSubHead1(object.optString("Sno"));
                commonRow.setSubHead2(new String(Base64.decode(object.optString("Heading"), Base64.DEFAULT)));
                commonRow.setSubHead3(DateTimeFormatter.getDateFromTimeStampForNews(object.optString("dateformat"), "dd MMM yyyy", type));
                commonRow.setSubHead4(object.optString("Section_name"));
                commonAdapter.add(commonRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
            toggleErrorLayout(true);
        }

    }


    public static String removeCharAt(String s, int pos) {
        if (s.equalsIgnoreCase(":")) {
            return s.substring(0, pos) + s.substring(pos + 1);
        }
        return s;
    }
}
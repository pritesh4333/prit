package com.acumengroup.mobile.markets;

import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.loopj.android.http.RequestParams;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CommonAdapter;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.adapter.PopulationListener;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.acumengroup.mobile.markets.NewsFragment.removeCharAt;

/**
 * Created by Arcadia
 */
public class NewsSearchFragment extends GreekBaseFragment {
    private GreekEditText edtSearch;
    private final View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!edtSearch.getText().toString().equalsIgnoreCase("")) {
                sendSearchRequest();
            } else {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.CP_ENTER_TEXT_MSG), "OK", true, null);
            }

        }
    };
    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (!edtSearch.getText().toString().equalsIgnoreCase("")) {
                sendSearchRequest();
            } else {
                refreshComplete();
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.CP_ENTER_TEXT_MSG), "OK", true, null);
            }
        }
    };
    private GreekTextView errorText;
    private RelativeLayout  errorMsgLayout;
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


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_search_news).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_search_news).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setupViews(newsView);
        return newsView;
    }

    private void setupViews(View parent) {
        newsList = parent.findViewById(R.id.listNews);
        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        edtSearch = parent.findViewById(R.id.inputSearch);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    if (!edtSearch.getText().toString().equalsIgnoreCase("")) {
                        sendSearchRequest();
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.CP_ENTER_TEXT_MSG), "OK", true, null);
                    }
                    InputMethodManager inputMethodManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }
        });
        edtSearch.setVisibility(View.VISIBLE);
        edtSearch.setText(getArguments().getString("searchText"));
        ImageView imgSearch = parent.findViewById(R.id.iconSearch);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            edtSearch.setTextColor(getResources().getColor(R.color.black));
            imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));

        } else {
            edtSearch.setTextColor(getResources().getColor(R.color.white));
            imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_white_24dp));
        }

        imgSearch.setVisibility(View.VISIBLE);
        imgSearch.setOnClickListener(searchClickListener);
        View view = parent.findViewById(R.id.view);
        view.setVisibility(View.VISIBLE);
        errorMsgLayout = parent.findViewById(R.id.showmsgLayout);
        errorText = parent.findViewById(R.id.errorHeader);
        errorText.setText("No data available");
        setupAdapter();
        if (!edtSearch.getText().toString().equalsIgnoreCase("")) {
            sendSearchRequest();
        } else {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.CP_ENTER_TEXT_MSG), "OK", true, null);
        }
        setAppTitle(getClass().toString(), "News");
    }

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void sendSearchRequest() {
        showProgress();
        RequestParams params = new RequestParams();
        params.add("search", edtSearch.getText().toString());
        params.add("top", "10");
        String findNewsUrl = "findNews?search=" + edtSearch.getText().toString() + "&top=10";
        WSHandler.getRequest(getMainActivity(), findNewsUrl, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                toggleErrorLayout(false);
                refreshComplete();
                commonAdapter.clear();
                commonAdapter.notifyDataSetChanged();
                Log.e("CustomLoopj", response.toString());

                try {
                    JSONArray array = response.getJSONArray("data");
                    if (array.length() > 0) {
                        errorText.setVisibility(View.GONE);
                        errorMsgLayout.setVisibility(View.GONE);
                        newsList.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            CommonRowData commonRow = new CommonRowData();
                            /*commonRow.setHead1(DateTimeFormatter.getDateFromTimeStamp(object.getString("dateformat"), "MMM", "bse"));
                            commonRow.setHead2(DateTimeFormatter.getDateFromTimeStamp(object.getString("dateformat"), "dd", "bse"));
                            //commonRow.setHead3(object.getString("Heading"));
                            final byte[] decodedHead = Base64.decode(object.getString("Heading"), Base64.DEFAULT);
                            commonRow.setHead3(String.valueOf(Html.fromHtml(new String(decodedHead))));
                            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                            commonRow.setHead4(DateTimeFormatter.getDateFromTimeStamp(object.optString("Time"), "HH:mm", "bse"));
                            commonRow.setSubHead1(object.optString("Sno"));
                            final byte[] decodedHead1 = Base64.decode(object.getString("Heading"), Base64.DEFAULT);
                            commonRow.setSubHead2(String.valueOf(Html.fromHtml(new String(decodedHead))));
                            commonRow.setSubHead3(DateTimeFormatter.getDateFromTimeStamp(object.optString("dateformat"), "dd MMM yyyy", "bse"));
                            commonRow.setSubHead4(object.optString("Section_name"));
                            commonAdapter.add(commonRow);*/
                            String type;
                            if (object.opt("Section_name").toString().equalsIgnoreCase("commodity")) {
                                type = "nse";
                            } else {
                                type = "bse";
                            }
                            //CommonRowData commonRow = new CommonRowData();
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
                    } else {
                        newsList.setVisibility(View.GONE);
                        errorMsgLayout.setVisibility(View.VISIBLE);
                        errorText.setVisibility(View.VISIBLE);
                        errorText.setText("No Results Found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toggleErrorLayout(true);
                }
            }

            @Override
            public void onFailure(String message) {
                toggleErrorLayout(true);
                if (newsList.getVisibility() == View.VISIBLE) {
                    newsList.setVisibility(View.GONE);
                    errorText.setVisibility(View.VISIBLE);
                }
                refreshComplete();
            }
        });
    }

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
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
}

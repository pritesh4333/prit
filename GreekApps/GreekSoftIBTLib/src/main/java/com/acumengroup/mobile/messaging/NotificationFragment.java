package com.acumengroup.mobile.messaging;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.adapter.CommonAdapter;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.adapter.PopulationListener;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Arcadia
 */
public class NotificationFragment extends GreekBaseFragment {
    private SwipeRefreshLayout swipeRefresh;
    RelativeLayout errorMsgLayout;
    private ListView notificationList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View notificationView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_notification).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_notification).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        //MyGcmListenerService.notificationCount = 0;
        setAppTitle(getClass().toString(), "Notifications");
        setupViews(notificationView);
        return notificationView;
    }

    private void setupViews(View parent) {
        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        notificationList = parent.findViewById(R.id.listNotification);
        errorMsgLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.black));
        }else{
            ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.white));

        }
        setupAdapter();
        sendNotificationRequest();
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            notificationData = null;
            sendNotificationRequest();
        }
    };

    public static NotificationFragment newInstance(int position, String type) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        args.putString("Type", type);
        fragment.setArguments(args);
        return fragment;
    }

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    public void sendNotificationRequest() {
        if (notificationData != null) handleNotificationResponse();
        else {
            showProgress();
            String notificationUrl = "getPushNotificationLog?duration=day";
            WSHandler.getRequest(getMainActivity(), notificationUrl, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    toggleErrorLayout(false);
                    refreshComplete();
                    commonAdapter.clear();
                    commonAdapter.notifyDataSetChanged();
                    Log.e("CustomLoopj", response.toString());
                    notificationData = response;
                    handleNotificationResponse();
                }

                @Override
                public void onFailure(String message) {
                    toggleErrorLayout(true);
                    if (notificationList.getVisibility() == View.VISIBLE) {
                        notificationList.setVisibility(View.GONE);
                    }
                    refreshComplete();
                }
            });
        }
    }

    JSONObject notificationData;

    private void handleNotificationResponse() {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();
        if (notificationData == null) return;
        try {
            JSONArray data = notificationData.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                notificationList.setVisibility(View.VISIBLE);
                JSONObject object = data.getJSONObject(i);
                CommonRowData commonRow = new CommonRowData();
                commonRow.setHead1(DateTimeFormatter.getDateFromTimeStamp(object.optString("created_on"), "MMM", "bse"));
                commonRow.setHead2(DateTimeFormatter.getDateFromTimeStamp(object.optString("created_on"), "dd", "bse"));
                commonRow.setHead3(object.optString("PushMessage"));
                commonRow.setHead4(DateTimeFormatter.getDateFromTimeStamp(object.optString("created_on"), "h:mm a", "bse").toLowerCase());
                commonAdapter.add(commonRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
            toggleErrorLayout(true);
        }
    }

    private CommonAdapter commonAdapter;

    private void setupAdapter() {
        int[] notificationViewIDs = {R.id.dateUpper, R.id.dateLower, R.id.newsTxt, R.id.timestamp};

        commonAdapter = new CommonAdapter(getMainActivity(), new ArrayList<CommonRowData>());
        commonAdapter.notifyDataSetChanged();
        commonAdapter.setLayoutTextViews(R.layout.row_notification, notificationViewIDs);
        commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.light_white), getResources().getColor(R.color.white));
        commonAdapter.setPopulationListener(new PopulationListener() {
            @Override
            public void populateFrom(View v, int position, CommonRowData row, View[] views) {
                ((GreekTextView) views[0]).setText(row.getHead1());
                ((GreekTextView) views[1]).setText(row.getHead2());
                ((GreekTextView) views[2]).setText(Html.fromHtml(row.getHead3()));
                ((GreekTextView) views[3]).setText(row.getHead4());
            }

            @Override
            public void onRowCreate(View[] views) {
            }
        });
        notificationList.setAdapter(commonAdapter);
        //notificationList.setOnItemClickListener(newsDetailListener);
    }
}

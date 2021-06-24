package com.acumengroup.mobile.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acumengroup.greekmain.core.app.AccountDetails;

import com.acumengroup.greekmain.core.model.recommonDisplay.RecomData;
import com.acumengroup.greekmain.core.model.recommonDisplay.RecommDisplayRequest;
import com.acumengroup.greekmain.core.model.recommonDisplay.RecommDisplayResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BottomTabScreens.NewsTabFragment;
import com.acumengroup.mobile.GreekBaseFragment;

import com.acumengroup.mobile.R;
import com.acumengroup.mobile.RecomRecyclerAdapter;
import com.acumengroup.mobile.model.NewsDataModel;
import com.acumengroup.ui.adapter.RecommAdapter;
import com.acumengroup.ui.textview.GreekTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by user on 09-Sep-16.
 */
public class RecommDisplayFragment extends GreekBaseFragment {

    private View mChangePwdView;
    private RelativeLayout errorLayout;
    private RecyclerView recyclerView;
    private RecomRecyclerAdapter recyclerView_recomAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChangePwdView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.frag_recomm).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.frag_recomm).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_RECOMMENDATION_SCREEN;

        recyclerView = mChangePwdView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        errorLayout = mChangePwdView.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.black));
        }else{
            ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.white));

        }
            setAppTitle(getClass().toString(), "Recommendations");

        RecommDisplayRequest.sendRequest(AccountDetails.getUsername(getActivity()), getActivity(), serviceResponseHandler);


        return mChangePwdView;
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            RecommDisplayResponse recommDisplayResponse = (RecommDisplayResponse) jsonResponse.getResponse();

            ArrayList<RecomData> recommListDataArrayLists = null;
            RecomData recomData = null;

            recommListDataArrayLists = new ArrayList<>();
            for (int i = 0; i < recommDisplayResponse.getData().size(); i++) {
                recomData = new RecomData();
                String pattern = "yyyy-MM-dd HH:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

                Date D = new Date(Long.parseLong(recommDisplayResponse.getData().get(i).getlLogTime()) * 1000);
                String date = simpleDateFormat.format(D);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.parseLong(recommDisplayResponse.getData().get(i).getlLogTime()) * 1000);
                //  Log.v("SSSS long time",new SimpleDateFormat("yyyy-MM-ddhh:mm:ss").parse(date).getTime()+"");
                recomData.setlLogTime(simpleDateFormat.format(cal.getTime()));
                recomData.setcMessage(recommDisplayResponse.getData().get(i).getcMessage());
                recomData.setcRecieverId(recommDisplayResponse.getData().get(i).getcRecieverId());
                recomData.setcSenderId(recommDisplayResponse.getData().get(i).getcSenderId());
                recomData.setiMsgType(recommDisplayResponse.getData().get(i).getiMsgType());
                recommListDataArrayLists.add(recomData);
            }

            if (recommListDataArrayLists.size() > 0) {
                toggleErrorLayout(false);
                recyclerView_recomAdapter = new RecomRecyclerAdapter(recommListDataArrayLists, getMainActivity());
                recyclerView.setAdapter(recyclerView_recomAdapter);
                recyclerView_recomAdapter.notifyDataSetChanged();
            } else {
                toggleErrorLayout(true);
            }
            hideProgress();

        } catch (Exception e) {
            e.printStackTrace();
            toggleErrorLayout(true);
            hideProgress();
        }
    }
}

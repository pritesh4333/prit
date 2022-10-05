package com.acumengroup.mobile.Dashboard;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;

import java.util.Timer;

import de.greenrobot.event.EventBus;

public class DashBoardFragment extends GreekBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FragmentTransaction trans_nome, trans_market, trans_derivative;
    private View dashBoardView;
    private FrameLayout home_frameLayout, market_frameLayout, derivative_frameLayout;
    private LinearLayout linearLayout;
    public static Timer requestTimer;
    public static Timer derivative_Timer;


    public DashBoardFragment() {

    }

    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onEventMainThread(final DashboardAnimate dashboardAnimate) {

        if (dashboardAnimate.isShowProgress()) {
            showProgress();
        } else {
            hideProgress();
        }

        onImageSelected(dashboardAnimate.getName(), dashboardAnimate.getPosition(), dashboardAnimate.isExpand(), dashboardAnimate.getListview_height());

    }

    public void onImageSelected(String FragmentName, int position, boolean isExpanded, int listheight) {

        if (position == 2) {

            if (isExpanded) {
                home_frameLayout.animate().translationZ(1000).setDuration(5000);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                market_frameLayout.setLayoutParams(lp);

                LinearLayout.LayoutParams lph = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                home_frameLayout.setLayoutParams(lph);
                home_frameLayout.setVisibility(View.GONE);
                LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                derivative_frameLayout.setLayoutParams(lpd);
                derivative_frameLayout.setVisibility(View.GONE);


                int height = home_frameLayout.getHeight();
                onSlideUp(height);


            } else {

                market_frameLayout.animate().translationYBy(0).setDuration(5000);

                home_frameLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lph = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                home_frameLayout.setLayoutParams(lph);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                market_frameLayout.setLayoutParams(lp);

                derivative_frameLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                derivative_frameLayout.setLayoutParams(lpd);

            }


        } else if (position == 3) {


            if (isExpanded) {

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                derivative_frameLayout.setLayoutParams(lp);

                LinearLayout.LayoutParams lph = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                home_frameLayout.setLayoutParams(lph);
                home_frameLayout.setVisibility(View.GONE);

                LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                market_frameLayout.setLayoutParams(lpd);
                market_frameLayout.setVisibility(View.GONE);

                int height = home_frameLayout.getHeight() + market_frameLayout.getHeight();
                onSlideUp(height);


            } else {

                home_frameLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lph = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                home_frameLayout.setLayoutParams(lph);

                market_frameLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                market_frameLayout.setLayoutParams(lp);


                LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                derivative_frameLayout.setLayoutParams(lpd);

            }

        } else if (position == 1) {

            if (isExpanded) {


                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                home_frameLayout.setLayoutParams(lp);

                LinearLayout.LayoutParams lph = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                derivative_frameLayout.setLayoutParams(lph);
                derivative_frameLayout.setVisibility(View.GONE);

                LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                market_frameLayout.setLayoutParams(lpd);
                market_frameLayout.setVisibility(View.GONE);

                int height = 0;
                onSlideUp(height);

            } else {

                LinearLayout.LayoutParams lph = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                home_frameLayout.setLayoutParams(lph);

                market_frameLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                market_frameLayout.setLayoutParams(lp);

                derivative_frameLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                derivative_frameLayout.setLayoutParams(lpd);

                int height = 0;
                onSlideDown(height);

            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestTimer = new Timer();
        derivative_Timer = new Timer();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFragmentResume() {

        sendStreamingRequest();
        if (AccountDetails.currentFragment == NAV_TO_SYMBOL_SEARCH_SCREEN || AccountDetails.currentFragment == NAV_TO_QUOTES_SCREEN || AccountDetails.currentFragment == NAV_TO_TRADE_SCREEN) {
            AccountDetails.currentFragment = NAV_TO_MARKET_HOME_SCREEN;
        } else {
            //AccountDetails.currentFragment = NAV_TO_DASHBOARD_SCREEN;
        }
    }

    private void sendStreamingRequest() {

        if (AccountDetails.getDashboardList() != null && AccountDetails.getDashboardList().size() > 0) {
            if (streamController != null) {
                streamController.sendStreamingRequest(getMainActivity(), AccountDetails.getDashboardList(), "ltpinfo", null, null, false);
            }
        }

    }

    @Override
    public void onPause() {
        if(requestTimer != null) {
            requestTimer.cancel();
            requestTimer = null;
        }
        if(derivative_Timer != null) {
            derivative_Timer.cancel();
            derivative_Timer = null;

        }
        super.onPause();
    }

    @Override
    public void onFragmentPause() {
        if(requestTimer != null) {
            requestTimer.cancel();
            requestTimer = null;
        }
        if(derivative_Timer != null) {
            derivative_Timer.cancel();
            derivative_Timer = null;
        }
        SendPauseStreamingList();
    }

    private void SendPauseStreamingList() {
        if (AccountDetails.getDashboardList() != null) {
            if (AccountDetails.getDashboardList().size() > 0)
                if (streamController != null) {
                    streamController.pauseStreaming(getActivity(), "ltpinfo", AccountDetails.getDashboardList());
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(AccountDetails.currentFragment != NAV_TO_TRADE_SCREEN && AccountDetails.currentFragment != NAV_TO_QUOTES_SCREEN) {
            AccountDetails.currentFragment = NAV_TO_MARKET_HOME_SCREEN;
        }
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashBoardView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            attachLayout(R.layout.fragment_dash_board).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_dash_board).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        AccountDetails.dashboardList.clear();
        AccountDetails.currentFragment = NAV_TO_MARKET_HOME_SCREEN;
        linearLayout = dashBoardView.findViewById(R.id.main_layout);

        home_frameLayout = dashBoardView.findViewById(R.id.home_content);
        market_frameLayout = dashBoardView.findViewById(R.id.market_content);
        derivative_frameLayout = dashBoardView.findViewById(R.id.derivative_content);


        Fragment someFragment = new HomeTabsFragment();
        trans_nome = getFragmentManager().beginTransaction();
        trans_nome.add(R.id.home_content, someFragment);
        trans_nome.commit();

        Fragment marketFragment = new MarketMoveFragment();
        trans_market = getFragmentManager().beginTransaction();
        trans_market.add(R.id.market_content, marketFragment);
        trans_market.commit();

        Fragment derivativeFragment = new DerivativesFragment();
        trans_derivative = getFragmentManager().beginTransaction();
        trans_derivative.add(R.id.derivative_content, derivativeFragment);
        trans_derivative.commit();

        return dashBoardView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void onSlideUp(int height_framelayout) {
        TranslateAnimation slide = new TranslateAnimation(0, 0, height_framelayout, 0);
        slide.setDuration(500);
        slide.setFillAfter(true);
        linearLayout.startAnimation(slide);
    }

    private void onSlideDown(int height_framelayout) {
        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, height_framelayout);
        slide.setDuration(1000);
        slide.setFillAfter(true);
        linearLayout.startAnimation(slide);
    }
}

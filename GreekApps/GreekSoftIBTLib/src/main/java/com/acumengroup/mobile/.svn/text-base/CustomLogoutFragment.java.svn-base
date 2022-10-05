package com.acumengroup.mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;
import com.acumengroup.greekmain.core.network.TCPConnectionHandler;
import com.acumengroup.greekmain.core.network.TCPOrderConnectionHandler;
import com.acumengroup.mobile.BottomTabScreens.OverviewTabFragment;
import com.acumengroup.mobile.login.LoginActivity;
import com.acumengroup.mobile.service.HeartBeatService;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.ui.textview.GreekTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.USER_TYPE;
import static com.acumengroup.mobile.portfolio.WatchListFragment.defaultGroupName;
import static com.acumengroup.mobile.portfolio.WatchListFragment.hashMapWatchListData;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomLogoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomLogoutFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TCPOrderConnectionHandler tcpOrderConnectionHandler;
    private TCPConnectionHandler tcpConnectionHandler;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomLogoutFragment() {
        // Required empty public constructor
        tcpOrderConnectionHandler = new TCPOrderConnectionHandler(); //TODO PK
        tcpConnectionHandler = new TCPConnectionHandler();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomLogoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomLogoutFragment newInstance(String param1, String param2) {
        CustomLogoutFragment fragment = new CustomLogoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_logout, container, false);

        GreekButton yesButton = view.findViewById(R.id.button_ok_transc);
        GreekButton noButton = view.findViewById(R.id.button_cancel_transc);
       GreekTextView title_dialog = view.findViewById(R.id.title_dialog);
        title_dialog.setText(GreekBaseActivity.GREEK);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OverviewTabFragment.isFirstTime=true;
                if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                    clearWatchlistDataFromSP(GreekBaseActivity.USER.OPENUSER);

                } else {
                    clearWatchlistDataFromSP(GreekBaseActivity.USER.IBTCUSTOMER);

                }
                ((GreekBaseActivity) getActivity()).doLogout(0);
                AccountDetails.setIsApolloConnected(false);
                AccountDetails.setIsIrisConnected(false);
                AccountDetails.setLastSelectedGroup("");
                AccountDetails.clearCache(getActivity());
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER || USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {
                    USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                }



                new HeartBeatService(getContext(), AccountDetails.getUsername(getActivity()),
                        AccountDetails.getSessionId(getActivity()), AccountDetails.getClientCode(getActivity())).stopHeartBeat();
                Util.getPrefs(getActivity()).edit().putString("GREEK_RETAINED_CUST_PASS", "").apply();
                Util.getPrefs(getActivity()).edit().putString("GREEK_RETAINED_CUST_TRANS_PASS", "").apply();
                Util.getPrefs(getActivity()).edit().putLong("LastValidatedTime", 0).apply();

                tcpConnectionHandler.stopStreaming();
                tcpOrderConnectionHandler.stopStreaming();
                AccountDetails.isValidSession = true;

                AccountDetails.setIsLogoutApp(true);
                getActivity().getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                Intent guestLogin = new Intent(getActivity(), LoginActivity.class);
                guestLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(guestLogin);
                getActivity().finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        return view;
    }
    private void clearWatchlistDataFromSP(GreekBaseActivity.USER usertype) {

        // WatchListFragment.hashMapWatchListData = new HashMap<String, List<SymbolList>>();


        if (hashMapWatchListData != null && hashMapWatchListData.size() > 0) {
            PortfolioGetUserWatchListResponse portfolioGetUserWatchListResponse = new PortfolioGetUserWatchListResponse();
            portfolioGetUserWatchListResponse.setErrorCode("0");
            List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();
            for (int i = 0; i < hashMapWatchListData.size(); i++) {
                GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
                getUserwatchlist.setWatchlistName(hashMapWatchListData.keySet().toArray()[i].toString());
                getUserwatchlist.setWatchtype("User");
                if (defaultGroupName.equalsIgnoreCase(hashMapWatchListData.keySet().toArray()[i].toString())) {
                    getUserwatchlist.setDefaultFlag("true");
                } else {
                    getUserwatchlist.setDefaultFlag("false");
                }
                List<SymbolList> symbolList = new ArrayList<>();
                symbolList = hashMapWatchListData.get(hashMapWatchListData.keySet().toArray()[i].toString());

                getUserwatchlist.setSymbolList(symbolList);

                getUserwatchlists.add(getUserwatchlist);
            }


            portfolioGetUserWatchListResponse.setGetUserwatchlist(getUserwatchlists);

            SharedPreferences.Editor editor1 = Util.getPrefs(getActivity()).edit();
            Gson gson = new Gson();
            String data = gson.toJson(portfolioGetUserWatchListResponse);
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getActivity()), data);
            editor1.apply();
            editor1.commit();
        }
        else {
            PortfolioGetUserWatchListResponse portfolioGetUserWatchListResponse1 = new PortfolioGetUserWatchListResponse();
            portfolioGetUserWatchListResponse1.setErrorCode("0");
            List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();

            portfolioGetUserWatchListResponse1.setGetUserwatchlist(getUserwatchlists);

            SharedPreferences.Editor editor1 = Util.getPrefs(getActivity()).edit();
            Gson gson = new Gson();
            String data = gson.toJson(portfolioGetUserWatchListResponse1);
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getActivity()), data);
            editor1.apply();
            editor1.commit();
        }


        if (usertype == GreekBaseActivity.USER.OPENUSER) {
            hashMapWatchListData.clear();
            SharedPreferences.Editor editor1 = Util.getPrefs(getActivity()).edit();
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getActivity()), " ");
            editor1.apply();
            editor1.commit();
        } else {
            hashMapWatchListData.clear();
            SharedPreferences.Editor editor1 = Util.getPrefs(getActivity()).edit();
            editor1.putString("WatchlistGroupsNew", " ");
            editor1.apply();
            editor1.commit();
        }

    }
}
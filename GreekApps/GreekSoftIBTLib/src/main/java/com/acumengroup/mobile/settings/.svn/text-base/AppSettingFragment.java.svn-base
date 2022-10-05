package com.acumengroup.mobile.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordRequest;
import com.acumengroup.greekmain.core.model.changepassword.ChangePasswordResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.WatchlistGroupResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class AppSettingFragment extends GreekBaseFragment implements View.OnClickListener {

    private View mChangePwdView;
    private GreekEditText oldPwdTxt;
    private GreekEditText newPwdTxt;
    private GreekEditText confirmPwdTxt;
    private ToggleButton updateBtn;
    InputFilter filter;
    private LinearLayout settingbg;
    private ToggleButton streamingToggle;
    private Spinner defaultSpinner, group_filtersSpinner;
    private GreekTextView txt_title;
    private PortfolioGetUserWatchListResponse getWatchListResponse = null;


    boolean result = false;
    ArrayAdapter<String> groupAdapter;
    ArrayAdapter<String> watchlist_groupAdapter;
    private final List<String> exchangelist = new ArrayList<>();
    private final List<String> group_watchlist = new ArrayList<>();
    private final ArrayList<String> groupTypeList = new ArrayList<>();


    private final AdapterView.OnItemSelectedListener exchangeItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String title = "";

            if (position == 1) {

                title = "Market is set as a default screen. Changes will be reflected after relogin";

                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", true).commit();
                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false).commit();
                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false).commit();


                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), title, "OK", true, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {


                    }
                });


            } else if (position == 2) {

                title = "Watchlist is set as a default screen. Changes will be reflected after relogin";


                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false).commit();
                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", true).commit();
                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", false).commit();


                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), title, "OK", true, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {


                    }
                });
            }else if (position == 3) {

                title = "DashBoard is set as a default screen. Changes will be reflected after relogin";


                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_ORDER_DASHBOARD", true).commit();
                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false).commit();
                Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false).commit();

                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), title, "OK", true, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {


                    }
                });
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final AdapterView.OnItemSelectedListener groupWatchSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String selectedGrp = group_filtersSpinner.getSelectedItem().toString();
            AccountDetails.setWatchlistGroup(selectedGrp);

            Util.getPrefs(getMainActivity()).edit().putString("GREEK_APP_DEFAULT_WATCHLIST_GROUP", selectedGrp).commit();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChangePwdView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragemnt_appsetting).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragemnt_appsetting).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        AccountDetails.currentFragment = NAV_TO_CHANGE_DEFAULTSCREEN_SCREEN;


        exchangelist.add("SELECT SCREEN");
        exchangelist.add("MARKET");
        exchangelist.add("WATCHLIST");
        exchangelist.add("DASHBOARD");

        txt_title = mChangePwdView.findViewById(R.id.txt_title);
        streamingToggle = mChangePwdView.findViewById(R.id.stramingToggle);
        defaultSpinner = mChangePwdView.findViewById(R.id.filtersSpinner);
        group_filtersSpinner = mChangePwdView.findViewById(R.id.group_filtersSpinner);
        updateBtn = mChangePwdView.findViewById(R.id.stramingToggle);
        settingbg = mChangePwdView.findViewById(R.id.setting_bg);

       // groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangelist);
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangelist) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }

                v.setPadding(15, 15, 15, 15);
                return v;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                //  v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        defaultSpinner.setAdapter(assetTypeAdapter);
        defaultSpinner.setOnItemSelectedListener(exchangeItemSelectedListener);

        group_watchlist.clear();
        watchlist_groupAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), group_watchlist);
        watchlist_groupAdapter.setDropDownViewResource(R.layout.custom_spinner);
        group_filtersSpinner.setAdapter(watchlist_groupAdapter);
        group_filtersSpinner.setOnItemSelectedListener(groupWatchSelectedListener);


        updateBtn.setOnClickListener(this);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            txt_title.setTextColor(getResources().getColor(R.color.black));
        }

        sendGroupNameRequest();
        hideAppTitle();
        setupViews();
        setTheme();


        return mChangePwdView;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            txt_title.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            settingbg.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void setupViews() {

//        if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
//            defaultSpinner.setSelection(0);
//
//        } else if (Util.getPrefs(getMainActivity()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {
//            defaultSpinner.setSelection(1);
//        } else {
//            defaultSpinner.setSelection(0);
//        }

        streamingToggle.setChecked(Util.getPrefs(getMainActivity()).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true));

    }

    @Override
    public void onClick(View v) {
        if (updateBtn.equals(v)) {

            Util.getPrefs(getMainActivity()).edit().putBoolean("GREEK_DATA_STREAMING_TOGGLE", streamingToggle.isChecked()).commit();

        }
    }

    public void sendGroupNameRequest() {

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
           // WatchlistGroupRequest.sendRequest(AccountDetails.getDeviceID(getMainActivity()), getMainActivity(), serviceResponseHandler);
        } else {
           // WatchlistGroupRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);
        }
    }

    private void getWatchlistGroupName() {

        if (getWatchListResponse != null) {
            List<GetUserwatchlist> getWatchlist = getWatchListResponse.getGetUserwatchlist();
            group_watchlist.clear();
            groupTypeList.clear();

            for (GetUserwatchlist watchlist : getWatchlist) {
                if (watchlist.getWatchlistName().equalsIgnoreCase("HIGH CONVICTION")) {
                    group_watchlist.add("HIGH CONVICTION");
                    groupTypeList.add("default");
                } else if (watchlist.getWatchlistName().equalsIgnoreCase("nifty")) {
                    group_watchlist.add("NIFTY");
                    groupTypeList.add("default2");
                } else {
                    group_watchlist.add(watchlist.getWatchlistName());
                    groupTypeList.add(watchlist.getWatchtype());
                }
            }

            watchlist_groupAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                if (getWatchListResponse != null && getWatchListResponse.getErrorCode() != null && getWatchListResponse.getErrorCode().equals("3")) {


                } else {
                    hideProgress();
                    getWatchlistGroupName();

                }

            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_GROUP_NAME_SVC_NAME.equals(jsonResponse.getServiceName())) {
                WatchlistGroupResponse watchlistGroupResponse = (WatchlistGroupResponse) jsonResponse.getResponse();
                group_watchlist.clear();
                groupTypeList.clear();
                for (int i = 0; i < watchlistGroupResponse.getGetUserwatchlistGroup().size(); i++) {

                    group_watchlist.add(watchlistGroupResponse.getGetUserwatchlistGroup().get(i).getWatchlistName());
                    groupTypeList.add(watchlistGroupResponse.getGetUserwatchlistGroup().get(i).getWatchlistName());

                }
                watchlist_groupAdapter.notifyDataSetChanged();
                hideProgress();
            } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_DATA_SVC_NAME.equals(jsonResponse.getServiceName())) {
                //WatchlistDataByGroupNameResponse watchlistGroupResponse = (WatchlistDataByGroupNameResponse) jsonResponse.getResponse();
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                hideProgress();
            } else {


                ChangePasswordResponse changePasswordResponse = (ChangePasswordResponse) jsonResponse.getResponse();

                if (ChangePasswordRequest.SERVICE_GROUP.equals(jsonResponse.getServiceGroup()) && ChangePasswordRequest.SERVICE_NAME.equals(jsonResponse.getServiceName()) && changePasswordResponse.getErrorCode().equals("0")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREEK_CHANGE_PASSWORD_SUCCESS_TXT), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                            //Logging out user
                            ((GreekBaseActivity) getMainActivity()).doLogout(0);
                        }
                    });

                    oldPwdTxt.setText("");
                    newPwdTxt.setText("");
                }
                if (changePasswordResponse.getErrorCode().equals("2")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREEK_INCORRECT_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("4")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREEK_DUPLICATE_PASSWORD_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("5")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREEK_MAX_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }

                if (changePasswordResponse.getErrorCode().equals("9")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREE_ID_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }
                if (changePasswordResponse.getErrorCode().equals("10")) {
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, getString(R.string.GREE_LOGIN_TRANSC_PASS_MSG), "OK", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            oldPwdTxt.setText("");
                            newPwdTxt.setText("");
                            confirmPwdTxt.setText("");
                        }
                    });
                }
                hideProgress();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {
        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && CHANGE_PWD_SVC_NAME.equals(jsonResponse.getServiceName())) {
            hideProgress();
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), jsonResponse.getInfoMsg(), "OK", true, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    oldPwdTxt.setText("");
                    newPwdTxt.setText("");
                    confirmPwdTxt.setText("");
                    //Logging out user
                    ((GreekBaseActivity) getMainActivity()).doLogout(0);
                }
            });
        }
    }

}

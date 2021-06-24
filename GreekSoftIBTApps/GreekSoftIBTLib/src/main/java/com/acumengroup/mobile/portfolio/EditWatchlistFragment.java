package com.acumengroup.mobile.portfolio;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.portfolioeditwatchlist.SymbolDetail;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.GreekDialog.Action;
import com.acumengroup.ui.GreekDialog.DialogListener;
import com.acumengroup.ui.adapter.CommonAdapter;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.adapter.PopulationListener;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class EditWatchlistFragment extends GreekBaseFragment {

    private final ArrayList<String> groupName = new ArrayList<>();
    private ArrayList<String> groupNameList = new ArrayList<>();
    private final ArrayList<String> groupType = new ArrayList<>();
    private final List<SymbolDetail> symbolList = new ArrayList<>();
    private View editWatchListView;
    private Spinner groupSpinner;
    private ArrayAdapter<String> groupsAdapter;
    private ListView sortListView;
    private PortfolioGetUserWatchListResponse getWatchListResponse;
    private ArrayList<CommonRowData> commonModel;
    private CommonAdapter commonAdapter;
    private final OnClickListener deleteGrpListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (groupsAdapter.getCount() > 0) {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_ASK_TO_DELETE_MSG), "No", "Yes", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        if (action != Action.OK) {
                            showProgress();
                            commonAdapter.clear();
                            commonAdapter.notifyDataSetChanged();


                            String groupAddURL;
                            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                                groupAddURL = "deleteGroupForWatchlist?clientCode=" + AccountDetails.getToken(getMainActivity()) + "&groupName=" + groupSpinner.getSelectedItem().toString() + "&watchlistType=user";
                            } else {
                                groupAddURL = "deleteGroupForWatchlist?clientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&groupName=" + groupSpinner.getSelectedItem().toString() + "&watchlistType=user";
                            }
                            WSHandler.getRequest(getMainActivity(), groupAddURL, new WSHandler.GreekResponseCallback() {
                                @Override
                                public void onSuccess(JSONObject response) {
                                    //toggleErrorLayout(false);
                                    try {
                                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_SUCCESSFUL_MSG), "OK", true, null);
                                        isDeleted = true;

                                        groupName.remove(groupSpinner.getSelectedItem().toString());
                                        groupsAdapter.notifyDataSetChanged();

                                        getArguments().putStringArrayList("grpnamelist", groupName);

                                        Bundle bundle = new Bundle();
                                        if (groupName.size() > 0) {
                                            getDetailOfGroup(groupName.get(0));

                                            bundle.putBoolean("toBeRefreshed", true);
                                            bundle.putSerializable("lastResponse", getWatchListResponse);
                                        } else {
                                            getWatchListResponse.getGetUserwatchlist().remove(0);
                                            bundle.putBoolean("toBeRefreshed", true);
                                            bundle.putSerializable("lastResponse", getWatchListResponse);
                                        }


                                        if (previousFragment != null)
                                            previousFragment.onFragmentResult(bundle);
                                        hideProgress();
                                    } catch (Exception e) {
                                        //toggleErrorLayout(true);
                                        e.printStackTrace();
                                    }
                                    //refreshComplete();
                                }

                                @Override
                                public void onFailure(String message) {
                                    //toggleErrorLayout(true);
                                    //refreshComplete();
                                }
                            });
                        }
                    }
                });

            }
        }
    };
    private GreekButton deleteGrpBtn, deleteSymBtn;


    private void getDetailOfGroup(String grpname) {
        showProgress();
        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
           // WatchlistDataByGroupNameRequest.sendRequest(AccountDetails.getToken(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
        } else {
           // WatchlistDataByGroupNameRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), grpname, "USER", getMainActivity(), serviceResponseHandler);
        }
    }

    private final OnItemSelectedListener groupsItemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            getDetailOfGroup(groupSpinner.getSelectedItem().toString());
            if (groupName.get(position).equalsIgnoreCase("nifty") || groupName.get(position).equalsIgnoreCase("HIGH CONVICTION")) {
                deleteGrpBtn.setEnabled(false);
            } else {
                deleteGrpBtn.setEnabled(true);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private String selectedGrp = "";
    private GreekTextView errorTextView;
    private boolean isDeleted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        editWatchListView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_editgroup).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_editgroup).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_EDIT_WATCHLIST_SCREEN;
        setupViews();
        setupAdapter();

        return editWatchListView;
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_EDIT_WATCHLIST_SCREEN;
        setAppTitle(getClass().toString(), "Edit Watchlist");
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

    private void setupViews() {
        groupSpinner = editWatchListView.findViewById(R.id.groupNameSpinner);
        sortListView = editWatchListView.findViewById(R.id.symbolList);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            sortListView.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            sortListView.setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        errorTextView = editWatchListView.findViewById(R.id.errorTextView);
        deleteSymBtn = editWatchListView.findViewById(R.id.deleteSymbolBtn);

        deleteSymBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (groupsAdapter.getCount() > 0) {
                    ArrayList<Boolean> isSelected = new ArrayList<>();

                    symbolList.clear();
                    for (CommonRowData row : commonModel) {
                        if (row.getHead2().equals("true")) {
                            SymbolDetail detail = new SymbolDetail();
                            detail.setExchange(row.getHead3());
                            detail.setTradeSymbol(row.getHead4());
                            detail.setToken(row.getToken());
                            detail.setAssetType(row.getAssetType());
                            symbolList.add(detail);
                            isSelected.add(true);
                        } else isSelected.add(false);
                    }

                    if (isSelected.contains(true)) {
                        selectedGrp = groupSpinner.getSelectedItem().toString();
                        showProgress();
                        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                            //RemoveSymbolFromGroupRequest.sendRequest(AccountDetails.getToken(getMainActivity()), groupSpinner.getSelectedItem().toString(), AccountDetails.getToken(getMainActivity()), symbolList, "user", getMainActivity(), serviceResponseHandler);
                        } else {
                            //RemoveSymbolFromGroupRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), groupSpinner.getSelectedItem().toString(), AccountDetails.getToken(getMainActivity()), symbolList, "user", getMainActivity(), serviceResponseHandler);
                        }
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_SELECT_SYMBOL_MSG), "Ok", true, null);
                    }
                }
            }
        });

        deleteGrpBtn = editWatchListView.findViewById(R.id.deleteGrpBtn);
        deleteGrpBtn.setOnClickListener(deleteGrpListener);
    }

    private void setupAdapter() {
        int[] newsViewIDs = {R.id.selectChkBox};
        commonModel = new ArrayList<>();
        commonAdapter = new CommonAdapter(getMainActivity(), commonModel);
        commonAdapter.setLayoutTextViews(R.layout.row_edit_watchlist, newsViewIDs);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            commonAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
        } else {
            commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        }
        commonAdapter.setPopulationListener(new PopulationListener() {

            @Override
            public void populateFrom(View v, int position, final CommonRowData row, View[] views) {
                CheckBox box = (CheckBox) views[0];
                box.setText(row.getHead1());
                Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
                box.setTypeface(font);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    box.setButtonTintList(ContextCompat.getColorStateList(getMainActivity(), R.color.checkbox_tint));
                }
                box.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                box.setChecked(row.getHead2().equals("true"));

                if (groupType.size() > 0 && groupName.size() > 0) {
                    box.setEnabled(true);
                } else box.setEnabled(false);

                box.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            row.setHead2("true");
                        } else {
                            row.setHead2("false");
                        }
                        commonAdapter.notifyDataSetChanged();

                    }
                });
            }

            @Override
            public void onRowCreate(View[] views) {

            }
        });
        sortListView.setAdapter(commonAdapter);
        getFromBundle();
    }

    private void getFromBundle() {
        getWatchListResponse = (PortfolioGetUserWatchListResponse) getArguments().getSerializable("Response");
        groupNameList = getArguments().getStringArrayList("grpnamelist");
        getWatchlistGroupName();
    }

    @Override
    public void handleResponse(Object response) {
        hideProgress();
        JSONResponse jsonResponse = (JSONResponse) response;
        if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && DELETE_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_DELETE_SUCCESS_MSG), "OK", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        hideProgress();
                        DeleteSymbolRefresh();
                        isDeleted = true;

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("toBeRefreshed", true);
                        bundle.putSerializable("lastResponse", getWatchListResponse);
                        bundle.putString("lastGroup", groupSpinner.getSelectedItem().toString());

                        if (previousFragment != null) previousFragment.onFragmentResult(bundle);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                getArguments().putSerializable("Response", getWatchListResponse);
                errorTextView.setVisibility(View.GONE);
                sortListView.setVisibility(View.VISIBLE);
                getWatchlistGroupName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && ADD_SYMBOL_WATCHLIST_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_ADDED_SUCCESS_MSG), "OK", true, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        showProgress();
                        commonAdapter.clear();
                        groupsAdapter.clear();
                        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                            PortfolioGetUserWatchListRequest.sendRequest(AccountDetails.getToken(getMainActivity()), AccountDetails.getToken(getMainActivity()), getMainActivity(), serviceResponseHandler);
                        } else {
                            PortfolioGetUserWatchListRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getToken(getMainActivity()), getMainActivity(), serviceResponseHandler);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("toBeRefreshed", true);

                        if (previousFragment != null) previousFragment.onFragmentResult(bundle);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (PORTFOLIO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GET_WATCHLIST_DATA_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                getWatchListResponse = (PortfolioGetUserWatchListResponse) jsonResponse.getResponse();
                populateSymbols();
                hideProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hideProgress();
        super.handleResponse(response);
    }

    private void DeleteSymbolRefresh() {

        List<GetUserwatchlist> getUserwatchlists = getWatchListResponse.getGetUserwatchlist();
        List<SymbolList> symbolLists = getUserwatchlists.get(0).getSymbolList();
        for (int i = 0; i < symbolLists.size(); i++) {
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolLists.get(i).getToken().equalsIgnoreCase(symbolList.get(j).getToken())) {
                    symbolLists.remove(i);
                }
            }
        }

        getWatchListResponse.getGetUserwatchlist().get(0).setSymbolList(symbolLists);
        commonAdapter.clear();
        if (symbolLists.size() == 0) {
            deleteSymBtn.setEnabled(false);
        } else {
            deleteSymBtn.setEnabled(true);
        }
        for (SymbolList symbolList : symbolLists) {
            CommonRowData commonRow = new CommonRowData();

            if (symbolList.getExchange().equalsIgnoreCase("mcx")) {

                if(symbolList.getOptionType().equalsIgnoreCase("XX"))
                {
                    commonRow.setHead1(symbolList.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(symbolList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + "M" + " - " + symbolList.getInstrumentName());
                }
                else {
                    commonRow.setHead1(symbolList.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(symbolList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + symbolList.getStrickPrice() + symbolList.getOptionType() + "" + "M" + " - " + symbolList.getInstrumentName());
                }


            } else {
                commonRow.setHead1(symbolList.getDescription());

            }
            commonRow.setHead2("false");
            commonRow.setHead3(symbolList.getExchange());
            commonRow.setHead4(symbolList.getTradeSymbol());
            commonRow.setToken(symbolList.getToken());
            commonRow.setAssetType(symbolList.getAssetType());
            commonAdapter.add(commonRow);
        }
        commonAdapter.notifyDataSetChanged();

    }

    private void getWatchlistGroupName() {
        if (getWatchListResponse != null) {
            List<GetUserwatchlist> getUserwatchlists = getWatchListResponse.getGetUserwatchlist();
            groupName.clear();
            groupType.clear();
            for (GetUserwatchlist watchlist : getUserwatchlists) {
                if (watchlist.getWatchlistName().equalsIgnoreCase("nifty")) {
                    groupName.add("NIFTY");
                    groupType.add("default");
                } else {
                    groupName.add(watchlist.getWatchlistName());
                    groupType.add(watchlist.getWatchtype());
                }
            }

            groupName.clear();
            groupName.addAll(getArguments().getStringArrayList("grpnamelist"));

            groupsAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), groupName);
            groupsAdapter.setDropDownViewResource(R.layout.custom_spinner);
            groupSpinner.setAdapter(groupsAdapter);
            if (!isDeleted) {
                if (groupsAdapter.getCount() > getArguments().getInt("SelectedGrp")) {
                    groupSpinner.setSelection(getArguments().getInt("SelectedGrp"));
                }
            }

            groupSpinner.setOnItemSelectedListener(groupsItemSelectedListener);
            if (groupsAdapter.getCount() == 0) {
                groupSpinner.setEnabled(false);
                deleteGrpBtn.setEnabled(false);
                deleteSymBtn.setEnabled(false);
            } else {
                groupSpinner.setEnabled(true);
                deleteGrpBtn.setEnabled(true);
            }

            if (selectedGrp != null) {
                for (int i = 0; i < groupName.size(); i++) {
                    if (groupName.get(i).equals(selectedGrp)) groupSpinner.setSelection(i);
                }
            }
        }
    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        errorTextView.setText(msg);
        errorTextView.setVisibility(View.VISIBLE);
        sortListView.setVisibility(View.GONE);
        groupSpinner.setEnabled(false);
        deleteGrpBtn.setEnabled(false);
        super.showMsgOnScreen(action, msg, jsonResponse);
    }

    private void populateSymbols() {
        if (getWatchListResponse != null) {
            commonAdapter.clear();
            List<GetUserwatchlist> getWatchlist = getWatchListResponse.getGetUserwatchlist();
            GetUserwatchlist watchlist = getWatchlist.get(0);
            List<SymbolList> symbolLists = watchlist.getSymbolList();
            if (symbolLists.size() == 0) {
                deleteSymBtn.setEnabled(false);
            } else {
                deleteSymBtn.setEnabled(true);
            }
            for (SymbolList symbolList : symbolLists) {
                CommonRowData commonRow = new CommonRowData();

                if (symbolList.getExchange().equalsIgnoreCase("mcx")) {
                    if(symbolList.getOptionType().equalsIgnoreCase("XX"))
                    {
                        commonRow.setHead1(symbolList.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(symbolList.getExpiryDate(), "yyMMM", "bse").toUpperCase()  + "-" + "M" + " - " + symbolList.getInstrumentName());
                    }
                    else {
                        commonRow.setHead1(symbolList.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(symbolList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + symbolList.getStrickPrice() + symbolList.getOptionType() + "-" + "M" + " - " + symbolList.getInstrumentName());
                    }



                } else {
                    commonRow.setHead1(symbolList.getDescription());
                }

                commonRow.setHead2("false");
                commonRow.setHead3(symbolList.getExchange());
                commonRow.setHead4(symbolList.getTradeSymbol());
                commonRow.setToken(symbolList.getToken());
                commonRow.setAssetType(symbolList.getAssetType());
                commonAdapter.add(commonRow);
            }
            commonAdapter.notifyDataSetChanged();
        }
    }
}
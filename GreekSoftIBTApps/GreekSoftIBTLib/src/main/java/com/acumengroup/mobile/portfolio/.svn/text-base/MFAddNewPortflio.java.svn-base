package com.acumengroup.mobile.portfolio;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MFAddNewPortflio extends GreekBaseFragment {

    private final List<String> restrictedGrp = new ArrayList();
    private View addNewView;
    private GreekEditText symbolName;
    private LinearLayout linearExtend;
    private final View.OnClickListener saveClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            if (symbolName.getText().toString().trim().length() > 0) {

                ArrayList grpName = new ArrayList();
                for (int i = 0; i < getArguments().getStringArrayList("Groups").size(); i++) {
                    grpName.add(getArguments().getStringArrayList("Groups").get(i).toLowerCase().trim());
                }
                if (!restrictedGrp.contains(symbolName.getText().toString().toLowerCase().trim().toUpperCase())) {
                    if (!grpName.contains(symbolName.getText().toString().toLowerCase().trim())) {
                        showProgress();
                        String groupAddURL;
                        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                            groupAddURL = "createGroupForWatchlistMF?clientCode=" + AccountDetails.getDeviceID(getMainActivity()) + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&groupName=" + symbolName.getText().toString().trim() + "&watchlistType=user";
                        } else {
                            groupAddURL = "createGroupForWatchlistMF?clientCode=" + AccountDetails.getClientCode(getMainActivity()) + "&gscid=" + AccountDetails.getUsername(getMainActivity()) + "&groupName=" + symbolName.getText().toString().trim() + "&watchlistType=user";
                        }
                        WSHandler.getRequest(getMainActivity(), groupAddURL, new WSHandler.GreekResponseCallback() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                //toggleErrorLayout(false);
                                try {
                                    hideProgress();
                                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_ADDED_SUCCESS_MSG), "OK", false, new GreekDialog.DialogListener() {

                                        @Override
                                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean("toBeRefreshed", true);
                                            bundle.putString("addedSymbol", symbolName.getText().toString());
                                            if (previousFragment != null)
                                                previousFragment.onFragmentResult(bundle);
                                            goBackOnce();

                                        }
                                    });
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
                    } else
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_GROUP_NAME_EXIST_MSG), "OK", true, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                symbolName.setText("");
                            }
                        });
                } else {
                    GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_GROUP_NAME_ERROR_MSG), "OK", true, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            symbolName.setText("");
                        }
                    });
                }

            } else
                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), getString(R.string.GR_GROUP_NAME_CANT_EMPTY_MSG), "OK", true, null);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addNewView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_addgroup).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_addgroup).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        setupViews();

        return addNewView;
    }

    private void setupViews() {
        restrictedGrp.clear();
        restrictedGrp.add("NSE");
        restrictedGrp.add("BSE");
        restrictedGrp.add("MCX");
        restrictedGrp.add("NCDEX");

        symbolName = addNewView.findViewById(R.id.search_text);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(symbolName, InputMethodManager.SHOW_IMPLICIT);
        symbolName.setFocusable(true);
        symbolName.requestFocus();
        GreekButton saveBtn = addNewView.findViewById(R.id.searchBtn);
        linearExtend = addNewView.findViewById(R.id.linear_extend);
        linearExtend.setOnTouchListener(
                new LinearLayout.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        return true;
                    }
                }
        );

        saveBtn.setOnClickListener(saveClickListener);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };


        symbolName.setFilters(new InputFilter[]{filter});
        symbolName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        symbolName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        if (getArguments().getString("groupCount") == null)
            symbolName.setText("WATCHLIST1");
        else
            symbolName.setText("WATCHLIST" + getArguments().getString("groupCount"));

    }


}


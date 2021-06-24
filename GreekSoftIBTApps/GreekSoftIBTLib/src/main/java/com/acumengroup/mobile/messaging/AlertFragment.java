package com.acumengroup.mobile.messaging;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.mobile.model.AlertResponse;
import com.acumengroup.mobile.model.CDSLReturnResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.AlertStreamingResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.alert.AlertCreateRequest;
import com.acumengroup.mobile.alert.AlertCreateResponse;
import com.acumengroup.mobile.alert.AlertData;
import com.acumengroup.mobile.alert.AlertDataBook;
import com.acumengroup.mobile.alert.DeleteAlertResponse;
import com.acumengroup.mobile.alert.ShowAlertDetail;
import com.acumengroup.mobile.alert.ShowAlertRequest;
import com.acumengroup.mobile.symbolsearch.SymbolSearchFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.adapter.GreekPopulationListener;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by Sushant on 9/27/2016.
 */

public class AlertFragment extends GreekBaseFragment {
    private View mChangeAlertView;
    private FloatingActionButton alertFltBtn;
    private SwipeRefreshLayout swipeRefresh;
    private RelativeLayout errorMsgLayout;
    private Spinner exchangeSpinner, filtersSpinner;
    SymbolSearchFragment symbolSearchFragment;
    private GreekEditText qty_textCust;
    private GreekTextView scriptText, ltpText, exchangeText,errorHeader;
    private Spinner assetTypeSpinner, exchangeTypeSpinner;
    private Button btnCreateAlert;
    public static final String TOKEN = "token";
    public static final String SCRIP_NAME = "scrip_name";
    public static final String FROM_PAGE = "From";
    public static final String ASSET_TYPE = "AssetType";
    private MarketsSingleScripResponse quoteResponse = null;
    private AlertCreateResponse alertCreateResponse = null;
    private AlertResponse showAlertResponse = null;
    private DeleteAlertResponse deleteAlertResponse = null;
    private ArrayList streamingList;
    private String alertType, direction, range;
    String assetType = "", token = "";
//    private GreekCommonAdapter<AlertData> commonAdapter;
    public AlertAdapter alerAdapter;
    private RecyclerView alertList;
    private String symbol, exchange;
    ArrayList<AlertData> alertDataBookModel;
    private StreamingController streamingController;
    ImageView imageBtnClose;
    private String ltp, percent, volume;
    AlertDialog.Builder alert;
    private final List<String> exchangeList = new ArrayList<>();
    private final List<String> assetTypeList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChangeAlertView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.alert_fragment).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.alert_fragment).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_ALERT_SCREEN;
        symbolSearchFragment = new SymbolSearchFragment();
        setupView(mChangeAlertView);
        setupController();
        setupAdapter();
//        showAlertRequest();
        setAppTitle(getClass().toString(), "Alert");
        return mChangeAlertView;
    }

    private void setupView(View mChangeAlertView) {
        setAppTitle(getClass().toString(), "Alerts");
        alertFltBtn = mChangeAlertView.findViewById(R.id.flt_alert);
        alertFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Source", "Alert");
                navigateTo(NAV_TO_SYMBOL_SEARCH_SCREEN, bundle, true);
                // TODO: sushant : for alert
            }
        });

        streamingController = new StreamingController();
        exchangeSpinner = mChangeAlertView.findViewById(R.id.exchange);
        filtersSpinner = mChangeAlertView.findViewById(R.id.filter);
        alertList = mChangeAlertView.findViewById(R.id.listMarketEquity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMainActivity());
        alertList.setLayoutManager(linearLayoutManager);
        alertList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        swipeRefresh = mChangeAlertView.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        errorMsgLayout = mChangeAlertView.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setText("No alerts available.");
        errorMsgLayout.setVisibility(View.VISIBLE);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//            LinearLayout header_layout = mChangeAlertView.findViewById(R.id.orderBookListHeader);
//            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
            ((GreekTextView) errorMsgLayout.findViewById(R.id.errorHeader)).setTextColor(getMainActivity().getColor(R.color.black));

        }
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            showAlertRequest();

        }
    };

    private void showAlertDialog(final MarketsSingleScripResponse jsonResponse) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.prompt_alert, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setView(promptsView);
        LinearLayout rootLayoutLabel;

        scriptText = promptsView.findViewById(R.id.textViewValue1);
        ltpText = promptsView.findViewById(R.id.textViewValue2);
        exchangeText = promptsView.findViewById(R.id.textViewValue3);
        errorHeader = promptsView.findViewById(R.id.errorHeader);
        assetTypeSpinner = promptsView.findViewById(R.id.assetTypeSpin);
        exchangeTypeSpinner = promptsView.findViewById(R.id.exchangeSpin);
        qty_textCust = promptsView.findViewById(R.id.qty_text_custom);
        InputMethodManager inputMethodManager1 = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager1.showSoftInput(qty_textCust, InputMethodManager.SHOW_IMPLICIT);
        qty_textCust.setImeOptions(EditorInfo.IME_ACTION_DONE);
        qty_textCust.setSelection(0);
        btnCreateAlert = promptsView.findViewById(R.id.btnCreate);
        imageBtnClose = promptsView.findViewById(R.id.imageCloseBtn);
        rootLayoutLabel = promptsView.findViewById(R.id.layout_root);

        if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
            scriptText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangeText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_textCust.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            rootLayoutLabel.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            imageBtnClose.setImageResource(R.drawable.ic_close_black_24dp);

        }

        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), Arrays.asList(getResources().getStringArray(R.array.alertMovement))) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
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
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };

        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
         exchangeTypeSpinner.setAdapter(assetTypeAdapter);
        exchangeTypeSpinner.setOnItemSelectedListener(exchangeTypeSpinnerItemSelectedListener);

        ArrayAdapter<String> col1Adapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), Arrays.asList(getResources().getStringArray(R.array.alertOption))) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
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
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };

        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        assetTypeSpinner.setAdapter(col1Adapter);
        assetTypeSpinner.setOnItemSelectedListener(assetTypeItemSelectedListener);

        // create alert dialog
        if (jsonResponse.getExch().equalsIgnoreCase("mcx")) {
            if(quoteResponse.getOptiontype().equalsIgnoreCase("XX"))
            {
                scriptText.setText(jsonResponse.getDescription() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase()  + " - " + quoteResponse.getInstrument());
            }
            else {
                scriptText.setText(jsonResponse.getDescription() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + " - " + quoteResponse.getInstrument());
            }

        } else {
            scriptText.setText(jsonResponse.getDescription());
        }

        if (jsonResponse.getAsset_type().equalsIgnoreCase("currency")) {
            ltp = String.format("%.4f", Double.parseDouble(jsonResponse.getLast()));
        } else {
            ltp = String.format("%.2f", Double.parseDouble(jsonResponse.getLast()));
        }
        percent = jsonResponse.getP_change();
        volume = jsonResponse.getTot_vol();

        exchangeText.setText(jsonResponse.getSymbol() + " - " + getExchange(jsonResponse.getToken()) + " - " + jsonResponse.getInstrument());

        final AlertDialog alertDialog = alertDialogBuilder.create();

        imageBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                if (streamingList != null) {
                    if (streamingList.size() > 0)
                        streamController.pauseStreaming(getActivity(), "marketPicture", streamingList);
                }
            }
        });

        btnCreateAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                range = qty_textCust.getText().toString();
                alertType = assetTypeSpinner.getSelectedItem().toString();
                direction = exchangeTypeSpinner.getSelectedItem().toString();

                setAlertType(alertType);
                setDirection(direction);

                if ((qty_textCust.getText().toString()).isEmpty()) {
                    String messageStr = "";
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("price")) {
                        messageStr = getString(R.string.AL_PRICE_MSG);
                    } else if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("percent")) {
                        messageStr = getString(R.string.AL_PERCENT_MSG);
                    } else if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("volume")) {
                        messageStr = getString(R.string.AL_VOLUME_MSG);
                    }
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, messageStr, "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                } else if ((qty_textCust.getText().toString().equalsIgnoreCase("0"))) {
                    String messageStr = "";
                    if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("price")) {
                        messageStr = "Price " + getString(R.string.AL_ZERO_MSG);
                    } else if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("percent")) {
                        messageStr = "Percent " + getString(R.string.AL_ZERO_MSG);
                    } else if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("volume")) {
                        messageStr = "Volume " + getString(R.string.AL_ZERO_MSG);
                    }
                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, messageStr, "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
                } else {


                    swipeRefresh.setRefreshing(true);
                    AlertCreateRequest.sendRequest(symbol, AccountDetails.getUsername(getMainActivity()),
                            exchange, jsonResponse.getToken(), jsonResponse.getAsset_type(),
                            AccountDetails.getClientCode(getMainActivity()), alertType, direction,
                            range, getMainActivity(), serviceResponseHandler);


                    alertDialog.dismiss();
                    if (streamingList != null) {
                        if (streamingList.size() > 0)
                            streamController.pauseStreaming(getActivity(), "marketPicture", streamingList);
                    }

                }

                //Alerts List was not refreshing after addition of a alert,so
                //refresh the list and notify adapter for changes in list.
                showAlertRequest();

                if (alerAdapter != null) {
                    alerAdapter.notifyDataSetChanged();

                }
                refreshComplete();
            }
        });

        // show it
        alertDialog.show();
    }

    //rule_type
    public void setAlertType(String alertTypes) {
        if (alertTypes.equalsIgnoreCase("price")) {
            alertType = "1";
        } else if (alertTypes.equalsIgnoreCase("percent")) {
            alertType = "2";
        } else if (alertTypes.equalsIgnoreCase("volume")) {
            alertType = "3";
        }
    }

    public void setDirection(String directions) {
        if (directions.equalsIgnoreCase("Moves Above")) {
            direction = "0";
        } else if (directions.equalsIgnoreCase("Moves Below")) {
            direction = "1";
        }
    }

    @Override
    public void onFragmentResult(Object data) {
        if (data != null) {
            assetType = ((Bundle) data).getString(ASSET_TYPE);
            token = ((Bundle) data).getString(TOKEN);
            sendQuotesRequest(token, assetType);
        }
    }

    private void sendQuotesRequest(String token, String assetType) {
        MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), token, assetType, AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;

        if (MARKETS_SVC_GROUP.equalsIgnoreCase(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equalsIgnoreCase(jsonResponse.getServiceName())) {
            hideProgress();
            refreshComplete();
            try {
                quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                if (quoteResponse.getExch().equalsIgnoreCase("mcx")) {
                    if(quoteResponse.getOptiontype().equalsIgnoreCase("XX"))
                    {
                        symbol = quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + " - " + quoteResponse.getInstrument();
                    }
                    else {
                        symbol = quoteResponse.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteResponse.getStrikeprice() + quoteResponse.getOptiontype() + " - " + quoteResponse.getInstrument();
                    }

                } else {
                    symbol = quoteResponse.getSymbol();
                }
                exchange = quoteResponse.getExch();
                streamingList = new ArrayList();
                streamingList.add(quoteResponse.getToken());
                sendStreamingRequest();
                showAlertDialog(quoteResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MARKETS_SVC_GROUP.equalsIgnoreCase(jsonResponse.getServiceGroup()) && ALERTCREATE_SVC_NAME.equalsIgnoreCase(jsonResponse.getServiceName())) {
            hideProgress();
            refreshComplete();

            try {
                alertCreateResponse = (AlertCreateResponse) jsonResponse.getResponse();
                if (alertCreateResponse.getErrorCode().equalsIgnoreCase("0")) {
                    streamingController.sendStreamingAlertBroadcastRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()), AccountDetails.getSessionId(getMainActivity()), alertCreateResponse.getRuleNo(), range, direction, alertCreateResponse.getGtoken(), alertType, "0", null, null);
                    showAlertRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MARKETS_SVC_GROUP.equalsIgnoreCase(jsonResponse.getServiceGroup()) && ALERTSHOW_SVC_NAME.equalsIgnoreCase(jsonResponse.getServiceName())) {
            hideProgress();
            refreshComplete();

            try {

                Gson gson = new Gson();
                showAlertResponse = gson.fromJson(String.valueOf(jsonResponse), AlertResponse.class);

                 handleAlertData(showAlertResponse.getResponse().getData().getAlertData());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MARKETS_SVC_GROUP.equalsIgnoreCase(jsonResponse.getServiceGroup()) && ALERTDELETE_SVC_NAME.equalsIgnoreCase(jsonResponse.getServiceName())) {
            hideProgress();
            refreshComplete();

            try {
                deleteAlertResponse = (DeleteAlertResponse) jsonResponse.getResponse();
                if (deleteAlertResponse.getErrorCode().equalsIgnoreCase("0")) {
                    showAlertRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        setAppTitle(getClass().toString(), "Alert");
    }

    private void showAlertRequest() {
        showProgress();
        ShowAlertRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }

    private void setupController() {

        //FOR EXCHANGE FILTER
        getExchangeList();
        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), exchangeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
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
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };

        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        exchangeSpinner.setAdapter(assetTypeAdapter);
        exchangeSpinner.setOnItemSelectedListener(exchangeItemSelectedListener);

        //FOR SEGEMENT FILTER
        ArrayAdapter<String> assetTypeAdapter1 = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetTypeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
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
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };

        assetTypeAdapter1.setDropDownViewResource(R.layout.custom_spinner);
         filtersSpinner.setAdapter(assetTypeAdapter1);
        filtersSpinner.setOnItemSelectedListener(filterItemSelectedListener);

    }

    private void sendStreamingRequest() {
        streamController.sendStreamingRequest(getMainActivity(), streamingList, "marketPicture", null, null, false);
        addToStreamingList("marketPicture", streamingList);
    }

    private void getExchangeList() {

        assetTypeList.clear();
        exchangeList.clear();

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
            assetTypeList.add("Equity");
        }
        if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
            assetTypeList.add("FNO");

        }
        if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            assetTypeList.add("Currency");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            assetTypeList.add("Commodity");
        }

        if (assetTypeList.size() > 1) {
            assetTypeList.add(0, "All");
        }

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_ncd) {
            exchangeList.add("NSE");
        }
        if (AccountDetails.allowedmarket_bse || AccountDetails.allowedmarket_bfo || AccountDetails.allowedmarket_bcd) {
            exchangeList.add("BSE");

        }
        if (AccountDetails.allowedmarket_mcx) {
            exchangeList.add("MCX");
        }
        if (AccountDetails.allowedmarket_ncdex) {
            exchangeList.add("NCDEX");
        }

        if (exchangeList.size() > 1) {
            exchangeList.add(0, "All");
        }
    }


    private void setupAdapter() {
       // int[] newsViewIDs = {R.id.row11, R.id.row12, R.id.row13, R.id.row21, R.id.row22, R.id.row23};
        alertDataBookModel = new ArrayList<>();
        alerAdapter= new AlertAdapter(getMainActivity(),alertDataBookModel,alertType,errorMsgLayout);
        alertList.setAdapter(alerAdapter);
//        commonAdapter = new GreekCommonAdapter<>(getMainActivity(), alertDataBookModel);
      //  commonAdapter.setLayoutTextViews(R.layout.row_alert_book, newsViewIDs);

//        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//            commonAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
//        } else {
//            commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
//        }
//        commonAdapter.setPopulationListener(new GreekPopulationListener<AlertData>() {
//
//
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void populateFrom(View v, int position, AlertData row, View[] views) {
//                ((ScrollingTextView) views[0]).setText(row.getSymbol());
//
//                if (row.getAlertType().equals("1")) {
//                    ((GreekTextView) views[1]).setText("price");
//                } else if (row.getAlertType().equals("2")) {
//                    ((GreekTextView) views[1]).setText("percent");
//                } else if (row.getAlertType().equals("3")) {
//                    ((GreekTextView) views[1]).setText("volume");
//                }
//
//                if (row.getDirectionFlag().equals("0")) {
//                    ((GreekTextView) views[2]).setText("up");
//                } else if (row.getDirectionFlag().equals("1")) {
//                    ((GreekTextView) views[2]).setText("down");
//                }
//
//                ((GreekTextView) views[3]).setText(row.getExchange());
//
//                if (row.getAssetType().equalsIgnoreCase("currency")) {
//                    ((GreekTextView) views[4]).setText(String.format("%.4f", Double.parseDouble(row.getRange())));
//                } else {
//                    ((GreekTextView) views[4]).setText(String.format("%.2f", Double.parseDouble(row.getRange())));
//                }
//
//                if (row.getIsExecuted().equalsIgnoreCase("0")) {
//
//                    ((GreekTextView) views[5]).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_down_red_24dp, 0);
//
//                } else if (row.getIsExecuted().equalsIgnoreCase("1")) {
//                    ((GreekTextView) views[5]).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_up_green_24dp, 0);
//                }
//
//                ((ScrollingTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                ((GreekTextView) views[5]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            }
//
//            @Override
//            public void onRowCreate(View[] views) {
//
//            }
//        });
//        alertList.setAdapter(commonAdapter);
//        alertList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                removeItemFromList(position);
//                return true;
//            }
//        });
//
//        alertList.setOnItemClickListener(alertBookListener);
    }

    //TODO: To delete item from list
//    private void removeItemFromList(final int position) {
//        final int deletePosition = position;
//
//        alert = new AlertDialog.Builder(getMainActivity());
////        AlertDialog.Builder alert = new AlertDialog.Builder(getMainActivity());
//        alert.setTitle("Delete Alert");
//        alert.setMessage("Do you want delete this item?");
//        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                swipeRefresh.setRefreshing(true);
//                streamingController.sendStreamingAlertBroadcastRequest(getMainActivity(), AccountDetails.getClientCode(getMainActivity()),
//                        AccountDetails.getSessionId(getMainActivity()), commonAdapter.getItem(deletePosition).getRuleNo(),
//                        commonAdapter.getItem(deletePosition).getRange(), commonAdapter.getItem(deletePosition).getDirectionFlag(),
//                        commonAdapter.getItem(deletePosition).getLourToken(), alertType, "1", null, null);
//                commonAdapter.remove(deletePosition);
//                commonAdapter.notifyDataSetChanged();
//                if(commonAdapter.getCount() < 1)
//                {
//                    errorMsgLayout.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    errorMsgLayout.setVisibility(View.GONE);
//                }
//                refreshComplete();
//            }
//        });
//
//        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        alert.show();
//    }

    private final AdapterView.OnItemClickListener alertBookListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            //TODO: action
        }
    };
    private final AdapterView.OnItemSelectedListener exchangeItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            showAlertRequest();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener exchangeTypeSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            qty_textCust.setText("");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener assetTypeItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            qty_textCust.setText("");
            if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("price")) {
                ltpText.setText(ltp);
                exchangeTypeSpinner.setEnabled(true);

            } else if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("percent")) {
                ltpText.setText(String.format("%.2f", Double.parseDouble(percent)) + '%');
                exchangeTypeSpinner.setEnabled(true);

            } else if (assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("volume")) {
                ltpText.setText(volume);
                exchangeTypeSpinner.setSelection(0);
                exchangeTypeSpinner.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener filterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            showAlertRequest();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void handleAlertData(List<AlertResponse.Response.Data.AlertData> showAlertDetails) {

        alertDataBookModel.clear();
         String currexch = exchangeSpinner.getSelectedItem().toString();
        String currasset = filtersSpinner.getSelectedItem().toString();

        for (AlertResponse.Response.Data.AlertData item : showAlertDetails) {

            boolean toAddExch, toAddAsset;
            switch (currexch.toLowerCase()) {
                case "nse":
                    toAddExch = getExchange(item.getToken()).equalsIgnoreCase("nse");//open
                    break;
                case "bse":
                    toAddExch = getExchange(item.getToken()).equalsIgnoreCase("bse");
                    break;
                case "mcx":
                    toAddExch = getExchange(item.getToken()).equalsIgnoreCase("mcx");
                    break;
                case "ncdex":
                    toAddExch = getExchange(item.getToken()).equalsIgnoreCase("ncdex");
                    break;
                default:
                    toAddExch = true;
                    break;
            }

            switch (currasset.toLowerCase()) {
                case "equity":
                    toAddAsset = getAssetTypeFromToken(item.getToken()).equalsIgnoreCase("equity");
                    break;
                case "fno":
                    toAddAsset = getAssetTypeFromToken(item.getToken()).equalsIgnoreCase("fno");
                    break;
                case "currency":
                    toAddAsset = getAssetTypeFromToken(item.getToken()).equalsIgnoreCase("currency");
                    break;
                case "commodity":
                    toAddAsset = getAssetTypeFromToken(item.getToken()).equalsIgnoreCase("commodity");
                    break;
                default:
                    toAddAsset = true;
                    break;
            }
            if (toAddExch && toAddAsset) {
                AlertDataBook alertDataBook = new AlertDataBook();
                alertDataBook.setRuleNo(item.getRuleNo());
                alertDataBook.setRange(item.getRange());
                alertDataBook.setAlertType(item.getAlertType());
                alertDataBook.setDirectionFlag(item.getDirectionFlag());
                alertDataBook.setEndDateTime(item.getEndDatetime());
                alertDataBook.setIsExecuted(item.isExecuted());
                alertDataBook.setLastUpadatedTime(item.getLastUpdatedTime());
                alertDataBook.setLourToken(item.getToken());
                alertDataBook.setStartDateTime(item.getStartDatetime());
                alertDataBook.setSeries_instname(item.getSeriesInstname());


                if (getExchange(item.getToken()).equalsIgnoreCase("MCX")) {

                    alertDataBook.setSymbol(item.getSymbol());
                } else {
                    alertDataBook.setSymbol(item.getDescription());

                }

                alertDataBook.setExchange(getExchange(item.getToken()));
                alertDataBook.setDescription(item.getDescription());
                alertDataBook.setAssetType(getAssetTypeFromToken(item.getToken()));
                alertDataBook.setDetails(item);

                alertDataBookModel.add(alertDataBook);
            }

            //alertList.setAdapter(commonAdapter);
            swipeRefresh.setRefreshing(true);
            if (alertDataBookModel.size() > 0) {
                errorMsgLayout.setVisibility(View.GONE);
            } else {
                errorMsgLayout.setVisibility(View.VISIBLE);
            }

        }

        alerAdapter= new AlertAdapter(getMainActivity(),alertDataBookModel,alertType,errorMsgLayout);
        alertList.setAdapter(alerAdapter);
        alerAdapter.notifyDataSetChanged();


        refreshComplete();
    }



    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_ALERT_SCREEN;
        EventBus.getDefault().register(this);
        setAppTitle(getClass().toString(), "Alert");
    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        showAlertRequest();
        setAppTitle(getClass().toString(), "Alert");

    }

    public void onEventMainThread(AlertStreamingResponse alertStreamingResponse) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("marketPicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                if (ltpText != null && quoteResponse.getToken().equalsIgnoreCase(streamingResponse.getResponse().getString("symbol"))) {

                    if(assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Price")) {
                        ltpText.setText(streamingResponse.getResponse().getString("ltp"));
                    }else if(assetTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Percent")){
                        ltpText.setText(streamingResponse.getResponse().getString("p_change")+"%");
                    }else{
                        ltpText.setText(streamingResponse.getResponse().getString("tot_vol"));

                    }

                }
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    private String getExchange(String token) {
        long tokenInt = Long.parseLong(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else if ((tokenInt >= 1903000000) && (tokenInt <= 1903999999)) {
            return "NSECOMM";
        } else if ((tokenInt >= 2003000000) && (tokenInt <= 2003999999)) {
            return "BSECOMM";
        } else if ((tokenInt >= 502000000) && (tokenInt <= 502999999)) {
            return "NSECURR";
        } else if ((tokenInt >= 1302000000) && (tokenInt <= 1302999999)) {
            return "BSECURR";
        } else {
            return "BSE";
        }
    }


    private String getAssetTypeFromToken(String token) {
        long tokenInt = Long.parseLong(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if (((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }

        // return "";
    }
}

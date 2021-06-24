package com.acumengroup.mobile.reports;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.ProductChangeResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.core.services.TradeBookRequest;
import com.acumengroup.greekmain.core.services.TradeBookResponse;
import com.acumengroup.greekmain.core.trade.TradeBook;
import com.acumengroup.greekmain.core.trade.TradeBookData;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.adapter.GreekPopulationListener;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by summert on 6/16/2015.
 */
public class TradeBookFragment extends GreekBaseFragment implements View.OnClickListener {
    private final ArrayList<String> assetList = new ArrayList<>();
    private boolean isWaitingForResponseOnPTR = false;
    private View tradeBookView;
    private TradeBookResponse tradeBookResponse;
    private Spinner assetTypeSpinner, tradeScripSpinner;
    private ArrayList<String> scripList;
    private ArrayList<TradeBook> filteredTradeBookList;
    private ArrayAdapter<String> scripAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private GreekTextView errorMsgLayout;
    private ListView tradeList;
    private GreekCommonAdapter<TradeBookData> commonAdapter;
    private TradeBookData rowData = null;
    private AlertDialog levelDialog;

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            sendTradeBookRequest();
        }
    };
    private final AdapterView.OnItemClickListener tradeBookItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

            Bundle bundle = new Bundle();
            rowData = commonAdapter.getItem(position);

            View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_two_actions, parent, false);
            GreekTextView details = layout.findViewById(R.id.action_item1);
            GreekTextView productChange = layout.findViewById(R.id.action_item2);

            details.setText("Details");
            productChange.setText("Product Change");
            details.setOnClickListener(TradeBookFragment.this);
            productChange.setOnClickListener(TradeBookFragment.this);

            AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
            builder.setView(layout);
            levelDialog = builder.create();
            levelDialog.show();

        }
    };
    private List<TradeBook> tradeBook;
    private final AdapterView.OnItemSelectedListener scripSelector = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (tradeBookResponse != null) {
                if ("All".equalsIgnoreCase(tradeScripSpinner.getSelectedItem().toString())) {
                    handleTradeBookResponse(filteredTradeBookList);
                } else {
                    commonAdapter.clear();

                    for (TradeBook item : tradeBook) {


                        TradeBookData tradeBookData = new TradeBookData();

                        if (getExchange(item.getToken()).equalsIgnoreCase("mcx")) {


                            if (item.getOptionType().equalsIgnoreCase("XX")) {

                                if ((item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument()).equalsIgnoreCase(tradeScripSpinner.getSelectedItem().toString())) {

                                    tradeBookData.setSymbol(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument());

                                    tradeBookData.setToken(item.getToken());
                                    tradeBookData.setExpiryDate(item.getExpiryDate());
                                    tradeBookData.setTrdQty(item.getTrdQty());
                                    tradeBookData.setPrice(item.getPrice());
                                    tradeBookData.setExchange(getExchange(item.getToken()));
                                    tradeBookData.setAmount(item.getAmount());
                                    tradeBookData.setInstrument(item.getInstrument());
                                    tradeBookData.setRegular_lot(item.getRegular_lot());
                                    tradeBookData.setExpiryDate(item.getExpiryDate());
                                    tradeBookData.setcPANNumber(item.getcPANNumber());
                                    tradeBookData.setTradeSymbol(item.getTradeSymbol());

                                    tradeBookData.setUniqueOrderID(item.getUniqueOrderID());
                                    tradeBookData.setOrdID(item.getOrdID());
                                    tradeBookData.setTradeNo(item.getTradeNo());
                                    tradeBookData.setOptionType(item.getOptionType());
                                    tradeBookData.setStrikePrice(item.getStrikePrice());

//                                    if (item.getProduct().equals("1")) {
//                                        tradeBookData.setProduct("Intraday");
//                                    } else if (item.getProduct().equals("0")) {
//                                        tradeBookData.setProduct("Delivery");
//                                    } else if (item.getProduct().equals("2")) {
//                                        tradeBookData.setProduct("MTF");
//                                    }else if (item.getProduct().equals("3")) {
//                                        tradeBookData.setProduct("SSEQ");
//                                    }

                                    if (item.getProduct().equals("1")) {
                                        tradeBookData.setProduct("Intraday");
                                    } else if (item.getProduct().equals("0")) {
                                        tradeBookData.setProduct("Delivery");
                                    } else if (item.getProduct().equals("2")) {
                                        tradeBookData.setProduct("MTF");
                                    } else if (item.getProduct().equals("5")) {
                                        tradeBookData.setProduct("SSEQ");
                                    } else if (item.getProduct().equals("3")) {
                                        tradeBookData.setProduct("TNC");
                                    } else if (item.getProduct().equals("4")) {
                                        tradeBookData.setProduct("CATALYST");
                                    }


                                    if (item.getAction().equals("1")) {
                                        tradeBookData.setAction("Buy");
                                    } else {
                                        tradeBookData.setAction("Sell");
                                    }
                                    tradeBookData.setDetails(item);
                                    commonAdapter.add(tradeBookData);

                                }
                            } else {
                                if ((item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument()).equalsIgnoreCase(tradeScripSpinner.getSelectedItem().toString())) {

                                    tradeBookData.setSymbol(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument());

                                    tradeBookData.setToken(item.getToken());
                                    tradeBookData.setExpiryDate(item.getExpiryDate());
                                    tradeBookData.setTrdQty(item.getTrdQty());
                                    tradeBookData.setPrice(item.getPrice());
                                    tradeBookData.setExchange(getExchange(item.getToken()));
                                    tradeBookData.setAmount(item.getAmount());
                                    tradeBookData.setInstrument(item.getInstrument());
                                    tradeBookData.setRegular_lot(item.getRegular_lot());
                                    tradeBookData.setExpiryDate(item.getExpiryDate());
                                    tradeBookData.setcPANNumber(item.getcPANNumber());
                                    tradeBookData.setTradeSymbol(item.getTradeSymbol());

                                    tradeBookData.setUniqueOrderID(item.getUniqueOrderID());
                                    tradeBookData.setOrdID(item.getOrdID());
                                    tradeBookData.setTradeNo(item.getTradeNo());
                                    tradeBookData.setOptionType(item.getOptionType());
                                    tradeBookData.setStrikePrice(item.getStrikePrice());

                                    if (item.getProduct().equals("1")) {
                                        tradeBookData.setProduct("Intraday");
                                    } else if (item.getProduct().equals("0")) {
                                        tradeBookData.setProduct("Delivery");
                                    } else if (item.getProduct().equals("2")) {
                                        tradeBookData.setProduct("MTF");
                                    } else if (item.getProduct().equals("3")) {
                                        tradeBookData.setProduct("SSEQ");
                                    }

                                    if (item.getAction().equals("1")) {
                                        tradeBookData.setAction("Buy");
                                    } else {
                                        tradeBookData.setAction("Sell");
                                    }
                                    tradeBookData.setDetails(item);
                                    commonAdapter.add(tradeBookData);

                                }
                            }


                        } else {
                            if (item.getSymbol().equalsIgnoreCase(tradeScripSpinner.getSelectedItem().toString())) {

                                tradeBookData.setSymbol(item.getSymbol());
                                tradeBookData.setToken(item.getToken());
                                tradeBookData.setExpiryDate(item.getExpiryDate());
                                tradeBookData.setTrdQty(item.getTrdQty());
                                tradeBookData.setPrice(item.getPrice());
                                tradeBookData.setExchange(getExchange(item.getToken()));
                                tradeBookData.setAmount(item.getAmount());
                                tradeBookData.setInstrument(item.getInstrument());
                                tradeBookData.setRegular_lot(item.getRegular_lot());
                                tradeBookData.setExpiryDate(item.getExpiryDate());
                                tradeBookData.setcPANNumber(item.getcPANNumber());
                                tradeBookData.setTradeSymbol(item.getTradeSymbol());

                                tradeBookData.setUniqueOrderID(item.getUniqueOrderID());
                                tradeBookData.setOrdID(item.getOrdID());
                                tradeBookData.setTradeNo(item.getTradeNo());


                                if (item.getProduct().equals("1")) {
                                    tradeBookData.setProduct("Intraday");
                                } else if (item.getProduct().equals("0")) {
                                    tradeBookData.setProduct("Delivery");
                                } else if (item.getProduct().equals("2")) {
                                    tradeBookData.setProduct("MTF");
                                } else if (item.getProduct().equals("3")) {
                                    tradeBookData.setProduct("SSEQ");
                                }

                                if (item.getAction().equals("1")) {
                                    tradeBookData.setAction("Buy");
                                } else {
                                    tradeBookData.setAction("Sell");
                                }
                                tradeBookData.setDetails(item);
                                commonAdapter.add(tradeBookData);
                            }
                        }
                    }

                }

                if (isWaitingForResponseOnPTR) {
                    isWaitingForResponseOnPTR = false;
                }
                if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
                commonAdapter.notifyDataSetChanged();
            }
        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private GreekTextView rowheader11, rowheader12, rowheader13, rowheader21, rowheader22, rowheader23;
    private final AdapterView.OnItemSelectedListener assetTypeSelector = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            commonAdapter.clear();
            tradeList.setAdapter(commonAdapter);
            sendTitleNames();

            sendTradeBookRequest();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void noDataFound(GreekCommonAdapter adapter) {
        if (adapter.getCount() > 0) {
            errorMsgLayout.setVisibility(View.GONE);
        } else {
            errorMsgLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tradeBookView = super.onCreateView(inflater, container, savedInstanceState);
        //attachLayout(R.layout.fragment_tradebook);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_tradebook).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_tradebook).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
//        AccountDetails.currentFragment = NAV_TO_TRADE_BOOK_SCREEN;
        setupViews();
        setupController();
        setupAdapter();
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 1);
        return tradeBookView;

    }

    private void setupViews() {
        setAppTitle(getClass().toString(), "Trade Book");
        swipeRefresh = tradeBookView.findViewById(R.id.tradeRefresh);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        tradeList = tradeBookView.findViewById(R.id.fnoListView);

        assetTypeSpinner = tradeBookView.findViewById(R.id.optionsSpinner);
        assetTypeSpinner.setOnItemSelectedListener(assetTypeSelector);

        tradeScripSpinner = tradeBookView.findViewById(R.id.expirySpinner);
        tradeScripSpinner.setOnItemSelectedListener(scripSelector);
        LinearLayout tradeBookHeader = tradeBookView.findViewById(R.id.tradeBookListHeader);
        tradeBookHeader.setVisibility(View.VISIBLE);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = tradeBookView.findViewById(R.id.tradeBookListHeader);
            header_layout.setBackgroundColor(getResources().getColor(R.color.selectColor));
        }

        rowheader11 = tradeBookView.findViewById(R.id.rowheader11);
        rowheader12 = tradeBookView.findViewById(R.id.rowheader12);
        rowheader13 = tradeBookView.findViewById(R.id.rowheader13);
        rowheader21 = tradeBookView.findViewById(R.id.rowheader21);
        rowheader22 = tradeBookView.findViewById(R.id.rowheader22);
        rowheader23 = tradeBookView.findViewById(R.id.rowheader23);

        errorMsgLayout = tradeBookView.findViewById(R.id.showmsgLayout);
        /*GreekTextView errorTextView = tradeBookView.findViewById(R.id.errorHeader);
        errorTextView.setText("You do not have any trades for today");*/


        int textColor = AccountDetails.getTextColorDropdown();
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = tradeBookView.findViewById(R.id.tradeBookListHeader);
            header_layout.setBackgroundColor(getResources().getColor(R.color.selectColor));

            rowheader11.setTextColor(Color.WHITE);
        }
    }

    private void sendTitleNames() {

        rowheader11.setText("Symbol");
        rowheader12.setText("Trade Qty");
        rowheader13.setText("Trade Price");
        rowheader21.setText("Exchange");
        rowheader22.setText("Action/Product");
        rowheader23.setText("Trade Value");
    }

    private void sendTradeBookRequest() {
        showProgress();
        isWaitingForResponseOnPTR = true;
        clearData();
        //generateNoteOnSd("TradeBook Request started");
        TradeBookRequest.sendRequest("getTradeBookDetail", assetTypeSpinner.getSelectedItem().toString(), AccountDetails.getClientCode(getMainActivity()), AccountDetails.getSessionId(getMainActivity()), AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }


    private void clearData() {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();

        scripList = new ArrayList<>();
        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        scripAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), scripList){
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
        tradeScripSpinner.setAdapter(scripAdapter);
        tradeScripSpinner.setEnabled(false);
    }

    private void setupController() {

        getAssetTypeList();

        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), assetList){
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
        assetTypeSpinner.setAdapter(assetTypeAdapter);
    }

    private void getAssetTypeList() {

        if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
            assetList.add("Equity");
        }
        if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
            assetList.add("FNO");
        }
        if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
            assetList.add("Currency");
        }
        if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
            assetList.add("Commodity");
        }

        if (assetList.size() > 1) {
            assetList.add(0, "ALL");
        }
    }

    private String getAssetType(String token) {
        int tokenInt = Integer.parseInt(token);
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

    private String getExchange(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else {
            return "BSE";
        }
        //  return "";
    }

    private void setupAdapter() {
        final ArrayList<TradeBookData> tradeBookModel = new ArrayList<>();
        commonAdapter = new GreekCommonAdapter<>(getMainActivity(), tradeBookModel);
        commonAdapter.notifyDataSetChanged();

        int[] newsViewIDs = {R.id.row11, R.id.row12, R.id.row13, R.id.row21, R.id.row22, R.id.row23};
        commonAdapter.setLayoutTextViews(R.layout.row_tradebook, newsViewIDs);
        //commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            commonAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
        } else {
            commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        }
        commonAdapter.setPopulationListener(new GreekPopulationListener<TradeBookData>() {
            @Override
            public void populateFrom(View v, int position, TradeBookData row, View[] views) {


                if (getExchange(row.getToken()).equalsIgnoreCase("mcx") || getExchange(row.getToken()).equalsIgnoreCase("ncdex")) {

                    if (row.getOptionType().equalsIgnoreCase("XX")) {
                        ((ScrollingTextView) views[0]).setText(row.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(row.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + row.getInstrument());
                    } else {
                        ((ScrollingTextView) views[0]).setText(row.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(row.getExpiryDate(), "yyMMM", "bse").toUpperCase() + row.getStrikePrice() + row.getOptionType() + "-" + row.getInstrument());
                    }


                    ((GreekTextView) views[1]).setText(String.valueOf((Integer.parseInt(row.getTrdQty())) / (Integer.parseInt(row.getRegular_lot()))));

                } else {

                    ((ScrollingTextView) views[0]).setText(row.getSymbol() + " - " + row.getInstrument());

                    ((GreekTextView) views[1]).setText(row.getTrdQty());
                }

                if (row.getAssetType().equalsIgnoreCase("currency")) {
                    ((GreekTextView) views[2]).setText(String.format("%.4f", Double.parseDouble(row.getPrice())));

                    String action = row.getAction();

                    if (action.equalsIgnoreCase("Sell")) {
                        ((GreekTextView) views[5]).setText(String.format("%.4f", Double.parseDouble(row.getAmount())));

                    } else {

                        ((GreekTextView) views[5]).setText(String.format("%.4f", Double.parseDouble(row.getAmount())));

                    }


                } else {

                    ((GreekTextView) views[2]).setText(String.format("%.2f", Double.parseDouble(row.getPrice())));


                    String action = row.getAction();

                    if (action.equalsIgnoreCase("Sell")) {
                        ((GreekTextView) views[5]).setText(String.format("%.2f", Double.parseDouble(row.getAmount())));

                    } else {

                        ((GreekTextView) views[5]).setText(String.format("%.2f", Double.parseDouble(row.getAmount())));
                    }
                }
                ((GreekTextView) views[3]).setText(row.getExchange());


                GreekTextView tv = ((GreekTextView) views[4]);
                String action = row.getAction();
                String product = row.getProduct();
                tv.setText(String.format("%s/%s", action, product), TextView.BufferType.SPANNABLE);
                Spannable span = (Spannable) tv.getText();
                if (action.equalsIgnoreCase("Buy")) {
                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.whitetheambuyColor)), 0, action.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else {
                        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.buyColor)), 0, action.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } else if (action.equalsIgnoreCase("Sell")) {
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_textcolor)), 0, action.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                int textColor = AccountDetails.getTextColorDropdown();

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {


                    ((ScrollingTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[5]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

                    ((ScrollingTextView) views[0]).setTextColor(getResources().getColor(textColor));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(textColor));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(textColor));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(textColor));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(textColor));
                    ((GreekTextView) views[5]).setTextColor(getResources().getColor(textColor));
                }

            }

            @Override
            public void onRowCreate(View[] views) {

            }
        });


        tradeList.setAdapter(commonAdapter);


        tradeList.setOnItemClickListener(tradeBookItemClickListener);
        swipeRefresh.setOnRefreshListener(onRefreshListener);

        if (getArguments() != null) {
            if (getArguments().getString("AssetType") != null) {

                if ("All".equalsIgnoreCase(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(0);

                if ("equity".equalsIgnoreCase(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(1);

                if ("fno".equalsIgnoreCase(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(2);

                if ("currency".equalsIgnoreCase(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(3);

                if ("Commodity".equalsIgnoreCase(getArguments().getString("AssetType")))
                    assetTypeSpinner.setSelection(4);


            }
        }
    }

    @Override
    public void handleResponse(Object response) {

        hideProgress();
        JSONResponse jsonResponse = (JSONResponse) response;

//        ((GreekBaseActivity) getMainActivity()).hideMessageOnScreenManually();
        try {
            tradeBookResponse = (TradeBookResponse) jsonResponse.getResponse();
            loadTradeBookData();

        } catch (Exception e) {
            toggleErrorLayout(true);
            e.printStackTrace();
            refreshComplete();
            hideProgress();
        }
        //}
        setAppTitle(getClass().toString(), "Trade Book");
        hideProgress();
    }

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void loadTradeBookData() {
        if (tradeBookResponse != null) {
            scripList = new ArrayList<>();
            scripList.add("All");

            tradeBook = tradeBookResponse.getTradeBook();

            ArrayList<String> typeList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.watchlistTypes)));
            filteredTradeBookList = new ArrayList<>();

            scripAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), scripList);
            scripAdapter.setDropDownViewResource(R.layout.custom_spinner);

            tradeScripSpinner.setAdapter(scripAdapter);
            tradeScripSpinner.setEnabled(true);


            for (TradeBook item : tradeBook) {

                if (typeList.get(assetTypeSpinner.getSelectedItemPosition()).equalsIgnoreCase("All")) {


                    filteredTradeBookList.add(item);

                    if (getExchange(item.getToken()).equalsIgnoreCase("mcx")) {

                        if (item.getOptionType().equalsIgnoreCase("XX")) {
                            if (!scripList.contains(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument())) {
                                scripList.add(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument());
                            }
                        } else {
                            if (!scripList.contains(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument())) {
                                scripList.add(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument());
                            }
                        }
                    } else {

                        if (!scripList.contains(item.getSymbol())) {
                            scripList.add(item.getSymbol());

                        }
                    }


                } else {
                    if (item.getAssetType().equalsIgnoreCase(assetTypeSpinner.getSelectedItem().toString())) {
                        filteredTradeBookList.add(item);

                        if (getExchange(item.getToken()).equalsIgnoreCase("mcx")) {

                            if (item.getOptionType().equalsIgnoreCase("XX")) {
                                if (!scripList.contains(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument())) {
                                    scripList.add(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument());
                                }
                            } else {
                                if (!scripList.contains(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument())) {
                                    scripList.add(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument());
                                }
                            }


                        } else {

                            if (!scripList.contains(item.getSymbol())) {
                                scripList.add(item.getSymbol());

                            }
                        }

                    }
                }
            }

            if (!("All").equalsIgnoreCase(assetTypeSpinner.getSelectedItem().toString())) {
                handleTradeBookResponse(filteredTradeBookList);
                scripAdapter.notifyDataSetChanged();
                tradeList.setAdapter(scripAdapter);
            }
        }
    }

    private void handleTradeBookResponse(List<TradeBook> tradeBook) {
        commonAdapter.clear();

        for (TradeBook item : tradeBook) {
            TradeBookData tradeBookData = new TradeBookData();
            tradeBookData.setAssetType(getAssetType(item.getToken()));

            if (getExchange(item.getToken()).equalsIgnoreCase("MCX")) {
                if (item.getOptionType().equalsIgnoreCase("XX")) {
                    tradeBookData.setSymbol(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + item.getInstrument());
                } else {
                    tradeBookData.setSymbol(item.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(item.getExpiryDate(), "yyMMM", "bse").toUpperCase() + item.getStrikePrice() + item.getOptionType() + "-" + item.getInstrument());
                }

            } else {

                tradeBookData.setSymbol(item.getSymbol() + "-" + item.getInstrument());
            }

            tradeBookData.setToken(item.getToken());
            tradeBookData.setExpiryDate(item.getExpiryDate());
            tradeBookData.setDescription(item.getDescription());
            tradeBookData.setTrdQty(item.getTrdQty());
            tradeBookData.setPrice(item.getPrice());
            tradeBookData.setExchange(getExchange(item.getToken()));
            tradeBookData.setInstrument(item.getInstrument());
            //tradeBookData.setProduct(item.getProduct());
            tradeBookData.setAmount(item.getAmount());
            //tradeBookData.setAction(item.getAction());
            //tradeBookData.setAssetType(item.getAssetType());
            tradeBookData.setQty(item.getQty());
            tradeBookData.setUniqueOrderID(item.getUniqueOrderID());
            tradeBookData.setTrdTime(item.getTrdTime());
            tradeBookData.setOrdID(item.getOrdID());
            tradeBookData.setTradeNo(item.getTradeNo());
            tradeBookData.setTradeDateTime(item.getTradeDateTime());
            tradeBookData.setRegular_lot(item.getRegular_lot());
            tradeBookData.setTradeSymbol(item.getTradeSymbol());
            tradeBookData.setStrikePrice(item.getStrikePrice());
            tradeBookData.setOptionType(item.getOptionType());

            if (item.getProduct().equals("1")) {
                tradeBookData.setProduct("Intraday");
            } else if (item.getProduct().equals("0")) {
                tradeBookData.setProduct("Delivery");
            } else if (item.getProduct().equals("2")) {
                tradeBookData.setProduct("MTF");
            } else if (item.getProduct().equals("3")) {
                tradeBookData.setProduct("SSEQ");
            }
            if (item.getAction().equals("1")) {
                tradeBookData.setAction("Buy");
            } else {
                tradeBookData.setAction("Sell");
            }
            tradeBookData.setDetails(item);
            commonAdapter.add(tradeBookData);

        }

        commonAdapter.notifyDataSetChanged();
        tradeList.setAdapter(commonAdapter);
        noDataFound(commonAdapter);

        if (isWaitingForResponseOnPTR) {
            isWaitingForResponseOnPTR = false;
        }
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void refreshComplete() {
        isWaitingForResponseOnPTR = false;
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onFragmentResume() {
        setAppTitle(getClass().toString(), "Trade Book");
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(3, 1);
        EventBus.getDefault().register(this);
        super.onFragmentResume();
//        AccountDetails.currentFragment = NAV_TO_TRADE_BOOK_SCREEN;

    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        if (isWaitingForResponseOnPTR) {
            isWaitingForResponseOnPTR = false;
        }
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
        super.infoDialog(action, msg, jsonResponse);
    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

        if (isWaitingForResponseOnPTR) {
            isWaitingForResponseOnPTR = false;
        }
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);

        super.showMsgOnScreen(action, msg, jsonResponse);
    }


    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        int id = view.getId();
        if (id == R.id.action_item1) {
            TradeBook hashtable = (TradeBook) rowData.getDetails();
            bundle.putSerializable("Response", hashtable);
            bundle.putSerializable("Type", "OrderBook");
            navigateTo(NAV_TO_TRADE_BOOK_DETAILS_SCREEN, bundle, true);
            levelDialog.dismiss();
        } else if (id == R.id.action_item2) {
            Bundle args = new Bundle();
            int qtylot = Math.abs(Integer.parseInt(rowData.getTrdQty()) / Integer.parseInt(rowData.getRegular_lot()));
            args.putString("qtylot", String.valueOf(qtylot));
            args.putString("lot", rowData.getRegular_lot());
            //args.putString("qty", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
            args.putString("product", getProduct(rowData.getProduct()));
            if (getExchange(rowData.getToken()).equalsIgnoreCase("mcx") || getExchange(rowData.getToken()).equalsIgnoreCase("ncdex")) {
                args.putString("traded_qty_abs", String.valueOf(qtylot));
                args.putString("traded_qty", String.valueOf(Integer.parseInt(rowData.getTrdQty()) / Integer.parseInt(rowData.getRegular_lot())));
                args.putString("qty", String.valueOf(Integer.parseInt(rowData.getTrdQty()) / Integer.parseInt(rowData.getRegular_lot())));
            } else {
                args.putString("traded_qty_abs", String.valueOf(Math.abs(Integer.parseInt(rowData.getTrdQty()))));
                args.putString("traded_qty", rowData.getTrdQty());
                args.putString("qty", rowData.getTrdQty());
            }
            args.putString("gtoken", rowData.getToken());
            args.putString("exchange", getExchange(rowData.getToken()));
            args.putString("assetType", getAssetTypeFromToken(rowData.getToken()));
            args.putString("gorderid", rowData.getUniqueOrderID());
            args.putString("tradeid", rowData.getTradeNo());
            args.putString("eorderid", rowData.getOrdID());
            args.putString("side", rowData.getAction());
            args.putString("from", "Tradebook");
            CustomNetpositionDialogFragment customNetpositionDialogFragment = new CustomNetpositionDialogFragment();
            customNetpositionDialogFragment.setArguments(args);
            customNetpositionDialogFragment.show(getFragmentManager(), "ProductChange");

            levelDialog.dismiss();
        }

    }

    public String getProductType(String type) {
        if (type.equalsIgnoreCase("1"))
            return "Intraday";
        else if (type.equalsIgnoreCase("0"))
            return "Delivery";
        else if (type.equalsIgnoreCase("2"))
            return "MTF";
        else if (type.equalsIgnoreCase("3"))
            return "SSEQ";
        return "";
    }


    public String getProduct(String type) {
        if (type.equalsIgnoreCase("Intraday"))
            return "1";
        else if (type.equalsIgnoreCase("Delivery"))
            return "0";
        else if (type.equalsIgnoreCase("MTF"))
            return "2";
        else if (type.equalsIgnoreCase("SSEQ"))
            return "3";
        return "";
    }

    private String getAssetTypeFromToken(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if (((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }

    }

    public void onEventMainThread(ProductChangeResponse productChangeResponse) {
        try {
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "\n Message : " + productChangeResponse.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    sendTradeBookRequest();
                }
            });

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}
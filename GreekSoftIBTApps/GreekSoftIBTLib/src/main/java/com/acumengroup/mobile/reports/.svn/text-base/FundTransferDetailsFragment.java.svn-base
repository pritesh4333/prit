package com.acumengroup.mobile.reports;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.BankName;
import com.acumengroup.mobile.model.BankNameData;
import com.acumengroup.mobile.model.BankNamesResponce;
import com.acumengroup.ui.adapter.GreekCommonAdapter;
import com.acumengroup.ui.adapter.GreekPopulationListener;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.ui.textview.ScrollingTextView;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.acumengroup.greekmain.util.date.DateTimeFormatter.getDateFromTimeStamp;
import static java.util.Calendar.getInstance;


public class FundTransferDetailsFragment extends GreekBaseFragment {

    private View orderBookView;
    private SwipeRefreshLayout swipeRefresh;
    private ListView bankList;
    private RelativeLayout errorMsgLayout;
    private GreekCommonAdapter<BankNameData> commonAdapter;
    private boolean isWaitingForResponseOnPTR = false;
    private GreekTextView rowheader11, rowheader12, rowheader13, rowheader21, rowheader22, rowheader23;
    private BankNamesResponce bankNamesResponce;
    private List<BankName> bankNames;
    private GreekEditText startDate_text, endDate_text;
    private int year, month, day;
    private  LinearLayout orderBookListHeader;

    public FundTransferDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(0, 1);
    }

    private final AdapterView.OnItemClickListener BankNameItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            Bundle bundle = new Bundle();
            BankNameData rowData = commonAdapter.getItem(arg2);


            BankName hashtable = (BankName) rowData.getDetails();

            bundle.putSerializable("response", hashtable);
            bundle.putSerializable("Type", "BankName");

            navigateTo(NAV_TO_BANK_DETAILS_SCREEN, bundle, true);
        }
    };

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        orderBookView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_fund_transfer_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_fund_transfer_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_FUNDTRANSFERDETAILS_SCREEN;
        setupViews();
        setTheme();
        setupAdapter();
//        ((GreekBaseActivity) getMainActivity()).setChildMenuSelection(8, 1);

        return orderBookView;
//        return inflater.inflate(R.layout.fragment_fund_transfer_details, container, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupViews() {
        setAppTitle(getClass().toString(), "Fund Transfer Details");
        swipeRefresh = orderBookView.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        bankList = orderBookView.findViewById(R.id.fnoListView);


        orderBookListHeader = orderBookView.findViewById(R.id.orderBookListHeader);
        errorMsgLayout = orderBookView.findViewById(R.id.showmsgLayout);
        GreekTextView errorTextView = orderBookView.findViewById(R.id.errorHeader);
        errorTextView.setText("You did not have any bank transactions.");
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            ((GreekTextView) errorTextView.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.black));
        }else{
            ((GreekTextView) errorTextView.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.white));

        }


        rowheader11 = orderBookView.findViewById(R.id.rowheader11);
        rowheader12 = orderBookView.findViewById(R.id.rowheader12);
        rowheader13 = orderBookView.findViewById(R.id.rowheader13);
        rowheader21 = orderBookView.findViewById(R.id.rowheader21);
        rowheader22 = orderBookView.findViewById(R.id.rowheader22);
        rowheader23 = orderBookView.findViewById(R.id.rowheader23);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = orderBookView.findViewById(R.id.orderBookListHeader);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

        rowheader11.setText("Trans ID");
        rowheader12.setText("Bank Trans ID");
        rowheader13.setText("Bank Name");
        rowheader21.setText("Date Time");
        rowheader22.setText("Amount cr./dr.");
        rowheader23.setText("Status");

        sendOrderStatusRequest("All");


        startDate_text = orderBookView.findViewById(R.id.start_text);
        startDate_text.setShowSoftInputOnFocus(false);
        startDate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate_text.setText("");
                showDialog(startDate_text);
            }
        });

        endDate_text = orderBookView.findViewById(R.id.endDate_text);
        endDate_text.setShowSoftInputOnFocus(false);
        endDate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDate_text.setText("");
                showDialog(endDate_text);
            }
        });

    }

    private void setupAdapter() {
        int[] newsViewIDs = {R.id.row11, R.id.row12, R.id.row13, R.id.row21, R.id.row22, R.id.row23};
        ArrayList<BankNameData> bankNameModel = new ArrayList<>();
        commonAdapter = new GreekCommonAdapter<>(getMainActivity(), bankNameModel);
        commonAdapter.setLayoutTextViews(R.layout.row_order_book, newsViewIDs);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            commonAdapter.setAlternativeRowColor(getResources().getColor(AccountDetails.backgroundColor), getResources().getColor(AccountDetails.backgroundColor));
        } else {
            commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));
        }
        //commonAdapter.setAlternativeRowColor(getResources().getColor(R.color.market_grey_light), getResources().getColor(R.color.market_grey_dark));

        commonAdapter.setPopulationListener(new GreekPopulationListener<BankNameData>() {

            @Override
            public void populateFrom(View v, int position, BankNameData row, View[] views) {

                ((ScrollingTextView) views[0]).setText(row.getTranId());
                ((GreekTextView) views[1]).setText(row.getBankTranId());
                ((GreekTextView) views[2]).setText(row.getBankName());
                ((GreekTextView) views[3]).setText(row.getDateAndTime());
                ((GreekTextView) views[4]).setText(row.getAmount()+" cr.");

                if (row.getStatusFlag().equalsIgnoreCase("0")) {
                    ((GreekTextView) views[5]).setText("Initiated");
                } else if (row.getStatusFlag().equalsIgnoreCase("1")) {
                    ((GreekTextView) views[5]).setText("Ack Received");
                } else if (row.getStatusFlag().equalsIgnoreCase("2")) {
                    ((GreekTextView) views[5]).setText("Request Sent");
                } else if (row.getStatusFlag().equalsIgnoreCase("3")) {
                    ((GreekTextView) views[5]).setText("Completed");
                } else {
                    ((GreekTextView) views[5]).setText("Invalid Status");
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


                    ((ScrollingTextView) views[0]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[1]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[2]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[3]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[4]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    ((GreekTextView) views[5]).setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }

            @Override
            public void onRowCreate(View[] views) {

            }
        });


        bankList.setAdapter(commonAdapter);

        bankList.setOnItemClickListener(BankNameItemClickListener);

    }

    private void showDialog(final EditText Date_text) {

        Calendar now = getInstance();

        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Date_text.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                if (startDate_text.getText().toString() != null && startDate_text.getText().toString().length() > 0 &&
                        endDate_text.getText().toString() != null && endDate_text.getText().toString().length() > 0) {

                    String startDate = startDate_text.getText().toString();
                    String endDate = endDate_text.getText().toString();
                    showProgress();

                    String sessionId = AccountDetails.getSessionId(getActivity());
                    String clientCode = AccountDetails.getClientCode(getActivity());


                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date strDate = sdf.parse(startDate);
                        Date EDdate = sdf.parse(endDate);

                        clearData();

                        String serviceURL = "getTransactionDetailsByDate?gcid=" + clientCode +
                                "&gscid=" + AccountDetails.getUsername(getMainActivity()) +
                                "&endDate=" + EDdate.getTime() + "&startDate=" + strDate.getTime();

                        Log.e("FundTransferDetails", "serviceURL========>" + serviceURL);
                        //To get Fundtransfer details get request is send to server
                        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                            @Override
                            public void onSuccess(JSONObject response) {

                                Log.e("FundTransferDetails", "serviceURL===response=====>" + response);

                                try {
                                    hideProgress();
                                    bankNamesResponce = new BankNamesResponce();
                                    bankNames = bankNamesResponce.fromJSON(response);

                                    handleBankNameResponse(bankNames);

                                } catch (Exception ex) {

                                    ex.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(String message) {//todo
                                toggleErrorLayout(true);
                                //hideProgress();
                                isWaitingForResponseOnPTR = false;
                                refreshComplete();
                            }
                        });


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, year, month, day);



        dpd.show();
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());


    }

    private void sendOrderStatusRequest(String order_status) {

        if (!isWaitingForResponseOnPTR) {
            showProgress();

            String sessionId = AccountDetails.getSessionId(getActivity());
            String clientCode = AccountDetails.getClientCode(getActivity());

            String serviceURL = "getTransactionDetails?gcid=" + clientCode + "&gscid=" + AccountDetails.getUsername(getMainActivity());
            // to get orderstatus get request is send to aracane server
            WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    try {
                        hideProgress();
                        bankNamesResponce = new BankNamesResponce();
                        bankNames = bankNamesResponce.fromJSON(response);

                        handleBankNameResponse(bankNames);

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String message) {//todo
                    toggleErrorLayout(true);
                    //hideProgress();
                    isWaitingForResponseOnPTR = false;
                    refreshComplete();
                }
            });
        }
    }

    private void handleBankNameResponse(List<BankName> bankNames) {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();

        for (BankName item : bankNames) {

            BankNameData bankNameData = new BankNameData();

            bankNameData.setTranId(item.getTranId());
            bankNameData.setBankTranId(item.getBankTranId());
            bankNameData.setBankName(item.getBankName());
            bankNameData.setDateAndTime(getDateFromTimeStamp(item.getDateAndTime(), "dd MMM yyyy", "BSE"));
            bankNameData.setAmount(item.getAmount());
            bankNameData.setTransStatus(item.getTransStatus());
            bankNameData.setToken(item.getToken());
            bankNameData.setBankName(item.getBankName());
            bankNameData.setBankTranId(item.getBankTranId());
            bankNameData.setCardNumber(item.getCardNumber());
            bankNameData.setcGreekClientid(item.getcGreekClientid());
            bankNameData.setDiscriminator(item.getDiscriminator());
            bankNameData.setGcid(item.getGcid());
            bankNameData.setGscid(item.getGscid());
            bankNameData.setLast_updated_time(item.getLast_updated_time());
            bankNameData.setLevel1_reqTime(item.getLevel1_reqTime());
            bankNameData.setLevel1_resTime(item.getLevel1_resTime());
            bankNameData.setLevel2_reqTime(item.getLevel2_reqTime());
            bankNameData.setLevel2_resTime(item.getLevel2_resTime());
            bankNameData.setStatusFlag(item.getStatusFlag());
            bankNameData.setTempTranId(item.getTempTranId());
            bankNameData.setTransStage(item.getTransStage());
            bankNameData.settType(item.gettType());
            bankNameData.setUniqueId(item.getUniqueId());
            bankNameData.setDetails(item);

            commonAdapter.add(bankNameData);
            commonAdapter.notifyDataSetChanged();
            bankList.setAdapter(commonAdapter);
            noDataFound(commonAdapter);
        }
        Log.e("AdapterSize", "commonAdapter Size====>" + commonAdapter.getCount());

        refreshComplete();
    }

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void noDataFound(GreekCommonAdapter adapter) {
        if (adapter.getCount() > 0) {
            errorMsgLayout.setVisibility(View.GONE);
        } else {
            errorMsgLayout.setVisibility(View.VISIBLE);
        }
    }

    private void refreshComplete() {
        isWaitingForResponseOnPTR = false;
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void clearData() {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();
    }

    private void setTheme() {

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            startDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            orderBookListHeader.setBackgroundColor(getResources().getColor(R.color.selectColor));
            endDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            endDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_black_24dp, 0);
            endDate_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_black_24dp, 0);


        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

            startDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            endDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            endDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_white_24dp, 0);
            endDate_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_white_24dp, 0);
        }
    }
}

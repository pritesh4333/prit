package com.acumengroup.mobile.trade;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.greekmain.core.model.SWPOrder.OrderDetailModel;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.SipSummaryModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MutualFundSTPFragment extends GreekBaseFragment implements View.OnClickListener {

    private final ArrayList<AvailableSchemeListModel> toschemeListData = new ArrayList<>();
    Bundle bundle;
    private AvailableSchemeListModel mfSchemeData;
    private List<String> fundHouseList;
    private List<String> categoryList;
    private List<String> schemeNameList;
    private ArrayAdapter<String> fundHouseAdapter, categoryAdapter, schemesAdapter;
    private String blockCharacterSet = "~#@^|$%&@><*!-?+=_()";
    private GreekEditText editfolioNumber, editTransferAmt, editNumberOfInstallment, editEndDate, editSubBrokerCode, editEUIN, editRemarks, editIntrenalRefNumber, editSubBrokerARNCode;
    private Spinner spnrAmc, spnrBuySellType, spnrToSchemename, sprnToSchmType, sprnFrequencyType, sprnTransactionMode, sprnEuinDecl;
    private CheckBox chkFirstOrder;
    private Button btnPlaceorder;
    private GreekEditText startDate;
    private ArrayList<OrderDetailModel> list;
    private GreekTextView schemeName, NavRs, returns, aum;
    private MaterialRatingBar ratingBar;
    private View layout;
    private CardView cardView;

    private final AdapterView.OnItemSelectedListener fundHouseSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            TextView selectedText = (TextView) parent.getChildAt(0);
            if (selectedText != null) {
                selectedText.setTextColor(getResources().getColor(R.color.black));
                selectedText.setSingleLine(true);
            }
            if (position != 0) {

                sprnToSchmType.setSelection(0);
                spnrToSchemename.setSelection(0);

                showProgress();
                loadSpinnerData("getCategory?FundHouse=" + spnrAmc.getItemAtPosition(position), "category");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private final AdapterView.OnItemSelectedListener categorySelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            TextView selectedText = (TextView) parent.getChildAt(0);
            if (selectedText != null) {
                selectedText.setTextColor(getResources().getColor(R.color.black));
                selectedText.setSingleLine(true);
            }

            if (position != 0) {
                spnrToSchemename.setSelection(0);
                showProgress();
                loadSpinnerData("getMFSchemeForFundHouseCategory?FundHouse=" + spnrAmc.getSelectedItem().toString() + "&Category=" + sprnToSchmType.getItemAtPosition(position) + "&gcid=" + AccountDetails.getClientCode(getMainActivity()), "schemes");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener schemeSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            TextView selectedText = (TextView) parent.getChildAt(0);
            if (selectedText != null) {
                selectedText.setTextColor(getResources().getColor(R.color.black));
                selectedText.setSingleLine(true);
            }

            if (position > 0) {
                position = position - 1;
                enableOrDisableButtons(toschemeListData.get(position).getTradingISIN(), toschemeListData.get(position).getSipISIN());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void enableOrDisableButtons(String tradingISIN, String sipISIN) {
        if (tradingISIN.equals("")) {
            btnPlaceorder.setEnabled(false);
        } else {
            btnPlaceorder.setEnabled(true);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mf_stp).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mf_stp).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        getFromIntent();

        cardView = mfActionView.findViewById(R.id.cardview);
        editfolioNumber = mfActionView.findViewById(R.id.edit_folio_number);
        editTransferAmt = mfActionView.findViewById(R.id.edit_transfer_amount);
        editNumberOfInstallment = mfActionView.findViewById(R.id.edit_num_installment);
        editEndDate = mfActionView.findViewById(R.id.edit_end_date);
        editSubBrokerCode = mfActionView.findViewById(R.id.edit_sub_broker_code);
        editEUIN = mfActionView.findViewById(R.id.edit_euin_number);
        editRemarks = mfActionView.findViewById(R.id.edit_remark);
        editIntrenalRefNumber = mfActionView.findViewById(R.id.edit_internal_ref_number);
        editSubBrokerARNCode = mfActionView.findViewById(R.id.edit_sub_broker_arn_code);
        startDate = mfActionView.findViewById(R.id.txt_start_date);
        //calender = (ImageView) mfActionView.findViewById(R.id.date_calender);
        chkFirstOrder = mfActionView.findViewById(R.id.chk_genarate_first_order);


        schemeName = mfActionView.findViewById(R.id.schemeName);
        returns = mfActionView.findViewById(R.id.returns);
        NavRs = mfActionView.findViewById(R.id.NavRs);
        aum = mfActionView.findViewById(R.id.aum);
        ratingBar = mfActionView.findViewById(R.id.ratingBar);


        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };


        //startDate.setFilters((new InputFilter[]{filter}));
        InputFilter[] lengthfilters = {new InputFilter.LengthFilter(10)};
        startDate.setFilters(lengthfilters);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        startDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (startDate.getRight() - startDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (event.getRawX() >= (startDate.getRight() - startDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            showDatePicker();

                            return true;
                        }
                    }
                }
                return false;
            }

        });

        spnrBuySellType = mfActionView.findViewById(R.id.spnr_buy_sell_type);

        int textColor = AccountDetails.getTextColorDropdown();

        //spnrToSchemename, sprnToSchmType, sprnFrequencyType, sprnTransactionMode, sprnEuinDecl;


        sprnToSchmType = mfActionView.findViewById(R.id.spnr_to_scheme_type);
        categoryList = new ArrayList<>();
        categoryList.add("Select Category");
        categoryAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprnToSchmType.setAdapter(categoryAdapter);
        sprnToSchmType.setOnItemSelectedListener(categorySelectedListener);

        sprnFrequencyType = mfActionView.findViewById(R.id.spnr_frequecy_type);
        ArrayList frequencyType = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.frequencyType)));
        ArrayAdapter<String> frequencyTypeAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, frequencyType);
        frequencyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprnFrequencyType.setAdapter(frequencyTypeAdapter);
        sprnFrequencyType.setSelection(0);
        sprnFrequencyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                    selectedText.setSingleLine(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sprnTransactionMode = mfActionView.findViewById(R.id.spnr_transaction_mode);
        sprnEuinDecl = mfActionView.findViewById(R.id.spnr_euin_decl);

        spnrToSchemename = mfActionView.findViewById(R.id.spnr_to_scheme_name);
        schemeNameList = new ArrayList<>();
        schemeNameList.add("Select Scheme Name");
        schemesAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, schemeNameList);
        schemesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnrToSchemename.setAdapter(schemesAdapter);
        spnrToSchemename.setOnItemSelectedListener(schemeSelectedListener);

        spnrAmc = mfActionView.findViewById(R.id.spnr_amc_name);
        fundHouseList = new ArrayList<>();
        fundHouseList.add("Select AMC");
        fundHouseAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, fundHouseList);
        fundHouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrAmc.setAdapter(fundHouseAdapter);
        spnrAmc.setOnItemSelectedListener(fundHouseSelectedListener);


        chkFirstOrder = mfActionView.findViewById(R.id.chk_genarate_first_order);
        btnPlaceorder = mfActionView.findViewById(R.id.btn_place_order);

        startDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                editEndDate.setText("");
                editNumberOfInstallment.setText("");
            }
        });

        editNumberOfInstallment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String swpEndDate = getSipEndDate(startDate.getText().toString(), sprnFrequencyType.getSelectedItem().toString(), editNumberOfInstallment.getText().toString());
                editEndDate.setText(swpEndDate);
            }
        });


        btnPlaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        sendOrderRequest();

        ArrayList transactionMode = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.stptransactionMode)));
        ArrayAdapter<String> transactionModeAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, transactionMode);
        transactionModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprnTransactionMode.setAdapter(transactionModeAdapter);
        sprnTransactionMode.setSelection(0);
        sprnTransactionMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                    selectedText.setSingleLine(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList buyselltype = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.buySellType)));
        ArrayAdapter<String> buyselltypeAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, buyselltype);
        buyselltypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrBuySellType.setAdapter(buyselltypeAdapter);
        spnrBuySellType.setSelection(0);
        spnrBuySellType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                    selectedText.setSingleLine(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList euinNumber = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.EuinDecl)));
        ArrayAdapter<String> euinNumberAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, euinNumber);

        euinNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprnEuinDecl.setAdapter(euinNumberAdapter);
        sprnEuinDecl.setSelection(0);
        sprnEuinDecl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                    selectedText.setSingleLine(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        loadSpinnerData("getFundHouse", "fundhouse");
        getOverview();


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {


            editfolioNumber.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editTransferAmt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editNumberOfInstallment.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editEndDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editSubBrokerCode.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editEUIN.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editRemarks.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editIntrenalRefNumber.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editSubBrokerARNCode.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //chkFirstOrder.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //chkFirstOrder.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            //cardView.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark_background));


//            spnrAmc.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            spnrBuySellType.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            spnrToSchemename.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            sprnToSchmType.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            sprnFrequencyType.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            sprnTransactionMode.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            sprnEuinDecl.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));


        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

            //cardView.setCardBackgroundColor(getResources().getColor(R.color.cardview_light_background));

//            editfolioNumber.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editTransferAmt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editNumberOfInstallment.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editEndDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editSubBrokerCode.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editEUIN.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editRemarks.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editIntrenalRefNumber.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            editSubBrokerARNCode.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            startDate.setTextColor(getResources().getColor(textColor));
//            startDate.setHintTextColor(getResources().getColor(textColor));
           // chkFirstOrder.setTextColor(getResources().getColor(textColor));
           // chkFirstOrder.setHintTextColor(getResources().getColor(textColor));


            spnrAmc.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            spnrBuySellType.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            spnrToSchemename.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            sprnToSchmType.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            sprnFrequencyType.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            sprnTransactionMode.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            sprnEuinDecl.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));

        }

        return mfActionView;
    }

    private void sendOrderRequest() {

        String userName = Util.getPrefs(getMainActivity()).getString("GREEK_RETAINED_CUST_UNAME", "");

        String orderDetails = "getOrderDetailsForSwitchOrders?clientCode=" + userName;
        showProgress();

        WSHandler.getRequest(getMainActivity(), orderDetails, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    Log.e("Response JSONOBJECT", response.getJSONArray("data").toString());


                    JSONArray respCategory = response.getJSONArray("data");
                    for (int i = 0; i < respCategory.length(); i++) {
                        OrderDetailModel orderModel = new OrderDetailModel();
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        orderModel.setIGreekCode(jsonObject.getInt("iGreekCode"));
                        orderModel.setLLogTime(jsonObject.getInt("lLogTime"));
                        orderModel.setIErrorCode(jsonObject.getInt("iErrorCode"));
                        orderModel.setCMemberCode(jsonObject.getString("cMemberCode"));
                        orderModel.setCDate(jsonObject.getString("cDate"));
                        orderModel.setCTime(jsonObject.getString("cTime"));
                        orderModel.setCMFOrderNo(jsonObject.getString("cMFOrderNo"));
                        orderModel.setCSettNo(jsonObject.getString("cSettNo"));
                        orderModel.setCClientCode(jsonObject.getString("cClientCode"));
                        orderModel.setCClientName(jsonObject.getString("cClientName"));
                        orderModel.setCSchemeCode(jsonObject.getString("cSchemeCode"));
                        orderModel.setCSchemeName(jsonObject.getString("cSchemeName"));
                        orderModel.setCISIN(jsonObject.getString("cISIN"));
                        orderModel.setCBuySell(jsonObject.getString("cBuySell"));
                        orderModel.setDAmount(jsonObject.getInt("dAmount"));
                        orderModel.setDUnits(jsonObject.getInt("dUnits"));
                        orderModel.setCDPTrans(jsonObject.getString("cDPTrans"));
                        orderModel.setCDPFolioNo(jsonObject.getString("cDPFolioNo"));
                        orderModel.setCFolioNo(jsonObject.getString("cFolioNo"));
                        orderModel.setCAllRedeem(jsonObject.getString("cEntryBy"));
                        orderModel.setCOrderStatus(jsonObject.getString("cOrderStatus"));
                        orderModel.setCOrderRemark(jsonObject.getString("cOrderRemark"));
                        orderModel.setCInternalRefNo(jsonObject.getString("cInternalRefNo"));
                        orderModel.setCSettlementType(jsonObject.getString("cSettlementType"));
                        orderModel.setCOrderType(jsonObject.getString("cOrderType"));
                        orderModel.setLSIPRegNo(jsonObject.getInt("lSIPRegNo"));
                        orderModel.setCSIPRegDate(jsonObject.getString("cSIPRegDate"));
                        orderModel.setCEUIN(jsonObject.getString("cEUIN"));
                        orderModel.setCEUINDecl(jsonObject.getString("cEUINDecl"));
                        orderModel.setCAllUnitsFlag(jsonObject.getString("cAllUnitsFlag"));
                        orderModel.setCDPCFlag(jsonObject.getString("cDPCFlag"));
                        orderModel.setCOrderSubType(jsonObject.getString("cOrderSubType"));
                        orderModel.setCOrderSubType(jsonObject.getString("cFirstOrderToday"));
                        orderModel.setCPurchaseRedeem(jsonObject.getString("cPurchaseRedeem"));
                        orderModel.setCMemberRemarks(jsonObject.getString("cMemberRemarks"));
                        orderModel.setCKYCFlag(jsonObject.getString("cKYCFlag"));
                        orderModel.setCMINredemptionflag(jsonObject.getString("cMINredemptionflag"));
                        orderModel.setCSubbrokerARN(jsonObject.getString("cSubbrokerARN"));
                        orderModel.setLOurOrderNo(jsonObject.getInt("lOurOrderNo"));
                        orderModel.setCTransactionCode(jsonObject.getString("cTransactionCode"));
                        orderModel.setCUniqueRefNumber(jsonObject.getString("cUniqueRefNumber"));
                        orderModel.setLOrderId(jsonObject.getInt("lOrderId"));
                        orderModel.setLUserId(jsonObject.getInt("lUserId"));
                        orderModel.setCBuySellType(jsonObject.getString("cBuySellType"));
                        orderModel.setCAllRedeem(jsonObject.getString("cAllRedeem"));
                        orderModel.setCSuccessflag(jsonObject.getString("cSuccessflag"));

                        list = new ArrayList<>();
                        list.add(orderModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    Log.d("Exception", ex.toString());
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
        hideProgress();

    }

    private String getSipEndDate(String startDateStr, String sipFrequency, String sipNoOfInst) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = sdf.parse(startDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            if (sipFrequency.equalsIgnoreCase("MONTHLY")) {
                calendar.add(Calendar.MONTH, Integer.parseInt(sipNoOfInst));
            } else if (sipFrequency.equalsIgnoreCase("QUARTERLY")) {
                calendar.add(Calendar.MONTH, (Integer.parseInt(sipNoOfInst) * 4));
            } else if (sipFrequency.equalsIgnoreCase("WEEKLY")) {
                calendar.add(Calendar.WEEK_OF_MONTH, (Integer.parseInt(sipNoOfInst)));
            }

            return sdf.format(calendar.getTime());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return startDateStr;

    }


    private void getFromIntent() {
        if (getArguments().getBoolean("isSIPOrder")) {
            mfSchemeData = (AvailableSchemeListModel) getArguments().getSerializable("Request");
            mfSchemeData.getSchemeName();
        }
    }

    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i("Log", "***** IP=" + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Log", ex.toString());
        }
        return null;
    }


    //submit sip data to server
    private void placeOrder() {

        String sipFrequency, swpNoOfInst, folio, transactionMode, firstOrderToday, localIp, transferAmount, str_date, end_date;
        String sessionId = AccountDetails.getToken(getMainActivity());
        String clientCode = mfSchemeData.getMfClientCode();


        if (sprnFrequencyType.getSelectedItemPosition() == 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Please select Frequency Type",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });

            return;
        }

        sipFrequency = sprnFrequencyType.getSelectedItem().toString();


        folio = editfolioNumber.getText().toString();


        if (sprnTransactionMode.getSelectedItem().toString().equalsIgnoreCase("Physical")) {
            transactionMode = "P";
        } else {
            transactionMode = "D";
        }


        localIp = getLocalIpAddress();

        if (chkFirstOrder.isChecked()) {
            firstOrderToday = "Y";

        } else {

            firstOrderToday = "N";
        }


        SipSummaryModel summary = new SipSummaryModel();
        summary.setFromSchemeName(mfSchemeData.getSchemeName());
        summary.setSchemeIsin(mfSchemeData.getISIN());
        summary.setFolioNumber(folio);
        summary.setFromSchemeCode(mfSchemeData.getBseCode());

        summary.setClientCode(clientCode);
        summary.setSessionId(sessionId);
        summary.setSipFrequency(sipFrequency);
        if (list != null) {
            summary.setInternalRefNumber(list.get(0).getCInternalRefNo());
        }

        if (spnrToSchemename.getSelectedItemPosition() == 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Please select scheme",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
            return;

        }


        if (editTransferAmt.getText().toString().length() <= 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Enter transfer amount",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
            return;

        } else {

            transferAmount = editTransferAmt.getText().toString();

        }

        if (startDate.getText().toString().length() <= 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Enter Start Date",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
            return;
        } else if (!validDate(startDate.getText().toString())) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Enter valid start date", "OK", false, null);
            return;
        } else {
            str_date = startDate.getText().toString();
        }

        if (editNumberOfInstallment.getText().toString().length() <= 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Enter number of installment",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
            return;
        } else {
            swpNoOfInst = editNumberOfInstallment.getText().toString();
        }


        if (editEndDate.getText().toString().length() <= 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Enter End Date",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });

            return;
        } else {
            end_date = editEndDate.getText().toString();

        }

        if (sprnEuinDecl.getSelectedItemPosition() == 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "please select EUIN Decl",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
            return;
        }


        if (spnrBuySellType.getSelectedItemPosition() == 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "please select Buy Sell type",
                    "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            if (action == GreekDialog.Action.OK) {

                            }
                        }
                    });
            return;
        }

        summary.setToSchemeCode(toschemeListData.get(spnrToSchemename.getSelectedItemPosition() - 1).getBseCode());
        summary.setToSchemeName(toschemeListData.get(spnrToSchemename.getSelectedItemPosition() - 1).getSchemeName());
        summary.setTransferAmt(transferAmount);
        summary.setSipNoOfInst(swpNoOfInst);
        summary.setStartDate(str_date);
        summary.setEndDate(end_date);
        summary.setLocalIp(localIp);
        summary.setTransmode(transactionMode);
        summary.setFirstOrderToday(firstOrderToday);

        String euindecl;
        if (sprnEuinDecl.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
            euindecl = "Y";
        } else {
            euindecl = "N";
        }
        summary.setEuinDecl(euindecl);
        summary.setNumberOfTransfer(editNumberOfInstallment.getText().toString());
        summary.setBuySelltype(spnrBuySellType.getSelectedItem().toString());


        Bundle bundle = new Bundle();
        bundle.putSerializable("summary", summary);
        bundle.putString("from", "stp");
        navigateTo(NAV_TO_MUTUAL_FUND_SIP_SUMMARY, bundle, true);

    }

    @Override
    public void onClick(View view) {

    }


    private void showDatePicker() {
        DatePickerstartdateFragment datePickerDialog = new DatePickerstartdateFragment();
        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setArguments(args);

        datePickerDialog.setCallBack(ondate);

        datePickerDialog.show(getActivity().getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String selecteddate = day + "/" + (month + 1) + "/" + year;
            updateLabel(selecteddate);
        }
    };


    private void loadSpinnerData(String url, final String type) {

        showProgress();
        WSHandler.getRequest(getMainActivity(), url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    switch (type) {
                        case "fundhouse":
                            fundHouseList.clear();
                            fundHouseList.add("Select Fund House");
                            for (int i = 0; i < respCategory.length(); i++) {

                                fundHouseList.add(respCategory.getJSONObject(i).getString("cAMCName"));

                            }
                            fundHouseAdapter.notifyDataSetChanged();
                            break;
                        case "category":
                            categoryList.clear();
                            categoryList.add("Select Category");
                            for (int i = 0; i < respCategory.length(); i++) {
                                categoryList.add(respCategory.getJSONObject(i).getString("category"));
                            }
                            categoryAdapter.notifyDataSetChanged();
                            break;
                        case "schemes":
                            toschemeListData.clear();
                            schemeNameList.clear();
                            schemeNameList.add("Select Scheme Name");
                            schemesAdapter.notifyDataSetChanged();

                            for (int i = 0; i < respCategory.length(); i++) {
                                JSONObject objJSONObject = respCategory.getJSONObject(i);
                                schemeNameList.add(objJSONObject.optString("schemeName"));

                                AvailableSchemeListModel availableSchemeList = new AvailableSchemeListModel();
                                availableSchemeList.setAMCName(objJSONObject.optString("AMCName"));
                                availableSchemeList.setNAVDate(objJSONObject.optString("navDate"));
                                availableSchemeList.setMaxPurchaseAmount(objJSONObject.optString("maxPurchaseAmt"));
                                availableSchemeList.setMinAddPurchaseAmount(objJSONObject.optString("minPurchaseAmt"));
                                availableSchemeList.setSchemeCode(objJSONObject.optString("schemeCode"));
                                availableSchemeList.setTradingISIN(objJSONObject.optString("tradingISIN"));
                                availableSchemeList.setSipISIN(objJSONObject.optString("sipISIN"));
                                availableSchemeList.setMfSchemeCode(objJSONObject.optString("corpSchCode"));
                                availableSchemeList.setNAV(objJSONObject.optString("navValue"));
                                availableSchemeList.setMinPurchaseAmount(objJSONObject.optString("minPurchaseAmt"));
                                availableSchemeList.setISIN(objJSONObject.optString("ISIN"));
                                availableSchemeList.setSchemeName(objJSONObject.optString("schemeName"));
                                availableSchemeList.setSchemeType(objJSONObject.optString("schemeType"));
                                availableSchemeList.setMfClientCode(objJSONObject.optString("mfClientCode"));
                                availableSchemeList.setBseCode(objJSONObject.optString("bseCode"));
                                availableSchemeList.setBseRTACode(objJSONObject.optString("bseRTACode"));
                                toschemeListData.add(availableSchemeList);
                            }
                            schemesAdapter.notifyDataSetChanged();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    private void getOverview() {

        String sipMFOverview = "getMFOverview?mf_schcode=" + mfSchemeData.getSchemeCode();
        showProgress();

        WSHandler.getRequest(getMainActivity(), sipMFOverview, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {

                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        if (jsonObject.getString("starRating") != null) {

                            float rateing = Float.parseFloat(jsonObject.getString("starRating"));

                            if (rateing < 0) {
                                ratingBar.setVisibility(View.INVISIBLE);

                            } else {

                                ratingBar.setRating(rateing);

                            }
                        } else {

                            ratingBar.setVisibility(View.INVISIBLE);
                        }

                        schemeName.setText(jsonObject.getString("SchemeName"));
                        NavRs.setText(String.format("%.2f", Double.parseDouble(jsonObject.getString("CurrNAV"))));
                        String value = String.format("%.2f", Double.parseDouble(jsonObject.getString("threeYearRet")));
                        returns.setText(value + "%");
                        aum.setText(String.format("%.2f", Double.parseDouble(jsonObject.getString("AssetSize"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    Log.d("Exception", ex.toString());
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
        hideProgress();
    }


    private void updateLabel(String str) {
// parse the String "29/07/2013" to a java.util.Date object
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// format the java.util.Date object to the desired format
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        startDate.setText(formattedDate);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUAL_FUND_STP;
    }

    private boolean validDate(String dtStart) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(dtStart);
            System.out.println(date);

            return System.currentTimeMillis() < date.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

}


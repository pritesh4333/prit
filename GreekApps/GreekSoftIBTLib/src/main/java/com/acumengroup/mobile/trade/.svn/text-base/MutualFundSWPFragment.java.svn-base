package com.acumengroup.mobile.trade;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
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

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MutualFundSWPFragment extends GreekBaseFragment {

    Bundle bundle;
    private AvailableSchemeListModel mfSchemeData;
    private ArrayList<String> sipDateList = new ArrayList<>();
    private ArrayAdapter<String> sipDateAdapter;
    private Spinner transmodeSpinner, frequencyTypeSpinner, sipDateSpinner, euinDeclSpinner;
    private GreekTextView instAmtLabel;
    static GreekEditText startDate;
    private GreekEditText folioNumberText, withDrawalUnitsText, withDrawalAmtText, numOfInstallmentText, endDateText, subBrokerCodetext, euinNumberText, remarksText, internalrefNoText, subBrokerarncodeText;
    private double sipAmountMax, sipAmountMin;
    private long sipNoOfInstMax, sipNoOfInstMin;
    private CheckBox termChkBox, firstorder;
    private View layout;
    private String blockCharacterSet = "~#@^|$%&@><*!-?+=_()";
    private ArrayList<OrderDetailModel> list;
    private GreekTextView schemeName, NavRs, returns, aum;
    private MaterialRatingBar ratingBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mf_swp).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mf_swp).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }


        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        transmodeSpinner = mfActionView.findViewById(R.id.sprn_transaction_mode);
        frequencyTypeSpinner = mfActionView.findViewById(R.id.spnr_frequency_type);
        euinDeclSpinner = mfActionView.findViewById(R.id.spnr_euin_decl);

        folioNumberText = mfActionView.findViewById(R.id.edit_folio_number);
        withDrawalUnitsText = mfActionView.findViewById(R.id.edit_withdrawal_unit);
        withDrawalAmtText = mfActionView.findViewById(R.id.edit_withdrawal_amt);
        numOfInstallmentText = mfActionView.findViewById(R.id.edit_num_of_installment);
        endDateText = mfActionView.findViewById(R.id.edit_end_date);
        subBrokerCodetext = mfActionView.findViewById(R.id.edit_sub_broker_code);
        subBrokerarncodeText = mfActionView.findViewById(R.id.edit_sub_broker_arn_code);
        euinNumberText = mfActionView.findViewById(R.id.edit_euin_no);
        remarksText = mfActionView.findViewById(R.id.edit_remarks);
        internalrefNoText = mfActionView.findViewById(R.id.edit_internal_ref_no);
        subBrokerarncodeText = mfActionView.findViewById(R.id.edit_sub_broker_arn_code);
        startDate = mfActionView.findViewById(R.id.txt_start_date);
        firstorder = mfActionView.findViewById(R.id.chk_genarate_first_order);

        schemeName = mfActionView.findViewById(R.id.schemeName);
        returns = mfActionView.findViewById(R.id.returns);
        NavRs = mfActionView.findViewById(R.id.NavRs);
        aum = mfActionView.findViewById(R.id.aum);
        ratingBar = mfActionView.findViewById(R.id.ratingBar);
        getFromIntent();

        int textColor = AccountDetails.getTextColorDropdown();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            folioNumberText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            withDrawalUnitsText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            withDrawalAmtText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            numOfInstallmentText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            endDateText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            subBrokerCodetext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            euinNumberText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            remarksText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            internalrefNoText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            subBrokerarncodeText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //firstorder.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //firstorder.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

//            folioNumberText.setTextColor(getResources().getColor(textColor));
//            withDrawalUnitsText.setTextColor(getResources().getColor(textColor));
//            withDrawalAmtText.setTextColor(getResources().getColor(textColor));
//            numOfInstallmentText.setTextColor(getResources().getColor(textColor));
//            endDateText.setTextColor(getResources().getColor(textColor));
//            remarksText.setTextColor(getResources().getColor(textColor));
//            internalrefNoText.setTextColor(getResources().getColor(textColor));
//            startDate.setTextColor(getResources().getColor(textColor));
//            subBrokerarncodeText.setTextColor(getResources().getColor(textColor));
            //firstorder.setTextColor(getResources().getColor(textColor));
            //firstorder.setHintTextColor(getResources().getColor(textColor));

            transmodeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            frequencyTypeSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
            euinDeclSpinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
        }

        getOverview();

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

        startDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                endDateText.setText("");
                numOfInstallmentText.setText("");
            }
        });

        numOfInstallmentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String swpEndDate = getSipEndDate(startDate.getText().toString(), frequencyTypeSpinner.getSelectedItem().toString(), numOfInstallmentText.getText().toString());
                endDateText.setText(swpEndDate);
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


        //sendOrderRequest();

        Button btnPlaceOrder = mfActionView.findViewById(R.id.btn_place_order);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        ArrayList transactionMode = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.transactionMode)));
        ArrayAdapter<String> transactionModeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), transactionMode);
        transactionModeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        transmodeSpinner.setAdapter(transactionModeAdapter);
        transmodeSpinner.setSelection(0);
        transmodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayList frequencyType = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.frequencyType)));
        ArrayAdapter<String> frequencyTypeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), frequencyType);
        frequencyTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        frequencyTypeSpinner.setAdapter(frequencyTypeAdapter);
        frequencyTypeSpinner.setSelection(0);
        frequencyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayList euinDecl = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.EuinDecl)));
        ArrayAdapter<String> euinDeclAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), euinDecl);
        euinDeclAdapter.setDropDownViewResource(R.layout.custom_spinner);
        euinDeclSpinner.setAdapter(euinDeclAdapter);
        euinDeclSpinner.setSelection(0);
        euinDeclSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return mfActionView;
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

    private void sendOrderRequest() {

        String userName = Util.getPrefs(getMainActivity()).getString("GREEK_RETAINED_CUST_UNAME", "");
        String orderDetails = "getOrderDetailsForSwitchOrders?clientCode=" + userName;
        showProgress();

        WSHandler.getRequest(getMainActivity(), orderDetails, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

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

//
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


    /**
     * this return next month date if the passed day is passed
     *
     * @param day
     * @return
     */
    private Date getDate(String day) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, Integer.parseInt(day));
        Date date = calendar.getTime();

        //day is passed
        if (date.before(today)) {
            calendar.add(Calendar.MONTH, 1);
            date = calendar.getTime();
        }
        return date;
    }

    /**
     * calculate sip end date
     *
     * @param startDateStr
     * @param sipFrequency
     * @return
     */
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
            schemeName.setText(mfSchemeData.getSchemeName());
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

    private boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    private boolean validateSipData(String paymentMode, String sipFrequency, String sipAmount, String sipNoOfInst) {
        if (!termChkBox.isChecked()) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Please confirm the terms and condition", "Ok", true, null);
            return false;
        }
        if (isBlank(paymentMode) || paymentMode.equalsIgnoreCase("PAYMENT MODE")) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Select payment mode", "Ok", true, null);
            return false;
        }


        if (isBlank(sipFrequency) || sipFrequency.equalsIgnoreCase("Select SIP frequency")) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Select SIP frequency", "Ok", true, null);
            return false;
        }

        String sipStartDate = sipDateSpinner.getSelectedItem().toString();
        if (isBlank(sipStartDate) || sipStartDate.equalsIgnoreCase("Select SIP date")) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Select SIP date", "Ok", true, null);
            return false;
        }
        if (isBlank(sipAmount)) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter installment amount", "Ok", true, null);
            return false;
        }
        Double sipAmountDouble = Double.parseDouble(sipAmount);
        if (sipAmountDouble < sipAmountMin || sipAmountDouble > sipAmountMax) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter installments amount between " + sipAmountMin + " and " + sipAmountMax, "Ok", true, null);
            return false;
        }

        if (isBlank(sipNoOfInst)) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter no of installments", "Ok", true, null);
            return false;
        }
        Double noOfInstDouble = Double.parseDouble(sipNoOfInst);
        if (noOfInstDouble < sipNoOfInstMin || noOfInstDouble > sipNoOfInstMax) {
            GreekDialog.alertDialog(getMainActivity(), 0, "Error", "Enter no of installments between " + sipNoOfInstMin + " and " + sipNoOfInstMax, "Ok", true, null);
            return false;
        }
        return true;
    }

    //submit sip data to server
    private void placeOrder() {


        if (transmodeSpinner.getSelectedItemPosition() == 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "please select transaction mode", "OK", false, null);

            return;
        }
        if (frequencyTypeSpinner.getSelectedItemPosition() == 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "please select Frequency type", "OK", false, null);

            return;
        }

        if (withDrawalUnitsText.getText().toString().length() <= 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Enter withdrawal unit", "OK", false, null);
            return;
        }

        if (startDate.getText().toString().length() <= 0) {


            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Enter start date", "OK", false, null);
            return;
        }
        if (!validDate(startDate.getText().toString())) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Enter valid start date", "OK", false, null);
            return;
        }


        if (numOfInstallmentText.getText().toString().length() <= 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Enter number of Installment", "OK", false, null);
            return;
        }

        if (euinDeclSpinner.getSelectedItemPosition() == 0) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "please select EUIN Decl", "OK", false, null);

            return;
        }

        String sessionId = AccountDetails.getToken(getMainActivity());
        String clientCode = mfSchemeData.getMfClientCode();
        String sipFrequency = frequencyTypeSpinner.getSelectedItem().toString();
        String swpNoOfInst = numOfInstallmentText.getText().toString();


        String transactionMode;
        if (transmodeSpinner.getSelectedItem().toString().equalsIgnoreCase("Physical")) {
            transactionMode = "P";
        } else {
            transactionMode = "D";
        }
        String folio = folioNumberText.getText().toString();
        String withdrawalUnit = withDrawalUnitsText.getText().toString();
        String swpAmt = withDrawalAmtText.getText().toString();
        String localIp = getLocalIpAddress();
        String firstOrderToday;
        if (firstorder.isChecked()) {
            firstOrderToday = "Y";

        } else {

            firstOrderToday = "N";
        }
        SipSummaryModel summary = new SipSummaryModel();
        summary.setSchemeName(mfSchemeData.getSchemeName());
        summary.setSchemeIsin(mfSchemeData.getISIN());
        summary.setSchemeCode(mfSchemeData.getBseCode());
        summary.setClientCode(clientCode);
        summary.setSessionId(sessionId);
        summary.setSipFrequency(sipFrequency);
        summary.setWithdrawalsAmt(swpAmt);

        summary.setSipNoOfInst(swpNoOfInst);
        summary.setWithdrawalsUnit(withdrawalUnit);
        summary.setStartDate(startDate.getText().toString());
        summary.setEndDate(endDateText.getText().toString());
        summary.setLocalIp(localIp);
        if (list != null) {
            summary.setInternalRefNumber(list.get(0).getCInternalRefNo());
            summary.setFolioNumber(list.get(0).getCFolioNo());
        }
        summary.setFolioNumber(folio);
        summary.setTransmode(transactionMode);
        summary.setFirstOrderToday(firstOrderToday);
        String euindecl;
        if (euinDeclSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
            euindecl = "Y";
        } else {
            euindecl = "N";
        }
        summary.setEuinDecl(euindecl);


        Bundle bundle = new Bundle();
        bundle.putSerializable("summary", summary);
        bundle.putString("from", "swp");
        navigateTo(NAV_TO_MUTUAL_FUND_SIP_SUMMARY, bundle, true);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUAL_FUND_SWP;
    }

    private boolean validDate(String dtStart) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = format.parse(dtStart);
            System.out.println(date);


            if (System.currentTimeMillis() < date.getTime()) {

                format.parse(dtStart.trim());
                return true;
            } else
                return false;


        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }
}


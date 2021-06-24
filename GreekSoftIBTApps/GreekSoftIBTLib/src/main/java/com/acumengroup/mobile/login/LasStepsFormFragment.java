package com.acumengroup.mobile.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.mobile.GreekBaseActivity;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.las.LASRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.WebContent;
import com.acumengroup.mobile.trade.DatePickerFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;


public class LasStepsFormFragment extends GreekBaseFragment {


    private String mParam2, mParam1;
    private View view;
    private GreekButton btn_detailSubmit, btn_loanSubmit, btn_finalSubmit;
    private GreekTextView txt_msg;
    private StateProgressBar stateProgressBar;
    private LinearLayout details_layout, loan_layout, bank_layout;
    private boolean isFirstCompleted = false, isSecondCompleted = false, isThirdCompleted = false;
    private GreekEditText edt_pinCode, edt_city, edt_add3, edt_state, edt_add2, edt_add1, edt_mobNum, edt_mail, edt_panNum, edt_appDOB, edt_appName;
    private GreekEditText edt_rateInterest, edt_loanAmount, edt_processFee;
    private GreekEditText edt_bankName, edt_ifscCode;
    private CheckBox chk_agree;
    private int mYear, mMonth, mDay;
    private boolean isChk_Agree = false;
    private static final SimpleDateFormat BIRTHDAY_FORMAT_PARSER = new SimpleDateFormat("dd/MM/yyyy");
    private String URL;
    private ServiceResponseHandler serviceResponseHandler;
    private ArrayList<String> bankName_list, bankIfsc_list, bankActNumber_list;
    private Spinner spinner_bankAccNum;
    ArrayAdapter<String> bankNameSpinAdapter;
    ArrayAdapter<String> acctNoSpinAdapter;


    public LasStepsFormFragment() {
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
    }


    public static LasStepsFormFragment newInstance(String param1, String param2) {
        LasStepsFormFragment fragment = new LasStepsFormFragment();
        Bundle args = new Bundle();
        args.putString("", param1);
        args.putString("", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("");
            mParam2 = getArguments().getString("");
        }
        bankName_list = new ArrayList<>();
        bankIfsc_list = new ArrayList<>();
        bankActNumber_list = new ArrayList<>();

    }


    private void setUpView() {

        txt_msg = view.findViewById(R.id.txt_msg);

        btn_detailSubmit = view.findViewById(R.id.btn_next);
        btn_loanSubmit = view.findViewById(R.id.submit_loan);
        btn_finalSubmit = view.findViewById(R.id.btn_submit);

        details_layout = view.findViewById(R.id.details_layout);
        loan_layout = view.findViewById(R.id.loan_layout);
        bank_layout = view.findViewById(R.id.bank_layout);

        edt_pinCode = view.findViewById(R.id.edt_pinCode);
        edt_city = view.findViewById(R.id.edt_city);
        edt_add3 = view.findViewById(R.id.edt_add3);
        edt_state = view.findViewById(R.id.edt_state);
        edt_add2 = view.findViewById(R.id.edt_add2);
        edt_add1 = view.findViewById(R.id.edt_add1);
        edt_mobNum = view.findViewById(R.id.edt_mobNum);
        edt_mail = view.findViewById(R.id.edt_mail);
        edt_panNum = view.findViewById(R.id.edt_panNum);
        edt_appDOB = view.findViewById(R.id.edt_appDOB);
        edt_appName = view.findViewById(R.id.edt_appName);

        edt_rateInterest = view.findViewById(R.id.edt_rateInterest);
        edt_loanAmount = view.findViewById(R.id.edt_loanAmount);
        edt_processFee = view.findViewById(R.id.edt_processFee);
        edt_rateInterest.setEnabled(false);
        edt_processFee.setEnabled(false);
        edt_loanAmount.setEnabled(false);

        spinner_bankAccNum = view.findViewById(R.id.spinner_bankAccNum);


        edt_bankName = view.findViewById(R.id.edt_bankName);
        edt_ifscCode = view.findViewById(R.id.edt_ifscCode);

        chk_agree = view.findViewById(R.id.chk_agree);
        GreekTextView str_agree=(GreekTextView)view.findViewById(R.id.str_agree);
        String strstring=getString(R.string.str_agree);

        String str_messae=strstring.replaceAll("APPNAME", GreekBaseActivity.GREEK);
        str_agree.setText(str_messae);

    }


    private void setupAdapter() {

        if (bankName_list.size() <= 0) {
            txt_msg.setVisibility(View.VISIBLE);

        } else {
            txt_msg.setVisibility(View.GONE);
        }

        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");


        acctNoSpinAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), bankActNumber_list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(getResources().getColor(R.color.white));
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
        acctNoSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner_bankAccNum.setAdapter(acctNoSpinAdapter);

        spinner_bankAccNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                edt_bankName.setText(bankName_list.get(i));
                edt_ifscCode.setText(bankIfsc_list.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_las_steps_form, container, false);
        stateProgressBar = view.findViewById(R.id.steps);

        sendForClientDetails();

//        stateProgressBar.setStateNumberTypeface("fonts/DaxOT.ttf");

        URL = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port()  + "/Loan/LAS-existingCust.aspx?PledgorBOID=" + AccountDetails.getDPID() + "&PledgeeBOID=1208860000000061";

        setUpView();
        ShowLayout(1);

        edt_panNum.setImeOptions(EditorInfo.IME_ACTION_DONE);
        InputFilter[] filters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        edt_panNum.setFilters(filters);
        edt_panNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.v("Length....!!!!!!!!!!", i2 + "   " + edt_panNum.length());
                if (edt_panNum.length() <= 4) {
                    NumberValidation();
                    edt_panNum.setInputType(InputType.TYPE_CLASS_TEXT);

                } else if (edt_panNum.length() > 4 && edt_panNum.length() <= 8) {
                    edt_panNum.setInputType(InputType.TYPE_CLASS_NUMBER);

                } else if (edt_panNum.length() > 8) {
                    // NumberValidation();
                    edt_panNum.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                if (edt_panNum.length() == 10) {

                    if (Character.isDigit(charSequence.charAt(9))) {
                        edt_panNum.setText(edt_panNum.getText().toString().substring(0, edt_panNum.getText().toString().length() - 1));
                        edt_panNum.setSelection(9);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        edt_appDOB.setFocusable(false);
        edt_appDOB.setClickable(true);
        edt_appDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getMainActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                if ((monthOfYear + 1) < 10) {
                                    if (dayOfMonth < 10) {
                                        edt_appDOB.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                    } else {

                                        edt_appDOB.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    }
                                } else {
                                    if (dayOfMonth < 10) {
                                        edt_appDOB.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                    } else {

                                        edt_appDOB.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    }
                                }


                            }
                        }, mYear - 19, mMonth, mDay);

                datePickerDialog.show();


            }
        });

        chk_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                isChk_Agree = b;
            }
        });

        btn_detailSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isValideApplicantDetails()) {

                    isFirstCompleted = true;
                    ShowLayout(2);
                }


            }
        });
        btn_loanSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValideLoanDetails()) {
                    isSecondCompleted = true;
                    ShowLayout(3);
                }


            }
        });
        btn_finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidBankDetails()) {

                    String fisrtName, middleName, lastName, addr1, addr2, addr3, city, state, dob, pan, mobile, email, pin, loanAmount,
                            roi, procFees, bankName, accNo, ifscCode;

                    String currentString = edt_appName.getText().toString();
                    String[] separated = currentString.split(" ");


                    fisrtName = separated[0];
                    middleName = separated[1];
                    lastName = separated[1];

                    addr1 = edt_add1.getText().toString();
                    addr2 = edt_add2.getText().toString();
                    addr3 = edt_add3.getText().toString();
                    city = edt_city.getText().toString();
                    state = edt_state.getText().toString();
                    dob = edt_appDOB.getText().toString();
                    pan = edt_panNum.getText().toString();
                    mobile = edt_mobNum.getText().toString();
                    email = edt_mail.getText().toString();
                    pin = edt_pinCode.getText().toString();
                    loanAmount = edt_loanAmount.getText().toString();
                    roi = edt_rateInterest.getText().toString();
                    procFees = edt_processFee.getText().toString();
                    bankName = edt_bankName.getText().toString();
                    accNo = spinner_bankAccNum.getSelectedItem().toString();
                    ifscCode = edt_ifscCode.getText().toString();

                    showProgress();
                    LASRequest.sendRequestForloanCreationRequest(AccountDetails.getUsername(getMainActivity()), fisrtName, middleName, lastName, addr1, addr2, addr3, city, state, dob, pan, mobile, email, pin, loanAmount, roi, procFees, bankName, accNo, ifscCode,
                            getMainActivity(), serviceResponseHandler);


                }


            }
        });

        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {

                ShowLayout(stateNumber);

            }
        });

        return view;
    }

    private void sendForClientDetails() {
        showProgress();
        LASRequest.sendRequestForClientDetails(AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        Log.e("LasStepsForm", "response====>" + response);
        hideProgress();
        JSONResponse jsonResponse = (JSONResponse) response;

        if (jsonResponse.getServiceName().equalsIgnoreCase("getClientDetailsForLAS")) {

            JSONArray arraydata = null;
            try {
                arraydata = jsonResponse.getResObject().getJSONObject("response").getJSONArray("data");

                for (int i = 0; i < arraydata.length(); i++) {


                    JSONObject jsonObject = arraydata.getJSONObject(i);

                    String cCity = jsonObject.getString("cCity");
                    String cGreekUserOrInvestorName = jsonObject.getString("cGreekUserOrInvestorName");
                    String cPanNo = jsonObject.getString("cPanNo");
                    String lPinCode = jsonObject.getString("lPinCode");
                    String cAddress = jsonObject.getString("cAddress");
                    String lMobileNo = jsonObject.getString("lMobileNo");
                    String bankName = jsonObject.getString("bankName");
                    String ifscCode = jsonObject.getString("ifscCode");
                    String bankAcNo = jsonObject.getString("bankAcNo");
                    String cEmailId = jsonObject.getString("cEmailId");

                    AccountDetails.setcCity(cCity);
                    AccountDetails.setcEmailId(cEmailId);
                    AccountDetails.setcGreekUserOrInvestorName(cGreekUserOrInvestorName);
                    AccountDetails.setcPanNo(cPanNo);
                    AccountDetails.setlPinCode(lPinCode);
                    AccountDetails.setcAddress(cAddress);
                    AccountDetails.setlMobileNo(lMobileNo);

                    edt_city.setText(AccountDetails.getcCity());
                    edt_mail.setText(AccountDetails.getcEmailId());
                    edt_appName.setText(AccountDetails.getcGreekUserOrInvestorName());
                    edt_panNum.setText(AccountDetails.getcPanNo());
                    edt_pinCode.setText(AccountDetails.getlPinCode());
                    edt_add1.setText(AccountDetails.getcAddress());
                    edt_add2.setText(AccountDetails.getcAddress());
                    edt_add3.setText(AccountDetails.getcAddress());
                    edt_mobNum.setText(AccountDetails.getlMobileNo());
                    edt_loanAmount.setText(AccountDetails.getBankamount());


                    bankActNumber_list.add(bankAcNo);
                    bankName_list.add(bankName);
                    bankIfsc_list.add(ifscCode);

                    setupAdapter();


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            try {
                final String errorCode = String.valueOf(jsonResponse.getResObject().getJSONObject("response").get("ErrorCode"));
                String msg = jsonResponse.getResObject().getJSONObject("response").getJSONObject("data").getString("Message");


                ViewGroup viewGroup = getMainActivity().findViewById(android.R.id.content);

                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(getMainActivity()).inflate(R.layout.proceed_layout, viewGroup, false);

                GreekTextView txt_msg = dialogView.findViewById(R.id.txt_msg);
                GreekButton buttonOk = dialogView.findViewById(R.id.buttonOk);

                txt_msg.setText(msg);


                if (errorCode.equalsIgnoreCase("0")) {
                    buttonOk.setText("PROCEED");

                    isThirdCompleted = true;
                    stateProgressBar.setAllStatesCompleted(true);

                } else {
                    buttonOk.setText("OK");
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.cancel();

                        if (errorCode.equalsIgnoreCase("0")) {

                            Intent intent = new Intent(getActivity(), WebContent.class);
                            intent.putExtra("AtomRequest", URL);
                            intent.putExtra("uniqueid", "");
                            intent.putExtra("amt", "");
                            startActivity(intent);


                        }


                        //open URL
                    }
                });

                alertDialog.show();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean isValideApplicantDetails() {


        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

        Matcher matcher = pattern.matcher(edt_panNum.getText().toString());

        if (edt_appName.getText().toString().isEmpty()) {

            edt_appName.setError("Enter Full name");
            edt_appName.setFocusable(true);

            return false;
        } else if (!edt_appName.getText().toString().contains(" ")) {
            edt_appName.setError("Enter name and surname");
            edt_appName.setFocusable(true);
            return false;
        } else if (edt_panNum.getText().toString().isEmpty() || edt_panNum.getText().toString().length() < 10) {
            edt_panNum.setError("Invalid Pan number");
            edt_panNum.setFocusable(true);
            return false;
        } else if (!matcher.matches()) {
            edt_panNum.setError("Invalid Pan number");
            edt_panNum.setFocusable(true);
            return false;
        } else if (edt_appDOB.getText().toString().isEmpty()) {
            edt_appDOB.setError("Select DOB");
            edt_appDOB.setFocusable(true);
            return false;
        }
//        else if (!isValidBirthday(edt_appDOB.getText().toString())) {
////            edt_appDOB.setError("Invalid DOB");
//            edt_appDOB.setFocusable(true);
//            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Invalid Date of Birth", "Ok", false, null);
//
//            return false;
//        }
        else if (getAge(edt_appDOB.getText().toString()) < 18) {
//            edt_appDOB.setError("Invalid DOB");
            edt_appDOB.setFocusable(true);
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Age can not be less than 18 Year", "Ok", false, null);

            return false;
        } else if (edt_mobNum.getText().toString().isEmpty()) {
            edt_mobNum.setError("Enter Mobile number");
            edt_mobNum.setFocusable(true);
            return false;
        } else if (edt_mobNum.getText().toString().length() < 10) {
            edt_mobNum.setError("Invalid Mobile number");
            edt_mobNum.setFocusable(true);
            return false;
        } else if (edt_mail.getText().toString().isEmpty()) {
            edt_mail.setError("Enter E-mailId ");
            edt_mail.setFocusable(true);
            return false;
        } else if (!isValidEmailId(edt_mail.getText().toString())) {
            edt_mail.setError("Invalid E-mailId ");
            edt_mail.setFocusable(true);
            return false;
        } else if (edt_add1.getText().toString().isEmpty()) {
            edt_add1.setError("Enter address");
            edt_add1.setFocusable(true);
            return false;
        } else if (edt_add2.getText().toString().isEmpty()) {
            edt_add2.setError("Enter address");
            edt_add2.setFocusable(true);
            return false;
        } else if (edt_add3.getText().toString().isEmpty()) {
            edt_add3.setError("Enter address");
            edt_add3.setFocusable(true);
            return false;
        } else if (edt_state.getText().toString().isEmpty()) {
            edt_state.setError("Enter state name");
            edt_state.setFocusable(true);
            return false;
        } else if (edt_city.getText().toString().isEmpty()) {
            edt_city.setError("Enter city name");
            edt_city.setFocusable(true);
            return false;
        } else if (edt_pinCode.getText().toString().isEmpty()) {
            edt_pinCode.setError("Enter pincode");
            edt_pinCode.setFocusable(true);
            return false;
        } else if (edt_pinCode.getText().toString().length() < 6) {
            edt_pinCode.setError("Invalid pincode");
            edt_pinCode.setFocusable(true);
            return false;
        }

        return true;
    }

    public boolean isValideLoanDetails() {

        if (edt_loanAmount.getText().toString().isEmpty()) {
            edt_loanAmount.setError("Enter amount");
            return false;
        } else if (edt_rateInterest.getText().toString().isEmpty()) {
            edt_rateInterest.setError("Enter rate interest");
            return false;
        } else if (edt_processFee.getText().toString().isEmpty()) {
            edt_processFee.setError("Enter fees");
            return false;
        }

        return true;
    }

    public boolean isValidBankDetails() {

//        if (edt_accNumber.getText().toString().isEmpty()) {
//            edt_accNumber.setError("Enter account number");
//            return false;
//        } else if (edt_ifscCode.getText().toString().isEmpty()) {
//            edt_ifscCode.setError("Enter ifsc ode");
//            return false;
//        } else if (!isIfscCodeValid(edt_ifscCode.getText().toString())) {
//            edt_ifscCode.setError("Invalid ifsc ode");
//            return false;
//        } else

        if (!isChk_Agree) {

            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Please agree T&C", "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                }
            });
            return false;
        }

        return true;
    }


    public void ShowLayout(int step) {

        if (step == 1) {
            details_layout.setVisibility(View.VISIBLE);
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

            loan_layout.setVisibility(View.GONE);
            bank_layout.setVisibility(View.GONE);

        } else if (step == 2 && isFirstCompleted) {
            loan_layout.setVisibility(View.VISIBLE);
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

            details_layout.setVisibility(View.GONE);
            bank_layout.setVisibility(View.GONE);

        } else if (step == 3 && isSecondCompleted) {
            bank_layout.setVisibility(View.VISIBLE);
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

            loan_layout.setVisibility(View.GONE);
            details_layout.setVisibility(View.GONE);
        }

    }

    public static Calendar parseDateString(String date) {
        Calendar calendar = Calendar.getInstance();
        BIRTHDAY_FORMAT_PARSER.setLenient(false);
        try {
            calendar.setTime(BIRTHDAY_FORMAT_PARSER.parse(date));
        } catch (ParseException e) {
        }
        return calendar;
    }

    public static boolean isValidBirthday(String birthday) {

        Boolean isvaliddate = false;
        Calendar calendar = parseDateString(birthday);

        int month = calendar.get(Calendar.MONTH);

        int year = calendar.get(Calendar.YEAR);

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        isvaliddate = year >= 1900 && year < thisYear && month < 13;
        return isvaliddate;
    }

    private int getAge(String dobString) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }

    private void NumberValidation() {
        if (edt_panNum.getText().toString().matches(".*[0-9].*")) {
            edt_panNum.setText(edt_panNum.getText().toString().substring(0, edt_panNum.getText().toString().length() - 1));

        }
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isIfscCodeValid(String email) {
//        String regExp = "^[A-Z]{4}[0][A-Z0-9]{6}$";
        String regExp = "^[A-Za-z]{4}[0-9]{6,7}$";
        boolean isvalid = false;

        if (email.length() > 0) {
            isvalid = email.matches(regExp);
        }
        return isvalid;
    }

    private void showDatePicker() {
        DatePickerFragment datePickerDialog = new DatePickerFragment();
        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR) - 19);
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
            edt_appDOB.setText(selecteddate);
        }
    };

}

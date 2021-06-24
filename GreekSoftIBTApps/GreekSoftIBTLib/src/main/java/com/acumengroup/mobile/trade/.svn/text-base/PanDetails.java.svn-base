package com.acumengroup.mobile.trade;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.PanValidation.PanRequest;
import com.acumengroup.greekmain.core.model.PanValidation.ValidatePanResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MutualFund.MFundCommunicator;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.login.UserKycValidation;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.greekmain.util.Util;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.acumengroup.mobile.login.UserCreationPanDetails.parseDateString;

public class PanDetails extends GreekBaseFragment implements View.OnClickListener, Step {

    GreekEditText editpan, editdob;
    Button next;
    String panNo;
    String dob;
    String mobile;
    String from = "";
    private ValidatePanResponse panResponse = null;
    private static final String ARG_PARAM2 = "param2";
    private String blockCharacterSet = "~#@^|$%&@><*!-?+=_()";

    public static PanDetails newInstance(Bundle bundle, String param2) {
        PanDetails fragment = new PanDetails();
        Bundle args = bundle;
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_pan_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_pan_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_PAN_DETAILS;


        setupView(mfActionView);
        if (getArguments() != null) {
            from = getArguments().getString("from");
        }
        mobile = Util.getPrefs(getActivity()).getString("Mobile", "0");
        String dob = Util.getPrefs(getActivity()).getString("dob", "");
        String pan = Util.getPrefs(getActivity()).getString("panno", "");

        if (!pan.equals("")) {

            editpan.setText(pan);
            editdob.setText(dob);
        }

        next.setOnClickListener(PanDetails.this);
        editdob.setOnClickListener(PanDetails.this);
        return mfActionView;
    }

    private void setupView(View mfActionView) {
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        editpan = mfActionView.findViewById(R.id.ed_pan);
        editpan.setImeOptions(EditorInfo.IME_ACTION_DONE);
        InputFilter[] filters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        editpan.setFilters(filters);
        editdob = mfActionView.findViewById(R.id.ed_dob);
        next = mfActionView.findViewById(R.id.btn_pan_nxt);


        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };


        editdob.setFilters((new InputFilter[]{filter}));
        editdob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editdob.length() >= 8) {
                    String[] dateParts = editdob.getText().toString().split("/");
                    String month = dateParts[1];
                    String day = dateParts[0];
                    String year = dateParts[2];
                    if (Integer.parseInt(month) > 12) {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Enter valid date", "OK", true, null);
                        editdob.setText("");
                        return;

                    }

                    if (Integer.parseInt(day) > 31) {
                        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Enter valid date", "OK", true, null);
                        editdob.setText("");
                        return;

                    }
                }

            }
        });


        editpan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("Length....!!!!!!!!!!", i + "   " + editpan.length());
                if (editpan.length() <= 4) {
                    if (editpan.getText().toString().matches(".*[0-9].*")) {
                        editpan.setText(editpan.getText().toString().substring(0, editpan.getText().toString().length() - 1));

                    }

                    editpan.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (editpan.length() > 4 && editpan.length() < 8) {
                    editpan.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else if (editpan.length() >= 8) {
                    editpan.setInputType(InputType.TYPE_CLASS_TEXT);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_pan_nxt) {

            MFundCommunicator mFundCommunicator = new MFundCommunicator();
            mFundCommunicator.setName("StepTwo");
            mFundCommunicator.setPosition(1);
            EventBus.getDefault().post(mFundCommunicator);

            Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

            Matcher matcher = pattern.matcher(editpan.getText().toString());


            dob = editdob.getText().toString();

            if (editpan.getText().toString().equals("") || editpan.getText().toString().length() < 10) {
                editpan.setError("Enter valid pan number");
                return;

            } else if (!matcher.matches()) {
                editpan.setError("Invalid Pan Number");
                editpan.requestFocus();

                return;
            } else if (editdob.getText().toString().equals("")) {

                editdob.setError("Enter DOB");
                editdob.requestFocus();
                return;

            } else if (!isValidBirthday(editdob.getText().toString())) {

                editdob.setError("Invalid Date Format");
                editdob.requestFocus();

                return;

            } else {

                panNo = editpan.getText().toString();
                dob = editdob.getText().toString();
                sendRequest();

            }

        }


        if (view.getId() == R.id.ed_dob) {
            showDatePicker();
        }
    }


    public static boolean isValidBirthday(String birthday) {
        Boolean isvaliddate = false;
        Calendar calendar = parseDateString(birthday);

        int month = calendar.get(Calendar.MONTH);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int year = calendar.get(Calendar.YEAR);

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        isvaliddate = year >= 1900 && year < thisYear && month < 13;
        return isvaliddate;
    }

    private void showDatePicker() {

        DatePickerFragment datePickerDialog = new DatePickerFragment();
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
            editdob.setText(selecteddate);

        }
    };


    private void sendRequest() {
        showProgress();
        PanRequest.sendRequest(panNo, dob, mobile, AccountDetails.getcUserType(), getActivity(), serviceResponseHandler);

    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        try {

            panResponse = (ValidatePanResponse) jsonResponse.getResponse();
            String kycFlag = panResponse.getErrorCode();
            Log.v("Service Response Pan ", panResponse.getErrorCode() + " " + panResponse.getGender());
            Bundle args = new Bundle();
            args.putString("userExists", panResponse.getUserExists());
            args.putString("clientCode", panResponse.getClientCode());
            args.putString("from", from);
            if (kycFlag.equals("2")) {
                String name = panResponse.getName();
                String ErrorCode = panResponse.getErrorCode();
                String panno = panResponse.getPANNo();
                String ipvflag = panResponse.getIPVFlag();
                String gender = panResponse.getGender();
                String dob = panResponse.getDob();
                String deltainfo = panResponse.getDeltaInfo();
                String fname = panResponse.getFName();
                String nationality = panResponse.getNationality();
                String resstatus = panResponse.getResStatus();
                String uidno = panResponse.getUIDNo();
                String corraddr1 = panResponse.getCorrAddr1();
                String corraddr2 = panResponse.getCorrAddr2();
                String corraddr3 = panResponse.getCorrAddr3();
                String corrcity = panResponse.getCorrCity();
                String corrpin = panResponse.getCorrPin();
                String corrstate = panResponse.getcClientState();
                String corrcountry = panResponse.getCorrCntry();
                String offno = panResponse.getOffNo();
                String resno = panResponse.getResNo();
                String faxno = panResponse.getFaxNo();
                String email = panResponse.getEmail();
                String peraddr1 = panResponse.getPerAddr1();
                String peradd2 = panResponse.getPerAddr2();
                String peradd3 = panResponse.getCorrAddr3();
                String percity = panResponse.getPerCity();
                String perpin = panResponse.getPerPin();
                String perstate = panResponse.getcForState();
                String percountry = panResponse.getPerCntry();
                String corraddressref = panResponse.getCorrAddrRef();
                String peraddref = panResponse.getPerAddrRef();
                String mob = panResponse.getMobNo();
                String client_code = panResponse.getClientCode();

                SharedPreferences.Editor editor = Util.getPrefs(getActivity()).edit();
                editor.putString("name", name);
                editor.putString("errorcode", ErrorCode);
                editor.putString("panno", panno);
                editor.putString("ipvflag", ipvflag);
                editor.putString("spinner_gender", gender);
                editor.putString("dob", dob);
                editor.putString("deltainfo", deltainfo);
                editor.putString("fname", fname);
                editor.putString("nationality", nationality);
                editor.putString("resstatus", resstatus);
                editor.putString("uidno", uidno);
                editor.putString("corraddr1", corraddr1);
                editor.putString("corraddr2", corraddr2);
                editor.putString("corraddr3", corraddr3);
                editor.putString("corrcity", corrcity);
                editor.putString("corrpin", corrpin);
                editor.putString("corrstate", corrstate);
                editor.putString("corrcountry", corrcountry);
                editor.putString("offno", offno);
                editor.putString("resno", resno);
                editor.putString("faxno", faxno);
                editor.putString("email", email);
                editor.putString("peraddr1", peraddr1);
                editor.putString("peradd2", peradd2);
                editor.putString("peradd3", peradd3);
                editor.putString("percity", percity);
                editor.putString("perpin", perpin);
                editor.putString("perstate", perstate);
                editor.putString("percountry", percountry);
                editor.putString("corraddressref", corraddressref);
                editor.putString("peraddref", peraddref);
                editor.putString("mob", mob);
                editor.putString("clientcode", client_code);


                editor.putString("validate", "yes");

                editor.commit();

            } else {

                String name = "";
                String ErrorCode = "";
                String panno = panResponse.getPANNo();
                String ipvflag = "";
                String gender = "";
                String dob = panResponse.getDob();
                String deltainfo = "";
                String fname = "";
                String nationality = "";
                String resstatus = "";
                String uidno = "";
                String corraddr1 = "";
                String corraddr2 = "";
                String corraddr3 = "";
                String corrcity = "";
                String corrpin = "";
                String corrstate = "";
                String corrcountry = "";
                String offno = "";
                String resno = "";
                String faxno = "";
                String email = "";
                String peraddr1 = "";
                String peradd2 = "";
                String peradd3 = "";
                String percity = "";
                String perpin = "";
                String perstate = "";
                String percountry = "";
                String corraddressref = "";
                String peraddref = "";
                String mob = "";
                String client_code = panResponse.getClientCode();
                Log.e("getConnectTo client", client_code);


                SharedPreferences.Editor editor = Util.getPrefs(getActivity()).edit();
                editor.putString("name", name);
                editor.putString("errorcode", ErrorCode);
                editor.putString("panno", panno);
                editor.putString("ipvflag", ipvflag);
                editor.putString("spinner_gender", gender);
                editor.putString("dob", dob);
                editor.putString("deltainfo", deltainfo);
                editor.putString("fname", fname);
                editor.putString("nationality", nationality);
                editor.putString("resstatus", resstatus);
                editor.putString("uidno", uidno);
                editor.putString("corraddr1", corraddr1);
                editor.putString("corraddr2", corraddr2);
                editor.putString("corraddr3", corraddr3);
                editor.putString("corrcity", corrcity);
                editor.putString("corrpin", corrpin);
                editor.putString("corrstate", corrstate);
                editor.putString("corrcountry", corrcountry);
                editor.putString("offno", offno);
                editor.putString("resno", resno);
                editor.putString("faxno", faxno);
                editor.putString("email", email);
                editor.putString("peraddr1", peraddr1);
                editor.putString("peradd2", peradd2);
                editor.putString("peradd3", peradd3);
                editor.putString("percity", percity);
                editor.putString("perpin", perpin);
                editor.putString("perstate", perstate);
                editor.putString("percountry", percountry);
                editor.putString("corraddressref", corraddressref);
                editor.putString("peraddref", peraddref);
                editor.putString("mob", mob);
                editor.putString("clientcode", client_code);


                editor.putString("validate", "yes");

                editor.commit();


            }

            if (from.equalsIgnoreCase("openAcct")) {
                Fragment personalDetail = new MyAccountPersonalDetails();
                personalDetail.setArguments(args);
                ((UserKycValidation) getMainActivity()).replaceFragment(personalDetail);
            } else {
                navigateTo(NAV_TO_MUTUALFUND_PERSONAL_DETAILS, args, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        EventBus.getDefault().unregister(this);
    }
}

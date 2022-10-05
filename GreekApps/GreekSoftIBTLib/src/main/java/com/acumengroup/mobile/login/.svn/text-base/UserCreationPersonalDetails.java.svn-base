package com.acumengroup.mobile.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.PersonaklDetail.PersonalDetailRequest;
import com.acumengroup.greekmain.core.model.PersonaklDetail.PersonalDetailResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.StateCountryRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.trade.DatePickerFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.greekmain.util.Util;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.acumengroup.mobile.login.UserCreationPanDetails.parseDateString;
import static com.acumengroup.mobile.login.VerhoeffAlgorithm.validateVerhoeff;


public class UserCreationPersonalDetails extends GreekBaseFragment implements View.OnClickListener, Step, BlockingStep {

    //Overriden method onCreateView
    private GreekEditText editname, editDob, editmobile, editemail, editpan, edit_income, editcaddr1, editcaddr2, editcaddr3, edtxt_country, edtxt_per_country, editcpin, editcresiphone, editcforaddr1, editcforaddr2, editcforaddr3, editcforpincode, editcforresiphone, editnationality, editaadhar, editgaurdianName;
    private Spinner gender, maritialstatus, occupation;
    private Button submit_Btn;
    private CheckBox adresscheck;
    private LinearLayout layout;
    private List<String> genderlist, maritiallist, occupationlist;
    private ArrayAdapter<String> genderAdapter, maritalAdapter, occupationAdapter;
    private PersonalDetailResponse personalResponse = null;
    private String clientCode = "";
    private String from = "";
    private Map<String, String> countryCode;
    private AutoCompleteTextView edit_State, edit_per_City, edit_per_State, edit_city;
    private String my_State = null, my_per_State = null;

    private ArrayAdapter<String> state_adapter, country_adapter;
    private String[] state_array, countries_array;
    private boolean nextScreen_flag = false;
    private StepperLayout.OnNextClickedCallback onNextCallback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        attachLayout(R.layout.fragment_personal_usercreation);

        setupView(mfActionView);

        if (getArguments() != null) {
            clientCode = getArguments().getString("clientCode");
            from = getArguments().getString("from");
        }

        genderlist = new ArrayList<>();
        genderlist.add("Select gender");
        genderlist.add("Male");
        genderlist.add("Female");
        genderAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, genderlist);
        genderAdapter.setDropDownViewResource(R.layout.custom_spinner);
        gender.setAdapter(genderAdapter);

        maritiallist = new ArrayList<>();
        maritiallist.add("Select marital status");
        maritiallist.add("Married");
        maritiallist.add("Single");
        maritalAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, maritiallist);
        maritalAdapter.setDropDownViewResource(R.layout.custom_spinner);
        maritialstatus.setAdapter(maritalAdapter);

        occupationlist = new ArrayList<>();
        occupationlist.add("Select Occupation");
        occupationlist.add("Business");
        occupationlist.add("Service");
        occupationlist.add("Professional");
        occupationlist.add("Agriculture");
        occupationlist.add("Retired");
        occupationlist.add("Housewife");
        occupationlist.add("Student");
        occupationlist.add("Others");
        occupationAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.row_spinner_mutualfund, occupationlist);
        occupationAdapter.setDropDownViewResource(R.layout.custom_spinner);
        occupation.setAdapter(occupationAdapter);


        submit_Btn.setOnClickListener(UserCreationPersonalDetails.this);
        editDob.setOnClickListener(UserCreationPersonalDetails.this);

        adresscheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    layout.setVisibility(View.GONE);
                } else {

                    layout.setVisibility(View.VISIBLE);
                }

            }
        });

        StateCountryRequest.sendRequest(getContext(), serviceResponseHandler);


        return mfActionView;
    }


    private void setupView(View mfActionView) {

        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);

        submit_Btn = mfActionView.findViewById(R.id.btn_personal_next);
        editname = mfActionView.findViewById(R.id.ed_name);
        editDob = mfActionView.findViewById(R.id.ed_dob);
        editmobile = mfActionView.findViewById(R.id.ed_mobile);
        editgaurdianName = mfActionView.findViewById(R.id.ed_cclient_gaurdian);
        maritialstatus = mfActionView.findViewById(R.id.spnr_maritial_status);
        editpan = mfActionView.findViewById(R.id.ed_pan);
        edit_income = mfActionView.findViewById(R.id.ed_income);
        InputFilter[] panfilters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        editpan.setFilters(panfilters);
        gender = mfActionView.findViewById(R.id.spnr_gender);
        occupation = mfActionView.findViewById(R.id.spnr_occupation);
        layout = mfActionView.findViewById(R.id.layout_corp_address);
        editemail = mfActionView.findViewById(R.id.ed_email);
        editnationality = mfActionView.findViewById(R.id.ed_nationality);
        editaadhar = mfActionView.findViewById(R.id.ed_aadhar_number);

        //for corraddress
        editcaddr1 = mfActionView.findViewById(R.id.ed_add1);
        editcaddr2 = mfActionView.findViewById(R.id.ed_add2);
        editcaddr3 = mfActionView.findViewById(R.id.ed_add3);
        edtxt_country = mfActionView.findViewById(R.id.ed_country);
        edit_State = mfActionView.findViewById(R.id.ed_state);
        edit_State.setValidator(new Validator());
        edit_State.setOnFocusChangeListener(new FocusListener());
        edit_city = mfActionView.findViewById(R.id.ed_city);
        editcpin = mfActionView.findViewById(R.id.ed_pincode);
        editcresiphone = mfActionView.findViewById(R.id.ed_resi_phone);

        adresscheck = mfActionView.findViewById(R.id.chk_same_address);

        //For perment
        editcforaddr1 = mfActionView.findViewById(R.id.ed_addr1);
        editcforaddr2 = mfActionView.findViewById(R.id.ed_addr2);
        editcforaddr3 = mfActionView.findViewById(R.id.ed_addr3);
        edit_per_City = mfActionView.findViewById(R.id.ed_per_city);
        edit_per_State = mfActionView.findViewById(R.id.ed_per_state);
        edit_per_State.setValidator(new Validator());
        edit_per_State.setOnFocusChangeListener(new FocusListenerper());
        edtxt_per_country = mfActionView.findViewById(R.id.ed_per_country);
        editcforpincode = mfActionView.findViewById(R.id.ed_per_pincode);
        editcforresiphone = mfActionView.findViewById(R.id.ed_resi_per_phone);

        String submit = Util.getPrefs(getActivity()).getString("personalDetailSubmit", "");


        if (!submit.equalsIgnoreCase("yes")) {
            submit_Btn.setClickable(true);
            submit_Btn.setEnabled(true);

            gender.setEnabled(true);
            maritialstatus.setEnabled(true);
            occupation.setEnabled(true);
            adresscheck.setEnabled(true);

            editname.setEnabled(true);
            editDob.setEnabled(true);
            editmobile.setEnabled(true);
            editemail.setEnabled(true);
            editpan.setEnabled(true);
            edit_income.setEnabled(true);
            editcaddr1.setEnabled(true);
            editcaddr2.setEnabled(true);
            editcaddr3.setEnabled(true);
            editcpin.setEnabled(true);
            editcresiphone.setEnabled(true);
            editcforaddr1.setEnabled(true);
            editcforaddr2.setEnabled(true);
            editcforaddr3.setEnabled(true);
            editcforpincode.setEnabled(true);
            editcforresiphone.setEnabled(true);
            editnationality.setEnabled(true);
            editaadhar.setEnabled(true);
            editgaurdianName.setEnabled(true);
            edit_per_City.setEnabled(true);
            edit_city.setEnabled(true);
            edit_State.setEnabled(true);
            edit_per_State.setEnabled(true);


        } else {

            submit_Btn.setClickable(false);
            submit_Btn.setEnabled(false);

            gender.setEnabled(false);
            maritialstatus.setEnabled(false);
            occupation.setEnabled(false);
            adresscheck.setEnabled(false);

            editname.setEnabled(false);
            editDob.setEnabled(false);
            editmobile.setEnabled(false);
            editemail.setEnabled(false);
            editpan.setEnabled(false);
            edit_income.setEnabled(false);
            editcaddr1.setEnabled(false);
            editcaddr2.setEnabled(false);
            editcaddr3.setEnabled(false);
            editcpin.setEnabled(false);
            editcresiphone.setEnabled(false);
            editcforaddr1.setEnabled(false);
            editcforaddr2.setEnabled(false);
            editcforaddr3.setEnabled(false);
            editcforpincode.setEnabled(false);
            editcforresiphone.setEnabled(false);
            editnationality.setEnabled(false);
            editaadhar.setEnabled(false);
            editgaurdianName.setEnabled(false);
            edit_per_City.setEnabled(false);
            edit_city.setEnabled(false);
            edit_State.setEnabled(false);
            edit_per_State.setEnabled(false);
            edtxt_country.setEnabled(false);
            edtxt_per_country.setEnabled(false);

        }


        InputFilter[] nationalityfilters = {new InputFilter.LengthFilter(50), new InputFilter.AllCaps()};
        editnationality.setFilters(nationalityfilters);

        edit_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                my_State = state_adapter.getItem(i);
            }
        });

        edit_State.setThreshold(1);
        edit_city.setThreshold(1);


        edit_per_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                my_per_State = state_adapter.getItem(i);
            }
        });

        edit_per_City.setThreshold(1);
        edit_per_State.setThreshold(1);

        edtxt_country.setText("India");
        edtxt_per_country.setText("India");
        edtxt_country.setEnabled(false);
        edtxt_per_country.setEnabled(false);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_personal_next) {

            SubmitUserDetails();
        } else if (view.getId() == R.id.ed_dob) {

            showDatePicker();
        }

    }

    public void SubmitUserDetails() {
        String cgender, cforad1, cforad2, cforad3, cforccity, cforcstate, cforcpin, cforccountry, cforcresiphone, stateName = "", nation = "";

        if (!isValidBirthday(editDob.getText().toString())) {
            editDob.setError("Invalid Date Format");
            editDob.requestFocus();
        }
        if (TextUtils.isEmpty(editname.getText().toString())) {
            editname.setError("Enter Name");
            editname.requestFocus();

        } else if (TextUtils.isEmpty(editDob.getText().toString())) {
            editDob.setError("Enter Date Of Birth");
            editDob.requestFocus();

        } else if (TextUtils.isEmpty(editgaurdianName.getText().toString())) {
            editgaurdianName.setError("Enter Guardian Name");
            editgaurdianName.requestFocus();

        } else if (TextUtils.isEmpty(editemail.getText().toString()) || !Patterns.EMAIL_ADDRESS.matcher(editemail.getText().toString()).matches()) {
            editemail.setError("Enter Email");
            editemail.requestFocus();

        } else if (TextUtils.isEmpty(editmobile.getText().toString()) || editmobile.getText().toString().length() < 10) {
            editmobile.setError("Enter 10 Digit Mobile Number");
            editmobile.requestFocus();

        } else if (occupation.getSelectedItemPosition() == 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please Select Occupation ", "OK", true, null);
            return;
        } else if (TextUtils.isEmpty(edit_income.getText().toString())) {
            edit_income.setError("Enter Annual Income");
            edit_income.requestFocus();

        } else if (TextUtils.isEmpty(editcaddr1.getText().toString())) {
            editcaddr1.setError("Enter Address");
            editcaddr1.requestFocus();

        } else if (TextUtils.isEmpty(editcaddr2.getText().toString())) {
            editcaddr2.setError("Enter Address");
            editcaddr2.requestFocus();

        } else if (TextUtils.isEmpty(editcaddr3.getText().toString())) {
            editcaddr3.setError("Enter Address");
            editcaddr3.requestFocus();

        } else if (TextUtils.isEmpty(edtxt_country.getText().toString())) {
            edtxt_country.setError("Enter Country Name");
            edtxt_country.requestFocus();

        } else if (TextUtils.isEmpty(edit_State.getText().toString())) {
            edit_State.setError("Enter state name");
            edit_State.requestFocus();
        } else if (!Arrays.asList(state_array).contains(my_State)) {
            edit_State.setError("Select available state name");
            edit_State.requestFocus();
        } else if (TextUtils.isEmpty(edit_city.getText().toString())) {
            edit_city.setError("Enter City Name");
            edit_city.requestFocus();
        } else if (TextUtils.isEmpty(editcpin.getText().toString()) || editcpin.getText().toString().length() < 6) {
            editcpin.setError("Enter PinCode");
            editcpin.requestFocus();

        } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforaddr1.getText().toString())) {
            editcforaddr1.setError("Enter Address");
            editcforaddr1.requestFocus();

        } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforaddr2.getText().toString())) {
            editcforaddr2.setError("Enter Address");
            editcforaddr2.requestFocus();

        } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforaddr3.getText().toString())) {
            editcforaddr3.setError("Enter Address");
            editcforaddr3.requestFocus();

        } else if (!adresscheck.isChecked() && TextUtils.isEmpty(edtxt_per_country.getText().toString())) {
            edtxt_per_country.setError("Enter Country Name");
            edtxt_per_country.requestFocus();

        } else if (!adresscheck.isChecked() && TextUtils.isEmpty(edit_per_State.getText().toString())) {
            edit_per_State.setError("Enter State Name");
            edit_per_State.requestFocus();

        } else if (!adresscheck.isChecked() && !Arrays.asList(state_array).contains(my_per_State)) {

            edit_per_State.setError("Select available state name");
            edit_per_State.requestFocus();

        } else if (!adresscheck.isChecked() && TextUtils.isEmpty(edit_per_City.getText().toString())) {
            edit_per_City.setError("Enter City Name");
            edit_per_City.requestFocus();

        } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforpincode.getText().toString())) {
            editcforpincode.setError("Enter PinCode");
            editcforpincode.requestFocus();

        } else if (TextUtils.isEmpty(editnationality.getText().toString())) {
            editnationality.setError("Enter Nationality");
            editnationality.requestFocus();

        } else if (TextUtils.isEmpty(editaadhar.getText().toString())) {
            editaadhar.setError("Enter Aadhar Number");
            editaadhar.requestFocus();

        } else if (!validateAadharNumber(editaadhar.getText().toString())) {
            editaadhar.setError("Enter Valid Aadhar Number");
            editaadhar.requestFocus();

        } else if (gender.getSelectedItemPosition() == 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please select gender ", "OK", true, null);
            return;
        } else if (maritialstatus.getSelectedItemPosition() == 0) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Please select marital status ", "OK", true, null);
            return;
        } else {

            if (gender.getSelectedItem().toString().equals("Male")) {
                cgender = "M";
            } else {
                cgender = "F";
            }

            if (adresscheck.isChecked()) {

                cforad1 = editcaddr1.getText().toString();
                cforad2 = editcaddr2.getText().toString();
                cforad3 = editcaddr3.getText().toString();
                cforccity = edit_city.getText().toString();
                cforcstate = edit_State.getText().toString();
                cforccountry = edtxt_country.getText().toString();
                cforcpin = editcpin.getText().toString();
                cforcresiphone = editcresiphone.getText().toString();

            } else {

                cforad1 = editcforaddr1.getText().toString();
                cforad2 = editcforaddr2.getText().toString();
                cforad3 = editcforaddr3.getText().toString();
                cforccity = edit_per_City.getText().toString();
                cforcstate = edit_per_State.getText().toString();
                cforccountry = edtxt_per_country.getText().toString();
                cforcpin = editcforpincode.getText().toString();
                cforcresiphone = editcforresiphone.getText().toString();

            }

            String name = editname.getText().toString();
            String gaurdian = editgaurdianName.getText().toString();
            String dob = editDob.getText().toString();
            String mobile = editmobile.getText().toString();
            String marital_status = maritialstatus.getSelectedItem().toString();
            String pan = editpan.getText().toString();
            String income = edit_income.getText().toString();
            String occption = "0" + (occupation.getSelectedItemPosition());


            String adhar = editaadhar.getText().toString();
            String email = editemail.getText().toString();

            String caddr1 = editcaddr1.getText().toString();
            String caddr2 = editcaddr2.getText().toString();
            String caddr3 = editcaddr3.getText().toString();
            String ccity = edit_city.getText().toString();
            String cstate = edit_State.getText().toString();
            String ccountry = edtxt_country.getText().toString();
            String cpin = editcpin.getText().toString();
            String cresiphone = editcresiphone.getText().toString();


            String clientcode = "";

            if (editnationality.getText().toString().equalsIgnoreCase("INDIAN")) {
                nation = "01";
            } else {

                nation = "02";
            }

            showProgress();

            PersonalDetailRequest.sendRequest("add", AccountDetails.getcUserType(),
                    name, dob, gaurdian, mobile, marital_status, cgender, pan, income,
                    caddr1, caddr2, caddr3, ccity, cstate, cpin, ccountry, cresiphone,
                    cforad1, cforad2, cforad3, cforccity, cforcstate, cforcpin, cforccountry, cforcresiphone,
                    occption, clientcode, email, nation, adhar, getActivity(), serviceResponseHandler);


            SharedPreferences.Editor editor = Util.getPrefs(getActivity()).edit();
            editor.putString("personalDetailSubmit", "yes");
            editor.putString("name", name);
            editor.putString("gender", cgender);
            editor.putString("nationality", editnationality.getText().toString());
            editor.putString("corraddr1", editcaddr1.getText().toString());
            editor.putString("corraddr2", editcaddr2.getText().toString());
            editor.putString("corraddr3", editcaddr3.getText().toString());
            editor.putString("corrcity", edit_city.getText().toString());
            editor.putString("corrpin", editcpin.getText().toString());
            editor.putString("corrstate", edit_State.getText().toString());
            editor.putString("corrcountry", edtxt_country.getText().toString());
            editor.putString("resno", editcresiphone.getText().toString());
            editor.putString("email", email);
            editor.putString("income", edit_income.getText().toString());
            editor.putString("peraddr1", editcforaddr1.getText().toString());
            editor.putString("peradd2", editcforaddr2.getText().toString());
            editor.putString("peradd3", editcforaddr3.getText().toString());
            editor.putString("percity", edit_per_City.getText().toString());
            editor.putString("perpin", editcforpincode.getText().toString());
            editor.putString("perstate", edit_per_State.getText().toString());
            editor.putString("percountry", edtxt_per_country.getText().toString());
            editor.putString("mob", editmobile.getText().toString());
            editor.putString("gname", editgaurdianName.getText().toString());
            editor.putString("perphone", cforcresiphone);
            editor.putString("AdharNo", editaadhar.getText().toString());
            editor.putString("MaritalStatus", maritialstatus.getSelectedItem().toString());
            editor.putString("Occupation", occupation.getSelectedItemPosition() + "");
            editor.apply();
            editor.commit();

            Bundle args = new Bundle();
            args.putString("clientCode", clientCode);
            args.putString("from", from);

        }

    }


    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String dd, mm;
            if (day < 10) {
                dd = "0" + day;
            } else {
                dd = String.valueOf(day);
            }
            if (month < 9) {
                int m = month + 1;
                mm = "0" + m;
            } else {
                mm = String.valueOf(month + 1);
            }

            String selecteddate = dd + "/" + mm + "/" + year;
            editDob.setText(selecteddate);
        }
    };


    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        if (STATE_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && STATE_SVC_GROUP.equals(jsonResponse.getServiceName())) {


            try {
                JSONObject jsonObject = ((JSONResponse) response).getResObject();
                JSONObject jsonObject_response = jsonObject.getJSONObject("response");
                JSONObject jsonObject2_data = jsonObject_response.getJSONObject("data");

                JSONArray jsonArray_country = jsonObject2_data.getJSONArray("countryMaster");
                JSONArray jsonArray_state = jsonObject2_data.getJSONArray("stateMaster");

                countries_array = new String[jsonArray_country.length()];

                for (int i = 0; i < jsonArray_country.length(); i++) {

                    countries_array[i] = jsonArray_country.getString(i);

                }

                country_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countries_array);


                state_array = new String[jsonArray_state.length()];

                for (int i = 0; i < jsonArray_state.length(); i++) {
                    state_array[i] = jsonArray_state.getString(i);
                }


                state_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, state_array);
                edit_State.setAdapter(state_adapter);

                edit_per_State.setAdapter(state_adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {

            hideProgress();

            if (MF_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && KYC_SVC_NAME.equals(jsonResponse.getServiceName())) {
                try {
                    personalResponse = (PersonalDetailResponse) jsonResponse.getResponse();
                    String errorcode = personalResponse.getErrorCode();


                    if (errorcode.equalsIgnoreCase("0") || errorcode.equalsIgnoreCase("3")) {
                        nextScreen_flag = true;

                        String usertype = AccountDetails.getcUserType();

                        if (usertype.equalsIgnoreCase("0")) {
                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, personalResponse.getStatus(), "Ok", true, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                    if (action == GreekDialog.Action.OK) {

                                        Intent i = new Intent(getMainActivity(), LoginActivity.class);
                                        startActivity(i);
                                        getMainActivity().finish();

                                    }
                                }
                            });
                        } else {
                            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Personal Details Updated Successfully", "Ok", true, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (onNextCallback != null) {
                                        onNextClicked(onNextCallback);
                                    }
                                }

                            });
                        }


                        submit_Btn.setClickable(false);
                        submit_Btn.setEnabled(false);
                        submit_Btn.setBackgroundResource(R.color.grey_text);


                        gender.setEnabled(false);
                        maritialstatus.setEnabled(false);
                        occupation.setEnabled(false);
                        adresscheck.setEnabled(false);

                        editname.setEnabled(false);
                        editDob.setEnabled(false);
                        editmobile.setEnabled(false);
                        editemail.setEnabled(false);
                        editpan.setEnabled(false);
                        edit_income.setEnabled(false);
                        editcaddr1.setEnabled(false);
                        editcaddr2.setEnabled(false);
                        editcaddr3.setEnabled(false);
                        editcpin.setEnabled(false);
                        editcresiphone.setEnabled(false);
                        editcforaddr1.setEnabled(false);
                        editcforaddr2.setEnabled(false);
                        editcforaddr3.setEnabled(false);
                        editcforpincode.setEnabled(false);
                        editcforresiphone.setEnabled(false);
                        editnationality.setEnabled(false);
                        editaadhar.setEnabled(false);
                        editgaurdianName.setEnabled(false);
                        edit_per_City.setEnabled(false);
                        edit_city.setEnabled(false);
                        edit_State.setEnabled(false);
                        edit_per_State.setEnabled(false);
                        edtxt_country.setEnabled(false);
                        edtxt_per_country.setEnabled(false);


                    }

                } catch (Exception e) {


                }
            }

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
        Log.e("first time call ", "on start " + nextScreen_flag);
//        Log.e("DATAMANAGER ", dataManager.getData());

        String submit = Util.getPrefs(getActivity()).getString("personalDetailSubmit", "");

        if (!submit.equalsIgnoreCase("yes")) {
            submit_Btn.setClickable(true);
            submit_Btn.setEnabled(true);
        } else {
            submit_Btn.setClickable(false);
            submit_Btn.setEnabled(false);
        }

        String name = Util.getPrefs(getActivity()).getString("name", "");
        String dob = Util.getPrefs(getActivity()).getString("dob", "");
        String cl_gender = Util.getPrefs(getActivity()).getString("gender", "");
        String pan = Util.getPrefs(getActivity()).getString("panno", "");
        String mobile = Util.getPrefs(getActivity()).getString("Mobile", "");
        String corradress1 = Util.getPrefs(getActivity()).getString("corraddr1", "");
        String corradress2 = Util.getPrefs(getActivity()).getString("corraddr2", "");
        String corradress3 = Util.getPrefs(getActivity()).getString("corraddr3", "");
        String corrcity = Util.getPrefs(getActivity()).getString("corrcity", "");
        String corrpin = Util.getPrefs(getActivity()).getString("corrpin", "");
        String corrstate = Util.getPrefs(getActivity()).getString("corrstate", "");
        String corrcountry = Util.getPrefs(getActivity()).getString("corrcountry", "");
        String offno = Util.getPrefs(getActivity()).getString("offno", "");
        String resno = Util.getPrefs(getActivity()).getString("resno", "");
        String faxno = Util.getPrefs(getActivity()).getString("faxno", "");
        String email = Util.getPrefs(getActivity()).getString("personalemail", "");
        String income = Util.getPrefs(getActivity()).getString("income", "");
        String peraddr1 = Util.getPrefs(getActivity()).getString("peraddr1", "");
        String peradd2 = Util.getPrefs(getActivity()).getString("peradd2", "");
        String peradd3 = Util.getPrefs(getActivity()).getString("peradd3", "");
        String percity = Util.getPrefs(getActivity()).getString("percity", "");
        String perpin = Util.getPrefs(getActivity()).getString("perpin", "");
        String perstate = Util.getPrefs(getActivity()).getString("perstate", "");
        String percountry = Util.getPrefs(getActivity()).getString("percountry", "");
        String corraddressref = Util.getPrefs(getActivity()).getString("corraddressref", "");
        String peraddref = Util.getPrefs(getActivity()).getString("peraddref", "");
        String nationality = Util.getPrefs(getActivity()).getString("nationality", "");
        String Gaurdian_name = Util.getPrefs(getActivity()).getString("gname", "");
        String perphone = Util.getPrefs(getActivity()).getString("perphone", "");
        String AdharNo = Util.getPrefs(getActivity()).getString("AdharNo", "");
        String MaritalStatus = Util.getPrefs(getActivity()).getString("MaritalStatus", "");
        String Occupation = Util.getPrefs(getActivity()).getString("Occupation", "");
        String guardian_name = Util.getPrefs(getActivity()).getString("gaurdian", "");


        editcresiphone.setText(resno);

        if (!name.equalsIgnoreCase("")) {

            editname.setText(name);
            editname.setClickable(false);
            editname.setEnabled(false);
        } else {

            editname.setText(name);
            editname.setClickable(true);
            editname.setEnabled(true);
        }

        String result = dob.replaceAll("[|?*<\":>+\\[\\]\']", "");
        if (result != null && !result.equalsIgnoreCase("")) {
            editDob.setText(result.replaceAll("\\'", "'"));
            editDob.setClickable(false);
            editDob.setEnabled(false);
        } else {
            editDob.setClickable(true);
            editDob.setEnabled(true);
        }
        String panNo = pan.replaceAll("[|?*<\":>+\\[\\]']", "");
        if (!panNo.equalsIgnoreCase("")) {
            editpan.setText(panNo);
            editpan.setEnabled(false);
        } else {
            editpan.setText(panNo);
            editpan.setEnabled(true);
        }
        String mob = mobile.replaceAll("[|?*<\":>+\\[\\]']", "");
        mob = mob.replace("\\", "");
        editmobile.setText(mob);
        editmobile.setEnabled(false);

        String gen = cl_gender.replaceAll("[|?*<\":>+\\[\\]']", "");

        if (gen.equals("M")) {
            gender.setSelection(1);
        } else if (gen.equals("F")) {
            gender.setSelection(2);

        } else {
            gender.setSelection(0);

        }
        String cemail = email.replaceAll("[|?*<\":>+\\[\\]']", "");
        editemail.setText(cemail);
        editemail.setEnabled(false);

        String cincome = income.replaceAll("[|?*<\":>+\\[\\]']", "");
        edit_income.setText(cincome);

        String cadd1 = corradress1.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcaddr1.setText(cadd1);
        String cadd2 = corradress2.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcaddr2.setText(cadd2);
        String cadd3 = corradress3.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcaddr3.setText(cadd3);
        String ccity = corrcity.replaceAll("[|?*<\":>+\\[\\]']", "");
        edit_city.setText(ccity);
        String cpin = corrpin.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcpin.setText(cpin);

        edit_State.setText(corrstate);


        String gaurdian = Gaurdian_name.replaceAll("[|?*<\":>+\\[\\]']", "");
        editgaurdianName.setText(gaurdian);


        String corrcntry = corrcountry.replaceAll("[|?*<\":>+\\[\\]']", "");

        String peradd1 = peraddr1.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcforaddr1.setText(peradd1);
        String peraddr2 = peradd2.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcforaddr2.setText(peraddr2);
        String peraddr3 = peradd3.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcforaddr3.setText(peraddr3);
        String pcity = percity.replaceAll("[|?*<\":>+\\[\\]']", "");
        edit_per_City.setText(pcity);

        edit_per_State.setText(perstate);


        String percntry = percountry.replaceAll("[|?*<\":>+\\[\\]']", "");
        String ppin = perpin.replaceAll("[|?*<\":>+\\[\\]']", "");
        editcforpincode.setText(ppin);
        String natoinal = nationality.replaceAll("[|?*<\":>+\\[\\]']", "");

        if (natoinal.equalsIgnoreCase("01") || natoinal.equalsIgnoreCase("Indian")) {
            editnationality.setText("INDIAN");
        } else if (natoinal.equalsIgnoreCase("")) {
            editnationality.setText("");
        } else {
            editnationality.setText("OTHERS");

        }

        editcforresiphone.setText(perphone.replaceAll("[|?*<\":>+\\[\\]']", ""));
        editaadhar.setText(AdharNo.replaceAll("[|?*<\":>+\\[\\]']", ""));
        String m_status = MaritalStatus.replaceAll("[|?*<\":>+\\[\\]']", "");
        if (m_status.equalsIgnoreCase("Married")) {
            maritialstatus.setSelection(1);
        } else if (m_status.equalsIgnoreCase("Single")) {
            maritialstatus.setSelection(2);
        }

        if (gaurdian.equalsIgnoreCase("")) {

            editgaurdianName.setText(guardian_name.replaceAll("[|?*<\":>+\\[\\]']", ""));
        }
        if (!Occupation.equalsIgnoreCase("")) {
            occupation.setSelection(Integer.parseInt(Occupation));
        } else {
            occupation.setSelection(0);
        }
    }


    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    public static boolean validateAadharNumber(String aadharNumber) {
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if (isValidAadhar) {
            isValidAadhar = validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback onNextClickedCallback) {

        onNextCallback = onNextClickedCallback;

        if (nextScreen_flag) {

            onNextClickedCallback.goToNextStep();

        } else {
            SubmitUserDetails();

        }

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback onCompleteClickedCallback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback onBackClickedCallback) {
        onBackClickedCallback.goToPrevStep();

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

    public static boolean isValidBirthday(String birthday) {
        Calendar calendar = parseDateString(birthday);

        int year = calendar.get(Calendar.YEAR);

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        return year >= 1900 && year < thisYear;
    }

    class Validator implements AutoCompleteTextView.Validator {

        @Override
        public boolean isValid(CharSequence text) {
            Log.v("Test", "Checking if valid: " + text);
            Arrays.sort(state_array);
            return Arrays.binarySearch(state_array, text.toString()) > 0;
        }

        @Override
        public CharSequence fixText(CharSequence invalidText) {
            Log.v("Test", "Returning fixed text");

            /* I'm just returning an empty string here, so the field will be blanked,
             * but you could put any kind of action here, like popping up a dialog?
             *
             * Whatever value you return here must be in the list of valid words.
             */
            return "";
        }
    }

    class FocusListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.v("Test", "Focus changed");
            if (v.getId() == R.id.ed_state && !hasFocus) {
                Log.v("Test", "Performing validation");
                ((AutoCompleteTextView) v).performValidation();
            }
        }
    }

    class FocusListenerper implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.v("Test", "Focus changed");
            if (v.getId() == R.id.ed_state && !hasFocus) {
                Log.v("Test", "Performing validation");
                ((AutoCompleteTextView) v).performValidation();
            }
        }
    }


}
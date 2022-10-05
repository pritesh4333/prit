package com.acumengroup.mobile.trade;


import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.acumengroup.greekmain.core.model.bankdetail.KycDetailRequest;
import com.acumengroup.greekmain.core.model.bankdetail.KycDetailResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.StateCountryRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MutualFund.MFundCommunicator;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.acumengroup.mobile.login.UserCreationPanDetails.parseDateString;
import static com.acumengroup.mobile.login.UserCreationPersonalDetails.validateAadharNumber;

public class MyAccountPersonalDetails extends GreekBaseFragment implements View.OnClickListener, Step {

    private GreekEditText editname, editDob, editmobile, editemail, editpan, edit_income, editcaddr1, editcaddr2, editcaddr3, editcpin, editcresiphone, editcforaddr1, editcforaddr2, editcforaddr3, editcforpincode, editcforresiphone, editnationality, editaadhar, editgaurdianName;
    private Spinner spinner_gender, spinner_maritialstatus, spinner_occupation;
    private Button nextBtn, submitBtn;
    private AutoCompleteTextView edit_City, edit_State, edit_Country, edit_per_city, edit_per_State, edit_per_country;
    private ArrayAdapter<String> state_adapter, country_adapter;
    private String[] state;
    private String my_State = null, my_per_State = null;
    private CheckBox adresscheck;
    private GreekTextView title;
    private LinearLayout layout_corp_address;
    private List<String> genderlist, maritiallist, occupationlist;
    private ArrayAdapter<String> genderAdapter, maritalAdapter, occupationAdapter;
    private PersonalDetailResponse personalResponse = null;
    private String cFirstApplicantDOB = "", userExists, clientCode = "";
    private String from, cForAdd1 = "";
    private FloatingActionButton flt_edit;
    private Boolean fltEditValue = false;
    private static final String ARG_PARAM2 = "param2";
    private Bundle bundle;
    private String blockCharacterSet = "~#@^|$%&@><*!-?+=_()";
    private KycDetailResponse kycDetailResponse;
    private Matcher matcher_date;
    private static final String DATE_PATTERN = "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";
    private String[] countries;
    private TextInputLayout ti_clientCode, ti_ed_dob, ti_gardian, ti_email, ti_mobile, ti_pan, ti_income, ti_add1, ti_add2, ti_add3,
            ti_country, ti_state, ti_city, ti_pincode, ti_resi_phone, ti_addr1, ti_addr2, ti_addr3, ti_per_country, ti_per_state,
            ti_per_city, ti_per_pincode, ti_resi_per_phone, ti_nationality, ti_aadhar_number;

    public static MyAccountPersonalDetails newInstance(Bundle bundle, String param2) {
        MyAccountPersonalDetails fragment = new MyAccountPersonalDetails();
        Bundle args = bundle;
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.personaldetailtab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.personaldetailtab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_PERSONAL_DETAILS;

        ti_clientCode = mfActionView.findViewById(R.id.ti_clientCode);
        ti_ed_dob = mfActionView.findViewById(R.id.ti_ed_dob);
        ti_gardian = mfActionView.findViewById(R.id.ti_gardian);
        ti_email = mfActionView.findViewById(R.id.ti_email);
        ti_mobile = mfActionView.findViewById(R.id.ti_mobile);
        ti_pan = mfActionView.findViewById(R.id.ti_pan);
        ti_income = mfActionView.findViewById(R.id.ti_income);
        ti_add1 = mfActionView.findViewById(R.id.ti_add1);
        ti_add2 = mfActionView.findViewById(R.id.ti_add2);
        ti_add3 = mfActionView.findViewById(R.id.ti_add3);
        ti_country = mfActionView.findViewById(R.id.ti_country);
        ti_state = mfActionView.findViewById(R.id.ti_state);
        ti_city = mfActionView.findViewById(R.id.ti_city);
        ti_pincode = mfActionView.findViewById(R.id.ti_pincode);
        ti_resi_phone = mfActionView.findViewById(R.id.ti_resi_phone);
        ti_addr1 = mfActionView.findViewById(R.id.ti_addr1);
        ti_addr2 = mfActionView.findViewById(R.id.ti_addr2);
        ti_addr3 = mfActionView.findViewById(R.id.ti_addr3);
        ti_per_country = mfActionView.findViewById(R.id.ti_per_country);
        ti_per_state = mfActionView.findViewById(R.id.ti_per_state);
        ti_per_city = mfActionView.findViewById(R.id.ti_per_city);
        ti_per_pincode = mfActionView.findViewById(R.id.ti_per_pincode);
        ti_resi_per_phone = mfActionView.findViewById(R.id.ti_resi_per_phone);
        ti_nationality = mfActionView.findViewById(R.id.ti_nationality);
        ti_aadhar_number = mfActionView.findViewById(R.id.ti_aadhar_number);


        setupView(mfActionView);
        hideAppTitle();

        KycDetailRequest.sendRequest(AccountDetails.getUserPAN(getMainActivity()), AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);

        genderlist = new ArrayList<>();
        genderlist.add("Male");
        genderlist.add("Female");
        genderAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), genderlist);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(genderAdapter);

        maritiallist = new ArrayList<>();
        maritiallist.add("Married");
        maritiallist.add("Single");
        maritalAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), maritiallist);
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maritialstatus.setAdapter(maritalAdapter);

        occupationlist = new ArrayList<>();
        occupationlist.add("Business");
        occupationlist.add("Service");
        occupationlist.add("Professional");
        occupationlist.add("Agriculture");
        occupationlist.add("Retired");
        occupationlist.add("Housewife");
        occupationlist.add("Student");
        occupationlist.add("Others");
        occupationAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), occupationlist);
        occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_occupation.setAdapter(occupationAdapter);


        submitBtn.setOnClickListener(MyAccountPersonalDetails.this);
        nextBtn.setOnClickListener(MyAccountPersonalDetails.this);
        editDob.setOnClickListener(MyAccountPersonalDetails.this);

        adresscheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    layout_corp_address.setVisibility(View.GONE);

                } else {

                    layout_corp_address.setVisibility(View.VISIBLE);
                }

            }
        });

        flt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fltEditValue) {

                    fltEditValue = false;

                    flt_edit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.common_red_bg)));
                    // flt_edit.setBackgroundResource(R.drawable.ic_mode_edit_white_24dp);
                    flt_edit.setImageDrawable(ContextCompat.getDrawable(getMainActivity(), R.drawable.ic_mode_edit_white_24dp));

                    editname.setEnabled(false);
                    editDob.setEnabled(false);
                    editgaurdianName.setEnabled(false);

                    spinner_occupation.setEnabled(false);
                    spinner_occupation.setClickable(false);

                    adresscheck.setEnabled(false);

                    editmobile.setEnabled(false);
                    editemail.setEnabled(false);
                    spinner_gender.setEnabled(false);
                    spinner_maritialstatus.setEnabled(false);
                    spinner_occupation.setEnabled(false);
                    editpan.setEnabled(false);
                    edit_income.setEnabled(false);


                    editcaddr1.setEnabled(false);
                    editcaddr2.setEnabled(false);
                    editcaddr3.setEnabled(false);
                    edit_City.setEnabled(false);
                    edit_State.setEnabled(false);
                    edit_Country.setEnabled(false);
                    editcpin.setEnabled(false);
                    editcresiphone.setEnabled(false);

                    if (cForAdd1.equalsIgnoreCase("")) {

                        adresscheck.setEnabled(false);

                    } else {

                        editcforaddr1.setEnabled(false);
                        editcforaddr2.setEnabled(false);
                        editcforaddr3.setEnabled(false);
                        edit_per_city.setEnabled(false);
                        edit_per_State.setEnabled(false);
                        edit_per_country.setEnabled(false);
                        editcforpincode.setEnabled(false);
                        editcforresiphone.setEnabled(false);
                    }

                    editnationality.setEnabled(false);
                    editaadhar.setEnabled(false);
                    submitBtn.setEnabled(false);
                    submitBtn.setClickable(false);
                    adresscheck.setEnabled(false);

                    nextBtn.setEnabled(true);
                    nextBtn.setClickable(true);

                } else {

                    fltEditValue = true;
                    flt_edit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_gray)));
                    //flt_edit.setBackgroundResource(R.drawable.ic_mode_edit_black_24dp);
                    flt_edit.setImageDrawable(ContextCompat.getDrawable(getMainActivity(), R.drawable.ic_mode_edit_black_24dp));

                    if (editname.getText().toString().length() <= 0) {
                        editname.setEnabled(true);
                    } else {
                        editname.setEnabled(false);
                    }
                    if (editpan.getText().toString().length() <= 0) {
                        editpan.setEnabled(true);
                    } else {
                        editpan.setEnabled(false);
                    }


                    if (cFirstApplicantDOB.equalsIgnoreCase("")) {
                        editDob.setEnabled(true);
                    } else {
                        editDob.setEnabled(false);
                    }
                    editgaurdianName.setEnabled(true);

                    spinner_occupation.setEnabled(true);
                    spinner_occupation.setClickable(true);

                    adresscheck.setEnabled(true);


                    editmobile.setEnabled(true);
                    editemail.setEnabled(true);
                    spinner_gender.setEnabled(true);
                    spinner_maritialstatus.setEnabled(true);
                    spinner_occupation.setEnabled(true);


                    edit_income.setEnabled(true);
                    editcaddr1.setEnabled(true);
                    editcaddr2.setEnabled(true);
                    editcaddr3.setEnabled(true);
                    edit_City.setEnabled(true);
                    edit_State.setEnabled(true);
                    //edit_Country.setEnabled(true);
                    editcpin.setEnabled(true);
                    editcresiphone.setEnabled(true);

                    if (cForAdd1.equalsIgnoreCase("")) {

                        adresscheck.setEnabled(true);

                    } else {

                        editcforaddr1.setEnabled(true);
                        editcforaddr2.setEnabled(true);
                        editcforaddr3.setEnabled(true);
                        edit_per_city.setEnabled(true);
                        edit_per_State.setEnabled(true);
                        //  edit_per_country.setEnabled(true);
                        editcforpincode.setEnabled(true);
                        editcforresiphone.setEnabled(true);
                    }

                    editnationality.setEnabled(true);
                    editaadhar.setEnabled(true);
                    submitBtn.setEnabled(true);
                    submitBtn.setClickable(true);
                    adresscheck.setEnabled(true);
                    nextBtn.setEnabled(false);
                    nextBtn.setClickable(false);
                    submitBtn.setBackground((getResources().getDrawable(R.drawable.button_arcadia)));

                }
            }
        });

        StateCountryRequest.sendRequest(getContext(), serviceResponseHandler);

        return mfActionView;
    }

    private void setupView(View mfActionView) {

        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        editname = mfActionView.findViewById(R.id.ed_name);
        editDob = mfActionView.findViewById(R.id.ed_dob);
        editmobile = mfActionView.findViewById(R.id.ed_mobile);
        editpan = mfActionView.findViewById(R.id.ed_pan);
        edit_income = mfActionView.findViewById(R.id.ed_income);
        InputFilter[] panfilters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        editpan.setFilters(panfilters);

        spinner_gender = mfActionView.findViewById(R.id.spnr_gender);
        spinner_maritialstatus = mfActionView.findViewById(R.id.spnr_maritial_status);
        nextBtn = mfActionView.findViewById(R.id.btn_personal_next);
        submitBtn = mfActionView.findViewById(R.id.btn_personal_submit);
        //calender = (ImageView) mfActionView.findViewById(R.id.calender_dob);
        adresscheck = mfActionView.findViewById(R.id.chk_same_address);

        editcaddr1 = mfActionView.findViewById(R.id.ed_add1);
        editcaddr2 = mfActionView.findViewById(R.id.ed_add2);
        editcaddr3 = mfActionView.findViewById(R.id.ed_add3);

        edit_City = mfActionView.findViewById(R.id.ed_city);
        edit_State = mfActionView.findViewById(R.id.ed_state);

        edit_State.setValidator(new Validator());
        edit_State.setOnFocusChangeListener(new FocusListener());

        edit_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                my_State = state_adapter.getItem(i);
            }
        });

        editcpin = mfActionView.findViewById(R.id.ed_pincode);
        edit_Country = mfActionView.findViewById(R.id.ed_country);
        InputFilter[] countryfilters = {new InputFilter.AllCaps()};
        // edit_Country.setFilters(countryfilters);
        editcresiphone = mfActionView.findViewById(R.id.ed_resi_phone);
        editgaurdianName = mfActionView.findViewById(R.id.ed_cgaurdian);

        editcforaddr1 = mfActionView.findViewById(R.id.ed_addr1);
        editcforaddr2 = mfActionView.findViewById(R.id.ed_addr2);
        editcforaddr3 = mfActionView.findViewById(R.id.ed_addr3);

        edit_per_city = mfActionView.findViewById(R.id.ed_per_city);
        edit_per_State = mfActionView.findViewById(R.id.ed_per_state);
        edit_per_State.setValidator(new Validator());
        edit_per_State.setOnFocusChangeListener(new FocusListenerper());

        edit_per_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                my_per_State = state_adapter.getItem(i);
            }
        });


        editcforpincode = mfActionView.findViewById(R.id.ed_per_pincode);
        edit_per_country = mfActionView.findViewById(R.id.ed_per_country);
        InputFilter[] countryperfilters = {new InputFilter.AllCaps()};
        // edit_per_country.setFilters(countryperfilters);
        editcforresiphone = mfActionView.findViewById(R.id.ed_resi_per_phone);
        spinner_occupation = mfActionView.findViewById(R.id.spnr_occupation);
        layout_corp_address = mfActionView.findViewById(R.id.layout_corp_address);
        editemail = mfActionView.findViewById(R.id.ed_email);
        editnationality = mfActionView.findViewById(R.id.ed_nationality);
        InputFilter[] nationalityfilters = {new InputFilter.AllCaps()};
        editnationality.setFilters(nationalityfilters);
        editaadhar = mfActionView.findViewById(R.id.ed_aadhar_number);
        flt_edit = mfActionView.findViewById(R.id.flt_edit);
        title = mfActionView.findViewById(R.id.personaldetail_title);


        edit_City.setThreshold(1);
        edit_Country.setThreshold(1);
        edit_State.setThreshold(1);
        edit_per_city.setThreshold(1);
        edit_per_country.setThreshold(1);
        edit_per_State.setThreshold(1);

        submitBtn.setEnabled(false);
        submitBtn.setClickable(false);

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

        InputFilter[] filters = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        editpan.setFilters(filters);

        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };
        editDob.setFilters((new InputFilter[]{filter}));
        InputFilter[] dobfilter = {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()};
        editDob.setFilters(panfilters);

        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePicker();
            }
        });

        int textColor = AccountDetails.getTextColorDropdown();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {


            editname.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editgaurdianName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editDob.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editDob.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_black_24dp, 0);
            editmobile.setTextColor(getResources().getColor(R.color.black));
            editmobile.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_income.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_income.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editpan.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editpan.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editpan.invalidate();
            adresscheck.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            adresscheck.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcaddr1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcaddr1.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcaddr2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcaddr2.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcaddr3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcaddr3.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_City.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_City.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_State.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_State.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcpin.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcpin.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_Country.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_Country.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcresiphone.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcresiphone.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforaddr1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforaddr1.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforaddr2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforaddr2.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforaddr3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforaddr3.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_per_city.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_per_city.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_per_State.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_per_State.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforpincode.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforpincode.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_per_country.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edit_per_country.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforresiphone.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editcforresiphone.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editemail.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editemail.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editnationality.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editnationality.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editaadhar.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            editaadhar.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            title.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));


        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {

            // adresscheck.setButtonDrawable(R.drawable.custom_checkbox_amo);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//23791
                adresscheck.setButtonTintList(ContextCompat.getColorStateList(getMainActivity(), R.color.white));
            }

//            ti_ed_dob.setHintTextAppearance(R.style.blacktheme);

//            setInputTextLayoutColor(R.color.white, ti_clientCode);
//            setInputTextLayoutColor(R.color.white, ti_ed_dob);
//            setInputTextLayoutColor(R.color.white, ti_gardian);
//            setInputTextLayoutColor(R.color.white, ti_email);
//            setInputTextLayoutColor(R.color.white, ti_mobile);
//            setInputTextLayoutColor(R.color.white, ti_pan);
//            setInputTextLayoutColor(R.color.white, ti_income);
//            setInputTextLayoutColor(R.color.white, ti_add1);
//            setInputTextLayoutColor(R.color.white, ti_add2);
//            setInputTextLayoutColor(R.color.white, ti_add3);
//            setInputTextLayoutColor(R.color.white, ti_country);
//            setInputTextLayoutColor(R.color.white, ti_state);
//            setInputTextLayoutColor(R.color.white, ti_city);
//            setInputTextLayoutColor(R.color.white, ti_pincode);
//            setInputTextLayoutColor(R.color.white, ti_resi_phone);
//            setInputTextLayoutColor(R.color.white, ti_addr1);
//            setInputTextLayoutColor(R.color.white, ti_addr2);
//            setInputTextLayoutColor(R.color.white, ti_addr3);
//            setInputTextLayoutColor(R.color.white, ti_per_country);
//            setInputTextLayoutColor(R.color.white, ti_per_state);
//            setInputTextLayoutColor(R.color.white, ti_per_city);
//            setInputTextLayoutColor(R.color.white, ti_per_pincode);
//            setInputTextLayoutColor(R.color.white, ti_resi_per_phone);
//            setInputTextLayoutColor(R.color.white, ti_nationality);
//            setInputTextLayoutColor(R.color.white, ti_aadhar_number);


//            setInputTextLayoutColors(editDob, getResources().getColor(R.color.white));

            editDob.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_white_24dp, 0);

            editname.setTextColor(getResources().getColor(textColor));
            editgaurdianName.setTextColor(getResources().getColor(textColor));
            editDob.setTextColor(getResources().getColor(textColor));
            editDob.setHintTextColor(getResources().getColor(R.color.white));
            editmobile.setTextColor(getResources().getColor(textColor));
            editmobile.setHintTextColor(getResources().getColor(R.color.white));
            edit_income.setTextColor(getResources().getColor(textColor));
            edit_income.setHintTextColor(getResources().getColor(textColor));
            editpan.setTextColor(getResources().getColor(textColor));
            editpan.setHintTextColor(getResources().getColor(textColor));
            adresscheck.setTextColor(getResources().getColor(textColor));
            adresscheck.setHintTextColor(getResources().getColor(textColor));
            editcaddr1.setTextColor(getResources().getColor(textColor));
            editcaddr1.setHintTextColor(getResources().getColor(textColor));
            editcaddr2.setTextColor(getResources().getColor(textColor));
            editcaddr2.setHintTextColor(getResources().getColor(textColor));
            editcaddr3.setTextColor(getResources().getColor(textColor));
            editcaddr3.setHintTextColor(getResources().getColor(textColor));
            edit_City.setTextColor(getResources().getColor(textColor));
            edit_City.setHintTextColor(getResources().getColor(textColor));
            edit_State.setTextColor(getResources().getColor(textColor));
            edit_State.setHintTextColor(getResources().getColor(textColor));
            editcpin.setTextColor(getResources().getColor(textColor));
            editcpin.setHintTextColor(getResources().getColor(textColor));
            edit_Country.setTextColor(getResources().getColor(textColor));
            edit_Country.setHintTextColor(getResources().getColor(textColor));
            editcresiphone.setTextColor(getResources().getColor(textColor));
            editcresiphone.setHintTextColor(getResources().getColor(textColor));
            editcforaddr1.setTextColor(getResources().getColor(textColor));
            editcforaddr1.setHintTextColor(getResources().getColor(textColor));
            editcforaddr2.setTextColor(getResources().getColor(textColor));
            editcforaddr2.setHintTextColor(getResources().getColor(textColor));
            editcforaddr3.setTextColor(getResources().getColor(textColor));
            editcforaddr3.setHintTextColor(getResources().getColor(textColor));
            edit_per_city.setTextColor(getResources().getColor(textColor));
            edit_per_city.setHintTextColor(getResources().getColor(textColor));
            edit_per_State.setTextColor(getResources().getColor(textColor));
            edit_per_State.setHintTextColor(getResources().getColor(textColor));
            editcforpincode.setTextColor(getResources().getColor(textColor));
            editcforpincode.setHintTextColor(getResources().getColor(textColor));
            edit_per_country.setTextColor(getResources().getColor(textColor));
            edit_per_country.setHintTextColor(getResources().getColor(textColor));
            editcforresiphone.setTextColor(getResources().getColor(textColor));
            editcforresiphone.setHintTextColor(getResources().getColor(textColor));
            editemail.setTextColor(getResources().getColor(textColor));
            editemail.setHintTextColor(getResources().getColor(textColor));
            editnationality.setTextColor(getResources().getColor(textColor));
            editnationality.setHintTextColor(getResources().getColor(textColor));
            editaadhar.setTextColor(getResources().getColor(textColor));
            editaadhar.setHintTextColor(getResources().getColor(textColor));
            title.setTextColor(getResources().getColor(textColor));
            // adresscheck.setButtonDrawable(R.drawable.custom_checkbox_black);
        }

    }

    @Override
    public void onClick(View view) {

        String nation, cforad1, cforad2, cforad3, cforccity, cforcstate, cforcpin = "", cforccountry = "", cforcresiphone, cgender;

        if (view.getId() == R.id.btn_personal_submit) {

            Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
            Matcher matcher = pattern.matcher(editpan.getText().toString());

            matcher_date = Pattern.compile(DATE_PATTERN).matcher(editDob.getText().toString());

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
            }

            if (TextUtils.isEmpty(editname.getText().toString())) {
                editname.setError("Enter name");
                editname.requestFocus();

            } else if (TextUtils.isEmpty(editDob.getText().toString())) {
                editDob.setError("Enter date of birth");
                editDob.requestFocus();

            } else if (!isValidBirthday(editDob.getText().toString())) {
                editDob.setError("Invalid Date Format");
                editDob.requestFocus();
                return;

            } else if (TextUtils.isEmpty(editgaurdianName.getText().toString())) {
                editgaurdianName.setError("Enter guardian name");
                editgaurdianName.requestFocus();

            } else if (TextUtils.isEmpty(editemail.getText().toString()) || !Patterns.EMAIL_ADDRESS.matcher(editemail.getText().toString()).matches()) {
                editemail.setError("Enter valid email");
                editemail.requestFocus();

            } else if (TextUtils.isEmpty(editmobile.getText().toString()) || editmobile.getText().toString().length() < 10) {
                editmobile.setError("Enter valid mobile number");
                editmobile.requestFocus();

            } else if (!matcher.matches()) {
                editpan.setError("Invalid Pan Number");
                return;

            } else if (TextUtils.isEmpty(edit_income.getText().toString())) {
                edit_income.setError("Enter annual income");
                edit_income.requestFocus();

            } else if (TextUtils.isEmpty(editcaddr1.getText().toString())) {
                editcaddr1.setError("Enter address");
                editcaddr1.requestFocus();

            } else if (TextUtils.isEmpty(editcaddr2.getText().toString())) {
                editcaddr2.setError("Enter address");
                editcaddr2.requestFocus();

            } else if (TextUtils.isEmpty(editcaddr3.getText().toString())) {
                editcaddr3.setError("Enter address");
                editcaddr3.requestFocus();

            } else if (TextUtils.isEmpty(edit_Country.getText().toString())) {

                edit_Country.setError("Enter country");
                edit_Country.requestFocus();

            } else if (TextUtils.isEmpty(edit_State.getText().toString())) {
                edit_State.setError("Enter state");
                edit_State.requestFocus();

            } else if (!Arrays.asList(state).contains(edit_State.getText().toString())) {
                edit_State.setError("Select available state name");
                edit_State.requestFocus();
            } else if (TextUtils.isEmpty(edit_City.getText().toString())) {
                edit_City.setError("Enter city");
                edit_City.requestFocus();

            } else if (TextUtils.isEmpty(editcpin.getText().toString()) || editcpin.getText().toString().length() < 6) {
                editcpin.setError("Enter valid pin code");
                editcpin.requestFocus();

            } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforaddr1.getText().toString())) {
                editcforaddr1.setError("Enter address");
                editcforaddr1.requestFocus();

            } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforaddr2.getText().toString())) {
                editcforaddr2.setError("Enter address");
                editcforaddr2.requestFocus();

            } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforaddr3.getText().toString())) {
                editcforaddr3.setError("Enter address");
                editcforaddr3.requestFocus();


            } else if (!adresscheck.isChecked() && TextUtils.isEmpty(edit_per_country.getText().toString())) {
                edit_per_country.setError("Enter country");
                edit_per_country.requestFocus();

            } else if (!adresscheck.isChecked() && TextUtils.isEmpty(edit_per_State.getText().toString())) {
                edit_per_State.setError("Enter state");
                edit_per_State.requestFocus();

            } else if (!adresscheck.isChecked() && !Arrays.asList(state).contains(edit_per_State.getText().toString())) {

                edit_per_State.setError("Select available state name");
                edit_per_State.requestFocus();

            } else if (!adresscheck.isChecked() && TextUtils.isEmpty(edit_per_city.getText().toString())) {
                edit_per_city.setError("Enter city");
                edit_per_city.requestFocus();

            } else if (!adresscheck.isChecked() && TextUtils.isEmpty(editcforpincode.getText().toString())) {
                editcforpincode.setError("Enter PinCode");
                editcforpincode.requestFocus();

            } else if (TextUtils.isEmpty(editnationality.getText().toString())) {
                editnationality.setError("Enter Nationality");
                editnationality.requestFocus();

            } else if (TextUtils.isEmpty(editaadhar.getText().toString())) {
                editaadhar.setError("Enter aadhar number");
                editaadhar.requestFocus();


            } else if (!validateAadharNumber(editaadhar.getText().toString())) {
                editaadhar.setError("Enter valid aadhar number");
                editaadhar.requestFocus();

            } else {

                if (spinner_gender.getSelectedItem().toString().equals("Male")) {
                    cgender = "M";

                } else {
                    cgender = "F";
                }

                if (!adresscheck.isChecked()) {

                    cforad1 = editcforaddr1.getText().toString();
                    cforad2 = editcforaddr2.getText().toString();
                    cforad3 = editcforaddr3.getText().toString();
                    cforccity = edit_per_city.getText().toString();
                    cforcstate = edit_per_State.getText().toString();
                    cforccountry = edit_per_country.getText().toString();
                    cforcpin = editcforpincode.getText().toString();
                    cforcresiphone = editcforresiphone.getText().toString();

                } else {

                    cforad1 = editcaddr1.getText().toString();
                    cforad2 = editcaddr2.getText().toString();
                    cforad3 = editcaddr3.getText().toString();
                    cforccity = edit_City.getText().toString();
                    cforcstate = edit_State.getText().toString();
                    cforccountry = edit_Country.getText().toString();
                    cforcpin = editcpin.getText().toString();
                    cforcresiphone = editcresiphone.getText().toString();
                }

                String name = editname.getText().toString();
                String dob = editDob.getText().toString();
                String mobile = editmobile.getText().toString();
                String marital_status = spinner_maritialstatus.getSelectedItem().toString();
                String pan = editpan.getText().toString();
                String income = edit_income.getText().toString();

                String caddr1 = editcaddr1.getText().toString();
                String caddr2 = editcaddr2.getText().toString();
                String caddr3 = editcaddr3.getText().toString();
                String ccity = edit_City.getText().toString();
                String cclientstate = edit_State.getText().toString();
                String ccountry = edit_Country.getText().toString();
                String cpin = editcpin.getText().toString();
                String cresiphone = editcresiphone.getText().toString();

                String occption = "0" + (spinner_occupation.getSelectedItemPosition() + 1);
                String adhar = editaadhar.getText().toString();
                String email = editemail.getText().toString();
                String gaurdian = editgaurdianName.getText().toString();

                if (editnationality.getText().toString().equalsIgnoreCase("INDIAN")) {
                    nation = "01";
                } else {

                    nation = "02";
                }

                showProgress();

                PersonalDetailRequest.sendRequest("update", AccountDetails.getcUserType(),
                        name, dob, gaurdian, mobile, marital_status, cgender, pan, income,
                        caddr1, caddr2, caddr3, ccity, cclientstate, cpin, ccountry, cresiphone,
                        cforad1, cforad2, cforad3, cforccity, cforcstate, cforcpin, cforccountry, cforcresiphone,
                        occption, AccountDetails.getUsername(getMainActivity()), email, nation, adhar, getMainActivity(), serviceResponseHandler);


                Bundle args = new Bundle();
                args.putString("clientCode", clientCode);
                args.putString("from", from);

            }

        } else if (view.getId() == R.id.calender_dob) {

            showDatePicker();

        } else if (view.getId() == R.id.btn_personal_next) {

            if (getArguments() != null && getArguments().get("cAccNoNo1").equals("")) {
                navigateTo(NAV_TO_ALL_BANKDETAILS_MF, getArguments(), false);

            } else if (getArguments() != null && !getArguments().get("cAccNoNo1").equals("")) {
                navigateTo(NAV_TO_ALL_BANKDETAILS_MF, getArguments(), true);

            }

        }

    }


    @Override
    public void handleResponse(Object response) {
        hideProgress();

        JSONResponse jsonResponse = (JSONResponse) response;
        if (STATE_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && STATE_SVC_GROUP.equals(jsonResponse.getServiceName())) {

            Log.e("STATEGROUP", "STATE_SVC_GROUP====>" + jsonResponse);

            try {
                JSONObject jsonObject = ((JSONResponse) response).getResObject();
                JSONObject jsonObject_response = jsonObject.getJSONObject("response");
                JSONObject jsonObject2_data = jsonObject_response.getJSONObject("data");

                JSONArray jsonArray_country = jsonObject2_data.getJSONArray("countryMaster");
                JSONArray jsonArray_state = jsonObject2_data.getJSONArray("stateMaster");

                countries = new String[jsonArray_country.length()];

                for (int i = 0; i < jsonArray_country.length(); i++) {

                    countries[i] = jsonArray_country.getString(i);
                }

                country_adapter = new ArrayAdapter<String>(getMainActivity(), android.R.layout.simple_list_item_1, countries);

                state = new String[jsonArray_state.length()];

                for (int i = 0; i < jsonArray_state.length(); i++) {
                    state[i] = jsonArray_state.getString(i);
                }


                state_adapter = new ArrayAdapter<String>(getMainActivity(), android.R.layout.simple_list_item_1, state);

                edit_State.setAdapter(state_adapter);
                edit_Country.setAdapter(country_adapter);

                edit_per_State.setAdapter(state_adapter);
                edit_per_country.setAdapter(country_adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (MF_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && KYC_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                personalResponse = (PersonalDetailResponse) jsonResponse.getResponse();
                String errorcode = personalResponse.getErrorCode();

                if (errorcode.equals("0") || errorcode.equals("3")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Personal Details Updated Successfully", "Ok", true, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                                nextBtn.setEnabled(true);
                                nextBtn.setClickable(true);

                                KycDetailRequest.sendRequest(AccountDetails.getUserPAN(getMainActivity()), AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);

                            }
                        }
                    });


                    editname.setEnabled(false);
                    editDob.setEnabled(false);
                    editgaurdianName.setEnabled(false);

                    spinner_occupation.setEnabled(false);
                    spinner_occupation.setClickable(false);
                    adresscheck.setEnabled(false);

                    editmobile.setEnabled(false);
                    editemail.setEnabled(false);
                    spinner_gender.setEnabled(false);
                    spinner_maritialstatus.setEnabled(false);
                    spinner_occupation.setEnabled(false);
                    editpan.setEnabled(false);
                    edit_income.setEnabled(false);


                    editcaddr1.setEnabled(false);
                    editcaddr2.setEnabled(false);
                    editcaddr3.setEnabled(false);
                    edit_City.setEnabled(false);
                    edit_State.setEnabled(false);
                    edit_Country.setEnabled(false);
                    editcpin.setEnabled(false);
                    editcresiphone.setEnabled(false);

                    if (cForAdd1.equalsIgnoreCase("")) {

                        adresscheck.setEnabled(false);

                    } else {

                        editcforaddr1.setEnabled(false);
                        editcforaddr2.setEnabled(false);
                        editcforaddr3.setEnabled(false);
                        edit_per_city.setEnabled(false);
                        edit_per_State.setEnabled(false);
                        edit_per_country.setEnabled(false);
                        editcforpincode.setEnabled(false);
                        editcforresiphone.setEnabled(false);
                    }
                    editnationality.setEnabled(false);
                    editaadhar.setEnabled(false);
                    submitBtn.setEnabled(false);
                    submitBtn.setClickable(false);
                    adresscheck.setEnabled(false);
                    nextBtn.setEnabled(true);
                    nextBtn.setClickable(true);
                    submitBtn.setBackgroundColor(Color.GRAY);

                    flt_edit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.common_red_bg)));
                    flt_edit.setImageDrawable(ContextCompat.getDrawable(getMainActivity(), R.drawable.ic_mode_edit_white_24dp));

                    fltEditValue = false;

                }

            } catch (Exception e) {


            }
        } else if ("getKYCDetailsMF".equals(jsonResponse.getServiceName())) {
            try {
                kycDetailResponse = (KycDetailResponse) jsonResponse.getResponse();
                Bundle args = new Bundle();
                args.putString("cErrorCode", kycDetailResponse.getErrorCode());


                if (kycDetailResponse.getErrorCode().equalsIgnoreCase("3")) {

                } else {

//                    userExists = getArguments().getString("userExists");
                    clientCode = getArguments().getString("clientCode");
                    from = getArguments().getString("from");

                    String cOccupation = kycDetailResponse.getcOccupation();
                    String cFirstApplicantName = kycDetailResponse.getcFirstApplicantName();
                    String cgaurdianName = kycDetailResponse.getcClientGuardian();
                    cFirstApplicantDOB = kycDetailResponse.getcFirstApplicantDOB();
                    String cFirstAppGender = kycDetailResponse.getcFirstAppGender();
                    String cFirstApplicantPAN = kycDetailResponse.getcFirstApplicantPAN();
                    String cClientNominee = kycDetailResponse.getcClientNominee();
                    String cClientNomineeRelation = kycDetailResponse.getcClientNomineeRelation();
                    String income = kycDetailResponse.getIncome();
                    String cAdd1 = kycDetailResponse.getcAdd1();
                    String cAdd2 = kycDetailResponse.getcAdd2();
                    String cAdd3 = kycDetailResponse.getcAdd3();
                    String cCity = kycDetailResponse.getcCity();
                    String cClientState = kycDetailResponse.getcClientState();
                    String cPINCode = kycDetailResponse.getcPINCode();
                    String cCountry = kycDetailResponse.getcCountry();
                    String cResiPhone = kycDetailResponse.getcResiPhone();
                    String cClientEmail = kycDetailResponse.getcClientEmail();
                    cForAdd1 = kycDetailResponse.getcForAdd1();
                    String cForAdd2 = kycDetailResponse.getcForAdd2();
                    String cForAdd3 = kycDetailResponse.getcForAdd3();
                    String cForCity = kycDetailResponse.getcForCity();
                    String cForPinCode = kycDetailResponse.getcForPinCode();
                    String cForState = kycDetailResponse.getcForState();
                    String cForCountry = kycDetailResponse.getcForCountry();
                    String cForResiPhone = kycDetailResponse.getcForResiPhone();
                    String cMobile = kycDetailResponse.getcMobile();
                    String nationality = kycDetailResponse.getcNationality();
                    String aadhar = kycDetailResponse.getcAadharNo();
                    String marital_status = kycDetailResponse.getcMaritalStatus();


                    int position = Integer.parseInt(cOccupation);
                    if (position != 0)
                        spinner_occupation.setSelection(position - 1, true);

                    editaadhar.setText(aadhar);
                    editaadhar.setEnabled(false);

                    editgaurdianName.setText(cgaurdianName);
                    editgaurdianName.setEnabled(false);


                    if (nationality.equals("01") || nationality.equalsIgnoreCase("INDIAN")) {
                        editnationality.setText("INDIAN");
                    } else {
                        editnationality.setText("OTHERS");
                    }
                    editnationality.setEnabled(false);

                    if (marital_status.equalsIgnoreCase("Married")) {
                        spinner_maritialstatus.setSelection(0);
                    } else if (marital_status.equalsIgnoreCase("Single")) {
                        spinner_maritialstatus.setSelection(1);
                    }
                    spinner_maritialstatus.setEnabled(false);

                    editname.setText(cFirstApplicantName);
                    editname.setClickable(false);

                    editpan.setText(cFirstApplicantPAN);
                    editpan.setEnabled(false);

                    String result = cFirstApplicantDOB.replaceAll("[|?*<\":>+\\[\\]\']", "");
                    editDob.setText(result);
                    editDob.setClickable(false);
                    editDob.setEnabled(false);


                    String mob = cMobile.replaceAll("[|?*<\":>+\\[\\]\']", "");
                    editmobile.setText(mob);
                    editmobile.setEnabled(false);


                    if (cFirstAppGender.equals("M")) {
                        spinner_gender.setSelection(0);
                    } else {
                        spinner_gender.setSelection(1);
                    }
                    spinner_gender.setEnabled(false);


                    editgaurdianName.setText(cgaurdianName);
                    editemail.setText(cClientEmail);
                    edit_income.setText(income);


                    editcaddr1.setText(cAdd1);
                    editcaddr2.setText(cAdd2);
                    editcaddr3.setText(cAdd3);
                    edit_City.setText(cCity);
                    edit_State.setText(cClientState);
                    editcpin.setText(cPINCode);
                    editcresiphone.setText(cResiPhone);

                    if (cForAdd1.equalsIgnoreCase("") || cForAdd1.isEmpty()) {

                        adresscheck.setChecked(true);
                        layout_corp_address.setVisibility(View.GONE);

                    } else {

                        editcforaddr1.setText(cForAdd1);
                        editcforaddr2.setText(cForAdd2);
                        editcforaddr3.setText(cForAdd3);
                        edit_per_city.setText(cForCity);
                        edit_per_State.setText(cForState);
                        editcforpincode.setText(cForPinCode);
                        editcforresiphone.setText(cForResiPhone);
                    }

                    editname.setEnabled(false);
                    edit_income.setEnabled(false);

                    spinner_occupation.setEnabled(false);
                    spinner_occupation.setClickable(false);

                    adresscheck.setEnabled(false);

                    editcaddr1.setEnabled(false);
                    editcaddr2.setEnabled(false);
                    editcaddr3.setEnabled(false);
                    edit_City.setEnabled(false);
                    edit_State.setEnabled(false);
                    edit_Country.setEnabled(false);
                    editcpin.setEnabled(false);
                    editcresiphone.setEnabled(false);

                    editemail.setEnabled(false);

                    if (cForAdd1.equalsIgnoreCase("")) {
                        adresscheck.setEnabled(false);
                    } else {
                        editcforaddr1.setEnabled(false);
                        editcforaddr2.setEnabled(false);
                        editcforaddr3.setEnabled(false);
                        edit_per_city.setEnabled(false);
                        edit_per_State.setEnabled(false);
                        editcforpincode.setEnabled(false);
                        edit_per_country.setEnabled(false);
                        editcforresiphone.setEnabled(false);


                    }

                    submitBtn.setEnabled(false);
                    submitBtn.setClickable(false);
                    nextBtn.setVisibility(View.VISIBLE);


                }
            } catch (Exception e) {
                e.printStackTrace();
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


        nextBtn.setVisibility(View.GONE);
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

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    public void onEventMainThread(final MFundCommunicator mFundCommunicator) {

        bundle = mFundCommunicator.getBundle();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_PERSONAL_DETAILS;
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

        datePickerDialog.show(getMainActivity().getFragmentManager(), "Date Picker");
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
                mm = "0" + (month + 1);
            } else {
                mm = String.valueOf(month + 1);
            }

            String selecteddate = dd + "/" + mm + "/" + year;
            editDob.setText(selecteddate);
        }
    };


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
            Arrays.sort(state);
            return Arrays.binarySearch(state, text.toString()) > 0;
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

    private void setInputTextLayoutColor(int color, TextInputLayout textInputLayout) {
        try {


//            Field field = textInputLayout.getClass().getDeclaredField("mFocusedTextColor");
//            field.setAccessible(true);
//            int[][] states = new int[][]{
//                    new int[]{}
//            };
//            int[] colors = new int[]{
//                    color
//            };
//            ColorStateList myList = new ColorStateList(states, colors);
//            field.set(textInputLayout, myList);
//
//            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
//            fDefaultTextColor.setAccessible(true);
//            fDefaultTextColor.set(textInputLayout, myList);
//
//            Method method = textInputLayout.getClass().getDeclaredMethod("updateLabelState", boolean.class);
//            method.setAccessible(true);
//            method.invoke(textInputLayout, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
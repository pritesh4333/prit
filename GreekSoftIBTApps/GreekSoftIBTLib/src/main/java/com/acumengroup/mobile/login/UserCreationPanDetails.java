package com.acumengroup.mobile.login;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.PanValidation.PanRequest;
import com.acumengroup.greekmain.core.model.PanValidation.ValidatePanResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.trade.DatePickerFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.greekmain.util.Util;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class UserCreationPanDetails extends GreekBaseFragment implements View.OnClickListener, Step, BlockingStep {

    GreekEditText editpan, editdob;
    GreekButton btn_submit;
    String panNo;
    String mobile;
    String dob;
    String from = "";
    private ValidatePanResponse panResponse = null;
    String nextclick = "true";
    boolean nextScreen_flag = false;
    private String blockCharacterSet = "~#@^|$%&@><*!-?+=_()";
    public static final SimpleDateFormat BIRTHDAY_FORMAT_PARSER = new SimpleDateFormat("dd/MM/yyyy");
    private StepperLayout.OnNextClickedCallback OnNextClickedCallback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        attachLayout(R.layout.fragment_userverfication_pandetails);
        AccountDetails.currentFragment = NAV_TO_USERCREATION_PAN_DETAILS;
        setupView(mfActionView);
        if (getArguments() != null) {
            from = getArguments().getString("from");
        }
        mobile = Util.getPrefs(getActivity()).getString("Mobile", "0");


        btn_submit.setOnClickListener(UserCreationPanDetails.this);


        return mfActionView;
    }


    private void setupView(View mfActionView) {
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);

        editdob = mfActionView.findViewById(R.id.ed_dob);
        btn_submit = mfActionView.findViewById(R.id.btn_pan_nxt);
        editpan = mfActionView.findViewById(R.id.ed_pan);

        String submit = Util.getPrefs(getActivity()).getString("panSubmit", "");

        if (!submit.equalsIgnoreCase("yes")) {
            btn_submit.setEnabled(true);
            editpan.setEnabled(true);
            editdob.setEnabled(true);
            btn_submit.setClickable(true);
            editpan.setClickable(true);
            editdob.setClickable(true);

        } else {
            btn_submit.setEnabled(false);
            editpan.setEnabled(false);
            editdob.setEnabled(false);
            btn_submit.setClickable(false);
            editpan.setClickable(false);
            editdob.setClickable(false);

        }

        editdob.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //MAKING ALL ENTERED DATA CAPITAL BYDEFAULT


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


        editdob.setFilters((new InputFilter[]{filter}));
        InputFilter[] lengthfilters = {new InputFilter.LengthFilter(10)};
        editdob.setFilters(lengthfilters);

        editdob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editpan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("Length....!!!!!!!!!!", i2 + "   " + editpan.length());
                if (editpan.length() <= 4) {
                    NumberValidation();
                    editpan.setInputType(InputType.TYPE_CLASS_TEXT);

                } else if (editpan.length() > 4 && editpan.length() <= 8) {
                    editpan.setInputType(InputType.TYPE_CLASS_NUMBER);

                } else if (editpan.length() > 8) {
                    // NumberValidation();
                    editpan.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                if (editpan.length() == 10) {

                    if (Character.isDigit(charSequence.charAt(9))) {
                        editpan.setText(editpan.getText().toString().substring(0, editpan.getText().toString().length() - 1));
                        editpan.setSelection(9);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        editdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showDatePicker();
            }
        });


        editdob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editdob.getRight() - editdob.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (event.getRawX() >= (editdob.getRight() - editdob.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            showDatePicker();

                            return true;
                        }
                    }
                }
                return false;
            }

        });

    }

    private void NumberValidation() {
        if (editpan.getText().toString().matches(".*[0-9].*")) {
            editpan.setText(editpan.getText().toString().substring(0, editpan.getText().toString().length() - 1));

        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_pan_nxt) {

            submitPanDetails();

        }

    }

    private void submitPanDetails() {

        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(editpan.getText().toString());

        if (!matcher.matches()) {
            editpan.setError("Enter valid pan number");
            editpan.requestFocus();

            return;
        } else if (editpan.getText().toString().equals("") || editpan.getText().toString().length() < 10) {
            editpan.setError("Enter valid pan number");
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

        } else if (getAge(editdob.getText().toString()) < 18) {

            editdob.setError("Invalid date of birth ");
            editdob.requestFocus();
            return;

        } else {

            panNo = editpan.getText().toString();
            dob = editdob.getText().toString();

            sendRequest();
        }

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
            editdob.setText(selecteddate);
        }
    };


    private void sendRequest() {

        PanRequest.sendRequest(panNo, dob, mobile, AccountDetails.getcUserType(), getActivity(), serviceResponseHandler);
        showProgress();
    }

    @Override
    public void handleResponse(Object response) {
        hideProgress();

        JSONResponse jsonResponse = (JSONResponse) response;
        try {

            panResponse = (ValidatePanResponse) jsonResponse.getResponse();
            if (panResponse.getPanExists().equalsIgnoreCase("true")) {

                GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), "Pan card already exists", "Ok", false, null);
                nextclick = "false";
                return;
            }

            btn_submit.setBackgroundResource(R.color.grey_text);
            nextclick = "true";
            Log.v("Service Response Pan ", panResponse.getErrorCode() + " " + panResponse.getGender());

            Bundle args = new Bundle();
            args.putString("userExists", panResponse.getUserExists());
            args.putString("clientCode", panResponse.getClientCode());
            args.putString("from", from);

            AccountDetails.setMfUserId(panResponse.getClientCode());
            AccountDetails.setUsername(getMainActivity(), panResponse.getClientCode());
            AccountDetails.setUserPAN(getMainActivity(), panResponse.getPANNo());


            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Pan details submitted", "Ok", true, new GreekDialog.DialogListener() {

                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    if (action == GreekDialog.Action.OK) {

                        nextScreen_flag = true;
                        if (OnNextClickedCallback != null) {
                            onNextClicked(OnNextClickedCallback);
                        }

                    }
                }
            });


            String kycFlag = panResponse.getErrorCode();
            if (kycFlag.equals("2") || kycFlag.equals("0")) {

                String name = panResponse.getName();
                String ErrorCode = panResponse.getErrorCode();
                String panno = panResponse.getPANNo();
                String ipvflag = panResponse.getIPVFlag();
                String gender = panResponse.getGender();

                String dob;
                if (!panResponse.getDob().equalsIgnoreCase("")) {
                    dob = panResponse.getDob();
                } else {
                    dob = editdob.getText().toString();
                }

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
                String peradd3 = panResponse.getPerAddr3();
                String percity = panResponse.getPerCity();
                String perpin = panResponse.getPerPin();
                String perstate = panResponse.getcForState();
                String percountry = panResponse.getPerCntry();
                String corraddressref = panResponse.getCorrAddrRef();
                String peraddref = panResponse.getPerAddrRef();
                String mob = panResponse.getMobNo();

                SharedPreferences.Editor editor = Util.getPrefs(getActivity()).edit();
                editor.putString("name", name);
                editor.putString("errorcode", ErrorCode);
                //dataManager.sendData(ErrorCode);
                editor.putString("panno", panno);
                editor.putString("ipvflag", ipvflag);
                editor.putString("gender", gender);
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
                editor.putString("gname", panResponse.getFName());
                editor.putString("perphone", panResponse.getPerPhone());
                editor.putString("AdharNo", panResponse.getAdharNo());
                editor.putString("MaritalStatus", panResponse.getMaritalStatus());
                editor.putString("Occupation", panResponse.getOccupation());
                editor.putString("gaurdian", panResponse.getGuardian());
                editor.putString("bankname", panResponse.getGetBankDetails().get(0).getcBankName1());
                editor.putString("branch", panResponse.getGetBankDetails().get(0).getcBankBranch1());
                editor.putString("accno", panResponse.getGetBankDetails().get(0).getcAccNoNo1());
                editor.putString("acctype", panResponse.getGetBankDetails().get(0).getcAccType1());
                editor.putString("ifcs", panResponse.getGetBankDetails().get(0).getcNEFTIFSCCode1());
                editor.putString("flag", panResponse.getGetBankDetails().get(0).getcDefaultBankFlag1());
                editor.putString("nomName", panResponse.getGetBankDetails().get(0).getcClientNominee());
                editor.putString("nomRel", panResponse.getGetBankDetails().get(0).getcClientNomineeRelation());
                editor.putString("validate", "yes");
                editor.putString("panSubmit", "yes");
                editor.commit();

            } else {

                String name1 = panResponse.getName();
                String ErrorCode1 = panResponse.getErrorCode();
                String panno = panResponse.getPANNo();
                String ipvflag = panResponse.getIPVFlag();
                String gender = panResponse.getGender();

                String dob;
                if (!panResponse.getDob().equalsIgnoreCase("")) {

                    dob = panResponse.getDob();

                } else {
                    dob = editdob.getText().toString();

                }
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

                SharedPreferences.Editor editor = Util.getPrefs(getActivity()).edit();
                editor.putString("name", name1);
                editor.putString("errorcode", ErrorCode1);
                //dataManager.sendData(ErrorCode);
                editor.putString("panno", panno);
                editor.putString("ipvflag", ipvflag);
                editor.putString("gender", gender);
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

                if (panResponse.getUserExists().equalsIgnoreCase("true")) {
                    editor.putString("bankname", panResponse.getcBankName1());
                    editor.putString("branch", panResponse.getcBankBranch1());
                    editor.putString("accno", panResponse.getcAccNoNo1());
                    editor.putString("acctype", panResponse.getcAccType1());
                    editor.putString("ifcs", panResponse.getcNEFTIFSCCode1());
                    editor.putString("flag", panResponse.getcDefaultBankFlag1());
                    editor.putString("nomName", panResponse.getcClientNominee());
                    editor.putString("nomRel", panResponse.getcClientNomineeRelation());
                }

                editor.putString("validate", "yes");
                editor.putString("panSubmit", "yes");
                editor.apply();
                editor.commit();

            }

            btn_submit.setClickable(false);
            editpan.setClickable(false);
            editdob.setClickable(false);
            btn_submit.setEnabled(false);
            editpan.setEnabled(false);
            editdob.setEnabled(false);

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
    public void onNextClicked(StepperLayout.OnNextClickedCallback onNextClickedCallback) {

        OnNextClickedCallback = onNextClickedCallback;

        if (nextclick.equals("false")) {

            submitPanDetails();
            //return;
        } else {
            if (nextScreen_flag) {

                onNextClickedCallback.goToNextStep();

            } else {
                submitPanDetails();

            }
        }

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback onCompleteClickedCallback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback onBackClickedCallback) {

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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

}

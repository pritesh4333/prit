package in.co.vyapari.ui.activity.firm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.VyapariApp;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.CommonService;
import in.co.vyapari.middleware.service.FirmService;
import in.co.vyapari.model.Address;
import in.co.vyapari.model.Bank;
import in.co.vyapari.model.Contact;
import in.co.vyapari.model.Employee;
import in.co.vyapari.model.Firm;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.FirmDTO;
import in.co.vyapari.model.response.dto.GstinDTO;
import in.co.vyapari.ui.activity.common.address.AddressesActivity;
import in.co.vyapari.ui.activity.common.bank.BanksActivity;
import in.co.vyapari.ui.activity.common.employee.EmployeesActivity;
import in.co.vyapari.ui.adapter.MiniContactAdapter;
import in.co.vyapari.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.generic.DividerItemDecoration;
import in.co.vyapari.ui.generic.MyItemSelectedListener;
import in.co.vyapari.ui.generic.MySpinner;
import in.co.vyapari.ui.listener.RemoveListener;
import in.co.vyapari.util.Utils;
import in.co.vyapari.util.ValidateUtil;

public class CreateFirmActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText firmNameET;
    @BindView(R.id.create_firm_type_sp)
    MySpinner typeSP;
    @BindView(R.id.create_firm_gst_registration_type_sp)
    MySpinner gstTypeSP;
    @BindView(R.id.create_firm_address)
    EditText addressET;
    @BindView(R.id.create_firm_country_sp)
    MySpinner countrySP;
    @BindView(R.id.create_firm_state_area)
    LinearLayout stateLL;
    @BindView(R.id.create_firm_state_sp)
    MySpinner stateSP;
    @BindView(R.id.create_firm_city_dist)
    LinearLayout cityDistLL;
    @BindView(R.id.create_firm_city_sp)
    MySpinner citySP;
    @BindView(R.id.create_firm_district_ll)
    LinearLayout districtLL;
    @BindView(R.id.create_firm_district_sp)
    MySpinner districtSP;
    @BindView(R.id.create_firm_gstin)
    Button gstinBT;
    @BindView(R.id.create_firm_pan)
    EditText panET;
    @BindView(R.id.create_firm_phone_rv)
    RecyclerView phoneRV;
    @BindView(R.id.create_firm_email_rv)
    RecyclerView emailRV;
    @BindView(R.id.create_firm_note)
    EditText firmNoteET;
    @BindView(R.id.create_firm_employee_count)
    TextView employeeCountTV;
    @BindView(R.id.create_firm_address_count)
    TextView addressCountTV;
    @BindView(R.id.create_firm_account_count)
    TextView bankAccountCountTV;

    private Context mContext;
    private ArrayList<KeyValue> gstTypes;
    private ArrayList<KeyValue> countries;
    private ArrayList<KeyValue> states;
    private ArrayList<KeyValue> cities;
    private ArrayList<KeyValue> districts;
    private boolean isIndia;

    private MiniContactAdapter phoneAdapter;
    private MiniContactAdapter emailAdapter;

    private ArrayList<Contact> contactList = new ArrayList<>();
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private ArrayList<Address> addressList = new ArrayList<>();
    private ArrayList<Bank> bankList = new ArrayList<>();
    private Firm firm = new Firm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_firm);
        ButterKnife.bind(this);
        mContext = this;

        setDetailToolbarConfig(R.string.add_ar_ap, R.drawable.ico_mini_ar_ap, R.string.ar_ap_name);

        CommonService.getCountries(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                lockSpinner(countrySP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                unlockSpinner(countrySP);
                ArrayList<KeyValue> data = response.getData();
                if (data == null) {
                    data = new ArrayList<>();
                }
                countries = data;
                setGSTTypeConfig();
                setCountriesConfig();
                setRecyclerViewConfig();
                setCounterConfig();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    private void setGSTTypeConfig() {
        gstTypes = Utils.getGSTRegistrationTypes();
        gstTypeSP.setAdapter(new OneLineSpinnerAdapter(mContext, gstTypes));
    }

    private void setCountriesConfig() {
        countrySP.setAdapter(new OneLineSpinnerAdapter(mContext, countries));
        countrySP.setOnItemSelectedListener(new MyItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                Utils.hideKeyboard(mContext);
                if (Utils.equalsKeyValue(countries.get(position), MobileConstants.defaultCountry)) {
                    isIndia = true;
                    cityDistLL.setVisibility(View.VISIBLE);
                    stateLL.setVisibility(View.VISIBLE);

                    String selectedParentId = countries.get(position).getKey();
                    CommonService.getStates(selectedParentId, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
                        @Override
                        public void start() {
                            lockSpinner(stateSP);
                        }

                        @Override
                        public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                            unlockSpinner(stateSP);

                            ArrayList<KeyValue> data = response.getData();
                            if (data == null) {
                                data = new ArrayList<>();
                            }
                            states = data;
                            setStatesConfig();
                        }

                        @Override
                        public void onFailure(boolean isOnline, Throwable throwable) {

                        }
                    });
                } else {
                    isIndia = false;
                    cityDistLL.setVisibility(View.GONE);
                    stateLL.setVisibility(View.GONE);
                }
            }
        });

        for (int i = 0; i < countries.size(); i++) {
            if (Utils.equalsKeyValue(countries.get(i), MobileConstants.defaultCountry)) {
                countrySP.setSelection(i);
                break;
            }
        }
    }

    private void setStatesConfig() {
        stateSP.setAdapter(new OneLineSpinnerAdapter(mContext, states));
        stateSP.setOnItemSelectedListener(new MyItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                Utils.hideKeyboard(mContext);
                String selectedParentId = states.get(position).getKey();
                CommonService.getCities(selectedParentId, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
                    @Override
                    public void start() {
                        lockSpinner(citySP);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                        unlockSpinner(citySP);
                        ArrayList<KeyValue> data = response.getData();
                        if (data == null) {
                            data = new ArrayList<>();
                        }
                        cities = data;
                        setCitiesConfig();
                    }

                    @Override
                    public void onFailure(boolean isOnline, Throwable throwable) {

                    }
                });
            }
        });
        stateSP.setSelection(0);
    }

    private void setCitiesConfig() {
        citySP.setAdapter(new OneLineSpinnerAdapter(mContext, cities));
        citySP.setOnItemSelectedListener(new MyItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                Utils.hideKeyboard(mContext);
                String selectedParentId = cities.get(position).getKey();
                CommonService.getDistricts(selectedParentId, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
                    @Override
                    public void start() {
                        lockSpinner(districtSP);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                        unlockSpinner(districtSP);
                        districts = response.getData();
                        setDistrictsConfig();
                    }

                    @Override
                    public void onFailure(boolean isOnline, Throwable throwable) {

                    }
                });
            }
        });
        citySP.setSelection(0);
    }

    private void setDistrictsConfig() {
        if (districts.size() < 2) {
            districtLL.setVisibility(View.INVISIBLE);
        } else {
            districtLL.setVisibility(View.VISIBLE);
        }
        districtSP.setAdapter(new OneLineSpinnerAdapter(mContext, districts));
    }

    private void setRecyclerViewConfig() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        phoneRV.setItemAnimator(new DefaultItemAnimator());
        phoneRV.setHasFixedSize(true);
        phoneRV.setNestedScrollingEnabled(false);
        phoneRV.setLayoutManager(mLayoutManager);
        phoneRV.addItemDecoration(new DividerItemDecoration(mContext));

        GridLayoutManager mLayoutManager2 = new GridLayoutManager(mContext, 1);
        emailRV.setItemAnimator(new DefaultItemAnimator());
        emailRV.setHasFixedSize(true);
        emailRV.setNestedScrollingEnabled(false);
        emailRV.setLayoutManager(mLayoutManager2);
        emailRV.addItemDecoration(new DividerItemDecoration(mContext));
    }

    private void setCounterConfig() {
        employeeCountTV.setText(String.valueOf(employeeList.size()));
        addressCountTV.setText(String.valueOf(addressList.size()));
        bankAccountCountTV.setText(String.valueOf(bankList.size()));
    }


    @OnClick(R.id.create_firm_gstin)
    public void gstinValidClick() {
        gstinValid(gstinBT.getText().toString());
    }

    private void gstinValid(String gstin) {
        Utils.generateInputDialog(mContext, getString(R.string.GSTIN), getString(R.string.gstin_msg),
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
                null, gstin,
                getString(R.string.validate), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        String gstin = input.toString();
                        gstin = gstin.replaceAll("[-\\[\\]^/,'*:.!><~@#;$%=?|\"\\\\()]+", "");
                        gstinValidCall(gstin);
                    }
                },
                getString(R.string.delete), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        gstinBT.setText("");
                        panET.setText("");
                        panET.setEnabled(true);
                    }
                },
                getString(R.string.cancel), null);
    }

    private void gstinValidCall(final String gstin) {
        Utils.showLoading(mContext);
        CommonService.validGSTIN(gstin, new ServiceCall<BaseModel<GstinDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<GstinDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    GstinDTO gstinDTO = response.getData();
                    firmNameET.setText(gstinDTO.getName());
                    gstinBT.setText(gstinDTO.getTaxNumber());
                    panET.setText(gstinDTO.getPanNumber());
                    panET.setEnabled(false);

                    String msg = String.format("%s \"%s\"", getString(R.string.firm_updated_msg), gstinDTO.getName());
                    Toasty.success(mContext, msg).show();
                } else {
                    Toasty.error(mContext, response.getMessage()).show();
                    gstinValid(gstin);
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    @OnClick(R.id.create_firm_new_phone)
    public void createPhoneNumberClick() {
        Utils.generateInputDialog(mContext, getString(R.string.add_phone),
                getString(R.string.add_phone_content),
                InputType.TYPE_CLASS_PHONE,
                getString(R.string.add_phone_hint), null,
                getString(R.string.add),
                new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        String phoneNumber = input.toString();
                        phoneNumber = phoneNumber.replaceAll("[-\\[\\]^/,'*:.!><~@#;$%=N ?|\"\\\\()]+", "");
                        if (phoneNumber.length() > 1) {
                            String temp = phoneNumber.substring(1, phoneNumber.length());
                            temp = temp.replace("+", "");
                            phoneNumber = phoneNumber.substring(0, 1) + temp;
                        }
                        if (!phoneNumber.equals(Constants.EMPTY)) {
                            addInfoConfig(phoneNumber, Constants.TYPE_PHONE);
                        } else {
                            Toasty.warning(mContext, getString(R.string.warn_empty_phone)).show();
                        }
                    }
                });
    }

    @OnClick(R.id.create_firm_new_email)
    public void createEmailClick() {
        Utils.generateInputDialog(mContext, getString(R.string.add_email),
                getString(R.string.add_email_content),
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                getString(R.string.add_email_hint), null,
                getString(R.string.add),
                new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        String email = input.toString();
                        if (ValidateUtil.validateEmail(email)) {
                            addInfoConfig(email, Constants.TYPE_EMAIL);
                        } else {
                            Toasty.warning(mContext, getString(R.string.warn_empty_email)).show();
                        }
                    }
                });
    }

    private void addInfoConfig(String data, int type) {
        Contact contact = new Contact(type, data);
        contactList.add(contact);

        RemoveListener<Contact> removeListener = new RemoveListener<Contact>() {
            @Override
            public void remove(Contact contact) {
                contactList.remove(contact);
            }
        };

        if (type == Constants.TYPE_PHONE) {
            phoneAdapter = new MiniContactAdapter(contactList, Constants.TYPE_PHONE, removeListener);
            phoneRV.setAdapter(phoneAdapter);
        } else {
            emailAdapter = new MiniContactAdapter(contactList, Constants.TYPE_EMAIL, removeListener);
            emailRV.setAdapter(emailAdapter);
        }
    }

    @OnClick(R.id.create_firm_employee_rl)
    public void employeesClick() {
        Bundle bundle = new Bundle();
        Intent employeeIntent = new Intent(mContext, EmployeesActivity.class);
        bundle.putParcelableArrayList(Constants.EMPLOYEE_LIST, employeeList);
        employeeIntent.putExtras(bundle);
        startActivityForResult(employeeIntent, Constants.EMPLOYEE_CODE);
    }

    @OnClick(R.id.create_firm_address_rl)
    public void addressesClick() {
        Bundle bundle = new Bundle();
        Intent addressIntent = new Intent(mContext, AddressesActivity.class);
        bundle.putParcelableArrayList(Constants.ADDRESS_LIST, addressList);
        addressIntent.putExtras(bundle);
        startActivityForResult(addressIntent, Constants.ADDRESS_CODE);
    }

    @OnClick(R.id.create_firm_bank_rl)
    public void banksClick() {
        Bundle bundle = new Bundle();
        Intent bankIntent = new Intent(mContext, BanksActivity.class);
        bundle.putParcelableArrayList(Constants.BANK_LIST, bankList);
        bankIntent.putExtras(bundle);
        startActivityForResult(bankIntent, Constants.BANK_CODE);
    }

    @OnClick(R.id.create_firm_submit_button)
    public void saveFirmClick() {
        boolean isValid = ValidateUtil.validateEditTexts(firmNameET, addressET);
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        String firmName = firmNameET.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        KeyValue country = countries.get(countrySP.getSelectedItemPosition());

        KeyValue state = null;
        KeyValue city = null;
        KeyValue district = null;
        if (isIndia) {
            state = states.get(stateSP.getSelectedItemPosition());
            city = cities.get(citySP.getSelectedItemPosition());
            district = districts.get(districtSP.getSelectedItemPosition());
        }

        String gstin = gstinBT.getText().toString().trim();
        String pan = panET.getText().toString().trim();
        String firmNote = firmNoteET.getText().toString().trim();

        int gstType = Integer.valueOf(gstTypes.get(gstTypeSP.getSelectedItemPosition()).getKey());

        if (pan.length() != 0 && pan.length() != 10) {
            Toasty.error(mContext, getString(R.string.warn_pan_length_msg)).show();
            return;
        }

        firm.setName(firmName);
        firm.setGSTRegistrationType(gstType);
        firm.setAddress(address);
        firm.setCountry(country);
        firm.setState(isIndia ? state : null);
        firm.setCity(isIndia ? city : null);
        firm.setDistrict(isIndia ? district : null);
        firm.setGSTIN(gstin);
        firm.setPAN(pan);
        firm.setNote(firmNote);
        firm.setContacts(contactList);
        //firm.setEmployees(employeeList);
        firm.setShippingAddresses(addressList);
        firm.setBanks(bankList);
        firm.setActive(true);

        addFirmCall();
    }

    private void addFirmCall() {
        Utils.showLoading(mContext);
        FirmService.addFirm(firm, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    returnResultFinish();
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.EMPLOYEE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Employee> employeeArrayList = data.getParcelableArrayListExtra(Constants.EMPLOYEE_LIST);
                if (employeeArrayList != null) {
                    employeeList = new ArrayList<>();
                    employeeList.addAll(employeeArrayList);
                    employeeCountTV.setText(String.valueOf(employeeList.size()));
                }
            }
        } else if (requestCode == Constants.ADDRESS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Address> addressArrayList = data.getParcelableArrayListExtra(Constants.ADDRESS_LIST);
                if (addressArrayList != null) {
                    addressList = new ArrayList<>();
                    addressList.addAll(addressArrayList);
                    addressCountTV.setText(String.valueOf(addressList.size()));
                }
            }
        } else if (requestCode == Constants.BANK_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Bank> bankArrayList = data.getParcelableArrayListExtra(Constants.BANK_LIST);
                if (bankArrayList != null) {
                    bankList = new ArrayList<>();
                    bankList.addAll(bankArrayList);
                    bankAccountCountTV.setText(String.valueOf(bankList.size()));
                }
            }
        }
    }

    private void returnResultFinish() {
        MobileConstants.firmId = "id";
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.REFRESH, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
package in.co.vyapari.ui.activity.management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.CommonService;
import in.co.vyapari.middleware.service.UserService;
import in.co.vyapari.model.Address;
import in.co.vyapari.model.Bank;
import in.co.vyapari.model.Company;
import in.co.vyapari.model.Contact;
import in.co.vyapari.model.Employee;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.User;
import in.co.vyapari.model.request.ImageUpload;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.CompanyDTO;
import in.co.vyapari.model.response.dto.LoginDTO;
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
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.Utils;
import in.co.vyapari.util.ValidateUtil;

public class EditCompanyActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText companyNameET;
    @BindView(R.id.company_logo_iv)
    ImageView companyLogoIV;
    @BindView(R.id.logo_area_ll)
    LinearLayout logoAreaLL;
    @BindView(R.id.edit_profile_fullname)
    EditText fullnameET;
    @BindView(R.id.edit_profile_email)
    EditText emailET;
    @BindView(R.id.edit_company_address)
    EditText addressET;
    @BindView(R.id.edit_company_country_sp)
    MySpinner countrySP;
    @BindView(R.id.edit_company_state_area)
    LinearLayout stateLL;
    @BindView(R.id.edit_company_state_sp)
    MySpinner stateSP;
    @BindView(R.id.edit_company_city_dist)
    LinearLayout cityDistLL;
    @BindView(R.id.edit_company_city_sp)
    MySpinner citySP;
    @BindView(R.id.edit_company_district_ll)
    LinearLayout districtLL;
    @BindView(R.id.edit_company_district_sp)
    MySpinner districtSP;
    @BindView(R.id.edit_company_gstin)
    EditText gstinET;
    @BindView(R.id.edit_company_pan)
    EditText panET;
    @BindView(R.id.edit_company_new_phone_ll)
    LinearLayout phoneAreaLL;
    @BindView(R.id.edit_company_new_phone)
    LinearLayout phoneAddLL;
    @BindView(R.id.edit_company_phone_rv)
    RecyclerView phoneRV;
    @BindView(R.id.edit_company_new_email_ll)
    LinearLayout emailAreaLL;
    @BindView(R.id.edit_company_new_email)
    LinearLayout emailAddLL;
    @BindView(R.id.edit_company_email_rv)
    RecyclerView emailRV;
    @BindView(R.id.edit_company_note)
    EditText companyNoteET;
    @BindView(R.id.edit_company_employee_count)
    TextView employeeCountTV;
    @BindView(R.id.edit_company_warehouse_line)
    ImageView warehouseLineIV;
    @BindView(R.id.edit_company_warehouse_rl)
    RelativeLayout warehouseRL;
    @BindView(R.id.edit_company_warehouse_count)
    TextView warehouseCountTV;
    @BindView(R.id.edit_company_account_count)
    TextView bankAccountCountTV;
    @BindView(R.id.edit_company_submit_button)
    Button saveButton;

    private Context mContext;
    private Company companyRequest;
    private CompanyDTO companyDTO;
    private User user;
    private ArrayList<KeyValue> countries;
    private ArrayList<KeyValue> states;
    private ArrayList<KeyValue> cities;
    private ArrayList<KeyValue> districts;
    private boolean isIndia;

    private MiniContactAdapter phoneAdapter;
    private MiniContactAdapter emailAdapter;

    private ArrayList<Contact> contactList = new ArrayList<>();
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private ArrayList<Address> warehouseList = new ArrayList<>();
    private ArrayList<Bank> bankList = new ArrayList<>();

    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_company);
        ButterKnife.bind(this);
        mContext = this;

        setDetailToolbarConfig(R.string.company_info, R.string.company_name);

        LoginDTO loginDTO = DataUtil.getBundle(LoginDTO.class);
        companyDTO = loginDTO.getCompany();
        user = loginDTO.getUser();

        if (companyDTO != null) {
            updateUILocal();
        } else {
            companyDTO = new CompanyDTO();
        }

        companyRequest = new Company();

        CommonService.getCountries(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                lockSpinner(countrySP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                ArrayList<KeyValue> data = response.getData();
                if (data == null) {
                    data = new ArrayList<>();
                }

                if (companyDTO.isCanEditable()) {
                    unlockSpinner(countrySP);
                } else {
                    if (data.size() > 0) {
                        data.remove(0);
                        KeyValue kv = new KeyValue(null, "-");
                        data.add(0, kv);
                    }

                    phoneAreaLL.setVisibility(View.GONE);
                    emailAreaLL.setVisibility(View.GONE);
                }
                countries = data;
                setCountriesConfig();
                setRecyclerViewConfig();
                setCounterConfig();
                setInfoConfig();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    private void updateUILocal() {
        companyNameET.setText(companyDTO.getName());
        companyNameET.setSelection(companyNameET.getText().toString().length());
        fullnameET.setText(user.getFullName());
        emailET.setText(user.getEmail());
        addressET.setText(companyDTO.getAddress());
        gstinET.setText(companyDTO.getGSTIN());
        panET.setText(companyDTO.getPAN());
        companyNoteET.setText(companyDTO.getNote());

        if (companyDTO.getContacts() != null) {
            contactList = companyDTO.getContacts();
        }
        /*if (companyDTO.getEmployees() != null) {
            employeeList = companyDTO.getEmployees();
        }*/
        if (companyDTO.getWarehouses() != null) {
            warehouseList = companyDTO.getWarehouses();
        }
        if (companyDTO.getBanks() != null) {
            bankList = companyDTO.getBanks();
        }

        updateCompanyLogo();

        lockSpinner(countrySP);
        lockSpinner(stateSP);
        lockSpinner(citySP);
        lockSpinner(districtSP);


        boolean isCanEditable = companyDTO.isCanEditable();
        companyNameET.setEnabled(isCanEditable);
        fullnameET.setEnabled(isCanEditable);
        addressET.setEnabled(isCanEditable);
        companyNoteET.setEnabled(isCanEditable);

        if (!isCanEditable) {
            phoneAreaLL.setVisibility(View.GONE);
            emailAreaLL.setVisibility(View.GONE);
            warehouseLineIV.setVisibility(View.GONE);
            warehouseRL.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            countrySP.setBackground(null);
            stateSP.setBackground(null);
            citySP.setBackground(null);
            districtSP.setBackground(null);
        }
    }

    private void updateCompanyLogo() {
        Bitmap companyLogo = Utils.loadCompanyLogo(mContext);
        if (companyLogo != null) {
            logoAreaLL.setVisibility(View.GONE);
            companyLogoIV.setVisibility(View.VISIBLE);
            companyLogoIV.setImageBitmap(companyLogo);
        } else {
            logoAreaLL.setVisibility(View.VISIBLE);
            companyLogoIV.setVisibility(View.GONE);
        }
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
                            ArrayList<KeyValue> data = response.getData();
                            if (data == null) {
                                data = new ArrayList<>();
                            }

                            if (companyDTO.isCanEditable()) {
                                unlockSpinner(stateSP);
                            } else {
                                if (data.size() > 0) {
                                    data.remove(0);
                                    KeyValue kv = new KeyValue(null, "-");
                                    data.add(0, kv);
                                }
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

        if (companyDTO.getCountry() == null) {
            countrySP.setSelection(0);
        } else {
            for (int i = 0; i < countries.size(); i++) {
                if (Utils.equalsKeyValue(countries.get(i), companyDTO.getCountry())) {
                    countrySP.setSelection(i);
                    break;
                }
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
                        ArrayList<KeyValue> data = response.getData();
                        if (data == null) {
                            data = new ArrayList<>();
                        }

                        if (companyDTO.isCanEditable()) {
                            unlockSpinner(citySP);
                        } else {
                            if (data.size() > 0) {
                                data.remove(0);
                                KeyValue kv = new KeyValue(null, "-");
                                data.add(0, kv);
                            }
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

        if (companyDTO.getState() == null) {
            stateSP.setSelection(0);
        } else {
            for (int i = 0; i < states.size(); i++) {
                if (Utils.equalsKeyValue(states.get(i), companyDTO.getState())) {
                    stateSP.setSelection(i);
                    break;
                }
            }
        }
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
                        ArrayList<KeyValue> data = response.getData();
                        if (data == null) {
                            data = new ArrayList<>();
                        }

                        if (companyDTO.isCanEditable()) {
                            unlockSpinner(districtSP);
                        } else {
                            if (data.size() > 0) {
                                data.remove(0);
                                KeyValue kv = new KeyValue(null, "-");
                                data.add(0, kv);
                            }
                        }
                        districts = data;
                        setDistrictsConfig();
                    }

                    @Override
                    public void onFailure(boolean isOnline, Throwable throwable) {

                    }
                });
            }
        });

        if (companyDTO.getCity() == null) {
            citySP.setSelection(0);
        } else {
            for (int i = 0; i < cities.size(); i++) {
                if (Utils.equalsKeyValue(cities.get(i), companyDTO.getCity())) {
                    citySP.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setDistrictsConfig() {
        if (districts.size() < 2) {
            districtLL.setVisibility(View.INVISIBLE);
        } else {
            districtLL.setVisibility(View.VISIBLE);
        }
        districtSP.setAdapter(new OneLineSpinnerAdapter(mContext, districts));

        if (companyDTO.getDistrict() == null) {
            districtSP.setSelection(0);
        } else {
            for (int i = 0; i < districts.size(); i++) {
                if (Utils.equalsKeyValue(districts.get(i), companyDTO.getDistrict())) {
                    final int selectedDistrictPosition = i;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            districtSP.setSelection(selectedDistrictPosition);
                        }
                    }, 100);
                    break;
                }
            }
        }
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
        warehouseCountTV.setText(String.valueOf(warehouseList.size()));
        bankAccountCountTV.setText(String.valueOf(bankList.size()));
    }

    private void setInfoConfig() {
        RemoveListener<Contact> removeListener = new RemoveListener<Contact>() {
            @Override
            public void remove(Contact contact) {
                contactList.remove(contact);
                infoRefresh();
            }
        };

        phoneAdapter = new MiniContactAdapter(contactList, Constants.TYPE_PHONE, removeListener);
        phoneRV.setAdapter(phoneAdapter);
        emailAdapter = new MiniContactAdapter(contactList, Constants.TYPE_EMAIL, removeListener);
        emailRV.setAdapter(emailAdapter);

        infoRefresh();
    }

    private void infoRefresh() {
        if (phoneAdapter.getItemCount() > 0) {
            phoneAddLL.setVisibility(View.GONE);
        } else {
            phoneAddLL.setVisibility(View.VISIBLE);
        }

        if (emailAdapter.getItemCount() > 0) {
            emailAddLL.setVisibility(View.GONE);
        } else {
            emailAddLL.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.company_logo_iv, R.id.company_logo_rl})
    public void selectProfilePhotoClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, Constants.PERMISSION_READ);
        } else {
            selectProfilePhoto();
        }
    }

    private void selectProfilePhoto() {
        if (companyDTO.isCanEditable()) {
            Utils.generateDialog(mContext, getString(R.string.company_logo), null,
                    getString(R.string.change), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            openGallery();
                        }
                    },
                    getString(R.string.remove), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            removeLogoCall();
                        }
                    },
                    getString(R.string.cancel), null);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), Constants.GALLERY_CODE);
    }

    @OnClick(R.id.edit_company_new_phone)
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
                        if (!phoneNumber.equals(Constants.EMPTY)) {
                            addInfoConfig(phoneNumber, Constants.TYPE_PHONE);
                        } else {
                            Toasty.warning(mContext, getString(R.string.warn_empty_phone)).show();
                        }
                    }
                });
    }

    @OnClick(R.id.edit_company_new_email)
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
                infoRefresh();
            }
        };

        if (type == Constants.TYPE_PHONE) {
            phoneAdapter = new MiniContactAdapter(contactList, Constants.TYPE_PHONE, removeListener);
            phoneRV.setAdapter(phoneAdapter);
        } else {
            emailAdapter = new MiniContactAdapter(contactList, Constants.TYPE_EMAIL, removeListener);
            emailRV.setAdapter(emailAdapter);
        }

        infoRefresh();
    }

    @OnClick(R.id.edit_company_employee_rl)
    public void employeesClick() {
        Bundle bundle = new Bundle();
        Intent employeeIntent = new Intent(mContext, EmployeesActivity.class);
        bundle.putParcelableArrayList(Constants.EMPLOYEE_LIST, employeeList);
        employeeIntent.putExtras(bundle);
        startActivityForResult(employeeIntent, Constants.EMPLOYEE_CODE);
    }

    @OnClick(R.id.edit_company_warehouse_rl)
    public void addressesClick() {
        Bundle bundle = new Bundle();
        Intent addressIntent = new Intent(mContext, AddressesActivity.class);
        bundle.putParcelableArrayList(Constants.ADDRESS_LIST, warehouseList);
        bundle.putBoolean(Constants.WAREHOUSE, true);
        addressIntent.putExtras(bundle);
        startActivityForResult(addressIntent, Constants.ADDRESS_CODE);
    }

    @OnClick(R.id.edit_company_bank_rl)
    public void banksClick() {
        Bundle bundle = new Bundle();
        Intent bankIntent = new Intent(mContext, BanksActivity.class);
        bundle.putParcelableArrayList(Constants.BANK_LIST, bankList);
        bankIntent.putExtras(bundle);
        startActivityForResult(bankIntent, Constants.BANK_CODE);
    }

    @OnClick(R.id.edit_company_submit_button)
    public void saveCompanyClick() {
        boolean isValid = ValidateUtil.validateEditTexts(companyNameET, addressET, gstinET);
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        String companyName = companyNameET.getText().toString().trim();
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

        String gstin = gstinET.getText().toString().trim();
        String pan = panET.getText().toString().trim();
        String companyNote = companyNoteET.getText().toString().trim();

        companyRequest.setId(companyDTO.getId());
        companyRequest.setName(companyName);
        companyRequest.setAddress(address);
        companyRequest.setCountry(country);
        companyRequest.setState(isIndia ? state : null);
        companyRequest.setCity(isIndia ? city : null);
        companyRequest.setDistrict(isIndia ? district : null);
        companyRequest.setGSTIN(gstin);
        companyRequest.setPAN(pan);
        companyRequest.setNote(companyNote);
        companyRequest.setContacts(contactList);
        //companyRequest.setEmployees(employeeList);
        companyRequest.setWarehouses(warehouseList);
        companyRequest.setBanks(bankList);
        companyRequest.setActive(true);

        updateCompanyCall(companyRequest);
    }

    private void updateCompanyCall(Company companyRequest) {
        Utils.showLoading(mContext);
        UserService.updateCompany(companyRequest, new ServiceCall<BaseModel<CompanyDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<CompanyDTO> response) {
                Utils.hideLoading();
                if (response.getData() != null) {
                    companyDTO = response.getData();
                    if (companyDTO != null) {
                        LoginDTO loginDTO = DataUtil.getBundleAndRemove(LoginDTO.class);
                        loginDTO.setCompany(companyDTO);
                        DataUtil.post(loginDTO);
                        Toasty.success(mContext, response.getMessage()).show();
                        returnResultFinish();
                    } else {
                        Toasty.error(mContext, getString(R.string.error)).show();
                    }
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
                    warehouseList = new ArrayList<>();
                    warehouseList.addAll(addressArrayList);
                    warehouseCountTV.setText(String.valueOf(warehouseList.size()));
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
        } else if (requestCode == Constants.GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri currentImageURI = data.getData();
                String selectedImagePath = Utils.getPath(getBaseContext(), currentImageURI);
                logoAreaLL.setVisibility(View.GONE);

                Bitmap _bitmap = Utils.decodeSampledBitmapFromResource(selectedImagePath, 400, 400);
                Bitmap companyLogo;
                try {
                    companyLogo = Utils.modifyOrientation(_bitmap, selectedImagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    companyLogo = _bitmap;
                }
                companyLogoIV.setImageBitmap(companyLogo);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                companyLogo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] profilePhotoBtye = baos.toByteArray();
                encodedImage = Base64.encodeToString(profilePhotoBtye, Base64.DEFAULT);

                String type = selectedImagePath.substring(selectedImagePath.length() - 3, selectedImagePath.length());
                String emailHash = Utils.convertToMd5(user.getEmail());
                String fileName = emailHash + "." + type;

                ImageUpload imageUpload = new ImageUpload(fileName, encodedImage);
                uploadLogoCall(imageUpload);
            }
        }
    }

    private void uploadLogoCall(ImageUpload imageUpload) {
        Utils.showLoading(mContext);
        UserService.uploadLogo(imageUpload, new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                Utils.hideLoading();
                Utils.saveCompanyLogo(mContext, encodedImage);
                updateCompanyLogo();
                Toasty.success(mContext, response.getMessage()).show();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
            }
        });
    }

    private void removeLogoCall() {
        Utils.showLoading(mContext);
        UserService.removeLogo(new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                Utils.hideLoading();
                Utils.saveCompanyLogo(mContext, null);
                updateCompanyLogo();
                Toasty.success(mContext, response.getMessage()).show();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_READ: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectProfilePhoto();
                } else {
                    Toasty.error(mContext, getString(R.string.permission_warn)).show();
                }
            }
        }
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.REFRESH, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
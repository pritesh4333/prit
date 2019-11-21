package in.co.vyaparienterprise.ui.activity.common.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.VyapariApp;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.CommonService;
import in.co.vyaparienterprise.middleware.service.FirmService;
import in.co.vyaparienterprise.model.Address;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.MyItemSelectedListener;
import in.co.vyaparienterprise.ui.generic.MySpinner;
import in.co.vyaparienterprise.util.Utils;
import in.co.vyaparienterprise.util.ValidateUtil;

public class EditAddressActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText addressNameET;
    @BindView(R.id.edit_address_address)
    EditText addressET;
    @BindView(R.id.edit_address_country_sp)
    MySpinner countrySP;
    @BindView(R.id.edit_address_state_area)
    LinearLayout stateLL;
    @BindView(R.id.edit_address_state_sp)
    MySpinner stateSP;
    @BindView(R.id.edit_address_city_dist)
    LinearLayout cityDistLL;
    @BindView(R.id.edit_address_city_sp)
    MySpinner citySP;
    @BindView(R.id.edit_address_district_ll)
    LinearLayout districtLL;
    @BindView(R.id.edit_address_district_sp)
    MySpinner districtSP;

    private Context mContext;
    private ArrayList<KeyValue> countries;
    private ArrayList<KeyValue> states;
    private ArrayList<KeyValue> cities;
    private ArrayList<KeyValue> districts;
    private boolean isIndia;

    private boolean isWarehouse;
    private Address address = new Address();
    private String firmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_firm_address);
        ButterKnife.bind(this);
        mContext = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            address = bundle.getParcelable(Constants.ADDRESS);
            firmId = bundle.getString(Constants.FIRM_ID);
            isWarehouse = bundle.getBoolean(Constants.WAREHOUSE);

            addressNameET.setText(address.getDescription());
            addressET.setText(address.getAddressText());
        }

        if (isWarehouse) {
            setDetailToolbarConfig(R.string.edit_warehouse, R.drawable.ico_mini_address, R.string.warehouse_header);
        } else {
            setDetailToolbarConfig(R.string.edit_delivery_address, R.drawable.ico_mini_address, R.string.address_header);
        }

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
                setCountriesConfig();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }


    private void setCountriesConfig() {
        countrySP.setAdapter(new OneLineSpinnerAdapter(mContext, countries));
        countrySP.setOnItemSelectedListener(new MyItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                Utils.hideKeyboard(mContext);
                if (Utils.equalsKeyValue(countries.get(position), address.getCountry())) {
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

        if (address.getCountry() == null) {
            countrySP.setSelection(0);
        } else {
            for (int i = 0; i < countries.size(); i++) {
                if (Utils.equalsKeyValue(countries.get(i), address.getCountry())) {
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

        if (address.getState() == null) {
            stateSP.setSelection(0);
        } else {
            for (int i = 0; i < states.size(); i++) {
                if (Utils.equalsKeyValue(states.get(i), address.getState())) {
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

        if (address.getCity() == null) {
            citySP.setSelection(0);
        } else {
            for (int i = 0; i < cities.size(); i++) {
                if (Utils.equalsKeyValue(cities.get(i), address.getCity())) {
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
        if (address.getDistrict() == null) {
            districtSP.setSelection(0);
        } else {
            for (int i = 0; i < districts.size(); i++) {
                if (Utils.equalsKeyValue(districts.get(i), address.getDistrict())) {
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

    @OnClick(R.id.edit_address_submit_button)
    public void saveAddressClick() {
        boolean isValid = ValidateUtil.validateEditTexts(addressNameET, addressET);
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        String addressTitle = addressNameET.getText().toString().trim();
        String addressContent = addressET.getText().toString().trim();
        KeyValue country = countries.get(countrySP.getSelectedItemPosition());

        KeyValue state = null;
        KeyValue city = null;
        KeyValue district = null;
        if (isIndia) {
            state = states.get(stateSP.getSelectedItemPosition());
            city = cities.get(citySP.getSelectedItemPosition());
            district = districts.get(districtSP.getSelectedItemPosition());
        }

        address.setDescription(addressTitle);
        address.setAddressText(addressContent);
        address.setCountry(country);
        address.setState(isIndia ? state : null);
        address.setCity(isIndia ? city : null);
        address.setDistrict(isIndia ? district : null);

        returnResultFinish();
    }

    @OnClick(R.id.edit_address_delete_button)
    public void deleteAddressClick() {
        if (address.getId() == null) {
            returnDeteleFinish();
        } else {
            Utils.generateDialog(mContext, getString(R.string.delete), address.getDescription(),
                    getString(R.string.delete), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            deleteAddressCall(address.getId());
                        }
                    },
                    getString(R.string.cancel), null);
        }
    }

    private void deleteAddressCall(String id) {
        FirmService.deleteAddress(id, firmId, new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    returnDeteleFinish();
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.ACTION, Constants.ADD);
        returnIntent.putExtra(Constants.ADDRESS, address);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void returnDeteleFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.ACTION, Constants.REMOVE);
        returnIntent.putExtra(Constants.ADDRESS, address);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
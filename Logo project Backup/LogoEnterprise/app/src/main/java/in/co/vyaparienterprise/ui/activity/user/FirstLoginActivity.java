package in.co.vyaparienterprise.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.middleware.ServiceCreator;
import in.co.vyaparienterprise.middleware.ServiceRequest;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.CommonService;
import in.co.vyaparienterprise.model.Company;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.ErrorModel;
import in.co.vyaparienterprise.model.response.dto.CompanyDTO;
import in.co.vyaparienterprise.ui.activity.app.MainActivity;
import in.co.vyaparienterprise.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.MyItemSelectedListener;
import in.co.vyaparienterprise.ui.generic.MySpinner;
import in.co.vyaparienterprise.util.ErrorUtils;
import in.co.vyaparienterprise.util.Utils;
import in.co.vyaparienterprise.util.ValidateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstLoginActivity extends SohoActivity {

    @BindView(R.id.first_login_company_name)
    EditText companyNameET;
    @BindView(R.id.first_login_address)
    EditText addressET;
    @BindView(R.id.first_login_country_sp)
    MySpinner countrySP;
    @BindView(R.id.first_login_state_area)
    LinearLayout stateLL;
    @BindView(R.id.first_login_state_sp)
    MySpinner stateSP;
    @BindView(R.id.first_login_city_dist)
    LinearLayout cityDistLL;
    @BindView(R.id.first_login_city_sp)
    MySpinner citySP;
    @BindView(R.id.first_login_district_ll)
    LinearLayout districtLL;
    @BindView(R.id.first_login_district_sp)
    MySpinner districtSP;
    @BindView(R.id.first_login_gstin)
    EditText gstinET;
    @BindView(R.id.first_login_pan)
    EditText panET;

    private Context mContext;
    private ArrayList<KeyValue> countries;
    private ArrayList<KeyValue> states;
    private ArrayList<KeyValue> cities;
    private ArrayList<KeyValue> districts;
    private Company companyRequest;
    private boolean isIndia;

    private int whiteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        ButterKnife.bind(this);
        mContext = this;

        whiteColor = Color.parseColor("#99FFFFFF");
        companyRequest = new Company();

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
        countrySP.setAdapter(new OneLineSpinnerAdapter(mContext, countries, whiteColor));
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
        stateSP.setAdapter(new OneLineSpinnerAdapter(mContext, states, whiteColor));
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
    }

    private void setCitiesConfig() {
        citySP.setAdapter(new OneLineSpinnerAdapter(mContext, cities, whiteColor));
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
    }

    private void setDistrictsConfig() {
        if (districts.size() < 2) {
            districtLL.setVisibility(View.INVISIBLE);
        } else {
            districtLL.setVisibility(View.VISIBLE);
        }
        districtSP.setAdapter(new OneLineSpinnerAdapter(mContext, districts, whiteColor));
    }

    @OnClick(R.id.first_login_submit_button)
    public void saveInfoClick() {
        boolean isValid = ValidateUtil.validateEditTexts(companyNameET, addressET, gstinET);
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        String companyName = companyNameET.getText().toString().trim();
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

        String gstin = gstinET.getText().toString().trim();
        String pan = panET.getText().toString().trim();

        companyRequest.setName(companyName);
        companyRequest.setAddress(addressContent);
        companyRequest.setCountry(country);
        companyRequest.setState(isIndia ? state : null);
        companyRequest.setCity(isIndia ? city : null);
        companyRequest.setDistrict(isIndia ? district : null);
        companyRequest.setGSTIN(gstin);
        companyRequest.setPAN(pan);
        companyRequest.setActive(true);

        updateCompanyCall(companyRequest);
    }

    private void updateCompanyCall(Company companyRequest) {
        ServiceRequest apiService = ServiceCreator.getClient().create(ServiceRequest.class);
        Call<BaseModel<CompanyDTO>> call = apiService.updateCompany(companyRequest);
        call.enqueue(new Callback<BaseModel<CompanyDTO>>() {
            @Override
            public void onResponse(Call<BaseModel<CompanyDTO>> call, Response<BaseModel<CompanyDTO>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        CompanyDTO companyDTO = response.body().getData();
                        if (companyDTO != null) {
                            startActivity(new Intent(FirstLoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toasty.error(mContext, getString(R.string.error)).show();
                        }
                    } else {
                        Toasty.error(mContext, response.body().getErrorDescription()).show();
                    }
                } else {
                    ErrorModel error = ErrorUtils.parseError(response);
                    Toasty.error(mContext, error.getError()).show();
                }
            }

            @Override
            public void onFailure(Call<BaseModel<CompanyDTO>> call, Throwable ignored) {
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toasty.warning(mContext, getString(R.string.empy_warn)).show();
    }
}
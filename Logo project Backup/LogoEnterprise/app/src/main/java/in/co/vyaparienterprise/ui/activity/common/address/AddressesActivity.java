package in.co.vyaparienterprise.ui.activity.common.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.model.Address;
import in.co.vyaparienterprise.ui.adapter.FirmAddressesAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.listener.ClickListener;
import in.co.vyaparienterprise.ui.listener.RecyclerViewClickListener;

public class AddressesActivity extends SohoActivity {

    @BindView(R.id.addresses_rv)
    RecyclerView addressesRV;

    private Context mContext;
    private FirmAddressesAdapter firmAddressesAdapter;
    private ArrayList<Address> addressList = new ArrayList<>();
    private String firmId;
    private Address clickedAddress;

    private boolean isWarehouse;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_addresses);
        ButterKnife.bind(this);
        setToolbarConfig(R.string.delivery_address);
        mContext = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            addressList = bundle.getParcelableArrayList(Constants.ADDRESS_LIST);
            firmId = bundle.getString(Constants.FIRM_ID);
            isWarehouse = bundle.getBoolean(Constants.WAREHOUSE);
        }

        if (isWarehouse) {
            setToolbarConfig(R.string.warehouse);
        }

        config();

        firmAddressesAdapter = new FirmAddressesAdapter(addressList);
        addressesRV.setAdapter(firmAddressesAdapter);

        if (addressList.size() == 0) {
            fabClick();
        }
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        addressesRV.setItemAnimator(new DefaultItemAnimator());
        addressesRV.setHasFixedSize(true);
        addressesRV.setNestedScrollingEnabled(false);
        addressesRV.setLayoutManager(mLayoutManager);
        addressesRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, addressesRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                clickedAddress = addressList.get(position);
                Bundle bundle = new Bundle();
                Intent addressIntent = new Intent(mContext, EditAddressActivity.class);
                bundle.putParcelable(Constants.ADDRESS, clickedAddress);
                bundle.putString(Constants.FIRM_ID, firmId);
                bundle.putBoolean(Constants.WAREHOUSE, isWarehouse);
                addressIntent.putExtras(bundle);
                startActivityForResult(addressIntent, Constants.REFRESH_CODE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, CreateAddressActivity.class);
        bundle.putBoolean(Constants.WAREHOUSE, isWarehouse);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.REFRESH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REFRESH_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                int action = data.getIntExtra(Constants.ACTION, 0);
                Address result = data.getParcelableExtra(Constants.ADDRESS);
                if (result != null) {
                    isRefresh = true;
                    if (action == Constants.ADD) {
                        addressList.remove(clickedAddress);
                        addressList.add(0, result);
                        if (isWarehouse) {
                            Toasty.info(mContext, getString(R.string.add_warehouse_warn)).show();
                        } else {
                            Toasty.info(mContext, getString(R.string.add_address_warn)).show();
                        }
                    } else if (action == Constants.REMOVE) {
                        addressList.remove(clickedAddress);
                    } //TODO: iconu değiştir.
                    firmAddressesAdapter = new FirmAddressesAdapter(addressList);
                    addressesRV.setAdapter(firmAddressesAdapter);
                }
            }
        }

        if (addressList.size() == 0) {
            returnResultFinish();
        }
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putParcelableArrayListExtra(Constants.ADDRESS_LIST, addressList);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isRefresh) {
            returnResultFinish();
        } else {
            super.onBackPressed();
        }
    }
}
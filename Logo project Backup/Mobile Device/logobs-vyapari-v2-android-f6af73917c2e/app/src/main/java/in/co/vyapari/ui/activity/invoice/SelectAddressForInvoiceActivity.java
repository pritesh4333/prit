package in.co.vyapari.ui.activity.invoice;

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
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.FirmService;
import in.co.vyapari.model.Address;
import in.co.vyapari.model.Firm;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.FirmDTO;
import in.co.vyapari.ui.activity.common.address.CreateAddressActivity;
import in.co.vyapari.ui.adapter.invoice.InvoiceSelectFirmAddressAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.listener.ClickListener;
import in.co.vyapari.ui.listener.RecyclerViewClickListener;
import in.co.vyapari.util.Utils;

public class SelectAddressForInvoiceActivity extends SohoActivity {

    @BindView(R.id.addresses_rv)
    RecyclerView addressesRV;

    private Context mContext;
    private InvoiceSelectFirmAddressAdapter invoiceSelectFirmAddressAdapter;
    private Firm firm;
    private ArrayList<Address> addressList = new ArrayList<>();
    private Address clickedAddress;
    private Address newAddress;

    private boolean isUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select_for_invoice);
        ButterKnife.bind(this);
        setToolbarConfig(R.string.delivery_addresses);
        mContext = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            firm = bundle.getParcelable(Constants.FIRM);
            addressList = firm.getShippingAddresses();
        } else {
            addressList = new ArrayList<>();
        }

        config();

        invoiceSelectFirmAddressAdapter = new InvoiceSelectFirmAddressAdapter(addressList);
        addressesRV.setAdapter(invoiceSelectFirmAddressAdapter);

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
                returnResultFinish(true);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivityForResult(new Intent(mContext, CreateAddressActivity.class), Constants.REFRESH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REFRESH_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                int action = data.getIntExtra(Constants.ACTION, 0);
                newAddress = data.getParcelableExtra(Constants.ADDRESS);
                if (newAddress != null) {
                    if (action == Constants.ADD) {
                        addressList.remove(clickedAddress);
                        addressList.add(0, newAddress);
                    }
                    firm.setShippingAddresses(addressList);
                    updateFirmCall(firm);
                }
            }
        }

        if (addressList.size() == 0) {
            finish();
        }
    }

    private void updateFirmCall(Firm firmRequest) {
        Utils.showLoading(mContext);
        FirmService.updateFirm(firmRequest, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    isUpdated = true;
                    Toasty.success(mContext, response.getErrorDescription()).show();
                    FirmDTO firmDTO = response.getData();
                    if (firmDTO.getShippingAddresses() != null) {
                        addressList = response.getData().getShippingAddresses();
                        invoiceSelectFirmAddressAdapter = new InvoiceSelectFirmAddressAdapter(addressList);
                        addressesRV.setAdapter(invoiceSelectFirmAddressAdapter);
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                    addressList.remove(newAddress);
                    firm.setShippingAddresses(addressList);
                    invoiceSelectFirmAddressAdapter = new InvoiceSelectFirmAddressAdapter(addressList);
                    addressesRV.setAdapter(invoiceSelectFirmAddressAdapter);

                    if (addressList.size() == 0) {
                        fabClick();
                    }
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    private void returnResultFinish(boolean isSelect) {
        Intent returnIntent = new Intent();
        if (isSelect) {
            returnIntent.putExtra(Constants.ADDRESS, clickedAddress);
        }
        if (isUpdated) {
            returnIntent.putParcelableArrayListExtra(Constants.ALL_ADDRESSES, addressList);
        }
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isUpdated) {
            returnResultFinish(false);
        } else {
            super.onBackPressed();
        }
    }
}
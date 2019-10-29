package in.co.vyapari.ui.activity.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import in.co.vyapari.middleware.service.ProductService;
import in.co.vyapari.model.Currency;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.Product;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ProductDTO;
import in.co.vyapari.ui.activity.common.hsnsac.HSNSACCodesActivity;
import in.co.vyapari.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.generic.MySpinner;
import in.co.vyapari.ui.generic.currencyedittext.CurrencyEditText;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.Utils;
import in.co.vyapari.util.ValidateUtil;

public class EditProductActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText productNameET;
    @BindView(R.id.edit_product_type_sp)
    MySpinner productTypeSP;
    @BindView(R.id.edit_product_hsnsac_code)
    Button HSNSACCodeBT;
    @BindView(R.id.edit_product_gst_code)
    MySpinner GSTCCodeSP;
    @BindView(R.id.edit_product_cess_code)
    MySpinner CESSCodeSP;
    @BindView(R.id.edit_product_barcode)
    EditText productBarcodeET;
    @BindView(R.id.edit_product_code)
    EditText productCodeET;
    @BindView(R.id.edit_product_purchase_price)
    CurrencyEditText purchasePriceET;
    //@BindView(R.id.edit_product_purchase_currency_sp)
    //MySpinner purchaseCurrencySP;
    @BindView(R.id.edit_product_sale_price)
    CurrencyEditText salePriceET;
    //@BindView(R.id.edit_product_sale_currency_sp)
    //MySpinner saleCurrencySP;
    // @BindView(R.id.edit_product_gst_include)
    // Switch GSTIncludeSW;
    @BindView(R.id.edit_product_unit_sp)
    MySpinner unitTypeSP;
    @BindView(R.id.edit_product_note)
    EditText noteET;
    @BindView(R.id.edit_product_is_active)
    Switch activeSW;

    private Context mContext;
    private ProductDTO productDTO;
    private ArrayList<Currency> currencyList;
    private ArrayList<KeyValue> productTypeList;
    private ArrayList<KeyValue> unitTypeList;
    private ArrayList<KeyValue> cessList;
    private ArrayList<KeyValue> gstList;

    private KeyValue selectedHSNSACCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        ButterKnife.bind(this);
        mContext = this;

        setDetailToolbarConfig(R.string.edit_product, R.drawable.ico_mini_product, R.string.product_name);

        setTypesConfig();
        //getCurrencyCodes();
        setUnitsConfig();
        setGSTConfig();
        setCESSConfig();

        productDTO = DataUtil.getBundleAndRemove(ProductDTO.class);

        if (productDTO != null) {
            updateUI();
        } else {
            Toasty.error(mContext, getString(R.string.product_error)).show();
            finish();
        }
    }

    private void updateUI() {
        productNameET.setText(productDTO.getName());
        productNameET.setSelection(productDTO.getName().length());

        if (productDTO.getProductType() != null) {
            int productTypeSelection = Utils.getSelection(productTypeList, productDTO.getProductType().getKey());
            if (productTypeSelection == 0) {
                productTypeSelection = 1;
            }
            productTypeSP.setSelection(productTypeSelection);
        }

        productCodeET.setText(productDTO.getCode());
        if (productDTO.getCode() != null) {
            productCodeET.setSelection(productDTO.getCode().length());
        }

        productBarcodeET.setText(productDTO.getBarcode());
        if (productDTO.getBarcode() != null) {
            productBarcodeET.setSelection(productDTO.getBarcode().length());
        }
        purchasePriceET.setValue(productDTO.getPurchasePrice());

        //int purchaseCurrencySelection = Utils.getCurrencySelection(currencyList, productDTO.getPurchaseCurrency());
        //purchaseCurrencySP.setSelection(purchaseCurrencySelection);

        salePriceET.setValue(productDTO.getSalesPrice());

        //int saleCurrencySelection = Utils.getCurrencySelection(currencyList, productDTO.getSalesCurrency());
        //saleCurrencySP.setSelection(saleCurrencySelection);

        selectedHSNSACCode = productDTO.getSACHSNCode();
        if (selectedHSNSACCode != null) {
            HSNSACCodeBT.setText(selectedHSNSACCode.getKey());
        } else {
            HSNSACCodeBT.setText(R.string.please_select);
        }

        if (productDTO.getGSTCode() != null) {
            int gstSelection = Utils.getSelection(gstList, productDTO.getGSTCode().getKey());
            GSTCCodeSP.setSelection(gstSelection);
        }

        if (productDTO.getCESSCode() != null) {
            int cessSelection = Utils.getSelection(cessList, productDTO.getCESSCode().getKey());
            CESSCodeSP.setSelection(cessSelection);
        }

        if (productDTO.getUnitSet() != null) {
            int unitSetSelection = Utils.getSelection(unitTypeList, productDTO.getUnitSet().getKey());
            unitTypeSP.setSelection(unitSetSelection);
        }

        noteET.setText(productDTO.getDescription());

        //GSTIncludeSW.setChecked(productDTO.isGSTIncluded());
        activeSW.setChecked(productDTO.isActive());

        lockSpinner(productTypeSP);
        lockSpinner(unitTypeSP);
    }

    private void setTypesConfig() {
        CommonService.getProductTypes(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {

            @Override
            public void start() {
                lockSpinner(productTypeSP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                unlockSpinner(productTypeSP);
                productTypeList = response.getData();
                productTypeSP.setAdapter(new OneLineSpinnerAdapter(mContext, productTypeList));
                if (productTypeList.size() > 1) {
                    productTypeSP.setSelection(1);
                }

                updateUI();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    /*private void getCurrencyCodes() {
        CurrencyService.getCurrencyCodes(new ActionListener<ArrayList<Currency>>() {
            @Override
            public void start() {
                lockSpinner(purchaseCurrencySP);
                lockSpinner(saleCurrencySP);
            }

            @Override
            public void results(boolean isPreloaded, ArrayList<Currency> data) {
                unlockSpinner(purchaseCurrencySP);
                unlockSpinner(saleCurrencySP);
                currencyList = data;
                purchaseCurrencySP.setAdapter(new CurrencySpinnerAdapter(mContext, currencyList));
                saleCurrencySP.setAdapter(new CurrencySpinnerAdapter(mContext, currencyList));

                if (!isPreloaded) {
                    updateUI();
                }
            }
        });
    }*/

    private void setUnitsConfig() {
        CommonService.getUnitTypes(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                lockSpinner(unitTypeSP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                unlockSpinner(unitTypeSP);
                unitTypeList = response.getData();
                unitTypeSP.setAdapter(new OneLineSpinnerAdapter(mContext, unitTypeList));

                updateUI();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    private void setGSTConfig() {
        CommonService.getGSTList(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                lockSpinner(GSTCCodeSP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                unlockSpinner(GSTCCodeSP);
                gstList = response.getData();
                GSTCCodeSP.setAdapter(new OneLineSpinnerAdapter(mContext, gstList));

                updateUI();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    private void setCESSConfig() {
        CommonService.getCESSList(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                lockSpinner(CESSCodeSP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                unlockSpinner(CESSCodeSP);
                cessList = response.getData();
                CESSCodeSP.setAdapter(new OneLineSpinnerAdapter(mContext, cessList));

                updateUI();
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    @OnClick(R.id.edit_product_hsnsac_code)
    public void HSNSACCodeClick() {
        String id = productTypeList.get(productTypeSP.getSelectedItemPosition()).getKey();
        DataUtil.post(id);
        startActivityForResult(new Intent(mContext, HSNSACCodesActivity.class), Constants.HSNSAC_CODE);
    }

    @OnClick(R.id.qr_mag_icon)
    public void qrClick() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt(getString(R.string.scan_barcode));
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @OnClick(R.id.edit_product_submit_button)
    public void updateProductClick() {
        boolean isValid = ValidateUtil.validateEditTexts(productNameET) || selectedHSNSACCode != null;
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        String productName = productNameET.getText().toString().trim();
        String productCode = productCodeET.getText().toString().trim();
        String productBarcode = productBarcodeET.getText().toString().trim();
        KeyValue productType = productDTO.getProductType(); //not change
        KeyValue SACHSNCode = selectedHSNSACCode;
        KeyValue GSTCode = gstList.get(GSTCCodeSP.getSelectedItemPosition());
        KeyValue CESSCode = cessList.get(CESSCodeSP.getSelectedItemPosition());
        double purchasePrice = purchasePriceET.getDouble();
        //Currency purchaseCurrency = currencyList.get(purchaseCurrencySP.getSelectedItemPosition());
        double salePrice = salePriceET.getDouble();
        //Currency saleCurrency = currencyList.get(saleCurrencySP.getSelectedItemPosition());
        //boolean isGSTInclude = GSTIncludeSW.isChecked();
        KeyValue unit = productDTO.getUnitSet(); //not change
        String note = noteET.getText().toString().trim();
        boolean isActive = activeSW.isChecked();

       final Product productRequest = new Product();
        productRequest.setId(productDTO.getId());
        productRequest.setName(productName);
        productRequest.setCode(productCode);
        productRequest.setBarcode(productBarcode);
        productRequest.setProductType(productType);
        productRequest.setSACHSNCode(SACHSNCode);
        productRequest.setGSTCode(GSTCode);
        productRequest.setCESSCode(CESSCode);
        productRequest.setUnitSet(unit);
        productRequest.setPurchasePrice(purchasePrice);
        //productRequest.setPurchaseCurrency(purchaseCurrency);
        productRequest.setSalesPrice(salePrice);
        //productRequest.setSaleCurrency(saleCurrency);
        //productRequest.setGSTIncluded(isGSTInclude);
        productRequest.setDescription(note);
        productRequest.setActive(isActive);
        if (salePrice<purchasePrice){

            Utils.generateDialog(mContext, getResources().getString(R.string.add_product), "Sale Price is less then Purchase Price",
                    getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            updateProductCall(productRequest);
                        }
                    },
                    getString(R.string.cancel), null);

        }else {

            updateProductCall(productRequest);
        }
    }

    private void updateProductCall(Product product) {
        Utils.showLoading(mContext);
        ProductService.updateProduct(product, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
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

    @OnClick(R.id.edit_product_delete_button)
    public void deleteProductClick() {
        Utils.generateDialog(mContext, getString(R.string.delete), productDTO.getName(),
                getString(R.string.delete), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteProductCall(productDTO.getId());
                    }
                },
                getString(R.string.cancel), null);
    }

    private void deleteProductCall(String id) {
        Utils.showLoading(mContext);
        ProductService.deleteProduct(id, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
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
        if (requestCode == Constants.HSNSAC_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                selectedHSNSACCode = data.getParcelableExtra(Constants.HSNSAC);
                if (selectedHSNSACCode != null) {
                    HSNSACCodeBT.setText(selectedHSNSACCode.getKey());
                }
            }
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() != null) {
                    productBarcodeET.setText(result.getContents());
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void returnResultFinish() {
        MobileConstants.productId = "id";
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.REFRESH, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
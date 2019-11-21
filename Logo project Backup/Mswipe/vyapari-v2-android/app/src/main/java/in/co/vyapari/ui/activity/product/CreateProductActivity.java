package in.co.vyapari.ui.activity.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

public class CreateProductActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText productNameET;
    @BindView(R.id.create_product_type_sp)
    MySpinner productTypeSP;
    @BindView(R.id.create_product_hsnsac_code)
    Button HSNSACCodeBT;
    @BindView(R.id.create_product_gst_code)
    MySpinner GSTCCodeSP;
    @BindView(R.id.create_product_cess_code)
    MySpinner CESSCodeSP;
    @BindView(R.id.create_product_barcode)
    EditText productBarcodeET;
    @BindView(R.id.create_product_code)
    EditText productCodeET;
    @BindView(R.id.create_product_purchase_price)
    CurrencyEditText purchasePriceET;
    //@BindView(R.id.create_product_purchase_currency_sp)
    //MySpinner purchaseCurrencySP;
    @BindView(R.id.create_product_sale_price)
    CurrencyEditText salePriceET;
    //@BindView(R.id.create_product_sale_currency_sp)
    //MySpinner saleCurrencySP;
    //   @BindView(R.id.create_product_gst_include)
    //   Switch GSTIncludeSW;
    @BindView(R.id.create_product_unit_sp)
    MySpinner unitTypeSP;
    @BindView(R.id.create_product_note)
    EditText noteET;

    private Context mContext;
    private ArrayList<Currency> currencyList;
    private ArrayList<KeyValue> productTypeList;
    private ArrayList<KeyValue> unitTypeList;
    private ArrayList<KeyValue> cessList;
    private ArrayList<KeyValue> gstList;

    private KeyValue selectedHSNSACCode = null;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        ButterKnife.bind(this);
        mContext = this;

        setDetailToolbarConfig(R.string.add_product, R.drawable.ico_mini_product, R.string.product_name);

        product = new Product();

        setTypesConfig();
        //getCurrencyCodes();
        setUnitsConfig();
        setGSTCodesConfig();
        setCESSConfig();
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

                for (int i = 0; i < currencyList.size(); i++) {
                    if (Utils.equalsKeyValue(currencyList.get(i),MobileConstants.defaultCurrency))) {
                        purchaseCurrencySP.setSelection(i);
                        saleCurrencySP.setSelection(i);
                        break;
                    }
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
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    private void setGSTCodesConfig() {
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
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    @OnClick(R.id.create_product_hsnsac_code)
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

    @OnClick(R.id.create_product_submit_button)
    public void saveProductClick() {
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
        KeyValue productType = productTypeList.get(productTypeSP.getSelectedItemPosition());
        KeyValue SACHSNCode;
        if (selectedHSNSACCode==null){
              SACHSNCode=new KeyValue(" "," ");
        }else {
              SACHSNCode = selectedHSNSACCode;
        }
        KeyValue GSTCode = gstList.get(GSTCCodeSP.getSelectedItemPosition());
        KeyValue CESSCode = cessList.get(CESSCodeSP.getSelectedItemPosition());
        double purchasePrice = purchasePriceET.getDouble();
        //Currency purchaseCurrency = currencyList.get(purchaseCurrencySP.getSelectedItemPosition());
        double salePrice = salePriceET.getDouble();
        //Currency saleCurrency = currencyList.get(saleCurrencySP.getSelectedItemPosition());
        //boolean isGSTInclude = GSTIncludeSW.isChecked();
        KeyValue unit = unitTypeList.get(unitTypeSP.getSelectedItemPosition());
        String note = noteET.getText().toString().trim();

        product.setName(productName);
        product.setCode(productCode);
        product.setBarcode(productBarcode);
        product.setProductType(productType);
        product.setSACHSNCode(SACHSNCode);
        product.setGSTCode(GSTCode);
        product.setCESSCode(CESSCode);
        product.setUnitSet(unit);
        product.setPurchasePrice(purchasePrice);
        //product.setPurchaseCurrency(purchaseCurrency);
        product.setSalesPrice(salePrice);
        //product.setSaleCurrency(saleCurrency);
        //product.setGSTIncluded(isGSTInclude);
        product.setDescription(note);
        product.setActive(true);

        if (purchasePrice<salePrice){
            Toasty.error(mContext,"Sale Price higher then Purchase Price").show();
        }else {
            addProductCall();
        }
    }

    private void addProductCall() {
        Utils.showLoading(mContext);
        ProductService.addProduct(product, new ServiceCall<BaseModel<ProductDTO>>() {
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
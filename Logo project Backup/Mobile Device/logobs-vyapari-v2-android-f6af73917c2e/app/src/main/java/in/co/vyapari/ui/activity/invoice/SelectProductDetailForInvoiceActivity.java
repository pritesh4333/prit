package in.co.vyapari.ui.activity.invoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import in.co.vyapari.model.InvoiceLine;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.Product;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ProductDTO;
import in.co.vyapari.ui.activity.common.hsnsac.HSNSACCodesActivity;
import in.co.vyapari.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.generic.MyItemSelectedListener;
import in.co.vyapari.ui.generic.MySpinner;
import in.co.vyapari.ui.generic.MySwitch;
import in.co.vyapari.ui.generic.currencyedittext.CurrencyEditText;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.Utils;
import in.co.vyapari.util.ValidateUtil;

public class SelectProductDetailForInvoiceActivity extends SohoActivity {

    @BindView(R.id.select_product_dfs_top_area)
    LinearLayout topArea;
    @BindView(R.id.select_product_dfs_name)
    EditText productNameET;
    @BindView(R.id.select_product_type_sp)
    MySpinner productTypeSP;
    @BindView(R.id.select_product_hsnsaccode_tv)
    TextView HSNSACCodeTV;
    @BindView(R.id.select_product_hsnsac_code)
    Button HSNSACCodeBT;
    @BindView(R.id.select_product_gst_code)
    MySpinner GSTCCodeSP;
    @BindView(R.id.select_product_cess_code)
    MySpinner CESSCodeSP;
    @BindView(R.id.select_product_dfs_code)
    EditText productCodeET;
    @BindView(R.id.select_product_dfs_barcode)
    EditText productBarcodeET;
    @BindView(R.id.elect_product_purchases_ll)
    LinearLayout purchaseLL;
    @BindView(R.id.select_product_dfs_purchase_price)
    CurrencyEditText purchasePriceET;
    //@BindView(R.id.select_product_dfs_purchase_currency_sp)
    //MySpinner purchaseCurrencySP;
    @BindView(R.id.elect_product_sales_ll)
    LinearLayout salesLL;
    @BindView(R.id.select_product_dfs_sale_price)
    CurrencyEditText salePriceET;
    //@BindView(R.id.select_product_dfs_sale_currency_sp)
    //MySpinner saleCurrencySP;
    @BindView(R.id.select_product_dfs_gst_include)
    MySwitch GSTIncludeSW;
    @BindView(R.id.select_product_dfs_unit_sp)
    MySpinner unitTypeSP;
    @BindView(R.id.select_product_dfs_subunit_sp)
    MySpinner subUnitTypeSP;
    @BindView(R.id.select_product_dfs_count_et)
    EditText countET;
    @BindView(R.id.select_product_dfs_discount_sp)
    MySpinner discountTypeSP;
    @BindView(R.id.select_product_dfs_discount_value_ll)
    LinearLayout discountTitleLL;
    @BindView(R.id.select_product_dfs_discount_value_et)
    EditText discountValueET;
    @BindView(R.id.select_product_dfs_description)
    EditText descriptionET;

    private Context mContext;
    private Product product;
    private ArrayList<Currency> currencyList;
    private ArrayList<KeyValue> discountTypeList;
    private ArrayList<KeyValue> productTypeList;
    private ArrayList<KeyValue> unitTypeList;
    private ArrayList<KeyValue> subUnitTypeList;
    private ArrayList<KeyValue> cessList;
    private ArrayList<KeyValue> gstList;

    private KeyValue selectedHSNSACCode = null;

    private boolean isHasDiscount;
    private boolean isNewProduct;
    private boolean isEditInvoiceLine;

    private InvoiceLine invoiceLine;
    private int invoiceType;
    private String ActiveUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_add_invoice_product);
        ButterKnife.bind(this);
        mContext = this;

        setDiscountTypeConfig();
        setTypesConfig();
        //getCurrencyCodes();
        setUnitsConfig();
        setGSTConfig();
        setCESSConfig();

        invoiceLine = new InvoiceLine();
        invoiceLine.setCurrency(MobileConstants.defaultCurrency);

        Bundle bundle = getIntent().getExtras();
        ActiveUnit=bundle.getString("ActiveUnit");
        isNewProduct = bundle.getBoolean(Constants.IS_NEW_PRODUCT, false);
        invoiceType = bundle.getInt(Constants.INVOICE_TYPE);
        if (!isNewProduct) {
            product = bundle.getParcelable(Constants.PRODUCT);
            if (product == null) {
                invoiceLine = bundle.getParcelable(Constants.INVOICE_LINE);
                product = invoiceLine.getProduct();
                isEditInvoiceLine = true;
            }
            setToolbarConfig(product.getName(), false);
        } else {
            product = new Product();
            setToolbarConfig(getString(R.string.create_and_add_product), false);
        }

        updateUI();
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
/*
    private void getCurrencyCodes() {
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
                    if (Utils.equalsKeyValue(currencyList.get(i),MobileConstants.defaultCurrency)) {
                        purchaseCurrencySP.setSelection(i);
                        saleCurrencySP.setSelection(i);
                        break;
                    }
                }

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

                if (product.getUnitSet() != null) {
                    setSubUnitsConfig(product.getUnitSet().getKey());
                }

                unitTypeSP.setOnItemSelectedListener(new MyItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position) {
                        String unitKey = unitTypeList.get(position).getKey();
                        setSubUnitsConfig(unitKey);
                    }
                });
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    private void setSubUnitsConfig(String unitKey) {
        CommonService.getSubUnitTypes(unitKey, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
                lockSpinner(subUnitTypeSP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                Utils.hideLoading();
                unlockSpinner(subUnitTypeSP);
                subUnitTypeList = response.getData();
                subUnitTypeSP.setAdapter(new OneLineSpinnerAdapter(mContext, subUnitTypeList));
                for (int i=0 ;i<subUnitTypeList.size();i++){
                    if (subUnitTypeList.get(i).getValue().equalsIgnoreCase(ActiveUnit)){
                        subUnitTypeSP.setSelection(i);
                        return;
                    }
                }



//                if (isEditInvoiceLine) {
//                    if (invoiceLine.getUnit() != null) {
//                        int subUnitSetSelection = Utils.getSelection(subUnitTypeList, invoiceLine.getUnit().getKey());
//                        subUnitTypeSP.setSelection(subUnitSetSelection);
//                    }
//                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
            }
        });
    }

    private void updateUI() {
        if (isNewProduct) {
            topArea.setVisibility(View.VISIBLE);
        } else {
            topArea.setVisibility(View.GONE);

            if (invoiceType == Constants.SALES_INVOICE) {
                salesLL.setVisibility(View.VISIBLE);
                purchaseLL.setVisibility(View.GONE);
            } else {
                salesLL.setVisibility(View.GONE);
                purchaseLL.setVisibility(View.VISIBLE);
            }
        }

        productNameET.setText(product.getName());

        if (product.getName() != null) {
            productNameET.setSelection(product.getName().length());
        }

        if (product.getProductType() != null) {
            int productTypeSelection = Utils.getSelection(productTypeList, product.getProductType().getKey());
            if (productTypeSelection == 0) {
                productTypeSelection = 1;
            }
            productTypeSP.setSelection(productTypeSelection);
        }

        productCodeET.setText(product.getCode());

        if (product.getCode() != null) {
            productCodeET.setSelection(product.getCode().length());
        }

        productBarcodeET.setText(product.getBarcode());

        if (product.getBarcode() != null) {
            productBarcodeET.setSelection(product.getBarcode().length());
        }

        purchasePriceET.setValue(product.getPurchasePrice());
        //int purchaseCurrencySelection = Utils.getCurrencySelection(currencyList, product.getPurchaseCurrency());
        //purchaseCurrencySP.setSelection(purchaseCurrencySelection);

        salePriceET.setValue(product.getSalesPrice());
        //int saleCurrencySelection = Utils.getCurrencySelection(currencyList, product.getSaleCurrency());
        //saleCurrencySP.setSelection(saleCurrencySelection);

        selectedHSNSACCode = product.getSACHSNCode();
        if (selectedHSNSACCode != null) {
            HSNSACCodeBT.setText(selectedHSNSACCode.getKey());
        } else {
            HSNSACCodeBT.setText(R.string.please_select);
        }

        if (product.getGSTCode() != null) {
            int gstSelection = Utils.getSelection(gstList, product.getGSTCode().getKey());
            GSTCCodeSP.setSelection(gstSelection);
        }

        if (product.getCESSCode() != null) {
            int cessSelection = Utils.getSelection(cessList, product.getCESSCode().getKey());
            CESSCodeSP.setSelection(cessSelection);
        }

        if (product.getUnitSet() != null) {
            int unitSetSelection = Utils.getSelection(unitTypeList, product.getUnitSet().getKey());
            unitTypeSP.setSelection(unitSetSelection, false);
        }

        GSTIncludeSW.setChecked(invoiceLine.isGSTIncluded());

        if (isEditInvoiceLine) {
            if (invoiceLine.getUnit() != null) {
                int subUnitSetSelection = Utils.getSelection(subUnitTypeList, invoiceLine.getUnit().getKey());
                subUnitTypeSP.setSelection(subUnitSetSelection);
            }

            discountTypeSP.setSelection(invoiceLine.getDiscountType());

            if (invoiceLine.getDiscountType() == 1) {
                discountValueET.setText(String.valueOf(invoiceLine.getDiscountAmount()));
            } else if (invoiceLine.getDiscountType() == 2) {
                discountValueET.setText(String.valueOf(invoiceLine.getDiscountRate()));
            }

            if (Utils.isInteger(invoiceLine.getQuantity())) {
                countET.setText(String.valueOf((int) invoiceLine.getQuantity()));
            } else {
                countET.setText(String.valueOf(invoiceLine.getQuantity()));
            }
        }

        if (invoiceLine.getDescription2() != null) {
            descriptionET.setText(invoiceLine.getDescription2());
        }

        if (isNewProduct) {
            unlockSpinner(productTypeSP);
        } else {
            lockSpinner(productTypeSP);
        }
    }

    private void setDiscountTypeConfig() {
        // TODO DÜZENLE
        discountTypeList = new ArrayList<>();
        KeyValue keyValue;
        keyValue = new KeyValue("NON", "NON");
        discountTypeList.add(keyValue);
        keyValue = new KeyValue("AMOUNT", "AMOUNT");
        discountTypeList.add(keyValue);
        keyValue = new KeyValue("PERCENT", "PERCENT");
        discountTypeList.add(keyValue);

        discountTypeSP.setAdapter(new OneLineSpinnerAdapter(mContext, discountTypeList));

        discountTypeSP.setOnItemSelectedListener(new MyItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                Utils.hideKeyboard(mContext);
                if (position == 0) {
                    discountTitleLL.setVisibility(View.GONE);
                    isHasDiscount = false;
                } else {
                    discountTitleLL.setVisibility(View.VISIBLE);
                    isHasDiscount = true;
                }
            }
        });
    }

    @OnClick(R.id.select_product_hsnsac_code)
    public void HSNSACCodeClick() {
        if (isNewProduct) {
            if (!VyapariApp.getServiceQueue().isSuccess()) {
                return;
            }

            String id = productTypeList.get(productTypeSP.getSelectedItemPosition()).getKey();
            DataUtil.post(id);
            startActivityForResult(new Intent(mContext, HSNSACCodesActivity.class), Constants.HSNSAC_CODE);
        }
    }

    @OnClick(R.id.select_product_dfs_remove_count)
    public void removeCountClick() {
        String counter = countET.getText().toString().trim();
        if (counter != null && !counter.isEmpty()) {
            double count = Double.valueOf(counter);
            count--;
            count = count < 1 ? 1 : count;
            String _count;
            if (Utils.isInteger(count)) {
                _count = String.valueOf((int) count);
            } else {
                _count = String.valueOf((int) count);
            }
            countET.setText(_count);
            invoiceLine.setQuantity(count);
        }
    }

    @OnClick(R.id.select_product_dfs_add_count)
    public void addCountClick() {
        String counter = countET.getText().toString().trim();
        if (counter != null && !counter.isEmpty()) {
            double count = Double.valueOf(counter);
            count++;
            String _count;
            if (Utils.isInteger(count)) {
                _count = String.valueOf((int) count);
            } else {
                _count = String.valueOf((int) count);
            }
            countET.setText(_count);
            invoiceLine.setQuantity(count);
        }
    }

    @OnClick(R.id.select_product_dfs_submit_button)
    public void updateProductClick() {
        boolean isValid = ValidateUtil.validateEditTexts(productNameET, salePriceET) || selectedHSNSACCode != null;
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        if (selectedHSNSACCode == null) {
            HSNSACCodeClick();
        }

        String discountValue = discountValueET.getText().toString().trim();
        double quantity = Double.valueOf(countET.getText().toString());

        invoiceLine.setProduct(product);

        if (isHasDiscount && !discountValue.isEmpty()) {
            if (discountTypeSP.getSelectedItemPosition() == Constants.NON_CODE) {
                invoiceLine.setDiscountType(0);
            } else if (discountTypeSP.getSelectedItemPosition() == Constants.AMOUNT_CODE) {
                invoiceLine.setDiscountType(1);
                invoiceLine.setDiscountAmount(Double.valueOf(discountValue));
            } else if (discountTypeSP.getSelectedItemPosition() == Constants.PERCENT_CODE) {
                invoiceLine.setDiscountType(2);
                invoiceLine.setDiscountRate(Double.valueOf(discountValue));
            }
        } else {
            invoiceLine.setDiscountType(0);
        }

        invoiceLine.setQuantity(quantity);

        String productName = isNewProduct ? productNameET.getText().toString().trim() : product.getName();
        KeyValue productType = isNewProduct ? productTypeList.get(productTypeSP.getSelectedItemPosition()) : product.getProductType();
        KeyValue SACHSNCode;
        if (selectedHSNSACCode==null){
            SACHSNCode=new KeyValue(" "," ");
        }else {
            SACHSNCode = selectedHSNSACCode;
        }

        KeyValue GSTCode = isNewProduct ? gstList.get(GSTCCodeSP.getSelectedItemPosition()) : product.getGSTCode();
        KeyValue CESSCode = isNewProduct ? cessList.get(CESSCodeSP.getSelectedItemPosition()) : product.getCESSCode();
        KeyValue unit = isNewProduct ? unitTypeList.get(unitTypeSP.getSelectedItemPosition()) : product.getUnitSet();
        String productCode = isNewProduct ? productCodeET.getText().toString().trim() : product.getCode();
        String productBarcode = isNewProduct ? productBarcodeET.getText().toString().trim() : product.getBarcode();
        double purchasePrice = purchasePriceET.getDouble();
        //Currency purchaseCurrency = currencyList.get(purchaseCurrencySP.getSelectedItemPosition());
        double salePrice = salePriceET.getDouble();
        //Currency saleCurrency = currencyList.get(saleCurrencySP.getSelectedItemPosition());
        //boolean isGSTInclude = GSTIncludeSW.isChecked();

        double discountValueInt = 0;
        if (discountValueET.getText().toString() != null && !discountValueET.getText().toString().isEmpty()) {
            discountValueInt = Double.valueOf(discountValueET.getText().toString().trim());
        }
        if (discountTypeSP.getSelectedItemPosition() == Constants.AMOUNT_CODE) {
            try {
                double value = Double.valueOf(discountValueET.getText().toString());
                double totalPrice = salePrice * quantity;
                if (value >= totalPrice) {
                    Toasty.warning(mContext, getString(R.string.amount_limit_warn)).show();
                    return;
                }
            } catch (Exception ignored) {
                Toasty.error(mContext, getString(R.string.error)).show();
                return;
            }
        }
        if ((discountTypeSP.getSelectedItemPosition() == Constants.PERCENT_CODE) && discountValueInt > 100) {
            Toasty.warning(mContext, getString(R.string.discount_limit_warn)).show();
            return;
        } else if (discountValueInt == 0) {
            discountTypeSP.setSelection(0);
        }

        final Product productForInvoice = new Product();
        productForInvoice.setId(product.getId());
        productForInvoice.setName(productName);
        productForInvoice.setBarcode(productBarcode);
        productForInvoice.setProductType(productType);
        productForInvoice.setSACHSNCode(SACHSNCode);
        productForInvoice.setGSTCode(GSTCode);
        productForInvoice.setCESSCode(CESSCode);
        productForInvoice.setUnitSet(unit);
        productForInvoice.setCode(productCode);
        productForInvoice.setPurchasePrice(purchasePrice);
        //productForInvoice.setPurchaseCurrency(purchaseCurrency);
        productForInvoice.setSalesPrice(salePrice);
        //productForInvoice.setSaleCurrency(saleCurrency);
        //productForInvoice.setGSTIncluded(isGSTInclude);
        productForInvoice.setActive(true);

        if (isNewProduct) {

                addProductCall(productForInvoice);

        } else {

                addProductToInvoiceLine(productForInvoice, false);


        }
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

    private void addProductToInvoiceLine(Product product, boolean refresh) {
        String description = descriptionET.getText().toString();
        invoiceLine.setDescription2(description);

        KeyValue subUnit = subUnitTypeList.get(subUnitTypeSP.getSelectedItemPosition());
        invoiceLine.setUnit(subUnit);

        boolean isGSTInclude = GSTIncludeSW.isChecked();
        invoiceLine.setGSTIncluded(isGSTInclude);

        invoiceLine.setProduct(product);
        if (invoiceType == Constants.SALES_INVOICE) {
            invoiceLine.setUnitPrice(product.getSalesPrice());
        } else {
            invoiceLine.setUnitPrice(product.getPurchasePrice());
        }
        returnResultFinish(invoiceLine, refresh);
    }

    private void addProductCall(Product product) {
        Utils.showLoading(mContext);
        ProductService.addProduct(product, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    if (response.getData() != null) {
                        Product newProduct = new Product(response.getData());
                        addProductToInvoiceLine(newProduct, true);
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

    private void returnResultFinish(InvoiceLine invoiceLine, boolean refresh) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.INVOICE_LINE, invoiceLine);
        returnIntent.putExtra(Constants.REFRESH, refresh);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
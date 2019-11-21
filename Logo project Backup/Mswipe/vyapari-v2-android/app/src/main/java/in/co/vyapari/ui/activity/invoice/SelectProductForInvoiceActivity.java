package in.co.vyapari.ui.activity.invoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.ProductService;
import in.co.vyapari.model.InvoiceLine;
import in.co.vyapari.model.Product;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ProductDTO;
import in.co.vyapari.model.response.dto.ProductsSumDTO;
import in.co.vyapari.model.response.summary.ProductSum;
import in.co.vyapari.ui.adapter.invoice.InvoiceSelectProductAdapter;
import in.co.vyapari.ui.adapter.invoice.InvoiceSelectedProductAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.generic.BottomSheetLayout;
import in.co.vyapari.ui.generic.DividerItemDecoration;
import in.co.vyapari.ui.generic.MyTextWatcher;
import in.co.vyapari.ui.generic.showcase.Showcase;
import in.co.vyapari.ui.listener.ClickListener;
import in.co.vyapari.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyapari.ui.listener.RecyclerViewClickListener;
import in.co.vyapari.ui.listener.UpdateListener;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.FilterUtil;
import in.co.vyapari.util.Utils;

public class SelectProductForInvoiceActivity extends SohoActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.qr_mag_icon)
    ImageView qrOrSearchIcon;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.products_rv)
    RecyclerView productsRV;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bottom_sheet_layout_view)
    View bottomSheetLayoutView;
    @BindView(R.id.bottom_sheet_layout)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.icon_up)
    ImageView iconUp;
    @BindView(R.id.add_to_invoice)
    Button addToInvoiceBT;
    @BindView(R.id.selected_products_rv)
    RecyclerView selectedProductsRV;

    private Context mContext;

    private InvoiceSelectProductAdapter invoiceSelectProductAdapter;
    private InvoiceSelectedProductAdapter invoiceSelectedProductAdapter;
    private ArrayList<ProductSum> productSumList = new ArrayList<>();
    private RecyclerView.OnScrollListener endlessSV;

    private String searchedText;
    private int invoiceType;
    private boolean isClickable;
    private boolean isSearched;

    private ArrayList<InvoiceLine> selectedInvoiceLines = new ArrayList<>();
    private HashMap<String, Integer> selectedProducts = new HashMap<>();
    private int selectedProductIndex;

    private boolean isExpendedBSL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_select_for_invoice);
        setToolbarConfig(getString(R.string.select_product), false);
        ButterKnife.bind(this);
        mContext = this;

        invoiceType = DataUtil.getBundleAndRemove(Integer.class);

        config();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getProductsCall(true);
            }
        });

        bottomSheetLayout.setOnProgressListener(new BottomSheetLayout.OnProgressListener() {
            @Override
            public void onProgress(float progress) {
                if (progress == 1) {
                    isExpendedBSL = true;
                } else if (progress == 0) {
                    isExpendedBSL = false;
                }

                if (isExpendedBSL) {
                    iconUp.setRotation(180 * progress);
                } else {
                    iconUp.setRotation(-180 * progress);
                }
            }
        });

        selectedProductsConfig();
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        productsRV.setItemAnimator(new DefaultItemAnimator());
        productsRV.setHasFixedSize(true);
        productsRV.setNestedScrollingEnabled(false);
        productsRV.setLayoutManager(mLayoutManager);
        endlessSV = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (fab.isShown()) {
                        fab.hide();
                    }
                } else if (dy < 0) {
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getProductsCall(page, false, null);
            }
        };
        productsRV.addOnScrollListener(endlessSV);

        productsRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, productsRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isClickable) {
                    if (selectedInvoiceLines.size() < 5) {
                        ProductSum productSum = productSumList.get(position);
                        selectedProductIndex = position;
                        getProductDetailAndAddCall(productSum.getId());
                    } else {
                        Toasty.warning(mContext, getString(R.string.selected_products_max_limit_warn)).show();
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        searchET.setHint(getString(R.string.search_product));
        searchET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                int count = searchET.getText().toString().length();
                if (count == 0) {
                    qrOrSearchIcon.setImageResource(R.drawable.ico_barcode);
                    searchedText = null;
                    if (isSearched) {
                        isSearched = false;
                        getProductsCall(true);
                    }
                } else {
                    qrOrSearchIcon.setImageResource(R.drawable.search_ok);
                    searchedText = searchET.getText().toString();
                }
            }
        });

        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchClick();
                    return true;
                }
                return false;
            }
        });
    }

    private void selectedProductsConfig() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        selectedProductsRV.setItemAnimator(new DefaultItemAnimator());
        selectedProductsRV.setHasFixedSize(true);
        selectedProductsRV.setNestedScrollingEnabled(false);
        selectedProductsRV.addItemDecoration(new DividerItemDecoration(mContext));
        selectedProductsRV.setLayoutManager(mLayoutManager);

        invoiceSelectedProductAdapter = new InvoiceSelectedProductAdapter(selectedInvoiceLines, new UpdateListener() {
            @Override
            public void update(String id) {
                updateBottomSheetArea(id);
            }
        });
        selectedProductsRV.setAdapter(invoiceSelectedProductAdapter);
    }

    public void searchClick() {
        isSearched = true;
        getProductsCall(true);
    }

    @OnClick(R.id.qr_mag_icon)
    public void qrClick() {
        if (searchedText != null) {
            searchClick();
        } else {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt(getString(R.string.scan_barcode));
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        }
    }

    private void getProductsCall(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }

        getProductsCall(1, true, null);
    }

    private void getProductsCall(final int page, final boolean isClean, final UpdateListener updateListener) {
        Filter filter = FilterUtil.createFilter(page, true, searchedText);

        ProductService.getProducts(filter, new ServiceCall<BaseModel<ProductsSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductsSumDTO> response) {
                if (page == 1) {
                    Utils.hideLoading();
                }
                swipeRefreshLayout.setRefreshing(false);
                isClickable = true;
                if (response.getData() != null) {
                    if (isClean) {
                        resetList();
                    }
                    ProductsSumDTO productsSumDTO = response.getData();
                    ArrayList<ProductSum> productsData = new ArrayList<>();

                    for (ProductSum productSum : productsSumDTO.getProducts()) {
                        if (productSum.isActive()) {
                            productsData.add(productSum);
                        }
                    }

                    if (page == 1 && productsData.size() == 0 && searchedText == null) {
                        fabClick();
                    }

                    productSumList.addAll(productsData);
                    invoiceSelectProductAdapter.notifyDataSetChanged();

                    if (updateListener != null) {
                        updateListener.update(null);
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                resetList();
                swipeRefreshLayout.setRefreshing(false);
                Utils.hideLoading();
            }
        });
    }

    private void resetList() {
        productSumList = new ArrayList<>();
        invoiceSelectProductAdapter = new InvoiceSelectProductAdapter(productSumList, selectedProducts);
        productsRV.setAdapter(invoiceSelectProductAdapter);
    }

    private void getProductDetailAndAddCall(String id) {
        Utils.showLoading(mContext);
        ProductService.getProductDetail(id, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                Utils.hideLoading();
                if (response.getData() != null) {
                    ProductDTO productDTO = response.getData();
                    if (productDTO != null) {
                        Product product = new Product(productDTO);

                        Bundle bundle = new Bundle();
                        Intent productIntent = new Intent(mContext, SelectProductDetailForInvoiceActivity.class);
                        bundle.putParcelable(Constants.PRODUCT, product);
                        bundle.putInt(Constants.INVOICE_TYPE, invoiceType);
                        productIntent.putExtras(bundle);
                        startActivityForResult(productIntent, Constants.SELECT_PRODUCT_DETAIL_FOR_INVOICE);
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

    private void getProductDetailWithBarcodeCall(String barcode) {
        ProductService.getProductDetailWithBarcode(barcode, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                Utils.hideLoading();
                if (response.getData() != null) {
                    ProductDTO productDTO = response.getData();
                    if (productDTO != null) {
                        Product product = new Product(productDTO);

                        Bundle bundle = new Bundle();
                        Intent productIntent = new Intent(mContext, SelectProductDetailForInvoiceActivity.class);
                        bundle.putParcelable(Constants.PRODUCT, product);
                        bundle.putInt(Constants.INVOICE_TYPE, invoiceType);
                        bundle.putString("ActiveUnit",productDTO.getActiveUnitDesc());
                        productIntent.putExtras(bundle);
                        startActivityForResult(productIntent, Constants.SELECT_PRODUCT_DETAIL_FOR_INVOICE);
                    } else {
                        Toasty.error(mContext, getString(R.string.error)).show();
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                resetList();
                Utils.hideLoading();
            }
        });
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, SelectProductDetailForInvoiceActivity.class);
        bundle.putInt(Constants.INVOICE_TYPE, invoiceType);
        bundle.putBoolean(Constants.IS_NEW_PRODUCT, true);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.SELECT_PRODUCT_DETAIL_FOR_INVOICE);
    }


    @OnClick(R.id.icon_up)
    public void iconUpClick() {
        bottomSheetLayout.toggle();
    }

    @OnClick(R.id.up_bar)
    public void bottomSheetLayoutClick() {
        bottomSheetLayout.toggle();
    }

    @OnClick(R.id.add_to_invoice)
    public void addToInvoiceClick() {
        returnResultFinish();
    }

    private void updateBottomSheetArea(String id) {
        if (selectedInvoiceLines != null && selectedInvoiceLines.size() > 0) {
            bottomSheetLayout.setVisibility(View.VISIBLE);
            bottomSheetLayoutView.setVisibility(View.VISIBLE);

            String buttonName = String.format(getString(R.string.products_add_invoice_button), String.valueOf(selectedInvoiceLines.size()));
            addToInvoiceBT.setText(buttonName);
        } else {
            bottomSheetLayout.setVisibility(View.GONE);
            bottomSheetLayoutView.setVisibility(View.GONE);
        }

        if (id != null) {
            for (int i = 0; i < productSumList.size(); i++) {
                if (id.equals(productSumList.get(i).getId())) {
                    String key = productSumList.get(i).getId() + "-" + productSumList.get(i).getName();
                    selectedProducts.remove(key);
                    invoiceSelectProductAdapter.notifyDataSetChanged();
                }
            }
        }

        invoiceSelectedProductAdapter.notifyDataSetChanged();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomSheetLayout.expand();
                bottomSheetLayout.collapse();
            }
        }, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SELECT_PRODUCT_DETAIL_FOR_INVOICE) {
            if (resultCode == Activity.RESULT_OK) {
                final InvoiceLine invoiceLine = data.getParcelableExtra(Constants.INVOICE_LINE);
                boolean isRefresh = data.getBooleanExtra(Constants.REFRESH, false);
                if (invoiceLine != null) {
                    if (isRefresh) {
                        Utils.showLoading(mContext);
                        getProductsCall(1, true, new UpdateListener() {
                            @Override
                            public void update(String x) {
                                addInvoiceLine(invoiceLine);
                            }
                        });
                    } else {
                        addInvoiceLine(invoiceLine);
                    }

                }
            } else {
                String key = productSumList.get(selectedProductIndex).getId() + "-" + productSumList.get(selectedProductIndex).getName();
                selectedProducts.put(key, 0);
                invoiceSelectProductAdapter.notifyDataSetChanged();
            }

            updateBottomSheetArea(null);
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() != null) {
                    getProductDetailWithBarcodeCall(result.getContents());
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void addInvoiceLine(InvoiceLine invoiceLine) {
        String productId = invoiceLine.getProduct().getId();
        selectedProductIndex = Utils.getPosition(productId, productSumList);
        selectedInvoiceLines.add(0, invoiceLine);

        if (selectedProductIndex != -1) {
            String key = productSumList.get(selectedProductIndex).getId() + "-" + productSumList.get(selectedProductIndex).getName();
            selectedProducts.put(key, 1);
            invoiceSelectProductAdapter.notifyDataSetChanged();
        }
        updateBottomSheetArea(null);
        displayShowcase();
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putParcelableArrayListExtra(Constants.INVOICE_LINE, selectedInvoiceLines);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onRefresh() {
        isClickable = false;
        swipeRefreshLayout.setRefreshing(true);
        getProductsCall(false);
    }

    protected void displayShowcase() {
        Showcase.from(this)
                .setContentView(R.layout.showcase_add_to_invoice)
                .setFitsSystemWindows(true)
                .on(R.id.add_to_invoice)
                .addRoundRect()
                .withBorder()
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        returnResultFinish();
                    }
                })
                .showOnce(Constants.ADD_TO_INVOICE);
    }
}
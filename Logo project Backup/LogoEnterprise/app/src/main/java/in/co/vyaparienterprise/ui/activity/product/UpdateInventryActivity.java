package in.co.vyaparienterprise.ui.activity.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.CommonService;
import in.co.vyaparienterprise.middleware.service.ProductService;
import in.co.vyaparienterprise.model.CalendarModel;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.Lines;
import in.co.vyaparienterprise.model.ProductSlip;
import in.co.vyaparienterprise.model.request.filter.Filter;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.ProductsSumDTO;
import in.co.vyaparienterprise.model.response.summary.InventoryProductSum;
import in.co.vyaparienterprise.model.response.summary.ProductInventory;
import in.co.vyaparienterprise.model.response.summary.ProductInventoryDiffrence;
import in.co.vyaparienterprise.model.response.summary.ProductSum;
import in.co.vyaparienterprise.ui.adapter.InventoryAdapter;
import in.co.vyaparienterprise.ui.adapter.ProductsAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;
import in.co.vyaparienterprise.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyaparienterprise.util.DateUtil;
import in.co.vyaparienterprise.util.FilterUtil;
import in.co.vyaparienterprise.util.Utils;

public class UpdateInventryActivity extends SohoActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.qr_mag_icon)
    ImageView qrOrSearchIcon;
    @BindView(R.id.swipe_refresh_layout)
    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.products_rv)
    RecyclerView productsRV;

    private Context mContext;

    private InventoryAdapter productsAdapter;
    private ArrayList<ProductSum> productSumList = new ArrayList<>();
    private RecyclerView.OnScrollListener endlessSV;
    public static  ArrayList<ProductSum> productSumListOrignal = new ArrayList<ProductSum>();
    public static ArrayList<ProductSum> productlistEdit=new ArrayList<ProductSum>();
    public static ArrayList<ProductInventoryDiffrence> surplusInventoryList= new ArrayList<>();
    public static ArrayList<ProductInventoryDiffrence> deficitInventoryList= new ArrayList<>();
    public static ArrayList<Double> usageInventoryList= new ArrayList<>();
    public static ArrayList<Integer> totlachangescount= new ArrayList<>();
    private String searchedText;
    private boolean isClickable;
    private boolean isSearched;
    private ArrayList<KeyValue> warehouses = null;
    ProductSlip productslip= new ProductSlip();
    ArrayList<ProductInventory> proddiffforsupluse ;
    ArrayList<ProductInventory> proddifffordefisit = new ArrayList<ProductInventory>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_inventry);
        setToolbarConfig(getString(R.string.update_inventory), false);
        ButterKnife.bind(this);
        mContext = this;
        config();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getProductsCall(true);
            }
        });

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

            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getProductsCall(page, false);
            }
        };
        productsRV.addOnScrollListener(endlessSV);
//        productsRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, productsRV, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                if (isClickable) {
//                    changeColor(position, true);
//                    getProductDetailCall(productSumList.get(position).getId(), position);
//                }
//            }
//
//            @Override
//            public void onLongClick(View view, final int position) {
//
//            }
//        }));

        searchET.setHint(getString(R.string.search_inventory));
        searchET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                int count = searchET.getText().toString().length();
                if (count == 0) {
                    //qrOrSearchIcon.setImageResource(R.drawable.ico_barcode);
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

    public void searchClick() {
        isSearched = true;
        getProductsCall(true);
    }

    @OnClick(R.id.qr_mag_icon)
    public void qrClick() {
        if (searchedText != null) {
            searchClick();
        } else {
//            IntentIntegrator integrator = new IntentIntegrator(UpdateInventryActivity.this);
//            integrator.setPrompt(getString(R.string.scan_barcode));
//            integrator.setCameraId(0);
//            integrator.setBeepEnabled(false);
//            integrator.setBarcodeImageEnabled(true);
//            integrator.initiateScan();
        }
    }
    @OnClick(R.id.saveinventory)
    public void saveInventoryclick() {

        proddifffordefisit= new ArrayList<ProductInventory>();
        proddiffforsupluse= new ArrayList<ProductInventory>();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        Date dates = new Date(new Date().getTime());
        CalendarModel cm = DateUtil.dateToCM(dates);
        Date date = dates;
        productslip.setSlipDate(sdf.format(date));





        for (int ii=0;ii<productlistEdit.size();ii++){
            double dublicate=  productlistEdit.get(ii).getActualInventory();
            double dublicate1=  productSumListOrignal.get(ii).getActualInventory();
            if (dublicate!=dublicate1){
                Log.e("diffrecne","diffrece");
                if(dublicate1>=dublicate){
                    double deficit=dublicate1-dublicate;
                    ProductInventory prodinventory= new ProductInventory();

                    prodinventory.setProductCode(productSumListOrignal.get(ii).getCode());
                    prodinventory.setProductDesc(productSumListOrignal.get(ii).getName());
                    prodinventory.setQty(deficit);
                    prodinventory.setUnit(Integer.valueOf(productSumListOrignal.get(ii).getMainUnitReference()));
                    prodinventory.setInventory(deficit);
                    proddifffordefisit.add(prodinventory);


                }else{
                    double surpluse=dublicate-dublicate1;
                    //surplusInventoryList.add(surpluse);
                    ProductInventory prodinventory= new ProductInventory();

                    prodinventory.setProductCode(productSumListOrignal.get(ii).getCode());
                    prodinventory.setProductDesc(productSumListOrignal.get(ii).getName());
                    prodinventory.setQty(surpluse);
                    prodinventory.setUnit(Integer.valueOf(productSumListOrignal.get(ii).getMainUnitReference()));
                    prodinventory.setInventory(surpluse);
                    proddiffforsupluse.add(prodinventory);
                }
            }
        }


        createProductSlipforSurpluse(1,proddiffforsupluse);





    }

    private void createProductSlipforSurpluse(int i, ArrayList<ProductInventory> productdiff) {
        Utils.showLoading(UpdateInventryActivity.this);
        ArrayList<Lines> line = new ArrayList<Lines>();
        for (int k = 0; k < productdiff.size(); k++) {

            Lines lines = new Lines();
            lines.setLineType(0);
            lines.setProductCode(productdiff.get(k).getProductCode());
            lines.setProductDesc(productdiff.get(k).getProductDesc());
            lines.setQty(productdiff.get(k).getQty());
            lines.setUnit(productdiff.get(k).getUnit());
            lines.setUnitPrice(0);
            lines.setAmount(0);
            line.add(lines);

        }
        productslip.setSlipNo("");
        productslip.setOrgUnitCode("");
        productslip.setTotal("");
        productslip.setLines(line);
        if (productslip.getLines().size() != 0) {
            ProductService.productSlip(i, productslip, new ServiceCall<BaseModel<String>>() {
                @Override
                public void onResponse(boolean isOnline, BaseModel<String> response) {
                    Utils.hideLoading();
                    if (!response.isError()) {
                        proddiffforsupluse= new ArrayList<ProductInventory>();
                        productlistEdit=new ArrayList<>();
                        productSumListOrignal= new ArrayList<>();
                        Toasty.success(mContext, response.getMessage()).show();
                        createProductSlipfordeficit(2, proddifffordefisit);


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
        }else{
            createProductSlipfordeficit(2, proddifffordefisit);
        }
    }

    private void createProductSlipfordeficit(int i, ArrayList<ProductInventory> proddiff) {
        Utils.showLoading(UpdateInventryActivity.this);
        ArrayList<Lines> line = new ArrayList<Lines>();
        for (int k=0;k<proddiff.size();k++){

            Lines lines= new Lines();
            lines.setLineType(0);
            lines.setProductCode(proddiff.get(k).getProductCode());
            lines.setProductDesc(proddiff.get(k).getProductDesc());
            lines.setQty(proddiff.get(k).getQty());
            lines.setUnit(0);
            lines.setUnitPrice(0);
            lines.setAmount(0);
            line.add(lines);
        }
        productslip.setLines(line);
        if (productslip.getLines().size()!=0) {
            ProductService.productSlip(i, productslip, new ServiceCall<BaseModel<String>>() {
                @Override
                public void onResponse(boolean isOnline, BaseModel<String> response) {
                    Utils.hideLoading();
                    if (!response.isError()) {

                        proddifffordefisit= new ArrayList<ProductInventory>();
                        productlistEdit=new ArrayList<>();
                        productSumListOrignal= new ArrayList<>();
                        Toasty.success(mContext, response.getMessage()).show();
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
        }else{
            Utils.hideLoading();
            returnResultFinish();
        }
    }


    private void getWarehouses() {
        CommonService.getWarehouses(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                warehouses = response.getData();
                Log.e("warehouse",warehouses.get(0).getValue());
                KeyValue keyvalue= new KeyValue(warehouses.get(0).getKey(),warehouses.get(0).getValue(),warehouses.get(0).getLogicalref());
                productslip.setWarehouse(keyvalue);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }


    private void returnResultFinish() {
        MobileConstants.productId = "id";
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.REFRESH, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    private void getProductsCall(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }
        getProductsCall(1, true);
    }

    private void getProductsCall(final int page, final boolean isClean) {
        Filter filter = FilterUtil.createFilter(page, searchedText);
        ProductService.getProducts(filter, new ServiceCall<BaseModel<ProductsSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductsSumDTO> response) {
                if (page == 1) {
                    Utils.hideLoading();
                }
                swipeRefreshLayout.setRefreshing(false);
                isClickable = true;
                if (!response.isError()) {
                    if (response.getData() != null) {
                        if (isClean) {
                            resetList();
                        }

                         ProductsSumDTO productsSumDTO = response.getData();
                        if (productsSumDTO.getProducts().size() > 0) {

                            productSumList.addAll(productsSumDTO.getProducts());
                            for (int i =0;i<productSumList.size();i++){
                                productSumList.get(i).setActualInventory(0.0);
                            }
                            try {
                                for (int ii = 0; ii < productSumList.size(); ii++) {

                                    for (int kk = 0; kk < productlistEdit.size(); kk++) {
                                        if (productSumList.get(ii).getCode().equalsIgnoreCase( productlistEdit.get(kk).getCode())) {
                                            productSumList.get(ii).setActualInventory(productlistEdit.get(kk).getActualInventory());
                                        }
                                    }

                                }
                            } catch (Exception e) {
                                Log.e("Exception ", e.getMessage());
                            }
                            //productSumListdublictae.addAll(productsSumDTO.getProducts());
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                if (searchedText == null) {

                                }
                            } else {

                            }
                        }
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

//    private void getProductDetailCall(String id, final int position) {
//        Utils.showLoading(mContext);
//        ProductService.getProductDetail(id, new ServiceCall<BaseModel<ProductDTO>>() {
//            @Override
//            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
//                if (position > -1) {
//                    changeColor(position, false);
//                }
//                Utils.hideLoading();
//                if (!response.isError()) {
//                    if (response.getData() != null) {
//                        ProductDTO productDTO = response.getData();
//                        if (productDTO != null) {
//                            DataUtil.post(productDTO);
//                            startActivity(new Intent(UpdateInventryActivity.this, EditProductActivity.class));
//                        } else {
//                            Toasty.error(mContext, getString(R.string.error)).show();
//                        }
//                    }
//                } else {
//                    Toasty.error(mContext, response.getErrorDescription()).show();
//                }
//            }
//
//            @Override
//            public void onFailure(boolean isOnline, Throwable throwable) {
//                if (position > -1) {
//                    changeColor(position, false);
//                }
//                Utils.hideLoading();
//                Toasty.error(mContext, getString(R.string.error)).show();
//            }
//        });
//    }

//    private void getProductDetailWithBarcodeCall(String barcode) {
//        ProductService.getProductDetailWithBarcode(barcode, new ServiceCall<BaseModel<ProductDTO>>() {
//            @Override
//            public void start() {
//                Utils.showLoading(mContext);
//            }
//
//            @Override
//            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
//                Utils.hideLoading();
//                if (!response.isError()) {
//                    if (response.getData() != null) {
//                        ProductDTO productDTO = response.getData();
//                        if (productDTO != null) {
//                            DataUtil.post(productDTO);
//                            startActivity(new Intent(UpdateInventryActivity.this, EditProductActivity.class));
//                        } else {
//                            Toasty.error(mContext, getString(R.string.error)).show();
//                        }
//                    }
//                } else {
//                    Toasty.error(mContext, response.getErrorDescription()).show();
//                }
//            }
//
//            @Override
//            public void onFailure(boolean isOnline, Throwable throwable) {
//                resetList();
//                Utils.hideLoading();
//            }
//        });
//    }

    private void resetList() {
        productSumList = new ArrayList<>();
        productsAdapter = new InventoryAdapter(productSumList);
        productsRV.setAdapter(productsAdapter);
        getWarehouses();
    }

    private void changeColor(int position, boolean isClick) {
        int color;
        if (isClick) {
            color = ContextCompat.getColor(mContext, R.color.fab_color);
        } else {
            color = ContextCompat.getColor(mContext, R.color.black);
        }

        ProductsAdapter.MyViewHolder holder;
        holder = (ProductsAdapter.MyViewHolder) productsRV.findViewHolderForLayoutPosition(position);
        holder.productName.setTextColor(color);
        holder.salePrice.setTextColor(color);
        holder.purchasePrice.setTextColor(color);
    }

//    @OnClick(R.id.fab)
//    public void fabClick() {
//        getActivity().startActivity(new Intent(mContext, CreateProductActivity.class));
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() != null) {
//                getProductDetailWithBarcodeCall(result.getContents());
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }





    @Override
    public void onRefresh() {
        isClickable = false;
        swipeRefreshLayout.setRefreshing(true);
        getProductsCall(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MobileConstants.productId != null) {
            MobileConstants.productId = null;
            getProductsCall(true);
        }
        //fab.setVisibility(View.VISIBLE);
    }
}

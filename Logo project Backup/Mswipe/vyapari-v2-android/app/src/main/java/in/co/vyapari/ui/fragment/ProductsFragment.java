package in.co.vyapari.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.ProductService;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ProductDTO;
import in.co.vyapari.model.response.dto.ProductsSumDTO;
import in.co.vyapari.model.response.summary.ProductSum;
import in.co.vyapari.ui.activity.product.CreateProductActivity;
import in.co.vyapari.ui.activity.product.EditProductActivity;
import in.co.vyapari.ui.adapter.ProductsAdapter;
import in.co.vyapari.ui.generic.MyTextWatcher;
import in.co.vyapari.ui.generic.showcase.Showcase;
import in.co.vyapari.ui.listener.ClickListener;
import in.co.vyapari.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyapari.ui.listener.RecyclerViewClickListener;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.FilterUtil;
import in.co.vyapari.util.Utils;

public class ProductsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.qr_mag_icon)
    ImageView qrOrSearchIcon;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.products_rv)
    RecyclerView productsRV;
    @BindView(R.id.products_ll)
    LinearLayout productsLL;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Context mContext;

    private ProductsAdapter productsAdapter;
    private ArrayList<ProductSum> productSumList = new ArrayList<>();
    private OnScrollListener endlessSV;

    private String searchedText;
    private boolean isClickable;
    private boolean isSearched;

    public ProductsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, rootView);

        config();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getProductsCall(true);
            }
        });

        return rootView;
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
                getProductsCall(page, false);
            }
        };
        productsRV.addOnScrollListener(endlessSV);
        productsRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, productsRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isClickable) {
                    changeColor(position, true);
                    getProductDetailCall(productSumList.get(position).getId(), position);
                }
            }

            @Override
            public void onLongClick(View view, final int position) {

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

    public void searchClick() {
        isSearched = true;
        getProductsCall(true);
    }

    @OnClick(R.id.qr_mag_icon)
    public void qrClick() {
        if (searchedText != null) {
            searchClick();
        } else {
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
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
                            productsLL.setVisibility(View.GONE);
                            productSumList.addAll(productsSumDTO.getProducts());
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                if (searchedText == null) {
                                    productsLL.setVisibility(View.VISIBLE);
                                }
                            } else {
                                productsLL.setVisibility(View.GONE);
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

    private void getProductDetailCall(String id, final int position) {
        Utils.showLoading(mContext);
        ProductService.getProductDetail(id, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                if (position > -1) {
                    changeColor(position, false);
                }
                Utils.hideLoading();
                if (!response.isError()) {
                    if (response.getData() != null) {
                        ProductDTO productDTO = response.getData();
                        if (productDTO != null) {
                            DataUtil.post(productDTO);
                            startActivity(new Intent(getActivity(), EditProductActivity.class));
                        } else {
                            Toasty.error(mContext, getString(R.string.error)).show();
                        }
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                if (position > -1) {
                    changeColor(position, false);
                }
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
                if (!response.isError()) {
                    if (response.getData() != null) {
                        ProductDTO productDTO = response.getData();
                        if (productDTO != null) {
                            DataUtil.post(productDTO);
                            startActivity(new Intent(getActivity(), EditProductActivity.class));
                        } else {
                            Toasty.error(mContext, getString(R.string.error)).show();
                        }
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

    private void resetList() {
        productSumList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(productSumList);
        productsRV.setAdapter(productsAdapter);
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

    @OnClick(R.id.fab)
    public void fabClick() {
        getActivity().startActivity(new Intent(mContext, CreateProductActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                getProductDetailWithBarcodeCall(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected void displayShowcase() {
        Showcase.from(getActivity(), this)
                .setContentView(R.layout.showcase_products)
                .setFitsSystemWindows(true)
                .on(R.id.fab)
                .addCircle()
                .withBorder()
                .openIntent(CreateProductActivity.class, Constants.REFRESH_CODE)
                .showOnce(Constants.PRODUCTS);
    }

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
            Utils.hideLoading();
            getProductsCall(true);
        }
        fab.setVisibility(View.VISIBLE);
    }
}
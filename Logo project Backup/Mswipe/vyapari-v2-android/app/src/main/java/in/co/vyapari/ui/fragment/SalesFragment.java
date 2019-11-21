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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.InvoiceService;
import in.co.vyapari.model.Invoice;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.InvoiceDTO;
import in.co.vyapari.model.response.dto.InvoicesSumDTO;
import in.co.vyapari.model.response.summary.InvoiceSum;
import in.co.vyapari.ui.activity.invoice.CreateInvoiceActivity;
import in.co.vyapari.ui.activity.invoice.InvoiceDetailActivity;
import in.co.vyapari.ui.adapter.invoice.InvoicesAdapter;
import in.co.vyapari.ui.generic.MyTextWatcher;
import in.co.vyapari.ui.generic.showcase.Showcase;
import in.co.vyapari.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.FilterUtil;
import in.co.vyapari.util.Utils;


public class SalesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.sales_rv)
    RecyclerView salesRV;
    @BindView(R.id.sales_ll)
    LinearLayout salesLL;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.layoutFabsale)
    LinearLayout fabRetailSaleInvoice;
    @BindView(R.id.layoutFabwholsale)
    LinearLayout fabWholSaleInvoice;
    private boolean fabExpanded = false;
    private Context mContext;

    private InvoicesAdapter invoicesAdapter;
    private ArrayList<InvoiceSum> invoiceSumList = new ArrayList<>();
    private OnScrollListener endlessSV;

    private String searchedText;
    private boolean isClickable;
    private boolean isSearched;

    public SalesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sales, container, false);
        ButterKnife.bind(this, rootView);

        config();
        //setupRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getSalesCall(true);
            }
        });
        closeSubMenusFab();
        return rootView;
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        salesRV.setItemAnimator(new DefaultItemAnimator());
        salesRV.setHasFixedSize(true);
        salesRV.setNestedScrollingEnabled(false);
        salesRV.setLayoutManager(mLayoutManager);
        endlessSV = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (fab.isShown()) {
                        fab.hide();
                        closeSubMenusFab();
                    }
                } else if (dy < 0) {
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getSalesCall(page, false);
            }
        };


//        salesRV.addOnScrollListener(endlessSV);
//        salesRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, salesRV, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                if (isClickable) {
//                    changeColor(position, true);
//                    DataUtil.post(invoiceSumList.get(position).isActive());
//                    getSaleDetailCall(invoiceSumList.get(position).getId(), position);
//                }
//            }
//
//            @Override
//            public void onLongClick(View view, final int position) {
//
//            }
//        }));

        searchET.setHint(getString(R.string.search_sales));
        searchET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                int count = searchET.getText().toString().length();
                if (count == 0) {
                    searchedText = null;
                    if (isSearched) {
                        isSearched = false;
                        getSalesCall(true);
                    }
                } else {
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

    @OnClick(R.id.search_ok)
    public void searchClick() {
        isSearched = true;
        getSalesCall(true);
    }

    @OnClick(R.id.fabRetailSaleInvoice)
    public void RetailsaleInvoiceClik() {
        closeSubMenusFab();
        Invoice invoice = new Invoice(Constants.SALES_INVOICE);
        DataUtil.post(invoice);
        getActivity().startActivity(new Intent(mContext, CreateInvoiceActivity.class));
    }

    @OnClick(R.id.fabWholSaleInvoice)
    public void FabWholSaleInvoiceClick() {
        closeSubMenusFab();
        Invoice invoice = new Invoice(Constants.SALES_INVOICE);
        DataUtil.post(invoice);
        getActivity().startActivity(new Intent(mContext, CreateInvoiceActivity.class));
    }

    private void getSalesCall(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }
        getSalesCall(1, true);
    }

    private void getSalesCall(final int page, final boolean isClean) {
        Filter filter = FilterUtil.createSaleInvoiceFilter(page, searchedText);
        InvoiceService.getInvoices(filter, new ServiceCall<BaseModel<InvoicesSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoicesSumDTO> response) {
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

                        InvoicesSumDTO invoicesSumDTO = response.getData();
                        if (invoicesSumDTO.getInvoices().size() > 0) {
                            salesLL.setVisibility(View.GONE);
                            invoiceSumList.addAll(invoicesSumDTO.getInvoices());
                            invoicesAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                if (searchedText == null) {
                                    displayShowcase();
                                }
                                salesLL.setVisibility(View.VISIBLE);
                            } else {
                                salesLL.setVisibility(View.GONE);
                            }
                        }

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                resetList();
            }
        });
    }

    private void getSaleDetailCall(String id, final int position) {
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, Constants.SALES_INVOICE, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false);
                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        startActivity(new Intent(mContext, InvoiceDetailActivity.class));
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                changeColor(position, false);
                Utils.hideLoading();
                resetList();
            }
        });
    }

    private void resetList() {

        invoiceSumList = new ArrayList<>();
        invoicesAdapter = new InvoicesAdapter(mContext, invoiceSumList,"Sales");
        salesRV.setAdapter(invoicesAdapter);
    }

    private void changeColor(int position, boolean isClick) {
        int color, color2, color3;
        if (isClick) {
            color = ContextCompat.getColor(mContext, R.color.fab_color);
            color2 = ContextCompat.getColor(mContext, R.color.fab_color);
            color3 = ContextCompat.getColor(mContext, R.color.fab_color);
        } else {
            color = ContextCompat.getColor(mContext, R.color.black);
            color2 = ContextCompat.getColor(mContext, R.color.price_color);
            color3 = ContextCompat.getColor(mContext, R.color.date_color);
        }

        InvoicesAdapter.MyViewHolder holder;
        holder = (InvoicesAdapter.MyViewHolder) salesRV.findViewHolderForLayoutPosition(position);
        holder.firmName.setTextColor(color);
        holder.price.setTextColor(color2);
        holder.iDate.setTextColor(color3);
        holder.status.setTextColor(color3);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        if (fabExpanded == true) {
            closeSubMenusFab();
        } else {
            openSubMenusFab();
        }


    }

    //closes FAB submenus
    private void closeSubMenusFab() {
        fabWholSaleInvoice.setVisibility(View.INVISIBLE);
       // fabRetailSaleInvoice.setVisibility(View.INVISIBLE);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab() {
        fabWholSaleInvoice.setVisibility(View.VISIBLE);
        //fabRetailSaleInvoice.setVisibility(View.VISIBLE);
        fabExpanded = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected void displayShowcase() {
        Invoice invoice = new Invoice(Constants.SALES_INVOICE);
        DataUtil.post(invoice);
        Showcase.from(getActivity(), this)
                .setContentView(R.layout.showcase_invoice)
                .setFitsSystemWindows(true)
                .on(R.id.fab)
                .addCircle()
                .withBorder()
                .openIntent(CreateInvoiceActivity.class, Constants.REFRESH_CODE)
                .showOnce(Constants.INVOICE);
    }

    @Override
    public void onRefresh() {
        isClickable = false;
        swipeRefreshLayout.setRefreshing(true);
        getSalesCall(false);
        closeSubMenusFab();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MobileConstants.invoiceId != null) {
            Utils.hideLoading();
            MobileConstants.invoiceId = null;
            getSalesCall(true);
        }else{

        }
        fab.setVisibility(View.VISIBLE);
        closeSubMenusFab();
    }

}
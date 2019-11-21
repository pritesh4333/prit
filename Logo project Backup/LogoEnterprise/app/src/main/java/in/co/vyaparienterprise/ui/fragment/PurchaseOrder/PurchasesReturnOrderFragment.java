package in.co.vyaparienterprise.ui.fragment.PurchaseOrder;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.InvoiceService;
import in.co.vyaparienterprise.model.Invoice;
import in.co.vyaparienterprise.model.request.filter.Filter;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;
import in.co.vyaparienterprise.model.response.dto.InvoicesSumDTO;
import in.co.vyaparienterprise.model.response.summary.InvoiceSum;
import in.co.vyaparienterprise.ui.activity.Filter.FilterActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.CreatePurchaseInvoiceActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.CreatePurchaseReturnActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.PurchaseInvoiceDetailActivity;
import in.co.vyaparienterprise.ui.adapter.invoice.InvoicesAdapter;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;
import in.co.vyaparienterprise.ui.generic.showcase.Showcase;
import in.co.vyaparienterprise.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyaparienterprise.util.DataUtil;
import in.co.vyaparienterprise.util.FilterUtil;
import in.co.vyaparienterprise.util.Utils;

public class PurchasesReturnOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.purchases_rv)
    RecyclerView purchasesRV;
    @BindView(R.id.purchases_ll)
    LinearLayout purchasesLL;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Context mContext;

    private InvoicesAdapter invoicesAdapter;
    private ArrayList<InvoiceSum> invoiceSumList = new ArrayList<>();
    private OnScrollListener endlessSV;

    private String searchedText;
    private boolean isClickable;
    private boolean isSearched;

    public PurchasesReturnOrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_purchases, container, false);
        ButterKnife.bind(this, rootView);

        config();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getPurchasesCall(true);
            }
        });
        ImageView Filter = (ImageView) getActivity().findViewById(R.id.Filter);
        Filter.setVisibility(View.VISIBLE);
        Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FilterActivity.class);
                i.putExtra("FilterType","PurchaseReturn");
                startActivity(i);
            }
        });

        return rootView;
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        purchasesRV.setItemAnimator(new DefaultItemAnimator());
        purchasesRV.setHasFixedSize(true);
        purchasesRV.setNestedScrollingEnabled(false);
        purchasesRV.setLayoutManager(mLayoutManager);
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
                getPurchasesCall(page, false);
            }
        };
//        purchasesRV.addOnScrollListener(endlessSV);
//        purchasesRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, purchasesRV, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                if (isClickable) {
//                    changeColor(position, true);
//                    DataUtil.post(invoiceSumList.get(position).isActive());
//                    getPurchaseDetailCall(invoiceSumList.get(position).getId(), position);
//                }
//            }
//
//            @Override
//            public void onLongClick(View view, final int position) {
//
//            }
//        }));

        searchET.setHint(getString(R.string.search_return));
        searchET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                int count = searchET.getText().toString().length();
                if (count == 0) {
                    searchedText = null;
                    if (isSearched) {
                        isSearched = false;
                        getPurchasesCall(true);
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
        getPurchasesCall(true);
    }

    private void getPurchasesCall(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }
        getPurchasesCall(1, true);
    }

    private void getPurchasesCall(final int page, final boolean isClean) {
        Filter filter = FilterUtil.createPurchaseReturnFilter(page, searchedText);
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
                            purchasesLL.setVisibility(View.GONE);
                            invoiceSumList.addAll(invoicesSumDTO.getInvoices());
                            invoicesAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                if (searchedText == null) {
                                    displayShowcase();
                                }
                                purchasesLL.setVisibility(View.VISIBLE);
                            } else {
                                purchasesLL.setVisibility(View.GONE);
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

    private void getPurchaseDetailCall(String id, final int position) {
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, Constants.PURCHASE_INVOICE, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                changeColor(position, false);
                Utils.hideLoading();
                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        startActivity(new Intent(mContext, PurchaseInvoiceDetailActivity.class));
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
        invoicesAdapter = new InvoicesAdapter(mContext, invoiceSumList,"PurchaseReturn", purchasesRV);
        purchasesRV.setAdapter(invoicesAdapter);
    }

    private void changeColor(int position, boolean isClick) {
        int color, color2, color3;
        if (isClick) {
            color = ContextCompat.getColor(mContext, R.color.fab_color);
            color2 = ContextCompat.getColor(mContext, R.color.fab_color);
            color3 = ContextCompat.getColor(mContext, R.color.fab_color);
        } else {
            color = ContextCompat.getColor(mContext, R.color.colorAccent);
            color2 = ContextCompat.getColor(mContext, R.color.colorAccent);
            color3 = ContextCompat.getColor(mContext, R.color.colorAccent);
        }

        InvoicesAdapter.MyViewHolder holder;
        holder = (InvoicesAdapter.MyViewHolder) purchasesRV.findViewHolderForLayoutPosition(position);
        holder.firmName.setTextColor(color);
        holder.price.setTextColor(color2);
        holder.iDate.setTextColor(color3);
        holder.status.setTextColor(color3);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        Invoice invoice = new Invoice(Constants.PURCHASE_INVOICE);
        DataUtil.post(invoice);
        getActivity().startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected void displayShowcase() {
        Invoice invoice = new Invoice(Constants.PURCHASE_INVOICE);
        DataUtil.post(invoice);
        Showcase.from(getActivity(), this)
                .setContentView(R.layout.showcase_invoice)
                .setFitsSystemWindows(true)
                .on(R.id.fab)
                .addCircle()
                .withBorder()
                .openIntent(CreatePurchaseInvoiceActivity.class, Constants.REFRESH_CODE)
                .showOnce(Constants.INVOICE);
    }

    @Override
    public void onRefresh() {
        isClickable = false;
        swipeRefreshLayout.setRefreshing(true);
        getPurchasesCall(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MobileConstants.invoiceId != null) {
            MobileConstants.invoiceId = null;
            getPurchasesCall(true);
        }
        fab.setVisibility(View.VISIBLE);
    }
}
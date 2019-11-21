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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import in.co.vyapari.model.response.summary.CollectionSum;
import in.co.vyapari.ui.activity.PaymentCollection.CreateCollectionActivity;
import in.co.vyapari.ui.activity.invoice.CreateInvoiceActivity;
import in.co.vyapari.ui.activity.invoice.InvoiceDetailActivity;
import in.co.vyapari.ui.adapter.CollectionPaymentAdapter;
import in.co.vyapari.ui.adapter.invoice.InvoicesAdapter;
import in.co.vyapari.ui.generic.MyTextWatcher;
import in.co.vyapari.ui.generic.showcase.Showcase;
import in.co.vyapari.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.FilterUtil;
import in.co.vyapari.util.Utils;

public class CollectionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.collection_rv)
    RecyclerView collection_rv;
    @BindView(R.id.collection_ll)
    LinearLayout collection_ll;
    @BindView(R.id.fab_payment)
    FloatingActionButton fab;
    private Context mContext;

    private CollectionPaymentAdapter invoicesAdapter;
    private List<CollectionSum> CollectionList = new ArrayList<>();
    private RecyclerView.OnScrollListener endlessSV;

    private String searchedText;
    private boolean isClickable;
    private boolean isSearched;
    List<CollectionSum> Data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection, container, false);
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
        searchET.setHint(getString(R.string.search_collection));
        return rootView;
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        collection_rv.setItemAnimator(new DefaultItemAnimator());
        collection_rv.setHasFixedSize(true);
        collection_rv.setNestedScrollingEnabled(false);
        collection_rv.setLayoutManager(mLayoutManager);
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
                getCollectionList(page, false);
            }
        };

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



    private void getSalesCall(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }
        getCollectionList(1, true);
    }

    private void getCollectionList(final int page, final boolean isClean) {
        Filter filters = FilterUtil.createCollectionFilter(page, searchedText);
        InvoiceService.getCollections(filters, new ServiceCall<BaseModel<List<CollectionSum>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<List<CollectionSum>> response) {
                if (page == 1) {
                    Utils.hideLoading();
                }
                swipeRefreshLayout.setRefreshing(false);
                isClickable = true;

                if (!response.isError()) {
                    if (response.getData() != null) {
                        if (isClean) {
                            Data = response.getData();
                            Iterator<CollectionSum> i = Data.iterator();

                            while (i.hasNext()) {
                                Object o = i.next();
                                if (((CollectionSum) o).getSlipType()!=2)
                                    //some condition
                                    i.remove();
                            }
                            resetList();
                        }

                        if (Data == null) {
                            Data = new ArrayList<>();
                        }



                        if (Data.size() > 0) {
                            Iterator<CollectionSum> i = Data.iterator();

                            while (i.hasNext()) {
                                Object o = i.next();
                                if (((CollectionSum) o).getSlipType()!=2)
                                    //some condition
                                    i.remove();
                            }
                            CollectionList.addAll(Data);
                            collection_ll.setVisibility(View.GONE);
                            invoicesAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                if (searchedText == null) {
                                  //  displayShowcase();
                                }
                                collection_ll.setVisibility(View.VISIBLE);
                            } else {
                                collection_ll.setVisibility(View.GONE);
                            }
                        }
                        Utils.hideLoading();
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

        invoicesAdapter = new CollectionPaymentAdapter(mContext, Data, 2);
        collection_rv.setAdapter(invoicesAdapter);
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
        holder = (InvoicesAdapter.MyViewHolder) collection_rv.findViewHolderForLayoutPosition(position);
        holder.firmName.setTextColor(color);
        holder.price.setTextColor(color2);
        holder.iDate.setTextColor(color3);
        holder.status.setTextColor(color3);
    }

    @OnClick(R.id.fab_payment)
    public void fabClick() {

        Intent intent= new Intent(mContext, CreateCollectionActivity.class);
        intent.putExtra("Create","true");

        getActivity().startActivity(intent);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        if (MobileConstants.invoiceId != null) {
            Utils.hideLoading();
            MobileConstants.invoiceId = null;
            getSalesCall(true);
        }
        fab.setVisibility(View.VISIBLE);

    }


}
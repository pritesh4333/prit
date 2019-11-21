package in.co.vyaparienterprise.ui.activity.invoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.FirmService;
import in.co.vyaparienterprise.model.Firm;
import in.co.vyaparienterprise.model.request.filter.Filter;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.FirmDTO;
import in.co.vyaparienterprise.model.response.dto.FirmsSumDTO;
import in.co.vyaparienterprise.model.response.summary.FirmSum;
import in.co.vyaparienterprise.ui.activity.firm.CreateFirmActivity;
import in.co.vyaparienterprise.ui.adapter.invoice.InvoiceSelectFirmAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;
import in.co.vyaparienterprise.ui.listener.ClickListener;
import in.co.vyaparienterprise.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyaparienterprise.ui.listener.RecyclerViewClickListener;
import in.co.vyaparienterprise.util.FilterUtil;
import in.co.vyaparienterprise.util.Utils;

public class SelectFirmForInvoiceActivity extends SohoActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.firms_rv)
    RecyclerView firmsRV;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Context mContext;

    private InvoiceSelectFirmAdapter invoiceSelectFirmAdapter;
    private ArrayList<FirmSum> firmSumList = new ArrayList<>();
    private RecyclerView.OnScrollListener endlessSV;

    private String searchedText;
    private boolean isClickable;
    private boolean isSearched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_select_for_invoice);
        setToolbarConfig(getString(R.string.select_firm), false);
        ButterKnife.bind(this);
        mContext = this;

        config();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getFirmsCall(true);
            }
        });
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        firmsRV.setItemAnimator(new DefaultItemAnimator());
        firmsRV.setHasFixedSize(true);
        firmsRV.setNestedScrollingEnabled(false);
        firmsRV.setLayoutManager(mLayoutManager);
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
                getFirmsCall(page, false);
            }
        };
        firmsRV.addOnScrollListener(endlessSV);

        firmsRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, firmsRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isClickable) {
                    changeColor(position, true);
                    getFirmDetailAndAddCall(firmSumList.get(position).getId(), position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        searchET.setHint(getString(R.string.search_firm));
        searchET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                int count = searchET.getText().toString().length();
                if (count == 0) {
                    searchedText = null;
                    if (isSearched) {
                        isSearched = false;
                        getFirmsCall(true);
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
        getFirmsCall(true);
    }

    private void getFirmsCall(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }

        getFirmsCall(1, true);
    }

    private void getFirmsCall(final int page, final boolean isClean) {
        Filter filter = FilterUtil.createFilter(page, true, searchedText);
        FirmService.getFirms(filter, new ServiceCall<BaseModel<FirmsSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmsSumDTO> response) {
                if (page == 1) {
                    Utils.hideLoading();
                }
                swipeRefreshLayout.setRefreshing(false);
                isClickable = true;
                if (response.getData() != null) {
                    if (isClean) {
                        resetList();
                    }
                    FirmsSumDTO firmsSumDTO = response.getData();
                    ArrayList<FirmSum> firmsData = new ArrayList<>();

                    for (FirmSum firmSum : firmsSumDTO.getFirms()) {
                        if (firmSum.isActive()) {
                            firmsData.add(firmSum);
                        }
                    }

                    if (page == 1 && firmsData.size() == 0 && searchedText == null) {
                        fabClick();
                    }

                    if (firmsData.size() > 0) {
                        firmSumList.addAll(firmsData);
                        invoiceSelectFirmAdapter.notifyDataSetChanged();
                    }
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

    private void resetList() {
        firmSumList = new ArrayList<>();
        invoiceSelectFirmAdapter = new InvoiceSelectFirmAdapter(mContext, firmSumList);
        firmsRV.setAdapter(invoiceSelectFirmAdapter);
    }

    private void changeColor(int position, boolean isClick) {
        int color;
        if (isClick) {
            color = ContextCompat.getColor(mContext, R.color.fab_color);
        } else {
            color = ContextCompat.getColor(mContext, R.color.black);
        }

        InvoiceSelectFirmAdapter.MyViewHolder holder;
        holder = (InvoiceSelectFirmAdapter.MyViewHolder) firmsRV.findViewHolderForLayoutPosition(position);
        holder.firmName.setTextColor(color);
        holder.distCity.setTextColor(color);
    }

    private void getFirmDetailAndAddCall(String id, final int position) {
        Utils.showLoading(mContext);
        FirmService.getFirmDetail(id, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                changeColor(position, false);
                Utils.hideLoading();
                if (response.getData() != null) {
                    FirmDTO firmDTO = response.getData();
                    if (firmDTO != null) {
                        Firm firm = new Firm(firmDTO);
                        returnResultFinish(firm);
                        finish();
                    } else {
                        Toasty.error(mContext, getString(R.string.error)).show();
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                changeColor(position, false);
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivityForResult(new Intent(mContext, CreateFirmActivity.class), Constants.REFRESH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REFRESH_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                boolean result = data.getBooleanExtra(Constants.REFRESH, false);
                if (result) {
                    getFirmsCall(true);
                }
            }
        }
    }

    private void returnResultFinish(Firm firm) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.FIRM, firm);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onRefresh() {
        isClickable = false;
        swipeRefreshLayout.setRefreshing(true);
        getFirmsCall(false);
    }
}
package in.co.vyapari.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.FirmService;
import in.co.vyapari.model.Firm;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.FirmDTO;
import in.co.vyapari.model.response.dto.FirmsSumDTO;
import in.co.vyapari.model.response.summary.FirmSum;
import in.co.vyapari.ui.activity.firm.CreateFirmActivity;
import in.co.vyapari.ui.activity.firm.EditFirmActivity;
import in.co.vyapari.ui.adapter.FirmsAdapter;
import in.co.vyapari.ui.generic.showcase.Showcase;
import in.co.vyapari.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyapari.ui.listener.FirmClickListener;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.FilterUtil;
import in.co.vyapari.util.Utils;

public class FirmsFragment extends Fragment implements FirmClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.firms_rv)
    RecyclerView firmsRV;
    @BindView(R.id.firms_ll)
    LinearLayout firmsLL;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Context mContext;

    private FirmsAdapter firmsAdapter;
    private ArrayList<FirmSum> firmSumList = new ArrayList<>();
    private OnScrollListener endlessSV;

    private String searchedText;
    private boolean isClickable;
    private boolean isSearched;

    public FirmsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_firms, container, false);
        ButterKnife.bind(this, rootView);

        config();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getFirmsCall(true);
            }
        });

        return rootView;
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

        searchET.setHint(getString(R.string.search_firm));

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

    @Override
    public void onItemClick(int position) {
        if (isClickable) {
            if (firmSumList.get(position).isActive()) {
                changeColor(position, true);
                getFirmDetailCall(firmSumList.get(position).getId(), position);
            } else {
                Toasty.error(mContext, getString(R.string.warn_ar_ap_passive_msg)).show();
            }
        }
    }

    @Override
    public void onItemLongClick(int position) {
    }

    @Override
    public void doPhoneCall(int position) {
        if (isClickable) {
            final FirmSum firmSum = firmSumList.get(position);
            final ArrayList<KeyValue> phoneList = Utils.createContactAndEmployeePhoneList(firmSum);
            if (phoneList.size() > 0) {
                Utils.createPhoneCallDialog(mContext, phoneList, new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        String phone = phoneList.get(position).getValue();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                }).show();
            }
        }
    }

    private void getFirmsCall(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }
        getFirmsCall(1, true);
    }

    private void getFirmsCall(final int page, final boolean isClean) {
        Filter filter = FilterUtil.createFilter(page, searchedText);
        FirmService.getFirms(filter, new ServiceCall<BaseModel<FirmsSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmsSumDTO> response) {
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

                        FirmsSumDTO firmsSumDTO = response.getData();
                        if (firmsSumDTO.getFirms().size() > 0) {
                            firmsLL.setVisibility(View.GONE);
                            firmSumList.addAll(firmsSumDTO.getFirms());
                            firmsAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                if (searchedText == null) {
                                    displayShowcase();
                                }
                                firmsLL.setVisibility(View.VISIBLE);
                            } else {
                                firmsLL.setVisibility(View.GONE);
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

    private void getFirmDetailCall(String id, final int position) {
        Utils.showLoading(mContext);
        FirmService.getFirmDetail(id, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                changeColor(position, false);
                Utils.hideLoading();
                if (!response.isError()) {
                    if (response.getData() != null) {
                        FirmDTO firmDTO = response.getData();
                        Firm firm = new Firm(firmDTO);
                        if (firm != null) {
                            DataUtil.post(firm);
                            startActivity(new Intent(getActivity(), EditFirmActivity.class));
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
                changeColor(position, false);
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    private void resetList() {
        firmSumList = new ArrayList<>();
        firmsAdapter = new FirmsAdapter(this, mContext, firmSumList);
        firmsRV.setAdapter(firmsAdapter);
    }

    private void changeColor(int position, boolean isClick) {
        int color;
        if (isClick) {
            color = ContextCompat.getColor(mContext, R.color.fab_color);
        } else {
            color = ContextCompat.getColor(mContext, R.color.black);
        }

        FirmsAdapter.MyViewHolder holder;
        holder = (FirmsAdapter.MyViewHolder) firmsRV.findViewHolderForLayoutPosition(position);
        holder.firmName.setTextColor(color);
        holder.distCity.setTextColor(color);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        getActivity().startActivity(new Intent(mContext, CreateFirmActivity.class));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected void displayShowcase() {
        Showcase.from(getActivity(), this)
                .setContentView(R.layout.showcase_firms)
                .setFitsSystemWindows(true)
                .on(R.id.fab)
                .addCircle()
                .withBorder()
                .openIntent(CreateFirmActivity.class, Constants.REFRESH_CODE)
                .showOnce(Constants.FIRMS);
    }

    @Override
    public void onRefresh() {
        isClickable = false;
        swipeRefreshLayout.setRefreshing(true);
        getFirmsCall(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MobileConstants.firmId != null) {
            MobileConstants.firmId = null;
            Utils.hideLoading();
            getFirmsCall(true);
        }
        fab.setVisibility(View.VISIBLE);
    }
}
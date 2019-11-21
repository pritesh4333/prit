package in.co.vyaparienterprise.ui.activity.common.hsnsac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.CommonService;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.ui.adapter.HSNSACAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;
import in.co.vyaparienterprise.ui.listener.ClickListener;
import in.co.vyaparienterprise.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyaparienterprise.ui.listener.RecyclerViewClickListener;
import in.co.vyaparienterprise.util.DataUtil;
import in.co.vyaparienterprise.util.Utils;

public class HSNSACCodesActivity extends SohoActivity {

    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.hsnsac_codes_rv)
    RecyclerView hsnsacRV;

    private Context mContext;

    private HSNSACAdapter hsnsacAdapter;
    private ArrayList<KeyValue> hsnsacList = new ArrayList<>();
    private OnScrollListener endlessSV;

    private String searchedText;
    private String productType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsnsac_codes);
        ButterKnife.bind(this);
        setToolbarConfig(R.string.hsn_sac_codes);
        mContext = this;

        productType = DataUtil.getBundleAndRemove(String.class);

        config();
        getHSNSACCodes(true);
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        hsnsacRV.setItemAnimator(new DefaultItemAnimator());
        hsnsacRV.setHasFixedSize(true);
        hsnsacRV.setNestedScrollingEnabled(false);
        hsnsacRV.setLayoutManager(mLayoutManager);
        endlessSV = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //getHSNSACCodesCall(false);
            }
        };
        hsnsacRV.addOnScrollListener(endlessSV);
        hsnsacRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, hsnsacRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                KeyValue hsnsac = hsnsacList.get(position);
                returnResultFinish(hsnsac);
            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));

        searchET.setHint(getString(R.string.search_hsn_sac_codes));
        searchET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                int count = searchET.getText().toString().length();
                if (count > 2) {
                    searchedText = searchET.getText().toString();
                    getHSNSACCodes(false);
                } else if (count == 0) {
                    searchedText = null;
                    getHSNSACCodes(true);
                }
            }
        });
    }

    private void getHSNSACCodes(boolean isLoadingShow) {
        if (isLoadingShow) {
            Utils.showLoading(mContext);
        }
        getHSNSACCodesCall(true);
    }

    private void getHSNSACCodesCall(final boolean isClean) {
        CommonService.getHSNSACCodes(productType, searchedText, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                Utils.hideLoading();
                if (response.getData() != null) {
                    if (isClean) {
                        resetList();
                    }

                    ArrayList<KeyValue> hsnsacResponseList = new ArrayList<>();
                    for (KeyValue keyValue : response.getData()) {
                        if (!Utils.equalsKeyValue(keyValue, Constants.EMPTY_KEYVALUE)) {
                            String value = keyValue.getValue().replace("\n", " ");
                            keyValue.setValue(value);
                            hsnsacResponseList.add(keyValue);
                        }
                    }

                    if (hsnsacResponseList.size() > 0) {
                        hsnsacList.addAll(hsnsacResponseList);
                        hsnsacAdapter.notifyDataSetChanged();
                    } else {
                        //TODO: arkaplan görseli ekle
                    }

                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                resetList();
            }
        });
    }

    private void resetList() {
        hsnsacList = new ArrayList<>();
        hsnsacAdapter = new HSNSACAdapter(hsnsacList);
        hsnsacRV.setAdapter(hsnsacAdapter);
    }

    private void returnResultFinish(KeyValue hsnsac) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.HSNSAC, hsnsac);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
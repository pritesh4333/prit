package com.acumengroup.mobile.MutualFund;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.FundsModel;
import com.acumengroup.mobile.model.FundsModelResponce;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class MFDetailsFragment extends GreekBaseFragment {

    private List<FundsModel> productList;
    private RecyclerView recyclerView;
    private RelativeLayout errorLayout;
    private ProductAdapter adapter;
    private String typeOfRequest;
    private TabLayout bottomNavigation;


    public MFDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeOfRequest = getArguments().getString("type");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mfdetails).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mfdetails).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }


        //getting the recyclerview from xml
        recyclerView = view.findViewById(R.id.recyclerView);
        bottomNavigation = view.findViewById(R.id.tabs);

        bottomNavigation.addTab(bottomNavigation.newTab().setText("1W"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("1M"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("3M"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("1Y"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("3Y"));
        bottomNavigation.addTab(bottomNavigation.newTab().setText("5Y"));

        TabLayout.Tab tab = bottomNavigation.getTabAt(4);
        tab.select();


        bottomNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (adapter != null)

                    switch (tab.getPosition()) {

                        case 0:
                            adapter.updateReturns("1W");
                            break;
                        case 1:
                            adapter.updateReturns("1M");
                            break;
                        case 2:
                            adapter.updateReturns("3M");
                            break;
                        case 3:
                            adapter.updateReturns("1Y");
                            break;
                        case 4:
                            adapter.updateReturns("3Y");
                            break;
                        case 5:
                            adapter.updateReturns("5Y");
                            break;

                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        errorLayout = view.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        //initializing the productlist
        productList = new ArrayList<>();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle args = new Bundle();
                        args.putString("schemCode", productList.get(position).getSchemeCode());
                        args.putString("tradingISIN", productList.get(position).getISIN());
                        args.putString("sipISIN", productList.get(position).getISIN());
                        args.putString("schemeName", productList.get(position).getSchemeName());

                        args.putString("From", "marketmutualfund");
                        navigateTo(NAV_TO_MUTUALFUND_GET_QUOTE, args, true);
                    }
                })
        );

        getDataRequest(typeOfRequest);
        return view;
    }

    private void getDataRequest(String Type) {

        String serviceURL = "getSchemeWiseCategory?type=" + Type;

        showProgress();
        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
                toggleErrorLayout(false);

                FundsModelResponce fundsModelResponce = new FundsModelResponce();
                try {

                    productList = fundsModelResponce.fromJSON(response);
                    adapter = new ProductAdapter(getMainActivity(), productList);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    toggleErrorLayout(false);

                }
            }

            @Override
            public void onFailure(String message) {
                toggleErrorLayout(true);
                hideProgress();
            }
        });

    }


    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


        private Context mCtx;
        private List<FundsModel> productList;

        public ProductAdapter(Context mCtx, List<FundsModel> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.row_mf_details_layout, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            FundsModel product = productList.get(position);

            if (product.getStarRating() != null) {

                float rateing = Float.parseFloat(product.getStarRating());

                if (rateing < 0) {
                    holder.ratingBar.setVisibility(View.INVISIBLE);

                } else {

                    holder.ratingBar.setRating(rateing);
                }
            } else {

                holder.ratingBar.setVisibility(View.INVISIBLE);
            }

            holder.schemeName.setText(product.getSchemeName());
            holder.NavRs.setText(String.format("%.2f", Double.parseDouble(product.getNAV())));

            if (!product.isUpdateFlag()) {

                String value = String.format("%.2f", Double.parseDouble(product.getThreeYearRet()));
                holder.returns_percent.setText(value + "%");
                holder.returns_label.setText("3Y Returns");

            } else {

                String value = String.format("%.2f", Double.parseDouble(product.getUpdate_return_per()));
                holder.returns_percent.setText(value + "%");
                holder.returns_label.setText(product.getUpdate_return_label());
            }

            holder.aum.setText(String.format("%.2f", Double.parseDouble(product.getAUM())));
        }

        public void updateReturns(String type) {

            for (int i = 0; i < productList.size(); i++) {

                FundsModel model = new FundsModel();
                model = productList.get(i);
                model.setUpdateFlag(true);

                if (type.equalsIgnoreCase("1W")) {

                    model.setUpdate_return_per(productList.get(i).getOneWeekRet());
                    model.setUpdate_return_label("1W Returns");

                } else if (type.equalsIgnoreCase("1M")) {
                    model.setUpdate_return_per(productList.get(i).getOneMonthRet());
                    model.setUpdate_return_label("1M Returns");

                } else if (type.equalsIgnoreCase("3M")) {

                    model.setUpdate_return_per(productList.get(i).getThreeMonthRet());
                    model.setUpdate_return_label("3M Returns");

                } else if (type.equalsIgnoreCase("1Y")) {

                    model.setUpdate_return_per(productList.get(i).getOneYearRet());
                    model.setUpdate_return_label("1Y Returns");

                } else if (type.equalsIgnoreCase("3Y")) {

                    model.setUpdate_return_per(productList.get(i).getThreeYearRet());
                    model.setUpdate_return_label("3Y Returns");

                } else if (type.equalsIgnoreCase("5Y")) {

                    model.setUpdate_return_per(productList.get(i).getFiveYearRet());
                    model.setUpdate_return_label("5Y Returns");
                }


                productList.set(i, model);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }


        class ProductViewHolder extends RecyclerView.ViewHolder {

            GreekTextView schemeName, NavRs, category, starRating, returns_percent, returns_label, aum;
            ImageView imageView;
            MaterialRatingBar ratingBar;

            public ProductViewHolder(View itemView) {

                super(itemView);

                schemeName = itemView.findViewById(R.id.schemeName);
                returns_percent = itemView.findViewById(R.id.returns);
                NavRs = itemView.findViewById(R.id.NavRs);
                starRating = itemView.findViewById(R.id.starRating);
                imageView = itemView.findViewById(R.id.imageView);
                returns_label = itemView.findViewById(R.id.textViewReturn);
                aum = itemView.findViewById(R.id.aum);
                ratingBar = itemView.findViewById(R.id.ratingBar);
            }
        }
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
        AccountDetails.currentFragment = NAV_TO_MFUND_DETAILS_SCREEN;
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MFUND_DETAILS_SCREEN;

    }
}

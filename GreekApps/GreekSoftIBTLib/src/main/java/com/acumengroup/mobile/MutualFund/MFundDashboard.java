package com.acumengroup.mobile.MutualFund;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.FundsModel;
import com.acumengroup.mobile.model.FundsModelResponce;
import com.acumengroup.mobile.model.MFModel;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class MFundDashboard extends GreekBaseFragment {

    private GridViewAdapter adapter;
    private List<FundsModel> productList, productList_bottom;
    private RecyclerView recyclerView, recyclerView_bottom;
    private RelativeLayout errorLayout;
    private MFundDashboard.RecyclerviewAdapter recycler_adapter;
    private GreekTextView txt_see_more, empty_view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_mfund).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_mfund).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        setAppTitle(getClass().toString(), "Mutual Fund");

        GridView gv = view.findViewById(R.id.gv);
        adapter = new GridViewAdapter(getContext(), getData());
        gv.setAdapter(adapter);

        txt_see_more = view.findViewById(R.id.see_more);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView_bottom = view.findViewById(R.id.recyclerView_bottom);
        empty_view = view.findViewById(R.id.empty_view);
        empty_view.setVisibility(View.GONE);

        errorLayout = view.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");

        recyclerView_bottom.setHasFixedSize(true);
        recyclerView_bottom.setLayoutManager(new LinearLayoutManager(getMainActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity(), LinearLayoutManager.HORIZONTAL, false));

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            txt_see_more.setTextColor(getResources().getColor(R.color.blue_600));
            empty_view.setTextColor(getResources().getColor(R.color.black));

        } else {

            empty_view.setTextColor(getResources().getColor(R.color.white));
        }

        SpannableString content = new SpannableString("View All");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt_see_more.setText(content);

        txt_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("type", "All");
                navigateTo(NAV_TO_MFUND_DETAILS_SCREEN, bundle, true);

            }
        });
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

        recyclerView_bottom.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle args = new Bundle();
                        args.putString("schemCode", productList_bottom.get(position).getSchemeCode());
                        args.putString("tradingISIN", productList_bottom.get(position).getISIN());
                        args.putString("sipISIN", productList_bottom.get(position).getISIN());
                        args.putString("schemeName", productList_bottom.get(position).getSchemeName());
                        args.putString("From", "marketmutualfund");
                        navigateTo(NAV_TO_MUTUALFUND_GET_QUOTE, args, true);
                    }
                })
        );

        getDataRequest("highreturn");
        getDataRequestBoottom("preferred");

        return view;
    }


    private void getDataRequestBoottom(String Type) {

        String serviceURL = "getSchemeWiseCategory?type=" + Type;

        showProgress();
        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
                FundsModelResponce fundsModelResponce = new FundsModelResponce();
                try {

                    productList_bottom = fundsModelResponce.fromJSON(response);
                    recycler_adapter = new MFundDashboard.RecyclerviewAdapter(getMainActivity(), productList_bottom);
                    recyclerView_bottom.setAdapter(recycler_adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    empty_view.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onFailure(String message) {
                empty_view.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getDataRequest(String Type) {

        String serviceURL = "getSchemeWiseCategory?type=" + Type;

        showProgress();
        WSHandler.getRequest(getMainActivity(), serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();

                FundsModelResponce fundsModelResponce = new FundsModelResponce();
                try {

                    productList = fundsModelResponce.fromJSON(response);
                    recycler_adapter = new MFundDashboard.RecyclerviewAdapter(getMainActivity(), productList);
                    recyclerView.setAdapter(recycler_adapter);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(String message) {
                toggleErrorLayout(true);
            }
        });

    }

    public class RecyclerviewAdapter extends RecyclerView.Adapter<MFundDashboard.RecyclerviewAdapter.ProductViewHolder> {


        //this context we will use to inflate the layout
        private Context mCtx;
        //we are storing all the products in a list
        private List<FundsModel> productList;
        private SnapHelper snapHelper;

        //getting the context and product list with constructor
        public RecyclerviewAdapter(Context mCtx, List<FundsModel> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }

        @Override
        public MFundDashboard.RecyclerviewAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.item_row_mf_layout, null);

            return new MFundDashboard.RecyclerviewAdapter.ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MFundDashboard.RecyclerviewAdapter.ProductViewHolder holder, int position) {
            //getting the product of the specified position
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

            //binding the data with the viewholder views
            holder.schemeName.setText(product.getSchemeName());
            holder.NavRs.setText(String.format("%.2f", Double.parseDouble(product.getNAV())));
            String value = String.format("%.2f", Double.parseDouble(product.getOneYearRet()));
            holder.returns.setText(value + "%");
            holder.aum.setText(String.format("%.2f", Double.parseDouble(product.getAUM())));

        }


        @Override
        public int getItemCount() {
            return productList.size();
        }


        class ProductViewHolder extends RecyclerView.ViewHolder {

            GreekTextView schemeName, NavRs, starRating, returns, ViewReturn, aum;
            ImageView imageView;
            MaterialRatingBar ratingBar;


            public ProductViewHolder(View itemView) {
                super(itemView);

                schemeName = itemView.findViewById(R.id.schemeName);
                returns = itemView.findViewById(R.id.returns);
                NavRs = itemView.findViewById(R.id.NavRs);
                // category = (TextView) itemView.findViewById(R.id.category);
                starRating = itemView.findViewById(R.id.starRating);
                aum = itemView.findViewById(R.id.aum);
                ViewReturn = itemView.findViewById(R.id.textViewReturn);
                imageView = itemView.findViewById(R.id.imageView);
                ratingBar = itemView.findViewById(R.id.ratingBar);
            }
        }
    }

    private ArrayList getData() {
        ArrayList<MFModel> spacecrafts = new ArrayList<>();

        MFModel s = new MFModel();
        s.setName("Top Tax Saving Funds");
        s.setType("Taxsaving");
        s.setImage(R.drawable.ic_tax_save);
        spacecrafts.add(s);

        s = new MFModel();
        s.setName("Top Low Risk Funds");
        s.setType("lowrisk");
        s.setImage(R.drawable.ic_risk_low);
        spacecrafts.add(s);

        s = new MFModel();
        s.setName("Top High Return Funds");
        s.setType("highreturn");
        s.setImage(R.drawable.ic_return_high);
        spacecrafts.add(s);

        s = new MFModel();
        s.setName("Balance Return & Risk");
        s.setType("balance");
        s.setImage(R.drawable.ic_return_balance);
        spacecrafts.add(s);

        s = new MFModel();
        s.setName("Top Large Cap Funds");
        s.setType("largecap");
        s.setImage(R.drawable.ic_cap_large);
        spacecrafts.add(s);

        s = new MFModel();
        s.setName("Top Small & Mid Cap Funds");
        s.setType("midcap");
        s.setImage(R.drawable.ic_small_mid);
        spacecrafts.add(s);


        return spacecrafts;
    }

    public class GridViewAdapter extends BaseAdapter {
        Context c;

        ArrayList<MFModel> spacecrafts;

        public GridViewAdapter(Context c, ArrayList<MFModel> spacecrafts) {
            this.c = c;
            this.spacecrafts = spacecrafts;
        }

        @Override
        public int getCount() {
            return spacecrafts.size();
        }

        @Override
        public Object getItem(int i) {
            return spacecrafts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(c).inflate(R.layout.grid_row_layout, viewGroup, false);
            }

            final MFModel s = (MFModel) this.getItem(i);

            ImageView img = view.findViewById(R.id.image);
            GreekTextView nameTxt = view.findViewById(R.id.txt_titile);

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

                nameTxt.setTextColor(getResources().getColor(R.color.black));
            } else {
                nameTxt.setTextColor(getResources().getColor(R.color.black));
            }

            //BIND
            nameTxt.setText(s.getName());
            img.setImageResource(s.getImage());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("type", s.getType());

                    navigateTo(NAV_TO_MFUND_DETAILS_SCREEN, bundle, true);

                }
            });

            return view;
        }

    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MFUND_DASHBOARD_SCREEN;
    }
}

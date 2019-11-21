package in.co.vyaparienterprise.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.response.summary.ProductSum;
import in.co.vyaparienterprise.util.CurrencyUtil;
import in.co.vyaparienterprise.util.Utils;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private ArrayList<ProductSum> productSumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.product_item_top_view)
//        View topArea;
        @BindView(R.id.product_item_letter_point)
        RelativeLayout deactivePoint;
        @BindView(R.id.product_item_name)
        public TextView productName;
        @BindView(R.id.product_item_inventory)
        public TextView inventory;
        @BindView(R.id.product_item_sale_price)
        public TextView salePrice;
        @BindView(R.id.product_item_purchase_price)
        public TextView purchasePrice;
//        @BindView(R.id.product_item_bottom_view)
//        View bottomView;
//        @BindView(R.id.product_item_bottom_rl)
//        RelativeLayout bottomAreaRL;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ProductsAdapter(ArrayList<ProductSum> productSumList) {
        if (productSumList == null) {
            productSumList = new ArrayList<>();
        }
        this.productSumList = productSumList;
    }

    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.MyViewHolder holder, final int position) {
        ProductSum productSum = productSumList.get(position);
        holder.productName.setText(productSum.getName());
        if (Utils.isInteger(productSum.getPhysicalInventory())) {
            String temp = String.valueOf((int) productSum.getPhysicalInventory());
            holder.inventory.setText(temp);
        } else {

            String temp = String.valueOf(productSum.getPhysicalInventory());
            holder.inventory.setText(temp);
        }

        if(String.valueOf(productSum.getCardType()).equalsIgnoreCase("30")){
            holder.inventory.setText("N/A");
        }
        holder.salePrice.setText(CurrencyUtil.doubleToCurrency(productSum.getSalesPrice(), productSum.getSaleCurrency()));
        holder.purchasePrice.setText(CurrencyUtil.doubleToCurrency(productSum.getPurchasePrice(), productSum.getPurchaseCurrency()));

        if (productSum.isActive()) {
            holder.deactivePoint.setVisibility(View.GONE);
        } else {
            holder.deactivePoint.setVisibility(View.VISIBLE);
        }

//        if (position == 0) {
//            holder.topArea.setVisibility(View.VISIBLE);
//            holder.bottomView.setVisibility(View.VISIBLE);
//            holder.bottomAreaRL.setVisibility(View.GONE);
//        } else if (position == productSumList.size() - 1) {
//            holder.topArea.setVisibility(View.GONE);
//            holder.bottomView.setVisibility(View.GONE);
//            holder.bottomAreaRL.setVisibility(View.VISIBLE);
//        } else {
//            holder.topArea.setVisibility(View.GONE);
//            holder.bottomView.setVisibility(View.VISIBLE);
//            holder.bottomAreaRL.setVisibility(View.GONE);
//        }
//
//        if (productSumList.size() == 1) {
//            holder.topArea.setVisibility(View.VISIBLE);
//            holder.bottomView.setVisibility(View.GONE);
//            holder.bottomAreaRL.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return productSumList.size();
    }
}
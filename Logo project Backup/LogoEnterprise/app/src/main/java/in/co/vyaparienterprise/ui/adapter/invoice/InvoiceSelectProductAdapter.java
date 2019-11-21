package in.co.vyaparienterprise.ui.adapter.invoice;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.response.summary.ProductSum;
import in.co.vyaparienterprise.util.CurrencyUtil;
import in.co.vyaparienterprise.util.Utils;

/**
 * Created by Bekir.Dursun on 7.11.2017.
 */

public class InvoiceSelectProductAdapter extends RecyclerView.Adapter<InvoiceSelectProductAdapter.MyViewHolder> {

    private ArrayList<ProductSum> productSumList;
    private HashMap<String, Integer> selectedProducts;
    private int color1, color2;

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.product_item_top_view)
//        View topArea;
        @BindView(R.id.product_item_name)
        public
TextView productName;
        @BindView(R.id.product_item_inventory)
        public TextView inventory;
        @BindView(R.id.product_item_sale_price)
        public
        TextView salePrice;
        @BindView(R.id.product_item_purchase_price)
        public
        TextView purchasePrice;
//        @BindView(R.id.product_item_bottom_view)
//        View bottomView;
//        @BindView(R.id.product_item_bottom_rl)
//        RelativeLayout bottomAreaRL;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public InvoiceSelectProductAdapter(ArrayList<ProductSum> productSumList, HashMap<String, Integer> selectedProducts) {
        if (productSumList == null) {
            productSumList = new ArrayList<>();
        }
        this.productSumList = productSumList;
        this.selectedProducts = selectedProducts;

        this.color1 = Color.parseColor("#00ce16");
        this.color2 = Color.parseColor("#000000");
    }

    @Override
    public InvoiceSelectProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InvoiceSelectProductAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(final InvoiceSelectProductAdapter.MyViewHolder holder, final int position) {
        ProductSum productSum = productSumList.get(position);
        holder.productName.setText(productSum.getName());
        if (Utils.isInteger(productSum.getPhysicalInventory())) {
            String temp = String.valueOf((int) productSum.getPhysicalInventory());
            holder.inventory.setText(temp);
        } else {
            String temp = String.valueOf(productSum.getPhysicalInventory());
            holder.inventory.setText(temp);
        }
        holder.salePrice.setText(CurrencyUtil.doubleToCurrency(productSum.getSalesPrice(), productSum.getSaleCurrency()));
        holder.purchasePrice.setText(CurrencyUtil.doubleToCurrency(productSum.getPurchasePrice(), productSum.getPurchaseCurrency()));

        String key = productSumList.get(position).getId() + "-" + productSumList.get(position).getName();
//        if (selectedProducts.get(key) != null && selectedProducts.get(key) == 1) {
//            holder.productName.setTextColor(color1);
//        } else {
//            holder.productName.setTextColor(color2);
//        }

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
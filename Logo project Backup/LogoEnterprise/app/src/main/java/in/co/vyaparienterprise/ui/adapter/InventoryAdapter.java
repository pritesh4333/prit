package in.co.vyaparienterprise.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.response.summary.InventoryProductSum;
import in.co.vyaparienterprise.model.response.summary.ProductSum;
import in.co.vyaparienterprise.ui.activity.product.UpdateInventryActivity;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;
import in.co.vyaparienterprise.util.CurrencyUtil;
import in.co.vyaparienterprise.util.Utils;

import static in.co.vyaparienterprise.ui.activity.product.UpdateInventryActivity.productSumListOrignal;
import static in.co.vyaparienterprise.ui.activity.product.UpdateInventryActivity.productlistEdit;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

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
        @BindView(R.id.select_product_dfs_remove_count)
        public ImageView select_product_dfs_remove_count;
        @BindView(R.id.select_product_dfs_add_count)
        public ImageView select_product_dfs_add_count;



//        @BindView(R.id.product_item_bottom_view)
//        View bottomView;
//        @BindView(R.id.product_item_bottom_rl)
//        RelativeLayout bottomAreaRL;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public InventoryAdapter(ArrayList<ProductSum> productSumList) {
        if (productSumList == null) {
            productSumList = new ArrayList<>();
        }
        this.productSumList = productSumList;
    }

    @Override
    public InventoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InventoryAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(final InventoryAdapter.MyViewHolder holder, final int position) {
        final ProductSum productSum = productSumList.get(position);
        if (productSum.getCardType()==1) {

            holder.productName.setText(productSum.getName());

            if (Utils.isInteger(productSum.getPhysicalInventory())) {
                String temp = String.valueOf((int) productSum.getPhysicalInventory());
                holder.inventory.setText(temp);
            } else {

                String temp = String.valueOf(productSum.getPhysicalInventory());
                holder.inventory.setText(temp);
            }
            if (String.valueOf(productSum.getCardType()).equalsIgnoreCase("30")) {
                holder.inventory.setText("0");
            }
            for (int ii = 0; ii < productlistEdit.size(); ii++) {

                if (productSum.getCode().equalsIgnoreCase(productlistEdit.get(ii).getCode())) {
                    Double iik = productlistEdit.get(ii).getActualInventory();
                    Log.e("Code"+productSum.getCode(),"value"+productSum.getActualInventory());
                    Log.e("Code Edit"+productlistEdit.get(ii).getCode(),"valueEdit"+productlistEdit.get(ii).getActualInventory());
                    int kk = iik.intValue();
                    holder.inventory.setText("" + kk);
                }
            }

            if (productSum.isActive()) {
                holder.deactivePoint.setVisibility(View.GONE);
            } else {
                holder.deactivePoint.setVisibility(View.VISIBLE);
            }
            holder.select_product_dfs_remove_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String counter = holder.inventory.getText().toString().trim();
                    if (counter != null && !counter.isEmpty()) {
                        double count = Double.valueOf(counter);
                        count--;
                        //count = count < 1 ? 1 : count;
                        String _count;
                        if (Utils.isInteger(count)) {
                            _count = String.valueOf((int) count);
                        } else {
                            _count = String.valueOf((int) count);
                        }
                        holder.inventory.setText(_count);
                        //invoiceLine.setQuantity(count);
                    }
                }
            });


            holder.select_product_dfs_add_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String counter = holder.inventory.getText().toString().trim();
                    if (counter != null && !counter.isEmpty()) {
                        double count = Double.valueOf(counter);
                        count++;
                        String _count;
                        if (Utils.isInteger(count)) {
                            _count = String.valueOf((int) count);
                        } else {
                            _count = String.valueOf((int) count);
                        }
                        holder.inventory.setText(_count);
                        //invoiceLine.setQuantity(count);
                    }
                }
            });
        }
        holder.inventory.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                int i = position;
                String count = holder.inventory.getText().toString();

                try {
                    if (!count.equalsIgnoreCase("0")) {
                        boolean isitnew = true;
                        boolean isitnewOrignal = true;
                        for (int ii = 0; ii < productlistEdit.size(); ii++) {

                            if (productSum.getCode().equalsIgnoreCase(productlistEdit.get(ii).getCode())) {
                                ProductSum productSumEdit = productlistEdit.get(ii);
                                if (count.equalsIgnoreCase("")){
                                    productSumEdit.setActualInventory(0.0);
                                    productlistEdit.remove(ii);
                                }else {
                                    productSumEdit.setActualInventory(Double.parseDouble(count));
                                }
                                isitnew = false;
                            }

                        }
                        for (int ii = 0; ii < productSumListOrignal.size(); ii++) {

                            if (productSum.getCode().equalsIgnoreCase(productSumListOrignal.get(ii).getCode())) {
                                ProductSum productSumEdit = productSumListOrignal.get(ii);
                                if (count.equalsIgnoreCase("")){
                                    productSumEdit.setActualInventory(0.0);
                                    productSumListOrignal.remove(ii);
                                }else {
                                    productSumEdit.setActualInventory(productSum.getActualInventory());
                                }

                                isitnewOrignal = false;
                            }

                        }
                        if (!count.equalsIgnoreCase("")) {
                            if (isitnew) {
                                ProductSum productSumEdit = new ProductSum();
                                productSumEdit.setCode(productSum.getCode());
                                productSumEdit.setMainUnitReference(productSum.getMainUnitReference());
                                productSumEdit.setActualInventory(Double.parseDouble(count));
                                productlistEdit.add(productSumEdit);
                            }
                            if (isitnewOrignal) {
                                ProductSum productSumOrignal = new ProductSum();
                                productSumOrignal.setCode(productSum.getCode());
                                productSumOrignal.setMainUnitReference(productSum.getMainUnitReference());
                                productSumOrignal.setActualInventory(productSum.getActualInventory());
                                productSumListOrignal.add(productSumOrignal);

                            }
                        }

                    }
                } catch (Exception e) {
                    Log.e("Invalid double", e.getMessage());
                }

            }
        });
//        if (position == 0) {
//            holder.topArea.setVisibility(View.VISIBLE);9
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
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
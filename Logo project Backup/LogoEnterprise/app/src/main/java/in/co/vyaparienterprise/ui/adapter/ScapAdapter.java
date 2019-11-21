package in.co.vyaparienterprise.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.builder.Builders;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.Product;
import in.co.vyaparienterprise.model.response.summary.ProductSum;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;


import static in.co.vyaparienterprise.ui.activity.product.ScrapActivity.productSumListOrignal;
import static in.co.vyaparienterprise.ui.activity.product.ScrapActivity.productlistEdit;


/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class ScapAdapter extends RecyclerView.Adapter<ScapAdapter.MyViewHolder> {

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
//        @BindView(R.id.select_product_dfs_remove_count)
//        public ImageView select_product_dfs_remove_count;
//        @BindView(R.id.select_product_dfs_add_count)
//        public ImageView select_product_dfs_add_count;


//        @BindView(R.id.product_item_bottom_view)
//        View bottomView;
//        @BindView(R.id.product_item_bottom_rl)
//        RelativeLayout bottomAreaRL;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ScapAdapter(ArrayList<ProductSum> productSumList) {
        if (productSumList == null) {
            productSumList = new ArrayList<>();
        }
        this.productSumList = productSumList;
    }

    @Override
    public ScapAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScapAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_scrap, parent, false));
    }

    @Override
    public void onBindViewHolder(final ScapAdapter.MyViewHolder holder, final int position) {
        final ProductSum productSum = productSumList.get(position);
        if (productSum.getCardType() == 1) {
            holder.productName.setText(productSum.getName());


            if (productSum.isActive()) {
                holder.deactivePoint.setVisibility(View.GONE);
            } else {
                holder.deactivePoint.setVisibility(View.VISIBLE);
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
//            holder.inventory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View view, boolean b) {
//                    int i = position;
//                    if (!b) {
//                        String count = holder.inventory.getText().toString();
//
//                        try {
//                            if (!count.equalsIgnoreCase("0")) {
//                                boolean isitnew = true;
//                                boolean isitnewOrignal = true;
//                                for (int ii = 0; ii < productlistEdit.size(); ii++) {
//
//                                    if (productSum.getCode().equalsIgnoreCase(productlistEdit.get(ii).getCode())) {
//                                        ProductSum productSumEdit = productlistEdit.get(ii);
//                                        if (count.equalsIgnoreCase("")){
//                                            productSumEdit.setActualInventory(0.0);
//                                            productlistEdit.remove(ii);
//                                        }else {
//                                            productSumEdit.setActualInventory(Double.parseDouble(count));
//                                        }
//                                        isitnew = false;
//                                    }
//
//                                }
//                                for (int ii = 0; ii < productSumListOrignal.size(); ii++) {
//
//                                    if (productSum.getCode().equalsIgnoreCase(productSumListOrignal.get(ii).getCode())) {
//                                        ProductSum productSumEdit = productSumListOrignal.get(ii);
//                                        if (count.equalsIgnoreCase("")){
//                                            productSumEdit.setActualInventory(0.0);
//                                            productSumListOrignal.remove(ii);
//                                        }else {
//                                            productSumEdit.setActualInventory(productSum.getActualInventory());
//                                        }
//
//                                        isitnewOrignal = false;
//                                    }
//
//                                }
//                                if (!count.equalsIgnoreCase("")) {
//                                    if (isitnew) {
//                                        ProductSum productSumEdit = new ProductSum();
//                                        productSumEdit.setCode(productSum.getCode());
//                                        productSumEdit.setMainUnitReference(productSum.getMainUnitReference());
//                                        productSumEdit.setActualInventory(Double.parseDouble(count));
//                                        productlistEdit.add(productSumEdit);
//                                    }
//                                    if (isitnewOrignal) {
//                                        ProductSum productSumOrignal = new ProductSum();
//                                        productSumOrignal.setCode(productSum.getCode());
//                                        productSumOrignal.setMainUnitReference(productSum.getMainUnitReference());
//                                        productSumOrignal.setActualInventory(productSum.getActualInventory());
//                                        productSumListOrignal.add(productSumOrignal);
//
//                                    }
//                                }
//
//                            }
//                        } catch (Exception e) {
//                            Log.e("Invalid double", e.getMessage());
//                        }
//                    }
//                }
//            });
        }
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
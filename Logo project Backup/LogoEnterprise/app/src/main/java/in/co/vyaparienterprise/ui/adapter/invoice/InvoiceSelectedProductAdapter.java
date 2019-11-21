package in.co.vyaparienterprise.ui.adapter.invoice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.InvoiceLine;
import in.co.vyaparienterprise.ui.listener.UpdateListener;
import in.co.vyaparienterprise.util.Utils;

/**
 * Created by Bekir.Dursun on 7.11.2017.
 */

public class InvoiceSelectedProductAdapter extends RecyclerView.Adapter<InvoiceSelectedProductAdapter.MyViewHolder> {

    private ArrayList<InvoiceLine> invoiceLines;
    private UpdateListener updateListener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_item_name)
        TextView productName;
        @BindView(R.id.remove)
        Button removeBT;
        @BindView(R.id.remove_count)
        ImageView removeCountBT;
        @BindView(R.id.count_et)
        EditText countET;
        @BindView(R.id.add_count)
        ImageView addCountBT;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public InvoiceSelectedProductAdapter(ArrayList<InvoiceLine> invoiceLines, UpdateListener updateListener) {
        if (invoiceLines == null) {
            invoiceLines = new ArrayList<>();
        }
        this.invoiceLines = invoiceLines;
        this.updateListener = updateListener;
    }

    @Override
    public InvoiceSelectedProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InvoiceSelectedProductAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_product, parent, false));
    }

    @Override
    public void onBindViewHolder(final InvoiceSelectedProductAdapter.MyViewHolder holder, final int position) {
        final InvoiceLine invoiceLine = invoiceLines.get(position);
        holder.productName.setText(invoiceLine.getProduct().getName());

        if (Utils.isInteger(invoiceLine.getQuantity())) {
            holder.countET.setText(String.valueOf((int) invoiceLine.getQuantity()));
        } else {
            holder.countET.setText(String.valueOf(invoiceLine.getQuantity()));
        }

        holder.removeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = invoiceLine.getProduct().getId();
                invoiceLines.remove(position);
                notifyDataSetChanged();
                updateListener.update(id);
            }
        });

        holder.removeCountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.valueOf(holder.countET.getText().toString());
                count--;
                count = count < 1 ? 1 : count;
                holder.countET.setText(String.valueOf(count));
                invoiceLine.setQuantity(count);
            }
        });

        holder.addCountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.valueOf(holder.countET.getText().toString());
                count++;
                holder.countET.setText(String.valueOf(count));
                invoiceLine.setQuantity(count);
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoiceLines.size();
    }
}
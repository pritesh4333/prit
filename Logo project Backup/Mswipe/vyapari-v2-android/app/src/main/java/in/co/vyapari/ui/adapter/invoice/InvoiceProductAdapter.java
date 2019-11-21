package in.co.vyapari.ui.adapter.invoice;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyapari.R;
import in.co.vyapari.model.InvoiceLine;
import in.co.vyapari.model.Product;
import in.co.vyapari.util.CurrencyUtil;
import in.co.vyapari.util.Utils;

/**
 * Created by Bekir.Dursun on 9.11.2017.
 */

public class InvoiceProductAdapter extends RecyclerView.Adapter<InvoiceProductAdapter.MyViewHolder> {

    private ArrayList<InvoiceLine> invoiceLineList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.invoiceLine_name)
        TextView title;
        @BindView(R.id.invoiceLine_hsn_sac_code)
        TextView hsnSacCode;
        @BindView(R.id.invoiceLine_quantity)
        TextView quantity;
        @BindView(R.id.invoiceLine_quantity_unit)
        TextView unit;
        @BindView(R.id.invoiceLine_price)
        TextView price;
        @BindView(R.id.invoiceLine_item_layout)
        LinearLayout itemLayout;
        @BindView(R.id.item_invoice_io_discount)
        TextView discount;
        @BindView(R.id.item_invoice_io_general_discount_label_ll)
        LinearLayout generalDiscountLL;
        @BindView(R.id.item_invoice_io_general_discount)
        TextView generalDiscount;
        @BindView(R.id.item_invoice_io_cess_layout)
        RelativeLayout cessRL;
        @BindView(R.id.item_invoice_io_cess_value)
        TextView cessTV;
        @BindView(R.id.item_invoice_io_sgst_ll)
        LinearLayout sgstLL;
        @BindView(R.id.item_invoice_io_sgst_tv)
        TextView sgstTV;
        @BindView(R.id.item_invoice_io_cgst_ll)
        LinearLayout cgstLL;
        @BindView(R.id.item_invoice_io_cgst_tv)
        TextView cgstTV;
        @BindView(R.id.item_invoice_io_igst_ll)
        LinearLayout igstLL;
        @BindView(R.id.item_invoice_io_igst_tv)
        TextView igstTV;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public InvoiceProductAdapter(ArrayList<InvoiceLine> invoiceLineList) {
        if (invoiceLineList == null) {
            invoiceLineList = new ArrayList<>();
        }
        this.invoiceLineList = invoiceLineList;
    }

    @Override
    public InvoiceProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InvoiceProductAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_invoiceline, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final InvoiceProductAdapter.MyViewHolder holder, final int position) {
        final InvoiceLine invoiceLine = invoiceLineList.get(position);

        Product product = invoiceLine.getProduct() == null ? new Product() : invoiceLine.getProduct();

        holder.title.setText(product.getName());

        if (product.getSACHSNCode() != null && product.getProductType() != null) {
            String text = product.getProductType().getKey().equals("1") ? "HSN: " : "SAC: ";
            text += product.getSACHSNCode().getKey();
            holder.hsnSacCode.setText(text);
        }

        if (Utils.isInteger(invoiceLine.getQuantity())) {
            holder.quantity.setText(String.valueOf((int) invoiceLine.getQuantity()));
        } else {
            holder.quantity.setText(String.valueOf(invoiceLine.getQuantity()));
        }

        if (invoiceLine.getUnit() != null) {
            holder.unit.setText(invoiceLine.getUnit().getValue());
        }

        holder.price.setText(CurrencyUtil.doubleToCurrency(invoiceLine.getTotal(), invoiceLine.getCurrency()));

        if (invoiceLine.getCESSAmount() != 0) {
            holder.cessRL.setVisibility(View.VISIBLE);
            holder.cessTV.setText(CurrencyUtil.doubleToCurrency(invoiceLine.getCESSAmount(), invoiceLine.getCurrency()));
        } else {
            holder.cessRL.setVisibility(View.GONE);
        }

        if (invoiceLine.getSGSTAmount() != 0) {
            holder.sgstLL.setVisibility(View.VISIBLE);
            holder.sgstTV.setText(CurrencyUtil.doubleToCurrency(invoiceLine.getSGSTAmount(), invoiceLine.getCurrency()));
        } else {
            holder.sgstLL.setVisibility(View.GONE);
        }

        if (invoiceLine.getCGSTAmount() != 0) {
            holder.cgstLL.setVisibility(View.VISIBLE);
            holder.cgstTV.setText(CurrencyUtil.doubleToCurrency(invoiceLine.getCGSTAmount(), invoiceLine.getCurrency()));
        } else {
            holder.cgstLL.setVisibility(View.GONE);
        }

        if (invoiceLine.getIGSTAmount() != 0) {
            holder.igstLL.setVisibility(View.VISIBLE);
            holder.igstTV.setText(CurrencyUtil.doubleToCurrency(invoiceLine.getIGSTAmount(), invoiceLine.getCurrency()));
        } else {
            holder.igstLL.setVisibility(View.GONE);
        }

        if (invoiceLine.getDiscountType() == 2) {
            if (Utils.isInteger(invoiceLine.getDiscountRate())) {
                holder.discount.setText(String.valueOf("%" + (int) invoiceLine.getDiscountRate()));
            } else {
                holder.discount.setText(String.valueOf("%" + invoiceLine.getDiscountRate()));
            }
        } else if (invoiceLine.getDiscountType() == 1) {
            holder.discount.setText(CurrencyUtil.doubleToCurrency(invoiceLine.getDiscountAmount(), invoiceLine.getCurrency()));
        } else {
            holder.discount.setText("-");
        }

        if (invoiceLine.getGeneralDiscountAmount() > 0) {
            holder.generalDiscountLL.setVisibility(View.VISIBLE);
            holder.generalDiscount.setText(CurrencyUtil.doubleToCurrency(invoiceLine.getGeneralDiscountAmount(), invoiceLine.getCurrency()));
        } else {
            holder.generalDiscountLL.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return invoiceLineList.size();
    }
}
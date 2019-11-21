package in.co.vyaparienterprise.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robinhood.ticker.TickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.model.Contact;
import in.co.vyaparienterprise.model.response.summary.FirmSum;
import in.co.vyaparienterprise.ui.listener.FirmClickListener;
import in.co.vyaparienterprise.util.CurrencyUtil;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class CustomerSupplierAdapter extends RecyclerView.Adapter<CustomerSupplierAdapter.MyViewHolder> {

    private Context mContext;

    private ArrayList<FirmSum> firmSumList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.customer_supplier_checkbox)
        CheckBox customer_supplier_checkbox;

        @BindView(R.id.customer_names)
        TextView customer_names;



        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CustomerSupplierAdapter(Context mContext, ArrayList<FirmSum> firmSumList) {
        if (firmSumList == null) {
            firmSumList = new ArrayList<>();
        }

        this.mContext = mContext;
        this.firmSumList = firmSumList;

    }

    @Override
    public CustomerSupplierAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomerSupplierAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_customer_supplier_list, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final CustomerSupplierAdapter.MyViewHolder holder, final int position) {
        FirmSum firmSum = firmSumList.get(position);
        holder.customer_names.setText(firmSum.getName());
        holder.customer_supplier_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });






    }

    @Override
    public int getItemCount() {
        return firmSumList.size();
    }
}
package in.co.vyaparienterprise.ui.adapter.invoice;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.Address;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class InvoiceSelectFirmAddressAdapter extends RecyclerView.Adapter<InvoiceSelectFirmAddressAdapter.MyViewHolder> {

    private ArrayList<Address> addressList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_top_view)
        View topArea;
        @BindView(R.id.item_icon)
        ImageView icon;
        @BindView(R.id.item_text1)
        TextView text1;
        @BindView(R.id.item_text2)
        TextView text2;
        @BindView(R.id.item_bottom_view)
        View bottomView;
        @BindView(R.id.item_bottom_rl)
        RelativeLayout bottomAreaRL;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public InvoiceSelectFirmAddressAdapter(ArrayList<Address> addressList) {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        this.addressList = addressList;
    }

    @Override
    public InvoiceSelectFirmAddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InvoiceSelectFirmAddressAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_text_twoline, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final InvoiceSelectFirmAddressAdapter.MyViewHolder holder, final int position) {
        Address address = addressList.get(position);
        holder.text1.setText(address.getDescription());
        holder.text2.setText(address.getAddressText());

        if (position == 0) {
            holder.topArea.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.VISIBLE);
            holder.bottomAreaRL.setVisibility(View.GONE);
        } else if (position == addressList.size() - 1) {
            holder.topArea.setVisibility(View.GONE);
            holder.bottomView.setVisibility(View.GONE);
            holder.bottomAreaRL.setVisibility(View.VISIBLE);
        } else {
            holder.topArea.setVisibility(View.GONE);
            holder.bottomView.setVisibility(View.VISIBLE);
            holder.bottomAreaRL.setVisibility(View.GONE);
        }

        if (addressList.size() == 1) {
            holder.topArea.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.GONE);
            holder.bottomAreaRL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
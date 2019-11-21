package in.co.vyaparienterprise.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.Address;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class FirmAddressesAdapter extends RecyclerView.Adapter<FirmAddressesAdapter.MyViewHolder> {

    private ArrayList<Address> addressList;
    private int iconId;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_top_view)
        View topArea;
        @BindView(R.id.item_icon)
        ImageView icon;
        @BindView(R.id.item_text)
        TextView text;
        @BindView(R.id.item_bottom_view)
        View bottomView;
        @BindView(R.id.item_bottom_rl)
        RelativeLayout bottomAreaRL;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public FirmAddressesAdapter(ArrayList<Address> addressList) {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        this.addressList = addressList;
    }

    @Override
    public FirmAddressesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FirmAddressesAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_text_oneline, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final FirmAddressesAdapter.MyViewHolder holder, final int position) {
        Address address = addressList.get(position);
        holder.text.setText(address.getDescription());

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
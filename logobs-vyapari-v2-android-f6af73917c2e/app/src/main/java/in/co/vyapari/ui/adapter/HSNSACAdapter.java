package in.co.vyapari.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyapari.R;
import in.co.vyapari.model.KeyValue;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class HSNSACAdapter extends RecyclerView.Adapter<HSNSACAdapter.MyViewHolder> {

    private ArrayList<KeyValue> hsnsacList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_top_view)
        View topArea;
        @BindView(R.id.item_hsnsac_key)
        TextView key;
        @BindView(R.id.item_hsnsac_value)
        TextView value;
        @BindView(R.id.item_bottom_view)
        View bottomView;
        @BindView(R.id.item_bottom_rl)
        RelativeLayout bottomAreaRL;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public HSNSACAdapter(ArrayList<KeyValue> hsnsacList) {
        if (hsnsacList == null) {
            hsnsacList = new ArrayList<>();
        }
        this.hsnsacList = hsnsacList;
    }

    @Override
    public HSNSACAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HSNSACAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hsnsac, parent, false));
    }

    @Override
    public void onBindViewHolder(final HSNSACAdapter.MyViewHolder holder, final int position) {
        KeyValue hsnsac = hsnsacList.get(position);
        holder.key.setText(hsnsac.getKey());
        holder.value.setText(hsnsac.getValue());

        if (position == 0) {
            holder.topArea.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.VISIBLE);
            holder.bottomAreaRL.setVisibility(View.GONE);
        } else if (position == hsnsacList.size() - 1) {
            holder.topArea.setVisibility(View.GONE);
            holder.bottomView.setVisibility(View.GONE);
            holder.bottomAreaRL.setVisibility(View.VISIBLE);
        } else {
            holder.topArea.setVisibility(View.GONE);
            holder.bottomView.setVisibility(View.VISIBLE);
            holder.bottomAreaRL.setVisibility(View.GONE);
        }

        if (hsnsacList.size() == 1) {
            holder.topArea.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.GONE);
            holder.bottomAreaRL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return hsnsacList.size();
    }
}
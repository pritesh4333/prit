package in.co.vyaparienterprise.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.Contact;
import in.co.vyaparienterprise.ui.listener.RemoveListener;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class MiniContactAdapter extends RecyclerView.Adapter<MiniContactAdapter.MyViewHolder> {

    private ArrayList<Contact> dataList;
    private RemoveListener<Contact> removeListener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_contact_mini_icon)
        ImageView dataIcon;
        @BindView(R.id.item_contact_mini_data)
        TextView dataTV;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MiniContactAdapter(ArrayList<Contact> dataList, int type, RemoveListener<Contact> removeListener) {
        this.removeListener = removeListener;
        this.dataList = new ArrayList<>();
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        for (Contact c : dataList) {
            if (c.getType() == type) {
                this.dataList.add(c);
            }
        }
    }

    @Override
    public MiniContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MiniContactAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_mini, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final MiniContactAdapter.MyViewHolder holder, final int position) {
        final Contact contact = dataList.get(position);
        holder.dataTV.setText(contact.getValue());

        holder.dataIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.remove(contact);
                removeListener.remove(contact);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
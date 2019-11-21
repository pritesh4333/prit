package in.co.vyaparienterprise.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.Bank;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class FirmBanksAdapter extends RecyclerView.Adapter<FirmBanksAdapter.MyViewHolder> {

    private ArrayList<Bank> bankList;
    private int iconId;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon)
        ImageView icon;
        @BindView(R.id.item_text)
        TextView text;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public FirmBanksAdapter(ArrayList<Bank> bankList, int iconId) {
        if (bankList == null) {
            bankList = new ArrayList<>();
        }
        this.bankList = bankList;
        this.iconId = iconId;
    }

    @Override
    public FirmBanksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FirmBanksAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_text_oneline, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final FirmBanksAdapter.MyViewHolder holder, final int position) {
        Bank bank = bankList.get(position);
        holder.icon.setImageResource(iconId);
        holder.text.setText(bank.getName());
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }
}
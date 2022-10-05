package com.acumengroup.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.recommonDisplay.RecomData;

import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

public class RecommAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<RecomData> recomDataArrayList= new ArrayList<>();


    public RecommAdapter(Context activity, ArrayList<RecomData> recommListDataArrayLists) {
        this.ctx = activity;
        this.recomDataArrayList = recommListDataArrayLists;

    }

    @Override
    public int getCount() {
        return recomDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return recomDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        GreekTextView timeTxt, senderTxt, msgTxt;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.fragment_recomm_row, parent, false);
            holder.timeTxt = convertView.findViewById(R.id.txt_time);
            holder.senderTxt = convertView.findViewById(R.id.txt_sender);
            holder.msgTxt = convertView.findViewById(R.id.txt_recommand);


            if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.timeTxt.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                holder.senderTxt.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                holder.msgTxt.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
              //  holder.tvperchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.timeTxt.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                holder.senderTxt.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                holder.msgTxt.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
//                holder.tvperchange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        RecomData recomData = (RecomData) getItem(position);

        holder.timeTxt.setText(recomData.getlLogTime());
        holder.senderTxt.setText(recomData.getcSenderId());
        holder.msgTxt.setText(recomData.getcMessage());
        return convertView;
    }
}

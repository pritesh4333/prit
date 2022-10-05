package com.acumengroup.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.recommonDisplay.RecomData;
import com.acumengroup.mobile.BottomTabScreens.NewsTabFragment;
import com.acumengroup.mobile.model.NewsDataModel;
import com.acumengroup.mobile.reports.RecommDisplayFragment;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.List;

public class RecomRecyclerAdapter extends RecyclerView.Adapter<RecomRecyclerAdapter.ViewHolder> {

    private List<RecomData> recomData;
    Context context;

    public RecomRecyclerAdapter(List<RecomData> recomData, Context context) {
        this.recomData = recomData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fragment_recomm_row, parent, false);
        return new RecomRecyclerAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            holder.timeTxt.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
            holder.senderTxt.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
            holder.msgTxt.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));

        } else {
            holder.timeTxt.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
            holder.senderTxt.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
            holder.msgTxt.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
        }

        RecomData recomDataList =  recomData.get(position);

        if (recomDataList!=null) {
            holder.timeTxt.setText(recomDataList.getlLogTime());
            holder.senderTxt.setText(recomDataList.getcSenderId());
            holder.msgTxt.setText(recomDataList.getcMessage());
        }
    }

    @Override
    public int getItemCount() {
        return recomData!=null? recomData.size():0 ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        GreekTextView timeTxt, senderTxt, msgTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timeTxt = itemView.findViewById(R.id.txt_time);
            senderTxt = itemView.findViewById(R.id.txt_sender);
            msgTxt = itemView.findViewById(R.id.txt_recommand);
        }
    }
}

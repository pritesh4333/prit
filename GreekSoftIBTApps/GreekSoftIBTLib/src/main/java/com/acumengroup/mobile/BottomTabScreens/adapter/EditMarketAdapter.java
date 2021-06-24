package com.acumengroup.mobile.BottomTabScreens.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

public class EditMarketAdapter extends RecyclerView.Adapter<EditMarketAdapter.MyViewHolder> {

    ArrayList<IndianIndice> gainerData;
    AppCompatActivity activity;
    int childIndex;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    String symbol;
    String imgeid;


    public EditMarketAdapter(ArrayList<IndianIndice> gainerData, AppCompatActivity activity, String imgeid) {
        this.gainerData = gainerData;
        this.activity = activity;
        this.imgeid = imgeid;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public GreekTextView symboltxt;
        ImageView addimg;
        LinearLayout row_Layout;
        View view1;

        public MyViewHolder(View view) {
            super(view);

            symboltxt = itemView.findViewById(R.id.txt_symbol);
            addimg = itemView.findViewById(R.id.img_add);
            row_Layout = itemView.findViewById(R.id.row_Layout);
            view1 = itemView.findViewById(R.id.view1);


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_market_child, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        IndianIndice data = gainerData.get(position);
        holder.symboltxt.setText(data.getToken());
        symbol = data.getToken();

        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")) {
            holder.symboltxt.setTextColor(activity.getResources().getColor(R.color.black));
            holder.row_Layout.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.view1.setBackgroundColor(activity.getResources().getColor(R.color.bajaj_black));
            holder.addimg.setImageResource(R.drawable.ic_black_library_add_24);


        }

        holder.addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return gainerData.size();
    }


}

package com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;
import com.acumengroup.mobile.R;

import java.util.ArrayList;


public class OptionChainAdapter_first extends RecyclerView.Adapter<OptionChainAdapter_first.MyViewHolder> {


    ArrayList<GainerData> gainerData;
    AppCompatActivity activity;

    public OptionChainAdapter_first(ArrayList<GainerData> gainerData, AppCompatActivity activity) {
        this.gainerData = gainerData;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.optionchain_child_view_row, parent, false);
        return new OptionChainAdapter_first.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionChainAdapter_first.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView callLTPTXT,callChngTXT,callIVTXT,callOITXT,callOIChngTXT,callVolTXT,strikepriceTXT,putLTPTXT,putChngTXT,putIVTXT,putOITXT,putOIChgTXT,putVolTXT;

        public MyViewHolder(View view) {
            super(view);

            callLTPTXT= view.findViewById(R.id.txt_call_ltp);
            callChngTXT= view.findViewById(R.id.txt_call_chng);
            callIVTXT= view.findViewById(R.id.txt_call_iv);
            callOITXT= view.findViewById(R.id.txt_call_oi);
            callOIChngTXT= view.findViewById(R.id.txt_call_oichg);
            callVolTXT= view.findViewById(R.id.txt_call_vol);
            putLTPTXT= view.findViewById(R.id.txt_put_ltp);
            putChngTXT= view.findViewById(R.id.txt_put_chng);
            putOITXT= view.findViewById(R.id.txt_put_oi);
            putOIChgTXT= view.findViewById(R.id.txt_put_oichg);
            putVolTXT= view.findViewById(R.id.txt_put_vol);


        }
    }

}


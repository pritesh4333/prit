package com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData_v2;
import com.acumengroup.mobile.R;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.bfsl.core.network.TopGainersModels;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class TopGainerAdapter extends RecyclerView.Adapter<TopGainerAdapter.MyViewHolder> {
    ArrayList<GainerData_v2> gainerData;
    AppCompatActivity activity;
    private ArrayList tokenList = new ArrayList();

    public TopGainerAdapter(ArrayList<GainerData_v2> gainerData, AppCompatActivity activity) {
        this.gainerData = gainerData;
        this.activity = activity;
        this.tokenList.clear();
        EventBus.getDefault().register(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_view_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopGainerAdapter.MyViewHolder holder, int position) {

        try {
            holder.nodataTxt.setVisibility(View.GONE);
            GainerData_v2 gainerData1Item = gainerData.get(position);

            if (gainerData1Item.getName() != null) {
                holder.symboltxt.setText(gainerData1Item.getName().toUpperCase());
            }


            if (gainerData1Item.getExchange() != null) {
                holder.exchangeTxt.setText(gainerData1Item.getExchange().toUpperCase());
            }
            if (gainerData1Item.getLtp() != null && !gainerData1Item.getLtp().equalsIgnoreCase("")) {
                holder.ltpTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(gainerData1Item.getLtp()))));
            }

            if (gainerData1Item.getChange() != null && !gainerData1Item.getChange().equalsIgnoreCase("") && !gainerData1Item.getPerchange().equalsIgnoreCase("")) {
                holder.changeTxt.setText(gainerData1Item.getChange() + "(" + String.format("%.2f", Double.parseDouble(gainerData1Item.getPerchange())) + "%)");
            }

            if (gainerData1Item.getChange().startsWith("-")) {

                holder.changeTxt.setTextColor(activity.getResources().getColor(R.color.bajaj_light_red));

            } else {
                if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                    holder.changeTxt.setTextColor(activity.getResources().getColor(R.color.whitetheambuyColor));
                } else {
                    holder.changeTxt.setTextColor(activity.getResources().getColor(R.color.dark_green_positive));
                }
            }
        } catch (Exception ex) {
            Log.e("exception", ex.getMessage());
        }

    }


    public void clearData() {
        gainerData.clear();
        tokenList.clear();
    }


    @Override
    public int getItemCount() {

        return gainerData.size();
    }


    public void addToken(String token) {
        tokenList.add(token);

    }


    public boolean containsSymbol(String symbol) {
        return tokenList.contains(symbol);
    }

    public GainerData_v2 getItem(int position) {
        return gainerData.get(position);
    }

    public void updateData(int position, GainerData_v2 gainerDatalist) {

        gainerData.set(position, gainerDatalist);
        notifyItemChanged(position);

    }


    public int indexOf(String symbol) {
        return tokenList.indexOf(symbol);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView symboltxt, exchangeTxt, ltpTxt, changeTxt, nodataTxt;
        LinearLayout layout, mainLayout;

        public MyViewHolder(View view) {
            super(view);
            mainLayout = view.findViewById(R.id.childmain_layout);
            symboltxt = view.findViewById(R.id.symbolname_text);
            exchangeTxt = view.findViewById(R.id.descriptionname_text);
            ltpTxt = view.findViewById(R.id.ltp_text);
            changeTxt = view.findViewById(R.id.change_text);
            layout = view.findViewById(R.id.child_layout);
            nodataTxt = view.findViewById(R.id.txt_nodata);

            if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")) {
                symboltxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                exchangeTxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                ltpTxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                changeTxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                nodataTxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                mainLayout.setBackgroundColor(activity.getColor(R.color.white));
            }


        }
    }

    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("ltpinfo")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }


    }


    public void updateBroadcastData(StreamerBroadcastResponse response) {

        int index = indexOf(response.getSymbol());

        GainerData_v2 data = getItem(index);

        if (data.getToken().equalsIgnoreCase(response.getSymbol())) {


            if (!data.getLtp().equals(response.getLast())) {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }

            } else {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }
            }

            if (!data.getChange().equals(response.getChange())) {
                if (response.getSymbol().contains("502") || response.getSymbol().contains("1302")) {
                    data.setChange(String.format("%.4f", Double.parseDouble(response.getChange())));
                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(response.getChange())));
                }

            } else {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setChange(String.format("%.4f", Double.parseDouble(data.getChange())));

                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(data.getChange())));
                }

            }

            if (!data.getPerchange().equals(response.getP_change())) {
                data.setPerchange(String.format("%.2f", Double.parseDouble(response.getP_change())));

            } else {
                data.setPerchange(String.format("%.2f", Double.parseDouble(data.getPerchange())));
            }

//            Log.e("TOPGainer", "======" + index + "=======>>>>>" + data.getName());
            //data.setName("Rahul Magar");

            updateData(index, data);
        } else {
//            Log.e("TOPGainer", "====notMatch==" + index + "=======>>>>>" + data.getName());
//            Log.e("TOPGainer", "====notMatch==" + index + "=======>>>>>" + data.getToken());
//            Log.e("TOPGainer", "====notMatch==" + index + "=======>>>>>" + response.getSymbol());

        }
    }

}

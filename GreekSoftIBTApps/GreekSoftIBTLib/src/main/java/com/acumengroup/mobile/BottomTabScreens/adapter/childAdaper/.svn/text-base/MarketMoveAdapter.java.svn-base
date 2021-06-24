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

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MarketMoveAdapter extends RecyclerView.Adapter<MarketMoveAdapter.MyViewHolder> {
    List<GainerData_v2> gainerData;
    AppCompatActivity activity;
    private ArrayList tokenArrayList = new ArrayList();

    public MarketMoveAdapter(List<GainerData_v2> gainerData, AppCompatActivity activity) {
        this.gainerData = gainerData;
        this.activity = activity;
        this.tokenArrayList.clear();
        EventBus.getDefault().register(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_view_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GainerData_v2 gainerData1Item = gainerData.get(position);

        if (gainerData1Item.getName() != null) {
            holder.symboltxt.setText(gainerData1Item.getName().toUpperCase());
        }
        if (gainerData1Item.getExchange() != null) {
            holder.exchangeTxt.setText(gainerData1Item.getExchange().toUpperCase());
        }
        if (gainerData1Item.getLtp() != null) {
            holder.ltpTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(gainerData1Item.getLtp()))));
        }

        holder.changeTxt.setText(gainerData1Item.getChange() + "(" + String.format("%.2f", Double.parseDouble(gainerData1Item.getPerchange())) + "%)");

        if (gainerData1Item.getChange().startsWith("-")) {

            holder.changeTxt.setTextColor(activity.getResources().getColor(R.color.bajaj_light_red));

        } else {
            if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                holder.changeTxt.setTextColor(activity.getResources().getColor(R.color.whitetheambuyColor));

            }else {
                holder.changeTxt.setTextColor(activity.getResources().getColor(R.color.dark_green_positive));
            }

        }

    }

    public void clearData() {
        gainerData.clear();
        tokenArrayList.clear();
    }

    @Override
    public int getItemCount() {
        return gainerData.size();
    }


    public boolean containsSymbol(String symbol) {
        return tokenArrayList.contains(symbol);
    }

    public GainerData_v2 getItem(int position) {
        return gainerData.get(position);
    }

    public void updateData(int position, GainerData_v2 gainerDatalist) {

        gainerData.set(position, gainerDatalist);
        notifyItemChanged(position);

    }

    public int indexOf(String symbol) {
        return tokenArrayList.indexOf(symbol);
    }


    public void addToken(String token) {
        tokenArrayList.add(token);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView symboltxt, exchangeTxt, ltpTxt, changeTxt;
        LinearLayout layout,mainLayout;

        public MyViewHolder(View view) {
            super(view);

            symboltxt = view.findViewById(R.id.symbolname_text);
            exchangeTxt = view.findViewById(R.id.descriptionname_text);
            ltpTxt = view.findViewById(R.id.ltp_text);
            changeTxt = view.findViewById(R.id.change_text);
            layout = view.findViewById(R.id.child_layout);
            mainLayout = view.findViewById(R.id.childmain_layout);

            if(AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")) {
                symboltxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                exchangeTxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                ltpTxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                changeTxt.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
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

        GainerData_v2 data = getItem(indexOf(response.getSymbol()));

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
            updateData(indexOf(response.getSymbol()), data);

        }
    }

}

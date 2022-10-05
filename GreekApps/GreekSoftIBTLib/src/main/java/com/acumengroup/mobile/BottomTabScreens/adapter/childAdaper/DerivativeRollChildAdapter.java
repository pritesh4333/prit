package com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;
import com.acumengroup.mobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class DerivativeRollChildAdapter extends RecyclerView.Adapter<DerivativeRollChildAdapter.MyViewHolder> {

    ArrayList<GainerData> gainerData;
    Context activity;
    int childIndex;
    private ArrayList<HashMap<String, String>> tokenList = new ArrayList<HashMap<String, String>>();
    private ArrayList<String> tokenLists = new ArrayList<String>();
    private ArrayList<String> tokenLists2 = new ArrayList<String>();
    private String groupPosition;

    public DerivativeRollChildAdapter(ArrayList<GainerData> gainerData, Context activity, String groupPosition, int chlidindex) {
        this.gainerData = gainerData;
        this.activity = activity;
        this.childIndex = chlidindex;
        EventBus.getDefault().register(this);
        this.groupPosition = groupPosition;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_view_row, parent, false);
        return new MyViewHolder(itemView);

    }

    public ArrayList<String> getSymbolTable(){

        return tokenLists;
    }

    public ArrayList<String> getSymbolTable1(){

        return tokenLists;
    }

    public void AddToken(String token){
        tokenLists.add(token);
    }
    public void AddToken2(String token){
        tokenLists2.add(token);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final GainerData data = gainerData.get(position);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(data.getToken1(), data.getToken2());
        tokenList.add(position, hashMap);

        holder.symbolname_text.setText(data.getDescription());
        holder.ltp_text.setText(String.format("%.2f", Double.parseDouble(String.valueOf(data.getLtp()))) + "%");

        holder.change_text.setText(String.format("%.2f", Double.parseDouble(String.valueOf(data.getChange())))
                + " (" + String.format("%.2f", Double.parseDouble(String.valueOf(data.getPerchange()))) + "%)");

        holder.descriptionname_text.setVisibility(View.GONE);

        if (data.getChange().startsWith("-")) {

            holder.change_text.setTextColor(activity.getResources().getColor(R.color.dark_red_negative));

        } else {
            if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                holder.change_text.setTextColor(activity.getResources().getColor(R.color.whitetheambuyColor));
            }else {
                holder.change_text.setTextColor(activity.getResources().getColor(R.color.dark_green_positive));
            }
        }


    }


    @Override
    public int getItemCount() {

        if (gainerData != null) {
            return gainerData.size();
        } else {
            return 0;
        }
    }


    public boolean containsSymbol(String token) {

        for (int i = 0; i < tokenList.size(); i++) {

            if (tokenList.get(i).containsKey(token)) {

                return true;
            } else {
                for (Map.Entry<String, String> entry : tokenList.get(i).entrySet()) {
                    return entry.getValue().equalsIgnoreCase(token);

                }
            }

        }
        return false;
    }


    public boolean contains(String symbol) {
        if (tokenLists.contains(symbol)) {
            return true;
        }
        return tokenLists2.contains(symbol);
    }



    public int indexOfsymbol(String symbol) {

        if(tokenLists.contains(symbol)){
            return tokenLists.indexOf(symbol);
        }if(tokenLists2.contains(symbol)){
            return tokenLists2.indexOf(symbol);
        }
        return -1;
    }


    public GainerData getItem(int position) {
        return gainerData.get(position);
    }


    public int indexOf(String token) {
        int i;
        for (i = 0; i < tokenList.size(); i++) {

            for (Map.Entry<String, String> entry : tokenList.get(i).entrySet()) {

                if (entry.getValue().equalsIgnoreCase(token)) {
                    return i;
                } else if (entry.getKey().equalsIgnoreCase(token)) {
                    return i;
                }
                i++;
            }
        }

        return i;

    }

    public void updateData(int position, GainerData gainerDatalist) {

        if (gainerDatalist != null && position >= 0) {
            gainerData.set(position, gainerDatalist);
            notifyItemChanged(position);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView symbolname_text, descriptionname_text, ltp_text, change_text;
        LinearLayout mainLayout;

        public MyViewHolder(View view) {
            super(view);

            symbolname_text = itemView.findViewById(R.id.symbolname_text);
            descriptionname_text = itemView.findViewById(R.id.descriptionname_text);
            ltp_text = itemView.findViewById(R.id.ltp_text);
            change_text = itemView.findViewById(R.id.change_text);

            mainLayout = view.findViewById(R.id.childmain_layout);
            if(AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")){
                symbolname_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                descriptionname_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                ltp_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                change_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
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

    private void updateBroadcastData(StreamerBroadcastResponse response) {

        GainerData data = null;
//        if (containsSymbol(response.getSymbol())) {
        if (contains(response.getSymbol())) {

//            data = getItem(indexOf(response.getSymbol()));
            data = getItem(indexOfsymbol(response.getSymbol()));

            if (data != null) {
                if (data.getToken1().equalsIgnoreCase(response.getSymbol())) {
                    data.setLtp1(response.getLast());
                } else if (data.getToken2().equalsIgnoreCase(response.getSymbol())) {

                    data.setLtp2(response.getLast());
                }
            }

            if (response.getSymbol().contains("502") || response.getSymbol().contains("1302")) {

                Double ltp1 = Double.valueOf(data.getLtp1());
                Double ltp2 = Double.valueOf(data.getLtp2());
                Double totalLTP = ltp2 - ltp1;

                data.setChange(String.format("%.4f", totalLTP));
                Double totalPerLTP = ((ltp2 - ltp1) / ltp1) * 100;

                data.setPerchange(String.format("%.2f", totalPerLTP));
//                updateData(indexOf(response.getSymbol()), data);
                updateData(indexOfsymbol(response.getSymbol()), data);
            } else {

                Double ltp1 = Double.valueOf(data.getLtp1());
                Double ltp2 = Double.valueOf(data.getLtp2());
                Double totalLTP = ltp2 - ltp1;

                data.setChange(String.format("%.2f", totalLTP));
                Double totalPerLTP = ((ltp2 - ltp1) / ltp1) * 100;

                data.setPerchange(String.format("%.2f", totalPerLTP));
//                updateData(indexOf(response.getSymbol()), data);
                updateData(indexOfsymbol(response.getSymbol()), data);


            }
        }

    }

}







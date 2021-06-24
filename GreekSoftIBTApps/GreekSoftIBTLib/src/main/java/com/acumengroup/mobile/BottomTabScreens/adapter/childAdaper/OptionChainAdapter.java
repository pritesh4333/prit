package com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.OptionChainhashData;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class OptionChainAdapter extends RecyclerView.Adapter<OptionChainAdapter.MyViewHolder> {

    private ArrayList<OptionChainhashData> optionChainDataList;
    private Context context;
    private final ArrayList<String> tokenList;
    private final ArrayList<String> tokenListce;
    private final ArrayList<String> tokenListpe;
    private double dLTP;

    public OptionChainAdapter(Context activity, int fragment_option_chain_row, ArrayList<OptionChainhashData> optionChainLists) {
        optionChainDataList = new ArrayList<>();
        this.optionChainDataList = optionChainLists;
        this.context = activity;
        tokenList = new ArrayList<>();
        tokenListce = new ArrayList<>();
        tokenListpe = new ArrayList<>();
        this.dLTP = dLTP;
        EventBus.getDefault().register(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_option_chain_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        String Oichangeper_call, oichangeper_put;
        OptionChainhashData optionChainData = optionChainDataList.get(position);

        if (optionChainData.getStrike() != null)
            holder.strike.setText(optionChainData.getStrike());
        if (optionChainData.getdLtp_call() != null)
            holder.callltp.setText(optionChainData.getdLtp_call());
        if (optionChainData.getdLtp_put() != null)
            holder.putltp.setText(optionChainData.getdLtp_put());
        if (optionChainData.getlPrevOpenInterest_put() != null && optionChainData.getlOpenInterest_call() != null)
            holder.callchg.setText(Double.parseDouble(optionChainData.getlOpenInterest_call()) - Double.parseDouble(optionChainData.getlPrevOpenInterest_call()) + "");
        if (optionChainData.getlPrevOpenInterest_put() != null && optionChainData.getlOpenInterest_put() != null)
            holder.putchg.setText(Double.parseDouble(optionChainData.getlOpenInterest_put()) - Double.parseDouble(optionChainData.getlPrevOpenInterest_put()) + "");
        if (optionChainData.getlOpenInterest_call() != null)
            holder.calloi.setText(optionChainData.getlOpenInterest_call());
        if (optionChainData.getlOpenInterest_put() != null)
            holder.putoi.setText(optionChainData.getlOpenInterest_put());
        holder.calliv.setText("");
        holder.putiv.setText("");
        if (optionChainData.getlPrevOpenInterest_call() != null && optionChainData.getlOpenInterest_call() != null) {
            Double diff_call = Double.parseDouble(optionChainData.getlOpenInterest_call()) - Double.parseDouble(optionChainData.getlPrevOpenInterest_call());
            Double abschange = 0.00;
            if (diff_call < 0) {
                abschange = Math.abs(diff_call);
            } else {
                abschange = diff_call;
            }


            Oichangeper_call = (abschange * 100) / Double.parseDouble(optionChainData.getlPrevOpenInterest_call()) + "";
            if (!Double.isNaN((abschange * 100) / Double.parseDouble(optionChainData.getlPrevOpenInterest_call()))) {
                if (!String.format("%.2f", Double.parseDouble(Oichangeper_call)).equalsIgnoreCase("infinity")) {
                    holder.calloichg.setText(String.format("%.2f", Double.parseDouble(Oichangeper_call)));
                } else {
                    holder.calloichg.setText("0.0");
                }
            } else {
                holder.calloichg.setText("0.0");
            }
        } else {
            holder.calloichg.setText("0.0");
        }

        if (optionChainData.getlPrevOpenInterest_put() != null && optionChainData.getlOpenInterest_put() != null) {
            Double diff_put = Double.parseDouble(optionChainData.getlOpenInterest_put()) - Double.parseDouble(optionChainData.getlPrevOpenInterest_put());
            Double abschangeput = 0.00;
            if (diff_put < 0) {
                abschangeput = Math.abs(diff_put);
            } else {
                abschangeput = diff_put;
            }


            oichangeper_put = (abschangeput * 100) / Double.parseDouble(optionChainData.getlPrevOpenInterest_put()) + "";
            if (!Double.isNaN((abschangeput * 100) / Double.parseDouble(optionChainData.getlPrevOpenInterest_put()))) {
                if (!String.format("%.2f", Double.parseDouble(oichangeper_put)).equalsIgnoreCase("infinity")) {
                    holder.putoichg.setText(String.format("%.2f", Double.parseDouble(oichangeper_put)));
                } else {
                    holder.putoichg.setText("0.0");
                }

            } else {
                holder.putoichg.setText("0.0");
            }
        } else {
            holder.putoichg.setText("0.0");
        }


        holder.callvol.setText(optionChainData.getlVolume_call());
        holder.putvol.setText(optionChainData.getlVolume_put());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return optionChainDataList.size();
    }

    public boolean containsSymbol(String symbol) {
        if (tokenListpe.contains(symbol)) {
            return true;
        }

        return tokenListce.contains(symbol);
    }

    public void AddToken(String token) {

        tokenList.add(token);

    }

    public void AddTokenCE(String token) {

        tokenListce.add(token);

    }

    public void AddTokenPE(String token) {

        tokenListpe.add(token);

    }

    public OptionChainhashData getListItem(int position) {

        return optionChainDataList.get(position);
    }


    public void setItem(int position, OptionChainhashData optionChainhashData) {

        optionChainDataList.set(position, optionChainhashData);
        notifyDataSetChanged();

    }

    public int indexOf(String symbol) {

        if (tokenListce.contains(symbol)) {
            return tokenListce.indexOf(symbol);
        }
        if (tokenListpe.contains(symbol)) {
            return tokenListpe.indexOf(symbol);
        }
        return -1;
    }

    public ArrayList<String> getSymbolTable() {
        return tokenList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public GreekTextView strike, callchg, callltp, putltp, putchg, calloi, putoi, calliv, putiv, calloichg, putoichg, callvol, putvol;
        LinearLayout mainLayout;

        public MyViewHolder(View view) {
            super(view);

            strike = view.findViewById(R.id.txt_strike);
            callltp = view.findViewById(R.id.ltp_call);
            putltp = view.findViewById(R.id.ltp_put);
            callchg = view.findViewById(R.id.change_call);
            putchg = view.findViewById(R.id.change_put);
            calloi = view.findViewById(R.id.txt_oi_call);

            putoi = view.findViewById(R.id.txt_oi_put);
            calliv = view.findViewById(R.id.txt_iv_call);
            putiv = view.findViewById(R.id.txt_iv_put);

            calloichg = view.findViewById(R.id.txt_chg_per_call);
            putoichg = view.findViewById(R.id.txt_chg_per_put);
            callvol = view.findViewById(R.id.txt_vol_call);
            putvol = view.findViewById(R.id.txt_vol_put);

            mainLayout = view.findViewById(R.id.main_layout);
            if(AccountDetails.getThemeFlag(context).equalsIgnoreCase("White")){
//                strike.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                callltp.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                putltp.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                callchg.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                putchg.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                calloi.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                putoi.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                calliv.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                putiv.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                calloichg.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                putoichg.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                callvol.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                putvol.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                mainLayout.setBackgroundColor(context.getColor(R.color.white));


            }

        }
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("touchline")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();

                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void updateBroadcastData(StreamerBroadcastResponse response) {


        if (containsSymbol(response.getSymbol())) {

            OptionChainhashData optionChainhashData = getListItem(indexOf(response.getSymbol()));

            optionChainhashData.setlAskQty_call(response.getTot_buyQty());

            if (optionChainhashData.getlOurToken_call().equalsIgnoreCase(response.getSymbol())) {

                optionChainhashData.setdLtp_call(response.getLast());
                optionChainhashData.setlVolume_call(response.getTot_vol());

                setItem(indexOf(response.getSymbol()), optionChainhashData);
            } else if (optionChainhashData.getlOurToken_put().equalsIgnoreCase(response.getSymbol())) {
                optionChainhashData.setdLtp_put(response.getLast());
                optionChainhashData.setlVolume_put(response.getTot_vol());
                setItem(indexOf(response.getSymbol()), optionChainhashData);

            }

        }


    }


}

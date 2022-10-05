package com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.BottomTabScreens.adapter.DerivativeRecycleAdapter.isExpandedOIA;

public class DerivativeRecycleChildAdapter_single extends RecyclerView.Adapter<DerivativeRecycleChildAdapter_single.MyViewHolder> {

    private ArrayList<GainerData> gainerData;
    private Context activity;
    private int childIndex;
    private ArrayList tokenList;
    private String groupPosition;
    private String header;
    RecyclerView OIA_recyclerview;

    public DerivativeRecycleChildAdapter_single(ArrayList<GainerData> gainerData, Context activity, String groupPosition, String header, int chlidindex, RecyclerView OIA_recyclerview) {
        this.gainerData = gainerData;
        this.activity = activity;
        this.childIndex = chlidindex;
        EventBus.getDefault().register(this);
        this.groupPosition = groupPosition;
        this.header = header;
        this.tokenList = new ArrayList<String>();
        this.OIA_recyclerview = OIA_recyclerview;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_view_row, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final GainerData data = gainerData.get(position);

        holder.symbolname_text.setText(data.getDescription());

        if(groupPosition.equalsIgnoreCase("OPEN INTEREST ANALYSIS")) {

            try {
                holder.descriptionname_text.setVisibility(View.VISIBLE);

                if(!String.valueOf(data.getLotSize()).isEmpty()) {

                    holder.descriptionname_text.setText("OI % " + String.format("%.2f", Double.parseDouble(String.valueOf(data.getLotSize()))));
                }

                if(data.getLotSize().startsWith("-")) {

                    holder.descriptionname_text.setTextColor(activity.getResources().getColor(R.color.dark_red_negative));

                } else {
                    if(AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                        holder.descriptionname_text.setTextColor(activity.getResources().getColor(R.color.whitetheambuyColor));
                    }else {
                        holder.descriptionname_text.setTextColor(activity.getResources().getColor(R.color.dark_green_positive));
                    }

                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                Log.e("Adapter_single", "Invalid " + e);
            }
        } else {

            holder.descriptionname_text.setVisibility(View.VISIBLE);
            holder.descriptionname_text.setText("Lots " + StringStuff.commaINRDecorator(data.getLotSize()));
        }

        if (!String.valueOf(data.getLtp()).isEmpty()) {

            holder.ltp_text.setText(String.format("%.2f", Double.parseDouble(String.valueOf(data.getLtp()))));
        }

        if (!String.valueOf(data.getChange()).isEmpty() && !String.valueOf(data.getPerchange()).isEmpty()) {

            holder.change_text.setText(String.format("%.2f", Double.parseDouble(String.valueOf(data.getChange())))
                    + " (" + String.format("%.2f", Double.parseDouble(String.valueOf(data.getPerchange()))) + "%)");
        }

        if (data.getChange().startsWith("-")) {

            holder.change_text.setTextColor(activity.getResources().getColor(R.color.dark_red_negative));

        } else {
            if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                holder.change_text.setTextColor(activity.getResources().getColor(R.color.whitetheambuyColor));
            } else {
                holder.change_text.setTextColor(activity.getResources().getColor(R.color.dark_green_positive));
            }

        }


        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")) {
            holder.symbolname_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
//            holder.descriptionname_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
            holder.ltp_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
//            holder.change_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
            holder.mainLayout.setBackgroundColor(activity.getColor(R.color.white));
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public GreekTextView symbolname_text, descriptionname_text, ltp_text, change_text;
        LinearLayout mainLayout;

        public MyViewHolder(View view) {
            super(view);
            symbolname_text = itemView.findViewById(R.id.symbolname_text);
            descriptionname_text = itemView.findViewById(R.id.descriptionname_text);
            ltp_text = itemView.findViewById(R.id.ltp_text);
            change_text = itemView.findViewById(R.id.change_text);
            mainLayout = view.findViewById(R.id.childmain_layout);

        }
    }

    @Override
    public int getItemCount() {

        if(gainerData != null) {
            return gainerData.size();

        } else {
            return 0;
        }
    }


    public boolean containsSymbol(String symbol) {

        if(tokenList != null && tokenList.size() > 0) {
            return tokenList.contains(symbol);

        } else {

            return false;
        }
    }

    public void addToken(String token) {

        if(tokenList != null && !tokenList.contains(token)) {
            tokenList.add(token);
        }

    }

    public GainerData getItem(int position) {

        return gainerData.get(position);
    }


    public int indexOf(String symbol) {

        if(tokenList.contains(symbol)) {
            return tokenList.indexOf(symbol);
        } else {
            return -1;
        }


    }


    public void updateData(int position, GainerData gainerDatalist) {

        if(gainerData.get(position).getToken().equalsIgnoreCase(gainerDatalist.getToken())) {
            gainerData.set(position, gainerDatalist);
//            notifyDataSetChanged();
//            notifyItemChanged(position);

        }


//        if(groupPosition.equalsIgnoreCase("OPEN INTEREST ANALYSIS")) {
//
//            if(header.equalsIgnoreCase("LONG BUILD UP(OI UP, PRICE UP)")) {
//
//                if(gainerDatalist.getChange().startsWith("-")) {
//                    if(isExpandedOIA) {
//                        EventBus.getDefault().post(header);
//                        gainerData.clear();
//                        OIA_recyclerview.getRecycledViewPool().clear();
//                        notifyDataSetChanged();
//                    }
//
//                }
//
//            } else if(header.equalsIgnoreCase("SHORT BUILD UP(OI UP, PRICE DOWN)")) {
//
//                if(!gainerDatalist.getChange().startsWith("-")) {
//                    if(isExpandedOIA) {
//                        EventBus.getDefault().post(header);
//                        gainerData.clear();
//                        OIA_recyclerview.getRecycledViewPool().clear();
//                        notifyDataSetChanged();
//                    }
//
//                }
//
//            } else if(header.equalsIgnoreCase("LONG UNWINDING(OI DOWN, PRICE DOWN)")) {
//
//                if(!gainerDatalist.getChange().startsWith("-")) {
//                    if(isExpandedOIA) {
//                        EventBus.getDefault().post(header);
//                        gainerData.clear();
//                        OIA_recyclerview.getRecycledViewPool().clear();
//                        notifyDataSetChanged();
//                    }
//
//                }
//
//            } else if(header.equalsIgnoreCase("SHORT COVERING (OI DOWN, PRICE UP)")) {
//
//                if(gainerDatalist.getChange().startsWith("-")) {
//                    if(isExpandedOIA) {
//                        EventBus.getDefault().post(header);
//                        gainerData.clear();
//                        OIA_recyclerview.getRecycledViewPool().clear();
//                        notifyDataSetChanged();
//                    }
//
//                }
//            }
//
//
//        }

    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if(streamingResponse.getStreamingType().equals("ltpinfo")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
//                updateBroadcastData(broadcastResponse);

            }

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public void onEventMainThread(String flag) {
        isExpandedOIA = flag.equalsIgnoreCase("open");

    }

    private void updateBroadcastData(StreamerBroadcastResponse response) {

        if(containsSymbol(response.getSymbol())) {
            boolean notifyNeeded = false;

            GainerData data = getItem(indexOf(response.getSymbol()));

            if(!data.getLtp().equals(response.getLast())) {
                if(((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }
                notifyNeeded = true;
            } else {
                if(((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }
            }

            if(!data.getChange().equals(response.getChange())) {
                if(response.getSymbol().contains("502") || response.getSymbol().contains("1302")) {
                    data.setChange(String.format("%.4f", Double.parseDouble(response.getChange())));
                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(response.getChange())));
                }
                notifyNeeded = true;
            } else {
                if(((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setChange(String.format("%.4f", Double.parseDouble(data.getChange())));

                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(data.getChange())));
                }

            }

            if(!data.getPerchange().equals(response.getP_change())) {
                data.setPerchange(String.format("%.2f", Double.parseDouble(response.getP_change())));
                notifyNeeded = true;
            } else {
                data.setPerchange(String.format("%.2f", Double.parseDouble(data.getPerchange())));
            }

            if(indexOf(response.getSymbol()) != -1) {
                updateData(indexOf(response.getSymbol()), data);
            }

        }

    }


}




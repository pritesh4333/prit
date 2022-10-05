package com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

public class CurrencyRecycleChildAdapterSingle extends RecyclerView.Adapter<CurrencyRecycleChildAdapterSingle.MyViewHolder> {

    ArrayList<GainerData> gainerData;
    AppCompatActivity activity;
    int childIndex;
    private ArrayList tokenList;
    private String groupPosition;
    private String header;

    public CurrencyRecycleChildAdapterSingle(ArrayList<GainerData> gainerData, AppCompatActivity activity, String groupPosition, String header, int chlidindex) {
        this.gainerData = gainerData;
        this.activity = activity;
        this.childIndex = chlidindex;
        EventBus.getDefault().register(this);
        this.groupPosition = groupPosition;
        this.header = header;
        this.tokenList = new ArrayList<String>();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_view_row, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final GainerData data = gainerData.get(position);

        holder.symbolname_text.setText(data.getDescription());

        if (groupPosition.equalsIgnoreCase("OPEN INTEREST ANALYSIS")) {

            holder.descriptionname_text.setVisibility(View.VISIBLE);

            if (data.getLotSize()!=null&&!String.valueOf(data.getLotSize()).isEmpty()&&!String.valueOf(data.getLotSize()).equalsIgnoreCase("null")) {

                holder.descriptionname_text.setText("OI % " + String.format("%.4f", Double.parseDouble(String.valueOf(data.getLotSize()))));
            }

            if (data.getLotSize().startsWith("-")) {

                holder.descriptionname_text.setTextColor(activity.getResources().getColor(R.color.dark_red_negative));

            } else {
                if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                    holder.descriptionname_text.setTextColor(activity.getResources().getColor(R.color.whitetheambuyColor));
                }else {
                    holder.descriptionname_text.setTextColor(activity.getResources().getColor(R.color.dark_green_positive));
                }


            }
        } else {

            holder.descriptionname_text.setVisibility(View.VISIBLE);
            holder.descriptionname_text.setText("Lots " + StringStuff.commaINRDecorator(data.getLotSize()));
        }

        if (!String.valueOf(data.getLtp()).isEmpty()) {

            holder.ltp_text.setText(String.format("%.4f", Double.parseDouble(String.valueOf(data.getLtp()))));
        }

        if (!String.valueOf(data.getChange()).isEmpty() && !String.valueOf(data.getPerchange()).isEmpty()) {

            holder.change_text.setText(String.format("%.4f", Double.parseDouble(String.valueOf(data.getChange())))
                    + " (" + String.format("%.2f", Double.parseDouble(String.valueOf(data.getPerchange()))) + "%)");
        }

        if (data.getChange().startsWith("-")) {

            holder.change_text.setTextColor(activity.getResources().getColor(R.color.dark_red_negative));

        } else {
            if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
                holder.change_text.setTextColor(activity.getResources().getColor(R.color.whitetheambuyColor));
            }else {
                holder.change_text.setTextColor(activity.getResources().getColor(R.color.dark_green_positive));
            }

        }

        if (tokenList != null) {
            tokenList.add(data.getToken());
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public GreekTextView symbolname_text, descriptionname_text, ltp_text, change_text;
        LinearLayout layout,childmain_layout;
        public MyViewHolder(View view) {
            super(view);
            symbolname_text = itemView.findViewById(R.id.symbolname_text);
            descriptionname_text = itemView.findViewById(R.id.descriptionname_text);
            ltp_text = itemView.findViewById(R.id.ltp_text);
            change_text = itemView.findViewById(R.id.change_text);
            layout = view.findViewById(R.id.child_layout);
            childmain_layout = view.findViewById(R.id.childmain_layout);


            if(AccountDetails.getThemeFlag(activity).equalsIgnoreCase("White")) {
                childmain_layout.setBackgroundColor(activity.getColor(R.color.white));
                symbolname_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                descriptionname_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                ltp_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));
                change_text.setTextColor(activity.getColor(AccountDetails.textColorDropdown));

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


    public boolean containsSymbol(String symbol) {

        if (tokenList != null && tokenList.size() > 0) {
            return tokenList.contains(symbol);

        } else {

            return false;
        }
    }


    public GainerData getItem(int position) {

        return gainerData.get(position);
    }


    public int indexOf(String symbol) {

        return tokenList.indexOf(symbol);
    }


    public void updateData(int position, GainerData gainerDatalist) {
        gainerData.set(position, gainerDatalist);
        notifyItemChanged(position);

        if (groupPosition.equalsIgnoreCase("OPEN INTEREST ANALYSIS")) {

            if (header.equalsIgnoreCase("LONG BUILD UP(OI UP, PRICE UP)")) {

                if (gainerDatalist.getChange().startsWith("-")) {
                    if (isExpandedOIA) {
                        EventBus.getDefault().post(header);
                    }

                }

            } else if (header.equalsIgnoreCase("SHORT BUILD UP(OI UP, PRICE DOWN)")) {

                if (!gainerDatalist.getChange().startsWith("-")) {
                    if (isExpandedOIA) {
                        EventBus.getDefault().post(header);
                    }

                }

            } else if (header.equalsIgnoreCase("LONG UNWINDING(OI DOWN, PRICE DOWN)")) {

                if (!gainerDatalist.getChange().startsWith("-")) {
                    if (isExpandedOIA) {
                        EventBus.getDefault().post(header);
                    }

                }

            } else if (header.equalsIgnoreCase("SHORT COVERING (OI DOWN, PRICE UP)")) {

                if (gainerDatalist.getChange().startsWith("-")) {
                    if (isExpandedOIA) {
                        EventBus.getDefault().post(header);
                    }

                }
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


    public void onEventMainThread(String flag) {
        isExpandedOIA = flag.equalsIgnoreCase("open");

    }

    private void updateBroadcastData(StreamerBroadcastResponse response) {

        if (containsSymbol(response.getSymbol())) {
            boolean notifyNeeded = false;

            GainerData data = getItem(indexOf(response.getSymbol()));

            if (!data.getLtp().equals(response.getLast())) {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setLtp(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    data.setLtp(String.format("%.2f", Double.parseDouble(response.getLast())));
                }
                notifyNeeded = true;
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
                notifyNeeded = true;
            } else {
                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    data.setChange(String.format("%.4f", Double.parseDouble(data.getChange())));

                } else {
                    data.setChange(String.format("%.2f", Double.parseDouble(data.getChange())));
                }

            }

            if (!data.getPerchange().equals(response.getP_change())) {
                data.setPerchange(String.format("%.4f", Double.parseDouble(response.getP_change())));
                notifyNeeded = true;
            } else {
                data.setPerchange(String.format("%.2f", Double.parseDouble(data.getPerchange())));
            }
            updateData(indexOf(response.getSymbol()), data);
        }

    }


}




package com.acumengroup.mobile.BottomTabScreens.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.marketsindicesstock.IndicesStock;
import com.acumengroup.greekmain.core.model.*;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.BottomTabScreens.FiiDiiTabFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.Category;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Admin2 on 10/Jul/2018.
 */

public class CategoryDropdownAdapter extends RecyclerView.Adapter<CategoryDropdownAdapter.CategoryViewHolder> {

    private List<IndicesStock> categories;
    private CategorySelectedListener categorySelectedListener;
    private Context context;
    private ArrayList<String> tokenList;


    public CategoryDropdownAdapter(List<IndicesStock> categories, Context context){
        super();
        this.categories = categories;
        this.context = context;
        tokenList=new ArrayList<>();

            EventBus.getDefault().register(this);

    }
    public void addToken(String token) {
        tokenList.add(token);

    }

    public int indexOf(String symbol) {
        return tokenList.indexOf(symbol);
    }

    public IndicesStock getItem(int position) {
        return categories.get(position);
    }

    public void updateData(int position, IndicesStock indicesStock) {

        categories.set(position, indicesStock);
        notifyItemChanged(position);

    }
    public void setCategorySelectedListener(CategoryDropdownAdapter.CategorySelectedListener categorySelectedListener) {
        this.categorySelectedListener = categorySelectedListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_view_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        final IndicesStock gainerData1Item = categories.get(position);
        try {
            /*holder.nodataTxt.setVisibility(View.GONE);
            holder.symboltxt.setText("TCS".toUpperCase());
            holder.exchangeTxt.setText("NSE".toUpperCase());
            holder.ltpTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble("233.300"))));
            holder.changeTxt.setText("55.0" + "(" + String.format("%.2f", Double.parseDouble("233.300")) + "%)");
            holder.changeTxt.setTextColor(context.getResources().getColor(R.color.bajaj_light_red));*/
            if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                holder.mainLayout.setBackgroundColor(context.getColor(R.color.white));
                holder.symboltxt.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                holder.exchangeTxt.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                holder.ltpTxt.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                holder.changeTxt.setTextColor(context.getColor(AccountDetails.textColorDropdown));
                holder.changeTxt.setTextColor(context.getColor(AccountDetails.textColorDropdown));
            }

           if (gainerData1Item.getDescription() != null) {
                holder.symboltxt.setText(gainerData1Item.getDescription().toUpperCase());
            }
            if (gainerData1Item.getToken() != null) {
                holder.exchangeTxt.setText(getExchangeFromToken(gainerData1Item.getToken()));
            }
            if (gainerData1Item.getLtp() != null && !gainerData1Item.getLtp().equalsIgnoreCase("")) {
                holder.ltpTxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(gainerData1Item.getLtp()))));
            }

            if (gainerData1Item.getChange() != null && !gainerData1Item.getChange().equalsIgnoreCase("") && !gainerData1Item.getP_change().equalsIgnoreCase("")) {
                holder.changeTxt.setText(String.format("%.2f", Double.parseDouble(gainerData1Item.getChange())) + "(" + String.format("%.2f", Double.parseDouble(gainerData1Item.getP_change())) + "%)");
            }

            if (gainerData1Item.getChange().startsWith("-")) {

                holder.changeTxt.setTextColor(context.getResources().getColor(R.color.bajaj_light_red));

            } else {
                if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                    holder.changeTxt.setTextColor(context.getResources().getColor(R.color.whitetheambuyColor));
                }else {
                    holder.changeTxt.setTextColor(context.getResources().getColor(R.color.dark_green_positive));
                }

            }

        } catch (Exception ex) {
            Log.e("exception", ex.getMessage());
        }









        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categorySelectedListener != null){
//                    categorySelectedListener.onCategorySelected(position, gainerData1Item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView symboltxt, exchangeTxt, ltpTxt, changeTxt, nodataTxt;
        LinearLayout layout,mainLayout;

        public CategoryViewHolder(View view) {
            super(view);
            symboltxt = view.findViewById(R.id.symbolname_text);
            exchangeTxt = view.findViewById(R.id.descriptionname_text);
            ltpTxt = view.findViewById(R.id.ltp_text);
            changeTxt = view.findViewById(R.id.change_text);
            layout = view.findViewById(R.id.child_layout);
            mainLayout = view.findViewById(R.id.childmain_layout);
            nodataTxt = view.findViewById(R.id.txt_nodata);

        }
    }

   public interface CategorySelectedListener {
        void onCategorySelected(int position, Category category);
    }

    private String getExchangeFromToken(String token) {
        /*int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NSEEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "NSE";
        } else {
            return "NSE";
        }*/
        //  return "";

        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else if ((tokenInt >= 1903000000) && (tokenInt <= 1903999999)) {
            return "NSECOMM";
        } else if ((tokenInt >= 2003000000) && (tokenInt <= 2003999999)) {
            return "BSECOMM";
        } else if ((tokenInt >= 502000000) && (tokenInt <= 502999999)) {
            return "NSECURR";
        } else if ((tokenInt >= 1302000000) && (tokenInt <= 1302999999)) {
            return "BSECURR";
        } else {
            return "BSE";
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

        IndicesStock data = getItem(index);

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

            if (!data.getP_change().equals(response.getP_change())) {
                data.setP_change(String.format("%.2f", Double.parseDouble(response.getP_change())));

            } else {
                data.setP_change(String.format("%.2f", Double.parseDouble(data.getP_change())));
            }

            Log.e("TOPGainer", "======" + index + "=======>>>>>" + data.getName());
            //data.setName("Rahul Magar");

            updateData(index, data);
        }
    }
}
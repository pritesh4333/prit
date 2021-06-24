package com.acumengroup.mobile.BottomTabScreens.adapter.holder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import de.greenrobot.event.EventBus;

public class GainerTitleHolder extends GroupViewHolder {

    private TextView gTitle;
    ImageView diectionarrow;
    LinearLayout topLayout;
    Context ctx;

    public GainerTitleHolder(View itemView, Context ctx) {
        super(itemView);
        this.ctx=ctx;
        gTitle = itemView.findViewById(R.id.txt_parent_header);
        diectionarrow = itemView.findViewById(R.id.direction_arrow);
        topLayout=itemView.findViewById(R.id.topheader_layout);
        if(AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("White")) {
            topLayout.setBackgroundColor(ctx.getResources().getColor(R.color.light_spinner));
            gTitle.setTextColor(ctx.getResources().getColor(R.color.black));
            diectionarrow.setImageResource(R.drawable.recycle_down_arrow_black);
        }

    }

    @Override
    public void expand() {
        if(AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("White")) {
            diectionarrow.setImageResource(R.drawable.recycle_down_arrow_black);
        }else {
            diectionarrow.setImageResource(R.drawable.recycle_down_arrow);
        }
        if(gTitle.getText().toString().equalsIgnoreCase("OPEN INTEREST ANALYSIS")){
            EventBus.getDefault().post("close");
        }

        if(gTitle.getText().toString().equalsIgnoreCase("FNO Activity")){
            EventBus.getDefault().post("closefno");
        }

        if(gTitle.getText().toString().equalsIgnoreCase("Roll Over")){
            EventBus.getDefault().post("closeroll");
        }


        if(gTitle.getText().toString().equalsIgnoreCase("Top Gainers")){
            EventBus.getDefault().post("Gainersclose");
        }
        if(gTitle.getText().toString().equalsIgnoreCase("Top losers")){
            EventBus.getDefault().post("losersclose");
        }
        if(gTitle.getText().toString().equalsIgnoreCase("Market Movers")){
            EventBus.getDefault().post("Marketclose");
        } if(gTitle.getText().toString().equalsIgnoreCase("MOST ACTIVE BY VALUE")){
            EventBus.getDefault().post("MarketByValueclose");
        }

        Log.i("Adapter", "expand");
    }

    @Override
    public void collapse() {
        Log.i("Adapter", "collapse");

        if(AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("White")) {
            diectionarrow.setImageResource(R.drawable.recycle_up_arrow_black);
        }else {
            diectionarrow.setImageResource(R.drawable.recycle_up_arrow);
        }
        if(gTitle.getText().toString().equalsIgnoreCase("OPEN INTEREST ANALYSIS")){
            EventBus.getDefault().post("open");
        }

        if(gTitle.getText().toString().equalsIgnoreCase("TOP GAINERS")){
            EventBus.getDefault().post("Gainersopen");
        }
        if(gTitle.getText().toString().equalsIgnoreCase("TOP LOSERS")){
            EventBus.getDefault().post("losersopen");
        }
        if(gTitle.getText().toString().equalsIgnoreCase("MARKET MOVERS")){
            EventBus.getDefault().post("Marketopen");
        }if(gTitle.getText().toString().equalsIgnoreCase("MOST ACTIVE BY VALUE")){
            EventBus.getDefault().post("Marketvalueopen");
        }



    }

    public void setGroupName(ExpandableGroup group) {
        gTitle.setText(group.getTitle());
    }
}

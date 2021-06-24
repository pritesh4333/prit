package com.acumengroup.mobile.portfolio;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

/**
 * Created by sushant.patil on 4/20/2016.
 */
public class portfolioFloatAction extends RelativeLayout {
    private GreekTextView pscrip, pwatchlist, plastvisited, pdashboard;

    public portfolioFloatAction(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CoordinatorLayout mBarView = (CoordinatorLayout) mInflater.inflate(R.layout.portfolio_float_view, null);
        addView(mBarView);

        pdashboard = mBarView.findViewById(R.id.portfolio_dashboard);
        pscrip = mBarView.findViewById(R.id.portfolio_scrip);
        pwatchlist = mBarView.findViewById(R.id.portfolio_watchlist);
        plastvisited = mBarView.findViewById(R.id.portfolio_lastvisited);

        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            pdashboard.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pscrip.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pwatchlist.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            plastvisited.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }


    }

}

package com.acumengroup.mobile.markets;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

/**
 * Created by sushant.patil on 4/21/2016.
 */
public class MarketsFloatAction extends RelativeLayout {

    private GreekTextView mequity, mfno, mcommodity, mglobal;

    public MarketsFloatAction(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CoordinatorLayout mBarView = (CoordinatorLayout) mInflater.inflate(R.layout.markets_float_view, null);
        addView(mBarView);

        mequity = mBarView.findViewById(R.id.market_equity);
        mfno = mBarView.findViewById(R.id.market_fno);
        mcommodity = mBarView.findViewById(R.id.market_commodity);
        mglobal = mBarView.findViewById(R.id.market_global);


        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            mequity.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mfno.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mcommodity.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mglobal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
    }
}

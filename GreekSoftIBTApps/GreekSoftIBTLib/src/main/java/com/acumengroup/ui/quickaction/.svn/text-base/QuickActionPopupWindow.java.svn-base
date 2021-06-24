package com.acumengroup.ui.quickaction;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Arcadia
 */
public class QuickActionPopupWindow extends LinearLayout {

    public static final int LAYOUT_ID = 998;
    public static final int SCROLLER_ID = 999;
    public static final int ARROWUP_ID = 996;
    public static final int ARROWDOWM_ID = 997;

    public QuickActionPopupWindow(Context context) {
        super(context);


        setOrientation(LinearLayout.VERTICAL);

        ImageView arraowUp = new ImageView(context);
        arraowUp.setId(ARROWUP_ID);
        arraowUp.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(arraowUp);

        ScrollView scrollView = new ScrollView(context);
        scrollView.setId(SCROLLER_ID);
        scrollView.setFillViewport(true);
        scrollView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        LinearLayout layout = new LinearLayout(context);
        layout.setBaselineAligned(false);
        layout.setId(LAYOUT_ID);

        layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        scrollView.addView(layout);
        addView(scrollView);

        ImageView arrowDown = new ImageView(context);
        arrowDown.setId(ARROWDOWM_ID);
        arrowDown.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(arrowDown);


    }

}

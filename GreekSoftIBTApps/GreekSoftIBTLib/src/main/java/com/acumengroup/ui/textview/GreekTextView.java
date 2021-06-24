package com.acumengroup.ui.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekCustomFont;

/**
 * Created by Arcadia
 */
public class GreekTextView extends AppCompatTextView {

    public GreekTextView(Context context) {
        super(context);
        setup(context, null);
    }

    public GreekTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public GreekTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {

            setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);

            String assetPath = getResources().getString(R.string.GREEK_TEXTVIEW_FONT);

            if (attrs != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GreekView);

                String customTypeface = a.getString(R.styleable.GreekView_customTypeFace);

                if (customTypeface != null) assetPath = customTypeface;
            }

            if (!assetPath.equalsIgnoreCase("font")) setCustomTypeface(getContext(), assetPath);
        }

    }

    public void setCustomTypeface(Context context, String assetPath) {
        setTypeface(GreekCustomFont.getCustomTypeface(context, assetPath));
    }

    public void setColorForChangeGreek(String greekView) {
        if (!(greekView == null) && greekView.equalsIgnoreCase("buy")) {
            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
            }else {
                setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }

        } else if (!(greekView == null) && greekView.equalsIgnoreCase("sell")) {
            setBackgroundColor(getResources().getColor(R.color.red_textcolor));
        } else if (!(greekView == null) && greekView.equalsIgnoreCase("neutral")) {
            setBackgroundColor(getResources().getColor(R.color.side_strip_color));
        } else
            setTextColor(Color.BLACK);
    }

    public void setFont(int type){
        setTypeface(null, type);
    }

    public void setColorForChange(String change) {
        change = change.replace("%", "");
        float diff = Float.parseFloat(change.length() > 0 ? change : "0.0");
        if (diff > 0) {
            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
            }else {
                setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }
        } else if (diff < 0) {
            setBackgroundColor(getResources().getColor(R.color.red_textcolor));
        } else {
            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
            }else {
                setBackgroundColor(getResources().getColor(R.color.green_textcolor));
            }
            setTextColor(Color.BLACK);
        }
    }

    public void setChangeColor(boolean isOdd, String change) {
        change = change.replace("%", "");
        float diff = Float.parseFloat(change.length() > 0 ? change : "0.0");
        if (diff >= 0) {
            setBackgroundResource(isOdd ? R.drawable.grid_positive_odd : R.drawable.grid_positive_even);
        } else if (diff < 0) {
            setBackgroundResource(isOdd ? R.drawable.grid_negative_odd : R.drawable.grid_negative_even);
        }
    }

    public void setGreekView(boolean isOdd, String greekView) {
        if (greekView != null && !greekView.isEmpty()) {
            if ("buy".equalsIgnoreCase(greekView)) {
                setBackgroundResource(isOdd ? R.drawable.grid_positive_odd : R.drawable.grid_positive_even);
            } else if ("sell".equalsIgnoreCase(greekView)) {
                setBackgroundResource(isOdd ? R.drawable.grid_negative_odd : R.drawable.grid_negative_even);
            } else {
                setBackgroundResource(isOdd ? R.color.alternateBackground : R.color.white);
            }
            setText(greekView);
        } else {
            setText("");
            setBackgroundResource(isOdd ? R.color.alternateBackground : R.color.white);
        }
        setFont(Typeface.NORMAL);
        setTextColor(Color.BLACK);
    }

    public void setTextColorForChange(String change) {
        change = change.replace("%", "");
        float diff = Float.parseFloat(change.length() > 0 ? change : "0.0");
        if (diff > 0) {
            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else {
                setTextColor(getResources().getColor(R.color.green_textcolor));
            }
        } else if (diff < 0) {
            setTextColor(getResources().getColor(R.color.red_textcolor));
        } else {
            setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
    }

    public void setColorForLTP(boolean isOdd, String ltp) {
        Double last = Double.parseDouble(ltp);
        if (last >= 0) {
            setBackgroundResource(isOdd ? R.drawable.grid_positive_odd : R.drawable.grid_positive_even);
        } else {
            setBackgroundResource(isOdd ? R.drawable.grid_negative_odd : R.drawable.grid_negative_even);
        }
    }

    public void setColorForLTP(boolean isOdd, boolean isPositive) {
        if (isPositive) {
           
           // setTextColor(isOdd ? R.color.green_textcolor : R.color.green_textcolor);
            setBackgroundResource(isOdd ? R.drawable.grid_positive_odd : R.drawable.grid_positive_even);
        } else {
            //setTextColor(isOdd ? R.color.red_textcolor : R.color.red_textcolor);
            setBackgroundResource(isOdd ? R.drawable.grid_negative_odd : R.drawable.grid_negative_even);
        }
    }

    public void setColorForLTP(boolean isPositive) {
        if (isPositive) {
            setBackgroundResource(R.drawable.grid_positive_odd);
        } else {
            setBackgroundResource(R.drawable.grid_negative_odd);
        }
    }

}

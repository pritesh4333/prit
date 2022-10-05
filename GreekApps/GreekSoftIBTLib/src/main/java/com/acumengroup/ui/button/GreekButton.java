package com.acumengroup.ui.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Handler;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekCustomFont;


/**
 * Created by Arcadia
 */
public class GreekButton extends AppCompatButton {

    private int clickDelay = 2000;
    private boolean isClickDelayEnabled = true;

    public GreekButton(Context context) {
        super(context);
        setup(context, null);
    }

    public GreekButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public GreekButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs);
    }

    public void setClickDelayEnabled(boolean isClickDelayEnabled) {
        this.isClickDelayEnabled = isClickDelayEnabled;
    }

    public void setClickDelay(int clickDelay) {
        this.clickDelay = clickDelay;
    }

    private void setup(Context context, AttributeSet attrs) {

        if (!isInEditMode()) {

            clickDelay = Integer.parseInt(getResources().getString(R.string.GREEK_BUTTON_CLICK_DELAY));

            isClickDelayEnabled = Boolean.parseBoolean(getResources().getString(R.string.GREEK_BUTTON_CLICK_DELAY_ENABLED));

            setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);

            String assetPath = getResources().getString(R.string.GREEK_BUTTON_FONT);

            if (attrs != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GreekView);

                String customTypeface = a.getString(R.styleable.GreekView_customTypeFace);

                a.recycle();

                if (customTypeface != null) assetPath = customTypeface;
            }

            if (!assetPath.equalsIgnoreCase("font")) setCustomTypeface(getContext(), assetPath);

        }

    }

    public void setCustomTypeface(Context context, String assetPath) {
        setTypeface(GreekCustomFont.getCustomTypeface(context, assetPath));
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#performClick()
     *
     * Overrided to handle click delay manually
     */
    @Override
    public boolean performClick() {

        if (isClickDelayEnabled) {
            setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setEnabled(true);
                }
            }, clickDelay);
        }

        return super.performClick();
    }

}

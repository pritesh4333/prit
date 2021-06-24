package com.acumengroup.ui.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekCustomFont;


/**
 * Created by Arcadia
 */
public class GreekEditText extends AppCompatEditText {

    public GreekEditText(Context context) {
        super(context);
        setup(context, null);
    }

    public GreekEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public GreekEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);

            String assetPath = getResources().getString(R.string.GREEK_EDITTEXT_FONT);

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

}

package com.acumengroup.ui.textview;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

public class ScrollingTextView extends GreekTextView {

    private boolean a;

    public ScrollingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a=false;
    }

    public ScrollingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollingTextView(Context context) {
        super(context);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused) super.onWindowFocusChanged(focused);
    }

    @Override
    public boolean isFocused() {
        return true;
    }


    public void setAlwaysMaarquee(boolean flag){
        setSelectAllOnFocus(flag);
        setSingleLine(flag);
        setSelected(flag);
        setHorizontallyScrolling(flag);
        setSingleLine(flag);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        a=flag;
    }

}
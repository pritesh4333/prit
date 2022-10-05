package com.acumengroup.greekmain.chart.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Arcadia
 * Used to handle multiple touch listener simultaneously by using
 * onInterceptTouchEvent(MotionEvent ev)
 */
public class ChartInterceptScrollView extends ScrollView {


    public interface OnEndScrollListener {
        public void onEndScroll();
    }

    private boolean mIsFling;
    private OnEndScrollListener mOnEndScrollListener;

    public ChartInterceptScrollView(Context context) {
        this(context, null, 0);
    }

    public ChartInterceptScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartInterceptScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        mIsFling = true;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (mIsFling) {
            if (Math.abs(y - oldY) < 2 || y >= getMeasuredHeight() || y == 0) {
                if (mOnEndScrollListener != null) {
                    mOnEndScrollListener.onEndScroll();
                }
                mIsFling = false;

            }
        }
    }


    public OnEndScrollListener getOnEndScrollListener() {
        return mOnEndScrollListener;
    }

    public void setOnEndScrollListener(OnEndScrollListener mOnEndScrollListener) {
        this.mOnEndScrollListener = mOnEndScrollListener;
    }


}
package com.acumengroup.ui.quickaction;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Arcadia
 */

public class CustomPopupWindow {

    protected final View parentView;
    protected final PopupWindow window;
    protected final WindowManager windowManager;
    private View root;
    private Drawable background = null;

    public CustomPopupWindow(View anchor) {
        this.parentView = anchor;
        this.window = new PopupWindow(anchor.getContext());
        window.setFocusable(true);
        window.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    CustomPopupWindow.this.window.dismiss();
                    return true;
                }
                return false;
            }
        });

        windowManager = (WindowManager) anchor.getContext().getSystemService(Context.WINDOW_SERVICE);
        onCreate();
    }

    /**
     * @param focusable Default is true.
     */
    public void setFocusable(boolean focusable) {
        window.setFocusable(focusable);
    }

    protected void onCreate() {
    }

    protected void onShow() {
    }

    protected void preShow() {
        if (root == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }

        onShow();

        if (background == null) {
            window.setBackgroundDrawable(new BitmapDrawable());
        } else {
            window.setBackgroundDrawable(background);
        }

        window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setContentView(root);
    }

    public void setBackgroundDrawable(Drawable background) {
        this.background = background;
    }

    public void setContentView(View root) {
        this.root = root;
        window.setContentView(root);
    }

    public void setContentView(int layoutResID) {
        LayoutInflater inflator = (LayoutInflater) parentView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(inflator.inflate(layoutResID, null));
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        window.setOnDismissListener(listener);
    }

    public void dismiss() {
        window.dismiss();
    }
}
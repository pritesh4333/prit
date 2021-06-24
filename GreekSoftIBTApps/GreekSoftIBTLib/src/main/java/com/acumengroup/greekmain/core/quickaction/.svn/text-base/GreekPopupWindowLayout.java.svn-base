package com.acumengroup.greekmain.core.quickaction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.quickaction.GreekPopupWindow.POPUP_DIRECTION;

/**
 * Created by Arcadia
 */
public class GreekPopupWindowLayout extends LinearLayout {

    private GreekPopupWindowListener popupWindowListener;
    // Left or Top
    private ImageView imageView1;
    // Right or Bottom
    private ImageView imageView2;

    public GreekPopupWindowLayout(Context context, GreekPopupWindowListener popupWindowListener) {
        super(context);
        this.popupWindowListener = popupWindowListener;
        imageView1 = new ImageView(context);
        imageView1.setScaleType(ScaleType.FIT_START);
        imageView2 = new ImageView(context);
        imageView2.setScaleType(ScaleType.FIT_START);
    }

    public void setContentView(View view, POPUP_DIRECTION direction) {
        removeAllViews();

        LayoutParams params = null;
        if (direction == POPUP_DIRECTION.LEFT_OR_RIGHT) {
            setOrientation(LinearLayout.HORIZONTAL);
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        } else if (direction == POPUP_DIRECTION.TOP_OR_BOTTOM) {
            setOrientation(LinearLayout.VERTICAL);
            params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }

        addView(imageView1, params);
        addView(view);
        addView(imageView2, params);
    }

    public void setLeftArrow(final Drawable drawableResourceId1, final int padding) {
       /* if (imageView1 != null) {
            imageView1.setImageDrawable(drawableResourceId1);
            imageView2.setImageDrawable(null);
            imageView1.setPadding(0, (padding - (drawableResourceId1.getMinimumHeight() / 2)), 0, 0);
        }*/
        return ;
    }

    public void setRightArrow(final Drawable drawableResourceId2, final int padding) {
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawableResourceId2);
            imageView1.setImageDrawable(null);
            imageView2.setPadding(0, (padding - (drawableResourceId2.getMinimumHeight() / 2)), 0, 0);
        }
    }

    public void setTopArrow(final Drawable drawableResourceId1, final int padding) {
        if (imageView1 != null) {
            imageView1.setImageDrawable(drawableResourceId1);
            imageView2.setImageDrawable(null);
            imageView1.setPadding((padding - (drawableResourceId1.getMinimumWidth() / 2)), 0, 0, 0);
            /*imageView1.post(new Runnable() {

				@Override
				public void run() {
					imageView1.setPadding(
							(padding - (drawableResourceId1.getMinimumWidth() / 2)),
							0, 0, 0);
					imageView1.setImageDrawable(drawableResourceId1);
				}
			});*/
        }
    }

    public void setBottomArrow(final Drawable drawableResourceId2, final int padding) {
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawableResourceId2);
            imageView1.setImageDrawable(null);
            imageView2.setPadding((padding - (drawableResourceId2.getMinimumWidth() / 2)), 0, 0, 0);
			/*imageView2.post(new Runnable() {

				@Override
				public void run() {
					imageView2.setPadding(
							(padding - (drawableResourceId2.getMinimumWidth() / 2)),
							0, 0, 0);
					imageView2.setImageDrawable(drawableResourceId2);
				}
			});*/
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (oldw == w && oldh == h) {
            return;
        }

        if (popupWindowListener != null) popupWindowListener.onSizeChanged(w, h, oldw, oldh);

    }

}
